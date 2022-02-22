package jp.co.kts.app.output.entity;

import jp.co.kts.app.common.entity.SalesItemDTO;

public class LeaveStockDTO extends SalesItemDTO {

	private String shipmentPlanDate;

	private String orderNo;

	private String warehouseNm;

	public String getShipmentPlanDate() {
		return shipmentPlanDate;
	}

	public void setShipmentPlanDate(String shipmentPlanDate) {
		this.shipmentPlanDate = shipmentPlanDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getWarehouseNm() {
		return warehouseNm;
	}

	public void setWarehouseNm(String warehouseNm) {
		this.warehouseNm = warehouseNm;
	}

}
