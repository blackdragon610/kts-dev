package jp.co.keyaki.cleave.fw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.ContextListener;
import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;
import jp.co.keyaki.cleave.fw.core.SystemConfig;
import jp.co.keyaki.cleave.fw.core.util.ReflectionUtils;

/**
 * システムで利用するコネクションの管理クラス。
 *
 * <p>
 * システムで利用するコネクションを、スレッドにて管理するクラス。
 * </p>
 *
 * <p>
 * コネクションの取得方法は、{@link ConnectionResource}へ委譲している。 そのため、このクラスを利用するにあたり、
 * <code>database-config.xml</code>に下記のキーが存在すること。
 * <table border="1">
 * <tr>
 * <th>キー名</th>
 * <th>値</th>
 * </tr>
 * <tr>
 * <th>database-config.connection-resource-class</th>
 * <th>{@link ConnectionResource}の実装クラス(FQCN)</th>
 * </tr>
 * </table>
 * </p>
 *
 *
 * @author ytakahashi
 * @see SystemConfig
 * @see ConnectionResource
 *
 */
public class ConnectionManager {

	/**
	 * ロガー。
	 */
	private static final Log LOG = LogFactory.getLog(ConnectionManager.class);

	/**
	 * デフォルトコネクション番号.
	 */
	public static final int DEFALUT_CONNECTION_NO = 0;

	/**
	 * データベース設定キープレフィックス
	 */
	private static final String PROPERTY_KEY_DATABASE_CONFIG = "database-configs.database-config";

	/**
	 * コネクションリソースクラスキー
	 */
	private static final String PROPERTY_KEY_RESOURCE_CLASS = "[@connection-resource-class]";

	/**
	 * アクションコンテキスト登録時キー
	 */
	private static final String CONTEXT_KEY_CONNECTION_CONTAINER = "CONTEXT_KEY_CONNECTION_CONTAINER";

	/**
	 * コネクションリソース。
	 */
	private static Map<Integer, ConnectionResource> connectionResources = new HashMap<Integer, ConnectionResource>();

	/**
	 * static イニシャライザ.
	 */
	static {
		// データベースコンフィグ数の取得
		int databaseConfigCount = SystemConfig.getKeyCount(PROPERTY_KEY_DATABASE_CONFIG + PROPERTY_KEY_RESOURCE_CLASS);
		for (int configNo = 0; configNo < databaseConfigCount; configNo++) {
			String prefixKey = SystemConfig.createIndexKey(PROPERTY_KEY_DATABASE_CONFIG, configNo);
			String connectionResourceClassName = SystemConfig.getString(prefixKey + PROPERTY_KEY_RESOURCE_CLASS);
			Class<ConnectionResource> connectionResourceClass = ReflectionUtils.forName(ConnectionResource.class,
					connectionResourceClassName);
			ConnectionResource connectionResource;
			try {
				connectionResource = ReflectionUtils.newInstance(connectionResourceClass);
			} catch (CoreRuntimeException cre) {
				throw new CoreRuntimeException(DaoMessageDefine.E000003, "configNo=" + configNo, cre);
			}
			Map<String, String> childrenConfig = getChildrenConfig(prefixKey);
			try {
				connectionResource.init(childrenConfig);
			} catch (DaoException de) {
				throw new CoreRuntimeException(DaoMessageDefine.E000004, "configNo=" + configNo, de);
			}
			connectionResources.put(configNo, connectionResource);
		}
	}

	/**
	 * 子要素設定を取得します.
	 *
	 * @param prefix 取得する親キー（プレフィックス）
	 * @return 設定キーをキー、設定値を値としたＭＡＰ
	 */
	private static Map<String, String> getChildrenConfig(String prefix) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> keys = new ArrayList<String>();
		CollectionUtils.addAll(keys, SystemConfig.getKeys(prefix));
		for (String key : keys) {
			String childPrefixKey = prefix + ".";
			if (key.startsWith(childPrefixKey)) {
				String childKey = key.substring(childPrefixKey.length());
				String childValue = SystemConfig.getString(key);
				map.put(childKey, childValue);
			}
		}
		return map;
	}

	/**
	 * 複数コネクションを１つに束ねるためのコンテナ.
	 *
	 * @author ytakahashi
	 */
	private static class ConnectionContainer implements ContextListener {

		/**
		 * マネージドコネクション番号をキーとしたコネクション群
		 */
		private Map<Integer, ManagedConnection> managedConnections = new HashMap<Integer, ConnectionManager.ManagedConnection>();

		/**
		 * マネージドコネクションを返します.
		 *
		 * @param connectionNo
		 *            取得するマネージドコネクション番号
		 * @return マネージドコネクション
		 */
		public ManagedConnection getManagedConnection(int connectionNo) {
			ManagedConnection managedConnection = managedConnections.get(connectionNo);
			if (managedConnection == null) {
				managedConnection = new ManagedConnection(connectionNo);
				managedConnections.put(connectionNo, managedConnection);
			}
			return managedConnection;
		}

		/**
		 * コンテキスト登録リスナー.
		 *
		 * このコンテナがコンテキストに登録された際、
		 * コンテナ内に定義済み管理コネクションを生成しリスナーへ相伝します.
		 *
		 * @param registKey 登録キー
		 */
		public void handleAdded(String registKey) {
			for (int connectionNo : connectionResources.keySet()) {
				ManagedConnection managedConnection = new ManagedConnection(connectionNo);
				managedConnections.put(connectionNo, managedConnection);
				managedConnection.handleAdded(registKey);
			}

		}

		/**
		 * コンテキスト削除リスナー.
		 *
		 * このコンテナがコンテキストから削除された際、
		 * コンテナ内に登録されている管理コネクションのリスナーへ相伝します.
		 *
		 * @param removetKey 削除キー
		 */
		public void handleRemoved(String removetKey) {
			for (ManagedConnection managedConnection : managedConnections.values()) {
				managedConnection.handleRemoved(removetKey);
			}
		}

	}

	/**
	 * 管理するコネクションを表すクラス。
	 *
	 * @author ytakahashi
	 *
	 */
	private static class ManagedConnection implements ContextListener {

		/**
		 * コネクション設定番号.
		 */
		private int connectionNo = 0;

		/**
		 * JDBCコネクション
		 */
		private Connection connection = null;

		/**
		 * コンストラクタ。
		 *
		 * @param connectionNo
		 *            コネクション設定番号
		 */
		ManagedConnection(int connectionNo) {
			this.connectionNo = connectionNo;
		}

		/**
		 * コネクション設定番号を返します.
		 *
		 * @return コネクション設定番号
		 */
		public int getConnectionNo() {
			return connectionNo;
		}

		/**
		 * 管理しているコネクションを返却します。
		 *
		 * <p>
		 * 内部で管理しているコネクションを返却します。<br>
		 *
		 * コネクションを内部でまだ管理していない場合は、コネクションを生成し管理します。
		 * </p>
		 *
		 * @return 管理しているコネクション
		 * @throws DaoException
		 *             コネクション取得時に問題が発生した場合。
		 */
		Connection getConnection() throws DaoException {
			if (isOpen()) {
				return connection;
			}
			ConnectionResource connectionResource = connectionResources.get(connectionNo);
			if (connectionResource == null) {
				throw new DaoException(DaoMessageDefine.E000025, "connectionNo=" + getConnectionNo());
			}
			connection = connectionResource.getConnection();
			LOG.debug("connection open.");
			try {
				connection.setAutoCommit(false);
			} catch (SQLException sqle) {
				throw new DaoException(DaoMessageDefine.E000008, "connectionNo=" + getConnectionNo(), sqle);
			}
			LOG.debug("autocomit off.");
			return connection;
		}

		/**
		 * トランザクションをコミットします。
		 *
		 * @throws DaoException
		 */
		void commit() throws DaoException {
			if (!isOpen()) {
				return;
			}
			try {
				getConnection().commit();
				LOG.debug("connection commit.");
			} catch (SQLException sqle) {
				release(true);
				throw new DaoException(DaoMessageDefine.E000006, "connectionNo=" + getConnectionNo(), sqle);
			}
		}

		/**
		 * トランザクションをロールバックします。
		 *
		 * @throws DaoException
		 */
		void rollback() throws DaoException {
			if (!isOpen()) {
				return;
			}
			try {
				getConnection().rollback();
				LOG.debug("connection rollback.");
			} catch (SQLException sqle) {
				release(true);
				throw new DaoException(DaoMessageDefine.E000005, "connectionNo=" + getConnectionNo(), sqle);
			}
		}

		/**
		 * コネクションを返却します。
		 *
		 * @throws Exception
		 *
		 */
		void release() throws DaoException {
			release(false);
		}

		/**
		 * コネクションがすでに開かれているかを判断し返却します。
		 *
		 * <p>
		 * コネクションがすでに開かれているか、まだ開かれていないかの判断は、コネクションのインスタンスが存在しているかによって判断する。<br>
		 *
		 * {@link java.sql.Connection}に api
		 * {@link java.sql.Connection#isClosed()}が存在するが、
		 * JDBCDriverが実装していない可能性もあるので、ここでは利用しない事とする。
		 *
		 * </p>
		 *
		 * @return true:コネクションがすでに開かれている/false:コネクションはまだ開かれていない
		 */
		private boolean isOpen() {
			return connection != null;
		}

		/**
		 * コネクションのリリースを行います。
		 *
		 * <p>
		 * コネクションすでに開かれている場合は、クローズ処理を実施します。<br>
		 * コネクションがまだ開かれていない場合は、何もせず処理が終了します。<br>
		 * コネクションのクローズ処理を実施時に例外が発生した際に、無視するかスローし直すかは、 第１引数によって決定します。
		 * </p>
		 *
		 * @param isIgnoreError
		 *            コネクションリリース時の例外を無視するか true:無視する/false:無視しない
		 * @throws DaoException
		 *             isIgnoreErrorがfalseでかつコネクションクローズ時に例外が発生した場合
		 *
		 */
		private void release(boolean isIgnoreError) throws DaoException {
			if (!isOpen()) {
				return;
			}
			try {
				connection.close();
				LOG.debug("connection close.");
			} catch (SQLException sqle) {
				if (!isIgnoreError) {
					throw new DaoException(DaoMessageDefine.E000007, "connectionNo=" + getConnectionNo(), sqle);
				} else {
					LOG.warn("thrown on close connection. connectionNo=" + getConnectionNo(), sqle);
				}
			} finally {
				connection = null;
			}
		}

		/**
		 * コンテキスト登録リスナー.
		 *
		 * この管理コネクションがコンテキストに登録された際、
		 * プール数の関係上、すぐにはコネクションをオープンしません。
		 * 必要になった際にオープンします。
		 * したがってこのメソッドでは特に実装はありません。
		 *
		 * @param registKey 登録キー
		 */
		public void handleAdded(String registKey) {
		}

		/**
		 * コンテキスト削除リスナー.
		 *
		 * このコンテナがコンテキストから削除された際、
		 * トランザクション処理を実施し、コネクションをプールへ返却をします。
		 *
		 * @param removetKey 削除キー
		 */
		public void handleRemoved(String removetKey) {
			if (!isOpen()) {
				return;
			}
			if (ActionContext.isTransactionRollbackOnly()) {
				try {
					rollback();
				} catch (Exception e) {
					throw new CoreRuntimeException(DaoMessageDefine.E000005, "connectionNo=" + getConnectionNo(), e);
				}
			} else {
				try {
					commit();
				} catch (Exception e) {
					throw new CoreRuntimeException(DaoMessageDefine.E000006, "connectionNo=" + getConnectionNo(), e);
				}
			}
			try {
				release(true);
			} catch (Exception e) {
				// ありえない
				// throw new CoreRuntimeException(DaoMessageDefine.E000007, "connectionNo=" + getConnectionNo(), e);
			}
		}

	}

	/**
	 * アクションコンテキストより、コネクションコンテナーを取得し返します.
	 *
	 * アクションコンテキストにコネクションコンテナーが登録されていない場合は、
	 * 作成・登録を実施します.
	 *
	 * @return コネクションコンテナー
	 */
	private static ConnectionContainer getConnectionContainer() {
		ConnectionContainer cc = ConnectionContainer.class.cast(ActionContext.get(CONTEXT_KEY_CONNECTION_CONTAINER));
		if (cc == null) {
			cc = new ConnectionContainer();
			ActionContext.regist(CONTEXT_KEY_CONNECTION_CONTAINER, cc);
		}
		return cc;
	}

	/**
	 * 指定されたマネージドコネクション番号で登録されている管理コネクションを返却します.
	 *
	 * @param connectionNo マネージドコネクション番号
	 * @return 管理コネクション
	 */
	private static ManagedConnection getManagedConnection(int connectionNo) {
		return getConnectionContainer().getManagedConnection(connectionNo);
	}

	/**
	 * コネクションを取得します。
	 *
	 * @return コネクション
	 * @throws DaoException
	 *             取得時に例外が発生した場合。
	 */
	public static Connection get() throws DaoException {
		return get(DEFALUT_CONNECTION_NO);
	}

	/**
	 * コネクションを取得します。
	 *
	 * @return コネクション
	 * @throws DaoException
	 *             取得時に例外が発生した場合。
	 */
	public static Connection get(int connectionNo) throws DaoException {
		return getManagedConnection(connectionNo).getConnection();
	}

	/**
	 * トランザクションをコミットします。
	 *
	 * @throws Exception
	 *             コミット時に例外が発生した場合。
	 *
	 */
	public static void commit() throws DaoException {
		commit(DEFALUT_CONNECTION_NO);
	}

	/**
	 * トランザクションをコミットします。
	 *
	 * @throws Exception
	 *             コミット時に例外が発生した場合。
	 *
	 */
	public static void commit(int connectionNo) throws DaoException {
		getManagedConnection(connectionNo).commit();
	}

	/**
	 * トランザクションをロールバックします。
	 *
	 * @throws Exception
	 *             ロールバック時に例外が発生した場合。
	 *
	 */
	public static void rollback() throws DaoException {
		rollback(DEFALUT_CONNECTION_NO);
	}

	/**
	 * トランザクションをロールバックします。
	 *
	 * @throws Exception
	 *             ロールバック時に例外が発生した場合。
	 *
	 */
	public static void rollback(int connectionNo) throws DaoException {
		getManagedConnection(connectionNo).rollback();
	}

	/**
	 * コネクションを返却します。
	 *
	 * @throws Exception
	 *             コレクション返却時に例外が発生した場合。
	 *
	 */
	public static void release() throws DaoException {
		getManagedConnection(DEFALUT_CONNECTION_NO).release();
	}

	/**
	 * コネクションを返却します。
	 *
	 * @throws Exception
	 *             コレクション返却時に例外が発生した場合。
	 *
	 */
	public static void release(int connectionNo) throws DaoException {
		getManagedConnection(connectionNo).release();
	}

}
