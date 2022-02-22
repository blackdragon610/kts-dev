package jp.co.keyaki.cleave.fw.service;

import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;
import jp.co.keyaki.cleave.fw.core.MessageDefine;

/**
 * サービス層ランタイム例外クラス.
 *
 *
 * @author ytakahashi
 */
public class ServiceRuntimeException extends CoreRuntimeException {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 */
	public ServiceRuntimeException(MessageDefine messageDefine) {
		super(messageDefine);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param message 付加メッセージ
	 */
	public ServiceRuntimeException(MessageDefine messageDefine, String message) {
		super(messageDefine, message);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param cause 起因例外
	 */
	public ServiceRuntimeException(MessageDefine messageDefine, Throwable cause) {
		super(messageDefine, cause);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param message 付加メッセージ
	 * @param cause 起因例外
	 */
	public ServiceRuntimeException(MessageDefine messageDefine, String message, Throwable cause) {
		super(messageDefine, message, cause);
	}

}
