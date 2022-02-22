package jp.co.keyaki.cleave.util.poi.formula;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * String型リテラル値を表現するクラス.
 *
 * @author ytakahashi
 */
public class StringFormula implements LiteralFormula {

	/**
	 * NULL評価時リテラル値.
	 */
	public static final String NULL_VALUE = StringUtils.EMPTY;

	/**
	 * 評価シンボル.
	 */
	public static final String FORMULA_SYMBOL = "\"";

	/**
	 * リテラル値
	 */
	private String value;

	/**
	 * コンストラクタ.
	 *
	 * @param value
	 */
	public StringFormula(String value) {
		this.value = value;
	}

	/**
	 * 参照式を返します.
	 *
	 * リテラル値に{@link #FORMULA_SYMBOL}が含まれる場合は、エスケープを実施します.
	 *
	 * @return 参照式
	 */
	public String toReferenceStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append(FORMULA_SYMBOL);
		sb.append(StringUtils.replace(ObjectUtils.toString(value, NULL_VALUE.toString()), FORMULA_SYMBOL, FORMULA_SYMBOL + FORMULA_SYMBOL));
		sb.append(FORMULA_SYMBOL);
		return sb.toString();
	}

}
