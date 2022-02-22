package jp.co.kts.ui.fileImport;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.output.entity.RegistryMessageDTO;

public class DomesticExhibitionImportForm extends AppBaseForm {


	private static final long serialVersionUID = 1L;

	private FormFile fileUp;

	private String alertType;

	/** メッセージ有無フラグ */
	private String messageFlg = "0";

	/** メッセージ */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();

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

	/**
	 * メッセージ有無フラグを取得します。
	 * @return メッセージ有無フラグ
	 */
	public String getMessageFlg() {
	    return messageFlg;
	}

	/**
	 * メッセージ有無フラグを設定します。
	 * @param messageFlg メッセージ有無フラグ
	 */
	public void setMessageFlg(String messageFlg) {
	    this.messageFlg = messageFlg;
	}

	/**
	 * メッセージを取得します。
	 * @return メッセージ
	 */
	public RegistryMessageDTO getRegistryDto() {
	    return registryDto;
	}

	/**
	 * メッセージを設定します。
	 * @param registryDto メッセージ
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
	    this.registryDto = registryDto;
	}



}
