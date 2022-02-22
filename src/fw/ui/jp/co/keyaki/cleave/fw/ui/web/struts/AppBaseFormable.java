package jp.co.keyaki.cleave.fw.ui.web.struts;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.keyaki.cleave.fw.core.security.UserInfo;

public interface AppBaseFormable {

	/**
	 * コンテンツが未変更状態.
	 */
	public static final boolean CONTENTS_NO_CHANGE = false;
	/**
	 * コンテンツが変更済み状態.
	 */
	public static final boolean CONTENTS_CHANGED = true;
	/**
	 * リクエスト未送信状態.
	 */
	public static final boolean REQUEST_NO_SUBMIT = false;
	/**
	 * リクエスト送信済み状態.
	 */
	public static final boolean REQUEST_SUBMITED = true;
	/**
	 * リクエスト送信ボタン値初期値.
	 */
	public static final String REQUEST_BUTTON_INIT_VALUE = "";

	/**
	 * ログインユーザ情報を返します.
	 *
	 * @return
	 */
	public UserInfo getLoginUserInfo();

	/**
	 * 操作者情報を返します.
	 *
	 * @return
	 */
	public UserInfo getOperationUserInfo();

	/**
	 * ログインしているかを返します.
	 *
	 * @return true:ログイン済/false:未ログイン
	 */
	public boolean isLogin();

	/**
	 * 代理操作中であるか返します.
	 *
	 * @return true:代理操作中/false:本人操作中
	 */
	public boolean isProxyOperation();

	/**
	 * コンテンツが変更済みかを返します.
	 *
	 * @return true:変更済み/false:未変更
	 * @see AppBaseFormable#CONTENTS_NO_CHANGE
	 * @see AppBaseFormable#CONTENTS_CHANGED
	 */
	public boolean isContentsChanged();

	/**
	 * コンテンツが変更済みかを設定します.
	 *
	 * @param isContentsChanged
	 *            true:変更済み/false:未変更
	 * @see AppBaseFormable#CONTENTS_NO_CHANGE
	 * @see AppBaseFormable#CONTENTS_CHANGED
	 */
	public void setContentsChanged(boolean isContentsChanged);

	/**
	 * リクエストが送信済みがを返します.
	 *
	 * @return true:送信済み/false:未送信
	 * @see AppBaseFormable#REQUEST_NO_SUBMIT
	 * @see AppBaseFormable#REQUEST_SUBMITED
	 */
	public boolean isRequestSubmited();

	/**
	 * リクエストが送信済みがを返します.
	 *
	 * @param isRequestSubmited
	 *            true:送信済み/false:未送信
	 * @see AppBaseFormable#REQUEST_NO_SUBMIT
	 * @see AppBaseFormable#REQUEST_SUBMITED
	 */
	public void setRequestSubmited(boolean isRequestSubmited);

	/**
	 * リクエスト送信ボタン値を返します.
	 *
	 * @return リクエスト送信ボタン値
	 */
	public String getRequestButtonValue();

	/**
	 * リクエスト送信ボタン値を設定します.
	 *
	 * @param requestButtonValue
	 *            リクエスト送信ボタン値
	 */
	public void setRequestButtonValue(String requestButtonValue);

	public class AppActionErrors extends ActionErrors {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void add(String property, ActionMessage message) {
			super.add(property, message);
		}
	}

}
