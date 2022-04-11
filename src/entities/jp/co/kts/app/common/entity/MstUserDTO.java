/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * M_ユーザー情報を格納します。
 *
 * @author admin
 */
public class MstUserDTO  {

	/** システムユーザーID */
	private long sysUserId;

	/** ログインコード */
	private String loginCd;

	/** パスワード */
	private String password;

	/** ユーザ姓（漢字） */
	private String userFamilyNmKanji;

	/** ユーザ名（漢字） */
	private String userFirstNmKanji;

	/** ユーザ姓（カナ） */
	private String userFamilyNmKana;

	/** ユーザ名（カナ） */
	private String userFirstNmKana;

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

	/** 法人間請求権限 */
	private String btobBillAuth;

	/** 海外情報閲覧権限 */
	private String overseasInfoAuth;
	
	/** 担当者番号 */
	private String responsibleNo;
	
	/* ID・PASS削除権限 */
	private String idPassDelAuth;
	
	private List<MstRulesDTO> mstRulesList = new ArrayList<>();
	
	private List<MstMasterDTO> mstMasterList = new ArrayList<>();
	/**
	 * <p>
	 * システムユーザーID を返却します。
	 * </p>
	 * @return sysUserId
	 */
	public long getSysUserId() {
		return this.sysUserId;
	}

	/**
	 * <p>
	 * システムユーザーID を設定します。
	 * </p>
	 * @param sysUserId
	 */
	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * <p>
	 * ログインコード を返却します。
	 * </p>
	 * @return loginCd
	 */
	public String getLoginCd() {
		return this.loginCd;
	}

	/**
	 * <p>
	 * ログインコード を設定します。
	 * </p>
	 * @param loginCd
	 */
	public void setLoginCd(String loginCd) {
		this.loginCd = loginCd;
	}

	/**
	 * <p>
	 * パスワード を返却します。
	 * </p>
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * <p>
	 * パスワード を設定します。
	 * </p>
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <p>
	 * ユーザ姓（漢字） を返却します。
	 * </p>
	 * @return userFamilyNmKanji
	 */
	public String getUserFamilyNmKanji() {
		return this.userFamilyNmKanji;
	}

	/**
	 * <p>
	 * ユーザ姓（漢字） を設定します。
	 * </p>
	 * @param userFamilyNmKanji
	 */
	public void setUserFamilyNmKanji(String userFamilyNmKanji) {
		this.userFamilyNmKanji = userFamilyNmKanji;
	}

	/**
	 * <p>
	 * ユーザ名（漢字） を返却します。
	 * </p>
	 * @return userFirstNmKanji
	 */
	public String getUserFirstNmKanji() {
		return this.userFirstNmKanji;
	}

	/**
	 * <p>
	 * ユーザ名（漢字） を設定します。
	 * </p>
	 * @param userFirstNmKanji
	 */
	public void setUserFirstNmKanji(String userFirstNmKanji) {
		this.userFirstNmKanji = userFirstNmKanji;
	}

	/**
	 * <p>
	 * ユーザ姓（カナ） を返却します。
	 * </p>
	 * @return userFamilyNmKana
	 */
	public String getUserFamilyNmKana() {
		return this.userFamilyNmKana;
	}

	/**
	 * <p>
	 * ユーザ姓（カナ） を設定します。
	 * </p>
	 * @param userFamilyNmKana
	 */
	public void setUserFamilyNmKana(String userFamilyNmKana) {
		this.userFamilyNmKana = userFamilyNmKana;
	}

	/**
	 * <p>
	 * ユーザ名（カナ） を返却します。
	 * </p>
	 * @return userFirstNmKana
	 */
	public String getUserFirstNmKana() {
		return this.userFirstNmKana;
	}

	/**
	 * <p>
	 * ユーザ名（カナ） を設定します。
	 * </p>
	 * @param userFirstNmKana
	 */
	public void setUserFirstNmKana(String userFirstNmKana) {
		this.userFirstNmKana = userFirstNmKana;
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
	 * 法人間請求権限 を返却します。
	 * </p>
	 * @return btobBillAuth
	 */
	public String getBtobBillAuth() {
		return this.btobBillAuth;
	}

	/**
	 * <p>
	 * 法人間請求権限 を設定します。
	 * </p>
	 * @param btobBillAuth
	 */
	public void setBtobBillAuth(String btobBillAuth) {
		this.btobBillAuth = btobBillAuth;
	}

	/**
	 * 海外情報閲覧権限を取得します。
	 * @return 海外情報閲覧権限
	 */
	public String getOverseasInfoAuth() {
	    return overseasInfoAuth;
	}

	/**
	 * 海外情報閲覧権限を設定します。
	 * @param overseasInfoAuth 海外情報閲覧権限
	 */
	public void setOverseasInfoAuth(String overseasInfoAuth) {
	    this.overseasInfoAuth = overseasInfoAuth;
	}

	/**
	 * 担当者番号を取得します。
	 * @return 担当者番号
	 */
	public String getResponsibleNo() {
	    return responsibleNo;
	}

	/**
	 * 担当者番号を設定します。
	 * @param responsibleNo 担当者番号
	 */
	public void setResponsibleNo(String responsibleNo) {
	    this.responsibleNo = responsibleNo;
	}

	/**
	 * @return the mstRulesList
	 */
	public List<MstRulesDTO> getMstRulesList() {
		return mstRulesList;
	}

	/**
	 * @param mstRulesList the mstRulesList to set
	 */
	public void setMstRulesList(List<MstRulesDTO> mstRulesList) {
		this.mstRulesList = mstRulesList;
	}

	/**
	 * @return the mstMasterList
	 */
	public List<MstMasterDTO> getMstMasterList() {
		return mstMasterList;
	}

	/**
	 * @param mstMasterList the mstMasterList to set
	 */
	public void setMstMasterList(List<MstMasterDTO> mstMasterList) {
		this.mstMasterList = mstMasterList;
	}

	/**
	 * @return the idPassDelAuth
	 */
	public String getIdPassDelAuth() {
		return idPassDelAuth;
	}

	/**
	 * @param idPassDelAuth the idPassDelAuth to set
	 */
	public void setIdPassDelAuth(String idPassDelAuth) {
		this.idPassDelAuth = idPassDelAuth;
	}

}

