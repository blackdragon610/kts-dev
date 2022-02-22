package jp.co.kts.service.fileImport;

import java.io.Console;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.util.csv.CsvConfig;
import jp.co.keyaki.cleave.util.csv.CsvContext;
import jp.co.keyaki.cleave.util.csv.CsvReader;
import jp.co.keyaki.cleave.util.csv.CsvRecord;
import jp.co.kts.app.common.entity.ArrivalScheduleDTO;
import jp.co.kts.app.common.entity.CsvImportDTO;
import jp.co.kts.app.common.entity.DomesticCsvImportDTO;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.DomesticOrderItemDTO;
import jp.co.kts.app.common.entity.DomesticOrderSlipDTO;
import jp.co.kts.app.common.entity.ExtendKeepCsvImportDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.input.entity.CsvInputDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.fileImport.CsvImportDAO;
import jp.co.kts.dao.fileImport.DomesticCsvImportDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.ChannelDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.mst.DomesticOrderDAO;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.DomesticOrderService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.ui.web.struts.WebConst;

public class CsvImportService {

	/**
	 * インポートするCSVファイルが既に登録されているか確認
	 * 登録済み：エラー
	 * 未登録：新規で登録を行う
	 * @param corporationId
	 * @param fileUp
	 * @param csvImportList
	 * @return
	 * @throws Exception
	 */
	public ErrorDTO importFile(long corporationId, FormFile fileUp, List<CsvImportDTO> csvImportList) throws Exception {

		InputStream inputStream = fileUp.getInputStream();
		ErrorDTO csvErrorDTO = new ErrorDTO();

//		int messageIdx = 0;
		if (StringUtils.isNotEmpty(exsistFileNm(fileUp.getFileName()))) {

			csvErrorDTO.setSuccess(false);
			csvErrorDTO.setFileName("「" + fileUp.getFileName() + "」はすでにインポート済みファイルです。");
			return csvErrorDTO;
		}

		CsvConfig config = new CsvConfig();
		CsvReader reader = new CsvReader(config, true);
		CsvContext context = reader.parse(inputStream);

		for (CsvRecord csvRecord : context) {

			String[] csvLineArray = csvRecord.toArray();

			CsvImportDTO csvImportDTO = new CsvImportDTO();
			csvImportDTO = setCsvImportList(csvLineArray);

			csvImportDTO.setOrderRemarksMemo(csvImportDTO.getOrderMemo() + "\r\n" + csvImportDTO.getOrderRemarks());

			csvImportDTO.setFileNm(fileUp.getFileName());

			csvImportDTO.setSysImportId((new SequenceDAO().getMaxSysImportId() + 1));
			csvImportDTO.setSysCorporationId(corporationId);

			//登録実行
			new CsvImportDAO().registryCsvImport(csvImportDTO);
			//伝票用Listに格納
			csvImportList.add(csvImportDTO);
		}
		return csvErrorDTO;
	}

	/**
	 * インポートファイルが既に存在するか確認
	 * @param fileNm
	 * @return
	 * @throws DaoException
	 */
	private String exsistFileNm(String fileNm) throws DaoException {

		fileNm = StringUtils.substring(fileNm, StringUtils.lastIndexOf(fileNm, "\\") + 1);

		CsvImportDAO dao = new CsvImportDAO();

		CsvImportDTO dto = new CsvImportDTO();
		dto = dao.getCsvFileNm(fileNm);

		if (dto == null || StringUtils.isEmpty(dto.getFileNm())) {

			return null;
		}

		return dto.getFileNm();
	}


	public List<ErrorDTO> importFileList(List<CsvInputDTO> csvInputList, List<CsvImportDTO> csvImportList) throws Exception {



		List<ErrorDTO> csvErrorList = new ArrayList<>();


		List<CsvInputDTO> checkedCsvInputList = new ArrayList<>();
		//csv
		csvErrorList = csvCheck(csvInputList, checkedCsvInputList);

		if (csvErrorList == null || csvErrorList.size() > 0) {

			return csvErrorList;
		}

		csvImportList = new ArrayList<>();
		for (CsvInputDTO csvInputDTO: checkedCsvInputList) {
			//インポート実行
			csvErrorList.add(importFile(csvInputDTO.getCorporationId(), csvInputDTO.getFileUp(), csvImportList));
		}


		for (ErrorDTO dto: csvErrorList) {

			if (!dto.isSuccess()) {
				return csvErrorList;
			}
		}

		return csvErrorList;
	}

	/**エラーチェック
	 *
	 * @param csvInputList
	 * @return
	 * @throws DaoException
	 */
	public List<ErrorDTO> csvCheck(List<CsvInputDTO> csvInputList, List<CsvInputDTO> checkedCsvInputList) throws DaoException {

		List<ErrorDTO> errorList = new ArrayList<>();
		//エラー判定
		for (int i = 0; i < csvInputList.size(); i ++) {

			CsvInputDTO csvInputDTO = csvInputList.get(i);

			if (StringUtils.isEmpty(csvInputDTO.getFileUp().getFileName())) {
				continue;
			}

			csvInputDTO.setCorporationId(getCorporationId(csvInputDTO.getFileUp().getFileName()));

			checkedCsvInputList.add(csvInputDTO);
		}

		return errorList;
	}

	private CsvImportDTO setCsvImportList(String[] array) throws IllegalAccessException, Exception {
		CsvImportDTO csvImportDTO = new CsvImportDTO();

		int i = 0;

		BeanUtils.setProperty(csvImportDTO,"dataDivision", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderRoute", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderNo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderDate", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderTime", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderFamilyNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderFirstNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderFamilyNmKana", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderFirstNmKana", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderMailAddress", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderZip", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderPrefectures", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderMunicipality", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderAddress", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderBuildingNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderCompanyNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderQuarter", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderTel", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"accountMethod", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"accountCommission", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"usedPoint", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"getPoint", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderRemarks", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"orderMemo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"sumClaimPrice", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"menberNo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"depositDate", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"registryStaff", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationDivision", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationFamilyNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationFirstNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationFamilyNmKana", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationFirstNmKana", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationZip", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationPrefectures", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationMunicipality", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationAddress", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationBuildingNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationCompanyNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationQuarter", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationTel", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderDivision", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderFamilyNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderFirstNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderFamilyNmKana", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderFirstNmKana", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderZip", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderPrefectures", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderMunicipality", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderAddress", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderBuildingNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderCompanyNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderQuarter", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderTel", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderRemarks", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"senderMemo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"giftMessage", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"slipDivision", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"invoiceClassification", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"slipNo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"cashOnDeliveryCommission", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"temperatureDivision", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationAppointDate", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"destinationAppointTime", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"shipmentPlanDate", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"transportCorporationSystem", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"slipMemo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"lastStatus", array[i++]);			//BP
		BeanUtils.setProperty(csvImportDTO,"reservationStatus", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"combineSource", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"combinePoint", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"itemDivision", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"itemClassification", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"shopItemCd", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"itemNm", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"itemNum", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"pieceRate", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"optionSukenekoOne", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"optionSukenekoTwo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"sukenekoItemCd", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"unpaidPrice", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"invoiceArticle", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"slipManagementNo", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"disposalRoute", array[i++]);		// cf
		BeanUtils.setProperty(csvImportDTO,"disposalDate", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"ownCompanyCd", array[i++]);
		BeanUtils.setProperty(csvImportDTO,"buyCount", array[i++]);

		return csvImportDTO;

	}

	public List<CsvInputDTO> initCsvInputList() {

		List<CsvInputDTO> list = new ArrayList<>();

		for (int i = 0; i < WebConst.CSV_INPUT_LENGTH; i++) {
			list.add(new CsvInputDTO());
		}

		return list;
	}

	public long getCorporationId(String csvFileName) throws DaoException {

		long corporationId = 0;

		String[] csvFileNames = StringUtils.split(csvFileName, "_");
		//今後の変更用に文字列結合を使用
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (true) {

			//ファイル名に先頭文字OSが含まれない場合
			if (csvFileNames.length <= i) {

				break;
			}

			//ファイル名の先頭文字OSの場合
			if (StringUtils.equals(csvFileNames[i], WebConst.CSV_FILE_NAME_HEADER)) {
				sb.append(csvFileNames[i + 1]);

				//CSVタイトルから会社IDを取得

				// CSVタイトルが「bcr」の場合、「syarakuin」で検索
				if(sb.toString().equals("bcr")){
					corporationId = new CorporationService().getCorporationIdFromName("syarakuin");

				// CSVタイトルが「kts-2」の場合、「kts」で検索
				} else if (sb.toString().equals("kts-2")) {
					corporationId = new CorporationService().getCorporationIdFromName("kts");
				} else {
					corporationId = new CorporationService().getCorporationIdFromName(sb.toString());
				}

				break;
			}
			i++;
		}
		return corporationId;
	}

	public ErrorDTO getFileNmError(String csvFileName) {

		ErrorDTO dto = new ErrorDTO();

		dto.setSuccess(false);
		dto.setFileName(csvFileName + "のファイル名の体裁が間違っています");
		return dto;
	}

	public boolean isSuccess(List<ErrorDTO> csvErrorList) {

		for (ErrorDTO dto: csvErrorList) {
			//一つでも失敗があれば取り込みキャンセル
			if (!dto.isSuccess()) {

				return false;
			}
		}
		return true;
	}

	/**
	 * 国内注文取込時ファイルが登録されているか確認する。
	 * @param corporationId
	 * @param fileUp
	 * @param csvImportList
	 * @return
	 * @throws Exception
	 */
	public ErrorDTO importDomesticFile(long corporationId, FormFile fileUp, List<CsvImportDTO> csvImportList) throws Exception {

		InputStream inputStream = fileUp.getInputStream();
		ErrorDTO csvErrorDTO = new ErrorDTO();

		CsvConfig config = new CsvConfig();
		CsvReader reader = new CsvReader(config, true);
		CsvContext context = reader.parse(inputStream);


//		int messageIdx = 0;
		if (StringUtils.isNotEmpty(exsistDomesticFileNm(fileUp.getFileName()))) {

			csvErrorDTO.setSuccess(false);
			csvErrorDTO.setFileName("「" + fileUp.getFileName() + "」はすでにインポート済みファイルです。");
			return csvErrorDTO;
		}

		for (CsvRecord csvRecord : context) {

			String[] csvLineArray = csvRecord.toArray();

			CsvImportDTO csvImportDTO = new CsvImportDTO();
			csvImportDTO = setCsvImportList(csvLineArray);

			csvImportDTO.setOrderRemarksMemo(csvImportDTO.getOrderMemo() + "\r\n" + csvImportDTO.getOrderRemarks());

			csvImportDTO.setFileNm(fileUp.getFileName());

			csvImportDTO.setSysImportId((new SequenceDAO().getMaxSysDomesticImportId() + 1));

			csvImportDTO.setSysCorporationId(corporationId);

			//登録実行
			new DomesticCsvImportDAO().registryDomesticCsvImport(csvImportDTO);

//			//登録実行
			//伝票用Listに格納
			csvImportList.add(csvImportDTO);
		}

		return csvErrorDTO;
	}


	/**
	 * CSVファイルを国内注文伝票に読み替え登録を行います
	 *
	 * @param csvImportList
	 * @return
	 * @throws Exception
	 */
	public ErrorDTO csvToDomesticSlip(List<CsvImportDTO> csvImportList) throws Exception {

		//インスタンス生成
		DomesticExhibitionDTO exhibitionDto = new DomesticExhibitionDTO();
		DomesticExhibitionDTO exhbyRsltDto = new DomesticExhibitionDTO();
		DomesticExhibitionDAO exhibitionDao = new DomesticExhibitionDAO();
		ItemService itemService = new ItemService();
		ErrorMessageDTO messageDTO = new ErrorMessageDTO();
		//商品コード(店舗)の文字列を保持して置く→納入先を振り分ける際に使用する
		String itemDeliveryType = "";
		int resultCnt = 0;

		ErrorDTO csvErrorDTO = new ErrorDTO();
		csvErrorDTO.setSuccess(true);
		if (csvImportList == null) {
			csvErrorDTO.setSuccess(false);
			messageDTO.setErrorMessage("予期せぬエラー!!");
			csvErrorDTO.getErrorMessageList().add(messageDTO);
			return csvErrorDTO;
		}

		//csvファイルのままだと一つの受注番号で複数行あるのでそれを正規化するイメージs
		for (int i = 0; i < csvImportList.size(); i++) {

			CsvImportDTO csvImportDTO = csvImportList.get(i);

			//DBに格納する値
			ExtendDomesticOrderSlipDTO domesticDto = setDomesticSlipDTO(csvImportDTO, itemDeliveryType);
			List<DomesticOrderItemDTO> orderItemList = new ArrayList<DomesticOrderItemDTO>();

			if (StringUtils.equals(csvImportDTO.getItemClassification(), "商品")) {
				long sysItemId = itemService.getSysItemIdFromShopCd(csvImportDTO.getShopItemCd());
				String shopItemCd = itemService.getItemCd(csvImportDTO.getShopItemCd());
				if (sysItemId == 0) {
					sysItemId = itemService.getSysItemIdFromShopCd(shopItemCd);
					if (sysItemId == 0) {
						//受注番号内の1行目の格納処理
						csvErrorDTO = setDomesticItemOrTaxOrOption(csvErrorDTO, orderItemList, csvImportDTO);
					} else {
						continue;
					}
				} else {
					continue;
				}
			} else {
				continue;
			}

			if (!csvErrorDTO.isSuccess()){
				csvImportList.remove(i--);
				continue;
			}
			itemDeliveryType = csvImportDTO.getShopItemCd();


			//商品の後ろに送料などのデータが来る場合と
			//単純に商品が連続する場合がある。
			for (int j = i + 1; j < csvImportList.size(); j++) {
				CsvImportDTO csvImportDTOAfter = csvImportList.get(j);

				//商品マスタにある商品の場合何もしない

				if (StringUtils.equals(domesticDto.getOrderNo(), csvImportDTOAfter.getOrderNo())) {

					if (StringUtils.equals(csvImportDTOAfter.getItemClassification(), "商品")) {

						long sysItemId = itemService.getSysItemIdFromShopCd(csvImportDTOAfter.getShopItemCd());
						String shopItemCd = itemService.getItemCd(csvImportDTOAfter.getShopItemCd());
						if (sysItemId == 0) {
							sysItemId = itemService.getSysItemIdFromShopCd(shopItemCd);
							if (sysItemId == 0) {
								//問屋毎に伝票を分けるため検索を実行する
								exhibitionDto.setItemNm(csvImportDTOAfter.getItemNm());
								exhibitionDto.setManagementCode(csvImportDTOAfter.getShopItemCd());
								exhbyRsltDto = exhibitionDao.getDomesticItem(exhibitionDto);

								if (exhbyRsltDto == null) {
									ErrorMessageDTO erroeMessage = new ErrorMessageDTO();
									erroeMessage.setErrorMessage("受注番号 " + csvImportDTO.getOrderNo() + "　の品番　　" +  csvImportDTO.getShopItemCd() + "が出品DB未登録の為登録されませんでした。");
									csvErrorDTO.getErrorMessageList().add(erroeMessage);
									csvImportList.remove(j--);
									continue;
								}

								if (!csvErrorDTO.isSuccess()){
									csvImportList.remove(j--);
									continue;
								}

								String wholsesalerNm = "";
								//1行目問屋名
								if (orderItemList.size() != 0) {
									wholsesalerNm = orderItemList.get(orderItemList.size() - 1).getWholsesalerNm();
								}

								if (wholsesalerNm == null) {
									wholsesalerNm = StringUtils.EMPTY;
								}
								//2行目以降の問屋名
								String afterwholsesalerNm = exhbyRsltDto.getWholsesalerNm();

								//問屋が違えば伝票を分ける
								if (wholsesalerNm.equals(afterwholsesalerNm)) {

										//受注番号内の1行目以降の格納処理
										csvErrorDTO = setDomesticItemOrTaxOrOption(csvErrorDTO, orderItemList, csvImportDTOAfter);

										if (!csvErrorDTO.isSuccess()) {
											csvErrorDTO.setSuccess(false);
											continue;
										}

										//同じ受注番号、問屋、メーカーのデータは正規化したため除去
										csvImportList.remove(j--);
								}
							}
						}
					}
				}
			}

			//エラー判定
			if (!csvErrorDTO.isSuccess()) {
				csvErrorDTO.setSuccess(false);
				continue;
			}

			//伝票の作成
			DomesticOrderService dao = new DomesticOrderService();
			domesticDto = dao.registryDomesticOrderSlipCsv(domesticDto);
			long sysDomesticOrderId = domesticDto.getSysDomesticSlipId();

			//国内注文商品の作成
			resultCnt += dao.registryDomesticOrderItemList(orderItemList, sysDomesticOrderId);
		}

		//国内商品が一件もない場合
		if (csvErrorDTO.getTrueCount() == 0) {
			messageDTO.setErrorMessage("国内商品が存在しないCSVファイルです。");
			csvErrorDTO.getErrorMessageList().add(messageDTO);
		}

		//注文商品が作成されなかった場合
		if (resultCnt == 0) {
			csvErrorDTO.setSuccess(false);
		}

		if (csvErrorDTO.getErrorMessageList().size() > 0) {
			csvErrorDTO.setSuccess(false);
		}

		return csvErrorDTO;
	}

	/**
	 * 伝票情報を格納します
	 *
	 * @param csvImportDTO
	 * @return
	 * @throws Exception
	 */
	private ExtendDomesticOrderSlipDTO setDomesticSlipDTO(CsvImportDTO csvImportDTO, String itemDeliveryType) throws Exception {

		ExtendDomesticOrderSlipDTO domesticDto = new ExtendDomesticOrderSlipDTO();
		DomesticOrderSlipDTO slipDto = new DomesticOrderSlipDTO();
		DomesticOrderSlipDTO warehouseDto = new DomesticOrderSlipDTO();
		warehouseDto =  new DomesticOrderDAO().getDomesticWarehouse(1);

		//チャンネルID
		long sysChannelId = new ChannelDAO().getChannelId(csvImportDTO.getOrderRoute());
		slipDto = getMallId(sysChannelId, csvImportDTO.getSysCorporationId());
		//伝票情報格納
		domesticDto.setItemOrderDate(csvImportDTO.getOrderDate());
		domesticDto.setSysCorporationId(slipDto.getSysCorporationId());
		domesticDto.setMall(slipDto.getMall());
		domesticDto.setOrderNo(csvImportDTO.getOrderNo());
		domesticDto.setNoteTurn(slipDto.getNoteTurn());


		//決済方法が「代引き以外」かつ「商品コード(店舗)」に下記いずれかの文字列が含まれている場合
		//納入先をCSVに入力されているお届け先情報を納入先として登録する
		if (!csvImportDTO.getAccountMethod().equals("代金引換")
				&& (itemDeliveryType.contains(ServiceConst.ITEM_CODE_SHOP_1)
				|| itemDeliveryType.contains(ServiceConst.ITEM_CODE_SHOP_2)
				|| itemDeliveryType.contains(ServiceConst.ITEM_CODE_SHOP_3)
				|| itemDeliveryType.contains(ServiceConst.ITEM_CODE_SHOP_4))) {
			domesticDto.setSysWarehouseId(99);
			domesticDto.setWarehouseNm("その他");
			domesticDto.setZip(csvImportDTO.getDestinationZip());
			domesticDto.setAddressFst(csvImportDTO.getDestinationPrefectures());
			domesticDto.setAddressNxt(csvImportDTO.getDestinationMunicipality());
			domesticDto.setAddressNxt2(csvImportDTO.getDestinationAddress());
			domesticDto.setTellNo(csvImportDTO.getDestinationTel());
			domesticDto.setLogisticNm(csvImportDTO.getDestinationFamilyNm() + csvImportDTO.getDestinationFirstNm());

		} else {
			domesticDto.setSysWarehouseId(1);
			domesticDto.setWarehouseNm(warehouseDto.getWarehouseNm());
			domesticDto.setZip(warehouseDto.getZip());
			domesticDto.setAddressFst(warehouseDto.getAddressFst());
			domesticDto.setAddressNxt(warehouseDto.getAddressNxt());
			domesticDto.setAddressNxt2(warehouseDto.getAddressNxt2());
			domesticDto.setTellNo(warehouseDto.getTellNo());
			domesticDto.setLogisticNm(warehouseDto.getLogisticNm());
		}
		domesticDto.setSenderRemarks(csvImportDTO.getOrderFamilyNm() + " " + csvImportDTO.getOrderFirstNm() + "様 発注分");

		return domesticDto;
	}

	/**
	 * 国内注文伝票のモールと注番を設定するサービス
	 * @param sysChannelId
	 * @param sysCorporationId
	 * @return
	 * @throws Exception
	 */
	private DomesticOrderSlipDTO getMallId(long sysChannelId, long sysCorporationId) throws Exception {
		DomesticOrderSlipDTO dto = new DomesticOrderSlipDTO();
		MstUserDTO userInfo = new MstUserDTO();
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		userInfo = userService.getUserName(userId);
		String authInfo = userInfo.getResponsibleNo();

		String channelId = String.valueOf(sysChannelId);
		String corporationId = String.valueOf(sysCorporationId);
		String noteTune = "";
		switch (corporationId) {
		case "1":
			noteTune = "K";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "2":
			noteTune = "S";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "3":
			noteTune = "T";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "4":
			noteTune = "L";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "5":
			noteTune = "B";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "6":
			noteTune = "E";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "7":
			noteTune = "K";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "8":
			noteTune = "L";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "9":
			noteTune = "L";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "10":
			noteTune = "K";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "11":
			noteTune = "K";
			dto.setSysCorporationId(sysCorporationId);
			break;
		case "12":
			noteTune = "K";
			dto.setSysCorporationId(sysCorporationId);
			break;
		default :
			noteTune = "";
			dto.setSysCorporationId(sysCorporationId);
		}

		switch (channelId) {
		case "1":
			noteTune = noteTune + "E";
			dto.setMall(noteTune);
			break;
		case "2":
			noteTune = noteTune + "K";
			dto.setMall(noteTune);
			break;
		case "3":
			noteTune = noteTune + "R";
			dto.setMall(noteTune);
			break;
		case "4":
			noteTune = noteTune + "R";
			dto.setMall(noteTune);
			break;
		case "5":
			noteTune = noteTune + "R";
			dto.setMall(noteTune);
			break;
		case "6":
			noteTune = noteTune + "YS";
			dto.setMall(noteTune);
			break;
		case "7":
			noteTune = noteTune + "Y";
			dto.setMall(noteTune);
			break;
		case "8":
			noteTune = noteTune + "A";
			dto.setMall(noteTune);
			break;
		case "9":
			noteTune = noteTune + "G";
			dto.setMall(noteTune);
			break;
		case "10":
			noteTune = "";
			dto.setMall(noteTune);
			dto.setSysCorporationId(99);
			break;
			default:
				noteTune = "";
				dto.setMall(noteTune);
		}

		dto.setNoteTurn(authInfo + " - " + noteTune);


		return dto;
	}

	/**
	 * 商品か消費税かそれ以外かを判定し売上情報を格納します
	 *
	 * @param salesSlipDTO
	 * @param salesItemList
	 * @param csvImportDTO
	 * @throws Exception
	 */
	private ErrorDTO setDomesticItemOrTaxOrOption(ErrorDTO csvErrorDTO,
			List<DomesticOrderItemDTO> orderItemList, CsvImportDTO csvImportDTO) throws Exception {

		DomesticOrderItemDTO domesticItemDto = new DomesticOrderItemDTO();
		csvErrorDTO.setSuccess(true);

		//商品情報を格納
		csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
		domesticItemDto = setDomesticItemDTO(csvErrorDTO, csvImportDTO);

		if (!csvErrorDTO.isSuccess()) {
			csvErrorDTO.setSuccess(false);
			csvErrorDTO.setFileName(csvImportDTO.getFileNm());
			ErrorMessageDTO messageDTO = new ErrorMessageDTO();
			messageDTO.setErrorMessage("受注番号 " + csvImportDTO.getOrderNo() + "　の品番　　" +  csvImportDTO.getShopItemCd() + "が出品DB未登録の為登録されませんでした。");
			csvErrorDTO.getErrorMessageList().add(messageDTO);
		}

		if (domesticItemDto != null) {
			orderItemList.add(domesticItemDto);
		}
		return csvErrorDTO;
	}

	/**
	 * 国内注文商品情報を格納し、返却します。
	 *
	 * @param csvImportDTO
	 * @return
	 * @throws Exception
	 */
	private DomesticOrderItemDTO setDomesticItemDTO(ErrorDTO csvErrorDTO, CsvImportDTO csvImportDTO) throws Exception {

		DomesticOrderItemDTO domesticItemDto = new DomesticOrderItemDTO();
		DomesticExhibitionDTO exhibitionDto = new DomesticExhibitionDTO();
		DomesticExhibitionDTO exhbyRsltDto = new DomesticExhibitionDTO();
		DomesticExhibitionDAO exhibitionDao = new DomesticExhibitionDAO();
		MstUserDTO userInfo = new MstUserDTO();
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		userInfo = userService.getUserName(userId);

		if (StringUtils.isEmpty(csvImportDTO.getShopItemCd())) {
			return domesticItemDto = null;
		}

		exhibitionDto.setManagementCode(csvImportDTO.getShopItemCd());
		exhibitionDto.setItemNm(csvImportDTO.getItemNm());
		exhbyRsltDto = exhibitionDao.getDomesticItem(exhibitionDto);

		if (exhbyRsltDto != null) {
			//※システム伝票IDはinsert時に格納する
			domesticItemDto.setManagementCode(exhbyRsltDto.getManagementCode());
			domesticItemDto.setOrderNum(csvImportDTO.getItemNum());
			domesticItemDto.setWholsesalerNm(exhbyRsltDto.getWholsesalerNm());
			domesticItemDto.setMakerCode(exhbyRsltDto.getMakerCode());
			domesticItemDto.setMakerId(exhbyRsltDto.getSysMakerId());
			domesticItemDto.setItemNm(exhbyRsltDto.getItemNm());
			domesticItemDto.setStatus(WebConst.DOMESTIC_EXIHIBITION_SORT_ORDER_NO);
			domesticItemDto.setOrderCharge(userInfo.getUserFamilyNmKanji() + userInfo.getUserFirstNmKanji());
			domesticItemDto.setPurchasingCost(exhbyRsltDto.getPurchasingCost());
			domesticItemDto.setCostComfFlag("0");
			domesticItemDto.setListPrice(exhbyRsltDto.getListPrice());
			domesticItemDto.setPostage(exhbyRsltDto.getPostage());
		} else {
			csvErrorDTO.setSuccess(false);
			domesticItemDto = null;
		}
		return domesticItemDto;
	}


	/**
	 * インポートファイルが既に存在するか確認：国内用
	 * @param fileNm
	 * @return
	 * @throws DaoException
	 */
	private String exsistDomesticFileNm(String fileNm) throws DaoException {

		fileNm = StringUtils.substring(fileNm, StringUtils.lastIndexOf(fileNm, "\\") + 1);

		DomesticCsvImportDAO dao = new DomesticCsvImportDAO();

		DomesticCsvImportDTO dto = new DomesticCsvImportDTO();
		dto = dao.getDomesticCsvFileNm(fileNm);

		if (dto == null || StringUtils.isEmpty(dto.getFileNm())) {

			return null;
		}

		return dto.getFileNm();
	}

	/**
	 * (在庫数キープ用)インポートするCSVファイルが既に登録されているか確認
	 * 登録済み：エラー
	 * 未登録：新規で登録を行う
	 * @param fileUp
	 * @param csvImportList
	 * @return
	 * @throws Exception
	 */
	public ErrorDTO keepImportFile (FormFile fileUp, List<CsvImportDTO> csvImportList) throws Exception {

		InputStream inputStream = fileUp.getInputStream();
		ErrorDTO csvErrorDTO = new ErrorDTO();

		//インポートされたファイルが登録されてないか判別
//		int messageIdx = 0;
		if (StringUtils.isNotEmpty(exsistKeepFileNm(fileUp.getFileName()))) {

			csvErrorDTO.setSuccess(false);
			csvErrorDTO.setFileName("「" + fileUp.getFileName() + "」はすでにインポート済みファイルです。");
			return csvErrorDTO;
		}

		CsvConfig config = new CsvConfig();
		CsvReader reader = new CsvReader(config, true);
		CsvContext context = reader.parse(inputStream);

		for (CsvRecord csvRecord : context) {

			String[] csvLineArray = csvRecord.toArray();

			CsvImportDTO csvImportDTO = new CsvImportDTO();
			csvImportDTO = setCsvImportList(csvLineArray);

			csvImportDTO.setOrderRemarksMemo(csvImportDTO.getOrderMemo() + "\r\n" + csvImportDTO.getOrderRemarks());

			csvImportDTO.setFileNm(fileUp.getFileName());

			csvImportDTO.setSysImportId((new SequenceDAO().getMaxSysKeepImportId() + 1));

			//ファイル名から法人IDを取得
			csvImportDTO.setSysCorporationId(getCorporationId(fileUp.getFileName()));

			//登録実行
			CsvImportDAO dao = new CsvImportDAO();
			dao.registryKeepCsvImport(csvImportDTO);
			//CSV取り込み日をYYYY/MM/DD形式で取得
			csvImportDTO.setImportDate(dao.getKeepCsvUpdateDate(csvImportDTO.getSysImportId()));

			//伝票用Listに格納
			csvImportList.add(csvImportDTO);
		}
		return csvErrorDTO;

	}

	/**
	 * @param fileUp
	 * @param csvImportList
	 * @return
	 * @throws Exception
	 */
	public ErrorDTO getCSVImportRecords (FormFile fileUp, List<CsvImportDTO> csvImportList) throws Exception {

		InputStream inputStream = fileUp.getInputStream();
		ErrorDTO csvErrorDTO = new ErrorDTO();

		if (StringUtils.isNotEmpty(exsistKeepFileNm(fileUp.getFileName()))) {
			
			csvErrorDTO.setSuccess(false);
			csvErrorDTO.setFileName("「" + fileUp.getFileName() + "」はすでにインポート済みファイルです。");
			return csvErrorDTO;
		}

		CsvConfig config = new CsvConfig();
		CsvReader reader = new CsvReader(config, true);
		CsvContext context = reader.parse(inputStream);

		for (CsvRecord csvRecord : context) {

			String[] csvLineArray = csvRecord.toArray();

			CsvImportDTO csvImportDTO = new CsvImportDTO();
			csvImportDTO = setCsvImportList(csvLineArray);

			csvImportDTO.setOrderRemarksMemo(csvImportDTO.getOrderMemo() + "\r\n" + csvImportDTO.getOrderRemarks());

			csvImportDTO.setFileNm(fileUp.getFileName());

			csvImportDTO.setSysImportId((new SequenceDAO().getMaxSysKeepImportId() + 1));

			//ファイル名から法人IDを取得
			csvImportDTO.setSysCorporationId(getCorporationId(fileUp.getFileName()));

			//伝票用Listに格納
			csvImportList.add(csvImportDTO);
		}
		return csvErrorDTO;
	}


	/**
	 * インポートファイルが既に存在するか確認(在庫数キープ用)
	 * @param fileNm
	 * @return
	 * @throws DaoException
	 */
	private String exsistKeepFileNm(String fileNm) throws DaoException {

		fileNm = StringUtils.substring(fileNm, StringUtils.lastIndexOf(fileNm, "\\") + 1);

		CsvImportDAO dao = new CsvImportDAO();

		ExtendKeepCsvImportDTO dto = new ExtendKeepCsvImportDTO();
		dto = dao.getKeepCsvFileNm(fileNm);

		if (dto == null || StringUtils.isEmpty(dto.getFileNm())) {

			return null;
		}

		return dto.getFileNm();
	}
	
	private boolean isSkipSetItem(ExtendKeepCsvImportDTO dto, Map<String, String> errorMessageGreenMap, Map<String, String> errorMessageYellowMap ) throws Exception {
		ItemDAO itemDAO = new ItemDAO();
		
		if ((dto.getSpecMemo() != null) && (dto.getSpecMemo().indexOf("廃盤") != -1)) {
			String itemCode = dto.getItemCode();
			String orderNo1 = dto.getOrderNo();
			
			List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(dto.getSysItemId());
			if (parentList != null) {
				System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
//				errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
//						"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
			}
			else {
//				errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
			}
			return true;
		}
		if ((dto.getSpecMemo() != null) && (dto.getSpecMemo().indexOf("受注生産") != -1)) {
			String itemCode = dto.getItemCode();
			String orderNo1 = dto.getOrderNo();
//			errorMessageYellowMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
			return true;
		}		
		
		return false;
	}

	private boolean isSkipSetItem(ExtendSetItemDTO dto, ExtendKeepCsvImportDTO csvImportDto, Map<String, String> errorMessageGreenMap, Map<String, String> errorMessageYellowMap ) throws Exception {
		ItemDAO itemDAO = new ItemDAO();
		
		if ((dto.getSpecMemo() != null) && (dto.getSpecMemo().indexOf("廃盤") != -1)) {
			String itemCode = itemDAO.getItemCode(dto.getFormSysItemId());
			String orderNo1 = csvImportDto.getOrderNo();
			
			List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(dto.getSysItemId());
			if (parentList != null) {
				System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
//				errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
//						"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
			}
			else {
//				errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
			}
			return true;
		}
		if ((dto.getSpecMemo() != null) && (dto.getSpecMemo().indexOf("受注生産") != -1)) {
			String itemCode = itemDAO.getItemCode(dto.getFormSysItemId());
			String orderNo1 = csvImportDto.getOrderNo();
//			errorMessageYellowMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
			return true;
		}
		
		return false;
	}

	// added by wahaha
	public ErrorDTO csvToRemoveKeeps(List<CsvImportDTO> csvImportList) throws Exception {

		ErrorDTO csvErrorDTO = new ErrorDTO();
		ItemDAO itemDAO = new ItemDAO();
		
		//警告文表示のためのリスト、マップ
		Map<String, String> errorMessageGreenMap = new LinkedHashMap<>();
		Map<String, String> errorMessageYellowMap = new LinkedHashMap<>();
		List<ErrorMessageDTO> errorMessageBlueList = new ArrayList<>();
		List<ErrorMessageDTO> errorMessageRedList = new ArrayList<>();
		List<ErrorMessageDTO> errorMessageBlackList = new ArrayList<>();

		List<ErrorMessageDTO> errorMessagePurpleList = new ArrayList<>();


		//警告文を表示した品番と個数を格納していくmapリスト<map>。入荷数によって入荷予定日を切り替える処理のために必要
		List<Map<String, Integer>> alertDispItemMapList = new ArrayList<>();

		//CSVファイル内からキープの追加に必要な情報を抜き取った商品リストを作成する処理
		//処理の中で商品コードが11桁未満の場合は追加処理を飛ばす。これは11桁に切り取る関数でのExceptionを避けるためと下記の理由のため。
		//CSVファイルの中には在庫一覧に存在するが、数字11桁+アルファベットの商品が存在するため11ケタに切らなければ正しい検索結果が取れない。

		/*
		 * TODO 楽天倉庫対象の商品かどうかを判別する。リストをkts倉庫用と楽天倉庫用で分ける。
		 * チェック内容は次の通り。
		 * １．楽天倉庫用のキープか　NO：kts倉庫へ
		 * ２．楽天倉庫用のキープならば同一受注伝票内にKTS倉庫対象の商品が存在する。　YES:KTS倉庫へ（警告メッセージを出力する。）
		 * ２．楽天倉庫用のキープならば楽天倉庫の在庫は存在するか　NO：KTS倉庫へ（警告メッセージを出力する。）
		 */
		List<ExtendKeepCsvImportDTO> csvItemList = new ArrayList<>();
		for (int i = 0; i < csvImportList.size(); i++) {

			ExtendKeepCsvImportDTO keepCsvImportDTO = new ExtendKeepCsvImportDTO();

			/*
			 *  商品種別が「内訳商品」でかつ名称に「RSL出荷分」が含まれていたら一つ前の
			 *  「セット商品」を楽天倉庫キープ対象とする。
			 */
			if (StringUtils.equals(csvImportList.get(i).getItemClassification(), "内訳商品")) {
				/*
				 *  商品種別が「セット商品」ならば楽天倉庫キープ対象とする。
				 *  ただし、次の条件の場合、楽天倉庫ではなくKTS倉庫のキープとなる。
				 *  ここでは、一度「セット商品」は楽天倉庫キープ対象として処理して、
				 *  条件チェックは別途行う。
				 *  ■条件
				 *  １．同一受注伝票内にKTS倉庫対象の商品がある。
				 *  ２．楽天倉庫の在庫が不足している。
				 */

				if (!csvImportList.get(i).getItemNm().contains("RSL出荷分")) {
					//内訳商品の商品名にRSL出荷分と含まれていなければキープしないのでキープ対象商品のリストから削除する。
					if (StringUtils.equals(csvItemList.get(csvItemList.size() - 1).getItemClassification(), "セット商品")) {
						csvItemList.remove(csvItemList.size() - 1);
					}
					continue;
				}


				// XXX 商品種別が「内訳商品」の次が「セット商品」になるので判断しなくてよいが念のため判断する。
				if (StringUtils.equals(csvItemList.get(csvItemList.size() - 1).getItemClassification(), "セット商品")) {
					csvItemList.get(csvItemList.size() - 1).setExternalKeepFlag("1");
					csvItemList.get(csvItemList.size() - 1).setExternalWarehouseCode("RSL");
				}
			}


			//商品種別が「商品」以外の時追加しない。
			if(!(csvImportList.get(i).getItemClassification().equals("商品")
					|| csvImportList.get(i).getItemClassification().equals("セット商品"))) {
				continue;
			}
			// 11桁以下の場合は自社商品ではないため追加しない。
			if (StringUtils.length(csvImportList.get(i).getShopItemCd()) < 11) {
				continue;
			}
			//商品コード
			keepCsvImportDTO.setShopItemCd(csvImportList.get(i).getShopItemCd().substring(0, 11));
			//受注番号
			keepCsvImportDTO.setOrderNo(csvImportList.get(i).getOrderNo());
			//個数を追加
			keepCsvImportDTO.setItemNum(csvImportList.get(i).getItemNum());
			//受注ルート
			keepCsvImportDTO.setOrderRoute(csvImportList.get(i).getOrderRoute());
			//システム法人ID
			keepCsvImportDTO.setSysCorporationId(csvImportList.get(i).getSysCorporationId());
			//取り込み日
			keepCsvImportDTO.setImportDate(csvImportList.get(i).getImportDate());

			//商品種別
			keepCsvImportDTO.setItemClassification(csvImportList.get(i).getItemClassification());
			
			keepCsvImportDTO.setLastStatus(csvImportList.get(i).getLastStatus());
			
			keepCsvImportDTO.setDisposalRoute(csvImportList.get(i).getDisposalRoute());

			csvItemList.add(keepCsvImportDTO);
		}

		//上の処理だけだとCSVファイル上の情報しかないため、ここで商品一つ一つを商品マスタから検索し詳細情報を付け加える。
		for(int i = 0; i < csvItemList.size(); i++) {

			ExtendKeepCsvImportDTO afterKeepCsvImport = csvItemList.get(i);
			MstItemDTO dto =  new MstItemDTO();
			dto = itemDAO.getMstItemForKeep(csvItemList.get(i).getShopItemCd());

			if (dto != null) {
				//商品コード
				afterKeepCsvImport.setItemCode(dto.getItemCode());
				//システム商品ID
				afterKeepCsvImport.setSysItemId(dto.getSysItemId());
				//出庫分類フラグをセット 通常商品の場合は出庫分類フラグがないため、代わりに”0”をセットしている
				if (dto.getLeaveClassFlg() != null) {
					afterKeepCsvImport.setLeaveClassFlg(dto.getLeaveClassFlg());
				} else {
					afterKeepCsvImport.setLeaveClassFlg("0");
				}
				//仕様メモ
				afterKeepCsvImport.setSpecMemo(dto.getSpecMemo());
				//指定の位置に上書き
				csvItemList.set(i, afterKeepCsvImport);
			}
		}

		//商品をひとつずつ参照し、キープを追加していく
		//セット商品でなおかつ出庫分類が"1"の時、子要素を見に行く。これは子要素に"1"を持つ限り続く。
		//同じ受注番号のキープが存在するとき二重登録をふせぐため、updFlgというフラグを使用。
		for (int i = 0; i < csvItemList.size(); i++) {

			// Found 
			String lastStatus = csvItemList.get(i).getLastStatus();
			String disposalRoute = csvItemList.get(i).getDisposalRoute();
			if(!((StringUtils.equals(lastStatus, "処理済み") || StringUtils.equals(lastStatus, "キャンセル")) &&
					(StringUtils.equals(disposalRoute, "基本ルート") || StringUtils.equals(disposalRoute, "BO") || StringUtils.equals(disposalRoute, "店舗受け取り")))) {
				continue;
			}

			//国内の商品は出庫分類がないため、処理を飛ばす
			if(csvItemList.get(i).getLeaveClassFlg() == null) {
				continue;
			}

			//廃盤または受注生産の時キープを追加しないようにするため、仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする。
			if ((csvItemList.get(i).getSpecMemo() != null) && (csvItemList.get(i).getSpecMemo().indexOf("廃盤") != -1)) {
				String itemCode = csvItemList.get(i).getItemCode();
				String orderNo1 = csvItemList.get(i).getOrderNo();
				
				List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(csvItemList.get(i).getSysItemId());
				if (parentList != null) {
					System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
//					errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
//							"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
				}
				else {
//					errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
				}
				continue;
			}
			
			if ((csvItemList.get(i).getSpecMemo() != null) && (csvItemList.get(i).getSpecMemo().indexOf("受注生産") != -1)) {
				String itemCode = csvItemList.get(i).getItemCode();
				String orderNo1 = csvItemList.get(i).getOrderNo();
//				errorMessageYellowMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
				continue;
			}


			if (csvItemList.get(i).getLeaveClassFlg().equals("0")) {

				// KTS倉庫キープ分
				//商品単位のキープリスト取得
				System.out.println("STEP 1");
				List<ExtendKeepDTO> getKeepList = new ArrayList<>();			
				MstItemDTO dto = itemDAO.getMstItemForKeep(csvItemList.get(i).getShopItemCd());
				int orgkeepIncreaseStep = 0;
				int changedKeepCount = 0;
				int totalStockNum = itemDAO.getMstItemDTO(dto.getSysItemId()).getTotalStockNum();

				System.out.println("STEP 1 : Normal Product");
				System.out.println("Found : OrderNo = " + csvItemList.get(i).getOrderNo() + 
						" ItemId = " + csvItemList.get(i).getSysItemId() + 
						" TotalStockNum = " + totalStockNum + 
						" ItemNum = " + csvItemList.get(i).getItemNum());
				if (csvItemList.get(i).getExternalKeepFlag() == null || StringUtils.equals(csvItemList.get(i).getExternalKeepFlag(), "0")) {
					getKeepList.addAll(itemDAO.getKeepList(csvItemList.get(i).getSysItemId()));

					//キープに何も登録されていない時登録
					System.out.println("STEP 2: keepList size = " + getKeepList.size() + " i = " + i 
							+ " sys_item_id = " + csvItemList.get(i).getSysItemId());

					if (!getKeepList.isEmpty()) {
						for (int n = 0; n < getKeepList.size(); n++) 
						{
							//キープ内を一つずつ参照し、更新
							int curKeepNum = getKeepList.get(n).getKeepNum();
							orgkeepIncreaseStep += getKeepList.get(n).getKeepNum();

							boolean isExistRemove = false;
							for (int j = i; j < csvItemList.size(); j++) {
								if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(j).getOrderNo())) {
									isExistRemove = true;
									break;
								}
							}
							if(isExistRemove) 
							{								
								// ----------------------------------------------------
								// remove item : remove order-matched keep item
								changedKeepCount += csvItemList.get(i).getItemNum(); // getKeepList.get(n).getKeepNum();
								int newNum = curKeepNum - csvItemList.get(i).getItemNum();
								keepMinusUpd(csvItemList.get(i), getKeepList.get(n));

								ErrorMessageDTO messageDTO = new ErrorMessageDTO();
								messageDTO.setErrorMessage("品番「" + dto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
										+"」 の商品キープを削除しました。");
								errorMessageBlackList.add(messageDTO);

								//追加件数を加算
								csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
								if (newNum <= 0)
									continue;
							}
							
							// --------------------------------------------------
							// remained item : detect arrival condition changed about remain keep item
							// 
							// check changed event
							int originalTempStockNum = totalStockNum - orgkeepIncreaseStep;
							int newTempStockNum = totalStockNum - orgkeepIncreaseStep + changedKeepCount;
							
							if ((originalTempStockNum < 0)) {
								ErrorMessageDTO messageDTO = new ErrorMessageDTO();
								if (newTempStockNum >= 0) {
									// new order is activated 
									messageDTO.setErrorMessage("品番「" + dto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
											+"」 の商品が出荷可能になりました。");
								} else {
									List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(dto.getSysItemId());
									String orgArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, dto.getItemCode(), 0, -1 * originalTempStockNum);
									String newArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, dto.getItemCode(), 0, -1 * newTempStockNum);
									
									if (orgArrivalScheduleDate.equals(newArrivalScheduleDate) == false) {
										messageDTO.setErrorMessage( "品番「" + dto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
												+ "」 の商品は" + newArrivalScheduleDate + "に変更になりました。");
									}
								}																						
								int isExistErrList = -1;
								for (int k = 0; k < errorMessageBlueList.size(); k++) {
									if(StringUtils.contains(errorMessageBlueList.get(k).getErrorMessage(), getKeepList.get(n).getOrderNo())) {
										isExistErrList = k;
										break;
									}
								}
								if(isExistErrList < 0) {
									errorMessageBlueList.add(messageDTO);
								}
							}
						}
					}
				} else {
					List<ExtendKeepDTO> getKeepKTSList = new ArrayList<>();			
					getKeepKTSList.addAll(itemDAO.getKeepList(csvItemList.get(i).getSysItemId()));

					if (!getKeepKTSList.isEmpty()) {
						for (int n = 0; n < getKeepKTSList.size(); n++) 
						{
							//キープ内を一つずつ参照し、更新
							int curKeepNum = getKeepKTSList.get(n).getKeepNum();
							orgkeepIncreaseStep += getKeepKTSList.get(n).getKeepNum();

							boolean isExistRemove = false;
							for (int j = i; j < csvItemList.size(); j++) {
								if(StringUtils.equals(getKeepKTSList.get(n).getOrderNo(), csvItemList.get(j).getOrderNo())) {
									isExistRemove = true;
									break;
								}
							}
							if(isExistRemove) 
							{								
								// ----------------------------------------------------
								// remove item : remove order-matched keep item
								changedKeepCount += csvItemList.get(i).getItemNum(); // getKeepList.get(n).getKeepNum();
								int newNum = curKeepNum - csvItemList.get(i).getItemNum();
								keepMinusUpd(csvItemList.get(i), getKeepKTSList.get(n));

								ErrorMessageDTO messageDTO = new ErrorMessageDTO();
								messageDTO.setErrorMessage("品番「" + dto.getItemCode() + "」 受注番号「"+ getKeepKTSList.get(n).getOrderNo() 
										+"」 の商品キープを削除しました。");
								errorMessageBlackList.add(messageDTO);

								//追加件数を加算
								csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
								if (newNum <= 0)
									continue;
							}
							
							// --------------------------------------------------
							// remained item : detect arrival condition changed about remain keep item
							// 
							// check changed event
							int originalTempStockNum = totalStockNum - orgkeepIncreaseStep;
							int newTempStockNum = totalStockNum - orgkeepIncreaseStep + changedKeepCount;
							
							if ((originalTempStockNum < 0)) {
								ErrorMessageDTO messageDTO = new ErrorMessageDTO();
								if (newTempStockNum >= 0) {
									// new order is activated 
									messageDTO.setErrorMessage("品番「" + dto.getItemCode() + "」 受注番号「"+ getKeepKTSList.get(n).getOrderNo() 
											+"」 の商品が出荷可能になりました。");
								} else {
									List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(dto.getSysItemId());
									String orgArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, dto.getItemCode(), 0, -1 * originalTempStockNum);
									String newArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, dto.getItemCode(), 0, -1 * newTempStockNum);
									
									if (orgArrivalScheduleDate.equals(newArrivalScheduleDate) == false) {
										messageDTO.setErrorMessage( "品番「" + dto.getItemCode() + "」 受注番号「"+ getKeepKTSList.get(n).getOrderNo() 
												+ "」 の商品は" + newArrivalScheduleDate + "に変更になりました。");
									}
								}																						
								int isExistErrList = -1;
								for (int k = 0; k < errorMessageBlueList.size(); k++) {
									if(StringUtils.contains(errorMessageBlueList.get(k).getErrorMessage(), getKeepKTSList.get(n).getOrderNo())) {
										isExistErrList = k;
										break;
									}
								}
								if(isExistErrList < 0) {
									errorMessageBlueList.add(messageDTO);
								}
							}
						}
					}
					// 楽天倉庫キープ分
					getKeepList.addAll(itemDAO.getExternalKeepList(csvItemList.get(i).getSysItemId(), csvItemList.get(i).getExternalWarehouseCode()));

					//キープに何も登録されていない時登録
					if (!getKeepList.isEmpty()) {
						for (int n = 0; n < getKeepList.size(); n++) 
						{
							boolean isExistRemove = false;
							for (int j = i; j < csvItemList.size(); j++) {
								if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(j).getOrderNo())) {
									isExistRemove = true;
									break;
								}
							}
							if(isExistRemove) 
							{
								// ----------------------------------------------------
								// remove item : remove order-matched keep item
								changedKeepCount += csvItemList.get(i).getItemNum(); // getKeepList.get(n).getKeepNum();
								externalKeepMinusUpd(csvItemList.get(i), getKeepList.get(n));

								ErrorMessageDTO messageDTO = new ErrorMessageDTO();
								messageDTO.setErrorMessage("品番「" + dto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
										+"」 の商品キープを削除しました。");
								errorMessageBlackList.add(messageDTO);
								
								//追加件数を加算
								csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
							}
						}
					}
				}

			} else if (csvItemList.get(i).getLeaveClassFlg().equals("1")) {

				//1層目の構成商品リストを取得
				List<ExtendSetItemDTO> secondItemList = new ItemService()
						.getSetItemInfoList(csvItemList.get(i).getSysItemId());

				for (int j = 0; j < secondItemList.size(); j++) {

					long secondItemFormSysItemId = secondItemList.get(j).getFormSysItemId();

					//廃盤または受注生産の時キープを追加しないようにするため仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
					if ((secondItemList.get(j).getSpecMemo() != null) && (secondItemList.get(j).getSpecMemo().indexOf("廃盤") != -1)) {
						String itemCode = itemDAO.getItemCode(secondItemList.get(j).getFormSysItemId());
						String orderNo1 = csvItemList.get(i).getOrderNo();
						
						List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(secondItemList.get(j).getSysItemId());
						if (parentList != null) {
							System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
//							errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
//									"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
						}
						else {
//							errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
						}
						continue;
					}
					if ((secondItemList.get(j).getSpecMemo() != null) && (secondItemList.get(j).getSpecMemo().indexOf("受注生産") != -1)) {
						String itemCode = itemDAO.getItemCode(secondItemList.get(j).getFormSysItemId());
						String orderNo1 = csvItemList.get(i).getOrderNo();
//						errorMessageYellowMap.put(itemCode, "注文番号「" + orderNo1 + "」: 品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
						continue;
					}

					// additional valuables 
					MstItemDTO secondDto = itemDAO.getMstItemDTO(secondItemFormSysItemId); 
					int secondOrgkeepIncreaseStep = 0;
					int secondChangedKeepCount = 0;
					int secondTotalStockNum = itemDAO.getMstItemDTO(secondItemFormSysItemId).getTotalStockNum();

					if (secondItemList.get(j).getLeaveClassFlg().equals("0")) {

						//商品単位のキープリスト取得
						List<ExtendKeepDTO> getKeepList = new ArrayList<>();
						getKeepList.addAll(itemDAO.getKeepList(secondItemList.get(j).getFormSysItemId()));

						if (!getKeepList.isEmpty()) {
							for (int n = 0; n < getKeepList.size(); n++) 
							{
								//キープ内を一つずつ参照、更新
								int curKeepNum = getKeepList.get(n).getKeepNum();
								secondOrgkeepIncreaseStep += getKeepList.get(n).getKeepNum();

								boolean isExistRemove = false;
								for (int m = i; m < csvItemList.size(); m++) {
									if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(m).getOrderNo())) {
										isExistRemove = true;
										break;
									}
								}
								//キープ内を一つずつ参照、更新
								if(isExistRemove) 
								{
									// ----------------------------------------------------
									// remove item : remove order-matched keep item
									secondChangedKeepCount += secondItemList.get(j).getItemNum(); // getKeepList.get(n).getKeepNum();
									int newNum = -1 * csvItemList.get(i).getItemNum() * secondItemList.get(j).getItemNum() + getKeepList.get(n).getKeepNum();
									keepMinusUpd(secondItemList.get(j), getKeepList.get(n), csvItemList.get(i));

									ErrorMessageDTO messageDTO = new ErrorMessageDTO();
									messageDTO.setErrorMessage("品番「" + secondDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
											+"」 の商品キープを削除しました。");
									errorMessageBlackList.add(messageDTO);

									//追加件数を加算
									csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
									if (newNum <= 0)
										continue;
								}

								// --------------------------------------------------
								// remained item : detect arrival condition changed about remain keep item
								// 
								// check changed event
								int originalTempStockNum = secondTotalStockNum - secondOrgkeepIncreaseStep;
								int newTempStockNum = secondTotalStockNum - secondOrgkeepIncreaseStep + secondChangedKeepCount;
								
								if ((originalTempStockNum < 0)) {
									ErrorMessageDTO messageDTO = new ErrorMessageDTO();
									if (newTempStockNum >= 0) {
										// new order is activated 
										messageDTO.setErrorMessage("品番「" + secondDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
												+"」 の商品が出荷可能になりました。");
									} else {
										List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(secondDto.getSysItemId());
										String orgArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, secondDto.getItemCode(), 0, -1 * originalTempStockNum);
										String newArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, secondDto.getItemCode(), 0, -1 * newTempStockNum);
										
										if (orgArrivalScheduleDate.equals(newArrivalScheduleDate) == false) {
											messageDTO.setErrorMessage( "品番「" + secondDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
													+ "」 の商品は" + newArrivalScheduleDate + "に変更になりました。");
										}
									}																						
									int isExistErrList = -1;
									for (int k = 0; k < errorMessageBlueList.size(); k++) {
										if(StringUtils.contains(errorMessageBlueList.get(k).getErrorMessage(), getKeepList.get(n).getOrderNo())) {
											isExistErrList = k;
											break;
										}
									}
									if(isExistErrList < 0) {
										errorMessageBlueList.add(messageDTO);
									}
								}
							}
						}

					} else if(secondItemList.get(j).getLeaveClassFlg().equals("1")) {

						//2層目の構成商品リストを取得
						List<ExtendSetItemDTO> thirdItemList = new ItemService()
								.getSetItemInfoList(secondItemList.get(j).getFormSysItemId());

						for (int k = 0; k < thirdItemList.size(); k++) {

							long thirdItemFormSysItemId = thirdItemList.get(k).getFormSysItemId();

							//廃盤または受注生産の時キープを追加しないようにするため仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
							if ((thirdItemList.get(k).getSpecMemo() != null) && (thirdItemList.get(k).getSpecMemo().indexOf("廃盤") != -1)) {
								String itemCode = itemDAO.getItemCode(thirdItemList.get(k).getFormSysItemId());
								String orderNo1 = csvItemList.get(i).getOrderNo();
								
								List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(thirdItemList.get(k).getSysItemId());
								if (parentList != null) {
									System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
//									errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
//											"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
								}
								else {
//									errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
								}
								continue;
							}
							if ((thirdItemList.get(k).getSpecMemo() != null) && (thirdItemList.get(k).getSpecMemo().indexOf("受注生産") != -1)) {
								String itemCode = itemDAO.getItemCode(thirdItemList.get(k).getFormSysItemId());
								String orderNo1 = csvItemList.get(i).getOrderNo();
//								errorMessageYellowMap.put(itemCode, "注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
								continue;
							}

							// additional valuables 
							MstItemDTO thirdDto = itemDAO.getMstItemDTO(thirdItemFormSysItemId); 
							int thirdOrgkeepIncreaseStep = 0;
							int thirdChangedKeepCount = 0;
							int thirdTotalStockNum = itemDAO.getMstItemDTO(thirdItemFormSysItemId).getTotalStockNum();

							if (thirdItemList.get(k).getLeaveClassFlg().equals("0")) {

								//商品単位のキープリストを取得します
								List<ExtendKeepDTO> getKeepList = new ArrayList<>();
								getKeepList.addAll(itemDAO.getKeepList(thirdItemList.get(k).getFormSysItemId()));

								if (!getKeepList.isEmpty()) {

									for (int n = 0; n < getKeepList.size(); n++) {
										thirdOrgkeepIncreaseStep += getKeepList.get(n).getKeepNum();

										boolean isExistRemove = false;
										for (int m = i; m < csvItemList.size(); m++) {
											if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(m).getOrderNo())) {
												isExistRemove = true;
												break;
											}
										}
										if(isExistRemove) 
										{
											// ----------------------------------------------------
											// remove item : remove order-matched keep item
											thirdChangedKeepCount += thirdItemList.get(k).getItemNum(); // getKeepList.get(n).getKeepNum();
											int newNum = -1 * csvItemList.get(i).getItemNum() * thirdItemList.get(k).getItemNum() + getKeepList.get(n).getKeepNum();
											keepMinusUpd(thirdItemList.get(k), getKeepList.get(n), csvItemList.get(i));

											ErrorMessageDTO messageDTO = new ErrorMessageDTO();
											messageDTO.setErrorMessage("品番「" + thirdDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
													+"」 の商品キープを削除しました。");
											errorMessageBlackList.add(messageDTO);
											//追加件数を加算
											csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
											if (newNum <= 0)
												continue;
										}

										// --------------------------------------------------
										// remained item : detect arrival condition changed about remain keep item
										// 
										// check changed event
										int originalTempStockNum = thirdTotalStockNum - thirdOrgkeepIncreaseStep;
										int newTempStockNum = thirdTotalStockNum - thirdOrgkeepIncreaseStep + thirdChangedKeepCount;
										if ((originalTempStockNum < 0)) {
											ErrorMessageDTO messageDTO = new ErrorMessageDTO();
											if (newTempStockNum >= 0) {
												// new order is activated 
												messageDTO.setErrorMessage("品番「" + thirdDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
														+"」 の商品が出荷可能になりました。");
											} else {
												List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(thirdDto.getSysItemId());
												String orgArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, thirdDto.getItemCode(), 0, -1 * originalTempStockNum);
												String newArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, thirdDto.getItemCode(), 0, -1 * newTempStockNum);
												
												if (orgArrivalScheduleDate.equals(newArrivalScheduleDate) == false) {
													messageDTO.setErrorMessage( "品番「" + thirdDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
															+ "」 の商品は" + newArrivalScheduleDate + "に変更になりました。");
												}
											}																						
											int isExistErrList = -1;
											for (int l = 0; l < errorMessageBlueList.size(); l++) {
												if(StringUtils.contains(errorMessageBlueList.get(l).getErrorMessage(), getKeepList.get(n).getOrderNo())) {
													isExistErrList = l;
													break;
												}
											}
											if(isExistErrList < 0) {
												errorMessageBlueList.add(messageDTO);
											}
										}
									}
								}

							} else if(thirdItemList.get(k).getLeaveClassFlg().equals("1")) {

								//3層目の構成商品リストを取得
								List<ExtendSetItemDTO> fourthItemList = new ItemService()
										.getSetItemInfoList(thirdItemList.get(k).getFormSysItemId());

								for (int l = 0; l < fourthItemList.size(); l++) {

									long fourthItemFormSysItemId = fourthItemList.get(l).getFormSysItemId();

									//廃盤または受注生産の時キープを追加しないようにするため仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
									if ((fourthItemList.get(l).getSpecMemo() != null) && (fourthItemList.get(l).getSpecMemo().indexOf("廃盤") != -1)) {
										String itemCode = itemDAO.getItemCode(fourthItemList.get(l).getFormSysItemId());
										String orderNo1 = csvItemList.get(i).getOrderNo();

										List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(fourthItemList.get(l).getSysItemId());
										if (parentList != null) {
											System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
//											errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
//													"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
										}
										else {
//											errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
										}
										continue;
									}
									if ((fourthItemList.get(l).getSpecMemo() != null) && (fourthItemList.get(l).getSpecMemo().indexOf("受注生産") != -1)) {
										String itemCode = itemDAO.getItemCode(fourthItemList.get(l).getFormSysItemId());
										String orderNo1 = csvItemList.get(i).getOrderNo();
//										errorMessageYellowMap.put(itemCode, "注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
										continue;
									}

									// additional valuables 
									MstItemDTO fourthDto = itemDAO.getMstItemDTO(fourthItemFormSysItemId); 
									int fourthOrgkeepIncreaseStep = 0;
									int fourthChangedKeepCount = 0;
									int fourthTotalStockNum = itemDAO.getMstItemDTO(fourthItemFormSysItemId).getTotalStockNum();

									if (fourthItemList.get(l).getLeaveClassFlg().equals("0")) {

										//商品単位のキープリスト取得
										List<ExtendKeepDTO> getKeepList = new ArrayList<>();
										getKeepList.addAll(itemDAO.getKeepList(fourthItemList.get(l).getFormSysItemId()));

										//キープに何もないとき登録
										if (!getKeepList.isEmpty()) {
											//キープ重複登録を避けるための受注番号単位で更新したかを保存する更新フラグ
											for (int n = 0; n < getKeepList.size(); n++) 
											{
												//キープ内を一つずつ参照、更新
												fourthOrgkeepIncreaseStep += getKeepList.get(n).getKeepNum();

												boolean isExistRemove = false;
												for (int m = i; m < csvItemList.size(); m++) {
													if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(m).getOrderNo())) {
														isExistRemove = true;
														break;
													}
												}
												if(isExistRemove) 
												{
													// ----------------------------------------------------
													// remove item : remove order-matched keep item
													fourthChangedKeepCount += fourthItemList.get(l).getItemNum(); //getKeepList.get(n).getKeepNum();
													int newNum = -1 * csvItemList.get(i).getItemNum() * fourthItemList.get(l).getItemNum() + getKeepList.get(n).getKeepNum();
													keepMinusUpd(fourthItemList.get(l), getKeepList.get(n), csvItemList.get(i));

													ErrorMessageDTO messageDTO = new ErrorMessageDTO();
													messageDTO.setErrorMessage("品番「" + fourthDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
															+"」 の商品キープを削除しました。");
													errorMessageBlackList.add(messageDTO);
													
													//追加件数を加算
													csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
													if (newNum <= 0)
														continue;
												}
												// --------------------------------------------------
												// remained item : detect arrival condition changed about remain keep item
												// 
												// check changed event
												int originalTempStockNum = fourthTotalStockNum - fourthOrgkeepIncreaseStep;
												int newTempStockNum = fourthTotalStockNum - fourthOrgkeepIncreaseStep + fourthChangedKeepCount;
												if ((originalTempStockNum < 0)) {
													ErrorMessageDTO messageDTO = new ErrorMessageDTO();
													if (newTempStockNum >= 0) {
														// new order is activated 
														messageDTO.setErrorMessage("品番「" + fourthDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
																+"」 の商品が出荷可能になりました。");
													} else {
														List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(fourthDto.getSysItemId());
														String orgArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, fourthDto.getItemCode(), 0, -1 * originalTempStockNum);
														String newArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, fourthDto.getItemCode(), 0, -1 * newTempStockNum);
														
														if (orgArrivalScheduleDate.equals(newArrivalScheduleDate) == false) {
															messageDTO.setErrorMessage( "品番「" + fourthDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
																	+ "」 の商品は" + newArrivalScheduleDate + "に変更になりました。");
														}
													}																						
													int isExistErrList = -1;
													for (int p = 0; p < errorMessageBlueList.size(); p++) {
														if(StringUtils.contains(errorMessageBlueList.get(p).getErrorMessage(), getKeepList.get(n).getOrderNo())) {
															isExistErrList = p;
															break;
														}
													}
													if(isExistErrList < 0) {
														errorMessageBlueList.add(messageDTO);
													}
												}
											}
										}

									} else if(fourthItemList.get(l).getLeaveClassFlg().equals("1")) {
										//4層目の構成商品リスト取得
										List<ExtendSetItemDTO> fifthItemList = new ItemService()
												.getSetItemInfoList(fourthItemList.get(l).getFormSysItemId());

										for (int m = 0; m < fifthItemList.size(); m++) {

											long fifthItemFormSysItemId = fifthItemList.get(m).getFormSysItemId();

											//廃盤または受注生産の時キープを追加しないようにするため、仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
											if ((fifthItemList.get(m).getSpecMemo() != null) && (fifthItemList.get(m).getSpecMemo().indexOf("廃盤") != -1)) {
												String itemCode = itemDAO.getItemCode(fifthItemFormSysItemId);
												String orderNo1 = csvItemList.get(i).getOrderNo();

												List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(fifthItemList.get(m).getSysItemId());
												if (parentList != null) {
													System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
//													errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
//															"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
												}
												else {
//													errorMessageGreenMap.put(itemCode, "注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
												}
												continue;
											}
											if ((fifthItemList.get(m).getSpecMemo() != null) && (fifthItemList.get(m).getSpecMemo().indexOf("受注生産") != -1)) {
												String itemCode = itemDAO.getItemCode(fifthItemFormSysItemId);
												String orderNo1 = csvItemList.get(i).getOrderNo();
//												errorMessageYellowMap.put(itemCode, "注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
												continue;
											}

											// additional valuables 
											MstItemDTO fifthDto = itemDAO.getMstItemDTO(fifthItemFormSysItemId); 
											int fifthOrgkeepIncreaseStep = 0;
											int fifthChangedKeepCount = 0;
											int fifthTotalStockNum = fifthDto.getTotalStockNum();

											if (fifthItemList.get(m).getLeaveClassFlg().equals("0")) {

												//商品単位のキープリスト取得
												List<ExtendKeepDTO> getKeepList = new ArrayList<>();
												getKeepList.addAll(itemDAO.getKeepList(fifthItemFormSysItemId));

												//キープに何もないとき登録
												if (!getKeepList.isEmpty()) {
													for (int n = 0; n < getKeepList.size(); n++) 
													{
														//キープ内を一つずつ参照、更新
														fifthOrgkeepIncreaseStep += getKeepList.get(n).getKeepNum();

														boolean isExistRemove = false;
														for (int q = i; q < csvItemList.size(); q++) {
															if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(q).getOrderNo())) {
																isExistRemove = true;
																break;
															}
														}
														if(isExistRemove) 
														{
															// ----------------------------------------------------
															// remove item : remove order-matched keep item
															fifthChangedKeepCount += fifthItemList.get(m).getItemNum(); //getKeepList.get(n).getKeepNum();
															int newNum = -1 * csvItemList.get(i).getItemNum() * fifthItemList.get(m).getItemNum() + getKeepList.get(n).getKeepNum();
															keepMinusUpd(fifthItemList.get(m), getKeepList.get(n), csvItemList.get(i));

															ErrorMessageDTO messageDTO = new ErrorMessageDTO();
															messageDTO.setErrorMessage("品番「" + fifthDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
																	+"」 の商品キープを削除しました。");
															errorMessageBlackList.add(messageDTO);

															//追加件数を加算
															csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
															if (newNum <= 0)
																continue;
														}

														// --------------------------------------------------
														// remained item : detect arrival condition changed about remain keep item
														// check changed event
														int originalTempStockNum = fifthTotalStockNum - fifthOrgkeepIncreaseStep;
														int newTempStockNum = fifthTotalStockNum - fifthOrgkeepIncreaseStep + fifthChangedKeepCount;
														if ((originalTempStockNum < 0)) {
															ErrorMessageDTO messageDTO = new ErrorMessageDTO();
															if (newTempStockNum >= 0) {
																// new order is activated 
																messageDTO.setErrorMessage("品番「" + fifthDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
																		+"」 の商品が出荷可能になりました。");
															} else {
																List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(fifthDto.getSysItemId());
																String orgArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, fifthDto.getItemCode(), 0, -1 * originalTempStockNum);
																String newArrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, fifthDto.getItemCode(), 0, -1 * newTempStockNum);
																
																if (orgArrivalScheduleDate.equals(newArrivalScheduleDate) == false) {
																	messageDTO.setErrorMessage( "品番「" + fifthDto.getItemCode() + "」 受注番号「"+ getKeepList.get(n).getOrderNo() 
																			+ "」 の商品は" + newArrivalScheduleDate + "に変更になりました。");
																}
															}																						
															int isExistErrList = -1;
															for (int r = 0; r < errorMessageBlueList.size(); r++) {
																if(StringUtils.contains(errorMessageBlueList.get(r).getErrorMessage(), getKeepList.get(n).getOrderNo())) {
																	isExistErrList = r;
																	break;
																}
															}
															if(isExistErrList < 0) {
																errorMessageBlueList.add(messageDTO);
															}
														}
													}
												}
											}
										}
										
									}
								}
								
							}
						}
					}
				}
			}
		}

		//警告文赤：品番でまとめるため、文字列でソート
		Collections.sort(errorMessageRedList, new Comparator<ErrorMessageDTO>() {

			@Override
			public int compare(ErrorMessageDTO emDTO1, ErrorMessageDTO emDTO2) {

				return (emDTO1.getErrorMessage().compareTo(emDTO2.getErrorMessage()));
			}
		});
		//警告文青：品番でまとめるため、文字列でソート
		Collections.sort(errorMessageBlueList, new Comparator<ErrorMessageDTO>() {

			@Override
			public int compare(ErrorMessageDTO emDTO1, ErrorMessageDTO emDTO2) {

				return (emDTO1.getErrorMessage().compareTo(emDTO2.getErrorMessage()));
			}
		});

		csvErrorDTO.setErrorMessageList(filterErrorMessageList(errorMessageRedList));
		csvErrorDTO.setErrorMessageListPurple(filterErrorMessageList(errorMessageBlackList));
		csvErrorDTO.setErrorMessageListBlue(filterErrorMessageList(errorMessageBlueList));
		csvErrorDTO.setErrorMessageGreenMap(errorMessageGreenMap);
		csvErrorDTO.setErrorMessageYellowMap(errorMessageYellowMap);
//		csvErrorDTO.setErrorMessageListPurple(filterErrorMessageList(errorMessagePurpleList));

		return csvErrorDTO;
	}
	
	/**
	 * CSVファイル内の商品のキープを更新します
	 * 《処理の流れ》
	 * CSVファイルの商品をリスト化し、さらに商品ごとのキープをリスト化します。
	 * キープの受注番号とCSVファイルの受注番号を比較し、同じものであれば更新、なければ新規登録します。
	 * セット商品でかつ、出庫分類が"1"の場合は子要素を参照し、最も深い要素にキープを追加します。
	 * 仮在庫数が0～2の時は青、マイナスの場合は赤でその旨を出します。
	 *
	 * @param csvImportList
	 * @return
	 * @throws Exception
	 */
	public ErrorDTO csvToKeepUpdate(List<CsvImportDTO> csvImportList) throws Exception {

		ErrorDTO csvErrorDTO = new ErrorDTO();
		ItemDAO itemDAO = new ItemDAO();
		//警告文表示のためのリスト、マップ
		Map<String, String> errorMessageGreenMap = new LinkedHashMap<>();
		Map<String, String> errorMessageYellowMap = new LinkedHashMap<>();
		List<ErrorMessageDTO> errorMessageBlueList = new ArrayList<>();
		List<ErrorMessageDTO> errorMessageGreenList = new ArrayList<>();
		List<ErrorMessageDTO> errorMessageYellowList = new ArrayList<>();
		List<ErrorMessageDTO> errorMessageRedList = new ArrayList<>();

		List<ErrorMessageDTO> errorMessagePurpleList = new ArrayList<>();


		//警告文を表示した品番と個数を格納していくmapリスト<map>。入荷数によって入荷予定日を切り替える処理のために必要
		List<Map<String, Integer>> alertDispItemMapList = new ArrayList<>();

		//CSVファイル内からキープの追加に必要な情報を抜き取った商品リストを作成する処理
		//処理の中で商品コードが11桁未満の場合は追加処理を飛ばす。これは11桁に切り取る関数でのExceptionを避けるためと下記の理由のため。
		//CSVファイルの中には在庫一覧に存在するが、数字11桁+アルファベットの商品が存在するため11ケタに切らなければ正しい検索結果が取れない。

		/*
		 * TODO 楽天倉庫対象の商品かどうかを判別する。リストをkts倉庫用と楽天倉庫用で分ける。
		 * チェック内容は次の通り。
		 * １．楽天倉庫用のキープか　NO：kts倉庫へ
		 * ２．楽天倉庫用のキープならば同一受注伝票内にKTS倉庫対象の商品が存在する。　YES:KTS倉庫へ（警告メッセージを出力する。）
		 * ２．楽天倉庫用のキープならば楽天倉庫の在庫は存在するか　NO：KTS倉庫へ（警告メッセージを出力する。）
		 */
		List<ExtendKeepCsvImportDTO> csvItemList = new ArrayList<>();
		for (int i = 0; i < csvImportList.size(); i++) {

			ExtendKeepCsvImportDTO keepCsvImportDTO = new ExtendKeepCsvImportDTO();

			/*
			 *  商品種別が「内訳商品」でかつ名称に「RSL出荷分」が含まれていたら一つ前の
			 *  「セット商品」を楽天倉庫キープ対象とする。
			 */
			if (StringUtils.equals(csvImportList.get(i).getItemClassification(), "内訳商品")) {
				/*
				 *  商品種別が「セット商品」ならば楽天倉庫キープ対象とする。
				 *  ただし、次の条件の場合、楽天倉庫ではなくKTS倉庫のキープとなる。
				 *  ここでは、一度「セット商品」は楽天倉庫キープ対象として処理して、
				 *  条件チェックは別途行う。
				 *  ■条件
				 *  １．同一受注伝票内にKTS倉庫対象の商品がある。
				 *  ２．楽天倉庫の在庫が不足している。
				 */

				if (!csvImportList.get(i).getItemNm().contains("RSL出荷分")) {
					//内訳商品の商品名にRSL出荷分と含まれていなければキープしないのでキープ対象商品のリストから削除する。
					if (StringUtils.equals(csvItemList.get(csvItemList.size() - 1).getItemClassification(), "セット商品")) {
						csvItemList.remove(csvItemList.size() - 1);
					}
					continue;
				}


				// XXX 商品種別が「内訳商品」の次が「セット商品」になるので判断しなくてよいが念のため判断する。
				if (StringUtils.equals(csvItemList.get(csvItemList.size() - 1).getItemClassification(), "セット商品")) {
					csvItemList.get(csvItemList.size() - 1).setExternalKeepFlag("1");
					csvItemList.get(csvItemList.size() - 1).setExternalWarehouseCode("RSL");
				}
			}


			//商品種別が「商品」以外の時追加しない。
			if(!(csvImportList.get(i).getItemClassification().equals("商品")
					|| csvImportList.get(i).getItemClassification().equals("セット商品"))) {
				continue;
			}
			// 11桁以下の場合は自社商品ではないため追加しない。
			if (StringUtils.length(csvImportList.get(i).getShopItemCd()) < 11) {
				continue;
			}
			//商品コード
			keepCsvImportDTO.setShopItemCd(csvImportList.get(i).getShopItemCd().substring(0, 11));
			//受注番号
			keepCsvImportDTO.setOrderNo(csvImportList.get(i).getOrderNo());
			//個数を追加
			keepCsvImportDTO.setItemNum(csvImportList.get(i).getItemNum());
			//受注ルート
			keepCsvImportDTO.setOrderRoute(csvImportList.get(i).getOrderRoute());
			//システム法人ID
			keepCsvImportDTO.setSysCorporationId(csvImportList.get(i).getSysCorporationId());
			//取り込み日
			keepCsvImportDTO.setImportDate(csvImportList.get(i).getImportDate());

			//商品種別
			keepCsvImportDTO.setItemClassification(csvImportList.get(i).getItemClassification());

			csvItemList.add(keepCsvImportDTO);
		}

		//上の処理だけだとCSVファイル上の情報しかないため、ここで商品一つ一つを商品マスタから検索し詳細情報を付け加える。
		for(int i = 0; i < csvItemList.size(); i++) {

			ExtendKeepCsvImportDTO afterKeepCsvImport = csvItemList.get(i);
			MstItemDTO dto =  new MstItemDTO();
			dto = itemDAO.getMstItemForKeep(csvItemList.get(i).getShopItemCd());

			if (dto != null) {
				//商品コード
				afterKeepCsvImport.setItemCode(dto.getItemCode());
				//システム商品ID
				afterKeepCsvImport.setSysItemId(dto.getSysItemId());
				//出庫分類フラグをセット 通常商品の場合は出庫分類フラグがないため、代わりに”0”をセットしている
				if (dto.getLeaveClassFlg() != null) {
					afterKeepCsvImport.setLeaveClassFlg(dto.getLeaveClassFlg());
				} else {
					afterKeepCsvImport.setLeaveClassFlg("0");
				}
				//仕様メモ
				afterKeepCsvImport.setSpecMemo(dto.getSpecMemo());
				//指定の位置に上書き
				csvItemList.set(i, afterKeepCsvImport);
			}
		}


		/*
		 * この時点では
		 * ============
		 * 商品
		 * セット商品
		 * セット商品
		 * 商品
		 * ============
		 * のようなリストになっている。
		 *
		 * ここから楽天倉庫からKTS倉庫へ切り替えるための判断をする。
		 *  ■切り替える条件
		 *  １．同一受注伝票内にKTS倉庫対象の商品がある。（KTS倉庫へ）
		 *  ２．楽天倉庫の在庫が不足している。（KTS倉庫へ）
		 *  ３．同一受注伝票内に「２．」の条件でKTS倉庫をキープする商品がある。
		 *
		 */

		if (!csvItemList.isEmpty()) {
			// １．同一受注伝票内にKTS倉庫対象の商品があるか判断（KTS倉庫へ：RslKeepFlagを「0」に設定する。）
			String isRslKeep = csvItemList.get(0).getExternalKeepFlag();
			String orderNo = csvItemList.get(0).getOrderNo();

			//受注番号ごとにキープ先を保持する。
			Map<String, String> keepMap = new LinkedHashMap<String, String>();
			keepMap.put(orderNo, isRslKeep);

			for (int i = 1; i < csvItemList.size() ; i++) {

				// 同一受注番号でかつ比較対象のどちらかがKTS倉庫商品であればKST倉庫をキープする。
				if (StringUtils.equals(orderNo, csvItemList.get(i).getOrderNo())) {
					if ((isRslKeep == null || StringUtils.equals(isRslKeep, "0"))) {
						keepMap.put(orderNo, isRslKeep);
					}

					if (csvItemList.get(i).getExternalKeepFlag() == null
							|| StringUtils.equals(csvItemList.get(i).getExternalKeepFlag(), "0")) {
						keepMap.put(orderNo, csvItemList.get(i).getExternalKeepFlag());
					}
				} else {
					//受注番号が変わったら初期値を入れ替える。
					isRslKeep = csvItemList.get(i).getExternalKeepFlag();
					orderNo = csvItemList.get(i).getOrderNo();
					keepMap.put(orderNo, isRslKeep);
				}

			}

			//受注番号毎でKTS倉庫となっているもので商品が楽天倉庫キープであればKTS倉庫キープへ変更する。
			for (Map.Entry<String, String> entry: keepMap.entrySet()) {
				if (entry.getValue() == null || StringUtils.equals(entry.getValue(), "0")) {
					for (int j = 0; j < csvItemList.size(); j++) {
						if (StringUtils.equals(csvItemList.get(j).getOrderNo(), entry.getKey())) {

							if (StringUtils.equals(csvItemList.get(j).getExternalKeepFlag(), "1")) {
								csvItemList.get(j).setExternalKeepFlag("0");

								//キープ取込中にマイナスになった際に青色警告文の内容が赤と同じになってしまうため、ここでインスタンス化
								ErrorMessageDTO messageDTO = new ErrorMessageDTO();
								messageDTO.setErrorMessage("受注番号「" + csvItemList.get(j).getOrderNo() + "」の商品コード「"+ csvItemList.get(j).getItemCode() +"は同一受注番号内にKTS倉庫の商品が含まれる為、全ての商品をKTS倉庫分をキープしました。");
								errorMessageRedList.add(messageDTO);
							}
						}
					}
				}
			}

			//  ２．楽天倉庫の在庫が不足しているかの判断。（KTS倉庫へ：RslKeepFlagを「0」に設定する。）
			// キープしようとしている商品の楽天倉庫の在庫数を取得する。

			// 楽天倉庫キープとなっている受注番号を取得してその受注番号の商品を取得する。
			List<String> rslKeepOrderNoList = new ArrayList<String>();
			for (Map.Entry<String, String> entry: keepMap.entrySet()) {
				if (StringUtils.equals(entry.getValue(), "1")) {
					rslKeepOrderNoList.add(entry.getKey());
				}
			}

			List<ExtendKeepCsvImportDTO> rslKeepItemList = new ArrayList<>();
			if (!CollectionUtils.isEmpty(rslKeepOrderNoList)) {

				// 受注番号毎の商品を取得する。
				for (String rslKeepOrderNo : rslKeepOrderNoList) {
					for (ExtendKeepCsvImportDTO ekciDto : csvItemList) {
						if (StringUtils.equals(ekciDto.getOrderNo(), rslKeepOrderNo)) {
							rslKeepItemList.add(ekciDto);
						}
					}
				}


				// 商品毎の楽天倉庫キープリストを作成し楽天倉庫の在庫数を比較する。
				// 楽天倉庫の在庫数を超える受注番号はKTS倉庫キープとする。

				// 同じ商品を何度も在庫数チェックしないように楽天倉庫の在庫数をチェックした商品を保持するコレクションクラス。
				Set<Long> containsCheckedItem = new LinkedHashSet<Long>();

				// 楽天倉庫の在庫数を保持するコレクションクラス。
				List<ExtendWarehouseStockDTO> externalWarehouseStockList = new ArrayList<>();
				for (ExtendKeepCsvImportDTO rslKeepItem : rslKeepItemList) {

					// 在庫数をチェックした商品はチェックしないので次の商品をチェックする。
					if (containsCheckedItem.contains(rslKeepItem.getSysItemId())) {
						continue;
					}

					externalWarehouseStockList = itemDAO.getExternalStockList(rslKeepItem.getSysItemId(), rslKeepItem.getExternalWarehouseCode());

					//在庫数
					int warehouseStocknum = 0;

					// 在庫が存在しなければKTS倉庫キープとする。
					if (CollectionUtils.isEmpty(externalWarehouseStockList)) {
						keepMap.put(rslKeepItem.getOrderNo(), "0");
						continue;
					}

					for (ExtendWarehouseStockDTO dto : externalWarehouseStockList) {
						warehouseStocknum += dto.getStockNum();
					}

					//仮在庫数 = 在庫数 - キープ数
					int tmpWarehouseStocknum = warehouseStocknum;

					List<ExtendKeepDTO> keepList = itemDAO.getExternalKeepNumList(rslKeepItem.getSysItemId(), rslKeepItem.getExternalWarehouseCode());

					for (ExtendKeepDTO dto : keepList) {
						tmpWarehouseStocknum -= dto.getKeepNum();
					}


					// 仮在庫が０個ならばKTS倉庫キープとする。
					if (tmpWarehouseStocknum <= 0) {
						keepMap.put(rslKeepItem.getOrderNo(), "0");
						continue;
					}

					// 仮キープ数
					int tmpKeepNum = 0;
					for (int i = 0; i < rslKeepItemList.size(); i++) {

						if (rslKeepItem.getSysItemId() == rslKeepItemList.get(i).getSysItemId()) {
							tmpKeepNum += rslKeepItemList.get(i).getItemNum();
						}

						// 注文数が仮在庫数を超えた場合はその商品の受注番号をKTS倉庫キープへ変更する。
						if (tmpWarehouseStocknum < tmpKeepNum) {
							keepMap.put(rslKeepItemList.get(i).getOrderNo(), "0");

							/*
							 * ===キープのCSV===
							 * 受注番号：A
							 * 楽天倉庫商品A→注文数７
							 * 受注番号：B
							 * 楽天倉庫商品A→注文数５
							 * 受注番号：C
							 * 楽天倉庫商品A→注文数２
							 * ==================
							 * ↓
							 * =====楽天倉庫=====
							 * 商品A→仮在庫数10
							 * ==================
							 * この場合は、
							 * 受注番号：Aは楽天倉庫キープ
							 * 受注番号：BはKTS倉庫キープ
							 * 受注番号：Cは楽天倉庫キープ
							 *
							 * とするので注文数を差し戻す。
							 */
							tmpKeepNum -= rslKeepItemList.get(i).getItemNum();
						}
					}

					// チェック済みの商品を保持して次の商品のチェックからチェック済みの商品をチェックから除外する。
					containsCheckedItem.add(rslKeepItem.getSysItemId());
				}
			}

			// ３．同一受注伝票内に「２．」の条件でKTS倉庫をキープする商品がある。（KTS倉庫へ：RslKeepFlagを「0」に設定する。）
			// 楽天倉庫キープからKTS倉庫キープに切り替えた受注番号の商品を全てKTS倉庫キープへ切り替える。
			for (Map.Entry<String, String> entry: keepMap.entrySet()) {
				if (entry.getValue() == null || StringUtils.equals(entry.getValue(), "0")) {
					for (int j = 0; j < csvItemList.size(); j++) {
						if (StringUtils.equals(csvItemList.get(j).getOrderNo(), entry.getKey())) {

							if (StringUtils.equals(csvItemList.get(j).getExternalKeepFlag(), "1")) {
								csvItemList.get(j).setExternalKeepFlag("0");

								//キープ取込中にマイナスになった際に青色警告文の内容が赤と同じになってしまうため、ここでインスタンス化
								ErrorMessageDTO messageDTO = new ErrorMessageDTO();
								messageDTO.setErrorMessage("受注番号「" + csvItemList.get(j).getOrderNo() + "」の商品コード「"+ csvItemList.get(j).getItemCode() +"」は同一受注番号内に楽天倉庫の在庫数を超える在庫数をキープしようとした商品がある為、KTS倉庫分をキープしました。");
								errorMessagePurpleList.add(messageDTO);
							}

						}
					}
				}
			}
		}

		//商品をひとつずつ参照し、キープを追加していく
		//セット商品でなおかつ出庫分類が"1"の時、子要素を見に行く。これは子要素に"1"を持つ限り続く。
		//同じ受注番号のキープが存在するとき二重登録をふせぐため、updFlgというフラグを使用。
		for (int i = 0; i < csvItemList.size(); i++) {

			//国内の商品は出庫分類がないため、処理を飛ばす
			if(csvItemList.get(i).getLeaveClassFlg() == null) {
				continue;
			}

			//廃盤または受注生産の時キープを追加しないようにするため、仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする。
			if ((csvItemList.get(i).getSpecMemo() != null) && (csvItemList.get(i).getSpecMemo().indexOf("廃盤") != -1)) {
				String itemCode = csvItemList.get(i).getItemCode();
				String orderNo1 = csvItemList.get(i).getOrderNo();
				
				List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(csvItemList.get(i).getSysItemId());
				if (parentList != null) {
					System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
					ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
					errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
							"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
					errorMessageGreenList.add(errorMessageDTO);
				}
				else {
					ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
					errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
					errorMessageGreenList.add(errorMessageDTO);
				}
				continue;
			}
			if ((csvItemList.get(i).getSpecMemo() != null) && (csvItemList.get(i).getSpecMemo().indexOf("受注生産") != -1)) {
				String itemCode = csvItemList.get(i).getItemCode();
				String orderNo1 = csvItemList.get(i).getOrderNo();
				ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
				errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
				errorMessageYellowList.add(errorMessageDTO);
				continue;
			}


			if (csvItemList.get(i).getLeaveClassFlg().equals("0")) {

				// KTS倉庫キープ分
				//商品単位のキープリスト取得
//				System.out.println("STEP 1");
				List<ExtendKeepDTO> getKeepList = new ArrayList<>();			
				if (csvItemList.get(i).getExternalKeepFlag() == null || StringUtils.equals(csvItemList.get(i).getExternalKeepFlag(), "0")) {
					getKeepList.addAll(itemDAO.getKeepList(csvItemList.get(i).getSysItemId()));

					//キープに何も登録されていない時登録
//					System.out.println("STEP 2: keepList size = " + getKeepList.size() + " i = " + i 
//							+ " sys_item_id = " + csvItemList.get(i).getSysItemId());

					if (getKeepList.isEmpty()) {
//						System.out.println("STEP 3: KeepReg is called");
						keepReg(csvItemList.get(i));
						//追加件数を加算
						csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
					} else {

						//キープ重複登録を避けるための受注番号単位で更新したかを保存する更新フラグ
						String updFlg = "0";
						
						for (int n = 0; n < getKeepList.size(); n++) {

							//キープ内を一つずつ参照し、更新
							if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(i).getOrderNo())) {								
//								System.out.println("STEP 4: KeepUpd is called : sys_item_id = " + getKeepList.get(n).getSysItemId() 
//										+ " csv's = " + csvItemList.get(i).getSysItemId());
								keepUpd(csvItemList.get(i), getKeepList.get(n));
								//追加件数を加算
								csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
								updFlg = "1";
							}
						}
						//一度も更新されていなかったら登録処理
						if(StringUtils.equals(updFlg, "0")) {
							keepReg(csvItemList.get(i));
							//追加件数を加算
							csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
						}

					}
				} else {
					// 楽天倉庫キープ分
					getKeepList.addAll(itemDAO.getExternalKeepList(csvItemList.get(i).getSysItemId(), csvItemList.get(i).getExternalWarehouseCode()));

					//キープに何も登録されていない時登録
					if (getKeepList.isEmpty()) {
						externalKeepReg(csvItemList.get(i));
						//追加件数を加算
						csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);

					} else {

						//キープ重複登録を避けるための受注番号単位で更新したかを保存する更新フラグ
						String updFlg = "0";

						for (int n = 0; n < getKeepList.size(); n++) {

							//キープ内を一つずつ参照し、更新
							if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(i).getOrderNo())) {
								externalKeepUpd(csvItemList.get(i), getKeepList.get(n));
								//追加件数を加算
								csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
								updFlg = "1";
							}
						}
						//一度も更新されていなかったら登録処理
						if(StringUtils.equals(updFlg, "0")) {
							externalKeepReg(csvItemList.get(i));
							//追加件数を加算
							csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
						}

					}

				}


			} else if (csvItemList.get(i).getLeaveClassFlg().equals("1")) {

				//1層目の構成商品リストを取得
				List<ExtendSetItemDTO> secondItemList = new ItemService()
						.getSetItemInfoList(csvItemList.get(i).getSysItemId());

				for (int j = 0; j < secondItemList.size(); j++) {

					//廃盤または受注生産の時キープを追加しないようにするため仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
					if ((secondItemList.get(j).getSpecMemo() != null) && (secondItemList.get(j).getSpecMemo().indexOf("廃盤") != -1)) {
						String itemCode = itemDAO.getItemCode(secondItemList.get(j).getFormSysItemId());
						String orderNo1 = csvItemList.get(i).getOrderNo();
						
						List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(secondItemList.get(j).getSysItemId());
						if (parentList != null) {
//							System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
							ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
							errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
									"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
							errorMessageGreenList.add(errorMessageDTO);
						}
						else {
							ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
							errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
							errorMessageGreenList.add(errorMessageDTO);
						}
						continue;
					}
					if ((secondItemList.get(j).getSpecMemo() != null) && (secondItemList.get(j).getSpecMemo().indexOf("受注生産") != -1)) {
						String itemCode = itemDAO.getItemCode(secondItemList.get(j).getFormSysItemId());
						String orderNo1 = csvItemList.get(i).getOrderNo();
						ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
						errorMessageYellowList.add(errorMessageDTO);
						continue;
					}

					if (secondItemList.get(j).getLeaveClassFlg().equals("0")) {

						//商品単位のキープリスト取得
						List<ExtendKeepDTO> getKeepList = new ArrayList<>();
						getKeepList.addAll(itemDAO.getKeepList(secondItemList.get(j).getFormSysItemId()));

						//キープに何も登録されていない時登録
						if (getKeepList.isEmpty()) {
							keepReg(secondItemList.get(j), csvItemList.get(i));
							//追加件数を加算
							csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);

						} else {

							//キープ重複登録を避けるための受注番号単位で更新したかを保存する更新フラグ
							String updFlg = "0";

							for (int n = 0; n < getKeepList.size(); n++) {

								//キープ内を一つずつ参照、更新
								if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(i).getOrderNo())) {
									keepUpd(secondItemList.get(j), getKeepList.get(n), csvItemList.get(i));
									//追加件数を加算
									csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
									updFlg = "1";
								}
							}
							//一度も更新されていなかったら登録
							if(StringUtils.equals(updFlg, "0")) {
								keepReg(secondItemList.get(j), csvItemList.get(i));
								//追加件数を加算
								csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
							}

						}

					} else if(secondItemList.get(j).getLeaveClassFlg().equals("1")) {

						//2層目の構成商品リストを取得
						List<ExtendSetItemDTO> thirdItemList = new ItemService()
								.getSetItemInfoList(secondItemList.get(j).getFormSysItemId());

						for (int k = 0; k < thirdItemList.size(); k++) {

							//廃盤または受注生産の時キープを追加しないようにするため仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
							if ((thirdItemList.get(k).getSpecMemo() != null) && (thirdItemList.get(k).getSpecMemo().indexOf("廃盤") != -1)) {
								String itemCode = itemDAO.getItemCode(thirdItemList.get(k).getFormSysItemId());
								String orderNo1 = csvItemList.get(i).getOrderNo();
								
								List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(thirdItemList.get(k).getSysItemId());
								if (parentList != null) {
//									System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
									ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
									errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
											"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
									errorMessageGreenList.add(errorMessageDTO);
								}
								else {
									ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
									errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
									errorMessageGreenList.add(errorMessageDTO);
								}
								continue;
							}
							if ((thirdItemList.get(k).getSpecMemo() != null) && (thirdItemList.get(k).getSpecMemo().indexOf("受注生産") != -1)) {
								String itemCode = itemDAO.getItemCode(thirdItemList.get(k).getFormSysItemId());
								String orderNo1 = csvItemList.get(i).getOrderNo();
								ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
								errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
								errorMessageYellowList.add(errorMessageDTO);
								continue;
							}

							if (thirdItemList.get(k).getLeaveClassFlg().equals("0")) {

								//商品単位のキープリストを取得します
								List<ExtendKeepDTO> getKeepList = new ArrayList<>();
								getKeepList.addAll(itemDAO.getKeepList(thirdItemList.get(k).getFormSysItemId()));

								//キープに何もないとき登録
								if (getKeepList.isEmpty()) {
									keepReg(thirdItemList.get(k), csvItemList.get(i));
									//追加件数を加算
									csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);

								} else {
									//キープ重複登録を避けるための受注番号単位で更新したかを保存する更新フラグ
									String updFlg = "0";

									for (int n = 0; n < getKeepList.size(); n++) {

										//キープ内を一つずつ参照、更新
										if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(i).getOrderNo())) {
											keepUpd(thirdItemList.get(k), getKeepList.get(n), csvItemList.get(i));
											//追加件数を加算
											csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
											updFlg = "1";
										}
									}
									//一度も更新されていなかったら登録
									if (StringUtils.equals(updFlg, "0")) {
										keepReg(thirdItemList.get(k), csvItemList.get(i));
										//追加件数を加算
										csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
									}

								}

							} else if(thirdItemList.get(k).getLeaveClassFlg().equals("1")) {

								//3層目の構成商品リストを取得
								List<ExtendSetItemDTO> fourthItemList = new ItemService()
										.getSetItemInfoList(thirdItemList.get(k).getFormSysItemId());

								for (int l = 0; l < fourthItemList.size(); l++) {

									//廃盤または受注生産の時キープを追加しないようにするため仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
									if ((fourthItemList.get(l).getSpecMemo() != null) && (fourthItemList.get(l).getSpecMemo().indexOf("廃盤") != -1)) {
										String itemCode = itemDAO.getItemCode(fourthItemList.get(l).getFormSysItemId());
										String orderNo1 = csvItemList.get(i).getOrderNo();

										List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(fourthItemList.get(l).getSysItemId());
										if (parentList != null) {
//											System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
											ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
											errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
													"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
											errorMessageGreenList.add(errorMessageDTO);
										}
										else {
											ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
											errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
											errorMessageGreenList.add(errorMessageDTO);
										}
										continue;
									}
									if ((fourthItemList.get(l).getSpecMemo() != null) && (fourthItemList.get(l).getSpecMemo().indexOf("受注生産") != -1)) {
										String itemCode = itemDAO.getItemCode(fourthItemList.get(l).getFormSysItemId());
										String orderNo1 = csvItemList.get(i).getOrderNo();
										ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
										errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
										errorMessageYellowList.add(errorMessageDTO);
										continue;
									}

									if (fourthItemList.get(l).getLeaveClassFlg().equals("0")) {

										//商品単位のキープリスト取得
										List<ExtendKeepDTO> getKeepList = new ArrayList<>();
										getKeepList.addAll(itemDAO.getKeepList(fourthItemList.get(l).getFormSysItemId()));

										//キープに何もないとき登録
										if (getKeepList.isEmpty()) {
											keepReg(fourthItemList.get(l), csvItemList.get(i));
											//追加件数を加算
											csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);

										} else {

											//キープ重複登録を避けるための受注番号単位で更新したかを保存する更新フラグ
											String updFlg = "0";

											for (int n = 0; n < getKeepList.size(); n++) {

												//キープ内を一つずつ参照し、更新
												if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(i).getOrderNo())) {
													keepUpd(fourthItemList.get(l), getKeepList.get(n), csvItemList.get(i));
													//追加件数を加算
													csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
													updFlg = "1";
												}
											}
											//一度も更新されていなかったら登録
											if(StringUtils.equals(updFlg, "0")) {
												keepReg(fourthItemList.get(l), csvItemList.get(i));
												//追加件数を加算
												csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
											}
										}

									} else if(fourthItemList.get(l).getLeaveClassFlg().equals("1")) {
										//4層目の構成商品リスト取得
										List<ExtendSetItemDTO> fifthItemList = new ItemService()
												.getSetItemInfoList(fourthItemList.get(l).getFormSysItemId());

										for (int m = 0; m < fifthItemList.size(); m++) {

											long fifthItemFormSysItemId = fifthItemList.get(m).getFormSysItemId();

											//廃盤または受注生産の時キープを追加しないようにするため、仕様メモに「廃盤」または「受注生産」の文字が含まれているかチェックする
											if ((fifthItemList.get(m).getSpecMemo() != null) && (fifthItemList.get(m).getSpecMemo().indexOf("廃盤") != -1)) {
												String itemCode = itemDAO.getItemCode(fifthItemFormSysItemId);
												String orderNo1 = csvItemList.get(i).getOrderNo();

												List<ExtendSetItemDTO> parentList = itemDAO.getParentSetItemList(fifthItemList.get(m).getSysItemId());
												if (parentList != null) {
//													System.out.println("Parent Item's id, code " + parentList.get(0).getSysItemId() + ":" + parentList.get(0).getItemCode());
													ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
													errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」: セット品番「" + parentList.get(0).getItemCode() +
															"」 品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
													errorMessageGreenList.add(errorMessageDTO);
												}
												else {
													ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
													errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は廃盤商品の為キープされませんでした。");
													errorMessageGreenList.add(errorMessageDTO);
												}
												continue;
											}
											if ((fifthItemList.get(m).getSpecMemo() != null) && (fifthItemList.get(m).getSpecMemo().indexOf("受注生産") != -1)) {
												String itemCode = itemDAO.getItemCode(fifthItemFormSysItemId);
												String orderNo1 = csvItemList.get(i).getOrderNo();
												ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
												errorMessageDTO.setErrorMessage("注文番号「" + orderNo1 + "」:品番「" + itemCode + "」の商品は受注生産品の為キープされませんでした。");
												errorMessageYellowList.add(errorMessageDTO);
												continue;
											}

											if (fifthItemList.get(m).getLeaveClassFlg().equals("0")) {

												//商品単位のキープリスト取得
												List<ExtendKeepDTO> getKeepList = new ArrayList<>();
												getKeepList.addAll(itemDAO.getKeepList(fifthItemFormSysItemId));

												//キープに何もないとき登録
												if (getKeepList.isEmpty()) {
													keepReg(fifthItemList.get(m), csvItemList.get(i));
													//追加件数を加算
													csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);

												} else {

													//キープ重複登録を避けるための受注番号単位で更新したかを保存する更新フラグ
													String updFlg ="0";

													for (int n = 0; n < getKeepList.size(); n++) {
														//キープ内を一つずつ参照、更新
														if(StringUtils.equals(getKeepList.get(n).getOrderNo(), csvItemList.get(i).getOrderNo())) {
															keepUpd(fifthItemList.get(m), getKeepList.get(n), csvItemList.get(i));
															//追加件数を加算
															csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
															updFlg = "1";
														}
													}
													//一度も更新されていなかったら登録
													if (StringUtils.equals(updFlg, "0")) {
														keepReg(fifthItemList.get(m),  csvItemList.get(i));
														//追加件数を加算
														csvErrorDTO.setTrueCount(csvErrorDTO.getTrueCount() + 1);
													}
												}
											}

											//メッセージ表示処理(構成商品4層目)
											List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(fifthItemFormSysItemId);
											String arrivalScheduleDate = "";
											//在庫数を取得と品番を取得
											int temporaryStockNum = itemDAO.getTemporaryStockNum(fifthItemFormSysItemId);
											String setItemCode = itemDAO.getItemCode(fifthItemFormSysItemId);
											//エラーメッセージ
											//入荷数によって入荷予定日を切り替えるために使用するマップ
											Map<String, Integer> alertDispitemMap = new LinkedHashMap<>();
											//仮在庫数が１つ以上の時、入荷数減算処理にはキープ個数 - 仮在庫数の値になるのでその前処理用変数
											int calcItemNum = csvItemList.get(i).getItemNum();

											//入荷予定日を設定する処理
											if (arrivalScheduleList == null) {
												arrivalScheduleDate = "未定　　　　";
											} else {
												/*
												 * 仮在庫数がキープ個数で引かれることによってマイナスになった時、入荷数によって入荷予定日を切り替える処理に
												 * 使用する数値を「キープ個数 - キープ個数が引かれる前の個数」の値にするための条件式
												 * */
												//現在の仮在庫数が0未満の時のみ
												if (temporaryStockNum < 0) {
													//現在の仮在庫数とキープ個数を足したものがマイナスになる前の仮在庫数になる
													calcItemNum = temporaryStockNum + csvItemList.get(i).getItemNum();
													//マイナスになる前の仮在庫数だった場合
													if (calcItemNum >= 0 ) {
														//キープ個数からマイナス前の仮在庫を引くことで、入荷数によって入荷予定日を切り替える処理に目当ての値が送られる
														calcItemNum =  csvItemList.get(i).getItemNum() - calcItemNum;
													} else {
														//前の仮在庫数が既にマイナスの場合は普通にキープ個数を使う
														calcItemNum = csvItemList.get(i).getItemNum();
													}
												}
												//入荷数によって入荷予定日を切り替える処理
												arrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, setItemCode, -(temporaryStockNum + calcItemNum), calcItemNum);
											}

											if(temporaryStockNum < 0) {
												ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
												errorMessageDTO.setErrorMessage( "品番「" + setItemCode + "」の在庫数がマイナスになりました　　"
																										+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																										+ csvItemList.get(i).getOrderNo());
												errorMessageRedList.add(errorMessageDTO);
												alertDispitemMap.put(setItemCode, calcItemNum);
												alertDispItemMapList.add(alertDispitemMap);
											} else if(temporaryStockNum < 3) {
												ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
												errorMessageDTO.setErrorMessage("品番「" + setItemCode + "」の在庫数が3個を切りました　　　　 "
														+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																										+ csvItemList.get(i).getOrderNo());
												errorMessageBlueList.add(errorMessageDTO);
//												alertDispitemMap.put(setItemCode, calcItemNum);
//												alertDispItemMapList.add(alertDispitemMap);
											}
										}
									}

									//セット商品で「構成商品から出庫」する場合はメッセージを表示しない制御。
									//構成商品から出庫するセット商品の在庫数が0の時も警告文が表示されてしまうため。
									if (fourthItemList.get(l).getLeaveClassFlg().equals("1")) {
										continue;
									}

									//メッセージ表示処理(構成商品3層目)
									List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(fourthItemList.get(l).getFormSysItemId());
									String arrivalScheduleDate = "";
									int temporaryStockNum = itemDAO.getTemporaryStockNum(fourthItemList.get(l).getFormSysItemId());
									String setItemCode = itemDAO.getItemCode(fourthItemList.get(l).getFormSysItemId());
									//キープ取込中にマイナスになった際に青色警告文の内容が赤と同じになってしまうため、ここでインスタンス化
									//入荷数によって入荷予定日を切り替えるために使用するマップ
									Map<String, Integer> alertDispitemMap = new LinkedHashMap<>();
									//仮在庫数が１つ以上の時、入荷数減算処理にはキープ個数 - 仮在庫数の値になるのでその前処理用変数
									int calcItemNum = csvItemList.get(i).getItemNum();

									//入荷予定日を設定する処理
									if (arrivalScheduleList == null) {
										arrivalScheduleDate = "未定　　　　";
									} else {
										/*
										 * 仮在庫数がキープ個数で引かれることによってマイナスになった時、入荷数によって入荷予定日を切り替える処理に
										 * 使用する数値を「キープ個数 - キープ個数が引かれる前の個数」の値にするための条件式
										 * */
										//現在の仮在庫数が0未満の時のみ
										if (temporaryStockNum < 0) {
											//現在の仮在庫数とキープ個数を足したものがマイナスになる前の仮在庫数になる
											calcItemNum = temporaryStockNum + csvItemList.get(i).getItemNum();
											//マイナスになる前の仮在庫数だった場合
											if (calcItemNum >= 0 ) {
												//キープ個数からマイナス前の仮在庫を引くことで、入荷数によって入荷予定日を切り替える処理に目当ての値が送られる
												calcItemNum =  csvItemList.get(i).getItemNum() - calcItemNum;
											} else {
												//前の仮在庫数が既にマイナスの場合は普通にキープ個数を使う
												calcItemNum = csvItemList.get(i).getItemNum();
											}
										}
										//入荷数によって入荷予定日を切り替える処理
										arrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, setItemCode, -(temporaryStockNum + calcItemNum),calcItemNum);
									}

									if(temporaryStockNum < 0) {
										ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
										errorMessageDTO.setErrorMessage( "品番「" + setItemCode + "」の在庫数がマイナスになりました　　"
																								+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																								+ csvItemList.get(i).getOrderNo());
										errorMessageRedList.add(errorMessageDTO);
										alertDispitemMap.put(setItemCode, calcItemNum);
										alertDispItemMapList.add(alertDispitemMap);
									} else if(temporaryStockNum < 3) {
										ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
										errorMessageDTO.setErrorMessage("品番「" + setItemCode + "」の在庫数が3個を切りました　　　　 "
												+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																								+ csvItemList.get(i).getOrderNo());
										errorMessageBlueList.add(errorMessageDTO);
//										alertDispitemMap.put(setItemCode, calcItemNum);
//										alertDispItemMapList.add(alertDispitemMap);
									}
								}
							}

							//セット商品で「構成商品から出庫」する場合はメッセージを表示しない制御。
							//構成商品から出庫するセット商品の在庫数が0の時も警告文が表示されてしまうため。
							if (thirdItemList.get(k).getLeaveClassFlg().equals("1")) {
								continue;
							}

							//メッセージ表示処理(構成商品2層目)
							List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(thirdItemList.get(k).getFormSysItemId());
							String arrivalScheduleDate = "";
							int temporaryStockNum = itemDAO.getTemporaryStockNum(thirdItemList.get(k).getFormSysItemId());
							String setItemCode = itemDAO.getItemCode(thirdItemList.get(k).getFormSysItemId());
							//キープ取込中にマイナスになった際に青色警告文の内容が赤と同じになってしまうため、ここでインスタンス化
							//入荷数によって入荷予定日を切り替えるために使用するマップ
							Map<String, Integer> alertDispitemMap = new LinkedHashMap<>();
							//仮在庫数が１つ以上の時、入荷数減算処理にはキープ個数 - 仮在庫数の値になるのでその前処理用変数
							int calcItemNum = csvItemList.get(i).getItemNum();

							//入荷予定日を設定する処理
							if (arrivalScheduleList == null) {
								arrivalScheduleDate = "未定　　　　";
							} else {
								/*
								 * 仮在庫数がキープ個数で引かれることによってマイナスになった時、入荷数によって入荷予定日を切り替える処理に
								 * 使用する数値を「キープ個数 - キープ個数が引かれる前の個数」の値にするための条件式
								 * */
								//現在の仮在庫数が0未満の時のみ
								if (temporaryStockNum < 0) {
									//現在の仮在庫数とキープ個数を足したものがマイナスになる前の仮在庫数になる
									calcItemNum = temporaryStockNum + csvItemList.get(i).getItemNum();
									//マイナスになる前の仮在庫数だった場合
									if (calcItemNum >= 0 ) {
										//キープ個数からマイナス前の仮在庫を引くことで、入荷数によって入荷予定日を切り替える処理に目当ての値が送られる
										calcItemNum =  csvItemList.get(i).getItemNum() - calcItemNum;
									} else {
										//前の仮在庫数が既にマイナスの場合は普通にキープ個数を使う
										calcItemNum = csvItemList.get(i).getItemNum();
									}
								}
								//入荷数によって入荷予定日を切り替える処理
								arrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, setItemCode, -(temporaryStockNum + calcItemNum), calcItemNum);
							}

							if(temporaryStockNum < 0) {
								ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
								errorMessageDTO.setErrorMessage("品番「" + setItemCode + "」の在庫数がマイナスになりました　　"
																						+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																						+ csvItemList.get(i).getOrderNo());
								errorMessageRedList.add(errorMessageDTO);
								alertDispitemMap.put(setItemCode, calcItemNum);
								alertDispItemMapList.add(alertDispitemMap);
							} else if(temporaryStockNum < 3) {
								ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
								errorMessageDTO.setErrorMessage("品番「" + setItemCode + "」の在庫数が3個を切りました　　　　 "
										+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																						+ csvItemList.get(i).getOrderNo());
								errorMessageBlueList.add(errorMessageDTO);
//								alertDispitemMap.put(setItemCode, calcItemNum);
//								alertDispItemMapList.add(alertDispitemMap);
							}

						}
					}

					//セット商品で「構成商品から出庫」する場合はメッセージを表示しない制御。
					//構成商品から出庫するセット商品の在庫数が0の時も警告文が表示されてしまうため。
					if (secondItemList.get(j).getLeaveClassFlg().equals("1")) {
						continue;
					}

					//メッセージ表示処理(構成商品1層目)
					List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(secondItemList.get(j).getFormSysItemId());
					String arrivalScheduleDate = "";
					//在庫数と品番を取得
					int temporaryStockNum = itemDAO.getTemporaryStockNum(secondItemList.get(j).getFormSysItemId());
					String setItemCode = itemDAO.getItemCode(secondItemList.get(j).getFormSysItemId());
					//キープ取込中にマイナスになった際に青色警告文の内容が赤と同じになってしまうため、ここでインスタンス化
					//入荷数によって入荷予定日を切り替えるために使用するマップ
					Map<String, Integer> alertDispitemMap = new LinkedHashMap<>();
					//仮在庫数が１つ以上の時、入荷数減算処理にはキープ個数 - 仮在庫数の値になるのでその前処理用変数
					int calcItemNum = csvItemList.get(i).getItemNum();

					//入荷予定日を設定する処理
					if (arrivalScheduleList == null) {
						arrivalScheduleDate = "未定　　　　";
					} else {
						/*
						 * 仮在庫数がキープ個数で引かれることによってマイナスになった時、入荷数によって入荷予定日を切り替える処理に
						 * 使用する数値を「キープ個数 - キープ個数が引かれる前の個数」の値にするための条件式
						 * */
						//現在の仮在庫数が0未満の時のみ
						if (temporaryStockNum < 0) {
							//現在の仮在庫数とキープ個数を足したものがマイナスになる前の仮在庫数になる
							calcItemNum = temporaryStockNum + csvItemList.get(i).getItemNum();
							//マイナスになる前の仮在庫数だった場合
							if (calcItemNum >= 0 ) {
								//キープ個数からマイナス前の仮在庫を引くことで、入荷数によって入荷予定日を切り替える処理に目当ての値が送られる
								calcItemNum =  csvItemList.get(i).getItemNum() - calcItemNum;
							} else {
								//前の仮在庫数が既にマイナスの場合は普通にキープ個数を使う
								calcItemNum = csvItemList.get(i).getItemNum();
							}
						}
						//入荷数によって入荷予定日を切り替える処理
						arrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, setItemCode, -(temporaryStockNum + calcItemNum), calcItemNum);
					}

					if(temporaryStockNum < 0) {
						ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setErrorMessage("品番「" + setItemCode + "」の在庫数がマイナスになりました　　"
																				+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																				+ csvItemList.get(i).getOrderNo());
						errorMessageRedList.add(errorMessageDTO);
						alertDispitemMap.put(setItemCode, calcItemNum);
						alertDispItemMapList.add(alertDispitemMap);
					} else if(temporaryStockNum < 3) {
						ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setErrorMessage("品番「" + setItemCode + "」の在庫数が3個を切りました　　　　 "
								+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																				+ csvItemList.get(i).getOrderNo());
						errorMessageBlueList.add(errorMessageDTO);
						System.out.println("4品番「" + setItemCode + "」の在庫数が3個を切りました　　　　 "
								+ "入荷予定日　" + arrivalScheduleDate + "　　　"
								+ csvItemList.get(i).getOrderNo());

//						alertDispitemMap.put(setItemCode, calcItemNum);
//						alertDispItemMapList.add(alertDispitemMap);
					}
				}
			}

			//セット商品で「構成商品から出庫」する場合はメッセージを表示しない制御。
			//構成商品から出庫するセット商品の在庫数が0の時も警告文が表示されてしまうため。
			if (csvItemList.get(i).getLeaveClassFlg().equals("1")) {
				continue;
			}

			// 楽天倉庫キープ分の場合はメッセージを表示しない。
			// 楽天倉庫から出庫する商品のKTS倉庫在庫数が0の時も警告文が表示されてしまうため
			if (StringUtils.equals(csvItemList.get(i).getExternalKeepFlag(), "1")) {
				continue;
			}


			//メッセージ表示処理
			List<ArrivalScheduleDTO> arrivalScheduleList = itemDAO.getArrivalScheduleDate(csvItemList.get(i).getSysItemId());
			String arrivalScheduleDate = "";
			//在庫数と品番を取得
			int temporaryStockNum = itemDAO.getTemporaryStockNum(csvItemList.get(i).getItemCode());
			String itemCode = csvItemList.get(i).getItemCode();
//			System.out.println("STEP 10: temporaryStockNum = " + temporaryStockNum 
//					+ " itemCode = " + csvItemList.get(i).getOrderNo());

			//キープ取込中にマイナスになった際に青色警告文の内容が赤と同じになってしまうため、ここでインスタンス化
			//入荷数によって入荷予定日を切り替えるために使用するマップ
			Map<String, Integer> alertDispitemMap = new LinkedHashMap<>();
			//仮在庫数が1つ以上の時、入荷数減算処理にはキープ個数 - 仮在庫数の値になるのでその前処理用変数
			int calcItemNum = csvItemList.get(i).getItemNum();

			//入荷予定日を設定する処理
			if (arrivalScheduleList == null) {
				arrivalScheduleDate = "未定　　　　";
			} else {
				/*
				 * 仮在庫数がキープ個数で引かれることによってマイナスになった時、入荷数によって入荷予定日を切り替える処理に
				 * 使用する数値を「キープ個数 - キープ個数が引かれる前の個数」の値にするための条件式
				 * */
				//現在の仮在庫数が0未満の時のみ
				if (temporaryStockNum < 0) {
					//現在の仮在庫数とキープ個数を足したものがマイナスになる前の仮在庫数になる
					calcItemNum = temporaryStockNum + csvItemList.get(i).getItemNum();
					//マイナスになる前の仮在庫数だった場合
					if (calcItemNum >= 0 ) {
						//キープ個数からマイナス前の仮在庫を引くことで、入荷数によって入荷予定日を切り替える処理に目当ての値が送られる
						calcItemNum =  csvItemList.get(i).getItemNum() - calcItemNum;
					} else {
						//前の仮在庫数が既にマイナスの場合は普通にキープ個数を使う
						calcItemNum = csvItemList.get(i).getItemNum();
					}
				}
				//入荷数によって入荷予定日を切り替える処理
				arrivalScheduleDate = chooseArrivalScheduleDate(alertDispItemMapList, arrivalScheduleList, itemCode, -(temporaryStockNum + calcItemNum), calcItemNum);
			}

			if(temporaryStockNum < 0) {
				ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
				errorMessageDTO.setErrorMessage("品番「" + itemCode + "」の在庫数がマイナスになりました　　"
																		+ "入荷予定日　" + arrivalScheduleDate  + "　　　"
																		+ csvItemList.get(i).getOrderNo());
				errorMessageRedList.add(errorMessageDTO);
				//警告文を格納した品番をリストに追加
				alertDispitemMap.put(itemCode, calcItemNum);
				alertDispItemMapList.add(alertDispitemMap);
			} else if (temporaryStockNum < 3) {
				ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
				errorMessageDTO.setErrorMessage("品番「" + itemCode + "」の在庫数が3個を切りました　　　　 "
						+ "入荷予定日　" + arrivalScheduleDate + "　　　"
																		+ csvItemList.get(i).getOrderNo());
				errorMessageBlueList.add(errorMessageDTO);
				//警告文を格納した品番をリストに追加
//				alertDispitemMap.put(itemCode, calcItemNum);
//				alertDispItemMapList.add(alertDispitemMap);
			}
		}

		//警告文赤：品番でまとめるため、文字列でソート
		Collections.sort(errorMessageRedList, new Comparator<ErrorMessageDTO>() {

			@Override
			public int compare(ErrorMessageDTO emDTO1, ErrorMessageDTO emDTO2) {

				return (emDTO1.getErrorMessage().compareTo(emDTO2.getErrorMessage()));
			}
		});
		//警告文青：品番でまとめるため、文字列でソート
		Collections.sort(errorMessageBlueList, new Comparator<ErrorMessageDTO>() {

			@Override
			public int compare(ErrorMessageDTO emDTO1, ErrorMessageDTO emDTO2) {

				return (emDTO1.getErrorMessage().compareTo(emDTO2.getErrorMessage()));
			}
		});

		csvErrorDTO.setErrorMessageList(errorMessageRedList);
		csvErrorDTO.setErrorMessageListBlue(errorMessageBlueList);
		csvErrorDTO.setErrorMessageListGreen(errorMessageGreenList);
		csvErrorDTO.setErrrorMessageListYellow(errorMessageYellowList);
		csvErrorDTO.setErrorMessageGreenMap(errorMessageGreenMap);
		csvErrorDTO.setErrorMessageYellowMap(errorMessageYellowMap);
		csvErrorDTO.setErrorMessageListPurple(errorMessagePurpleList);

		return csvErrorDTO;
	}

	/**
	 * キープの登録処理
	 * @param itemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void keepReg(ExtendKeepCsvImportDTO keepCsvImportDTO) throws DaoException {

		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO = new CorporationDAO();
		ItemService service = new ItemService();
		
		//キープの各項目に追加
		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		SequenceDAO sequenceDAO = new SequenceDAO();
		extendKeepDTO.setSysKeepId(sequenceDAO.getMaxSysKeepId() + 1);
		extendKeepDTO.setSysItemId(keepCsvImportDTO.getSysItemId());
		extendKeepDTO.setOrderNo(keepCsvImportDTO.getOrderNo());
		extendKeepDTO.setKeepNum(keepCsvImportDTO.getItemNum());
		extendKeepDTO.setRemarks(keepCsvImportDTO.getImportDate() + " "
														+ corporationDAO.getCorporationName(keepCsvImportDTO.getSysCorporationId()) + " "
														+ keepCsvImportDTO.getOrderRoute());
		
			//登録処理
		System.out.println("KeepReg: syskeepid, keepnum" + extendKeepDTO.getSysKeepId() + ":" + keepCsvImportDTO.getItemNum());
			itemDAO.registryKeep(extendKeepDTO);
			//総在庫数、仮在庫数更新
			service.updateTotalStockNum(keepCsvImportDTO.getSysItemId());
	}

	/**
	 * 外部倉庫キープの登録処理
	 * @param itemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void externalKeepReg(ExtendKeepCsvImportDTO keepCsvImportDTO) throws DaoException {

		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO = new CorporationDAO();

		//キープの各項目に追加
		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		SequenceDAO sequenceDAO = new SequenceDAO();
		extendKeepDTO.setSysExternalKeepId(sequenceDAO.getMaxSysExternalKeepId() + 1);
		extendKeepDTO.setSysItemId(keepCsvImportDTO.getSysItemId());
		extendKeepDTO.setOrderNo(keepCsvImportDTO.getOrderNo());
		extendKeepDTO.setKeepNum(keepCsvImportDTO.getItemNum());
		extendKeepDTO.setRemarks(keepCsvImportDTO.getImportDate() + " "
														+ corporationDAO.getCorporationName(keepCsvImportDTO.getSysCorporationId()) + " "
														+ keepCsvImportDTO.getOrderRoute());
		extendKeepDTO.setSysExternalWarehouseCode(keepCsvImportDTO.getExternalWarehouseCode());

			//登録処理
			itemDAO.registryExternalKeep(extendKeepDTO);
			// XXX 外部倉庫の在庫数、キープ数は商品の総在庫数、仮在庫数に連動しない。
			// 総在庫数＝KTS倉庫の在庫数 ※楽天倉庫の在庫数は含まれない。
			// 仮在庫数＝総在庫数－(KTS倉庫のキープ数） ※楽天倉庫のキープ数は含まれない。

	}

	/**
	 * キープの更新処理(通常商品用)
	 * @param itemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void keepUpd(ExtendKeepCsvImportDTO KeepCsvImportDTO, ExtendKeepDTO keepDTO) throws DaoException {

		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO =  new CorporationDAO();
		ItemService service = new ItemService();

		//キープの各項目に追加
		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		extendKeepDTO.setSysKeepId(keepDTO.getSysKeepId());
		extendKeepDTO.setOrderNo(KeepCsvImportDTO.getOrderNo());
//		extendKeepDTO.setKeepNum(KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum());
		extendKeepDTO.setKeepNum(KeepCsvImportDTO.getItemNum());
		extendKeepDTO.setRemarks( KeepCsvImportDTO.getImportDate() + " "
														+ corporationDAO.getCorporationName(KeepCsvImportDTO.getSysCorporationId())  + " "
														+ KeepCsvImportDTO.getOrderRoute());
		
		//更新処理
		System.out.println("KeepUPD: syskeepid, keepnum" + extendKeepDTO.getSysKeepId() + ":" + (KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum()));
//		keepDTO.setKeepNum(KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum());		

		itemDAO.updateKeep(extendKeepDTO);
		//総在庫数、仮在庫数更新
		service.updateTotalStockNum(KeepCsvImportDTO.getSysItemId());
	}

	/**
	 * キープの更新処理(通常商品用)
	 * @param itemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void keepMinusUpd(ExtendKeepCsvImportDTO KeepCsvImportDTO, ExtendKeepDTO keepDTO) throws DaoException {
		System.out.println("keepMinusUpd1: order no = " + KeepCsvImportDTO.getOrderNo());

		int newNum = -1 * KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum();
		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO =  new CorporationDAO();
		ItemService service = new ItemService();

		//キープの各項目に追加
		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		extendKeepDTO.setSysKeepId(keepDTO.getSysKeepId());
		extendKeepDTO.setOrderNo(KeepCsvImportDTO.getOrderNo());
		extendKeepDTO.setKeepNum(newNum);
		extendKeepDTO.setRemarks( KeepCsvImportDTO.getImportDate() + " "
														+ corporationDAO.getCorporationName(KeepCsvImportDTO.getSysCorporationId())  + " "
														+ KeepCsvImportDTO.getOrderRoute());
		
		//更新処理
		keepDTO.setKeepNum(newNum);		

		itemDAO.updateKeep(extendKeepDTO);
		//総在庫数、仮在庫数更新
		service.updateTotalStockNum(KeepCsvImportDTO.getSysItemId());
//		// remove it
		if (newNum <= 0)
			service.deleteKeep(keepDTO.getSysKeepId());
	}

	/**
	 * 外部倉庫キープの更新処理(通常商品用)
	 * @param itemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void externalKeepUpd(ExtendKeepCsvImportDTO KeepCsvImportDTO, ExtendKeepDTO keepDTO) throws DaoException {

		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO =  new CorporationDAO();

		//キープの各項目に追加
		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		extendKeepDTO.setSysExternalKeepId(keepDTO.getSysExternalKeepId());
		extendKeepDTO.setOrderNo(KeepCsvImportDTO.getOrderNo());
//		extendKeepDTO.setKeepNum(KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum());
		extendKeepDTO.setKeepNum(KeepCsvImportDTO.getItemNum());
		extendKeepDTO.setRemarks( KeepCsvImportDTO.getImportDate() + " "
														+ corporationDAO.getCorporationName(KeepCsvImportDTO.getSysCorporationId())  + " "
														+ KeepCsvImportDTO.getOrderRoute());
		extendKeepDTO.setSysExternalWarehouseCode(KeepCsvImportDTO.getExternalWarehouseCode());

		//更新処理
		System.out.println("ExternalKeepUPD: syskeepid, keepnum" + extendKeepDTO.getSysKeepId() + ":" + (KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum()));
//		keepDTO.setKeepNum(KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum());		

		itemDAO.updateExternalKeep(extendKeepDTO);
		// XXX 外部倉庫の在庫数、キープ数は商品の総在庫数、仮在庫数に連動しない。
		// 総在庫数＝KTS倉庫の在庫数 ※楽天倉庫の在庫数は含まれない。
		// 仮在庫数＝総在庫数－(KTS倉庫のキープ数） ※楽天倉庫のキープ数は含まれない。
	}

	/**
	 * 外部倉庫キープの更新処理(通常商品用)
	 * @param itemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void externalKeepMinusUpd(ExtendKeepCsvImportDTO KeepCsvImportDTO, ExtendKeepDTO keepDTO) throws DaoException {
		System.out.println("externalKeepMinusUpd: order no = " + keepDTO.getSysExternalKeepId());
		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO =  new CorporationDAO();

		//キープの各項目に追加
		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		extendKeepDTO.setSysExternalKeepId(keepDTO.getSysExternalKeepId());
		extendKeepDTO.setOrderNo(KeepCsvImportDTO.getOrderNo());
		extendKeepDTO.setKeepNum(-1 * KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum());
		extendKeepDTO.setRemarks( KeepCsvImportDTO.getImportDate() + " "
														+ corporationDAO.getCorporationName(KeepCsvImportDTO.getSysCorporationId())  + " "
														+ KeepCsvImportDTO.getOrderRoute());
		extendKeepDTO.setSysExternalWarehouseCode(KeepCsvImportDTO.getExternalWarehouseCode());

		//更新処理
		keepDTO.setKeepNum(-1 * KeepCsvImportDTO.getItemNum() + keepDTO.getKeepNum());		

		itemDAO.updateExternalKeep(extendKeepDTO);
		ItemService service = new ItemService();
		service.deleteExternalKeep(keepDTO.getSysExternalKeepId());
	}
	/**
	 * キープの登録処理(セット商品用)
	 * @param itemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void keepReg(ExtendSetItemDTO setItemDTO, ExtendKeepCsvImportDTO keepCsvImportDTO) throws DaoException {

		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO = new CorporationDAO();
		ItemService service = new ItemService();

		//キープの各項目に追加
		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		SequenceDAO sequenceDAO = new SequenceDAO();
		extendKeepDTO.setSysKeepId(sequenceDAO.getMaxSysKeepId() + 1);
		extendKeepDTO.setSysItemId(setItemDTO.getFormSysItemId());
		extendKeepDTO.setOrderNo(keepCsvImportDTO.getOrderNo());
		//セット商品を構成する商品には必要個数が設定されているため、構成商品の必要数にキープCSVの個数をかける。
		extendKeepDTO.setKeepNum(keepCsvImportDTO.getItemNum() * setItemDTO.getItemNum());
		extendKeepDTO.setRemarks( keepCsvImportDTO.getImportDate() + " "
														+ corporationDAO.getCorporationName(keepCsvImportDTO.getSysCorporationId())  + " "
														+ keepCsvImportDTO.getOrderRoute());
		//登録処理
		itemDAO.registryKeep(extendKeepDTO);
		//総在庫数、仮在庫数更新
		service.updateTotalStockNum(setItemDTO.getFormSysItemId());

	}

	/**
	 * キープの更新処理(セット商品用)
	 * @param setItemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void keepUpd(ExtendSetItemDTO setItemDTO, ExtendKeepDTO keepDTO, ExtendKeepCsvImportDTO keepCsvImportDTO) throws DaoException {

		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO = new CorporationDAO();
		ItemService service = new ItemService();

		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		extendKeepDTO.setSysKeepId(keepDTO.getSysKeepId());
		extendKeepDTO.setOrderNo(keepCsvImportDTO.getOrderNo());
		//セット商品を構成する商品には必要個数が設定されているため、構成商品の必要数にキープCSVの個数をかける。
//		extendKeepDTO.setKeepNum(keepCsvImportDTO.getItemNum() * setItemDTO.getItemNum() + keepDTO.getKeepNum());
		extendKeepDTO.setKeepNum(keepCsvImportDTO.getItemNum() * setItemDTO.getItemNum());
		extendKeepDTO.setRemarks(keepCsvImportDTO.getImportDate() +" "
														+ corporationDAO.getCorporationName(keepCsvImportDTO.getSysCorporationId()) + " "
														+ keepCsvImportDTO.getOrderRoute());
		//更新処理
//		keepDTO.setKeepNum(keepCsvImportDTO.getItemNum() * setItemDTO.getItemNum() + keepDTO.getKeepNum());
		itemDAO.updateKeep(extendKeepDTO);
		//総在庫数、仮在庫数更新
		service.updateTotalStockNum(setItemDTO.getFormSysItemId());
	}

	/**
	 * キープの更新処理(セット商品用)
	 * @param setItemDTO
	 * @param keepDTO
	 * @param csvImportDTO
	 * @throws DaoException
	 */
	private void keepMinusUpd(ExtendSetItemDTO setItemDTO, ExtendKeepDTO keepDTO, ExtendKeepCsvImportDTO keepCsvImportDTO) throws DaoException {
		System.out.println("keepMinusUpd2: order no = " + keepCsvImportDTO.getOrderNo());
		
		int newNum = -1 * keepCsvImportDTO.getItemNum() * setItemDTO.getItemNum() + keepDTO.getKeepNum();
		
		ItemDAO itemDAO = new ItemDAO();
		CorporationDAO corporationDAO = new CorporationDAO();
		ItemService service = new ItemService();

		ExtendKeepDTO extendKeepDTO = new ExtendKeepDTO();
		extendKeepDTO.setSysKeepId(keepDTO.getSysKeepId());
		extendKeepDTO.setOrderNo(keepCsvImportDTO.getOrderNo());
		//セット商品を構成する商品には必要個数が設定されているため、構成商品の必要数にキープCSVの個数をかける。
		extendKeepDTO.setKeepNum(newNum);
		extendKeepDTO.setRemarks(keepCsvImportDTO.getImportDate() +" "
														+ corporationDAO.getCorporationName(keepCsvImportDTO.getSysCorporationId()) + " "
														+ keepCsvImportDTO.getOrderRoute());
		//更新処理
		keepDTO.setKeepNum(newNum);
		itemDAO.updateKeep(extendKeepDTO);
		//総在庫数、仮在庫数更新
		service.updateTotalStockNum(setItemDTO.getFormSysItemId());

		// remove it
		if (newNum <= 0)
			service.deleteKeep(keepDTO.getSysKeepId());
	}
	/**
	 * 警告文が表示された回数によって、表示する入荷予定日を切り替える処理
	 * @param itemCodeList
	 * @param arrivalScheduleDateList
	 * @param forComparisonItemCode
	 * @return
	 */
	private String chooseArrivalScheduleDate(List<Map<String, Integer>> alertDispItemMapList, List<ArrivalScheduleDTO> arrivalScheduleList,
			String forComparisonItemCode, int oldKeepMinusNum, int newKeepAddNum) {

		/*
		 * 同じ品番にキープを追加した際に、1つ目の入荷数が超えた時次の入荷予定日を表示する処理。
		 * 次の入予定日情報が無い場合は「未定」を表示する。
		 * また、入荷数をキープが上回っていた場合は次の入荷予定日に繰り越して、入荷数の減算処理を行い、
		 * 入荷予定日を表示する。
		 * */

		//表示する入荷日
		String arrivalScheduleDate = "";

		//キープ個数を品番でまとめたリスト
		List<Integer> keepAddNumList = new ArrayList<>();
		if (oldKeepMinusNum > 0) keepAddNumList.add(oldKeepMinusNum);

		//警告文表示Mapリストから今回警告文表示対象の品番でキープ個数をまとめたリストを作成する
		for (Map<String, Integer> alertDispItemMap : alertDispItemMapList) {
			if (alertDispItemMap.containsKey(forComparisonItemCode)) {
				keepAddNumList.add(alertDispItemMap.get(forComparisonItemCode));
			}
		}

			//1つの入荷数減算処理を終え、次の入荷数を減算処理することになったことを示すフラグ
			String isSubtract = "0";

			for (ArrivalScheduleDTO dto : arrivalScheduleList) {
				int arrivalNum = dto.getArrivalNum();

				//キープリストが存在するとき
				if (keepAddNumList != null) {
					//次の入荷予定数を見るときはこの減算処理を行わない
					if (isSubtract.equals("0")) {
						//入荷数を警告文表示した商品の個数で引いていく
						for (int keepAddNum: keepAddNumList) {
							arrivalNum -= keepAddNum;
						}
					}
				}

				//入荷数が1以上残った尚且つ新しく警告文表示する商品の個数を上回っていたら入荷予定日を表示する
				if (arrivalNum > 0 && arrivalNum >= newKeepAddNum) {
					arrivalScheduleDate = dto.getArrivalScheduleDate();
					break;
				//新しく警告文表示する商品の個数が入荷数を上回っていた場合、次の入荷数減算処理に上回った個数を引き継ぐため減算
				} else if (newKeepAddNum > arrivalNum) {
					newKeepAddNum -= arrivalNum;
					//次の入荷数に移行した時減算処理を行わないようにする
					isSubtract = "1";
				}
			}

			//最終的に入荷予定日が取得できなかったら未定を表示する
			if (arrivalScheduleDate.isEmpty()) {
				arrivalScheduleDate = "未定　　　　";
			}

		return arrivalScheduleDate;
	}


	private List<ErrorMessageDTO> filterErrorMessageList(List<ErrorMessageDTO> origin) {
		List<ErrorMessageDTO> newErrorMessageList = new ArrayList<>();
		for(int i=0; i<origin.size(); i++) {
			if(!newErrorMessageList.contains(origin.get(i))) {
				newErrorMessageList.add(origin.get(i));
			}
		}
		
		return newErrorMessageList;
	}
}
