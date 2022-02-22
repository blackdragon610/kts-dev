package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.CellFormula;

/**
 * 関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public abstract class AbstractFunction implements CellFormula {


	/**
	 * 参照式を返します.
	 *
	 * @return 参照式
	 */
	public String toReferenceStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append(getFunctionName());
		sb.append(SYMBOL_BRACKETS_OPEN);
		sb.append(getArgs());
		sb.append(SYMBOL_BRACKETS_CLOSE);
		return sb.toString();
	}

	/**
	 * 関数名を返します.
	 *
	 * @return 関数名
	 */
	public abstract String getFunctionName();

	/**
	 * 引数式を返します.
	 *
	 * @return 引数式
	 */
	public abstract String getArgs();

}
