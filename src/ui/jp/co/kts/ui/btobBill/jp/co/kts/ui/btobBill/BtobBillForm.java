package jp.co.kts.ui.btobBill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.BackOrderDTO;
import jp.co.kts.app.common.entity.BtobBillDTO;
import jp.co.kts.app.common.entity.MstChannelDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendBtobBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemCostDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemManualDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemPriceDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.CorporateSaleListTotalDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;
import jp.co.kts.app.output.entity.SysCorprateSaleItemIDDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.app.output.entity.SysSaleItemIDDTO;
import jp.co.kts.app.search.entity.BtobBillSearchDTO;
import jp.co.kts.app.search.entity.CorporateSaleCostSearchDTO;
import jp.co.kts.app.search.entity.SaleCostSearchDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 法人間請求書機能Formクラス
 * 機能内で使用するデータを保持する。
 *
 * 作成日　：2015/12/15
 * 作成者　：大山智史
 * 更新日　：
 * 更新者　：
 *
 */
public class BtobBillForm extends AppBaseForm {

	/**
	 * エラーメッセージ
	 */
	private ErrorDTO errorDTO = new ErrorDTO();


	/**************************************************** 原価一覧用Formデータ ***********************************************/

	/**  在庫一覧リスト  */
	private List<ResultItemSearchDTO> itemList = new ArrayList<>();

	/**  在庫一覧１ページ当りの最大表示件数  */
	private int itemListPageMax;

	/**  倉庫リスト  */
	private List<MstWarehouseDTO> warehouseList = new ArrayList<>();

	/**  在庫検索用DTO  */
	private SearchItemDTO searchItemDTO = new SearchItemDTO();

	/**  在庫検索結果システム商品IDリスト  */
	private List<SysItemIdDTO> sysItemIdList = new ArrayList<>();

	/**  エラーメッセージDTO  */
	private ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

	/**  原価一覧総検索結果件数  */
	private int sysItemIdListSize = 0;

	/**  一覧の表示ページ  */
	private int pageIdx = 0;

	/** 検索条件・並替え */
	private Map<String, String> itemListSortMap = WebConst.ITEM_LIST_SORT_MAP;

	/** 検索条件・昇順・降順 */
	private Map<String, String> itemListSortOrder = WebConst.ITEM_LIST_SORT_ORDER;

	/** 検索条件・1ページの表示件数 */
	private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;

	/** 在庫詳細情報 */
	private ExtendMstItemDTO mstItemDTO = new ExtendMstItemDTO();

	/** 選択行システム商品ID */
	private long sysItemId;

	/** 在庫詳細更新者情報 */
	private MstUserDTO mstUserDTO = new MstUserDTO();

	/** 倉庫在庫数情報 */
	private List<ExtendWarehouseStockDTO> warehouseStockList = new ArrayList<>();

	/** 追加用倉庫情報 */
	private List<ExtendWarehouseStockDTO> addWarehouseStockList = new ArrayList<>();

	/** 倉庫数 */
	private int warehouseLength = 0;

	/** 入荷予定情報 */
	private List<ExtendArrivalScheduleDTO> arrivalScheduleList = new ArrayList<>();

	/** 入荷予定追加用 */
	private List<ExtendArrivalScheduleDTO> addArrivalScheduleList = new ArrayList<>();

	/** 原価情報 */
	private List<ExtendItemCostDTO> itemCostList = new ArrayList<>();

	/** 売価情報 */
	private List<ExtendItemPriceDTO> itemPriceList = new ArrayList<>();

	/** 法人情報 */
	private List<MstCorporationDTO> corporationList = new ArrayList<>();

	/** バックオーダー情報 */
	private List<BackOrderDTO> backOrderList = new ArrayList<>();

	/** バックオーダー追加用 */
	private List<BackOrderDTO> addBackOrderList = new ArrayList<>();

	/** キープ情報 */
	private List<ExtendKeepDTO> keepList = new ArrayList<>();

	/** キープ追加用 */
	private List<ExtendKeepDTO> addKeepList = new ArrayList<>();

	/** 説明書情報 */
	private List<ExtendItemManualDTO> itemManualList = new ArrayList<>();

	/** 説明書追加用 */
	private List<ExtendItemManualDTO> addItemManualList = new ArrayList<>();

	/** 販売チャンネル情報 */
	private List<MstChannelDTO> channelList = new ArrayList<>();

	/** 入荷履歴情報 */
	private List<ExtendArrivalScheduleDTO> arrivalHistoryList = new ArrayList<>();

	/** 説明書追加可能数 */
	private int addItemManualLength = WebConst.ADD_ITEM_MANUAL_LENGTH;

	/** 入庫予定追加可能数 */
	private int addArrivalScheduleLength = WebConst.ADD_ARRIVAL_SCHEDULE_LENGTH;

	/** バックオーダー追加可能数 */
	private int addBackOrderLength = WebConst.ADD_BACK_ORDER_LENGTH;

	/** キープ追加可能数 */
	private int addKeepLength = WebConst.ADD_KEEP_LENGTH;

	/** システム説明書ID */
	private long sysItemManualId = 0;

	/** アラート表示タイプ */
	private String alertType;

	/** 合計値エリア表示非表示 */
	private final Map<String, String> sumDispMap = WebConst.SUM_DISP_MAP;

	/****************************************************** 売上原価入力用Formデータ **********************************************/

	/** 売上原価一覧リスト */
	private List<ExtendSalesItemDTO> salesCostList = new ArrayList<>();

	/**  売上原価一覧１ページ当りの最大表示件数  */
	private int saleListPageMax;

	/** 売上原価一覧検索条件保持DTO */
	private SaleCostSearchDTO saleCostSearchDTO = new SaleCostSearchDTO();

	/** 決済方法 */
	private Map<String, String> accountMethodMap = WebConst.ACCOUNT_METHOD_MAP;

	/** 送り状種別 */
	private Map<String, String> invoiceClassificationMap = WebConst.INVOICE_CLASSIFICATION_MAP_B2;

	/** 処理ルート */
	private Map<String, String> disposalRouteMap = WebConst.DISPOSAL_ROUTE_MAP;

	/** 運送会社 */
	private Map<String, String> transportCorporationSystemMap = WebConst.TRANSPORT_CORPORATION_SYSTEM_MAP;

	/** 表示件数 */
	private Map<String, Integer> salelistPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;

	/** 並替え */
	private Map<String, String> saleSearchMap = WebConst.SALE_COST_LIST_SORT_MAP;

	/** 昇順・降順 */
	private Map<String, String> saleSearchSortOrder = WebConst.SALE_SEARCH_SORT_ORDER;

	/** 一覧検索結果・システム売上商品IDリスト */
	private List<SysSaleItemIDDTO> sysSaleItemIDList = new ArrayList<>();

	/**  売上原価一覧総検索結果件数  */
	private int sysSaleItemIDListSize = 0;

	/**  一覧の表示ページ  */
	private int saleCostPageIdx = 0;

	/**  一覧のインデックス  */
	private int saleCostListIdx = 0;


	/******************************************* 業販原価入力用Formデータ ********************************************/

	/** 業販原価一覧リスト */
	private List<ExtendCorporateSalesItemDTO> corpSalesCostList = new ArrayList<>();

	/** 業販原価一覧１ページ当りの最大表示件数  */
	private int corpSaleListPageMax;

	/** 業販原価一覧検索条件保持DTO */
	private CorporateSaleCostSearchDTO corpSaleCostSearchDTO = new CorporateSaleCostSearchDTO();

	/** 法人情報 */
	private long sysCorporationId;

	/** 業販支払方法 */
	private Map<String, String> paymentMethodMap = WebConst.PAYMENT_METHOD_MAP;

	/** 一覧検索結果・システム業販商品IDリスト */
	private List<SysCorprateSaleItemIDDTO> sysCorprateSaleItemIDList = new ArrayList<>();

	/** 業販原価一覧総検索結果件数 */
	private int sysCorprateSaleItemIDListSize = 0;

	/**  一覧の表示ページ  */
	private int corpSaleCostPageIdx = 0;

	/**  一覧のインデックス  */
	private int corpSaleCostListIdx = 0;

	/**  システム業販伝票ID  */
	private int sysCorporateSalesSlipId = 0;


	/************************************************* 法人間請求一覧入力用Formデータ ************************************/

	/** 表示対象法人情報 */
	private long dispSysCorporationId;

	/** 出力法人ID */
	private long exportSysCorporationId;

	/** 法人名 */
	private String corporationNm;

	/** 出力月 */
	private String exportMonth;

	/**  一覧の表示ページ  */
	private int btobBillPageIdx = 0;

	/**  一覧のインデックス  */
	private int btobBillListIdx = 0;

	/**  法人間請求書一覧リスト検索結果件数  */
	private int btobBillListSize = 0;

	/** １ページ当りの最大表示件数  */
	private int btobBillListPageMax = 10;

	/** 法人間請求書一覧リスト */
	private List<ExtendBtobBillDTO> btobBillDTOList = new ArrayList<>();

	/** システム法人間請求書IDリスト */
	private List<BtobBillDTO> sysBtobBillIdList = new ArrayList<>();

	/** 法人間請求書仮検索DTO */
	private BtobBillSearchDTO btobBillSearchDto = new BtobBillSearchDTO();

	/** 粗利計算方法 */
	private Map<String, String> btobBillGrossProfitCalcMap = WebConst.BTOBBILL_GROSS_PROFIT_CALC_MAP;

	/** 合計値エリア用合計合計？（業販から流用） */
	private CorporateSaleListTotalDTO corporateSaleListTotalDTO = new CorporateSaleListTotalDTO();

	/** 合計値エリア用金額計算？（業販から流用） */
	private List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList = new ArrayList<>();

	/*************************    以下、ゲッターセッター    ******************************************/

	public int getBtobBillListSize() {
		return btobBillListSize;
	}

	public List<BtobBillDTO> getSysBtobBillIdList() {
		return sysBtobBillIdList;
	}

	public void setSysBtobBillIdList(List<BtobBillDTO> sysBtobBillIdList) {
		this.sysBtobBillIdList = sysBtobBillIdList;
	}

	public void setBtobBillListSize(int btobBillListSize) {
		this.btobBillListSize = btobBillListSize;
	}

	public List<ExtendBtobBillDTO> getBtobBillDTOList() {
		return btobBillDTOList;
	}

	public void setBtobBillDTOList(List<ExtendBtobBillDTO> btobBillDTOList) {
		this.btobBillDTOList = btobBillDTOList;
	}

	public int getSysCorporateSalesSlipId() {
		return sysCorporateSalesSlipId;
	}

	public long getDispSysCorporationId() {
		return dispSysCorporationId;
	}

	public void setDispSysCorporationId(long dispSysCorporationId) {
		this.dispSysCorporationId = dispSysCorporationId;
	}

	public long getExportSysCorporationId() {
		return exportSysCorporationId;
	}

	public void setExportSysCorporationId(long exportSysCorporationId) {
		this.exportSysCorporationId = exportSysCorporationId;
	}

	public String getExportMonth() {
		return exportMonth;
	}

	public void setExportMonth(String exportMonth) {
		this.exportMonth = exportMonth;
	}

	public int getBtobBillPageIdx() {
		return btobBillPageIdx;
	}

	public void setBtobBillPageIdx(int btobBillPageIdx) {
		this.btobBillPageIdx = btobBillPageIdx;
	}

	public int getBtobBillListIdx() {
		return btobBillListIdx;
	}

	public void setBtobBillListIdx(int btobBillListIdx) {
		this.btobBillListIdx = btobBillListIdx;
	}

	public int getBtobBillListPageMax() {
		return btobBillListPageMax;
	}

	public void setBtobBillListPageMax(int btobBillListPageMax) {
		this.btobBillListPageMax = btobBillListPageMax;
	}

	public void setSysCorporateSalesSlipId(int sysCorporateSalesSlipId) {
		this.sysCorporateSalesSlipId = sysCorporateSalesSlipId;
	}

	public List<SysCorprateSaleItemIDDTO> getSysCorprateSaleItemIDList() {
		return sysCorprateSaleItemIDList;
	}

	public int getCorpSaleListPageMax() {
		return corpSaleListPageMax;
	}

	public void setCorpSaleListPageMax(int corpSaleListPageMax) {
		this.corpSaleListPageMax = corpSaleListPageMax;
	}

	public int getSysCorprateSaleItemIDListSize() {
		return sysCorprateSaleItemIDListSize;
	}

	public void setSysCorprateSaleItemIDListSize(int sysCorprateSaleItemIDListSize) {
		this.sysCorprateSaleItemIDListSize = sysCorprateSaleItemIDListSize;
	}

	public int getCorpSaleCostPageIdx() {
		return corpSaleCostPageIdx;
	}

	public void setCorpSaleCostPageIdx(int corpSaleCostPageIdx) {
		this.corpSaleCostPageIdx = corpSaleCostPageIdx;
	}

	public int getCorpSaleCostListIdx() {
		return corpSaleCostListIdx;
	}

	public void setCorpSaleCostListIdx(int corpSaleCostListIdx) {
		this.corpSaleCostListIdx = corpSaleCostListIdx;
	}

	public void setSysCorprateSaleItemIDList(
			List<SysCorprateSaleItemIDDTO> sysCorprateSaleItemIDList) {
		this.sysCorprateSaleItemIDList = sysCorprateSaleItemIDList;
	}

	public Map<String, String> getPaymentMethodMap() {
		return paymentMethodMap;
	}

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public List<ExtendCorporateSalesItemDTO> getCorpSalesCostList() {
		return corpSalesCostList;
	}

	public int getcorpSaleListPageMax() {
		return corpSaleListPageMax;
	}

	public void setcorpSaleListPageMax(int corpSaleListPageMax) {
		this.corpSaleListPageMax = corpSaleListPageMax;
	}

	public CorporateSaleCostSearchDTO getCorpSaleCostSearchDTO() {
		return corpSaleCostSearchDTO;
	}

	public void setCorpSaleCostSearchDTO(CorporateSaleCostSearchDTO corpSaleCostSearchDTO) {
		this.corpSaleCostSearchDTO = corpSaleCostSearchDTO;
	}

	public void setCorpSalesCostList(
			List<ExtendCorporateSalesItemDTO> corpSalesCostList) {
		this.corpSalesCostList = corpSalesCostList;
	}

	public void setSaleCostListIdx(int saleCostListIdx) {
		this.saleCostListIdx = saleCostListIdx;
	}

	public int getSaleCostListIdx() {
		return saleCostListIdx;
	}

	public int getSysSaleItemIDListSize() {
		return sysSaleItemIDListSize;
	}

	public void setSysSaleItemIDListSize(int sysSaleItemIDListSize) {
		this.sysSaleItemIDListSize = sysSaleItemIDListSize;
	}

	public int getSaleCostPageIdx() {
		return saleCostPageIdx;
	}

	public void setSaleCostPageIdx(int saleCostPageIdx) {
		this.saleCostPageIdx = saleCostPageIdx;
	}

	public List<SysSaleItemIDDTO> getSysSaleItemIDList() {
		return sysSaleItemIDList;
	}

	public void setSysSaleItemIDList(List<SysSaleItemIDDTO> sysSaleItemIDList) {
		this.sysSaleItemIDList = sysSaleItemIDList;
	}

	public ErrorDTO getErrorDTO() {
		return errorDTO;
	}

	public void setErrorDTO(ErrorDTO errorDTO) {
		this.errorDTO = errorDTO;
	}

	public Map<String, String> getAccountMethodMap() {
		return accountMethodMap;
	}

	public Map<String, String> getInvoiceClassificationMap() {
		return invoiceClassificationMap;
	}

	public Map<String, String> getDisposalRouteMap() {
		return disposalRouteMap;
	}

	public Map<String, String> getTransportCorporationSystemMap() {
		return transportCorporationSystemMap;
	}

	public Map<String, Integer> getSalelistPageMaxMap() {
		return salelistPageMaxMap;
	}

	public Map<String, String> getSaleSearchMap() {
		return saleSearchMap;
	}

	public Map<String, String> getSaleSearchSortOrder() {
		return saleSearchSortOrder;
	}

	public SaleCostSearchDTO getSaleCostSearchDTO() {
		return saleCostSearchDTO;
	}

	public void setSaleCostSearchDTO(SaleCostSearchDTO saleCostSearchDTO) {
		this.saleCostSearchDTO = saleCostSearchDTO;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public long getSysItemManualId() {
		return sysItemManualId;
	}

	public void setSysItemManualId(long sysItemManualId) {
		this.sysItemManualId = sysItemManualId;
	}

	public int getAddItemManualLength() {
		return addItemManualLength;
	}

	public int getAddArrivalScheduleLength() {
		return addArrivalScheduleLength;
	}


	public int getAddBackOrderLength() {
		return addBackOrderLength;
	}


	public int getAddKeepLength() {
		return addKeepLength;
	}


	public List<ExtendArrivalScheduleDTO> getArrivalHistoryList() {
		return arrivalHistoryList;
	}

	public void setArrivalHistoryList(
			List<ExtendArrivalScheduleDTO> arrivalHistoryList) {
		this.arrivalHistoryList = arrivalHistoryList;
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

	public List<ExtendItemCostDTO> getItemCostList() {
		return itemCostList;
	}

	public void setItemCostList(List<ExtendItemCostDTO> itemCostList) {
		this.itemCostList = itemCostList;
	}

	public List<ExtendItemPriceDTO> getItemPriceList() {
		return itemPriceList;
	}

	public void setItemPriceList(List<ExtendItemPriceDTO> itemPriceList) {
		this.itemPriceList = itemPriceList;
	}

	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}

	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
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

	public List<ExtendKeepDTO> getKeepList() {
		return keepList;
	}

	public void setKeepList(List<ExtendKeepDTO> keepList) {
		this.keepList = keepList;
	}

	public List<ExtendKeepDTO> getAddKeepList() {
		return addKeepList;
	}

	public void setAddKeepList(List<ExtendKeepDTO> addKeepList) {
		this.addKeepList = addKeepList;
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

	public List<MstChannelDTO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<MstChannelDTO> channelList) {
		this.channelList = channelList;
	}

	public void setItemListSortMap(Map<String, String> itemListSortMap) {
		this.itemListSortMap = itemListSortMap;
	}

	public void setItemListSortOrder(Map<String, String> itemListSortOrder) {
		this.itemListSortOrder = itemListSortOrder;
	}

	public void setListPageMaxMap(Map<String, Integer> listPageMaxMap) {
		this.listPageMaxMap = listPageMaxMap;
	}

	public List<ResultItemSearchDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ResultItemSearchDTO> itemList) {
		this.itemList = itemList;
	}

	public int getItemListPageMax() {
		return itemListPageMax;
	}

	public void setItemListPageMax(int itemListPageMax) {
		this.itemListPageMax = itemListPageMax;
	}

	public List<MstWarehouseDTO> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<MstWarehouseDTO> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public SearchItemDTO getSearchItemDTO() {
		return searchItemDTO;
	}

	public void setSearchItemDTO(SearchItemDTO searchItemDTO) {
		this.searchItemDTO = searchItemDTO;
	}

	public List<SysItemIdDTO> getSysItemIdList() {
		return sysItemIdList;
	}

	public void setSysItemIdList(List<SysItemIdDTO> sysItemIdList) {
		this.sysItemIdList = sysItemIdList;
	}

	public ErrorMessageDTO getErrorMessageDTO() {
		return errorMessageDTO;
	}

	public void setErrorMessageDTO(ErrorMessageDTO errorMessageDTO) {
		this.errorMessageDTO = errorMessageDTO;
	}

	public int getSysItemIdListSize() {
		return sysItemIdListSize;
	}

	public void setSysItemIdListSize(int sysItemIdListSize) {
		this.sysItemIdListSize = sysItemIdListSize;
	}

	public int getPageIdx() {
		return pageIdx;
	}

	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}

	public Map<String, String> getItemListSortMap() {
		return itemListSortMap;
	}

	public Map<String, String> getItemListSortOrder() {
		return itemListSortOrder;
	}

	public Map<String, Integer> getListPageMaxMap() {
		return listPageMaxMap;
	}

	public ExtendMstItemDTO getMstItemDTO() {
		return mstItemDTO;
	}

	public void setMstItemDTO(ExtendMstItemDTO mstItemDTO) {
		this.mstItemDTO = mstItemDTO;
	}

	public long getSysItemId() {
		return sysItemId;
	}

	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	public MstUserDTO getMstUserDTO() {
		return mstUserDTO;
	}

	public void setMstUserDTO(MstUserDTO mstUserDTO) {
		this.mstUserDTO = mstUserDTO;
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

	public int getWarehouseLength() {
		return warehouseLength;
	}

	public void setWarehouseLength(int warehouseLength) {
		this.warehouseLength = warehouseLength;
	}

	public List<ExtendSalesItemDTO> getSalesCostList() {
		return salesCostList;
	}

	public void setSalesCostList(List<ExtendSalesItemDTO> salesCostList) {
		this.salesCostList = salesCostList;
	}

	/**
	 * 合計値エリア表示非表示
	 * @return sumDispMap
	 */
	public Map<String, String> getSumDispMap() {
		return sumDispMap;
	}

	/**
	 * 法人間請求書管理画面検索【仮】DTO
	 * @return btoBillSearchDto
	 */
	public BtobBillSearchDTO getBtobBillSearchDto() {
		return btobBillSearchDto;
	}

	/**
	 * 法人間請求書管理画面検索【仮】DTO
	 * @param btoBillSearchDto
	 */
	public void setBtobBillSearchDto(BtobBillSearchDTO btobBillSearchDto) {
		this.btobBillSearchDto = btobBillSearchDto;
	}

	/**
	 * 粗利計算方法
	 * @return grossProfitCalcMap
	 */
	public Map<String, String> getBtobBillGrossProfitCalcMap() {
		return btobBillGrossProfitCalcMap;
	}

	/**
	 * 粗利計算方法
	 * @param grossProfitCalcMap セットする grossProfitCalcMap
	 */
	public void setBtobBillGrossProfitCalcMap(Map<String, String> btobBillGrossProfitCalcMap) {
		this.btobBillGrossProfitCalcMap = btobBillGrossProfitCalcMap;
	}

	/**
	 * 合計値エリア用（業販から流用）
	 * @return corporateSaleListTotalDTO
	 */
	public CorporateSaleListTotalDTO getCorporateSaleListTotalDTO() {
		return corporateSaleListTotalDTO;
	}

	/**
	 * 合計値エリア用（業販から流用）
	 * @param corporateSaleListTotalDTO セットする corporateSaleListTotalDTO
	 */
	public void setCorporateSaleListTotalDTO(CorporateSaleListTotalDTO corporateSaleListTotalDTO) {
		this.corporateSaleListTotalDTO = corporateSaleListTotalDTO;
	}

	/**
	 * 合計値エリア用計算？（業販から流用）
	 * @return sysCorporateSalesSlipIdList
	 */
	public List<SysCorporateSalesSlipIdDTO> getSysCorporateSalesSlipIdList() {
		return sysCorporateSalesSlipIdList;
	}

	/**
	 * 合計値エリア用計算？（業販から流用）
	 * @param sysCorporateSalesSlipIdList セットする sysCorporateSalesSlipIdList
	 */
	public void setSysCorporateSalesSlipIdList(List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList) {
		this.sysCorporateSalesSlipIdList = sysCorporateSalesSlipIdList;
	}

	/**
	 * 法人名
	 * @return corporationNm
	 */
	public String getCorporationNm() {
		return corporationNm;
	}

	/**
	 * 法人名
	 * @param corporationNm セットする corporationNm
	 */
	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}


	/*    ここまで、ゲッターセッター    */

	public int getSaleListPageMax() {
		return saleListPageMax;
	}

	public void setSaleListPageMax(int saleListPageMax) {
		this.saleListPageMax = saleListPageMax;
	}

	/**
	 * 法人間請求書機能Formの再初期化
	 */
	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {

		//Kind原価一覧のチェックボックスの値を初期化
		if ("/initKindCostList".equals(appMapping.getPath()) || "/kindCostList".equals(appMapping.getPath())) {
			this.getSearchItemDTO().setBackOrderFlg(StringUtil.SWITCH_OFF_KEY);
			this.getSearchItemDTO().setOrderAlertFlg(StringUtil.SWITCH_OFF_KEY);
			this.getSearchItemDTO().setOverArrivalScheduleFlg(StringUtil.SWITCH_OFF_KEY);
			this.searchItemDTO = new SearchItemDTO();

			this.setPageIdx(0);
			this.setSysItemIdListSize(0);
			mstUserDTO = new MstUserDTO();
		}

		//売上原価一覧のページ情報と検索条件を初期化
		if ("/initSaleCostList".equals(appMapping.getPath()) || "/saleCostList".equals(appMapping.getPath())) {
			this.setSaleCostPageIdx(0);
			this.setSysSaleItemIDListSize(0);
			this.saleCostSearchDTO = new SaleCostSearchDTO();
		}

		//業販原価一覧のページ情報と検索条件を初期化
		if ("/initCorporateSaleCostList".equals(appMapping.getPath()) || "/corporateSaleCostList".equals(appMapping.getPath())) {
			this.setCorpSaleCostPageIdx(0);
			this.setSysCorprateSaleItemIDListSize(0);
			this.corpSaleCostSearchDTO = new CorporateSaleCostSearchDTO();
		}

		//法人間請求書一覧のページ情報を初期化
		if ("/initBtobBillList".equals(appMapping.getPath()) || "/btobBillList".equals(appMapping.getPath())) {
			this.setBtobBillPageIdx(0);
			this.setBtobBillListSize(0);
		}

		this.setSaleCostListIdx(0);
		this.setCorpSaleCostListIdx(0);
		this.setBtobBillListIdx(0);
		errorDTO = new ErrorDTO();
		alertType = "0";

	}




}
