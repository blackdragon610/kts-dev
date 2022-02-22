package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.search.entity.ClientSearchDTO;
import jp.co.kts.ui.web.struts.WebConst;

public class ClientForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysClientId;

	private long sysCorporationId;

	private List<MstClientDTO> clientList = new ArrayList<>();

	private MstClientDTO clientDTO = new MstClientDTO();

	private String alertType;

	private ClientSearchDTO clientSearchDTO = new ClientSearchDTO();

	private List<ExtendMstClientDTO> extClientList = new ArrayList<>();

	private List<MstCorporationDTO> corporationList = new ArrayList<>();

	private Map<String, String> paymentMethodMap = WebConst.PAYMENT_METHOD_MAP;

	/**
	 * 配送情報MAP
	 */
	private Map<String, String> transportCorporationSystemMap = WebConst.TRANSPORT_CORPORATION_SYSTEM_MAP;

	/**
	 * 締日MAP
	 */
	private Map<String, String> cutoffDateMap = WebConst.CUTOFF_DATE_MAP;


	//表示用法人名
	private String corporationNm;

	/**
	 * @return sysClientId
	 */
	public long getSysClientId() {
		return sysClientId;
	}

	/**
	 * @param sysClientId セットする sysClientId
	 */
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}



	/**
	 * @return clientList
	 */
	public List<MstClientDTO> getClientList() {
		return clientList;
	}

	/**
	 * @param clientList セットする clientList
	 */
	public void setClientList(List<MstClientDTO> clientList) {
		this.clientList = clientList;
	}



	/**
	 * @return userDTO
	 */
	public MstClientDTO getClientDTO() {
		return clientDTO;
	}

	/**
	 * @param userDTO セットする userDTO
	 */
	public void setClientDTO(MstClientDTO clientDTO) {
		this.clientDTO = clientDTO;
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



	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		this.alertType = "0";
	}



	public ClientSearchDTO getClientSearchDTO() {
		return clientSearchDTO;
	}

	public void setClientSearchDTO(ClientSearchDTO clientSearchDTO) {
		this.clientSearchDTO = clientSearchDTO;
	}



	public List<ExtendMstClientDTO> getExtClientList() {
		return extClientList;
	}

	public void setExtClientList(List<ExtendMstClientDTO> extClientList) {
		this.extClientList = extClientList;
	}



	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}

	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
	}



	public Map<String, String> getPaymentMethodMap() {
		return paymentMethodMap;
	}

	public void setPaymentMethodMap(Map<String, String> paymentMethodMap) {
		this.paymentMethodMap = paymentMethodMap;
	}



	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

	/**
	 * @return sysCorporationId
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 * @param sysCorporationId セットする sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * @return transportCorporationSystemMap
	 */
	public Map<String, String> getTransportCorporationSystemMap() {
		return transportCorporationSystemMap;
	}

	/**
	 * @param transportCorporationSystemMap セットする transportCorporationSystemMap
	 */
	public void setTransportCorporationSystemMap(
			Map<String, String> transportCorporationSystemMap) {
		this.transportCorporationSystemMap = transportCorporationSystemMap;
	}

	/**
	 * @return cutoffDateMap
	 */
	public Map<String, String> getCutoffDateMap() {
		return cutoffDateMap;
	}

	/**
	 * @param cutoffDateMap セットする cutoffDateMap
	 */
	public void setCutoffDateMap(
			Map<String, String> cutoffDateMap) {
		this.cutoffDateMap = cutoffDateMap;
	}
}
