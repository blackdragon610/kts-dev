/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_口座情報を格納します。
 *
 * @author admin
 */
public class MstAccountDTO  {

	/** システム口座ID */
	private long sysAccountId;

	/** システム法人ID */
	private long sysCorporationId;

	/** 口座名 */
	private String accountNm;

	/** 銀行名 */
	private String bankNm;

	/** 支店名 */
	private String branchNm;

	/** 種目 */
	private String accountType;

	/** 口座番号 */
	private String accountNumber;

	/** 名義人 */
	private String accountHolder;

	/** 優先表示フラグ */
	private String priorFlag;

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
	 * システム口座ID を返却します。
	 * </p>
	 * @return sysAccountId
	 */
	public long getSysAccountId() {
		return this.sysAccountId;
	}

	/**
	 * <p>
	 * システム口座ID を設定します。
	 * </p>
	 * @param sysAccountId
	 */
	public void setSysAccountId(long sysAccountId) {
		this.sysAccountId = sysAccountId;
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
	 * 口座名 を返却します。
	 * </p>
	 * @return accountNm
	 */
	public String getAccountNm() {
		return this.accountNm;
	}

	/**
	 * <p>
	 * 口座名 を設定します。
	 * </p>
	 * @param accountNm
	 */
	public void setAccountNm(String accountNm) {
		this.accountNm = accountNm;
	}

	/**
	 * <p>
	 * 銀行名 を返却します。
	 * </p>
	 * @return bankNm
	 */
	public String getBankNm() {
		return this.bankNm;
	}

	/**
	 * <p>
	 * 銀行名 を設定します。
	 * </p>
	 * @param bankNm
	 */
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}

	/**
	 * <p>
	 * 支店名 を返却します。
	 * </p>
	 * @return branchNm
	 */
	public String getBranchNm() {
		return this.branchNm;
	}

	/**
	 * <p>
	 * 支店名 を設定します。
	 * </p>
	 * @param branchNm
	 */
	public void setBranchNm(String branchNm) {
		this.branchNm = branchNm;
	}

	/**
	 * <p>
	 * 種目 を返却します。
	 * </p>
	 * @return accountType
	 */
	public String getAccountType() {
		return this.accountType;
	}

	/**
	 * <p>
	 * 種目 を設定します。
	 * </p>
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * <p>
	 * 口座番号 を返却します。
	 * </p>
	 * @return accountNumber
	 */
	public String getAccountNumber() {
		return this.accountNumber;
	}

	/**
	 * <p>
	 * 口座番号 を設定します。
	 * </p>
	 * @param accountNumber
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * <p>
	 * 名義人 を返却します。
	 * </p>
	 * @return accountHolder
	 */
	public String getAccountHolder() {
		return this.accountHolder;
	}

	/**
	 * <p>
	 * 名義人 を設定します。
	 * </p>
	 * @param accountHolder
	 */
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	/**
	 * <p>
	 * 優先表示フラグ を返却します。
	 * </p>
	 * @return defaultAccountFlag
	 */
	public String getPriorFlag() {
		return this.priorFlag;
	}

	/**
	 * <p>
	 * 優先表示フラグ を設定します。
	 * </p>
	 * @param defaultAccountFlag
	 */
	public void setPriorFlag(String priorFlag) {
		this.priorFlag = priorFlag;
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

}

