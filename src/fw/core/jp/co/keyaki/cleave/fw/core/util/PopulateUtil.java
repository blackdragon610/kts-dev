package jp.co.keyaki.cleave.fw.core.util;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JavaBeansのプロパティ操作ユーティリティ
 * @author ytakahashi
 *
 */
public class PopulateUtil {

	/**
	 * ロガー.
	 */
	private static final Log LOG = LogFactory.getLog(PopulateUtil.class);

	/**
	 * 委譲先プロパティユーティリティ
	 */
	private static final BeanUtilsBean BEAN_UTILS_BEAN;
	static {
		BEAN_UTILS_BEAN = new BeanUtilsBean();
		ConvertUtilsBean convertUtils = BEAN_UTILS_BEAN.getConvertUtils();
		boolean throwException = false;
		boolean defaultNull = true;
		int defaultArraySize = 0;
		convertUtils.register(throwException, defaultNull, defaultArraySize);

		CustomStringConverter stringConverter = new CustomStringConverter();
		register(convertUtils, String.class, stringConverter);

		TimestampConverter timestampConverter = new TimestampConverter();
		register(convertUtils, Timestamp.class, timestampConverter);

	}
//	private static final BeanUtilsBean BEAN_UTILS_BEAN = new BeanUtilsBean();

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
	 * プロパティを単一オブジェクトから単一オブジェクトへ
	 * @param from コピー元
	 * @param to コピー先
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
	 * リスト内の要素のプロパティをコピーします.
	 * @param sourceList コピー元リスト
	 * @param destinationClass コピー先の要素の型
	 * @return コピーしたリスト
	 */
	public static <T> List<T> populate(List<?> sourceList, Class<T> destinationClass) {
		List<T> destinationList = new ArrayList<T>();
		for (Object source : sourceList) {
			T destination = null;
			try {
				destination = ReflectionUtils.newInstance(destinationClass);
				populate(source, destination);
			} catch (Exception e) {
				if (LOG.isTraceEnabled()) {
					LOG.trace(e);
				}
			}
			destinationList.add(destination);
		}
		return destinationList;
	}

}
