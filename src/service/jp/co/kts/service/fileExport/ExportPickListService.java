package jp.co.kts.service.fileExport;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeCodabar;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.StoreDTO;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.sale.SaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;
import net.arnx.jsonic.JSON;

public class ExportPickListService {

	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	// static int testrow = 40;
	public void pickList(HttpServletRequest request, HttpServletResponse response,
			List<ExtendSalesSlipDTO> salesSlipList) throws Exception {

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		List<ExtendSalesSlipDTO> pickList = new ArrayList<>();
		pickList = saleDisplayService.getPickItemList(salesSlipList);

		/*
		 *  出力しようとしている伝票が全て楽天倉庫の商品であった場合
		 *  ピッキングリスト・納品書を出力する処理はスキップする。
		 *  ※楽天倉庫の伝票はピッキングリスト・納品書を出力しない仕様のため。
		 */
		/*
		 *  ピッキングリスト・納品書の出力はajaxで処理しているので
		 *  楽天倉庫伝票のみを印刷しようとした場合はメッセージを出力する為に
		 *  判別する文字をjspへ渡す。
		 */
		int[] slipCountArray = new int[2];
		slipCountArray = countKtsStocks(pickList);

		//KTS伝票が０件の場合、ピッキングリスト・納品書は出力しない。
		if (slipCountArray[0] <= 0) {
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(slipCountArray));
			return;
		}

		Date date = new Date();

		String fname = "ピッキング＆納品書リスト" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("pickList.pdf"));

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

		for (ExtendSalesSlipDTO slipDto : pickList) {

			// KTS倉庫から出庫予定の商品だけピッキングリストと納品書を出力する。
			if (slipDto.getRslLeaveFlag() == null || StringUtils.equals(slipDto.getRslLeaveFlag(), "0")) {
				/** ピッキングリスト */
				pickHeader(document, writer, baseFont, date);
				pickList(request, document, writer, font, baseFont, slipDto);
				document.newPage();

				/** 納品書 */
				float orderCurrentHeight = 0;
				orderCurrentHeight = fixedPhrases(document, writer, font, baseFont,
						slipDto);
				orderCurrentHeight = orderDetail(document, writer, font, baseFont,
						slipDto, orderCurrentHeight);
				orderItemDetail(document, writer, font, baseFont, slipDto,
						orderCurrentHeight);
				// 改ページ
				document.newPage();
			}
		}

		document.close();

		// ピッキングリスト・納品書を作成することができたら呼び出し元に目印を返してpdf出力する。
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(slipCountArray));

	}

	private static boolean addNewRow(PdfWriter writer, PdfPTable pdfPTable, Font font, int rowNum, ExtendSalesItemDTO item /*ExtendSalesSlipDTO slipDto*/) {
		// 表の要素を作成		
		PdfPCell cell3_1;
		
		if (item /*slipDto*/ != null) {
			cell3_1 = new PdfPCell(new Paragraph(/*slipDto.getPickItemList().get(rowNum)*/item.getItemCode(), font));

			// cell3_2, cell3_3がnullの場合,当該セルが表示されなくなる現象を修正
			if (/*slipDto.getPickItemList().get(rowNum)*/item.getWarehouseNm() == null) {
				/*slipDto.getPickItemList().get(rowNum)*/item.setWarehouseNm("　");
			}
			if (/*slipDto.getPickItemList().get(rowNum)*/item.getLocationNo() == null){
				/*slipDto.getPickItemList().get(rowNum)*/item.setLocationNo("　");
			}
		}
		else { 
			cell3_1 = new PdfPCell(new Paragraph("", font));
		}


		// 表の要素を作成
		PdfPCell cell3_3;
		PdfPCell cell3_4;
		PdfPCell cell3_5;
		PdfPCell cell4_1;
		
		if (item/*slipDto*/ != null) {
			cell3_3 = new PdfPCell(new Paragraph(/*slipDto.getPickItemList().get(rowNum)*/item.getLocationNo(), font));
			cell3_4 = new PdfPCell(new Paragraph(String.valueOf(1) + "/" + 
						String.valueOf(/*slipDto.getPickItemList().get(rowNum)*/item.getOrderNum()), font));

			// 4996740500084
			// 表の要素を作成

			com.itextpdf.text.Image image = null;
			image = makeBarcode(writer, /*slipDto.getPickItemList().get(rowNum)*/item.getItemCode());

			if (image != null) {
				cell3_5 = new PdfPCell(image);
			} else {
				cell3_5 = new PdfPCell(new Paragraph("", font));
			}

			cell4_1 = new PdfPCell(new Paragraph(/*slipDto.getPickItemList().get(rowNum)*/item.getItemNm(), font));
		}
		else {
			cell3_3 = new PdfPCell(new Paragraph("", font));
			cell3_4 = new PdfPCell(new Paragraph("", font));
			cell3_5 = new PdfPCell(new Paragraph("", font));
			cell4_1 = new PdfPCell(new Paragraph("", font));
		}

		cell3_1.setHorizontalAlignment(1);
		cell3_3.setHorizontalAlignment(1);
		cell3_4.setHorizontalAlignment(1);
		cell3_5.setHorizontalAlignment(1);
		cell4_1.setHorizontalAlignment(1);

//		if (image != null) {
			cell3_1.setPaddingTop(10f);
			cell3_1.setPaddingBottom(5f);
			cell3_1.setPaddingLeft(5f);
			cell3_1.setPaddingRight(5f);
			cell3_1.setFixedHeight(50f);
			
			cell3_3.setPaddingTop(10f);
			cell3_3.setPaddingBottom(5f);
			cell3_3.setPaddingLeft(5f);
			cell3_3.setPaddingRight(5f);
			cell3_3.setFixedHeight(50f);
			
			cell3_4.setPaddingTop(10f);
			cell3_4.setPaddingBottom(5f);
			cell3_4.setPaddingLeft(5f);
			cell3_4.setPaddingRight(5f);
			cell3_4.setFixedHeight(50f);
			
			cell3_5.setPaddingTop(10f);
			cell3_5.setPaddingBottom(5f);
			cell3_5.setPaddingLeft(5f);
			cell3_5.setPaddingRight(5f);
			cell3_5.setFixedHeight(50f);
			
			cell4_1.setPaddingTop(10f);
			cell4_1.setPaddingBottom(5f);
			cell4_1.setPaddingLeft(5f);
			cell4_1.setPaddingRight(5f);
			cell4_1.setFixedHeight(50f);
//		}
		
		if (rowNum % 2 == 1) {
			cell3_1.setGrayFill(0.8f);
			cell4_1.setGrayFill(0.8f);
			cell3_4.setGrayFill(0.8f);
//			cell3_5.setGrayFill(0.8f);
			cell3_3.setGrayFill(0.8f);
		}

		pdfPTable.addCell(cell3_1);
		pdfPTable.addCell(cell4_1);
		pdfPTable.addCell(cell3_4);
		pdfPTable.addCell(cell3_5);
		pdfPTable.addCell(cell3_3);
		
		return true;
	}

	private static Image loadImageFromWebContent(HttpServletRequest request, String relativeWebPath) {
		InputStream stream = request.getServletContext().getResourceAsStream("/WEB-INF/img/pagefooter.png"); 
		ByteArrayOutputStream bis = new ByteArrayOutputStream();

		try {
		    int i;
		    byte[] data = new byte[1024];
		    while ((i = stream.read(data, 0, data.length)) != -1) {
		      bis.write(data, 0, i);
		    }
		    bis.flush();
		    stream.close();
		    return Image.getInstance(bis.toByteArray());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
//		String scheme = request.getScheme();
//		String serverName = request.getServerName();
//		int portNumber = request.getServerPort();
//		String contextPath = request.getContextPath();
//		
//		String fullPath = "https://stage.kind-alpha.jp/kts_/img/pagefooter.png";
//		System.out.println("Image's Path : = " + fullPath);
//		
//		try {
//			return Image.getInstance(fullPath);
//		} catch (BadElementException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		// static path 
//		String fullPath1 = scheme + "://" + serverName + ":" +portNumber + contextPath + relativeWebPath;
//		try {
//			return Image.getInstance(fullPath1);
//		} catch (BadElementException | IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();				
//		}			
//
//		return null;
	}
	
	private static Image makeCodaBarCode(PdfWriter writer, String value) {
		com.itextpdf.text.Image image = null;
		
		try {
			BarcodeCodabar codabar = new BarcodeCodabar();
			// codabar.setGenerateChecksum(true);
		    codabar.setCode(value);
		    
		    PdfContentByte cb = writer.getDirectContent();		    
		    image = codabar.createImageWithBarcode(cb, null, null);
		    return image;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
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
		pdfContentByte.showText("ピッキングリスト");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 8);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(465, 825);

		// 表示する文字列の設定
		pdfContentByte.showText(displyTimeFormat.format(date) + "　作成");

		// テキストの終了
		pdfContentByte.endText();
	}

	private static void pickList(HttpServletRequest request, Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendSalesSlipDTO slipDto)
			throws Exception {

		int PAGE_HEIGHT = 820;

		PdfContentByte pdfContentByte = writer.getDirectContent();

		/**
		 * ---------------------------------------------------注文者情報START--------
		 * ---------------------------------------------------------
		 */
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 8);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(30, 800);

		// 表示する文字列の設定
		pdfContentByte.showText("■注文情報");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(40, 780);

		// 表示する文字列の設定
		pdfContentByte.showText("受注ルート");
		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 780);

		// 表示する文字列の設定
		if (slipDto.getCorporationNm() != null)
			pdfContentByte.showText(slipDto.getCorporationNm());
		else 
			pdfContentByte.showText("");
		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(40, 760);

		// 表示する文字列の設定
		pdfContentByte.showText("受注番号");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 760);

		// 表示する文字列の設定
		if (slipDto.getOrderNo() != null)
			pdfContentByte.showText(slipDto.getOrderNo());
		else 
			pdfContentByte.showText("");
		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(40, 740);

		// 表示する文字列の設定
		pdfContentByte.showText("お届先名");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 740);

		// 表示する文字列の設定
		{
			String name = "";
			if (slipDto.getDestinationFullNm() != null)
				name += slipDto.getDestinationFullNm();
			
			if (slipDto.getDestinationFullNmKana() != null)
				name += ("(" + slipDto.getDestinationFullNmKana() + ")");
			
			name += "様";
			
			pdfContentByte.showText(name);
		}
		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(40, 720);

		// 表示する文字列の設定
		pdfContentByte.showText("お届先住所");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 720);

		// 表示する文字列の設定
		{
			String destination = "〒";
			if (slipDto.getDestinationZip() != null)
				destination += slipDto.getDestinationZip() + " ";
			
			if (slipDto.getDestinationPrefectures() != null)
				destination += slipDto.getDestinationPrefectures();
			
			if (slipDto.getDestinationMunicipality() != null)
				destination += slipDto.getDestinationMunicipality();
			
			if (slipDto.getDestinationAddress() != null)
				destination += slipDto.getDestinationAddress();
			
			if (slipDto.getDestinationBuildingNm() != null)
				destination += slipDto.getDestinationBuildingNm();
			
			pdfContentByte.showText(destination);
		}
		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(40, 700);

		// 表示する文字列の設定
		pdfContentByte.showText("電話番号");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 700);

		// 表示する文字列の設定
		if (slipDto.getDestinationTel() != null)
			pdfContentByte.showText(slipDto.getDestinationTel());
		else 
			pdfContentByte.showText("");
		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(40, 680);

		// 表示する文字列の設定
		pdfContentByte.showText("運送会社");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 680);

		// 表示する文字列の設定
		if (slipDto.getTransportCorporationSystem() != null)
			pdfContentByte.showText(slipDto.getTransportCorporationSystem());
		else 
			pdfContentByte.showText("");
		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(40, 660);

		// 表示する文字列の設定
		pdfContentByte.showText("送り状種別");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(100, 660);

		// 表示する文字列の設定
		if (slipDto.getInvoiceClassification() != null)
			pdfContentByte.showText(slipDto.getInvoiceClassification());
		else
			pdfContentByte.showText("");

		
		// 表示位置の設定
		pdfContentByte.setTextMatrix(200, 660);

		// 表示する文字列の設定
		pdfContentByte.showText("運送日付");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(240, 660);

		// 表示する文字列の設定
		if (StringUtils.isNotEmpty(slipDto.getDestinationAppointDate())) {
			String date = slipDto.getDestinationAppointDate();
			String time = slipDto.getDestinationAppointTime();
			pdfContentByte.showText(date + " " + time);
		}
		else 
			pdfContentByte.showText("");
		
		
		{
			// Get SlipNo 
			
			String slipNo = "";
			if (slipDto.getSlipNo() != null)
				slipNo += slipDto.getSlipNo();
			
			String slipSystem = "";
			if (slipDto.getTransportCorporationSystem() != null)
				slipSystem += slipDto.getTransportCorporationSystem();
			
			System.out.println("slipSystem = " + slipSystem);

			boolean isCodaBar = false;
			if (slipSystem.equals("ヤマト運輸")) {
				slipNo = "a" + slipNo + "a";
				isCodaBar = true;
			}
			else if (slipSystem.equals("日本郵便")) {
				slipNo = "a" + slipNo + "a";
				isCodaBar = true;
			}
			else if (slipSystem.equals("西濃運輸")) {
				slipNo = "a" + slipNo + "a";
				isCodaBar = true;
			}
			else if (slipSystem.equals("佐川急便")) {
				slipNo = "d" + slipNo + "d";
				isCodaBar = true;
			}

			System.out.println("SlipNo = " + slipNo);
			
			com.itextpdf.text.Image image = null;
			
			if (!slipNo.equals("")) {
				if (isCodaBar == true)
					image = makeCodaBarCode(writer, slipNo);
				else 
					image = makeBarcode(writer, slipNo);
				
			    image.setAbsolutePosition(430, 770);
			    writer.getDirectContent().addImage(image, false);					    
			}
			
		}

		
		// create
		{
			PdfPTable pdfPTable = new PdfPTable(2);
	
			PdfPCell cell1_1 = new PdfPCell(new Paragraph("梱包", font));
			PdfPCell cell1_2 = new PdfPCell(new Paragraph("ピッキング", font));
	
			PdfPCell cell2_1 = new PdfPCell(new Paragraph("", font));
			PdfPCell cell2_2 = new PdfPCell(new Paragraph("", font));
	
			cell1_1.setHorizontalAlignment(1);
			cell1_2.setHorizontalAlignment(1);
	
			cell2_1.setHorizontalAlignment(1);
			cell2_1.setFixedHeight(40f);
			cell2_2.setHorizontalAlignment(1);
			cell2_2.setFixedHeight(40f);
	
			// 線消すメモ
			// cell1_1.setBorder(Rectangle.NO_BORDER);
			pdfPTable.addCell(cell1_1);
	
			pdfPTable.addCell(cell1_2);
	
			pdfPTable.addCell(cell2_1);
			pdfPTable.addCell(cell2_2);
	
			pdfPTable.setTotalWidth(120);
			int width[] = { 60, 60 };
			pdfPTable.setWidths(width);
			pdfPTable.writeSelectedRows(0, 2, 430, 720, writer.getDirectContent());
		}
		
		pdfContentByte.endText();

		int pageHeight = (int) document.getPageSize().getHeight();

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawLine(30, pageHeight - 650, 565, pageHeight - 650);
		pdfGraphics2D.dispose();

		/**
		 * ---------------------------------------------------一言メモ︓ START--------
		 * ---------------------------------------------------------
		 */
		// テキストの開始
		pdfContentByte.beginText();

		pdfContentByte.setTextMatrix(40, 630);

		// 表示する文字列の設定
		pdfContentByte.showText("一言メモ︓ ");
		
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		
		pdfContentByte.setTextMatrix(100, 630);

		int rowNum = 0;
		// 表示する文字列の設定
		if (slipDto.getSlipMemo() != null) {
			int totalLength = replaceNewline(slipDto.getSlipMemo()).length();
			if (totalLength % 42 > 0) {
				rowNum = totalLength / 42 + 1;
			}else {
				rowNum = totalLength / 42;
			}
			if (rowNum <= 1) {
				pdfContentByte.showText(replaceNewline(slipDto.getSlipMemo()));
			}
		}else {
			pdfContentByte.showText("");
		}
		
		// テキストの終了
		pdfContentByte.endText();
		
		if (rowNum > 1) {
			for(int i=0; i< rowNum; i++) {
				pdfContentByte.beginText();
				pdfContentByte.setFontAndSize(baseFont, 11);
				pdfContentByte.setTextMatrix(100, 630 - i * 13);
				if (i == rowNum -1) {
					pdfContentByte.showText(replaceNewline(slipDto.getSlipMemo()).substring(i*42, replaceNewline(slipDto.getSlipMemo()).length()));
				}else {
					pdfContentByte.showText(replaceNewline(slipDto.getSlipMemo()).substring(i*42, i*42 + 42));
				}
				pdfContentByte.endText();
			}
			rowNum = rowNum - 1;
		}

		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawLine(30, pageHeight - (620 - rowNum * 13), 565, pageHeight - (620 - rowNum * 13));
		pdfGraphics2D.dispose();

		int yPos = 620 - rowNum * 13;
		PdfPTable pdfPTable = new PdfPTable(5);

		// 表の要素(列タイトル)を作成
		PdfPCell cell1_1 = new PdfPCell(new Paragraph("品番", font));
		cell1_1.setGrayFill(0.6f); // セルを灰色に設定

		// 表の要素(列タイトル)を作成
		PdfPCell cell1_2 = new PdfPCell(new Paragraph("商品名", font));
		cell1_2.setGrayFill(0.6f); // セルを灰色に設定

		// 表の要素(列タイトル)を作成
		PdfPCell cell1_4 = new PdfPCell(new Paragraph("個数", font));
		cell1_4.setGrayFill(0.6f); // セルを灰色に設定

		PdfPCell cell1_5 = new PdfPCell(new Paragraph("バーコード", font));
		cell1_5.setGrayFill(0.6f); // セルを灰色に設定

		// 表の要素(列タイトル)を作成
		PdfPCell cell1_6 = new PdfPCell(new Paragraph("棚番号", font));
		cell1_6.setGrayFill(0.6f); // セルを灰色に設定

		cell1_1.setHorizontalAlignment(1);
		cell1_2.setHorizontalAlignment(1);
		cell1_4.setHorizontalAlignment(1);
		cell1_5.setHorizontalAlignment(1);
		cell1_6.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cell1_1);
		pdfPTable.addCell(cell1_2);
		pdfPTable.addCell(cell1_4);
		pdfPTable.addCell(cell1_5);
		pdfPTable.addCell(cell1_6);

		yPos -= 15;
		pdfPTable.setTotalWidth(535);
		int width[] = { 70, 220, 45, 150, 50 };
		pdfPTable.setWidths(width);

		int repaginationRow = 0;
		float pageHight = 0;
		rowNum = 0;
		
		//総描画行数
		int totalRowNum = 1;
		int itemNum = 0;
		int pageNumber = 1;
		
		int orgYPos = yPos;

		// merge same itemcode 
		List<ExtendSalesItemDTO> sortedPickItemList = new ArrayList<ExtendSalesItemDTO>();
		{
			for (int i=0; i<slipDto.getPickItemList().size(); i++) 
			{
				ExtendSalesItemDTO selectedItemDto = slipDto.getPickItemList().get(i); 
				
				if (selectedItemDto.getItemCode() == null)
					continue;
				
				boolean bFound = false;
				for (int j=0; j<sortedPickItemList.size(); j++) {
					ExtendSalesItemDTO targetItemDto = sortedPickItemList.get(j);
					
					if (targetItemDto.getItemCode() == null)
						continue;
					
					if (targetItemDto.getItemCode().equals(selectedItemDto.getItemCode()) &&
							targetItemDto.getItemNm().equals(selectedItemDto.getItemNm())) {
						targetItemDto.setOrderNum(targetItemDto.getOrderNum() + selectedItemDto.getOrderNum());
						bFound = true;
						break;
					}
				}
				
				if (bFound == false) 
				{
					ExtendSalesItemDTO newItemDto = new ExtendSalesItemDTO();
					newItemDto.setItemCode(selectedItemDto.getItemCode());
					newItemDto.setItemNm(selectedItemDto.getItemNm());
					newItemDto.setOrderNo(selectedItemDto.getOrderNo());
					newItemDto.setOrderNum(selectedItemDto.getOrderNum());
					newItemDto.setWarehouseNm(selectedItemDto.getWarehouseNm());
					newItemDto.setLocationNo(selectedItemDto.getLocationNo());
					
					sortedPickItemList.add(newItemDto);
				}
			}
		}
		
		for (rowNum = 0; rowNum < /*slipDto.getPickItemList().size()*/sortedPickItemList.size(); rowNum++) {

			//複数個の商品も1個につき1バーコードを出力するよう修正			
			for (itemNum = 0; itemNum < /*slipDto.getPickItemList().get(rowNum).getOrderNum()*/sortedPickItemList.get(rowNum).getOrderNum(); itemNum++) {

				addNewRow(writer, pdfPTable, font, rowNum, /*slipDto*/sortedPickItemList.get(rowNum));

				if (pdfPTable.calculateHeights() >= orgYPos - 20 + (pageNumber -1) * (PAGE_HEIGHT - 70)) {
					System.out.print("Render before new page: row(start, end) = " + repaginationRow + " : " + totalRowNum + ", yPos = " + yPos + "\n");		
					pdfPTable.writeSelectedRows(0, 5, repaginationRow, totalRowNum, 30, yPos, writer.getDirectContent());
					
					// draw page footer 
					{
						// Adding image to the document footer       
						String relativeWebPath = "/img/pagefooter.png";
						Image image = loadImageFromWebContent(request, relativeWebPath);
						if (image != null) {
						    image.setAbsolutePosition(35, 10);
						    writer.getDirectContent().addImage(image, false);
						}
					}

					document.newPage();

					yPos = PAGE_HEIGHT - 30;
					repaginationRow = totalRowNum;
					pageNumber++;		
					
					
				}
				totalRowNum ++;

// --------------------------------------------------				
// Comment : Original Source code
// --------------------------------------------------
//				if (pdfPTable.calculateHeights() > yPos + 10
//						&& repaginationRow == 0) {
//					/** 大枠の線から10px上を越えていたらその行削除し次ページに表示 */
//					pageHight = pdfPTable.calculateHeights();
//					totalRowNum -= 1;
//					pdfPTable.writeSelectedRows(0, 5, 0, totalRowNum, 30, yPos + 10,
//							writer.getDirectContent());
//					repaginationRow = totalRowNum;
//
//				} else if (pdfPTable.calculateHeights() - pageHight > 750) {
//					/** 行の高さがページ超えてくると無限ループ発生するはずなのであとで対処 */
//					document.newPage();
//					pageHight = pdfPTable.calculateHeights();
//					/** 大枠の線から10px上を越えていたらその行削除し次ページに表示 */
//					pdfPTable.writeSelectedRows(0, 5, repaginationRow,
//							totalRowNum, 30, 800, writer.getDirectContent());
//					repaginationRow = totalRowNum;
//				}
//
//				totalRowNum += 2;
			}
		}

		// Here, rendering remain rows
		// 		 and adding space cells for it
//		while (pdfPTable.calculateHeights() < orgYPos - 20 - 50/* default cell height*/ + (pageNumber -1) * (PAGE_HEIGHT - 70)) 
//		{
//			addNewRow(writer, pdfPTable, font, 0, null);
//
//			System.out.print("BLANK CELL : PdfTable.calculateHeight() = " + pdfPTable.calculateHeights() + 
//					 "New Page height :" + (orgYPos - 20 - 50/* default cell height*/ + (pageNumber -1) * (PAGE_HEIGHT - 70)) + " \n");
//			System.out.print("BLANK CELL : YPOS = " + yPos + 
//					 " repaginationRow = " + repaginationRow + 
//					 " totalRowNum = " + totalRowNum + 
//					 " pageNumber = " + pageNumber + "\n");
//			
//			totalRowNum++;
//		}
		
		System.out.print("Render : row(start, end) = " + repaginationRow + " : " + totalRowNum + ", yPos = " + yPos + "\n");		
		pdfPTable.writeSelectedRows(0, 5, repaginationRow, totalRowNum, 30, yPos, writer.getDirectContent());
		
//		if (totalRowNum > repaginationRow && repaginationRow == 0) {
//			pdfPTable.writeSelectedRows(0, 5, 0, -1, 30, yPos + 10,
//					writer.getDirectContent());
//		} else if (totalRowNum > repaginationRow) {
//			document.newPage();
//			pdfPTable.writeSelectedRows(0, 5, repaginationRow, -1, 30, 800,
//					writer.getDirectContent());
//
//		}

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
			Font font, BaseFont baseFont, ExtendSalesSlipDTO slipDto)
			throws Exception {
//TODO
		PdfContentByte pdfContentByte = writer.getDirectContent();

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawRect(30, 30, 535, 782);
		pdfGraphics2D.drawLine(30, 60, 565, 60);
		pdfGraphics2D.dispose();

		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 18);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(230, 790);

		// 表示する文字列の設定
		pdfContentByte.showText("お買い上げ明細書");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(50, 760);

		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getOrderFullNm() + " 様");
		// 表示位置の設定
		pdfContentByte.setTextMatrix(50, 730);
		// 表示する文字列の設定
		pdfContentByte.showText("この度は当店のご利用、誠にありがとうございました。");
		pdfContentByte.setTextMatrix(50, 719);
		pdfContentByte.showText("下記のとおり商品をご納品いたします。");
		pdfContentByte.setTextMatrix(50, 708);
		pdfContentByte.showText("ご確認いただきますよう、お願い申し上げます。");

		// 表示位置の設定
		pdfContentByte.setTextMatrix(350, 760);

		// 表示する文字列の設定
		pdfContentByte.showText("受注番号：" + slipDto.getOrderNo());
		// テキストの終了
		pdfContentByte.endText();

		SaleDisplayService saleDisplayService = new SaleDisplayService();

		StoreDTO storeDTO = new StoreDTO();
		storeDTO = saleDisplayService.selectShopInfo(
				slipDto.getSysCorporationId(), slipDto.getSysChannelId());

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

		if (StringUtils.equals(storeDTO.getCorporationNmDispFlg(), "1")) {
			String corporationNm = storeDTO.getCorporationNm();
			if (StringUtils.equals(storeDTO.getNameHeaderDispFlg(), "1")) {
				corporationNm = "株式会社 " + corporationNm;
			}
			cell = new PdfPCell(new Paragraph(corporationNm, font));
			cell.setBorder(Rectangle.NO_BORDER);
			CorporationTable.addCell(cell);
		}

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

		cell = new PdfPCell(new Paragraph("　", font));
		cell.setBorder(Rectangle.NO_BORDER);
		CorporationTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("ご注文日：" + slipDto.getOrderDate(),
				font));
		cell.setHorizontalAlignment(2);
		cell.setBorder(Rectangle.NO_BORDER);
		CorporationTable.addCell(cell);

		CorporationTable.setTotalWidth(195);

		float CorporationTableYPos = 745;
//TODO
		CorporationTable.writeSelectedRows(0, -1, 350, CorporationTableYPos,
				writer.getDirectContent());

		float CorporationTableHight = CorporationTable.calculateHeights();

		return CorporationTableYPos - CorporationTableHight;

	}

	private static float orderDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendSalesSlipDTO slipDto,
			float orderCurrentHeight) throws Exception {

		PdfPTable orderDetailTable = new PdfPTable(1);
		PdfPCell cell = null;
		// 表の要素(列タイトル)を作成
		cell = new PdfPCell(new Paragraph("お買い上げ明細", font));
		cell.setGrayFill(0.8f); // セルを灰色に設定
		// 表の要素を表に追加する
		orderDetailTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("〒" + slipDto.getDestinationZip()
				+ " " + slipDto.getDestinationPrefectures()
				+ slipDto.getDestinationMunicipality()
				+ slipDto.getDestinationAddress()
				+ slipDto.getDestinationBuildingNm(), font));
		cell.setBorder(Rectangle.NO_BORDER);
		orderDetailTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(
				slipDto.getDestinationFullNm() + " 様", font));
		cell.setBorder(Rectangle.NO_BORDER);
		orderDetailTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("配送方法： "
				+ slipDto.getInvoiceClassification(), font));
		cell.setBorder(Rectangle.NO_BORDER);
		orderDetailTable.addCell(cell);

		orderDetailTable.setTotalWidth(495);
		// pdfPTable.setWidths(width);
		orderDetailTable.writeSelectedRows(0, -1, 50, orderCurrentHeight,
				writer.getDirectContent());

		float orderDetailTableHight = orderDetailTable.calculateHeights();

		return orderCurrentHeight - orderDetailTableHight;

	}

	private static void orderItemDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendSalesSlipDTO slipDto,
			float orderCurrentHeight) throws Exception {
		PdfPTable pdfPTable = new PdfPTable(4);
		pdfPTable.setTotalWidth(495);
		int width[] = { 297, 74, 50, 74 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		cellItemNmHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellUnitPriceHeader = new PdfPCell(new Paragraph("単価", font));
		cellUnitPriceHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		cellQuantityHeader.setGrayFill(0.8f); // セルを灰色に設定
		// 表の要素(列タイトル)を作成
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("価格", font));
		cellPriceHeader.setGrayFill(0.8f); // セルを灰色に設定

		cellUnitPriceHeader.setHorizontalAlignment(1);
		cellQuantityHeader.setHorizontalAlignment(1);
		cellPriceHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
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

			cellUnitPrice.setHorizontalAlignment(2);
			cellQuantity.setHorizontalAlignment(2);
			cellPrice.setHorizontalAlignment(2);

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
				pdfPTable.writeSelectedRows(0, 4, 0, rowNum - 1, 50,
						orderCurrentHeight, writer.getDirectContent());
				repaginationRow = rowNum;

			} else if (pdfPTable.calculateHeights() - pageHight > 750) {
				/** 行の高さがページ超えてくると無限ループ発生するはずなのであとで対処 */
				newPage(document, writer);
				pageHight = pdfPTable.calculateHeights();
				/** 大枠の線から10px上を越えていたらその行削除し次ページに表示 */
				// pdfPTable.deleteRow(rowNum);
				// rowNum--;
				pdfPTable.writeSelectedRows(0, 4, repaginationRow - 1,
						rowNum - 1, 50, 800, writer.getDirectContent());
				repaginationRow = rowNum;
			}
		}
		if (rowNum > repaginationRow && repaginationRow == 0) {
			pdfPTable.writeSelectedRows(0, 4, 0, -1, 50, orderCurrentHeight,
					writer.getDirectContent());
		} else if (rowNum > repaginationRow) {
			newPage(document, writer);
			pdfPTable.writeSelectedRows(0, 4, repaginationRow - 1, rowNum - 1,
					50, 800, writer.getDirectContent());
		}
		/** 多分、計算式違う、下の計算から、テーブルを記述始めているyposから以下の値を引かないと欲しい値が算出されない。 */
		float height = pdfPTable.calculateHeights() - pageHight;

		PdfPTable itemTotalPriceTable;
		float yPos = 0.0f;

		// 代金引換または代金引換(カード)の場合、納品書に手数料の出力を行う
		if (slipDto.getCodCommission() > 0) {
			itemTotalPriceTable = new PdfPTable(3);

			// 表の要素(列タイトル)を作成
			PdfPCell cellSumItemPriceHeader = new PdfPCell(new Paragraph(
					"商品合計(税込)", font));
			cellSumItemPriceHeader.setGrayFill(0.8f); // セルを灰色に設定
			PdfPCell cellpostageHeader = new PdfPCell(new Paragraph("送料", font));
			cellpostageHeader.setGrayFill(0.8f); // セルを灰色に設定
			PdfPCell cellCommissionHeader = new PdfPCell(new Paragraph("代引き手数料", font));
			cellCommissionHeader.setGrayFill(0.8f); // セルを灰色に設定

			cellSumItemPriceHeader.setHorizontalAlignment(1);
			cellpostageHeader.setHorizontalAlignment(1);
			cellCommissionHeader.setHorizontalAlignment(1);

			// 表の要素を表に追加する
			itemTotalPriceTable.addCell(cellSumItemPriceHeader);
			itemTotalPriceTable.addCell(cellpostageHeader);
			itemTotalPriceTable.addCell(cellCommissionHeader);

			// 外税の場合、税追加
			if (StringUtils.equals(slipDto.getTaxClass(), "2")) {
				totalPrice += slipDto.getTax();
			}

			PdfPCell cellSumItemPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(totalPrice)) + "円",
					font));
			PdfPCell cellpostage = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getPostage()))
							+ "円", font));
			PdfPCell cellCommission = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getCodCommission()))
							+ "円", font));

			cellSumItemPrice.setHorizontalAlignment(2);
			cellpostage.setHorizontalAlignment(2);
			cellCommission.setHorizontalAlignment(2);

			itemTotalPriceTable.addCell(cellSumItemPrice);
			itemTotalPriceTable.addCell(cellpostage);
			itemTotalPriceTable.addCell(cellCommission);

			itemTotalPriceTable.setTotalWidth(170);
			int itemTotalPriceTableWidth[] = { 70, 40, 60 };
			itemTotalPriceTable.setWidths(itemTotalPriceTableWidth);

			yPos = 580 - (height + 10);
			//cellSumItemPriceHeaderを次ページに表示するか判定
			if (height > 400) {
				newPage(document, writer);
				yPos = 800;
			}


			itemTotalPriceTable.writeSelectedRows(0, -1, 375, yPos,
					writer.getDirectContent());

			//代引きの場合、合計金額に代引き手数料を加算
			totalPrice = totalPrice + slipDto.getCodCommission();

			// 代金引換以外の場合
		} else {
			itemTotalPriceTable = new PdfPTable(2);

			// 表の要素(列タイトル)を作成
			PdfPCell cellSumItemPriceHeader = new PdfPCell(new Paragraph(
					"商品合計(税込)", font));
			cellSumItemPriceHeader.setGrayFill(0.8f); // セルを灰色に設定
			PdfPCell cellpostageHeader = new PdfPCell(new Paragraph("送料", font));
			cellpostageHeader.setGrayFill(0.8f); // セルを灰色に設定

			cellSumItemPriceHeader.setHorizontalAlignment(1);
			cellpostageHeader.setHorizontalAlignment(1);

			// 表の要素を表に追加する
			itemTotalPriceTable.addCell(cellSumItemPriceHeader);
			itemTotalPriceTable.addCell(cellpostageHeader);

			// 外税の場合、税追加
			if (StringUtils.equals(slipDto.getTaxClass(), "2")) {
				totalPrice += slipDto.getTax();
			}

			PdfPCell cellSumItemPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(totalPrice)) + "円",
					font));
			PdfPCell cellpostage = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getPostage()))
							+ "円", font));

			cellSumItemPrice.setHorizontalAlignment(2);
			cellpostage.setHorizontalAlignment(2);

			itemTotalPriceTable.addCell(cellSumItemPrice);
			itemTotalPriceTable.addCell(cellpostage);

			itemTotalPriceTable.setTotalWidth(110);
			int itemTotalPriceTableWidth[] = { 70, 40 };
			itemTotalPriceTable.setWidths(itemTotalPriceTableWidth);


			yPos = 580 - (height + 10);
			//cellSumItemPriceHeaderを次ページに表示するか判定
			if (height > 400) {
				newPage(document, writer);
				yPos = 800;
			}


			itemTotalPriceTable.writeSelectedRows(0, -1, 435, yPos,
					writer.getDirectContent());
		}


		PdfContentByte pdfContentByte = writer.getDirectContent();
		// テキストの開始
		pdfContentByte.beginText();
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(50, yPos - 40);
		// 表示する文字列の設定
		pdfContentByte.showText("お買い上げ総合計");
		// 表示位置の設定
		pdfContentByte.setTextMatrix(520, yPos - 105);
		// 表示する文字列の設定
		pdfContentByte.showText("以上");
		// テキストの終了
		pdfContentByte.endText();

		PdfPTable finalItemTotalPriceTable = new PdfPTable(3);

		// 表の要素(列タイトル)を作成
		PdfPCell cellTotalPriceHeader = new PdfPCell(new Paragraph("合計", font));
		cellTotalPriceHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellUsedPointHeader = new PdfPCell(new Paragraph("利用ポイント",
				font));
		cellUsedPointHeader.setGrayFill(0.8f); // セルを灰色に設定
		PdfPCell cellTotalSumPriceHeader = new PdfPCell(new Paragraph("総合計",
				font));
		cellTotalSumPriceHeader.setGrayFill(0.8f); // セルを灰色に設定

		cellTotalPriceHeader.setHorizontalAlignment(1);
		cellUsedPointHeader.setHorizontalAlignment(1);
		cellTotalSumPriceHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		finalItemTotalPriceTable.addCell(cellTotalPriceHeader);
		finalItemTotalPriceTable.addCell(cellUsedPointHeader);
		finalItemTotalPriceTable.addCell(cellTotalSumPriceHeader);

		PdfPCell cellTotalPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(totalPrice
						+ slipDto.getPostage()))
						+ "円", font));
		PdfPCell cellUsedPoint = new PdfPCell(new Paragraph(
				slipDto.getUsedPoint() + "円", font));
		PdfPCell cellTotalSumPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(totalPrice
						+ slipDto.getPostage() - slipDto.getUsedPoint()))
						+ "円", font));

		cellTotalPrice.setHorizontalAlignment(2);
		cellUsedPoint.setHorizontalAlignment(2);
		cellTotalSumPrice.setHorizontalAlignment(2);

		finalItemTotalPriceTable.addCell(cellTotalPrice);
		finalItemTotalPriceTable.addCell(cellUsedPoint);
		finalItemTotalPriceTable.addCell(cellTotalSumPrice);

		finalItemTotalPriceTable.setTotalWidth(160);
		int finalItemTotalPriceTableWidth[] = { 50, 60, 50 };
		finalItemTotalPriceTable.setWidths(finalItemTotalPriceTableWidth);
		finalItemTotalPriceTable.writeSelectedRows(0, -1, 385, yPos - 60,
				writer.getDirectContent());

		PdfPTable pdfRemarksTable = new PdfPTable(1);
		pdfRemarksTable.setTotalWidth(495);

		// int remarksTablewidth[] = {495};
		// pdfPTable.setWidths(remarksTablewidth);
		if (StringUtils.isEmpty(slipDto.getDeliveryRemarks())) {
			slipDto.setDeliveryRemarks(StringUtils.EMPTY);
		}
		PdfPCell cellIRemaks = new PdfPCell(new Paragraph("備考："
				+ slipDto.getDeliveryRemarks(), font));
		cellIRemaks.setBorder(Rectangle.NO_BORDER);
		pdfRemarksTable.addCell(cellIRemaks);
		pdfRemarksTable.writeSelectedRows(0, -1, 50, yPos - 125,
				writer.getDirectContent());
	}

	public void totalPickList(HttpServletResponse response,
			List<ExtendSalesSlipDTO> salesSlipList) throws Exception {

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

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		List<ExtendSalesItemDTO> pickList = new ArrayList<>();
		pickList = saleDisplayService.getTotalPickItemList(salesSlipList);

		// 全て楽天倉庫から出庫する商品だった場合は表を出力しない。
		if (!pickList.isEmpty()) {

			int itemRowCount = 1;
			int totalItemNum = 0;
			for (ExtendSalesItemDTO itemDto : pickList) {
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

		}

		document.add(pdfPTable);
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

	public int[] countKtsStocks(List<ExtendSalesSlipDTO> pickList) {

		int ktsStockCount = 0;

		int totalCount = 0;
		if (pickList != null) {
			totalCount = pickList.size();

			for (ExtendSalesSlipDTO slipDto : pickList) {

				// KTS倉庫から出庫予定の商品だけピッキングリストと納品書を出力する。
				if (slipDto.getRslLeaveFlag() == null || StringUtils.equals(slipDto.getRslLeaveFlag(), "0")) {
					ktsStockCount++;
				}
			}
		}

		//先頭要素：KTS伝票数、後要素：RSL伝票数
		int[] slipCountArray = new int[2];

		slipCountArray[0] = ktsStockCount;
		slipCountArray[1] = totalCount - ktsStockCount;
		return slipCountArray;
	}
}