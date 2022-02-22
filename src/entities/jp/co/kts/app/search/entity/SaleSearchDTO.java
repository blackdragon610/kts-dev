package jp.co.kts.app.search.entity;

import jp.co.keyaki.cleave.common.util.StringUtil;

/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
public class SaleSearchDTO {


	private long sysSalesSlipId;

	//チェックボックス
	private String pickingListFlg = StringUtil.SWITCH_OFF_KEY;
	private String leavingFlg = StringUtil.SWITCH_OFF_KEY;
	private String slipNoExist = StringUtil.SWITCH_OFF_KEY;
	private String slipNoHyphen = StringUtil.SWITCH_OFF_KEY;
	private String slipNoNone = StringUtil.SWITCH_OFF_KEY;
	private String searchAllFlg = StringUtil.SWITCH_OFF_KEY;
	// i/o
	/* -- 1行目 -- */

	/** 注文日 */
	private String orderDateFrom;
	private String orderDateTo;

	//法人ID
	private int sysCorporationId;

	/** 出荷予定日 */
	private String shipmentPlanDateFrom;
	private String shipmentPlanDateTo;

	/* -- 2行目 -- */
	/** 受注番号 */
	private String orderNo;

	//チャネルID
	private int sysChannelId;

	//伝票番号（送り状種別）
	private String slipNo;

	/* -- 3行目 -- */

	//注文者名
	private String orderNm;

	//品番
	private String salesItemCode;
	private String itemCode;
	private String itemCodeFrom;
	private String itemCodeTo;

	/** 送り状種別 */
	private String invoiceClassification;

	/* -- 4行目 -- */

	/** 注文者電話番号 */
	private String orderTel;

	/** 商品名 */
	private String salesItemNm;
	private String itemNm;

	/** お届け先名 */
	private String destinationNm;
	/* -- 5行目 -- */

	/** 注文者メールアドレス */
	private String orderMailAddress;

	/** 運送会社システム */
	private String transportCorporationSystem;

	/** お届け先電話番号 */
	private String destinationTel;

	/* -- 6行目 -- */

	/** 決済方法 */
	private String accountMethod;

	/** お届け指定日 */
	private String destinationAppointDateFrom;

	/** お届け指定日 */
	private String destinationAppointDateTo;

	/** 処理ルート */
	private String disposalRoute;

	/* -- 7行目 -- */
	/** 一言メモ（伝票） */
	private String slipMemo;

	/** 請求金額 */
	private String sumClaimPriceFrom;

	/** 請求金額 */
	private String sumClaimPriceTo;

	private String sortFirst;

	private String sortFirstSub;

	private String listPageMax;

	private String grossProfitCalc;

	private String memo;

	private String itemCodeAreaFlg;

	//返品伝票チェックボックス
	private String returnFlg = StringUtil.SWITCH_OFF_KEY;

	/** 出荷日 */
	private String shipmentDateFrom;
	private String shipmentDateTo;

	/** 合計値表示フラグ */
	private String sumDispFlg;
	
	private int orderType;
	
	private String orderContent;

	private int telType;
	
	private String telContent;
	
	private String wholseSalerName;


	public long getSysSalesSlipId() {
		return sysSalesSlipId;
	}

	public void setSysSalesSlipId(long sysSalesSlipId) {
		this.sysSalesSlipId = sysSalesSlipId;
	}

	public String getPickingListFlg() {
		return pickingListFlg;
	}

	public void setPickingListFlg(String pickingListFlg) {
		this.pickingListFlg = pickingListFlg;
	}

	public String getLeavingFlg() {
		return leavingFlg;
	}

	public void setLeavingFlg(String leavingFlg) {
		this.leavingFlg = leavingFlg;
	}

	/**
	 * @return slipNoExist
	 */
	public String getSlipNoExist() {
		return slipNoExist;
	}

	/**
	 * @param slipNoExist セットする slipNoExist
	 */
	public void setSlipNoExist(String slipNoExist) {
		this.slipNoExist = slipNoExist;
	}

	/**
	 * @return slipNoHyphen
	 */
	public String getSlipNoHyphen() {
		return slipNoHyphen;
	}

	/**
	 * @param slipNoHyphen セットする slipNoHyphen
	 */
	public void setSlipNoHyphen(String slipNoHyphen) {
		this.slipNoHyphen = slipNoHyphen;
	}

	/**
	 * @return slipNoNone
	 */
	public String getSlipNoNone() {
		return slipNoNone;
	}

	/**
	 * @param slipNoNone セットする slipNoNone
	 */
	public void setSlipNoNone(String slipNoNone) {
		this.slipNoNone = slipNoNone;
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
	 * 注文日From
	 * @return
	 */
	public String getOrderDateFrom() {
		return orderDateFrom;
	}

	/**
	 * 注文日From
	 * @param orderDateFrom
	 */
	public void setOrderDateFrom(String orderDateFrom) {
		this.orderDateFrom = orderDateFrom;
	}

	/**
	 * 注文日To
	 * @return
	 */
	public String getOrderDateTo() {
		return orderDateTo;
	}

	/**
	 * 注文日To
	 * @param orderDateTo
	 */
	public void setOrderDateTo(String orderDateTo) {
		this.orderDateTo = orderDateTo;
	}

	/**
	 * 法人ID
	 * @return
	 */
	public int getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 * 法人ID
	 * @param sysCorporationId
	 */
	public void setSysCorporationId(int sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public String getShipmentPlanDateFrom() {
		return shipmentPlanDateFrom;
	}

	public void setShipmentPlanDateFrom(String shipmentPlanDateFrom) {
		this.shipmentPlanDateFrom = shipmentPlanDateFrom;
	}

	public String getShipmentPlanDateTo() {
		return shipmentPlanDateTo;
	}

	public void setShipmentPlanDateTo(String shipmentPlanDateTo) {
		this.shipmentPlanDateTo = shipmentPlanDateTo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * チャネルID
	 * @return
	 */
	public int getSysChannelId() {
		return sysChannelId;
	}

	/**
	 * チャネルID
	 * @param sysChannelId
	 */
	public void setSysChannelId(int sysChannelId) {
		this.sysChannelId = sysChannelId;
	}

	public String getSlipNo() {
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	public String getOrderNm() {
		return orderNm;
	}

	public void setOrderNm(String orderNm) {
		this.orderNm = orderNm;
	}

	public String getSalesItemCode() {
		return salesItemCode;
	}

	public void setSalesItemCode(String salesItemCode) {
		this.salesItemCode = salesItemCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCodeFrom() {
		return itemCodeFrom;
	}

	public void setItemCodeFrom(String itemCodeFrom) {
		this.itemCodeFrom = itemCodeFrom;
	}

	public String getItemCodeTo() {
		return itemCodeTo;
	}

	public void setItemCodeTo(String itemCodeTo) {
		this.itemCodeTo = itemCodeTo;
	}

	public String getInvoiceClassification() {
		return invoiceClassification;
	}

	public void setInvoiceClassification(String invoiceClassification) {
		this.invoiceClassification = invoiceClassification;
	}

	public String getOrderTel() {
		return orderTel;
	}

	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}

	public String getSalesItemNm() {
		return salesItemNm;
	}

	public void setSalesItemNm(String salesItemNm) {
		this.salesItemNm = salesItemNm;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getDestinationNm() {
		return destinationNm;
	}

	public void setDestinationNm(String destinationNm) {
		this.destinationNm = destinationNm;
	}

	public String getOrderMailAddress() {
		return orderMailAddress;
	}

	public void setOrderMailAddress(String orderMailAddress) {
		this.orderMailAddress = orderMailAddress;
	}

	public String getTransportCorporationSystem() {
		return transportCorporationSystem;
	}

	public void setTransportCorporationSystem(String transportCorporationSystem) {
		this.transportCorporationSystem = transportCorporationSystem;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getAccountMethod() {
		return accountMethod;
	}

	public void setAccountMethod(String accountMethod) {
		this.accountMethod = accountMethod;
	}

	public String getDestinationAppointDateFrom() {
		return destinationAppointDateFrom;
	}

	public void setDestinationAppointDateFrom(String destinationAppointDateFrom) {
		this.destinationAppointDateFrom = destinationAppointDateFrom;
	}

	public String getDestinationAppointDateTo() {
		return destinationAppointDateTo;
	}

	public void setDestinationAppointDateTo(String destinationAppointDateTo) {
		this.destinationAppointDateTo = destinationAppointDateTo;
	}

	/**
	 * 処理ルート
	 * @return
	 */
	public String getDisposalRoute() {
		return disposalRoute;
	}

	/**
	 * 処理ルート
	 * @param disposalRoute
	 */
	public void setDisposalRoute(String disposalRoute) {
		this.disposalRoute = disposalRoute;
	}

	public String getSlipMemo() {
		return slipMemo;
	}

	public void setSlipMemo(String slipMemo) {
		this.slipMemo = slipMemo;
	}

	public String getSumClaimPriceFrom() {
		return sumClaimPriceFrom;
	}

	public void setSumClaimPriceFrom(String sumClaimPriceFrom) {
		this.sumClaimPriceFrom = sumClaimPriceFrom;
	}

	public String getSumClaimPriceTo() {
		return sumClaimPriceTo;
	}

	public void setSumClaimPriceTo(String sumClaimPriceTo) {
		this.sumClaimPriceTo = sumClaimPriceTo;
	}

	public String getSortFirst() {
		return sortFirst;
	}

	public void setSortFirst(String sortFirst) {
		this.sortFirst = sortFirst;
	}

	public String getSortFirstSub() {
		return sortFirstSub;
	}

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
	 * @return memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo セットする memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getReturnFlg() {
		return returnFlg;
	}

	public void setReturnFlg(String returnFlg) {
		this.returnFlg = returnFlg;
	}

	/**
	 * 出荷日From
	 * @return shipmentDateFrom
	 */
	public String getShipmentDateFrom() {
		return shipmentDateFrom;
	}

	/**
	 * 出荷日From
	 * @param shipmentDateFrom セットする shipmentDateFrom
	 */
	public void setShipmentDateFrom(String shipmentDateFrom) {
		this.shipmentDateFrom = shipmentDateFrom;
	}

	/**
	 * 出荷日To
	 * @return shipmentDateTo
	 */
	public String getShipmentDateTo() {
		return shipmentDateTo;
	}

	/**
	 * 出荷日To
	 * @param shipmentDateTo セットする shipmentDateTo
	 */
	public void setShipmentDateTo(String shipmentDateTo) {
		this.shipmentDateTo = shipmentDateTo;
	}

	/**
	 * @return itemCodeAreaFlg
	 */
	public String getItemCodeAreaFlg() {
		return itemCodeAreaFlg;
	}

	/**
	 * @param itemCodeAreaFlg セットする itemCodeAreaFlg
	 */
	public void setItemCodeAreaFlg(String itemCodeAreaFlg) {
		this.itemCodeAreaFlg = itemCodeAreaFlg;
	}

	/**
	 * @return sumDispFlg
	 */
	public String getSumDispFlg() {
		return sumDispFlg;
	}

	/**
	 * @param sumDispFlg セットする sumDispFlg
	 */
	public void setSumDispFlg(String sumDispFlg) {
		this.sumDispFlg = sumDispFlg;
	}

	public int getOrderType() {
		return this.orderType;
	}
	
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getOrderContent() {
		return this.orderContent;
	}
	
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	public String getWholseSalerName() {
		return this.wholseSalerName;
	}
	
	public void setWholseSalerName(String wholseSalerName) {
		this.wholseSalerName = wholseSalerName;
	}

	public int getTelType() {
		return telType;
	}

	public void setTelType(int telType) {
		this.telType = telType;
	}

	public String getTelContent() {
		return telContent;
	}

	public void setTelContent(String telContent) {
		this.telContent = telContent;
	}

}
