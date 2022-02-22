/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * 掲示板情報を格納します。
 *
 * @author admin
 */
public class NoticeBoardDTO  {

	/** システム記事ID */
	private long noticeSystemId;

	/** 記事 */
	private String notice;

	/** 登録日 */
	private Date createDate;

	/** 登録者ID */
	private int createUserId;

	/** 削除フラグ */
	private String deleteFlag;

	/** 更新日 */
	private Date updateDate;

	/** 更新者ID */
	private int updateUserId;

	/**
	 * <p>
	 * システム記事ID を返却します。
	 * </p>
	 * @return noticeSystemId
	 */
	public long getNoticeSystemId() {
		return this.noticeSystemId;
	}

	/**
	 * <p>
	 * システム記事ID を設定します。
	 * </p>
	 * @param noticeSystemId
	 */
	public void setNoticeSystemId(long noticeSystemId) {
		this.noticeSystemId = noticeSystemId;
	}

	/**
	 * <p>
	 * 記事 を返却します。
	 * </p>
	 * @return notice
	 */
	public String getNotice() {
		return this.notice;
	}

	/**
	 * <p>
	 * 記事 を設定します。
	 * </p>
	 * @param notice
	 */
	public void setNotice(String notice) {
		this.notice = notice;
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
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return updateUserId
	 */
	public int getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId セットする updateUserId
	 */
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

}

