/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_販売チャネル情報を格納します。
 * 
 * @author admin
 */
public class MstChannelDTO  {

	/** システム販売チャネルID */
	private long sysChannelId;

	/** 販売チャネル名 */
	private String channelNm;

	/** URL */
	private String url;

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

	/** 売上高利益率 */
	private float salesProfitRate;
	
	/**
	 * <p>
	 * システム販売チャネルID を返却します。
	 * </p>
	 * @return sysChannelId
	 */
	public long getSysChannelId() {
		return this.sysChannelId;
	}

	/**
	 * <p>
	 * システム販売チャネルID を設定します。
	 * </p>
	 * @param sysChannelId
	 */
	public void setSysChannelId(long sysChannelId) {
		this.sysChannelId = sysChannelId;
	}

	/**
	 * <p>
	 * 販売チャネル名 を返却します。
	 * </p>
	 * @return channelNm
	 */
	public String getChannelNm() {
		return this.channelNm;
	}

	/**
	 * <p>
	 * 販売チャネル名 を設定します。
	 * </p>
	 * @param channelNm
	 */
	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}

	/**
	 * <p>
	 * URL を返却します。
	 * </p>
	 * @return url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * <p>
	 * URL を設定します。
	 * </p>
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the salesProfitRate
	 */
	public float getSalesProfitRate() {
		return salesProfitRate;
	}

	/**
	 * @param salesProfitRate the salesProfitRate to set
	 */
	public void setSalesProfitRate(float salesProfitRate) {
		this.salesProfitRate = salesProfitRate;
	}

}

