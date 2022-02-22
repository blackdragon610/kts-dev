package jp.co.kts.app.common.entity;

public class ForeignSlipSearchDTO {

	private long sysForeignSlipId;
	/** 品番 */
	private String itemCode;

	/** 工場品番 */
	private String factoryItemCode;

	/** 検索方法1 */
	private String searchMethod1;

	/** 検索方法2 */
	private String searchMethod2;

	/** 商品名 */
	private String itemNm;

	/** 会社・工場名 */
	private String companyFactoryNm;

	/** インボイスNo */
	private String invoiceNo;

	/** PONo */
	private String poNo;

	/** 注文日(始まり) */
	private String orderDateFrom;

	/** 注文日(終わり) */
	private String orderDateTo;

	/** 入荷予定日(始まり) */
	private String arrivalScheduleDateFrom;

	/** 入荷予定日(終わり) */
	private String arrivalScheduleDateTo;

	/** 入荷日(始まり) */
	private String arrivalDateFrom;

	/** 入荷日(終わり) */
	private String arrivalDateTo;

	/** 曖昧入荷日 */
	private String vagueArrivalSchedule;

	/** ステータス */
	private String orderStatus;

	/** 入荷状態 */
	private String arriveStatusFlg;

	/** 支払状態1 */
	private String paymentStatus1Flg;

	/** 支払状態2 */
	private String paymentStatus2Flg;

	private String deliveryDateOverFlg1;

	private String deliveryDateOverFlg2;

	/** 並び順(基準項目) */
	private String sortFirst;

	/** 並び順(順番) */
	private String sortFirstSub;

	/** 表示件数 */
	private String listPageMax;

	/** 商品検索フラグ */
	private int itemSearchFlag;

	/** 検索優先度 */
	private String searchPriority;

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

	/**
	 * @return itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return factoryItemCode
	 */
	public String getFactoryItemCode() {
		return factoryItemCode;
	}

	/**
	 * @param factoryItemCode
	 */
	public void setFactoryItemCode(String factoryItemCode) {
		this.factoryItemCode = factoryItemCode;
	}



	/**
	 * @return searchMethod1
	 */
	public String getSearchMethod1() {
		return searchMethod1;
	}

	/**
	 * @param searchMethod1
	 */
	public void setSearchMethod1(String searchMethod1) {
		this.searchMethod1 = searchMethod1;
	}

	/**
	 * @return searchMethod2
	 */
	public String getSearchMethod2() {
		return searchMethod2;
	}

	/**
	 * @param searchMethod2
	 */
	public void setSearchMethod2(String searchMethod2) {
		this.searchMethod2 = searchMethod2;
	}

	/**
	 * @return itemNm
	 */
	public String getItemNm() {
		return itemNm;
	}

	/**
	 * @param itemNm
	 */
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	/**
	 * @return companyFactoryNm
	 */
	public String getCompanyFactoryNm() {
		return companyFactoryNm;
	}

	/**
	 * @param companyFactoryNm
	 */
	public void setCompanyFactoryNm(String companyFactoryNm) {
		this.companyFactoryNm = companyFactoryNm;
	}

	/**
	 * @return invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return poNo
	 */
	public String getPoNo() {
		return poNo;
	}

	/**
	 * @param poNo
	 */
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	/**
	 * @return orderDateFrom
	 */
	public String getOrderDateFrom() {
		return orderDateFrom;
	}

	/**
	 * @param orderDateFrom
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
	 * @param orderDateTo
	 */
	public void setOrderDateTo(String orderDateTo) {
		this.orderDateTo = orderDateTo;
	}

	/**
	 * @return arrivalScheduleDateFrom
	 */
	public String getArrivalScheduleDateFrom() {
		return arrivalScheduleDateFrom;
	}

	/**
	 * @param arrivalScheduleDateFrom
	 */
	public void setArrivalScheduleDateFrom(String arrivalScheduleDateFrom) {
		this.arrivalScheduleDateFrom = arrivalScheduleDateFrom;
	}

	/**
	 * @return arrivalScheduledDateTo
	 */
	public String getArrivalScheduleDateTo() {
		return arrivalScheduleDateTo;
	}

	/**
	 * @param arrivalScheduledDateTo
	 */
	public void setArrivalScheduleDateTo(String arrivalScheduleDateTo) {
		this.arrivalScheduleDateTo = arrivalScheduleDateTo;
	}

	/**
	 * @return arrivalDateFrom
	 */
	public String getArrivalDateFrom() {
		return arrivalDateFrom;
	}

	/**
	 * @param arrivalDateFrom
	 */
	public void setArrivalDateFrom(String arrivalDateFrom) {
		this.arrivalDateFrom = arrivalDateFrom;
	}

	/**
	 * @return arrivalDateTo
	 */
	public String getArrivalDateTo() {
		return arrivalDateTo;
	}

	/**
	 * @param arrivalDateTo
	 */
	public void setArrivalDateTo(String arrivalDateTo) {
		this.arrivalDateTo = arrivalDateTo;
	}

	/**
	 * @return vagueArrivalSchedule
	 */
	public String getVagueArrivalSchedule() {
		return vagueArrivalSchedule;
	}

	/**
	 * @param vagueArrivalSchedule
	 */
	public void setVagueArrivalSchedule(String vagueArrivalSchedule) {
		this.vagueArrivalSchedule = vagueArrivalSchedule;
	}

	/**
	 * @return orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return arriveStatus
	 */
	public String getArriveStatusFlg() {
		return arriveStatusFlg;
	}

	/**
	 * @param arriveStatus
	 */
	public void setArriveStatusFlg(String arriveStatusFlg) {
		this.arriveStatusFlg = arriveStatusFlg;
	}

	/**
	 * @return paymentStatus1
	 */
	public String getPaymentStatus1Flg() {
		return paymentStatus1Flg;
	}

	/**
	 * @param paymentStatus1
	 */
	public void setPaymentStatus1Flg(String paymentStatus1Flg) {
		this.paymentStatus1Flg = paymentStatus1Flg;
	}

	/**
	 * @return paymentStatus2
	 */
	public String getPaymentStatus2Flg() {
		return paymentStatus2Flg;
	}

	/**
	 * @param paymentStatus2
	 */
	public void setPaymentStatus2Flg(String paymentStatus2Flg) {
		this.paymentStatus2Flg = paymentStatus2Flg;
	}

	/**
	 * @return deliveryDateOverFlg1
	 */
	public String getDeliveryDateOverFlg1() {
		return deliveryDateOverFlg1;
	}

	/**
	 * @param deliveryDateOverFlg1
	 */
	public void setDeliveryDateOverFlg1(String deliveryDateOverFlg1) {
		this.deliveryDateOverFlg1 = deliveryDateOverFlg1;
	}

	/**
	 * @return deliveryDateOverFlg2
	 */
	public String getDeliveryDateOverFlg2() {
		return deliveryDateOverFlg2;
	}

	/**
	 * @param deliveryDateOverFlg2
	 */
	public void setDeliveryDateOverFlg2(String deliveryDateOverFlg2) {
		this.deliveryDateOverFlg2 = deliveryDateOverFlg2;
	}

	/**
	 * @return sortFirst
	 */
	public String getSortFirst() {
		return sortFirst;
	}

	/**
	 * @param sortFirst
	 */
	public void setSortFirst(String sortFirst) {
		this.sortFirst = sortFirst;
	}

	/**
	 * @return sortFirstSub
	 */
	public String getSortFirstSub() {
		return sortFirstSub;
	}

	/**
	 * @param sortFirstSub
	 */
	public void setSortFirstSub(String sortFirstSub) {
		this.sortFirstSub = sortFirstSub;
	}

	/**
	 * @return listPageMax
	 */
	public String getListPageMax() {
		return listPageMax;
	}

	/**
	 * @param listPageMax
	 */
	public void setListPageMax(String listPageMax) {
		this.listPageMax = listPageMax;
	}

	/**
	 * @return itemSearchFlag
	 */
	public int getItemSearchFlag() {
		return itemSearchFlag;
	}

	/**
	 * @param itemSearchFlag セットする itemSearchFlag
	 */
	public void setItemSearchFlag(int itemSearchFlag) {
		this.itemSearchFlag = itemSearchFlag;
	}

	/**
	 * @return searchPriority
	 */
	public String getSearchPriority() {
		return searchPriority;
	}

	/**
	 * @param searchPriority
	 */
	public void setSearchPriority(String searchPriority) {
		this.searchPriority = searchPriority;
	}
}