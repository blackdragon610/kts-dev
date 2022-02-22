/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.dao.currency;

/**
 * 通貨台帳用のDAO
 *一覧表示検索、通貨新規登録、通貨情報更新、通貨情報削除
 * SQLを取得
 *
 * @author n_nozawa
 * 20161108
 */
import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.CurrencyLedgerDTO;

public class CurrencyLedgerDAO extends BaseDAO  {

	//初期表示用登録済み通貨検索
	public List<CurrencyLedgerDTO> getCurrencyLedgerList() throws DaoException {

		SQLParameters parameters = new SQLParameters();

		return selectList("SEL_CURRENCYLEDGER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(CurrencyLedgerDTO.class));
	}

	/**
	 * [概要]通貨レート情報取得
	 * @param currencyId 通貨ID
	 * @return
	 * @throws DaoException
	 */
	public List<CurrencyLedgerDTO> getCurrencyLedgerList(long currencyId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("currencyId", currencyId);

		return selectList("SEL_CURRENCY_RATE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(CurrencyLedgerDTO.class));
	}

	//更新
	public void updateCurrencyLedger(CurrencyLedgerDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UP_CURRENCYLEDGER", parameters);
	}

	//削除
	public void deleteCurrencyLedger(long currencyId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("currencyId", currencyId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UP_DEL_CURRENCYLEDGER", parameters);
	}

	//通貨新規登録
	public void registryCurrencyLedger(CurrencyLedgerDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_CURRENCYLEDGER", parameters);
	}

	public int checkCurrencyDelete(long deleteTargetId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("currencyId", deleteTargetId);

		return select("SEL_CNT_CURRNCY_ID", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}
}
