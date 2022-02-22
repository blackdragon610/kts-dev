/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 法人間請求書情報を格納します。
 *
 * @author admin
 */
public class CorporateBillDTO  {

	/** システム法人間請求書ID */
	private long sysCorporateBillId;

	/** システム法人ID */
	private long sysCorporationId;

	/** 請求書番号 */
	private String corporateBillNo;

	/** 請求月 */
	private String demandMonth;

	/** 請求月内番号 */
	private String demandMonthNo;

	/** 請求データ作成日 */
	private String billdataCreateDate;

	/** 取引先法人名 */
	private String clientCorporationNm;

	/** 請求先法人名 */
	private String clientBillingNm;

	/** 請求先締日 */
	private String clientBillingCutoff;

	/** 取引先電話番号 */
	private String clientTelNo;

	/** 取引先FAX番号 */
	private String clientFaxNo;

	/** 前月請求金額 */
	private int preMonthBillAmount;

	/** 請求金額 */
	private int billAmount;

	/** フリーワード1 */
	private String freeWord;

	/** フリーワード2 */
	private String freeWord2;

	/** フリーワード3 */
	private String freeWord3;

	/** 入金額 */
	private int receivePrice;

	/** 金額2 */
	private int charge2;

	/** 金額3 */
	private int charge3;

	/** 入金日 */
	private String receiveDate;

	/** 手数料 */
	private int charge;

	/** 繰越金額 */
	private int carryOverAmount;

	/** 商品単価小計 */
	private int sumPieceRate;

	/** 消費税 */
	private int consumptionTax;

	/** 合計請求金額 */
	private int sumClaimPrice;

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

	/** 締日コード */
	private int clientCutoffDate;

	/** 締日 */
	private String clientCutoffDateNm;

	/** メモ */
	private String memo;

	/** システム口座ID */
	private long sysAccountId;
	/**
	 * <p>
	 * システム法人間請求書ID を返却します。
	 * </p>
	 * @return sysCorporateBillId
	 */
	public long getSysCorporateBillId() {
		return this.sysCorporateBillId;
	}

	/**
	 * <p>
	 * システム法人間請求書ID を設定します。
	 * </p>
	 * @param sysCorporateBillId
	 */
	public void setSysCorporateBillId(long sysCorporateBillId) {
		this.sysCorporateBillId = sysCorporateBillId;
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
	 * 請求書番号 を返却します。
	 * </p>
	 * @return CorporateBillNo
	 */
	public String getCorporateBillNo() {
		return this.corporateBillNo;
	}

	/**
	 * <p>
	 * 請求書番号 を設定します。
	 * </p>
	 * @param CorporateBillNo
	 */
	public void setCorporateBillNo(String corporateBillNo) {
		this.corporateBillNo = corporateBillNo;
	}

	/**
	 * <p>
	 * 請求月 を返却します。
	 * </p>
	 * @return demandMonth
	 */
	public String getDemandMonth() {
		return this.demandMonth;
	}

	/**
	 * <p>
	 * 請求月 を設定します。
	 * </p>
	 * @param demandMonth
	 */
	public void setDemandMonth(String demandMonth) {
		this.demandMonth = demandMonth;
	}

	/**
	 * <p>
	 * 請求月内番号 を返却します。
	 * </p>
	 * @return demandMonthNo
	 */
	public String getDemandMonthNo() {
		return this.demandMonthNo;
	}

	/**
	 * <p>
	 * 請求月内番号 を設定します。
	 * </p>
	 * @param demandMonthNo
	 */
	public void setDemandMonthNo(String demandMonthNo) {
		this.demandMonthNo = demandMonthNo;
	}

	/**
	 * <p>
	 * 請求データ作成日 を返却します。
	 * </p>
	 * @return billdataCreateDate
	 */
	public String getBilldataCreateDate() {
		return this.billdataCreateDate;
	}

	/**
	 * <p>
	 * 請求データ作成日 を設定します。
	 * </p>
	 * @param billdataCreateDate
	 */
	public void setBilldataCreateDate(String billdataCreateDate) {
		this.billdataCreateDate = billdataCreateDate;
	}

	/**
	 * <p>
	 * 取引先法人名 を返却します。
	 * </p>
	 * @return clientCorporationNm
	 */
	public String getClientCorporationNm() {
		return this.clientCorporationNm;
	}

	/**
	 * <p>
	 * 取引先法人名 を設定します。
	 * </p>
	 * @param clientCorporationNm
	 */
	public void setClientCorporationNm(String clientCorporationNm) {
		this.clientCorporationNm = clientCorporationNm;
	}

	/**
	 * <p>
	 * 請求先法人名 を返却します。
	 * </p>
	 * @return clientBillingNm
	 */
	public String getClientBillingNm() {
		return this.clientBillingNm;
	}

	/**
	 * <p>
	 * 請求先法人名 を設定します。
	 * </p>
	 * @param clientBillingNm
	 */
	public void setClientBillingNm(String clientBillingNm) {
		this.clientBillingNm = clientBillingNm;
	}
	/**
	 * <p>
	 * 取引先電話番号 を返却します。
	 * </p>
	 * @return clientTelNo
	 */
	public String getClientTelNo() {
		return this.clientTelNo;
	}

	/**
	 * <p>
	 * 取引先電話番号 を設定します。
	 * </p>
	 * @param clientTelNo
	 */
	public void setClientTelNo(String clientTelNo) {
		this.clientTelNo = clientTelNo;
	}

	/**
	 * <p>
	 * 取引先FAX番号 を返却します。
	 * </p>
	 * @return clientFaxNo
	 */
	public String getClientFaxNo() {
		return this.clientFaxNo;
	}

	/**
	 * <p>
	 * 取引先FAX番号 を設定します。
	 * </p>
	 * @param clientFaxNo
	 */
	public void setClientFaxNo(String clientFaxNo) {
		this.clientFaxNo = clientFaxNo;
	}

	/**
	 * <p>
	 * 前月請求金額 を返却します。
	 * </p>
	 * @return preMonthBillAmount
	 */
	public int getPreMonthBillAmount() {
		return this.preMonthBillAmount;
	}

	/**
	 * <p>
	 * 前月請求金額 を設定します。
	 * </p>
	 * @param preMonthBillAmount
	 */
	public void setPreMonthBillAmount(int preMonthBillAmount) {
		this.preMonthBillAmount = preMonthBillAmount;
	}

	/**
	 * <p>
	 * 請求金額 を返却します。
	 * </p>
	 * @return billAmount
	 */
	public int getBillAmount() {
		return this.billAmount;
	}

	/**
	 * <p>
	 * 請求金額 を設定します。
	 * </p>
	 * @param billAmount
	 */
	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}

	/**
	 * <p>
	 * フリーワード を返却します。
	 * </p>
	 * @return freeWord
	 */
	public String getFreeWord() {
		return this.freeWord;
	}

	/**
	 * <p>
	 * フリーワード を設定します。
	 * </p>
	 * @param freeWord
	 */
	public void setFreeWord(String freeWord) {
		this.freeWord = freeWord;
	}

	/**
	 * <p>
	 * 入金額 を返却します。
	 * </p>
	 * @return receivePrice
	 */
	public int getReceivePrice() {
		return this.receivePrice;
	}

	/**
	 * <p>
	 * 入金額 を設定します。
	 * </p>
	 * @param receivePrice
	 */
	public void setReceivePrice(int receivePrice) {
		this.receivePrice = receivePrice;
	}

	/**
	 * <p>
	 * 入金日 を返却します。
	 * </p>
	 * @return receiveDate
	 */
	public String getReceiveDate() {
		return this.receiveDate;
	}

	/**
	 * <p>
	 * 入金日 を設定します。
	 * </p>
	 * @param receiveDate
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * <p>
	 * 手数料 を返却します。
	 * </p>
	 * @return charge
	 */
	public int getCharge() {
		return this.charge;
	}

	/**
	 * <p>
	 * 手数料 を設定します。
	 * </p>
	 * @param charge
	 */
	public void setCharge(int charge) {
		this.charge = charge;
	}

	/**
	 * <p>
	 * 繰越金額 を返却します。
	 * </p>
	 * @return carryOverAmount
	 */
	public int getCarryOverAmount() {
		return this.carryOverAmount;
	}

	/**
	 * <p>
	 * 繰越金額 を設定します。
	 * </p>
	 * @param carryOverAmount
	 */
	public void setCarryOverAmount(int carryOverAmount) {
		this.carryOverAmount = carryOverAmount;
	}

	/**
	 * <p>
	 * 商品単価小計 を返却します。
	 * </p>
	 * @return sumPieceRate
	 */
	public int getSumPieceRate() {
		return this.sumPieceRate;
	}

	/**
	 * <p>
	 * 商品単価小計 を設定します。
	 * </p>
	 * @param sumPieceRate
	 */
	public void setSumPieceRate(int sumPieceRate) {
		this.sumPieceRate = sumPieceRate;
	}

	/**
	 * <p>
	 * 消費税 を返却します。
	 * </p>
	 * @return consumptionTax
	 */
	public int getConsumptionTax() {
		return this.consumptionTax;
	}

	/**
	 * <p>
	 * 消費税 を設定します。
	 * </p>
	 * @param consumptionTax
	 */
	public void setConsumptionTax(int consumptionTax) {
		this.consumptionTax = consumptionTax;
	}

	/**
	 * <p>
	 * 合計請求金額 を返却します。
	 * </p>
	 * @return sumClaimPrice
	 */
	public int getSumClaimPrice() {
		return this.sumClaimPrice;
	}

	/**
	 * <p>
	 * 合計請求金額 を設定します。
	 * </p>
	 * @param sumClaimPrice
	 */
	public void setSumClaimPrice(int sumClaimPrice) {
		this.sumClaimPrice = sumClaimPrice;
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
	 * @return clientCutoffDate
	 */
	public int getClientCutoffDate() {
		return clientCutoffDate;
	}

	/**
	 * @param clientCutoffDate セットする clientCutoffDate
	 */
	public void setClientCutoffDate(int clientCutoffDate) {
		this.clientCutoffDate = clientCutoffDate;
	}

	/**
	 * @return clientCutoffDate
	 */
	public String getClientCutoffDateNm() {
		return clientCutoffDateNm;
	}


	/**
	 * @param clientCutoffDate セットする clientCutoffDate
	 */
	public void setClientCutoffDateNm(String clientCutoffDateNm) {
		this.clientCutoffDateNm = clientCutoffDateNm;
	}

	/**
	 * <p>
	 * フリーワード2 を返却します。
	 * </p>
	 * @return freeWord2
	 */
	public String getFreeWord2() {
		return freeWord2;
	}

	/**
	 * <p>
	 * フリーワード2 を設定します。
	 * </p>
	 * @param freeWord2
	 */
	public void setFreeWord2(String freeWord2) {
		this.freeWord2 = freeWord2;
	}

	/**
	 * <p>
	 * フリーワード3 を返却します。
	 * </p>
	 * @return freeWord3
	 */
	public String getFreeWord3() {
		return freeWord3;
	}

	/**
	 * <p>
	 * フリーワード3 を設定します。
	 * </p>
	 * @param freeWord3
	 */
	public void setFreeWord3(String freeWord3) {
		this.freeWord3 = freeWord3;
	}

	/**
	 * <p>
	 * 入金額2 を返却します。
	 * </p>
	 * @return charge2
	 */
	public int getCharge2() {
		return charge2;
	}

	/**
	 * <p>
	 * 入金額2 を設定します。
	 * </p>
	 * @param charge2
	 */
	public void setCharge2(int charge2) {
		this.charge2 = charge2;
	}

	/**
	 * <p>
	 * 入金額3 を返却します。
	 * </p>
	 * @return charge3
	 */
	public int getCharge3() {
		return charge3;
	}

	/**
	 * <p>
	 * 入金額3 を設定します。
	 * </p>
	 * @param charge3
	 */
	public void setCharge3(int charge3) {
		this.charge3 = charge3;
	}

	/**
	 * メモを取得します。
	 * @return メモ
	 */
	public String getMemo() {
	    return memo;
	}

	/**
	 * メモを設定します。
	 * @param memo メモ
	 */
	public void setMemo(String memo) {
	    this.memo = memo;
	}

	/**
	 * システム口座IDを取得します
	 * @return sysAccountId
	 */
	public long getSysAccountId() {
		return sysAccountId;
	}

	/**
	 * システム口座IDを設定します
	 * @param sysAccountId セットする sysAccountId
	 */
	public void setSysAccountId(long sysAccountId) {
		this.sysAccountId = sysAccountId;
	}



}
