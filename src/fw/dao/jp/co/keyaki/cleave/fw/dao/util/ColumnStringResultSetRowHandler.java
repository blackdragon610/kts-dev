package jp.co.keyaki.cleave.fw.dao.util;

import java.sql.ResultSet;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.ResultSetHandler;
import jp.co.keyaki.cleave.fw.dao.jdbc.JdbcUtils;

/**
 * 列番号指定で{@link ResultSet}から{@link String}値を取り出す{@link ResultSetHandler}クラス.
 *
 * @author ytakahashi
 */
public class ColumnStringResultSetRowHandler extends AbstractColumnResultSetRowHandler<String> {

	/**
	 * 1列目を{@link ResultSet}から{@link String}値を取り出す{@link ResultSetHandler}クラス.
	 */
    public static final ColumnStringResultSetRowHandler FIRST_COLUMN = new ColumnStringResultSetRowHandler(1);

    /**
     * コンストラクタ.
     *
     * @param columnNo 取得する列番号
     */
    public ColumnStringResultSetRowHandler(int columnNo) {
        super(columnNo);
    }

	/**
	 * 引数の{@link ResultSet}のカレントカーソルの指定列を{@link String}値で取得し返します.
	 *
	 * @param resultSet 操作対象{@link ResultSet}
	 * @return 指定列の値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
    public String handle(ResultSet resultSet) throws DaoException {
        return JdbcUtils.getString(resultSet, getColumnNo());
    }

}
