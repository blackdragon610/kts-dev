/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.ui.domestic;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.ErrorMessage;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.DomesticOrderSlipDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderItemSearchDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.service.fileExport.ExportDomesticOrderService;
import jp.co.kts.service.mst.DomesticOrderService;
import jp.co.kts.service.mst.MakerService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.ui.web.struts.WebConst;
import net.arnx.jsonic.JSON;

/**
 * 国内注文書作成のActionクラス
 *
 * Serviceクラスへ
 *
 * @author n_nozawa
 * 20161130
 */
public class DomesticOrderAction extends AppBaseAction {

	//法人情報取得時使用
	private static final Long SYS_CORP_ID_1 = (long)1;
	private static final Long SYS_CORP_ID_2 = (long)2;
	private static final Long SYS_CORP_ID_3 = (long)3;
	private static final Long SYS_CORP_ID_4 = (long)4;
	private static final Long SYS_CORP_ID_5 = (long)5;
	private static final Long SYS_CORP_ID_6 = (long)6;

	@Override
	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		DomesticOrderForm form = (DomesticOrderForm)appForm;

		//国内注文書作成画面初期表示処理
		if ("/initDomesticOrder".equals(appMapping.getPath())) {
			return initdomesticOrder(appMapping, form, request);

		//国内注文書作成画面検索処理
		} else if ("/searchDomesticOrder".equals(appMapping.getPath())) {
			 return searchDomesticOrder(appMapping, form, request);

		//国内注文書作成画面登録処理
		} else if ("/registryDomesticOrderSlip".equals(appMapping.getPath())) {
			 return registryDomesticOrderSlip(appMapping, form, request);

		//国内注文書作成画面更新処理
		} else if ("/updateDomesticOrderSlip".equals(appMapping.getPath())) {
		 return  updateDomesticOrderSlip(appMapping, form, request);

		//国内注文書作成詳細画面
		} else if ("/detailDomesticOrderSlip".equals(appMapping.getPath())) {
		 return  detailDomesticOrderSlip(appMapping, form, request);

		//国内注文書作成画面削除
		} else if ("/deleteDomesticOrderSlip".equals(appMapping.getPath())) {
			return deleteDomesticOrderSlip(appMapping, form, request, response);

		 //国内注文書作成画面JSON型で取得処理
		} else if ("/getDomesticOrderWarehouse".equals(appMapping.getPath())) {
			 return getDomesticOrderWarehouse(appMapping, form, request, response);

		//国内注文書作成画面J検索小ウインドウ
		} else if ("/subWinDomesticItemSearch".equals(appMapping.getPath())) {
			 return subWinDomesticItemSearch(appMapping, form, request, response);

		//国内注文書作成画面、注文書出力
		} else if ("/exportDomesticOrderAcceptanceList".equals(appMapping.getPath())) {
			 return exportDomesticOrderAcceptanceList(appMapping, form, request, response);

		//国内注文書作成画面、注文書出力succes
		} else if ("/domesticOrderAcceptancePrintOutFile".equals(appMapping.getPath())) {
			return domesticOrderAcceptancePrintOutFile(response);

		//国内注文一覧画面からの遷移処理
		} else if ("/searchDomesticSlipItem".equals(appMapping.getPath())){
			return searchDomesticSlipItem(appMapping, form, request);
		//国内注文書作成画面SET商品検索
		} else if ("/searchSetDomesticOrder".equals(appMapping.getPath())){
			return searchSetDomesticOrder(appMapping, form, request, response);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}


	/**
	 * 初期表示
	 *
	 * 倉庫、法人情報取得、ログインユーザ取得
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward initdomesticOrder(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request) throws Exception {

		//メッセージを空にするため
		RegistryMessageDTO registryMessageDTO = new RegistryMessageDTO();
		registryMessageDTO.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDto(registryMessageDTO);

		//伝票部分を初期化
		form.setDomesticOrderSlipDto(new DomesticOrderSlipDTO());
		//商品部分を初期化
		form.setAddDomesticOrderItemList(new ArrayList<DomesticOrderListDTO>());
		//商品部分を初期化
		form.setDomesticOrderList(new ArrayList<DomesticOrderListDTO>());
		//国内商品検索タイプ設定
		form.getDomesticExhibitionSearchDTO().setSearchItemType(WebConst.DOMESTIC_ITEM_SEARCH_TYPE_0);

		// 現在日付を取得.
		String date = DateUtil.dateToString("yyyy/MM/dd");
		form.getDomesticOrderSlipDto().setItemOrderDate(date);

		//インスタンス生成
		DomesticOrderService service = new DomesticOrderService();
		MstUserDTO userDto = new MstUserDTO();
		//ユーザー情報取得
		long sysUserId = ActionContext.getLoginUserInfo().getUserId();

		//追加分listをセット
		form.setAddDomesticOrderItemList(service.initAddDomesticOrderList());

		DomesticOrderSlipDTO dto = new DomesticOrderSlipDTO();

		//その他表示用
		dto.setSysWarehouseId(99);
		dto.setWarehouseNm("その他");

		form.setSysWarehouseId("");
		//倉庫取得
		form.setDomesticOrderSlipList(new ArrayList<>(new DomesticOrderService().getDomesticWarehouse()));

		form.getDomesticOrderSlipList().add(dto);

		//法人情報取得
		List<Long> corporList = new ArrayList<Long>();

		corporList.add(SYS_CORP_ID_1);
		corporList.add(SYS_CORP_ID_2);
		corporList.add(SYS_CORP_ID_3);
		corporList.add(SYS_CORP_ID_4);
		corporList.add(SYS_CORP_ID_5);
		corporList.add(SYS_CORP_ID_6);

		form.setCorporationListDTO(service.getCorporationList(corporList));

		//インスタンスの生成
		MstCorporationDTO corpoDto = new MstCorporationDTO();
		//法人情報に店舗を追加
		corpoDto.setSysCorporationId(99);
		corpoDto.setCorporationNm("店舗");

		form.getCorporationListDTO().add(corpoDto);

		//ログインユーザー情報取得
		UserService userService = new UserService();
		userService.getUser(sysUserId);
		userDto = userService.getUser(sysUserId);
		form.setResponsibleNo(userDto.getResponsibleNo());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 検索
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 */
	private ActionForward searchDomesticOrder(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request) throws DaoException {

		//メッセージを空にするため
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		registryDto.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDto(registryDto);

		//インスタンスを生成
		DomesticOrderService service = new DomesticOrderService();
		form.setExtendDomesticOrderItemSearchList(service.getSearchDomesticOrder(form.getDomesticExhibitionSearchDTO()));
		
		

		//検索結果数をカウント
		form.setItemListSize(form.getExtendDomesticOrderItemSearchList().size());

		if (form.getItemListSize() == 0) {

			registryDto.setMessageFlg("1");
			registryDto.setMessage("検索結果は0件です。");
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 登録
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward registryDomesticOrderSlip(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンスを生成
		DomesticOrderService service = new DomesticOrderService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryDto);

		//登録された受注番号があるか検索
//		registryDto = service.orderNoCheck(registryDto, form.getDomesticOrderSlipDto());

		//管理品番チェック
		registryDto = service.managementCheck(registryDto, form.getDomesticOrderList(), form.getAddDomesticOrderItemList());

		//問屋名、定価チェック
		registryDto = service.domesticRegistryCheck(registryDto, form.getDomesticOrderList(), form.getAddDomesticOrderItemList(), form.getDomesticExhibitionSearchDTO());

		//メーカーチェック:メーカー名でのチェックはなくなる？
//		registryDto = service.makerCheck(registryDto, form.getDomesticOrderList(), form.getAddDomesticOrderItemList());

		if (!registryDto.isSuccess()) {
			List<ErrorMessage> message = new ArrayList<ErrorMessage>();
			message.addAll(registryDto.getResult().getErrorMessages());
			saveErrorMessages(request, message);
			registryDto.setMessageFlg("1");
			registryDto.setMessage("登録に失敗しました。");
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//国内注文伝票テーブルに登録
		service.newRegistryDomesticOrderSlip(form.getDomesticOrderSlipDto());
		//国内注文商品テーブルに登録
		service.newRegistryDomesticOrderItemList(form.getAddDomesticOrderItemList(), form.getDomesticOrderSlipDto().getSysDomesticSlipId());

		//登録完了メッセージ
		registryDto.setMessageFlg("0");
		registryDto.setMessage("注文書を登録しました。");
		form.setMessageFlg("1");


		form.setSysDomesticSlipId(form.getDomesticOrderSlipDto().getSysDomesticSlipId());

		form.setDomesticOrderSlipDto(form.getDomesticOrderSlipDto());
		form.setDomesticOrderItemDto(form.getDomesticOrderItemDto());

		//追加分？検索用？listをセット
		form.setAddDomesticOrderItemList(service.initAddDomesticOrderList());

		return detailDomesticOrderSlip(appMapping, form, request);
	}


	/**
	 * 詳細画面
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward detailDomesticOrderSlip(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンス生成
		DomesticOrderService service = new DomesticOrderService();

		MstUserDTO userDto = new MstUserDTO();
		//ユーザー情報取得
		long sysUserId = ActionContext.getLoginUserInfo().getUserId();

		//追加分？検索用？listをセット
//		form.setAddDomesticOrderItemList(service.initAddDomesticOrderList());

		//インスタンス生成
		DomesticOrderSlipDTO dto = new DomesticOrderSlipDTO();

		//その他表示用
		dto.setSysWarehouseId(99);
		dto.setWarehouseNm("その他");

		form.setSysWarehouseId("");
		//倉庫取得
		form.setDomesticOrderSlipList(new ArrayList<>(new DomesticOrderService().getDomesticWarehouse()));

		form.getDomesticOrderSlipList().add(dto);

		//法人情報取得
		List<Long> corporList = new ArrayList<Long>();

		corporList.add(SYS_CORP_ID_1);
		corporList.add(SYS_CORP_ID_2);
		corporList.add(SYS_CORP_ID_3);
		corporList.add(SYS_CORP_ID_4);
		corporList.add(SYS_CORP_ID_5);
		corporList.add(SYS_CORP_ID_6);

		//国内商品検索タイプ設定
		form.getDomesticExhibitionSearchDTO().setSearchItemType(WebConst.DOMESTIC_ITEM_SEARCH_TYPE_0);

		form.setCorporationListDTO(service.getCorporationList(corporList));

		//インスタンスの生成
		MstCorporationDTO corpoDto = new MstCorporationDTO();
		//法人情報に店舗を追加
		corpoDto.setSysCorporationId(99);
		corpoDto.setCorporationNm("店舗");

		form.getCorporationListDTO().add(corpoDto);

		//ログインユーザー情報取得
		UserService userService = new UserService();
		userService.getUser(sysUserId);
		userDto = userService.getUser(sysUserId);
		form.setResponsibleNo(userDto.getResponsibleNo());

		//登録分商品取得
		form.setDomesticOrderList(service.getSearchDomesticOrderItemList(form.getDomesticOrderSlipDto().getSysDomesticSlipId()));
		//伝票部分を取得
		form.setDomesticOrderSlipDto(service.getSearchDomesticOrder(form.getDomesticOrderSlipDto().getSysDomesticSlipId()));

//		form.setDomesticOrderSlipDto(dto.getSysDomesticSlipId());
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}


	/**
	 * 更新
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateDomesticOrderSlip(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request) throws Exception {

		//インスタンスを生成
		DomesticOrderService service = new DomesticOrderService();
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(registryDto);

		//管理品番チェック
		registryDto = service.managementCheck(registryDto, form.getDomesticOrderList(), form.getAddDomesticOrderItemList());

		//問屋名、定価チェック
		registryDto = service.domesticRegistryCheck(registryDto, form.getDomesticOrderList(), form.getAddDomesticOrderItemList(), form.getDomesticExhibitionSearchDTO());

		//メーカーチェック
//		registryDto = service.makerCheck(registryDto, form.getDomesticOrderList(), form.getAddDomesticOrderItemList());


		if (!registryDto.isSuccess()) {
			List<ErrorMessage> message = new ArrayList<ErrorMessage>();
			message.addAll(registryDto.getResult().getErrorMessages());
			saveErrorMessages(request, message);
			registryDto.setMessageFlg("1");
			registryDto.setMessage("更新に失敗しました。");
			form.setMessageFlg("0");
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//国内注伝票テーブルに更新
		service.updateDomesticOrderSlip(form.getDomesticOrderSlipDto());
		//国内注文商品テーブルに更新
		service.updateDomesticOrderItemList(form.getDomesticOrderList(), form.getDomesticOrderSlipDto().getSysDomesticSlipId());
		//国内注文商品テーブルに登録
		service.newRegistryDomesticOrderItemList(form.getAddDomesticOrderItemList(), form.getDomesticOrderSlipDto().getSysDomesticSlipId());

		//更新完了メッセージ
		registryDto.setMessageFlg("0");
		registryDto.setMessage("注文書を更新しました。");
		form.setMessageFlg("1");

		//追加分？検索用？listをセット
		form.setAddDomesticOrderItemList(service.initAddDomesticOrderList());

		return detailDomesticOrderSlip(appMapping, form, request);
	}


	/**
	 * 削除
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward deleteDomesticOrderSlip(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//インスタンス生成
		DomesticOrderService service = new DomesticOrderService();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);

		int deleteCnt = 0;
		//削除対象の件数をカウント
		for (DomesticOrderListDTO dto: form.getDomesticOrderList()) {
			if (StringUtils.equals(dto.getDeleteCheckFlg(), "on")) {
				deleteCnt++;
			}
		}

		//登録されたものの削除
		service.setFlags(form.getDomesticOrderList());
		int resultDeleteCnt = service.deleteDomesticOrderItem(form.getDomesticOrderList());

		if (deleteCnt == resultDeleteCnt) {
			messageDTO.setMessageFlg("0");
			messageDTO.setMessage("商品を削除しました。");
			form.setRegistryDto(messageDTO);
			form.setMessageFlg("1");
		}

		detailDomesticOrderSlip(appMapping, form, request);
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 *
	 * 納入先情報取得処理
	 *
	 * @param appMapping
	 * @param warehouseForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward getDomesticOrderWarehouse(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//倉庫IDから倉庫情報を取得
		DomesticOrderService service = new DomesticOrderService();
		DomesticOrderSlipDTO domesticOrderSlipDto = service.getDomesticWarehouse(Long.valueOf(form.getSysWarehouseId()));



		//倉庫情報が取得できているか判定
		if (domesticOrderSlipDto == null || domesticOrderSlipDto.getWarehouseNm() == "") {
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(""));
		} else {
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print(JSON.encode(domesticOrderSlipDto));
		}
		return null;
	}



	/**
	 * 検索小画面初期表示
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward subWinDomesticItemSearch(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//メッセージを空にするため
		RegistryMessageDTO registryDto = new RegistryMessageDTO();
		registryDto.setRegistryMessage(StringUtils.EMPTY);
		form.setRegistryDto(registryDto);

		//検索結果数初期化
		form.setItemListSize(0);
		//検索条件、検索結果初期化
		form.setDomesticMakerNmList(new ArrayList<MstMakerDTO>());
		//検索結果初期化
		form.setExtendDomesticOrderItemSearchList(new ArrayList<ExtendDomesticOrderItemSearchDTO>());
		//検索条件初期化
		form.setDomesticExhibitionSearchDTO(new DomesticExhibitionSearchDTO());

		//インスタンス生成
		MakerService makerService = new MakerService();
		//メーカー情報取得
		form.setDomesticMakerNmList(makerService.getMakerInfo());


		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
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
	protected ActionForward exportDomesticOrderAcceptanceList(AppActionMapping appMapping, DomesticOrderForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		//出力する伝票を取得し、ExtendDTOに格納
		DomesticOrderService service = new DomesticOrderService();
		form.setExtendDomesticOrderSlipDto(service.getSearchDomesticOrderSlip(form.getDomesticOrderSlipDto().getSysDomesticSlipId()));
		form.getExtendDomesticOrderSlipDto().setDomesticOrderItemList(service.getSearchExtendDomesticOrderItemList(form.getDomesticOrderSlipDto().getSysDomesticSlipId()));


		begin();
		//国内注伝票テーブルの印刷フラグ更新
		form.getDomesticOrderSlipDto().setPrintCheckFlag(form.getPrintCheckFlag());
		service.updateDomesticSlipPrintFlg(form.getDomesticOrderSlipDto());

		if (form.getPrintCheckFlag().equals("on")) {
			//ステータス移動と採番処理
			for (DomesticOrderListDTO updDto : form.getExtendDomesticOrderSlipDto().getDomesticOrderItemList()) {

				//ステータスが「注文前」の場合のみステータス移動、採番実施
				if (updDto.getStatus().equals("1")) {
					//納入先が「その他」以外の場合,採番して、ステータスを「入荷待ち」に変更する
					if (form.getExtendDomesticOrderSlipDto().getSysWarehouseId() != 99) {
						//更新処理実行
						updDto.setStatus(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO);
						service.updateDomesticOrderItem(updDto, form.getDomesticOrderSlipDto().getSysDomesticSlipId());

					//ステータスを「直送」に変更する
					} else {
						updDto.setStatus(WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO);
						service.updateDomesticOrderItem(updDto, form.getDomesticOrderSlipDto().getSysDomesticSlipId());
					}
				}
			}
		}

		for (int i = 0; i < form.getExtendDomesticOrderSlipDto().getDomesticOrderItemList().size(); i++) {
			if (form.getExtendDomesticOrderSlipDto().getDomesticOrderItemList().get(i).getItemType().equals(WebConst.RESULT_ITEM_TYPE_SET)) {
				form.getExtendDomesticOrderSlipDto().getDomesticOrderItemList().remove(i);
				i--;
			}
		}



		//取得したExtendDTOがnullだったらエラー
		if (form.getExtendDomesticOrderSlipDto() == null) {
			rollback();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		try{
			//サービスで出力内容を設定
			ExportDomesticOrderService exportService = new ExportDomesticOrderService();
			exportService.orderAcceptanceList(response, form.getExtendDomesticOrderSlipDto());

		}catch (Exception e) {
			rollback();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw e;
		}

		form.getDomesticOrderSlipDto().setItemOrderDate(StringUtil.getToday());
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
	protected ActionForward domesticOrderAcceptancePrintOutFile(
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

	/**
	 * 国内注文管理一覧画面からの遷移
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward searchDomesticSlipItem(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request) throws Exception {

		RegistryMessageDTO messageDTO = new RegistryMessageDTO();
		//メッセージ初期化
		form.setRegistryDto(messageDTO);

		//セッションの参照渡し
		HttpSession sesstion = request.getSession();
		DomesticOrderListForm searchForm = (DomesticOrderListForm)sesstion.getAttribute("domesticOrderListForm");

		//インスタンス生成
		DomesticOrderService service = new DomesticOrderService();

		MstUserDTO userDto = new MstUserDTO();
		//ユーザー情報取得
		long sysUserId = ActionContext.getLoginUserInfo().getUserId();

		DomesticOrderSlipDTO dto = new DomesticOrderSlipDTO();

		//その他表示用
		dto.setSysWarehouseId(99);
		dto.setWarehouseNm("その他");

		form.setSysWarehouseId("");
		//国内倉庫取得
		form.setDomesticOrderSlipList(new ArrayList<>(new DomesticOrderService().getDomesticWarehouse()));

		form.getDomesticOrderSlipList().add(dto);

		//法人情報取得
		List<Long> corporList = new ArrayList<Long>();

		corporList.add(SYS_CORP_ID_1);
		corporList.add(SYS_CORP_ID_2);
		corporList.add(SYS_CORP_ID_3);
		corporList.add(SYS_CORP_ID_4);
		corporList.add(SYS_CORP_ID_5);
		corporList.add(SYS_CORP_ID_6);

		form.setCorporationListDTO(service.getCorporationList(corporList));

		//インスタンスの生成
		MstCorporationDTO corpoDto = new MstCorporationDTO();
		//法人情報に店舗を追加
		corpoDto.setSysCorporationId(99);
		corpoDto.setCorporationNm("店舗");

		form.getCorporationListDTO().add(corpoDto);

		//ログインユーザー情報取得
		UserService userService = new UserService();
		userService.getUser(sysUserId);
		userDto = userService.getUser(sysUserId);
		form.setResponsibleNo(userDto.getResponsibleNo());

		//登録分商品取得
		form.setDomesticOrderList(service.getSearchDomesticOrderItemList(Long.valueOf(searchForm.getStrSysDomesticSlipId())));
		//伝票部分を取得
		form.setDomesticOrderSlipDto(service.getSearchDomesticOrder(Long.valueOf(searchForm.getStrSysDomesticSlipId())));
		//追加分？検索用？listをセット
		form.setAddDomesticOrderItemList(service.initAddDomesticOrderList());
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 検索
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 * @throws IOException
	 */
	private ActionForward searchSetDomesticOrder(AppActionMapping appMapping,
			DomesticOrderForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException, IOException {

		//インスタンスを生成
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
		List<ExtendSetDomesticExhibitionDto> formDtoList = new ArrayList<>();
		DomesticOrderService Orderservice = new DomesticOrderService();
		List<ExtendDomesticOrderItemSearchDTO> resultDtoList = new ArrayList<>();
		ExtendDomesticOrderItemSearchDTO formDto = new ExtendDomesticOrderItemSearchDTO();

		//画面で選択されたセット商品の構成商品情報を検索
		formDtoList = dao.getFormDomesticExhibition(form.getSysManagementId());

		//取得した構成商品のシステム管理IDで出品DB検索
		for (ExtendSetDomesticExhibitionDto searchDto : formDtoList) {
			formDto = Orderservice.getFormDomesticOrder(searchDto.getFormSysManagementId(), form.getSysManagementId());
			if (formDto != null) {
				resultDtoList.add(formDto);
			}
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(JSON.encode(resultDtoList));

		return null;
	}
}
