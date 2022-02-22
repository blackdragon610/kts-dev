package jp.co.keyaki.cleave.fw.dao.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * メソッドの呼び出しおよび戻り値をログに出力し、戻り値が主要なJDBCインターフェースを実装インスタンスの場合、{@link Proxy}を戻り値とする{@link InvocationHandler}。
 *
 * <p>
 * {@link Proxy}作成対象としては{@link ProxyFactory}で定義されているのもです。
 * </p>
 *
 * @author ytakahashi
 * @see ProxyFactory
 */
public class WrapInvocationHandler implements InvocationHandler {

    /**
     * ロガー。
     */
    private static final Log LOG = LogFactory.getLog(WrapInvocationHandler.class);

    /**
     * ラップされていない実際のインスタンス。
     */
    public Object realObject;

    /**
     * コンストラクタ。
     *
     * @param realObject ラップされていない実際のインスタンス
     */
    WrapInvocationHandler(Object realObject) {
        this.realObject = realObject;
    }

    /**
     * メソッドの呼び出しをデバックします。
     *
     * <p>
     * このメソッドでは、メソッドの呼び出しおよび戻り値をログに出力します。
     * </p>
     * <p>
     * また、メソッドの戻り値がJDBCの主要なインターフェースを実装したクラスの場合、そのインスタンスに対して{@link Proxy}を生成し戻り値として返却します。
     * {@link Proxy}作成対象としては{@link ProxyFactory}で定義されているのもです。
     * </p>
     *
     * @param proxy {@link Proxy}インスタンス
     * @param method 呼び出されたメソッド
     * @param args メソッドの引数
     * @throws Throwable メソッド呼び出しに失敗した場合
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     *
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (LOG.isDebugEnabled()) {
            LOG.debug("invoke method " + method + " args " + Arrays.toString(args));
        }
        Object result = method.invoke(realObject, args);
        if (LOG.isDebugEnabled()) {
            LOG.debug("\tresult " + result);
        }
        if (result == null) {
            return result;
        }
        Class<?> resultClass = result.getClass();
        if (Connection.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(Connection.class.cast(result));
        }
        if (DatabaseMetaData.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(DatabaseMetaData.class.cast(result));
        }
        if (Savepoint.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(Savepoint.class.cast(result));
        }
        if (CallableStatement.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(CallableStatement.class.cast(result));
        }
        if (PreparedStatement.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(PreparedStatement.class.cast(result));
        }
        if (Statement.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(Statement.class.cast(result));
        }
        if (ResultSet.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(ResultSet.class.cast(result));
        }
        if (ResultSetMetaData.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(ResultSetMetaData.class.cast(result));
        }
        if (ParameterMetaData.class.isAssignableFrom(resultClass)) {
            return ProxyFactory.wrap(ParameterMetaData.class.cast(result));
        }
        if (Array.class.isAssignableFrom(resultClass)) {
        	return ProxyFactory.wrap(Array.class.cast(result));
        }
        if (Blob.class.isAssignableFrom(resultClass)) {
        	return ProxyFactory.wrap(Blob.class.cast(result));
        }
        if (Clob.class.isAssignableFrom(resultClass)) {
        	return ProxyFactory.wrap(Clob.class.cast(result));
        }
        if (SQLData.class.isAssignableFrom(resultClass)) {
        	return ProxyFactory.wrap(SQLData.class.cast(result));
        }
        if (SQLInput.class.isAssignableFrom(resultClass)) {
        	return ProxyFactory.wrap(SQLInput.class.cast(result));
        }
        if (SQLOutput.class.isAssignableFrom(resultClass)) {
        	return ProxyFactory.wrap(SQLOutput.class.cast(result));
        }
        if (Struct.class.isAssignableFrom(resultClass)) {
        	return ProxyFactory.wrap(Struct.class.cast(result));
        }

        return result;
    }
}
