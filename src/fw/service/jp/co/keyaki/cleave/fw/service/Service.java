package jp.co.keyaki.cleave.fw.service;

import jp.co.keyaki.cleave.fw.core.security.PermissionInfo;

/**
 * サービスを表すインターフェース.
 *
 * @author ytakahashi
 *
 * @param <S> レスポンス
 * @param <Q> リクエスト
 */
public interface Service<S extends ResponseMessage<?>, Q extends RequestMessage<S>> {

	/**
	 * サービスを実行する為に必要な権限を返します.
	 *
	 * @return 必須権限.
	 */
	PermissionInfo getExecutablePermission();

	/**
	 * サービスを実行します.
	 *
	 * @param requestMessage リクエスト
	 * @return レスポンス
	 */
	S execute(Q requestMessage);

}
