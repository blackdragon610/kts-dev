package jp.co.kts.ui.domestic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderSlipDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticOrderListSearchDTO;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.fileExport.ExportDomesticOrderService;
import jp.co.kts.service.fileExport.ExportItemListService;
import jp.co.kts.service.mst.DomesticOrderListService;
import jp.co.kts.service.mst.DomesticOrderService;
import jp.co.kts.service.mst.MakerService;
import jp.co.kts.ui.web.struts.WebConst;
import net.arnx.jsonic.JSON;

public class DomesticOrderListAction extends AppBaseAction {
	//国内チェックフラグ定数
	private static final String ORDER_CHECK_FLG_ON = "on";
	//検索結果0件
	private static final int SEARCH_RESULT_CNT = 0;

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DomesticOrderListForm form = (DomesticOrderListForm)appForm;
		//初期表示処理
		if ("/initDomesticOrderList".equals(appMapping.getPath())) {
			return initdomesticOrderList(appMapping, form, request);
		//検索処理
		} else if ("/searchDomesticOrderList".equals(appMapping.getPath())) {
			 return searchDomesticOrderList(appMapping, form, request);
		//更新処理
		} else if ("/updateDomesticOrderList".equals(appMapping.getPath())) {
			 return updateDomesticOrderList(appMapping, form, request);
		//ページング処理
		} else if ("/domesticListPageNo".equals(appMapping.getPath())) {
			 return domesticListPageNo(appMapping, form, request);
		//削除処理
		} else if ("/domesticListItemDelete".equals(appMapping.getPath())) {
			return domesticListItemDelete(appMapping, form, request);
		//検索結果DL処理
		} else if ("/domesticSlipListDownLoad".equals(appMapping.getPath())) {
			return domesticSlipListDownLoad(appMapping, form, request, response);
		//ステータス移動処理
		} else if ("/domesticOrderStatusMove".equals(appMapping.getPath())) {
			return domesticOrderStatusMove(appMapping, form, request);
		//管理品番をセッションに保持する処理
		} else if ("/setDomesticValue".equals(appMapping.getPath())) {
			return setDomesticValue(appMapping, form, request, response);
		//国内注文書作成：データ作成
		} else if ("/exportDomesticOrdeList".equals(appMapping.getPath())) {
			return exportDomesticOrdeList(appMapping, form, request, response);
		//国内注文書一括印刷：出力処理
		} else if  ("/domesticOrderListPrintOutFile".equals(appMapping.getPath())) {
			return domesticOrderListPrintOutFile(response);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * 初期表示
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initdomesticOrderList(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request) throws Exception {
		//インスタンス生成
		MakerService makerService = new MakerService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);

		//メーカー情報取得
		form.setMakerListDTO(makerService.getMakerInfo());
		//検索結果レコード初期化
		form.setDomesticOrderItemInfoList(new ArrayList<DomesticOrderListDTO>());
		//検索条件項目初期化
		form.setDomesticOrderListSearchDTO(new DomesticOrderListSearchDTO());
		//移動ステータス初期化
		form.setMoveStatus(StringUtils.EMPTY);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 国内注文一覧画面検索処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 */
	private ActionForward searchDomesticOrderList(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request) throws Exception{

		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//更新・削除から呼び出されていない場合、メッセージを初期化
		if (!form.getMessageFlg().equals("1")) {
			//メッセージ初期化
			form.setRegistryDto(messageDTO);
		}
		DomesticOrderListService service = new DomesticOrderListService();
		//国内商品のIDを検索
		form.setDomesticOrderItemIdList(service.getDomesticOrderIdList(form.getDomesticOrderListSearchDTO()));
		//検索結果の件数を設定
		form.setSysDomesticItemIdListSize(form.getDomesticOrderItemIdList().size());
		//ページング処理用の設定
		form.setDomesticOrderPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getDomesticOrderListSearchDTO().getListPageMax()));
		form.setPageIdx(0);
		//国内商品情報検索実行
		form.setDomesticOrderItemInfoList(service.getDomesticOrderList(form.getDomesticOrderItemIdList(), form.getPageIdx(), form.getDomesticOrderListSearchDTO()));

		if (form.getSysDomesticItemIdListSize() == SEARCH_RESULT_CNT
				&& !form.getMessageFlg().equals("1")) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("検索結果が0件です。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setMessageFlg("0");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * 国内注文一覧画面の更新処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateDomesticOrderList(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request) throws Exception {
		//更新対象件数
		int targetCnt = 0;
		//更新結果件数
		int resultCnt = 0;
		//インスタンス生成
		DomesticOrderListService service = new DomesticOrderListService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//原価反映対象格納リスト
		List<DomesticOrderListDTO> targetList = new ArrayList<DomesticOrderListDTO>();

		//更新する件数をカウントしておく
		targetCnt = form.getDomesticOrderItemInfoList().size();

		begin();

		//更新処理実行
		resultCnt = service.updateDomesticListItem(form.getDomesticOrderItemInfoList());

		//原価反映対象を格納
		for (DomesticOrderListDTO checkList : form.getDomesticOrderItemInfoList()) {
			//ステータスが「原価確認済」且つ「完了」以外の場合原価反映対象としてリストに格納
			if (!checkList.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO)
					&& !checkList.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_COMPLETE_NO)) {
				targetList.add(checkList);
			}

		}
		//原価反映対象がいる場合反映処理実行
		if (targetList.size() != 0) {
			messageDTO = service.updateDomesticOrderItemCost(messageDTO, targetList);
		}
		if (!messageDTO.isSuccess()) {
			rollback();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		if (targetCnt != resultCnt) {
			rollback();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			commit();
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("国内注文管理一覧を更新しました。");
			form.setMessageFlg("1");
			form.setRegistryDto(messageDTO);
		}
		return searchDomesticOrderList(appMapping, form, request);

	}


	/**
	 * 国内注文一覧画面のページング処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward domesticListPageNo(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request) throws Exception {
		//インスタンス生成
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		DomesticOrderListService service = new DomesticOrderListService();

		//メッセージ初期化
		form.setRegistryDto(messageDTO);
		form.setMessageFlg("0");

		//検索対象がない場合は何もしない
		if (form.getDomesticOrderItemIdList() == null || form.getDomesticOrderItemIdList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setDomesticOrderItemInfoList(service.getDomesticOrderList
				(form.getDomesticOrderItemIdList(), form.getPageIdx(), form.getDomesticOrderListSearchDTO()));

		form.setDomesticOrderPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getDomesticOrderListSearchDTO().getListPageMax()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 国内注文一覧にある商品の削除処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward domesticListItemDelete(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request) throws Exception {
		//削除対象件数
		int targetCnt = 0;
		//削除結果件数
		int resultCnt = 0;

		//インスタンス生成
		DomesticOrderListService service = new DomesticOrderListService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		Map<Long, Long> checkslipNo = new HashMap<Long, Long>();
		List<Long> targetSysSlipId = new ArrayList<Long>();

		//削除する商品の件数をカウントしておく
		for (DomesticOrderListDTO dto : form.getDomesticOrderItemInfoList()) {
			if (dto.getOrderCheckFlg().equals(ORDER_CHECK_FLG_ON)) {
				targetCnt++;
			}
			//伝票番号が重複したら最初に戻る
			if (checkslipNo.containsKey(dto.getSysDomesticSlipId())) {
				continue;
			}
			//削除商品の伝票番号を保持
			checkslipNo.put(dto.getSysDomesticSlipId(), dto.getSysDomesticSlipId());
			targetSysSlipId.add(dto.getSysDomesticSlipId());
		}

		//商品削除実行
		resultCnt = service.deleteDomesticListItem(form.getDomesticOrderItemInfoList());

		//伝票に紐づく商品が全て削除されている場合、伝票も削除する
		service.selectUpdateCntTargetSlip(targetSysSlipId);


		if (targetCnt != resultCnt) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("削除に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("対象の削除が完了しました。");
			form.setMessageFlg("1");
			form.setRegistryDto(messageDTO);
		}
		return searchDomesticOrderList(appMapping, form, request);
	}

	/**
	 * 国内注文一覧画面の検索結果DL処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward domesticSlipListDownLoad(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//テンプレートのパス取得
		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.DOMESTIC_LIST_TEMPLATE_PATH);
		// ファイルを読み込みます。
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ワークブックを読み込みます。
		HSSFWorkbook workBook = new HSSFWorkbook(filein);
		//エクセルファイルを編集します
		ExportItemListService exportItemListService = new ExportItemListService();
		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "";

		workBook = exportItemListService.getExportDoemsticList(form.getDomesticOrderListSearchDTO(), workBook);
		fname = "国内注文一覧情報_" + date + ".xls";

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
	 * 国内注文一覧画面のステータス移動処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward domesticOrderStatusMove(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request) throws Exception {
		//更新対象件数
		int targetCnt = 0;
		//更新結果件数
		int resultCnt = 0;
		//インスタンス生成
		DomesticOrderListService service = new DomesticOrderListService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		List<DomesticOrderListDTO> targetDto = new ArrayList<DomesticOrderListDTO>();

		//更新する件数をカウントしておく
		for (DomesticOrderListDTO dto: form.getDomesticOrderItemInfoList()) {
			if(dto.getOrderCheckFlg().equals(ORDER_CHECK_FLG_ON)) {
				targetCnt++;
				targetDto.add(dto);
			}
		}

		messageDTO = service.checkStatusMove(messageDTO, targetDto, form.getMoveStatus());
		//ステータスチェック結果エラー時
		if (!messageDTO.isSuccess()) {
			messageDTO.setMessageFlg("1");
			if (StringUtils.isBlank(messageDTO.getMessage())) {
				messageDTO.setMessage("ステータスは一つずつしか移動できません。");
			}
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		resultCnt = service.updateDomesticListStatus(targetDto,
				form.getMoveStatus());
		//更新失敗時
		if (targetCnt != resultCnt) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("ステータス移動に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		//更新成功時
		} else {
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("ステータス移動が完了しました。");
			form.setMessageFlg("1");
			form.setRegistryDto(messageDTO);
		}
		return searchDomesticOrderList(appMapping, form, request);

	}
	/**
	 * 選択されたレコードの値をセッションに保持するための処理
	 * セッションに値を保持させるだけなので、処理の中身は空です。
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward setDomesticValue(AppActionMapping appMapping,
			DomesticOrderListForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		form.setManagementCode(form.getManagementCode());
		form.setStrSysDomesticSlipId(form.getStrSysDomesticSlipId());

		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(""));

		return null;
	}

	/**
	 * 国内注文書一括印刷機能
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportDomesticOrdeList(AppActionMapping appMapping, DomesticOrderListForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		//一括印刷用のList
		List<ExtendDomesticOrderSlipDTO> exportList = new ArrayList<>();
		//重複排他用のMAP
		Map<String, String> checkDomesticOrderMap = new HashMap<String, String>();
		//出力する伝票を取得し、ExtendDTOに格納
		DomesticOrderService service = new DomesticOrderService();
		//取得結果判定値

		begin();
		for (DomesticOrderListDTO exportDto : form.getDomesticOrderItemInfoList()) {

			if (exportDto.getOrderCheckFlg().equals("off")) {
				continue;
			}

			//国内注文管理一覧画面は商品単位なので印刷する注文書データが重複しないようにはじく
			if (checkDomesticOrderMap.containsKey(String.valueOf(exportDto.getSysDomesticSlipId()))) {
				continue;
			}
			ExtendDomesticOrderSlipDTO extendDto = new ExtendDomesticOrderSlipDTO();
			//国内注文伝票データ取得
			extendDto = service.getSearchDomesticOrderSlip(exportDto.getSysDomesticSlipId());

			//取得結果がnullの場合エラー
			if (extendDto == null) {
				rollback();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}

			//国内注文商品データ取得
			extendDto.setDomesticOrderItemList(service.getSearchExtendDomesticOrderItemList(exportDto.getSysDomesticSlipId()));
			//国内注文書データをまとめる
			exportList.add(extendDto);

			//画面側で印刷確認実行が選択された場合、注文書の印刷確認フラグを更新
			if (form.getPrintCheckFlag().equals(WebConst.PRINT_CHECK_COMPLETION)) {
				extendDto.setPrintCheckFlag("on");
				//印刷確認更新
				service.updateDomesticSlipPrintFlg(extendDto);

				for (DomesticOrderListDTO updDto :extendDto.getDomesticOrderItemList()) {
					//ステータスが「注文前」の場合のみステータス移動、採番実施
					if (updDto.getStatus().equals("1")) {
						//納入先が「その他」以外の場合,採番して、ステータスを「入荷待ち」に変更する
						if (extendDto.getSysWarehouseId() != 99) {
							//更新処理実行
							updDto.setStatus(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO);
							service.updateDomesticOrderItem(updDto, extendDto.getSysDomesticSlipId());

						//ステータスを「直送」に変更する
						} else {
							updDto.setStatus(WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO);
							service.updateDomesticOrderItem(updDto, extendDto.getSysDomesticSlipId());
						}
					}
				}

			//画面側で印刷確認実行が選択されていない場合
			} else {
				//印刷確認が完了している場合、ステータス変更処理開始
				if (extendDto.getPrintCheckFlag().equals(WebConst.PRINT_CHECK_COMPLETION)) {

					for (DomesticOrderListDTO updDto :extendDto.getDomesticOrderItemList()) {
						//ステータスが「注文前」の場合のみステータス移動、採番実施
						if (updDto.getStatus().equals("1")) {
							//納入先が「その他」以外の場合,採番して、ステータスを「入荷待ち」に変更する
							if (extendDto.getSysWarehouseId() != 99) {
								//更新処理実行
								updDto.setStatus(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO);
								service.updateDomesticOrderItem(updDto, extendDto.getSysDomesticSlipId());

							//ステータスを「直送」に変更する
							} else {
								updDto.setStatus(WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO);
								service.updateDomesticOrderItem(updDto, extendDto.getSysDomesticSlipId());
							}
						}
					}
				}
			}
			//出力データをまとめたら、重複して印刷しないようにMapに格納する
			checkDomesticOrderMap.put(String.valueOf(exportDto.getSysDomesticSlipId()), String.valueOf(exportDto.getSysDomesticSlipId()));

			for (int i = 0; i < extendDto.getDomesticOrderItemList().size(); i++) {
				if (extendDto.getDomesticOrderItemList().get(i).getItemType().equals(WebConst.RESULT_ITEM_TYPE_SET)) {
					extendDto.getDomesticOrderItemList().remove(i);
					i--;
				}
			}


		}

		try {
			//サービスで出力内容を設定
			DomesticOrderListService exportService = new DomesticOrderListService();
			exportService.orderAcceptanceList(response, exportList);
		}catch(Exception e) {
			rollback();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw e;
		}
		commit();
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
	protected ActionForward domesticOrderListPrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "orderAcceptance.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "注文書" + fileNmTimeFormat.format(date) + ".pdf";

		ExportDomesticOrderService exportService = new ExportDomesticOrderService();

		try {
			exportService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;

		}
		return null;
	}

}
