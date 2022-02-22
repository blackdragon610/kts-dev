package jp.co.keyaki.cleave.fw.dao;

import java.net.URL;

import jp.co.keyaki.cleave.fw.core.util.ReflectionUtils;

/**
 * {@link SQLBuilder}生成クラス。
 * 
 * @author ytakahashi
 * @see SQLBuilder
 * 
 */
public class SQLBuilderFactory {

	/**
	 * 利用する{@link SQLBuilder}インスタンス。
	 */
	private static SQLBuilder builderInstance;

	/**
	 * 
	 * 利用する{@link SQLBuilder}を登録します。
	 * 
	 * @param builderClass
	 *            利用する{@link SQLBuilder}
	 * @throws DaoException
	 *             登録時に例外が発生した場合
	 */
	public static void regist(Class<?> builderClass) throws DaoException {
		builderInstance = SQLBuilder.class.cast(ReflectionUtils.newInstance(builderClass));
	}

	/**
	 * 利用する{@link SQLBuilder}に対して設定ファイルを指示します。
	 * 
	 * @param configurationFile
	 *            設定ファイル。
	 * @throws DaoException
	 *             設定ファイル指定時に例外が発生した場合。
	 */
	public static void configure(URL configurationFile) throws DaoException {
		builderInstance.configure(configurationFile);
	}

	/**
	 * 利用する{@link SQLBuilder}を返却します。
	 * 
	 * @return 利用する{@link SQLBuilder}
	 * @throws DaoException
	 *             利用する{@link SQLBuilder}取得時に例外が発生した場合
	 */
	static SQLBuilder getDefaultSQLBuilder() throws DaoException {
		return builderInstance;
	}
}
