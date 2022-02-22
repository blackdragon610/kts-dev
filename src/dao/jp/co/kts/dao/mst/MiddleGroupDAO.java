package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstMiddleGroupDTO;
import jp.co.kts.app.extendCommon.entity.VariousGroupDTO;

public class MiddleGroupDAO extends BaseDAO {

	public List<MstMiddleGroupDTO> getMiddleGroupList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_MIDDLE_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstMiddleGroupDTO.class));
	}

	public MstMiddleGroupDTO getMiddleGroup(long sysVariousGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysMiddleGroupId", sysVariousGroupId);

		return select("SEL_MIDDLE_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstMiddleGroupDTO.class));
	}

	public void updateGroup(VariousGroupDTO groupDTO) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(groupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_MIDDLE_GROUP", parameters);
	}

	public void deleteGroup(long sysGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysGroupId", sysGroupId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_MIDDLE_GROUP", parameters);
	}

	public void registryMiddleGroup(MstMiddleGroupDTO middleGroupDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(middleGroupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_MIDDLE_GROUP", parameters);
	}

	public void updateMiddleGroup(MstMiddleGroupDTO middleGroupDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(middleGroupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_MIDDLE_GROUP", parameters);
	}

	public void deleteMiddleGroup(long sysMiddleGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysMiddleGroupId", sysMiddleGroupId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_DEL_MIDDLE_GROUP", parameters);
	}
}
