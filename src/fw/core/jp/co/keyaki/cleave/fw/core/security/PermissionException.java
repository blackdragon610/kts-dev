package jp.co.keyaki.cleave.fw.core.security;

import jp.co.keyaki.cleave.fw.core.CoreMessageDefine;
import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;

/**
 * 権限例外クラス.
 *
 * <p>
 * 実行時に権限不足を検知した際に送出される例外.
 * </p>
 *
 * @author ytakahashi
 */
public class PermissionException extends CoreRuntimeException {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ.
	 *
	 * @param userPrivilege ユーザ保有権限
	 * @param executablePermission 実行時必要権限
	 */
	public PermissionException(PermissionInfo userPrivilege, PermissionInfo executablePermission) {
		super(CoreMessageDefine.E000003, "userPrivilege=" + userPrivilege + ", executablePermission=" + executablePermission);
	}

}
