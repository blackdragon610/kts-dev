package jp.co.kts.dao.fileImport;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.ArrivalScheduleDTO;
import jp.co.kts.app.common.entity.BackOrderDTO;
import jp.co.kts.app.common.entity.CodeCollationDTO;
import jp.co.kts.app.common.entity.ItemCostDTO;
import jp.co.kts.app.common.entity.ItemPriceDTO;
import jp.co.kts.app.common.entity.MstChannelDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstGroupNmDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.common.entity.SetItemDTO;
import jp.co.kts.app.common.entity.WarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.dao.common.SequenceDAO;

public class ExcelImportDAO extends BaseDAO {

	public MstGroupNmDTO checkSysGroupId(long sysGroupNmId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysGroupNmId", sysGroupNmId);

		return select("SEL_CHECK_SYS_GROUP_ID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstGroupNmDTO.class));
	}

	public MstCorporationDTO checkSysCorporationId(long sysCorporationIdLn) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", sysCorporationIdLn);

		return select("SEL_CHECK_SYS_CORPORATION_ID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstCorporationDTO.class));
	}

	public MstChannelDTO checkSysChannelId(long sysChannelIdLn) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysChannelId", sysChannelIdLn);

		return select("SEL_CHECK_SYS_CHANNEL_ID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstChannelDTO.class));
	}

	public MstWarehouseDTO checkSysWarehouseId(long sysWarehouseIdLn) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysWarehouseId", sysWarehouseIdLn);

		return select("SEL_CHECK_SYS_WAREHOUSE_ID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstWarehouseDTO.class));
	}

	public MstItemDTO checkSysItemId(String itemCode) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("itemCode", itemCode);

		return select("SEL_GET_SYS_ITEM_ID", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstItemDTO.class));
	}

	public Integer getSysItemId(String itemCode) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("itemCode", itemCode);

		return select("SEL_GET_SYS_ITEM_ID", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public void insertMstItem(MstItemDTO mstItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(mstItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_MST_ITEM", parameters);

	}

	/**
	 * 新規の商品情報Excelの登録用SQL
	 * @param mstItemDTO
	 * @throws DaoException
	 */
	public void insertMstItemDetail(MstItemDTO mstItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(mstItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_MST_ITEM", parameters);

	}

	/**
	 * 在庫倉庫登録
	 * @param warehouseStockDTO
	 * @throws DaoException
	 */
	public void insertWarehouseStock(WarehouseStockDTO warehouseStockDTO) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(warehouseStockDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_WAREHOUSE_STOCK", parameters);
	}

	/**TODO
	 * 入荷予定登録:現在未使用【廃棄予定‗20170328時点】
	 * @param arrivalScheduleDTO
	 * @throws DaoException
	 */
	public void insertArrivalSchedule(ArrivalScheduleDTO arrivalScheduleDTO) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(arrivalScheduleDTO, parameters);

		parameters.addParameter("arrivalFlag", "0");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ARRIVAL_SCHEDULE", parameters);
	}

	/**TODO
	 * バックオーダー登録:現在未使用【廃棄予定‗20170328時点】
	 * @param backOrderDTO
	 * @throws DaoException
	 */
	public void insertBackOrder(BackOrderDTO backOrderDTO) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(backOrderDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_BACK_ORDER", parameters);
	}

	/**
	 * 商品原価登録
	 * @param itemCostDTO
	 * @throws DaoException
	 */
	public void insertItemCost(ItemCostDTO itemCostDTO) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemCostDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ITEM_COST", parameters);
	}

	/**
	 * 商品売価登録
	 * @param itemPriceDTO
	 * @throws DaoException
	 */
	public void insertItemPrice(ItemPriceDTO itemPriceDTO) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemPriceDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ITEM_PRICE", parameters);
	}

	/**
	 * 品番照合登録
	 * @param codeCollationDTO
	 * @throws DaoException
	 */
	public void insertCodeCollation(CodeCollationDTO codeCollationDTO) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(codeCollationDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_CODE_COLLATION", parameters);

	}

	/**
	 * セット商品登録
	 * @param setItemDTO
	 * @throws DaoException
	 */
	public void insertSetItem(SetItemDTO setItemDTO) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(setItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_SET_ITEM", parameters);
	}

	/**
	 * DB内の在庫数を集計し、商品マスタの総合計数へ反映します。
	 *
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void stockSum(long sysItemId) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_STOCK_SUM", parameters);
	}

	/**
	 * 品番の重複チェックをします。
	 *
	 * @param itemCode
	 * @return
	 * @throws DaoException
	 */
	public Integer itemCodeRepeatCheck(String itemCode) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("itemCode", itemCode);

		return select("SEL_ITEM_CODE_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * 原価テーブルに不足しているデータ（品番＋会社IDの組み合わせ）を探します。
	 *
	 * @param itemCode
	 * @return
	 * @throws DaoException
	 */
	public List<ItemCostDTO> getNotFindItemCost(long sysItemId) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_NOT_FIND_ITEM_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ItemCostDTO.class));
	}

	/**
	 * 売価テーブルに不足しているデータ（品番＋会社IDの組み合わせ）を探します。
	 *
	 * @param itemCode
	 * @return
	 * @throws DaoException
	 */
	public List<ItemPriceDTO> getNotFindItemPrice(long sysItemId) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_NOT_FIND_ITEM_PRICE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ItemPriceDTO.class));
	}

	public List<WarehouseStockDTO> getNotFindItemWarehouse(long sysItemId)throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_NOT_FIND_ITEM_WAREHOUSE", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(WarehouseStockDTO.class));
	}

	public Integer itemCostRepeatCheck(long sysItemId, long sysCorporationId) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_ITEM_COST_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public Integer itemPriceRepeatCheck(long sysItemId, long sysCorporationId) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_ITEM_PRICE_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public Integer sysWarehouseIdCheck(long sysItemId, long sysWarehouseId) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysWarehouseId", sysWarehouseId);

		return select("SEL_SYS_WAREHOUSE_STOCK_ID_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public Integer priorityRepeatCheck(long sysItemId, String priority) throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("priority", priority);

		return select("SEL_PRIORITY_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	//20140405 八鍬
	public void updateMstItemFlg(SetItemDTO setItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(setItemDTO, parameters);
		parameters.addParameter("setItemFlg", "1");
		parameters.addParameter("leaveClassFlg", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_MST_ITEM_FLG", parameters);
	}

	public int updateItem(MstItemDTO mstItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(mstItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_EXCEL_MST_ITEM", parameters);
	}

	public int updateItemInfo(MstItemDTO mstItemDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(mstItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_MST_ITEM_EXCEL", parameters);
	}

	public int updateWarehouseStock(WarehouseStockDTO warehouseStockDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(warehouseStockDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_EXCEL_WAREHOUSE_STOCK", parameters);

	}

	/**
	 * 価格情報の原価を更新します
	 * @param sysItemId
	 * @param sysCorporationId
	 * @param itemCost
	 * @return
	 * @throws DaoException
	 */
	public int updateCostInfo(long sysItemId, String sysCorporationId, Integer itemCost) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();

		//ユーザーID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		//システム商品ID
		parameters.addParameter("sysItemId", sysItemId);
		//システム法人ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//原価
		parameters.addParameter("itemCost", itemCost);
		//削除フラグ
		parameters.addParameter("deleteFlag", "0");

		//SQL実行
		return update("UPDATE_COST_INFO", parameters);
	}

	/**
	 * 価格情報の売価を更新します
	 * @param sysItemId
	 * @param sysCorporationId
	 * @param itemPrice
	 * @return
	 * @throws DaoException
	 */
	public int updatePriceInfo(long sysItemId, String sysCorporationId, Integer itemPrice) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();

		//ユーザーID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		//システム商品ID
		parameters.addParameter("sysItemId", sysItemId);
		//システム法人ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//原価
		parameters.addParameter("itemPrice", itemPrice);
		//削除フラグ
		parameters.addParameter("deleteFlag", "0");

		//SQL実行
		return update("UPDATE_PRICE_INFO", parameters);
	}

	/**
	 * 価格情報の原価を登録します
	 * @param sysItemId
	 * @param sysCorporationId
	 * @param itemCost
	 * @return
	 * @throws DaoException
	 */
	public int insertCostInfo(long sysItemId, String sysCorporationId, Integer itemCost) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		SequenceDAO dao = new SequenceDAO();
		long maxSysItemCostId = dao.getMaxSysItemCostId() + 1;
		//システム商品原価ID
		parameters.addParameter("sysItemCostId", maxSysItemCostId);
		//ユーザーID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("createUserId", userInfo.getUserId());
		//システム商品ID
		parameters.addParameter("sysItemId", sysItemId);
		//システム法人ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//原価
		parameters.addParameter("cost", itemCost);
		//削除フラグ
		parameters.addParameter("deleteFlag", "0");

		//SQL実行
		return update("INS_ITEM_COST", parameters);
	}

	/**
	 * 価格情報の売価を登録します
	 * @param sysItemId
	 * @param sysCorporationId
	 * @param itemPrice
	 * @return
	 * @throws DaoException
	 */
	public int insertPriceInfo(long sysItemId, String sysCorporationId, Integer itemPrice) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		SequenceDAO dao = new SequenceDAO();
		long maxSysItemPriceId = dao.getMaxSysItemPriceId() + 1;
		//システム商品売価ID
		parameters.addParameter("sysItemPriceId", maxSysItemPriceId);
		//ユーザーID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("createUserId", userInfo.getUserId());
		//システム商品ID
		parameters.addParameter("sysItemId", sysItemId);
		//システム法人ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//原価
		parameters.addParameter("price", itemPrice);
		//削除フラグ
		parameters.addParameter("deleteFlag", "0");

		//SQL実行
		return update("INS_ITEM_PRICE", parameters);
	}

	/**
	 * 商品マスタの仕入金額と加算経費を登録します。
	 * @param sysItemId
	 * @param price
	 * @param updType
	 * @return
	 * @throws DaoException
	 */
	public int updateMstItemCostOrLoading(long sysItemId, long price, String updType) throws DaoException{
		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("price", price);
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("updType", updType);

		return update("UPD_MST_ITEM_COST_LOADING", parameters);
	}

	/**
	 * 倉庫在庫を取得する
	 * @param sysWarehouseStockId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendWarehouseStockDTO> selectWarehouseStockInfo(long sysWarehouseStockId) throws DaoException{

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysWarehouseStockId", sysWarehouseStockId);

		return selectList("SEL_WAREHOUSE_STOCK_INFO", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class) );
	}
}
