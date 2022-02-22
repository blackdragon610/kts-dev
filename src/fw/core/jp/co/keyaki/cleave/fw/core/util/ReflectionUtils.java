package jp.co.keyaki.cleave.fw.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import jp.co.keyaki.cleave.fw.core.CoreMessageDefine;
import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;

/**
 * リフレクション利用時におけるクラスユーティリティ.
 *
 * @author ytakahashi
 *
 */
public class ReflectionUtils {

	/**
	 * FQCN文字列よりクラスオブジェクトを返します.
	 *
	 * @param className 取得したいFQCN文字列
	 * @return クラスオブジェクト.
	 * @throws CoreRuntimeException クラスオブジェクトの取得に失敗した場合
	 */
	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException cnfe) {
			throw new CoreRuntimeException(CoreMessageDefine.E000011, "className=" + className, cnfe);
		}
	}

	@SuppressWarnings("unchecked")
	public static <E> Class<E> forName(Class<E> type, String className) {
		Class<?> clazz = forName(className);
		try {
			return (Class<E>) clazz;
		} catch (ClassCastException cce) {
			throw new CoreRuntimeException(CoreMessageDefine.E000012, "type=" + type, cce);
		}
	}

	/**
	 * 新しいインスタンスを生成し返します。
	 *
	 * @return 新しいインスタンス
	 * @throws CoreRuntimeException
	 *             インスタンス生成に失敗した場合
	 */
	public static <E> E newInstance(Class<E> type) throws CoreRuntimeException {
		try {
			return type.newInstance();
		} catch (InstantiationException ie) {
			throw new CoreRuntimeException(CoreMessageDefine.E000007, "class=" + type, ie);
		} catch (IllegalAccessException iae) {
			throw new CoreRuntimeException(CoreMessageDefine.E000007, "class=" + type, iae);
		}
	}

	/**
	 * フィールドの値を取得して返します.
	 *
	 * @param field アクセス先フィールド
	 * @param instance フィールドを所有するインスタンス(staticの場合はnull)
	 * @return フィールド値
	 */
	public static Object getValue(Field field, Object instance) {
		try {
			return field.get(instance);
		} catch (IllegalArgumentException iae) {
			throw new CoreRuntimeException(CoreMessageDefine.E000013, "field=" + field + "instance=" + instance, iae);
		} catch (IllegalAccessException iae) {
			throw new CoreRuntimeException(CoreMessageDefine.E000013, "field=" + field + "instance=" + instance, iae);
		}
	}

	/**
	 * フィールドが定数（public static final）か判定します.
	 *
	 * @param field フィールド
	 * @return true:定数/false:変数
	 */
	public static boolean isPublicStaticFinalField(Field field) {
		int modifiers = field.getModifiers();
		if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers)) {
			return false;
		}
		return true;
	}

}
