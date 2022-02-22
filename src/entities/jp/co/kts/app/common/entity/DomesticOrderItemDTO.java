/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.app.common.entity;

/**
 * 国内注文商品情報を格納します。
 *
 * @author n_nozawa
 * 20161209
 */
public class DomesticOrderItemDTO {


	/** システム国内商品ID */
	private long sysDomesticItemId;

	/** システム伝票ID */
	private long sysDomesticSlipId;

	/** 管理品番 */
	private String managementCode;

	/** 数量 */
	private long orderNum;

	/** 問屋ID */
	private long wholsesalerId;

	/** 問屋名 */
	private String wholsesalerNm;

	/** 注文備考 */
	private String orderRemarks;

	/** 一覧備考 */
	private String listRemarks;

	/** 入荷予定日 */
	private String arrivalScheduleDate;

	/** 入荷日 */
	private String arrivalDate;

	/** メーカーID */
	private long makerId;

	/** メーカー品番 */
	private String makerCode;

	/** 商品名 */
	private String itemNm;

	/** 通番 */
	private String serealNum;

	/** ステータス */
	private String status;

	/** 入荷担当 */
	private String stockCharge;

	/** 注文担当 */
	private String orderCharge;

	/** 仕入原価 */
	private long purchasingCost;

	/** 定価 */
	private long listPrice;

	/** 送料 */
	private long postage;

	/** 対応者 */
	private String personInCharge;

	/** 対応日 */
	private String chargeDate;

	/** 対応 */
	private String correspondence;

	/** 原価確認FLAG */
	private String costComfFlag;

	/** 削除フラグ */
	private String deleteFlag;

	/** 登録日 */
	private String createDate;

	/** 登録者ID */
	private int createUserId;

	/** 更新者ID */
	private int updateDateUserId;

	/** 削除対象ID */
	private String deleteCheckFlg;

	/** 商品タイプ／0：通常／1：セット商品／2：構成商品 */
	private String ItemType = "0";

	/** セット商品ID */
	private long setSysDomesticItemId;


	/**
	 * システム国内商品IDを取得します。
	 * @return システム国内商品ID
	 */
	public long getSysDomesticItemId() {
	    return sysDomesticItemId;
	}

	/**
	 * システム国内商品IDを設定します。
	 * @param sysDomesticItemId システム国内商品ID
	 */
	public void setSysDomesticItemId(long sysDomesticItemId) {
	    this.sysDomesticItemId = sysDomesticItemId;
	}

	/**
	 * システム伝票IDを取得します。
	 * @return システム伝票ID
	 */
	public long getSysDomesticSlipId() {
	    return sysDomesticSlipId;
	}

	/**
	 * システム伝票IDを設定します。
	 * @param sysDomesticSlipId システム伝票ID
	 */
	public void setSysDomesticSlipId(long sysDomesticSlipId) {
	    this.sysDomesticSlipId = sysDomesticSlipId;
	}

	/**
	 * 国内商品コードを取得します。
	 * @return 国内商品コード
	 */
	public String getManagementCode() {
	    return managementCode;
	}

	/**
	 * 国内商品コードを設定します。
	 * @param managementCode 国内商品コード
	 */
	public void setManagementCode(String managementCode) {
	    this.managementCode = managementCode;
	}

	/**
	 * 数量を取得します。
	 * @return 数量
	 */
	public long getOrderNum() {
	    return orderNum;
	}

	/**
	 * 数量を設定します。
	 * @param orderNum 数量
	 */
	public void setOrderNum(long orderNum) {
	    this.orderNum = orderNum;
	}

	/**
	 * 問屋IDを取得します。
	 * @return 問屋ID
	 */
	public long getWholsesalerId() {
	    return wholsesalerId;
	}

	/**
	 * 問屋IDを設定します。
	 * @param wholsesalerId 問屋ID
	 */
	public void setWholsesalerId(long wholsesalerId) {
	    this.wholsesalerId = wholsesalerId;
	}

	/**
	 * 問屋名を取得します。
	 * @return 問屋名
	 */
	public String getWholsesalerNm() {
	    return wholsesalerNm;
	}

	/**
	 * 問屋名を設定します。
	 * @param wholsesalerNm 問屋名
	 */
	public void setWholsesalerNm(String wholsesalerNm) {
	    this.wholsesalerNm = wholsesalerNm;
	}

	/**
	 * 注文備考を取得します。
	 * @return 注文備考
	 */
	public String getOrderRemarks() {
	    return orderRemarks;
	}

	/**
	 * 注文備考を設定します。
	 * @param orderRemarks 注文備考
	 */
	public void setOrderRemarks(String orderRemarks) {
	    this.orderRemarks = orderRemarks;
	}

	/**
	 * 一覧備考を取得します。
	 * @return 一覧備考
	 */
	public String getListRemarks() {
	    return listRemarks;
	}

	/**
	 * 一覧備考を設定します。
	 * @param listRemarks 一覧備考
	 */
	public void setListRemarks(String listRemarks) {
	    this.listRemarks = listRemarks;
	}

	/**
	 * 入荷予定日を取得します。
	 * @return 入荷予定日
	 */
	public String getArrivalScheduleDate() {
	    return arrivalScheduleDate;
	}

	/**
	 * 入荷予定日を設定します。
	 * @param arrivalScheduleDate 入荷予定日
	 */
	public void setArrivalScheduleDate(String arrivalScheduleDate) {
	    this.arrivalScheduleDate = arrivalScheduleDate;
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
	 * メーカーIDを取得します。
	 * @return メーカーID
	 */
	public long getMakerId() {
	    return makerId;
	}

	/**
	 * メーカーIDを設定します。
	 * @param makerId メーカーID
	 */
	public void setMakerId(long makerId) {
	    this.makerId = makerId;
	}

	/**
	 * メーカー品番を取得します。
	 * @return メーカー品番
	 */
	public String getMakerCode() {
	    return makerCode;
	}

	/**
	 * メーカー品番を設定します。
	 * @param makerCode メーカー品番
	 */
	public void setMakerCode(String makerCode) {
	    this.makerCode = makerCode;
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
	 * 通番を取得します。
	 * @return 通番
	 */
	public String getSerealNum() {
	    return serealNum;
	}

	/**
	 * 通番を設定します。
	 * @param serealNum 通番
	 */
	public void setSerealNum(String serealNum) {
	    this.serealNum = serealNum;
	}

	/**
	 * ステータスを取得します。
	 * @return ステータス
	 */
	public String getStatus() {
	    return status;
	}

	/**
	 * ステータスを設定します。
	 * @param status ステータス
	 */
	public void setStatus(String status) {
	    this.status = status;
	}

	/**
	 * 入荷担当を取得します。
	 * @return 入荷担当
	 */
	public String getStockCharge() {
	    return stockCharge;
	}

	/**
	 * 入荷担当を設定します。
	 * @param stockCharge 入荷担当
	 */
	public void setStockCharge(String stockCharge) {
	    this.stockCharge = stockCharge;
	}

	/**
	 * 注文担当を取得します。
	 * @return 注文担当
	 */
	public String getOrderCharge() {
	    return orderCharge;
	}

	/**
	 * 注文担当を設定します。
	 * @param orderCharge 注文担当
	 */
	public void setOrderCharge(String orderCharge) {
	    this.orderCharge = orderCharge;
	}

	/**
	 * 仕入原価を取得します。
	 * @return 仕入原価
	 */
	public long getPurchasingCost() {
	    return purchasingCost;
	}

	/**
	 * 仕入原価を設定します。
	 * @param purchasingCost 仕入原価
	 */
	public void setPurchasingCost(long purchasingCost) {
	    this.purchasingCost = purchasingCost;
	}

	/**
	 * 定価を取得します。
	 * @return 定価
	 */
	public long getListPrice() {
	    return listPrice;
	}

	/**
	 * 定価を設定します。
	 * @param listPrice 定価
	 */
	public void setListPrice(long listPrice) {
	    this.listPrice = listPrice;
	}

	/**
	 * @return postage
	 */
	public long getPostage() {
		return postage;
	}

	/**
	 * @param postage セットする postage
	 */
	public void setPostage(long postage) {
		this.postage = postage;
	}

	/**
	 * 対応者を取得します。
	 * @return 対応者
	 */
	public String getPersonInCharge() {
	    return personInCharge;
	}

	/**
	 * 対応者を設定します。
	 * @param personInCharge 対応者
	 */
	public void setPersonInCharge(String personInCharge) {
	    this.personInCharge = personInCharge;
	}

	/**
	 * 対応日を取得します。
	 * @return 対応日
	 */
	public String getChargeDate() {
	    return chargeDate;
	}

	/**
	 * 対応日を設定します。
	 * @param chargeDate 対応日
	 */
	public void setChargeDate(String chargeDate) {
	    this.chargeDate = chargeDate;
	}

	/**
	 * 対応を取得します。
	 * @return 対応
	 */
	public String getCorrespondence() {
	    return correspondence;
	}

	/**
	 * 対応を設定します。
	 * @param correspondence 対応
	 */
	public void setCorrespondence(String correspondence) {
	    this.correspondence = correspondence;
	}

	/**
	 * 原価確認FLAGを取得します。
	 * @return 原価確認FLAG
	 */
	public String getCostComfFlag() {
	    return costComfFlag;
	}

	/**
	 * 原価確認FLAGを設定します。
	 * @param costComfFlag 原価確認FLAG
	 */
	public void setCostComfFlag(String costComfFlag) {
	    this.costComfFlag = costComfFlag;
	}

	/**
	 * 削除フラグを取得します。
	 * @return 削除フラグ
	 */
	public String getDeleteFlag() {
	    return deleteFlag;
	}

	/**
	 * 削除フラグを設定します。
	 * @param deleteFlag 削除フラグ
	 */
	public void setDeleteFlag(String deleteFlag) {
	    this.deleteFlag = deleteFlag;
	}

	/**
	 * 登録日を取得します。
	 * @return 登録日
	 */
	public String getCreateDate() {
	    return createDate;
	}

	/**
	 * 登録日を設定します。
	 * @param createDate 登録日
	 */
	public void setCreateDate(String createDate) {
	    this.createDate = createDate;
	}

	/**
	 * 登録者IDを取得します。
	 * @return 登録者ID
	 */
	public int getCreateUserId() {
	    return createUserId;
	}

	/**
	 * 登録者IDを設定します。
	 * @param createUserId 登録者ID
	 */
	public void setCreateUserId(int createUserId) {
	    this.createUserId = createUserId;
	}

	/**
	 * 更新者IDを取得します。
	 * @return 更新者ID
	 */
	public int getUpdateDateUserId() {
	    return updateDateUserId;
	}

	/**
	 * 更新者IDを設定します。
	 * @param updateDateUserId 更新者ID
	 */
	public void setUpdateDateUserId(int updateDateUserId) {
	    this.updateDateUserId = updateDateUserId;
	}

	/**
	 * 削除対象IDを取得します。
	 * @return 削除対象ID
	 */
	public String getDeleteCheckFlg() {
	    return deleteCheckFlg;
	}

	/**
	 * 削除対象IDを設定します。
	 * @param deleteCheckFlg 削除対象ID
	 */
	public void setDeleteCheckFlg(String deleteCheckFlg) {
	    this.deleteCheckFlg = deleteCheckFlg;
	}

	/**
	 * @return itemType
	 */
	public String getItemType() {
		return ItemType;
	}

	/**
	 * @param itemType セットする itemType
	 */
	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	/**
	 * @return setSysDomesticItemId
	 */
	public long getSetSysDomesticItemId() {
		return setSysDomesticItemId;
	}

	/**
	 * @param setSysDomesticItemId セットする setSysDomesticItemId
	 */
	public void setSetSysDomesticItemId(long setSysDomesticItemId) {
		this.setSysDomesticItemId = setSysDomesticItemId;
	}




}
