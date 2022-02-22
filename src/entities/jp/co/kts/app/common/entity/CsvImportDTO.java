/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * CSV_インポート情報を格納します。
 *
 * @author admin
 */
public class CsvImportDTO  {

	/** システムインポートID */
	private long sysImportId;

	/** システム法人ID */
	private long sysCorporationId;

	/** データ区分 */
	private String dataDivision;

	/** 受注ルート */
	private String orderRoute;

	/** ファイル名 */
	private String fileNm;

	/** 受注番号 */
	private String orderNo;

	/** 注文日 */
	private String orderDate;

	/** 注文時間 */
	private String orderTime;

	/** 注文者名（姓） */
	private String orderFamilyNm;

	/** 注文者名（名） */
	private String orderFirstNm;

	/** 注文者名（セイ） */
	private String orderFamilyNmKana;

	/** 注文者名（メイ） */
	private String orderFirstNmKana;

	/** 注文者メールアドレス */
	private String orderMailAddress;

	/** 注文者郵便番号 */
	private String orderZip;

	/** 注文者住所（都道府県） */
	private String orderPrefectures;

	/** 注文者住所（市区町村） */
	private String orderMunicipality;

	/** 注文者住所（市区町村以降） */
	private String orderAddress;

	/** 注文者住所（建物名等） */
	private String orderBuildingNm;

	/** 注文者会社名 */
	private String orderCompanyNm;

	/** 注文者部署名 */
	private String orderQuarter;

	/** 注文者電話番号 */
	private String orderTel;

	/** 決済方法 */
	private String accountMethod;

	/** 決済手数料 */
	private int accountCommission;

	/** ご利用ポイント */
	private int usedPoint;

	/** 獲得ポイント */
	private int getPoint;

	/** 備考（注文） */
	private String orderRemarks;

	/** 一言メモ（注文） */
	private String orderMemo;

	/** 備考/一言メモ（注文） */
	private String orderRemarksMemo;

	/** 合計請求金額 */
	private int sumClaimPrice;

	/** 会員番号（自社サイト） */
	private String menberNo;

	/** 入金日 */
	private String depositDate;

	/** 登録担当者 */
	private String registryStaff;

	/** お届け先区分 */
	private String destinationDivision;

	/** お届け先名（姓） */
	private String destinationFamilyNm;

	/** お届け先名（名） */
	private String destinationFirstNm;

	/** お届け先名（セイ） */
	private String destinationFamilyNmKana;

	/** お届け先名（メイ） */
	private String destinationFirstNmKana;

	/** お届け先郵便番号 */
	private String destinationZip;

	/** お届け先住所（都道府県） */
	private String destinationPrefectures;

	/** お届け先住所（市区町村） */
	private String destinationMunicipality;

	/** お届け先住所（市区町村以降） */
	private String destinationAddress;

	/** お届け先住所（建物名等） */
	private String destinationBuildingNm;

	/** お届け先会社名 */
	private String destinationCompanyNm;

	/** お届け先部署名 */
	private String destinationQuarter;

	/** お届け先電話番号 */
	private String destinationTel;

	/** 送り主区分 */
	private String senderDivision;

	/** 送り主名（姓） */
	private String senderFamilyNm;

	/** 送り主名（名） */
	private String senderFirstNm;

	/** 送り主名（セイ） */
	private String senderFamilyNmKana;

	/** 送り主名（メイ） */
	private String senderFirstNmKana;

	/** 送り主郵便番号 */
	private String senderZip;

	/** 送り主住所（都道府県） */
	private String senderPrefectures;

	/** 送り主住所（市区町村） */
	private String senderMunicipality;

	/** 送り主住所（市区町村以降） */
	private String senderAddress;

	/** 送り主住所（建物名等） */
	private String senderBuildingNm;

	/** 送り主会社名 */
	private String senderCompanyNm;

	/** 送り主部署名 */
	private String senderQuarter;

	/** 送り主電話番号 */
	private String senderTel;

	/** 備考（お届け先） */
	private String senderRemarks;

	/** 一言メモ（お届け先） */
	private String senderMemo;

	/** ギフトメッセージ */
	private String giftMessage;

	/** 伝票区分 */
	private int slipDivision;

	/** 送り状種別 */
	private String invoiceClassification;

	/** 伝票番号 */
	private String slipNo;

	/** 代引請求金額 */
	private int cashOnDeliveryCommission;

	/** 温度区分 */
	private String temperatureDivision;

	/** お届け指定日 */
	private String destinationAppointDate;

	/** お届け時間帯 */
	private String destinationAppointTime;

	/** 出荷予定日 */
	private String shipmentPlanDate;

	/** 運送会社システム */
	private String transportCorporationSystem;

	/** 一言メモ（伝票） */
	private String slipMemo;

	/** 最終ステータス */
	private String lastStatus;

	/** 保留ステータス */
	private String reservationStatus;

	/** 同梱元 */
	private String combineSource;

	/** 同梱先 */
	private String combinePoint;

	/** 商品区分 */
	private int itemDivision;

	/** 商品種別 */
	private String itemClassification;

	/** 商品コード（店舗） */
	private String shopItemCd;

	/** 商品名 */
	private String itemNm;

	/** 個数 */
	private int itemNum;

	/** 単価 */
	private int pieceRate;

	/** オプション１（助ネコ） */
	private String optionSukenekoOne;

	/** オプション２（助ネコ） */
	private String optionSukenekoTwo;

	/** 商品コード（助ネコ） */
	private String sukenekoItemCd;

	/** 未入金額 */
	private int unpaidPrice;

	/** 送り状記事欄 */
	private String invoiceArticle;

	/** 伝票管理番号 */
	private String slipManagementNo;

	/** 処理ルート */
	private String disposalRoute;

	/** 処理済日 */
	private String disposalDate;

	/** 自社商品コード */
	private String ownCompanyCd;

	/** 購入回数 */
	private int buyCount;

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

	/** 取り込み日 */
	private String importDate;

	/**
	 * <p>
	 * システムインポートID を返却します。
	 * </p>
	 * @return sysImportId
	 */
	public long getSysImportId() {
		return this.sysImportId;
	}

	/**
	 * <p>
	 * システムインポートID を設定します。
	 * </p>
	 * @param sysImportId
	 */
	public void setSysImportId(long sysImportId) {
		this.sysImportId = sysImportId;
	}

	/**
	 * <p>
	 * システム法人ID を返却します。
	 * </p>
	 * @return sysCorporationId
	 */
	public long getSysCorporationId() {
		return this.sysCorporationId;
	}

	/**
	 * <p>
	 * システム法人ID を設定します。
	 * </p>
	 * @param sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * <p>
	 * データ区分 を返却します。
	 * </p>
	 * @return dataDivision
	 */
	public String getDataDivision() {
		return this.dataDivision;
	}

	/**
	 * <p>
	 * データ区分 を設定します。
	 * </p>
	 * @param dataDivision
	 */
	public void setDataDivision(String dataDivision) {
		this.dataDivision = dataDivision;
	}

	/**
	 * <p>
	 * 受注ルート を返却します。
	 * </p>
	 * @return orderRoute
	 */
	public String getOrderRoute() {
		return this.orderRoute;
	}

	/**
	 * <p>
	 * 受注ルート を設定します。
	 * </p>
	 * @param orderRoute
	 */
	public void setOrderRoute(String orderRoute) {
		this.orderRoute = orderRoute;
	}



	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	/**
	 * <p>
	 * 受注番号 を返却します。
	 * </p>
	 * @return orderNo
	 */
	public String getOrderNo() {
		return this.orderNo;
	}

	/**
	 * <p>
	 * 受注番号 を設定します。
	 * </p>
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * <p>
	 * 注文日 を返却します。
	 * </p>
	 * @return orderDate
	 */
	public String getOrderDate() {
		return this.orderDate;
	}

	/**
	 * <p>
	 * 注文日 を設定します。
	 * </p>
	 * @param orderDate
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * <p>
	 * 注文時間 を返却します。
	 * </p>
	 * @return orderTime
	 */
	public String getOrderTime() {
		return this.orderTime;
	}

	/**
	 * <p>
	 * 注文時間 を設定します。
	 * </p>
	 * @param orderTime
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * <p>
	 * 注文者名（姓） を返却します。
	 * </p>
	 * @return orderFamilyNm
	 */
	public String getOrderFamilyNm() {
		return this.orderFamilyNm;
	}

	/**
	 * <p>
	 * 注文者名（姓） を設定します。
	 * </p>
	 * @param orderFamilyNm
	 */
	public void setOrderFamilyNm(String orderFamilyNm) {
		this.orderFamilyNm = orderFamilyNm;
	}

	/**
	 * <p>
	 * 注文者名（名） を返却します。
	 * </p>
	 * @return orderFirstNm
	 */
	public String getOrderFirstNm() {
		return this.orderFirstNm;
	}

	/**
	 * <p>
	 * 注文者名（名） を設定します。
	 * </p>
	 * @param orderFirstNm
	 */
	public void setOrderFirstNm(String orderFirstNm) {
		this.orderFirstNm = orderFirstNm;
	}

	/**
	 * <p>
	 * 注文者名（セイ） を返却します。
	 * </p>
	 * @return orderFamilyNmKana
	 */
	public String getOrderFamilyNmKana() {
		return this.orderFamilyNmKana;
	}

	/**
	 * <p>
	 * 注文者名（セイ） を設定します。
	 * </p>
	 * @param orderFamilyNmKana
	 */
	public void setOrderFamilyNmKana(String orderFamilyNmKana) {
		this.orderFamilyNmKana = orderFamilyNmKana;
	}

	/**
	 * <p>
	 * 注文者名（メイ） を返却します。
	 * </p>
	 * @return orderFirstNmKana
	 */
	public String getOrderFirstNmKana() {
		return this.orderFirstNmKana;
	}

	/**
	 * <p>
	 * 注文者名（メイ） を設定します。
	 * </p>
	 * @param orderFirstNmKana
	 */
	public void setOrderFirstNmKana(String orderFirstNmKana) {
		this.orderFirstNmKana = orderFirstNmKana;
	}

	/**
	 * <p>
	 * 注文者メールアドレス を返却します。
	 * </p>
	 * @return orderMailAddress
	 */
	public String getOrderMailAddress() {
		return this.orderMailAddress;
	}

	/**
	 * <p>
	 * 注文者メールアドレス を設定します。
	 * </p>
	 * @param orderMailAddress
	 */
	public void setOrderMailAddress(String orderMailAddress) {
		this.orderMailAddress = orderMailAddress;
	}

	/**
	 * <p>
	 * 注文者郵便番号 を返却します。
	 * </p>
	 * @return orderZip
	 */
	public String getOrderZip() {
		return this.orderZip;
	}

	/**
	 * <p>
	 * 注文者郵便番号 を設定します。
	 * </p>
	 * @param orderZip
	 */
	public void setOrderZip(String orderZip) {
		this.orderZip = orderZip;
	}

	/**
	 * <p>
	 * 注文者住所（都道府県） を返却します。
	 * </p>
	 * @return orderPrefectures
	 */
	public String getOrderPrefectures() {
		return this.orderPrefectures;
	}

	/**
	 * <p>
	 * 注文者住所（都道府県） を設定します。
	 * </p>
	 * @param orderPrefectures
	 */
	public void setOrderPrefectures(String orderPrefectures) {
		this.orderPrefectures = orderPrefectures;
	}

	/**
	 * <p>
	 * 注文者住所（市区町村） を返却します。
	 * </p>
	 * @return orderMunicipality
	 */
	public String getOrderMunicipality() {
		return this.orderMunicipality;
	}

	/**
	 * <p>
	 * 注文者住所（市区町村） を設定します。
	 * </p>
	 * @param orderMunicipality
	 */
	public void setOrderMunicipality(String orderMunicipality) {
		this.orderMunicipality = orderMunicipality;
	}

	/**
	 * <p>
	 * 注文者住所（市区町村以降） を返却します。
	 * </p>
	 * @return orderAddress
	 */
	public String getOrderAddress() {
		return this.orderAddress;
	}

	/**
	 * <p>
	 * 注文者住所（市区町村以降） を設定します。
	 * </p>
	 * @param orderAddress
	 */
	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	/**
	 * <p>
	 * 注文者住所（建物名等） を返却します。
	 * </p>
	 * @return orderBuildingNm
	 */
	public String getOrderBuildingNm() {
		return this.orderBuildingNm;
	}

	/**
	 * <p>
	 * 注文者住所（建物名等） を設定します。
	 * </p>
	 * @param orderBuildingNm
	 */
	public void setOrderBuildingNm(String orderBuildingNm) {
		this.orderBuildingNm = orderBuildingNm;
	}

	/**
	 * <p>
	 * 注文者会社名 を返却します。
	 * </p>
	 * @return orderCompanyNm
	 */
	public String getOrderCompanyNm() {
		return this.orderCompanyNm;
	}

	/**
	 * <p>
	 * 注文者会社名 を設定します。
	 * </p>
	 * @param orderCompanyNm
	 */
	public void setOrderCompanyNm(String orderCompanyNm) {
		this.orderCompanyNm = orderCompanyNm;
	}

	/**
	 * <p>
	 * 注文者部署名 を返却します。
	 * </p>
	 * @return orderQuarter
	 */
	public String getOrderQuarter() {
		return this.orderQuarter;
	}

	/**
	 * <p>
	 * 注文者部署名 を設定します。
	 * </p>
	 * @param orderQuarter
	 */
	public void setOrderQuarter(String orderQuarter) {
		this.orderQuarter = orderQuarter;
	}

	/**
	 * <p>
	 * 注文者電話番号 を返却します。
	 * </p>
	 * @return orderTel
	 */
	public String getOrderTel() {
		return this.orderTel;
	}

	/**
	 * <p>
	 * 注文者電話番号 を設定します。
	 * </p>
	 * @param orderTel
	 */
	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}

	/**
	 * <p>
	 * 決済方法 を返却します。
	 * </p>
	 * @return accountMethod
	 */
	public String getAccountMethod() {
		return this.accountMethod;
	}

	/**
	 * <p>
	 * 決済方法 を設定します。
	 * </p>
	 * @param accountMethod
	 */
	public void setAccountMethod(String accountMethod) {
		this.accountMethod = accountMethod;
	}

	/**
	 * <p>
	 * 決済手数料 を返却します。
	 * </p>
	 * @return accountCommission
	 */
	public int getAccountCommission() {
		return this.accountCommission;
	}

	/**
	 * <p>
	 * 決済手数料 を設定します。
	 * </p>
	 * @param accountCommission
	 */
	public void setAccountCommission(int accountCommission) {
		this.accountCommission = accountCommission;
	}

	/**
	 * <p>
	 * ご利用ポイント を返却します。
	 * </p>
	 * @return usedPoint
	 */
	public int getUsedPoint() {
		return this.usedPoint;
	}

	/**
	 * <p>
	 * ご利用ポイント を設定します。
	 * </p>
	 * @param usedPoint
	 */
	public void setUsedPoint(int usedPoint) {
		this.usedPoint = usedPoint;
	}

	/**
	 * <p>
	 * 獲得ポイント を返却します。
	 * </p>
	 * @return getPoint
	 */
	public int getGetPoint() {
		return this.getPoint;
	}

	/**
	 * <p>
	 * 獲得ポイント を設定します。
	 * </p>
	 * @param getPoint
	 */
	public void setGetPoint(int getPoint) {
		this.getPoint = getPoint;
	}

	/**
	 * <p>
	 * 備考（注文） を返却します。
	 * </p>
	 * @return orderRemarks
	 */
	public String getOrderRemarks() {
		return this.orderRemarks;
	}

	/**
	 * <p>
	 * 備考（注文） を設定します。
	 * </p>
	 * @param orderRemarks
	 */
	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	/**
	 * <p>
	 * 一言メモ（注文） を返却します。
	 * </p>
	 * @return orderMemo
	 */
	public String getOrderMemo() {
		return this.orderMemo;
	}

	/**
	 * <p>
	 * 一言メモ（注文） を設定します。
	 * </p>
	 * @param orderMemo
	 */
	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	/**
	 * 備考/一言メモ（注文）を取得します。
	 * @return 備考/一言メモ（注文）
	 */
	public String getOrderRemarksMemo() {
	    return orderRemarksMemo;
	}

	/**
	 * 備考/一言メモ（注文）を設定します。
	 * @param orderRemarksMemo 備考/一言メモ（注文）
	 */
	public void setOrderRemarksMemo(String orderRemarksMemo) {
	    this.orderRemarksMemo = orderRemarksMemo;
	}

	/**
	 * <p>
	 * 合計請求金額 を返却します。
	 * </p>
	 * @return sumClaimPrice
	 */
	public int getSumClaimPrice() {
		return this.sumClaimPrice;
	}

	/**
	 * <p>
	 * 合計請求金額 を設定します。
	 * </p>
	 * @param sumClaimPrice
	 */
	public void setSumClaimPrice(int sumClaimPrice) {
		this.sumClaimPrice = sumClaimPrice;
	}

	/**
	 * <p>
	 * 会員番号（自社サイト） を返却します。
	 * </p>
	 * @return menberNo
	 */
	public String getMenberNo() {
		return this.menberNo;
	}

	/**
	 * <p>
	 * 会員番号（自社サイト） を設定します。
	 * </p>
	 * @param menberNo
	 */
	public void setMenberNo(String menberNo) {
		this.menberNo = menberNo;
	}

	/**
	 * <p>
	 * 入金日 を返却します。
	 * </p>
	 * @return depositDate
	 */
	public String getDepositDate() {
		return this.depositDate;
	}

	/**
	 * <p>
	 * 入金日 を設定します。
	 * </p>
	 * @param depositDate
	 */
	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	/**
	 * <p>
	 * 登録担当者 を返却します。
	 * </p>
	 * @return registryStaff
	 */
	public String getRegistryStaff() {
		return this.registryStaff;
	}

	/**
	 * <p>
	 * 登録担当者 を設定します。
	 * </p>
	 * @param registryStaff
	 */
	public void setRegistryStaff(String registryStaff) {
		this.registryStaff = registryStaff;
	}

	/**
	 * <p>
	 * お届け先区分 を返却します。
	 * </p>
	 * @return destinationDivision
	 */
	public String getDestinationDivision() {
		return this.destinationDivision;
	}

	/**
	 * <p>
	 * お届け先区分 を設定します。
	 * </p>
	 * @param destinationDivision
	 */
	public void setDestinationDivision(String destinationDivision) {
		this.destinationDivision = destinationDivision;
	}

	/**
	 * <p>
	 * お届け先名（姓） を返却します。
	 * </p>
	 * @return destinationFamilyNm
	 */
	public String getDestinationFamilyNm() {
		return this.destinationFamilyNm;
	}

	/**
	 * <p>
	 * お届け先名（姓） を設定します。
	 * </p>
	 * @param destinationFamilyNm
	 */
	public void setDestinationFamilyNm(String destinationFamilyNm) {
		this.destinationFamilyNm = destinationFamilyNm;
	}

	/**
	 * <p>
	 * お届け先名（名） を返却します。
	 * </p>
	 * @return destinationFirstNm
	 */
	public String getDestinationFirstNm() {
		return this.destinationFirstNm;
	}

	/**
	 * <p>
	 * お届け先名（名） を設定します。
	 * </p>
	 * @param destinationFirstNm
	 */
	public void setDestinationFirstNm(String destinationFirstNm) {
		this.destinationFirstNm = destinationFirstNm;
	}

	/**
	 * <p>
	 * お届け先名（セイ） を返却します。
	 * </p>
	 * @return destinationFamilyNmKana
	 */
	public String getDestinationFamilyNmKana() {
		return this.destinationFamilyNmKana;
	}

	/**
	 * <p>
	 * お届け先名（セイ） を設定します。
	 * </p>
	 * @param destinationFamilyNmKana
	 */
	public void setDestinationFamilyNmKana(String destinationFamilyNmKana) {
		this.destinationFamilyNmKana = destinationFamilyNmKana;
	}

	/**
	 * <p>
	 * お届け先名（メイ） を返却します。
	 * </p>
	 * @return destinationFirstNmKana
	 */
	public String getDestinationFirstNmKana() {
		return this.destinationFirstNmKana;
	}

	/**
	 * <p>
	 * お届け先名（メイ） を設定します。
	 * </p>
	 * @param destinationFirstNmKana
	 */
	public void setDestinationFirstNmKana(String destinationFirstNmKana) {
		this.destinationFirstNmKana = destinationFirstNmKana;
	}

	/**
	 * <p>
	 * お届け先郵便番号 を返却します。
	 * </p>
	 * @return destinationZip
	 */
	public String getDestinationZip() {
		return this.destinationZip;
	}

	/**
	 * <p>
	 * お届け先郵便番号 を設定します。
	 * </p>
	 * @param destinationZip
	 */
	public void setDestinationZip(String destinationZip) {
		this.destinationZip = destinationZip;
	}

	/**
	 * <p>
	 * お届け先住所（都道府県） を返却します。
	 * </p>
	 * @return destinationPrefectures
	 */
	public String getDestinationPrefectures() {
		return this.destinationPrefectures;
	}

	/**
	 * <p>
	 * お届け先住所（都道府県） を設定します。
	 * </p>
	 * @param destinationPrefectures
	 */
	public void setDestinationPrefectures(String destinationPrefectures) {
		this.destinationPrefectures = destinationPrefectures;
	}

	/**
	 * <p>
	 * お届け先住所（市区町村） を返却します。
	 * </p>
	 * @return destinationMunicipality
	 */
	public String getDestinationMunicipality() {
		return this.destinationMunicipality;
	}

	/**
	 * <p>
	 * お届け先住所（市区町村） を設定します。
	 * </p>
	 * @param destinationMunicipality
	 */
	public void setDestinationMunicipality(String destinationMunicipality) {
		this.destinationMunicipality = destinationMunicipality;
	}

	/**
	 * <p>
	 * お届け先住所（市区町村以降） を返却します。
	 * </p>
	 * @return destinationAddress
	 */
	public String getDestinationAddress() {
		return this.destinationAddress;
	}

	/**
	 * <p>
	 * お届け先住所（市区町村以降） を設定します。
	 * </p>
	 * @param destinationAddress
	 */
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	/**
	 * <p>
	 * お届け先住所（建物名等） を返却します。
	 * </p>
	 * @return destinationBuildingNm
	 */
	public String getDestinationBuildingNm() {
		return this.destinationBuildingNm;
	}

	/**
	 * <p>
	 * お届け先住所（建物名等） を設定します。
	 * </p>
	 * @param destinationBuildingNm
	 */
	public void setDestinationBuildingNm(String destinationBuildingNm) {
		this.destinationBuildingNm = destinationBuildingNm;
	}

	/**
	 * <p>
	 * お届け先会社名 を返却します。
	 * </p>
	 * @return destinationCompanyNm
	 */
	public String getDestinationCompanyNm() {
		return this.destinationCompanyNm;
	}

	/**
	 * <p>
	 * お届け先会社名 を設定します。
	 * </p>
	 * @param destinationCompanyNm
	 */
	public void setDestinationCompanyNm(String destinationCompanyNm) {
		this.destinationCompanyNm = destinationCompanyNm;
	}

	/**
	 * <p>
	 * お届け先部署名 を返却します。
	 * </p>
	 * @return destinationQuarter
	 */
	public String getDestinationQuarter() {
		return this.destinationQuarter;
	}

	/**
	 * <p>
	 * お届け先部署名 を設定します。
	 * </p>
	 * @param destinationQuarter
	 */
	public void setDestinationQuarter(String destinationQuarter) {
		this.destinationQuarter = destinationQuarter;
	}

	/**
	 * <p>
	 * お届け先電話番号 を返却します。
	 * </p>
	 * @return destinationTel
	 */
	public String getDestinationTel() {
		return this.destinationTel;
	}

	/**
	 * <p>
	 * お届け先電話番号 を設定します。
	 * </p>
	 * @param destinationTel
	 */
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	/**
	 * <p>
	 * 送り主区分 を返却します。
	 * </p>
	 * @return senderDivision
	 */
	public String getSenderDivision() {
		return this.senderDivision;
	}

	/**
	 * <p>
	 * 送り主区分 を設定します。
	 * </p>
	 * @param senderDivision
	 */
	public void setSenderDivision(String senderDivision) {
		this.senderDivision = senderDivision;
	}

	/**
	 * <p>
	 * 送り主名（姓） を返却します。
	 * </p>
	 * @return senderFamilyNm
	 */
	public String getSenderFamilyNm() {
		return this.senderFamilyNm;
	}

	/**
	 * <p>
	 * 送り主名（姓） を設定します。
	 * </p>
	 * @param senderFamilyNm
	 */
	public void setSenderFamilyNm(String senderFamilyNm) {
		this.senderFamilyNm = senderFamilyNm;
	}

	/**
	 * <p>
	 * 送り主名（名） を返却します。
	 * </p>
	 * @return senderFirstNm
	 */
	public String getSenderFirstNm() {
		return this.senderFirstNm;
	}

	/**
	 * <p>
	 * 送り主名（名） を設定します。
	 * </p>
	 * @param senderFirstNm
	 */
	public void setSenderFirstNm(String senderFirstNm) {
		this.senderFirstNm = senderFirstNm;
	}

	/**
	 * <p>
	 * 送り主名（セイ） を返却します。
	 * </p>
	 * @return senderFamilyNmKana
	 */
	public String getSenderFamilyNmKana() {
		return this.senderFamilyNmKana;
	}

	/**
	 * <p>
	 * 送り主名（セイ） を設定します。
	 * </p>
	 * @param senderFamilyNmKana
	 */
	public void setSenderFamilyNmKana(String senderFamilyNmKana) {
		this.senderFamilyNmKana = senderFamilyNmKana;
	}

	/**
	 * <p>
	 * 送り主名（メイ） を返却します。
	 * </p>
	 * @return senderFirstNmKana
	 */
	public String getSenderFirstNmKana() {
		return this.senderFirstNmKana;
	}

	/**
	 * <p>
	 * 送り主名（メイ） を設定します。
	 * </p>
	 * @param senderFirstNmKana
	 */
	public void setSenderFirstNmKana(String senderFirstNmKana) {
		this.senderFirstNmKana = senderFirstNmKana;
	}

	/**
	 * <p>
	 * 送り主郵便番号 を返却します。
	 * </p>
	 * @return senderZip
	 */
	public String getSenderZip() {
		return this.senderZip;
	}

	/**
	 * <p>
	 * 送り主郵便番号 を設定します。
	 * </p>
	 * @param senderZip
	 */
	public void setSenderZip(String senderZip) {
		this.senderZip = senderZip;
	}

	/**
	 * <p>
	 * 送り主住所（都道府県） を返却します。
	 * </p>
	 * @return senderPrefectures
	 */
	public String getSenderPrefectures() {
		return this.senderPrefectures;
	}

	/**
	 * <p>
	 * 送り主住所（都道府県） を設定します。
	 * </p>
	 * @param senderPrefectures
	 */
	public void setSenderPrefectures(String senderPrefectures) {
		this.senderPrefectures = senderPrefectures;
	}

	/**
	 * <p>
	 * 送り主住所（市区町村） を返却します。
	 * </p>
	 * @return senderMunicipality
	 */
	public String getSenderMunicipality() {
		return this.senderMunicipality;
	}

	/**
	 * <p>
	 * 送り主住所（市区町村） を設定します。
	 * </p>
	 * @param senderMunicipality
	 */
	public void setSenderMunicipality(String senderMunicipality) {
		this.senderMunicipality = senderMunicipality;
	}

	/**
	 * <p>
	 * 送り主住所（市区町村以降） を返却します。
	 * </p>
	 * @return senderAddress
	 */
	public String getSenderAddress() {
		return this.senderAddress;
	}

	/**
	 * <p>
	 * 送り主住所（市区町村以降） を設定します。
	 * </p>
	 * @param senderAddress
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * <p>
	 * 送り主住所（建物名等） を返却します。
	 * </p>
	 * @return senderBuildingNm
	 */
	public String getSenderBuildingNm() {
		return this.senderBuildingNm;
	}

	/**
	 * <p>
	 * 送り主住所（建物名等） を設定します。
	 * </p>
	 * @param senderBuildingNm
	 */
	public void setSenderBuildingNm(String senderBuildingNm) {
		this.senderBuildingNm = senderBuildingNm;
	}

	/**
	 * <p>
	 * 送り主会社名 を返却します。
	 * </p>
	 * @return senderCompanyNm
	 */
	public String getSenderCompanyNm() {
		return this.senderCompanyNm;
	}

	/**
	 * <p>
	 * 送り主会社名 を設定します。
	 * </p>
	 * @param senderCompanyNm
	 */
	public void setSenderCompanyNm(String senderCompanyNm) {
		this.senderCompanyNm = senderCompanyNm;
	}

	/**
	 * <p>
	 * 送り主部署名 を返却します。
	 * </p>
	 * @return senderQuarter
	 */
	public String getSenderQuarter() {
		return this.senderQuarter;
	}

	/**
	 * <p>
	 * 送り主部署名 を設定します。
	 * </p>
	 * @param senderQuarter
	 */
	public void setSenderQuarter(String senderQuarter) {
		this.senderQuarter = senderQuarter;
	}

	/**
	 * <p>
	 * 送り主電話番号 を返却します。
	 * </p>
	 * @return senderTel
	 */
	public String getSenderTel() {
		return this.senderTel;
	}

	/**
	 * <p>
	 * 送り主電話番号 を設定します。
	 * </p>
	 * @param senderTel
	 */
	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}

	/**
	 * <p>
	 * 備考（お届け先） を返却します。
	 * </p>
	 * @return senderRemarks
	 */
	public String getSenderRemarks() {
		return this.senderRemarks;
	}

	/**
	 * <p>
	 * 備考（お届け先） を設定します。
	 * </p>
	 * @param senderRemarks
	 */
	public void setSenderRemarks(String senderRemarks) {
		this.senderRemarks = senderRemarks;
	}

	/**
	 * <p>
	 * 一言メモ（お届け先） を返却します。
	 * </p>
	 * @return senderMemo
	 */
	public String getSenderMemo() {
		return this.senderMemo;
	}

	/**
	 * <p>
	 * 一言メモ（お届け先） を設定します。
	 * </p>
	 * @param senderMemo
	 */
	public void setSenderMemo(String senderMemo) {
		this.senderMemo = senderMemo;
	}

	/**
	 * <p>
	 * ギフトメッセージ を返却します。
	 * </p>
	 * @return giftMessage
	 */
	public String getGiftMessage() {
		return this.giftMessage;
	}

	/**
	 * <p>
	 * ギフトメッセージ を設定します。
	 * </p>
	 * @param giftMessage
	 */
	public void setGiftMessage(String giftMessage) {
		this.giftMessage = giftMessage;
	}

	/**
	 * <p>
	 * 伝票区分 を返却します。
	 * </p>
	 * @return slipDivision
	 */
	public int getSlipDivision() {
		return this.slipDivision;
	}

	/**
	 * <p>
	 * 伝票区分 を設定します。
	 * </p>
	 * @param slipDivision
	 */
	public void setSlipDivision(int slipDivision) {
		this.slipDivision = slipDivision;
	}

	/**
	 * <p>
	 * 送り状種別 を返却します。
	 * </p>
	 * @return invoiceClassification
	 */
	public String getInvoiceClassification() {
		return this.invoiceClassification;
	}

	/**
	 * <p>
	 * 送り状種別 を設定します。
	 * </p>
	 * @param invoiceClassification
	 */
	public void setInvoiceClassification(String invoiceClassification) {
		this.invoiceClassification = invoiceClassification;
	}

	/**
	 * <p>
	 * 伝票番号 を返却します。
	 * </p>
	 * @return slipNo
	 */
	public String getSlipNo() {
		return this.slipNo;
	}

	/**
	 * <p>
	 * 伝票番号 を設定します。
	 * </p>
	 * @param slipNo
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * <p>
	 * 代引請求金額 を返却します。
	 * </p>
	 * @return cashOnDeliveryCommission
	 */
	public int getCashOnDeliveryCommission() {
		return this.cashOnDeliveryCommission;
	}

	/**
	 * <p>
	 * 代引請求金額 を設定します。
	 * </p>
	 * @param cashOnDeliveryCommission
	 */
	public void setCashOnDeliveryCommission(int cashOnDeliveryCommission) {
		this.cashOnDeliveryCommission = cashOnDeliveryCommission;
	}

	/**
	 * <p>
	 * 温度区分 を返却します。
	 * </p>
	 * @return temperatureDivision
	 */
	public String getTemperatureDivision() {
		return this.temperatureDivision;
	}

	/**
	 * <p>
	 * 温度区分 を設定します。
	 * </p>
	 * @param temperatureDivision
	 */
	public void setTemperatureDivision(String temperatureDivision) {
		this.temperatureDivision = temperatureDivision;
	}

	/**
	 * <p>
	 * お届け指定日 を返却します。
	 * </p>
	 * @return destinationAppointDate
	 */
	public String getDestinationAppointDate() {
		return this.destinationAppointDate;
	}

	/**
	 * <p>
	 * お届け指定日 を設定します。
	 * </p>
	 * @param destinationAppointDate
	 */
	public void setDestinationAppointDate(String destinationAppointDate) {
		this.destinationAppointDate = destinationAppointDate;
	}

	/**
	 * <p>
	 * お届け時間帯 を返却します。
	 * </p>
	 * @return destinationAppointTime
	 */
	public String getDestinationAppointTime() {
		return this.destinationAppointTime;
	}

	/**
	 * <p>
	 * お届け時間帯 を設定します。
	 * </p>
	 * @param destinationAppointTime
	 */
	public void setDestinationAppointTime(String destinationAppointTime) {
		this.destinationAppointTime = destinationAppointTime;
	}

	/**
	 * <p>
	 * 出荷予定日 を返却します。
	 * </p>
	 * @return shipmentPlanDate
	 */
	public String getShipmentPlanDate() {
		return this.shipmentPlanDate;
	}

	/**
	 * <p>
	 * 出荷予定日 を設定します。
	 * </p>
	 * @param shipmentPlanDate
	 */
	public void setShipmentPlanDate(String shipmentPlanDate) {
		this.shipmentPlanDate = shipmentPlanDate;
	}

	/**
	 * <p>
	 * 運送会社システム を返却します。
	 * </p>
	 * @return transportCorporationSystem
	 */
	public String getTransportCorporationSystem() {
		return this.transportCorporationSystem;
	}

	/**
	 * <p>
	 * 運送会社システム を設定します。
	 * </p>
	 * @param transportCorporationSystem
	 */
	public void setTransportCorporationSystem(String transportCorporationSystem) {
		this.transportCorporationSystem = transportCorporationSystem;
	}

	/**
	 * <p>
	 * 一言メモ（伝票） を返却します。
	 * </p>
	 * @return slipMemo
	 */
	public String getSlipMemo() {
		return this.slipMemo;
	}

	/**
	 * <p>
	 * 一言メモ（伝票） を設定します。
	 * </p>
	 * @param slipMemo
	 */
	public void setSlipMemo(String slipMemo) {
		this.slipMemo = slipMemo;
	}

	/**
	 * <p>
	 * 最終ステータス を返却します。
	 * </p>
	 * @return lastStatus
	 */
	public String getLastStatus() {
		return this.lastStatus;
	}

	/**
	 * <p>
	 * 最終ステータス を設定します。
	 * </p>
	 * @param lastStatus
	 */
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	/**
	 * <p>
	 * 保留ステータス を返却します。
	 * </p>
	 * @return reservationStatus
	 */
	public String getReservationStatus() {
		return this.reservationStatus;
	}

	/**
	 * <p>
	 * 保留ステータス を設定します。
	 * </p>
	 * @param reservationStatus
	 */
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	/**
	 * <p>
	 * 同梱元 を返却します。
	 * </p>
	 * @return combineSource
	 */
	public String getCombineSource() {
		return this.combineSource;
	}

	/**
	 * <p>
	 * 同梱元 を設定します。
	 * </p>
	 * @param combineSource
	 */
	public void setCombineSource(String combineSource) {
		this.combineSource = combineSource;
	}

	/**
	 * <p>
	 * 同梱先 を返却します。
	 * </p>
	 * @return combinePoint
	 */
	public String getCombinePoint() {
		return this.combinePoint;
	}

	/**
	 * <p>
	 * 同梱先 を設定します。
	 * </p>
	 * @param combinePoint
	 */
	public void setCombinePoint(String combinePoint) {
		this.combinePoint = combinePoint;
	}

	/**
	 * <p>
	 * 商品区分 を返却します。
	 * </p>
	 * @return itemDivision
	 */
	public int getItemDivision() {
		return this.itemDivision;
	}

	/**
	 * <p>
	 * 商品区分 を設定します。
	 * </p>
	 * @param itemDivision
	 */
	public void setItemDivision(int itemDivision) {
		this.itemDivision = itemDivision;
	}

	/**
	 * <p>
	 * 商品種別 を返却します。
	 * </p>
	 * @return itemClassification
	 */
	public String getItemClassification() {
		return this.itemClassification;
	}

	/**
	 * <p>
	 * 商品種別 を設定します。
	 * </p>
	 * @param itemClassification
	 */
	public void setItemClassification(String itemClassification) {
		this.itemClassification = itemClassification;
	}

	/**
	 * <p>
	 * 商品コード（店舗） を返却します。
	 * </p>
	 * @return shopItemCd
	 */
	public String getShopItemCd() {
		return this.shopItemCd;
	}

	/**
	 * <p>
	 * 商品コード（店舗） を設定します。
	 * </p>
	 * @param shopItemCd
	 */
	public void setShopItemCd(String shopItemCd) {
		this.shopItemCd = shopItemCd;
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
	 * 個数 を返却します。
	 * </p>
	 * @return itemNum
	 */
	public int getItemNum() {
		return this.itemNum;
	}

	/**
	 * <p>
	 * 個数 を設定します。
	 * </p>
	 * @param itemNum
	 */
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	/**
	 * <p>
	 * 単価 を返却します。
	 * </p>
	 * @return pieceRate
	 */
	public int getPieceRate() {
		return this.pieceRate;
	}

	/**
	 * <p>
	 * 単価 を設定します。
	 * </p>
	 * @param pieceRate
	 */
	public void setPieceRate(int pieceRate) {
		this.pieceRate = pieceRate;
	}

	/**
	 * <p>
	 * オプション１（助ネコ） を返却します。
	 * </p>
	 * @return optionSukenekoOne
	 */
	public String getOptionSukenekoOne() {
		return this.optionSukenekoOne;
	}

	/**
	 * <p>
	 * オプション１（助ネコ） を設定します。
	 * </p>
	 * @param optionSukenekoOne
	 */
	public void setOptionSukenekoOne(String optionSukenekoOne) {
		this.optionSukenekoOne = optionSukenekoOne;
	}

	/**
	 * <p>
	 * オプション２（助ネコ） を返却します。
	 * </p>
	 * @return optionSukenekoTwo
	 */
	public String getOptionSukenekoTwo() {
		return this.optionSukenekoTwo;
	}

	/**
	 * <p>
	 * オプション２（助ネコ） を設定します。
	 * </p>
	 * @param optionSukenekoTwo
	 */
	public void setOptionSukenekoTwo(String optionSukenekoTwo) {
		this.optionSukenekoTwo = optionSukenekoTwo;
	}

	/**
	 * <p>
	 * 商品コード（助ネコ） を返却します。
	 * </p>
	 * @return sukenekoItemCd
	 */
	public String getSukenekoItemCd() {
		return this.sukenekoItemCd;
	}

	/**
	 * <p>
	 * 商品コード（助ネコ） を設定します。
	 * </p>
	 * @param sukenekoItemCd
	 */
	public void setSukenekoItemCd(String sukenekoItemCd) {
		this.sukenekoItemCd = sukenekoItemCd;
	}

	/**
	 * <p>
	 * 未入金額 を返却します。
	 * </p>
	 * @return unpaidPrice
	 */
	public int getUnpaidPrice() {
		return this.unpaidPrice;
	}

	/**
	 * <p>
	 * 未入金額 を設定します。
	 * </p>
	 * @param unpaidPrice
	 */
	public void setUnpaidPrice(int unpaidPrice) {
		this.unpaidPrice = unpaidPrice;
	}

	/**
	 * <p>
	 * 送り状記事欄 を返却します。
	 * </p>
	 * @return invoiceArticle
	 */
	public String getInvoiceArticle() {
		return this.invoiceArticle;
	}

	/**
	 * <p>
	 * 送り状記事欄 を設定します。
	 * </p>
	 * @param invoiceArticle
	 */
	public void setInvoiceArticle(String invoiceArticle) {
		this.invoiceArticle = invoiceArticle;
	}

	/**
	 * <p>
	 * 伝票管理番号 を返却します。
	 * </p>
	 * @return slipManagementNo
	 */
	public String getSlipManagementNo() {
		return this.slipManagementNo;
	}

	/**
	 * <p>
	 * 伝票管理番号 を設定します。
	 * </p>
	 * @param slipManagementNo
	 */
	public void setSlipManagementNo(String slipManagementNo) {
		this.slipManagementNo = slipManagementNo;
	}

	/**
	 * <p>
	 * 処理ルート を返却します。
	 * </p>
	 * @return disposalRoute
	 */
	public String getDisposalRoute() {
		return this.disposalRoute;
	}

	/**
	 * <p>
	 * 処理ルート を設定します。
	 * </p>
	 * @param disposalRoute
	 */
	public void setDisposalRoute(String disposalRoute) {
		this.disposalRoute = disposalRoute;
	}

	/**
	 * <p>
	 * 処理済日 を返却します。
	 * </p>
	 * @return disposalDate
	 */
	public String getDisposalDate() {
		return this.disposalDate;
	}

	/**
	 * <p>
	 * 処理済日 を設定します。
	 * </p>
	 * @param disposalDate
	 */
	public void setDisposalDate(String disposalDate) {
		this.disposalDate = disposalDate;
	}

	/**
	 * <p>
	 * 自社商品コード を返却します。
	 * </p>
	 * @return ownCompanyCd
	 */
	public String getOwnCompanyCd() {
		return this.ownCompanyCd;
	}

	/**
	 * <p>
	 * 自社商品コード を設定します。
	 * </p>
	 * @param ownCompanyCd
	 */
	public void setOwnCompanyCd(String ownCompanyCd) {
		this.ownCompanyCd = ownCompanyCd;
	}

	/**
	 * <p>
	 * 購入回数 を返却します。
	 * </p>
	 * @return buyCount
	 */
	public int getBuyCount() {
		return this.buyCount;
	}

	/**
	 * <p>
	 * 購入回数 を設定します。
	 * </p>
	 * @param buyCount
	 */
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
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
	 * <p>
	 * 取り込み日を返却します
	 * </p>
	 * @return
	 */
	public String getImportDate() {
		return importDate;
	}

	/**
	 * <p>
	 * 取り込み日を設定します
	 * </p>
	 * @param importDate
	 */
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

}

