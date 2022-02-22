/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 説明書情報を格納します。
 *
 * @author admin
 */
public class ItemManualDTO  {

	/** システム説明書ID */
	private long sysItemManualId;

	/** システム商品ID */
	private long sysItemId;

	/** 説明書パス */
	private String manualPath;

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

	/** 資料区分 */
	private String documentType;


	/**
	 * <p>
	 * システム説明書ID を返却します。
	 * </p>
	 * @return sysItemManualId
	 */
	public long getSysItemManualId() {
		return this.sysItemManualId;
	}

	/**
	 * <p>
	 * システム説明書ID を設定します。
	 * </p>
	 * @param sysItemManualId
	 */
	public void setSysItemManualId(long sysItemManualId) {
		this.sysItemManualId = sysItemManualId;
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
	 * 説明書パス を返却します。
	 * </p>
	 * @return manualPath
	 */
	public String getManualPath() {
		return this.manualPath;
	}

	/**
	 * <p>
	 * 説明書パス を設定します。
	 * </p>
	 * @param manualPath
	 */
	public void setManualPath(String manualPath) {
		this.manualPath = manualPath;
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
	 * <p>
	 * 資料区分 を返却します。
	 * </p>
	 * @return documentType
	 */
	public String getDocumentType() {
		return this.documentType;
	}

	/**
	 * <p>
	 * 資料区分 を設定します。
	 * </p>
	 * @param documentType
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

}

