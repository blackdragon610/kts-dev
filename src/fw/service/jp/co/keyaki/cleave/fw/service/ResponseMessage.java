package jp.co.keyaki.cleave.fw.service;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.MessageDefine;

/**
 * サービス実行におけるレスポンスインターフェース.
 *
 * @author ytakahashi
 *
 * @param <R> 実行結果オブジェクト
 */
public interface ResponseMessage<R> {

	/**
	 * サービス実行が成功したかを返します.
	 *
	 * @return true:成功/false:失敗
	 */
	boolean isSuccess();

	/**
	 * エラーメッセージを追加設定します.
	 *
	 * @param serviceMessageDefine 追加するエラーメッセージ
	 */
	void addErrorMessage(MessageDefine serviceMessageDefine);

	/**
	 * 設定されているエラーメッセージを返します.
	 *
	 * @return エラーメッセージ
	 */
	List<MessageDefine> getErrorMessages();

	/**
	 * サービス実行時に補足した例外を保持します.
	 *
	 * @param t 補足した例外
	 */
	void handleThrowable(Throwable t);

	/**
	 * サービス実行時に補足した例外を返します.
	 *
	 * @return 補足した例外
	 */
	Throwable getHandledThrowable();

	/**
	 * 実行結果オブジェクトを返します.
	 *
	 * @return 実行結果オブジェクト
	 */
	R getResultObject();

}
