package jp.co.keyaki.cleave.fw.ui.web.value;

/**
 * 年月日時分秒を表現するクラス.
 *
 * @author
 *
 */
public class TimestampValue extends DateTimeValue {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ.
	 */
	public TimestampValue() {
		this(FORMAT_PATTERN_TIMESTAMP);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param format 文字列表現時におけるフォーマット
	 */
	public TimestampValue(String format) {
		super(format);
	}

}
