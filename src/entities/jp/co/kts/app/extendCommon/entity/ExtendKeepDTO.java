package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.KeepDTO;

public class ExtendKeepDTO extends KeepDTO {
	/** システム外部キープID */
	private long sysExternalKeepId;

	/** システム外部倉庫コード */
	private String sysExternalWarehouseCode;


	public long getSysExternalKeepId() {
		return sysExternalKeepId;
	}


	public void setSysExternalKeepId(long sysExternalKeepId) {
		this.sysExternalKeepId = sysExternalKeepId;
	}


	public String getSysExternalWarehouseCode() {
		return sysExternalWarehouseCode;
	}


	public void setSysExternalWarehouseCode(String sysExternalWarehouseCode) {
		this.sysExternalWarehouseCode = sysExternalWarehouseCode;
	}
}
