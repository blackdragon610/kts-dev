package jp.co.kts.app.output.entity;

public class SysSalesSlipIdDTO {

	private long sysSalesSlipId;

	private long noTaxSumPieceRate;

	private long inTaxSumPieceRate;

	private long sumClaimPrice;

	private long cost;

	private long sumGrossMargin;

	private long sysCorporationId;

	public long getSysSalesSlipId() {
		return sysSalesSlipId;
	}

	public void setSysSalesSlipId(long sysSalesSlipId) {
		this.sysSalesSlipId = sysSalesSlipId;
	}

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

	public long getSumClaimPrice() {
		return sumClaimPrice;
	}

	public void setSumClaimPrice(long sumClaimPrice) {
		this.sumClaimPrice = sumClaimPrice;
	}

	public long getCost() {
		return cost;
	}

	public void setSumCost(long cost) {
		this.cost = cost;
	}

	public long getSumGrossMargin() {
		return sumGrossMargin;
	}

	public void setSumGrossMargin(long sumGrossMargin) {
		this.sumGrossMargin = sumGrossMargin;
	}

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

}
