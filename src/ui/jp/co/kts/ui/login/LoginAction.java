package jp.co.kts.ui.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.ui.web.WebFwConst;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendNoticeBoardDTO;
import jp.co.kts.core.SystemSetting;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.login.LoginService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.ui.web.struts.WebConst;


public class LoginAction extends AppBaseAction{
	
	

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginForm form = (LoginForm)appForm;

		if ("/initLogin".equals(appMapping.getPath())) {
			return initLogin(appMapping, form, request);
		} else if ("/login".equals(appMapping.getPath())) {
			return login(appMapping, form, request, response);
		} else if ("/logout".equals(appMapping.getPath())) {
			return logout(appMapping, form, request);
		} else if("/registryNotice".equals(appMapping.getPath())){
			return registryNotice (appMapping, form, request);
		} else if("/updateNotice".equals(appMapping.getPath())){
			return updateNotice (appMapping, form, request);
		} else if("/deleteNotice".equals(appMapping.getPath())){
			return deleteNotice (appMapping, form, request);
		} else if("/initNotice".equals(appMapping.getPath())){
			return initNotice(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);

	}

	protected ActionForward initLogin (AppActionMapping appMapping, LoginForm form,
			HttpServletRequest request) throws Exception {

//		HttpSession session = request.getSession(true);
//		session.invalidate();
		form.setLoginCd(StringUtils.EMPTY);
		form.setPassword(StringUtils.EMPTY);

		form.setVersion(SystemSetting.get("VERSION"));

		// ?????????????????????
		Cookie cookie = getCookie(WebConst.COOKIE_LOGIN_USER_ID_NAME, request);
		if (cookie != null) {
			form.setLoginCd(cookie.getValue());
			form.setSaveLoginCdFlg(true);
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward login(AppActionMapping appMapping, LoginForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoginService service = new LoginService();
		MstUserDTO userDTO = new MstUserDTO();

		/*?????????????????????*/
		Result<MstUserDTO> loginResult = service.validate(form.getLoginCd(),form.getPassword());

		if (!loginResult.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(loginResult.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		/*??????????????????*/
		userDTO = service.getContact(form.getLoginCd(),form.getPassword());

		if (userDTO == null) {
			loginResult.addErrorMessage("LOG00101");
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(loginResult.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// ????????????ID?????????????????????????????????????????????
		if (form.isSaveLoginCdFlg()) {
			// ?????????????????????
			Cookie cookie = getCookie(WebConst.COOKIE_LOGIN_USER_ID_NAME, request);
			if (cookie != null) {
				cookie.setMaxAge(WebConst.COOKIE_MAX_AGE);
				cookie.setValue(userDTO.getLoginCd());
				// ?????????????????????
				response.addCookie(cookie);
			} else {
				cookie = new Cookie(WebConst.COOKIE_LOGIN_USER_ID_NAME, userDTO.getLoginCd());
				cookie.setMaxAge(WebConst.COOKIE_MAX_AGE);
				// ?????????????????????
				response.addCookie(cookie);
			}
		} else {
			Cookie cookie = getCookie(WebConst.COOKIE_LOGIN_USER_ID_NAME, request);
			if (cookie != null) {
				// ?????????????????????(???????????????0?????????)
				cookie.setMaxAge(0);
				// ?????????????????????
				response.addCookie(cookie);
			}
		}

		// DB???????????????UPDATE_USER???????????????
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userDTO.getSysUserId());
		userInfo.setLoginCd(userDTO.getLoginCd());

		userInfo.setFamilyName(userDTO.getUserFamilyNmKanji());
		userInfo.setFirstName(userDTO.getUserFirstNmKanji());

		userInfo.setFamilyNameKana(userDTO.getUserFamilyNmKana());
		userInfo.setFirstNameKana(userDTO.getUserFirstNmKana());
		
		request.getSession().setAttribute("LOGIN_USER_ID", userInfo.getUserId());

		request.getSession().setAttribute(WebFwConst.SESSION_KEY_LOGIN_USER, userInfo);
		ActionContext.setLoginUserInfo(userInfo);

		//????????????????????????????????????
		UserService userService = new UserService();
		form.setUserDTO(userService.getUser(userDTO.getSysUserId()));
		String fullName = (form.getUserDTO().getUserFamilyNmKanji() + " " + form.getUserDTO().getUserFirstNmKanji());
		request.getSession().setAttribute("LOGIN_USER_NAME", fullName);
		
		

		/*  2015/12/15 ooyama ADD START ??????????????????????????????  */

		// ??????????????????????????????
		request.getSession().setAttribute("LOGIN_USER_BTOB_BILL_AUTH",userDTO.getBtobBillAuth());

		/*  2015/12/15 ooyama ADD END ??????????????????????????????  */

		// ?????????????????????????????????
		request.getSession().setAttribute("LOGIN_USER_OVERSEAS_INFO_AUTH", userDTO.getOverseasInfoAuth());
		
		request.getSession().setAttribute("LOGIN_USER_MASTER_LIST", form.getUserDTO().getMstMasterList());

		//??????????????????
		form.setNoticeList(service.getNoticeBoard());
		if(form.getNoticeList() == null){
			form.setNoticeList(new ArrayList<ExtendNoticeBoardDTO>());
		}

		//??????????????????????????????????????????
		form.setNoticeDetail("");

		//form.setLoginDTO(userDTO);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ?????????????????????
	 *
	 */
	protected ActionForward logout(AppActionMapping appMapping, LoginForm form, HttpServletRequest request) throws Exception {

	// ????????????????????????
		HttpSession session = request.getSession();
		session.removeAttribute(WebFwConst.SESSION_KEY_LOGIN_USER);
		form.reset(appMapping, request);

		initLogin(appMapping, form, request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//???????????????????????????
	protected ActionForward registryNotice(AppActionMapping appMapping, LoginForm form,
			HttpServletRequest request) throws Exception {

		LoginService service = new LoginService();
		service.registryNotice(form);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//???????????????????????????
	protected ActionForward updateNotice(AppActionMapping appMapping, LoginForm form,
			HttpServletRequest request) throws Exception {

		LoginService service = new LoginService();
		service.updateNotice(form);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//???????????????????????????
	protected ActionForward deleteNotice(AppActionMapping appMapping, LoginForm form,
			HttpServletRequest request) throws Exception {

		LoginService service = new LoginService();
		service.deleteNotice(form);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initNotice(
			AppActionMapping appMapping,
			LoginForm form,
			HttpServletRequest request) throws Exception {

		LoginService service = new LoginService();
		//??????????????????
		form.setNoticeList(service.getNoticeBoard());
		if(form.getNoticeList() == null){
			form.setNoticeList(new ArrayList<ExtendNoticeBoardDTO>());
		}

		//??????????????????????????????????????????
		form.setNoticeDetail("");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

}
