package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.ui.mst.ForeignOrderItemDTO;

public class ExtendForeignOrderItemDTO extends ForeignOrderItemDTO {

	/** システム入荷予定ID */
	private long sysArrivalScheduleId;

	/** システム倉庫ID */
	private long sysWarehouseId;

	/** 削除対象ID */
	private String deleteCheckFlg;

	/** 入荷数 */
	private int arrivalNum;

	/** 入荷日 */
	private String arrivalDate;

	/** 入荷予定数 */
	private int arrivalScheduleNum;

	/** 入荷予定日 */
	private String arrivalScheduleDate;

	/** 入荷状態 */
	private String arrivalStatus;

	/** 曖昧入荷日 */
	private String vagueArrivalSchedule;

	/** 伝票のみ入荷予定日に登録 */
	private int slipFlag;

	/** 入荷情報一時格納 */
	private int tempArrivalNum;

	/** 発注日 */
	private String itemOrderDate;

	/** 入荷予定日：入荷済み用*/
	private String arrivalScheduleDateSub;

	/** 曖昧入荷予定日：入荷済み用*/
	private String vagueArrivalScheduleSub;

	//----------倉庫情報----------------

	/** システム倉庫在庫ID */
	private long sysWarehouseStockId = 0;

	/** 在庫数 */
	private int stockNum = 0;

	/** ロケーションNO */
	private String locationNo;

	/** 商品選択checkbox用 */
	private String itemSelect;

	/** 総在庫数 */
	private int totalStockNum = 0;

	/** 仮在庫数 */
	private int temporaryStockNum = 0;




	/**
	 * <p>
	 * システム入荷ID予定 を返却します。
	 * </p>
	 * @return sysArrivalScheduleId
	 */
	public long getSysArrivalScheduleId() {
		return sysArrivalScheduleId;
	}

	/**
	 * <p>
	 * システム入荷ID予定 を設定します。
	 * </p>
	 * @param sysArrivalScheduleId
	 */
	public void setSysArrivalScheduleId(long sysArrivalScheduleId) {
		this.sysArrivalScheduleId = sysArrivalScheduleId;
	}

	/**
	 * <p>
	 * システム倉庫ID を返却します。
	 * </p>
	 * @return sysWarehouseId
	 */
	public long getSysWarehouseId() {
		return sysWarehouseId;
	}

	/**
	 * <p>
	 * システム倉庫ID を返却します。
	 * </p>
	 * @param sysWarehouseId
	 */
	public void setSysWarehouseId(long sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	/**
	 * 削除対象IDを返却します。
	 * @return 削除対象ID
	 */
	public String getDeleteCheckFlg() {
	    return deleteCheckFlg;
	}

	/**
	 * 削除対象IDを設定します。
	 * @param deleteCheckFlg 削除対象ID
	 */
	public void setDeleteCheckFlg(String deleteCheckFlg) {
	    this.deleteCheckFlg = deleteCheckFlg;
	}

	/**
	 * <p>
	 * 入荷数 を返却します。
	 * </p>
	 * @return arrivalNum
	 */
	public int getArrivalNum() {
		return this.arrivalNum;
	}

	/**
	 * <p>
	 * 入荷数 を設定します。
	 * </p>
	 * @param arrivalNum
	 */
	public void setArrivalNum(int arrivalNum) {
		this.arrivalNum = arrivalNum;
	}

	/**
	 * <p>
	 * 発注日 を返却します。
	 * </p>
	 * @return itemOrderDate
	 */
	public String getItemOrderDate() {
		return this.itemOrderDate;
	}

	/**
	 * <p>
	 * 発注日 を設定します。
	 * </p>
	 * @param itemOrderDate
	 */
	public void setItemOrderDate(String itemOrderDate) {
		this.itemOrderDate = itemOrderDate;
	}

	/**
	 * 入荷日
	 * @return arrivalDate
	 */
	public String getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * 入荷日
	 * @param arrivalDate セットする arrivalDate
	 */
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * 入荷予定数 を返却します。
	 * @return arrivalScheduleNum
	 */
	public int getArrivalScheduleNum() {
		return arrivalScheduleNum;
	}

	/**
	 * 入荷予定数 を設定します。
	 * @param arrivalScheduleNum セットする arrivalScheduleNum
	 */
	public void setArrivalScheduleNum(int arrivalScheduleNum) {
		this.arrivalScheduleNum = arrivalScheduleNum;
	}

	/**
	 * <p>
	 * 入荷予定日を返却します
	 * </p>
	 */
	public String getArrivalScheduleDate() {
		return arrivalScheduleDate;
	}

	/**
	 * <p>
	 * 入荷予定日を設定します
	 * </p>
	 */
	public void setArrivalScheduleDate(String arrivalScheduleDate) {
		this.arrivalScheduleDate = arrivalScheduleDate;
	}
	/**
	 * @return arrivalStatus
	 */
	public String getArrivalStatus() {
		return arrivalStatus;
	}

	/**
	 * @param arrivalStatus セットする arrivalStatus
	 */
	public void setArrivalStatus(String arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	/**
	 * <p>
	 * システム倉庫在庫ID を返却します。
	 * </p>
	 * @return sysWarehouseStockId
	 */
	public long getSysWarehouseStockId() {
		return this.sysWarehouseStockId;
	}

	/**
	 * <p>
	 * システム倉庫在庫ID を設定します。
	 * </p>
	 * @param sysWarehouseStockId
	 */
	public void setSysWarehouseStockId(long sysWarehouseStockId) {
		this.sysWarehouseStockId = sysWarehouseStockId;
	}
	/**
	 * <p>
	 * 在庫数 を返却します。
	 * </p>
	 * @return stockNum
	 */
	public int getStockNum() {
		return this.stockNum;
	}

	/**
	 * <p>
	 * 在庫数 を設定します。
	 * </p>
	 * @param stockNum
	 */
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

	/**
	 * <p>
	 * ロケーションNO を返却します。
	 * </p>
	 * @return locationNo
	 */
	public String getLocationNo() {
		return this.locationNo;
	}

	/**
	 * <p>
	 * ロケーションNO を設定します。
	 * </p>
	 * @param locationNo
	 */
	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	/**
	 * <p>
	 * 曖昧入荷日を返却します
	 * </p>
	 * @return vagueArrivalSchedule
	 */
	public String getVagueArrivalSchedule() {
		return vagueArrivalSchedule;
	}

	/**
	 * <p>
	 * 曖昧入荷日を設定します
	 * </p>
	 * @param vagueArrivalSchedule
	 */
	public void setVagueArrivalSchedule(String vagueArrivalSchedule) {
		this.vagueArrivalSchedule = vagueArrivalSchedule;
	}

	/**
	 * @return slipFlag
	 */
	public int getSlipFlag() {
		return slipFlag;
	}

	/**
	 * @param slipFlag セットする slipFlag
	 */
	public void setSlipFlag(int slipFlag) {
		this.slipFlag = slipFlag;
	}

	/**
	 * @return tempArrivalNum
	 */
	public int getTempArrivalNum() {
		return tempArrivalNum;
	}

	/**
	 * @param tempArrivalNum セットする tempArrivalNum
	 */
	public void setTempArrivalNum(int tempArrivalNum) {
		this.tempArrivalNum = tempArrivalNum;
	}

	/**
	 * 入荷予定日：入荷済み用を取得します。
	 * @return 入荷予定日：入荷済み用
	 */
	public String getArrivalScheduleDateSub() {
	    return arrivalScheduleDateSub;
	}

	/**
	 * 入荷予定日：入荷済み用を設定します。
	 * @param arrivalScheduleDateSub 入荷予定日：入荷済み用
	 */
	public void setArrivalScheduleDateSub(String arrivalScheduleDateSub) {
	    this.arrivalScheduleDateSub = arrivalScheduleDateSub;
	}

	/**
	 * 曖昧入荷予定日：入荷済み用を取得します。
	 * @return 入荷予定日：曖昧入荷済み用
	 */
	public String getVagueArrivalScheduleSub() {
		return vagueArrivalScheduleSub;
	}

	/**
	 * 曖昧入荷予定日：入荷済み用を設定します。
	 * @param arrivalScheduleDateSub 曖昧入荷予定日：入荷済み用
	 */
	public void setVagueArrivalScheduleSub(String vagueArrivalScheduleSub) {
		this.vagueArrivalScheduleSub = vagueArrivalScheduleSub;
	}

	/**
	 * 商品選択用チェックボックスを取得します
	 * @return itemSelect
	 */
	public String getItemSelect() {
		return itemSelect;
	}

	/**
	 * 商品選択用チェックボックスを設定します。
	 * @param itemSelect セットする itemSelect
	 */
	public void setItemSelect(String itemSelect) {
		this.itemSelect = itemSelect;
	}

	/**
	 * @return totalStockNum
	 */
	public int getTotalStockNum() {
		return totalStockNum;
	}

	/**
	 * @param totalStockNum セットする totalStockNum
	 */
	public void setTotalStockNum(int totalStockNum) {
		this.totalStockNum = totalStockNum;
	}

	/**
	 * @return temporaryStockNum
	 */
	public int getTemporaryStockNum() {
		return temporaryStockNum;
	}

	/**
	 * @param temporaryStockNum セットする temporaryStockNum
	 */
	public void setTemporaryStockNum(int temporaryStockNum) {
		this.temporaryStockNum = temporaryStockNum;
	}
}
