package jp.co.keyaki.cleave.fw.ui.web.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.core.util.ReflectionUtils;
import jp.co.keyaki.cleave.fw.ui.web.value.DateTimeValue;
import jp.co.keyaki.cleave.fw.ui.web.value.StringConverter;
import jp.co.keyaki.cleave.fw.ui.web.value.DateTimeValueConverter;
import jp.co.keyaki.cleave.fw.ui.web.value.DateValue;
import jp.co.keyaki.cleave.fw.ui.web.value.JdkDateConverter;
import jp.co.keyaki.cleave.fw.ui.web.value.TimeValue;
import jp.co.keyaki.cleave.fw.ui.web.value.TimestampValue;

/**
 * {@link DateTimeValue}および派生クラスの型変換を加味したプロパティコピーユーティリティクラス.
 *
 * @author
 */
public class ValuePopulateUtil {

	/**
	 * ロガー.
	 */
	private static final Log LOG = LogFactory.getLog(ValuePopulateUtil.class);

	/**
	 * 独自コンバーター登録用.
	 */
	private static final BeanUtilsBean BEAN_UTILS_BEAN;
	static {
		BEAN_UTILS_BEAN = new BeanUtilsBean();
		ConvertUtilsBean convertUtils = BEAN_UTILS_BEAN.getConvertUtils();
		boolean throwException = false;
		boolean defaultNull = true;
		int defaultArraySize = 0;
		convertUtils.register(throwException, defaultNull, defaultArraySize);

		StringConverter stringConverter = new StringConverter();
		register(convertUtils, String.class, stringConverter);

		DateTimeValueConverter dateTimeValueConverter = new DateTimeValueConverter();
		register(convertUtils, DateValue.class, dateTimeValueConverter);
		register(convertUtils, TimeValue.class, dateTimeValueConverter);
		register(convertUtils, TimestampValue.class, dateTimeValueConverter);

		JdkDateConverter jdkDateConverter = new JdkDateConverter();
		register(convertUtils, java.util.Date.class, jdkDateConverter);
		register(convertUtils, java.sql.Date.class, jdkDateConverter);
		register(convertUtils, java.sql.Time.class, jdkDateConverter);
		register(convertUtils, java.sql.Timestamp.class, jdkDateConverter);

	}

	/**
	 * コンバーター登録用メソッド.
	 *
	 * <p>
	 * 配列要素用のコンバーターも同時に登録します.
	 * </p>
	 *
	 * @param convertUtils
	 * @param componentType
	 * @param converter
	 */
	protected static void register(ConvertUtilsBean convertUtils, Class<?> componentType, Converter converter) {
		Class<?> arrayType = Array.newInstance(componentType, 0).getClass();
		convertUtils.register(converter, componentType);
		convertUtils.register(new ArrayConverter(arrayType, converter, 0), arrayType);
	}

	/**
	 * インスタンス間でのプロパティのコピーを行います.
	 *
	 * <p>
	 * 同一のクラスである必要はありません.
	 * コピーするプロパティは名称が一致したプロパティに値をコピーします.
	 * プロパティの型が異なる場合は変換します.
	 * </p>
	 * <p>
	 * 型変換については、{@link ConvertUtilsBean}でデフォルトで登録されている型変換と
	 * {@link DateTimeValueConverter}、{@link JdkDateConverter}、{@link StringConverter}で
	 * 変換できる内容は変換をします.
	 * </p>
	 *
	 * @param from コピー元インスタンス.
	 * @param to コピー先インスタンス.
	 * @see ConvertUtilsBean
	 * @see DateTimeValueConverter
	 * @see JdkDateConverter
	 * @see StringConverter
	 */
	public static void populate(Object from, Object to) {
		try {
			BEAN_UTILS_BEAN.copyProperties(to, from);
		} catch (Exception e) {
			if (LOG.isTraceEnabled()) {
				LOG.trace(e);
			}
		}
	}

	/**
	 * リストの要素間のプロパティのコピーを行います.
	 *
	 * <p>
	 * 戻り値として返される要素は、当メソッドの第二引数で指定されたクラスとなります.
	 * 要素のプロパティの型変換については、{@link ValuePopulateUtil#populate(Object, Object)}を参照の事
	 * </p>
	 *
	 *
	 * @param sourceList コピー元インスタンス.
	 * @param destinationClass コピー先の要素の型
	 * @return 新たに作成されたList
	 * @see ValuePopulateUtil#populate(Object, Object)
	 */
	public static <T> List<T> populate(List<?> sourceList, Class<T> destinationClass) {
		List<T> destinationList = new ArrayList<T>();
		for (Object source : sourceList) {
			T destination = null;
			try {
				destination = ReflectionUtils.newInstance(destinationClass);
			} catch (Exception e) {
				if (LOG.isTraceEnabled()) {
					LOG.trace(e);
				}
			}
			populate(source, destination);
			destinationList.add(destination);
		}
		return destinationList;
	}

}
