package jp.co.kts.service.fileImport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.ExcelImportDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticManageDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;
import jp.co.kts.app.output.entity.ActionErrorExcelImportDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.mst.MakerDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.common.ServiceValidator;
import jp.co.kts.service.mst.DomesticExhibitionService;

/**
 * 出品DBインポートアップデート処理を行うクラス
 * @author Boncre
 *
 */
public class DomesticImportUpdateService extends DomesticExhibitionImportService{

	/** シート名：出品DB */
	private static final String SHEET_NAME_DOMESTIC = "出品DB";
	private static final String SHEET_NAME_DOMESTIC_SETITEM = "セット商品";

	/** シート無しの返却判定値 */
	private static final int SHEET_NAME_NO_EXIST = -1;
	/** オープン価格フラグ：有 */
	private static final String OPEN_PRICE_FLG_ON = "○";
	/** 定価入力判定値 */
	private static final int LIST_PRICE_INPUT_JUDG = 0;
	/** 更新処理件数判定値 */
	private static final int RESULT_UPDATE_CNT = 1;


	@Override
	public ActionErrorExcelImportDTO validate(FormFile excelImportForm) throws Exception {



		ActionErrorExcelImportDTO dto = super.validate(excelImportForm);

		//終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		//ファイル名に「出品DB情報_」,「.xls」を含んでいるかを確認する
		fileFormatChecker(dto.getResult(), excelImportForm.getFileName());

		//シート名が存在することを判定し、そのシート番号を控えます
		int domesticInfoNum = checkSheetName(dto.getResult(), wb, SHEET_NAME_DOMESTIC);

		//終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		// 出品DBシートが存在していた場合
		if (domesticInfoNum > SHEET_NAME_NO_EXIST) {

			errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS;
			//出品DBのシートから情報をListに格納する
			List<List<String>> strDomesticInfoList =
					uploadExcelFile(wb,wb.getSheetAt(domesticInfoNum), ServiceConst.UPLOAD_EXCEL_SET_DOMESTIC_COLUMN, dto);


			if (strDomesticInfoList.size() == 0) {
				dto.getResult().addErrorMessage("LED00120", "入力に不備があります。");
			}
			//終了
			if (!dto.getResult().isSuccess()) {
				return dto;
			}

			// 事前にDBから管理品番が一部一致しているもののみ取得しておく
			Set<String> managementCdSet = new LinkedHashSet<>();
			for (List<String> strList: strDomesticInfoList) {
				//管理品番を一時的に取得します
				String managementCd = strList.get(0);
				managementCdSet.add(managementCd);
			}
			DomesticExhibitionService domesticService = new DomesticExhibitionService();
			List<DomesticExhibitionDTO> domesticExhibitionDtoList = domesticService.getDomesticExhibitionDTOList(managementCdSet);

			// 事前にDBからメーカー名が一部一致しているもののみ取得しておく
			Set<String> makerNmSet = new LinkedHashSet<>();
			Set<String> makerNmKanaSet = new LinkedHashSet<>();
			for (List<String> strList: strDomesticInfoList) {
				//メーカー名を一時的に取得します
				makerNmSet.add(strList.get(1));
				//メーカー名ｶﾅを一時的に取得します
				makerNmKanaSet.add(strList.get(2));
			}
			MakerDAO makerDao = new MakerDAO();
			List<MstMakerDTO> mstMakerDtoList = makerDao.getSysMakerIdList(makerNmSet, makerNmKanaSet);

			//出品DB情報
			//String型のListからDTOに格納
			List<DomesticExhibitionDTO> domesticExhibitionList = new ArrayList<>();
			//品番重複確認用のマップ
			Map<String, String> checkUpdMngCd = new HashMap<String, String>();
			for (List<String> strList: strDomesticInfoList) {
				ExtendDomesticManageDTO domesticExhibitionDto = new ExtendDomesticManageDTO();
				//管理品番を一時的に取得します
				String managementCd = strList.get(0);
				//メーカー名を一時的に取得します
				String makerNm = strList.get(1);
				//メーカー名ｶﾅを一時的に取得します
				String makerNmKana = strList.get(2);
				//メーカー品番を一時的に取得します
				String makerCode = strList.get(3);
				//商品名を一時的に取得します
				String itemNm = strList.get(4);
				//問屋を一時的に取得します
				String wholsesalerNm = strList.get(5);
				//オープン価格フラグを一時的に取得します
				String openPriceFlg = strList.get(6);
				//定価を一時的に取得します
				String strListPrice = strList.get(7);
				//掛率を一時的に取得します
				String strItemRateOver = strList.get(8);
				//送料を一時的に取得します
				String strPostage = strList.get(9);
				//仕入原価を一時的に取得します
				String purchasingCost = strList.get(10);
				//担当部署ー名を一時的に取得します
				String departmentNm = strList.get(12);

				//定価が空の場合「0」を入れる
				if (StringUtils.isBlank(strListPrice)){
					strListPrice = "0";
				}
				//掛率が空の場合「0」を入れる
//				if (StringUtils.isBlank(strItemRateOver)) {
//					strItemRateOver="0";
//				}
				//送料が空の場合「0」を入れる
				if (StringUtils.isBlank(strPostage)) {
					strPostage = "0";
				}
				//仕入原価が空の場合「0」を入れる
				if (StringUtils.isBlank(purchasingCost)) {
					purchasingCost = "0";
				}
				if (StringUtils.isBlank(openPriceFlg)) {
					openPriceFlg = "";
				}

				if (checkUpdMngCd.containsKey(managementCd)){
					dto.getResult().addErrorMessage("LED00133", String.valueOf(checkUpdMngCd.get(managementCd)),
							  String.valueOf(errorIndex), managementCd);
				}
				//管理品番重複用のマップに管理品番を格納
				checkUpdMngCd.put(managementCd,String.valueOf(errorIndex));

				//管理品番半角英数ハイフンチェック
				ServiceValidator.strMatchesCheckSlash(dto.getResult(), managementCd, String.valueOf(errorIndex),"管理品番");

				//品番が存在しない場合エラー
				boolean existsManagementCode = false;
				for(DomesticExhibitionDTO domesticExhibitonDto : domesticExhibitionDtoList) {
					if(managementCd.equals(domesticExhibitonDto.getManagementCode())) {
						existsManagementCode = true;
						break;
					}
				}
				if(!existsManagementCode) {
					dto.getResult().addErrorMessage("LED00126",
					String.valueOf(errorIndex), managementCd);
				}

				//品番の必須チェック
				ServiceValidator.requiredChecker(dto.getResult(), managementCd, (errorIndex) + "行目の管理品番");

				//商品名の必須チェック
				ServiceValidator.requiredChecker(dto.getResult(), itemNm, (errorIndex) + "行目の商品名");

				//メーカーIDの取得
				if (StringUtils.isBlank(makerNm) && StringUtils.isBlank(makerNmKana)) {
					domesticExhibitionDto.setMakerInputFlg("0");
				} else {
					MstMakerDTO mstMakerDto = new MstMakerDTO();
					mstMakerDto.setMakerNm(makerNm);
					mstMakerDto.setMakerNmKana(makerNmKana);

					boolean existsMstMakerDto = false;
					for(MstMakerDTO tempDto : mstMakerDtoList) {
						if(tempDto.getMakerNm().equals(makerNm) && tempDto.getMakerNmKana().equals(makerNmKana)) {
							existsMstMakerDto = true;
							mstMakerDto = tempDto;
							break;
						}
					}

					//入力されているメーカー名、又はメーカー名ｶﾅが存在しない場合エラー
					if (!existsMstMakerDto) {
						dto.getResult().addErrorMessage("LED00129",
								String.valueOf(errorIndex));
					} else {
						domesticExhibitionDto.setSysMakerId(mstMakerDto.getSysMakerId());
						domesticExhibitionDto.setMakerInputFlg("1");
					}
				}
				//メーカー品番の半角英数ハイフンチェック
				if (StringUtils.isNotBlank(makerCode)) {
					ServiceValidator.strMatchesCheck(dto.getResult(), makerCode, String.valueOf(errorIndex),"メーカー品番");
				}

				//入力制御
				ServiceValidator.checkDouble(dto.getResult(), strListPrice, (errorIndex) + "行目の定価");
				ServiceValidator.checkDouble(dto.getResult(), strItemRateOver, (errorIndex) + "行目の掛率");
				ServiceValidator.checkDouble(dto.getResult(), strPostage, (errorIndex) + "行目の送料");
				ServiceValidator.checkDouble(dto.getResult(), purchasingCost, (errorIndex) + "行目の仕入原価");


				//問屋の必須チェック
				ServiceValidator.requiredChecker(dto.getResult(), wholsesalerNm, (errorIndex) + "行目の問屋" );



				if (GenericValidator.isDouble(strListPrice)) {
					//オープン価格フラグと定価がどちらも空の場合エラー
					if (StringUtils.isBlank(openPriceFlg) && (StringUtils.isBlank(strListPrice)
							|| strListPrice.equals("0"))) {

						if (GenericValidator.isBlankOrNull(strItemRateOver)) {
							dto.getResult().addErrorMessage("LED00155",
									String.valueOf(errorIndex));
						}else if (Double.valueOf(strItemRateOver) == 0) {
							dto.getResult().addErrorMessage("LED00154",
									String.valueOf(errorIndex));
						}
					}

					//オープン価格フラグ、定価(1以上)の両方に入力があった場合エラー
					if (openPriceFlg.equals(OPEN_PRICE_FLG_ON) &&
							Long.valueOf(strListPrice) > LIST_PRICE_INPUT_JUDG) {
						dto.getResult().addErrorMessage("LED00131",
								String.valueOf(errorIndex));
					}
				}

				//担当部署ー名空欄チェック
				if (StringUtils.isEmpty(departmentNm) || departmentNm.equals(null)){
					dto.getResult().addErrorMessage("LED00137", String.valueOf(errorIndex), managementCd);
				}

				//すでにエラーがある場合、dtoに格納する意味が無くなるので、
				//次のvalidate処理へ移す。
				if (!dto.getResult().isSuccess()) {
					errorIndex++;
					continue;
				}

				//システム管理IDの取得
				for(DomesticExhibitionDTO domesticExhibitonDto : domesticExhibitionDtoList) {
					if(managementCd.equals(domesticExhibitonDto.getManagementCode())) {
						domesticExhibitionDto.setSysManagementId(domesticExhibitonDto.getSysManagementId());
						break;
					}
				}
				// domesticExhibitionDto.setSysManagementId(domesticService.getSysManagementId(managementCd));

				//仕入原価の計算を行う
				ExtendDomesticManageDTO priceCalcDto = new ExtendDomesticManageDTO();
				priceCalcDto = purchasingCostCalc(strListPrice, strItemRateOver ,strPostage, openPriceFlg, purchasingCost);
				domesticExhibitionDto.setListPrice(priceCalcDto.getListPrice());
				domesticExhibitionDto.setItemRateOver(priceCalcDto.getItemRateOver());
				domesticExhibitionDto.setPostage(priceCalcDto.getPostage());
				domesticExhibitionDto.setPurchasingCost(priceCalcDto.getPurchasingCost());
				domesticExhibitionDto.setOpenPriceFlg(priceCalcDto.getOpenPriceFlg());

				domesticExhibitionDto.setDepartmentNm(departmentNm);

				domesticExhibitionList.add(setDomesticExhibitionDTO(domesticExhibitionDto, strList));
				errorIndex++;
			}

			//出品DB更新処理
			DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
			for (DomesticExhibitionDTO domesticExhibitionDto : domesticExhibitionList) {

				//FIXME 存在しない品番はvalidate処理でチェック済みなので以下の処理でエラーメッセージを出力することはないはず・・・
				if (dao.updateDomesticExhibition(domesticExhibitionDto) != RESULT_UPDATE_CNT){
						dto.getResult().addErrorMessage("LED00116", domesticExhibitionDto.getManagementCode());
				}
			}

			/*
			 * 更新時の仕様：出品DBシートに記載されている管理品番とセット商品シートに記載されている管理品番が
			 * 一致していなければならない。
			 */
			//次の処理のために更新対象の管理品番をdtoに詰め替える。
			dto.getExcelImportDTO().setDomesticExhibitionDtoList(domesticExhibitionList);

			//セット商品と構成商品を紐づけるための管理品番のマップを作成する。
			Map<String, Long> managementCodeMap = new HashMap<String, Long>();
			for (int i = 0; i < dto.getExcelImportDTO().getDomesticExhibitionDtoList().size(); i++) {
				String managementCode = dto.getExcelImportDTO().getDomesticExhibitionDtoList().get(i).getManagementCode();
				long sysManagementId = dto.getExcelImportDTO().getDomesticExhibitionDtoList().get(i).getSysManagementId();
				managementCodeMap.put(managementCode, sysManagementId);
			}



			/***************************** セット商品情報START *****************************/
			//シート名が存在することを判定し、そのシート番号を控えます
			int setItemNm = checkSheetName(dto.getResult(), wb, SHEET_NAME_DOMESTIC_SETITEM);

			//TODO シート名を判定してエラーとするかどうかの処理を実装すること。

			//XXX エクセルフォーマットに問題があれば、ここで終了・・・他の処理が良好でもこの時点で処理を中断する・・・本当にこれで良いのか・・・
			if (!dto.getResult().isSuccess()) {
				return dto;
			}

			//シート別にデータを取得。すべてString型で取得。
			List<List<String>> setItemList = new ArrayList<List<String>>();
			setItemList = uploadExcelFile(wb,wb.getSheetAt(setItemNm), ServiceConst.UPLOAD_EXCEL_SET_ITEM_LAST_COLUMN, dto);

			// 何もセット商品シートに記載されていなければ処理終了とする。
			if (!setItemList.isEmpty()) {

				//取得したデータをDTOにつめる(varidateチェック含む)
				//この処理で、出品DBシートに記載のない構成商品は除外される。
				dto = setSetDomesticItem(dto, setItemList, managementCodeMap);

				//一度構成商品を削除したセット商品を管理するSet変数
				Set<Long> sysManagementIdSet = new LinkedHashSet<Long>();

				//セット商品テーブルにインサートとマスタ商品をアップデート
				for (int i = 0; i < dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().size(); i++) {
					DomesticExhibitionDTO domesticDto = new DomesticExhibitionDTO();

					/*
					 * FIXME 構成商品ごとにセット商品のIDを取得しているので、重複したIDを処理することになる。
					 * また、一回のインポート内に重複するIDが存在した場合、削除しなくても良いデータを削除することになるので処理を変更しなければならない。
					 */
					domesticDto.setSysManagementId(dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().get(i).getSysManagementId());

					/*
					 * 構成商品の更新は総入れ替えとなるので、処理は以下の通りとなります。
					 * １.既存の構成商品を削除する。
					 * ２．シートに記載されている構成商品を追加する。
					 */

					if (!sysManagementIdSet.contains(domesticDto.getSysManagementId())) {
						sysManagementIdSet.add(domesticDto.getSysManagementId());

						//１．既存の構成商品を削除する。
						dao.deleteSetDomesticExhibitionOfSysManagementId(dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().get(i).getSysManagementId());
					}

					//２．シートに記載されている構成商品を追加する。
					dao.registFromDomesticExhibition(dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().get(i), domesticDto);
					dao.updateDomesticSetItemFlg(dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().get(i));
				}
			}
			/****************************** セット商品情報END ******************************/

		} else {
			dto.getResult().addErrorMessage("LED00120", "入力に不備があります。");
		}
		return dto;
	}

	/**
	 * ファイル名から指定のフォーマットであるか判定します。
	 * @param result
	 * @param fileName
	 */
	private void fileFormatChecker(Result<?> result, String fileName) {

		//ファイル名チェック処理→ファイル名不整合：true
		if (fileName.indexOf( "出品DB情報_") == -1) {
			result.addErrorMessage("LED00121");
			return;
		}

		if (fileName.indexOf(".xls") == -1
				&& fileName.indexOf(".xlsx") == -1
				&& fileName.indexOf(".xlsm") == -1
				) {
			result.addErrorMessage("LED00121");
		}
	}


	/**
	 * セット商品作成
	 * DomesticImportinsertServiceのsetSetDomesticItemを流用しています。
	 * @param dto
	 * @param setItemList
	 * @return
	 * @throws Exception
	 *
	 */
	private ActionErrorExcelImportDTO setSetDomesticItem(ActionErrorExcelImportDTO dto,
			List<List<String>> setItemList, Map<String, Long> itemCodeMap) throws Exception {


		Result<ExcelImportDTO> result = dto.getResult();
		List<ExtendSetDomesticExhibitionDto> setDomesticItem = new ArrayList<ExtendSetDomesticExhibitionDto>();

		ExcelImportDTO excelImportDTO = dto.getExcelImportDTO();

		SequenceDAO dao = new SequenceDAO();

		//構成商品最大値
		long maxSysSetManagementId = dao.getSysSetManagementId() + 1;



		int errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS;

		/****************************************
		 * 構成商品のsys_item_idを取得する。
		 ***************************************/
		Set<String> managementCdSet = new LinkedHashSet<>();

		//SYS_MANAGEMENT_IDを取得したい管理品番を集める。
		String itemCode = null;
		for (List<String> strList: setItemList) {

			if (!GenericValidator.isBlankOrNull(strList.get(0))) {

				itemCode = strList.get(0);

				if (itemCode.contains(".")) {
					itemCode = itemCode.substring(0, itemCode.indexOf("."));
				}

				managementCdSet.add(itemCode);
			}


			if (!GenericValidator.isBlankOrNull(strList.get(1))) {

				itemCode = strList.get(1);

				if (itemCode.contains(".")) {
					itemCode = itemCode.substring(0, itemCode.indexOf("."));
				}

				managementCdSet.add(itemCode);
			}
		}

		DomesticExhibitionService domesticService = new DomesticExhibitionService();
		List<DomesticExhibitionDTO> domesticExhibitionDtoList = domesticService.getDomesticExhibitionDTOList(managementCdSet);

		// チェック処理で使用するために管理品番と管理品番IDを保持する。
		Map<String, Long> managementCodeIDMap = new HashMap<String, Long>();
		for (DomesticExhibitionDTO deDto : domesticExhibitionDtoList) {
			managementCodeIDMap.put(deDto.getManagementCode(), deDto.getSysManagementId());
		}




		for (int i = 0; i < setItemList.size(); i++) {
			ExtendSetDomesticExhibitionDto setDto = new ExtendSetDomesticExhibitionDto();
			/** validate */
			//セット商品品番

			if (!itemCodeMap.containsKey(setItemList.get(i).get(0))){
				result.addErrorMessage("LED00153", "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品管理品番");
			}
			//セット商品管理品番
			ServiceValidator.strMatchesCheckSlash(result, setItemList.get(i).get(0), String.valueOf(errorIndex),"セット商品管理品番");
			ServiceValidator.requiredChecker(result, setItemList.get(i).get(0), "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品管理品番");
			checkSysItemId(result, setItemList.get(i).get(0), "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品管理品番", managementCodeIDMap);


			//構成商品管理品番
			ServiceValidator.strMatchesCheckSlash(result, setItemList.get(i).get(1), String.valueOf(errorIndex),"構成商品管理品番");
			ServiceValidator.requiredChecker(result, setItemList.get(i).get(1), "シート名「セット商品」 " + (i + errorIndex) + "行目の構成商品管理品番");
			checkSysItemId(result, setItemList.get(i).get(1), "シート名「セット商品」 " + (i + errorIndex) + "行目の構成商品管理品番", managementCodeIDMap);


			//個数
			ServiceValidator.requiredChecker(result, setItemList.get(i).get(2), "シート名「セット商品」 " + (i + errorIndex) + "行目の個数");
			ServiceValidator.checkDouble(result, setItemList.get(i).get(2), "シート名「セット商品」 " + (i + errorIndex) + "行目の個数");

			//すでにエラーがある場合、dtoに格納する意味が無くなるので、
			//次のvalidate処理へ移す。
			if (!result.isSuccess()) {
				continue;
			}

			/** dtoに格納 */
			//システムセット商品ID
			BeanUtils.setProperty(setDto, "sysSetManagementId", maxSysSetManagementId);

			//セット商品品番
			BeanUtils.setProperty(setDto, "sysManagementId", managementCodeIDMap.get(setItemList.get(i).get(0)));

			//構成商品品番
			BeanUtils.setProperty(setDto, "formSysManagementId", managementCodeIDMap.get(setItemList.get(i).get(1)));

			//個数
			String itemNum = setItemList.get(i).get(2);
			if (itemNum.contains(".")) {
				itemNum = itemNum.substring(0, itemNum.indexOf("."));
			}
			BeanUtils.setProperty(setDto, "itemNum", itemNum);

			maxSysSetManagementId++;

			setDomesticItem.add(setDto);
		}

		dto.setResult(result);
		excelImportDTO.setSetDomesticExhibitionDtoList(setDomesticItem);
		dto.setExcelImportDTO(excelImportDTO);

		return dto;
	}

	/**
	 * 【その他テーブル用】商品ID有無チェック(double型考慮Ver)
	 * DomesticImportInsertServiceのcheckSysItemIdを流用しています。
	 * @param result
	 * @param itemCode
	 * @param caption
	 * @throws DaoException
	 */
	private void checkSysItemId(Result<?> result, String itemCode, String caption, Map<String, Long> managementMap) throws DaoException {

		if (GenericValidator.isBlankOrNull(itemCode)) {
			return;
		}

		if (itemCode.contains(".")) {
			itemCode = itemCode.substring(0, itemCode.indexOf("."));
		}

		if (!managementMap.containsKey(itemCode)) {
			result.addErrorMessage("LED00109", caption);
		}
	}

}
