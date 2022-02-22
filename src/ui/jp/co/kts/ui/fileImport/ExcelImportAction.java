package jp.co.kts.ui.fileImport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.output.entity.ActionErrorExcelImportDTO;
import jp.co.kts.service.fileImport.ExcelImportItemInsertService;
import jp.co.kts.service.fileImport.ExcelImportItemUpdateService;
import jp.co.kts.service.fileImport.ExcelImportService;
import jp.co.kts.service.fileImport.ExcelXImportItemInsertService;
import jp.co.kts.service.fileImport.ExcelXImportItemUpdateService;
import jp.co.kts.service.fileImport.ExcelXImportService;
import jp.co.kts.ui.web.struts.WebConst;

public class ExcelImportAction extends AppBaseAction{

	private static final String EXCEL_XLSX_CONTENT_TYPE =
			 "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ExcelImportForm form = (ExcelImportForm)appForm;

		//初期処理
		if ("/initExcelImport".equals(appMapping.getPath())) {
			return initExcelImport(appMapping, form, request);
		//商品新規登録
		} else if ("/excelImport".equals(appMapping.getPath())) {
			return excelImport(appMapping, form, request);
		//商品情報更新
		} else if ("/excelImportUpdateItem".equals(appMapping.getPath())) {
			return excelImportUpdateItem(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * 商品インポート画面の初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initExcelImport(AppActionMapping appMapping, ExcelImportForm form,
            HttpServletRequest request) throws Exception {

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 在庫一覧の商品情報Excelをインポートし新規登録するメソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward excelImport(AppActionMapping appMapping, ExcelImportForm form,
            HttpServletRequest request) throws Exception {

		ExcelImportForm excelImportForm = form;
		
		if (!EXCEL_XLSX_CONTENT_TYPE.equals(excelImportForm.getFileUp().getContentType())) { //xls format
			ExcelImportService service = new ExcelImportItemInsertService();

			ActionErrorExcelImportDTO dto = new ActionErrorExcelImportDTO();

			begin();

			 //ExcelファイルをDTOに取込(validateチェック含む)

			try {
				dto = service.validate(excelImportForm.getFileUp());

				 //バリデートチェックにかかっている場合は画面上にエラーを表示
				if (!dto.getResult().isSuccess()) {
					List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
					messages.addAll(dto.getResult().getErrorMessages());
					saveErrorMessages(request, messages);
					rollback();
					return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
				}

				commit();

				form.setAlertType(WebConst.ALERT_TYPE_REGIST);

				//これはよくないけど仕様がない
			} catch (Exception e) {
				rollback();
				throw new Exception(e);
			}
		}else { //xlsx format
			ExcelXImportService service = new ExcelXImportItemInsertService();

			ActionErrorExcelImportDTO dto = new ActionErrorExcelImportDTO();

			begin();

			 //ExcelファイルをDTOに取込(validateチェック含む)

			try {
				dto = service.validate(excelImportForm.getFileUp());

				 //バリデートチェックにかかっている場合は画面上にエラーを表示
				if (!dto.getResult().isSuccess()) {
					List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
					messages.addAll(dto.getResult().getErrorMessages());
					saveErrorMessages(request, messages);
					rollback();
					return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
				}

				commit();

				form.setAlertType(WebConst.ALERT_TYPE_REGIST);

				//これはよくないけど仕様がない
			} catch (Exception e) {
				rollback();
				throw new Exception(e);
			}
		}

		return initExcelImport(appMapping, form, request);
	}

	/**
	 * 商品インポート画面で商品情報の更新Excelを取り込む際のメソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward excelImportUpdateItem(AppActionMapping appMapping,
			ExcelImportForm form, HttpServletRequest request) throws Exception {

		ExcelImportForm excelImportForm = form;
		
		if (!EXCEL_XLSX_CONTENT_TYPE.equals(excelImportForm.getFileUp().getContentType())) { //xls format
			begin();

			ExcelImportService excelImportService = new ExcelImportItemUpdateService();
			ActionErrorExcelImportDTO dto = new ActionErrorExcelImportDTO();
			dto = excelImportService.validate(form.getFileUp());

			 //バリデートチェックにかかっている場合は画面上にエラーを表示
			if (!dto.getResult().isSuccess()) {

				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(dto.getResult().getErrorMessages());
				saveErrorMessages(request, messages);
				rollback();
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			commit();
			form.setAlertType(WebConst.ALERT_TYPE_UPDATE);
		}else { //xlsx format
			begin();

			ExcelXImportService excelImportService = new ExcelXImportItemUpdateService();
			ActionErrorExcelImportDTO dto = new ActionErrorExcelImportDTO();
			dto = excelImportService.validate(form.getFileUp());

			 //バリデートチェックにかかっている場合は画面上にエラーを表示
			if (!dto.getResult().isSuccess()) {

				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(dto.getResult().getErrorMessages());
				saveErrorMessages(request, messages);
				rollback();
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			commit();
			form.setAlertType(WebConst.ALERT_TYPE_UPDATE);
		}

		return initExcelImport(appMapping, form, request);
	}

}
