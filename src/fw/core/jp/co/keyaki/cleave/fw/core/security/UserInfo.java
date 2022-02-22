package jp.co.keyaki.cleave.fw.core.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * ユーザオブジェクト.
 *
 * @author ytakahashi
 *
 */
public class UserInfo implements Serializable {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 姓・名セパレータ.
	 */
	private static final String NAME_SEP = " ";

	/**
	 * ユーザID.
	 */
	private long userId;

	/**
	 * ログインCD.
	 */
	private String loginCd;

	/**
	 * ユーザ苗字.
	 */
	private String familyName;

	/**
	 * ユーザ名前.
	 */
	private String firstName;

	/**
	 * ユーザ苗字カナ.
	 */
	private String familyNameKana;

	/**
	 * ユーザ名前カナ.
	 */
	private String firstNameKana;

	/**
	 * パスワード変更要求有無.
	 */
	private Boolean passwordChange;

	/**
	 * ユーザ保有ロール.
	 */
	private Set<RoleInfo> roleInfos = new HashSet<RoleInfo>();

	/**
	 * ユーザIDを返します.
	 *
	 * @return ユーザID
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * ユーザIDを設定します.
	 *
	 * @param userId ユーザID
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * ログインCDを返します.
	 *
	 * @return ログインCD
	 */
	public String getLoginCd() {
		return loginCd;
	}

	/**
	 * ログインCDを設定します.
	 *
	 * @param loginCd ログインCD
	 */
	public void setLoginCd(String loginCd) {
		this.loginCd = loginCd;
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 *
	 *
	 * @param familyName
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 *
	 *
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getFamilyNameKana() {
		return familyNameKana;
	}

	/**
	 *
	 *
	 * @param familyNameKana
	 */
	public void setFamilyNameKana(String familyNameKana) {
		this.familyNameKana = familyNameKana;
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getFirstNameKana() {
		return firstNameKana;
	}

	/**
	 *
	 *
	 * @param firstNameKana
	 */
	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}

	/**
	 *
	 *
	 * @return
	 */
	public Boolean getPasswordChange() {
		return passwordChange;
	}

	/**
	 *
	 *
	 * @param passwordChange
	 */
	public void setPasswordChange(Boolean passwordChange) {
		this.passwordChange = passwordChange;
	}

	/**
	 *
	 *
	 * @return
	 */
	public Set<RoleInfo> getRoleInfos() {
		return roleInfos;
	}

	/**
	 *
	 *
	 * @param roleInfos
	 */
	public void setRoleInfos(Set<RoleInfo> roleInfos) {
		this.roleInfos = roleInfos;
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getFullName() {
		return createFullName(firstName, familyName);
	}

	/**
	 *
	 *
	 * @return
	 */
	public String getFullNameKana() {
		return createFullName(firstNameKana, familyNameKana);
	}

	/**
	 *
	 *
	 * @param first
	 * @param familly
	 * @return
	 */
	protected String createFullName(String first, String familly) {
		if (StringUtils.isEmpty(first) || StringUtils.isEmpty(familly)) {
			return StringUtils.trimToEmpty(first) + StringUtils.trimToEmpty(familly);
		}
		if (isNameOrderFamilyToFirstLocale()) {
			return familly + NAME_SEP + first;
		}
		return first + NAME_SEP + familly;
	}

	/**
	 *
	 *
	 * @return
	 */
	protected boolean isNameOrderFamilyToFirstLocale() {
		if (Locale.JAPAN.equals(Locale.getDefault()) || Locale.JAPANESE.equals(Locale.getDefault())) {
			return true;
		}
		return false;
	}

	/**
	 *
	 *
	 * @return
	 */
	protected PermissionInfo getPermission() {
		Set<PermissionInfo> permissionInfos = new HashSet<PermissionInfo>();
		for (RoleInfo roleInfos : getRoleInfos()) {
			permissionInfos.add(roleInfos.getPermission());
		}
		CompositePermissionInfo permission = new CompositePermissionInfo(permissionInfos);
		return permission;
	}

}
