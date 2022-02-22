package jp.co.kts.dao.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.search.entity.DomesticOrderListSearchDTO;

public class DomesticSlipDAO extends BaseDAO {


	/**
	 * 国内注文商品Id検索機能
	 * @param searchDto
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> getDomesticOrderIdSearch(DomesticOrderListSearchDTO dto) throws DaoException{

		SQLParameters parameters = new SQLParameters();
		long orderNum = 0;
		addParametersFromBeanProperties(dto, parameters);
		//商品名：部分一致
		if (!StringUtils.isBlank(dto.getItemNm())) {
			parameters.addParameter("itemNm", createLikeWord(dto.getItemNm(), true, true));
		}
		//受注番号：前方一致
		if (!StringUtils.isBlank(dto.getOrdeNo())) {
			parameters.addParameter("orderNo", createLikeWord(dto.getOrdeNo(), true, false));
		}
		//メーカー品番：前方一致
		if (!StringUtils.isBlank(dto.getMakerCode())) {
			parameters.addParameter("makerCode", createLikeWord(dto.getMakerCode(), true, false));
		}
		//数量
		if (!StringUtils.isBlank(dto.getOrderNum())) {
			orderNum = Long.valueOf(dto.getOrderNum());
		}
		parameters.addParameter("orderNum", orderNum);
		//数量検索タイプ
		parameters.addParameter("orderType", dto.getNumberOrderType());
		//通番：後方一致
		if (!StringUtils.isBlank(dto.getSerealNum())) {
			parameters.addParameter("serealNum", createLikeWord(dto.getSerealNum(), false, true));
		}
		//入荷担当：部分一致
		if (!StringUtils.isBlank(dto.getStockCharge())) {
			parameters.addParameter("stockCharge", createLikeWord(dto.getStockCharge(), true, true));
		}
		//注文担当：部分一致
		if (!StringUtils.isBlank(dto.getOrderCharge())) {
			parameters.addParameter("orderCharge", createLikeWord(dto.getOrderCharge(), true, true));
		}
		//仕入原価：FROM
		if (!StringUtils.isBlank(dto.getPurchasingCostFrom())) {
			parameters.addParameter("purchasingCostFrom", Long.valueOf(dto.getPurchasingCostFrom()));
		} else {
			parameters.addParameter("purchasingCostFrom", null);
		}
		//仕入原価：TO
		if (!StringUtils.isBlank(dto.getPurchasingCostTo())) {
			parameters.addParameter("purchasingCostTo", Long.valueOf(dto.getPurchasingCostTo()));
		} else {
			parameters.addParameter("purchasingCostTo", null);
		}
		//対応者：部分一致
		if (!StringUtils.isBlank(dto.getPersonCharge())) {
			parameters.addParameter("personCharge", createLikeWord(dto.getPersonCharge(), true, true));
		}
		//納入先
		if (!StringUtils.isBlank(dto.getDeliveryType())) {
			parameters.addParameter(("deliveryType"), dto.getDeliveryType());
		}
		//未印刷・印刷済み
		if ((dto.getNotPrintData().equals("on") && dto.getPrintedData().equals("on"))
				|| (dto.getNotPrintData().equals("off") && dto.getPrintedData().equals("off"))) {
			parameters.addParameter(("printType"), "2");
		} else if (dto.getNotPrintData().equals("on") && dto.getPrintedData().equals("off")) {
			parameters.addParameter(("printType"), "0");
		} else {
			parameters.addParameter(("printType"), "1");
		}

		return selectList("SEL_DOMESTIC_ORDER_ITEM_ID", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}


	/**
	 * 国内注文商品検索
	 * @param searchDto
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderListDTO getDomesticOrderItemList(DomesticOrderListSearchDTO searchDto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticItemId", searchDto.getSysDomesticItemId());

		return select("SEL_DOMESTIC_ORDER_ITEM", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}

	/**
	 * 国内注文一覧商品削除
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int deleteDomesticListItem(DomesticOrderListDTO dto) throws DaoException{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticItemId", dto.getSysDomesticItemId());
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " DELETE_" + String.valueOf(userInfo.getUserId()) + "} ");


		return update("DELETE_ORDER_LIST_ITEM", parameters);
	}

	/**
	 * 削除対象の伝票を取得するＳＱＬ
	 * @param sysDomesticSlipId
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderListDTO selectCntTargetSlip(Long sysDomesticSlipId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticSlipId", sysDomesticSlipId);

		return select("SEL_CNT_DELETE_SLIP", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}


	/**
	 * 国内注文伝票削除
	 * @param sysDomesticSlipId
	 * @return
	 * @throws DaoException
	 */
	public int deleteDomesticSlip(Long sysDomesticSlipId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();

		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("sysDomesticSlipId", sysDomesticSlipId);
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " DELETE_" + String.valueOf(userInfo.getUserId()) + "} ");


		return update ("DELETE_DOMESTIC_SLIP",parameters);
	}

	/**
	 * 国内注文一覧商品更新
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticListItem(DomesticOrderListDTO dto) throws DaoException{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("arrivalScheduleDate", dto.getArrivalScheduleDate());
		parameters.addParameter("orderNum", dto.getOrderNum());
		parameters.addParameter("purchasingCost", dto.getPurchasingCost());
//		parameters.addParameter("postage", dto.getPostage());
		parameters.addParameter("correspondence", dto.getCorrespondence());
		parameters.addParameter("listRemarks", dto.getListRemarks());
		parameters.addParameter("sysDomesticItemId", dto.getSysDomesticItemId());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId()) + "} ");


		return update("UPDATE_ORDER_LIST_ITEM", parameters);
	}

	/**
	 * 国内注文商品原価反映処理:国内注文管理一覧画面
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateSalesItemTargetCost(ExtendSalesSlipDTO dto,
			DomesticOrderListDTO targetList) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("kindCost", targetList.getPurchasingCost());
		parameters.addParameter("sysSalesItemId", dto.getSysSalesItemId());

		return update("UPD_DOMESTIC_SALES_ITEM_COST", parameters);
	}

	/**
	 * 国内注文一覧ステータス更新
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticListStatus(DomesticOrderListDTO dto, String status) throws DaoException{
		SQLParameters parameters = new SQLParameters();
		String arrivalDate = DateUtil.dateToString("yyyy/MM/dd");
		String chargeDate = DateUtil.dateToString("yyyy/MM/dd");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("sysDomesticItemId", dto.getSysDomesticItemId());
		parameters.addParameter("status", status);
		parameters.addParameter("userNm", userInfo.getFullName());
		parameters.addParameter("arrivalDate", arrivalDate);
		parameters.addParameter("chargeDate", chargeDate);
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " STATUS_" + String.valueOf(userInfo.getUserId()) + "} ");


		return update("UPDATE_ORDER_LIST_STATUS", parameters);
	}

}
