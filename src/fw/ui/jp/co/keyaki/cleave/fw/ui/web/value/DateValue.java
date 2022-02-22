package jp.co.keyaki.cleave.fw.ui.web.value;

/**
 * 年月日を表現するクラス.
 *
 * @author
 *
 */
public class DateValue extends DateTimeValue {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ.
	 */
	public DateValue() {
		this(FORMAT_PATTERN_DATE);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param format 文字列表現時におけるフォーマット
	 */
	public DateValue(String format) {
		super(format);
	}

}
