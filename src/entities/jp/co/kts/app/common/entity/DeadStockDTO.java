package jp.co.kts.app.common.entity;

import java.util.Date;

public class DeadStockDTO {

	/** システム不良在庫ID */
	private long sysDeadStockId;
	/** システム商品ID */
	private long sysItemId;
	/** 不良原因 */
	private String deadReason;
	/** 個数 */
	private int itemNum;
	/** 削除フラグ */
	private String deleteFlag;
	/** 更新日 */
	private Date updateDate;
	/** 更新者ID */
	private int updateUserId;
	/** 作成日 */
	private Date createDate;
	/** 作成者ID */
	private int createUserId;
	/**
	 * システム不良在庫IDを取得します。
	 * @return システム不良在庫ID
	 */
	public long getSysDeadStockId() {
	    return sysDeadStockId;
	}
	/**
	 * システム不良在庫IDを設定します。
	 * @param sysDeadStockId システム不良在庫ID
	 */
	public void setSysDeadStockId(long sysDeadStockId) {
	    this.sysDeadStockId = sysDeadStockId;
	}
	/**
	 * システム商品IDを取得します。
	 * @return システム商品ID
	 */
	public long getSysItemId() {
	    return sysItemId;
	}
	/**
	 * システム商品IDを設定します。
	 * @param sysItemId システム商品ID
	 */
	public void setSysItemId(long sysItemId) {
	    this.sysItemId = sysItemId;
	}
	/**
	 * 不良原因を取得します。
	 * @return 不良原因
	 */
	public String getDeadReason() {
	    return deadReason;
	}
	/**
	 * 不良原因を設定します。
	 * @param deadReason 不良原因
	 */
	public void setDeadReason(String deadReason) {
	    this.deadReason = deadReason;
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
	 * 作成日を取得します。
	 * @return 作成日
	 */
	public Date getCreateDate() {
	    return createDate;
	}
	/**
	 * 作成日を設定します。
	 * @param createDate 作成日
	 */
	public void setCreateDate(Date createDate) {
	    this.createDate = createDate;
	}
	/**
	 * 作成者IDを取得します。
	 * @return 作成者ID
	 */
	public int getCreateUserId() {
	    return createUserId;
	}
	/**
	 * 作成者IDを設定します。
	 * @param createUserId 作成者ID
	 */
	public void setCreateUserId(int createUserId) {
	    this.createUserId = createUserId;
	}


}