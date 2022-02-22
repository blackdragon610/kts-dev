package jp.co.kts.ui.mst;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.CurrencyLedgerDTO;
import jp.co.kts.app.common.entity.ForeignSlipSearchDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.app.search.entity.SupplierSearchDTO;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.currencyLedger.CurrencyLedgerService;
import jp.co.kts.service.fileExport.ExportForeignOrderService;
import jp.co.kts.service.fileExport.ExportForeignOrderTransfer2Service;
import jp.co.kts.service.fileExport.ExportForeignOrderTransferService;
import jp.co.kts.service.fileExport.ExportItemListService;
import jp.co.kts.service.mst.SupplierService;
import jp.co.kts.service.mst.WarehouseService;
import jp.co.kts.ui.web.struts.WebConst;
import net.arnx.jsonic.JSON;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.service.item.ItemService;


/**
 * [概要] 海外注文actionクラス
 * @author Boncre
 *
 */
public class ForeignOrderAction extends AppBaseAction {

	//海外チェックフラグ定数
	private static final String CHECK_BOX_FLG_ON = "on";
	//検索結果0件
	private static final int SEARCH_RESULT_CNT = 0;
	//メッセージタイプ
	//成功
	private static final String RESULT_MESSAGE_TYPE0 = "0";
	//登録失敗
	private static final String RESULT_MESSAGE_TYPE1 = "1";
	//更新失敗
	private static final String RESULT_MESSAGE_TYPE2 = "2";

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ForeignOrderForm form = (ForeignOrderForm)appForm;

		//海外注文伝票一覧の初期表示を行う処理
		if ("/initForeignOrderList".equals(appMapping.getPath())) {
			return initForeignOrderList(appMapping, form, request);

		//海外注文伝票を検索する処理
		} else if ("/searchForeignOrder".equals(appMapping.getPath())) {
			return searchForeignOrder(appMapping, form, request);

		//海外注文一覧画面に戻る処理
		} else if ("/returnForeignOrderList".equals(appMapping.getPath())) {
			return returnForeignOrderList(appMapping, form, request);

		//海外注文伝票を更新する処理
		} else if ("/updateForeignOrder".equals(appMapping.getPath())) {
			return updateForeignOrder(appMapping, form, request);

		//海外注文伝票を登録する初期表示を行う
		} else if ("/initRegistryForeignOrder".equals(appMapping.getPath())) {
			return initRegistryForeignOrder(appMapping, form, request);

		//海外注文伝票を登録する処理
		} else if ("/registryForeignOrder".equals(appMapping.getPath())) {
			return registryForeignOrder(appMapping, form, request);

		//海外注文伝票詳細を取得する処理
		} else if ("/detailForeignOrderSlip".equals(appMapping.getPath())) {
			return detailForeignOrderSlip(appMapping, form, request);

		//仕入先詳細情報の小ウィンドウによる検索を行う処理
		} else if ("/subWinSupplierSearch".equals(appMapping.getPath())) {
			return subWinSupplierSearch(appMapping, form, request);

		//登録済み商品削除処理
		} else if ("/deleteForeignOrderItem".equals(appMapping.getPath())) {
			return deleteForeignOrderItem(appMapping, form, request);

		//海外注文伝票を削除する処理
		} else if ("/resetForeignOrderItem".equals(appMapping.getPath())) {
			return resetForeignOrderItem(appMapping, form, request, response);

		//登録済み商品削除処理
		} else if ("/deleteForeignOrderItem".equals(appMapping.getPath())) {
			return deleteForeignOrderItem(appMapping, form, request);

		//海外伝票の検索結果ダウンロード機能
		} else if ("/foreignOrderListDownLoad".equals(appMapping.getPath())) {
			return foreignOrderListDownLoad(appMapping, form, request, response);

		//海外伝票を一括削除する処理
		} else if ("/lumpForeignSlipDelete".equals(appMapping.getPath())) {
			return lumpForeignSlipDelete(appMapping, form, request);

		//海外伝票をまとめてステータス移動する処理
		} else if ("/lumpOrderStatusMove".equals(appMapping.getPath())) {
			return lumpOrderStatusMove(appMapping, form, request);

		//海外伝票一覧画面のページング処理
		} else if ("/foreignSlipPageNo".equals(appMapping.getPath())) {
			return foreignSlipPageNo(appMapping, form, request);

		//サブウィンドウによる注文プール商品検索初期表示
		} else if ("/subWinInitOrderPoolItemSearch".equals(appMapping.getPath())) {
			return subWinInitOrderPoolItemSearch(appMapping, form, request);

		//サブウィンドウによる注文プール商品検索処理
		} else if ("/subWinOrderPoolItemSearch".equals(appMapping.getPath())) {
			return subWinOrderPoolItemSearch(appMapping, form, request);

		//海外商品の一括入荷初期処理
		} else if ("/initLumpArrival".equals(appMapping.getPath())) {
			return initLumpArrival(appMapping, form, request);

		//海外商品の一括入荷処理
		} else if ("/lumpArrival".equals(appMapping.getPath())) {
			return lumpArrival(appMapping, form, request);

		//海外伝票の一括支払初期処理
		} else if ("/initLumpPayment".equals(appMapping.getPath())) {
			return initLumpPayment(appMapping, form, request);

		//海外伝票の一括支払処理
		} else if ("/lumpPayment".equals(appMapping.getPath())) {
			return lumpPayment(appMapping, form, request);

		//海外注文書作成画面、注文書出力
		} else if ("/exportForeignOrderAcceptanceList".equals(appMapping.getPath())) {
			 return exportForeignOrderAcceptanceList(appMapping, form, request, response);

		//海外注文書作成画面、注文書出力succes
		} else if ("/foreignOrderAcceptancePrintOutFile".equals(appMapping.getPath())) {
			return foreignOrderAcceptancePrintOutFile(response);

		//海外振込依頼書１作成画面、注文書出力
		} else if ("/exportForeignOrderTransferFst".equals(appMapping.getPath())) {
			 return exportForeignOrderTransferFst(appMapping, form, request, response);

		//海外振込依頼書２作成画面、注文書出力
		} else if ("/exportForeignOrderTransferSnd".equals(appMapping.getPath())) {
			 return exportForeignOrderTransferSnd(appMapping, form, request, response);

		//海外振込依頼書１作成画面、注文書出力succes
		} else if ("/foreignOrderTransferPrintOutFileFst".equals(appMapping.getPath())) {
			return foreignOrderTransferPrintOutFileFst(response);

		//海外振込依頼書２作成画面、注文書出力succes
		} else if ("/foreignOrderTransferPrintOutFileSnd".equals(appMapping.getPath())) {
			return foreignOrderTransferPrintOutFileSnd(response);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * [概要] 海外注文一覧画面を表示する
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	private ActionForward initForeignOrderList(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) {

		//メッセージ初期化
		form.setRegistryDto(new RegistryMessageDTO());
		form.setErrorMessageDTO(new ErrorMessageDTO());

		//DTOの初期化
		form.setForeignSlipSearchDTO(new ForeignSlipSearchDTO());
		form.setForeignOrderDTO(new ExtendForeignOrderDTO());
		form.setForeignOrderSlipList(new ArrayList<ExtendForeignOrderDTO>());

		//初期表示の値を設定
		form.setOrderStatus("");
		form.getForeignSlipSearchDTO().setSearchPriority("1");
		form.getForeignSlipSearchDTO().setArriveStatusFlg("4");
		form.getForeignSlipSearchDTO().setPaymentStatus1Flg("3");
		form.getForeignSlipSearchDTO().setPaymentStatus2Flg("3");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]海外注文伝票を検索する
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward searchForeignOrder(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		ForeignSlipSearchDTO searchDTO = new ForeignSlipSearchDTO();
		searchDTO = form.getForeignSlipSearchDTO();
		form.setErrorMessageDTO(new ErrorMessageDTO());

		//ステータス移動から呼び出されていない場合、メッセージを初期化
		if (!form.getMessageFlg().equals("1")) {
			//メッセージ初期化
			form.setRegistryDto(messageDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE0);
		}

		//フラグ読み替え
		foreignOrderService.setSearchFlags(searchDTO);
		//検索対象のID件数を取得
		form.setSysForeignSlipIdList(foreignOrderService.getSysForeignSlipIdList(searchDTO));
		form.setForeignSlipSearchDTO(searchDTO);

		//ページ設定
		form.setSysForeignSlipIdListSize(form.getSysForeignSlipIdList().size());
		form.setForeignOrderListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(searchDTO.getListPageMax()));
		form.setPageIdx(0);

		//取得したIDに基づき、伝票情報の検索を行う
		form.setForeignOrderSlipList(foreignOrderService.getForeignOrderSlipSearch(form.getSysForeignSlipIdList(), form.getPageIdx(), searchDTO));

		//フラグ読み替え
		foreignOrderService.setSearchFlags(searchDTO);

		//検索結果が0件だった場合のエラー判定
		if (form.getSysForeignSlipIdList().size() == SEARCH_RESULT_CNT
				&& !form.getMessageFlg().equals("1")) {
			messageDTO.setMessageFlg(RESULT_MESSAGE_TYPE1);
			messageDTO.setMessage("検索結果が0件です。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]海外注文伝票のページング処理を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward foreignSlipPageNo(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		ForeignOrderService foreignOrderService = new ForeignOrderService();

		//メッセージDTOの初期化
		form.setRegistryDto(messageDTO);
		form.setMessageFlg(RESULT_MESSAGE_TYPE0);

		//ページング処理
		form.setForeignOrderSlipList(foreignOrderService.getForeignOrderSlipSearch(form.getSysForeignSlipIdList(), form.getPageIdx(), form.getForeignSlipSearchDTO()));
		form.setForeignOrderListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getForeignSlipSearchDTO().getListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


//	/**
//	 * [概要] 海外注文一覧画面(検索結果)に戻る
//	 * @param appMapping
//	 * @param form
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	private ActionForward returnForeignOrderList(AppActionMapping appMapping,
//			ForeignOrderForm form, HttpServletRequest request) throws Exception {
//
//		//登録メッセージの初期化
//		form.setRegistryDto(new RegistryMessageDTO());
//
//		//検索結果が無ければfalse
//		if (form.getSysForeignSlipIdList() == null || form.getSysForeignSlipIdList().size() <= 0) {
//
//			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
//		}
//
//		//インスタンス生成
//		ForeignOrderService foreignOrderService = new ForeignOrderService();
//
//		//検索
//		form.setForeignOrderSlipList(foreignOrderService.getForeignOrderSlipSearch(form.getSysForeignSlipIdList(), form.getPageIdx(), form.getForeignSlipSearchDTO()));
//		//検索結果ページ数を取得
//		form.setForeignOrderListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getForeignSlipSearchDTO().getListPageMax()));
//
//		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
//
////		return searchForeignOrder(appMapping, form, request);
//	}

	/**
	 * [概要] 海外注文一覧画面(検索結果)に戻る
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward returnForeignOrderList(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		///登録メッセージの初期化
		form.setRegistryDto(messageDTO);
		form.setMessageFlg("0");
		form.setErrorMessageDTO(new ErrorMessageDTO());

		//検索結果が無ければfalse
		if (form.getSysForeignSlipIdList() == null || form.getSysForeignSlipIdList().size() <= 0) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		ForeignSlipSearchDTO searchDTO = form.getForeignSlipSearchDTO();
		//フラグ読み替え
		foreignOrderService.setSearchFlags(searchDTO);
		//検索対象のID件数を取得
		form.setSysForeignSlipIdList(foreignOrderService.getSysForeignSlipIdList(searchDTO));
		form.setForeignSlipSearchDTO(searchDTO);

		//ページ設定
		form.setSysForeignSlipIdListSize(form.getSysForeignSlipIdList().size());
		form.setForeignOrderListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(searchDTO.getListPageMax()));

		//取得したIDに基づき、伝票情報の検索を行う
		form.setForeignOrderSlipList(foreignOrderService.getForeignOrderSlipSearch(form.getSysForeignSlipIdList(), form.getPageIdx(), searchDTO));

		//フラグ読み替え
		foreignOrderService.setSearchFlags(searchDTO);

		//検索結果が0件だった場合のエラー判定
		if (form.getSysForeignSlipIdList().size() == SEARCH_RESULT_CNT
				&& !form.getMessageFlg().equals("1")) {
			messageDTO.setMessageFlg(RESULT_MESSAGE_TYPE1);
			messageDTO.setMessage("検索結果が0件です。");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setMessageFlg(RESULT_MESSAGE_TYPE0);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 海外注文情報を登録する初期表示を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initRegistryForeignOrder(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		if (!form.getMessageFlg().equals("1")) {
			//メッセージ初期化
			registryDto.setRegistryMessage(StringUtils.EMPTY);
			form.setRegistryDto(registryDto);
		}

		form.setMessageFlg(RESULT_MESSAGE_TYPE0);

		//インスタンス生成
		ExtendForeignOrderDTO foreignOrderDTO = new ExtendForeignOrderDTO();
		ForeignOrderService foreignOrderService = new ForeignOrderService();

		//初期表示ステータスのセット
		foreignOrderDTO.setOrderStatus("0");
		//追加用海外注文商品リストを初期化
		form.setAddItemList(new ArrayList<ExtendForeignOrderItemDTO>());
		//表示用海外注文商品リストを初期化
		form.setItemList(new ArrayList<ExtendForeignOrderItemDTO>());

		//注文書情報初期化
		form.setForeignOrderDTO(foreignOrderDTO);
		//仕入先情報初期化
		form.setSupplierDTO(new ExtendMstSupplierDTO());
		form.setSysSupplierId(0);

		//ユーザー情報取得
		foreignOrderService.initSetPersonInCharge(foreignOrderDTO);
		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyy/MM/dd");
		form.getForeignOrderDTO().setRegistDate(date);
		form.getForeignOrderDTO().setOrderDate(date);

		//納期1超過フラグ読替
		form.getForeignOrderDTO().setDeliveryDate1OverFlag("on");
		//納期2超過フラグ読替
		form.getForeignOrderDTO().setDeliveryDate2OverFlag("on");

		//追加用海外注文商品リストを設定
		form.setAddItemList(foreignOrderService.initAddItemList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]海外注文情報を登録する
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward registryForeignOrder(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンスを生成
		ForeignOrderService service = new ForeignOrderService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();

		//メッセージ初期化
		form.setRegistryDto(registryDto);
		form.setMessageFlg(RESULT_MESSAGE_TYPE0);

		//Form仕入先から海外注文DTOにset
//		long supplierId = Long.valueOf(form.getSysSupplierId());
		form.getForeignOrderDTO().setSysSupplierId(form.getSysSupplierId());

		//PoNo.を付与
		service.createPoNo(form.getForeignOrderDTO(), form.getSysSupplierId());

		begin();

		//海外注文伝票登録
		int resultSlip = service.registryForeignOrderSlip(form.getForeignOrderDTO());

		//伝票登録成功判定
		if(resultSlip != 1) {
			rollback();
			form.getForeignOrderDTO().setSysForeignSlipId(0);
			setFailMessage(form, RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//商品CODE、商品名バリデート
		registryDto = service.ForeignOrderItemValidate(registryDto, form.getAddItemList(), form.getItemList(), form.getSysSupplierId());

		if (!registryDto.isSuccess()) {
			rollback();
			List<ErrorMessage> message = new ArrayList<ErrorMessage>();
			message.addAll(registryDto.getResult().getErrorMessages());
			form.getForeignOrderDTO().setSysForeignSlipId(0);
			saveErrorMessages(request, message);
			setFailMessage(form, RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//登録される商品の数を数えておく
		int itemTargetNum = 0;
		for (ExtendForeignOrderItemDTO item : form.getAddItemList()) {
			if (StringUtils.isBlank(item.getItemCode())) {
				continue;
			}
			itemTargetNum++;
		}

		//海外注文商品登録
		int resultItem = service.registryForeignOrderItemList(form.getAddItemList(), form.getForeignOrderDTO().getSysForeignSlipId());

		//商品登録成功判定
		//登録件数が対象商品数と違う場合エラー
		if(resultItem != itemTargetNum) {
			rollback();
			form.getForeignOrderDTO().setSysForeignSlipId(0);
			setFailMessage(form, RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//入荷予定テーブルに登録
		int resultArrivalCnt = service.registryArrival(form.getAddItemList(), form.getForeignOrderDTO().getSysForeignSlipId() ,form.getForeignOrderDTO().getOrderStatus(), form.getForeignOrderDTO().getOrderDate());

		//登録件数が対象商品数と違う場合エラー
		if (itemTargetNum != resultArrivalCnt) {
			rollback();
			form.getForeignOrderDTO().setSysForeignSlipId(0);
			setFailMessage(form, RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//登録した商品からorderPoolFlagを0、注文数を0に設定
		service.setOrderPoolFlag(form.getAddItemList());

		commit();
		//登録完了メッセージ
		registryDto.setMessageFlg("0");
		registryDto.setMessage("登録しました。");
		form.setMessageFlg(RESULT_MESSAGE_TYPE1);

		//追加用海外注文商品リストを初期化
		form.setAddItemList(new ArrayList<ExtendForeignOrderItemDTO>());
		//追加用海外注文商品リストを設定
		form.setAddItemList(service.initAddItemList());

		//token用に伝票IDをFormにセット
		form.setSysForeignSlipId(form.getForeignOrderDTO().getSysForeignSlipId());

		//tokenセット
		saveToken(request);

		return detailForeignOrderSlip(appMapping, form, request);
	}

	/**
	 * [概要]海外注文伝票詳細を表示する処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward detailForeignOrderSlip(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		if (!form.getMessageFlg().equals("1")) {

			// メッセージ初期化
			registryDto.setRegistryMessage(StringUtils.EMPTY);
			form.setRegistryDto(registryDto);
			form.setMessageFlg(RESULT_MESSAGE_TYPE0);
		}

		//インスタンスを生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();

		//伝票情報をシステム海外注文伝票IDから取得
		form.setForeignOrderDTO(foreignOrderService.getdetail(form.getSysForeignSlipId()));
		//登録された商品を取得
		form.setItemList(foreignOrderService.getForeignItemSearch(form.getSysForeignSlipId()));


		if (form.getDetailFlag() == "0") {

			// 追加用海外注文商品リストを設定
			form.setAddItemList(foreignOrderService.initAddItemList());
		}

		// 詳細画面処理フラグ初期化
		form.setDetailFlag("0");

		// 訂正フラグにチェックがあればonに変換
		if (StringUtils.equals(form.getForeignOrderDTO().getCorrectionFlag(), "1")) {
			form.getForeignOrderDTO().setCorrectionFlag("on");
		}

		// 納期1超過フラグ読替
		if (StringUtils.equals(form.getForeignOrderDTO().getDeliveryDate1OverFlag(), "1")) {
			form.getForeignOrderDTO().setDeliveryDate1OverFlag("on");
		}

		//納期2超過フラグ読替
		if (StringUtils.equals(form.getForeignOrderDTO().getDeliveryDate2OverFlag(), "1")) {
			form.getForeignOrderDTO().setDeliveryDate2OverFlag("on");
		}

		//インスタンスを生成
		SupplierService supplierService = new SupplierService();
		//通貨IDと通貨名を連結して通貨リストに格納
		List<CurrencyLedgerDTO> currencyList = supplierService.connectCurrencyList(new CurrencyLedgerService().getCurrencyLedger());
		//通貨Listをformにセット
		form.setCurrencyList(currencyList);

		//システム仕入先IDから仕入先情報のセット
		form.setSysSupplierId(form.getForeignOrderDTO().getSysSupplierId());
		form.setSupplierDTO(foreignOrderService.getForeignOrderDetail(form.getSysSupplierId()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 海外注文情報を更新する
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateForeignOrder(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンスを生成
		ForeignOrderService service = new ForeignOrderService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryDto);
		begin();

		//商品CODE、商品名バリデート
		registryDto = service.ForeignOrderItemValidate(registryDto, form.getAddItemList(), form.getItemList(), form.getSysSupplierId());

		if (!registryDto.isSuccess()) {
			List<ErrorMessage> message = new ArrayList<ErrorMessage>();
			message.addAll(registryDto.getResult().getErrorMessages());
			saveErrorMessages(request, message);
			setFailMessage(form, RESULT_MESSAGE_TYPE2);
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//念のため仕入先IDをDTOにセット
		form.getForeignOrderDTO().setSysSupplierId(form.getSysSupplierId());

		//伝票部分更新
		int result = service.updateForeignOrderSlip(form.getForeignOrderDTO());
		//ステータス、更新
//		result = service.updateForeignOrderSlipArrival(form.getForeignOrderDTO(), form.getForeignOrderDTO().getSysForeignSlipId());

		//更新成功判定処理
		if (result != 1){
			rollback();
			setFailMessage(form, RESULT_MESSAGE_TYPE2);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//倉庫情報のbefore検索
		form.setWarehouseStockList(service.getWarehouseInfo(form.getItemList()));
		//I/O倉庫在庫更新
		result = service.updateWarehouseStockList(form.getItemList());
		//倉庫情報のafter検索
		form.setAddWarehouseStockList(service.getWarehouseInfo(form.getItemList()));
		//倉庫在庫履歴作成
		result = service.insertWarehouseInfo(form.getItemList(), form.getWarehouseStockList(), form.getAddWarehouseStockList());

		//商品部分更新
		result = service.updateForeignOrderItemList(form.getItemList(), form.getForeignOrderDTO().getSysForeignSlipId());
		//追加商品部分登録
		result = service.registryForeignOrderItemList(form.getAddItemList(), form.getForeignOrderDTO().getSysForeignSlipId());

		//I/O入荷予定更新
		result = service.updateArrivalScheduleList(form.getItemList() ,form.getForeignOrderDTO().getOrderStatus() ,form.getForeignOrderDTO().getOrderDate());
		//入荷予定テーブルに登録
		result = service.registryArrivalScheduleList(form.getAddItemList(), form.getForeignOrderDTO());

		//キープ数の更新が終わった後にこの処理を行うと仮在庫の更新も一気に更新が行えるのでこの場所にあります
		result = service.updateTotalStockNum(form.getItemList());

		//更新成功判定処理
		if (result != 1){
			setFailMessage(form, RESULT_MESSAGE_TYPE2);
			rollback();
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//登録完了メッセージ
		registryDto.setMessageFlg("0");
		registryDto.setMessage("更新しました。");
		form.setMessageFlg(RESULT_MESSAGE_TYPE1);

		//登録した商品からorderPoolFlagを０に設定
		service.setOrderPoolFlag(form.getAddItemList());

		//追加用海外注文商品リストを初期化
		form.setAddItemList(new ArrayList<ExtendForeignOrderItemDTO>());
		commit();

		//tokenセット
		saveToken(request);

		return detailForeignOrderSlip(appMapping, form, request);
	}


	/**
	 * 登録済み商品の削除処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteForeignOrderItem(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		begin();
		//インスタンス生成
		ForeignOrderService service = new ForeignOrderService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();

		int deleteCnt = 0;
		//削除対象の件数をカウント
		for (ExtendForeignOrderItemDTO dto: form.getItemList()) {
			if (StringUtils.equals(dto.getDeleteCheckFlg(), "on")) {
				//商品削除用CNT
				deleteCnt++;
			}
		}

		//登録されたものの削除
		service.setItemFlags(form.getItemList());
		//商品と入荷予定にデリートフラグ付与
		int resultDeleteCnt = service.deleteItem(form.getItemList());

		//一括削除成功判定
		if (deleteCnt != resultDeleteCnt) {
			rollback();
			registryDto.setMessageFlg("1");
			registryDto.setMessage("商品の削除に失敗しました。");
			form.setRegistryDto(registryDto);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else if (deleteCnt == resultDeleteCnt) {
			registryDto.setMessageFlg("0");
			registryDto.setMessage("商品を削除しました。");
			form.setRegistryDto(registryDto);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
		}

		//詳細画面で商品追加リストを初期化させないため
		form.setDetailFlag("1");

		commit();
		detailForeignOrderSlip(appMapping, form, request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 登録済み商品のリセット処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward resetForeignOrderItem(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		begin();
		//インスタンス生成
		ExtendArrivalScheduleDTO arrivalDto = new ExtendArrivalScheduleDTO();
		arrivalDto.setSysItemId(new Long(request.getParameter("sys_item_id")));
		arrivalDto.setSysForeignSlipId(new Long(request.getParameter("sys_foreign_slip_id")));
		arrivalDto.setSysForeignSlipItemId(new Long(request.getParameter("sys_foreign_slip_item_id")));
		arrivalDto.setArrivalNum(new Integer(request.getParameter("order_num")));
		ItemService itemService = new ItemService();

		itemService.resetLumpArrivalSchedule(arrivalDto, new Long(request.getParameter("sys_arrival_schedule_id")));
		commit();
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(request.getParameter("sys_item_id")));

		return null;
	}

	/**
	 * [概要] 仕入先情報を検索するサブウィンドウを表示、初期検索
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward subWinSupplierSearch(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//メッセージを空にするため
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		registryDto.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDto(registryDto);

		//検索条件初期化
		form.setSearchSupplierList(new ArrayList<ExtendMstSupplierDTO>());
		//検索結果初期化
		form.setOrderPoolSearchResultItemList(new ArrayList<ExtendForeignOrderItemDTO>());

		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		//検索
		form.setSearchSupplierList(foreignOrderService.getSearchSupplierList(form.getSupplierSearchDTO().getSysSupplierId()));
		//検索数取得
		form.setSupplierListSize(form.getSearchSupplierList().size());

		form.setSupplierSearchDTO(new SupplierSearchDTO());

		//検索結果数が０件の場合アラート
		if (form.getSupplierListSize() == SEARCH_RESULT_CNT) {

			registryDto.setMessageFlg("1");
			registryDto.setMessage("検索結果は0件です。");
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * サブウィンドウ、注文プールの検索初期表示
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 */
	private ActionForward subWinInitOrderPoolItemSearch(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) {

		//メッセージを空にするため
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		registryDto.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDto(registryDto);

		//検索結果数初期化
		form.setOrderPoolItemListSize(0);
		//検索条件初期化
		form.setSearchItemDTO(new SearchItemDTO());
		//検索結果初期化
		form.setOrderPoolSearchResultItemList(new ArrayList<ExtendForeignOrderItemDTO>());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * サブウィンドウ、注文プールの検索
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward subWinOrderPoolItemSearch(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		ForeignOrderService service = new ForeignOrderService();
		//メッセージを空にするため
		registryDto.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDto(registryDto);

		//子画面、注文プール検索
		form.setOrderPoolSearchResultItemList(service.getSearchOrderPoolList(form.getSearchItemDTO()));
		//検索結果数を取得、セット
		form.setOrderPoolItemListSize(form.getOrderPoolSearchResultItemList().size());

		//検索結果数が０件の場合アラート
		if (form.getOrderPoolItemListSize() == SEARCH_RESULT_CNT) {

			registryDto.setMessageFlg("1");
			registryDto.setMessage("検索結果は0件です。");
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要] 海外注文情報を削除する
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteForeignOrder(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンスの生成
		ForeignOrderService service = new ForeignOrderService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();

		//メッセージを空にするため
		registryDto.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDto(registryDto);

		begin();
		//伝票情報削除
		registryDto = service.detailDeleteSlip(form.getForeignOrderDTO(), form.getItemList());

		//一括削除成功判定
		if (!registryDto.isSuccess()) {
			rollback();
			registryDto.setMessageFlg("1");
			registryDto.setMessage("伝票の削除に失敗しました。");
			form.setRegistryDto(registryDto);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			form.getErrorMessageDTO().setErrorMessage("削除対象の伝票がありません。大変申し訳ありませんが、管理者に連絡してください");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			registryDto.setMessageFlg("0");
			registryDto.setMessage("削除が完了し、初期画面に戻りました。");
			form.setRegistryDto(registryDto);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
		}

		commit();
		return initRegistryForeignOrder(appMapping, form, request);

	}

	/**
	 * [概要]海外注文伝票の一括削除を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	private ActionForward lumpForeignSlipDelete(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		begin();
		ForeignOrderService foreignOrderService = new ForeignOrderService();

		//選択された伝票を一括削除する
		RegistryMessageDTO messageDTO = foreignOrderService.lumpDeleteSlip(form.getForeignOrderSlipList());

		//一括削除成功判定
		if (!messageDTO.isSuccess()) {
			rollback();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("削除に失敗しました。");
			messageDTO.setErrorMessage("対象の伝票がありません。大変申し訳ありませんが、管理者に連絡してください");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			commit();
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("削除が完了しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
		}

		return searchForeignOrder(appMapping, form, request);
	}

	/**
	 * [概要]海外注文伝票の検索結果ダウンロードを行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward foreignOrderListDownLoad(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//テンプレートのパス取得
		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.FOREIGN_LIST_TEMPLATE_PATH);
		// ファイルを読み込みます。
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ワークブックを読み込みます。
		HSSFWorkbook workBook = new HSSFWorkbook(filein);
		//エクセルファイルを編集します
		ExportItemListService exportItemListService = new ExportItemListService();
		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "";

		workBook = exportItemListService.getExportForeignList(form.getForeignSlipSearchDTO(), workBook);
		fname = "海外注文一覧情報_" + date + ".xls";

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
	 * [概要]海外注文伝票の一括入荷初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initLumpArrival(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {


		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();

		//メッセージ初期化
		form.setRegistryDto(new RegistryMessageDTO());
		form.setErrorMessageDTO(new ErrorMessageDTO());

		//伝票検索結果0件判定処理
		if (form.getForeignOrderSlipList() == null || form.getForeignOrderSlipList().size() <= SEARCH_RESULT_CNT) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//エラーメッセージ初期化
		form.setErrorMessageDTO(new ErrorMessageDTO());

		//一括入荷リストを作成する。
		form.setLumpArrivalList(foreignOrderService.getLumpArrivalList(form.getForeignOrderSlipList()));

		//倉庫リストの取得
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]海外注文伝票の一括入荷処理を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward lumpArrival(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		RegistryMessageDTO registryDTO = new RegistryMessageDTO();

		begin();
		//一括入荷処理を行い、入荷予定と倉庫在庫に反映させる
		ErrorMessageDTO errorMessageDTO = foreignOrderService.lumpArrival(form.getLumpArrivalList());

		form.setRegistryDto(registryDTO);
		form.setMessageFlg("0");

		//DB更新 成功判定
		if (!errorMessageDTO.isSuccess()) {
			rollback();
			registryDTO.setMessageFlg("1");
			registryDTO.setMessage("一括入荷に失敗しました");
			form.setRegistryDto(registryDTO);
			form.setErrorMessageDTO(errorMessageDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			commit();
			registryDTO.setMessageFlg("0");
			registryDTO.setMessage("一括入荷が完了しました");
			form.setRegistryDto(registryDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
		}

		//一括入荷終了後、再検索してから一括入荷画面に戻る(入荷済の商品を再表示させないため)
		searchForeignOrder(appMapping, form, request);

		initLumpArrival(appMapping, form, request);

		//tokenセット
		saveToken(request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]海外注文伝票の一括支払初期処理を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initLumpPayment(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		//検索結果0件判定処理
		if (form.getForeignOrderSlipList() == null || form.getForeignOrderSlipList().size() <= SEARCH_RESULT_CNT) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//エラーメッセージ初期化
		form.setErrorMessageDTO(new ErrorMessageDTO());

		ForeignOrderService foreignOrderService = new ForeignOrderService();
		//一括支払リストを作成する
		form.setLumpPaymentList(foreignOrderService.getSysForeignSlipIdList(form.getForeignSlipSearchDTO()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]海外注文伝票の一括支払処理を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward lumpPayment(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		ForeignOrderService foreignOrderService = new ForeignOrderService();
		RegistryMessageDTO registryDTO = new RegistryMessageDTO();

		begin();
		//一括支払処理
		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

		errorMessageDTO = foreignOrderService.lumpPayment(form.getLumpPaymentList(), errorMessageDTO);

		//DB更新 成功判定
		if (!errorMessageDTO.isSuccess()) {
			rollback();
			registryDTO.setMessageFlg("1");
			registryDTO.setMessage("一括支払に失敗しました");
			form.setRegistryDto(registryDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			form.setErrorMessageDTO(errorMessageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			commit();
			registryDTO.setMessageFlg("0");
			registryDTO.setMessage("一括支払に成功しました");
			form.setRegistryDto(registryDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
		}

		initLumpPayment(appMapping, form, request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * [概要]注文ステータスをまとめて移動する処理を行う
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward lumpOrderStatusMove(AppActionMapping appMapping,
			ForeignOrderForm form, HttpServletRequest request) throws Exception {

		begin();

		//更新対象件数
		int targetCnt = 0;
		//更新結果件数
		int resultCnt = 0;
		//インスタンス生成
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
		List<ExtendForeignOrderDTO> targetList = new ArrayList<ExtendForeignOrderDTO>();


		//更新する件数をカウントしておく
		for (ExtendForeignOrderDTO dto: form.getForeignOrderSlipList()) {
			if(dto.getCheckBoxFlg().equals(CHECK_BOX_FLG_ON)) {
				targetCnt++;
				targetList.add(dto);
			}
		}

		errorMessageDTO = foreignOrderService.checkStatusMove(errorMessageDTO, targetList, form.getOrderStatus());
		//ステータスチェック結果エラー時
		if (!errorMessageDTO.isSuccess()) {
			registryDto.setMessageFlg("1");
			form.setRegistryDto(registryDto);
			form.setErrorMessageDTO(errorMessageDTO);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		resultCnt = foreignOrderService.updateForeignListStatus(targetList,
				form.getOrderStatus());
		//更新失敗時
		if (targetCnt != resultCnt) {
			rollback();
			registryDto.setMessageFlg("1");
			registryDto.setMessage("ステータス移動に失敗しました。");
			errorMessageDTO.setErrorMessage("対象の伝票がありません。大変申し訳ありませんが、管理者に連絡してください");
			form.setRegistryDto(registryDto);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
			form.setErrorMessageDTO(errorMessageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		//更新成功時
		} else {
			commit();
			registryDto.setMessageFlg("0");
			registryDto.setMessage("ステータス移動が完了しました。");
			form.setRegistryDto(registryDto);
			form.setMessageFlg(RESULT_MESSAGE_TYPE1);
		}

		form.setOrderStatus("");
		return searchForeignOrder(appMapping, form, request);
	}


	/**
	 * 注文書出力
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportForeignOrderAcceptanceList(AppActionMapping appMapping, ForeignOrderForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンスを生成する
		ForeignOrderService service = new ForeignOrderService();
		ExtendForeignOrderDTO dto = new ExtendForeignOrderDTO();
		List<ExtendForeignOrderItemDTO> list = new ArrayList<ExtendForeignOrderItemDTO>();


		//出力する伝票を取得し、ExtendDTOに格納
		dto = service.getdetail(form.getForeignOrderDTO().getSysForeignSlipId());
		//登録された商品を取得、ExtendDTOに格納
		list.addAll(service.getForeignItemOrderSearch(form.getForeignOrderDTO().getSysForeignSlipId()));

		//取得したExportDTOがnullだったらエラー
		if (dto == null || list == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{

			//サービスで出力内容を設定
			ExportForeignOrderService exportService = new ExportForeignOrderService();
			exportService.orderAcceptanceList(response, dto, list);


		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			throw e;
		}

//		form.getDomesticOrderSlipDto().setItemOrderDate(StringUtil.getToday());
		return null;
	}

	/**
	 * 注文書出力
	 * succes時
	 *
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ActionForward foreignOrderAcceptancePrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "orderAcceptance.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "注文書" + fileNmTimeFormat.format(date) + ".pdf";

		ExportForeignOrderService exportService = new ExportForeignOrderService();

		try {
			exportService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;

		}
		return null;
	}

	/**
	 * 振込依頼書出力１
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportForeignOrderTransferFst(AppActionMapping appMapping, ForeignOrderForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンスを生成する
		ForeignOrderService service = new ForeignOrderService();
		ExtendForeignOrderDTO dto = new ExtendForeignOrderDTO();
		List<ExtendForeignOrderItemDTO> list = new ArrayList<ExtendForeignOrderItemDTO>();
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		List<ExtendMstSupplierDTO> supList = new ArrayList<ExtendMstSupplierDTO>();

		//出力する伝票を取得し、ExtendDTOに格納
		dto = service.getdetail(form.getForeignOrderDTO().getSysForeignSlipId());
		//登録された商品を取得、ExtendDTOに格納
		list.addAll(service.getForeignItemSearch(form.getForeignOrderDTO().getSysForeignSlipId()));
		//仕入先検索
		supList.addAll(foreignOrderService.getSearchSupplierList(form.getSysSupplierId()));

		//振込先依頼書が１か２をセット
		dto.setTransferPatern("1");

		//取得したExportDTOがnullだったらエラー
		if (dto == null || list == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{

			//サービスで出力内容を設定
			ExportForeignOrderTransferService exportService = new ExportForeignOrderTransferService();
			exportService.orderAcceptanceList(response, dto, list, supList);

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			throw e;
		}

//		form.getDomesticOrderSlipDto().setItemOrderDate(StringUtil.getToday());
		return null;
	}

	/**
	 * 振込依頼書出力2
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportForeignOrderTransferSnd(AppActionMapping appMapping, ForeignOrderForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンスを生成する
		ForeignOrderService service = new ForeignOrderService();
		ExtendForeignOrderDTO dto = new ExtendForeignOrderDTO();
		List<ExtendForeignOrderItemDTO> list = new ArrayList<ExtendForeignOrderItemDTO>();
		ForeignOrderService foreignOrderService = new ForeignOrderService();
		List<ExtendMstSupplierDTO> supList = new ArrayList<ExtendMstSupplierDTO>();

		//出力する伝票を取得し、ExtendDTOに格納
		dto = service.getdetail(form.getForeignOrderDTO().getSysForeignSlipId());
		//登録された商品を取得、ExtendDTOに格納
		list.addAll(service.getForeignItemSearch(form.getForeignOrderDTO().getSysForeignSlipId()));
		//仕入先検索
		supList.addAll(foreignOrderService.getSearchSupplierList(form.getSysSupplierId()));

		//振込先依頼書が１か２をセット
		dto.setTransferPatern("2");

		//取得したExportDTOがnullだったらエラー
		if (dto == null || list == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{

			//サービスで出力内容を設定
			ExportForeignOrderTransfer2Service exportService = new ExportForeignOrderTransfer2Service();
			exportService.orderAcceptanceList(response, dto, list, supList);

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			throw e;
		}

//		form.getDomesticOrderSlipDto().setItemOrderDate(StringUtil.getToday());
		return null;
	}


	/**
	 * 振込依頼書出力１
	 * succes時
	 *
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ActionForward foreignOrderTransferPrintOutFileFst(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "ordertrance1.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "振込依頼書1" + fileNmTimeFormat.format(date) + ".pdf";

		ExportForeignOrderTransferService exportService = new ExportForeignOrderTransferService();

		try {
			exportService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;

		}
		return null;
	}

	/**
	 * 振込依頼書出力2
	 * succes時
	 *
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected ActionForward foreignOrderTransferPrintOutFileSnd(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "ordertrance2.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "振込依頼書2" + fileNmTimeFormat.format(date) + ".pdf";

		ExportForeignOrderTransfer2Service exportService = new ExportForeignOrderTransfer2Service();

		try {
			exportService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;

		}
		return null;
	}

	private void setFailMessage(ForeignOrderForm form , String messageType) {
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		registryDto.setMessageFlg("1");
		if (messageType.equals("1")) {
			registryDto.setMessage("登録に失敗しました。");
		} else if (messageType.equals("2")) {
			registryDto.setMessage("更新に失敗しました。");
		}

		form.setMessageFlg("1");
		form.setRegistryDto(registryDto);
	}
}
