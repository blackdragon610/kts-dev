package jp.co.keyaki.cleave.fw.core.util;

import java.sql.Timestamp;

import jp.co.keyaki.cleave.common.util.DateUtil;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 独自クラスを文字列値に変換するコンバーター.
 *
 * <p>
 * インスタンスを{@link java.lang.String}クラスに変換するためのコンバータークラス.<br>
 * {@link ConvertUtilsBean#register(Converter, Class)}を呼び出して
 * 登録される際は、第2引数として{@link java.lang.String#getClass()}の同値が指定されている事を前提としている.
 * したがって、{@link #convert(Class, Object)}が呼び出された際メソッド内では、第１引数については判断せず
 * 必ず{@link java.lang.String}のクラスに変換する振る舞いをとります.<br>
 * </p>
 *
 * @author
 * @see ConvertUtilsBean
 * @see Converter
 * @see DefaultConverter
 * @see CustomStringConverter
 */
public class CustomStringConverter implements Converter {

	/**
	 * ロガー
	 */
	private static final Log LOG = LogFactory.getLog(CustomStringConverter.class);

	/**
	 * {@link java.lang.String}型に変換します.
	 *
	 * @param targetType 変換後型
	 * @param value 値
	 * @return {@link java.lang.String}型の値
	 */
	@SuppressWarnings("rawtypes")
	public Object convert(Class targetType, Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof Timestamp) {
			String timestamp = DateUtil.timestampToString((Timestamp)value);
			if (timestamp != null) {
				return timestamp;
			}
		}
		StringConverter defaultConverter = new StringConverter();
		return defaultConverter.convert(targetType, value);
	}
}