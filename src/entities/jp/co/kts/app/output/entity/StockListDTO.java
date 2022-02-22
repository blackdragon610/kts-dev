package jp.co.kts.app.output.entity;

public class StockListDTO {

	/** システム倉庫在庫ID */
	private long sysWarehouseStockId = 0;

	/** 第一倉庫在庫数 */
	private int stockNum1 = 0;

	/** 総在庫数 */
	private int totalStockNum = 0;

	/** 総在庫数入力値 */
	private int totalStockNumInput = 0;

	/**
	 * @return sysWarehouseStockId
	 */
	public long getSysWarehouseStockId() {
		return sysWarehouseStockId;
	}

	/**
	 * @param sysWarehouseStockId セットする sysWarehouseStockId
	 */
	public void setSysWarehouseStockId(long sysWarehouseStockId) {
		this.sysWarehouseStockId = sysWarehouseStockId;
	}

	/**
	 * @return stockNum1
	 */
	public int getStockNum1() {
		return stockNum1;
	}

	/**
	 * @param stockNum1 セットする stockNum1
	 */
	public void setStockNum1(int stockNum1) {
		this.stockNum1 = stockNum1;
	}

	/**
	 * @return totalStockNum
	 */
	public int getTotalStockNum() {
		return totalStockNum;
	}

	/**
	 * @param totalStockNum セットする totalStockNum
	 */
	public void setTotalStockNum(int totalStockNum) {
		this.totalStockNum = totalStockNum;
	}

	/**
	 * @return totalStockNumInput
	 */
	public int getTotalStockNumInput() {
		return totalStockNumInput;
	}

	/**
	 * @param totalStockNumInput セットする totalStockNumInput
	 */
	public void setTotalStockNumInput(int totalStockNumInput) {
		this.totalStockNumInput = totalStockNumInput;
	}


}
