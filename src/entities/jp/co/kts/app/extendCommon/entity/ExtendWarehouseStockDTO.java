package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.WarehouseStockDTO;

public class ExtendWarehouseStockDTO extends WarehouseStockDTO {

	private String warehouseNm;

	private int priorityNumber;

	private String warehouseCount;

	private String itemCode;

	/** 変更前在庫数 */
	private int beforeStockNum = 0;

	/** 変更前ロケーションNO */
	private String beforeLocationNo;

	/** 変更前優先度 */
	private String beforePriority;

	/** [外部倉庫用]在庫ID */
	private long sysExternalStockId;

	/** [外部倉庫用]倉庫コード */
	private String sysExternalWarehouseCode;


	public String getWarehouseNm() {
		return warehouseNm;
	}

	public void setWarehouseNm(String warehouseNm) {
		this.warehouseNm = warehouseNm;
	}

	public int getPriorityNumber() {
		return priorityNumber;
	}

	public void setPriorityNumber(int priorityNumber) {
		this.priorityNumber = priorityNumber;
	}

	public String getWarehouseCount() {
		return warehouseCount;
	}

	public void setWarehouseCount(String warehouseCount) {
		this.warehouseCount = warehouseCount;
	}

	/**
	 * itemCodeを取得します。
	 * @return itemCode
	 */
	public String getItemCode() {
	    return itemCode;
	}

	/**
	 * itemCodeを設定します。
	 * @param itemCode itemCode
	 */
	public void setItemCode(String itemCode) {
	    this.itemCode = itemCode;
	}

	/**
	 * 変更前在庫数を取得します。
	 * @return 変更前在庫数
	 */
	public int getBeforeStockNum() {
	    return beforeStockNum;
	}

	/**
	 * 変更前在庫数を設定します。
	 * @param beforeStockNum 変更前在庫数
	 */
	public void setBeforeStockNum(int beforeStockNum) {
	    this.beforeStockNum = beforeStockNum;
	}

	/**
	 * 変更前ロケーションNOを取得します。
	 * @return 変更前ロケーションNO
	 */
	public String getBeforeLocationNo() {
	    return beforeLocationNo;
	}

	/**
	 * 変更前ロケーションNOを設定します。
	 * @param beforeLocationNo 変更前ロケーションNO
	 */
	public void setBeforeLocationNo(String beforeLocationNo) {
	    this.beforeLocationNo = beforeLocationNo;
	}

	/**
	 * 変更前優先度を取得します。
	 * @return 変更前優先度
	 */
	public String getBeforePriority() {
	    return beforePriority;
	}

	/**
	 * 変更前優先度を設定します。
	 * @param beforePriority 変更前優先度
	 */
	public void setBeforePriority(String beforePriority) {
	    this.beforePriority = beforePriority;
	}

	/**
	 * 外部倉庫コードを取得します。
	 * @return 外部倉庫コード
	 */
	public String getSysExternalWarehouseCode() {
		return sysExternalWarehouseCode;
	}

	/**
	 * 外部倉庫コードを設定します。
	 * @param externaWarehouseCode 外部倉庫コード
	 */
	public void setSysExternalWarehouseCode(String sysExternaWarehouseCode) {
		this.sysExternalWarehouseCode = sysExternaWarehouseCode;
	}

	/**
	 * 外部倉庫在庫IDを取得します。
	 * @return
	 */
	public long getSysExternalStockId() {
		return sysExternalStockId;
	}

	/**
	 * 外部倉庫在庫IDを設定します。
	 * @param sysExternalStockId
	 */
	public void setSysExternalStockId(long sysExternalStockId) {
		this.sysExternalStockId = sysExternalStockId;
	}

}
