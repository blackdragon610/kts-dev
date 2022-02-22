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
import jp.co.kts.app.common.entity.ForeignSlipSearchDTO;
import jp.co.kts.app.common.entity.WarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;

/**
 * [概要]海外注文管理daoクラス
 *
 * @author Boncre
 *
 */
public class ForeignOrderDAO extends BaseDAO {

	// private static final int IS_EMPTY_NUM = 0;

	SQLParameters parameters = new SQLParameters();

	/**
	 * [概要]仕入先リストを取得する処理
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstSupplierDTO> getSupplierList(long sysSupplierId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		// addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("sysSupplierId", sysSupplierId);
		// parameters.addParameter("paymentTerms1",dto.getPaymentTerms1());
		// parameters.addParameter("paymentTerms2",dto.getPaymentTerms2());
		// parameters.addParameter("leadTime",dto.getLeadTime());

		return selectList("SEL_SEARCH_SUPPLIER", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendMstSupplierDTO.class));
	}

	/**
	 * [概要]詳細画面時仕入先リストを取得する処理
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public ExtendMstSupplierDTO getDetailSupplierList(long sysSupplierId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSupplierId", sysSupplierId);

		return select("SEL_SEARCH_SUPPLIER_DETAIL", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendMstSupplierDTO.class));
	}

	/**
	 * サブウィンドウ、海外注文商品情報検索
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public List<ExtendForeignOrderItemDTO> getForeignItem(SearchItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// 商品コード
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("itemCode", dto.getItemCode() + "%");
		}
		// 商品コードFrom
		if (StringUtils.isNotEmpty(dto.getItemCodeFrom())) {
			parameters.addParameter("itemCodeFrom", dto.getItemCodeFrom());
		}
		// 商品コードTo
		if (StringUtils.isNotEmpty(dto.getItemCodeTo())) {
			parameters.addParameter("itemCodeTo", dto.getItemCodeTo());
		}
		// 商品名
		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");
		}
		// システム仕入先ＩＤ
		if (StringUtils.isNotEmpty(dto.getSysSupplierId())) {
			//商品コードが00の場合は仕入れ先IDは無視
			if (StringUtils.equals(dto.getItemCode(), "00")) {
				parameters.addParameter("sysSupplierId", "");
			} else {
				parameters.addParameter("sysSupplierId",
						Long.valueOf(dto.getSysSupplierId()));
			}
		}

		return selectList(
				"SEL_FOREIGN_ITEM_SEARCH",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderItemDTO.class));
	}

	/**
	 * 伝票情報取得
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public ExtendForeignOrderDTO getForeignOrderSlipSearch(long sysForeignSlipId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		// システム海外注文伝票ＩＤ
		parameters.addParameter("sysForeignSlipId",
				Long.valueOf(sysForeignSlipId));

		return select(
				"SEL_FOREIGN_SLIP_SEARCH",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderDTO.class));
	}

	/**
	 * 伝票登録商品検索
	 *
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendForeignOrderItemDTO> getForeignOrderSlipItemSearch(
			long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		// システム海外注文伝票ＩＤ
		parameters.addParameter("sysForeignSlipId",
				Long.valueOf(sysForeignSlipId));

		return selectList(
				"SEL_FOREIGN_SLIP_ITEM_SEARCH",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderItemDTO.class));
	}

	/**
	 * PDF注文書用商品検索
	 *
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendForeignOrderItemDTO> getForeignOrderItemSearch(
			long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		// システム海外注文伝票ＩＤ
		parameters.addParameter("sysForeignSlipId",
				Long.valueOf(sysForeignSlipId));

		return selectList(
				"SEL_FOREIGN_ORDER_SLIP_ITEM_SEARCH",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderItemDTO.class));
	}

	/**
	 * 海外注文伝票登録
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int registryForeignOrderSlip(ExtendForeignOrderDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// 納期超過フラグをセット
		parameters.addParameter("deliveryDate1OverFlag", "1");
		parameters.addParameter("deliveryDate2OverFlag", "1");

		// ユーザー情報登録
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		int result = update("INS_FOREIGN_ORDER_SLIP", parameters);
		return result;
	}

	/**
	 * 商品登録、商品CODE重複チェック
	 *
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public ExtendForeignOrderItemDTO ForeignOrderItemValidate(String itemCode)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("itemCode", itemCode);
		parameters.addParameter("setFlg", "1");

		return select(
				"SEL_FOREIGN_ITEM_SEARCH",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderItemDTO.class));
	}

	/**
	 * 仕入先IDと商品が紐付いているか検索
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public ExtendForeignOrderItemDTO validateItemSupplier(
			ExtendForeignOrderItemDTO dto, long sysSupplierId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("setFlg", 1);

		// 商品コード
		if (StringUtils.isNotEmpty(dto.getItemCode())) {
			parameters.addParameter("itemCode", dto.getItemCode());
		}

		// システム仕入先ＩＤ
		if (sysSupplierId != 0) {
			parameters.addParameter("sysSupplierId",
					Long.valueOf(sysSupplierId));
		}

		return select(
				"SEL_FOREIGN_ITEM_SEARCH",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderItemDTO.class));
	}

	/**
	 * 入荷予定日の登録
	 *
	 * @param dto
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public int registryForeignOrderSlipArrival(ExtendForeignOrderDTO dto,
			long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);

		// ユーザー情報登録
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		int result = update("INS_FOREIGN_ORDER_SLIP_ARRIVAL", parameters);
		return result;
	}

	/**
	 * 海外注文商品登録
	 *
	 * @param addItemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public int registryForeignOrderItemList(
			ExtendForeignOrderItemDTO addItemList, long sysForeignSlipId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(addItemList, parameters);
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);
		parameters.addParameter("orderNum", addItemList.getOrderNum());

		// ユーザー情報取得
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		//システム海外注文商品IDを付与
		parameters.addParameter("sysForeignSlipItemId",	addItemList.getSysForeignSlipItemId());

		int resultCnt = update("INS_FOREIGN_ORDER_ITEM", parameters);

		return resultCnt;
	}

	/**
	 * 商品の入荷予定日、注文数の登録
	 *
	 * @param dto
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public int registryArrival(ExtendForeignOrderItemDTO dto,
			long sysForeignSlipId, String orderStatus, String orderDate)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);
		parameters.addParameter("orderStatus", orderStatus);
		parameters.addParameter("orderDate", orderDate);
		parameters.addParameter("sysForeignSlipItemId", dto.getSysForeignSlipItemId());

		// ユーザー情報登録
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		int result = update("INS_FOREIGN_ARRIVAL_SCHEDULE", parameters);
		return result;
	}

	/**
	 * 商品のorderPoolflagを０に設定
	 *
	 * @param dto
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public int setOrderPoolFlag(ExtendForeignOrderItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// ユーザー情報登録
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		int result = update("UPD_ITEM_ORDER_POOL_FLAG", parameters);
		return result;
	}

	/**
	 * [概要]海外注文伝票を更新する
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateForeignOrderSlip(ExtendForeignOrderDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		// インスタンスの生成
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		// 更新日付、更新したユーザー情報を格納
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId())
						+ "} ");

		// 訂正フラグ確認
		// if (StringUtils.isNotEmpty(dto.getCorrectionFlag())) {
		// parameters.addParameter("correctionFlag", dto.getCorrectionFlag());
		// }

		int result = update("UPD_FOREIGN_ORDER_SLIP", parameters);
		return result;
	}

	/**
	 * 倉庫情報検索 STOCK_NUM取得
	 *
	 * @param dto
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public ExtendWarehouseStockDTO getWarehouseStock(
			ExtendForeignOrderItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return select(
				"SEL_WAREHOUSE_FOREIGN_SEARCH",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	/**
	 * 伝票入荷予定日の更新
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateForeignOrderSlipArrival(ExtendForeignOrderDTO dto,
			long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);
		parameters.addParameter("sysSupplierId", dto.getSysSupplierId());

		// インスタンスの生成
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		// 更新日付、更新したユーザー情報を格納
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId())
						+ "} ");

		int result = update("UPD_FOREIGN_ORDER_SLIP", parameters);
		return result;
	}

	/**
	 * 海外注文商品更新
	 *
	 * @param addItemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public int updateForeignSlipItem(ExtendForeignOrderItemDTO itemList,
			long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		int result = 1;
		addParametersFromBeanProperties(itemList, parameters);
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);

		// ユーザー情報取得
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		// 更新日付、更新したユーザー情報を格納
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId())
						+ "} ");

		result = update("UPD_FOREIGN_ORDER_ITEM", parameters);

		return result;
	}

	/**
	 * 商品入荷日、注文数の更新
	 *
	 * @param itemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public int updateForeignSlipItemArrival(ExtendForeignOrderItemDTO itemList,
			long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemList, parameters);
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);

		// 注文数が０だったら入荷予定フラグをセット
		if (itemList.getArrivalNum() == 0) {
			parameters.addParameter("arrivalFlag", "1");
		}

		// ユーザー情報取得
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		// 更新日付、更新したユーザー情報を格納
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId())
						+ "} ");

		int result = update("UPD_FOREIGN_ORDER_ITEM_ARRIVAL", parameters);

		return result;
	}

	/**
	 * 入荷数分入荷処理
	 *
	 * @param dto
	 * @throws DaoException
	 *             nozawa
	 */
	public void registryArrivalHistory(ExtendForeignOrderItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("arrivalFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ARRIVAL_SCHEDULE_HISTORY", parameters);

	}

	public void registryArrivalHistory(ExtendArrivalScheduleDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("arrivalFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("INS_ARRIVAL_SCHEDULE_FOREIGN", parameters);

	}

	/**
	 * 残り入荷数を更新する
	 *
	 * @param dto
	 * @throws DaoException
	 *             nozawa
	 */
	public int updateArrivalNum(ExtendForeignOrderItemDTO dto,
			String orderStatus, String orderDate) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("sysArrivalScheduleId",	dto.getSysArrivalScheduleId());
		parameters.addParameter("arrivalNum", dto.getArrivalNum());
		parameters.addParameter("orderStatus", orderStatus);
		parameters.addParameter("ItemOrderDate", orderDate);

		// ユーザ情報取得
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		int result = update("UPD_ARRIVAL_SCHEDULE_PART_FOREIGN", parameters);
		return result;
	}

	/**
	 * 入荷予定テーブルへ更新
	 *
	 * @param dto
	 * @throws DaoException
	 *             nozawa
	 */
	public int updateArrivalSchedule(ExtendForeignOrderItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		int result = update("UPD_FOREIGN_ARRIVAL_SCHEDULE", parameters);
		return result;
	}

	/**
	 * 入荷予定テーブルへ新規登録
	 *
	 * @param dto
	 * @throws DaoException
	 *             nozawa
	 */
	public int registryArrivalSchedule(ExtendForeignOrderItemDTO dto,
			String orderStatus) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);
		parameters.addParameter("orderStatus", orderStatus);
		parameters.addParameter("sysArrivalScheduleId",
				dto.getSysArrivalScheduleId());
		parameters.addParameter("sysForeignSlipId", dto.getSysForeignSlipId());
		parameters.addParameter("sysItemId", dto.getSysItemId());
		parameters.addParameter("arrivalScheduleDate",
				dto.getArrivalScheduleDate());
		parameters.addParameter("vagueArrivalSchedule",
				dto.getVagueArrivalSchedule());
		parameters.addParameter("orderDate", dto.getItemOrderDate());
		parameters.addParameter("arrivalNum", dto.getOrderNum());
		parameters.addParameter("sysForeignSlipItemId", dto.getSysForeignSlipItemId());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		int result = update("INS_FOREIGN_ARRIVAL_SCHEDULE", parameters);

		return result;
	}

	/**
	 * 倉庫入荷 倉庫在庫数更新処理
	 *
	 * @param dto
	 * @throws DaoException
	 *             nozawa
	 */
	public int updateWarehouseStock(ExtendForeignOrderItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		int result = update("UPD_WAREHOUSE_STOCK_FOREIGN_ITEM", parameters);

		return result;
	}

	/**
	 * 倉庫在庫情報登録処理
	 *
	 * @param dto
	 * @throws DaoException
	 *             nozawa
	 */
	public int registryWarehouseStock(ExtendWarehouseStockDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("INS_WAREHOUSE_STOCK", parameters);
	}

	/**
	 * 登録済み商品削除
	 *
	 * @param itemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 *             nozawa
	 */
	public int deleteItem(ExtendForeignOrderItemDTO itemList)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(itemList, parameters);

		// ユーザー情報取得
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		// 削除日付、削除したユーザー情報を格納
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " DELETE_" + String.valueOf(userInfo.getUserId())
						+ "} ");

		return update("DEL_FOREIGN_ORDER_ITEM", parameters);
	}

	/**
	 * [概要]海外注文商品の入荷予定を単品削除する
	 *
	 * @param sysForeignSlipId
	 * @throws DaoException
	 */
	public int deleteArrivalItem(ExtendForeignOrderItemDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", dto.getSysForeignSlipId());
		parameters.addParameter("sysItemId", dto.getSysItemId());
		parameters.addParameter("sysForeignSlipItemId", dto.getSysForeignSlipItemId());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("UPD_DEL_FOREIGN_ITEM_ARRIVAL", parameters);
	}

	/**
	 * [概要]海外注文伝票ID検索
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendForeignOrderDTO> getForeignOrderIdSearch(
			ForeignSlipSearchDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		// 品番
		if (StringUtils.isNotEmpty(dto.getItemCode())) {

			switch (dto.getSearchMethod1()) {

			case "1":
				parameters.addParameter("itemCode", dto.getItemCode() + "%");
				break;
			case "2":
				parameters.addParameter("itemCode", "%" + dto.getItemCode()
						+ "%");
				break;
			case "3":
				parameters.addParameter("itemCode", "%" + dto.getItemCode());
				break;
			}

			// 商品検索フラグをセット
			parameters.addParameter("itemSearchFlag", 1);

		}

		// 工場品番
		if (StringUtils.isNotEmpty(dto.getFactoryItemCode())) {

			switch (dto.getSearchMethod2()) {

			case "1":
				parameters.addParameter("factoryItemCode",
						dto.getFactoryItemCode() + "%");
				break;
			case "2":
				parameters.addParameter("factoryItemCode",
						"%" + dto.getFactoryItemCode() + "%");
				break;
			case "3":
				parameters.addParameter("factoryItemCode",
						"%" + dto.getFactoryItemCode());
				break;
			}

			// 商品検索フラグをセット
			parameters.addParameter("itemSearchFlag", 1);
		}

		// 曖昧入荷日
		if (StringUtils.isNotEmpty(dto.getVagueArrivalSchedule())) {
			parameters.addParameter("vagueArrivalSchedule",
					"%" + dto.getVagueArrivalSchedule() + "%");

			// 商品検索フラグをセット
			parameters.addParameter("itemSearchFlag", 1);
		}

		// 商品名
		if (StringUtils.isNotEmpty(dto.getItemNm())) {
			parameters.addParameter("itemNm", "%" + dto.getItemNm() + "%");

			// 商品検索フラグをセット
			parameters.addParameter("itemSearchFlag", 1);
		}

		// 会社・工場名
		if (StringUtils.isNotEmpty(dto.getCompanyFactoryNm())) {
			parameters.addParameter("companyFactoryNm",
					"%" + dto.getCompanyFactoryNm() + "%");
		}

		// インボイスNo.
		if (StringUtils.isNotEmpty(dto.getInvoiceNo())) {
			parameters
					.addParameter("invoiceNo", "%" + dto.getInvoiceNo() + "%");
		}

		// PoNo.
		if (StringUtils.isNotEmpty(dto.getPoNo())) {
			parameters.addParameter("poNo", "%" + dto.getPoNo() + "%");
		}

		// 入荷状態フラグが4でない場合、商品検索フラグをセット
		if (!dto.getArriveStatusFlg().equals("4")) {
			parameters.addParameter("itemSearchFlag", 1);
		}

		// 入荷日が空でない場合、商品検索フラグをセット
		if (StringUtils.isNotEmpty(dto.getArrivalScheduleDateFrom())
				|| StringUtils.isNotEmpty(dto.getArrivalScheduleDateTo())) {
			parameters.addParameter("itemSearchFlag", 1);
		}

		// 並び順が入荷日順の場合、商品検索フラグをセット
		if (dto.getSortFirst().equals("2")) {
			parameters.addParameter("itemSearchFlag", 1);
		}

		return selectList(
				"SEL_FOREIGN_ORDER_SLIP_ID",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderDTO.class));
	}

	/**
	 * [概要]海外注文伝票検索
	 *
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public ExtendForeignOrderDTO getForeignOrderSlipIdSearch(
			long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);

		return select(
				"SEL_FOREIGN_ORDER_SLIP",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderDTO.class));
	}

	/**
	 * [概要]海外注文伝票詳細取得処理
	 *
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public ExtendForeignOrderDTO getForeignOrderSlipDetail(long sysForeignSlipId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);

		return select(
				"SEL_FOREIGN_ORDER_SLIP_DETAIL",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderDTO.class));
	}

	/**
	 * [概要]海外注文伝票情報を削除する
	 *
	 * @param sysForeignSlipId
	 * @throws DaoException
	 */
	public int deleteSlip(long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " DELETE_" + String.valueOf(userInfo.getUserId())
						+ "} ");
		return update("UPD_DEL_FOREIGN_SLIP", parameters);
	}

	/**
	 * [概要]海外注文商品を削除する
	 *
	 * @param sysForeignSlipId
	 * @throws DaoException
	 */
	public int deleteItem(long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);
		parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " DELETE_" + String.valueOf(userInfo.getUserId())
						+ "} ");
		return update("UPD_DEL_FOREIGN_ITEM", parameters);
	}

	public int getArrivalScheduleList(long sysForeignSlipId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);

		List<ExtendArrivalScheduleDTO> arrivalList = selectList(
				"SEL_ARRIVAL_SCHEDULE_LIST",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendArrivalScheduleDTO.class));

		return arrivalList.size();
	}

	/**
	 * [概要]海外注文商品の入荷予定を削除する
	 *
	 * @param sysForeignSlipId
	 * @throws DaoException
	 */
	public int deleteArrival(long sysForeignSlipId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);
		// parameters.addParameter("deleteFlag", "1");

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("UPD_DEL_FOREIGN_ITEM_ARRIVAL", parameters);
	}

	/**
	 * 注文ステータスをまとめて更新する
	 *
	 * @param orderDTO
	 * @param orderStatus
	 * @return
	 * @throws DaoException
	 */
	public int updateForeignOrderStatus(ExtendForeignOrderDTO orderDTO,
			String orderStatus) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("sysForeignSlipId",
				orderDTO.getSysForeignSlipId());
		parameters.addParameter("orderStatus", orderStatus);

		return update("UPDATE_FOREIGN_ORDER_STATUS", parameters);
	}

	/**
	 * 支払日をまとめて更新する
	 *
	 * @param orderDTO
	 * @return
	 * @throws DaoException
	 */
	public int updatePaymentDate(ExtendForeignOrderDTO orderDTO)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		parameters.addParameter("sysForeignSlipId",
				orderDTO.getSysForeignSlipId());
		parameters.addParameter("paymentDate1", orderDTO.getPaymentDate1());
		parameters.addParameter("paymentDate2", orderDTO.getPaymentDate2());
		// 更新日付、更新したユーザー情報を格納
		String date = DateUtil.dateToString("yyyy/MM/dd hh:mm:ss");
		parameters.addParameter("historyInfo",
				"{" + date + " UPDATE_" + String.valueOf(userInfo.getUserId())
						+ "} ");

		return update("UPDATE_FOREIGN_SLIP_PAYMENT", parameters);
	}

	/**
	 * [概要]海外商品リストを取得する処理
	 *
	 * @param searchSlipDTO
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendForeignOrderItemDTO> getForeignItemList(
			ForeignSlipSearchDTO searchSlipDTO, long sysForeignSlipId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();

		if (StringUtils.equals(searchSlipDTO.getSearchPriority(), "2")) {

			addParametersFromBeanProperties(searchSlipDTO, parameters);

			if (StringUtils.isNotEmpty(searchSlipDTO.getItemCode())) {

				switch (searchSlipDTO.getSearchMethod1()) {

				case "1":
					parameters.addParameter("itemCode",
							searchSlipDTO.getItemCode() + "%");
					break;
				case "2":
					parameters.addParameter("itemCode",
							"%" + searchSlipDTO.getItemCode() + "%");
					break;
				case "3":
					parameters.addParameter("itemCode",
							"%" + searchSlipDTO.getItemCode());
					break;
				}

			}

			if (StringUtils.isNotEmpty(searchSlipDTO.getFactoryItemCode())) {

				switch (searchSlipDTO.getSearchMethod2()) {

				case "1":
					parameters.addParameter("factoryItemCode",
							searchSlipDTO.getFactoryItemCode() + "%");
					break;
				case "2":
					parameters.addParameter("factoryItemCode", "%"
							+ searchSlipDTO.getFactoryItemCode() + "%");
					break;
				case "3":
					parameters.addParameter("factoryItemCode", "%"
							+ searchSlipDTO.getFactoryItemCode());
					break;
				}
			}

			if (StringUtils.isNotEmpty(searchSlipDTO.getItemNm())) {
				parameters.addParameter("itemNm",
						"%" + searchSlipDTO.getItemNm() + "%");
			}

			if (StringUtils.isNotEmpty(searchSlipDTO.getVagueArrivalSchedule())) {
				parameters.addParameter("vagueArrivalSchedule", "%"
						+ searchSlipDTO.getVagueArrivalSchedule() + "%");
			}

		}

		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);

		return selectList(
				"SEL_FOREIGN_ITEM_LIST",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendForeignOrderItemDTO.class));
	}

	/**
	 * 一覧画面入荷予定テーブルからの検索
	 * @param searchSlipDTO
	 * @param sysForeignSlipId
	 * @param item
	 * @return
	 * @throws DaoException
	 */
	public ExtendArrivalScheduleDTO getForeignOrderArrivalScheduleList(
			ForeignSlipSearchDTO searchSlipDTO, long sysForeignSlipId,
			ExtendForeignOrderItemDTO item) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(searchSlipDTO, parameters);

		parameters.addParameter("sysForeignSlipId", sysForeignSlipId);
		parameters.addParameter("sysItemId", item.getSysItemId());
		parameters.addParameter("sysForeignSlipItemId", item.getSysForeignSlipItemId());

		return select(
				"SEL_FOREIGN_ARRIVAL_SCHEDULE_LIST",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendArrivalScheduleDTO.class));
	}

	public String getPoNoInitial(long sysSupplierId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSupplierId", sysSupplierId);

		return select("SEL_PO_NO_INITIAL", parameters,
				ResultSetHandlerFactory.getFirstColumnStringRowHandler());
	}

	public ExtendArrivalScheduleDTO getArrivalSchedule(
			ExtendArrivalScheduleDTO scheduleDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysArrivalScheduleId",	scheduleDTO.getSysArrivalScheduleId());
		parameters.addParameter("sysForeignSlipId",	scheduleDTO.getSysForeignSlipId());
		parameters.addParameter("sysItemId", scheduleDTO.getSysItemId());
		parameters.addParameter("sysForeignSlipItemId",	scheduleDTO.getSysForeignSlipItemId());

		return select(
				"SEL_FOREIGN_ARRIVAL_SCHEDULE",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendArrivalScheduleDTO.class));
	}

	public ExtendArrivalScheduleDTO getSysArrivalScheduleId(
			ExtendForeignOrderItemDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId", dto.getSysForeignSlipId());
		parameters.addParameter("sysItemId", dto.getSysItemId());
		parameters.addParameter("sysForeignSlipItemId", dto.getSysForeignSlipItemId());
		return select(
				"SEL_ARRIVAL_SCHEDULE_ID",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendArrivalScheduleDTO.class));
	}

	public Long getSysWarehouseId(long sysItemId) throws DaoException {

		parameters.addParameter("sysItemId", sysItemId);

		return select("SEL_SYS_WAREHOUSE_ID", parameters,
				ResultSetHandlerFactory.getFirstColumnLongRowHandler());
	}

	/**
	 * 入荷予定数を更新する
	 *
	 * @param dto
	 * @throws DaoException
	 */
	public int updateArrivalNum(ExtendArrivalScheduleDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysArrivalScheduleId",	dto.getSysArrivalScheduleId());
		parameters.addParameter("arrivalNum", dto.getArrivalNum());
		parameters.addParameter("arrivalFlag", dto.getArrivalFlag());

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_ARRIVAL_SCHEDULE_PART", parameters);

	}

	public List<ExtendWarehouseStockDTO> getWarehouseStockList(long sysItemId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getSortFlg", "1");

		return selectList(
				"SEL_WAREHOUSE_STOCK_LIST",
				parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}

	public void updateTemporaryStockNum(long sysItemId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_TEMPORARY_STOCK_NUM", parameters);
	}

//	組立可数プロシージャ化のため処理を凍結 20171107 y_saito
//	public void setAssemblyNum(long sysItemId, int assemblyNum)
//			throws DaoException {
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("sysItemId", sysItemId);
//		parameters.addParameter("assemblyNum", assemblyNum);
//
//		UserInfo userInfo = ActionContext.getLoginUserInfo();
//		parameters.addParameter("updateUserId", userInfo.getUserId());
//		update("UPD_ASSEMBLY_NUM", parameters);
//	}

	/**
	 * 出庫分類フラグが1：構成商品から出庫のものを取得
	 *
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getParentSetItemLeaveOnList(long sysItemId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("PARENT_SET_ITEM_LEAVE_ON_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	public List<ExtendSetItemDTO> getParentSetItemList(long sysItemId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		return selectList("PARENT_SET_ITEM_LIST", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	/**
	 * 一括入荷処理、部分入荷でない場合の更新
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateArrivalSchedule(ExtendArrivalScheduleDTO dto)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_ARRIVAL_SCHEDULE", parameters);
	}

	public int updateWarehouseStock(WarehouseStockDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("UPD_WAREHOUSE_STOCK", parameters);
	}

	/**
	 * 出庫分類フラグが1：構成商品在庫から出庫のものを取得する
	 *
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getSetItemLeaveClassFlgOnList(long sysItemId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_SET_ITEM_LEAVE_CLASS_FLG_ON", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	public List<ExtendSetItemDTO> getSetItemList(long sysItemId)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysItemId", sysItemId);

		parameters.addParameter("getSortFlg", "1");

		return selectList("SEL_SET_ITEM", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(ExtendSetItemDTO.class));
	}

	public void updateArrivalScheduleOrderStatus(
			ExtendForeignOrderItemDTO itemDTO, String orderStatus)
			throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysForeignSlipId",
				itemDTO.getSysForeignSlipId());
		parameters.addParameter("sysItemId", itemDTO.getSysItemId());
		parameters.addParameter("orderStatus", orderStatus);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_ARRIVAL_SCHEDULE_ORDER_STATUS", parameters);
	}
}