package jp.co.keyaki.cleave.fw.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.core.CoreMessageDefine;
import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;

/**
 *
 * JavaBeanに対するユーティリティクラス。
 *
 * @author ytakahashi
 *
 * @param <E> 操作対象となるJavaBeanの型
 */
public class BeanUtils<E> {

    /**
     * ロガー。
     */
    private static final Log LOG = LogFactory.getLog(BeanUtils.class);

    /**
     * プロパティ操作時に型が異なる場合に利用するデフォルトのコンバーター群。
     */
    private static final ConvertUtilsBean DEFAULT_CONVERTERS = new ConvertUtilsBean();

    /**
     * 操作対象となるJavaBeanクラス。
     */
    private Class<E> type;

    /**
     * プロパティ名指定で、利用するコンバーター群。
     */
    private Map<String, Converter> converters = new HashMap<String, Converter>();

    /**
     * コンストラクタ。
     *
     * @param type 操作対象となるJavaBeanクラス
     */
    public BeanUtils(Class<E> type) {
        this.type = type;
    }

    /**
     * プロパティ固有のコンバーターをこのインスタンスに登録します。
     *
     * <p>
     * プロパティ操作時に、型違いがあった際に利用するコンバーターを登録します。
     * 当クラスを利用してプロパティ操作（コピーや設定等）を行う場合、設定値の型と設定先のプロパティ型をチェックします。
     * 異なる場合は、このメソッドで登録されているコンバーターを利用して設定値の型変更を行います。
     * もし、このメソッドでプロパティ固有のコンバーターが登録されていない場合は、デフォルトのコンバーターを利用して
     * 設定値の型変更を行います。
     * </p>
     *
     * @param propertyName プロパティ名
     * @param converter コンバーター
     */
    public void registerConverter(String propertyName, Converter converter) {
        converters.put(propertyName, converter);
    }

    /**
     * コンバーターを返却します。
     *
     * <p>
     * このインスタンスに登録されているプロパティ固有のコンバーターがある場合は、登録されているコンバーターを返却します。
     * プロパティ固有のコンバーターが登録されていない場合、デフォルトのコンバーターが返却されます。
     * <p>
     *
     * @param propertyName プロパティ名
     * @param convertType 変更する型（変更後の型）
     * @return コンバーター
     * @see BeanUtils#registerConverter(String, Converter)
     */
    protected Converter getConverter(String propertyName, Class<?> convertType) {
        Converter converter = converters.get(propertyName);
        if (converter != null) {
            return converter;
        }
        return DEFAULT_CONVERTERS.lookup(convertType);
    }

    /**
     *
     * 第３引数の型をある特定の型へ変換して返します。
     *
     * @param propertyName プロパティ固有のコンバータが登録されているかを判定するためのプロパティ名
     * @param toType デフォルトのコンバーターを特定するための、変換後の型
     * @param value 変換する値
     * @return 変換後の値
     * @see BeanUtils#registerConverter(String, Converter)
     * @see BeanUtils#getConverter(String, Class)
     */
    protected Object convert(String propertyName, Class<?> toType, Object value) {
        Converter converter = getConverter(propertyName, toType);
        Object convertedValue =converter.convert(toType, value);
        if (LOG.isDebugEnabled()) {
            LOG.debug(propertyName + " done convert. original value type=" + value.getClass() + ". convert value type=" + toType + ".");
            LOG.debug(propertyName + " is original value =" + value + ". convert value=" + convertedValue + ".");
        }
        return convertedValue;
    }

    /**
     * 新しいインスタンスを生成し返します。
     *
     * <p>
     * コンストラクタで指定されたクラスのインスタンスを生成し返します。
     * コンストラクタはデフォルトコンストラクタを呼び出します。
     * コンストラクタで記述されている以外の初期化は行いません。
     * （プロパティの初期値の設定は、コンストラクタに任されます。）
     * </p>
     *
     * @return 新しいインスタンス
     * @throws CoreRuntimeException インスタンス生成に失敗した場合
     */
    public E newInstance() throws CoreRuntimeException {
    	return ReflectionUtils.newInstance(type);
    }

    /**
     * 新しいインスタンスを生成し返します。
     *
     * <p>
     * コンストラクタで指定されたクラスのインスタンスを生成し返します。
     * コンストラクタはデフォルトコンストラクタを呼び出します。
     * 引数に指定されたインスタンスのプロパティで初期化できる範囲は
     * 新しいインスタンスへ参照のコピーがされます。
     * </p>
     *
     * @param from 新しいインスタンスのプロパティの初期値
     * @return 新しいインスタンス
     * @throws CoreRuntimeException インスタンス生成に失敗した場合
     */
    public E newInstance(Object from) throws CoreRuntimeException {
        E to = newInstance();
        copyProperties(from, to);
        return to;
    }

    /**
     * 第１引数のプロパティを第２引数のプロパティへコピーをします。
     *
     * <p>
     * 第１引数のインスタンスが{@link Map}の実装クラスの場合、マップのキーをプロパティ名として振舞います。
     * また、マップのキーのインスタンスが{@link String}以外の場合、{@link Object#toString()}値をプロパティ名として振舞います。
     * </p>
     *
     * @param from コピー元インスタンス
     * @param to コピー先インスタンス
     * @throws CoreRuntimeException プロパティコピーに失敗した場合
     */
    @SuppressWarnings("unchecked")
    public void copyProperties(Object from, E to) throws CoreRuntimeException {
        if (from instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>)from;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                if (key == null) {
                    continue;
                }
                String propertyName = null;
                if (key instanceof String) {
                    propertyName = (String) key;
                } else {
                    propertyName = key.toString();
                }
                setProperty(to, propertyName, entry.getValue());
            }
        } else {
            PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(from);
            for (PropertyDescriptor descriptor : descriptors) {
                String propertyName = descriptor.getName();
                Object value;
				try {
					value = PropertyUtils.getProperty(from, propertyName);
				} catch (IllegalAccessException ie) {
					throw new CoreRuntimeException(CoreMessageDefine.E000010, "propertyName=" + propertyName, ie);
				} catch (InvocationTargetException ite) {
					throw new CoreRuntimeException(CoreMessageDefine.E000010, "propertyName=" + propertyName, ite);
				} catch (NoSuchMethodException nsme) {
					throw new CoreRuntimeException(CoreMessageDefine.E000010, "propertyName=" + propertyName, nsme);
				}
                setProperty(to, propertyName, value);
            }
        }
    }

    /**
     * 第１引数のインスタンスの第２引数のプロパティに第３引数の値を設定します。
     *
     * @param instance 設定先のインスタンス
     * @param propertyName プロパティ名
     * @param value 設定するプロパティ値
     * @return true:プロパティ値の設定が行えた場合/false:プロパティ値の設定が行えなかった場合
     * @throws CoreRuntimeException プロパティ値の設定処理中に例外が発生した場合。
     */
    public boolean setProperty(E instance, String propertyName,
            Object value) throws CoreRuntimeException {

        if (!PropertyUtils.isWriteable(instance, propertyName)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(propertyName + " is nothing or read only property.");
            }
            return false;
        }

        Object setValue = value;
        if (value != null) {
            Class<?> propertyType;
			try {
				propertyType = PropertyUtils.getPropertyType(instance, propertyName);
			} catch (IllegalAccessException ie) {
				throw new CoreRuntimeException(CoreMessageDefine.E000008, "propertyName=" + propertyName, ie);
			} catch (InvocationTargetException ite) {
				throw new CoreRuntimeException(CoreMessageDefine.E000008, "propertyName=" + propertyName, ite);
			} catch (NoSuchMethodException nsme) {
				throw new CoreRuntimeException(CoreMessageDefine.E000008, "propertyName=" + propertyName, nsme);
			}
            Class<?> valueType = value.getClass();
            if (!propertyType.isAssignableFrom(valueType)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(propertyName + " is value type=" + valueType + " but propety type=" + propertyType + ". try convert.");
                }
                setValue = convert(propertyName, propertyType, value);
            }

        }
        try {
			PropertyUtils.setProperty(instance, propertyName, setValue);
		} catch (IllegalAccessException ie) {
			throw new CoreRuntimeException(CoreMessageDefine.E000009, "propertyName=" + propertyName, ie);
		} catch (InvocationTargetException ite) {
			throw new CoreRuntimeException(CoreMessageDefine.E000009, "propertyName=" + propertyName, ite);
		} catch (NoSuchMethodException nsme) {
			throw new CoreRuntimeException(CoreMessageDefine.E000009, "propertyName=" + propertyName, nsme);
		}
        return true;
    }



}
