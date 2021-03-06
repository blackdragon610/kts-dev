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
	 * ?????????????????????Excel????????????SQL
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
	 * ??????????????????
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
	 * ??????????????????:?????????????????????????????????20170328?????????
	 * @param arrivalScheduleDTO
	 * @throws DaoException
	 */
	public void insertArrivalSchedule(ArrivalScheduleDTO arrivalScheduleDTO) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(arrivalScheduleDTO, parameters);

		parameters.addParameter("arrivalFlag", "0");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ARRIVAL_SCHEDULE", parameters);
	}

	/**TODO
	 * ???????????????????????????:?????????????????????????????????20170328?????????
	 * @param backOrderDTO
	 * @throws DaoException
	 */
	public void insertBackOrder(BackOrderDTO backOrderDTO) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(backOrderDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_BACK_ORDER", parameters);
	}

	/**
	 * ??????????????????
	 * @param itemCostDTO
	 * @throws DaoException
	 */
	public void insertItemCost(ItemCostDTO itemCostDTO) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemCostDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ITEM_COST", parameters);
	}

	/**
	 * ??????????????????
	 * @param itemPriceDTO
	 * @throws DaoException
	 */
	public void insertItemPrice(ItemPriceDTO itemPriceDTO) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemPriceDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ITEM_PRICE", parameters);
	}

	/**
	 * ??????????????????
	 * @param codeCollationDTO
	 * @throws DaoException
	 */
	public void insertCodeCollation(CodeCollationDTO codeCollationDTO) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(codeCollationDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_CODE_COLLATION", parameters);

	}

	/**
	 * ?????????????????????
	 * @param setItemDTO
	 * @throws DaoException
	 */
	public void insertSetItem(SetItemDTO setItemDTO) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(setItemDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_SET_ITEM", parameters);
	}

	/**
	 * DB?????????????????????????????????????????????????????????????????????????????????
	 *
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void stockSum(long sysItemId) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_STOCK_SUM", parameters);
	}

	/**
	 * ??????????????????????????????????????????
	 *
	 * @param itemCode
	 * @return
	 * @throws DaoException
	 */
	public Integer itemCodeRepeatCheck(String itemCode) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("itemCode", itemCode);

		return select("SEL_ITEM_CODE_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * ??????????????????????????????????????????????????????????????????ID???????????????????????????????????????
	 *
	 * @param itemCode
	 * @return
	 * @throws DaoException
	 */
	public List<ItemCostDTO> getNotFindItemCost(long sysItemId) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("SEL_NOT_FIND_ITEM_COST", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ItemCostDTO.class));
	}

	/**
	 * ??????????????????????????????????????????????????????????????????ID???????????????????????????????????????
	 *
	 * @param itemCode
	 * @return
	 * @throws DaoException
	 */
	public List<ItemPriceDTO> getNotFindItemPrice(long sysItemId) throws DaoException {
		// TODO ?????????????????????????????????????????????
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
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_ITEM_COST_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public Integer itemPriceRepeatCheck(long sysItemId, long sysCorporationId) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_ITEM_PRICE_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public Integer sysWarehouseIdCheck(long sysItemId, long sysWarehouseId) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("sysWarehouseId", sysWarehouseId);

		return select("SEL_SYS_WAREHOUSE_STOCK_ID_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	public Integer priorityRepeatCheck(long sysItemId, String priority) throws DaoException {
		// TODO ?????????????????????????????????????????????
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);
		parameters.addParameter("priority", priority);

		return select("SEL_PRIORITY_REPEAT_CHECK", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	//20140405 ??????
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
	 * ???????????????????????????????????????
	 * @param sysItemId
	 * @param sysCorporationId
	 * @param itemCost
	 * @return
	 * @throws DaoException
	 */
	public int updateCostInfo(long sysItemId, String sysCorporationId, Integer itemCost) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();

		//????????????ID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		//??????????????????ID
		parameters.addParameter("sysItemId", sysItemId);
		//??????????????????ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//??????
		parameters.addParameter("itemCost", itemCost);
		//???????????????
		parameters.addParameter("deleteFlag", "0");

		//SQL??????
		return update("UPDATE_COST_INFO", parameters);
	}

	/**
	 * ???????????????????????????????????????
	 * @param sysItemId
	 * @param sysCorporationId
	 * @param itemPrice
	 * @return
	 * @throws DaoException
	 */
	public int updatePriceInfo(long sysItemId, String sysCorporationId, Integer itemPrice) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();

		//????????????ID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		//??????????????????ID
		parameters.addParameter("sysItemId", sysItemId);
		//??????????????????ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//??????
		parameters.addParameter("itemPrice", itemPrice);
		//???????????????
		parameters.addParameter("deleteFlag", "0");

		//SQL??????
		return update("UPDATE_PRICE_INFO", parameters);
	}

	/**
	 * ???????????????????????????????????????
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
		//????????????????????????ID
		parameters.addParameter("sysItemCostId", maxSysItemCostId);
		//????????????ID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("createUserId", userInfo.getUserId());
		//??????????????????ID
		parameters.addParameter("sysItemId", sysItemId);
		//??????????????????ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//??????
		parameters.addParameter("cost", itemCost);
		//???????????????
		parameters.addParameter("deleteFlag", "0");

		//SQL??????
		return update("INS_ITEM_COST", parameters);
	}

	/**
	 * ???????????????????????????????????????
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
		//????????????????????????ID
		parameters.addParameter("sysItemPriceId", maxSysItemPriceId);
		//????????????ID
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("createUserId", userInfo.getUserId());
		//??????????????????ID
		parameters.addParameter("sysItemId", sysItemId);
		//??????????????????ID
		parameters.addParameter("sysCorporationId", Integer.valueOf(sysCorporationId));
		//??????
		parameters.addParameter("price", itemPrice);
		//???????????????
		parameters.addParameter("deleteFlag", "0");

		//SQL??????
		return update("INS_ITEM_PRICE", parameters);
	}

	/**
	 * ??????????????????????????????????????????????????????????????????
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
	 * ???????????????????????????
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
