package jp.co.kts.service.fileImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.struts.upload.FormFile;

import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.ExcelImportDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticManageDTO;
import jp.co.kts.app.output.entity.ActionErrorExcelImportDTO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceConst;

/**
 * 出品データベースインポートクラス
 * @author 齋藤優太
 *
 */
public class DomesticExhibitionImportService {

	protected HSSFWorkbook wb;

	private static final String EXCEL_XLS_CONTENT_TYPE = "application/vnd.ms-excel";
	private static final String EXCEL_XLS_CONTENT_TYPE_2 = "application/octet-stream";
	//メーカー名/メーカー名ｶﾅ入力有無フラグ
	private static final String MAKER_INPUT_FLG = "1";
	//メーカー名/メーカー名ｶﾅ未入力
	private static final int MAKER_NO_INPUT = 0;
	//掛率％
	private static final double RATE_OVER_PERCENT = 0.01;
	/** オープン価格フラグ：Excel値 */
	private static final String OPEN_PRICE_FLG_ON_VALUE = "○";
	/** オープン価格フラグ：Excel値 */
	private static final String OPEN_PRICE_FLG_ON_VALUE_KAN= "〇";
	/** オープン価格フラグ：有 */
	private static final String OPEN_PRICE_FLG_ON = "1";
	/** オープン価格フラグ：無 */
	private static final String OPEN_PRICE_FLG_OFF = "0";

	private static final int ERROR_IDX = ServiceConst.UPLOAD_EXCEL_INIT_ROWS1;

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

		//ファイルのcontentTypeをチェック
		if (!EXCEL_XLS_CONTENT_TYPE.equals(excelImportForm.getContentType())
				&& !EXCEL_XLS_CONTENT_TYPE_2.equals(excelImportForm.getContentType())) {
			dto.getResult().addErrorMessage("LED00119", excelImportForm.getContentType());
 			return dto;
		}

		try {
			//エクセルファイルの取り込み
			POIFSFileSystem filein = new POIFSFileSystem();
			InputStream inputStream = excelImportForm.getInputStream();
			filein = new POIFSFileSystem(inputStream);

			//エクセルを開く
			wb = new HSSFWorkbook();
			wb = new HSSFWorkbook(filein);

		} catch (Exception e) {
			dto.getResult().addErrorMessage("LED00120", e.getMessage());
			return dto;
		}

		return dto;
	}

	/**
	 * シートの存在を確認します。
	 * 存在すれば、そのシートがある位置情報を返します。
	 * なければ-1を返します。
	 *
	 * @param result
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	protected int checkSheetName(Result<ExcelImportDTO> result, HSSFWorkbook wb, String sheetName) {

		int sheetNum = wb.getSheetIndex(sheetName);

		//シートが存在しなければエラーメッセージを出す
		if (sheetNum < 0) {
			result.addErrorMessage("LED00111", sheetName);
		}

		return sheetNum;
	}

	/**
	 *  シートの存在を確認します。
	 * 存在すれば、そのシートがある位置情報を返します。
	 * なければ-1を返します。エラーメッセージの返却無し版
	 * @param result
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	protected int checkSheetNameResult(Result<ExcelImportDTO> result, HSSFWorkbook wb, String sheetName) {

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
	protected List<List<String>> uploadExcelFile(HSSFWorkbook wb,HSSFSheet sheet, int col, ActionErrorExcelImportDTO dto) throws IOException{

		int rowIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS1;

		List<List<String>> excelList = new ArrayList<>();

		boolean firstRowBlankFlg = false;

		//エクセルのデータをList<List<String>>に変換
		while(true){
			HSSFRow row = sheet.getRow(rowIndex);

			if (row != null) {
				List<String> rowList = new ArrayList<>();
				//rowの行から列ごとに値の取得
				for (int cellnum = 0; cellnum <= col; cellnum++){
					HSSFCell cell = row.getCell(cellnum);

					String text = null;

					//最初の値が入っていない場合は、読み込みを打ち切る
//					if (cellnum == 0
//							&& (cell == null || cell.getStringCellValue().equals(""))) {
//						firstRowBlankFlg = true;
//						break;
//					}

					if (cell != null) {

						switch (cell.getCellType()) {

						 case Cell.CELL_TYPE_STRING:
						  // 文字列
							 text = cell.getRichStringCellValue().getString();
							 break;
						 case Cell.CELL_TYPE_NUMERIC:
						  if(DateUtil.isCellDateFormatted(cell)) {
						   // 日付
							  text =  String.valueOf(cell.getDateCellValue());
							  break;
						  }
						  // 数値
						  double _value = cell.getNumericCellValue();
						  int ___value = (int) _value;
						  double deffer = (_value - ___value) * 1000;
						  if ((int) deffer == 999) {
							  	text = String.valueOf(Math.round(_value));
						  }else {
							  	text = String.valueOf(_value);
						  }
//						  	double val = Double.valueOf(text)+0.1;
//						  	text = String.valueOf((long) val);

						  	break;
						 case Cell.CELL_TYPE_FORMULA:

						 // 数式の結果を返す
							 text = String.valueOf(wb.getCreationHelper().createFormulaEvaluator().evaluateInCell(cell));
						   break;
						 case Cell.CELL_TYPE_BOOLEAN:
						   text = String.valueOf(cell.getBooleanCellValue());
						   break;
						 default :
							 text = StringUtils.EMPTY;
						 }
					}

					if (cellnum == 0
					&& (cell == null || StringUtils.isEmpty(text))) {

						firstRowBlankFlg = true;
						break;
					}

					rowList.add(text);
				}

				//最初の列に値が入っていない場合は、読み込みを打ち切る
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
	 * 文字列のリストをItemCostPriceDTOに格納し返却します
	 * @param dto
	 * @param list
	 * @return
	 * @throws Exception
	 */
	protected DomesticExhibitionDTO setDomesticExhibitionDTO(ExtendDomesticManageDTO dto,
			List<String> list) throws Exception {


		//管理品番
		BeanUtils.setProperty(dto, "managementCode", list.get(0));
		//メーカー名/メーカー名ｶﾅが両方未入力の場合
		if(!dto.getMakerInputFlg().equals(MAKER_INPUT_FLG)) {
			BeanUtils.setProperty(dto, "sysMakerId", MAKER_NO_INPUT);
		}
		//メーカーコード
		BeanUtils.setProperty(dto, "makerCode", list.get(3));
		//商品名
		BeanUtils.setProperty(dto, "itemNm", list.get(4));
		//TODO 問屋のIDが管理されたら追加
//		BeanUtils.setProperty(dto, "wholsesalerId", list.get(0));
		//問屋名
		BeanUtils.setProperty(dto, "wholsesalerNm", list.get(5));
		//担当部署名
		BeanUtils.setProperty(dto, "departmentNm", list.get(12));


		return dto;
	}

	/**
	 * 仕入原価を計算します。
	 * @param strListPrice：定価
	 * @param strItemRateOver：掛率
	 * @param strPostage：送料
	 * @param openPriceFlg：オープン価格フラグ
	 * @param purchasingCost：仕入原価
	 * @return
	 * @throws Exception
	 */
	protected ExtendDomesticManageDTO purchasingCostCalc(String strListPrice,String
			strItemRateOver,String strPostage, String openPriceFlg, String strPurchasingCost) throws Exception{
		ExtendDomesticManageDTO dto = new ExtendDomesticManageDTO();

		// HERE I AM
		double listPrice = 0;
		double itemRateOver = 0;
		double postage = 0;
		double purchasingCost = 0;
		
		if (!GenericValidator.isBlankOrNull(strListPrice)) {
			listPrice = Double.valueOf(strListPrice);
		}
//			if (!strItemRateOver.isEmpty()) {
		if (!GenericValidator.isBlankOrNull(strItemRateOver)) {
			itemRateOver = Double.valueOf(strItemRateOver);
		}
		if (!GenericValidator.isBlankOrNull(strPostage)) {
			postage = Double.valueOf(strPostage);
		}

		if (!GenericValidator.isBlankOrNull(strPurchasingCost)) {
			purchasingCost = Double.valueOf(strPurchasingCost);
		}

		if (StringUtils.isBlank(openPriceFlg)|| (!openPriceFlg.equals(OPEN_PRICE_FLG_ON))){
			dto.setOpenPriceFlg("off");
			//仕入原価計算
			if (itemRateOver > 0 && listPrice > 0) {
				purchasingCost = listPrice * (itemRateOver * RATE_OVER_PERCENT);
			}else {
				if (itemRateOver == 0) {
					if(purchasingCost > 0 && listPrice > 0) {
						itemRateOver = purchasingCost / listPrice * 100;
						itemRateOver = itemRateOver * 100;
						itemRateOver = Math.round(itemRateOver);
						itemRateOver = itemRateOver / 100;
					}
				}else if (listPrice == 0) {
					if(purchasingCost > 0 && itemRateOver > 0) {
						listPrice = purchasingCost / itemRateOver * 100;
						listPrice = Math.round(listPrice);
					}
				}
			}
		}
		else {
			dto.setOpenPriceFlg("on");
			listPrice = 0;
			itemRateOver = 0;
			
			if (!GenericValidator.isBlankOrNull(strPurchasingCost)) {
				purchasingCost = Double.valueOf(strPurchasingCost);
			}
			
		}

		//返却用DTOにセット
		//オープン価格フラグがある場合、定価/掛率/送料/仕入原価には0が入る
		dto.setListPrice((int)Math.floor(listPrice));
		dto.setItemRateOver(itemRateOver);
		dto.setPostage((int)Math.floor(postage));
		dto.setPurchasingCost((int)Math.floor(purchasingCost));
		return dto;

	}












































}
