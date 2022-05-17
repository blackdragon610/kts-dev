package jp.co.kts.app.extendCommon.entity;

import java.math.BigDecimal;

import jp.co.kts.app.common.entity.CorporateSalesItemDTO;

public class ExtendCorporateSalesItemDTO extends CorporateSalesItemDTO {

	/** 総在庫数 */
	private int totalStockNum;

	/** 仮在庫数 */
	private int temporaryStockNum;

	/** 組立可数 */
	private int assemblyNum;

	/** システム口座ID */
	private long sysAccountId;

	private long sysCorporationId;

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

	/** 受注者名 */
	private String orderName;

	/** 納品状況 */
	private String deliveryStatus;

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */

	/** 伝票番号 */
	private String saleSlipNo;

	/** 取引先法人名 */
	private String corporationNm;

	/** 法人掛け率 */
	private BigDecimal corporationRateOver;

	/** 在庫Kind原価 */
	private int itemKindCost;

	/** 出庫予定日 */
	private String shipmentPlanDate;

	/** システム法人間請求書商品ID */
	private long sysCorporateBillItemId;

	/** システム法人間請求書ID */
	private long sysCorporateBillId;

	/** 業販区分 */
	private String corporateSalesFlg;

	/** 得意先ID*/
	private long sysClientId;

	/** 得意先名*/
	private String clientNm;

	/** kind原価*/
	private int kindCost;

	// 税率
	private int taxRate;

	private int updatedFlag;

	private float salesProfitRate;
	private float purchaseProfitRate;
	public float getSalesProfitRate() {
		return salesProfitRate;
	}
	public void setSalesProfitRate(float salesProfitRate) {
		this.salesProfitRate = salesProfitRate;
	}
	public float getPurchaseProfitRate() {
		return purchaseProfitRate;
	}
	public void setPurchaseProfitRate(float purchaseProfitRate) {
		this.purchaseProfitRate = purchaseProfitRate;
	}
	
	public String getShipmentPlanDate() {
		return shipmentPlanDate;
	}

	public void setShipmentPlanDate(String shipmentPlanDate) {
		this.shipmentPlanDate = shipmentPlanDate;
	}

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
	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

	public ExtendCorporateSalesItemDTO () {

	}

	public ExtendCorporateSalesItemDTO (String empty) {

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

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	/**
	 * @return deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	/**
	 * @param deliveryStatus セットする deliveryStatus
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public int getAssemblyNum() {
		return assemblyNum;
	}

	public void setAssemblyNum(int assemblyNum) {
		this.assemblyNum = assemblyNum;
	}

	/**
	 * システム口座IDを取得します
	 * @return
	 */
	public long getSysAccountId() {
		return sysAccountId;
	}

	/**
	 * システム口座IDを設定します
	 * @param sysAccountId
	 */
	public void setSysAccountId(long sysAccountId) {
		this.sysAccountId = sysAccountId;
	}


	/**
	 * <p>
	 * システム法人間請求書商品ID を返却します。
	 * </p>
	 * @return sysCorporateBillItemId
	 */
	public long getSysCorporateBillItemId() {
		return this.sysCorporateBillItemId;
	}

	/**
	 * <p>
	 * システム法人間請求書商品ID を設定します。
	 * </p>
	 * @param sysCorporateBillItemId
	 */
	public void setSysCorporateBillItemId(long sysCorporateBillItemId) {
		this.sysCorporateBillItemId = sysCorporateBillItemId;
	}

	/**
	 * <p>
	 * システム法人間請求書ID を返却します。
	 * </p>
	 * @return sysCorporateBillId
	 */
	public long getSysCorporateBillId() {
		return this.sysCorporateBillId;
	}

	/**
	 * <p>
	 * システム法人間請求書ID を設定します。
	 * </p>
	 * @param sysCorporateBillId
	 */
	public void setSysCorporateBillId(long sysCorporateBillId) {
		this.sysCorporateBillId = sysCorporateBillId;
	}
	/**
	 * <p>
	 * 業販区分 を返却します。
	 * </p>
	 * @return corporateSalesFlg
	 */
	public String getCorporateSalesFlg() {
		return this.corporateSalesFlg;
	}

	/**
	 * <p>
	 * 業販区分 を設定します。
	 * </p>
	 * @param corporateSalesFlg
	 */
	public void setCorporateSalesFlg(String corporateSalesFlg) {
		this.corporateSalesFlg = corporateSalesFlg;
	}

	/**
	 * <p>
	 * 得意先ID を返却します。
	 * </p>
	 * @return sysClientId
	 */
	public long getSysClientId() {
		return this.sysClientId;
	}

	/**
	 * <p>
	 * 得意先ID を設定します。
	 * </p>
	 * @param sysClientId
	 */
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}

	/**
	 * <p>
	 * 得意先名 を返却します。
	 * </p>
	 * @return clientNm
	 */
	public String getClientNm() {
		return clientNm;
	}

	/**
	 * <p>
	 * 得意先名 を設定します。
	 * </p>
	 * @param clientNm
	 */
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}

	/**
	 * kind原価
	 * @return kindCost
	 */
	public int getKindCost() {
		return kindCost;
	}

	/**
	 * kind原価
	 * @param kindCost セットする kindCost
	 */
	public void setKindCost(int kindCost) {
		this.kindCost = kindCost;
	}

	public int getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

	public int getUpdatedFlag() {
		return updatedFlag;
	}

	public void setUpdatedFlag(int updatedFlag) {
		this.updatedFlag = updatedFlag;
	}


}