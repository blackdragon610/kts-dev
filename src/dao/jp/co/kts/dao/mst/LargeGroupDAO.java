package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstLargeGroupDTO;
import jp.co.kts.app.extendCommon.entity.VariousGroupDTO;

public class LargeGroupDAO extends BaseDAO {

//	public List<VariousGroupDTO> getLargeGroupList() throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("getListFlg", "1");
//
//		return selectList("SEL_LARGE_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(VariousGroupDTO.class));
//	}

//	public VariousGroupDTO getLargeGroup(long sysVariousGroupId) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("getListFlg", "0");
//		parameters.addParameter("sysLargeGroupId", sysVariousGroupId);
//
//		return select("SEL_LARGE_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(VariousGroupDTO.class));
//	}
	public List<MstLargeGroupDTO> getLargeGroupList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_LARGE_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstLargeGroupDTO.class));
	}

	public MstLargeGroupDTO getLargeGroup(long sysVariousGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysLargeGroupId", sysVariousGroupId);

		return select("SEL_LARGE_GROUP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstLargeGroupDTO.class));
	}

	public void updateGroup(VariousGroupDTO groupDTO) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(groupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_LARGE_GROUP", parameters);
	}

	public void deleteGroup(long sysGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysGroupId", sysGroupId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_LARGE_GROUP", parameters);
	}

	public void registryLargeGroup(MstLargeGroupDTO largeGroupDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(largeGroupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_LARGE_GROUP", parameters);
	}

	public void updateLargeGroup(MstLargeGroupDTO largeGroupDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(largeGroupDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_LARGE_GROUP", parameters);
	}

	public void deleteLargeGroup(long sysLargeGroupId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysLargeGroupId", sysLargeGroupId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_DEL_LARGE_GROUP", parameters);
	}
}
