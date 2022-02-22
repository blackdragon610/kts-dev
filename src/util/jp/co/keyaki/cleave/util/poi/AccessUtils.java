/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * オブジェクト（行・列）へのアクセス手段を提供するクラス。
 *
 *
 * @author ytakahashi
 *
 */
public class AccessUtils {

	/**
	 * 取得オブジェクトを作成する。
	 */
	public static final boolean CREATE_OBJECT = true;
	/**
	 * 取得オブジェクトを作成しない。
	 */
	public static final boolean NON_CREATE_OBJECT = false;

	/**
	 * 列オブジェクトを返却します。
	 *
	 * <p>
	 * 当メソッドは、座標オブジェクトの位置にオブジェクトが存在しない場合は作成し返します。
	 * </p>
	 *
	 * @param sheet 取得元シートオブジェクト
	 * @param point 取得座標オブジェクト
	 * @return 列オブジェクト
	 * @see #getCell(HSSFSheet, Point, boolean)
	 */
	public static Cell getCell(Sheet sheet, Point point) {
		return getCell(sheet, point, CREATE_OBJECT);
	}

	/**
	 * 行オブジェクトを返却します。
	 *
	 * <p>
	 * 当メソッドは、行番号の位置にオブジェクトが存在しない場合は作成し返します。
	 * </p>
	 *
	 * @param sheet 取得元シートオブジェクト
	 * @param rowIndex 取得行番号（0ベース）
	 * @return 行オブジェクト
	 * @see #getRow(HSSFSheet, int, boolean)
	 */
	public static Row getRow(Sheet sheet, int rowIndex) {
		return getRow(sheet, rowIndex, CREATE_OBJECT);
	}

	/**
	 * 列オブジェクトを返却します。
	 *
	 * <p>
	 * 当メソッドは、列番号の位置にオブジェクトが存在しない場合は作成し返します。
	 * </p>
	 *
	 * @param row 取得元行オブジェクト
	 * @param colIndex 取得列番号（0ベース）
	 * @return 列オブジェクト
	 * @see #getCell(HSSFRow, short, boolean)
	 */
	public static Cell getCell(Row row, short colIndex) {
		return getCell(row, colIndex, CREATE_OBJECT);
	}

	/**
	 * 列オブジェクトを返却します。
	 *
	 * <p>
	 * 当メソッドは、座標オブジェクトの位置にオブジェクトが存在しない場合の振る舞いを、第３引数にて指定できます。
	 * </p>
	 *
	 * @param sheet 取得元シートオブジェクト
	 * @param point 取得座標オブジェクト
	 * @param isCreate 取得列オブジェクトの作成有無（true:存在しない場合は作成/false:存在しない場合は作成しない null返却）
	 * @return 列オブジェクト（取得オブジェクトが存在せず、isCreate == falseの場合はnull）
	 */
	public static Cell getCell(Sheet sheet, Point point, boolean isCreate) {
		if (point == null) {
			return null;
		}
		Row row = getRow(sheet, point.getRowIndex(), isCreate);
		if (row == null) {
			return null;
		}
		return getCell(row, point.getColIndex(), isCreate);
	}

	/**
	 * 行オブジェクトを返却します。
	 *
	 * <p>
	 * 当メソッドは、行番号の位置にオブジェクトが存在しない場合の振る舞いを、第３引数にて指定できます。
	 * </p>
	 *
	 * @param sheet 取得元シートオブジェクト
	 * @param rowIndex 取得行番号（0ベース）
	 * @param isCreate 取得列オブジェクトの作成有無（true:存在しない場合は作成/false:存在しない場合は作成しない null返却）
	 * @return 行オブジェクト（取得オブジェクトが存在せず、isCreate == falseの場合はnull）
	 */
	public static Row getRow(Sheet sheet, int rowIndex, boolean isCreate) {
		SpreadType spreadType = SpreadType.get(sheet);
		if (!spreadType.validateRowIndex(rowIndex)) {
			return null;
		}
		Row row = sheet.getRow(rowIndex);
		if (row == null && isCreate) {
			row = sheet.createRow(rowIndex);
		}
		return row;
	}

	/**
	 * 列オブジェクトを返却します。
	 *
	 * <p>
	 * 当メソッドは、列番号の位置にオブジェクトが存在しない場合の振る舞いを、第３引数にて指定できます。
	 * </p>
	 *
	 * @param row 取得元行オブジェクト
	 * @param colIndex 取得列番号（0ベース）
	 * @param isCreate 取得列オブジェクトの作成有無（true:存在しない場合は作成/false:存在しない場合は作成しない null返却）
	 * @return 列オブジェクト（取得オブジェクトが存在せず、isCreate == falseの場合はnull）
	 */
	public static Cell getCell(Row row, int colIndex, boolean isCreate) {
		SpreadType spreadType = SpreadType.get(row);
		if (!spreadType.validateColIndex(colIndex)) {
			return null;
		}
		Cell cell = row.getCell(colIndex);
		if (cell == null && isCreate) {
			cell = row.createCell(colIndex);
		}
		return cell;
	}

}
