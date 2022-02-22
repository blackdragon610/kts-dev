package jp.co.kts.service.fileImport;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.AnnualSalesDTO;
import jp.co.kts.app.common.entity.CodeCollationDTO;
import jp.co.kts.app.common.entity.ExcelImportDTO;
import jp.co.kts.app.common.entity.ItemCostDTO;
import jp.co.kts.app.common.entity.ItemPriceDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.SetItemDTO;
import jp.co.kts.app.common.entity.WarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ItemCostPriceDTO;
import jp.co.kts.app.output.entity.ActionErrorExcelImportDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.fileImport.ExcelImportDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.SupplierDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.common.ServiceValidator;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.ui.web.struts.WebConst;

public class ExcelImportItemInsertService extends ExcelImportService {


	/**
	 *	2014/04/18　編集　伊東
	 * @param excelImportForm
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActionErrorExcelImportDTO validate(FormFile excelImportForm) throws Exception {


		//2014/04/18　編集　伊東　start
		//親クラスの共通部分を呼び出します
		ActionErrorExcelImportDTO dto = super.validate(excelImportForm);
		//2014/04/18　編集　伊東　end

		//終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		//TODO ファイル名の指定をする?→新規Excelの名前を固定しても大丈夫か質問する。
		//ファイル名に「仕入商品データ_」,「.xls」を含んでいるかを確認する
		fileFormatChecker(dto.getResult(), excelImportForm.getFileName());

		//ファイルフォーマットが違えば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		/**
		 * Excelインポートでの新規登録の場合は、在庫情報と価格情報、価格情報が必要
		 */

		//インポート処理を行う
		if (excelImportForm.getFileName().indexOf( "新在庫情報_") != -1) {
			ItemInfoFileImport(dto);
		}
		return dto;
	}


	/**
	 * [概要]商品情報Excelをインポートする際に実行するメソッド
	 * dtoの値に対して、整合性チェック、値を設定、インサート処理を実施する
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	private ActionErrorExcelImportDTO ItemInfoFileImport(ActionErrorExcelImportDTO dto) throws Exception {

		boolean authInfo = true;
		ExcelImportDAO dao = new ExcelImportDAO();
		ItemService itemService = new ItemService();

		//ユーザー情報取得
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		MstUserDTO mstUserDTO = new MstUserDTO();
		mstUserDTO = userService.getUserName(userId);
		String auth = mstUserDTO.getOverseasInfoAuth();


		/************************************** MST_商品テーブルSTART **************************************/
		//シート名が存在することを判定し、そのシート番号を控えます
		int mstItemInfoSheetNmOn = 0;
		int mstItemInfoSheetNmOff = 0;
		String itemSheetNm = "商品情報_権限有";
		mstItemInfoSheetNmOn = checkSheetNameCostPrice(dto.getResult(), wb, "商品情報_権限有");
		authInfo = true;
		//権限と、シートの照らし合わせ
		//権限有
		if (auth.equals("1")) {
			if (mstItemInfoSheetNmOn < 0) {
				mstItemInfoSheetNmOff = checkSheetNameResult(dto.getResult(), wb, "商品情報");
				itemSheetNm = "商品情報";
				if (mstItemInfoSheetNmOff < 0) {
					dto.getResult().addErrorMessage("LED00111", "商品情報_権限有");
				} else {
					throw new Exception();
				}
			}
			//エクセルフォーマットに問題があれば、ここで終了
			if (!dto.getResult().isSuccess()) {
				return dto;
			}
		//権限無
		} else {
			//権限無しシートが存在するか確認
			if (mstItemInfoSheetNmOn < 0) {
				authInfo = false;
				itemSheetNm = "商品情報";
				mstItemInfoSheetNmOff = checkSheetName(dto.getResult(), wb, "商品情報");

			//権限が違うExcelの場合Exceptionを出す
			} else {
				throw new Exception();
			}
			//エクセルフォーマットに問題があれば、ここで終了
			if (!dto.getResult().isSuccess()) {
				return dto;
			}
		}


		List<List<String>> itemMstfoList = new ArrayList<List<String>>();
		if (authInfo) {
			//シート別にデータを取得。すべてString型で取得。
			itemMstfoList = uploadExcelFile(wb, wb.getSheetAt(mstItemInfoSheetNmOn), ServiceConst.UPLOAD_EXCEL_ITEM_COLUMN);
		} else {
			//シート別にデータを取得。すべてString型で取得。
			itemMstfoList = uploadExcelFile(wb, wb.getSheetAt(mstItemInfoSheetNmOff), ServiceConst.UPLOAD_EXCEL_NOT_AUTH_ITEM_COLUMN);
		}

		//取得したデータをDTOにつめる(varidateチェック含む)
		dto = setItemDetailInfo(dto, itemMstfoList, authInfo);


		//商品
		//品番重複チェック
		for (int i = 0; i < dto.getExcelImportDTO().getMstItemDTOList().size(); i++) {

			String itemCode = dto.getExcelImportDTO().getMstItemDTOList().get(i).getItemCode();

			for (int j = i + 1; j < dto.getExcelImportDTO().getMstItemDTOList().size(); j++) {

				if (StringUtils.equals(itemCode, dto.getExcelImportDTO().getMstItemDTOList().get(j).getItemCode())) {

					//本来取り込みが始まっている行のインデックスをiとjに足してエラーを出します
					dto.getResult().addErrorMessage("LED00145", itemSheetNm, String.valueOf(i + ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1)
							, String.valueOf(j + ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1)
							,itemCode);
					continue;
				}
			}
		}

		//varidateチェックにかかっていれば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		Map<String, String> itemCodeMap = new HashMap<String, String>();
		//MST_商品テーブルにインサート
		for (int i = 0; i < dto.getExcelImportDTO().getMstItemDTOList().size(); i++) {
			dao.insertMstItemDetail(dto.getExcelImportDTO().getMstItemDTOList().get(i));
			String itemCode = dto.getExcelImportDTO().getMstItemDTOList().get(i).getItemCode();
			itemCodeMap.put(itemCode, itemCode);
		}
		/************************************** MST_商品テーブルEND **************************************/



		/************************************** 在庫情報START **************************************/
		//シート名が存在することを判定し、そのシート番号を控えます
		int warehouseStockNm = checkSheetName(dto.getResult(), wb, "在庫情報");
		//エクセルフォーマットに問題があれば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}
		List<List<String>> warehouseStockList = new ArrayList<List<String>>();
		//シート別にデータを取得。すべてString型で取得。
		warehouseStockList = uploadExcelFile(wb,wb.getSheetAt(warehouseStockNm), ServiceConst.UPLOAD_EXCEL_WAREHOUSE_STOCK_LAST_COLUMN);
		//取得したデータをDTOにつめる(varidateチェック含む)
		dto = setWarehouseStockInfo(dto, warehouseStockList);
		//倉庫在庫テーブルにインサート
		for (int i = 0; i < dto.getExcelImportDTO().getWarehouseStockDTOList().size(); i++) {
			dao.insertWarehouseStock(dto.getExcelImportDTO().getWarehouseStockDTOList().get(i));
		}
		/************************************** 在庫情報END **************************************/



		/************************************** 価格情報START **************************************/
		int itmCstPrcShtNmAuthOn = 0;
		int itmCstPrcShtNmAuthOff = 0;
		String priceSheetNm = "価格情報_権限有";
		itmCstPrcShtNmAuthOn = checkSheetNameCostPrice(dto.getResult(), wb, "価格情報_権限有");
		authInfo = true;
		if (auth.equals("1")) {
			if (itmCstPrcShtNmAuthOn < 0) {
				dto.getResult().addErrorMessage("LED00111", "価格情報_権限有");
			}
			//エクセルフォーマットに問題があれば、ここで終了
			if (!dto.getResult().isSuccess()) {
				return dto;
			}
		} else {
			//シートが存在しなければエラーメッセージを出す:価格情報のみ
			if (itmCstPrcShtNmAuthOn < 0) {
				authInfo = false;
				priceSheetNm = "価格情報";
				itmCstPrcShtNmAuthOff = checkSheetName(dto.getResult(), wb, "価格情報");
				//エクセルフォーマットに問題があれば、ここで終了
				if (!dto.getResult().isSuccess()) {
					return dto;
				}
			//権限が違うExcelの場合Exceptionを出す
			} else {
				throw new Exception();
			}
		}




		List<List<String>> itemCostPriceList = new ArrayList<List<String>>();
		//シート別にデータを取得。すべてString型で取得。
		if (authInfo) {
			itemCostPriceList = uploadExcelFile(wb,wb.getSheetAt(itmCstPrcShtNmAuthOn), ServiceConst.UPLOAD_EXCEL_SET_PRICE_LAST_COLUMN);

		} else {
			itemCostPriceList = uploadExcelFile(wb,wb.getSheetAt(itmCstPrcShtNmAuthOff), ServiceConst.UPLOAD_EXCEL_SET_PRICE_LAST_COLUMN_NOTAUTH);
		}

		//価格情報
		//String型のListからDTOに格納
		List<ItemCostPriceDTO> strCostPriceInfoList = new ArrayList<>();
		for (List<String> strList: itemCostPriceList) {

			ItemCostPriceDTO itemCostPriceDto = new ItemCostPriceDTO();
			//品番を一時的に取得します
			String itemCode = strList.get(0);

			//品番が存在しない場合
			if (!itemCodeMap.containsKey(itemCode)) {
				dto.getResult().addErrorMessage("LED00150", "シート名「" + priceSheetNm + "」"+ String.valueOf(errorIndex++) + "行目の品番"+itemCode);
				continue;
			}

			//価格情報の入力制御 権限有の場合
			if (authInfo) {
				//TODO いずれ定数化今は勘弁して
				ServiceValidator.checkDouble(dto.getResult(), strList.get(1), "シート名「" + priceSheetNm + "」 " + (errorIndex) + "行目の仕入金額");
				ServiceValidator.checkDouble(dto.getResult(), strList.get(2), "シート名「" + priceSheetNm + "」 " + (errorIndex) + "行目の加算経費");
				ServiceValidator.checkDouble(dto.getResult(), strList.get(3), "シート名「" + priceSheetNm + "」 " + (errorIndex) + "行目のKind原価");

				// 原価の設定
				for (int i = 4; i < 16; i++) {
					ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + priceSheetNm + "」 " + (errorIndex) + "行目の原価");
					//エクセルフォーマットに問題があれば、ここで終了
					if (!dto.getResult().isSuccess()) {
						break;
					}
				}
				for (int i = 16; i <= 27; i++) {
					ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + priceSheetNm + "」 " + (errorIndex) + "行目の売価");
					//エクセルフォーマットに問題があれば、ここで終了
					if (!dto.getResult().isSuccess()) {
						break;
					}
				}

			// 権限無しの場合
			} else {
				// 売価の設定
				//TODO いずれ定数化今は勘弁して
				for (int i = 1; i < 13; i++) {
					ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + priceSheetNm + "」 " + (errorIndex) + "行目の原価");
					//エクセルフォーマットに問題があれば、ここで終了
					if (!dto.getResult().isSuccess()) {
						break;
					}
				}
				for (int i = 13; i <= 24; i++) {
					ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + priceSheetNm + "」 " + (errorIndex) + "行目の売価");
					//エクセルフォーマットに問題があれば、ここで終了
					if (!dto.getResult().isSuccess()) {
						break;
					}
				}
			}

			//エクセルフォーマットに問題があれば、ここで終了
			if (!dto.getResult().isSuccess()) {
				errorIndex++;
				continue;
			}

			itemCostPriceDto.setSysItemId(itemService.getSysItemId(itemCode));

			strCostPriceInfoList.add(setItemCostPriceInsertDTO(itemCostPriceDto, strList));
			errorIndex++;
		}

		//エクセルフォーマットに問題があれば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}


		//価格情報
		//品番重複チェック
		for (int i = 0; i < strCostPriceInfoList.size(); i++) {
			String itemCode = strCostPriceInfoList.get(i).getItemCode();
			for (int j = i + 1; j < strCostPriceInfoList.size(); j++) {
				if (StringUtils.equals(itemCode, strCostPriceInfoList.get(j).getItemCode())) {

					//本来取り込みが始まっている行のインデックスをiとjに足してエラーを出します
					dto.getResult().addErrorMessage("LED00125", String.valueOf(i + ServiceConst.UPLOAD_EXCEL_INIT_ROWS)
							, String.valueOf(j + ServiceConst.UPLOAD_EXCEL_INIT_ROWS)
							,itemCode);
					continue;
				}
			}
		}

		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		//価格情報
		//更新の処理
		for (ItemCostPriceDTO itemCostPriceDto: strCostPriceInfoList) {
			//原価情報の更新
			List<ItemCostDTO> costDtoList = new ArrayList<ItemCostDTO>();
			for (int i = 0 ; i < itemCostPriceDto.getItemCostList().size(); i++) {
				//MST_商品の仕入価格を更新
				if (itemCostPriceDto.getSysCorporationIdCostList().get(i).equals("77")) {

					dao.updateMstItemCostOrLoading(itemCostPriceDto.getSysItemId(),
							itemCostPriceDto.getItemCostList().get(i),
								itemCostPriceDto.getSysCorporationIdCostList().get(i));
				//MST_商品の加算経費を更新
				} else if (itemCostPriceDto.getSysCorporationIdCostList().get(i).equals("88")) {

					dao.updateMstItemCostOrLoading(itemCostPriceDto.getSysItemId(),
							itemCostPriceDto.getItemCostList().get(i),
								itemCostPriceDto.getSysCorporationIdCostList().get(i));
				//商品原価を登録
				} else {
					dao.insertCostInfo(itemCostPriceDto.getSysItemId(),
						itemCostPriceDto.getSysCorporationIdCostList().get(i),
							itemCostPriceDto.getItemCostList().get(i));
				}

				ItemCostDTO costDto = new ItemCostDTO();
				costDto.setSysItemId(itemCostPriceDto.getSysItemId());
				costDto.setSysCorporationId(Long.valueOf(itemCostPriceDto.getSysCorporationIdCostList().get(i)));
				costDtoList.add(costDto);
			}
			//海外閲覧権限がないExcelでインポートした場合Kind原価が入力されていないので、ここで作成する。
			if (!authInfo) {
				ItemCostDTO itemCostDto = new ItemCostDTO();
				SequenceDAO seqDao = new SequenceDAO();
				itemCostDto.setSysCorporationId(99);
				itemCostDto.setCost(0);
				long maxSysItemCostId = seqDao.getMaxSysItemCostId() + 1;
				itemCostDto.setSysItemCostId(maxSysItemCostId);
				itemCostDto.setSysItemId(itemCostPriceDto.getSysItemId());
				dao.insertItemCost(itemCostDto);
			}
			dto.getExcelImportDTO().setItemCostDTOList(costDtoList);

			//売価情報の更新
			List<ItemPriceDTO> priceDtoList = new ArrayList<ItemPriceDTO>();
			for (int i = 0 ; i < itemCostPriceDto.getItemPriceList().size(); i++) {
				if (dao.insertPriceInfo(itemCostPriceDto.getSysItemId(),
						itemCostPriceDto.getSysCorporationIdPriceList().get(i),
							itemCostPriceDto.getItemPriceList().get(i)) == 0) {

					dto.getResult().addErrorMessage("LED00116", itemCostPriceDto.getItemCode());
					continue;
				}
				ItemPriceDTO priceDto = new ItemPriceDTO();
				priceDto.setSysItemId(itemCostPriceDto.getSysItemId());
				priceDto.setSysCorporationId(Long.valueOf(itemCostPriceDto.getSysCorporationIdPriceList().get(i)));
				priceDtoList.add(priceDto);

			}
			dto.getExcelImportDTO().setItemPriceDTOList(priceDtoList);
			itemCostRepeatCheck(dto.getResult(), dto.getExcelImportDTO().getItemCostDTOList());
			itemPriceRepeatCheck(dto.getResult(), dto.getExcelImportDTO().getItemPriceDTOList());
		}
		/************************************** 価格情報END **************************************/



		/************************************** SET商品情報START **************************************/
		//シート名が存在することを判定し、そのシート番号を控えます
		int setItemNm = checkSheetName(dto.getResult(), wb, "セット商品");
		//エクセルフォーマットに問題があれば、ここで終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}
		List<List<String>> setItemList = new ArrayList<List<String>>();
		//シート別にデータを取得。すべてString型で取得。
		setItemList = uploadExcelFile(wb,wb.getSheetAt(setItemNm), ServiceConst.UPLOAD_EXCEL_SET_ITEM_LAST_COLUMN);
		//取得したデータをDTOにつめる(varidateチェック含む)
		dto = setSetItem(dto, setItemList, itemCodeMap);
		//セット商品テーブルにインサートとマスタ商品をアップデート
		for (int i = 0; i < dto.getExcelImportDTO().getSetItemDTOList().size(); i++) {
			dao.insertSetItem(dto.getExcelImportDTO().getSetItemDTOList().get(i));
			dao.updateMstItemFlg(dto.getExcelImportDTO().getSetItemDTOList().get(i));
		}
		/************************************** SET商品情報END **************************************/


		/************************************** 品番照合テーブルSTART **************************************/
		dto = setCodeCollation(dto, itemMstfoList);
		for (int i = 0; i < dto.getExcelImportDTO().getCodeCollationDTOList().size(); i++) {
			dao.insertCodeCollation(dto.getExcelImportDTO().getCodeCollationDTOList().get(i));
		}
		/************************************** 品番照合テーブルEND **************************************/


		//在庫情報の重複チェック
		sysWarehouseIdRepeatCheck(dto.getResult(), dto.getExcelImportDTO().getWarehouseStockDTOList());

		//総在庫数を集計する
		for (int i = 0; i < dto.getExcelImportDTO().getWarehouseStockDTOList().size(); i++) {
			dao.stockSum(dto.getExcelImportDTO().getWarehouseStockDTOList().get(i).getSysItemId());
		}

		//原価、売価テーブルで入力されてなかった会社IDを原価0で登録します
		for (int i = 0; i < dto.getExcelImportDTO().getMstItemDTOList().size(); i++) {
			itemCostDataAdd(dto.getResult(), dto.getExcelImportDTO().getMstItemDTOList().get(i).getSysItemId());
			itemPriceDataAdd(dto.getResult(), dto.getExcelImportDTO().getMstItemDTOList().get(i).getSysItemId());
		}

		//倉庫在庫テーブルで入力されていなかった倉庫情報を登録します
		for (int i = 0; i < dto.getExcelImportDTO().getMstItemDTOList().size(); i++) {
			itemWarehouseAdd(dto.getResult(), dto.getExcelImportDTO().getMstItemDTOList().get(i).getSysItemId());
		}

		//年間販売数を登録します。
		for (int i = 0; i < dto.getExcelImportDTO().getMstItemDTOList().size(); i++) {
			annuakSalesAdd(dto.getResult(), dto.getExcelImportDTO().getMstItemDTOList().get(i).getSysItemId());
		}

		/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
		//セット商品の組立可数・構成部品も含めて総計算
//		for (int i = 0; i < dto.getExcelImportDTO().getMstItemDTOList().size(); i++) {
//			itemService.setAllAssemblyNum(dto.getExcelImportDTO().getMstItemDTOList().get(i).getSysItemId());
//		}
		/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
//		for (int i = 0; i < dto.getExcelImportDTO().getSetItemDTOList().size(); i++) {
//			itemService.setAllAssemblyNum(dto.getExcelImportDTO().getSetItemDTOList().get(i).getSysItemId());
//		}

		return dto;
	}

	/**
	 * ファイル名から指定のフォーマットであるか判定します。
	 * @param result
	 * @param fileName
	 */
	private void fileFormatChecker(Result<?> result, String fileName) {

		//ファイル名チェック処理→ファイル名不整合：true
		if (fileName.indexOf( "新在庫情報_") == -1) {
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
	 *
	 * @param dto
	 * @param mstItemList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws DaoException
	 */
	private ActionErrorExcelImportDTO setCodeCollation(
			ActionErrorExcelImportDTO dto, List<List<String>> mstItemList) throws IllegalAccessException, InvocationTargetException, DaoException {


		List<CodeCollationDTO> codeCollation = new ArrayList<CodeCollationDTO>();
		ExcelImportDTO excelImportDTO = dto.getExcelImportDTO();

		SequenceDAO dao = new SequenceDAO();
		long maxSysCodeCollationId = dao.getMaxSysCodeCollationId() + 1;

		for (int i = 0; i < dto.getExcelImportDTO().getMstItemDTOList().size(); i++) {
			CodeCollationDTO sidto = new CodeCollationDTO();
			MstItemDTO midto = dto.getExcelImportDTO().getMstItemDTOList().get(i);

			/** dtoに格納 */
			//システム品番照合ID
			BeanUtils.setProperty(sidto, "sysCodeCollationId", maxSysCodeCollationId);

			//システム商品ID
			BeanUtils.setProperty(sidto, "sysItemId", midto.getSysItemId());

			//品番
			BeanUtils.setProperty(sidto, "itemCode", midto.getItemCode());

			//旧品番
			BeanUtils.setProperty(sidto, "oldItemCode", midto.getOldItemCode());

			maxSysCodeCollationId++;

			codeCollation.add(sidto);
		}

		excelImportDTO.setCodeCollationDTOList(codeCollation);
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
	private ActionErrorExcelImportDTO setSetItem(ActionErrorExcelImportDTO dto,
			List<List<String>> setItemList, Map<String, String> itemCodeMap) throws Exception {


		Result<ExcelImportDTO> result = dto.getResult();
		List<SetItemDTO> setItem = new ArrayList<SetItemDTO>();
		ExcelImportDTO excelImportDTO = dto.getExcelImportDTO();

		SequenceDAO dao = new SequenceDAO();
		ExcelImportDAO excelImportDao = new ExcelImportDAO();
		long maxSysSetItemId = dao.getMaxSysSetItemId() + 1;

		int errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1;

		for (int i = 0; i < setItemList.size(); i++) {
			SetItemDTO sidto = new SetItemDTO();
			/** validate */
			//セット商品品番

			if (!itemCodeMap.containsKey(setItemList.get(i).get(0))){
				result.addErrorMessage("LED00150", "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品品番");
			}
			ServiceValidator.requiredChecker(result, setItemList.get(i).get(0), "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品品番");
			checkSysItemId(result, setItemList.get(i).get(0), "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品品番");
			ServiceValidator.equalLengthChecker(result, setItemList.get(i).get(0), "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品品番", 11);
			ServiceValidator.checkDouble(result, setItemList.get(i).get(0), "シート名「セット商品」 " + (i + errorIndex) + "行目のセット商品品番");

			//構成商品品番
			ServiceValidator.requiredChecker(result, setItemList.get(i).get(1), "シート名「セット商品」 " + (i + errorIndex) + "行目の構成商品品番");
			checkSysItemId(result, setItemList.get(i).get(1), "シート名「セット商品」 " + (i + errorIndex) + "行目の構成商品品番");
			ServiceValidator.inputChecker(result, setItemList.get(i).get(1), "シート名「セット商品」 " + (i + errorIndex) + "行目の構成商品品番", 11);
			ServiceValidator.checkDouble(result, setItemList.get(i).get(1), "シート名「セット商品」 " + (i + errorIndex) + "行目の構成商品品番");

			//個数
			ServiceValidator.requiredChecker(result, setItemList.get(i).get(2), "シート名「セット商品」 " + (i + errorIndex) + "行目の個数");
			ServiceValidator.inputChecker(result, setItemList.get(i).get(2), "シート名「セット商品」 " + (i + errorIndex) + "行目の個数", 9);
			ServiceValidator.checkDouble(result, setItemList.get(i).get(2), "シート名「セット商品」 " + (i + errorIndex) + "行目の個数");

			//すでにエラーがある場合、dtoに格納する意味が無くなるので、
			//次のvalidate処理へ移す。
			if (!result.isSuccess()) {
				continue;
			}

			/** dtoに格納 */
			//システムセット商品ID
			BeanUtils.setProperty(sidto, "sysSetItemId", maxSysSetItemId);

			//セット商品品番
			Integer sysItemId = excelImportDao.getSysItemId(setItemList.get(i).get(0));
			BeanUtils.setProperty(sidto, "sysItemId", sysItemId);

			//構成商品品番
			long formSysItemId = excelImportDao.getSysItemId(setItemList.get(i).get(1));
			BeanUtils.setProperty(sidto, "formSysItemId", formSysItemId);

			//個数
			String itemNum = setItemList.get(i).get(2);
			if (itemNum.contains(".")) {
				itemNum = itemNum.substring(0, itemNum.indexOf("."));
			}
			BeanUtils.setProperty(sidto, "itemNum", itemNum);

			maxSysSetItemId++;

			setItem.add(sidto);
		}

		dto.setResult(result);
		excelImportDTO.setSetItemDTOList(setItem);
		dto.setExcelImportDTO(excelImportDTO);

		return dto;
	}






	/**
	 * 新規の在庫情報シートの情報をDTOに格納する
	 * @param dto
	 * @param warehouseStockList
	 * @return
	 * @throws Exception
	 */
	private ActionErrorExcelImportDTO setWarehouseStockInfo(
			ActionErrorExcelImportDTO dto, List<List<String>> warehouseStockList) throws Exception {


		Result<ExcelImportDTO> result = dto.getResult();
		List<WarehouseStockDTO> warehouseStock = new ArrayList<WarehouseStockDTO>();
		ExcelImportDTO excelImportDTO = dto.getExcelImportDTO();

		SequenceDAO dao = new SequenceDAO();
		long maxSysWarehouseStockId = dao.getMaxSysWarehouseStockId() + 1;

		errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1;

		for (int i = 0; i < warehouseStockList.size(); i++) {
			WarehouseStockDTO wsdto = new WarehouseStockDTO();

			/** validate */
			//品番
			ServiceValidator.requiredChecker(result, warehouseStockList.get(i).get(0), "シート名「在庫情報」 " + (i + errorIndex) + "行目の品番");
			checkSysItemId(result, warehouseStockList.get(i).get(0), "シート名「在庫情報」 " + (i + errorIndex) + "行目の品番");
			ServiceValidator.equalLengthChecker(result, warehouseStockList.get(i).get(0), "シート名「在庫情報」 " + (i + errorIndex) + "行目の品番", 11);
			ServiceValidator.checkDouble(result, warehouseStockList.get(i).get(0), "シート名「在庫情報」 " + (i + errorIndex) + "行目の品番");

			//倉庫No
			ServiceValidator.requiredChecker(result, warehouseStockList.get(i).get(1), "シート名「在庫情報」 " + (i + errorIndex) + "行目の倉庫ID");
			String sysWarehouseId = warehouseStockList.get(i).get(1);
			if (!GenericValidator.isBlankOrNull(sysWarehouseId)) {
				if (sysWarehouseId.contains(".")) {
//					sysWarehouseId = sysWarehouseId.substring(0, sysWarehouseId.indexOf("."));
					warehouseStockList.get(i).set(1, sysWarehouseId.substring(0, sysWarehouseId.indexOf(".")));
				}
			}
			ServiceValidator.inputChecker(result, sysWarehouseId, "シート名「在庫情報」 " + (i + errorIndex) + "行目の倉庫ID", 1);
			ServiceValidator.checkDouble(result, warehouseStockList.get(i).get(1), "シート名「在庫情報」 " + (i + errorIndex) + "行目の倉庫ID");
			checkSysWarehouseId(result, warehouseStockList.get(i).get(1), "シート名「在庫情報」 " + (i + errorIndex) + "行目の倉庫ID");

			//在庫数
			ServiceValidator.requiredChecker(result, warehouseStockList.get(i).get(2), "シート名「在庫情報」 " + (i + errorIndex) + "行目の在庫数");
			ServiceValidator.inputChecker(result, warehouseStockList.get(i).get(2), "シート名「在庫情報」 " + (i + errorIndex) + "行目の在庫数", 9);
			ServiceValidator.excelStockCheck(result, warehouseStockList.get(i).get(2), "シート名「在庫情報」 " + (i + errorIndex) + "行目の在庫数");

			//ロケーションNo
			ServiceValidator.inputChecker(result, warehouseStockList.get(i).get(3), "シート名「在庫情報」 " + (i + errorIndex) + "行目のロケーションNo", 30);

			//すでにエラーがある場合、dtoに格納する意味が無くなるので、
			//次のvalidate処理へ移す。
			if (!result.isSuccess()) {
				continue;
			}

			/** dtoに格納 */
			//システム倉庫在庫ID
			BeanUtils.setProperty(wsdto, "sysWarehouseStockId", maxSysWarehouseStockId);
			setWarehouseStockInfoDTO(wsdto, warehouseStockList.get(i));

			maxSysWarehouseStockId++;

			warehouseStock.add(wsdto);
		}

		dto.setResult(result);
		excelImportDTO.setWarehouseStockDTOList(warehouseStock);
		dto.setExcelImportDTO(excelImportDTO);

		return dto;
	}

	/**
	 * [概要] Excelから取得したデータをdtoに格納するメソッド（バリデート処理も含む）：商品情報
	 * @param dto
	 * @param warehouseStockList
	 * @return dto
	 * @throws Exception
	 */
	private ActionErrorExcelImportDTO setItemDetailInfo(
			ActionErrorExcelImportDTO dto, List<List<String>> mstItemInfoList, boolean authInfo) throws Exception {

		Result<ExcelImportDTO> result = dto.getResult();
		List<MstItemDTO> mstItem = new ArrayList<MstItemDTO>();
		ExcelImportDTO excelImportDTO = dto.getExcelImportDTO();

		String sheetNm = "";
		if (authInfo) {
			sheetNm = "商品情報_権限有";
		} else {
			sheetNm = "商品情報";
		}

		SequenceDAO dao = new SequenceDAO();
		long maxSysItemId = dao.getMaxSysItemId() + 1;
		errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1;
		for (int i = 0; i < mstItemInfoList.size(); i++) {
			MstItemDTO midto = new MstItemDTO();

			/** validate */
			//品番
			ServiceValidator.requiredChecker(result, mstItemInfoList.get(i).get(0), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の品番");
			ServiceValidator.equalLengthChecker(result, mstItemInfoList.get(i).get(0), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の品番", 11);
			ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(0), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の品番");
			checkSysItemIdMstItem(result, mstItemInfoList.get(i).get(0), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の品番");

			//海外閲覧権限有のExcelの場合
			if (authInfo) {
				//工場品番
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(1), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の工場品番", 30);
				//工場品番半角英数ハイフンチェック
				ServiceValidator.strMatchesCheckHypDot(result, mstItemInfoList.get(i).get(1), String.valueOf(i + errorIndex),"工場品番");

				//旧品番
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(2), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の旧品番", 30);

				//仕入金額
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(4), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の仕入金額");

				//商品名
				ServiceValidator.requiredChecker(result, mstItemInfoList.get(i).get(6), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の商品名");

				//仕入先ID
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(8), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の仕入先ID");
				checkSysSupplierIdMstItem(result, mstItemInfoList.get(i).get(8), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の仕入先ID");

				//在庫アラート数
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(13), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の在庫アラート数", 9);
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(13), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の在庫アラート数");

				//商品リードタイム制御
				Object keyVal = null;
				if (StringUtils.isNotEmpty(mstItemInfoList.get(i).get(14))) {
					for (Iterator<String> lead = WebConst.LEAD_TIME_MAP.keySet().iterator(); lead.hasNext();) {
						Object key = lead.next();
						Object val = WebConst.LEAD_TIME_MAP.get(key);
						if (mstItemInfoList.get(i).get(14).equals(val)) {
							keyVal = key;
							break;
						}
					}
					if (keyVal == null) {
						result.addErrorMessage("LED00146", sheetNm ,(i+errorIndex) + "行目の商品リードタイム");
					}
				}

				WebConst.LEAD_TIME_MAP.get(mstItemInfoList.get(i).get(14));


				//仕様メモ
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(12), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の仕様メモ", 500);

				//重量
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(16), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の重量");

				//最少ロット数
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(18), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の最少ロット数");

			//海外閲覧権限無しExcelの場合
			} else {
				//旧品番
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(1), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の旧品番", 30);

				//商品名
				ServiceValidator.requiredChecker(result, mstItemInfoList.get(i).get(2), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の商品名");

				//在庫アラート数
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(7), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の在庫アラート数", 9);
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(7), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の在庫アラート数");

				//商品リードタイム制御
				Object keyVal = null;
				if (StringUtils.isNotEmpty(mstItemInfoList.get(i).get(8))) {
					for (Iterator<String> lead = WebConst.LEAD_TIME_MAP.keySet().iterator(); lead.hasNext();) {
						Object key = lead.next();
						Object val = WebConst.LEAD_TIME_MAP.get(key);
						if (mstItemInfoList.get(i).get(8).equals(val)) {
							keyVal = key;
							break;
						}
					}
					if (keyVal == null) {
						result.addErrorMessage("LED00146", sheetNm ,(i+errorIndex) + "行目の商品リードタイム");
					}
				}

				WebConst.LEAD_TIME_MAP.get(mstItemInfoList.get(i).get(8));


				//仕様メモ
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(6), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の仕様メモ", 500);

				//重量
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(10), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の重量");

				//最少ロット数
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(12), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の最少ロット数");

			}
			//すでにエラーがある場合、dtoに格納する意味が無くなるので、
			//次のvalidate処理へ移す。
			if (!result.isSuccess()) {
				continue;
			}

			//システム商品ID
			BeanUtils.setProperty(midto, "sysItemId", maxSysItemId);

			mstItem.add(setMstItemDetailDTO(midto, mstItemInfoList.get(i), authInfo));
//			mstItem.add(midto);

			maxSysItemId++;
		}

		dto.setResult(result);
		excelImportDTO.setMstItemDTOList(mstItem);
		dto.setExcelImportDTO(excelImportDTO);

		return dto;
	}

	/**
	 * 会社ID有無チェック (double型考慮Ver)
	 *
	 * @param result
	 * @param sysCorporationId
	 * @param caption
	 * @throws DaoException
	 */
	public void checkSysCorporationId(Result<?> result, String sysCorporationId, String caption) throws DaoException {

		if (GenericValidator.isBlankOrNull(sysCorporationId)) {
			return;
		}

		if (!GenericValidator.isDouble(sysCorporationId)) {
			result.addErrorMessage("LED00109", caption);
			return;
		}

		if (sysCorporationId.contains(".")) {
			sysCorporationId = sysCorporationId.substring(0, sysCorporationId.indexOf("."));
		}

		long sysCorporationIdLn = 0;

		try {
			sysCorporationIdLn = Integer.parseInt(sysCorporationId);
		} catch (Exception e) {
			result.addErrorMessage("LED00109", caption);
			return;
		}

		ExcelImportDAO dao = new ExcelImportDAO();
		if (dao.checkSysCorporationId(sysCorporationIdLn) == null) {
			result.addErrorMessage("LED00109", caption);
		}
	}

	/**
	 * 販売チャネルID有無チェック (double型考慮Ver)
	 *
	 * @param result
	 * @param sysChannelId
	 * @param caption
	 * @throws DaoException
	 */
	public void checkSysChannelId(Result<?> result, String sysChannelId, String caption) throws DaoException {

		if (GenericValidator.isBlankOrNull(sysChannelId)) {
			return;
		}

		if (!GenericValidator.isDouble(sysChannelId)) {
			result.addErrorMessage("LED00109", caption);
			return;
		}

		if (sysChannelId.contains(".")) {
			sysChannelId = sysChannelId.substring(0, sysChannelId.indexOf("."));
		}

		long sysChannelIdLn = 0;

		try {
			sysChannelIdLn = Integer.parseInt(sysChannelId);
		} catch (Exception e) {
			result.addErrorMessage("LED00109", caption);
			return;
		}

		ExcelImportDAO dao = new ExcelImportDAO();
		if (dao.checkSysChannelId(sysChannelIdLn) == null) {
			result.addErrorMessage("LED00109", caption);
		}
	}

	/**
	 * 原価の重複チェックをします。
	 * @param result
	 * @param itemCostDTOList
	 * @throws DaoException
	 */
	private void itemCostRepeatCheck(Result<ExcelImportDTO> result,
			List<ItemCostDTO> itemCostDTOList) throws DaoException {

		ExcelImportDAO dao = new ExcelImportDAO();

		//チェック済の品番リスト
		List<ItemCostDTO> ngItemCost = new ArrayList<ItemCostDTO>();

		for (int i = 0; i < itemCostDTOList.size(); i++) {
			boolean ngCheck = false;
			long sysItemId = itemCostDTOList.get(i).getSysItemId();
			long sysCorporationId = itemCostDTOList.get(i).getSysCorporationId();

			//すでに重複チェック済の品番×会社IDを見つけた場合は、
			//チェック処理をスルーする。
			for (int j = 0; j < ngItemCost.size(); j++) {
				if (sysItemId == ngItemCost.get(j).getSysItemId()
						&& sysCorporationId == ngItemCost.get(j).getSysCorporationId()) {
					ngCheck = true;
					break;
				}
			}

			if (ngCheck) {
				continue;
			}

			//重複していればエラーメッセージを追加
			Integer count = dao.itemCostRepeatCheck(sysItemId, sysCorporationId);
			if (count != null) {
				if (count > 1) {
					ItemDAO itemDao = new ItemDAO();
					ExtendMstItemDTO mstItemDTO = itemDao.getMstItemDTO(sysItemId);
					result.addErrorMessage("LED00113", "原価", mstItemDTO.getItemCode(), "会社ID", String.valueOf(sysCorporationId));
					ngItemCost.add(itemCostDTOList.get(i));
				}
			}
		}
	}

	/**
	 * 売価の重複チェックをします。
	 * @param result
	 * @param itemPriceDTOList
	 * @throws DaoException
	 */
	private void itemPriceRepeatCheck(Result<ExcelImportDTO> result,
			List<ItemPriceDTO> itemPriceDTOList) throws DaoException {

		ExcelImportDAO dao = new ExcelImportDAO();

		//チェック済の品番リスト
		List<ItemPriceDTO> ngItemPrice = new ArrayList<ItemPriceDTO>();

		for (int i = 0; i < itemPriceDTOList.size(); i++) {
			boolean ngCheck = false;
			long sysItemId = itemPriceDTOList.get(i).getSysItemId();
			long sysCorporationId = itemPriceDTOList.get(i).getSysCorporationId();

			//すでに重複チェック済の品番×会社IDを見つけた場合は、
			//チェック処理をスルーする。
			for (int j = 0; j < ngItemPrice.size(); j++) {
				if (sysItemId == ngItemPrice.get(j).getSysItemId()
						&& sysCorporationId == ngItemPrice.get(j).getSysCorporationId()) {
					ngCheck = true;
					break;
				}
			}

			if (ngCheck) {
				continue;
			}

			//重複していればエラーメッセージを追加
			Integer count = dao.itemPriceRepeatCheck(sysItemId, sysCorporationId);
			if (count != null) {
				if (count > 1) {
					ItemDAO itemDao = new ItemDAO();
					ExtendMstItemDTO mstItemDTO = itemDao.getMstItemDTO(sysItemId);
					result.addErrorMessage("LED00113", "売価", mstItemDTO.getItemCode(), "会社ID", String.valueOf(sysCorporationId));
					ngItemPrice.add(itemPriceDTOList.get(i));
				}
			}
		}
	}

	/**
	 * 原価テーブルのデータを補います。
	 * Excel内に入っていなかった会社IDは、すべて原価0で登録します。
	 * @param result
	 * @param sysItemId
	 * @throws DaoException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void itemCostDataAdd(Result<ExcelImportDTO> result, long sysItemId) throws DaoException, IllegalAccessException, InvocationTargetException {

		ExcelImportDAO dao = new ExcelImportDAO();

		//Excel内に会社IDが入っていなかったものを抜き出します
		List<ItemCostDTO> itemCostDTO = dao.getNotFindItemCost(sysItemId);

		if (itemCostDTO == null) {
			return;
		}

		SequenceDAO SequenceDAO = new SequenceDAO();
		long maxSysItemCostId = SequenceDAO.getMaxSysItemCostId() + 1;

		for (int i = 0; i < itemCostDTO.size(); i++) {
			ItemCostDTO dto = itemCostDTO.get(i);

			//システム商品原価ID
			BeanUtils.setProperty(dto, "sysItemCostId", maxSysItemCostId);

			//システム商品ID
			BeanUtils.setProperty(dto, "sysItemId", sysItemId);

			//システム法人ID
			//取得済のため必要なし

			//原価
			BeanUtils.setProperty(dto, "cost", 0);

			//登録
			dao.insertItemCost(dto);

			maxSysItemCostId++;
		}

		//Kind原価登録：未登録の場合0円で登録する
		if (itemCostDTO.size() > 0) {
			boolean kindCostFlg = false;
			for (ItemCostDTO dto : itemCostDTO) {
				if (dto.getSysCorporationId() == 99) {
					kindCostFlg = true;
				}
			}
			if (!kindCostFlg) {
				ItemCostDTO dto = new ItemCostDTO();
				//システム商品原価ID
				BeanUtils.setProperty(dto, "sysItemCostId", maxSysItemCostId);
				//システム商品ID
				BeanUtils.setProperty(dto, "sysItemId", sysItemId);
				//原価
				BeanUtils.setProperty(dto, "cost", 0);
				//法人ID
				BeanUtils.setProperty(dto, "sysCorporationId", 99);
				//登録
				dao.insertItemCost(dto);
			}
		}

	}

	/**
	 * 売価テーブルのデータを補います。
	 * Excel内に入っていなかった会社IDは、すべて売価0で登録します。
	 * @param result
	 * @param sysItemId
	 * @throws DaoException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void itemPriceDataAdd(Result<ExcelImportDTO> result, long sysItemId) throws DaoException, IllegalAccessException, InvocationTargetException {

		ExcelImportDAO dao = new ExcelImportDAO();

		//Excel内に会社IDが入っていなかったものを抜き出します
		List<ItemPriceDTO> itemPriceDTO = dao.getNotFindItemPrice(sysItemId);

		if (itemPriceDTO == null) {
			return;
		}

		SequenceDAO SequenceDAO = new SequenceDAO();
		long maxSysItemPriceId = SequenceDAO.getMaxSysItemPriceId() + 1;

		for (int i = 0; i < itemPriceDTO.size(); i++) {
			ItemPriceDTO dto = itemPriceDTO.get(i);
			//システム商品原価ID
			BeanUtils.setProperty(dto, "sysItemPriceId", maxSysItemPriceId);
			//システム商品ID
			BeanUtils.setProperty(dto, "sysItemId", sysItemId);
			//原価
			BeanUtils.setProperty(dto, "price", 0);
			//登録
			dao.insertItemPrice(dto);
			maxSysItemPriceId++;
		}
	}

	/**
	 * 倉庫在庫テーブルのデータを補います
	 * @param result
	 * @param sysItemId
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void itemWarehouseAdd(Result<ExcelImportDTO> result, long sysItemId) throws DaoException, IllegalAccessException, InvocationTargetException {
		ExcelImportDAO dao = new ExcelImportDAO();

		//Excel内に倉庫IDが入っていなかったものを抜き出す
		List<WarehouseStockDTO> itemWarehouseDto = dao.getNotFindItemWarehouse(sysItemId);

		if (itemWarehouseDto == null) {
			return;
		}

		SequenceDAO seqDao = new SequenceDAO();
		long maxSysWarehouseStockId = seqDao.getMaxSysWarehouseStockId() + 1;

		for (WarehouseStockDTO dto : itemWarehouseDto) {
			//システム商品ID
			BeanUtils.setProperty(dto, "sysItemId", sysItemId);
			//システム倉庫在庫ID
			BeanUtils.setProperty(dto, "sysWarehouseStockId", maxSysWarehouseStockId);
			//システム倉庫ID
			BeanUtils.setProperty(dto, "priority", dto.getSysWarehouseId());
			//在庫数
			BeanUtils.setProperty(dto, "stockNum", 0);
			//ロケーションNo
			BeanUtils.setProperty(dto, "locationNo", "");
			//登録
			dao.insertWarehouseStock(dto);
			maxSysWarehouseStockId++;
		}
	}

	/**
	 * 年間販売数を登録します。
	 * @param result
	 * @param sysItemId
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void annuakSalesAdd(Result<ExcelImportDTO> result, long sysItemId) throws DaoException, IllegalAccessException, InvocationTargetException {
		ItemDAO dao = new ItemDAO();
		AnnualSalesDTO dto = new AnnualSalesDTO();

		SequenceDAO seqDao = new SequenceDAO();
		long maxAnnualSalesId = seqDao.getMaxSysAnnualSalesId() + 1;
		dto.setSysAnnualSalesId(maxAnnualSalesId);
		dto.setSysItemId(sysItemId);
		dao.registryAnnualSales(dto);
	}

	/**
	 * 倉庫ID(NO)の重複チェックをします。
	 * @param result
	 * @param itemCostDTOList
	 * @throws DaoException
	 */
	private void sysWarehouseIdRepeatCheck(Result<ExcelImportDTO> result,
			List<WarehouseStockDTO> WarehouseStockDTOList) throws DaoException {

		ExcelImportDAO dao = new ExcelImportDAO();
		//チェック済の品番リスト
		List<WarehouseStockDTO> ngWarehouseStock = new ArrayList<WarehouseStockDTO>();

		for (int i = 0; i < WarehouseStockDTOList.size(); i++) {
			boolean ngCheck = false;
			long sysItemId = WarehouseStockDTOList.get(i).getSysItemId();
			long sysWarehouseId = WarehouseStockDTOList.get(i).getSysWarehouseId();

			//すでに重複チェック済の品番×倉庫IDを見つけた場合は、
			//チェック処理をスルーする。
			for (int j = 0; j < ngWarehouseStock.size(); j++) {
				if (sysItemId == ngWarehouseStock.get(j).getSysItemId()
						&& sysWarehouseId == ngWarehouseStock.get(j).getSysWarehouseId()) {
					ngCheck = true;
					break;
				}
			}

			if (ngCheck) {
				continue;
			}

			//重複していればエラーメッセージを追加
			Integer count = dao.sysWarehouseIdCheck(sysItemId, sysWarehouseId);
			if (count != null) {
				if (count > 1) {
					ItemDAO itemDao = new ItemDAO();
					ExtendMstItemDTO mstItemDTO = itemDao.getMstItemDTO(sysItemId);
					result.addErrorMessage("LED00113", "倉庫在庫", mstItemDTO.getItemCode(), "倉庫No", String.valueOf(sysWarehouseId));
					ngWarehouseStock.add(WarehouseStockDTOList.get(i));
				}
			}
		}
	}

	/**
	 * 【その他テーブル用】商品ID有無チェック(double型考慮Ver)
	 *
	 * @param result
	 * @param itemCode
	 * @param caption
	 * @throws DaoException
	 */
	private void checkSysItemId(Result<?> result, String itemCode, String caption) throws DaoException {

		if (GenericValidator.isBlankOrNull(itemCode)) {
			return;
		}

		if (itemCode.contains(".")) {
			itemCode = itemCode.substring(0, itemCode.indexOf("."));
		}

		ExcelImportDAO dao = new ExcelImportDAO();
		if (dao.checkSysItemId(itemCode) == null ) {
			result.addErrorMessage("LED00109", caption);
		}
	}

	/**
	 * 倉庫ID有無チェック (double型考慮Ver)
	 *
	 * @param result
	 * @param sysWarehouseId
	 * @param caption
	 * @throws DaoException
	 */
	private void checkSysWarehouseId(Result<?> result, String sysWarehouseId, String caption) throws DaoException {

		if (GenericValidator.isBlankOrNull(sysWarehouseId)) {
			return;
		}

		if (!GenericValidator.isDouble(sysWarehouseId)) {
			result.addErrorMessage("LED00109", caption);
			return;
		}

		if (sysWarehouseId.contains(".")) {
			sysWarehouseId = sysWarehouseId.substring(0, sysWarehouseId.indexOf("."));
		}

		long sysWarehouseIdLn = 0;

		try {
			sysWarehouseIdLn = Integer.parseInt(sysWarehouseId);
		} catch (Exception e) {
			result.addErrorMessage("LED00109", caption);
			return;
		}

		ExcelImportDAO dao = new ExcelImportDAO();
		if (dao.checkSysWarehouseId(sysWarehouseIdLn) == null) {
			result.addErrorMessage("LED00109", caption);
		}
	}


	/**
	 * 【商品マスタ用】商品ID有無チェック (double型考慮Ver)
	 *
	 * @param result
	 * @param itemCode
	 * @param caption
	 * @throws DaoException
	 */
	private void checkSysItemIdMstItem(Result<?> result, String itemCode, String caption) throws DaoException {

		if (GenericValidator.isBlankOrNull(itemCode)) {
			return;
		}

		if (itemCode.contains(".")) {
			itemCode = itemCode.substring(0, itemCode.indexOf("."));
		}

		ExcelImportDAO dao = new ExcelImportDAO();
		if (dao.checkSysItemId(itemCode) != null ) {
			result.addErrorMessage("LED00114", caption);
		}
	}

	/**
	 * 【商品マスタ用】仕入先ID有無チェック (double型考慮Ver)
	 *
	 * @param result
	 * @param itemCode
	 * @param caption
	 * @throws DaoException
	 */
	private void checkSysSupplierIdMstItem(Result<?> result, String sysSupplierId, String caption) throws DaoException {

		if (GenericValidator.isBlankOrNull(sysSupplierId)) {
			return;
		}

		if (sysSupplierId.contains(".")) {
			sysSupplierId = sysSupplierId.substring(0, sysSupplierId.indexOf("."));
		}

		SupplierDAO dao = new SupplierDAO();
		if (dao.getSupplier(Long.valueOf(sysSupplierId)) == null ) {
			result.addErrorMessage("LED00149", caption);
		}
	}
}
