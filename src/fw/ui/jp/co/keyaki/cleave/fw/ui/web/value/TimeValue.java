package jp.co.keyaki.cleave.fw.ui.web.value;

/**
 * 時分秒を表現するクラス.
 *
 * @author
 *
 */
public class TimeValue extends DateTimeValue {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ.
	 */
	public TimeValue() {
		this(FORMAT_PATTERN_TIME);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param format 文字列表現時におけるフォーマット
	 */
	public TimeValue(String format) {
		super(format);
	}

}
