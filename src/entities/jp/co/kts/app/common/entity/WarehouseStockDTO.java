/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 倉庫在庫情報を格納します。
 *
 * @author admin
 */
public class WarehouseStockDTO  {

	/** システム倉庫在庫ID */
	private long sysWarehouseStockId = 0;

	/** システム商品ID */
	private long sysItemId = 0;

	/** システム倉庫ID */
	private long sysWarehouseId = 0;

	/** 在庫数 */
	private int stockNum = 0;

	/** ロケーションNO */
	private String locationNo;

	/** 優先度 */
	private String priority;

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

	/**
	 * <p>
	 * システム倉庫在庫ID を返却します。
	 * </p>
	 * @return sysWarehouseStockId
	 */
	public long getSysWarehouseStockId() {
		return this.sysWarehouseStockId;
	}

	/**
	 * <p>
	 * システム倉庫在庫ID を設定します。
	 * </p>
	 * @param sysWarehouseStockId
	 */
	public void setSysWarehouseStockId(long sysWarehouseStockId) {
		this.sysWarehouseStockId = sysWarehouseStockId;
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
	 * システム倉庫ID を返却します。
	 * </p>
	 * @return sysWarehouseId
	 */
	public long getSysWarehouseId() {
		return this.sysWarehouseId;
	}

	/**
	 * <p>
	 * システム倉庫ID を設定します。
	 * </p>
	 * @param sysWarehouseId
	 */
	public void setSysWarehouseId(long sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	/**
	 * <p>
	 * 在庫数 を返却します。
	 * </p>
	 * @return stockNum
	 */
	public int getStockNum() {
		return this.stockNum;
	}

	/**
	 * <p>
	 * 在庫数 を設定します。
	 * </p>
	 * @param stockNum
	 */
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

	/**
	 * <p>
	 * ロケーションNO を返却します。
	 * </p>
	 * @return locationNo
	 */
	public String getLocationNo() {
		return this.locationNo;
	}

	/**
	 * <p>
	 * ロケーションNO を設定します。
	 * </p>
	 * @param locationNo
	 */
	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	/**
	 * <p>
	 * 優先度 を返却します。
	 * </p>
	 * @return priority
	 */
	public String getPriority() {
		return this.priority;
	}

	/**
	 * <p>
	 * 優先度 を設定します。
	 * </p>
	 * @param priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
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

}

