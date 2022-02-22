package jp.co.keyaki.cleave.fw.service;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * デフォルト実装のリクエストクラス.
 *
 * @author ytakahashi
 *
 * @param <S> レスポンス
 */
public abstract class DefaultRequestMessage<S extends DefaultResponseMessage<?>> implements RequestMessage<S> {

	/**
	 * デフォルトコンストラクタ.
	 */
	public DefaultRequestMessage() {
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

	/**
	 * リクエストに対応するレスポンスオブジェクト生成メソッド.
	 *
	 * @return レスポンス
	 */
	public abstract S createResponse();

}
