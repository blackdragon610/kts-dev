package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstAccountDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstAccountDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.mst.AccountService;
import jp.co.kts.service.mst.CorporationService;

public class AccountAction extends AppBaseAction {

	//検索結果が1の場合
	private static final int ACCOUNT_LIST_SIZE = 1;
	//検索結果が0の場合
	private static final int ACCOUNT_LIST_SIZE_NON = 0;
	//優先させる更新処理用
	private static final int ACCOUNT_PRIOR = 1;

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AccountForm form = (AccountForm)appForm;


		if ("/initRegistryAccount".equals(appMapping.getPath())) {
			return initRegistryAccount(appMapping, form, request);
		} else if ("/registryAccount".equals(appMapping.getPath())) {
			return registryAccount(appMapping, form, request);
		} else if ("/detailAccount".equals(appMapping.getPath())) {
			return detailAccount(appMapping, form, request);
		} else if ("/updateAccount".equals(appMapping.getPath())) {
			return updateAccount(appMapping, form, request);
		} else if ("/deleteAccount".equals(appMapping.getPath())) {
			return deleteAccount(appMapping, form, request);
		} else if ("/initAccountList".equals(appMapping.getPath())) {
			return initAccountList(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	protected ActionForward initRegistryAccount(AppActionMapping appMapping, AccountForm form,
            HttpServletRequest request) throws Exception {

		MstAccountDTO accountDTO = new MstAccountDTO();
		form.setAccountDTO(accountDTO);

		CorporationService corpService = new CorporationService();
		form.setCorporationList(corpService.getCorporationList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 登録
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward registryAccount(AppActionMapping appMapping, AccountForm form,
            HttpServletRequest request) throws Exception {

		AccountService accService = new AccountService();
		List<ExtendMstAccountDTO> accountList = new ArrayList<>();

		//バリデート
		Result<MstAccountDTO> result = accService.validate(form.getAccountDTO().getAccountNm(), form.getAccountDTO().getSysCorporationId());

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//選択された法人で口座が登録されているか検索
		accountList = accService.getAccountList(form.getAccountDTO().getSysCorporationId());

		accService.registryAccount(form.getAccountDTO(), accountList);
		form.setAlertType("1");

		//登録後の一覧を再取得
		form.setAccountList(accService.getAccountList(0));

		//tokenセット
		saveToken(request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	 protected ActionForward detailAccount(AppActionMapping appMapping, AccountForm form,
	            HttpServletRequest request) throws Exception {

		AccountService accService = new AccountService();
		form.setAccountDTO(accService.getAccount(form.getSysAccountId()));

		CorporationService corpService = new CorporationService();
		form.setCorporationList(corpService.getCorporationList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	 /**
	  * 更新
	  * @param appMapping
	  * @param form
	  * @param request
	  * @return
	  * @throws Exception
	  */
	protected ActionForward updateAccount(AppActionMapping appMapping, AccountForm form,
            HttpServletRequest request) throws Exception {

		//インスタンス生成
		AccountService accService = new AccountService();
		List<ExtendMstAccountDTO> accountList = new ArrayList<>();

		//バリデート
		Result<MstAccountDTO> result = accService.validate(form.getAccountDTO().getAccountNm(), form.getAccountDTO().getSysCorporationId());

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//更新処理
		accService.updateAccount(form.getAccountDTO(), ACCOUNT_PRIOR);

		//他法人で口座が登録されているか検索
		accountList = accService.getAccountList(form.getAccountDTO().getSysCorporationId());

		//複数登録されている場合、変更
		if (accountList.size() != ACCOUNT_LIST_SIZE) {
			accService.updatePrior(accountList, form.getAccountDTO());
		}

		form.setAlertType("2");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 削除
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward deleteAccount(AppActionMapping appMapping, AccountForm form,
            HttpServletRequest request) throws Exception {

		//インスタンス生成
		List<ExtendMstAccountDTO> accountList = new ArrayList<>();

		//削除後検索用に法人IDを非難
		long sysCorporationId = form.getAccountDTO().getSysCorporationId();

		//口座削除
		AccountService accService = new AccountService();
		accService.deleteAccount(form.getAccountDTO().getSysAccountId());

		MstAccountDTO accountDTO = new MstAccountDTO();
		form.setAccountDTO(accountDTO);
		form.setAlertType("3");

		//選択された法人で他に口座が登録されているか検索
		accountList = accService.getAccountList(sysCorporationId);

		int listCount = 0;
		//検索結果が一件以上ある場合
		if (accountList.size() != ACCOUNT_LIST_SIZE_NON) {

			//他口座分ループ
			for (ExtendMstAccountDTO dto: accountList) {

				//優先度が設定されていないものを判定
				if (StringUtils.equals("1", dto.getPriorFlag())) {
					listCount++;
				}
			}

			//優先度がすべて外れてしまっているか確認
			if (listCount == 0) {
				//その場合は口座IDの最初を優先度設定
				accService.updateAccount(accountList.get(0), listCount);
			}
		}
		//削除後の一覧を再取得
		form.setAccountList(accService.getAccountList(0));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 口座一覧初期表示
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initAccountList(AppActionMapping appMapping, AccountForm form,
	            HttpServletRequest request) throws Exception {

		AccountService accService = new AccountService();
		form.setAccountList(accService.getAccountList(0));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


}
