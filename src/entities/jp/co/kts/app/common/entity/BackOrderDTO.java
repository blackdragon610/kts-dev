/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * バックオーダー情報を格納します。
 *
 * @author admin
 */
public class BackOrderDTO  {

	/** システムバックオーダーID */
	private long sysBackOrderId;

	/** システム商品ID */
	private long sysItemId;

	/** システム法人ID */
	private long sysCorporationId;

	/** システム販売チャネルID */
	private long sysChannelId;

	/** バックオーダー日 */
	private String backOrderDate;

	/** 個数 */
	private int itemNum;

	/** 備考 */
	private String remarks;

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
	 * システムバックオーダーID を返却します。
	 * </p>
	 * @return sysBackOrderId
	 */
	public long getSysBackOrderId() {
		return this.sysBackOrderId;
	}

	/**
	 * <p>
	 * システムバックオーダーID を設定します。
	 * </p>
	 * @param sysBackOrderId
	 */
	public void setSysBackOrderId(long sysBackOrderId) {
		this.sysBackOrderId = sysBackOrderId;
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
	 * システム法人ID を返却します。
	 * </p>
	 * @return sysCorporationId
	 */
	public long getSysCorporationId() {
		return this.sysCorporationId;
	}

	/**
	 * <p>
	 * システム法人ID を設定します。
	 * </p>
	 * @param sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * <p>
	 * システム販売チャネルID を返却します。
	 * </p>
	 * @return sysChannelId
	 */
	public long getSysChannelId() {
		return this.sysChannelId;
	}

	/**
	 * <p>
	 * システム販売チャネルID を設定します。
	 * </p>
	 * @param sysChannelId
	 */
	public void setSysChannelId(long sysChannelId) {
		this.sysChannelId = sysChannelId;
	}

	/**
	 * <p>
	 * バックオーダー日 を返却します。
	 * </p>
	 * @return backOrderDate
	 */
	public String getBackOrderDate() {
		return this.backOrderDate;
	}

	/**
	 * <p>
	 * バックオーダー日 を設定します。
	 * </p>
	 * @param backOrderDate
	 */
	public void setBackOrderDate(String backOrderDate) {
		this.backOrderDate = backOrderDate;
	}

	/**
	 * <p>
	 * 個数 を返却します。
	 * </p>
	 * @return itemNum
	 */
	public int getItemNum() {
		return this.itemNum;
	}

	/**
	 * <p>
	 * 個数 を設定します。
	 * </p>
	 * @param itemNum
	 */
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	/**
	 * <p>
	 * 備考 を返却します。
	 * </p>
	 * @return remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * <p>
	 * 備考 を設定します。
	 * </p>
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

