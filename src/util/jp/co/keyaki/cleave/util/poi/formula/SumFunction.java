package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

/**
 * SUM関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class SumFunction extends AbstractFunction {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "SUM";

	/**
	 * 対象参照可能オブジェクト群.
	 */
	private Referencable[] refs = null;

	/**
	 * コンストラクタ.
	 *
	 * @param refs 対象参照可能オブジェクト群
	 */
	public SumFunction(Referencable... refs) {
		this.refs = refs;
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
		return ReferenceUtils.toReferenceStyleJoin(SYMBOL_COMMA, refs);
	}
}
