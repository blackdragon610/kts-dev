package jp.co.kts.app.common.entity;

import java.util.Date;

/**
 * M_メーカーを格納します
 * @author 齋藤 優太
 *
 */
public class MstMakerDTO {

	/** システムメーカーID */
	private long sysMakerId;
	/** メーカー名称 */
	private String makerNm;
	/** メーカー名称カナ */
	private String makerNmKana;
	/** 担当者名 */
	private String contactPersonNm;
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
	 * システムメーカーIDを取得します。
	 * @return システムメーカーID
	 */
	public long getSysMakerId() {
	    return sysMakerId;
	}
	/**
	 * システムメーカーIDを設定します。
	 * @param sysMakerId システムメーカーID
	 */
	public void setSysMakerId(long sysMakerId) {
	    this.sysMakerId = sysMakerId;
	}
	/**
	 * メーカー名称を取得します。
	 * @return メーカー名称
	 */
	public String getMakerNm() {
	    return makerNm;
	}
	/**
	 * メーカー名称を設定します。
	 * @param makerNm メーカー名称
	 */
	public void setMakerNm(String makerNm) {
	    this.makerNm = makerNm;
	}
	/**
	 * メーカー名称カナを取得します。
	 * @return メーカー名称カナ
	 */
	public String getMakerNmKana() {
	    return makerNmKana;
	}
	/**
	 * メーカー名称カナを設定します。
	 * @param makerNmKana メーカー名称カナ
	 */
	public void setMakerNmKana(String makerNmKana) {
	    this.makerNmKana = makerNmKana;
	}
	/**
	 * 担当者名を取得します。
	 * @return contactPersonNm
	 */
	public String getContactPersonNm() {
		return contactPersonNm;
	}
	/**
	 * 担当者名を設定します。
	 * @param contactPersonNm セットする contactPersonNm
	 */
	public void setContactPersonNm(String contactPersonNm) {
		this.contactPersonNm = contactPersonNm;
	}
	/**
	 * 登録日を取得します。
	 * @return 登録日
	 */
	public Date getCreateDate() {
	    return createDate;
	}
	/**
	 * 登録日を設定します。
	 * @param createDate 登録日
	 */
	public void setCreateDate(Date createDate) {
	    this.createDate = createDate;
	}
	/**
	 * 登録者IDを取得します。
	 * @return 登録者ID
	 */
	public int getCreateUserId() {
	    return createUserId;
	}
	/**
	 * 登録者IDを設定します。
	 * @param createUserId 登録者ID
	 */
	public void setCreateUserId(int createUserId) {
	    this.createUserId = createUserId;
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

}
