/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * 範囲座標を表すクラス.
 *
 * @author ytakahashi
 */
public class Range implements Iterable<Point>, Referencable {

	/**
	 * 範囲シンボル.
	 */
	public static final String RANGE_SYMBOL = ":";

	/**
	 * シートシンボル.
	 */
	public static final String SHEET_SYMBOL = "!";

	/**
	 * 範囲の中で一番左列インデックス.
	 */
	private int minColIndex = 0;

	/**
	 * 範囲の中で一番右列インデックス.
	 */
	private int maxColIndex = 0;

	/**
	 * 範囲の中で一番上行インデックス.
	 */
	private int minRowIndex = 0;

	/**
	 * 範囲の中で一番下行インデックス.
	 */
	private int maxRowIndex = 0;

	/**
	 * 範囲内全座標オブジェクト.
	 */
	private List<Point> points = new ArrayList<Point>();

	/**
	 * シート名.
	 */
	private String sheetName;

	/**
	 * 指定座標オブジェクト１.
	 */
	private Point orignalPoint1;

	/**
	 * 指定座標オブジェクト２.
	 */
	private Point orignalPoint2;

	/**
	 * コンストラクタ.
	 *
	 * @param point 指定座標オブジェクト
	 */
	public Range(Point point) {
		this(String.class.cast(null), point);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param sheetName シート名
	 * @param point 指定座標オブジェクト
	 */
	public Range(String sheetName, Point point) {

		this.orignalPoint1 = point.clone();
		this.sheetName = sheetName;

		minColIndex = point.getColIndex();
		maxColIndex = point.getColIndex();
		minRowIndex = point.getRowIndex();
		maxRowIndex = point.getRowIndex();

		buildPoints();

	}

	/**
	 * コンストラクタ.
	 *
	 * @param point1 指定座標オブジェクト１
	 * @param point2 指定座標オブジェクト２
	 */
	public Range(Point point1, Point point2) {
		this(null, point1, point2);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param sheetName シート名
	 * @param point1 指定座標オブジェクト１
	 * @param point2 指定座標オブジェクト２
	 */
	public Range(String sheetName, Point point1, Point point2) {

		this.orignalPoint1 = point1.clone();
		this.orignalPoint2 = point2.clone();
		this.sheetName = sheetName;

		minColIndex = point1.getColIndex();
		maxColIndex = point1.getColIndex();
		if (point2.getColIndex() < minColIndex) {
			minColIndex = point2.getColIndex();
		}
		if (point2.getColIndex() > maxColIndex) {
			maxColIndex = point2.getColIndex();
		}
		minRowIndex = point1.getRowIndex();
		maxRowIndex = point1.getRowIndex();
		if (point2.getRowIndex() < minRowIndex) {
			minRowIndex = point2.getRowIndex();
		}
		if (point2.getRowIndex() > maxRowIndex) {
			maxRowIndex = point2.getRowIndex();
		}

		buildPoints();

	}

	/**
	 * 範囲内全座標オブジェクトを生成します.
	 *
	 */
	protected void buildPoints() {
		for (int rowIndex = minRowIndex; rowIndex <= maxRowIndex; rowIndex++) {
			for (int colIndex = minColIndex; colIndex <= maxColIndex; colIndex++) {
				points.add(new Point(orignalPoint1.getSpreadType(), rowIndex, colIndex));
			}
		}
	}

	/**
	 * 引数の座標がこの範囲オブジェクトの範囲内か判定し返します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:範囲内/false:範囲外
	 */
	public boolean contains(Point point) {
		return points.contains(point);
	}

	/**
	 * 引数の座標より左に座標オブジェクトが存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最左ではない/false:範囲外または最左
	 */
	public boolean hasLeft(Point point) {
		return contains(point) && minColIndex < point.getColIndex();
	}

	/**
	 * 引数の座標より右に座標オブジェクトが存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最右ではない/false:範囲外または最右
	 */
	public boolean hasRight(Point point) {
		return contains(point) && point.getColIndex() < maxColIndex ;
	}

	/**
	 * 引数の座標より上に座標オブジェクトが存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最上ではない/false:範囲外または最上
	 */
	public boolean hasUp(Point point) {
		return contains(point) && minRowIndex < point.getRowIndex();
	}

	/**
	 * 引数の座標より下に座標オブジェクトが存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最下ではない/false:範囲外または最下
	 */
	public boolean hasDown(Point point) {
		return contains(point) && point.getRowIndex() < maxRowIndex ;
	}

	/**
	 * 引数の座標が最左に存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最左/false:最左ではない
	 */
	public boolean isLeftmost(Point point) {
		return minColIndex == point.getColIndex();
	}

	/**
	 * 引数の座標が最右に存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最右/false:最右ではない
	 */
	public boolean isRightmost(Point point) {
		return maxColIndex == point.getColIndex();
	}

	/**
	 * 引数の座標が最上に存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最上/false:最上ではない
	 */
	public boolean isTopmost(Point point) {
		return minRowIndex == point.getRowIndex();
	}

	/**
	 * 引数の座標が最下に存在するか判定します.
	 *
	 * @param point 対象座標オブジェクト
	 * @return true:最下/false:最下ではない
	 */
	public boolean isBottommost(Point point) {
		return maxRowIndex == point.getRowIndex();
	}

	/**
	 * 範囲内全座標オブジェクトを{@link List}で返します.
	 *
	 * <p>
	 * 座標オブジェクトの順番は最左上から最右上、最左下から最右下の様に
	 * Zの軌道で返します.
	 * </p>
	 *
	 * @return 全座標イテレーター
	 */
	public List<Point> getPoints() {
		return Collections.unmodifiableList(points);
	}

	/**
	 * 範囲内全座標オブジェクトをイテレーターで返します.
	 *
	 * <p>
	 * 座標オブジェクトの順番は最左上から最右上、最左下から最右下の様に
	 * Zの軌道で返します.
	 * </p>
	 *
	 * @return 全座標イテレーター
	 */
	public ListIterator<Point> iterator() {
		return getPoints().listIterator();
	}

	/**
	 * 範囲オブジェクトの列数を返します.
	 *
	 * @return 列数
	 */
	public int getColCount() {
		return maxColIndex - minColIndex + 1;
	}

	/**
	 * 範囲オブジェクトの行数を返します.
	 *
	 * @return 行数
	 */
	public int getRowCount() {
		return maxRowIndex - minRowIndex + 1;
	}

	/**
	 * ハッシュ値を返します.
	 *
	 * @return ハッシュ値
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += minColIndex;
		hashCode *= 32;
		hashCode += maxColIndex;
		hashCode *= 32;
		hashCode += minRowIndex;
		hashCode *= 32;
		hashCode += maxRowIndex;
		hashCode *= 32;
		hashCode += points.hashCode();
		return hashCode;
	}

	/**
	 * オブジェクトの同値性を判定します.
	 *
	 * @param obj 比較対象
	 * @return true:同値/false:同値ではない
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(getClass().isAssignableFrom(obj.getClass()))) {
			return false;
		}
		Range same = Range.class.cast(obj);
		return minColIndex == same.minColIndex
			&& maxColIndex == same.maxColIndex
			&& minRowIndex == same.minRowIndex
			&& maxRowIndex == same.maxRowIndex
			&& points.equals(same.points);
	}

	/**
	 * 範囲座標の参照式を返します.
	 *
	 * @return 参照式
	 */
	public String toReferenceStyle() {
		String sheetPrefix = "";
		if (sheetName != null) {
			sheetPrefix = sheetName + SHEET_SYMBOL;
		}
		if (orignalPoint2 == null) {
			return sheetPrefix + orignalPoint1.toReferenceStyle();
		}
		return sheetPrefix + orignalPoint1.toReferenceStyle() + RANGE_SYMBOL + orignalPoint2.toReferenceStyle();
	}

}
