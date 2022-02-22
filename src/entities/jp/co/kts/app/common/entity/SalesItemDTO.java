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
public class SalesItemDTO  {

	/** システム売上商品ID */
	private long sysSalesItemId;

	/** システム売上伝票ID */
	private long sysSalesSlipId;

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

	private int updatedFlag;

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

	/** 楽天倉庫出荷フラグ */
	private String rslLeaveFlag;


	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

	/**
	 * <p>
	 * システム売上商品ID を返却します。
	 * </p>
	 * @return sysSalesItemId
	 */
	public long getSysSalesItemId() {
		return this.sysSalesItemId;
	}

	/**
	 * <p>
	 * システム売上商品ID を設定します。
	 * </p>
	 * @param sysSalesItemId
	 */
	public void setSysSalesItemId(long sysSalesItemId) {
		this.sysSalesItemId = sysSalesItemId;
	}

	/**
	 * <p>
	 * システム売上伝票ID を返却します。
	 * </p>
	 * @return sysSalesSlipId
	 */
	public long getSysSalesSlipId() {
		return this.sysSalesSlipId;
	}

	/**
	 * <p>
	 * システム売上伝票ID を設定します。
	 * </p>
	 * @param sysSalesSlipId
	 */
	public void setSysSalesSlipId(long sysSalesSlipId) {
		this.sysSalesSlipId = sysSalesSlipId;
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

	/*  2015/12/15 ooyama ADD START 法人間請求書機能対応  */

	/**
	 * <p>
	 * KIND原価 を返却します。
	 * </p>
	 * @return updateUserId
	 */
	public int getKindCost() {
		return kindCost;
	}

	/**
	 * <p>
	 * KIND原価 を設定します。
	 * </p>
	 * @param updateUserId
	 */
	public void setKindCost(int kindCost) {
		this.kindCost = kindCost;
	}

	/**
	 * <p>
	 * 定価 を返却します。
	 * </p>
	 * @return updateUserId
	 */
	public int getListPrice() {
		return listPrice;
	}

	/**
	 * <p>
	 * 定価 を設定します。
	 * </p>
	 * @param updateUserId
	 */
	public void setListPrice(int listPrice) {
		this.listPrice = listPrice;
	}

	/**
	 * <p>
	 * 商品掛け率 を返却します。
	 * </p>
	 * @return updateUserId
	 */
	public BigDecimal getItemRateOver() {
		return itemRateOver;
	}

	/**
	 * <p>
	 * 商品掛け率 を設定します。
	 * </p>
	 * @param updateUserId
	 */
	public void setItemRateOver(BigDecimal itemRateOver) {
		this.itemRateOver = itemRateOver;
	}

	public String getCostCheckFlag() {
		return costCheckFlag;
	}

	public void setCostCheckFlag(String costCheckFlag) {
		this.costCheckFlag = costCheckFlag;
	}

	/*  2015/12/15 ooyama ADD END 法人間請求書機能対応  */

	/**
	 * <p>
	 * RSL（楽天倉庫）出荷フラグを返却します。
	 * </p>
	 * @return rslLeaveFlag
	 */
	public String getRslLeaveFlag() {
		return rslLeaveFlag;
	}

	/**
	 * <p>
	 * RSL（楽天倉庫）出荷フラグを設定します。
	 * </p>
	 * @param rslLeaveCheckFlag
	 */
	public void setRslLeaveFlag(String rslLeaveFlag) {
		this.rslLeaveFlag = rslLeaveFlag;
	}

	public int getUpdatedFlag() {
		return updatedFlag;
	}

	public void setUpdatedFlag(int updatedFlag) {
		this.updatedFlag = updatedFlag;
	}
}

