package jp.co.kts.ui.mst;

import java.util.Date;

/**
 * [概要] 海外注文商品情報を格納します
 * @author Boncre
 *
 */
public class ForeignOrderItemDTO {

	/** システム海外注文伝票ID */
	private long sysForeignSlipId;

	/** システム海外注文商品ID */
	private long sysForeignSlipItemId;

	/** システム商品ID */
	private long sysItemId;

	/** システム仕入先ID */
	private long sysSupplierId;

	/** 品番 */
	private String itemCode;

	/** 工場品番 */
	private String factoryItemCode;

	/** 商品名 */
	private String itemNm;

	/** 注文数 */
	private int orderNum;

	/** 単価 */
	private float unitPrice;

	/** 入荷済みフラグ */
	private String arrivalFlag;

	/** 削除フラグ */
	private String deleteFlag;

	/** 更新日 */
	private Date updateDate;

	/** 更新者ID */
	private int updateUserId;

	/** 作成日 */
	private Date createDate;

	/** 作成者ID */
	private int createUserDate;

	/** 注文プールフラグ */
	private String orderPoolFlg;

	/** 海外商品名 */
	private String foreignItemNm;



	/**
	 * システム海外注文伝票ID
	 * @return sysForeignSlipId
	 */
	public long getSysForeignSlipId() {
		return sysForeignSlipId;
	}

	/**
	 * システム海外注文伝票ID
	 * @param sysForeignSlipId セットする sysForeignSlipId
	 */
	public void setSysForeignSlipId(long sysForeignSlipId) {
		this.sysForeignSlipId = sysForeignSlipId;
	}

	/**
	 * システム海外注文商品ID
	 * @return sysForeignSlipItemId
	 */
	public long getSysForeignSlipItemId() {
		return sysForeignSlipItemId;
	}

	/**
	 * システム海外注文商品ID
	 * @param sysForeignSlipItemId セットする sysForeignSlipItemId
	 */
	public void setSysForeignSlipItemId(long sysForeignSlipItemId) {
		this.sysForeignSlipItemId = sysForeignSlipItemId;
	}

	/**
	 * システム商品ID
	 * @return sysItemId
	 */
	public long getSysItemId() {
		return sysItemId;
	}

	/**
	 * システム商品ID
	 * @param sysItemId セットする sysItemId
	 */
	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	/**
	 * 品番
	 * @return itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * 品番
	 * @param itemCode セットする itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 工場品番
	 * @return factoryItemCode
	 */
	public String getFactoryItemCode() {
		return factoryItemCode;
	}

	/**
	 * 工場品番
	 * @param factoryItemCode セットする factoryItemCode
	 */
	public void setFactoryItemCode(String factoryItemCode) {
		this.factoryItemCode = factoryItemCode;
	}

	/**
	 * 商品名
	 * @return itemNm
	 */
	public String getItemNm() {
		return itemNm;
	}

	/**
	 * 商品名
	 * @param itemNm セットする itemNm
	 */
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	/**
	 * 注文数
	 * @return orderNum
	 */
	public int getOrderNum() {
		return orderNum;
	}

	/**
	 * 注文数
	 * @param orderNum セットする orderNum
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 単価
	 * @return unitPrice
	 */
	public float getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 単価
	 * @param unitPrice セットする unitPrice
	 */
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 入荷済みフラグ
	 * @return arrivalFlag
	 */
	public String getArrivalFlag() {
		return arrivalFlag;
	}

	/**
	 * 入荷済みフラグ
	 * @param arrivalFlag セットする arrivalFlag
	 */
	public void setArrivalFlag(String arrivalFlag) {
		this.arrivalFlag = arrivalFlag;
	}

	/**
	 * 削除フラグ
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * 削除フラグ
	 * @param deleteFlag セットする deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * 更新日
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 更新日
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 更新者ID
	 * @return updateUserId
	 */
	public int getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * 更新者ID
	 * @param updateUserId セットする updateUserId
	 */
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * 登録日
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 登録日
	 * @param createDate セットする createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 登録者ID
	 * @return createUserDate
	 */
	public int getCreateUserDate() {
		return createUserDate;
	}

	/**
	 * 登録者ID
	 * @param createUserDate セットする createUserDate
	 */
	public void setCreateUserDate(int createUserDate) {
		this.createUserDate = createUserDate;
	}

	/**
	 * @return orderPoolFlg
	 */
	public String getOrderPoolFlg() {
		return orderPoolFlg;
	}

	/**
	 * @param orderPoolFlg セットする orderPoolFlg
	 */
	public void setOrderPoolFlg(String orderPoolFlg) {
		this.orderPoolFlg = orderPoolFlg;
	}

	/**
	 * @return sysSupplierId
	 */
	public long getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * @param sysSupplierId セットする sysSupplierId
	 */
	public void setSysSupplierId(long sysSupplierId) {
		this.sysSupplierId = sysSupplierId;
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
}
