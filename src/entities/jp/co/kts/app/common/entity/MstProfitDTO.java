/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

public class MstProfitDTO  {

	private long sysProfitId;
	
	private long sysCorporationId;
	
	private long sysChannelId;
	
	private String channelNm;
	
	private String taxFlg;
	
	private float royalty;
	
	private long orgColorRange;
	
	private long redColorRange;

	/**
	 * @return the sysProfitId
	 */
	public long getSysProfitId() {
		return sysProfitId;
	}

	/**
	 * @param sysProfitId the sysProfitId to set
	 */
	public void setSysProfitId(long sysProfitId) {
		this.sysProfitId = sysProfitId;
	}

	/**
	 * @return the sysCoporationId
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 * @param sysCoporationId the sysCoporationId to set
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * @return the sysChannelId
	 */
	public long getSysChannelId() {
		return sysChannelId;
	}

	/**
	 * @param sysChannelId the sysChannelId to set
	 */
	public void setSysChannelId(long sysChannelId) {
		this.sysChannelId = sysChannelId;
	}

	/**
	 * @return the channelNm
	 */
	public String getChannelNm() {
		return channelNm;
	}

	/**
	 * @param channelNm the channelNm to set
	 */
	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}

	/**
	 * @return the taxFlg
	 */
	public String getTaxFlg() {
		return taxFlg;
	}

	/**
	 * @param taxFlg the taxFlg to set
	 */
	public void setTaxFlg(String taxFlg) {
		this.taxFlg = taxFlg;
	}

	/**
	 * @return the royalty
	 */
	public float getRoyalty() {
		return royalty;
	}

	/**
	 * @param royalty the royalty to set
	 */
	public void setRoyalty(float royalty) {
		this.royalty = royalty;
	}

	/**
	 * @return the orgColorRange
	 */
	public long getOrgColorRange() {
		return orgColorRange;
	}

	/**
	 * @param orgColorRange the orgColorRange to set
	 */
	public void setOrgColorRange(long orgColorRange) {
		this.orgColorRange = orgColorRange;
	}

	/**
	 * @return the redColorRange
	 */
	public long getRedColorRange() {
		return redColorRange;
	}

	/**
	 * @param redColorRange the redColorRange to set
	 */
	public void setRedColorRange(long redColorRange) {
		this.redColorRange = redColorRange;
	}
	

}

