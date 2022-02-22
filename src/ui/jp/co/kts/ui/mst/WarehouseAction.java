package jp.co.kts.ui.mst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.WarehouseService;

public class WarehouseAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WarehouseForm form = (WarehouseForm)appForm;


		if ("/initWarehouseList".equals(appMapping.getPath())) {
			return warehouseList(appMapping, form, request);
		} else if ("/detailWarehouse".equals(appMapping.getPath())) {
			return  detailWarehouse(appMapping, form, request);
		} else if ("/updateWarehouse".equals(appMapping.getPath())) {
			 return updateWarehouse(appMapping, form, request);
		} else if ("/deleteWarehouse".equals(appMapping.getPath())) {
			 return deleteWarehouse(appMapping, form, request);
		} else if ("/initRegistryWarehouse".equals(appMapping.getPath())) {
			 return initRegistryWarehouse(appMapping, form, request);
		} else if ("/registryWarehouse".equals(appMapping.getPath())) {
			 return registryWarehouse(appMapping, form, request);
		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

		/**
		 *
		 * 初期表示
		 *
		 * @param appMapping
		 * @param form
		 * @param request
		 * @return
		 * @throws Exception
		 */
		 protected ActionForward warehouseList(AppActionMapping appMapping, WarehouseForm form,
		            HttpServletRequest request) throws Exception {

			 WarehouseService service = new WarehouseService();

			 form.setWarehouseList(service.getWarehouseList());

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 /**
		  *
		  * 詳細画面表示
		  *
		  * @param appMapping
		  * @param form
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 protected ActionForward detailWarehouse(AppActionMapping appMapping, WarehouseForm form,
		            HttpServletRequest request) throws Exception {

			 WarehouseService service = new WarehouseService();
			 form.setWarehouseDTO(service.getWarehouse(form.getSysWarehouseId()));

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 /**
		  *
		  * 更新
		  *
		  * @param appMapping
		  * @param form
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 protected ActionForward updateWarehouse(AppActionMapping appMapping, WarehouseForm form,
		            HttpServletRequest request) throws Exception {

			 WarehouseService service = new WarehouseService();
			 service.updateWarehouse(form.getWarehouseDTO());
			 form.setAlertType("2");
			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 /**
		  *
		  * 削除
		  *
		  * @param appMapping
		  * @param form
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 protected ActionForward deleteWarehouse(AppActionMapping appMapping, WarehouseForm form,
		            HttpServletRequest request) throws Exception {

			 WarehouseService service = new WarehouseService();
			 service.deleteWarehouse(form.getWarehouseDTO().getSysWarehouseId());

			MstWarehouseDTO warehouseDTO = new MstWarehouseDTO();
			form.setWarehouseDTO(warehouseDTO);
			form.setAlertType("3");

			//削除後再検索
			form.setWarehouseList(service.getWarehouseList());

			 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 /**
		  *
		  * 登録画面
		  *
		  * @param appMapping
		  * @param form
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 protected ActionForward initRegistryWarehouse(AppActionMapping appMapping, WarehouseForm form,
		            HttpServletRequest request) throws Exception {

			 MstWarehouseDTO warehouseDTO = new MstWarehouseDTO();
			form.setWarehouseDTO(warehouseDTO);

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }

		 /**
		  *
		  * 登録
		  *
		  * @param appMapping
		  * @param form
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 protected ActionForward registryWarehouse(AppActionMapping appMapping, WarehouseForm form,
		            HttpServletRequest request) throws Exception {

			WarehouseService service = new WarehouseService();
			long  sysWarehouseId = service.registryWarehouse(form.getWarehouseDTO());
			form.setAlertType("1");

			//商品IDを全件取得する
			//serviceを呼び出す→さらにDAO
			form.setSysItemListAll(service.getSysItemIdList());

			//取得したIDをキーに取得したID件数分Insertを行う
			ItemService itemService = new ItemService();
			itemService.registryAllItemId(form, sysWarehouseId);

			//登録後再検索
			form.setWarehouseList(service.getWarehouseList());

			//tokenセット
			saveToken(request);

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		 }
}
