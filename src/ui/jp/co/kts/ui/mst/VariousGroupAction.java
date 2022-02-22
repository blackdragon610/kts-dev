package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstLargeGroupDTO;
import jp.co.kts.app.common.entity.MstMiddleGroupDTO;
import jp.co.kts.app.common.entity.MstSmallGroupDTO;
import jp.co.kts.app.extendCommon.entity.VariousGroupDTO;
import jp.co.kts.service.mst.LargeGroupService;
import jp.co.kts.service.mst.MiddleGroupService;
import jp.co.kts.service.mst.SmallGroupService;
import jp.co.kts.ui.web.struts.WebConst;

import org.apache.struts.action.ActionForward;

public class VariousGroupAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		VariousGroupForm form = (VariousGroupForm)appForm;

		if ("/initVariousGroupList".equals(appMapping.getPath())) {
			return initVariousGroupList(appMapping, form, request);
		} else if ("/variousGroupList".equals(appMapping.getPath())) {
			return variousGroupList(appMapping, form, request);
		} else if ("/detailVariousGroup".equals(appMapping.getPath())) {
			return  detailVariousGroup(appMapping, form, request);
		} else if ("/updateVariousGroup".equals(appMapping.getPath())) {
			 return updateVariousGroup(appMapping, form, request);
		} else if ("/deleteVariousGroup".equals(appMapping.getPath())) {
			 return deleteVariousGroup(appMapping, form, request);
		} else if ("/initRegistryVariousGroup".equals(appMapping.getPath())) {
			 return initRegistryVariousGroup(appMapping, form, request);
		} else if ("/registryVariousGroup".equals(appMapping.getPath())) {
			 return registryVariousGroup(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}



	private ActionForward initVariousGroupList(AppActionMapping appMapping,
			VariousGroupForm form, HttpServletRequest request) {

		form.setGroupSelect(0);

		groupListIntialize(form);



		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private void groupListIntialize(VariousGroupForm form) {

		List<VariousGroupDTO> list = new ArrayList<>();
		form.setVariousGroupList(list);
		List<MstLargeGroupDTO> largeList = new ArrayList<>();
		form.setLargeGroupList(largeList);
		List<MstMiddleGroupDTO> middleList = new ArrayList<>();
		form.setMiddleGroupList(middleList);
		List<MstSmallGroupDTO> smallList = new ArrayList<>();
		form.setSmallGroupList(smallList);
	}



	private ActionForward variousGroupList(AppActionMapping appMapping,
			VariousGroupForm form, HttpServletRequest request) throws DaoException {


//		VariousGroupService variousGroupService = new VariousGroupService();
//		form.setVariousGroupList(variousGroupService.getVariousGroupList(form.getGroupSelect()));

		groupListIntialize(form);
		if (form.getGroupSelect() == 1) {

			LargeGroupService largeGroupService = new LargeGroupService();
			form.setLargeGroupList(largeGroupService.getLargeGroupList());
//			form.setVariousGroupList(largeGroupService.getLargeGroupList());

		} else if (form.getGroupSelect() == 2) {

			MiddleGroupService middleGroupService = new MiddleGroupService();
			form.setMiddleGroupList(middleGroupService.getMiddleGroupList());
//			form.setGroupList(middleGroupService.getMiddleGroupList());

		} else if (form.getGroupSelect() == 3) {


			SmallGroupService smallGroupService = new SmallGroupService();
			form.setSmallGroupList(smallGroupService.getSmallGroupList());
//			form.setGroupList(smallGroupService.getSmallGroupList());
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward detailVariousGroup(AppActionMapping appMapping,
			VariousGroupForm form, HttpServletRequest request) throws DaoException {

//		VariousGroupService variousGroupService = new VariousGroupService();
//
//		form.setVariousGroupDTO(variousGroupService.getVariousGroup(form.getSysVariousGroupId(), form.getGroupSelect()));

		groupDTOSetNull(form);
		if (form.getGroupSelect() == 1) {

			LargeGroupService largeGroupService = new LargeGroupService();
//			form.setVariousGroupDTO(largeGroupService.getLargeGroup(form.getSysVariousGroupId()));
			form.setLargeGroupDTO(largeGroupService.getLargeGroup(form.getSysVariousGroupId()));

		} else if (form.getGroupSelect() == 2) {

			MiddleGroupService middleGroupService = new MiddleGroupService();
			form.setMiddleGroupDTO(middleGroupService.getMiddleGroup(form.getSysVariousGroupId()));

		} else if (form.getGroupSelect() == 3) {

			SmallGroupService smallGroupService = new SmallGroupService();
			form.setSmallGroupDTO(smallGroupService.getSmallGroup(form.getSysVariousGroupId()));
		}

		form.setGroupSelectName(WebConst.GROUP_MAP.get(form.getGroupSelect()));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward updateVariousGroup(AppActionMapping appMapping,
			VariousGroupForm form, HttpServletRequest request) throws DaoException {

//		VariousGroupService variousGroupService = new VariousGroupService();
//
//		variousGroupService.updateVariousGroup(form.getVariousGroupDTO(), form.getGroupSelect());


		if (form.getGroupSelect() == 1) {

			LargeGroupService largeGroupService = new LargeGroupService();
			largeGroupService.updateLargeGroup(form.getLargeGroupDTO());
//			largeGroupService.updateLargeGroup(form.getVariousGroupDTO());

		} else if (form.getGroupSelect() == 2) {

			MiddleGroupService middleGroupService = new MiddleGroupService();
			middleGroupService.updateMiddleGroup(form.getMiddleGroupDTO());

		} else if (form.getGroupSelect() == 3) {

			SmallGroupService smallGroupService = new SmallGroupService();
			smallGroupService.updateSmallGroup(form.getSmallGroupDTO());
		}
		form.setAlertType("2");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward deleteVariousGroup(AppActionMapping appMapping,
			VariousGroupForm form, HttpServletRequest request) throws DaoException {

//		VariousGroupService variousGroupService = new VariousGroupService();
//
//		variousGroupService.deleteVariousGroup(form.getVariousGroupDTO().getSysVariousGroupId(), form.getGroupSelect());

		if (form.getGroupSelect() == 1) {

			LargeGroupService largeGroupService = new LargeGroupService();
			largeGroupService.deleteLargeGroup(form.getLargeGroupDTO().getSysLargeGroupId());
			form.setLargeGroupDTO(new MstLargeGroupDTO());
//			largeGroupService.deleteLargeGroup(form.getVariousGroupDTO().getSysVariousGroupId());

			//削除後再検索
			form.setLargeGroupList(largeGroupService.getLargeGroupList());
		} else if (form.getGroupSelect() == 2) {

			MiddleGroupService middleGroupService = new MiddleGroupService();
			middleGroupService.deleteMiddleGroup(form.getMiddleGroupDTO().getSysMiddleGroupId());
			form.setMiddleGroupDTO(new MstMiddleGroupDTO());

			//削除後再検索
			form.setMiddleGroupList(middleGroupService.getMiddleGroupList());
		} else if (form.getGroupSelect() == 3) {

			SmallGroupService smallGroupService = new SmallGroupService();
			smallGroupService.deleteSmallGroup(form.getSmallGroupDTO().getSysSmallGroupId());
			form.setSmallGroupDTO(new MstSmallGroupDTO());

			//削除後再検索
			form.setSmallGroupList(smallGroupService.getSmallGroupList());
		}
		form.setSysVariousGroupId(0);
		form.setVariousGroupDTO(new VariousGroupDTO());
		form.setAlertType("3");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward initRegistryVariousGroup(
			AppActionMapping appMapping, VariousGroupForm form,
			HttpServletRequest request) {

//		form.setGroupSelect(0);
		groupDTOSetNull(form);

		if (form.getGroupSelect() == 1) {
			form.setLargeGroupDTO(new MstLargeGroupDTO());
		} else if (form.getGroupSelect() == 2) {
			form.setMiddleGroupDTO(new MstMiddleGroupDTO());
		} else if (form.getGroupSelect() == 3) {
			form.setSmallGroupDTO(new MstSmallGroupDTO());
		}
		form.setGroupSelectName(WebConst.GROUP_MAP.get(form.getGroupSelect()));
		form.setVariousGroupDTO(new VariousGroupDTO());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private void groupDTOSetNull(VariousGroupForm form) {

		form.setLargeGroupDTO(null);
		form.setMiddleGroupDTO(null);
		form.setSmallGroupDTO(null);
	}


	private ActionForward registryVariousGroup(AppActionMapping appMapping,
			VariousGroupForm form, HttpServletRequest request) throws DaoException {


//		VariousGroupService variousGroupService = new VariousGroupService();

//		variousGroupService.registryVariousGroup(form.getVariousGroupDTO(), form.getGroupSelect());

		if (form.getGroupSelect() == 1) {

			LargeGroupService largeGroupService = new LargeGroupService();
			largeGroupService.registryLargeGroup(form.getLargeGroupDTO());
			form.setSysVariousGroupId(form.getLargeGroupDTO().getSysLargeGroupId());
//			largeGroupService.registryLargeGroup(form.getVariousGroupDTO());

			//登録後再検索
			form.setLargeGroupList(largeGroupService.getLargeGroupList());

		} else if (form.getGroupSelect() == 2) {

			MiddleGroupService middleGroupService = new MiddleGroupService();
			middleGroupService.registryMiddleGroup(form.getMiddleGroupDTO());
			form.setSysVariousGroupId(form.getMiddleGroupDTO().getSysMiddleGroupId());
//			middleGroupService.registryMiddleGroup(form.getVariousGroupDTO());

			//登録後再検索
			form.setMiddleGroupList(middleGroupService.getMiddleGroupList());

		} else if (form.getGroupSelect() == 3) {


			SmallGroupService smallGroupService = new SmallGroupService();
			smallGroupService.registrySmallGroup(form.getSmallGroupDTO());
			form.setSysVariousGroupId(form.getSmallGroupDTO().getSysSmallGroupId());
//			smallGroupService.registrySmallGroup(form.getVariousGroupDTO());

			//登録後再検索
			form.setSmallGroupList(smallGroupService.getSmallGroupList());
		}
		 form.setAlertType("1");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


}
