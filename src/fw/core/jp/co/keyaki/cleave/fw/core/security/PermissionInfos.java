package jp.co.keyaki.cleave.fw.core.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 権限管理クラス.
 *
 * @author ytakahashi
 *
 */
public class PermissionInfos {

	/**
	 * シングルトンインスタンス.
	 */
	private static PermissionInfos instance = new PermissionInfos();

	/**
	 * シングルトンインスタンスを返します.
	 *
	 * @return インスタンス
	 */
	public static PermissionInfos getInstance() {
		return instance;
	}

	/**
	 * 権限CDに対応した権限オブジェクトを返します.
	 *
	 * @param permissionCd 取得する権限CD
	 * @return 権限オブジェクト
	 */
	public static PermissionInfo getPermissionInfo(String permissionCd) {
		PermissionInfos instance = getInstance();
		synchronized (instance.permissionCdMap) {
			return instance.permissionCdMap.get(permissionCd);
		}
	}

	/**
	 * このクラスが保持している全権限を返します.
	 *
	 * @return 保持している全権限オブジェクト
	 */
	public static Collection<SimplePermissionInfo> getAllPermissionInfo() {
		PermissionInfos instance = getInstance();
		synchronized (instance.permissionCdMap) {
			return Collections.unmodifiableCollection(instance.permissionCdMap.values()) ;
		}
	}

	/**
	 * 権限CDをキーとした権限オブジェクトマップ.
	 */
	private Map<String, SimplePermissionInfo> permissionCdMap = new HashMap<String, SimplePermissionInfo>();

	/**
	 * プライベートコンストラクタ.
	 */
	private PermissionInfos() {

	}

	/**
	 * このクラスが保持している権限オブジェクトを一旦破棄し、引数の権限オブジェクトを再保持します.
	 *
	 * @param permissionInfos 再保持する権限オブジェクト
	 */
	public void setPermissionInfos(Collection<SimplePermissionInfo> permissionInfos) {
		synchronized (permissionCdMap) {
			permissionCdMap.clear();
			for (SimplePermissionInfo permissionInfo : permissionInfos) {
				addPermissionInfo(permissionInfo);
			}
		}
	}

	/**
	 * 引数の権限オブジェクトを保持します.
	 *
	 * @param permissionInfo 追加する権限オブジェクト
	 */
	public void addPermissionInfo(SimplePermissionInfo permissionInfo) {
		synchronized (permissionCdMap) {
			permissionCdMap.put(permissionInfo.getPermissionCd(), permissionInfo);
		}
	}
}
