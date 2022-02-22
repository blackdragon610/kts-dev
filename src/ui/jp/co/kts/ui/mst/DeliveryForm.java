package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstDeliveryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstDeliveryDTO;

public class DeliveryForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysDeliveryId;

	private MstDeliveryDTO deliveryDTO = new MstDeliveryDTO();

	private List<ExtendMstDeliveryDTO> deliveryList = new ArrayList<>();

	private String alertType;

	private List<MstClientDTO> clientList = new ArrayList<>();

	private List<MstCorporationDTO> corporationList = new ArrayList<>();

	private long sysCorporationId;

	public long getSysDeliveryId() {
		return sysDeliveryId;
	}

	public void setSysDeliveryId(long sysDeliveryId) {
		this.sysDeliveryId = sysDeliveryId;
	}

	public MstDeliveryDTO getDeliveryDTO() {
		return deliveryDTO;
	}

	public void setDeliveryDTO(MstDeliveryDTO deliveryDTO) {
		this.deliveryDTO = deliveryDTO;
	}

	public List<ExtendMstDeliveryDTO> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<ExtendMstDeliveryDTO> deliveryList) {
		this.deliveryList = deliveryList;
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

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}

	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
	}

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		this.alertType = "0";

	}

	public List<MstClientDTO> getClientList() {
		return clientList;
	}

	public void setClientList(List<MstClientDTO> clientList) {
		this.clientList = clientList;
	}

}
