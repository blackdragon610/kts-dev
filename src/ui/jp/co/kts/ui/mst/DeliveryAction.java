package jp.co.kts.ui.mst;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstDeliveryDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.DeliveryService;
import net.arnx.jsonic.JSON;

public class DeliveryAction extends AppBaseAction {


	/**
	 * バリデートチェックタイプ:UPDATE
	 */
	private static final String VALID_TYPE_UPD = "update";
	/**
	 * バリデートチェックタイプ：INSERT
	 */
	private static final String VALID_TYPE_INS = "insert";


	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DeliveryForm form = (DeliveryForm)appForm;


		if ("/initRegistryDelivery".equals(appMapping.getPath())) {
			return initRegistryDelivery(appMapping, form, request);
		} else if ("/registryDelivery".equals(appMapping.getPath())) {
			return registryDelivery(appMapping, form, request);
		} else if ("/detailDelivery".equals(appMapping.getPath())) {
			return detailDelivery(appMapping, form, request);
		} else if ("/updateDelivery".equals(appMapping.getPath())) {
			return updateDelivery(appMapping, form, request);
		} else if ("/deleteDelivery".equals(appMapping.getPath())) {
			return deleteDelivery(appMapping, form, request);
		} else if ("/getClientList".equals(appMapping.getPath())) {
			return getClientList(appMapping, form, request, response);
		}
		else if ("/initDeliveryList".equals(appMapping.getPath())) {
			return initDeliveryList(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	protected ActionForward initRegistryDelivery(AppActionMapping appMapping, DeliveryForm form,
            HttpServletRequest request) throws Exception {

		MstDeliveryDTO deliveryDTO = new MstDeliveryDTO();
		form.setDeliveryDTO(deliveryDTO);

		form.setSysCorporationId(1);
		CorporationService corporationService = new CorporationService();
		form.setCorporationList(corporationService.getCorporationList());

		ClientService clientService = new ClientService();
		form.setClientList(clientService.getClientList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward registryDelivery(AppActionMapping appMapping, DeliveryForm form,
            HttpServletRequest request) throws Exception {

		DeliveryService deliService = new DeliveryService();
		String validType = VALID_TYPE_INS;

		Result<MstDeliveryDTO> result = deliService.validate(form.getDeliveryDTO(), validType);

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}


		deliService.registryDelivery(form.getDeliveryDTO());
		form.setAlertType("1");

		//追加後の一覧を再取得
		form.setDeliveryList(deliService.getDeliveryList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	 protected ActionForward detailDelivery(AppActionMapping appMapping, DeliveryForm form,
	            HttpServletRequest request) throws Exception {

		DeliveryService deliService = new DeliveryService();
		form.setDeliveryDTO(deliService.getDelivery(form.getSysDeliveryId()));

		form.setSysCorporationId(form.getDeliveryDTO().getSysCorporationId());
		CorporationService corporationService = new CorporationService();
		form.setCorporationList(corporationService.getCorporationList());

		ClientService clientService = new ClientService();
		form.setClientList(clientService.getClientList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward updateDelivery(AppActionMapping appMapping, DeliveryForm form,
            HttpServletRequest request) throws Exception {

		DeliveryService deliService = new DeliveryService();
		String validType = VALID_TYPE_UPD;

		Result<MstDeliveryDTO> result = deliService.validate(form.getDeliveryDTO(),validType);

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		deliService.updateDelivery(form.getDeliveryDTO());

		form.setAlertType("2");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward deleteDelivery(AppActionMapping appMapping, DeliveryForm form,
            HttpServletRequest request) throws Exception {

		DeliveryService deliService = new DeliveryService();
		deliService.deleteDelivery(form.getDeliveryDTO().getSysDeliveryId());

		MstDeliveryDTO deliveryDTO = new MstDeliveryDTO();
		form.setDeliveryDTO(deliveryDTO);
		form.setAlertType("3");

		//削除後の一覧を再取得
		form.setDeliveryList(deliService.getDeliveryList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward initDeliveryList(AppActionMapping appMapping, DeliveryForm form,
	            HttpServletRequest request) throws Exception {

		DeliveryService deliService = new DeliveryService();
		form.setDeliveryList(deliService.getDeliveryList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward getClientList(AppActionMapping appMapping,DeliveryForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ClientService clientService = new ClientService();
		List<MstClientDTO> clientList = clientService.getCorprationClientList(form.getSysCorporationId());

		if (clientList.size() == 0){
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(""));
		} else {
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(clientList));
		}

		return null;
	}


}
