package jp.co.keyaki.cleave.fw.dao;

import jp.co.keyaki.cleave.fw.core.CoreException;
import jp.co.keyaki.cleave.fw.core.MessageDefine;

/**
 * DAO層例外クラス.
 *
 * @author ytakahashi
 */
public class DaoException extends CoreException {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 */
	public DaoException(MessageDefine messageDefine) {
		super(messageDefine);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param message 付加メッセージ
	 */
	public DaoException(MessageDefine messageDefine, String message) {
		super(messageDefine, message);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param cause 起因例外
	 */
	public DaoException(MessageDefine messageDefine, Throwable cause) {
		super(messageDefine, cause);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param message 付加メッセージ
	 * @param cause 起因例外
	 */
	public DaoException(MessageDefine messageDefine, String message,
			Throwable cause) {
		super(messageDefine, message, cause);
	}

}
