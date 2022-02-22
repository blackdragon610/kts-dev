package jp.co.kts.service.fileExport;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jp.co.kts.app.output.entity.ExportSaleSummalyDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;


public class ExportCorporateSaleSummalyService extends ExportExcelCorporateSalesSummalyService {

	{
		/**
		 * 始まりの行
		 */
		rowIdx = 0;

		/**
		 * 始まりの列
		 */
		colIdx = 0;

		/**
		 * 始まりのシート
		 */
		sheetIdx = 0;
	}

	@Override
	public HSSFWorkbook getFileExportCorporateSales(CorporateSaleSearchDTO saleSearchDTO,
			HSSFWorkbook workBook) throws Exception {

		sheet = workBook.getSheetAt(sheetIdx);
		//List出力開始位置
		int startListRow = 12;

		rowIdx = 2;

		//受注日
		row = callGetRow(rowIdx, 13.5F);
		String orderDateFromTo = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(saleSearchDTO.getOrderDateFrom())
				|| StringUtils.isNotEmpty(saleSearchDTO.getOrderDateTo())) {

			orderDateFromTo = saleSearchDTO.getOrderDateFrom() + "～" + saleSearchDTO.getOrderDateTo();
		}
		callCreateCell(row, 1).setCellValue(castRichTextString(orderDateFromTo));
		rowIdx++;

		//出庫予定日
		row = callGetRow(rowIdx, 13.5F);
		String scheduledLeavingDateFromTo = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(saleSearchDTO.getScheduledLeavingDateFrom())
				|| StringUtils.isNotEmpty(saleSearchDTO.getScheduledLeavingDateTo())) {

			scheduledLeavingDateFromTo = saleSearchDTO.getScheduledLeavingDateFrom() + "～" + saleSearchDTO.getScheduledLeavingDateTo();
		}
		callCreateCell(row, 1).setCellValue(castRichTextString(scheduledLeavingDateFromTo));
		rowIdx++;

		//法人
		row = callGetRow(rowIdx, 13.5F);
		CorporationService corporationService = new CorporationService();
		if (saleSearchDTO.getSysCorporationId() != 0) {

			callCreateCell(row, 1).setCellValue(castRichTextString(
					corporationService.getCorporationNm(saleSearchDTO.getSysCorporationId())));
		}
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		rowIdx++;

		//品番(前方一致)
		row = callGetRow(rowIdx, 13.5F);
		callCreateCell(row, 1).setCellValue(castRichTextString(saleSearchDTO.getItemCode()));
		rowIdx++;

		//品番
		row = callGetRow(rowIdx, 13.5F);
		String itemCodeFromTo = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(saleSearchDTO.getItemCodeFrom())
				|| StringUtils.isNotEmpty(saleSearchDTO.getItemCodeTo())) {

			itemCodeFromTo = saleSearchDTO.getItemCodeFrom() + "～" + saleSearchDTO.getItemCodeTo();
		}
		callCreateCell(row, 1).setCellValue(castRichTextString(itemCodeFromTo));
		rowIdx++;

		//商品名
		row = callGetRow(rowIdx, 13.5F);
		callCreateCell(row, 1).setCellValue(castRichTextString(saleSearchDTO.getItemNm()));
		rowIdx++;

		//全受注分表示の有無
		row = callGetRow(rowIdx, 13.5F);
		if (saleSearchDTO.getAllOrderDisplayFlag().equals("0")) {
			callCreateCell(row, 1).setCellValue("無し");
		} else if (saleSearchDTO.getAllOrderDisplayFlag().equals("1")) {
			callCreateCell(row, 1).setCellValue("有り");
		}
		rowIdx++;

		//summaryList出力開始
		CorporateSaleDisplayService corpDispService = new CorporateSaleDisplayService();
		List<ExportSaleSummalyDTO> exportSaleSummalyList = new ArrayList<>();
		exportSaleSummalyList = corpDispService.getCorporateSaleSummaly(saleSearchDTO);

		//出力設定
		rowIdx = startListRow;
//		long sumTotalPieceRate = 0;
		long sumTotalInTaxPieceRate = 0;
		long sumTotalNoTaxPieceRate = 0;
		long sumTotalCost = 0;
		long sumTotalGrossMargin = 0;
		for (ExportSaleSummalyDTO dto: exportSaleSummalyList) {

			//行の設定
			row = sheet.getRow(rowIdx);
			if (row == null) {
				row = sheet.createRow(rowIdx);
			}
			row.setHeightInPoints(13.5F);

			colIdx = 0;
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemCode()));
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemNm()));
			callCreateCell(row, colIdx++).setCellValue(dto.getTotalOrderNum());
//			callCreateCell(row, colIdx++).setCellValue(dto.getTotalSalesPieceRate());
			callCreateCell(row, colIdx++).setCellValue(dto.getTotalInTaxPieceRate());
			callCreateCell(row, colIdx++).setCellValue(dto.getTotalNoTaxPieceRate());
			callCreateCell(row, colIdx++).setCellValue(dto.getTotalCost());
			callCreateCell(row, colIdx++).setCellValue(dto.getTotalGrossMargin());

			//合計値を合計します
//			sumTotalPieceRate += dto.getTotalSalesPieceRate();
			sumTotalInTaxPieceRate += dto.getTotalInTaxPieceRate();
			sumTotalNoTaxPieceRate += dto.getTotalNoTaxPieceRate();
			sumTotalCost += dto.getTotalCost();
			sumTotalGrossMargin += dto.getTotalGrossMargin();

			rowIdx++;
		}

		//集計結果表示
		row = callGetRow(startListRow - 3, 13.5F);
//		callCreateCell(row, 3).setCellValue(sumTotalPieceRate);
		callCreateCell(row, 3).setCellValue(sumTotalInTaxPieceRate);
		callCreateCell(row, 4).setCellValue(sumTotalNoTaxPieceRate);
		callCreateCell(row, 5).setCellValue(sumTotalCost);
		callCreateCell(row, 6).setCellValue(sumTotalGrossMargin);
		//test

		return workBook;
	}
}
