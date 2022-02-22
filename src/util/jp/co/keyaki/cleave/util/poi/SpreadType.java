package jp.co.keyaki.cleave.util.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * スプレットシートタイプ.
 *
 * @author ytakahashi
 */
public enum SpreadType {

	/**
	 * 旧タイプ.
	 */
	SPREAD_OLD(SpreadsheetVersion.EXCEL97),

	/**
	 * 新タイプ.
	 */
	SPREAD_NEW(SpreadsheetVersion.EXCEL2007);

	/**
	 * POI SpreadsheetVersion.
	 */
	private final SpreadsheetVersion version;

	/**
	 * コンストラクタ.
	 *
	 * @param version POI SpreadsheetVersion.
	 */
	private SpreadType(SpreadsheetVersion version) {
		this.version = version;
	}

	/**
	 *
	 * @param book
	 * @param index
	 * @return
	 */
	static Short getCustomColorIndex(Workbook book, short index) {
		SpreadType type = get(book);
		if (type.equals(SPREAD_NEW)) {
			return null;
		}
		HSSFWorkbook workbook = HSSFWorkbook.class.cast(book);
		HSSFColor color = workbook.getCustomPalette().getColor(index);
		if (color == null) {
			return null;
		}
		return color.getIndex();
	}


	/**
	 * ブックからスプレットシートタイプを判定し返します.
	 *
	 * @param book
	 * @return スプレットシートタイプ
	 */
	public static SpreadType get(Workbook book) {
		if (XSSFWorkbook.class.isAssignableFrom(book.getClass())) {
			return SPREAD_NEW;
		}
		return SPREAD_OLD;
	}

	/**
	 * シートからスプレットシートタイプを判定し返します.
	 *
	 * @param sheet
	 * @return スプレットシートタイプ
	 */
	public static SpreadType get(Sheet sheet) {
		return get(sheet.getWorkbook());
	}

	/**
	 * 行からスプレットシートタイプを判定し返します.
	 *
	 * @param row
	 * @return スプレットシートタイプ
	 */
	public static SpreadType get(Row row) {
		return get(row.getSheet());
	}

	/**
	 * セルからスプレットシートタイプを判定し返します.
	 *
	 * @param cell
	 * @return スプレットシートタイプ
	 */
	public static SpreadType get(Cell cell) {
		return get(cell.getRow());
	}

	/**
	 * ブックインスタンスを生成します.
	 *
	 * @return ブック
	 */
	public Workbook createWorkbook() {
		if (SpreadType.SPREAD_OLD.equals(this)) {
			return new HSSFWorkbook();
		}
		return new XSSFWorkbook();
	}

	/**
	 * 最大有効行インデックスを返します.
	 *
	 * @return 最大有効行インデックス
	 */
	public int getLastRowIndex() {
		return version.getLastRowIndex();
	}

	/**
	 * 最大有効列インデックスを返します.
	 *
	 * @return 最大有効列インデックス
	 */
	public int getLastColumnIndex() {
		return version.getLastColumnIndex();
	}

	/**
	 * 行インデックスが指定可能範囲内か判定し返します.
	 *
	 * @param rowIndex 検査対象行インデックス
	 * @return true:範囲内/false:範囲外
	 */
	public boolean validateRowIndex(int rowIndex) {
		if (rowIndex < 0 || getLastRowIndex() < rowIndex) {
			return false;
		}
		return true;
	}

	/**
	 * 列インデックスが指定可能範囲内か判定し返します.
	 *
	 * @param colIndex 検査対象列インデックス
	 * @return true:範囲内/false:範囲外
	 */
	public boolean validateColIndex(int colIndex) {
		if (colIndex < 0 || getLastColumnIndex() < colIndex) {
			return false;
		}
		return true;
	}

	/**
	 * 行インデックスが指定可能範囲外の場合例外をスローします.
	 *
	 * @param rowIndex 検査対象行インデックス
	 * @throws IllegalArgumentException 行インデックスが範囲外の場合
	 */
	public void checkRowIndex(int rowIndex) throws IllegalArgumentException {
		if (!validateRowIndex(rowIndex)) {
			throw new IllegalArgumentException("行番号範囲外 MAX=" + getLastRowIndex() + ", rowIndex=" + rowIndex);
		}
	}

	/**
	 * 列インデックスが指定可能範囲外の場合例外をスローします.
	 *
	 * @param colIndex 検査対象列インデックス
	 * @throws IllegalArgumentException 列インデックスが範囲外の場合
	 */
	public void checkColIndex(int colIndex) throws IllegalArgumentException {
		if (!validateColIndex(colIndex)) {
			throw new IllegalArgumentException("列番号範囲外 MAX=" + getLastColumnIndex() + ", colIndex=" + colIndex);
		}
	}
}