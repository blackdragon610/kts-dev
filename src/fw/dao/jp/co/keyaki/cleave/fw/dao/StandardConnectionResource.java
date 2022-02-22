package jp.co.keyaki.cleave.fw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.core.SystemConfig;

/**
 * ドライバーマネージャ（{@link DriverManager}）よりコネクションを取得する実装クラス。
 *
 * <p>
 * この実装クラスを利用するにあたり、<code>database-config.xml</code>に下記のキーが存在すること。
 * <table border="1">
 * <tr>
 * <th>キー名</th>
 * <th>値</th>
 * </tr>
 * <tr>
 * <th>database-config.database-driver-class</th>
 * <th>データベースドライバークラス(FQCN)</th>
 * </tr>
 * <tr>
 * <th>database-config.database-url</th>
 * <th>データベース接続URL</th>
 * </tr>
 * <tr>
 * <th>database-config.database-user-name</th>
 * <th>データベース接続ユーザ</th>
 * </tr>
 * <tr>
 * <th>database-config.database-password</th>
 * <th>データベース接続パスワード</th>
 * </tr>
 * </table>
 * </p>
 *
 *
 * @author ytakahashi
 * @see SystemConfig
 * @see DriverManager
 *
 */
public class StandardConnectionResource implements ConnectionResource {

	/**
	 * データベースドライバー名取得用キー。
	 */
	private static final String DATABASE_DRIVER_KEY = "database-driver-class";

	/**
	 * データベース接続URL取得用キー。
	 */
	private static final String DATABASE_URL_KEY = "database-url";

	/**
	 * データベース接続ユーザ取得用キー。
	 */
	private static final String DATABASE_USER_KEY = "database-user-name";

	/**
	 * データベース接続パスワード取得用キー。
	 */
	private static final String DATABASE_PASSWORD_KEY = "database-password";

	/**
	 * 設定情報
	 */
	private Map<String, String> config;

	/**
	 * データベースドライバーの登録処理
	 *
	 * @param config
	 *            設定情報
	 * @throws DaoException
	 *             ドライバークラスを取得出来なかった場合
	 * @see ConnectionResource#init(Map)
	 */
	public void init(Map<String, String> config) throws DaoException {
		this.config = config;
		try {
			Class.forName(this.config.get(DATABASE_DRIVER_KEY));
		} catch (ClassNotFoundException cnfe) {
			throw new DaoException(DaoMessageDefine.E000001, "key=".concat(DATABASE_DRIVER_KEY), cnfe);
		}
	}

	/**
	 * データベースドライバーよりコネクションを取得します。
	 *
	 * @throws DaoException
	 *             設定ファイルのURL/ユーザ/パスワードを利用してコネクションを取得出来なかった場合
	 * @see ConnectionResource#getConnection()
	 */
	public Connection getConnection() throws DaoException {
		try {
			return DriverManager.getConnection(config.get(DATABASE_URL_KEY), config.get(DATABASE_USER_KEY),
					config.get(DATABASE_PASSWORD_KEY));
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000002, "keys=".concat(StringUtils.join(new String[] {
					DATABASE_URL_KEY, DATABASE_USER_KEY, DATABASE_PASSWORD_KEY }, ',')), sqle);
		}
	}

}
