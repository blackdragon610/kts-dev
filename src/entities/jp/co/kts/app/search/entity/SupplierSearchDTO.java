package jp.co.kts.app.search.entity;

public class SupplierSearchDTO {

	/** システム仕入先ID */
	private long sysSupplierId;

	private String supplierId;

	/** 支払条件1 */
	private int paymentTerms1;

	/** 支払条件2 */
	private int paymentTerms2;

	/** 仕入先リードタイム */
	private String leadTime;

	/**
	 * @return sysSupplierId
	 */
	public long getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * @return supplierId
	 */
	public String getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public SupplierSearchDTO() {
		setSysSupplierId(0);
		setSupplierId("");
		setPaymentTerms1(0);
		setPaymentTerms2(0);
		setLeadTime("");
	}

	/**
	 * @param sysSupplierId
	 */
	public void setSysSupplierId(long sysSupplierId) {
		this.sysSupplierId = sysSupplierId;
	}

	/**
	 * @return paymentTerms1
	 */
	public int getPaymentTerms1() {
		return paymentTerms1;
	}

	/**
	 * @param paymentTerms1
	 */
	public void setPaymentTerms1(int paymentTerms1) {
		this.paymentTerms1 = paymentTerms1;
	}

	/**
	 * @return paymentTerms2
	 */
	public int getPaymentTerms2() {
		return paymentTerms2;
	}

	/**
	 * @param paymentTerms2
	 */
	public void setPaymentTerms2(int paymentTerms2) {
		this.paymentTerms2 = paymentTerms2;
	}

	/**
	 * @return leadTime
	 */
	public String getLeadTime() {
		return leadTime;
	}

	/**
	 * @param leadTime
	 */
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

}
