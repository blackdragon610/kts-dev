package jp.co.keyaki.cleave.fw.dao.util;

import java.sql.ResultSet;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.ResultSetHandler;
import jp.co.keyaki.cleave.fw.dao.jdbc.JdbcUtils;

/**
 * {@link ResultSet}のカーソルを次に移動するための{@link ResultSetHandler}実装クラス。
 * 
 * <p>
 * {@link ResultSet#next()}を実施し、戻り値が<code>true</code>の場合 コンストラクタで指定された
 * {@link ResultSetHandler}に処理を委譲します。 {@link ResultSet#next()}を実施し、戻り値が
 * <code>false</code>の場合 戻り値を<code>null</code>として返却します。
 * </p>
 * 
 * @author ytakahashi
 * 
 * @param <E>
 */
public class FetchResultSetHandler<E> implements ResultSetHandler<E> {

	/**
	 * 委譲先{@link ResultSetHandler}。
	 */
	private ResultSetHandler<E> handler;

	/**
	 * コンストラクタ。
	 * 
	 * <p>
	 * 第一引数の委譲先{@link ResultSetHandler}がそもそも、{@link FetchResultSetHandler}の場合は
	 * 引数の{@link FetchResultSetHandler}が保持している委譲先{@link ResultSetHandler}を、
	 * 自インスタンスの委譲先{@link ResultSetHandler}とします。
	 * </p>
	 * 
	 * @param handler
	 *            委譲先{@link ResultSetHandler}
	 */
	public FetchResultSetHandler(ResultSetHandler<E> handler) {
		if (handler instanceof FetchResultSetHandler<?>) {
			this.handler = ((FetchResultSetHandler<E>) handler).handler;
		} else {
			this.handler = handler;
		}
	}

	/**
	 * {@link ResultSet#next()}を実施し、コンストラクタで指定された{@link ResultSetHandler}
	 * に処理を委譲します。
	 * 
	 * <p>
	 * {@link ResultSet#next()}を実施し、戻り値が<code>true</code>の場合 コンストラクタで指定された
	 * {@link ResultSetHandler}に処理を委譲した結果を返却します。 {@link ResultSet#next()}
	 * を実施し、戻り値が<code>false</code>の場合 戻り値を<code>null</code>として返却します。
	 * </p>
	 * 
	 * @param resultSet
	 *            操作対象のリザルトセット
	 * @return 操作した結果（{@link ResultSet#next()}を実施し、戻り値が<code>false</code>
	 *         の場合<coce>null</code>）
	 * @throws DaoException
	 *             リザルトセット操作中に例外が発生した場合
	 * @see jp.co.ans.dao.base.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	public E handle(ResultSet resultSet) throws DaoException {

		if (JdbcUtils.next(resultSet)) {
			return handler.handle(resultSet);
		}

		return null;
	}

}
