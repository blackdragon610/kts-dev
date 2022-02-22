package jp.co.keyaki.cleave.fw.dao.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.DaoMessageDefine;

/**
 * JDBC API 操作ユーティリティクラス.
 *
 * @author ytakahashi
 *
 */
public class JdbcUtils {

	/**
	 * ロガー.
	 */
	private static final Log LOG = LogFactory.getLog(JdbcUtils.class);

	/**
	 * リザルトセットカーソルを次に移動します.
	 *
	 * @param resultSet
	 *            操作対象リザルトセット
	 * @return true:有効なカーソルが存在する場合/false:最終カーソルを超えた場合
	 * @throws DaoException
	 *             カーソル操作時に例外が発生した場合
	 */
	public static boolean next(ResultSet resultSet) throws DaoException {
		try {
			return resultSet.next();
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * カレントカーソルの指定列の値をオブジェクトで取得し返します.
	 *
	 * @param resultSet
	 * @param columnName
	 * @return
	 * @throws DaoException
	 */
	public static Object getObject(ResultSet resultSet, String columnName) throws DaoException {
		try {
			return resultSet.getObject(columnName);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * カレントカーソルの指定列の値をオブジェクトで取得し返します.
	 *
	 * @param resultSet 操作対象リザルトセット
	 * @param columnNo 取得する列番号
	 * @return 値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static Object getObject(ResultSet resultSet, int columnNo) throws DaoException {
		try {
			return resultSet.getObject(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * カレントカーソルの指定列の値を文字列で取得し返します.
	 *
	 * @param resultSet 操作対象リザルトセット
	 * @param columnNo 取得する列番号
	 * @return 値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static String getString(ResultSet resultSet, int columnNo) throws DaoException {
		try {
			return resultSet.getString(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * カレントカーソルの指定列の値を日付型で取得し返します.
	 *
	 * @param resultSet 操作対象リザルトセット
	 * @param columnNo 取得する列番号
	 * @return 値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static Date getDate(ResultSet resultSet, int columnNo) throws DaoException {
		try {
			return resultSet.getDate(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * カレントカーソルの指定列の値をIntegerで取得し返します.
	 *
	 * @param resultSet 操作対象リザルトセット
	 * @param columnNo 取得する列番号
	 * @return 値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static Integer getInteger(ResultSet resultSet, int columnNo) throws DaoException {
		try {
			int v = resultSet.getInt(columnNo);
			if (resultSet.wasNull()) {
				return null;
			}
			return v;
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * カレントカーソルの指定列の値をLongで取得し返します.
	 *
	 * @param resultSet 操作対象リザルトセット
	 * @param columnNo 取得する列番号
	 * @return 値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static Long getLong(ResultSet resultSet, int columnNo) throws DaoException {
		try {
			long v = resultSet.getLong(columnNo);
			if (resultSet.wasNull()) {
				return null;
			}
			return v;
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * カレントカーソルの指定列の値をBigDecimalで取得し返します.
	 *
	 * @param resultSet 操作対象リザルトセット
	 * @param columnNo 取得する列番号
	 * @return 値
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static BigDecimal getBigDecimal(ResultSet resultSet, int columnNo) throws DaoException {
		try {
			return resultSet.getBigDecimal(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * リザルトセットからメタデータを取得し返します.
	 *
	 * @param resultSet 操作対象リザルトセット
	 * @return メタデータ
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static ResultSetMetaData getMetaData(ResultSet resultSet) throws DaoException {
		try {
			return resultSet.getMetaData();
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000015, sqle);
		}
	}

	/**
	 * メタデータより列数を取得し返します.
	 *
	 * @param metaData 取得対象メタデータ
	 * @return 列数
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static int getColumnCount(ResultSetMetaData metaData) throws DaoException {
		try {
			return metaData.getColumnCount();
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000016, sqle);
		}
	}

	/**
	 * メタデータより指定列の列名を取得し返します.
	 *
	 * @param metaData 取得対象メタデータ
	 * @param columnNo 列番号
	 * @return 列名
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static String getColumnLabel(ResultSetMetaData metaData, int columnNo) throws DaoException {
		try {
			return metaData.getColumnLabel(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000016, sqle);
		}
	}

	/**
	 * メタデータより指定列のデータ型を取得し返します.
	 *
	 * @param metaData 取得対象メタデータ
	 * @param columnNo 列番号
	 * @return データ型
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static int getColumnType(ResultSetMetaData metaData, int columnNo) throws DaoException {
		try {
			return metaData.getColumnType(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000016, sqle);
		}
	}

	/**
	 * メタデータより指定列のデータ型名を取得し返します.
	 *
	 * @param metaData 取得対象メタデータ
	 * @param columnNo 列番号
	 * @return データ型名
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static String getColumnTypeName(ResultSetMetaData metaData, int columnNo) throws DaoException {
		try {
			return metaData.getColumnTypeName(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000016, sqle);
		}
	}

	/**
	 * メタデータより指定列のデータ型のクラス名を取得し返します.
	 *
	 * @param metaData 取得対象メタデータ
	 * @param columnNo 列番号
	 * @return データ型のクラス名
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static String getColumnClassName(ResultSetMetaData metaData, int columnNo) throws DaoException {
		try {
			return metaData.getColumnClassName(columnNo);
		} catch (SQLException sqle) {
			throw new DaoException(DaoMessageDefine.E000016, sqle);
		}
	}

	/**
	 * メタデータより列数を取得し返します.
	 *
	 * @param resultSet 取得対象リザルトセット
	 * @return 列数
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static int getColumnCount(ResultSet resultSet) throws DaoException {
		return getColumnCount(getMetaData(resultSet));
	}

	/**
	 * リザルトセットより指定列の列名を取得し返します.
	 *
	 * @param resultSet 取得対象リザルトセット
	 * @param columnNo 列番号
	 * @return 列名
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static String getColumnLabel(ResultSet resultSet, int columnNo) throws DaoException {
		return getColumnLabel(getMetaData(resultSet), columnNo);
	}

	/**
	 * リザルトセットより指定列のデータ型を取得し返します.
	 *
	 * @param resultSet 取得対象リザルトセット
	 * @param columnNo 列番号
	 * @return データ型
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static int getColumnType(ResultSet resultSet, int columnNo) throws DaoException {
		return getColumnType(getMetaData(resultSet), columnNo);
	}

	/**
	 * リザルトセットより指定列のデータ型名を取得し返します.
	 *
	 * @param resultSet 取得対象リザルトセット
	 * @param columnNo 列番号
	 * @return データ型名
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static String getColumnTypeName(ResultSet resultSet, int columnNo) throws DaoException {
		return getColumnTypeName(getMetaData(resultSet), columnNo);
	}

	/**
	 * リザルトセットより指定列のデータ型のクラス名を取得し返します.
	 *
	 * @param resultSet 取得対象リザルトセット
	 * @param columnNo 列番号
	 * @return データ型のクラス名
	 * @throws DaoException API操作時に例外が発生した場合
	 */
	public static String getColumnClassName(ResultSet resultSet, int columnNo) throws DaoException {
		return getColumnClassName(getMetaData(resultSet), columnNo);
	}

	/**
	 * リザルトセットを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のリザルトセット
	 */
	public static void closeQuietly(ResultSet resultSet, Statement stmt) {
		try {
			close(resultSet, stmt, true);
		} catch (DaoException e) {
			// ありえない
		}
	}

	/**
	 * リザルトセットを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のリザルトセット
	 * @param isIgnoreException
	 *            閉じる際に例外が発生した場合無視するか true:無視する/false:無視しない
	 * @throws DaoException
	 *             第２引数がfalseで閉じる際に例外が発生した場合
	 */
	public static void close(ResultSet resultSet, Statement stmt, boolean isIgnoreException) throws DaoException {
		close(resultSet, isIgnoreException);
		close(stmt, isIgnoreException);
	}

	/**
	 * リザルトセットを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のリザルトセット
	 */
	public static void closeQuietly(ResultSet resultSet) {
		try {
			close(resultSet, true);
		} catch (DaoException e) {
			// ありえない
		}
	}

	/**
	 * リザルトセットを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のリザルトセット
	 * @throws DaoException
	 *             リザルトセットを閉じる際に例外が発生した場合
	 */
	public static void close(ResultSet resultSet) throws DaoException {
		close(resultSet, false);
	}

	/**
	 * リザルトセットを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のリザルトセット
	 * @param isIgnoreException
	 *            閉じる際に例外が発生した場合無視するか true:無視する/false:無視しない
	 * @throws DaoException
	 *             第２引数がfalseで閉じる際に例外が発生した場合
	 */
	public static void close(ResultSet resultSet, boolean isIgnoreException) throws DaoException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException sqle) {
				if (isIgnoreException) {
					if (LOG.isInfoEnabled()) {
						LOG.info("catch exception on call ResultSet#close()", sqle);
					}
					return;
				}
				throw new DaoException(DaoMessageDefine.E000017, sqle);
			} finally {
				resultSet = null;
			}

		}
	}

	/**
	 * ステートメントを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のステートメント
	 */
	public static void closeQuietly(Statement stmt) {
		try {
			close(stmt, true);
		} catch (DaoException e) {
			// ありえない
		}
	}

	/**
	 * ステートメントを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のステートメント
	 * @throws DaoException
	 *             ステートメントを閉じる際に例外が発生した場合
	 */
	public static void close(Statement stmt) throws DaoException {
		close(stmt, false);
	}

	/**
	 * ステートメントを閉じます。
	 *
	 * @param resultSet
	 *            閉じる対象のステートメント
	 * @param isIgnoreException
	 *            閉じる際に例外が発生した場合無視するか true:無視する/false:無視しない
	 * @throws DaoException
	 *             第２引数がfalseで閉じる際に例外が発生した場合
	 */
	public static void close(Statement stmt, boolean isIgnoreException) throws DaoException {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqle) {
				if (isIgnoreException) {
					if (LOG.isInfoEnabled()) {
						LOG.info("catch exception on call Statement#close()", sqle);
					}
					return;
				}
				throw new DaoException(DaoMessageDefine.E000018, sqle);
			} finally {
				stmt = null;
			}
		}
	}
}
