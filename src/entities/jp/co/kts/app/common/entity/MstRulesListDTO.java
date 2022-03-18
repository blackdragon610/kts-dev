/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.Date;
/**
 * M_ユーザー情報を格納します。
 *
 * @author admin
 */
/**
 * @author SUN
 *
 */
public class MstRulesListDTO  {

	/** マスターID */
	private long ruleId;
	
	private long ruleListId;
	
	private long sysUserId;
	
	private String listName;
	
	private String listId;
	
	private String listPass;
	
	private String listLink;
	
	private String listRemarks;
	
	private String listVisible;
	
	/** 削除フラグ */
	private String delFlag;

	/** 登録日 */
	private Date createDate;

	/** 更新日 */
	private Date updateDate;
	
	/** 項目削除フラグ */
	private String itemCheckFlg;

	/**
	 * @return the ruleId
	 */
	public long getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the ruleListId
	 */
	public long getRuleListId() {
		return ruleListId;
	}

	/**
	 * @param ruleListId the ruleListId to set
	 */
	public void setRuleListId(long ruleListId) {
		this.ruleListId = ruleListId;
	}

	/**
	 * @return the sysUserId
	 */
	public long getSysUserId() {
		return sysUserId;
	}

	/**
	 * @param sysUserId the sysUserId to set
	 */
	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * @return the listName
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * @param listName the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}

	/**
	 * @return the listId
	 */
	public String getListId() {
		return listId;
	}

	/**
	 * @param listId the listId to set
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

	/**
	 * @return the listPass
	 */
	public String getListPass() {
		return listPass;
	}

	/**
	 * @param listPass the listPass to set
	 */
	public void setListPass(String listPass) {
		this.listPass = listPass;
	}

	/**
	 * @return the listLink
	 */
	public String getListLink() {
		return listLink;
	}

	/**
	 * @param listLink the listLink to set
	 */
	public void setListLink(String listLink) {
		this.listLink = listLink;
	}

	/**
	 * @return the listRemarks
	 */
	public String getListRemarks() {
		return listRemarks;
	}

	/**
	 * @param listRemarks the listRemarks to set
	 */
	public void setListRemarks(String listRemarks) {
		this.listRemarks = listRemarks;
	}

	/**
	 * @return the listVisible
	 */
	public String getListVisible() {
		return listVisible;
	}

	/**
	 * @param listVisible the listVisible to set
	 */
	public void setListVisible(String listVisible) {
		this.listVisible = listVisible;
	}

	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the itemCheckFlg
	 */
	public String getItemCheckFlg() {
		return itemCheckFlg;
	}

	/**
	 * @param itemCheckFlg the itemCheckFlg to set
	 */
	public void setItemCheckFlg(String itemCheckFlg) {
		this.itemCheckFlg = itemCheckFlg;
	}

	
	

}

