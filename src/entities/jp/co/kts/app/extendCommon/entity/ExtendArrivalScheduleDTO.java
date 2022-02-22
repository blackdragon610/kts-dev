package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.ArrivalScheduleDTO;

public class ExtendArrivalScheduleDTO extends ArrivalScheduleDTO {

	/** 品番 */
	private String itemCode;

	/** 商品名 */
	private String itemNm;

	/** 入荷予定数 */
	private int arrivalScheduleNum;

	/** 倉庫ID */
	private long sysWarehouseId;

	/** 注文No */
	private String poNo;

	/** 入荷日 */
	private String arrivalDate;

	/** 入荷状態  */
	private String arrivalStatus;

	/**注文ステータス名*/
	private String orderStatusNm;

	/**
	 * 品番を取得します。
	 * @return 品番
	 */
	public String getItemCode() {
	    return itemCode;
	}

	/**
	 * 品番を設定します。
	 * @param itemCode 品番
	 */
	public void setItemCode(String itemCode) {
	    this.itemCode = itemCode;
	}

	/**
	 * 商品名を取得します。
	 * @return 商品名
	 */
	public String getItemNm() {
	    return itemNm;
	}

	/**
	 * 商品名を設定します。
	 * @param itemNm 商品名
	 */
	public void setItemNm(String itemNm) {
	    this.itemNm = itemNm;
	}

	/**
	 * 入荷予定数を取得します。
	 * @return 入荷予定数
	 */
	public int getArrivalScheduleNum() {
	    return arrivalScheduleNum;
	}

	/**
	 * 入荷予定数を設定します。
	 * @param arrivalScheduleNum 入荷予定数
	 */
	public void setArrivalScheduleNum(int arrivalScheduleNum) {
	    this.arrivalScheduleNum = arrivalScheduleNum;
	}

	/**
	 * 倉庫IDを取得します。
	 * @return 倉庫ID
	 */
	public long getSysWarehouseId() {
	    return sysWarehouseId;
	}

	/**
	 * 倉庫IDを設定します。
	 * @param sysWarehouseId 倉庫ID
	 */
	public void setSysWarehouseId(long sysWarehouseId) {
	    this.sysWarehouseId = sysWarehouseId;
	}

	/**
	 * 注文Noを取得します。
	 * @return 注文No
	 */
	public String getPoNo() {
	    return poNo;
	}

	/**
	 * 注文Noを設定します。
	 * @param poNo 注文No
	 */
	public void setPoNo(String poNo) {
	    this.poNo = poNo;
	}

	/**
	 * 入荷日を取得します。
	 * @return 入荷日
	 */
	public String getArrivalDate() {
	    return arrivalDate;
	}

	/**
	 * 入荷日を設定します。
	 * @param arrivalDate 入荷日
	 */
	public void setArrivalDate(String arrivalDate) {
	    this.arrivalDate = arrivalDate;
	}

	/**
	 * @return arrivalStatus
	 */
	public String getArrivalStatus() {
		return arrivalStatus;
	}

	/**
	 * @param arrivalStatus セットする arrivalStatus
	 */
	public void setArrivalStatus(String arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	/**
	 * 注文ステータス名を取得します。
	 * @return 注文ステータス名
	 */
	public String getOrderStatusNm() {
	    return orderStatusNm;
	}

	/**
	 * 注文ステータス名を設定します。
	 * @param orderStatusNm 注文ステータス名
	 */
	public void setOrderStatusNm(String orderStatusNm) {
	    this.orderStatusNm = orderStatusNm;
	}


}
