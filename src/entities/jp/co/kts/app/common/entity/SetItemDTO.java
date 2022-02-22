/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * セット商品情報を格納します。
 * 
 * @author admin
 */
public class SetItemDTO  {

	/** システムセット商品ID */
	private long sysSetItemId;

	/** システム商品ID */
	private long sysItemId;

	/** 構成システム商品ID */
	private long formSysItemId;

	/** 個数 */
	private int itemNum;

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
	 * システムセット商品ID を返却します。
	 * </p>
	 * @return sysSetItemId
	 */
	public long getSysSetItemId() {
		return this.sysSetItemId;
	}

	/**
	 * <p>
	 * システムセット商品ID を設定します。
	 * </p>
	 * @param sysSetItemId
	 */
	public void setSysSetItemId(long sysSetItemId) {
		this.sysSetItemId = sysSetItemId;
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
	 * 構成システム商品ID を返却します。
	 * </p>
	 * @return formSysItemId
	 */
	public long getFormSysItemId() {
		return this.formSysItemId;
	}

	/**
	 * <p>
	 * 構成システム商品ID を設定します。
	 * </p>
	 * @param formSysItemId
	 */
	public void setFormSysItemId(long formSysItemId) {
		this.formSysItemId = formSysItemId;
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

