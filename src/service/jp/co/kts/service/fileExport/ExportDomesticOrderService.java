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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.BaseColor;
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
import jp.co.kts.app.common.entity.DomesticCsvImportDTO;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderSlipDTO;
import jp.co.kts.dao.fileImport.DomesticCsvImportDAO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;

public class ExportDomesticOrderService {

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
		ExtendDomesticOrderSlipDTO dto) throws Exception {

		Date date = new Date();

		String fname = "注文書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("orderAcceptance.pdf"));

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10);

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min, "UniJIS-UCS2-H", BaseFont.EMBEDDED);

		document.open();

		exportOrderAcceptance(document, writer, baseFont, font, date, dto);
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
	public void exportOrderAcceptance(Document document, PdfWriter writer, BaseFont baseFont, Font font, Date date,
			ExtendDomesticOrderSlipDTO slipDto) throws Exception {

		/** 注文書 */
		//ヘッダー
		orderAcceptanceHeader(document, writer, baseFont, date, slipDto);

		// 商品テーブルの最終描画Yポジション
		float tableLastDrawingYPosition = 0;
		/** 商品一覧 */
		tableLastDrawingYPosition = orderItemDetail(document, writer, font, baseFont, slipDto, 650);

		//備考欄改ページ時に使用するマップ。表示した商品の備考のインデックスを保存したりする。
		Map<String, Integer> orderRemarksMap = new LinkedHashMap<>();
		//備考を全て表示したか判別するキー
		orderRemarksMap.put("isRemarksDisplayAll", 0);
		//改ページが必要か判別するキー
		orderRemarksMap.put("isNewPage", 0);
		//備考欄をそのページに表示しきれなかった場合の最大表示インデックス
		orderRemarksMap.put("itemRowMaxIndex", 0);
		//備考を続きから表示する際の目印
		orderRemarksMap.put("rowNum", 0);
		//備考欄を表示するY地点を保存するキー
		orderRemarksMap.put("remarksDisplayStartPointY", 0);
		//備考欄を全て表示するまで繰り返す
		while (orderRemarksMap.get("isRemarksDisplayAll") != 1) {
			//備考欄表示
			orderAcceptance(document, writer, font, baseFont, slipDto, tableLastDrawingYPosition, orderRemarksMap);
			//改ページが必要なとき改ページ
			if (orderRemarksMap.get("isNewPage") == 1) {
				document.newPage();
			}
		}

		//直送先以下
		directDeliveryTargetOutput(document, writer, font, baseFont, slipDto);
		/** 注文者情報設定 */
//		orderAcceptance(document, writer, font, baseFont, slipDto, tableLastDrawingYPosition);


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
			BaseFont baseFont, Date date, ExtendDomesticOrderSlipDTO slipDto) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 25);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(260, 800);
		// 表示する文字列の設定
		pdfContentByte.showText("注文書");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(430, 800);
		// 表示する文字列の設定
		pdfContentByte.showText("注文書No.");
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		//注文書No取得
		String purchaseOrderNoString = "";
		purchaseOrderNoString += "KS" + slipDto.getPurchaseOrderNoPdf();
		//注文書No表示
		showTextArea(document, writer, baseFont,
				purchaseOrderNoString, ",", 490, 800, 5);

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(430, 785);
		// 年月日の設定
		SimpleDateFormat orderAcceptanceDate = new SimpleDateFormat("yyyy年MM月dd日", Locale.JAPAN);
		pdfContentByte.showText(orderAcceptanceDate.format(date));

		/**
		 * ---------------------------------------------------注文者情報START--------
		 * ---------------------------------------------------------
		 */
		// 595
		// int pageWidth = (int)document.getPageSize().getWidth();
		// TODO A4の縦の高さ 842
		Integer pageHeight = (int)document.getPageSize().getHeight();


		//【左上】問屋情報表示
		pdfContentByte.setFontAndSize(baseFont, 17);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(50, 765);
		// 表示する文字列の設定
		pdfContentByte.showText(slipDto.getDomesticOrderItemList().get(0).getWholsesalerNm());


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 17);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(50, 710);
		// 表示する文字列の設定
		pdfContentByte.showText("御担当者");
		//文字数によって文字サイズ変更


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 15);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(275, 765);
		// 表示する文字列の設定
		pdfContentByte.showText("御中");


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 15);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(165, 710);
		// 表示する文字列の設定
		pdfContentByte.showText("様");


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 670);
		// 表示する文字列の設定
		pdfContentByte.showText("注文お願いします。");


		//【右上】法人情報設定

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(390, 755);
		// 表示する文字列の設定
		pdfContentByte.showText("Ｋ・Ｔ・Ｓ 業務部");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(360, 740);
		// 表示する文字列の設定
		pdfContentByte.showText("埼玉県川口市南鳩ヶ谷市1-25-3");
		// 表示位置の設定
		pdfContentByte.setTextMatrix(355, 725);
		// 表示する文字列の設定
		pdfContentByte.showText("株式会社 カインドテクノストラクチャー");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(355, 705);
		// 表示する文字列の設定
		pdfContentByte.showText("TEL 048-285-8941  FAX 048-285-8948");


		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);

		// 表示位置の設定
		pdfContentByte.setTextMatrix(400, 670);

		// 表示する文字列の設定
		pdfContentByte.showText("( " + " 担当者 ： " + slipDto.getNoteTurn() + " )");

		// テキストの終了
		pdfContentByte.endText();

		//ボーダーライン設定
		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.drawLine(35, pageHeight - 760, 300, pageHeight - 760);
		pdfGraphics2D.dispose();

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics3D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics3D.setColor(new Color(0, 0, 0));
		pdfGraphics3D.drawLine(35, pageHeight - 700, 180, pageHeight - 700);
		pdfGraphics3D.dispose();

	}

	/**
	 * 注文書
	 * 注文者情報設定
	 *
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @param tableLastDrawingYPosition
	 * @param orderRemarksMap
	 * @throws Exception
	 */
	private static void orderAcceptance(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendDomesticOrderSlipDTO slipDto, float tableLastDrawingYPosition, Map<String, Integer> orderRemarksMap)
			throws Exception {
		PdfContentByte pdfContentByte = writer.getDirectContent();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);


		/*------------ 中段商品備考欄表示処理 ここから ----------------------------------------------------------------------------------------------------------*/
		//TODO 中段商品備考表示処理

		//プラットフォームに適した改行コードを取得
		String lineSep = System.getProperty("line.separator");

		int TABLE_COLS = 1;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		//テーブルにおけるセルの最大表示数
		int maxRow = 80;

		//空のセルを用意
		PdfPCell cellItemOrderRemarks = new PdfPCell();
		String orderItemRemarks = "";

		//「発送後、配送業者、送り状番号をお知らせ下さいませ。宜しくお願いします。」の文言を開始する位置。備考欄の常に上に表示する。
		int noteStartPointY = 0;
		//備考欄終了位置。これが222を下回ると、直送先を表示するのに改ページが必要になる。 ※処理最後の判別で使用する
		float remarksEndPoint = 222;

		//備考欄のみのリストを作成する処理
		List<String> listRemarksList = new ArrayList<>();
		DomesticCsvImportDAO daoo = new DomesticCsvImportDAO();
		for (DomesticOrderListDTO dto : slipDto.getDomesticOrderItemList()) {
			//空っぽ、またはnullはこの時点で除外する。
//			if (dto.getListRemarks() == null || dto.getListRemarks().isEmpty()) {
			DomesticCsvImportDTO data = new DomesticCsvImportDTO();
			data = daoo.getDomesticCsvdataFromDomesticimportId(slipDto.getSysDomesticImportId());
				if(!StringUtils.isEmpty(dto.getOrderRemarks()) || 
					!StringUtils.isEmpty(data.getDestinationAppointDate()) || 
					!StringUtils.isEmpty(data.getDestinationAppointTime()) ||
					!StringUtils.isEmpty(data.getSenderMemo())) {
					
					listRemarksList.add("[管理品番:] " + dto.getManagementCode() + " : ");
					if(!StringUtils.isEmpty(dto.getOrderRemarks())) {
						listRemarksList.add("[備考:] " + dto.getOrderRemarks());
					}
					if(!StringUtils.isEmpty(data.getDestinationAppointDate()) || !StringUtils.isEmpty(data.getDestinationAppointTime())) 
					{
						listRemarksList.add("[お届け指定日:] " + data.getDestinationAppointDate() + data.getDestinationAppointTime()); 
					}
					if(!StringUtils.isEmpty(data.getSenderMemo())) {
						listRemarksList.add("[一言メモ（お届け先）:] " + data.getSenderMemo());
					}
					listRemarksList.add("\n\r sadfasfasdfasdfdddddddddddddd \n\r ddddddddddddddddddddddddddddd \n\r eeeeeeeeeeeeeeeeeeeeeeeeeee");
					listRemarksList.add("\n");
				}
//				continue;
//				
//			}
//			listRemarksList.add(dto.getListRemarks());
		}

		//備考欄開始Y地点が760(改ページ後には備考欄以下のみ表示)の場合、商品終了Y地点を760とする。
		if ( ((float) orderRemarksMap.get("remarksDisplayStartPointY")) >= 760) {
			tableLastDrawingYPosition = orderRemarksMap.get("remarksDisplayStartPointY");
		}

		//商品テーブル終了Y地点の場所によって備考欄の基準が変わる。800未満の時、1ページ目または改ページ先にも商品テーブルが存在する。
		if (tableLastDrawingYPosition < 800) {

			//商品テーブルの終了位置が382より上かつ662より下の時、備考欄は362の位置から表示される。
			if (tableLastDrawingYPosition > 382 && tableLastDrawingYPosition < 662) {
				tableLastDrawingYPosition = 382;
			}

			//備考欄と直送先の間(20px)を考慮してあらかじめ引いておく
			tableLastDrawingYPosition -= 20;
			
			float lastPositionYTemp = tableLastDrawingYPosition;
			//備考欄がそのページに収まりきるかを調べる
			for (int i = 0; i < listRemarksList.size(); i++) {
				//備考欄のフォントサイズは10px。備考欄の文字数が50文字を超えると改行され、行数が増えるため計算する。
				lastPositionYTemp -= 10  * stringIsmanyLinesHaveIsCheck(listRemarksList.get(i));
				//商品テーブルの終了地点 - 20地点が232を下回ると直送先に被る
					if (lastPositionYTemp < 232) {
					//備考欄表示開始Y地点を760に設定し、改ページする
					orderRemarksMap.put("remarksDisplayStartPointY", 800);
					orderRemarksMap.put("isNewPage", 1);
					return;
				}
			}
			
			//直送先に被らない場合は備考欄を表示する。	
			for (int i = 0; i < listRemarksList.size() ; i++) {
				//商品の備考をセット
				orderItemRemarks += " " + listRemarksList.get(i) + lineSep;
			}
			//セルに備考欄を設定
			cellItemOrderRemarks = new PdfPCell(new Paragraph(orderItemRemarks, font));
			//セル内の表示位置を設定(左寄せ)
			cellItemOrderRemarks.setHorizontalAlignment(0);
			//セルの最小高さを設定 (備考欄が少ない時、備考欄枠がせばまってしまい余計な空白が生まれてしまうので最小高さを設定した)
			cellItemOrderRemarks.setMinimumHeight(130);
			//セルをテーブルに追加
			pdfPTable.addCell(cellItemOrderRemarks);
			//備考欄テーブルを描画
			
			pdfPTable.writeSelectedRows(0, TABLE_COLS,  0, maxRow + 2, 30, /*362*/ tableLastDrawingYPosition, writer.getDirectContent());	
			//備考欄の表示が終わったら注意書き文言の開始位置を設定 ※備考欄の表示位置 + 10pxの位置
			noteStartPointY = 362 + 10;
			

		//商品テーブル終了Y地点の場所によって備考欄の基準が変わる。760以上の時、改ページ先には商品テーブルは存在せず備考欄以下のみである。
		} else /* if (tableLastDrawingYPosition >= 760) */ {
			//続きから表示する際のインデックスを用意するためにカウンタ変数はここで用意した。
			int i;
			//備考欄の続きインデックスがある場合はそれから、無い場合は0から始める。
			if (orderRemarksMap.get("rowNum") != 0) {
				i = orderRemarksMap.get("rowNum");
			} else {
				i = 0;
			}

			//備考欄がそのページに収まりきるかを調べる
			for (; i < listRemarksList.size(); i++) {
				//備考欄のフォントサイズは10px。備考欄の文字数が50文字を超えると改行され、行数が増えるため計算する。
				tableLastDrawingYPosition -= 10 * stringIsmanyLinesHaveIsCheck(listRemarksList.get(i));

				//Y地点が232を下回ると直送先に被る
				if (tableLastDrawingYPosition < 232) {

					float lastPositionYTemp = tableLastDrawingYPosition;
					//改ページ後で直送先に被った場合はそのページの最後まで表示するため引き続き計算
					for (int j = i + 1 ; j < listRemarksList.size(); j++) {
						//備考欄のフォントサイズは10px。備考欄の文字数が50文字を超えると改行され、行数が増えるため計算する。
						lastPositionYTemp -= 10 * stringIsmanyLinesHaveIsCheck(listRemarksList.get(j));

						//62がそのページいっぱいに備考欄を表示するY地点の限界
						if (lastPositionYTemp <= 62) {
							//改ページ後の備考欄開始Y地点、ページ内での備考欄の最大表示数、改ページ先での備考欄の続きから表示するインデックス、改ページフラグを設定
							orderRemarksMap.put("remarksDisplayStartPointY", 760);
							orderRemarksMap.put("itemRowMaxIndex", j);
							orderRemarksMap.put("rowNum", j + 1);
							orderRemarksMap.put("isNewPage", 1);

							//そのページでの備考欄最大表示数まで備考欄を表示する。
							for (i = 0; i <= orderRemarksMap.get("itemRowMaxIndex"); i++) {
								//商品の備考をセット
								orderItemRemarks += " " + listRemarksList.get(i) + lineSep;
							}
							//セルに備考欄を設定
							cellItemOrderRemarks = new PdfPCell(new Paragraph(orderItemRemarks, font));
							//セル内の表示位置を設定(左寄せ)
							cellItemOrderRemarks.setHorizontalAlignment(0);
							//セルの最小高さを設定 (備考欄が少ない時、備考欄枠がせばまってしまい余計な空白が生まれてしまうので最小高さを設定した)
							cellItemOrderRemarks.setMinimumHeight(130);
							//セルをテーブルに追加
							pdfPTable.addCell(cellItemOrderRemarks);
							//備考欄テーブルを描画
							pdfPTable.writeSelectedRows(0, TABLE_COLS,  0, maxRow + 2, 30, 800, writer.getDirectContent());

							//itemRowMaxIndexが備考欄リストサイズ - 1と同じ時、そのページで備考欄が全て表示されている
							if (listRemarksList.size() - 1 == orderRemarksMap.get("itemRowMaxIndex")) {
								//直送先の表示用に改ページは必要だが、備考欄表示の繰り返しは必要ない
								orderRemarksMap.put("isNewPage", 1);
								orderRemarksMap.put("isRemarksDisplayAll", 1);
								//見積上の注意書き表示処理
								showNote(pdfContentByte, baseFont, noteStartPointY, slipDto.getSysWarehouseId());
							}
							return;
						}
					}
				}
			}

			//表示できる場合はリストのサイズとする
			orderRemarksMap.put("itemRowMaxIndex", listRemarksList.size());

			//備考欄の続きインデックスがある場合はそれから、無い場合は0から始める。
			if (orderRemarksMap.get("rowNum") != 0) {
				i = orderRemarksMap.get("rowNum");
			} else {
				i = 0;
			}

			//直送先に被らない場合は備考欄を表示する
			for (; i < orderRemarksMap.get("itemRowMaxIndex"); i++) {
				//商品の備考をセット
				orderItemRemarks += " " + listRemarksList.get(i) + lineSep;
			}
			//セルに備考欄を設定
			cellItemOrderRemarks = new PdfPCell(new Paragraph(orderItemRemarks, font));
			//セル内の表示位置を設定(左寄せ)
			cellItemOrderRemarks.setHorizontalAlignment(0);
			//セルの最小高さを設定 (備考欄が少ない時、備考欄枠がせばまってしまい余計な空白が生まれてしまうので最小高さを設定した)
			cellItemOrderRemarks.setMinimumHeight(130);
			//セルをテーブルに追加
			pdfPTable.addCell(cellItemOrderRemarks);

			if (orderRemarksMap.get("remarksDisplayStartPointY") != 0) {
				tableLastDrawingYPosition = orderRemarksMap.get("remarksDisplayStartPointY");
			} else {
				tableLastDrawingYPosition = 760;
			}

			//直送先+20の位置に備考欄の終了地点がくるように、備考欄の開始地点を求める
			int remarksFieldDisplayPosition = displayLocationCalculation(tableLastDrawingYPosition, pdfPTable.getTotalHeight());

			//備考欄テーブルを描画
			remarksEndPoint = pdfPTable.writeSelectedRows(0, TABLE_COLS,
																							0, maxRow + 2,
																							30, remarksFieldDisplayPosition,
																							writer.getDirectContent());

			//備考欄の表示が終わったら注意書き文言の開始位置を設定 ※備考欄の表示位置 + 10pxの位置
			noteStartPointY = remarksFieldDisplayPosition + 10;

		}

		/*------------ 中段商品備考欄表示処理 ここまで ----------------------------------------------------------------------------------------------------------*/

		//見積上の注意書き表示処理
		showNote(pdfContentByte, baseFont, noteStartPointY, slipDto.getSysWarehouseId());

		//備考欄の終了地点が直送先と被る場合は改ページをして終わる
		if (remarksEndPoint < 222.0) {
			//直送先の表示用に改ページは必要だが、備考欄表示の繰り返しは必要ない
			orderRemarksMap.put("isNewPage", 1);
			orderRemarksMap.put("isRemarksDisplayAll", 1);
			return;
		}

		//備考欄を最後まで表示できたら改ページしないよう設定する
		orderRemarksMap.put("isNewPage", 0);
		//最後まで表示できたら、備考欄表示は繰り返さない。
		orderRemarksMap.put("isRemarksDisplayAll", 1);

	}
	

	/**
	 * 備考欄上の注意書き文言を出力する
	 * @param pdfContentByte PDF出力情報
	 * @param baseFont 文字フォント
	 * @param noteStartPointY 出力する位置(高さ)
	 * @param sysWarehouseId 納入先ID 納入先が「その他」の場合のみ表示する文言がある為、引数を追加
	 */
	private static void showNote(PdfContentByte pdfContentByte, BaseFont baseFont, int noteStartPointY,
			long sysWarehouseId) {
		//ここから備考欄上の注意書き文言の表示処理
		//フォントサイズを指定
		pdfContentByte.setFontAndSize(baseFont, 14);

		//色を生成
		BaseColor red = new BaseColor(255, 0, 0);
		BaseColor black = new BaseColor(0, 0, 0);

		//文字の色を赤に設定
		pdfContentByte.setColorFill(red);

		//テキストの開始
		pdfContentByte.beginText();

		//表示位置の設定
		pdfContentByte.setTextMatrix(49, noteStartPointY);

		//表示する文字列の設定
		//納入先が「その他」の場合のみ表示する
		if(sysWarehouseId == 99) {
			pdfContentByte.showText("※発送後、配送業者、送り状番号をお知らせ下さいませ。宜しくお願いします。");
		}

		//表示したら文字の色を黒に戻しておく
		pdfContentByte.setColorFill(black);

		//テキストの終了
		pdfContentByte.endText();

	}

	/**
	 * 直送先以下を出力する
	 * @param document
	 * @param writer
	 * @param font
	 * @param baseFont
	 * @param slipDto
	 * @throws Exception
	 */
	private void directDeliveryTargetOutput(Document document, PdfWriter writer, Font font, BaseFont baseFont,
			ExtendDomesticOrderSlipDTO slipDto) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 10);

		// テキストの開始
		pdfContentByte.beginText();

		int estimateRemarksX = 30;
		//A4サイズの縦のサイズ
		int pageHeight = (int)document.getPageSize().getHeight();

		//注文書memoの表示位置を右下に変更
		int yPos = 650;

		int paddingTop = 12;
		//表示位置を右下に変更
		int paddingLeft = 415;
		// ※2018/01/12 表示位置を右下に変更
		showTextArea(document, writer, baseFont,
				  slipDto.getSenderRemarks()
				, "\\r\\n"
				, estimateRemarksX + paddingLeft
				//テキストの場合は下が基準になる
				, pageHeight - yPos - paddingTop
				, 11);
		/**
		 * この辺暫定。とりあえず、２バイト文字と１バイト文字によって改行の桁数変わるから判断する。時間あるときAPI読んで全体的に作りかえる
		 */

		yPos -= 5;

		// 表示位置の設定 ※中央から右下の枠に移動
		pdfContentByte.setTextMatrix(440, 195);
		// 表示する文字列の設定
		pdfContentByte.showText("《 MEMO 》");

		//[左下]直送先情報

		String wardString = "";
		wardString += "《 直 送 先 》";
		wardString += ",";
		wardString += "," + "〔住所1〕";
		wardString += "," + "〔住所2〕";
		wardString += ",〔氏名〕";
		wardString += ",〔TEL〕";

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);

		showTextArea(document, writer, baseFont,
				wardString, ",", 30, 191, 23);


		String warehouseString = "";
		warehouseString += "〒" + slipDto.getZip() + "," + "    " + slipDto.getAddressFst();
		warehouseString += ", " + "    " + slipDto.getAddressNxt()+ " "+ slipDto.getAddressNxt2();
		warehouseString += ", " + "    "  + slipDto.getLogisticNm();
		warehouseString += ", " + "    "  + slipDto.getTellNo();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);

		showTextArea(document, writer, baseFont,
				warehouseString, ",", 70, 168, 23);


		// テキストの終了
		pdfContentByte.endText();

		//ボーダーライン設定
		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.dispose();


		//見積備考の位置
		//数値を大きくする場合　下へ移動
		//数値を小さくする場合　上へ移動
		int esitimateRemarksY = 500;

		//直送先欄(表示位置を下段左
		pdfGraphics2D.drawRect(
				  estimateRemarksX
				//グラフの場合は上が基準になる
				, esitimateRemarksY + 130
				, 375
				, 150);

		//手書きメモ欄(表示位置を下段左
		pdfGraphics2D.drawRect(
				  estimateRemarksX + 410
				//グラフの場合は上が基準になる
				, esitimateRemarksY + 130
				, 125
				, 150);

		//直送先：住所1ボーダーライン設定
		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics7D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics7D.setColor(new Color(0, 0, 0));
		pdfGraphics7D.drawLine(35, pageHeight - 143, 380, pageHeight - 143);
		pdfGraphics7D.dispose();

		//直送先：住所2ボーダーライン設定
		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics4D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics4D.setColor(new Color(0, 0, 0));
		pdfGraphics4D.drawLine(35, pageHeight - 120, 380, pageHeight - 120);
		pdfGraphics4D.dispose();

		//直送先：氏名 ボーダーライン設定
		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics5D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics5D.setColor(new Color(0, 0, 0));
		pdfGraphics5D.drawLine(35, pageHeight - 97, 380, pageHeight - 97);
		pdfGraphics5D.dispose();

		//直送先：TEL ボーダーライン設定
		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics6D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics6D.setColor(new Color(0, 0, 0));
		pdfGraphics6D.drawLine(35, pageHeight - 74, 380, pageHeight - 74);
		pdfGraphics6D.dispose();

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
	private static float orderItemDetail(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, ExtendDomesticOrderSlipDTO slipDto,
			float orderCurrentHeight) throws Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 6);

		pdfContentByte.endText();

		int TABLE_COLS = 6;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		pdfPTable.setTotalWidth(535);
		int width[] = {200, 35, 100, 65, 45, 90};
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellItemNmCarHeader = new PdfPCell(new Paragraph("商品名 / 車種", font));
		PdfPCell cellItemOrderNumHeader = new PdfPCell(new Paragraph("数量", font));
		PdfPCell cellMakerCodeHeader = new PdfPCell(new Paragraph("メーカー品番", font));
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("定価", font));
		PdfPCell cellPostageHeader = new PdfPCell(new Paragraph("送料", font));
		PdfPCell cellRunHeader = new PdfPCell(new Paragraph("納期", font));



		// セルを灰色に設定
		cellItemNmCarHeader.setGrayFill(0.9f);
		cellItemOrderNumHeader.setGrayFill(0.9f);
		cellMakerCodeHeader.setGrayFill(0.9f);
		cellPriceHeader.setGrayFill(0.9f);
		cellPostageHeader.setGrayFill(0.9f);
		cellRunHeader.setGrayFill(0.9f);


		// 表の要素(列タイトル)を作成
		cellItemNmCarHeader.setHorizontalAlignment(1);
		cellItemOrderNumHeader.setHorizontalAlignment(1);
		cellMakerCodeHeader.setHorizontalAlignment(1);
		cellPriceHeader.setHorizontalAlignment(1);
		cellPostageHeader.setHorizontalAlignment(1);
		cellRunHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellItemNmCarHeader);
		pdfPTable.addCell(cellItemOrderNumHeader);
		pdfPTable.addCell(cellMakerCodeHeader);
		pdfPTable.addCell(cellPriceHeader);
		pdfPTable.addCell(cellPostageHeader);
		pdfPTable.addCell(cellRunHeader);

		/**
		 * ループ(商品LISTのDTOをループさせる予定)
		 */

		int maxRow = 80;
		//商品カウント
		int rowNum = 0;
		//メーカー名含む行数カウント
		int rowCnt = 0;
		//描画した商品の行数を保存するための変数
		int repaginationRow = 0;
		//描画した時の商品テーブルの高さを保存するための変数
		float pageHight = 0;
		String tempMakerNm = "";

		//商品の表示ループ
		for (rowNum = 0; rowNum < slipDto.getDomesticOrderItemList().size(); rowNum++) {

			//makerNmとtempMakerNmが違う場合メーカー名を表示
			if (!StringUtils.equals(slipDto.getDomesticOrderItemList().get(rowNum).getMakerNm(), tempMakerNm)) {
				//メーカー名
				PdfPCell cellMakerNm =  new PdfPCell(new Paragraph(slipDto.getDomesticOrderItemList().get(rowNum).getMakerNm(), font));

				//セルの結合
				cellMakerNm.setColspan(6);

				// セルを灰色に設定
				cellMakerNm.setBorderWidthRight(0);
				cellMakerNm.setBorderWidthLeft(0);

				//セル内位置設定
				cellMakerNm.setHorizontalAlignment(0);

				//セル作成
				pdfPTable.addCell(cellMakerNm);

				//tempMakerNmを初期化
				tempMakerNm = slipDto.getDomesticOrderItemList().get(rowNum).getMakerNm();
				rowCnt++;
			}

			//商品名/車種
			PdfPCell cellItemNmCar =  new PdfPCell(new Paragraph(slipDto.getDomesticOrderItemList().get(rowNum).getItemNm(), font));
			// 数量
			PdfPCell cellItemOrder = new PdfPCell(new Paragraph(
					String.valueOf(slipDto.getDomesticOrderItemList().get(rowNum).getOrderNum()), font));
			// メーカー品番
			PdfPCell cellMakerCode = new PdfPCell(new Paragraph(slipDto.getDomesticOrderItemList().get(rowNum).getMakerCode(), font));

			// 定価
			PdfPCell cellPrice = new PdfPCell();

			//出品DBの情報取得
			DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
			DomesticExhibitionDTO exhibitionDto = new DomesticExhibitionDTO();
			exhibitionDto.setManagementCode(slipDto.getDomesticOrderItemList().get(rowNum).getManagementCode());

			//管理品番から商品情報を検索
			exhibitionDto = dao.getManagementInfo(exhibitionDto.getManagementCode());

			//openPriceFlgがあるか確認し、1の場合は定価をopen表示
			if (exhibitionDto.getOpenPriceFlg().equals("0")) {

				cellPrice = new PdfPCell(new Paragraph(
						StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getDomesticOrderItemList().get(rowNum).getListPrice())) + "円", font));
			} else if (exhibitionDto.getOpenPriceFlg().equals("1")) {
				cellPrice = new PdfPCell(new Paragraph("　" + "open", font));
			}

			//送料
			PdfPCell cellPostage = new PdfPCell();
			//送料が０円の場合表示しない
			if (slipDto.getDomesticOrderItemList().get(rowNum).getPostage() == 0) {
				cellPostage = new PdfPCell(new Paragraph("", font));
			} else {
				cellPostage = new PdfPCell(new Paragraph(
						StringUtil.formatCalc(BigDecimal.valueOf(slipDto.getDomesticOrderItemList().get(rowNum).getPostage())) + "円", font));
			}

			// 納期
			PdfPCell cellRun = new PdfPCell(new Paragraph("", font));

			cellItemNmCar.setHorizontalAlignment(0);
			cellItemOrder.setHorizontalAlignment(1);
			cellMakerCode.setHorizontalAlignment(0);
			cellPrice.setHorizontalAlignment(2);
			cellPostage.setHorizontalAlignment(2);
			cellRun.setHorizontalAlignment(1);


			pdfPTable.addCell(cellItemNmCar);
			pdfPTable.addCell(cellItemOrder);
			pdfPTable.addCell(cellMakerCode);
			pdfPTable.addCell(cellPrice);
			pdfPTable.addCell(cellPostage);
			pdfPTable.addCell(cellRun);
			rowCnt++;


			/*
			 * 【改ページ処理メモ】
			 * ・商品テーブルの高さが中段備考欄に被る(600とする)まで、商品テーブルを作成していく。(この時点ではまだテーブルの描画はしない)
			 * ・商品テーブルの高さが600を超えたら1ページ目に作成していた商品テーブルを描画する。その際、描画した時の商品テーブルの高さ、商品レコードの行数を保存する。
			 * ・改ページして、再び商品テーブルの作成を改ページ後で再び改ページする基準(作成中のテーブルの高さ - 前ページで描画した際の高さが750を超えるまで)に達したら
			 * 　商品テーブルを描画(この時前ページで描画した行数+1から描画する)、再び商品テーブルの高さ、商品レコードの行数を保存する。
			 * ・以下この繰り返し
			 * */

			//1ページ目の商品テーブル高さ判定 ※備考欄の上に文言が追加されたので、商品テーブルの高さを下げる。
			if (pdfPTable.getTotalHeight() > 600 && repaginationRow == 0) {

				//1ページ目で改ページの基準に達したら一度作成した商品テーブルを描画する
				pdfPTable.writeSelectedRows(0, TABLE_COLS, 0, maxRow + 2, 30, 650,
						writer.getDirectContent());

				//描画した商品テーブルの高さを保存。改ページで描画するときの比較で使用する。
				pageHight = pdfPTable.calculateHeights();
				//描画した商品の行数を保存。改ページで描画するときにここの商品から表示しますよという目印。
				repaginationRow = rowCnt;

			//現在「作成」している商品テーブルから改ページ前に「描画した」商品テーブルの高さを引いた高さが、改ページの基準となる高さに達していたら改ページ
			} else if (pdfPTable.getTotalHeight() - pageHight > 750 && repaginationRow != 0) {

				newPage(document, writer);

				//改ページ後の商品テーブルを描画する。前のページで描画した商品の次の商品から描画を開始する。
				pdfPTable.writeSelectedRows(0, TABLE_COLS, repaginationRow + 1,
						rowCnt + 1, 30, 800, writer.getDirectContent());

				//描画した商品テーブルの高さを保存。改ページで描画するときの比較で使用する。
				pageHight = pdfPTable.calculateHeights();
				//描画した商品テーブルの行数を保存。改ページで描画するときにここの商品から表示しますよという目印。
				repaginationRow = rowCnt;
			}
		}

		//TODO テーブル描画の最終Y地点目印用TODO

		//商品テーブルの最終描画Yポジション
		float tableLastDrawingYPosition = 0;

		//1ページに収まり、備考欄以下を表示するのに改ページ必要なし
		if (rowCnt > repaginationRow && repaginationRow == 0) {

			//描画したテーブルの底のY地点を保存する。
			tableLastDrawingYPosition = pdfPTable.writeSelectedRows(0, TABLE_COLS,
																											0, maxRow + 2,
																											30, 650,
																											writer.getDirectContent());

			//1ページに収まるが、備考欄以下を表示するのに改ページ必要あり
			if (pdfPTable.calculateHeights() > 280){

				//改ページ後は備考欄以下のみ表示する
				tableLastDrawingYPosition = 800;

				newPage(document, writer);
			}

		//2ページ以上で最終ページに備考欄以下を表示するのに改ページ必要あり
		} else if (rowCnt >= repaginationRow) {
			document.newPage();

			//作成していた商品テーブルで残りの描画していない分を描画する。
			tableLastDrawingYPosition = pdfPTable.writeSelectedRows(0, TABLE_COLS,
																											repaginationRow + 1, rowCnt + 1,
																											30, 800,
																											writer.getDirectContent());

			//最後のテーブルを描画したページに備考欄以下が表示できないときさらに改ページが必要
			if (pdfPTable.calculateHeights() - pageHight > 450) {

				//改ページ後は備考欄以下のみ表示する
//				tableLastDrawingYPosition = 800;

				newPage(document, writer);
			}
		}

		return tableLastDrawingYPosition;

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
	 * 位置調整？
	 *
	 * @param text
	 * @return
	 */
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
	 * 備考欄の終了Y地点が直送先の開始地点+20の位置になるような、備考欄の開始Y地点を求める処理
	 * @param itemTableEndPoint
	 * @param pdfPTableTotalHeight
	 * @return
	 */
	private static int displayLocationCalculation(float itemTableEndPoint, float pdfPTableTotalHeight) {

		//備考欄の開始地点Y
		int remarksFieldDisplayPosition = 0;

		//備考欄の開始地点を商品テーブルの終了地点からどのくらい下げるかを計算する
		float downHowMany = itemTableEndPoint - pdfPTableTotalHeight - 232;
		//商品テーブルの終了地点から上で求めた数だけ下げることで、直送先から常に+20の高さで備考欄が表示できる
		if (downHowMany < 0) {
			remarksFieldDisplayPosition = (int) (itemTableEndPoint + downHowMany);
		} else {
			remarksFieldDisplayPosition = (int) (itemTableEndPoint - downHowMany);
		}

		return remarksFieldDisplayPosition;
	}

	/**
	 * 備考欄の文字が中断備考欄に表示した時何行になるかを調べる処理
	 * @param remarks
	 * @return
	 */
	private static int stringIsmanyLinesHaveIsCheck(String remarks) {
		int lines = 1;

		//備考欄の文字列が何行になるか調べる
		for (int i = 1; i <= remarks.length(); i++) {
			//50文字に達したら改行扱い
			if (i % 50 == 0) {
				lines ++;
			}
		}

		return lines;
	}

}