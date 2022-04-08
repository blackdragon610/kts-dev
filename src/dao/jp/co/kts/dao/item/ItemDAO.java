package jp.co.kts.dao.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.AnnualSalesDTO;
import jp.co.kts.app.common.entity.ArrivalScheduleDTO;
import jp.co.kts.app.common.entity.BackOrderDTO;
import jp.co.kts.app.common.entity.DeadStockDTO;
import jp.co.kts.app.common.entity.ItemCostDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.common.entity.UpdateDataHistoryDTO;
import jp.co.kts.app.common.entity.WarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemCostDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemManualDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemPriceDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ItemCostPriceDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.dao.common.SequenceDAO;

public class ItemDAO extends BaseDAO {

	/**
	 * ----------------------------------------OUTPUT---------------------------------------------------
	 */

	public ExtendMstItemDTO getMstItemDTO(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_MST_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstItemDTO.class));
	}

	/**
	 * 在庫キープ更新用商品抽出
	 * @param itemCode
	 * @return
	 * @throws DaoException
	 */
	public MstItemDTO getMstItemForKeep(String itemCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("itemCode", itemCode);

		return select("SEL_MST_ITEM_FOR_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstItemDTO.class));

	}

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */
	/**
	 * 在庫詳細とKind原価を取得
	 * @param dto
	 * @return 検索結果
	 * @throws DaoException
	 */
	public ExtendMstItemDTO getKindCostDTO(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_KIND_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstItemDTO.class));
	}
	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

	public ExtendMstItemDTO getMstItemDTO(ExtendMstItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_MST_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstItemDTO.class));
	}

	public List<ExtendMstItemDTO> getMstItemList(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		if (StringUtils.isNotEmpty(dto.getItemNm())) {

			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");
			parameters.addParameter("itemNmHalf", "%" + StringUtil.toHalfKatakana(dto.getItemNm()) + "%");
			parameters.addParameter("itemNmUpper", "%" + StringUtil.toUpperKatakana(dto.getItemNm()) + "%");
		}

		if (StringUtils.isNotEmpty(dto.getItemCode())) {

			parameters.addParameter("itemCode", dto.getItemCode() + "%");
		}

		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_MST_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstItemDTO.class));
	}

	/**
	 * KTS倉庫在庫のリストを取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendWarehouseStockDTO> getWarehouseStockList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_WAREHOUSE_STOCK_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	/**
	 * 外部倉庫の在庫のリストを取得する。
	 * @param sysItemId
	 * @param sysExternalWarehouseCode
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendWarehouseStockDTO> getExternalStockList(long sysItemId, String sysExternalWarehouseCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysExternalWarehouseCode", sysExternalWarehouseCode);

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_EXTERNAL_STOCK_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	/**
	 * 品番を返却するSQL
	 *(セット商品の構成商品用)
	 * @param itemCode
	 * @return ITEM_CODE
	 * @throws DaoException
	 */
	public String getItemCode(long formSysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("formSysItemId", formSysItemId);

		return select("SEL_MST_ITEM_ITEM_CODE", parameters,ResultSetHandlerFactory.getFirstColumnStringRowHandler());

	}

	/**
	 * 出庫分類を返却するSQL
	 * @param itemCode
	 * @return LEAVE_CLASS_FLG
	 * @throws DaoException
	 */
	public String getLeaveClassFlg(String itemCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("itemCode", itemCode);

		return select("SEL_LEAVE_CLASS_FLG", parameters,ResultSetHandlerFactory.getFirstColumnStringRowHandler());
	}

	/**
	 * 仮在庫数を返却するSQL
	 *
	 * @param itemCode
	 * @return TEMPORARY_STOCK_NUM
	 * @throws DaoException
	 */
	public int getTemporaryStockNum(String itemCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("itemCode", itemCode);

		return select("SEL_MST_ITEM_TEMPORARY_STOCK_NUM", parameters,ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());

	}

	/**
	 * 仮在庫数を返却するSQL
	 * (セット商品の構成商品用)
	 * @param formSysItemId
	 * @return
	 * @throws DaoException
	 */
	public int getTemporaryStockNum(long formSysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("formSysItemId", formSysItemId);

		return select("SEL_MST_AND_SET_ITEM_TEMPORARY_STOCK_NUM", parameters,ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());

	}


	/**
	 * 総在庫数を取得するSQL
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public ExtendWarehouseStockDTO getWarehouseTotalStockList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return select("SEL_WAREHOUSE_STOCK_NUM_TOTAL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	/**
	 * 倉庫在庫取得
	 * @param sysItemId
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public ExtendWarehouseStockDTO getSysWarehouseStockId(long sysItemId, long sysWarehouseId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysWarehouseId", sysWarehouseId);

		return select("SEL_WAREHOUSE_STOCK", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	/**
	 * 倉庫在庫取得
	 * @param sysItemId
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public ExtendWarehouseStockDTO getSysExternalStockId(long sysItemId, String sysExternalWarehouseCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysExternalWarehouseCode", sysExternalWarehouseCode);

		return select("SEL_EXTERNAL_STOCK_LIST", parameters,
				ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	public List<ExtendArrivalScheduleDTO> getArrivalScheduleList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

//		parameters.addParameter("getListFlg", "1");
		parameters.addParameter("arrivalFlag", "0");

		return selectList("SEL_ARRIVAL_SCHEDULE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendArrivalScheduleDTO.class));
	}

	public List<ExtendArrivalScheduleDTO> getArrivalHistoryList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

//		parameters.addParameter("getListFlg", "1");
		parameters.addParameter("arrivalFlag", "1");

		return selectList("SEL_ARRIVAL_SCHEDULE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendArrivalScheduleDTO.class));
	}

	public List<BackOrderDTO> getBackOrderList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_BACK_ORDER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(BackOrderDTO.class));
	}

	public List<ExtendSetItemDTO> getAllSetItemList() throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_ALL_SET_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	public List<ExtendSetItemDTO> getSetItemList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_SET_ITEM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	/**
	 * セット商品の情報を取得する（組立可数計算用）
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getSetItemInfoList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_SET_ITEM_INFO", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	/**
	 * 出庫分類フラグが1：構成商品在庫から出庫のものを取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getSetItemLeaveClassFlgOnList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_SET_ITEM_LEAVE_CLASS_FLG_ON", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	public ResultItemSearchDTO getItemSearch(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("haibangFlg", dto.getHaibangFlg().equals("on") ? "1" : "0");

		return select("SEL_SEARCH_ITEM_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ResultItemSearchDTO.class));
}

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */
	/**
	 * 在庫一覧とKind原価を取得
	 * @param dto
	 * @return 検索結果
	 * @throws DaoException
	 */
	public ResultItemSearchDTO getItemKindCostSearch(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_SEARCH_ITEM_KIND_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ResultItemSearchDTO.class));
	}

	public List<SysItemIdDTO> getItemKindCostSearchList(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(dto, parameters);

		if (StringUtils.isNotEmpty(dto.getItemNm())) {

			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");
			parameters.addParameter("itemNmHalf", "%" + StringUtil.toHalfKatakana(dto.getItemNm()) + "%");
			parameters.addParameter("itemNmUpper", "%" + StringUtil.toUpperKatakana(dto.getItemNm()) + "%");
		}
		if (StringUtils.isNotEmpty(dto.getLocationNo())) {

			parameters.addParameter("locationNo", dto.getLocationNo() + "%");
		}

		if (StringUtils.isNotEmpty(dto.getItemCode())) {

			parameters.addParameter("itemCode", dto.getItemCode() + "%");
		}

		if (StringUtils.isNotEmpty(dto.getSpecMemo())) {

			parameters.addParameter("specMemo",  "%" + dto.getSpecMemo() + "%");
		}

		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_SEARCH_ITEM_KIND_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(SysItemIdDTO.class));
	}
	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

	public List<SysItemIdDTO> getItemSearchList(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(dto, parameters);

		if (StringUtils.isNotEmpty(dto.getItemNm())) {

			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");
			parameters.addParameter("itemNmHalf", "%" + StringUtil.toHalfKatakana(dto.getItemNm()) + "%");
			parameters.addParameter("itemNmUpper", "%" + StringUtil.toUpperKatakana(dto.getItemNm()) + "%");
		}
		if (StringUtils.isNotEmpty(dto.getLocationNo())) {

			parameters.addParameter("locationNo", dto.getLocationNo() + "%");
		}

		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			if (dto.getSearchMethod().equals("1")) {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), true, false));
			} else if (dto.getSearchMethod().equals("2")) {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), true, true));
			} else {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), false, true));
			}
		}

		if (StringUtils.isNotEmpty(dto.getCompanyFactoryNm())) {
			parameters.addParameter("companyFactoryNm", createLikeWord(dto.getCompanyFactoryNm(), true, true));
		}

		parameters.addParameter("getListFlg", "1");

		//入荷日
		if (StringUtils.isNotBlank(dto.getArrivalDateFrom())|| StringUtils.isNotBlank(dto.getArrivalDateTo())) {
			parameters.addParameter("arrivalDateInput", "1");
		} else if (StringUtils.isNotBlank(dto.getArrivalDateFrom()) && StringUtils.isNotBlank(dto.getArrivalDateTo())) {
			parameters.addParameter("arrivalDateInput", "0");
		}
		//数量
		if (StringUtils.isNotBlank(dto.getStockNum())) {
			parameters.addParameter("stockNum", Long.valueOf(dto.getStockNum()));
		} else {
			parameters.addParameter("stockNum", 0);
		}
		//仕入先ID
		if (StringUtils.isNotBlank(dto.getSysSupplierId())) {
			parameters.addParameter("sysSupplierId", Long.valueOf(dto.getSysSupplierId()));
		}

		//仕様メモ
		if (StringUtils.isNotBlank(dto.getSpecMemo())) {
			parameters.addParameter("specMemo", createLikeWord(dto.getSpecMemo(), true, true));
		}

		//キープ有フラグ
		if (dto.getKeepFlg().equals("on")) {
			parameters.addParameter("keepFlg", '1');
		}
		//在庫アラートフラグ
		if (dto.getOrderAlertFlg().equals("on")) {
			parameters.addParameter("orderAlertFlg", '1');
		}

		//注文アラートフラグ
		if (dto.getDeliveryAlertFlg().equals("on")) {
			parameters.addParameter("deliveryAlertFlg", '1');
		}

		//入荷予定日超過フラグ
		if (dto.getOverArrivalScheduleFlg().equals("on")) {
			parameters.addParameter("overArrivalScheduleFlg", '1');
		}

		//入荷予定日有フラグ
		if (dto.getArrivalScheduleFlg().equals("on")) {
			parameters.addParameter("arrivalScheduleFlg", '1');
		}

		//セット商品フラグ
		if (dto.getSetItemFlg().equals("on")) {
			parameters.addParameter("setItemFlg", '1');
		}

		//説明書フラグ
		if (dto.getManualFlg().equals("on")) {
			parameters.addParameter("manualFlg", '1');
		}

		//図面フラグ
		if (dto.getPlanSheetFlg().equals("on")) {
			parameters.addParameter("planSheetFlg", '1');
		}

		//その他資料フラグ
		if (dto.getOtherDocumentFlg().equals("on")) {
			parameters.addParameter("otherDocumentFlg", '1');
		}

		//不良在庫フラグ
		if (dto.getDeadStockFlg().equals("on")) {
			parameters.addParameter("deadStockFlg", '1');
		}

		//注文プールフラグ
		if (dto.getOrderPoolFlg().equals("on")) {
			parameters.addParameter("orderPoolFlg", '1');
		}

		//注文数0検索
		if (dto.getOrderNum0Flg().equals("on")) {
			parameters.addParameter("orderNum0Flg", 1);
		}
		
		//廃盤商品
		parameters.addParameter("haibangFlg", dto.getHaibangFlg().equals("on") ? "1" : "0");

		return selectList("SEL_SEARCH_ITEM_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(SysItemIdDTO.class));
	}

	/**
	 * 入荷予定日を取得します(キープ取込用)
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ArrivalScheduleDTO> getArrivalScheduleDate(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_ARRIVAL_SCHEDULE_FOR_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ArrivalScheduleDTO.class));
	}

	/**
	 * 検索結果をダウンロード機能
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ResultItemSearchDTO> getExportItemSearchList(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(dto, parameters);

		if (StringUtils.isNotEmpty(dto.getItemNm())) {

			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");
			parameters.addParameter("itemNmHalf", "%" + StringUtil.toHalfKatakana(dto.getItemNm()) + "%");
			parameters.addParameter("itemNmUpper", "%" + StringUtil.toUpperKatakana(dto.getItemNm()) + "%");
		}
		if (StringUtils.isNotEmpty(dto.getLocationNo())) {

			parameters.addParameter("locationNo", dto.getLocationNo() + "%");
		}

		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			if (dto.getSearchMethod().equals("1")) {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), true, false));
			} else if (dto.getSearchMethod().equals("2")) {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), true, true));
			} else {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), false, true));
			}
		}

		if (StringUtils.isNotEmpty(dto.getCompanyFactoryNm())) {
			parameters.addParameter("companyFactoryNm", createLikeWord(dto.getCompanyFactoryNm(), true, true));
		}

		parameters.addParameter("getListFlg", "1");

		//入荷日
		if (StringUtils.isNotBlank(dto.getArrivalDateFrom())|| StringUtils.isNotBlank(dto.getArrivalDateTo())) {
			parameters.addParameter("arrivalDateInput", "1");
		} else if (StringUtils.isNotBlank(dto.getArrivalDateFrom()) && StringUtils.isNotBlank(dto.getArrivalDateTo())) {
			parameters.addParameter("arrivalDateInput", "0");
		}
		//数量
		if (StringUtils.isNotBlank(dto.getStockNum())) {
			parameters.addParameter("stockNum", Long.valueOf(dto.getStockNum()));
		} else {
			parameters.addParameter("stockNum", 0);
		}

		//仕入先ID
		if (StringUtils.isNotBlank(dto.getSysSupplierId())) {
			parameters.addParameter("sysSupplierId", Long.valueOf(dto.getSysSupplierId()));
		}

		//仕様メモ
		if (StringUtils.isNotBlank(dto.getSpecMemo())) {
			parameters.addParameter("specMemo", createLikeWord(dto.getSpecMemo(), true, true));
		}

		//キープ有フラグ
		if (dto.getKeepFlg().equals("on")) {
			parameters.addParameter("keepFlg", '1');
		}
		//在庫アラートフラグ
		if (dto.getOrderAlertFlg().equals("on")) {
			parameters.addParameter("orderAlertFlg", '1');
		}

		//注文アラートフラグ
		if (dto.getDeliveryAlertFlg().equals("on")) {
			parameters.addParameter("deliveryAlertFlg", '1');
		}

		//入荷予定日超過フラグ
		if (dto.getOverArrivalScheduleFlg().equals("on")) {
			parameters.addParameter("overArrivalScheduleFlg", '1');
		}

		//入荷予定日有フラグ
		if (dto.getArrivalScheduleFlg().equals("on")) {
			parameters.addParameter("arrivalScheduleFlg", '1');
		}

		//セット商品フラグ
		if (dto.getSetItemFlg().equals("on")) {
			parameters.addParameter("setItemFlg", '1');
		}

		//説明書フラグ
		if (dto.getManualFlg().equals("on")) {
			parameters.addParameter("manualFlg", '1');
		}

		//図面フラグ
		if (dto.getPlanSheetFlg().equals("on")) {
			parameters.addParameter("planSheetFlg", '1');
		}

		//その他資料フラグ
		if (dto.getOtherDocumentFlg().equals("on")) {
			parameters.addParameter("otherDocumentFlg", '1');
		}

		//不良在庫フラグ
		if (dto.getDeadStockFlg().equals("on")) {
			parameters.addParameter("deadStockFlg", '1');
		}

		//注文プールフラグ
		if (dto.getOrderPoolFlg().equals("on")) {
			parameters.addParameter("orderPoolFlg", '1');
		}

		//注文数0検索
		if (dto.getOrderNum0Flg().equals("on")) {
			parameters.addParameter("orderNum0Flg", 1);
		}

		return selectList("SEL_EXPORT_ITEM_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ResultItemSearchDTO.class));
	}

	/**
	 * [概要]商品IDから在庫情報DTOを取得する
	 * @param itemIdList
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendWarehouseStockDTO> getExportStockItemInfoList(List<SysItemIdDTO> itemIdList) throws DaoException {
		// インスタンス生成
		SQLParameters parameters = new SQLParameters();
		ArrayList<Long> sysItemIdList = new ArrayList<Long>();


		for (SysItemIdDTO dto : itemIdList) {
			sysItemIdList.add(dto.getSysItemId());
		}

		// システム商品ID
		parameters.addParameter("sysItemIdList", sysItemIdList);
		// SQL実行
		return selectList("SELECT_DL_STOCK_INFO", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	/**
	 * 商品IDから価格情報DTOを取得する
	 * @param itemIdList
	 * @return
	 * @throws DaoException
	 */
	public List<ItemCostPriceDTO> getExportPriceInfoList(List<SysItemIdDTO> itemIdList) throws DaoException {

		// インスタンス生成
		SQLParameters parameters = new SQLParameters();

		ArrayList<Long> sysItemIdList = new ArrayList<Long>();

		for (SysItemIdDTO dto : itemIdList) {
			sysItemIdList.add(dto.getSysItemId());
		}

		// システム商品ID
		parameters.addParameter("sysItemIdList", sysItemIdList);
		// SQL実行
		return selectList("SEARCH_ITEM_COST_PRICE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ItemCostPriceDTO.class));
	}

	/**
	 * 商品IDから原価情報DTOを取得する
	 * @param itemIdList
	 * @return
	 * @throws DaoException
	 */
	public List<ItemCostPriceDTO> getExportCostInfoList(SysItemIdDTO sysItemId) throws DaoException {

		// インスタンス生成
		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(sysItemId, parameters);


		// SQL実行
		return selectList("SEL_ITEM_COST_DL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ItemCostPriceDTO.class));
	}

	/**
	 * 商品IDから売価情報DTOを取得する
	 * @param itemIdList
	 * @return
	 * @throws DaoException
	 */
	public List<ItemCostPriceDTO> getExportPriceInfoList(SysItemIdDTO sysItemId) throws DaoException {

		// インスタンス生成
		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(sysItemId, parameters);
		// SQL実行
		return selectList("SEL_ITEM_PRICE_DL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ItemCostPriceDTO.class));
	}

	/**
	 * 検索条件からCSVダウンロード情報を取得する
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ResultItemSearchDTO> getItemSearchCsvList(SearchItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		addParametersFromBeanProperties(dto, parameters);

		if (StringUtils.isNotEmpty(dto.getItemNm())) {

			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");
			parameters.addParameter("itemNmHalf", "%" + StringUtil.toHalfKatakana(dto.getItemNm()) + "%");
			parameters.addParameter("itemNmUpper", "%" + StringUtil.toUpperKatakana(dto.getItemNm()) + "%");
		}
		if (StringUtils.isNotEmpty(dto.getLocationNo())) {

			parameters.addParameter("locationNo", dto.getLocationNo() + "%");
		}

		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			if (dto.getSearchMethod().equals("1")) {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), true, false));
			} else if (dto.getSearchMethod().equals("2")) {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), true, true));
			} else {
				parameters.addParameter("itemCode", createLikeWord(dto.getItemCode(), false, true));
			}
		}

		if (StringUtils.isNotEmpty(dto.getCompanyFactoryNm())) {
			parameters.addParameter("companyFactoryNm", dto.getCompanyFactoryNm());
		}

		parameters.addParameter("getListFlg", "1");

		//入荷日
		if (StringUtils.isNotBlank(dto.getArrivalDateFrom())|| StringUtils.isNotBlank(dto.getArrivalDateTo())) {
			parameters.addParameter("arrivalDateInput", "1");
		} else if (StringUtils.isNotBlank(dto.getArrivalDateFrom()) && StringUtils.isNotBlank(dto.getArrivalDateTo())) {
			parameters.addParameter("arrivalDateInput", "0");
		}
		//数量
		if (StringUtils.isNotBlank(dto.getStockNum())) {
			parameters.addParameter("stockNum", Long.valueOf(dto.getStockNum()));
		} else {
			parameters.addParameter("stockNum", 0);
		}
		
		parameters.addParameter("haibangFlg", dto.getHaibangFlg().equals("on") ? "1" : "0");

		return selectList("SEL_SEARCH_ITEM_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ResultItemSearchDTO.class));
	}


	public List<ExtendItemCostDTO> getItemCostList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_ITEM_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemCostDTO.class));
	}

	/**
	 * 構成品番の原価合計値を取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendItemCostDTO> getItemCostSumList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SUM_SET_ITEM_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemCostDTO.class));
	}

	/**
	 * 商品id・会社idから原価DTOを取得します
	 * @param sysItemId 商品id
	 * @param sysCorporationId 会社id
	 * @return 原価DTO
	 * @throws DaoException
	 */
	public ItemCostDTO getItemCost(long sysItemId, long sysCorporationId) throws DaoException {

		if (sysItemId == 0
				|| sysCorporationId == 0) {
			throw new NullPointerException();
		}

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_ITEM_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemCostDTO.class));
	}


	/*  本番障害対応20160203 大山 START */
	/**
	 * 商品id・会社idから原価DTOを取得します
	 * @param sysItemId 商品id
	 * @param sysCorporationId 会社id
	 * @return 原価DTO
	 * @throws DaoException
	 */
	public ItemCostDTO getItemCostCSV(long sysItemId, long sysCorporationId) throws DaoException {

		if (sysItemId == 0
				|| sysCorporationId == 0) {
			throw new NullPointerException();
		}

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_ITEM_COST_CSV", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemCostDTO.class));
	}
	/*  本番障害対応20160203 大山 END */


	public List<ExtendItemPriceDTO> getItemPriceList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_ITEM_PRICE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemPriceDTO.class));
	}

	/**
	 * 構成品番の売価合計値を取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendItemPriceDTO> getItemPriceSumList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SUM_SET_ITEM_PRICE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemPriceDTO.class));
	}

	/**
	 * 構成品番の金額合算情報を取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public ExtendMstItemDTO getSumPurcharPrice(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return select("SEL_SUM_PURCHAR_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstItemDTO.class));
	}

	public List<ExtendItemManualDTO> getItemManualList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_ITEM_MANUAL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemManualDTO.class));
	}

	/**
	 * ----------------------------------------INPUT---------------------------------------------------
	 */
	public void registryItem(ExtendMstItemDTO mstItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(mstItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_MST_ITEM", parameters);
	}

	public int updateItem(MstItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_MST_ITEM", parameters);
	}

	public int updatePoolFlg(MstItemDTO dto) throws DaoException {
		SQLParameters parameters = new SQLParameters();

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("orderPoolFlg", dto.getOrderPoolFlg());
		parameters.addParameter("orderNum", dto.getOrderNum());
		parameters.addParameter("sysItemId", dto.getSysItemId());

		return update("UPD_MST_ITEM_POOL_FLG", parameters);
	}

	public void registryItemPrice(ExtendItemPriceDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ITEM_PRICE", parameters);
	}

	public void updateItemPrice(ExtendItemPriceDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_ITEM_PRICE", parameters);
	}

	public void registryItemCost(ExtendItemCostDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ITEM_COST", parameters);
	}

	public void updateItemCost(ExtendItemCostDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_ITEM_COST", parameters);
	}

	public void registryWarehouseStock(ExtendWarehouseStockDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_WAREHOUSE_STOCK", parameters);
	}


	public void registryExternalStock(ExtendWarehouseStockDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_EXTERNAL_STOCK", parameters);
	}

	/**
	 * 倉庫在庫更新
	 * @param dto
	 * @throws DaoException
	 */
	public void updateWarehouseStock(WarehouseStockDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_WAREHOUSE_STOCK", parameters);
	}


	/**
	 * 楽天倉庫在庫更新
	 * @param dto
	 * @throws DaoException
	 */
	public void updateExternalStock(WarehouseStockDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_EXTERNAL_STOCK", parameters);
	}

	public void registryArrivalSchedule(ExtendArrivalScheduleDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("arrivalFlag", "0");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ARRIVAL_SCHEDULE", parameters);
	}

	public void registryArrivalHistory(ExtendArrivalScheduleDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("arrivalFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ARRIVAL_SCHEDULE", parameters);

	}

	public void resetArrivalSchedule(ExtendArrivalScheduleDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("arrivalFlag", "0");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("RESET_ORDER_NUM", parameters);
		if(dto.getSysArrivalScheduleId() == 0) {
			update("RESET_MAX_ARRIVAL_SCHEDULE", parameters);
			update("DEL_ARRIVAL_SCHEDULE", parameters);
		}else {
			update("RESET_ARRIVAL_SCHEDULE", parameters);
			update("DEL_ARRIVAL_SCHEDULE", parameters);
		}
	}

	public void updateArrivalSchedule(ExtendArrivalScheduleDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_ARRIVAL_SCHEDULE", parameters);
	}

	/**
	 * 入荷予定数を更新する
	 *
	 * @param dto
	 * @throws DaoException
	 */
	public void updateArrivalNum(ExtendArrivalScheduleDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysArrivalScheduleId", dto.getSysArrivalScheduleId());
		parameters.addParameter("arrivalNum", dto.getArrivalNum());


		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_ARRIVAL_SCHEDULE_PART", parameters);

	}

	public void registryBackOrder(BackOrderDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_BACK_ORDER", parameters);
	}

	public void updateBackOrder(BackOrderDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_BACK_ORDER", parameters);
	}

	//20140323 八鍬
	public void registrySetItem(ExtendSetItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_SET_ITEM", parameters);
	}

	//20140324 八鍬
	public void updateSetItem(ExtendSetItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_SET_ITEM", parameters);
	}

	//20140327 八鍬
	public void deleteItem(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_MST_ITEM", parameters);
	}

	//20140327 八鍬
	public void deleteWarehouseStock(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_WAREHOUSE_STOCK", parameters);
	}


	public void deleteExternalStock(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_EXTERNAL_STOCK", parameters);
	}

	//20140327 八鍬
	public void deleteArrivalSchedule(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_ARRIVAL_SCHEDULE", parameters);
	}

	//20140327 八鍬
	public void deleteCost(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_ITEM_COST", parameters);
	}

	//20140327 八鍬
	public void deletePrice(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_ITEM_PRICE", parameters);
	}

	//20140327 八鍬
	public void deleteBackOrder(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_BACK_ORDER", parameters);
	}

	//20140327 八鍬
	public void deleteCodeCollation(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_CODE_COLLATION", parameters);
	}

	//20140327 八鍬
	public void deleteFormItem(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_FORM_ITEM", parameters);
	}

	//20140329 八鍬
	public void registryLumpArrivalSchedule(ExtendArrivalScheduleDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ARRIVAL_SCHEDULE", parameters);
	}

	public void registryItemManual(ExtendItemManualDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ITEM_MANUAL", parameters);
	}

	public void deleteItemManual(ExtendItemManualDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemManualId", dto.getSysItemManualId());
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("DEL_ITEM_MANUAL", parameters);
	}

	public void updateItemManual(ExtendItemManualDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_ITEM_MANUAL", parameters);
	}

	public ExtendItemManualDTO getItemManualDTO(long sysItemManualId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemManualId", sysItemManualId);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_ITEM_MANUAL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendItemManualDTO.class));
	}


	/**
	 * キープ数を追加する
	 * @param dto
	 * @throws DaoException
	 */
	public void registryKeep(ExtendKeepDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_KEEP", parameters);
	}

	/**
	 * 外部倉庫キープ数を追加する
	 * @param dto
	 * @throws DaoException
	 */
	public void registryExternalKeep(ExtendKeepDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_EXTERNAL_KEEP", parameters);
	}

	/**
	 * KTS倉庫キープの情報を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getKeepList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}


	/**
	 * 外部倉庫倉庫キープの情報を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getExternalKeepList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysExternalWarehouseCode", "");
		parameters.addParameter("orderNo", null);

		return selectList("SEL_EXTERNAL_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}

	/**
	 * KTS倉庫キープの情報を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getKeepList(String orderNo) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("orderNo", orderNo);
		parameters.addParameter("sysItemId", 0);

		return selectList("SEL_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}

	/**
	 * 楽天倉庫キープの情報を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getExternalKeepList(long sysItemId, String externalWarehouseCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysExternalWarehouseCode", externalWarehouseCode);

		return selectList("SEL_EXTERNAL_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}

	/**
	 * 楽天倉庫キープの情報を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getExternalKeepList(String orderNo, String externalWarehouseCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("orderNo", orderNo);
		parameters.addParameter("sysExternalWarehouseCode", externalWarehouseCode);
		parameters.addParameter("sysItemId", 0);

		return selectList("SEL_EXTERNAL_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}

	/**
	 * キープの個数を取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getKeepNumList(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_KEEP_NUM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}


	/**
	 * 楽天倉庫キープの個数を取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getExternalKeepNumList(long sysItemId, String sysExternalWarehouseCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysExternalWarehouseCode", sysExternalWarehouseCode);

		return selectList("SEL_EXTERNAL_KEEP_NUM", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}

	/**
	 * キープ情報を取得する。
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public ExtendKeepDTO getKeepDTO(ExtendKeepDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}

	/**
	 * [外部倉庫用]
	 * キープ情報を取得する。
	 * @param dto
	 * @param sysExternalWarehouseCode
	 * @return
	 * @throws DaoException
	 */
	public ExtendKeepDTO getExternalKeepDTO(ExtendKeepDTO dto, String sysExternalWarehouseCode) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		dto.setSysExternalWarehouseCode(sysExternalWarehouseCode);
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_EXTERNAL_KEEP", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepDTO.class));
	}

	/**
	 * キープ数を更新する
	 * @param dto
	 * @throws DaoException
	 */
	public void updateKeep(ExtendKeepDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_KEEP", parameters);
	}


	/**
	 * 外部倉庫キープ数を更新する
	 * @param dto
	 * @throws DaoException
	 */
	public void updateExternalKeep(ExtendKeepDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_EXTERNAL_KEEP", parameters);
	}

	public void deleteKeep(long sysKeepId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysKeepId", sysKeepId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("DEL_KEEP", parameters);
	}

	public void deleteExternalKeep(long sysExternalKeepId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysExternalKeepId", sysExternalKeepId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("DEL_EXTERNAL_KEEP", parameters);
	}

	public void deleteExternalKeepOfItem(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("DEL_EXTERNAL_KEEP_OF_ITEM", parameters);
	}


	/**
	 * 仮在庫数更新
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void updateTemporaryStockNum(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_TEMPORARY_STOCK_NUM", parameters);
	}

	/**
	 * 全セット商品組立可数計算
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public int setAllAssemblyNum() throws DaoException {
		int result = 0;
		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		result = select("SELECT_PALLCALCASSEMBLYNUM", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
		return result;
	}

	public List<ExtendSetItemDTO> getParentSetItemList(long sysItemId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		List<ExtendSetItemDTO> list = selectList("PARENT_SET_ITEM_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
		if ((list == null) || (list.size() == 0))
			return null;
		
		return list;
	}

	/**
	 * 出庫分類フラグが1：構成商品から出庫のものを取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getParentSetItemLeaveOnList(long sysItemId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("PARENT_SET_ITEM_LEAVE_ON_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	/**
	 * [概要]不良在庫を登録する
	 * @param dto
	 * @throws DaoException
	 */
	public void registryDeadStock(DeadStockDTO dto) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_DEAD_STOCK", parameters);
	}


	/**
	 * [概要]不良在庫リストを取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<DeadStockDTO> getDeadStockList(long sysItemId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_DEAD_STOCK", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DeadStockDTO.class));
	}


	/**
	 * [概要]更新履歴リストを取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<UpdateDataHistoryDTO> getUpdateDataHistoryList(long sysItemId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_UPDATE_DATA_HISTORY", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(UpdateDataHistoryDTO.class));
	}


	/**
	 * [概要]不良在庫を削除する
	 * @param sysDeadStockId
	 * @throws DaoException
	 */
	public void deleteDeadStock(long sysDeadStockId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysDeadStockId", sysDeadStockId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("DEL_DEAD_STOCK", parameters);
	}


	/**
	 * [概要]不良在庫を更新する
	 * @param dto
	 * @throws DaoException
	 */
	public void updateDeadStock(DeadStockDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEAD_STOCK", parameters);
	}


	/**
	 * [概要]仕入先リストを取得する
	 * @param sysSupplierId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstSupplierDTO> getSupplierList(long sysSupplierId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSupplierId", sysSupplierId);

		return selectList("SEL_SUPPLIER_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstSupplierDTO.class));
	}


	/**
	 * [概要]仕入先情報を取得する
	 * @param sysSupplierId
	 * @return
	 * @throws DaoException
	 */
	public ExtendMstSupplierDTO getSupplier(long sysSupplierId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSupplierId", sysSupplierId);

		return select("SEL_SUPPLIER_LIST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstSupplierDTO.class));
	}

	/**
	 * 年間販売数の登録
	 * @param dto
	 * @throws DaoException
	 */
	public void registryAnnualSales(AnnualSalesDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysAnnualSalesId", dto.getSysAnnualSalesId());
		parameters.addParameter("sysItemId", dto.getSysItemId());
		update("INS_ANNUAL_SALES", parameters);
	}

	/**
	 * [概要]年間販売数を削除する
	 * @param sysDeadStockId
	 * @throws DaoException
	 */
	public void deleteAnnualSales(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("DEL_ANNUAL_SALES", parameters);
	}

	/**
	 * [概要]海外注文商品を削除する
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void deleteForeignItem(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("DEL_FOREIGN_ITEM", parameters);
	}

	/**
	 * 更新情報を更新する
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void updateInfo(long sysItemId) throws DaoException  {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_UPDATE_INFO", parameters);
	}
}