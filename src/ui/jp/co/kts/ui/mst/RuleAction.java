package jp.co.kts.ui.mst;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.fileExport.ExportRuleListService;
import jp.co.kts.service.mst.RulesDetailService;
import jp.co.kts.service.mst.RulesService;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
		} else if ("/initRegistryRule".equals(appMapping.getPath())) {
			return initRegistryRule(appMapping, form, request);	
		} else if ("/registryRule".equals(appMapping.getPath())) {
			return registryRule(appMapping, form, request);
		} else if ("/editeRule".equals(appMapping.getPath())) {
			return initUpdateRule(appMapping, form, request);	
		} else if ("/updateRule".equals(appMapping.getPath())) {
			return updateRule(appMapping, form, request);
		} else if ("/detailRule".equals(appMapping.getPath())) {
			return detailRule(appMapping, form, request);
		}else if ("/initRegistryRuleDetail".equals(appMapping.getPath())) {
			return initRegistryRuleDetail(appMapping, form, request);	
		}else if ("/registryRuleList".equals(appMapping.getPath())) {
			return registryRuleList(appMapping, form, request);
		}else if ("/deleteRuleList".equals(appMapping.getPath())) {
			return deleteRuleList(appMapping, form, request);
		}else if ("/initUpdateRuleList".equals(appMapping.getPath())) {
			return initUpdateRuleList(appMapping, form, request, response);
		}else if ("/updateRuleList".equals(appMapping.getPath())) {
			return updateRuleList(appMapping, form, request);
		}else if ("/ruleListCsvDownLoad".equals(appMapping.getPath())) {
			return ruleListCsvDownLoad(appMapping, form, request, response);
		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}
	
	protected ActionForward ruleList(AppActionMapping appMapping, RuleForm form, HttpServletRequest request) throws Exception {
		 RulesService service = new RulesService();
		 List<MstRulesDTO> rulesList = service.getRulesList();
		 if(rulesList != null && !rulesList.isEmpty()) {
			 form.setRuleList(service.getRulesList());
			 
		 }
//		 form.setAlertType("0");
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
			form.setAlertType("3");
		}
		return ruleList(appMapping, form, request);
			
	 }
	 protected ActionForward initRegistryRule(AppActionMapping appMapping, RuleForm form,
	            HttpServletRequest request) throws Exception {

		 MstRulesDTO ruleDTO = new MstRulesDTO();
		form.setRuleDTO(ruleDTO);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
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
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		//登録処理実行
		int resultCnt = service.registryRule(form.getRuleDTO());
		//実行結果件数が1の場合
		if (resultCnt == 1) {
			registryMessageDTO.setMessage("["+ form.getRuleDTO().getRuleName() +"] 登録完了いたしました。");
			form.setRuleId(form.getRuleDTO().getRuleId());
			form.setRegistryDto(registryMessageDTO);
			form.setAlertType("1");
		}
		//tokenセット
		saveToken(request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
	protected ActionForward initUpdateRule(AppActionMapping appMapping, RuleForm form,
            HttpServletRequest request) throws Exception {

		RulesService service = new RulesService();
		form.setRuleDTO(service.getRules(form.getRuleId()));
	
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
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
			 registryMessageDTO.setMessage("更新致しました。");
			 form.setRegistryDto(registryMessageDTO);
			 form.setAlertType("2");
		 }
		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
//		 return ruleList(appMapping, form, request);
	}

	protected ActionForward detailRule(AppActionMapping appMapping, RuleForm form, HttpServletRequest request) throws Exception
	{
		RulesDetailService detailService = new RulesDetailService();
		form.setRuleDetailList(detailService.getRuleDetail(form.getRuleId()));
//		form.setAlertType("0");
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
			if (StringUtils.isBlank(dto.getItemCheckFlg())) {
				continue;
			}
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
			form.setAlertType("3");
		}
		return detailRule(appMapping, form, request);
			
	 }
	
	protected ActionForward initRegistryRuleDetail(AppActionMapping appMapping, RuleForm form,
            HttpServletRequest request) throws Exception {

		MstRulesListDTO ruleListDTO = new MstRulesListDTO();
		form.setRuleDetailDTO(ruleListDTO);
	
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
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
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		 }
		 
		//登録処理実行
		int resultCnt = detailService.registryRuleList(form.getRuleDetailDTO(),form.getRuleId());
		//実行結果件数が1の場合
		if (resultCnt == 1) {
			registryMessageDTO.setMessage("["+form.getRuleDetailDTO().getListName()+"] 登録完了いたしました。");
			form.setRegistryDto(registryMessageDTO);
			form.setAlertType("1");
		}
		//tokenセット
		saveToken(request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
	 
	 
	protected ActionForward initUpdateRuleList(AppActionMapping appMapping, RuleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RulesDetailService detailService = new RulesDetailService();
		form.setRuleDetailDTO(detailService.getRuleDetails(form.getRuleListId()));
		
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
	protected ActionForward updateRuleList(AppActionMapping appMapping, RuleForm form,
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
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		 }
		 
		 //更新処理実行
		 int resultCnt = detailService.updateRuleList(form.getRuleDetailDTO(), form.getRuleListId());
		 //実行結果件数が1の場合
		 if (resultCnt == 1) {
			 registryMessageDTO.setMessage("更新致しました。");
			 form.setRegistryDto(registryMessageDTO);
			 form.setAlertType("2");
		 }
		 
		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward ruleListCsvDownLoad(AppActionMapping appMapping, RuleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		ExportRuleListService exportService = new ExportRuleListService();
		
		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.RULE_LIST_TEMPLATE_PATH);
		
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		
		// ワークブックを読み込みます。
		HSSFWorkbook workBook = new HSSFWorkbook(filein);
		
		workBook = exportService.getExportRuleList(form.getRuleList(), workBook);
		String fname = "ID_PASS_" + date + ".xls";
		
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// エクセルファイル出力
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);
		
		ServletOutputStream fileOutHssf = response.getOutputStream();
		workBook.write(fileOutHssf);
		exportService.close();
		fileOutHssf.close();

		return null;
	}
}
