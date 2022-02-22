/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_倉庫情報を格納します。
 *
 * @author admin
 */
public class MstWarehouseDTO  {

	/** システム倉庫ID */
	private long sysWarehouseId;

	/** 倉庫名 */
	private String warehouseNm;

	/** 所在地 */
	private String address;

	/** 住所1 */
	private String addressFst;

	/** 住所2 */
	private String addressNxt;

	/** 電話番号 */
	private String tellNo;

	/** 氏名 */
	private String logisticNm;

	/** 郵便番号 */
	private String zip;

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
	 * 倉庫名 を返却します。
	 * </p>
	 * @return warehouseNm
	 */
	public String getWarehouseNm() {
		return this.warehouseNm;
	}

	/**
	 * <p>
	 * 倉庫名 を設定します。
	 * </p>
	 * @param warehouseNm
	 */
	public void setWarehouseNm(String warehouseNm) {
		this.warehouseNm = warehouseNm;
	}

	/**
	 * <p>
	 * 所在地 を返却します。
	 * </p>
	 * @return address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * <p>
	 * 所在地 を設定します。
	 * </p>
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 *
	 * 住所1を返却します。
	 *
	 * @return addressFst
	 */
	public String getAddressFst() {
		return addressFst;
	}

	/**
	 *
	 * 住所1を設定します
	 *
	 * @param addressFst
	 */
	public void setAddressFst(String addressFst) {
		this.addressFst = addressFst;
	}

	/**
	 *
	 * 住所2を返却します。
	 *
	 * @return addressNxt
	 */
	public String getAddressNxt() {
		return addressNxt;
	}

	/**
	 *
	 * 住所2を設定します。
	 *
	 * @param addressNxt
	 */
	public void setAddressNxt(String addressNxt) {
		this.addressNxt = addressNxt;
	}

	/**
	 *
	 * 電話番号を返却します。
	 *
	 * @return tellNo
	 */
	public String getTellNo() {
		return tellNo;
	}

	/**
	 *
	 * 電話番号を設定します。
	 *
	 * @param tellNo
	 */
	public void setTellNo(String tellNo) {
		this.tellNo = tellNo;
	}

	/**
	 *
	 * 氏名を返却します。
	 *
	 * @return logisticNm
	 */
	public String getLogisticNm() {
		return logisticNm;
	}

	/**
	 *
	 * 氏名を設定します。
	 *
	 * @param logisticNm
	 */
	public void setLogisticNm(String logisticNm) {
		this.logisticNm = logisticNm;
	}

	/**
	 *
	 *郵便番号を返却します。
	 *
	 * @return zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 *
	 * 郵便番号を設定します。
	 *
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

}

