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
 * 出品DBインポートインサート処理を行うクラス
 * @author Boncre
 *
 */
public class DomesticImportInsertService extends DomesticExhibitionImportService{

	/**
	 *
	 */
	@Override
	public ActionErrorExcelImportDTO validate(FormFile excelImportForm) throws Exception {

		ActionErrorExcelImportDTO dto = super.validate(excelImportForm);

		//終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		//ファイル名に「出品DB情報_」,「.xls」を含んでいるかを確認する
		fileFormatChecker(dto.getResult(), excelImportForm.getFileName());

		//ファイルフォーマットが違えば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		existingFileImport(dto);


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
	 * [概要]既存のファイルをインポートする際に実行するメソッド
	 * dtoの値に対して、整合性チェック、値を設定、インサート処理を実施する
	 * @param dto
	 * @return dto
	 * @throws Exception
	 */
	private ActionErrorExcelImportDTO existingFileImport(ActionErrorExcelImportDTO dto) throws Exception  {

		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();

		//シート名が存在することを判定し、そのシート番号を控えます
		int domesticInfoNum = checkSheetName(dto.getResult(), wb, "出品DB");

		//エクセルフォーマットに問題があれば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}


		//出品DBのシートから情報をListに格納する
		List<List<String>> strDomesticInfoList =
				uploadExcelFile(wb,wb.getSheetAt(domesticInfoNum), ServiceConst.UPLOAD_EXCEL_SET_DOMESTIC_COLUMN, dto);

		if (strDomesticInfoList.size() == 0) {
			dto.getResult().addErrorMessage("LED00120", "入力に不備があります。");
		}

		//取得したデータをDTOにつめる(varidateチェック含む)
		dto = setDoemsticExhibition(dto, strDomesticInfoList);
		Map<String, Long> managementCodeMap = new HashMap<String, Long>();
		for (int i = 0; i < dto.getExcelImportDTO().getDomesticExhibitionDtoList().size(); i++) {
			dao.registryDomesticExhibition(dto.getExcelImportDTO().getDomesticExhibitionDtoList().get(i));
			String managementCode = dto.getExcelImportDTO().getDomesticExhibitionDtoList().get(i).getManagementCode();
			long sysManagementId = dto.getExcelImportDTO().getDomesticExhibitionDtoList().get(i).getSysManagementId();
			managementCodeMap.put(managementCode, sysManagementId);
		}


		/***************************** セット商品情報START *****************************/
		//シート名が存在することを判定し、そのシート番号を控えます
		int setItemNm = checkSheetName(dto.getResult(), wb, "セット商品");
		//エクセルフォーマットに問題があれば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}
		List<List<String>> setItemList = new ArrayList<List<String>>();
		//シート別にデータを取得。すべてString型で取得。
		setItemList = uploadExcelFile(wb,wb.getSheetAt(setItemNm), ServiceConst.UPLOAD_EXCEL_SET_ITEM_LAST_COLUMN, dto);

		// 何もセット商品シートに記載されていなければ処理終了とする。
		if (!setItemList.isEmpty()) {
			//取得したデータをDTOにつめる(varidateチェック含む)
			dto = setSetDomesticItem(dto, setItemList, managementCodeMap);
			//セット商品テーブルにインサートとマスタ商品をアップデート
			for (int i = 0; i < dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().size(); i++) {
				DomesticExhibitionDTO domesticDto = new DomesticExhibitionDTO();
				domesticDto.setSysManagementId(dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().get(i).getSysManagementId());
				dao.registFromDomesticExhibition(dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().get(i), domesticDto);
				dao.updateDomesticSetItemFlg(dto.getExcelImportDTO().getSetDomesticExhibitionDtoList().get(i));
			}
		}

		/****************************** セット商品情報END ******************************/

		return dto;
	}

	/**
	 *
	 * @param dto
	 * @param strDomesticInfoList
	 * @return
	 * @throws Exception
	 */
	private ActionErrorExcelImportDTO setDoemsticExhibition(
			ActionErrorExcelImportDTO dto, List<List<String>> strDomesticInfoList) throws Exception {


		Result<ExcelImportDTO> result = dto.getResult();
		List<DomesticExhibitionDTO> domesticExhibition = new ArrayList<DomesticExhibitionDTO>();
		ExcelImportDTO excelImportDTO = dto.getExcelImportDTO();

		//品番重複確認用のマップ
		Map<String, String> checkUpdMngCd = new HashMap<String, String>();

		SequenceDAO dao = new SequenceDAO();
		long maxSysManagementId = dao.getSysManagementId() + 1;

		errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS;

		// 事前にDBから管理品番が一部一致しているもののみ取得しておく
		Set<String> managementCdSet = new LinkedHashSet<>();
		for (List<String> strList: strDomesticInfoList) {
			//管理品番を一時的に取得します
			String managementCd = strList.get(0);
			managementCdSet.add(managementCd.toLowerCase());
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

		for (int i = 0; i < strDomesticInfoList.size(); i++) {
			ExtendDomesticManageDTO domesticExhibitionDto = new ExtendDomesticManageDTO();

			boolean listPriceInputCheck = false;
			boolean openPriceInputCheck = false;
			//管理品番を一時的に取得します
			String managementCd = strDomesticInfoList.get(i).get(0);
			//メーカー名を一時的に取得します
			String makerNm = strDomesticInfoList.get(i).get(1);
			//メーカー名ｶﾅを一時的に取得します
			String makerNmKana = strDomesticInfoList.get(i).get(2);
			//メーカー品番を一時的に取得します
			String makerCode = strDomesticInfoList.get(i).get(3);
			//オープン価格フラグを一時的に取得します:
			String openPriceFlg = strDomesticInfoList.get(i).get(6);
			//定価を一時的に取得します
			String strListPrice = strDomesticInfoList.get(i).get(7);
			//掛率を一時的に取得します
			String strItemRateOver = strDomesticInfoList.get(i).get(8);
			//送料を一時的に取得します
			String strPostage = strDomesticInfoList.get(i).get(9);
			//仕入原価を一時的に取得します
			String purchasingCost = strDomesticInfoList.get(i).get(10);
			//担当部署ー名を一時的に取得します
			String departmentNm = strDomesticInfoList.get(i).get(12);

			//管理品番半角英数ハイフンチェック
			ServiceValidator.strMatchesCheckSlash(result, managementCd, String.valueOf(errorIndex),"管理品番");

			//管理品番重複チェック
			if (checkUpdMngCd.containsKey(managementCd)){
				dto.getResult().addErrorMessage("LED00133", String.valueOf(checkUpdMngCd.get(managementCd)),
						  String.valueOf(errorIndex), managementCd);
			}
			//管理品番重複用のマップに管理品番を格納
			checkUpdMngCd.put(managementCd,String.valueOf(errorIndex));

			String newCd = String.valueOf(managementCd.toLowerCase());
			//品番が存在する場合エラー
			boolean existsManagementCode = false;
			for(DomesticExhibitionDTO domesticExhibitonDto : domesticExhibitionDtoList) {
				String oldCd = String.valueOf(domesticExhibitonDto.getManagementCode().toLowerCase());
				if(newCd.equals(oldCd)) {
					existsManagementCode = true;
					break;
				}
			}
			if(existsManagementCode) {
				dto.getResult().addErrorMessage("LED00136",
				String.valueOf(errorIndex), managementCd);
			}

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
				ServiceValidator.strMatchesCheck(result, makerCode, String.valueOf(errorIndex),"メーカー品番");
			}



			/** validate */
			//品番
			ServiceValidator.requiredChecker(result, strDomesticInfoList.get(i).get(0), (errorIndex) + "行目の管理品番");

			//商品名
			ServiceValidator.requiredChecker(result, strDomesticInfoList.get(i).get(4), (errorIndex) + "行目の商品名");

			//問屋
			ServiceValidator.requiredChecker(result, strDomesticInfoList.get(i).get(5), (errorIndex) + "行目の問屋");



			/**************************定価とオープン価格フラグチェック**************************/
			ServiceValidator.checkDouble(result, strDomesticInfoList.get(i).get(7), (errorIndex) + "行目の定価");
			ServiceValidator.checkDouble(result, strDomesticInfoList.get(i).get(8), (errorIndex) + "行目の掛率");
			ServiceValidator.checkDouble(result, strDomesticInfoList.get(i).get(9), (errorIndex) + "行目の送料");
			ServiceValidator.checkDouble(result, strDomesticInfoList.get(i).get(10), (errorIndex) + "行目の仕入原価");

			/**************************定価とオープン価格フラグチェック**************************/
			//オープン価格
			if (GenericValidator.isBlankOrNull(strDomesticInfoList.get(i).get(6))) {
				openPriceInputCheck = true;
			}
			//定価
			if (GenericValidator.isBlankOrNull(strDomesticInfoList.get(i).get(7))
					|| strDomesticInfoList.get(i).get(7).equals("0")) {
				listPriceInputCheck = true;
			}


			//両方空の場合
			if (openPriceInputCheck && listPriceInputCheck) {
//				result.addErrorMessage("LED00132", String.valueOf(errorIndex));
				if (GenericValidator.isBlankOrNull(strItemRateOver)) {
					dto.getResult().addErrorMessage("LED00155",
							String.valueOf(errorIndex));
				}else if (Double.valueOf(strItemRateOver) == 0) {
					dto.getResult().addErrorMessage("LED00154",
							String.valueOf(errorIndex));
				}
			}
			//両方入力されていた場合
			if (!openPriceInputCheck && !listPriceInputCheck) {
				result.addErrorMessage("LED00131", String.valueOf(errorIndex));
			}

			//担当部署ー名空欄チェック
			if (StringUtils.isEmpty(departmentNm) || departmentNm.equals(null)){
				dto.getResult().addErrorMessage("LED00137", String.valueOf(errorIndex), managementCd);
			}

			//すでにエラーがある場合、dtoに格納する意味が無くなるので、
			//次のvalidate処理へ移す。
			if (!result.isSuccess()) {
				errorIndex++;
				continue;
			}

			/** dtoに格納 */
			BeanUtils.setProperty(domesticExhibitionDto, "sysManagementId", maxSysManagementId);
			setDomesticExhibitionDTO(domesticExhibitionDto, strDomesticInfoList.get(i));

			//仕入原価の計算を行う
			ExtendDomesticManageDTO priceCalcDto = new ExtendDomesticManageDTO();
			priceCalcDto = purchasingCostCalc(strListPrice, strItemRateOver ,strPostage, openPriceFlg, purchasingCost);
			domesticExhibitionDto.setListPrice(priceCalcDto.getListPrice());
			domesticExhibitionDto.setItemRateOver(priceCalcDto.getItemRateOver());
			//TODO 送料はオープン時でも入れる？
			domesticExhibitionDto.setPostage(priceCalcDto.getPostage());
			domesticExhibitionDto.setPurchasingCost(priceCalcDto.getPurchasingCost());
			domesticExhibitionDto.setOpenPriceFlg(priceCalcDto.getOpenPriceFlg());
			
			domesticExhibitionDto.setDepartmentNm(departmentNm);

			maxSysManagementId++;

			domesticExhibition.add(domesticExhibitionDto);
			errorIndex++;
		}

		dto.setResult(result);
		excelImportDTO.setDomesticExhibitionDtoList(domesticExhibition);
		dto.setExcelImportDTO(excelImportDTO);

		return dto;
	}

	/**
	 *
	 * @param dto
	 * @param setItemList
	 * @return
	 * @throws Exception
	 */
	private ActionErrorExcelImportDTO setSetDomesticItem(ActionErrorExcelImportDTO dto,
			List<List<String>> setItemList, Map<String, Long> itemCodeMap) throws Exception {


		Result<ExcelImportDTO> result = dto.getResult();
		List<ExtendSetDomesticExhibitionDto> setDomesticItem = new ArrayList<ExtendSetDomesticExhibitionDto>();

		ExcelImportDTO excelImportDTO = dto.getExcelImportDTO();

		SequenceDAO dao = new SequenceDAO();

		//構成商品最大値
		long maxSysSetManagementId = dao.getSysSetManagementId() + 1;

		int errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1;



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

				managementCdSet.add(itemCode.toLowerCase());
			}


			if (!GenericValidator.isBlankOrNull(strList.get(1))) {

				itemCode = strList.get(1);

				if (itemCode.contains(".")) {
					itemCode = itemCode.substring(0, itemCode.indexOf("."));
				}

				managementCdSet.add(itemCode.toLowerCase());
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
				result.addErrorMessage("LED00150", "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品管理品番");
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
			BeanUtils.setProperty(setDto, "sysManagementId", itemCodeMap.get(setItemList.get(i).get(0)));

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
	 *
	 * @param result
	 * @param itemCode
	 * @param caption
	 * @throws DaoException
	 */
	private void checkSysItemId(Result<?> result, String itemCode, String caption, Map<String, Long> managementCodeIDMap) throws DaoException {

		if (GenericValidator.isBlankOrNull(itemCode)) {
			return;
		}

		if (itemCode.contains(".")) {
			itemCode = itemCode.substring(0, itemCode.indexOf("."));
		}

		if (!managementCodeIDMap.containsKey(itemCode)) {
			result.addErrorMessage("LED00109", caption);
		}
	}
}
