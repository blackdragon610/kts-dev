package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstAccountDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstAccountDTO;

public class AccountForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysAccountId;
//
	private List<ExtendMstAccountDTO> accountList = new ArrayList<>();

	private MstAccountDTO accountDTO = new MstAccountDTO();

	private String alertType;

	private List<MstCorporationDTO> corporationList = new ArrayList<>();


	public long getSysAccountId() {
		return sysAccountId;
	}

	public void setSysAccountId(long sysAccountId) {
		this.sysAccountId = sysAccountId;
	}



	public List<ExtendMstAccountDTO> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<ExtendMstAccountDTO> accountList) {
		this.accountList = accountList;
	}

	/**
	 * @return userDTO
	 */
	public MstAccountDTO getAccountDTO() {
		return accountDTO;
	}



	/**
	 * @param userDTO セットする userDTO
	 */
	public void setAccountDTO(MstAccountDTO accountDTO) {
		this.accountDTO = accountDTO;
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



	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}



	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
	}

}
