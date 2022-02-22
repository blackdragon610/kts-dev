package jp.co.kts.service.sale;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CorporateSalesSlipDTO;
import jp.co.kts.app.common.entity.CsvImportDTO;
import jp.co.kts.app.common.entity.ItemCostDTO;
import jp.co.kts.app.common.entity.SalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.ChannelDAO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * CSVインポート時に使用するクラスです
 * @author aito
 *
 */

public class SaleCsvService extends SaleService {

	private static String rslMemo = "楽天倉庫から出庫予定です。";


	/**
	 * 型がList<CsvImportDTO>になってしまっているものを法人ごとのList<List<CsvImportDTO>>にし返却します
	 *
	 * @param csvImportList
	 * @return
	 * @throws DaoException
	 * @throws ParseException
	 */
	public List<List<CsvImportDTO>> csvToSaleSlipList(List<CsvImportDTO> csvImportList) throws DaoException, ParseException {

//		List<ErrorDTO> errorList = new ArrayList<>();
//		List<CsvImportDTO>[] csvImportLists = new ArrayList<CsvImportDTO>[5];
//		List<Integer>[] aa = new ArrayList<>[5];
		List<List<CsvImportDTO>> csvImportLists = new ArrayList<>();

		//要検討、そもそもひとつにまとめたのは考慮不足？
		//複数のCSVファイルがひとつのlistになってしまっているので、分割して更新。
		String fileNm = StringUtils.EMPTY;
		List<CsvImportDTO> dividedCsvImportList = new ArrayList<>();
//		int idx = 0;
		for (CsvImportDTO dto: csvImportList) {

			//0番目の場合
			if (StringUtils.isEmpty(fileNm)) {
				fileNm = dto.getFileNm();
			}

			//csvファイルが切り替わる判定
			if (StringUtils.equals(fileNm, dto.getFileNm())) {

				dividedCsvImportList.add(dto);

			} else {

				//更新
//				errorList.add(csvToSaleSlip(dividedCsvImportList));
				csvImportLists.add(dividedCsvImportList);
				dividedCsvImportList = new ArrayList<CsvImportDTO>();
				dividedCsvImportList.add(dto);
				fileNm = dto.getFileNm();
			}
		}

		csvImportLists.add(dividedCsvImportList);
		return csvImportLists;
	}

	/**
	 * CSVファイルを売上伝票に読み替え登録を行います
	 *
	 * @param csvImportList
	 * @return
	 * @throws DaoException
	 * @throws ParseException
	 */
	public ErrorDTO csvToSaleSlip(List<CsvImportDTO> csvImportList) throws DaoException, ParseException {

		ErrorDTO csvErrorDTO = new ErrorDTO();
		if (csvImportList == null) {
			csvErrorDTO.setSuccess(false);
			ErrorMessageDTO messageDTO = new ErrorMessageDTO();
			messageDTO.setErrorMessage("予期せぬエラー!!");
			csvErrorDTO.getErrorMessageList().add(messageDTO);
			return csvErrorDTO;
		}

		//csvファイルのままだと一つの受注番号で複数行あるのでそれを正規化するイメージ
		for (int i = 0; i < csvImportList.size(); i++) {
			csvErrorDTO.setSuccess(true);

			CsvImportDTO csvImportDTO = csvImportList.get(i);

			//DBに格納する値
			ExtendSalesSlipDTO salesSlipDTO = setSalesSlipDTO(csvImportDTO);
			List<ExtendSalesItemDTO> salesItemList = new ArrayList<>();

			setSalesItemOrTaxOrOption(salesSlipDTO, salesItemList, csvImportDTO);

			/*
			 * 商品種別を保存し、KTS倉庫から出庫する商品の中に楽天倉庫から出庫する予定の商品がないか判断する。
			 * もしも、KTS倉庫から出庫する商品と楽天倉庫から出庫する予定の商品が混在していた場合は個口割れを
			 *  防ぐため、楽天倉庫から出庫する予定の商品をKTS倉庫から出庫する。
			 */
			List<String> itemClassificationList = new ArrayList<>();
			itemClassificationList.add(csvImportDTO.getItemClassification());


			//商品の後ろに送料などのデータが来る場合と
			//単純に商品が連続する場合がある。
			for (int j = i + 1; j < csvImportList.size(); j++) {

				CsvImportDTO csvImportDTOAfter = csvImportList.get(j);

				if (StringUtils.equals(salesSlipDTO.getOrderNo(), csvImportDTOAfter.getOrderNo())) {

					// 出庫する倉庫を判断するために商品種別を保管する。
					itemClassificationList.add(csvImportDTOAfter.getItemClassification());


					setSalesItemOrTaxOrOption(salesSlipDTO, salesItemList, csvImportDTOAfter);

					// 次のレコードが内訳商品であれば、今のレコードは「セット商品」となるのでその商品の楽天倉庫出荷フラグを立てる。
					if (StringUtils.equals(csvImportDTOAfter.getItemClassification(), "内訳商品")) {
						if (csvImportDTOAfter.getItemNm().contains("RSL出荷分")) {

							// KTS倉庫の内訳商品は商品として伝票に登録する。
							salesItemList.get(salesItemList.size() - 1).setRslLeaveFlag("1");
							salesSlipDTO.setExternalWarehouseCode("RSL");

						} else {
							// 内訳商品だが商品名に「RSL出荷分」が入っていないのでKTS倉庫商品と判断する。
							// 同一受注番号内にKTS倉庫商品が混在していた場合は、全ての商品をKTS倉庫商品とする。
							// KTS倉庫の内訳商品は商品として伝票に登録する。
							salesItemList.get(salesItemList.size() - 1).setRslLeaveFlag("0");
						}
					}

					//同じ受注番号のデータなので、次伝票の処理では除外する。

					csvImportList.remove(j--);
				}
			}

			/*****************************************************************************************************
			 *  一つの受注伝票の中にKTS倉庫の商品が存在していた場合は楽天倉庫の出庫からKTS倉庫の出庫へ切り替える。
			 *****************************************************************************************************/
			/*
			 * 商品種別による判断
			 */
			// XXX 楽天倉庫のデータに送料が入ってきたらその送料も楽天倉庫出荷フラグを立てる必要がある。
			// XXX 現時点では送料は入らないので、考慮した処理にはなっていない。
			if (itemClassificationList.contains("商品") && itemClassificationList.contains("セット商品")) {
				for (int j = 0; j < salesItemList.size(); j++) {
					if (salesItemList.get(j).getRslLeaveFlag() == "1") {
						salesItemList.get(j).setRslLeaveFlag("0");
					}
				}
			}

			/*
			 * 伝票内にKTS倉庫商品と楽天倉庫商品が混在していた場合
			 * 全てをKTS倉庫とする。
			 */
			for (ExtendSalesItemDTO item : salesItemList) {
				if (item.getRslLeaveFlag() == null || StringUtils.equals(item.getRslLeaveFlag(), "0")) {
					salesSlipDTO.setRslLeaveFlag("0");
					break;
				}

				salesSlipDTO.setRslLeaveFlag("1");
			}


			/*****************************************************************************************************
			 * キープがKTS倉庫、外部倉庫どちらにあるか判断する。
			 * どちらにもなければ受注時の判断とする。
			 *****************************************************************************************************/
			ItemDAO itemDAO = new ItemDAO();

			// キープが外部倉庫に存在していれば受注は外部倉庫とする。
			if (!CollectionUtils.isEmpty(itemDAO.getExternalKeepList(salesSlipDTO.getOrderNo(),salesSlipDTO.getExternalWarehouseCode()))) {
				salesSlipDTO.setRslLeaveFlag("1");
			}

			// キープがKTS倉庫にに存在していれば受注はKTS倉庫とする。
			if (!CollectionUtils.isEmpty(itemDAO.getKeepList(salesSlipDTO.getOrderNo()))) {
				salesSlipDTO.setRslLeaveFlag("0");
			}

			// KTS倉庫と外部倉庫どちらにもキープが存在していなければ受注データインポート時の判断に従う。


			// 全ての商品が楽天倉庫であれば、伝票の一言メモに追加する。
			if (StringUtils.equals(salesSlipDTO.getRslLeaveFlag(), "1")) {
				salesSlipDTO.setSenderMemo(salesSlipDTO.getSenderMemo() + rslMemo);
			} else {
				// 一つでもKTS倉庫が含まれていた場合、全ての商品をKTS倉庫商品とする。
				for (int j = 0; j < salesItemList.size(); j++) {
					if (salesItemList.get(j).getRslLeaveFlag() == "1") {
						salesItemList.get(j).setRslLeaveFlag("0");
					}
				}
			}

			//エラー判定
			csvErrorDTO = checkSalesSlip(salesSlipDTO, csvImportDTO.getFileNm(), csvErrorDTO, true, true);

			if (!csvErrorDTO.isSuccess()) {
				continue;
			}

			long sysSalesSlipId = new SequenceDAO().getMaxSysSalesSlipId() + 1;
			salesSlipDTO.setSysSalesSlipId(sysSalesSlipId);

			SaleDAO saleDAO = new SaleDAO();
			for (ExtendSalesItemDTO dto: salesItemList) {

				if (dto.getSysItemId() != 0
						&& salesSlipDTO.getSysCorporationId() != 0) {

					// 商品明細をテーブルに登録する。
					ItemCostDTO costDTO = itemDAO.getItemCostCSV(dto.getSysItemId(), salesSlipDTO.getSysCorporationId());
					dto.setCost(costDTO.getCost());
				}
				registrySaleItem(dto, sysSalesSlipId ,salesSlipDTO);
			}

			int sumPieceRate = getSumPieceRate(salesItemList);
			salesSlipDTO.setSumPieceRate(sumPieceRate);

			//内税の場合は消費税の計算を行う
			if (StringUtils.equals(salesSlipDTO.getTaxClass(), WebConst.TAX_CODE_INCLUDE)) {

				salesSlipDTO.setTax(getTax(sumPieceRate, salesSlipDTO.getTaxClass(), salesSlipDTO.getTaxRate()));
			}

			// 伝票をテーブルに登録する。
			csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + saleDAO.registrySaleSlip(salesSlipDTO));
		}

		return csvErrorDTO;
	}

	/**
	 * 伝票情報を格納します
	 *
	 * @param csvImportDTO
	 * @return
	 * @throws DaoException
	 * @throws ParseException
	 */
	private ExtendSalesSlipDTO setSalesSlipDTO(CsvImportDTO csvImportDTO) throws DaoException, ParseException {

		ExtendSalesSlipDTO salesSlipDTO = new ExtendSalesSlipDTO();

		long sysChannelId = new ChannelDAO().getChannelId(csvImportDTO.getOrderRoute());
		salesSlipDTO.setSysChannelId(sysChannelId);

		salesSlipDTO.setSysCorporationId(csvImportDTO.getSysCorporationId());
		//システム店舗ID
		salesSlipDTO.setOrderNo(csvImportDTO.getOrderNo());
		salesSlipDTO.setOrderDate(csvImportDTO.getOrderDate());
		salesSlipDTO.setOrderTime(csvImportDTO.getOrderTime());
		salesSlipDTO.setOrderFamilyNm(csvImportDTO.getOrderFamilyNm());
		salesSlipDTO.setOrderFirstNm(csvImportDTO.getOrderFirstNm());
		salesSlipDTO.setOrderFamilyNmKana(csvImportDTO.getOrderFamilyNmKana());
		salesSlipDTO.setOrderFirstNmKana(csvImportDTO.getOrderFirstNmKana());
		salesSlipDTO.setOrderMailAddress(csvImportDTO.getOrderMailAddress());
		salesSlipDTO.setOrderZip(csvImportDTO.getOrderZip());
		salesSlipDTO.setOrderPrefectures(csvImportDTO.getOrderPrefectures());
		salesSlipDTO.setOrderMunicipality(csvImportDTO.getOrderMunicipality());
		salesSlipDTO.setOrderAddress(csvImportDTO.getOrderAddress());
		salesSlipDTO.setOrderBuildingNm(csvImportDTO.getOrderBuildingNm());
		salesSlipDTO.setOrderCompanyNm(csvImportDTO.getOrderCompanyNm());
		salesSlipDTO.setOrderQuarter(csvImportDTO.getOrderQuarter());
		salesSlipDTO.setOrderTel(csvImportDTO.getOrderTel());
		salesSlipDTO.setAccountMethod(csvImportDTO.getAccountMethod());
		salesSlipDTO.setAccountCommission(csvImportDTO.getAccountCommission());

		salesSlipDTO.setMenberNo(csvImportDTO.getMenberNo());
		salesSlipDTO.setDepositDate(csvImportDTO.getDepositDate());
		salesSlipDTO.setDestinationFamilyNm(csvImportDTO.getDestinationFamilyNm());
		salesSlipDTO.setDestinationFirstNm(csvImportDTO.getDestinationFirstNm());
		salesSlipDTO.setDestinationFamilyNmKana(csvImportDTO.getDestinationFamilyNmKana());
		salesSlipDTO.setDestinationFirstNmKana(csvImportDTO.getDestinationFirstNmKana());
		salesSlipDTO.setDestinationZip(csvImportDTO.getDestinationZip());
		salesSlipDTO.setDestinationPrefectures(csvImportDTO.getDestinationPrefectures());
		salesSlipDTO.setDestinationMunicipality(csvImportDTO.getDestinationMunicipality());
		salesSlipDTO.setDestinationAddress(csvImportDTO.getDestinationAddress());
		salesSlipDTO.setDestinationBuildingNm(csvImportDTO.getDestinationBuildingNm());
		salesSlipDTO.setDestinationCompanyNm(csvImportDTO.getDestinationCompanyNm());
		salesSlipDTO.setDestinationQuarter(csvImportDTO.getDestinationQuarter());
		salesSlipDTO.setDestinationTel(csvImportDTO.getDestinationTel());
		salesSlipDTO.setSenderMemo(csvImportDTO.getSenderMemo());
		salesSlipDTO.setInvoiceClassification(csvImportDTO.getInvoiceClassification());
		salesSlipDTO.setSlipNo(csvImportDTO.getSlipNo());
		salesSlipDTO.setDestinationAppointDate(csvImportDTO.getDestinationAppointDate());
		//お届け時間帯の変換(なくなりました)BONCRE-2632
		salesSlipDTO.setDestinationAppointTime(csvImportDTO.getDestinationAppointTime());
		salesSlipDTO.setShipmentPlanDate(csvImportDTO.getShipmentPlanDate());
		salesSlipDTO.setTransportCorporationSystem(csvImportDTO.getTransportCorporationSystem());
		salesSlipDTO.setStatus(csvImportDTO.getLastStatus());

		//20140319追加　伊東 start
		salesSlipDTO.setTaxClass(WebConst.TAX_CODE_INCLUDE);
//		salesSlipDTO.setcsvImportDTO.getPieceRate();
		salesSlipDTO.setCashOnDeliveryCommission(csvImportDTO.getCashOnDeliveryCommission());
		salesSlipDTO.setSumClaimPrice(csvImportDTO.getSumClaimPrice());

		//税率判定
		if (StringUtils.isNotEmpty(csvImportDTO.getOrderDate())) {
			salesSlipDTO.setTaxRate(getTaxRate(csvImportDTO.getOrderDate()));
		}

		//保留
		//現時点で受注日が入っている前提での作成.
		//入っていない場合今日日付で判定
		else {
			salesSlipDTO.setTaxRate(getTaxRate(StringUtil.getToday()));
		}

		if (StringUtils.isNotEmpty(csvImportDTO.getInvoiceClassification())) {
			salesSlipDTO.setInvoiceClassification(switchInvoiceClassification(csvImportDTO.getInvoiceClassification()));
		}
		//20140319追加　伊東 end

		//送料などの値をセット
//		setOptionPrice(csvImportDTO, salesSlipDTO);

		//2014/03/12 八鍬追加
		salesSlipDTO.setOrderMemo(csvImportDTO.getOrderMemo());
		salesSlipDTO.setOrderRemarks(csvImportDTO.getOrderRemarks());
		salesSlipDTO.setOrderRemarksMemo(csvImportDTO.getOrderRemarksMemo());

		salesSlipDTO.setSenderRemarks(csvImportDTO.getSenderRemarks());

		salesSlipDTO.setUsedPoint(csvImportDTO.getUsedPoint());
		salesSlipDTO.setGetPoint(csvImportDTO.getGetPoint());
		salesSlipDTO.setRegistryStaff(csvImportDTO.getRegistryStaff());
		salesSlipDTO.setSlipMemo(csvImportDTO.getSlipMemo());

		salesSlipDTO.setDisposalRoute(csvImportDTO.getDisposalRoute());
		
		return salesSlipDTO;
	}



	//送り状種別読み替え
	private String switchInvoiceClassification(String invoiceClassification) {

		if (StringUtils.isEmpty(invoiceClassification)) {
			return StringUtils.EMPTY;
		}

		if (WebConst.INVOICE_CLASSIFICATION_CODE_1.indexOf(invoiceClassification) >= 0) {
			invoiceClassification = WebConst.INVOICE_CLASSIFICATION_NAME_1;
		} else if (WebConst.INVOICE_CLASSIFICATION_CODE_2.indexOf(invoiceClassification) >= 0) {
			invoiceClassification = WebConst.INVOICE_CLASSIFICATION_NAME_2;
		}

		return invoiceClassification;
	}

	//伝票上書き
	public ErrorDTO saveSlipNo(List<CsvImportDTO> csvImportList) throws DaoException, ParseException {

		ErrorDTO errorDTO = new ErrorDTO();
		//保留
		//一回目の取り込みとロジック似ているので切り出して使いたい。

		//csvファイルのままだと一つの受注番号で複数行あるのでそれを正規化するイメージ
		for (int i = 0; i < csvImportList.size(); i++) {

			CsvImportDTO csvImportDTO = csvImportList.get(i);

			//DBに格納する値
			ExtendSalesSlipDTO salesSlipDTO = setSalesSlipDTO(csvImportDTO);
			List<ExtendSalesItemDTO> salesItemList = new ArrayList<>();

			setSalesItemOrTaxOrOption(salesSlipDTO, salesItemList, csvImportDTO);
//			}

			//商品の後ろに送料などのデータが来る場合と
			//単純に商品が連続する場合がある。
			for (int j = i + 1; j < csvImportList.size(); j++) {

				CsvImportDTO csvImportDTOAfter = csvImportList.get(j);

				if (StringUtils.equals(salesSlipDTO.getOrderNo(), csvImportDTOAfter.getOrderNo())) {

					setSalesItemOrTaxOrOption(salesSlipDTO, salesItemList, csvImportDTOAfter);

					//同じ受注番号のデータは正規化したため除去
					csvImportList.remove(j--);
				}
			}

			long sysSalesSlipId = getSysSalesSlipId(salesSlipDTO.getOrderNo());

			//ありえないけど伝票IDが取得できない場合
//			if (sysSalesSlipId == 0) {
//				continue;
//			}
			salesSlipDTO.setSysSalesSlipId(sysSalesSlipId);

			checkSalesSlip(salesSlipDTO, csvImportDTO.getFileNm(), errorDTO, false, true);

			if (!errorDTO.isSuccess()) {
				continue;
			}

			SaleDAO saleDAO = new SaleDAO();

			for (ExtendSalesItemDTO dto: salesItemList) {

				dto.setSysSalesSlipId(sysSalesSlipId);

				long sysSalesItemId = getSysSalesItemId(dto.getSysSalesSlipId(), dto.getSysItemId(), dto.getItemCode());

				//新規で商品が増えた場合
				if (sysSalesItemId == 0) {
					registrySaleItem(dto, sysSalesSlipId, salesSlipDTO);
				} else {
					dto.setSysSalesItemId(sysSalesItemId);
					updateSalesItem(dto);
				}
			}

			int sumPieceRate = getSumPieceRate(salesItemList);
			salesSlipDTO.setSumPieceRate(sumPieceRate);
			salesSlipDTO.setTax(getTax(sumPieceRate, salesSlipDTO.getTaxClass(), salesSlipDTO.getTaxRate()));

			errorDTO.setTrueCount(errorDTO.getTrueCount() + saleDAO.updateSalesSlip(salesSlipDTO));
		}
		return errorDTO;
	}

	//伝票上書き
	/**
	 * @param csvImportList
	 * @return
	 * @throws DaoException
	 * @throws ParseException
	 */
	public ErrorDTO saveSlipNoOnly(List<CsvImportDTO> csvImportList) throws DaoException, ParseException {

		ErrorDTO errorDTO = new ErrorDTO();
		//保留
		//一回目の取り込みとロジック似ているので切り出して使いたい。

		System.out.println("SaleCsvService::saveSlipNoOnly() is called : (imported count) " + csvImportList.size());

		//csvファイルのままだと一つの受注番号で複数行あるのでそれを正規化するイメージ
		for (int i = 0; i < csvImportList.size(); i++) {

			CsvImportDTO csvImportDTO = csvImportList.get(i);

			// DBに格納する値 : for ExtendSalesSlipDTO
			ExtendSalesSlipDTO salesSlipDTO = getExtendSalesSlip(csvImportDTO.getOrderNo());
			if (salesSlipDTO != null) {
				// UPDATE : ExtendSalesSlipDTO ==== save jis, transport system.
				salesSlipDTO.setTransportCorporationSystem(csvImportDTO.getTransportCorporationSystem());
				salesSlipDTO.setSlipNo(csvImportDTO.getSlipNo());
	
				long sysSalesSlipId = getSysSalesSlipId(salesSlipDTO.getOrderNo());
				salesSlipDTO.setSysSalesSlipId(sysSalesSlipId);

				System.out.println("Found SaleSlip DTO: (id, orderno, slipno)" + 
							sysSalesSlipId + ":" + salesSlipDTO.getOrderNo() + ":" + salesSlipDTO.getSlipNo());
				
				SaleDAO saleDAO = new SaleDAO();
				errorDTO.setTrueCount(errorDTO.getTrueCount() + saleDAO.updateSalesSlip(salesSlipDTO));
			}
			
			// For CorporateSalesSlipDto
			List<ExtendCorporateSalesSlipDTO> list = getCorporateSaleSlipByOrderNo(csvImportDTO.getOrderNo());
			if (list != null) {
				
				for (int j=0; j< list.size(); j++) {
					ExtendCorporateSalesSlipDTO corpSalesSlipDTO = list.get(j);
					
					// update : ExtendCorporateSalesSlipDTO ==== save jis, transport system
					corpSalesSlipDTO.setTransportCorporationSystem(csvImportDTO.getTransportCorporationSystem());
					corpSalesSlipDTO.setSlipNo(csvImportDTO.getSlipNo());
					
					long sysCorpSalesSlipId = corpSalesSlipDTO.getSysCorporateSalesSlipId();
					corpSalesSlipDTO.setSysCorporateSalesSlipId(sysCorpSalesSlipId);
					
					System.out.println("Found CorpSaleSlip DTO: (id, orderno, slipno)" + 
							sysCorpSalesSlipId + ":" + corpSalesSlipDTO.getOrderNo() + ":" + corpSalesSlipDTO.getSlipNo());

					CorporateSaleDAO corpSaleDAO = new CorporateSaleDAO();
					errorDTO.setTrueCount(errorDTO.getTrueCount() + corpSaleDAO.updateCorporateSalesSlip(corpSalesSlipDTO));					
				}
				
			}

		}
		return errorDTO;
	}

	/**
	 * 商品か消費税かそれ以外かを判定し売上情報を格納します
	 *
	 * @param salesSlipDTO
	 * @param salesItemList
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void setSalesItemOrTaxOrOption(ExtendSalesSlipDTO salesSlipDTO,
			List<ExtendSalesItemDTO> salesItemList, CsvImportDTO csvImportDTO) throws DaoException {

		//商品情報を格納
		if (StringUtils.equals(csvImportDTO.getItemClassification(), "商品")) {

			salesItemList.add(setSalesItemDTO(csvImportDTO));

		} else if (StringUtils.equals(csvImportDTO.getItemClassification(), "消費税")) {

			setTax(csvImportDTO, salesSlipDTO);

		} else if (StringUtils.equals(csvImportDTO.getItemClassification(), "セット商品")) {

			salesItemList.add(setSalesItemDTO(csvImportDTO));

		} else if (StringUtils.equals(csvImportDTO.getItemClassification(), "内訳商品")) {

			//XXX 内訳商品の場合は、伝票に登録しないので特に処理なし。

		} else {
			//送料などの値をセット
			setOptionPrice(csvImportDTO, salesSlipDTO);
		}

	}
	/**
	 * CSVインポート時に消費税が含まれていた場合の処理
	 * 消費税をセットします。
	 * @param csvImportDTO
	 * @param salesSlipDTO
	 */
	private void setTax(CsvImportDTO csvImportDTO, ExtendSalesSlipDTO salesSlipDTO) {

		//
		if (StringUtils.equals(salesSlipDTO.getTaxClass(), WebConst.TAX_CODE_INCLUDE)) {

			salesSlipDTO.setTaxClass(WebConst.TAX_CODE_EXCLUSIVE);
			salesSlipDTO.setTax(0);
		}

		salesSlipDTO.setTax(salesSlipDTO.getTax() + csvImportDTO.getPieceRate());
	}

	private long getSysSalesItemId(long sysSalesSlipId, long sysItemId, String itemCode) throws DaoException {

		ExtendSalesItemDTO dto = new ExtendSalesItemDTO();
		dto.setSysSalesSlipId(sysSalesSlipId);

		if (sysItemId != 0) {

			dto.setSysItemId(sysItemId);

		} else if (StringUtils.isNotEmpty(itemCode)) {

			dto.setItemCode(itemCode);
		}

		dto = new SaleDAO().getSalesItemDTO(dto);

		if (dto == null) {

			return 0;
		}
		return dto.getSysSalesItemId();
	}

	/**
	 * 商品情報を格納し、返却します。
	 *
	 * @param csvImportDTO
	 * @return
	 * @throws DaoException
	 */
	private ExtendSalesItemDTO setSalesItemDTO(CsvImportDTO csvImportDTO) throws DaoException {

		ExtendSalesItemDTO salesItemDTO = new ExtendSalesItemDTO();

		salesItemDTO.setItemNm(csvImportDTO.getItemNm());
		salesItemDTO.setPieceRate(csvImportDTO.getPieceRate());
		salesItemDTO.setOrderNum(csvImportDTO.getItemNum());


		if (StringUtils.isEmpty(csvImportDTO.getShopItemCd())) {
			return salesItemDTO;
		}

		//20140315 伊東敦史 商品コードカラム追加
//		salesItemDTO.setItemCode(csvImportDTO.getShopItemCd());

		ItemService itemService = new ItemService();
		//マスタにある商品の場合IDを格納
		long sysItemId = itemService.getSysItemIdFromShopCd(csvImportDTO.getShopItemCd());
		String shopItemCd = itemService.getItemCd(csvImportDTO.getShopItemCd());
		if (sysItemId == 0) {

			sysItemId = itemService.getSysItemIdFromShopCd(shopItemCd);

			//旧品番で検索かけた場合でもシステムIDが取れない場合
			if (sysItemId == 0) {

				shopItemCd = csvImportDTO.getShopItemCd();
				//TODO 国内商品を紐づける処理を追記
			}

		} else {

//			shopItemCd = csvImportDTO.getShopItemCd();
			//TODO 国内商品を紐づける処理を追記
		}
		//20140315 伊東敦史 商品コードカラム追加
		salesItemDTO.setItemCode(shopItemCd);
		salesItemDTO.setSysItemId(sysItemId);

		return salesItemDTO;
	}

	/**
	 * 伝票情報にある商品をリスト化して返却します。
	 * @param csvImportDTO
	 * @return salesItemList
	 * @throws Exception
	 */
	public void getSlipItemList(List<ExtendSalesItemDTO> salesItemList, List<CsvImportDTO> csvImportList) throws  Exception {

//			//DBに格納する値
//			ExtendSalesSlipDTO salesSlipDTO = setSalesSlipDTO(csvImportDTO);
//			List<ExtendSalesItemDTO> salesItemList = new ArrayList<>();
//
//			setSalesItemOrTaxOrOption(salesSlipDTO, salesItemList, csvImportDTO);
		//csvファイルのままだと一つの受注番号で複数行あるのでそれを正規化するイメージ
		for (int i = 0; i < csvImportList.size(); i++) {

			CsvImportDTO csvImportDTO = csvImportList.get(i);

			//DBに格納する値
			ExtendSalesSlipDTO salesSlipDTO = setSalesSlipDTO(csvImportDTO);

			setSalesItemOrTaxOrOption(salesSlipDTO, salesItemList, csvImportDTO);

			//商品の後ろに送料などのデータが来る場合と
			//単純に商品が連続する場合がある。
			for (int j = i + 1; j < csvImportList.size(); j++) {

				CsvImportDTO csvImportDTOAfter = csvImportList.get(j);

				if (StringUtils.equals(salesSlipDTO.getOrderNo(), csvImportDTOAfter.getOrderNo())) {

					setSalesItemOrTaxOrOption(salesSlipDTO, salesItemList, csvImportDTOAfter);

					//同じ受注番号のデータは正規化したため除去
					csvImportList.remove(j--);
				}
			}
		}
	}

	private void setOptionPrice(CsvImportDTO csvImportDTO,
			SalesSlipDTO salesSlipDTO) {

		if (StringUtils.equals(csvImportDTO.getItemClassification(), "送料")) {

			salesSlipDTO.setPostage(salesSlipDTO.getPostage() + csvImportDTO.getPieceRate());

		} else if (StringUtils.equals(csvImportDTO.getItemClassification(), "代引き手数料")) {

			salesSlipDTO.setCodCommission(salesSlipDTO.getCodCommission() + csvImportDTO.getPieceRate());

		} else if (StringUtils.equals(csvImportDTO.getItemClassification(), "消費税")) {

			salesSlipDTO.setConsumptionTax(salesSlipDTO.getConsumptionTax() + csvImportDTO.getPieceRate());

		} else if (StringUtils.equals(csvImportDTO.getItemClassification(), "非商品")) {

			salesSlipDTO.setDiscommondity(salesSlipDTO.getDiscommondity() + csvImportDTO.getPieceRate());

		} else if (StringUtils.equals(csvImportDTO.getItemClassification(), "ギフト")) {

			salesSlipDTO.setGift(salesSlipDTO.getGift() + csvImportDTO.getPieceRate());

		}
	}


	//伝票番号のみ付与
	public ErrorDTO addSlipNo(List<CsvImportDTO> csvImportList) throws DaoException {

		ErrorDTO errorDTO = new ErrorDTO();
//		for (CsvImportDTO dto: csvImportList) {
		for (int i = 0; i < csvImportList.size(); i++) {

			CsvImportDTO csvImportDTO = csvImportList.get(i);
			for (int j = i + 1; j < csvImportList.size(); j++) {
				errorDTO.setSuccess(true);

				CsvImportDTO afterCsvImportDTO = csvImportList.get(j);
				//同じ受注番号が複数行ある場合があるので、あった場合は除去
				if (StringUtils.equals(csvImportDTO.getOrderNo(), afterCsvImportDTO.getOrderNo())) {
					csvImportList.remove(j--);
				}
			}

			ExtendSalesSlipDTO salesSlipDTO = new ExtendSalesSlipDTO();
//			salesSlipDTO.setSysSalesSlipId(getSysSalesSlipId(salesSlipDTO.getOrderNo()));
			salesSlipDTO.setOrderNo(csvImportDTO.getOrderNo());
			salesSlipDTO.setSysSalesSlipId(getSysSalesSlipId(csvImportDTO.getOrderNo()));
			salesSlipDTO.setSlipNo(csvImportDTO.getSlipNo());

			errorDTO = checkSalesSlip(salesSlipDTO, csvImportDTO.getFileNm(), errorDTO, false, true);

			if (!errorDTO.isSuccess()) {
				continue;
			}

			//伝票番号（送り状番号）が入っていない場合
			if (StringUtils.isEmpty(csvImportDTO.getSlipNo())) {

				errorDTO.setSuccess(false);
				errorDTO.setFileName(csvImportDTO.getFileNm());
				ErrorMessageDTO messageDTO = new ErrorMessageDTO();
				messageDTO.setErrorMessage("受注番号" + csvImportDTO.getOrderNo() + "に伝票番号が入っていません");
				errorDTO.getErrorMessageList().add(messageDTO);
				continue;
			}

			errorDTO.setTrueCount(errorDTO.getTrueCount() + updateAddSlipNo(salesSlipDTO));
		}
		return errorDTO;
	}

	private int updateAddSlipNo(ExtendSalesSlipDTO dto) throws DaoException {

		return new SaleDAO().updateAddSlipNo(dto.getSysSalesSlipId(), dto.getSlipNo());
	}

	private long getSysSalesSlipId(String orderNo) throws DaoException {

		ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO();
		dto.setOrderNo(orderNo);
		dto = new SaleDAO().getSaleSlip(dto);

		if (dto == null) {

			return 0;
		}

		return dto.getSysSalesSlipId();
	}
	
	private ExtendSalesSlipDTO getExtendSalesSlip(String orderNo) throws DaoException {
		ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO();
		dto.setOrderNo(orderNo);
		dto = new SaleDAO().getSaleSlip(dto);

		if (dto == null) {

			return null;
		}

		return dto;		
	}
	
//	private long getSysCorporateSaleSlipId(String orderNo) throws DaoException {
//		ExtendCorporateSalesSlipDTO dto = new ExtendCorporateSalesSlipDTO();
//		dto.setOrderNo(orderNo);
//		dto = new CorporateSaleDAO().getCorporateSaleSlipByOrderNo(orderNo);
//		
//		if (dto == null) {
//			
//			return 0;
//		}
//		
//		return dto.getSysCorporateSalesSlipId();
//	}
	

	private List<ExtendCorporateSalesSlipDTO> getCorporateSaleSlipByOrderNo(String orderNo) throws DaoException {
		ExtendCorporateSalesSlipDTO dto = new ExtendCorporateSalesSlipDTO();
		dto.setOrderNo(orderNo);
			
		List<ExtendCorporateSalesSlipDTO> list = new CorporateSaleDAO().getCorporateSaleSlipByOrderNo(orderNo);
		
		if (list == null) {
			
			return null;
		}
		
		return list;
	}


	private ErrorDTO checkSalesSlip(ExtendSalesSlipDTO salesSlipDTO, String fileNm, ErrorDTO csvErrorDTO, boolean orderNoCheck, boolean channelIdCheck) throws DaoException {

		csvErrorDTO.setSuccess(true);

		if (orderNoCheck) {
			if (StringUtils.isNotEmpty(exsistOrderNo(salesSlipDTO.getOrderNo()))) {

				csvErrorDTO.setSuccess(false);
				csvErrorDTO.setFileName(fileNm);
				ErrorMessageDTO messageDTO = new ErrorMessageDTO();
				messageDTO.setErrorMessage("受注番号" + salesSlipDTO.getOrderNo() + "はインポート済みです");
				csvErrorDTO.getErrorMessageList().add(messageDTO);
			}
		} else {

			//取り込んだことの無い受注番号だった場合
			if (StringUtils.isEmpty(exsistOrderNo(salesSlipDTO.getOrderNo()))) {

				csvErrorDTO.setSuccess(false);
				csvErrorDTO.setFileName(fileNm);
				ErrorMessageDTO messageDTO = new ErrorMessageDTO();
				messageDTO.setErrorMessage("受注番号" + salesSlipDTO.getOrderNo() + "の売上伝票はありません");
				csvErrorDTO.getErrorMessageList().add(messageDTO);
			}
		}

		if (salesSlipDTO.getSysChannelId() == 0) {

			csvErrorDTO.setSuccess(false);
			csvErrorDTO.setFileName(fileNm);
			ErrorMessageDTO messageDTO = new ErrorMessageDTO();
			messageDTO.setErrorMessage("受注番号" + salesSlipDTO.getOrderNo() + "の受注ルート（販売チャネル）が取得できません");
			csvErrorDTO.getErrorMessageList().add(messageDTO);
		}

		//出庫予定日が空欄だった場合
		if (StringUtils.isEmpty(salesSlipDTO.getShipmentPlanDate())) {

			csvErrorDTO.setSuccess(false);
			csvErrorDTO.setFileName(fileNm);
			ErrorMessageDTO messageDTO = new ErrorMessageDTO();
			messageDTO.setErrorMessage("受注番号" + salesSlipDTO.getOrderNo() + "の出庫予定日が入力されていません");
			csvErrorDTO.getErrorMessageList().add(messageDTO);
		}
		return csvErrorDTO;
	}
}
