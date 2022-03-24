/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

/**
 * M_ユーザー情報を格納します。
 *
 * @author admin
 */
public class MstMasterDTO  {

	private long sysUserId;
	
	private String userListFlg;
	
	private String ruleListFlg;
	
	private String corporationListFlg;
	
	private String accountListFlg;
	
	private String channelListFlg;
	
	private String warehouseListFlg;
	
	private String makerListFlg;
	
	private String setItemListFlg;
	
	private String clientListFlg;
	
	private String deliveryListFlg;
	
	private String userListName;
	
	private String ruleListName;
	
	private String corporationListName;
	
	private String accountListName;
	
	private String channelListName;
	
	private String warehouseListName;
	
	private String makerListName;
	
	private String setItemListName;
	
	private String clientListName;
	
	private String deliveryListName;
	
	private int viewableCount;
	
	private String isvisible = "0";
	
	private String childrenMasterCheckedFlag = "-1";
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
	 * @return the userListFlg
	 */
	public String getUserListFlg() {
		return userListFlg;
	}

	/**
	 * @param userListFlg the userListFlg to set
	 */
	public void setUserListFlg(String userListFlg) {
		this.userListFlg = userListFlg;
	}

	/**
	 * @return the ruleListFlg
	 */
	public String getRuleListFlg() {
		return ruleListFlg;
	}

	/**
	 * @param ruleListFlg the ruleListFlg to set
	 */
	public void setRuleListFlg(String ruleListFlg) {
		this.ruleListFlg = ruleListFlg;
	}

	/**
	 * @return the corporatinListFlg
	 */
	public String getCorporationListFlg() {
		return corporationListFlg;
	}

	/**
	 * @param corporatinListFlg the corporatinListFlg to set
	 */
	public void setCorporationListFlg(String corporationListFlg) {
		this.corporationListFlg = corporationListFlg;
	}

	/**
	 * @return the accountListFlg
	 */
	public String getAccountListFlg() {
		return accountListFlg;
	}

	/**
	 * @param accountListFlg the accountListFlg to set
	 */
	public void setAccountListFlg(String accountListFlg) {
		this.accountListFlg = accountListFlg;
	}

	/**
	 * @return the channelListFlg
	 */
	public String getChannelListFlg() {
		return channelListFlg;
	}

	/**
	 * @param channelListFlg the channelListFlg to set
	 */
	public void setChannelListFlg(String channelListFlg) {
		this.channelListFlg = channelListFlg;
	}

	/**
	 * @return the warehouseListFlg
	 */
	public String getWarehouseListFlg() {
		return warehouseListFlg;
	}

	/**
	 * @param warehouseListFlg the warehouseListFlg to set
	 */
	public void setWarehouseListFlg(String warehouseListFlg) {
		this.warehouseListFlg = warehouseListFlg;
	}

	/**
	 * @return the makerListFlg
	 */
	public String getMakerListFlg() {
		return makerListFlg;
	}

	/**
	 * @param makerListFlg the makerListFlg to set
	 */
	public void setMakerListFlg(String makerListFlg) {
		this.makerListFlg = makerListFlg;
	}

	/**
	 * @return the setItemListFlg
	 */
	public String getSetItemListFlg() {
		return setItemListFlg;
	}

	/**
	 * @param setItemListFlg the setItemListFlg to set
	 */
	public void setSetItemListFlg(String setItemListFlg) {
		this.setItemListFlg = setItemListFlg;
	}

	/**
	 * @return the clientListFlg
	 */
	public String getClientListFlg() {
		return clientListFlg;
	}

	/**
	 * @param clientListFlg the clientListFlg to set
	 */
	public void setClientListFlg(String clientListFlg) {
		this.clientListFlg = clientListFlg;
	}

	/**
	 * @return the deliveryListFlg
	 */
	public String getDeliveryListFlg() {
		return deliveryListFlg;
	}

	/**
	 * @param deliveryListFlg the deliveryListFlg to set
	 */
	public void setDeliveryListFlg(String deliveryListFlg) {
		this.deliveryListFlg = deliveryListFlg;
	}
	
	

	/**
	 * @return the userListName
	 */
	public String getUserListName() {
		return userListName;
	}

	/**
	 * @param userListName the userListName to set
	 */
	public void setUserListName(String userListName) {
		this.userListName = userListName;
	}

	/**
	 * @return the ruleListName
	 */
	public String getRuleListName() {
		return ruleListName;
	}

	/**
	 * @param ruleListName the ruleListName to set
	 */
	public void setRuleListName(String ruleListName) {
		this.ruleListName = ruleListName;
	}

	/**
	 * @return the corporationListName
	 */
	public String getCorporationListName() {
		return corporationListName;
	}

	/**
	 * @param corporationListName the corporationListName to set
	 */
	public void setCorporationListName(String corporationListName) {
		this.corporationListName = corporationListName;
	}

	/**
	 * @return the accountListName
	 */
	public String getAccountListName() {
		return accountListName;
	}

	/**
	 * @param accountListName the accountListName to set
	 */
	public void setAccountListName(String accountListName) {
		this.accountListName = accountListName;
	}

	/**
	 * @return the channelListName
	 */
	public String getChannelListName() {
		return channelListName;
	}

	/**
	 * @param channelListName the channelListName to set
	 */
	public void setChannelListName(String channelListName) {
		this.channelListName = channelListName;
	}

	/**
	 * @return the warehouseListName
	 */
	public String getWarehouseListName() {
		return warehouseListName;
	}

	/**
	 * @param warehouseListName the warehouseListName to set
	 */
	public void setWarehouseListName(String warehouseListName) {
		this.warehouseListName = warehouseListName;
	}

	/**
	 * @return the makerListName
	 */
	public String getMakerListName() {
		return makerListName;
	}

	/**
	 * @param makerListName the makerListName to set
	 */
	public void setMakerListName(String makerListName) {
		this.makerListName = makerListName;
	}

	/**
	 * @return the setItemListName
	 */
	public String getSetItemListName() {
		return setItemListName;
	}

	/**
	 * @param setItemListName the setItemListName to set
	 */
	public void setSetItemListName(String setItemListName) {
		this.setItemListName = setItemListName;
	}

	/**
	 * @return the clientListName
	 */
	public String getClientListName() {
		return clientListName;
	}

	/**
	 * @param clientListName the clientListName to set
	 */
	public void setClientListName(String clientListName) {
		this.clientListName = clientListName;
	}

	/**
	 * @return the deliveryListName
	 */
	public String getDeliveryListName() {
		return deliveryListName;
	}

	/**
	 * @param deliveryListName the deliveryListName to set
	 */
	public void setDeliveryListName(String deliveryListName) {
		this.deliveryListName = deliveryListName;
	}

	/**
	 * @return the viewableCount
	 */
	public int getViewableCount() {
		return viewableCount;
	}

	/**
	 * @param viewableCount the viewableCount to set
	 */
	public void setViewableCount(int viewableCount) {
		this.viewableCount = viewableCount;
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
	 * @return the childrenMasterCheckedFlag
	 */
	public String getChildrenMasterCheckedFlag() {
		return childrenMasterCheckedFlag;
	}

	/**
	 * @param childrenMasterCheckedFlag the childrenMasterCheckedFlag to set
	 */
	public void setChildrenMasterCheckedFlag(String childrenMasterCheckedFlag) {
		this.childrenMasterCheckedFlag = childrenMasterCheckedFlag;
	}
	

}

