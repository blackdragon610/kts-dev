package jp.co.keyaki.cleave.util.poi.formula;

/**
 * TRUE関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class TrueFunction extends AbstractFunction implements BooleableFormula {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "TRUE";

	/**
	 * コンストラクタ.
	 */
	public TrueFunction() {
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
		return "";
	}
}
