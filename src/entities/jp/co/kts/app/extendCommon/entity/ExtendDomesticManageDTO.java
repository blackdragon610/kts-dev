package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.DomesticExhibitionDTO;

public class ExtendDomesticManageDTO extends DomesticExhibitionDTO{

	/** メーカー名 */
	private String makerNm;

	/** メーカー名ｶﾅ */
	private String makerNmKana;

	/** メーカー入力フラグ:Exceliインポート用 */
	private String makerInputFlg;

	/** 担当部署名 */
	private String departmentNm;

	/**
	 * メーカー名を取得します。
	 * @return メーカー名
	 */
	public String getMakerNm() {
	    return makerNm;
	}

	/**
	 * メーカー名を設定します。
	 * @param makerNm メーカー名
	 */
	public void setMakerNm(String makerNm) {
	    this.makerNm = makerNm;
	}

	/**
	 * メーカー名ｶﾅを取得します。
	 * @return メーカー名ｶﾅ
	 */
	public String getMakerNmKana() {
	    return makerNmKana;
	}

	/**
	 * メーカー名ｶﾅを設定します。
	 * @param makerNmKana メーカー名ｶﾅ
	 */
	public void setMakerNmKana(String makerNmKana) {
	    this.makerNmKana = makerNmKana;
	}

	/**
	 * メーカー入力フラグ:Exceliインポート用を取得します。
	 * @return メーカー入力フラグ:Exceliインポート用
	 */
	public String getMakerInputFlg() {
	    return makerInputFlg;
	}

	/**
	 * メーカー入力フラグ:Exceliインポート用を設定します。
	 * @param makerInputFlg メーカー入力フラグ:Exceliインポート用
	 */
	public void setMakerInputFlg(String makerInputFlg) {
	    this.makerInputFlg = makerInputFlg;
	}

	/**
	 * 担当部署名を取得します。
	 * @return 担当部署名
	 */
	public String getDepartmentNm() {
	    return departmentNm;
	}

	/**
	 * 担当部署名を設定します。
	 * @param makerNm 担当部署名
	 */
	public void setDepartmentNm(String departmentNm) {
	    this.departmentNm = departmentNm;
	}

}
