package jp.co.kts.app.search.entity;


/**
 * 業販原価一覧画面検索条件DTOクラス
 * 機能内で使用するデータを保持する。
 *
 * 作成日　：2016/1/4
 * 作成者　：大山智史
 * 更新日　：
 * 更新者　：
 *
 */
public class CorporateSaleCostSearchDTO extends CorporateSaleSearchDTO {


	/** 原価：メーカー品フラグ */
	private String costMakerItemFlg;

	/** 原価：原価未入力フラグ */
	private String costNoRegistry;

	/** 原価：原価0円フラグ */
	private String costZeroRegistry;

	/** システム業販商品ID */
	long sysCorpSaleItemId;

	/** 原価チェック：未チェックフラグ */
	private String costNoCheckFlg;

	/** 原価チェック：チェック済フラグ */
	private String costCheckedFlg;


	public String getCostNoCheckFlg() {
		return costNoCheckFlg;
	}

	public void setCostNoCheckFlg(String costNoCheckFlg) {
		this.costNoCheckFlg = costNoCheckFlg;
	}

	public String getCostCheckedFlg() {
		return costCheckedFlg;
	}

	public void setCostCheckedFlg(String costCheckedFlg) {
		this.costCheckedFlg = costCheckedFlg;
	}

	public long getSysCorpSaleItemId() {
		return sysCorpSaleItemId;
	}

	public void setSysCorpSaleItemId(long sysCorpSaleItemId) {
		this.sysCorpSaleItemId = sysCorpSaleItemId;
	}

	public String getCostMakerItemFlg() {
		return costMakerItemFlg;
	}

	public void setCostMakerItemFlg(String costMakerItemFlg) {
		this.costMakerItemFlg = costMakerItemFlg;
	}

	public String getCostNoRegistry() {
		return costNoRegistry;
	}

	public void setCostNoRegistry(String costNoRegistry) {
		this.costNoRegistry = costNoRegistry;
	}

	public String getCostZeroRegistry() {
		return costZeroRegistry;
	}

	public void setCostZeroRegistry(String costZeroRegistry) {
		this.costZeroRegistry = costZeroRegistry;
	}




}
