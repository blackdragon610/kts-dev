package jp.co.keyaki.cleave.fw.ui.web.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.validator.DynaValidatorForm;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;

public class AppBaseDynaForm extends DynaValidatorForm implements AppBaseFormable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ログ出力クラス.
	 */
	private static final Log LOG = LogFactory.getLog(AppBaseForm.class);

	/**
	 * コンテンツ更新済みフラグ.
	 */
	private boolean isContentsChanged = AppBaseFormable.CONTENTS_NO_CHANGE;

	/**
	 * フォーム送信済みフラグ.
	 */
	private boolean isRequestSubmited = AppBaseFormable.REQUEST_NO_SUBMIT;

	/**
	 * 送信ボタン値.
	 */
	private String requestButtonValue = AppBaseFormable.REQUEST_BUTTON_INIT_VALUE;

	/**
	 * ログインユーザ情報を返します.
	 *
	 * @return
	 */
	public UserInfo getLoginUserInfo() {
		return ActionContext.getLoginUserInfo();
	}

	/**
	 * 操作者情報を返します.
	 *
	 * @return
	 */
	public UserInfo getOperationUserInfo() {
		return ActionContext.getOperationUserInfo();
	}

	/**
	 * ログインしているかを返します.
	 *
	 * @return true:ログイン済/false:未ログイン
	 */
	public boolean isLogin() {
		return ActionContext.hasLoginUserInfo();
	}

	/**
	 * 代理操作中であるか返します.
	 *
	 * @return true:代理操作中/false:本人操作中
	 */
	public boolean isProxyOperation() {
		return ActionContext.isProxyOperation();
	}

	/**
	 * コンテンツが変更済みかを返します.
	 *
	 * @return true:変更済み/false:未変更
	 * @see AppBaseFormable#CONTENTS_NO_CHANGE
	 * @see AppBaseFormable#CONTENTS_CHANGED
	 */
	public boolean isContentsChanged() {
		return isContentsChanged;
	}

	/**
	 * コンテンツが変更済みかを設定します.
	 *
	 * @param isContentsChanged
	 *            true:変更済み/false:未変更
	 * @see AppBaseFormable#CONTENTS_NO_CHANGE
	 * @see AppBaseFormable#CONTENTS_CHANGED
	 */
	public void setContentsChanged(boolean isContentsChanged) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("setContentsChanged(" + isContentsChanged + ")");
		}
		this.isContentsChanged = isContentsChanged;
	}

	/**
	 * リクエストが送信済みがを返します.
	 *
	 * @return true:送信済み/false:未送信
	 * @see AppBaseFormable#REQUEST_NO_SUBMIT
	 * @see AppBaseFormable#REQUEST_SUBMITED
	 */
	public boolean isRequestSubmited() {
		return isRequestSubmited;
	}

	/**
	 * リクエストが送信済みがを返します.
	 *
	 * @param isRequestSubmited
	 *            true:送信済み/false:未送信
	 * @see AppBaseFormable#REQUEST_NO_SUBMIT
	 * @see AppBaseFormable#REQUEST_SUBMITED
	 */
	public void setRequestSubmited(boolean isRequestSubmited) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("setRequestSubmited(" + isRequestSubmited + ")");
		}
		this.isRequestSubmited = isRequestSubmited;
	}

	/**
	 * リクエスト送信ボタン値を返します.
	 *
	 * @return リクエスト送信ボタン値
	 */
	public String getRequestButtonValue() {
		return requestButtonValue;
	}

	/**
	 * リクエスト送信ボタン値を設定します.
	 *
	 * @param requestButtonValue
	 *            リクエスト送信ボタン値
	 */
	public void setRequestButtonValue(String requestButtonValue) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("setRequestButtonValue(" + requestButtonValue + ")");
		}
		this.requestButtonValue = requestButtonValue;
	}

}
