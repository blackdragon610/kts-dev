package jp.co.kts.app.search.entity;

public class BtobBillSearchDTO {


	/** 合計値表示フラグ */
	private String sumDispFlg;

	/** 合計金額 */
	private String grossProfitCalc;

	/**
	 * 合計値表示フラグ
	 * @return sumDispFlg
	 */
	public String getSumDispFlg() {
		return sumDispFlg;
	}

	/**
	 * 合計値表示フラグ
	 * @param sumDispFlg セットする sumDispFlg
	 */
	public void setSumDispFlg(String sumDispFlg) {
		this.sumDispFlg = sumDispFlg;
	}

	/**
	 * 合計金額
	 * @return grossProfitCalc
	 */
	public String getGrossProfitCalc() {
		return grossProfitCalc;
	}

	/**
	 * 合計金額
	 * @param grossProfitCalc セットする grossProfitCalc
	 */
	public void setGrossProfitCalc(String grossProfitCalc) {
		this.grossProfitCalc = grossProfitCalc;
	}
}
