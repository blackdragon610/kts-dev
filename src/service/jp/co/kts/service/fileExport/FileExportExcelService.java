package jp.co.kts.service.fileExport;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public abstract class FileExportExcelService {

	/**
	 * シートのデータ
	 */
	protected HSSFSheet sheet;
	protected XSSFSheet xsheet;

	/**
	 * 行のデータ
	 */
	protected HSSFRow row;
	protected XSSFRow xrow;

	/**
	 * 列のデータ
	 */

	protected HSSFCell cell;
	protected XSSFCell xcell;

	/**
	 * 行のインデックス
	 */
	protected int rowIdx;

	/**
	 * 列のインデックス
	 */
	protected int colIdx;

	/**
	 * シートのインデックス
	 */
	protected int sheetIdx;

	{
		//セルの1行目は0から始まります
		rowIdx = 0;
		//セルの1列目は0から始まります
		colIdx = 0;
		//シートの一つ目は0から始まります
		sheetIdx = 0;
	}

	/**
	 * 行（row）列（cellIdx）に対応するセルを返却します
	 *
	 * @param row
	 * @param cellIdx
	 * @return
	 */
	public HSSFCell callCreateCell(HSSFRow row, int colIdx) {

		cell = row.getCell(colIdx);
		if (cell == null) {
			cell = row.createCell(colIdx);
		}
		return cell;
	}
	public XSSFCell xcallCreateCell(XSSFRow row, int colIdx) {

		xcell = row.getCell(colIdx);
		if (xcell == null) {
			xcell = row.createCell(colIdx);
		}
		return xcell;
	}

	protected HSSFRow callGetRow(int num, float height) {

		HSSFRow hRow = sheet.getRow(num);
		if (hRow == null) {
			hRow = sheet.createRow(num);
		}
		hRow.setHeightInPoints(height);

		return hRow;
	}
	protected XSSFRow xcallGetRow(int num, float height) {

		XSSFRow hRow = xsheet.getRow(num);
		if (hRow == null) {
			hRow = xsheet.createRow(num);
		}
		hRow.setHeightInPoints(height);

		return hRow;
	}

	/**
	 * 引数の値をRichTextStringとして返却します
	 *
	 * @param value
	 * @return
	 */
	public HSSFRichTextString castRichTextString (String value) {

		if (value == null) {
			value = StringUtils.EMPTY;
		}

		return new HSSFRichTextString(value);
	}
	public XSSFRichTextString xcastRichTextString (String value) {

		if (value == null) {
			value = StringUtils.EMPTY;
		}

		return new XSSFRichTextString(value);
	}

	/**
	 * 引数の値をRichTextStringとして返却します
	 *
	 * @param num
	 * @return
	 */
//	public HSSFRichTextString castRichTextString(int num) {
//
//		String value = StringUtils.EMPTY;
//
//		value = Integer.toString(num);
//		return new HSSFRichTextString(value);
//	}

	/**
	 * 列と文字数を元にセルの幅を設定します
	 *
	 * @param colIdx
	 * 列のインデックス
	 * @param strLen
	 * 文字数
	 */
//	public void callSetColumnWidth(HSSFSheet sheet, int colIdx, int strLen) {
	public void callSetColumnWidth(int colIdx, int strLen) {

		if (sheet == null) {
			return;
		}

		//文字数×256が大体の幅らしいけど・・・少し大きい気がする
		sheet.setColumnWidth(colIdx, strLen * 256);
	}
	public void xcallSetColumnWidth(int colIdx, int strLen) {

		if (xsheet == null) {
			return;
		}

		//文字数×256が大体の幅らしいけど・・・少し大きい気がする
		xsheet.setColumnWidth(colIdx, strLen * 256);
	}

	protected void callSetColumnWidthMap(Map<Integer, Integer> map) {

		for (Integer key: map.keySet()) {

			System.out.println(key);
			callSetColumnWidth(key, map.get(key));
		}

	}
	protected void xcallSetColumnWidthMap(Map<Integer, Integer> map) {

		for (Integer key: map.keySet()) {

			System.out.println(key);
			xcallSetColumnWidth(key, map.get(key));
		}

	}

}
