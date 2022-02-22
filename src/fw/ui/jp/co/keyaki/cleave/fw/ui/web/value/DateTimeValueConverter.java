package jp.co.keyaki.cleave.fw.ui.web.value;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 独自クラスを文字列値に変換するコンバーター.
 *
 * <p>
 * インスタンスを{@link DateTimeValue}の派生クラスに変換するためのコンバータークラス.<br>
 * {@link DateTimeValue}の派生クラスのインスタンス以外の場合は、
 * いったん{@link java.util.Date}に変換した後、{@link DateTimeValue}としてラップします.<br>
 * <br>
 * {@link ConvertUtilsBean#register(Converter, Class)}を呼び出して
 * 登録される際は、第2引数として{@link DateTimeValue#getClass()}の同値が指定されている事を前提としている.
 * したがって、{@link #convert(Class, Object)}が呼び出された際メソッド内では、第１引数については判断せず
 * 必ず{@link DateTimeValue}の派生クラスに変換する振る舞いをとります.<br>
 * またさらに、変換元のオブジェクトが<code>null</code>の場合、変換後の値としては{@link DateTimeValue}の派生クラスの
 * インスタンスは存在するが、内部で保持している文字列型の日付データがnullという形で帰します.
 * </p>
 *
 * @author
 * @see ConvertUtilsBean
 * @see Converter
 * @see DefaultConverter
 * @see DateTimeValueConverter
 * @see StringConverter
 */
public class DateTimeValueConverter implements Converter {

	/**
	 * ロガー
	 */
	private static final Log LOG = LogFactory.getLog(DateTimeValueConverter.class);

	/**
	 * {@link DateTimeValue}型に変換します.
	 *
	 * @param targetType 変換後型
	 * @param value 値
	 * @return {@link DateTimeValue}型の値
	 */
	@SuppressWarnings("rawtypes")
	public Object convert(Class targetType, Object value) {

		DateTimeValue result;
		try {
			result = (DateTimeValue) targetType.newInstance();
		} catch (Exception e) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("paramClass=" + targetType, e);
			}
			return null;
		}

		if (value == null) {
			return result;
		}
		if (targetType.equals(value.getClass())) {
			return value;
		}

		Date source = null;
		if (DateTimeValue.class.isAssignableFrom(value.getClass())) {
			source = DateTimeValue.class.cast(value).getDateTime();
		} else {
			source = DefaultConverter.convert(Date.class, value);
		}

		result.setDateTime(source);

		return result;
	}
}