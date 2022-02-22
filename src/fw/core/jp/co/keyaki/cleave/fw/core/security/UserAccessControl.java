package jp.co.keyaki.cleave.fw.core.security;

/**
 * ユーザの実行権限をチェックするためのインターフェース.
 *
 * @author ytakahashi
 *
 */
public interface UserAccessControl extends AccessControl {

	/**
	 * 実行権限検証ユーザ情報を設定します.
	 *
	 * @param userInfo
	 *            ユーザ情報
	 */
	void setUserInfo(UserInfo userInfo);

}
