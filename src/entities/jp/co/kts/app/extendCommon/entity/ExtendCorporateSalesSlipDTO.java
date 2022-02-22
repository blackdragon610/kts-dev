package jp.co.kts.app.extendCommon.entity;

import java.util.ArrayList;
import java.util.List;

import jp.co.kts.app.common.entity.CorporateReceiveDTO;
import jp.co.kts.app.common.entity.CorporateSalesSlipDTO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * CorporateSalesSlipDTOを継承したDTO
 * @author kaihatsu18
 *
 */
public class ExtendCorporateSalesSlipDTO extends CorporateSalesSlipDTO {

//	/** システム口座ID */
//	private long sysAccountId;

	private String corporationNm;

	private String clientNm;

	private String destinationFullNm;

	private String destinationFullNmKana;

	private String paymentMethodNm;

	private String slipStatusNm;

	private long sysItemId;

	private List<ExtendCorporateSalesItemDTO> pickItemList = new ArrayList<>();

	private List<ExtendCorporateSalesItemDTO> corporateSalesItemList = new ArrayList<>();

	private List<CorporateReceiveDTO> corporateReceiveList = new ArrayList<>();

	private int notItemCount;

	/** システム売上商品ID */
	private long sysCorporateSalesItemId;

	/** 品番 */
	private String itemCode;

	/** 商品名 */
	private String itemNm;

	/** 注文数 */
	private int orderNum;

	/** 単価 */
	private int pieceRate;

	/** 原価 */
	private int cost;

	/**
	 * 商品種別金額
	 */
	private String etcStr;

	/** (表示用)注文者郵便番号 */
	private String orderZipDisp;

	/** (表示用)お届け先郵便番号 */
	private String destinationZipDisp;

	private long grossMargin;

	/** 業販入金 */
	private int corporateReceivePrice;
	private String corporateReceiveDate;

	/** 請求書出力用 */
	private int sumLastSales;
	private int sumSales;

	/** 得意先の締日*/
	private int cutoffDate;

	/**
	 * 販売チャネル
	 */
	public static final int SYS_CHANNEL_ID = WebConst.DB_CORPORATE_SALE_ID;

	public ExtendCorporateSalesSlipDTO() {

	}
	/**
	 * String型をからで初期化するコンストラクター
	 * @param empty
	 */
	public ExtendCorporateSalesSlipDTO(String empty) {

		setOrderNo(empty);

		setOrderDate(empty);

		setOrderRemarks(empty);

		setDepositDate(empty);

		setDestinationNm(empty);

		setDestinationNmKana(empty);

		setDestinationZip(empty);

		setDestinationPrefectures(empty);

		setDestinationMunicipality(empty);

		setDestinationAddress(empty);

		setDestinationBuildingNm(empty);

		setDestinationCompanyNm(empty);

		setDestinationQuarter(empty);

		setDestinationTel(empty);

		setSenderRemarks(empty);

		setGenpyoKbn(empty);

		setYusoShiji(empty);

		setYusoShiji2(empty);

		setInvoiceClassification(empty);

		setSlipNo(empty);

		setDestinationAppointDate(empty);

		setDestinationAppointTime(empty);

		setTransportCorporationSystem(empty);

		setReturnFlg(empty);

		corporationNm = empty;

		destinationFullNm = empty;

		destinationFullNmKana = empty;

	}

//
//	/**
//	 * システム口座IDを取得します
//	 * @return
//	 */
//	public long getSysAccountId() {
//		return sysAccountId;
//	}
//
//	/**
//	 * システム口座IDを設定します
//	 * @param sysAccountId
//	 */
//	public void setSysAccountId(long sysAccountId) {
//		this.sysAccountId = sysAccountId;
//	}
//
//
//
//
	public String getCorporationNm() {
		return corporationNm;
	}

	public void setCorporationNm(String corporationNm) {
		this.corporationNm = corporationNm;
	}

	/**
	 * @return clientNm
	 */
	public String getClientNm() {
		return clientNm;
	}
	/**
	 * @param clientNm セットする clientNm
	 */
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}
	public String getDestinationFullNm() {
		return destinationFullNm;
	}

	public void setDestinationFullNm(String destinationFullNm) {
		this.destinationFullNm = destinationFullNm;
	}

	public String getDestinationFullNmKana() {
		return destinationFullNmKana;
	}

	public void setDestinationFullNmKana(String destinationFullNmKana) {
		this.destinationFullNmKana = destinationFullNmKana;
	}

	/**
	 * @return paymentMethodNm
	 */
	public String getPaymentMethodNm() {
		return paymentMethodNm;
	}
	/**
	 * @param paymentMethodNm セットする paymentMethodNm
	 */
	public void setPaymentMethodNm(String paymentMethodNm) {
		this.paymentMethodNm = paymentMethodNm;
	}
	public long getSysItemId() {
		return sysItemId;
	}

	public void setSysItemId(long sysItemId) {
		this.sysItemId = sysItemId;
	}

	public String getEtcStr() {
		return etcStr;
	}

	public void setEtcStr(String etcStr) {
		this.etcStr = etcStr;
	}
	/**
	 * @return pickItemList
	 */
	public List<ExtendCorporateSalesItemDTO> getPickItemList() {
		return pickItemList;
	}
	/**
	 * @param pickItemList セットする pickItemList
	 */
	public void setPickItemList(List<ExtendCorporateSalesItemDTO> pickItemList) {
		this.pickItemList = pickItemList;
	}
	public int getNotItemCount() {
		return notItemCount;
	}
	public void setNotItemCount(int notItemCount) {
		this.notItemCount = notItemCount;
	}
	public long getSysCorporateSalesItemId() {
		return sysCorporateSalesItemId;
	}
	public void setSysCorporateSalesItemId(long sysCorporateSalesItemId) {
		this.sysCorporateSalesItemId = sysCorporateSalesItemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getPieceRate() {
		return pieceRate;
	}
	public void setPieceRate(int pieceRate) {
		this.pieceRate = pieceRate;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getOrderZipDisp() {
		return orderZipDisp;
	}
	public void setOrderZipDisp(String orderZipDisp) {
		this.orderZipDisp = orderZipDisp;
	}
	public String getDestinationZipDisp() {
		return destinationZipDisp;
	}
	public void setDestinationZipDisp(String destinationZipDisp) {
		this.destinationZipDisp = destinationZipDisp;
	}
	public long getGrossMargin() {
		return grossMargin;
	}
	public void setGrossMargin(long grossMargin) {
		this.grossMargin = grossMargin;
	}
	/**
	 * @return slipStatusNm
	 */
	public String getSlipStatusNm() {
		return slipStatusNm;
	}
	/**
	 * @param slipStatusNm セットする slipStatusNm
	 */
	public void setSlipStatusNm(String slipStatusNm) {
		this.slipStatusNm = slipStatusNm;
	}
	/**
	 * @return corporateSalesItemList
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemList() {
		return corporateSalesItemList;
	}
	/**
	 * @param corporateSalesItemList セットする corporateSalesItemList
	 */
	public void setCorporateSalesItemList(List<ExtendCorporateSalesItemDTO> corporateSalesItemList) {
		this.corporateSalesItemList = corporateSalesItemList;
	}
	/**
	 * @return corporateReceiveList
	 */
	public List<CorporateReceiveDTO> getCorporateReceiveList() {
		return corporateReceiveList;
	}
	/**
	 * @param corporateReceiveList セットする corporateReceiveList
	 */
	public void setCorporateReceiveList(List<CorporateReceiveDTO> corporateReceiveList) {
		this.corporateReceiveList = corporateReceiveList;
	}
	/**
	 * @return corporateReceivePrice
	 */
	public int getCorporateReceivePrice() {
		return corporateReceivePrice;
	}
	/**
	 * @param corporateReceivePrice セットする corporateReceivePrice
	 */
	public void setCorporateReceivePrice(int corporateReceivePrice) {
		this.corporateReceivePrice = corporateReceivePrice;
	}
	/**
	 * @return corporateReceiveDate
	 */
	public String getCorporateReceiveDate() {
		return corporateReceiveDate;
	}
	/**
	 * @param corporateReceiveDate セットする corporateReceiveDate
	 */
	public void setCorporateReceiveDate(String corporateReceiveDate) {
		this.corporateReceiveDate = corporateReceiveDate;
	}
	/**
	 * @return sumLastSales
	 */
	public int getSumLastSales() {
		return sumLastSales;
	}
	/**
	 * @param sumLastSales セットする sumLastSales
	 */
	public void setSumLastSales(int sumLastSales) {
		this.sumLastSales = sumLastSales;
	}
	/**
	 * @return sumSales
	 */
	public int getSumSales() {
		return sumSales;
	}
	/**
	 * @param sumSales セットする sumSales
	 */
	public void setSumSales(int sumSales) {
		this.sumSales = sumSales;
	}
	/**
	 * @return cutoffDate
	 */
	public int getCutoffDate() {
		return cutoffDate;
	}
	/**
	 * @param sumSales セットする cutoffDate
	 */
	public void setCutoffDate(int cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

}
