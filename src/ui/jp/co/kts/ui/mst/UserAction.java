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
import jp.co.kts.app.common.entity.MstMasterDTO;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.mst.RulesDetailService;
import jp.co.kts.service.mst.RulesService;
import jp.co.kts.service.mst.UserService;
import net.arnx.jsonic.JSON;

import org.apache.struts.action.ActionForward;

public class UserAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserForm form = (UserForm)appForm;

		if ("/initUserList".equals(appMapping.getPath())) {
			return userList(appMapping, form, request);
		} else if ("/detailUser".equals(appMapping.getPath())) {
			return  detailUser(appMapping, form, request);
		} else if ("/updateUser".equals(appMapping.getPath())) {
			 return updateUser(appMapping, form, request);
		} else if ("/goUpdateExtraUserRule".equals(appMapping.getPath())) {
			 return goUpdateExtraUserRule(appMapping, form, request);	 
		} else if ("/updateAllUserList".equals(appMapping.getPath())) {
			 return updateAllUserList(appMapping, form, request);
		} else if ("/backViewList".equals(appMapping.getPath())) {
			 return backViewList(appMapping, form, request);	 
		} else if ("/editAllUserList".equals(appMapping.getPath())) {
			 return editAllUserList(appMapping, form, request);	 
		} else if ("/deleteUser".equals(appMapping.getPath())) {
			 return deleteUser(appMapping, form, request);
		} else if ("/initRegistryUser".equals(appMapping.getPath())) {
			 return initRegistryUser(appMapping, form, request);
		} else if ("/registryUser".equals(appMapping.getPath())) {
			 return registryUser(appMapping, form, request);
		}else if ("/saveExtraRuleDetailByUserId".equals(appMapping.getPath())) {
			 return saveExtraRuleDetailByUserId(appMapping, form, request, response);
		}


		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	 protected ActionForward userList(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		 UserService service = new UserService();
		 RulesService ruleService = new RulesService();

		 List<MstUserDTO> userList = service.getUserList();
		 form.setUserList(userList);
		 form.setRuleList(ruleService.getRulesList());
		 
		 form.setIsEditModeAll(0);

		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }

	 protected ActionForward detailUser(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		 UserService service = new UserService();
		 form.setUserDTO(service.getUser(form.getSysUserId()));

		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }

	 protected ActionForward updateUser(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		 UserService service = new UserService();

		/*入力値チェック*/
		Result<MstUserDTO> result = service.validate(form.getUserDTO().getLoginCd(), form.getUserDTO().getPassword(),form.getUserDTO().getSysUserId());

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		 service.updateUser(form.getUserDTO());

		 form.setAlertType("2");
		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }
	 
	 protected ActionForward goUpdateExtraUserRule(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		UserService userService = new UserService();
		RulesService ruleService = new RulesService();
		
		for (MstUserDTO userDto : form.getUserList()) {
			
			if(userDto.getSysUserId() == form.getSysUserId()) {
				for (MstRulesDTO ruleDto : userDto.getMstRulesList()) {
					
					if(!ruleDto.getChildrenRuleCheckedFlag().equals("-1")) {
						ruleService.updateExtraRule(ruleDto, userDto.getSysUserId());
					}
				}
				
				for (MstMasterDTO masterDto : userDto.getMstMasterList()) {
					if(!masterDto.getChildrenMasterCheckedFlag().equals("-1")) {
						masterDto.setSysUserId(form.getSysUserId());
						masterDto.setUserListFlg("1");
						masterDto.setRuleListFlg("1");
						masterDto.setCorporationListFlg("1");
						masterDto.setAccountListFlg("1");
						masterDto.setChannelListFlg("1");
						masterDto.setWarehouseListFlg("1");
						masterDto.setMakerListFlg("1");
						masterDto.setSetItemListFlg("1");
						masterDto.setClientListFlg("1");
						masterDto.setDeliveryListFlg("1");
						userService.updateMasterByUser(masterDto);
					}
				}
				userService.updateUserMainRule(userDto);	
			}
		}
		
		form.setAlertType("2");
		form.setIsEditModeAll(0);
		form.setSysUserId(0);
		return userList(appMapping, form, request);
	 }
	 
	 protected ActionForward updateAllUserList(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		UserService userService = new UserService();
		RulesService ruleService = new RulesService();
		
		for (MstUserDTO userDto : form.getUserList()) {
			
			for (MstRulesDTO ruleDto : userDto.getMstRulesList()) {
				
				if(!ruleDto.getChildrenRuleCheckedFlag().equals("-1")) {
					ruleService.updateExtraRule(ruleDto, userDto.getSysUserId());
				}
			}
			for (MstMasterDTO masterDto : userDto.getMstMasterList()) {
				if(!masterDto.getChildrenMasterCheckedFlag().equals("-1")) {
					userService.updateMasterByUser(masterDto);
				}
			}
			userService.updateUserMainRule(userDto);
		}
		
		 form.setAlertType("2");
		 form.setIsEditModeAll(0);
		 return userList(appMapping, form, request);
	 }
	 
	 protected ActionForward editAllUserList(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		 UserService service = new UserService();
		 RulesService ruleService = new RulesService();

		 List<MstUserDTO> userList = service.getUserList();
		 form.setUserList(userList);
		 form.setRuleList(ruleService.getRulesList());
		 form.setIsEditModeAll(1);

		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }
	 
	 protected ActionForward backViewList(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {
		 
		 form.setIsEditModeAll(0);
		 return userList(appMapping, form, request);
	 }
	 protected ActionForward deleteUser(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		UserService service = new UserService();
		service.deleteUser(form.getSysUserId());
//		service.deleteUser(form.getUserDTO().getSysUserId());

		MstUserDTO userDTO = new MstUserDTO();
		form.setUserDTO(userDTO);
		form.setAlertType("3");

		//削除後の一覧を再取得する
		form.setUserList(service.getUserList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }

	 protected ActionForward initRegistryUser(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		MstUserDTO userDTO = new MstUserDTO();
		form.setUserDTO(userDTO);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }

	 protected ActionForward registryUser(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		UserService service = new UserService();

		/*入力値チェック*/
		Result<MstUserDTO> result = service.validate(form.getUserDTO().getLoginCd(), form.getUserDTO().getPassword(),form.getUserDTO().getSysUserId());

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		service.registryUser(form.getUserDTO());

		MstUserDTO userDTO = new MstUserDTO();
		form.setUserDTO(userDTO);
		form.setAlertType("1");

		//登録後の一覧を再取得する
		form.setUserList(service.getUserList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }
	 protected ActionForward saveExtraRuleDetailByUserId(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request,  HttpServletResponse response) throws Exception {
		 
		UserService userService = new UserService();
		RulesDetailService dService = new RulesDetailService();
		
		for (MstUserDTO userDto : form.getUserList()) {
			
			if(userDto.getSysUserId() == form.getSysUserId() && form.getRuleId() != 0) {
				for (MstRulesDTO ruleDto : userDto.getMstRulesList()) {
					if(ruleDto.getRuleId() == form.getRuleId()) {
						int index = 0;
						for (MstRulesListDTO dDto : ruleDto.getMstRulesDetailList()) {
							dService.updateExtraRuleDetail(dDto, userDto.getSysUserId(), form.getRuleId(), form.getRuleDetailList()[index]);
							index++;
						}
					}
				}
			}
			else if(userDto.getSysUserId() == form.getSysUserId() && form.getRuleId() == 0) {
				for (MstMasterDTO masterDto : userDto.getMstMasterList()) {
					masterDto.setSysUserId(form.getSysUserId());
					masterDto.setUserListFlg(form.getRuleDetailList()[0] > 0 ? "1" : "0");
					masterDto.setRuleListFlg(form.getRuleDetailList()[1] > 0 ? "1" : "0");
					masterDto.setCorporationListFlg(form.getRuleDetailList()[2] > 0 ? "1" : "0");
					masterDto.setAccountListFlg(form.getRuleDetailList()[3] > 0 ? "1" : "0");
					masterDto.setChannelListFlg(form.getRuleDetailList()[4] > 0 ? "1" : "0");
					masterDto.setWarehouseListFlg(form.getRuleDetailList()[5] > 0 ? "1" : "0");
					masterDto.setMakerListFlg(form.getRuleDetailList()[6] > 0 ? "1" : "0");
					masterDto.setSetItemListFlg(form.getRuleDetailList()[7] > 0 ? "1" : "0");
					masterDto.setClientListFlg(form.getRuleDetailList()[8] > 0 ? "1" : "0");
					masterDto.setDeliveryListFlg(form.getRuleDetailList()[9] > 0 ? "1" : "0");
					userService.updateMasterByUser(masterDto);
				}
			}
		}
		return null;
//		if (itemList.size() == 0) {
//			PrintWriter printWriter = response.getWriter();
//			printWriter.print(JSON.encode(""));
//		} else {
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter printWriter = response.getWriter();
//			printWriter.print(JSON.encode("success"));
//		}
//		response.setCharacterEncoding("UTF-8");
//		PrintWriter printWriter = response.getWriter();
//		printWriter.print(JSON.encode("success"));
//		return null;
	 }

}
