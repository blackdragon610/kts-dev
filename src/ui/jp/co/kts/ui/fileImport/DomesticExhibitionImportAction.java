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
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.fileImport.DomesticExhibitionImportService;
import jp.co.kts.service.fileImport.DomesticImportInsertService;
import jp.co.kts.service.fileImport.DomesticImportUpdateService;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticExhibitionImportAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DomesticExhibitionImportForm form = (DomesticExhibitionImportForm) appForm;

		//出品DBインポート画面初期表示処理
		if ("/initDomesticExhibitionImportList".equals(appMapping.getPath())) {
			return initDmstcExhbtnImprt(appMapping, form, request);
			//出品DBExcelインポートの新規登録処理を行う
		} else if ("/dmstcExibtExclImprt".equals(appMapping.getPath())) {
			return dmstcExibtExclImprt(appMapping, form, request);
			//出品DBExcelインポートの更新処理を行う
		} else if ("/dmstcExibtExclImprtUpdItm".equals(appMapping.getPath())) {
			return dmstcExibtExclImprtUpdItm(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * 出品DBインポート画面初期表示処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	private ActionForward initDmstcExhbtnImprt(AppActionMapping appMapping,
			DomesticExhibitionImportForm form, HttpServletRequest request) {

		//更新・削除から呼び出されていない場合、メッセージを初期化
		if (!form.getMessageFlg().equals("1")) {
			RegistryMessageDTO messageDTO = new RegistryMessageDTO();
			//メッセージ初期化
			form.setRegistryDto(messageDTO);
		}
		//メッセージフラグを切っておく
		form.setMessageFlg("0");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 出品DBExcelインポートの更新処理を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward dmstcExibtExclImprtUpdItm(AppActionMapping appMapping,
			DomesticExhibitionImportForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		DomesticExhibitionImportService service = new DomesticImportUpdateService();
		ActionErrorExcelImportDTO dto = new ActionErrorExcelImportDTO();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		begin();

		try {

			//ExcelファイルをDTOに取込(validateチェック含む)
			dto = service.validate(form.getFileUp());

			//バリデートチェックにかかっている場合は画面上にエラーを表示
			if (!dto.getResult().isSuccess()) {
				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(dto.getResult().getErrorMessages());
				saveErrorMessages(request, messages);
				messageDTO.setMessageFlg("1");
				messageDTO.setMessage("更新に失敗しました。");
				form.setMessageFlg("0");
				form.setRegistryDto(messageDTO);
				rollback();
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			} else {
				messageDTO.setMessageFlg("0");
				messageDTO.setMessage("更新しました。");
				form.setMessageFlg("1");
				form.setRegistryDto(messageDTO);
			}

			commit();
			form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		} catch (Exception e) {

			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(dto.getResult().getErrorMessages());
			saveErrorMessages(request, messages);
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setMessageFlg("0");
			form.setRegistryDto(messageDTO);
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);

		}

		return initDmstcExhbtnImprt(appMapping, form, request);
	}

	/**
	 * 出品DBExcelインポートの新規登録処理を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward dmstcExibtExclImprt(AppActionMapping appMapping,
			DomesticExhibitionImportForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		DomesticExhibitionImportService service = new DomesticImportInsertService();
		ActionErrorExcelImportDTO dto = new ActionErrorExcelImportDTO();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		begin();
		try {

			//ExcelファイルをDTOに取込(validateチェック含む)
			dto = service.validate(form.getFileUp());

			//バリデートチェックにかかっている場合は画面上にエラーを表示
			if (!dto.getResult().isSuccess()) {
				List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
				messages.addAll(dto.getResult().getErrorMessages());
				saveErrorMessages(request, messages);
				messageDTO.setMessageFlg("1");
				messageDTO.setMessage("登録に失敗しました。");
				form.setMessageFlg("0");
				form.setRegistryDto(messageDTO);
				rollback();
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			} else {
				messageDTO.setMessageFlg("0");
				messageDTO.setMessage("登録しました。");
				form.setMessageFlg("1");
				form.setRegistryDto(messageDTO);
			}

			commit();

			form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		} catch (Exception e) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(dto.getResult().getErrorMessages());
			saveErrorMessages(request, messages);
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("登録に失敗しました。");
			form.setMessageFlg("0");
			form.setRegistryDto(messageDTO);
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		return initDmstcExhbtnImprt(appMapping, form, request);
	}

}
