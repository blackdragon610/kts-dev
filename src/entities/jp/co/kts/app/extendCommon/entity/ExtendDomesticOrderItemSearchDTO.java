package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.DomesticExhibitionDTO;

public class ExtendDomesticOrderItemSearchDTO extends DomesticExhibitionDTO{

	/** 数量 */
	private long orderNum;

	/** 定価 */
	private long listPrice;

	/** 注文備考 */
	private String orderRemarks;

	/** 入荷予定日 */
	private String arrivalScheduleDate;

	/** 構成数量 */
	private long itemNum;

	public long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}

	public long getListPrice() {
		return listPrice;
	}

	public void setListPrice(long listPrice) {
		this.listPrice = listPrice;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setListRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	/**
	 * @return arrivalScheduleDate
	 */
	public String getArrivalScheduleDate() {
		return arrivalScheduleDate;
	}

	/**
	 * @param arrivalScheduleDate セットする arrivalScheduleDate
	 */
	public void setArrivalScheduleDate(String arrivalScheduleDate) {
		this.arrivalScheduleDate = arrivalScheduleDate;
	}

	/**
	 * @return itemNum
	 */
	public long getItemNum() {
		return itemNum;
	}

	/**
	 * @param itemNum セットする itemNum
	 */
	public void setItemNum(long itemNum) {
		this.itemNum = itemNum;
	}
}
