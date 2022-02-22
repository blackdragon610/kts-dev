package jp.co.keyaki.cleave.fw.dao.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import jp.co.keyaki.cleave.fw.dao.ResultSetHandler;

/**
 * リザルトセットファクトリークラス
 *
 * @author ytakahashi
 *
 */
public class ResultSetHandlerFactory {

	/**
	 * リザルトセットのカレントカーソルの値をBeanで取得する{@link ResultSetHandler}を返します.
	 *
	 * @param <E> 取得するBeanの型
	 * @param entityType 取得するBeanの型
	 * @return Beanで取得するための{@link ResultSetHandler}
	 */
	public static <E> ResultSetHandler<E> getNameMatchBeanRowHandler(Class<E> entityType) {
		return new NameMatchBeanResultSetRowHandler<E>(entityType);
	}

	/**
	 * リザルトセットのカレントカーソルの値を{@link Map}で取得する{@link ResultSetHandler}を返します.
	 *
	 * @return {@link Map}で取得するための{@link ResultSetHandler}
	 */
	public static ResultSetHandler<Map<String, Object>> getMapRowHandler() {
		return new MapResultSetRowHandler();
	}

	/**
	 * リザルトセットのカレントカーソルの一列目の値を{@link String}で取得する{@link ResultSetHandler}を返します.
	 *
	 * @return {@link String}で取得するための{@link ResultSetHandler}
	 */
	public static ResultSetHandler<String> getFirstColumnStringRowHandler() {
		return ColumnStringResultSetRowHandler.FIRST_COLUMN;
	}

	/**
	 * リザルトセットのカレントカーソルの一列目の値を{@link Date}で取得する{@link ResultSetHandler}を返します.
	 *
	 * @return {@link Date}で取得するための{@link ResultSetHandler}
	 */
	public static ResultSetHandler<Date> getFirstColumnDateRowHandler() {
		return ColumnDateResultSetRowHandler.FIRST_COLUMN;
	}

	/**
	 * リザルトセットのカレントカーソルの一列目の値を{@link Integer}で取得する{@link ResultSetHandler}を返します.
	 *
	 * @return {@link Integer}で取得するための{@link ResultSetHandler}
	 */
	public static ResultSetHandler<Integer> getFirstColumnIntegerRowHandler() {
		return ColumnIntegerResultSetRowHandler.FIRST_COLUMN;
	}

	/**
	 * リザルトセットのカレントカーソルの一列目の値を{@link Long}で取得する{@link ResultSetHandler}を返します.
	 *
	 * @return {@link Long}で取得するための{@link ResultSetHandler}
	 */
	public static ResultSetHandler<Long> getFirstColumnLongRowHandler() {
		return ColumnLongResultSetRowHandler.FIRST_COLUMN;
	}

	/**
	 * リザルトセットのカレントカーソルの一列目の値を{@link BigDecimal}で取得する{@link ResultSetHandler}を返します.
	 *
	 * @return {@link BigDecimal}で取得するための{@link ResultSetHandler}
	 */
	public static ResultSetHandler<BigDecimal> getFirstColumnBigDecimalRowHandler() {
		return ColumnBigDecimalResultSetRowHandler.FIRST_COLUMN;
	}

}
