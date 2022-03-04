/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.app.common.entity;

/**
 * 国内注文書作成情報を格納します。
 *
 * @author n_nozawa
 * 20161201
 */
public class DomesticOrderSlipDTO {

	/** システム伝票ID */
	private long sysDomesticSlipId;

	/** 国内システムインポートID */
	private long sysDomesticimportId;
	
	/** 注文書No */
	private long purchaseOrderNo;

	/** 注文日 */
	private String itemOrderDate;

	/** システム法人ID */
	private long sysCorporationId;

	/** モール */
	private String mall;

	/** 受注番号 */
	private String orderNo;

	/** 注番 */
	private String noteTurn;

	/** MEMO */
	private String senderRemarks;

	/** システム倉庫ID */
	private long sysWarehouseId;

	/** 倉庫名 */
	private String warehouseNm;

	/** 郵便番号 */
	private String zip;

	/** 住所1 */
	private String addressFst;

	/** 住所2 */
	private String addressNxt;

	/** 住所3 */
	private String addressNxt2;

	/** 電話番号 */
	private String tellNo;

	/** 氏名 */
	private String logisticNm;

	/** 削除フラグ */
	private String deleteFlag;

	/** 登録日 */
	private String createDate;

	/** 登録者ID */
	private int createUserId;

	/** 更新日 */
	private String updateDate;

	/** 更新者ID */
	private int updateDateUserId;

	/** 削除対象ID */
	private String deleteCheckFlg;

	/** 注文書作製日 */
	private String orderSlipDate;

	/** 印刷確認フラグ */
	private String printCheckFlag;


	public long getSysDomesticSlipId() {
		return sysDomesticSlipId;
	}

	public void setSysDomesticSlipId(long sysDomesticSlipId) {
		this.sysDomesticSlipId = sysDomesticSlipId;
	}

	public long getSysDomesticimportId() {
		return sysDomesticimportId;
	}

	public void setSysDomesticimportId(long sysDomesticimportId) {
		this.sysDomesticimportId = sysDomesticimportId;
	}

	/**
	 * 注文書Noを取得します。
	 * @return 注文書No
	 */
	public long getPurchaseOrderNo() {
	    return purchaseOrderNo;
	}

	/**
	 * 注文書Noを設定します。
	 * @param string 注文書No
	 */
	public void setPurchaseOrderNo(long purchaseOrderNo) {
	    this.purchaseOrderNo = purchaseOrderNo;
	}

	public String getItemOrderDate() {
		return itemOrderDate;
	}

	public void setItemOrderDate(String itemOrderDate) {
		this.itemOrderDate = itemOrderDate;
	}

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public String getMall() {
		return mall;
	}

	public void setMall(String mall) {
		this.mall = mall;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getNoteTurn() {
		return noteTurn;
	}

	public void setNoteTurn(String noteTurn) {
		this.noteTurn = noteTurn;
	}

	public long getSysWarehouseId() {
		return sysWarehouseId;
	}

	public void setSysWarehouseId(long sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}



	public String getSenderRemarks() {
		return senderRemarks;
	}

	public void setSenderRemarks(String senderRemarks) {
		this.senderRemarks = senderRemarks;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getUpdateDateUserId() {
		return updateDateUserId;
	}

	public void setUpdateDateUserId(int updateDateUserId) {
		this.updateDateUserId = updateDateUserId;
	}

	public String getDeleteCheckFlg() {
		return deleteCheckFlg;
	}

	public void setDeleteCheckFlg(String deleteCheckFlg) {
		this.deleteCheckFlg = deleteCheckFlg;
	}

	public String getOrderSlipDate() {
		return orderSlipDate;
	}

	public void setOrderSlipDate(String orderSlipDate) {
		this.orderSlipDate = orderSlipDate;
	}

	/**
	 * @return addressFst
	 */
	public String getAddressFst() {
		return addressFst;
	}

	/**
	 * @param addressFst セットする addressFst
	 */
	public void setAddressFst(String addressFst) {
		this.addressFst = addressFst;
	}

	/**
	 * @return addressNxt
	 */
	public String getAddressNxt() {
		return addressNxt;
	}

	/**
	 * @param addressNxt セットする addressNxt
	 */
	public void setAddressNxt(String addressNxt) {
		this.addressNxt = addressNxt;
	}

	/**
	 * @return addressNxt2
	 */
	public String getAddressNxt2() {
		return addressNxt2;
	}

	/**
	 * @param addressNxt2 セットする addressNxt2
	 */
	public void setAddressNxt2(String addressNxt2) {
		this.addressNxt2 = addressNxt2;
	}

	/**
	 * @return tellNo
	 */
	public String getTellNo() {
		return tellNo;
	}

	/**
	 * @param tellNo セットする tellNo
	 */
	public void setTellNo(String tellNo) {
		this.tellNo = tellNo;
	}

	/**
	 * @return logisticNm
	 */
	public String getLogisticNm() {
		return logisticNm;
	}

	/**
	 * @param logisticNm セットする logisticNm
	 */
	public void setLogisticNm(String logisticNm) {
		this.logisticNm = logisticNm;
	}

	/**
	 * @return warehouseNm
	 */
	public String getWarehouseNm() {
		return warehouseNm;
	}

	/**
	 * @param warehouseNm セットする warehouseNm
	 */
	public void setWarehouseNm(String warehouseNm) {
		this.warehouseNm = warehouseNm;
	}

	/**
	 * 印刷確認フラグを取得します。
	 * @return 印刷確認フラグ
	 */
	public String getPrintCheckFlag() {
	    return printCheckFlag;
	}

	/**
	 * 印刷確認フラグを設定します。
	 * @param printCheckFlag 印刷確認フラグ
	 */
	public void setPrintCheckFlag(String printCheckFlag) {
	    this.printCheckFlag = printCheckFlag;
	}


}
