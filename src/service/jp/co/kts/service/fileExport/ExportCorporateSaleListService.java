package jp.co.kts.service.fileExport;

import java.util.List;

import jp.co.kts.app.common.entity.MstAccountDTO;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.service.mst.AccountService;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.sale.CorporateReceiveService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportCorporateSaleListService extends ExportExcelCorporateSalesService {

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
	public HSSFWorkbook getFileExportCorporateSales(CorporateSaleSearchDTO searchDto,
			HSSFWorkbook workBook) throws Exception {

		sheet = workBook.getSheetAt(0);

		// シート名の設定.
		//使うかわからないけどコピーしました
		workBook.setSheetName(0, "業販売上表");

//		CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
//		List<ExtendCorporateSalesSlipDTO> corpSalesSlipList = corporateSaleDAO.getSearchCorporateSalesSlipList(searchDto);		//暫定
		CorporateSaleDisplayService corpDispService = new CorporateSaleDisplayService();
		List<SysCorporateSalesSlipIdDTO> corpSalesSlipIdList = corpDispService.getSysCorporateSalesSlipIdList(searchDto);

		row = sheet.getRow(0);
		if (row == null) {
			row = sheet.createRow(0);
		}
//		//粗利の算出方法を取得
//		callCreateCell(row, 63).setCellValue(castRichTextString
//				("粗利（" + WebConst.GROSS_PROFIT_CALC_MAP.get(corporateSaleSearchDTO.getGrossProfitCalc())) + "）");


		//商品数
		int itemCount = 0;

		for (SysCorporateSalesSlipIdDTO idDto: corpSalesSlipIdList) {


			//伝票情報取得
			long sysCorporateSalesSlipId = idDto.getSysCorporateSalesSlipId();

			CorporateSaleSearchDTO saleSearchDTO = new CorporateSaleSearchDTO();
			saleSearchDTO.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);

			CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();

			ExtendCorporateSalesSlipDTO dto = corporateSaleDAO.getSearchCorporateSalesSlip(saleSearchDTO);
//			corpDispService.setFlagsDisp(dto);
			if (StringUtils.isNotEmpty(searchDto.getSlipStatus()) && StringUtils.equals(searchDto.getSlipStatus(), "3")) {
				dto.setSlipStatusNm("売上");
			}

			//伝票に紐付くアイテム情報
			List<ExtendCorporateSalesItemDTO> itemList = corpDispService.getCorporateSalesItemList(sysCorporateSalesSlipId, dto.getSysCorporationId(), searchDto);
			if (itemList.size() <= 0) {
				continue;
			}
			dto.setCorporateSalesItemList(itemList);
			//ステータスセット
			for (ExtendCorporateSalesItemDTO item : itemList) {
				if (StringUtils.isEmpty(item.getLeavingDate())) {
					item.setDeliveryStatus("未納");
				} else if (item.getOrderNum() > item.getLeavingNum()) {
					item.setDeliveryStatus("一部");
				}
			}


			itemCount = 0;

			//得意先
			MstClientDTO client = new ClientService().getClient(dto.getSysClientId());
			//入金情報
			String receiveDate = new CorporateReceiveService().getCorporateReceiveLatestDate(dto.getSysCorporateSalesSlipId());
			//口座情報
			MstAccountDTO account = new AccountService().getAccount(dto.getSysAccountId());

 			/** 伝票用の金額情報を算出 */
			//金額関係
			long sumSubtotal = 0;
			long sumSubtotal77 = 0;
			long sumGrossMargin = 0;
			long sumGrossMargin77 = 0;
			long sumTax = 0;



			for (ExtendCorporateSalesItemDTO itemDto : dto.getCorporateSalesItemList()) {
				if (!StringUtils.equals(itemDto.getItemCode(), "77")) {
				//金額情報取得
				sumSubtotal += itemDto.getPieceRate() * itemDto.getOrderNum();
				sumGrossMargin += (itemDto.getPieceRate() - itemDto.getCost()) * itemDto.getOrderNum();

//				//商品コード77は税をかけない
//				if (!StringUtils.equals(itemDto.getItemCode(), "77")) {
//					if (itemDto.getOrderNum() > 0) {
//						sumTax += corpDispService.getTax(itemDto.getPieceRate(), dto.getTaxRate()) * itemDto.getOrderNum();
//					} else {
//						sumTax += corpDispService.getTax(itemDto.getPieceRate() * -1, dto.getTaxRate()) * itemDto.getOrderNum() * -1;
//					}
//				}

				} else {
					sumSubtotal77 += itemDto.getPieceRate() * itemDto.getOrderNum();
					sumGrossMargin77 += (itemDto.getPieceRate() - itemDto.getCost()) * itemDto.getOrderNum();
				}
			}

			// 税計算
			sumTax = (int)Math.floor(sumSubtotal * dto.getTaxRate()) / 100;
			//合計請求額再計算
			sumSubtotal += sumSubtotal77;
			//粗利60再計算
			sumGrossMargin += sumGrossMargin77;


			for (ExtendCorporateSalesItemDTO itemDto : dto.getCorporateSalesItemList()) {
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
				colIdx = 47;
				//品番
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(itemDto.getItemCode()));
				//商品名
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(itemDto.getItemNm()));
				//注文数
				callCreateCell(row, colIdx++).setCellValue(itemDto.getOrderNum());
				//単価50
				callCreateCell(row, colIdx++).setCellValue(itemDto.getPieceRate());
				//原価
				callCreateCell(row, colIdx++).setCellValue(itemDto.getCost());
				//出庫予定日
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(itemDto.getScheduledLeavingDate()));
				//出庫日
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(itemDto.getLeavingDate()));
				//出庫数
				callCreateCell(row, colIdx++).setCellValue(itemDto.getLeavingNum());

				itemCount++;

				//複数行あった場合商品情報を書き込みのみを行います
	 			if (itemCount != 1) {
					rowIdx++;
					continue;
				}

				/**
				 * 商品情報 end
				 */



	 			/**
				 * 商品情報の書き込み終了後伝票の情報をセルに書き込みます
				 */
				colIdx = 0;

				//ピッキングリスト0
				int pickNum = 0;
				for (ExtendCorporateSalesItemDTO item : dto.getCorporateSalesItemList()) {
					//画面上に表示があるフラグはon/offに置き換えられている
					if (StringUtils.equals(item.getPickingListFlg(),"on")) { pickNum++; }
				}
				String picking = "";
				if (pickNum == 0) {
					picking = "×";
				} else if (pickNum != dto.getCorporateSalesItemList().size()) {
					picking = "△";
				} else if (pickNum == dto.getCorporateSalesItemList().size()) {
					picking = "○";
				}
				callCreateCell(row, colIdx).setCellValue(castRichTextString(picking));
				colIdx++;

				//出庫1
				int leaveNum = 0;
				for (ExtendCorporateSalesItemDTO item : dto.getCorporateSalesItemList()) {
					if (StringUtils.isNotEmpty(item.getLeavingDate())) { leaveNum++; }
				}
				String leave = "";
				if (leaveNum == 0) {
					leave = "×";
				} else if (leaveNum != dto.getCorporateSalesItemList().size()) {
					leave = "△";
				} else if (leaveNum == dto.getCorporateSalesItemList().size()) {
					leave = "○";
				}
				callCreateCell(row, colIdx).setCellValue(castRichTextString(leave));
				colIdx++;

				//返品伝票2
				if (StringUtils.isNotEmpty(dto.getReturnFlg())) {
					callCreateCell(row, colIdx).setCellValue(castRichTextString(WebConst.SALES_SLIP_FLG_MAP.get(dto.getReturnFlg())));
				}
				colIdx++;

				//無効伝票3
				if (StringUtils.isNotEmpty(dto.getInvalidFlag())) {
					callCreateCell(row, colIdx).setCellValue(castRichTextString(WebConst.SALES_SLIP_FLG_MAP.get(dto.getInvalidFlag())));
				}
				colIdx++;


				colIdx = colOrderNo;
				//伝票番号4
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderNo()));
				//ステータス5
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSlipStatusNm()));
				//担当者
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getPersonInCharge()));
				//見積日
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getEstimateDate()));
				//注文日
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderDate()));
				//法人
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getCorporationNm()));
				//得意先名（名）10
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getClientNm()));
				//得意先名（セイ）
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getClientNmKana()));
				//得意先TEL
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getTel()));
				//得意先MAIL
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getMailAddress()));
				//得意先郵便番号
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getZip()));
				//得意先都道府県15
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getPrefectures()));
				//得意先市区町村
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getMunicipality()));
				//得意先市区町村以降
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getAddress()));
				//得意先建物名
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getBuildingNm()));
				//得意先部署名
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(client.getQuarter()));
				//得意先支払方法20
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getPaymentMethodNm()));
				//納入期限
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDeliveryDeadline()));
				//回収方法
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(account.getAccountNm()));
				//入金日
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(receiveDate));
				//入金額
				callCreateCell(row, colIdx++).setCellValue(dto.getReceivePrice());
				//備考25
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getOrderRemarks()));
				//お届け先名（名）
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationNm()));
				//お届け先名（メイ）
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationNmKana()));
				//お届け先TEL
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationTel()));
				//郵便番号
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationZipDisp()));
				//都道府県30
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationPrefectures()));
				//市区町村
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationMunicipality()));
				//市区町村以降
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationAddress()));
				//建物名
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationBuildingNm()));
				//部署名
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationQuarter()));
				//役職名35
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationPosition()));
				//担当者名
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationContactPersonNm()));
				//備考
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSenderRemarks()));
	//			//一言メモ(注文)
	//			callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSenderMemo()));
				//伝票番号
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getSlipNo()));
				//運送会社
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getTransportCorporationSystem()));
				//送り状種別40
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getInvoiceClassification()));
				//お届け指定日
				callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getDestinationAppointDate()));
				//お届け時間帯
				if (StringUtils.equals(dto.getTransportCorporationSystem(), "佐川急便")) {
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(WebConst.APPOINT_TIME_EHIDEN_MAP.get(dto.getDestinationAppointTime())));
				} else if (StringUtils.equals(dto.getTransportCorporationSystem(), "ヤマト運輸")) {
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(WebConst.APPOINT_TIME_B2_MAP.get(dto.getDestinationAppointTime())));
				} else if (StringUtils.equals(dto.getTransportCorporationSystem(), "日本郵便")) {
					callCreateCell(row, colIdx++).setCellValue(castRichTextString("指定なし"));
				} else {
					callCreateCell(row, colIdx++).setCellValue(castRichTextString("指定なし"));
				}
				//見積書備考
				callCreateCell(row, colIdx++).setCellValue(dto.getEstimateRemarks());
				//注文確定所備考
				callCreateCell(row, colIdx++).setCellValue(dto.getOrderFixRemarks());
				//請求書備考45
				callCreateCell(row, colIdx++).setCellValue(dto.getBillingRemarks());
				//消費税46
				callCreateCell(row, colIdx++).setCellValue(sumTax);

				colIdx = 56;
				//通貨コード56
				String currency = (StringUtils.equals(dto.getCurrency(), "1")) ? "JPY" : "USD";
				callCreateCell(row, colIdx).setCellValue(currency);
				colIdx++;

				//税区分67
				callCreateCell(row, colIdx).setCellValue(castRichTextString("外税"));
				colIdx++;

				//小計
				callCreateCell(row, colIdx++).setCellValue(sumSubtotal);
				//合計請求額
				callCreateCell(row, colIdx++).setCellValue(sumSubtotal + sumTax);
				//粗利60
				callCreateCell(row, colIdx++).setCellValue(sumGrossMargin);

				rowIdx++;
			}
		}

		return workBook;
	}

}
