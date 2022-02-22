/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_得意先情報を格納します。
 *
 * @author admin
 */
public class MstClientDTO  {

	/** システム得意先ID */
	private long sysClientId;

	/** 法人No */
	private String clientNo;

	/** システム法人ID */
	private long sysCorporationId;

	/** 得意先名 */
	private String clientNm;

	/** 得意先名フリガナ */
	private String clientNmKana;

	/** 略称 */
	private String clientAbbreviation;

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

	/** ご担当者名 */
	private String contactPersonNm;

	/** 敬称 */
	private String title;

	/** 電話番号 */
	private String tel;

	/** FAX番号 */
	private String fax;

	/** メールアドレス */
	private String mailAddress;

	/** 運送会社 */
	private String transportCorporationSystem;

	/** 支払方法 */
	private String paymentMethod;

	/** 備考/一言メモ */
	private String remarks;

	/** 備考/一言メモ26文字 */
	private String shortenedRemarks;

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

	/** 締日 */
	private int cutoffDate;

	/** 請求先 */
	private String billingDst;

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
	 * 得意先名 を返却します。
	 * </p>
	 * @return clientNm
	 */
	public String getClientNm() {
		return this.clientNm;
	}

	/**
	 * <p>
	 * 得意先名 を設定します。
	 * </p>
	 * @param clientNm
	 */
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}

	/**
	 * <p>
	 * 得意先名フリガナ を返却します。
	 * </p>
	 * @return clientNmKana
	 */
	public String getClientNmKana() {
		return this.clientNmKana;
	}

	/**
	 * <p>
	 * 得意先名フリガナ を設定します。
	 * </p>
	 * @param clientNmKana
	 */
	public void setClientNmKana(String clientNmKana) {
		this.clientNmKana = clientNmKana;
	}

	/**
	 * <p>
	 * 略称 を返却します。
	 * </p>
	 * @return clientAbbreviation
	 */
	public String getClientAbbreviation() {
		return this.clientAbbreviation;
	}

	/**
	 * <p>
	 * 略称 を設定します。
	 * </p>
	 * @param clientAbbreviation
	 */
	public void setClientAbbreviation(String clientAbbreviation) {
		this.clientAbbreviation = clientAbbreviation;
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
	 * 役職名 を返却します。
	 * </p>
	 * @return position
	 */
	public String getPosition() {
		return this.position;
	}

	/**
	 * <p>
	 * 役職名 を設定します。
	 * </p>
	 * @param position
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * <p>
	 * ご担当者名 を返却します。
	 * </p>
	 * @return contactPersonNm
	 */
	public String getContactPersonNm() {
		return this.contactPersonNm;
	}

	/**
	 * <p>
	 * ご担当者名 を設定します。
	 * </p>
	 * @param contactPersonNm
	 */
	public void setContactPersonNm(String contactPersonNm) {
		this.contactPersonNm = contactPersonNm;
	}

	/**
	 * <p>
	 * 敬称 を返却します。
	 * </p>
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * <p>
	 * 敬称 を設定します。
	 * </p>
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * FAX番号 を返却します。
	 * </p>
	 * @return fax
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * <p>
	 * FAX番号 を設定します。
	 * </p>
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * <p>
	 * メールアドレス を返却します。
	 * </p>
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return this.mailAddress;
	}

	/**
	 * <p>
	 * メールアドレス を設定します。
	 * </p>
	 * @param mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * <p>
	 * 支払方法 を返却します。
	 * </p>
	 * @return paymentMethod
	 */
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * <p>
	 * 支払方法 を設定します。
	 * </p>
	 * @param paymentMethod
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * <p>
	 * 備考/一言メモ を返却します。
	 * </p>
	 * @return remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * <p>
	 * 備考/一言メモ を設定します。
	 * </p>
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * <p>
	 * 備考/一言メモ省略版 を返却します。
	 * </p>
	 * @return shortenedRemarks
	 */
	public String getShortenedRemarks() {
		return this.shortenedRemarks;
	}

	/**
	 * <p>
	 * 備考/一言メモ省略版 を設定します。
	 * </p>
	 * @param shortenedRemarks
	 */
	public void setShortenedRemarks(String shortenedRemarks) {
		this.shortenedRemarks = shortenedRemarks;
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
	 * <p>
	 * 締日 を返却します。
	 * </p>
	 * @return cutoffDate
	 */
	public int getCutoffDate() {
		return this.cutoffDate;
	}

	/**
	 * <p>
	 * 締日 を設定します。
	 * </p>
	 * @param cutoffDate
	 */
	public void setCutoffDate(int cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

	/**
	 * <p>
	 * 請求先 を返却します。
	 * </p>
	 * @return deleteFlag
	 */
	public String getBillingDst() {
		return this.billingDst;
	}

	/**
	 * <p>
	 * 請求先 を設定します。
	 * </p>
	 * @param deleteFlag
	 */
	public void setBillingDst(String billingDst) {
		this.billingDst = billingDst;
	}

	/**
	 * @return clientNo
	 */
	public String getClientNo() {
		return clientNo;
	}

	/**
	 * @param clientNo セットする clientNo
	 */
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	/**
	 * @return transportCorporationSystem
	 */
	public String getTransportCorporationSystem() {
		return transportCorporationSystem;
	}

	/**
	 * @param transportCorporationSystem セットする transportCorporationSystem
	 */
	public void setTransportCorporationSystem(String transportCorporationSystem) {
		this.transportCorporationSystem = transportCorporationSystem;
	}

}

