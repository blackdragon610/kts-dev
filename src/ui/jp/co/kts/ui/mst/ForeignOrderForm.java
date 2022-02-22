package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.CurrencyLedgerDTO;
import jp.co.kts.app.common.entity.ForeignSlipSearchDTO;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.app.search.entity.SupplierSearchDTO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * [概要] 海外注文情報を格納します。
 * @author Boncre
 *
 */
public class ForeignOrderForm extends AppBaseForm {

	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;

	/** 海外注文伝票DTO */
	private ExtendForeignOrderDTO foreignOrderDTO = new ExtendForeignOrderDTO();
	private List<ExtendForeignOrderDTO> foreignOrderSlipList = new ArrayList<>();
	/** 海外伝票検索DTO */
	private ForeignSlipSearchDTO ForeignSlipSearchDTO = new ForeignSlipSearchDTO();
	/** システム海外伝票IDリスト */
	private List<ExtendForeignOrderDTO> sysForeignSlipIdList = new ArrayList<>();
	/** システム海外伝票IDリストサイズ */
	private int sysForeignSlipIdListSize = 0;

	/** 海外伝票ID */
	private long sysForeignSlipId = 0;

	/** 注文ステータスリンク */
	private String dispOrderStatus;

	/** 海外注文商品リスト */
	private ExtendForeignOrderItemDTO itemDto = new ExtendForeignOrderItemDTO();
	/** 海外注文商品リスト(表示用) */
	private List<ExtendForeignOrderItemDTO> itemList = new ArrayList<>();
	/** 海外注文商品リスト(追加用) */
	private List<ExtendForeignOrderItemDTO> addItemList = new ArrayList<>();
	/** 海外注文商品リストサイズ */
	private int foreignOrderItemListSize = 0;

	/** 商品検索DTO(注文プール用) */
	private SearchItemDTO searchItemDTO = new SearchItemDTO();
	/** 注文プール商品検索リスト */
	private List<SearchItemDTO> orderPoolSearchItemList = new ArrayList<>();
	/** 注文プール商品検索結果リスト */
	private List<ExtendForeignOrderItemDTO> orderPoolSearchResultItemList = new ArrayList<>();
	/** 注文プール商品検索結果リストサイズ */
	private int orderPoolItemListSize = 0;

	/** 仕入先情報DTO */
	private ExtendMstSupplierDTO supplierDTO = new ExtendMstSupplierDTO();
	/** 仕入先検索DTO */
	private SupplierSearchDTO supplierSearchDTO = new SupplierSearchDTO();
	/** 仕入先リスト */
	private List<ExtendMstSupplierDTO> searchSupplierList = new ArrayList<>();
	/** 仕入先リストサイズ */
	private int SupplierListSize = 0;

	/** システム仕入先ID */
	private long sysSupplierId;

	/** 支払条件1 */
	private int paymentTerms1;
	/** 支払条件2 */
	private int paymentTerms2;

	/** 仕入先リードタイム */
	private String leadTime;

	/** レート空表示用 */
	private String noRate = "";

	/** 取引通貨リスト */
	private List<CurrencyLedgerDTO> currencyList = new ArrayList<>();

	/** メッセージ用 */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();
	/** メッセージ有無フラグ */
	private String messageFlg = "0";

	/** エラーメッセージ用 */
	private ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

	/** 注文ステータス */
	private String orderStatus;

	/** ページ */
	private int pageIdx = 0;
	/** ページ最大表示件数 */
	private int foreignOrderListPageMax;

	/** 一括入荷リスト */
	private List<ExtendArrivalScheduleDTO> lumpArrivalList = new ArrayList<>();

	/** 倉庫リスト */
	private List<MstWarehouseDTO> warehouseList = new ArrayList<>();

	/** システム倉庫ID */
	private long sysWarehouseId;

	/** 一括支払リスト */
	private List<ExtendForeignOrderDTO> lumpPaymentList = new ArrayList<>();

	/** 倉庫在庫リスト */
	private List<ExtendWarehouseStockDTO> warehouseStockList = new ArrayList<>();
	/** 倉庫在庫追加リスト */
	private List<ExtendWarehouseStockDTO> addWarehouseStockList = new ArrayList<>();

	/** 一覧からの詳細画面遷移に使用 */
	private String detailFlag = "0";

	/**
	 * ---------------------------------------getterのみ---------------------------------------------------------
	 */

	/** 仕入先リードタイムMAP */
	private Map<String, String> leadTimeMap = WebConst.LEAD_TIME_MAP;
	/** 注文ステータスMAP */
	private Map<String, String> orderStatusMap = WebConst.ORDER_STATUS_MAP;
	/** 注文ステータス(アルファベット付き)MAP */
	private Map<String, String> orderStatusNumberedMap = WebConst.ORDER_STATUS_NUMBERED_MAP;
	/** 海外注文入荷ステータスMAP */
	private Map<String, String> foreignOrderArriveStatusMap = WebConst.FOREIGN_ORDER_ARRIVE_STATUS_MAP;
	/** 海外注文支払ステータスMAP */
	private Map<String, String> foreignOrderPaymentStatusMap = WebConst.FOREIGN_ORDER_PAYMENT_STATUS_MAP;
	/** 検索方法MAP */
	private Map<String, String> searchMethodMap = WebConst.SEARCH_METHOD_MAP;
	/** 海外注文伝票並び替えMAP */
	private Map<String, String> foreignOrderItemSortMap = WebConst.FOREIGN_ORDER_ITEM_SORT_MAP;
	/** 海外注文伝票ソート順MAP */
	private Map<String, String> foreignOrderItemSortTypeMap = WebConst.FOREIGN_ORDER_ITEM_SORT_TYPE_MAP;
	/** ページ表示件数MAP */
	private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;

	/**
	 * @return sysForeignSlipId
	 */
	public long getSysForeignSlipId() {
		return sysForeignSlipId;
	}

	/**
	 * @param sysForeignSlipId
	 */
	public void setSysForeignSlipId(long sysForeignSlipId) {
		this.sysForeignSlipId = sysForeignSlipId;
	}

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {

	}

	/**
	 * <p>
	 * 海外注文DTOを返却します。
	 * </p>
	 * @return foreignOrderDTO
	 */
	public ExtendForeignOrderDTO getForeignOrderDTO() {
		return foreignOrderDTO;
	}

	/**
	 * <p>
	 * 海外注文DTOを設定します。
	 * </p>
	 * @param foreignOrderDTO
	 */
	public void setForeignOrderDTO(ExtendForeignOrderDTO foreignOrderDTO) {
		this.foreignOrderDTO = foreignOrderDTO;
	}

	/**
	 * @return foreignSlipSearchDTO
	 */
	public ForeignSlipSearchDTO getForeignSlipSearchDTO() {
		return ForeignSlipSearchDTO;
	}

	/**
	 * @param foreignSlipSearchDTO
	 */
	public void setForeignSlipSearchDTO(ForeignSlipSearchDTO foreignSlipSearchDTO) {
		ForeignSlipSearchDTO = foreignSlipSearchDTO;
	}

	/**
	 * @return dispOrderStatus
	 */
	public String getDispOrderStatus() {
		return dispOrderStatus;
	}

	/**
	 * @param dispOrderStatus
	 */
	public void setDispOrderStatus(String dispOrderStatus) {
		this.dispOrderStatus = dispOrderStatus;
	}

	/**
	 * <p>
	 * 海外注文伝票リストを返却します。
	 * </p>
	 * @return foreignOrderSlipList
	 */
	public List<ExtendForeignOrderDTO> getForeignOrderSlipList() {
		return foreignOrderSlipList;
	}

	/**
	 * <p>
	 * 海外注文伝票リストを設定します。
	 * </p>
	 * @param foreignOrderSlipList
	 */
	public void setForeignOrderSlipList(List<ExtendForeignOrderDTO> foreignOrderSlipList) {
		this.foreignOrderSlipList = foreignOrderSlipList;
	}

	/**
	 * @return sysForeignSlipIdList
	 */
	public List<ExtendForeignOrderDTO> getSysForeignSlipIdList() {
		return sysForeignSlipIdList;
	}

	/**
	 * @param sysForeignSlipIdList
	 */
	public void setSysForeignSlipIdList(List<ExtendForeignOrderDTO> sysForeignSlipIdList) {
		this.sysForeignSlipIdList = sysForeignSlipIdList;
	}

	/**
	 * @return pageIdx
	 */
	public int getPageIdx() {
		return pageIdx;
	}

	/**
	 * @param pageIdx
	 */
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}


	/**
	 * @return foreignOrderListPageMax
	 */
	public int getForeignOrderListPageMax() {
		return foreignOrderListPageMax;
	}

	/**
	 * @param foreignOrderListPageMax
	 */
	public void setForeignOrderListPageMax(int foreignOrderListPageMax) {
		this.foreignOrderListPageMax = foreignOrderListPageMax;
	}

	/**
	 * <p>
	 * 海外注文商品リストを返却します。
	 * </p>
	 * @return itemList
	 */
	public List<ExtendForeignOrderItemDTO> getItemList() {
		return itemList;
	}

	/**
	 * <p>
	 * 海外注文商品リストを設定します。
	 * </p>
	 * @param itemList
	 */
	public void setItemList(List<ExtendForeignOrderItemDTO> itemList) {
		this.itemList = itemList;
	}

	/**
	 * <p>
	 * 海外注文商品追加用リストを返却します。
	 * </p>
	 * @return addItemList
	 */
	public List<ExtendForeignOrderItemDTO> getAddItemList() {
		return addItemList;
	}

	/**
	 * <p>
	 * 海外注文商品追加用リストを設定します。
	 * </p>
	 * @param addItemList
	 */
	public void setAddItemList(List<ExtendForeignOrderItemDTO> addItemList) {
		this.addItemList = addItemList;
	}

	/**
	 * <p>
	 * 仕入先DTOを返却します。
	 * </p>
	 * @return supplierDTO
	 */
	public ExtendMstSupplierDTO getSupplierDTO() {
		return supplierDTO;
	}

	/**
	 * <p>
	 * 仕入先DTOを設定します。
	 * </p>
	 * @param supplierDTO
	 */
	public void setSupplierDTO(ExtendMstSupplierDTO supplierDTO) {
		this.supplierDTO = supplierDTO;
	}

	/**
	 * @return supplierSearchDTO
	 */
	public SupplierSearchDTO getSupplierSearchDTO() {
		return supplierSearchDTO;
	}

	/**
	 * @param supplierSearchDTO
	 */
	public void setSupplierSearchDTO(SupplierSearchDTO supplierSearchDTO) {
		this.supplierSearchDTO = supplierSearchDTO;
	}

	/**
	 * @return searchSupplierList
	 */
	public List<ExtendMstSupplierDTO> getSearchSupplierList() {
		return searchSupplierList;
	}

	/**
	 * @param searchSupplierList
	 */
	public void setSearchSupplierList(List<ExtendMstSupplierDTO> searchSupplierList) {
		this.searchSupplierList = searchSupplierList;
	}

	/**
	 * @return noRate
	 */
	public String getNoRate() {
		return noRate;
	}

	/**
	 * @param noRate
	 */
	public void setNoRate(String noRate) {
		this.noRate = noRate;
	}

	/**
	 * @return supplierListSize
	 */
	public int getSupplierListSize() {
		return SupplierListSize;
	}

	/**
	 * @param supplierListSize
	 */
	public void setSupplierListSize(int supplierListSize) {
		SupplierListSize = supplierListSize;
	}

	/**
	 * @return currencyList
	 */
	public List<CurrencyLedgerDTO> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @param currencyList
	 */
	public void setCurrencyList(List<CurrencyLedgerDTO> currencyList) {
		this.currencyList = currencyList;
	}

	/**
	 * @return searchItemDTO
	 */
	public SearchItemDTO getSearchItemDTO() {
		return searchItemDTO;
	}

	/**
	 * @param searchItemDTO セットする searchItemDTO
	 */
	public void setSearchItemDTO(SearchItemDTO searchItemDTO) {
		this.searchItemDTO = searchItemDTO;
	}

	/**
	 * @return orderPoolSearchItemList
	 */
	public List<SearchItemDTO> getOrderPoolSearchItemList() {
		return orderPoolSearchItemList;
	}

	/**
	 * @param orderPoolSearchItemList
	 */
	public void setOrderPoolSearchItemList(List<SearchItemDTO> orderPoolSearchItemList) {
		this.orderPoolSearchItemList = orderPoolSearchItemList;
	}



	/**
	 * @return sysSupplierId
	 */
	public long getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * @param sysSupplierId
	 */
	public void setSysSupplierId(long sysSupplierId) {
		this.sysSupplierId = sysSupplierId;
	}

	/**
	 * @return paymentTerms1
	 */
	public int getPaymentTerms1() {
		return paymentTerms1;
	}

	/**
	 * @param paymentTerms1
	 */
	public void setPaymentTerms1(int paymentTerms1) {
		this.paymentTerms1 = paymentTerms1;
	}

	/**
	 * @return paymentTerms2
	 */
	public int getPaymentTerms2() {
		return paymentTerms2;
	}

	/**
	 * @param paymentTerms2
	 */
	public void setPaymentTerms2(int paymentTerms2) {
		this.paymentTerms2 = paymentTerms2;
	}

	/**
	 * @return leadTime
	 */
	public String getLeadTime() {
		return leadTime;
	}

	/**
	 * @param leadTime
	 */
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	/**
	 * <p>
	 * リードタイム情報MAPを返却します。(getterのみ)
	 * </p>
	 * @return leadTimeMap
	 */
	public Map<String, String> getLeadTimeMap() {
		return leadTimeMap;
	}

	/**
	 * <p>
	 * 注文ステータスを返却します。
	 * </p>
	 * @return orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * <p>
	 * 注文ステータスを設定します。
	 * </p>
	 * @param orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * <p>
	 * ステータス情報MAPを返却します。(getterのみ)
	 * </p>
	 * @return orderStatusMap
	 */
	public Map<String, String> getOrderStatusMap() {
		return orderStatusMap;
	}

	/**
	 * @return orderStatusNumberedMap
	 */
	public Map<String, String> getOrderStatusNumberedMap() {
		return orderStatusNumberedMap;
	}

	/**
	 * @return foreignOrderArriveStatusMap
	 */
	public Map<String, String> getForeignOrderArriveStatusMap() {
		return foreignOrderArriveStatusMap;
	}

	/**
	 * @return foreignOrderPaymentStatusMap
	 */
	public Map<String, String> getForeignOrderPaymentStatusMap() {
		return foreignOrderPaymentStatusMap;
	}

	/**
	 * @param foreignOrderPaymentStatusMap
	 */
	public void setForeignOrderPaymentStatusMap(
			Map<String, String> foreignOrderPaymentStatusMap) {
		this.foreignOrderPaymentStatusMap = foreignOrderPaymentStatusMap;
	}

	/**
	 * @return searchMethodMap
	 */
	public Map<String, String> getSearchMethodMap() {
		return searchMethodMap;
	}

	/**
	 * @return foreignOrderItemSortMap
	 */
	public Map<String, String> getForeignOrderItemSortMap() {
		return foreignOrderItemSortMap;
	}

	/**
	 * @return foreignOrderItemSortTypeMap
	 */
	public Map<String, String> getForeignOrderItemSortTypeMap() {
		return foreignOrderItemSortTypeMap;
	}

	/**
	 * @return listPageMaxMap
	 */
	public Map<String, Integer> getListPageMaxMap() {
		return listPageMaxMap;
	}

	/**
	 * @return foreignOrderItemListSize
	 */
	public int getForeignOrderItemListSize() {
		return foreignOrderItemListSize;
	}

	/**
	 * @return sysForeignSlipIdListSize
	 */
	public int getSysForeignSlipIdListSize() {
		return sysForeignSlipIdListSize;
	}

	/**
	 * @param sysForeignSlipIdListSize
	 */
	public void setSysForeignSlipIdListSize(int sysForeignSlipIdListSize) {
		this.sysForeignSlipIdListSize = sysForeignSlipIdListSize;
	}

	/**
	 * @return registryDto
	 */
	public RegistryMessageDTO getRegistryDto() {
		return registryDto;
	}

	/**
	 * @param registryDto
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
		this.registryDto = registryDto;
	}

	/**
	 * @return messageFlg
	 */
	public String getMessageFlg() {
		return messageFlg;
	}

	/**
	 * @param messageFlg
	 */
	public void setMessageFlg(String messageFlg) {
		this.messageFlg = messageFlg;
	}

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

	/**
	 * @return orderPoolItemListSize
	 */
	public int getOrderPoolItemListSize() {
		return orderPoolItemListSize;
	}

	/**
	 * @param orderPoolItemListSize
	 */
	public void setOrderPoolItemListSize(int orderPoolItemListSize) {
		this.orderPoolItemListSize = orderPoolItemListSize;
	}

	/**
	 * @return itemDto
	 */
	public ForeignOrderItemDTO getItemDto() {
		return itemDto;
	}

	/**
	 * @param itemDto
	 */
	public void setItemDto(ExtendForeignOrderItemDTO itemDto) {
		this.itemDto = itemDto;
	}

	/**
	 * @return lumpArrivalList
	 */
	public List<ExtendArrivalScheduleDTO> getLumpArrivalList() {
		return lumpArrivalList;
	}

	/**
	 * @param lumpArrivalList
	 */
	public void setLumpArrivalList(List<ExtendArrivalScheduleDTO> lumpArrivalList) {
		this.lumpArrivalList = lumpArrivalList;
	}

	/**
	 * @return warehouseList
	 */
	public List<MstWarehouseDTO> getWarehouseList() {
		return warehouseList;
	}

	/**
	 * @param warehouseList
	 */
	public void setWarehouseList(List<MstWarehouseDTO> warehouseList) {
		this.warehouseList = warehouseList;
	}

	/**
	 * <p>
	 * システム倉庫IDを返却します
	 * </p>
	 * @return sysWarehouseId
	 */
	public long getSysWarehouseId() {
		return sysWarehouseId;
	}

	/**
	 * <p>
	 * システム倉庫IDを設定します
	 * </p>
	 * @param sysWarehouseId
	 */
	public void setSysWarehouseId(long sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	/**
	 * @return lumpPaymentList
	 */
	public List<ExtendForeignOrderDTO> getLumpPaymentList() {
		return lumpPaymentList;
	}

	/**
	 * @param lumpPaymentList
	 */
	public void setLumpPaymentList(List<ExtendForeignOrderDTO> lumpPaymentList) {
		this.lumpPaymentList = lumpPaymentList;
	}

	/**
	 * @return orderPoolSearchResultItemList
	 */
	public List<ExtendForeignOrderItemDTO> getOrderPoolSearchResultItemList() {
		return orderPoolSearchResultItemList;
	}

	/**
	 * @param orderPoolSearchResultItemList セットする orderPoolSearchResultItemList
	 */
	public void setOrderPoolSearchResultItemList(
			List<ExtendForeignOrderItemDTO> orderPoolSearchResultItemList) {
		this.orderPoolSearchResultItemList = orderPoolSearchResultItemList;
	}

	/**
	 * @return warehouseStockList
	 */
	public List<ExtendWarehouseStockDTO> getWarehouseStockList() {
		return warehouseStockList;
	}

	/**
	 * @param warehouseStockList セットする warehouseStockList
	 */
	public void setWarehouseStockList(List<ExtendWarehouseStockDTO> warehouseStockList) {
		this.warehouseStockList = warehouseStockList;
	}

	/**
	 * @return addWarehouseStockList
	 */
	public List<ExtendWarehouseStockDTO> getAddWarehouseStockList() {
		return addWarehouseStockList;
	}

	/**
	 * @param addWarehouseStockList セットする addWarehouseStockList
	 */
	public void setAddWarehouseStockList(List<ExtendWarehouseStockDTO> addWarehouseStockList) {
		this.addWarehouseStockList = addWarehouseStockList;
	}

	/**
	 * @return detailFlag
	 */
	public String getDetailFlag() {
		return detailFlag;
	}

	/**
	 * @param detailFlag セットする detailFlag
	 */
	public void setDetailFlag(String detailFlag) {
		this.detailFlag = detailFlag;
	}

}
