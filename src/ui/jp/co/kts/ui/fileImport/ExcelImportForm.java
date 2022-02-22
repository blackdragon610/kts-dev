package jp.co.kts.ui.fileImport;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;

public class ExcelImportForm extends AppBaseForm{

	private static final long serialVersionUID = 1L;

	private FormFile fileUp;

	private String alertType;

	/**
	 * @return fileUp
	 */
	public FormFile getFileUp() {
		return fileUp;
	}

	/**
	 * @param fileUp セットする fileUp
	 */
	public void setFileUp(FormFile fileUp) {
		this.fileUp = fileUp;
	}

	/**
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		// TODO 自動生成されたメソッド・スタブ

		alertType = "0";

	}

	/**
	 * @return alertType
	 */
	public String getAlertType() {
		return alertType;
	}

	/**
	 * @param alertType セットする alertType
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}



}
