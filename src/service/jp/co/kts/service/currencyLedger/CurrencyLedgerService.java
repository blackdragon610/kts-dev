/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.service.currencyLedger;

/**
 * 通貨台帳のServiceクラス
 *一覧表示検索、通貨新規登録、通貨情報更新、通貨情報削除
 * DAOへ
 *
 * @author n_nozawa
 * 20161108
 */
import java.util.ArrayList;
import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CurrencyLedgerDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.currency.CurrencyLedgerDAO;
import jp.co.kts.ui.web.struts.WebConst;

public class CurrencyLedgerService {

	/**
	 * 通貨台帳初期表示使用
	 * 全件検索取得
	 *
	 * @return
	 * @throws Exception
	 */
	public List<CurrencyLedgerDTO> getCurrencyLedger() throws Exception {

		CurrencyLedgerDAO dao = new CurrencyLedgerDAO();

		return dao.getCurrencyLedgerList();
	}

	/**
	 * 通貨レート情報取得
	 * @param currencyId 通貨ID
	 * @return
	 * @throws Exception
	 */
	public List<CurrencyLedgerDTO> getCurrencyLedger(long currencyId) throws Exception {

		CurrencyLedgerDAO dao = new CurrencyLedgerDAO();

		return dao.getCurrencyLedgerList(currencyId);
	}

	// 追加登録分レコード数作成
	public List<CurrencyLedgerDTO> initAddCurrencyLedgerList() {

		List<CurrencyLedgerDTO> list = new ArrayList<>();

		for (int i = 0; i < WebConst.ADD_CURRENCY_LEDGER_LIST_LENGTH; i++) {

			list.add(new CurrencyLedgerDTO());
		}

		return list;
	}

	// 通貨台帳更新に使用
	public void updateCurrencyLedgerList(List<CurrencyLedgerDTO> list)
			throws Exception {

		// 登録済み通貨情報を全て更新
		for (CurrencyLedgerDTO dto : list) {
			new CurrencyLedgerDAO().updateCurrencyLedger(dto);
		}
	}

	// 通貨台帳削除に使用
	public void deleteCurrencyLedger(long deleteTargetId) throws Exception {

		CurrencyLedgerDAO dao = new CurrencyLedgerDAO();
		dao.deleteCurrencyLedger(deleteTargetId);
	}

	// 通貨台帳登録に使用
	public void registryCurrecyLedgerList(List<CurrencyLedgerDTO> addlist)
			throws Exception {

		for (CurrencyLedgerDTO dto : addlist) {

			if (dto.getCurrencyType() == "") {
				continue;
			}
			dto.setCurrencyId(new SequenceDAO().getMaxCurrencyId() + 1);

			new CurrencyLedgerDAO().registryCurrencyLedger(dto);
		}
	}

	/**
	 * 削除対象の通貨が仕入先に登録されていないかチェック
	 * @param deleteTargetId
	 * @return
	 * @throws DaoException
	 */
	public boolean checkCurrencyRegist(long deleteTargetId) throws DaoException {

		boolean result = true;

		CurrencyLedgerDAO dao = new CurrencyLedgerDAO();

		if (dao.checkCurrencyDelete(deleteTargetId) > 0) {
			result = false;
		}

		return result;
	}
}
