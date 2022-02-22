package jp.co.keyaki.cleave.fw.ui.web.struts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.core.MessageDefine;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.core.util.KeyThreadLocker;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.service.RequestMessage;
import jp.co.keyaki.cleave.fw.service.ResponseMessage;
import jp.co.keyaki.cleave.fw.service.ServiceExecutor;
import jp.co.kts.dao.common.TransactionDAO;

public abstract class AppBaseAction extends Action {

	/**
	 * 同一セッションの並行性を制御するためのロックオブジェクト。
	 */
	private final static KeyThreadLocker<String> SESSION_LOCK_MONITOR = new KeyThreadLocker<String>();

	private static final Log LOG = LogFactory.getLog(AppBaseAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("action=[" + mapping.getPath() + "], parameters=[" + request.getParameterMap() + "]");
		}

		// 以下共通チェック項目
		AppActionMapping appMapping = AppActionMapping.class.cast(mapping);

		String sessionId = request.getSession().getId().intern();
		KeyThreadLocker<String> sessionLockMonitor = getSessionLockMonitorObject(appMapping, form, request);
		try {
			sessionLockMonitor.lock(sessionId);
			// セキュアアクションの判定
			if (!appMapping.isNonSecureAction()) {
				UserInfo loginUser = ActionContext.getLoginUserInfo();
				// ログイン状態の判定
				if (loginUser == null) {
					return mapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_NOLOGIN_ERROR);
				}
				// ログインユーザーが他者に更新されていないか判定
				// return
				// mapping.findForward(ForwardConst.GROBAL_CHANGED_ERROR);

			}
			// トークンチェック
			TokenType tokenType = TokenTypeFactory.getTokenType(appMapping.getTokenType());
			if (!tokenType.validate(mapping, form, request, response)) {
				return mapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_TOKEN_ERROR);
			}

			try {
				// サブクラスの固有処理の設定
				return doExecute(appMapping, AppBaseForm.class.cast(form), request, response);
			} catch (Throwable t) {
				LOG.fatal("action=[" + mapping.getPath() + "], parameters=[" + request.getParameterMap() + "]", t);

				saveStackTraceInfo(request, t);
				return mapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
			}

		} finally {
			sessionLockMonitor.unlock(sessionId);
		}
	}

	protected KeyThreadLocker<String> getSessionLockMonitorObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request) {
		return SESSION_LOCK_MONITOR;
	}

	protected <S extends ResponseMessage<?>, Q extends RequestMessage<S>> S executeService(Q requestMessage) {
		return ServiceExecutor.execute(requestMessage);
	}

	protected <S extends ResponseMessage<?>, Q extends RequestMessage<S>> Future<S> invokeService(final Q requestMessage) {
		return ServiceExecutor.invoke(requestMessage);
	}

	protected void saveStackTraceInfo(HttpServletRequest request, Throwable handledThrowable) {
		List<Throwable> throwables = new ArrayList<>();
		while (handledThrowable != null) {
			throwables.add(handledThrowable);
			handledThrowable = handledThrowable.getCause();
		}
		List<List<String>> stackTraceList = new ArrayList<>();
		for (Throwable throwable : throwables) {
			List<String> stackTrace = new ArrayList<>();
			stackTrace.add(throwable.toString());
			for (StackTraceElement elem : throwable.getStackTrace()) {
				stackTrace.add(elem.toString());
			}
			stackTraceList.add(stackTrace);
		}
		request.setAttribute("stackTraceInfo", stackTraceList);
	}

	protected void saveErrors(HttpServletRequest request, ResponseMessage<?> responseMessage) {
		Throwable handledThrowable = responseMessage.getHandledThrowable();
		if (handledThrowable != null) {
			saveStackTraceInfo(request, handledThrowable);
		}
		ActionErrors errors = new ActionErrors();
		for (MessageDefine errorMessage : responseMessage.getErrorMessages()) {
			errors.add("service_error", new ActionMessage(errorMessage.getCode(), errorMessage.getArgs()));
		}
		saveErrors(request, errors);
		ActionContext.setTransactionRollbackOnly();
	}

	/**
     * サービスからのエラーメッセージをJSPへ転送します。
     *
     * @param request
     * @param messages
     */
    protected void saveErrorMessages(HttpServletRequest request,
            List<ErrorMessage> messages) {
        ActionMessages actionMessages = new ActionMessages();
        for (ErrorMessage message : messages) {
            ActionMessage actionMessage = new ActionMessage(message.getMessageId(), message.getReplaseWords());
            actionMessages.add(Globals.ERROR_KEY, actionMessage);
            for (String propertyName : message.getPropertyNames()) {
                actionMessages.add(propertyName, actionMessage);
            }
        }
        saveErrors(request, actionMessages);
    }

	protected Cookie getCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null){
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

	protected abstract ActionForward doExecute(AppActionMapping appMapping, AppBaseForm appForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 2014/03/03　追加　伊東敦史 start
	 */
	/**
	 * postgresqlのBEGIN関数を実行します
	 * @throws DaoException
	 */
	protected void begin () throws DaoException {
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.begin();
	}

	/**
	 * postgresqlのROLLBACK関数を実行します
	 * @throws DaoException
	 */
	protected void rollback () throws DaoException {
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.rollback();
	}

	/**
	 * postgresqlのCOMMIT関数を実行します
	 * @throws DaoException
	 */
	protected void commit () throws DaoException {
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.commit();
	}
	/**
	 * 2014/03/03　追加　伊東敦史 end
	 */
}
