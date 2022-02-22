package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstSmallGroupDTO;
import jp.co.kts.app.extendCommon.entity.VariousGroupDTO;

public class SmallGroupDAO extends BaseDAO {

	public List<MstSmallGroupDTO> getSmallGroupList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_SMALL_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstSmallGroupDTO.class));
	}

	public MstSmallGroupDTO getSmallGroup(long sysVariousGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysSmallGroupId", sysVariousGroupId);

		return select("SEL_SMALL_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstSmallGroupDTO.class));
	}

	public void updateGroup(VariousGroupDTO groupDTO) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(groupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SMALL_GROUP", parameters);
	}

	public void deleteGroup(long sysGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysGroupId", sysGroupId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_SMALL_GROUP", parameters);
	}

	public void registrySmallGroup(MstSmallGroupDTO smallGroupDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(smallGroupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_SMALL_GROUP", parameters);
	}

	public void updateSmallGroup(MstSmallGroupDTO smallGroupDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(smallGroupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_SMALL_GROUP", parameters);
	}

	public void deleteSmallGroup(long sysSmallGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSmallGroupId", sysSmallGroupId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_DEL_SMALL_GROUP", parameters);
	}
}
