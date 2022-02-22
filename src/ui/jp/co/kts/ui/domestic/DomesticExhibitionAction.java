/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.ui.domestic;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.core.SystemSetting;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.fileExport.ExportItemListService;
import jp.co.kts.service.mst.DomesticExhibitionService;
import jp.co.kts.service.mst.MakerService;
import jp.co.kts.ui.web.struts.WebConst;


/**
 * 出品データベース一覧画面で使用するActionクラス
 * @author n_nozawa
 * 20161108
 */
public class DomesticExhibitionAction extends AppBaseAction {

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {



		DomesticExhibitionForm form = (DomesticExhibitionForm)appForm;

		//初期表示時処理
		if ("/initDomesticExhibitionList".equals(appMapping.getPath())) {
			return domesticExhibitionList(appMapping, form, request);
		//検索処理
		} else if ("/searchDomesticExhibition".equals(appMapping.getPath())) {
			 return searchDomesticExhibition(appMapping, form, request);
		//更新処理
		} else if ("/updateDomesticExhibition".equals(appMapping.getPath())) {
			 return updateDomesticExhibition(appMapping, form, request);
		//削除処理
		} else if ("/deleteDomesticExhibition".equals(appMapping.getPath())) {
			 return deleteDomesticExhibition(appMapping, form, request);
		//検索結果DL処理
		} else if ("/domesticListDownload".equals(appMapping.getPath())) {
			return domesticDownload(appMapping, form, request, response);
		//ページング処理
		} else if ("/domesticExhibitionPageNo".equals(appMapping.getPath())) {
			return domesticExhibitionPageNo(appMapping, form, request);
		//国内注文一覧画面からの遷移処理
		} else if ("/searchDomesticExhibitionItem".equals(appMapping.getPath())) {
			return searchDomesticExhibitionItem(appMapping, request, form);
		//国内セット商品登録初期処理
		} else if ("/initSetManegementRegist".equals(appMapping.getPath())) {
			return initSetManegementRegist(appMapping, form, request);
		//国内セット商品編集初期処理
		} else if ("/initSetManegementEdit".equals(appMapping.getPath())) {
			return initSetManegementEdit(appMapping, form, request);
		//国内セット商品登録処理
		} else if ("/registSetManagementItem".equals(appMapping.getPath())) {
			return registSetManagementItem(appMapping, form, request, response);
		//国内セット商品更新処理
		} else if ("/updateSetManagementItem".equals(appMapping.getPath())) {
			return updateSetManagementItem(appMapping, form, request, response);
		//国内セット商品削除処理
		} else if ("/deleteSetDomesticExhibition".equals(appMapping.getPath())) {
			return deleteSetDomesticExhibition(appMapping, form, request, response);
		}
		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * 概要
	 * 出品DB画面の初期表示検索前処理
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return FORWARD_NAME_SUCCESS
	 * @throws Exception
	 */
	protected ActionForward domesticExhibitionList(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request) throws Exception {
		//インスタンス生成
		MakerService makerService = new MakerService();
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		DomesticExhibitionSearchDTO domesticExhibitionSearchDTO = new DomesticExhibitionSearchDTO();
		//メッセージ初期化
		//エラーメッセージの初期化
		form.setErrorDTO(new ErrorDTO());
		form.setRegistryDto(registryMessageDTO);
		form.setDomesticExhibitionSearchDTO(domesticExhibitionSearchDTO);
		form.setDomesticExhibitionList(new ArrayList<DomesticExhibitionDTO>());
		form.setDomesticMakerNmList(makerService.getMakerInfo());

		DomesticExhibitionService service = new DomesticExhibitionService();
		//追加分listをセット
		form.setAddDomesticExhibitionList(service.initAddDomesticExhibitionList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 出品DB画面での検索処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward searchDomesticExhibition(AppActionMapping appMapping,
		DomesticExhibitionForm form, HttpServletRequest request) throws Exception {

		/*
		 * 検索条件が設定されていない状態で検索された場合
		 * データの件数によってはメモリ不足となる可能性があるので
		 * 出品DB画面の初期表示検索前処理へ遷移する。
		 *
		 * 検索条件としては下記を対象とする。
		 * 当改修箇所は
		 * BONCRE-5378 国内注文＆出品DB　管理品番を出品DB一覧から登録しようとするとメモリエラーが発生する。
		 * の対応。
		 */
		
		String itemCode = request.getParameter("managementCode");
		if(itemCode != null) {
			form.getDomesticExhibitionSearchDTO().setManagementCode(itemCode);
			if (StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getListPageMax())) {
				form.getDomesticExhibitionSearchDTO().setListPageMax("1");
			}
			
		}
		//インスタンス生成
		MakerService makerService = new MakerService();

		form.setDomesticMakerNmList(makerService.getMakerInfo());

		DomesticExhibitionService service = new DomesticExhibitionService();

		DomesticExhibitionSearchDTO dto = form.getDomesticExhibitionSearchDTO();
		if (StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getItemNm()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getDepartmentNm()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getItemRateOver()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getListPriceFrom()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getListPriceTo()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getMakerCode()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getMakerNm()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getMakerNmPhonetic()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getManagementCode()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getPurchasingCostFrom()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getPurchasingCostTo()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getUpdateDateFrom()) &&
				StringUtils.isEmpty(form.getDomesticExhibitionSearchDTO().getUpdateDateTo())
				) {
			RegistryMessageDTO messageDTO = new RegistryMessageDTO();

			String msg = form.getRegistryDto().getMessage();
			String path = appMapping.getPath();
			// 国内商品の登録・更新・削除から遷移してきた場合は、登録しましたのメッセージを出力する。
			if (StringUtils.isEmpty(form.getRegistryDto().getMessage()) || "/searchDomesticExhibition".equals(appMapping.getPath())) {
				messageDTO.setMessage("検索条件を設定して検索してください。");
				messageDTO.setMessageFlg("1");
				form.setRegistryDto(messageDTO);
				form.setMessageFlg("0");
			}

			DomesticExhibitionSearchDTO domesticExhibitionSearchDTO = new DomesticExhibitionSearchDTO();
			form.setDomesticExhibitionSearchDTO(domesticExhibitionSearchDTO);
			form.setDomesticExhibitionList(new ArrayList<DomesticExhibitionDTO>());
			/**
			 *  前の検索結果を残さないために一覧結果を初期化する。
			 */
			//追加分listをセット
			form.setAddDomesticExhibitionList(service.initAddDomesticExhibitionList());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);

		}


		//更新・削除から呼び出されていない場合、メッセージを初期化
		if (!form.getMessageFlg().equals("1") || "/searchDomesticExhibition".equals(appMapping.getPath())) {
			RegistryMessageDTO messageDTO = new RegistryMessageDTO();
			//メッセージ初期化
			form.setRegistryDto(messageDTO);
			form.setErrorDTO(new ErrorDTO());
		}

		service.setFlagsSwutch(form.getDomesticExhibitionSearchDTO());
		form.setSysManagementIdList(service.getDomesticExhibionIdList(form.getDomesticExhibitionSearchDTO()));
		form.setSysManagementIdListSize(form.getSysManagementIdList().size());
		form.setDomesticExhibitionListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getDomesticExhibitionSearchDTO().getListPageMax()));
		form.setPageIdx(0);
		//検索実行
		form.setDomesticExhibitionList(service.getDomesticExhibionSearch(form.getSysManagementIdList(), form.getPageIdx(), form.getDomesticExhibitionSearchDTO()));
		service.setFlagsSwutch(form.getDomesticExhibitionSearchDTO());
		//検索結果0件メッセージを表示する
		if (form.getDomesticExhibitionList().isEmpty()
				&& !form.getMessageFlg().equals("1")) {
			RegistryMessageDTO messageDTO = new RegistryMessageDTO();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("検索結果が0件です。");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}
		form.setMessageFlg("0");
		form.getRegistryDto().setMessageFlg("0");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 出品データベース一覧画面のページング処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward domesticExhibitionPageNo(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request) throws Exception {

		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);
		//エラーメッセージの初期化
		form.setErrorDTO(new ErrorDTO());

		//検索対象がない場合は何もしない
		if (form.getSysManagementIdList() == null || form.getSysManagementIdList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//検索実行
		form.setDomesticExhibitionList(new DomesticExhibitionService().getDomesticExhibionSearch
				(form.getSysManagementIdList(), form.getPageIdx(),form.getDomesticExhibitionSearchDTO()));
		form.setDomesticExhibitionListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getDomesticExhibitionSearchDTO().getListPageMax()));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 出品DB画面の更新処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateDomesticExhibition(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request) throws Exception {
		//インスタンス生成
		DomesticExhibitionService service = new DomesticExhibitionService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);
		//エラーメッセージの初期化
		form.setErrorDTO(new ErrorDTO());
		//更新件数を設定
		int updateListSize = form.getDomesticExhibitionList().size();
		int registListSize = 0;

		for (DomesticExhibitionDTO dto : form.getAddDomesticExhibitionList()) {
			//更新件数をカウントして設定
			if (StringUtils.isBlank(dto.getManagementCode())) {
				continue;
			}
			registListSize++;
		}

		//管理品番の重複チェック
		messageDTO = service.checkManagementCode(messageDTO, form.getDomesticExhibitionList(), form.getAddDomesticExhibitionList());

		if (!messageDTO.isSuccess()) {
			List<ErrorMessage> message = new ArrayList<ErrorMessage>();
			message.addAll(messageDTO.getResult().getErrorMessages());
			saveErrorMessages(request, message);
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		begin();
		//更新
		int resultUpdCnt = service.updateDomesticExhibitionList(form.getDomesticExhibitionList());
		//登録
		int resultRegCnt = service.registryDomesticExhibitionList(form.getAddDomesticExhibitionList());

		for (DomesticExhibitionDTO targetDto :form.getDomesticExhibitionList()) {
			//更新対象を使用しているの国内注文商品とそれに紐づく売上商品の原価を更新
			messageDTO = service.updateDomesticOrderItemCost(messageDTO, targetDto);
		}

		if (!messageDTO.isSuccess()) {
			rollback();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//登録・更新件数が合っていればメッセージ表示
		if (resultUpdCnt == updateListSize &&
				resultRegCnt == registListSize) {
			commit();
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("出品データベースを更新しました。");
			form.setMessageFlg("1");
		} else {
			rollback();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setMessageFlg("0");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//追加分listをセット
		form.setAddDomesticExhibitionList(service.initAddDomesticExhibitionList());

		return searchDomesticExhibition(appMapping, form, request);
	}

	/**
	 * 出品DB画面での商品削除処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteDomesticExhibition(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request) throws Exception {
		//インスタンス生成
		DomesticExhibitionService service = new DomesticExhibitionService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		//エラーメッセージの初期化
		form.setErrorDTO(new ErrorDTO());
		//メッセージ初期化
		form.setRegistryDto(messageDTO);

		int deleteCnt = 0;
		//削除対象の件数をカウント
		for (DomesticExhibitionDTO dto: form.getDomesticExhibitionList()) {
			if (StringUtils.equals(dto.getDeleteCheckFlg(), "on")) {
				deleteCnt++;
			}
		}

		//登録されたものの削除
		service.setFlags(form.getDomesticExhibitionList());
		messageDTO = service.deleteDomesticExhibition(messageDTO, form.getDomesticExhibitionList());

		form.getErrorDTO().setErrorMessageList(messageDTO.getErrorMessageList());
		if (form.getErrorDTO().getErrorMessageList().size() > 0) {
			form.getErrorDTO().setSuccess(false);
		} else {
			form.getErrorDTO().setSuccess(true);
		}

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("商品情報を" +  messageDTO.getResultCnt() +"件削除しました。");
		form.setRegistryDto(messageDTO);
		form.setMessageFlg("1");

		return searchDomesticExhibition(appMapping, form, request);
	}

	/**
	 * 出品データベース一覧の検索結果をダウンロードするメソッド
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward domesticDownload(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//テンプレートのパス取得
		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.DOMESTIC_TEMPLATE_PATH);
		// ファイルを読み込みます。
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ワークブックを読み込みます。
		HSSFWorkbook workBook = new HSSFWorkbook(filein);
		//エクセルファイルを編集します
		ExportItemListService exportItemListService = new ExportItemListService();
		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "";
		//インスタンス生成
		DomesticExhibitionService service = new DomesticExhibitionService();
		//セット商品フラグ読み替え
		service.setFlagsSwutch(form.getDomesticExhibitionSearchDTO());
		workBook = exportItemListService.getExportDoemsticExhibiti(form.getDomesticExhibitionSearchDTO(), workBook);
		fname = "出品DB情報_" + date + ".xls";

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
	 * 国内注文管理一覧画面からレコード選択された場合の検索処理
	 * @param appMapping
	 * @param request
	 * @param searchForm
	 * @return
	 * @throws Exception
	 */
	private ActionForward searchDomesticExhibitionItem(AppActionMapping appMapping,
			 HttpServletRequest request, DomesticExhibitionForm form) throws Exception {

		//セッションの参照渡し
		HttpSession sesstion = request.getSession();
		DomesticOrderListForm searchForm = (DomesticOrderListForm)sesstion.getAttribute("domesticOrderListForm");
		//エラーメッセージの初期化
		form.setErrorDTO(new ErrorDTO());
		//インスタンス生成
		MakerService makerService = new MakerService();
		DomesticExhibitionService service = new DomesticExhibitionService();
		//一覧の初期化
		form.setDomesticExhibitionList(new ArrayList<DomesticExhibitionDTO>());
		form.setSysManagementIdList(new ArrayList<DomesticExhibitionDTO>());
		//メーカーリスト取得
		form.setDomesticMakerNmList(makerService.getMakerInfo());
		//追加分listをセット
		form.setAddDomesticExhibitionList(service.initAddDomesticExhibitionList());
		//メッセージ初期化
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);
		//メッセージフラグを切っておく
		form.setMessageFlg("0");

		form.getDomesticExhibitionSearchDTO().setManagementCode(searchForm.getManagementCode());
		form.getSysManagementIdList().add(service.getDomesticExhibionIdItem(form.getDomesticExhibitionSearchDTO()));
		form.setSysManagementIdListSize(form.getSysManagementIdList().size());
		form.setDomesticExhibitionListPageMax(WebConst.LIST_PAGE_MAX_MAP.get("1"));
		form.setPageIdx(0);
		//検索実行
		form.setDomesticExhibitionList(service.getDomesticExhibionSearch(form.getSysManagementIdList(), form.getPageIdx(), form.getDomesticExhibitionSearchDTO()));

		//検索結果0件メッセージを表示する
		if (form.getDomesticExhibitionList().isEmpty()) {
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("検索結果が0件です。");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
		}
		form.getRegistryDto().setMessageFlg("0");


		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);

	}

	/**
	 * 国内セット商品登録初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initSetManegementRegist(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request) throws Exception {

		DomesticExhibitionDTO setItemDTO = new DomesticExhibitionDTO();
		DomesticExhibitionService service = new DomesticExhibitionService();
		MakerService makerService = new MakerService();
		List<ExtendSetDomesticExhibitionDto> formList = new ArrayList<>();

		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//エラーメッセージの初期化
		form.setErrorDTO(new ErrorDTO());
		//メッセージ初期化
		form.setRegistryDto(messageDTO);

		form.setSetDomesticExhibitionDTO(setItemDTO);
		//国内商品検索タイプ設定
		form.getDomesticExhibitionSearchDTO().setSearchItemType(WebConst.DOMESTIC_ITEM_SEARCH_TYPE_1);
		//メーカーリスト取得
		form.setDomesticMakerNmList(makerService.getMakerInfo());
		//追加分listをセット
		form.setFormDomesticExhibitionList(formList);
		form.setAddformDomesticExhibitionList(service.initAddFromDomesticExhibitionList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 国内セット商品編集初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initSetManegementEdit(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		List<ExtendSetDomesticExhibitionDto> fromDomesticExhibitionList = new ArrayList<>();
		DomesticExhibitionService service = new DomesticExhibitionService();
		DomesticExhibitionDTO setItemDTO = new DomesticExhibitionDTO();
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
		MakerService makerService = new MakerService();
		HttpSession sesstion = request.getSession();
		String onloadActiontype = "0";

		//更新・削除から呼び出されていない場合、メッセージを初期化
		if (!form.getMessageFlg().equals("1")) {
			RegistryMessageDTO messageDTO = new RegistryMessageDTO();
			//メッセージ初期化
			form.setRegistryDto(messageDTO);
			form.setErrorDTO(new ErrorDTO());
		} else {
			//商品検索でSetDomesticExhibitionDTOの中身を入れ替えるため、画面ロード時のコメント種別を退避
			onloadActiontype = form.getSetDomesticExhibitionDTO().getOnloadActionType();
			//メッセージフラグを初期化
			form.setMessageFlg("0");
			form.getRegistryDto().setMessageFlg("0");
		}
		//選択された商品のシステム管理IDを保持する
		if (form.getSetDomesticExhibitionDTO() == null) {
			form.setSetDomesticExhibitionDTO(new DomesticExhibitionDTO());
		}

		//リダイレクトによってシステム管理IDが引き渡されない場合、セッションから取得している
		//リダイレクト以外はシステム管理IDが引き渡されるのでDTOから取得
		if (form.getDomesticExhibitionSearchDTO().getSysManagementId() == 0) {
			form.getSetDomesticExhibitionDTO().setSysManagementId((long)sesstion.getAttribute("test"));
		} else {
			form.getSetDomesticExhibitionDTO().setSysManagementId(form.getDomesticExhibitionSearchDTO().getSysManagementId());
		}
		//親商品検索
		setItemDTO = dao.getSetDomesticExhibition(form.getSetDomesticExhibitionDTO().getSysManagementId());

		//親商品情報をFormDTOに格納
		form.setSetDomesticExhibitionDTO(setItemDTO);
		//オープン価格フラグ読み替え
		if (StringUtils.isNotEmpty(form.getSetDomesticExhibitionDTO().getOpenPriceFlg())) {
			form.getSetDomesticExhibitionDTO().setOpenPriceFlg(StringUtil.switchCheckBox(form.getSetDomesticExhibitionDTO().getOpenPriceFlg()));
		}
		//画面ロード時のコメント種別を格納→0：なし　1：登録　2：更新　3：削除
		form.getSetDomesticExhibitionDTO().setOnloadActionType(onloadActiontype);

		//親商品のシステム管理IDをKEYに構成商品を検索する
		fromDomesticExhibitionList = dao.getFormDomesticExhibition(form.getSetDomesticExhibitionDTO().getSysManagementId());
		//取得した構成商品を格納
		form.setFormDomesticExhibitionList(fromDomesticExhibitionList);
		//メーカーリスト取得
		form.setDomesticMakerNmList(makerService.getMakerInfo());
		//国内商品検索タイプ設定：商品検索時の挙動を分ける種別
		form.getDomesticExhibitionSearchDTO().setSearchItemType(WebConst.DOMESTIC_ITEM_SEARCH_TYPE_1);
		//追加分listをセット
		form.setAddformDomesticExhibitionList(service.initAddFromDomesticExhibitionList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 国内セット商品登録処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward registSetManagementItem(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンス生成
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		DomesticExhibitionService service = new DomesticExhibitionService();

		//管理品番の重複チェック
		messageDTO = service.checkSetManagementCode(form.getSetDomesticExhibitionDTO(), messageDTO);

		//すでに登録されている場合はエラーメッセージ返却
		if (!messageDTO.isSuccess()) {
			List<ErrorMessage> message = new ArrayList<ErrorMessage>();
			message.addAll(messageDTO.getResult().getErrorMessages());
			saveErrorMessages(request, message);
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("登録に失敗しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		begin();

		//セット親商品登録処理
		messageDTO = service.registSetDomesticExhibition(form.getSetDomesticExhibitionDTO(), messageDTO);
		//リダイレクト用に今登録したSET商品のシステム管理IDを取得する
		form.getSetDomesticExhibitionDTO().setSysManagementId(messageDTO.getSysManagementId());
		HttpSession sesstion = request.getSession();
		sesstion.setAttribute("test", messageDTO.getSysManagementId());
		//登録件数が0件の場合エラーメッセージ返却
		if (messageDTO.getResultCnt() == 0) {
			rollback();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("登録に失敗しました。");
			form.setMessageFlg("0");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {
			int targetFromItem = 0;
			for (ExtendSetDomesticExhibitionDto cntDto : form.getAddformDomesticExhibitionList()) {
				if (StringUtils.isBlank(cntDto.getManagementCode())) {
					continue;
				}
				targetFromItem++;
			}

			//構成商品登録処理
			int resultFromItem = service.registFromDomesticExhibition(form.getAddformDomesticExhibitionList(), form.getSetDomesticExhibitionDTO());

			form.getAddformDomesticExhibitionList();
			if (resultFromItem != targetFromItem) {
				rollback();
				messageDTO.setMessageFlg("1");
				messageDTO.setMessage("登録に失敗しました。");
				form.setMessageFlg("0");
				form.setRegistryDto(messageDTO);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}
			commit();
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("セット商品を登録しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");
			form.getSetDomesticExhibitionDTO().setOnloadActionType(WebConst.ONLOAD_ACTION_TYPE_REGIST);
		}
		//リダイレクト処理
		response.sendRedirect(SystemSetting.get("SET_DOMESTIC_REDIRECT_URL"));
//		response.sendRedirect(SystemSetting.get("SET_DOMESTIC_REDIRECT_URL")+"?param=" + form.getSetDomesticExhibitionDTO().getSysManagementId());
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 国内セット商品更新処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateSetManagementItem(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//インスタンス生成
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		DomesticExhibitionService service = new DomesticExhibitionService();
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
		DomesticExhibitionDTO setItemDTO = new DomesticExhibitionDTO();

		//管理品番が変更されていた場合、管理品番の重複チェック
		if (!form.getSetDomesticExhibitionDTO().getManagementCode().equals(form.getSetDomesticExhibitionDTO().getBeforeManagementCode())) {

			//管理品番の重複チェック実行
			messageDTO = service.checkSetManagementCode(form.getSetDomesticExhibitionDTO(), messageDTO);

			//すでに登録されている場合はエラーメッセージ返却
			if (!messageDTO.isSuccess()) {
				List<ErrorMessage> message = new ArrayList<ErrorMessage>();
				message.addAll(messageDTO.getResult().getErrorMessages());
				saveErrorMessages(request, message);
				messageDTO.setMessageFlg("1");
				messageDTO.setMessage("更新に失敗しました。");
				form.setMessageFlg("0");
				form.setRegistryDto(messageDTO);
				return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
			}
		}
		begin();
		//セット親商品更新処理
		int resultSetItem = service.updateSetDomesticExhibition(form.getSetDomesticExhibitionDTO());

		//更新対象を使用しているの国内注文商品とそれに紐づく売上商品の原価を更新
		messageDTO = service.updateDomesticOrderItemCost(messageDTO, form.getSetDomesticExhibitionDTO());


		//登録件数が0件の場合エラーメッセージ返却
		if (resultSetItem == 0) {
			rollback();
			messageDTO.setMessageFlg("1");
			messageDTO.setMessage("更新に失敗しました。");
			form.setMessageFlg("0");
			form.setRegistryDto(messageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		} else {


			/****************************************表示分登録START****************************************/
			int targetFormItem = 0;
			for (ExtendSetDomesticExhibitionDto cntDto : form.getFormDomesticExhibitionList()) {
				if (StringUtils.isBlank(cntDto.getManagementCode())) {
					continue;
				}
				targetFormItem++;
			}
			int resultFromItem = 0;
			if (targetFormItem > 0) {
				//構成商品更新処理：表示分
				resultFromItem = service.updateFromDomesticExhibition(form.getFormDomesticExhibitionList(), form.getSetDomesticExhibitionDTO());

				if (resultFromItem != targetFormItem) {
					rollback();
					messageDTO.setMessageFlg("1");
					messageDTO.setMessage("更新に失敗しました。");
					form.setMessageFlg("0");
					form.setRegistryDto(messageDTO);
					return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
				}
			}
			/*****************************************表示分登録END*****************************************/

			/****************************************追加分登録START****************************************/
			int targetAddItem = 0;
			for (ExtendSetDomesticExhibitionDto cntDto : form.getAddformDomesticExhibitionList()) {
				if (StringUtils.isBlank(cntDto.getManagementCode())) {
					continue;
				}
				targetAddItem++;
			}
			int resultAddItem = 0;
			if (targetAddItem > 0) {
				//構成商品登録処理：追加分
				resultAddItem = service.registFromDomesticExhibition(form.getAddformDomesticExhibitionList(), form.getSetDomesticExhibitionDTO());

				form.getAddformDomesticExhibitionList();
				if (resultAddItem != targetAddItem) {
					rollback();
					messageDTO.setMessageFlg("1");
					messageDTO.setMessage("更新に失敗しました。");
					form.setMessageFlg("0");
					form.setRegistryDto(messageDTO);
					return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
				}
			}
			/*****************************************追加分登録END*****************************************/


			commit();
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("セット商品を更新しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");

		}

		//再表示処理スタート
		MakerService makerService = new MakerService();
		setItemDTO = dao.getSetDomesticExhibition(form.getSetDomesticExhibitionDTO().getSysManagementId());
		form.setSetDomesticExhibitionDTO(setItemDTO);
		List<ExtendSetDomesticExhibitionDto> fromDomesticExhibitionList = new ArrayList<>();
		fromDomesticExhibitionList = dao.getFormDomesticExhibition(setItemDTO.getSysManagementId());
		form.setFormDomesticExhibitionList(fromDomesticExhibitionList);
		//メーカーリスト取得
		form.setDomesticMakerNmList(makerService.getMakerInfo());
		//国内商品検索タイプ設定
		form.getDomesticExhibitionSearchDTO().setSearchItemType(WebConst.DOMESTIC_ITEM_SEARCH_TYPE_1);
		//追加分listをセット
		form.setAddformDomesticExhibitionList(service.initAddFromDomesticExhibitionList());
		form.getSetDomesticExhibitionDTO().setOnloadActionType(WebConst.ONLOAD_ACTION_TYPE_UPDATE);

		//リダイレクト処理
		response.sendRedirect(SystemSetting.get("SET_DOMESTIC_REDIRECT_URL"));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 国内セット構成商品削除処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteSetDomesticExhibition(AppActionMapping appMapping,
			DomesticExhibitionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//インスタンス生成
		DomesticExhibitionService service = new DomesticExhibitionService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		DomesticExhibitionDTO setItemDTO = new DomesticExhibitionDTO();
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();

		//エラーメッセージの初期化
		form.setErrorDTO(new ErrorDTO());
		//メッセージ初期化
		form.setRegistryDto(messageDTO);

		//削除実行
		messageDTO = service.deleteSetDomesticExhibition(messageDTO, form.getFormDomesticExhibitionList());

		messageDTO.setMessageFlg("0");
		messageDTO.setMessage("商品情報を" +  messageDTO.getResultCnt() +"件削除しました。");
		form.setRegistryDto(messageDTO);
		form.setMessageFlg("1");


		//再表示処理スタート
		MakerService makerService = new MakerService();
		setItemDTO = dao.getSetDomesticExhibition(form.getSetDomesticExhibitionDTO().getSysManagementId());
		form.setSetDomesticExhibitionDTO(setItemDTO);
		List<ExtendSetDomesticExhibitionDto> fromDomesticExhibitionList = new ArrayList<>();
		fromDomesticExhibitionList = dao.getFormDomesticExhibition(setItemDTO.getSysManagementId());
		form.setFormDomesticExhibitionList(fromDomesticExhibitionList);
		//メーカーリスト取得
		form.setDomesticMakerNmList(makerService.getMakerInfo());
		//国内商品検索タイプ設定
		form.getDomesticExhibitionSearchDTO().setSearchItemType(WebConst.DOMESTIC_ITEM_SEARCH_TYPE_1);
		//追加分listをセット
		form.setAddformDomesticExhibitionList(service.initAddFromDomesticExhibitionList());
		form.getSetDomesticExhibitionDTO().setOnloadActionType(WebConst.ONLOAD_ACTION_TYPE_DELETE);

		//リダイレクト処理
		response.sendRedirect(SystemSetting.get("SET_DOMESTIC_REDIRECT_URL"));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


}
