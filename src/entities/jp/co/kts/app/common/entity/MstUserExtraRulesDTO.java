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
public class MstUserExtraRulesDTO  {

	/** マスターID */
	private long ruleId;
	
	private long extraId;
	
	private long sysUserId;
	/** 登録日 */
	private Date createDate;

	/** 更新日 */
	private Date updateDate;
	

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	
	public long getExtraId() {
		return extraId;
	}

	public void setExtraId(long extraId) {
		this.extraId = extraId;
	}

	public long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
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
	
}

