package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.Referencable;

/**
 * ISNA関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class IsNaFunction extends AbstractFunction implements BooleableFormula {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "ISNA";

	/**
	 * 判定対象参照可能オブジェクト.
	 */
	private Referencable ref;

	/**
	 * コンストラクタ.
	 *
	 * @param ref 判定対象参照可能オブジェクト
	 */
	public IsNaFunction(Referencable ref) {
		this.ref = ref;
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
		return ref.toReferenceStyle();
	}
}
