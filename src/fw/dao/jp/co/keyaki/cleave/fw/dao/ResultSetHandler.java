package jp.co.keyaki.cleave.fw.dao;

import java.sql.ResultSet;

/**
 *
 * リザルトセット（{@link ResultSet}）の内容を操作する戦略を表すインターフェース。
 *
 * <p>
 * このインターフェースは主に、リザルトセットの内容を他のインスタンスへ割り当てる事を実現するためのインターフェースです。
 * </p>
 *
 * @author ytakahashi
 *
 * @param <E>
 *            操作結果を表すクラス（詰めなおす対象となるクラス）
 * @see ResultSet
 */
public interface ResultSetHandler<E> {

    /**
     * リザルトセット{@link ResultSet}の内容に対して操作するためのハンドリングメソッド。
     *
     * @param resultSet
     *            操作対象のリザルトセット
     * @return 操作した結果
     * @throws DaoException
     *             リザルトセット操作中に例外が発生した場合
     */
    E handle(ResultSet resultSet) throws DaoException;

}
