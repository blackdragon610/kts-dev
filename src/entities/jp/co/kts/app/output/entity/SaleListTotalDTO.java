package jp.co.kts.app.output.entity;

public class SaleListTotalDTO {

	/**
	 * 税抜き商品単価合計
	 */
	private long noTaxSumPieceRate;

	/**
	 * 税込み商品単価合計
	 */
	private long inTaxSumPieceRate;

	/**
	 * 原価合計
	 */
	private long sumCost;

	/**
	 * 粗利合計
	 */
	private long sumGrossMargin;

	/**
	 * 合計請求金額合計
	 */
	private long sumSumClaimPrice;

	public long getNoTaxSumPieceRate() {
		return noTaxSumPieceRate;
	}

	public void setNoTaxSumPieceRate(long noTaxSumPieceRate) {
		this.noTaxSumPieceRate = noTaxSumPieceRate;
	}

	public long getInTaxSumPieceRate() {
		return inTaxSumPieceRate;
	}

	public void setInTaxSumPieceRate(long inTaxSumPieceRate) {
		this.inTaxSumPieceRate = inTaxSumPieceRate;
	}

	public long getSumCost() {
		return sumCost;
	}

	public void setSumCost(long sumCost) {
		this.sumCost = sumCost;
	}

	public long getSumGrossMargin() {
		return sumGrossMargin;
	}

	public void setSumGrossMargin(long sumGrossMargin) {
		this.sumGrossMargin = sumGrossMargin;
	}

	public long getSumSumClaimPrice() {
		return sumSumClaimPrice;
	}

	public void setSumSumClaimPrice(long sumSumClaimPrice) {
		this.sumSumClaimPrice = sumSumClaimPrice;
	}

}
