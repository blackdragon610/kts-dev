package jp.co.kts.ui.sale;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.CorporateBillItemDTO;
import jp.co.kts.app.common.entity.CorporateReceiveDTO;
import jp.co.kts.app.common.entity.ExportCorporateBillDTO;
import jp.co.kts.app.common.entity.MstDeliveryDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendPaymentManagementDTO;
import jp.co.kts.app.output.entity.CorporateSaleListTotalDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.ErrorObject;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.dao.common.TransactionDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.fileExport.ExportCorporateBillService;
import jp.co.kts.service.fileExport.ExportCorporateEstimateService;
import jp.co.kts.service.fileExport.ExportCorporateOrderAcceptanceService;
import jp.co.kts.service.fileExport.ExportCorporatePickListService;
import jp.co.kts.service.fileExport.ExportCorporatePickListServiceNew;
import jp.co.kts.service.fileExport.ExportCorporateSaleListService;
import jp.co.kts.service.fileExport.ExportCorporateSaleSummalyService;
import jp.co.kts.service.fileExport.ExportCsvService;
import jp.co.kts.service.fileExport.ExportExcelCorporateSalesService;
import jp.co.kts.service.fileExport.ExportExcelCorporateSalesSummalyService;
import jp.co.kts.service.fileExport.ExportExcelYubinService;
import jp.co.kts.service.fileExport.ExportPickListService;
import jp.co.kts.service.fileExport.ExportYubinService;
import jp.co.kts.service.mst.AccountService;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.DeliveryService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.service.sale.CorporatePaymentManagementService;
import jp.co.kts.service.sale.CorporateReceiveService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * ［概要］業販管理のActionクラス
 * ［詳細］業販一覧・業販詳細画面において、検索、登録、更新、削除、出力を行うクラス
 * @author Boncre
 *
 */
public class CorporateSaleAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CorporateSaleForm form = (CorporateSaleForm) appForm;
		
		//業販新規登録画面表示初期処理
		if ("/initRegistryCorporateSales".equals(appMapping.getPath())) {
			return initRegistryCorporateSales(appMapping, form, request);
		//からの新規登録画面表示初期処理
		} else if ("/initRegistryCorporateSalesButton".equals(appMapping.getPath())) {
			return initRegistryCorporateSalesButton(appMapping, form, request);
		//業販伝票新規登録処理
		} else if ("/registryCorporateSales".equals(appMapping.getPath())) {
			return registryCorporateSales(appMapping, form, request);
		//業販詳細画面から遷移後の伝票新規登録処理
		} else if ("/registryCorporateSalesButton".equals(appMapping.getPath())) {
			return registryCorporateSalesButton(appMapping, form, request);
		//業販詳細画面表示初期処理
		} else if ("/detailCorporateSale".equals(appMapping.getPath())) {
			return detailCorporateSale(appMapping, form, request);
		//更新処理
		} else if ("/updateDetailCorporateSale".equals(appMapping.getPath())) {
			return updateDetailCorporateSale(appMapping, form, request);
		//業販詳細画面から遷移後の伝票更新処理
		} else if ("/updateDetailCorporateSaleButton".equals(appMapping.getPath())) {
			return updateDetailCorporateSaleButton(appMapping, form, request);
		} else if ("/initCorporateSaleList".equals(appMapping.getPath())) {
			return initCorporateSaleList(appMapping, form, request);
		} else if ("/initReturnCorporateSales".equals(appMapping.getPath())) {
			return initReturnCorporateSales(appMapping, form, request, response);
		} else if ("/returnCorporateSales".equals(appMapping.getPath())) {
			return returnCorporateSales(appMapping, form, request, response);
		} else if ("/deleteCorporateSales".equals(appMapping.getPath())) {
			return deleteCorporateSales(appMapping, form, request, response);
		} else if ("/searchCorporateSaleList".equals(appMapping.getPath())) {
			return searchCorporateSaleList(appMapping, form, request);
		} else if ("/initCopyCorporateSalesSlip".equals(appMapping.getPath())) {
			return initCopyCorporateSalesSlip(appMapping, form, request, response);
		
		
		/*******************************************************************/
		/***********************出庫処理画面処理PATH************************/
		/*******************************************************************/
		//出庫処理画面遷移
		} else if ("/initCorporateLeaveStock".equals(appMapping.getPath())) {
			return initCorporateLeaveStock(appMapping, form, request);
		//出庫処理
		} else if ("/corporateLeaveStock".equals(appMapping.getPath())) {
			return corporateLeaveStock(appMapping, form, request);
		} else if ("/corporateSaleListPageNo".equals(appMapping.getPath())) {
			return corporateSaleListPageNo(appMapping, form, request);
		
		
		//ピッキングリスト・納品書情報作成処理
		} else if ("/exportCorporatePickList".equals(appMapping.getPath())) {
			return exportCorporatePickList(appMapping, form, request, response);
		//ピッキングリスト・納品書ファイル出力処理
		} else if ("/corporatePickListPrintOutFile".equals(appMapping.getPath())) {
			return corporatePickListPrintOutFile(response);
		//トータルピッキングリスト情報作成処理
		} else if ("/exportCorporateTotalPickList".equals(appMapping.getPath())) {
			return exportCorporateTotalPickList(appMapping, form, request, response);
		//トータルピッキングリストファイル出力処理
		} else if ("/corporateTotalPickListPrintOutFile".equals(appMapping.getPath())) {
			return corporateTotalPickListPrintOutFile(response);
		//業販一覧検索結果ダウンロード処理
		} else if ("/corporateSaleListDownLoad".equals(appMapping.getPath())) {
			return corporateSaleListDownLoad(appMapping, form, request, response);
		//e飛伝データ出力処理
		} else if ("/ehidenCsvDownLoad".equals(appMapping.getPath())) {
			return ehidenCsvDownLoad(appMapping, form, request, response);
		//B2データ出力処理
		} else if ("/b2CsvDownLoad".equals(appMapping.getPath())) {
			return b2CsvDownLoad(appMapping, form, request, response);
		//日本郵便データ出力処理
		} else if ("/yubinExcelDownLoad".equals(appMapping.getPath())) {
			return yubinExcelDownLoad(appMapping, form, request, response);
		}
		// 西濃運輸CSVダウンロード
		else if ("/seinoCsvDownLoad".equals(appMapping.getPath())) {
            return seinoCsvDownLoad(appMapping, form, request, response);
        } else if ("/lumpUpdateCorporateSales".equals(appMapping.getPath())) {
			return lumpUpdateCorporateSales(appMapping, form, request, response);
		} else if ("/lumpUpdateCorporateReceivePrice".equals(appMapping.getPath())) {
			return lumpUpdateCorporateReceivePrice(appMapping, form, request, response);
		} else if ("/exportCorporateEstimate".equals(appMapping.getPath())) {
			return exportCorporateEstimate(appMapping, form, request, response);
		} else if ("/exportCorporateEstimateList".equals(appMapping.getPath())) {
			return exportCorporateEstimateList(appMapping, form, request, response);
		//検索結果一覧見積書ファイル出力処理
		} else if ("/corporateEstimatePrintOutFile".equals(appMapping.getPath())) {
			return corporateEstimatePrintOutFile(response);
		} else if ("/exportCorporateOrderAcceptance".equals(appMapping.getPath())) {
			return exportCorporateOrderAcceptance(appMapping, form, request, response);
		} else if ("/exportCorporateOrderAcceptanceList".equals(appMapping.getPath())) {
			return exportCorporateOrderAcceptanceList(appMapping, form, request, response);
		} else if ("/corporateOrderAcceptancePrintOutFile".equals(appMapping.getPath())) {
			return corporateOrderAcceptancePrintOutFile(response);
		} else if ("/corporateBillList".equals(appMapping.getPath())) {
			return corporateBillList(appMapping, form, request);
		//業販請求書出力画面の初期表示処理
		} else if("/initExportCorporateBill".equals(appMapping.getPath())) {
			return initExportCorporateBill(appMapping, form, request);
		//業販請求書出力画面の新規作成処理
		} else if("/createExportCorporateBill".equals(appMapping.getPath())) {
			return createExportCorporateBill(appMapping, form, request);
		//業販請求書出力画面の更新処理
		} else if ("/updateExportCorporateBill".equals(appMapping.getPath())) {
			return updateExportCorporateBill(appMapping, form, request);
		//業販請求書出力画面の削除処理
		} else if("/deleteExportCorporateBill".equals(appMapping.getPath())) {
			return deleteExportCorporateBill(appMapping, form, request);
		//業販請求書出力画面の一覧ページ遷移処理
		} else if ("/corporateBillListPageNo".equals(appMapping.getPath())) {
			return corporateBillListPageNo(appMapping, form, request);
		//業販請求書出力画面の出力処理
		} else if ("/exportCorporateBillList".equals(appMapping.getPath())) {
			return exportCorporateBillList(appMapping, form, request, response);
		//業販請求書出力画面の出力処理の表示部分
		} else if ("/corporateBillPrintOutFile".equals(appMapping.getPath())) {
			return corporateBillPrintOutFile(response);
		} else if ("/initCorporateSaleSummary".equals(appMapping.getPath())) {
			return initCorporateSaleSummary(appMapping, form, request);
		} else if ("/corporateSaleSummaryDownLoad".equals(appMapping.getPath())) {
			return corporateSaleSummaryDownLoad(appMapping, form, request, response);
		} else if ("/searchExportCorporateBill".equals(appMapping.getPath())) {
			return searchExportCorporateBill(appMapping, form, request, response);
		} else if ("/batchExportCorporateBill".equals(appMapping.getPath())) {
			return batchExportCorporateBill(appMapping, form, request, response);
		
		
		/*******************************************************************/
		/***********************業販入金管理処理PATH************************/
		/*******************************************************************/
		//初期表示処理
		} else if ("/initCorporatePaymentManagement".equals(appMapping.getPath())) {
			return initCorporatePaymentManagement(appMapping, form, request);
		//ページング処理
		} else if ("/corporatePaymentManagementPageNo".equals(appMapping.getPath())) {
			return corporatePaymentManagementPageNo(appMapping, form, request);
		//入金検索処理：業者、締日リンク押下時
		} else if ("/paymentManagementList".equals(appMapping.getPath())) {
			return paymentManagementList(appMapping, form, request);
		//入金検索処理：通常検索
		} else if ("/searchPaymentManagement".equals(appMapping.getPath())) {
			return searchPaymentManagement(appMapping, form, request);
		//入金更新処理
		} else if ("/updatePaymentManagement".equals(appMapping.getPath())) {
			return updatePaymentManagement(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * 新規登録画面初期表示処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initRegistryCorporateSales(AppActionMapping appMapping, CorporateSaleForm form,
	            HttpServletRequest request) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

		// 業販伝票情報初期化
		form.setCorporateSalesSlipDTO(corporateSaleDisplayService.initCorporateSalesSlipDTO(request));
		// 業販商品情報初期化
		form.setAddCorporateSalesItemList(corporateSaleDisplayService.initAddCorporateSalesItemList());
		form.setCorporateSalesItemList(new ArrayList<ExtendCorporateSalesItemDTO>());

		CorporationService corpService = new CorporationService();
		form.setCorporationDTO(corpService.getCorporation(form.getSysCorporationId()));

		form.setClientDTO(new ExtendMstClientDTO());

		// 法人の口座情報
		AccountService accService = new AccountService();
		// 法人に紐付く口座情報を取得するためコメントアウト
		// form.setAccountList(accService.getAccountList(form.getCorporateSalesSlipDTO().getSysCorporationId()));
		form.setAccountList(accService.getAccountList(form.getCorporationDTO().getSysCorporationId()));

		// 伝票の入金情報リスト初期化
		List<CorporateReceiveDTO> receive = new ArrayList<>();
		form.setCorporateReceiveList(receive);

		MstUserDTO userDTO = new MstUserDTO();
		form.setMstUserDTO(userDTO);
		// UserService service = new UserService();
		// form.setUserList(service.getUserList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販詳細画面：からの新規登録画面表示初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initRegistryCorporateSalesButton(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		form.setCorporateSalesSlipDTO(corporateSaleDisplayService.initCorporateSalesSlipDTO(request));

		form.setAddCorporateSalesItemList(corporateSaleDisplayService.initAddCorporateSalesItemList());
		form.setCorporateSalesItemList(new ArrayList<ExtendCorporateSalesItemDTO>());

		// CorporationService corpService = new CorporationService();
		// form.setCorporationDTO(corpService.getCorporation(form.getSysCorporationId()));

		form.setClientDTO(new ExtendMstClientDTO());

		// 法人の口座情報
		AccountService accService = new AccountService();
		// 法人に紐付く口座情報を取得するためコメントアウト
		// form.setAccountList(accService.getAccountList(form.getCorporateSalesSlipDTO().getSysCorporationId()));
		form.setAccountList(accService.getAccountList(form.getCorporationDTO().getSysCorporationId()));

		// 伝票の入金情報リスト初期化
		List<CorporateReceiveDTO> receive = new ArrayList<>();
		form.setCorporateReceiveList(receive);

		MstUserDTO userDTO = new MstUserDTO();
		form.setMstUserDTO(userDTO);
		// UserService service = new UserService();
		// form.setUserList(service.getUserList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 新規登録処理
	 * 「複製ボタン」を押下して開いた業販売上登録から「登録ボタン」押下時に動作するメソッド
	 * 「新規登録ボタン」を押下して開いた業販売上登録から「登録ボタン」を押下の処理はregistryCorporateSalesButton()メソッドが動作する
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward registryCorporateSales(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {
		// インスタンス生成
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

		// 納入先新規登録
		if (form.getNewDeliveryFlg()) {
			DeliveryService deliService = new DeliveryService();
			MstDeliveryDTO dto = new MstDeliveryDTO();
			dto.setTel(form.getCorporateSalesSlipDTO().getDestinationTel());
			// 納入先重複チェック
			Result<MstDeliveryDTO> result = deliService.validate(dto, "insert");

			// 納入先重複チェックで引っかかったらエラーを表示
			if (!result.isSuccess()) {
				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(result.getErrorMessages());
				saveErrorMessages(request, messages);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}
			// 納入先情報登録実行
			corporateSaleDisplayService.insertDelivery(form.getCorporateSalesSlipDTO());
		}

		// 業販伝票登録
		corporateSaleDisplayService.newRegistryCorporateSalesSlip(form.getCorporateSalesSlipDTO());

		// 業販商品登録
		corporateSaleDisplayService.registryCorporateSaleItemList(form.getAddCorporateSalesItemList(),
					form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId(), form.getCorporateSalesSlipDTO());

		// 入金管理に作成した伝票情報をINSERTする処理 boncre_m_suda
		CorporatePaymentManagementService paymentmanageService = new CorporatePaymentManagementService();
		paymentmanageService.registerPaymentInformation(form.getCorporateSalesSlipDTO());

		// 登録完了後業販詳細画面に返却する値を設定
		form.setSysCorporateSalesSlipId(form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId());
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		form.setCorporateSaleSearchDTO(corporateSaleDisplayService.initCorporateSaleSearchDTO());

		// トークンをセーブ
		saveToken(request);

		// 業販詳細画面表示処理へのリダイレクト処理
		return detailCorporateSale(appMapping, form, request);
	}

	/**
	 * 業販詳細画面表示処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward detailCorporateSale(AppActionMapping appMapping, CorporateSaleForm form,
	            HttpServletRequest request) throws Exception {

		// インスタンス生成
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		//業販売上伝票
		form.setCorporateSalesSlipDTO(corporateSaleDisplayService.getCorporateSalesSlip(form.getSysCorporateSalesSlipId()));
		//業販伝票情報がなければエラーメッセージを表示
		if (form.getCorporateSalesSlipDTO() == null) {
			// 業販伝票情報初期化
			form.setCorporateSalesSlipDTO(corporateSaleDisplayService.initCorporateSalesSlipDTO(request));
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		// 業販伝票情報が存在している場合ユーザー情報を設定
		if (form.getCorporateSalesSlipDTO() != null) {
			UserService userService = new UserService();
			form.setMstUserDTO(userService.getUserName(form.getCorporateSalesSlipDTO().getUpdateUserId()));
		}

		// 法人情報（業販では固定
		CorporationService corpService = new CorporationService();
		form.setCorporationDTO(corpService.getCorporation(form.getCorporateSalesSlipDTO().getSysCorporationId()));

		// 得意先情報
		ClientService clientService = new ClientService();
		form.setClientDTO(clientService.getDispClient(form.getCorporateSalesSlipDTO().getSysClientId()));

		// 得意先の納入先一覧
		DeliveryService deliveryService = new DeliveryService();
		form.setDeliveryList(deliveryService.getDeliveryList(form.getClientDTO().getSysClientId()));

		// 法人の口座情報
		AccountService accService = new AccountService();
		form.setAccountList(accService.getAccountList(form.getCorporationDTO().getSysCorporationId()));

		//売上商品
		form.setCorporateSalesItemList(corporateSaleDisplayService.getCorporateSalesItemList(form.getSysCorporateSalesSlipId(), form.getCorporateSalesSlipDTO().getSysCorporationId()));
		form.setAddCorporateSalesItemList(corporateSaleDisplayService.initAddCorporateSalesItemList());

		// 入金情報
		CorporateReceiveService corporateReceiveService = new CorporateReceiveService();
		form.setCorporateReceiveList(corporateReceiveService
				.getCorporateReceiveList(form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId(), 0, ""));
		form.getCorporateSalesSlipDTO().setReceivePrice(corporateReceiveService
				.getCorporateReceiveTotal(form.getSysCorporateSalesSlipId(), form.getClientDTO().getSysClientId(), ""));

		//伝票情報を取得し売上商品情報も取得したのちに粗利を計算します
		form.getCorporateSalesSlipDTO().setGrossMargin(
				corporateSaleDisplayService.getGrossMargin(form.getCorporateSalesSlipDTO(), form.getCorporateSalesItemList(), form.getCorporateSaleSearchDTO()));

		// form.setReturnButtonFlg("1");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/*
	 * 業販伝票の新規登録処理
	 * 業務販売詳細画面から「新規登録」ボタンを押下して開く業販売上登録画面より「登録」ボタンを押下した際に走るメソッド
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return detailCorporateSaleButton(appMapping, form, request);
	 * @throws Exception
	 * @author Boncre山村_registerPaymentInformation()を記述
	 */

	protected ActionForward registryCorporateSalesButton(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

		// 納入先新規登録
		if (form.getNewDeliveryFlg()) {
			DeliveryService deliService = new DeliveryService();
			MstDeliveryDTO dto = new MstDeliveryDTO();
			dto.setTel(form.getCorporateSalesSlipDTO().getDestinationTel());
			// 納入先重複チェック
			Result<MstDeliveryDTO> result = deliService.validate(dto, "insert");

			if (!result.isSuccess()) {
				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(result.getErrorMessages());
				saveErrorMessages(request, messages);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			corporateSaleDisplayService.insertDelivery(form.getCorporateSalesSlipDTO());
		}

		// 売上伝票
		corporateSaleDisplayService.newRegistryCorporateSalesSlip(form.getCorporateSalesSlipDTO());

		// 売上商品
		corporateSaleDisplayService.registryCorporateSaleItemList(form.getAddCorporateSalesItemList(),
				form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId(), form.getCorporateSalesSlipDTO());

		// 入金管理に作成した伝票情報をINSERTする処理(入金額、請求額など)
		CorporatePaymentManagementService paymentmanageService = new CorporatePaymentManagementService();
		paymentmanageService.registerPaymentInformation(form.getCorporateSalesSlipDTO());

		form.setSysCorporateSalesSlipId(form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId());

		form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		form.setCorporateSaleSearchDTO(corporateSaleDisplayService.initCorporateSaleSearchDTO());

		saveToken(request);

		return detailCorporateSaleButton(appMapping, form, request);
		// return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward detailCorporateSaleButton(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request) throws Exception {


		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		// 売上伝票
		form.setCorporateSalesSlipDTO(
				corporateSaleDisplayService.getCorporateSalesSlip(form.getSysCorporateSalesSlipId()));
		if (form.getCorporateSalesSlipDTO() == null) {

			form.setCorporateSalesSlipDTO(corporateSaleDisplayService.initCorporateSalesSlipDTO(request));
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		if (form.getCorporateSalesSlipDTO() != null) {
			UserService userService = new UserService();
			form.setMstUserDTO(userService.getUserName(form.getCorporateSalesSlipDTO().getUpdateUserId()));
		}

		// 法人情報（業販では固定
		// CorporationService corpService = new CorporationService();
		// form.setCorporationDTO(corpService.getCorporation(form.getCorporateSalesSlipDTO().getSysCorporationId()));

		// 得意先情報
		ClientService clientService = new ClientService();
		form.setClientDTO(clientService.getDispClient(form.getCorporateSalesSlipDTO().getSysClientId()));

		// 得意先の納入先一覧
		DeliveryService deliveryService = new DeliveryService();
		form.setDeliveryList(deliveryService.getDeliveryList(form.getClientDTO().getSysClientId()));

		// 法人の口座情報
		AccountService accService = new AccountService();
		form.setAccountList(accService.getAccountList(form.getCorporationDTO().getSysCorporationId()));

		//売上商品
		form.setCorporateSalesItemList(corporateSaleDisplayService.getCorporateSalesItemList(form.getSysCorporateSalesSlipId(), form.getCorporateSalesSlipDTO().getSysCorporationId()));
		form.setAddCorporateSalesItemList(corporateSaleDisplayService.initAddCorporateSalesItemList());

		// 入金情報
		CorporateReceiveService corporateReceiveService = new CorporateReceiveService();
		form.setCorporateReceiveList(corporateReceiveService.getCorporateReceiveList(form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId(), 0, ""));
		form.getCorporateSalesSlipDTO().setReceivePrice(corporateReceiveService.getCorporateReceiveTotal(form.getSysCorporateSalesSlipId(), form.getClientDTO().getSysClientId(), ""));

		//伝票情報を取得し売上商品情報も取得したのちに粗利を計算します
		form.getCorporateSalesSlipDTO().setGrossMargin(
				corporateSaleDisplayService.getGrossMargin(form.getCorporateSalesSlipDTO(), form.getCorporateSalesItemList(), form.getCorporateSaleSearchDTO()));

		// form.setReturnButtonFlg("1");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 更新
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateDetailCorporateSale(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

		// 納入先新規登録
		if (form.getNewDeliveryFlg()) {
			DeliveryService deliService = new DeliveryService();
			MstDeliveryDTO dto = new MstDeliveryDTO();
			dto.setTel(form.getCorporateSalesSlipDTO().getDestinationTel());

			// 納入先重複チェック
			Result<MstDeliveryDTO> result = deliService.validate(dto, "insert");

			if (!result.isSuccess()) {
				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(result.getErrorMessages());
				saveErrorMessages(request, messages);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			corporateSaleDisplayService.insertDelivery(form.getCorporateSalesSlipDTO());
		}

		// 入金情報削除
		CorporateReceiveService receiveService = new CorporateReceiveService();
		for (CorporateReceiveDTO dto : form.getCorporateReceiveList()) {
			if (StringUtils.equals(dto.getDeleteFlag(), "1")) {
				receiveService.deleteCorporateReceive(dto.getSysCorporateReceiveId());
			}
		}

		// 売上伝票
		corporateSaleDisplayService.updateCorporateSalesSlip(form.getCorporateSalesSlipDTO());

		// 売上商品
		corporateSaleDisplayService.updateCorporateSalesItemList(form.getCorporateSalesItemList());
		corporateSaleDisplayService.registryCorporateSaleItemList(form.getAddCorporateSalesItemList(), form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId(), form.getCorporateSalesSlipDTO());

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		saveToken(request);

		return detailCorporateSale(appMapping, form, request);
		// return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販詳細画面での「修正」ボタン押下処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateDetailCorporateSaleButton(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

		// 納入先新規登録
		if (form.getNewDeliveryFlg()) {
			DeliveryService deliService = new DeliveryService();
			MstDeliveryDTO dto = new MstDeliveryDTO();
			dto.setTel(form.getCorporateSalesSlipDTO().getDestinationTel());

			// 納入先重複チェック
			Result<MstDeliveryDTO> result = deliService.validate(dto, "insert");

			if (!result.isSuccess()) {
				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(result.getErrorMessages());
				saveErrorMessages(request, messages);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			corporateSaleDisplayService.insertDelivery(form.getCorporateSalesSlipDTO());
		}

		// 入金情報削除
		CorporateReceiveService receiveService = new CorporateReceiveService();
		for (CorporateReceiveDTO dto : form.getCorporateReceiveList()) {
			if (StringUtils.equals(dto.getDeleteFlag(), "1")) {
				receiveService.deleteCorporateReceive(dto.getSysCorporateReceiveId());
			}
		}

		// 売上伝票
		corporateSaleDisplayService.updateCorporateSalesSlip(form.getCorporateSalesSlipDTO());

		// 売上商品
		corporateSaleDisplayService.updateCorporateSalesItemList(form.getCorporateSalesItemList());
		corporateSaleDisplayService.registryCorporateSaleItemList(form.getAddCorporateSalesItemList(), form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId(), form.getCorporateSalesSlipDTO());

		// 更新時、紐付く請求書用の入金管理データの方も更新する処理
		CorporatePaymentManagementService payManageService = new CorporatePaymentManagementService();
		List<ExtendPaymentManagementDTO> payManageList = new ArrayList<>();
		CorporateSaleSearchDTO searchDTO = new CorporateSaleSearchDTO();
		// 修正された業販伝票の口座、得意先に紐付く入金管理が無いとき新規登録する必要があるので登録
		payManageService.registerPaymentInformation(form.getCorporateSalesSlipDTO());
		/*
		 * ※業販伝票を更新した時、金額の変更があった場合入金管理画面の「編集を有効にする」ボタンを押さないと請求額が再計算されない。
		 * ただし、以下の処理を加えると全件検索する必要があるため処理が遅くなるため一旦コメントアウト。
		 * 請求額の再計算は「編集を有効にする」ボタンを押してもらうことで行ってもらう。
		 */
		// //入金管理レコード全件検索
		// payManageList =
		// payManageService.getSysPaymentManageIdList(searchDTO);
		// //金額の変更があった時、請求額の更新がされないため入金管理更新
		// payManageService.updatePaymentManagement(payManageList);

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

//		saveToken(request);

		return detailCorporateSaleButton(appMapping, form, request);
		// return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販詳細から返品ボタン押下処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward initReturnCorporateSales(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 20140322 伊東敦史 認識不足で作り変えます一応コメントアウトで
		// SaleDisplayService saleDisplayService = new SaleDisplayService();
		// form.setSalesSlipDTO(SaleDisplayService.initReturnSalesSlip(form.getSalesSlipDTO().getSysSalesSlipId()));
		// form.setSalesItemList(SaleDisplayService.initReturnSalesItem(form.getSalesSlipDTO()));

		// インスタンス生成
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		//商品の返品チェックボタンにチェックされているものをListに格納
		form.setCorporateSalesItemList(corporateSaleDisplayService.initReturnCorporateSalesItem(form.getCorporateSalesItemList()));
		//返品伝票の設定
		form.setCorporateSalesSlipDTO(corporateSaleDisplayService.initReturnCorporateSalesSlip(form.getCorporateSalesSlipDTO(), form.getCorporateSalesItemList()));

		//伝票の入金情報リスト初期化
		List<CorporateReceiveDTO> receive = new ArrayList<>();
		form.setCorporateReceiveList(receive);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 返品伝票登録
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward returnCorporateSales(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 20140322 伊東敦史 認識不足で作り変えます一応コメントアウトで
		// SaleDisplayService saleDisplayService = new SaleDisplayService();
		// SaleDisplayService.registryReturnSalesSlip(form.getSalesSlipDTO());
		// SaleDisplayService.registryReturnSalesItem(form.getSalesItemList(),
		// form.getSalesSlipDTO().getSysSalesSlipId());

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		corporateSaleDisplayService.registryReturnCorporateSalesSlip(form.getCorporateSalesSlipDTO());
		corporateSaleDisplayService.registryReturnCorporateSalesItem(form.getCorporateSalesItemList(), form.getCorporateSalesSlipDTO());

		form.setSysCorporateSalesSlipId(form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId());

		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		form.setReturnButtonFlg("1");

		saveToken(request);

		return detailCorporateSale(appMapping, form, request);
	}

	/**
	 * 業販詳細画面：業販伝票削除処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteCorporateSales(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		corporateSaleDisplayService.deleteCorporateSalesSlip(form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId());

		corporateSaleDisplayService.deleteCorporateSalesItem(form.getCorporateSalesItemList());

		form.setReturnButtonFlg("3");
		form.setAlertType(WebConst.ALERT_TYPE_DELETE);
		form.setSysCorporationId(form.getCorporateSalesSlipDTO().getSysCorporationId());

		saveToken(request);

		return initRegistryCorporateSales(appMapping, form, request);

		// return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販一覧画面初期表示
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initCorporateSaleList(AppActionMapping appMapping, CorporateSaleForm form,
	           HttpServletRequest request) throws Exception {

		form.setCorporateSalesSlipList(new ArrayList<ExtendCorporateSalesSlipDTO>());

		CorporationService corporationService = new CorporationService();
		form.setCorporationList(corporationService.getCorporationList());

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		form.setCorporateSaleSearchDTO(corporateSaleDisplayService.initCorporateSaleSearchDTO());
		form.setPickoutputFlg("0");

		//テスト用--------
		//corporateSaleDisplayService.getCorporateSalesSlipList(sysSalesSlipIdList, pageIdx, dto);
		//---------------

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);

	}

	/**
	 * 業販一覧画面検索
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 */
	private ActionForward searchCorporateSaleList(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request) throws DaoException {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		CorporateSaleSearchDTO searchDTO = new CorporateSaleSearchDTO();

		// 検索プリセット判定 ※当日出庫検索の時"3"
		switch (form.getCorporateSaleSearchDTO().getSearchPreset()) {
		case 0:
			searchDTO = form.getCorporateSaleSearchDTO();
			break;
		case 3:
			searchDTO.setSysCorporationId(form.getSysCorporationId());
			searchDTO.setSlipStatus("2");
			searchDTO.setScheduledLeavingDateFrom(StringUtil.getToday());
			searchDTO.setScheduledLeavingDateTo(StringUtil.getToday());
			searchDTO.setSumDispFlg(WebConst.SUM_DISP_FLG1);
			break;
		}
		corporateSaleDisplayService.setFlags(searchDTO);
		form.setSysCorporateSalesSlipIdList(corporateSaleDisplayService.getSysCorporateSalesSlipIdList(searchDTO));
		corporateSaleDisplayService.setFlags(searchDTO);
		form.setCorporateSaleSearchDTO(searchDTO);

		// 検索件数が０件の場合失敗
		if (form.getSysCorporateSalesSlipIdList() == null || form.getSysCorporateSalesSlipIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する検索結果はありません。");
			form.setErrorDTO(errorMessageDTO);

			form.setSysCorporateSalesSlipIdListSize(0);
			form.setCorporateSalesSlipList(new ArrayList<ExtendCorporateSalesSlipDTO>());

			form.setCorporateSaleListTotalDTO(new CorporateSaleListTotalDTO());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 画面上に表示する計算処理
		form.setCorporateSaleListTotalDTO(corporateSaleDisplayService.getCorporateSaleListTotalDTO(
				form.getSysCorporateSalesSlipIdList()
				,form.getCorporateSaleSearchDTO().getGrossProfitCalc()
				,form.getCorporateSaleSearchDTO().getSumDispFlg()));

		form.setSysCorporateSalesSlipIdListSize(form.getSysCorporateSalesSlipIdList().size());

		form.setPageIdx(0);
		form.setCorporateSalesSlipList(corporateSaleDisplayService.getCorporateSalesSlipList(form.getSysCorporateSalesSlipIdList(), form.getPageIdx(), form.getCorporateSaleSearchDTO()));
		form.setCorporateSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getCorporateSaleSearchDTO().getListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販詳細画面：業販伝票コピー画面表示初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward initCopyCorporateSalesSlip(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		form.setAddCorporateSalesItemList(corporateSaleDisplayService.initCopyCorporateSlipItem(form.getCorporateSalesItemList()));
		form.setCorporateSalesItemList(new ArrayList<ExtendCorporateSalesItemDTO>());
		form.setCorporateSalesSlipDTO(corporateSaleDisplayService.initCopyCorporateSalesSlip(form.getCorporateSalesSlipDTO(), form.getAddCorporateSalesItemList()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 出庫処理画面初期処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initCorporateLeaveStock(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		// 検索結果が無ければ終了
		if (form.getCorporateSalesSlipList() == null || form.getCorporateSalesSlipList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// インスタンス生成
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		// 出庫画面表示商品を設定
		form.setLeaveStockList(corporateSaleDisplayService.getLeaveStockList(form.getCorporateSalesSlipList()));
		// 出庫処理が複数ある場合、注文日を見ている
		form.setLeaveStockList(corporateSaleDisplayService.sortLeaveStockList(form.getLeaveStockList()));

		/** 出庫画面表示商品があるかの判定 */
		/** 返却する画面の決定 */
		if (form.getLeaveStockList() == null || form.getLeaveStockList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("本日出庫予定の商品はありません。");
			form.setErrorDTO(errorMessageDTO);

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 出庫処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward corporateLeaveStock(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request) throws Exception {
		//インスタンス生成
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		ErrorObject<List<ExtendCorporateSalesItemDTO>> checkedResult = new ErrorObject<>();
		// brembo商品を格納するためのリスト
		List<ExtendCorporateSalesItemDTO> bremboItemList = new ArrayList<>();

		// 出庫の妥当性チェック
		checkedResult = corporateSaleDisplayService.checkLeaveStock(form.getLeaveStockList());
		// エラーメッセージ格納
		form.getErrorDTO().setErrorMessageList(checkedResult.getErrorMessageList());
		// 在庫数出庫処理
		corporateSaleDisplayService.leaveStock(checkedResult.getResultObject());
		// 出庫フラグ更新と部分出庫時の処理
		corporateSaleDisplayService.updateLeaveStatus(form.getCorporateSalesSlipList());

		/**** TODO 入金管理情報更新処理 一旦中断 ***************************************************************/
		//出庫された(売り上げた)商品から伝票を検索、さらにそこから入金管理TBLを検索し請求額を加算して更新する
		//※請求額は商品が売り上げてからでないとわからないが、今回の入金管理は業販伝票を作成したタイミングで作るので、コメントアウトしている。
//		CorporatePaymentManagementService paymentManagementService = new CorporatePaymentManagementService();
//		paymentManagementService.updatePaymentManagementInformation(checkedResult.getResultObject());
		/**** 入金管理情報更新処理 一旦中断 ***************************************************************/


		/**** Brembo業販伝票自動生成処理 ***************************************************************/
		//出庫した商品からBremboの商品をリスト化する
		bremboItemList = corporateSaleDisplayService.checkTargetExistsForBremboItem(checkedResult.getResultObject());

		//Bremboの商品が存在するときBremboの業販伝票を作成する処理
		if (!(bremboItemList == null || bremboItemList.isEmpty())) {
			corporateSaleDisplayService.createBremboCorpSalesSlip(bremboItemList);
		}
		/**** Brembo業販伝票自動生成処理 ***************************************************************/

		// アラート設定
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		// 出庫処理画面初期処理
		initCorporateLeaveStock(appMapping, form, request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販一覧画面：ページング処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 */
	private ActionForward corporateSaleListPageNo(AppActionMapping appMapping, CorporateSaleForm form, HttpServletRequest request) throws DaoException {

		if (form.getSysCorporateSalesSlipIdList() == null || form.getSysCorporateSalesSlipIdList().size() <= 0) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setCorporateSalesSlipList(new CorporateSaleDisplayService().getCorporateSalesSlipList(form.getSysCorporateSalesSlipIdList(), form.getPageIdx(), form.getCorporateSaleSearchDTO()));
		form.setCorporateSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getCorporateSaleSearchDTO().getListPageMax()));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ［概要］業販一覧画面からピッキングリスト・納品書の出力を行うメソッド
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	protected ActionForward exportCorporatePickList(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Modified by Wahaha
		System.out.println("exportCorporatePickList: START -----------");
		
		// 作成対象がない場合エラー返却
		if (form.getCorporateSalesSlipList() == null || form.getCorporateSalesSlipList().size() <= 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		System.out.println("exportCorporatePickList: getCorporateSalesSlipList() DONE-----------");
		try{
			// Modified by Wahaha
			ExportCorporatePickListServiceNew exportPickListService = new ExportCorporatePickListServiceNew();
			exportPickListService.pickList(request, response,form.getCorporateSalesSlipList(), form.getCorporateSaleSearchDTO(), form.getPdfPattern());

		}catch (Exception e) {

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		System.out.println("exportCorporatePickList: ExportCorporatePickListService.pickList() DONE -----------");
		
		if (form.getPdfPattern().equals("0")) {
			CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
			corporateSaleDisplayService.updatePickFlg(form.getCorporateSalesSlipList(), form.getCorporateSaleSearchDTO());
		}

		System.out.println("exportCorporatePickList: CorporateSaleDisplayService.updatePickFlg() DONE -----------");		
		return null;
	}

	/**
	 * 業販一覧画面：トータルピッキングリスト情報作成処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportCorporateTotalPickList(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (form.getCorporateSalesSlipList() == null || form.getCorporateSalesSlipList().size() <= 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{
			ExportCorporatePickListService exportPickListService = new ExportCorporatePickListService();

			exportPickListService.totalPickList(response,form.getCorporateSalesSlipList());

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		return null;
	}

	/**
	 * 業販一覧画面：ピッキングリスト・納品書ファイル出力処理
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ActionForward corporatePickListPrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "pickList.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "ピッキング＆納品書リスト" + fileNmTimeFormat.format(date) + ".pdf";

		ExportPickListService exportPickListService = new ExportPickListService();

		try {
			exportPickListService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return null;
	}

	/**
	 * 業販一覧画面：トータルピッキングリストファイル出力処理
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ActionForward corporateTotalPickListPrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "totalPickList.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "トータルピッキングリスト" + fileNmTimeFormat.format(date) + ".pdf";

		ExportPickListService exportPickListService = new ExportPickListService();

		try {
			exportPickListService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return null;
	}

	/**
	 * 業販一覧画面：業販一覧検索結果ダウンロード処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward corporateSaleListDownLoad(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.CORP_SALE_LIST_TEMPLATE_PATH);

		// ファイルを読み込みます。
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ワークブックを読み込みます。
		HSSFWorkbook workBook = new HSSFWorkbook(filein);

		// エクセルファイルを編集します
		ExportExcelCorporateSalesService exportSaleList = new ExportCorporateSaleListService();

		CorporateSaleDisplayService saleDisplayService = new CorporateSaleDisplayService();
		saleDisplayService.setFlags(form.getCorporateSaleSearchDTO());
		workBook = exportSaleList.getFileExportCorporateSales(form.getCorporateSaleSearchDTO(), workBook);
		saleDisplayService.setFlags(form.getCorporateSaleSearchDTO());

		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "業販売上表_" + date + ".xls";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// エクセルファイル出力
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);
		ServletOutputStream fileOutHssf = response.getOutputStream();
		workBook.write(fileOutHssf);
		fileOutHssf.close();

		return null;
	}

	/**
	 * 業販一覧画面：e飛伝データ出力処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward ehidenCsvDownLoad(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ExportCsvService service = new ExportCsvService();
		service.ehidenCsvDownLoad(response, form.getCorporateSalesSlipList());

		return null;
	}

	/**
	 * 業販一覧画面：B2データ出力処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward b2CsvDownLoad(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ExportCsvService service = new ExportCsvService();
		service.b2CsvDownLoad(response, form.getCorporateSalesSlipList());

		return null;
	}

	/**
	 * 業販一覧画面：日本郵便データ出力処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward yubinExcelDownLoad(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.YUBIN_TEMPLATE_PATH);

		// ファイルを読み込みます。
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ワークブックを読み込みます。
		HSSFWorkbook workBook = new HSSFWorkbook(filein);

		// エクセルファイルを編集します
		ExportExcelYubinService exportYubinService = new ExportYubinService();

		workBook = exportYubinService.getFileExportYubin(form.getCorporateSalesSlipList(), workBook);

		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMddHHmmss");
		String fname = "日本郵便送り状_" + date + ".xls";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// エクセルファイル出力
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);
		ServletOutputStream fileOutHssf = response.getOutputStream();
		workBook.write(fileOutHssf);
		fileOutHssf.close();

		return null;
	}

	/**
	 * <p>
	 * 西濃運輸CSVダウンロードを行います。
	 * </p>
	 * @param appMapping マッピング
	 * @param form フォーム
	 * @param request リクエスト
	 * @param response レスポンス
	 * @return ActionForward アクションフォワード
	 * @throws Exception 例外
	 */
	private ActionForward seinoCsvDownLoad(AppActionMapping appMapping,
            CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExportCsvService service = new ExportCsvService();
        service.seinoCsvDownLoad(response, form.getCorporateSalesSlipList());

        return null;
    }

	private ActionForward lumpUpdateCorporateSales(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		corporateSaleDisplayService.lumpUpdateCorporateSalesList(form.getCorporateSalesSlipList());

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		return corporateSaleListPageNo(appMapping, form, request);

		//return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販一覧画面：一括入金処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws DaoException
	 */
	private ActionForward lumpUpdateCorporateReceivePrice(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException {

		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		corporateSaleDisplayService.updateCorporateReceivePrice(form.getCorporateSalesSlipList());

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		return corporateSaleListPageNo(appMapping, form, request);

		//return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * 業販詳細画面：見積書出力処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportCorporateEstimate(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId() == 0 || form.getCorporateSalesSlipDTO().getSysClientId() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{
			ExportCorporateEstimateService exportEstimateService = new ExportCorporateEstimateService();

			exportEstimateService.estimate(response,form.getCorporateSalesSlipDTO(), request.getParameter("tax"));

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
//		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
//		corporateSaleDisplayService.updatePickFlg(form.getCorporateSalesSlipList());
		form.getCorporateSalesSlipDTO().setEstimateDate(StringUtil.getToday());
		return null;
	}

	/**
	 * 検索結果一覧から見積書を作成する
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportCorporateEstimateList(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (form.getCorporateSalesSlipList() == null || form.getCorporateSalesSlipList().size() <= 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		// 見積書に出す伝票がページごとではなく検索結果すべてになったので追加
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		List<ExtendCorporateSalesSlipDTO> saleAllSlipList = new ArrayList<>();
		saleAllSlipList = corporateSaleDisplayService.getCorporateSalesAllSlipList(form.getSysCorporateSalesSlipIdList());

		try{
			ExportCorporateEstimateService exportEstimateService = new ExportCorporateEstimateService();

			// exportEstimateService.estimateList(response,form.getCorporateSalesSlipList());
			// 見積書を作成する対象を検索結果すべてに変更
			exportEstimateService.estimateList(response, saleAllSlipList, request.getParameter("tax"));

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		// CorporateSaleDisplayService corporateSaleDisplayService = new
		// CorporateSaleDisplayService();
		// corporateSaleDisplayService.updatePickFlg(form.getCorporateSalesSlipList());
		form.getCorporateSalesSlipDTO().setEstimateDate(StringUtil.getToday());
		return null;
	}

	/**
	 * 業販一覧画面：検索結果一覧見積書ファイル出力処理
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ActionForward corporateEstimatePrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "estimate.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "御見積書" + fileNmTimeFormat.format(date) + ".pdf";

		ExportCorporateEstimateService exportEstimateService = new ExportCorporateEstimateService();

		try {
			exportEstimateService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return null;
	}

	/**
	 * 業販詳細画面：注文請書データファイル出力処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportCorporateOrderAcceptance(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (form.getCorporateSalesSlipDTO().getSysCorporateSalesSlipId() == 0 || form.getCorporateSalesSlipDTO().getSysClientId() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{
			ExportCorporateOrderAcceptanceService exportOrderAcceptanceService = new ExportCorporateOrderAcceptanceService();

			exportOrderAcceptanceService.orderAcceptance(response,form.getCorporateSalesSlipDTO(), request.getParameter("tax"));

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		form.getCorporateSalesSlipDTO().setOrderDate(StringUtil.getToday());
		return null;
	}

	/**
	 * 業販一覧画面：検索結果一覧注文請書データ作成処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportCorporateOrderAcceptanceList(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (form.getCorporateSalesSlipList() == null || form.getCorporateSalesSlipList().size() <= 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{
			ExportCorporateOrderAcceptanceService exportOrderAcceptanceService = new ExportCorporateOrderAcceptanceService();

			exportOrderAcceptanceService.orderAcceptanceList(response,form.getCorporateSalesSlipList(), request.getParameter("tax"));

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw e;
		}
		form.getCorporateSalesSlipDTO().setOrderDate(StringUtil.getToday());
		return null;
	}

	/**
	 * 業販一覧画面：検索結果一覧注文請書ファイル出力処理
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ActionForward corporateOrderAcceptancePrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "orderAcceptance.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "注文請書" + fileNmTimeFormat.format(date) + ".pdf";

		ExportCorporateOrderAcceptanceService exportOrderAcceptanceService = new ExportCorporateOrderAcceptanceService();

		try {
			exportOrderAcceptanceService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;

		}
		return null;
	}

	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の初期表示処理<br>
	 */
	protected ActionForward initExportCorporateBill(AppActionMapping appMapping, CorporateSaleForm form,
	           HttpServletRequest request) throws Exception {

		 form.setCorporateSalesSlipList(new ArrayList<ExtendCorporateSalesSlipDTO>());

		 //法人リスト取得
		 ExportCorporateBillService corporationService = new ExportCorporateBillService();
		 form.setCorporationList(corporationService.getCorporationList());

		 CorporateSaleDisplayService saleDisplayService = new CorporateSaleDisplayService();
		 form.setCorporateSaleSearchDTO(saleDisplayService.initCorporateSaleSearchDTO());

		 // 表示対象法人をAllに設定
		 form.setDispSysCorporationId(0);

		 //1ページ目を指定
		 form.setCorporateBillPageIdx(0);

		// 検索の表示件数リストボックスの初期表示を「20」とするために初期値を設定する。
		form.getCorporateSaleSearchDTO().setListPageMax("2");

		 // 指定法人のシステム業販請求書IDリストを全件取得し、formへ設定
		 form.setSysCorporateBillIdList(corporationService.getSysCorporateBillIdList(form.getDispSysCorporationId()));

		 //検索件数が０件の場合、失敗
		if (form.getSysCorporateBillIdList() == null || form.getSysCorporateBillIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する請求書はありません。");
			form.setErrorDTO(errorMessageDTO);
			// 一覧表示を初期化
			form.setCorporateBillListSize(0);
			form.setCorporateBillDTOList(new ArrayList<ExtendCorporateBillDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}

		//一覧表示用
		form.setCorporateBillListSize(form.getSysCorporateBillIdList().size());

		// 1ページ目の業販請求書一覧リストを取得する。
		form.setCorporateBillDTOList(corporationService.getCorporateBillList(form.getSysCorporateBillIdList(), form.getCorporateBillPageIdx(),
				form.getCorporateBillListPageMax()));

		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の新規作成処理<br>
	 * ＤＢの業販データに基づき請求書情報を作成、表示する。
	 */
	protected ActionForward createExportCorporateBill(AppActionMapping appMapping, CorporateSaleForm form,
	           HttpServletRequest request) throws Exception {


		ExportCorporateBillService corporationService = new ExportCorporateBillService();
		TransactionDAO transactionDAO = new TransactionDAO();

//		try {

			transactionDAO.begin();

			// 指定された法人の月単位・締日指定での売上・業販情報を集計し、請求書情報をDBに登録
			ErrorDTO errorMessageDTO = corporationService.createCorporateBill(form.getExportMonth(), form.getSysCorporationId(), form.getSelectedCutoff());

			// 作成に失敗した場合、エラーメッセージを設定
			if(!errorMessageDTO.isSuccess()){
				form.setErrorDTO(errorMessageDTO);
				// 次へ遷移
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			// 出力法人IDを表示対象に設定
			form.setDispSysCorporationId(form.getSysCorporationId());
/*
		} catch (Exception e) {

			transactionDAO.rollback();

			// Exception処理
			ErrorDTO errorMessageDTO = new ErrorDTO();
			ErrorMessageDTO errMessDTO = new ErrorMessageDTO();
			List<ErrorMessageDTO> errorMessageList = new ArrayList<ErrorMessageDTO>();
			errMessDTO.setErrorMessage("請求書作成中にエラーが発生したため、請求書は作成されませんでした");
			errMessDTO.setSuccess(false);
			errorMessageList.add(errMessDTO);
			errorMessageDTO.setErrorMessageList(errorMessageList);
			errorMessageDTO.setSuccess(false);
			form.setErrorDTO(errorMessageDTO);
			// 次へ遷移
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		*/

		transactionDAO.commit();

		// 画面の法人一覧処理
		corporateBillList(appMapping, form, request);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);

	}

	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の更新処理<br>
	 * 　業販請求書出力画面にて該当の請求書情報を更新する。
	 */
	protected ActionForward updateExportCorporateBill(AppActionMapping appMapping, CorporateSaleForm form,
	           HttpServletRequest request) throws Exception {
		ExportCorporateBillService corporationService = new ExportCorporateBillService();

		// 入力内容登録をDBに反映
		corporationService.updateCorporateBillList(form.getCorporateBillDTOList());

		// アラートを設定
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		// ページング
		corporateBillListPageNo(appMapping, form, request);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の削除処理<br>
	 * 業販請求書出力画面にて該当の請求書情報を削除する。
	 */
	protected ActionForward deleteExportCorporateBill (AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request) throws Exception {

		ExportCorporateBillService corporationService = new ExportCorporateBillService();

		// 開始
		begin();

		// 入力内容登録をDBに反映
		corporationService.deleteCorporateBill(form.getCorporateBillDTOList(), form.getCorporateBillListIdx());

		// 登録成功
		commit();

		// アラートを設定
		form.setAlertType(WebConst.ALERT_TYPE_DELETE);

		// 画面の法人一覧処理
		corporateBillList(appMapping, form, request);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の出力処理<br>
	 * 業販請求書出力画面にて該当の請求書情報をpdf出力する。
	 */
	protected ActionForward exportCorporateBillList(AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		String result = "";
		try{
			ExportCorporateBillService exportBillService = new ExportCorporateBillService();
			//画面で選択した列のIDを基準に出力対象を絞り込む
			long sysCorporateBillId = form.getSysCorporateBillId();

			// 選択行の業販請求書、および商品リストを取得
			ExportCorporateBillDTO corporateBillDTO = exportBillService.getSearchedCorporateBill(sysCorporateBillId);
			List<CorporateBillItemDTO> billItemList = exportBillService.getSearchedCorporateBillItemList(sysCorporateBillId);

			// PDFファイルを出力
			exportBillService.billList(response, corporateBillDTO, billItemList);

			// 次へ遷移
			return null;

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			result = e.getMessage();
			PrintWriter printWriter = response.getWriter();
			if (StringUtils.equals(result, "The document has no pages.")) {
				result = "1";
				printWriter.print(result);
			} else {
				printWriter.print(result);
			}
			return null;
		}
	}

	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の出力処理の表示部分<br>
	 * pdf出力された業販請求書を表示する。
	 */
	protected ActionForward corporateBillPrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "bill.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "請求書" + fileNmTimeFormat.format(date) + ".pdf";

		ExportCorporateBillService exportBillService = new ExportCorporateBillService();

		try {
			exportBillService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return null;
	}

	/**
	 * 業販売上統計画面：初期表示処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initCorporateSaleSummary(AppActionMapping appMapping, CorporateSaleForm form,
	           HttpServletRequest request) throws Exception {

		 form.setCorporateSalesSlipList(new ArrayList<ExtendCorporateSalesSlipDTO>());

		 CorporationService corpSaleDisplayService = new CorporationService();
		 form.setCorporationList(corpSaleDisplayService.getCorporationList());

		 CorporateSaleDisplayService saleDisplayService = new CorporateSaleDisplayService();
		 form.setCorporateSaleSearchDTO(saleDisplayService.initCorporateSaleSearchDTOSummaly());

		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販入金管理初期表示処理<br>
	 * 入金管理の初期表示をする処理<br>
	 * 入金管理初期表示時は表示件数10件<br>
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward initCorporatePaymentManagement(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		 //法人リスト取得
		 CorporatePaymentManagementService paymentManageService = new CorporatePaymentManagementService();
		 form.setCorporationList(paymentManageService.getCorporationList());

		 CorporateSaleDisplayService saleDisplayService = new CorporateSaleDisplayService();
		 CorporateSaleSearchDTO searchDTO = saleDisplayService.initCorporateSaleSearchDTO();
		 // 入金管理の時は初期表示が10件のため、上書き
		 searchDTO.setListPageMax("4");
		 form.setCorporateSaleSearchDTO(searchDTO);

		 // 表示対象法人をAllに設定
		 form.setDispSysCorporationId(0);

		 //1ページ目を指定
		 form.setPaymentManagePageIdx(0);

		 // 指定法人のシステム入金管理IDリストを全件取得し、formへ設定
		 form.setSysPayManageIdList(paymentManageService.getSysPaymentManageIdList(form.getDispSysCorporationId()));

		 //検索件数が０件の場合、失敗
		if (form.getSysPayManageIdList() == null || form.getSysPayManageIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する入金管理情報はありません。");
			form.setErrorDTO(errorMessageDTO);
			// 一覧表示を初期化
			form.setPaymentManageListSize(0);
			form.setPaymentManageDTOList(new ArrayList<ExtendPaymentManagementDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}

		// 一覧表示用
		form.setPaymentManageListSize(form.getSysPayManageIdList().size());

		// 1ページ目の入金管理情報一覧リストを取得する。
		form.setPaymentManageDTOList(paymentManageService.getPaymentManagement(form.getSysPayManageIdList(), form.getPaymentManageListIdx(),
				form.getPaymentManageListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 入金管理のページ遷移処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward corporatePaymentManagementPageNo(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		CorporatePaymentManagementService paymentManageService = new CorporatePaymentManagementService();

		// システム入金管理IDリストが0件の場合、元画面に遷移
		if (form.getSysPayManageIdList() == null || form.getSysPayManageIdList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定ページの入金管理情報一覧リストを取得する。
		form.setPaymentManageDTOList(paymentManageService.getPaymentManagement(form.getSysPayManageIdList(), form.getPaymentManagePageIdx(),
				form.getPaymentManageListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販入金管理画面：入金検索処理：業者、締日リンク押下時
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward paymentManagementList(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		CorporatePaymentManagementService payManageService = new CorporatePaymentManagementService();

		// 指定法人のシステム入金管理情報IDリストを全件取得しformへ設定
		// 締日を指定しない場合
		if (form.getDispCutoffDate() == 999) {
			form.setSysPayManageIdList(payManageService.getSysPaymentManageIdList(form.getDispSysCorporationId()));
		} else {
			form.setSysPayManageIdList(payManageService.getSysPaymentManageIdList(form.getDispSysCorporationId(), form.getDispCutoffDate()));
		}

		// 検索件数が0件の場合
		if (form.getSysPayManageIdList() == null || form.getSysPayManageIdList().size() <= 0) {
			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する入金管理情報はありません");
			form.setErrorDTO(errorMessageDTO);

			// 一覧表示を初期化
			form.setPaymentManageListSize(0);
			form.setPaymentManageDTOList(new ArrayList<ExtendPaymentManagementDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 一覧表示用
		form.setPaymentManageListSize(form.getSysPayManageIdList().size());

		// 1ページ目の入金管理情報リストを取得する
		form.setPaymentManageDTOList(payManageService.getPaymentManagement(form.getSysPayManageIdList(),
																																	form.getPaymentManagePageIdx(),
																																	form.getPaymentManageListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 入金管理画面での検索処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward searchPaymentManagement(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		CorporatePaymentManagementService paymentManageService = new CorporatePaymentManagementService();
		CorporateSaleSearchDTO searchDTO = form.getCorporateSaleSearchDTO();

		//1ページ目を設定
		 form.setPaymentManagePageIdx(0);

		// 検索条件を表示
		searchDTO.setListPageMax(searchDTO.getListPageMax());
		//表示件数を指定
		form.setPaymentManageListPageMax(WebConst.LIST_PAGE_MAX_FOR_PAYMENTMANAGEMENT_MAP.get(searchDTO.getListPageMax()));

		//検索条件を元に入金管理IDを検索
		form.setSysPayManageIdList(paymentManageService.getSysPaymentManageIdList(searchDTO));

		 //検索件数が０件の場合、失敗
		if (form.getSysPayManageIdList() == null || form.getSysPayManageIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する入金管理情報はありません。");
			form.setErrorDTO(errorMessageDTO);
			// 一覧表示を初期化
			form.setPaymentManageListSize(0);
			form.setPaymentManageDTOList(new ArrayList<ExtendPaymentManagementDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}

		// 一覧表示用
		form.setPaymentManageListSize(form.getSysPayManageIdList().size());

		// 1ページ目の入金管理情報一覧リストを取得する。
		form.setPaymentManageDTOList(paymentManageService.getPaymentManagement(form.getSysPayManageIdList(), form.getPaymentManagePageIdx(),
				form.getPaymentManageListPageMax()));


		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 入金画面での「編集を有効にするボタン」の処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward updatePaymentManagement(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request) throws Exception {

		CorporatePaymentManagementService paymentManageService = new CorporatePaymentManagementService();

		// 入力内容をDBに反映
		paymentManageService.updatePaymentManagement(form.getPaymentManageDTOList());

		// アラートを設定
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		// ページング
		corporatePaymentManagementPageNo(appMapping, form, request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上統計ダウンロード処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward corporateSaleSummaryDownLoad(AppActionMapping appMapping,
			CorporateSaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.CORPORATE_SALE_SUMMALY_TEMPLATE_PATH);

		// ファイルを読み込みます。
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ワークブックを読み込みます。
		HSSFWorkbook workBook = new HSSFWorkbook(filein);

		// エクセルファイルを編集します
		ExportExcelCorporateSalesSummalyService exportExcelSalesService = new ExportCorporateSaleSummalyService();

		CorporateSaleSearchDTO searchDto = form.getCorporateSaleSearchDTO();
		// corpSaleDisplayService.setFlags(searchDto);
		workBook = exportExcelSalesService.getFileExportCorporateSales(searchDto, workBook);

		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "業販売上統計表_" + date + ".xls";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// エクセルファイル出力
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);
		ServletOutputStream fileOutHssf = response.getOutputStream();
		workBook.write(fileOutHssf);
		fileOutHssf.close();

		return null;
	}
	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の法人一覧処理<br>
	 * 法人ごとに請求書情報を取得し、一覧表示する。
	 */
	protected ActionForward corporateBillList (AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request) throws Exception {

		ExportCorporateBillService corporationService = new ExportCorporateBillService();

		// 指定法人のシステム業販請求書IDリストを全件取得し、formへ設定
		// 締日を指定しない場合
		if (form.getDispCutoffDate() == 999) {
			form.setSysCorporateBillIdList(corporationService.getSysCorporateBillIdList(form.getDispSysCorporationId()));
		} else {
			form.setSysCorporateBillIdList(corporationService.getSysCorporateBillIdList(form.getDispSysCorporationId(), form.getDispCutoffDate()));
		}

			//検索件数が０件の場合失敗
			if (form.getSysCorporateBillIdList() == null || form.getSysCorporateBillIdList().size() <= 0) {

				ErrorDTO errorMessageDTO = new ErrorDTO();
				errorMessageDTO.setSuccess(false);
				errorMessageDTO.setErrorMessage("該当する請求書はありません。");
				form.setErrorDTO(errorMessageDTO);

				// 一覧表示を初期化
				form.setCorporateBillListSize(0);
				form.setCorporateBillDTOList(new ArrayList<ExtendCorporateBillDTO>());

				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			//一覧表示用
			form.setCorporateBillListSize(form.getSysCorporateBillIdList().size());

			// 1ページ目の業販請求書一覧リストを取得する。
			form.setCorporateBillDTOList(corporationService.getCorporateBillList(form.getSysCorporateBillIdList(), form.getCorporateBillPageIdx(),
					form.getCorporateBillListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);

	}
	/**
	 * 業販請求書出力<br>
	 * 業販請求書出力画面の一覧ページ遷移処理<br>
	 * 一覧の指定されたページの表示分の請求書情報を取得し、一覧表示する。
	 */
	protected ActionForward corporateBillListPageNo (AppActionMapping appMapping, CorporateSaleForm form,
            HttpServletRequest request) throws Exception {

		ExportCorporateBillService corporationService = new ExportCorporateBillService();

		// システム業販請求書IDリストが0件の場合、元画面に遷移
		if (form.getSysCorporateBillIdList() == null || form.getSysCorporateBillIdList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定ページの業販請求書一覧リストを取得する。
		form.setCorporateBillDTOList(corporationService.getCorporateBillList(form.getSysCorporateBillIdList(), form.getCorporateBillPageIdx(),
				form.getCorporateBillListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 請求書検索メソッド.
	 * <br>
	 * 請求書出力画面で検索条件に一致する請求書情報を取得し一覧表示する。
	 *
	 * @author gkikuchi
	 * @since 2017/11/09
	 * @param appMapping 請求書検索
	 * @param form 請求書出力画面のフォーム
	 * @param request
	 * @param response
	 * @return マッピング 請求書作成画面
	 * @throws Exception DB接続失敗
	 */
	protected ActionForward searchExportCorporateBill(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ExportCorporateBillService corporationService = new ExportCorporateBillService();

		/*
		 * 請求書出力画面の入力値をもとに業販請求書情報を検索する。
		 * 請求月のFROｍとTO両方が入力されていなかった場合は全日付対象の検索とする。
		 * 必須入力項目なし。請求月と合計請求金額は片方が入力されていた場合は以上以下で検索する。
		 * 請求書番号、得意先番号と得意先名は部分一致とする。
		 */
		form.setSysCorporateBillIdList(corporationService.getSysCorporateBillIdList(form.getCorporateSaleSearchDTO()));

		// 検索件数が０件の場合失敗
		if (form.getSysCorporateBillIdList() == null || form.getSysCorporateBillIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する請求書はありません。");
			form.setErrorDTO(errorMessageDTO);

			// 一覧表示を初期化
			form.setCorporateBillListSize(0);
			form.setCorporateBillDTOList(new ArrayList<ExtendCorporateBillDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 表示するときに一ページの最大件数分表示したらページングしないように制御するために請求書の件数を取得する。
		form.setCorporateBillListSize(form.getSysCorporateBillIdList().size());

		// 検索後の初期表示は1ページ目からなのでページ数を「0」に設定する。
		form.setCorporateBillPageIdx(0);

		// 1ページの最大出力件数を設定する。
		form.setCorporateBillListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getCorporateSaleSearchDTO().getListPageMax()));

		// 業販請求書情報から業販請求書一覧リストを取得する。
		form.setCorporateBillDTOList(corporationService.getCorporateBillList(form.getSysCorporateBillIdList(),
				form.getCorporateBillPageIdx(),
				form.getCorporateBillListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 請求書一括出力メソッド.
	 * <br/>
	 *
	 * @author gkikuchi
	 * @exception DB接続失敗
	 * @exception 画面でチェックした請求書番号が取得できなかった場合
	 * @param addmapping 請求書出力
	 * @param form 請求書作成画面のフォーム（請求書番号）
	 * @param request
	 * @param response
	 * @return マッピング 請求書作成画面
	 * @since 2017/11/21
	 */
	protected ActionForward batchExportCorporateBill(AppActionMapping appMapping, CorporateSaleForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 業販請求書が取得できなかった時の情報を格納する変数
		String result = "";
		try {
			ExportCorporateBillService exportBillService = new ExportCorporateBillService();

			// 画面で選択した列の業販請求書IDを取得する。
			long[] sysCorporateBillIdArray = form.getSysCorporateBillIdArray();

			// 選択行の業販請求書IDに紐づく業販請求書情報と商品リストを取得する。
			Map<String, ExportCorporateBillDTO> ecbDtoMap = exportBillService.getSearchedCorporateBill(sysCorporateBillIdArray);
			Map<String, List<CorporateBillItemDTO>> billItemListMap = exportBillService.getSearchedCorporateBillItemList(sysCorporateBillIdArray);

			// PDFファイルを出力
			exportBillService.billList(response, ecbDtoMap, billItemListMap);

			response.setStatus(HttpServletResponse.SC_OK);

			// 次へ遷移
			return null;

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			result = e.getMessage();
			PrintWriter printWriter = response.getWriter();
			if (StringUtils.equals(result, "The document has no pages.")) {
				result = "1";
				printWriter.print(result);
			} else {
				printWriter.print(result);
			}
			return null;
		}
	}

}
