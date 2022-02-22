package jp.co.kts.ui.item;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

	//20140319 八鍬
	private ActionForward itemList(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request) throws DaoException {

		ItemService itemService = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		form.setSysItemIdList(itemService.getSysItemIdList(form.getSearchItemDTO()));
		form.setSysItemIdListSize(form.getSysItemIdList().size());
		form.setPageIdx(0);
		form.setItemList(itemService.getItemList(form.getSysItemIdList(), form.getPageIdx(), form.getSearchItemDTO()));

		form.setDisplayContentsVal(form.getSearchItemDTO().getDisplayContents());

		//注文プール追加メソッドから呼び出されていない場合、メッセージを初期化
		if (!form.getMessageFlg().equals("1")) {
			form.setRegistryDto(messageDTO);
		}
		form.setMessageFlg("0");
		//検索件数が０件の場合のメッセージ格納
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

	//20140329 八鍬
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

		//I/O商品
		ItemService itemService = new ItemService();
		form.setMstItemDTO(itemService.initMstItem());

		form.setErrorMessageDTO(new ErrorMessageDTO());

		//I/O倉庫在庫
		form.setWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>());
		form.setAddWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>(itemService.initAddWarehousStockList()));

		//O倉庫
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));

		// I/O外部倉庫
		form.setExternalWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>(itemService.initAddExternalStockList()));
		form.setExternalWarehouseList(new ArrayList<>(new WarehouseService().getExternalWarehouseList()));

		//I工場リスト
		form.setSupplierList(new ArrayList<ExtendMstSupplierDTO>());
		form.setSupplierDTO(new ExtendMstSupplierDTO());

		//I/O不良在庫
		form.setDeadStockList(new ArrayList<DeadStockDTO>());
		form.setAddDeadStockList(new ArrayList<>(itemService.initAddDeadStockList()));

		//I/O入荷予定
		form.setArrivalScheduleList(new ArrayList<ExtendArrivalScheduleDTO>());
		form.setAddArrivalScheduleList(itemService.initAddArrivalScheduleList());

		//I入荷履歴
		form.setArrivalHistoryList(new ArrayList<ExtendArrivalScheduleDTO>());

		//I/O売価・原価
		form.setItemCostList(itemService.initItemCostList());
		form.setItemPriceList(itemService.initItemPriceList());

		//O法人
		form.setCorporationList(new CorporationService().getCorporationList());

		//I/Oバックオーダー
		form.setBackOrderList(new ArrayList<BackOrderDTO>());
		form.setAddBackOrderList(new ArrayList<BackOrderDTO>(itemService.initAddBackOrderList()));
		form.setWarehouseLength(form.getWarehouseList().size());

		//更新情報
		form.setUpdateDataHistoryList(new ArrayList<UpdateDataHistoryDTO>());

		//I/Oキープ
		form.setKeepList(new ArrayList<ExtendKeepDTO>());
		form.setAddKeepList(itemService.initAddKeepList());

		//I/O外部倉庫キープ
		form.setExternalKeepList(new ArrayList<ExtendKeepDTO>());
		form.setAddExternalKeepList(itemService.initAddExternalKeepList());

		//I/O説明書
		form.setItemManualList(new ArrayList<ExtendItemManualDTO>());
		form.setAddItemManualList(itemService.initAddItemManualList());

		//O販売チャネル
		form.setChannelList(new ChannelService().getChannelList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * 商品登録処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward registryItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		//開始
		begin();

		ItemService itemService = new ItemService();

		//I/O商品
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
		//I/O倉庫在庫
		itemService.registryWarehouseStockList(form.getAddWarehouseStockList(), form.getMstItemDTO().getSysItemId());

		// I/O外部倉庫
		itemService.registryExternalWarehouseStockList(form.getExternalWarehouseStockList(), form.getMstItemDTO().getSysItemId());

		//年間販売数情報登録:
		itemService.registryAnnualSales(form.getMstItemDTO().getSysItemId());

//		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());

		//I/O入荷予定
		itemService.registryArrivalScheduleList(form.getAddArrivalScheduleList(), form.getMstItemDTO().getSysItemId());

		//I/O売価・原価
		itemService.registryItemPriceList(form.getItemPriceList(), form.getMstItemDTO().getSysItemId());
		itemService.registryItemCostList(form.getItemCostList(), form.getMstItemDTO().getSysItemId());

		//I/Oバックオーダー
		itemService.registryBackOrderList(form.getAddBackOrderList(), form.getMstItemDTO().getSysItemId());

		//I/Oキープ
		form.setErrorMessageDTO(itemService.checkKeep(form.getKeepList(), form.getAddKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.registryKeepList(form.getAddKeepList(), form.getMstItemDTO().getSysItemId());

		//キープ数の更新が終わった後にこの処理を行うと仮在庫の更新も一気に行えるのでこの場所にあります
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());


		//外部倉庫キープ
		form.setErrorMessageDTO(itemService.checkExternalKeep(form.getExternalKeepList(), form.getAddExternalKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.registryExternalKeepList(form.getAddExternalKeepList(), form.getMstItemDTO().getSysItemId());



		//I/O説明書
		itemService.registryFileUpLoadList(form.getItemManualList(), form.getAddItemManualList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

		//I/O不良在庫
		itemService.registryDeadStocklist(form.getDeadStockList(), form.getAddDeadStockList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

//		//I更新情報
//		itemService.registryUpdateData(form);

		//登録成功
		commit();
		form.setSysItemId(form.getMstItemDTO().getSysItemId());
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("登録しました");
		form.setRegistryMessageDTO(registryMessageDTO);

		saveToken(request);

		return detailItem(appMapping, form, request);

//		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 商品詳細情報取得処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward detailItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		//I/O商品
		ItemService itemService = new ItemService();
		form.setMstItemDTO(itemService.getMstItemDTO(form.getSysItemId()));
		itemService.setFlags(form.getMstItemDTO());

		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(form.getMstItemDTO().getUpdateUserId()));

		long userId = ActionContext.getLoginUserInfo().getUserId();
		form.setMstUserDTO(userService.getUserName(userId));
		String authInfo = form.getMstUserDTO().getOverseasInfoAuth();
		form.setOverseasInfoAuth(authInfo);

		//I/O倉庫在庫
		form.setWarehouseStockList(itemService.getWarehouseStockList(form.getSysItemId()));
		itemService.setBeforWarehouseInfo(form.getWarehouseStockList());
		form.setAddWarehouseStockList(itemService.initAddWarehousStockList());

		//O倉庫
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));
		form.setAddWarehouseStockList(itemService.initAddWarehousStockList());
		form.setWarehouseLength(form.getWarehouseList().size());

		// I/O外部倉庫
		form.setExternalWarehouseStockList(itemService.getExternalStockList(form.getSysItemId(), null));
		itemService.setBeforExternalWarehouseInfo(form.getExternalWarehouseStockList());
		form.setExternalWarehouseList(new ArrayList<>(new WarehouseService().getExternalWarehouseList()));


		//O工場
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

		//O不良在庫
		form.setDeadStockList(itemService.getDeadStockList(form.getSysItemId()));
		form.setAddDeadStockList(itemService.initAddDeadStockList());

		//O更新情報
		form.setUpdateDataHistoryList(itemService.getUpdateDataHistoryList(form.getSysItemId()));

		//I/O入荷予定
		form.setArrivalScheduleList(itemService.getArrivalScheduleList(form.getSysItemId()));
//		form.setAddArrivalScheduleList(itemService.initAddArrivalScheduleList());
		//O入荷履歴
		form.setArrivalHistoryList(itemService.getArrivalHistoryList(form.getSysItemId()));

		//I/O売価・原価
		form.setItemCostList(itemService.getItemCostList(form.getSysItemId()));
		form.setItemPriceList(itemService.getItemPriceList(form.getSysItemId()));

		//O法人
		form.setCorporationList(new CorporationService().getCorporationList());

		//I/Oバックオーダー
		form.setBackOrderList(itemService.getBackOrderList(form.getSysItemId()));
		form.setAddBackOrderList(itemService.initAddBackOrderList());

		//キープ
		form.setKeepList(itemService.getKeepList(form.getSysItemId()));
		form.setAddKeepList(itemService.initAddKeepList());

		//外部倉庫キープ
		form.setExternalKeepList(itemService.getExternalKeepList(form.getSysItemId()));
		form.setAddExternalKeepList(itemService.initAddExternalKeepList());

		//I/O説明書
		form.setItemManualList(itemService.getItemManualList(form.getSysItemId()));
		if (form.getItemManualList() != null && form.getItemManualList().size() > 0) {
			itemService.changeItemManualList(form.getItemManualList());
		}
		form.setAddItemManualList(itemService.initAddItemManualList());

		//O販売チャネル
		form.setChannelList(new ChannelService().getChannelList());

		//最終更新者情報の取得
		form.setExtendMstUserDTO(userService.getUserName(form.getMstItemDTO().getUpdateUserId()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 商品更新処理
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
		//I/O商品
		form.setErrorMessageDTO(itemService.updateItem(form.getMstItemDTO()));
		if (!form.getErrorMessageDTO().isSuccess()) {
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
//		//更新情報をインサート →必要になったら使ってください。
//		itemService.insertItemInfo(form.getMstItemDTO(), form.getMstItemDTO());


		//Kind原価
		itemService.updateKindCost(form.getMstItemDTO());

		//倉庫在庫バリデート
		form.setErrorMessageDTO(itemService.checkWarehouseStock(form.getWarehouseStockList(), form.getAddWarehouseStockList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		/*---ここから -- BONCRE-2700_在庫数不具合修正追加分 この処理をコメントアウトすることで前の状態に戻すことができます -----------------------*/

		//変動があった倉庫及び変動値を保存する
		Map<Long, Integer> fluctuationWarehouseValue =  new LinkedHashMap<>();
		//各倉庫の増分値を計算
		itemService.calculationWarehouseStock(form.getWarehouseStockList(), form.getWarehouseStockList(), fluctuationWarehouseValue);
		//DB上の最新倉庫情報をだす
		form.setWarehouseStockList(itemService.getLatestWarehouseList(form.getWarehouseStockList(),
																												fluctuationWarehouseValue,
																												form.getMstItemDTO().getSysItemId()));

		/*--- ここまで -- BONCRE-2700_在庫数不具合修正追加分 この処理をコメントアウトすることで前の状態に戻すことができます -----------------------*/

		//I/O倉庫在庫
		itemService.updateWarehouseStockList(form.getWarehouseStockList());
		itemService.registryWarehouseStockList(form.getAddWarehouseStockList(), form.getMstItemDTO().getSysItemId());
		itemService.insertWarehouseInfo(form.getWarehouseStockList(), form.getWarehouseStockList(), "0");
		//追加分は必要？
//		itemService.insertWarehouseInfo(form.getAddWarehouseStockList(), form.getAddWarehouseStockList());


		/*
		 * 外部倉庫
		 */
		// 変更があった倉庫及び変動値を保存する。
		Map<String, Integer> fluctuationEWValue =  new LinkedHashMap<>();

		//各倉庫の増分値を計算
		itemService.calculationExternalWarehouseStock(form.getExternalWarehouseStockList(), form.getExternalWarehouseStockList(), fluctuationEWValue);
		//DB上の最新倉庫情報をだす
		form.setExternalWarehouseStockList(itemService.getLatestExternalWarehouseList(form.getExternalWarehouseStockList(), fluctuationEWValue, form.getMstItemDTO().getSysItemId()));

		//I/O倉庫在庫
		itemService.updateExternalWarehouseStockList(form.getExternalWarehouseStockList());
		itemService.insertExternalWarehouseInfo(form.getExternalWarehouseStockList(), form.getExternalWarehouseStockList(), "0");



		//I/O入荷予定
		itemService.updateArrivalScheduleList(form.getArrivalScheduleList(), form.getMstItemDTO().getSysItemId());
		itemService.registryArrivalScheduleList(form.getAddArrivalScheduleList(), form.getMstItemDTO().getSysItemId());

		//I/O売価・原価
		itemService.updateItemPriceList(form.getItemPriceList());
		itemService.updateItemCostList(form.getItemCostList());

		//I/Oバックオーダー
		itemService.updateBackOrderList(form.getBackOrderList());
		itemService.registryBackOrderList(form.getAddBackOrderList(), form.getMstItemDTO().getSysItemId());

		//I/O説明書
		itemService.updateItemManualList(form.getItemManualList());
		itemService.registryFileUpLoadList(form.getItemManualList(), form.getAddItemManualList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

		/*
		 * キープ処理
		 */
		//キープ
		form.setErrorMessageDTO(itemService.checkKeep(form.getKeepList(), form.getAddKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.updateKeepList(form.getKeepList());
		itemService.registryKeepList(form.getAddKeepList(), form.getMstItemDTO().getSysItemId());
//		itemService.updateTemporaryStockNum(form.getMstItemDTO().getSysItemId());

		//キープ数の更新が終わった後にこの処理を行うと仮在庫の更新も一気に更新が行えるのでこの場所にあります
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());


		//外部倉庫キープ
		form.setErrorMessageDTO(itemService.checkExternalKeep(form.getExternalKeepList(), form.getAddExternalKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		itemService.updateExternalKeepList(form.getExternalKeepList());
		itemService.registryExternalKeepList(form.getAddExternalKeepList(), form.getMstItemDTO().getSysItemId());




		//I不良在庫
		itemService.updateDeadStocklist(form.getDeadStockList());
		itemService.registryDeadStocklist(form.getDeadStockList(), form.getAddDeadStockList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

//		//I更新情報
//		itemService.registryUpdateData(form);

		form.setSysItemId(form.getMstItemDTO().getSysItemId());
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
		//セット商品の組立可数・構成部品も含めて総計算
//		itemService.setAllAssemblyNum(form.getSysItemId());
//		itemService.setAllSetItemAssemblyNum();

		commit();

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("更新しました");
		form.setRegistryMessageDTO(registryMessageDTO);

//		 saveToken(request);

		detailItem(appMapping, form, request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward lumpUpdateStock(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws DaoException {


		//■保留　在庫数変更する場合排他処理が必要かもしれない
		ItemService itemService = new ItemService();

		itemService.stockNumLoad(form.getItemList());

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * 商品削除処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteItem(AppActionMapping appMapping,
			ItemForm form, HttpServletRequest request) throws Exception {

		ItemService itemService = new ItemService();

		//商品削除
		itemService.deleteItem(form.getMstItemDTO().getSysItemId());
		//倉庫在庫削除
		itemService.deleteWarehouseStock(form.getMstItemDTO().getSysItemId());

		//外部倉庫在庫削除
		itemService.deleteExternalStock(form.getMstItemDTO().getSysItemId());

		//外部倉庫キープ削除
		itemService.deleteExternalKeepOfItem(form.getMstItemDTO().getSysItemId());

		//入荷予定削除
		itemService.deleteArrivalSchedule(form.getMstItemDTO().getSysItemId());
		//原価削除
		itemService.deleteCost(form.getMstItemDTO().getSysItemId());
		//売価削除
		itemService.deletePrice(form.getMstItemDTO().getSysItemId());
		//バックオーダー削除
		itemService.deleteBackOrder(form.getMstItemDTO().getSysItemId());
		//品番照合削除
		itemService.deleteCodeCollation(form.getMstItemDTO().getSysItemId());
		//構成商品削除
		itemService.deleteFormItem(form.getMstItemDTO().getSysItemId());
		//不良在庫削除
		itemService.deleteDeadStock(form.getMstItemDTO().getSysItemId());
		//年間販売数削除
		itemService.deleteAnnualSales(form.getMstItemDTO().getSysItemId());
		//海外注文書商品削除
		itemService.deleteForeignItem(form.getMstItemDTO().getSysItemId());

		form.setAlertType(WebConst.ALERT_TYPE_DELETE);

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("削除しました");
		form.setRegistryMessageDTO(registryMessageDTO);

		return initRegistryItem(appMapping, form, request);

//		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	//20140328 八鍬
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
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fileName.getBytes("Shift_JIS");
		fileName = new String(sJis, "ISO8859_1");

		OutputStream os = response.getOutputStream();
		File fileOut = new File(fileName);

		try {
			FileInputStream hFile = new FileInputStream(filePath);
			BufferedInputStream bis = new BufferedInputStream(hFile);

			System.out.println(response.getContentType());

			// レスポンス設定
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
	 * 在庫一覧画面から選択されたDLタイプのExcelを出力する
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward itemListDownLoad(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(userId));
		String filePath = "";
		String authInfo = form.getMstUserDTO().getOverseasInfoAuth();

		//ダウンロードタイプを確認し、テンプレート設定
		switch (form.getSearchItemDTO().getDownloadType()) {
		//商品情報テンプレートのパス
		case WebConst.DOWNLOAD_TYPE_CODE1:
			if (form.getMstUserDTO().getOverseasInfoAuth().equals(WebConst.AUTH_INFO_OK)) {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_INFO_LIST_TEMPLATE_PATH);
			} else {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_INFO_LIST_NOT_AUTH_PATH);
			}
			break;
		//在庫情報テンプレートのパス
		case WebConst.DOWNLOAD_TYPE_CODE2:
			filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.STOCK_ITEM_INFO_LIST_TEMPLATE_PATH);
			break;
		//価格情報テンプレートのパス
		case WebConst.DOWNLOAD_TYPE_CODE3:
			if (form.getMstUserDTO().getOverseasInfoAuth().equals(WebConst.AUTH_INFO_OK)) {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.PRICE_INFO_LIST_TEMPLATE_PATH);
			} else {
				filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.NOT_AUTH_PRICE_INFO_LIST_TEMPLATE_PATH);
			}
			break;
		//助ネコCSVテンプレートのパス
		case WebConst.DOWNLOAD_TYPE_CODE4:
			filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_LIST_TEMPLATE_PATH);
			break;
		// 通常のDL機能（新規DL機能が実装されるまで！）
		case StringUtils.EMPTY:
			filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.ITEM_LIST_TEMPLATE_PATH);
			break;
		//新在庫Excelテンプレートのパス
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

		// ファイルを読み込みます。
//		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		OPCPackage pkg = OPCPackage.open(filePath);
		// ワークブックを読み込みます。
		XSSFWorkbook workBook = new XSSFWorkbook(pkg);

		//エクセルファイルを編集します
		ExportItemListService exportItemListService = new ExportItemListService();

		ItemService itemService = new ItemService();
//		itemService.setFlags(form.getSearchItemDTO());

		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "";
		int sheetNo = 0;
		//ダウンロードタイプを確認し、Excelに書き出し
		switch (form.getSearchItemDTO().getDownloadType()) {
		//商品情報
		// 現在は使用しない二回目の本番リリース時までに作成する
		case WebConst.DOWNLOAD_TYPE_CODE1:
			workBook = exportItemListService.getExportItemInfoList(form.getSearchItemDTO(), workBook, authInfo, sheetNo);
			fname = "商品詳細情報_" + date + ".xlsx";
			break;
		//在庫情報
		case WebConst.DOWNLOAD_TYPE_CODE2:
			form.setSysItemIdList(itemService.getSysItemIdList(form.getSearchItemDTO()));
			workBook = exportItemListService.getExportStockItemInfoList(form.getSysItemIdList(), workBook, sheetNo);
			fname = "在庫情報_" + date + ".xlsx";
			break;
		//価格情報
		case WebConst.DOWNLOAD_TYPE_CODE3:
			form.setSysItemIdList(itemService.getSysItemIdList(form.getSearchItemDTO()));
			workBook = exportItemListService.getExportPriceInfoList(form.getSysItemIdList(), workBook, authInfo, sheetNo);
			fname = "価格情報_" + date + ".xlsx";
			break;
		//助ネコCSV
		case WebConst.DOWNLOAD_TYPE_CODE4:
			workBook = exportItemListService.getExportItemList(form.getSearchItemDTO(), workBook);
			break;
		// 通常のDL機能（新規機能が全て実装されるまで！！）
		case StringUtils.EMPTY:
			workBook = exportItemListService.getExportItemList(form.getSearchItemDTO(), workBook);
			fname = "商品情報_" + date + ".xlsx";
			break;
		//新在庫Excel
		case WebConst.DOWNLOAD_TYPE_CODE5:

			form.setSysItemIdList(itemService.getSysItemIdList(form.getSearchItemDTO()));
			sheetNo = 0;
			workBook = exportItemListService.getExportItemInfoList(form.getSearchItemDTO(), workBook, authInfo, sheetNo);
			sheetNo = 1;
			workBook = exportItemListService.getExportStockItemInfoList(form.getSysItemIdList(), workBook, sheetNo);
			sheetNo = 2;
			workBook = exportItemListService.getExportPriceInfoList(form.getSysItemIdList(), workBook, authInfo, sheetNo);

			fname = "新在庫情報_" + date + ".xlsx";
			break;
		case WebConst.DOWNLOAD_TYPE_CODE6:
			workBook = exportItemListService.getKeepOrderList(form.getSearchItemDTO(), workBook);
			fname = "受注_" + date + ".xlsx";
			break;
		}

		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// エクセルファイル出力
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
	 * [概要] 在庫一覧の検索結果からCSVファイルを出力します。
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

		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		// ファイル名設定
		String fname = "助ネコCSV_" + date + ".csv";

		//CSVファイルを編集します
		ExportItemListService exportItemListService = new ExportItemListService();

		//CSV出力処理
		exportItemListService.exportCsvList(form.getSearchItemDTO(), fname, response);

		exportItemListService.close();

		return null;
	}

	/**
	 * [概要]仕入先情報を検索します。
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
	 * 注文プール追加処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward addOrderPool(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンス生成
		ItemService service = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//注文プール追加処理
		ErrorMessageDTO errorMessageDTO = service.addOrderPool(form.getItemList());
		if (!errorMessageDTO.isSuccess()) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("商品を注文プールに追加しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");
		}
		return itemList(appMapping, form, request);
	}

	/**
	 * 注文プールから削除する処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteOrderPool(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンス生成
		ItemService service = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//注文プール追加処理
		ErrorMessageDTO errorMessageDTO = service.deleteOrderPool(form.getItemList());
		if (!errorMessageDTO.isSuccess()) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);

		} else {
			//更新件数1件もない場合、再検索せずに画面に返却
			if (errorMessageDTO.getResultCnt() == 0) {
				messageDTO.setMessageFlg("1");
				messageDTO.setMessage("更新対象が選択されていません");
				form.setRegistryDto(messageDTO);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("商品を注文プールから" + errorMessageDTO.getResultCnt() + "件削除しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");
		}

		return itemList(appMapping, form, request);
	}

	/**
	 * 組立可数を更新する処理メソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward updAssemblyNum(AppActionMapping appMapping, ItemForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//インスタンス生成
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
