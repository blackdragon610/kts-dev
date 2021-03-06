package jp.co.kts.ui.item;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;

import com.itextpdf.text.DocumentException;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.BackOrderDTO;
import jp.co.kts.app.common.entity.DeadStockDTO;
import jp.co.kts.app.common.entity.UpdateDataHistoryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemManualDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.fileExport.ExportItemListService;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.ChannelService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.service.mst.WarehouseService;
import jp.co.kts.ui.web.struts.WebConst;

public class ItemAction extends AppBaseAction{


	private static String ALERT_TYPE_ASSEMBLY_SUCCESS = "4";
	private static String ALERT_TYPE_ASSEMBLY_ERROR = "5";

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {



		ItemForm form = (ItemForm)appForm;

		if ("/initItemList".equals(appMapping.getPath())) {
			return initItemList(appMapping, form, request);
		} else if("/itemList".equals(appMapping.getPath())) {
			return itemList(appMapping, form, request);
		} else if("/itemListPageNo".equals(appMapping.getPath())) {
			return itemListPageNo(appMapping, form, request);
//		} else if("/initLumpArrival".equals(appMapping.getPath())) {
//			return initLumpArrival(appMapping, form, request);
		} else if("/initScheduledLumpArrivalRegistry".equals(appMapping.getPath())) {
			return initScheduledLumpArrivalRegistry(appMapping, form, request);
		} else if("/registryLumpArrivalSchedule".equals(appMapping.getPath())) {
			return registryLumpArrivalSchedule(appMapping, form, request);
		} else if ("/initRegistryItem".equals(appMapping.getPath())) {
			return initRegistryItem(appMapping, form, request);
		} else if ("/registryItem".equals(appMapping.getPath())) {
			return registryItem(appMapping, form, request);
		} else if ("/detailItem".equals(appMapping.getPath())) {
			return detailItem(appMapping, form, request);
		} else if ("/updateItem".equals(appMapping.getPath())) {
			return updateItem(appMapping, form, request);
		} else if ("/lumpUpdateStock".equals(appMapping.getPath())) {
			return lumpUpdateStock(appMapping, form, request);
		} else if ("/deleteItem".equals(appMapping.getPath())) {
			return deleteItem(appMapping, form, request);
		} else if ("/lumpDeleteItem".equals(appMapping.getPath())) {
			return lumpDeleteItem(appMapping, form, request);
		} else if ("/itemManualDownLoad".equals(appMapping.getPath())) {
			return itemManualDownLoad(appMapping, form, request, response);
//		} else if ("/lumpArrival".equals(appMapping.getPath())) {
//			return lumpArrival(appMapping, form, request);
		} else if ("/itemListDownLoad".equals(appMapping.getPath())) {
			return itemListDownLoad(appMapping, form, request, response);
		} else if ("/itemListCsvDownLoad".equals(appMapping.getPath())) {
			return itemListCsvDownLoad(appMapping, form, request, response);
		} else if ("/subWinItemSupplierSearch".equals(appMapping.getPath())) {
			return subWinItemSupplierSearch(appMapping, form, request, response);
		} else if ("/addOrderPool".equals(appMapping.getPath())){
			return addOrderPool(appMapping, form, request, response);
		} else if ("/deleteOrderPool".equals(appMapping.getPath())) {
			return deleteOrderPool(appMapping, form, request, response);
		} else if ("/updAssemblyNum".equals(appMapping.getPath())) {
			return updAssemblyNum(appMapping, form, request, response);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	protected ActionForward initItemList(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request) throws Exception {

		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(userId));
		String authInfo = form.getMstUserDTO().getOverseasInfoAuth();
		form.setOverseasInfoAuth(authInfo);

		ItemService itemService = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		form.setRegistryDto(messageDTO);

		form.setItemList(new ArrayList<ResultItemSearchDTO>());
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));

		form.setSearchItemDTO(itemService.initItemListDTO());
		form.setDisplayContentsVal("1");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//20140319 ??????
	private ActionForward itemList(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request) throws DaoException {

		// speed session-block
		HttpSession session = request.getSession(false);	
		
		ItemService itemService = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		// speed session-block
		form.setSysItemIdList(itemService.getSysItemIdList(session, form.getSearchItemDTO()));
		form.setSysItemIdListSize(form.getSysItemIdList().size());
		form.setPageIdx(0);
		form.setItemList(itemService.getItemList(form.getSysItemIdList(), form.getPageIdx(), form.getSearchItemDTO()));

		form.setDisplayContentsVal(form.getSearchItemDTO().getDisplayContents());

		//??????????????????????????????????????????????????????????????????????????????????????????????????????
		if (!form.getMessageFlg().equals("1")) {
			form.setRegistryDto(messageDTO);
		}
		form.setMessageFlg("0");
		//??????????????????????????????????????????????????????
		form.setErrorMessageDTO(itemService.checkItemList(form.getSysItemIdList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			form.setSysItemIdListSize(0);
			//itemService.setFlags(form.getSearchItemDTO());
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			form.setErrorMessageDTO(itemService.checkOrderAlertNum(form.getItemList()));
		}

		form.setItemListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSearchItemDTO().getListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward itemListPageNo(AppActionMapping appMapping, ItemForm form, HttpServletRequest request) throws DaoException {

		if (form.getSysItemIdList() == null || form.getSysItemIdList().size() <= 0) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setItemList(new ItemService().getItemList(form.getSysItemIdList(), form.getPageIdx(), form.getSearchItemDTO()));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	protected ActionForward initScheduledLumpArrivalRegistry(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request) throws Exception {

		ItemService itemService = new ItemService();

		form.setLumpArrivalScheduleList(itemService.initLumpArrivalScheduleList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//20140329 ??????
	protected ActionForward registryLumpArrivalSchedule(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request) throws Exception {

		ItemService itemService = new ItemService();

		itemService.registryLumpArrivalSchedule(form.getLumpArrivalScheduleList());

		form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		return initScheduledLumpArrivalRegistry(appMapping, form, request);

		//return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward initItemDetail(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request) throws Exception {

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}



	private ActionForward initRegistryItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(userId));
		String authInfo = form.getMstUserDTO().getOverseasInfoAuth();
		form.setOverseasInfoAuth(authInfo);

		//I/O??????
		ItemService itemService = new ItemService();
		form.setMstItemDTO(itemService.initMstItem());

		form.setErrorMessageDTO(new ErrorMessageDTO());

		//I/O????????????
		form.setWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>());
		form.setAddWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>(itemService.initAddWarehousStockList()));

		//O??????
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));

		// I/O????????????
		form.setExternalWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>(itemService.initAddExternalStockList()));
		form.setExternalWarehouseList(new ArrayList<>(new WarehouseService().getExternalWarehouseList()));

		//I???????????????
		form.setSupplierList(new ArrayList<ExtendMstSupplierDTO>());
		form.setSupplierDTO(new ExtendMstSupplierDTO());

		//I/O????????????
		form.setDeadStockList(new ArrayList<DeadStockDTO>());
		form.setAddDeadStockList(new ArrayList<>(itemService.initAddDeadStockList()));

		//I/O????????????
		form.setArrivalScheduleList(new ArrayList<ExtendArrivalScheduleDTO>());
		form.setAddArrivalScheduleList(itemService.initAddArrivalScheduleList());

		//I????????????
		form.setArrivalHistoryList(new ArrayList<ExtendArrivalScheduleDTO>());

		//I/O???????????????
		form.setItemCostList(itemService.initItemCostList());
		form.setItemPriceList(itemService.initItemPriceList());

		//O??????
		form.setCorporationList(new CorporationService().getCorporationList());

		//I/O?????????????????????
		form.setBackOrderList(new ArrayList<BackOrderDTO>());
		form.setAddBackOrderList(new ArrayList<BackOrderDTO>(itemService.initAddBackOrderList()));
		form.setWarehouseLength(form.getWarehouseList().size());

		//????????????
		form.setUpdateDataHistoryList(new ArrayList<UpdateDataHistoryDTO>());

		//I/O?????????
		form.setKeepList(new ArrayList<ExtendKeepDTO>());
		form.setAddKeepList(itemService.initAddKeepList());

		//I/O?????????????????????
		form.setExternalKeepList(new ArrayList<ExtendKeepDTO>());
		form.setAddExternalKeepList(itemService.initAddExternalKeepList());

		//I/O?????????
		form.setItemManualList(new ArrayList<ExtendItemManualDTO>());
		form.setAddItemManualList(itemService.initAddItemManualList());

		//O??????????????????
		form.setChannelList(new ChannelService().getChannelList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * ??????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward registryItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		//??????
		begin();

		ItemService itemService = new ItemService();

		//I/O??????
		itemService.setFlags(form.getMstItemDTO());
		form.setErrorMessageDTO(itemService.registryItem(itemService.setSetItemFlg(form.getMstItemDTO())));

		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setErrorMessageDTO(itemService.checkWarehouseStock(form.getWarehouseStockList(), form.getAddWarehouseStockList()));
		if (!form.getErrorMessageDTO().isSuccess()) {
			rollback();
			form.getMstItemDTO().setSysItemId(0);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		//I/O????????????
		itemService.registryWarehouseStockList(form.getAddWarehouseStockList(), form.getMstItemDTO().getSysItemId());

		// I/O????????????
		itemService.registryExternalWarehouseStockList(form.getExternalWarehouseStockList(), form.getMstItemDTO().getSysItemId());

		//???????????????????????????:
		itemService.registryAnnualSales(form.getMstItemDTO().getSysItemId());

//		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());

		//I/O????????????
		itemService.registryArrivalScheduleList(form.getAddArrivalScheduleList(), form.getMstItemDTO().getSysItemId());

		//I/O???????????????
		itemService.registryItemPriceList(form.getItemPriceList(), form.getMstItemDTO().getSysItemId());
		itemService.registryItemCostList(form.getItemCostList(), form.getMstItemDTO().getSysItemId());

		//I/O?????????????????????
		itemService.registryBackOrderList(form.getAddBackOrderList(), form.getMstItemDTO().getSysItemId());

		//I/O?????????
		form.setErrorMessageDTO(itemService.checkKeep(form.getKeepList(), form.getAddKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.registryKeepList(form.getAddKeepList(), form.getMstItemDTO().getSysItemId());

		//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());


		//?????????????????????
		form.setErrorMessageDTO(itemService.checkExternalKeep(form.getExternalKeepList(), form.getAddExternalKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.registryExternalKeepList(form.getAddExternalKeepList(), form.getMstItemDTO().getSysItemId());



		//I/O?????????
		itemService.registryFileUpLoadList(form.getItemManualList(), form.getAddItemManualList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

		//I/O????????????
		itemService.registryDeadStocklist(form.getDeadStockList(), form.getAddDeadStockList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

//		//I????????????
//		itemService.registryUpdateData(form);

		//????????????
		commit();
		form.setSysItemId(form.getMstItemDTO().getSysItemId());
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("??????????????????");
		form.setRegistryMessageDTO(registryMessageDTO);

		saveToken(request);

		return detailItem(appMapping, form, request);

//		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ??????????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward detailItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		//I/O??????
		ItemService itemService = new ItemService();
		form.setMstItemDTO(itemService.getMstItemDTO(form.getSysItemId()));
		itemService.setFlags(form.getMstItemDTO());

		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(form.getMstItemDTO().getUpdateUserId()));

		long userId = ActionContext.getLoginUserInfo().getUserId();
		form.setMstUserDTO(userService.getUserName(userId));
		String authInfo = form.getMstUserDTO().getOverseasInfoAuth();
		form.setOverseasInfoAuth(authInfo);

		//I/O????????????
		form.setWarehouseStockList(itemService.getWarehouseStockList(form.getSysItemId()));
		itemService.setBeforWarehouseInfo(form.getWarehouseStockList());
		form.setAddWarehouseStockList(itemService.initAddWarehousStockList());

		//O??????
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));
		form.setAddWarehouseStockList(itemService.initAddWarehousStockList());
		form.setWarehouseLength(form.getWarehouseList().size());

		// I/O????????????
		form.setExternalWarehouseStockList(itemService.getExternalStockList(form.getSysItemId(), null));
		itemService.setBeforExternalWarehouseInfo(form.getExternalWarehouseStockList());
		form.setExternalWarehouseList(new ArrayList<>(new WarehouseService().getExternalWarehouseList()));


		//O??????
		String supplierId = "";
		form.setSupplierDTO(new ExtendMstSupplierDTO());
		if (form.getMstItemDTO().getSysSupplierId() != 0) {
			form.setSupplierDTO(itemService.getSupplier(form.getMstItemDTO().getSysSupplierId()));
			supplierId = String.valueOf(form.getMstItemDTO().getSysSupplierId());
		}

		if (form.getSupplierDTO() == null) {
			form.setSupplierDTO(new ExtendMstSupplierDTO());
		}

		form.getMstItemDTO().setSupplierId(supplierId);

		//O????????????
		form.setDeadStockList(itemService.getDeadStockList(form.getSysItemId()));
		form.setAddDeadStockList(itemService.initAddDeadStockList());

		//O????????????
		form.setUpdateDataHistoryList(itemService.getUpdateDataHistoryList(form.getSysItemId()));

		//I/O????????????
		form.setArrivalScheduleList(itemService.getArrivalScheduleList(form.getSysItemId()));
//		form.setAddArrivalScheduleList(itemService.initAddArrivalScheduleList());
		//O????????????
		form.setArrivalHistoryList(itemService.getArrivalHistoryList(form.getSysItemId()));

		//I/O???????????????
		form.setItemCostList(itemService.getItemCostList(form.getSysItemId()));
		form.setItemPriceList(itemService.getItemPriceList(form.getSysItemId()));

		//O??????
		form.setCorporationList(new CorporationService().getCorporationList());

		//I/O?????????????????????
		form.setBackOrderList(itemService.getBackOrderList(form.getSysItemId()));
		form.setAddBackOrderList(itemService.initAddBackOrderList());

		//?????????
		form.setKeepList(itemService.getKeepList(form.getSysItemId()));
		form.setAddKeepList(itemService.initAddKeepList());

		//?????????????????????
		form.setExternalKeepList(itemService.getExternalKeepList(form.getSysItemId()));
		form.setAddExternalKeepList(itemService.initAddExternalKeepList());

		//I/O?????????
		form.setItemManualList(itemService.getItemManualList(form.getSysItemId()));
		if (form.getItemManualList() != null && form.getItemManualList().size() > 0) {
			itemService.changeItemManualList(form.getItemManualList());
		}
		form.setAddItemManualList(itemService.initAddItemManualList());

		//O??????????????????
		form.setChannelList(new ChannelService().getChannelList());

		//??????????????????????????????
		form.setExtendMstUserDTO(userService.getUserName(form.getMstItemDTO().getUpdateUserId()));
		form.setHaibangFlg(form.getMstItemDTO().getHaibangFlg());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ??????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		begin();

		ItemService itemService = new ItemService();
		itemService.setFlags(form.getMstItemDTO());
		
		form.getMstItemDTO().setHaibangFlg(form.getHaibangFlg().equals("on") ? "1" : "0");
		
		//I/O??????
		form.setErrorMessageDTO(itemService.updateItem(form.getMstItemDTO()));
		if (!form.getErrorMessageDTO().isSuccess()) {
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
//		//?????????????????????????????? ????????????????????????????????????????????????
//		itemService.insertItemInfo(form.getMstItemDTO(), form.getMstItemDTO());

		//Kind??????
		itemService.updateKindCost(form.getMstItemDTO());

		//???????????????????????????
		form.setErrorMessageDTO(itemService.checkWarehouseStock(form.getWarehouseStockList(), form.getAddWarehouseStockList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		/*---???????????? -- BONCRE-2700_????????????????????????????????? ????????????????????????????????????????????????????????????????????????????????????????????? -----------------------*/

		//??????????????????????????????????????????????????????
		Map<Long, Integer> fluctuationWarehouseValue =  new LinkedHashMap<>();
		//??????????????????????????????
		itemService.calculationWarehouseStock(form.getWarehouseStockList(), form.getWarehouseStockList(), fluctuationWarehouseValue);
		//DB?????????????????????????????????
		form.setWarehouseStockList(itemService.getLatestWarehouseList(form.getWarehouseStockList(),
																												fluctuationWarehouseValue,
																												form.getMstItemDTO().getSysItemId()));

		/*--- ???????????? -- BONCRE-2700_????????????????????????????????? ????????????????????????????????????????????????????????????????????????????????????????????? -----------------------*/

		//I/O????????????
		itemService.updateWarehouseStockList(form.getWarehouseStockList());
		itemService.registryWarehouseStockList(form.getAddWarehouseStockList(), form.getMstItemDTO().getSysItemId());
		itemService.insertWarehouseInfo(form.getWarehouseStockList(), form.getWarehouseStockList(), "0");
		//?????????????????????
//		itemService.insertWarehouseInfo(form.getAddWarehouseStockList(), form.getAddWarehouseStockList());


		/*
		 * ????????????
		 */
		// ?????????????????????????????????????????????????????????
		Map<String, Integer> fluctuationEWValue =  new LinkedHashMap<>();

		//??????????????????????????????
		itemService.calculationExternalWarehouseStock(form.getExternalWarehouseStockList(), form.getExternalWarehouseStockList(), fluctuationEWValue);
		//DB?????????????????????????????????
		form.setExternalWarehouseStockList(itemService.getLatestExternalWarehouseList(form.getExternalWarehouseStockList(), fluctuationEWValue, form.getMstItemDTO().getSysItemId()));

		//I/O????????????
		itemService.updateExternalWarehouseStockList(form.getExternalWarehouseStockList());
		itemService.insertExternalWarehouseInfo(form.getExternalWarehouseStockList(), form.getExternalWarehouseStockList(), "0");



		//I/O????????????
		itemService.updateArrivalScheduleList(form.getArrivalScheduleList(), form.getMstItemDTO().getSysItemId());
		itemService.registryArrivalScheduleList(form.getAddArrivalScheduleList(), form.getMstItemDTO().getSysItemId());

		//I/O???????????????
		itemService.updateItemPriceList(form.getItemPriceList());
		itemService.updateItemCostList(form.getItemCostList());

		//I/O?????????????????????
		itemService.updateBackOrderList(form.getBackOrderList());
		itemService.registryBackOrderList(form.getAddBackOrderList(), form.getMstItemDTO().getSysItemId());

		//I/O?????????
		itemService.updateItemManualList(form.getItemManualList());
		itemService.registryFileUpLoadList(form.getItemManualList(), form.getAddItemManualList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

		/*
		 * ???????????????
		 */
		//?????????
		form.setErrorMessageDTO(itemService.checkKeep(form.getKeepList(), form.getAddKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.updateKeepList(form.getKeepList());
		itemService.registryKeepList(form.getAddKeepList(), form.getMstItemDTO().getSysItemId());
//		itemService.updateTemporaryStockNum(form.getMstItemDTO().getSysItemId());

		//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());


		//?????????????????????
		form.setErrorMessageDTO(itemService.checkExternalKeep(form.getExternalKeepList(), form.getAddExternalKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.updateExternalKeepList(form.getExternalKeepList());
		itemService.registryExternalKeepList(form.getAddExternalKeepList(), form.getMstItemDTO().getSysItemId());




		//I????????????
		itemService.updateDeadStocklist(form.getDeadStockList());
		itemService.registryDeadStocklist(form.getDeadStockList(), form.getAddDeadStockList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

//		//I????????????
//		itemService.registryUpdateData(form);

		form.setSysItemId(form.getMstItemDTO().getSysItemId());
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		/* ????????????????????????????????????????????????????????? 20171107 y_saito*/
		//??????????????????????????????????????????????????????????????????
//		itemService.setAllAssemblyNum(form.getSysItemId());
//		itemService.setAllSetItemAssemblyNum();

		commit();

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("??????????????????");
		form.setRegistryMessageDTO(registryMessageDTO);

//		 saveToken(request);

		detailItem(appMapping, form, request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward lumpUpdateStock(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws DaoException {


		//??????????????????????????????????????????????????????????????????????????????
		ItemService itemService = new ItemService();

		itemService.stockNumLoad(form.getItemList());

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * ??????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		ItemService itemService = new ItemService();

		//????????????
		itemService.deleteItem(form.getMstItemDTO().getSysItemId());
		//??????????????????
		itemService.deleteWarehouseStock(form.getMstItemDTO().getSysItemId());

		//????????????????????????
		itemService.deleteExternalStock(form.getMstItemDTO().getSysItemId());

		//???????????????????????????
		itemService.deleteExternalKeepOfItem(form.getMstItemDTO().getSysItemId());

		//??????????????????
		itemService.deleteArrivalSchedule(form.getMstItemDTO().getSysItemId());
		//????????????
		itemService.deleteCost(form.getMstItemDTO().getSysItemId());
		//????????????
		itemService.deletePrice(form.getMstItemDTO().getSysItemId());
		//???????????????????????????
		itemService.deleteBackOrder(form.getMstItemDTO().getSysItemId());
		//??????????????????
		itemService.deleteCodeCollation(form.getMstItemDTO().getSysItemId());
		//??????????????????
		itemService.deleteFormItem(form.getMstItemDTO().getSysItemId());
		//??????????????????
		itemService.deleteDeadStock(form.getMstItemDTO().getSysItemId());
		//?????????????????????
		itemService.deleteAnnualSales(form.getMstItemDTO().getSysItemId());
		//???????????????????????????
		itemService.deleteForeignItem(form.getMstItemDTO().getSysItemId());

		form.setAlertType(WebConst.ALERT_TYPE_DELETE);

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("??????????????????");
		form.setRegistryMessageDTO(registryMessageDTO);

		return initRegistryItem(appMapping, form, request);

//		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//20140328 ??????
	private ActionForward lumpDeleteItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws DaoException {

		ItemService itemService = new ItemService();

		itemService.setFlags(form.getItemList());
		itemService.lumpDeleteItem(form.getItemList());

		form.setAlertType(WebConst.ALERT_TYPE_DELETE);

		return itemList(appMapping, form, request);

		//return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
	private ActionForward itemManualDownLoad(AppActionMapping appMapping, ItemForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException, DocumentException, IOException {

		ItemService itemService = new ItemService();
		String filePath = itemService.getItemManualPath(form.getSysItemManualId());
		String fileName = itemService.getItemManualFileName(filePath);
		// ???????????????????????????????????????????????????????????????????????????????????????.
		byte[] sJis = fileName.getBytes("Shift_JIS");
		fileName = new String(sJis, "ISO8859_1");

		OutputStream os = response.getOutputStream();
		File fileOut = new File(fileName);

		try {
			FileInputStream hFile = new FileInputStream(filePath);
			BufferedInputStream bis = new BufferedInputStream(hFile);

			System.out.println(response.getContentType());

			// ?????????????????????
			if (StringUtils.endsWith(fileName, ".pdf")) {

				response.setContentType("application/pdf");

			} else if (StringUtils.endsWith(fileName, ".png")) {

//				response.setContentType("image/png");

			} else if (StringUtils.endsWith(fileName, ".jpg") || StringUtils.endsWith(fileName, ".jpe")
					|| StringUtils.endsWith(filePath, "jfif") || StringUtils.endsWith(fileName, ".jpeg")
					|| StringUtils.endsWith(filePath, "pjpeg") || StringUtils.endsWith(fileName, ".pjp")) {

				response.setContentType("image/jpeg");

			} else {
				bis.close();
				return null;
			}

			response.setHeader("Content-Disposition", "inline; filename=\"" + fileOut.getName() + "\"");

			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}

			bis.close();
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		} finally {

			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return null;
				} finally {
					os = null;
				}
			}
		}

		return null;
	}

	/**
	 * ???????????????????????????????????????DL????????????Excel???????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward itemListDownLoad(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// speed session-block
		HttpSession session = request.getSession(false);
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(userId));
		String filePath = "";
		String authInfo = form.getMstUserDTO().getOverseasInfoAuth();

		//??????????????????????????????????????????????????????????????????
		switch (form.getSearchItemDTO().getDownloadType()) {
		//???????????????????????????????????????
		case WebConst.DOWNLOAD_TYPE_CODE1:
			if (form.getMstUserDTO().getOverseasInfoAuth().equals(WebConst.AUTH_INFO_OK)) {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_INFO_LIST_TEMPLATE_PATH);
			} else {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_INFO_LIST_NOT_AUTH_PATH);
			}
			break;
		//???????????????????????????????????????
		case WebConst.DOWNLOAD_TYPE_CODE2:
			filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.STOCK_ITEM_INFO_LIST_TEMPLATE_PATH);
			break;
		//???????????????????????????????????????
		case WebConst.DOWNLOAD_TYPE_CODE3:
			if (form.getMstUserDTO().getOverseasInfoAuth().equals(WebConst.AUTH_INFO_OK)) {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.PRICE_INFO_LIST_TEMPLATE_PATH);
			} else {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.NOT_AUTH_PRICE_INFO_LIST_TEMPLATE_PATH);
			}
			break;
		//?????????CSV???????????????????????????
		case WebConst.DOWNLOAD_TYPE_CODE4:
			filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_LIST_TEMPLATE_PATH);
			break;
		// ?????????DL???????????????DL????????????????????????????????????
		case StringUtils.EMPTY:
			filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_LIST_TEMPLATE_PATH);
			break;
		//?????????Excel???????????????????????????
		case WebConst.DOWNLOAD_TYPE_CODE5:
			if (form.getMstUserDTO().getOverseasInfoAuth().equals(WebConst.AUTH_INFO_OK)) {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_ALL_LIST_TEMPLATE_PATH);
			} else {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_ALL_LIST_TEMPLATE_PATH_NOT_AUTH);
			}
			break;
		case WebConst.DOWNLOAD_TYPE_CODE6:
			filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_KEEPLIST_TEMPLATE_PATH);
			break;
		}

		// ????????????????????????????????????
//		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		OPCPackage pkg = OPCPackage.open(filePath);
		// ??????????????????????????????????????????
		XSSFWorkbook workBook = new XSSFWorkbook(pkg);

		//??????????????????????????????????????????
		ExportItemListService exportItemListService = new ExportItemListService();

		ItemService itemService = new ItemService();
//		itemService.setFlags(form.getSearchItemDTO());

		// ?????????????????????.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "";
		int sheetNo = 0;
		//??????????????????????????????????????????Excel???????????????
		switch (form.getSearchItemDTO().getDownloadType()) {
		//????????????
		// ??????????????????????????????????????????????????????????????????????????????
		case WebConst.DOWNLOAD_TYPE_CODE1:
			// speed session-block
			workBook = exportItemListService.getExportItemInfoList(session, form.getSearchItemDTO(), workBook, authInfo, sheetNo);
			fname = "??????????????????_" + date + ".xlsx";
			break;
		//????????????
		case WebConst.DOWNLOAD_TYPE_CODE2:
			// speed session-block
			//form.setSysItemIdList(itemService.getSysItemIdList(session, form.getSearchItemDTO()));
			form.setSysItemIdList( (List<SysItemIdDTO>) session.getAttribute("List<SysItemIdDTO>getSysItemIdList(searchItemDTO)" ));			
			workBook = exportItemListService.getExportStockItemInfoList(form.getSysItemIdList(), workBook, sheetNo);
			fname = "????????????_" + date + ".xlsx";
			break;
		//????????????
		case WebConst.DOWNLOAD_TYPE_CODE3:
			// speed session-block
			//form.setSysItemIdList(itemService.getSysItemIdList(session, form.getSearchItemDTO()));
			form.setSysItemIdList( (List<SysItemIdDTO>) session.getAttribute("List<SysItemIdDTO>getSysItemIdList(searchItemDTO)" ));
			workBook = exportItemListService.getExportPriceInfoList(form.getSysItemIdList(), workBook, authInfo, sheetNo);
			fname = "????????????_" + date + ".xlsx";
			break;
		//?????????CSV
		case WebConst.DOWNLOAD_TYPE_CODE4:
			// speed session-block
			workBook = exportItemListService.getExportItemList(session, form.getSearchItemDTO(), workBook);
			break;
		// ?????????DL????????????????????????????????????????????????????????????
		case StringUtils.EMPTY:
			// speed session-block
			workBook = exportItemListService.getExportItemList(session, form.getSearchItemDTO(), workBook);
			fname = "????????????_" + date + ".xlsx";
			break;
		//?????????Excel
		case WebConst.DOWNLOAD_TYPE_CODE5:
			// speed session-block
			//form.setSysItemIdList(itemService.getSysItemIdList(session, form.getSearchItemDTO()));
			form.setSysItemIdList( (List<SysItemIdDTO>) session.getAttribute("List<SysItemIdDTO>getSysItemIdList(searchItemDTO)" ));
			sheetNo = 0;
			// speed session-block
			workBook = exportItemListService.getExportItemInfoList(session, form.getSearchItemDTO(), workBook, authInfo, sheetNo);
			sheetNo = 1;
			workBook = exportItemListService.getExportStockItemInfoList(form.getSysItemIdList(), workBook, sheetNo);
			sheetNo = 2;
			workBook = exportItemListService.getExportPriceInfoList(form.getSysItemIdList(), workBook, authInfo, sheetNo);

			fname = "???????????????_" + date + ".xlsx";
			break;
		case WebConst.DOWNLOAD_TYPE_CODE6:
			// speed session-block
			workBook = exportItemListService.getKeepOrderList(session, form.getSearchItemDTO(), workBook);
			fname = "??????_" + date + ".xlsx";
			break;
		}

		// ???????????????????????????????????????????????????????????????????????????????????????.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// ??????????????????????????????
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fname);
		ServletOutputStream fileOutHssf = response.getOutputStream();
		workBook.write(fileOutHssf);
		exportItemListService.close();
		fileOutHssf.close();

		return null;
	}

	/**
	 * [??????] ?????????????????????????????????CSV?????????????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DaoException
	 */
	private ActionForward itemListCsvDownLoad(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ?????????????????????.
		String date = DateUtil.dateToString("yyyyMMdd");
		// ?????????????????????
		String fname = "?????????CSV_" + date + ".csv";

		//CSV??????????????????????????????
		ExportItemListService exportItemListService = new ExportItemListService();

		//CSV????????????
		exportItemListService.exportCsvList(form.getSearchItemDTO(), fname, response);

		exportItemListService.close();

		return null;
	}

	/**
	 * [??????]????????????????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward subWinItemSupplierSearch(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		form.setSupplierList(new ArrayList<ExtendMstSupplierDTO>());
		ItemService itemService = new ItemService();
		form.setSupplierList(itemService.getSupplierList(form.getMstItemDTO().getSysSupplierId()));

		form.setSupplierListSize(form.getSupplierList().size());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);

	}

	/**
	 * ???????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward addOrderPool(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//????????????????????????
		ItemService service = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//???????????????????????????
		ErrorMessageDTO errorMessageDTO = service.addOrderPool(form.getItemList());
		if (!errorMessageDTO.isSuccess()) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("??????????????????????????????");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("????????????????????????????????????????????????");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");
		}
		return itemList(appMapping, form, request);
	}

	/**
	 * ???????????????????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteOrderPool(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//????????????????????????
		ItemService service = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//???????????????????????????
		ErrorMessageDTO errorMessageDTO = service.deleteOrderPool(form.getItemList());
		if (!errorMessageDTO.isSuccess()) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("??????????????????????????????");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);

		} else {
			//????????????1??????????????????????????????????????????????????????
			if (errorMessageDTO.getResultCnt() == 0) {
				messageDTO.setMessageFlg("1");
				messageDTO.setMessage("??????????????????????????????????????????");
				form.setRegistryDto(messageDTO);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("??????????????????????????????" + errorMessageDTO.getResultCnt() + "????????????????????????");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");
		}

		return itemList(appMapping, form, request);
	}

	/**
	 * ?????????????????????????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward updAssemblyNum(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//????????????????????????
		ItemService itemService =  new ItemService();

		begin();
		if (itemService.setAllSetItemAssemblyNum() != 1) {
			rollback();
			form.setAlertType(ALERT_TYPE_ASSEMBLY_ERROR);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		commit();

		form.setAlertType(ALERT_TYPE_ASSEMBLY_SUCCESS);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

}
