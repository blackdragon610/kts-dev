package jp.co.keyaki.cleave.util.poi.formula;

/**
 * NOT関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class NotFunction extends AbstractFunction implements BooleableFormula {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "NOT";

	/**
	 * 真偽反転対象参照可能オブジェクト.
	 */
	private BooleableFormula booleableFormula = null;

	/**
	 * コンストラクタ.
	 *
	 * @param booleableFormula 真偽反転対象参照可能オブジェクト
	 */
	public NotFunction(BooleableFormula booleableFormula) {
		this.booleableFormula = booleableFormula;
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
		return booleableFormula.toReferenceStyle();
	}
}
