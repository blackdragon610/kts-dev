package jp.co.kts.app.extendCommon.entity;

import java.math.BigDecimal;

import jp.co.kts.app.common.entity.ItemCostDTO;

public class ExtendItemCostDTO extends ItemCostDTO {


	private String corporationNm;

	/** 法人掛け率 */
	private BigDecimal corporationRateOver;

	public BigDecimal getCorporationRateOver() {
		return corporationRateOver;
	}

	public void setCorporationRateOver(BigDecimal corporationRateOver) {
		this.corporationRateOver = corporationRateOver;
	}

	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

}
