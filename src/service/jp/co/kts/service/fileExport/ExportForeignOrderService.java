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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.ui.mst.ForeignOrderService;

/**
 * ［概要］注文書出力用のServiceクラス
 * ［詳細］海外注文管理詳細画面において、PDF出力を行うクラス
 * @author Boncre
 *
 */
public class ExportForeignOrderService {
	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	/**
	 * 注文書作成初期メソッド
	 *
	 * @param response
	 * @param slipList
	 * @throws Exception
	 */
	public void orderAcceptanceList(HttpServletResponse response,
			ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList) throws Exception {

		Date date = new Date();

		String fname = "注文書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("orderAcceptance.pdf"));

		BaseFont baseFont  = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Go,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Go,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10);


		document.open();

		exportOrderAcceptance(document, writer, baseFont, font, date, dto, itemList);
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
	 * @throws Exception
	 */
	private void exportOrderAcceptance(Document document, PdfWriter writer, BaseFont baseFont, Font font, Date date,
			ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList) throws Exception {

		/** 注文書 ヘッダー*/
		orderAcceptanceHeader(document, writer, baseFont, date, dto, itemList);

		/** 商品一覧 */
		orderItemDetail(document, writer, font, baseFont, dto, itemList, 630);

	}

	/**
	 *  注文書
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
		pdfContentByte.setFontAndSize(baseFont, 25);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(180, 800);
		// 表示する文字列の設定
		pdfContentByte.showText("PURCHASE ORDER");


		//-------------------------------------------------【左上】法人情報設定

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 15);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 765);
		// 表示する文字列の設定
		pdfContentByte.showText("KIND TECHNOSTRUCTURE CO.,LTD");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 747);
		// 表示する文字列の設定
		pdfContentByte.showText("1-25-3 Minami Hatogaya, Kawaguchishi Saitama JAPAN 334-0013");
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 735);
		// 表示する文字列の設定
		pdfContentByte.showText("TEL: +81- 48- 285- 8941" + "                " + "FAX: +81- 48- 285- 8948");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 723);
		// 表示する文字列の設定
		pdfContentByte.showText("mail: k.hanaki@kts-web.com,  sales@kindtechnostructutre.co.jp");



		//--------------------------------------------【右上】注文日、PO No.設定

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(400, 775);
		// 表示する文字列の設定
		pdfContentByte.showText("DATE :");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(436, 775);
		// 年月日の設定
//		Locale locale = new Locale("ja", "JP", "JP");
//		SimpleDateFormat orderAcceptanceDate = new SimpleDateFormat("yyyy/MM/dd", locale);
		// 現在日付を取得.
		String orderAcceptanceDate = DateUtil.dateToString("yyyy/MM/dd");
		pdfContentByte.showText(orderAcceptanceDate);



		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(400, 760);
		// 表示する文字列の設定
		pdfContentByte.showText("Po No.:");
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		//PO No.取得
		String PO_NoString = "";
		PO_NoString += dto.getPoNo();
		//PO No.表示
		showTextArea(document, writer, baseFont,
				PO_NoString, ",", 436, 760, 5);


		//訂正フラグあり時、表示
		if (StringUtils.equals(dto.getCorrectionFlag(), "on")  || StringUtils.equals(dto.getCorrectionFlag(), "1")) {

			// フォントとサイズの設定
			pdfContentByte.setFontAndSize(baseFont, 22);
			// 色の設定
			pdfContentByte.setRGBColorFill(255, 0, 0);
			// 表示位置の設定
			pdfContentByte.setTextMatrix(430, 725);
			// 表示する文字列の設定
			pdfContentByte.showText("REVISED");
		}

		// ---------------------------------------------相手会社情報

//		Integer pageHeight = (int)document.getPageSize().getHeight();

		//仕入先情報取得
		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		List<ExtendMstSupplierDTO> suppList = new ArrayList<ExtendMstSupplierDTO>();
		//検索
		suppList = foreignOrderService.getSearchSupplierList(dto.getSysSupplierId());


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 14);
		// 色の設定
		pdfContentByte.setRGBColorFill(0, 0, 0);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 700);
		// 表示する文字列の設定 会社
		pdfContentByte.showText(suppList.get(0).getCompanyFactoryNm());


		String supplierString = "";
		//担当者
		supplierString += "," + "ATTN: " + suppList.get(0).getContactPersonNm();
		//TEL
		supplierString += "," + "TEL: " + suppList.get(0).getTel();
		//FAX
		supplierString += "," + "FAX: " + suppList.get(0).getFax();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		showTextArea(document, writer, baseFont,
				supplierString, ",", 35, 700, 15);

		// テキストの終了
		pdfContentByte.endText();
	}

	/**
	 * 商品情報
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
			Font font, BaseFont baseFont, ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList,
			float orderCurrentHeight) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		//通貨情報取得
		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		List<ExtendMstSupplierDTO> currencyList = new ArrayList<ExtendMstSupplierDTO>();
		//検索
		currencyList = foreignOrderService.getSearchSupplierList(dto.getSysSupplierId());

		// テキストの開始
		pdfContentByte.beginText();

		//通貨の表示
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(440, 640);
		// 表示する文字列の設定
		pdfContentByte.showText("currency: " + currencyList.get(0).getCurrencyNm());

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 6);

		int TABLE_COLS = 6;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		int width[] = {70, 70, 180, 65, 65, 65};
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellParts = new PdfPCell(new Paragraph("Parts No.", font));
		cellParts.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellKindNo = new PdfPCell(new Paragraph("Kind No.", font));
		cellKindNo.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellFescription = new PdfPCell(new Paragraph("Description", font));
		cellFescription.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellQty = new PdfPCell(new Paragraph("QTY", font));
		cellQty.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellUnitPrice = new PdfPCell(new Paragraph("Unit Price", font));
		cellUnitPrice.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellTotalProce = new PdfPCell(new Paragraph("Total Price", font));
		cellTotalProce.setGrayFill(0.8f); // セルを灰色に設定

		// 表の要素(列タイトル)を作成
		cellParts.setHorizontalAlignment(1);
		cellKindNo.setHorizontalAlignment(1);
		cellFescription.setHorizontalAlignment(1);
		cellQty.setHorizontalAlignment(1);
		cellUnitPrice.setHorizontalAlignment(1);
		cellTotalProce.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellParts);
		pdfPTable.addCell(cellKindNo);
		pdfPTable.addCell(cellFescription);
		pdfPTable.addCell(cellQty);
		pdfPTable.addCell(cellUnitPrice);
		pdfPTable.addCell(cellTotalProce);


		// テキストの終了
		pdfContentByte.endText();

		/**
		 * ループ(商品LISTのDTOをループさせる予定)
		 */

		int maxRow = 0;
		int rowNum = 0;
		int repaginationRow = 0;
		float pageHight = 0;

		int qtyTotal = 0;
		float unitPrice = 0;
		float totalPrice = 0;

		for (rowNum = 0; rowNum < itemList.size(); rowNum++, maxRow++) {

			//セル、フォント変え用
//			Font font_Go = new Font(BaseFont.createFont(
//					AsianFontMapper.JapaneseFont_Go,
//					AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10);

			//Parts No.
			PdfPCell cellrecodeFactoryItemCode =  new PdfPCell(new Paragraph(itemList.get(rowNum).getFactoryItemCode(), font));
			// Kind No.
			PdfPCell cellrecodeItemCode = new PdfPCell(new Paragraph(String.valueOf(itemList.get(rowNum).getItemCode()), font));
			// Fescription
			PdfPCell cellrecodeForeignItemNm = new PdfPCell(new Paragraph(itemList.get(rowNum).getForeignItemNm(), font));
			// QTY
			PdfPCell cellrecodeOrderNum = new PdfPCell(new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(itemList.get(rowNum).getOrderNum())), font));
			// Unit Price
			PdfPCell cellrecodeUnitPrice = new PdfPCell(new Paragraph(StringUtil.formatCalcFloat(BigDecimal.valueOf(itemList.get(rowNum).getUnitPrice())), font));
			//Total Price
			float total = itemList.get(rowNum).getOrderNum() * itemList.get(rowNum).getUnitPrice();
			PdfPCell cellrecodeTotal = new PdfPCell(new Paragraph(StringUtil.formatCalcFloat(BigDecimal.valueOf(total)), font));

			//セル内表示位置
			cellrecodeFactoryItemCode.setHorizontalAlignment(1);
			cellrecodeItemCode.setHorizontalAlignment(0);
			cellrecodeForeignItemNm.setHorizontalAlignment(0);
			cellrecodeOrderNum.setHorizontalAlignment(0);
			cellrecodeUnitPrice.setHorizontalAlignment(0);
			cellrecodeTotal.setHorizontalAlignment(0);

			//セル追加
			pdfPTable.addCell(cellrecodeFactoryItemCode);
			pdfPTable.addCell(cellrecodeItemCode);
			pdfPTable.addCell(cellrecodeForeignItemNm);
			pdfPTable.addCell(cellrecodeOrderNum);
			pdfPTable.addCell(cellrecodeUnitPrice);
			pdfPTable.addCell(cellrecodeTotal);

			qtyTotal += itemList.get(rowNum).getOrderNum();
			unitPrice += itemList.get(rowNum).getUnitPrice();
			totalPrice += total;

			if (rowNum + 1 == itemList.size()) {
				//totl の表示
				PdfPCell cellQtyTotalNm =  new PdfPCell(new Paragraph("Total", font));
				PdfPCell cell1 =  new PdfPCell(new Paragraph("", font));
				PdfPCell cell2 =  new PdfPCell(new Paragraph("", font));
				PdfPCell cellQtyTotal =  new PdfPCell(new Paragraph(String.valueOf(qtyTotal), font));
				PdfPCell cellUnitTotal =  new PdfPCell(new Paragraph(StringUtil.formatCalcFloat(BigDecimal.valueOf(unitPrice)), font));
				PdfPCell cellTotalTotal =  new PdfPCell(new Paragraph(StringUtil.formatCalcFloat(BigDecimal.valueOf(totalPrice)), font));

				//表示位置
				cellQtyTotalNm.setHorizontalAlignment(0);
				cell1.setHorizontalAlignment(1);
				cell2.setHorizontalAlignment(1);
				cellQtyTotal.setHorizontalAlignment(0);
				cellUnitTotal.setHorizontalAlignment(0);
				cellTotalTotal.setHorizontalAlignment(0);

				//セル追加
				pdfPTable.addCell(cellQtyTotalNm);
				pdfPTable.addCell(cell1);
				pdfPTable.addCell(cell2);
				pdfPTable.addCell(cellQtyTotal);
				pdfPTable.addCell(cellUnitTotal);
				pdfPTable.addCell(cellTotalTotal);
			}

			//商品テーブルの最下段判定
			if (pdfPTable.calculateHeights() > 600 && repaginationRow == 0) {

				//最下段の行数を格納
				pageHight = pdfPTable.calculateHeights();

				//行数を格納
				repaginationRow = rowNum;

				//商品テーブル開始位置
				pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, rowNum, 30, orderCurrentHeight,
						writer.getDirectContent());

			//２ページ目以降の最下段判定
			} else if (pdfPTable.calculateHeights() - pageHight > 750 && repaginationRow != 0) {

				//最下段の行数を格納
				newPage(document, writer);
				pageHight = pdfPTable.calculateHeights();

				//商品テーブル開始位置
				pdfPTable.writeSelectedRows(0, TABLE_COLS, repaginationRow,
						rowNum, 30, 800, writer.getDirectContent());

				//行数を格納
				repaginationRow = rowNum;
			}
		}

		//商品テーブルが1ページに収まる場合
		if (rowNum > repaginationRow && repaginationRow == 0) {

			//ヘッダー下部からテーブル表示
			pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow + 2, 30, orderCurrentHeight,
			writer.getDirectContent());

		//2ページ以上で最終ページ改ページ必要
		} else if (rowNum > repaginationRow) {
			newPage(document, writer);
			pdfPTable.writeSelectedRows(0, TABLE_COLS, repaginationRow, rowNum + 2,
					30, 800, writer.getDirectContent());
//			if (pdfPTable.calculateHeights() - pageHight > 600) {
//				newPage(document, writer);
//			}
		}
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
	 * 改ページ作成
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
//		pdfGraphics2D.drawRect(30, 30, 535, 782);
		pdfGraphics2D.dispose();

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
