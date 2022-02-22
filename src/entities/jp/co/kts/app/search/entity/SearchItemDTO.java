package jp.co.kts.app.search.entity;

import org.apache.commons.lang.StringUtils;

public class SearchItemDTO {

	/** システム商品ID */
	private long sysItemId;

	/** 商品コード */
	private String itemCode;
	private String itemCodeFrom;
	private String itemCodeTo;

	/** 商品名 */
	private String itemNm;

	/** 受注番号 */
	private String orderNo;

	/** システム海外注文商品ID */
	private long sysForeignSlipItemId;

	/**親画面のインデックス */
	private long openerIdx;

	/**親画面の列カウント */
	private long rowCount;

	/**仕入先ＩＤ */
	private String sysSupplierId;

	private long sysCorporationId;

	private long sysWarehouseId;

	private String itemOrderDateFrom;

	private String itemOrderDateTo;

	private String arrivalScheduleDateFrom;

	private String arrivalScheduleDateTo;

	private String backOrderDateFrom;

	private String backOrderDateTo;

	private String locationNo;

	private String costFrom;

	private String costTo;

	private String specMemo;

	private String backOrderFlg;

	private String keepFlg;

	private String orderAlertFlg;

	private String overArrivalScheduleFlg;

	private String manualFlg;

	private String planSheetFlg;

	private String otherDocumentFlg;

	private String setItemFlg;

	private String sortFirst;

	private String sortFirstSub;

	private String listPageMax;

	/**ダウンロード区分*/
	private String downloadType;

	private String itemCodeType;

	private String searchMethod;

	private String deadStockFlg;

	private String displayContents;

	private String nameType;

	private String arrivalScheduleFlg;

	/** 注文アラートフラグ */
	private String deliveryAlertFlg;

	private String arrivalDateType;

	private String numberOrder;

	/** 在庫数 */
	private String stockNum;

	/** 会社/工場名 */
	private String companyFactoryNm;

	/** 注文プールフラグ */
	private String orderPoolFlg;

	/**注文数０検索*/
	private String orderNum0Flg;

	/** 工場品番 */
	private String factoryItemCode;

	/**入荷日FROM*/
	private String arrivalDateFrom;
	/**入荷日TO*/
	private String arrivalDateTo;



	public SearchItemDTO(String empty) {

		itemCodeFrom = empty;
		itemCodeTo = empty;
		itemNm = empty;
	}

	public SearchItemDTO() {
		itemCodeFrom = StringUtils.EMPTY;
		itemCodeTo = StringUtils.EMPTY;
		itemNm = StringUtils.EMPTY;
	}



	/**
	 * @return sysItemId
	 */
	public long getSysItemId() {
		return sysItemId;
	}

	/**
	 * @param sysItemId セットする sysItemId
	 */
	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
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

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
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


	public long getOpenerIdx() {
		return openerIdx;
	}

	public void setOpenerIdx(long openerIdx) {
		this.openerIdx = openerIdx;
	}

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * @return sysWarehouseId
	 */
	public long getSysWarehouseId() {
		return sysWarehouseId;
	}

	/**
	 * @param sysWarehouseId セットする sysWarehouseId
	 */
	public void setSysWarehouseId(long sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	/**
	 * @return itemOrderDateFrom
	 */
	public String getItemOrderDateFrom() {
		return itemOrderDateFrom;
	}

	/**
	 * @param itemOrderDateFrom セットする itemOrderDateFrom
	 */
	public void setItemOrderDateFrom(String itemOrderDateFrom) {
		this.itemOrderDateFrom = itemOrderDateFrom;
	}

	/**
	 * @return itemOrderDateTo
	 */
	public String getItemOrderDateTo() {
		return itemOrderDateTo;
	}

	/**
	 * @param itemOrderDateTo セットする itemOrderDateTo
	 */
	public void setItemOrderDateTo(String itemOrderDateTo) {
		this.itemOrderDateTo = itemOrderDateTo;
	}

	/**
	 * @return arrivalScheduleDateFrom
	 */
	public String getArrivalScheduleDateFrom() {
		return arrivalScheduleDateFrom;
	}

	/**
	 * @param arrivalScheduleDateFrom セットする arrivalScheduleDateFrom
	 */
	public void setArrivalScheduleDateFrom(String arrivalScheduleDateFrom) {
		this.arrivalScheduleDateFrom = arrivalScheduleDateFrom;
	}

	/**
	 * @return arrivalScheduleDateTo
	 */
	public String getArrivalScheduleDateTo() {
		return arrivalScheduleDateTo;
	}

	/**
	 * @param arrivalScheduleDateTo セットする arrivalScheduleDateTo
	 */
	public void setArrivalScheduleDateTo(String arrivalScheduleDateTo) {
		this.arrivalScheduleDateTo = arrivalScheduleDateTo;
	}

	/**
	 * @return backOrderDateFrom
	 */
	public String getBackOrderDateFrom() {
		return backOrderDateFrom;
	}

	/**
	 * @param backOrderDateFrom セットする backOrderDateFrom
	 */
	public void setBackOrderDateFrom(String backOrderDateFrom) {
		this.backOrderDateFrom = backOrderDateFrom;
	}

	/**
	 * @return backOrderDateTo
	 */
	public String getBackOrderDateTo() {
		return backOrderDateTo;
	}

	/**
	 * @param backOrderDateTo セットする backOrderDateTo
	 */
	public void setBackOrderDateTo(String backOrderDateTo) {
		this.backOrderDateTo = backOrderDateTo;
	}

	/**
	 * @return locationNo
	 */
	public String getLocationNo() {
		return locationNo;
	}

	/**
	 * @param locationNo セットする locationNo
	 */
	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}



	/**
	 * @return costFrom
	 */
	public String getCostFrom() {
		return costFrom;
	}

	/**
	 * @param costFrom セットする costFrom
	 */
	public void setCostFrom(String costFrom) {
		this.costFrom = costFrom;
	}

	/**
	 * @return costTo
	 */
	public String getCostTo() {
		return costTo;
	}

	/**
	 * @param costTo セットする costTo
	 */
	public void setCostTo(String costTo) {
		this.costTo = costTo;
	}

	/**
	 * @return specMemo
	 */
	public String getSpecMemo() {
		return specMemo;
	}

	/**
	 * @param specMemo セットする specMemo
	 */
	public void setSpecMemo(String specMemo) {
		this.specMemo = specMemo;
	}

	/**
	 * @return backOrderFlg
	 */
	public String getBackOrderFlg() {
		return backOrderFlg;
	}

	/**
	 * @param backOrderFlg セットする backOrderFlg
	 */
	public void setBackOrderFlg(String backOrderFlg) {
		this.backOrderFlg = backOrderFlg;
	}

	/**
	 * @return keepFlg
	 */
	public String getKeepFlg() {
		return keepFlg;
	}

	/**
	 * @param keepFlg セットする keepFlg
	 */
	public void setKeepFlg(String keepFlg) {
		this.keepFlg = keepFlg;
	}

	/**
	 * @return orderAlertFlg
	 */
	public String getOrderAlertFlg() {
		return orderAlertFlg;
	}

	/**
	 * @param orderAlertFlg セットする orderAlertFlg
	 */
	public void setOrderAlertFlg(String orderAlertFlg) {
		this.orderAlertFlg = orderAlertFlg;
	}

	/**
	 * @return overArrivalScheduleFlg
	 */
	public String getOverArrivalScheduleFlg() {
		return overArrivalScheduleFlg;
	}

	/**
	 * @param overArrivalScheduleFlg セットする overArrivalScheduleFlg
	 */
	public void setOverArrivalScheduleFlg(String overArrivalScheduleFlg) {
		this.overArrivalScheduleFlg = overArrivalScheduleFlg;
	}

	/**
	 * @return manualFlg
	 */
	public String getManualFlg() {
		return manualFlg;
	}

	/**
	 * @param manualFlg セットする manualFlg
	 */
	public void setManualFlg(String manualFlg) {
		this.manualFlg = manualFlg;
	}

	/**
	 * @return planSheetFlg
	 */
	public String getPlanSheetFlg() {
		return planSheetFlg;
	}

	/**
	 * @param planSheetFlg セットする planSheetFlg
	 */
	public void setPlanSheetFlg(String planSheetFlg) {
		this.planSheetFlg = planSheetFlg;
	}

	/**
	 * @return otherDocumentFlg
	 */
	public String getOtherDocumentFlg() {
		return otherDocumentFlg;
	}

	/**
	 * @param otherDocumentFlg セットする otherDocumentFlg
	 */
	public void setOtherDocumentFlg(String otherDocumentFlg) {
		this.otherDocumentFlg = otherDocumentFlg;
	}

	/**
	 * @return setItemFlg
	 */
	public String getSetItemFlg() {
		return setItemFlg;
	}

	/**
	 * @param setItemFlg セットする setItemFlg
	 */
	public void setSetItemFlg(String setItemFlg) {
		this.setItemFlg = setItemFlg;
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
	 * @return sortFirstSub
	 */
	public String getSortFirstSub() {
		return sortFirstSub;
	}

	/**
	 * @param sortFirstSub セットする sortFirstSub
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
	 * @param listPageMax セットする listPageMax
	 */
	public void setListPageMax(String listPageMax) {
		this.listPageMax = listPageMax;
	}

	/**
	 * ダウンロード区分を取得します。
	 * @return ダウンロード区分
	 */
	public String getDownloadType() {
	    return downloadType;
	}

	/**
	 * ダウンロード区分を設定します。
	 * @param downloadType ダウンロード区分
	 */
	public void setDownloadType(String downloadType) {
	    this.downloadType = downloadType;
	}

	/**
	 * @return itemCodeType
	 */
	public String getItemCodeType() {
		return itemCodeType;
	}

	/**
	 * @param itemCodeType セットする itemCodeType
	 */
	public void setItemCodeType(String itemCodeType) {
		this.itemCodeType = itemCodeType;
	}

	/**
	 * @return searchMethod
	 */
	public String getSearchMethod() {
		return searchMethod;
	}

	/**
	 * @param searchMethod セットする searchMethod
	 */
	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}

	/**
	 * @return deadStockFlg
	 */
	public String getDeadStockFlg() {
		return deadStockFlg;
	}

	/**
	 * @param deadStockFlg セットする deadStockFlg
	 */
	public void setDeadStockFlg(String deadStockFlg) {
		this.deadStockFlg = deadStockFlg;
	}

	/**
	 * @return displayContents
	 */
	public String getDisplayContents() {
		return displayContents;
	}

	/**
	 * @param displayContents
	 */
	public void setDisplayContents(String displayContents) {
		this.displayContents = displayContents;
	}

	/**
	 * @return nameType
	 */
	public String getNameType() {
		return nameType;
	}

	/**
	 * @param nameType セットする nameType
	 */
	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	/**
	 * @return arrivalScheduleFlg
	 */
	public String getArrivalScheduleFlg() {
		return arrivalScheduleFlg;
	}

	/**
	 * @param arrivalScheduleFlg セットする arrivalScheduleFlg
	 */
	public void setArrivalScheduleFlg(String arrivalScheduleFlg) {
		this.arrivalScheduleFlg = arrivalScheduleFlg;
	}

	/**
	 * 注文アラートフラグを取得します。
	 * @return 注文アラートフラグ
	 */
	public String getDeliveryAlertFlg() {
	    return deliveryAlertFlg;
	}

	/**
	 * 注文アラートフラグを設定します。
	 * @param deliveryAlertFlg 注文アラートフラグ
	 */
	public void setDeliveryAlertFlg(String deliveryAlertFlg) {
	    this.deliveryAlertFlg = deliveryAlertFlg;
	}

	/**
	 * @return arrivalDateType
	 */
	public String getArrivalDateType() {
		return arrivalDateType;
	}

	/**
	 * @param arrivalDateType セットする arrivalDateType
	 */
	public void setArrivalDateType(String arrivalDateType) {
		this.arrivalDateType = arrivalDateType;
	}

	/**
	 * @return numberOrder
	 */
	public String getNumberOrder() {
		return numberOrder;
	}

	/**
	 * @param numberOrder
	 */
	public void setNumberOrder(String numberOrder) {
		this.numberOrder = numberOrder;
	}

	/**
	 * @return stockNum
	 */
	public String getStockNum() {
		return stockNum;
	}

	/**
	 * @param stockNum セットする stockNum
	 */
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	/**
	 * @return companyFactoryNm
	 */
	public String getCompanyFactoryNm() {
		return companyFactoryNm;
	}

	/**
	 * @param companyFactoryNm セットする companyFactoryNm
	 */
	public void setCompanyFactoryNm(String companyFactoryNm) {
		this.companyFactoryNm = companyFactoryNm;
	}

	/**
	 * @return orderPoolFlg
	 */
	public String getOrderPoolFlg() {
		return orderPoolFlg;
	}

	/**
	 * @param orderPoolFlg
	 */
	public void setOrderPoolFlg(String orderPoolFlg) {
		this.orderPoolFlg = orderPoolFlg;
	}

	/**
	 * @return factoryItemCode
	 */
	public String getFactoryItemCode() {
		return factoryItemCode;
	}

	/**
	 * @param factoryItemCode セットする factoryItemCode
	 */
	public void setFactoryItemCode(String factoryItemCode) {
		this.factoryItemCode = factoryItemCode;
	}

	/**
	 * @return sysForeignSlipItemId
	 */
	public long getSysForeignSlipItemId() {
		return sysForeignSlipItemId;
	}

	/**
	 * @param sysForeignSlipItemId セットする sysForeignSlipItemId
	 */
	public void setSysForeignSlipItemId(long sysForeignSlipItemId) {
		this.sysForeignSlipItemId = sysForeignSlipItemId;
	}

	/**
	 * @return sysSupplierId
	 */
	public String getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * @param sysSupplierId セットする sysSupplierId
	 */
	public void setSysSupplierId(String sysSupplierId) {
		this.sysSupplierId = sysSupplierId;
	}

	/**
	 * 入荷日FROMを取得します。
	 * @return 入荷日FROM
	 */
	public String getArrivalDateFrom() {
	    return arrivalDateFrom;
	}

	/**
	 * 入荷日FROMを設定します。
	 * @param arrivalDateFrom 入荷日FROM
	 */
	public void setArrivalDateFrom(String arrivalDateFrom) {
	    this.arrivalDateFrom = arrivalDateFrom;
	}

	/**
	 * 入荷日TOを取得します。
	 * @return 入荷日TO
	 */
	public String getArrivalDateTo() {
	    return arrivalDateTo;
	}

	/**
	 * 入荷日TOを設定します。
	 * @param arrivalDateTo 入荷日TO
	 */
	public void setArrivalDateTo(String arrivalDateTo) {
	    this.arrivalDateTo = arrivalDateTo;
	}

	/**
	 * 列カウントを取得します。
	 * @return rowCount
	 */
	public long getRowCount() {
		return rowCount;
	}

	/**
	 * 列カウントを設定します。
	 * @param rowCount セットする rowCount
	 */
	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * 注文数０検索を取得します。
	 * @return orderNum0Flg
	 */
	public String getOrderNum0Flg() {
		return orderNum0Flg;
	}

	/**
	 * 注文数０検索を設定します。
	 * @param orderNum0Flg セットする orderNum0Flg
	 */
	public void setOrderNum0Flg(String orderNum0Flg) {
		this.orderNum0Flg = orderNum0Flg;
	}

}
