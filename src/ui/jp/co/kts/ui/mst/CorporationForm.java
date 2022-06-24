package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstProfitDTO;

public class CorporationForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysCorporationId;

	private List<MstCorporationDTO> corporationList = new ArrayList<>();

	private MstCorporationDTO corporationDTO = new MstCorporationDTO();

	private String alertType;
	
	// profit calc
	private List<MstProfitDTO> channelProfitList = new ArrayList<>();

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
	 * @return corporationDTO
	 */
	public MstCorporationDTO getCorporationDTO() {
		return corporationDTO;
	}







	/**
	 * @param corporationDTO セットする corporationDTO
	 */
	public void setCorporationDTO(MstCorporationDTO corporationDTO) {
		this.corporationDTO = corporationDTO;
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







	/**
	 * @return the channelProfitList
	 */
	public List<MstProfitDTO> getChannelProfitList() {
		return channelProfitList;
	}







	/**
	 * @param channelProfitList the channelProfitList to set
	 */
	public void setChannelProfitList(List<MstProfitDTO> channelProfitList) {
		this.channelProfitList = channelProfitList;
	}

}
