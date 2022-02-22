/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.keyaki.cleave.util.poi.style.FontSetting;



/**
 * 遅延修飾用コンテキスト.
 *
 * 同一定義の修飾は同一の{@link CellStyle}を利用させるため、修飾タイミングを遅延させるためのコンテキスト.
 *
 * @author ytakahashi
 *
 */
public class CellStyleLazyContext {

	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException {

//		System.out.println(Point.toA1StyleColLabel(SpreadType.SPREAD_NEW, SpreadType.SPREAD_NEW.getLastColumnIndex()));

		new BookShareStyles(WorkbookFactory.create(new FileInputStream("C:/pleiades/workspace/pa/template/ProgressReportTemplate.xls")));
//		new BookShareStyles(SpreadType.SPREAD_OLD.createWorkbook());
	}

	/**
	 * {@link Workbook}で共有する{@link CellStyle}を管理するクラス.
	 *
	 * @author ytakahashi
	 *
	 */
	public static class BookShareStyles {

		private Workbook book;
		private Map<CellStyleLazyDecorator, CellStyle> cache = new HashMap<CellStyleLazyDecorator, CellStyle>();

		public BookShareStyles(Workbook book) {
			this.book = book;


			for (short i = 0; i < book.getNumberOfFonts(); i++) {

				Font font = book.getFontAt(i);
				System.out.println("[" + i + "]" + font);
				System.out.println(font.getIndex());

				System.out.println("fontName=" + font.getFontName());
				System.out.println("boldweight=" + font.getBoldweight());
				System.out.println("charSet=" + font.getCharSet());
				System.out.println("color=" + font.getColor());
				System.out.println("fontHeight=" + font.getFontHeight());
				System.out.println("typeOffset=" + font.getTypeOffset());
				System.out.println("underline=" + font.getUnderline());
				System.out.println("italic=" + font.getItalic());
				System.out.println("strikeout=" + font.getStrikeout());

				FontSetting setting = new FontSetting(font.getBoldweight()
						, ColorUtils.getColor(font.getColor())
						, font.getFontHeightInPoints()
						, font.getFontName()
						, font.getItalic()
						, font.getStrikeout()
						, font.getTypeOffset()
						, font.getUnderline());

				System.out.println(setting);

			}


			for (short i = 0; i < book.getNumCellStyles(); i++) {

				CellStyle style = book.getCellStyleAt(i);
				System.out.println("[" + i + "]" + style);
				System.out.println(style.getIndex());

				System.out.println("hidden=" + style.getHidden());
				System.out.println("locked=" + style.getLocked());
				System.out.println("wrapText=" + style.getWrapText());
				System.out.println("alignment=" + style.getAlignment());
				System.out.println("varticalAlignment=" + style.getVerticalAlignment());
				System.out.println("fontIndex=" + style.getFontIndex());
				System.out.println("indention=" + style.getIndention());
				System.out.println("rotation=" + style.getRotation());

				System.out.println("borderBottom=" + style.getBorderBottom());
				System.out.println("bottomBorderColor=" + style.getBottomBorderColor());
				System.out.println("borderLeft=" + style.getBorderLeft());
				System.out.println("leftBorderColor=" + style.getLeftBorderColor());
				System.out.println("borderRight=" + style.getBorderRight());
				System.out.println("rightBorderColor=" + style.getRightBorderColor());
				System.out.println("borderTop=" + style.getBorderTop());
				System.out.println("topBorderColor=" + style.getTopBorderColor());

				System.out.println("dataFormatString=" + style.getDataFormatString());
				System.out.println("dataFormat=" + style.getDataFormat());

				System.out.println("fillPattern=" + style.getFillPattern());
				System.out.println("fillBackgroundColor=" + style.getFillBackgroundColor());
				System.out.println("fillForegroundColor=" + style.getFillForegroundColor());

			}
		}

		public CellStyle getStyle(CellStyleLazyDecorator decorator) {
			return cache.get(decorator);
		}
		public CellStyle createStyle(CellStyleLazyDecorator decorator) {
			if (cache.containsKey(decorator)) {
				return cache.get(decorator);
			}
			CellStyle style =  book.createCellStyle();
			cache.put(decorator, style);
			return style;
		}
	}

	/**
	 * ブックで共有するスタイル保持オブジェクト.
	 */
	private BookShareStyles shareStyles;

	/**
	 * 適用先シート.
	 */
	private Sheet sheet;

	/**
	 * 既存セルに対する座標オブジェクト.
	 */
	private Set<Point> existsCells = new TreeSet<Point>();

	/**
	 * 座標に適用する遅延修飾オブジェクトマップ.
	 */
	private Map<Point, CompositeCellStyleLazyDecorator> lazyStyles = new HashMap<Point, CompositeCellStyleLazyDecorator>();

	/**
	 * コンストラクタ.
	 *
	 * @param sheet 適用先シート
	 * @param shareStyles ブック共有スタイル保持オブジェクト
	 */
	public CellStyleLazyContext(Sheet sheet, BookShareStyles shareStyles) {
		this.sheet = sheet;
		this.shareStyles = shareStyles;
		readExistsCell();
	}

	/**
	 * ブック共有スタイル保持オブジェクトを返します.
	 *
	 * @return ブック共有スタイル保持オブジェクト
	 */
	public BookShareStyles getBookShareStyles() {
		return shareStyles;
	}

	/**
	 * 座標に遅延修飾オブジェクトを登録します.
	 *
	 * @param point 座標
	 * @param decorator 遅延修飾オブジェクト
	 */
	public void regist(Point point, CellStyleLazyDecorator decorator) {
		if (!lazyStyles.containsKey(point)) {
			lazyStyles.put(point, new CompositeCellStyleLazyDecorator());
		}
		lazyStyles.get(point).add(decorator);
	}

	/**
	 * 遅延させていた{@link CellStyle}に対して、実際にスタイルを適用させるメソッド.
	 *
	 */
	public void applyLazyStyle() {

		Map<CellStyleLazyDecorator, Set<Point>> identicalStylePointsMap = getIdenticalStylePointsMap();

		for (Map.Entry<CellStyleLazyDecorator, Set<Point>> identicalStylePoints : identicalStylePointsMap.entrySet()) {

			CellStyleLazyDecorator identicalStyle = identicalStylePoints.getKey();
			Set<Point> points = identicalStylePoints.getValue();

			for (Point point : points) {
				Cell cell = AccessUtils.getCell(sheet, point);
				if (existsCells.contains(point)) {
					CellStyle existingStyle = cell.getCellStyle();
					identicalStyle.decorateLazy(existingStyle, cell, this);
					continue;
				}
				CellStyle madeStyle = shareStyles.getStyle(identicalStyle);
				if (madeStyle == null) {
					madeStyle = shareStyles.createStyle(identicalStyle);
					identicalStyle.decorateLazy(madeStyle, cell, this);
				}
				cell.setCellStyle(madeStyle);
			}

		}
	}

	/**
	 * 同一の遅延修飾を行う座標を対にして返します.
	 *
	 *
	 * @return 同一遅延修飾を行う座標マップ
	 */
	protected Map<CellStyleLazyDecorator, Set<Point>> getIdenticalStylePointsMap() {
		Map<CellStyleLazyDecorator, Set<Point>> identicalMap = new HashMap<CellStyleLazyDecorator, Set<Point>>();
		for (Map.Entry<Point, CompositeCellStyleLazyDecorator> lazyStyle : lazyStyles.entrySet()) {
			CompositeCellStyleLazyDecorator styles = lazyStyle.getValue();
			Point point = lazyStyle.getKey();
			if (!identicalMap.containsKey(styles)) {
				identicalMap.put(styles, new TreeSet<Point>());
			}
			identicalMap.get(styles).add(point);
		}
		return identicalMap;

	}

	/**
	 * シートにすでに存在するセルに対する座標オブジェクトを取得します.
	 *
	 */
	protected void readExistsCell() {

		SpreadType spreadType = SpreadType.get(sheet);
		for (int rowIndex = sheet.getFirstRowNum(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = AccessUtils.getRow(sheet, rowIndex, AccessUtils.NON_CREATE_OBJECT);
			if (row == null) {
				continue;
			}
			for (short colIndex = row.getFirstCellNum(); colIndex <= row.getLastCellNum(); colIndex++) {
				Cell cell = AccessUtils.getCell(row, colIndex, AccessUtils.NON_CREATE_OBJECT);
				if (cell == null) {
					continue;
				}
				existsCells.add(new Point(spreadType, rowIndex, colIndex));
			}
		}
	}
}
