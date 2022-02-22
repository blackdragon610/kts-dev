package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

/**
 * IF関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class IfFunction extends AbstractFunction {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "IF";

	/**
	 * 評価対象参照可能オブジェクト
	 */
	private BooleableFormula booleableFormula;

	/**
	 * 真評価時参照可能オブジェクト
	 */
	private Referencable yes;

	/**
	 * 偽評価時参照可能オブジェクト
	 */
	private Referencable no;

	/**
	 * コンストラクタ.
	 *
	 * @param booleableFormula 評価対象参照可能オブジェクト
	 * @param yes 真評価時参照可能オブジェクト
	 * @param no 偽評価時参照可能オブジェクト
	 */
	public IfFunction(BooleableFormula booleableFormula, Referencable yes, Referencable no) {
		this.booleableFormula = booleableFormula;
		this.yes = yes;
		this.no = no;
	}

	/**
	 * 関数名を返します.
	 *
	 * @return 関数名
	 */
	@Override
	public String getFunctionName() {
		return FORMULA_SYMBOL;
	}

	/**
	 * 引数式を返します.
	 *
	 * @return 引数式
	 */
	@Override
	public String getArgs() {
		StringBuilder sb = new StringBuilder();
		sb.append(ReferenceUtils.toReferenceStyleJoin(SYMBOL_COMMA, booleableFormula, yes, no));
		return sb.toString();
	}
}
