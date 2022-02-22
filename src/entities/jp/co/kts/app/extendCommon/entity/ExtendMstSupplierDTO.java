package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.MstSupplierDTO;

public class ExtendMstSupplierDTO extends MstSupplierDTO {

	/** 表示用システム仕入先ID  */
	private String supplierId;

	private String currencyType;

	private String currencyNm;

	private float rate;



	/**
	 * @return supplierId
	 */
	public String getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return currencyType
	 */
	public String getCurrencyType() {
		return currencyType;
	}

	/**
	 * @param currencyType
	 */
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * @return currencyNm
	 */
	public String getCurrencyNm() {
		return currencyNm;
	}

	/**
	 * @param currencyNm
	 */
	public void setCurrencyNm(String currencyNm) {
		this.currencyNm = currencyNm;
	}

	/**
	 * @return rate
	 */
	public float getRate() {
		return rate;
	}

	/**
	 * @param rate
	 */
	public void setRate(float rate) {
		this.rate = rate;
	}


}
