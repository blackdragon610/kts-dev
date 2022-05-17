/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * M_法人情報を格納します。
 *
 * @author admin
 */
public class MstCorporationDTO  {

	/** システム法人ID */
	private long sysCorporationId;

	/** 法人名 */
	private String corporationNm;

	/** 法人正式名 */
	private String corporationFullNm;

	/** ファイル法人名 */
	private String fileCorporationNm;

	/** 郵便番号 */
	private String zip;

	/** 住所 */
	private String address;

	/** 電話番号 */
	private String telNo;

	/** FAX番号 */
	private String faxNo;

	/** メールアドレス */
	private String mailAddress;

	/** URL */
	private String url;

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

	/** 略称 */
	private String abbreviation;

	/** 法人掛け率 */
	private BigDecimal corporationRateOver;

	/** 売上高利益率 */
	private float salesProfitRate;

	/** 購入利益率 */
	private float purchaseProfitRate;

	public float getSalesProfitRate() {
		return salesProfitRate;
	}
	public void setSalesProfitRate(float salesProfitRate) {
		this.salesProfitRate = salesProfitRate;
	}
	public float getPurchaseProfitRate() {
		return this.purchaseProfitRate;
	}
	public void setPurchaseProfitRate(float purchaseProfitRate) {
		this.purchaseProfitRate = purchaseProfitRate;
	}


	
	public BigDecimal getCorporationRateOver() {
		return corporationRateOver;
	}

	public void setCorporationRateOver(BigDecimal corporationRateOver) {
		this.corporationRateOver = corporationRateOver;
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
	 * 法人名 を返却します。
	 * </p>
	 * @return corporationNm
	 */
	public String getCorporationNm() {
		return this.corporationNm;
	}

	/**
	 * <p>
	 * 法人名 を設定します。
	 * </p>
	 * @param corporationNm
	 */
	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

	/**
	 * <p>
	 * ファイル法人名 を返却します。
	 * </p>
	 * @return fileCorporationNm
	 */
	public String getFileCorporationNm() {
		return this.fileCorporationNm;
	}

	/**
	 * <p>
	 * ファイル法人名 を設定します。
	 * </p>
	 * @param fileCorporationNm
	 */
	public void setFileCorporationNm(String fileCorporationNm) {
		this.fileCorporationNm = fileCorporationNm;
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
	 * 住所 を返却します。
	 * </p>
	 * @return address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * <p>
	 * 住所 を設定します。
	 * </p>
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * <p>
	 * 電話番号 を返却します。
	 * </p>
	 * @return telNo
	 */
	public String getTelNo() {
		return this.telNo;
	}

	/**
	 * <p>
	 * 電話番号 を設定します。
	 * </p>
	 * @param telNo
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * <p>
	 * FAX番号 を返却します。
	 * </p>
	 * @return faxNo
	 */
	public String getFaxNo() {
		return this.faxNo;
	}

	/**
	 * <p>
	 * FAX番号 を設定します。
	 * </p>
	 * @param faxNo
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
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
	 * URL を返却します。
	 * </p>
	 * @return url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * <p>
	 * URL を設定します。
	 * </p>
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @param abbreviation セットする abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * @return corporationFullNm
	 */
	public String getCorporationFullNm() {
		return this.corporationNm;
//		return corporationFullNm;
	}

	/**
	 * @param corporationFullNm セットする corporationFullNm
	 */
	public void setCorporationFullNm(String corporationFullNm) {
		this.corporationFullNm = corporationFullNm;
	}


}

