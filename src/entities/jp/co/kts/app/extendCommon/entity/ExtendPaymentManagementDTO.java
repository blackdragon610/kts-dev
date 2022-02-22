package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.MstClientDTO;

public class ExtendPaymentManagementDTO extends MstClientDTO {

	/** 入金管理ID */
	private long sysPaymentManagementId;
	/** 口座ID */
	private long sysAccountId;
	/** 請求月 */
	private String demandMonth;
	/** 請求額 */
	private int billAmount;
	/** 繰越金額 */
	private int carryOverAmount;
	/** 入金金額 */
	private int receivePrice;
	/** 入金日 */
	private String receiveDate;
	/** フリーワード1 */
	private String freeWord;
	/** 金額1 */
	private int charge;
	/** フリーワード2 */
	private String freeWord2;
	/** 金額2 */
	private int charge2;
	/** フリーワード3 */
	private String freeWord3;
	/** 金額3 */
	private int charge3;
	/** 口座名 */
	private String accountNm;

	/**
	 * @return sysPaymentManagementId
	 */
	public long getSysPaymentManagementId() {
		return sysPaymentManagementId;
	}
	/**
	 * @param sysPaymentManagementId セットする sysPaymentManagementId
	 */
	public void setSysPaymentManagementId(long sysPaymentManagementId) {
		this.sysPaymentManagementId = sysPaymentManagementId;
	}
	/**
	 * システム口座IDを返却します
	 * @return sysAccountId
	 */
	public long getSysAccountId() {
		return sysAccountId;
	}
	/**
	 * @param sysAccountId セットする sysAccountId
	 */
	public void setSysAccountId(long sysAccountId) {
		this.sysAccountId = sysAccountId;
	}
	/**
	 * 請求月を返却します
	 * @return demandMonth
	 */
	public String getDemandMonth() {
		return demandMonth;
	}
	/**
	 * @param demandMonth セットする demandMonth
	 */
	public void setDemandMonth(String demandMonth) {
		this.demandMonth = demandMonth;
	}
	/**
	 * 請求額を返却します
	 * @return billAmount
	 */
	public int getBillAmount() {
		return billAmount;
	}
	/**
	 * @param billAmount セットする billAmount
	 */
	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}
	/**
	 * @return carryOverAmount
	 */
	public int getCarryOverAmount() {
		return carryOverAmount;
	}
	/**
	 * @param carryOverAmount セットする carryOverAmount
	 */
	public void setCarryOverAmount(int carryOverAmount) {
		this.carryOverAmount = carryOverAmount;
	}
	/**
	 * 入金額を返却します
	 * @return receivePrice
	 */
	public int getReceivePrice() {
		return receivePrice;
	}
	/**
	 * @param receivePrice セットする receivePrice
	 */
	public void setReceivePrice(int receivePrice) {
		this.receivePrice = receivePrice;
	}
	/**
	 * @return receiveDate
	 */
	public String getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @param receiveDate セットする receiveDate
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	/**
	 * フリーワードを返却します
	 * @return freeWord
	 */
	public String getFreeWord() {
		return freeWord;
	}
	/**
	 * @param freeWord セットする freeWord
	 */
	public void setFreeWord(String freeWord) {
		this.freeWord = freeWord;
	}
	/**
	 * 金額を返却します
	 * @return charge
	 */
	public int getCharge() {
		return charge;
	}
	/**
	 * @param charge セットする charge
	 */
	public void setCharge(int charge) {
		this.charge = charge;
	}
	/**
	 * フリーワード2を返却します
	 * @return freeWord2
	 */
	public String getFreeWord2() {
		return freeWord2;
	}
	/**
	 * @param freeWord2 セットする freeWord2
	 */
	public void setFreeWord2(String freeWord2) {
		this.freeWord2 = freeWord2;
	}
	/**
	 * 金額2を返却します
	 * @return charge2
	 */
	public int getCharge2() {
		return charge2;
	}
	/**
	 * @param charge2 セットする charge2
	 */
	public void setCharge2(int charge2) {
		this.charge2 = charge2;
	}
	/**
	 * フリーワード3を返却します
	 * @return freeWord3
	 */
	public String getFreeWord3() {
		return freeWord3;
	}
	/**
	 * @param freeWord3 セットする freeWord3
	 */
	public void setFreeWord3(String freeWord3) {
		this.freeWord3 = freeWord3;
	}
	/**
	 * 金額3を返却します
	 * @return charge3
	 */
	public int getCharge3() {
		return charge3;
	}
	/**
	 * @param charge3 セットする charge3
	 */
	public void setCharge3(int charge3) {
		this.charge3 = charge3;
	}
	/**
	 * @return accountNm
	 */
	public String getAccountNm() {
		return accountNm;
	}
	/**
	 * @param accountNm セットする accountNm
	 */
	public void setAccountNm(String accountNm) {
		this.accountNm = accountNm;
	}
}
