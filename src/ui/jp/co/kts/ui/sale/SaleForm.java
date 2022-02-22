package jp.co.kts.ui.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstChannelDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.SaleListTotalDTO;
import jp.co.kts.app.output.entity.SysSalesSlipIdDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * @author admin
 *
 */
public class SaleForm extends AppBaseForm{

	private String alertType;

	/**
	 * エラーメッセージ
	 */
	private ErrorDTO errorDTO = new ErrorDTO();

	/** **/
	private String pickoutputFlg;

	/**
	 * ---------------------------------------売上一覧---------------------------------------------------------
	 */
	/**
	 * 売上一覧検索
	 */
	private SaleSearchDTO saleSearchDTO = new SaleSearchDTO();

	private List<SysSalesSlipIdDTO> sysSalesSlipIdList = new ArrayList<>();

	private List<ExtendSalesSlipDTO> salesSlipList = new ArrayList<>();

	private ExtendSalesSlipDTO salesSlipDTO = new ExtendSalesSlipDTO();

	private SaleListTotalDTO saleListTotalDTO = new SaleListTotalDTO();

	private int pageIdx = 0;

	private int sysSalesSlipIdListSize = 0;

	private long sysSalesSlipId;

	private int saleListPageMax;

	/**
	 * ---------------------------------------売上詳細---------------------------------------------------------
	 */
	/**
	 * 売上商品リスト
	 */
	private List<ExtendSalesItemDTO> salesItemList = new ArrayList<>();

	/**
	 * 売上商品追加用リスト
	 */
	private List<ExtendSalesItemDTO> addSalesItemList = new ArrayList<>();

	private String returnButtonFlg = "0";

	private SearchItemDTO searchItemDTO = new SearchItemDTO();

	/**
	 * 更新者情報表示用
	 */
	private MstUserDTO mstUserDTO = new MstUserDTO();

	/**
	 * ---------------------------------------出庫---------------------------------------------------------
	 */
	/**
	 * 出庫処理
	 */
	private List<ExtendSalesItemDTO> leaveStockList = new ArrayList<>();
	/**
	 * ---------------------------------------getterのみ---------------------------------------------------------
	 */
	/**
	 * 決済方法マップ
	 */
	private Map<String, String> accountMethodMap = WebConst.ACCOUNT_METHOD_MAP;

	private Map<String, String> invoiceClassificationMap = WebConst.INVOICE_CLASSIFICATION_MAP_B2;

	private Map<String, String> disposalRouteMap = WebConst.DISPOSAL_ROUTE_MAP;

	private Map<String, String> transportCorporationSystemMap = WebConst.TRANSPORT_CORPORATION_SYSTEM_MAP;

	private Map<String, String> etcPriceMap = WebConst.ETC_PRICE_MAP;

	private long salesItemLength = WebConst.ADD_SALES_ITEM_LENGTH;

	//20140326 八鍬
	private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;

	private Map<String, String> taxMap = WebConst.TAX_MAP;

	/** 並替え */
	private Map<String, String> saleSearchMap = WebConst.SALE_SEARCH_MAP;

	/** 昇順・降順 */
	private Map<String, String> saleSearchSortOrder = WebConst.SALE_SEARCH_SORT_ORDER;

	private int taxRate5 = WebConst.TAX_RATE_5;

	private int taxRate8 = WebConst.TAX_RATE_8;

	private String taxUpDate8 = WebConst.TAX_UP_DATE_8;

	private int taxRate10 = WebConst.TAX_RATE_10;

	private String taxUpDate10 = WebConst.TAX_UP_DATE_10;


	/** 粗利計算方法 */
	private Map<String, String> grossProfitCalcMap = WebConst.GROSS_PROFIT_CALC_MAP;

	/** 売上統計マップ */
	private Map<String, String> summalySortMap = WebConst.SUMMALY_SORT_MAP;
	/**
	 * ---------------------------------------getterのみ---------------------------------------------------------
	 */

	/**
	 * 法人プルダウン
	 */
	private List<MstCorporationDTO> corporationList = new ArrayList<>();
	/**
	 * 販売チャネルプルダウン
	 */
	private List<MstChannelDTO> channelList = new ArrayList<>();

	private final Map<String, String> sumDispMap = WebConst.SUM_DISP_MAP;

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {


		//チェックボックスの値
		if ("/updateDetailSale".equals(appMapping.getPath())) {
			this.getSalesSlipDTO().setPickingListFlg(StringUtil.SWITCH_OFF_KEY);
//			this.getSalesSlipDTO().setPickingFlg(StringUtil.SWITCH_OFF_KEY);
			this.getSalesSlipDTO().setLeavingFlg(StringUtil.SWITCH_OFF_KEY);
		}
		if ("/initSaleList".equals(appMapping.getPath()) || "/searchSaleList".equals(appMapping.getPath())) {

			this.setSysSalesSlipIdListSize(0);
			this.setPageIdx(0);
			this.saleSearchDTO = new SaleSearchDTO();
		}
		if ("/lumpUpdateSales".equals(appMapping.getPath())) {

			for (ExtendSalesSlipDTO dto: this.salesSlipList) {

				dto.setPickingListFlg(StringUtil.SWITCH_OFF_KEY);
				dto.setLeavingFlg(StringUtil.SWITCH_OFF_KEY);
			}
		}
		errorDTO = new ErrorDTO();

		mstUserDTO = new MstUserDTO();
		alertType = "0";
		returnButtonFlg ="0";
	}



	/**
	 * @return alertType
	 */
	public String getAlertType() {
		return alertType;
	}



	/**
	 * @param alertType セットする alertType
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}



	public ErrorDTO getErrorDTO() {
		return errorDTO;
	}

	public void setErrorDTO(ErrorDTO errorDTO) {
		this.errorDTO = errorDTO;
	}


	/**
	 * @return pickoutputFlg
	 */
	public String getPickoutputFlg() {
		return pickoutputFlg;
	}

	/**
	 * @param pickoutputFlg セットする pickoutputFlg
	 */
	public void setPickoutputFlg(String pickoutputFlg) {
		this.pickoutputFlg = pickoutputFlg;
	}

	public SaleSearchDTO getSaleSearchDTO() {
		return saleSearchDTO;
	}

	public void setSaleSearchDTO(SaleSearchDTO saleSearchDTO) {
		this.saleSearchDTO = saleSearchDTO;
	}

	/**
	 * @return mstUserDTO
	 */
	public MstUserDTO getMstUserDTO() {
		return mstUserDTO;
	}



	/**
	 * @param mstUserDTO セットする mstUserDTO
	 */
	public void setMstUserDTO(MstUserDTO mstUserDTO) {
		this.mstUserDTO = mstUserDTO;
	}



	public List<SysSalesSlipIdDTO> getSysSalesSlipIdList() {
		return sysSalesSlipIdList;
	}

	public void setSysSalesSlipIdList(List<SysSalesSlipIdDTO> sysSalesSlipIdList) {
		this.sysSalesSlipIdList = sysSalesSlipIdList;
	}



	/**
	 * @return saleListPageMax
	 */
	public int getSaleListPageMax() {
		return saleListPageMax;
	}



	/**
	 * @param saleListPageMax セットする saleListPageMax
	 */
	public void setSaleListPageMax(int saleListPageMax) {
		this.saleListPageMax = saleListPageMax;
	}



	public List<ExtendSalesSlipDTO> getSalesSlipList() {
		return salesSlipList;
	}

	public void setSalesSlipList(List<ExtendSalesSlipDTO> salesSlipList) {
		this.salesSlipList = salesSlipList;
	}

	public ExtendSalesSlipDTO getSalesSlipDTO() {
		return salesSlipDTO;
	}

	public void setSalesSlipDTO(ExtendSalesSlipDTO salesSlipDTO) {
		this.salesSlipDTO = salesSlipDTO;
	}

	public SaleListTotalDTO getSaleListTotalDTO() {
		return saleListTotalDTO;
	}

	public void setSaleListTotalDTO(SaleListTotalDTO saleListTotalDTO) {
		this.saleListTotalDTO = saleListTotalDTO;
	}

	public int getPageIdx() {
		return pageIdx;
	}

	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}

	public int getSysSalesSlipIdListSize() {
		return sysSalesSlipIdListSize;
	}

	public void setSysSalesSlipIdListSize(int sysSalesSlipIdListSize) {
		this.sysSalesSlipIdListSize = sysSalesSlipIdListSize;
	}

	public long getSysSalesSlipId() {
		return sysSalesSlipId;
	}

	public void setSysSalesSlipId(long sysSalesSlipId) {
		this.sysSalesSlipId = sysSalesSlipId;
	}

	public List<ExtendSalesItemDTO> getSalesItemList() {
		return salesItemList;
	}

	public void setSalesItemList(List<ExtendSalesItemDTO> salesItemList) {
		this.salesItemList = salesItemList;
	}

	public List<ExtendSalesItemDTO> getAddSalesItemList() {
		return addSalesItemList;
	}

	public void setAddSalesItemList(List<ExtendSalesItemDTO> addSalesItemList) {
		this.addSalesItemList = addSalesItemList;
	}

	/**
	 * @return returnButtonFlg
	 */
	public String getReturnButtonFlg() {
		return returnButtonFlg;
	}



	/**
	 * @param returnButtonFlg セットする returnButtonFlg
	 */
	public void setReturnButtonFlg(String returnButtonFlg) {
		this.returnButtonFlg = returnButtonFlg;
	}

	public SearchItemDTO getSearchItemDTO() {
		return searchItemDTO;
	}

	public void setSearchItemDTO(SearchItemDTO searchItemDTO) {
		this.searchItemDTO = searchItemDTO;
	}

	public List<ExtendSalesItemDTO> getLeaveStockList() {
		return leaveStockList;
	}

	public void setLeaveStockList(List<ExtendSalesItemDTO> leaveStockList) {
		this.leaveStockList = leaveStockList;
	}

	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}

	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
	}

	public List<MstChannelDTO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<MstChannelDTO> channelList) {
		this.channelList = channelList;
	}

	/**
	 * ---------------------------------------getterのみ-------------------------------------------
	 */
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

	public Map<String, String> getEtcPriceMap() {
		return etcPriceMap;
	}
	public long getSalesItemLength() {
		return salesItemLength;
	}

	/**
	 * @return listPageMaxMap
	 */
	public Map<String, Integer> getListPageMaxMap() {
		return listPageMaxMap;
	}

	public Map<String, String> getTaxMap() {
		return taxMap;
	}

	public Map<String, String> getSaleSearchMap() {
		return saleSearchMap;
	}

	public Map<String, String> getSaleSearchSortOrder() {
		return saleSearchSortOrder;
	}

	public int getTaxRate5() {
		return taxRate5;
	}

	public int getTaxRate8() {
		return taxRate8;
	}

	public String getTaxUpDate8() {
		return taxUpDate8;
	}

	public int getTaxRate10() {
		return taxRate10;
	}

	public String getTaxUpDate10() {
		return taxUpDate10;
	}



	/**
	 * @return grossProfitCalcMap
	 */
	public Map<String, String> getGrossProfitCalcMap() {
		return grossProfitCalcMap;
	}



	/**
	 * @param grossProfitCalcMap セットする grossProfitCalcMap
	 */
	public void setGrossProfitCalcMap(Map<String, String> grossProfitCalcMap) {
		this.grossProfitCalcMap = grossProfitCalcMap;
	}



	public Map<String, String> getSummalySortMap() {
		return summalySortMap;
	}



	/**
	 * @return sumDispMap
	 */
	public Map<String, String> getSumDispMap() {
		return sumDispMap;
	}

	/**
	 * ---------------------------------------getterのみ-------------------------------------------
	 */

}
