package jp.co.keyaki.cleave.fw.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import jp.co.keyaki.cleave.fw.core.MessageDefine;

/**
 * デフォルト実装のレスポンスクラス.
 *
 * @author ytakahashi
 *
 * @param <R> 実行結果オブジェクト
 */
public class DefaultResponseMessage<R> implements ResponseMessage<R> {

	/**
	 * サービスからクライアントへのエラーメッセージ.
	 */
	private List<MessageDefine> errorMesssages = new ArrayList<MessageDefine>();

	/**
	 * サービス実行中にハンドルした例外.
	 */
	private Throwable handleThrowable;

	/**
	 * 実行結果オブジェクト.
	 */
	private R resultObject;

	/**
	 * デフォルトコンストラクタ.
	 */
	protected DefaultResponseMessage() {

	}

	/**
	 * サービス実行結果.
	 *
	 * <p>
	 * 当メソッドは、サービスからクライアントへのエラーメッセージが存在しない場合は成功と判断します.
	 * </p>
	 *
	 * @return true:成功/false:失敗
	 */
	public boolean isSuccess() {
		return errorMesssages.isEmpty();
	}

	/**
	 * エラーメッセージを追加設定します.
	 *
	 * @param serviceMessageDefine 追加するエラーメッセージ
	 */
	public void addErrorMessage(MessageDefine serviceMessageDefine) {
		errorMesssages.add(serviceMessageDefine);
	}

	/**
	 * 設定されているエラーメッセージを返します.
	 *
	 * @return エラーメッセージ
	 */
	public List<MessageDefine> getErrorMessages() {
		return errorMesssages;
	}

	/**
	 * サービス実行時に補足した例外を保持します.
	 *
	 * @param t 補足した例外
	 */
	public void handleThrowable(Throwable t) {

		handleThrowable = t;
		addErrorMessage(ServiceMessageDefine.E000001);
	}

	/**
	 * サービス実行時に補足した例外を返します.
	 *
	 * @return 補足した例外
	 */
	public Throwable getHandledThrowable() {

		return handleThrowable;

	}

	/**
	 * 実行結果オブジェクトを返します.
	 *
	 * @return 実行結果オブジェクト
	 */
	public R getResultObject() {
		return resultObject;
	}

	/**
	 * 実行結果オブジェクトを設定します.
	 *
	 * @param resultObject 実行結果オブジェクト
	 */
	public void setResultObject(R resultObject) {
		this.resultObject = resultObject;
	}

	/**
	 * このインスタンスの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
