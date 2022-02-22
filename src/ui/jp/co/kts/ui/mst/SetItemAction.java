package jp.co.kts.ui.mst;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import com.itextpdf.text.DocumentException;

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
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.ChannelService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.MakerService;
import jp.co.kts.service.mst.SetItemService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.service.mst.WarehouseService;
import jp.co.kts.ui.web.struts.WebConst;

public class SetItemAction extends AppBaseAction {

	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SetItemForm form = (SetItemForm)appForm;

		//セット商品一覧画面表示処理メソッド
		if ("/initSetItemList".equals(appMapping.getPath())) {
			return initSetItemList(appMapping, form, request);
		//セット商品新規登録画面初期表示処理メソッド
		} else if("/initRegistrySetItem".equals(appMapping.getPath())) {
			return initRegistrySetItem(appMapping, form, request);
		//セット商品新規登録処理メソッド
		} else if ("/registrySetItem".equals(appMapping.getPath())) {
			return registrySetItem(appMapping, form, request);
		//セット商品詳細表示処理メソッド
		} else if ("/detailSetItem".equals(appMapping.getPath())) {
			return detailSetItem(appMapping, form, request);
		//セット商品更新処理メソッド
		} else if ("/updateSetItem".equals(appMapping.getPath())) {
			return updateSetItem(appMapping, form, request);
		//セット商品削除処理メソッド
		} else if ("/deleteSetItem".equals(appMapping.getPath())) {
			return deleteSetItem(appMapping, form, request);
		//資料ダウンロード処理メソッド
		} else if ("/setItemManualDownLoad".equals(appMapping.getPath())) {
			return setItemManualDownLoad(appMapping, form, request, response);
		} else if ("/subWinSetItemSupplierSearch".equals(appMapping.getPath())) {
			return subWinSetItemSupplierSearch(appMapping, form, request);
		//SET商品の構成商品の金額を合算する。
		} else if ("/sumFormItemPrice".equals(appMapping.getPath())){
			return sumFormItemPrice(appMapping, form, request, response);
		}
//		else if ("/subWinItemSearchSetItem".equals(appMapping.getPath())) {
//			return subWinItemSearchSetItem(appMapping, form, request);
//		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * セット商品一覧画面表示処理メソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initSetItemList(AppActionMapping appMapping, SetItemForm form,
	            HttpServletRequest request) throws Exception {

		ItemService service = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		form.setRegistryDto(messageDTO);
		form.setSetItemList(service.getAllSetItemList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }

	/**
	 * セット商品新規登録画面初期表示処理メソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 protected ActionForward initRegistrySetItem(AppActionMapping appMapping, SetItemForm form,
	            HttpServletRequest request) throws Exception {
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(userId));
		String authInfo = form.getMstUserDTO().getOverseasInfoAuth();
		form.setOverseasInfoAuth(authInfo);

		//I/O商品
		ItemService itemService = new ItemService();
		form.setMstItemDTO(itemService.initMstItem());

		//I/Oメーカー
		MakerService makerService = new MakerService();
		form.setMstMakerList(makerService.getMakerInfo());

		//I/O倉庫在庫
		form.setWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>());
		form.setAddWarehouseStockList(new ArrayList<ExtendWarehouseStockDTO>(itemService.initAddWarehousStockList()));

		//O倉庫
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));
		form.setWarehouseLength(form.getWarehouseList().size());

		//I工場リスト
		form.setSupplierList(new ArrayList<ExtendMstSupplierDTO>());

		//I/O不良在庫
		form.setDeadStockList(new ArrayList<DeadStockDTO>());
		form.setAddDeadStockList(new ArrayList<>(itemService.initAddDeadStockList()));

		//I/O売価・原価
		form.setItemCostList(itemService.initItemCostList());
		form.setItemPriceList(itemService.initItemPriceList());

		//O法人
		form.setCorporationList(new CorporationService().getCorporationList());

		//I/Oバックオーダー
		form.setBackOrderList(new ArrayList<BackOrderDTO>());
		form.setAddBackOrderList(new ArrayList<BackOrderDTO>(itemService.initAddBackOrderList()));

		//I/Oセット商品
		form.setSetItemList(new ArrayList<ExtendSetItemDTO>());
		form.setAddSetItemList(new ArrayList<ExtendSetItemDTO>(itemService.initAddSetItemList()));

		//I/O説明書
		form.setItemManualList(new ArrayList<ExtendItemManualDTO>());
		form.setAddItemManualList(itemService.initAddItemManualList());

		//O販売チャネル
		form.setChannelList(new ChannelService().getChannelList());

		//キープ
		form.setKeepList(new ArrayList<ExtendKeepDTO>());
		form.setAddKeepList(itemService.initAddKeepList());

		//I/O入荷予定
		form.setArrivalScheduleList(new ArrayList<ExtendArrivalScheduleDTO>());

		//I入荷履歴
		form.setArrivalHistoryList(new ArrayList<ExtendArrivalScheduleDTO>());

		//組立可数
		form.setAssemblyNum(0);

		//I工場リスト
		form.setSupplierList(new ArrayList<ExtendMstSupplierDTO>());
		form.setSupplierDTO(new ExtendMstSupplierDTO());

		//I/O不良在庫
		form.setDeadStockList(new ArrayList<DeadStockDTO>());
		form.setAddDeadStockList(new ArrayList<>(itemService.initAddDeadStockList()));

		//更新情報
		form.setUpdateDataHistoryList(new ArrayList<UpdateDataHistoryDTO>());
		//合算取得フラグ
		form.setSumPurcharPriceFlg("0");

		//エラーメッセージ初期化
		form.setErrorMessageDTO(new ErrorMessageDTO());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	 }

	 /**
	  * セット商品新規登録処理メソッド
	  * @param appMapping
	  * @param form
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 private ActionForward registrySetItem(AppActionMapping appMapping,
				SetItemForm form, HttpServletRequest request) throws Exception {

		ItemService itemService = new ItemService();
		SetItemService setItemService = new SetItemService();
		itemService.setFlags(form.getMstItemDTO());
		//I/O商品
		form.getMstItemDTO().setOrderAlertNum(0);
		form.setErrorMessageDTO(itemService.registryItem(setItemService.setSetItemFlg(form.getMstItemDTO())));

		if ((form.getRegistryMessageDTO().getMessageFlg()).equals("1")) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}


		//年間販売数情報登録:
		itemService.registryAnnualSales(form.getMstItemDTO().getSysItemId());

		//I/O倉庫在庫
		itemService.registryWarehouseStockList(form.getAddWarehouseStockList(), form.getMstItemDTO().getSysItemId());
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());

		//I/O売価・原価
		itemService.registryItemPriceList(form.getItemPriceList(), form.getMstItemDTO().getSysItemId());
		itemService.registryItemCostList(form.getItemCostList(), form.getMstItemDTO().getSysItemId());

		//I/Oバックオーダー
		itemService.registryBackOrderList(form.getAddBackOrderList(), form.getMstItemDTO().getSysItemId());

		//I/O構成商品
		itemService.registrySetItemList(form.getAddSetItemList(), form.getMstItemDTO().getSysItemId());

		//I/O説明書
		itemService.registryFileUpLoadList(form.getItemManualList(), form.getAddItemManualList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

		//I/O不良在庫
		itemService.registryDeadStocklist(form.getDeadStockList(), form.getAddDeadStockList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

//		//I更新情報
//		itemService.registryUpdateData(form);

		//キープ
		form.setErrorMessageDTO(itemService.checkKeep(form.getKeepList(), form.getAddKeepList()));
		if ((form.getRegistryMessageDTO().getMessageFlg()).equals("1")) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		itemService.updateKeepList(form.getKeepList());
		itemService.registryKeepList(form.getAddKeepList(), form.getMstItemDTO().getSysItemId());
		//キープ数の更新が終わった後にこの処理を行うと仮在庫の更新も一気に更新が行えるのでこの場所にあります
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());

		form.setSysItemId(form.getMstItemDTO().getSysItemId());

		/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
		//セット商品の組立可数・構成部品も含めて総計算
//		itemService.setAllAssemblyNum(form.getSysItemId());
		//登録成功
		commit();
		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("登録しました");
		form.setRegistryMessageDTO(registryMessageDTO);

		//登録後再検索
		ItemService service = new ItemService();
		form.setSetItemList(service.getAllSetItemList());

		//return detailSetItem(appMapping, form, request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * セット商品詳細表示処理メソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward detailSetItem(AppActionMapping appMapping, SetItemForm form,
			HttpServletRequest request) throws Exception {

		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//注文プール追加メソッドから呼び出されていない場合、メッセージを初期化
		if (!form.getMessageFlg().equals("1")) {
			form.setRegistryMessageDTO(messageDTO);
		}
		form.setMessageFlg("0");

		//合算処理フラグ初期化
		form.setSumPurcharPriceFlg("0");
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

		//O工場
		String supplierId = "";
		form.setSupplierDTO(new ExtendMstSupplierDTO());
		if (form.getMstItemDTO().getSysSupplierId() != 0) {
			form.setSupplierDTO(itemService.getSupplier(form.getMstItemDTO().getSysSupplierId()));
			supplierId = String.valueOf(form.getMstItemDTO().getSysSupplierId());
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

		//O販売チャネル
		form.setChannelList(new ChannelService().getChannelList());

		//I/Oセット商品
		form.setSetItemList(itemService.getSetItemList(form.getSysItemId()));
		form.setAddSetItemList(itemService.initAddSetItemList());

		//I/O説明書
		form.setItemManualList(itemService.getItemManualList(form.getSysItemId()));
		if (form.getItemManualList() != null && form.getItemManualList().size() > 0) {
			itemService.changeItemManualList(form.getItemManualList());
		}
		form.setAddItemManualList(itemService.initAddItemManualList());

		//キープ
		form.setKeepList(itemService.getKeepList(form.getSysItemId()));
		form.setAddKeepList(itemService.initAddKeepList());

		//組立可数
		form.setAssemblyNum(form.getMstItemDTO().getAssemblyNum());

		//最終更新者情報の取得
		form.setExtendMstUserDTO(userService.getUserName(form.getMstItemDTO().getUpdateUserId()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * セット商品更新処理メソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateSetItem(AppActionMapping appMapping,
			SetItemForm form, HttpServletRequest request) throws Exception {

		begin();

		ItemService itemService = new ItemService();
		itemService.setFlags(form.getMstItemDTO());
		//I/O商品
		form.setErrorMessageDTO(itemService.updateItem(form.getMstItemDTO()));
		if (!form.getErrorMessageDTO().isSuccess()) {
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//倉庫在庫バリデート
		form.setErrorMessageDTO(itemService.checkWarehouseStock(form.getWarehouseStockList(), form.getAddWarehouseStockList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//Kind原価
		itemService.updateKindCost(form.getMstItemDTO());

		//I/O倉庫在庫
		itemService.updateWarehouseStockList(form.getWarehouseStockList());
		itemService.registryWarehouseStockList(form.getAddWarehouseStockList(), form.getMstItemDTO().getSysItemId());
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());
		itemService.insertWarehouseInfo(form.getWarehouseStockList(), form.getWarehouseStockList(), "0");

		//I/O売価・原価
		itemService.updateItemPriceList(form.getItemPriceList());
		itemService.updateItemCostList(form.getItemCostList());

		//I/Oバックオーダー
		itemService.updateBackOrderList(form.getBackOrderList());
		itemService.registryBackOrderList(form.getAddBackOrderList(), form.getMstItemDTO().getSysItemId());

		//I/Oセット商品
		itemService.updateSetItemList(form.getSetItemList());
		itemService.registrySetItemList(form.getAddSetItemList(), form.getMstItemDTO().getSysItemId());

		//I/O説明書
		itemService.updateItemManualList(form.getItemManualList());
		itemService.registryFileUpLoadList(form.getItemManualList(), form.getAddItemManualList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

		//I不良在庫
		itemService.updateDeadStocklist(form.getDeadStockList());
		itemService.registryDeadStocklist(form.getDeadStockList(), form.getAddDeadStockList(), form.getMstItemDTO().getSysItemId(), form.getMstItemDTO());

//		//I更新情報
//		itemService.registryUpdateData(form);

		//キープ
		form.setErrorMessageDTO(itemService.checkKeep(form.getKeepList(), form.getAddKeepList()));
		if (!form.getErrorMessageDTO().isSuccess()) {

			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		itemService.updateKeepList(form.getKeepList());
		itemService.registryKeepList(form.getAddKeepList(), form.getMstItemDTO().getSysItemId());

		//キープ数の更新が終わった後にこの処理を行うと仮在庫の更新も一気に更新が行えるのでこの場所にあります
		itemService.updateTotalStockNum(form.getMstItemDTO().getSysItemId());

		form.setSysItemId(form.getMstItemDTO().getSysItemId());

		/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
		//組立可数・構成商品も含めて総計算
//		itemService.setAllAssemblyNum(form.getSysItemId());

		commit();

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("更新しました");
		form.setRegistryMessageDTO(registryMessageDTO);
		form.setMessageFlg("1");

		saveToken(request);

		detailSetItem(appMapping, form, request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * セット商品削除処理メソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteSetItem(AppActionMapping appMapping,
			SetItemForm form, HttpServletRequest request) throws Exception {

		ItemService itemService = new ItemService();

		//商品削除
		itemService.deleteItem(form.getMstItemDTO().getSysItemId());
		//倉庫在庫削除
		itemService.deleteWarehouseStock(form.getMstItemDTO().getSysItemId());
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
		form.setAlertType(WebConst.ALERT_TYPE_DELETE);

		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setMessageFlg("0");
		registryMessageDTO.setRegistryMessage("削除しました");
		form.setRegistryMessageDTO(registryMessageDTO);

		//削除後再検索
		ItemService service = new ItemService();
		form.setSetItemList(service.getAllSetItemList());


//		return initRegistrySetItem(appMapping, form, request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 資料ダウンロード処理メソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws DaoException
	 * @throws DocumentException
	 * @throws IOException
	 */
	private ActionForward setItemManualDownLoad(AppActionMapping appMapping, SetItemForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException, DocumentException, IOException {

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

	private ActionForward subWinSetItemSupplierSearch(AppActionMapping appMapping, SetItemForm form, HttpServletRequest request) throws Exception {

		form.setSupplierList(new ArrayList<ExtendMstSupplierDTO>());
		ItemService itemService = new ItemService();
		form.setSupplierList(itemService.getSupplierList(form.getMstItemDTO().getSysSupplierId()));

		form.setSupplierListSize(form.getSupplierList().size());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * SET商品の構成商品の金額を合算する。
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sumFormItemPrice(AppActionMapping appMapping, SetItemForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンス生成
		ItemService service = new ItemService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//メッセージを初期化
		form.setRegistryMessageDTO(messageDTO);
		//原価の合算処理
		service.getItemCostSumList(form.getMstItemDTO().getSysItemId(), form);
		//売価の合算処理→現在は使用しない
//		service.getItemPriceSumList(form.getSysItemId(), form);
		service.getSumPurcharPrice(form.getMstItemDTO().getSysItemId(), form);

		form.setSumPurcharPriceFlg("1");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

}
