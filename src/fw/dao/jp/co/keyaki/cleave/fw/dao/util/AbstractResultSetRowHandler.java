package jp.co.keyaki.cleave.fw.dao.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.ResultSetHandler;
import jp.co.keyaki.cleave.fw.dao.jdbc.JdbcUtils;

/**
 * リザルトセットの１行に対する操作を行う抽象クラス。
 *
 *
 * @author ytakahashi
 *
 * @param <E> 操作結果を表すクラス
 */
public abstract class AbstractResultSetRowHandler<E> implements
        ResultSetHandler<E> {

    /**
     * ロガー。
     */
    private static final Log LOG = LogFactory
            .getLog(AbstractResultSetRowHandler.class);

    /**
     * リザルトセットのカラム情報。
     */
    private List<ColumnMappingInfo> cacheResultSetMetaInfos;

    /**
     * デフォルトコンストラクタ。
     *
     */
    public AbstractResultSetRowHandler() {
    }

    /**
     * リザルトセットのカラム情報を返します。
     *
     * <p>
     * このメソッドは、一度解析した情報がある場合は解析済みのカラム情報を返却します。
     * </p>
     *
     * @param resultSet 解析対象のリザルトセット
     * @return カラム情報
     * @throws DaoException リザルトセットの解析時に例外が発生した場合
     */
    protected List<ColumnMappingInfo> getResultSetMetaInfos(ResultSet resultSet)
            throws DaoException {
        if (cacheResultSetMetaInfos != null) {
            return cacheResultSetMetaInfos;
        }
        cacheResultSetMetaInfos = parseResultSetMetaInfos(resultSet);
        return cacheResultSetMetaInfos;
    }

    /**
     * リザルトセットのメタ情報を解析し、カラム情報を生成し返します。
     *
     * @param resultSet 解析対象のリザルトセット
     * @return カラム情報
     * @throws DaoException リザルトセットの解析時に例外が発生した場合
     */
    protected List<ColumnMappingInfo> parseResultSetMetaInfos(
            ResultSet resultSet) throws DaoException {

        ResultSetMetaData metaData = JdbcUtils.getMetaData(resultSet);
        int columnCount = JdbcUtils.getColumnCount(resultSet);
        List<ColumnMappingInfo> cacheResultSetMetaInfos = new ArrayList<ColumnMappingInfo>(columnCount);
        for (int columnNo = 1; columnNo <= columnCount; columnNo++) {
            ColumnMappingInfo mappingInfo = new ColumnMappingInfo();
            mappingInfo.setColumnNo(columnNo);
            mappingInfo.setColumnName(JdbcUtils.getColumnLabel(metaData, columnNo));
            mappingInfo.setDatabaseType(JdbcUtils.getColumnType(metaData, columnNo));
            mappingInfo.setDatabaseTypeName(JdbcUtils.getColumnTypeName(metaData, columnNo));
            mappingInfo.setJavaTypeClassName(JdbcUtils.getColumnClassName(metaData, columnNo));
            cacheResultSetMetaInfos.add(mappingInfo);
            if (LOG.isDebugEnabled()) {
                LOG.debug("column info " + mappingInfo.toString());
            }
        }
        return cacheResultSetMetaInfos;
    }

    /**
     * カラム情報を表すクラス。
     *
     *  @author ytakahashi
     *
     */
    protected class ColumnMappingInfo {

        /**
         * 列のカラムのデリミタ。
         */
        private static final String WORD_DELIM = "_";

        /**
         * カラム列順。
         */
        private int columnNo;

        /**
         * カラム名。
         */
        private String columnName;

        /**
         * データベース上の列の型。
         * @see java.sql.Types
         */
        private int databaseType;

        /**
         * データベースベンダーの型名。
         */
        private String databaseTypeName;

        /**
         * データベース上の列の型がJava上でマッピングされるクラス。
         */
        private String javaTypeClassName;

        /**
         * Javaプロパティ名称
         */
        private String propertyName;

        /**
         * デフォルトコンストラクタ。
         *
         */
        public ColumnMappingInfo() {
        }

        /**
         * カラムの列順を返します。
         *
         * @return カラムの列順
         */
        public int getColumnNo() {
            return this.columnNo;
        }

        /**
         * カラムの列順を設定します。
         *
         * @param columnNo カラムの列順
         */
        public void setColumnNo(int columnNo) {
            this.columnNo = columnNo;
        }

        /**
         * カラム名を返します。
         *
         * @return カラム名
         */
        public String getColumnName() {
            return this.columnName;
        }

        /**
         * カラム名を設定します。
         *
         * @param columnName カラム名
         */
        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        /**
         * データベース上の列の型を返します。
         *
         * @return データベース上の列の型
         * @see java.sql.Types
         */
        public int getDatabaseType() {
            return this.databaseType;
        }

        /**
         * データベース上の列の型を設定します。
         *
         * @param databaseType データベース上の列の型
         * @see java.sql.Types
         */
        public void setDatabaseType(int databaseType) {
            this.databaseType = databaseType;
        }

        /**
         * データベースベンダーの型名を返します。
         *
         * @return データベースベンダーの型名
         */
        public String getDatabaseTypeName() {
            return this.databaseTypeName;
        }

        /**
         * データベースベンダーの型名を設定します。
         *
         * @param databaseTypeName データベースベンダーの型名
         */
        public void setDatabaseTypeName(String databaseTypeName) {
            this.databaseTypeName = databaseTypeName;
        }

        /**
         * データベース上の列の型がJava上でマッピングされるクラスを返します。
         *
         * @return データベース上の列の型がJava上でマッピングされるクラス
         */
        public String getJavaTypeClassName() {
            return this.javaTypeClassName;
        }

        /**
         * データベース上の列の型がJava上でマッピングされるクラスを設定します。
         *
         * @param javaTypeClassName データベース上の列の型がJava上でマッピングされるクラス
         */
        public void setJavaTypeClassName(String javaTypeClassName) {
            this.javaTypeClassName = javaTypeClassName;
        }

        /**
         * Javaプロパティ名称を返します。
         *
         * @return Javaプロパティ名称
         */
        public String getPropertyName() {
            if (columnName == null) {
                return null;
            }
            if (propertyName == null) {
                String[] lowerColumnWords = columnName.toLowerCase().split(WORD_DELIM);
                StringBuilder propertyName = new StringBuilder();
                boolean firstWord = true;
                for (String lowerColumnWord : lowerColumnWords) {
                    if (!firstWord) {
                        propertyName.append(lowerColumnWord.substring(0, 1)
                                .toUpperCase());
                        propertyName.append(lowerColumnWord.substring(1));
                    } else {
                        propertyName.append(lowerColumnWord);
                        firstWord = false;
                    }
                }
                this.propertyName = propertyName.toString();
            }
            return propertyName;
        }

        /**
         * このインスタンスの文字列表現返します。
         *
         * @see java.lang.Object#toString()
         */
        public String toString() {
            StringBuilder toString = new StringBuilder();
            toString.append(this.getClass().getSimpleName());
            toString.append("@columnNo=");
            toString.append(getColumnNo());
            toString.append(", columnName=");
            toString.append(getColumnName());
            toString.append(", databaseType=");
            toString.append(getDatabaseType());
            toString.append(", databaseTypeName=");
            toString.append(getDatabaseTypeName());
            toString.append(", javaTypeClassName=");
            toString.append(getJavaTypeClassName());
            toString.append(", propertyName=");
            toString.append(getPropertyName());
            return toString.toString();
        }
    }

}
