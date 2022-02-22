package jp.co.kts.app.search.entity;

public class ResultDomesticOrderSearchDTO {

	/**	管理品番 */
	private String managementCode;

	/**	数量 */
	private long quantity;

	/** 問屋ID */
	private String wholsesalerNm;

	/**	備考 */
	private String memo;

	/**	チェックボックス */
	private String removeCheckFlg;

	/** 商品名 */
	private String itemNm;

	/** 定価 */
	private long listPrice;

	/** モール */
	private String mall;

	/** システム会社ID */
	private long sysCorporationId;

	/** システム倉庫ID */
	private long sysWarehouseId;

	/** ページ最大件数 */
	private String listPageMax;

	/** 数値検索タイプ */
	private String numberOrderType;

	/** メーカーID */
	private String sysMakerId;

	/** メーカー名 */
	private String makerNm;

	/** メーカー名（カナ） */
	private String makerNmKana;

	/** 親画面のインデックス */
	private long openerIdx;

	/**	検索数 */
	private int itemListSize;


	/**
	 *
	 * @return
	 */
	public String getManagementCode() {
		return managementCode;
	}

	/**
	 *
	 * @param managementCode
	 */
	public void setManagementCode(String managementCode) {
		this.managementCode = managementCode;
	}

	/**
	 *
	 * @return
	 */
	public long getQuantity() {
		return quantity;
	}

	/**
	 *
	 * @param quantity
	 */
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	/**
	 *
	 * @return
	 */
	public String getWholsesalerNm() {
		return wholsesalerNm;
	}

	/**
	 *
	 * @param wholsesalerNm
	 */
	public void setWholsesalerNm(String wholsesalerNm) {
		this.wholsesalerNm = wholsesalerNm;
	}

	/**
	 *
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 *
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 *
	 * @return
	 */
	public String getRemoveCheckFlg() {
		return removeCheckFlg;
	}

	/**
	 *
	 * @param chekbox
	 */
	public void setRemoveCheckFlg(String removeCheckFlg) {
		this.removeCheckFlg = removeCheckFlg;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public long getListPrice() {
		return listPrice;
	}

	public void setListPrice(long listPrice) {
		this.listPrice = listPrice;
	}

	public String getMall() {
		return mall;
	}

	public void setMall(String mall) {
		this.mall = mall;
	}

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public long getSysWarehouseId() {
		return sysWarehouseId;
	}

	public void setSysWarehouseId(long sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	public String getListPageMax() {
		return listPageMax;
	}

	public void setListPageMax(String listPageMax) {
		this.listPageMax = listPageMax;
	}

	public String getNumberOrderType() {
		return numberOrderType;
	}

	public void setNumberOrderType(String numberOrderType) {
		this.numberOrderType = numberOrderType;
	}

	public long getOpenerIdx() {
		return openerIdx;
	}

	public void setOpenerIdx(long openerIdx) {
		this.openerIdx = openerIdx;
	}

	public String getMakerNm() {
		return makerNm;
	}

	public void setMakerNm(String makerNm) {
		this.makerNm = makerNm;
	}

	public String getMakerNmKana() {
		return makerNmKana;
	}

	public void setMakerNmKana(String makerNmKana) {
		this.makerNmKana = makerNmKana;
	}

	public String getSysMakerId() {
		return sysMakerId;
	}

	public void setSysMakerId(String sysMakerId) {
		this.sysMakerId = sysMakerId;
	}

	public int getItemListSize() {
		return itemListSize;
	}

	public void setItemListSize(int itemListSize) {
		this.itemListSize = itemListSize;
	}
}
