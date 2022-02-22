/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_商品情報を格納します。
 *
 * @author admin
 */
public class MstItemDTO  {

	/** システム商品ID */
	private long sysItemId;

	/** 品番 */
	private String itemCode;

	/** 旧品番 */
	private String oldItemCode;

	/** 商品名 */
	private String itemNm;

	/** システム商品分類ID */
	private long sysGroupNmId;

	/** 車種 */
	private String carModel;

	/** 型式 */
	private String model;

	/** メーカー名 */
	private String makerNm;

	/** 商品リードタイム */
	private String itemLeadTime;

	/** 月間平均注文個数 */
	private int monthAvgOrderNum;

	/** 梱包サイズ */
	private String packingSize;

	/** 重量 */
	private int weight;

	/** 最小ロット数 */
	private int minimumOrderQuantity;

	/** 工場品番 */
	private String factoryItemCode;

	/** 仕入金額(現地通貨での値段) */
	private float purchaceCost;

	/** 加算経費 */
	private int loading;

	/** 工場商品名 */
	private String foreignItemNm;

	/** システム仕入先ID */
	private long sysSupplierId;

	/** 不良在庫フラグ */
	private String deadStockFlag;

	/** 資料用メモ */
	private String documentRemarks;

	/** 仕入国 */
	private String purchaceCountry;

	/** 仕入れ価格(日本円に直した場合の値段) */
	private int purchacePrice;

	/** 総在庫数 */
	private int totalStockNum;

	/** 仮在庫数 */
	private int temporaryStockNum;

	/** 発注アラート数 */
	private int orderAlertNum;

	/** 説明書フラグ */
	private String manualFlg;

	/** 図面フラグ */
	private String planSheetFlg;

	/** その他資料フラグ */
	private String otherDocumentFlg;

	/** 仕様メモ */
	private String specMemo;

	/** セット商品フラグ */
	private String setItemFlg;

	/** 出庫分類フラグ */
	private String leaveClassFlg;

	/** 削除フラグ */
	private String deleteFlag;

	/** 登録日 */
	private Date createDate;

	/** 登録者ID */
	private int createUserId;

	/** 更新日 */
	private Date updateDate;

	/** 更新者ID */
	private int updateUserId;

	/** 組立可数*/
	private int assemblyNum;

	/** 備考 */
	private String remarks;

	/** 注文プール用注文数 */
	private int orderNum;

	/** 注文プールフラグ */
	private String orderPoolFlg;

	/**
	 * <p>
	 * システム商品ID を返却します。
	 * </p>
	 * @return sysItemId
	 */
	public long getSysItemId() {
		return this.sysItemId;
	}

	/**
	 * <p>
	 * システム商品ID を設定します。
	 * </p>
	 * @param sysItemId
	 */
	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	/**
	 * <p>
	 * 品番 を返却します。
	 * </p>
	 * @return itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * <p>
	 * 品番 を設定します。
	 * </p>
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * <p>
	 * 旧品番 を返却します。
	 * </p>
	 * @return oldItemCode
	 */
	public String getOldItemCode() {
		return this.oldItemCode;
	}

	/**
	 * <p>
	 * 旧品番 を設定します。
	 * </p>
	 * @param oldItemCode
	 */
	public void setOldItemCode(String oldItemCode) {
		this.oldItemCode = oldItemCode;
	}

	/**
	 * <p>
	 * 商品名 を返却します。
	 * </p>
	 * @return itemNm
	 */
	public String getItemNm() {
		return this.itemNm;
	}

	/**
	 * <p>
	 * 商品名 を設定します。
	 * </p>
	 * @param itemNm
	 */
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	/**
	 * <p>
	 * システム商品分類ID を返却します。
	 * </p>
	 * @return sysGroupNmId
	 */
	public long getSysGroupNmId() {
		return this.sysGroupNmId;
	}

	/**
	 * <p>
	 * システム商品分類ID を設定します。
	 * </p>
	 * @param sysGroupNmId
	 */
	public void setSysGroupNmId(long sysGroupNmId) {
		this.sysGroupNmId = sysGroupNmId;
	}

	/**
	 * <p>
	 * 車種を返却します
	 * </p>
	 * @return carModel
	 */
	public String getCarModel() {
		return carModel;
	}

	/**
	 * <p>
	 * 車種を設定します
	 * </P>
	 * @param carModel
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	/**
	 * <P>
	 * 型式を返却します
	 * </P>
	 * @return model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * <p>
	 * 型式を設定します
	 * </p>
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * <p>
	 * メーカー名を返却します
	 * </p>
	 * @return makerNm
	 */
	public String getMakerNm() {
		return makerNm;
	}

	/**
	 * <p>
	 * メーカー名を設定します
	 * </p>
	 * @param makerNm
	 */
	public void setMakerNm(String makerNm) {
		this.makerNm = makerNm;
	}

	/**
	 * <p>
	 * 商品リードタイムを返却します
	 * </p>
	 * @return itemLeadTime
	 */
	public String getItemLeadTime() {
		return itemLeadTime;
	}

	/**
	 * <p>
	 * 商品リードタイムを設定します
	 * </p>
	 * @param itemLeadTime
	 */
	public void setItemLeadTime(String itemLeadTime) {
		this.itemLeadTime = itemLeadTime;
	}

	/**
	 * <p>
	 * 月間平均注文数を返却します
	 * </p>
	 * @return monthAvgOrderNum
	 */
	public int getMonthAvgOrderNum() {
		return monthAvgOrderNum;
	}

	/**
	 * <p>
	 * 月間平均注文数を設定します
	 * </p>
	 * @param monthAvgOrderNum
	 */
	public void setMonthAvgOrderNum(int monthAvgOrderNum) {
		this.monthAvgOrderNum = monthAvgOrderNum;
	}

	/**
	 * <p>
	 * 梱包サイズを返却します
	 * </p>
	 * @return packingSize
	 */
	public String getPackingSize() {
		return packingSize;
	}

	/**
	 * <p>
	 * 梱包サイズを設定します
	 * </p>
	 * @param packingSize
	 */
	public void setPackingSize(String packingSize) {
		this.packingSize = packingSize;
	}

	/**
	 * <p>
	 * 重量を返却します
	 * </p>
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * <p>
	 * 重量を設定します
	 * </p>
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * <p>
	 * 最小ロット数を返却します
	 * </p>
	 * @return minimumOrderQuantity
	 */
	public int getMinimumOrderQuantity() {
		return minimumOrderQuantity;
	}

	/**
	 * <p>
	 * 最小ロット数を設定します
	 * </p>
	 * @param minimumOrderQuantity
	 */
	public void setMinimumOrderQuantity(int minimumOrderQuantity) {
		this.minimumOrderQuantity = minimumOrderQuantity;
	}

	/**
	 * <p>
	 * 工場品番を返却します
	 * </p>
	 * @return factoryItemCode
	 */
	public String getFactoryItemCode() {
		return factoryItemCode;
	}

	/**
	 * <p>
	 * 工場品番を設定します
	 * </p>
	 * @param factoryItemCode
	 */
	public void setFactoryItemCode(String factoryItemCode) {
		this.factoryItemCode = factoryItemCode;
	}

	/**
	 * <p>
	 * 仕入れ金額を返却します
	 * </p>
	 * @return purchaceCost
	 */
	public float getPurchaceCost() {
		return purchaceCost;
	}

	/**
	 * <p>
	 * 仕入れ金額を設定します
	 * </p>
	 * @param purchaceCost
	 */
	public void setPurchaceCost(float purchaceCost) {
		this.purchaceCost = purchaceCost;
	}

	/**
	 * <p>
	 * 仕入れ価格を返却します
	 * </p>
	 * @return purchacePrice
	 */
	public int getPurchacePrice() {
		return purchacePrice;
	}

	/**
	 * <p>
	 * 仕入れ価格を設定します
	 * </p>
	 * @param purchacePrice
	 */
	public void setPurchacePrice(int purchacePrice) {
		this.purchacePrice = purchacePrice;
	}

	/**
	 * <p>
	 * 加算経費を返却します
	 * </p>
	 * @return loading
	 */
	public int getLoading() {
		return loading;
	}

	/**
	 * <p>
	 * 加算経費を設定します
	 * </p>
	 * @param loading
	 */
	public void setLoading(int loading) {
		this.loading = loading;
	}

	/**
	 * <p>
	 * 海外商品名を返却します
	 * </p>
	 * @return foreignItemNm
	 */
	public String getForeignItemNm() {
		return foreignItemNm;
	}

	/**
	 * <p>
	 * 海外商品名を設定します
	 * </p>
	 * @param foreignItemNm
	 */
	public void setForeignItemNm(String foreignItemNm) {
		this.foreignItemNm = foreignItemNm;
	}

	/**
	 * <p>
	 * システム仕入先IDを返却します
	 * </p>
	 * @return sysSupplierId
	 */
	public long getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * <p>
	 * システム仕入先IDを設定します
	 * </p>
	 * @param sysSupplierId
	 */
	public void setSysSupplierId(long sysSupplierId) {
		this.sysSupplierId = sysSupplierId;
	}

	/**
	 * <p>
	 * 不良在庫フラグを返却します
	 * </p>
	 * @return deadStockFlag
	 */
	public String getDeadStockFlag() {
		return deadStockFlag;
	}

	/**
	 * <p>
	 * 不良在庫フラグを設定します
	 * </p>
	 * @param deadStockFlag
	 */
	public void setDeadStockFlag(String deadStockFlag) {
		this.deadStockFlag = deadStockFlag;
	}

	/**
	 * <p>
	 * 資料用メモを返却します
	 * </p>
	 * @return documentRemarks
	 */
	public String getDocumentRemarks() {
		return documentRemarks;
	}

	/**
	 * <p>
	 * 資料用メモを設定します
	 * </p>
	 * @param documentRemarks
	 */
	public void setDocumentRemarks(String documentRemarks) {
		this.documentRemarks = documentRemarks;
	}

	/**
	 * <p>
	 * 仕入国を返却します
	 * </p>
	 * @return purchaceCountry
	 */
	public String getPurchaceCountry() {
		return purchaceCountry;
	}

	/**
	 * <p>
	 * 仕入国を設定します
	 * </p>
	 * @param purchaceCountry
	 */
	public void setPurchaceCountry(String purchaceCountry) {
		this.purchaceCountry = purchaceCountry;
	}

	/**
	 * <p>
	 * 総在庫数 を返却します。
	 * </p>
	 * @return totalStockNum
	 */
	public int getTotalStockNum() {
		return this.totalStockNum;
	}

	/**
	 * <p>
	 * 総在庫数 を設定します。
	 * </p>
	 * @param totalStockNum
	 */
	public void setTotalStockNum(int totalStockNum) {
		this.totalStockNum = totalStockNum;
	}

	/**
	 * <p>
	 * 仮在庫数 を返却します。
	 * </p>
	 * @return temporaryStockNum
	 */
	public int getTemporaryStockNum() {
		return this.temporaryStockNum;
	}

	/**
	 * <p>
	 * 仮在庫数 を設定します。
	 * </p>
	 * @param temporaryStockNum
	 */
	public void setTemporaryStockNum(int temporaryStockNum) {
		this.temporaryStockNum = temporaryStockNum;
	}

	/**
	 * <p>
	 * 発注アラート数 を返却します。
	 * </p>
	 * @return orderAlertNum
	 */
	public int getOrderAlertNum() {
		return this.orderAlertNum;
	}

	/**
	 * <p>
	 * 発注アラート数 を設定します。
	 * </p>
	 * @param orderAlertNum
	 */
	public void setOrderAlertNum(int orderAlertNum) {
		this.orderAlertNum = orderAlertNum;
	}

	/**
	 * @return manualFlg
	 */
	public String getManualFlg() {
		return manualFlg;
	}

	/**
	 * @param manualFlg セットする manualFlg
	 */
	public void setManualFlg(String manualFlg) {
		this.manualFlg = manualFlg;
	}

	/**
	 * <p>
	 * 図面フラグ を返却します。
	 * </p>
	 * @return planSheetFlg
	 */
	public String getPlanSheetFlg() {
		return this.planSheetFlg;
	}

	/**
	 * <p>
	 * 図面フラグ を設定します。
	 * </p>
	 * @param planSheetFlg
	 */
	public void setPlanSheetFlg(String planSheetFlg) {
		this.planSheetFlg = planSheetFlg;
	}

	/**
	 * <p>
	 * その他資料フラグを返却します。
	 * </p>
	 * @return otherDocumentFlg
	 */
	public String getOtherDocumentFlg() {
		return otherDocumentFlg;
	}

	/**
	 * <p>
	 * その他資料フラグを設定します。
	 * @param otherDocumentFlg
	 */
	public void setOtherDocumentFlg(String otherDocumentFlg) {
		this.otherDocumentFlg = otherDocumentFlg;
	}

	/**
	 * <p>
	 * 仕様メモ を返却します。
	 * </p>
	 * @return specMemo
	 */
	public String getSpecMemo() {
		return this.specMemo;
	}

	/**
	 * <p>
	 * 仕様メモ を設定します。
	 * </p>
	 * @param specMemo
	 */
	public void setSpecMemo(String specMemo) {
		this.specMemo = specMemo;
	}

	/**
	 * <p>
	 * セット商品フラグ を返却します。
	 * </p>
	 * @return setItemFlg
	 */
	public String getSetItemFlg() {
		return this.setItemFlg;
	}

	/**
	 * <p>
	 * セット商品フラグ を設定します。
	 * </p>
	 * @param setItemFlg
	 */
	public void setSetItemFlg(String setItemFlg) {
		this.setItemFlg = setItemFlg;
	}

	/**
	 * <p>
	 * 出庫分類フラグ を返却します。
	 * </p>
	 * @return leaveClassFlg
	 */
	public String getLeaveClassFlg() {
		return this.leaveClassFlg;
	}

	/**
	 * <p>
	 * 出庫分類フラグ を設定します。
	 * </p>
	 * @param leaveClassFlg
	 */
	public void setLeaveClassFlg(String leaveClassFlg) {
		this.leaveClassFlg = leaveClassFlg;
	}

	/**
	 * <p>
	 * 削除フラグ を返却します。
	 * </p>
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	/**
	 * <p>
	 * 削除フラグ を設定します。
	 * </p>
	 * @param deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * <p>
	 * 登録日 を返却します。
	 * </p>
	 * @return createDate
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * <p>
	 * 登録日 を設定します。
	 * </p>
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * <p>
	 * 登録者ID を返却します。
	 * </p>
	 * @return createUserId
	 */
	public int getCreateUserId() {
		return this.createUserId;
	}

	/**
	 * <p>
	 * 登録者ID を設定します。
	 * </p>
	 * @param createUserId
	 */
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * <p>
	 * 更新日 を返却します。
	 * </p>
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * <p>
	 * 更新日 を設定します。
	 * </p>
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * <p>
	 * 更新者ID を返却します。
	 * </p>
	 * @return updateUserId
	 */
	public int getUpdateUserId() {
		return this.updateUserId;
	}

	/**
	 * <p>
	 * 更新者ID を設定します。
	 * </p>
	 * @param updateUserId
	 */
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return assemblyNum
	 */
	public int getAssemblyNum() {
		return assemblyNum;
	}

	/**
	 * @param assemblyNum
	 */
	public void setAssemblyNum(int assemblyNum) {
		this.assemblyNum = assemblyNum;
	}

	/**
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return orderPoolOrderNum
	 */
	public int getOrderNum() {
		return orderNum;
	}

	/**
	 * @param orderPoolOrderNum
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * @return orderPoolFlg
	 */
	public String getOrderPoolFlg() {
		return orderPoolFlg;
	}

	/**
	 * @param orderPoolFlg
	 */
	public void setOrderPoolFlg(String orderPoolFlg) {
		this.orderPoolFlg = orderPoolFlg;
	}

}
