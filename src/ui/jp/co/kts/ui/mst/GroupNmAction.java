package jp.co.kts.ui.mst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstGroupNmDTO;
import jp.co.kts.service.mst.GroupNmService;
import jp.co.kts.service.mst.LargeGroupService;
import jp.co.kts.service.mst.MiddleGroupService;
import jp.co.kts.service.mst.SmallGroupService;

import org.apache.struts.action.ActionForward;

public class GroupNmAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		GroupNmForm form = (GroupNmForm)appForm;

		//分類
		if ("/groupNmList".equals(appMapping.getPath())) {
			return groupNmList(appMapping, form, request);
		} else if ("/detailGroupNm".equals(appMapping.getPath())) {
			return  detailGroupNm(appMapping, form, request);
		} else if ("/updateGroupNm".equals(appMapping.getPath())) {
			 return updateGroupNm(appMapping, form, request);
		} else if ("/deleteGroupNm".equals(appMapping.getPath())) {
			 return deleteGroupNm(appMapping, form, request);
		} else if ("/initRegistryGroupNm".equals(appMapping.getPath())) {
			 return initRegistryGroupNm(appMapping, form, request);
		} else if ("/registryGroupNm".equals(appMapping.getPath())) {
			 return registryGroupNm(appMapping, form, request);
		}
		//大・中・小分類
//		else if ("/initGroupNmList".equals(appMapping.getPath())) {
//			return groupNmList(appMapping, form, request);
//		} else if ("/detailGroupNm".equals(appMapping.getPath())) {
//			return  detailGroupNm(appMapping, form, request);
//		} else if ("/updateGroupNm".equals(appMapping.getPath())) {
//			 return updateGroupNm(appMapping, form, request);
//		} else if ("/deleteGroupNm".equals(appMapping.getPath())) {
//			 return deleteGroupNm(appMapping, form, request);
//		} else if ("/initRegistryGroupNm".equals(appMapping.getPath())) {
//			 return initRegistryGroupNm(appMapping, form, request);
//		} else if ("/registryGroupNm".equals(appMapping.getPath())) {
//			 return registryGroupNm(appMapping, form, request);
//		} else if ("/registryVariousGroupNm".equals(appMapping.getPath())) {
//			return registryVariousGroupNm(appMapping, form, request);
//		} else if ( ){
//
//		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

		 protected ActionForward groupNmList(AppActionMapping appMapping, GroupNmForm form,
		            HttpServletRequest request) throws Exception {

			 GroupNmService service = new GroupNmService();

			 form.setGroupNmList(service.getGroupNmList());

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }


		protected ActionForward detailGroupNm(AppActionMapping appMapping, GroupNmForm form,
		            HttpServletRequest request) throws Exception {

			GroupNmService service = new GroupNmService();

			form.setGroupNmDTO(service.getGroupNm(form.getSysGroupNmId()));
			variousListSet(form);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}

		 protected ActionForward updateGroupNm(AppActionMapping appMapping, GroupNmForm form,
		            HttpServletRequest request) throws Exception {

			 GroupNmService service = new GroupNmService();
			 service.updateGroupNm(form.getGroupNmDTO());
			 form.setAlertType("2");
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		protected ActionForward deleteGroupNm(AppActionMapping appMapping, GroupNmForm form,
				HttpServletRequest request) throws Exception {

			GroupNmService service = new GroupNmService();
			service.deleteGroupNm(form.getGroupNmDTO().getSysGroupNmId());

			MstGroupNmDTO groupNmDTO = new MstGroupNmDTO();
			form.setGroupNmDTO(groupNmDTO);
			form.setAlertType("3");

			//削除後再検索
			form.setGroupNmList(service.getGroupNmList());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}

		 protected ActionForward initRegistryGroupNm(AppActionMapping appMapping, GroupNmForm form,
		            HttpServletRequest request) throws Exception {

//			MstGroupNmDTO groupNmDTO = new MstGroupNmDTO();

			variousListSet(form);

			form.setGroupNmDTO(new MstGroupNmDTO());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 private void variousListSet(GroupNmForm form) throws DaoException {

			 LargeGroupService largeGroupService = new LargeGroupService();
			 form.setLargeGroupList(largeGroupService.getLargeGroupList());
			 MiddleGroupService middleGroupService = new MiddleGroupService();
			 form.setMiddleGroupList(middleGroupService.getMiddleGroupList());
			 SmallGroupService smallGroupService = new SmallGroupService();
			 form.setSmallGroupList(smallGroupService.getSmallGroupList());
		 }

		protected ActionForward registryGroupNm(AppActionMapping appMapping, GroupNmForm form,
		            HttpServletRequest request) throws Exception {

			 GroupNmService service = new GroupNmService();
			 service.registryGroupNm(form.getGroupNmDTO());
			 form.setAlertType("1");

			//登録後再検索
			form.setGroupNmList(service.getGroupNmList());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }
}
