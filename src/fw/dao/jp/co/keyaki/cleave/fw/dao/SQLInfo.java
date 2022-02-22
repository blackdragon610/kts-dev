package jp.co.keyaki.cleave.fw.dao;

import java.util.List;

/**
 *
 * 発行するSQL情報を表すインターフェース。
 *
 * @author ytakahashi
 *
 */
public interface SQLInfo {

    /**
     *
     * SQL文を返します。
     *
     * @return SQL文
     */
    public String getSQL();

    /**
     *
     * SQL文を発行するにあたって必要なバインドパラメータを返却します。
     *
     *
     * @return バインドパラメータ
     */
    public List<Object> getParameters();

}
