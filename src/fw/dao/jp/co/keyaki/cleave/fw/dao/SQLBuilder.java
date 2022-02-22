package jp.co.keyaki.cleave.fw.dao;

import java.net.URL;

/**
 * SQL情報を生成するためのインターフェース。
 * 
 * このインターフェースには、SQLBuilderを準備するための<code>configure(URL)</code>と 実際にSQL情報を生成するための
 * <code>build(String, SQLParameters)</code>を定義。
 * 
 * @author ytakahashi
 * 
 */
public interface SQLBuilder {

	/**
	 * SQLBuilderの実装クラスの準備のためのメソッド。
	 * 
	 * @param templateFile
	 *            準備に必要なファイルへのURL等
	 * @throws DaoException
	 *             SQLBuilder実装クラスの準備時に例外が発生した場合。
	 */
	void configure(URL templateFile) throws DaoException;

	/**
	 * 引数を元にSQL情報を生成し返します。
	 * 
	 * @param sqlId
	 *            SQLID
	 * @param parameters
	 *            SQL情報を生成するにあたって必要なパラメータ群
	 * @return SQL情報
	 * @throws DaoException
	 *             SQL情報生成時に例外が発生した場合
	 */
	SQLInfo build(String sqlId, SQLParameters parameters) throws DaoException;

}
