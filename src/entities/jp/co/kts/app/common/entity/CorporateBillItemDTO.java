/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 業販出力用の請求書商品情報を格納します。
 *
 * @author admin
 */
public class CorporateBillItemDTO extends CorporateSalesItemDTO  {

	/** システム法人間請求書商品ID */
	private long sysCorporateBillItemId;

	/** システム法人間請求書ID */
	private long sysCorporateBillId;

	/** 業販区分 */
	private String corporateSalesFlg;

	/** 売上日 */
	private String salesDate;

	/** 伝票番号 */
	private String slipNo;

	/** システム商品ID */
	private long sysItemId;

	/** 品番 */
	private String itemCode;

	/** 商品名 */
	private String itemNm;

	/** 注文数 */
	private int orderNum;

	/** 単価 */
	private int pieceRate;

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

	/** アカウントID */
	private int sysAccountId;

	/**
	 * <p>
	 * システム法人間請求書商品ID を返却します。
	 * </p>
	 * @return sysCorporateBillItemId
	 */
	public long getSysCorporateBillItemId() {
		return this.sysCorporateBillItemId;
	}

	/**
	 * <p>
	 * システム法人間請求書商品ID を設定します。
	 * </p>
	 * @param sysCorporateBillItemId
	 */
	public void setSysCorporateBillItemId(long sysCorporateBillItemId) {
		this.sysCorporateBillItemId = sysCorporateBillItemId;
	}

	/**
	 * <p>
	 * システム法人間請求書ID を返却します。
	 * </p>
	 * @return sysCorporateBillId
	 */
	public long getSysCorporateBillId() {
		return this.sysCorporateBillId;
	}

	/**
	 * <p>
	 * システム法人間請求書ID を設定します。
	 * </p>
	 * @param sysCorporateBillId
	 */
	public void setSysCorporateBillId(long sysCorporateBillId) {
		this.sysCorporateBillId = sysCorporateBillId;
	}

	/**
	 * <p>
	 * 業販区分 を返却します。
	 * </p>
	 * @return corporateSalesFlg
	 */
	public String getCorporateSalesFlg() {
		return this.corporateSalesFlg;
	}

	/**
	 * <p>
	 * 業販区分 を設定します。
	 * </p>
	 * @param corporateSalesFlg
	 */
	public void setCorporateSalesFlg(String corporateSalesFlg) {
		this.corporateSalesFlg = corporateSalesFlg;
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
	 * システム商品ID を返却します。
	 * </p>
	 * @return sysItemId
	 */
	public long getSysItemId() {
		return this.sysItemId;
	}

	/**
	 * <p>
	 * システム商品ID を設定します。
	 * </p>
	 * @param sysItemId
	 */
	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	/**
	 * <p>
	 * 品番 を返却します。
	 * </p>
	 * @return itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * <p>
	 * 品番 を設定します。
	 * </p>
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
	 * 注文数 を返却します。
	 * </p>
	 * @return orderNum
	 */
	public int getOrderNum() {
		return this.orderNum;
	}

	/**
	 * <p>
	 * 注文数 を設定します。
	 * </p>
	 * @param orderNum
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
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
	 * 口座ID を返却します。
	 * </p>
	 * @return sys_account_id
	 */
	public int getSysAccounId() {
		return this.sysAccountId;
	}

	/**
	 * <p>
	 * 口座ID を設定します。
	 * </p>
	 * @param sys_account_id
	 */
	public void setSysAccountId(int sysAccountId) {
		this.sysAccountId = sysAccountId;
	}

}

