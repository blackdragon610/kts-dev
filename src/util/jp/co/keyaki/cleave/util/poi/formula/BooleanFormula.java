package jp.co.keyaki.cleave.util.poi.formula;

import org.apache.commons.lang.ObjectUtils;

/**
 * Boolean型リテラル値を表現するクラス.
 *
 * @author ytakahashi
 */
public class BooleanFormula implements LiteralFormula, BooleableFormula {

	/**
	 * NULL評価時リテラル値.
	 */
	public static final Boolean NULL_VALUE = Boolean.FALSE;

	/**
	 * リテラル値
	 */
	private Boolean value;

	/**
	 * コンストラクタ.
	 *
	 * @param value
	 */
	public BooleanFormula(Boolean value) {
		this.value = value;
	}

	/**
	 * 参照式を返します.
	 *
	 * @return 参照式
	 */
	public String toReferenceStyle() {
		return ObjectUtils.toString(value, NULL_VALUE.toString());
	}

}
