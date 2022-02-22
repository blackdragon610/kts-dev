package jp.co.keyaki.cleave.fw.dao;

import java.util.LinkedList;
import java.util.List;

/**
 * 発行するSQL情報（SQL文とパラメータ）を構築できるシンプルな実装クラス。
 *
 * <p>
 * フレームワークが採用しているSQL構築方法と異なる場合、APIを利用してSQL文と
 * パラメータを独自に設定できるようにした{@link SQLInfo}実装クラス。
 * </p>
 *
 * @author ytakahashi
 * @SQLInfo
 */
public class SimpleSQLInfo implements SQLInfo {

    /**
     * SQL文。
     */
    private String sql = "";

    /**
     * バインドパラメータ。
     */
    private List<Object> parameters = new LinkedList<Object>();

    /**
     * コンストラクタ。
     *
     */
    public SimpleSQLInfo() {
    }

    /**
     *
     * SQL文を返します。
     *
     * @return SQL文
     * @see jp.co.ans.dao.base.SQLInfo#getSQL()
     */
    public String getSQL() {
        return this.sql;
    }

    /**
     * SQL文を設定します。
     *
     * @param sql SQL文
     */
    public void setSQL(String sql) {
        this.sql = sql;
    }

    /**
     *
     * SQL文を発行するにあたって必要なバインドパラメータを返却します。
     *
     *
     * @return バインドパラメータ
     * @see jp.co.ans.dao.base.SQLInfo#getParameters()
     */
    public List<Object> getParameters() {
        return parameters;
    }

    /**
     *
     * SQL文を発行するにあたって必要なバインドパラメータを設定します。
     *
     * @param parameters バインドパラメータ
     */
    public void setParameters(List<Object> parameters) {
        this.parameters.clear();
        this.parameters.addAll(parameters);
    }

    /**
     *
     * SQL文を発行するにあたって必要なバインドパラメータを追加します。
     *
     * @param parameters バインドパラメータ
     */
    public void addParameter(Object parameter) {
        parameters.add(parameter);
    }

    /**
     * このインスタンスの文字列表現を返します。
     *
     * @return インスタンスの文字列表現
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SQLInfo=[").append(getClass().getName()).append("]\n");
        sb.append("sql=[").append(getSQL()).append("]\n");
        sb.append("param=").append(getParameters());
        return sb.toString();
    }
}
