package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;

public class RuleForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private long ruleId;
	
	private long ruleListId;
	
	private List<MstRulesDTO> ruleList = new ArrayList<>();
	
	private MstRulesDTO ruleDTO = new MstRulesDTO();
	
	private List<MstRulesListDTO> ruleDetailList = new ArrayList<>();
	
	private MstRulesListDTO ruleDetailDTO = new MstRulesListDTO();
	/** メッセージ */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();
	
	private String alertType;
	
	/** メッセージ有無フラグ */
	private String messageFlg = "0";

	public long getRuleId() { return ruleId; }
	 
	public void setRuleId(long ruleId) { this.ruleId = ruleId; }
	 
	public List<MstRulesDTO> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<MstRulesDTO> ruleList) {
		this.ruleList = ruleList;
	}

	public MstRulesDTO getRuleDTO() {
		return ruleDTO;
	}

	public void setRuleDTO(MstRulesDTO ruleDTO) {
		this.ruleDTO = ruleDTO;
	}
	
	public String getAlertType() {
		return alertType;
	}
	
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}


	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		this.alertType = "0";

	}
	
	/**
	 * @return registryDTO
	 */
	public RegistryMessageDTO getRegistryDto() {
		return registryDto;
	}

	/**
	 * @param registryDTO セットする registryDTO
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
		this.registryDto = registryDto;
	}

	/**
	 * @return the ruleDetailList
	 */
	public List<MstRulesListDTO> getRuleDetailList() {
		return ruleDetailList;
	}

	/**
	 * @param ruleDetailList the ruleDetailList to set
	 */
	public void setRuleDetailList(List<MstRulesListDTO> ruleDetailList) {
		this.ruleDetailList = ruleDetailList;
	}

	/**
	 * @return the ruleDetailDTO
	 */
	public MstRulesListDTO getRuleDetailDTO() {
		return ruleDetailDTO;
	}

	/**
	 * @param ruleDetailDTO the ruleDetailDTO to set
	 */
	public void setRuleDetailDTO(MstRulesListDTO ruleDetailDTO) {
		this.ruleDetailDTO = ruleDetailDTO;
	}

	public String getMessageFlg() {
		return messageFlg;
	}

	public void setMessageFlg(String messageFlg) {
		this.messageFlg = messageFlg;
	}

	public long getRuleListId() {
		return ruleListId;
	}

	public void setRuleListId(long ruleListId) {
		this.ruleListId = ruleListId;
	}
	
}
