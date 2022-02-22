package jp.co.keyaki.cleave.fw.ui.web;

import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;
import jp.co.keyaki.cleave.fw.core.MessageDefine;

public class WebRuntimeException extends CoreRuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public WebRuntimeException(MessageDefine messageDefine, String message, Throwable cause) {
		super(messageDefine, message, cause);
	}

	public WebRuntimeException(MessageDefine messageDefine, String message) {
		super(messageDefine, message);
	}

	public WebRuntimeException(MessageDefine messageDefine, Throwable cause) {
		super(messageDefine, cause);
	}

	public WebRuntimeException(MessageDefine messageDefine) {
		super(messageDefine);
	}

}
