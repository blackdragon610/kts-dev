/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * キープ情報を格納します。
 *
 * @author admin
 */
public class KeepDTO  {

	/** システムキープID */
	private long sysKeepId;

	/** システム商品ID */
	private long sysItemId;

	/** 受注番号 */
	private String orderNo;

	/** 個数 */
	private int keepNum;

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
	 * システムキープID を返却します。
	 * </p>
	 * @return sysKeepId
	 */
	public long getSysKeepId() {
		return this.sysKeepId;
	}

	/**
	 * <p>
	 * システムキープID を設定します。
	 * </p>
	 * @param sysKeepId
	 */
	public void setSysKeepId(long sysKeepId) {
		this.sysKeepId = sysKeepId;
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
	 * 受注番号 を返却します。
	 * </p>
	 * @return orderNo
	 */
	public String getOrderNo() {
		return this.orderNo;
	}

	/**
	 * <p>
	 * 受注番号 を設定します。
	 * </p>
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * <p>
	 * 個数 を返却します。
	 * </p>
	 * @return keepNum
	 */
	public int getKeepNum() {
		return this.keepNum;
	}

	/**
	 * <p>
	 * 個数 を設定します。
	 * </p>
	 * @param keepNum
	 */
	public void setKeepNum(int keepNum) {
		this.keepNum = keepNum;
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

