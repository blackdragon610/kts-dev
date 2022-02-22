package jp.co.keyaki.cleave.fw.core.security;

/**
 * 
 * アクセス制御を実施するインターフェス.
 * 
 * @author ytakahashi
 * 
 */
public interface AccessControl {

	/**
	 * 所有権限を返却する.
	 * 
	 * @return 所有権限
	 */
	PermissionInfo getPrivilege();

	/**
	 * 引数で指定された実行権限を所有しているか判定します.
	 * 
	 * @param executablePermission
	 *            実行権限
	 * @return true:実行権限を有する/false:実行権限を有しない
	 */
	boolean validatePermission(PermissionInfo executablePermission);

	/**
	 * 引数で指定された実行権限を所有していない場合例外を発生させる.
	 * 
	 * @param executablePermission
	 *            実行権限
	 * @throws PermissionException
	 *             権限不足の場合
	 */
	void checkPermission(PermissionInfo executablePermission) throws PermissionException;

}
