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
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.output.entity.StoreDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;

public class ExportCorporatePickListService {

	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	// static int testrow = 40;
	public void pickList(HttpServletResponse response,
			List<ExtendCorporateSalesSlipDTO> salesSlipList,
			CorporateSaleSearchDTO corporateSaleSearchDTO, String pdfPattern) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		List<ExtendCorporateSalesSlipDTO> pickList = new ArrayList<>(salesSlipList);
		pickList = corporateSaleDisplayService.getPickItemList(pickList);

		Date date = new Date();

		String fname = "ピッキング＆納品書リスト" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");


		System.out.println("ExportCorporatePickListService.pickList: START -----------");
		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("pickList.pdf"));

		//フォントを明朝体に変更のためコメントアウト
//		BaseFont baseFont = BaseFont.createFont(
//				AsianFontMapper.JapaneseFont_Go,
//				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);
//
//		Font font = new Font(BaseFont.createFont(
//				AsianFontMapper.JapaneseFont_Go,
//				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		document.open();

		System.out.println("ExportCorporatePickListService.pickList: PdfWriter.open() -----------");
		
		Date dateFrom = DateUtil.toDate(corporateSaleSearchDTO.getScheduledLeavingDateFrom(), DateUtil.DATE_YYYYMMDD);
		Date dateTo = DateUtil.toDate(corporateSaleSearchDTO.getScheduledLeavingDateTo(), DateUtil.DATE_YYYYMMDD);
		for (ExtendCorporateSalesSlipDTO slipDto : pickList) {
			List<ExtendCorporateSalesItemDTO>pickItemList = new ArrayList<>();
			for (ExtendCorporateSalesItemDTO item: slipDto.getPickItemList()) {

				//「ピッキングリスト・納品書」ボタン押下の場合
				if (pdfPattern.equals("0")) {
					//ピッキング済みのものは対象外
					if (StringUtils.equals(item.getPickingListFlg(), "on")) {
						continue;
					}
				} else {
					//ピッキング済みのもの以外は対象外
					if (!StringUtils.equals(item.getPickingListFlg(), "on")) {
						continue;
					}
				}

				Date scheduledDate = DateUtil.toDate(item.getScheduledLeavingDate(), DateUtil.DATE_YYYYMMDD);

				if (scheduledDate == null) {
					continue;
				}

				//期間が指定されている場合は期間判定を行います
				if (scheduledDate != null
						&& dateFrom != null
						&& scheduledDate.compareTo(dateFrom) < 0) {
					continue;
				}
				//期間が指定されている場合は期間判定を行います
				if (scheduledDate != null
						&& dateTo != null
						&& scheduledDate.compareTo(dateTo) > 0) {
					continue;
				}

				pickItemList.add(item);
			}
			if (pickItemList.size() == 0) {
				continue;
			}
			
			System.out.println("ExportCorporatePickListService.pickList: pickItemList.size() > 0 -----------");
			slipDto.setPickItemList(pickItemList);

			//「ピッキングリスト・納品書」ボタン押下の場合
			if (pdfPattern.equals("0")) {

				//販売情報と納入先情報が同じ場合
				if (slipDto.getClientNm().equals(slipDto.getDestinationNm())) {


					System.out.println("ExportCorporatePickListService.pickList: Before pickHeader()-----------");

					/** ピッキングリスト */
					pickHeader(document, writer, baseFont, date);

					System.out.println("ExportCorporatePickListService.pickList: Before pickList()-----------");
					
					pickList(document, writer, font, baseFont, slipDto);
					document.newPage();

					/** 納品書 */
					//Aパターンで出力：金額情報あり
					float orderCurrentHeightA = 0;
					orderCurrentHeightA = fixedPhrases(document, writer, font, baseFont,
							slipDto, true);
					orderCurrentHeightA = orderDetail(document, writer, font, baseFont,
							slipDto, orderCurrentHeightA);
					orderItemDetailA(document, writer, font, baseFont, slipDto,
							orderCurrentHeightA, dateFrom, dateTo, true);
					// 改ページ
					document.newPage();

				//販売情報と納入先情報が違う場合
				} else {
					/** ピッキングリスト */
					pickHeader(document, writer, baseFont, date);
					pickList(document, writer, font, baseFont, slipDto);
					document.newPage();


					//Bパターンで出力：金額情報無し
					float orderCurrentHeightB = 0;
					orderCurrentHeightB = fixedPhrases(document, writer, font, baseFont,
							slipDto, true);
					orderCurrentHeightB = orderDetail(document, writer, font, baseFont,
							slipDto, orderCurrentHeightB);
					orderItemDetailB(document, writer, font, baseFont, slipDto,
							orderCurrentHeightB, dateFrom, dateTo);
					// 改ページ
					document.newPage();
				}

			//「納品書再発行」ボタン押下時
			} else {

				/** 納品書 */
				//Aパターンで出力：金額情報あり
				float orderCurrentHeightA = 0;
				orderCurrentHeightA = fixedPhrases(document, writer, font, baseFont,
						slipDto, false);
				orderCurrentHeightA = orderDetail(document, writer, font, baseFont,
						slipDto, orderCurrentHeightA);
				orderItemDetailA(document, writer, font, baseFont, slipDto,
						orderCurrentHeightA, dateFrom, dateTo, true);
				// 改ページ
				document.newPage();
			}
		}
		document.close();

	}

	private static void pickHeader(Document document, PdfWriter writer,
			BaseFont baseFont, Date date) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(210, 820);

		// 表示する文字列の設定
		pdfContentByte.showText("★★ピッキングリスト★★");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 8);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(465, 825);

		// 表示する文字列の設定
		pdfContentByte.showText(displyTimeFormat.format(date) + "　作成");

		// テキストの終了
		pdfContentByte.endText();

//		Font font = new Font(BaseFont.createFont(
//				AsianFontMapper.JapaneseFont_Go,
//				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 8);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 8);

		PdfPTable pdfPTable = new PdfPTable(2);

		PdfPCell cell1_1 = new PdfPCell(new Paragraph("開始", font));
		PdfPCell cell1_2 = new PdfPCell(new Paragraph("：", font));

		PdfPCell cell2_1 = new PdfPCell(new Paragraph("終了", font));
		PdfPCell cell2_2 = new PdfPCell(new Paragraph("：", font));

		PdfPCell cell3_1 = new PdfPCell(new Paragraph("担当", font));
		PdfPCell cell3_2 = new PdfPCell(new Paragraph("", font));

		/**
		 * ALIGN_LEFT 左詰め 0 ALIGN_CENTER 中央（左右） 1 ALIGN_RIGHT 右詰め 2
		 * ALIGN_JUSTIFIED 両端揃え 3 ALIGN_TOP 上詰め 4 ALIGN_MIDDLE 中央（上下） 5
		 * ALIGN_BOTTOM 下詰め 6 ALIGN_BASELINE ベースライン 7
		 */
		cell1_1.setHorizontalAlignment(1);
		cell1_2.setHorizontalAlignment(1);

		cell2_1.setHorizontalAlignment(1);
		cell2_2.setHorizontalAlignment(1);

		cell3_1.setHorizontalAlignment(1);
		cell3_2.setHorizontalAlignment(1);

		// 線消すメモ
		// cell1_1.setBorder(Rectangle.NO_BORDER);
		pdfPTable.addCell(cell1_1);

		pdfPTable.addCell(cell1_2);

		pdfPTable.addCell(cell2_1);
		pdfPTable.addCell(cell2_2);

		pdfPTable.addCell(cell3_1);
		pdfPTable.addCell(cell3_2);

		pdfPTable.setTotalWidth(80);
		int width[] = { 25, 55 };
		pdfPTable.setWidths(width);
		pdfPTable.writeSelectedRows(0, 3, 485, 820, writer.getDirectContent());

	}

	private static void pickList(Document document, PdfWriter writer,
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

		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 8);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(30, 800);

		// 表示する文字列の設定
		pdfContentByte.showText("■注文者");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 800);

		// 表示する文字列の設定
		pdfContentByte.showText("受注ルート");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, 800);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getCorporationNm());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 785);

		// 表示する文字列の設定
		pdfContentByte.showText("受注番号");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, 785);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getOrderNo());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 770);

		// 表示する文字列の設定
		pdfContentByte.showText("注文日時");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, 770);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getOrderDate());
//		+ " " + slipDto.getOrderTime());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(420, 770);

		// 表示する文字列の設定
		pdfContentByte.showText("支払方法");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(470, 770);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getPaymentMethodNm());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 755);

		// 表示する文字列の設定
		pdfContentByte.showText("注文者名");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, 755);

		// 表示する文字列の設定
		pdfContentByte.showText(client.getClientNm());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(420, 755);

		// 表示する文字列の設定
		pdfContentByte.showText("電話番号");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(470, 755);

		// 表示する文字列の設定
		pdfContentByte.showText(client.getTel());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 740);

		// 表示する文字列の設定
		pdfContentByte.showText("注文者住所");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, 740);

		// 表示する文字列の設定
		pdfContentByte.showText("〒" + client.getZip() + " "
				+ client.getPrefectures()
				+ client.getMunicipality() + client.getAddress()
				+ client.getBuildingNm());

		pdfContentByte.setTextMatrix(100, 725);

		// 表示する文字列の設定
		pdfContentByte.showText("メール");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, 725);

		// 表示する文字列の設定
		pdfContentByte.showText(client.getMailAddress());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(30, 710);

		// 表示する文字列の設定
		pdfContentByte.showText("備考欄：");

		String orderRemarks = slipDto.getOrderRemarks();

		orderRemarks = replaceNewline(orderRemarks);
		int yPos = 710;
		int newlineCount = 58;
		/**
		 * 暫定。継続条件に＝が入っているから58文字くらいの時、無駄に空行作られるかも。＝
		 * 外すと備考が空のときypos加算しないから同じとこに書かれるのかな。
		 */
		for (int strNum = 0; strNum <= orderRemarks.length();) {
			pdfContentByte.setTextMatrix(100, yPos);
			pdfContentByte.showText(StringUtils.substring(orderRemarks, strNum,
					strNum + newlineCount));

			strNum += newlineCount;
			yPos -= 10;
		}
		/**
		 * この辺暫定。とりあえず、２バイト文字と１バイト文字によって改行の桁数変わるから判断する。時間あるときAPI読んで全体的に作りかえる
		 */

		yPos -= 5;

		// テキストの終了
		pdfContentByte.endText();
		yPos += 5;

		int pageHeight = (int) document.getPageSize().getHeight();

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawLine(30, pageHeight - yPos, 565, pageHeight - yPos);
		pdfGraphics2D.dispose();
		/**
		 * ---------------------------------------------------お届け先START--------
		 * ---------------------------------------------------------
		 */
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 8);
		// 表示位置の設定
		yPos -= 15;
		pdfContentByte.setTextMatrix(30, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("■お届け先");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("お届け先名");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getDestinationNm() + "("
				+ slipDto.getDestinationNmKana() + ")" + "様");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(420, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("電話番号");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(470, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getDestinationTel());

		// 表示位置の設定
		yPos -= 15;
		pdfContentByte.setTextMatrix(100, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("お届け先住所");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("〒" + slipDto.getDestinationZip() + " "
				+ slipDto.getDestinationPrefectures()
				+ slipDto.getDestinationMunicipality()
				+ slipDto.getDestinationAddress()
				+ slipDto.getDestinationBuildingNm());

//		// 表示位置の設定
		yPos -= 15;

		// テキストの終了
		pdfContentByte.endText();

		yPos += 5;
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawLine(30, pageHeight - yPos, 565, pageHeight - yPos);
		pdfGraphics2D.dispose();

		/**
		 * ---------------------------------------------------伝票情報START--------
		 * ---------------------------------------------------------
		 */
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 8);
		// 表示位置の設定
		yPos -= 15;
		pdfContentByte.setTextMatrix(30, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("■伝票情報");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("運送会社");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getTransportCorporationSystem());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(420, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("配送指定日");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(470, yPos);

		// 表示する文字列の設定
		if (StringUtils.isNotBlank(slipDto.getDestinationAppointDate())) {
			pdfContentByte.showText(slipDto.getDestinationAppointDate());
		} else {
			pdfContentByte.showText("指定なし");
		}

		// 表示位置の設定
		yPos -= 15;
		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("送り状種別");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(170, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getInvoiceClassification());

		// 表示位置の設定
		pdfContentByte.setTextMatrix(420, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("時間帯指定");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(470, yPos);

		// 表示する文字列の設定
		if (StringUtils.equals(slipDto.getTransportCorporationSystem(), "佐川急便")) {
			pdfContentByte.showText(WebConst.APPOINT_TIME_EHIDEN_MAP.get(slipDto.getDestinationAppointTime()));
		} else if (StringUtils.equals(slipDto.getTransportCorporationSystem(), "ヤマト運輸")) {
			pdfContentByte.showText(WebConst.APPOINT_TIME_B2_MAP.get(slipDto.getDestinationAppointTime()));
		} else if (StringUtils.equals(slipDto.getTransportCorporationSystem(), "日本郵便")) {
			pdfContentByte.showText("指定なし");
		} else {
			pdfContentByte.showText("指定なし");
		}

		// 表示位置の設定
		yPos -= 15;
		pdfContentByte.setTextMatrix(30, yPos);

		// 表示する文字列の設定
		pdfContentByte.showText("備考：");

		String slipMemo = slipDto.getOrderRemarks();
		slipMemo = replaceNewline(slipMemo);
		for (int strNum = 0; strNum <= slipMemo.length();) {
			pdfContentByte.setTextMatrix(100, yPos);
			pdfContentByte.showText(StringUtils.substring(slipMemo, strNum,
					strNum + newlineCount));

			strNum += newlineCount;
			yPos -= 10;
		}

		// テキストの終了
		pdfContentByte.endText();

		yPos += 5;
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawLine(30, pageHeight - yPos, 565, pageHeight - yPos);
		pdfGraphics2D.dispose();

		PdfPTable pdfPTable = new PdfPTable(6);

		// 表の要素(列タイトル)を作成
		PdfPCell cell1_1 = new PdfPCell(new Paragraph("品番", font));
		cell1_1.setRowspan(2); // セルを2行分結合
		cell1_1.setGrayFill(0.8f); // セルを灰色に設定

		PdfPCell cell1_2 = new PdfPCell(new Paragraph("倉庫場所", font));
		cell1_2.setGrayFill(0.8f); // セルを灰色に設定

		PdfPCell cell1_3 = new PdfPCell(new Paragraph("ロケーションNo", font));
		cell1_3.setGrayFill(0.8f); // セルを灰色に設定

		// 表の要素(列タイトル)を作成
		PdfPCell cell1_4 = new PdfPCell(new Paragraph("個数", font));
		cell1_4.setRowspan(2); // セルを2行分結合
		cell1_4.setGrayFill(0.8f); // セルを灰色に設定

		PdfPCell cell1_5 = new PdfPCell(new Paragraph("バーコード", font));
		cell1_5.setRowspan(2); // セルを2行分結合
		cell1_5.setGrayFill(0.8f); // セルを灰色に設定

		// 表の要素(列タイトル)を作成
		PdfPCell cell1_6 = new PdfPCell(new Paragraph("チェック", font));
		cell1_6.setRowspan(2); // セルを2行分結合
		cell1_6.setGrayFill(0.8f); // セルを灰色に設定

		// 表の要素(列タイトル)を作成
		PdfPCell cell2_1 = new PdfPCell(new Paragraph("商品名", font));
		cell2_1.setColspan(2); // セルを2列分結合
		cell2_1.setGrayFill(0.8f); // セルを灰色に設定

		cell1_1.setHorizontalAlignment(1);
		cell1_2.setHorizontalAlignment(1);
		cell1_3.setHorizontalAlignment(1);
		cell1_4.setHorizontalAlignment(1);
		cell1_5.setHorizontalAlignment(1);
		cell1_6.setHorizontalAlignment(1);
		cell2_1.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cell1_1);
		pdfPTable.addCell(cell1_2);
		pdfPTable.addCell(cell1_3);
		pdfPTable.addCell(cell1_4);
		pdfPTable.addCell(cell1_5);
		pdfPTable.addCell(cell1_6);
		pdfPTable.addCell(cell2_1);

		yPos -= 15;
		pdfPTable.setTotalWidth(535);
		int width[] = { 70, 145, 100, 25, 150, 45 };
		pdfPTable.setWidths(width);

		int repaginationRow = 0;
		float pageHight = 0;
		int rowNum = 0;
		//総描画行数
		int totalRowNum = 1;
		int itemNum = 0;
		for (rowNum = 0; rowNum < slipDto.getPickItemList().size(); rowNum++) {

			//複数個の商品も1個につき1バーコードを出力するよう修正
			for (itemNum = 0; itemNum < slipDto.getPickItemList().get(rowNum).getOrderNum(); itemNum++) {

				// 表の要素を作成
				PdfPCell cell3_1 = new PdfPCell(new Paragraph(slipDto
						.getPickItemList().get(rowNum).getItemCode(), font));
				cell3_1.setRowspan(2); // セルを2行分結合

				// cell3_2, cell3_3がnullの場合,当該セルが表示されなくなる現象を修正
				if (slipDto.getPickItemList().get(rowNum).getWarehouseNm() == null) {
					slipDto.getPickItemList().get(rowNum).setWarehouseNm("　");
				}
				if (slipDto.getPickItemList().get(rowNum).getLocationNo() == null){
					slipDto.getPickItemList().get(rowNum).setLocationNo("　");
				}

				// 表の要素を作成
				PdfPCell cell3_2 = new PdfPCell(new Paragraph(slipDto
						.getPickItemList().get(rowNum).getWarehouseNm(), font));

				PdfPCell cell3_3 = new PdfPCell(new Paragraph(slipDto
						.getPickItemList().get(rowNum).getLocationNo(), font));

				PdfPCell cell3_4 = new PdfPCell(new Paragraph(
						String.valueOf(1), font));
				cell3_4.setRowspan(2); // セルを2行分結合
				// 4996740500084
				// 表の要素を作成

				com.itextpdf.text.Image image = null;
				image = makeBarcode(writer, slipDto.getPickItemList().get(rowNum)
						.getItemCode());

				PdfPCell cell3_5;
				if (image != null) {
					cell3_5 = new PdfPCell(image);
				} else {
					cell3_5 = new PdfPCell(new Paragraph("", font));
				}
				cell3_5.setRowspan(2); // セルを2行分結合

				PdfPCell cell3_6 = new PdfPCell(new Paragraph("", font));
				cell3_6.setRowspan(2); // セルを2行分結合

				PdfPCell cell4_1 = new PdfPCell(new Paragraph(slipDto
						.getPickItemList().get(rowNum).getItemNm(), font));
				cell4_1.setColspan(2); // セルを2列分結合

				cell3_1.setHorizontalAlignment(1);
				cell3_2.setHorizontalAlignment(1);
				cell3_3.setHorizontalAlignment(1);
				cell3_4.setHorizontalAlignment(1);
				cell3_5.setHorizontalAlignment(1);
				cell3_6.setHorizontalAlignment(1);
				cell4_1.setHorizontalAlignment(1);

				if (image != null) {
					cell3_1.setPaddingTop(10f);
					cell3_1.setPaddingBottom(5f);
					cell3_2.setPaddingTop(10f);
					cell3_2.setPaddingBottom(5f);
					cell3_3.setPaddingTop(10f);
					cell3_3.setPaddingBottom(5f);
					cell3_4.setPaddingTop(10f);
					cell3_4.setPaddingBottom(5f);
					cell3_5.setPaddingTop(10f);
					cell3_5.setPaddingBottom(5f);
					cell3_6.setPaddingTop(10f);
					cell3_6.setPaddingBottom(5f);
					cell4_1.setPaddingTop(10f);
					cell4_1.setPaddingBottom(5f);
				}

				pdfPTable.addCell(cell3_1);
				pdfPTable.addCell(cell3_2);
				pdfPTable.addCell(cell3_3);
				pdfPTable.addCell(cell3_4);
				pdfPTable.addCell(cell3_5);
				pdfPTable.addCell(cell3_6);
				pdfPTable.addCell(cell4_1);

				if (pdfPTable.calculateHeights() > yPos + 10
						&& repaginationRow == 0) {
					/** 大枠の線から10px上を越えていたらその行削除し次ページに表示 */
					pageHight = pdfPTable.calculateHeights();
					totalRowNum -= 1;
					pdfPTable.writeSelectedRows(0, 6, 0, totalRowNum, 30, yPos + 10,
							writer.getDirectContent());
					repaginationRow = totalRowNum;

				} else if (pdfPTable.calculateHeights() - pageHight > 750) {
					/** 行の高さがページ超えてくると無限ループ発生するはずなのであとで対処 */
					document.newPage();
					pageHight = pdfPTable.calculateHeights();
					/** 大枠の線から10px上を越えていたらその行削除し次ページに表示 */
					pdfPTable.writeSelectedRows(0, 6, repaginationRow,
							totalRowNum, 30, 800, writer.getDirectContent());
					repaginationRow = totalRowNum;
				}

				totalRowNum += 2;
			}
		}
		if (totalRowNum > repaginationRow && repaginationRow == 0) {
			pdfPTable.writeSelectedRows(0, 6, 0, -1, 30, yPos + 10,
					writer.getDirectContent());
		} else if (totalRowNum > repaginationRow) {
			document.newPage();
			pdfPTable.writeSelectedRows(0, 6, repaginationRow, -1, 30, 800,
					writer.getDirectContent());

		}

	}

	private static String replaceNewline(String text) {

		if (text == null) {
			return StringUtils.EMPTY;
		}
		String LINE_SEPARATOR_PATTERN1 = "\r\n";
		text = StringUtils.replace(text, LINE_SEPARATOR_PATTERN1, " ");
		String LINE_SEPARATOR_PATTERN2 = "\n";
		text = StringUtils.replace(text, LINE_SEPARATOR_PATTERN2, " ");
		return text;

	}

	/**
	 *
	 * @param document
	 * @param writer
	 */
	private static void newPage(Document document, PdfWriter writer) {

		document.newPage();

		PdfContentByte pdfContentByte = writer.getDirectContent();
		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawRect(30, 30, 535, 782);
		pdfGraphics2D.dispose();

	}

	private static Image makeBarcode(PdfWriter writer, String value) {

		// バーコードイメージの作成
		com.itextpdf.text.Image image = null;
		try {
			Barcode barcode39 = new com.itextpdf.text.pdf.Barcode39();
			barcode39.setCode(value);
			PdfContentByte cb = writer.getDirectContent();

			image = barcode39.createImageWithBarcode(cb, null, null);
		} catch (Exception e) {
			return null;
		}
		return image;
	}

	private static float fixedPhrases(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto, boolean pickFlag)
			throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 18);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(280, 790);
		// 表示する文字列の設定
		pdfContentByte.showText("納品書");

		//納品書新規レイアウト
		//フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);
		ExtendMstClientDTO client = new ClientService().getDispClient(slipDto.getSysClientId());

		boolean bremboFlag = false;
		for (int ii = 0; ii < slipDto.getPickItemList().size(); ii++) {
			if(slipDto
					.getPickItemList().get(ii).getItemCode().startsWith("71")) {
				
				bremboFlag = true;
				break;
			}
		}

		if (bremboFlag) {
			if (!pickFlag) {
				// 得意先郵便番号
				pdfContentByte.setTextMatrix(50, 790);
				pdfContentByte.showText("〒" + client.getZip());
				// 得意先住所(都道府県+市区町村+市区町村以降+建物名)
				pdfContentByte.setTextMatrix(50, 780);
				pdfContentByte.showText(client.getPrefectures()+client.getMunicipality()+client.getAddress()+client.getBuildingNm());
				//得意先電話番号
				if (!(client.getTel() == null || client.getTel().isEmpty())) {
					pdfContentByte.setTextMatrix(50, 770);
					pdfContentByte.showText("TEL:" + client.getTel());
				}
				//得意先FAX
				if (!(client.getFax() == null || client.getFax().isEmpty())) {
					pdfContentByte.setTextMatrix(50, 760);
					pdfContentByte.showText("FAX:" + client.getFax());
				}
					//得意先名の長さによっては法人情報に被ってしまうためフォントサイズを制御
				if (client.getClientNm().getBytes("Shift_JIS").length >= 28) {
					pdfContentByte.setFontAndSize(baseFont, 10);
				} else {
					pdfContentByte.setFontAndSize(baseFont, 16);
				}
				// 得意先名
				pdfContentByte.setTextMatrix(50, 740);
				pdfContentByte.showText(client.getClientNm() + " 御中");

				// 受注番号
				pdfContentByte.setTextMatrix(350, 760);
				pdfContentByte.setFontAndSize(baseFont, 9);
				pdfContentByte.showText("受注番号：" + slipDto.getOrderNo());
			}
		}else {
			if (!pickFlag) {
				// 得意先郵便番号
				pdfContentByte.setTextMatrix(50, 790);
				pdfContentByte.showText("〒" + client.getZip());
				// 得意先住所(都道府県+市区町村+市区町村以降+建物名)
				pdfContentByte.setTextMatrix(50, 780);
				pdfContentByte.showText(client.getPrefectures()+client.getMunicipality()+client.getAddress()+client.getBuildingNm());
				//得意先電話番号
				if (!(client.getTel() == null || client.getTel().isEmpty())) {
					pdfContentByte.setTextMatrix(50, 770);
					pdfContentByte.showText("TEL:" + client.getTel());
				}
				//得意先FAX
				if (!(client.getFax() == null || client.getFax().isEmpty())) {
					pdfContentByte.setTextMatrix(50, 760);
					pdfContentByte.showText("FAX:" + client.getFax());
				}
					//得意先名の長さによっては法人情報に被ってしまうためフォントサイズを制御
				if (client.getClientNm().getBytes("Shift_JIS").length >= 28) {
					pdfContentByte.setFontAndSize(baseFont, 10);
				} else {
					pdfContentByte.setFontAndSize(baseFont, 16);
				}
				// 得意先名
				pdfContentByte.setTextMatrix(50, 740);
				pdfContentByte.showText(client.getClientNm() + " 御中");

				// 受注番号
				pdfContentByte.setTextMatrix(350, 760);
				pdfContentByte.setFontAndSize(baseFont, 9);
				pdfContentByte.showText("受注番号：" + slipDto.getOrderNo());
			}else {
				pdfContentByte.setTextMatrix(50, 750);
				pdfContentByte.showText("納入先");

				// 得意先郵便番号
				pdfContentByte.setTextMatrix(50, 740);
				pdfContentByte.showText("〒" + slipDto.getDestinationZip()  + " " + slipDto.getDestinationPrefectures() + slipDto.getDestinationMunicipality() + slipDto.getDestinationAddress());

				if (!(slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())) {
					pdfContentByte.setTextMatrix(50, 730);
					pdfContentByte.showText("TEL：" + slipDto.getDestinationTel());
				}

				if (!(slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(50, 720);
					pdfContentByte.showText("FAX：" + slipDto.getDestinationFax());
				}

				if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						&& (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 730);
				} else if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						|| (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 720);
				} else {
					pdfContentByte.setTextMatrix(50, 710);
				}
				pdfContentByte.showText( "配送方法：" + slipDto.getInvoiceClassification());
				
				String destinationNm = slipDto.getDestinationNm() + " 御中";
//				if (destinationNm.getBytes("Shift_JIS").length >= 50) {
//					pdfContentByte.setFontAndSize(baseFont, 10);
//					if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
//							&& (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
//						pdfContentByte.setTextMatrix(53, 720);
//					} else if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
//							|| (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
//						pdfContentByte.setTextMatrix(53, 710);
//					} else {
//						pdfContentByte.setTextMatrix(50, 700);
//					}
//				} else {
//					pdfContentByte.setFontAndSize(baseFont, 16);
//					if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
//							&& (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
//						pdfContentByte.setTextMatrix(53, 710);
//					} else if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
//							|| (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
//						pdfContentByte.setTextMatrix(53, 700);
//					} else {
//						pdfContentByte.setTextMatrix(50, 690);
//					}
//				}
//				pdfContentByte.showText(destinationNm);
				pdfContentByte.setFontAndSize(baseFont, 10);
				int _x = 50;
				int _y = 700;
				if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						&& (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					_x = 53;
					_y = 720;
				} else if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						|| (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					_x = 53;
					_y = 710;
				} else {
					_x = 50;
					_y = 700;
				}

				int length = destinationNm.codePointCount(0, destinationNm.length());
				if (length >= 25) {
					int _rows = length / 25;
					if (length % 25 > 0)
						_rows += 1;
					for (int i=0; i<_rows; i++) {
						pdfContentByte.setTextMatrix(_x, _y - i * 13);
						int start = i * 25;
						int end = 25;
						if (end * (i + 1) >= length)
							end = length - i * 25;
						int startIndex = destinationNm.offsetByCodePoints(0, start);
						int endIndex = destinationNm.offsetByCodePoints(startIndex, end);
						String str = destinationNm.substring(startIndex, endIndex);
						pdfContentByte.showText(str);
					}
				} else {
					pdfContentByte.setTextMatrix(_x, _y);
					pdfContentByte.showText(destinationNm);
				}
			}
		}

		// テキストの終了
		pdfContentByte.endText();

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();


		StoreDTO storeDTO = new StoreDTO();

		//今後の機能実装で売上伝票と業販伝票を統合して売上統計を出す場合に注意してください。
		//業販伝票TBに販売チャネルIDカラムを追加してください。
		//20151028 aito

		//業販伝票TBでは販売チャネルIDはDBで管理されてないため、ここでは定数でセットしています。
		storeDTO = corporateSaleDisplayService.selectShopInfo(
				slipDto.getSysCorporationId(), ExtendCorporateSalesSlipDTO.SYS_CHANNEL_ID);
		if (storeDTO == null) {
			storeDTO = new StoreDTO();
		}

		PdfPTable CorporationTable = new PdfPTable(1);

		PdfPCell cell = null;

		if (StringUtils.equals(storeDTO.getStoreNmDispFlg(), "1")) {
		cell = new PdfPCell(new Paragraph(storeDTO.getStoreNm(), font));
			cell.setBorder(Rectangle.NO_BORDER);
			CorporationTable.addCell(cell);
		}

		if (bremboFlag && pickFlag) {
		}else {
			String corporationNm = storeDTO.getCorporationNm();
			if (StringUtils.equals(storeDTO.getNameHeaderDispFlg(), "1")) {
				//株式会社BCRの時「株式会社株式会社BCR」となってしまうため制御
				if (!corporationNm.equals("株式会社BCR")) {
					corporationNm = "株式会社 " + corporationNm;
				}
			}
			cell = new PdfPCell(new Paragraph(corporationNm, font));
			cell.setBorder(Rectangle.NO_BORDER);
			CorporationTable.addCell(cell);


			if (StringUtils.equals(storeDTO.getZipDispFlg(), "1")) {
				cell = new PdfPCell(new Paragraph("〒" + storeDTO.getZip(), font));
				cell.setBorder(Rectangle.NO_BORDER);
				CorporationTable.addCell(cell);
			}
			if (StringUtils.equals(storeDTO.getAddressDispFlg(), "1")) {
				cell = new PdfPCell(new Paragraph(storeDTO.getAddress(), font));
				cell.setBorder(Rectangle.NO_BORDER);
				CorporationTable.addCell(cell);
			}

			if (StringUtils.equals(storeDTO.getTelNoDispFlg(), "1")) {
				cell = new PdfPCell(new Paragraph("TEL:" + storeDTO.getTelNo(),
						font));
				cell.setBorder(Rectangle.NO_BORDER);
				CorporationTable.addCell(cell);
			}

			if (StringUtils.equals(storeDTO.getFaxNoDispFlg(), "1")) {
				cell = new PdfPCell(new Paragraph("FAX:" + storeDTO.getFaxNo(),
						font));
				cell.setBorder(Rectangle.NO_BORDER);
				CorporationTable.addCell(cell);
			}
			if (StringUtils.equals(storeDTO.getMailDispFlg(), "1")) {
				cell = new PdfPCell(new Paragraph("Email:"
						+ storeDTO.getStoreMailAddress(), font));
				cell.setBorder(Rectangle.NO_BORDER);
				CorporationTable.addCell(cell);
			}
		}

		cell = new PdfPCell(new Paragraph("　", font));
		cell.setBorder(Rectangle.NO_BORDER);
		CorporationTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("ご注文日：" + slipDto.getOrderDate(),
				font));
		cell.setHorizontalAlignment(2);
		cell.setBorder(Rectangle.NO_BORDER);
		CorporationTable.addCell(cell);

		CorporationTable.setTotalWidth(195);

		//該当商品の中で最も早い出庫予定日を発送日とする
		String shipmentDate = "";
		for (ExtendCorporateSalesItemDTO item : slipDto.getPickItemList()) {
			if (StringUtils.isEmpty(shipmentDate)) {
				shipmentDate = item.getScheduledLeavingDate();
			} else if (shipmentDate.compareTo(item.getScheduledLeavingDate()) > 0) {
				shipmentDate = item.getScheduledLeavingDate();
			}
		}
		cell = new PdfPCell(new Paragraph("発送日： "
				+ shipmentDate, font));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(2);
		CorporationTable.addCell(cell);

		//法人情報を「受注番号」のすぐ下に表示するための調節
		float CorporationTableYPos = 757;

		CorporationTable.writeSelectedRows(0, -1, 350, CorporationTableYPos,
				writer.getDirectContent());

		float CorporationTableHight = CorporationTable.calculateHeights();

		return CorporationTableYPos - CorporationTableHight;

	}

	private static float orderDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto,
			float orderCurrentHeight) throws Exception {

		//納品書新レイアウト
		PdfContentByte pdfContentByte = writer.getDirectContent();
		// テキストの開始
		pdfContentByte.beginText();
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(50, orderCurrentHeight);
		// 表示する文字列の設定
		if (!(slipDto.getBillingRemarks() == null || slipDto.getBillingRemarks().isEmpty())) {
			pdfContentByte.showText("■" + slipDto.getBillingRemarks());
		}
		// テキストの終了
		pdfContentByte.endText();

		//業販伝票商品テーブルの描画位置が【請求書備考】に被ってしまうので少し下に下げるために調節
		return orderCurrentHeight - 5;

	}

	/**
	 * ［概要］納品書出力メソッド：Aパターン（金額情報あり）
	 * ［詳細］金額情報がすべて記載されている納品書を出力する
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @param orderCurrentHeight
	 * @param dateFrom
	 * @param dateTo
	 * @throws Exception
	 */
	private static void orderItemDetailA(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto,
			float orderCurrentHeight, Date dateFrom, Date dateTo, boolean pickFlag) throws Exception {

		PdfPTable pdfPTable = new PdfPTable(5);
		pdfPTable.setTotalWidth(495);
		int width[] = { 70, 227, 74, 50, 74 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellItemCodeHeader = new PdfPCell(new Paragraph("品番", font));
		cellItemCodeHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		cellItemNmHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellUnitPriceHeader = new PdfPCell(new Paragraph("単価", font));
		cellUnitPriceHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		cellQuantityHeader.setGrayFill(0.8f); // セルを灰色に設定
		// 表の要素(列タイトル)を作成
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("価格", font));
		cellPriceHeader.setGrayFill(0.8f); // セルを灰色に設定

		cellItemCodeHeader.setHorizontalAlignment(1);
		cellItemNmHeader.setHorizontalAlignment(1);
		cellUnitPriceHeader.setHorizontalAlignment(1);
		cellQuantityHeader.setHorizontalAlignment(1);
		cellPriceHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellItemCodeHeader);
		pdfPTable.addCell(cellItemNmHeader);
		pdfPTable.addCell(cellUnitPriceHeader);
		pdfPTable.addCell(cellQuantityHeader);
		pdfPTable.addCell(cellPriceHeader);

		/**
		 * ループ(商品LISTのDTOをループさせる予定)
		 */
		int repaginationRow = 0;
		float pageHight = 0;
		int rowNum = 0;
		long totalPrice = 0;
		for (rowNum = 0; rowNum < slipDto.getPickItemList().size(); rowNum++) {

			// 品番
			PdfPCell cellItemCode = new PdfPCell(new Paragraph(slipDto
					.getPickItemList().get(rowNum).getItemCode(), font));
			// 商品名
			PdfPCell cellItemNm = new PdfPCell(new Paragraph(slipDto
					.getPickItemList().get(rowNum).getItemNm(), font));
			// 単価
			PdfPCell cellUnitPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(slipDto
							.getPickItemList().get(rowNum).getPieceRate()))
							+ "円", font));
			PdfPCell cellQuantity = new PdfPCell(new Paragraph(
					String.valueOf(slipDto.getPickItemList().get(rowNum)
							.getOrderNum()), font));
			PdfPCell cellPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(slipDto
							.getPickItemList().get(rowNum).getPieceRate()
							* slipDto.getPickItemList().get(rowNum)
									.getOrderNum()))
							+ "円", font));

			totalPrice += slipDto.getPickItemList().get(rowNum).getPieceRate()
					* slipDto.getPickItemList().get(rowNum).getOrderNum();

			cellItemCode.setHorizontalAlignment(2);
			cellUnitPrice.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);
			cellPrice.setHorizontalAlignment(2);

			pdfPTable.addCell(cellItemCode);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellUnitPrice);
			pdfPTable.addCell(cellQuantity);
			pdfPTable.addCell(cellPrice);

			if (pdfPTable.calculateHeights() > orderCurrentHeight
					&& repaginationRow == 0) {
				pageHight = pdfPTable.calculateHeights();
				/** 大枠の線を越えていたらその行削除し次ページに表示 */
				// pdfPTable.deleteRow(rowNum);
				// rowNum--;
				pageHight = pdfPTable.calculateHeights();
				pdfPTable.writeSelectedRows(0, 5, 0, rowNum - 1, 50,
						orderCurrentHeight, writer.getDirectContent());
				repaginationRow = rowNum;

			} else if (pdfPTable.calculateHeights() - pageHight > 770) {
				/** 行の高さがページ超えてくると無限ループ発生するはずなのであとで対処 */
				newPage(document, writer);
				pageHight = pdfPTable.calculateHeights();
				/** 大枠の線から10px上を越えていたらその行削除し次ページに表示 */
				// pdfPTable.deleteRow(rowNum);
				// rowNum--;
				pdfPTable.writeSelectedRows(0, 5, repaginationRow - 1,
						rowNum - 1, 50, 800, writer.getDirectContent());
				repaginationRow = rowNum;
			}
		}
		if (rowNum > repaginationRow && repaginationRow == 0) {
			pdfPTable.writeSelectedRows(0, 5, 0, -1, 50, orderCurrentHeight,
					writer.getDirectContent());
		} else if (rowNum > repaginationRow) {
			newPage(document, writer);
			//TODO 引数→１：colstart、２：colEnd、３：rowStart、４：rowEnd,  ５：xPos, ６：yPos　rowEnd最終行の取得がおかしいみたい。
			pdfPTable.writeSelectedRows(0, 5, repaginationRow - 1, rowNum + 1,
					50, 800, writer.getDirectContent());
		}
		/** 多分、計算式違う、下の計算から、テーブルを記述始めているyposから以下の値を引かないと欲しい値が算出されない。 */
		float height = pdfPTable.calculateHeights() - pageHight;

		PdfPTable itemTotalPriceTable = new PdfPTable(2);
		itemTotalPriceTable.setTotalWidth(210);
		int itemTotalPriceTableWidth[] = { 100, 110 };
		itemTotalPriceTable.setWidths(itemTotalPriceTableWidth);

		// 表の要素(列タイトル)を作成
		PdfPCell cellSumItemPriceHeader = new PdfPCell(new Paragraph(
				"税抜合計金額", font));
		cellSumItemPriceHeader.setGrayFill(0.8f); // セルを灰色に設定

		PdfPCell cellSumItemPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(totalPrice)) + "円", font));

		cellSumItemPriceHeader.setHorizontalAlignment(2);
		cellSumItemPrice.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		itemTotalPriceTable.addCell(cellSumItemPriceHeader);
		itemTotalPriceTable.addCell(cellSumItemPrice);

		// 表の要素(列タイトル)を作成
		PdfPCell cellTaxHeader = new PdfPCell(new Paragraph(
				"消費税", font));
		cellTaxHeader.setGrayFill(0.8f); // セルを灰色に設定

		PdfPCell cellTax = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getTax())) + "円", font));


		cellTaxHeader.setHorizontalAlignment(2);
		cellTax.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		itemTotalPriceTable.addCell(cellTaxHeader);
		itemTotalPriceTable.addCell(cellTax);


		// 表の要素(列タイトル)を作成
		PdfPCell cellSumItemPriceWithTaxHeader = new PdfPCell(new Paragraph(
				"合計金額", font));
		cellSumItemPriceWithTaxHeader.setGrayFill(0.8f); // セルを灰色に設定

		// 税追加
		totalPrice += slipDto.getTax();

		PdfPCell cellSumItemPriceWithTax = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(totalPrice)) + "円", font));


		cellSumItemPriceWithTaxHeader.setHorizontalAlignment(2);
		cellSumItemPriceWithTax.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		itemTotalPriceTable.addCell(cellSumItemPriceWithTaxHeader);
		itemTotalPriceTable.addCell(cellSumItemPriceWithTax);


		float yPos = 580 - (height + 10);
		//cellSumItemPriceHeaderを次ページに表示するか判定
		if (height > 400) {
			newPage(document, writer);
			yPos=800;
		}

		itemTotalPriceTable.writeSelectedRows(0, -1, 335, yPos,
				writer.getDirectContent());

		// 納品書新レイアウト
		// 納入先情報
		PdfContentByte pdfContentByte = writer.getDirectContent();
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.dispose();
		// 納入先情報の枠を描画
		pdfGraphics2D.drawRect(50, 730, 495, 75);

		boolean bremboFlag = false;
		for (int ii = 0; ii < slipDto.getPickItemList().size(); ii++) {
			if(slipDto
					.getPickItemList().get(ii).getItemCode().startsWith("71")) {
				
				bremboFlag = true;
				break;
			}
		}

		if (bremboFlag) {
			if (!pickFlag) {
				// 納入先情報
				// テキストの開始
				pdfContentByte.beginText();

				pdfContentByte.setFontAndSize(baseFont, 9);

				pdfContentByte.setTextMatrix(53, 95);
				pdfContentByte.showText("納入先");

				pdfContentByte.setTextMatrix(53, 85);
				pdfContentByte.showText("〒" + slipDto.getDestinationZip()  + " " + slipDto.getDestinationPrefectures() + slipDto.getDestinationMunicipality() + slipDto.getDestinationAddress());

				pdfContentByte.setTextMatrix(53, 75);
				pdfContentByte.showText(slipDto.getDestinationNm() + "様");

				if (!(slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 65);
					pdfContentByte.showText("TEL：" + slipDto.getDestinationTel());
				}

				if (!(slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 55);
					pdfContentByte.showText("FAX：" + slipDto.getDestinationFax());
				}

				//納入先電話番号と納入先FAXの有無によって配送方法の表示位置を指定する。
				if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						&& (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 65);
				} else if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						|| (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 55);
				} else {
					pdfContentByte.setTextMatrix(53, 45);
				}
				pdfContentByte.showText("配送方法：" + slipDto.getInvoiceClassification());

				// テキストの終了
				pdfContentByte.endText();
			}else {
				// 納入先情報
				// テキストの開始
				pdfContentByte.beginText();

				pdfContentByte.setFontAndSize(baseFont, 9);

				pdfContentByte.setTextMatrix(53, 95);
				pdfContentByte.showText("【ご購入後の商品の問い合わせについて】");

				pdfContentByte.setTextMatrix(53, 85);
				pdfContentByte.showText("商品を購入された販売店までご連絡ください。");
				// テキストの終了
				pdfContentByte.endText();
				
			}
		}else {
			if (!pickFlag) {
				// 納入先情報
				// テキストの開始
				pdfContentByte.beginText();

				pdfContentByte.setFontAndSize(baseFont, 9);

				pdfContentByte.setTextMatrix(53, 95);
				pdfContentByte.showText("納入先");

				pdfContentByte.setTextMatrix(53, 85);
				pdfContentByte.showText("〒" + slipDto.getDestinationZip()  + " " + slipDto.getDestinationPrefectures() + slipDto.getDestinationMunicipality() + slipDto.getDestinationAddress());

				pdfContentByte.setTextMatrix(53, 75);
				pdfContentByte.showText(slipDto.getDestinationNm() + "様");

				if (!(slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 65);
					pdfContentByte.showText("TEL：" + slipDto.getDestinationTel());
				}

				if (!(slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 55);
					pdfContentByte.showText("FAX：" + slipDto.getDestinationFax());
				}

				//納入先電話番号と納入先FAXの有無によって配送方法の表示位置を指定する。
				if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						&& (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 65);
				} else if ((slipDto.getDestinationTel() == null || slipDto.getDestinationTel().isEmpty())
						|| (slipDto.getDestinationFax() == null || slipDto.getDestinationFax().isEmpty())) {
					pdfContentByte.setTextMatrix(53, 55);
				} else {
					pdfContentByte.setTextMatrix(53, 45);
				}
				pdfContentByte.showText("配送方法：" + slipDto.getInvoiceClassification());

				// テキストの終了
				pdfContentByte.endText();
			}
		}
	}

	/**
	 * ［概要］納品書出力メソッド：Bパターン（金額情報無し）
	 * ［詳細］金額情報が記載されていない納品書を出力する
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @param orderCurrentHeight
	 * @param dateFrom
	 * @param dateTo
	 * @throws Exception
	 */
	private static void orderItemDetailB(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendCorporateSalesSlipDTO slipDto,
			float orderCurrentHeight, Date dateFrom, Date dateTo) throws Exception {

		PdfPTable pdfPTable = new PdfPTable(3);
		pdfPTable.setTotalWidth(495);
		int width[] = { 70, 375, 50};
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellItemCodeHeader = new PdfPCell(new Paragraph("品番", font));
		cellItemCodeHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		cellItemNmHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		cellQuantityHeader.setGrayFill(0.8f); // セルを灰色に設定

		cellItemCodeHeader.setHorizontalAlignment(1);
		cellItemNmHeader.setHorizontalAlignment(1);
		cellQuantityHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellItemCodeHeader);
		pdfPTable.addCell(cellItemNmHeader);
		pdfPTable.addCell(cellQuantityHeader);

		/**
		 * ループ(商品LISTのDTOをループさせる予定)
		 */
		int repaginationRow = 0;
		float pageHight = 0;
		int rowNum = 0;

		for (rowNum = 0; rowNum < slipDto.getPickItemList().size(); rowNum++) {

			// 品番
			PdfPCell cellItemCode = new PdfPCell(new Paragraph(slipDto
					.getPickItemList().get(rowNum).getItemCode(), font));
			// 商品名
			PdfPCell cellItemNm = new PdfPCell(new Paragraph(slipDto
					.getPickItemList().get(rowNum).getItemNm(), font));
			// 数量
			PdfPCell cellQuantity = new PdfPCell(new Paragraph(
					String.valueOf(slipDto.getPickItemList().get(rowNum)
							.getOrderNum()), font));

			cellItemCode.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);

			pdfPTable.addCell(cellItemCode);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellQuantity);

			if (pdfPTable.calculateHeights() > orderCurrentHeight
					&& repaginationRow == 0) {
				pageHight = pdfPTable.calculateHeights();
				/** 大枠の線を越えていたらその行削除し次ページに表示 */
				// pdfPTable.deleteRow(rowNum);
				// rowNum--;
				pageHight = pdfPTable.calculateHeights();
				pdfPTable.writeSelectedRows(0, 3, 0, rowNum - 1, 50,
						orderCurrentHeight, writer.getDirectContent());
				repaginationRow = rowNum;

			} else if (pdfPTable.calculateHeights() - pageHight > 770) {
				/** 行の高さがページ超えてくると無限ループ発生するはずなのであとで対処 */
				newPage(document, writer);
				pageHight = pdfPTable.calculateHeights();
				/** 大枠の線から10px上を越えていたらその行削除し次ページに表示 */
				// pdfPTable.deleteRow(rowNum);
				// rowNum--;
				pdfPTable.writeSelectedRows(0, 3, repaginationRow - 1,
						rowNum - 1, 50, 800, writer.getDirectContent());
				repaginationRow = rowNum;
			}
		}
		if (rowNum > repaginationRow && repaginationRow == 0) {
			pdfPTable.writeSelectedRows(0, 3, 0, -1, 50, orderCurrentHeight,
					writer.getDirectContent());
		} else if (rowNum > repaginationRow) {
			newPage(document, writer);
			pdfPTable.writeSelectedRows(0, 3, repaginationRow - 1, rowNum - 1,
					50, 800, writer.getDirectContent());
		}
		/** 多分、計算式違う、下の計算から、テーブルを記述始めているyposから以下の値を引かないと欲しい値が算出されない。 */
		float height = pdfPTable.calculateHeights() - pageHight;


		PdfPTable itemTotalPriceTable = new PdfPTable(2);
		itemTotalPriceTable.setTotalWidth(210);
		int itemTotalPriceTableWidth[] = { 100, 110 };
		itemTotalPriceTable.setWidths(itemTotalPriceTableWidth);

		float yPos = 580 - (height + 10);
		//cellSumItemPriceHeaderを次ページに表示するか判定
		if (height > 400) {
			newPage(document, writer);
			yPos=800;
		}

		itemTotalPriceTable.writeSelectedRows(0, -1, 335, yPos,
				writer.getDirectContent());

		// 納品書新レイアウト
		// 納入先情報の欄
		PdfContentByte pdfContentByte = writer.getDirectContent();
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.dispose();
		// 納入先情報の枠を描画
		pdfGraphics2D.drawRect(50, 730, 495, 75);
	}

	public void totalPickList(HttpServletResponse response,
			List<ExtendCorporateSalesSlipDTO> salesSlipList) throws Exception {

		Date date = new Date();

		Document document = new Document(PageSize.A4, 0, 0, 30, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("totalPickList.pdf"));

//		BaseFont baseFont = BaseFont.createFont(
//				AsianFontMapper.JapaneseFont_Go,
//				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);
//
//		Font font = new Font(BaseFont.createFont(
//				AsianFontMapper.JapaneseFont_Go,
//				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		document.open();

		PdfContentByte pdfContentByte = writer.getDirectContent();
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(210, 820);

		// 表示する文字列の設定
		pdfContentByte.showText("トータルピッキングリスト");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 8);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(430, 820);

		// 表示する文字列の設定
		pdfContentByte.showText("作成日時:" + displyTimeFormat.format(date));

		// テキストの終了
		pdfContentByte.endText();

		PdfPTable pdfPTable = new PdfPTable(4);
		pdfPTable.setTotalWidth(535);
		int width[] = { 30, 70, 405, 30 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellIdHeader = new PdfPCell(new Paragraph("", font));
		cellIdHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellItemCdHeader = new PdfPCell(new Paragraph("商品コード", font));
		cellItemCdHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		cellItemNmHeader.setGrayFill(0.8f); // セルを灰色に設定
		// 表の要素(列タイトル)を作成
		PdfPCell cellItemNumHeader = new PdfPCell(new Paragraph("数量", font));
		cellItemNumHeader.setGrayFill(0.8f); // セルを灰色に設定

		cellItemCdHeader.setHorizontalAlignment(1);
		cellItemNmHeader.setHorizontalAlignment(1);
		cellItemNumHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellIdHeader);
		pdfPTable.addCell(cellItemCdHeader);
		pdfPTable.addCell(cellItemNmHeader);
		pdfPTable.addCell(cellItemNumHeader);

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		List<ExtendCorporateSalesItemDTO> pickList = new ArrayList<>();
		pickList = corporateSaleDisplayService.getTotalPickItemList(salesSlipList);

		int itemRowCount = 1;
		int totalItemNum = 0;
		for (ExtendCorporateSalesItemDTO itemDto : pickList) {

			PdfPCell cellId = new PdfPCell(new Paragraph(
					String.valueOf(itemRowCount), font));

			PdfPCell cellItemCd = new PdfPCell(new Paragraph(
					itemDto.getItemCode(), font));

			PdfPCell cellItemNm = new PdfPCell(new Paragraph(
					itemDto.getItemNm(), font));

			PdfPCell cellItemNum = new PdfPCell(new Paragraph(
					String.valueOf(itemDto.getOrderNum()), font));

			totalItemNum += itemDto.getOrderNum();

			cellId.setHorizontalAlignment(2);
			cellItemCd.setHorizontalAlignment(1);
			cellItemNm.setHorizontalAlignment(0);
			cellItemNum.setHorizontalAlignment(2);

			// 表の要素を表に追加する
			pdfPTable.addCell(cellId);
			pdfPTable.addCell(cellItemCd);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellItemNum);

			itemRowCount++;
		}

		PdfPCell cellTotalItem = new PdfPCell(new Paragraph("合計", font));

		PdfPCell cellTotalItemNum = new PdfPCell(new Paragraph(
				String.valueOf(totalItemNum), font));
		cellTotalItem.setColspan(3); // セルを2列分結合

		cellTotalItem.setHorizontalAlignment(2);
		cellTotalItemNum.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellTotalItem);
		pdfPTable.addCell(cellTotalItemNum);

		document.add(pdfPTable);
		// pdfPTable.writeSelectedRows(0, 4, 0, -1, 30, 815,
		// writer.getDirectContent());
		document.close();

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
}