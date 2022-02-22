package jp.co.keyaki.cleave.fw.dao.jdbc;

import java.lang.reflect.Proxy;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;

/**
 * java.sql の主要なインターフェースに対する{@link Proxy}を生成するクラス。
 *
 * <p>
 * このクラスでは、主に<code>JDBC</code>のデバック利用のために使用する。
 * </p>
 * <p>
 * このクラスが作成する{@link Proxy}は、下記のインターフェースに対して作成するためのメソッドを提供する。
 * <ul>
 * <li>{@link Connection}</li>
 * <li>{@link DatabaseMetaData}</li>
 * <li>{@link Savepoint}</li>
 * <li>{@link Statement}</li>
 * <li>{@link PreparedStatement}</li>
 * <li>{@link CallableStatement}</li>
 * <li>{@link ParameterMetaData}</li>
 * <li>{@link ResultSet}</li>
 * <li>{@link ResultSetMetaData}</li>
 * <li>{@link Array}</li>
 * <li>{@link Blob}</li>
 * <li>{@link Clob}</li>
 * <li>{@link SQLData}</li>
 * <li>{@link SQLInput}</li>
 * <li>{@link SQLOutput}</li>
 * <li>{@link Struct}</li>
 * </ul>
 * </p>
 *
 * @author ytakahashi
 *
 */
public class ProxyFactory {

    /**
     * {@link Proxy}を生成し返却します。
     *
     * <p>
     * このメソッド内では、第一引数がすでに{@link Proxy}であり、かつ{@link java.lang.reflect.InvocationHandler}として
     * {@link WrapInvocationHandler}で有る場合は第一引数の値をそのまま返します。
     * （二重の{@link Proxy}は作成しません。）
     * </p>
     *
     * <p>
     * 実際に{@link Proxy}を生成する場合は、{@link java.lang.reflect.InvocationHandler}として
     * {@link WrapInvocationHandler}を利用します。
     * </p>
     *
     * @param realObject {@link Proxy}を作成する実際のインスタンス。
     * @param interfaceClass {@link Proxy}を生成するためのインターフェース。
     * @return {@link Proxy}インスタンス。
     */
    private static Object createProxy(Object realObject, Class<?>... interfaceClass) {
        if (Proxy.isProxyClass(realObject.getClass())
                && WrapInvocationHandler.class.isAssignableFrom(Proxy.getInvocationHandler(realObject).getClass())) {
            return realObject;
        }
        return Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(),
                interfaceClass, new WrapInvocationHandler(realObject));
    }

    /**
     * {@link Connection}に対する{@link Proxy}を生成し返却します。
     *
     * @param connection {@link Proxy}を作成したい{@link Connection}インスタンス
     * @return {@link Connection}に対する{@link Proxy}
     */
    public static Connection wrap(Connection connection) {
        return Connection.class.cast(createProxy(connection, Connection.class));
    }

    /**
     * {@link DatabaseMetaData}に対する{@link Proxy}を生成し返却します。
     *
     * @param databaseMetaData {@link Proxy}を作成したい{@link DatabaseMetaData}インスタンス
     * @return {@link DatabaseMetaData}に対する{@link Proxy}
     */
    public static DatabaseMetaData wrap(DatabaseMetaData databaseMetaData) {
        return DatabaseMetaData.class.cast(createProxy(databaseMetaData, DatabaseMetaData.class));
    }

    /**
     * {@link Savepoint}に対する{@link Proxy}を生成し返却します。
     *
     * @param savepoint {@link Proxy}を作成したい{@link Savepoint}インスタンス
     * @return {@link Savepoint}に対する{@link Proxy}
     */
    public static Savepoint wrap(Savepoint savepoint) {
        return Savepoint.class.cast(createProxy(savepoint, Savepoint.class));
    }

    /**
     * {@link Statement}に対する{@link Proxy}を生成し返却します。
     *
     * @param statement {@link Proxy}を作成したい{@link Statement}インスタンス
     * @return {@link Statement}に対する{@link Proxy}
     */
    public static Statement wrap(Statement statement) {
        return Statement.class.cast(createProxy(statement, Statement.class));
    }

    /**
     * {@link PreparedStatement}に対する{@link Proxy}を生成し返却します。
     *
     * @param preparedStatement {@link Proxy}を作成したい{@link PreparedStatement}インスタンス
     * @return {@link PreparedStatement}に対する{@link Proxy}
     */
    public static PreparedStatement wrap(PreparedStatement preparedStatement) {
        return PreparedStatement.class.cast(createProxy(preparedStatement, PreparedStatement.class, Statement.class));
    }

    /**
     * {@link CallableStatement}に対する{@link Proxy}を生成し返却します。
     *
     * @param callableStatement {@link Proxy}を作成したい{@link CallableStatement}インスタンス
     * @return {@link CallableStatement}に対する{@link Proxy}
     */
    public static CallableStatement wrap(CallableStatement callableStatement) {
        return CallableStatement.class.cast(createProxy(callableStatement, CallableStatement.class, PreparedStatement.class, Statement.class));
    }

    /**
     * {@link ResultSet}に対する{@link Proxy}を生成し返却します。
     *
     * @param resultSet {@link Proxy}を作成したい{@link ResultSet}インスタンス
     * @return {@link ResultSet}に対する{@link Proxy}
     */
    public static ResultSet wrap(ResultSet resultSet) {
        return ResultSet.class.cast(createProxy(resultSet, ResultSet.class));
    }

    /**
     * {@link ResultSetMetaData}に対する{@link Proxy}を生成し返却します。
     *
     * @param resultSetMetaData {@link Proxy}を作成したい{@link ResultSetMetaData}インスタンス
     * @return {@link ResultSetMetaData}に対する{@link Proxy}
     */
    public static ResultSetMetaData wrap(ResultSetMetaData resultSetMetaData) {
        return ResultSetMetaData.class.cast(createProxy(resultSetMetaData, ResultSetMetaData.class));
    }

    /**
     * {@link ParameterMetaData}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link ParameterMetaData}インスタンス
     * @return {@link ParameterMetaData}に対する{@link Proxy}
     */
    public static ParameterMetaData wrap(ParameterMetaData parameterMetaData) {
        return ParameterMetaData.class.cast(createProxy(parameterMetaData, ParameterMetaData.class));
    }

    /**
     * {@link Array}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link Array}インスタンス
     * @return {@link Array}に対する{@link Proxy}
     */
    public static Array wrap(Array array) {
        return Array.class.cast(createProxy(array, Array.class));
    }

    /**
     * {@link Blob}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link Blob}インスタンス
     * @return {@link Blob}に対する{@link Proxy}
     */
    public static Blob wrap(Blob blog) {
        return Blob.class.cast(createProxy(blog, Blob.class));
    }

    /**
     * {@link Clob}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link Clob}インスタンス
     * @return {@link Clob}に対する{@link Proxy}
     */
    public static Clob wrap(Clob clog) {
        return Clob.class.cast(createProxy(clog, Clob.class));
    }

    /**
     * {@link SQLData}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link SQLData}インスタンス
     * @return {@link SQLData}に対する{@link Proxy}
     */
    public static SQLData wrap(SQLData sqlData) {
        return SQLData.class.cast(createProxy(sqlData, SQLData.class));
    }

    /**
     * {@link SQLInput}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link SQLInput}インスタンス
     * @return {@link SQLInput}に対する{@link Proxy}
     */
    public static SQLInput wrap(SQLInput sqlInput) {
        return SQLInput.class.cast(createProxy(sqlInput, SQLInput.class));
    }

    /**
     * {@link SQLOutput}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link SQLOutput}インスタンス
     * @return {@link SQLOutput}に対する{@link Proxy}
     */
    public static SQLOutput wrap(SQLOutput sqlOutput) {
        return SQLOutput.class.cast(createProxy(sqlOutput, SQLOutput.class));
    }

    /**
     * {@link Struct}に対する{@link Proxy}を生成し返却します。
     *
     * @param parameterMetaData {@link Proxy}を作成したい{@link Struct}インスタンス
     * @return {@link Struct}に対する{@link Proxy}
     */
    public static Struct wrap(Struct struct) {
        return Struct.class.cast(createProxy(struct, Struct.class));
    }
}
