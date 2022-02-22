package jp.co.kts.ui.mst;

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
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.mst.MakerService;

public class MakerAction extends AppBaseAction {

	private static final int RESULT_CNT_REG = 1;

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MakerForm form = (MakerForm)appForm;
		//メーカー一覧初期表示
		if ("/initMakerList".equals(appMapping.getPath())) {
			return initMakerList(appMapping, form, request);
		//メーカー情報新規登録画面初期表示
		} else if ("/initRegistryMaker".equals(appMapping.getPath())) {
			return initRegistryMaker(appMapping, form, request);
		//メーカー情報登録処理
		} else if ("/registryMaker".equals(appMapping.getPath())) {
			return registryMaker(appMapping, form, request);
		//メーカー情報更新処理
		} else if ("/updateMaker".equals(appMapping.getPath())) {
			return updateMaker(appMapping, form, request);
		//メーカー情報削除
		} else if ("/deleteMaker".equals(appMapping.getPath())) {
			return deleteMaker(appMapping, form, request);
		//メーカー詳細画面初期表示
		} else if ("/detailMaker".equals(appMapping.getPath())) {
			return detailMaker(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * メーカー一覧画面初期表示
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward initMakerList(AppActionMapping appMapping, MakerForm form,
			HttpServletRequest request) throws Exception{
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryMessageDTO);

		//インスタンス生成
		MakerService makerService = new MakerService();
		//メーカー情報取得
		form.setMakerList(makerService.getMakerInfo());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * メーカー情報新規登録画面初期表示
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward initRegistryMaker(AppActionMapping appMapping, MakerForm form,
			HttpServletRequest request) throws Exception{

		//インスタンス生成
		MstMakerDTO mstMakerDto = new MstMakerDTO();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryMessageDTO);

		//入力欄初期化
		form.setMakerDto(mstMakerDto);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * メーカー情報新規登録処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward registryMaker(AppActionMapping appMapping, MakerForm form,
			HttpServletRequest request) throws Exception{

		//インスタンス生成
		MakerService makerService = new MakerService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryMessageDTO);

		//入力チェック
		Result<MstMakerDTO> result = makerService.validate(form.getMakerDto().getMakerNm(),
				form.getMakerDto().getMakerNmKana());

		//入力チェック失敗の場合
		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//登録処理実行
		int resultCnt = makerService.registryMaker(form.getMakerDto());
		//実行結果件数が1の場合
		if (resultCnt == RESULT_CNT_REG) {
//			registryDTO.setSuccess();
			registryMessageDTO.setRegistryMessage("登録しました");
			form.setSysMakerId(form.getMakerDto().getSysMakerId());
			form.setRegistryDto(registryMessageDTO);
		}

		//tokenセット
		saveToken(request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * メーカー情報更新処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward updateMaker(AppActionMapping appMapping, MakerForm form,
			HttpServletRequest request) throws Exception{

		//インスタンス生成
		MakerService makerService = new MakerService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryMessageDTO);

		//入力チェック
		Result<MstMakerDTO> result = makerService.validate(form.getMakerDto().getMakerNm(),
				form.getMakerDto().getMakerNmKana());

		//入力チェック失敗の場合
		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//更新処理実行
		int resultCnt = makerService.updateMaker(form.getMakerDto());
		//実行結果件数が1の場合
		if (resultCnt == RESULT_CNT_REG) {

//			registryDTO.setSuccess();
			registryMessageDTO.setRegistryMessage("更新しました");
			form.setRegistryDto(registryMessageDTO);
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * メーカー情報削除処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward deleteMaker(AppActionMapping appMapping, MakerForm form,
			HttpServletRequest request) throws Exception{

		//インスタンス生成
		MakerService makerService = new MakerService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryMessageDTO);

		//削除処理実行
		int resultCnt = makerService.deleteMaker(form.getMakerDto());
		//実行結果件数が1の場合
		if (resultCnt == RESULT_CNT_REG) {
//			registryDTO.setSuccess();
			registryMessageDTO.setRegistryMessage("削除しました");
			form.setRegistryDto(registryMessageDTO);
		}

		//メーカー情報再取得
		form.setMakerList(makerService.getMakerInfo());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * メーカー情報詳細
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	protected ActionForward detailMaker(AppActionMapping appMapping, MakerForm form,
			HttpServletRequest request) throws Exception{

		//インスタンス生成
		MakerService makerService = new MakerService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryMessageDTO);
		form.getMakerDto().setSysMakerId(form.getSysMakerId());

		//メーカー情報取得
		form.setMakerDto(makerService.getMakerDetail(form.getMakerDto()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}















}
