package jp.co.kts.app.common.entity;

import java.util.Date;

/**
 * 更新情報を格納するDTO
 * @author Boncre
 *
 */
public class UpdateDataHistoryDTO {

	/** システム更新情報データID */
	private long sysUpdateDataId;

	/** システム商品ID */
	private long sysItemId;

	/** 更新履歴 */
	private String updateHistory;

	/** 削除フラグ */
	private String deleteFlag;

	/** 更新日時 */
	private Date updateDate;

	/** 更新者ID */
	private long updateUserId;

	/** 作成日時 */
	private Date createDate;

	/** 作成者ID */
	private long createUserId;

	/**
	 * <p>
	 * システム更新情報IDを返却します
	 * </p>
	 * @return sysUpdateDataId
	 */
	public long getSysUpdateDataId() {
		return sysUpdateDataId;
	}

	/**
	 * <p>
	 * システム更新情報IDを取得します
	 * </p>
	 * @param sysUpdateDataId
	 */
	public void setSysUpdateDataId(long sysUpdateDataId) {
		this.sysUpdateDataId = sysUpdateDataId;
	}

	/**<p>
	 * システム商品IDを返却します
	 * </p>
	 * @return sysItemId
	 */
	public long getSysItemId() {
		return sysItemId;
	}

	/**
	 * <p>
	 * システム商品IDを設定します
	 * </p>
	 * @param sysItemId
	 */
	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	/**
	 * <p>
	 * 更新履歴を返却します
	 * </p>
	 * @return updateHistory
	 */
	public String getUpdateHistory() {
		return updateHistory;
	}

	/**
	 * <p>
	 * 更新履歴を設定します
	 * </p>
	 * @param updateHistory
	 */
	public void setUpdateHistory(String updateHistory) {
		this.updateHistory = updateHistory;
	}

	/**
	 * <p>
	 * 削除フラグを返却します
	 * </p>
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * <p>
	 * 削除フラグを設定します
	 * </p>
	 * @param deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * <p>
	 * 更新日時を返却します
	 * </p>
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * <p>
	 * 更新日時を設定します
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
	public long getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * <p>
	 * 更新者IDを設定します
	 * </p>
	 * @param updateUserId
	 */
	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * <p>
	 * 更新日時を返却します
	 * </p>
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * <p>
	 * 更新日時を設定します
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
	public long getCreateUserId() {
		return createUserId;
	}

	/**
	 * <p>
	 * 作成者IDを返却します
	 * </p>
	 * @param createUserId
	 */
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
}
