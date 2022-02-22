package jp.co.kts.ui.clientSearch;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstDeliveryDTO;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.DeliveryService;
import net.arnx.jsonic.JSON;

public class ClientSearchAction extends AppBaseAction {

	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientSearchForm form = (ClientSearchForm)appForm;

		if ("/subWinClientSearch".equals(appMapping.getPath())) {
			return subWinClientSearch(appMapping, form, request);
		} else if ("/getDeliveryList".equals(appMapping.getPath())) {
			return getDeliveryList(appMapping, form, request, response);
		}


		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	protected ActionForward subWinClientSearch(AppActionMapping appMapping,
			ClientSearchForm form, HttpServletRequest request) throws Exception {

		form.setSearchClientList(new ArrayList<ExtendMstClientDTO>());

		ClientService cliService = new ClientService();
		form.setSearchClientList(cliService.getExtClientList(form.getClientSearchDTO()));

		form.setClientListSize(form.getSearchClientList().size());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward getDeliveryList(AppActionMapping appMapping,
			ClientSearchForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		DeliveryService deliveryService = new DeliveryService();
		List<ExtendMstDeliveryDTO> deliveryList = deliveryService.getDeliveryList(form.getSysClientId());

		if (deliveryList.size() == 0){
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(""));
		} else {
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(deliveryList));
		}

		return null;
	}

}
