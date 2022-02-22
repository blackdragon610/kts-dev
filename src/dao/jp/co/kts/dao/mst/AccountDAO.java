package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstAccountDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstAccountDTO;

public class AccountDAO extends BaseDAO {

	public void registryAccount(MstAccountDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_MST_ACCOUNT", parameters);
	}

	public ExtendMstAccountDTO getAccount(long sysAccountId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysAccountId", sysAccountId);
		parameters.addParameter("sysCorporationId", "0");

		return select("SEL_MST_ACCOUNT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstAccountDTO.class));
	}

	public void updateAccount(MstAccountDTO dto, int listCount) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("priorFlagRemove", 0);

		//優先度が他口座に設定されていない場合の処理
		if (listCount == 0) {
			parameters.addParameter("priorFlag", 1);
		}

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_MST_ACCOUNT", parameters);
	}

	public void deleteAccount(long sysAccountId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysAccountId", sysAccountId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_MST_ACCOUNT", parameters);
	}

	public List<ExtendMstAccountDTO> getAccountList(long sysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysAccountId", "0");
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return selectList("SEL_MST_ACCOUNT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstAccountDTO.class));
	}

	public long cheackSameLoginCd(String loginCd,long sysUserId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("loginCd", loginCd);

		if (sysUserId != 0){
			parameters.addParameter("sysUserId", sysUserId);
		}
		return select("SEL_CHECK_SAME_LOGIN_CD", parameters, ResultSetHandlerFactory.getFirstColumnLongRowHandler());
	}

	/**
	 * 優先度の変更
	 * @param dto
	 * @throws DaoException
	 */
	public void updatePrior(ExtendMstAccountDTO dto, int listCount) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		//優先度が他に存在していれば優先度フラグを削除
		if (listCount == 1) {
			parameters.addParameter("priorFlagRemove", 1);
		} else {
			parameters.addParameter("priorFlagRemove", 0);
			parameters.addParameter("priorFlag", 1);
		}

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_MST_ACCOUNT", parameters);
	}

}
