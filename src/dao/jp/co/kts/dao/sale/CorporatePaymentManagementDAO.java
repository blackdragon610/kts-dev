package jp.co.kts.dao.sale;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.extendCommon.entity.ExtendPaymentManagementDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;

/**
 * 業販入金管理情報テーブルDAOクラス
 *
 * 作成日　：2017/12/01
 * 作成者　：須田将規
 */
public class CorporatePaymentManagementDAO extends BaseDAO {

	/**
	 * 入金管理情報のIDリストを取得します
	 * @param sysCorporationId
	 * @return
	 */
	public List<ExtendPaymentManagementDTO> getSysPaymentManageIdList(long sysCorporationId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);

		return selectList("SEL_SEARCH_SYS_PAYMENT_MANAGE_ID_LIST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendPaymentManagementDTO.class));

	}

	/**
	 * 検索条件に一致する請求書の一覧を取得する
	 *
	 * @author mSuda
	 * @param searchDTO
	 * @return
	 * @exception DaoException
	 */
	public List<ExtendPaymentManagementDTO> getSysPaymentManageIdList(CorporateSaleSearchDTO searchDTO)
			throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(searchDTO, parameters);

		//請求月From～To
		parameters.addParameter("demandMonthFrom", searchDTO.getSearchExportMonthFrom());
		parameters.addParameter("demandMonthTo", searchDTO.getSearchExportMonthTo());

		//法人ID
		parameters.addParameter("sysCorporationId", searchDTO.getSearchSysCorporationId());

		//得意先番号：部分一致
		if (!StringUtils.isBlank(searchDTO.getClientNo())) {
			parameters.addParameter("clientNo", createLikeWord(searchDTO.getClientNo(), true, true));
		}

		//得意先名：部分一致
		if (!StringUtils.isBlank(searchDTO.getClientNm())) {
			parameters.addParameter("clientNm", createLikeWord(searchDTO.getClientNm(), true, true));
		}

		//口座でも検索する？

		//請求額From
		if (StringUtils.isNotEmpty(searchDTO.getSearchBillingAmountFrom())) {
			parameters.addParameter("billAmountFrom",
					Integer.parseInt(searchDTO.getSearchBillingAmountFrom()));
		}

		//請求額To
		if (StringUtils.isNotEmpty(searchDTO.getSearchBillingAmountTo())) {
			parameters.addParameter("billAmountTo",
					Integer.parseInt(searchDTO.getSearchBillingAmountTo()));
		}

		//並び順
		parameters.addParameter("sortOrder", searchDTO.getSortFirstSub());

		return selectList("SEL_SEARCH_SYS_PAYMENT_MANAGE_ID_LIST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendPaymentManagementDTO.class));
	}

	/**
	 * <h1>入金管理作成処理、請求書出力処理</h1>
	 * 入金管理情報を取得します
	 * @param sysCorporationId
	 * @param sysClientId
	 * @param sysAccountId
	 * @param demandMonth
	 * @return
	 */
	public ExtendPaymentManagementDTO getPaymentManagement(long sysCorporationId, long sysClientId, long sysAccountId,
			String demandMonth) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("sysClientId", sysClientId);
		parameters.addParameter("sysAccountId", sysAccountId);
		parameters.addParameter("demandMonth", demandMonth);

		return select("SEL_PAYMENT_MANAGEMENT_INFO", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendPaymentManagementDTO.class));

	}

	/**
	 * <h1>入金管理初期表示、ページング処理</h1>
	 * 入金管理情報を取得します
	 * @param sysPaymentManagementId
	 * @return
	 */
	public ExtendPaymentManagementDTO getSearchPaymentManage(long sysPaymentManagementId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysPaymentManagementId", sysPaymentManagementId);

		return select("SEL_PAYMENT_MANAGEMENT_INFO", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendPaymentManagementDTO.class));

	}

	/**
	 * 入金管理情報を新規登録します
	 * @param newPayManageDTO
	 */
	public void registryPaymentManagement(ExtendPaymentManagementDTO newPayManageDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(newPayManageDTO, parameters);

		//登録
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_PAYMENT_MANAGEMENT", parameters);

	}

	/**
	 * 入金管理情報を更新します
	 * @param prePayManageDTO
	 */
	public void updatePaymentManagement(ExtendPaymentManagementDTO prePayManageDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(prePayManageDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_PAYMENT_MANAGEMENT", parameters);

	}

	/**
	 * 入金管理テーブルにおけるシステム入金管理IDの最大値を取得します
	 * @return
	 */
	public int getSequenceOfPaymentManagement()
			throws DaoException {
		return select("SEL_SEQUENCE_OF_SYS_PAYMENT_MANAGEMENT_ID", ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * 入金管理画面の法人と締日で検索する
	 * @param dispSysCorporationId
	 * @param dispCutoffDate
	 * @return List<ExtendPaymentManagementDTO>
	 * @throws DaoException
	 */
	public List<ExtendPaymentManagementDTO> getSearchPaymentManage(long dispSysCorporationId, int dispCutoffDate)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", dispSysCorporationId);
		parameters.addParameter("cutoffDate", dispCutoffDate);

		return selectList("SEL_SEARCH_SYS_PAYMENT_MANAGE_ID_LIST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendPaymentManagementDTO.class));
	}

}
