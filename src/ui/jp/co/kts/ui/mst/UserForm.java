package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.common.entity.MstUserDTO;

public class UserForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysUserId;

	private List<MstUserDTO> userList = new ArrayList<>();

	private MstUserDTO userDTO = new MstUserDTO();

	private String alertType;
	
	private List<MstRulesDTO> ruleList = new ArrayList<>();
	
	public int isEditModeSingle;
	
	public int isEditModeAll;
	
	private List<MstRulesListDTO> extraRuleList = new ArrayList<>();
	
	private MstRulesListDTO extraRuleListDTO = new MstRulesListDTO();

	/**
	 * @return sysUserId
	 */
	public long getSysUserId() {
		return sysUserId;
	}



	/**
	 * @param sysUserId セットする sysUserId
	 */
	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}



	/**
	 * @return userList
	 */
	public List<MstUserDTO> getUserList() {
		return userList;
	}



	/**
	 * @param userList セットする userList
	 */
	public void setUserList(List<MstUserDTO> userList) {
		this.userList = userList;
	}



	/**
	 * @return userDTO
	 */
	public MstUserDTO getUserDTO() {
		return userDTO;
	}



	/**
	 * @param userDTO セットする userDTO
	 */
	public void setUserDTO(MstUserDTO userDTO) {
		this.userDTO = userDTO;
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
	 * @return the ruleList
	 */
	public List<MstRulesDTO> getRuleList() {
		return ruleList;
	}



	/**
	 * @param ruleList the ruleList to set
	 */
	public void setRuleList(List<MstRulesDTO> ruleList) {
		this.ruleList = ruleList;
	}


	/**
	 * @return the isEditModeSingle
	 */
	public int getIsEditModeSingle() {
		return isEditModeSingle;
	}



	/**
	 * @param isEditModeSingle the isEditModeSingle to set
	 */
	public void setIsEditModeSingle(int isEditModeSingle) {
		this.isEditModeSingle = isEditModeSingle;
	}



	/**
	 * @return the isEditModeAll
	 */
	public int getIsEditModeAll() {
		return isEditModeAll;
	}



	/**
	 * @param isEditModeAll the isEditModeAll to set
	 */
	public void setIsEditModeAll(int isEditModeAll) {
		this.isEditModeAll = isEditModeAll;
	}



	/**
	 * @return the extraRuleList
	 */
	public List<MstRulesListDTO> getExtraRuleList() {
		return extraRuleList;
	}



	/**
	 * @param extraRuleList the extraRuleList to set
	 */
	public void setExtraRuleList(List<MstRulesListDTO> extraRuleList) {
		this.extraRuleList = extraRuleList;
	}



	/**
	 * @return the extraRuleListDTO
	 */
	public MstRulesListDTO getExtraRuleListDTO() {
		return extraRuleListDTO;
	}



	/**
	 * @param extraRuleListDTO the extraRuleListDTO to set
	 */
	public void setExtraRuleListDTO(MstRulesListDTO extraRuleListDTO) {
		this.extraRuleListDTO = extraRuleListDTO;
	}

}
