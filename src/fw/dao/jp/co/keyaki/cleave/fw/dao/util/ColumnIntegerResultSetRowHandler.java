package jp.co.keyaki.cleave.fw.dao.util;

import java.sql.ResultSet;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.ResultSetHandler;
import jp.co.keyaki.cleave.fw.dao.jdbc.JdbcUtils;

/**
 * 列番号指定で{@link ResultSet}から{@link Integer}値を取り出す{@link ResultSetHandler}クラス.
 *
 * @author ytakahashi
 */
public class ColumnIntegerResultSetRowHandler extends AbstractColumnResultSetRowHandler<Integer> {

	/**
	 * 1列目を{@link ResultSet}から{@link Integer}値を取り出す{@link ResultSetHandler}クラス.
	 */
	public static final ColumnIntegerResultSetRowHandler FIRST_COLUMN = new ColumnIntegerResultSetRowHandler(1);

    /**
     * コンストラクタ.
     *
     * @param columnNo 取得する列番号
     */
	public ColumnIntegerResultSetRowHandler(int columnNo) {
		super(columnNo);
	}

	/**
	 * 引数の{@link ResultSet}のカレントカーソルの指定列を{@link Integer}値で取得し返します.
	 *
	 * @param resultSet 操作対象{@link ResultSet}
	 * @return 指定列の値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public Integer handle(ResultSet resultSet) throws DaoException {
		return JdbcUtils.getInteger(resultSet, getColumnNo());
	}

}
