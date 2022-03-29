package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.mst.UserService;

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
		} else if ("/deleteUser".equals(appMapping.getPath())) {
			 return deleteUser(appMapping, form, request);
		} else if ("/initRegistryUser".equals(appMapping.getPath())) {
			 return initRegistryUser(appMapping, form, request);
		} else if ("/registryUser".equals(appMapping.getPath())) {
			 return registryUser(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	 protected ActionForward userList(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		 UserService service = new UserService();

		 form.setUserList(service.getUserList());

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

	 protected ActionForward deleteUser(AppActionMapping appMapping, UserForm form,
	            HttpServletRequest request) throws Exception {

		UserService service = new UserService();
		service.deleteUser(form.getUserDTO().getSysUserId());

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
}
