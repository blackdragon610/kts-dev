/**
 *
 */
package jp.co.kts.ui.btobBill;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.BtobBillItemDTO;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendBtobBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.CorporateSaleListTotalDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.search.entity.CorporateSaleCostSearchDTO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.btobBill.BtobBillCorporateSaleCostService;
import jp.co.kts.service.btobBill.BtobBillSaleCostService;
import jp.co.kts.service.btobBill.BtobBillService;
import jp.co.kts.service.fileExport.ExportCorporateBillService;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.ChannelService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.service.mst.WarehouseService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.sale.CorporateSaleForm;
import jp.co.kts.ui.web.struts.WebConst;
import net.arnx.jsonic.JSON;


/**
 * 法人間請求書機能Actionクラス<br>
 * 業務ロジッククラス<br>
 *<br>
 * 作成日　：2015/12/15<br>
 * 作成者　：大山智史<br>
 * 更新日　：<br>
 * 更新者　：<br>
 *
 */
public class BtobBillAction extends AppBaseAction {

	/**
	 * 法人間請求書機能<br>
	 * 原価一覧　　　　：Kind原価を在庫情報に対して、登録・変更を行う。<br>
	 * 売上原価入力　　：売上商品情報を一覧表示する。<br>
	 * 　　　　　　　　：売上商品情報の原価など金額の情報を一括で編集する。<br>
	 * 業販原価入力　　：業販商品情報を一覧表示する。<br>
	 * 　　　　　　　　：業販商品情報の原価など金額の情報を一括で編集する。<br>
	 * 法人間請求書管理：Kindから各法人への請求書を月を指定して作成する。<br>
	 * 　　　　　　　　：請求データに入金情報・フリーワードの編集が可能。<br>
	 * 　　　　　　　　：請求データをPDFに出力する。<br>
	 *
	 */
	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BtobBillForm form = (BtobBillForm)appForm;

		if ("/initKindCostList".equals(appMapping.getPath())) {
			return initKindCostList(appMapping, form, request);
		}else if("/kindCostList".equals(appMapping.getPath())){
			return kindCostList(appMapping, form, request);
		}else if("/kindCostListPageNo".equals(appMapping.getPath())){
			return kindCostListPageNo(appMapping, form, request);
		}else if("/detailKindCost".equals(appMapping.getPath())){
			return detailKindCost(appMapping, form, request);
		}else if("/registryKindCost".equals(appMapping.getPath())){
			return registryKindCost(appMapping, form, request);
		}else if("/initSaleCostList".equals(appMapping.getPath())){
			return initSaleCostList(appMapping, form, request);
		}else if("/saleCostList".equals(appMapping.getPath())){
			return saleCostList(appMapping, form, request);
		}else if("/saleCostListPageNo".equals(appMapping.getPath())){
			return saleCostListPageNo(appMapping, form, request);
		}else if("/editsaleCost".equals(appMapping.getPath())){
			return editsaleCost(appMapping, form, request);
		}else if("/saveSaleCost".equals(appMapping.getPath())){
			return saveSaleCost(appMapping, form, request);
		}else if("/saveSaleCostById".equals(appMapping.getPath())){
			return saveSaleCostById(appMapping, form, request, response);
		}else if("/reflectLatestSaleCostCost".equals(appMapping.getPath())){
			return reflectLatestSaleCostCost(appMapping, form, request);
		}else if("/reflectLatestSaleCostById".equals(appMapping.getPath())){
			return reflectLatestSaleCostById(appMapping, form, request, response);
		}else if("/reflectLatestSaleCostlist".equals(appMapping.getPath())){
			return reflectLatestSaleCostCost(appMapping, form, request);
		}else if("/initCorporateSaleCostList".equals(appMapping.getPath())){
			return initCorporateSaleCostList(appMapping, form, request);
		}else if("/corporateSaleCostList".equals(appMapping.getPath())){
			return corporateSaleCostList(appMapping, form, request);
		}else if("/corporateSaleCostListPageNo".equals(appMapping.getPath())){
			return corporateSaleCostListPageNo(appMapping, form, request);
		}else if("/editCorporateSaleCost".equals(appMapping.getPath())){
			return editCorporateSaleCost(appMapping, form, request);
		}else if("/initCorporateSaleDetail".equals(appMapping.getPath())){
			return initCorporateSaleDetail(appMapping, form, request);
		}else if("/savecorporateSaleCostById".equals(appMapping.getPath())){
			return savecorporateSaleCostById(appMapping, form, request, response);
		}else if("/savecorporateSaleCost".equals(appMapping.getPath())){
			return savecorporateSaleCost(appMapping, form, request);
		}else if("/reflectLatestCorporateSaleCost".equals(appMapping.getPath())){
			return reflectLatestCorporateSaleCost(appMapping, form, request);
		}else if("/reflectLatestCorporateSaleCostById".equals(appMapping.getPath())){
			return reflectLatestCorporateSaleCostById(appMapping, form, request, response);
		}else if("/btobBillList".equals(appMapping.getPath())){
			return btobBillList(appMapping, form, request);
		}else if("/btobBillListPageNo".equals(appMapping.getPath())){
			return btobBillListPageNo(appMapping, form, request);
		}else if("/createBtobBill".equals(appMapping.getPath())){
			return createBtobBill(appMapping, form, request);
		}else if("/searchBtobBill".equals(appMapping.getPath())){
			return searchBtobBill(appMapping, form, request);
		}else if("/updateBtobBill".equals(appMapping.getPath())){
			return updateBtobBill(appMapping, form, request);
		}else if("/deleteBtobBill".equals(appMapping.getPath())){
			return deleteBtobBill(appMapping, form, request);
		}else if("/exportBillBtobBill".equals(appMapping.getPath())){
			return exportBillBtobBill(appMapping, form, request, response);
		}else if("/billBtobBillPrintOutFile".equals(appMapping.getPath())){
			return billBtobBillPrintOutFile(response);
		}else if("/initBtobBillList".equals(appMapping.getPath())){
			return initBtobBillList(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}


	/**
	 * 原価一覧<br>
	 * Kind原価一覧画面の初期表示処理
	 */
	protected ActionForward initKindCostList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		// 在庫一覧リストを初期化
		form.setItemList(new ArrayList<ResultItemSearchDTO>());

		// M_倉庫から倉庫リストを取得して、Formに設定
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));

		// 在庫検索用DTOを初期化
		form.setSearchItemDTO(btobService.initItemListDTO());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 原価一覧<br>
	 * Kind原価一覧画面の検索・一覧表示処理<br>
	 * 　入力された検索条件にて、在庫情報を1ページ表示分を取得し、一覧表示する。
	 */
	protected ActionForward kindCostList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		// 在庫検索用DTOチェックボックスをキーから値に設定し直す。
		btobService.setFlags(form.getSearchItemDTO());

		// 在庫検索結果システム商品IDリストを取得し、Formに設定
		form.setSysItemIdList(btobService.getSysItemIdList(form.getSearchItemDTO()));

		// 在庫検索結果システム商品IDリストから

		//検索件数が０件の場合のメッセージ格納
		form.setErrorMessageDTO(btobService.checkItemList(form.getSysItemIdList()));

		// 確認結果がエラーの場合、総検索結果を0件に設定し、Kind原価一覧画面へ遷移
		if (!form.getErrorMessageDTO().isSuccess()) {
			form.setSysItemIdListSize(0);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 総検索結果を設定
		form.setSysItemIdListSize(form.getSysItemIdList().size());
		// ページを1ページ目に設定
		form.setPageIdx(0);

		// 1ページ目の在庫一覧リストを取得する
		form.setItemList(btobService.getItemList(form.getSysItemIdList(), form.getPageIdx(), form.getSearchItemDTO()));

		// 在庫検索用DTOチェックボックスを値からキーに設定し直す。
		btobService.setFlags(form.getSearchItemDTO());

		// 在庫一覧１ページ当りの最大表示件数を設定
		form.setItemListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSearchItemDTO().getListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 原価一覧<br>
	 * Kind原価一覧画面の一覧ページ遷移処理<br>
	 * 　一覧の指定されたページの表示分の在庫情報を取得し、一覧表示する。
	 */
	protected ActionForward kindCostListPageNo (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		// 在庫検索結果システム商品IDリストを保持しているか確認
		if (form.getSysItemIdList() == null || form.getSysItemIdList().size() <= 0) {
			// 保持していない場合、Kind原価一覧画面へ遷移
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定されたページの在庫一覧リストを取得する
		BtobBillService btobService = new BtobBillService();
		form.setItemList(btobService.getItemList(form.getSysItemIdList(), form.getPageIdx(), form.getSearchItemDTO()));
		form.setItemListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSearchItemDTO().getListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 原価一覧<br>
	 * Kind原価一覧画面の詳細画面遷移処理<br>
	 * 　指定した在庫情報の詳細情報を取得し、Kind原価詳細画面を表示する。
	 */
	protected ActionForward detailKindCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		// 在庫詳細情報を取得
		form.setMstItemDTO(btobService.getMstItemDTO(form.getSysItemId()));
		btobService.setFlags(form.getMstItemDTO());

		// 商品原価の更新者情報を取得
		UserService userService = new UserService();
		form.setMstUserDTO(userService.getUserName(form.getMstItemDTO().getUpdateUserId()));
		// 更新者情報が取得できない場合、初期化
		if(form.getMstUserDTO()==null){
			form.setMstUserDTO(new MstUserDTO());
		}

		//O法人
		form.setCorporationList(new CorporationService().getCorporationList());

		// 在庫詳細情報の周辺情報の取得は、在庫管理から流用
		ItemService itemService = new ItemService();
		//I/O倉庫在庫
		form.setWarehouseStockList(itemService.getWarehouseStockList(form.getSysItemId()));
		form.setAddWarehouseStockList(itemService.initAddWarehousStockList());

		//O倉庫
		form.setWarehouseList(new ArrayList<>(new WarehouseService().getWarehouseList()));
		form.setWarehouseLength(form.getWarehouseList().size());

		//I/O入荷予定
		form.setArrivalScheduleList(itemService.getArrivalScheduleList(form.getSysItemId()));
		form.setAddArrivalScheduleList(itemService.initAddArrivalScheduleList());
		//O入荷履歴
		form.setArrivalHistoryList(itemService.getArrivalHistoryList(form.getSysItemId()));

		//I/O売価
		form.setItemCostList(itemService.getItemCostList(form.getSysItemId()));
		form.setItemPriceList(itemService.getItemPriceList(form.getSysItemId()));

		//I/Oバックオーダー
		form.setBackOrderList(itemService.getBackOrderList(form.getSysItemId()));
		form.setAddBackOrderList(itemService.initAddBackOrderList());

		//キープ
		form.setKeepList(itemService.getKeepList(form.getSysItemId()));
		form.setAddKeepList(itemService.initAddKeepList());

		//I/O説明書
		form.setItemManualList(itemService.getItemManualList(form.getSysItemId()));
		if (form.getItemManualList() != null && form.getItemManualList().size() > 0) {
			itemService.changeItemManualList(form.getItemManualList());
		}
		form.setAddItemManualList(itemService.initAddItemManualList());

		//O販売チャネル
		form.setChannelList(new ChannelService().getChannelList());
		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 原価一覧<br>
	 * Kind原価詳細画面のKind原価登録処理<br>
	 * 　入力されたKind原価を在庫情報に登録する。
	 */
	protected ActionForward registryKindCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();


		//開始
		begin();

		// 更新者IDが0の場合、Kind原価を登録する
		if(form.getMstUserDTO().getSysUserId()==0){

			// Kind原価登録
			form.setErrorMessageDTO(btobService.registryKindCost(form.getMstItemDTO()));

			// 登録に失敗した場合、ロールバック
			if (!form.getErrorMessageDTO().isSuccess()) {
				rollback();
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			// 成功した場合、アラートの設定
			form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		}else {
		// 更新者IDが0以外の場合、Kind原価を更新する

			// Kind原価更新
			form.setErrorMessageDTO(btobService.updateKindCost(form.getMstItemDTO()));


			// 更新に失敗した場合、ロールバック
			if (!form.getErrorMessageDTO().isSuccess()) {
				rollback();
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}

			// 成功した場合、アラートの設定
			form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		}

		//登録成功
		commit();
		form.setSysItemId(form.getMstItemDTO().getSysItemId());

		// 詳細データ取得処理
		detailKindCost(appMapping,form,request);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上原価入力<br>
	 * 売上原価一覧画面の初期表示処理
	 */
	protected ActionForward initSaleCostList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {


		// 売上伝票リストを初期化
		form.setSalesCostList(new ArrayList<ExtendSalesItemDTO>());

		// 法人リストを取得
		CorporationService corporationService = new CorporationService();
		form.setCorporationList(corporationService.getCorporationList());

		// 販売チャンネルを取得
		ChannelService channelService = new ChannelService();
		form.setChannelList(channelService.getChannelList());


		// 検索条件を初期化
		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();
		form.setSaleCostSearchDTO(btobSaleService.initSaleCostSearchDTO());


		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上原価入力<br>
	 * 売上原価一覧画面の検索・一覧表示処理<br>
	 * 　入力された検索条件にて、売上商品情報を1ページ表示分を取得し、一覧表示する。
	 */
	protected ActionForward saleCostList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();

		// チェックボックスのフラグを値に変換
		btobSaleService.setFlags(form.getSaleCostSearchDTO());

		// 検索結果・システム売上商品IDリストをDBから取得し、formに設定
		form.setSysSaleItemIDList(btobSaleService.getSaleItemIDList(form.getSaleCostSearchDTO()));

		// チェックボックスの値をフラグに変換
		btobSaleService.setFlags(form.getSaleCostSearchDTO());

		//検索件数が０件の場合失敗
		if (form.getSysSaleItemIDList() == null || form.getSysSaleItemIDList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する検索結果はありません。");
			form.setErrorDTO(errorMessageDTO);

			// 一覧表示を初期化
			form.setSysSaleItemIDListSize(0);
			form.setSalesCostList(new ArrayList<ExtendSalesItemDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 検索結果の件数を原価一覧総検索結果件数に設定
		form.setSysSaleItemIDListSize(form.getSysSaleItemIDList().size());

		// 1ページ目の売上原価一覧リストを取得する。
		form.setSalesCostList(btobSaleService.getSaleCostList(form.getSysSaleItemIDList(), form.getSaleCostPageIdx(), form.getSaleCostSearchDTO()));

		form.setSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSaleCostSearchDTO().getListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上原価入力<br>
	 * 売上原価一覧画面の一覧ページ遷移処理<br>
	 * 　一覧の指定されたページの表示分の売上商品情報を取得し、一覧表示する。
	 */
	protected ActionForward saleCostListPageNo (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();

		// システム売上商品IDリストが0件の場合、元画面に遷移
		if (form.getSysSaleItemIDList() == null || form.getSysSaleItemIDList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定したページの売上原価一覧リストを取得する。
		form.setSalesCostList(btobSaleService.getSaleCostList(form.getSysSaleItemIDList(), form.getSaleCostPageIdx(), form.getSaleCostSearchDTO()));
		form.setSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSaleCostSearchDTO().getListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上原価入力<br>
	 * 売上原価一覧画面の売上原価一括編集画面遷移処理
	 */
	protected ActionForward editsaleCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();

		// システム売上商品IDリストが0件の場合、元画面に遷移
		if (form.getSysSaleItemIDList() == null || form.getSysSaleItemIDList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定したページの売上原価一覧リストを取得する。
		form.setSalesCostList(btobSaleService.getSaleCostList(form.getSysSaleItemIDList(), form.getSaleCostPageIdx(), form.getSaleCostSearchDTO()));
		form.setSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSaleCostSearchDTO().getListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上原価入力<br>
	 * 売上原価一括編集画面の入力内容登録処理
	 */
	protected ActionForward saveSaleCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();

		//開始
		begin();

		// 入力内容登録をDBに反映
		btobSaleService.updateSaleCostList(form.getSalesCostList());

		//登録成功
		commit();

		// アラートを設定
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		// 売上原価一覧を再取得
		editsaleCost(appMapping, form, request);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward saveSaleCostById (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();

		DomesticExhibitionDAO domesticDAO = new DomesticExhibitionDAO();
		DomesticExhibitionDTO domesticDto = new DomesticExhibitionDTO();

		ExtendSalesSlipDTO slipDto = new ExtendSalesSlipDTO();
		SaleDAO saleDAO = new SaleDAO();

		ExtendSalesItemDTO salesCost = new ExtendSalesItemDTO();
		String dd = request.getParameter("itemRateOver");
		salesCost.setSysSalesItemId(new Long(request.getParameter("sysSalesItemId")));
		salesCost.setCost(new Integer(request.getParameter("cost")));
		salesCost.setKindCost(new Integer(request.getParameter("kindCost")));
		salesCost.setListPrice(new Integer(request.getParameter("listPrice")));
		salesCost.setItemRateOver(new BigDecimal(request.getParameter("itemRateOver")));
		salesCost.setCostCheckFlag(request.getParameter("costCheckFlag"));
		salesCost.setProfit(new Integer(request.getParameter("profit")));
		salesCost.setPostage(new Integer(request.getParameter("domePostage")));
		salesCost.setUpdatedFlag(new Integer(request.getParameter("updatedFlag")));

		int index = new Integer(request.getParameter("returnIndex"));

		domesticDto.setManagementCode(request.getParameter("itemCode"));
//		domesticDto.setPostage(new Integer(request.getParameter("domePostage")));
		domesticDto.setItemRateOver(new Double(request.getParameter("itemRateOver")));

		slipDto.setPostage(new Integer(request.getParameter("domePostage")));
		slipDto.setSysSalesSlipId(new Integer(request.getParameter("sysSalesSlipId")));
		//開始
		begin();

		// 入力内容登録をDBに反映
		btobSaleService.updateSaleCostId(salesCost);
		domesticDAO.updateItemCodeDomesticExhibition(domesticDto);
//		saleDAO.updateSalesSlipPostage(slipDto);

		//登録成功
		commit();

		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(index));

		return null;
	}
	/**
	 * 売上原価入力<br>
	 * 売上原価一括編集画面の直近の原価を反映処理<br>
	 * 　売上商品情報から直近の原価を取得し、画面の原価欄に反映する。
	 */
	protected ActionForward reflectLatestSaleCostCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();

		// 売上原価一覧と選択行のインデックスから、
		// 直近の原価を取得し、売上原価一覧に設定し直す。
		form.setSalesCostList(btobSaleService.reflectLatestSaleCost(form.getSalesCostList(),form.getSaleCostListIdx()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward reflectLatestSaleCostById (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		BtobBillSaleCostService btobSaleService = new BtobBillSaleCostService();

		// 売上原価一覧と選択行のインデックスから、
		// 直近の原価を取得し、売上原価一覧に設定し直す。
		List<ExtendSalesItemDTO> salesCostList = new ArrayList<>();

		ExtendSalesItemDTO salesCost = new ExtendSalesItemDTO();

		salesCostList = btobSaleService.reflectLatestSaleCost(form.getSalesCostList(),form.getSaleCostListIdx());

		int dd = new Integer(request.getParameter("sysSalesIndex"));
		salesCost = salesCostList.get(new Integer(request.getParameter("sysSalesIndex")));

		String returnValue = request.getParameter("sysSalesIndex") + "," + String.valueOf(salesCost.getCost()) + ","
							+ String.valueOf(salesCost.getKindCost()) + "," + String.valueOf(salesCost.getPostage()) + ","
							+ String.valueOf(salesCost.getListPrice()) + "," + String.valueOf(salesCost.getItemRateOver()) ;
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(returnValue));

		return null;
	}
	/**
	 * 業販原価入力<br>
	 * 業販原価一覧画面の初期表示処理
	 */
	protected ActionForward initCorporateSaleCostList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		// 業販伝票リストを初期化
		form.setCorpSalesCostList(new ArrayList<ExtendCorporateSalesItemDTO>());

		// 法人リストを取得
		CorporationService corporationService = new CorporationService();
		form.setCorporationList(corporationService.getCorporationList());

		// 検索条件を初期化
		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();
		form.setCorpSaleCostSearchDTO(coopSaleCostService.initCorpSaleCostSearchDTO());

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販原価入力<br>
	 * 業販原価一覧画面の検索・一覧表示処理<br>
	 * 　入力された検索条件にて、業販商品情報を1ページ表示分を取得し、一覧表示する。
	 */
	protected ActionForward corporateSaleCostList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();

		// 検索条件を取得
		CorporateSaleCostSearchDTO searchDTO = form.getCorpSaleCostSearchDTO();

		// チェックボックスのフラグを値に変換
		coopSaleCostService.setFlags(searchDTO);

		// 検索結果・システム業販商品IDリストをDBから取得し、formに設定
		form.setSysCorprateSaleItemIDList(coopSaleCostService.getCorporateSaleItemIDList(searchDTO));

		// チェックボックスの値をフラグに変換
		coopSaleCostService.setFlags(searchDTO);

		//検索件数が０件の場合失敗
		if (form.getSysCorprateSaleItemIDList() == null || form.getSysCorprateSaleItemIDList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する検索結果はありません。");
			form.setErrorDTO(errorMessageDTO);

			// 一覧表示を初期化
			form.setSysCorprateSaleItemIDListSize(0);
			form.setCorpSalesCostList(new ArrayList<ExtendCorporateSalesItemDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 検索結果の件数を業販一覧総検索結果件数に設定
		form.setSysCorprateSaleItemIDListSize(form.getSysCorprateSaleItemIDList().size());

		// 1ページ目の業販原価一覧リストを取得する。
		form.setCorpSalesCostList(coopSaleCostService.getCorpSaleCostList(form.getSysCorprateSaleItemIDList(), form.getCorpSaleCostPageIdx(),
				form.getCorpSaleCostSearchDTO()));

		form.setCorpSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getCorpSaleCostSearchDTO().getListPageMax()));


		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販原価入力<br>
	 * 業販原価一覧画面の一覧ページ遷移処理<br>
	 * 　一覧の指定されたページの表示分の業販商品情報を取得し、一覧表示する。
	 */
	protected ActionForward corporateSaleCostListPageNo (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();

		// システム業販商品IDリストが0件の場合、元画面に遷移
		if (form.getSysCorprateSaleItemIDList() == null || form.getSysCorprateSaleItemIDList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定したページの業販原価一覧リストを取得する。
		form.setCorpSalesCostList(coopSaleCostService.getCorpSaleCostList(form.getSysCorprateSaleItemIDList(), form.getCorpSaleCostPageIdx(),
				form.getCorpSaleCostSearchDTO()));

		form.setCorpSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getCorpSaleCostSearchDTO().getListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販原価入力<br>
	 * 業販原価一覧画面の業販原価一括編集画面遷移処理
	 */
	protected ActionForward editCorporateSaleCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();

		// システム業販商品IDリストが0件の場合、元画面に遷移
		if (form.getSysCorprateSaleItemIDList() == null || form.getSysCorprateSaleItemIDList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定したページの業販原価一覧リストを取得する。
		form.setCorpSalesCostList(coopSaleCostService.getCorpSaleCostList(form.getSysCorprateSaleItemIDList(), form.getCorpSaleCostPageIdx(),
				form.getCorpSaleCostSearchDTO()));

		form.setCorpSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getCorpSaleCostSearchDTO().getListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販原価入力<br>
	 * 業販原価一覧画面の業販詳細画面遷移処理<br>
	 * 　伝票番号から業販伝票情報を取得し、詳細画面表示処理へ渡す。
	 */
	protected ActionForward initCorporateSaleDetail (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		// 次に遷移する業販詳細画面のFormを作成
		CorporateSaleForm nextForm = new CorporateSaleForm();

		// 業販詳細画面のFormの初期化
		nextForm.setCorporateSalesSlipList(new ArrayList<ExtendCorporateSalesSlipDTO>());
		CorporationService corporationService = new CorporationService();
		nextForm.setCorporationList(corporationService.getCorporationList());
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		nextForm.setCorporateSaleSearchDTO(corporateSaleDisplayService.initCorporateSaleSearchDTO());
		nextForm.setPickoutputFlg("0");

		// 法人間請求書機能Formから業販詳細画面のFormへシステム業販伝票IDを設定する。
		nextForm.setSysCorporateSalesSlipId(form.getSysCorporateSalesSlipId());

		// 業販詳細画面のFormをセッションに設定
		request.getSession().setAttribute("corporateSaleForm", nextForm);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 業販原価入力<br>
	 * 業販原価一括編集画面の入力内容登録処理
	 */
	protected ActionForward savecorporateSaleCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();

		//開始
		begin();

		// 入力内容登録をDBに反映
		coopSaleCostService.updateCorpSaleCostList(form.getCorpSalesCostList());


		//登録成功
		commit();

		// アラートを設定
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		editCorporateSaleCost(appMapping, form, request);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}
	/**
	 * 業販原価入力<br>
	 * 業販原価一括編集画面の入力内容登録処理
	 * @return
	 */
	protected ActionForward savecorporateSaleCostById (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();

		ExtendCorporateSalesItemDTO corporateSalesCost = new ExtendCorporateSalesItemDTO();

		DomesticExhibitionDAO domesticDAO = new DomesticExhibitionDAO();
		DomesticExhibitionDTO domesticDto = new DomesticExhibitionDTO();

		String dd = request.getParameter("itemRateOver");
		corporateSalesCost.setSysCorporateSalesItemId(new Long(request.getParameter("sysCorporateSalesItemId")));
		corporateSalesCost.setCost(new Integer(request.getParameter("cost")));
		corporateSalesCost.setKindCost(new Integer(request.getParameter("kindCost")));
		corporateSalesCost.setListPrice(new Integer(request.getParameter("listPrice")));
		corporateSalesCost.setItemRateOver(new BigDecimal(request.getParameter("itemRateOver")));
		corporateSalesCost.setCostCheckFlag(request.getParameter("costCheckFlag"));
		corporateSalesCost.setProfit(new Integer(request.getParameter("profit")));
		corporateSalesCost.setPostage(new Integer(request.getParameter("domePostage")));
		corporateSalesCost.setUpdatedFlag(new Integer(request.getParameter("updatedFlag")));

		domesticDto.setItemRateOver(new Double(request.getParameter("itemRateOver")));
		domesticDto.setManagementCode(request.getParameter("itemCode"));

		int index = new Integer(request.getParameter("returnIndex"));
		//開始
		begin();

		// 入力内容登録をDBに反映
		coopSaleCostService.updateCorpSaleCostId(corporateSalesCost);
		domesticDAO.updateItemCodeDomesticExhibition(domesticDto);

		//登録成功
		commit();



		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(index));

		return null;
	}

	/**
	 * 業販原価入力<br>
	 * 業販原価一括編集画面の直近の原価を反映処理<br>
	 * 　業販商品情報から直近の原価を取得し、画面の原価欄に反映する。
	 */
	protected ActionForward reflectLatestCorporateSaleCost (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();

		// 業販原価一覧と選択行のインデックスから、
		// 直近の原価を取得し、業販原価一覧に設定し直す。
		form.setCorpSalesCostList(coopSaleCostService.reflectLatestCorpSaleCost(form.getCorpSalesCostList(), form.getCorpSaleCostListIdx()));


		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward reflectLatestCorporateSaleCostById (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		BtobBillCorporateSaleCostService coopSaleCostService = new BtobBillCorporateSaleCostService();

		// 売上原価一覧と選択行のインデックスから、
		// 直近の原価を取得し、売上原価一覧に設定し直す。
		List<ExtendCorporateSalesItemDTO> salesCostList = new ArrayList<>();

		ExtendCorporateSalesItemDTO salesCost = new ExtendCorporateSalesItemDTO();

		salesCostList = coopSaleCostService.reflectLatestCorpSaleCost(form.getCorpSalesCostList(),form.getCorpSaleCostListIdx());

		int dd = new Integer(request.getParameter("sysSalesIndex"));
		salesCost = salesCostList.get(new Integer(request.getParameter("sysSalesIndex")));

		String returnValue = request.getParameter("sysSalesIndex") + "," + String.valueOf(salesCost.getCost()) + ","
							+ String.valueOf(salesCost.getKindCost()) + "," + String.valueOf(salesCost.getPostage()) + ","
							+ String.valueOf(salesCost.getListPrice()) + "," + String.valueOf(salesCost.getItemRateOver()) ;
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(returnValue));

		return null;
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の初期表示処理<br>
	 * 　すべての法人の請求書情報を一覧表示する。
	 */
	protected ActionForward initBtobBillList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		// 法人リスト取得
		 CorporationService corporationService = new CorporationService();
		 List<MstCorporationDTO> corporationList = new ArrayList<MstCorporationDTO>();

		  corporationList.add(corporationService.getCorporation(1)); // KTS
		 corporationList.add(corporationService.getCorporation(2)); // 車楽院
		 corporationList.add(corporationService.getCorporation(4)); // ラルグスリテール
		 corporationList.add(corporationService.getCorporation(5)); // 株式会社BCR

		 // 株式会社ラルグスの設定
		 MstCorporationDTO corpDTO = new MstCorporationDTO();
		 corpDTO.setCorporationNm("株式会社日本中央貿易");
		 corpDTO.setSysCorporationId(20);
		 corpDTO.setCorporationFullNm("株式会社日本中央貿易");
		 corpDTO.setAbbreviation("株式会社日本中央貿易");
		 corporationList.add(corpDTO);

		 corporationList.add(corporationService.getCorporation(12)); // Brembo事業部

		 corporationList.get(0).setCorporationNm("株式会社"+corporationList.get(0).getCorporationNm());
		 corporationList.get(1).setCorporationNm("株式会社"+corporationList.get(1).getCorporationNm());
		 corporationList.get(2).setCorporationNm("株式会社"+corporationList.get(2).getCorporationNm());

		 form.setCorporationList(corporationList);

		 // 表示対象法人をAllに設定
		 form.setDispSysCorporationId(0);

		 //1ページ目を指定
		 form.setBtobBillPageIdx(0);

		 // 法人間請求書を全件取得し、formへ設定
		 // 指定法人のシステム法人間請求書IDリストを全件取得し、formへ設定
		 form.setSysBtobBillIdList(btobService.getSysBtobBillIdList(form.getDispSysCorporationId()));

		//検索件数が０件の場合失敗
		if (form.getSysBtobBillIdList() == null || form.getSysBtobBillIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する請求書はありません。");
			form.setErrorDTO(errorMessageDTO);

			// 一覧表示を初期化
			form.setBtobBillListSize(0);
			form.setBtobBillDTOList(new ArrayList<ExtendBtobBillDTO>());
			form.setCorporateSaleListTotalDTO(new CorporateSaleListTotalDTO());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}

		form.setBtobBillListSize(form.getSysBtobBillIdList().size());

		// 1ページ目の法人間請求書一覧リストを取得する。
		form.setBtobBillDTOList(btobService.getBtobBillList(form.getSysBtobBillIdList(), form.getBtobBillPageIdx(),
				form.getBtobBillListPageMax()));

		form.setCorporateSaleListTotalDTO(new CorporateSaleListTotalDTO());
		form.getBtobBillSearchDto().setSumDispFlg(WebConst.SUM_DISP_FLG1);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の法人一覧処理<br>
	 * 　法人ごとに請求書情報を取得し、一覧表示する。
	 */
	protected ActionForward btobBillList (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		 // 指定法人のシステム法人間請求書IDリストを全件取得し、formへ設定
		 form.setSysBtobBillIdList(btobService.getSysBtobBillIdList(form.getDispSysCorporationId()));

		 //法人IDは株式会社ラルグスだとDispIDが20のため、kindCostが取得できない
		 if (form.getDispSysCorporationId() == 20) {
			 form.setSysCorporationId(8);
		 } else {
			 form.setSysCorporationId(form.getDispSysCorporationId());
		 }

		//検索件数が０件の場合失敗
		if (form.getSysBtobBillIdList() == null || form.getSysBtobBillIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する請求書はありません。");
			form.setErrorDTO(errorMessageDTO);

			// 一覧表示を初期化
			form.setBtobBillListSize(0);
			form.setBtobBillDTOList(new ArrayList<ExtendBtobBillDTO>());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setBtobBillListSize(form.getSysBtobBillIdList().size());

		// 指定法人の全ての法人間請求書一覧リストから取得する。
		form.setBtobBillDTOList(btobService.getBtobBillAllList(form.getSysBtobBillIdList()));

		//TODO BONCRE-2375 合計値エリア追加

		//画面上に表示する計算処理
		form.setCorporateSaleListTotalDTO(btobService.getCorporateSaleListTotalDTO(
				form.getBtobBillDTOList()
				,form.getBtobBillSearchDto().getGrossProfitCalc()
				,form.getBtobBillSearchDto().getSumDispFlg()));

		// 再度1ページ目の法人間請求書一覧リストを取得する。
		form.setBtobBillDTOList(btobService.getBtobBillList(form.getSysBtobBillIdList(), form.getBtobBillPageIdx(),
				form.getBtobBillListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の一覧ページ遷移処理<br>
	 * 　一覧の指定されたページの表示分の請求書情報を取得し、一覧表示する。
	 */
	protected ActionForward btobBillListPageNo (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		// システム法人間請求書IDリストが0件の場合、元画面に遷移
		if (form.getSysBtobBillIdList() == null || form.getSysBtobBillIdList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定ページの法人間請求書一覧リストを取得する。
		form.setBtobBillDTOList(btobService.getBtobBillList(form.getSysBtobBillIdList(), form.getBtobBillPageIdx(),
				form.getBtobBillListPageMax()));

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の請求書情報作成処理<br>
	 * 　指定された法人の月単位での売上・業販情報を集計し、請求書情報をDBに登録する。
	 */
	protected ActionForward createBtobBill (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		ErrorDTO checkErrorDTO = btobService.checkBtobBill(form.getExportMonth(), form.getExportSysCorporationId());

		// チェックがエラーの場合、エラーメッセージを設定
		if(!checkErrorDTO.isSuccess()){
			form.setErrorDTO(checkErrorDTO);
			// 次へ遷移
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 指定された法人の月単位での売上・業販情報を集計し、請求書情報をDBに登録
		ErrorDTO errorMessageDTO = btobService.createBtobBill(form.getExportMonth(), form.getExportSysCorporationId());

		// 作成に失敗した場合、エラーメッセージを設定
		if(!errorMessageDTO.isSuccess()){
			form.setErrorDTO(errorMessageDTO);
			form.setCorporateSaleListTotalDTO(new CorporateSaleListTotalDTO());
			form.getBtobBillSearchDto().setSumDispFlg(WebConst.SUM_DISP_FLG1);
			// 次へ遷移
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		// 出力法人IDを表示対象に設定
		form.setDispSysCorporationId(form.getExportSysCorporationId());

		// 法人間請求書管理画面の法人一覧処理
		btobBillList(appMapping, form, request);

		form.getBtobBillSearchDto().setSumDispFlg(WebConst.SUM_DISP_FLG1);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の請求書検索処理<br>
	 * 　法人間請求書管理画面にて与えられた検索条件をもとに検索を行い、画面に表示する。
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @author boncre_suda
	 */
	private ActionForward searchBtobBill(AppActionMapping appMapping, BtobBillForm form,
			HttpServletRequest request) throws Exception {

		 //1ページ目を指定
		 form.setBtobBillPageIdx(0);

		 BtobBillService btobBillService = new BtobBillService();
		//法人間請求書IDリストを検索
		form.setSysBtobBillIdList(btobBillService.searchBtobBillIdList(form.getExportMonth(), form.getExportSysCorporationId()));

		 //法人IDは株式会社ラルグスだとDispIDが20のため、kindCostが取得できない
		 if (form.getDispSysCorporationId() == 20) {
			 form.setSysCorporationId(8);
		 } else {
			 form.setSysCorporationId(form.getDispSysCorporationId());
		 }

		//検索件数が０件の場合失敗
		if (form.getSysBtobBillIdList() == null || form.getSysBtobBillIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する請求書はありません。");
			form.setErrorDTO(errorMessageDTO);

			// 一覧表示を初期化
			form.setBtobBillListSize(0);
			form.setBtobBillDTOList(new ArrayList<ExtendBtobBillDTO>());
			form.setCorporateSaleListTotalDTO(new CorporateSaleListTotalDTO());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}

		form.setBtobBillListSize(form.getSysBtobBillIdList().size());

		// 指定法人の全ての法人間請求書一覧リストから取得する。
		form.setBtobBillDTOList(btobBillService.getBtobBillAllList(form.getSysBtobBillIdList()));

		//画面上に表示する計算処理
		form.setCorporateSaleListTotalDTO(btobBillService.getCorporateSaleListTotalDTO(
				form.getBtobBillDTOList()
				,form.getBtobBillSearchDto().getGrossProfitCalc()
				,form.getBtobBillSearchDto().getSumDispFlg()));

		//検索結果のページング処理
		form.setBtobBillDTOList(btobBillService.getBtobBillList(form.getSysBtobBillIdList(), form.getBtobBillPageIdx(),
				form.getBtobBillListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の編集有効化処理<br>
	 * 　法人間請求書管理画面にて入力した値を請求書情報に反映する。
	 */
	protected ActionForward updateBtobBill (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		// 入力内容登録をDBに反映
		btobService.updateBtobBillList(form.getBtobBillDTOList());

		// アラートを設定
		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		btobBillListPageNo(appMapping, form, request);

		// 次へ遷移
		return btobBillList(appMapping, form, request);
//		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の削除処理<br>
	 * 　法人間請求書管理画面にて該当の請求書情報を削除する。
	 */
	protected ActionForward deleteBtobBill (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request) throws Exception {

		BtobBillService btobService = new BtobBillService();

		//開始
		begin();

		int index = form.getBtobBillListIdx() + (form.getBtobBillListPageMax() * form.getBtobBillPageIdx());

		// 入力内容登録をDBに反映
		btobService.deleteBtobBill(form.getBtobBillDTOList(), index);


		//登録成功
		commit();

		// アラートを設定
		form.setAlertType(WebConst.ALERT_TYPE_DELETE);

		btobBillList(appMapping, form, request);

		// 次へ遷移
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面の請求書出力処理<br>
	 * 　法人間請求書管理画面にて該当の請求書情報を取得する。
	 */
	protected ActionForward exportBillBtobBill (AppActionMapping appMapping, BtobBillForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		BtobBillService btobService = new BtobBillService();

		int index = form.getBtobBillListIdx() + (form.getBtobBillListPageMax() * form.getBtobBillPageIdx());

		// 選択行の法人間請求書を取得
		ExtendBtobBillDTO btobBillDTO = btobService.getExportBtobBill(form.getSysBtobBillIdList(), index);

		// 選択行の法人間請求書商品を取得
		List<BtobBillItemDTO> billItemList = btobService.getExportBtobBillItemList(form.getSysBtobBillIdList(), form.getBtobBillListIdx());

		// PDFファイルをサーバ上に作成
		btobService.setPDFBtobBill(response, btobBillDTO, billItemList);

		// 次へ遷移
		return null;
	}

	/**
	 * 法人間請求書管理<br>
	 * 法人間請求書管理画面のPDF出力処理<br>
	 * 　取得した請求書情報をPDFに出力する。
	 */
	protected ActionForward billBtobBillPrintOutFile (
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "btobBill.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "法人間請求書_" + fileNmTimeFormat.format(date) + ".pdf";

		ExportCorporateBillService exportBillService = new ExportCorporateBillService();

		try {
			exportBillService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return null;

	}

}
