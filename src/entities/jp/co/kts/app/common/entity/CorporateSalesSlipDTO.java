/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 業販伝票情報を格納します。
 *
 * @author admin
 */
public class CorporateSalesSlipDTO  {

	/** システム売上伝票ID */
	private long sysCorporateSalesSlipId;

	/** システム法人ID */
	private long sysCorporationId;

	/** 受注番号 */
	private String orderNo;

	/** 伝票ステータス */
	private String slipStatus;

	/** 担当者 */
	private String personInCharge;

	/** 無効フラグ */
	private String invalidFlag;

	/** 見積日 */
	private String estimateDate;

	/** 注文日 */
	private String orderDate;

	/** 売上日 */
	private String salesDate;

	/** 請求日 */
	private String billingDate;

	/** 得意先ID */
	private long sysClientId;

	/** 支払方法 */
	private String paymentMethod;

	/** 納入期限 */
	private String deliveryDeadline;

	/** 口座ID */
	private long sysAccountId;

	/** 売上合計 */
	private int sumSalesPrice;

	/** 入金額 */
	private int receivePrice;

	/** 入金日 */
	private String receiveDate;

	/** 備考/一言メモ（注文） */
	private String orderRemarks;

	/** 注文確定所備考 */
	private String orderFixRemarks;

	/** 見積書備考 */
	private String estimateRemarks;

	/** 請求書備考 */
	private String billingRemarks;

	/** 入金日 */
	private String depositDate;

	/** 納入先名 */
	private String destinationNm;

	/** 納入先名（カナ） */
	private String destinationNmKana;

	/** 納入先郵便番号 */
	private String destinationZip;

	/** 納入先住所（都道府県） */
	private String destinationPrefectures;

	/** 納入先住所（市区町村） */
	private String destinationMunicipality;

	/** 納入先住所（市区町村以降） */
	private String destinationAddress;

	/** 納入先住所（建物名等） */
	private String destinationBuildingNm;

	/** 納入先会社名 */
	private String destinationCompanyNm;

	/** 納入先部署名 */
	private String destinationQuarter;

	/** 納入先役職名 */
	private String destinationPosition;

	/** 納入先御担当者名 */
	private String destinationContactPersonNm;

	/** 納入先電話番号 */
	private String destinationTel;

	/** 納入先FAX番号 */
	private String destinationFax;

	/** 備考/一言メモ（納入先） */
	private String senderRemarks;

	/** 伝票番号 */
	private String slipNo;

	/** 運送会社システム */
	private String transportCorporationSystem;

	/** 原票区分 */
	private String genpyoKbn;

	/** 輸送指示1 */
	private String yusoShiji;

	/** 輸送指示2 */
    private String yusoShiji2;

	/** 送り状種別 */
	private String invoiceClassification;

	/** お届け指定日 */
	private String destinationAppointDate;

	/** お届け時間帯 */
	private String destinationAppointTime;

	/** 出荷日 */
	private String shipmentDate;

	/** 出荷日 */
	private String shipmentPlanDate;

	/** 合計請求金額 */
	private int sumClaimPrice;

	/** 商品単価小計 */
	private int sumPieceRate;

	/** 税率 */
	private int taxRate;

	/** 税 */
	private int tax;

	/** 通貨 */
	private String currency;

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

	/**
	 * <p>
	 * システム売上伝票ID を返却します。
	 * </p>
	 * @return sysCorporateSalesSlipId
	 */
	public long getSysCorporateSalesSlipId() {
		return this.sysCorporateSalesSlipId;
	}

	/**
	 * <p>
	 * システム売上伝票ID を設定します。
	 * </p>
	 * @param sysCorporateSalesSlipId
	 */
	public void setSysCorporateSalesSlipId(long sysCorporateSalesSlipId) {
		this.sysCorporateSalesSlipId = sysCorporateSalesSlipId;
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
	 * 伝票ステータス を返却します。
	 * </p>
	 * @return slipStatus
	 */
	public String getSlipStatus() {
		return this.slipStatus;
	}

	/**
	 * <p>
	 * 伝票ステータス を設定します。
	 * </p>
	 * @param slipStatus
	 */
	public void setSlipStatus(String slipStatus) {
		this.slipStatus = slipStatus;
	}

	/**
	 * <p>
	 * 担当者 を返却します。
	 * </p>
	 * @return personInCharge
	 */
	public String getPersonInCharge() {
		return this.personInCharge;
	}

	/**
	 * <p>
	 * 担当者 を設定します。
	 * </p>
	 * @param personInCharge
	 */
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	/**
	 * @return invalidFlag
	 */
	public String getInvalidFlag() {
		return invalidFlag;
	}

	/**
	 * @param invalidFlag セットする invalidFlag
	 */
	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}

	/**
	 * <p>
	 * 見積日 を返却します。
	 * </p>
	 * @return estimateDate
	 */
	public String getEstimateDate() {
		return this.estimateDate;
	}

	/**
	 * <p>
	 * 見積日 を設定します。
	 * </p>
	 * @param estimateDate
	 */
	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
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
	 * 売上日 を返却します。
	 * </p>
	 * @return salesDate
	 */
	public String getSalesDate() {
		return this.salesDate;
	}

	/**
	 * <p>
	 * 売上日 を設定します。
	 * </p>
	 * @param salesDate
	 */
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	/**
	 * <p>
	 * 請求日 を返却します。
	 * </p>
	 * @return billingDate
	 */
	public String getBillingDate() {
		return this.billingDate;
	}

	/**
	 * <p>
	 * 請求日 を設定します。
	 * </p>
	 * @param billingDate
	 */
	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	/**
	 * <p>
	 * 得意先ID を返却します。
	 * </p>
	 * @return sysClientId
	 */
	public long getSysClientId() {
		return this.sysClientId;
	}

	/**
	 * <p>
	 * 得意先ID を設定します。
	 * </p>
	 * @param sysClientId
	 */
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}

	/**
	 * @return deliveryDeadline
	 */
	public String getDeliveryDeadline() {
		return deliveryDeadline;
	}

	/**
	 * @param deliveryDeadline セットする deliveryDeadline
	 */
	public void setDeliveryDeadline(String deliveryDeadline) {
		this.deliveryDeadline = deliveryDeadline;
	}

	/**
	 * @return sysAccountId
	 */
	public long getSysAccountId() {
		return sysAccountId;
	}

	/**
	 * @param sysAccountId セットする sysAccountId
	 */
	public void setSysAccountId(long sysAccountId) {
		this.sysAccountId = sysAccountId;
	}

	/**
	 * <p>
	 * 入金額 を返却します。
	 * </p>
	 * @return receivePrice
	 */
	public int getReceivePrice() {
		return this.receivePrice;
	}

	/**
	 * <p>
	 * 入金額 を設定します。
	 * </p>
	 * @param receivePrice
	 */
	public void setReceivePrice(int receivePrice) {
		this.receivePrice = receivePrice;
	}

	/**
	 * <p>
	 * 入金日 を返却します。
	 * </p>
	 * @return receiveDate
	 */
	public String getReceiveDate() {
		return this.receiveDate;
	}

	/**
	 * <p>
	 * 入金日 を設定します。
	 * </p>
	 * @param receiveDate
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * <p>
	 * 備考/一言メモ（注文） を返却します。
	 * </p>
	 * @return orderRemarks
	 */
	public String getOrderRemarks() {
		return this.orderRemarks;
	}

	/**
	 * <p>
	 * 備考/一言メモ（注文） を設定します。
	 * </p>
	 * @param orderRemarks
	 */
	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	/**
	 * <p>
	 * 注文確定所備考 を返却します。
	 * </p>
	 * @return orderFixRemarks
	 */
	public String getOrderFixRemarks() {
		return this.orderFixRemarks;
	}

	/**
	 * <p>
	 * 注文確定所備考 を設定します。
	 * </p>
	 * @param orderFixRemarks
	 */
	public void setOrderFixRemarks(String orderFixRemarks) {
		this.orderFixRemarks = orderFixRemarks;
	}

	/**
	 * <p>
	 * 見積書備考 を返却します。
	 * </p>
	 * @return estimateRemarks
	 */
	public String getEstimateRemarks() {
		return this.estimateRemarks;
	}

	/**
	 * <p>
	 * 見積書備考 を設定します。
	 * </p>
	 * @param estimateRemarks
	 */
	public void setEstimateRemarks(String estimateRemarks) {
		this.estimateRemarks = estimateRemarks;
	}

	/**
	 * <p>
	 * 請求書備考 を返却します。
	 * </p>
	 * @return billingRemarks
	 */
	public String getBillingRemarks() {
		return this.billingRemarks;
	}

	/**
	 * <p>
	 * 請求書備考 を設定します。
	 * </p>
	 * @param billingRemarks
	 */
	public void setBillingRemarks(String billingRemarks) {
		this.billingRemarks = billingRemarks;
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
	 * 納入先郵便番号 を返却します。
	 * </p>
	 * @return destinationZip
	 */
	public String getDestinationZip() {
		return this.destinationZip;
	}

	/**
	 * <p>
	 * 納入先郵便番号 を設定します。
	 * </p>
	 * @param destinationZip
	 */
	public void setDestinationZip(String destinationZip) {
		this.destinationZip = destinationZip;
	}

	/**
	 * <p>
	 * 納入先住所（都道府県） を返却します。
	 * </p>
	 * @return destinationPrefectures
	 */
	public String getDestinationPrefectures() {
		return this.destinationPrefectures;
	}

	/**
	 * <p>
	 * 納入先住所（都道府県） を設定します。
	 * </p>
	 * @param destinationPrefectures
	 */
	public void setDestinationPrefectures(String destinationPrefectures) {
		this.destinationPrefectures = destinationPrefectures;
	}

	/**
	 * <p>
	 * 納入先住所（市区町村） を返却します。
	 * </p>
	 * @return destinationMunicipality
	 */
	public String getDestinationMunicipality() {
		return this.destinationMunicipality;
	}

	/**
	 * <p>
	 * 納入先住所（市区町村） を設定します。
	 * </p>
	 * @param destinationMunicipality
	 */
	public void setDestinationMunicipality(String destinationMunicipality) {
		this.destinationMunicipality = destinationMunicipality;
	}

	/**
	 * <p>
	 * 納入先住所（市区町村以降） を返却します。
	 * </p>
	 * @return destinationAddress
	 */
	public String getDestinationAddress() {
		return this.destinationAddress;
	}

	/**
	 * <p>
	 * 納入先住所（市区町村以降） を設定します。
	 * </p>
	 * @param destinationAddress
	 */
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	/**
	 * <p>
	 * 納入先住所（建物名等） を返却します。
	 * </p>
	 * @return destinationBuildingNm
	 */
	public String getDestinationBuildingNm() {
		return this.destinationBuildingNm;
	}

	/**
	 * <p>
	 * 納入先住所（建物名等） を設定します。
	 * </p>
	 * @param destinationBuildingNm
	 */
	public void setDestinationBuildingNm(String destinationBuildingNm) {
		this.destinationBuildingNm = destinationBuildingNm;
	}

	/**
	 * <p>
	 * 納入先会社名 を返却します。
	 * </p>
	 * @return destinationCompanyNm
	 */
	public String getDestinationCompanyNm() {
		return this.destinationCompanyNm;
	}

	/**
	 * <p>
	 * 納入先会社名 を設定します。
	 * </p>
	 * @param destinationCompanyNm
	 */
	public void setDestinationCompanyNm(String destinationCompanyNm) {
		this.destinationCompanyNm = destinationCompanyNm;
	}

	/**
	 * <p>
	 * 納入先部署名 を返却します。
	 * </p>
	 * @return destinationQuarter
	 */
	public String getDestinationQuarter() {
		return this.destinationQuarter;
	}

	/**
	 * <p>
	 * 納入先部署名 を設定します。
	 * </p>
	 * @param destinationQuarter
	 */
	public void setDestinationQuarter(String destinationQuarter) {
		this.destinationQuarter = destinationQuarter;
	}

	/**
	 * <p>
	 * 納入先電話番号 を返却します。
	 * </p>
	 * @return destinationTel
	 */
	public String getDestinationTel() {
		return this.destinationTel;
	}

	/**
	 * <p>
	 * 納入先電話番号 を設定します。
	 * </p>
	 * @param destinationTel
	 */
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	/**
	 * <p>
	 * 備考/一言メモ（納入先） を返却します。
	 * </p>
	 * @return senderRemarks
	 */
	public String getSenderRemarks() {
		return this.senderRemarks;
	}

	/**
	 * <p>
	 * 備考/一言メモ（納入先） を設定します。
	 * </p>
	 * @param senderRemarks
	 */
	public void setSenderRemarks(String senderRemarks) {
		this.senderRemarks = senderRemarks;
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
     * 原票区分を返却します。
     * </p>
     * @return genpyoKbn
     */
    public String getGenpyoKbn() {
        return genpyoKbn;
    }

    /**
     * <p>
     * 原票区分を設定します。
     * </p>
     * @param genpyoKbn 原票区分
     */
    public void setGenpyoKbn(String genpyoKbn) {
        this.genpyoKbn = genpyoKbn;
    }

    /**
     * <p>
     * 輸送指示1を返却します。
     * </p>
     * @return yusoShiji 輸送指示1
     */
    public String getYusoShiji() {
        return yusoShiji;
    }

    /**
     * <p>
     * 輸送指示1を設定します。
     * </p>
     * @param yusoShiji 輸送指示1
     */
    public void setYusoShiji(String yusoShiji) {
        this.yusoShiji = yusoShiji;
    }

    /**
     * <p>
     * 輸送指示2を返却します。
     * </p>
     * @return yusoShiji2 輸送指示2
     */
    public String getYusoShiji2() {
        return yusoShiji2;
    }

    /**
     * <p>
     * 輸送指示2を設定します。
     * </p>
     * @param yusoShiji2 輸送指示
     */
    public void setYusoShiji2(String yusoShiji2) {
        this.yusoShiji2 = yusoShiji2;
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
	 * 出荷日 を返却します。
	 * </p>
	 * @return shipmentDate
	 */
	public String getShipmentDate() {
		return this.shipmentDate;
	}

	/**
	 * <p>
	 * 出荷日 を設定します。
	 * </p>
	 * @param shipmentDate
	 */
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	/**
	 * <p>
	 * 出荷日 を返却します。
	 * </p>
	 * @return shipmentPlanDate
	 */
	public String getShipmentPlanDate() {
		return this.shipmentPlanDate;
	}

	/**
	 * <p>
	 * 出荷日 を設定します。
	 * </p>
	 * @param shipmentDate
	 */
	public void setShipmentPlanDate(String shipmentPlanDate) {
		this.shipmentPlanDate = shipmentPlanDate;
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
	 * 通貨 を返却します。
	 * </p>
	 * @return currency
	 */
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * <p>
	 * 通貨 を設定します。
	 * </p>
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
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
	 * @return sumSalesPrice
	 */
	public int getSumSalesPrice() {
		return sumSalesPrice;
	}

	/**
	 * @param sumSalesPrice セットする sumSalesPrice
	 */
	public void setSumSalesPrice(int sumSalesPrice) {
		this.sumSalesPrice = sumSalesPrice;
	}

	/**
	 * @return destinationNm
	 */
	public String getDestinationNm() {
		return destinationNm;
	}

	/**
	 * @param destinationNm セットする destinationNm
	 */
	public void setDestinationNm(String destinationNm) {
		this.destinationNm = destinationNm;
	}

	/**
	 * @return destinationNmKana
	 */
	public String getDestinationNmKana() {
		return destinationNmKana;
	}

	/**
	 * @param destinationNmKana セットする destinationNmKana
	 */
	public void setDestinationNmKana(String destinationNmKana) {
		this.destinationNmKana = destinationNmKana;
	}

	/**
	 * @return paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod セットする paymentMethod
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return destinationPosition
	 */
	public String getDestinationPosition() {
		return destinationPosition;
	}

	/**
	 * @param destinationPosition セットする destinationPosition
	 */
	public void setDestinationPosition(String destinationPosition) {
		this.destinationPosition = destinationPosition;
	}

	/**
	 * @return destinationContactPersonNm
	 */
	public String getDestinationContactPersonNm() {
		return destinationContactPersonNm;
	}

	/**
	 * @param destinationContactPersonNm セットする destinationContactPersonNm
	 */
	public void setDestinationContactPersonNm(String destinationContactPersonNm) {
		this.destinationContactPersonNm = destinationContactPersonNm;
	}

	/**
	 * @return destinationFax
	 */
	public String getDestinationFax() {
		return destinationFax;
	}

	/**
	 * @param destinationFax セットする destinationFax
	 */
	public void setDestinationFax(String destinationFax) {
		this.destinationFax = destinationFax;
	}

}

