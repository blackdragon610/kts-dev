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
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;

public class RulesDAO extends BaseDAO {

	public List<MstRulesDTO> getRulesList() throws DaoException {

		SQLParameters parameters = new SQLParameters();

		return selectList("SEL_RULES", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesDTO.class));
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
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("ruleId", ruleId);
		parameters.addParameter("ruleListId", 0);
		parameters.addParameter("sysUserId", userInfo.getUserId());

		return selectList("SEL_MST_RULE_LIST", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesListDTO.class));
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
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("ruleId", ruleId);
		parameters.addParameter("ruleListId", listId);
		parameters.addParameter("sysUserId", userInfo.getUserId());

		return selectList("SEL_MST_RULE_LIST", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstRulesListDTO.class));
	}
	
	public int updateRuleList(MstRulesListDTO dto) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("ruleListID", dto.getRuleListId());
		return update("UPD_RULE_LIST", parameters);
	}
//	public MstUserDTO getUser(long sysUserId) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("getListFlg", "0");
//		parameters.addParameter("sysUserId", sysUserId);
//
//		return select("SEL_USER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstUserDTO.class));
//	}
//
//	public ExtendMstUserDTO getUserName(long sysUserId) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("sysUserId", sysUserId);
//
//		return select("SEL_USERNAME", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstUserDTO.class));
//	}
//
//	public void updateUser(MstUserDTO dto) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		addParametersFromBeanProperties(dto, parameters);
//
//		UserInfo userInfo = ActionContext.getLoginUserInfo();
//		parameters.addParameter("updateUserId", userInfo.getUserId());
//		update("UPD_USER", parameters);
//	}
//
//	public void deleteUser(long sysUserId) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("sysUserId", sysUserId);
//
//		UserInfo userInfo = ActionContext.getLoginUserInfo();
//		parameters.addParameter("updateUserId", userInfo.getUserId());
//		update("UPD_DEL_USER", parameters);
//	}
//	public void registryUser(MstUserDTO dto) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		addParametersFromBeanProperties(dto, parameters);
//
//		UserInfo userInfo = ActionContext.getLoginUserInfo();
//		parameters.addParameter("createUserId", userInfo.getUserId());
//		parameters.addParameter("updateUserId", userInfo.getUserId());
//
//		update("INS_USER", parameters);
//	}
//
//	public long cheackSameLoginCd(String loginCd,long sysUserId) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("loginCd", loginCd);
//
//		if (sysUserId != 0){
//			parameters.addParameter("sysUserId", sysUserId);
//		}
//		return select("SEL_CHECK_SAME_LOGIN_CD", parameters, ResultSetHandlerFactory.getFirstColumnLongRowHandler());
//	}

}
