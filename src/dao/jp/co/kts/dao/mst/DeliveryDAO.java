package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstDeliveryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstDeliveryDTO;

public class DeliveryDAO extends BaseDAO {

	public void registryDelivery(MstDeliveryDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_MST_DELIVERY", parameters);
	}

	public MstDeliveryDTO getDelivery(long sysDeliveryId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDeliveryId", sysDeliveryId);
		parameters.addParameter("sysClientId", "0");

		return select("SEL_MST_DELIVERY", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstDeliveryDTO.class));
	}

	public void updateDelivery(MstDeliveryDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_MST_DELIVERY", parameters);
	}

	public void deleteDelivery(long sysDeliveryId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDeliveryId", sysDeliveryId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_MST_DELIVERY", parameters);
	}

	public List<ExtendMstDeliveryDTO> getDeliveryList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDeliveryId", "0");
		parameters.addParameter("sysClientId", "0");

		return selectList("SEL_MST_DELIVERY", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstDeliveryDTO.class));
	}

	public List<ExtendMstDeliveryDTO> getDeliveryList(long sysClientId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDeliveryId", "0");
		parameters.addParameter("sysClientId", sysClientId);

		return selectList("SEL_MST_DELIVERY", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstDeliveryDTO.class));
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
	 * ［概要］引数で渡された電話番号が同一得意先に既に存在するか、登録数をカウントする。
	 * @param telNo 電話番号
	 * @return 登録数（int）
	 * @throws DaoException
	 */
	public int getTelCnt(String telNo, long sysClientId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("telNo", telNo);
		parameters.addParameter("sysClientId", sysClientId);

		return select("CNT_MST_DELIVERY_TEL", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

}
