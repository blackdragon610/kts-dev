package jp.co.kts.service.btobBill;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.BtobBillDTO;
import jp.co.kts.app.common.entity.BtobBillItemDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendBtobBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemCostDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.output.entity.CorporateSaleListTotalDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.dao.btobBill.BtobBillDAO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.common.TransactionDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 法人間請求書機能サービスクラス
 * 業務ロジックにて使用される機能を提供する。
 *
 * 作成日　：2015/12/15
 * 作成者　：大山智史
 * 更新日　：
 * 更新者　：
 *
 */

public class BtobBillService {

	static SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	static SimpleDateFormat displyTimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd  HH:mm:ss");

	/**
	 * 在庫検索用DTO初期化
	 * @return 在庫検索用DTO
	 */
	public SearchItemDTO initItemListDTO() {

		SearchItemDTO dto = new SearchItemDTO();

		// 1ページ当たりの最大表示件数を設定
		dto.setListPageMax(WebConst.LIST_PAGE_MAX_CODE_1);

		return dto;
	}

	/**
	 * 在庫検索用DTOチェックボックスのキーと値を設定し直す。
	 *
	 * @param  在庫検索用DTO
	 */
	public void setFlags(SearchItemDTO searchItemDTO) {

		if (StringUtils.isNotEmpty(searchItemDTO.getKeepFlg())) {
			searchItemDTO.setKeepFlg(StringUtil.switchCheckBox(searchItemDTO
					.getKeepFlg()));
		}

		if (StringUtils.isNotEmpty(searchItemDTO.getOrderAlertFlg())) {
			searchItemDTO.setOrderAlertFlg(StringUtil
					.switchCheckBox(searchItemDTO.getOrderAlertFlg()));
		}

		if (StringUtils.isNotEmpty(searchItemDTO.getOverArrivalScheduleFlg())) {
			searchItemDTO.setOverArrivalScheduleFlg(StringUtil
					.switchCheckBox(searchItemDTO.getOverArrivalScheduleFlg()));
		}

		if (StringUtils.isNotEmpty(searchItemDTO.getSetItemFlg())) {
			searchItemDTO.setSetItemFlg(StringUtil.switchCheckBox(searchItemDTO
					.getSetItemFlg()));
		}

		if (StringUtils.isNotEmpty(searchItemDTO.getManualFlg())) {
			searchItemDTO.setManualFlg(StringUtil.switchCheckBox(searchItemDTO
					.getManualFlg()));
		}

		if (StringUtils.isNotEmpty(searchItemDTO.getPlanSheetFlg())) {
			searchItemDTO.setPlanSheetFlg(StringUtil
					.switchCheckBox(searchItemDTO.getPlanSheetFlg()));
		}

		if (StringUtils.isNotEmpty(searchItemDTO.getOtherDocumentFlg())) {
			searchItemDTO.setOtherDocumentFlg(StringUtil
					.switchCheckBox(searchItemDTO.getOtherDocumentFlg()));
		}
	}

	/**
	 * 在庫詳細情報チェックボックスのキーと値を設定し直す。
	 *
	 * @param 在庫詳細情報
	 */
	public void setFlags(ExtendMstItemDTO dto) {

		if (StringUtils.isNotEmpty(dto.getManualFlg())) {
			dto.setManualFlg(StringUtil.switchCheckBox(dto.getManualFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getPlanSheetFlg())) {
			dto.setPlanSheetFlg(StringUtil.switchCheckBox(dto.getPlanSheetFlg()));
		}
	}

	/**
	 * 在庫検索用DTOの検索条件をもとに在庫検索結果システム商品IDリストを取得
	 * @param searchItemDTO  在庫検索用DTO
	 * @return 在庫検索結果システム商品IDリスト
	 */
	public List<SysItemIdDTO> getSysItemIdList(SearchItemDTO searchItemDTO)
			throws DaoException {

		return new ItemDAO().getItemKindCostSearchList(searchItemDTO);
	}

	/**
	 * 在庫検索結果システム商品IDリストを確認
	 * @param 在庫検索結果システム商品IDリスト
	 * @return 確認結果
	 */
	public ErrorMessageDTO checkItemList(List<SysItemIdDTO> itemList)
			throws DaoException {

		if (itemList == null || itemList.size() <= 0) {

			ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する検索結果はありません。");
			return errorMessageDTO;
		}

		return new ErrorMessageDTO();
	}

	/**
	 * 指定されたページに表示する在庫一覧リストを取得する
	 * @param 在庫検索結果システム商品IDリスト
	 * @param 一覧の表示ページ
	 * @param 在庫検索用DTO
	 * @return 検索結果（在庫一覧リスト）
	 */
	public List<ResultItemSearchDTO> getItemList(
			List<SysItemIdDTO> sysItemIdList, int pageIdx, SearchItemDTO dto)
			throws DaoException {

		// 検索結果を初期化
		List<ResultItemSearchDTO> itemList = new ArrayList<>();

		// 在庫一覧１ページ当りの最大表示件数を確認
		if (StringUtils.isEmpty(dto.getListPageMax())) {
			dto.setListPageMax("1");
		}

		// 指定されたページに表示する分のレコードを在庫検索結果システム商品IDリストをもとに
		// 1件ずつDBから取得する。
		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(dto.getListPageMax())
				* pageIdx; i < WebConst.LIST_PAGE_MAX_MAP.get(dto
						.getListPageMax()) * (pageIdx + 1)
						&& i < sysItemIdList.size(); i++) {

			SearchItemDTO searchItemDTO = new SearchItemDTO();

			// 該当行のシステム商品IDを取得し、検索条件に設定
			searchItemDTO.setSysItemId(sysItemIdList.get(i).getSysItemId());

			// 在庫情報を取得
			ResultItemSearchDTO itemDTO = new ItemDAO()
					.getItemKindCostSearch(searchItemDTO);

			if (itemDTO.getArrivalNum() == 0) {

				itemDTO.setArrivalNumDisp(StringUtils.EMPTY);

			} else {

				itemDTO.setArrivalNumDisp(String.valueOf(itemDTO
						.getArrivalNum()));
			}

			if (itemDTO.getBackOrderCount() == 0) {

				itemDTO.setBackOrderCountDisp(StringUtils.EMPTY);

			} else {

				itemDTO.setBackOrderCountDisp(String.valueOf(itemDTO
						.getBackOrderCount()));
			}

			itemList.add(itemDTO);
		}
		return itemList;
	}

	/**
	 * 指定されたシステム商品IDにて在庫詳細を取得する
	 * @param システム商品ID
	 * @return 検索結果（在庫詳細）
	 */
	public ExtendMstItemDTO getMstItemDTO(long sysItemId) throws DaoException {

		ItemDAO dao = new ItemDAO();

		// 在庫詳細をDBから取得する
		return dao.getKindCostDTO(sysItemId);
	}

	/**
	 * Kind原価詳細から商品原価情報を取得し、DBに登録する
	 * @param Kind原価詳細情報
	 * @return エラー情報
	 */
	public ErrorMessageDTO registryKindCost(ExtendMstItemDTO mstItemDTO) throws DaoException {

		ExtendItemCostDTO dto = new ExtendItemCostDTO();

		// 登録情報の設定

		// システム商品原価IDを設定
		dto.setSysItemCostId(new SequenceDAO().getMaxSysItemCostId() + 1);

		// システム商品IDを設定
		dto.setSysItemId(mstItemDTO.getSysItemId());

		// システム法人IDに99：Kind原価を設定
		dto.setSysCorporationId(99);

		// 原価を設定
		dto.setCost((int) mstItemDTO.getCost());

		// Kind原価登録
		ItemDAO dao = new ItemDAO();
		dao.registryItemCost(dto);

		return new ErrorMessageDTO();

	}

	/**
	 * Kind原価詳細から商品原価情報を取得し、DBに更新する
	 * @param Kind原価詳細情報
	 * @return エラー情報
	 */
	public ErrorMessageDTO updateKindCost(ExtendMstItemDTO mstItemDTO) throws DaoException {

		ExtendItemCostDTO dto = new ExtendItemCostDTO();

		// システム商品原価IDを設定
		dto.setSysItemCostId(mstItemDTO.getSysItemCostId());

		// 原価を設定
		dto.setCost((int) mstItemDTO.getCost());

		// Kind原価登録
		ItemDAO dao = new ItemDAO();
		dao.updateItemCost(dto);

		return new ErrorMessageDTO();
	}

	/**
	 * 法人掛け率から商品原価情報を算出し、商品原価リストを作成する
	 * @param Kind原価詳細情報
	 * @param 法人リスト
	 * @return 商品原価リスト
	 */
	public List<ExtendItemCostDTO> calcItemCostList(ExtendMstItemDTO mstItemDTO, List<MstCorporationDTO> corpList)
			throws DaoException {

		List<ExtendItemCostDTO> itemCostList = new ArrayList<ExtendItemCostDTO>();

		BigDecimal itemCost = null; // Kind原価
		BigDecimal corpRate = null; // 法人掛け率

		// 法人の件数分、原価を算出
		for (MstCorporationDTO corpDTO : corpList) {

			ExtendItemCostDTO costDTO = new ExtendItemCostDTO();

			// システム法人IDを設定
			costDTO.setSysCorporationId(corpDTO.getSysCorporationId());

			// 法人名を設定
			costDTO.setCorporationNm(corpDTO.getCorporationNm());

			// 法人掛け率を取得
			corpRate = corpDTO.getCorporationRateOver();

			// Kind原価を商品原価に設定
			itemCost = new BigDecimal(mstItemDTO.getCost());

			// 法人掛け率が設定されている場合、原価を算出
			if (corpRate != null) {

				// 法人掛け率を設定
				costDTO.setCorporationRateOver(corpRate);

				// 法人掛け率の％を小数点に変更
				corpRate = corpRate.multiply(new BigDecimal(0.01));

				// Kind原価 + (Kind原価 * 法人掛け率) = 原価
				itemCost = itemCost.add(itemCost.multiply(corpRate));
				// 小数点以下切り捨て
				itemCost.setScale(0, BigDecimal.ROUND_DOWN);

			} else {
				// 法人掛け率に0を設定
				costDTO.setCorporationRateOver(new BigDecimal(0));

				// 原価に0を設定
				itemCost = new BigDecimal(0);
			}

			// 商品原価に設定
			costDTO.setCost(itemCost.intValue());

			// リストに設定
			itemCostList.add(costDTO);

		}

		return itemCostList;
	}

	/**
	 * 法人の検索条件をもとにシステム法人間請求書IDリストを取得
	 * @param sysCorporationId  法人ID
	 * @return システム法人間請求書IDリスト
	 */
	public List<BtobBillDTO> getSysBtobBillIdList(long sysCorporationId)
			throws DaoException {

		return new BtobBillDAO().getSearchSysBtobBillIdList(sysCorporationId);
	}

	/**
	 * システム法人間請求書IDリストから指定されたページの法人間請求書一覧リストを取得する。
	 * @param sysCorporationId  法人ID
	 * @param pageIdx 指定ページ
	 * @param pageMax 1ページの最大表示件数
	 * @return List<BtobBillDTO> 法人間請求書リスト
	 */
	public List<ExtendBtobBillDTO> getBtobBillList(List<BtobBillDTO> sysCorporationIdList, int pageIdx, int pageMax)
			throws DaoException {

		List<ExtendBtobBillDTO> btobBillList = new ArrayList<>();

		BtobBillDAO dao = new BtobBillDAO();

		// 指定されたページに表示する分のレコードをシステム業販商品IDリストをもとに
		// 1件ずつDBから取得する。
		for (int i = pageMax
				* pageIdx; i < pageMax * (pageIdx + 1)
						&& i < sysCorporationIdList.size(); i++) {

			ExtendBtobBillDTO dto = dao.getSearchBtobBill(sysCorporationIdList.get(i).getSysBtobBillId());

			btobBillList.add(dto);

		}

		return btobBillList;
	}

	/**
	 * システム法人間請求書IDリストから指定されたページの法人間請求書一覧リストを取得する。
	 * @param sysCorporationId  法人ID
	 * @param pageIdx 指定ページ
	 * @param pageMax 1ページの最大表示件数
	 * @return List<BtobBillDTO> 法人間請求書リスト
	 */
	public List<ExtendBtobBillDTO> getBtobBillAllList(List<BtobBillDTO> sysCorporationIdList)
			throws DaoException {

		List<ExtendBtobBillDTO> btobBillList = new ArrayList<>();

		BtobBillDAO dao = new BtobBillDAO();

		// システム業販商品IDリストをもとに
		// 1件ずつDBから取得する。
		for (int i = 0; i < sysCorporationIdList.size(); i++) {

			ExtendBtobBillDTO dto = dao.getSearchBtobBill(sysCorporationIdList.get(i).getSysBtobBillId());

			btobBillList.add(dto);
		}
		return btobBillList;
	}

	/**
	 * 法人間請求書一覧リストのフリーワード・入金日・入金額・手数料をDBに反映する。
	 *
	 * @param 法人間請求書一覧リスト
	 */
	public void updateBtobBillList(List<ExtendBtobBillDTO> btobBillList) throws ParseException, DaoException {

		TransactionDAO transactionDAO = new TransactionDAO();

		BtobBillDAO btobBillDAO = new BtobBillDAO();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
		SimpleDateFormat formatPreMonth = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		ExtendBtobBillDTO dto = new ExtendBtobBillDTO();

		// 法人間請求書一覧リストから1件ずつ取り出しアップデート
		for (int i = 0; i < btobBillList.size(); i++) {

			// リストの末尾から法人間請求書を取得
			dto = btobBillList.get(btobBillList.size() - 1 - i);

			// 請求月の前月を算出
			cal.setTime(format.parse(dto.getDemandMonth() + "/01"));
			cal.add(Calendar.MONTH, -1);

			//開始
			transactionDAO.begin();

			// 前月の法人間請求書を取得
			ExtendBtobBillDTO preMonthDTO = btobBillDAO.getSearchDemandMonthBtobBill(dto.getSysCorporationId(),
					formatPreMonth.format(cal.getTime()));

			// 前月請求金額を設定
			if (preMonthDTO == null) {
				dto.setPreMonthBillAmount(0);
			} else {
				dto.setPreMonthBillAmount(preMonthDTO.getBillAmount());
			}

			// 繰越金額の設定
			dto.setCarryOverAmount(dto.getPreMonthBillAmount() - (dto.getCharge() + dto.getReceivePrice()));

			// 請求金額の設定
			dto.setBillAmount(dto.getSumClaimPrice() + dto.getCarryOverAmount());

			// 法人間請求書を更新
			btobBillDAO.updateBtobBill(dto);

			//登録成功
			transactionDAO.commit();
		}

	}

	/**
	 * 法人間請求書をDBから削除する。
	 *
	 * @param 法人間請求書一覧リスト
	 * @param 削除対象インデックス
	 */
	public void deleteBtobBill(List<ExtendBtobBillDTO> btobBillList, int index) throws DaoException {

		BtobBillDAO btobBillDAO = new BtobBillDAO();

		// 法人間請求書一覧リストからインデックス指定で取り出し、
		// 法人間請求書の削除フラグをアップデート
		btobBillDAO.deleteBtobBill(btobBillList.get(index));

		// 法人間請求書一覧リストからインデックス指定で取り出し、
		// 法人間請求書商品の削除フラグをアップデート
		btobBillDAO.deleteBtobBillItem(btobBillList.get(index));

	}

	/**
	 * 指定されたの法人間請求書を取得する。
	 * @param 法人間請求書一覧リスト
	 * @param 出力対象のリストインデックス
	 * @return 法人間請求書
	 */
	public ExtendBtobBillDTO getExportBtobBill(List<BtobBillDTO> sysBtobBillIdList, int listIdx)
			throws DaoException {

		BtobBillDAO dao = new BtobBillDAO();

		// 法人間請求書を取得
		ExtendBtobBillDTO dto = dao.getSearchBtobBill(sysBtobBillIdList.get(listIdx).getSysBtobBillId());

		return dto;
	}

	/**
	 * 指定されたの法人間請求書の商品情報リストを取得する。
	 * @param 法人間請求書一覧リスト
	 * @param 出力対象のリストインデックス
	 * @return 法人間請求書の商品情報リスト
	 */
	public List<BtobBillItemDTO> getExportBtobBillItemList(List<BtobBillDTO> sysBtobBillIdList, int listIdx)
			throws DaoException {

		return new BtobBillDAO().getSearchBtobBillItemList(sysBtobBillIdList.get(listIdx).getSysBtobBillId());
	}

	/**
	 * 指定された法人の法人間請求書の商品情報リストを全て取得する。
	 * @param 法人間請求書一覧リスト
	 * @param 法人ID
	 * @return 法人間請求書の商品情報リスト
	 */
	public List<SysCorporateSalesSlipIdDTO> getExportBtobBillItemAllList(long sysCorporationID)
			throws DaoException {

		return new BtobBillDAO().getSearchBtobBillItemAllList(sysCorporationID);
	}

	/**
	 * 法人間請求書と法人間請求書の商品情報リストのデータを受け取り、PDFに設定する。
	 * @param response
	 * @param 法人間請求書
	 * @param 法人間請求書の商品情報リスト
	 */
	public void setPDFBtobBill(HttpServletResponse response,
			ExtendBtobBillDTO btobBillDTO, List<BtobBillItemDTO> billItemList)
			throws ParseException, DocumentException, DaoException, IOException, Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
		String targetDateStr = btobBillDTO.getDemandMonth() + "/01";
		Date date = format.parse(targetDateStr);

		String fname = "法人間請求書_" + fileNmTimeFormat.format(date) + ".pdf";
		// ファイル名に日本語を使う場合、以下の方法でファイル名を設定.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		Document document = new Document(PageSize.A4, 5, 5, 245, 5);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("btobBill.pdf"));

		BaseFont baseFont = BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);

		Font font = new Font(BaseFont.createFont(
				AsianFontMapper.JapaneseFont_Min,
				AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED), 9);

		document.open();

		billHeader(document, writer, baseFont, date, btobBillDTO);

		bill(document, writer, font, baseFont, date, btobBillDTO);

		billItemList(document, writer, font, baseFont, date, btobBillDTO, billItemList, 595);

		document.close();

	}

	/**
	 * 法人間請求書のPDFのヘッダー部分を作成する。
	 * @param Document
	 * @param PdfWriter
	 * @param BaseFont
	 * @param Date 請求月
	 */
	private static void billHeader(Document document, PdfWriter writer,
			BaseFont baseFont, Date date, ExtendBtobBillDTO btobBillDTO)
			throws IOException, ParseException, DocumentException, Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();
		// テキストの開始
		pdfContentByte.beginText();

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 16);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(60, 800);

		// 表示する文字列の設定
		pdfContentByte.showText("請求書");

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 11);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(325, 785);

		// 請求月のPDF出力
		// 表示する文字列の設定
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		cal.setTime(date);
		SimpleDateFormat estimateDate = new SimpleDateFormat("yyyy年MM月", Locale.JAPAN);
		pdfContentByte.showText(
				"請求日：" + estimateDate.format(date) + String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH)) + "日");

		// 請求書番号のPDF出力
		// 表示位置の設定
		pdfContentByte.setTextMatrix(475, 785);
		// 表示する文字列の設定
		pdfContentByte.showText("No. " + btobBillDTO.getBtobBillNo());

		// テキストの終了
		pdfContentByte.endText();
	}

	/**
	 * 法人間請求書のPDFの上部の請求金額部分を作成する。
	 * @param Document
	 * @param PdfWriter
	 * @param Font
	 * @param BaseFont
	 * @param BtobBillDTO 法人間請求書データ
	 */
	private static void bill(Document document, PdfWriter writer,
			Font font, BaseFont baseFont, Date date, ExtendBtobBillDTO btobBillDTO)
			throws IOException, ParseException, DocumentException, DaoException, Exception {

		PdfContentByte pdfContentByte = writer.getDirectContent();
		Integer pageHeight = (int) document.getPageSize().getHeight();

		// 取引先法人のPDF出力

		//取引先法人データ
		CorporationService corpService = new CorporationService();
		MstCorporationDTO corpDTO = corpService.getCorporation(btobBillDTO.getSysCorporationId());

		// テキストの開始
		pdfContentByte.beginText();
		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);

		String clientString = "";
		if (btobBillDTO.getSysCorporationId() == 20) {
			clientString = "株式会社日本中央貿易御中";
		} else {
			if (corpDTO.getCorporationFullNm().indexOf("株式会社") < 0) {
				corpDTO.setCorporationFullNm("株式会社" + corpDTO.getCorporationFullNm());
			}
			if (corpDTO.getCorporationFullNm().length() <= 18) {
				clientString = corpDTO.getCorporationFullNm() + "御中";
			} else {
				clientString = corpDTO.getCorporationFullNm().substring(0, 18);
				clientString += "," + corpDTO.getCorporationFullNm().substring(18) + "御中";
			}

			clientString += ",〒" + corpDTO.getZip();
			if (corpDTO.getAddress().length() <= 18) {
				clientString += "," + corpDTO.getAddress();
			} else {
				clientString += "," + corpDTO.getAddress().substring(0, 18);
				clientString += "," + corpDTO.getAddress().substring(18);
			}

			clientString += ",TEL：" + corpDTO.getTelNo();
			if (!StringUtils.isEmpty(corpDTO.getFaxNo())) {
				clientString += ",FAX：" + corpDTO.getFaxNo();
			}
		}

		showTextArea(document, writer, baseFont,
				clientString, ",", 35, 765, 14);

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 9);
		// 表示位置の設定
		pdfContentByte.setTextMatrix(35, 645);
		// 表示する文字列の設定
		pdfContentByte.showText("毎度ありがとうございます。下記の通り御請求申し上げます。");

		// KTSのPDF出力

		//法人情報設定
		String corporationString = "";
		corporationString += "〒334-0013　埼玉県川口市南鳩ヶ谷1-25-3";
		corporationString += ",株式会社カインドテクノストラクチャー";
		corporationString += ",TEL：048-285-8941　FAX：048-285-8948";

		corporationString += ",三菱東京UFJ銀行　赤羽駅前支店";
		corporationString += ",普通口座　1672722";
		corporationString += ",カ）カインドテクノストラクチャー";

		// フォントとサイズの設定
		pdfContentByte.setFontAndSize(baseFont, 12);

		showTextArea(document, writer, baseFont,
				corporationString, ",", 290, 755, 15);

		//請求額テーブル
		PdfPTable pdfPTable = new PdfPTable(6);
		pdfPTable.setTotalWidth(400);
		int width[] = { 75, 75, 25, 75, 75, 75 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		PdfPCell lastClaimHeader = new PdfPCell(
				new Paragraph(String.valueOf(cal.get(Calendar.MONTH) + 1) + "月請求額", font));
		PdfPCell receiveHeader = new PdfPCell(new Paragraph("御入金額", font));
		PdfPCell blankHeader = new PdfPCell(new Paragraph("", font));
		PdfPCell balanceHeader = new PdfPCell(new Paragraph("繰越金額", font));
		PdfPCell purchaseHeader = new PdfPCell(new Paragraph("御買上額", font));
		PdfPCell currentClaimHeader = new PdfPCell(new Paragraph("今回御請求額", font));

		// 表の要素(列タイトル)を作成
		lastClaimHeader.setHorizontalAlignment(1);
		receiveHeader.setHorizontalAlignment(1);
		blankHeader.setHorizontalAlignment(1);
		balanceHeader.setHorizontalAlignment(1);
		purchaseHeader.setHorizontalAlignment(1);
		currentClaimHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(lastClaimHeader);
		pdfPTable.addCell(receiveHeader);
		pdfPTable.addCell(blankHeader);
		pdfPTable.addCell(balanceHeader);
		pdfPTable.addCell(purchaseHeader);
		pdfPTable.addCell(currentClaimHeader);

		PdfPCell lastClaim = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getPreMonthBillAmount())), font));
		PdfPCell receive = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getReceivePrice())
						.add(BigDecimal.valueOf(btobBillDTO.getCharge()))), font));
		PdfPCell blank = new PdfPCell(new Paragraph("", font));
		PdfPCell balance = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getCarryOverAmount())), font));
		PdfPCell purchase = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getSumClaimPrice())), font));
		PdfPCell currentClaim = new PdfPCell(
				new Paragraph(StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getBillAmount())), font));

		// 表の要素(列タイトル)を作成
		lastClaim.setHorizontalAlignment(2);
		receive.setHorizontalAlignment(2);
		blank.setHorizontalAlignment(2);
		balance.setHorizontalAlignment(2);
		purchase.setHorizontalAlignment(2);
		currentClaim.setHorizontalAlignment(2);

		//高さを設定
		lastClaim.setPaddingTop(12f);
		receive.setPaddingTop(12f);
		blank.setPaddingTop(12f);
		balance.setPaddingTop(12f);
		purchase.setPaddingTop(12f);
		currentClaim.setPaddingTop(12f);

		// 表の要素を表に追加する
		pdfPTable.addCell(lastClaim);
		pdfPTable.addCell(receive);
		pdfPTable.addCell(blank);
		pdfPTable.addCell(balance);
		pdfPTable.addCell(purchase);
		pdfPTable.addCell(currentClaim);

		//テーブル描画
		pdfPTable.writeSelectedRows(0, 6, 0, 2, 30, 640, writer.getDirectContent());

		// テキストの終了
		pdfContentByte.endText();

		// PdfGraphics2D のインスタンス化
		PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(pdfContentByte,
				document.getPageSize().getWidth(), document.getPageSize()
						.getHeight());
		pdfGraphics2D.setColor(new Color(0, 0, 0));
		pdfGraphics2D.dispose();

		//印鑑欄
		pdfGraphics2D.drawRect(450, pageHeight - 655, 50, 50);
		pdfGraphics2D.drawRect(500, pageHeight - 655, 50, 50);

	}

	/**
	 * 法人間請求書のPDFの商品部分を作成する。
	 * @param Document
	 * @param PdfWriter
	 * @param Font
	 * @param BaseFont
	 * @param BtobBillDTO 法人間請求書データ
	 * @param List<BtobBillItemDTO> 法人間請求書商品リスト
	 */
	private static void billItemList(Document document, PdfWriter writer, Font font,
			BaseFont baseFont, Date date, ExtendBtobBillDTO btobBillDTO, List<BtobBillItemDTO> billItemList,
			float orderCurrentHeight)
			throws IOException, ParseException, DocumentException {

		//法人間請求書商品テーブル

		int TABLE_COLS = 7;
		PdfPTable pdfPTable = new PdfPTable(TABLE_COLS);
		// pdfPTable.setTotalWidth(850);
		pdfPTable.setWidthPercentage(93);
		//表のwidth
		int width[] = { 80, 110, 455, 40, 85, 45, 90 };
		pdfPTable.setWidths(width);

		// 表の要素(列タイトル)を作成
		PdfPCell cellSalesDateHeader = new PdfPCell(new Paragraph("日付", font));
		PdfPCell cellItemCdHeader = new PdfPCell(new Paragraph("商品コード", font));
		PdfPCell cellItemNmHeader = new PdfPCell(new Paragraph("商品名", font));
		PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("数量", font));
		PdfPCell cellTaxRateHeader = new PdfPCell(new Paragraph("税率", font));
		PdfPCell cellUnitPriceHeader = new PdfPCell(new Paragraph("単価", font));
		PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("金額", font));

		// 表の要素(列タイトル)を揃え（アライン）を設定する
		cellSalesDateHeader.setHorizontalAlignment(1);
		cellItemCdHeader.setHorizontalAlignment(1);
		cellItemNmHeader.setHorizontalAlignment(1);
		cellTaxRateHeader.setHorizontalAlignment(1);
		cellUnitPriceHeader.setHorizontalAlignment(1);
		cellQuantityHeader.setHorizontalAlignment(1);
		cellPriceHeader.setHorizontalAlignment(1);

		// 表の要素を表に追加する
		pdfPTable.addCell(cellSalesDateHeader);
		pdfPTable.addCell(cellItemCdHeader);
		pdfPTable.addCell(cellItemNmHeader);
		pdfPTable.addCell(cellTaxRateHeader);
		pdfPTable.addCell(cellUnitPriceHeader);
		pdfPTable.addCell(cellQuantityHeader);
		pdfPTable.addCell(cellPriceHeader);

		pdfPTable.setHeaderRows(1);

		// ループ(商品LISTのDTOをループさせる)
		int rowNum = 0;
		String corpSalesFlag = "";
		String slipNo = "";
		int taxRate = 0;

		BigDecimal lowRateSlipPriceSum = BigDecimal.ZERO;
		int lowRateTax = 0;
		BigDecimal highRateSlipPriceSum = BigDecimal.ZERO;
		int highRateTax = 0;


		//空白セル定義
		PdfPCell blankCell = new PdfPCell(new Paragraph(" ", font));

		///XXX 伝票のデータ出力処理　start
		// 商品テーブル部の出力　（行ごとのループ）
		for (BtobBillItemDTO itemDTO : billItemList) {
			if(itemDTO.getSlipNo() == null) {
				continue;
			}

			// 売上か業販の伝票かを判定
			if (!corpSalesFlag.equals(itemDTO.getCorporateSalesFlg())) {

				if (!(rowNum == 0)) {
					// 表の要素(空行)を表に追加する
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					pdfPTable.addCell(blankCell);
					rowNum++;
				}

				PdfPCell cellItemNm = new PdfPCell(new Paragraph("【売上伝票】", font));

				if ("1".equals(itemDTO.getCorporateSalesFlg())) {

					cellItemNm = new PdfPCell(new Paragraph("【業販伝票】", font));

				}

				// 表の要素を表に追加する
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellItemNm);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				rowNum++;

				corpSalesFlag = itemDTO.getCorporateSalesFlg();

			}

			// 伝票番号の切り替わり
			if (!slipNo.equals(itemDTO.getSlipNo()) || rowNum == 1) {

				PdfPCell cellItemNm = new PdfPCell(new Paragraph("伝票番号 ： " + itemDTO.getSlipNo(), font));

				// 表の要素を表に追加する
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellItemNm);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				rowNum++;

				slipNo = itemDTO.getSlipNo();
			}

			PdfPCell cellSalesDate = new PdfPCell(new Paragraph(itemDTO.getSalesDate(), font));
			PdfPCell cellItemCd = new PdfPCell(new Paragraph(itemDTO.getItemCode(), font));

			taxRate = itemDTO.getTaxRate();
			PdfPCell cellTaxRate = new PdfPCell(new Paragraph(String.valueOf(taxRate + "％"), font));

			PdfPCell cellItemNm = new PdfPCell(new Paragraph(itemDTO.getItemNm(), font));
			PdfPCell cellUnitPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(itemDTO.getPieceRate())), font));
			PdfPCell cellQuantity = new PdfPCell(new Paragraph(
					String.valueOf(itemDTO.getOrderNum()), font));


			BigDecimal slipPriceSum = BigDecimal.valueOf(itemDTO.getPieceRate() * itemDTO.getOrderNum());
			PdfPCell cellPrice = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(slipPriceSum), font));

			//税計算と消費税合計
			//消費税率によって出力を変更するため格納する変数を分ける。
			if (taxRate == WebConst.TAX_RATE_8) {
				lowRateSlipPriceSum = lowRateSlipPriceSum.add(slipPriceSum);
			} else if (taxRate == WebConst.TAX_RATE_10) {
				highRateSlipPriceSum = highRateSlipPriceSum.add(slipPriceSum);
			}

			// 表の要素(列タイトル)を揃え（アライン）を設定する
			cellSalesDate.setHorizontalAlignment(1);
			cellUnitPrice.setHorizontalAlignment(2);

			cellTaxRate.setHorizontalAlignment(1);

			cellQuantity.setHorizontalAlignment(1);
			cellPrice.setHorizontalAlignment(2);

			// 表の要素を表に追加する
			pdfPTable.addCell(cellSalesDate);
			pdfPTable.addCell(cellItemCd);
			pdfPTable.addCell(cellItemNm);
			pdfPTable.addCell(cellTaxRate);
			pdfPTable.addCell(cellUnitPrice);
			pdfPTable.addCell(cellQuantity);
			pdfPTable.addCell(cellPrice);
		}

		//XXX　伝票の出力処理　end

		// フリーワード・手数料がない場合は非表示
		if (!StringUtils.isEmpty(btobBillDTO.getReceiveDate()) || !StringUtils.isEmpty(btobBillDTO.getFreeWord()) ||
				btobBillDTO.getReceivePrice() != 0 || btobBillDTO.getCharge() != 0) {

			// フリーワード・手数料エリアの表示

			//空白行
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			pdfPTable.addCell(blankCell);
			rowNum++;

			// フリーワードの設定
			PdfPCell cellSalesDateCArea = new PdfPCell(new Paragraph(btobBillDTO.getReceiveDate(), font));
			PdfPCell cellTaxRate = new PdfPCell(new Paragraph(btobBillDTO.getFreeWord(), font));
			PdfPCell cellItemNmCArea = new PdfPCell(new Paragraph(btobBillDTO.getFreeWord(), font));
			PdfPCell cellUnitPriceCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getReceivePrice())), font));
			PdfPCell cellPriceCArea = new PdfPCell(new Paragraph(
					StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getReceivePrice())), font));

			// 表の要素(列タイトル)を揃え（アライン）を設定する
			cellSalesDateCArea.setHorizontalAlignment(1);
			cellTaxRate.setHorizontalAlignment(2);
			cellUnitPriceCArea.setHorizontalAlignment(2);
			cellPriceCArea.setHorizontalAlignment(2);

			// 表の要素を表に追加する
			if (btobBillDTO.getReceiveDate() == null) {
				pdfPTable.addCell(blankCell);

			} else {
				pdfPTable.addCell(cellSalesDateCArea);
			}
			pdfPTable.addCell(blankCell);

			//振込金額表示
			if (btobBillDTO.getFreeWord() == null) {
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellItemNmCArea);
			}
			pdfPTable.addCell(blankCell);

			if (btobBillDTO.getReceivePrice() == 0) {
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
			} else {
				pdfPTable.addCell(cellUnitPriceCArea);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellPriceCArea);
			}
			rowNum++;

			// 手数料がある場合、出力する。
			if (btobBillDTO.getCharge() != 0) {
				// 手数料の設定
				cellItemNmCArea = new PdfPCell(new Paragraph("手数料", font));
				cellUnitPriceCArea = new PdfPCell(new Paragraph(
						StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getCharge())), font));
				cellPriceCArea = new PdfPCell(new Paragraph(
						StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getCharge())), font));

				// 表の要素(列タイトル)を揃え（アライン）を設定する
				cellUnitPriceCArea.setHorizontalAlignment(2);
				cellPriceCArea.setHorizontalAlignment(2);

				// 表の要素を表に追加する
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellItemNmCArea);
				pdfPTable.addCell(cellUnitPriceCArea);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(blankCell);
				pdfPTable.addCell(cellPriceCArea);
				rowNum++;
			}
		}

		// 税抜合計金額・消費税エリアの表示

		//空白行
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		rowNum++;

		//TODO　伝票番号から消費税率を取得する処理を追加して税率ごとに合計金額を算出する。

		//税抜き合計金額
		PdfPCell cellItemNm = new PdfPCell(new Paragraph("＜税抜合計金額＞　", font));
		PdfPCell sumPriceRateCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getSumPieceRate())), font));

		cellItemNm.setHorizontalAlignment(2);
		sumPriceRateCell.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(sumPriceRateCell);
		rowNum++;

		//消費税表示
		cellItemNm = new PdfPCell(new Paragraph("＜ 消 費 税 ＞　", font));
		PdfPCell taxCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getConsumptionTax())), font));

		cellItemNm.setHorizontalAlignment(2);
		taxCell.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(taxCell);
		rowNum++;

		//空白行
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		rowNum++;

		//税計算と消費税合計
		//消費税率によって出力を変更するため格納する変数を分ける。
		highRateTax = highRateSlipPriceSum.intValue() * WebConst.TAX_RATE_10 / 100;
		lowRateTax = lowRateSlipPriceSum.intValue() * WebConst.TAX_RATE_8 / 100;

		//10％対象表示
		cellItemNm = new PdfPCell(new Paragraph("＜10%対象(税抜)＞　 ", font));
		PdfPCell highRateSlipPriceSumCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(highRateSlipPriceSum), font));

		cellItemNm.setHorizontalAlignment(2);
		highRateSlipPriceSumCell.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(highRateSlipPriceSumCell);
		rowNum++;

		//10％対象消費税 表示
		cellItemNm = new PdfPCell(new Paragraph("＜10%対象消費税＞　 ", font));
		PdfPCell highRateTaxCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(highRateTax)), font));

		cellItemNm.setHorizontalAlignment(2);
		highRateTaxCell.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(highRateTaxCell);
		rowNum++;

		//8％対象表示
		cellItemNm = new PdfPCell(new Paragraph("＜ 8%対象(税抜)＞　 ", font));
		PdfPCell lowRateSlipPriceSumCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(lowRateSlipPriceSum), font));

		cellItemNm.setHorizontalAlignment(2);
		lowRateSlipPriceSumCell.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(lowRateSlipPriceSumCell);
		rowNum++;

		//8％対象消費税表示
		cellItemNm = new PdfPCell(new Paragraph("＜ 8%対象消費税＞　 ", font));
		PdfPCell lowRateTaxCell = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(lowRateTax)), font));

		cellItemNm.setHorizontalAlignment(2);
		lowRateTaxCell.setHorizontalAlignment(2);

		// 表の要素を表に追加する
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(cellItemNm);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(lowRateTaxCell);
		rowNum++;

		//空白行
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		pdfPTable.addCell(blankCell);
		rowNum++;

		//最下段行
		PdfPCell blankSpanCell = new PdfPCell(new Paragraph(" ", font));
		blankSpanCell.setColspan(5);

		//合計表示
		PdfPCell cellSum = new PdfPCell(new Paragraph("合計 ", font));
		PdfPCell cellSumClaimPrice = new PdfPCell(new Paragraph(
				StringUtil.formatCalc(BigDecimal.valueOf(btobBillDTO.getSumClaimPrice())), font));

		cellItemNm.setHorizontalAlignment(2);
		cellSumClaimPrice.setHorizontalAlignment(2);

		blankSpanCell.setPaddingTop(7f);
		cellSum.setPaddingTop(7f);
		cellSum.setPadding(3f);
		cellSumClaimPrice.setPaddingTop(7f);
		cellSumClaimPrice.setPadding(3f);

		cellSum.setHorizontalAlignment(1);
		cellSumClaimPrice.setHorizontalAlignment(2);

		pdfPTable.addCell(blankSpanCell);
		pdfPTable.addCell(cellSum);
		pdfPTable.addCell(cellSumClaimPrice);
		rowNum += 2;

		//テーブル描画
		pdfPTable.setSplitLate(true);
		document.setMargins(5, 5, 40, 5);
		document.add(pdfPTable);
	}

	/**
	 * PDFの複数行の文字列出力
	 * @param Document
	 * @param PdfWriter
	 * @param BaseFont
	 * @param String 出力文字列
	 * @param String 区切り文字（改行位置）
	 * @param float PDF出力X座標
	 * @param float PDF出力Y座標（開始位置）
	 * @param float PDF出力Y座標（改行のマージン）
	 */
	public static void showTextArea(Document document, PdfWriter writer,
			BaseFont baseFont, String texts, String splWord, float posX, float posY, float posYInc) {

		PdfContentByte pdfContentByte = writer.getDirectContent();

		String[] textAry = texts.split(splWord);

		for (String text : textAry) {
			// 表示位置の設定
			pdfContentByte.setTextMatrix(posX, posY);

			// 表示する文字列の設定
			pdfContentByte.showText(text);

			posY -= posYInc;
		}
	}

	/**
	 * 指定された法人の該当月での請求書情報がDBに存在するか確認
	 * 存在する場合、エラーとする。
	 * @param Kind原価詳細情報
	 *
	 * @return エラー情報
	 */
	public ErrorDTO checkBtobBill(String exportMonth, long exportSysCorporationId)
			throws ParseException, NumberFormatException, DaoException {
		ErrorDTO errDTO = new ErrorDTO();
		BtobBillDAO btobBillDAO = new BtobBillDAO();

		ExtendBtobBillDTO dto = btobBillDAO.getSearchDemandMonthBtobBill(exportSysCorporationId, exportMonth);

		if (dto != null) {
			errDTO.setSuccess(false);
			errDTO.setErrorMessage("該当の売上月の請求書が存在します。削除してから、再度作成してください。");
		}

		return errDTO;

	}

	ErrorDTO errDTO = new ErrorDTO();
	SaleDAO saleDAO = new SaleDAO();

	/**
	 * 指定された法人の月単位での売上・業販情報を集計し、請求書情報をDBに登録
	 * @param Kind原価詳細情報
	 *
	 * @return エラー情報
	 */
	public ErrorDTO createBtobBill(String exportMonth, long exportSysCorporationId)
			throws ParseException, NumberFormatException, DaoException {

		ErrorDTO errDTO = new ErrorDTO();
		SaleDAO saleDAO = new SaleDAO();
		CorporateSaleDAO corpSaleDAO = new CorporateSaleDAO();

		List<ErrorMessageDTO> errorMessageList = new ArrayList<ErrorMessageDTO>();
		List<BtobBillItemDTO> billItemList = new ArrayList<BtobBillItemDTO>();

		// 売上伝票
		// 指定された法人の月単位で売上伝票から請求書商品情報を取得する
		List<ExtendSalesItemDTO> saleItemList = new ArrayList<ExtendSalesItemDTO>();

		// KTSの場合
		if (exportSysCorporationId == 1) {
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 1)); // KTSの売り上げデータ
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 3)); // T-FOURの売り上げデータ
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 7)); // KTS法人営業部の売り上げデータ
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 10)); // KTS掛業販の売り上げデータ
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 11)); // KTS海外事業部の売り上げデータ
		} else if (exportSysCorporationId == 2) {
			// 車楽院の場合
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 2)); // 車楽院の売り上げデータ
		} else if (exportSysCorporationId == 4) {
			//ラルグスリテールの場合
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 4)); // ラルグスリテールの売り上げデータ
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 8)); // Largusretail業販の売り上げデータ
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 9)); // ラルグス車高調専門店の売り上げデータ
		} else if (exportSysCorporationId == 5) {
			//株式会社BCRの場合
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 5)); // 株式会社BCRの売り上げデータ
		} else if (exportSysCorporationId == 20) {
			//サイバーエコの場合
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 6)); // サイバーエコの売り上げデータ
			//株式会社ラルグス　ウルトラレーシング事業部の場合
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 13)); //株式会社ラルグス　ウルトラレーシング事業部の売り上げデータ
		} else if (exportSysCorporationId == 12) {
			//Brembo事業部の場合
			saleItemList.addAll(saleDAO.getSaleCostBtobBill(exportMonth, 12)); // Brembo事業部の売り上げデータ
		}

		// 同様に、業販伝票
		// 指定された法人の月単位で業販伝票から請求書商品情報を取得する
		List<ExtendCorporateSalesItemDTO> corpSaleItemList = new ArrayList<ExtendCorporateSalesItemDTO>();
		// KTSの場合
		if (exportSysCorporationId == 1) {
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 1)); // KTSの売り上げデータ
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 3)); // T-FOURの売り上げデータ
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 7)); // KTS法人営業部の売り上げデータ
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 10)); // KTS掛業販の売り上げデータ
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 11)); // KTS海外事業部の売り上げデータ
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 12)); // Brembo事業部の売り上げデータ
		} else if (exportSysCorporationId == 2) {
			// 車楽院の場合
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 2)); // 車楽院の売り上げデータ
		} else if (exportSysCorporationId == 4) {
			//ラルグスリテールの場合
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 4)); // ラルグスリテールの売り上げデータ
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 8)); // Largusretail業販の売り上げデータ
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 9)); // ラルグス車高調専門店の売り上げデータ
		} else if (exportSysCorporationId == 5) {
			//株式会社BCRの場合
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 5)); // 株式会社BCRの売り上げデータ
		} else if (exportSysCorporationId == 20) {
			//サイバーエコの場合
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 6)); // サイバーエコの売り上げデータ
			//株式会社ラルグス　ウルトラレーシング事業部の場合
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 13)); // 株式会社ラルグス　ウルトラレーシング事業部の売り上げデータ
		} else if (exportSysCorporationId == 12) {
			//Brembo事業部の場合
			corpSaleItemList.addAll(corpSaleDAO.getCorpSaleCostBtobBill(exportMonth, 12)); // Brembo事業部の売り上げデータ
		}

		// 請求書情報が1件もない場合
		if (saleItemList.size() == 0 && corpSaleItemList.size() == 0) {
			// 原価が未設定の場合、エラーメッセージを出力し、処理終了
			ErrorMessageDTO errMessDTO = new ErrorMessageDTO();
			errMessDTO.setErrorMessage("請求対象となる伝票がありません");
			errMessDTO.setSuccess(false);
			errorMessageList.add(errMessDTO);
			errDTO.setErrorMessageList(errorMessageList);
			errDTO.setSuccess(false);
			return errDTO;
		}

		// システム法人間請求書IDを取得する
		SequenceDAO seqDAO = new SequenceDAO();
		long sysBtobBillId = seqDAO.getMaxSysBtobBillId() + 1;

		// 売上伝票の情報を設定する
		for (ExtendSalesItemDTO saleItemDTO : saleItemList) {
			BtobBillItemDTO itemDTO = new BtobBillItemDTO();

			if (StringUtils.isEmpty(saleItemDTO.getCostCheckFlag()) || "0".equals(saleItemDTO.getCostCheckFlag())) {
				// 未確認の場合、エラーメッセージを出力
				ErrorMessageDTO errMessDTO = new ErrorMessageDTO();
				errMessDTO.setErrorMessage("伝票番号:" + saleItemDTO.getOrderNo() + "品番:" + saleItemDTO.getItemCode()
						+ "の売上商品は原価の確認がされていません。");
				errMessDTO.setSuccess(false);
				errorMessageList.add(errMessDTO);
				continue;
			}
			// システム法人間請求書ID
			itemDTO.setSysBtobBillId(sysBtobBillId);
			// 業販区分
			itemDTO.setCorporateSalesFlg("0");
			// 売上日
			itemDTO.setSalesDate(saleItemDTO.getShipmentPlanDate());
			// 伝票番号
			itemDTO.setSlipNo(saleItemDTO.getOrderNo());
			// システム商品ID
			itemDTO.setSysItemId(saleItemDTO.getSysItemId());
			// 品番
			itemDTO.setItemCode(saleItemDTO.getItemCode());
			// 商品名
			itemDTO.setItemNm(saleItemDTO.getItemNm());
			// 注文数 通常伝票の場合
			if ("0".equals(saleItemDTO.getReturnFlg())) {
				itemDTO.setOrderNum(saleItemDTO.getOrderNum());
			} else {
				//返品伝票の場合 マイナスの値を設定
				if (saleItemDTO.getOrderNum() > 0) {
					itemDTO.setOrderNum(saleItemDTO.getOrderNum() * -1);
				} else {
					itemDTO.setOrderNum(saleItemDTO.getOrderNum());
				}
			}

			// 単価
			if (saleItemDTO.getCost() == 0) {
				//原価が未設定の場合、請求書に出力しない
				continue;
			} else {
				if(saleItemDTO.getUpdatedFlag() == 1) {
					itemDTO.setPieceRate(saleItemDTO.getCost()+saleItemDTO.getPostage()/Math.abs(saleItemDTO.getOrderNum()));
				}else {
					itemDTO.setPieceRate(saleItemDTO.getCost()+saleItemDTO.getDomePostage()/Math.abs(saleItemDTO.getOrderNum()));
				}
			}

			// 税率
			itemDTO.setTaxRate(saleItemDTO.getTaxRate());

			// リストに追加
			billItemList.add(itemDTO);

			if(saleItemDTO.getUpdatedFlag() == 1) {
			}else {
				saleItemDTO.setUpdatedFlag(1);
				saleItemDTO.setPostage(saleItemDTO.getDomePostage());
				saleDAO.updateSalesItem(saleItemDTO);
			}

		}

		// 業販伝票の情報を設定する
		for (ExtendCorporateSalesItemDTO corpSaleItemDTO : corpSaleItemList) {
			BtobBillItemDTO itemDTO = new BtobBillItemDTO();

			if (StringUtils.isEmpty(corpSaleItemDTO.getCostCheckFlag())
					|| "0".equals(corpSaleItemDTO.getCostCheckFlag())) {
				// 未確認の場合、エラーメッセージを出力
				ErrorMessageDTO errMessDTO = new ErrorMessageDTO();
				errMessDTO.setErrorMessage("伝票番号:" + corpSaleItemDTO.getOrderNo() + "品番:"
						+ corpSaleItemDTO.getItemCode() + "の業販商品は原価の確認がされていません。");
				errMessDTO.setSuccess(false);
				errorMessageList.add(errMessDTO);
				continue;
			}

			// システム法人間請求書ID
			itemDTO.setSysBtobBillId(sysBtobBillId);
			// 業販区分
			itemDTO.setCorporateSalesFlg("1");
			// 売上日
			itemDTO.setSalesDate(corpSaleItemDTO.getScheduledLeavingDate());
			// 伝票番号
			itemDTO.setSlipNo(corpSaleItemDTO.getOrderNo());
			// システム商品ID
			itemDTO.setSysItemId(corpSaleItemDTO.getSysItemId());
			// 品番
			itemDTO.setItemCode(corpSaleItemDTO.getItemCode());
			// 商品名
			itemDTO.setItemNm(corpSaleItemDTO.getItemNm());
			// 注文数 通常伝票の場合
			if ("0".equals(corpSaleItemDTO.getReturnFlg())) {
				itemDTO.setOrderNum(corpSaleItemDTO.getOrderNum());
			} else {
				//返品伝票の場合 マイナスの値を設定
				if (corpSaleItemDTO.getOrderNum() > 0) {
					itemDTO.setOrderNum(corpSaleItemDTO.getOrderNum() * -1);
				} else {
					itemDTO.setOrderNum(corpSaleItemDTO.getOrderNum());
				}
			}
			// 単価
			if (corpSaleItemDTO.getCost() == 0) {
				//原価が未設定の場合、請求書に出力しない
				continue;
			} else {
				if(corpSaleItemDTO.getUpdatedFlag() == 1) {
					itemDTO.setPieceRate(corpSaleItemDTO.getCost()+corpSaleItemDTO.getPostage()/Math.abs(corpSaleItemDTO.getOrderNum()));
				}else {
					itemDTO.setPieceRate(corpSaleItemDTO.getCost()+corpSaleItemDTO.getDomePostage()/Math.abs(corpSaleItemDTO.getOrderNum()));
				}
			}

			// 税率
			itemDTO.setTaxRate(corpSaleItemDTO.getTaxRate());

			// リストに追加
			billItemList.add(itemDTO);

			if(corpSaleItemDTO.getUpdatedFlag() == 1) {
			}else {
				corpSaleItemDTO.setPostage(corpSaleItemDTO.getDomePostage());
				corpSaleItemDTO.setUpdatedFlag(1);
				corpSaleDAO.updateCorporateSalesItem(corpSaleItemDTO);
			}
		}

		// リストが0件の場合、エラー
		if (billItemList.size() == 0) {
			ErrorMessageDTO errMessDTO = new ErrorMessageDTO();
			errMessDTO.setErrorMessage("請求対象となる伝票がありません");
			errMessDTO.setSuccess(false);
			errorMessageList.add(errMessDTO);
		}

		// エラーが発生している場合は、処理を終了
		if (errorMessageList.size() > 0) {
			errDTO.setErrorMessageList(errorMessageList);
			errDTO.setSuccess(false);
			return errDTO;
		}

		BtobBillDAO btobBillDAO = new BtobBillDAO();

		// 法人間請求書商品テーブルに登録
		for (BtobBillItemDTO itemDTO : billItemList) {

			// システム法人間請求書商品ID
			itemDTO.setSysBtobBillItemId(seqDAO.getMaxSysBtobBillItemId() + 1);

			// 法人間請求書商品テーブルに登録
			btobBillDAO.registryBtobBillItem(itemDTO);

		}

		// 法人情報取得
		CorporationService corporationService = new CorporationService();
		MstCorporationDTO corpDTO = corporationService.getCorporation(exportSysCorporationId);

		// 法人間請求書情報を設定
		BtobBillDTO btobBillDTO = new BtobBillDTO();

		// システム法人間請求書ID
		btobBillDTO.setSysBtobBillId(sysBtobBillId);
		// システム法人ID
		btobBillDTO.setSysCorporationId(exportSysCorporationId);
		// 請求月
		btobBillDTO.setDemandMonth(exportMonth);

		// 請求月内番号
		String MaxNo = btobBillDAO.getSearchMaxDemandMonthNo(exportSysCorporationId, exportMonth);

		if (StringUtils.isEmpty(MaxNo)) {
			btobBillDTO.setDemandMonthNo("001");
		} else {
			int iMaxNo = Integer.parseInt(MaxNo) + 1;
			btobBillDTO.setDemandMonthNo(String.format("%03d", iMaxNo));
		}

		// 請求書番号
		btobBillDTO.setBtobBillNo(WebConst.SYS_CORPORATION_CODE_MAP.get(String.valueOf(exportSysCorporationId))
				+ StringUtil.slashDelete(exportMonth) + btobBillDTO.getDemandMonthNo());
		// 請求データ作成日
		btobBillDTO.setBilldataCreateDate(StringUtil.getToday());
		// 取引先法人名
		if (exportSysCorporationId == 1 || exportSysCorporationId == 2 || exportSysCorporationId == 4) {
			corpDTO.setCorporationNm("株式会社" + corpDTO.getCorporationNm());
		} else if (exportSysCorporationId == 20) {
			corpDTO = new MstCorporationDTO();
			corpDTO.setCorporationNm("株式会社日本中央貿易");
		} else if (exportSysCorporationId == 12) {
			corpDTO.setCorporationNm("株式会社カインドテクノストラクチャー　Brembo事業部");
		}
		btobBillDTO.setClientCorporationNm(corpDTO.getCorporationNm());
		if (exportSysCorporationId == 20) {
			// 取引先電話番号
			btobBillDTO.setClientTelNo("");
			// 取引先FAX番号
			btobBillDTO.setClientFaxNo("");
		} else {
			// 取引先電話番号
			btobBillDTO.setClientTelNo(corpDTO.getTelNo());
			// 取引先FAX番号
			btobBillDTO.setClientFaxNo(corpDTO.getFaxNo());
		}

		// 前月請求金額
		// 請求月の前月を算出
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
		SimpleDateFormat formatPreMonth = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		cal.setTime(format.parse(exportMonth + "/01"));
		cal.add(Calendar.MONTH, -1);
		// 前月の法人間請求書を取得
		ExtendBtobBillDTO preMonthDTO = btobBillDAO.getSearchDemandMonthBtobBill(exportSysCorporationId,
				formatPreMonth.format(cal.getTime()));
		if (preMonthDTO == null) {
			btobBillDTO.setPreMonthBillAmount(0);
		} else {
			btobBillDTO.setPreMonthBillAmount(preMonthDTO.getBillAmount());
		}

		// フリーワード
		btobBillDTO.setFreeWord("振込金額");
		// 商品単価小計
		btobBillDTO.setSumPieceRate(btobBillDAO.getSearchCalcSumPriceRate(sysBtobBillId));
		// 消費税
		btobBillDTO.setConsumptionTax(getTax(sysBtobBillId));
		// 合計請求金額
		btobBillDTO.setSumClaimPrice(btobBillDTO.getSumPieceRate() + btobBillDTO.getConsumptionTax());
		// 繰越金額
		btobBillDTO.setCarryOverAmount(btobBillDTO.getPreMonthBillAmount());
		// 請求金額
		btobBillDTO.setBillAmount(btobBillDTO.getSumClaimPrice() + btobBillDTO.getCarryOverAmount());

		// 法人間請求書テーブルに登録
		btobBillDAO.registryBtobBill(btobBillDTO);

		errDTO.setSuccess(true);
		return errDTO;
	}

	/**
	 * 請求書の検索処理
	 * @param exportMonth
	 * @param exportSysCorporationId
	 * @return List<ExtendBtobBillDTO>
	 * @throws DaoException
	 */
	public List<BtobBillDTO> searchBtobBillIdList(String exportMonth, long exportSysCorporationId) throws DaoException {

		List<BtobBillDTO> btobBillDTOList = new ArrayList<>();
		BtobBillDAO dao = new BtobBillDAO();

		//対象の請求書を取得する
		btobBillDTOList = dao.getSearchBtobBillList(exportMonth, exportSysCorporationId);

		return btobBillDTOList;
	}

	protected int getTax(long sumPieceRate, String taxClass, String orderDate) throws ParseException {

		if (sumPieceRate == 0) {
			return 0;
		}

		Date taxUpDate8 = new Date();
		Date taxUpDate10 = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		taxUpDate8 = dateFormat.parse(WebConst.TAX_UP_DATE_8);
		taxUpDate10 = dateFormat.parse(WebConst.TAX_UP_DATE_10);

		int taxRate = 0;
		if (DateUtil.parseYYYYMMDDDate(orderDate).compareTo(taxUpDate8) < 0) {
			taxRate = WebConst.TAX_RATE_5;
		} else if (DateUtil.parseYYYYMMDDDate(orderDate).compareTo(taxUpDate10) < 0) {
			taxRate = WebConst.TAX_RATE_8;
		} else {
			taxRate = WebConst.TAX_RATE_10;
		}

		//saleDetail.jspのtaxCalcにも同じロジックがあり
		//ここの税計算ロジックを変更した場合、同じ仕様に変更する必要あり
		long tax = 0;
		//内税の場合
		if (StringUtils.equals(WebConst.TAX_CODE_INCLUDE, taxClass)) {

			tax = sumPieceRate - (int) Math.floor((sumPieceRate / (double) (100 + taxRate)) * 100);

			//外税の場合
		} else if (StringUtils.equals(WebConst.TAX_CODE_EXCLUSIVE, taxClass)) {

			tax = (int) Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
		}

		return (int) tax;
	}

	protected int getTax(long sysBtobBillId) throws ParseException, DaoException {

		BtobBillDAO btobBillDAO = new BtobBillDAO();

		long highSumPieceRate = btobBillDAO.getSearchCalcSumPriceRate(sysBtobBillId, WebConst.TAX_RATE_10);
		long highTax = (int) Math.floor((highSumPieceRate * (100 + WebConst.TAX_RATE_10)) / 100) - highSumPieceRate;

		long lowSumPieceRate = btobBillDAO.getSearchCalcSumPriceRate(sysBtobBillId, WebConst.TAX_RATE_8);
		long lowTax = (int) Math.floor((lowSumPieceRate * (100 + WebConst.TAX_RATE_8)) / 100) - lowSumPieceRate;

		long tax = highTax + lowTax;

		return (int) tax;
	}

	/**
	 * 伝票一覧から商品価格合計や粗利の各合計を取得します(Java)<br />
	 * 表示フラグによって計算するか否かを判定します<br />
	 *
	 * @param sysCorporateSalesSlipIdList
	 * @param grossMarginFlg
	 * @param sumDispFlg 表示フラグ
	 * @return
	 * @throws DaoException
	 */
	public CorporateSaleListTotalDTO getCorporateSaleListTotalDTO(
			List<ExtendBtobBillDTO> btobBillList, String grossMarginFlg, String sumDispFlg)
			throws DaoException {

		CorporateSaleListTotalDTO corporatesaleListTotalDTO = new CorporateSaleListTotalDTO();
		// Backlog No. BONCRE-488 対応 (サーバ側)(Backlog 存在せず)
		// 非表示の場合
		if (StringUtils.equals(WebConst.SUM_DISP_FLG1, sumDispFlg)) {
			return corporatesaleListTotalDTO;
		}

		for (ExtendBtobBillDTO dto : btobBillList) {

			long price = 0;
			if (StringUtils.equals(grossMarginFlg,
					WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE)) {

				//税抜き合計金額
				price = dto.getSumPieceRate();

			} else if (StringUtils.equals(grossMarginFlg,
					WebConst.GROSS_PROFIT_CALC_TOTAL_CODE)) {

				//税込み合計金額
				price = dto.getSumClaimPrice();
			}

			long[] arrPrice = getCostGrossMargin(
					dto.getSysBtobBillId(),
					price);

			// 合計請求金額を計算する
			corporatesaleListTotalDTO.setSumSumClaimPrice(
					corporatesaleListTotalDTO.getSumSumClaimPrice() + dto.getSumClaimPrice() - dto.getReceivePrice()
							- dto.getCharge());

			// 税込商品単価の合計を計算する
			corporatesaleListTotalDTO.setInTaxSumPieceRate(
					corporatesaleListTotalDTO.getInTaxSumPieceRate() + dto.getSumPieceRate() + dto.getConsumptionTax());

			// 税抜き商品単価の合計を計算する
			corporatesaleListTotalDTO.setNoTaxSumPieceRate(
					corporatesaleListTotalDTO.getNoTaxSumPieceRate() + dto.getSumPieceRate());

			// 原価の合計を計算する
			corporatesaleListTotalDTO.setSumCost(
					corporatesaleListTotalDTO.getSumCost() + arrPrice[0]);

			// 粗利の合計を計算する
			corporatesaleListTotalDTO.setSumGrossMargin(
					corporatesaleListTotalDTO.getSumGrossMargin() + arrPrice[1]);
		}

		return corporatesaleListTotalDTO;
	}

	/**
	 * 原価と粗利を取得します<br />
	 * 売上伝票ID・会社IDから原価を取得するb<br />
	 *
	 * @param sysSalesSlipId 売上伝票ID
	 * @param sysCorporationId 会社ID
	 * @param price 売値
	 * @return
	 * [0]:kind原価<br />
	 * [1]:粗利(売値-kind原価)<br />
	 * @throws DaoException
	 */
	public long[] getCostGrossMargin(
			long sysCorporationId,
			long price) throws DaoException {

		long[] arrPrice = new long[2];
		long cost = 0;
		long grossMargin = 0;

		cost = getSumCost(
				//				sysBtobBillId,
				sysCorporationId);
		grossMargin = price - cost;

		arrPrice[0] = cost;
		arrPrice[1] = grossMargin;
		return arrPrice;
	}

	/**
	 * 伝票ひとつ分の原価（コスト）合計値を返却します
	 *
	 * @param sysSalesSlipId
	 * @param sysCorporationId
	 *            原価がitem_costTBにある場合に必要
	 * @return
	 * @throws DaoException
	 */
	private long getSumCost(
			long sysBtobBillId)
			throws DaoException {

		ExtendBtobBillDTO searchDTO = new ExtendBtobBillDTO();
		searchDTO.setSysBtobBillId(sysBtobBillId);

		//業販kind原価取得
		List<ExtendCorporateSalesItemDTO> corpList = new BtobBillDAO().getCorporateSalesKindList(searchDTO);
		//売上kind原価取得
		List<ExtendCorporateSalesItemDTO> salesList = new BtobBillDAO().getSalesKindItemList(searchDTO);

		// 合計用
		//		long sumCost = 0;
		long sumKindCost = 0;

		if (corpList != null && corpList.size() != 0) {
			//業販
			for (ExtendCorporateSalesItemDTO dto : corpList) {

				if(dto.getUpdatedFlag() == 1) {
					sumKindCost += (dto.getKindCost() * dto.getOrderNum() + dto.getPostage());
				}else {
					sumKindCost += (dto.getKindCost() * dto.getOrderNum() + dto.getDomePostage());
				}
				//				sumCost += (dto.getCost() * dto.getOrderNum());
			}
		}

		if (salesList != null && salesList.size() != 0) {
			//売上
			for (ExtendCorporateSalesItemDTO dto : salesList) {
				if(dto.getUpdatedFlag() == 1) {
					sumKindCost += (dto.getKindCost() * dto.getOrderNum() + dto.getPostage());
				}else {
					sumKindCost += (dto.getKindCost() * dto.getOrderNum() + dto.getDomePostage());
				}
				//			sumCost += (dto.getCost() * dto.getOrderNum());
			}
		}

		return sumKindCost;
	}
}
