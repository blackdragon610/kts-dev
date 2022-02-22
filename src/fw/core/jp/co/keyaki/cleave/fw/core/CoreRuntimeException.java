package jp.co.keyaki.cleave.fw.core;

/**
 * ランタイム例外規定クラス.
 *
 * @author ytakahashi
 *
 */
public class CoreRuntimeException extends RuntimeException {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * メッセージオブジェクト.
	 */
	private MessageDefine messageDefine;

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 */
	public CoreRuntimeException(MessageDefine messageDefine) {
		super();
		setErrorDefine(messageDefine);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param message 付加メッセージ
	 */
	public CoreRuntimeException(MessageDefine messageDefine, String message) {
		super(message);
		setErrorDefine(messageDefine);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param cause 起因例外
	 */
	public CoreRuntimeException(MessageDefine messageDefine, Throwable cause) {
		super(cause);
		setErrorDefine(messageDefine);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @param message 付加メッセージ
	 * @param cause 起因例外
	 */
	public CoreRuntimeException(MessageDefine messageDefine, String message, Throwable cause) {
		super(message, cause);
		setErrorDefine(messageDefine);
	}

	/**
	 * メッセージオブジェクトを設定します.
	 *
	 * @param messageDefine メッセージオブジェクト
	 * @exception NullPointerException メッセージオブジェクトがnullの場合
	 */
	protected void setErrorDefine(MessageDefine messageDefine) {
		if (messageDefine == null) {
			throw new NullPointerException("エラー識別子は必須です。");
		}
		this.messageDefine = messageDefine;
	}

	/**
	 * メッセージオブジェクトを返します.
	 *
	 * @return メッセージオブジェクト
	 */
	public MessageDefine getErrorDefine() {
		return messageDefine;
	}

	/**
	 * オブジェクトの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	@Override
	public String getMessage() {
		return messageDefine.toString() + " " + super.getMessage();
	}

}
