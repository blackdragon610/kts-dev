package jp.co.kts.dao.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.search.entity.ClientSearchDTO;

public class ClientDAO extends BaseDAO {

	public List<MstClientDTO> getClientList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("getListFlg", "1");
		parameters.addParameter("sysClientId", 0);

		return selectList("SEL_MST_CLIENT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstClientDTO.class));
	}

	public List<MstClientDTO> getCorprationClientList(long sysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("sysClientId", 0);
		parameters.addParameter("paymentMethod", 0);

		return selectList("SEL_EXT_MST_CLIENT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstClientDTO.class));
	}

	public List<ExtendMstClientDTO> getClientList(ClientSearchDTO search) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(search, parameters);
		if (StringUtils.isNotEmpty(search.getClientNm())) {
			parameters.addParameter("clientNm", createLikeWord(search.getClientNm(), true, true));
		}
		if (StringUtils.isNotEmpty(search.getClientNo())){
			parameters.addParameter("clientNo", createLikeWord(search.getClientNo(), true, false));
		}


		return selectList("SEL_EXT_MST_CLIENT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstClientDTO.class));
	}

	public MstClientDTO getClient(long sysClientId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysClientId", sysClientId);

		return select("SEL_MST_CLIENT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstClientDTO.class));
	}

	/**
	 * ?????????????????????????????????????????????
	 * ???????????????????????????????????????<br>
	 * ?????????ID???0???????????????????????????????????????????????????
	 * @param clientBillingNm
	 * @return
	 * @throws DaoException
	 */
	public MstClientDTO getClient(String clientBillingNm)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysClientId", 0);
		parameters.addParameter("clientNm", clientBillingNm);

		return select("SEL_MST_CLIENT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstClientDTO.class));
	}

	public ExtendMstClientDTO getDispClient(long sysClientId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysClientId", sysClientId);
		parameters.addParameter("paymentMethod", 0);
		parameters.addParameter("sysCorporationId", 0);

		return select("SEL_EXT_MST_CLIENT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstClientDTO.class));
	}

	public ExtendMstClientDTO getDispClientName(String clientNm) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("clientNm", clientNm);

		return select("SEL_EXT_MST_CLIENT_NM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstClientDTO.class));
	}

	/**
	 * ?????????(????????????)???????????????ID??????????????????<br>
	 * Brembo?????????????????????????????????
	 * @param sysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public long getSysClientId(String corporationName) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("corporationName", corporationName);
		return select("SEL_CLIENT_ID_FOR_BREMBO_CORPSALESLIP", parameters, ResultSetHandlerFactory.getFirstColumnLongRowHandler());
	}

	/** ?????????billingDst?????????????????????ID??????????????????????????????<br>
	 * ????????????????????????????????????????????????????????????????????????????????????
	 * @param billingDst
	 * @return
	 * @throws DaoException
	 */
	public List<Long> getSysClientIdList(String billingDst) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("billingDst", billingDst);
		return selectList("SEL_CLIENT_ID_FOR_BILLING_DST", parameters, ResultSetHandlerFactory.getFirstColumnLongRowHandler());
	}

//
//	public ExtendMstUserDTO getUserName(long sysUserId) throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("sysUserId", sysUserId);
//
//		return select("SEL_USERNAME", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstUserDTO.class));
//	}
//
	public void updateClient(MstClientDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_MST_CLIENT", parameters);
	}

	public void deleteClient(long sysClientId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysClientId", sysClientId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_MST_CLIENT", parameters);
	}

	public void registryClient(MstClientDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_MST_CLIENT", parameters);
	}

	public int getReceivableBalance(long sysClientId) throws DaoException{

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysClientId", sysClientId);

		return select("SEL_RECEIVABLE_BALANCE",parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public int getReceivableBalance(long sysClientId, String receiveDate) throws DaoException{

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysClientId", sysClientId);
		parameters.addParameter("receiveDate", receiveDate);

		return select("SEL_RECEIVABLE_BALANCE",parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/*
	 * ??????????????????????????????????????????ID????????????
	 */
	public String getClientNmWithName(String clientNm, long sysCorporationId) throws DaoException{

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("clientNm", clientNm);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_CLIENT_ID_WITH_NAME",parameters, ResultSetHandlerFactory.getFirstColumnStringRowHandler());
	}

	/*
	 * ???????????????????????????????????????????????????
	 */
	public int getClientCutoffDate(String clientNm) throws DaoException{

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("clientNm", clientNm);

		return select("SEL_CLIENT_CUTOFF",parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 * @param clientNo ???????????????
	 * @return ?????????(int)
	 * @throws DaoException
	 */
	public int getClientNoCnt(String clientNo) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("clientNo", clientNo);

		return select("CNT_SYS_CLIENT_NO", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 * @param clientNo ???????????????
	 * @return ?????????(int)
	 * @throws DaoException
	 */

	public int getClientNoCnt(String clientNo, long sysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("clientNo", clientNo);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("CNT_SYS_CLIENT_NO", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * ??????????????????ID?????????????????????????????????
	 * @param sysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<MstClientDTO> getClientBillingNm(long sysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);

		return selectList("SEL_CLIENT_BILLING_NM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstClientDTO.class));
	}

}
