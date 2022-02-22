package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.MstAccountDTO;

public class ExtendMstAccountDTO extends MstAccountDTO {


	private String corporationNm;

	private String accountTypeNm;


	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

	/**
	 * @return accountTypeNm
	 */
	public String getAccountTypeNm() {
		return accountTypeNm;
	}

	/**
	 * @param accountTypeNm セットする accountTypeNm
	 */
	public void setAccountTypeNm(String accountTypeNm) {
		this.accountTypeNm = accountTypeNm;
	}


}
