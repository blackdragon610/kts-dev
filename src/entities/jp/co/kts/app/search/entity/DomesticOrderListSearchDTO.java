/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.app.search.entity;


/**
 * 国内注文書作成画面、検索情報を格納します。
 *
 * @author n_nozawa
 * 20161212
 */
public class DomesticOrderListSearchDTO {

	/**システム国内商品ID*/
	private long sysDomesticItemId;

	/**システム伝票ID*/
	private long sysDomesticSlipId;
	
	/**会社ID*/
	private long sysCorporationId;


	/** 注文書作成日From */
	private String orderCreatDateFrom;

	/** 注文書作成日To */
	private String orderCreatDateTo;

	/** 入荷予定日From */
	private String arrivalScheduleDateFrom;

	/** 入荷予定日To */
	private String arrivalScheduleDateTo;

	/** メーカー */
	private String makerId;

	/** メーカー名称カナ */
	private String makerNmKana;

	/** 商品名 */
	private String itemNm;

	/** 受注番号 */
	private String ordeNo;

	/** メーカー品番 */
	private String makerCode;

	/** 数量 */
	private String orderNum;

	/** 通番 */
	private String serealNum;

	/** ステータス */
	private String status;

	/** 入荷担当 */
	private String stockCharge;

	/** 注文担当 */
	private String orderCharge;

	/** 仕入原価From */
	private String purchasingCostFrom;

	/** 仕入原価To */
	private String purchasingCostTo;

	/** 対応者 */
	private String personCharge;

	/** 対応日From */
	private String chargeDateFrom;

	/** 対応日To */
	private String chargeDateTo;

	/** 対応 */
	private String correspondence;

	/** 並び順 */
	private String sortFirst;

	/** 降順、昇順 */
	private String sortFirstSub;

	/** ページ最大件数 ：初期表示時「100件」*/
	private String listPageMax = "3";

	private String otherListPageMax = "2";

	/** セットするためのもの */
	private String checkDate;

	/** チェックされたものからセレクト */
	private String checkSelect;

	/** システムメーカーID */
	private long sysMakerId;

	/** 数値検索タイプ */
	private String numberOrderType;

	/** 一括入力タイプ */
	private String shipment;

	/** 一括入力値 */
	private String move;

	/** 一括入力：原価 */
	private String kindCost;

	/** 印刷確認フラグ */
	private String printCheckFlag;

	/** 納入先種別 */
	private String deliveryType;

	/** 未印刷 */
	private String notPrintData;

	/** 印刷済み */
	private String printedData;

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
	 * 注文書作成日Fromを取得します。
	 * @return 注文書作成日From
	 */
	public String getOrderCreatDateFrom() {
	    return orderCreatDateFrom;
	}

	/**
	 * 注文書作成日Fromを設定します。
	 * @param orderCreatDateFrom 注文書作成日From
	 */
	public void setOrderCreatDateFrom(String orderCreatDateFrom) {
	    this.orderCreatDateFrom = orderCreatDateFrom;
	}

	/**
	 * 注文書作成日Toを取得します。
	 * @return 注文書作成日To
	 */
	public String getOrderCreatDateTo() {
	    return orderCreatDateTo;
	}

	/**
	 * 注文書作成日Toを設定します。
	 * @param orderCreatDateTo 注文書作成日To
	 */
	public void setOrderCreatDateTo(String orderCreatDateTo) {
	    this.orderCreatDateTo = orderCreatDateTo;
	}

	/**
	 * 入荷予定日Fromを取得します。
	 * @return 入荷予定日From
	 */
	public String getArrivalScheduleDateFrom() {
	    return arrivalScheduleDateFrom;
	}

	/**
	 * 入荷予定日Fromを設定します。
	 * @param arrivalScheduleDateFrom 入荷予定日From
	 */
	public void setArrivalScheduleDateFrom(String arrivalScheduleDateFrom) {
	    this.arrivalScheduleDateFrom = arrivalScheduleDateFrom;
	}

	/**
	 * 入荷予定日Toを取得します。
	 * @return 入荷予定日To
	 */
	public String getArrivalScheduleDateTo() {
	    return arrivalScheduleDateTo;
	}

	/**
	 * 入荷予定日Toを設定します。
	 * @param arrivalScheduleDateTo 入荷予定日To
	 */
	public void setArrivalScheduleDateTo(String arrivalScheduleDateTo) {
	    this.arrivalScheduleDateTo = arrivalScheduleDateTo;
	}

	/**
	 * メーカーを取得します。
	 * @return メーカー
	 */
	public String getMakerId() {
	    return makerId;
	}

	/**
	 * メーカーを設定します。
	 * @param makerId メーカー
	 */
	public void setMakerId(String makerId) {
	    this.makerId = makerId;
	}

	/**
	 * メーカー名称カナを取得します。
	 * @return メーカー名称カナ
	 */
	public String getMakerNmKana() {
	    return makerNmKana;
	}

	/**
	 * メーカー名称カナを設定します。
	 * @param makerNmKana メーカー名称カナ
	 */
	public void setMakerNmKana(String makerNmKana) {
	    this.makerNmKana = makerNmKana;
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
	 * 受注番号を取得します。
	 * @return 受注番号
	 */
	public String getOrdeNo() {
	    return ordeNo;
	}

	/**
	 * 受注番号を設定します。
	 * @param ordeNo 受注番号
	 */
	public void setOrdeNo(String ordeNo) {
	    this.ordeNo = ordeNo;
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
	 * 数量を取得します。
	 * @return 数量
	 */
	public String getOrderNum() {
	    return orderNum;
	}

	/**
	 * 数量を設定します。
	 * @param orderNum 数量
	 */
	public void setOrderNum(String orderNum) {
	    this.orderNum = orderNum;
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
	 * 仕入原価Fromを取得します。
	 * @return 仕入原価From
	 */
	public String getPurchasingCostFrom() {
	    return purchasingCostFrom;
	}

	/**
	 * 仕入原価Fromを設定します。
	 * @param purchasingCostFrom 仕入原価From
	 */
	public void setPurchasingCostFrom(String purchasingCostFrom) {
	    this.purchasingCostFrom = purchasingCostFrom;
	}

	/**
	 * 仕入原価Toを取得します。
	 * @return 仕入原価To
	 */
	public String getPurchasingCostTo() {
	    return purchasingCostTo;
	}

	/**
	 * 仕入原価Toを設定します。
	 * @param purchasingCostTo 仕入原価To
	 */
	public void setPurchasingCostTo(String purchasingCostTo) {
	    this.purchasingCostTo = purchasingCostTo;
	}

	/**
	 * 対応者を取得します。
	 * @return 対応者
	 */
	public String getPersonCharge() {
	    return personCharge;
	}

	/**
	 * 対応者を設定します。
	 * @param personCharge 対応者
	 */
	public void setPersonCharge(String personCharge) {
	    this.personCharge = personCharge;
	}

	/**
	 * 対応日Fromを取得します。
	 * @return 対応日From
	 */
	public String getChargeDateFrom() {
	    return chargeDateFrom;
	}

	/**
	 * 対応日Fromを設定します。
	 * @param chargeDateFrom 対応日From
	 */
	public void setChargeDateFrom(String chargeDateFrom) {
	    this.chargeDateFrom = chargeDateFrom;
	}

	/**
	 * 対応日Toを取得します。
	 * @return 対応日To
	 */
	public String getChargeDateTo() {
	    return chargeDateTo;
	}

	/**
	 * 対応日Toを設定します。
	 * @param chargeDateTo 対応日To
	 */
	public void setChargeDateTo(String chargeDateTo) {
	    this.chargeDateTo = chargeDateTo;
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
	 * ページ最大件数を取得します。
	 * @return ページ最大件数
	 */
	public String getOtherListPageMax() {
	    return otherListPageMax;
	}

	/**
	 * ページ最大件数を設定します。
	 * @param listPageMax ページ最大件数
	 */
	public void setOtherListPageMax(String otherListPageMax) {
	    this.otherListPageMax = otherListPageMax;
	}

	/**
	 * セットするためのものを取得します。
	 * @return セットするためのもの
	 */
	public String getCheckDate() {
	    return checkDate;
	}

	/**
	 * セットするためのものを設定します。
	 * @param checkDate セットするためのもの
	 */
	public void setCheckDate(String checkDate) {
	    this.checkDate = checkDate;
	}

	/**
	 * チェックされたものからセレクトを取得します。
	 * @return チェックされたものからセレクト
	 */
	public String getCheckSelect() {
	    return checkSelect;
	}

	/**
	 * チェックされたものからセレクトを設定します。
	 * @param checkSelect チェックされたものからセレクト
	 */
	public void setCheckSelect(String checkSelect) {
	    this.checkSelect = checkSelect;
	}

	/**
	 * システムメーカーIDを取得します。
	 * @return システムメーカーID
	 */
	public long getSysMakerId() {
	    return sysMakerId;
	}

	/**
	 * システムメーカーIDを設定します。
	 * @param sysMakerId システムメーカーID
	 */
	public void setSysMakerId(long sysMakerId) {
	    this.sysMakerId = sysMakerId;
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
	 * 一括入力タイプを取得します。
	 * @return 一括入力タイプ
	 */
	public String getShipment() {
	    return shipment;
	}

	/**
	 * 一括入力タイプを設定します。
	 * @param shipment 一括入力タイプ
	 */
	public void setShipment(String shipment) {
	    this.shipment = shipment;
	}

	/**
	 * 一括入力値を取得します。
	 * @return 一括入力値
	 */
	public String getMove() {
	    return move;
	}

	/**
	 * 一括入力値を設定します。
	 * @param move 一括入力値
	 */
	public void setMove(String move) {
	    this.move = move;
	}

	/**
	 * 一括入力：原価を取得します。
	 * @return 一括入力：原価
	 */
	public String getKindCost() {
	    return kindCost;
	}

	/**
	 * 一括入力：原価を設定します。
	 * @param kindCost 一括入力：原価
	 */
	public void setKindCost(String kindCost) {
	    this.kindCost = kindCost;
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
	 * 納入先種別を取得します。
	 * @return 納入先種別
	 */
	public String getDeliveryType() {
	    return deliveryType;
	}

	/**
	 * 納入先種別を設定します。
	 * @param deliveryType 納入先種別
	 */
	public void setDeliveryType(String deliveryType) {
	    this.deliveryType = deliveryType;
	}

	/**
	 * 未印刷を取得します。
	 * @return 未印刷
	 */
	public String getNotPrintData() {
	    return notPrintData;
	}

	/**
	 * 未印刷を設定します。
	 * @param notPrintData 未印刷
	 */
	public void setNotPrintData(String notPrintData) {
	    this.notPrintData = notPrintData;
	}

	/**
	 * 印刷済みを取得します。
	 * @return 印刷済み
	 */
	public String getPrintedData() {
	    return printedData;
	}

	/**
	 * 印刷済みを設定します。
	 * @param printedData 印刷済み
	 */
	public void setPrintedData(String printedData) {
	    this.printedData = printedData;
	}


	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}
}
