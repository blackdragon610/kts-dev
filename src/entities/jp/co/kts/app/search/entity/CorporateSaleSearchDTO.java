package jp.co.kts.app.search.entity;

import java.util.Map;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
public class CorporateSaleSearchDTO {

	/** 伝票ID */
	private long sysCorporateSalesSlipId;

	/** 法人ID */
	private long sysCorporationId;

	/** 検索プリセット */
	private int searchPreset;

	/** 検索フォーム******************************************************* */
	/** 日付関連 ***********************************/
	/** 見積日 */
	private String estimateDateFrom;
	private String estimateDateTo;

	/** 受注日 */
	private String orderDateFrom;
	private String orderDateTo;

	/** 出庫日 */
	private String leavingDateFrom;
	private String leavingDateTo;

	/** 出庫予定日 */
	private String scheduledLeavingDateFrom;
	private String scheduledLeavingDateTo;


	/** 売上日 */
	private String salesDateFrom;
	private String salesDateTo;

	/** 請求日 */
	private String billingDateFrom;
	private String billingDateTo;

	/** セレクトボックス ************************************/
	/** 支払方法 */
	private String paymentMethod;
	private Map<String, String> paymentMethodMap = WebConst.PAYMENT_METHOD_MAP;
	private String transportCorporationSystem;

	/** 売掛残 */
	private String receivableBalance;

	/** ソート条件 */
	private String sortFirst;

	/** 昇順/降順 */
	private String sortFirstSub;

	/** 最大表示数 */
	private String listPageMax;

	/** 合計金額オプション */
	private String sumClaimPriceOption;

	/** チェックボックス ************************************************/
	/** 返品伝票フラグ */
	private String returnFlg = StringUtil.SWITCH_OFF_KEY;

	/** 全件表示フラグ */
	private String searchAllFlg = StringUtil.SWITCH_OFF_KEY;

	/** 伝票ステータス  見積/受注/売上*/
	private String slipStatusEstimate = StringUtil.SWITCH_OFF_KEY;
	private String slipStatusOrder = StringUtil.SWITCH_OFF_KEY;
	private String slipStatusSales = StringUtil.SWITCH_OFF_KEY;

	/** 無効フラグ */
	private String invalidFlag = StringUtil.SWITCH_OFF_KEY;

	/** テキストボックス ************************************************/
	/** 伝票No */
	private String orderNo;

	/** 得意先ID */
	private long sysClientId;

	/** 得意先名 */
	private String clientNm;

	/** 得意先番号 */
	private String clientNo;

	/** 納入先電話番号*/
	private String deliveryTelNo;

	/** 納入先名*/
	private String deliveryNm;

	/** 担当者 */
	private String personInCharge;

	/** 合計金額 */
	private String sumClaimPrice;

	/** ラジオボタン ************************************************/
	/** 伝票ステータス */
	private String slipStatus;

	/** ピッキング済みフラグ */
	private String pickingListFlg = StringUtil.SWITCH_OFF_KEY;

	/** 出庫済フラグ */
	private String leavingFlg;

	/** 検索優先項目（伝票/商品） */
	private String searchPriority;

	/** 金額産出 */
	private String grossProfitCalc;

	/** 請求書出力用 */
	private String currency;

	// 他社商品コード
	private String salesItemCode;

	private String salesItemNm;

	/** 合計値表示フラグ */
	private String sumDispFlg;

	/* 得意先番号 */
	private String corporateBillNo;

	/* 請求月From */
	private String searchExportMonthFrom;

	/* 請求月To */
	private String searchExportMonthTo;

	/* 請求金額From */
	private String searchBillingAmountFrom;

	/* 請求金額To */
	private String searchBillingAmountTo;

	/* 法人ID */
	private long searchSysCorporationId;

	private String test;
	
	private String wholseSalerName;
	
	// end



	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	/** 統計用 *****************************************************/
	private String otherItemFlag;
	private String itemCode;
	private String itemCodeFrom;
	private String itemCodeTo;
	private String itemNm;
	private String sysCorporateSalesSlipIds;
	private String allOrderDisplayFlag;

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
	 * @return estimateDateFrom
	 */
	public String getEstimateDateFrom() {
		return estimateDateFrom;
	}

	/**
	 * @param estimateDateFrom セットする estimateDateFrom
	 */
	public void setEstimateDateFrom(String estimateDateFrom) {
		this.estimateDateFrom = estimateDateFrom;
	}

	/**
	 * @return estimateDateTo
	 */
	public String getEstimateDateTo() {
		return estimateDateTo;
	}

	/**
	 * @param estimateDateTo セットする estimateDateTo
	 */
	public void setEstimateDateTo(String estimateDateTo) {
		this.estimateDateTo = estimateDateTo;
	}

	/**
	 * @return orderDateFrom
	 */
	public String getOrderDateFrom() {
		return orderDateFrom;
	}

	/**
	 * @param orderDateFrom セットする orderDateFrom
	 */
	public void setOrderDateFrom(String orderDateFrom) {
		this.orderDateFrom = orderDateFrom;
	}

	/**
	 * @return orderDateTo
	 */
	public String getOrderDateTo() {
		return orderDateTo;
	}

	/**
	 * @param orderDateTo セットする orderDateTo
	 */
	public void setOrderDateTo(String orderDateTo) {
		this.orderDateTo = orderDateTo;
	}

	/**
	 * @return leavingDateFrom
	 */
	public String getLeavingDateFrom() {
		return leavingDateFrom;
	}

	/**
	 * @param leavingDateFrom セットする leavingDateFrom
	 */
	public void setLeavingDateFrom(String leavingDateFrom) {
		this.leavingDateFrom = leavingDateFrom;
	}

	/**
	 * @return leavingDateTo
	 */
	public String getLeavingDateTo() {
		return leavingDateTo;
	}

	/**
	 * @param leavingDateTo セットする leavingDateTo
	 */
	public void setLeavingDateTo(String leavingDateTo) {
		this.leavingDateTo = leavingDateTo;
	}

	/**
	 * @return scheduledLeavingDateFrom
	 */
	public String getScheduledLeavingDateFrom() {
		return scheduledLeavingDateFrom;
	}

	/**
	 * @param scheduledLeavingDateFrom セットする scheduledLeavingDateFrom
	 */
	public void setScheduledLeavingDateFrom(String scheduledLeavingDateFrom) {
		this.scheduledLeavingDateFrom = scheduledLeavingDateFrom;
	}

	/**
	 * @return scheduledLeavingDateTo
	 */
	public String getScheduledLeavingDateTo() {
		return scheduledLeavingDateTo;
	}

	/**
	 * @param scheduledLeavingDateTo セットする scheduledLeavingDateTo
	 */
	public void setScheduledLeavingDateTo(String scheduledLeavingDateTo) {
		this.scheduledLeavingDateTo = scheduledLeavingDateTo;
	}

	/**
	 * @return salesDateFrom
	 */
	public String getSalesDateFrom() {
		return salesDateFrom;
	}

	/**
	 * @param salesDateFrom セットする salesDateFrom
	 */
	public void setSalesDateFrom(String salesDateFrom) {
		this.salesDateFrom = salesDateFrom;
	}

	/**
	 * @return salesDateTo
	 */
	public String getSalesDateTo() {
		return salesDateTo;
	}

	/**
	 * @param salesDateTo セットする salesDateTo
	 */
	public void setSalesDateTo(String salesDateTo) {
		this.salesDateTo = salesDateTo;
	}

	/**
	 * @return billingDateFrom
	 */
	public String getBillingDateFrom() {
		return billingDateFrom;
	}

	/**
	 * @param billingDateFrom セットする billingDateFrom
	 */
	public void setBillingDateFrom(String billingDateFrom) {
		this.billingDateFrom = billingDateFrom;
	}

	/**
	 * @return billingDateTo
	 */
	public String getBillingDateTo() {
		return billingDateTo;
	}

	/**
	 * @param billingDateTo セットする billingDateTo
	 */
	public void setBillingDateTo(String billingDateTo) {
		this.billingDateTo = billingDateTo;
	}

	/**
	 * @return paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod セットする paymentMethod
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return sysCorporationId
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 * @param sysCorporationId セットする sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * @return receivableBalance
	 */
	public String getReceivableBalance() {
		return receivableBalance;
	}

	/**
	 * @param receivableBalance セットする receivableBalance
	 */
	public void setReceivableBalance(String receivableBalance) {
		this.receivableBalance = receivableBalance;
	}

	/**
	 * @return sortFirst
	 */
	public String getSortFirst() {
		return sortFirst;
	}

	/**
	 * @param sortFirst セットする sortFirst
	 */
	public void setSortFirst(String sortFirst) {
		this.sortFirst = sortFirst;
	}

	/**
	 * @return sortOrder
	 */
	public String getSortFirstSub() {
		return sortFirstSub;
	}

	/**
	 * @param sortOrder セットする sortOrder
	 */
	public void setSortFirstSub(String sortFirstSub) {
		this.sortFirstSub = sortFirstSub;
	}

	/**
	 * @return pickingListFlg
	 */
	public String getPickingListFlg() {
		return pickingListFlg;
	}

	/**
	 * @param pickingListFlg セットする pickingListFlg
	 */
	public void setPickingListFlg(String pickingListFlg) {
		this.pickingListFlg = pickingListFlg;
	}

	/**
	 * @return leavingFlg
	 */
	public String getLeavingFlg() {
		return leavingFlg;
	}

	/**
	 * @param leavingFlg セットする leavingFlg
	 */
	public void setLeavingFlg(String leavingFlg) {
		this.leavingFlg = leavingFlg;
	}

	/**
	 * @return returnFlg
	 */
	public String getReturnFlg() {
		return returnFlg;
	}

	/**
	 * @param returnFlg セットする returnFlg
	 */
	public void setReturnFlg(String returnFlg) {
		this.returnFlg = returnFlg;
	}

	/**
	 * @return searchAllFlg
	 */
	public String getSearchAllFlg() {
		return searchAllFlg;
	}

	/**
	 * @param searchAllFlg セットする searchAllFlg
	 */
	public void setSearchAllFlg(String searchAllFlg) {
		this.searchAllFlg = searchAllFlg;
	}

	/**
	 * @return slipStatusEstimate
	 */
	public String getSlipStatusEstimate() {
		return slipStatusEstimate;
	}

	/**
	 * @param slipStatusEstimate セットする slipStatusEstimate
	 */
	public void setSlipStatusEstimate(String slipStatusEstimate) {
		this.slipStatusEstimate = slipStatusEstimate;
	}

	/**
	 * @return slipStatusOrder
	 */
	public String getSlipStatusOrder() {
		return slipStatusOrder;
	}

	/**
	 * @param slipStatusOrder セットする slipStatusOrder
	 */
	public void setSlipStatusOrder(String slipStatusOrder) {
		this.slipStatusOrder = slipStatusOrder;
	}

	/**
	 * @return slipStatusSales
	 */
	public String getSlipStatusSales() {
		return slipStatusSales;
	}

	/**
	 * @param slipStatusSales セットする slipStatusSales
	 */
	public void setSlipStatusSales(String slipStatusSales) {
		this.slipStatusSales = slipStatusSales;
	}

	/**
	 * @return invalidFlag
	 */
	public String getInvalidFlag() {
		return invalidFlag;
	}

	/**
	 * @param invalidFlag セットする invalidFlag
	 */
	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}

	/**
	 * @return clientNm
	 */
	public String getClientNm() {
		return clientNm;
	}

	/**
	 * @param clientNm セットする clientNm
	 */
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}

	/**
	 * 納入先電話番号を取得します。
	 * @return 納入先電話番号
	 */
	public String getDeliveryTelNo() {
	    return deliveryTelNo;
	}

	/**
	 * 納入先電話番号を設定します。
	 * @param deliveryTelNo 納入先電話番号
	 */
	public void setDeliveryTelNo(String deliveryTelNo) {
	    this.deliveryTelNo = deliveryTelNo;
	}

	/**
	 * 納入先名を取得します。
	 * @return 納入先名
	 */
	public String getDeliveryNm() {
	    return deliveryNm;
	}

	/**
	 * 納入先名を設定します。
	 * @param deliveryNm 納入先名
	 */
	public void setDeliveryNm(String deliveryNm) {
	    this.deliveryNm = deliveryNm;
	}

	/**
	 * @return personInCharge
	 */
	public String getPersonInCharge() {
		return personInCharge;
	}

	/**
	 * @param personInCharge セットする personInCharge
	 */
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	/**
	 * @return listPageMax
	 */
	public String getListPageMax() {
		return listPageMax;
	}

	/**
	 * @param listPageMax セットする listPageMax
	 */
	public void setListPageMax(String listPageMax) {
		this.listPageMax = listPageMax;
	}

	/**
	 * @return grossProfitCalc
	 */
	public String getGrossProfitCalc() {
		return grossProfitCalc;
	}

	/**
	 * @param grossProfitCalc セットする grossProfitCalc
	 */
	public void setGrossProfitCalc(String grossProfitCalc) {
		this.grossProfitCalc = grossProfitCalc;
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
	 * @return searchPreset
	 */
	public int getSearchPreset() {
		return searchPreset;
	}

	/**
	 * @param searchPreset セットする searchPreset
	 */
	public void setSearchPreset(int searchPreset) {
		this.searchPreset = searchPreset;
	}

	/**
	 * @return slipStatus
	 */
	public String getSlipStatus() {
		return slipStatus;
	}

	/**
	 * @param slipStatus セットする slipStatus
	 */
	public void setSlipStatus(String slipStatus) {
		this.slipStatus = slipStatus;
	}

	/**
	 * @return transportCorporationSystem
	 */
	public String getTransportCorporationSystem() {
		return transportCorporationSystem;
	}

	/**
	 * @param transportCorporationSystem セットする transportCorporationSystem
	 */
	public void setTransportCorporationSystem(String transportCorporationSystem) {
		this.transportCorporationSystem = transportCorporationSystem;
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
	 * @return sysClientId
	 */
	public long getSysClientId() {
		return sysClientId;
	}

	/**
	 * @param sysClientId セットする sysClientId
	 */
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}

	/**
	 * @return orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo セットする orderNo
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return clientNo
	 */
	public String getClientNo() {
		return clientNo;
	}

	/**
	 * @param clientNo セットする clientNo
	 */
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	/**
	 * @return otherItemFlag
	 */
	public String getOtherItemFlag() {
		return otherItemFlag;
	}

	/**
	 * @param itemCodeAreaFlg セットする otherItemFlag
	 */
	public void setOtherItemFlag(String otherItemFlag) {
		this.otherItemFlag = otherItemFlag;
	}

	/**
	 * @return itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode セットする itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return itemCodeFrom
	 */
	public String getItemCodeFrom() {
		return itemCodeFrom;
	}

	/**
	 * @param itemCodeFrom セットする itemCodeFrom
	 */
	public void setItemCodeFrom(String itemCodeFrom) {
		this.itemCodeFrom = itemCodeFrom;
	}

	/**
	 * @return itemCodeTo
	 */
	public String getItemCodeTo() {
		return itemCodeTo;
	}

	/**
	 * @param itemCodeTo セットする itemCodeTo
	 */
	public void setItemCodeTo(String itemCodeTo) {
		this.itemCodeTo = itemCodeTo;
	}

	/**
	 * @return itemNm
	 */
	public String getItemNm() {
		return itemNm;
	}

	/**
	 * @param itemNm セットする itemNm
	 */
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	/**
	 * @return sysCorporateSalesSlipIds
	 */
	public String getSysCorporateSalesSlipIds() {
		return sysCorporateSalesSlipIds;
	}

	/**
	 * @param sysCorporateSalesSlipIds セットする sysCorporateSalesSlipIds
	 */
	public void setSysCorporateSalesSlipIds(String sysCorporateSalesSlipIds) {
		this.sysCorporateSalesSlipIds = sysCorporateSalesSlipIds;
	}

	public String getSalesItemCode() {
		return salesItemCode;
	}

	public void setSalesItemCode(String salesItemCode) {
		this.salesItemCode = salesItemCode;
	}

	public String getSalesItemNm() {
		return salesItemNm;
	}

	public void setSalesItemNm(String salesItemNm) {
		this.salesItemNm = salesItemNm;
	}

	/**
	 * @return searchPriority
	 */
	public String getSearchPriority() {
		return searchPriority;
	}

	/**
	 * @param searchPriority セットする searchPriority
	 */
	public void setSearchPriority(String searchPriority) {
		this.searchPriority = searchPriority;
	}

	/**
	 * @return sumClaimPrice
	 */
	public String getSumClaimPrice() {
		return sumClaimPrice;
	}

	/**
	 * @param sumClaimPrice セットする sumClaimPrice
	 */
	public void setSumClaimPrice(String sumClaimPrice) {
		this.sumClaimPrice = sumClaimPrice;
	}

	/**
	 * @return sumClaimPriceOption
	 */
	public String getSumClaimPriceOption() {
		return sumClaimPriceOption;
	}

	/**
	 * @param sumClaimPriceOption セットする sumClaimPriceOption
	 */
	public void setSumClaimPriceOption(String sumClaimPriceOption) {
		this.sumClaimPriceOption = sumClaimPriceOption;
	}

	/**
	 * @return 合計値表示フラグ
	 */
	public String getSumDispFlg() {
		return sumDispFlg;
	}

	/**
	 * @param 合計値表示フラグ セットする sumDispFlg
	 */
	public void setSumDispFlg(String sumDispFlg) {
		this.sumDispFlg = sumDispFlg;
	}

	/**
	 * @return 全受注表示フラグ
	 */
	public String getAllOrderDisplayFlag() {
		return allOrderDisplayFlag;
	}

	/**
	 * @param allOrderDisplayFlag セットする allOrderDisplayFlag
	 */
	public void setAllOrderDisplayFlag(String allOrderDisplayFlag) {
		this.allOrderDisplayFlag = allOrderDisplayFlag;
	}

	/**
	 * 請求月Fromを取得する。
	 * @return 請求月FROM
	 */
	public String getSearchExportMonthFrom() {
		return searchExportMonthFrom;
	}

	/**
	 * 請求月FROMを設定する。
	 * @param searchExportMonthFrom セットする請求月FROM
	 */
	public void setSearchExportMonthFrom(String searchExportMonthFrom) {
		this.searchExportMonthFrom = searchExportMonthFrom;
	}

	/**
	 * 請求月TOを取得する。
	 * @return 請求月TO
	 */
	public String getSearchExportMonthTo() {
		return searchExportMonthTo;
	}

	/**
	 * 請求月TOを設定する。
	 * @param searchExportMonthTo セットする請求月TO
	 */
	public void setSearchExportMonthTo(String searchExportMonthTo) {
		this.searchExportMonthTo = searchExportMonthTo;
	}

	/**
	 * 請求書番号を取得する。
	 * @return 請求書番号
	 */
	public String getCorporateBillNo() {
		return corporateBillNo;
	}

	/**
	 * 請求書番号を設定する。
	 * @param corporateBillNoHd セットする請求書番号
	 */
	public void setCorporateBillNo(String corporateBillNo) {
		this.corporateBillNo = corporateBillNo;
	}

	/**
	 * 請求金額Fromを取得する。
	 * @return 合計請求金額From
	 */
	public String getSearchBillingAmountFrom() {
		return searchBillingAmountFrom;
	}

	/**
	 * 請求金額Fromを設定する。
	 * @param searchBillingAmountFrom セットする請求金額From
	 */
	public void setSearchBillingAmountFrom(String searchBillingAmountFrom) {
		this.searchBillingAmountFrom = searchBillingAmountFrom;
	}

	/**
	 * 請求金額Toを取得する。
	 * @return 請求金額To
	 */
	public String getSearchBillingAmountTo() {
		return searchBillingAmountTo;
	}

	/**
	 * 請求金額Toを設定する。
	 * @param searchBillingAmountToセットする請求金額To
	 */
	public void setSearchBillingAmountTo(String searchBillingAmountTo) {
		this.searchBillingAmountTo = searchBillingAmountTo;
	}

	// TODO start
	/**
	 * 検索ボタン用法人番号を取得する。
	 * @return 検索ボタン用法人番号
	 */
	public long getSearchSysCorporationId() {
		return searchSysCorporationId;
	}

	/**
	 * 検索ボタン用法人番号を設定する。
	 * @param searchSysCorporationId セットする検索ボタン用法人番号
	 */
	public void setSearchSysCorporationId(long searchSysCorporationId) {
		this.searchSysCorporationId = searchSysCorporationId;
	}

	public String getWholseSalerName() {
		return wholseSalerName;
	}

	public void setWholseSalerName(String wholseSalerName) {
		this.wholseSalerName = wholseSalerName;
	}

	
}
