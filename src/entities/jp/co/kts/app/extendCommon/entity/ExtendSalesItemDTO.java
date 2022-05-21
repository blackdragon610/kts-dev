package jp.co.kts.app.extendCommon.entity;

import java.math.BigDecimal;

import jp.co.kts.app.common.entity.SalesItemDTO;

public class ExtendSalesItemDTO extends SalesItemDTO {

	/** 総在庫数 */
	private int totalStockNum;

	/** 仮在庫数 */
	private int temporaryStockNum;

	private long sysCorporationId;

	private String shipmentPlanDate;

	private String orderNo;

	private String warehouseNm;

	private String returnFlg;

	/** ロケーションNO */
	private String locationNo;

	/** 出庫数 */
	private int leaveNum;

	/** 第一倉庫の在庫数 */
	private int firstWarehouseStockNum;

	/** 出庫分類フラグ */
	private String leaveClassFlg;

	private String orderDate;

	private String orderTime;

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */

	/** 伝票番号 */
	private String saleSlipNo;

	/** 取引先法人名 */
	private String corporationNm;

	/** 法人掛け率 */
	private BigDecimal corporationRateOver;

	/** 在庫Kind原価 */
	private int itemKindCost;

	/** 出荷日 */
	private String shipmentDate;

	/** 税率 */
	private int taxRate;
	
	private int wholsesalerId;
	
	private String wholsesalerNm;
	
	private String purchasingCost;
	
	private int domePostage;
	
	private int profit;
	
	private int storeFlag;
	
	private int postage;
	
	private float purchaseProfitRate;
	
	private float salesProfitRate;

	
	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */


	public String getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public BigDecimal getCorporationRateOver() {
		return corporationRateOver;
	}

	public void setCorporationRateOver(BigDecimal corporationRateOver) {
		this.corporationRateOver = corporationRateOver;
	}

	public int getItemKindCost() {
		return itemKindCost;
	}

	public void setItemKindCost(int itemKindCost) {
		this.itemKindCost = itemKindCost;
	}

	public ExtendSalesItemDTO (String empty) {

		setItemCode(empty);
		setItemNm(empty);
	}

	public int getTotalStockNum() {
		return totalStockNum;
	}

	public void setTotalStockNum(int totalStockNum) {
		this.totalStockNum = totalStockNum;
	}

	public int getTemporaryStockNum() {
		return temporaryStockNum;
	}

	public void setTemporaryStockNum(int temporaryStockNum) {
		this.temporaryStockNum = temporaryStockNum;
	}

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public String getShipmentPlanDate() {
		return shipmentPlanDate;
	}

	public void setShipmentPlanDate(String shipmentPlanDate) {
		this.shipmentPlanDate = shipmentPlanDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getWarehouseNm() {
		return warehouseNm;
	}

	public void setWarehouseNm(String warehouseNm) {
		this.warehouseNm = warehouseNm;
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

	public String getReturnFlg() {
		return returnFlg;
	}

	public void setReturnFlg(String returnFlg) {
		this.returnFlg = returnFlg;
	}

	public int getLeaveNum() {
		return leaveNum;
	}

	public void setLeaveNum(int leaveNum) {
		this.leaveNum = leaveNum;
	}

	public int getFirstWarehouseStockNum() {
		return firstWarehouseStockNum;
	}

	public void setFirstWarehouseStockNum(int firstWarehouseStockNum) {
		this.firstWarehouseStockNum = firstWarehouseStockNum;
	}

	/**
	 * @return leaveClassFlg
	 */
	public String getLeaveClassFlg() {
		return leaveClassFlg;
	}

	/**
	 * @param leaveClassFlg セットする leaveClassFlg
	 */
	public void setLeaveClassFlg(String leaveClassFlg) {
		this.leaveClassFlg = leaveClassFlg;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */

	public String getSaleSlipNo() {
		return saleSlipNo;
	}

	public void setSaleSlipNo(String saleSlipNo) {
		this.saleSlipNo = saleSlipNo;
	}

	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

	public ExtendSalesItemDTO () {

	}

	public int getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

	public int getWholsesalerId() {
		return wholsesalerId;
	}

	public void setWholsesalerId(int wholsesalerId) {
		this.wholsesalerId = wholsesalerId;
	}

	public String getWholsesalerNm() {
		return wholsesalerNm;
	}

	public void setWholsesalerNm(String wholsesalerNm) {
		this.wholsesalerNm = wholsesalerNm;
	}

	public String getPurchasingCost() {
		return purchasingCost;
	}

	public void setPurchasingCost(String purchasingCost) {
		this.purchasingCost = purchasingCost;
	}

	public int getDomePostage() {
		return domePostage;
	}

	public void setDomePostage(int domePostage) {
		this.domePostage = domePostage;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getStoreFlag() {
		return storeFlag;
	}

	public void setStoreFlag(int storeFlag) {
		this.storeFlag = storeFlag;
	}

	public int getPostage() {
		return postage;
	}

	public void setPostage(int postage) {
		this.postage = postage;
	}

	/**
	 * @return the purchaseProfitRate
	 */
	public float getPurchaseProfitRate() {
		return purchaseProfitRate;
	}

	/**
	 * @param purchaseProfitRate the purchaseProfitRate to set
	 */
	public void setPurchaseProfitRate(float purchaseProfitRate) {
		this.purchaseProfitRate = purchaseProfitRate;
	}

	/**
	 * @return the salesProfitRate
	 */
	public float getSalesProfitRate() {
		return salesProfitRate;
	}

	/**
	 * @param salesProfitRate the salesProfitRate to set
	 */
	public void setSalesProfitRate(float salesProfitRate) {
		this.salesProfitRate = salesProfitRate;
	}
}