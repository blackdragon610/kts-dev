/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 業販入金管理情報を格納します。
 *
 * @author admin
 */
public class CorporateReceiveDTO  {

	/** システム入金ID */
	private long sysCorporateReceiveId;

	/** システム業販伝票ID */
	private long sysCorporateSalesSlipId;

	/** システム得意先ID */
	private long sysClientId;

	/** 入金額 */
	private int receivePrice;

	/** 入金日 */
	private String receiveDate;

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
	 * システム入金ID を返却します。
	 * </p>
	 * @return sysCorporateReceiveId
	 */
	public long getSysCorporateReceiveId() {
		return this.sysCorporateReceiveId;
	}

	/**
	 * <p>
	 * システム入金ID を設定します。
	 * </p>
	 * @param sysCorporateReceiveId
	 */
	public void setSysCorporateReceiveId(long sysCorporateReceiveId) {
		this.sysCorporateReceiveId = sysCorporateReceiveId;
	}

	/**
	 * <p>
	 * システム業販伝票ID を返却します。
	 * </p>
	 * @return sysCorporateSalesSlipId
	 */
	public long getSysCorporateSalesSlipId() {
		return this.sysCorporateSalesSlipId;
	}

	/**
	 * <p>
	 * システム業販伝票ID を設定します。
	 * </p>
	 * @param sysCorporateSalesSlipId
	 */
	public void setSysCorporateSalesSlipId(long sysCorporateSalesSlipId) {
		this.sysCorporateSalesSlipId = sysCorporateSalesSlipId;
	}

	/**
	 * <p>
	 * 入金額 を返却します。
	 * </p>
	 * @return receivePrice
	 */
	public int getReceivePrice() {
		return this.receivePrice;
	}

	/**
	 * <p>
	 * 入金額 を設定します。
	 * </p>
	 * @param receivePrice
	 */
	public void setReceivePrice(int receivePrice) {
		this.receivePrice = receivePrice;
	}

	/**
	 * <p>
	 * 入金日 を返却します。
	 * </p>
	 * @return receiveDate
	 */
	public String getReceiveDate() {
		return this.receiveDate;
	}

	/**
	 * <p>
	 * 入金日 を設定します。
	 * </p>
	 * @param receiveDate
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
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
	 * @return sysClientId
	 */
	public long getSysClientId() {
		return sysClientId;
	}

	/**
	 * @param sysClientId セットする sysClientId
	 */
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}

}

