package jp.co.kts.service.fileExport;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.ForeignSlipSearchDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticManageDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ItemCostPriceDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.app.search.entity.DomesticOrderListSearchDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.mst.ForeignOrderDAO;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.DomesticOrderListService;
import jp.co.kts.ui.mst.ForeignOrderService;
import jp.co.kts.ui.web.struts.WebConst;
/**
 * 商品のエクセルを出力するクラス
 * @author aito
 *
 */
public class ExportItemListService extends FileExportExcelService implements AutoCloseable{

	{
		/**
		 * 始まりの行
		 */
		rowIdx = 1;

		/**
		 * 始まりの列
		 */
		colIdx = 0;
	}


	static final int LIST_INDEX0 = 0;
	static final int UN_REGIST = 0;

	//原価確認フラグON
	static final String COST_COMF_FLAG_ON = "1";
	//原価確認フラグOFF
	static final String COST_COMF_FLAG_OFF = "0";
	//原価確認フラグON:済
	static final String COST_COMF_FLAG_ON_VAL = "済";
	//原価確認フラグOFF：未
	static final String COST_COMF_FLAG_OFF_VAL = "未";

	//対応：出荷
	static final String CORRESPONDENCE_VAL1 = "出荷";
	//対応：移動
	static final String CORRESPONDENCE_VAL2 = "移動";

	//処理状態：未
	static final String PROCESSING_VAL1 = "未";
	//処理状態：済
	static final String PROCESSING_VAL2 = "済";

	/**
	 * 商品の在庫表をエクセル出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public XSSFWorkbook getExportItemList(SearchItemDTO searchItemDTO, XSSFWorkbook workBook) throws DaoException {

		XSSFSheet sheet = workBook.getSheetAt(0);
		// シート名の設定.
		//使うかわからないけどコピーしました
		workBook.setSheetName(0, "在庫表");

		ItemDAO itemDAO = new ItemDAO();
		List<ResultItemSearchDTO> mstItemList = itemDAO.getExportItemSearchList(searchItemDTO);

		//値の始まりの行
		rowIdx = 1;
		for (ResultItemSearchDTO dto: mstItemList) {

			colIdx = 0;

			//行の設定
			xrow = sheet.getRow(rowIdx);
			if (xrow == null) {
				xrow = sheet.createRow(rowIdx);
			}
			xrow.setHeightInPoints(16.5F);
			//新品番
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getItemCode()));

			//旧品番
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getOldItemCode()));

			//商品名
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getItemNm()));

			//総在庫数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getTotalStockNum());

			//仮在庫数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getTemporaryStockNum());

			//キープ数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getTotalStockNum() - dto.getTemporaryStockNum());

			//発注アラート
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getOrderAlertNum());

			//(第一倉庫)倉庫名
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getWarehouseNm()));

			//(第一倉庫)在庫数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getStockNum());

			//(第一倉庫)ロケーションNO
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getLocationNo()));

			//仕様メモ
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getSpecMemo()));

			xrow = null;
			rowIdx++;
		}

		return workBook;
	}

	/**
	 * 在庫一覧：商品情報のエクセル出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public XSSFWorkbook getExportItemInfoList(SearchItemDTO searchItemDTO, XSSFWorkbook workBook, String authInfo, int sheetNo) throws DaoException {

		XSSFSheet sheet = workBook.getSheetAt(sheetNo);
		// 埋め込み計算式を動作させるための記述
		//使うかわからないけどコピーしました
//		sheet.setForceFormulaRecalculation(true);

		// シート名の設定.
		if (authInfo.equals(WebConst.AUTH_INFO_OK)) {
			workBook.setSheetName(sheetNo, "商品情報_権限有");
		} else {
			workBook.setSheetName(sheetNo, "商品情報");
		}

		ItemDAO itemDAO = new ItemDAO();
		List<ResultItemSearchDTO> mstItemList = itemDAO.getExportItemSearchList(searchItemDTO);
//		ExtendMstItemDTO mstItemDTO = new ExtendMstItemDTO();

		//メモrowStartIdx = 0

		//値の始まりの行
		rowIdx = 2;
		for (ResultItemSearchDTO dto: mstItemList) {

			colIdx = 0;

			//行の設定
			xrow = sheet.getRow(rowIdx);
			if (xrow == null) {
				xrow = sheet.createRow(rowIdx);
			}
			xrow.setHeightInPoints(16.5F);

			//品番
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getItemCode()));
			//海外閲覧権限が有るユーザーのみ見れる
			if (authInfo.equals(WebConst.AUTH_INFO_OK)) {
				//工場品番
				xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getFactoryItemCode()));
			}
			//旧品番
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getOldItemCode()));
			//海外閲覧権限が有るユーザーのみ見れる
			if (authInfo.equals(WebConst.AUTH_INFO_OK)) {
				//仕入国(名)
				xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getCountry()));
				//仕入金額
				//そのまま入れると、エクセルで少数点以下のずれが生じるためdouble型に計算してから出力
				//しようとしたけどString型に変換でうまくいくのでそちらでいく。不具合があった場合はdouble型で
//				double purchaceCost = Math.round(dto.getPurchaceCost() * 100);
//				callCreateCell(row, colIdx++).setCellValue(purchaceCost / 100);
				xcallCreateCell(xrow, colIdx++).setCellValue(String.valueOf(dto.getPurchaceCost()));
				//通貨
				xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getCurrencyNm()));
			}
			//商品名
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getItemNm()));
			//海外閲覧権限が有るユーザーのみ見れる
			if (authInfo.equals(WebConst.AUTH_INFO_OK)) {
				//海外商品名
				xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getForeignItemNm()));
				//仕入先ID
				if (dto.getSysSupplierId() == 0) {
					xcallCreateCell(xrow, colIdx++).setCellValue("");
				} else {
					xcallCreateCell(xrow, colIdx++).setCellValue(dto.getSysSupplierId());
				}

			}
			//車種
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getCarModel()));
			//型式
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getModel()));
			//メーカー
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getMakerNm()));
			//仕様メモ
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getSpecMemo()));
			//在庫アラート数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getOrderAlertNum());
			//商品リードタイム
			String leadTime = WebConst.LEAD_TIME_MAP.get(dto.getItemLeadTime());
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(leadTime));
			//梱包サイズ
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getPackingSize()));
			//重量
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getWeight());
			//総在庫数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getTotalStockNum());
			//仮在庫数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getTemporaryStockNum());
			//最少ロット数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getMinimumOrderQuantity());
			//海外閲覧権限が有るユーザーのみ見れる
			if (authInfo.equals(WebConst.AUTH_INFO_OK)) {
				//発注数
				xcallCreateCell(xrow, colIdx++).setCellValue(dto.getSumNotInStock());
			}

			xrow = null;
			rowIdx++;
		}

		return workBook;
	}

	/**
	 * 在庫一覧：在庫情報のエクセル出力します
	 *
	 * @param itemList
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public XSSFWorkbook getExportStockItemInfoList(List<SysItemIdDTO> itemIdList, XSSFWorkbook workBook, int sheetNo) throws DaoException {



		XSSFSheet sheet = workBook.getSheetAt(sheetNo);
		// 埋め込み計算式を動作させるための記述

		// シート名の設定.
		workBook.setSheetName(sheetNo, "在庫情報");
		// インスタンス生成
		ItemDAO itemDAO = new ItemDAO();
		//倉庫情報の取得
		List<ExtendWarehouseStockDTO> stockInfoList = itemDAO.getExportStockItemInfoList(itemIdList);

		//値の始まりの行
		rowIdx = 2;
		for (ExtendWarehouseStockDTO dto: stockInfoList) {

			colIdx = 0;

			//行の設定
			xrow = sheet.getRow(rowIdx);
			if (xrow == null) {
				xrow = sheet.createRow(rowIdx);
			}
			xrow.setHeightInPoints(16.5F);

			//品番
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getItemCode()));

			//倉庫名
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getSysWarehouseId());

			//在庫数
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getStockNum());

			//ロケーションNO
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getLocationNo()));

			row = null;
			rowIdx++;
		}

		return workBook;
	}

	/**
	 * 在庫一覧：価格情報のエクセル出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public XSSFWorkbook getExportPriceInfoList(List<SysItemIdDTO> itemIdList, XSSFWorkbook workBook, String authInfo, int sheetNo) throws DaoException {


		XSSFSheet sheet = workBook.getSheetAt(sheetNo);
		// シート名の設定.
		if (authInfo.equals(WebConst.AUTH_INFO_OK)) {
			workBook.setSheetName(sheetNo, "価格情報_権限有");
		} else {
			workBook.setSheetName(sheetNo, "価格情報");
		}
		ItemDAO itemDAO = new ItemDAO();

		ItemService service = new ItemService();

		List<ItemCostPriceDTO> itemCostPriceList = new ArrayList<ItemCostPriceDTO>();

		for(SysItemIdDTO sysItemId : itemIdList) {
			//ID一つ当たりの原価、売価情報を格納する
			itemCostPriceList.add(setItemCostPriceInfo(itemDAO.getExportCostInfoList(sysItemId), itemDAO.getExportPriceInfoList(sysItemId)));
		}

		//値の始まりの行
		rowIdx = 2;
		for (ItemCostPriceDTO dto : itemCostPriceList) {

			colIdx = 0;

			//行の設定
			xrow = sheet.getRow(rowIdx);
			if (xrow == null) {
				xrow = sheet.createRow(rowIdx);
			}
			xrow.setHeightInPoints(16.5F);

			//品番
			xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getItemCode()));
			MstItemDTO itemDto = new MstItemDTO();
			long sysItemId = service.getSysItemId(dto.getItemCode());
			itemDto =  itemDAO.getMstItemDTO(sysItemId);
			if (authInfo.equals(WebConst.AUTH_INFO_OK)) {
				//仕入価格
				xcallCreateCell(xrow, colIdx++).setCellValue(itemDto.getPurchacePrice());
				//加算経費
				xcallCreateCell(xrow, colIdx++).setCellValue(itemDto.getLoading());
				//Kind原価
				xcallCreateCell(xrow, colIdx++).setCellValue(dto.getKindCost());
			}
			/***************************************以下原価情報**********************************************/
			// KTS
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost1());
			// 車楽院
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost2());
			// T-four
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost3());
			// ラルグスリテール
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost4());
			// BCR
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost5());
			// サイバーエコ
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost6());
			// KTS法人
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost7());
			// ラルグス法人
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost8());
			// KTS掛け業販
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost10());
			// Kind
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost11());
			// Brembo
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost12());
			// ウルトラレーシング事業部
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost13());
			// bellwors
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost14());
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemCost15());

			/****************************************以下売価情報**********************************************/
			// KTS
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice1());
			// 車楽院
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice2());
			// T-four
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice3());
			// ラルグスリテール
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice4());
			// BCR
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice5());
			// サイバーエコ
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice6());
			// KTS法人
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice7());
			// ラルグス法人
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice8());
			// KTS掛け業販
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice10());
			// Kind
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice11());
			// Brembo
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice12());
			// ウルトラレーシング事業部
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice13());
			// bellwork
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice14());
			xcallCreateCell(xrow, colIdx++).setCellValue(dto.getItemPrice15());

			row = null;
			rowIdx++;
		}

		return workBook;
	}

	// added by wahaha
	/**
	 * 在庫一覧：受注情報のエクセル出力します
	 *
	 * @param SysItemIdDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public XSSFWorkbook getKeepOrderList(SearchItemDTO searchItemDTO, XSSFWorkbook workBook) throws DaoException {

		XSSFSheet sheet = workBook.getSheetAt(0);
		workBook.setSheetName(0, "受注情報");
		
		ItemDAO itemDAO = new ItemDAO();		
		List<ResultItemSearchDTO> mstItemList = itemDAO.getExportItemSearchList(searchItemDTO);
		
		rowIdx = 1;
		for(ResultItemSearchDTO dto : mstItemList) {		
			List<ExtendKeepDTO> getKeepList = new ArrayList<>();
			getKeepList.addAll(itemDAO.getKeepList(dto.getSysItemId()));
			
			if (!getKeepList.isEmpty()) {				
				for (int n = 0; n < getKeepList.size(); n++) {

					colIdx = 0;

					//行の設定
					xrow = sheet.getRow(rowIdx);
					if (xrow == null) {
						xrow = sheet.createRow(rowIdx);
					}
					xrow.setHeightInPoints(16.5F);

					xcallCreateCell(xrow, colIdx++).setCellValue(xcastRichTextString(dto.getItemCode()));
					xcallCreateCell(xrow, colIdx++).setCellValue(getKeepList.get(n).getOrderNo());
					xcallCreateCell(xrow, colIdx++).setCellValue(getKeepList.get(n).getKeepNum());
					xcallCreateCell(xrow, colIdx++).setCellValue(getKeepList.get(n).getRemarks());

					row = null;
					rowIdx++;
				}				
			}
		}

		return workBook;
	}
	
	public ItemCostPriceDTO setItemCostPriceInfo(List<ItemCostPriceDTO> itemCost, List<ItemCostPriceDTO> itemPrice) {
		ItemCostPriceDTO dto = new ItemCostPriceDTO();

		//品番設定
		dto.setItemCode(itemCost.get(LIST_INDEX0).getItemCode());

		/*
		 *  ダウンロードするExcelに値を設定する位置を法人によって帰るために
		 *  それぞれのセッターに値を設定する。
		 */

		// 原価を設定する。
		for (int i = 0; i < itemCost.size(); i++) {
			switch(String.valueOf(itemCost.get(i).getSysCorporationId())) {
			case "1":
				dto.setItemCost1(itemCost.get(i).getItemCost());
				break;
			case "2":
				dto.setItemCost2(itemCost.get(i).getItemCost());
				break;
			case "3":
				dto.setItemCost3(itemCost.get(i).getItemCost());
				break;
			case "4":
				dto.setItemCost4(itemCost.get(i).getItemCost());
				break;
			case "5":
				dto.setItemCost5(itemCost.get(i).getItemCost());
				break;
			case "6":
				dto.setItemCost6(itemCost.get(i).getItemCost());
				break;
			case "7":
				dto.setItemCost7(itemCost.get(i).getItemCost());
				break;
			case "8":
				dto.setItemCost8(itemCost.get(i).getItemCost());
				break;
			case "10":
				dto.setItemCost10(itemCost.get(i).getItemCost());
				break;
			case "11":
				dto.setItemCost11(itemCost.get(i).getItemCost());
				break;
			case "12":
				dto.setItemCost12(itemCost.get(i).getItemCost());
				break;
			case "13":
				dto.setItemCost13(itemCost.get(i).getItemCost());
				break;
			case "14":
				dto.setItemCost14(itemCost.get(i).getItemCost());
				break;
			case "15":
				dto.setItemCost15(itemCost.get(i).getItemCost());
				break;
			case "99":
				dto.setKindCost(itemCost.get(i).getItemCost());
				break;
			}
		}

		// 売価を設定する。
		for (int i = 0; i < itemPrice.size(); i++) {
			switch(String.valueOf(itemPrice.get(i).getSysCorporationId())) {
			case "1":
				dto.setItemPrice1(itemPrice.get(i).getItemPrice());
				break;
			case "2":
				dto.setItemPrice2(itemPrice.get(i).getItemPrice());
				break;
			case "3":
				dto.setItemPrice3(itemPrice.get(i).getItemPrice());
				break;
			case "4":
				dto.setItemPrice4(itemPrice.get(i).getItemPrice());
				break;
			case "5":
				dto.setItemPrice5(itemPrice.get(i).getItemPrice());
				break;
			case "6":
				dto.setItemPrice6(itemPrice.get(i).getItemPrice());
				break;
			case "7":
				dto.setItemPrice7(itemPrice.get(i).getItemPrice());
				break;
			case "8":
				dto.setItemPrice8(itemPrice.get(i).getItemPrice());
				break;
			case "10":
				dto.setItemPrice10(itemPrice.get(i).getItemPrice());
				break;
			case "11":
				dto.setItemPrice11(itemPrice.get(i).getItemPrice());
				break;
			case "12":
				dto.setItemPrice12(itemPrice.get(i).getItemPrice());
				break;
			case "13":
				dto.setItemPrice13(itemPrice.get(i).getItemPrice());
				break;
			case "14":
				dto.setItemPrice14(itemPrice.get(i).getItemPrice());
				break;
			case "15":
				dto.setItemPrice15(itemPrice.get(i).getItemPrice());
				break;
			}
		}

		return dto;
	}

	/**
	 * 在庫一覧：助ネコCSVを出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public void exportCsvList(SearchItemDTO searchItemDTO, String fname, HttpServletResponse response) throws DaoException {

		ItemService itemService = new ItemService();
		ItemDAO itemDAO = new ItemDAO();
		List<ResultItemSearchDTO> itemCodeList = new ArrayList<ResultItemSearchDTO>();


		itemService.setFlags(searchItemDTO);
		itemCodeList = itemDAO.getItemSearchCsvList(searchItemDTO);

		// エクセルファイル出力
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fname);

		try (PrintWriter pw = response.getWriter()) {

			//タイトル設定
			String title =    "店舗ID,"
					 + "助ネコ商品コード,"
					 + "カテゴリー大分類,"
					 + "カテゴリー中分類,"
					 + "カテゴリー小分類,"
					 + "商品名,"
					 + "並び順,"
					 + "販売価格,"
					 + "販売単位,"
					 + "商品重量,"
					 + "在庫数更新,"
					 + "販売期間,"
					 + "販売期間 開始日,"
					 + "販売期間 開始時間,"
					 + "販売期間 終了日,"
					 + "販売期間 終了時間,"
					 + "販売先,"
					 + "商品説明文（サムネイル）,"
					 + "商品画像：画像ファイル名１,"
					 + "商品画像：画像キャプション１,"
					 + "商品画像：画像ファイル名２,"
					 + "商品画像：画像キャプション２,"
					 + "商品画像：画像ファイル名３,"
					 + "商品画像：画像キャプション３,"
					 + "商品画像：画像ファイル名４,"
					 + "商品画像：画像キャプション４,"
					 + "商品画像：画像ファイル名５,"
					 + "商品画像：画像キャプション５,"
					 + "商品画像：画像ファイル名６,"
					 + "商品画像：画像キャプション６,"
					 + "商品画像：画像ファイル名７,"
					 + "商品画像：画像キャプション７,"
					 + "商品名（オプション）,"
					 + "商品バリエーション,"
					 + "商品バリエーション：項目名：横軸項目名,"
					 + "商品バリエーション：項目名：縦軸項目名,"
					 + "仕入価格,"
					 + "販売単位：注文数プルダウン設定開始,"
					 + "販売単位：注文数プルダウン設定終了,"
					 + "販売単位：注文数プルダウン設定間隔,"
					 + "販売期間：一覧に表示する指定文字,"
					 + "FTP自動更新,"
					 + "実在庫数,"
					 + "販売期間：一覧のサムネイルは常に表示,"
					 + "同梱,"
					 + "送料計算";

			//CSVファイルに書き込み
			pw.println(title);

			for (ResultItemSearchDTO dto : itemCodeList) {

				String stockNum= "";

				//店舗ID
				String shopNm = "kts" + ",";
				//助ネコ商品コード
				String itemCd = dto.getItemCode() + ",";
				//カテゴリー大分類
				String categoryBig = "1" + ",";
				//カテゴリー中分類
				String categoryMiddle = "0" + ",";
				//カテゴリー小分類
				String categorySmall = "0" + ",";
				//商品名
				String itemNm = dto.getItemNm() + ",";
				//並び順
				String sortNo = "1000" + ",";
				//販売価格
				String sellingPrice = "1" + ",";
				//販売単位
				String sellingUnit = "" + ",";
				//商品重量
				String productWeight = "0" + ",";
				//在庫数更新
				String updateInventryCount = "1" + ",";
				//販売期間
				String salesPeriod = "0" + ",";
				//販売期間 開始日
				String salesStartDate = "" + ",";
				//販売期間 開始時間
				String salesStartTime = "" + ",";
				//販売期間 終了日
				String salesEndDate = "" + ",";
				//販売期間 終了時間
				String salesEndTime = "" + ",";
				//販売先
				String salesDestination = "0" + ",";
				//商品説明文（サムネイル）
				String productDescription = "" + ",";
				//商品画像：画像ファイル名１
				String productImage1 = "" + ",";
				//商品画像：画像キャプション１
				String productCaption1 = "" + ",";
				//商品画像：画像ファイル名２
				String productImage2 = "" + ",";
				//商品画像：画像キャプション２
				String productCaption2 = "" + ",";
				//商品画像：画像ファイル名３
				String productImage3 = "" + ",";
				//商品画像：画像キャプション３
				String productCaption3 = "" + ",";
				//商品画像：画像ファイル名４
				String productImage4 = "" + ",";
				//商品画像：画像キャプション４
				String productCaption4 = "" + ",";
				//商品画像：画像ファイル名５
				String productImage5 = "" + ",";
				//商品画像：画像キャプション５
				String productCaption5 = "" + ",";
				//商品画像：画像ファイル名６
				String productImage6 = "" + ",";
				//商品画像：画像キャプション６
				String productCaption6 = "" + ",";
				//商品画像：画像ファイル名７
				String productImage7 = "" + ",";
				//商品画像：画像キャプション７
				String productCaption7 = "" + ",";
				//商品名（オプション）
				String productNm = "" + ",";
				//商品バリエーション
				String productVariation = "0" + ",";
				//商品バリエーション：項目名：横軸項目名
				String productHor = "" + ",";
				//商品バリエーション：項目名：縦軸項目名
				String productVar = "" + ",";
				//仕入価格
				String purchasePrice = "" + ",";
				//販売単位：注文数プルダウン設定開始
				String salesUnitStart = "" + ",";
				//販売単位：注文数プルダウン設定終了
				String salesUnitEnd = "" + ",";
				//販売単位：注文数プルダウン設定間隔
				String salesUnitInterval = "" + ",";
				//販売期間：一覧に表示する指定文字
				String salesUnitDesignated = "" + ",";
				//FTP自動更新
				String ftpAutoUpdate = "1" + ",";
				//実在庫数 （set商品の場合、出庫分類が1ならば組立可数、0ならば仮在庫数）
				if (dto.getSetItemFlg() == null) {
					stockNum = String.valueOf(dto.getTemporaryStockNum()) + ",";
				} else if (dto.getSetItemFlg().equals("1") && itemDAO.getLeaveClassFlg(dto.getItemCode()).equals("0")) {
					stockNum = String.valueOf(dto.getTemporaryStockNum()) + ",";
				} else if (dto.getSetItemFlg().equals("1") && itemDAO.getLeaveClassFlg(dto.getItemCode()).equals("1")) {
					stockNum = String.valueOf(dto.getAssemblyNum()) + ",";
				} else {
					stockNum = String.valueOf(dto.getTemporaryStockNum()) + ",";
				}

//				if (dto.getSetItemFlg() == null) {
//					stockNum = String.valueOf(dto.getTemporaryStockNum()) + ",";
//				} else if (dto.getSetItemFlg().equals("1")){
//					stockNum = String.valueOf(dto.getAssemblyNum()) + ",";
//				} else {
//					stockNum = String.valueOf(dto.getTemporaryStockNum()) + ",";
//				}

				//販売期間：一覧のサムネイルは常に表示
				String salesInterValSam = "" + ",";
				//同梱
				String include = "0" + ",";
				//送料計算
				String shippingCalculation = "0";

				//文字列を結合する
				String csvRecord =  shopNm
									+ itemCd
									+ categoryBig
									+ categoryMiddle
									+ categorySmall
									+ itemNm
									+ sortNo
									+ sellingPrice
									+ sellingUnit
									+ productWeight
									+ updateInventryCount
									+ salesPeriod
									+ salesStartDate
									+ salesStartTime
									+ salesEndDate
									+ salesEndTime
									+ salesDestination
									+ productDescription
									+ productImage1
									+ productCaption1
									+ productImage2
									+ productCaption2
									+ productImage3
									+ productCaption3
									+ productImage4
									+ productCaption4
									+ productImage5
									+ productCaption5
									+ productImage6
									+ productCaption6
									+ productImage7
									+ productCaption7
									+ productNm
									+ productVariation
									+ productHor
									+ productVar
									+ purchasePrice
									+ salesUnitStart
									+ salesUnitEnd
									+ salesUnitInterval
									+ salesUnitDesignated
									+ ftpAutoUpdate
									+ stockNum
									+ salesInterValSam
									+ include
									+ shippingCalculation;

				//CSVファイルに書き込み
				pw.println(csvRecord);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 出品DBをエクセル出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 * @throws ParseException
	 */
	public HSSFWorkbook getExportDoemsticExhibiti(DomesticExhibitionSearchDTO domesticSearchDTO, HSSFWorkbook workBook) throws DaoException, ParseException {

		HSSFSheet sheet = workBook.getSheetAt(0);
		// シート名の設定.
		//使うかわからないけどコピーしました
		workBook.setSheetName(0, "出品DB");

		DomesticExhibitionDAO domesticExhibitionDao = new DomesticExhibitionDAO();
		List<ExtendDomesticManageDTO> domesticList = domesticExhibitionDao.getDmstcExhbtnDwnLdLst(domesticSearchDTO);

		//値の始まりの行
		rowIdx = 1;
		for (ExtendDomesticManageDTO dto: domesticList) {

			//オープン価格フラグを変換
			if (dto.getOpenPriceFlg().equals("1")) {
				dto.setOpenPriceFlg("1");
			} else {
				dto.setOpenPriceFlg(StringUtils.EMPTY);
			}

			colIdx = 0;

			//行の設定
			row = sheet.getRow(rowIdx);
			if (row == null) {
				row = sheet.createRow(rowIdx);
			}
			row.setHeightInPoints(16.5F);
			//管理品番
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getManagementCode()));
			//メーカー名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getMakerNm()));
			//メーカー名ｶﾅ
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getMakerNmKana()));
			//メーカー品番
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getMakerCode()));
			//商品名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemNm()));
			//問屋
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getWholsesalerNm()));
			//オープン価格
			callCreateCell(row, colIdx++).setCellValue(dto.getOpenPriceFlg());
			//定価
			callCreateCell(row, colIdx++).setCellValue(dto.getListPrice());
			//掛率
			callCreateCell(row, colIdx++).setCellValue(dto.getItemRateOver());
			//送料
			callCreateCell(row, colIdx++).setCellValue(dto.getPostage());
			//仕入原価
			callCreateCell(row, colIdx++).setCellValue(dto.getPurchasingCost());
			//更新日
			callCreateCell(row, colIdx++).setCellValue(dto.getUpdateDate());
			//担当部署名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDepartmentNm()));

			row = null;
			rowIdx++;
		}

		return workBook;
	}

	/**
	 * 国内注文管理一覧をエクセル出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook getExportDoemsticList(DomesticOrderListSearchDTO domesticSearchDTO, HSSFWorkbook workBook) throws Exception {

		sheet = workBook.getSheetAt(0);
		DomesticOrderListService service = new DomesticOrderListService();
		List<DomesticOrderListDTO> DomesticListdto = new ArrayList<DomesticOrderListDTO>();
		Map<String, String> domesticstatus = WebConst.DOMESTIC_EXIHIBITION_STATUS_MAP;
		// シート名の設定.
		//使うかわからないけどコピーしました
		workBook.setSheetName(0, "国内注文一覧");
		DomesticListdto = service.getDomesticOrderIdList(domesticSearchDTO);

		//値の始まりの行
		rowIdx = 1;
		for (DomesticOrderListDTO dto: DomesticListdto) {

			//オープン価格フラグを変換
			if (!StringUtils.isBlank(dto.getStatus())) {
				dto.setStatusNm(domesticstatus.get(dto.getStatus()));
			} else {
				dto.setStatusNm(StringUtils.EMPTY);
			}

			colIdx = 0;

			//行の設定
			row = sheet.getRow(rowIdx);
			if (row == null) {
				row = sheet.createRow(rowIdx);
			}
			row.setHeightInPoints(16.5F);
			/*******************1行目*******************/
			//受注番号
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderNo()));
			//注文書No.
			callCreateCell(row, colIdx++).setCellValue(castRichTextString("KS" + dto.getPurchaseOrderNo()));
			//通番
			callCreateCell(row, colIdx++).setCellValue(dto.getSerealNum());
			//注文書作成日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderSlipDate()));
			//入荷予定日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getArrivalScheduleDate()));
			//入荷日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getArrivalDate()));
			//問屋
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getWholsesalerNm()));
			//メーカー
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getMakerNm()));
			//商品名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemNm()));
			//メーカー品番
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getMakerCode()));
			//数量
			callCreateCell(row, colIdx++).setCellValue(dto.getOrderNum());
			//原価
			callCreateCell(row, colIdx++).setCellValue(dto.getPurchasingCost());
			//送料
			callCreateCell(row, colIdx++).setCellValue(dto.getPostage());
			//原価確認
			if (dto.getCostComfFlag().equals(COST_COMF_FLAG_ON)){
				dto.setCostComfFlag(COST_COMF_FLAG_ON_VAL);
			} else {
				dto.setCostComfFlag(COST_COMF_FLAG_OFF_VAL);
			}
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getCostComfFlag()));
			//注文担当
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderCharge()));
			//対応者
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getPersonInCharge()));
			//入荷担当
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getStockCharge()));
			//対応
			if (dto.getCorrespondence().equals("1")) {
				dto.setCorrespondence(CORRESPONDENCE_VAL1);
			} else if (dto.getCorrespondence().equals("2")){
				dto.setCorrespondence(CORRESPONDENCE_VAL2);
			}
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getCorrespondence()));
			//対応日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getChargeDate()));
			//ステータス
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getStatusNm()));
			//備考
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getListRemarks()));

			row = null;
			rowIdx++;
		}

		return workBook;
	}



	@Override
	public void close() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	public HSSFWorkbook getExportForeignList(ForeignSlipSearchDTO searchDTO, HSSFWorkbook workBook) throws Exception {

		HSSFSheet sheet = workBook.getSheetAt(0);
		ForeignOrderService service = new ForeignOrderService();
		Map<String, String> orderStatusMap = WebConst.ORDER_STATUS_MAP;

		//海外伝票検索実行
		List<ExtendForeignOrderDTO> foreignList = service.getSysForeignSlipIdList(searchDTO);

		// シート名の設定.
		workBook.setSheetName(0, "海外注文一覧DB");

		//値の始まりの行
		rowIdx = 1;


		//行の設定
		row = sheet.getRow(rowIdx);
		if (row == null) {
			row = sheet.createRow(rowIdx);
		}
		row.setHeightInPoints(16.5F);

		//伝票情報をExcel出力するループ：このループ内でさらに商品単位のループがある
		for (ExtendForeignOrderDTO idDto: foreignList) {
			//システム海外伝票ID
			long sysForeignSlipId = idDto.getSysForeignSlipId();

			//インスタンス生成
			ForeignSlipSearchDTO slipSearchDTO = new ForeignSlipSearchDTO();
			ForeignOrderDAO dao = new ForeignOrderDAO();
			//システム海外伝票IDを検索用DTOに格納
			slipSearchDTO.setSysForeignSlipId(sysForeignSlipId);

			//伝票情報検索実行
			ExtendForeignOrderDTO dto = dao.getForeignOrderSlipIdSearch(sysForeignSlipId);

			//注文ステータス読み替え：コード値→名称
			if (!StringUtils.isBlank(dto.getOrderStatus())) {
				dto.setOrderStatusNm(orderStatusMap.get(dto.getOrderStatus()));
			} else {
				dto.setOrderStatusNm(StringUtils.EMPTY);
			}

			//商品の検索実行
			List<ExtendForeignOrderItemDTO> itemList = service.getForeignOrderItemList(searchDTO, sysForeignSlipId);
			//検索結果格納
			dto.setItemList(itemList);

			//伝票毎の入荷予定を検索実行
			List<ExtendArrivalScheduleDTO> arrivalScheduleList = service.getForeignOrderArrivalScheduleList(searchDTO, sysForeignSlipId, itemList);
			//検索結果格納
			dto.setArrivalScheduleList(arrivalScheduleList);

			colIdx = 0;
			row = sheet.createRow(rowIdx);

			/*******************1行目*******************/
			//注文日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderDate()));
			//インボイスNo.
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getInvoiceNo()));
			//PoNo.
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getPoNo()));
			//会社・工場
			callCreateCell(row, colIdx++).setCellValue(dto.getCompanyFactoryNm());

			//ステータス：コード値→名称
			if (dto.getOrderStatus().equals("0")) {
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_0);
			} else if (dto.getOrderStatus().equals("1")){
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_1);
			} else if (dto.getOrderStatus().equals("2")){
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_2);
			} else if (dto.getOrderStatus().equals("3")){
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_3);
			} else if (dto.getOrderStatus().equals("4")){
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_4);
			} else if (dto.getOrderStatus().equals("5")){
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_5);
			} else if (dto.getOrderStatus().equals("6")){
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_6);
			} else if (dto.getOrderStatus().equals("7")){
				dto.setOrderStatus(WebConst.ORDER_STATUS_LABEL_7);
			}
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderStatus()));

			colIdx = 11;

			//支払1：空→未　非空→済
			if (dto.getPaymentDate1().equals("")) {
				dto.setPaymentDate1(PROCESSING_VAL1);
			} else {
				dto.setPaymentDate1(PROCESSING_VAL2);
			}
			callCreateCell(row, colIdx++).setCellValue(dto.getPaymentDate1());

			//支払2：空→未　非空→済
			if (dto.getPaymentDate2().equals("")) {
				dto.setPaymentDate2(PROCESSING_VAL1);
			} else {
				dto.setPaymentDate2(PROCESSING_VAL2);
			}
			callCreateCell(row, colIdx++).setCellValue(dto.getPaymentDate2());

			//商品の登録無し伝票対策
			if (dto.getItemList().size() == 0) {
				rowIdx++;
			}

			//商品情報単位でのループ
			for (int i = 0 ; i < dto.getItemList().size(); i++){

				colIdx = 5;

				row = sheet.getRow(rowIdx);
				if (row == null) {
					row = sheet.createRow(rowIdx);
				}
				//kind品番
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemList().get(i).getItemCode()));
				//工場品番
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemList().get(i).getFactoryItemCode()));
				//商品名
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemList().get(i).getItemNm()));
				//注文数
				callCreateCell(row, colIdx++).setCellValue((dto.getItemList().get(i).getOrderNum()));

				//入荷日
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getArrivalScheduleList().get(i).getArrivalScheduleDate()));

				//入荷
				if (dto.getOrderNum() != dto.getArrivalNum()) {
					dto.getArrivalScheduleList().get(i).setArrivalDate(PROCESSING_VAL1);
				} else if (dto.getOrderNum() == dto.getArrivalNum()) {
					dto.getArrivalScheduleList().get(i).setArrivalDate(PROCESSING_VAL2);
				}
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getArrivalScheduleList().get(i).getArrivalStatus()));


				colIdx = 13;
				//総在庫数
				callCreateCell(row, colIdx++).setCellValue(dto.getItemList().get(i).getTotalStockNum());
				//仮在庫数
				callCreateCell(row, colIdx++).setCellValue(dto.getItemList().get(i).getTemporaryStockNum());

				row = null;
				rowIdx++;

			}
		}

		return workBook;
	}
}
