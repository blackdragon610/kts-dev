package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.NoticeBoardDTO;

public class ExtendNoticeBoardDTO extends NoticeBoardDTO {

	private String createUserFamilyName;

	private String createUserFirstName;

	private String updateUserFamilyName;

	private String updateUserFirstName;

	/**
	 * @return createUserFamilyName
	 */
	public String getCreateUserFamilyName() {
		return createUserFamilyName;
	}

	/**
	 * @param createUserFamilyName セットする createUserFamilyName
	 */
	public void setCreateUserFamilyName(String createUserFamilyName) {
		this.createUserFamilyName = createUserFamilyName;
	}

	/**
	 * @return createUserFirstName
	 */
	public String getCreateUserFirstName() {
		return createUserFirstName;
	}

	/**
	 * @param createUserFirstName セットする createUserFirstName
	 */
	public void setCreateUserFirstName(String createUserFirstName) {
		this.createUserFirstName = createUserFirstName;
	}

	/**
	 * @return updateUserFamilyName
	 */
	public String getUpdateUserFamilyName() {
		return updateUserFamilyName;
	}

	/**
	 * @param updateUserFamilyName セットする updateUserFamilyName
	 */
	public void setUpdateUserFamilyName(String updateUserFamilyName) {
		this.updateUserFamilyName = updateUserFamilyName;
	}

	/**
	 * @return updateUserFirstName
	 */
	public String getUpdateUserFirstName() {
		return updateUserFirstName;
	}

	/**
	 * @param updateUserFirstName セットする updateUserFirstName
	 */
	public void setUpdateUserFirstName(String updateUserFirstName) {
		this.updateUserFirstName = updateUserFirstName;
	}
}
