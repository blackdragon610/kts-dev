package jp.co.kts.service.fileImport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.ExcelImportDTO;
import jp.co.kts.app.common.entity.ItemCostDTO;
import jp.co.kts.app.common.entity.ItemPriceDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.WarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.extendCommon.entity.ItemCostPriceDTO;
import jp.co.kts.app.output.entity.ActionErrorExcelImportDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.fileImport.ExcelImportDAO;
import jp.co.kts.dao.mst.SupplierDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.common.ServiceValidator;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * エクセル取り込みで商品情報の更新を行うクラスです
 * 2014/04/18　作成　aito
 *
 * @author aito
 *
 */
public class ExcelXImportItemUpdateService extends ExcelXImportService {

	/**
	 * シート名　倉庫在庫
	 */
	private static final String SHEET_NAME_WAREHOUSE_STOCK = "倉庫在庫";

	/**
	 * シート名　在庫情報
	 */
	private static final String SHEET_NAME_STOCK_INFO = "在庫情報";

	/**
	 * シート名　価格情報：権限有
	 */
	private static final String SHEET_NAME_PRICE_INFO = "価格情報_権限有";

	/**
	 * シート名　価格情報：権限無
	 */
	private static final String SHEET_NAME_PRICE_NOT_AUTH_INFO = "価格情報";

	/**
	 * シート名　商品情報：権限有
	 */
	private static final String SHEET_NAME_ITEM_INFO = "商品情報_権限有";

	/**
	 * シート名　商品情報：権限無
	 */
	private static final String SHEET_NAME_ITEM_NOT_AUTH_INFO = "商品情報";

	/**
	 * シート無しの返却判定値
	 */
	private static final int SHEET_NAME_NO_EXIST = -1;

	@Override
	public ActionErrorExcelImportDTO validate(FormFile excelImportForm) throws Exception {

		ActionErrorExcelImportDTO dto = super.validate(excelImportForm);

		int mstItemSheetNum = 0;

		//ユーザー情報取得
		long userId = ActionContext.getLoginUserInfo().getUserId();
		UserService userService = new UserService();
		MstUserDTO mstUserDTO = new MstUserDTO();
		mstUserDTO = userService.getUserName(userId);
		String auth = mstUserDTO.getOverseasInfoAuth();


		//終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		/************************************************新規Excelシート取込START************************************************/
		//シートの存在を確認
		boolean authInfo = true;
		//シート名が存在することを判定し、そのシート番号を控えます
		int itemInfoNum = 0;
		String itemSheetNm = SHEET_NAME_ITEM_INFO;
		itemInfoNum = checkSheetNameResult(dto.getResult(), wb, SHEET_NAME_ITEM_INFO);

		//権限有シートが存在しなければ権限無しシートが存在するか確認
		if (itemInfoNum < 0) {
			authInfo = false;
			itemSheetNm = SHEET_NAME_ITEM_NOT_AUTH_INFO;
			itemInfoNum = checkSheetNameResult(dto.getResult(), wb, SHEET_NAME_ITEM_NOT_AUTH_INFO);
			if (itemInfoNum >= 0) {
				//権限が違うExcelの場合Exception。
				if (auth.equals("1")) {
					throw new Exception();
				}
			}
		} else {
			//権限が違うExcelの場合Exception。
			if (auth.equals("0")) {
				throw new Exception();
			}
			authInfo = true;
		}
		int stockInfoNum = checkSheetNameResult(dto.getResult(), wb, SHEET_NAME_STOCK_INFO);
		int priceInfoNm = 0;

		priceInfoNm = checkSheetNameResult(dto.getResult(), wb, SHEET_NAME_PRICE_INFO);
		String sheetNm = SHEET_NAME_PRICE_INFO;
		if (priceInfoNm < 0) {
			priceInfoNm = checkSheetNameResult(dto.getResult(), wb, SHEET_NAME_PRICE_NOT_AUTH_INFO);
			sheetNm = SHEET_NAME_PRICE_NOT_AUTH_INFO;
			if (priceInfoNm > 0) {
				//権限が違うExcelの場合Exception。
				if (auth.equals("1")) {
					throw new Exception();
				}
			}
		} else {
			//権限が違うExcelの場合Exception。
			if (auth.equals("0")) {
				throw new Exception();
			}
		}


		int sheetCheckResult = 0;
		sheetCheckResult = itemInfoNum + stockInfoNum + priceInfoNm;
		//終了
		if (!dto.getResult().isSuccess()) {
			return dto;
		}

		//新規Excelシートのいずれかが存在していた場合
		if (sheetCheckResult > -3) {
			//商品情報シートが存在していた場合
			if (itemInfoNum > SHEET_NAME_NO_EXIST) {

				//終了
				if (!dto.getResult().isSuccess()) {
					return dto;
				}

				List<List<String>> strMstItemList =
						uploadExcelFile(wb, wb.getSheetAt(mstItemSheetNum), ServiceConst.UPLOAD_EXCEL_ITEM_COLUMN);
				//取得したデータをDTOにつめる(varidateチェック含む)
				dto = setItemDetailInfo(dto, strMstItemList, authInfo);

				//varidateチェックにかかっていれば、ここで終了
				if (!dto.getResult().isSuccess()) {
					return dto;
				}

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

				if (!dto.getResult().isSuccess()) {
					return dto;
				}

				//商品
				//更新の処理
				for (MstItemDTO mstItemDTO: dto.getExcelImportDTO().getMstItemDTOList()) {

	//				ItemDAO itemDAO = new ItemDAO();
					ExcelImportDAO dao = new ExcelImportDAO();
					if (dao.updateItemInfo(mstItemDTO) == 0) {

						dto.getResult().addErrorMessage("LED00116", mstItemDTO.getItemCode());
						continue;
					}
				}
			}

			// 在庫情報シートが存在していた場合
			if (stockInfoNum > SHEET_NAME_NO_EXIST) {
				//在庫情報のシートから情報をListに格納する
				List<List<String>> strStockListList =
						uploadExcelFile(wb, wb.getSheetAt(stockInfoNum), ServiceConst.UPLOAD_EXCEL_WAREHOUSE_STOCK_LAST_COLUMN);

				//倉庫在庫
				//String型のListからDTOに格納
				initErrorIdex();
				List<WarehouseStockDTO> stockInfoList = new ArrayList<>();
		 		for (int i = 0; i < strStockListList.size(); i++) {

					String itemCode = strStockListList.get(i).get(0);
					ItemService itemService = new ItemService();

					//品番存在有無チェック
					if (StringUtils.isEmpty(itemService.existItemCode(itemCode))) {

						dto.getResult().addErrorMessage("LED00116", SHEET_NAME_WAREHOUSE_STOCK
								,String.valueOf(errorIndex++), itemCode);
						continue;
					}
					/** validate */
					//品番
					ServiceValidator.equalLengthChecker(dto.getResult(), strStockListList.get(i).get(0), "シート名「在庫情報」 " + (errorIndex) + "行目の品番", 11);
					ServiceValidator.checkDouble(dto.getResult(), strStockListList.get(i).get(0), "シート名「在庫情報」 " + (errorIndex) + "行目の品番");

					//倉庫ID
					ServiceValidator.requiredChecker(dto.getResult(), strStockListList.get(i).get(1), "シート名「在庫情報」 " + (errorIndex) + "行目の倉庫ID");
					String sysWarehouseId = strStockListList.get(i).get(1);
					ServiceValidator.inputChecker(dto.getResult(), sysWarehouseId, "シート名「在庫情報」 " + (errorIndex) + "行目の倉庫ID", 1);
					ServiceValidator.checkDouble(dto.getResult(), strStockListList.get(i).get(1), "シート名「在庫情報」 " + (errorIndex) + "行目の倉庫ID");

					//在庫数
					ServiceValidator.requiredChecker(dto.getResult(), strStockListList.get(i).get(2), "シート名「在庫情報」 " + (errorIndex) + "行目の在庫数");
					ServiceValidator.inputChecker(dto.getResult(), strStockListList.get(i).get(2), "シート名「在庫情報」 " + (errorIndex) + "行目の在庫数", 9);
					ServiceValidator.excelStockCheck(dto.getResult(), strStockListList.get(i).get(2), "シート名「在庫情報」 " + (errorIndex) + "行目の在庫数");

					//ロケーションNo
					ServiceValidator.inputChecker(dto.getResult(), strStockListList.get(i).get(3), "シート名「在庫情報」 " + (errorIndex) + "行目のロケーションNo", 30);
					List<String> strListBefore = strStockListList.get(i);

					for (int j = i + 1; j < strStockListList.size(); j++) {

						List<String> strListAfter = strStockListList.get(j);

						if (StringUtils.equals(strListBefore.get(0), strListAfter.get(0))
								&& StringUtils.equals(strListBefore.get(1), strListAfter.get(1))) {

							//本来取り込みが始まっている行のインデックスをiとjに足してエラーを出します
							dto.getResult().addErrorMessage("LED00123", String.valueOf(i + ServiceConst.UPLOAD_EXCEL_INIT_ROWS),
									String.valueOf(j + ServiceConst.UPLOAD_EXCEL_INIT_ROWS), strListBefore.get(0), strListBefore.get(1));
							continue;
						}
					}
					WarehouseStockDTO warehouseDto = new WarehouseStockDTO();
					warehouseDto = setStockInfoDTO(warehouseDto, strListBefore);

					// 商品ID, 倉庫IDから倉庫在庫IDを取得します
					long sysWarehouseStockId = itemService.getSysWarehouseStockId(
							warehouseDto.getSysItemId()
							, warehouseDto.getSysWarehouseId());

					//存在しない組み合わせの場合
					if (sysWarehouseStockId == 0) {

						//エラーの場合はList<String>型の方から品番・倉庫IDを取得します
						dto.getResult().addErrorMessage("LED00124", String.valueOf(errorIndex++),
								strListBefore.get(0), strListBefore.get(1));
						continue;
					}

					warehouseDto.setSysWarehouseStockId(sysWarehouseStockId);

					stockInfoList.add(warehouseDto);
					errorIndex++;
		 		}

				//エクセルフォーマットに問題があれば、ここで終了
				if (!dto.getResult().isSuccess()) {
					return dto;
				}

		 		//倉庫在庫
				//更新
				initErrorIdex();
				for (WarehouseStockDTO warehouseStockDTO: stockInfoList) {

					warehouseStockDTO.setPriority(null);
					ExcelImportDAO dao = new ExcelImportDAO();
					List<ExtendWarehouseStockDTO> beforeStockDto = dao.selectWarehouseStockInfo(warehouseStockDTO.getSysWarehouseStockId());
					//倉庫在庫が更新されなかった場合、総・仮在庫の更新と組立可数の更新を行いません
					if (dao.updateWarehouseStock(warehouseStockDTO) == 0){
						continue;
					}
					List<ExtendWarehouseStockDTO> afterStockDto = dao.selectWarehouseStockInfo(warehouseStockDTO.getSysWarehouseStockId());

					//総・仮在庫数の更新
					//更新者情報更新
					ItemService itemService = new ItemService();
					itemService.insertWarehouseInfo(afterStockDto, beforeStockDto, "0");
					itemService.updateInfo(warehouseStockDTO.getSysItemId());
					//総在庫数更新
					itemService.updateTotalStockNum(warehouseStockDTO.getSysItemId());
					/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
//					/* 組立可数更新 */
//					itemService.setAllAssemblyNum(warehouseStockDTO.getSysItemId());


				}
			}

			//価格情報シートが存在する場合
			if (priceInfoNm > SHEET_NAME_NO_EXIST) {

				//価格情報のシートから情報をListに格納する
				List<List<String>> strCostPriceInfoList =
						uploadExcelFile(wb,wb.getSheetAt(priceInfoNm), ServiceConst.UPLOAD_EXCEL_SET_PRICE_LAST_COLUMN);

				//価格情報
				//String型のListからDTOに格納
				List<ItemCostPriceDTO> itemCostPriceList = new ArrayList<>();
				for (List<String> strList: strCostPriceInfoList) {

					ItemCostPriceDTO itemCostPriceDto = new ItemCostPriceDTO();
					//品番を一時的に取得します
					String itemCode = strList.get(0);
					ItemService itemService = new ItemService();

					//品番が存在しない場合
					if (StringUtils.isEmpty(itemService.existItemCode(itemCode))) {

						dto.getResult().addErrorMessage("LED00116", sheetNm
								, String.valueOf(errorIndex++), itemCode);
						continue;
					}

					//価格情報の入力制御 権限有の場合
					if (sheetNm.equals(SHEET_NAME_PRICE_INFO)) {
						//いずれ定数化今は勘弁して
						ServiceValidator.checkDouble(dto.getResult(), strList.get(1), "シート名「" + sheetNm + "」 " + (errorIndex) + "行目の仕入金額");
						ServiceValidator.checkDouble(dto.getResult(), strList.get(2), "シート名「" + sheetNm + "」 " + (errorIndex) + "行目の加算経費");
						ServiceValidator.checkDouble(dto.getResult(), strList.get(3), "シート名「" + sheetNm + "」 " + (errorIndex) + "行目のKind原価");

						// 原価の設定
//						for (int i = 4; i < 16; i++) {
						for (int i = 4; i < 17; i++) {
							ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + sheetNm + "」 " + (errorIndex) + "行目の原価");
							//エクセルフォーマットに問題があれば、ここで終了
							if (!dto.getResult().isSuccess()) {
								break;
							}
						}
						// 売価の設定
//						for (int i = 16; i <= 27; i++) {
						for (int i = 17; i <= 28; i++) {
							ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + sheetNm + "」 " + (errorIndex) + "行目の売価");
							//エクセルフォーマットに問題があれば、ここで終了
							if (!dto.getResult().isSuccess()) {
								break;
							}
						}

					// 権限無しの場合
					} else {

						// 原価の設定
						//TODO いずれ定数化今は勘弁して
						for (int i = 1; i < 14; i++) {
//						for (int i = 1; i < 13; i++) {
							ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + sheetNm + "」 " + (errorIndex) + "行目の原価");
							//エクセルフォーマットに問題があれば、ここで終了
							if (!dto.getResult().isSuccess()) {
								break;

							}
						}
						// 売価の設定
//						for (int i = 13; i <= 24; i++) {
						for (int i = 14; i <= 25; i++) {
							ServiceValidator.checkDouble(dto.getResult(), strList.get(i), "シート名「" + sheetNm + "」 " + (errorIndex) + "行目の売価");
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

					itemCostPriceList.add(setItemCostPriceDTO(itemCostPriceDto, strList));
					errorIndex++;
				}

				//エクセルフォーマットに問題があれば、ここで終了
				if (!dto.getResult().isSuccess()) {
					return dto;
				}


				//価格情報
				//品番重複チェック
				for (int i = 0; i < itemCostPriceList.size(); i++) {

					String itemCode = itemCostPriceList.get(i).getItemCode();

					for (int j = i + 1; j < itemCostPriceList.size(); j++) {

						if (StringUtils.equals(itemCode, itemCostPriceList.get(j).getItemCode())) {

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
				for (ItemCostPriceDTO itemCostPriceDto: itemCostPriceList) {

					ExcelImportDAO dao = new ExcelImportDAO();

					/*
					 * 原価テーブルに不足している法人のレコードが存在するので
					 * 法人のレコード（品番＋会社IDの組み合わせ）を探します。
					 */
					List<ItemCostDTO> itemCostDTO = dao.getNotFindItemCost(itemCostPriceDto.getSysItemId());
					List<Long> noticlist = new ArrayList<Long>();

					for (ItemCostDTO iCDto : itemCostDTO) {
						noticlist.add(iCDto.getSysCorporationId());
					}

					//原価情報の更新
					for (int i = 0 ; i < itemCostPriceDto.getItemCostList().size(); i++) {

						//MST_商品の仕入価格を更新
						if (itemCostPriceDto.getSysCorporationIdCostList().get(i).equals("77")) {

							dao.updateMstItemCostOrLoading(itemCostPriceDto.getSysItemId(),
									itemCostPriceDto.getItemCostList().get(i),
										itemCostPriceDto.getSysCorporationIdCostList().get(i));
							continue;
						}

						//MST_商品の加算経費を更新
						if (itemCostPriceDto.getSysCorporationIdCostList().get(i).equals("88")) {

							dao.updateMstItemCostOrLoading(itemCostPriceDto.getSysItemId(),
									itemCostPriceDto.getItemCostList().get(i),
										itemCostPriceDto.getSysCorporationIdCostList().get(i));
							continue;
						}


						//商品原価を更新
						if (dao.updateCostInfo(itemCostPriceDto.getSysItemId(),
								itemCostPriceDto.getSysCorporationIdCostList().get(i), itemCostPriceDto.getItemCostList().get(i)) == 0) {

							/*
							 * 「Kind原価:99」が登録されていない場合、入力された情報で更新
							 * または
							 * 新規で追加された法人の場合原価のレコードが存在しない可能性があるため、
							 * その場合には、原価テーブルに新規追加分の法人用レコードをインサートする。
							 */
							//
							if (itemCostPriceDto.getSysCorporationIdCostList().get(i).equals("99")
									 || noticlist.contains(Long.parseLong(itemCostPriceDto.getSysCorporationIdCostList().get(i)))) {
								ItemCostDTO itemCostDto = new ItemCostDTO();
								SequenceDAO seqDao = new SequenceDAO();

								// 法人ID
								itemCostDto.setSysCorporationId(Long.parseLong(itemCostPriceDto.getSysCorporationIdCostList().get(i)));

								// 原価
								itemCostDto.setCost(itemCostPriceDto.getItemCostList().get(i));

								//システム商品原価ID
								long maxSysItemCostId = seqDao.getMaxSysItemCostId() + 1;
								itemCostDto.setSysItemCostId(maxSysItemCostId);

								//システム商品ID
								itemCostDto.setSysItemId(itemCostPriceDto.getSysItemId());

								dao.insertItemCost(itemCostDto);
							} else {
								dto.getResult().addErrorMessage("LED7", sheetNm , itemCostPriceDto.getItemCode());
								continue;
							}

							// フォーマットに対して全ての法人のレコードが存在するが登録しようとしている値に問題がある場合はエラーとする。
						//	dto.getResult().addErrorMessage("LED00147", sheetNm , itemCostPriceDto.getItemCode());
						//	continue;

						}
					}

					/*
					 * 売価テーブルに不足している法人のレコードが存在するので
					 * 法人のレコード（品番＋会社IDの組み合わせ）を探します。
					 */
					List<ItemPriceDTO> itemPriceDTO = dao.getNotFindItemPrice(itemCostPriceDto.getSysItemId());
					List<Long> notiplist = new ArrayList<Long>();

					for (ItemPriceDTO iPDto : itemPriceDTO) {
						notiplist.add(iPDto.getSysCorporationId());
					}


					//売価情報の更新
					for (int i = 0 ; i < itemCostPriceDto.getItemPriceList().size(); i++) {
						if (dao.updatePriceInfo(itemCostPriceDto.getSysItemId(),
								itemCostPriceDto.getSysCorporationIdPriceList().get(i), itemCostPriceDto.getItemPriceList().get(i)) == 0) {

							/*
							 * 新規で追加された法人の場合売価のレコードが存在しない可能性があるため、
							 * その場合には、売価テーブルに新規追加分の法人用レコードをインサートする。
							 */
							if (notiplist.contains(Long.parseLong(itemCostPriceDto.getSysCorporationIdPriceList().get(i)))) {
								ItemPriceDTO itemPriceDto = new ItemPriceDTO();
								SequenceDAO seqDao = new SequenceDAO();

								// 法人ID
								itemPriceDto.setSysCorporationId(Long.parseLong(itemCostPriceDto.getSysCorporationIdPriceList().get(i)));

								// 売価
								itemPriceDto.setPrice(itemCostPriceDto.getItemPriceList().get(i));

								//システム商品原価ID
								long maxSysItemPricetId = seqDao.getMaxSysItemPriceId() + 1;
								itemPriceDto.setSysItemPriceId(maxSysItemPricetId);

								//システム商品ID
								itemPriceDto.setSysItemId(itemCostPriceDto.getSysItemId());

								//売価レコードが存在していない法人のレコードをインサートする。
								dao.insertItemPrice(itemPriceDto);

								continue;
							}

							dto.getResult().addErrorMessage("LED00147", sheetNm, itemCostPriceDto.getItemCode());
							continue;
						}
					}
					ItemService service = new ItemService();
					service.updateInfo(itemCostPriceDto.getSysItemId());
				}
			}
			/************************************************新Excelシート取込END************************************************/
		}

		int sumSheetCheckResult = sheetCheckResult;

		if (sumSheetCheckResult == -3) {
			dto.getResult().addErrorMessage("LED00152");
			//終了
			if (!dto.getResult().isSuccess()) {
				return dto;
			}
		}

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
		ItemService itemService = new ItemService();
		errorIndex = ServiceConst.UPLOAD_EXCEL_INIT_ROWS + 1;
		String sheetNm = "";
		if (authInfo) {
			sheetNm = "商品情報_権限有";
		} else {
			sheetNm = "商品情報";
		}
		for (int i = 0; i < mstItemInfoList.size(); i++) {
			MstItemDTO midto = new MstItemDTO();

			//品番が存在しない場合
			if (StringUtils.isEmpty(itemService.existItemCode(mstItemInfoList.get(i).get(0)))) {
				result.addErrorMessage("LED00116", sheetNm
						, String.valueOf(i + errorIndex), mstItemInfoList.get(i).get(0));
				continue;
			}

			/** validate */
			//品番
			ServiceValidator.requiredChecker(result, mstItemInfoList.get(i).get(0), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の品番");
			ServiceValidator.equalLengthChecker(result, mstItemInfoList.get(i).get(0), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の品番", 11);
			ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(0), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の品番");

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
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(19), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の最少ロット数");

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
						result.addErrorMessage("シート名「" + sheetNm + "」 " +(i + errorIndex) + "行目の商品リードタイムが不正です。");
					}
				}
				WebConst.LEAD_TIME_MAP.get(mstItemInfoList.get(i).get(8));

				//仕様メモ
				ServiceValidator.inputChecker(result, mstItemInfoList.get(i).get(6), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の仕様メモ", 500);

				//重量
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(10), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の重量");

				//最少ロット数
				ServiceValidator.checkDouble(result, mstItemInfoList.get(i).get(13), "シート名「" + sheetNm + "」 " + (i + errorIndex) + "行目の最少ロット数");

			}
			//すでにエラーがある場合、dtoに格納する意味が無くなるので、
			//次のvalidate処理へ移す。
			if (!result.isSuccess()) {
				continue;
			}

			//システム商品ID
			midto.setSysItemId(itemService.getSysItemId(mstItemInfoList.get(i).get(0)));
			mstItem.add(setMstItemDetailDTO(midto, mstItemInfoList.get(i), authInfo));
		}

		dto.setResult(result);
		excelImportDTO.setMstItemDTOList(mstItem);
		dto.setExcelImportDTO(excelImportDTO);

		return dto;
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

		if (!GenericValidator.isDouble(sysSupplierId)) {
			result.addErrorMessage("LED00106", caption);
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
