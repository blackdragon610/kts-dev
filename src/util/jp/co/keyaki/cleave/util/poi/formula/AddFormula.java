package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.CellFormula;
import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

/**
 * 足し算を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class AddFormula implements CellFormula {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "+";

	/**
	 * 対象参照可能オブジェクト群.
	 */
	private Referencable[] refs = null;

	/**
	 * コンストラクタ.
	 *
	 * @param refs 対象参照可能オブジェクト群
	 */
	public AddFormula(Referencable... refs) {
		this.refs = refs;
	}

	/**
	 * 参照式を返します.
	 *
	 * @return 参照式
	 */
	public String toReferenceStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append(ReferenceUtils.toReferenceStyleJoin(FORMULA_SYMBOL, refs));
		return sb.toString();
	}
}
