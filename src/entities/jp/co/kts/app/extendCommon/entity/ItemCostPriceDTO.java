package jp.co.kts.app.extendCommon.entity;

import java.util.ArrayList;

/**
 * [概要]Excelダウンロード（priceInfoListTemplate.xls）用DTO
 * @author Boncre
 *
 */
public class ItemCostPriceDTO {

	/**品番*/
	private String itemCode;

	/** システム商品ID */
	private long sysItemId;

	/** システム商品原価ID */
	private long sysItemCostId;

	/** 仕入価格 */
	private int purchasePrice;

	/** 加算経費 */
	private int addingExpenses;

	/**Kind原価*/
	private int KindCost;

	/** 原価 */
	private int itemCost;

	/** 売価 */
	private int itemPrice;

	/** 原価1 */
	private int itemCost1;

	/** 原価2 */
	private int itemCost2;

	/** 原価3 */
	private int itemCost3;

	/** 原価4 */
	private int itemCost4;

	/** 原価5*/
	private int itemCost5;

	/** 原価6 */
	private int itemCost6;

	/** 原価7 */
	private int itemCost7;

	/** 原価8 */
	private int itemCost8;

	/** 原価10 */
	private int itemCost10;

	/** 原価11 */
	private int itemCost11;

	/** 原価12 */
	private int itemCost12;

	/** 原価13（ウルトラレーシング事業部）*/
	private int itemCost13;
	/** bellwork */

	private int itemCost14;
	
	private int itemCost15;


	/** システム商品売価ID */
	private long sysItemPriceId;

	/**売価1*/
	private int itemPrice1;

	/**売価2*/
	private int itemPrice2;

	/**売価3*/
	private int itemPrice3;

	/**売価4*/
	private int itemPrice4;

	/**売価5*/
	private int itemPrice5;

	/**売価6*/
	private int itemPrice6;

	/**売価7*/
	private int itemPrice7;

	/**売価8*/
	private int itemPrice8;

	/**売価10*/
	private int itemPrice10;

	/**売価11*/
	private int itemPrice11;

	/**売価12*/
	private int itemPrice12;

	/** 売価13（ウルトラレーシング事業部）*/
	private int itemPrice13;

	/** bellwork */

	private int itemPrice14;


	private int itemPrice15;


	/** システム法人Id */
	private long sysCorporationId;

	/** 各法人の原価を格納するList */
	ArrayList<Integer> itemCostList = new ArrayList<Integer>();

	/** 各法人の売価を格納するList */
	ArrayList<Integer> itemPriceList = new ArrayList<Integer>();

	/** 各法人の原価用IDを格納するList */
	ArrayList<String> sysCorporationIdCostList = new ArrayList<String>();

	/** 各法人の売価用IDを格納するList */
	ArrayList<String> sysCorporationIdPriceList = new ArrayList<String>();

	/** 各法人のシステム商品原価IDを格納するList */
	ArrayList<Long> sysItemCostIdList = new ArrayList<Long>();

	/** 各法人のシステム商品売価IDを格納するList */
	ArrayList<Long> sysItemPriceIdList = new ArrayList<Long>();

	/**
	 * 品番を取得します。
	 * @return 品番
	 */
	public String getItemCode() {
	    return itemCode;
	}

	/**
	 * 品番を設定します。
	 * @param itemCode 品番
	 */
	public void setItemCode(String itemCode) {
	    this.itemCode = itemCode;
	}

	/**
	 * システム商品IDを取得します。
	 * @return システム商品ID
	 */
	public long getSysItemId() {
	    return sysItemId;
	}

	/**
	 * システム商品IDを設定します。
	 * @param sysItemId システム商品ID
	 */
	public void setSysItemId(long sysItemId) {
	    this.sysItemId = sysItemId;
	}

	/**
	 * システム商品原価IDを取得します。
	 * @return システム商品原価ID
	 */
	public long getSysItemCostId() {
	    return sysItemCostId;
	}

	/**
	 * システム商品原価IDを設定します。
	 * @param sysItemCostId システム商品原価ID
	 */
	public void setSysItemCostId(long sysItemCostId) {
	    this.sysItemCostId = sysItemCostId;
	}

	/**
	 * 仕入価格を取得します。
	 * @return 仕入価格
	 */
	public int getPurchasePrice() {
	    return purchasePrice;
	}

	/**
	 * 仕入価格を設定します。
	 * @param purchasePrice 仕入価格
	 */
	public void setPurchasePrice(int purchasePrice) {
	    this.purchasePrice = purchasePrice;
	}

	/**
	 * 加算経費を取得します。
	 * @return 加算経費
	 */
	public int getAddingExpenses() {
	    return addingExpenses;
	}

	/**
	 * 加算経費を設定します。
	 * @param addingExpenses 加算経費
	 */
	public void setAddingExpenses(int addingExpenses) {
	    this.addingExpenses = addingExpenses;
	}

	/**
	 * Kind原価を取得します。
	 * @return Kind原価
	 */
	public int getKindCost() {
	    return KindCost;
	}

	/**
	 * Kind原価を設定します。
	 * @param KindCost Kind原価
	 */
	public void setKindCost(int KindCost) {
	    this.KindCost = KindCost;
	}

	/**
	 * 原価を取得します。
	 * @return 原価
	 */
	public int getItemCost() {
	    return itemCost;
	}

	/**
	 * 原価を設定します。
	 * @param itemCost 原価
	 */
	public void setItemCost(int itemCost) {
	    this.itemCost = itemCost;
	}

	/**
	 * 売価を取得します。
	 * @return 売価
	 */
	public int getItemPrice() {
	    return itemPrice;
	}

	/**
	 * 売価を設定します。
	 * @param itemPrice 売価
	 */
	public void setItemPrice(int itemPrice) {
	    this.itemPrice = itemPrice;
	}

	/**
	 * 原価1を取得します。
	 * @return 原価1
	 */
	public int getItemCost1() {
	    return itemCost1;
	}

	/**
	 * 原価1を設定します。
	 * @param itemCost1 原価1
	 */
	public void setItemCost1(int itemCost1) {
	    this.itemCost1 = itemCost1;
	}

	/**
	 * 原価2を取得します。
	 * @return 原価2
	 */
	public int getItemCost2() {
	    return itemCost2;
	}

	/**
	 * 原価2を設定します。
	 * @param itemCost2 原価2
	 */
	public void setItemCost2(int itemCost2) {
	    this.itemCost2 = itemCost2;
	}

	/**
	 * 原価3を取得します。
	 * @return 原価3
	 */
	public int getItemCost3() {
	    return itemCost3;
	}

	/**
	 * 原価3を設定します。
	 * @param itemCost3 原価3
	 */
	public void setItemCost3(int itemCost3) {
	    this.itemCost3 = itemCost3;
	}

	/**
	 * 原価4を取得します。
	 * @return 原価4
	 */
	public int getItemCost4() {
	    return itemCost4;
	}

	/**
	 * 原価4を設定します。
	 * @param itemCost4 原価4
	 */
	public void setItemCost4(int itemCost4) {
	    this.itemCost4 = itemCost4;
	}

	/**
	 * 原価5を取得します。
	 * @return 原価5
	 */
	public int getItemCost5() {
	    return itemCost5;
	}

	/**
	 * 原価5を設定します。
	 * @param itemCost5 原価5
	 */
	public void setItemCost5(int itemCost5) {
	    this.itemCost5 = itemCost5;
	}

	/**
	 * 原価6を取得します。
	 * @return 原価6
	 */
	public int getItemCost6() {
	    return itemCost6;
	}

	/**
	 * 原価6を設定します。
	 * @param itemCost6 原価6
	 */
	public void setItemCost6(int itemCost6) {
	    this.itemCost6 = itemCost6;
	}

	/**
	 * 原価7を取得します。
	 * @return 原価7
	 */
	public int getItemCost7() {
	    return itemCost7;
	}

	/**
	 * 原価7を設定します。
	 * @param itemCost7 原価7
	 */
	public void setItemCost7(int itemCost7) {
	    this.itemCost7 = itemCost7;
	}

	/**
	 * 原価8を取得します。
	 * @return 原価8
	 */
	public int getItemCost8() {
	    return itemCost8;
	}

	/**
	 * 原価8を設定します。
	 * @param itemCost8 原価8
	 */
	public void setItemCost8(int itemCost8) {
	    this.itemCost8 = itemCost8;
	}

	/**
	 * 原価10を取得します。
	 * @return 原価10
	 */
	public int getItemCost10() {
	    return itemCost10;
	}

	/**
	 * 原価10を設定します。
	 * @param itemCost10 原価10
	 */
	public void setItemCost10(int itemCost10) {
	    this.itemCost10 = itemCost10;
	}

	/**
	 * 原価11を取得します。
	 * @return 原価11
	 */
	public int getItemCost11() {
	    return itemCost11;
	}

	/**
	 * 原価11を設定します。
	 * @param itemCost11 原価11
	 */
	public void setItemCost11(int itemCost11) {
	    this.itemCost11 = itemCost11;
	}

	/**
	 * 原価12を取得します。
	 * @return 原価12
	 */
	public int getItemCost12() {
	    return itemCost12;
	}

	/**
	 * 原価12を設定します。
	 * @param itemCost12 原価12
	 */
	public void setItemCost12(int itemCost12) {
	    this.itemCost12 = itemCost12;
	}

	/**
	 * 原価13（ウルトラレーシング事業部）を取得する。
	 * @return int ウルトラレーシング事業部原価
	 */
	public int getItemCost13() {
		return itemCost13;
	}

	/**
	 * 原価13（ウルトラレーシング事業部）を設定します。
	 * @param int 原価13
	 */
	public void setItemCost13(int itemCost13) {
	    this.itemCost13 = itemCost13;
	}

	
	/**
	 * 原価14（bellwork）を取得する。
	 * @return int bellwork事業部原価
	 */
	public int getItemCost14() {
		return itemCost14;
	}

	/**
	 * 原価14（bellwork）を設定します。
	 * @param int 原価14
	 */
	public void setItemCost14(int itemCost14) {
	    this.itemCost14 = itemCost14;
	}

	
	/**
	 * 原価15（）を取得する。
	 * @return 事業部原価
	 */
	public int getItemCost15() {
		return itemCost15;
	}

	/**
	 * 原価15（）を設定します。
	 * @param int 原価15
	 */
	public void setItemCost15(int itemCost15) {
	    this.itemCost15 = itemCost15;
	}

	
	
	/**
	 * システム商品売価IDを取得します。
	 * @return システム商品売価ID
	 */
	public long getSysItemPriceId() {
	    return sysItemPriceId;
	}

	/**
	 * システム商品売価IDを設定します。
	 * @param sysItemPriceId システム商品売価ID
	 */
	public void setSysItemPriceId(long sysItemPriceId) {
	    this.sysItemPriceId = sysItemPriceId;
	}

	/**
	 * 売価1を取得します。
	 * @return 売価1
	 */
	public int getItemPrice1() {
	    return itemPrice1;
	}

	/**
	 * 売価1を設定します。
	 * @param itemPrice1 売価1
	 */
	public void setItemPrice1(int itemPrice1) {
	    this.itemPrice1 = itemPrice1;
	}

	/**
	 * 売価2を取得します。
	 * @return 売価2
	 */
	public int getItemPrice2() {
	    return itemPrice2;
	}

	/**
	 * 売価2を設定します。
	 * @param itemPrice2 売価2
	 */
	public void setItemPrice2(int itemPrice2) {
	    this.itemPrice2 = itemPrice2;
	}

	/**
	 * 売価3を取得します。
	 * @return 売価3
	 */
	public int getItemPrice3() {
	    return itemPrice3;
	}

	/**
	 * 売価3を設定します。
	 * @param itemPrice3 売価3
	 */
	public void setItemPrice3(int itemPrice3) {
	    this.itemPrice3 = itemPrice3;
	}

	/**
	 * 売価4を取得します。
	 * @return 売価4
	 */
	public int getItemPrice4() {
	    return itemPrice4;
	}

	/**
	 * 売価4を設定します。
	 * @param itemPrice4 売価4
	 */
	public void setItemPrice4(int itemPrice4) {
	    this.itemPrice4 = itemPrice4;
	}

	/**
	 * 売価5を取得します。
	 * @return 売価5
	 */
	public int getItemPrice5() {
	    return itemPrice5;
	}

	/**
	 * 売価5を設定します。
	 * @param itemPrice5 売価5
	 */
	public void setItemPrice5(int itemPrice5) {
	    this.itemPrice5 = itemPrice5;
	}

	/**
	 * 売価6を取得します。
	 * @return 売価6
	 */
	public int getItemPrice6() {
	    return itemPrice6;
	}

	/**
	 * 売価6を設定します。
	 * @param itemPrice6 売価6
	 */
	public void setItemPrice6(int itemPrice6) {
	    this.itemPrice6 = itemPrice6;
	}

	/**
	 * 売価7を取得します。
	 * @return 売価7
	 */
	public int getItemPrice7() {
	    return itemPrice7;
	}

	/**
	 * 売価7を設定します。
	 * @param itemPrice7 売価7
	 */
	public void setItemPrice7(int itemPrice7) {
	    this.itemPrice7 = itemPrice7;
	}

	/**
	 * 売価8を取得します。
	 * @return 売価8
	 */
	public int getItemPrice8() {
	    return itemPrice8;
	}

	/**
	 * 売価8を設定します。
	 * @param itemPrice8 売価8
	 */
	public void setItemPrice8(int itemPrice8) {
	    this.itemPrice8 = itemPrice8;
	}

	/**
	 * 売価10を取得します。
	 * @return 売価10
	 */
	public int getItemPrice10() {
	    return itemPrice10;
	}

	/**
	 * 売価10を設定します。
	 * @param itemPrice10 売価10
	 */
	public void setItemPrice10(int itemPrice10) {
	    this.itemPrice10 = itemPrice10;
	}

	/**
	 * 売価11を取得します。
	 * @return 売価11
	 */
	public int getItemPrice11() {
	    return itemPrice11;
	}

	/**
	 * 売価11を設定します。
	 * @param itemPrice11 売価11
	 */
	public void setItemPrice11(int itemPrice11) {
	    this.itemPrice11 = itemPrice11;
	}

	/**
	 * 売価12を取得します。
	 * @return 売価12
	 */
	public int getItemPrice12() {
	    return itemPrice12;
	}

	/**
	 * 売価12を設定します。
	 * @param itemPrice12 売価12
	 */
	public void setItemPrice12(int itemPrice12) {
	    this.itemPrice12 = itemPrice12;
	}

	/**
	 * 売価13（ウルトラレーシング事業部）を取得する。
	 * @return int ウルトラレーシング事業部売価
	 */
	public int getItemPrice13() {
		return itemPrice13;
	}


	/**
	 * 売価13（ウルトラレーシング事業部）を設定する。
	 * @param int 売価13
	 */
	public void setItemPrice13(int itemPrice13) {
		this.itemPrice13 = itemPrice13;
	}

	/**
	 * 売価14（bellwork）を取得する。
	 * @return int ウルトラレーシング事業部売価
	 */
	public int getItemPrice14() {
		return itemPrice14;
	}


	/**
	 * 売価14（bellwork）を設定する。
	 * @param int 売価13
	 */
	public void setItemPrice14(int itemPrice14) {
		this.itemPrice14 = itemPrice14;
	}

	public int getItemPrice15() {
		return itemPrice15;
	}
	public void setItemPrice15(int itemPrice15) {
		this.itemPrice14 = itemPrice15;
	}


	
	
	/**
	 * システム法人Idを取得します。
	 * @return システム法人Id
	 */
	public long getSysCorporationId() {
	    return sysCorporationId;
	}

	/**
	 * システム法人Idを設定します。
	 * @param sysCorporationId システム法人Id
	 */
	public void setSysCorporationId(long sysCorporationId) {
	    this.sysCorporationId = sysCorporationId;
	}

	/**
	 * 各法人の原価を格納するListを取得します。
	 * @return 各法人の原価を格納するList
	 */
	public ArrayList<Integer> getItemCostList() {
	    return itemCostList;
	}

	/**
	 * 各法人の原価を格納するListを設定します。
	 * @param itemCostList 各法人の原価を格納するList
	 */
	public void setItemCostList(ArrayList<Integer> itemCostList) {
	    this.itemCostList = itemCostList;
	}

	/**
	 * 各法人の売価を格納するListを取得します。
	 * @return 各法人の売価を格納するList
	 */
	public ArrayList<Integer> getItemPriceList() {
	    return itemPriceList;
	}

	/**
	 * 各法人の売価を格納するListを設定します。
	 * @param itemPriceList 各法人の売価を格納するList
	 */
	public void setItemPriceList(ArrayList<Integer> itemPriceList) {
	    this.itemPriceList = itemPriceList;
	}

	/**
	 * 各法人の原価用IDを格納するListを取得します。
	 * @return 各法人の原価用IDを格納するList
	 */
	public ArrayList<String> getSysCorporationIdCostList() {
	    return sysCorporationIdCostList;
	}

	/**
	 * 各法人の原価用IDを格納するListを設定します。
	 * @param sysCorporationIdCostList 各法人の原価用IDを格納するList
	 */
	public void setSysCorporationIdCostList(ArrayList<String> sysCorporationIdCostList) {
	    this.sysCorporationIdCostList = sysCorporationIdCostList;
	}

	/**
	 * 各法人の売価用IDを格納するListを取得します。
	 * @return 各法人の売価用IDを格納するList
	 */
	public ArrayList<String> getSysCorporationIdPriceList() {
	    return sysCorporationIdPriceList;
	}

	/**
	 * 各法人の売価用IDを格納するListを設定します。
	 * @param sysCorporationIdPriceList 各法人の売価用IDを格納するList
	 */
	public void setSysCorporationIdPriceList(ArrayList<String> sysCorporationIdPriceList) {
	    this.sysCorporationIdPriceList = sysCorporationIdPriceList;
	}

	/**
	 * 各法人のシステム商品原価IDを格納するListを取得します。
	 * @return 各法人のシステム商品原価IDを格納するList
	 */
	public ArrayList<Long> getSysItemCostIdList() {
	    return sysItemCostIdList;
	}

	/**
	 * 各法人のシステム商品原価IDを格納するListを設定します。
	 * @param sysItemCostIdList 各法人のシステム商品原価IDを格納するList
	 */
	public void setSysItemCostIdList(ArrayList<Long> sysItemCostIdList) {
	    this.sysItemCostIdList = sysItemCostIdList;
	}

	/**
	 * 各法人のシステム商品売価IDを格納するListを取得します。
	 * @return 各法人のシステム商品売価IDを格納するList
	 */
	public ArrayList<Long> getSysItemPriceIdList() {
	    return sysItemPriceIdList;
	}

	/**
	 * 各法人のシステム商品売価IDを格納するListを設定します。
	 * @param sysItemPriceIdList 各法人のシステム商品売価IDを格納するList
	 */
	public void setSysItemPriceIdList(ArrayList<Long> sysItemPriceIdList) {
	    this.sysItemPriceIdList = sysItemPriceIdList;
	}

}

