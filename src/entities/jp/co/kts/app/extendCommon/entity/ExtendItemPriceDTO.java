package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.ItemPriceDTO;

public class ExtendItemPriceDTO extends ItemPriceDTO {


	private String corporationNm;

	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}


}
