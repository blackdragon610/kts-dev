package jp.co.keyaki.cleave.fw.service;

/**
 * サービス実行におけるリクエストインターフェース.
 *
 * @author ytakahashi
 *
 * @param <S> レスポンス
 */
public interface RequestMessage<S extends ResponseMessage<?>> {

	/**
	 * リクエストに対応するレスポンスオブジェクト生成メソッド.
	 *
	 * @return レスポンス
	 */
	S createResponse();

}

