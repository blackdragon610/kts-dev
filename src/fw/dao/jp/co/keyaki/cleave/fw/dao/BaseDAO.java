package jp.co.keyaki.cleave.fw.dao;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.dao.jdbc.JdbcUtils;
import jp.co.keyaki.cleave.fw.dao.util.FetchResultSetHandler;
import jp.co.keyaki.cleave.fw.dao.util.ListResultSetHandler;

/**
 * データアクセスオブジェクトのスーパークラス。
 *
 * @author ytakahashi
 */
public class BaseDAO {

	/**
	 * LIKE句の複数文字一致。
	 */
	protected static final String LIKE_PATTERN_WORD = "%";

	/**
	 * LIKE句の１文字一致。
	 */
	protected static final String LIKE_PATTERN_CHAR = "_";

	/**
	 * ロガー。
	 */
	private static final Log LOG = LogFactory.getLog(BaseDAO.class);

	/**
	 * パラメータが設定されていない空パラメータ。
	 */
	protected static final SQLParameters EMPTY_PARAMETERS = new SQLParameters();

	/**
	 * デフォルトコンストラクタ。
	 *
	 */
	protected BaseDAO() {
	}

	/**
	 * SELECT文を発行しList形式にて返却します。
	 *
	 * <p>
	 * SELECT文を発行し、データベースのテーブル１行を１要素とするList形式にして返却します。 主に複数件取得する場合に利用します。
	 * <p>
	 *
	 * <p>
	 * １要素を表すインスタンスの型は、第２引数に指定された{@link ResultSetHandler}によって変わります。
	 * </p>
	 *
	 * @param <E>
	 *            Listの要素の型
	 * @param sqlId
	 *            発行するSQLID
	 * @param handler
	 *            インスタンスへの設定戦略
	 * @return SELECT文を発行した結果(0件の場合であってもListのインスタンスは返却します。)
	 * @throws Exception
	 *             SELECT文を発行するにあたって例外が発生した場合
	 * @see ResultSetHandler
	 */
	protected <E extends Object> List<E> selectList(String sqlId, ResultSetHandler<E> handler) throws DaoException {
		return selectList(sqlId, EMPTY_PARAMETERS, handler);
	}

	/**
	 * SELECT文を発行し返却します。
	 *
	 * <p>
	 * 戻り値のインスタンスの型は、第２引数に指定された{@link ResultSetHandler}によって変わります。
	 * </p>
	 *
	 * @param <E>
	 *            返却する型のクラス
	 * @param sqlId
	 *            発行するSQLID
	 * @param parameters
	 *            バインドパラメータなどの、SQLを発行するにあたって必要なパラメータ
	 * @param handler
	 *            インスタンスへの設定戦略
	 * @return SELECT文を発行した結果
	 * @throws Exception
	 *             SELECT文を発行するにあたって例外が発生した場合
	 * @see ResultSetHandler
	 */
	protected <E extends Object> E select(String sqlId, ResultSetHandler<E> handler) throws DaoException {
		return select(sqlId, EMPTY_PARAMETERS, handler);
	}

	/**
	 * SELECT文を発行しList形式にて返却します。
	 *
	 * <p>
	 * SELECT文を発行し、データベースのテーブル１行を１要素とするList形式にして返却します。 主に複数件取得する場合に利用します。
	 * <p>
	 *
	 * <p>
	 * １要素を表すインスタンスの型は、第３引数に指定された{@link ResultSetHandler}によって変わります。
	 * </p>
	 *
	 * @param <E>
	 *            Listの要素の型
	 * @param sqlId
	 *            発行するSQLID
	 * @param parameters
	 *            バインドパラメータなどの、SQLを発行するにあたって必要なパラメータ
	 * @param handler
	 *            インスタンスへの設定戦略
	 * @return SELECT文を発行した結果(0件の場合であってもListのインスタンスは返却します。)
	 * @throws Exception
	 *             SELECT文を発行するにあたって例外が発生した場合
	 * @see ResultSetHandler
	 */
	protected <E> List<E> selectList(String sqlId, SQLParameters parameters, ResultSetHandler<E> handler)
			throws DaoException {
		SQLInfo sqlInfo = getSQLInfo(sqlId, parameters);
		return select(sqlInfo, new ListResultSetHandler<E>(new FetchResultSetHandler<E>(handler)));
	}

	/**
	 * SELECT文を発行し返却します。
	 *
	 * <p>
	 * 戻り値のインスタンスの型は、第３引数に指定された{@link ResultSetHandler}によって変わります。
	 * </p>
	 *
	 * @param <E>
	 *            返却する型のクラス
	 * @param sqlId
	 *            発行するSQLID
	 * @param parameters
	 *            バインドパラメータなどの、SQLを発行するにあたって必要なパラメータ
	 * @param handler
	 *            インスタンスへの設定戦略
	 * @return SELECT文を発行した結果
	 * @throws Exception
	 *             SELECT文を発行するにあたって例外が発生した場合
	 * @see ResultSetHandler
	 */
	protected <E> E select(String sqlId, SQLParameters parameters, ResultSetHandler<E> handler) throws DaoException {
		SQLInfo sqlInfo = getSQLInfo(sqlId, parameters);
		return select(sqlInfo, new FetchResultSetHandler<E>(handler));
	}

	/**
	 * SELECT文を発行し返却します。
	 *
	 * <p>
	 * 戻り値のインスタンスの型は、第３引数に指定された{@link ResultSetHandler}によって変わります。
	 * </p>
	 *
	 * @param <E>
	 *            返却する型のクラス
	 * @param sqlInfo
	 *            発行するSQL情報
	 * @param handler
	 *            インスタンスへの設定戦略
	 * @return SELECT文を発行した結果
	 * @throws DaoException
	 *             SELECT文を発行するにあたって例外が発生した場合
	 * @see ResultSetHandler
	 */
	protected <E> E select(SQLInfo sqlInfo, ResultSetHandler<E> handler) throws DaoException {
		boolean isHandleException = false;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = createStatement(sqlInfo.getSQL());
			bindParameters(stmt, sqlInfo.getParameters());
			String temp = stmt.toString();
			temp = temp.replace("%''", "%'");
			temp = temp.replace("''%", "'%");
			stmt = createStatement(temp);
			
			resultSet = stmt.executeQuery();
			return handler.handle(resultSet);
		} catch (SQLException sqle) {
			isHandleException = true;
			throw new DaoException(DaoMessageDefine.E000009, "sqlInfo=" + sqlInfo.toString(), sqle);
		} finally {
			JdbcUtils.close(resultSet, stmt, isHandleException);
			resultSet = null;
			stmt = null;
		}
	}

	/**
	 * INSERT/UPDATE/DELETE文を発行します。
	 *
	 * @param sqlId
	 *            発行するSQLID
	 * @param parameters
	 *            バインドパラメータなどの、SQLを発行するにあたって必要なパラメータ
	 * @return INSERT/UPDATE/DELETE文を発行し,影響を受けた件数
	 * @throws Exception
	 *             INSERT/UPDATE/DELETE文を発行するにあたって例外が発生した場合
	 */
	protected int update(String sqlId, SQLParameters parameters) throws DaoException {
		SQLInfo sqlInfo = getSQLInfo(sqlId, parameters);
		return update(sqlInfo);
	}

	/**
	 * INSERT/UPDATE/DELETE文を発行します。
	 *
	 * @param sqlId
	 *            発行するSQLID
	 * @param parameters
	 *            バインドパラメータなどの、SQLを発行するにあたって必要なパラメータ
	 * @return INSERT/UPDATE/DELETE文を発行し,影響を受けた件数
	 * @throws Exception
	 *             INSERT/UPDATE/DELETE文を発行するにあたって例外が発生した場合
	 */
	protected int update(SQLInfo sqlInfo) throws DaoException {
		boolean isHandleException = false;
		PreparedStatement stmt = null;
		try {
			stmt = createStatement(sqlInfo.getSQL());
			bindParameters(stmt, sqlInfo.getParameters());
			return stmt.executeUpdate();
		} catch (SQLException sqle) {
			isHandleException = true;
			throw new DaoException(DaoMessageDefine.E000009, "sqlInfo=" + sqlInfo.toString(), sqle);
		} finally {
			JdbcUtils.close(stmt, isHandleException);
			stmt = null;
		}
	}

	/**
	 * SQLIDと引数のパラメータから、実際に発行するSQL情報を返却します。
	 *
	 * @param sqlId
	 *            発行するSQLID
	 * @param parameters
	 *            バインドパラメータなどの、SQLを発行するにあたって必要なパラメータ
	 * @return SQL情報
	 * @throws Exception
	 *             SQL情報を作成するにあたって例外が発生した場合
	 */
	protected SQLInfo getSQLInfo(String sqlId, SQLParameters parameters) throws DaoException {
		SQLBuilder builder = SQLBuilderFactory.getDefaultSQLBuilder();
		SQLInfo sqlInfo = builder.build(sqlId, parameters);
		if (LOG.isDebugEnabled()) {
			LOG.debug("build sql\n" + sqlInfo);
		}
		return sqlInfo;
	}

	/**
	 * エンティティの内容からSQLParametersを生成し返します。
	 *
	 * <p>
	 * 第２引数のisNullExcludeにtrueを指定した場合、entityのプロパティ値がnullの場合はSQLParametersに設定しません
	 * 。
	 * </p>
	 *
	 * @param entity
	 *            パラメータを生成するためのエンティティ
	 * @param isNullExclude
	 *            プロパティ値がnullの場合パラメータから除外するか true:パラメータに含めない/false:パラメータに含める
	 * @return 生成したSQLParameters
	 * @throws DaoException
	 *             SQLParameters生成時に例外が発生した場合
	 */
	@SuppressWarnings("unchecked")
	protected SQLParameters createSQLParametersBy(Object entity, boolean isNullExclude) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		Map<String, Object> properties;
		try {
			properties = PropertyUtils.describe(entity);
		} catch (IllegalAccessException iae) {
			throw new DaoException(DaoMessageDefine.E000026, iae);
		} catch (InvocationTargetException ite) {
			throw new DaoException(DaoMessageDefine.E000026, ite);
		} catch (NoSuchMethodException nsme) {
			throw new DaoException(DaoMessageDefine.E000026, nsme);
		}
		for (Map.Entry<String, Object> property : properties.entrySet()) {
			if (isNullExclude && property.getValue() == null) {
				continue;
			}
			parameters.addParameter(property.getKey(), property.getValue());
		}
		return parameters;
	}

	/**
	 * エンティティの内容からSQLParametersを生成し返します。
	 *
	 * <p>
	 * このメソッドは、{@link BaseDAO#createSQLParametersBy(Object, boolean)}
	 * の第２引数にfalseを指定して 呼び出した事と同じ振る舞いを行います。
	 * </p>
	 *
	 * @param entity
	 *            パラメータを生成するためのエンティティ
	 * @return 生成したSQLParameters
	 * @throws DaoException
	 *             SQLParameters生成時に例外が発生した場合
	 * @see BaseDAO#createSQLParametersBy(Object, boolean)
	 */
	protected SQLParameters createSQLParametersBy(Object entity) throws DaoException {
		return createSQLParametersBy(entity, false);
	}

	/**
	 * データベースコネクションを返します。
	 *
	 * <p>
	 * データベースコネクションの取得方法は、{@link ConnectionManager}に依存しています。
	 * </p>
	 *
	 * @param connectionNo
	 *            コネクション番号
	 * @return データベースコネクション
	 * @throws DaoException
	 *             データベースコネクション取得時に例外が発生した場合
	 * @see ConnectionManager
	 *
	 */
	protected Connection getConnection() throws DaoException {
		return getConnection(ConnectionManager.DEFALUT_CONNECTION_NO);
	}

	/**
	 * データベースコネクションを返します。
	 *
	 * <p>
	 * データベースコネクションの取得方法は、{@link ConnectionManager}に依存しています。
	 * </p>
	 *
	 * @param connectionNo
	 *            コネクション番号
	 * @return データベースコネクション
	 * @throws DaoException
	 *             データベースコネクション取得時に例外が発生した場合
	 * @see ConnectionManager
	 *
	 */
	protected Connection getConnection(int connectionNo) throws DaoException {
		Connection connection = ConnectionManager.get(connectionNo);
		// if
		// (LogFactory.getLog(Connection.class.getPackage().getName()).isDebugEnabled())
		// {
		// connection = ProxyFactory.wrap(connection);
		// }
		return connection;
	}

	/**
	 * SQL文を基にステートメントオブジェクトを生成し返します。
	 *
	 * @param sql
	 *            SQL文
	 * @return ステートメントオブジェクト
	 * @throws SQLException
	 *             ステートメントオブジェクト生成時に例外が発生した場合
	 */
	protected PreparedStatement createStatement(String sql) throws DaoException {
		try {
			return getConnection().prepareStatement(sql);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000013, sqle);
		}
	}

	/**
	 * ステートメントオブジェクトにバインド変数を設定します。
	 *
	 * @param stmt
	 *            バインド変数を設定するステートメントオブジェクト
	 * @param params
	 *            パラメータ
	 * @throws DaoException
	 *             バインド変数設定時に例外が発生した場合
	 */
	protected void bindParameters(PreparedStatement stmt, List<Object> params) throws DaoException {
		int parameterNo = 1;
		for (Object param : params) {
			try {
				stmt.setObject(parameterNo, param);
			} catch (SQLException sqle) {
				throw new DaoException(DaoMessageDefine.E000014, "parameterNo=" + parameterNo + " parameterObject="
						+ param, sqle);
			}
			parameterNo++;
		}
	}

	/**
	 * JavaBeanのプロパティ名をSQLパラメータ名として設定します.
	 *
	 * @param dto SQLパラメータとして設定するJavaBean
	 * @param params SQLパラメータ設定先
	 * @throws DaoException パラメータ抽出に失敗した場合
	 */
	protected void addParametersFromBeanProperties(Object dto, SQLParameters params)
			throws DaoException {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(dto.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (propertyDescriptor.getReadMethod() == null) {
					continue;
				}
				String name = propertyDescriptor.getName();
				Object value = PropertyUtils.getProperty(dto, name);
				params.addParameter(name, value);
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException | IntrospectionException e) {
			throw new DaoException(DaoMessageDefine.E000026, e);
		}
	}

	/**
	 * 部分一致用のLIKE句のパラメータ作成。
	 *
	 * @param param
	 *            文字列
	 * @return 部分一致用LIKE句パラメータ
	 */
	protected static String createPartialLike(String param) {
		return createLikeWord(param, true, true);
	}

	/**
	 * 前方一致用のLIKE句のパラメータ作成。
	 *
	 * @param param
	 *            文字列
	 * @return 前方一致用LIKE句パラメータ
	 */
	protected static String createPrefixLike(String param) {
		return createLikeWord(param, true, false);
	}

	/**
	 * 後方一致用のLIKE句のパラメータ作成。
	 *
	 * @param param
	 *            文字列
	 * @return 後方一致用LIKE句パラメータ
	 */
	protected static String createSuffixLike(String param) {
		return createLikeWord(param, false, true);
	}

	/**
	 * LIKE句のパラメータ作成。
	 *
	 * @param param
	 *            文字列
	 * @param prefixMatch
	 *            前方一致の場合はtrue
	 * @param suffixMatch
	 *            後方一致の場合はtrue
	 * @return LIKE句用パラメータ
	 */
	protected static String createLikeWord(String param, boolean prefixMatch, boolean suffixMatch) {
		String likeWord = StringUtils.EMPTY;
		if (suffixMatch) {
			likeWord += LIKE_PATTERN_WORD;
		}
		likeWord += param;
		if (prefixMatch) {
			likeWord += LIKE_PATTERN_WORD;
		}
		return likeWord;
	}

}
