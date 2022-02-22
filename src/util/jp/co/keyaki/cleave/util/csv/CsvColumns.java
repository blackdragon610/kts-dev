/*
 *
 */
package jp.co.keyaki.cleave.util.csv;

import java.util.Iterator;

import org.apache.commons.lang.math.NumberUtils;

/**
 * CSVの列定義群を表すクラス.
 *
 * @author Yoshikazu Takahashi
 */
public class CsvColumns implements Iterable<String> {

    /**
     * CSVの列定義群.
     */
    private final CsvRecord columns;

    /**
     * コンストラクタ.
     *
     * @param record CSVの列定義群
     */
    public CsvColumns(CsvRecord record) {
        columns = record;
    }

    /**
     * 列位置を算出します.
     *
     * @param columnName 検索する列名.
     * @return 見つかった列位置 見つからなかった場合-1
     */
    public int findColumnIndex(String columnName) {
        int columnIndex = 0;
        for (String value : columns) {
            if (columnName.equals(value)) {
                return columnIndex;
            }
            columnIndex++;
        }
        return NumberUtils.INTEGER_MINUS_ONE;
    }

    /**
     * 列定義群をレコードして返します.
     *
     * @return 列定義群
     */
    public CsvRecord getRecord() {
        return columns;
    }

    /**
     * 列定義群をイテレータで返します.
     *
     * @return 列定義群
     */
    public Iterator<String> iterator() {
        return columns.iterator();
    }
}
