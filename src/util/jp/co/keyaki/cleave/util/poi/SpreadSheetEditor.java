/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext.BookShareStyles;

/**
 * スプレットシート編集クラス.
 *
 * @author ytakahashi
 */
public class SpreadSheetEditor {

	/**
	 * 操作対象ブック.
	 */
	private Workbook book;

	/**
	 * 操作対象シート.
	 */
	private Sheet activeSheet;

	/**
	 * シート毎の遅延修飾コンテキスト
	 */
	private Map<Sheet, CellStyleLazyContext> lazyStyleContents = new HashMap<Sheet, CellStyleLazyContext>();

	/**
	 * ブックを新規に作成するコンストラクタ.
	 */
	public SpreadSheetEditor() {
		this(SpreadType.SPREAD_OLD);
	}

	/**
	 * ブックを新規に作成するコンストラクタ.
	 */
	public SpreadSheetEditor(SpreadType type) {
		setBook(type.createWorkbook());
		setActiveSheet(0);
	}

	/**
	 * 既存ブックを開くコンストラクタ.
	 *
	 * @param is 既存ブックに対する{@link InputStream}
	 * @throws IOException オープン時に例外が発生した場合
	 * @throws IllegalArgumentException スプレットシートに対するストリームではない場合
	 */
	public SpreadSheetEditor(InputStream is) throws IOException {
		try {
			setBook(WorkbookFactory.create(is));
		} catch (InvalidFormatException ife) {
			throw new IllegalArgumentException("not spread sheet file stream.", ife);
		}
		setActiveSheet(0);
	}

	/**
	 * ブックを保存します.
	 *
	 * @param os ブック保存先{@link OutputStream}
	 * @throws IOException 保存時に例外が発生した場合
	 */
	public void write(OutputStream os) throws IOException {
		applyLazyStyle();
		book.write(os);
	}

	/**
	 * ワークブックを設定します.
	 *
	 * @param book 設定するワークブック
	 */
	protected void setBook(Workbook book) {
		this.book = book;
	}

	/**
	 * 座標オブジェクトを生成し返します.
	 *
	 * @param rowIndex
	 * @param colIndex
	 * @return 座標オブジェクト
	 */
	public Point createPoint(int rowIndex, int colIndex) {
		return new Point(SpreadType.get(book), rowIndex, colIndex);
	}

	/**
	 * 座標オブジェクトを生成し返します.
	 *
	 * @param rowIndex
	 * @param colIndex
	 * @param rowAbsolute
	 * @param colAbsolute
	 * @return 座標オブジェクト
	 */
	public Point createPoint(int rowIndex, int colIndex, boolean rowAbsolute, boolean colAbsolute) {
		return new Point(SpreadType.get(book), rowIndex, colIndex, rowAbsolute, colAbsolute);
	}

	/**
	 * 操作対象シートを変更します.
	 *
	 * @param sheetIndex シートインデックス
	 * @throws IllegalArgumentException 不正なインデックスの場合
	 */
	public void setActiveSheet(int sheetIndex) throws IllegalArgumentException {
		if (sheetIndex < 0) {
			throw new IllegalArgumentException("sheetIndexに指定できるのは、0以上の値です。sheetIndex=" + sheetIndex);
		}
		while (sheetIndex >= book.getNumberOfSheets()) {
			book.createSheet();
		}
		activeSheet = book.getSheetAt(sheetIndex);
		if (!lazyStyleContents.containsKey(activeSheet)) {

			BookShareStyles bookShareStyles = null;
			if (lazyStyleContents.isEmpty()) {
				bookShareStyles = new BookShareStyles(book);
			} else {
				bookShareStyles = lazyStyleContents.values().iterator().next().getBookShareStyles();
			}
			lazyStyleContents.put(activeSheet, new CellStyleLazyContext(activeSheet, bookShareStyles));
		}
	}

	/**
	 * シート名を利用して操作対象シートを変更します.
	 *
	 * @param sheetName シート名
	 */
	public void setActiveSheet(String sheetName) {
		if (book.getSheetIndex(sheetName) < 0) {
			book.createSheet(sheetName);
		}
		setActiveSheet(book.getSheetIndex(sheetName));
	}

	/**
	 * セルのデータ型を設定します.
	 *
	 * <p>
	 * セルのデータ型を判定する際に、実際のデータの型を参照します.
	 * データが<code>null</code>の場合は、データ型を未設定（自動判定）として設定します.
	 * </p>
	 *
	 * @param value データ
	 * @param cell データ型設定対象セル
	 */
	protected void setCellType(Object value, Cell cell) {
		if (value == null) {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		} else if (value instanceof Number) {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		} else if (value instanceof Date) {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		} else if (value instanceof Calendar) {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		} else if (value instanceof Boolean) {
			cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
		} else {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
	}

	/**
	 * セルにスタイルレイアウトを設定します.
	 *
	 * @param rowIndex 行インデックス
	 * @param colIndex 列インデックス
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setLayout(int rowIndex, int colIndex, CellStyleDecorator cellStyleDecorator) {
		Point point = createPoint(rowIndex, colIndex);
		setLayout(point, cellStyleDecorator);
	}

	/**
	 * セルにスタイルレイアウトを設定します.
	 *
	 * @param point 座標
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setLayout(Point point, CellStyleDecorator cellStyleDecorator) {
		setLayout(point, point, cellStyleDecorator);
	}

	/**
	 * 範囲セルにスタイルレイアウトを設定します.
	 *
	 * @param fromRowIndex 開始行インデックス
	 * @param fromColIndex 開始列インデックス
	 * @param toRowIndex 終了行インデックス
	 * @param toColIndex 終了列インデックス
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setLayout(int fromRowIndex, int fromColIndex, int toRowIndex, int toColIndex, CellStyleDecorator cellStyleDecorator) {
		Point fromPoint = createPoint(fromRowIndex, fromColIndex);
		Point toPoint = createPoint(toRowIndex, toColIndex);
		setLayout(fromPoint, toPoint, cellStyleDecorator);
	}

	/**
	 * 範囲セルにスタイルレイアウトを設定します.
	 *
	 * @param fromPoint 開始座標
	 * @param toPoint 終了座標
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setLayout(Point fromPoint, Point toPoint, CellStyleDecorator cellStyleDecorator) {
		if (fromPoint == null || toPoint == null || cellStyleDecorator == null) {
			return;
		}
		Range range = new Range(fromPoint, toPoint);
		setLayout(range, cellStyleDecorator);
	}

	/**
	 * 範囲セルにスタイルレイアウトを設定します.
	 *
	 * @param range 範囲セル
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setLayout(Range range, CellStyleDecorator cellStyleDecorator) {
		if (range == null || cellStyleDecorator == null) {
			return;
		}
		for (Point point : range) {
			Cell cell = AccessUtils.getCell(activeSheet, point);
			cellStyleDecorator.decorate(cell, range, point, lazyStyleContents.get(activeSheet));
		}
	}

	/**
	 * セルに値を設定します.
	 *
	 * @param rowIndex 行インデックス
	 * @param colIndex 列インデックス
	 * @param value 値
	 */
	public void setValue(int rowIndex, int colIndex, Object value) {

		Point point = createPoint(rowIndex, colIndex);
		setValue(point, value);

	}

	/**
	 * セルに値を設定します.
	 *
	 * @param point 座標
	 * @param value 値
	 */
	public void setValue(Point point, Object value) {
		setValue(point, value, null);
	}

	/**
	 * セルに値とスタイルレイアウトを設定します.
	 *
	 * @param rowIndex 行インデックス
	 * @param colIndex 列インデックス
	 * @param value 値
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setValue(int rowIndex, int colIndex, Object value, CellStyleDecorator cellStyleDecorator) {

		Point point = createPoint(rowIndex, colIndex);
		setValue(point, value, cellStyleDecorator);
	}

	/**
	 * セルに値とスタイルレイアウトを設定します.
	 *
	 * @param point 座標
	 * @param value 値
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setValue(Point point, Object value, CellStyleDecorator cellStyleDecorator) {

		Cell cell = AccessUtils.getCell(activeSheet, point);
		setCellType(value, cell);
		setLayout(point, cellStyleDecorator);
		setCellValue(value, cell);
	}

	/**
	 * セルにデータを設定します.
	 *
	 * @param value データ
	 * @param cell データ設定対象セル
	 */
	protected void setCellValue(Object value, Cell cell) {
		if (value == null) {
			return;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN :
			cell.setCellValue(Boolean.valueOf(value.toString()).booleanValue());
			break;
		case Cell.CELL_TYPE_NUMERIC :
			if (value instanceof Date) {
				cell.setCellValue(Date.class.cast(value));
			} else if (value instanceof Calendar) {
				cell.setCellValue(Calendar.class.cast(value));
			} else {
				cell.setCellValue(new BigDecimal(value.toString()).doubleValue());
			}
			break;
		default :
			CreationHelper creationHelper = book.getCreationHelper();
			cell.setCellValue(creationHelper.createRichTextString(value.toString()));
			break;
		}
	}

	/**
	 * セルに式を設定します.
	 *
	 * @param rowIndex 行インデックス
	 * @param colIndex 列インデックス
	 * @param formula 式
	 */
	public void setFormula(int rowIndex, int colIndex, CellFormula formula) {
		Point point = createPoint(rowIndex, colIndex);
		setFormula(point, formula);
	}

	/**
	 * セルに式を設定します.
	 *
	 * @param point 座標
	 * @param formula 式
	 */
	public void setFormula(Point point, CellFormula formula) {
		setFormula(point, formula, null);
	}

	/**
	 * セルに式とスタイルレイアウトを設定します.
	 *
	 * @param rowIndex 行インデックス
	 * @param colIndex 列インデックス
	 * @param formula 式
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setFormula(int rowIndex, int colIndex, CellFormula formula, CellStyleDecorator cellStyleDecorator) {
		Point point = createPoint(rowIndex, colIndex);
		setFormula(point, formula, cellStyleDecorator);
	}

	/**
	 * セルに式とスタイルレイアウトを設定します.
	 *
	 * @param point 座標
	 * @param formula 式
	 * @param cellStyleDecorator スタイルレイアウト
	 */
	public void setFormula(Point point, CellFormula formula, CellStyleDecorator cellStyleDecorator) {
		Cell cell = AccessUtils.getCell(activeSheet, point);
		setCellType(null, cell);
		setLayout(point, cellStyleDecorator);
		setCellFormula(formula, cell);
	}

	/**
	 * セルに式を設定します.
	 *
	 * @param formula 式
	 * @param cell 式設定対象セル
	 */
	protected void setCellFormula(CellFormula formula, Cell cell) {
		cell.setCellFormula(formula.toReferenceStyle());
	}

	/**
	 * 遅延修飾を適用します.
	 *
	 */
	protected void applyLazyStyle() {

		for (CellStyleLazyContext styleContext  : lazyStyleContents.values()) {

			styleContext.applyLazyStyle();

		}
	}

}
