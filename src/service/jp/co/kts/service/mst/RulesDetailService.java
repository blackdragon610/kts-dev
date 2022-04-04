package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.core.util.CipherUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.MstUserExtraRulesDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.DomesticSlipDAO;
import jp.co.kts.dao.mst.MakerDAO;
import jp.co.kts.dao.mst.RulesDAO;
import jp.co.kts.dao.mst.UserDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceValidator;
import jp.co.kts.service.login.LoginService;

public class RulesDetailService {

	private static final String RULE_LIST_CHECK_FLG_ON = "on";
	
	public List<MstRulesListDTO> getRuleDetail(long ruleId) throws Exception {
		RulesDAO dao = new RulesDAO();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		List<MstRulesListDTO> listRulesDetail = dao.getRuleDetailInfoByUserId(ruleId, userInfo.getUserId());
		for(MstRulesListDTO dto : listRulesDetail) {
			dto.setListPass(CipherUtil.decodeString(dto.getListPass()));
		}
		return listRulesDetail;
//		return dao.getRuleDetailInfoByUserId(ruleId, userInfo.getUserId());
	}
	
	public MstRulesListDTO getRuleDetails(long ruleListId) throws Exception {
		RulesDAO dao = new RulesDAO();
		MstRulesListDTO dto = dao.getRuleDetails(ruleListId);
		dto.setListPass(CipherUtil.decodeString(dto.getListPass()));
		
		return dto;
	}
	
	public int ruleListItemDelete(List<MstRulesListDTO> dto) throws DaoException {
		int resultCnt = 0;
		RulesDAO dao = new RulesDAO();
		for (MstRulesListDTO ruleDto : dto) {
			if (!ruleDto.getItemCheckFlg().equals(RULE_LIST_CHECK_FLG_ON)) {
				continue;
			}
			resultCnt += dao.deleteRuleListItem(ruleDto);
		}

		return resultCnt;
	}
	public int registryRuleList(MstRulesListDTO dto, long ruleId)throws Exception {
		int result = 0;
		RulesDAO dao = new RulesDAO();
		dto.setRuleListId(new SequenceDAO().getMaxRuleListId() + 1);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		dto.setSysUserId(userInfo.getUserId());
		dto.setRuleId(ruleId);
		
		dto.setListPass(CipherUtil.encodeString(dto.getListPass()));

		result = dao.registryRuleList(dto);
		dto.setListPass(CipherUtil.decodeString(dto.getListPass()));
	
		return result;
	}
	public List<MstRulesListDTO> getRuleDetailById(long ruleId, long listId) throws Exception {
		RulesDAO dao = new RulesDAO();
		return dao.getRuleDetailById(ruleId, listId);
	}
	
	public int updateRuleList(MstRulesListDTO dto, long ruleListId)throws Exception {
		int result = 0;
		RulesDAO dao = new RulesDAO();
		dto.setRuleListId(ruleListId);
		dto.setListPass(CipherUtil.encodeString(dto.getListPass()));
		result = dao.updateRuleList(dto);
		dto.setListPass(CipherUtil.decodeString(dto.getListPass()));
		return result;
	}
	public int updateExtraRuleDetail(MstRulesListDTO dto, long userId, long ruleId, long visible)throws Exception {
		int result = 0;
		
		RulesDAO dao = new RulesDAO();
		List<MstUserExtraRulesDTO> extraRulesList = dao.getExtraRulesByListId(dto.getRuleListId(), userId);
		
		if(extraRulesList.size() < 1 ) {
			if(visible > 0) 
				result = dao.insertExtraRule(dto.getRuleId(), userId, dto.getRuleListId());	
		}
		else {
			for (MstUserExtraRulesDTO extraDto : extraRulesList) 
			{
				if(visible > 0) result = dao.updateExtraRule(extraDto);
				else result = dao.deleteExtraRule(extraDto);
			}
		}
			
		return result;
	}
	
	public Result<MstRulesDTO> validate(MstRulesListDTO dto) {

		Result<MstRulesDTO> result = new Result<MstRulesDTO>();

		//入力チェック(必須・文字数)
		ServiceValidator.requiredChecker(result, dto.getListName(), "名称");
//		ServiceValidator.inputChecker(result, dto.getListName(), "名称", 30, true);
		ServiceValidator.requiredChecker(result, dto.getListId(), "ID");
//		ServiceValidator.inputChecker(result, dto.getListId(), "ID", 30, true);
		ServiceValidator.requiredChecker(result, dto.getListPass(), "PASS");
//		ServiceValidator.inputChecker(result, dto.getListPass(), "PASS", 30, true);
		return result;
	}

}
