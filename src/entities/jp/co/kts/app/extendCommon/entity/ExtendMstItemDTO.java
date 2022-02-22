package jp.co.kts.app.extendCommon.entity;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.kts.app.common.entity.MstItemDTO;

public class ExtendMstItemDTO extends MstItemDTO {

	private String manualFlg = StringUtil.SWITCH_OFF_KEY;

	private String planSheetFlg = StringUtil.SWITCH_OFF_KEY;

	private int sysSmallGroupId;

	private int sysMiddleGroupId;

	private int sysLargeGroupId;

	private long cost;

	private long price;

	private int sysItemCostId;

	private String supplierId;

	private int sumNotInStock;

	/** Kind原価 */
	private long kindCost;
	/** Kind原価ID */
	private long kindCostId;

	/**年間平均売上数*/
	private long annualAverageSalesNum;

	/** 定価 */
	private long listPrice;
	/** 仕入原価 */
	private long purchasingCost;
	/** 商品掛け率 */
	private long itemRateOver;
	/** 商品区分 */
	private String itemType;


	public ExtendMstItemDTO(String empty) {

		setItemCode(empty);
		setOldItemCode(empty);
		setItemNm(empty);
//		setManualPath(empty);
//		setPlanSheetPath(empty);
		setSpecMemo(empty);
	}

	public ExtendMstItemDTO() {

	}

	public int getSysItemCostId() {
		return sysItemCostId;
	}

	public void setSysItemCostId(int sysItemCostId) {
		this.sysItemCostId = sysItemCostId;
	}

	public String getManualFlg() {
		return manualFlg;
	}

	public void setManualFlg(String manualFlg) {
		this.manualFlg = manualFlg;
	}

	public String getPlanSheetFlg() {
		return planSheetFlg;
	}

	public void setPlanSheetFlg(String planSheetFlg) {
		this.planSheetFlg = planSheetFlg;
	}

	public int getSysSmallGroupId() {
		return sysSmallGroupId;
	}

	public void setSysSmallGroupId(int sysSmallGroupId) {
		this.sysSmallGroupId = sysSmallGroupId;
	}

	public int getSysMiddleGroupId() {
		return sysMiddleGroupId;
	}

	public void setSysMiddleGroupId(int sysMiddleGroupId) {
		this.sysMiddleGroupId = sysMiddleGroupId;
	}

	public int getSysLargeGroupId() {
		return sysLargeGroupId;
	}

	public void setSysLargeGroupId(int sysLargeGroupId) {
		this.sysLargeGroupId = sysLargeGroupId;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	/**
	 * @return supplierId
	 */
	public String getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId セットする supplierId
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return sumNotInStock
	 */
	public int getSumNotInStock() {
		return sumNotInStock;
	}

	/**
	 * @param sumNotInStock セットする sumNotInStock
	 */
	public void setSumNotInStock(int sumNotInStock) {
		this.sumNotInStock = sumNotInStock;
	}

	/****************************** 更新情報保持用変数START ******************************/

	/** 変更前品番 */
	private String beforeItemCode;

	/** 変更前旧品番 */
	private String beforeOldItemCode;

	/** 変更前商品名 */
	private String beforeItemNm;

	/** 変更前車種 */
	private String beforeCarModel;

	/** 変更前型式 */
	private String beforeModel;

	/** 変更前メーカー名 */
	private String beforeMakerNm;

	/** 変更前商品リードタイム */
	private String beforeItemLeadTime;

	/** 変更前梱包サイズ */
	private String beforePackingSize;

	/** 変更前重量 */
	private int beforeWeight;

	/** 変更前最小ロット数 */
	private int beforeMinimumOrderQuantity;

	/** 変更前工場品番 */
	private String beforeFactoryItemCode;

	/** 変更前仕入金額(現地通貨での値段) */
	private float beforePurchaceCost;

	/** 変更前加算経費 */
	private int beforeLoading;

	/** 変更前工場商品名 */
	private String beforeForeignItemNm;

	/** 変更前システム仕入先ID */
	private long beforeSysSupplierId;

	/** 変更前資料用メモ */
	private String beforeDocumentRemarks;

	/** 変更前仕入国 */
	private String beforePurchaceCountry;

	/** 変更前仕入れ価格(日本円に直した場合の値段) */
	private int beforePurchacePrice;

	/** 変更前総在庫数 */
	private int beforeTotalStockNum;

	/** 変更前仮在庫数 */
	private int beforeTemporaryStockNum;

	/** 変更前発注アラート数 */
	private int beforeOrderAlertNum;

	/** 変更前仕様メモ */
	private String beforeSpecMemo;

	/** 変更前組立可数*/
	private int beforeAssemblyNum;

	/** 変更前注文プール用注文数 */
	private int beforeOrderNum;

	/** 変更前Kind原価 */
	private long beforeKindCost;

	/** 変更前仕入先ID */
	private long BeforeSysSupplierId;


	/**
	 * Kind原価を取得します。
	 * @return Kind原価
	 */
	public long getKindCost() {
	    return kindCost;
	}

	/**
	 * Kind原価を設定します。
	 * @param kindCost Kind原価
	 */
	public void setKindCost(long kindCost) {
	    this.kindCost = kindCost;
	}

	/**
	 * Kind原価IDを取得します。
	 * @return Kind原価ID
	 */
	public long getKindCostId() {
	    return kindCostId;
	}

	/**
	 * Kind原価IDを設定します。
	 * @param kindCostId Kind原価ID
	 */
	public void setKindCostId(long kindCostId) {
	    this.kindCostId = kindCostId;
	}

	/**
	 * 年間平均売上数を取得します。
	 * @return 年間平均売上数
	 */
	public long getAnnualAverageSalesNum() {
	    return annualAverageSalesNum;
	}

	/**
	 * 年間平均売上数を設定します。
	 * @param annualAverageSalesNum 年間平均売上数
	 */
	public void setAnnualAverageSalesNum(long annualAverageSalesNum) {
	    this.annualAverageSalesNum = annualAverageSalesNum;
	}

	/**
	 * @return listPrice
	 */
	public long getListPrice() {
		return listPrice;
	}

	/**
	 * @return purchasingCost
	 */
	public long getPurchasingCost() {
		return purchasingCost;
	}

	/**
	 * @return itemRateOver
	 */
	public long getItemRateOver() {
		return itemRateOver;
	}

	/**
	 * @param listPrice セットする listPrice
	 */
	public void setListPrice(long listPrice) {
		this.listPrice = listPrice;
	}

	/**
	 * @param purchasingCost セットする purchasingCost
	 */
	public void setPurchasingCost(long purchasingCost) {
		this.purchasingCost = purchasingCost;
	}

	/**
	 * @param itemRateOver セットする itemRateOver
	 */
	public void setItemRateOver(long itemRateOver) {
		this.itemRateOver = itemRateOver;
	}

	/**
	 * 変更前品番を取得します。
	 * @return 変更前品番
	 */
	public String getBeforeItemCode() {
	    return beforeItemCode;
	}

	/**
	 * 変更前品番を設定します。
	 * @param beforeItemCode 変更前品番
	 */
	public void setBeforeItemCode(String beforeItemCode) {
	    this.beforeItemCode = beforeItemCode;
	}

	/**
	 * 変更前旧品番を取得します。
	 * @return 変更前旧品番
	 */
	public String getBeforeOldItemCode() {
	    return beforeOldItemCode;
	}

	/**
	 * 変更前旧品番を設定します。
	 * @param beforeOldItemCode 変更前旧品番
	 */
	public void setBeforeOldItemCode(String beforeOldItemCode) {
	    this.beforeOldItemCode = beforeOldItemCode;
	}

	/**
	 * 変更前商品名を取得します。
	 * @return 変更前商品名
	 */
	public String getBeforeItemNm() {
	    return beforeItemNm;
	}

	/**
	 * 変更前商品名を設定します。
	 * @param beforeItemNm 変更前商品名
	 */
	public void setBeforeItemNm(String beforeItemNm) {
	    this.beforeItemNm = beforeItemNm;
	}

	/**
	 * 変更前車種を取得します。
	 * @return 変更前車種
	 */
	public String getBeforeCarModel() {
	    return beforeCarModel;
	}

	/**
	 * 変更前車種を設定します。
	 * @param beforeCarModel 変更前車種
	 */
	public void setBeforeCarModel(String beforeCarModel) {
	    this.beforeCarModel = beforeCarModel;
	}

	/**
	 * 変更前型式を取得します。
	 * @return 変更前型式
	 */
	public String getBeforeModel() {
	    return beforeModel;
	}

	/**
	 * 変更前型式を設定します。
	 * @param beforeModel 変更前型式
	 */
	public void setBeforeModel(String beforeModel) {
	    this.beforeModel = beforeModel;
	}

	/**
	 * 変更前メーカー名を取得します。
	 * @return 変更前メーカー名
	 */
	public String getBeforeMakerNm() {
	    return beforeMakerNm;
	}

	/**
	 * 変更前メーカー名を設定します。
	 * @param beforeMakerNm 変更前メーカー名
	 */
	public void setBeforeMakerNm(String beforeMakerNm) {
	    this.beforeMakerNm = beforeMakerNm;
	}

	/**
	 * 変更前商品リードタイムを取得します。
	 * @return 変更前商品リードタイム
	 */
	public String getBeforeItemLeadTime() {
	    return beforeItemLeadTime;
	}

	/**
	 * 変更前商品リードタイムを設定します。
	 * @param beforeItemLeadTime 変更前商品リードタイム
	 */
	public void setBeforeItemLeadTime(String beforeItemLeadTime) {
	    this.beforeItemLeadTime = beforeItemLeadTime;
	}

	/**
	 * 変更前梱包サイズを取得します。
	 * @return 変更前梱包サイズ
	 */
	public String getBeforePackingSize() {
	    return beforePackingSize;
	}

	/**
	 * 変更前梱包サイズを設定します。
	 * @param beforePackingSize 変更前梱包サイズ
	 */
	public void setBeforePackingSize(String beforePackingSize) {
	    this.beforePackingSize = beforePackingSize;
	}

	/**
	 * 変更前重量を取得します。
	 * @return 変更前重量
	 */
	public int getBeforeWeight() {
	    return beforeWeight;
	}

	/**
	 * 変更前重量を設定します。
	 * @param beforeWeight 変更前重量
	 */
	public void setBeforeWeight(int beforeWeight) {
	    this.beforeWeight = beforeWeight;
	}

	/**
	 * 変更前最小ロット数を取得します。
	 * @return 変更前最小ロット数
	 */
	public int getBeforeMinimumOrderQuantity() {
	    return beforeMinimumOrderQuantity;
	}

	/**
	 * 変更前最小ロット数を設定します。
	 * @param beforeMinimumOrderQuantity 変更前最小ロット数
	 */
	public void setBeforeMinimumOrderQuantity(int beforeMinimumOrderQuantity) {
	    this.beforeMinimumOrderQuantity = beforeMinimumOrderQuantity;
	}

	/**
	 * 変更前工場品番を取得します。
	 * @return 変更前工場品番
	 */
	public String getBeforeFactoryItemCode() {
	    return beforeFactoryItemCode;
	}

	/**
	 * 変更前工場品番を設定します。
	 * @param beforeFactoryItemCode 変更前工場品番
	 */
	public void setBeforeFactoryItemCode(String beforeFactoryItemCode) {
	    this.beforeFactoryItemCode = beforeFactoryItemCode;
	}

	/**
	 * 変更前仕入金額(現地通貨での値段)を取得します。
	 * @return 変更前仕入金額(現地通貨での値段)
	 */
	public float getBeforePurchaceCost() {
	    return beforePurchaceCost;
	}

	/**
	 * 変更前仕入金額(現地通貨での値段)を設定します。
	 * @param beforePurchaceCost 変更前仕入金額(現地通貨での値段)
	 */
	public void setBeforePurchaceCost(float beforePurchaceCost) {
	    this.beforePurchaceCost = beforePurchaceCost;
	}

	/**
	 * 変更前加算経費を取得します。
	 * @return 変更前加算経費
	 */
	public int getBeforeLoading() {
	    return beforeLoading;
	}

	/**
	 * 変更前加算経費を設定します。
	 * @param beforeLoading 変更前加算経費
	 */
	public void setBeforeLoading(int beforeLoading) {
	    this.beforeLoading = beforeLoading;
	}

	/**
	 * 変更前工場商品名を取得します。
	 * @return 変更前工場商品名
	 */
	public String getBeforeForeignItemNm() {
	    return beforeForeignItemNm;
	}

	/**
	 * 変更前工場商品名を設定します。
	 * @param beforeForeignItemNm 変更前工場商品名
	 */
	public void setBeforeForeignItemNm(String beforeForeignItemNm) {
	    this.beforeForeignItemNm = beforeForeignItemNm;
	}

	/**
	 * 変更前資料用メモを取得します。
	 * @return 変更前資料用メモ
	 */
	public String getBeforeDocumentRemarks() {
	    return beforeDocumentRemarks;
	}

	/**
	 * 変更前資料用メモを設定します。
	 * @param beforeDocumentRemarks 変更前資料用メモ
	 */
	public void setBeforeDocumentRemarks(String beforeDocumentRemarks) {
	    this.beforeDocumentRemarks = beforeDocumentRemarks;
	}

	/**
	 * 変更前仕入国を取得します。
	 * @return 変更前仕入国
	 */
	public String getBeforePurchaceCountry() {
	    return beforePurchaceCountry;
	}

	/**
	 * 変更前仕入国を設定します。
	 * @param beforePurchaceCountry 変更前仕入国
	 */
	public void setBeforePurchaceCountry(String beforePurchaceCountry) {
	    this.beforePurchaceCountry = beforePurchaceCountry;
	}

	/**
	 * 変更前仕入れ価格(日本円に直した場合の値段)を取得します。
	 * @return 変更前仕入れ価格(日本円に直した場合の値段)
	 */
	public int getBeforePurchacePrice() {
	    return beforePurchacePrice;
	}

	/**
	 * 変更前仕入れ価格(日本円に直した場合の値段)を設定します。
	 * @param beforePurchacePrice 変更前仕入れ価格(日本円に直した場合の値段)
	 */
	public void setBeforePurchacePrice(int beforePurchacePrice) {
	    this.beforePurchacePrice = beforePurchacePrice;
	}

	/**
	 * 変更前総在庫数を取得します。
	 * @return 変更前総在庫数
	 */
	public int getBeforeTotalStockNum() {
	    return beforeTotalStockNum;
	}

	/**
	 * 変更前総在庫数を設定します。
	 * @param beforeTotalStockNum 変更前総在庫数
	 */
	public void setBeforeTotalStockNum(int beforeTotalStockNum) {
	    this.beforeTotalStockNum = beforeTotalStockNum;
	}

	/**
	 * 変更前仮在庫数を取得します。
	 * @return 変更前仮在庫数
	 */
	public int getBeforeTemporaryStockNum() {
	    return beforeTemporaryStockNum;
	}

	/**
	 * 変更前仮在庫数を設定します。
	 * @param beforeTemporaryStockNum 変更前仮在庫数
	 */
	public void setBeforeTemporaryStockNum(int beforeTemporaryStockNum) {
	    this.beforeTemporaryStockNum = beforeTemporaryStockNum;
	}

	/**
	 * 変更前発注アラート数を取得します。
	 * @return 変更前発注アラート数
	 */
	public int getBeforeOrderAlertNum() {
	    return beforeOrderAlertNum;
	}

	/**
	 * 変更前発注アラート数を設定します。
	 * @param beforeOrderAlertNum 変更前発注アラート数
	 */
	public void setBeforeOrderAlertNum(int beforeOrderAlertNum) {
	    this.beforeOrderAlertNum = beforeOrderAlertNum;
	}

	/**
	 * 変更前仕様メモを取得します。
	 * @return 変更前仕様メモ
	 */
	public String getBeforeSpecMemo() {
	    return beforeSpecMemo;
	}

	/**
	 * 変更前仕様メモを設定します。
	 * @param beforeSpecMemo 変更前仕様メモ
	 */
	public void setBeforeSpecMemo(String beforeSpecMemo) {
	    this.beforeSpecMemo = beforeSpecMemo;
	}

	/**
	 * 変更前組立可数を取得します。
	 * @return 変更前組立可数
	 */
	public int getBeforeAssemblyNum() {
	    return beforeAssemblyNum;
	}

	/**
	 * 変更前組立可数を設定します。
	 * @param beforeAssemblyNum 変更前組立可数
	 */
	public void setBeforeAssemblyNum(int beforeAssemblyNum) {
	    this.beforeAssemblyNum = beforeAssemblyNum;
	}

	/**
	 * 変更前注文プール用注文数を取得します。
	 * @return 変更前注文プール用注文数
	 */
	public int getBeforeOrderNum() {
	    return beforeOrderNum;
	}

	/**
	 * 変更前注文プール用注文数を設定します。
	 * @param beforeOrderNum 変更前注文プール用注文数
	 */
	public void setBeforeOrderNum(int beforeOrderNum) {
	    this.beforeOrderNum = beforeOrderNum;
	}

	/**
	 * 変更前Kind原価を取得します。
	 * @return 変更前Kind原価
	 */
	public long getBeforeKindCost() {
	    return beforeKindCost;
	}

	/**
	 * 変更前Kind原価を設定します。
	 * @param beforeKindCost 変更前Kind原価
	 */
	public void setBeforeKindCost(long beforeKindCost) {
	    this.beforeKindCost = beforeKindCost;
	}

	/**
	 * 変更前仕入先IDを取得します。
	 * @return 変更前仕入先ID
	 */
	public long getBeforeSysSupplierId() {
	    return beforeSysSupplierId;
	}

	/**
	 * 変更前仕入先IDを設定します。
	 * @param beforeSysSupplierId 変更前仕入先ID
	 */
	public void setBeforeSysSupplierId(long beforeSysSupplierId) {
	    this.beforeSysSupplierId = beforeSysSupplierId;
	}

	/**
	 * @return itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType セットする itemType
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/****************************** 更新情報保持用変数END ******************************/
}
