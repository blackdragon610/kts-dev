
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
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;

/**
 * ［概要］振込依頼書（1）のServiceクラス
 * ［詳細］海外注文管理詳細画面において、PDF出力を行うクラス
 * @author Boncre
 *
 */
public class ExportForeignOrderTransferService {

	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	/**
	 * 振込依頼書作成初期メソッド
	 *
	 * @param response
	 * @param slipList
	 * @throws Exception
	 */
	public void orderAcceptanceList(HttpServletResponse response,
			ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList, List<ExtendMstSupplierDTO> supList) throws Exception {

		Date date = new Date();

		String fname = "振込依頼書1" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("ordertrance1.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10);

//		BaseFont baseFont = BaseFont.createFont(
//				AsianFontMapper.JapaneseFont_Go,
//				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);
//
//		BaseFont baseFont = BaseFont.createFont(
//				AsianFontMapper.JapaneseFont_Go,
//				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

//		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);


		document.open();

		exportOrderAcceptance(document, writer, baseFont, font, date, dto, itemList, supList);
		// 改ページ
		document.newPage();

		document.close();

	}

	/**
	 * 出力内容振り分け
	 *
	 * @param document
	 * @param writer
	 * @param baseFont
	 * @param font
	 * @param date
	 * @param slipDto
	 */
	private void exportOrderAcceptance(Document document, PdfWriter writer, BaseFont baseFont, Font font, Date date,
			ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList, List<ExtendMstSupplierDTO> supList) throws Exception {

		/** 振り込み依頼書 ヘッダー*/
		orderAcceptanceHeader(document, writer, baseFont, date, dto, itemList);

		/** 出力情報 */
		orderItemDetail(document, writer, font, baseFont, dto, itemList, supList, date);

	}

	/**
	 *  振り込み依頼書
	 *  ヘッダー部分
	 *  出力情報フォント、サイズ、位置設定
	 *
	 * @param document
	 * @param writer
	 * @param baseFont
	 * @param date
	 * @param slipDto
	 * @throws Exception
	 */
	private static void orderAcceptanceHeader(Document document, PdfWriter writer,
			BaseFont baseFont, Date date, ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		// テキストの開始
		pdfContentByte.beginText();

		//------------------------------------------------- 題目

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 23);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(210, 780);
		// 表示する文字列の設定
		pdfContentByte.showText("振込み依頼書");


		//--------------------------------------------【左上】印刷日設定

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(55, 755);
		// 表示する文字列の設定
		pdfContentByte.showText("依頼日:");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(95, 755);

		SimpleDateFormat orderAcceptanceDate = new SimpleDateFormat("yyyy年MM月dd日");
		pdfContentByte.showText(orderAcceptanceDate.format(date));


		//--------------------------------------------【右上】部署名設定

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(400, 755);
		// 表示する文字列の設定
		pdfContentByte.showText("部署名：KTS");

		//--------------------------------------------【右上】部署名設定

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(365, 110);
		// 表示する文字列の設定
		pdfContentByte.showText("第一");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(430, 110);
		// 表示する文字列の設定
		pdfContentByte.showText("第二");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(493, 110);
		// 表示する文字列の設定
		pdfContentByte.showText("社長");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(50, 350);
		// 表示する文字列の設定
		pdfContentByte.showText("取扱い");

		// テキストの終了
		pdfContentByte.endText();


		//----------------------------------------------ボックス設定

		Integer pageHeight = (int)document.getPageSize().getHeight();

		int estimateRemarksX = 385;
		//A4サイズの縦のサイズ
		int pageSizeY = (int)document.getPageSize().getHeight();

		//見積備考の位置
		//数値を大きくする場合　下へ移動
		//数値を小さくする場合　上へ移動
		int esitimateRemarksY = 75;

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.dispose();

		//部署欄四角
		pdfGraphics2D.drawRect(
				estimateRemarksX
				//グラフの場合は上が基準になる
				, esitimateRemarksY
				, 120
				, 20);

		//四角幅
		int stampWidth = 65;
		//四角縦幅
		int stampHeight = 50;
		//横位置
		int stampX = 345;
		//印鑑欄
		pdfGraphics2D.drawRect(stampX, pageHeight - 105, stampWidth, stampHeight);
		stampX += stampWidth;
		pdfGraphics2D.drawRect(stampX, pageHeight - 105, stampWidth, stampHeight);
		stampX += stampWidth;
		pdfGraphics2D.drawRect(stampX, pageHeight - 105, stampWidth, stampHeight);

	}

	/**
	 * 出力情報
	 *
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @param orderCurrentHeight
	 * @throws Exception
	 */
	private static void orderItemDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList, List<ExtendMstSupplierDTO> supList, Date date
			) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		// テキストの開始
		pdfContentByte.beginText();

		int TABLE_COLS = 2;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setHorizontalAlignment(1);
		pdfPTable.setTotalWidth(495);
		int width[] = {55, 440};
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellParts = new PdfPCell(new Paragraph("\n" + "依頼者名" + "\n\n", font));
		//依頼者(担当者)
		PdfPCell cellrecodeFactoryItemCode =  new PdfPCell(new Paragraph("\n" + dto.getPersonInCharge(), font));

		// 表の要素(列タイトル)を作成
		cellParts.setHorizontalAlignment(1);
		cellrecodeFactoryItemCode.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellParts);
		pdfPTable.addCell(cellrecodeFactoryItemCode);

		// 表の要素(列タイトル)を作成
		PdfPCell cellb1 = new PdfPCell(new Paragraph("\n" + "振込指定日" + "\n\n", font));
		//振込指定日
		PdfPCell cellb2 = new PdfPCell();
		if (StringUtils.equals(dto.getTransferPatern(),"1")) {

			if (StringUtils.equals(dto.getPaymentDate1(), "")) {
				dto.setPaymentDate1("");
			} else {

				//yyyy/mm/ddを年月日に置換
				String paymentDate1 = dto.getPaymentDate1();
				String year = paymentDate1.substring(0, 4);
				String month = paymentDate1.substring(5, 7);
				String day = paymentDate1.substring(8, 10);

				cellb2 = new PdfPCell(new Paragraph("\n" + year + "年" + month + "月" + day + "日", font));
			}

		} else {

			if (StringUtils.equals(dto.getPaymentDate2(), "")) {
				dto.setPaymentDate2("");
			} else {

				//yyyy/mm/ddを年月日に置換
				String paymentDate2 = dto.getPaymentDate2();
				String year = paymentDate2.substring(0, 4);
				String month = paymentDate2.substring(5, 7);
				String day = paymentDate2.substring(8, 10);

				cellb2 = new PdfPCell(new Paragraph("\n" + year + "年" + month + "月" + day + "日", font));
			}
		}

		// 表の要素(列タイトル)を作成
		cellb1.setHorizontalAlignment(1);
		cellb2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellb1);
		pdfPTable.addCell(cellb2);

		// 表の要素(列タイトル)を作成
		PdfPCell cellc1 = new PdfPCell(new Paragraph("\n" + "金額" + "\n\n", font));
		//金額（支払）
		PdfPCell cellc2 = new PdfPCell();

		//通貨が無い場合は非表示
		if (supList.get(0).getCurrencyType() == null || supList.get(0).getCurrencyType() == "") {
			supList.get(0).setCurrencyType("");
		}

		//支払い条件1
		if (StringUtils.equals(dto.getTransferPatern(),"1")) {

			//計算用意
			BigDecimal payment1 = dto.getPayment1();
			BigDecimal one = new BigDecimal(1.00);
			BigDecimal zero = new BigDecimal(0.00);

			//計算
			BigDecimal divide = payment1.remainder(one);

			if (divide.compareTo(zero) == 0) {
//				Phrase phrase1 = new Phrase("(6)");
//				phrase1.add(new Chunk(StringUtil.formatCalc(BigDecimal.valueOf(dto.getPayment1())), FontFactory.getFont(FontFactory.HELVETICA, 12)));

//				Font font_underlines = FontFactory.getFont(FontFactory.HELVETICA, 10);
//				Font font_underline = new Font(BaseFont.createFont(FontFactory.getFont(FontFactory.HELVETICA)), 10, Font.UNDERLINE);
				cellc2 = new PdfPCell(new Paragraph(String.valueOf("\n" + supList.get(0).getCurrencyType()) + StringUtil.formatCalc(dto.getPayment1()), font));
			} else {

//				Font font_underline = new Font(BaseFont.createFont(AsianFontMapper.JapaneseFont_Min, AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10,Font.UNDERLINE);
				cellc2 = new PdfPCell(new Paragraph(String.valueOf("\n" + supList.get(0).getCurrencyType()) + StringUtil.formatCalcFloat(dto.getPayment1()) + " *　　　　　　　　     少数点含", font));
			}
//			Phrase phrase1 = new Phrase("(6)");
//			Chunk chunk = new Chunk("This is a Font : ");
//			phrase1.add(chunk);
//			phrase1.add(new Chunk("Helvetica", FontFactory.getFont(FontFactory.HELVETICA, 12)));
//			phrase1.add(chunk);
//			phrase1.add(new Chunk("Times New Roman", FontFactory.getFont(FontFactory.TIMES_ROMAN, 13)));
//			phrase1.add(chunk);
//			phrase1.add(new Chunk("Courier", FontFactory.getFont(FontFactory.COURIER, 14)));
//
//			document.add(phrase1);
//
//			String[] textAry = StringUtil.formatCalcFloat(BigDecimal.valueOf(dto.getPayment1())).split(".");
//
//			for (String text : textAry) {
//				// 表示位置の設定
//				pdfContentByte.setTextMatrix(10, 10);
//
//				// 表示する文字列の設定
//				pdfContentByte.showText(text);
//			}

		} else {
			//計算用意
			BigDecimal payment2 = dto.getPayment2();
			BigDecimal one = new BigDecimal(1.00);
			BigDecimal zero = new BigDecimal(0.00);

			//計算
			BigDecimal divide = payment2.remainder(one);

			if (divide.compareTo(zero) == 0) {

				//支払い条件2
//				Font font_underline = new Font(BaseFont.createFont(AsianFontMapper.JapaneseFont_Min, AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10,Font.UNDERLINE);
				cellc2 = new PdfPCell(new Paragraph(String.valueOf("\n" + supList.get(0).getCurrencyType()) + StringUtil.formatCalc(dto.getPayment2()), font));
			} else {

				//支払い条件2
//				Font font_underline = new Font(BaseFont.createFont(AsianFontMapper.JapaneseFont_Min, AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10,Font.UNDERLINE);
				cellc2 = new PdfPCell(new Paragraph(String.valueOf("\n" + supList.get(0).getCurrencyType()) + StringUtil.formatCalcFloat(dto.getPayment2()) + "  *　　　　　　　　    小数点含", font));
			}
		}
		// 表の要素(列タイトル)を作成
		cellc1.setHorizontalAlignment(1);
		cellc2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellc1);
		pdfPTable.addCell(cellc2);


		// 表の要素(列タイトル)を作成
		PdfPCell celld1 = new PdfPCell(new Paragraph("\n" + "振込口座", font));
		//振込口座()
//		Font font_HELVETICA = FontFactory.getFont(FontFactory.HELVETICA, 10);
		PdfPCell celld2 =  new PdfPCell(new Paragraph("\n" + "SWIFT CODE   : " + supList.get(0).getSwiftCode() + "\n\n" + "ACCOUNT NO. : " + supList.get(0).getAccountNo() + "\n\n", font));

		// 表の要素(列タイトル)を作成
		celld1.setHorizontalAlignment(1);
		celld2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTable.addCell(celld1);
		pdfPTable.addCell(celld2);



		// 表の要素(列タイトル)を作成
		PdfPCell celle1 = new PdfPCell(new Paragraph("\n" + "振込先名義", font));
		//振込先名義()
		PdfPCell celle2 =  new PdfPCell(new Paragraph("\n" + supList.get(0).getCompanyFactoryNm() + "\n\n", font));

		// 表の要素(列タイトル)を作成
		celle1.setHorizontalAlignment(1);
		celle2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTable.addCell(celle1);
		pdfPTable.addCell(celle2);



		// 表の要素(列タイトル)を作成
		PdfPCell cellf1 = new PdfPCell(new Paragraph("\n" + "手数料負担", font));
		//手数料負担()
		PdfPCell cellf2 = new PdfPCell(new Paragraph("\n" +"当社" + "     " + "/" + "    " + "お客様" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellf1.setHorizontalAlignment(1);
		cellf2.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellf1);
		pdfPTable.addCell(cellf2);



		// 表の要素(列タイトル)を作成
		PdfPCell cellg1 = new PdfPCell(new Paragraph("\n" + "振込目的", font));
		//振込目的()
		PdfPCell cellg2 = new PdfPCell(new Paragraph("\n" + "支払い" + "     " + "/" + "    " + "返金" + "     " + "/"
		+ "     " + "仕入れ" + "    " + "/" + "    " + "下取り値引き" + "   " + "/" + "   " + "その他" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellg1.setHorizontalAlignment(1);
		cellg2.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellg1);
		pdfPTable.addCell(cellg2);



		// 表の要素(列タイトル)を作成
		PdfPCell cellhq1 = new PdfPCell(new Paragraph("\n" + "振込理由", font));
		//振込理由()
		PdfPCell cellh2 = new PdfPCell();
		if (StringUtils.equals(dto.getTransferPatern(),"1")) {
			cellh2 = new PdfPCell(new Paragraph("インボイス No. " + dto.getInvoiceNo()
				 + "         " + "インボイス金額 " + dto.getPaymentTerms1() + "%" + "\n" + "\n\n\n\n\n\n\n", font));
		} else {
			cellh2 = new PdfPCell(new Paragraph("インボイス No. " + dto.getInvoiceNo()
					 + "         " + "インボイス金額 " + "   " + "残" + dto.getPaymentTerms2() + "%" + "\n\n\n\n\n\n\n\n", font));
		}

		// 表の要素(列タイトル)を作成
		cellhq1.setHorizontalAlignment(1);
		cellh2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellhq1);
		pdfPTable.addCell(cellh2);


		//振込理由表示
		//ひらがな横35文字縦6行
		//半角なら
		showTextArea(document, writer, baseFont,
				  dto.getTransferReason()
				, "\\r\\n"
				, 110
				//テキストの場合は下が基準になる
				, 418
				, 11);

		//テーブル描画
		pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, 10 + 2, 50, 730,
		writer.getDirectContent());

		// テキストの終了
		pdfContentByte.endText();

		//----------------------------------取り扱い----------------------------------

		int TABLE_COLS_SECOND = 2;
		PdfPTable pdfPTableSecond = new PdfPTable(TABLE_COLS_SECOND);
		pdfPTableSecond.setTotalWidth(495);
		int widths[] = {95, 400};
		pdfPTableSecond.setWidths(widths);

		// 表の要素(列タイトル)を作成
		PdfPCell cellPartsb1 = new PdfPCell(new Paragraph("\n" + "販売窓口", font));
		//販売窓口
		PdfPCell cellrecodeb2 =  new PdfPCell(new Paragraph("" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellPartsb1.setHorizontalAlignment(1);
		cellrecodeb2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTableSecond.addCell(cellPartsb1);
		pdfPTableSecond.addCell(cellrecodeb2);


		// 表の要素(列タイトル)を作成
		PdfPCell cellPartsc1 = new PdfPCell(new Paragraph("\n" + "入金日/発送日", font));
		//依頼者(担当者)
		PdfPCell cellrecodec2 =  new PdfPCell(new Paragraph("" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellPartsc1.setHorizontalAlignment(1);
		cellrecodec2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTableSecond.addCell(cellPartsc1);
		pdfPTableSecond.addCell(cellrecodec2);


		// 表の要素(列タイトル)を作成
		PdfPCell cellPartsd1 = new PdfPCell(new Paragraph("\n" + "入金方法", font));
		//入金方法
		PdfPCell cellrecoded2 =  new PdfPCell(new Paragraph("" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellPartsd1.setHorizontalAlignment(1);
		cellrecoded2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTableSecond.addCell(cellPartsd1);
		pdfPTableSecond.addCell(cellrecoded2);


		// 表の要素(列タイトル)を作成
		PdfPCell cellPartse1 = new PdfPCell(new Paragraph("\n" + "入金名義", font));
		//入金名義
		PdfPCell cellrecodee2 =  new PdfPCell(new Paragraph("" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellPartse1.setHorizontalAlignment(1);
		cellrecodee2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTableSecond.addCell(cellPartse1);
		pdfPTableSecond.addCell(cellrecodee2);


		// 表の要素(列タイトル)を作成
		PdfPCell cellPartsf1 = new PdfPCell(new Paragraph("\n" + "注文者住所", font));
		//注文者住所
		PdfPCell cellrecodef2 =  new PdfPCell(new Paragraph("" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellPartsf1.setHorizontalAlignment(1);
		cellrecodef2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTableSecond.addCell(cellPartsf1);
		pdfPTableSecond.addCell(cellrecodef2);


		// 表の要素(列タイトル)を作成
		PdfPCell cellPartsa1 = new PdfPCell(new Paragraph("\n" + "注文者電話番号", font));
		//注文者電話番号
		PdfPCell cellrecodea2 =  new PdfPCell(new Paragraph("" + "\n\n\n", font));

		// 表の要素(列タイトル)を作成
		cellPartsa1.setHorizontalAlignment(1);
		cellrecodea2.setHorizontalAlignment(0);

		// 表の要素を表に追加する
		pdfPTableSecond.addCell(cellPartsa1);
		pdfPTableSecond.addCell(cellrecodea2);

		//テーブル描画
		pdfPTableSecond.writeSelectedRows(0, TABLE_COLS_SECOND, 0, 10 + 2, 50, 345,
		writer.getDirectContent());

	}


	/**
	 * 複数情報の表示メソッド
	 *
	 * @param document
	 * @param writer
	 * @param baseFont
	 * @param texts
	 * @param splWord
	 * @param posX
	 * @param posY
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


	/**
	 * ファイル設定
	 *
	 * @param response
	 * @param filePath
	 * @param fname
	 * @throws Exception
	 */
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
