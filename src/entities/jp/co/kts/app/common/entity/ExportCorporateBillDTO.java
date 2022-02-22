/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2012 boncre
 */
package jp.co.kts.app.common.entity;

import java.util.ArrayList;
import java.util.List;

import jp.co.kts.app.extendCommon.entity.ExtendCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
/**
 * 請求書出力用情報を格納します。
 *
 * @author admin
 */
public class ExportCorporateBillDTO extends ExtendCorporateBillDTO  {

	private long sysCorporationId;

	private long sysClientId;

	private String exportMonth;

	private String selectedCutoff;

	private int sumLastClaimPrice = 0;

	private int lastReceivableBalance = 0;

	private int sumSalesPrice = 0;

	private int sumLastReceivePrice = 0;

	private int sumTax = 0;

	private int sumPostage = 0;

	private int sumCodCommission = 0;

	private int sumClaimPrice = 0;

	private List<ExtendCorporateSalesSlipDTO> slipList = new ArrayList<>();

	private List<ExtendCorporateSalesItemDTO> itemList = new ArrayList<>();

	private List<CorporateBillItemDTO> itemBillList = new ArrayList<>();

	/**
	 * @return sysCorporationId
	 */
	public long getSysCorporationId() {
		return sysCorporationId;
	}

	/**
	 * @param sysCorporationId セットする sysCorporationId
	 */
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	/**
	 * @return sysClientId
	 */
	public long getSysClientId() {
		return sysClientId;
	}

	/**
	 * @param sysClientId セットする sysClientId
	 */
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}

	/**
	 * @return exportMonth
	 */
	public String getExportMonth() {
		return exportMonth;
	}

	/**
	 * @param exportMonth セットする exportMonth
	 */
	public void setExportMonth(String exportMonth) {
		this.exportMonth = exportMonth;
	}

	/**
	 * @return selectedCutoff
	 */
	public String getSelectedCutoff() {
		return selectedCutoff;
	}

	/**
	 * @param exportMonth セットする selectedCutoff
	 */
	public void setSelectedCutoff(String selectedCutoff) {
		this.selectedCutoff = selectedCutoff;
	}

	/**
	 * @return sumLastClaimPrice
	 */
	public int getSumLastClaimPrice() {
		return sumLastClaimPrice;
	}

	/**
	 * @param sumLastClaimPrice セットする sumLastSalesPrice
	 */
	public void setSumLastClaimPrice(int sumLastClaimPrice) {
		this.sumLastClaimPrice = sumLastClaimPrice;
	}

	/**
	 * @return lastReceivableBalance
	 */
	public int getLastReceivableBalance() {
		return lastReceivableBalance;
	}

	/**
	 * @param lastReceivableBalance セットする lastReceivableBalance
	 */
	public void setLastReceivableBalance(int lastReceivableBalance) {
		this.lastReceivableBalance = lastReceivableBalance;
	}

	/**
	 * @return sumSalesPrice
	 */
	public int getSumSalesPrice() {
		return sumSalesPrice;
	}

	/**
	 * @param sumSalesPrice セットする sumSalesPrice
	 */
	public void setSumSalesPrice(int sumSalesPrice) {
		this.sumSalesPrice = sumSalesPrice;
	}

	/**
	 * @return sumLastReceivePrice
	 */
	public int getSumLastReceivePrice() {
		return sumLastReceivePrice;
	}

	/**
	 * @param sumLastReceivePrice セットする sumLastReceivePrice
	 */
	public void setSumLastReceivePrice(int sumLastReceivePrice) {
		this.sumLastReceivePrice = sumLastReceivePrice;
	}

	/**
	 * @return itemList
	 */
	public List<ExtendCorporateSalesItemDTO> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList セットする itemList
	 */
	public void setItemList(List<ExtendCorporateSalesItemDTO> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @return sumTax
	 */
	public int getSumTax() {
		return sumTax;
	}

	/**
	 * @param sumTax セットする sumTax
	 */
	public void setSumTax(int sumTax) {
		this.sumTax = sumTax;
	}

	/**
	 * @return sumPostage
	 */
	public int getSumPostage() {
		return sumPostage;
	}

	/**
	 * @param sumPostage セットする sumPostage
	 */
	public void setSumPostage(int sumPostage) {
		this.sumPostage = sumPostage;
	}

	/**
	 * @return sumCodCommission
	 */
	public int getSumCodCommission() {
		return sumCodCommission;
	}

	/**
	 * @param sumCodCommission セットする sumCodCommission
	 */
	public void setSumCodCommission(int sumCodCommission) {
		this.sumCodCommission = sumCodCommission;
	}

	/**
	 * @return sumClaimPrice
	 */
	public int getSumClaimPrice() {
		return sumClaimPrice;
	}

	/**
	 * @param sumClaimPrice セットする sumClaimPrice
	 */
	public void setSumClaimPrice(int sumClaimPrice) {
		this.sumClaimPrice = sumClaimPrice;
	}

	/**
	 * @return slipList
	 */
	public List<ExtendCorporateSalesSlipDTO> getSlipList() {
		return slipList;
	}

	/**
	 * @param slipList セットする slipList
	 */
	public void setSlipList(List<ExtendCorporateSalesSlipDTO> slipList) {
		this.slipList = slipList;
	}

	/**
	 * @return slipList
	 */
	public List<CorporateBillItemDTO> getItemBillList() {
		return itemBillList;
	}

	/**
	 * @param slipList セットする slipList
	 */
	public void setItemBillList(List<CorporateBillItemDTO> itemBillList) {
		this.itemBillList = itemBillList;
	}

	/**
	 * @return sumLastReceivePrice
	 */
	public int a() {
		return sumLastReceivePrice;
	}

	/**
	 * @param sumLastReceivePrice セットする sumLastReceivePrice
	 */
	public void a(int sumLastReceivePrice) {
		this.sumLastReceivePrice = sumLastReceivePrice;
	}

}

