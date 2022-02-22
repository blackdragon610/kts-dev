/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.ui.mst;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.CurrencyLedgerDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.currencyLedger.CurrencyLedgerService;
import jp.co.kts.service.mst.SupplierService;

/**
 * [概要]仕入先管理actionクラス
 * @author Boncre
 *
 */
public class SupplierAction extends AppBaseAction {


	/**
	 * バリデートチェックタイプ:UPDATE
	 */
	private static final String VALID_TYPE_UPD = "update";

	/**
	 * バリデートチェックタイプ：INSERT
	 */
	private static final String VALID_TYPE_INS = "insert";

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SupplierForm form = (SupplierForm)appForm;

		//仕入先リストを取得する処理
		if ("/initSupplierList".equals(appMapping.getPath())) {
			return SupplierList(appMapping, form, request);

		//仕入先詳細を取得する処理
		} else if ("/detailSupplier".equals(appMapping.getPath())) {
			return detailSupplier(appMapping, form, request);

		//仕入先情報を更新する処理
		} else if ("/updateSupplier".equals(appMapping.getPath())) {
			return updateSupplier(appMapping, form, request);

		//仕入先情報を削除する処理
		} else if ("/deleteSupplier".equals(appMapping.getPath())) {
			return deleteSupplier(appMapping, form, request);

		//仕入先情報登録の初期処理
		} else if ("/initRegistrySupplier".equals(appMapping.getPath())) {
			return initRegistrySupplier(appMapping, form, request);

		//仕入先情報を登録する処理
		} else if ("/registrySupplier".equals(appMapping.getPath())) {
			return registrySupplier(appMapping, form, request);

		//通貨リストをJSON型で取得する処理
		} else if ("/getCurrencyList".equals(appMapping.getPath())) {
			return getCurrencyList(appMapping, form, request, response);

		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}


	/**
	 * [概要] 仕入先リストを取得する処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward SupplierList(AppActionMapping appMapping,
			SupplierForm form, HttpServletRequest request) throws Exception {

		SupplierService supplierService = new SupplierService();

		//メッセージ初期化
		form.setRegistryDto(new RegistryMessageDTO());
		form.setErrorDTO(new ErrorMessageDTO());

		form.setSupplierList(supplierService.getSupplierList());
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 仕入先詳細を取得する処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward detailSupplier(AppActionMapping appMapping,
			SupplierForm form, HttpServletRequest request) throws Exception {

		SupplierService supplierService = new SupplierService();

		//メッセージ初期化
		form.setRegistryDto(new RegistryMessageDTO());
		form.setErrorDTO(new ErrorMessageDTO());

		form.setSupplierDTO(supplierService.getSupplier(form.getSysSupplierId()));
		form.setCurrencyList(supplierService.connectCurrencyList(new CurrencyLedgerService().getCurrencyLedger()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 仕入先情報を更新する処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateSupplier(AppActionMapping appMapping,
			SupplierForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		SupplierService supplierService = new SupplierService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		ErrorMessageDTO errorDTO = new ErrorMessageDTO();
		String validType = VALID_TYPE_UPD;

		ExtendMstSupplierDTO supplierDTO = form.getSupplierDTO();

		begin();

		//メッセージ初期化
		form.getRegistryDto().setMessage(StringUtils.EMPTY);

		//入力チェック処理
		boolean doubleCheck = supplierService.validateSupplierNo(supplierDTO, validType);

		//仕入先番号が重複していないかチェック処理
		if (!doubleCheck) {
			registryMessageDTO.setMessageFlg("1");
			errorDTO.setErrorMessage("仕入先番号が重複しています");
			form.setRegistryDto(registryMessageDTO);
			form.setErrorDTO(errorDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//入力チェック(PoNo.頭文字)
		boolean poNoInitialCheck = supplierService.validatePoNoInitial(supplierDTO, validType);

		//PoNo.頭文字が重複していないかチェック処理
		if (!poNoInitialCheck) {
			registryMessageDTO.setMessageFlg("1");
			errorDTO.setErrorMessage("PoNo.頭文字が重複しています");
			form.setRegistryDto(registryMessageDTO);
			form.setErrorDTO(errorDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		int result = supplierService.updateSupplier(supplierDTO);

		//更新成功判定処理
		if (result != 1){
			rollback();
			registryMessageDTO.setMessageFlg("1");
			registryMessageDTO.setMessage("更新に失敗しました");
			errorDTO.setErrorMessage("対象の仕入先情報がありません。大変申し訳ありませんが、管理者に連絡してください");
			form.setRegistryDto(registryMessageDTO);
			form.setErrorDTO(errorDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		commit();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setMessage("更新しました");
		form.setRegistryDto(registryMessageDTO);
		form.setMessageFlg("1");
		form.setErrorDTO(new ErrorMessageDTO());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]　仕入先情報を削除する処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteSupplier(AppActionMapping appMapping,
			SupplierForm form, HttpServletRequest request) throws Exception {

		begin();

		//インスタンス生成
		SupplierService supplierService = new SupplierService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		ErrorMessageDTO errorDTO = new ErrorMessageDTO();

		//メッセージ初期化
		form.getRegistryDto().setMessage(StringUtils.EMPTY);

		//削除実行
		int result = supplierService.deleteSupplier(form.getSupplierDTO().getSysSupplierId());

		//削除成功判定処理
		if (result != 1){
			rollback();
			registryMessageDTO.setMessageFlg("1");
			registryMessageDTO.setMessage("削除に失敗しました");
			errorDTO.setErrorMessage("対象の仕入先情報がありません。大変申し訳ありませんが、管理者に連絡してください");
			form.setRegistryDto(registryMessageDTO);
			form.setErrorDTO(errorDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		commit();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setMessage("削除しました");
		form.setRegistryDto(registryMessageDTO);
		form.setMessageFlg("1");
		form.setErrorDTO(new ErrorMessageDTO());

//		//削除後、仕入先リストを再取得する。
//		form.setSupplierList(supplierService.getSupplierList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 仕入先情報登録の初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initRegistrySupplier(AppActionMapping appMapping,
			SupplierForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		ExtendMstSupplierDTO supplierDTO = new ExtendMstSupplierDTO();
		SupplierService supplierService = new SupplierService();

		//DTO初期化
		form.setSupplierDTO(supplierDTO);
		form.getRegistryDto().setRegistryMessage(StringUtils.EMPTY);

		//仕入先リスト取得
		form.setSupplierList(supplierService.getSupplierList());
		//通貨リスト取得
		form.setCurrencyList(supplierService.connectCurrencyList(new CurrencyLedgerService().getCurrencyLedger()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 仕入先情報登録処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward registrySupplier(AppActionMapping appMapping,
			SupplierForm form, HttpServletRequest request) throws Exception {

		begin();

		//インスタンス生成
		SupplierService supplierService = new SupplierService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		ErrorMessageDTO errorDTO = new ErrorMessageDTO();
		String validType = VALID_TYPE_INS;

		ExtendMstSupplierDTO supplierDTO = form.getSupplierDTO();

		//メッセージ初期化
		form.getRegistryDto().setMessage(StringUtils.EMPTY);

		//入力チェック(仕入先番号)
		boolean supplierNoCheck = supplierService.validateSupplierNo(supplierDTO, validType);

		//仕入先番号が重複していないかチェック処理
		if (!supplierNoCheck) {
			registryDto.setMessageFlg("1");
			errorDTO.setErrorMessage("仕入先番号が重複しています");
			form.setRegistryDto(registryDto);
			form.setErrorDTO(errorDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//入力チェック(PoNo.頭文字)
		boolean poNoInitialCheck = supplierService.validatePoNoInitial(supplierDTO, validType);

		//PoNo.頭文字が重複していないかチェック処理
		if (!poNoInitialCheck) {
			registryDto.setMessageFlg("1");
			errorDTO.setErrorMessage("PoNo.頭文字が重複しています");
			form.setErrorDTO(errorDTO);
			form.setRegistryDto(registryDto);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//登録実行
		int result = supplierService.registrySupplier(supplierDTO);

		//登録成功確認処理
		if (result != 1) {
			rollback();
			registryDto.setMessageFlg("1");
			registryDto.setMessage("登録に失敗しました");
			form.setRegistryDto(registryDto);
			form.setMessageFlg("0");
			form.setErrorDTO(errorDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		commit();
		registryDto.setMessageFlg("0");
		registryDto.setMessage("登録しました");
		form.setRegistryDto(registryDto);
		form.setMessageFlg("1");
		form.setErrorDTO(new ErrorMessageDTO());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 通貨リストをJSON型で取得する処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward getCurrencyList(AppActionMapping appMapping,
			SupplierForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンス生成
		CurrencyLedgerService currencyService = new CurrencyLedgerService();
		List<CurrencyLedgerDTO> currencyList = currencyService.getCurrencyLedger(form.getCurrencyId());

		//通貨リスト取得成功判定
		if (currencyList.size() == 0) {
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(""));
		} else {
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(currencyList));
		}

		return null;
	}

}