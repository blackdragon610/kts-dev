package jp.co.keyaki.cleave.fw.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.co.keyaki.cleave.fw.core.security.AccessControl;
import jp.co.keyaki.cleave.fw.core.security.DefaultUserAccessControl;
import jp.co.keyaki.cleave.fw.core.security.PermissionException;
import jp.co.keyaki.cleave.fw.core.security.PermissionInfo;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;

/**
 * アクションコンテキスト.
 *
 * <p>
 * 処理開始から処理終了までのグローバルコンテキストを表現したクラス.
 *
 * </p>
 *
 * @author ytakahashi
 */
public class ActionContext {

	/**
	 * システム的に予約されている登録キープレフィックス.
	 */
	public static final String CONTEXT_KEY_RESERVE_PREFIX = ActionContext.class.getPackage().getName().concat(".");

	/**
	 * ログインユーザ登録キー.
	 */
	public static final String CONTEXT_KEY_LOGIN_USER = CONTEXT_KEY_RESERVE_PREFIX.concat("CONTEXT_KEY_LOGIN_USER");

	/**
	 * オペレーションユーザ登録キー.
	 */
	public static final String CONTEXT_KEY_OPERATION_USER = CONTEXT_KEY_RESERVE_PREFIX.concat("CONTEXT_KEY_OPERATION_USER");

	/**
	 * ユーザロケール登録キー.
	 */
	public static final String CONTEXT_KEY_USER_LOCALE = CONTEXT_KEY_RESERVE_PREFIX.concat("CONTEXT_KEY_USER_LOCALE");

	/**
	 * トランザクションロールバック登録キー.
	 */
	public static final String CONTEXT_KEY_TRANSACTION_ROLLBAK_ONLY = CONTEXT_KEY_RESERVE_PREFIX.concat("CONTEXT_KEY_TRANSACTION_ROLLBAK_ONLY");

	/**
	 * システム的に予約されたオブジェクトを格納するThreadLocal.
	 */
	private static final ThreadLocal<Map<String, Object>> SYSTEM_THREAD_LOCAL = new ThreadLocal<Map<String, Object>>() {

		/**
		 * ThreadLocal初期オブジェクト生成メソッド.
		 */
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}

	};

	/**
	 * クライアント(当クラス利用オブジェクト)が自由にオブジェクトを格納する事ができるThreadLocal.
	 */
	private static final ThreadLocal<Map<String, Object>> USER_THREAD_LOCAL = new ThreadLocal<Map<String, Object>>() {

		/**
		 * ThreadLocal初期オブジェクト生成メソッド.
		 */
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}

	};

	/**
	 * 登録キーがシステムで予約されているキーでないことを判定します.
	 *
	 * @param key
	 *            判定対象登録キー
	 * @return true:システムで予約されていない登録キー/false:システムで予約されている登録キー
	 * @see ActionContext#CONTEXT_KEY_RESERVE_PREFIX
	 */
	protected static boolean validateReservePrefixKey(String key) {
		return !key.startsWith(CONTEXT_KEY_RESERVE_PREFIX);
	}

	/**
	 * 登録キーから該当のThreadLocalに保持されているコンテキスト(Map)を返します.
	 *
	 * @param key
	 *            登録キー
	 * @return コンテキスト
	 */
	protected static Map<String, Object> getThreadLocalMap(String key) {
		ThreadLocal<Map<String, Object>> threadLocal = USER_THREAD_LOCAL;
		if (!validateReservePrefixKey(key)) {
			threadLocal = SYSTEM_THREAD_LOCAL;
		}
		return threadLocal.get();
	}

	/**
	 * キーで登録されているオブジェクトを返します.
	 *
	 * @param key
	 *            登録キー
	 * @return 登録されているオブジェクト(未登録時はnull)
	 */
	public static Object get(String key) {
		return getThreadLocalMap(key).get(key);
	}

	/**
	 * オブジェクトをコンテキストに登録します.
	 *
	 * <p>
	 * 登録オブジェクトが{@link ContextListener}を実装していた場合、 コンテキスト登録後に
	 * {@link ContextListener#handleAdded(String)}を呼び出します.
	 * </p>
	 *
	 * @param key
	 *            登録キー
	 * @param value
	 *            登録オブジェクト
	 */
	protected static void doRegist(String key, Object value) {
		getThreadLocalMap(key).put(key, value);
		if (value == null) {
			return;
		}
		if (ContextListener.class.isAssignableFrom(value.getClass())) {
			ContextListener listener = ContextListener.class.cast(value);
			listener.handleAdded(key);
		}
	}

	/**
	 * オブジェクトをコンテキストに登録します.
	 *
	 * @param key
	 *            登録キー
	 * @param value
	 *            登録オブジェクト
	 * @throws CoreRuntimeException
	 *             登録キーがシステム予約キー規則に一致した場合.
	 */
	public static void regist(String key, Object value) throws CoreRuntimeException {
		if (!validateReservePrefixKey(key)) {
			throw new CoreRuntimeException(CoreMessageDefine.E000001, "key=" + key);
		}
		doRegist(key, value);
	}

	/**
	 * コンテキストよりオブジェクトを削除します.
	 *
	 * <p>
	 * 登録オブジェクトが{@link ContextListener}を実装していた場合、 コンテキスト削除後に
	 * {@link ContextListener#handleRemoved(String)}を呼び出します.
	 * </p>
	 *
	 * @param key
	 *            登録キー
	 */
	public static void remove(String key) {
		Object value = get(key);
		getThreadLocalMap(key).remove(key);
		if (value == null) {
			return;
		}
		if (ContextListener.class.isAssignableFrom(value.getClass())) {
			ContextListener listener = ContextListener.class.cast(value);
			listener.handleRemoved(key);
		}
	}

	/**
	 * コンテキストをリセットします.
	 *
	 * <p>
	 * 登録済みオブジェクトを１つ１つコンテキストより削除します. そのため、登録オブジェクトが{@link ContextListener}
	 * を実装していた場合、 {@link ContextListener#handleRemoved(String)}が呼び出されます.
	 * </p>
	 *
	 */
	public static void reset() {
		List<String> allKey = new ArrayList<String>();
		allKey.addAll(USER_THREAD_LOCAL.get().keySet());
		allKey.addAll(SYSTEM_THREAD_LOCAL.get().keySet());
		for (String key : allKey) {
			remove(key);
		}
	}

	/**
	 * ログインユーザ情報を返します.
	 *
	 * @return ログインユーザ情報(設定されていない場合はnull)
	 */
	public static UserInfo getLoginUserInfo() {
		return UserInfo.class.cast(get(CONTEXT_KEY_LOGIN_USER));
	}

	/**
	 * ログインユーザ情報を設定します.
	 *
	 * @param loginUserInfo
	 *            ログインユーザ情報
	 */
	public static void setLoginUserInfo(UserInfo loginUserInfo) {
		doRegist(CONTEXT_KEY_LOGIN_USER, loginUserInfo);
	}

	/**
	 * ログインユーザ情報が設定されているか判定し返します.
	 *
	 * @return true:ログインユーザ情報が設定済み/false:ログインユーザ情報が未設定
	 */
	public static boolean hasLoginUserInfo() {
		return getLoginUserInfo() != null;
	}

	/**
	 * 操作者ユーザ情報を返します.
	 *
	 * <p>
	 * ログインユーザ情報とは別に操作者ユーザ情報が設定されている場合は操作者ユーザ情報を返します.
	 * 操作者ユーザ情報が別に設定されていない場合は、ログインユーザ情報を操作者情報として返します.
	 * </p>
	 *
	 * @return 操作者ユーザ情報
	 */
	public static UserInfo getOperationUserInfo() {
		UserInfo operationUser = UserInfo.class.cast(get(CONTEXT_KEY_OPERATION_USER));
		if (operationUser != null) {
			return operationUser;
		}
		return getLoginUserInfo();
	}

	/**
	 * 操作者ユーザ情報を設定します.
	 *
	 * @param operationUserInfo
	 *            操作者ユーザ情報
	 * @throws CoreRuntimeException
	 *             ログインユーザ情報が未設定の状態で操作者ユーザ情報を設定しようとした場合
	 */
	public static void setOperationUserInfo(UserInfo operationUserInfo) throws CoreRuntimeException {
		if (!hasLoginUserInfo()) {
			throw new CoreRuntimeException(CoreMessageDefine.E000002);
		}
		doRegist(CONTEXT_KEY_OPERATION_USER, operationUserInfo);
	}

	/**
	 * 代理操作実行中かを判定し返します.
	 *
	 * @return true:代理操作中/false:本人操作中またはログインユーザ情報が未設定
	 */
	public static boolean isProxyOperation() {
		if (!hasLoginUserInfo()) {
			return false;
		}
		return !getLoginUserInfo().equals(getOperationUserInfo());
	}

	/**
	 * ユーザのロケールを返します.
	 *
	 * @return ユーザロケール（未設定の場合は{@link Locale#JAPAN})
	 */
	public static Locale getUserLocale() {
		return getUserLocale(Locale.JAPAN);
	}

	/**
	 * ユーザのロケールを返します.
	 *
	 * @param defaultLocale ユーザロケールが未設定の場合に代用として利用するロケール
	 * @return ユーザロケール
	 */
	public static Locale getUserLocale(Locale defaultLocale) {
		Locale contextLocale = Locale.class.cast(get(CONTEXT_KEY_USER_LOCALE));
		if (contextLocale == null) {
			contextLocale = defaultLocale;
		}
		return contextLocale;
	}

	/**
	 * ユーザのロケールを設定します.
	 *
	 * @param userLocale ユーザロケール
	 */
	public static void setUserLocale(Locale userLocale) {
		doRegist(CONTEXT_KEY_USER_LOCALE, userLocale);
	}

	/**
	 * トランザクションを必ずロールバックするように設定します.
	 *
	 */
	public static void setTransactionRollbackOnly() {
		doRegist(CONTEXT_KEY_TRANSACTION_ROLLBAK_ONLY, Boolean.TRUE);
	}

	/**
	 * トランザクションを必ずロールバックするように設定されているか判定し返します.
	 *
	 * @return true:ロールバック確定/false:ロールバック未確定
	 */
	public static boolean isTransactionRollbackOnly() {
		return Boolean.TRUE.equals(get(CONTEXT_KEY_TRANSACTION_ROLLBAK_ONLY));
	}

	/**
	 * アクセス制御オブジェクトを返します.
	 *
	 * @return アクセス制御オブジェクト
	 */
	public static AccessControl getAccessControl() {
		return new DefaultUserAccessControl(getOperationUserInfo());
	}

	/**
	 * 操作者ユーザが引数で指定された実行権限を保有していない場合、例外を発生させます.
	 *
	 * @param executablePermission
	 *            チェック実行権限
	 * @throws PermissionException
	 *             操作者ユーザが実行権限を保有していない場合
	 */
	public static void checkPermission(PermissionInfo executablePermission) throws PermissionException {
		getAccessControl().checkPermission(executablePermission);
	}

	/**
	 * 操作者ユーザが引数で指定された実行権限を保有しているか判定し返します.
	 *
	 * @param executablePermission
	 *            チェック実行権限
	 * @return true:実行権限を有する/false:実行権限を有しない
	 */
	public static boolean validatePermission(PermissionInfo executablePermission) {
		return getAccessControl().validatePermission(executablePermission);
	}

}
