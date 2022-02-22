/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 売上商品情報を格納します。
 *
 * @author admin
 */
public class CorporateSalesItemDTO  {

	/** システム業販売上商品ID */
	private long sysCorporateSalesItemId;

	/** システム業販売上伝票ID */
	private long sysCorporateSalesSlipId;

	/** システム商品ID */
	private long sysItemId;

	/** 品番 */
	private String itemCode;

	/** 商品名 */
	private String itemNm;

	/** 注文数 */
	private int orderNum;

	/** 単価 */
	private int pieceRate;

	/** 原価 */
	private int cost;

	private int domeCost;

	/** 出予定庫日 */
	private String scheduledLeavingDate;

	/** 出庫日 */
	private String leavingDate;

	/** 出庫数 */
	private int leavingNum;

	/** 売上日 */
	private String salesDate;

	/** ピッキングリスト出力フラグ */
	private String pickingListFlg;

	/** 削除フラグ */
	private String deleteFlag;

	/** 登録日 */
	private Date createDate;

	/** 登録者ID */
	private int createUserId;

	/** 更新日 */
	private Date updateDate;

	/** 更新者ID */
	private int updateUserId;

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */

	/** KIND原価 */
	private int kindCost;

	/** 定価 */
	private int listPrice;

	/** 商品掛け率 */
	private BigDecimal itemRateOver;

	/** 原価チェックフラグ */
	private String costCheckFlag;

	private int wholsesalerId;
	
	private String wholsesalerNm;
	
	private String purchasingCost;
	
	private int domePostage;
	
	private int profit;
	
	private int postage;

	private int storeFlag;
	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

	/**
	 * <p>
	 * システム業販売上商品ID を返却します。
	 * </p>
	 * @return sysCorporateSalesItemId
	 */
	public long getSysCorporateSalesItemId() {
		return this.sysCorporateSalesItemId;
	}

	/**
	 * <p>
	 * システム業販売上商品ID を設定します。
	 * </p>
	 * @param sysCorporateSalesItemId
	 */
	public void setSysCorporateSalesItemId(long sysCorporateSalesItemId) {
		this.sysCorporateSalesItemId = sysCorporateSalesItemId;
	}

	/**
	 * <p>
	 * システム業販売上伝票ID を返却します。
	 * </p>
	 * @return sysCorporateSalesSlipId
	 */
	public long getSysCorporateSalesSlipId() {
		return this.sysCorporateSalesSlipId;
	}

	/**
	 * <p>
	 * システム業販売上伝票ID を設定します。
	 * </p>
	 * @param sysCorporateSalesSlipId
	 */
	public void setSysCorporateSalesSlipId(long sysCorporateSalesSlipId) {
		this.sysCorporateSalesSlipId = sysCorporateSalesSlipId;
	}

	/**
	 * <p>
	 * システム商品ID を返却します。
	 * </p>
	 * @return sysItemId
	 */
	public long getSysItemId() {
		return this.sysItemId;
	}

	/**
	 * <p>
	 * システム商品ID を設定します。
	 * </p>
	 * @param sysItemId
	 */
	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	/**
	 * <p>
	 * 品番 を返却します。
	 * </p>
	 * @return itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * <p>
	 * 品番 を設定します。
	 * </p>
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * <p>
	 * 商品名 を返却します。
	 * </p>
	 * @return itemNm
	 */
	public String getItemNm() {
		return this.itemNm;
	}

	/**
	 * <p>
	 * 商品名 を設定します。
	 * </p>
	 * @param itemNm
	 */
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	/**
	 * <p>
	 * 注文数 を返却します。
	 * </p>
	 * @return orderNum
	 */
	public int getOrderNum() {
		return this.orderNum;
	}

	/**
	 * <p>
	 * 注文数 を設定します。
	 * </p>
	 * @param orderNum
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * <p>
	 * 単価 を返却します。
	 * </p>
	 * @return pieceRate
	 */
	public int getPieceRate() {
		return this.pieceRate;
	}

	/**
	 * <p>
	 * 単価 を設定します。
	 * </p>
	 * @param pieceRate
	 */
	public void setPieceRate(int pieceRate) {
		this.pieceRate = pieceRate;
	}

	/**
	 * <p>
	 * 原価 を返却します。
	 * </p>
	 * @return cost
	 */
	public int getCost() {
		return this.cost;
	}

	public int getDomeCost() {
		return this.domeCost;
	}

	/**
	 * <p>
	 * 原価 を設定します。
	 * </p>
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setDomeCost(int domeCost) {
		this.domeCost = domeCost;
	}

	/**
	 * <p>
	 * 出予定庫日 を返却します。
	 * </p>
	 * @return scheduledLeavingDate
	 */
	public String getScheduledLeavingDate() {
		return this.scheduledLeavingDate;
	}

	/**
	 * <p>
	 * 出予定庫日 を設定します。
	 * </p>
	 * @param scheduledLeavingDate
	 */
	public void setScheduledLeavingDate(String scheduledLeavingDate) {
		this.scheduledLeavingDate = scheduledLeavingDate;
	}

	/**
	 * <p>
	 * 出庫日 を返却します。
	 * </p>
	 * @return leavingDate
	 */
	public String getLeavingDate() {
		return this.leavingDate;
	}

	/**
	 * <p>
	 * 出庫日 を設定します。
	 * </p>
	 * @param leavingDate
	 */
	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}

	/**
	 * <p>
	 * 出庫数 を返却します。
	 * </p>
	 * @return leavingNum
	 */
	public int getLeavingNum() {
		return this.leavingNum;
	}

	/**
	 * <p>
	 * 出庫数 を設定します。
	 * </p>
	 * @param leavingNum
	 */
	public void setLeavingNum(int leavingNum) {
		this.leavingNum = leavingNum;
	}

	/**
	 * <p>
	 * 売上日 を返却します。
	 * </p>
	 * @return salesDate
	 */
	public String getSalesDate() {
		return this.salesDate;
	}

	/**
	 * <p>
	 * 売上日 を設定します。
	 * </p>
	 * @param salesDate
	 */
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	/**
	 * <p>
	 * 削除フラグ を返却します。
	 * </p>
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	/**
	 * <p>
	 * 削除フラグ を設定します。
	 * </p>
	 * @param deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * <p>
	 * 登録日 を返却します。
	 * </p>
	 * @return createDate
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * <p>
	 * 登録日 を設定します。
	 * </p>
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * <p>
	 * 登録者ID を返却します。
	 * </p>
	 * @return createUserId
	 */
	public int getCreateUserId() {
		return this.createUserId;
	}

	/**
	 * <p>
	 * 登録者ID を設定します。
	 * </p>
	 * @param createUserId
	 */
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * <p>
	 * 更新日 を返却します。
	 * </p>
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * <p>
	 * 更新日 を設定します。
	 * </p>
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * <p>
	 * 更新者ID を返却します。
	 * </p>
	 * @return updateUserId
	 */
	public int getUpdateUserId() {
		return this.updateUserId;
	}

	/**
	 * <p>
	 * 更新者ID を設定します。
	 * </p>
	 * @param updateUserId
	 */
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
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

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */

	public int getKindCost() {
		return kindCost;
	}

	public void setKindCost(int kindCost) {
		this.kindCost = kindCost;
	}

	public int getListPrice() {
		return listPrice;
	}

	public void setListPrice(int listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getItemRateOver() {
		return itemRateOver;
	}

	public void setItemRateOver(BigDecimal itemRateOver) {
		this.itemRateOver = itemRateOver;
	}


	public String getCostCheckFlag() {
		return costCheckFlag;
	}

	public void setCostCheckFlag(String costCheckFlag) {
		this.costCheckFlag = costCheckFlag;
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

	public int getPostage() {
		return postage;
	}

	public void setPostage(int postage) {
		this.postage = postage;
	}

	public int getStoreFlag() {
		return storeFlag;
	}

	public void setStoreFlag(int storeFlag) {
		this.storeFlag = storeFlag;
	}

	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

}

