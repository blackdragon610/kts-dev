package jp.co.keyaki.cleave.util.poi.formula;

import org.apache.commons.lang.ObjectUtils;


/**
 * Integer型リテラル値を表現するクラス.
 *
 * @author ytakahashi
 */
public class IntegerFormula implements LiteralFormula {

	/**
	 * NULL評価時リテラル値.
	 */
	public static final Integer NULL_VALUE = new Integer(0);

	/**
	 * リテラル値
	 */
	private Integer value;

	/**
	 * コンストラクタ.
	 *
	 * @param value
	 */
	public IntegerFormula(Integer value) {
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
