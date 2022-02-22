/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.app.common.entity;

/**
 * 通貨台帳情報を格納します。
 *
 * @author n_nozawa
 * 20161108
 */
public class CurrencyLedgerDTO {


	/** 通貨ID */
	private long CurrencyId;

	/** 通貨記号 */
	private String CurrencyType;

	/** 通貨名 */
	private String CurrencyNm;

	/** レート */
	private float Rate;

	/** 削除フラグ */
	private char DeleteFlag;

	/** 登録日 */
	private String CreateDate;

	/** 登録者ID */
	private int CreateUserId;

	/** 更新日 */
	private String UpdateDate;

	/** 更新者ID */
	private int UpdateDateUserId;

	/**
	 * <p>
	 * 通貨ID を返却します。
	 * </p>
	 * @return getCurrencyId
	 */
	public long getCurrencyId() {
		return CurrencyId;
	}

	/**
	 * <p>
	 * 通貨ID を設定します。
	 * </p>
	 * @param setCurrencyId
	 */
	public void setCurrencyId(long currencyId) {
		CurrencyId = currencyId;
	}

	/**
	 * <p>
	 * 通貨記号 を返却します。
	 * </p>
	 * @return getCurrencyType
	 */
	public String getCurrencyType() {
		return CurrencyType;
	}

	/**
	 * <p>
	 * 通貨記号 を設定します。
	 * </p>
	 * @param setCurrencyType
	 */
	public void setCurrencyType(String currencyType) {
		CurrencyType = currencyType;
	}

	/**
	 * <p>
	 * 通貨名 を返却します。
	 * </p>
	 * @return getCurrencyNm
	 */
	public String getCurrencyNm() {
		return CurrencyNm;
	}

	/**
	 * <p>
	 * 通貨名 を設定します。
	 * </p>
	 * @param setCurrencyNm
	 */
	public void setCurrencyNm(String currencyNm) {
		CurrencyNm = currencyNm;
	}

	/**
	 * <p>
	 * レート を返却します。
	 * </p>
	 * @return getRate
	 */
	public float getRate() {
		return Rate;
	}

	/**
	 * <p>
	 * レート を設定します。
	 * </p>
	 * @param setRate
	 */
	public void setRate(float rate) {
		Rate = rate;
	}

	/**
	 * <p>
	 * 削除フラグ を返却します。
	 * </p>
	 * @return getDeleteFlag
	 */
	public char getDeleteFlag() {
		return DeleteFlag;
	}

	/**
	 * <p>
	 * 削除フラグ を設定します。
	 * </p>
	 * @param setDeleteFlag
	 */
	public void setDeleteFlag(char deleteFlag) {
		DeleteFlag = deleteFlag;
	}

	/**
	 * <p>
	 * 登録日 を返却します。
	 * </p>
	 * @return getCreateDate
	 */
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	 * <p>
	 * 登録日 を設定します。
	 * </p>
	 * @param setCreateDate
	 */
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	/**
	 * <p>
	 * 登録者Id を返却します。
	 * </p>
	 * @return getCreateDateId
	 */
	public int getCreateUserId() {
		return CreateUserId;
	}

	/**
	 * <p>
	 * 登録者Id を設定します。
	 * </p>
	 * @param setCreateDateId
	 */
	public void setCreateUserId(int createUserId) {
		CreateUserId = createUserId;
	}

	/**
	 * <p>
	 * 更新日 を返却します。
	 * </p>
	 * @return getUpdateDate
	 */
	public String getUpdateDate() {
		return UpdateDate;
	}

	/**
	 * <p>
	 * 更新日 を設定します。
	 * </p>
	 * @param setUpdateDate
	 */
	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}

	/**
	 * <p>
	 * 更新者ID を返却します。
	 * </p>
	 * @return getUpdateDateId
	 */
	public int getUpdateDateUserId() {
		return UpdateDateUserId;
	}

	/**
	 * <p>
	 * 通貨ID を設定します。
	 * </p>
	 * @param setUpdateDateId
	 */
	public void setUpdateDateUserId(int updateDateUserId) {
		UpdateDateUserId = updateDateUserId;
	}
}
