package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.MstUserDTO;

public class ExtendMstUserDTO extends MstUserDTO {

	private String updateUserFamilyNmKanji;

	private String updateUserFirstNmKanji;

	private String overseasInfoAuth;

	/**
	 * @return updateUserFamilyNmKanji
	 */
	public String getUpdateUserFamilyNmKanji() {
		return updateUserFamilyNmKanji;
	}

	/**
	 * @param updateUserFamilyNmKanji セットする updateUserFamilyNmKanji
	 */
	public void setUpdateUserFamilyNmKanji(String updateUserFamilyNmKanji) {
		this.updateUserFamilyNmKanji = updateUserFamilyNmKanji;
	}

	/**
	 * @return updateUserFirstNmKanji
	 */
	public String getUpdateUserFirstNmKanji() {
		return updateUserFirstNmKanji;
	}

	/**
	 * @param updateUserFirstNmKanji セットする updateUserFirstNmKanji
	 */
	public void setUpdateUserFirstNmKanji(String updateUserFirstNmKanji) {
		this.updateUserFirstNmKanji = updateUserFirstNmKanji;
	}

	/**
	 * overseasInfoAuthを取得します。
	 * @return overseasInfoAuth
	 */
	public String getOverseasInfoAuth() {
	    return overseasInfoAuth;
	}

	/**
	 * overseasInfoAuthを設定します。
	 * @param overseasInfoAuth overseasInfoAuth
	 */
	public void setOverseasInfoAuth(String overseasInfoAuth) {
	    this.overseasInfoAuth = overseasInfoAuth;
	}


}
