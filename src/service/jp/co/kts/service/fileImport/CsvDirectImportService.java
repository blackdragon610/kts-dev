package jp.co.kts.service.fileImport;

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

public class CsvDirectImportService {

	/**
	 * インポートするCSVファイルが既に登録されているか確認
	 * 登録済み：エラー
	 * 未登録：新規で登録を行う
	 * @param deliveryCompanyId
	 * @param corporationId
	 * @param fileUp
	 * @param csvImportList
	 * @return
	 * @throws Exception
	 */
	public ErrorDTO importFile(long deliveryCompanyId, long corporationId, FormFile fileUp, List<CsvImportDTO> csvImportList) throws Exception {

		InputStream inputStream = fileUp.getInputStream();
		ErrorDTO csvErrorDTO = new ErrorDTO();
		String fname = fileUp.getFileName();
		long deliveryId = 1;
		
		if(fname.contains("発行済データ")) { // ヤマト
			deliveryId = 1;
		}else if(fname.contains("Eoa")) { // 佐川
			deliveryId = 2;
		}else if(fname.contains("SHUKKADL")) { // 西濃
			deliveryId = 3;
		}else if(fname.contains("〒")) { // 郵政
			deliveryId = 4;
		}

		long corpoId = 0;
		 /* 
		 * String[] csvFileNames = StringUtils.split(fname, "_"); if(csvFileNames.length
		 * > 1) { corpoId = Long.valueOf(csvFileNames[1].replace(".csv", "")); }
		 */
		System.out.println("CsvDirectImportService::importFile() is called : " + deliveryId);
		
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
			csvImportDTO = setCsvImportList(deliveryId, csvLineArray);

			csvImportDTO.setOrderRemarksMemo(csvImportDTO.getOrderMemo() + "\r\n" + csvImportDTO.getOrderRemarks());

			csvImportDTO.setFileNm(fileUp.getFileName());

			csvImportDTO.setSysImportId((new SequenceDAO().getMaxSysImportId() + 1));
			csvImportDTO.setSysCorporationId(corpoId);

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

	private CsvImportDTO setCsvImportList(long deliveryCompanyId, String[] array) throws IllegalAccessException, Exception {
		CsvImportDTO csvImportDTO = new CsvImportDTO();

		int i = 0;
		
		switch ((int)deliveryCompanyId) {
		case 1: // ヤマト
			System.out.println("ヤマト : (slipno, orderno): " + array[3] + ":" + array[32]);
			BeanUtils.setProperty(csvImportDTO,"slipNo", array[3]);							// 伝票番号	
			BeanUtils.setProperty(csvImportDTO,"orderNo", array[32]);						// 記事  ==>   受注番号
			BeanUtils.setProperty(csvImportDTO,"transportCorporationSystem", "ヤマト運輸");
			break;
			
		case 2: // 佐川
			System.out.println("佐川 : (slipno, orderno): " + array[0] + ":" + array[33]);
			BeanUtils.setProperty(csvImportDTO,"slipNo", array[0]);							// お問合せ送り状№,	
			BeanUtils.setProperty(csvImportDTO,"orderNo", array[33]);						// 受注番号			
			BeanUtils.setProperty(csvImportDTO,"transportCorporationSystem", "佐川急便");
			break;			
			
		case 3: // 西濃
			System.out.println("西濃 : (slipno, orderno): " + array[2] + ":" + array[1]);
			BeanUtils.setProperty(csvImportDTO,"orderNo", array[1]);						// 受注番号			
			BeanUtils.setProperty(csvImportDTO,"slipNo", array[2]);							// お問合せ送り状№,				
			BeanUtils.setProperty(csvImportDTO,"transportCorporationSystem", "西濃運輸");
			break;
			
		case 4: // 郵政
			System.out.println("郵政 : (slipno, orderno): " + array[124] + ":" + array[63]);
			BeanUtils.setProperty(csvImportDTO,"slipNo", array[124]);							// お問合せ送り状№,				
			BeanUtils.setProperty(csvImportDTO,"orderNo", array[63]);						// 受注番号			
			BeanUtils.setProperty(csvImportDTO,"transportCorporationSystem", "日本郵便");
			break;

		default:
			break;
		}
		

//		BeanUtils.setProperty(csvImportDTO,"dataDivision", array[i++]);						// データ区分
//		BeanUtils.setProperty(csvImportDTO,"orderRoute", array[i++]);						// 受注ルート
//		BeanUtils.setProperty(csvImportDTO,"orderNo", array[i++]);							// 受注番号
//		BeanUtils.setProperty(csvImportDTO,"orderDate", array[i++]);						// 注文日
//		BeanUtils.setProperty(csvImportDTO,"orderTime", array[i++]);						// 注文時間
//		BeanUtils.setProperty(csvImportDTO,"orderFamilyNm", array[i++]);					// 注文者名（姓）
//		BeanUtils.setProperty(csvImportDTO,"orderFirstNm", array[i++]);						// 注文者名（名）
//		BeanUtils.setProperty(csvImportDTO,"orderFamilyNmKana", array[i++]);				// 注文者名（セイ）
//		BeanUtils.setProperty(csvImportDTO,"orderFirstNmKana", array[i++]);					// 注文者名（メイ）
//		BeanUtils.setProperty(csvImportDTO,"orderMailAddress", array[i++]);					// 注文者メールアドレス
//		BeanUtils.setProperty(csvImportDTO,"orderZip", array[i++]);							// 注文者郵便番号
//		BeanUtils.setProperty(csvImportDTO,"orderPrefectures", array[i++]);					// 注文者住所（都道府県） 
//		BeanUtils.setProperty(csvImportDTO,"orderMunicipality", array[i++]);				// 注文者住所（市区町村）
//		BeanUtils.setProperty(csvImportDTO,"orderAddress", array[i++]);						// 注文者住所（市区町村以降）
//		BeanUtils.setProperty(csvImportDTO,"orderBuildingNm", array[i++]);					// 注文者住所（建物名等）
//		BeanUtils.setProperty(csvImportDTO,"orderCompanyNm", array[i++]);					// 注文者会社名
//		BeanUtils.setProperty(csvImportDTO,"orderQuarter", array[i++]);						// 注文者部署名
//		BeanUtils.setProperty(csvImportDTO,"orderTel", array[i++]);							// 注文者電話番号
//		BeanUtils.setProperty(csvImportDTO,"accountMethod", array[i++]);					// 決済方法
//		BeanUtils.setProperty(csvImportDTO,"accountCommission", array[i++]);				// 決済手数料
//		BeanUtils.setProperty(csvImportDTO,"usedPoint", array[i++]);						// ご利用ポイント
//		BeanUtils.setProperty(csvImportDTO,"getPoint", array[i++]);							// 獲得ポイント
//		BeanUtils.setProperty(csvImportDTO,"orderRemarks", array[i++]);						// 備考（注文）
//		BeanUtils.setProperty(csvImportDTO,"orderMemo", array[i++]);						// 一言メモ（注文）
//		BeanUtils.setProperty(csvImportDTO,"sumClaimPrice", array[i++]);					// 合計請求金額
//		BeanUtils.setProperty(csvImportDTO,"menberNo", array[i++]);							// 会員番号（自社サイト）
//		BeanUtils.setProperty(csvImportDTO,"depositDate", array[i++]);						// 入金日	
//		BeanUtils.setProperty(csvImportDTO,"registryStaff", array[i++]);					// 登録担当者
//		BeanUtils.setProperty(csvImportDTO,"destinationDivision", array[i++]);				// お届け先区分
//		BeanUtils.setProperty(csvImportDTO,"destinationFamilyNm", array[i++]);				// お届け先名（姓）
//		BeanUtils.setProperty(csvImportDTO,"destinationFirstNm", array[i++]);				// お届け先名（名）
//		BeanUtils.setProperty(csvImportDTO,"destinationFamilyNmKana", array[i++]);			// お届け先名（セイ）
//		BeanUtils.setProperty(csvImportDTO,"destinationFirstNmKana", array[i++]);			// お届け先名（メイ）
//		BeanUtils.setProperty(csvImportDTO,"destinationZip", array[i++]);					// お届け先郵便番号
//		BeanUtils.setProperty(csvImportDTO,"destinationPrefectures", array[i++]);			// お届け先住所（都道府県）
//		BeanUtils.setProperty(csvImportDTO,"destinationMunicipality", array[i++]);			// お届け先住所（市区町村）
//		BeanUtils.setProperty(csvImportDTO,"destinationAddress", array[i++]);				// お届け先住所（市区町村以降）
//		BeanUtils.setProperty(csvImportDTO,"destinationBuildingNm", array[i++]);			// お届け先住所（建物名等）
//		BeanUtils.setProperty(csvImportDTO,"destinationCompanyNm", array[i++]);				// お届け先会社名
//		BeanUtils.setProperty(csvImportDTO,"destinationQuarter", array[i++]);				// お届け先部署名	
//		BeanUtils.setProperty(csvImportDTO,"destinationTel", array[i++]);					// お届け先電話番号
//		BeanUtils.setProperty(csvImportDTO,"senderDivision", array[i++]);					// 送り主区分
//		BeanUtils.setProperty(csvImportDTO,"senderFamilyNm", array[i++]);					// 送り主名（姓）
//		BeanUtils.setProperty(csvImportDTO,"senderFirstNm", array[i++]);					// 送り主名（名）
//		BeanUtils.setProperty(csvImportDTO,"senderFamilyNmKana", array[i++]);				// 送り主名（セイ）
//		BeanUtils.setProperty(csvImportDTO,"senderFirstNmKana", array[i++]);				// 送り主名（メイ）
//		BeanUtils.setProperty(csvImportDTO,"senderZip", array[i++]);						// 送り主郵便番号
//		BeanUtils.setProperty(csvImportDTO,"senderPrefectures", array[i++]);				// 送り主住所（都道府県）
//		BeanUtils.setProperty(csvImportDTO,"senderMunicipality", array[i++]);				// 送り主住所（市区町村）
//		BeanUtils.setProperty(csvImportDTO,"senderAddress", array[i++]);					// 送り主住所（市区町村以降）
//		BeanUtils.setProperty(csvImportDTO,"senderBuildingNm", array[i++]);					// 送り主住所（建物名等）
//		BeanUtils.setProperty(csvImportDTO,"senderCompanyNm", array[i++]);					// 送り主会社名
//		BeanUtils.setProperty(csvImportDTO,"senderQuarter", array[i++]);					// 送り主部署名
//		BeanUtils.setProperty(csvImportDTO,"senderTel", array[i++]);						// 送り主電話番号
//		BeanUtils.setProperty(csvImportDTO,"senderRemarks", array[i++]);					// 備考（お届け先）
//		BeanUtils.setProperty(csvImportDTO,"senderMemo", array[i++]);						// 一言メモ（お届け先）
//		BeanUtils.setProperty(csvImportDTO,"giftMessage", array[i++]);						// ギフトメッセージ
//		BeanUtils.setProperty(csvImportDTO,"slipDivision", array[i++]);						// 伝票区分
//		BeanUtils.setProperty(csvImportDTO,"invoiceClassification", array[i++]);			// 送り状種別
//		BeanUtils.setProperty(csvImportDTO,"slipNo", array[i++]);							// 伝票番号
//		BeanUtils.setProperty(csvImportDTO,"cashOnDeliveryCommission", array[i++]);			// 代引請求金額
//		BeanUtils.setProperty(csvImportDTO,"temperatureDivision", array[i++]);				// 温度区分
//		BeanUtils.setProperty(csvImportDTO,"destinationAppointDate", array[i++]);			// お届け指定日
//		BeanUtils.setProperty(csvImportDTO,"destinationAppointTime", array[i++]);			// お届け時間帯
//		BeanUtils.setProperty(csvImportDTO,"shipmentPlanDate", array[i++]);					// 出荷予定日
//		BeanUtils.setProperty(csvImportDTO,"transportCorporationSystem", array[i++]);		// 運送会社システム
//		BeanUtils.setProperty(csvImportDTO,"slipMemo", array[i++]);							// 一言メモ（伝票）
//		BeanUtils.setProperty(csvImportDTO,"lastStatus", array[i++]);						// 最終ステータス
//		BeanUtils.setProperty(csvImportDTO,"reservationStatus", array[i++]);				// 保留ステータス"
//		BeanUtils.setProperty(csvImportDTO,"combineSource", array[i++]);					// 同梱元
//		BeanUtils.setProperty(csvImportDTO,"combinePoint", array[i++]);						// 同梱先
//		BeanUtils.setProperty(csvImportDTO,"itemDivision", array[i++]);						// 商品区分
//		BeanUtils.setProperty(csvImportDTO,"itemClassification", array[i++]);				// 商品種別
//		BeanUtils.setProperty(csvImportDTO,"shopItemCd", array[i++]);						// 商品コード（店舗）
//		BeanUtils.setProperty(csvImportDTO,"itemNm", array[i++]);							// 商品名
//		BeanUtils.setProperty(csvImportDTO,"itemNum", array[i++]);							// 個数
		
//		BeanUtils.setProperty(csvImportDTO,"pieceRate", array[i++]);						// 単価		
		
		
//		BeanUtils.setProperty(csvImportDTO,"optionSukenekoOne", array[i++]);				// オプション１（助ネコ）
//		BeanUtils.setProperty(csvImportDTO,"optionSukenekoTwo", array[i++]);				// オプション２（助ネコ）
//		BeanUtils.setProperty(csvImportDTO,"sukenekoItemCd", array[i++]);					// 商品コード（助ネコ）
//		BeanUtils.setProperty(csvImportDTO,"unpaidPrice", array[i++]);						// 未入金額
//		BeanUtils.setProperty(csvImportDTO,"invoiceArticle", array[i++]);					// 送り状記事欄
//		BeanUtils.setProperty(csvImportDTO,"slipManagementNo", array[i++]);					// 伝票管理番号
//		BeanUtils.setProperty(csvImportDTO,"disposalRoute", array[i++]);					// 処理ルート
//		BeanUtils.setProperty(csvImportDTO,"disposalDate", array[i++]);						// 処理済日
//		BeanUtils.setProperty(csvImportDTO,"ownCompanyCd", array[i++]);						// 自社商品コード
//		BeanUtils.setProperty(csvImportDTO,"buyCount", array[i++]);							// 購入回数

		return csvImportDTO;

	}

}
