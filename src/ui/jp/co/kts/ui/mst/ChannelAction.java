package jp.co.kts.ui.mst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstChannelDTO;
import jp.co.kts.service.mst.ChannelService;

public class ChannelAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ChannelForm form = (ChannelForm)appForm;

		if ("/initChannelList".equals(appMapping.getPath())) {
			return channelList(appMapping, form, request);
		} else if ("/detailChannel".equals(appMapping.getPath())) {
			return  detailChannel(appMapping, form, request);
		} else if ("/updateChannel".equals(appMapping.getPath())) {
			 return updateChannel(appMapping, form, request);
		} else if ("/deleteChannel".equals(appMapping.getPath())) {
			 return deleteChannel(appMapping, form, request);
		} else if ("/initRegistryChannel".equals(appMapping.getPath())) {
			 return initRegistryChannel(appMapping, form, request);
		} else if ("/registryChannel".equals(appMapping.getPath())) {
			 return registryChannel(appMapping, form, request);
		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

		 protected ActionForward channelList(AppActionMapping appMapping, ChannelForm form,
		            HttpServletRequest request) throws Exception {

			 ChannelService service = new ChannelService();

			 form.setChannelList(service.getChannelList());

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }


		 protected ActionForward detailChannel(AppActionMapping appMapping, ChannelForm form,
		            HttpServletRequest request) throws Exception {

			 ChannelService service = new ChannelService();
			 form.setChannelDTO(service.getChannel(form.getSysChannelId()));

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 protected ActionForward updateChannel(AppActionMapping appMapping, ChannelForm form,
		            HttpServletRequest request) throws Exception {

			 ChannelService service = new ChannelService();
			 service.updateChannel(form.getChannelDTO());
			 form.setAlertType("2");
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 protected ActionForward deleteChannel(AppActionMapping appMapping, ChannelForm form,
		            HttpServletRequest request) throws Exception {

			ChannelService service = new ChannelService();
			service.deleteChannel(form.getChannelDTO().getSysChannelId());

			MstChannelDTO channelDTO = new MstChannelDTO();
			form.setChannelDTO(channelDTO);
			form.setAlertType("3");

			//削除後再検索
			form.setChannelList(service.getChannelList());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 protected ActionForward initRegistryChannel(AppActionMapping appMapping, ChannelForm form,
		            HttpServletRequest request) throws Exception {

			MstChannelDTO channelDTO = new MstChannelDTO();
			form.setChannelDTO(channelDTO);

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 /**
		  * 登録
		  * @param appMapping
		  * @param form
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 protected ActionForward registryChannel(AppActionMapping appMapping, ChannelForm form,
		            HttpServletRequest request) throws Exception {

			ChannelService service = new ChannelService();
			service.registryChannel(form.getChannelDTO());
			form.setAlertType("1");

			//登録後再検索
			form.setChannelList(service.getChannelList());

			//tokenセット
			saveToken(request);

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }
}
