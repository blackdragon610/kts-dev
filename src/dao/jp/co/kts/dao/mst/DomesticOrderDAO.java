package jp.co.kts.dao.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.DomesticOrderItemDTO;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.DomesticOrderSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderItemSearchDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticOrderDAO extends BaseDAO {


	/**
	 * 出品DBから商品検索
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendDomesticOrderItemSearchDTO> getDomesticOrderSearch(DomesticExhibitionSearchDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		//管理品番
		if (StringUtils.isNotEmpty(dto.getManagementCode())) {
			parameters.addParameter("managementCode", "%" + dto.getManagementCode() + "%");
		}
		//メーカーID
		if (StringUtils.isNotEmpty(dto.getMakerNm())) {
			parameters.addParameter("sysMakerId", Long.valueOf(dto.getMakerNm()));

		}
		//商品名
		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm","%" + dto.getItemNm() + "%");
		}

		//問屋名
		if (StringUtils.isNotEmpty(dto.getWholsesalerNm())) {
			parameters.addParameter("wholsesalerNm","%" + dto.getWholsesalerNm() + "%");
		}

		if (StringUtils.isNotBlank(dto.getSetItemFlg())) {
			dto.setSetItemFlg(StringUtil.switchCheckBox(dto.getSetItemFlg()));
			parameters.addParameter("setItemFlg", dto.getSetItemFlg());
		}

		return selectList("SEL_ORDER_DOMESTICEXHIBITION", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendDomesticOrderItemSearchDTO.class));
	}

	/**
	 * 出品DBから商品検索
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public ExtendDomesticOrderItemSearchDTO getSetDomesticOrderSearch(long formSysManagementId, long sysManagementId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysFormManagementId", formSysManagementId);
		parameters.addParameter("sysSetManagementId", sysManagementId);

		return select("SEL_FORM_DOMESTICEXHIBITION", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendDomesticOrderItemSearchDTO.class));
	}

	/**
	 * 商品検索
	 *
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> getDomesticOrderItemSearch(long sysDomesticSlipId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticSlipId", sysDomesticSlipId);

		return selectList("SEL_DETAIL_DOMESTIC_ORDER_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}

	/**
	 * 商品検索:CSV取込用
	 *
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderListDTO getDomesticOrderItemSearchCsv(String managementCode, String orderNo) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("managementCode", managementCode);
		parameters.addParameter("orderNo", orderNo);

		return select("SEL_DOMESTIC_ITEM_CSV_INFO", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}

	/**
	 * 伝票検索
	 *
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderSlipDTO getDomesticOrderSlipSearch(long sysDomesticSlipId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticSlipId", sysDomesticSlipId);

		return select("SEL_DETAIL_DOMESTIC_ORDER_SLIP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderSlipDTO.class));
	}

	/**
	 * PDF出力用伝票検索
	 *
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public ExtendDomesticOrderSlipDTO getExportDomesticOrderSlip(long sysDomesticSlipId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticSlipId", sysDomesticSlipId);

		return select("SEL_DETAIL_DOMESTIC_ORDER_SLIP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendDomesticOrderSlipDTO.class));
	}

	/**
	 * PDF出力用商品検索
	 *
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> getExportDomesticOrderItemSearch(long sysDomesticSlipId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticSlipId", sysDomesticSlipId);

		return selectList("SEL_DETAIL_DOMESTIC_ORDER_ITEM_PDF", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}

	/**
	 *
	 * 倉庫検索List
	 *
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderSlipDTO> getWarehouse() throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderSlipDTO.class));
	}

	/**
	 *
	 * 倉庫検索
	 *
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderSlipDTO getWarehouse(long sysWarehouseId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysWarehouseId", sysWarehouseId);

		return select("SEL_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderSlipDTO.class));
	}

	/**
	 *
	 * 国内倉庫検索
	 *
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderSlipDTO getDomesticWarehouse(long sysWarehouseId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysWarehouseId", sysWarehouseId);

		return select("SEL_DOMESTIC_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderSlipDTO.class));
	}

	/**
	 *
	 * 国内倉庫検索List
	 *
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderSlipDTO> getDomesticWarehouse() throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_DOMESTIC_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderSlipDTO.class));
	}


	/**
	 * 管理品番があるか検索
	 *
	 * @param managementCode
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderListDTO managementCheck(String managementCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		//管理品番
		if (StringUtils.isNotEmpty(managementCode)) {
			parameters.addParameter("managementCode", managementCode);
		}

		return select("SEL_MANAGEMENT_CODE_CHECK", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}

	/**
	 * 受注番号があるか検索
	 *
	 * @param managementCode
	 * @return
	 * @throws DaoException
	 */
	public DomesticOrderSlipDTO orderNoCheck(String orderNo) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		//管理品番
		if (StringUtils.isNotEmpty(orderNo)) {
			parameters.addParameter("orderNo", orderNo);
		}

		return select("SEL_ORDER_NO_CHECK", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderSlipDTO.class));
	}

	/**
	 * メーカー名取得
	 *
	 * @param makerId
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> getMakerNm(long makerId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysMakerId", makerId);

		return selectList("SEL_MST_MAKER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}

	/**
	 * 注文伝票テーブル登録
	 *
	 * @param domesticOrderItemDTO
	 * @throws DaoException
	 */
	public int registryDomesticSlipDAO(DomesticOrderSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		//注文書作製日
		String orderSlipDate = DateUtil.dateToString("yyyy/MM/dd");
		parameters.addParameter("orderSlipDate", orderSlipDate);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " CREATE_" + String.valueOf(userInfo.getUserId()) + "} ");

		return update("INS_DOMESTIC_ORDER_SLIP", parameters);
	}

	/**
	 * 注文商品テーブル登録
	 *
	 * @param dto
	 * @param sysDomesticSlipId
	 * @return
	 * @throws DaoException
	 */
	public int newRegistryDomesticItem(DomesticOrderListDTO dto,
			long sysDomesticSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " CREATE_" + String.valueOf(userInfo.getUserId()) + "} ");

		return update("INS_DOMESTIC_ORDER_ITEM", parameters);
	}

	/**
	 * 注文伝票テーブル更新
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticSlipDAO(DomesticOrderSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId()) + "} ");

		return update("UPD_DOMESTIC_ORDER_SLIP", parameters);
	}

	/**
	 * 注文伝票印刷確認フラグ更新
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticPrintFlgDAO(DomesticOrderSlipDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " PRINT_" + String.valueOf(userInfo.getUserId()) + "} ");
		parameters.addParameter("printCheckFlag", dto.getPrintCheckFlag());
		parameters.addParameter("sysDomesticSlipId", dto.getSysDomesticSlipId());

		return update("UPD_DOMESTIC_PRINT_FLG", parameters);
	}

	/**
	 * 注文書出力時更新
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticOrderPrintStatus(DomesticOrderItemDTO dto)throws DaoException {
		SQLParameters parameters = new SQLParameters();

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		//ユーザー情報
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		//履歴情報
		parameters.addParameter("historyInfo", "{" + date + " PRINT_" + String.valueOf(userInfo.getUserId()) + "} ");
		//ステータス
		parameters.addParameter("status", dto.getStatus());
		//ステータスを「入荷待ち」にする場合採番する
		if (dto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO)) {
			if (StringUtils.isNotBlank(dto.getSerealNum())) {
				parameters.addParameter("serealNum", dto.getSerealNum());
			} else {
				parameters.addParameter("serealNum", String.valueOf(Long.valueOf(new SequenceDAO().getMaxSerealNum()) + 1));
			}
		}
		//システム国内注文商品ID
		parameters.addParameter("sysDomesticItemId", dto.getSysDomesticItemId());

		return update("UPDATE_ORDER_PRINT_STATUS", parameters);
	}

	/**
	 * 注文商品テーブル更新
	 *
	 * @param dto
	 * @param sysDomesticSlipId
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticItem(DomesticOrderListDTO dto,
			long sysDomesticSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId()) + "} ");

		return update("UPD_DOMESTIC_ORDER_ITEM", parameters);
	}

	/**
	 * 既存注文商品テーブル追加登録
	 *
	 * @param dto
	 * @param sysDomesticSlipId
	 * @return
	 * @throws DaoException
	 */
	public int registryDomesticItem(DomesticOrderListDTO dto,
			long sysDomesticSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " CREATE_" + String.valueOf(userInfo.getUserId()) + "} ");

		return update("INS_DOMESTIC_ORDER_ITEM", parameters);
	}

	/**
	 * 既存注文商品テーブル追加登録
	 * Csv用
	 * @param dto
	 * @param sysDomesticSlipId
	 * @return
	 * @throws DaoException
	 */
	public int registryDomesticItemList(DomesticOrderItemDTO dto,
			long sysDomesticSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " CREATE_" + String.valueOf(userInfo.getUserId()) + "} ");

		return update("INS_DOMESTIC_ORDER_ITEM", parameters);
	}
	/**
	 * 削除処理
	 * @param sysManagementId
	 * @return
	 * @throws DaoException
	 */
	public int deleteDomesticOrderItem(long sysDomesticItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDomesticItemId", sysDomesticItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo", "{" + date + " DELETE_" + String.valueOf(userInfo.getUserId()) + "} ");

		return update("DEL_UPD_DOMESTIC_ORDER_ITEM", parameters);
	}

	/**
	 * 売上詳細画面からの商品検索
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstItemDTO> getSearchDomesticItemList(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		// 管理品番
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("managementCode", createLikeWord(dto.getItemCode(), true, true));
		}
		// 商品名
		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");
		}
		//受注番号
		if (StringUtils.isNotEmpty(dto.getOrderNo())) {
			parameters.addParameter("orderNo", dto.getOrderNo());
		}

		return selectList("SEL_SALE_SEARCH_DOMESTIC_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstItemDTO.class));
	}

	/**
	 * 構成商品検索
	 * SET登録されている構成商品を検索する
	 * キー：セットシステム国内商品ID
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> selectFormDomesticItem(long setSysDomesticItemId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("setSysDomesticItemId", setSysDomesticItemId);

		return selectList("SEL_FOMR_DOMESTIC_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticOrderListDTO.class));
	}

	/**
	 * 構成商品削除
	 * SET登録されている構成商品を削除する
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public int deleteFormDomesticItem(long setSysDomesticItemId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("setSysDomesticItemId", setSysDomesticItemId);

		return update("UPD_DELETE_FOMR_DOMESTIC_ITEM", parameters);
	}
}