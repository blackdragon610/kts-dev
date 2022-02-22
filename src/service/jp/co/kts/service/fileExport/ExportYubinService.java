package jp.co.kts.service.fileExport;

import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CorporateReceiveDTO;
import jp.co.kts.app.common.entity.MstAccountDTO;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.mst.AccountService;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.sale.CorporateReceiveService;
import jp.co.kts.ui.web.struts.WebConst;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportYubinService extends ExportExcelYubinService {

	/**
	 * 受注番号の列番号　4
	 */
	private final int colOrderNo = 4;

	/**
	 * 商品名の列番号　60
	 */
	private final int colItemNm = 60;

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


	@Override
	public HSSFWorkbook getFileExportYubin(List<ExtendCorporateSalesSlipDTO> corpSalesSlipList,
			HSSFWorkbook workBook) throws Exception {

		sheet = workBook.getSheetAt(0);

		// シート名の設定.
		//使うかわからないけどコピーしました
		workBook.setSheetName(0, "日本郵便");

		row = sheet.getRow(0);
		if (row == null) {
			row = sheet.createRow(0);
		}

		MstCorporationDTO corporation = new MstCorporationDTO();


		for (ExtendCorporateSalesSlipDTO slipDto : corpSalesSlipList) {
			if (!StringUtils.equals(slipDto.getTransportCorporationSystem(), "日本郵便")) {
				continue;
			}

			//行の設定
			row = sheet.getRow(rowIdx);
			if (row == null) {
				row = sheet.createRow(rowIdx);
			}
			row.setHeightInPoints(30);
			colIdx = 0;

			//法人情報
			corporation = new CorporationService().getCorporation(slipDto.getSysCorporationId());

			//お客様郵便番号
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(slipDto.getDestinationZip()));
			//お客様住所
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(slipDto.getDestinationPrefectures()
					+ slipDto.getDestinationMunicipality() + slipDto.getDestinationAddress() + slipDto.getDestinationBuildingNm()));
			//お客様氏名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(slipDto.getDestinationNm()));
			//購入元郵便番号
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(corporation.getZip()));
			//購入元住所
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(corporation.getAddress()));
			//購入元会社名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(corporation.getCorporationFullNm()));
			//注文番号
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(slipDto.getOrderNo()));

			rowIdx++;
		}

		return workBook;
	}

}
