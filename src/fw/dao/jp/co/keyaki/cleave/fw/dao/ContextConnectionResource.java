package jp.co.keyaki.cleave.fw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.co.keyaki.cleave.fw.core.SystemConfig;

/**
 * コンテキスト（{@link InitialContext},{@link Context},{@link DataSource}
 * ）よりコネクションを取得する実装クラス。
 * 
 * <p>
 * この実装クラスを利用するにあたり、<code>database-config.xml</code>に下記のキーが存在すること。
 * <table border="1">
 * <tr>
 * <th>キー名</th>
 * <th>値</th>
 * </tr>
 * <tr>
 * <th>database-config.data-source-name</th>
 * <th>Contextに登録されているDataSource名</th>
 * </tr>
 * </table>
 * </p>
 * <p>
 * {@link InitialContext}よりルックアップする際の名称は<code>java:/comp/env</code>にて固定です。
 * </p>
 * 
 * @author ytakahashi
 * @see SystemConfig
 * @see InitialContext
 * @see Context
 * @see DataSource
 * 
 */
public class ContextConnectionResource implements ConnectionResource {

	/**
	 * データソース名取得用キー。
	 */
	private static final String DATA_SOURCE_NAME_KEY = "data-source-name";

	/**
	 * データソース。
	 */
	private static DataSource dataSource;

	/**
	 * 設定情報
	 */
	private Map<String, String> config;

	/**
	 * コンテキストよりデータソースを準備します.
	 * 
	 * @param config
	 *            設定情報
	 * @throws DaoException
	 *             データソース取得に失敗した場合.
	 * @see jp.co.ans.dao.base.ConnectionResource#init(Map)
	 * @see InitialContext
	 * @see Context
	 * @see DataSource
	 */
	public void init(Map<String, String> config) throws DaoException {
		this.config = config;
		if (dataSource != null) {
			return;
		}
		synchronized (ContextConnectionResource.class) {
			if (dataSource != null) {
				return;
			}
			InitialContext initialContext;
			try {
				initialContext = new InitialContext();
			} catch (NamingException ne) {
				throw new DaoException(DaoMessageDefine.E000010, ne);
			}
			try {
				Context context = (Context) initialContext.lookup("java:/comp/env");
				dataSource = (DataSource) context.lookup(this.config.get(DATA_SOURCE_NAME_KEY));
			} catch (NamingException ne) {
				throw new DaoException(DaoMessageDefine.E000011, ne);
			}
		}
	}

	/**
	 * データソース{@link DataSource}よりコネクションを取得します。
	 * 
	 * @return コネクション
	 * @see jp.co.ans.dao.base.ConnectionResource#getConnection()
	 * @see DataSource
	 */
	public Connection getConnection() throws DaoException {
		try {
			return dataSource.getConnection();
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000012, sqle);
		}
	}

}
