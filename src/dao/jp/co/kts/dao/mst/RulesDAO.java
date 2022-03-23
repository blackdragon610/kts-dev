package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.MstUserExtraRulesDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;

public class RulesDAO extends BaseDAO {

	public List<MstRulesDTO> getRulesList() throws DaoException {

		SQLParameters parameters = new SQLParameters();

		return selectList("SEL_RULES", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesDTO.class));
	}
	
	public List<MstRulesDTO> getRulesByUserId(long userId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysUserId", userId);

		return selectList("SEL_RULES_BY_USERID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesDTO.class));
	}
	
	public int deleteRuleItem(MstRulesDTO dto) throws DaoException{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleId", dto.getRuleId());
		return update("DELETE_RULE_ITEM", parameters);
	}
	public int registoryRule(MstRulesDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		return update("INS_RULE", parameters);
	}

	public int updateRule(MstRulesDTO dto, long id) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("ruleID", id);
		return update("UPD_RULE", parameters);
	}
	
	public List<MstRulesListDTO> getRuleDetailInfo(long ruleId) throws DaoException {

		//パラメータ設定
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleId", ruleId);
		parameters.addParameter("ruleListId", 0);

		return selectList("SEL_MST_RULE_LIST", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesListDTO.class));
	}
	
	public List<MstRulesListDTO> getRuleDetailInfoByUserId(long ruleId, long userId) throws DaoException {

		//パラメータ設定
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleId", ruleId);
		parameters.addParameter("ruleListId", 0);
		parameters.addParameter("sysUserId", userId);

		return selectList("SEL_MST_RULE_LIST_BY_USERID", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesListDTO.class));
	}
	
	public int registryRuleList(MstRulesListDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		return update("INS_RULE_LIST", parameters);
	}
	
	public int deleteRuleListItem(MstRulesListDTO dto) throws DaoException{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleListId", dto.getRuleListId());
		return update("DELETE_RULE_LIST_ITEM", parameters);
	}
	
	public List<MstRulesListDTO> getRuleDetailById(long ruleId, long listId) throws DaoException {

		//パラメータ設定
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleId", ruleId);
		parameters.addParameter("ruleListId", listId);

		return selectList("SEL_MST_RULE_LIST", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesListDTO.class));
	}
	
	public int updateRuleList(MstRulesListDTO dto) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("ruleListID", dto.getRuleListId());
		return update("UPD_RULE_LIST", parameters);
	}
	
	public int deletRuleListByRuleId(long ruleId) throws DaoException{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleId", ruleId);
		return update("DELETE_RULE_LIST_BY_RULEID", parameters);
	}
	
	public List<MstUserExtraRulesDTO> getExtraRulesByUserId(long ruleId, long userId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysUserId", userId);
		parameters.addParameter("ruleId", ruleId);
		parameters.addParameter("ruleListId", 0);

		return selectList("SEL_EXTRA_RULES_BY_USERID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstUserExtraRulesDTO.class));
	}
	
	public List<MstUserExtraRulesDTO> getExtraRulesByListId(long ruleListId, long userId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleListId", ruleListId);
		parameters.addParameter("sysUserId", userId);
		parameters.addParameter("ruleId", 0);

		return selectList("SEL_EXTRA_RULES_BY_USERID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstUserExtraRulesDTO.class));
	}
	
	public int insertExtraRule(long ruleId, long userId, long ruleListId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("ruleId", ruleId);
		parameters.addParameter("sysUserId", userId);
		parameters.addParameter("ruleListId", ruleListId);
		return update("INS_EXTRA_RULE_BY_USERID", parameters);
	}
	public int deleteExtraRule(MstUserExtraRulesDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("extraId", dto.getExtraId());
		return update("DEL_EXTRA_RULE_BY_USERID", parameters);
	}
	public int updateExtraRule(MstUserExtraRulesDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("extraId", dto.getExtraId());
		return update("UPD_EXTRA_RULE_BY_USERID", parameters);
	}

}
