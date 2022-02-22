package jp.co.keyaki.cleave.util.poi.formula;

import java.math.BigDecimal;

/**
 * Decimal型リテラル値を表現するクラス.
 *
 * @author ytakahashi
 */
public class DecimalFormula implements LiteralFormula {

	/**
	 * NULL評価時リテラル値.
	 */
	public static final BigDecimal NULL_VALUE = BigDecimal.ZERO;

	/**
	 * リテラル値
	 */
	private BigDecimal value;

	/**
	 * コンストラクタ.
	 *
	 * @param value
	 */
	public DecimalFormula(BigDecimal value) {
		this.value = value;
	}

	/**
	 * 参照式を返します.
	 *
	 * @return 参照式
	 */
	public String toReferenceStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append(value == null ? NULL_VALUE.toPlainString() : value.toPlainString());
		return sb.toString();
	}

}
