package jp.co.kts.service.fileExport;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstAccountDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.AccountService;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;

public class ExportCorporateOrderAcceptanceService {

	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	// static int testrow = 40;
	public void orderAcceptance(HttpServletResponse response,
			ExtendCorporateSalesSlipDTO slipDto, String tax) throws Exception {

		Date date = new Date();

		String fname = "注文請書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("orderAcceptance.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		document.open();
		exportOrderAcceptance(document, writer, baseFont, font, date, slipDto, Integer.valueOf(tax));
		document.close();

		// return document;

	}

	private void exportOrderAcceptance(Document document, PdfWriter writer, BaseFont baseFont, Font font, Date date,
			ExtendCorporateSalesSlipDTO slipDto, int tax) throws Exception {

		/** 注文請書 */
		orderAcceptanceHeader(document, writer, baseFont, date, slipDto);
		orderAcceptance(document, writer, font, baseFont, slipDto);

		/** 商品一覧 */
		orderItemDetail(document, writer, font, baseFont, slipDto, 595, tax);

		ExportCorporateEstimateService service =
				new ExportCorporateEstimateService();
		/** 注文者情報の出力 */
		service.setClientInfo(
				 document
				, writer
				, baseFont
				, slipDto);

		/** 納入先情報の出力 */
		service.setDestinationInfo(
				 document
				, writer
				, baseFont
				, slipDto);
	}

	public void orderAcceptanceList(HttpServletResponse response,
			List<ExtendCorporateSalesSlipDTO> slipList, String tax) throws Exception {

		Date date = new Date();

		String fname = "注文請書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("orderAcceptance.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		document.open();

		for (ExtendCorporateSalesSlipDTO slipDto : slipList){

			exportOrderAcceptance(document, writer, baseFont, font, date, slipDto, tax == null ? 0 : Integer.valueOf(tax));
			// 改ページ
			document.newPage();
		}
		document.close();

		// return document;

	}

	private static void orderAcceptanceHeader(Document document, PdfWriter writer,
			BaseFont baseFont, Date date, ExtendCorporateSalesSlipDTO slipDto) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 16);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(60, 807);

		// 表示する文字列の設定
		pdfContentByte.showText("注文請書");


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(290, 795);

		// 表示する文字列の設定
		SimpleDateFormat orderAcceptanceDate = new SimpleDateFormat("yyyy年MM月dd日", Locale.JAPAN);
		pdfContentByte.showText("受注日：" + orderAcceptanceDate.format(date));

		// 表示位置の設定
		pdfContentByte.setTextMatrix(440, 795);

		// 表示する文字列の設定
		pdfContentByte.showText("伝票No：" + slipDto.getOrderNo());

		// テキストの終了
		pdfContentByte.endText();
	}

	private static void orderAcceptance(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto)
			throws Exception {
		PdfContentByte pdfContentByte = writer.getDirectContent();

		/**
		 * ---------------------------------------------------注文者情報START--------
		 * ---------------------------------------------------------
		 */
		//注文者データ
		ClientService clientService = new ClientService();
		ExtendMstClientDTO client = clientService.getDispClient(slipDto.getSysClientId());

		//口座データ
		AccountService accountService = new AccountService();
		ExtendMstAccountDTO account = accountService.getAccount(slipDto.getSysAccountId());

		//法人データ
		CorporationService corpService = new CorporationService();
		MstCorporationDTO corporation = corpService.getCorporation(slipDto.getSysCorporationId());

		// 595
		// int pageWidth = (int)document.getPageSize().getWidth();
		// 842
		Integer pageHeight = (int)document.getPageSize().getHeight();
//		float posY = pageHeight;


		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);

		//【左上】請求先名に紐付く得意先情報表示、なければテーブル由来の情報
		String clientString;
		if (!client.getClientNm().isEmpty()) {
			clientString = client.getClientNm();
			if (StringUtils.isNotEmpty(client.getContactPersonNm())) {
				if (StringUtils.isNotEmpty(client.getQuarter())
					|| StringUtils.isNotEmpty(client.getPosition())){
					clientString += "," + client.getQuarter() + client.getPosition();
				}
				clientString += "," + client.getContactPersonNm() + "様";
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
			clientString = client.getClientNm();
			clientString += ",TEL：" + client.getTel();
			clientString += ",FAX：" + client.getFax();
		}

		showTextArea(document, writer, baseFont,clientString, ",", 35, 776, 14);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 666);

		// 表示する文字列の設定
		pdfContentByte.showText("下記の通り御注文承ります。");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 16);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 610);

		// 表示する文字列の設定
		if (StringUtils.equals(slipDto.getCurrency(), "2")) {
			pdfContentByte.showText("御注文金額　＄"
					+ StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumClaimPrice())) + "─");
		} else {
			pdfContentByte.showText("御注文金額　\u00a5"
					+ StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumClaimPrice())) + "─");
		}


		//【右上】法人情報設定
		String corporationString = "";
		corporationString += "〒" + corporation.getZip() + "," + corporation.getAddress();
		corporationString += "," + corporation.getCorporationFullNm();
		corporationString += ",TEL：" + corporation.getTelNo() + "　FAX：" + corporation.getFaxNo();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);

		showTextArea(document, writer, baseFont,corporationString, ",", 290, 765, 13);

		/************口座情報START*************/
		account = accountService.getAccount(slipDto.getSysAccountId());
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(290, 713);
		// 表示する文字列の設定
		pdfContentByte.showText("振込先：" + account.getBankNm() + " "+ account.getBranchNm());
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(290, 699);
		// 表示する文字列の設定
		pdfContentByte.showText(account.getAccountTypeNm() + " " + account.getAccountNumber() + " " + account.getAccountHolder());
		/*************口座情報END**************/

		/*************注意文言START**************/
		/*
		 * 注文請書では、Brembo事業部の場合のみ注意文言を表示させる
		 * 他の帳票（見積書・請求書）も同様。
		 */
		if(corporation.getSysCorporationId() == 12) {
			// フォントとサイズの設定
			pdfContentByte.setFontAndSize(baseFont, 11);
			// 表示位置の設定
			pdfContentByte.setTextMatrix(290, 676);
			// 表示する文字列の設定
			// 注意文言：振込みの際の手数料は御社負担でお願い致します。
			pdfContentByte.showText(WebConst.BEAR_TRANSFER_FEE);
		}

		 /*************注意文言END**************/

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(385, 660);

		// 表示する文字列の設定
		pdfContentByte.showText("担当者：" + slipDto.getPersonInCharge());

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);

		int estimateRemarksX = 30;
		//A4サイズの縦のサイズ
		int pageSizeY = (int)document.getPageSize().getHeight();

		//見積備考の位置
		//数値を大きくする場合　下へ移動
		//数値を小さくする場合　上へ移動
		int esitimateRemarksY = 550;

		int paddingTop = 12;
		int paddingLeft = 10;
		//備考表示(表示位置を中段に変更）
		showTextArea(document, writer, baseFont,
				  slipDto.getOrderFixRemarks()
				, "\\r\\n"
				, estimateRemarksX + paddingLeft
				//テキストの場合は下が基準になる
				, pageSizeY - esitimateRemarksY - paddingTop
				, 11);

		// テキストの終了
		pdfContentByte.endText();

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawLine(35, pageHeight - 605, 315, pageHeight - 605);
		pdfGraphics2D.dispose();

		int stampWidth = 50;
		int stampHeight = 50;
		int stampX = 415;
		//印鑑欄
		pdfGraphics2D.drawRect(stampX, pageHeight - 655, stampWidth, stampHeight);
		stampX += stampWidth;
		pdfGraphics2D.drawRect(stampX, pageHeight - 655, stampWidth, stampHeight);
		stampX += stampWidth;
		pdfGraphics2D.drawRect(stampX, pageHeight - 655, stampWidth, stampHeight);

		//備考欄(表示位置を中段に変更）
		pdfGraphics2D.drawRect(
				  estimateRemarksX
				//グラフの場合は上が基準になる
				, esitimateRemarksY
				, 535
				, 200);
	}



	private static void orderItemDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto,
			float orderCurrentHeight, int tax) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		List<ExtendCorporateSalesItemDTO> itemList = corporateSaleDisplayService.getCorporateSalesItemList(
					slipDto.getSysCorporateSalesSlipId(), slipDto.getSysCorporationId());

		int TABLE_COLS = 5;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		int width[] = { 60, 345, 40, 35, 55};
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellItemCdHeader = new PdfPCell(new Paragraph("商品コード", font));
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		PdfPCell cellUnitPriceHeader = new PdfPCell(new Paragraph("単価", font));
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("金額", font));

//		cellItemNmHeader.setColspan(2);

		// 表の要素(列タイトル)を作成
		cellItemCdHeader.setHorizontalAlignment(2);
		cellItemNmHeader.setHorizontalAlignment(1);
		cellUnitPriceHeader.setHorizontalAlignment(1);
		cellQuantityHeader.setHorizontalAlignment(1);
		cellPriceHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellItemCdHeader);
		pdfPTable.addCell(cellItemNmHeader);
		pdfPTable.addCell(cellUnitPriceHeader);
		pdfPTable.addCell(cellQuantityHeader);
		pdfPTable.addCell(cellPriceHeader);

		/**
		 * ループ(商品LISTのDTOをループさせる予定)
		 */
//		int maxRow = 33;
		int maxRow = 20;
		int rowNum = 0;

		ItemService itemService = new ItemService();
		String itemCd;

		for (rowNum = 0; rowNum < itemList.size(); rowNum++) {
			long itemId = itemList.get(rowNum).getSysItemId();
			if (itemId != 0) {
				itemCd = itemService.getMstItemDTO(itemId).getItemCode();
			} else {
				itemCd = itemList.get(rowNum).getItemCode();
			}

			//商品コード
			PdfPCell cellItemCd = new PdfPCell(new Paragraph(itemCd, font));
			// 商品名
			PdfPCell cellItemNm = new PdfPCell(new Paragraph("　" + itemList.get(rowNum).getItemNm(), font));
			// 数量
			PdfPCell cellQuantity = new PdfPCell(new Paragraph(
					String.valueOf(itemList.get(rowNum)
							.getOrderNum()), font));
			// 単価
			PdfPCell cellUnitPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(rowNum).getPieceRate())), font));
			// 金額
			PdfPCell cellPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(rowNum).getPieceRate()
							* itemList.get(rowNum).getOrderNum())), font));

			cellItemCd.setHorizontalAlignment(2);
			cellUnitPrice.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);
			cellPrice.setHorizontalAlignment(2);


			pdfPTable.addCell(cellItemCd);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellUnitPrice);
			pdfPTable.addCell(cellQuantity);
			pdfPTable.addCell(cellPrice);
		}

		//空白セル定義
		PdfPCell blankCell = new PdfPCell(new Paragraph(" ", font));

		//消費税を表示
		//空白行
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		rowNum++;

		//税抜き合計金額
		PdfPCell cellItemNm =  new PdfPCell(new Paragraph("＜税抜合計金額＞　" ,font));
		PdfPCell sumPriceRateCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate())), font));
		cellItemNm.setHorizontalAlignment(2);
		sumPriceRateCell.setHorizontalAlignment(2);

		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(sumPriceRateCell);
		rowNum++;

		//消費税表示
		cellItemNm =  new PdfPCell(new Paragraph("＜ 消 費 税 ＞　" ,font));
		PdfPCell taxCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() > 0 ? slipDto.getSumPieceRate() / WebConst.TAX_RATE_10 : 0)), font));

		cellItemNm.setHorizontalAlignment(2);
		taxCell.setHorizontalAlignment(2);

		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(taxCell);
		rowNum++;


		//一番下までテーブルを埋める
		for (int i = rowNum; i < maxRow; i++) {
			if ( pdfPTable.calculateHeights() > 260 ) { break; }
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
		}

		//最下段行
		PdfPCell blankSpanCell = new PdfPCell(new Paragraph(" ", font));
		blankSpanCell.setColspan(3);

		PdfPCell cellSum = new PdfPCell(new Paragraph("合計", font));
		PdfPCell cellSumClaimPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() + (slipDto.getSumPieceRate() > 0 ? slipDto.getSumPieceRate() / WebConst.TAX_RATE_10 : 0))), font));

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

		//テーブル描画
		pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow +2, 30, orderCurrentHeight,
		writer.getDirectContent());


	}


	public void outPut(HttpServletResponse response, String filePath,
			String fname) throws Exception {

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
			BaseFont baseFont,String texts, String splWord, float posX, float posY, float posYInc) throws Exception {

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
}