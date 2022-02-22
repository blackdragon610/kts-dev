package jp.co.kts.service.fileImport;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
//import org.apache.poi.XSSf.usermodel.XSSFCell;
//import org.apache.poi.XSSf.usermodel.XSSFRow;
//import org.apache.poi.XSSf.usermodel.XSSFSheet;
//import org.apache.poi.XSSf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.ExcelImportDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.WarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ItemCostPriceDTO;
import jp.co.kts.app.output.entity.ActionErrorExcelImportDTO;
import jp.co.kts.dao.fileImport.ExcelImportDAO;
import jp.co.kts.dao.mst.SupplierDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * エクセルインポートクラス
 *
 * @author aito
 *
 */

public class ExcelXImportService {

	protected XSSFWorkbook wb;

	private static final int ITEM_COST_CORPORATION_NUM_START = 1;
	private static final int ITEM_COST_CORPORATION_NUM_END = 17;
	private static final int ITEM_PRICE_CORPORATION_NUM_START = 18;
	private static final int ITEM_PRICE_CORPORATION_NUM_END = 31;

	private static final int ITEM_COST_NOT_AUTH_START = 1;
	private static final int ITEM_COST_NOT_AUTH_END = 14;
	private static final int ITEM_PRICE_NOT_AUTH_START = 16;
	private static final int ITEM_PRICE_NOT_AUTH_END = 28;
	private static final String EXCEL_XLSX_CONTENT_TYPE =
	 "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	private static final int ERROR_IDX = ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1;

	protected int errorIndex;

	{
		initErrorIdex();
	}

	/**
	 * エラーインデックスを初期化します。
	 */
	protected void initErrorIdex() {

		errorIndex = ERROR_IDX;
	}

	/**
	 * ファイルの体裁がエクセルかどうか判定し、エクセルだった場合そのファイルを開きます
	 *
	 * @param excelImportForm
	 * @return
	 * @throws Exception
	 */
	public ActionErrorExcelImportDTO validate(FormFile excelImportForm) throws Exception {

		ActionErrorExcelImportDTO dto = new ActionErrorExcelImportDTO();

		// 2014/04/18 伊東 start
		// ファイルのcontentTypeをチェック
		if (!EXCEL_XLSX_CONTENT_TYPE.equals(excelImportForm.getContentType())) {
			dto.getResult().addErrorMessage("LED00119", excelImportForm.getContentType());
			return dto;
		}

		try {
			OPCPackage pkg = OPCPackage.open(excelImportForm.getInputStream());
			wb = new XSSFWorkbook(pkg);
		} catch (Exception e) {
			dto.getResult().addErrorMessage("LED00120", e.getMessage());
			return dto;
		}

		return dto;
	}

	/**
	 * シートの存在を確認します。 存在すれば、そのシートがある位置情報を返します。 なければ-1を返します。
	 *
	 * @param result
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	protected int checkSheetName(Result<ExcelImportDTO> result, XSSFWorkbook wb, String sheetName) {

		int sheetNum = wb.getSheetIndex(sheetName);

		// シートが存在しなければエラーメッセージを出す
		if (sheetNum < 0) {
			result.addErrorMessage("LED00111", sheetName);
		}

		return sheetNum;
	}

	/**
	 * シートの存在を確認します。 存在すれば、そのシートがある位置情報を返します。 なければ-1を返します。
	 *
	 * @param result
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	protected int checkSheetNameCostPrice(Result<ExcelImportDTO> result, XSSFWorkbook wb, String sheetName) {

		int sheetNum = wb.getSheetIndex(sheetName);

		return sheetNum;
	}

	/**
	 * シートの存在を確認します。 存在すれば、そのシートがある位置情報を返します。 なければ-1を返します。エラーメッセージの返却無し版
	 *
	 * @param result
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	protected int checkSheetNameResult(Result<ExcelImportDTO> result, XSSFWorkbook wb, String sheetName) {

		int sheetNum = wb.getSheetIndex(sheetName);

		return sheetNum;
	}

	/**
	 * エクセルのファイル取り込み(EXCELをList<List<String>>に変換する)
	 *
	 * @param sheet
	 * @param col
	 * @return
	 * @throws IOException
	 */
	protected List<List<String>> uploadExcelFile(XSSFWorkbook wb, XSSFSheet sheet, int col) throws IOException {

		int rowIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS;

		List<List<String>> excelList = new ArrayList<>();

		boolean firstRowBlankFlg = false;

		// エクセルのデータをList<List<String>>に変換
		while (true) {
			XSSFRow row = sheet.getRow(rowIndex);

			if (row != null) {
				List<String> rowList = new ArrayList<>();
				// rowの行から列ごとに値の取得
				for (int cellnum = 0; cellnum <= col; cellnum++) {
					XSSFCell cell = row.getCell(cellnum);

					String text = null;

					// 最初の値が入っていない場合は、読み込みを打ち切る
					// if (cellnum == 0
					// && (cell == null || cell.getStringCellValue().equals(""))) {
					// firstRowBlankFlg = true;
					// break;
					// }

					if (cell != null) {

						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							// 文字列
							text = cell.getRichStringCellValue().getString();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								// 日付
								text = String.valueOf(cell.getDateCellValue());
								break;
							}
							// 商品情報シートの仕入金額のみダブル型で取得する
							if (col == ServiceConst.UPLOAD_EXCEL_ITEM_COLUMN && cellnum == 4) {
								text = String.valueOf(cell.getNumericCellValue());
							} else {
								// 数値
								text = String.valueOf((long) cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_FORMULA:

							// 数式の結果を返す
							text = String.valueOf(wb.getCreationHelper().createFormulaEvaluator().evaluateInCell(cell));
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							text = String.valueOf(cell.getBooleanCellValue());
							break;
						default:
							text = StringUtils.EMPTY;
						}
					}

					if (cellnum == 0 && (cell == null || StringUtils.isEmpty(text))) {
						firstRowBlankFlg = true;
						break;
					}

					rowList.add(text);
				}

				// 最初の列に値が入っていない場合は、読み込みを打ち切る
				if (firstRowBlankFlg) {
					break;
				}

				excelList.add(rowList);
				rowIndex++;
			} else {
				break;
			}
		}
		return excelList;
	}

	/**
	 * 文字列のリストをMstItemDTOに格納し返却します
	 *
	 * @param midto
	 * @param strList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected MstItemDTO setMstItemDTO(MstItemDTO midto, List<String> strList)
			throws IllegalAccessException, InvocationTargetException {

		/** dtoに格納 */

		// 品番
		BeanUtils.setProperty(midto, "itemCode", strList.get(0));

		// 旧品番
		BeanUtils.setProperty(midto, "oldItemCode", strList.get(1));

		// 商品名
		BeanUtils.setProperty(midto, "itemNm", strList.get(2));

		// 分類ID
		String sysGroupId = strList.get(3);
		if (StringUtils.isNotEmpty(sysGroupId)) {
			if (sysGroupId.contains(".")) {
				sysGroupId = sysGroupId.substring(0, sysGroupId.indexOf("."));
			}
		}
		BeanUtils.setProperty(midto, "sysGroupNmId", sysGroupId);

		// 総在庫数
		BeanUtils.setProperty(midto, "manualPath", "0");

		// 発注アラート数
		String orderAlertNum = strList.get(4);
		if (StringUtils.isNotEmpty(orderAlertNum)) {
			if (orderAlertNum.contains(".")) {
				orderAlertNum = orderAlertNum.substring(0, orderAlertNum.indexOf("."));
			}
		}
		BeanUtils.setProperty(midto, "orderAlertNum", orderAlertNum);

		// 説明書フラグ
		BeanUtils.setProperty(midto, "manualFlg", "0");

		// 図面フラグ
		BeanUtils.setProperty(midto, "planSheetFlg", "0");

		// セット商品フラグ
		BeanUtils.setProperty(midto, "setItemFlg", "0");

		// 出庫分類フラグ
		BeanUtils.setProperty(midto, "leaveClassFlg", "0");

		// 仕様メモ
		BeanUtils.setProperty(midto, "specMemo", strList.get(5));

		return midto;
	}

	/**
	 * 文字列のリストをMstItemDTOに格納し返却します：商品情報
	 *
	 * @param midto
	 * @param strList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws DaoException
	 * @throws NumberFormatException
	 */
	protected MstItemDTO setMstItemDetailDTO(MstItemDTO midto, List<String> strList, boolean authInfo)
			throws IllegalAccessException, InvocationTargetException, NumberFormatException, DaoException {

		// インスタンス生成
		SupplierDAO supDao = new SupplierDAO();
		ExtendMstSupplierDTO dto = new ExtendMstSupplierDTO();

		/** dtoに格納 */
		// 品番
		BeanUtils.setProperty(midto, "itemCode", strList.get(0));

		// 海外閲覧権限有Excelの場合
		if (authInfo) {
			// 工場品番
			BeanUtils.setProperty(midto, "factoryItemCode", strList.get(1));
			// 旧品番
			BeanUtils.setProperty(midto, "oldItemCode", strList.get(2));
			// 仕入金額
			BeanUtils.setProperty(midto, "purchaceCost", strList.get(4));
			// 商品名
			BeanUtils.setProperty(midto, "itemNm", strList.get(6));
			// 海外商品名
			BeanUtils.setProperty(midto, "foreignItemNm", strList.get(7));
			// 仕入先ID
			BeanUtils.setProperty(midto, "sysSupplierId", strList.get(8));
			if (StringUtils.isNotBlank(strList.get(8))) {
				dto = supDao.getSupplier(Long.valueOf(strList.get(8)));
				if (dto != null) {
					// 仕入国
					BeanUtils.setProperty(midto, "purchaceCountry", dto.getCountry());
				}
			} else {
				BeanUtils.setProperty(midto, "sysSupplierId", 0);
				BeanUtils.setProperty(midto, "purchaceCountry", "");
			}
			// 車種
			BeanUtils.setProperty(midto, "carModel", strList.get(9));
			// 型式
			BeanUtils.setProperty(midto, "model", strList.get(10));
			// メーカー
			BeanUtils.setProperty(midto, "makerNm", strList.get(11));
			// 仕様メモ
			BeanUtils.setProperty(midto, "specMemo", strList.get(12));
			// 在庫アラート数
			String orderAlertNum = strList.get(13);
			if (StringUtils.isNotEmpty(orderAlertNum)) {
				if (orderAlertNum.contains(".")) {
					orderAlertNum = orderAlertNum.substring(0, orderAlertNum.indexOf("."));
				}
			}
			BeanUtils.setProperty(midto, "orderAlertNum", orderAlertNum);

			// 商品リードタイム
			Object keyVal = null;
			if (StringUtils.isNotEmpty(strList.get(14))) {
				for (Iterator<String> lead = WebConst.LEAD_TIME_MAP.keySet().iterator(); lead.hasNext();) {
					Object key = lead.next();
					Object val = WebConst.LEAD_TIME_MAP.get(key);
					if (strList.get(14).equals(val)) {
						keyVal = key;
						break;
					}
				}
			}

			if (keyVal != null) {
				BeanUtils.setProperty(midto, "itemLeadTime", String.valueOf(keyVal));
			} else {
				BeanUtils.setProperty(midto, "itemLeadTime", strList.get(14));
			}

			// 梱包サイズ
			BeanUtils.setProperty(midto, "packingSize", strList.get(15));
			// 重量
			BeanUtils.setProperty(midto, "weight", strList.get(16));

			// 最少ロット数
			BeanUtils.setProperty(midto, "minimumOrderQuantity", strList.get(19));
			// 海外閲覧権限無しExcelの場合
		} else {

			// 旧品番
			BeanUtils.setProperty(midto, "oldItemCode", strList.get(1));

			// 商品名
			BeanUtils.setProperty(midto, "itemNm", strList.get(2));

			// 車種
			BeanUtils.setProperty(midto, "carModel", strList.get(3));
			// 型式
			BeanUtils.setProperty(midto, "model", strList.get(4));
			// メーカー
			BeanUtils.setProperty(midto, "makerNm", strList.get(5));
			// 仕様メモ
			BeanUtils.setProperty(midto, "specMemo", strList.get(6));
			// 在庫アラート数
			String orderAlertNum = strList.get(7);
			if (StringUtils.isNotEmpty(orderAlertNum)) {
				if (orderAlertNum.contains(".")) {
					orderAlertNum = orderAlertNum.substring(0, orderAlertNum.indexOf("."));
				}
			}
			BeanUtils.setProperty(midto, "orderAlertNum", orderAlertNum);
			// 商品リードタイム
			Object keyVal = null;
			if (StringUtils.isNotEmpty(strList.get(8))) {
				for (Iterator<String> lead = WebConst.LEAD_TIME_MAP.keySet().iterator(); lead.hasNext();) {
					Object key = lead.next();
					Object val = WebConst.LEAD_TIME_MAP.get(key);
					if (strList.get(8).equals(val)) {
						keyVal = key;
						break;
					}
				}
			}
			if (keyVal != null) {
				BeanUtils.setProperty(midto, "itemLeadTime", String.valueOf(keyVal));
			} else {
				BeanUtils.setProperty(midto, "itemLeadTime", strList.get(8));
			}
			// 梱包サイズ
			BeanUtils.setProperty(midto, "packingSize", strList.get(9));
			// 重量
			BeanUtils.setProperty(midto, "weight", strList.get(10));
			// 最少ロット数
			BeanUtils.setProperty(midto, "minimumOrderQuantity", strList.get(13));
		}

		// 説明書フラグ
		BeanUtils.setProperty(midto, "manualFlg", "0");

		// 図面フラグ
		BeanUtils.setProperty(midto, "planSheetFlg", "0");

		// セット商品フラグ
		BeanUtils.setProperty(midto, "setItemFlg", "0");

		// 出庫分類フラグ
		BeanUtils.setProperty(midto, "leaveClassFlg", "0");
		return midto;
	}

	/**
	 * 文字列のリストをWarehouseStockDTOに格納し返却します
	 * TODO 新規作成後削除予定
	 *
	 * @param wsdto
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws DaoException
	 */
	protected WarehouseStockDTO setWarehouseStockDTO(WarehouseStockDTO wsdto, List<String> list)
			throws IllegalAccessException, InvocationTargetException, DaoException {

		ExcelImportDAO excelImportDao = new ExcelImportDAO();

		// システム商品ID
		Integer sysItemId = excelImportDao.getSysItemId(list.get(0));
		BeanUtils.setProperty(wsdto, "sysItemId", sysItemId);

		// システム倉庫ID
		BeanUtils.setProperty(wsdto, "sysWarehouseId", list.get(1));

		// 優先度
		String priority = list.get(2);
		if (priority.contains(".")) {
			priority = priority.substring(0, priority.indexOf("."));
		}
		BeanUtils.setProperty(wsdto, "priority", priority);

		// 在庫数
		String stockNum = list.get(3);
		if (stockNum.contains(".")) {
			stockNum = stockNum.substring(0, stockNum.indexOf("."));
		}
		BeanUtils.setProperty(wsdto, "stockNum", stockNum);

		// ロケーションNo
		BeanUtils.setProperty(wsdto, "locationNo", list.get(4));

		return wsdto;
	}

	/**
	 * 文字列のリストをWarehouseStockDTOに格納し返却します
	 *
	 * @param wsdto
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws DaoException
	 */
	protected WarehouseStockDTO setWarehouseStockInfoDTO(WarehouseStockDTO wsdto, List<String> list)
			throws IllegalAccessException, InvocationTargetException, DaoException {

		ExcelImportDAO excelImportDao = new ExcelImportDAO();

		// システム商品ID
		Integer sysItemId = excelImportDao.getSysItemId(list.get(0));
		BeanUtils.setProperty(wsdto, "sysItemId", sysItemId);

		// システム倉庫ID
		BeanUtils.setProperty(wsdto, "sysWarehouseId", list.get(1));
		// 優先度：システム倉庫ID順
		BeanUtils.setProperty(wsdto, "priority", list.get(1));

		// 在庫数
		String stockNum = list.get(2);
		if (stockNum.contains(".")) {
			stockNum = stockNum.substring(0, stockNum.indexOf("."));
		}
		BeanUtils.setProperty(wsdto, "stockNum", stockNum);

		// ロケーションNo
		BeanUtils.setProperty(wsdto, "locationNo", list.get(3));

		return wsdto;
	}

	/**
	 * 文字列のリストをWarehouseStockDTOに格納し返却します[新規用]
	 *
	 * @param wsdto
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws DaoException
	 */
	protected WarehouseStockDTO setStockInfoDTO(WarehouseStockDTO wsdto, List<String> list)
			throws IllegalAccessException, InvocationTargetException, DaoException {

		ExcelImportDAO excelImportDao = new ExcelImportDAO();

		// システム商品ID
		Integer sysItemId = excelImportDao.getSysItemId(list.get(0));
		BeanUtils.setProperty(wsdto, "sysItemId", sysItemId);

		// システム倉庫ID(倉庫Ｎo)
		BeanUtils.setProperty(wsdto, "sysWarehouseId", list.get(1));

		// 在庫数
		String stockNum = list.get(2);
		if (stockNum.contains(".")) {
			stockNum = stockNum.substring(0, stockNum.indexOf("."));
		}
		BeanUtils.setProperty(wsdto, "stockNum", stockNum);

		// ロケーションNo
		BeanUtils.setProperty(wsdto, "locationNo", list.get(3));

		return wsdto;
	}

	/**
	 * 文字列のリストをItemCostPriceDTOに格納し返却します[更新用]
	 *
	 * @param dto
	 * @param list
	 * @return dto
	 * @throws Exception
	 */
	protected ItemCostPriceDTO setItemCostPriceDTO(ItemCostPriceDTO dto, List<String> list) throws Exception {
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		MstUserDTO mstUserDTO = new MstUserDTO();
		mstUserDTO = userService.getUserName(userId);
		String authInfo = mstUserDTO.getOverseasInfoAuth();

		ExcelImportDAO excelImportDao = new ExcelImportDAO();

		// 品番
		BeanUtils.setProperty(dto, "itemCode", list.get(0));

		// システム商品ID
		Integer sysItemId = excelImportDao.getSysItemId(list.get(0));
		BeanUtils.setProperty(dto, "sysItemId", sysItemId);

		/*
		 * Excelフォーマット「権限有り」の処理
		 */
		if (authInfo.equals("1")) {
			/********************** 原価情報 **********************/
			ArrayList<Integer> itemCostList = new ArrayList<Integer>();
			for (int i = ITEM_COST_CORPORATION_NUM_START; i <= ITEM_COST_CORPORATION_NUM_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String cost = list.get(i).trim();

				// 原価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (cost == null || cost.equals("")) {
					itemCostList.add(Integer.valueOf(0));
				} else {
					itemCostList.add(Integer.valueOf(cost));
				}
			}
			BeanUtils.setProperty(dto, "itemCostList", itemCostList);

			/********************** 売価情報 **********************/
			ArrayList<Integer> itemPriceList = new ArrayList<Integer>();
			for (int i = ITEM_PRICE_CORPORATION_NUM_START; i <= ITEM_PRICE_CORPORATION_NUM_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String price = list.get(i).trim();

				// 売価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (price == null || price.equals("")) {
					itemPriceList.add(Integer.valueOf(0));
				} else {
					itemPriceList.add(Integer.valueOf(price));
				}
			}
			BeanUtils.setProperty(dto, "itemPriceList", itemPriceList);

			/********************** 法人ID 原価用 **********************/
			ArrayList<String> sysCorporationIdCostList = new ArrayList<String>();
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_77);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_88);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_99);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_15);

			BeanUtils.setProperty(dto, "sysCorporationIdCostList", sysCorporationIdCostList);

			/********************** 法人ID 売価用 **********************/
			ArrayList<String> sysCorporationIdPriceList = new ArrayList<String>();
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_15);

			BeanUtils.setProperty(dto, "sysCorporationIdPriceList", sysCorporationIdPriceList);

		} else {
			/*
			 * Excelフォーマット「権限無し」の処理
			 */
			/********************** 原価情報 **********************/
			ArrayList<Integer> itemCostList = new ArrayList<Integer>();
			for (int i = ITEM_COST_NOT_AUTH_START; i <= ITEM_COST_NOT_AUTH_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String cost = list.get(i).trim();

				// 原価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (cost == null || cost.equals("")) {
					itemCostList.add(Integer.valueOf(0));

				} else {
					itemCostList.add(Integer.valueOf(cost));
				}

			}
			BeanUtils.setProperty(dto, "itemCostList", itemCostList);

			/********************** 売価情報 **********************/
			ArrayList<Integer> itemPriceList = new ArrayList<Integer>();
			for (int i = ITEM_PRICE_NOT_AUTH_START; i <= ITEM_PRICE_NOT_AUTH_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String price = list.get(i).trim();

				// 売価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (price == null || price.equals("")) {
					itemPriceList.add(Integer.valueOf(0));
				} else {
					itemPriceList.add(Integer.valueOf(price));
				}

			}
			BeanUtils.setProperty(dto, "itemPriceList", itemPriceList);

			/********************** 法人ID 原価用 **********************/
			ArrayList<String> sysCorporationIdCostList = new ArrayList<String>();

			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_15);

			BeanUtils.setProperty(dto, "sysCorporationIdCostList", sysCorporationIdCostList);

			/********************** 法人ID 売価用 **********************/
			ArrayList<String> sysCorporationIdPriceList = new ArrayList<String>();
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_15);

			BeanUtils.setProperty(dto, "sysCorporationIdPriceList", sysCorporationIdPriceList);
		}

		return dto;
	}

	/**
	 * 文字列のリストをItemCostPriceDTOに格納し返却します[新規登録用]
	 *
	 * @param dto
	 * @param list
	 * @return dto
	 * @throws Exception
	 */
	protected ItemCostPriceDTO setItemCostPriceInsertDTO(ItemCostPriceDTO dto, List<String> list) throws Exception {
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		MstUserDTO mstUserDTO = new MstUserDTO();
		mstUserDTO = userService.getUserName(userId);
		String authInfo = mstUserDTO.getOverseasInfoAuth();

		ExcelImportDAO excelImportDao = new ExcelImportDAO();

		// 品番
		BeanUtils.setProperty(dto, "itemCode", list.get(0));

		// システム商品ID
		Integer sysItemId = excelImportDao.getSysItemId(list.get(0));
		BeanUtils.setProperty(dto, "sysItemId", sysItemId);

		if (authInfo.equals("1")) {
			/********************** 原価情報 **********************/
			ArrayList<Integer> itemCostList = new ArrayList<Integer>();
			for (int i = ITEM_COST_CORPORATION_NUM_START; i <= ITEM_COST_CORPORATION_NUM_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String cost = list.get(i).trim();

				// システム商品原価ID
				// 原価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (cost == null ||cost.equals("")) {
					itemCostList.add(Integer.valueOf(0));
				} else {
					itemCostList.add(Integer.valueOf(cost));
				}
			}
			BeanUtils.setProperty(dto, "itemCostList", itemCostList);

			/********************** 売価情報 **********************/
			ArrayList<Integer> itemPriceList = new ArrayList<Integer>();
			for (int i = ITEM_PRICE_CORPORATION_NUM_START; i <= ITEM_PRICE_CORPORATION_NUM_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String price = list.get(i).trim();

				// システム商品売価ID
				// 売価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (price == null ||price.equals("")) {
					itemPriceList.add(Integer.valueOf(0));
				} else {
					itemPriceList.add(Integer.valueOf(price));
				}
			}

			BeanUtils.setProperty(dto, "itemPriceList", itemPriceList);

			/********************** 法人ID 原価用 **********************/
			ArrayList<String> sysCorporationIdCostList = new ArrayList<String>();
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_77);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_88);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_99);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_15);


			BeanUtils.setProperty(dto, "sysCorporationIdCostList", sysCorporationIdCostList);

			/********************** 法人ID 売価用 **********************/
			ArrayList<String> sysCorporationIdPriceList = new ArrayList<String>();
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_15);

			BeanUtils.setProperty(dto, "sysCorporationIdPriceList", sysCorporationIdPriceList);

		} else {

			/********************** 原価情報 **********************/
			ArrayList<Integer> itemCostList = new ArrayList<Integer>();
			for (int i = ITEM_COST_NOT_AUTH_START; i <= ITEM_COST_NOT_AUTH_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String cost = list.get(i).trim();

				// 原価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (cost == null || cost.equals("")) {
					itemCostList.add(Integer.valueOf(0));
				} else {
					itemCostList.add(Integer.valueOf(cost));
				}
			}
			BeanUtils.setProperty(dto, "itemCostList", itemCostList);

			/********************** 売価情報 **********************/
			ArrayList<Integer> itemPriceList = new ArrayList<Integer>();
			for (int i = ITEM_PRICE_NOT_AUTH_START; i <= ITEM_PRICE_NOT_AUTH_END; i++) {
				//StringをIntegerに変換する以下の処理でエラーを防ぐために空白文字を全て削除する
				String price = list.get(i).trim();

				// 売価のセルが未入力、空文字、スペースの場合、原価未設定となるのでデータベースには「0」を設定する。
				if (price == null || price.equals("")) {
					itemPriceList.add(Integer.valueOf(0));
				} else {
					itemPriceList.add(Integer.valueOf(price));
				}
			}
			BeanUtils.setProperty(dto, "itemPriceList", itemPriceList);

			/********************** 法人ID 原価用 **********************/
			ArrayList<String> sysCorporationIdCostList = new ArrayList<String>();

			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdCostList.add(WebConst.SYS_CORPORATION_ID_15);

			BeanUtils.setProperty(dto, "sysCorporationIdCostList", sysCorporationIdCostList);

			/********************** 法人ID 売価用 **********************/
			ArrayList<String> sysCorporationIdPriceList = new ArrayList<String>();
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_1);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_2);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_3);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_4);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_5);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_6);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_7);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_8);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_10);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_11);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_12);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_13);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_14);
			sysCorporationIdPriceList.add(WebConst.SYS_CORPORATION_ID_15);

			BeanUtils.setProperty(dto, "sysCorporationIdPriceList", sysCorporationIdPriceList);
		}

		return dto;
	}

}
