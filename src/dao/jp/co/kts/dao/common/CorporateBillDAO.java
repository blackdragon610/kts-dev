package jp.co.kts.dao.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.CorporateBillDTO;
import jp.co.kts.app.common.entity.CorporateBillItemDTO;
import jp.co.kts.app.common.entity.CorporateSalesSlipDTO;
import jp.co.kts.app.common.entity.ExportCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;

/**
 * 業販請求テーブルDAOクラス
 * 表示用格納データへのアクセスを提供する。
 *
 * 作成日　：2016/06/15
 * 作成者　：杉浦望
 * 更新日　：
 * 更新者　：
 *
 */

public class CorporateBillDAO extends BaseDAO {

	/**
	 * 業販請求書一覧の請求書リストを取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public List<CorporateBillDTO> getSearchSysCorporateBillIdList(long sysCorporationId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);

		return selectList("SEL_SEARCH_SYS_CORPBILL_ID_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(CorporateBillDTO.class));
	}

	/**
	 * 業販請求書一覧の請求書リストを取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public List<CorporateBillDTO> getSearchSysCorporateBillIdList(long sysCorporationId, int dispCutoffDate)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("dispCutoffDate", dispCutoffDate);

		return selectList("SEL_SEARCH_SYS_CORPBILL_ID_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(CorporateBillDTO.class));
	}

	/**
	 * 業販請求書データを取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public ExportCorporateBillDTO getSearchCorporateBill(long sysCorporateBillId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporateBillId", sysCorporateBillId);

		return select("SEL_SEARCH_CORPBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExportCorporateBillDTO.class));
	}

	/**
	 * 請求先をもとに前月業販請求書データを取得する。
	 * @param sysCorporationId
	 * @return ExtendCorporateBillDTO
	 * @throws DaoException
	 */
	public ExportCorporateBillDTO getSearchCorporateBill(String clientBillingNm, String demandMonth, long sysAccountId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("clientBillingNm", clientBillingNm);
		parameters.addParameter("demandMonth", demandMonth);
		parameters.addParameter("sysAccountId", sysAccountId);

		return select("SEL_LAST_PRICE_SEARCH_CORPBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExportCorporateBillDTO.class));
	}

	/**
	 * 業販請求書を更新する。
	 * @param CorporateBillDTO
	 * @throws DaoException
	 */
	public void updateCorporateBill(ExtendCorporateBillDTO corporateBillDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporateBillDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_CORP_BILL", parameters);
	}

	/**
	 * 業販請求書を削除する。
	 * @param CorporateBillDTO
	 * @throws DaoException
	 */
	public void deleteCorporateBill(ExtendCorporateBillDTO corporateBillDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporateBillDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("deleteFlg", "1");
		update("UPD_CORP_BILL", parameters);
	}

	/**
	 * 業販請求書商品を削除する。
	 * @param CorporateBillDTO
	 * @throws DaoException
	 */
	public void deleteCorporateBillItem(ExtendCorporateBillDTO corporateBillDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporateBillDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("deleteFlg", "1");
		update("UPD_DEL_CORP_BILL_ITEM", parameters);
	}

	/**
	 * 業販請求書の商品情報リストを取得する。
	 * @param sysCorporateBillId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public List<CorporateBillItemDTO> getSearchCorporateBillItemList(long sysCorporateBillId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporateBillId", sysCorporateBillId);

		return selectList("SEL_SEARCH_CORPBILL_ITEM_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(CorporateBillItemDTO.class));
	}

	/**
	 * 指定した請求月の業販請求書データを1件取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public ExtendCorporateBillDTO getSearchDemandMonthCorporateBill(long sysCorporationId, String demandMonth)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("demandMonth", demandMonth);

		return select("SEL_SEARCH_DEMAND_MONTH_CORPBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateBillDTO.class));
	}

	/**
	 * 指定した請求月の業販請求書データを1件取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public ExtendCorporateBillDTO getSearchDemandMonthCorporateBill(long sysCorporationId, String demandMonth, String clientBillingNm)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("demandMonth", demandMonth);
		parameters.addParameter("clientBillingNm", clientBillingNm);

		return select("SEL_SEARCH_DEMAND_MONTH_CORPBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateBillDTO.class));
	}

	/**
	 * 指定した請求月の業販請求書データを1件取得する。<br>
	 * ※口座も条件に付けくわえた
	 * @param sysCorporationId
	 * @param demandMonth
	 * @param clientBillingNm
	 * @param sysAccountId
	 * @return
	 * @throws DaoException
	 */
	public ExtendCorporateBillDTO getSearchDemandMonthCorporateBill(long sysCorporationId, String demandMonth, String clientBillingNm, long sysAccountId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("demandMonth", demandMonth);
		parameters.addParameter("clientBillingNm", clientBillingNm);
		parameters.addParameter("sysAccountId", sysAccountId);

		return select("SEL_SEARCH_DEMAND_MONTH_CORPBILL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateBillDTO.class));
	}

	/**
	 * 業販請求書商品データを登録する。
	 * @param CorporateBillItemDTO
	 * @throws DaoException
	 */
	public void registryCorporateBillItem(ExtendCorporateSalesItemDTO itemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_CORP_BILL_ITEM", parameters);
	}

	/**
	 * 請求月内番号の最大数を取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public String getSearchMaxDemandMonthNo(long sysCorporationId, String demandMonth)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("demandMonth", demandMonth);

		return select("SEL_SEARCH_CORP_MAX_DEMAND_MONTH_NO", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(CorporateBillDTO.class)).getDemandMonthNo();
	}

	/**
	 * 業販請求書商品から合計金額を取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public int getSearchCalcSumPriceRate(long sysCorporateBillId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporateBillId", sysCorporateBillId);

		return select("SEL_SEARCH_CORP_CALC_SUM_PIECE_RATE", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(CorporateBillDTO.class)).getSumPieceRate();


	}

	/**
	 * 業販請求書データを登録する。
	 * @param CorporateBillDTO
	 * @throws DaoException
	 */
	public void registryCorporateBill(CorporateBillDTO billDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(billDTO, parameters);

		//登録

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_CORP_BILL", parameters);
	}

	/**
	 * 請求先をもとに前月業販請求書データを取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public List<ExportCorporateBillDTO> checkCorporateBill(CorporateBillDTO billDTO, long sysAccountId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("clientCorporationNm", billDTO.getClientCorporationNm());
		parameters.addParameter("clientBillingNm", billDTO.getClientBillingNm());
		parameters.addParameter("demandMonth", billDTO.getDemandMonth());
		parameters.addParameter("sysAccountId", sysAccountId);

		return selectList("CHECK_CORP_BILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExportCorporateBillDTO.class));
	}


	/**
	 * 請求Noをもとに伝票の請求書備考欄を取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public String getBillRemarks(String slipNo)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("slipNo", slipNo);

		return select("CORPBILL_REMARKS", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(CorporateSalesSlipDTO.class)).getBillingRemarks();
	}


	/**
	 * 伝票No.を基に消費税率を取得する。
	 * @param sysCorporationId
	 * @return List<CorporateBillDTO>
	 * @throws DaoException
	 */
	public int getTaxRateOfSlip(String slipNo)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("slipNo", slipNo);

		return select("CORPSLIP_TAXRATE", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(CorporateSalesSlipDTO.class)).getTaxRate();
	}



	/**
	 *検索条件に一致する請求書の一覧を取得する。
	 *
	 *@author gkikuchi
	 *@since 2017/11/14
	 * @param dispSysCorporationId
	 * @param searchExportMonthFrom
	 * @param searchExportMonthTo
	 * @return
	 * @throws DaoException
	 */
	public List<CorporateBillDTO> getSearchCorporateBillItemList(CorporateSaleSearchDTO csSearchDto) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(csSearchDto, parameters);

		/*
		 * テキスト入力項目は部分一致とする。
		 */
		parameters.addParameter("sysCorporationId", csSearchDto.getSearchSysCorporationId());

		// 請求書番号：部分一致
		if (!StringUtils.isBlank(csSearchDto.getCorporateBillNo())) {
			parameters.addParameter("corporateBillNo", createLikeWord(csSearchDto.getCorporateBillNo(), true, true));
		}

		parameters.addParameter("searchExportMonthFrom", csSearchDto.getSearchExportMonthFrom());
		parameters.addParameter("searchExportMonthTo", csSearchDto.getSearchExportMonthTo());

		// 得意先番号：部分一致
		if (!StringUtils.isBlank(csSearchDto.getClientNo())) {
			parameters.addParameter("clientNo", createLikeWord(csSearchDto.getClientNo(), true, true));
		}

		// 得意先名：部分一致（大文字小文字区別なし）
		if (!StringUtils.isBlank(csSearchDto.getClientNm())) {
			parameters.addParameter("clientNm", createLikeWord(csSearchDto.getClientNm(), true, true));
		}

		if (StringUtils.isNotEmpty(csSearchDto.getSearchBillingAmountFrom())) {
			parameters.addParameter("searchBillingAmountFrom",
					Integer.parseInt(csSearchDto.getSearchBillingAmountFrom()));
		}

		if (StringUtils.isNotEmpty(csSearchDto.getSearchBillingAmountTo())) {
			parameters.addParameter("searchBillingAmountTo",
					Integer.parseInt(csSearchDto.getSearchBillingAmountTo()));
		}

		parameters.addParameter("sortOrder", csSearchDto.getSortFirstSub());

		return selectList("SEL_SEARCH_CORPBILL_ID_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(CorporateBillDTO.class));
	}

	/**
	 * 法人、最終請求月、締日で前回請求情報を取得する
	 * @param String 売上月
	 * @param long 法人ID
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public List<ExportCorporateBillDTO> getLastCorpSaleBill(
			String exportLastMonth, long exportSysCorporationId, String selectedCutoff) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("exportMonth",exportLastMonth);
		parameters.addParameter("selectedCutoff",Integer.valueOf(selectedCutoff));

		return selectList("SEL_SEARCH_LAST_CORPBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExportCorporateBillDTO.class));
	}

}
