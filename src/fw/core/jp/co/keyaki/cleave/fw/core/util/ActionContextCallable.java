package jp.co.keyaki.cleave.fw.core.util;

import java.util.concurrent.Callable;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;

/**
 * アクションコンテキスト属性を設定するための非同期処理インターフェース.
 *
 * @author ytakahashi
 *
 * @param <V> 非同期処理の戻り値
 */
public abstract class ActionContextCallable<V> implements Callable<V> {

	/**
	 * 起動元のログインユーザ情報.
	 */
	private UserInfo loginUserInfo;

	/**
	 * 起動元の操作者ユーザ情報.
	 */
	private UserInfo operationUserInfo;

	/**
	 * 非同期処理開始時のトランザクション状態.
	 */
	private boolean isTransactionRollbackOnly;

	/**
	 * コンストラクタ.
	 * <p>
	 * このコンストラクタは起動元のログインユーザ情報・操作者ユーザ情報を引き継いぎ
	 * 非同期処理が実行される状態になる.
	 * トランザクションは起動元の属性は引き継ぎません.
	 * ただし非同期処理開始前に、{@link #setTransactionRollbackOnly()} を呼び出しておくことで、
	 * 非同期処理内では、ロールバックが確定した状態でのトランザクション処理が実施されます.
	 * </p>
	 */
	public ActionContextCallable() {
		this(ActionContext.getLoginUserInfo(), ActionContext.getOperationUserInfo());
	}

	/**
	 * コンストラクタ.
	 * <p>
	 * このコンストラクタは指定したログインユーザ情報にて非同期処理が実行される状態になる.
	 * トランザクションは起動元の属性は引き継ぎません.
	 * ただし非同期処理開始前に、{@link #setTransactionRollbackOnly()} を呼び出しておくことで、
	 * 非同期処理内では、ロールバックが確定した状態でのトランザクション処理が実施されます.
	 * </p>
	 */
	public ActionContextCallable(UserInfo loginUserInfo) {
		this(loginUserInfo, null);
	}

	/**
	 * コンストラクタ.
	 * <p>
	 * このコンストラクタは指定したログインユーザ情報・操作者ユーザ情報にて非同期処理が実行される状態になる.
	 * トランザクションは起動元の属性は引き継ぎません.
	 * トランザクションは起動元の属性は引き継ぎません.
	 * ただし非同期処理開始前に、{@link #setTransactionRollbackOnly()} を呼び出しておくことで、
	 * 非同期処理内では、ロールバックが確定した状態でのトランザクション処理が実施されます.
	 * </p>
	 */
	public ActionContextCallable(UserInfo loginUserInfo, UserInfo operationUserInfo) {
		setLoginUserInfo(loginUserInfo);
		setOperationUserInfo(operationUserInfo);
	}

	/**
	 * 非同期処理時におけるログインユーザ情報を返します.
	 *
	 * @return 非同期処理時におけるログインユーザ
	 */
	public UserInfo getLoginUserInfo() {
		return loginUserInfo;
	}

	/**
	 * 非同期処理時におけるログインユーザ情報を設定します.
	 *
	 * @param loginUserInfo 非同期処理時におけるログインユーザ情報
	 */
	public void setLoginUserInfo(UserInfo loginUserInfo) {
		this.loginUserInfo = loginUserInfo;
	}

	/**
	 * 非同期処理時における操作者ユーザ情報を返します.
	 *
	 * @return 非同期処理時における操作者ユーザ情報
	 */
	public UserInfo getOperationUserInfo() {
		return operationUserInfo;
	}

	/**
	 * 非同期処理時における操作者ユーザ情報を設定します.
	 *
	 * @param operationUserInfo 非同期処理時における操作者ユーザ情報
	 */
	public void setOperationUserInfo(UserInfo operationUserInfo) {
		this.operationUserInfo = operationUserInfo;
	}

	/**
	 * 非同期処理時におけるトランザクション状態を返します.
	 *
	 * @return true:非同期処理後ロールバック確定/false:非同期処理後にコミット実施
	 */
	public boolean isTransactionRollbackOnly() {
		return isTransactionRollbackOnly;
	}

	/**
	 *非同期処理時におけるトランザクション状態をロールバック確定に設定します.
	 *
	 */
	public void setTransactionRollbackOnly() {
		isTransactionRollbackOnly = true;
	}

	/**
	 * 非同期処理開始前後に{@link ActionContext}の操作を実施します.
	 *
	 * <p>
	 * このクラスのサブクラスは、非同期処理自体は{@link ActionContextCallable#doCall()}を実装すること.
	 * </p>
	 *
	 * @return 非同期処理の結果
	 * @throws Exception 非同期処理時に例外が発生した場場合
	 */
	public V call() throws Exception {
		try {
			ActionContext.setLoginUserInfo(getLoginUserInfo());
			ActionContext.setOperationUserInfo(getOperationUserInfo());
			if (isTransactionRollbackOnly()) {
				ActionContext.setTransactionRollbackOnly();
			}
			return doCall();
		} finally {
			ActionContext.reset();
		}
	}

	/**
	 * 固有非同期処理の実装メソッド.
	 *
	 * @return 非同期処理の結果
	 * @throws Exception 非同期処理時に例外が発生した場場合
	 */
	protected abstract V doCall() throws Exception;
}
