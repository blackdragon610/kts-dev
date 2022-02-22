package jp.co.keyaki.cleave.util.poi;

/**
 * 参照可能を表現し参照式を作り出すインターフェース.
 *
 * @author ytakahashi
 *
 */
public interface Referencable {

	/**
	 * 括弧：(
	 */
	public static final String SYMBOL_BRACKETS_OPEN = "(";
	/**
	 * 括弧：)
	 */
	public static final String SYMBOL_BRACKETS_CLOSE = ")";

	/**
	 * カンマ.
	 */
	public static final String SYMBOL_COMMA = ",";

	/**
	 * 参照式を返します.
	 *
	 * @return 参照式
	 */
	String toReferenceStyle();

}
