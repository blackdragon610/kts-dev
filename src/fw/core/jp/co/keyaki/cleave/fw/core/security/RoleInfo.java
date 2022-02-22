package jp.co.keyaki.cleave.fw.core.security;

import java.io.Serializable;
import java.util.Set;

/**
 * 役割を表します.
 *
 * @author ytakahashi
 *
 */
public class RoleInfo implements Serializable {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 役割名.
	 */
	private String roleName;

	/**
	 * 役割が保有する権限.
	 */
	private Set<PermissionInfo> permissionInfos;

	/**
	 * コンストラクタ.
	 *
	 * @param roleName
	 *            役割名
	 * @param permissionInfos
	 *            役割が保有する権限.
	 */
	public RoleInfo(String roleName, Set<PermissionInfo> permissionInfos) {
		setRoleName(roleName);
		setPermissions(permissionInfos);
	}

	/**
	 * 役割名を返します.
	 *
	 * @return 役割名
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 保有する権限を返します.
	 *
	 * @return 保有権限
	 */
	public PermissionInfo getPermission() {
		return new CompositePermissionInfo(permissionInfos);
	}

	/**
	 * 役割名を設定します.
	 *
	 * @param roleName
	 *            役割名
	 */
	protected void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 保有する権限を設定します.
	 *
	 * @param permissionInfos
	 *            保有権限.
	 */
	protected void setPermissions(Set<PermissionInfo> permissionInfos) {
		this.permissionInfos = permissionInfos;
	}

}
