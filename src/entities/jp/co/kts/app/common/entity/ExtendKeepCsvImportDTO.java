package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 在庫数キープインポートテーブルのエンティティクラス
 * @author Boncre
 *
 */
public class ExtendKeepCsvImportDTO extends MstItemDTO{

	/** 在庫キープインポートID */
	private long sysKeepImportId;

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

	/** 外部倉庫キープ対象 */
	private String externalKeepFlag;

	/** 外部倉庫コード */
	private String externalWarehouseCode;

	public String getExternalKeepFlag() {
		return externalKeepFlag;
	}

	public void setExternalKeepFlag(String externalKeepFlag) {
		this.externalKeepFlag = externalKeepFlag;
	}

	public String getExternalWarehouseCode() {
		return externalWarehouseCode;
	}

	public void setExternalWarehouseCode(String externalWarehouseCode) {
		this.externalWarehouseCode = externalWarehouseCode;
	}

	/**
	 * 国内システムインポートIDを取得します。
	 * @return 在庫数キープインポートID
	 */
	public long getSysKeepImportId() {
	    return sysKeepImportId;
	}

	/**
	 * 在庫数キープインポートIDを設定します。
	 * @param sysKeepImportId 在庫数キープインポートID
	 */
	public void setSysKeepImportId(long sysDomesticImportId) {
	    this.sysKeepImportId = sysDomesticImportId;
	}

	/**
	 * システム法人IDを取得します。
	 * @return システム法人ID
	 */
	public long getSysCorporationId() {
	    return sysCorporationId;
	}

	/**
	 * システム法人IDを設定します。
	 * @param sysCorporationId システム法人ID
	 */
	public void setSysCorporationId(long sysCorporationId) {
	    this.sysCorporationId = sysCorporationId;
	}

	/**
	 * データ区分を取得します。
	 * @return データ区分
	 */
	public String getDataDivision() {
	    return dataDivision;
	}

	/**
	 * データ区分を設定します。
	 * @param dataDivision データ区分
	 */
	public void setDataDivision(String dataDivision) {
	    this.dataDivision = dataDivision;
	}

	/**
	 * 受注ルートを取得します。
	 * @return 受注ルート
	 */
	public String getOrderRoute() {
	    return orderRoute;
	}

	/**
	 * 受注ルートを設定します。
	 * @param orderRoute 受注ルート
	 */
	public void setOrderRoute(String orderRoute) {
	    this.orderRoute = orderRoute;
	}

	/**
	 * ファイル名を取得します。
	 * @return ファイル名
	 */
	public String getFileNm() {
	    return fileNm;
	}

	/**
	 * ファイル名を設定します。
	 * @param fileNm ファイル名
	 */
	public void setFileNm(String fileNm) {
	    this.fileNm = fileNm;
	}

	/**
	 * 受注番号を取得します。
	 * @return 受注番号
	 */
	public String getOrderNo() {
	    return orderNo;
	}

	/**
	 * 受注番号を設定します。
	 * @param orderNo 受注番号
	 */
	public void setOrderNo(String orderNo) {
	    this.orderNo = orderNo;
	}

	/**
	 * 注文日を取得します。
	 * @return 注文日
	 */
	public String getOrderDate() {
	    return orderDate;
	}

	/**
	 * 注文日を設定します。
	 * @param orderDate 注文日
	 */
	public void setOrderDate(String orderDate) {
	    this.orderDate = orderDate;
	}

	/**
	 * 注文時間を取得します。
	 * @return 注文時間
	 */
	public String getOrderTime() {
	    return orderTime;
	}

	/**
	 * 注文時間を設定します。
	 * @param orderTime 注文時間
	 */
	public void setOrderTime(String orderTime) {
	    this.orderTime = orderTime;
	}

	/**
	 * 注文者名（姓）を取得します。
	 * @return 注文者名（姓）
	 */
	public String getOrderFamilyNm() {
	    return orderFamilyNm;
	}

	/**
	 * 注文者名（姓）を設定します。
	 * @param orderFamilyNm 注文者名（姓）
	 */
	public void setOrderFamilyNm(String orderFamilyNm) {
	    this.orderFamilyNm = orderFamilyNm;
	}

	/**
	 * 注文者名（名）を取得します。
	 * @return 注文者名（名）
	 */
	public String getOrderFirstNm() {
	    return orderFirstNm;
	}

	/**
	 * 注文者名（名）を設定します。
	 * @param orderFirstNm 注文者名（名）
	 */
	public void setOrderFirstNm(String orderFirstNm) {
	    this.orderFirstNm = orderFirstNm;
	}

	/**
	 * 注文者名（セイ）を取得します。
	 * @return 注文者名（セイ）
	 */
	public String getOrderFamilyNmKana() {
	    return orderFamilyNmKana;
	}

	/**
	 * 注文者名（セイ）を設定します。
	 * @param orderFamilyNmKana 注文者名（セイ）
	 */
	public void setOrderFamilyNmKana(String orderFamilyNmKana) {
	    this.orderFamilyNmKana = orderFamilyNmKana;
	}

	/**
	 * 注文者名（メイ）を取得します。
	 * @return 注文者名（メイ）
	 */
	public String getOrderFirstNmKana() {
	    return orderFirstNmKana;
	}

	/**
	 * 注文者名（メイ）を設定します。
	 * @param orderFirstNmKana 注文者名（メイ）
	 */
	public void setOrderFirstNmKana(String orderFirstNmKana) {
	    this.orderFirstNmKana = orderFirstNmKana;
	}

	/**
	 * 注文者メールアドレスを取得します。
	 * @return 注文者メールアドレス
	 */
	public String getOrderMailAddress() {
	    return orderMailAddress;
	}

	/**
	 * 注文者メールアドレスを設定します。
	 * @param orderMailAddress 注文者メールアドレス
	 */
	public void setOrderMailAddress(String orderMailAddress) {
	    this.orderMailAddress = orderMailAddress;
	}

	/**
	 * 注文者郵便番号を取得します。
	 * @return 注文者郵便番号
	 */
	public String getOrderZip() {
	    return orderZip;
	}

	/**
	 * 注文者郵便番号を設定します。
	 * @param orderZip 注文者郵便番号
	 */
	public void setOrderZip(String orderZip) {
	    this.orderZip = orderZip;
	}

	/**
	 * 注文者住所（都道府県）を取得します。
	 * @return 注文者住所（都道府県）
	 */
	public String getOrderPrefectures() {
	    return orderPrefectures;
	}

	/**
	 * 注文者住所（都道府県）を設定します。
	 * @param orderPrefectures 注文者住所（都道府県）
	 */
	public void setOrderPrefectures(String orderPrefectures) {
	    this.orderPrefectures = orderPrefectures;
	}

	/**
	 * 注文者住所（市区町村）を取得します。
	 * @return 注文者住所（市区町村）
	 */
	public String getOrderMunicipality() {
	    return orderMunicipality;
	}

	/**
	 * 注文者住所（市区町村）を設定します。
	 * @param orderMunicipality 注文者住所（市区町村）
	 */
	public void setOrderMunicipality(String orderMunicipality) {
	    this.orderMunicipality = orderMunicipality;
	}

	/**
	 * 注文者住所（市区町村以降）を取得します。
	 * @return 注文者住所（市区町村以降）
	 */
	public String getOrderAddress() {
	    return orderAddress;
	}

	/**
	 * 注文者住所（市区町村以降）を設定します。
	 * @param orderAddress 注文者住所（市区町村以降）
	 */
	public void setOrderAddress(String orderAddress) {
	    this.orderAddress = orderAddress;
	}

	/**
	 * 注文者住所（建物名等）を取得します。
	 * @return 注文者住所（建物名等）
	 */
	public String getOrderBuildingNm() {
	    return orderBuildingNm;
	}

	/**
	 * 注文者住所（建物名等）を設定します。
	 * @param orderBuildingNm 注文者住所（建物名等）
	 */
	public void setOrderBuildingNm(String orderBuildingNm) {
	    this.orderBuildingNm = orderBuildingNm;
	}

	/**
	 * 注文者会社名を取得します。
	 * @return 注文者会社名
	 */
	public String getOrderCompanyNm() {
	    return orderCompanyNm;
	}

	/**
	 * 注文者会社名を設定します。
	 * @param orderCompanyNm 注文者会社名
	 */
	public void setOrderCompanyNm(String orderCompanyNm) {
	    this.orderCompanyNm = orderCompanyNm;
	}

	/**
	 * 注文者部署名を取得します。
	 * @return 注文者部署名
	 */
	public String getOrderQuarter() {
	    return orderQuarter;
	}

	/**
	 * 注文者部署名を設定します。
	 * @param orderQuarter 注文者部署名
	 */
	public void setOrderQuarter(String orderQuarter) {
	    this.orderQuarter = orderQuarter;
	}

	/**
	 * 注文者電話番号を取得します。
	 * @return 注文者電話番号
	 */
	public String getOrderTel() {
	    return orderTel;
	}

	/**
	 * 注文者電話番号を設定します。
	 * @param orderTel 注文者電話番号
	 */
	public void setOrderTel(String orderTel) {
	    this.orderTel = orderTel;
	}

	/**
	 * 決済方法を取得します。
	 * @return 決済方法
	 */
	public String getAccountMethod() {
	    return accountMethod;
	}

	/**
	 * 決済方法を設定します。
	 * @param accountMethod 決済方法
	 */
	public void setAccountMethod(String accountMethod) {
	    this.accountMethod = accountMethod;
	}

	/**
	 * 決済手数料を取得します。
	 * @return 決済手数料
	 */
	public int getAccountCommission() {
	    return accountCommission;
	}

	/**
	 * 決済手数料を設定します。
	 * @param accountCommission 決済手数料
	 */
	public void setAccountCommission(int accountCommission) {
	    this.accountCommission = accountCommission;
	}

	/**
	 * ご利用ポイントを取得します。
	 * @return ご利用ポイント
	 */
	public int getUsedPoint() {
	    return usedPoint;
	}

	/**
	 * ご利用ポイントを設定します。
	 * @param usedPoint ご利用ポイント
	 */
	public void setUsedPoint(int usedPoint) {
	    this.usedPoint = usedPoint;
	}

	/**
	 * 獲得ポイントを取得します。
	 * @return 獲得ポイント
	 */
	public int getGetPoint() {
	    return getPoint;
	}

	/**
	 * 獲得ポイントを設定します。
	 * @param getPoint 獲得ポイント
	 */
	public void setGetPoint(int getPoint) {
	    this.getPoint = getPoint;
	}

	/**
	 * 備考（注文）を取得します。
	 * @return 備考（注文）
	 */
	public String getOrderRemarks() {
	    return orderRemarks;
	}

	/**
	 * 備考（注文）を設定します。
	 * @param orderRemarks 備考（注文）
	 */
	public void setOrderRemarks(String orderRemarks) {
	    this.orderRemarks = orderRemarks;
	}

	/**
	 * 一言メモ（注文）を取得します。
	 * @return 一言メモ（注文）
	 */
	public String getOrderMemo() {
	    return orderMemo;
	}

	/**
	 * 一言メモ（注文）を設定します。
	 * @param orderMemo 一言メモ（注文）
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
	 * 合計請求金額を取得します。
	 * @return 合計請求金額
	 */
	public int getSumClaimPrice() {
	    return sumClaimPrice;
	}

	/**
	 * 合計請求金額を設定します。
	 * @param sumClaimPrice 合計請求金額
	 */
	public void setSumClaimPrice(int sumClaimPrice) {
	    this.sumClaimPrice = sumClaimPrice;
	}

	/**
	 * 会員番号（自社サイト）を取得します。
	 * @return 会員番号（自社サイト）
	 */
	public String getMenberNo() {
	    return menberNo;
	}

	/**
	 * 会員番号（自社サイト）を設定します。
	 * @param menberNo 会員番号（自社サイト）
	 */
	public void setMenberNo(String menberNo) {
	    this.menberNo = menberNo;
	}

	/**
	 * 入金日を取得します。
	 * @return 入金日
	 */
	public String getDepositDate() {
	    return depositDate;
	}

	/**
	 * 入金日を設定します。
	 * @param depositDate 入金日
	 */
	public void setDepositDate(String depositDate) {
	    this.depositDate = depositDate;
	}

	/**
	 * 登録担当者を取得します。
	 * @return 登録担当者
	 */
	public String getRegistryStaff() {
	    return registryStaff;
	}

	/**
	 * 登録担当者を設定します。
	 * @param registryStaff 登録担当者
	 */
	public void setRegistryStaff(String registryStaff) {
	    this.registryStaff = registryStaff;
	}

	/**
	 * お届け先区分を取得します。
	 * @return お届け先区分
	 */
	public String getDestinationDivision() {
	    return destinationDivision;
	}

	/**
	 * お届け先区分を設定します。
	 * @param destinationDivision お届け先区分
	 */
	public void setDestinationDivision(String destinationDivision) {
	    this.destinationDivision = destinationDivision;
	}

	/**
	 * お届け先名（姓）を取得します。
	 * @return お届け先名（姓）
	 */
	public String getDestinationFamilyNm() {
	    return destinationFamilyNm;
	}

	/**
	 * お届け先名（姓）を設定します。
	 * @param destinationFamilyNm お届け先名（姓）
	 */
	public void setDestinationFamilyNm(String destinationFamilyNm) {
	    this.destinationFamilyNm = destinationFamilyNm;
	}

	/**
	 * お届け先名（名）を取得します。
	 * @return お届け先名（名）
	 */
	public String getDestinationFirstNm() {
	    return destinationFirstNm;
	}

	/**
	 * お届け先名（名）を設定します。
	 * @param destinationFirstNm お届け先名（名）
	 */
	public void setDestinationFirstNm(String destinationFirstNm) {
	    this.destinationFirstNm = destinationFirstNm;
	}

	/**
	 * お届け先名（セイ）を取得します。
	 * @return お届け先名（セイ）
	 */
	public String getDestinationFamilyNmKana() {
	    return destinationFamilyNmKana;
	}

	/**
	 * お届け先名（セイ）を設定します。
	 * @param destinationFamilyNmKana お届け先名（セイ）
	 */
	public void setDestinationFamilyNmKana(String destinationFamilyNmKana) {
	    this.destinationFamilyNmKana = destinationFamilyNmKana;
	}

	/**
	 * お届け先名（メイ）を取得します。
	 * @return お届け先名（メイ）
	 */
	public String getDestinationFirstNmKana() {
	    return destinationFirstNmKana;
	}

	/**
	 * お届け先名（メイ）を設定します。
	 * @param destinationFirstNmKana お届け先名（メイ）
	 */
	public void setDestinationFirstNmKana(String destinationFirstNmKana) {
	    this.destinationFirstNmKana = destinationFirstNmKana;
	}

	/**
	 * お届け先郵便番号を取得します。
	 * @return お届け先郵便番号
	 */
	public String getDestinationZip() {
	    return destinationZip;
	}

	/**
	 * お届け先郵便番号を設定します。
	 * @param destinationZip お届け先郵便番号
	 */
	public void setDestinationZip(String destinationZip) {
	    this.destinationZip = destinationZip;
	}

	/**
	 * お届け先住所（都道府県）を取得します。
	 * @return お届け先住所（都道府県）
	 */
	public String getDestinationPrefectures() {
	    return destinationPrefectures;
	}

	/**
	 * お届け先住所（都道府県）を設定します。
	 * @param destinationPrefectures お届け先住所（都道府県）
	 */
	public void setDestinationPrefectures(String destinationPrefectures) {
	    this.destinationPrefectures = destinationPrefectures;
	}

	/**
	 * お届け先住所（市区町村）を取得します。
	 * @return お届け先住所（市区町村）
	 */
	public String getDestinationMunicipality() {
	    return destinationMunicipality;
	}

	/**
	 * お届け先住所（市区町村）を設定します。
	 * @param destinationMunicipality お届け先住所（市区町村）
	 */
	public void setDestinationMunicipality(String destinationMunicipality) {
	    this.destinationMunicipality = destinationMunicipality;
	}

	/**
	 * お届け先住所（市区町村以降）を取得します。
	 * @return お届け先住所（市区町村以降）
	 */
	public String getDestinationAddress() {
	    return destinationAddress;
	}

	/**
	 * お届け先住所（市区町村以降）を設定します。
	 * @param destinationAddress お届け先住所（市区町村以降）
	 */
	public void setDestinationAddress(String destinationAddress) {
	    this.destinationAddress = destinationAddress;
	}

	/**
	 * お届け先住所（建物名等）を取得します。
	 * @return お届け先住所（建物名等）
	 */
	public String getDestinationBuildingNm() {
	    return destinationBuildingNm;
	}

	/**
	 * お届け先住所（建物名等）を設定します。
	 * @param destinationBuildingNm お届け先住所（建物名等）
	 */
	public void setDestinationBuildingNm(String destinationBuildingNm) {
	    this.destinationBuildingNm = destinationBuildingNm;
	}

	/**
	 * お届け先会社名を取得します。
	 * @return お届け先会社名
	 */
	public String getDestinationCompanyNm() {
	    return destinationCompanyNm;
	}

	/**
	 * お届け先会社名を設定します。
	 * @param destinationCompanyNm お届け先会社名
	 */
	public void setDestinationCompanyNm(String destinationCompanyNm) {
	    this.destinationCompanyNm = destinationCompanyNm;
	}

	/**
	 * お届け先部署名を取得します。
	 * @return お届け先部署名
	 */
	public String getDestinationQuarter() {
	    return destinationQuarter;
	}

	/**
	 * お届け先部署名を設定します。
	 * @param destinationQuarter お届け先部署名
	 */
	public void setDestinationQuarter(String destinationQuarter) {
	    this.destinationQuarter = destinationQuarter;
	}

	/**
	 * お届け先電話番号を取得します。
	 * @return お届け先電話番号
	 */
	public String getDestinationTel() {
	    return destinationTel;
	}

	/**
	 * お届け先電話番号を設定します。
	 * @param destinationTel お届け先電話番号
	 */
	public void setDestinationTel(String destinationTel) {
	    this.destinationTel = destinationTel;
	}

	/**
	 * 送り主区分を取得します。
	 * @return 送り主区分
	 */
	public String getSenderDivision() {
	    return senderDivision;
	}

	/**
	 * 送り主区分を設定します。
	 * @param senderDivision 送り主区分
	 */
	public void setSenderDivision(String senderDivision) {
	    this.senderDivision = senderDivision;
	}

	/**
	 * 送り主名（姓）を取得します。
	 * @return 送り主名（姓）
	 */
	public String getSenderFamilyNm() {
	    return senderFamilyNm;
	}

	/**
	 * 送り主名（姓）を設定します。
	 * @param senderFamilyNm 送り主名（姓）
	 */
	public void setSenderFamilyNm(String senderFamilyNm) {
	    this.senderFamilyNm = senderFamilyNm;
	}

	/**
	 * 送り主名（名）を取得します。
	 * @return 送り主名（名）
	 */
	public String getSenderFirstNm() {
	    return senderFirstNm;
	}

	/**
	 * 送り主名（名）を設定します。
	 * @param senderFirstNm 送り主名（名）
	 */
	public void setSenderFirstNm(String senderFirstNm) {
	    this.senderFirstNm = senderFirstNm;
	}

	/**
	 * 送り主名（セイ）を取得します。
	 * @return 送り主名（セイ）
	 */
	public String getSenderFamilyNmKana() {
	    return senderFamilyNmKana;
	}

	/**
	 * 送り主名（セイ）を設定します。
	 * @param senderFamilyNmKana 送り主名（セイ）
	 */
	public void setSenderFamilyNmKana(String senderFamilyNmKana) {
	    this.senderFamilyNmKana = senderFamilyNmKana;
	}

	/**
	 * 送り主名（メイ）を取得します。
	 * @return 送り主名（メイ）
	 */
	public String getSenderFirstNmKana() {
	    return senderFirstNmKana;
	}

	/**
	 * 送り主名（メイ）を設定します。
	 * @param senderFirstNmKana 送り主名（メイ）
	 */
	public void setSenderFirstNmKana(String senderFirstNmKana) {
	    this.senderFirstNmKana = senderFirstNmKana;
	}

	/**
	 * 送り主郵便番号を取得します。
	 * @return 送り主郵便番号
	 */
	public String getSenderZip() {
	    return senderZip;
	}

	/**
	 * 送り主郵便番号を設定します。
	 * @param senderZip 送り主郵便番号
	 */
	public void setSenderZip(String senderZip) {
	    this.senderZip = senderZip;
	}

	/**
	 * 送り主住所（都道府県）を取得します。
	 * @return 送り主住所（都道府県）
	 */
	public String getSenderPrefectures() {
	    return senderPrefectures;
	}

	/**
	 * 送り主住所（都道府県）を設定します。
	 * @param senderPrefectures 送り主住所（都道府県）
	 */
	public void setSenderPrefectures(String senderPrefectures) {
	    this.senderPrefectures = senderPrefectures;
	}

	/**
	 * 送り主住所（市区町村）を取得します。
	 * @return 送り主住所（市区町村）
	 */
	public String getSenderMunicipality() {
	    return senderMunicipality;
	}

	/**
	 * 送り主住所（市区町村）を設定します。
	 * @param senderMunicipality 送り主住所（市区町村）
	 */
	public void setSenderMunicipality(String senderMunicipality) {
	    this.senderMunicipality = senderMunicipality;
	}

	/**
	 * 送り主住所（市区町村以降）を取得します。
	 * @return 送り主住所（市区町村以降）
	 */
	public String getSenderAddress() {
	    return senderAddress;
	}

	/**
	 * 送り主住所（市区町村以降）を設定します。
	 * @param senderAddress 送り主住所（市区町村以降）
	 */
	public void setSenderAddress(String senderAddress) {
	    this.senderAddress = senderAddress;
	}

	/**
	 * 送り主住所（建物名等）を取得します。
	 * @return 送り主住所（建物名等）
	 */
	public String getSenderBuildingNm() {
	    return senderBuildingNm;
	}

	/**
	 * 送り主住所（建物名等）を設定します。
	 * @param senderBuildingNm 送り主住所（建物名等）
	 */
	public void setSenderBuildingNm(String senderBuildingNm) {
	    this.senderBuildingNm = senderBuildingNm;
	}

	/**
	 * 送り主会社名を取得します。
	 * @return 送り主会社名
	 */
	public String getSenderCompanyNm() {
	    return senderCompanyNm;
	}

	/**
	 * 送り主会社名を設定します。
	 * @param senderCompanyNm 送り主会社名
	 */
	public void setSenderCompanyNm(String senderCompanyNm) {
	    this.senderCompanyNm = senderCompanyNm;
	}

	/**
	 * 送り主部署名を取得します。
	 * @return 送り主部署名
	 */
	public String getSenderQuarter() {
	    return senderQuarter;
	}

	/**
	 * 送り主部署名を設定します。
	 * @param senderQuarter 送り主部署名
	 */
	public void setSenderQuarter(String senderQuarter) {
	    this.senderQuarter = senderQuarter;
	}

	/**
	 * 送り主電話番号を取得します。
	 * @return 送り主電話番号
	 */
	public String getSenderTel() {
	    return senderTel;
	}

	/**
	 * 送り主電話番号を設定します。
	 * @param senderTel 送り主電話番号
	 */
	public void setSenderTel(String senderTel) {
	    this.senderTel = senderTel;
	}

	/**
	 * 備考（お届け先）を取得します。
	 * @return 備考（お届け先）
	 */
	public String getSenderRemarks() {
	    return senderRemarks;
	}

	/**
	 * 備考（お届け先）を設定します。
	 * @param senderRemarks 備考（お届け先）
	 */
	public void setSenderRemarks(String senderRemarks) {
	    this.senderRemarks = senderRemarks;
	}

	/**
	 * 一言メモ（お届け先）を取得します。
	 * @return 一言メモ（お届け先）
	 */
	public String getSenderMemo() {
	    return senderMemo;
	}

	/**
	 * 一言メモ（お届け先）を設定します。
	 * @param senderMemo 一言メモ（お届け先）
	 */
	public void setSenderMemo(String senderMemo) {
	    this.senderMemo = senderMemo;
	}

	/**
	 * ギフトメッセージを取得します。
	 * @return ギフトメッセージ
	 */
	public String getGiftMessage() {
	    return giftMessage;
	}

	/**
	 * ギフトメッセージを設定します。
	 * @param giftMessage ギフトメッセージ
	 */
	public void setGiftMessage(String giftMessage) {
	    this.giftMessage = giftMessage;
	}

	/**
	 * 伝票区分を取得します。
	 * @return 伝票区分
	 */
	public int getSlipDivision() {
	    return slipDivision;
	}

	/**
	 * 伝票区分を設定します。
	 * @param slipDivision 伝票区分
	 */
	public void setSlipDivision(int slipDivision) {
	    this.slipDivision = slipDivision;
	}

	/**
	 * 送り状種別を取得します。
	 * @return 送り状種別
	 */
	public String getInvoiceClassification() {
	    return invoiceClassification;
	}

	/**
	 * 送り状種別を設定します。
	 * @param invoiceClassification 送り状種別
	 */
	public void setInvoiceClassification(String invoiceClassification) {
	    this.invoiceClassification = invoiceClassification;
	}

	/**
	 * 伝票番号を取得します。
	 * @return 伝票番号
	 */
	public String getSlipNo() {
	    return slipNo;
	}

	/**
	 * 伝票番号を設定します。
	 * @param slipNo 伝票番号
	 */
	public void setSlipNo(String slipNo) {
	    this.slipNo = slipNo;
	}

	/**
	 * 代引請求金額を取得します。
	 * @return 代引請求金額
	 */
	public int getCashOnDeliveryCommission() {
	    return cashOnDeliveryCommission;
	}

	/**
	 * 代引請求金額を設定します。
	 * @param cashOnDeliveryCommission 代引請求金額
	 */
	public void setCashOnDeliveryCommission(int cashOnDeliveryCommission) {
	    this.cashOnDeliveryCommission = cashOnDeliveryCommission;
	}

	/**
	 * 温度区分を取得します。
	 * @return 温度区分
	 */
	public String getTemperatureDivision() {
	    return temperatureDivision;
	}

	/**
	 * 温度区分を設定します。
	 * @param temperatureDivision 温度区分
	 */
	public void setTemperatureDivision(String temperatureDivision) {
	    this.temperatureDivision = temperatureDivision;
	}

	/**
	 * お届け指定日を取得します。
	 * @return お届け指定日
	 */
	public String getDestinationAppointDate() {
	    return destinationAppointDate;
	}

	/**
	 * お届け指定日を設定します。
	 * @param destinationAppointDate お届け指定日
	 */
	public void setDestinationAppointDate(String destinationAppointDate) {
	    this.destinationAppointDate = destinationAppointDate;
	}

	/**
	 * お届け時間帯を取得します。
	 * @return お届け時間帯
	 */
	public String getDestinationAppointTime() {
	    return destinationAppointTime;
	}

	/**
	 * お届け時間帯を設定します。
	 * @param destinationAppointTime お届け時間帯
	 */
	public void setDestinationAppointTime(String destinationAppointTime) {
	    this.destinationAppointTime = destinationAppointTime;
	}

	/**
	 * 出荷予定日を取得します。
	 * @return 出荷予定日
	 */
	public String getShipmentPlanDate() {
	    return shipmentPlanDate;
	}

	/**
	 * 出荷予定日を設定します。
	 * @param shipmentPlanDate 出荷予定日
	 */
	public void setShipmentPlanDate(String shipmentPlanDate) {
	    this.shipmentPlanDate = shipmentPlanDate;
	}

	/**
	 * 運送会社システムを取得します。
	 * @return 運送会社システム
	 */
	public String getTransportCorporationSystem() {
	    return transportCorporationSystem;
	}

	/**
	 * 運送会社システムを設定します。
	 * @param transportCorporationSystem 運送会社システム
	 */
	public void setTransportCorporationSystem(String transportCorporationSystem) {
	    this.transportCorporationSystem = transportCorporationSystem;
	}

	/**
	 * 一言メモ（伝票）を取得します。
	 * @return 一言メモ（伝票）
	 */
	public String getSlipMemo() {
	    return slipMemo;
	}

	/**
	 * 一言メモ（伝票）を設定します。
	 * @param slipMemo 一言メモ（伝票）
	 */
	public void setSlipMemo(String slipMemo) {
	    this.slipMemo = slipMemo;
	}

	/**
	 * 最終ステータスを取得します。
	 * @return 最終ステータス
	 */
	public String getLastStatus() {
	    return lastStatus;
	}

	/**
	 * 最終ステータスを設定します。
	 * @param lastStatus 最終ステータス
	 */
	public void setLastStatus(String lastStatus) {
	    this.lastStatus = lastStatus;
	}

	/**
	 * 保留ステータスを取得します。
	 * @return 保留ステータス
	 */
	public String getReservationStatus() {
	    return reservationStatus;
	}

	/**
	 * 保留ステータスを設定します。
	 * @param reservationStatus 保留ステータス
	 */
	public void setReservationStatus(String reservationStatus) {
	    this.reservationStatus = reservationStatus;
	}

	/**
	 * 同梱元を取得します。
	 * @return 同梱元
	 */
	public String getCombineSource() {
	    return combineSource;
	}

	/**
	 * 同梱元を設定します。
	 * @param combineSource 同梱元
	 */
	public void setCombineSource(String combineSource) {
	    this.combineSource = combineSource;
	}

	/**
	 * 同梱先を取得します。
	 * @return 同梱先
	 */
	public String getCombinePoint() {
	    return combinePoint;
	}

	/**
	 * 同梱先を設定します。
	 * @param combinePoint 同梱先
	 */
	public void setCombinePoint(String combinePoint) {
	    this.combinePoint = combinePoint;
	}

	/**
	 * 商品区分を取得します。
	 * @return 商品区分
	 */
	public int getItemDivision() {
	    return itemDivision;
	}

	/**
	 * 商品区分を設定します。
	 * @param itemDivision 商品区分
	 */
	public void setItemDivision(int itemDivision) {
	    this.itemDivision = itemDivision;
	}

	/**
	 * 商品種別を取得します。
	 * @return 商品種別
	 */
	public String getItemClassification() {
	    return itemClassification;
	}

	/**
	 * 商品種別を設定します。
	 * @param itemClassification 商品種別
	 */
	public void setItemClassification(String itemClassification) {
	    this.itemClassification = itemClassification;
	}

	/**
	 * 商品コード（店舗）を取得します。
	 * @return 商品コード（店舗）
	 */
	public String getShopItemCd() {
	    return shopItemCd;
	}

	/**
	 * 商品コード（店舗）を設定します。
	 * @param shopItemCd 商品コード（店舗）
	 */
	public void setShopItemCd(String shopItemCd) {
	    this.shopItemCd = shopItemCd;
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
	 * 個数を取得します。
	 * @return 個数
	 */
	public int getItemNum() {
	    return itemNum;
	}

	/**
	 * 個数を設定します。
	 * @param itemNum 個数
	 */
	public void setItemNum(int itemNum) {
	    this.itemNum = itemNum;
	}

	/**
	 * 単価を取得します。
	 * @return 単価
	 */
	public int getPieceRate() {
	    return pieceRate;
	}

	/**
	 * 単価を設定します。
	 * @param pieceRate 単価
	 */
	public void setPieceRate(int pieceRate) {
	    this.pieceRate = pieceRate;
	}

	/**
	 * オプション１（助ネコ）を取得します。
	 * @return オプション１（助ネコ）
	 */
	public String getOptionSukenekoOne() {
	    return optionSukenekoOne;
	}

	/**
	 * オプション１（助ネコ）を設定します。
	 * @param optionSukenekoOne オプション１（助ネコ）
	 */
	public void setOptionSukenekoOne(String optionSukenekoOne) {
	    this.optionSukenekoOne = optionSukenekoOne;
	}

	/**
	 * オプション２（助ネコ）を取得します。
	 * @return オプション２（助ネコ）
	 */
	public String getOptionSukenekoTwo() {
	    return optionSukenekoTwo;
	}

	/**
	 * オプション２（助ネコ）を設定します。
	 * @param optionSukenekoTwo オプション２（助ネコ）
	 */
	public void setOptionSukenekoTwo(String optionSukenekoTwo) {
	    this.optionSukenekoTwo = optionSukenekoTwo;
	}

	/**
	 * 商品コード（助ネコ）を取得します。
	 * @return 商品コード（助ネコ）
	 */
	public String getSukenekoItemCd() {
	    return sukenekoItemCd;
	}

	/**
	 * 商品コード（助ネコ）を設定します。
	 * @param sukenekoItemCd 商品コード（助ネコ）
	 */
	public void setSukenekoItemCd(String sukenekoItemCd) {
	    this.sukenekoItemCd = sukenekoItemCd;
	}

	/**
	 * 未入金額を取得します。
	 * @return 未入金額
	 */
	public int getUnpaidPrice() {
	    return unpaidPrice;
	}

	/**
	 * 未入金額を設定します。
	 * @param unpaidPrice 未入金額
	 */
	public void setUnpaidPrice(int unpaidPrice) {
	    this.unpaidPrice = unpaidPrice;
	}

	/**
	 * 送り状記事欄を取得します。
	 * @return 送り状記事欄
	 */
	public String getInvoiceArticle() {
	    return invoiceArticle;
	}

	/**
	 * 送り状記事欄を設定します。
	 * @param invoiceArticle 送り状記事欄
	 */
	public void setInvoiceArticle(String invoiceArticle) {
	    this.invoiceArticle = invoiceArticle;
	}

	/**
	 * 伝票管理番号を取得します。
	 * @return 伝票管理番号
	 */
	public String getSlipManagementNo() {
	    return slipManagementNo;
	}

	/**
	 * 伝票管理番号を設定します。
	 * @param slipManagementNo 伝票管理番号
	 */
	public void setSlipManagementNo(String slipManagementNo) {
	    this.slipManagementNo = slipManagementNo;
	}

	/**
	 * 処理ルートを取得します。
	 * @return 処理ルート
	 */
	public String getDisposalRoute() {
	    return disposalRoute;
	}

	/**
	 * 処理ルートを設定します。
	 * @param disposalRoute 処理ルート
	 */
	public void setDisposalRoute(String disposalRoute) {
	    this.disposalRoute = disposalRoute;
	}

	/**
	 * 処理済日を取得します。
	 * @return 処理済日
	 */
	public String getDisposalDate() {
	    return disposalDate;
	}

	/**
	 * 処理済日を設定します。
	 * @param disposalDate 処理済日
	 */
	public void setDisposalDate(String disposalDate) {
	    this.disposalDate = disposalDate;
	}

	/**
	 * 自社商品コードを取得します。
	 * @return 自社商品コード
	 */
	public String getOwnCompanyCd() {
	    return ownCompanyCd;
	}

	/**
	 * 自社商品コードを設定します。
	 * @param ownCompanyCd 自社商品コード
	 */
	public void setOwnCompanyCd(String ownCompanyCd) {
	    this.ownCompanyCd = ownCompanyCd;
	}

	/**
	 * 購入回数を取得します。
	 * @return 購入回数
	 */
	public int getBuyCount() {
	    return buyCount;
	}

	/**
	 * 購入回数を設定します。
	 * @param buyCount 購入回数
	 */
	public void setBuyCount(int buyCount) {
	    this.buyCount = buyCount;
	}

	/**
	 * 削除フラグを取得します。
	 * @return 削除フラグ
	 */
	public String getDeleteFlag() {
	    return deleteFlag;
	}

	/**
	 * 削除フラグを設定します。
	 * @param deleteFlag 削除フラグ
	 */
	public void setDeleteFlag(String deleteFlag) {
	    this.deleteFlag = deleteFlag;
	}

	/**
	 * 登録日を取得します。
	 * @return 登録日
	 */
	public Date getCreateDate() {
	    return createDate;
	}

	/**
	 * 登録日を設定します。
	 * @param createDate 登録日
	 */
	public void setCreateDate(Date createDate) {
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
	public Date getUpdateDate() {
	    return updateDate;
	}

	/**
	 * 更新日を設定します。
	 * @param updateDate 更新日
	 */
	public void setUpdateDate(Date updateDate) {
	    this.updateDate = updateDate;
	}

	/**
	 * 更新者IDを取得します。
	 * @return 更新者ID
	 */
	public int getUpdateUserId() {
	    return updateUserId;
	}

	/**
	 * 更新者IDを設定します。
	 * @param updateUserId 更新者ID
	 */
	public void setUpdateUserId(int updateUserId) {
	    this.updateUserId = updateUserId;
	}

	/**
	 * 取り込み日を返却します
	 * @return
	 */
	public String getImportDate() {
		return importDate;
	}

	/**
	 * 取り込み日を設定します
	 * @param importDate 取り込み日
	 */
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

}
