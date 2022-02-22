package jp.co.kts.app.search.entity;


/**
 * 売上原価一覧画面検索条件DTOクラス
 * 機能内で使用するデータを保持する。
 *
 * 作成日　：2015/12/21
 * 作成者　：大山智史
 * 更新日　：
 * 更新者　：
 *
 */
public class SaleCostSearchDTO extends SaleSearchDTO {

	/** 原価：メーカー品フラグ */
	private String costMakerItemFlg;

	/** 原価：原価未入力フラグ */
	private String costNoRegistry;

	/** 原価：原価0円フラグ */
	private String costZeroRegistry;

	/** システム売上商品ID */
	long sysSaleItemId;

	/** 原価チェック：未チェックフラグ */
	private String costNoCheckFlg;

	/** 原価チェック：チェック済フラグ */
	private String costCheckedFlg;

	private String sysChannelIdOne;
	
	private String sysChannelIdTwo;
	
	private String sysChannelIdOther;
	

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

	public long getSysSaleItemId() {
		return sysSaleItemId;
	}

	public void setSysSaleItemId(long sysSaleItemId) {
		this.sysSaleItemId = sysSaleItemId;
	}

	public String getSysChannelIdOne() {
		return this.sysChannelIdOne;
	}
	
	public void setSysChannelIdOne(String sysChannelIdOne) {
		this.sysChannelIdOne = sysChannelIdOne;
	}

	public String getSysChannelIdTwo() {
		return this.sysChannelIdTwo;
	}
	
	public void setSysChannelIdTwo(String sysChannelIdTwo) {
		this.sysChannelIdTwo = sysChannelIdTwo;
	}

	public String getSysChannelIdOther() {
		return this.sysChannelIdOther;
	}
	
	public void setSysChannelIdOther(String sysChannelIdOther) {
		this.sysChannelIdOther = sysChannelIdOther;
	}
}
