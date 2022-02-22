package jp.co.keyaki.cleave.common.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 *
 * 日付操作に関するユーティリティクラス。
 *
 * @author hsatou
 *
 */
public class DateUtil {

	private static final String DATE_PATTERN ="yyyy/MM/dd HH:mm";
	public static final String DATE_YYYYMMDD ="yyyy/MM/dd";
	private static final String MAIL_FORMAT_DATE_PATTERN ="MM月dd日 HH時mm分";

	private static final BigDecimal DATE_COMPUTED_FROM_MILLI_SECOND = new BigDecimal(1000*60*60*24);

	public static Timestamp stringToTimestamp(String value){;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		try {
			return new Timestamp(sdf.parse(value).getTime());
		} catch(ParseException e) {
			return null;
		}
	}

	public static String timestampToString(Timestamp timestamp){
		return new SimpleDateFormat(DATE_PATTERN).format(timestamp);
	}

	public static String timestampToStringMailFormat(Timestamp timestamp){
		return new SimpleDateFormat(MAIL_FORMAT_DATE_PATTERN).format(timestamp);
	}


    /**
     * <P>
     * システム時刻を指定のフォーマットに変換する。
     * </P>
     *
     * @param format
     *            フォーマット
     * @return フォーマットされた文字列
     */
    public static String dateToString(String format) {

        return dateToString(format, null);
    }
	/**
	 * <P>
	 * java.util.Date型を指定のフォーマットに変換する。
	 * </P>
	 *
	 * @param format
	 *            フォーマット
	 * @param currentTime
	 *            文字列に変換する時刻データ
	 * @return フォーマットされた文字列
	 */
	public static String dateToString(String format, Date currentTime) {

		// currentTimeがnullの場合はシステム日付をセットする
		if (currentTime == null) {
			currentTime = new Date();
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.getCalendar().setLenient(false);
		sdf.setLenient(false);
		return sdf.format(currentTime);
	}

	/**
	 * <P>
	 * 指定した日数分加算します。
	 * </P>
	 *
	 * @return 加算日付
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 文字列を指定書式で解析し日付型にして返します.
	 *
	 * @param value
	 *            文字列の日付
	 * @param format
	 *            書式
	 * @return 日付型（解析エラーの場合はnull)
	 */
	public static Date toDate(String value, String format) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		ParsePosition position = new ParsePosition(0);
		return dateFormat.parse(value, position);
	}

	public static String changeFormat(String date, String oldFormat, String newFormat) {
		if (StringUtils.isEmpty(date)) {
			return StringUtils.EMPTY;
		}
		Date old = toDate(date, oldFormat);
		if (old == null) {
			//return StringUtils.EMPTY;
			if (date.length()==5){
				return date;
			}
			return StringUtils.EMPTY;
		}
		return dateToString(newFormat, old);
	}

}



