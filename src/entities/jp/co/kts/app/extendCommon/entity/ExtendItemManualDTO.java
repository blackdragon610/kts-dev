package jp.co.kts.app.extendCommon.entity;

import org.apache.struts.upload.FormFile;

import jp.co.kts.app.common.entity.ItemManualDTO;

public class ExtendItemManualDTO extends ItemManualDTO {

	/** 説明書ファイル */
	private FormFile manualFile;

	private String manualFileNameDisp;

	public FormFile getManualFile() {
		return manualFile;
	}

	public void setManualFile(FormFile manualFile) {
		this.manualFile = manualFile;
	}

	public String getManualFileNameDisp() {
		return manualFileNameDisp;
	}

	public void setManualFileNameDisp(String manualFileNameDisp) {
		this.manualFileNameDisp = manualFileNameDisp;
	}
}
