package jp.co.kts.dao.mst;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticManageDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;

public class DomesticExhibitionDAO extends BaseDAO {

	//検索条件未入力の場合の定数：数値
	private static final int IS_EMPTY_NUM = 0;
	//オープン価格フラグがONの場合定価を0にする定数
	private static final int PRICE_SET0 = 0;

	/**
	 * 検索機能
	 * @param dto
	 * @return
	 * @throws DaoException
	 * @throws ParseException
	 */
	public List<DomesticExhibitionDTO> getDomesticExhibitionSearch(DomesticExhibitionSearchDTO dto) throws DaoException, ParseException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);


		//管理品番
		if (StringUtils.isNotEmpty(dto.getManagementCode())) {
			parameters.addParameter("managementCode", "%" + dto.getManagementCode() + "%");
		}
		//定価FROM
		if (StringUtils.isNotEmpty(dto.getListPriceFrom())  ) {
			parameters.addParameter("listPriceFrom", Long.valueOf(dto.getListPriceFrom()));
		} else {
			parameters.addParameter("listPriceFrom", IS_EMPTY_NUM);
		}
		//定価TO
		if (StringUtils.isNotEmpty(dto.getListPriceTo())  ) {
			parameters.addParameter("listPriceTo", Long.valueOf(dto.getListPriceTo()));
		} else {
			parameters.addParameter("listPriceTo", IS_EMPTY_NUM);
		}
		//掛率
		if (StringUtils.isNotEmpty(dto.getItemRateOver())) {
			parameters.addParameter("itemRateOver", Long.valueOf(dto.getItemRateOver()));
		} else {
			parameters.addParameter("itemRateOver", IS_EMPTY_NUM);
		}
		//掛率:検索タイプ
		if (StringUtils.isNotEmpty(dto.getNumberOrderType())) {
			parameters.addParameter("numberOrderType", dto.getNumberOrderType());
		}
		//メーカーID
		if (StringUtils.isNotEmpty(dto.getSysMakerId())) {
			parameters.addParameter("sysMakerId", Long.valueOf(dto.getSysMakerId()));
		}
		//メーカー品番
		if (StringUtils.isNotEmpty(dto.getMakerCode())) {
			parameters.addParameter("makerCode",dto.getMakerCode() + "%");
		}
		//問屋
		if (StringUtils.isNotEmpty(dto.getWholsesalerNm())) {
			parameters.addParameter("wholsesalerNm","%" + dto.getWholsesalerNm() + "%");
		}
		//商品名
		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm","%" + dto.getItemNm() + "%");
		}
		//仕入原価FROM
		if (StringUtils.isNotEmpty(dto.getPurchasingCostFrom())) {
			parameters.addParameter("purchasingCostFrom", Long.valueOf(dto.getPurchasingCostFrom()));
		} else {
			parameters.addParameter("purchasingCostFrom", IS_EMPTY_NUM);
		}
		//仕入原価TO
		if (StringUtils.isNotEmpty(dto.getPurchasingCostTo())) {
			parameters.addParameter("purchasingCostTo", Long.valueOf(dto.getPurchasingCostTo()));
		} else {
			parameters.addParameter("purchasingCostTo", IS_EMPTY_NUM);
		}
		//更新日付From
		if (StringUtils.isNotBlank(dto.getUpdateDateFrom())) {
			Timestamp updateFrom = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(dto.getUpdateDateFrom()).getTime());
			parameters.addParameter("updateDateFrom", updateFrom);
		}
		//更新日付To
		if (StringUtils.isNotBlank(dto.getUpdateDateTo())) {
			Calendar cal = Calendar.getInstance();
			DateFormat df =  new SimpleDateFormat("yyyy/MM/dd");
			Date dt = df.parse(dto.getUpdateDateTo());
			cal.setTime(dt);
			cal.add(Calendar.DATE, 1);
			cal.getTime();
			Timestamp updateTo = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(df.format(cal.getTime())).getTime());
			parameters.addParameter("updateDateTo", updateTo);
		}
		//セット商品フラグ
		if (StringUtils.isNotEmpty(dto.getSetItemFlg())) {
			parameters.addParameter("setItemFlg", dto.getSetItemFlg());
		}

		return selectList("SEL_DOMESTICEXHIBITION", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}

	/**
	 * 検索機能
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public DomesticExhibitionDTO getDomesticExhibitionIdSearch(DomesticExhibitionSearchDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_DOMESTICEXHIBITION", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}

	/**
	 * 更新処理
	 * @param dto
	 * @return
	 * @throws DaoException
	 */

	public int updateDomesticExhibition(DomesticExhibitionDTO dto) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		if (!StringUtils.isBlank(dto.getOpenPriceFlg())) {
			dto.setOpenPriceFlg(StringUtil.switchCheckBox(dto.getOpenPriceFlg()));
		} else {
			dto.setOpenPriceFlg("0");
		}
		addParametersFromBeanProperties(dto, parameters);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("UPD_DOMESTICEXHIBITION", parameters);
	}

	public int updateItemCodeDomesticExhibition(DomesticExhibitionDTO dto) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(dto, parameters);

		return update("UPD_DOMESTICEXHIBITION_ITEM_CODE", parameters);
	}

	/**
	 * 登録処理：通常商品
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int registryDomesticExhibition(DomesticExhibitionDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		//オープン価格フラグ設定
		if (!StringUtils.isBlank(dto.getOpenPriceFlg())) {
			dto.setOpenPriceFlg(StringUtil.switchCheckBox(dto.getOpenPriceFlg()));
		} else {
			dto.setOpenPriceFlg("0");
		}
		parameters.addParameter("openPriceFlg", dto.getOpenPriceFlg());
		parameters.addParameter("setItemFlg", "0");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("INS_DOMESTICEXHIBITION", parameters);
	}

	/**
	 * 国内注文商品原価反映対象検索機能
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> getDomesticOrderItemTargetSearch(DomesticExhibitionDTO dto) throws DaoException {
		List<DomesticOrderListDTO> resultDto = new ArrayList<DomesticOrderListDTO>();

		SQLParameters parameters = new SQLParameters();
		//管理品番
		if (StringUtils.isNotEmpty(dto.getManagementCode())) {
			parameters.addParameter("managementCode", dto.getBeforeManagementCode());
		}
		resultDto = selectList("SEL_DOMESTIC_ORDER_ITEM_TARGET", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));

		return resultDto;
	}

	/**
	 * 国内注文商品原価反映処理
	 * @param dto
	 * @return
	 * @throws DaoException
	 */

	public int updateDomesticOrderItemCost(DomesticOrderListDTO dto, DomesticExhibitionDTO infolist) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("postage", infolist.getPostage());
		parameters.addParameter("listPrice", infolist.getListPrice());
		parameters.addParameter("purchasingCost", infolist.getPurchasingCost());
		parameters.addParameter("sysDomesticItemId", dto.getSysDomesticItemId());
		parameters.addParameter("managementCode", infolist.getManagementCode());
		parameters.addParameter("wholsesalerNm", infolist.getWholsesalerNm());
		parameters.addParameter("sysMakerId", infolist.getSysMakerId());
		parameters.addParameter("makerCode", infolist.getMakerCode());
		parameters.addParameter("itemNm", infolist.getItemNm());


		return update("UPD_DOMESTIC_ORDER_ITEM_COST", parameters);
	}

	/**
	 * 国内注文商品原価反映対象検索機能
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSalesSlipDTO> getSalesItemTargetSearch(DomesticOrderListDTO dto) throws DaoException {
		List<ExtendSalesSlipDTO> resultDto = new ArrayList<ExtendSalesSlipDTO>();

		SQLParameters parameters = new SQLParameters();
		//管理品番
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo", dto.getOrderNo());
			parameters.addParameter("managementCode", dto.getManagementCode());
		}
		resultDto = selectList("SEL_UPD_TARGET_SALES_ITEM_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSalesSlipDTO.class));

		return resultDto;
	}

	/**
	 * 国内注文商品原価反映処理:出品DB画面
	 * @param dto
	 * @return
	 * @throws DaoException
	 */

	public int updateSalesItemTargetCost(ExtendSalesSlipDTO dto, DomesticExhibitionDTO infolist
			, ExtendSalesItemDTO itemDto) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("kindCost", itemDto.getKindCost());
		parameters.addParameter("listPrice", itemDto.getListPrice());
		parameters.addParameter("itemRateOver", itemDto.getItemRateOver());
		parameters.addParameter("cost", itemDto.getCost());
		parameters.addParameter("sysSalesItemId", dto.getSysSalesItemId());

		return update("UPD_SALES_ITEM_TARGET_COST", parameters);
	}

	/**
	 * 削除処理
	 * @param sysManagementId
	 * @return
	 * @throws DaoException
	 */
	public int deleteDomesticExhibition(long sysManagementId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysManagementId", sysManagementId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("DELETE_DOMESTICEXHIBITION", parameters);
	}


	/**
	 * 検索結果DL用SELECT
	 * @param dto
	 * @return
	 * @throws DaoException
	 * @throws ParseException
	 */
	public List<ExtendDomesticManageDTO> getDmstcExhbtnDwnLdLst(DomesticExhibitionSearchDTO dto) throws DaoException, ParseException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);


		//管理品番
		if (StringUtils.isNotEmpty(dto.getManagementCode())) {
			parameters.addParameter("managementCode", "%" + dto.getManagementCode() + "%");
		}
		//定価FROM
		if (StringUtils.isNotEmpty(dto.getListPriceFrom())  ) {
			parameters.addParameter("listPriceFrom", Long.valueOf(dto.getListPriceFrom()));
		} else {
			parameters.addParameter("listPriceFrom", IS_EMPTY_NUM);
		}
		//定価TO
		if (StringUtils.isNotEmpty(dto.getListPriceTo())  ) {
			parameters.addParameter("listPriceTo", Long.valueOf(dto.getListPriceTo()));
		} else {
			parameters.addParameter("listPriceTo", IS_EMPTY_NUM);
		}
		//掛率
		if (StringUtils.isNotEmpty(dto.getItemRateOver())) {
			parameters.addParameter("itemRateOver", dto.getItemRateOver());
		} else {
			parameters.addParameter("itemRateOver", IS_EMPTY_NUM);
		}
		//掛率:検索タイプ
		if (StringUtils.isNotEmpty(dto.getNumberOrderType())) {
			parameters.addParameter("numberOrderType", dto.getNumberOrderType());
		}
		//メーカーID
		if (StringUtils.isNotEmpty(dto.getSysMakerId())) {
			parameters.addParameter("sysMakerId", Long.valueOf(dto.getSysMakerId()));
		}
		//メーカー品番
		if (StringUtils.isNotEmpty(dto.getMakerCode())) {
			parameters.addParameter("makerCode",dto.getMakerCode() + "%");
		}
		//問屋
		if (StringUtils.isNotEmpty(dto.getWholsesalerNm())) {
			parameters.addParameter("wholsesalerNm","%" + dto.getWholsesalerNm() + "%");
		}
		//商品名
		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm","%" + dto.getItemNm() + "%");
		}
		//仕入原価FROM
		if (StringUtils.isNotEmpty(dto.getPurchasingCostFrom())) {
			parameters.addParameter("purchasingCostFrom", Long.valueOf(dto.getPurchasingCostFrom()));
		} else {
			parameters.addParameter("purchasingCostFrom", IS_EMPTY_NUM);
		}
		//仕入原価TO
		if (StringUtils.isNotEmpty(dto.getPurchasingCostTo())) {
			parameters.addParameter("purchasingCostTo", Long.valueOf(dto.getPurchasingCostTo()));
		} else {
			parameters.addParameter("purchasingCostTo", IS_EMPTY_NUM);
		}
		//更新日付From
		if (StringUtils.isNotBlank(dto.getUpdateDateFrom())) {
			Timestamp updateFrom = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(dto.getUpdateDateFrom()).getTime());
			parameters.addParameter("updateDateFrom", updateFrom);
		}
		//更新日付To
		if (StringUtils.isNotBlank(dto.getUpdateDateTo())) {
			Calendar cal = Calendar.getInstance();
			DateFormat df =  new SimpleDateFormat("yyyy/MM/dd");
			Date dt = df.parse(dto.getUpdateDateTo());
			cal.setTime(dt);
			cal.add(Calendar.DATE, 1);
			cal.getTime();
			Timestamp updateTo = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(df.format(cal.getTime())).getTime());
			parameters.addParameter("updateDateTo", updateTo);
		}
		//担当部署名
		if (StringUtils.isNotEmpty(dto.getDepartmentNm())) {
			parameters.addParameter("departmentNm","%" + dto.getDepartmentNm() + "%");
		}
		//セット商品フラグ
		if (StringUtils.isNotEmpty(dto.getSetItemFlg())) {
			parameters.addParameter("setItemFlg", dto.getSetItemFlg());
		}


		return selectList("SEL_DMSTCXHBTN_DL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendDomesticManageDTO.class));
	}

	/**
	 * 出品情報を検索する（条件：管理品番）
	 * @param managementCode
	 * @return
	 * @throws DaoException
	 */
	public DomesticExhibitionDTO getManagementInfo(String managementCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("managementCode", managementCode);

		return select("SEL_DOMESTIC_MANAGEMENT_CD", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}

	/**
	 * 出品情報のリストを取得する
	 * @param managementCodesSet
	 * @return List<DomesticExhibitionDTO>
	 * @throws DaoException
	 */
	public List<DomesticExhibitionDTO> getManagementInfoList(Set<String> managementCodesSet) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("managementCodesSet", managementCodesSet);

		return selectList("SEL_DOMESTIC_EXHIBITION_DTO_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}

	/**
	 * 管理品番の登録数をカウントする
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int getManagementCodeCnt(DomesticExhibitionDTO dto)throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("managementCode", dto.getManagementCode());
		return select("SEL_CNT_MANAGEMENT_CODE", parameters,ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * 管理品番の登録数をカウントする
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public DomesticExhibitionDTO getDomesticItem(DomesticExhibitionDTO dto)throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("managementCode", dto.getManagementCode());

		return select("SEL_EXHIBITION_ITEM", parameters,ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}

	/**
	 * 検索機能：国内注文管理一覧画面遷移用
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public DomesticExhibitionDTO getDomesticExhibitionItemSearch(DomesticExhibitionSearchDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		//管理品番
		if (StringUtils.isNotEmpty(dto.getManagementCode())) {
			parameters.addParameter("managementCode",dto.getManagementCode());
		}

		return select("SEL_DOMESTIC_MANAGEMENT_CD", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}

	/**
	 * 注文書に登録されている管理品番をカウントする
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int getDomesticOrderItemCnt(String managementCode)throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("managementCode", managementCode);
		return select("SEL_CNT_DOMESTIC_ORDER_ITEM", parameters,ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * 国内セット商品を検索する
	 * @param sysManagementId
	 * @return
	 * @throws DaoException
	 */
	public DomesticExhibitionDTO getSetDomesticExhibition(long sysManagementId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysManagementId", sysManagementId);

		return select("SEL_SET_EXHIBIT_MANAGE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}
	/**
	 * 国内セット商品の構成商品を検索する
	 * @param sysManagementId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetDomesticExhibitionDto> getFormDomesticExhibition(long sysManagementId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysManagementId", sysManagementId);

		return selectList("SEL_FROM_EXHIBIT_MANAGE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSetDomesticExhibitionDto.class));
	}

	/**
	 * 登録処理：セット商品：親
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int registSetDomesticExhibition(DomesticExhibitionDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		//オープン価格フラグ設定
		if (!StringUtils.isBlank(dto.getOpenPriceFlg())) {
			dto.setOpenPriceFlg(StringUtil.switchCheckBox(dto.getOpenPriceFlg()));
		} else {
			dto.setOpenPriceFlg("0");
		}
		parameters.addParameter("openPriceFlg", dto.getOpenPriceFlg());
		parameters.addParameter("setItemFlg", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("INS_DOMESTICEXHIBITION", parameters);
	}

	/**
	 * 登録処理：セット商品：子
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int registFromDomesticExhibition(ExtendSetDomesticExhibitionDto dto, DomesticExhibitionDTO setDto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("sysSetManagementId", dto.getSysSetManagementId());
		parameters.addParameter("sysManagementId", setDto.getSysManagementId());
		parameters.addParameter("formSysManagementId", dto.getFormSysManagementId());
		parameters.addParameter("itemNum", dto.getItemNum());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());


		return update("INS_SET_EXHIBIT_MANAGE", parameters);
	}


	/**
	 * 更新処理：セット商品：子
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateFromDomesticExhibition(ExtendSetDomesticExhibitionDto dto, DomesticExhibitionDTO setDto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("sysSetManagementId", dto.getSysSetManagementId());
		parameters.addParameter("itemNum", dto.getItemNum());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("UPD_SET_EXHIBIT_MANAGE", parameters);
	}

	/**
	 * 削除処理：構成商品削除
	 * @param sysManagementId
	 * @return
	 * @throws DaoException
	 */
	public int deleteSetDomesticExhibition(long sysSetManagementId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSetManagementId", sysSetManagementId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("DELETE_SET_DOMESTICEXHIBITION", parameters);
	}

	/**
	 * 削除処理：構成商品削除
	 * @param sysManagementId
	 * @return
	 * @throws DaoException
	 */
	public int deleteSetDomesticExhibitionOfSysManagementId(long sysManagementId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysManagementId", sysManagementId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("DELETE_SET_DOMESTICEXHIBITION_OF_SYSMANAGEMENTID", parameters);
	}

	/**
	 * セット商品アップデート
	 * Excelimportで登録された商品をSet商品にする
	 * @param setItemDTO
	 * @throws DaoException
	 */
	public void updateDomesticSetItemFlg(ExtendSetDomesticExhibitionDto setItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("setItemFlg", "1");
		parameters.addParameter("sysManagementId", setItemDTO.getSysManagementId());
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DOMESTIC_SET_ITEM_FLG", parameters);
	}

	/**
	 * 出品DB商品検索(WHERE:管理品番)
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public DomesticExhibitionDTO getDomesticExhibitionCodeSearch(String managementCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("managementCode", managementCode);

		return select("SEL_DOMESTIC_EXHIBITION_CODE_SEARCH", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticExhibitionDTO.class));
	}

}
