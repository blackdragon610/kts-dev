package jp.co.keyaki.cleave.fw.dao;

import java.sql.Connection;
import java.util.Map;

/**
 * コネクションへの取得方法を表すインターフェース。
 * 
 * @author ytakahashi
 * 
 */
public interface ConnectionResource {

	/**
	 * 初期化処理。
	 * 
	 * １インスタンスに対して１回初期化処理として呼び出されるメソッド。
	 * 
	 * @param config
	 *            設定情報
	 * @throws Exception
	 *             初期化処理時に例外が発生した場合。
	 */
	void init(Map<String, String> config) throws DaoException;

	/**
	 * コネクションを取得し返します。
	 * 
	 * @return コネクション
	 * @throws Exception
	 *             コネクション取得時に例外が発生した場合
	 */
	Connection getConnection() throws DaoException;
}
