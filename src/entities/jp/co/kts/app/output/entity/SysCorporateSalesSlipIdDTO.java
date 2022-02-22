package jp.co.kts.app.output.entity;

public class SysCorporateSalesSlipIdDTO {

	/** システム業販伝票ID */
	private long sysCorporateSalesSlipId;

	/** 税抜き商品単価の合計 */
	private long noTaxSumPieceRate;

	/** 税込商品単価の合計 */
	private long inTaxSumPieceRate;

	/**  */
	private long sumPieceRate;

	/**  */
	private long sumClaimPrice;

	/** 原価 */
	private long cost;

	/** 消費税 */
	private long tax;

	/** 粗利合計？ */
	private long sumGrossMargin;

	/** システム法人ID */
	private long sysCorporationId;


	/**
	 * システム業販伝票ID
	 * @return
	 */
	public long getSysCorporateSalesSlipId() {
		return sysCorporateSalesSlipId;
	}

	/**
	 * システム業販伝票ID
	 * @param sysCorporateSalesSlipId
	 */
	public void setSysCorporateSalesSlipId(long sysCorporateSalesSlipId) {
		this.sysCorporateSalesSlipId = sysCorporateSalesSlipId;
	}

	/**
	 * 税抜き商品単価の合計
	 * @return
	 */
	public long getNoTaxSumPieceRate() {
		return noTaxSumPieceRate;
	}

	/**
	 * 税抜き商品単価の合計
	 * @param noTaxSumPieceRate
	 */
	public void setNoTaxSumPieceRate(long noTaxSumPieceRate) {
		this.noTaxSumPieceRate = noTaxSumPieceRate;
	}

	/**
	 * 税込商品単価の合計
	 * @return
	 */
	public long getInTaxSumPieceRate() {
		return inTaxSumPieceRate;
	}

	/**
	 * 税込商品単価の合計
	 * @param inTaxSumPieceRate
	 */
	public void setInTaxSumPieceRate(long inTaxSumPieceRate) {
		this.inTaxSumPieceRate = inTaxSumPieceRate;
	}

	/**
	 *
	 * @return
	 */
	public long getSumPieceRate() {
		return sumPieceRate;
	}

	/**
	 *
	 * @param sumPieceRate
	 */
	public void setSumPieceRate(long sumPieceRate) {
		this.sumPieceRate = sumPieceRate;
	}

	/**
	 * 合計請求金額？
	 * @return
	 */
	public long getSumClaimPrice() {
		return sumClaimPrice;
	}

	/**
	 * 合計請求金額？
	 * @param sumClaimPrice
	 */
	public void setSumClaimPrice(long sumClaimPrice) {
		this.sumClaimPrice = sumClaimPrice;
	}

	/**
	 * 原価
	 * @return
	 */
	public long getCost() {
		return cost;
	}

	/**
	 * 原価
	 * @param cost
	 */
	public void setSumCost(long cost) {
		this.cost = cost;
	}

	/**
	 * 消費税
	 * @return
	 */
	public long getTax() {
		return tax;
	}

	/**
	 * 消費税
	 * @param tax
	 */
	public void setTax(long tax) {
		this.tax = tax;
	}

	/**
	 * 粗利合計？
	 * @return
	 */
	public long getSumGrossMargin() {
		return sumGrossMargin;
	}

	/**
	 * 粗利合計？
	 * @param sumGrossMargin
	 */
	public void setSumGrossMargin(long sumGrossMargin) {
		this.sumGrossMargin = sumGrossMargin;
	}

	/**
	 * システム法人ID
	 * @return
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 * システム法人ID
	 * @param sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

}
