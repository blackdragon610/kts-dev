/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.ui.currency;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.CurrencyLedgerDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;

/**
 * 通貨台帳情報を格納します。
 *
 * @author n_nozawa
 * 20161108
 */
public class CurrencyLedgerForm extends AppBaseForm {

	/** 通貨ID */
	private long CurrencyId;

	/** 削除対象ID */
	private long deleteTargetId;

	/** List通貨台帳情報を格納 */
	private List<CurrencyLedgerDTO> currencyLedgerList = new ArrayList<>();

	/** 追加用リスト */
	private List<CurrencyLedgerDTO> addCurrencyLedgerList = new ArrayList<>();

	/**  */
	private CurrencyLedgerDTO currencyLedgerDTO = new CurrencyLedgerDTO();

	/** メッセージ用 */
	private RegistryMessageDTO registryDTO = new RegistryMessageDTO();

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		//  自動生成されたメソッド・スタブ

	}

	/**
	 * @return CurrencyId
	 */
	public long getCurrencyId() {
		return CurrencyId;
	}

	/**
	 * 通貨IDを設定します。
	 * @param CurrencyId 削除対象ID
	 */
	public void setCurrencyId(long currencyId) {
		CurrencyId = currencyId;
	}

	/**
	 * 削除対象IDを取得します。
	 * @return 削除対象ID
	 */
	public long getDeleteTargetId() {
	    return deleteTargetId;
	}

	/**
	 * 削除対象IDを設定します。
	 * @param deleteTargetId 削除対象ID
	 */
	public void setDeleteTargetId(long deleteTargetId) {
	    this.deleteTargetId = deleteTargetId;
	}

	/**
	 * @return currencyLedgerList
	 */
	public List<CurrencyLedgerDTO> getCurrencyLedgerList() {
		return currencyLedgerList;
	}

	/**
	 * @param currencyLedgerList セットする currencyLedgerList
	 */
	public void setCurrencyLedgerList(List<CurrencyLedgerDTO> currencyLedgerList) {
		this.currencyLedgerList = currencyLedgerList;
	}

	/**
	 * 追加登録分を取得します。
	 * @return addCurrencyLedgerList
	 */
	public List<CurrencyLedgerDTO> getAddCurrencyLedgerList() {
		return addCurrencyLedgerList;
	}

	/**
	 * @param addCurrencyLedgerList セットする addCurrencyLedgerList
	 */
	public void setAddCurrencyLedgerList(List<CurrencyLedgerDTO> addCurrencyLedgerList) {
		this.addCurrencyLedgerList = addCurrencyLedgerList;
	}

	/**
	 *
	 * @return currencyLedgerDTO
	 */
	public CurrencyLedgerDTO getCurrencyLedgerDTO() {
		return currencyLedgerDTO;
	}

	/**
	 * @param currencyLedgerDTO セットする currencyLedgerDTO
	 */
	public void setCurrencyLedgerDTO(CurrencyLedgerDTO currencyLedgerDTO) {
		this.currencyLedgerDTO = currencyLedgerDTO;
	}

	/**
	 * @return registryDTO
	 */
	public RegistryMessageDTO getRegistryDTO() {
		return registryDTO;
	}

	/**
	 * @param registryDTO セットする registryDTO
	 */
	public void setRegistryDTO(RegistryMessageDTO registryDTO) {
		this.registryDTO = registryDTO;
	}
}