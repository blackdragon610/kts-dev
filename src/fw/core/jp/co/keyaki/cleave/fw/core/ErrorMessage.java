
package jp.co.keyaki.cleave.fw.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * エラーメッセージを表すクラス。
 *
 * @author ytakahashi
 */
public class ErrorMessage extends Message {

    /**
     * シリアルバージョン。
     */
    private static final long serialVersionUID = 4692232363226786880L;

    /**
     * エラーとなった対象のプロパティ名
     */
    private List<String> propertyNames = new ArrayList<String>();

    /**
     * デフォルトコンストラクタ。
     *
     */
    protected ErrorMessage() {
        super();
    }

    /**
     * コンストラクタ。
     *
     * @param messageId
     *            メッセージID
     */
    public ErrorMessage(String messageId) {
        super(messageId);
    }

    /**
     * コンストラクタ。
     *
     * @param messageId
     *            メッセージID
     * @param replaseWords
     *            置換文字列
     */
    public ErrorMessage(String messageId, String... replaseWords) {
        super(messageId, replaseWords);
    }

    /**
     * エラーとなった対象のプロパティ名を返します。
     *
     * @return エラーとなった対象のプロパティ名
     */
    public List<String> getPropertyNames() {
        return Collections.unmodifiableList(propertyNames);
    }

    /**
     * エラーとなった対象のプロパティ名を追加します。
     *
     * @param propertyName
     *            エラーとなった対象のプロパティ名
     * @return エラーメッセージのインスタンス自身
     */
    public ErrorMessage addPropertyName(String propertyName) {
        propertyNames.add(propertyName);
        return this;
    }

    /**
     * エラーメッセージオブジェクトの文字列表現を返します。
     *
     * @return 文字列表現
     */
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append(super.toString());
        toString.append(",propertyNames=").append(propertyNames.toString());
        return toString.toString();
    }
}
