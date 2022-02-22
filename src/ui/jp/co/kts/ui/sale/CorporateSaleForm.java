package jp.co.kts.ui.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.CorporateBillDTO;
import jp.co.kts.app.common.entity.CorporateReceiveDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstAccountDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstDeliveryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendPaymentManagementDTO;
import jp.co.kts.app.output.entity.CorporateSaleListTotalDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;
import jp.co.kts.app.search.entity.ClientSearchDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.ui.web.struts.WebConst;

public class CorporateSaleForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 法人情報
	 */
	private long sysCorporationId;
	private long sysCorporateBillId;

	private List<ExtendMstAccountDTO> accountList = new ArrayList<>();




	private long sysCorporateSalesSlipId;

	//法人情報
	private MstCorporationDTO corporationDTO = new MstCorporationDTO();
	private List<MstCorporationDTO> corporationList = new ArrayList<>();

	private ExtendMstClientDTO clientDTO = new ExtendMstClientDTO();
	private Map<String, String> paymentMethodMap = WebConst.PAYMENT_METHOD_MAP;

	private List<ExtendMstDeliveryDTO> deliveryList = new ArrayList<>();

	//伝票情報
	private ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = new ExtendCorporateSalesSlipDTO();
	private List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList = new ArrayList<>();

	//業販請求書用伝票情報
	private ExtendCorporateBillDTO corporateBillDTO = new ExtendCorporateBillDTO();
	private List<ExtendCorporateBillDTO> corporateBillList = new ArrayList<>();

	//入金管理用情報
	private ExtendPaymentManagementDTO paymentManageDTO = new ExtendPaymentManagementDTO();
	private List<ExtendPaymentManagementDTO> paymentManageDTOList = new ArrayList<>();

	/** 売上統計マップ */
	private Map<String, String> summalySortMap = WebConst.SUMMALY_SORT_MAP;
	/** 昇順・降順 */
	private Map<String, String> saleSearchSortOrder = WebConst.SALE_SEARCH_SORT_ORDER;
	/** 締日マップ */
	private Map<String, String> billingCutoffDate = WebConst.CUTOFF_DATE_MAP;


	private String alertType;

	/**
	 * 更新者情報表示用
	 */
	private MstUserDTO mstUserDTO = new MstUserDTO();

	/**
	 * 入金リスト
	 */
	private List<CorporateReceiveDTO> corporateReceiveList = new ArrayList<>();

	/**
	 * 売上商品リスト
	 */
	private List<ExtendCorporateSalesItemDTO> corporateSalesItemList = new ArrayList<>();

	/**
	 * 売上商品追加用リスト
	 */
	private List<ExtendCorporateSalesItemDTO> addCorporateSalesItemList = new ArrayList<>();

	/**
	 * 配送情報MAP
	 */
	private Map<String, String> genpyoKbnMapSeino = WebConst.GENPYO_KBN_MAP_SEINO;
	private Map<String, String> yusoShijiMapSeino = WebConst.YUSO_SHIJI_MAP_SEINO;
	private Map<String, String> invoiceClassificationMapB2 = WebConst.INVOICE_CLASSIFICATION_MAP_B2;
	private Map<String, String> invoiceClassificationMapEhiden = WebConst.INVOICE_CLASSIFICATION_MAP_EHIDEN;
	private Map<String, String> invoiceClassificationMapSeino = WebConst.INVOICE_CLASSIFICATION_MAP_SEINO;
    private Map<String, String> transportCorporationSystemMap = WebConst.TRANSPORT_CORPORATION_SYSTEM_MAP;
	private Map<String, String> taxMap = WebConst.TAX_MAP;
	private Map<String, String> appointTimeB2Map = WebConst.APPOINT_TIME_B2_MAP;
	private Map<String, String> appointTimeEhidenMap = WebConst.APPOINT_TIME_EHIDEN_MAP;
	private Map<String, String> appointTimeSeinoMap = WebConst.APPOINT_TIME_SEINO_MAP;

	/**
	 * サブウィンド検索用
	 */
	private ClientSearchDTO clientSearchDTO = new ClientSearchDTO();
	private SearchItemDTO searchItemDTO = new SearchItemDTO();

	/**
	 * 同時最大登録数
	 */
	private long salesItemLength = WebConst.ADD_CORP_SALES_ITEM_LENGTH;

	/**
	 * 税率判定用
	 */
	private int taxRate5 = WebConst.TAX_RATE_5;
	private int taxRate8 = WebConst.TAX_RATE_8;
	private String taxUpDate8 = WebConst.TAX_UP_DATE_8;
	private int taxRate10 = WebConst.TAX_RATE_10;
	private String taxUpDate10 = WebConst.TAX_UP_DATE_10;

	/**
	 * ピックアップリスト出力用
	 */
	private String pickoutputFlg;

	/**
	 * 見積書出力用
	 */
	private String estimateOutputFlag;

	/**
	 * 請求書出力用
	 */
	private String exportMonth;
	private String selectedCutoff;
	private String currency;

	/**
	 * 注文請書出力用
	 */
	private String orderAcceptanceOutputFlag;

	/**
	 * 戻るボタンフラグ
	 */
	private String returnButtonFlg = "0";

	/**
	 * 納入先新規登録フラグ
	 */
	private boolean newDeliveryFlg = false;

	/**
	 * 納品書発行・再発行用フラグ
	 */
	private String pdfPattern = "0";

	/**
	 * 検索用
	 */
	private CorporateSaleSearchDTO corporateSaleSearchDTO = new CorporateSaleSearchDTO();
	private List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList = new ArrayList<>();
	private int sysCorporateSalesSlipIdListSize = 0;
	private CorporateSaleListTotalDTO corporateSaleListTotalDTO = new CorporateSaleListTotalDTO();
	private ErrorDTO errorDTO = new ErrorDTO();
	private int pageIdx = 0;
	private int corporateSaleListPageMax = 20;
	private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;
	private Map<String, Integer> listPageMaxForPaymentManagementMap = WebConst.LIST_PAGE_MAX_FOR_PAYMENTMANAGEMENT_MAP;


	/**
	 * ---------------------------------------出庫---------------------------------------------------------
	 */
	/**
	 * 出庫処理
	 */
	private List<ExtendCorporateSalesItemDTO> leaveStockList = new ArrayList<>();
	/**
	 */


	/* 法人間請求一覧入力用Formデータ */

	/** 表示対象法人情報 */
	private long dispSysCorporationId;

	/** 出力法人ID */
	private long exportSysCorporationId;

	/**  一覧の表示ページ  */
	private int corporateBillPageIdx = 0;

	/**  一覧のインデックス  */
	private int corporateBillListIdx = 0;

	/** 一覧の表示ページ */
	private int paymentManagePageIdx = 0;

	/** 一覧のインデックス */
	private int paymentManageListIdx = 0;

	/**  表示用締日  */
	private int dispCutoffDate;

	/**  法人間請求書一覧リスト検索結果件数  */
	private int corporateBillListSize = 0;

	/** 入金管理リスト検索結果件数 */
	private int paymentManageListSize = 0;

	/**
	 * １ページ当りの最大表示件数
	 * 初期表示は50件で出力する。
	 */
	private int corporateBillListPageMax = 50;

	/**
	 * 1ページあたりの最大表示件数
	 * 初期表示は10件で表示
	 * (入金管理)
	 */
	private int paymentManageListPageMax = 10;

	/** 法人間請求書一覧リスト */
	private List<ExtendCorporateBillDTO> corporateBillDTOList = new ArrayList<>();

	/** システム法人間請求書IDリスト */
	private List<CorporateBillDTO> sysCorporateBillIdList = new ArrayList<>();

	/** システム入金管理情報IDリスト */
	private List<ExtendPaymentManagementDTO> sysPayManageIdList = new ArrayList<>();

	private final Map<String, String> sumDispMap = WebConst.SUM_DISP_MAP;

	/** 粗利計算方法 */
	private Map<String, String> grossProfitCalcMap = WebConst.GROSS_PROFIT_CALC_MAP;

	/** 選択された業販請求書ID */
	private long[] sysCorporateBillIdArray;

	/**
	 * setter / getter ---------------------------------------------------------------------------------------------
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public long getSysCorporateBillId() {
		return sysCorporateBillId;
	}

	public void setSysCorporateBillId(long sysCorporateBillId) {
		this.sysCorporateBillId = sysCorporateBillId;
	}




	/**
	 * @return accountList
	 */
	public List<ExtendMstAccountDTO> getAccountList() {
		return accountList;
	}

	/**
	 * @param accountList セットする accountList
	 */
	public void setAccountList(List<ExtendMstAccountDTO> accountList) {
		this.accountList = accountList;
	}



	/**
	 * @return sysCorporateSalesSlipId
	 */
	public long getSysCorporateSalesSlipId() {
		return sysCorporateSalesSlipId;
	}

	/**
	 * @param sysCorporateSalesSlipId セットする sysCorporateSalesSlipId
	 */
	public void setSysCorporateSalesSlipId(long sysCorporateSalesSlipId) {
		this.sysCorporateSalesSlipId = sysCorporateSalesSlipId;
	}



	/**
	 * @return corporateSalesSlipDTO
	 */
	public ExtendCorporateSalesSlipDTO getCorporateSalesSlipDTO() {
		return corporateSalesSlipDTO;
	}

	/**
	 * @param corporateSalesSlipDTO セットする corporateSalesSlipDTO
	 */
	public void setCorporateSalesSlipDTO(ExtendCorporateSalesSlipDTO corporateSalesSlipDTO) {
		this.corporateSalesSlipDTO = corporateSalesSlipDTO;
	}



	/**
	 * @return corporateSalesSlipList
	 */
	public List<ExtendCorporateSalesSlipDTO> getCorporateSalesSlipList() {
		return corporateSalesSlipList;
	}

	/**
	 * @param corporateSalesSlipList セットする corporateSalesSlipList
	 */
	public void setCorporateSalesSlipList(
			List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList) {
		this.corporateSalesSlipList = corporateSalesSlipList;
	}



	/**
	 * @return corporateBillDTO
	 */
	public ExtendCorporateBillDTO getCorporateBillDTO() {
		return corporateBillDTO;
	}

	/**
	 * @param corporateBillDTO セットする corporateBillDTO
	 */
	public void setCorporateBillDTO(ExtendCorporateBillDTO corporateBillDTO) {
		this.corporateBillDTO = corporateBillDTO;
	}


	/**
	 * @return corporateBillList
	 */
	public List<ExtendCorporateBillDTO> getCorporateBillList() {
		return corporateBillList;
	}

	/**
	 * @param corporateBillList セットする corporateBillList
	 */
	public void setCorporateBillList(
			List<ExtendCorporateBillDTO> corporateBillList) {
		this.corporateBillList = corporateBillList;
	}


	/**
	 * @return paymentManageDTO
	 */
	public ExtendPaymentManagementDTO getPaymentManageDTO() {
		return paymentManageDTO;
	}

	/**
	 * @param paymentManageDTO セットする paymentManageDTO
	 */
	public void setPaymentManageDTO(ExtendPaymentManagementDTO paymentManageDTO) {
		this.paymentManageDTO = paymentManageDTO;
	}

	/**
	 * @return paymentManageList
	 */
	public List<ExtendPaymentManagementDTO> getPaymentManageDTOList() {
		return paymentManageDTOList;
	}

	/**
	 * @param paymentManageList セットする paymentManageList
	 */
	public void setPaymentManageDTOList(List<ExtendPaymentManagementDTO> paymentManageList) {
		this.paymentManageDTOList = paymentManageList;
	}

	public MstCorporationDTO getCorporationDTO() {
		return corporationDTO;
	}

	public void setCorporationDTO(MstCorporationDTO corporationDTO) {
		this.corporationDTO = corporationDTO;
	}



	/**
	 * @return corporationList
	 */
	public List<MstCorporationDTO> getCorporationList() {
		return corporationList;
	}

	/**
	 * @param corporationList セットする corporationList
	 */
	public void setCorporationList(List<MstCorporationDTO> corporationList) {
		this.corporationList = corporationList;
	}



	/**
	 * @return client
	 */
	public ExtendMstClientDTO getClientDTO() {
		return clientDTO;
	}

	/**
	 * @param client セットする client
	 */
	public void setClientDTO(ExtendMstClientDTO clientDTO) {
		this.clientDTO = clientDTO;
	}



	/**
	 * @return deliveryList
	 */
	public List<ExtendMstDeliveryDTO> getDeliveryList() {
		return deliveryList;
	}

	/**
	 * @param deliveryList セットする deliveryList
	 */
	public void setDeliveryList(List<ExtendMstDeliveryDTO> deliveryList) {
		this.deliveryList = deliveryList;
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






	/**
	 * @return corporateSalesItemList
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemList() {
		return corporateSalesItemList;
	}

	/**
	 * @param corporateSalesItemList セットする corporateSalesItemList
	 */
	public void setCorporateSalesItemList(
			List<ExtendCorporateSalesItemDTO> corporateSalesItemList) {
		this.corporateSalesItemList = corporateSalesItemList;
	}



	/**
	 * @return addCorporateSalesItemList
	 */
	public List<ExtendCorporateSalesItemDTO> getAddCorporateSalesItemList() {
		return addCorporateSalesItemList;
	}

	/**
	 * @param addCorporateSalesItemList セットする addCorporateSalesItemList
	 */
	public void setAddCorporateSalesItemList(
			List<ExtendCorporateSalesItemDTO> addCorporateSalesItemList) {
		this.addCorporateSalesItemList = addCorporateSalesItemList;
	}

	/**
	 * @return invoiceClassificationMap
	 */
	public Map<String, String> getInvoiceClassificationMapB2() {
		return invoiceClassificationMapB2;
	}

	public Map<String, String> getInvoiceClassificationMapEhiden() {
		return invoiceClassificationMapEhiden;
	}

    public Map<String, String> getInvoiceClassificationMapSeino() {
        return invoiceClassificationMapSeino;
    }

    public Map<String, String> getGenpyoKbnMapSeino() {
        return genpyoKbnMapSeino;
    }

    public Map<String, String> getYusoShijiMapSeino() {
        return yusoShijiMapSeino;
    }

	/**
	 * @return transportCorporationSystemMap
	 */
	public Map<String, String> getTransportCorporationSystemMap() {
		return transportCorporationSystemMap;
	}

	/**
	 * @return taxMap
	 */
	public Map<String, String> getTaxMap() {
		return taxMap;
	}

	/**
	 * @return clientSearchDTO
	 */
	public ClientSearchDTO getClientSearchDTO() {
		return clientSearchDTO;
	}

	/**
	 * @param clientSearchDTO セットする clientSearchDTO
	 */
	public void setClientSearchDTO(ClientSearchDTO clientSearchDTO) {
		this.clientSearchDTO = clientSearchDTO;
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
	 * @return salesItemLength
	 */
	public long getSalesItemLength() {
		return salesItemLength;
	}

	/**
	 * @return taxRate5
	 */
	public int getTaxRate5() {
		return taxRate5;
	}

	/**
	 * @return taxRate8
	 */
	public int getTaxRate8() {
		return taxRate8;
	}

	/**
	 * @return taxUpDate8
	 */
	public String getTaxUpDate8() {
		return taxUpDate8;
	}

	/**
	 * @return taxRate10
	 */
	public int getTaxRate10() {
		return taxRate10;
	}

	/**
	 * @return taxUpDate10
	 */
	public String getTaxUpDate10() {
		return taxUpDate10;
	}



	/**
	 * 納品書発行・再発行用フラグを取得します。
	 * @return 納品書発行・再発行用フラグ
	 */
	public String getPdfPattern() {
	    return pdfPattern;
	}

	/**
	 * 納品書発行・再発行用フラグを設定します。
	 * @param pdfPattern 納品書発行・再発行用フラグ
	 */
	public void setPdfPattern(String pdfPattern) {
	    this.pdfPattern = pdfPattern;
	}

	/**
	 * @return coroprateSaleSearchDTO
	 */
	public CorporateSaleSearchDTO getCorporateSaleSearchDTO() {
		return corporateSaleSearchDTO;
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



	/**
	 * @param coroprateSaleSearchDTO セットする coroprateSaleSearchDTO
	 */
	public void setCorporateSaleSearchDTO(
			CorporateSaleSearchDTO corporateSaleSearchDTO) {
		this.corporateSaleSearchDTO = corporateSaleSearchDTO;
	}

	/**
	 * @return sysCorporateSalesSlipIdList
	 */
	public List<SysCorporateSalesSlipIdDTO> getSysCorporateSalesSlipIdList() {
		return sysCorporateSalesSlipIdList;
	}

	/**
	 * @param sysCorporateSalesSlipIdList セットする sysCorporateSalesSlipIdList
	 */
	public void setSysCorporateSalesSlipIdList(
			List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList) {
		this.sysCorporateSalesSlipIdList = sysCorporateSalesSlipIdList;
	}

	/**
	 * @return sysCorporateSalesSlipIdSize
	 */
	public int getSysCorporateSalesSlipIdListSize() {
		return sysCorporateSalesSlipIdListSize;
	}

	/**
	 * @param sysCorporateSalesSlipIdSize セットする sysCorporateSalesSlipIdSize
	 */
	public void setSysCorporateSalesSlipIdListSize(int sysCorporateSalesSlipIdListSize) {
		this.sysCorporateSalesSlipIdListSize = sysCorporateSalesSlipIdListSize;
	}

	/**
	 * @return corporateSalesListTotalDTO
	 */
	public CorporateSaleListTotalDTO getCorporateSaleListTotalDTO() {
		return corporateSaleListTotalDTO;
	}

	/**
	 * @param corporateSalesListTotalDTO セットする corporateSalesListTotalDTO
	 */
	public void setCorporateSaleListTotalDTO(
			CorporateSaleListTotalDTO corporateSaleListTotalDTO) {
		this.corporateSaleListTotalDTO = corporateSaleListTotalDTO;
	}

	/**
	 * @return errorDTO
	 */
	public ErrorDTO getErrorDTO() {
		return errorDTO;
	}

	/**
	 * @param errorDTO セットする errorDTO
	 */
	public void setErrorDTO(ErrorDTO errorDTO) {
		this.errorDTO = errorDTO;
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

	/**
	 * @return corporateSaleListPageMax
	 */
	public int getCorporateSaleListPageMax() {
		return corporateSaleListPageMax;
	}

	/**
	 * @param corporateSaleListPageMax セットする corporateSaleListPageMax
	 */
	public void setCorporateSaleListPageMax(int corporateSaleListPageMax) {
		this.corporateSaleListPageMax = corporateSaleListPageMax;
	}

	/**
	 * @return listPageMaxForPaymentManagementMap
	 */
	public Map<String, Integer> getListPageMaxForPaymentManagementMap() {
		return listPageMaxForPaymentManagementMap;
	}

	/**
	 * @param listPageMaxForPaymentManagementMap セットする listPageMaxForPaymentManagementMap
	 */
	public void setListPageMaxForPaymentManagementMap(Map<String, Integer> listPageMaxForPaymentManagementMap) {
		this.listPageMaxForPaymentManagementMap = listPageMaxForPaymentManagementMap;
	}

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {

		//チェックボックスの値
		if ("/updateDetailCorporateSale".equals(appMapping.getPath())) {
		}
		if ("/initCorporateSaleList".equals(appMapping.getPath()) || "/searchCorporateSaleList".equals(appMapping.getPath())) {

			this.setSysCorporateSalesSlipIdListSize(0);
			this.setPageIdx(0);
			this.corporateSaleSearchDTO = new CorporateSaleSearchDTO();
		}
		if ("/lumpUpdateCorporateSales".equals(appMapping.getPath())) {

			for (ExtendCorporateSalesSlipDTO dto: this.corporateSalesSlipList) {
			}
		}
		errorDTO = new ErrorDTO();

		mstUserDTO = new MstUserDTO();
		alertType = "0";
		newDeliveryFlg = false;
//		returnButtonFlg ="0";
	}

	/**
	 * @return leaveStockList
	 */
	public List<ExtendCorporateSalesItemDTO> getLeaveStockList() {
		return leaveStockList;
	}

	/**
	 * @param leaveStockList セットする leaveStockList
	 */
	public void setLeaveStockList(List<ExtendCorporateSalesItemDTO> leaveStockList) {
		this.leaveStockList = leaveStockList;
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

	/**
	 * @return listPageMaxMap
	 */
	public Map<String, Integer> getListPageMaxMap() {
		return listPageMaxMap;
	}

	/**
	 * @param listPageMaxMap セットする listPageMaxMap
	 */
	public void setListPageMaxMap(Map<String, Integer> listPageMaxMap) {
		this.listPageMaxMap = listPageMaxMap;
	}

	/**
	 * @return paymentMethodMap
	 */
	public Map<String, String> getPaymentMethodMap() {
		return paymentMethodMap;
	}

	/**
	 * @param paymentMethodMap セットする paymentMethodMap
	 */
	public void setPaymentMethodMap(Map<String, String> paymentMethodMap) {
		this.paymentMethodMap = paymentMethodMap;
	}

	/**
	 * @return estimateOutputFlag
	 */
	public String getEstimateOutputFlag() {
		return estimateOutputFlag;
	}

	/**
	 * @param estimateOutputFlag セットする estimateOutputFlag
	 */
	public void setEstimateOutputFlag(String estimateOutputFlag) {
		this.estimateOutputFlag = estimateOutputFlag;
	}

	/**
	 * @return orderAcceptanceOutputFlag
	 */
	public String getOrderAcceptanceOutputFlag() {
		return orderAcceptanceOutputFlag;
	}

	/**
	 * @param orderAcceptanceOutputFlag セットする orderAcceptanceOutputFlag
	 */
	public void setOrderAcceptanceOutputFlag(String orderAcceptanceOutputFlag) {
		this.orderAcceptanceOutputFlag = orderAcceptanceOutputFlag;
	}

	/**
	 * @return corporateReceiveList
	 */
	public List<CorporateReceiveDTO> getCorporateReceiveList() {
		return corporateReceiveList;
	}

	/**
	 * @param corporateReceiveList セットする corporateReceiveList
	 */
	public void setCorporateReceiveList(List<CorporateReceiveDTO> corporateReceiveList) {
		this.corporateReceiveList = corporateReceiveList;
	}

	/**
	 * @return exportMonth
	 */
	public String getExportMonth() {
		return exportMonth;
	}

	/**
	 * @param exportMonth セットする exportMonth
	 */
	public void setExportMonth(String exportMonth) {
		this.exportMonth = exportMonth;
	}

	/**
	 * @return selectedCutoff
	 */
	public String getSelectedCutoff() {
		return selectedCutoff;
	}

	/**
	 * @param exportMonth セットする selectedCutoff
	 */
	public void setSelectedCutoff(String selectedCutoff) {
		this.selectedCutoff = selectedCutoff;
	}


	/**
	 * @return currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency セットする currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return newDeliveryFlg
	 */
	public boolean getNewDeliveryFlg() {
		return newDeliveryFlg;
	}

	/**
	 * @param newDeliveryFlg セットする newDeliveryFlg
	 */
	public void setNewDeliveryFlg(boolean newDeliveryFlg) {
		this.newDeliveryFlg = newDeliveryFlg;
	}

	/**
	 * @return summalySortMap
	 */
	public Map<String, String> getSummalySortMap() {
		return summalySortMap;
	}

	/**
	 * @param summalySortMap セットする summalySortMap
	 */
	public void setSummalySortMap(Map<String, String> summalySortMap) {
		this.summalySortMap = summalySortMap;
	}

	/**
	 * @return saleSearchSortOrder
	 */
	public Map<String, String> getSaleSearchSortOrder() {
		return saleSearchSortOrder;
	}

	/**
	 * @param saleSearchSortOrder セットする saleSearchSortOrder
	 */
	public void setSaleSearchSortOrder(Map<String, String> saleSearchSortOrder) {
		this.saleSearchSortOrder = saleSearchSortOrder;
	}

	/**
	 * @return billingCutoffDate
	 */
	public Map<String, String> getBillingCutoffDate() {
		return billingCutoffDate;
	}

	/**
	 * @param billingCutoffDate セットする billingCutoffDate
	 */
	public void setBillingCutoffDate(Map<String, String> billingCutoffDate) {
		this.billingCutoffDate = billingCutoffDate;
	}

	/**
	 * @return appointTimeB2Map
	 */
	public Map<String, String> getAppointTimeB2Map() {
		return appointTimeB2Map;
	}

	/**
	 * @param appointTimeB2Map セットする appointTimeB2Map
	 */
	public void setAppointTimeB2Map(Map<String, String> appointTimeB2Map) {
		this.appointTimeB2Map = appointTimeB2Map;
	}

	/**
	 * @return appointTimeEhidenMap
	 */
	public Map<String, String> getAppointTimeEhidenMap() {
		return appointTimeEhidenMap;
	}

	/**
	 * @param appointTimeEhidenMap セットする appointTimeEhidenMap
	 */
	public void setAppointTimeEhidenMap(Map<String, String> appointTimeEhidenMap) {
		this.appointTimeEhidenMap = appointTimeEhidenMap;
	}

	/**
     * @return appointTimeSeinoMap
     */
    public Map<String, String> getAppointTimeSeinoMap() {
        return appointTimeSeinoMap;
    }

    /**
     * @param appointTimeSeinoMap セットする appointTimeSeinoMap
     */
    public void setAppointTimeSeinoMap(Map<String, String> appointTimeSeinoMap) {
        this.appointTimeSeinoMap = appointTimeSeinoMap;
    }

	//エクスポート用追加分

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
	public int getCorporateBillPageIdx() {
		return corporateBillPageIdx;
	}

	public void setCorporateBillPageIdx(int corporateBillPageIdx) {
		this.corporateBillPageIdx = corporateBillPageIdx;
	}

	public int getCorporateBillListIdx() {
		return corporateBillListIdx;
	}

	public void setCorporateBillListIdx(int corporateBillListIdx) {
		this.corporateBillListIdx = corporateBillListIdx;
	}
	public int getCorporateBillListPageMax() {
		return corporateBillListPageMax;
	}

	public void setDispCutoffDate(int dispCutoffDate) {
		this.dispCutoffDate = dispCutoffDate;
	}
	public int getDispCutoffDate() {
		return dispCutoffDate;
	}

	public void setCorporateBillListPageMax(int corporateBillListPageMax) {
		this.corporateBillListPageMax = corporateBillListPageMax;
	}
	public List<ExtendCorporateBillDTO> getCorporateBillDTOList() {
		return corporateBillDTOList;
	}

	public void setCorporateBillDTOList(List<ExtendCorporateBillDTO> corporateBillDTOList) {
		this.corporateBillDTOList = corporateBillDTOList;
	}
	public int getCorporateBillListSize() {
		return corporateBillListSize;
	}

	public void setCorporateBillListSize(int corporateBillListSize) {
		this.corporateBillListSize = corporateBillListSize;
	}
	public List<CorporateBillDTO> getSysCorporateBillIdList() {
		return sysCorporateBillIdList;
	}

	public void setSysCorporateBillIdList(List<CorporateBillDTO> sysCorporateBillIdList) {
		this.sysCorporateBillIdList = sysCorporateBillIdList;
	}

	/**
	 * @return sysPayManageIdList
	 */
	public List<ExtendPaymentManagementDTO> getSysPayManageIdList() {
		return sysPayManageIdList;
	}

	/**
	 * @param sysPayManageIdList セットする sysPayManageIdList
	 */
	public void setSysPayManageIdList(List<ExtendPaymentManagementDTO> sysPayManageIdList) {
		this.sysPayManageIdList = sysPayManageIdList;
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


	/**
	 * @return sumDispMap
	 */
	public Map<String, String> getSumDispMap() {
		return sumDispMap;
	}

	/**
	 * 業販請求書IDを取得する。
	 * @return 業販請求書ID
	 */
	public long[] getSysCorporateBillIdArray() {
		return sysCorporateBillIdArray;
	}

	/**
	 * 業販請求書IDを設定する。
	 * @param sysCorporateBillIdArray セットする業販請求書ID
	 */
	public void setSysCorporateBillIdArray(long[] sysCorporateBillIdArray) {
		this.sysCorporateBillIdArray = sysCorporateBillIdArray;
	}

	/**
	 * @return paymentManagePageIdx
	 */
	public int getPaymentManagePageIdx() {
		return paymentManagePageIdx;
	}

	/**
	 * @param paymentManagePageIdx セットする paymentManagePageIdx
	 */
	public void setPaymentManagePageIdx(int paymentManagePageIdx) {
		this.paymentManagePageIdx = paymentManagePageIdx;
	}

	/**
	 * @return paymentManageListIdx
	 */
	public int getPaymentManageListIdx() {
		return paymentManageListIdx;
	}

	/**
	 * @param paymentManageListIdx セットする paymentManageListIdx
	 */
	public void setPaymentManageListIdx(int paymentManageListIdx) {
		this.paymentManageListIdx = paymentManageListIdx;
	}

	/**
	 * @return paymentManageListSize
	 */
	public int getPaymentManageListSize() {
		return paymentManageListSize;
	}

	/**
	 * @param paymentManageListSize セットする paymentManageListSize
	 */
	public void setPaymentManageListSize(int paymentManageListSize) {
		this.paymentManageListSize = paymentManageListSize;
	}

	/**
	 * @return paymentManageListPageMax
	 */
	public int getPaymentManageListPageMax() {
		return paymentManageListPageMax;
	}

	/**
	 * @param paymentManageListPageMax セットする paymentManageListPageMax
	 */
	public void setPaymentManageListPageMax(int paymentManageListPageMax) {
		this.paymentManageListPageMax = paymentManageListPageMax;
	}
}
