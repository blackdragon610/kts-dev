/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_店舗情報を格納します。
 * 
 * @author admin
 */
public class MstStoreDTO  {

	/** システム店舗ID */
	private long sysStoreId;

	/** 店舗名 */
	private String storeNm;

	/** システム法人ID */
	private long sysCorporationId;

	/** システム販売チャネルID */
	private long sysChannelId;

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
	 * システム店舗ID を返却します。
	 * </p>
	 * @return sysStoreId
	 */
	public long getSysStoreId() {
		return this.sysStoreId;
	}

	/**
	 * <p>
	 * システム店舗ID を設定します。
	 * </p>
	 * @param sysStoreId
	 */
	public void setSysStoreId(long sysStoreId) {
		this.sysStoreId = sysStoreId;
	}

	/**
	 * <p>
	 * 店舗名 を返却します。
	 * </p>
	 * @return storeNm
	 */
	public String getStoreNm() {
		return this.storeNm;
	}

	/**
	 * <p>
	 * 店舗名 を設定します。
	 * </p>
	 * @param storeNm
	 */
	public void setStoreNm(String storeNm) {
		this.storeNm = storeNm;
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

