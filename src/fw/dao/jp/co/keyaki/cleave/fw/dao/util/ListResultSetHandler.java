package jp.co.keyaki.cleave.fw.dao.util;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.ResultSetHandler;

/**
 * {@link ResultSet}への操作結果を{@link List}で表現する{@link ResultSetHandler}。
 * <p>
 * コンストラクタで指定された{@link ResultSetHandler}に処理を委譲し、 結果が<code>null</code>
 * が返却されるまでの処理を委譲し続け、 操作結果を{@link List}に溜め込み返却する{@link ResultSetHandler}。
 * </p>
 *
 * @author ytakahashi
 *
 * @param <E>
 *            {@link List}の要素として操作結果を表すクラス（詰めなおす対象となるクラス）
 * @see List
 * @see ResultSetHandler
 */
public class ListResultSetHandler<E> implements ResultSetHandler<List<E>> {

	/**
	 * 委譲先{@link ResultSetHandler}。
	 */
	private ResultSetHandler<E> handler;

	/**
	 * コンストラクタ。
	 *
	 * @param handler
	 *            委譲先{@link ResultSetHandler}
	 */
	public ListResultSetHandler(ResultSetHandler<E> handler) {
		this.handler = handler;
	}

	/**
	 * 委譲先{@link ResultSetHandler}の操作結果を{@link List}形式にして返却します。
	 *
	 * コンストラクタで指定された{@link ResultSetHandler}に処理を委譲し、 結果が<code>null</code>
	 * が返却されるまでの処理を委譲し続け、 操作結果を{@link List}に溜め込み返却する{@link ResultSetHandler}。
	 *
	 * @param resultSet
	 *            操作対象のリザルトセット
	 * @return 操作した結果（{@link List} <code>null</code>を返却することはありません。要素数0はありえます.）
	 * @throws DaoException
	 *             リザルトセット操作中に例外が発生した場合
	 * @see jp.co.ans.dao.base.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	public List<E> handle(ResultSet resultSet) throws DaoException {

		List<E> results = new LinkedList<E>();

		while (true) {
			E result = handler.handle(resultSet);
			if (result == null) {
				break;
			}
			results.add(result);
		}

		return results;
	}

}
