package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.MstClientDTO;

public class ExtendMstClientDTO extends MstClientDTO {


	private String corporationNm;

	private String paymentMethodNm;

	private int receivableBalance;

	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

	public String getPaymentMethodNm() {
		return paymentMethodNm;
	}

	public void setPaymentMethodNm(String paymentMethodNm) {
		this.paymentMethodNm = paymentMethodNm;
	}

	/**
	 * @return receivableBalance
	 */
	public int getReceivableBalance() {
		return receivableBalance;
	}

	/**
	 * @param receivableBalance セットする receivableBalance
	 */
	public void setReceivableBalance(int receivableBalance) {
		this.receivableBalance = receivableBalance;
	}


}
