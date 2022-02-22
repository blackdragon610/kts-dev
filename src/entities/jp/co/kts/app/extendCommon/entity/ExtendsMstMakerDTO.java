package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.MstMakerDTO;

public class ExtendsMstMakerDTO extends MstMakerDTO {

	private String makerNm;

	private String makerNmKana;

	/**
	 * makerNmを取得します。
	 * @return makerNm
	 */
	public String getMakerNm() {
	    return makerNm;
	}

	/**
	 * makerNmを設定します。
	 * @param makerNm makerNm
	 */
	public void setMakerNm(String makerNm) {
	    this.makerNm = makerNm;
	}

	/**
	 * makerNmKanaを取得します。
	 * @return makerNmKana
	 */
	public String getMakerNmKana() {
	    return makerNmKana;
	}

	/**
	 * makerNmKanaを設定します。
	 * @param makerNmKana makerNmKana
	 */
	public void setMakerNmKana(String makerNmKana) {
	    this.makerNmKana = makerNmKana;
	}

}
