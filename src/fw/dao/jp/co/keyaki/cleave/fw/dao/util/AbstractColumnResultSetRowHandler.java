package jp.co.keyaki.cleave.fw.dao.util;

import java.sql.ResultSet;

import jp.co.keyaki.cleave.fw.dao.ResultSetHandler;

/**
 * 列番号指定で{@link ResultSet}から値を取り出す{@link ResultSetHandler}の基底クラス.
 *
 *
 * @author ytakahashi
 *
 * @param <E> 列の型
 */
public abstract class AbstractColumnResultSetRowHandler<E> implements ResultSetHandler<E> {

	/**
	 * 取得する列番号.
	 */
    protected int columnNo;

    /**
     * コンストラクタ.
     *
     * @param columnNo 取得する列番号
     */
    protected AbstractColumnResultSetRowHandler(int columnNo) {
        this.columnNo = columnNo;
    }

    /**
     * 取得する列番号を返します.
     *
     * @return 取得する列番号
     */
    protected int getColumnNo() {
        return columnNo;
    }

}
