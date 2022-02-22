
package jp.co.keyaki.cleave.fw.core;

import java.io.Serializable;
import java.util.Arrays;

/**
 * メッセージを表すクラス。
 *
 * @author ytakahashi
 */
public class Message implements Serializable {

    /**
     * シリアルバージョン。
     */
    private static final long serialVersionUID = -2962138627747710580L;

    /**
     * 置換文字列なし用のempty配列。
     */
    private static final String[] EMPTY_REPLACE_WORDS = new String[] {};

    /**
     * メッセージID。
     */
    private String messageId;

    /**
     * 置換文字列。
     */
    private String[] replaseWords;

    /**
     * デフォルトコンストラクタ。
     *
     */
    protected Message() {
        replaseWords = EMPTY_REPLACE_WORDS;
    }

    /**
     * コンストラクタ。
     *
     * @param messageId
     *            メッセージID
     */
    public Message(String messageId) {
        this();
        setMessageId(messageId);
    }

    /**
     * コンストラクタ。
     *
     * @param messageId
     *            メッセージID
     * @param replaseWords
     *            置換文字列。
     */
    public Message(String messageId, String... replaseWords) {
        this(messageId);
        setReplaseWords(replaseWords);
    }

    /**
     * メッセージIDを返します。
     *
     * @return メッセージID
     */
    public String getMessageId() {
        return this.messageId;
    }

    /**
     * メッセージIDを設定します。
     *
     * @param messageId
     *            メッセージID
     */
    protected void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 置換文字列を返します。
     *
     * @return 置換文字列
     */
    public String[] getReplaseWords() {
        return this.replaseWords.clone();
    }

    /**
     * 置換文字列を設定します。
     *
     * @param replaseWords
     *            置換文字列
     */
    protected void setReplaseWords(String... replaseWords) {
        this.replaseWords = replaseWords;
    }

    /**
     * メッセージオブジェクトの文字列表現を返します。
     *
     * @return 文字列表現
     */
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("messageId=").append(messageId);
        toString.append(",replaseWords=").append(Arrays.toString(replaseWords));
        return toString.toString();
    }
}
