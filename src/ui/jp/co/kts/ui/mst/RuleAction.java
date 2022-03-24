package jp.co.kts.ui.mst;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.mst.RulesDetailService;
import jp.co.kts.service.mst.RulesService;
import net.arnx.jsonic.JSON;

import org.apache.struts.action.ActionForward;

public class RuleAction extends AppBaseAction {
	
	private static final String ORDER_CHECK_FLG_ON = "on";

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RuleForm form = (RuleForm)appForm;
		if ("/ruleList".equals(appMapping.getPath())) {
			return  ruleList(appMapping, form, request);
		} else if ("/ruleItemDelete".equals(appMapping.getPath())) {
			return ruleItemDelete(appMapping, form, request);
		} else if ("/registryRule".equals(appMapping.getPath())) {
			return registryRule(appMapping, form, request);
		} else if ("/updateRule".equals(appMapping.getPath())) {
			return updateRule(appMapping, form, request);
		} else if ("/detailRule".equals(appMapping.getPath())) {
			return detailRule(appMapping, form, request);
		}else if ("/registryRuleList".equals(appMapping.getPath())) {
			return registryRuleList(appMapping, form, request);
		}else if ("/deleteRuleList".equals(appMapping.getPath())) {
			return deleteRuleList(appMapping, form, request);
		}else if ("/initUpdateRuleList".equals(appMapping.getPath())) {
			return initUpdateRuleList(appMapping, form, request, response);
		}else if ("/updateRuleList".equals(appMapping.getPath())) {
			return updateRuleList(appMapping, form, request);
		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}
	
	protected ActionForward ruleList(AppActionMapping appMapping, RuleForm form, HttpServletRequest request) throws Exception {
		 RulesService service = new RulesService();
		 List<MstRulesDTO> rulesList = service.getRulesList();
		 if(rulesList != null && !rulesList.isEmpty()) {
			 form.setRuleList(service.getRulesList());
			 form.setAlertType("0");
		 }
		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }
	 
	 protected ActionForward ruleItemDelete(AppActionMapping appMapping, RuleForm form, HttpServletRequest request) throws Exception {
		//削除対象件数
		int targetCnt = 0;
		//削除結果件数
		int resultCnt = 0;
		//インスタンス生成
		RulesService service = new RulesService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		Map<Long, Long> checkRuleNo = new HashMap<Long, Long>();
		List<Long> targetRuleId = new ArrayList<Long>();
		//削除する商品の件数をカウントしておく
		for (MstRulesDTO dto : form.getRuleList()) {
			if (dto.getItemCheckFlg().equals(ORDER_CHECK_FLG_ON)) {
				targetCnt++;
			}
			//伝票番号が重複したら最初に戻る
			if (checkRuleNo.containsKey(dto.getRuleId())) {
				continue;
			}
			//削除商品の伝票番号を保持
			checkRuleNo.put(dto.getRuleId(), dto.getRuleId());
			targetRuleId.add(dto.getRuleId());
		}
		 //削除実行
		 resultCnt = service.ruleItemDelete(form.getRuleList());

		if (targetCnt != resultCnt) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("削除に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("対象の削除が完了しました。");
			form.setMessageFlg("1");
			form.setRegistryDto(messageDTO);
		}
		return ruleList(appMapping, form, request);
			
	 }
	protected ActionForward registryRule(AppActionMapping appMapping, RuleForm form,
		HttpServletRequest request) throws Exception{
		//インスタンス生成
		RulesService service = new RulesService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryMessageDTO);
		//入力チェック
		Result<MstRulesDTO> result = service.validate(form.getRuleDTO().getRuleName());
		//入力チェック失敗の場合
		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			form.setAlertType("3");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		//登録処理実行
		int resultCnt = service.registryRule(form.getRuleDTO());
		//実行結果件数が1の場合
		if (resultCnt == 1) {
			registryMessageDTO.setRegistryMessage("登録しました");
			form.setRuleId(form.getRuleDTO().getRuleId());
			form.setRegistryDto(registryMessageDTO);
		}
		//tokenセット
		saveToken(request);
		return ruleList(appMapping, form, request);
	}
	
	protected ActionForward updateRule(AppActionMapping appMapping, RuleForm form,
			HttpServletRequest request) throws Exception{
		 //インスタンス生成
		 RulesService service = new RulesService();
		 RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		 //メッセージ初期化
		 form.setRegistryDto(registryMessageDTO);
		 //入力チェック
		 Result<MstRulesDTO> result = service.validate(form.getRuleDTO().getRuleName());
		 //入力チェック失敗の場合
		 if (!result.isSuccess()) {
			 List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			 messages.addAll(result.getErrorMessages());
			 saveErrorMessages(request, messages);
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		 }
		 //更新処理実行
		 int resultCnt = service.updateRule(form.getRuleDTO(), form.getRuleId());
		 //実行結果件数が1の場合
		 if (resultCnt == 1) {
			 registryMessageDTO.setRegistryMessage("更新しました");
			 form.setRegistryDto(registryMessageDTO);
		 }
		 return ruleList(appMapping, form, request);
	}

	protected ActionForward detailRule(AppActionMapping appMapping, RuleForm form, HttpServletRequest request) throws Exception
	{
		RulesDetailService detailService = new RulesDetailService();
		form.setRuleDetailList(detailService.getRuleDetail(form.getRuleId()));
		form.setAlertType("0");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
	
	protected ActionForward deleteRuleList(AppActionMapping appMapping, RuleForm form, HttpServletRequest request) throws Exception {
		//削除対象件数
		int targetCnt = 0;
		//削除結果件数
		int resultCnt = 0;
		//インスタンス生成
		RulesDetailService detailService = new RulesDetailService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		Map<Long, Long> checkNo = new HashMap<Long, Long>();
		List<Long> targetRuleListId = new ArrayList<Long>();
		//削除する商品の件数をカウントしておく
		for (MstRulesListDTO dto : form.getRuleDetailList()) {
			if (dto.getItemCheckFlg().equals(ORDER_CHECK_FLG_ON)) {
				targetCnt++;
			}
			//伝票番号が重複したら最初に戻る
			if (checkNo.containsKey(dto.getRuleListId())) {
				continue;
			}
			//削除商品の伝票番号を保持
			checkNo.put(dto.getRuleListId(), dto.getRuleListId());
			targetRuleListId.add(dto.getRuleListId());
		}
		 //削除実行
		 resultCnt = detailService.ruleListItemDelete(form.getRuleDetailList());

		if (targetCnt != resultCnt) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("削除に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("対象の削除が完了しました。");
			form.setMessageFlg("1");
			form.setRegistryDto(messageDTO);
		}
		return detailRule(appMapping, form, request);
			
	 }
	protected ActionForward registryRuleList(AppActionMapping appMapping, RuleForm form,
			HttpServletRequest request) throws Exception{
		//インスタンス生成
		RulesDetailService detailService = new RulesDetailService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		 //入力チェック
		 Result<MstRulesDTO> result = detailService.validate(form.getRuleDetailDTO());
		 
		 //入力チェック失敗の場合
		 if (!result.isSuccess()) {
			 List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			 messages.addAll(result.getErrorMessages());
			 saveErrorMessages(request, messages);
			 form.setAlertType("3");
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		 }
		 
		//登録処理実行
		int resultCnt = detailService.registryRuleList(form.getRuleDetailDTO(),form.getRuleId());
		//実行結果件数が1の場合
		if (resultCnt == 1) {
			registryMessageDTO.setRegistryMessage("登録しました");
			form.setRegistryDto(registryMessageDTO);
		}
		//tokenセット
		saveToken(request);
		return detailRule(appMapping, form, request);
	}
	protected ActionForward initUpdateRuleList(AppActionMapping appMapping, RuleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RulesDetailService detailService = new RulesDetailService();
		List<MstRulesListDTO> itemList = detailService.getRuleDetailById(form.getRuleId(), form.getRuleListId());
		
		if (itemList.size() == 0) {
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(""));
		} else {
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(itemList));
		}

		return null;
	}
	protected ActionForward updateRuleList(AppActionMapping appMapping, RuleForm form,
			HttpServletRequest request) throws Exception{
		 //インスタンス生成
		RulesDetailService detailService = new RulesDetailService();
		 RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		 
		 //更新処理実行
		 int resultCnt = detailService.updateRuleList(form.getRuleDetailDTO(), form.getRuleListId());
		 //実行結果件数が1の場合
		 if (resultCnt == 1) {
			 registryMessageDTO.setRegistryMessage("更新しました");
			 form.setRegistryDto(registryMessageDTO);
		 }
		 return detailRule(appMapping, form, request);
	}

	
}
