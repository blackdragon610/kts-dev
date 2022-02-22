/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_納入先情報を格納します。
 *
 * @author admin
 */
public class MstDeliveryDTO  {

	/** システム納入先ID */
	private long sysDeliveryId;

	/** システム得意先ID */
	private long sysClientId;

	/** システム法人ID */
	private long sysCorporationId;

	/** 納入先名 */
	private String deliveryNm;

	/** 納入先名カナ */
	private String deliveryNmKana;

	/** 郵便番号 */
	private String zip;

	/** 住所（都道府県） */
	private String prefectures;

	/** 住所（市区町村） */
	private String municipality;

	/** 住所（市区町村以降） */
	private String address;

	/** 住所（建物名等） */
	private String buildingNm;

	/** 部署名 */
	private String quarter;

	/** 役職名 */
	private String position;

	/** 御担当者名 */
	private String contactPersonNm;

	/** 電話番号 */
	private String tel;

	/** FAX番号 */
	private String fax;

	/** 登録日 */
	private Date createDate;

	/** 登録者ID */
	private int createUserId;

	/** 削除フラグ */
	private String deleteFlag;

	/** 更新日 */
	private Date updateDate;

	/** 更新者ID */
	private int updateUserId;

	/**
	 * <p>
	 * システム納入先ID を返却します。
	 * </p>
	 * @return sysDeliveryId
	 */
	public long getSysDeliveryId() {
		return this.sysDeliveryId;
	}

	/**
	 * <p>
	 * システム納入先ID を設定します。
	 * </p>
	 * @param sysDeliveryId
	 */
	public void setSysDeliveryId(long sysDeliveryId) {
		this.sysDeliveryId = sysDeliveryId;
	}

	/**
	 * <p>
	 * システム得意先ID を返却します。
	 * </p>
	 * @return sysClientId
	 */
	public long getSysClientId() {
		return this.sysClientId;
	}

	/**
	 * <p>
	 * システム得意先ID を設定します。
	 * </p>
	 * @param sysClientId
	 */
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}

	/**
	 * <p>
	 * 納入先名 を返却します。
	 * </p>
	 * @return deliveryNm
	 */
	public String getDeliveryNm() {
		return this.deliveryNm;
	}

	/**
	 * <p>
	 * 納入先名 を設定します。
	 * </p>
	 * @param deliveryNm
	 */
	public void setDeliveryNm(String deliveryNm) {
		this.deliveryNm = deliveryNm;
	}

	/**
	 * @return deliveryNmKana
	 */
	public String getDeliveryNmKana() {
		return deliveryNmKana;
	}

	/**
	 * @param deliveryNmKana セットする deliveryNmKana
	 */
	public void setDeliveryNmKana(String deliveryNmKana) {
		this.deliveryNmKana = deliveryNmKana;
	}

	/**
	 * <p>
	 * 郵便番号 を返却します。
	 * </p>
	 * @return zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * <p>
	 * 郵便番号 を設定します。
	 * </p>
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * <p>
	 * 住所（都道府県） を返却します。
	 * </p>
	 * @return prefectures
	 */
	public String getPrefectures() {
		return this.prefectures;
	}

	/**
	 * <p>
	 * 住所（都道府県） を設定します。
	 * </p>
	 * @param prefectures
	 */
	public void setPrefectures(String prefectures) {
		this.prefectures = prefectures;
	}

	/**
	 * <p>
	 * 住所（市区町村） を返却します。
	 * </p>
	 * @return municipality
	 */
	public String getMunicipality() {
		return this.municipality;
	}

	/**
	 * <p>
	 * 住所（市区町村） を設定します。
	 * </p>
	 * @param municipality
	 */
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	/**
	 * <p>
	 * 住所（市区町村以降） を返却します。
	 * </p>
	 * @return address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * <p>
	 * 住所（市区町村以降） を設定します。
	 * </p>
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * <p>
	 * 住所（建物名等） を返却します。
	 * </p>
	 * @return buildingNm
	 */
	public String getBuildingNm() {
		return this.buildingNm;
	}

	/**
	 * <p>
	 * 住所（建物名等） を設定します。
	 * </p>
	 * @param buildingNm
	 */
	public void setBuildingNm(String buildingNm) {
		this.buildingNm = buildingNm;
	}

	/**
	 * <p>
	 * 部署名 を返却します。
	 * </p>
	 * @return quarter
	 */
	public String getQuarter() {
		return this.quarter;
	}

	/**
	 * <p>
	 * 部署名 を設定します。
	 * </p>
	 * @param quarter
	 */
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	/**
	 * <p>
	 * 電話番号 を返却します。
	 * </p>
	 * @return tel
	 */
	public String getTel() {
		return this.tel;
	}

	/**
	 * <p>
	 * 電話番号 を設定します。
	 * </p>
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
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
	 * @return position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position セットする position
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return contactPersonNm
	 */
	public String getContactPersonNm() {
		return contactPersonNm;
	}

	/**
	 * @param contactPersonNm セットする contactPersonNm
	 */
	public void setContactPersonNm(String contactPersonNm) {
		this.contactPersonNm = contactPersonNm;
	}

	/**
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax セットする fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return sysCorporationId
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 * @param sysCorporationId セットする sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

}

