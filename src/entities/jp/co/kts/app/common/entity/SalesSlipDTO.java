/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 売上伝票情報を格納します。
 *
 * @author admin
 */
public class SalesSlipDTO  {

	/** システム売上伝票ID */
	private long sysSalesSlipId;

	/** システム法人ID */
	private long sysCorporationId;

	/** システム販売チャネルID */
	private long sysChannelId;

	/** システム店舗ID */
	private long sysStoreId;

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
	/** 会員番号（自社サイト） */
	private String menberNo;

	/** 入金日 */
	private String depositDate;

	/** 登録担当者 */
	private String registryStaff;

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

	/** 備考（お届け先） */
	private String senderRemarks;

	/** 一言メモ（お届け先） */
	private String senderMemo;

	/** 送り状種別 */
	private String invoiceClassification;

	/** 伝票番号 */
	private String slipNo;

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

	/** 代引請求金額 */
	private int cashOnDeliveryCommission;

	/** ステータス */
	private String status;

	/** 送料 */
	private int postage;

	/** 代引き手数料 */
	private int codCommission;

	/** 消費税 */
	private int consumptionTax;

	/** 非商品 */
	private int discommondity;

	/** ギフト */
	private int gift;

	/** 合計請求金額 */
	private int sumClaimPrice;

	/** 商品単価小計 */
	private int sumPieceRate;

	/** 処理ルート */
	private String disposalRoute;

	/** 納品書備考 */
	private String deliveryRemarks;

	/** 購入回数 */
	private int buyCount;

	/** 税率 */
	private int taxRate;

	/** 税区分 */
	private String taxClass;

	/** 税 */
	private int tax;

	/** ピッキングリスト出力フラグ */
	private String pickingListFlg;

	/** 出庫完了フラグ */
	private String leavingFlg;

	/** 返品フラグ */
	private String returnFlg;

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

	/** 出荷日 */
	private String shipmentDate;

	/**
	 * <p>
	 * システム売上伝票ID を返却します。
	 * </p>
	 * @return sysSalesSlipId
	 */
	public long getSysSalesSlipId() {
		return this.sysSalesSlipId;
	}

	/**
	 * <p>
	 * システム売上伝票ID を設定します。
	 * </p>
	 * @param sysSalesSlipId
	 */
	public void setSysSalesSlipId(long sysSalesSlipId) {
		this.sysSalesSlipId = sysSalesSlipId;
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
	 * システム販売チャネルID を返却します。
	 * </p>
	 * @return sysChannelId
	 */
	public long getSysChannelId() {
		return this.sysChannelId;
	}

	/**
	 * <p>
	 * システム販売チャネルID を設定します。
	 * </p>
	 * @param sysChannelId
	 */
	public void setSysChannelId(long sysChannelId) {
		this.sysChannelId = sysChannelId;
	}

	/**
	 * <p>
	 * システム店舗ID を返却します。
	 * </p>
	 * @return sysStoreId
	 */
	public long getSysStoreId() {
		return this.sysStoreId;
	}

	/**
	 * <p>
	 * システム店舗ID を設定します。
	 * </p>
	 * @param sysStoreId
	 */
	public void setSysStoreId(long sysStoreId) {
		this.sysStoreId = sysStoreId;
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
	 * ステータス を返却します。
	 * </p>
	 * @return status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * <p>
	 * ステータス を設定します。
	 * </p>
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * <p>
	 * 送料 を返却します。
	 * </p>
	 * @return postage
	 */
	public int getPostage() {
		return this.postage;
	}

	/**
	 * <p>
	 * 送料 を設定します。
	 * </p>
	 * @param postage
	 */
	public void setPostage(int postage) {
		this.postage = postage;
	}

	/**
	 * <p>
	 * 代引き手数料 を返却します。
	 * </p>
	 * @return codCommission
	 */
	public int getCodCommission() {
		return this.codCommission;
	}

	/**
	 * <p>
	 * 代引き手数料 を設定します。
	 * </p>
	 * @param codCommission
	 */
	public void setCodCommission(int codCommission) {
		this.codCommission = codCommission;
	}

	/**
	 * <p>
	 * 消費税 を返却します。
	 * </p>
	 * @return consumptionTax
	 */
	public int getConsumptionTax() {
		return this.consumptionTax;
	}

	/**
	 * <p>
	 * 消費税 を設定します。
	 * </p>
	 * @param consumptionTax
	 */
	public void setConsumptionTax(int consumptionTax) {
		this.consumptionTax = consumptionTax;
	}

	/**
	 * <p>
	 * 非商品 を返却します。
	 * </p>
	 * @return discommondity
	 */
	public int getDiscommondity() {
		return this.discommondity;
	}

	/**
	 * <p>
	 * 非商品 を設定します。
	 * </p>
	 * @param discommondity
	 */
	public void setDiscommondity(int discommondity) {
		this.discommondity = discommondity;
	}

	/**
	 * <p>
	 * ギフト を返却します。
	 * </p>
	 * @return gift
	 */
	public int getGift() {
		return this.gift;
	}

	/**
	 * <p>
	 * ギフト を設定します。
	 * </p>
	 * @param gift
	 */
	public void setGift(int gift) {
		this.gift = gift;
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
	 * 商品単価小計 を返却します。
	 * </p>
	 * @return sumPieceRate
	 */
	public int getSumPieceRate() {
		return this.sumPieceRate;
	}

	/**
	 * <p>
	 * 商品単価小計 を設定します。
	 * </p>
	 * @param sumPieceRate
	 */
	public void setSumPieceRate(int sumPieceRate) {
		this.sumPieceRate = sumPieceRate;
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
	 * 納品書備考 を返却します。
	 * </p>
	 * @return deliveryRemarks
	 */
	public String getDeliveryRemarks() {
		return this.deliveryRemarks;
	}

	/**
	 * <p>
	 * 納品書備考 を設定します。
	 * </p>
	 * @param deliveryRemarks
	 */
	public void setDeliveryRemarks(String deliveryRemarks) {
		this.deliveryRemarks = deliveryRemarks;
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
	 * 税率 を返却します。
	 * </p>
	 * @return taxRate
	 */
	public int getTaxRate() {
		return this.taxRate;
	}

	/**
	 * <p>
	 * 税率 を設定します。
	 * </p>
	 * @param taxRate
	 */
	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * <p>
	 * 税区分 を返却します。
	 * </p>
	 * @return taxClass
	 */
	public String getTaxClass() {
		return this.taxClass;
	}

	/**
	 * <p>
	 * 税区分 を設定します。
	 * </p>
	 * @param taxClass
	 */
	public void setTaxClass(String taxClass) {
		this.taxClass = taxClass;
	}

	/**
	 * <p>
	 * 税 を返却します。
	 * </p>
	 * @return tax
	 */
	public int getTax() {
		return this.tax;
	}

	/**
	 * <p>
	 * 税 を設定します。
	 * </p>
	 * @param tax
	 */
	public void setTax(int tax) {
		this.tax = tax;
	}

	/**
	 * <p>
	 * ピッキングリスト出力フラグ を返却します。
	 * </p>
	 * @return pickingListFlg
	 */
	public String getPickingListFlg() {
		return this.pickingListFlg;
	}

	/**
	 * <p>
	 * ピッキングリスト出力フラグ を設定します。
	 * </p>
	 * @param pickingListFlg
	 */
	public void setPickingListFlg(String pickingListFlg) {
		this.pickingListFlg = pickingListFlg;
	}

	/**
	 * <p>
	 * 出庫完了フラグ を返却します。
	 * </p>
	 * @return leavingFlg
	 */
	public String getLeavingFlg() {
		return this.leavingFlg;
	}

	/**
	 * <p>
	 * 出庫完了フラグ を設定します。
	 * </p>
	 * @param leavingFlg
	 */
	public void setLeavingFlg(String leavingFlg) {
		this.leavingFlg = leavingFlg;
	}

	/**
	 * <p>
	 * 返品フラグ を返却します。
	 * </p>
	 * @return returnFlg
	 */
	public String getReturnFlg() {
		return this.returnFlg;
	}

	/**
	 * <p>
	 * 返品フラグ を設定します。
	 * </p>
	 * @param returnFlg
	 */
	public void setReturnFlg(String returnFlg) {
		this.returnFlg = returnFlg;
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
	 * @return shipmentDate
	 */
	public String getShipmentDate() {
		return shipmentDate;
	}

	/**
	 * @param shipmentDate セットする shipmentDate
	 */
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

}

