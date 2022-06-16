package jp.co.kts.service.fileExport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.SysSalesSlipIdDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.ui.web.struts.WebConst;

public class ExportSaleListService extends ExportExcelSalesService {

	/**
	 * 受注番号の列番号　4
	 */
	private final int colOrderNo = 4;

	/**
	 * 商品名の列番号　60
	 */
	private final int colItemNm = 59;

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
	public HSSFWorkbook getFileExportSales(
			SaleSearchDTO saleSearchDTO,
			HSSFWorkbook workBook) throws DaoException {
		return getFileExportSales(null, saleSearchDTO, workBook);
	}

	@Override
	public HSSFWorkbook getFileExportSales(
			HttpSession session, 
			SaleSearchDTO saleSearchDTO,
			HSSFWorkbook workBook) throws DaoException {

		sheet = workBook.getSheetAt(0);

		//セルの幅を設定します
//		callSetColumnWidth(sheet, 60, 72);
//		Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
//		map.put(colOrderNo, 23);
//		map.put(colItemNm, 72);
//		map.put(8, 18);
//		//郵便番号
//		map.put(15, 9);

//		callSetColumnWidthMap(map);

		//受注番号
//		callSetColumnWidth(colOrderNo, 23);
		//商品名
//		callSetColumnWidth(colItemNm, 72);
		//販売チャネル
//		callSetColumnWidth(8, 18);

		// 埋め込み計算式を動作させるための記述
		//使うかわからないけどコピーしました
//		sheet.setForceFormulaRecalculation(true);

		// シート名の設定.
		//使うかわからないけどコピーしました
		workBook.setSheetName(0, "売上表");

		SaleDAO saleDAO = new SaleDAO();
		
		List<ExtendSalesSlipDTO> salesSlipList =
				session == null ? 
				saleDAO.getExportSaleSearchList(saleSearchDTO) : 
				(List<ExtendSalesSlipDTO>)session.getAttribute("getSearchSalesSlipList(SaleSearchDTO)");
		
		//検索結果をセッション 取得     
//		List<ExtendSalesSlipDTO> salesSlipList = saleDAO.getExportSaleSearchList(saleSearchDTO);
		
		row = sheet.getRow(0);
		if (row == null) {
			row = sheet.createRow(0);
		}
		//粗利の算出方法を取得
		callCreateCell(row, 66).setCellValue(castRichTextString
				("粗利（" + WebConst.GROSS_PROFIT_CALC_MAP.get(saleSearchDTO.getGrossProfitCalc())) + "）");

		long sysSalesSlipId = 0;
//		long saveRowIdx = 0;
//		HSSFRow saveRow = null;
		HSSFCell saveCell = null;
		for (ExtendSalesSlipDTO dto: salesSlipList) {

			//行の設定
			row = sheet.getRow(rowIdx);
			if (row == null) {
				row = sheet.createRow(rowIdx);
			}
			row.setHeightInPoints(30);

			/**
			 * 商品情報 start
			 * 複数行表示される可能性があるので初めに書き込みを行います
			 */
			colIdx = 58;
			//品番58
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemCode()));
			colIdx = colItemNm;
			//商品名59
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getItemNm()));
			//注文数60
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderNum()));
			callCreateCell(row, colIdx++).setCellValue(dto.getOrderNum());
			//単価61
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getPieceRate()));
			callCreateCell(row, colIdx++).setCellValue(dto.getPieceRate());
			//原価62
			callCreateCell(row, colIdx++).setCellValue(dto.getCost());

			//複数行あった場合商品情報を書き込みのみを行います
 			if (sysSalesSlipId != 0 && sysSalesSlipId == dto.getSysSalesSlipId()
					&& saveCell != null) {

				String str = String.valueOf((long)saveCell.getNumericCellValue());
				long grossMargin = Long.parseLong(str);
				saveCell.setCellValue(grossMargin - dto.getCost() * dto.getOrderNum());
				sysSalesSlipId = dto.getSysSalesSlipId();

				rowIdx++;
				continue;

			} else {

//				saveRowIdx = rowIdx;
				sysSalesSlipId = dto.getSysSalesSlipId();
			}

			/**
			 * 商品情報 end
			 */

			/**
			 * 商品情報の書き込み終了後伝票の情報をセルに書き込みます
			 */
			colIdx = 0;
			//処理ルート0
 			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDisposalRoute()));

			if (StringUtils.isNotEmpty(dto.getPickingListFlg())) {
				//ピッキングリスト
				callCreateCell(row, colIdx).setCellValue(castRichTextString(WebConst.SALES_SLIP_FLG_MAP.get(dto.getPickingListFlg())));
			}
			colIdx++;

			if (StringUtils.isNotEmpty(dto.getLeavingFlg())) {

				//出庫2
				callCreateCell(row, colIdx).setCellValue(castRichTextString(WebConst.SALES_SLIP_FLG_MAP.get(dto.getLeavingFlg())));
			}
			colIdx++;

			if (StringUtils.isNotEmpty(dto.getLeavingFlg())) {

				//返品伝票3
				callCreateCell(row, colIdx).setCellValue(castRichTextString(WebConst.SALES_SLIP_FLG_MAP.get(dto.getReturnFlg())));
			}
			colIdx++;

			colIdx = colOrderNo;
			//受注番号4
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderNo()));
			//注文日5
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderDate()));
			//注文時間6
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderTime()));
			//法人7
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getCorporationNm()));
			//販売チャネル8
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getChannelNm()));
			//注文者名（姓）9
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderFamilyNm()));
			//注文者名（名）10
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderFirstNm()));
			//注文者名（セイ）11
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderFamilyNmKana()));
			//注文者名（メイ）12
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderFirstNmKana()));
			//注文者TEL13
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderTel()));
			//注文者MAIL14
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderMailAddress()));
			//郵便番号15
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderZipDisp()));
			//都道府県
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderPrefectures()));
			//市区町村
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderMunicipality()));
			//市区町村以降
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderAddress()));
			//建物名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderBuildingNm()));
			//会社名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderCompanyNm()));
			//部署名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderQuarter()));
			//決済方法
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getAccountMethod()));
			//決済手数料
			callCreateCell(row, colIdx++).setCellValue(dto.getAccountCommission());
			//入金日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDepositDate()));
			//ご利用ポイント
			callCreateCell(row, colIdx++).setCellValue(dto.getUsedPoint());
			//獲得ポイント
			callCreateCell(row, colIdx++).setCellValue(dto.getGetPoint());
			//会員番号
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getMenberNo()));
			//備考/一言メモ
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderRemarksMemo()));
//			//一言メモ
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderMemo()));
			//お届け先名（姓）
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationFamilyNm()));
			//お届け先名（名）
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationFirstNm()));
			//お届け先名（セイ）
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationFamilyNmKana()));
			//お届け先名（メイ）
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationFirstNm()));
			//お届け先TEL
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationTel()));
			//郵便番号
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationZipDisp()));
			//都道府県
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationPrefectures()));
			//市区町村
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationMunicipality()));
			//市区町村以降
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationAddress()));
			//建物名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationBuildingNm()));
			//会社名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationCompanyNm()));
			//部署名
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationQuarter()));
			//備考
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSenderRemarks()));
			//一言メモ(注文)
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSenderMemo()));
			//伝票番号
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSlipNo()));
			//運送会社
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getTransportCorporationSystem()));
			//送り状種別
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getInvoiceClassification()));
			//代引請求金額
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getCashOnDeliveryCommission()));
			callCreateCell(row, colIdx++).setCellValue(dto.getCashOnDeliveryCommission());
			//お届け指定日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationAppointDate()));
			//お届け時間帯
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationAppointTime()));
			//出荷予定日
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getShipmentPlanDate()));
			//一言メモ(伝票)
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSlipMemo()));
			//納品書備考
			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDeliveryRemarks()));
			//購入回数
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getBuyCount()));
			callCreateCell(row, colIdx++).setCellValue(dto.getBuyCount());
			//非商品
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDiscommondity()));
			callCreateCell(row, colIdx++).setCellValue(dto.getDiscommondity());
			//ギフト
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getGift()));
			callCreateCell(row, colIdx++).setCellValue(dto.getGift());
			//送料
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getPostage()));
			callCreateCell(row, colIdx++).setCellValue(dto.getPostage());
			//代引手数料
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getCodCommission()));
			callCreateCell(row, colIdx++).setCellValue(dto.getCodCommission());
			//消費税
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getTax()));
			callCreateCell(row, colIdx++).setCellValue(dto.getTax());

			colIdx = 63;
			if (StringUtils.isNotBlank(dto.getTaxClass())) {

				//税区分63
				callCreateCell(row, colIdx).setCellValue(castRichTextString(WebConst.TAX_MAP.get(dto.getTaxClass())));
			}
			colIdx++;

			//小計64
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSumPieceRate()));
			callCreateCell(row, colIdx++).setCellValue(dto.getSumPieceRate());
			//合計請求額65
//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSumClaimPrice()));
			callCreateCell(row, colIdx++).setCellValue(dto.getSumClaimPrice());

//			long[] test = new long[2];
//			SaleDisplayService saleDisplayService = new SaleDisplayService();
			long price = 0;
			if (StringUtils.equals(saleSearchDTO.getGrossProfitCalc(),
					WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE)) {

//				test = saleDisplayService.getCostGrossMargin(dto.getSysSalesSlipId(), dto.getSysCorporationId()
//						, dto.getSumPieceRate());
				price = dto.getSumPieceRate();
				if (StringUtils.equals(dto.getTaxClass(), WebConst.TAX_CODE_INCLUDE)) {

					price -= dto.getTax();
				}


			} else if (StringUtils.equals(saleSearchDTO.getGrossProfitCalc(),
					WebConst.GROSS_PROFIT_CALC_TOTAL_CODE)) {

//				test = saleDisplayService.getCostGrossMargin(dto.getSysSalesSlipId(), dto.getSysCorporationId()
//						, dto.getSumClaimPrice());
				price = dto.getSumClaimPrice();
			}
			//粗利66
//			saveRow = row;
			saveCell = callCreateCell(row, colIdx);
			callCreateCell(row, colIdx++).setCellValue(price - (dto.getCost() * dto.getOrderNum()));

			rowIdx++;
		}

		return workBook;
	}

}
