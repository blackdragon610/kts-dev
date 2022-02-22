package jp.co.kts.app.common.entity;

public class SetExhibitManage {

	/** システムセット管理ID */
	private long sysSetManagementId;

	/** 管理ID */
	private long sysManagementId;

	/** 構成管理ID */
	private long formSysManagementId;

	/** 個数 */
	private long itemNum;

	/** 削除フラグ */
	private String deleteFlag;

	/** 登録日 */
	private String createDate;

	/** 登録者ID */
	private int createUserId;

	/** 更新日 */
	private String updateDate;

	/** 更新者ID */
	private int updateUserId;

	/**
	 * @return sysSetManagementId
	 */
	public long getSysSetManagementId() {
		return sysSetManagementId;
	}
	/**
	 * @param sysSetManagementId セットする sysSetManagementId
	 */
	public void setSysSetManagementId(long sysSetManagementId) {
		this.sysSetManagementId = sysSetManagementId;
	}
	/**
	 * @return sysManagementId
	 */
	public long getSysManagementId() {
		return sysManagementId;
	}
	/**
	 * @param sysManagementId セットする sysManagementId
	 */
	public void setSysManagementId(long sysManagementId) {
		this.sysManagementId = sysManagementId;
	}
	/**
	 * @return formSysManagementId
	 */
	public long getFormSysManagementId() {
		return formSysManagementId;
	}
	/**
	 * @param formSysManagementId セットする formSysManagementId
	 */
	public void setFormSysManagementId(long formSysManagementId) {
		this.formSysManagementId = formSysManagementId;
	}
	/**
	 * @return itemNum
	 */
	public long getItemNum() {
		return itemNum;
	}
	/**
	 * @param itemNum セットする itemNum
	 */
	public void setItemNum(long itemNum) {
		this.itemNum = itemNum;
	}
	/**
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * @param deleteFlag セットする deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * @return createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate セットする createDate
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return createUserId
	 */
	public int getCreateUserId() {
		return createUserId;
	}
	/**
	 * @param createUserId セットする createUserId
	 */
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return updateUserId
	 */
	public int getUpdateUserId() {
		return updateUserId;
	}
	/**
	 * @param updateUserId セットする updateUserId
	 */
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

}
