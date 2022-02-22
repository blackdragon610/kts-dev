package jp.co.kts.app.output.entity;

public class ExportSaleSummalyDTO {

	private String itemCode;
	private String itemNm;
	private long totalSalesPieceRate;
	private long totalInTaxPieceRate;
	private long totalNoTaxPieceRate;
	private long totalCost;
	private long totalGrossMargin;
	private long totalOrderNum;

	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public long getTotalSalesPieceRate() {
		return totalSalesPieceRate;
	}
	public void setTotalSalesPieceRate(long totalSalesPieceRate) {
		this.totalSalesPieceRate = totalSalesPieceRate;
	}

	public long getTotalInTaxPieceRate() {
		return totalInTaxPieceRate;
	}
	public void setTotalInTaxPieceRate(long totalInTaxPieceRate) {
		this.totalInTaxPieceRate = totalInTaxPieceRate;
	}
	public long getTotalNoTaxPieceRate() {
		return totalNoTaxPieceRate;
	}
	public void setTotalNoTaxPieceRate(long totalNoTaxPieceRate) {
		this.totalNoTaxPieceRate = totalNoTaxPieceRate;
	}
	public long getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
	}
	public long getTotalGrossMargin() {
		return totalGrossMargin;
	}
	public void setTotalGrossMargin(long totalGrossMargin) {
		this.totalGrossMargin = totalGrossMargin;
	}
	public long getTotalOrderNum() {
		return totalOrderNum;
	}
	public void setTotalOrderNum(long totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
	}

}
