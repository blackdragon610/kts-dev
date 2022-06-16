package jp.co.kts.app.extendCommon.entity;

import java.util.ArrayList;
import java.util.List;

import jp.co.kts.app.common.entity.SalesSlipDTO;

public class ExtendSalesSlipDTO extends SalesSlipDTO {

	private String channelNm;

	private String corporationNm;

	private String orderFullNm;

	private String orderFullNmKana;

	private String destinationFullNm;

	private String destinationFullNmKana;

	private String editRemarks;

	private String orderRemarksMemo;

	private long sysItemId;

	private List<ExtendSalesItemDTO> pickItemList = new ArrayList<>();
	
	private List<ExtendSalesItemDTO> saleItemList = new ArrayList<>();

	private int notItemCount;

	/** システム売上商品ID */
	private long sysSalesItemId;

	/** 品番 */
	private String itemCode;

	/** 商品名 */
	private String itemNm;

	/** 注文数 */
	private int orderNum;

	/** 単価 */
	private int pieceRate;

	/** 原価 */
	private int cost;

	/**
	 * 商品種別金額
	 */
	private int etcPrice;

	/**
	 * 商品種別金額
	 */
	private String etcStr;

	/** (表示用)注文者郵便番号 */
	private String orderZipDisp;

	/** (表示用)お届け先郵便番号 */
	private String destinationZipDisp;

	private long grossMargin;

	/** 元の受注番号 */
	private String originOrderNo;

	/** （表示用）楽天倉庫フラグ */
	private String RslLeaveFlag;

	/** 外部倉庫コード */
	private String externalWarehouseCode;


	public String getExternalWarehouseCode() {
		return externalWarehouseCode;
	}
	public void setExternalWarehouseCode(String externalWarehouseCode) {
		this.externalWarehouseCode = externalWarehouseCode;
	}

	public ExtendSalesSlipDTO() {

	}
	/**
	 * String型をからで初期化するコンストラクター
	 * @param empty
	 */
	public ExtendSalesSlipDTO(String empty) {

		setOrderNo(empty);

		setOrderDate(empty);

		setOrderTime(empty);

		setOrderFamilyNm(empty);

		setOrderFirstNm(empty);

		setOrderFamilyNmKana(empty);

		setOrderFirstNmKana(empty);

		setOrderMailAddress(empty);

		setOrderZip(empty);

		setOrderPrefectures(empty);

		setOrderMunicipality(empty);

		setOrderAddress(empty);

		setOrderBuildingNm(empty);

		setOrderCompanyNm(empty);

		setOrderQuarter(empty);

		setOrderTel(empty);

		setAccountMethod(empty);

		setOrderRemarks(empty);

		setMenberNo(empty);

		setDepositDate(empty);

		setDestinationFamilyNm(empty);

		setDestinationFirstNm(empty);

		setDestinationFamilyNmKana(empty);

		setDestinationFirstNmKana(empty);

		setDestinationZip(empty);

		setDestinationPrefectures(empty);

		setDestinationMunicipality(empty);

		setDestinationAddress(empty);

		setDestinationBuildingNm(empty);

		setDestinationCompanyNm(empty);

		setDestinationQuarter(empty);

		setDestinationTel(empty);

		setSenderMemo(empty);

		setInvoiceClassification(empty);

		setSlipNo(empty);

		setDestinationAppointDate(empty);

		setDestinationAppointTime(empty);

		setShipmentPlanDate(empty);

		setTransportCorporationSystem(empty);

		setStatus(empty);

		setDisposalRoute(empty);

		setDeliveryRemarks(empty);

		setPickingListFlg(empty);

		setLeavingFlg(empty);

		setReturnFlg(empty);

		channelNm = empty;

		corporationNm = empty;

		orderFullNm = empty;

		orderFullNmKana = empty;

		destinationFullNm = empty;

		destinationFullNmKana = empty;

		editRemarks = empty;

		orderRemarksMemo = empty;
	}
	public String getChannelNm() {
		return channelNm;
	}

	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}

	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

	public String getOrderFullNm() {
		return orderFullNm;
	}

	public void setOrderFullNm(String orderFullNm) {
		this.orderFullNm = orderFullNm;
	}

	public String getOrderFullNmKana() {
		return orderFullNmKana;
	}

	public void setOrderFullNmKana(String orderFullNmKana) {
		this.orderFullNmKana = orderFullNmKana;
	}

	public String getDestinationFullNm() {
		return destinationFullNm;
	}

	public void setDestinationFullNm(String destinationFullNm) {
		this.destinationFullNm = destinationFullNm;
	}

	public String getDestinationFullNmKana() {
		return destinationFullNmKana;
	}

	public void setDestinationFullNmKana(String destinationFullNmKana) {
		this.destinationFullNmKana = destinationFullNmKana;
	}

	public String getEditRemarks() {
		return editRemarks;
	}

	public void setEditRemarks(String editRemarks) {
		this.editRemarks = editRemarks;
	}

	/**
	 * @return orderRemarksMemo
	 */
	public String getOrderRemarksMemo() {
		return orderRemarksMemo;
	}
	/**
	 * @param orderRemarksMemo セットする orderRemarksMemo
	 */
	public void setOrderRemarksMemo(String orderRemarksMemo) {
		this.orderRemarksMemo = orderRemarksMemo;
	}
	public long getSysItemId() {
		return sysItemId;
	}

	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	public int getEtcPrice() {
		return etcPrice;
	}

	public void setEtcPrice(int etcPrice) {
		this.etcPrice = etcPrice;
	}

	public String getEtcStr() {
		return etcStr;
	}

	public void setEtcStr(String etcStr) {
		this.etcStr = etcStr;
	}
	/**
	 * @return pickItemList
	 */
	public List<ExtendSalesItemDTO> getPickItemList() {
		return pickItemList;
	}
	/**
	 * @param pickItemList セットする pickItemList
	 */
	public void setPickItemList(List<ExtendSalesItemDTO> pickItemList) {
		this.pickItemList = pickItemList;
	}
	public int getNotItemCount() {
		return notItemCount;
	}
	public void setNotItemCount(int notItemCount) {
		this.notItemCount = notItemCount;
	}
	public long getSysSalesItemId() {
		return sysSalesItemId;
	}
	public void setSysSalesItemId(long sysSalesItemId) {
		this.sysSalesItemId = sysSalesItemId;
	}
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
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getPieceRate() {
		return pieceRate;
	}
	public void setPieceRate(int pieceRate) {
		this.pieceRate = pieceRate;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getOrderZipDisp() {
		return orderZipDisp;
	}
	public void setOrderZipDisp(String orderZipDisp) {
		this.orderZipDisp = orderZipDisp;
	}
	public String getDestinationZipDisp() {
		return destinationZipDisp;
	}
	public void setDestinationZipDisp(String destinationZipDisp) {
		this.destinationZipDisp = destinationZipDisp;
	}
	public long getGrossMargin() {
		return grossMargin;
	}
	public void setGrossMargin(long grossMargin) {
		this.grossMargin = grossMargin;
	}

	/**
	 * 元の受注番号を取得します。
	 * @return originOrderNo
	 */
	public String getOriginOrderNo() {
		return originOrderNo;
	}

	/**
	 * 元の受注番号を設定します。
	 * @param originOrderNo セットする originOrderNo
	 */
	public void setOriginOrderNo(String originOrderNo) {
		this.originOrderNo = originOrderNo;
	}

	/**
	 * 楽天倉庫フラグを取得します。
	 * @return
	 */
	public String getRslLeaveFlag() {
		return RslLeaveFlag;
	}
	/**
	 * 楽天倉庫フラグを設定します。
	 * @param rslLeaveFlag
	 */
	public void setRslLeaveFlag(String rslLeaveFlag) {
		RslLeaveFlag = rslLeaveFlag;
	}
	/**
	 * @return the saleItemList
	 */
	public List<ExtendSalesItemDTO> getSaleItemList() {
		return saleItemList;
	}
	/**
	 * @param saleItemList the saleItemList to set
	 */
	public void setSaleItemList(List<ExtendSalesItemDTO> saleItemList) {
		this.saleItemList = saleItemList;
	}

}