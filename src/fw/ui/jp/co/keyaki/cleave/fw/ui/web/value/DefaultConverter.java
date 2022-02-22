package jp.co.keyaki.cleave.fw.ui.web.value;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;

/**
 * デフォルトの型変換を実施するためのデリゲートクラス.
 *
 * @author
 *
 */
class DefaultConverter {

	/**
	 * 委譲先ユーティリティクラス.
	 */
	private static final ConvertUtilsBean DEFAULT_COMVERTER = BeanUtilsBean.getInstance().getConvertUtils();

	/**
	 * デフォルトの型変換を実施します.
	 *
	 * @param targetType 変換後型
	 * @param value 値
	 * @return 変換後インスタンス
	 */
	public static <T> T convert(Class<T> targetType, Object value) {
		if (value == null) {
			return null;
		}
		return targetType.cast(DEFAULT_COMVERTER.convert(value, targetType));
	}

}
