package jp.co.kts.service.mst;

import java.util.List;

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

public class RulesService {

	private static final String RULE_CHECK_FLG_ON = "on";

	/**
	 * 全ユーザを取得します(削除フラグは無視)
	 */
	public List<MstRulesDTO> getRulesList() throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		RulesDAO dao = new RulesDAO();
		
		List<MstRulesDTO> dto = dao.getRulesList();
		
		for (MstRulesDTO rDto : dto) 
		{
			rDto.setMstRulesDetailList(dao.getRuleDetailInfo(rDto.getRuleId()));
		}
		
		return dto;

//		return dao.getRulesList();
	}
	
	public MstRulesDTO getRules(long ruleId) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		RulesDAO dao = new RulesDAO();
		
		return dao.getRules(ruleId);
	}
	
	public int ruleItemDelete(List<MstRulesDTO> dto) throws DaoException {
		int resultCnt = 0;
		RulesDAO dao = new RulesDAO();
		for (MstRulesDTO ruleDto : dto) {
			if (!ruleDto.getItemCheckFlg().equals(RULE_CHECK_FLG_ON)) {
				continue;
			}
			resultCnt += dao.deleteRuleItem(ruleDto);
			//基本権限が削除されると、子も削除されます。
			dao.deletRuleListByRuleId(ruleDto.getRuleId());
		}

		return resultCnt;
	}
	
	public int registryRule(MstRulesDTO dto)throws Exception {
		int result = 0;
		RulesDAO dao = new RulesDAO();
		dto.setRuleId(new SequenceDAO().getMaxRuleId() + 1);

		result = dao.registoryRule(dto);
		return result;
	}
	
	public int updateRule(MstRulesDTO dto, long id)throws Exception {
		int result = 0;
		RulesDAO dao = new RulesDAO();
		result = dao.updateRule(dto, id);
		return result;
	}
	
	public Result<MstRulesDTO> validate(String ruleName) {

		Result<MstRulesDTO> result = new Result<MstRulesDTO>();

		//入力チェック(必須・文字数)
		ServiceValidator.requiredChecker(result, ruleName, "分類");
		ServiceValidator.inputChecker(result, ruleName, "分類", 30, true);

		return result;
	}
	
	public int updateExtraRule( MstRulesDTO dto, long userId)throws Exception {
		int result = 0;
		
		if(dto.getIsvisible().equals("1")) {
			result = this.checkVisible(dto, userId);
		}
		else 
			result = this.unCheckVisible(dto, userId);
		return result;
	}
	
	private int checkVisible(MstRulesDTO ruleDto, long userId) throws Exception
	{
		int result = 0;
		RulesDAO dao = new RulesDAO();
		
		List<MstRulesListDTO> childrenRules = dao.getRuleDetailInfo(ruleDto.getRuleId());
		for(MstRulesListDTO chDto : childrenRules) {
			List<MstUserExtraRulesDTO> extraRulesList = dao.getExtraRulesByListId(chDto.getRuleListId(), userId);
			if(extraRulesList.size() < 1) {
				result = dao.insertExtraRule(ruleDto.getRuleId(), userId, chDto.getRuleListId());
			}
			else {
				for (MstUserExtraRulesDTO extraDto : extraRulesList) 
				{
					result = dao.updateExtraRule(extraDto);
				}
			}
		}
		return result;
	}
	
	private int unCheckVisible(MstRulesDTO ruleDto, long userId) throws Exception
	{
		int result = 0;
		RulesDAO dao = new RulesDAO();
		List<MstUserExtraRulesDTO> extraRulesList = dao.getExtraRulesByUserId(ruleDto.getRuleId(), userId);
		
		if(extraRulesList.size() > 0) {
			for (MstUserExtraRulesDTO extraDto : extraRulesList) 
			{
				result = dao.deleteExtraRule(extraDto);
			}
		}
		return result;
		
	}
}
