package jp.co.kts.service.fileExport;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CorporateBillDTO;
import jp.co.kts.app.common.entity.CorporateBillItemDTO;
import jp.co.kts.app.common.entity.ExportCorporateBillDTO;
import jp.co.kts.app.common.entity.MstAccountDTO;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstAccountDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendPaymentManagementDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.dao.common.CorporateBillDAO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.common.TransactionDAO;
import jp.co.kts.dao.mst.AccountDAO;
import jp.co.kts.dao.mst.ClientDAO;
import jp.co.kts.dao.sale.CorporatePaymentManagementDAO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.AccountService;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.sale.CorporatePaymentManagementService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;

public class ExportCorporateBillService extends CorporationService {

	static DateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static DateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	/** リスト、配列の先頭指定する定数 */
	private static int INDEX_ZERO = 0;
	/** 金額系0設定、金額系の0と比較に使用する定数 */
	private static int AMOUNT_ZERO_SET = 0;
	/** コード値の比較：0(数値系) */
	private static long CODE_COMPARISON_ZERO = 0;
	/** 様々な計算で使用する：100 */
	private static int CALCULATION_100 = 100;

	/**
	 * [概要]請求書出力サービス
	 * @param response
	 * @param billList
	 * @param billItemList
	 * @throws Exception
	 */
	public void billList(HttpServletResponse response,
			ExportCorporateBillDTO billList, List<CorporateBillItemDTO> billItemList) throws Exception {

		DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
		String billMonth = billList.getDemandMonth();
		String targetDateStr = billMonth + "/01";
		Date date = format.parse(targetDateStr);

		String fname = "請求書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("bill.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		document.open();

		//商品がなければスキップ
		//while(billItemList.size() == 0) {}
		long accountId = 0;
		//口座ID比較用変数をリストの1件目の口座情報で初期化
		if (billItemList.size() != 0) {
			accountId = billItemList.get(0).getSysAccounId();
		}

		/** 見積書 */
		billHeader(document, writer, baseFont, date, billList);
		bill(document, writer, font, baseFont, date, billList, accountId);

		/** 商品一覧 */
		orderItemDetailNew(document, writer, font, baseFont, billList, billItemList, 595);

		// 改ページ
		document.newPage();

		document.close();

		// return document;

	}

	private static void billHeader(Document document, PdfWriter writer,
			BaseFont baseFont, Date date, ExtendCorporateBillDTO billList) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		//注文者データ
		ClientService clientService = new ClientService();
		ExtendMstClientDTO client = clientService.getDispClientName(billList.getClientBillingNm());

		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 16);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(370, 811);

		// 表示する文字列の設定
		pdfContentByte.showText("請求書");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(290, 792);

		// 請求日に表示する文字列の設定
		String billingDateCutByCutoff = "";

		//日付計算用
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//締日によって締めるのが当月・次月で変わるので「月」までの各文字列を求める
		DateFormat estimateDate = new SimpleDateFormat("yyyy年MM月", Locale.JAPAN);
		//当月
		String billingPresentMonth = estimateDate.format(cal.getTime());
		//次月
		cal.add(Calendar.MONTH, 1);
		String billingNextMonth = estimateDate.format(cal.getTime());
		//末日用に当月に戻す
		cal.add(Calendar.MONTH, -1);

		//締日によって表示日を判断する
		//指定した締日selectedCutoffにより追加するデータを変える
		switch (client.getCutoffDate()) {
		//末日締め
		case 0:
			billingDateCutByCutoff += billingPresentMonth + String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH))
					+ "日";
			break;
		//締日
		//25日締(1)、20日締(2)は前月から当月
		case 1:
			billingDateCutByCutoff += billingPresentMonth + "25日";
			break;
		case 2:
			billingDateCutByCutoff += billingPresentMonth + "20日";
			break;
		//15日締(3)、10日締(4)、5日締め(5)は当月から次月
		case 3:
			billingDateCutByCutoff += billingNextMonth + "15日";
			break;
		case 4:
			billingDateCutByCutoff += billingNextMonth + "10日";
			break;
		case 5:
			billingDateCutByCutoff += billingNextMonth + "05日";
			break;
		default:
			break;
		}

		//請求日の表示
		pdfContentByte.showText("請求日：" + billingDateCutByCutoff + "　No. " + billList.getCorporateBillNo());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(475, 792);

		// テキストの終了
		pdfContentByte.endText();
	}

	/**
	 *
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param date
	 * @param billDto
	 * @throws Exception
	 */
	private static void bill(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, Date date, ExportCorporateBillDTO billDto, long accountId)
			throws Exception {
		PdfContentByte pdfContentByte = writer.getDirectContent();

		/**
		 * ---------------------------------------------------注文者情報START--------
		 * ---------------------------------------------------------
		 */
		//注文者データ
		ClientService clientService = new ClientService();
		ExtendMstClientDTO client = clientService.getDispClientName(billDto.getClientBillingNm());

		//法人データ
		CorporationService corpService = new CorporationService();
		MstCorporationDTO corporation = corpService.getCorporation(billDto.getSysCorporationId());

		//口座データ
		AccountService accountService = new AccountService();

		ExtendMstAccountDTO account = new ExtendMstAccountDTO();
		List<ExtendMstAccountDTO> accountList = new ArrayList<ExtendMstAccountDTO>();
		if (accountId != 0) {
			account = accountService.getAccount(accountId);
		} else {
			accountList = accountService.getAccountList(billDto.getSysCorporationId());
		}

		Integer pageHeight = (int) document.getPageSize().getHeight();

		// テキストの開始
		pdfContentByte.beginText();

		//【左上】得意先番号
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 792);
		// 表示する文字列の設定
		pdfContentByte.showText("(" + client.getClientNo() + ")");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);

		//【左上】請求先名に紐付く得意先情報表示、なければテーブル由来の情報
		String clientString;
		if (!client.getClientNm().isEmpty()) {
			clientString = client.getClientNm();
			if (StringUtils.isNotEmpty(client.getContactPersonNm())) {
				clientString += "," + client.getQuarter() + client.getPosition()
						+ "," + client.getContactPersonNm() + "様";
			} else {
				if (StringUtils.isNotEmpty(client.getQuarter())) {
					clientString += "," + client.getQuarter() + "御中";
				} else {
					clientString += "御中";
				}
			}
			//住所
			clientString += ",〒"
					+ client.getZip() + ","
					+ client.getPrefectures()
					+ client.getMunicipality()
					+ client.getAddress();
			if (!StringUtils.isEmpty(client.getBuildingNm())) {
				clientString += "," + client.getBuildingNm();
			}
			clientString += ",TEL：" + client.getTel();
			clientString += ",FAX：" + client.getFax();
		} else {
			clientString = billDto.getClientBillingNm();
			clientString += ",TEL：" + billDto.getClientTelNo();
			clientString += ",FAX：" + billDto.getClientFaxNo();
		}

		//得意先の郵便番号と法人情報の郵便番号が同じ高さ
		showTextArea(document, writer, baseFont, clientString, ",", 35, 772, 14);

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 652);
		// 表示する文字列の設定
		pdfContentByte.showText("毎度ありがとうございます。下記の通り御請求申し上げます。");

		//////////メモ出力start//////////
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 645);

		if (!StringUtils.isBlank(billDto.getMemo())) {
			// 表示する文字列の設定
			pdfContentByte.showText(billDto.getMemo());
		} else {
			// 表示する文字列の設定
			pdfContentByte.showText("");
		}
		//////////メモ出力end//////////

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 5);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(3, 503);
		// 色の設定
		pdfContentByte.setRGBColorFill(255, 0, 0);
		// 表示する文字列の設定
		pdfContentByte.showText("◀");

		// 色の設定
		pdfContentByte.setRGBColorFill(0, 0, 0);
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 16);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 617);
		//【右上】法人情報設定
		String corporationString = "";
		corporationString += "〒" + corporation.getZip() + ",," + corporation.getAddress();
		corporationString += ",," + corporation.getCorporationFullNm();
		corporationString += ",,TEL：" + corporation.getTelNo() + "　FAX：" + corporation.getFaxNo();

		//口座情報
		if (accountId != 0) {
			if (account != null) {
				corporationString += ",,振込先：" + account.getBankNm() + "　" + account.getBranchNm();
				corporationString += ",," + account.getAccountTypeNm() + "　" + account.getAccountNumber() + "　"
						+ account.getAccountHolder();
			}
		} else {
			if (accountList != null) {
				corporationString += ",,振込先：" + accountList.get(0).getBankNm() + "　" + accountList.get(0).getBranchNm();
				corporationString += ",," + accountList.get(0).getAccountTypeNm() + "　"
						+ accountList.get(0).getAccountNumber() + "　" + accountList.get(0).getAccountHolder();
			}
		}

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);

		showTextArea(document, writer, baseFont, corporationString, ",,", 290, 762, 15);

		/*************注意文言START**************/
		/*
		 * 請求書では、Brembo事業部の場合のみ注意文言を表示させる
		 * 他の帳票（見積書・注文請書）も同様。
		 */
//		if (corporation.getSysCorporationId() == 12) {
		if (true) {
			// フォントとサイズの設定
			pdfContentByte.setFontAndSize(baseFont, 11);

			// 表示位置の設定
			pdfContentByte.setTextMatrix(290, 668);

			// 表示する文字列の設定
			// 注意文言：振込みの際の手数料は御社負担でお願い致します。
			pdfContentByte.showText(WebConst.BEAR_TRANSFER_FEE);
		}
		/*************注意文言END**************/

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(370, 670);

		//請求額テーブル
		PdfPTable pdfPTable = new PdfPTable(5);
		pdfPTable.setTotalWidth(375);
		int width[] = { 75, 75, 75, 75, 75 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String billMonth = billDto.getDemandMonth();
		Date billDate = format.parse(billMonth + "/01");
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		cal.setTime(billDate);
		cal.add(Calendar.MONTH, -1);
		PdfPCell lastClaimHeader = new PdfPCell(
				new Paragraph(String.valueOf(cal.get(Calendar.MONTH) + 1) + "月請求額", font));
		PdfPCell receiveHeader = new PdfPCell(new Paragraph("御入金額", font));
		PdfPCell balanceHeader = new PdfPCell(new Paragraph("繰越金額", font));
		PdfPCell purchaseHeader = new PdfPCell(new Paragraph("御買上額", font));
		PdfPCell currentClaimHeader = new PdfPCell(new Paragraph("今回御請求額", font));

		// 表の要素(列タイトル)を作成
		lastClaimHeader.setHorizontalAlignment(1);
		receiveHeader.setHorizontalAlignment(1);
		balanceHeader.setHorizontalAlignment(1);
		purchaseHeader.setHorizontalAlignment(1);
		currentClaimHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(lastClaimHeader);
		pdfPTable.addCell(receiveHeader);
		pdfPTable.addCell(balanceHeader);
		pdfPTable.addCell(purchaseHeader);
		pdfPTable.addCell(currentClaimHeader);

		PdfPCell lastClaim = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(billDto.getPreMonthBillAmount())), font));
		PdfPCell receive = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(billDto.getReceivePrice())
						.add(BigDecimal.valueOf(billDto.getCharge())).add(BigDecimal.valueOf(billDto.getCharge2()))
						.add(BigDecimal.valueOf(billDto.getCharge3()))), font));
		PdfPCell balance = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCarryOverAmount())), font));
		PdfPCell purchase = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(billDto.getSumClaimPrice())), font));
		PdfPCell currentClaim = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(billDto.getBillAmount())), font));

		// 表の要素(列タイトル)を作成
		lastClaim.setHorizontalAlignment(2);
		receive.setHorizontalAlignment(2);
		balance.setHorizontalAlignment(2);
		purchase.setHorizontalAlignment(2);
		currentClaim.setHorizontalAlignment(2);

		//高さを設定
		lastClaim.setPaddingTop(12f);
		receive.setPaddingTop(12f);
		balance.setPaddingTop(12f);
		purchase.setPaddingTop(12f);
		currentClaim.setPaddingTop(12f);

		// 表の要素を表に追加する
		pdfPTable.addCell(lastClaim);
		pdfPTable.addCell(receive);
		pdfPTable.addCell(balance);
		pdfPTable.addCell(purchase);
		pdfPTable.addCell(currentClaim);

		//テーブル描画
		pdfPTable.writeSelectedRows(0, 6, 0, 2, 30, 640, writer.getDirectContent());

		// テキストの終了
		pdfContentByte.endText();

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.dispose();

		//印鑑欄
		pdfGraphics2D.drawRect(450, pageHeight - 655, 50, 50);
		pdfGraphics2D.drawRect(500, pageHeight - 655, 50, 50);

	}

	/**
	 * 商品一覧
	 *
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param billDto
	 * @param billItemList
	 * @param orderCurrentHeight
	 * @throws IOException
	 * @throws ParseException
	 * @throws DocumentException
	 * @throws DaoException
	 */
	private static void orderItemDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExportCorporateBillDTO billDto,
			List<CorporateBillItemDTO> billItemList,
			float orderCurrentHeight) throws IOException, ParseException, DocumentException, DaoException {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();

		int TABLE_COLS = 7;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		int width[] = { 55, 60, 250, 40, 40, 35, 55 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellSalesDateHeader = new PdfPCell(new Paragraph("日付", font));
		PdfPCell cellItemCdHeader = new PdfPCell(new Paragraph("伝票番号", font));
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		PdfPCell cellTaxRateHeader = new PdfPCell(new Paragraph("税率", font));
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		PdfPCell cellUnitPriceHeader = new PdfPCell(new Paragraph("単価", font));
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("金額", font));

		// 表の要素(列タイトル)を作成
		cellSalesDateHeader.setHorizontalAlignment(1);
		cellItemCdHeader.setHorizontalAlignment(1);
		cellItemNmHeader.setHorizontalAlignment(1);
		cellTaxRateHeader.setHorizontalAlignment(1);
		cellUnitPriceHeader.setHorizontalAlignment(1);
		cellQuantityHeader.setHorizontalAlignment(1);
		cellPriceHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellSalesDateHeader);
		pdfPTable.addCell(cellItemCdHeader);
		pdfPTable.addCell(cellItemNmHeader);
		pdfPTable.addCell(cellTaxRateHeader);
		pdfPTable.addCell(cellUnitPriceHeader);
		pdfPTable.addCell(cellQuantityHeader);
		pdfPTable.addCell(cellPriceHeader);

		/**
		 * ループ(商品LISTのDTOをループさせる予定)
		 */
		int MAX_ROW_WITH_HEADER = 35;
		int MAX_ROW_NO_HEADER = 56;
		int pageNum = 1;
		int maxRow = MAX_ROW_WITH_HEADER;
		float pageHight = 0;
		int rowNum = 0;
		int taxAreaSize = 3;

		ItemService itemService = new ItemService();
		String itemCd;

		//空白セル定義
		PdfPCell blankCell = new PdfPCell(new Paragraph(" ", font));

		// 伝票番号
		PdfPCell cellItemCd = blankCell;
		PdfPCell cellItemNm = blankCell;
		PdfPCell cellTaxRate = blankCell;
		PdfPCell cellQuantity = blankCell;
		PdfPCell cellUnitPrice = blankCell;
		PdfPCell cellPrice = blankCell;
		PdfPCell slipTax = blankCell;
		String slipNo = "";
		int tempRowNum = rowNum;
		//個別税計算用
		int slipPriceSum = 0;
		//最初の伝票の消費税率を初期値とする。
		int taxRate = 0;
		if (billItemList.size() > 0
				&& (billItemList.get(0).getSlipNo() != null || !billItemList.get(0).getSlipNo().isEmpty())) {
			taxRate = corporateBillDAO.getTaxRateOfSlip(billItemList.get(0).getSlipNo());
		}

		//消費税率毎に税抜金額と消費税額を分けて出力するために変数を用意しました。
		int highRateTax = 0;
		int lowRateTax = 0;
		int highRateSlipPriceSum = 0;
		int lowRateSlipPriceSum = 0;

		int billTaxSum = 0;

		//備考欄用
		String slipBillingRemarks = "";
		cellItemCd.setHorizontalAlignment(2);
		cellUnitPrice.setHorizontalAlignment(2);
		cellQuantity.setHorizontalAlignment(2);
		cellPrice.setHorizontalAlignment(2);

		for (CorporateBillItemDTO item : billItemList) {
			// 伝票番号がないとき処理しない
			if (item.getSlipNo().isEmpty()) {
				continue;
			}
			if (rowNum != 0) {
				//改ページの判断
				//次の余白(1)＋伝票・備考枠(2)＋次の商品1つ(1)を表示しきれるかどうか
				if ((rowNum + 4) >= maxRow) {
					pageNum++;
					maxRow += MAX_ROW_NO_HEADER;
				}
			}

			// 伝票番号の切り替わり（1行目のみ必須）
			if (!slipNo.equals(item.getSlipNo())) {
				//1行目でない場合、これまでの伝票分の消費税＋空白行を挟む
				if (rowNum != tempRowNum) {
					//税計算と消費税合計
					//消費税率によって出力を変更するため格納する変数を分ける。
					if (taxRate == WebConst.TAX_RATE_8) {
						lowRateTax += (int) ((double) slipPriceSum * taxRate / 100);
						lowRateSlipPriceSum += slipPriceSum;
					} else if (taxRate == WebConst.TAX_RATE_10) {
						highRateTax += (int) ((double) slipPriceSum * taxRate / 100);
						;
						highRateSlipPriceSum += slipPriceSum;
					}

					slipPriceSum = (int) ((double) slipPriceSum * taxRate / 100);
					billTaxSum += slipPriceSum;
					//記入
					cellItemNm = new PdfPCell(new Paragraph("＜ 消 費 税 ＞　", font));
					cellItemNm.setHorizontalAlignment(2);
					slipTax = new PdfPCell(new Paragraph(String.valueOf(slipPriceSum), font));
					slipTax.setHorizontalAlignment(2);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(slipTax);
					cellItemNm.setHorizontalAlignment(1);
					rowNum++;

					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					rowNum++;

					//次の伝票の処理を行うため、伝票税計算リセット
					taxRate = corporateBillDAO.getTaxRateOfSlip(item.getSlipNo());
					slipPriceSum = 0;

				}
				//日付
				PdfPCell cellSalesDate = new PdfPCell(new Paragraph(item.getSalesDate(), font));
				//伝票番号
				cellItemNm = new PdfPCell(new Paragraph(item.getSlipNo(), font));

				pdfPTable.addCell(cellSalesDate);
				pdfPTable.addCell(cellItemNm);

				//伝票単位の備考欄を取得
				slipBillingRemarks = corporateBillDAO.getBillRemarks(item.getSlipNo());
				//備考欄に入力あれば記入
				if (!StringUtils.isEmpty(slipBillingRemarks)) {

					cellItemNm = new PdfPCell(new Paragraph(slipBillingRemarks, font));
					pdfPTable.addCell(cellItemNm);
					cellItemNm.setHorizontalAlignment(2);
				} else {
					pdfPTable.addCell(blankCell);
				}
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);

				if (getHan1Zen2(slipBillingRemarks) >= 54) {
					rowNum += 2;
				} else {
					rowNum++;
				}

			}

			long itemId = item.getSysItemId();
			if (itemId != 0) {

				//				itemCd = corporateSaleDisplayService.getMstItemDTO(itemId).getItemCode();
				itemCd = itemService.getMstItemDTO(itemId).getItemCode();
			} else {
				itemCd = item.getItemCode();
			}

			//商品コード
			//			cellItemCd = new PdfPCell(new Paragraph(itemCd, font));
			// 商品名
			cellItemNm = new PdfPCell(new Paragraph("　" + item.getItemNm(), font));
			// 数量
			cellQuantity = new PdfPCell(new Paragraph(
					String.valueOf(item.getOrderNum()), font));
			//税率
			cellTaxRate = new PdfPCell(new Paragraph(
					String.valueOf(taxRate + "％"), font));
			// 単価
			cellUnitPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(item.getPieceRate())), font));
			// 金額
			cellPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(item.getPieceRate()
							* item.getOrderNum())),
					font));
			//伝票税計算リセット用に金額加算
			slipPriceSum += item.getPieceRate() * item.getOrderNum();

			//			cellSalesDate.setHorizontalAlignment(1);
			//			cellItemCd.setHorizontalAlignment(2);
			cellItemNm.setHorizontalAlignment(0);
			cellTaxRate.setHorizontalAlignment(1);
			cellUnitPrice.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);
			cellPrice.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellTaxRate);
			pdfPTable.addCell(cellUnitPrice);
			pdfPTable.addCell(cellQuantity);
			pdfPTable.addCell(cellPrice);

			//ループ時の比較用
			slipNo = item.getSlipNo();

			if (getHan1Zen2(item.getItemNm()) >= 54) {
				rowNum += 2;
			} else {
				rowNum++;
			}
		}

		if (billItemList.size() > 0) {
			//最後の伝票の消費税
			//税計算と消費税合計
			//消費税率によって出力を変更するため格納する変数を分ける。
			if (taxRate == WebConst.TAX_RATE_8) {
				lowRateTax += (int) ((double) slipPriceSum * taxRate / 100);
				lowRateSlipPriceSum += slipPriceSum;
			} else if (taxRate == WebConst.TAX_RATE_10) {
				highRateTax += (int) ((double) slipPriceSum * taxRate / 100);
				;
				highRateSlipPriceSum += slipPriceSum;
			}
			taxRate = corporateBillDAO.getTaxRateOfSlip(slipNo);
			slipPriceSum = (int) ((double) slipPriceSum * taxRate / 100);
			billTaxSum += slipPriceSum;
			//記入
			cellItemNm = new PdfPCell(new Paragraph("＜ 消 費 税 ＞　", font));
			cellItemNm.setHorizontalAlignment(2);
			slipTax = new PdfPCell(new Paragraph(String.valueOf(slipPriceSum), font));
			slipTax.setHorizontalAlignment(2);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(slipTax);
			cellItemNm.setHorizontalAlignment(1);
			rowNum++;
			//伝票税計算リセット
			slipPriceSum = 0;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			rowNum++;
		}

		//----------------------- 振込金額（入金額欄）、フリーワード(手数料欄)がない場合は非表示
		if (!StringUtils.isEmpty(billDto.getReceiveDate()) || !StringUtils.isEmpty(billDto.getFreeWord()) ||
				billDto.getReceivePrice() != 0 || !StringUtils.isEmpty(billDto.getFreeWord2()) ||
				billDto.getCharge2() != 0 || !StringUtils.isEmpty(billDto.getFreeWord3()) ||
				billDto.getCharge3() != 0) {

			// 振込金額、フリーワード(手数料)エリアの表示
			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			rowNum++;

			// フリーワードの設定
			//入金日
			PdfPCell cellSalesDateCArea = new PdfPCell(new Paragraph(billDto.getReceiveDate(), font));
			PdfPCell cellItemNmCArea = new PdfPCell(new Paragraph("入金額", font));
			PdfPCell cellOne = new PdfPCell(new Paragraph("1", font));
			//単価用入金額
			PdfPCell cellUnitPriceCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getReceivePrice())), font));
			//金額用入金額
			PdfPCell cellPriceCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getReceivePrice())), font));

			//単価用金額1
			PdfPCell cellUnitChargeCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())), font));
			//金額用金額1
			PdfPCell cellChargeCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())), font));
			//単価用金額2
			PdfPCell cellUnitCharge2CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge2())), font));
			//金額用金額2
			PdfPCell cellCharge2CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge2())), font));
			//単価用金額3
			PdfPCell cellUnitCharge3CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge3())), font));
			//金額用金額3
			PdfPCell cellCharge3CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge3())), font));
			//フリーワード1
			PdfPCell cellFreeWord1CArea = new PdfPCell(new Paragraph(billDto.getFreeWord(), font));
			//フリーワード2
			PdfPCell cellFreeWord2CArea = new PdfPCell(new Paragraph(billDto.getFreeWord2(), font));
			//フリーワード3
			PdfPCell cellFreeWord3CArea = new PdfPCell(new Paragraph(billDto.getFreeWord3(), font));

			// 表の要素(列タイトル)を揃え（アライン）を設定する
			cellSalesDateCArea.setHorizontalAlignment(1);
			cellOne.setHorizontalAlignment(2);
			cellUnitPriceCArea.setHorizontalAlignment(2);
			cellPriceCArea.setHorizontalAlignment(2);
			cellUnitChargeCArea.setHorizontalAlignment(2);
			cellChargeCArea.setHorizontalAlignment(2);
			cellUnitCharge2CArea.setHorizontalAlignment(2);
			cellCharge2CArea.setHorizontalAlignment(2);
			cellUnitCharge3CArea.setHorizontalAlignment(2);
			cellCharge3CArea.setHorizontalAlignment(2);

			// 表の要素を表に追加する
			//入金日
			if (billDto.getReceiveDate() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellSalesDateCArea);
			}

			pdfPTable.addCell(blankCell);

			//入金額があれば入金額表示
			if (billDto.getReceivePrice() == 0) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellItemNmCArea);
			}

			//入金額
			if (billDto.getReceivePrice() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitPriceCArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellPriceCArea);
				rowNum++;
			}

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			//フリーワード1
			if (billDto.getFreeWord() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellFreeWord1CArea);
			}

			//金額1
			if (billDto.getCharge() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitChargeCArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellChargeCArea);
				rowNum++;
			}

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			//フリーワード2
			if (billDto.getFreeWord2() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellFreeWord2CArea);
			}

			//金額2
			if (billDto.getCharge2() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitCharge2CArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellCharge2CArea);
				rowNum++;
			}

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			//フリーワード3
			if (billDto.getFreeWord3() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellFreeWord3CArea);
			}

			//金額3
			if (billDto.getCharge3() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitCharge3CArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellCharge3CArea);
				rowNum++;
			}

			// 手数料がある場合、出力する。
			//			if(billDto.getCharge() != 0){
			//				// 手数料の設定（フリーワードに入力があれば名称を変更）
			//				if (billDto.getFreeWord().isEmpty()) {
			//					cellItemNmCArea =  new PdfPCell(new Paragraph("手数料" ,font));
			//				} else {
			//					//フリーワード1
			//					cellItemNmCArea =  new PdfPCell(new Paragraph(billDto.getFreeWord() ,font));
			//				}
			//				cellUnitPriceCArea =  new PdfPCell(new Paragraph(
			//						StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())) ,font));
			//				cellPriceCArea =  new PdfPCell(new Paragraph(
			//						StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())) ,font));
			//
			//				// 表の要素(列タイトル)を揃え（アライン）を設定する
			//				cellUnitPriceCArea.setHorizontalAlignment(2);
			//				cellPriceCArea.setHorizontalAlignment(2);
			//
			//				// 表の要素を表に追加する
			//				pdfPTable.addCell(blankCell);
			//				pdfPTable.addCell(blankCell);
			//				pdfPTable.addCell(cellItemNmCArea);
			//				pdfPTable.addCell(cellUnitPriceCArea);
			//				pdfPTable.addCell(blankCell);
			//				pdfPTable.addCell(cellPriceCArea);
			//				rowNum++;
			//			}
		}

		//外税の場合は消費税を表示
		if (billDto.getConsumptionTax() != 0) {
			//改ページの判断
			//空白行(1)＋税抜合計(1)＋消費税(1)+空白行(2)+10%消費税(2)+8%消費税(2)+空白行(1)+請求額(1)を表示しきれるかどうか
			if ((rowNum + 11) >= maxRow) {
				pageNum++;
				maxRow += MAX_ROW_NO_HEADER;
			}

			int nextRowNum = rowNum + taxAreaSize;
			if (nextRowNum >= MAX_ROW_WITH_HEADER + (MAX_ROW_NO_HEADER * (pageNum - 1))) {
				for (int i = rowNum; i < maxRow - 1; i++) {
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					rowNum++;
				}
				// 表の要素を表に追加する
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellItemCdHeader);
				pdfPTable.addCell(cellItemNmHeader);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitPriceHeader);
				pdfPTable.addCell(cellQuantityHeader);
				pdfPTable.addCell(cellPriceHeader);
				rowNum++;

				maxRow += MAX_ROW_NO_HEADER;
				pageNum++;
			} else {
				//空白行
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				rowNum++;
			}

			//税抜き合計金額
			cellItemNm = new PdfPCell(new Paragraph("＜税抜合計金額＞　", font));
			PdfPCell sumPriceRateCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getSumClaimPrice() - billDto.getConsumptionTax())),
					font));

			cellItemNm.setHorizontalAlignment(2);
			sumPriceRateCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(sumPriceRateCell);
			rowNum++;

			//消費税表示
			cellItemNm = new PdfPCell(new Paragraph("＜消費税合計＞　", font));
			PdfPCell taxSumCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billTaxSum)), font));

			cellItemNm.setHorizontalAlignment(2);
			taxSumCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(taxSumCell);
			rowNum++;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			rowNum++;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			rowNum++;

			//10%対象（税抜）表示
			cellItemNm = new PdfPCell(new Paragraph("＜10％対象(税抜き)＞　", font));
			PdfPCell highRateSlipPriceSumCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(highRateSlipPriceSum)), font));

			cellItemNm.setHorizontalAlignment(2);
			highRateSlipPriceSumCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(highRateSlipPriceSumCell);
			rowNum++;

			//10%対象消費税表示
			cellItemNm = new PdfPCell(new Paragraph("＜10％対象消費税＞　", font));
			PdfPCell highRateTaxCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(highRateTax)), font));

			cellItemNm.setHorizontalAlignment(2);
			highRateTaxCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(highRateTaxCell);
			rowNum++;

			//8%対象（税抜）表示
			cellItemNm = new PdfPCell(new Paragraph("＜8％対象(税抜き)＞　", font));
			PdfPCell lowRateSlipPriceSumCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(lowRateSlipPriceSum)), font));

			cellItemNm.setHorizontalAlignment(2);
			lowRateSlipPriceSumCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(lowRateSlipPriceSumCell);
			rowNum++;

			//8%対象消費税表示
			cellItemNm = new PdfPCell(new Paragraph("＜8％対象消費税＞　", font));
			PdfPCell lowRateTaxCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(lowRateTax)), font));

			cellItemNm.setHorizontalAlignment(2);
			lowRateTaxCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(lowRateTaxCell);
			rowNum++;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			rowNum++;

			//請求金額表示
			cellItemNm = new PdfPCell(new Paragraph("＜請求金額＞　", font));
			PdfPCell CurrentBillAmount = new PdfPCell(
					new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(billDto.getBillAmount())), font));
			cellItemNm.setHorizontalAlignment(2);
			CurrentBillAmount.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(CurrentBillAmount);
			rowNum++;
		}

		//一番下までテーブルを埋める
		for (int i = rowNum; i < maxRow - 2; i++) {

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			rowNum++;
		}

		//最下段行
		PdfPCell blankSpanCell = new PdfPCell(new Paragraph(" ", font));
		blankSpanCell.setColspan(3);

		PdfPCell cellSum = new PdfPCell(new Paragraph("合計", font));
		PdfPCell cellSumClaimPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(billDto.getSumClaimPrice())), font));

		blankSpanCell.setPaddingTop(7f);
		cellSum.setPaddingTop(7f);
		cellSum.setPadding(3f);
		cellSumClaimPrice.setPaddingTop(7f);
		cellSumClaimPrice.setPadding(3f);

		cellSum.setHorizontalAlignment(1);
		cellSumClaimPrice.setHorizontalAlignment(2);

		pdfPTable.addCell(blankSpanCell);
		pdfPTable.addCell(cellSum);
		pdfPTable.addCell(cellSumClaimPrice);
		rowNum += 2;

		//テーブル描画
		pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, MAX_ROW_WITH_HEADER, 30, orderCurrentHeight,
				writer.getDirectContent());
		if (pageNum > 1) {
			for (int i = 1; i < pageNum; i++) {
				document.newPage();
				int rowStart = MAX_ROW_WITH_HEADER + (MAX_ROW_NO_HEADER * (i - 1));
				int rowEnd = MAX_ROW_WITH_HEADER + (MAX_ROW_NO_HEADER * i);
				pdfPTable.writeSelectedRows(0, TABLE_COLS, rowStart, rowEnd, 30, 800,
						writer.getDirectContent());
			}
		}

		/** 多分、計算式違う、下の計算から、テーブルを記述始めているyposから以下の値を引かないと欲しい値が算出されない。 */
		float height = pdfPTable.calculateHeights() - pageHight;
	}

	private static boolean isNewPageForOrderItemTable(PdfPTable pdfPTable, Document document, PdfWriter writer,
			float yPos, int pageNumber, int repaginationRow, int totalRowNum, float orderCurrentHeight) {

		if (pdfPTable.calculateHeights() >= orderCurrentHeight - 30 + (pageNumber - 1) * (820 - 40)) {
			pdfPTable.writeSelectedRows(0, 7, repaginationRow, totalRowNum, 30, yPos, writer.getDirectContent());
			return true;
		}

		return false;
	}

	private static void orderItemDetailNew(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExportCorporateBillDTO billDto,
			List<CorporateBillItemDTO> billItemList,
			float orderCurrentHeight) throws IOException, ParseException, DocumentException, DaoException {

		int PAGE_HEIGHT = 820;
		int pageNumber  = 1;
		float yPos = orderCurrentHeight;
		int repaginationRow = 0;
		int totalRowNum = 0;

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();

		int TABLE_COLS = 7;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		int width[] = { 55, 60, 250, 40, 40, 35, 55 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellSalesDateHeader = new PdfPCell(new Paragraph("日付", font));
		PdfPCell cellItemCdHeader = new PdfPCell(new Paragraph("伝票番号", font));
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		PdfPCell cellTaxRateHeader = new PdfPCell(new Paragraph("税率", font));
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		PdfPCell cellUnitPriceHeader = new PdfPCell(new Paragraph("単価", font));
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("金額", font));

		// 表の要素(列タイトル)を作成
		cellSalesDateHeader.setHorizontalAlignment(1);
		cellItemCdHeader.setHorizontalAlignment(1);
		cellItemNmHeader.setHorizontalAlignment(1);
		cellTaxRateHeader.setHorizontalAlignment(1);
		cellUnitPriceHeader.setHorizontalAlignment(1);
		cellQuantityHeader.setHorizontalAlignment(1);
		cellPriceHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellSalesDateHeader);
		pdfPTable.addCell(cellItemCdHeader);
		pdfPTable.addCell(cellItemNmHeader);
		pdfPTable.addCell(cellTaxRateHeader);
		pdfPTable.addCell(cellUnitPriceHeader);
		pdfPTable.addCell(cellQuantityHeader);
		pdfPTable.addCell(cellPriceHeader);

		/**
		 * ループ(商品LISTのDTOをループさせる予定)
		 */
		int taxAreaSize = 3;

		ItemService itemService = new ItemService();
		String itemCd;

		//空白セル定義
		PdfPCell blankCell = new PdfPCell(new Paragraph(" ", font));

		// 伝票番号
		PdfPCell cellItemCd = blankCell;
		PdfPCell cellItemNm = blankCell;
		PdfPCell cellTaxRate = blankCell;
		PdfPCell cellQuantity = blankCell;
		PdfPCell cellUnitPrice = blankCell;
		PdfPCell cellPrice = blankCell;
		PdfPCell slipTax = blankCell;
		String slipNo = "";

		//個別税計算用
		int slipPriceSum = 0;
		int slipPriceTaxSum = 0;
		//最初の伝票の消費税率を初期値とする。
		int taxRate = 0;
		if (billItemList.size() > 0
				&& (billItemList.get(0).getSlipNo() != null || !billItemList.get(0).getSlipNo().isEmpty())) {
			taxRate = corporateBillDAO.getTaxRateOfSlip(billItemList.get(0).getSlipNo());
		}

		//消費税率毎に税抜金額と消費税額を分けて出力するために変数を用意しました。
		int highRateTax = 0;
		int lowRateTax = 0;
		int highRateSlipPriceSum = 0;
		int lowRateSlipPriceSum = 0;

		int billTaxSum = 0;

		//備考欄用
		String slipBillingRemarks = "";
		cellItemCd.setHorizontalAlignment(2);
		cellUnitPrice.setHorizontalAlignment(2);
		cellQuantity.setHorizontalAlignment(2);
		cellPrice.setHorizontalAlignment(2);

		for (CorporateBillItemDTO item : billItemList) {
			// 伝票番号がないとき処理しない
			if (item.getSlipNo().isEmpty()) {
				continue;
			}

			// 伝票番号の切り替わり（1行目のみ必須）
			if (!slipNo.equals(item.getSlipNo())) {
				//1行目でない場合、これまでの伝票分の消費税＋空白行を挟む
				if (totalRowNum != 0) {
					//税計算と消費税合計
					//消費税率によって出力を変更するため格納する変数を分ける。
					if (taxRate == WebConst.TAX_RATE_8) {
						lowRateTax += (int) ((double) slipPriceTaxSum * taxRate / 100);
						lowRateSlipPriceSum += slipPriceSum;
					} else if (taxRate == WebConst.TAX_RATE_10) {
						highRateTax += (int) ((double) slipPriceTaxSum * taxRate / 100);
						;
						highRateSlipPriceSum += slipPriceSum;
					}

					slipPriceTaxSum = (int) ((double) slipPriceTaxSum * taxRate / 100);
					billTaxSum += slipPriceTaxSum;
					//記入
					cellItemNm = new PdfPCell(new Paragraph("＜ 消 費 税 ＞　", font));
					cellItemNm.setHorizontalAlignment(2);
					slipTax = new PdfPCell(new Paragraph(String.valueOf(slipPriceTaxSum), font));
					slipTax.setHorizontalAlignment(2);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(slipTax);
					cellItemNm.setHorizontalAlignment(1);

					if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
						document.newPage();
						yPos = PAGE_HEIGHT - 20;
						repaginationRow = totalRowNum;
						pageNumber++;
					}
					totalRowNum++;

					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);

					if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
						document.newPage();
						yPos = PAGE_HEIGHT - 20;
						repaginationRow = totalRowNum;
						pageNumber++;
					}
					totalRowNum++;

					//次の伝票の処理を行うため、伝票税計算リセット
					taxRate = corporateBillDAO.getTaxRateOfSlip(item.getSlipNo());
					slipPriceSum = 0;
					slipPriceTaxSum = 0;

				}
				//日付
				PdfPCell cellSalesDate = new PdfPCell(new Paragraph(item.getSalesDate(), font));
				//伝票番号
				cellItemNm = new PdfPCell(new Paragraph(item.getSlipNo(), font));

				pdfPTable.addCell(cellSalesDate);
				pdfPTable.addCell(cellItemNm);

				//伝票単位の備考欄を取得
				slipBillingRemarks = corporateBillDAO.getBillRemarks(item.getSlipNo());
				//備考欄に入力あれば記入
				if (!StringUtils.isEmpty(slipBillingRemarks)) {

					cellItemNm = new PdfPCell(new Paragraph(slipBillingRemarks, font));
					pdfPTable.addCell(cellItemNm);
					cellItemNm.setHorizontalAlignment(2);
				} else {
					pdfPTable.addCell(blankCell);
				}
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);

				if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
					document.newPage();
					yPos = PAGE_HEIGHT - 20;
					repaginationRow = totalRowNum;
					pageNumber++;
				}
				totalRowNum++;

			}

			long itemId = item.getSysItemId();
			if (itemId != 0) {

				//				itemCd = corporateSaleDisplayService.getMstItemDTO(itemId).getItemCode();
				itemCd = itemService.getMstItemDTO(itemId).getItemCode();
			} else {
				itemCd = item.getItemCode();
			}

			//商品コード
			//			cellItemCd = new PdfPCell(new Paragraph(itemCd, font));
			// 商品名
			cellItemNm = new PdfPCell(new Paragraph("　" + item.getItemNm(), font));
			// 数量
			cellQuantity = new PdfPCell(new Paragraph(
					String.valueOf(item.getOrderNum()), font));
			//税率
			cellTaxRate = new PdfPCell(new Paragraph(
					String.valueOf(taxRate + "％"), font));
			// 単価
			cellUnitPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(item.getPieceRate())), font));
			// 金額
			cellPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(item.getPieceRate()
							* item.getOrderNum())),
					font));
			//伝票税計算リセット用に金額加算
			slipPriceSum += item.getPieceRate() * item.getOrderNum();

			//値引き（77）のみ税計算をさせないため別管理する
			if (StringUtils.equals(item.getItemCode(), "77")) {
			} else {
				slipPriceTaxSum += item.getPieceRate() * item.getOrderNum();
			}

			//			cellSalesDate.setHorizontalAlignment(1);
			//			cellItemCd.setHorizontalAlignment(2);
			cellItemNm.setHorizontalAlignment(0);
			cellTaxRate.setHorizontalAlignment(1);
			cellUnitPrice.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);
			cellPrice.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellTaxRate);
			pdfPTable.addCell(cellUnitPrice);
			pdfPTable.addCell(cellQuantity);
			pdfPTable.addCell(cellPrice);

			//ループ時の比較用
			slipNo = item.getSlipNo();

			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;
		}

		if (billItemList.size() > 0) {
			//最後の伝票の消費税
			//税計算と消費税合計
			//消費税率によって出力を変更するため格納する変数を分ける。
			if (taxRate == WebConst.TAX_RATE_8) {
				lowRateTax += (int) ((double) slipPriceTaxSum * taxRate / 100);
				lowRateSlipPriceSum += slipPriceSum;
			} else if (taxRate == WebConst.TAX_RATE_10) {
				highRateTax += (int) ((double) slipPriceTaxSum * taxRate / 100);
				;
				highRateSlipPriceSum += slipPriceSum;
			}
			taxRate = corporateBillDAO.getTaxRateOfSlip(slipNo);
			slipPriceTaxSum = (int) ((double) slipPriceTaxSum * taxRate / 100);
			billTaxSum += slipPriceTaxSum;
			//記入
			cellItemNm = new PdfPCell(new Paragraph("＜ 消 費 税 ＞　", font));
			cellItemNm.setHorizontalAlignment(2);
			slipTax = new PdfPCell(new Paragraph(String.valueOf(slipPriceTaxSum), font));
			slipTax.setHorizontalAlignment(2);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(slipTax);
			cellItemNm.setHorizontalAlignment(1);

			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//伝票税計算リセット
			slipPriceSum = 0;
			slipPriceTaxSum = 0;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;
		}

		//----------------------- 振込金額（入金額欄）、フリーワード(手数料欄)がない場合は非表示
		if (!StringUtils.isEmpty(billDto.getReceiveDate()) || !StringUtils.isEmpty(billDto.getFreeWord()) ||
				billDto.getReceivePrice() != 0 || !StringUtils.isEmpty(billDto.getFreeWord2()) ||
				billDto.getCharge2() != 0 || !StringUtils.isEmpty(billDto.getFreeWord3()) ||
				billDto.getCharge3() != 0) {

			// 振込金額、フリーワード(手数料)エリアの表示
			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			// フリーワードの設定
			//入金日
			PdfPCell cellSalesDateCArea = new PdfPCell(new Paragraph(billDto.getReceiveDate(), font));
			PdfPCell cellItemNmCArea = new PdfPCell(new Paragraph("入金額", font));
			PdfPCell cellOne = new PdfPCell(new Paragraph("1", font));
			//単価用入金額
			PdfPCell cellUnitPriceCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getReceivePrice())), font));
			//金額用入金額
			PdfPCell cellPriceCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getReceivePrice())), font));

			//単価用金額1
			PdfPCell cellUnitChargeCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())), font));
			//金額用金額1
			PdfPCell cellChargeCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())), font));
			//単価用金額2
			PdfPCell cellUnitCharge2CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge2())), font));
			//金額用金額2
			PdfPCell cellCharge2CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge2())), font));
			//単価用金額3
			PdfPCell cellUnitCharge3CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge3())), font));
			//金額用金額3
			PdfPCell cellCharge3CArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge3())), font));
			//フリーワード1
			PdfPCell cellFreeWord1CArea = new PdfPCell(new Paragraph(billDto.getFreeWord(), font));
			//フリーワード2
			PdfPCell cellFreeWord2CArea = new PdfPCell(new Paragraph(billDto.getFreeWord2(), font));
			//フリーワード3
			PdfPCell cellFreeWord3CArea = new PdfPCell(new Paragraph(billDto.getFreeWord3(), font));

			// 表の要素(列タイトル)を揃え（アライン）を設定する
			cellSalesDateCArea.setHorizontalAlignment(1);
			cellOne.setHorizontalAlignment(2);
			cellUnitPriceCArea.setHorizontalAlignment(2);
			cellPriceCArea.setHorizontalAlignment(2);
			cellUnitChargeCArea.setHorizontalAlignment(2);
			cellChargeCArea.setHorizontalAlignment(2);
			cellUnitCharge2CArea.setHorizontalAlignment(2);
			cellCharge2CArea.setHorizontalAlignment(2);
			cellUnitCharge3CArea.setHorizontalAlignment(2);
			cellCharge3CArea.setHorizontalAlignment(2);

			// 表の要素を表に追加する
			//入金日
			if (billDto.getReceiveDate() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellSalesDateCArea);
			}

			pdfPTable.addCell(blankCell);

			//入金額があれば入金額表示
			if (billDto.getReceivePrice() == 0) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellItemNmCArea);
			}

			//入金額
			if (billDto.getReceivePrice() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitPriceCArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellPriceCArea);
			}

			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			//フリーワード1
			if (billDto.getFreeWord() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellFreeWord1CArea);
			}

			//金額1
			if (billDto.getCharge() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitChargeCArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellChargeCArea);
			}
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			//フリーワード2
			if (billDto.getFreeWord2() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellFreeWord2CArea);
			}

			//金額2
			if (billDto.getCharge2() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitCharge2CArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellCharge2CArea);
			}
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;


			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			//フリーワード3
			if (billDto.getFreeWord3() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellFreeWord3CArea);
			}

			//金額3
			if (billDto.getCharge3() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellUnitCharge3CArea);
				pdfPTable.addCell(cellOne);
				pdfPTable.addCell(cellCharge3CArea);
			}

			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			// 手数料がある場合、出力する。
			//			if(billDto.getCharge() != 0){
			//				// 手数料の設定（フリーワードに入力があれば名称を変更）
			//				if (billDto.getFreeWord().isEmpty()) {
			//					cellItemNmCArea =  new PdfPCell(new Paragraph("手数料" ,font));
			//				} else {
			//					//フリーワード1
			//					cellItemNmCArea =  new PdfPCell(new Paragraph(billDto.getFreeWord() ,font));
			//				}
			//				cellUnitPriceCArea =  new PdfPCell(new Paragraph(
			//						StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())) ,font));
			//				cellPriceCArea =  new PdfPCell(new Paragraph(
			//						StringUtil.formatCalc(BigDecimal.valueOf(billDto.getCharge())) ,font));
			//
			//				// 表の要素(列タイトル)を揃え（アライン）を設定する
			//				cellUnitPriceCArea.setHorizontalAlignment(2);
			//				cellPriceCArea.setHorizontalAlignment(2);
			//
			//				// 表の要素を表に追加する
			//				pdfPTable.addCell(blankCell);
			//				pdfPTable.addCell(blankCell);
			//				pdfPTable.addCell(cellItemNmCArea);
			//				pdfPTable.addCell(cellUnitPriceCArea);
			//				pdfPTable.addCell(blankCell);
			//				pdfPTable.addCell(cellPriceCArea);
			//				rowNum++;
			//			}
		}

		//外税の場合は消費税を表示
		if (billDto.getConsumptionTax() != 0) {
			//改ページの判断
			//空白行(1)＋税抜合計(1)＋消費税(1)+空白行(2)+10%消費税(2)+8%消費税(2)+空白行(1)+請求額(1)を表示しきれるかどうか

//			int nextRowNum = rowNum + taxAreaSize;
//			if (nextRowNum >= MAX_ROW_WITH_HEADER + (MAX_ROW_NO_HEADER * (pageNum - 1))) {
//				for (int i = rowNum; i < maxRow - 1; i++) {
//					pdfPTable.addCell(blankCell);
//					pdfPTable.addCell(blankCell);
//					pdfPTable.addCell(blankCell);
//					pdfPTable.addCell(blankCell);
//					pdfPTable.addCell(blankCell);
//					pdfPTable.addCell(blankCell);
//					pdfPTable.addCell(blankCell);
//					rowNum++;
//				}
//				// 表の要素を表に追加する
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(cellItemCdHeader);
//				pdfPTable.addCell(cellItemNmHeader);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(cellUnitPriceHeader);
//				pdfPTable.addCell(cellQuantityHeader);
//				pdfPTable.addCell(cellPriceHeader);
//				rowNum++;
//
//				maxRow += MAX_ROW_NO_HEADER;
//				pageNum++;
//			} else {
//				//空白行
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				rowNum++;
//			}

			//空白行
//			for (int i = 0; i < taxAreaSize; i++) {
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//				pdfPTable.addCell(blankCell);
//
//				if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
//					document.newPage();
//					yPos = PAGE_HEIGHT - 20;
//					repaginationRow = totalRowNum;
//					pageNumber++;
//				}
//				totalRowNum++;
//			}

			//税抜き合計金額
			cellItemNm = new PdfPCell(new Paragraph("＜税抜合計金額＞　", font));
			PdfPCell sumPriceRateCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billDto.getSumClaimPrice() - billDto.getConsumptionTax())),
					font));

			cellItemNm.setHorizontalAlignment(2);
			sumPriceRateCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(sumPriceRateCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//消費税表示
			cellItemNm = new PdfPCell(new Paragraph("＜消費税合計＞　", font));
			PdfPCell taxSumCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(billTaxSum)), font));

			cellItemNm.setHorizontalAlignment(2);
			taxSumCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(taxSumCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//10%対象（税抜）表示
			cellItemNm = new PdfPCell(new Paragraph("＜10％対象(税抜き)＞　", font));
			PdfPCell highRateSlipPriceSumCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(highRateSlipPriceSum)), font));

			cellItemNm.setHorizontalAlignment(2);
			highRateSlipPriceSumCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(highRateSlipPriceSumCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//10%対象消費税表示
			cellItemNm = new PdfPCell(new Paragraph("＜10％対象消費税＞　", font));
			PdfPCell highRateTaxCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(highRateTax)), font));

			cellItemNm.setHorizontalAlignment(2);
			highRateTaxCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(highRateTaxCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//8%対象（税抜）表示
			cellItemNm = new PdfPCell(new Paragraph("＜8％対象(税抜き)＞　", font));
			PdfPCell lowRateSlipPriceSumCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(lowRateSlipPriceSum)), font));

			cellItemNm.setHorizontalAlignment(2);
			lowRateSlipPriceSumCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(lowRateSlipPriceSumCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//8%対象消費税表示
			cellItemNm = new PdfPCell(new Paragraph("＜8％対象消費税＞　", font));
			PdfPCell lowRateTaxCell = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(lowRateTax)), font));

			cellItemNm.setHorizontalAlignment(2);
			lowRateTaxCell.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(lowRateTaxCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;

			//請求金額表示
			cellItemNm = new PdfPCell(new Paragraph("＜請求金額＞　", font));
			PdfPCell CurrentBillAmount = new PdfPCell(
					new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(billDto.getBillAmount())), font));
			cellItemNm.setHorizontalAlignment(2);
			CurrentBillAmount.setHorizontalAlignment(2);

			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(CurrentBillAmount);
			if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
				document.newPage();
				yPos = PAGE_HEIGHT - 20;
				repaginationRow = totalRowNum;
				pageNumber++;
			}
			totalRowNum++;
		}

		//一番下までテーブルを埋める
//		for (int i = rowNum; i < maxRow - 2; i++) {
//
//			pdfPTable.addCell(blankCell);
//			pdfPTable.addCell(blankCell);
//			pdfPTable.addCell(blankCell);
//			pdfPTable.addCell(blankCell);
//			pdfPTable.addCell(blankCell);
//			pdfPTable.addCell(blankCell);
//			pdfPTable.addCell(blankCell);
//			rowNum++;
//		}

		//最下段行
		PdfPCell blankSpanCell = new PdfPCell(new Paragraph(" ", font));
		blankSpanCell.setColspan(3);

		PdfPCell cellSum = new PdfPCell(new Paragraph("合計", font));
		PdfPCell cellSumClaimPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(billDto.getSumClaimPrice())), font));

		blankSpanCell.setPaddingTop(7f);
		cellSum.setPaddingTop(7f);
		cellSum.setPadding(3f);
		cellSumClaimPrice.setPaddingTop(7f);
		cellSumClaimPrice.setPadding(3f);

		cellSum.setHorizontalAlignment(1);
		cellSumClaimPrice.setHorizontalAlignment(2);

		pdfPTable.addCell(blankSpanCell);
		pdfPTable.addCell(cellSum);
		pdfPTable.addCell(cellSumClaimPrice);

		if (isNewPageForOrderItemTable(pdfPTable, document, writer, yPos, pageNumber, repaginationRow, totalRowNum, orderCurrentHeight)) {
			document.newPage();
			yPos = PAGE_HEIGHT - 20;
			repaginationRow = totalRowNum;
			pageNumber++;
		}
		totalRowNum++;

		//テーブル描画
//		pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, MAX_ROW_WITH_HEADER, 30, orderCurrentHeight,
//				writer.getDirectContent());
//		if (pageNum > 1) {
//			for (int i = 1; i < pageNum; i++) {
//				document.newPage();
//				int rowStart = MAX_ROW_WITH_HEADER + (MAX_ROW_NO_HEADER * (i - 1));
//				int rowEnd = MAX_ROW_WITH_HEADER + (MAX_ROW_NO_HEADER * i);
//				pdfPTable.writeSelectedRows(0, TABLE_COLS, rowStart, rowEnd, 30, 800,
//						writer.getDirectContent());
//			}
//		}

		/** 多分、計算式違う、下の計算から、テーブルを記述始めているyposから以下の値を引かないと欲しい値が算出されない。 */
//		float height = pdfPTable.calculateHeights() - pageHight;

		while (pdfPTable.calculateHeights() < orderCurrentHeight - 100 + (pageNumber - 1) * (PAGE_HEIGHT - 40)) {
			// blank cell
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);

			totalRowNum++;
		}
		pdfPTable.writeSelectedRows(0, 7, repaginationRow, totalRowNum, 30, yPos, writer.getDirectContent());
	}

	public void outPut(HttpServletResponse response, String filePath,
			String fname) throws IOException {

		OutputStream os = response.getOutputStream();

		try {

			// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
			byte[] sJis = fname.getBytes("Shift_JIS");
			fname = new String(sJis, "ISO8859_1");
			File fileOut = new File(fname);
			FileInputStream hFile = new FileInputStream(filePath);
			BufferedInputStream bis = new BufferedInputStream(hFile);

			// レスポンス設定
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ fileOut.getName() + "\"");

			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}

			bis.close();
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		} finally {

			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				} finally {
					os = null;
				}
			}
		}
	}

	public static void showTextArea(Document document, PdfWriter writer,
			BaseFont baseFont, String texts, String splWord, float posX, float posY, float posYInc) {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		String[] textAry = texts.split(splWord);

		for (String text : textAry) {
			// 表示位置の設定
			pdfContentByte.setTextMatrix(posX, posY);

			// 表示する文字列の設定
			pdfContentByte.showText(text);

			posY -= posYInc;
		}
	}

	/**
	 * 法人の検索条件をもとにシステム業販請求書IDリストを取得
	 * @param sysCorporationId  法人ID
	 * @return システム業販請求書IDリスト
	 */
	public List<CorporateBillDTO> getSysCorporateBillIdList(long sysCorporationId)
			throws DaoException {

		return new CorporateBillDAO().getSearchSysCorporateBillIdList(sysCorporationId);
	}

	/**
	 * 法人の検索条件をもとにシステム業販請求書IDリストを取得
	 * @param sysCorporationId  法人ID
	 * @return システム業販請求書IDリスト
	 */
	public List<CorporateBillDTO> getSysCorporateBillIdList(long sysCorporationId, int dispCutoffDate)
			throws DaoException {

		return new CorporateBillDAO().getSearchSysCorporateBillIdList(sysCorporationId, dispCutoffDate);
	}

	/**
	 * システムIDをもとにシステム業販請求書IDリストを取得
	 * @param sysCorporationId  法人ID
	 * @return システム業販請求書IDリスト
	 */
	public ExportCorporateBillDTO getSearchedCorporateBill(long sysCorporateBillId)
			throws DaoException {

		return new CorporateBillDAO().getSearchCorporateBill(sysCorporateBillId);
	}

	/**
	 * 法人の検索条件をもとにシステム業販請求書IDリストを取得
	 * @param sysCorporationId  法人ID
	 * @return システム業販請求書IDリスト
	 */
	public List<CorporateBillItemDTO> getSearchedCorporateBillItemList(long sysCorporateBillId)
			throws DaoException {

		return new CorporateBillDAO().getSearchCorporateBillItemList(sysCorporateBillId);
	}

	/**
	 * 指定されたの業販請求書を取得する。
	 * @param 業販請求書一覧リスト
	 * @param 出力対象のリストインデックス
	 * @return 業販請求書
	 */
	public ExtendCorporateBillDTO getExportCorporateBill(List<CorporateBillDTO> sysCorporateBillIdList, int listIdx)
			throws DaoException {

		CorporateBillDAO dao = new CorporateBillDAO();

		// 業販請求書を取得
		ExtendCorporateBillDTO dto = dao
				.getSearchCorporateBill(sysCorporateBillIdList.get(listIdx).getSysCorporateBillId());

		return dto;
	}

	/**
	 * 指定されたの業販請求書の商品情報リストを取得する。
	 * @param 業販請求書一覧リスト
	 * @param 出力対象のリストインデックス
	 * @return 業販請求書の商品情報リスト
	 */
	public List<CorporateBillItemDTO> getExportCorporateBillItemList(List<CorporateBillDTO> sysCorporateBillIdList,
			int listIdx)
			throws DaoException {

		return new CorporateBillDAO()
				.getSearchCorporateBillItemList(sysCorporateBillIdList.get(listIdx).getSysCorporateBillId());
	}

	/**
	 * システム業販請求書IDリストから指定されたページの業販請求書一覧リストを取得する。<br>
	 * [20171204追記ｍSuda]<br>
	 * 入金管理機能実装に伴い、請求書の入金情報は入金管理情報TBLと連動させる。<br>
	 * 請求額、入金日、入金額、各フリーワード、各金額は入金管理情報TBLから取得、無い場合はそのまま請求書情報TBLから取得
	 * @param sysCorporationId  法人ID
	 * @param pageIdx 指定ページ
	 * @param pageMax 1ページの最大表示件数
	 * @return List<CorporateBillDTO> 業販請求書リスト
	 */
	public List<ExtendCorporateBillDTO> getCorporateBillList(List<CorporateBillDTO> sysCorporationIdList, int pageIdx,
			int pageMax)
			throws DaoException {

		List<ExtendCorporateBillDTO> corporateBillList = new ArrayList<>();

		CorporatePaymentManagementDAO payManageDAO = new CorporatePaymentManagementDAO();
		ClientDAO clientDAO = new ClientDAO();
		CorporateBillDAO dao = new CorporateBillDAO();
		AccountDAO accountDao = new AccountDAO();
		MstAccountDTO accountDto = new MstAccountDTO();

		// 指定されたページに表示する分のレコードをシステム業販商品IDリストをもとに
		// 1件ずつDBから取得する。
		for (int i = pageMax
				* pageIdx; i < pageMax * (pageIdx + 1)
						&& i < sysCorporationIdList.size(); i++) {

			ExtendCorporateBillDTO dto = dao
					.getSearchCorporateBill(sysCorporationIdList.get(i).getSysCorporateBillId());
			accountDto = accountDao.getAccount(dto.getSysAccountId());
			dto.setAccountNm(accountDto.getAccountNm());
			//締日コードをもとに締日を入力
			dto.setClientCutoffDateNm(WebConst.CUTOFF_DATE_MAP.get(String.valueOf(dto.getClientCutoffDate())));

			corporateBillList.add(dto);

		}

		return corporateBillList;
	}

	/**
	 * 業販請求書をDBから削除する。
	 *
	 * @param 業販請求書一覧リスト
	 * @param 削除対象インデックス
	 */
	public void deleteCorporateBill(List<ExtendCorporateBillDTO> corporateBillList, int index) throws DaoException {

		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();

		// 業販請求書一覧リストからインデックス指定で取り出し、
		// 業販請求書の削除フラグをアップデート
		corporateBillDAO.deleteCorporateBill(corporateBillList.get(index));

		// 業販請求書一覧リストからインデックス指定で取り出し、
		// 業販請求書商品の削除フラグをアップデート
		corporateBillDAO.deleteCorporateBillItem(corporateBillList.get(index));

	}

	/**
	 * 業販請求書一覧リストのフリーワード・入金日・入金額・手数料をDBに反映する。
	 *
	 * @param 業販請求書一覧リスト
	 */
	public void updateCorporateBillList(List<ExtendCorporateBillDTO> corporateBillList)
			throws ParseException, DaoException {

		TransactionDAO transactionDAO = new TransactionDAO();

		AccountDAO accountDAO = new AccountDAO();
		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
		DateFormat formatPreMonth = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		ExtendCorporateBillDTO dto = new ExtendCorporateBillDTO();
		ExtendCorporateBillDTO preMonthDTO = new ExtendCorporateBillDTO();
		long sysAccountId = 0;

		// 業販請求書一覧リストから1件ずつ取り出しアップデート
		for (int i = 0; i < corporateBillList.size(); i++) {

			// リストの末尾から業販請求書を取得
			dto = corporateBillList.get(corporateBillList.size() - 1 - i);

			// 請求月の前月を算出
			cal.setTime(format.parse(dto.getDemandMonth() + "/01"));
			cal.add(Calendar.MONTH, -1);

			//開始
			transactionDAO.begin();

			/** 前月の業販請求書を取得 */
			//更新対象請求書の口座IDが無い場合：0（商品が0件）
			if (dto.getSysAccountId() == CODE_COMPARISON_ZERO) {
				//会社コードから優先表示が高いものを1件取得
				sysAccountId = accountDAO.getAccountList(dto.getSysCorporationId()).get(INDEX_ZERO).getSysAccountId();
				//口座IDを元に前月請求額を取得
				preMonthDTO = corporateBillDAO.getSearchCorporateBill(dto.getClientBillingNm(),
						formatPreMonth.format(cal.getTime()), sysAccountId);
				//口座ID指定で取得できない場合、会社コード、会社名、前回請求月で取得する
				if (preMonthDTO != null) {
					if (preMonthDTO.getBillAmount() == AMOUNT_ZERO_SET) {
						preMonthDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(dto.getSysCorporationId(),
								formatPreMonth.format(cal.getTime()), dto.getClientBillingNm());
						// 前月請求金額を設定
						if (preMonthDTO == null) {
							dto.setPreMonthBillAmount(AMOUNT_ZERO_SET);
						} else {
							dto.setPreMonthBillAmount(preMonthDTO.getBillAmount());
						}
					} else {
						dto.setPreMonthBillAmount(preMonthDTO.getBillAmount());
					}
				} else {
					dto.setPreMonthBillAmount(AMOUNT_ZERO_SET);
				}
			} else {
				sysAccountId = dto.getSysAccountId();
				//口座IDを元に前月請求額を取得
				preMonthDTO = corporateBillDAO.getSearchCorporateBill(dto.getClientBillingNm(),
						formatPreMonth.format(cal.getTime()), sysAccountId);
				if (preMonthDTO != null) {
					if (preMonthDTO.getBillAmount() == AMOUNT_ZERO_SET) {
						preMonthDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(dto.getSysCorporationId(),
								formatPreMonth.format(cal.getTime()), dto.getClientBillingNm());

						if (preMonthDTO == null || (preMonthDTO.getSysAccountId() != sysAccountId
								&& preMonthDTO.getSysAccountId() != 0)) {
							dto.setPreMonthBillAmount(AMOUNT_ZERO_SET);
						} else {
							dto.setPreMonthBillAmount(preMonthDTO.getBillAmount());
						}
						// 前月請求金額を設定
						//					if(preMonthDTO == null){
						//						dto.setPreMonthBillAmount(AMOUNT_ZERO_SET);
						//					} else {
						//						dto.setPreMonthBillAmount(preMonthDTO.getBillAmount());
						//					}
					} else {
						dto.setPreMonthBillAmount(preMonthDTO.getBillAmount());
					}
				} else {
					dto.setPreMonthBillAmount(AMOUNT_ZERO_SET);
				}
			}

			//			//口座ID指定で取得できない場合は口座ID指定なしで再度取得する
			//			if (preMonthDTO == null) {
			//				preMonthDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(dto.getSysCorporationId(), formatPreMonth.format(cal.getTime()), dto.getClientBillingNm());
			//			} else {
			//				preMonthDTO.setBillAmount((preMonthDTO.getBillAmount() * taxRate / 100) + preMonthDTO.getBillAmount());
			//			}
			//
			//			// 前月請求金額を設定
			//			if(preMonthDTO == null){
			//				dto.setPreMonthBillAmount(0);
			//			} else {
			//				dto.setPreMonthBillAmount(preMonthDTO.getBillAmount());
			//			}

			// 繰越金額の設定
			dto.setCarryOverAmount(dto.getPreMonthBillAmount()
					- (dto.getCharge() + dto.getReceivePrice() + dto.getCharge2() + dto.getCharge3()));

			// 請求金額の設定
			dto.setBillAmount(dto.getSumClaimPrice() + dto.getCarryOverAmount());

			// 業販請求書を更新
			corporateBillDAO.updateCorporateBill(dto);

			//入金管理TBLの方も編集する
			ExtendPaymentManagementDTO payManageDTO = new ExtendPaymentManagementDTO();
			CorporatePaymentManagementService payManageService = new CorporatePaymentManagementService();
			ClientDAO clientDAO = new ClientDAO();
			MstClientDTO client = new MstClientDTO();
			client = clientDAO.getClient(dto.getClientBillingNm());

			payManageDTO = payManageService.getPaymentManagement(dto.getSysCorporationId(), client.getSysClientId(),
					dto.getSysAccountId(), dto.getDemandMonth());

			if (payManageDTO != null) {
				payManageDTO.setBillAmount(dto.getBillAmount());
				payManageDTO.setReceivePrice(dto.getReceivePrice());
				payManageDTO.setReceiveDate(dto.getReceiveDate());
				payManageDTO.setFreeWord(dto.getFreeWord());
				payManageDTO.setCharge(dto.getCharge());
				payManageDTO.setFreeWord2(dto.getFreeWord2());
				payManageDTO.setCharge2(dto.getCharge2());
				payManageDTO.setFreeWord3(dto.getFreeWord3());
				payManageDTO.setCharge3(dto.getCharge3());

				payManageService.updatePaymentManagement(payManageDTO);
			}

			//登録成功
			transactionDAO.commit();
		}

	}

	/**
	 * 指定された法人の月単位での売上・業販情報を集計し、請求書情報をDBに登録
	 * @param Kind原価詳細情報
	 *
	 * @return エラー情報
	 */
	public ErrorDTO createCorporateBill(String exportMonth, long exportSysCorporationId, String selectedCutoff)
			throws Exception {

		ErrorDTO errDTO = new ErrorDTO();
		SaleDAO saleDAO = new SaleDAO();
		CorporateBillDAO billDao = new CorporateBillDAO();
		ExportCorporateBillDTO lastMonthBillDto = new ExportCorporateBillDTO();

		CorporateSaleDAO corpSaleDAO = new CorporateSaleDAO();
		List<ErrorMessageDTO> errorMessageList = new ArrayList<ErrorMessageDTO>();

		// 指定された法人の月単位で業販伝票から請求書商品情報を取得する
		List<ExtendCorporateSalesItemDTO> corpSaleItemList = new ArrayList<ExtendCorporateSalesItemDTO>();
		//次月、先月の文字列を求める
		//〆締日により請求月は調整済みなので月に関してのみ行う
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
		String targetDateStringPrevious = exportMonth + "/01";
		String targetDateStringNext = exportMonth + "/01";
		Date exportPresentMonthDatePrevious = dateFormat.parse(targetDateStringPrevious);
		Date exportPresentMonthDateNext = dateFormat.parse(targetDateStringNext);
		Calendar fromCalendar = Calendar.getInstance(Locale.JAPAN);
		Calendar toCalendar = Calendar.getInstance(Locale.JAPAN);
		fromCalendar.setTime(exportPresentMonthDatePrevious);
		fromCalendar.add(Calendar.MONTH, -1);
		toCalendar.setTime(exportPresentMonthDateNext);
		toCalendar.add(Calendar.MONTH, 1);
		String prevMonth = dateFormat.format(fromCalendar.getTime());
		String nextMonth = dateFormat.format(toCalendar.getTime());
		long sysAccountId = CODE_COMPARISON_ZERO;

		//指定した締日selectedCutoffにより追加するデータを変える
		switch (selectedCutoff) {
		//末日締め
		case "0":
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostCorporateBill(exportMonth, exportSysCorporationId)); // 売り上げデータ
			break;
		//締日
		//25日締(1)、20日締(2)は前月から当月
		case "1":
			String fromDate = prevMonth + "/26";
			String toDate = exportMonth + "/25";
			corpSaleItemList
					.addAll(corpSaleDAO.getCorpSaleCostCorporateBill(1, fromDate, toDate, exportSysCorporationId)); // 売り上げデータ
			break;
		case "2":
			fromDate = prevMonth + "/21";
			toDate = exportMonth + "/20";
			corpSaleItemList
					.addAll(corpSaleDAO.getCorpSaleCostCorporateBill(2, fromDate, toDate, exportSysCorporationId)); // 売り上げデータ
			break;
		//15日締(3)、10日締(4)、5日締め(5)は当月から次月
		case "3":
			fromDate = exportMonth + "/16";
			toDate = nextMonth + "/15";
			corpSaleItemList
					.addAll(corpSaleDAO.getCorpSaleCostCorporateBill(3, fromDate, toDate, exportSysCorporationId)); // 売り上げデータ
			break;
		case "4":
			fromDate = exportMonth + "/11";
			toDate = nextMonth + "/10";
			corpSaleItemList
					.addAll(corpSaleDAO.getCorpSaleCostCorporateBill(4, fromDate, toDate, exportSysCorporationId)); // 売り上げデータ
			break;
		case "5":
			fromDate = exportMonth + "/06";
			toDate = nextMonth + "/05";
			corpSaleItemList
					.addAll(corpSaleDAO.getCorpSaleCostCorporateBill(5, fromDate, toDate, exportSysCorporationId)); // 売り上げデータ
			break;
		default:
			break;
		}

		/**前回請求情報の取得：請求書比較用START**/
		//前回請求月
		DateFormat billFormat = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
		String billTargetDateStr = exportMonth + "/01";
		Date billExportMonthDate = billFormat.parse(billTargetDateStr);
		Calendar billCal = Calendar.getInstance(Locale.JAPAN);
		billCal.setTime(billExportMonthDate);
		billCal.add(Calendar.MONTH, -1);
		String billLastMonth = billFormat.format(billCal.getTime());
		//前回作成されている請求書を取得
		List<ExportCorporateBillDTO> corpBillDto = billDao.getLastCorpSaleBill(billLastMonth, exportSysCorporationId,
				selectedCutoff);
		/***前回請求情報の取得：請求書比較用END***/

		//該当請求書の請求先別リストを取得する
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		List<ExportCorporateBillDTO> corporateBillDispList = new ArrayList<ExportCorporateBillDTO>();
		//TODO	ここから改修開始、
		corporateBillDispList = corporateSaleDisplayService.getExportCorporateBill(exportSysCorporationId, exportMonth,
				corpSaleItemList, "円", selectedCutoff);

		//今回作成されるの請求書と、前月作成された請求書を比較し、今回作成の請求書に
		//「前回の請求書で請求金額が0以外かつ、業販伝票が存在しない」が含まれていない場合
		//今回作成する請求書の最後に追加する
		//下記ループは今回作成する請求書に含まれている場合、データが重複しないよう、除外する処理
		for (int i = 0; i < corpBillDto.size(); i++) {
			for (int j = 0; j < corporateBillDispList.size(); j++) {
				ClientService clientService = new ClientService();
				ExtendMstClientDTO client = clientService.getDispClient(corporateBillDispList.get(j).getSysClientId());
				if (corpBillDto.get(i).getSysAccountId() == corporateBillDispList.get(j).getSysAccountId()
						&& corpBillDto.get(i).getClientBillingNm().equals(client.getClientNm())) {
					corpBillDto.remove(i);

					if (corpBillDto.size() <= i) {
						break;
					}
				}
			}
		}
		//「前回の請求書で請求金額が0以外かつ、業販伝票が存在しない」ものを、今回繰り越しあり、として、請求書を作成する
		if (corpBillDto.size() > 0) {
			for (ExportCorporateBillDTO exportBill : corpBillDto) {
				corporateBillDispList.add(exportBill);
			}
		}

		// リストが0件の場合、エラー
		if (corporateBillDispList.size() == 0) {
			ErrorMessageDTO errMessDTO = new ErrorMessageDTO();
			errMessDTO.setErrorMessage("請求対象となる伝票がありません");
			errMessDTO.setSuccess(false);
			errorMessageList.add(errMessDTO);
		}
		// エラーが発生している場合は、処理を終了
		if (errorMessageList.size() > 0) {
			errDTO.setErrorMessageList(errorMessageList);
			errDTO.setSuccess(false);
			return errDTO;
		}

		CorporationService corporationService = new CorporationService();
		CorporateBillDTO corporateBillDTO = new CorporateBillDTO();
		SequenceDAO seqDAO = new SequenceDAO();
		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();
		AccountDAO accountDAO = new AccountDAO();
		CorporatePaymentManagementDAO payManageDAO = new CorporatePaymentManagementDAO();
		ExtendPaymentManagementDTO payManageDTO = new ExtendPaymentManagementDTO();

		//以下、得意先ごとに登録する
		for (ExportCorporateBillDTO billListDTO : corporateBillDispList) {
			ClientService clientService = new ClientService();

			// 法人情報取得
			MstCorporationDTO corpDTO = corporationService.getCorporation(exportSysCorporationId);
			// 請求書情報を設定
			// システム法人間請求書ID（連番・一意）
			long sysCorporateBillId = seqDAO.getMaxSysCorporateBillId() + 1;
			corporateBillDTO.setSysCorporateBillId(sysCorporateBillId);
			// システム法人ID,請求月
			corporateBillDTO.setSysCorporationId(exportSysCorporationId);
			corporateBillDTO.setDemandMonth(exportMonth);

			//口座ID
			corporateBillDTO.setSysAccountId(billListDTO.getSysAccountId());

			//請求先情報
			ExtendMstClientDTO client = clientService.getDispClient(billListDTO.getSysClientId());
			//得意先が削除されている場合があるので、得意先が取得できなかった場合は請求書を作成しない。
			//得意先が消えることは通常ないようなのでコメントアウト。一応処理は残しておきます。
//			if (client == null) {
//				continue;
//			}
			corporateBillDTO.setClientBillingNm(client.getClientNm());

			//締日
			corporateBillDTO.setClientCutoffDate(client.getCutoffDate());

			// 請求月内番号
			String MaxNo = corporateBillDAO.getSearchMaxDemandMonthNo(exportSysCorporationId, exportMonth);

			if (StringUtils.isEmpty(MaxNo)) {
				corporateBillDTO.setDemandMonthNo("001");
			} else {
				int iMaxNo = Integer.parseInt(MaxNo) + 1;
				corporateBillDTO.setDemandMonthNo(String.format("%03d", iMaxNo));
			}

			// 請求書番号
			corporateBillDTO
					.setCorporateBillNo(WebConst.SYS_CORPORATION_CODE_MAP.get(String.valueOf(exportSysCorporationId))
							+ StringUtil.slashDelete(exportMonth) + corporateBillDTO.getDemandMonthNo());
			// 請求データ作成日
			corporateBillDTO.setBilldataCreateDate(StringUtil.getToday());
			// 取引先法人名
			if (exportSysCorporationId == 1 || exportSysCorporationId == 2 || exportSysCorporationId == 4) {
				corpDTO.setCorporationNm("株式会社" + corpDTO.getCorporationNm());
				// sysCorporationId ==  20 はmst_Corporationテーブルに未定義なので、ここで設定している。
				// 請求書に載る名称を決め打ちで設定
			} else if (exportSysCorporationId == 20) {
				corpDTO = new MstCorporationDTO();
				corpDTO.setCorporationNm("株式会社日本中央貿易");
			} else if (exportSysCorporationId == 12) {
				corpDTO.setCorporationNm("株式会社カインドテクノストラクチャー　Brembo事業部");
			}
			corporateBillDTO.setClientCorporationNm(corpDTO.getCorporationNm());
			if (exportSysCorporationId == 20) {
				// 取引先電話番号
				corporateBillDTO.setClientTelNo("");
				// 取引先FAX番号
				corporateBillDTO.setClientFaxNo("");
			} else {
				// 取引先電話番号
				corporateBillDTO.setClientTelNo(corpDTO.getTelNo());
				// 取引先FAX番号
				corporateBillDTO.setClientFaxNo(corpDTO.getFaxNo());
			}

			//重複チェック（DB上にすでに存在するデータがあれば登録作業をスキップ）
			List<ExportCorporateBillDTO> checkList = new ArrayList<ExportCorporateBillDTO>();
			sysAccountId = billListDTO.getSysAccountId();
			if (sysAccountId == 0) {
				sysAccountId = accountDAO.getAccountList(exportSysCorporationId).get(INDEX_ZERO).getSysAccountId();
			}
			checkList = corporateBillDAO.checkCorporateBill(corporateBillDTO, sysAccountId);
			if (checkList.size() != CODE_COMPARISON_ZERO) {
				continue;
			}

			// 業販請求書の個別商品テーブルに登録
			//請求ごとにアイテムを抽出
			for (ExtendCorporateSalesItemDTO itemDTO : billListDTO.getItemList()) {
				// システム業販請求書商品ID
				itemDTO.setSysCorporateBillItemId(seqDAO.getMaxSysCorporateBillItemId() + 1);
				itemDTO.setSysCorporateBillId(sysCorporateBillId);
				itemDTO.setCorporateSalesFlg("1");
				// 業販請求書商品テーブルに登録
				corporateBillDAO.registryCorporateBillItem(itemDTO);
			}

			//法人、得意先、口座、請求月を条件に入金管理情報を取得
			payManageDTO = payManageDAO.getPaymentManagement(exportSysCorporationId, client.getSysClientId(),
					sysAccountId, exportMonth);

			// 前月請求金額
			//先月の文字列を求める
			//締日により請求月は調整済みなので月に関してのみ行う
			DateFormat format = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
			String targetDateStr = exportMonth + "/01";
			Date exportMonthDate = format.parse(targetDateStr);
			Calendar cal = Calendar.getInstance(Locale.JAPAN);
			cal.setTime(exportMonthDate);
			cal.add(Calendar.MONTH, -1);
			String lastMonth = format.format(cal.getTime());

			//			//先月の最終的な請求額をテーブルから持ってくる
			//				lastMonthBillDto = billDao.getSearchCorporateBill(client.getClientNm(), lastMonth, billListDTO.getSysAccountId());
			//				if (lastMonthBillDto!=null) {
			//					corporateBillDTO.setPreMonthBillAmount((lastMonthBillDto.getBillAmount() * taxRate / 100) + lastMonthBillDto.getBillAmount() );
			//				//取得できなかった場合は0円扱い
			//				} else {
			//					corporateBillDTO.setPreMonthBillAmount(0);
			//				}

			ExtendCorporateBillDTO preMonthDTO = new ExtendCorporateBillDTO();

			/** 前月の業販請求書を取得 */
			//更新対象請求書の口座IDが無い場合：0（商品が0件）
			if (billListDTO.getSysAccountId() == CODE_COMPARISON_ZERO) {
				//会社コードから優先表示が高いものを1件取得
				sysAccountId = accountDAO.getAccountList(exportSysCorporationId).get(INDEX_ZERO).getSysAccountId();
				//口座IDを元に前月請求額を取得
				preMonthDTO = corporateBillDAO.getSearchCorporateBill(client.getClientNm(), lastMonth, sysAccountId);
				if (preMonthDTO != null) {
					//口座ID指定で取得できない場合、会社コード、会社名、前回請求月で取得する
					if (preMonthDTO.getBillAmount() == AMOUNT_ZERO_SET) {
						preMonthDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(exportSysCorporationId,
								lastMonth, client.getClientNm());
						// 前月請求金額を設定
						if (preMonthDTO == null) {
							corporateBillDTO.setPreMonthBillAmount(AMOUNT_ZERO_SET);
						} else {
							corporateBillDTO.setPreMonthBillAmount(preMonthDTO.getBillAmount());
						}
					} else {
						corporateBillDTO.setPreMonthBillAmount(preMonthDTO.getBillAmount());
					}
				} else {
					corporateBillDTO.setPreMonthBillAmount(AMOUNT_ZERO_SET);
				}
			} else {
				sysAccountId = billListDTO.getSysAccountId();
				//口座IDを元に前月請求額を取得
				preMonthDTO = corporateBillDAO.getSearchCorporateBill(client.getClientNm(), lastMonth, sysAccountId);
				if (preMonthDTO != null) {
					if (preMonthDTO.getBillAmount() == AMOUNT_ZERO_SET) {
						preMonthDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(exportSysCorporationId,
								lastMonth, client.getClientNm());
						if (preMonthDTO != null) {
							if (preMonthDTO.getSysAccountId() != sysAccountId && preMonthDTO.getSysAccountId() != 0) {
								corporateBillDTO.setPreMonthBillAmount(AMOUNT_ZERO_SET);
							} else {
								corporateBillDTO.setPreMonthBillAmount(preMonthDTO.getBillAmount());
							}
						} else {
							corporateBillDTO.setPreMonthBillAmount(AMOUNT_ZERO_SET);
						}
					} else {
						corporateBillDTO.setPreMonthBillAmount(preMonthDTO.getBillAmount());
					}
				} else {
					corporateBillDTO.setPreMonthBillAmount(AMOUNT_ZERO_SET);
				}
			}

			// フリーワード(空欄)/商品単価小計/消費税/合計請求金額/繰越金額/請求金額
			corporateBillDTO.setFreeWord("");

			//入金管理情報があるときは各フリーワード、各金額が入力されている場合もあるため格納
			if (payManageDTO != null) {
				corporateBillDTO.setFreeWord(payManageDTO.getFreeWord());
				corporateBillDTO.setCharge(payManageDTO.getCharge());
				corporateBillDTO.setFreeWord2(payManageDTO.getFreeWord2());
				corporateBillDTO.setCharge2(payManageDTO.getCharge2());
				corporateBillDTO.setFreeWord3(payManageDTO.getFreeWord3());
				corporateBillDTO.setCharge3(payManageDTO.getCharge3());
				//入金額と入金日を請求書に反映する
				corporateBillDTO.setReceiveDate(payManageDTO.getReceiveDate());
				corporateBillDTO.setReceivePrice(payManageDTO.getReceivePrice());
				//入金管理が無いとき、関係のない請求書にも入金額が反映されてしまうのでここで空を設定
			} else {
				corporateBillDTO.setFreeWord("");
				corporateBillDTO.setCharge(0);
				corporateBillDTO.setFreeWord2("");
				corporateBillDTO.setCharge2(0);
				corporateBillDTO.setFreeWord3("");
				corporateBillDTO.setCharge3(0);
				//入金額を請求書に反映する
				corporateBillDTO.setReceiveDate("");
				corporateBillDTO.setReceivePrice(0);
			}

			corporateBillDTO.setSumPieceRate(corporateBillDAO.getSearchCalcSumPriceRate(sysCorporateBillId));

			//****************消費税計算************************************************
			//税計算は「請求単位の合計額に課税」ではなく「請求を構成する各伝票の合計額に課税したものの合計」

			//伝票番号格納用
			String slipNo = "";
			int taxRate = 0;

			//指定月に購入がなく、繰越金額だけあった場合、伝票番号が存在しないため、ここで判定
			if (!billListDTO.getItemList().isEmpty()) {
				//伝票番号を格納
				slipNo = billListDTO.getItemList().get(0).getOrderNo();

				//消費税率の初期値は一番最初に処理される伝票の税率
				for (int i = 0; i < billListDTO.getSlipList().size(); i++) {
					if (slipNo.equals(billListDTO.getSlipList().get(i).getOrderNo())) {
						taxRate = billListDTO.getSlipList().get(i).getTaxRate();
						break;
					}
				}
			}

			int slipPriceSum = 0;
			int consumTaxSum = 0;

			for (ExtendCorporateSalesItemDTO itemDTO : billListDTO.getItemList()) {

				//伝票番号が同じものなら加算
				if (slipNo.equals(itemDTO.getOrderNo())) {
					//額の加算をしてから税計算を行う
					if (StringUtils.equals(itemDTO.getItemCode(), "77")) {
					} else {
						slipPriceSum += itemDTO.getPieceRate() * itemDTO.getOrderNum();
					}

				} else {
					// 伝票番号の切り替わりを確認したら税計算＆リセット
					//税だけ加算
					consumTaxSum += (int) ((double) slipPriceSum * taxRate / 100);
					slipPriceSum = 0;

					//ループ判別用に伝票番号を格納
					slipNo = itemDTO.getOrderNo();
					//伝票が変わったら消費税率も変更する。
					for (int i = 0; i < billListDTO.getSlipList().size(); i++) {
						if (slipNo.equals(billListDTO.getSlipList().get(i).getOrderNo())) {
							taxRate = billListDTO.getSlipList().get(i).getTaxRate();
							break;
						}
					}
					//額の加算
					if (StringUtils.equals(itemDTO.getItemCode(), "77")) {
					} else {
						slipPriceSum += itemDTO.getPieceRate() * itemDTO.getOrderNum();
					}
				}
			}
			//最終伝票用税計算
			//TODO 税率によって税率の計算を分ける。
			consumTaxSum += (int) ((double) slipPriceSum * taxRate / 100);

			//****************************************************************

			corporateBillDTO.setConsumptionTax(consumTaxSum);
			corporateBillDTO.setSumClaimPrice(corporateBillDTO.getSumPieceRate() + consumTaxSum);

			/*
			 * 繰越金額：当月請求した分は、次月に入金されるという時間のズレがある。
			 * そのため、（前月から当月への繰越金額）＝（前月の請求金額）ー（当月の入金）で算出している。
			 * （当月の請求金額）ー（当月の入金）ではない点に注意。
			 */
			corporateBillDTO
					.setCarryOverAmount(corporateBillDTO.getPreMonthBillAmount() - (corporateBillDTO.getReceivePrice()
							+ corporateBillDTO.getCharge()
							+ corporateBillDTO.getCharge2()
							+ corporateBillDTO.getCharge3()));
			corporateBillDTO.setBillAmount(corporateBillDTO.getSumClaimPrice() + corporateBillDTO.getCarryOverAmount());

			// 請求書テーブルに登録（重複チェックフラグ付き）
			corporateBillDAO.registryCorporateBill(corporateBillDTO);

			/*
			 * 請求書作成時に、繰越金額があれば次月の入金管理を作成する処理。
			 */

			/*
			 * 繰越金額
			 *  当月請求した分は、次月に入金されるという時間のズレがある。
			 *  そのため（当月から次月への繰越金額）＝（当月の請求額）- (次月の入金情報：そもそも次月の入金管理の作成前なのでまだ存在しない)　で算出している。
			 *  次月の入金情報は、そもそも次月の入金管理を作成前なのでまだ存在しない。ゆえに（当月から次月への繰越金額）＝（当月の請求額）。
			 * （当月の請求金額）ー（当月の入金）ではない点に注意。
			 */
			int nextCarryOverAmount = corporateBillDTO.getBillAmount();

			if (nextCarryOverAmount != 0) {
				CorporatePaymentManagementService payManageService = new CorporatePaymentManagementService();

				//DTOに請求書情報を詰める
				ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = new ExtendCorporateSalesSlipDTO();

				corporateSalesSlipDTO.setSysClientId(client.getSysClientId());
				corporateSalesSlipDTO.setSysCorporationId(exportSysCorporationId);
				corporateSalesSlipDTO.setSysAccountId(billListDTO.getSysAccountId());

				payManageService.registerPaymentInformation(corporateSalesSlipDTO, nextMonth, nextCarryOverAmount);
			}
		} //for文の終了：for(ExportCorporateBillDTO billListDTO : corporateBillDispList){

		errDTO.setSuccess(true);
		return errDTO;
	}

	protected int getTax(long sumPieceRate, String taxClass, String orderDate) throws ParseException {

		if (sumPieceRate == 0) {
			return 0;
		}

		Date taxUpDate8 = new Date();
		Date taxUpDate10 = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		taxUpDate8 = dateFormat.parse(WebConst.TAX_UP_DATE_8);
		taxUpDate10 = dateFormat.parse(WebConst.TAX_UP_DATE_10);

		int taxRate = 0;
		if (DateUtil.parseYYYYMMDDDate(orderDate).compareTo(taxUpDate8) < 0) {
			taxRate = WebConst.TAX_RATE_5;
		} else if (DateUtil.parseYYYYMMDDDate(orderDate).compareTo(taxUpDate10) < 0) {
			taxRate = WebConst.TAX_RATE_8;
		} else {
			taxRate = WebConst.TAX_RATE_10;
		}

		//saleDetail.jspのtaxCalcにも同じロジックがあり
		//ここの税計算ロジックを変更した場合、同じ仕様に変更する必要あり
		long tax = 0;
		//内税の場合
		if (StringUtils.equals(WebConst.TAX_CODE_INCLUDE, taxClass)) {

			tax = sumPieceRate - (int) Math.floor((sumPieceRate / (double) (100 + taxRate)) * 100);

			//外税の場合
		} else if (StringUtils.equals(WebConst.TAX_CODE_EXCLUSIVE, taxClass)) {

			tax = (int) Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
		}

		return (int) tax;
	}

	/**
	 * 指定された法人・請求月の請求書情報がDBに存在するか確認
	 * 存在する場合、作成を行わない。
	 * @param Kind原価詳細情報
	 *
	 * @return エラー情報
	 */
	public ErrorDTO checkCorporateBill(String exportMonth, long exportSysCorporationId, String clientBillingNm)
			throws ParseException, NumberFormatException, DaoException {
		ErrorDTO errDTO = new ErrorDTO();
		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();

		ExtendCorporateBillDTO dto = corporateBillDAO.getSearchDemandMonthCorporateBill(exportSysCorporationId,
				exportMonth, clientBillingNm);

		if (dto != null) {
			errDTO.setSuccess(false);
			errDTO.setErrorMessage("該当の売上月の請求書が存在します。削除してから、再度作成してください。");
		}

		return errDTO;

	}

	/**
	 * 請求書作成画面で指定された検索条件から請求書情報を取得する。
	 * <br/>
	 * 画面上の検索ボタンが押下されると呼び出されるメソッドで、
	 * ・請求月FROM～請求月TO
	 * ・請求書番号
	 * ・法人
	 * ・得意先番号
	 * ・得意先名
	 * ・合計請求金額FROM～合計請求金額TO
	 * ・並び替え
	 * ・表示件数
	 * を条件に条件に一致した請求書情報を取得する。
	 *
	 * @author gkikuchi
	 * @since 2017/11/13
	 * @param dispSysCorporationId 法人ID
	 * @param searchExportMonthFrom 請求月FROM
	 * @param searchExportMonthTo 請求月TO
	 * @return 請求書一覧
	 * @throws DaoException
	 */
	public List<CorporateBillDTO> getSysCorporateBillIdList(CorporateSaleSearchDTO csSearchDto) throws DaoException {

		// 検索条件に一致する請求書一覧を取得する。
		return new CorporateBillDAO().getSearchCorporateBillItemList(csSearchDto);
	}

	/**
	 * 業販請求書一括出力時に選択した業販請求書IDに紐づく請求書情報を取得する。
	 * <br/>
	 * 業販請求書IDをを基に請求書情報を取得する。
	 *
	 * @author gkikuchi
	 * @since 2017/11/21
	 * @exception DBからレコードの取得失敗
	 * @param 選択した業販請求書ID
	 * @return LinkedHashMap 請求書情報
	 */
	public Map<String, ExportCorporateBillDTO> getSearchedCorporateBill(long[] sysCorporateBillIdArry)
			throws Exception {

		/*
		 * 請求書作成画面から複数の業販請求書IDが送られてくる。
		 * 後ほど請求書と紐づく商品情報と請求書情報を業販請求書IDでリンクさせるために
		 * Mapで業販請求書IDと請求書情報を紐づける。
		 */
		Map<String, ExportCorporateBillDTO> ecbDtoMap = new LinkedHashMap<String, ExportCorporateBillDTO>();

		CorporateBillDAO cbDao = new CorporateBillDAO();

		for (long sysCorporateBillId : sysCorporateBillIdArry) {
			// 商品情報とリンクさせるために業販請求書IDと請求書情報を紐づける。
			ecbDtoMap.put(Long.toString(sysCorporateBillId), cbDao.getSearchCorporateBill(sysCorporateBillId));
		}

		return ecbDtoMap;
	}

	/**
	 * 業販請求書一括出力時に選択した業販請求書IDに紐づく商品情報を取得する。
	 * <br/>
	 * 業販請求書IDに紐づく商品情報を取得する。
	 *
	 * @author gkikuchi
	 * @since 2017/11/21
	 * @exception DBからレコードの取得失敗
	 * @param 選択した業販請求書ID
	 * @return LinkedHashMap 請求書情報
	 */
	public Map<String, List<CorporateBillItemDTO>> getSearchedCorporateBillItemList(long[] sysCorporateBillIdArray)
			throws Exception {

		/*
		 * 請求書作成画面から複数の業販請求書IDが送られてくる。
		 * 商品情報と請求書情報を業販請求書IDでリンクさせるために
		 * Mapで業販請求書IDと商品情報を紐づける。
		 */
		Map<String, List<CorporateBillItemDTO>> billItemListMap = new LinkedHashMap<String, List<CorporateBillItemDTO>>();

		CorporateBillDAO cbDao = new CorporateBillDAO();

		for (long sysCorporateBillId : sysCorporateBillIdArray) {
			// 請求書情報とリンクさせるために業販請求書IDと商品情報を紐づける。
			billItemListMap.put(Long.toString(sysCorporateBillId),
					cbDao.getSearchCorporateBillItemList(sysCorporateBillId));
		}

		return billItemListMap;
	}

	/**
	 * 請求作成画面一括出力用の請求を書作成する。
	 * <br/>
	 * 複数の業販請求書を出力するためのメソッド、複数の業販請求書を一つのpdfファイルに作成する。
	 *
	 * @author gkikuchi
	 * @since 2017/11/21
	 * @exception pdf作成失敗
	 * @param response
	 * @param ecbDtoMap 業販請求書IDに紐づく請求書情報
	 * @param billItemListMap 業販請求書IDに紐づく商品情報
	 */
	public void billList(HttpServletResponse response, Map<String, ExportCorporateBillDTO> ecbDtoMap,
			Map<String, List<CorporateBillItemDTO>> billItemListMap) throws Exception {

		DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);

		/*
		 * 請求書情報と商品情報を紐づける業販請求書IDを請求書情報のMapから取得する。
		 */
		Set<String> set = new LinkedHashSet<String>();
		set = ecbDtoMap.keySet();

		String fname = "請求書" + ".pdf";

		// ファイル名に日本語を使うのでファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("bill.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		document.open();

		// 各業販請求書IDに紐づく請求書情報と商品情報をPdfに書き込む。
		for (String key : set) {
			ExportCorporateBillDTO ecbDto = ecbDtoMap.get(key);

			String billMonth = ecbDto.getDemandMonth();
			String targetDateStr = billMonth + "/01";
			Date date = format.parse(targetDateStr);

			//商品がなければスキップ
			//while(billItemList.size() == 0) {}
			long accountId = 0;
			//口座ID比較用変数をリストの1件目の口座情報で初期化
			accountId = ecbDto.getSysAccountId();

			/** 見積書 */
			billHeader(document, writer, baseFont, date, ecbDto);
			bill(document, writer, font, baseFont, date, ecbDto, accountId);

			/** 商品一覧 */
			orderItemDetailNew(document, writer, font, baseFont, ecbDto, billItemListMap.get(key), 595);

			// 改ページ
			document.newPage();

		}

		document.close();
	}

	/**
	 * 全角文字は２桁、半角文字は１桁として文字数をカウントする
	 * @param str 対象文字列
	 * @return 文字数
	 */
	private static int getHan1Zen2(String str) {

		//戻り値
		int ret = 0;

		if (StringUtils.isNotEmpty(str)) {
			//全角半角判定
			char[] c = str.toCharArray();
			for (int i = 0; i < c.length; i++) {

				if ((c[i] <= '\u007e') || // 英数字
						(c[i] == '\u00a5') || // \記号
						(c[i] == '\u203e') || // ~記号
						(c[i] >= '\uff61' && c[i] <= '\uff9f') // 半角カナ
				) {
					ret += 1; //半角文字なら＋１
				} else {
					ret += 2; //全角文字なら＋２
				}
			}
		}
		return ret;
	}

}
