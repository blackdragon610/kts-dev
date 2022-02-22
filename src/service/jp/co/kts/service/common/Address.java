package jp.co.kts.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Address {

	/** 郵便番号 */
	private String zip;

	/** 住所（都道府県） */
	private String prefectures;

	/** 住所（市区町村） */
	private String municipality;

	/** 住所（市区町村以降） */
	private String address;

	/** 住所（建物名等） */
	private String buildingNm;

	public String getZipDisp() {
		
		String zipDisp = new String();
		
		if (StringUtils.isEmpty(zip)) {
			return zipDisp;
		}
		
		zipDisp += "〒";
		zipDisp += zip;
		
		return zipDisp;
	}
	public String getAddress1() {
		
		List<String> strList = new ArrayList<String>();
		strList.add(this.prefectures);
		strList.add(this.municipality);
		strList.add(this.address);
		
		String address1 = new String();
		for (String str: strList) {
			
			if (StringUtils.isEmpty(str)) {
				continue;
			}			
			address1 += str;
		}
		
		return address1;
	}
	
	public String getAddress2() {

		List<String> strList = new ArrayList<String>();
		strList.add(this.buildingNm);
		String address2 = new String();
		for (String str: strList) {
			
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			
			if (StringUtils.isEmpty(address2)) {
				address2 += "　";
			}
			
			address2 += str;
		}
		return address2;
	}
	
	/**
	 * @param zip セットする zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @param prefectures セットする prefectures
	 */
	public void setPrefectures(String prefectures) {
		this.prefectures = prefectures;
	}

	/**
	 * @param municipality セットする municipality
	 */
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	/**
	 * @param address セットする address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param buildingNm セットする buildingNm
	 */
	public void setBuildingNm(String buildingNm) {
		this.buildingNm = buildingNm;
	}

}
