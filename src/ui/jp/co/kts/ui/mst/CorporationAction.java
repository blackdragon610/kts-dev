package jp.co.kts.ui.mst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.service.mst.CorporationService;

import org.apache.struts.action.ActionForward;

public class CorporationAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CorporationForm form = (CorporationForm)appForm;

		if ("/initCorporationList".equals(appMapping.getPath())) {
			return corporationList(appMapping, form, request);
		} else if ("/detailCorporation".equals(appMapping.getPath())) {
			return  detailCorporation(appMapping, form, request);
		} else if ("/updateCorporation".equals(appMapping.getPath())) {
			 return updateCorporation(appMapping, form, request);
		} else if ("/deleteCorporation".equals(appMapping.getPath())) {
			 return deleteCorporation(appMapping, form, request);
		} else if ("/initRegistryCorporation".equals(appMapping.getPath())) {
			 return initRegistryCorporation(appMapping, form, request);
		} else if ("/registryCorporation".equals(appMapping.getPath())) {
			 return registryCorporation(appMapping, form, request);
		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

		 protected ActionForward corporationList(AppActionMapping appMapping, CorporationForm form,
		            HttpServletRequest request) throws Exception {

			 CorporationService service = new CorporationService();

			 form.setCorporationList(service.getCorporationList());

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }


		 protected ActionForward detailCorporation(AppActionMapping appMapping, CorporationForm form,
		            HttpServletRequest request) throws Exception {

			 CorporationService service = new CorporationService();
			 form.setCorporationDTO(service.getCorporation(form.getSysCorporationId()));

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 protected ActionForward updateCorporation(AppActionMapping appMapping, CorporationForm form,
		            HttpServletRequest request) throws Exception {

			 CorporationService service = new CorporationService();
			 service.updateCorporation(form.getCorporationDTO());
			 form.setAlertType("2");
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 protected ActionForward deleteCorporation(AppActionMapping appMapping, CorporationForm form,
		            HttpServletRequest request) throws Exception {

			 CorporationService service = new CorporationService();
			 service.deleteCorporation(form.getCorporationDTO().getSysCorporationId());

			 MstCorporationDTO corporationDTO = new MstCorporationDTO();
			 form.setCorporationDTO(corporationDTO);
			 form.setAlertType("3");

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 protected ActionForward initRegistryCorporation(AppActionMapping appMapping, CorporationForm form,
		            HttpServletRequest request) throws Exception {

			 MstCorporationDTO corporationDTO = new MstCorporationDTO();
			form.setCorporationDTO(corporationDTO);

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 protected ActionForward registryCorporation(AppActionMapping appMapping, CorporationForm form,
		            HttpServletRequest request) throws Exception {

			 CorporationService service = new CorporationService();
			 service.registryCorporation(form.getCorporationDTO());
			 form.setAlertType("1");

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }
}
