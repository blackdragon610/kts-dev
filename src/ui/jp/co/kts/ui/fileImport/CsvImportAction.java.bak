package jp.co.kts.ui.fileImport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.CsvImportDTO;
import jp.co.kts.app.input.entity.CsvInputDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.fileImport.CsvDirectImportService;
import jp.co.kts.service.fileImport.CsvImportService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.sale.SaleCsvService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * CSVインポート用Actionクラス
 * @author kaihatsu18
 *
 */
public class CsvImportAction extends AppBaseAction{

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CsvImportForm form = (CsvImportForm)appForm;

		
		if ("/initDomesticOrderStockCsvImport".equals(appMapping.getPath())) {
			//国内注文入荷予定日取込 画面初期処理
			return initDomesticOrderStockCsvImport(appMapping, form, request);
		} 
		
		else if ("/domesticOrderStockCsvImport".equals(appMapping.getPath())) {
			// 国内注文入荷予定日 データ取込（単一ファイル）
			return domesticOrderStockCsvImport(appMapping, form, request);
			
		}
		//受注データ取込画面初期処理
		if ("/initCsvImport".equals(appMapping.getPath())) {
			return initCsvImport(appMapping, form, request);
		//受注データ取込（単一ファイル）
		} else if ("/csvImport".equals(appMapping.getPath())) {
			return  csvImport(appMapping, form, request);
		//受注データ取込（複数ファイル）
		} else if ("/csvListImport".equals(appMapping.getPath())) {
			return  csvListImport(appMapping, form, request);
		//配送データ取込初期処理
		} else if ("/initDeliveryCsvImport".equals(appMapping.getPath())) {
			return  initDeliveryCsvImport(appMapping, form, request);
		//配送データ取込：すべて上書き（単一ファイル）
		} else if ("/saveDeliveryCsvImport".equals(appMapping.getPath())) {
			return  saveDeliveryCsvImport(appMapping, form, request);
		//配送データ取込：すべて上書き(複数ファイル)
		} else if ("/saveDeliveryCsvImportList".equals(appMapping.getPath())) {
			return  saveDeliveryCsvImportList(appMapping, form, request);
		//配送データ取込：送り状番号付与(単一ファイル)
		} else if ("/addSlipNoDeliveryCsvImport".equals(appMapping.getPath())) {
			return addSlipNoDeliveryCsvImport(appMapping, form, request);
		//配送データ取込：送り状番号付与(複数ファイル)
		} else if ("/addSlipNoDeliveryCsvImportList".equals(appMapping.getPath())) {
			return addSlipNoDeliveryCsvImportList(appMapping, form, request);
		//助ネコインポート：国内注文データ取込画面初期処理
		} else if ("/initDomesticCsvImport".equals(appMapping.getPath())) {
			return initDomesticCsvImport(appMapping, form, request);
		//助ネコインポート：国内注文データインポート処理
		} else if ("/domesticCsvImport".equals(appMapping.getPath())) {
			return  domesticCsvImport(appMapping, form, request);
		//助ネコインポート：国内注文データインポート処理(複数ファイル)
		} else if ("/csvDomesticListImport".equals(appMapping.getPath())) {
			return  csvDomesticListImport(appMapping, form, request);
		//助ネコインポート：在庫数キープインポート初期処理
		} else if ("/initKeepCsvImport".equals(appMapping.getPath())) {
			return initKeepCsvImport(appMapping, form, request);
		//助ネコインポート：助ネコインポート：在庫数キープ取込（単一ファイル）
		}else if ("/initRemoveKeepCsvImport".equals(appMapping.getPath())) {
			return initRemoveKeepCsvImport(appMapping, form, request);		
		} else if ("/keepCsvImport".equals(appMapping.getPath())) {
			return keepCsvImport(appMapping, form, request);
		//助ネコインポート：助ネコインポート：在庫数キープ取込（複数ファイル）
		} else if ("/keepRemoveCsvImport".equals(appMapping.getPath())) {
			return keepRemoveCsvImport(appMapping, form, request);
		//助ネコインポート：助ネコインポート：在庫数キープ取込（複数ファイル）
		}else if ("/keepCsvListImport".equals(appMapping.getPath())) {
			return KeepCsvListImport(appMapping, form, request);
		}
		
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * 助ネコインポート：受注データ取込画面初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initCsvImport(AppActionMapping appMapping, CsvImportForm form,
	            HttpServletRequest request) throws Exception {

		CorporationService corporationService = new CorporationService();

		form.setCorporationId(0);
		form.setCorporationList(corporationService.getCorporationList());

		 CsvImportService csvImportService = new CsvImportService();
		form.setAlertType("0");
		form.setCsvInputList(csvImportService.initCsvInputList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：受注データ取込（単一ファイル）
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward csvImport(AppActionMapping appMapping, CsvImportForm form,
	            HttpServletRequest request) throws Exception {

		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		CsvImportService service = new CsvImportService();

		begin();

		form.setCsvErrorDTO(new ErrorDTO());

		if (form.getCorporationId() != 0) {

			form.setCsvErrorDTO(service.importFile(form.getCorporationId(), form.getFileUp(), form.getCsvImportList()));
		}

		// CSVファイルから売上伝票の作成
		if (form.getCsvErrorDTO().isSuccess()) {

			SaleCsvService saleCsvService = new SaleCsvService();
			form.setCsvErrorDTO(saleCsvService.csvToSaleSlip(form.getCsvImportList()));
			form.setTrueCount(form.getCsvErrorDTO().getTrueCount());
		}

		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：受注データ取込（複数ファイル）
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward csvListImport(AppActionMapping appMapping,
			CsvImportForm form, HttpServletRequest request) throws Exception {

		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		begin();

		CsvImportService service = new CsvImportService();

//		form.setCsvErrorList(service.importFileList(form.getCsvInputList(), form.getCsvImportList()));

		long[] corporationIds = {0L, 0L, 0L, 0L, 0L, 0L};
		int idx = 0;
		//エラーチェック
		for (CsvInputDTO dto: form.getCsvInputList()) {

			if (StringUtils.isEmpty(dto.getFileUp().getFileName())) {
				corporationIds[idx++] = 0L;
				continue;
			}

			//ファイル会社名判定
			corporationIds[idx] = service.getCorporationId(dto.getFileUp().getFileName());
			if (corporationIds[idx++] == 0L) {
				form.getCsvErrorList().add(service.getFileNmError(dto.getFileUp().getFileName()));
				continue;
			}
		}

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//体裁が整っているかチェック
		List<CsvInputDTO> checkedCsvInputList = new ArrayList<>();
		form.setCsvErrorList(service.csvCheck(form.getCsvInputList(), checkedCsvInputList));

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//csvインポート
		idx = 0;
		for (CsvInputDTO dto: form.getCsvInputList()) {

			form.getCsvErrorList().add(service.importFile(corporationIds[idx++], dto.getFileUp(),form.getCsvImportList()));
		}

		//売上伝票作成
//		form.setCsvErrorList(new SaleService().csvToSaleSlipList(form.getCsvImportList()));
		SaleCsvService saleCsvService = new SaleCsvService();
		List<List<CsvImportDTO>> csvImportLists = saleCsvService.csvToSaleSlipList(form.getCsvImportList());
		for (List<CsvImportDTO> list: csvImportLists) {

			ErrorDTO dto = saleCsvService.csvToSaleSlip(list);
			form.getCsvErrorList().add(dto);
			form.setTrueCount(dto.getTrueCount() + form.getTrueCount());
		}

		//仕様変更
		//一部の伝票を取り込むことに失敗してもエラーメッセージを画面にだすだけに
//		if (!service.isSuccess(form.getCsvErrorList())) {
//			rollback();
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}

		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：配送データ取込初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initDeliveryCsvImport(AppActionMapping appMapping, CsvImportForm form,
            HttpServletRequest request) throws Exception {

		CorporationService corporationService = new CorporationService();

		form.setCorporationId(0);
		form.setCorporationList(corporationService.getCorporationList());

		CsvImportService csvImportService = new CsvImportService();

		form.setCsvInputList(csvImportService.initCsvInputList());

		form.setAlertType("0");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：配送データ取込：すべて上書き（単一ファイル）
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward saveDeliveryCsvImport(AppActionMapping appMapping,
			CsvImportForm form, HttpServletRequest request) throws Exception {

		begin();

		/*
		 * if (!StringUtils.equals(form.getDeliveryRadio(), "save")) {
		 * 
		 * return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE); }
		 */
		//csv_importテーブルへ格納とimportDTOへ格納
/*		if (form.getDeliveryCompanyId() == 0) {
			// 助ネコ CSV
			form.setCsvErrorDTO(new CsvImportService().importFile(form.getCorporationId(), form.getFileUp(), form.getCsvImportList()));
		}
		else {
*/			// Other delivery csv
			form.setCsvErrorDTO(new CsvDirectImportService().importFile(form.getDeliveryCompanyId(), form.getCorporationId(), form.getFileUp(), form.getCsvImportList()));
//		}

		if (!form.getCsvErrorDTO().isSuccess()) {
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//			form.setCsvErrorDTO(new SaleService().csvToSaleSlip(form.getCsvImportList()));
		}

		//売上伝票の上書
		SaleCsvService saleCsvService = new SaleCsvService();
		/*
		 * if (form.getDeliveryCompanyId() == 0) {
		 * form.setCsvErrorDTO(saleCsvService.saveSlipNo(form.getCsvImportList())); }
		 * else {
		 */			// other delivery company
			form.setCsvErrorDTO(saleCsvService.saveSlipNoOnly(form.getCsvImportList()));
//		}
		form.setTrueCount(form.getCsvErrorDTO().getTrueCount());

		//仕様変更
		//一部の伝票を取り込むことに失敗してもエラーメッセージを画面にだすだけに
//		if (!form.getCsvErrorDTO().isSuccess()) {
//			rollback();
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}

		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：配送データ取込：すべて上書き一括(複数ファイル)
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward saveDeliveryCsvImportList(AppActionMapping appMapping, CsvImportForm form,
			HttpServletRequest request) throws Exception {

		if (!StringUtils.equals(form.getDeliveryRadio(), "save")) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());
		begin();
		CsvImportService service = new CsvImportService();

		long[] corporationIds = {0L, 0L, 0L, 0L, 0L, 0L};
		int idx = 0;
		//エラーチェック
		for (CsvInputDTO dto: form.getCsvInputList()) {

			if (StringUtils.isEmpty(dto.getFileUp().getFileName())) {
				corporationIds[idx++] = 0L;
				continue;
			}
			//ファイル会社名判定
			corporationIds[idx] = service.getCorporationId(dto.getFileUp().getFileName());
			if (corporationIds[idx++] == 0L) {
				form.getCsvErrorList().add(service.getFileNmError(dto.getFileUp().getFileName()));
				continue;
			}
		}

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//体裁が整っているかチェック
		List<CsvInputDTO> checkedCsvInputList = new ArrayList<>();
		form.setCsvErrorList(service.csvCheck(form.getCsvInputList(), checkedCsvInputList));

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//csvインポート
		idx = 0;
		for (CsvInputDTO dto: form.getCsvInputList()) {

			if (corporationIds[idx] == 0) {
				idx++;
				continue;
			}

			form.getCsvErrorList().add(service.importFile(corporationIds[idx++], dto.getFileUp(),form.getCsvImportList()));
		}

		SaleCsvService saleCsvService = new SaleCsvService();
		//売上伝票上書き
		List<List<CsvImportDTO>> csvImportLists = saleCsvService.csvToSaleSlipList(form.getCsvImportList());
		for (List<CsvImportDTO> list: csvImportLists) {

			ErrorDTO dto = saleCsvService.saveSlipNo(list);
			form.getCsvErrorList().add(dto);
			form.setTrueCount(form.getTrueCount() + dto.getTrueCount());
		}


		//仕様変更
		//一部の伝票を取り込むことに失敗してもエラーメッセージを画面にだすだけに
//		if (!service.isSuccess(form.getCsvErrorList())) {
//
//			rollback();
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}

		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：配送データ取込：送り状番号付与(単一ファイル)
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward addSlipNoDeliveryCsvImport(AppActionMapping appMapping,
			CsvImportForm form, HttpServletRequest request) throws Exception {

		if (!StringUtils.equals(form.getDeliveryRadio(), "addSlipNo")) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		begin();
		//csv_importテーブルへ格納とimportDTOへ格納
		form.setCsvErrorDTO(new CsvImportService().importFile(form.getCorporationId(), form.getFileUp(), form.getCsvImportList()));

		if (!form.getCsvErrorDTO().isSuccess()) {
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		SaleCsvService saleCsvService = new SaleCsvService();
		//伝票番号のみ付与
		form.setCsvErrorDTO(saleCsvService.addSlipNo(form.getCsvImportList()));
		form.setTrueCount(form.getCsvErrorDTO().getTrueCount());

		//仕様変更
		//一部の伝票を取り込むことに失敗してもエラーメッセージを画面にだすだけに
//		if (!form.getCsvErrorDTO().isSuccess()) {
//			rollback();
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}

		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：配送データ取込：送り状番号付与(複数ファイル)
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward addSlipNoDeliveryCsvImportList(AppActionMapping appMapping, CsvImportForm form,
			HttpServletRequest request) throws Exception {

		if (!StringUtils.equals(form.getDeliveryRadio(), "addSlipNo")) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());
		begin();
		CsvImportService service = new CsvImportService();

		long[] corporationIds = {0L, 0L, 0L, 0L, 0L, 0L};
		int idx = 0;
		//エラーチェック
		for (CsvInputDTO dto: form.getCsvInputList()) {

			if (StringUtils.isEmpty(dto.getFileUp().getFileName())) {
				corporationIds[idx++] = 0L;
				continue;
			}
			//ファイル会社名判定
			corporationIds[idx] = service.getCorporationId(dto.getFileUp().getFileName());
			if (corporationIds[idx++] == 0L) {
				form.getCsvErrorList().add(service.getFileNmError(dto.getFileUp().getFileName()));
				continue;
			}
		}

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//体裁が整っているかチェック
		List<CsvInputDTO> checkedCsvInputList = new ArrayList<>();
		form.setCsvErrorList(service.csvCheck(form.getCsvInputList(), checkedCsvInputList));

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//csvインポート
		idx = 0;
		for (CsvInputDTO dto: form.getCsvInputList()) {

			if (corporationIds[idx] == 0) {
				idx++;
				continue;
			}
			form.getCsvErrorList().add(service.importFile(corporationIds[idx++], dto.getFileUp(),form.getCsvImportList()));
		}

		SaleCsvService saleCsvService = new SaleCsvService();
		//伝票番号のみ付与
		List<List<CsvImportDTO>> csvImportLists = saleCsvService.csvToSaleSlipList(form.getCsvImportList());
		for (List<CsvImportDTO> list: csvImportLists) {

			ErrorDTO dto = saleCsvService.saveSlipNo(list);
			form.setCsvErrorDTO(dto);
			form.setTrueCount(form.getTrueCount() + dto.getTrueCount());
		}

		//仕様変更
		//一部の伝票を取り込むことに失敗してもエラーメッセージを画面にだすだけに
//		if (!service.isSuccess(form.getCsvErrorList())) {
//			rollback();
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}

		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：国内注文データ取込画面初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward initDomesticCsvImport(AppActionMapping appMapping, CsvImportForm form,
			HttpServletRequest request) throws Exception {
		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);
		CorporationService corporationService = new CorporationService();

		form.setCorporationId(0);
		form.setCorporationList(corporationService.getCorporationList());

		CsvImportService csvImportService = new CsvImportService();

		form.setCsvInputList(csvImportService.initCsvInputList());

		form.setAlertType("0");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：国内注文データインポート処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward domesticCsvImport(AppActionMapping appMapping, CsvImportForm form,
            HttpServletRequest request) throws Exception {
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		CsvImportService service = new CsvImportService();

		begin();

		form.setCsvErrorDTO(new ErrorDTO());

		//CSVの情報をリストに格納
		if (form.getCorporationId() != 0) {
			form.setCsvErrorDTO(service.importDomesticFile(form.getCorporationId(), form.getFileUp(), form.getCsvImportList()));
		}

		// CSVファイルから国内注文書の作成
		if (form.getCsvErrorDTO().isSuccess()) {

			form.setCsvErrorDTO(service.csvToDomesticSlip(form.getCsvImportList()));
			form.setTrueCount(form.getCsvErrorDTO().getTrueCount());
		}

//		if (!form.getCsvErrorDTO().isSuccess()) {
//			messageDTO.setMessageFlg("1");
//			messageDTO.setMessage("インポートに失敗しました。");
//			form.setRegistryDto(messageDTO);
//			rollback();
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("インポートが完了しました。");
		form.setRegistryDto(messageDTO);
		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：国内注文データインポート処理(複数ファイル)
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward csvDomesticListImport(AppActionMapping appMapping,
			CsvImportForm form, HttpServletRequest request) throws Exception {
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		begin();

		CsvImportService service = new CsvImportService();

//		form.setCsvErrorList(service.importFileList(form.getCsvInputList(), form.getCsvImportList()));

		long[] corporationIds = {0L, 0L, 0L, 0L, 0L, 0L};
		int idx = 0;
		//エラーチェック
		for (CsvInputDTO dto: form.getCsvInputList()) {

			if (StringUtils.isEmpty(dto.getFileUp().getFileName())) {
				corporationIds[idx++] = 0L;
				continue;
			}

			//ファイル会社名判定
			corporationIds[idx] = service.getCorporationId(dto.getFileUp().getFileName());
			if (corporationIds[idx++] == 0L) {
				form.getCsvErrorList().add(service.getFileNmError(dto.getFileUp().getFileName()));
				continue;
			}
		}

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//体裁が整っているかチェック
		List<CsvInputDTO> checkedCsvInputList = new ArrayList<>();
		form.setCsvErrorList(service.csvCheck(form.getCsvInputList(), checkedCsvInputList));

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//csvインポート
		idx = 0;
		for (CsvInputDTO dto: form.getCsvInputList()) {

			form.getCsvErrorList().add(service.importDomesticFile(corporationIds[idx++], dto.getFileUp(),form.getCsvImportList()));
		}

		//国内注文書作成
		SaleCsvService saleCsvService = new SaleCsvService();
		List<List<CsvImportDTO>> csvImportLists = saleCsvService.csvToSaleSlipList(form.getCsvImportList());
		for (List<CsvImportDTO> list: csvImportLists) {

			ErrorDTO dto = service.csvToDomesticSlip(list);
			form.getCsvErrorList().add(dto);
			form.setTrueCount(dto.getTrueCount() + form.getTrueCount());
		}

//		for (ErrorDTO error : form.getCsvErrorList()) {
//			if (!error.isSuccess()) {
//
//				messageDTO.setMessageFlg("1");
//				messageDTO.setMessage("インポートに失敗しました。");
//				form.setRegistryDto(messageDTO);
//				rollback();
//				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//			}
//		}

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("インポートが完了しました。");
		form.setRegistryDto(messageDTO);
		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：在庫数キープインポート初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initKeepCsvImport(AppActionMapping appMapping,
			CsvImportForm form, HttpServletRequest request) throws Exception {

		 CsvImportService csvImportService = new CsvImportService();
		form.setAlertType("0");
		form.setCsvInputList(csvImportService.initCsvInputList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);

	}

	private ActionForward initRemoveKeepCsvImport(AppActionMapping appMapping,
			CsvImportForm form, HttpServletRequest request) throws Exception {

		CsvImportService csvImportService = new CsvImportService();
		form.setAlertType("0");
		form.setCsvInputList(csvImportService.initCsvInputList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);

	}
	
	protected ActionForward keepRemoveCsvImport(AppActionMapping appMapping, CsvImportForm form,
            HttpServletRequest request) throws Exception {
		
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		CsvImportService service = new CsvImportService();

		begin();

		form.setCsvErrorDTO(new ErrorDTO());

		//CSVの情報をリストに格納
		form.setCsvErrorDTO(service.getCSVImportRecords(form.getFileUp(), form.getCsvImportList()));

		// CSVファイルから在庫数のキープを更新
		if (form.getCsvErrorDTO().isSuccess()) {
			form.setCsvErrorDTO(service.csvToRemoveKeeps(form.getCsvImportList()));
			form.setTrueCount(form.getCsvErrorDTO().getTrueCount());
		}

		if (!form.getCsvErrorDTO().isSuccess()) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("インポートに失敗しました。");
			form.setRegistryDto(messageDTO);
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("インポートが完了しました。");
		form.setRegistryDto(messageDTO);
		commit();
		
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}	

	/**
	 * 助ネコインポート：助ネコインポート：在庫数キープ取込（単一ファイル）
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward keepCsvImport(AppActionMapping appMapping, CsvImportForm form,
            HttpServletRequest request) throws Exception {

		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		CsvImportService service = new CsvImportService();

		begin();

		form.setCsvErrorDTO(new ErrorDTO());

		//CSVの情報をリストに格納
		form.setCsvErrorDTO(service.keepImportFile(form.getFileUp(), form.getCsvImportList()));

		// CSVファイルから在庫数のキープを更新
		if (form.getCsvErrorDTO().isSuccess()) {
			form.setCsvErrorDTO(service.csvToKeepUpdate(form.getCsvImportList()));
			form.setTrueCount(form.getCsvErrorDTO().getTrueCount());
		}

		if (!form.getCsvErrorDTO().isSuccess()) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("インポートに失敗しました。");
			form.setRegistryDto(messageDTO);
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("インポートが完了しました。");
		form.setRegistryDto(messageDTO);
		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：助ネコインポート：在庫数キープ取込（複数ファイル）
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward KeepCsvListImport(AppActionMapping appMapping,
			CsvImportForm form, HttpServletRequest request) throws Exception {

		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		begin();

		CsvImportService service = new CsvImportService();

		form.setCsvErrorList(service.importFileList(form.getCsvInputList(), form.getCsvImportList()));

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//体裁が整っているかチェック
		List<CsvInputDTO> checkedCsvInputList = new ArrayList<>();
		form.setCsvErrorList(service.csvCheck(form.getCsvInputList(), checkedCsvInputList));

		if (!service.isSuccess(form.getCsvErrorList())) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//csvインポート
		for (CsvInputDTO dto: form.getCsvInputList()) {

			form.getCsvErrorList().add(service.keepImportFile(dto.getFileUp(),form.getCsvImportList()));
		}

		SaleCsvService saleCsvService = new SaleCsvService();
		//リスト化されている伝票情報がさらにCSVごとにリスト化されるイメージ
		List<List<CsvImportDTO>> csvImportLists = saleCsvService.csvToSaleSlipList(form.getCsvImportList());
		//在庫数キープを更新
		for(List<CsvImportDTO> list : csvImportLists) {

			ErrorDTO dto = service.csvToKeepUpdate(list);
			form.getCsvErrorList().add(dto);
			form.setTrueCount(dto.getTrueCount() + form.getTrueCount());
		}

		for (ErrorDTO error : form.getCsvErrorList()) {
			if (!error.isSuccess()) {

				messageDTO.setMessageFlg("1");
				messageDTO.setMessage("インポートに失敗しました。");
				form.setRegistryDto(messageDTO);
				rollback();
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}
		}

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("インポートが完了しました。");
		form.setRegistryDto(messageDTO);
		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	
	/**
	 * 助ネコインポート：国内注文データ取込画面初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward initDomesticOrderStockCsvImport(AppActionMapping appMapping, CsvImportForm form,
			HttpServletRequest request) throws Exception {
		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);
		form.setAlertType("0");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 助ネコインポート：国内注文データインポート処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward domesticOrderStockCsvImport(AppActionMapping appMapping, CsvImportForm form,
            HttpServletRequest request) throws Exception {
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//エラーメッセージ初期化
		form.setCsvErrorDTO(new ErrorDTO());
		form.setCsvErrorList(new ArrayList<ErrorDTO>());

		CsvImportService service = new CsvImportService();

		begin();

		form.setCsvErrorDTO(new ErrorDTO());

		//CSVの情報をリストに格納
		form.setCsvErrorDTO(
				service.importDomesticOrderStockFile(form.getFileUp(), form.getCsvOrderStockImportList()));

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("インポートが完了しました。");
		form.setRegistryDto(messageDTO);
		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

}
