/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 入荷予定情報を格納します。
 *
 * @author admin
 */
public class ArrivalScheduleDTO  {

	/** システム入荷予定ID */
	private long sysArrivalScheduleId;

	/** システム商品ID */
	private long sysItemId;

	private String orderStatus;

	/** システム海外伝票商品ID */
	private long sysForeignSlipItemId;

	/** システム海外伝票ID */
	private long sysForeignSlipId;

	/** 発注日 */
	private String itemOrderDate;

	/** 入荷予定日 */
	private String arrivalScheduleDate;

	/** 曖昧予定日 */
	private String vagueArrivalSchedule;

	/** 入荷数 */
	private int arrivalNum;

	/** 入荷フラグ */
	private String arrivalFlag;

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
	 * @return orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus セットする orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return sysForeignSlipId
	 */
	public long getSysForeignSlipId() {
		return sysForeignSlipId;
	}

	/**
	 * @param sysForeignSlipId セットする sysForeignSlipId
	 */
	public void setSysForeignSlipId(long sysForeignSlipId) {
		this.sysForeignSlipId = sysForeignSlipId;
	}

	/**
	 * <p>
	 * 発注日 を返却します。
	 * </p>
	 * @return itemOrderDate
	 */
	public String getItemOrderDate() {
		return this.itemOrderDate;
	}

	/**
	 * <p>
	 * 発注日 を設定します。
	 * </p>
	 * @param itemOrderDate
	 */
	public void setItemOrderDate(String itemOrderDate) {
		this.itemOrderDate = itemOrderDate;
	}

	/**
	 * <p>
	 * 入荷予定日 を返却します。
	 * </p>
	 * @return arrivalScheduleDate
	 */
	public String getArrivalScheduleDate() {
		return this.arrivalScheduleDate;
	}

	/**
	 * <p>
	 * 入荷予定日 を設定します。
	 * </p>
	 * @param arrivalScheduleDate
	 */
	public void setArrivalScheduleDate(String arrivalScheduleDate) {
		this.arrivalScheduleDate = arrivalScheduleDate;
	}

	/**
	 * <p>
	 * 入荷数 を返却します。
	 * </p>
	 * @return arrivalNum
	 */
	public int getArrivalNum() {
		return this.arrivalNum;
	}

	/**
	 * <p>
	 * 入荷数 を設定します。
	 * </p>
	 * @param arrivalNum
	 */
	public void setArrivalNum(int arrivalNum) {
		this.arrivalNum = arrivalNum;
	}

	/**
	 * <p>
	 * 入荷フラグ を返却します。
	 * </p>
	 * @return arrivalFlag
	 */
	public String getArrivalFlag() {
		return this.arrivalFlag;
	}

	/**
	 * <p>
	 * 入荷フラグ を設定します。
	 * </p>
	 * @param arrivalFlag
	 */
	public void setArrivalFlag(String arrivalFlag) {
		this.arrivalFlag = arrivalFlag;
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
	 * @return vagueArrivalSchedule
	 */
	public String getVagueArrivalSchedule() {
		return vagueArrivalSchedule;
	}

	/**
	 * @param vagueArrivalSchedule セットする vagueArrivalSchedule
	 */
	public void setVagueArrivalSchedule(String vagueArrivalSchedule) {
		this.vagueArrivalSchedule = vagueArrivalSchedule;
	}

	/**
	 * <p>
	 *  システム海外伝票商品ＩＤを返却します。
	 * </p>
	 * @return sysForeignSlipItemId
	 */
	public long getSysForeignSlipItemId() {
		return sysForeignSlipItemId;
	}

	/**
	 * <p>
	 * システム海外伝票商品ＩＤ を設定します。
	 * </p>
	 * @param sysForeignSlipItemId
	 */
	public void setSysForeignSlipItemId(long sysForeignSlipItemId) {
		this.sysForeignSlipItemId = sysForeignSlipItemId;
	}

}

