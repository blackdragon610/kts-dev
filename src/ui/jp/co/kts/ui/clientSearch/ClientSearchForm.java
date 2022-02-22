package jp.co.kts.ui.clientSearch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.extendCommon.entity.ExtendItemManualDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstDeliveryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.search.entity.ClientSearchDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;

public class ClientSearchForm extends AppBaseForm {

	private long searchSysClientId;

	private ClientSearchDTO clientSearchDTO = new ClientSearchDTO();

	private List<ExtendMstClientDTO> searchClientList = new ArrayList<>();

	private int clientListSize = 0;

	private long sysClientId;

	private List<ExtendMstDeliveryDTO> deliveryList = new ArrayList<>();

	private long sysCorporationId;

//	private List<ExtendItemManualDTO> itemManualList = initManualList();
//
//	private List<ExtendItemManualDTO> addItemManualList = initManualList();

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {

	}

	/**
	 * @return searchSysClientId
	 */
	public long getSearchSysClientId() {
		return searchSysClientId;
	}

	/**
	 * @param searchSysClientId セットする searchSysClientId
	 */
	public void setSearchSysClientId(long searchSysClientId) {
		this.searchSysClientId = searchSysClientId;
	}

	/**
	 * @return clientSearchDTO
	 */
	public ClientSearchDTO getClientSearchDTO() {
		return clientSearchDTO;
	}

	/**
	 * @param clientSearchDTO セットする clientSearchDTO
	 */
	public void setClientSearchDTO(ClientSearchDTO clientSearchDTO) {
		this.clientSearchDTO = clientSearchDTO;
	}

	/**
	 * @return searchClientList
	 */
	public List<ExtendMstClientDTO> getSearchClientList() {
		return searchClientList;
	}

	/**
	 * @param searchClientList セットする searchClientList
	 */
	public void setSearchClientList(List<ExtendMstClientDTO> searchClientList) {
		this.searchClientList = searchClientList;
	}

	/**
	 * @return clientListSize
	 */
	public int getClientListSize() {
		return clientListSize;
	}

	/**
	 * @param clientListSize セットする clientListSize
	 */
	public void setClientListSize(int clientListSize) {
		this.clientListSize = clientListSize;
	}

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
	 * @return deliveryList
	 */
	public List<ExtendMstDeliveryDTO> getDeliveryList() {
		return deliveryList;
	}

	/**
	 * @param deliveryList セットする deliveryList
	 */
	public void setDeliveryList(List<ExtendMstDeliveryDTO> deliveryList) {
		this.deliveryList = deliveryList;
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

}
