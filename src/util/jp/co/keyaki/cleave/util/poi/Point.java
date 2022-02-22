/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 座標位置を表現するクラス.
 *
 * @author ytakahashi
 *
 */
public class Point implements Comparable<Point>, Cloneable, Referencable {

	/**
	 * A1スタイルの列ラベル定数.
	 */
	public static final List<String> A1_STYLE_COL_LABELS = Collections.unmodifiableList(Arrays.asList(new String[] {
		"A", "B", "C", "D", "E",
		"F", "G", "H", "I", "J",
		"K", "L", "M", "N", "O",
		"P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y",
		"Z"
		}));

	/**
	 * 絶対指定シンボル.
	 */
	public static final String ABSOLUTE_SYMBOL = "$";

	/**
	 * 行インデックス最小値.
	 */
	public static final int ROW_INDEX_MIN = 0;

	/**
	 * 列インデックス最小値.
	 */
	public static final int COL_INDEX_MIN = 0;

	/**
	 * 1の定数
	 */
	public static final int INT_ONE = 1;
	/**
	 * -1の定数
	 */
	public static final int INT_MINUS_ONE = -1;

	/**
	 *
	 */
	private SpreadType spreadType;

	/**
	 * 行インデックス.
	 */
	private int rowIndex;

	/**
	 * 列インデックス.
	 */
	private int colIndex;

	/**
	 * 行の絶対表現
	 */
	private boolean rowAbsolute;

	/**
	 * 列の絶対表現
	 */
	private boolean colAbsolute;

	/**
	 * コンストラクタ.
	 *
	 * <p>
	 * このコンストラクタで作成された、座標位置オブジェクトは
	 * 参照表現時は相対表現となります.
	 * </p>
	 *
	 * @param spreadType スプレットシートタイプ
	 * @param rowIndex 行インデックス
	 * @param colIndex 列インデックス
	 * @throws IllegalArgumentException 不正なインデックスを指定した場合.
	 * @see SpreadType#checkRowIndex(int)
	 * @see SpreadType#checkColIndex(int)
	 */
	protected Point(SpreadType spreadType, int rowIndex, int colIndex) throws IllegalArgumentException {
		this(spreadType, rowIndex, colIndex, false, false);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param spreadType スプレットシートタイプ
	 * @param rowIndex 行インデックス
	 * @param colIndex 列インデックス
	 * @param rowAbsolute 行絶対表現
	 * @param colAbsolute 列絶対表現
	 * @throws IllegalArgumentException 不正なインデックスを指定した場合.
	 * @see SpreadType#checkRowIndex(int)
	 * @see SpreadType#checkColIndex(int)
	 */
	protected Point(SpreadType spreadType, int rowIndex, int colIndex, boolean rowAbsolute, boolean colAbsolute) throws IllegalArgumentException {
		setSpreadType(spreadType);
		setRowIndex(rowIndex);
		setColIndex(colIndex);
		setRowAbsolute(rowAbsolute);
		setColAbsolute(colAbsolute);
	}

	/**
	 * スプレットシートタイプを返します.
	 *
	 * @return スプレットシートタイプ
	 */
	public SpreadType getSpreadType() {
		return spreadType;
	}

	/**
	 * スプレットシートタイプを設定します.
	 *
	 * @param spreadType スプレットシートタイプ
	 */
	protected void setSpreadType(SpreadType spreadType) {
		this.spreadType = spreadType;
	}

	/**
	 * 行インデックスを返します.
	 *
	 * @return 行インデックス
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * 行インデックスを設定します.
	 *
	 * @param rowIndex 行インデックス
	 * @throws IllegalArgumentException 不正な行インデックスを指定した場合.
	 * @see SpreadType#checkRowIndex(int)
	 */
	protected void setRowIndex(int rowIndex) throws IllegalArgumentException {

		getSpreadType().checkRowIndex(rowIndex);

		this.rowIndex = rowIndex;
	}

	/**
	 * 列インデックスを返します.
	 *
	 * @return 列インデックス
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * 列インデックスを設定します.
	 *
	 * @param colIndex 列インデックス
	 * @throws IllegalArgumentException 不正な列インデックスを指定した場合.
	 * @see SpreadType#checkColIndex(int)
	 */
	protected void setColIndex(int colIndex) throws IllegalArgumentException {

		getSpreadType().checkColIndex(colIndex);

		this.colIndex = colIndex;
	}

	/**
	 * このオブジェクトが行絶対表現か判定し返します.
	 *
	 * @return true:行絶対表現/false:行相対表現
	 */
	public boolean isRowAbsolute() {
		return rowAbsolute;
	}

	/**
	 * このオブジェクトの行絶対表現を指定します.
	 *
	 * @param rowAbsolute true:行絶対表現/false:行相対表現
	 */
	protected void setRowAbsolute(boolean rowAbsolute) {
		this.rowAbsolute = rowAbsolute;
	}

	/**
	 * このオブジェクトが列絶対表現か判定し返します.
	 *
	 * @return true:列絶対表現/false:列相対表現
	 */
	public boolean isColAbsolute() {
		return colAbsolute;
	}

	/**
	 * このオブジェクトの列絶対表現を指定します.
	 *
	 * @param colAbsolute true:列絶対表現/false:列相対表現
	 */
	protected void setColAbsolute(boolean colAbsolute) {
		this.colAbsolute = colAbsolute;
	}

	/**
	 * この座標位置の１つ左の列の座標位置を返します.
	 *
	 * @return １つ左の座標位置(この座標位置が最左のインデックスを保有していた場合はnull）
	 */
	public Point getLeftPoint() {
		int left = getColIndex();
		left += INT_MINUS_ONE;
		if (!getSpreadType().validateColIndex(left)) {
			return null;
		}
		return new Point(getSpreadType(), getRowIndex(), left, isRowAbsolute(), isColAbsolute());
	}

	/**
	 * この座標位置の１つ右の列の座標位置を返します.
	 *
	 * @return １つ右の座標位置(この座標位置が最右のインデックスを保有していた場合はnull）
	 */
	public Point getRightPoint() {
		int right = getColIndex();
		right += INT_ONE;
		if (!getSpreadType().validateColIndex(right)) {
			return null;
		}
		return new Point(getSpreadType(), getRowIndex(), right, isRowAbsolute(), isColAbsolute());
	}

	/**
	 * この座標位置の１つ上の行の座標位置を返します.
	 *
	 * @return １つ上の座標位置(この座標位置が最上のインデックスを保有していた場合はnull）
	 */
	public Point getUpPoint() {
		int up = getRowIndex();
		up += INT_MINUS_ONE;
		if (!getSpreadType().validateRowIndex(up)) {
			return null;
		}
		return new Point(getSpreadType(), up, getColIndex(), isRowAbsolute(), isColAbsolute());
	}

	/**
	 * この座標位置の１つ下の行の座標位置を返します.
	 *
	 * @return １つ下の座標位置(この座標位置が最下のインデックスを保有していた場合はnull）
	 */
	public Point getDownPoint() {
		int down = getRowIndex();
		down += INT_ONE;
		if (!getSpreadType().validateRowIndex(down)) {
			return null;
		}
		return new Point(getSpreadType(), down, getColIndex(), isRowAbsolute(), isColAbsolute());
	}

	/**
	 * オブジェクトのハッシュ値を返します.
	 *
	 * @return ハッシュ値
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += rowIndex;
		hashCode *= 32;
		hashCode += colIndex;
		return hashCode;
	}

	/**
	 * オブジェクトの同値性を判定します.
	 *
	 * <p>
	 * このクラスの同値性は下記のフィールドを利用して判断します.
	 * <ul>
	 * <li>行インデックス</li>
	 * <li>列インデックス</li>
	 * </ul>
	 * つまり位置が一緒であるかを判断することとなり
	 * 参照表現時の使用される絶対表現/相対表現は同値性を判定する際には無視します.
	 * </p>
	 *
	 * @param obj 比較対象
	 * @return true:同値/false:同値ではない
	 *
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
		Point same = Point.class.cast(obj);
		return rowIndex == same.rowIndex && colIndex == same.colIndex;
	}

	/**
	 * オブジェクトの大小関係を判断します.
	 *
	 * 左上に位置する座標位置オブジェクトが小さいと判断し、
	 * 右下に位置する座標位置オブジェクトが大きいと判断します.
	 *
	 * @param point 比較対象
	 * @return マイナス値:このオブジェクトの方が小さい 0:同値 プラス値:このオブジェクトの方が大きい
	 * @see #compareRowCol(Point, Point)
	 */
	public int compareTo(Point point) {
		return compareRowCol(this, point);
	}

	/**
	 * オブジェクトの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	public String toString() {
		return "point " + toReferenceStyle() +  " (row=" + rowIndex + ", col=" + colIndex + ")";
	}

	/**
	 * この座標位置の位置に対するA1書式での参照文字列を返します.
	 *
	 * @return A1書式での参照文字列
	 */
	public String toReferenceStyle() {
		return toA1ReferenceStyleString(isRowAbsolute(), isColAbsolute());
	}

	/**
	 * この座標位置の位置に対するA1書式での参照文字列を返します.
	 *
	 * @param rowAbsolute 行を絶対参照表現するか
	 * @param colAbsolute 列を絶対参照表現するか
	 * @return A1書式での参照文字列
	 */
	public String toA1ReferenceStyleString(boolean rowAbsolute, boolean colAbsolute) {
		String result = "";
		if (colAbsolute) {
			result += ABSOLUTE_SYMBOL;
		}
		result += toA1StyleColLabel(getSpreadType(), getColIndex());
		if (rowAbsolute) {
			result += ABSOLUTE_SYMBOL;
		}
		result += (rowIndex + 1);
		return result;
	}

	/**
	 * インスタンスのクローンを作成し返します.
	 *
	 * @return クローン
	 */
	@Override
	public Point clone() {

		return new Point(spreadType, rowIndex, colIndex, rowAbsolute, colAbsolute);

	}

	/**
	 * 引数の列インデックスに対するA1スタイルの列ラベルを返します.
	 *
	 * @param colIndex 列インデックス
	 * @return A1スタイルの列ラベル
	 * @throws IllegalArgumentException 不正な列インデックスを指定した場合.
	 * @see SpreadType#checkColIndex(int)
	 */
	public static String toA1StyleColLabel(SpreadType spreadType, int colIndex) throws IllegalArgumentException {

		spreadType.checkColIndex(colIndex);

		String result = "";
		int divide = colIndex / A1_STYLE_COL_LABELS.size();
		int remainder = colIndex % A1_STYLE_COL_LABELS.size();
		if (divide > 0) {
			result = toA1StyleColLabel(spreadType, divide - 1);
		}
		return result + A1_STYLE_COL_LABELS.get(remainder);
	}

	/**
	 * 座標オブジェクトの比較順定義.
	 *
	 * @author ytakahashi
	 *
	 */
	public static enum ComparePriority implements Comparator<Point> {
		/**
		 * 行位置を最初に比較し同行の場合列位置を比較する.
		 *
		 * @see Point#compareRowCol(Point, Point)
		 */
		ROW_COL() {
			public int compare(Point point1, Point point2) {
				return compareRowCol(point1, point2);
			}
		},
		/**
		 * 列位置を最初に比較し同列の場合行位置を比較する.
		 *
		 * @see Point#compareColRow(Point, Point)
		 */
		COL_ROW() {
			public int compare(Point point1, Point point2) {
				return compareColRow(point1, point2);
			}
		},
	}

	/**
	 * 行位置を最初に比較し同行の場合列位置を比較する.
	 *
	 * @param point1 比較対象1
	 * @param point2 比較対象2
	 * @return マイナス値:比較対象1の方が小さい 0:同値 プラス値:比較対象1の方が大きい
	 */
	public static int compareRowCol(Point point1, Point point2) {
		int compare = 0;
		compare = point1.getRowIndex() - point2.getRowIndex();
		if (compare != 0) {
			return compare;
		}
		compare = point1.getColIndex() - point2.getColIndex();
		return compare ;
	}

	/**
	 * 列位置を最初に比較し同列の場合行位置を比較する.
	 *
	 * @param point1 比較対象1
	 * @param point2 比較対象2
	 * @return マイナス値:比較対象1の方が小さい 0:同値 プラス値:比較対象1の方が大きい
	 */
	public static int compareColRow(Point point1, Point point2) {
		int compare = 0;
		compare = point1.getColIndex() - point2.getColIndex();
		if (compare != 0) {
			return compare;
		}
		compare = point1.getRowIndex() - point2.getRowIndex();
		return compare ;
	}

//	/**
//	 * 座標オブジェクト順序付けクラス.
//	 *
//	 * @author ytakahashi
//	 * @see java.util.Collections#reverseOrder(Comparator)
//	 *
//	 */
//	public static class PointComparator implements Comparator<Point> {
//
//		/**
//		 * 座標オブジェクトの比較順定義.
//		 */
//		private ComparePriority priority;
//
//		/**
//		 * コンストラクタ.
//		 *
//		 * <p>
//		 * 座標オブジェクトの比較順定義は{@link ComparePriority#ROW_COL}となります.
//		 * </p>
//		 * @see ComparePriority
//		 */
//		public PointComparator() {
//			this(ComparePriority.ROW_COL);
//		}
//
//		/**
//		 * コンストラクタ.
//		 *
//		 * @param priority 座標オブジェクトの比較順定義
//		 * @see ComparePriority
//		 */
//		public PointComparator(ComparePriority priority) {
//			this.priority = priority;
//		}
//
//		/**
//		 * オブジェクトの比較を行います.
//		 *
//		 * <p>
//		 * 座標オブジェクトの比較順定義により、行列の比較順が異なります.
//		 * </p>
//		 *
//		 * @param point1 比較対象1
//		 * @param point2 比較対象2
//		 * @return マイナス値:比較対象1の方が小さい 0:同値 プラス値:比較対象1の方が大きい
//		 * @see ComparePriority
//		 */
//		public int compare(Point point1, Point point2) {
//			return priority.compare(point1, point2);
//		}
//
//	}

}
