package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.DomesticExhibitionDTO;

public class ExtendSetDomesticExhibitionDto extends DomesticExhibitionDTO {

	/** システムセット管理ID */
	private long sysSetManagementId;


	/** 構成管理ID */
	private long formSysManagementId;

	/** 個数 */
	private long itemNum;

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


}
