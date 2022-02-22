package jp.co.kts.ui.mst;

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
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.search.entity.ClientSearchDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;

public class ClientAction extends AppBaseAction {

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

		ClientForm form = (ClientForm)appForm;

		if ("/initClientList".equals(appMapping.getPath())) {
			return initClientList(appMapping, form, request);
		} else if ("/clientList".equals(appMapping.getPath())) {
			return clientList(appMapping, form, request);
		} else if ("/detailClient".equals(appMapping.getPath())) {
			return detailClient(appMapping, form, request);
		} else if ("/updateClient".equals(appMapping.getPath())) {
			return updateClient(appMapping, form, request);
		} else if ("/deleteClient".equals(appMapping.getPath())) {
			return deleteClient(appMapping, form, request);
		} else if ("/initRegistryClient".equals(appMapping.getPath())) {
			return initRegistryClient(appMapping, form, request);
		} else if ("/copyRegistryClient".equals(appMapping.getPath())) {
			return copyRegistryClient(appMapping, form, request);
		} else if ("/registryClient".equals(appMapping.getPath())) {
			return registryClient(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	protected ActionForward initClientList(AppActionMapping appMapping, ClientForm form,
	            HttpServletRequest request) throws Exception {

		ClientSearchDTO search = new ClientSearchDTO();
		form.setClientSearchDTO(search);

		ClientService clientService = new ClientService();
		form.setExtClientList(clientService.getExtClientList(search));

		CorporationService corpService = new CorporationService();
		form.setCorporationList(corpService.getCorporationList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward clientList(AppActionMapping appMapping, ClientForm form,
	            HttpServletRequest request) throws Exception {

		ClientSearchDTO search = new ClientSearchDTO();
		search = form.getClientSearchDTO();

		ClientService service = new ClientService();

		form.setExtClientList(service.getExtClientList(search));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward detailClient(AppActionMapping appMapping, ClientForm form,
	            HttpServletRequest request) throws Exception {

		ClientService clientService = new ClientService();
		form.setClientDTO(clientService.getClient(form.getSysClientId()));


		CorporationService corpService = new CorporationService();
		form.setCorporationNm(corpService.getCorporationNm(form.getClientDTO().getSysCorporationId()));
//		form.setCorporationList(corpService.getCorporationList());法人は選ばせない

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
	protected ActionForward initRegistryClient(AppActionMapping appMapping, ClientForm form,
	            HttpServletRequest request) throws Exception {

		//画面表示用法人名
		CorporationService corpService = new CorporationService();
		MstCorporationDTO corporationDTO = corpService.getCorporation(form.getSysCorporationId());
		form.setCorporationNm(corporationDTO.getCorporationNm());

		MstClientDTO clientDTO = new MstClientDTO();
		clientDTO.setSysCorporationId(corporationDTO.getSysCorporationId());
		form.setClientDTO(clientDTO);
		form.setSysClientId(0);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward copyRegistryClient(AppActionMapping appMapping, ClientForm form,
            HttpServletRequest request) throws Exception {

		MstClientDTO clientDTO = form.getClientDTO();
		clientDTO.setSysClientId(0);
		clientDTO.setClientNo("");
		form.setClientDTO(clientDTO);
		form.setSysClientId(0);

	return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
}

	protected ActionForward updateClient(AppActionMapping appMapping, ClientForm form,
	            HttpServletRequest request) throws Exception {

		ClientService service = new ClientService();
		String validType = VALID_TYPE_UPD;

		/*入力値チェック*/
		Result<MstClientDTO> result = service.validate(form.getClientDTO(), validType);

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		service.updateClient(form.getClientDTO());

		form.setAlertType("2");
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward deleteClient(AppActionMapping appMapping, ClientForm form,
	            HttpServletRequest request) throws Exception {

		//得意先削除
		ClientService clientService = new ClientService();
		clientService.deleteClient(form.getClientDTO().getSysClientId());

		MstClientDTO clientDTO = new MstClientDTO();
		form.setClientDTO(clientDTO);
		form.setAlertType("3");

		//削除後の一覧を再取得
		ClientSearchDTO search = new ClientSearchDTO();
		search = form.getClientSearchDTO();
		form.setExtClientList(clientService.getExtClientList(search));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward registryClient(AppActionMapping appMapping, ClientForm form,
	            HttpServletRequest request) throws Exception {

		 ClientService service = new ClientService();
		 String validType = VALID_TYPE_INS;

			/*入力値チェック*/
		 Result<MstClientDTO> result = service.validate(form.getClientDTO(), validType);

		if (!result.isSuccess()) {
			List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
			messages.addAll(result.getErrorMessages());
			saveErrorMessages(request, messages);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		service.registryClient(form.getClientDTO());
		form.setAlertType("1");

		//追加後の一覧を再取得
		ClientSearchDTO search = new ClientSearchDTO();
		search = form.getClientSearchDTO();
		ClientService clientService = new ClientService();
		form.setExtClientList(clientService.getExtClientList(search));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }

}
