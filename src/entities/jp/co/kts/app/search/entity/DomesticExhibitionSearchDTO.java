package jp.co.kts.app.search.entity;

public class DomesticExhibitionSearchDTO {

	/** 管理品番 */
	private String managementCode;

	/** 定価FROM */
	private String listPriceFrom;

	/** 定価TO */
	private String listPriceTo;

	/** 掛率 */
	private String itemRateOver;

	/** メーカー名 */
	private String makerNm;

	/** メーカー名（カナ） */
	private String makerNmPhonetic;

	/** メーカーID */
	private String sysMakerId;

	/** メーカー品番 */
	private String makerCode;

	/** 商品名 */
	private String itemNm;

	/** 問屋名 */
	private String wholsesalerNm;

	/** 仕入原価FROM */
	private String purchasingCostFrom;

	/** 仕入原価TO */
	private String purchasingCostTo;

	/** 並び順 */
	private String sortFirst;

	/** 降順、昇順 */
	private String sortFirstSub;

	/** ページ最大件数 */
	private String listPageMax;

	/** 数値検索タイプ */
	private String numberOrderType;

	/** 管理ID */
	private long sysManagementId;

	/** 親画面のインデックス */
	private long openerIdx;

	/** システム会社ID */
	private long sysCorporationId;

	/**更新日From*/
	private String updateDateFrom;

	/**更新日To*/
	private String updateDateTo;

	/** セット商品フラグ */
	private String setItemFlg;

	/** 国内商品検索タイプ */
	private String searchItemType;

	/** 担当部署名 */
	private String departmentNm;

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
	 * 定価FROMを取得します。
	 * @return 定価FROM
	 */
	public String getListPriceFrom() {
	    return listPriceFrom;
	}

	/**
	 * 定価FROMを設定します。
	 * @param listPriceFrom 定価FROM
	 */
	public void setListPriceFrom(String listPriceFrom) {
	    this.listPriceFrom = listPriceFrom;
	}

	/**
	 * 定価TOを取得します。
	 * @return 定価TO
	 */
	public String getListPriceTo() {
	    return listPriceTo;
	}

	/**
	 * 定価TOを設定します。
	 * @param listPriceTo 定価TO
	 */
	public void setListPriceTo(String listPriceTo) {
	    this.listPriceTo = listPriceTo;
	}

	/**
	 * 掛率を取得します。
	 * @return 掛率
	 */
	public String getItemRateOver() {
	    return itemRateOver;
	}

	/**
	 * 掛率を設定します。
	 * @param itemRateOver 掛率
	 */
	public void setItemRateOver(String itemRateOver) {
	    this.itemRateOver = itemRateOver;
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
	public String getMakerNmPhonetic() {
	    return makerNmPhonetic;
	}

	/**
	 * メーカー名（カナ）を設定します。
	 * @param makerNmPhonetic メーカー名（カナ）
	 */
	public void setMakerNmPhonetic(String makerNmPhonetic) {
	    this.makerNmPhonetic = makerNmPhonetic;
	}

	/**
	 * メーカーIDを取得します。
	 * @return メーカーID
	 */
	public String getSysMakerId() {
	    return sysMakerId;
	}

	/**
	 * メーカーIDを設定します。
	 * @param sysMakerId メーカーID
	 */
	public void setSysMakerId(String sysMakerId) {
	    this.sysMakerId = sysMakerId;
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
	 * 仕入原価FROMを取得します。
	 * @return 仕入原価FROM
	 */
	public String getPurchasingCostFrom() {
	    return purchasingCostFrom;
	}

	/**
	 * 仕入原価FROMを設定します。
	 * @param purchasingCostFrom 仕入原価FROM
	 */
	public void setPurchasingCostFrom(String purchasingCostFrom) {
	    this.purchasingCostFrom = purchasingCostFrom;
	}

	/**
	 * 仕入原価TOを取得します。
	 * @return 仕入原価TO
	 */
	public String getPurchasingCostTo() {
	    return purchasingCostTo;
	}

	/**
	 * 仕入原価TOを設定します。
	 * @param purchasingCostTo 仕入原価TO
	 */
	public void setPurchasingCostTo(String purchasingCostTo) {
	    this.purchasingCostTo = purchasingCostTo;
	}

	/**
	 * 並び順を取得します。
	 * @return 並び順
	 */
	public String getSortFirst() {
	    return sortFirst;
	}

	/**
	 * 並び順を設定します。
	 * @param sortFirst 並び順
	 */
	public void setSortFirst(String sortFirst) {
	    this.sortFirst = sortFirst;
	}

	/**
	 * 降順、昇順を取得します。
	 * @return 降順、昇順
	 */
	public String getSortFirstSub() {
	    return sortFirstSub;
	}

	/**
	 * 降順、昇順を設定します。
	 * @param sortFirstSub 降順、昇順
	 */
	public void setSortFirstSub(String sortFirstSub) {
	    this.sortFirstSub = sortFirstSub;
	}

	/**
	 * ページ最大件数を取得します。
	 * @return ページ最大件数
	 */
	public String getListPageMax() {
	    return listPageMax;
	}

	/**
	 * ページ最大件数を設定します。
	 * @param listPageMax ページ最大件数
	 */
	public void setListPageMax(String listPageMax) {
	    this.listPageMax = listPageMax;
	}

	/**
	 * 数値検索タイプを取得します。
	 * @return 数値検索タイプ
	 */
	public String getNumberOrderType() {
	    return numberOrderType;
	}

	/**
	 * 数値検索タイプを設定します。
	 * @param numberOrderType 数値検索タイプ
	 */
	public void setNumberOrderType(String numberOrderType) {
	    this.numberOrderType = numberOrderType;
	}

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
	 * 検索時小画面を取得します。
	 * @return
	 */
	public long getOpenerIdx() {
		return openerIdx;
	}

	/**
	 *
	 *
	 * @param openerIdx
	 */
	public void setOpenerIdx(long openerIdx) {
		this.openerIdx = openerIdx;
	}

	/**
	 *
	 *
	 * @return
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 *
	 *
	 * @param sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * 更新日Fromを取得します。
	 * @return 更新日From
	 */
	public String getUpdateDateFrom() {
	    return updateDateFrom;
	}

	/**
	 * 更新日Fromを設定します。
	 * @param updateDateFrom 更新日From
	 */
	public void setUpdateDateFrom(String updateDateFrom) {
	    this.updateDateFrom = updateDateFrom;
	}

	/**
	 * 更新日Toを取得します。
	 * @return 更新日To
	 */
	public String getUpdateDateTo() {
	    return updateDateTo;
	}

	/**
	 * 更新日Toを設定します。
	 * @param updateDateTo 更新日To
	 */
	public void setUpdateDateTo(String updateDateTo) {
	    this.updateDateTo = updateDateTo;
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
	 * @return searchItemType
	 */
	public String getSearchItemType() {
		return searchItemType;
	}

	/**
	 * @param searchItemType セットする searchItemType
	 */
	public void setSearchItemType(String searchItemType) {
		this.searchItemType = searchItemType;
	}

	/**
	 * 担当部署名を取得します。
	 * @return 担当部署名
	 */
	public String getDepartmentNm() {
	    return departmentNm;
	}

	/**
	 * 担当部署名を設定します。
	 * @param wholsesalerNm 担当部署名
	 */
	public void setDepartmentNm(String departmentNm) {
	    this.departmentNm = departmentNm;
	}
}
