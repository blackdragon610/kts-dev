package jp.co.keyaki.cleave.fw.core.security;

import jp.co.keyaki.cleave.fw.core.CoreMessageDefine;
import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;

/**
 * 
 * デフォルトのユーザアクセス制御クラス.
 * 
 * @author ytakahashi
 * 
 */
public class DefaultUserAccessControl implements UserAccessControl {

	/**
	 * 検証対象ユーザオブジェクト.
	 */
	private UserInfo userInfo;

	/**
	 * コンストラクタ.
	 * 
	 * @param userInfo
	 *            検証対象ユーザオブジェクト.
	 */
	public DefaultUserAccessControl(UserInfo userInfo) {
		setUserInfo(userInfo);
	}

	/**
	 * 実行権限検証ユーザ情報を設定します.
	 * 
	 * @param userInfo
	 *            ユーザ情報
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * 検証対象ユーザが所有している権限を返します.
	 * 
	 * @return ユーザ所有権限
	 */
	public PermissionInfo getPrivilege() {
		return userInfo.getPermission();
	}

	/**
	 * 引数で指定された実行権限を検証対象ユーザが所有しているか判定します.
	 * 
	 * @param executablePermission
	 *            実行権限
	 * @return true:実行権限を有する/false:実行権限を有しない
	 */
	public boolean validatePermission(PermissionInfo executablePermission) {

		if (executablePermission == null) {
			return true;
		}

		return getPrivilege().contains(executablePermission);
	}

	/**
	 * 引数で指定された実行権限を検証対象ユーザが所有していない場合例外を発生させる.
	 * 
	 * @param executablePermission
	 *            実行権限
	 * @throws PermissionException
	 *             権限不足の場合
	 */
	public void checkPermission(PermissionInfo executablePermission) {
		if (!validatePermission(executablePermission)) {
			throw new CoreRuntimeException(CoreMessageDefine.E000003);
		}
	}

}
