package jp.co.kts.service.common;

import java.util.ArrayList;
import java.util.List;

import jp.co.keyaki.cleave.fw.core.ErrorMessage;
/**
 * エラーメッセージを構築するクラス
 *
 * @author ytakahashi
 *
 * @param <O>
 */
public class Result<O> {

    /** SQL結果 */
    private O resultObject = null;

    /** エラーメッセージ */
    private List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

    /**
     * コンストラクタ
     *
     */
    public Result() {

    }

    /**
     * 成功判定
     *
     * @return true:成功／false:失敗
     */
    public boolean isSuccess() {
        return errorMessages.isEmpty();
    }

    /**
     * エラーメッセージ個数を取得する。
     *
     * @return エラーメッセージ個数
     */
    public int countMessages() {
        return errorMessages.size();
    }

    /**
     * SQL結果の取得する。
     *
     * @return SQL結果
     */
    public O getResultObject() {
        return resultObject;
    }

    /**
     * SQL結果を設定する。
     *
     * @param resultObject SQL結果
     */
    public void setResultObject(O resultObject) {
        this.resultObject = resultObject;
    }

    /**
     * メッセージＩＤを追加する
     * @param messageId
     * @return
     */
    public ErrorMessage addErrorMessage(String messageId) {
        ErrorMessage message = new ErrorMessage(messageId);
        addErrorMessage(message);
        return message;
    }

    /**
     * メッセージＩＤを追加する（動的にメッセージを変更）
     *
     * @param messageId
     * @return
     */
    public ErrorMessage addErrorMessage(String messageId,
            String... replaseWords) {
        ErrorMessage message = new ErrorMessage(messageId, replaseWords);
        addErrorMessage(message);
        return message;
    }

    /**
     * エラーメッセージを追加する
     * @param message
     */
    public void addErrorMessage(ErrorMessage message) {
        errorMessages.add(message);
    }

    /**
     * エラーメッセージを追加する（List用）
     *
     * @param message
     */
    public void addErrorMessages(List<ErrorMessage> errorMessages) {
        errorMessages.addAll(errorMessages);
    }

    /**
     * エラーメッセージを取得する
     */
    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

}
