package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstGroupNmDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstGroupNmDTO;

public class GroupNmDAO extends BaseDAO {

	public List<ExtendMstGroupNmDTO> getGroupNmList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_GROUP_NM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstGroupNmDTO.class));
	}

	public MstGroupNmDTO getGroupNm(long sysGroupNmId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysGroupNmId", sysGroupNmId);

		return select("SEL_GROUP_NM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstGroupNmDTO.class));
	}

	public void updateGroupNm(MstGroupNmDTO groupNmDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(groupNmDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_GROUP_NM", parameters);
	}

	public void deleteGroupNm(long sysGroupNmId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysGroupNmId", sysGroupNmId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_GROUP_NM", parameters);
	}

	public void registryGroupNm(MstGroupNmDTO groupNmDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(groupNmDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_GROUP_NM", parameters);
	}
}
