package jp.co.kts.ui.fileImport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.CsvImportDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstDeliveryCompanyDTO;
import jp.co.kts.app.common.entity.MstDeliveryDTO;
import jp.co.kts.app.input.entity.CsvInputDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;

import org.apache.struts.upload.FormFile;

public class CsvImportForm extends AppBaseForm{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<CsvImportDTO> csvImportList = new ArrayList<>();

	private int corporationId;

	private List<MstCorporationDTO> corporationList = new ArrayList<>();

	private FormFile fileUp;

	private List<CsvInputDTO> csvInputList = new ArrayList<>();

	private List<ErrorDTO> csvErrorList = new ArrayList<>();

	private ErrorDTO csvErrorDTO = new ErrorDTO();

	private String alertType = "0";

	private String deliveryRadio;

	private int trueCount = 0;
	/** メッセージ */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();
	
	// added by wahaha
	private int deliveryCompanyId;
	
	private List<MstDeliveryCompanyDTO> deliveryCompanyList = new ArrayList<>();
	

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {

		//エラーメッセージ初期化
		this.setCsvErrorDTO(new ErrorDTO());
		this.setCsvErrorList(new ArrayList<ErrorDTO>());
		this.setCsvImportList(new ArrayList<CsvImportDTO>());

	}

	/**
	 * @return csvImportList
	 */
	public List<CsvImportDTO> getCsvImportList() {
		return csvImportList;
	}




	/**
	 * @param csvImportList セットする csvImportList
	 */
	public void setCsvImportList(List<CsvImportDTO> csvImportList) {
		this.csvImportList = csvImportList;
	}




	/**
	 * @return corporationId
	 */
	public int getCorporationId() {
		return corporationId;
	}

	/**
	 * @param corporationId セットする corporationId
	 */
	public void setCorporationId(int corporationId) {
		this.corporationId = corporationId;
	}







	/**
	 * @return corporationList
	 */
	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}




	/**
	 * @param corporationList セットする corporationList
	 */
	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
	}




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






	public List<CsvInputDTO> getCsvInputList() {
		return csvInputList;
	}




	public void setCsvInputList(List<CsvInputDTO> csvInputList) {
		this.csvInputList = csvInputList;
	}

	public List<ErrorDTO> getCsvErrorList() {
		return csvErrorList;
	}




	public void setCsvErrorList(List<ErrorDTO> csvErrorList) {
		this.csvErrorList = csvErrorList;
	}

	public ErrorDTO getCsvErrorDTO() {
		return csvErrorDTO;
	}




	public void setCsvErrorDTO(ErrorDTO csvErrorDTO) {
		this.csvErrorDTO = csvErrorDTO;
	}




	public String getAlertType() {
		return alertType;
	}




	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getDeliveryRadio() {
		return deliveryRadio;
	}

	public void setDeliveryRadio(String deliveryRadio) {
		this.deliveryRadio = deliveryRadio;
	}

	public int getTrueCount() {
		return trueCount;
	}

	public void setTrueCount(int trueCount) {
		this.trueCount = trueCount;
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

	
	/**
	 * Delivery company DTO list
	 * @return deliveryCompanyList
	 */
	public List<MstDeliveryCompanyDTO> getDeliveryCompanyList() {
		if (deliveryCompanyList.size() == 0) {
			
			MstDeliveryCompanyDTO yamatoDto = new MstDeliveryCompanyDTO();
			yamatoDto.setCompanyId(1);
			yamatoDto.setCompanyName("ヤマト");
			deliveryCompanyList.add(yamatoDto);
			
			MstDeliveryCompanyDTO sagawaDto = new MstDeliveryCompanyDTO();
			sagawaDto.setCompanyId(2);
			sagawaDto.setCompanyName("佐川");
			deliveryCompanyList.add(sagawaDto);
			
			MstDeliveryCompanyDTO seinoDto = new MstDeliveryCompanyDTO();
			seinoDto.setCompanyId(3);
			seinoDto.setCompanyName("西濃");
			deliveryCompanyList.add(seinoDto);
			
			MstDeliveryCompanyDTO jpPostDto = new MstDeliveryCompanyDTO();
			jpPostDto.setCompanyId(4);
			jpPostDto.setCompanyName("郵政");
			deliveryCompanyList.add(jpPostDto);
		}
		return deliveryCompanyList;
	}
	
	/**
	 * DeliveryCompanyList を 設定します。
	 * @param deliveryCompanyList メッセージ
	 */
	public void setDeliveryCompanyList(List<MstDeliveryCompanyDTO> deliveryCompanyList) {
		this.deliveryCompanyList = deliveryCompanyList;		
	}
	
	/**
	 * @return deliveryCompanyId
	 */
	public int getDeliveryCompanyId() {
		return deliveryCompanyId;
	}

	/**
	 * @param deliveryCompanyId セットする deliveryCompanyId
	 */
	public void setDeliveryCompanyId(int deliveryCompanyId) {
		this.deliveryCompanyId = deliveryCompanyId;
	}
	
	
}
