package jp.co.keyaki.cleave.fw.ui.web.value;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * {@link String}と{@link Date}の型を吸収するためのアダプタークラス.
 *
 * <p>
 * WEBでの日付をあらわすデータを表現する事を前提としているため、
 * 内部的には文字列で値の保持をしている.
 * </p>
 *
 * @author
 */
public abstract class DateTimeValue implements Serializable {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ロガー.
	 */
	private static final Log LOG = LogFactory.getLog(DateTimeValue.class);

	/**
	 * 日付型書式：年月日
	 */
	public static final String FORMAT_PATTERN_DATE = "yyyy/MM/dd";
	/**
	 * 日付型書式：時分
	 */
	public static final String FORMAT_PATTERN_HOUR_MINUTE = "HH:mm";
	/**
	 * 日付型書式：時分秒
	 */
	public static final String FORMAT_PATTERN_TIME = FORMAT_PATTERN_HOUR_MINUTE + ":ss";
	/**
	 * 日付型書式：時分秒ミリ秒
	 */
	public static final String FORMAT_PATTERN_TIME_MILLI = FORMAT_PATTERN_TIME + ".SSS";
	/**
	 * 日付型書式：年月日時分秒ミリ秒
	 */
	public static final String FORMAT_PATTERN_TIMESTAMP = FORMAT_PATTERN_DATE + " " + FORMAT_PATTERN_TIME_MILLI;

	/**
	 * フォーマット.
	 */
	private String format;

	/**
	 * 文字列型日付データ.
	 */
	private String value;

	/**
	 * コンストラクタ.
	 *
	 * @param format 日付書式
	 */
	public DateTimeValue(String format) {
		this.format = format;
	}

	/**
	 * 文字列型の日付データを返します.
	 *
	 * @return 文字列型の日付データ
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 文字列型の日付データを設定します.
	 *
	 * @param value 文字列型の日付データ
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 日付型の日付データを返します.
	 *
	 * @return 日付型の日付データ
	 */
	public Date getDateTime() {
		String value = getValue();
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return toDate(value);
	}

	/**
	 * 日付型の日付データを設定します.
	 *
	 * @param date 日付型の日付データ
	 */
	public void setDateTime(Date date) {
		setValue(null);
		if (date == null) {
			return;
		}
		setValue(toString(date));
	}

	/**
	 * このインスタンスの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * 文字列型の日付データを日付型にして返します.
	 *
	 * @param value 文字列型日付データ
	 * @return 日付型日付データ
	 */
	protected Date toDate(String value) {
		SimpleDateFormat formatter = getFormatter();
		try {
			Date date = formatter.parse(value);
			return date;
		} catch (ParseException pe) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("value=" + value, pe);
			}
		}
		return null;
	}

	/**
	 * 日付型の日付データを文字列型にして返します.
	 *
	 * @param date 日付型日付データ
	 * @return 文字列型日付データ
	 */
	protected String toString(Date date) {
		SimpleDateFormat formatter = getFormatter();
		String value = formatter.format(date);
		return value;
	}

	/**
	 * フォーマッターを返します.
	 *
	 * @return フォーマッター
	 */
	protected SimpleDateFormat getFormatter() {
		String format = getFormat();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter;
	}

	/**
	 * フォーマットを返します.
	 *
	 * @return フォーマット
	 */
	protected String getFormat() {
		return format;
	}

}
