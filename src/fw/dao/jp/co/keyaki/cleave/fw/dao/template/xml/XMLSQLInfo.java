package jp.co.keyaki.cleave.fw.dao.template.xml;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jp.co.keyaki.cleave.fw.dao.SQLInfo;

/**
 * XMLをテンプレートとし発行するSQL情報を表す実装クラス。
 *
 * 当クラスは、SQL文を作成するために、SQL文を構成する文字列を追加していく機能を有します。
 *
 * @author ytakahashi
 *
 */
public class XMLSQLInfo implements SQLInfo {

    /**
     * SQLID。
     */
    private String sqlId = null;

    /**
     * 構築するSQL文。
     */
    private StringBuilder sql = new StringBuilder();

    /**
     * SQLを発行する際に必要なパラメータ。（JDBCの?に対してバインドするパラメータ）
     */
    private List<Object> params = new LinkedList<Object>();

    /**
     * コンストラクタ。
     *
     * @param sqlId SQLテンプレート内のSQLID
     */
    XMLSQLInfo(String sqlId) {
        this.sqlId = sqlId;
    }

    /**
     *
     * 現状構築されているSQL文の最後尾に引数の文字列を追加します。
     *
     * @param sqlFragment 追加する文字列。
     */
    void appendFragment(String sqlFragment) {
        sql.append(sqlFragment);
        sql.append(" ");
    }

    /**
     * SQL発行に必要なパラメータを追加します。
     *
     * @param param 追加するパラメータ
     */
    void addParameter(Object param) {
        params.add(param);
    }

    /**
     * 構築したSQL文を発行するにあたって必要なバインドパラメータを返却します。
     *
     * @return バインドパラメータ
     * @see SQLInfo#getParameters()
     */
    public List<Object> getParameters() {
        return Collections.unmodifiableList(params);
    }

    /**
     *
     * 構築したSQL文を返します。
     *
     * @return SQL文
     * @see SQLInfo#getSQL()
     */
    public String getSQL() {
        return sql.toString();
    }

    /**
     *
     * このインスタンスの文字列表現を返します。
     *
     * @return このインスタンスの文字列表現
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SQLInfo=[").append(getClass().getName()).append("]\n");
        sb.append("id=[").append(sqlId).append("]\n");
        sb.append("sql=[").append(getSQL()).append("]\n");
        sb.append("param=").append(getParameters());
        return sb.toString();
    }
}
