package jp.co.keyaki.cleave.fw.core.util;

import java.sql.Timestamp;

import jp.co.keyaki.cleave.common.util.DateUtil;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 独自クラスを文字列値に変換するコンバーター.
 *
 * <p>
 * インスタンスを{@link java.sql.Timestamp}クラスに変換するためのコンバータークラス.<br>
 * {@link ConvertUtilsBean#register(Converter, Class)}を呼び出して
 * 登録される際は、第2引数として{@link java.sql.Timestamp#getClass()}の同値が指定されている事を前提としている.
 * したがって、{@link #convert(Class, Object)}が呼び出された際メソッド内では、第１引数については判断せず
 * 必ず{@link java.sql.Timestamp}のクラスに変換する振る舞いをとります.<br>
 * </p>
 *
 * @author
 * @see ConvertUtilsBean
 * @see Converter
 * @see DefaultConverter
 * @see TimestampConverter
 * @see CustomStringConverter
 */
public class TimestampConverter implements Converter {

	/**
	 * ロガー
	 */
	private static final Log LOG = LogFactory.getLog(TimestampConverter.class);

	/**
	 * {@link java.sql.Timestamp}型に変換します.
	 *
	 * @param targetType 変換後型
	 * @param value 値
	 * @return {@link java.sql.Timestamp}型の値
	 */
	@SuppressWarnings("rawtypes")
	public Object convert(Class targetType, Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof String) {
			Timestamp timestamp = DateUtil.stringToTimestamp((String)value);
			if (timestamp != null) {
				return timestamp;
			}
		}
		SqlTimestampConverter defaultConverter = new SqlTimestampConverter();
		return defaultConverter.convert(targetType, value);
	}
}