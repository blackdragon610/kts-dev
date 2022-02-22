package jp.co.kts.dao.sale;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.CorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.output.entity.ExportSaleSummalyDTO;
import jp.co.kts.app.output.entity.SaleListTotalDTO;
import jp.co.kts.app.output.entity.StoreDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;
import jp.co.kts.app.output.entity.SysCorprateSaleItemIDDTO;
import jp.co.kts.app.search.entity.CorporateSaleCostSearchDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;
import jp.co.kts.service.sale.CorporateReceiveService;

public class CorporateSaleDAO extends BaseDAO {

	//業販伝票基本操作-------------------------------------------------------------------------------------------------------------
	/**
	 * 業販伝票登録
	 * @param corporateSalesSlipDTO
	 * @return
	 * @throws DaoException
	 */
	public int registryCorporateSaleSlip(ExtendCorporateSalesSlipDTO corporateSalesSlipDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporateSalesSlipDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("INS_CORP_SALES_SLIP", parameters);
	}

	/**
	 * 伝票IDから伝票情報取得
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public ExtendCorporateSalesSlipDTO getCorporateSaleSlip(ExtendCorporateSalesSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_CORP_SALES_SLIP", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesSlipDTO.class));
	}

	public List<ExtendCorporateSalesSlipDTO> getCorporateSaleSlipByOrderNo(String orderNo) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("orderNo", orderNo);

		return selectList("SEL_CORP_SALES_SLIP_BY_ORDERNO", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateSalesSlipDTO.class));
	}

	public int updateCorporateSalesSlip(ExtendCorporateSalesSlipDTO dto) throws DaoException, ParseException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);


		parameters.addParameter("receiveDate", new CorporateReceiveService().getCorporateReceiveLatestDate(dto.getSysCorporateSalesSlipId()));

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_CORP_SALES_SLIP", parameters);
	}

	public void deleteCorporateSalesSlip(long sysCorporateSalesSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporateSalesSlipId", sysCorporateSalesSlipId);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_DEL_CORP_SALES_SLIP", parameters);
	}

	//業販商品基本操作-------------------------------------------------------------------------------------------------------------
	/**
	 * 業販商品登録(海外商品用)
	 * @param corporateSalesItemDTO
	 * @throws DaoException
	 */
	public void registryCorporateSaleItem(CorporateSalesItemDTO corporateSalesItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		//パラメータを設定
		addParametersFromBeanProperties(corporateSalesItemDTO, parameters);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_CORP_SALES_ITEM", parameters);
	}

	/**
	 * 業販商品登録(国内商品用)
	 * @param corporateSalesItemDTO
	 * @throws DaoException
	 */
	public void registryCorporateSaleDomesticItem(CorporateSalesItemDTO corporateSalesItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		//パラメータを設定
		addParametersFromBeanProperties(corporateSalesItemDTO, parameters);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_CORP_SALES_DOMESTIC_ITEM", parameters);
	}

	/**
	 * 伝票IDで検索
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemList(ExtendCorporateSalesItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_CORP_SALES_ITEM_LIST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	//商品検索
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemList(ExtendCorporateSalesItemDTO dto, CorporateSaleSearchDTO searchDto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		//商品優先で検索する場合は、条件を追加して商品を検索する
		if (StringUtils.equals(searchDto.getSearchPriority(), "1")) {
			parameters.addParameter("slipStatus", searchDto.getSlipStatus());
		} else if (StringUtils.equals(searchDto.getSearchPriority(), "2")) {
			addParametersFromBeanProperties(searchDto, parameters);

			// 他社商品情報
			if (StringUtils.isNotBlank(searchDto.getSalesItemCode())) {
				parameters.addParameter("salesItemCode",
						createLikeWord(searchDto.getSalesItemCode(), true, false));
			}

			if (StringUtils.isNotBlank(searchDto.getSalesItemNm())) {
				parameters.addParameter("salesItemNm",
						createLikeWord(searchDto.getSalesItemNm(), true, true));
			}

			// マスタから検索される場合フラグセット
			if (StringUtils.isNotBlank(searchDto.getItemCodeFrom())
					|| StringUtils.isNotBlank(searchDto.getItemCodeTo())
					|| StringUtils.isNotBlank(searchDto.getItemCode())
					|| StringUtils.isNotBlank(searchDto.getItemNm())) {
				parameters.addParameter("itemCodeAreaFlg", "1");
			}

			// 前方一致
			if (StringUtils.isNotEmpty(searchDto.getItemCode())) {
				parameters.addParameter("itemCode",
						createLikeWord(searchDto.getItemCode(), true, false));
			}

			if (StringUtils.isNotEmpty(searchDto.getItemNm())) {
				parameters.addParameter("itemNm",
						createLikeWord(searchDto.getItemNm(), true, true));
			}


		} else {
			parameters.addParameter("slipStatus", searchDto.getSlipStatus());
		}

//		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("sysCorporateSalesSlipId", dto.getSysCorporateSalesSlipId());
		parameters.addParameter("sysCorporationId", dto.getSysCorporationId());
		parameters.addParameter("sysItemId", dto.getSysItemId());
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_CORP_SALES_ITEM_LIST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	/**
	 * 商品更新
	 * @param dto
	 * @throws DaoException
	 */
	public void updateCorporateSalesItem(ExtendCorporateSalesItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// システムIDがある場合は原価・商品コードはmst_itemTBとitem_costTBに格納されているため、更新を行わない
		if (dto.getSysItemId() != 0) {

			//parameters.addParameter("cost", 0);
			parameters.addParameter("itemCode", StringUtils.EMPTY);
			// parameters.addParameter("itemNm", StringUtils.EMPTY);
		}

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_CORP_SALES_ITEM", parameters);
	}

	public void deleteCorporateSalesItem(long sysCorporateSalesItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporateSalesItemId", sysCorporateSalesItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_CORP_SALES_ITEM", parameters);
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

	public ExtendCorporateSalesSlipDTO getSearchCorporateSalesSlip(CorporateSaleSearchDTO corporateSaleSearchDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporateSaleSearchDTO, parameters);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_SEARCH_CORP_SALES_SLIP", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateSalesSlipDTO.class));
	}

	public List<ExtendCorporateSalesSlipDTO> getExportCorporateSaleSearchList(CorporateSaleSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

//		// 前方一致
//		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
//			parameters.addParameter("orderNo",
//					createLikeWord(dto.getOrderNo(), true, false));
//		}
//
//		// 前方一致
//		if (StringUtils.isNotEmpty(dto.getSlipNo())) {
//			parameters.addParameter("slipNo",
//					createLikeWord(dto.getSlipNo(), true, false));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getOrderNm())) {
//			parameters.addParameter("orderNm",
//					createLikeWord(dto.getOrderNm(), true, true));
//			// parameters.addParameter("orderNmHarf",
//			// createLikeWord(StringUtil.toHalfKatakana(dto.getOrderNm()), true,
//			// true));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getOrderTel())) {
//			parameters.addParameter("orderTel",
//					createLikeWord(dto.getOrderTel(), true, false));
//		}
//
//		// 他社商品情報
//		if (StringUtils.isNotBlank(dto.getSalesItemCode())) {
//			parameters.addParameter("salesItemCode",
//					createLikeWord(dto.getSalesItemCode(), true, false));
//		}
//
//		if (StringUtils.isNotBlank(dto.getSalesItemNm())) {
//			parameters.addParameter("salesItemNm",
//					createLikeWord(dto.getSalesItemNm(), true, true));
//		}
//
//		// マスタから検索される場合フラグセット
//		if (StringUtils.isNotBlank(dto.getItemCodeFrom())
//				|| StringUtils.isNotBlank(dto.getItemCodeTo())
//				|| StringUtils.isNotBlank(dto.getItemCode())
//				|| StringUtils.isNotBlank(dto.getItemNm())) {
//			parameters.addParameter("itemCodeAreaFlg", "1");
//		}
//
//		// 前方一致
//		if (StringUtils.isNotEmpty(dto.getItemCode())) {
//			parameters.addParameter("itemCode",
//					createLikeWord(dto.getItemCode(), true, false));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getItemCodeFrom())) {
//			parameters.addParameter("itemCodeFrom",
//					Long.parseLong(dto.getItemCodeFrom()));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getItemCodeTo())) {
//			parameters.addParameter("itemCodeTo",
//					Long.parseLong(dto.getItemCodeTo()));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getItemNm())) {
//			parameters.addParameter("itemNm",
//					createLikeWord(dto.getItemNm(), true, true));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getDestinationNm())) {
//			parameters.addParameter("destinationNm",
//					createLikeWord(dto.getDestinationNm(), true, true));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getOrderMailAddress())) {
//			parameters.addParameter("orderMailAddress",
//					createLikeWord(dto.getOrderMailAddress(), true, true));
//		}
//
//		// 前方一致
//		if (StringUtils.isNotEmpty(dto.getDestinationTel())) {
//			parameters.addParameter("destinationTel",
//					createLikeWord(dto.getDestinationTel(), true, false));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getSumClaimPriceFrom())) {
//			parameters.addParameter("sumClaimPriceFrom",
//					Integer.parseInt(dto.getSumClaimPriceFrom()));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getSumClaimPriceTo())) {
//			parameters.addParameter("sumClaimPriceTo",
//					Integer.parseInt(dto.getSumClaimPriceTo()));
//		}
//
//		if (StringUtils.isNotEmpty(dto.getMemo())) {
//			parameters.addParameter("memo",
//					createLikeWord(dto.getMemo(), true, true));
//		}

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
						.getNameMatchBeanRowHandler(ExtendCorporateSalesSlipDTO.class));
	}

	public List<SysCorporateSalesSlipIdDTO> getSearchCorporateSalesSlipList(CorporateSaleSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		if (StringUtils.isNotEmpty(dto.getClientNm())){
			parameters.addParameter("clientNm", createLikeWord(dto.getClientNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getClientNo())){
			parameters.addParameter("clientNo", createLikeWord(dto.getClientNo(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getDeliveryNm())) {
			parameters.addParameter("deliveryNm", createLikeWord(dto.getDeliveryNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getDeliveryTelNo())) {
			parameters.addParameter("deliveryTelNo", createLikeWord(dto.getDeliveryTelNo(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getPersonInCharge())){
			parameters.addParameter("personInCharge", createLikeWord(dto.getPersonInCharge(), true, true));
		}


		// 前方一致
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo",
					createLikeWord(dto.getOrderNo(), true, false));
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
		return selectList("SEL_SEARCH_CORP_SALES_SLIP", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(SysCorporateSalesSlipIdDTO.class));
	}

	/**
	 * 未使用？
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
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

	/**
	 * 業販伝票の更新関連情報を更新
	 * ・更新日/更新者/出荷日/(返却フラグ)
	 * @param dto
	 * @throws DaoException
	 */
	public void updateCorporateSalesSlipFlg(ExtendCorporateSalesSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		if (StringUtils.isNotEmpty(dto.getReturnFlg())) {
			parameters.addParameter("returnFlg", dto.getReturnFlg());
		}

		/** 20140521 Takakuwa 出庫時に現在の日付を出荷日として登録する処理を追加 */
		parameters.addParameter("shipmentDate", dto.getShipmentDate());

		parameters.addParameter("sysCorporateSalesSlipId", dto.getSysCorporateSalesSlipId());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_CORP_SALES_SLIP_FLG", parameters);
	}

	public void updateCorporateReceivePrice(ExtendCorporateSalesSlipDTO dto) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporateSalesSlipId", dto.getSysCorporateSalesSlipId());
		parameters.addParameter("receivePrice", dto.getSumSalesPrice());
		parameters.addParameter("receiveDate", StringUtil.getToday());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_CORP_RECEIVE_PRICE", parameters);
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

	public StoreDTO selectStoreInfo(long sysCorporationId, long sysChannelId)
			throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("sysChannelId", sysChannelId);
		return select("SEL_STORE_INFO", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(StoreDTO.class));

	}

	public List<ExtendCorporateSalesSlipDTO> getCorporateSalesSlipListByIds(String sysCorporateSalesSlipIds) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporateSalesSlipIds", sysCorporateSalesSlipIds);

		return selectList("SEL_CORP_SALES_SLIP_LIST_BY_IDS", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesSlipDTO.class));
	}

	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemListByIds(CorporateSaleSearchDTO searchDto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysCorporateSalesSlipIds", searchDto.getSysCorporateSalesSlipIds());
		addParametersFromBeanProperties(searchDto, parameters);

		// 前方一致
		if (StringUtils.isNotEmpty(searchDto.getItemCode())) {
			parameters.addParameter("itemCode",
					createLikeWord(searchDto.getItemCode(), true, false));
		}

		if (StringUtils.isNotEmpty(searchDto.getItemCodeFrom())) {
			parameters.addParameter("itemCodeFrom",
					Long.parseLong(searchDto.getItemCodeFrom()));
		}

		if (StringUtils.isNotEmpty(searchDto.getItemCodeTo())) {
			parameters.addParameter("itemCodeTo",
					Long.parseLong(searchDto.getItemCodeTo()));
		}

		if (StringUtils.isNotEmpty(searchDto.getItemNm())) {
			parameters.addParameter("itemNm",
					createLikeWord(searchDto.getItemNm(), true, true));
		}



		return selectList("SEL_CORP_SALES_ITEM_LIST_BY_IDS", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemListClaimed (long sysClientId,String from, String to) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysClientId", sysClientId);
		parameters.addParameter("from", from);
		parameters.addParameter("to", to);

		return selectList("SEL_CORP_SALES_ITEM_LIST_CLAIMED", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));

	}

	/*  2016/1/4 ooyama ADD START 法人間請求書機能対応  */

	/**
	 * 業販原価一覧のシステム業販商品IDを取得する。
	 * @param dto
	 * @return List<SysSaleItemIDDTO>
	 * @throws DaoException
	 */
	public List<SysCorprateSaleItemIDDTO> getSearchCorporateSalesItemIDList(CorporateSaleCostSearchDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		if (StringUtils.isNotEmpty(dto.getClientNm())){
			parameters.addParameter("clientNm", createLikeWord(dto.getClientNm(), true, true));
		}

		if (StringUtils.isNotEmpty(dto.getClientNo())){
			parameters.addParameter("clientNo", createLikeWord(dto.getClientNo(), true, false));
		}

		if (StringUtils.isNotEmpty(dto.getPersonInCharge())){
			parameters.addParameter("personInCharge", createLikeWord(dto.getPersonInCharge(), true, true));
		}


		// 前方一致
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo",
					createLikeWord(dto.getOrderNo(), true, false));
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
					sql = sql + "ITEM.ITEM_NM ILIKE '" + createLikeWord(item, true, true) ;
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

		if (StringUtils.isNotEmpty(dto.getSortFirst())) {
			parameters.addParameter("sortValue", dto.getSortFirst());
		}

		if (StringUtils.isNotEmpty(dto.getSortFirstSub())) {
			parameters.addParameter("sortOrder", dto.getSortFirstSub());
		}

		if (StringUtils.isNotEmpty(dto.getWholseSalerName())) {
			parameters.addParameter("wholseSalerName", createLikeWord(dto.getWholseSalerName(), true, true));
		}


		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_SEARCH_SYS_CORP_SALES_ITEM", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(SysCorprateSaleItemIDDTO.class));
	}

	/**
	 * 業販原価情報を取得する。
	 * @param dto
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public ExtendCorporateSalesItemDTO getSearchCorpSalesCost(CorporateSaleCostSearchDTO costSearchDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(costSearchDTO, parameters);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_SEARCH_CORPRATE_SALES_COST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));

	}

	/**
	 * 業販原価情報を更新する。
	 * @param ExtendCorporateSalesItemDTO
	 * @throws DaoException
	 */
	public void updateCorpSalesCost(ExtendCorporateSalesItemDTO saleItemDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(saleItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_CORP_SALES_ITEM_COST", parameters);
	}

	/**
	 * 品番から直近の業販原価情報を取得する。
	 * @param ExtendCorporateSalesItemDTO
	 * @return ExtendCorporateSalesItemDTO
	 * @throws DaoException
	 */
	public ExtendCorporateSalesItemDTO getLatestCorpSaleCost(
			ExtendCorporateSalesItemDTO saleItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(saleItemDTO, parameters);

		return select("SEL_SEARCH_LATEST_CORP_SALES_COST", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}


	/**
	 * 法人と売上月指定で業販情報を取得する。
	 * @param String 売上月
	 * @param long 法人ID
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCorpSaleCostBtobBill(
			String exportMonth, long exportSysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("shipmentDate",
				createLikeWord(exportMonth, true, false));

		return selectList("SEL_SEARCH_CORP_SALE_COST_BTOBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}



	/**
	 * 法人と売上月指定で業販用業販情報を取得する。(末〆用)
	 * @param String 売上月
	 * @param long 法人ID
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
//〆　	public List<ExtendCorporateSalesItemDTO> getCorpSaleCostCorporateBill(
//	String exportMonth, String previousMonth, long exportSysCorporationId) throws DaoException {
	public List<ExtendCorporateSalesItemDTO> getCorpSaleCostCorporateBill(
			String exportMonth, long exportSysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("shipmentDate",
				createLikeWord(exportMonth, true, false));

		return selectList("SEL_SEARCH_SALE_COST_CORPBILL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	/**
	 * 法人と売上月指定で業販用業販情報を取得する。(末〆用)<br>
	 * 入金管理にて請求月対象予定の業販伝票も取得したい時に使用する。(主に更新時)
	 * @param exportMonth
	 * @param exportSysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCurrentMonthCorpSaleCostCorporateBill(
			String exportMonth, long exportSysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("shipmentDate",
				createLikeWord(exportMonth, true, false));
		parameters.addParameter("curMonthFlag", "on");

		return selectList("SEL_SEARCH_SALE_COST_CORPBILL", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	/**
	 * 法人と売上月と締日指定で業販用業販情報を取得する。(〆用)
	 * @param String 売上月
	 * @param long 法人ID
	 * @return ExtendSalesItemDTO
	 * @throws DaoException
	 */
//〆　	public List<ExtendCorporateSalesItemDTO> getCorpSaleCostCorporateBill(
//	String exportMonth, String previousMonth, long exportSysCorporationId) throws DaoException {
	public List<ExtendCorporateSalesItemDTO> getCorpSaleCostCorporateBill(int cutoffDateCd,
			String fromDate, String toDate, long exportSysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("cutoffDateCd", cutoffDateCd);
		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("shipmentDateFrom",
				createLikeWord(fromDate, true, false));
		parameters.addParameter("shipmentDateTo",
				createLikeWord(toDate, true, false));

		return selectList("SEL_SEARCH_SALE_COST_CORPBILL_CUTOFF", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	/**
	 * 法人と売上月と締日指定で業販用業販情報を取得する。(〆用)
	 * 入金管理にて請求月対象の予定の業販伝票も取得したい時に使用する。(主に更新時)
	 * @param cutoffDateCd
	 * @param fromDate
	 * @param toDate
	 * @param exportSysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCurrentMonthCorpSaleCostCorporateBill(int cutoffDateCd,
			String fromDate, String toDate, long exportSysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("cutoffDateCd", cutoffDateCd);
		parameters.addParameter("sysCorporationId", exportSysCorporationId);
		parameters.addParameter("shipmentDateFrom",
				createLikeWord(fromDate, true, false));
		parameters.addParameter("shipmentDateTo",
				createLikeWord(toDate, true, false));

		return selectList("SEL_SEARCH_SALE_COST_CORPBILL_CUTOFF", parameters,
				ResultSetHandlerFactory
				.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}
	/**
	 * Kind原価情報を持っている業販商品情報の登録
	 * @param corporateSalesItemDTO
	 * @throws DaoException
	 */
	public void registryCorporateSaleItemKind(CorporateSalesItemDTO corporateSalesItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporateSalesItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_CORP_SALES_ITEM_KIND", parameters);
	}

	/**
	 * システム業販伝票IDで該当の商品の原価チェックフラグを更新する。
	 * @param long システム業販伝票ID
	 * @param String 原価チェックフラグ
	 * @throws DaoException
	 */
	public void updateCorpSaleItemCostCheckFlag(long sysSaleSlipId, String costCheckFlag) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysSaleSlipId", sysSaleSlipId);
		parameters.addParameter("costCheckFlag", costCheckFlag);
		update("UPD_CORP_SALE_COST_CHECK_FLAG", parameters);
	}

	/**
	 * 請求書作成、商品が削除されてしまった場合のPDF出力依頼用
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public ExtendMstItemDTO getMstItemDTO(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_INVOICE_MST_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstItemDTO.class));
	}

	/**
	 * 指定範囲内に売り上げた業販伝票を法人ID、得意先ID、口座IDで絞り込み取得する。
	 * (入金管理画面)
	 * @param specifiedDateFrom
	 * @param specifiedDateTo
	 * @param sysCorporationId
	 * @param sysClientId
	 * @param sysAccountId
	 * @return List<ExtendCorporateSalesSlipDTO>
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesSlip(String specifiedDateFrom, String specifiedDateTo)
					throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("salesDateFrom", specifiedDateFrom);
		parameters.addParameter("salesDateTo", specifiedDateTo);

		return selectList("SEL_PRESALE_CORPORATE_SALES_SLIP", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendCorporateSalesItemDTO.class));
	}

	/*  2016/1/4 ooyama ADD END 法人間請求書機能対応  */
}
