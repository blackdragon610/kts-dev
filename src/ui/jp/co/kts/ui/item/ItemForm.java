package jp.co.kts.ui.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.BackOrderDTO;
import jp.co.kts.app.common.entity.DeadStockDTO;
import jp.co.kts.app.common.entity.MstChannelDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstLargeGroupDTO;
import jp.co.kts.app.common.entity.MstMiddleGroupDTO;
import jp.co.kts.app.common.entity.MstSmallGroupDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.common.entity.UpdateDataHistoryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemCostDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemManualDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemPriceDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.output.entity.StockListDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.ui.web.struts.WebConst;

public class ItemForm extends AppBaseForm{

	private ExtendMstItemDTO mstItemDTO = new ExtendMstItemDTO();

	private List<MstWarehouseDTO> warehouseList = new ArrayList<>();

	private int warehouseLength = 0;

	private List<ExtendWarehouseStockDTO> warehouseStockList = new ArrayList<>();

	private List<ExtendWarehouseStockDTO> latestWarehouseStockList = new ArrayList<>();

	private List<ExtendWarehouseStockDTO> addWarehouseStockList = new ArrayList<>();

	private List<ExtendWarehouseStockDTO> beforeWarehouseList = new ArrayList<>();

	private List<ExtendWarehouseStockDTO> externalWarehouseStockList = new ArrayList<>();

	private List<ExtendMstWarehouseDTO> externalWarehouseList = new ArrayList<>();

	private List<ExtendItemPriceDTO> itemPriceList = new ArrayList<>();

	private List<ExtendItemCostDTO> itemCostList = new ArrayList<>();

	private List<MstCorporationDTO> corporationList = new ArrayList<>();

	private List<MstLargeGroupDTO> largeGroupList = new ArrayList<>();

	private List<MstMiddleGroupDTO> middleGroupList = new ArrayList<>();

	private List<MstSmallGroupDTO> smallGroupList = new ArrayList<>();

	private String alertType;

	private String overseasInfoAuth;

	private int sysWarehouseId;

	private List<ExtendArrivalScheduleDTO> arrivalScheduleList = new ArrayList<>();

	private List<ExtendArrivalScheduleDTO> addArrivalScheduleList = new ArrayList<>();

	private List<ExtendArrivalScheduleDTO> arrivalHistoryList = new ArrayList<>();

	private List<BackOrderDTO> backOrderList = new ArrayList<>();

	private List<BackOrderDTO> addBackOrderList = new ArrayList<>();

	private List<ExtendKeepDTO> addKeepList = new ArrayList<>();

	private List<ExtendKeepDTO> keepList = new ArrayList<>();

	private List<ExtendKeepDTO> externalKeepList = new ArrayList<>();

	private List<ExtendKeepDTO> addExternalKeepList = new ArrayList<>();

	private String priority = StringUtils.EMPTY;

	private List<MstChannelDTO> channelList = new ArrayList<>();

	private List<ResultItemSearchDTO> itemList = new ArrayList<>();

	private List<SysItemIdDTO> sysItemIdList = new ArrayList<>();

	private List<StockListDTO> stockList = new ArrayList<>();

	private int addItemManualLength = WebConst.ADD_ITEM_MANUAL_LENGTH;

	private int addArrivalScheduleLength = WebConst.ADD_ARRIVAL_SCHEDULE_LENGTH;

	private int addBackOrderLength = WebConst.ADD_BACK_ORDER_LENGTH;

	private int addKeepLength = WebConst.ADD_KEEP_LENGTH;

	private int addExternalKeepLength = WebConst.ADD_KEEP_LENGTH;

	/** 不良在庫追加分の長さを格納します */
	private int addDeadStockLength = WebConst.ADD_DEAD_STOCK_LENGTH;

	private SearchItemDTO searchItemDTO = new SearchItemDTO();

	private long sysItemId;

	private int pageIdx = 0;

	private int sysItemIdListSize = 0;

	private RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();

	private ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

	private List<ExtendArrivalScheduleDTO> lumpArrivalScheduleList = new ArrayList<>();

	private List<ExtendItemManualDTO> itemManualList = new ArrayList<>();

	private List<ExtendItemManualDTO> addItemManualList = new ArrayList<>();

	private long sysItemManualId = 0;

	private List<ExtendArrivalScheduleDTO> lumpArrivalList = new ArrayList<>();

	private int itemListPageMax;

	private MstUserDTO mstUserDTO = new MstUserDTO();

	/** 更新者ユーザー情報 */
	private ExtendMstUserDTO extendMstUserDTO = new ExtendMstUserDTO();

	private long sysSupplierId;

	/** M_仕入先DTO */
	private ExtendMstSupplierDTO supplierDTO = new ExtendMstSupplierDTO();

	private List<ExtendMstSupplierDTO> supplierList = new ArrayList<>();

	private int supplierListSize = 0;

	private int searchSysSupplierId;

	/** 不良在庫DTO */
	private DeadStockDTO deadStockDTO = new DeadStockDTO();

	/** 不良在庫リスト */
	private List<DeadStockDTO> deadStockList = new ArrayList<>();

	/** 不良在庫リスト(追加分) */
	private List<DeadStockDTO> addDeadStockList = new ArrayList<>();

	/** システム不良在庫ID */
	private int sysDeadStockId;

	/** 更新履歴DTO */
	private UpdateDataHistoryDTO updateDataHistoryDTO = new UpdateDataHistoryDTO();

	/** 更新履歴リスト */
	private List<UpdateDataHistoryDTO> updateDataHistoryList = new ArrayList<>();

	/** 注文プールリスト */
	private List<ExtendMstItemDTO> orderPoolList = new ArrayList<>();

	/** 注文候補リスト */
	private List<ExtendMstItemDTO> orderCandidateList = new ArrayList<>();
	
	private String haibangFlg;

	/**
	 * ---------------------------------------getterのみ---------------------------------------------------------
	 */

	/** 並替え */
	private Map<String, String> itemListSortMap = WebConst.ITEM_LIST_SORT_MAP;

	/** 昇順・降順 */
	private Map<String, String> itemListSortOrder = WebConst.ITEM_LIST_SORT_ORDER;

	private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;

	/** 資料区分*/
	private Map<String, String> documentTypeMap = WebConst.DOCUMENT_TYPE_MAP;

	/** ダウンロード区分 */
	private Map<String, String> downloadTypeCodeMap = WebConst.DOWNLOAD_TYPE_CODE_MAP;

	/** 商品リードタイム */
	private Map<String, String> itemLeadTimeMap = WebConst.ITEM_LEAD_TIME_MAP;

	/** 品番区分 */
	private Map<String, String> itemListCodeTypeMap = WebConst.ITEM_LIST_CODE_TYPE_MAP;

	/** 名前区分 */
	private Map<String, String> itemListNameTypeMap = WebConst.ITEM_LIST_NAME_TYPE_MAP;

	/** 入荷日区分 */
	private Map<String, String> itemListArrivalDateTypeMap = WebConst.ITEM_LIST_ARRIVAL_DATE_TYPE_MAP;

	/** 検索方法 */
	private Map<String, String> searchMethodMap = WebConst.SEARCH_METHOD_MAP;

	private Map<String, String> numberOrderMap = WebConst.NUMBER_ORDER_MAP;

	/** 検索結果表示タイプ：値 */
	private String displayContentsVal;

	/** メッセージ有無フラグ */
	private String messageFlg = "0";

	/** メッセージ */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();

	/** 注文数表示用 */
	private long totalOrderNum = 0;

	/**
	 * ---------------------------------------getterのみ---------------------------------------------------------
	 */

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {


		if ("/registryItem".equals(appMapping.getPath()) || "/updateItem".equals(appMapping.getPath())) {
			this.setRegistryMessageDTO(new RegistryMessageDTO());

			// チェックボックスの変数
			this.mstItemDTO.setManualFlg(StringUtil.SWITCH_OFF_KEY);
			this.mstItemDTO.setPlanSheetFlg(StringUtil.SWITCH_OFF_KEY);
		}

		//チェックボックスの値
		if ("/initItemList".equals(appMapping.getPath()) || "/itemList".equals(appMapping.getPath())) {
			this.getSearchItemDTO().setBackOrderFlg(StringUtil.SWITCH_OFF_KEY);
			this.getSearchItemDTO().setOrderAlertFlg(StringUtil.SWITCH_OFF_KEY);
			this.getSearchItemDTO().setOverArrivalScheduleFlg(StringUtil.SWITCH_OFF_KEY);
			this.searchItemDTO = new SearchItemDTO();

			this.setSysItemIdListSize(0);
		}
		registryMessageDTO = new RegistryMessageDTO();

		mstUserDTO = new MstUserDTO();

		alertType = "0";

	}

	public ExtendMstItemDTO getMstItemDTO() {
		return mstItemDTO;
	}

	public void setMstItemDTO(ExtendMstItemDTO mstItemDTO) {
		this.mstItemDTO = mstItemDTO;
	}

	public List<MstWarehouseDTO> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<MstWarehouseDTO> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public int getWarehouseLength() {
		return warehouseLength;
	}

	public void setWarehouseLength(int warehouseLength) {
		this.warehouseLength = warehouseLength;
	}

	public List<ExtendWarehouseStockDTO> getWarehouseStockList() {
		return warehouseStockList;
	}

	public void setWarehouseStockList(
			List<ExtendWarehouseStockDTO> warehouseStockList) {
		this.warehouseStockList = warehouseStockList;
	}

	public List<ExtendWarehouseStockDTO> getAddWarehouseStockList() {
		return addWarehouseStockList;
	}

	public void setAddWarehouseStockList(
			List<ExtendWarehouseStockDTO> addWarehouseStockList) {
		this.addWarehouseStockList = addWarehouseStockList;
	}

	/**
	 * 外部倉庫在庫リストを取得する。
	 * @return
	 */
	public List<ExtendWarehouseStockDTO> getExternalWarehouseStockList() {
		return externalWarehouseStockList;
	}

	/**
	 * 外部倉庫在庫リストを設定する。
	 * @param externalWarehouseStockList
	 */
	public void setExternalWarehouseStockList(List<ExtendWarehouseStockDTO> externalWarehouseStockList) {
		this.externalWarehouseStockList = externalWarehouseStockList;
	}

	/**
	 * 外部倉庫リストを取得する。
	 * @return
	 */
	public List<ExtendMstWarehouseDTO> getExternalWarehouseList() {
		return externalWarehouseList;
	}

	/**
	 * 外部倉庫リストを設定する。
	 * @param externalWarehouseList
	 */
	public void setExternalWarehouseList(List<ExtendMstWarehouseDTO> externalWarehouseList) {
		this.externalWarehouseList = externalWarehouseList;
	}

	/**
	 * beforeWarehouseListを取得します。
	 * @return beforeWarehouseList
	 */
	public List<ExtendWarehouseStockDTO> getBeforeWarehouseList() {
	    return beforeWarehouseList;
	}

	/**
	 * beforeWarehouseListを設定します。
	 * @param beforeWarehouseList beforeWarehouseList
	 */
	public void setBeforeWarehouseList(List<ExtendWarehouseStockDTO> beforeWarehouseList) {
	    this.beforeWarehouseList = beforeWarehouseList;
	}

	/**
	 * latestWarehouseStockListを取得します
	 * @return
	 */
	public List<ExtendWarehouseStockDTO> getLatestWarehouseStockList() {
		return latestWarehouseStockList;
	}

	/**
	 * latestWarehouseStockListを設定します
	 * @param latestWarehouseStockList
	 */
	public void setLatestWarehouseStockList(List<ExtendWarehouseStockDTO> latestWarehouseStockList) {
		this.latestWarehouseStockList = latestWarehouseStockList;
	}

	public List<ExtendItemPriceDTO> getItemPriceList() {
		return itemPriceList;
	}

	public void setItemPriceList(List<ExtendItemPriceDTO> itemPriceList) {
		this.itemPriceList = itemPriceList;
	}

	public List<ExtendItemCostDTO> getItemCostList() {
		return itemCostList;
	}

	public void setItemCostList(List<ExtendItemCostDTO> itemCostList) {
		this.itemCostList = itemCostList;
	}

	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}

	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
	}

	public List<MstLargeGroupDTO> getLargeGroupList() {
		return largeGroupList;
	}

	public void setLargeGroupList(List<MstLargeGroupDTO> largeGroupList) {
		this.largeGroupList = largeGroupList;
	}

	public List<MstMiddleGroupDTO> getMiddleGroupList() {
		return middleGroupList;
	}

	public void setMiddleGroupList(List<MstMiddleGroupDTO> middleGroupList) {
		this.middleGroupList = middleGroupList;
	}

	public List<MstSmallGroupDTO> getSmallGroupList() {
		return smallGroupList;
	}

	public void setSmallGroupList(List<MstSmallGroupDTO> smallGroupList) {
		this.smallGroupList = smallGroupList;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	/**
	 * @return overseasInfoAuth
	 */
	public String getOverseasInfoAuth() {
		return overseasInfoAuth;
	}

	/**
	 * @param overseasInfoAuth セットする overseasInfoAuth
	 */
	public void setOverseasInfoAuth(String overseasInfoAuth) {
		this.overseasInfoAuth = overseasInfoAuth;
	}

	public int getSysWarehouseId() {
		return sysWarehouseId;
	}

	public void setSysWarehouseId(int sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	public List<ExtendArrivalScheduleDTO> getArrivalScheduleList() {
		return arrivalScheduleList;
	}

	public void setArrivalScheduleList(
			List<ExtendArrivalScheduleDTO> arrivalScheduleList) {
		this.arrivalScheduleList = arrivalScheduleList;
	}

	public List<ExtendArrivalScheduleDTO> getAddArrivalScheduleList() {
		return addArrivalScheduleList;
	}

	public void setAddArrivalScheduleList(
			List<ExtendArrivalScheduleDTO> addArrivalScheduleList) {
		this.addArrivalScheduleList = addArrivalScheduleList;
	}

	public List<ExtendArrivalScheduleDTO> getArrivalHistoryList() {
		return arrivalHistoryList;
	}

	public void setArrivalHistoryList(
			List<ExtendArrivalScheduleDTO> arrivalHistoryList) {
		this.arrivalHistoryList = arrivalHistoryList;
	}

	public List<BackOrderDTO> getBackOrderList() {
		return backOrderList;
	}

	public void setBackOrderList(List<BackOrderDTO> backOrderList) {
		this.backOrderList = backOrderList;
	}

	public List<BackOrderDTO> getAddBackOrderList() {
		return addBackOrderList;
	}

	public void setAddBackOrderList(List<BackOrderDTO> addBackOrderList) {
		this.addBackOrderList = addBackOrderList;
	}



	/**
	 * @return addKeepList
	 */
	public List<ExtendKeepDTO> getAddKeepList() {
		return addKeepList;
	}

	/**
	 * @param addKeepList
	 */
	public void setAddKeepList(List<ExtendKeepDTO> addKeepList) {
		this.addKeepList = addKeepList;
	}



	/**
	 * @return keepList
	 */
	public List<ExtendKeepDTO> getKeepList() {
		return keepList;
	}

	/**
	 * @param keepList
	 */
	public void setKeepList(List<ExtendKeepDTO> keepList) {
		this.keepList = keepList;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public List<MstChannelDTO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<MstChannelDTO> channelList) {
		this.channelList = channelList;
	}

	public List<ResultItemSearchDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ResultItemSearchDTO> itemList) {
		this.itemList = itemList;
	}



	/**
	 * @return sysItemIdList
	 */
	public List<SysItemIdDTO> getSysItemIdList() {
		return sysItemIdList;
	}

	/**
	 * @param sysItemIdList
	 */
	public void setSysItemIdList(List<SysItemIdDTO> sysItemIdList) {
		this.sysItemIdList = sysItemIdList;
	}

	/**
	 * @return stockList
	 */
	public List<StockListDTO> getStockList() {
		return stockList;
	}

	/**
	 * @param stockList
	 */
	public void setStockList(List<StockListDTO> stockList) {
		this.stockList = stockList;
	}

	public int getAddArrivalScheduleLength() {
		return addArrivalScheduleLength;
	}

	public void setAddArrivalScheduleLength(int addArrivalScheduleLength) {
		this.addArrivalScheduleLength = addArrivalScheduleLength;
	}

	public int getAddBackOrderLength() {
		return addBackOrderLength;
	}

	public void setAddBackOrderLength(int addBackOrderLength) {
		this.addBackOrderLength = addBackOrderLength;
	}

	/**
	 * @return addKeepLength
	 */
	public int getAddKeepLength() {
		return addKeepLength;
	}

	/**
	 * @param addKeepLength
	 */
	public void setAddKeepLength(int addKeepLength) {
		this.addKeepLength = addKeepLength;
	}

	/**
	 * <p>
	 * 追加用不良在庫のサイズを返却します
	 * </p>
	 * @return addDeadStockLength
	 */
	public int getAddDeadStockLength() {
		return addDeadStockLength;
	}

	public SearchItemDTO getSearchItemDTO() {
		return searchItemDTO;
	}

	public void setSearchItemDTO(SearchItemDTO searchItemDTO) {
		this.searchItemDTO = searchItemDTO;
	}

	public long getSysItemId() {
		return sysItemId;
	}

	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}



	/**
	 * @return pageIdx
	 */
	public int getPageIdx() {
		return pageIdx;
	}

	/**
	 * @param pageIdx セットする pageIdx
	 */
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}

	public int getAddItemManualLength() {
		return addItemManualLength;
	}


	/**
	 * @return sysItemIdListSize
	 */
	public int getSysItemIdListSize() {
		return sysItemIdListSize;
	}

	/**
	 * @param sysItemIdListSize
	 */
	public void setSysItemIdListSize(int sysItemIdListSize) {
		this.sysItemIdListSize = sysItemIdListSize;
	}

	/**
	 * @return registryMessageDTO
	 */
	public RegistryMessageDTO getRegistryMessageDTO() {
		return registryMessageDTO;
	}

	/**
	 * @param registryMessageDTO セットする registryMessageDTO
	 */
	public void setRegistryMessageDTO(RegistryMessageDTO registryMessageDTO) {
		this.registryMessageDTO = registryMessageDTO;
	}

//	public ErrorMessageDTO getErrorMessageDTO() {
//		return errorMessageDTO;
//	}
//
//	public void setErrorMessageDTO(ErrorMessageDTO errorMessageDTO) {
//		this.errorMessageDTO = errorMessageDTO;
//	}

	/**
	 * @return errorMessageDTO
	 */
	public ErrorMessageDTO getErrorMessageDTO() {
		return errorMessageDTO;
	}

	/**
	 * @param errorMessageDTO セットする errorMessageDTO
	 */
	public void setErrorMessageDTO(ErrorMessageDTO errorMessageDTO) {
		this.errorMessageDTO = errorMessageDTO;
	}

	public List<ExtendItemManualDTO> getItemManualList() {
		return itemManualList;
	}

	public void setItemManualList(List<ExtendItemManualDTO> itemManualList) {
		this.itemManualList = itemManualList;
	}

	public List<ExtendItemManualDTO> getAddItemManualList() {
		return addItemManualList;
	}

	public void setAddItemManualList(List<ExtendItemManualDTO> addItemManualList) {
		this.addItemManualList = addItemManualList;
	}

	public long getSysItemManualId() {
		return sysItemManualId;
	}

	public void setSysItemManualId(long sysItemManualId) {
		this.sysItemManualId = sysItemManualId;
	}

	public List<ExtendArrivalScheduleDTO> getLumpArrivalList() {
		return lumpArrivalList;
	}

	public void setLumpArrivalList(List<ExtendArrivalScheduleDTO> lumpArrivalList) {
		this.lumpArrivalList = lumpArrivalList;
	}

	/**
	 * @return itemListPageMax
	 */
	public int getItemListPageMax() {
		return itemListPageMax;
	}

	/**
	 * @param itemListPageMax
	 */
	public void setItemListPageMax(int itemListPageMax) {
		this.itemListPageMax = itemListPageMax;
	}

	/**
	 * @return itemListSortMap
	 */
	public Map<String, String> getItemListSortMap() {
		return itemListSortMap;
	}

	/**
	 * @return itemListSortOrder
	 */
	public Map<String, String> getItemListSortOrder() {
		return itemListSortOrder;
	}

	/**
	 * @return listPageMaxMap
	 */
	public Map<String, Integer> getListPageMaxMap() {
		return listPageMaxMap;
	}

	/**
	 * @return documentTypeFlgMap : 資料区分
	 */
	public Map<String, String> getDocumentTypeMap() {
		return documentTypeMap;
	}

	/**
	 * @return lumpArrivalScheduleList
	 */
	public List<ExtendArrivalScheduleDTO> getLumpArrivalScheduleList() {
		return lumpArrivalScheduleList;
	}

	/**
	 * @param lumpArrivalScheduleList
	 */
	public void setLumpArrivalScheduleList(
			List<ExtendArrivalScheduleDTO> lumpArrivalScheduleList) {
		this.lumpArrivalScheduleList = lumpArrivalScheduleList;
	}

	/**
	 * @return mstUserDTO
	 */
	public MstUserDTO getMstUserDTO() {
		return mstUserDTO;
	}

	/**
	 * @param mstUserDTO
	 */
	public void setMstUserDTO(MstUserDTO mstUserDTO) {
		this.mstUserDTO = mstUserDTO;
	}


	public long getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * <p>
	 * 仕入先DTOを返却します
	 * </p>
	 * @return supplierDTO
	 */
	public ExtendMstSupplierDTO getSupplierDTO() {
		return supplierDTO;
	}

	/**
	 * <p>
	 * 仕入先DTOを設定します
	 * </p>
	 * @param supplierDTO
	 */
	public void setSupplierDTO(ExtendMstSupplierDTO supplierDTO) {
		this.supplierDTO = supplierDTO;
	}

	/**
	 * @return supplierList
	 */
	public List<ExtendMstSupplierDTO> getSupplierList() {
		return supplierList;
	}

	/**
	 * @param supplierList
	 */
	public void setSupplierList(List<ExtendMstSupplierDTO> supplierList) {
		this.supplierList = supplierList;
	}

	/**
	 * @return supplierListSize
	 */
	public int getSupplierListSize() {
		return supplierListSize;
	}

	/**
	 * @param supplierListSize
	 */
	public void setSupplierListSize(int supplierListSize) {
		this.supplierListSize = supplierListSize;
	}

	/**
	 * @return searchSysSupplierId
	 */
	public int getSearchSysSupplierId() {
		return searchSysSupplierId;
	}

	/**
	 * @param searchSysSupplierId セットする searchSysSupplierId
	 */
	public void setSearchSysSupplierId(int searchSysSupplierId) {
		this.searchSysSupplierId = searchSysSupplierId;
	}

	/**
	 * <p>
	 * 不良在庫DTOを返却します
	 * </p>
	 * @return deadStockDTO
	 */
	public DeadStockDTO getDeadStockDTO() {
		return deadStockDTO;
	}

	/**
	 * <p>
	 * 不良在庫DTOを設定します
	 * </p>
	 * @param deadStockDTO
	 */
	public void setDeadStockDTO(DeadStockDTO deadStockDTO) {
		this.deadStockDTO = deadStockDTO;
	}

	/**
	 * <p>
	 * 不良在庫リストを返却します
	 * </p>
	 * @return deadStockList
	 */
	public List<DeadStockDTO> getDeadStockList() {
		return deadStockList;
	}

	/**
	 * <p>
	 * 不良在庫リストを設定します
	 * </p>
	 * @param deadStockList
	 */
	public void setDeadStockList(List<DeadStockDTO> deadStockList) {
		this.deadStockList = deadStockList;
	}

	/**
	 * <p>
	 * 追加用不良在庫リストを返却します
	 * </p>
	 * @return addDeadStockList
	 */
	public List<DeadStockDTO> getAddDeadStockList() {
		return addDeadStockList;
	}

	/**
	 * <p>
	 * 追加用不良在庫リストを設定します
	 * </p>
	 * @param addDeadStockList
	 */
	public void setAddDeadStockList(List<DeadStockDTO> addDeadStockList) {
		this.addDeadStockList = addDeadStockList;
	}

	/**
	 * <p>
	 * システム不良在庫IDを返却します
	 * </p>
	 * @return sysDeadStockId
	 */
	public int getSysDeadStockId() {
		return sysDeadStockId;
	}

	/**
	 * <p>
	 * システム不良在庫IDを設定します
	 * </p>
	 * @param sysDeadStockId
	 */
	public void setSysDeadStockId(int sysDeadStockId) {
		this.sysDeadStockId = sysDeadStockId;
	}

	/**
	 * <p>
	 * 更新履歴DTOを返却します
	 * </p>
	 * @return updateDataHistoryDTO
	 */
	public UpdateDataHistoryDTO getUpdateDataHistoryDTO() {
		return updateDataHistoryDTO;
	}

	/**
	 * <p>
	 * 更新履歴DTOを設定します
	 * </p>
	 * @param updateDataHistoryDTO
	 */
	public void setUpdateDatahistoryDTO(UpdateDataHistoryDTO updateDataHistoryDTO) {
		this.updateDataHistoryDTO = updateDataHistoryDTO;
	}

	/**
	 * <p>
	 * 更新履歴リストを返却します
	 * </p>
	 * @return updatedataHistoryList
	 */
	public List<UpdateDataHistoryDTO> getUpdateDataHistoryList() {
		return updateDataHistoryList;
	}

	/**
	 * <p>
	 * 更新履歴リストを設定します
	 * </p>
	 * @param updatedataHistoryList
	 */
	public void setUpdateDataHistoryList(List<UpdateDataHistoryDTO> updateDataHistoryList) {
		this.updateDataHistoryList = updateDataHistoryList;
	}

	/**
	 * @return orderPoolList
	 */
	public List<ExtendMstItemDTO> getOrderPoolList() {
		return orderPoolList;
	}

	/**
	 * @param orderPoolList セットする orderPoolList
	 */
	public void setOrderPoolList(List<ExtendMstItemDTO> orderPoolList) {
		this.orderPoolList = orderPoolList;
	}

	/**
	 * @return orderCandidateList
	 */
	public List<ExtendMstItemDTO> getOrderCandidateList() {
		return orderCandidateList;
	}

	/**
	 * @param orderCandidateList セットする orderCandidateList
	 */
	public void setOrderCandidateList(List<ExtendMstItemDTO> orderCandidateList) {
		this.orderCandidateList = orderCandidateList;
	}

	/**
	 * <p>
	 * 商品リードタイムマップを返却します
	 * </p>
	 * @return itemLeadTimeMap
	 */
	public Map<String, String> getItemLeadTimeMap() {
		return itemLeadTimeMap;
	}

	/**
	 * @return itemListCodeTypeMap
	 */
	public Map<String, String> getItemListCodeTypeMap() {
		return itemListCodeTypeMap;
	}

	/**
	 * @return itemListNameTypeMap
	 */
	public Map<String, String> getItemListNameTypeMap() {
		return itemListNameTypeMap;
	}

	/**
	 * @return itemListArrivalDateTypeMap
	 */
	public Map<String, String> getItemListArrivalDateTypeMap() {
		return itemListArrivalDateTypeMap;
	}

	/**
	 * @return searchMethodMap
	 */
	public Map<String, String> getSearchMethodMap() {
		return searchMethodMap;
	}

	/**
	 * @return numberOrderMap
	 */
	public Map<String, String> getNumberOrderMap() {
		return numberOrderMap;
	}


	/**
	 * ダウンロード区分を返却します。
	 * @return ダウンロード区分
	 */
	public Map<String,String> getDownloadTypeCodeMap() {
	    return downloadTypeCodeMap;
	}

	/**
	 * @return displayContentsVal
	 */
	public String getDisplayContentsVal() {
		return displayContentsVal;
	}

	/**
	 * @param displayContentsVal セットする displayContentsVal
	 */
	public void setDisplayContentsVal(String displayContentsVal) {
		this.displayContentsVal = displayContentsVal;
	}

	/**
	 * メッセージ有無フラグを取得します。
	 * @return メッセージ有無フラグ
	 */
	public String getMessageFlg() {
	    return messageFlg;
	}

	/**
	 * メッセージ有無フラグを設定します。
	 * @param messageFlg メッセージ有無フラグ
	 */
	public void setMessageFlg(String messageFlg) {
	    this.messageFlg = messageFlg;
	}

	/**
	 * メッセージを取得します。
	 * @return メッセージ
	 */
	public RegistryMessageDTO getRegistryDto() {
	    return registryDto;
	}

	/**
	 * メッセージを設定します。
	 * @param registryDto メッセージ
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
	    this.registryDto = registryDto;
	}

	/**
	 * 注文数表示用を取得します。
	 * @return totalOrderNum
	 */
	public long getTotalOrderNum() {
		return totalOrderNum;
	}

	/**
	 * 注文数表示用を設定します。
	 * @param totalOrderNum
	 */
	public void setTotalOrderNum(long totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
	}

	/**
	 * 更新者ユーザー情報を取得します
	 * @return
	 */
	public ExtendMstUserDTO getExtendMstUserDTO() {
		return extendMstUserDTO;
	}

	/**
	 * 更新者ユーザー情報を設定します
	 * @param extendmstUserDTO
	 */
	public void setExtendMstUserDTO(ExtendMstUserDTO extendMstUserDTO) {
		this.extendMstUserDTO = extendMstUserDTO;
	}

	public List<ExtendKeepDTO> getExternalKeepList() {
		return externalKeepList;
	}

	public void setExternalKeepList(List<ExtendKeepDTO> externalKeepList) {
		this.externalKeepList = externalKeepList;
	}

	public List<ExtendKeepDTO> getAddExternalKeepList() {
		return addExternalKeepList;
	}

	public void setAddExternalKeepList(List<ExtendKeepDTO> addExternalKeepList) {
		this.addExternalKeepList = addExternalKeepList;
	}

	public int getAddExternalKeepLength() {
		return addExternalKeepLength;
	}

	public void setAddExternalKeepLength(int addExternalKeepLength) {
		this.addExternalKeepLength = addExternalKeepLength;
	}

	/**
	 * @return the haibangFlg
	 */
	public String getHaibangFlg() {
		return haibangFlg;
	}

	/**
	 * @param haibangFlg the haibangFlg to set
	 */
	public void setHaibangFlg(String haibangFlg) {
		this.haibangFlg = haibangFlg;
	}

}