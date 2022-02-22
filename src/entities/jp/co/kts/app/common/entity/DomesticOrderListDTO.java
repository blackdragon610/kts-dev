package jp.co.kts.app.common.entity;

public class DomesticOrderListDTO extends DomesticOrderItemDTO{


	/** システム伝票ID */
	private long domesticSlipId;

	/** 注文書No */
	private String purchaseOrderNo;

	/** 注文日 */
	private String itemOrderDate;

	/** 注文書作成日 */
	private String orderSlipDate;

	/** システム法人ID */
	private long sysCorporationId;

	/** モール */
	private String mall;

	/** 受注番号 */
	private String orderNo;

	/** 注番 */
	private String noteTurn;

	/** システム倉庫ID */
	private long sysWarehouseId;

	/** MEMO */
	private String senderRemarks;

	/** 項目削除フラグ */
	private String orderCheckFlg;

	/** メーカー名 */
	private String makerNm;

	/** ステータス名 */
	private String statusNm;

	/** オープン価格フラグ */
	private String openPriceFlg;

	/** ステータスID */
	private String statusId;

	/** 原価確認表示値 */
	private String costComfFlagNm;

	/** 国内注文伝票Id：文字列用 */
	private String strSysDomesticSlipId;

	/** 伝票削除対象商品数カウンタ */
	private int deleteCnt;

	/** 掛率 */
	private long itemRateOver;

	/** 印刷確認フラグ */
	private String printCheckFlag;

	/**納入先名*/
	private String deliveryNm;

	/** システム管理Id */
	private long sysManagementId;


	/**
	 * システム伝票IDを取得します。
	 * @return システム伝票ID
	 */
	public long getDomesticSlipId() {
	    return domesticSlipId;
	}

	/**
	 * システム伝票IDを設定します。
	 * @param domesticSlipId システム伝票ID
	 */
	public void setDomesticSlipId(long domesticSlipId) {
	    this.domesticSlipId = domesticSlipId;
	}

	/**
	 * 注文書Noを取得します。
	 * @return 注文書No
	 */
	public String getPurchaseOrderNo() {
	    return purchaseOrderNo;
	}

	/**
	 * 注文書Noを設定します。
	 * @param purchaseOrderNo 注文書No
	 */
	public void setPurchaseOrderNo(String purchaseOrderNo) {
	    this.purchaseOrderNo = purchaseOrderNo;
	}

	/**
	 * 注文日を取得します。
	 * @return 注文日
	 */
	public String getItemOrderDate() {
	    return itemOrderDate;
	}

	/**
	 * 注文日を設定します。
	 * @param itemOrderDate 注文日
	 */
	public void setItemOrderDate(String itemOrderDate) {
	    this.itemOrderDate = itemOrderDate;
	}

	/**
	 * 注文書作成日を取得します。
	 * @return 注文書作成日
	 */
	public String getOrderSlipDate() {
	    return orderSlipDate;
	}

	/**
	 * 注文書作成日を設定します。
	 * @param orderSlipDate 注文書作成日
	 */
	public void setOrderSlipDate(String orderSlipDate) {
	    this.orderSlipDate = orderSlipDate;
	}

	/**
	 * システム法人IDを取得します。
	 * @return システム法人ID
	 */
	public long getSysCorporationId() {
	    return sysCorporationId;
	}

	/**
	 * システム法人IDを設定します。
	 * @param sysCorporationId システム法人ID
	 */
	public void setSysCorporationId(long sysCorporationId) {
	    this.sysCorporationId = sysCorporationId;
	}

	/**
	 * モールを取得します。
	 * @return モール
	 */
	public String getMall() {
	    return mall;
	}

	/**
	 * モールを設定します。
	 * @param mall モール
	 */
	public void setMall(String mall) {
	    this.mall = mall;
	}

	/**
	 * 受注番号を取得します。
	 * @return 受注番号
	 */
	public String getOrderNo() {
	    return orderNo;
	}

	/**
	 * 受注番号を設定します。
	 * @param orderNo 受注番号
	 */
	public void setOrderNo(String orderNo) {
	    this.orderNo = orderNo;
	}

	/**
	 * 注番を取得します。
	 * @return 注番
	 */
	public String getNoteTurn() {
	    return noteTurn;
	}

	/**
	 * 注番を設定します。
	 * @param noteTurn 注番
	 */
	public void setNoteTurn(String noteTurn) {
	    this.noteTurn = noteTurn;
	}

	/**
	 * システム倉庫IDを取得します。
	 * @return システム倉庫ID
	 */
	public long getSysWarehouseId() {
	    return sysWarehouseId;
	}

	/**
	 * システム倉庫IDを設定します。
	 * @param sysWarehouseId システム倉庫ID
	 */
	public void setSysWarehouseId(long sysWarehouseId) {
	    this.sysWarehouseId = sysWarehouseId;
	}

	/**
	 * MEMOを取得します。
	 * @return MEMO
	 */
	public String getSenderRemarks() {
	    return senderRemarks;
	}

	/**
	 * MEMOを設定します。
	 * @param senderRemarks MEMO
	 */
	public void setSenderRemarks(String senderRemarks) {
	    this.senderRemarks = senderRemarks;
	}

	/**
	 * 項目削除フラグを取得します。
	 * @return 項目削除フラグ
	 */
	public String getOrderCheckFlg() {
	    return orderCheckFlg;
	}

	/**
	 * 項目削除フラグを設定します。
	 * @param orderCheckFlg 項目削除フラグ
	 */
	public void setOrderCheckFlg(String orderCheckFlg) {
	    this.orderCheckFlg = orderCheckFlg;
	}

	/**
	 * メーカー名を取得します。
	 * @return メーカー名
	 */
	public String getMakerNm() {
	    return makerNm;
	}

	/**
	 * メーカー名を設定します。
	 * @param makerNm メーカー名
	 */
	public void setMakerNm(String makerNm) {
	    this.makerNm = makerNm;
	}

	/**
	 * ステータス名を取得します。
	 * @return ステータス名
	 */
	public String getStatusNm() {
	    return statusNm;
	}

	/**
	 * ステータス名を設定します。
	 * @param statusNm ステータス名
	 */
	public void setStatusNm(String statusNm) {
	    this.statusNm = statusNm;
	}

	/**
	 * @return openPriceFlg
	 */
	public String getOpenPriceFlg() {
		return openPriceFlg;
	}

	/**
	 * @param openPriceFlg セットする openPriceFlg
	 */
	public void setOpenPriceFlg(String openPriceFlg) {
		this.openPriceFlg = openPriceFlg;
	}

	/**
	 * ステータスIDを取得します。
	 * @return ステータスID
	 */
	public String getStatusId() {
	    return statusId;
	}

	/**
	 * ステータスIDを設定します。
	 * @param statusId ステータスID
	 */
	public void setStatusId(String statusId) {
	    this.statusId = statusId;
	}

	/**
	 * 原価確認表示値を取得します。
	 * @return 原価確認表示値
	 */
	public String getCostComfFlagNm() {
	    return costComfFlagNm;
	}

	/**
	 * 原価確認表示値を設定します。
	 * @param costComfFlagNm 原価確認表示値
	 */
	public void setCostComfFlagNm(String costComfFlagNm) {
	    this.costComfFlagNm = costComfFlagNm;
	}

	/**
	 * 国内注文伝票Id：文字列用を取得します。
	 * @return 国内注文伝票Id：文字列用
	 */
	public String getStrSysDomesticSlipId() {
	    return strSysDomesticSlipId;
	}

	/**
	 * 国内注文伝票Id：文字列用を設定します。
	 * @param strSysDomesticSlipId 国内注文伝票Id：文字列用
	 */
	public void setStrSysDomesticSlipId(String strSysDomesticSlipId) {
	    this.strSysDomesticSlipId = strSysDomesticSlipId;
	}

	/**
	 * 伝票削除対象商品数カウンタを取得します。
	 * @return 伝票削除対象商品数カウンタ
	 */
	public int getDeleteCnt() {
	    return deleteCnt;
	}

	/**
	 * 伝票削除対象商品数カウンタを設定します。
	 * @param deleteCnt 伝票削除対象商品数カウンタ
	 */
	public void setDeleteCnt(int deleteCnt) {
	    this.deleteCnt = deleteCnt;
	}

	/**
	 * 掛率を取得します。
	 * @return 掛率
	 */
	public long getItemRateOver() {
	    return itemRateOver;
	}

	/**
	 * 掛率を設定します。
	 * @param itemRateOver 掛率
	 */
	public void setItemRateOver(long itemRateOver) {
	    this.itemRateOver = itemRateOver;
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

	/**
	 * 納入先名を取得します。
	 * @return 納入先名
	 */
	public String getDeliveryNm() {
	    return deliveryNm;
	}

	/**
	 * 納入先名を設定します。
	 * @param deliveryNm 納入先名
	 */
	public void setDeliveryNm(String deliveryNm) {
	    this.deliveryNm = deliveryNm;
	}

	/**
	 * @return sysManagementId
	 */
	public long getSysManagementId() {
		return sysManagementId;
	}

	/**
	 * @param sysManagementId セットする sysManagementId
	 */
	public void setSysManagementId(long sysManagementId) {
		this.sysManagementId = sysManagementId;
	}

}
