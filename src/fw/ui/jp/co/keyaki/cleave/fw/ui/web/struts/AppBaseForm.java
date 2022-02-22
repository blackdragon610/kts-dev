package jp.co.keyaki.cleave.fw.ui.web.struts;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.ValidatorForm;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;

public abstract class AppBaseForm extends ValidatorForm implements AppBaseFormable {

	/**
	 * シリアルバージョン.
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

	/**
	 * フォームの状態をリセットします.
	 *
	 * <p>
	 * 継承を考慮し、フォームをリセットする順は下記の通りとする.
	 * <ol>
	 * <li>サブクラスのリセットメソッド
	 * {@link AppBaseForm#doReset(AppActionMapping, HttpServletRequest)}</li>
	 * <li>自インスタンスのプロパティ初期化</li>
	 * <li>スーパークラスのリセットメソッド
	 * {@link ValidatorForm#reset(ActionMapping, HttpServletRequest)}</li>
	 * </ol>
	 * </p>
	 *
	 * <p>
	 * 自インスタンスのプロパティを初期化する際、下記のルールで実施する.<br>
	 * <ul>
	 * <li>{@link AppBaseForm#isRequestSubmited} = {@link AppBaseFormable#REQUEST_NO_SUBMIT}
	 * </li>
	 * <li>{@link AppBaseForm#requestButtonValue} =
	 * {@link AppBaseFormable#REQUEST_BUTTON_INIT_VALUE}</li>
	 * <li>{@link AppActionMapping#isContentsInitAction()} の場合</li>
	 * <ul>
	 * <li>{@link AppBaseForm#isContentsChanged} =
	 * {@link AppBaseFormable#CONTENTS_NO_CHANGE}</li>
	 * </ul>
	 * </ul>
	 * </p>
	 *
	 * @param mapping
	 *            アクションマッピング情報
	 * @param request
	 *            リクエスト
	 * @see AppBaseForm#doReset(AppActionMapping, HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("reset(" + mapping + ", " + request.getParameterMap() + ")");
		}
		AppActionMapping appMapping = AppActionMapping.class.cast(mapping);
		doReset(appMapping, request);
		if (appMapping.isContentsInitAction()) {
			setContentsChanged(AppBaseFormable.CONTENTS_NO_CHANGE);
		}
		setRequestSubmited(AppBaseFormable.REQUEST_NO_SUBMIT);
		setRequestButtonValue(AppBaseFormable.REQUEST_BUTTON_INIT_VALUE);
		super.reset(mapping, request);
	}

	/**
	 * このオブジェクトの文字列表現を返します.
	 *
	 * @return オブジェクトの文字列表現
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * サブクラス固有のプロパティを初期化するためのメソッド.
	 *
	 * @param appMapping
	 *            アクションマッピング情報
	 * @param request
	 *            リクエスト
	 */
	protected abstract void doReset(AppActionMapping appMapping, HttpServletRequest request);

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ServletContext application = getServlet().getServletContext();
		ActionErrors errors = new AppActionErrors();
		String validationKey = getValidationKey(mapping, request);

		Validator validator = Resources.initValidator(validationKey, this, application, request, errors, this.page);
		try {
			this.validatorResults = validator.validate();
		} catch (ValidatorException e) {
		}

		return errors;
	}

}
