package jp.co.keyaki.cleave.fw.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.PermissionException;
import jp.co.keyaki.cleave.fw.core.security.PermissionInfo;

/**
 * サービス実行に必要な権限チェックを実施する機能を有した基底サービスクラス.
 *
 * @author ytakahashi
 *
 * @param <S> レスポンス
 * @param <Q> リクエスト
 */
public abstract class AbstractService<S extends ResponseMessage<?>, Q extends RequestMessage<S>> implements
		Service<S, Q> {

	/**
	 * ロガー.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractService.class);

	/**
	 * サービス実行時権限チェックしサービスを実行します.
	 *
	 * @param requestMessage リクエスト
	 * @return レスポンス
	 */
	public S execute(Q requestMessage) {
		checkPermission();
		try {
			return doExecute(requestMessage);
		}catch (Exception e){
			S responseMessage = requestMessage.createResponse();
			responseMessage.handleThrowable(e);
			if (LOG.isErrorEnabled()) {
				LOG.error("Service#execute handle error", e);
			}
			return responseMessage;
		}

	}

	/**
	 * サービス実行時権限チェックを実施します.
	 *
	 * @throws PermissionException 権限不足の場合
	 */
	protected void checkPermission() throws PermissionException {
		PermissionInfo executablePermission = getExecutablePermission();
		if (LOG.isDebugEnabled()) {
			LOG.debug("executablePermission=" + executablePermission);
		}
		ActionContext.checkPermission(executablePermission);
	}

	/**
	 * サブクラス実装サービス実行メソッド.
	 *
	 * @param requestMessage リクエスト
	 * @return レスポンス
	 * @throws Exception
	 */
	protected abstract S doExecute(Q requestMessage) throws Exception;

}
