/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 品番照合情報を格納します。
 *
 * @author admin
 */
public class CodeCollationDTO  {

	/** システム品番照合ID */
	private long sysCodeCollationId;

	/** システム商品ID */
	private long sysItemId;

	/** 品番 */
	private String itemCode;

	/** 旧品番 */
	private String oldItemCode;

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
	 * システム品番照合ID を返却します。
	 * </p>
	 * @return sysCodeCollationId
	 */
	public long getSysCodeCollationId() {
		return this.sysCodeCollationId;
	}

	/**
	 * <p>
	 * システム品番照合ID を設定します。
	 * </p>
	 * @param sysCodeCollationId
	 */
	public void setSysCodeCollationId(long sysCodeCollationId) {
		this.sysCodeCollationId = sysCodeCollationId;
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
	 * 旧品番 を返却します。
	 * </p>
	 * @return oldItemCode
	 */
	public String getOldItemCode() {
		return this.oldItemCode;
	}

	/**
	 * <p>
	 * 旧品番 を設定します。
	 * </p>
	 * @param oldItemCode
	 */
	public void setOldItemCode(String oldItemCode) {
		this.oldItemCode = oldItemCode;
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

