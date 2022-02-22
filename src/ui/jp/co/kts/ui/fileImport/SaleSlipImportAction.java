package jp.co.kts.ui.fileImport;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
//import jp.co.kts.app.output.entity.SaleSlipErrorExcelImportDTO;
//import jp.co.kts.service.fileImport.SaleSlipUpdateService;
import jp.co.kts.ui.web.struts.WebConst;

public class SaleSlipImportAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SaleSlipImportForm form = (SaleSlipImportForm)appForm;

		if ("/initSaleSlipExcelImport".equals(appMapping.getPath())) {
			return initSaleSlipExcelImport(appMapping, form, request);
		}else if ("/saleSlipExcelImport".equals(appMapping.getPath())) {
			return saleSlipExcelImport(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	private ActionForward initSaleSlipExcelImport(AppActionMapping appMapping,
			SaleSlipImportForm form, HttpServletRequest request) throws Exception {
		form.setAlertType("0");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上データインポート画面で売上データの更新Excelを取り込む際のメソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward saleSlipExcelImport(AppActionMapping appMapping,
			SaleSlipImportForm form, HttpServletRequest request) throws Exception {

		begin();

//		SaleSlipUpdateService excelImportService = new SaleSlipUpdateService();
//		SaleSlipErrorExcelImportDTO dto = new SaleSlipErrorExcelImportDTO();
//		dto = excelImportService.validate(form.getFileUp());
//
//		 //バリデートチェックにかかっている場合は画面上にエラーを表示
//		if (!dto.getResult().isSuccess()) {
//
//			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
//			messages.addAll(dto.getResult().getErrorMessages());
//			saveErrorMessages(request, messages);
//			rollback();
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}

		commit();
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

}
