package jp.co.kts.dao.btobBill;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.BtobBillDTO;
import jp.co.kts.app.common.entity.BtobBillItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendBtobBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;

/**
 * 法人間請求書機能DAOクラス
 * 業務ロジックにて使用されるデータアクセスを提供する。
 *
 * 作成日　：2015/12/15
 * 作成者　：大山智史
 * 更新日　：
 * 更新者　：
 *
 */

public class BtobBillDAO extends BaseDAO {

	/**
	 * 法人間請求書一覧の請求書リストを取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public List<BtobBillDTO> getSearchSysBtobBillIdList(long sysCorporationId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);

		return selectList("SEL_SEARCH_SYS_BTOBILL_ID_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(BtobBillDTO.class));
	}

	/**
	 * 法人間請求書データを取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public ExtendBtobBillDTO getSearchBtobBill(long sysBtobBillId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysBtobBillId", sysBtobBillId);

		return select("SEL_SEARCH_BTOBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendBtobBillDTO.class));
	}

	/**
	 * 法人間請求書を更新する。
	 * @param BtobBillDTO
	 * @throws DaoException
	 */
	public void updateBtobBill(ExtendBtobBillDTO btobBillDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(btobBillDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_BTOB_BILL", parameters);
	}

	/**
	 * 法人間請求書を削除する。
	 * @param BtobBillDTO
	 * @throws DaoException
	 */
	public void deleteBtobBill(ExtendBtobBillDTO btobBillDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(btobBillDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("deleteFlg", "1");
		update("UPD_BTOB_BILL", parameters);
	}

	/**
	 * 法人間請求書商品を削除する。
	 * @param BtobBillDTO
	 * @throws DaoException
	 */
	public void deleteBtobBillItem(ExtendBtobBillDTO btobBillDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(btobBillDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("deleteFlg", "1");
		update("UPD_DEL_BTOB_BILL_ITEM", parameters);
	}

	/**
	 * 法人間請求書の商品情報リストを取得する。
	 * @param sysBtobBillId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public List<BtobBillItemDTO> getSearchBtobBillItemList(long sysBtobBillId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysBtobBillId", sysBtobBillId);

		return selectList("SEL_SEARCH_BTOBILL_ITEM_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(BtobBillItemDTO.class));
	}

	/**
	 * BONCRE- 指定した法人の間請求書の商品情報リストをすべて取得する。
	 * @param sysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<SysCorporateSalesSlipIdDTO> getSearchBtobBillItemAllList(long sysCorporationId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);

		return selectList("SEL_SEARCH_BTOBILL_ITEM_ALL_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(SysCorporateSalesSlipIdDTO.class));
	}

	/**
	 * 指定した請求月の法人間請求書データを1件取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public ExtendBtobBillDTO getSearchDemandMonthBtobBill(long sysCorporationId, String demandMonth)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("demandMonth", demandMonth);

		return select("SEL_SEARCH_DEMAND_MONTH_BTOBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendBtobBillDTO.class));
	}

	/**
	 * 指定した請求月と法人の請求書データを取得する
	 * @param demandMonth
	 * @param sysCorporationId
	 * @return List<ExtendBtobBillDTO>
	 * @throws DaoException
	 */
	public List<BtobBillDTO> getSearchBtobBillList(String demandMonth, long sysCorporationId)
			throws DaoException{

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("demandMonth", demandMonth);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return selectList("SEL_SEARCH_BTOBILL_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(BtobBillDTO.class));
	}

	/**
	 * 法人間請求書商品データを登録する。
	 * @param BtobBillItemDTO
	 * @throws DaoException
	 */
	public void registryBtobBillItem(BtobBillItemDTO itemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_BTOB_BILL_ITEM", parameters);
	}

	/**
	 * 請求月内番号の最大数を取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public String getSearchMaxDemandMonthNo(long sysCorporationId, String demandMonth)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("demandMonth", demandMonth);

		return select("SEL_SEARCH_MAX_DEMAND_MONTH_NO", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(BtobBillDTO.class)).getDemandMonthNo();
	}

	/**
	 * 法人間請求書商品から合計金額を取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public int getSearchCalcSumPriceRate(long sysBtobBillId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysBtobBillId", sysBtobBillId);

		return select("SEL_SEARCH_CALC_SUM_PIECE_RATE", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(BtobBillDTO.class)).getSumPieceRate();


	}

	/**
	 * 法人間請求書商品から税率ごとの合計金額を取得する。
	 * @param sysCorporationId
	 * @return List<BtobBillDTO>
	 * @throws DaoException
	 */
	public int getSearchCalcSumPriceRate(long sysBtobBillId, int taxRate)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysBtobBillId", sysBtobBillId);
		parameters.addParameter("taxRate", taxRate);

		return select("SEL_SEARCH_CALC_SUM_PIECE_RATE_OF_TAX", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(BtobBillDTO.class)).getSumPieceRate();


	}

	/**
	 * 法人間請求書データを登録する。
	 * @param BtobBillDTO
	 * @throws DaoException
	 */
	public void registryBtobBill(BtobBillDTO billDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(billDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_BTOB_BILL", parameters);
	}


	/**
	 * 法人間請求書IDで業販kind原価検索
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesKindList(ExtendBtobBillDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return selectList("SEL_CORP_SALES_KIND_COST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	/**
	 * 法人間請求書IDで売上kind原価検索
	 * @param searchDTO
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getSalesKindItemList(ExtendBtobBillDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return selectList("SEL_SALES_KIND_COST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}
}
