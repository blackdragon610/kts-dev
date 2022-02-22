/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.ui.currency;

/**
 * 通貨台帳のActionクラス
 *一覧表示検索、通貨情報更新、通貨情報削除、通貨新規登録
 * Serviceクラスへ
 *
 * @author n_nozawa
 * 20161108
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.currencyLedger.CurrencyLedgerService;

public class CurrencyLedgerAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//  自動生成されたメソッド・スタブ

		CurrencyLedgerForm form = (CurrencyLedgerForm)appForm;

		if ("/initCurrencyLedgerList".equals(appMapping.getPath())) {
			return CurrencyLedgertList(appMapping, form, request);
		} else if ("/updateCurrencyLedger".equals(appMapping.getPath())) {
			 return updateCurrencyLedger(appMapping, form, request);
		} else if ("/deleteCurrencyLedger".equals(appMapping.getPath())) {
			 return deleteCurrencyLedger(appMapping, form, request);
		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);

	}

	//初期画面表示
	private ActionForward CurrencyLedgertList(AppActionMapping appMapping,
			CurrencyLedgerForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		CurrencyLedgerService service = new CurrencyLedgerService();

		//メッセージを空にするため
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDTO(registryMessageDTO);

		//追加分listをセット
		form.setAddCurrencyLedgerList(service.initAddCurrencyLedgerList());
		//登録分セット
		form.setCurrencyLedgerList(service.getCurrencyLedger());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//登録、更新
	private ActionForward updateCurrencyLedger(AppActionMapping appMapping,
			CurrencyLedgerForm form, HttpServletRequest request) throws Exception {

		CurrencyLedgerService service = new CurrencyLedgerService();

		service.updateCurrencyLedgerList(form.getCurrencyLedgerList());
		service.registryCurrecyLedgerList(form.getAddCurrencyLedgerList());

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
//		registryDTO.setSuccess();
		registryMessageDTO.setMessage("更新しました");
		registryMessageDTO.setMessageFlg("0");
		form.setRegistryDTO(registryMessageDTO);

		//登録後再検索
		//追加分listをセット
		form.setAddCurrencyLedgerList(service.initAddCurrencyLedgerList());
		//登録分セット
		form.setCurrencyLedgerList(service.getCurrencyLedger());

		//トークンセット
		saveToken(request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//削除
	private ActionForward deleteCurrencyLedger(AppActionMapping appMapping,
			CurrencyLedgerForm form, HttpServletRequest request) throws Exception {

		CurrencyLedgerService service = new CurrencyLedgerService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();

		boolean currencyRegist = service.checkCurrencyRegist(form.getDeleteTargetId());

		if (!currencyRegist) {
			registryMessageDTO.setMessage("この通貨は仕入先に登録されています");
			registryMessageDTO.setMessageFlg("1");
			form.setRegistryDTO(registryMessageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		service.deleteCurrencyLedger(form.getDeleteTargetId());

//		registryDTO.setSuccess(true);
		registryMessageDTO.setMessage("削除しました");
		registryMessageDTO.setMessageFlg("0");
		form.setRegistryDTO(registryMessageDTO);

		//削除後再検索
		//追加分listをセット
		form.setAddCurrencyLedgerList(service.initAddCurrencyLedgerList());
		//登録分セット
		form.setCurrencyLedgerList(service.getCurrencyLedger());

		//トークンセット
		saveToken(request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
}
