package jp.co.kts.app.extendCommon.entity;

import java.util.ArrayList;
import java.util.List;

import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.DomesticOrderSlipDTO;

public class ExtendDomesticOrderSlipDTO extends DomesticOrderSlipDTO {

	/** 国内注文商品情報 */
	private List<DomesticOrderListDTO> domesticOrderItemList = new ArrayList<>();

	/** 対応者 */
	private String personInCharge;

	/** オープン価格フラグ */
	private String openPriceFlg;

	/** PDF出力用注文書No */
	private String purchaseOrderNoPdf;

	/**
	 * 国内注文商品情報
	 * @return domesticOrderItemList
	 */
	public List<DomesticOrderListDTO> getDomesticOrderItemList() {
		return domesticOrderItemList;
	}

	/**
	 * 国内注文商品情報
	 * @param domesticOrderItemList セットする domesticOrderItemList
	 */
	public void setDomesticOrderItemList(List<DomesticOrderListDTO> domesticOrderItemList) {
		this.domesticOrderItemList = domesticOrderItemList;
	}

	/**
	 * @return personInCharge
	 */
	public String getPersonInCharge() {
		return personInCharge;
	}

	/**
	 * @param personInCharge セットする personInCharge
	 */
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	/**
	 * @return openPriceFlg
	 */
	public String getOpenPriceFlg() {
		return openPriceFlg;
	}

	/**
	 * @param openPriceFlg セットする openPriceFlg
	 */
	public void setOpenPriceFlg(String openPriceFlg) {
		this.openPriceFlg = openPriceFlg;
	}

	/**
	 * @return purchaseOrderNoPdf
	 */
	public String getPurchaseOrderNoPdf() {
		return purchaseOrderNoPdf;
	}

	/**
	 * @param purchaseOrderNoPdf セットする purchaseOrderNoPdf
	 */
	public void setPurchaseOrderNoPdf(String purchaseOrderNoPdf) {
		this.purchaseOrderNoPdf = purchaseOrderNoPdf;
	}
}