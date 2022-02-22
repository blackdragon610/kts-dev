/*
 *　著作権　　　　Copyright 2016 boncre
 *　開発システム　商品在庫・受注管理システム
 */
package jp.co.kts.app.common.entity;

/**
 * 国内出品データベース	情報を格納します。
 *
 * @author n_nozawa
 * 20161108
 */

public class DomesticExhibitionDTO {

	/** 管理ID */
	private long sysManagementId;

	/** 管理品番 */
	private String managementCode;

	/**管理品番：変更前*/
	private String beforeManagementCode;

	/** 定価 */
	private long listPrice;

	/** 掛率 */
	private double itemRateOver;

	/** メーカーID */
	private long sysMakerId;

	/** メーカー名 */
	private String makerNm;

	/** メーカー名（カナ） */
	private String makerNmKana;

	/** メーカー品番 */
	private String makerCode;

	/** 商品ID */
	private long sysItemId;

	/** 商品名 */
	private String itemNm;

	/** 問屋ID */
	private long wholsesalerId;

	/** 問屋名 */
	private String wholsesalerNm;

	/** 仕入原価 */
	private long purchasingCost;

	/** 送料 */
	private long postage;

	/** 削除フラグ */
	private char deleteFlag;

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

	/** 追加削除対象ID */
	private String removeCheckFlg;

	/**	チェックボックス */
	private String chekbox;

	/** オープン価格フラグ */
	private String openPriceFlg;

	/** セット商品フラグ */
	private String setItemFlg;

	/** 担当部署名 */
	private String departmentNm;

	/** 画面ロード時の実行処理タイプ */
	private String onloadActionType = "0";
	/**
	 * 管理IDを取得します。
	 * @return 管理ID
	 */
	public long getSysManagementId() {
	    return sysManagementId;
	}

	/**
	 * 管理IDを設定します。
	 * @param sysManagementId 管理ID
	 */
	public void setSysManagementId(long sysManagementId) {
	    this.sysManagementId = sysManagementId;
	}

	/**
	 * 管理品番を取得します。
	 * @return 管理品番
	 */
	public String getManagementCode() {
	    return managementCode;
	}

	/**
	 * 管理品番を設定します。
	 * @param managementCode 管理品番
	 */
	public void setManagementCode(String managementCode) {
	    this.managementCode = managementCode;
	}

	/**
	 * 管理品番：変更前を取得します。
	 * @return 管理品番：変更前
	 */
	public String getBeforeManagementCode() {
	    return beforeManagementCode;
	}

	/**
	 * 管理品番：変更前を設定します。
	 * @param beforeManagementCode 管理品番：変更前
	 */
	public void setBeforeManagementCode(String beforeManagementCode) {
	    this.beforeManagementCode = beforeManagementCode;
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
	 * 掛率を取得します。
	 * @return 掛率
	 */
	public double getItemRateOver() {
	    return itemRateOver;
	}

	/**
	 * 掛率を設定します。
	 * @param itemRateOver 掛率
	 */
	public void setItemRateOver(double itemRateOver) {
	    this.itemRateOver = itemRateOver;
	}

	/**
	 * メーカーIDを取得します。
	 * @return メーカーID
	 */
	public long getSysMakerId() {
	    return sysMakerId;
	}

	/**
	 * メーカーIDを設定します。
	 * @param sysMakerId メーカーID
	 */
	public void setSysMakerId(long sysMakerId) {
	    this.sysMakerId = sysMakerId;
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
	 * メーカー名（カナ）を取得します。
	 * @return メーカー名（カナ）
	 */
	public String getMakerNmKana() {
	    return makerNmKana;
	}

	/**
	 * メーカー名（カナ）を設定します。
	 * @param makerNmPhonetic メーカー名（カナ）
	 */
	public void setMakerNmPhonetic(String makerNmKana) {
	    this.makerNmKana = makerNmKana;
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
	 * 商品IDを取得します。
	 * @return 商品ID
	 */
	public long getSysItemId() {
	    return sysItemId;
	}

	/**
	 * 商品IDを設定します。
	 * @param sysItemId 商品ID
	 */
	public void setSysItemId(long sysItemId) {
	    this.sysItemId = sysItemId;
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
	 * 送料を取得します。
	 * @return 送料
	 */
	public long getPostage() {
	    return postage;
	}

	/**
	 * 送料を設定します。
	 * @param postage 送料
	 */
	public void setPostage(long postage) {
	    this.postage = postage;
	}

	/**
	 * 削除フラグを取得します。
	 * @return 削除フラグ
	 */
	public char getDeleteFlag() {
	    return deleteFlag;
	}

	/**
	 * 削除フラグを設定します。
	 * @param deleteFlag 削除フラグ
	 */
	public void setDeleteFlag(char deleteFlag) {
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
	 * 更新日を取得します。
	 * @return 更新日
	 */
	public String getUpdateDate() {
	    return updateDate;
	}

	/**
	 * 更新日を設定します。
	 * @param updateDate 更新日
	 */
	public void setUpdateDate(String updateDate) {
	    this.updateDate = updateDate;
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
	 * 追加削除対象IDを取得します。
	 * @return 追加削除対象ID
	 */
	public String getRemoveCheckFlg() {
	    return removeCheckFlg;
	}

	/**
	 * 追加削除対象IDを設定します。
	 * @param removeCheckFlg 追加削除対象ID
	 */
	public void setRemoveCheckFlg(String removeCheckFlg) {
	    this.removeCheckFlg = removeCheckFlg;
	}


	public String getChekbox() {
		return chekbox;
	}

	public void setChekbox(String chekbox) {
		this.chekbox = chekbox;
	}

	/**
	 * オープン価格フラグを取得します。
	 * @return オープン価格フラグ
	 */
	public String getOpenPriceFlg() {
	    return openPriceFlg;
	}

	/**
	 * オープン価格フラグを設定します。
	 * @param openPriceFlg オープン価格フラグ
	 */
	public void setOpenPriceFlg(String openPriceFlg) {
	    this.openPriceFlg = openPriceFlg;
	}

	/**
	 * セット商品フラグを取得します。
	 * @return setItemFlg
	 */
	public String getSetItemFlg() {
		return setItemFlg;
	}

	/**
	 * セット商品フラグを設定します。
	 * @param setItemFlg セットする setItemFlg
	 */
	public void setSetItemFlg(String setItemFlg) {
		this.setItemFlg = setItemFlg;
	}

	/**
	 * @return onloadActionType
	 */
	public String getOnloadActionType() {
		return onloadActionType;
	}

	/**
	 * @param onloadActionType セットする onloadActionType
	 */
	public void setOnloadActionType(String onloadActionType) {
		this.onloadActionType = onloadActionType;
	}

	/**
	 * @return departmentNm
	 */
	public String getDepartmentNm() {
		return departmentNm;
	}

	/**
	 * @param onloadActionType セットする onloadActionType
	 */
	public void setDepartmentNm(String departmentNm) {
		this.departmentNm = departmentNm;
	}

}
