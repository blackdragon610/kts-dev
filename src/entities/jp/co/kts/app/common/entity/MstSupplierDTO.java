/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;

/**
 * M_仕入先情報を格納します。
 * @author Boncre
 *
 */
public class MstSupplierDTO {

	/** システム仕入先ID */
	private long sysSupplierId;

	/** 仕入先No */
	private String supplierNo;

	/** 会社・工場名 */
	private String companyFactoryNm;

	/** PO_NO頭文字 */
	private String poNoInitial;

	/** 国 */
	private String country;

	/** 住所 */
	private String address;

	/** 電話番号 */
	private String tel;

	/** FAX番号 */
	private String fax;

	/** 担当者名 */
	private String contactPersonNm;

	/** メールアドレス */
	private String mailAddress;

	/** 貿易条件 */
	private String tradeTerms;

	/** 支払条件1 */
	private int paymentTerms1;

	/** 支払条件2 */
	private int paymentTerms2;

	/** 通貨ID */
	private long currencyId;

	/** 仕入先リードタイム */
	private String leadTime;

	/** 銀行名 */
	private String bankNm;

	/** 支店名 */
	private String branchNm;

	/** 銀行住所 */
	private String bankAddress;

	/** SWIFT CODE */
	private String swiftCode;

	/** 口座番号 */
	private String accountNo;

	/** 備考/一言メモ */
	private String supplierRemarks;

	/** 削除フラグ */
	private String deleteFlg;

	/** 更新日 */
	private Date updateDate;

	/** 更新者ID */
	private int updateUserId;

	/**
	 * <p>
	 * システム仕入先IDを返却します。
	 * </p>
	 * @return sysSupplierId
	 */
	public long getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * <p>
	 * システム仕入先IDを設定します。
	 * </p>
	 * @param sysSupplierId
	 */
	public void setSysSupplierId(long sysSupplierId) {
		this.sysSupplierId = sysSupplierId;
	}

	/**
	 * <p>
	 * 仕入先番号を返却します。
	 * </p>
	 * @return supplierNo
	 */
	public String getSupplierNo() {
		return supplierNo;
	}

	/**
	 * <p>
	 * 仕入先番号を設定します。
	 * </p>
	 * @param supplierNo
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	/**
	 * <p>
	 * 工場・会社名を返却します。
	 * </p>
	 * @return companyFactoryNm
	 */
	public String getCompanyFactoryNm() {
		return companyFactoryNm;
	}

	/**
	 * <p>
	 * 工場・会社名を設定します。
	 * </p>
	 * @param companyFactoryNm
	 */
	public void setCompanyFactoryNm(String companyFactoryNm) {
		this.companyFactoryNm = companyFactoryNm;
	}

	/**
	 * <p>
	 * PO_NO頭文字を返却します
	 * </p>
	 * @return poNoInitial
	 */
	public String getPoNoInitial() {
		return poNoInitial;
	}

	/**
	 * <p>
	 * PO_NO頭文字を設定します
	 * </p>
	 * @param poNoInitial
	 */
	public void setPoNoInitial(String poNoInitial) {
		this.poNoInitial = poNoInitial;
	}

	/**
	 * <p>
	 * 国名を返却します。
	 * </p>
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * <p>
	 * 国名を設定します。
	 * </p>
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * <p>
	 * 住所を返却します。
	 * </p>
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * <p>
	 * 住所を設定します。
	 * </p>
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * <p>
	 * 電話番号を返却します。
	 * </p>
	 * @return tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * <p>
	 * 電話番号を設定します。
	 * </p>
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * <p>
	 * FAX番号を返却します。
	 * </p>
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * <p>
	 * FAX番号を設定します。
	 * </p>
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * <p>
	 * 担当者名を返却します。
	 * </p>
	 * @return contactPersonNm
	 */
	public String getContactPersonNm() {
		return contactPersonNm;
	}

	/**
	 * <p>
	 * 担当者名を設定します。
	 * </p>
	 * @param contactPersonNm
	 */
	public void setContactPersonNm(String contactPersonNm) {
		this.contactPersonNm = contactPersonNm;
	}

	/**
	 * <p>
	 * メールアドレスを返却します。
	 * </p>
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * <p>
	 * メールアドレスを設定します。
	 * </p>
	 * @param mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * <p>
	 * 貿易条件を返却します。
	 * </p>
	 * @return tradeTerms
	 */
	public String getTradeTerms() {
		return tradeTerms;
	}

	/**
	 * <p>
	 * 貿易条件を設定します。
	 * </p>
	 * @param tradeTerms
	 */
	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}

	/**
	 * <p>
	 * 支払条件1を返却します。
	 * </p>
	 * @return paymentTerms1
	 */
	public int getPaymentTerms1() {
		return paymentTerms1;
	}

	/**
	 * <p>
	 * 支払条件1を設定します。
	 * </p>
	 * @param paymentTerms1
	 */
	public void setPaymentTerms1(int paymentTerms1) {
		this.paymentTerms1 = paymentTerms1;
	}

	/**
	 * <p>
	 * 支払条件2を返却します。
	 * </p>
	 * @return paymentTerms2
	 */
	public int getPaymentTerms2() {
		return paymentTerms2;
	}

	/**
	 * <p>
	 * 支払条件2を設定します。
	 * </p>
	 * @param paymentTerms2
	 */
	public void setPaymentTerms2(int paymentTerms2) {
		this.paymentTerms2 = paymentTerms2;
	}

	/**
	 * <p>
	 * 通貨IDを返却します。
	 * </p>
	 * @return currencyId
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * <p>
	 * 通貨IDを設定します。
	 * </p>
	 * @param currencyId
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}


	/**
	 * <p>
	 * リードタイムを返却します。
	 * </p>
	 * @return leadTime
	 */
	public String getLeadTime() {
		return leadTime;
	}

	/**
	 * <p>
	 * リードタイムを設定します。
	 * </p>
	 * @param leadTime
	 */
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	/**
	 * <p>
	 * 銀行名を返却します。
	 * </p>
	 * @return bankNm
	 */
	public String getBankNm() {
		return bankNm;
	}

	/**
	 * <p>
	 * 銀行名を設定します。
	 * </p>
	 * @param bankNm
	 */
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}

	/**
	 * <p>
	 * 支店名を返却します。
	 * </p>
	 * @return branchNm
	 */
	public String getBranchNm() {
		return branchNm;
	}

	/**
	 * <p>
	 * 支店名を設定します。
	 * </p>
	 * @param branchNm
	 */
	public void setBranchNm(String branchNm) {
		this.branchNm = branchNm;
	}

	/**
	 * <p>
	 * 銀行住所を返却します。
	 * </p>
	 * @return bankAddress
	 */
	public String getBankAddress() {
		return bankAddress;
	}

	/**
	 * <p>
	 * 銀行住所を設定します。
	 * </p>
	 * @param bankAddress
	 */
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	/**
	 * <p>
	 * SWIFT CODEを返却します。
	 * </p>
	 * @return swiftCode
	 */
	public String getSwiftCode() {
		return swiftCode;
	}

	/**
	 * <p>
	 * SWIFT CODEを設定します。
	 * </p>
	 * @param swiftCode
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * <p>
	 * 口座番号を返却します。
	 * </p>
	 * @return accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * <p>
	 * 口座番号を設定します。
	 * </p>
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * <p>
	 * 備考を返却します。
	 * </p>
	 * @return supplierRemarks
	 */
	public String getSupplierRemarks() {
		return supplierRemarks;
	}

	/**
	 * <p>
	 * 備考を設定します。
	 * </p>
	 * @param supplierRemarks
	 */
	public void setSupplierRemarks(String supplierRemarks) {
		this.supplierRemarks = supplierRemarks;
	}

	/**
	 * <p>
	 * 削除フラグを返却します。
	 * </p>
	 * @return deleteFlg
	 */
	public String getDeleteFlg() {
		return deleteFlg;
	}

	/**
	 * <p>
	 * 削除フラグを設定します。
	 * </p>
	 * @param deleteFlg
	 */
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	/**
	 * <p>
	 * 更新日を返却します。
	 * </p>
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * <p>
	 * 更新日を設定します。
	 * </p>
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * <p>
	 * 更新者IDを返却します。
	 * </p>
	 * @return updateUserId
	 */
	public int getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * <p>
	 * 更新者IDを設定します。
	 * </p>
	 * @param updateUserId
	 */
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}
}