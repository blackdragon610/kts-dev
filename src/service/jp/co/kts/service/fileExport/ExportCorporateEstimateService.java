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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import jp.co.kts.service.common.Address;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.AccountService;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;

public class ExportCorporateEstimateService {

	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	/**
	 * 見積書出力初期設定
	 * @param response
	 * @param slipDto
	 * @throws Exception
	 */
	public void estimate(HttpServletResponse response,
			ExtendCorporateSalesSlipDTO slipDto, String tax) throws Exception {

		Date date = new Date();

		String fname = "御見積書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("estimate.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), (float)8.5);

		document.open();

		exportEstimate(document, writer, baseFont, font, date, slipDto, Integer.valueOf(tax));

		document.close();
	}

	int INFO_Y = 70;
	/**
	 * 見積書を作成します<br><br>
	 * 仕様変更追記(2017/09/07)<br>
	 * 商品テーブルが備考欄と被るときに改ページをして新しいページに備考欄以下を表示します。<br>
	 * その際、表示する商品が残っている場合はそのページの最後まで商品テーブルを表示します。
	 * @param document
	 * @param writer
	 * @param baseFont
	 * @param font
	 * @param date
	 * @param slipDto
	 * @throws Exception
	 */
	private void exportEstimate(Document document, PdfWriter writer, BaseFont baseFont, Font font, Date date,
			ExtendCorporateSalesSlipDTO slipDto, int tax) throws Exception {

		/** 見積書 */
		estimateHeader(document, writer, baseFont, date, slipDto);
		estimate(document, writer, font, baseFont, slipDto);
		/** 商品一覧 */
		Map<String, Integer> itemTableMap = orderItemDetail(document, writer, font, baseFont, slipDto, 595, tax);

		if (itemTableMap.get("tableHeight") != 0) {

			//テーブル作成終了時のY地点
			int endPointY = 0;

			//テーブル作成判別フラグが1の限り、改ページをして商品テーブルを作成する
			while (itemTableMap.get("createTableFlg") != 0) {
				//改ページ
				document.newPage();
				//改ページ後の商品テーブル作成
				endPointY = orderItemDetailNewPage(document, writer, font, baseFont, slipDto, 800, itemTableMap, tax);
			}
			//備考欄、注文者情報、納入先情報を表示(商品テーブルのY地点に合わせるため、備考欄･注文者情報･納入先情報を一緒にした)
			estimateFooterNewPage(document, writer, font, baseFont, slipDto, endPointY, itemTableMap);
			return;
		}

		/** 備考欄 */
		estimateFooter(document, writer, font, baseFont, slipDto);

		/** 注文者情報の出力 */
		this.setClientInfo(
				 document
				, writer
				, baseFont
				, slipDto);

		/** 納入先情報の出力 */
		this.setDestinationInfo(
				 document
				, writer
				, baseFont
				, slipDto);
	}

	/**
	 * 業販一覧から見積書一覧を作成します
	 * @param response
	 * @param slipList
	 * @throws Exception
	 */
	public void estimateList(HttpServletResponse response,
			List<ExtendCorporateSalesSlipDTO> slipList, String tax) throws Exception {

		Date date = new Date();

		String fname = "御見積書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("estimate.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), (float)8.5);

		document.open();

		for (ExtendCorporateSalesSlipDTO slipDto : slipList) {

			exportEstimate(document, writer, baseFont, font, date, slipDto, tax == null ? 0 : Integer.valueOf(tax));
			// 改ページ
			document.newPage();
		}

		document.close();
	}

	/**
	 * 納入先情報出力
	 * @param document
	 * @param writer
	 * @param baseFont
	 * @param slipDto
	 * @throws Exception
	 */
	public void setDestinationInfo(Document document, PdfWriter writer, BaseFont baseFont,
			ExtendCorporateSalesSlipDTO slipDto) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();
		pdfContentByte.beginText();

		Address address = new Address();
		address.setZip(slipDto.getDestinationZip());
		address.setPrefectures(slipDto.getDestinationPrefectures());
		address.setMunicipality(slipDto.getDestinationMunicipality());
		address.setAddress(slipDto.getDestinationAddress());
		address.setBuildingNm(slipDto.getDestinationBuildingNm());

		List<String> strList = new ArrayList<String>();

		strList.add("■納入先情報");
		strList.add(slipDto.getDestinationNm());
		strList.add(address.getZipDisp());
		strList.add(address.getAddress1());
		strList.add(address.getAddress2());

		String text = new String();
		String sepalator = ",,";

		for (String str: strList) {

			if (StringUtils.isEmpty(str)) {
				continue;
			}

			if (StringUtils.isNotEmpty(text)) {
				text += sepalator;
			}

			text += str;
		}

		showTextArea(document, writer, baseFont,
				text, sepalator, 270, INFO_Y, 11);
		// テキストの終了
		pdfContentByte.endText();
	}

	/**
	 * 注文者情報出力
	 * @param document
	 * @param writer
	 * @param baseFont
	 * @param slipDto
	 * @throws Exception
	 */
	public void setClientInfo(Document document
			, PdfWriter writer
			, BaseFont baseFont
			, ExtendCorporateSalesSlipDTO slipDto) throws Exception {

		ClientService clientService = new ClientService();
		ExtendMstClientDTO clientDTO = new ExtendMstClientDTO();
		Address address = new Address();

		clientDTO = clientService.getDispClient(slipDto.getSysClientId());
		address.setZip(clientDTO.getZip());
		address.setPrefectures(clientDTO.getPrefectures());
		address.setAddress(clientDTO.getAddress());
		address.setMunicipality(clientDTO.getMunicipality());
		address.setBuildingNm(clientDTO.getBuildingNm());

		PdfContentByte pdfContentByte = writer.getDirectContent();
		pdfContentByte.beginText();
		List<String> strList = new ArrayList<String>();

		strList.add("■注文者情報");
		strList.add(clientDTO.getClientNm());
		strList.add(address.getZipDisp());
		strList.add(address.getAddress1());
		strList.add(address.getAddress2());

		String text = new String();
		String sepalator = ",,";

		for (String str: strList) {

			if (StringUtils.isEmpty(str)) {
				continue;
			}

			if (StringUtils.isNotEmpty(text)) {
				text += sepalator;
			}

			text += str;
		}

		showTextArea(document, writer, baseFont,
				text, sepalator, 35, INFO_Y, 11);
		// テキストの終了
		pdfContentByte.endText();
	}

	private static void estimateHeader(Document document, PdfWriter writer,
			BaseFont baseFont, Date date, ExtendCorporateSalesSlipDTO slipDto) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 16);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(60, 805);

		// 表示する文字列の設定
		pdfContentByte.showText("御見積書");


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(290, 795);

		// 表示する文字列の設定
		SimpleDateFormat estimateDate = new SimpleDateFormat("yyyy年MM月dd日", Locale.JAPAN);
		pdfContentByte.showText("見積日：" + estimateDate.format(date));

		// 表示位置の設定
		pdfContentByte.setTextMatrix(440, 795);

		// 表示する文字列の設定
		pdfContentByte.showText("伝票No：" + slipDto.getOrderNo());


		// テキストの終了
		pdfContentByte.endText();

		//PDF図形作成の開始
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		//842 A4ページの高さ(Y軸)
		Integer pageHeight = (int)document.getPageSize().getHeight();

		pdfGraphics2D.setColor(new Color(0, 0, 0));
		//「御見積合計金額」の下線を引く
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

	}

	/**
	 * PDF上部の注文者データ、口座データ、法人データを出力します
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @throws Exception
	 */
	private void estimate(Document document, PdfWriter writer,
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

		//注文者データ出力
		showTextArea(document, writer, baseFont,clientString, ",", 35, 773, 14);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 670);

		// 表示する文字列の設定
		pdfContentByte.showText("下記の通り御見積申し上げます。");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 16);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 610);

		// 表示する文字列の設定
		if (StringUtils.equals(slipDto.getCurrency(), "2")) {
			pdfContentByte.showText("御見積合計金額　＄"
					+ StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumClaimPrice())) + "─");
		} else {
			pdfContentByte.showText("御見積合計金額　\u00a5"
					+ StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumClaimPrice())) + "─");
		}

		//【右上】法人情報設定
		String corporationString = "";
		corporationString += "〒" + corporation.getZip() + "," + corporation.getAddress();
		corporationString += "," + corporation.getCorporationFullNm();
		corporationString += ",TEL：" + corporation.getTelNo() + "　FAX：" + corporation.getFaxNo();

		//口座情報
		if (account!=null){
			if (account.getBankNm()!=null) {
				corporationString += ",振込先：" + account.getBankNm() + "　" + account.getBranchNm();
				corporationString += "," + account.getAccountTypeNm() +"　" + account.getAccountNumber() +"　" + account.getAccountHolder();
			}
		}

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);

		//法人情報と口座情報を出力
		showTextArea(document, writer, baseFont,corporationString, ",", 290, 762, 13);

		/*************注意文言START**************/
		/*
		 * 見積書では、Brembo事業部の場合のみ注意文言を表示させる
		 * 他の帳票（注文請書・請求書）も同様。
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

		//テキストの終了
		pdfContentByte.endText();

	}

	/**
	 * 見積書の備考欄を表示します(改ページ無しver)
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @throws Exception
	 */
	private static void estimateFooter(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto)
					throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		//テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);

		int estimateRemarksX = 30;
		//A4サイズの縦のサイズ
		int pageSizeY = 842;

		//見積備考の位置
		//数値を大きくする場合　下へ移動
		//数値を小さくする場合　上へ移動
		int esitimateRemarksY = 550;

		int paddingTop = 12;
		int paddingLeft = 10;
		//見積書備考表示(表示位置を中段に変更）
		showTextArea(document, writer, baseFont,
				  slipDto.getEstimateRemarks()
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
		pdfGraphics2D.dispose();

		pdfGraphics2D.drawRect(
				  estimateRemarksX
				//グラフの場合は上が基準になる
				, esitimateRemarksY
				, 535
				, 200);

	}

	/**
	 * 見積書の備考欄、注文者情報、納入先情報を表示します(改ページ後に使用)
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @param endPointY
	 * @throws Exception
	 */
	public static void estimateFooterNewPage(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto, int endPointY, Map<String, Integer> map)
				throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		//テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);

		int estimateRemarksX = 30;
		//A4サイズの縦のサイズ
		int pageSizeY = 842;

		int paddingTop = 12;
		int paddingLeft = 10;

		//備考欄のみが改ページされたかを判別するフラグを取得
		int REMARKS_NEWPAGE_FLG = map.get("remarksNewPageFlg");

		//注文者情報と納入先情報を表示するY地点 (商品テーブル終了地点 - paddinTop * 3 - 備考欄枠の縦サイズ)
		int CLIENT_AND_DESTINATION_DISPLAY_Y_POINT = endPointY - 3 * paddingTop - 200 ;
		//備考欄以下のみが改ページされた時の注文者情報と納入先情報を表示するY地点
		int CLIENT_AND_DESTINATION_DISPLAY_Y_POINT_TOP = 800 - 2 * paddingTop - 200;

		/*------------------------------------ 備考欄の内容表示 開始 ------------------------------------- */

		//見積書備考欄表示 合計まで1ページ目に表示され、備考欄以下が改ページされるときもある
		if (REMARKS_NEWPAGE_FLG == 1)	 {

			//ページのTOPに備考欄が来た場合
			showTextArea(document, writer, baseFont,
					  slipDto.getEstimateRemarks()
					, "\\r\\n"
					, estimateRemarksX + paddingLeft
					//テキストの場合は下が基準になる
					, 800 - paddingTop
					, 11);

		} else {

			//商品テーブルに合わせて備考欄を表示する場合
			showTextArea(document, writer, baseFont,
					  slipDto.getEstimateRemarks()
					, "\\r\\n"
					, estimateRemarksX + paddingLeft
					//テキストの場合は下が基準になる
					, endPointY - paddingTop - paddingTop
					, 11);

		}

		/*------------------------------------ 備考欄の内容表示 終了 ------------------------------------- */

		/*------------------------------------     注文者情報 開始      ------------------------------------- */

		//注文者情報
		ClientService clientService = new ClientService();
		ExtendMstClientDTO clientDTO = new ExtendMstClientDTO();
		Address clientAddress = new Address();

		clientDTO = clientService.getDispClient(slipDto.getSysClientId());
		clientAddress.setZip(clientDTO.getZip());
		clientAddress.setPrefectures(clientDTO.getPrefectures());
		clientAddress.setAddress(clientDTO.getAddress());
		clientAddress.setMunicipality(clientDTO.getMunicipality());
		clientAddress.setBuildingNm(clientDTO.getBuildingNm());

		List<String> strClientInfoList = new ArrayList<String>();

		strClientInfoList.add("■注文者情報");
		strClientInfoList.add(clientDTO.getClientNm());
		strClientInfoList.add(clientAddress.getZipDisp());
		strClientInfoList.add(clientAddress.getAddress1());
		strClientInfoList.add(clientAddress.getAddress2());

		String clientText = new String();
		String clientSepalator = ",,";

		for (String str: strClientInfoList) {

			if (StringUtils.isEmpty(str)) {
				continue;
			}

			if (StringUtils.isNotEmpty(clientText)) {
				clientText += clientSepalator;
			}

			clientText += str;
		}

		//注文者情報表示 合計までが1ページ目に表示され、備考欄以下が改ページされるときもある
		if (REMARKS_NEWPAGE_FLG == 1) {

			//備考欄がページのTOPに来た場合
			showTextArea(document, writer, baseFont,
					clientText, clientSepalator, 35, CLIENT_AND_DESTINATION_DISPLAY_Y_POINT_TOP, 11);

		} else {

			//商品テーブルに合わせて備考欄を表示する場合
			showTextArea(document, writer, baseFont,
					clientText, clientSepalator, 35, CLIENT_AND_DESTINATION_DISPLAY_Y_POINT, 11);

		}

		/*------------------------------------     注文者情報 終了      ------------------------------------- */

		/*------------------------------------     納入先情報 開始      ------------------------------------- */

		//納入先情報
		Address destinationAddress = new Address();
		destinationAddress.setZip(slipDto.getDestinationZip());
		destinationAddress.setPrefectures(slipDto.getDestinationPrefectures());
		destinationAddress.setMunicipality(slipDto.getDestinationMunicipality());
		destinationAddress.setAddress(slipDto.getDestinationAddress());
		destinationAddress.setBuildingNm(slipDto.getDestinationBuildingNm());

		List<String> strDestinationInfoList = new ArrayList<String>();

		strDestinationInfoList.add("■納入先情報");
		strDestinationInfoList.add(slipDto.getDestinationNm());
		strDestinationInfoList.add(destinationAddress.getZipDisp());
		strDestinationInfoList.add(destinationAddress.getAddress1());
		strDestinationInfoList.add(destinationAddress.getAddress2());

		String destinationText = new String();
		String destinationSepalator = ",,";

		for (String str: strDestinationInfoList) {

			if (StringUtils.isEmpty(str)) {
				continue;
			}

			if (StringUtils.isNotEmpty(destinationText)) {
				destinationText += destinationSepalator;
			}

			destinationText += str;
		}

		//納入先情報表示 合計までが1ページ目に表示され、備考欄以下が改ページされるときもある
		if (REMARKS_NEWPAGE_FLG == 1) {

			//備考欄がページのTOPに来た場合
			showTextArea(document, writer, baseFont,
					destinationText, destinationSepalator, 270, CLIENT_AND_DESTINATION_DISPLAY_Y_POINT_TOP, 11);

		} else {

			//商品テーブルに合わせて備考欄を表示する場合
			showTextArea(document, writer, baseFont,
					destinationText, destinationSepalator, 270, CLIENT_AND_DESTINATION_DISPLAY_Y_POINT, 11);
		}

		/*------------------------------------     納入先情報 終了      ------------------------------------- */

		// テキストの終了
		pdfContentByte.endText();

		/*------------------------------------     備考欄枠出力 開始    ------------------------------------- */

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.dispose();

		//見積書備考欄の枠線表示 合計までが1ページ目に表示され、備考欄以下が改ページされるときもある
		if (REMARKS_NEWPAGE_FLG == 1) {

			//ページのTOPに備考欄が来た場合
			pdfGraphics2D.drawRect(
					estimateRemarksX
					//グラフの場合は上が基準になる
					, pageSizeY - 800
					, 535
					, 200);

		} else {

			//商品テーブルに合わせて備考欄を表示する場合
			pdfGraphics2D.drawRect(
					estimateRemarksX
					//グラフの場合は上が基準になる
					, pageSizeY - endPointY + paddingTop
					, 535
					, 200);

		}

		/*------------------------------------     備考欄枠出力 終了    ------------------------------------- */

	}

	/**
	 * 商品をテーブル形式で出力します
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @param orderCurrentHeight
	 * @return tableHeight
	 * @throws Exception
	 */
	private static Map<String, Integer> orderItemDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto,
			float orderCurrentHeight, int tax) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		List<ExtendCorporateSalesItemDTO> itemList = corporateSaleDisplayService.getCorporateSalesItemList(
					slipDto.getSysCorporateSalesSlipId(), slipDto.getSysCorporationId());

		// 表のカラム数、カラム幅を設定
		int TABLE_COLS = 5;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		int width[] = { 70, 345, 40, 25, 55};
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellItemCdHeader = new PdfPCell(new Paragraph("商品コード", font));
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		PdfPCell cellUnitPriceHeader = new PdfPCell(new Paragraph("単価", font));
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("金額", font));

		// 表の要素(列タイトル)を作成 (真ん中)
		cellItemCdHeader.setHorizontalAlignment(1);
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
		int maxRow = 20;
		int rowNum = 0;
		//1ページ目の商品テーブルを改行させる基準値
		int FIRSTPAGE_ITEMTABLE_STANDARDHEIGHT = 249;

		ItemService itemService = new ItemService();
		String itemCd;

		//商品テーブルを作成
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

			//表の要素を作成 (左寄せ)
			cellItemCd.setHorizontalAlignment(2);
			cellUnitPrice.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);
			cellPrice.setHorizontalAlignment(2);

			//表の要素に表を追加する
			pdfPTable.addCell(cellItemCd);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellUnitPrice);
			pdfPTable.addCell(cellQuantity);
			pdfPTable.addCell(cellPrice);

			//テーブルの高さを取得
			int tableHeight = (int) pdfPTable.getTotalHeight();

			//商品テーブルが改ページの基準値を超えたとき、1ページ目の最後まで商品テーブルを埋める
			if (tableHeight > FIRSTPAGE_ITEMTABLE_STANDARDHEIGHT) {

				//1ページ目での商品テーブルの高さの最大値
				int TABLE_MAX_HEIGHT = 515;
				//商品リストを進める
				rowNum++;

				//商品テーブルを最大の高さまで埋める
				for ( ; tableHeight < TABLE_MAX_HEIGHT; rowNum++) {

					//表示する商品がなくなった時抜ける
					if (rowNum >= itemList.size()) {
						break;
					}

					itemId = itemList.get(rowNum).getSysItemId();
					if (itemId != 0) {
						itemCd = itemService.getMstItemDTO(itemId).getItemCode();
					} else {
						itemCd = itemList.get(rowNum).getItemCode();
					}

					//商品コード
					cellItemCd = new PdfPCell(new Paragraph(itemCd, font));
					// 商品名
					 cellItemNm = new PdfPCell(new Paragraph("　" + itemList.get(rowNum).getItemNm(), font));
					// 数量
					cellQuantity = new PdfPCell(new Paragraph(
							String.valueOf(itemList.get(rowNum)
									.getOrderNum()), font));
					// 単価
					cellUnitPrice = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(rowNum).getPieceRate())), font));
					// 金額
					cellPrice = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(rowNum).getPieceRate()
									* itemList.get(rowNum).getOrderNum())), font));

					//表の要素を作成 (左寄せ)
					cellItemCd.setHorizontalAlignment(2);
					cellUnitPrice.setHorizontalAlignment(2);
					cellQuantity.setHorizontalAlignment(2);
					cellPrice.setHorizontalAlignment(2);

					//表の要素に表を追加する
					pdfPTable.addCell(cellItemCd);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(cellUnitPrice);
					pdfPTable.addCell(cellQuantity);
					pdfPTable.addCell(cellPrice);

					//テーブル描画の最大行を更新
					maxRow++;
					//テーブルの高さを更新
					tableHeight = (int)pdfPTable.getTotalHeight();
				}

				//もし合計金額などが入るようだったら合計金額などを1ページ目に表示する
				if (tableHeight < TABLE_MAX_HEIGHT) {
					//空白セル定義
					PdfPCell blankCell = new PdfPCell(new Paragraph(" ", font));

					//消費税を表示
					//空白行
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					maxRow++;

					//税抜き合計金額
					cellItemNm =  new PdfPCell(new Paragraph("＜税抜合計金額＞　" ,font));
					PdfPCell sumPriceRateCell = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate())), font));
					cellItemNm.setHorizontalAlignment(2);
					sumPriceRateCell.setHorizontalAlignment(2);

					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(sumPriceRateCell);
					maxRow++;

					//消費税表示
					cellItemNm =  new PdfPCell(new Paragraph("＜ 消 費 税 ＞　" ,font));
					PdfPCell taxCell = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() / WebConst.TAX_RATE_10)), font));

					cellItemNm.setHorizontalAlignment(2);
					taxCell.setHorizontalAlignment(2);

					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(taxCell);
					maxRow++;

					//最下段行
					PdfPCell blankSpanCell = new PdfPCell(new Paragraph(" ", font));
					blankSpanCell.setColspan(3);

					PdfPCell cellSum = new PdfPCell(new Paragraph("合計", font));
					PdfPCell cellSumClaimPrice = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() + (slipDto.getSumPieceRate() / WebConst.TAX_RATE_10))), font));

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
					maxRow++;

					Map<String, Integer> map = new HashMap<>();
					//テーブルの高さ、商品リストの番号、テーブル作成フラグ、備考欄改ページフラグをセット
					map.put("tableHeight", tableHeight);
					map.put("itemRow", rowNum + 1);
					map.put("createTableFlg", 0);
					map.put("remarksNewPageFlg", 1);

					//テーブルを描画
					pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow + 4, 30, orderCurrentHeight,
							writer.getDirectContent());

					//改ページ
					document.newPage();

					return map;

				}

				//テーブルの高さ、次から見る商品リストの番号、テーブル作成判別フラグ(0:終了、1:続行)、備考欄以下改ページフラグをセット
				Map<String, Integer> map = new HashMap<>();
				map.put("tableHeight", tableHeight);
				map.put("itemRow", rowNum);
				map.put("createTableFlg", 1);
				map.put("remarksNewPageFlg", 0);
				//テーブルを描画
				pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow + 3, 30, orderCurrentHeight,
						writer.getDirectContent());

				return map;
			}
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
				StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() / WebConst.TAX_RATE_10)), font));

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
				StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() + (slipDto.getSumPieceRate() / WebConst.TAX_RATE_10))), font));

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
		pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow +3, 30, orderCurrentHeight,
		writer.getDirectContent());

		//テーブルの高さをセット
		Map<String, Integer> map = new HashMap<>();
		map.put("tableHeight", 0);

		return map;
	}

	/**
	 * 改ページ先に商品テーブルを作成します
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @param orderCurrentHeight
	 * @param map
	 * @return endPointY
	 * @throws Exception
	 */
	public int orderItemDetailNewPage(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto,
			float orderCurrentHeight, Map<String, Integer> map, int tax) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		List<ExtendCorporateSalesItemDTO> itemList = corporateSaleDisplayService.getCorporateSalesItemList(
					slipDto.getSysCorporateSalesSlipId(), slipDto.getSysCorporationId());

		// 表のカラム数、カラム幅を設定
		int TABLE_COLS = 5;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		int width[] = { 70, 345, 40, 25, 55};
		pdfPTable.setWidths(width);

		ItemService itemService = new ItemService();
		String itemCd;

		int maxRow = 0;
		//改行するテーブルの高さの基準値
		int NEWPAGE_TABLE_STANDARDHEIGHT = 462;
		//商品を続きからテーブルに表示していく
		int itemNum = map.get("itemRow");

		//商品テーブルの作成
		for ( ; itemNum < itemList.size(); itemNum++) {

			long itemId = itemList.get(itemNum).getSysItemId();
			if (itemId != 0) {
				itemCd = itemService.getMstItemDTO(itemId).getItemCode();
			} else {
				itemCd = itemList.get(itemNum).getItemCode();
			}

			//商品コード
			PdfPCell cellItemCd = new PdfPCell(new Paragraph(itemCd, font));
			// 商品名
			PdfPCell cellItemNm = new PdfPCell(new Paragraph("　" + itemList.get(itemNum).getItemNm(), font));
			// 数量
			PdfPCell cellQuantity = new PdfPCell(new Paragraph(
					String.valueOf(itemList.get(itemNum)
							.getOrderNum()), font));
			// 単価
			PdfPCell cellUnitPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(itemNum).getPieceRate())), font));
			// 金額
			PdfPCell cellPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(itemNum).getPieceRate()
							* itemList.get(itemNum).getOrderNum())), font));

			cellItemCd.setHorizontalAlignment(2);
			cellUnitPrice.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);
			cellPrice.setHorizontalAlignment(2);


			pdfPTable.addCell(cellItemCd);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellUnitPrice);
			pdfPTable.addCell(cellQuantity);
			pdfPTable.addCell(cellPrice);

			maxRow++;

			//商品テーブルの高さを取得
			int tableHeight = (int)pdfPTable.getTotalHeight();

			//商品テーブルの高さが基準値まで達したらそのページで表示できる分だけ商品テーブルを表示する
			if(tableHeight > NEWPAGE_TABLE_STANDARDHEIGHT) {

				//改ページ後の商品テーブルの高さの最大値
				int TABLE_MAX_HEIGHT = 720;

				//商品リストを進める
				itemNum++;

				//商品テーブルを最大の高さまでうめる
				for ( ; tableHeight < TABLE_MAX_HEIGHT; itemNum++) {

					//表示する商品がなくなったら抜ける
					if (itemNum >= itemList.size()) {
						break;
					}

					itemId = itemList.get(itemNum).getSysItemId();
					if (itemId != 0) {
						itemCd = itemService.getMstItemDTO(itemId).getItemCode();
					} else {
						itemCd = itemList.get(itemNum).getItemCode();
					}

					//商品コード
					cellItemCd = new PdfPCell(new Paragraph(itemCd, font));
					// 商品名
					cellItemNm = new PdfPCell(new Paragraph("　" + itemList.get(itemNum).getItemNm(), font));
					// 数量
					 cellQuantity = new PdfPCell(new Paragraph(
							String.valueOf(itemList.get(itemNum)
									.getOrderNum()), font));
					// 単価
					cellUnitPrice = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(itemNum).getPieceRate())), font));
					// 金額
					cellPrice = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(itemNum).getPieceRate()
									* itemList.get(itemNum).getOrderNum())), font));

					cellItemCd.setHorizontalAlignment(2);
					cellUnitPrice.setHorizontalAlignment(2);
					cellQuantity.setHorizontalAlignment(2);
					cellPrice.setHorizontalAlignment(2);


					pdfPTable.addCell(cellItemCd);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(cellUnitPrice);
					pdfPTable.addCell(cellQuantity);
					pdfPTable.addCell(cellPrice);

					//最大行更新
					maxRow++;
					//テーブルの高さを更新
					tableHeight = (int)pdfPTable.getTotalHeight();
				}

				//もし合計金額などが入るようだったら合計金額をそのページに表示する
				if (tableHeight < TABLE_MAX_HEIGHT) {

					//空白セル定義
					PdfPCell blankCell = new PdfPCell(new Paragraph(" ", font));

					//消費税を表示
					//空白行
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);

					//税抜き合計金額
					cellItemNm =  new PdfPCell(new Paragraph("＜税抜合計金額＞　" ,font));
					PdfPCell sumPriceRateCell = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate())), font));
					cellItemNm.setHorizontalAlignment(2);
					sumPriceRateCell.setHorizontalAlignment(2);

					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(sumPriceRateCell);

					//消費税表示
					cellItemNm =  new PdfPCell(new Paragraph("＜ 消 費 税 ＞　" ,font));
					PdfPCell taxCell = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() / WebConst.TAX_RATE_10)), font));

					cellItemNm.setHorizontalAlignment(2);
					taxCell.setHorizontalAlignment(2);

					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(cellItemNm);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(taxCell);

					//最下段行
					PdfPCell blankSpanCell = new PdfPCell(new Paragraph(" ", font));
					blankSpanCell.setColspan(3);

					PdfPCell cellSum = new PdfPCell(new Paragraph("合計", font));
					PdfPCell cellSumClaimPrice = new PdfPCell(new Paragraph(
							StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumPieceRate() + (slipDto.getSumPieceRate() / WebConst.TAX_RATE_10))), font));

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

					//商品リストの番号をセット
					map.put("createTableFlg", 0);
					//合計金額は表示したので備考欄以下は改ページになる
					map.put("remarksNewPageFlg", 1);

					//テーブルを描画
					int endPointY = (int)pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow + 4, 30, orderCurrentHeight,
							writer.getDirectContent());

					//改ページ
					document.newPage();

					return endPointY;
				}

				//次から見る商品リストの番号をセット
				map.put("itemRow", itemNum);
				//テーブルを描画
				int endPointY = (int)pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow +3, 30, orderCurrentHeight,
						writer.getDirectContent());

				return endPointY;
			}
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
		maxRow++;

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
		maxRow++;

		//消費税表示
		cellItemNm =  new PdfPCell(new Paragraph("＜ 消 費 税 ＞　" ,font));
		PdfPCell taxCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(tax)), font));

		cellItemNm.setHorizontalAlignment(2);
		taxCell.setHorizontalAlignment(2);

		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(taxCell);
		maxRow++;

		//最下段行
		PdfPCell blankSpanCell = new PdfPCell(new Paragraph(" ", font));
		blankSpanCell.setColspan(3);

		PdfPCell cellSum = new PdfPCell(new Paragraph("合計", font));
		PdfPCell cellSumClaimPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getSumClaimPrice())), font));

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
		maxRow++;


		//テーブル描画
		int endPointY = (int)pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow + 3, 30, orderCurrentHeight,
														writer.getDirectContent());

		//商品テーブル作成を終了
		map.put("createTableFlg", 0);
		//範囲内に収まっているので備考欄以下は改ページしない
		map.put("remarksNewPageFlg", 0);

		return endPointY;
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

	/**
	 *
	 * @param document
	 * @param writer
	 * @param baseFont
	 * @param texts
	 * @param splWord
	 * @param posX X座標
	 * @param posY Y座標
	 * @param posYInc
	 * @throws Exception
	 */
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