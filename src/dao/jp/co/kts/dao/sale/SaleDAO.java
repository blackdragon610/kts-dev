package jp.co.kts.dao.sale;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.SalesItemDTO;
import jp.co.kts.app.common.entity.SalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.ExportSaleSummalyDTO;
import jp.co.kts.app.output.entity.SaleListTotalDTO;
import jp.co.kts.app.output.entity.StoreDTO;
import jp.co.kts.app.output.entity.SysSaleItemIDDTO;
import jp.co.kts.app.output.entity.SysSalesSlipIdDTO;
import jp.co.kts.app.search.entity.SaleCostSearchDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;

public class SaleDAO extends BaseDAO {

	/**
	 * 新規登録処理
	 * @param salesSlipDTO
	 * @return
	 * @throws DaoException
	 */
	public int registrySaleSlip(SalesSlipDTO salesSlipDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(salesSlipDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("INS_SALES_SLIP", parameters);
	}

	public void registrySaleItem(SalesItemDTO salesItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(salesItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_SALES_ITEM", parameters);
	}

	// public List<ExtendSalesSlipDTO> getSearchSalesSlipList(SaleSearchDTO dto)
	// throws DaoException {
	//
	// SQLParameters parameters = new SQLParameters();
	// addParametersFromBeanProperties(dto, parameters);
	//
	// parameters.addParameter("getListFlg", "1");
	//
	// return selectList("SEL_SEARCH_SALES_SLIP", parameters,
	// ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSalesSlipDTO.class));
	// }

	public ExtendSalesSlipDTO getSearchSalesSlip(SaleSearchDTO saleSearchDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(saleSearchDTO, parameters);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_SEARCH_SALES_SLIP", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSalesSlipDTO.class));
	}

	public List<ExtendSalesSlipDTO> getExportSaleSearchList(SaleSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo",
					createLikeWord(dto.getOrderNo(), true, false));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getSlipNo())) {
			parameters.addParameter("slipNo",
					createLikeWord(dto.getSlipNo(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getOrderNm())) {
			parameters.addParameter("orderNm",
					createLikeWord(dto.getOrderNm(), true, true));
			// parameters.addParameter("orderNmHarf",
			// createLikeWord(StringUtil.toHalfKatakana(dto.getOrderNm()), true,
			// true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderTel())) {
			parameters.addParameter("orderTel",
					createLikeWord(dto.getOrderTel(), true, false));
		}

		// 他社商品情報
		if (StringUtils.isNotBlank(dto.getSalesItemCode())) {
			parameters.addParameter("salesItemCode",
					createLikeWord(dto.getSalesItemCode(), true, false));
		}

		if (StringUtils.isNotBlank(dto.getSalesItemNm())) {
			parameters.addParameter("salesItemNm",
					createLikeWord(dto.getSalesItemNm(), true, true));
		}

		// マスタから検索される場合フラグセット
		if (StringUtils.isNotBlank(dto.getItemCodeFrom())
				|| StringUtils.isNotBlank(dto.getItemCodeTo())
				|| StringUtils.isNotBlank(dto.getItemCode())
				|| StringUtils.isNotBlank(dto.getItemNm())) {
			parameters.addParameter("itemCodeAreaFlg", "1");
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("itemCode",
					createLikeWord(dto.getItemCode(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeFrom())) {
			parameters.addParameter("itemCodeFrom",
					Long.parseLong(dto.getItemCodeFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeTo())) {
			parameters.addParameter("itemCodeTo",
					Long.parseLong(dto.getItemCodeTo()));
		}

		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm",
					createLikeWord(dto.getItemNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getDestinationNm())) {
			parameters.addParameter("destinationNm",
					createLikeWord(dto.getDestinationNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderMailAddress())) {
			parameters.addParameter("orderMailAddress",
					createLikeWord(dto.getOrderMailAddress(), true, true));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getDestinationTel())) {
			parameters.addParameter("destinationTel",
					createLikeWord(dto.getDestinationTel(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceFrom())) {
			parameters.addParameter("sumClaimPriceFrom",
					Integer.parseInt(dto.getSumClaimPriceFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceTo())) {
			parameters.addParameter("sumClaimPriceTo",
					Integer.parseInt(dto.getSumClaimPriceTo()));
		}

		if (StringUtils.isNotEmpty(dto.getMemo())) {
			parameters.addParameter("memo",
					createLikeWord(dto.getMemo(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getSortFirst())) {
			parameters.addParameter("sortValue", dto.getSortFirst());
		}

		if (StringUtils.isNotEmpty(dto.getSortFirstSub())) {
			parameters.addParameter("sortOrder", dto.getSortFirstSub());
		}

		// if (StringUtils.isNotEmpty(dto.getListPageMax())) {
		// parameters.addParameter("listPageMax", dto.getListPageMax());
		// }

		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_EXCEL_SEARCH_SALES_SLIP", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSalesSlipDTO.class));
	}

	public List<SysSalesSlipIdDTO> getSearchSalesSlipList(HttpSession session , SaleSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo",
					createLikeWord(dto.getOrderNo(), true, false));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getSlipNo())) {
			parameters.addParameter("slipNo",
					createLikeWord(dto.getSlipNo(), true, false));
		}

		// if (StringUtils.equals(dto.getSlipNoExist(), "1")) {
		// parameters.addParameter("slipNoExist", dto.getSlipNoExist());
		// }

		// if (StringUtils.equals(dto.getSlipNoHyphen(), "1")) {
		// parameters.addParameter("slipNoHyphen", "-");
		// }

		// if (StringUtils.equals(dto.getSlipNoNone(), "1")) {
		// parameters.addParameter("slipNoNone", dto.getSlipNoNone());
		// }

		if (StringUtils.isNotEmpty(dto.getOrderNm())) {
			parameters.addParameter("orderNm",
					createLikeWord(dto.getOrderNm(), true, true));
			// parameters.addParameter("orderNmHarf",
			// createLikeWord(StringUtil.toHalfKatakana(dto.getOrderNm()), true,
			// true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderTel())) {
			parameters.addParameter("orderTel",
					createLikeWord(dto.getOrderTel(), true, false));
		}

		// 他社商品情報
		if (StringUtils.isNotBlank(dto.getSalesItemCode())) {
			parameters.addParameter("salesItemCode",
					createLikeWord(dto.getSalesItemCode(), true, false));
		}

		if (StringUtils.isNotBlank(dto.getSalesItemNm())) {
			parameters.addParameter("salesItemNm",
					createLikeWord(dto.getSalesItemNm(), true, true));
		}

		// マスタから検索される場合フラグセット
		if (StringUtils.isNotBlank(dto.getItemCodeFrom())
				|| StringUtils.isNotBlank(dto.getItemCodeTo())
				|| StringUtils.isNotBlank(dto.getItemCode())
				|| StringUtils.isNotBlank(dto.getItemNm())) {
			parameters.addParameter("itemCodeAreaFlg", "1");
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("itemCode",
					createLikeWord(dto.getItemCode(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeFrom())) {
			parameters.addParameter("itemCodeFrom",
					Long.parseLong(dto.getItemCodeFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeTo())) {
			parameters.addParameter("itemCodeTo",
					Long.parseLong(dto.getItemCodeTo()));
		}

		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm",
					createLikeWord(dto.getItemNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getDestinationNm())) {
			parameters.addParameter("destinationNm",
					createLikeWord(dto.getDestinationNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderMailAddress())) {
			parameters.addParameter("orderMailAddress",
					createLikeWord(dto.getOrderMailAddress(), true, true));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getDestinationTel())) {
			parameters.addParameter("destinationTel",
					createLikeWord(dto.getDestinationTel(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceFrom())) {
			parameters.addParameter("sumClaimPriceFrom",
					Integer.parseInt(dto.getSumClaimPriceFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceTo())) {
			parameters.addParameter("sumClaimPriceTo",
					Integer.parseInt(dto.getSumClaimPriceTo()));
		}

		if (StringUtils.isNotEmpty(dto.getMemo())) {
			parameters.addParameter("memo",
					createLikeWord(dto.getMemo(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getSortFirst())) {
			parameters.addParameter("sortValue", dto.getSortFirst());
		}

		if (StringUtils.isNotEmpty(dto.getSortFirstSub())) {
			parameters.addParameter("sortOrder", dto.getSortFirstSub());
		}

		// if (StringUtils.isNotEmpty(dto.getListPageMax())) {
		// parameters.addParameter("listPageMax", dto.getListPageMax());
		// }

		parameters.addParameter("getListFlg", "1");
		
		
// ################ it's unknown speed down error #################
// ### so that  		SysSalesSlipIdDTO => ExtendSalesSlipDTO ###
//		return selectList("SEL_SEARCH_SALES_SLIP", parameters,
//		ResultSetHandlerFactory
//				.getNameMatchBeanRowHandler(SysSalesSlipIdDTO.class));
		List<ExtendSalesSlipDTO> result = selectList("SEL_SEARCH_SALES_SLIP", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(ExtendSalesSlipDTO.class));
		List<SysSalesSlipIdDTO> rtn = new ArrayList<>();
		long slipId = 0;
		for(int i=0; i<result.size(); i++)
		{
			
			SysSalesSlipIdDTO one = new SysSalesSlipIdDTO();
			one.setSysSalesSlipId(result.get(i).getSysSalesSlipId());
			if(slipId != result.get(i).getSysSalesSlipId()) {
				slipId = result.get(i).getSysSalesSlipId();
				rtn.add(one);
			}
		}

		//検索結果をセッションとして保持
		session.setAttribute("getSearchSalesSlipList(SaleSearchDTO)", result );
		return rtn;
	}

	public List<ExportSaleSummalyDTO> getExportSaleSummay(SaleSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("orderDateFrom", dto.getOrderDateFrom());
		parameters.addParameter("orderDateTo", dto.getOrderDateTo());

		parameters.addParameter("shipmentPlanDateFrom", dto.getShipmentPlanDateFrom());
		parameters.addParameter("shipmentPlanDateTo", dto.getShipmentPlanDateTo());

		parameters.addParameter("sysCorporationId", dto.getSysCorporationId());
		parameters.addParameter("sysChannelId", dto.getSysChannelId());

		parameters.addParameter("disposalRoute", dto.getDisposalRoute());

		if (dto.getItemCodeAreaFlg() != null) {
			// 他社製品から検索される場合
			if(dto.getItemCodeAreaFlg().equals("1")){
			parameters.addParameter("itemCodeAreaFlg", "1");
			// 自社製品から検索される場合
			} else if (dto.getItemCodeAreaFlg().equals("1")
					&& StringUtils.isNotBlank(dto.getItemCodeFrom()) || StringUtils.isNotBlank(dto.getItemCodeTo())
					|| StringUtils.isNotBlank(dto.getItemCode()) || StringUtils.isNotBlank(dto.getItemNm())) {
				parameters.addParameter("itemCodeAreaFlg", "0");
			}
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("itemCode",
					createLikeWord(dto.getItemCode(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeFrom())) {
			parameters.addParameter("itemCodeFrom",
					Long.parseLong(dto.getItemCodeFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeTo())) {
			parameters.addParameter("itemCodeTo",
					Long.parseLong(dto.getItemCodeTo()));
		}

		if (StringUtils.isNotEmpty(dto.getItemNm())) {

			parameters.addParameter("itemNm",
					createLikeWord(dto.getItemNm(), true, true));
			parameters.addParameter(
					"itemNmHalf",
					createLikeWord(StringUtil.toHalfKatakana(dto.getItemNm()),
							true, true));
			parameters.addParameter(
					"itemNmUpper",
					createLikeWord(StringUtil.toUpperKatakana(dto.getItemNm()),
							true, true));
		}

		parameters.addParameter("sortFirst", dto.getSortFirst());
		parameters.addParameter("sortFirstSub", dto.getSortFirstSub());

		return selectList("SEL_EXCEL_SALE_SUMMALY", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExportSaleSummalyDTO.class));
	}

	public SaleListTotalDTO getSaleListTotalDTO(SaleSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo",
					createLikeWord(dto.getOrderNo(), true, false));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getSlipNo())) {
			parameters.addParameter("slipNo",
					createLikeWord(dto.getSlipNo(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getOrderNm())) {
			parameters.addParameter("orderNm",
					createLikeWord(dto.getOrderNm(), true, true));
			// parameters.addParameter("orderNmHarf",
			// createLikeWord(StringUtil.toHalfKatakana(dto.getOrderNm()), true,
			// true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderTel())) {
			parameters.addParameter("orderTel",
					createLikeWord(dto.getOrderTel(), true, false));
		}

		// 他社商品情報
		if (StringUtils.isNotBlank(dto.getSalesItemCode())) {
			parameters.addParameter("salesItemCode",
					createLikeWord(dto.getSalesItemCode(), true, false));
		}

		if (StringUtils.isNotBlank(dto.getSalesItemNm())) {
			parameters.addParameter("salesItemNm",
					createLikeWord(dto.getSalesItemNm(), true, true));
		}

		// マスタから検索される場合フラグセット
		if (StringUtils.isNotBlank(dto.getItemCodeFrom())
				|| StringUtils.isNotBlank(dto.getItemCodeTo())
				|| StringUtils.isNotBlank(dto.getItemCode())
				|| StringUtils.isNotBlank(dto.getItemNm())) {
			parameters.addParameter("itemCodeAreaFlg", "1");
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("itemCode",
					createLikeWord(dto.getItemCode(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeFrom())) {
			parameters.addParameter("itemCodeFrom",
					Long.parseLong(dto.getItemCodeFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeTo())) {
			parameters.addParameter("itemCodeTo",
					Long.parseLong(dto.getItemCodeTo()));
		}

		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm",
					createLikeWord(dto.getItemNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getDestinationNm())) {
			parameters.addParameter("destinationNm",
					createLikeWord(dto.getDestinationNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderMailAddress())) {
			parameters.addParameter("orderMailAddress",
					createLikeWord(dto.getOrderMailAddress(), true, true));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getDestinationTel())) {
			parameters.addParameter("destinationTel",
					createLikeWord(dto.getDestinationTel(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceFrom())) {
			parameters.addParameter("sumClaimPriceFrom",
					Integer.parseInt(dto.getSumClaimPriceFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceTo())) {
			parameters.addParameter("sumClaimPriceTo",
					Integer.parseInt(dto.getSumClaimPriceTo()));
		}

		if (StringUtils.isNotEmpty(dto.getSlipMemo())) {
			parameters.addParameter("slipMemo",
					createLikeWord(dto.getSlipMemo(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getSortFirst())) {
			parameters.addParameter("sortValue", dto.getSortFirst());
		}

		if (StringUtils.isNotEmpty(dto.getSortFirstSub())) {
			parameters.addParameter("sortOrder", dto.getSortFirstSub());
		}

		return select("SEL_SEARCH_SALES_SLIP_TOTAL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(SaleListTotalDTO.class));
	}

	public ExtendSalesSlipDTO getSaleSlip(ExtendSalesSlipDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_SALES_SLIP", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSalesSlipDTO.class));
	}

	public List<ExtendSalesItemDTO> getSalesItemList(ExtendSalesItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_SALES_ITEM_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSalesItemDTO.class));
	}

	public int updateSalesSlip(ExtendSalesSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_SALES_SLIP", parameters);
	}

	public int updateSalesSlipTransAndSlipnoByOrderno(String strTransportCorporationSystem, String strSlipNo, String strOrderNo) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("OrderNo", strOrderNo);
		parameters.addParameter("SlipNo", strSlipNo);
		parameters.addParameter("TransportCorporationSystem", strTransportCorporationSystem);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_SALES_SLIP_TRANS_SLIPNO_BY_ORDERNO", parameters);
	}

	public int updateSalesSlipPostage(ExtendSalesSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return update("UPD_SALES_SLIP_POSTAGE", parameters);
	}

	public void updateSalesItem(ExtendSalesItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		//システムIDがある場合は商品コードはTBに格納されているため、登録はしない
		if (dto.getSysItemId() != 0) {
			parameters.addParameter("itemCode", StringUtils.EMPTY);
		}

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SALES_ITEM", parameters);
	}

	/**
	 * 売上伝票更新処理
	 * @param dto
	 * @throws DaoException
	 */
	public void updateSalesSlipFlg(ExtendSalesSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		if (StringUtils.isNotEmpty(dto.getPickingListFlg())) {
			parameters.addParameter("pickingListFlg", dto.getPickingListFlg());
		}

		if (StringUtils.isNotEmpty(dto.getLeavingFlg())) {
			parameters.addParameter("leavingFlg", dto.getLeavingFlg());
		}

		if (StringUtils.isNotEmpty(dto.getReturnFlg())) {
			parameters.addParameter("returnFlg", dto.getReturnFlg());
		}

		/** 20140521 Takakuwa 出庫時に現在の日付を出荷日として登録する処理を追加 */
		parameters.addParameter("shipmentDate", dto.getShipmentDate());

		parameters.addParameter("deliveryRemarks", dto.getDeliveryRemarks());

		parameters.addParameter("sysSalesSlipId", dto.getSysSalesSlipId());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SALES_SLIP_FLG", parameters);
	}

	public ExtendSalesItemDTO getSalesItemDTO(ExtendSalesItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_SALES_ITEM", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSalesItemDTO.class));
	}

	// テーブルの一部更新（パーツを更新する用のSQL）
	public int updateAddSlipNo(long sysSalesSlipId, String slipNo)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSalesSlipId", sysSalesSlipId);
		parameters.addParameter("slipNo", slipNo);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_SALES_SLIP_PARTS", parameters);
	}

	public void deleteSalesSlip(long sysSalesSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSalesSlipId", sysSalesSlipId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SALES_SLIP_PARTS", parameters);
	}

	public void deleteSalesItem(long sysSalesItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSalesItemId", sysSalesItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SALES_ITEM_PARTS", parameters);
	}

	public StoreDTO selectStoreInfo(long sysCorporationId, long sysChannelId)
			throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("sysChannelId", sysChannelId);
		return select("SEL_STORE_INFO", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(StoreDTO.class));

	}

	/*  2015/12/22 ooyama ADD START 法人間請求書機能対応  */
	/**
	 * 売上原価一覧のシステム売上商品IDを取得する。
	 * @param dto
	 * @return List<SysSaleItemIDDTO>
	 * @throws DaoException
	 */
	public List<SysSaleItemIDDTO> getSearchSysSaleItemIDList(SaleSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo",
					createLikeWord(dto.getOrderNo(), true, true));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getSlipNo())) {
			parameters.addParameter("slipNo",
					createLikeWord(dto.getSlipNo(), true, false));
		}


		if (StringUtils.isNotEmpty(dto.getOrderNm())) {
			parameters.addParameter("orderNm",
					createLikeWord(dto.getOrderNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderTel())) {
			parameters.addParameter("orderTel",
					createLikeWord(dto.getOrderTel(), true, false));
		}

		// 他社商品情報
		if (StringUtils.isNotBlank(dto.getSalesItemCode())) {
			parameters.addParameter("salesItemCode",
					createLikeWord(dto.getSalesItemCode(), true, true));
		}

		if (StringUtils.isNotBlank(dto.getSalesItemNm())) {
			String temp = dto.getSalesItemNm();
			String[] tempArray = temp.split(",");
			
			String sql="";
			int tempFlag = 0;
			for(String item : tempArray) {
				if(tempFlag != 0) {
					sql = sql + "' OR ";
					sql = sql + "S_ITEM.ITEM_NM ILIKE '" + createLikeWord(item, true, true) ;
				}else {
					sql = createLikeWord(item, true, true);
				}

				tempFlag++;
			}
			parameters.addParameter("salesItemNm", sql);
		}

		// マスタから検索される場合フラグセット
		if (StringUtils.isNotBlank(dto.getItemCodeFrom())
				|| StringUtils.isNotBlank(dto.getItemCodeTo())
				|| StringUtils.isNotBlank(dto.getItemCode())
				|| StringUtils.isNotBlank(dto.getItemNm())) {
			parameters.addParameter("itemCodeAreaFlg", "1");
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("itemCode",
					createLikeWord(dto.getItemCode(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeFrom())) {
			parameters.addParameter("itemCodeFrom",
					Long.parseLong(dto.getItemCodeFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getItemCodeTo())) {
			parameters.addParameter("itemCodeTo",
					Long.parseLong(dto.getItemCodeTo()));
		}

		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm",
					createLikeWord(dto.getItemNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getDestinationNm())) {
			parameters.addParameter("destinationNm",
					createLikeWord(dto.getDestinationNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getOrderMailAddress())) {
			parameters.addParameter("orderMailAddress",
					createLikeWord(dto.getOrderMailAddress(), true, true));
		}

		// 前方一致
		if (StringUtils.isNotEmpty(dto.getDestinationTel())) {
			parameters.addParameter("destinationTel",
					createLikeWord(dto.getDestinationTel(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceFrom())) {
			parameters.addParameter("sumClaimPriceFrom",
					Integer.parseInt(dto.getSumClaimPriceFrom()));
		}

		if (StringUtils.isNotEmpty(dto.getSumClaimPriceTo())) {
			parameters.addParameter("sumClaimPriceTo",
					Integer.parseInt(dto.getSumClaimPriceTo()));
		}

		if (StringUtils.isNotEmpty(dto.getMemo())) {
			parameters.addParameter("memo",
					createLikeWord(dto.getMemo(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getSortFirst())) {
			parameters.addParameter("sortValue", dto.getSortFirst());
		}

		if (StringUtils.isNotEmpty(dto.getSortFirstSub())) {
			parameters.addParameter("sortOrder", dto.getSortFirstSub());
		}
		
		parameters.addParameter("orderType", dto.getOrderType());

		if (StringUtils.isNotEmpty(dto.getOrderContent())) {
			parameters.addParameter("orderContent", createLikeWord(dto.getOrderContent(), true, true));
		}

		parameters.addParameter("telType", dto.getTelType());

		if (StringUtils.isNotEmpty(dto.getTelContent())) {
			parameters.addParameter("telContent", createLikeWord(dto.getTelContent(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getWholseSalerName())) {
			parameters.addParameter("wholseSalerName", createLikeWord(dto.getWholseSalerName(), true, true));
		}

		
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_SEARCH_SYS_SALE_ITEM", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(SysSaleItemIDDTO.class));
	}
	/**
	 * 売上原価情報を取得する。
	 * @param dto
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public ExtendSalesItemDTO getSearchSalesCost(SaleCostSearchDTO costSearchDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(costSearchDTO, parameters);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_SEARCH_SALES_COST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSalesItemDTO.class));
	}

	/**
	 * 売上原価情報を更新する。
	 * @param ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public void updateSalesCost(ExtendSalesItemDTO saleItemDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(saleItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SALES_ITEM_COST", parameters);
	}

	/**
	 * 送料情報を更新する。
	 * @param ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public void updateSalesPostage(ExtendSalesItemDTO saleItemDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(saleItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SALES_ITEM_POSTAGE", parameters);
	}

	/**
	 * 品番から直近の売上原価情報を取得する。
	 * @param ExtendSalesItemDTO
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public ExtendSalesItemDTO getLatestSaleCost(
			ExtendSalesItemDTO saleItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(saleItemDTO, parameters);

		return select("SEL_SEARCH_LATEST_SALES_COST", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(ExtendSalesItemDTO.class));
	}

	/**
	 * 法人と売上月指定で売上情報を取得する。
	 * @param String 売上月
	 * @param long 法人ID
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public List<ExtendSalesItemDTO> getSaleCostBtobBill(
			String exportMonth, long exportSysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("shipmentDate",
				createLikeWord(exportMonth, true, false));

		return selectList("SEL_SEARCH_SALE_COST_BTOBILL", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(ExtendSalesItemDTO.class));
	}


	/**
	 * 法人と売上月指定で業販売上情報を取得する。
	 * @param String 売上月
	 * @param long 法人ID
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public List<ExtendSalesItemDTO> getSaleCostCorporateBill(
			String exportMonth, long exportSysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("shipmentDate",
				createLikeWord(exportMonth, true, false));

		return selectList("SEL_SEARCH_SALE_COST_CORPBILL", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(ExtendSalesItemDTO.class));
	}

	public void registrySaleItemKindCost(SalesItemDTO salesItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(salesItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_SALES_ITEM_KIND", parameters);
	}

	/**
	 * システム売上伝票IDで該当の商品の原価チェックフラグを更新する。
	 * @param long システム売上伝票ID
	 * @param String 原価チェックフラグ
	 * @throws DaoException
	 */
	public void updateSaleItemCostCheckFlag(long sysSaleSlipId, String costCheckFlag) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysSaleSlipId", sysSaleSlipId);
		parameters.addParameter("costCheckFlag", costCheckFlag);
		update("UPD_SALE_COST_CHECK_FLAG", parameters);
	}
	/*  2015/12/22 ooyama ADD END 法人間請求書機能対応  */

	/* BONCRE-1992 売上の受注番号の重複を防ぐ */
	/**
	 * 売上伝票の受注番号の重複を検索
	 * @param saleSearchDTO
	 * @return
	 * @throws DaoException
	 */
	public String getSearchSalesSlipOrderNo(String orderNo, String originOrderNo)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("orderNo", orderNo);
		parameters.addParameter("originOrderNo", originOrderNo);

		 String result = select("SALES_SLIP_ORDER_NO", parameters,
				ResultSetHandlerFactory
						.getFirstColumnStringRowHandler());

		 return result;
	}
}
