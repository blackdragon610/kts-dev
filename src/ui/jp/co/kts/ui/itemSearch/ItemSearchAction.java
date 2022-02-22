package jp.co.kts.ui.itemSearch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.service.item.ItemSearchService;

public class ItemSearchAction extends AppBaseAction {

	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ItemSearchForm form = (ItemSearchForm)appForm;

		if ("/initSubWinItem".equals(appMapping.getPath())) {
			return initSubWinItem(appMapping, form, request);
		} else if ("/subWinItemSearch".equals(appMapping.getPath())) {
			return subWinItemSearch(appMapping, form, request);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	/**
	 * 売上登録・詳細画面での商品検索ウィンドウ表示初期処理
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 */
	protected ActionForward initSubWinItem(AppActionMapping appMapping,
			ItemSearchForm form, HttpServletRequest request) throws DaoException {

		form.setSearchItemList(new ArrayList<ExtendMstItemDTO>());

		form.setItemListSize(form.getSearchItemList().size());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * 売上登録・詳細画面での商品検索
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 */
	protected ActionForward subWinItemSearch(AppActionMapping appMapping,
			ItemSearchForm form, HttpServletRequest request) throws DaoException {

		//インスタンス生成
		ItemSearchService itemSearchService = new ItemSearchService();
		List<ExtendMstItemDTO> domesticList = new ArrayList<ExtendMstItemDTO>();
		//検索結果一覧初期化
		form.setSearchItemList(new ArrayList<ExtendMstItemDTO>());

		//自社商品の検索
		form.setSearchItemList(itemSearchService.getSearchItemList(form.getSearchItemDTO()));

		//受注番号が入力されている場合国内商品の検索を行う
		if (StringUtils.isNotEmpty(form.getSearchItemDTO().getOrderNo())) {
			//国内商品の検索
			domesticList = itemSearchService.getSearchDomesticItemList(form.getSearchItemDTO());

			//検索結果一覧の最後尾に国内商品を追加
			for (ExtendMstItemDTO dmstcDto : domesticList) {
				form.getSearchItemList().add(dmstcDto);
			}

		}

		form.setItemListSize(form.getSearchItemList().size());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

}