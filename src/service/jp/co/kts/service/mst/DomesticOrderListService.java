package jp.co.kts.service.mst;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticOrderListSearchDTO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.mst.DomesticOrderDAO;
import jp.co.kts.dao.mst.DomesticSlipDAO;
import jp.co.kts.service.fileExport.ExportDomesticOrderService;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticOrderListService {

	/** 国内一覧検索結果チェックボックス選択値 */
	private static final String ORDER_CHECK_FLG_ON = "on";
	/** 国内一覧検索結果原価確認フラグON：1 */
	private static final String COST_COMF_FLG_ON = "1";
	/** 国内一覧検索結果原価確認フラグON画面表示値：済 */
	private static final String COST_COMF_FLG_ON_VAL = "済";

	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");


	/**
	 * 国内注文一覧検索対象件数取得用
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<DomesticOrderListDTO> getDomesticOrderIdList(DomesticOrderListSearchDTO searchDto) throws Exception{
		List<DomesticOrderListDTO> list = new DomesticSlipDAO().getDomesticOrderIdSearch(searchDto);

		return list;
	}


	/**
	 * 商品の検索とページングの設定を行う
	 * @param domesticOrderItemIdList
	 * @param pageIdx
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<DomesticOrderListDTO> getDomesticOrderList(List<DomesticOrderListDTO> domesticOrderItemIdList,
			int pageIdx, DomesticOrderListSearchDTO searchDto) throws Exception{

		List<DomesticOrderListDTO> list = new ArrayList<>();
		Map<String, String> domesticstatus = WebConst.DOMESTIC_EXIHIBITION_STATUS_MAP;
		String strYear = DateUtil.dateToString("YY");

		if (StringUtils.isEmpty(searchDto.getListPageMax())) {
			searchDto.setListPageMax("1");
		}

		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(searchDto.getListPageMax()) * pageIdx;
				i < WebConst.LIST_PAGE_MAX_MAP.get(searchDto.getListPageMax()) * (pageIdx + 1)
				&& i < domesticOrderItemIdList.size(); i++) {
			DomesticOrderListSearchDTO searchItemDto = new DomesticOrderListSearchDTO();
			searchItemDto.setSysDomesticItemId(domesticOrderItemIdList.get(i).getSysDomesticItemId());
			DomesticOrderListDTO resultDto = new DomesticSlipDAO().getDomesticOrderItemList(searchItemDto);


			/***********************和名変換START**********************/
			//注文書No.
			resultDto.setPurchaseOrderNo("KS" + resultDto.getPurchaseOrderNo());
			//ステータス
			if (!StringUtils.isBlank(resultDto.getStatus())) {
				resultDto.setStatusNm(domesticstatus.get(resultDto.getStatus()));
			}
			//原価確認FLG
			if (!StringUtils.isBlank(resultDto.getCostComfFlag())
					&& resultDto.getCostComfFlag().equals(COST_COMF_FLG_ON)) {
				resultDto.setCostComfFlagNm(COST_COMF_FLG_ON_VAL);
			} else {
				resultDto.setCostComfFlagNm(StringUtils.EMPTY);
			}

			//納入先名
			if (resultDto.getSysWarehouseId() == 99) {
				resultDto.setDeliveryNm("その他");
			}

			//国内注文伝票ID
			resultDto.setStrSysDomesticSlipId(String.valueOf(resultDto.getSysDomesticSlipId()));

			/************************和名変換END***********************/

			list.add(resultDto);
		}
		return list;
	}

	/**
	 * 国内一覧画面の削除処理
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int deleteDomesticListItem(List<DomesticOrderListDTO> dto) throws DaoException {
		int resultCnt = 0;
		DomesticSlipDAO dao = new DomesticSlipDAO();
		for (DomesticOrderListDTO orderDto : dto) {
			if (!orderDto.getOrderCheckFlg().equals(ORDER_CHECK_FLG_ON)) {
				continue;
			}
			resultCnt += dao.deleteDomesticListItem(orderDto);
		}


		return resultCnt;
	}

	/**
	 * 国内一覧画面の更新処理
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticListItem(List<DomesticOrderListDTO> dto) throws DaoException {
		int resultCnt = 0;
		DomesticSlipDAO dao = new DomesticSlipDAO();
		for (DomesticOrderListDTO orderDto : dto) {
			resultCnt += dao.updateDomesticListItem(orderDto);
		}
		return resultCnt;
	}

	/**
	 * 国内注文商品原価反映処理
	 *
	 * @param list
	 * @throws DaoException
	 */
	public RegistryMessageDTO updateDomesticOrderItemCost(RegistryMessageDTO messageDto ,
			List<DomesticOrderListDTO> targetlist) throws DaoException {

		DomesticExhibitionDAO domesticExhibitDao = new DomesticExhibitionDAO();
		DomesticSlipDAO domesticSlipDAO = new DomesticSlipDAO();
		List<ExtendSalesSlipDTO> resultsalesDto = new ArrayList<ExtendSalesSlipDTO>();

		try {
			for (DomesticOrderListDTO targetSubList : targetlist) {
				//同一受注番号の伝票情報を取得する
				resultsalesDto = domesticExhibitDao.getSalesItemTargetSearch(targetSubList);

				if (resultsalesDto != null) {
					for (ExtendSalesSlipDTO targetSalesDto: resultsalesDto) {
						domesticSlipDAO.updateSalesItemTargetCost(targetSalesDto, targetSubList);

					}
				}
			}
		} catch(Exception e) {
			messageDto.setSuccess(false);
			return messageDto;
		}
		messageDto.setSuccess(true);
		return messageDto;
	}

	/**
	 * 伝票に紐づく商品が全て削除されている場合、伝票も削除する処理
	 * @param sysDomesticSlipId
	 * @return
	 * @throws DaoException
	 */
	public int selectUpdateCntTargetSlip(List<Long> sysDomesticSlipId) throws DaoException {
		//インスタンス生成
		DomesticSlipDAO dao = new DomesticSlipDAO();
		int resultCnt = 0;

		for (Long targetId : sysDomesticSlipId) {
			DomesticOrderListDTO dto = dao.selectCntTargetSlip(targetId);
			if (dto.getDeleteCnt() == 0) {
				resultCnt += dao.deleteDomesticSlip(targetId);
			}
		}

		return resultCnt;
	}

	/**
	 * 国内一覧画面のステータス移動更新
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateDomesticListStatus(List<DomesticOrderListDTO> dto, String status) throws DaoException {
		int resultCnt = 0;
		DomesticSlipDAO dao = new DomesticSlipDAO();
		for (DomesticOrderListDTO orderDto : dto) {
			//選択されているもの以外は弾く
			if (!orderDto.getOrderCheckFlg().equals(ORDER_CHECK_FLG_ON)) {
				continue;
			}

			//更新対象がSET商品の場合、構成商品のステータスも変更する
			if (orderDto.getItemType().equals(WebConst.RESULT_ITEM_TYPE_SET)) {
				DomesticOrderDAO domesticDao = new DomesticOrderDAO();
				List<DomesticOrderListDTO> formDtoList = new ArrayList<>();
				//構成商品取得
				formDtoList = domesticDao.selectFormDomesticItem(orderDto.getSysDomesticItemId());
				for (DomesticOrderListDTO orderFormDto : formDtoList) {
					//構成商品ステータス変更
					dao.updateDomesticListStatus(orderFormDto, status);
				}
			}

			resultCnt += dao.updateDomesticListStatus(orderDto, status);
		}
		return resultCnt;
	}

	/**
	 * ステータス移動時、移動ステータスが一つずつ移動しているかチェック
	 * @param messageDto
	 * @param dto
	 * @param status
	 * @return
	 */
	public RegistryMessageDTO checkStatusMove(RegistryMessageDTO messageDto,
			List<DomesticOrderListDTO> dto, String status) {

		//TODO 印刷確認フラグのチェック追加

		for (DomesticOrderListDTO checkDto : dto) {

			if (checkDto.getPrintCheckFlag().equals("0")) {
				messageDto.setSuccess(false);
				messageDto.setMessage("印刷確認が済んでいないデータがあります。");
				return messageDto;
			}
			//移動ステータス：注文「1」
			switch (status) {
			case WebConst.DOMESTIC_EXIHIBITION_SORT_ORDER_NO:
				//同ステータスの場合エラー
				if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_ORDER_NO)) {
					messageDto.setSuccess(false);
					messageDto.setMessage("同一ステータスが選択されています。");
					return messageDto;
				}
				//ステータスが入荷済み以外の場合エラー
				if (!checkDto.getStatus().equals
						(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO)
							&& !checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO)) {
					messageDto.setSuccess(false);
					return messageDto;
				}
				break;
				//移動ステータス：入荷待ち「2」
				case WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO:
					//納入先が「その他」の場合
					if (checkDto.getSysWarehouseId() == 99) {
						messageDto.setSuccess(false);
						messageDto.setMessage("納入先が「その他」の場合「入荷待ち」に移動できません。");
						return messageDto;
					}
					//ステータスが直送の場合
					if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO)) {
						messageDto.setSuccess(false);
						messageDto.setMessage("「直送」からは移動できません。");
						return messageDto;
					}
					//同ステータスの場合エラー
					if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO)) {
						messageDto.setSuccess(false);
						messageDto.setMessage("同一ステータスが選択されています。");
						return messageDto;
					}
					//ステータスが注文以外且つ処理済み以外の場合エラー
					if (!checkDto.getStatus().equals
							(WebConst.DOMESTIC_EXIHIBITION_SORT_ORDER_NO)
									&& !checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO)) {
						messageDto.setSuccess(false);
						return messageDto;
					}
					break;
					//移動ステータス：直送「3」
				case WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO:
					//納入先が「その他以外」の場合
					if (checkDto.getSysWarehouseId() != 99) {
						messageDto.setSuccess(false);
						messageDto.setMessage("納入先が「その他以外」の場合「直送」に移動できません。");
						return messageDto;
					}
					//ステータスが入荷待ちの場合
					if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO)) {
						messageDto.setSuccess(false);
						messageDto.setMessage("「入荷待ち」からは移動できません。");
						return messageDto;
					}
					//同ステータスの場合エラー
					if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO)) {
						messageDto.setSuccess(false);
						messageDto.setMessage("同一ステータスが選択されています。");
						return messageDto;
					}
					//ステータスが注文以外且つ処理済み以外の場合エラー
					if (!checkDto.getStatus().equals
							(WebConst.DOMESTIC_EXIHIBITION_SORT_ORDER_NO)
									&& !checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO)) {
						messageDto.setSuccess(false);
						return messageDto;
					}
					break;
			//移動ステータス：入荷済み「4」
			case WebConst.DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO:
				//同ステータスの場合エラー
				if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO)) {
					messageDto.setSuccess(false);
					messageDto.setMessage("同一ステータスが選択されています。");
					return messageDto;
				}
				//ステータスが注文以外且つ処理済み以外の場合エラー
				if (!checkDto.getStatus().equals
						(WebConst.DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO)
								&& !checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_DIRECT_NO)
								&& !checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_TREATED_NO)) {
					messageDto.setSuccess(false);
					return messageDto;
				}
				break;
			//移動ステータス：処理済「5」
			case WebConst.DOMESTIC_EXIHIBITION_SORT_TREATED_NO:
				//同ステータスの場合エラー
				if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_TREATED_NO)) {
					messageDto.setSuccess(false);
					messageDto.setMessage("同一ステータスが選択されています。");
					return messageDto;
				}
				//ステータスが入荷済み以外且つ原価確認済み以外の場合エラー
				if (!checkDto.getStatus().equals
						(WebConst.DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO)
								&& !checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO)) {
					messageDto.setSuccess(false);
					return messageDto;
				}
				break;
			//移動ステータス：原価確認済「6」
			case WebConst.DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO:
				//同ステータスの場合エラー
				if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO)) {
					messageDto.setSuccess(false);
					messageDto.setMessage("同一ステータスが選択されています。");
					return messageDto;
				}
				//ステータスが処理済み以外の場合エラー
				if (!checkDto.getStatus().equals
						(WebConst.DOMESTIC_EXIHIBITION_SORT_TREATED_NO)
							&& !checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_COMPLETE_NO)) {
					messageDto.setSuccess(false);
					return messageDto;
				}
				break;
				//移動ステータス：完了「7」
			case WebConst.DOMESTIC_EXIHIBITION_SORT_COMPLETE_NO:
				//同ステータスの場合エラー
				if (checkDto.getStatus().equals(WebConst.DOMESTIC_EXIHIBITION_SORT_COMPLETE_NO)) {
					messageDto.setSuccess(false);
					messageDto.setMessage("同一ステータスが選択されています。");
					return messageDto;
				}
				//ステータスが原価確認済み以外の場合エラー
				if (!checkDto.getStatus().equals
						(WebConst.DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO)) {
					messageDto.setSuccess(false);
					return messageDto;
				}

				break;
			}
		}
		return messageDto;
	}

	/**
	 * 注文書作成初期メソッド
	 *
	 * @param response
	 * @param slipList
	 * @throws Exception
	 */
	public void orderAcceptanceList(HttpServletResponse response,
		List<ExtendDomesticOrderSlipDTO> dto) throws Exception {

		Date date = new Date();

		String fname = "注文書" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 40, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("orderAcceptance.pdf"));

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 10);

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min, "UniJIS-UCS2-H", BaseFont.EMBEDDED);

		document.open();

		//TODO 一括印刷の場合はココを伝票毎にループさせる
		ExportDomesticOrderService service = new ExportDomesticOrderService();

		for (ExtendDomesticOrderSlipDTO exportDto : dto) {

			service.exportOrderAcceptance(document, writer, baseFont, font, date, exportDto);
			// 改ページ
			document.newPage();
		}

		document.close();
	}







}
