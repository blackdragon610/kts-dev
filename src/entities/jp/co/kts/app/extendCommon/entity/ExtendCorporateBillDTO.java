package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.CorporateBillDTO;

public class ExtendCorporateBillDTO extends CorporateBillDTO {

	/** システム口座名 */
	private String accountNm;


	/** システム口座ID */
	private long sysAccountId;

	/**
	 * システム口座名を取得します
	 * @return sysAccountId
	 */
	public String getAccountNm() {
		return accountNm;
	}

	/**
	 * システム口座名を設定します
	 * @param sysAccountId セットする sysAccountId
	 */
	public void setAccountNm(String accountNm) {
		this.accountNm = accountNm;
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
