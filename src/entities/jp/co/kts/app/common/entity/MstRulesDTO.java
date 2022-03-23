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
/**
 * @author SUN
 *
 */
public class MstRulesDTO  {

	/** マスターID */
	private long ruleId;
	
	/** マスターName */
	private String ruleName;
	
	/** 削除フラグ */
	private String deleteFlag;

	/** 登録日 */
	private Date createDate;

	/** 更新日 */
	private Date updateDate;
	
	/** 項目削除フラグ */
	private String itemCheckFlg;
	
	private String isvisible;
	
	private List<MstRulesListDTO> mstRulesDetailList = new ArrayList<>();
	
	private int childCount;

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	/**
	 * 項目削除フラグを取得します。
	 * @return 項目削除フラグ
	 */
	public String getItemCheckFlg() {
	    return itemCheckFlg;
	}

	/**
	 * 項目削除フラグを設定します。
	 * @param itemCheckFlg 項目削除フラグ
	 */
	public void setItemCheckFlg(String itemCheckFlg) {
	    this.itemCheckFlg = itemCheckFlg;
	}

	/**
	 * @return the isvisible
	 */
	public String getIsvisible() {
		return isvisible;
	}

	/**
	 * @param isvisible the isvisible to set
	 */
	public void setIsvisible(String isvisible) {
		this.isvisible = isvisible;
	}

	/**
	 * @return the mstRulesDetailList
	 */
	public List<MstRulesListDTO> getMstRulesDetailList() {
		return mstRulesDetailList;
	}

	/**
	 * @param mstRulesDetailList the mstRulesDetailList to set
	 */
	public void setMstRulesDetailList(List<MstRulesListDTO> mstRulesDetailList) {
		this.mstRulesDetailList = mstRulesDetailList;
	}

	public int getChildCount() {
		return this.mstRulesDetailList.size();
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	
}

