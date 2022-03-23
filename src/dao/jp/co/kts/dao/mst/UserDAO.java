package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;

public class UserDAO extends BaseDAO {

	public List<MstUserDTO> getUserList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");
		
		return selectList("SEL_USER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstUserDTO.class));
	}


	public List<MstUserDTO> getUserListAll() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("deleteFlgIgnore", "1");
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_USER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstUserDTO.class));
	}
	public MstUserDTO getUser(long sysUserId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysUserId", sysUserId);

		return select("SEL_USER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstUserDTO.class));
	}

	public ExtendMstUserDTO getUserName(long sysUserId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysUserId", sysUserId);

		return select("SEL_USERNAME", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstUserDTO.class));
	}

	public void updateUser(MstUserDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_USER", parameters);
	}

	public void deleteUser(long sysUserId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysUserId", sysUserId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_USER", parameters);
	}
	public void registryUser(MstUserDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_USER", parameters);
	}

	public long cheackSameLoginCd(String loginCd,long sysUserId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("loginCd", loginCd);

		if (sysUserId != 0){
			parameters.addParameter("sysUserId", sysUserId);
		}
		return select("SEL_CHECK_SAME_LOGIN_CD", parameters, ResultSetHandlerFactory.getFirstColumnLongRowHandler());
	}
	
	public void updateUserMainRule(MstUserDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_MAIN_USER_RULE", parameters);
	}
}
