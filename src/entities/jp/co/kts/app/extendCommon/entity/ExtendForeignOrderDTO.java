package jp.co.kts.app.extendCommon.entity;

import java.util.ArrayList;
import java.util.List;

import jp.co.kts.app.common.entity.ForeignOrderDTO;

public class ExtendForeignOrderDTO extends ForeignOrderDTO {

	/** システム海外商品ID */
	private long sysForeignSlipItemId;

	/** 会社・工場名 */
	private String companyFactoryNm;

	/** 品番 */
	private String itemCode;

	/** 工場品番 */
	private String factoryItemCode;

	/** 商品名 */
	private String itemNm;

	/** 注文数 */
	private int orderNum;

	/** 入荷数 */
	private int arrivalNum;

	/** 入荷予定日 */
	private String arrivalScheduleDate;

	/** 入荷日 */
	private String arrivalDate;

	/** 注文ステータス名 */
	private String orderStatusNm;

	/** 仕入先ID(表示用) */
//	private String supplierId;

	/** チェックボックスフラグ */
	private String checkBoxFlg;

	/** システム入荷予定ID */
	private long sysArrivalScheduleId;

	/** 海外商品リスト */
	private List<ExtendForeignOrderItemDTO> itemList = new ArrayList<>();

	/** 支払状態1 */
	private String paymentStatus1;

	/** 支払状態2 */
	private String paymentStatus2;


	/** PDF振込依頼書１か２ */
	private String transferPatern;

	private List<ExtendArrivalScheduleDTO> arrivalScheduleList = new ArrayList<>();

	/** 総在庫数 */
	private int totalStockNum = 0;

	/** 仮在庫数 */
	private int temporaryStockNum = 0;

	/**
	 * @return sysForeignSlipItemId
	 */
	public long getSysForeignSlipItemId() {
		return sysForeignSlipItemId;
	}

	/**
	 * @param sysForeignSlipItemId セットする sysForeignSlipItemId
	 */
	public void setSysForeignSlipItemId(long sysForeignSlipItemId) {
		this.sysForeignSlipItemId = sysForeignSlipItemId;
	}

	/**
	 * <p>
	 * 会社・工場名を返却します
	 * </p>
	 * @return companyFactoryNm
	 */
	public String getCompanyFactoryNm() {
		return companyFactoryNm;
	}

	/**
	 * <p>
	 * 会社・工場名を設定します
	 * </p>
	 * @param companyFactoryNm
	 */
	public void setCompanyFactoryNm(String companyFactoryNm) {
		this.companyFactoryNm = companyFactoryNm;
	}

	/**
	 * <p>
	 * 品番を返却します
	 * </p>
	 * @return itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * <p>
	 * 品番を設定します
	 * </p>
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
	 * 商品名を返却します
	 * </p>
	 * @return itemNm
	 */
	public String getItemNm() {
		return itemNm;
	}

	/**
	 * <p>
	 * 商品名を設定します
	 * </p>
	 * @param itemNm
	 */
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	/**
	 * <p>
	 * 注文数を返却します
	 * </p>
	 * @return orderNum
	 */
	public int getOrderNum() {
		return orderNum;
	}

	/**
	 * <p>
	 * 注文数を設定します
	 * </p>
	 * @param orderNum
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * <p>
	 * 入荷数を返却します
	 * </p>
	 * @return arrivalNum
	 */
	public int getArrivalNum() {
		return arrivalNum;
	}

	/**
	 * <p>
	 * 入荷数を設定します
	 * </p>
	 * @param arrivalNum
	 */
	public void setArrivalNum(int arrivalNum) {
		this.arrivalNum = arrivalNum;
	}

	/**
	 * <p>
	 * 入荷予定日を返却します
	 * </p>
	 */
	public String getArrivalScheduleDate() {
		return arrivalScheduleDate;
	}

	/**
	 * <p>
	 * 入荷予定日を設定します
	 * </p>
	 */
	public void setArrivalScheduleDate(String arrivalScheduleDate) {
		this.arrivalScheduleDate = arrivalScheduleDate;
	}

	/**
	 * <p>
	 * 入荷日を返却します
	 * </p>
	 * @return arrivalDate
	 */
	public String getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * <p>
	 * 入荷日を設定します
	 * </p>
	 * @param arrivalDate
	 */
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * <p>
	 * 注文ステータス名を返却します
	 * </p>
	 * @return orderStatusNm
	 */
	public String getOrderStatusNm() {
		return orderStatusNm;
	}

	/**
	 * <p>
	 * 注文ステータス名を設定します
	 * </p>
	 * @param orderStatusNm
	 */
	public void setOrderStatusNm(String orderStatusNm) {
		this.orderStatusNm = orderStatusNm;
	}

	/**
	 * <p>
	 * 仕入先IDを返却します
	 * </p>
	 * @return supplierId
	 */
//	public String getSupplierId() {
//		return supplierId;
//	}

	/**
	 * <p>
	 * 仕入先IDを設定します
	 * </p>
	 * @param supplierId
	 */
//	public void setSupplierId(String supplierId) {
//		this.supplierId = supplierId;
//	}

	/**
	 * <p>
	 * チェックボックスフラグを返却します。
	 * </p>
	 * @return checkBoxFlg
	 */
	public String getCheckBoxFlg() {
		return checkBoxFlg;
	}

	/**
	 * <p>
	 * チェックボックスフラグを設定します。
	 * </p>
	 * @param checkBoxFlg
	 */
	public void setCheckBoxFlg(String checkBoxFlg) {
		this.checkBoxFlg = checkBoxFlg;
	}

	/**
	 * @return itemList
	 */
	public List<ExtendForeignOrderItemDTO> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList
	 */
	public void setItemList(List<ExtendForeignOrderItemDTO> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @return paymentStatus1
	 */
	public String getPaymentStatus1() {
		return paymentStatus1;
	}

	/**
	 * @param paymentStatus1 セットする paymentStatus1
	 */
	public void setPaymentStatus1(String paymentStatus1) {
		this.paymentStatus1 = paymentStatus1;
	}

	/**
	 * @return paymentStatus2
	 */
	public String getPaymentStatus2() {
		return paymentStatus2;
	}

	/**
	 * @param paymentStatus2 セットする paymentStatus2
	 */
	public void setPaymentStatus2(String paymentStatus2) {
		this.paymentStatus2 = paymentStatus2;
	}


	/**
	 * <p>
	 * システム入荷予定ID を返却します。
	 * </p>
	 * @return sysArrivalScheduleId
	 */
	public long getSysArrivalScheduleId() {
		return this.sysArrivalScheduleId;
	}

	/**
	 * <p>
	 * システム入荷予定ID を設定します。
	 * </p>
	 * @param sysArrivalScheduleId
	 */
	public void setSysArrivalScheduleId(long sysArrivalScheduleId) {
		this.sysArrivalScheduleId = sysArrivalScheduleId;
	}


	/**
	 * @return arrivalScheduleList
	 */
	public List<ExtendArrivalScheduleDTO> getArrivalScheduleList() {
		return arrivalScheduleList;
	}

	/**
	 * @param arrivalScheduleList セットする arrivalScheduleList
	 */
	public void setArrivalScheduleList(List<ExtendArrivalScheduleDTO> arrivalScheduleList) {
		this.arrivalScheduleList = arrivalScheduleList;
	}

	/**
	 * <p>
	 * PDF振込依頼書１か２を返却します
	 * </p>
	 * @return transferPatern
	 */
	public String getTransferPatern() {
		return transferPatern;
	}

	/**
	 * <p>
	 * PDF振込依頼書１か２を設定します
	 * </p>
	 * @param transferPatern セットする transferPatern
	 */
	public void setTransferPatern(String transferPatern) {
		this.transferPatern = transferPatern;
	}

	/**
	 * @return totalStockNum
	 */
	public int getTotalStockNum() {
		return totalStockNum;
	}

	/**
	 * @param totalStockNum セットする totalStockNum
	 */
	public void setTotalStockNum(int totalStockNum) {
		this.totalStockNum = totalStockNum;
	}

	/**
	 * @return temporaryStockNum
	 */
	public int getTemporaryStockNum() {
		return temporaryStockNum;
	}

	/**
	 * @param temporaryStockNum セットする temporaryStockNum
	 */
	public void setTemporaryStockNum(int temporaryStockNum) {
		this.temporaryStockNum = temporaryStockNum;
	}

}