/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_中分類情報を格納します。
 * 
 * @author admin
 */
public class MstMiddleGroupDTO  {

	/** システム中分類ID */
	private long sysMiddleGroupId;

	/** 中分類NO */
	private String middleGroupNo;

	/** 中分類名 */
	private String middleGroupNm;

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
	 * システム中分類ID を返却します。
	 * </p>
	 * @return sysMiddleGroupId
	 */
	public long getSysMiddleGroupId() {
		return this.sysMiddleGroupId;
	}

	/**
	 * <p>
	 * システム中分類ID を設定します。
	 * </p>
	 * @param sysMiddleGroupId
	 */
	public void setSysMiddleGroupId(long sysMiddleGroupId) {
		this.sysMiddleGroupId = sysMiddleGroupId;
	}

	/**
	 * <p>
	 * 中分類NO を返却します。
	 * </p>
	 * @return middleGroupNo
	 */
	public String getMiddleGroupNo() {
		return this.middleGroupNo;
	}

	/**
	 * <p>
	 * 中分類NO を設定します。
	 * </p>
	 * @param middleGroupNo
	 */
	public void setMiddleGroupNo(String middleGroupNo) {
		this.middleGroupNo = middleGroupNo;
	}

	/**
	 * <p>
	 * 中分類名 を返却します。
	 * </p>
	 * @return middleGroupNm
	 */
	public String getMiddleGroupNm() {
		return this.middleGroupNm;
	}

	/**
	 * <p>
	 * 中分類名 を設定します。
	 * </p>
	 * @param middleGroupNm
	 */
	public void setMiddleGroupNm(String middleGroupNm) {
		this.middleGroupNm = middleGroupNm;
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

}

