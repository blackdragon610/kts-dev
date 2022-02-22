package jp.co.kts.app.input.entity;

import org.apache.struts.upload.FormFile;

public class CsvInputDTO {

	private FormFile fileUp;

	private String csvFileName;

	private long corporationId;



	public FormFile getFileUp() {
		return fileUp;
	}

	public void setFileUp(FormFile fileUp) {
		this.fileUp = fileUp;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	public long getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(long corporationId) {
		this.corporationId = corporationId;
	}



}
