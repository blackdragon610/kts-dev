package jp.co.kts.app.common.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [概要] 海外注文管理情報を格納します。
 * @author Boncre
 *
 */
public class ForeignOrderDTO {

	/** システム海外伝票ID */
	private long sysForeignSlipId;

	/** システム仕入先ID */
	private long sysSupplierId;

	/** 支払条件1 */
	private int paymentTerms1;

	/** 支払条件2 */
	private int paymentTerms2;

	/** リードタイム */
	private String leadTime;

	/** PONo */
	private String poNo;

	/** インボイスNo */
	private String invoiceNo;

	/** 注文ステータス */
	private String orderStatus;

	/** 担当者名 */
	private String personInCharge;

	/** 訂正フラグ */
	private String correctionFlag;

	/** 作成日 */
	private String registDate;

	/** 注文日 */
	private String orderDate;

	/** 納期日付1 */
	private String deliveryDate1;

	/** 納期日付2 */
	private String deliveryDate2;

	/** 支払日付1 */
	private String paymentDate1;

	/** 支払日付2 */
	private String paymentDate2;

	/** 小計 */
	private BigDecimal subTotal;

	/** その他費用 */
	private BigDecimal otherExpenses;

	/** 値引き */
	private BigDecimal discount;

	/** 合計 */
	private BigDecimal total;

	/** 支払1 */
	private BigDecimal payment1;

	/** 支払2 */
	private BigDecimal payment2;

	/** メモ */
	private String memo;

	/** 振込み理由 */
	private String transferReason;

	/** 作成日 */
	private Date createDate;

	/** 作成者ID */
	private int createUserId;

	/** 更新日 */
	private Date updateDate;

	/** 更新者ID */
	private int updateUserId;

	/** 納期1超過フラグ */
	private String deliveryDate1OverFlag;

	/** 納期2超過フラグ */
	private String deliveryDate2OverFlag;


	/**
	 * <p>
	 * システム海外伝票IDを返却します
	 * </p>
	 * @return sysForeignSlipId
	 */
	public long getSysForeignSlipId() {
		return sysForeignSlipId;
	}

	/**
	 * <p>
	 * システム海外伝票IDを設定します
	 * </p>
	 * @param sysForeignSlipId
	 */
	public void setSysForeignSlipId(long sysForeignSlipId) {
		this.sysForeignSlipId = sysForeignSlipId;
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
	 * 支払条件1を返却します
	 * </p>
	 * @return paymentTerms1
	 */
	public int getPaymentTerms1() {
		return paymentTerms1;
	}

	/**
	 * <p>
	 * 支払条件1を設定します
	 * </p>
	 * @param paymentTerms1
	 */
	public void setPaymentTerms1(int paymentTerms1) {
		this.paymentTerms1 = paymentTerms1;
	}

	/**
	 * <p>
	 * 支払条件2を返却します
	 * </p>
	 * @return paymentTerms2
	 */
	public int getPaymentTerms2() {
		return paymentTerms2;
	}

	/**
	 * <p>
	 * 支払条件2を設定します
	 * </p>
	 * @param paymentTerms2
	 */
	public void setPaymentTerms2(int paymentTerms2) {
		this.paymentTerms2 = paymentTerms2;
	}

	/**
	 * <p>
	 * リードタイムを返却します
	 * </p>
	 * @return leadTime
	 */
	public String getLeadTime() {
		return leadTime;
	}

	/**
	 * <p>
	 * リードタイムを設定します
	 * </p>
	 * @param leadTime
	 */
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	/**
	 * <p>
	 * PoNoを返却します。
	 * </p>
	 * @return poNo
	 */
	public String getPoNo() {
		return poNo;
	}

	/**
	 * <p>
	 * PoNoを設定します。
	 * </p>
	 * @param poNo
	 */
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	/**
	 * <p>
	 * インボイスNoを返却します。
	 * </p>
	 * @return invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * <p>
	 * インボイスNoを設定します。
	 * </p>
	 * @param invoiceNo
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * <p>
	 * 注文ステータスを返却します。
	 * </p>
	 * @return orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * <p>
	 * 注文ステータスを設定します。
	 * </p>
	 * @param orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * <p>
	 * 担当者名を返却します
	 * </p>
	 * @return personInCharge
	 */
	public String getPersonInCharge() {
		return personInCharge;
	}

	/**
	 * <p>
	 * 担当者名を設定します
	 * </p>
	 * @param personInCharge
	 */
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	/**
	 * <p>
	 * 訂正フラグを返却します。
	 * </p>
	 * @return correctionFlag
	 */
	public String getCorrectionFlag() {
		return correctionFlag;
	}

	/**
	 * <p>
	 * 訂正フラグを設定します。
	 * </p>
	 * @param correctionFlag
	 */
	public void setCorrectionFlag(String correctionFlag) {
		this.correctionFlag = correctionFlag;
	}

	/**
	 * <p>
	 * 作成日を返却します。
	 * </p>
	 * @return registDate
	 */
	public String getRegistDate() {
		return registDate;
	}

	/**
	 * <p>
	 * 作成日を設定します。
	 * </p>
	 * @param registDate
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	/**
	 * <p>
	 * 注文日を返却します。
	 * </p>
	 * @return orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * <p>
	 * 注文日を設定します。
	 * </p>
	 * @param orderDate
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * <p>
	 * 納期1を返却します。
	 * </p>
	 * @return deliveryDate1
	 */
	public String getDeliveryDate1() {
		return deliveryDate1;
	}

	/**
	 * <p>
	 * 納期1を設定します。
	 * </p>
	 * @param deliveryDate1
	 */
	public void setDeliveryDate1(String deliveryDate1) {
		this.deliveryDate1 = deliveryDate1;
	}

	/**
	 * <p>
	 * 納期2を返却します。
	 * </p>
	 * @return deliveryDate2
	 */
	public String getDeliveryDate2() {
		return deliveryDate2;
	}

	/**
	 * <p>
	 * 納期2を設定します。
	 * </p>
	 * @param deliveryDate2
	 */
	public void setDeliveryDate2(String deliveryDate2) {
		this.deliveryDate2 = deliveryDate2;
	}

	/**
	 * <p>
	 * 支払1を返却します。
	 * </p>
	 * @return payment1
	 */
	public String getPaymentDate1() {
		return paymentDate1;
	}

	/**
	 * <p>
	 * 支払1を設定します。
	 * </p>
	 * @param payment1
	 */
	public void setPaymentDate1(String paymentDate1) {
		this.paymentDate1 = paymentDate1;
	}

	/**
	 * <p>
	 * 支払2を返却します。
	 * </p>
	 * @return payment2
	 */
	public String getPaymentDate2() {
		return paymentDate2;
	}

	/**
	 * <p>
	 * 支払2を設定します。
	 * </p>
	 * @param payment2
	 */
	public void setPaymentDate2(String paymentDate2) {
		this.paymentDate2 = paymentDate2;
	}



	/**
	 * <p>
	 * 小計を返却します。
	 * </p>
	 * @return subTotal
	 */
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	/**
	 * <p>
	 * 小計を設定します。
	 * </p>
	 * @param subTotal
	 */
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	/**
	 * <p>
	 * その他費用を返却します。
	 * </p>
	 * @return otherExpenses
	 */
	public BigDecimal getOtherExpenses() {
		return otherExpenses;
	}

	/**
	 * <p>
	 * その他費用を設定します。
	 * </p>
	 * @param otherExpenses
	 */
	public void setOtherExpenses(BigDecimal otherExpenses) {
		this.otherExpenses = otherExpenses;
	}

	/**
	 * <p>
	 * 合計を返却します。
	 * </p>
	 * @return total
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * <p>
	 * 合計を設定します。
	 * </p>
	 * @param total
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * <p>
	 * 支払1を返却します。
	 * </p>
	 * @return payment1
	 */
	public BigDecimal getPayment1() {
		return payment1;
	}

	/**
	 * <p>
	 * 支払1を設定します。
	 * </p>
	 * @param payment1
	 */
	public void setPayment1(BigDecimal payment1) {
		this.payment1 = payment1;
	}

	/**
	 * <p>
	 * 支払2を返却します。
	 * </p>
	 * @return payment2
	 */
	public BigDecimal getPayment2() {
		return payment2;
	}

	/**
	 * <p>
	 * 支払2を設定します。
	 * </p>
	 * @param payment2
	 */
	public void setPayment2(BigDecimal payment2) {
		this.payment2 = payment2;
	}

	/**
	 * <p>
	 * メモを返却します。
	 * </p>
	 * @return memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * <p>
	 * メモを設定します。
	 * </p>
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * <p>
	 * 振込み理由を返却します。
	 * </p>
	 * @return transferReason
	 */
	public String getTransferReason() {
		return transferReason;
	}

	/**
	 * <p>
	 * 振込み理由を設定します。
	 * </p>
	 * @param transferReason
	 */
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	/**
	 * <p>
	 * 作成日を返却します
	 * </p>
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * <p>
	 * 作成日を設定します
	 * </p>
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * <p>
	 * 作成者IDを返却します
	 * </p>
	 * @return createUserId
	 */
	public int getCreateUserId() {
		return createUserId;
	}

	/**
	 * <p>
	 * 作成者IDを設定します
	 * </p>
	 * @param createUserId
	 */
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * <p>
	 * 更新日を返却します
	 * </p>
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * <p>
	 * 更新日を設定します
	 * </p>
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * <p>
	 * 更新者IDを返却します
	 * </p>
	 * @return updateUserId
	 */
	public int getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * <p>
	 * 更新者IDを設定します
	 * </p>
	 * @param updateUserId
	 */
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * <p>
	 * 納期1超過フラグを返却します
	 * </p>
	 * @return deliveryDate1OverFlag
	 */
	public String getDeliveryDate1OverFlag() {
		return deliveryDate1OverFlag;
	}

	/**
	 * <p>
	 * 納期1超過フラグを設定します
	 * </p>
	 * @param deliveryDate1OverFlag セットする deliveryDate1OverFlag
	 */
	public void setDeliveryDate1OverFlag(String deliveryDate1OverFlag) {
		this.deliveryDate1OverFlag = deliveryDate1OverFlag;
	}

	/**
	 * <p>
	 * 納期2超過フラグを返却します
	 * </p>
	 * @return deliveryDate2OverFlag
	 */
	public String getDeliveryDate2OverFlag() {
		return deliveryDate2OverFlag;
	}

	/**
	 * <p>
	 * 納期2超過フラグを設定します
	 * </p>
	 * @param deliveryDate2OverFlag セットする deliveryDate2OverFlag
	 */
	public void setDeliveryDate2OverFlag(String deliveryDate2OverFlag) {
		this.deliveryDate2OverFlag = deliveryDate2OverFlag;
	}

	/**
	 * <p>
	 * 値引きを返却します
	 * </p>
	 *
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * <p>
	 * 値引きを設定します
	 * </p>
	 *
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

}