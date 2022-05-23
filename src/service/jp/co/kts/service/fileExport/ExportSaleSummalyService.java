package jp.co.kts.service.fileExport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jp.co.kts.app.output.entity.ExportSaleSummalyDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.mst.ChannelService;
import jp.co.kts.service.mst.CorporationService;


public class ExportSaleSummalyService extends ExportExcelSalesService {

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
	public HSSFWorkbook getFileExportSales(HttpSession session, SaleSearchDTO saleSearchDTO,
			HSSFWorkbook workBook) throws Exception {
		
		return getFileExportSales(saleSearchDTO, workBook);
	}
	@Override
	public HSSFWorkbook getFileExportSales(SaleSearchDTO saleSearchDTO,
			HSSFWorkbook workBook) throws Exception {

		sheet = workBook.getSheetAt(sheetIdx);
		//List出力開始位置
		int startListRow = 12;

		rowIdx = 2;

		row = callGetRow(rowIdx, 13.5F);
		String orderDateFromTo = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(saleSearchDTO.getOrderDateFrom())
				|| StringUtils.isNotEmpty(saleSearchDTO.getOrderDateTo())) {

			orderDateFromTo = saleSearchDTO.getOrderDateFrom() + "～" + saleSearchDTO.getOrderDateTo();
		}
		callCreateCell(row, 1).setCellValue(castRichTextString(orderDateFromTo));
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		String shipmentDateFromTo = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(saleSearchDTO.getShipmentPlanDateFrom())
				|| StringUtils.isNotEmpty(saleSearchDTO.getShipmentPlanDateTo())) {

			shipmentDateFromTo = saleSearchDTO.getShipmentPlanDateFrom() + "～" + saleSearchDTO.getShipmentPlanDateTo();
		}
		callCreateCell(row, 1).setCellValue(castRichTextString(shipmentDateFromTo));
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		CorporationService corporationService = new CorporationService();
		if (saleSearchDTO.getSysCorporationId() != 0) {

			callCreateCell(row, 1).setCellValue(castRichTextString(
					corporationService.getCorporationNm(saleSearchDTO.getSysCorporationId())));
		}
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		ChannelService channelService = new ChannelService();
		callCreateCell(row, 1).setCellValue(castRichTextString(
				channelService.getChannelNm(saleSearchDTO.getSysChannelId())));
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		callCreateCell(row, 1).setCellValue(castRichTextString(saleSearchDTO.getItemCode()));
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		String itemCodeFromTo = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(saleSearchDTO.getItemCodeFrom())
				|| StringUtils.isNotEmpty(saleSearchDTO.getItemCodeTo())) {

			itemCodeFromTo = saleSearchDTO.getItemCodeFrom() + "～" + saleSearchDTO.getItemCodeTo();
		}
		callCreateCell(row, 1).setCellValue(castRichTextString(itemCodeFromTo));
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		callCreateCell(row, 1).setCellValue(castRichTextString(saleSearchDTO.getItemNm()));
		rowIdx++;

		row = callGetRow(rowIdx, 13.5F);
		callCreateCell(row, 1).setCellValue(castRichTextString(saleSearchDTO.getDisposalRoute()));
		rowIdx++;

		//summaryList出力開始
		SaleDAO saleDAO = new SaleDAO();
		List<ExportSaleSummalyDTO> exportSaleSummalyList = new ArrayList<>();
		exportSaleSummalyList = saleDAO.getExportSaleSummay(saleSearchDTO);

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
