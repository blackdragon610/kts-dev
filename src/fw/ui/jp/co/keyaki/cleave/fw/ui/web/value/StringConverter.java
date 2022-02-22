package jp.co.keyaki.cleave.fw.ui.web.value;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 独自クラスを文字列値に変換するコンバーター.
 *
 * <p>
 * {@link DateTimeValue}の派生クラスのインスタンスを{@link String}に変換するためのコンバータークラス.<br>
 * {@link DateTimeValue}の派生クラスのインスタンス以外の場合は、
 * {@link DefaultConverter#convert(Class, Object)}を呼び出して処理を委譲します.<br>
 * <br>
 * {@link ConvertUtilsBean#register(Converter, Class)}を呼び出して
 * 登録される際は、第2引数として{@link String#getClass()}の同値が指定されている事を前提としている.
 * したがって、{@link #convert(Class, Object)}が呼び出された際メソッド内では、第１引数については判断せず
 * 必ず{@link String}に変換する振る舞いをとります.
 * </p>
 *
 * @author
 * @see ConvertUtilsBean
 * @see Converter
 * @see DefaultConverter
 * @see DateTimeValueConverter
 * @see JdkDateConverter
 */
public class StringConverter implements Converter {

	/**
	 * ロガー.
	 */
	private static final Log LOG = LogFactory.getLog(StringConverter.class);

	/**
	 * 文字列型に変換します.
	 *
	 * @param targetType 変換後型
	 * @param value 値
	 * @return 文字列型の値
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convert(Class targetType, Object value) {
		if (value == null) {
			return null;
		}
		if (LOG.isTraceEnabled()) {
			LOG.trace("targetType is " + targetType + ". value type is " + value.getClass() + ". value is " + value);
		}
		if (DateTimeValue.class.isAssignableFrom(value.getClass())) {
			return DateTimeValue.class.cast(value).getValue();
		}
		Object source = value;
		return DefaultConverter.convert(targetType, source);
	}
}
