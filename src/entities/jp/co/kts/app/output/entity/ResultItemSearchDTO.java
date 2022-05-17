package jp.co.kts.app.output.entity;

import jp.co.kts.app.common.entity.MstItemDTO;

public class ResultItemSearchDTO extends MstItemDTO {

	/** システム入荷予定ID */
	private long sysArrivalScheduleId;

	/** システム倉庫在庫ID */
	private long sysWarehouseStockId;

	/** 発注日 */
	private String itemOrderDate;

	/** 入荷予定日 */
	private String arrivalScheduleDate;

	/** 入荷数 */
	private int arrivalNum;

	/** 入荷数 */
	private String arrivalNumDisp;

	/** バックオーダー日 */
	private String backOrderDate;

	/** バックオーダー件数 */
	private int backOrderCount;

	/** バックオーダー件数 */
	private String backOrderCountDisp;

	/** 倉庫ID(第一倉庫) */
	private String warehouseNm;

	/** 在庫数(第一倉庫) */
	private int stockNum = 0;

	/** システム倉庫ID */
	private long sysWarehouseId;

	/** 総在庫数入力値 */
	private int totalStockNumInput = 0;

	/** 在庫増減値 */
	private int updateStockNum = 0;

	/** 発注アラート数 */
	private int orderAlertNum;

	/** ロケーションNO */
	private String locationNo;

	/** 原価 */
	private int cost;

	/** 組立可数*/
	private int assemblyNum;

	/** 削除チェックボックス：在庫情報 */
	private String deleteCheckFlg;

	/** 削除チェックボックス：販売情報 */
	private String orderCheckFlg;

	/** 注文数(注文プール用) */
	private int orderNum;

	/** 未入荷商品合計数 */
	private int sumNotInStock;

	/** 変更前注文プールフラグ */
	private String beforeOrderPoolFlg;

	/** 会社工場名 */
	private String companyFactoryNm;

	/** 通貨 */
	private String currencyNm;

	/** 国 */
	private String country;

	/** 年間平均売上数 */
	private long annualAverageSalesNum;
	/** 1ヶ月前売上数 */
	private long oneMonthAgo;
	/** 2ヶ月前売上数 */
	private long twoMonthAgo;
	/** 3ヶ月前売上数 */
	private long threeMonthAgo;
	/** 4ヶ月前売上数 */
	private long fourMonthAgo;
	/** 5ヶ月前売上数 */
	private long fiveMonthAgo;
	/** 6ヶ月前売上数 */
	private long sixMonthAgo;
	/** 7ヶ月前売上数 */
	private long sevenMonthAgo;
	/** 8ヶ月前売上数 */
	private long eightMonthAgo;
	/** 9ヶ月前売上数 */
	private long nineMonthAgo;
	/** 10ヶ月前売上数 */
	private long tenMonthAgo;
	/** 11ヶ月前売上数 */
	private long elevenMonthAgo;
	/** 12ヶ月前売上数 */
	private long twelveMonthAgo;

	/** 注文アラート対象フラグ */
	private String deliveryAlertTargetFlg = "0";

	/** 総在庫数入力値 */
	private int temporaryStockNum = 0;
	public int getTemporaryStockNum() {
	    return temporaryStockNum;
	}
	public void setTemporaryStockNum(int temporaryStockNum) {
	    this.temporaryStockNum = temporaryStockNum;
	}

	
	/**
	 * システム入荷予定IDを取得します。
	 * @return システム入荷予定ID
	 */
	public long getSysArrivalScheduleId() {
	    return sysArrivalScheduleId;
	}

	/**
	 * システム入荷予定IDを設定します。
	 * @param sysArrivalScheduleId システム入荷予定ID
	 */
	public void setSysArrivalScheduleId(long sysArrivalScheduleId) {
	    this.sysArrivalScheduleId = sysArrivalScheduleId;
	}

	/**
	 * システム倉庫在庫IDを取得します。
	 * @return システム倉庫在庫ID
	 */
	public long getSysWarehouseStockId() {
	    return sysWarehouseStockId;
	}

	/**
	 * システム倉庫在庫IDを設定します。
	 * @param sysWarehouseStockId システム倉庫在庫ID
	 */
	public void setSysWarehouseStockId(long sysWarehouseStockId) {
	    this.sysWarehouseStockId = sysWarehouseStockId;
	}

	/**
	 * 発注日を取得します。
	 * @return 発注日
	 */
	public String getItemOrderDate() {
	    return itemOrderDate;
	}

	/**
	 * 発注日を設定します。
	 * @param itemOrderDate 発注日
	 */
	public void setItemOrderDate(String itemOrderDate) {
	    this.itemOrderDate = itemOrderDate;
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
	 * 入荷数を取得します。
	 * @return 入荷数
	 */
	public int getArrivalNum() {
	    return arrivalNum;
	}

	/**
	 * 入荷数を設定します。
	 * @param arrivalNum 入荷数
	 */
	public void setArrivalNum(int arrivalNum) {
	    this.arrivalNum = arrivalNum;
	}

	/**
	 * 入荷数を取得します。
	 * @return 入荷数
	 */
	public String getArrivalNumDisp() {
	    return arrivalNumDisp;
	}

	/**
	 * 入荷数を設定します。
	 * @param arrivalNumDisp 入荷数
	 */
	public void setArrivalNumDisp(String arrivalNumDisp) {
	    this.arrivalNumDisp = arrivalNumDisp;
	}

	/**
	 * バックオーダー日を取得します。
	 * @return バックオーダー日
	 */
	public String getBackOrderDate() {
	    return backOrderDate;
	}

	/**
	 * バックオーダー日を設定します。
	 * @param backOrderDate バックオーダー日
	 */
	public void setBackOrderDate(String backOrderDate) {
	    this.backOrderDate = backOrderDate;
	}

	/**
	 * バックオーダー件数を取得します。
	 * @return バックオーダー件数
	 */
	public int getBackOrderCount() {
	    return backOrderCount;
	}

	/**
	 * バックオーダー件数を設定します。
	 * @param backOrderCount バックオーダー件数
	 */
	public void setBackOrderCount(int backOrderCount) {
	    this.backOrderCount = backOrderCount;
	}

	/**
	 * バックオーダー件数を取得します。
	 * @return バックオーダー件数
	 */
	public String getBackOrderCountDisp() {
	    return backOrderCountDisp;
	}

	/**
	 * バックオーダー件数を設定します。
	 * @param backOrderCountDisp バックオーダー件数
	 */
	public void setBackOrderCountDisp(String backOrderCountDisp) {
	    this.backOrderCountDisp = backOrderCountDisp;
	}

	/**
	 * 倉庫ID(第一倉庫)を取得します。
	 * @return 倉庫ID(第一倉庫)
	 */
	public String getWarehouseNm() {
	    return warehouseNm;
	}

	/**
	 * 倉庫ID(第一倉庫)を設定します。
	 * @param warehouseNm 倉庫ID(第一倉庫)
	 */
	public void setWarehouseNm(String warehouseNm) {
	    this.warehouseNm = warehouseNm;
	}

	/**
	 * 在庫数(第一倉庫)を取得します。
	 * @return 在庫数(第一倉庫)
	 */
	public int getStockNum() {
	    return stockNum;
	}

	/**
	 * 在庫数(第一倉庫)を設定します。
	 * @param stockNum 在庫数(第一倉庫)
	 */
	public void setStockNum(int stockNum) {
	    this.stockNum = stockNum;
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
	 * 総在庫数入力値を取得します。
	 * @return 総在庫数入力値
	 */
	public int getTotalStockNumInput() {
	    return totalStockNumInput;
	}

	/**
	 * 総在庫数入力値を設定します。
	 * @param totalStockNumInput 総在庫数入力値
	 */
	public void setTotalStockNumInput(int totalStockNumInput) {
	    this.totalStockNumInput = totalStockNumInput;
	}

	/**
	 * 在庫増減値を取得します。
	 * @return 在庫増減値
	 */
	public int getUpdateStockNum() {
	    return updateStockNum;
	}

	/**
	 * 在庫増減値を設定します。
	 * @param updateStockNum 在庫増減値
	 */
	public void setUpdateStockNum(int updateStockNum) {
	    this.updateStockNum = updateStockNum;
	}

	/**
	 * 発注アラート数を取得します。
	 * @return 発注アラート数
	 */
	public int getOrderAlertNum() {
	    return orderAlertNum;
	}

	/**
	 * 発注アラート数を設定します。
	 * @param orderAlertNum 発注アラート数
	 */
	public void setOrderAlertNum(int orderAlertNum) {
	    this.orderAlertNum = orderAlertNum;
	}

	/**
	 * ロケーションNOを取得します。
	 * @return ロケーションNO
	 */
	public String getLocationNo() {
	    return locationNo;
	}

	/**
	 * ロケーションNOを設定します。
	 * @param locationNo ロケーションNO
	 */
	public void setLocationNo(String locationNo) {
	    this.locationNo = locationNo;
	}

	/**
	 * 原価を取得します。
	 * @return 原価
	 */
	public int getCost() {
	    return cost;
	}

	/**
	 * 原価を設定します。
	 * @param cost 原価
	 */
	public void setCost(int cost) {
	    this.cost = cost;
	}

	/**
	 * 組立可数を取得します。
	 * @return 組立可数
	 */
	public int getAssemblyNum() {
	    return assemblyNum;
	}

	/**
	 * 組立可数を設定します。
	 * @param assemblyNum 組立可数
	 */
	public void setAssemblyNum(int assemblyNum) {
	    this.assemblyNum = assemblyNum;
	}

	/**
	 * 削除チェックボックス：在庫情報を取得します。
	 * @return 削除チェックボックス：在庫情報
	 */
	public String getDeleteCheckFlg() {
	    return deleteCheckFlg;
	}

	/**
	 * 削除チェックボックス：在庫情報を設定します。
	 * @param deleteCheckFlg 削除チェックボックス：在庫情報
	 */
	public void setDeleteCheckFlg(String deleteCheckFlg) {
	    this.deleteCheckFlg = deleteCheckFlg;
	}

	/**
	 * 削除チェックボックス：販売情報を取得します。
	 * @return 削除チェックボックス：販売情報
	 */
	public String getOrderCheckFlg() {
	    return orderCheckFlg;
	}

	/**
	 * 削除チェックボックス：販売情報を設定します。
	 * @param orderCheckFlg 削除チェックボックス：販売情報
	 */
	public void setOrderCheckFlg(String orderCheckFlg) {
	    this.orderCheckFlg = orderCheckFlg;
	}

	/**
	 * 注文数(注文プール用)を取得します。
	 * @return 注文数(注文プール用)
	 */
	public int getOrderNum() {
	    return orderNum;
	}

	/**
	 * 注文数(注文プール用)を設定します。
	 * @param orderNum 注文数(注文プール用)
	 */
	public void setOrderNum(int orderNum) {
	    this.orderNum = orderNum;
	}

	/**
	 * 未入荷商品合計数を取得します。
	 * @return 未入荷商品合計数
	 */
	public int getSumNotInStock() {
	    return sumNotInStock;
	}

	/**
	 * 未入荷商品合計数を設定します。
	 * @param sumNotInStock 未入荷商品合計数
	 */
	public void setSumNotInStock(int sumNotInStock) {
	    this.sumNotInStock = sumNotInStock;
	}

	/**
	 * 変更前注文プールフラグを取得します。
	 * @return 変更前注文プールフラグ
	 */
	public String getBeforeOrderPoolFlg() {
	    return beforeOrderPoolFlg;
	}

	/**
	 * 変更前注文プールフラグを設定します。
	 * @param beforeOrderPoolFlg 変更前注文プールフラグ
	 */
	public void setBeforeOrderPoolFlg(String beforeOrderPoolFlg) {
	    this.beforeOrderPoolFlg = beforeOrderPoolFlg;
	}

	/**
	 * 会社工場名を取得します。
	 * @return 会社工場名
	 */
	public String getCompanyFactoryNm() {
	    return companyFactoryNm;
	}

	/**
	 * 会社工場名を設定します。
	 * @param companyFactoryNm 会社工場名
	 */
	public void setCompanyFactoryNm(String companyFactoryNm) {
	    this.companyFactoryNm = companyFactoryNm;
	}

	/**
	 * 通貨を取得します。
	 * @return 通貨
	 */
	public String getCurrencyNm() {
	    return currencyNm;
	}

	/**
	 * 通貨を設定します。
	 * @param currencyNm 通貨
	 */
	public void setCurrencyNm(String currencyNm) {
	    this.currencyNm = currencyNm;
	}

	/**
	 * 国を取得します。
	 * @return 国
	 */
	public String getCountry() {
	    return country;
	}

	/**
	 * 国を設定します。
	 * @param country 国
	 */
	public void setCountry(String country) {
	    this.country = country;
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
	 * 1ヶ月前売上数を取得します。
	 * @return 1ヶ月前売上数
	 */
	public long getOneMonthAgo() {
	    return oneMonthAgo;
	}

	/**
	 * 1ヶ月前売上数を設定します。
	 * @param oneMonthAgo 1ヶ月前売上数
	 */
	public void setOneMonthAgo(long oneMonthAgo) {
	    this.oneMonthAgo = oneMonthAgo;
	}

	/**
	 * 2ヶ月前売上数を取得します。
	 * @return 2ヶ月前売上数
	 */
	public long getTwoMonthAgo() {
	    return twoMonthAgo;
	}

	/**
	 * 2ヶ月前売上数を設定します。
	 * @param twoMonthAgo 2ヶ月前売上数
	 */
	public void setTwoMonthAgo(long twoMonthAgo) {
	    this.twoMonthAgo = twoMonthAgo;
	}

	/**
	 * 3ヶ月前売上数を取得します。
	 * @return 3ヶ月前売上数
	 */
	public long getThreeMonthAgo() {
	    return threeMonthAgo;
	}

	/**
	 * 3ヶ月前売上数を設定します。
	 * @param threeMonthAgo 3ヶ月前売上数
	 */
	public void setThreeMonthAgo(long threeMonthAgo) {
	    this.threeMonthAgo = threeMonthAgo;
	}

	/**
	 * 4ヶ月前売上数を取得します。
	 * @return 4ヶ月前売上数
	 */
	public long getFourMonthAgo() {
	    return fourMonthAgo;
	}

	/**
	 * 4ヶ月前売上数を設定します。
	 * @param fourMonthAgo 4ヶ月前売上数
	 */
	public void setFourMonthAgo(long fourMonthAgo) {
	    this.fourMonthAgo = fourMonthAgo;
	}

	/**
	 * 5ヶ月前売上数を取得します。
	 * @return 5ヶ月前売上数
	 */
	public long getFiveMonthAgo() {
	    return fiveMonthAgo;
	}

	/**
	 * 5ヶ月前売上数を設定します。
	 * @param fiveMonthAgo 5ヶ月前売上数
	 */
	public void setFiveMonthAgo(long fiveMonthAgo) {
	    this.fiveMonthAgo = fiveMonthAgo;
	}

	/**
	 * 6ヶ月前売上数を取得します。
	 * @return 6ヶ月前売上数
	 */
	public long getSixMonthAgo() {
	    return sixMonthAgo;
	}

	/**
	 * 6ヶ月前売上数を設定します。
	 * @param sixMonthAgo 6ヶ月前売上数
	 */
	public void setSixMonthAgo(long sixMonthAgo) {
	    this.sixMonthAgo = sixMonthAgo;
	}

	/**
	 * 7ヶ月前売上数を取得します。
	 * @return 7ヶ月前売上数
	 */
	public long getSevenMonthAgo() {
	    return sevenMonthAgo;
	}

	/**
	 * 7ヶ月前売上数を設定します。
	 * @param sevenMonthAgo 7ヶ月前売上数
	 */
	public void setSevenMonthAgo(long sevenMonthAgo) {
	    this.sevenMonthAgo = sevenMonthAgo;
	}

	/**
	 * 8ヶ月前売上数を取得します。
	 * @return 8ヶ月前売上数
	 */
	public long getEightMonthAgo() {
	    return eightMonthAgo;
	}

	/**
	 * 8ヶ月前売上数を設定します。
	 * @param eightMonthAgo 8ヶ月前売上数
	 */
	public void setEightMonthAgo(long eightMonthAgo) {
	    this.eightMonthAgo = eightMonthAgo;
	}

	/**
	 * 9ヶ月前売上数を取得します。
	 * @return 9ヶ月前売上数
	 */
	public long getNineMonthAgo() {
	    return nineMonthAgo;
	}

	/**
	 * 9ヶ月前売上数を設定します。
	 * @param nineMonthAgo 9ヶ月前売上数
	 */
	public void setNineMonthAgo(long nineMonthAgo) {
	    this.nineMonthAgo = nineMonthAgo;
	}

	/**
	 * 10ヶ月前売上数を取得します。
	 * @return 10ヶ月前売上数
	 */
	public long getTenMonthAgo() {
	    return tenMonthAgo;
	}

	/**
	 * 10ヶ月前売上数を設定します。
	 * @param tenMonthAgo 10ヶ月前売上数
	 */
	public void setTenMonthAgo(long tenMonthAgo) {
	    this.tenMonthAgo = tenMonthAgo;
	}

	/**
	 * 11ヶ月前売上数を取得します。
	 * @return 11ヶ月前売上数
	 */
	public long getElevenMonthAgo() {
	    return elevenMonthAgo;
	}

	/**
	 * 11ヶ月前売上数を設定します。
	 * @param elevenMonthAgo 11ヶ月前売上数
	 */
	public void setElevenMonthAgo(long elevenMonthAgo) {
	    this.elevenMonthAgo = elevenMonthAgo;
	}

	/**
	 * 12ヶ月前売上数を取得します。
	 * @return 12ヶ月前売上数
	 */
	public long getTwelveMonthAgo() {
	    return twelveMonthAgo;
	}

	/**
	 * 12ヶ月前売上数を設定します。
	 * @param twelveMonthAgo 12ヶ月前売上数
	 */
	public void setTwelveMonthAgo(long twelveMonthAgo) {
	    this.twelveMonthAgo = twelveMonthAgo;
	}

	/**
	 * 注文アラート対象フラグを取得します。
	 * @return 注文アラート対象フラグ
	 */
	public String getDeliveryAlertTargetFlg() {
	    return deliveryAlertTargetFlg;
	}

	/**
	 * 注文アラート対象フラグを設定します。
	 * @param deliveryAlertTargetFlg 注文アラート対象フラグ
	 */
	public void setDeliveryAlertTargetFlg(String deliveryAlertTargetFlg) {
	    this.deliveryAlertTargetFlg = deliveryAlertTargetFlg;
	}


}
