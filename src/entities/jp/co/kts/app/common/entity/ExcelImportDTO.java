package jp.co.kts.app.common.entity;

import java.util.List;

import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;

public class ExcelImportDTO {

	/** 基本情報 (MST_ITEM) */
	private List<MstItemDTO> mstItemDTOList;

	/** 倉庫情報 (WAREHOUSE_STOCK) */
	private List<WarehouseStockDTO> warehouseStockDTOList;

	/** 入荷予定 (ARRIVAL_SCHEDULE) */
	private List<ArrivalScheduleDTO> arrivalScheduleDTOList;

	/** 原価 (ITEM_COST) */
	private List<ItemCostDTO> itemCostDTOList;

	/** 売価 (ITEM_PRICE) */
	private List<ItemPriceDTO> itemPriceDTOList;

	/** バックオーダー (BACK_ORDER) */
	private List<BackOrderDTO> backOrderDTOList;

	/** セット商品 (SET_ITEM) */
	private List<SetItemDTO> setItemDTOList;

	/** セット商品 (CODE_COLLATION) */
	private List<CodeCollationDTO> codeCollationDTOList;

	/** 出品DB(EXHIBIT_MANAGE) */
	private List<DomesticExhibitionDTO> domesticExhibitionDtoList;

	/** セット出品DB(SET_EXHIBIT_MANAGE) */
	private List<ExtendSetDomesticExhibitionDto> setDomesticExhibitionDtoList;

	/**
	 * @return mstItemDTOList
	 */
	public List<MstItemDTO> getMstItemDTOList() {
		return mstItemDTOList;
	}

	/**
	 * @param mstItemDTOList セットする mstItemDTOList
	 */
	public void setMstItemDTOList(List<MstItemDTO> mstItemDTOList) {
		this.mstItemDTOList = mstItemDTOList;
	}

	/**
	 * @return warehouseStockDTOList
	 */
	public List<WarehouseStockDTO> getWarehouseStockDTOList() {
		return warehouseStockDTOList;
	}

	/**
	 * @param warehouseStockDTOList セットする warehouseStockDTOList
	 */
	public void setWarehouseStockDTOList(
			List<WarehouseStockDTO> warehouseStockDTOList) {
		this.warehouseStockDTOList = warehouseStockDTOList;
	}

	/**
	 * @return arrivalScheduleDTOList
	 */
	public List<ArrivalScheduleDTO> getArrivalScheduleDTOList() {
		return arrivalScheduleDTOList;
	}

	/**
	 * @param arrivalScheduleDTOList セットする arrivalScheduleDTOList
	 */
	public void setArrivalScheduleDTOList(
			List<ArrivalScheduleDTO> arrivalScheduleDTOList) {
		this.arrivalScheduleDTOList = arrivalScheduleDTOList;
	}

	/**
	 * @return itemCostDTOList
	 */
	public List<ItemCostDTO> getItemCostDTOList() {
		return itemCostDTOList;
	}

	/**
	 * @param itemCostDTOList セットする itemCostDTOList
	 */
	public void setItemCostDTOList(List<ItemCostDTO> itemCostDTOList) {
		this.itemCostDTOList = itemCostDTOList;
	}

	/**
	 * @return itemPriceDTOList
	 */
	public List<ItemPriceDTO> getItemPriceDTOList() {
		return itemPriceDTOList;
	}

	/**
	 * @param itemPriceDTOList セットする itemPriceDTOList
	 */
	public void setItemPriceDTOList(List<ItemPriceDTO> itemPriceDTOList) {
		this.itemPriceDTOList = itemPriceDTOList;
	}

	/**
	 * @return backOrderDTOList
	 */
	public List<BackOrderDTO> getBackOrderDTOList() {
		return backOrderDTOList;
	}

	/**
	 * @param backOrderDTOList セットする backOrderDTOList
	 */
	public void setBackOrderDTOList(List<BackOrderDTO> backOrderDTOList) {
		this.backOrderDTOList = backOrderDTOList;
	}

	/**
	 * @return setItemDTOList
	 */
	public List<SetItemDTO> getSetItemDTOList() {
		return setItemDTOList;
	}

	/**
	 * @param setItemDTOList セットする setItemDTOList
	 */
	public void setSetItemDTOList(List<SetItemDTO> setItemDTOList) {
		this.setItemDTOList = setItemDTOList;
	}

	/**
	 * @return codeCollationDTOList
	 */
	public List<CodeCollationDTO> getCodeCollationDTOList() {
		return codeCollationDTOList;
	}

	/**
	 * @param codeCollationDTOList セットする codeCollationDTOList
	 */
	public void setCodeCollationDTOList(List<CodeCollationDTO> codeCollationDTOList) {
		this.codeCollationDTOList = codeCollationDTOList;
	}

	/**
	 * 出品DB(EXHIBIT_MANAGE)を取得します。
	 * @return 出品DB(EXHIBIT_MANAGE)
	 */
	public List<DomesticExhibitionDTO> getDomesticExhibitionDtoList() {
	    return domesticExhibitionDtoList;
	}

	/**
	 * 出品DB(EXHIBIT_MANAGE)を設定します。
	 * @param domesticExhibitionDtoList 出品DB(EXHIBIT_MANAGE)
	 */
	public void setDomesticExhibitionDtoList(List<DomesticExhibitionDTO> domesticExhibitionDtoList) {
	    this.domesticExhibitionDtoList = domesticExhibitionDtoList;
	}

	/**
	 * @return setDomesticExhibitionDtoList
	 */
	public List<ExtendSetDomesticExhibitionDto> getSetDomesticExhibitionDtoList() {
		return setDomesticExhibitionDtoList;
	}

	/**
	 * @param setDomesticExhibitionDtoList セットする setDomesticExhibitionDtoList
	 */
	public void setSetDomesticExhibitionDtoList(List<ExtendSetDomesticExhibitionDto> setDomesticExhibitionDtoList) {
		this.setDomesticExhibitionDtoList = setDomesticExhibitionDtoList;
	}

}
