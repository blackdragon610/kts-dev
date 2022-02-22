package jp.co.kts.ui.mst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.ForeignSlipSearchDTO;
import jp.co.kts.app.common.entity.UpdateDataHistoryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderDTO;
import jp.co.kts.app.extendCommon.entity.ExtendForeignOrderItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.fileImport.ExcelImportDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.ForeignOrderDAO;
import jp.co.kts.dao.mst.UpdateDataHistoryDAO;
import jp.co.kts.dao.mst.WarehouseDAO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * [概要] 海外注文管理serviceクラス
 * @author Boncre
 *
 */
public class ForeignOrderService {

	/** 海外注文一覧検索結果チェックボックス選択値 */
	private static final String CHECK_BOX_FLG_ON = "on";

	/** 処理状態：未 */
	static final String PROCESSING_VAL1 = "未";
	/** 処理状態：済 */
	static final String PROCESSING_VAL2 = "済";

	/**
	 * ユーザー情報取得
	 * @param dto
	 */
	public void initSetPersonInCharge(ExtendForeignOrderDTO dto) {

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		dto.setPersonInCharge(userInfo.getFamilyName() + userInfo.getFirstName());
	}

	/**
	 * 登録伝票情報の検索
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public ExtendForeignOrderDTO getdetail(long sysForeignSlipId) throws DaoException {

		//インスタンス生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		ExtendForeignOrderDTO dto = dao.getForeignOrderSlipSearch(sysForeignSlipId);

		return dto;
	}

	/**
	 * 登録商品情報の検索
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendForeignOrderItemDTO> getForeignItemSearch(long sysForeignSlipId) throws DaoException {

		//インスタンス生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		List<ExtendForeignOrderItemDTO> list = dao.getForeignOrderSlipItemSearch(sysForeignSlipId);

		return list;
	}

	/**
	 * PDF注文書商品情報の検索
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendForeignOrderItemDTO> getForeignItemOrderSearch(long sysForeignSlipId) throws DaoException {

		//インスタンス生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		List<ExtendForeignOrderItemDTO> list = dao.getForeignOrderItemSearch(sysForeignSlipId);

		return list;
	}

	/**
	 * [概要] 追加用海外注文商品リストを設定する処理
	 * @return
	 */
	public List<ExtendForeignOrderItemDTO> initAddItemList() {

		//インスタンスを生成
		List<ExtendForeignOrderItemDTO> list = new ArrayList<>();

		//追加用海外注文商品リストを指定数分を作成
		for (int i = 0; i < WebConst.ADD_FOREIGN_ORDER_LIST_LENGTH ; i++) {

			ExtendForeignOrderItemDTO dto = new ExtendForeignOrderItemDTO();
			list.add(dto);
		}

		return list;
	}

	/**
	 * [概要]海外注文伝票を登録する
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int registryForeignOrderSlip(ExtendForeignOrderDTO dto) throws Exception {

		//システム海外注文伝票ID付与
		dto.setSysForeignSlipId(new SequenceDAO().getMaxSysForeignSlipId() + 1);

		//訂正フラグ読替
		if (StringUtils.isNotEmpty(dto.getCorrectionFlag())) {
			dto.setCorrectionFlag(StringUtil.switchCheckBox(dto
					.getCorrectionFlag()));
		}

		//インスタンス生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		//登録結果格納
		int result = dao.registryForeignOrderSlip(dto);

		return result;
	}

	/**
	 * 入荷予定日を登録
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int registryForeignOrderSlipArrival(ExtendForeignOrderDTO dto, long sysForeignSlipId) throws Exception {

		//システム海外注文伝票ID付与
		dto.setSysArrivalScheduleId(new SequenceDAO().getMaxSysArrivalScheduleId() + 1);

		//インスタンス生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		//登録結果格納
		int result = dao.registryForeignOrderSlipArrival(dto, sysForeignSlipId);

		return result;
	}

	/**
	 * 商品登録、更新バリデート 重複チェック
	 * @param addItemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws Exception
	 */
	public RegistryMessageDTO ForeignOrderItemValidate(RegistryMessageDTO registryDto, List<ExtendForeignOrderItemDTO> addItemList, List<ExtendForeignOrderItemDTO> itemList, long sysSupplierId) throws Exception {

		//-------- 品番が存在しているかチェック  --------------------------------------------------------------------------

		for (ExtendForeignOrderItemDTO dto: addItemList) {

			// 品番が無いものは確認しない
			if (StringUtils.isBlank(dto.getItemCode())) {
				continue;
			}

			//インスタンスの生成
			ForeignOrderDAO dao = new ForeignOrderDAO();
			//登録された品番があるか検索
			ExtendForeignOrderItemDTO itemCodeDto = dao.ForeignOrderItemValidate(dto.getItemCode());
			//商品が存在しない場合伝票を作成しないで終了
			if (itemCodeDto == null) {
				registryDto.getResult().addErrorMessage("LED00151",dto.getItemCode());
				registryDto.setSuccess(false);
			} else {
				//検索ボタン押していない対策として検索して得た商品情報をセット
				//システムID
				dto.setSysItemId(itemCodeDto.getSysItemId());
				//仕入れ先ID
				dto.setSysSupplierId(itemCodeDto.getSysSupplierId());
			}
		}

		//-------- 品番が重複しているかチェック  --------------------------------------------------------------------------

		Map<String, String> checkUpdMngCd = new HashMap<String, String>();

		for (ExtendForeignOrderItemDTO addDto : addItemList) {

			//品番が空ならcontinue
			if (addDto.getItemCode().equals(StringUtils.EMPTY)) {
				continue;
			}

			//品番が00ならcontinue
			if (StringUtils.equals(addDto.getItemCode(), "00")) {
				continue;
			}

			if (checkUpdMngCd.containsKey(addDto.getItemCode())){
				registryDto.getResult().addErrorMessage("LED00142",addDto.getItemCode());
				registryDto.setSuccess(false);
			}
			checkUpdMngCd.put( addDto.getItemCode(), addDto.getItemCode());
		}

		for (ExtendForeignOrderItemDTO dto : itemList) {

			if (dto.getItemCode().equals(StringUtils.EMPTY)) {
				continue;
			}

			//品番が00ならcontinue
			if (StringUtils.equals(dto.getItemCode(), "00")) {
				continue;
			}

			if (checkUpdMngCd.containsKey(dto.getItemCode())){
				registryDto.getResult().addErrorMessage("LED00142",dto.getItemCode());
				registryDto.setSuccess(false);
			}

			checkUpdMngCd.put( dto.getItemCode(), dto.getItemCode());
		}

		//-------- 仕入先IDと商品が関係しているかチェック  --------------------------------------------------------------------------

		//登録済みレコード
		for (ExtendForeignOrderItemDTO dto : itemList) {

			// 品番が無いものは確認しない
			if (StringUtils.isBlank(dto.getItemCode()) || StringUtils.equals(dto.getItemCode(), "00")) {
				continue;
			}

			//商品と仕入先IDが合っているか検索
			ExtendForeignOrderItemDTO suppDto = new ForeignOrderDAO().validateItemSupplier(dto, sysSupplierId);

			if (suppDto == null) {
				registryDto.getResult().addErrorMessage("LED00143",dto.getItemCode());
				registryDto.setSuccess(false);
			}
		}

		//追加レコード
		for (ExtendForeignOrderItemDTO dto : addItemList) {

			// 品番が無いものは確認しない
			if (StringUtils.isBlank(dto.getItemCode()) || StringUtils.equals(dto.getItemCode(), "00")) {
				continue;
			}

			//商品と仕入先が合っているか検索
			ExtendForeignOrderItemDTO suppDto = new ForeignOrderDAO().validateItemSupplier(dto, sysSupplierId);

			if (suppDto == null) {
				registryDto.getResult().addErrorMessage("LED00143",dto.getItemCode());
				registryDto.setSuccess(false);
			}
		}

		return registryDto;
	}

	/**
	 * [概要]海外注文商品リストを登録する
	 * @param addItemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws Exception
	 */
	public int registryForeignOrderItemList(List<ExtendForeignOrderItemDTO> addItemList, long sysForeignSlipId) throws Exception {

		//インスタンスの生成
		ForeignOrderDAO dao = new ForeignOrderDAO();

		int result = 0;
		for (int i = 0; i < addItemList.size(); i++) {

			if (StringUtils.isBlank(addItemList.get(i).getItemCode())) {
				continue;
			}
			//システム海外注文商品IDを付与
			long sysForeignSlipItemId = new SequenceDAO().getMaxSysForeignSlipItemId() + 1;
			addItemList.get(i).setSysForeignSlipItemId(sysForeignSlipItemId);

			result += dao.registryForeignOrderItemList(addItemList.get(i), sysForeignSlipId);
		}

		return result;
	}

	/**
	 * 商品の注文数、入荷予定日の登録
	 * @param addItemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws Exception
	 */
	public int registryArrival(List<ExtendForeignOrderItemDTO> addItemList, long sysForeignSlipId, String orderStaus, String orderDate) throws Exception {

		int result = 0;
		for (ExtendForeignOrderItemDTO dto : addItemList) {

 			// 管理品番・商品名・注文数が空のものは登録しない
			if (StringUtils.isBlank(dto.getItemCode())) {
				continue;
			}

			//システム商品IDは再度マスタから取りに行く
			if (dto.getSysItemId() == 0) {
				ForeignOrderDAO dao = new ForeignOrderDAO();
				ExtendForeignOrderItemDTO itemCodeDto = dao.ForeignOrderItemValidate(dto.getItemCode());
				dto.setSysItemId(itemCodeDto.getSysItemId());
			}

			//システム入荷予定ID付与
			dto.setSysArrivalScheduleId(new SequenceDAO().getMaxSysArrivalScheduleId() + 1);
			//入荷数に注文数を入れる
			dto.setArrivalNum(dto.getOrderNum());

			//インスタンスの生成
			ForeignOrderDAO dao = new ForeignOrderDAO();
			result += dao.registryArrival(dto, sysForeignSlipId, orderStaus, orderDate);
		}

		return result;
	}

	/**
	 * [概要]海外注文商品リストのorderPoolFlagを０登録する
	 * @param addItemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws Exception
	 */
	public int setOrderPoolFlag(List<ExtendForeignOrderItemDTO> addItemList) throws Exception {

		int result = 1;
		for (ExtendForeignOrderItemDTO dto: addItemList) {

			//品番/システム商品IDがないものはcontinue
			if (StringUtils.equals(dto.getItemCode(), "") || dto.getSysItemId() == 0
					|| StringUtils.equals(dto.getItemCode(), "00")) {
				continue;
			}

			//インスタンスの生成
			ForeignOrderDAO dao = new ForeignOrderDAO();
			result = dao.setOrderPoolFlag(dto);
		}
		return result;
	}

	/**
	 * [概要]海外注文伝票を更新する
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int updateForeignOrderSlip(ExtendForeignOrderDTO dto) throws Exception {

		//訂正フラグ
		if (StringUtils.isNotEmpty(dto.getCorrectionFlag())) {
			dto.setCorrectionFlag(StringUtil.switchCheckBox(dto
					.getCorrectionFlag()));
		}

		//納期1超過フラグ読替
		if (StringUtils.isNotEmpty(dto.getDeliveryDate1OverFlag())) {
			dto.setDeliveryDate1OverFlag(StringUtil.switchCheckBox(dto
					.getDeliveryDate1OverFlag()));
		}

		//納期2超過フラグ読替
		if (StringUtils.isNotEmpty(dto.getDeliveryDate2OverFlag())) {
			dto.setDeliveryDate2OverFlag(StringUtil.switchCheckBox(dto
					.getDeliveryDate2OverFlag()));
		}

		//インスタンスを生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		//更新結果格納
		int result = dao.updateForeignOrderSlip(dto);

		return result;
	}

	/**
	 * 伝票入荷予定日の更新
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int updateForeignOrderSlipArrival(ExtendForeignOrderDTO dto, long sysForeignSlipId) throws Exception {

		//インスタンスを生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		//更新結果格納
		int result = dao.updateForeignOrderSlipArrival(dto, sysForeignSlipId);

		return result;
	}
	/**
	 * [概要]海外注文商品リストを更新する
	 * @param addItemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws Exception
	 */
	public int updateForeignOrderItemList(List<ExtendForeignOrderItemDTO> itemList, long sysForeignSlipId) throws Exception {

		int result = 1;
		for (ExtendForeignOrderItemDTO dto : itemList) {

			// 管理品番・商品名・注文数が空のものは更新しない
//			if (StringUtils.isBlank(dto.getManagementCode()) || StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
//				continue;
//			}

			//インスタンスの生成
			ForeignOrderDAO dao = new ForeignOrderDAO();
			result = dao.updateForeignSlipItem(dto, sysForeignSlipId);
		}

		return result;
	}

	/**
	 * 商品注文数、入荷日の更新
	 * @param itemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws Exception
	 */
	public int updateArrival(List<ExtendForeignOrderItemDTO> itemList, long sysForeignSlipId) throws Exception {

		int result = 1;
		for (ExtendForeignOrderItemDTO dto : itemList) {

			if (StringUtils.isBlank(dto.getItemCode())) {
				continue;
			}
			//インスタンスの生成
			ForeignOrderDAO dao = new ForeignOrderDAO();
			result = dao.updateForeignSlipItemArrival(dto, sysForeignSlipId);
		}

		return result;
	}

	/**
	 * [概要]商品チェックボックスで選択されている商品にチェックボックスフラグを設定する
	 * @param foreignOrderSlipList
	 */
	public void setItemFlags(List<ExtendForeignOrderItemDTO> itemList) {


		for (ExtendForeignOrderItemDTO dto : itemList) {

			if (StringUtils.isNotEmpty(dto.getDeleteCheckFlg())) {
				dto.setDeleteCheckFlg(StringUtil.switchCheckBox(dto
						.getDeleteCheckFlg()));
			}
		}
	}

	/**
	 * 登録済み商品の削除処理
	 * @param supplierDTO
	 * @return
	 * @throws Exception
	 */
	public int deleteItem(List<ExtendForeignOrderItemDTO> list) throws Exception {

		int resultDeleteCnt = 0;
		for (ExtendForeignOrderItemDTO dto: list) {

			if (StringUtils.equals(dto.getDeleteCheckFlg(), "1")) {

				ForeignOrderDAO dao = new ForeignOrderDAO();
				//商品の削除
				resultDeleteCnt += dao.deleteItem(dto);

				//入荷予定テーブルからの商品ごとの削除
				dao.deleteArrivalItem(dto);
			}
		}
		return resultDeleteCnt;
	}

	/**
	 * [概要]仕入先情報を検索する処理
	 * @param supplierDTO
	 * @return
	 * @throws Exception
	 */
	public List<ExtendMstSupplierDTO> getSearchSupplierList(long sysSupplierId) throws Exception {

		List<ExtendMstSupplierDTO> supplierDto = new ForeignOrderDAO().getSupplierList(sysSupplierId);

		return supplierDto;
	}

	/**
	 * [概要]PoNo.を作成する
	 * @param foreignOrderDTO
	 * @throws Exception
	 */
	public void createPoNo(ExtendForeignOrderDTO foreignOrderDTO, long sysSupplierId) throws Exception {

		//仕入先IDに紐付くpoNoの数値部分の最大値を取得
		String poNo = new SequenceDAO().getMaxPoNo(sysSupplierId);

		//PoNo頭文字を取得(頭文字部分)
		String poNoInitial = new ForeignOrderDAO().getPoNoInitial(sysSupplierId);

		//取得したPoNoの数値部分をint型に変換する
		int poNoInt = Integer.parseInt(poNo) + 1;

		//最大5桁、0埋め(数値部分)
		String poNoTerminal = String.format("%05d", poNoInt);

		/*仕入先ごとの、既存の通し番号を引き継ぐ処理
		(運用開始後、不要になった時点で削除する)**/
		if (poNoInt == 1) {

			switch(poNoInitial) {
			case "ABL":
				poNoTerminal = ("00032");
				break;
			case "TBC":
				poNoTerminal = ("00346");
				break;
			case "CCI":
				poNoTerminal = ("00055");
				break;
			case "GCC":
				poNoTerminal = ("00006");
				break;
			case "GCA":
				poNoTerminal = ("00042");
				break;
			case "GYA":
				poNoTerminal = ("00008");
				break;
			case "HHI":
				poNoTerminal = ("00035");
				break;
			case "HAA":
				poNoTerminal = ("00006");
				break;
			case "HDS":
				poNoTerminal = ("00170");
				break;
			case "HKY":
				poNoTerminal = ("00247");
				break;
			case "HTC":
				poNoTerminal = ("00026");
				break;
			case "HYA":
				poNoTerminal = ("00005");
				break;
			case "JJE":
				poNoTerminal = ("00055");
				break;
			case "KLB":
				poNoTerminal = ("00021");
				break;
			case "KCA":
				poNoTerminal = ("00004");
				break;
			case "NAA":
				poNoTerminal = ("00020");
				break;
			case "TRC":
				poNoTerminal = ("00013");
				break;
			case "PIR":
				poNoTerminal = ("00029");
				break;
			case "TLA":
				poNoTerminal = ("00009");
				break;
			case "RHA":
				poNoTerminal = ("00014");
				break;
			case "WRV":
				poNoTerminal = ("00009");
				break;
			case "ZCA":
				poNoTerminal = ("00002");
				break;
			case "ZLA":
				poNoTerminal = ("00002");
				break;
			case "ZHD":
				poNoTerminal = ("00004");
				break;
			case "BRC":
				poNoTerminal = ("00018");
				break;
			case "BRI":
				poNoTerminal = ("00010");
				break;
			case "BQP":
				poNoTerminal = ("00001");
				break;
			default:
				break;
			}
		}

		foreignOrderDTO.setPoNo(poNoInitial + poNoTerminal);
	}

	public void setSearchFlags(ForeignSlipSearchDTO searchDTO) {

		if (StringUtils.isNotEmpty(searchDTO.getDeliveryDateOverFlg1())) {
			searchDTO.setDeliveryDateOverFlg1(StringUtil
					.switchCheckBox(searchDTO.getDeliveryDateOverFlg1()));
		}

		if (StringUtils.isNotEmpty(searchDTO.getDeliveryDateOverFlg2())) {
			searchDTO.setDeliveryDateOverFlg2(StringUtil
					.switchCheckBox(searchDTO.getDeliveryDateOverFlg2()));
		}

	}

	/**
	 * [概要]海外注文伝票IDリストを取得する
	 * @param searchDTO
	 * @return
	 * @throws Exception
	 */
	public List<ExtendForeignOrderDTO> getSysForeignSlipIdList(ForeignSlipSearchDTO searchDTO) throws Exception {

		List<ExtendForeignOrderDTO> list = new ForeignOrderDAO().getForeignOrderIdSearch(searchDTO);

		return list;
	}

	/**
	 * [概要]海外注文伝票の検索とページングを設定する
	 * @param sysForeignSlipIdList
	 * @param pageIdx
	 * @param searchDTO
	 * @return
	 * @throws Exception
	 */
	public List<ExtendForeignOrderDTO> getForeignOrderSlipSearch(List<ExtendForeignOrderDTO> sysForeignSlipIdList,
			int pageIdx, ForeignSlipSearchDTO searchDTO) throws Exception {

		//インスタンス生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		List<ExtendForeignOrderDTO> list = new ArrayList<>();

		if (StringUtils.isEmpty(searchDTO.getListPageMax())) {
			searchDTO.setListPageMax("1");
		}

		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(searchDTO.getListPageMax()) * pageIdx;
				i < WebConst.LIST_PAGE_MAX_MAP.get(searchDTO.getListPageMax()) * (pageIdx + 1)
				&& i < sysForeignSlipIdList.size(); i++) {

			long sysForeignSlipId = sysForeignSlipIdList.get(i).getSysForeignSlipId();

			ExtendForeignOrderDTO slipDTO = dao.getForeignOrderSlipIdSearch(sysForeignSlipId);

			slipDTO.setOrderStatusNm(WebConst.ORDER_STATUS_NUMBERED_MAP.get(slipDTO.getOrderStatus()));

			if (StringUtils.isBlank(slipDTO.getPaymentDate1())) {
				slipDTO.setPaymentStatus1(PROCESSING_VAL1);
			} else {
				slipDTO.setPaymentStatus1(PROCESSING_VAL2);
			}

			if (StringUtils.isBlank(slipDTO.getPaymentDate2())) {
				slipDTO.setPaymentStatus2(PROCESSING_VAL1);
			} else {
				slipDTO.setPaymentStatus2(PROCESSING_VAL2);
			}

			//伝票毎の商品を検索する
			List<ExtendForeignOrderItemDTO> itemList = this.getForeignOrderItemList(searchDTO, sysForeignSlipId);


//			slipDTO.setItemList(itemList);

			//伝票毎の入荷予定を検索する
//			List<ExtendArrivalScheduleDTO> arrivalScheduleList = this.getForeignOrderArrivalScheduleList(searchDTO, sysForeignSlipId, itemList);

			for (ExtendForeignOrderItemDTO dto : itemList) {

				ExtendArrivalScheduleDTO arrivalScheduleDTO = dao.getForeignOrderArrivalScheduleList(searchDTO, sysForeignSlipId, dto);
				
				if (arrivalScheduleDTO == null) 
					continue;

				if (arrivalScheduleDTO.getArrivalNum() < dto.getOrderNum() || arrivalScheduleDTO.getArrivalFlag().equals("0")) {
					dto.setArrivalStatus(PROCESSING_VAL1);
				} else {
					dto.setArrivalStatus(PROCESSING_VAL2);
				}

				dto.setArrivalScheduleDate(arrivalScheduleDTO.getArrivalScheduleDate());
			}

			slipDTO.setItemList(itemList);

//			slipDTO.setArrivalScheduleList(arrivalScheduleList);

			list.add(slipDTO);
		}

		return list;
	}

	/**
	 * 伝票に紐付く入荷予定リストを取得する
	 * @param searchSlipDTO
	 * @return
	 * @throws Exception
	 */
	public List<ExtendArrivalScheduleDTO> getForeignOrderArrivalScheduleList(ForeignSlipSearchDTO searchSlipDTO, long sysForeignSlipId, List<ExtendForeignOrderItemDTO> itemList ) throws Exception {
		//インスタンス生成
		ForeignOrderDAO dao = new ForeignOrderDAO();
		List<ExtendArrivalScheduleDTO> arrivalScheduleList = new ArrayList<ExtendArrivalScheduleDTO>();

		//各商品に対応した入荷予定情報の取得
		for (ExtendForeignOrderItemDTO dto : itemList ) {

			//入荷予定の取得
			ExtendArrivalScheduleDTO arrivalScheduleDTO = dao.getForeignOrderArrivalScheduleList(searchSlipDTO, sysForeignSlipId, dto);

			// 入荷状態の表示変更
			if ((arrivalScheduleDTO.getArrivalNum() < dto.getOrderNum())) {
				arrivalScheduleDTO.setArrivalStatus(PROCESSING_VAL1);
			} else {
				arrivalScheduleDTO.setArrivalStatus(PROCESSING_VAL2);
			}

			arrivalScheduleList.add(arrivalScheduleDTO);
		}
		return arrivalScheduleList;
	}

	/**
	 * 伝票に紐付く商品リストを取得する
	 * @param sysForeignSlipId
	 * @param searchSlipDTO
	 * @return
	 * @throws Exception
	 */
	public List<ExtendForeignOrderItemDTO> getForeignOrderItemList(ForeignSlipSearchDTO searchSlipDTO, long sysForeignSlipId) throws Exception {

		List<ExtendForeignOrderItemDTO> itemList = new ForeignOrderDAO().getForeignItemList(searchSlipDTO , sysForeignSlipId);

//		for (ExtendForeignOrderItemDTO dto : itemList ) {
//
//			// 入荷状態の表示変更
//			if ((dto.getArrivalNum() < dto.getOrderNum())) {
//				dto.setArrivalStatus(PROCESSING_VAL1);
//			} else {
//				dto.setArrivalStatus(PROCESSING_VAL2);
//			}
//
//			itemList.add(dto);
//		}

		return itemList;
	}
	/**
	 * [概要]海外注文伝票詳細を取得する
	 * @param sysForeignSlipId
	 * @return
	 * @throws Exception
	 */
	public ExtendMstSupplierDTO getForeignOrderDetail(long sysSupplierId) throws Exception {

		//インスタンス生成
		ExtendMstSupplierDTO supplierDTO = new ExtendMstSupplierDTO();
		ForeignOrderDAO dao = new ForeignOrderDAO();

		supplierDTO = dao.getDetailSupplierList(sysSupplierId);

		return supplierDTO;
	}

	/**
	 * [概要]子画面、注文プール検索
	 *
	 * @param searchItemDTO
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendForeignOrderItemDTO> getSearchOrderPoolList(SearchItemDTO dto) throws DaoException {

		List<ExtendForeignOrderItemDTO> list = new ForeignOrderDAO().getForeignItem(dto);
		return list;
	}

	/**
	 * [概要]チェックボックスで選択されている伝票にチェックボックスフラグを設定する
	 * @param foreignOrderSlipList
	 */
	public void setFlags(List<ExtendForeignOrderDTO> foreignOrderSlipList) {


		for (ExtendForeignOrderDTO dto : foreignOrderSlipList) {

			if (StringUtils.isNotEmpty(dto.getCheckBoxFlg())) {
				dto.setCheckBoxFlg("1");
			}
		}
	}

	/**
	 * 詳細画面用削除
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public RegistryMessageDTO detailDeleteSlip(ExtendForeignOrderDTO dto, List<ExtendForeignOrderItemDTO> itemList) throws DaoException {

		ForeignOrderDAO dao = new ForeignOrderDAO();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

			// 伝票削除
			int result = dao.deleteSlip(dto.getSysForeignSlipId());

			// 構成商品削除
			result += dao.deleteItem(dto.getSysForeignSlipId());

			//削除件数が伝票と商品件数と合っていなければfalse
//			if (result != itemList.size() + 1) {
//				messageDTO.setSuccess(false);
//				return messageDTO;
//			}

			//ステータスが入荷済みよりも前の状態のものは入荷予定テーブルのデリートフラグを立てる
			if (!dto.getOrderStatus().equals("7")) {
				dao.deleteArrival(dto.getSysForeignSlipId());
			}
		return messageDTO;
	}

	/**
	 * [概要]海外注文伝票を削除する
	 * @param foreignOrderSlipList
	 * @throws DaoException
	 */
	public RegistryMessageDTO lumpDeleteSlip(List<ExtendForeignOrderDTO> foreignOrderSlipList) throws DaoException {

		//削除対象商品数
		int targetCntItem = 0;

		ForeignOrderDAO dao = new ForeignOrderDAO();
		RegistryMessageDTO messageDTO = new RegistryMessageDTO();

		int i = 0;
		for (ExtendForeignOrderDTO dto : foreignOrderSlipList) {

			//チェックされていない伝票を処理対象外とする
			if (!dto.getCheckBoxFlg().equals(CHECK_BOX_FLG_ON)) {
				i++;
				continue;
			}

			//削除結果伝票数
			int resultCntSlip = 0;
			//削除結果商品数
			int resultCntItem = 0;

			//伝票削除
			resultCntSlip += dao.deleteSlip(dto.getSysForeignSlipId());

			//対象商品数取得
			targetCntItem = foreignOrderSlipList.get(i).getItemList().size();

			//商品削除
			resultCntItem += dao.deleteItem(dto.getSysForeignSlipId());


			//ステータスが入荷済みよりも前の状態のものは入荷予定テーブルのデリートフラグを立てる
			if (!dto.getOrderStatus().equals("7")) {
				dao.deleteArrival(dto.getSysForeignSlipId());
			}

			/**削除した伝票が1でない場合、削除対象商品数と実行数が等しくない場合、削除対象入荷予定件数と実行数が等しくない場合
				のいずれかの場合、削除失敗とする */
			if (resultCntSlip != 1 || resultCntItem != targetCntItem) {
				messageDTO.setSuccess(false);
				return messageDTO;
			}
			i++;
		}
		return messageDTO;
	}

	/**
	 * [概要]注文ステータス移動のエラーチェック
	 * @param messageDTO
	 * @param targetList
	 * @param orderStatus
	 * @return
	 */
	public ErrorMessageDTO checkStatusMove(ErrorMessageDTO messageDTO,
			List<ExtendForeignOrderDTO> targetList, String orderStatus) {

		for (ExtendForeignOrderDTO checkDTO : targetList) {
			//移動ステータス：注文書作成「0」
			switch (orderStatus) {
			case WebConst.ORDER_STATUS_CODE_0:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_0)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが注文済以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_1)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;
			//移動ステータス：注文済「1」
			case WebConst.ORDER_STATUS_CODE_1:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_1)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが注文書作成以外且つ納期1以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_0)
								&& !checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_2)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;
			//移動ステータス：納期1「2」
			case WebConst.ORDER_STATUS_CODE_2:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_2)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが注文済以外且つ納期2以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_1)
								&& !checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_3)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;
			//移動ステータス：納期2「3」
			case WebConst.ORDER_STATUS_CODE_3:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_3)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが納期1以外且つ出荷待ち以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_2)
							&& !checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_4)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;
			//移動ステータス：出荷待ち「4」
			case WebConst.ORDER_STATUS_CODE_4:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_4)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが納期2以外且つ一部出荷済以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_3)
							&& !checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_5)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;
			//移動ステータス：一部出荷済「5」
			case WebConst.ORDER_STATUS_CODE_5:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_5)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが出荷待ち以外且つ出荷済以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_4)
							&& !checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_6)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;
			//移動ステータス：出荷済「6」
			case WebConst.ORDER_STATUS_CODE_6:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_6)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが一部出荷済以外且つ入荷済以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_5)
							&& !checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_7)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;

			//移動ステータス：入荷済「6」
			case WebConst.ORDER_STATUS_CODE_7:
				//同ステータスの場合エラー
				if (checkDTO.getOrderStatus().equals(WebConst.ORDER_STATUS_CODE_7)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("同ステータスが選択されています。");
					return messageDTO;
				}
				//ステータスが出荷済以外の場合エラー
				if (!checkDTO.getOrderStatus().equals
						(WebConst.ORDER_STATUS_CODE_6)) {
					messageDTO.setSuccess(false);
					messageDTO.setErrorMessage("ステータスは一つずつしか移動できません。");
					return messageDTO;
				}
				break;
			}
		}
		return messageDTO;
	}

	/**
	 * 注文ステータス情報の更新処理
	 * @param targetList
	 * @param orderStatus
	 * @return
	 * @throws DaoException
	 */
	public int updateForeignListStatus(List<ExtendForeignOrderDTO> targetList,
			String orderStatus) throws DaoException {
		int resultCnt = 0;
		ForeignOrderDAO dao = new ForeignOrderDAO();
		for (ExtendForeignOrderDTO orderDTO : targetList) {
			//選択されているもの以外は弾く
			if (!orderDTO.getCheckBoxFlg().equals(CHECK_BOX_FLG_ON)) {
				continue;
			}

			resultCnt += dao.updateForeignOrderStatus(orderDTO, orderStatus);
			for (ExtendForeignOrderItemDTO itemDTO : orderDTO.getItemList()) {
				dao.updateArrivalScheduleOrderStatus(itemDTO, orderStatus);
			}

		}
		return resultCnt;
	}
//TODO

	/**
	 * 倉庫在庫情報検索
	 * @param itemList
	 * @param sysForeignSlipId
	 * @return
	 * @throws DaoException
	 * nozawa
	 */
	public List<ExtendWarehouseStockDTO> getWarehouseInfo(List<ExtendForeignOrderItemDTO> itemList) throws DaoException {

		List<ExtendWarehouseStockDTO> list = new ArrayList<ExtendWarehouseStockDTO>();
		ForeignOrderDAO dao = new ForeignOrderDAO();
		for (ExtendForeignOrderItemDTO dto: itemList) {

			list.add(dao.getWarehouseStock(dto));
		}
		return list;
	}

	/**
	 * 倉庫情報更新
	 * @param list
	 * @throws DaoException
	 * nozawa
	 */
	public int updateWarehouseStockList(List<ExtendForeignOrderItemDTO> itemList)
			throws DaoException {

		int result = 1;
		for (ExtendForeignOrderItemDTO dto : itemList) {

			if (StringUtils.equals(dto.getArrivalFlag(),"0")) {
				continue;
			}
			// 20140330伊東敦史　下記ソースコメントアウト
			// 意図的に優先度と倉庫IDを消す場合があるかもしれないので
			// //優先度・倉庫ID・在庫数が入っていない場合更新しない
			// if (StringUtils.isEmpty(dto.getPriority()) || dto.getStockNum()
			// == 0
			// dto.getSysWarehouseId == 0) {
			// continue;
			// }
			ForeignOrderDAO dao = new ForeignOrderDAO();
			result = dao.updateWarehouseStock(dto);

			//組立可数プロシージャ化のため処理を凍結 20171107 y_saito
			//セット商品の組立可数・構成部品も含めて総計算
//			setAllAssemblyNum(dto.getSysItemId());

		}
		return result;
	}

	/**
	 * 更新情報を作成する：倉庫情報
	 * @param afterDTO
	 * @param beforeDto
	 * @return
	 * @throws DaoException
	 */
	public int insertWarehouseInfo(List<ExtendForeignOrderItemDTO> list, List<ExtendWarehouseStockDTO> beforeWareHouseDTO, List<ExtendWarehouseStockDTO> afterWareHouseDTO)
			throws DaoException {

		//インスタンス生成
		SequenceDAO seqDao = new SequenceDAO();
		UpdateDataHistoryDAO dao = new UpdateDataHistoryDAO();
		List<UpdateDataHistoryDTO> updHstry = new ArrayList<UpdateDataHistoryDTO>();
		WarehouseDAO warehouseDao = new WarehouseDAO();

		int result = 1;
		int sumStock = 0;

		//伝票に登録された商品の数ループ
		for (int i = 0; i < list.size(); i++) {

			//在庫数更新前と更新後に差があるかの判定、差があれば入荷があった
			//00品番対策に入荷フラグが立っていなければ更新情報を作成しない
			if (beforeWareHouseDTO.get(i).getStockNum() != afterWareHouseDTO.get(i).getStockNum() && StringUtils.equals(list.get(i).getArrivalFlag(), "1")) {

				//00品番対策に、00品番の場合は更新前情報にstockNumを足したものを渡す
				if (StringUtils.equals(list.get(i).getItemCode(), "00")) {

					//更新情報作成
					//渡すのは更新前在庫数、更新後在庫数、倉庫名、システム商品ID
					updHstry.add(registryUpdateData(String.valueOf(beforeWareHouseDTO.get(i).getStockNum() + list.get(i).getStockNum() + sumStock), String.valueOf(beforeWareHouseDTO.get(i).getStockNum() + sumStock),
							warehouseDao.getWarehouse(afterWareHouseDTO.get(i).getSysWarehouseId()).getWarehouseNm() +  "在庫数", list.get(i).getSysItemId()));

					//入荷数を増加させることで00品番が複数登録、入荷対策
					sumStock += list.get(i).getStockNum();
				} else {

					//差があるのであれば更新情報作成
					//渡すのは更新前在庫数、更新後在庫数、倉庫名、システム商品ID
					updHstry.add(registryUpdateData(String.valueOf(afterWareHouseDTO.get(i).getStockNum()), String.valueOf(beforeWareHouseDTO.get(i).getStockNum()),
							warehouseDao.getWarehouse(afterWareHouseDTO.get(i).getSysWarehouseId()).getWarehouseNm() +  "在庫数", list.get(i).getSysItemId()));
				}
			}
		}

		//差分のある情報を判別し、更新メッセージ作成
		for (UpdateDataHistoryDTO updaeDto: updHstry) {

			//システム更新IDの作成
			long sysUpdateDataId = seqDao.getMaxUpdateDataId() + 1;
			//システム更新IDセット
			updaeDto.setSysUpdateDataId(sysUpdateDataId);

			result = dao.updateHistoryInfo(updaeDto);

		}
		return result;
	}

	/**
	 * [概要]更新情報を登録する(セット商品用)
	 * @param form
	 */
	public UpdateDataHistoryDTO registryUpdateData(String afterInfo, String beforeInfo, String updTarget, long sysItemId) {

		UpdateDataHistoryDTO dto = new UpdateDataHistoryDTO();

		//更新情報を格納する変数
		UserInfo userInfo = ActionContext.getLoginUserInfo();

		//日時取得
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		//メッセージ設定
		String updateHistory = userInfo.getFullName();
		if (StringUtils.isBlank(beforeInfo)) {
			updateHistory += " [" + sdf1.format(date) + "]";
			updateHistory += updTarget + "に[" + afterInfo;
			updateHistory += "] を登録しました。";
		} else {
			updateHistory += " [" + sdf1.format(date) + "]";
			updateHistory += updTarget + "を [" + beforeInfo + "]→[" + afterInfo;
			updateHistory += "]に変更しました。";
		}

		//システム商品ID設定
		dto.setSysItemId(sysItemId);
		//更新履歴設定
		dto.setUpdateHistory(updateHistory);
		//更新者ID設定
		dto.setUpdateUserId(userInfo.getUserId());
		//登録者ID設定
		dto.setCreateUserId(userInfo.getUserId());

		return dto;
	}

	/**
	 * 入荷数分入荷処理
	 * @param arrivalScheduleList
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 * nozawa
	 */
	public int updateArrivalScheduleList(List<ExtendForeignOrderItemDTO> itemList ,String orderStatus, String orderDate)
			throws DaoException {

		int result = 1;

		for (ExtendForeignOrderItemDTO dto : itemList) {

			if (dto.getArrivalNum() == 0) {
				continue;
			}

			// 部分入荷の場合
			if (StringUtils.equals(dto.getArrivalFlag(), "1")
					&& dto.getArrivalNum() < dto.getTempArrivalNum()) {

				/*部分入荷の際にdtoのArrivalNumを1度書き換える為、
				 * dto.getArrivalNumの一時格納先として用意し格納*/
				int arrivalNum = dto.getArrivalNum();

				/*元の注文数から入荷後の注文数を差し引いて入荷数を算出したものを格納*/
				dto.setArrivalNum(dto.getTempArrivalNum() - dto.getArrivalNum());

				//入荷フラグを１で登録
				registryArrivalHistory(dto, orderDate);

				/*部分入荷の際に書き換えたArrivalNumを元の値に戻します*/
				dto.setArrivalNum(arrivalNum);
				dto.setArrivalFlag("0");

				// 残り入荷数を更新かける。
				ForeignOrderDAO dao = new ForeignOrderDAO();
				result = dao.updateArrivalNum(dto, orderStatus, orderDate);

				continue;
			}

			// 入荷数分入荷ボタンをおされていないのに入荷数がいじられている場合
			if (StringUtils.equals(dto.getArrivalFlag(), "0")
					&& dto.getArrivalNum() != dto.getTempArrivalNum()) {

				dto.setArrivalNum(dto.getTempArrivalNum());
			}
			//全入荷、入荷日、曖昧入荷日、ステータスの更新
			ForeignOrderDAO dao = new ForeignOrderDAO();
			result = dao.updateArrivalNum(dto, orderStatus, orderDate);
		}
		return result;
	}

	/**
	 * 入荷履歴登録
	 *
	 * 本来入荷予定を登録した後に入荷フラグをたて入荷履歴とするが、 一部入荷があった場合などいきなり入荷履歴に登録する場合に使用する。
	 *
	 * @param dto
	 * @throws DaoException
	 * nozawa
	 */
	private void registryArrivalHistory(ExtendForeignOrderItemDTO dto, String orderDate)
			throws DaoException {

		ExtendForeignOrderItemDTO registryDTO = new ExtendForeignOrderItemDTO();

		if (dto.getSysItemId() == 0) {
			return;
		}

		// 入荷した分を入荷フラグをたてて登録
		registryDTO.setArrivalNum(dto.getArrivalNum());
		//システム商品ID
		registryDTO.setSysItemId(dto.getSysItemId());
		//システム入荷予定ID
		registryDTO.setSysArrivalScheduleId(new SequenceDAO().getMaxSysArrivalScheduleId() + 1);
		//システム海外注文伝票ID
		registryDTO.setSysForeignSlipId(dto.getSysForeignSlipId());
		//注文日
		registryDTO.setItemOrderDate(orderDate);
		//入荷予定日
		registryDTO.setArrivalScheduleDate(dto.getArrivalScheduleDate());
		//曖昧入荷予定日
		registryDTO.setVagueArrivalSchedule(dto.getVagueArrivalSchedule());
		//システム海外注文商品ID
		registryDTO.setSysForeignSlipItemId(dto.getSysForeignSlipItemId());

		ForeignOrderDAO dao = new ForeignOrderDAO();
		dao.registryArrivalHistory(registryDTO);
	}

	/**
	 * // ■入荷予定 新規登録
	 * @param addArrivalScheduleList
	 * @param sysItemId
	 * @throws DaoException
	 * nozawa
	 */
	public int registryArrivalScheduleList(
			List<ExtendForeignOrderItemDTO> addArrivalScheduleList, ExtendForeignOrderDTO slipInfo) throws DaoException {

		int result = 1;
		for (ExtendForeignOrderItemDTO dto : addArrivalScheduleList) {

			// 入荷予定日・入荷数が入っていない場合登録しない
			if (dto.getOrderNum() == 0 || StringUtils.isBlank(dto.getArrivalScheduleDate())) {
				continue;
			}

			//システム商品IDは再度マスタから取りに行く
			if (dto.getSysItemId() == 0) {
				ForeignOrderDAO dao = new ForeignOrderDAO();
				ExtendForeignOrderItemDTO itemCodeDto = dao.ForeignOrderItemValidate(dto.getItemCode());
				dto.setSysItemId(itemCodeDto.getSysItemId());
			}

			//注文日
			dto.setItemOrderDate(slipInfo.getOrderDate());
			//伝票ID
			dto.setSysForeignSlipId(slipInfo.getSysForeignSlipId());
			//システム入荷予定ID
			dto.setSysArrivalScheduleId(new SequenceDAO().getMaxSysArrivalScheduleId() + 1);

			ForeignOrderDAO dao = new ForeignOrderDAO();
			result = dao.registryArrivalSchedule(dto, slipInfo.getOrderStatus());
		}
		return result;
	}

	/**
	 * 総在庫・仮在庫数更新
	 *
	 * @param sysItemId
	 * @throws DaoException
	 */
	public int updateTotalStockNum(List<ExtendForeignOrderItemDTO> itemList) throws DaoException {

		int result = 1;
		for (ExtendForeignOrderItemDTO dto: itemList) {
			new ExcelImportDAO().stockSum(dto.getSysItemId());

			new ForeignOrderDAO().updateTemporaryStockNum(dto.getSysItemId());
		}
		return result;
	}

	/**
	 * 一括支払処理
	 * @param lumpPaymentList
	 * @param errorMessageDTO
	 * @return
	 * @throws DaoException
	 */
	public ErrorMessageDTO lumpPayment(List<ExtendForeignOrderDTO> lumpPaymentList, ErrorMessageDTO errorMessageDTO)
			throws DaoException {

		ForeignOrderDAO dao = new ForeignOrderDAO();


		for (ExtendForeignOrderDTO dto : lumpPaymentList) {

			int result = dao.updatePaymentDate(dto);
			if (result != 1) {
				errorMessageDTO.setSuccess(false);
				errorMessageDTO.setErrorMessage("対象の伝票がありません。大変申し訳ありませんが、管理者に連絡してください");
				return errorMessageDTO;
			}
		}

		return errorMessageDTO;
	}

	/**
	 * 検索条件内のフラグ読み換え
	 * @param dto
	 */
	public void setSearchFlg(ForeignSlipSearchDTO dto) {

		if (StringUtils.isNotEmpty(dto.getDeliveryDateOverFlg1())) {
			dto.setDeliveryDateOverFlg1(StringUtil.switchCheckBox(dto.getDeliveryDateOverFlg1()));
		}

		if (StringUtils.isNotEmpty(dto.getDeliveryDateOverFlg2())) {
			dto.setDeliveryDateOverFlg2(StringUtil.switchCheckBox(dto.getDeliveryDateOverFlg2()));
		}

	}

	/**
	 * 一括入荷予定取得
	 *
	 * 在庫一覧以外から来た場合正しいデータきません。
	 *
	 * @param itemList
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendArrivalScheduleDTO> getLumpArrivalList(
			List<ExtendForeignOrderDTO> slipList) throws DaoException {

		//インスタンス生成
		List<ExtendArrivalScheduleDTO> arrivalScheduleList = new ArrayList<>();
		ExtendArrivalScheduleDTO arrivalScheduleDTO = new ExtendArrivalScheduleDTO();

		ForeignOrderDAO dao = new ForeignOrderDAO();

		//伝票リストを確認するループ文
		for (int i = 0; i < slipList.size(); i++) {

			//伝票リストのi番目を格納
			ExtendForeignOrderDTO slipDTO = slipList.get(i);

			//i番目の中の商品リストを取得
			List<ExtendForeignOrderItemDTO> itemList = slipDTO.getItemList();

			//商品リストを確認するループ文
			for (int j = 0; j < itemList.size(); j++) {

				//商品リストのj番目の商品を取得
				ExtendForeignOrderItemDTO itemDTO = itemList.get(j);

				//既に入荷済だった場合、後の処理を飛ばす
				if(itemDTO.getArrivalStatus().equals("済")) {
					continue;
				}

				//j番目の商品に紐付く入荷予定IDを取得
				arrivalScheduleDTO = dao.getSysArrivalScheduleId(itemDTO);

				//入荷予定が空だった場合、後の処理を飛ばす
				if (arrivalScheduleDTO == null) {
					continue;
				}

				//入荷予定IDを元に入荷予定情報を取得
				arrivalScheduleDTO = dao.getArrivalSchedule(arrivalScheduleDTO);

				//入荷予定が空だった場合、後の処理を飛ばす
				if (arrivalScheduleDTO == null) {
					continue;
				}

				//j番目の商品の倉庫在庫情報を取得
				long sysWarehouseId = dao.getSysWarehouseId(itemDTO.getSysItemId());

				//一括入荷画面で表示に必要な情報をDTOに設定
				arrivalScheduleDTO.setSysWarehouseId(sysWarehouseId);
				arrivalScheduleDTO.setItemCode(itemDTO.getItemCode());
				arrivalScheduleDTO.setItemNm(itemDTO.getItemNm());

				arrivalScheduleDTO.setItemOrderDate(slipDTO.getOrderDate());
				arrivalScheduleDTO.setPoNo(slipDTO.getPoNo());

				//設定したDTOをListに追加
				arrivalScheduleList.add(arrivalScheduleDTO);

			}

		}

		return arrivalScheduleList;
	}

	/**
	 * 一括入荷処理
	 * @param lumpArrivalList
	 * @return
	 * @throws DaoException
	 */
	public ErrorMessageDTO lumpArrival(List<ExtendArrivalScheduleDTO> lumpArrivalList)
			throws DaoException {

		ErrorMessageDTO errorDTO = new ErrorMessageDTO();

		for (ExtendArrivalScheduleDTO dto : lumpArrivalList) {

			int arrivalNum = 0;
			int result = 0;

			arrivalNum = dto.getArrivalNum();

			//入荷数が0だった場合、以降の入荷処理を行わない
			if (arrivalNum == 0) {
				continue;
			}

			// ■入荷処理あとできりわけるかも
			ForeignOrderDAO dao = new ForeignOrderDAO();

			// 入荷予定より、入荷数が少ない場合、部分入荷処理
			if (dto.getArrivalNum() < dto.getArrivalScheduleNum()) {

				//入荷履歴を登録
				registryArrivalHistory(dto);
				// 入荷した分入荷予定数を減らして更新
				dto.setArrivalNum(dto.getArrivalScheduleNum() - arrivalNum);
				dto.setArrivalFlag("0");
				result += dao.updateArrivalNum(dto);

			} else {

				dto.setArrivalFlag("1");
				dto.setArrivalScheduleNum(arrivalNum);
				result += dao.updateArrivalSchedule(dto);
			}

			if (result != 1) {
				errorDTO.setSuccess(false);
				errorDTO.setErrorMessage("対象の入荷予定がありません。大変申し訳ありませんが、管理者に連絡してください");
				return errorDTO;
			}

			// ■在庫増処理あとできりわけるかも
			//倉庫情報のbefore用リスト
			List<ExtendWarehouseStockDTO> targetList = getWarehouseStockList(dto
					.getSysItemId());

			//倉庫情報のafter用リスト
			List<ExtendWarehouseStockDTO> resultList = getWarehouseStockList(dto
					.getSysItemId());

			String registryFlg = "0";
			ExtendWarehouseStockDTO registryWarehouseStockDTO = new ExtendWarehouseStockDTO();
			int targetCnt = 0;
			int resultCnt = 0;

			// 倉庫在庫が一つも無い場合登録フラグを立てる
			if (resultList == null) {
				registryFlg = "1";
			}

			for (int i = 0; i < resultList.size(); i++) {

				ExtendWarehouseStockDTO warehouseStockDTO = resultList.get(i);
				// 既に存在している倉庫在庫の場合
				// 在庫数を増やす
				if (warehouseStockDTO.getSysWarehouseId() == dto
						.getSysWarehouseId()) {

					warehouseStockDTO.setStockNum(warehouseStockDTO
							.getStockNum() + arrivalNum);
					resultCnt += dao.updateWarehouseStock(warehouseStockDTO);
					targetCnt++;
					break;
				}

				// 入荷したい倉庫に倉庫在庫が無い場合登録フラグを立てる
				if (i == resultList.size() - 1) {
					registryFlg = "1";
				}
			}

			if (targetCnt != resultCnt) {
				errorDTO.setSuccess(false);
				errorDTO.setErrorMessage("対象の倉庫在庫がありません");
				return errorDTO;
			}

			// 新規登録の場合
			if (StringUtils.equals(registryFlg, "1")) {

				result = 0;
				registryWarehouseStockDTO.setPriority(getMaxPriority(dto
						.getSysItemId()));
				registryWarehouseStockDTO.setStockNum(arrivalNum);
				registryWarehouseStockDTO.setSysWarehouseId(dto
						.getSysWarehouseId());
				registryWarehouseStockDTO.setSysItemId(dto.getSysItemId());
				result = registryWarehouseStock(registryWarehouseStockDTO);

				if (result != 1) {
					errorDTO.setSuccess(false);
					errorDTO.setErrorMessage("対象の倉庫在庫がありません");
					return errorDTO;
				}
			}

			//総在庫数・仮在庫数更新
			updateTotalStockNum(dto.getSysItemId());

			/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
			//セット商品の組立可数・構成部品も含めて総計算
//			setAllAssemblyNum(dto.getSysItemId());

			//倉庫在庫更新情報作成
			result = this.lumpInsertWarehouseInfo(dto, targetList, resultList);

			if (result != 1) {
				errorDTO.setSuccess(false);
				errorDTO.setErrorMessage("対象の在庫更新情報がありません");
				return errorDTO;
			}
		}

		return errorDTO;
	}

	/**
	 * 更新情報を作成する：倉庫情報(一括入荷用)
	 * @param afterDTO
	 * @param beforeDto
	 * @return
	 * @throws DaoException
	 */
	public int lumpInsertWarehouseInfo(ExtendArrivalScheduleDTO dto, List<ExtendWarehouseStockDTO> targetWareHouseStockList, List<ExtendWarehouseStockDTO> resultWareHouseStockList)
			throws DaoException {

		//インスタンス生成
		SequenceDAO seqDao = new SequenceDAO();
		UpdateDataHistoryDAO dao = new UpdateDataHistoryDAO();
		WarehouseDAO warehouseDao = new WarehouseDAO();

		//在庫を更新した倉庫Idを格納
		long sysWarehouseId = dto.getSysWarehouseId();

		//各倉庫の在庫リストを確認するループ
		int result = 1;
		int i = 0;
		for (ExtendWarehouseStockDTO targetDTO: targetWareHouseStockList) {

			//インスタンス作成
			UpdateDataHistoryDTO updateHistoryDTO = new UpdateDataHistoryDTO();

			//現在確認している倉庫Idと在庫を更新した倉庫Idが一致したら更新情報を作成する
			if (targetDTO.getSysWarehouseId() == sysWarehouseId) {

				//更新情報を作成するメソッドを呼び出す
				updateHistoryDTO = this.registryUpdateData(String.valueOf(resultWareHouseStockList.get(i).getStockNum()), String.valueOf(targetDTO.getStockNum()),
						warehouseDao.getWarehouse(sysWarehouseId).getWarehouseNm() +  "在庫数", dto.getSysItemId());

//				//システム商品IDセット
//				updateHistoryDTO.setSysItemId(dto.getSysItemId());

				//システム更新情報IDの最大値を取得
				long sysUpdateDataId = seqDao.getMaxUpdateDataId() + 1;
				//システム更新情報IDセット
				updateHistoryDTO.setSysUpdateDataId(sysUpdateDataId);

				//更新情報をDBに登録
				result = dao.updateHistoryInfo(updateHistoryDTO);

				//DB登録失敗判定
				if (result != 1) {
					return result;
				}
			}
			i++;
		}

		return result;

	}

	/**
	 * 倉庫在庫情報登録
	 * @param registryWarehouseStockDTO
	 * @throws DaoException
	 */
	private int registryWarehouseStock(
			ExtendWarehouseStockDTO registryWarehouseStockDTO) throws DaoException {

			registryWarehouseStockDTO.setSysWarehouseStockId(new SequenceDAO().getMaxSysWarehouseStockId() + 1);
			return new ForeignOrderDAO().registryWarehouseStock(registryWarehouseStockDTO);

	}

	/**
	 * 入荷履歴登録
	 *
	 * 本来入荷予定を登録した後に入荷フラグをたて入荷履歴とするが、 一部入荷があった場合などいきなり入荷履歴に登録する場合に使用する。
	 *
	 * @param dto
	 * @throws DaoException
	 */
	private void registryArrivalHistory(ExtendArrivalScheduleDTO dto)
			throws DaoException {

		ExtendArrivalScheduleDTO registryDTO = new ExtendArrivalScheduleDTO();

		if (dto.getSysItemId() == 0) {
			return;
		}

		// 入荷した分を入荷フラグをたてて登録
		registryDTO.setSysForeignSlipId(dto.getSysForeignSlipId());
		registryDTO.setSysForeignSlipItemId(dto.getSysForeignSlipItemId());
		registryDTO.setArrivalNum(dto.getArrivalNum());
		registryDTO.setVagueArrivalSchedule(dto.getVagueArrivalSchedule());
		registryDTO.setSysItemId(dto.getSysItemId());
		registryDTO.setItemOrderDate(dto.getItemOrderDate());
		registryDTO.setArrivalScheduleDate(dto.getArrivalScheduleDate());
		registryDTO.setSysArrivalScheduleId(new SequenceDAO().getMaxSysArrivalScheduleId() + 1);
		// dto.setArrivalFlag("1");

		new ForeignOrderDAO().registryArrivalHistory(registryDTO);
	}

	public List<ExtendWarehouseStockDTO> getWarehouseStockList(long sysItemId)
			throws DaoException {

		ForeignOrderDAO dao = new ForeignOrderDAO();

		return dao.getWarehouseStockList(sysItemId);
	}

	private String getMaxPriority(long sysItemId) throws DaoException {

		List<ExtendWarehouseStockDTO> list = new ArrayList<>();
		list = new ForeignOrderDAO().getWarehouseStockList(sysItemId);

		if (list == null) {

			return "1";
		}

		int max = list.get(0).getPriorityNumber();
		for (ExtendWarehouseStockDTO dto : list) {

			if (max < dto.getPriorityNumber()) {
				max = dto.getPriorityNumber();
			}
		}

		return String.valueOf(max + 1);
	}

	/**
	 * 総在庫・仮在庫数更新
	 *
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void updateTotalStockNum(long sysItemId) throws DaoException {

		new ExcelImportDAO().stockSum(sysItemId);

		new ForeignOrderDAO().updateTemporaryStockNum(sysItemId);
	}



	/**
	 * 組立可数プロシージャ化のため処理を凍結 20171107 y_saito
	 * 組立可数を構成商品も含めて計算し、DBの値を更新します。
	 *
	 * @param sysItemId 出庫した商品のID
	 * @throws DaoException
	 */
//	public void setAllAssemblyNum(long sysItemId) throws DaoException {
//
//		/*組立可数を更新したシステムIDを保管する*/
////		List<Long> calcSysItemIdList = new ArrayList<Long>();
//		Map<Long, Long> checkItemId = new HashMap<Long, Long>();
//
//		/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
//		/*自分自身の組立可数の計算*/
////		setAssemblyNum(sysItemId);
////		calcSysItemIdList.add(sysItemId);
//		checkItemId.put(sysItemId, sysItemId);
//
//
//		/*この商品を構成商品として使用しているセット商品を探す。*/
//		List<ExtendSetItemDTO> parentSetItemList = getParentSetItemLeaveOnList(sysItemId);
//
//		//組立可数プロシージャ化のため処理を凍結 20171107 y_saito
//		/*そのセット商品の組立可数を計算する。*/
//		if (parentSetItemList.size() != 0 && parentSetItemList != null) {
//			checkItemId = parentAssemblyNumUpdate(parentSetItemList, checkItemId);
//		}
//
//		/*ひもづく構成部品リストを取得*/
//		List<ExtendSetItemDTO> childSetItemList = new ForeignOrderService().getSetItemLeaveClassFlgOnList(sysItemId);
//
//		/*構成部品の更新*/
//		if (childSetItemList.size() != 0 && childSetItemList != null) {
//			checkItemId = childAssemblyNumUpdate(childSetItemList, checkItemId);
//		}
//
//	}

	/**
	 * 出庫分類フラグが1:構成商品のものを取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getSetItemLeaveClassFlgOnList(long sysItemId)
			throws DaoException {

		return new ForeignOrderDAO().getSetItemLeaveClassFlgOnList(sysItemId);
	}

	/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
	//組立可数計算：廃棄予定
//	public void setAssemblyNum(long sysItemId, int assemblyNum)
//			throws DaoException {
//
//		new ForeignOrderDAO().setAssemblyNum(sysItemId, assemblyNum);
//	}
	/**
	 *組立可数プロシージャ化のため処理を凍結 20171107 y_saito
	 * 組立可数を取得します：廃棄予定
	 *
	 * @param sysItemId
	 * @return assemblyNum
	 * @throws DaoException
	 */
//	public int getAssemblyNum(long sysItemId) throws DaoException {
//
//		List<ExtendSetItemDTO> setItemList = new ForeignOrderService()
//				.getSetItemInfoList(sysItemId);
//
//		ForeignOrderService foreignOrderService = new ForeignOrderService();
//
//		int assemblyNum = 999999999;
//		int divNum = 0;
//
//		for (ExtendSetItemDTO dto : setItemList) {
//			if (dto.getLeaveClassFlg().equals("0")) {
//				int itemNum = foreignOrderService.getTemporaryStockNum(dto.getFormSysItemId());
//				//構成商品の在庫数が0以下の場合、0を返す
//				if(itemNum <= 0){
//					return 0;
//				}
//				divNum = itemNum / dto.getItemNum();
//				if(divNum < assemblyNum){
//					assemblyNum = divNum;
//				}
//
//			} else if (dto.getLeaveClassFlg().equals("1")) {
//				// 1層目の構成商品リストを取得
//				List<ExtendSetItemDTO> secondItemList = new ForeignOrderService()
//						.getSetItemInfoList(dto.getFormSysItemId());
//
//				for (ExtendSetItemDTO secondItem : secondItemList) {
//					if (secondItem.getLeaveClassFlg().equals("0")) {
//						int itemNum = foreignOrderService.getTemporaryStockNum(secondItem.getFormSysItemId());
//
//						if(itemNum <= 0){
//							return 0;
//						}
//
//						divNum = itemNum / (secondItem.getItemNum() * dto.getItemNum());
//
//						if (divNum < assemblyNum) {
//							assemblyNum = divNum;
//						}
//
//					} else if (secondItem.getLeaveClassFlg().equals("1")) {
//
//						// 2層目の構成商品リストを取得
//						List<ExtendSetItemDTO> thirdItemList = new ForeignOrderService()
//								.getSetItemInfoList(secondItem.getFormSysItemId());
//
//						for (ExtendSetItemDTO thirdItem : thirdItemList) {
//							if (thirdItem.getLeaveClassFlg().equals("0")) {
//								int itemNum = foreignOrderService.getTemporaryStockNum(thirdItem.getFormSysItemId());
//								if(itemNum <= 0){
//									return 0;
//								}
//
//								divNum = itemNum / (thirdItem.getItemNum()* secondItem.getItemNum() * dto.getItemNum());
//
//								if (divNum < assemblyNum) {
//									assemblyNum = divNum;
//								}
//
//							} else if (thirdItem.getLeaveClassFlg().equals("1")) {
//
//								// 3層目の構成商品リストを取得
//								List<ExtendSetItemDTO> fourthItemList = new ForeignOrderService()
//										.getSetItemInfoList(thirdItem
//												.getFormSysItemId());
//
//								for (ExtendSetItemDTO fourthItem : fourthItemList) {
//									if (fourthItem.getLeaveClassFlg().equals("0")) {
//										int itemNum = foreignOrderService.getTemporaryStockNum(fourthItem.getFormSysItemId());
//										if(itemNum <= 0){
//											return 0;
//										}
//										divNum = itemNum
//												/ (fourthItem.getItemNum()
//														* thirdItem
//																.getItemNum()
//														* secondItem
//																.getItemNum() * dto
//															.getItemNum());
//
//										if (divNum < assemblyNum) {
//											assemblyNum = divNum;
//										}
//
//								} else if (fourthItem.getLeaveClassFlg()
//											.equals("1")) {
//										// 4層目の構成商品リストを取得
//										List<ExtendSetItemDTO> fifthItemList = new ForeignOrderService()
//												.getSetItemInfoList(fourthItem
//														.getFormSysItemId());
//
//										for (ExtendSetItemDTO fifthItem : fifthItemList) {
//											if (fifthItem.getLeaveClassFlg().equals("0")) {
//												int itemNum = foreignOrderService.getTemporaryStockNum(fifthItem.getFormSysItemId());
//												if (itemNum <= 0) {
//													return 0;
//												}
//												divNum = itemNum
//														/ (fifthItem
//																.getItemNum()
//																* fourthItem
//																		.getItemNum()
//																* thirdItem
//																		.getItemNum()
//																* secondItem
//																		.getItemNum() * dto
//																	.getItemNum());
//												if (divNum < assemblyNum) {
//													assemblyNum = divNum;
//												}
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//
//		//通常商品の場合は、0を返す
//		if (assemblyNum != 999999999) {
//			return assemblyNum;
//		} else {
//			return 0;
//		}
//	}

	/**
	 * 商品の仮在庫を取得するサービスメソッド
	 *
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public int getTemporaryStockNum(long sysItemId) throws DaoException {

		int temporaryStockNum = getTotalWarehouseStockNum(sysItemId);

		List<ExtendKeepDTO> keepList = getKeepNumList(sysItemId);

		for (ExtendKeepDTO dto : keepList) {

			temporaryStockNum -= dto.getKeepNum();
		}

		return temporaryStockNum;
	}

	/**
	 * キープの個数を取得する
	 * @param sysItemId
	 * @return 実行結果
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getKeepNumList(long sysItemId) throws DaoException {

		return new ItemDAO().getKeepNumList(sysItemId);
	}

	/**
	 * 商品の総在庫を取得します
	 *
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public int getTotalWarehouseStockNum(long sysItemId) throws DaoException {

		int totalStock = getWarehouseTotalStockList(sysItemId);

		return totalStock;
	}

	/**
	 * 総在庫数を取得するサービスメソッド
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public int getWarehouseTotalStockList(long sysItemId)
			throws DaoException {
		ExtendWarehouseStockDTO dto = new ExtendWarehouseStockDTO();
		ItemDAO dao = new ItemDAO();
		dto  = dao.getWarehouseTotalStockList(sysItemId);
		return dto.getStockNum();
	}

	/**
	 * セット商品の情報を取得する（組立可数計算用）
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getSetItemInfoList(long sysItemId)
			throws DaoException {

		return new ItemDAO().getSetItemInfoList(sysItemId);
	}

	/**
	 * 出庫分類フラグが1：構成商品から出庫のものを取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	private List<ExtendSetItemDTO> getParentSetItemLeaveOnList(long sysItemId) throws DaoException {

		return new ForeignOrderDAO().getParentSetItemLeaveOnList(sysItemId);
	}

	/**
	 * 組立可数プロシージャ化のため処理を凍結 20171107 y_saito
	 * 構成商品として使用しているセット商品を洗い出し、その組立可数を計算する。
	 * 計算後、さらにセット商品を構成商品として使用している商品を洗い出し、組立可数を計算する。
	 * @param setItemList 構成商品リスト
	 * @throws DaoException
	 */
//	public Map<Long, Long> parentAssemblyNumUpdate(List<ExtendSetItemDTO> setItemList, Map<Long, Long> checkItemId) throws DaoException {
//
//		/*今の階層の構成商品をすべて参照*/
//		for (ExtendSetItemDTO dto : setItemList) {
//
////			//すでに計算済みの商品だったら、ここで前の階層へ戻る
////			if (!checkSeachedItemId(dto.getSysItemId(), calcSysItemIdList)) {
////				continue;
////			}
//			if (checkItemId.containsKey(dto.getSysItemId())) {
//				continue;
//			}
//
//			//計算
//			setAssemblyNum(dto.getSysItemId());
//			checkItemId.put(dto.getSysItemId(), dto.getSysItemId());
////			calcSysItemIdList.add(dto.getSysItemId());
//
//			/*この商品を構成商品として使用しているセット商品を探す。*/
//			/*そのセット商品の組立可数を計算する。(一つ上の階層へ行く 見終わったらこの場所へ戻る)*/
//			List<ExtendSetItemDTO> parentSetItemList = getParentSetItemList(dto.getSysItemId());
//			if (parentSetItemList != null && parentSetItemList.size() != 0) {
//				checkItemId = parentAssemblyNumUpdate(parentSetItemList, checkItemId);
//			}
//
//			/*構成紐付く構成部品の取得(一つ下の階層へ行く 見終わったらこの場所へ戻る)*/
//			List<ExtendSetItemDTO> childSetItemList = new ForeignOrderService().getSetItemLeaveClassFlgOnList(dto.getSysItemId());
//				if (childSetItemList != null && childSetItemList.size() != 0) {
//					checkItemId = childAssemblyNumUpdate(childSetItemList, checkItemId);
//			}
//
//		}
//
//		return checkItemId;
//
//	}

	/**
	 * 組立可数プロシージャ化のため処理を凍結 20171107 y_saito
	 * 構成商品のリストからセット商品であるものを探し、組立可数を更新します。
	 * 構成商品かつセット商品の場合、さらに下層のセット商品を探し出し組立可数を更新する。
	 *
	 * @param setItemList 構成商品リスト
	 * @throws DaoException
	 */
//	public  Map<Long, Long> childAssemblyNumUpdate(List<ExtendSetItemDTO> setItemList,  Map<Long, Long> checkItemId) throws DaoException {
//
//		/*今の階層の構成商品をすべて参照*/
//		for (ExtendSetItemDTO dto : setItemList) {
//
////			//すでに計算済みの商品だったら、ここで前の階層へ戻る
////			if (!checkSeachedItemId(dto.getFormSysItemId(), calcSysItemIdList)) {
////				continue;
////			}
//
//			if (checkItemId.containsKey(dto.getFormSysItemId())) {
//				continue;
//			}
//
//			//計算
//			setAssemblyNum(dto.getFormSysItemId());
////			calcSysItemIdList.add(dto.getFormSysItemId());
//			checkItemId.put(dto.getFormSysItemId(), dto.getFormSysItemId());
//
//			/*この商品を構成商品として使用しているセット商品を探す。*/
//			/*そのセット商品の組立可数を計算する。(一つ上の階層へ行く 見終わったらこの場所へ戻る)*/
//			List<ExtendSetItemDTO> parentSetItemList = getParentSetItemList(dto.getFormSysItemId());
//			if (parentSetItemList.size() != 0 && parentSetItemList != null) {
//				checkItemId = parentAssemblyNumUpdate(parentSetItemList, checkItemId);
//			}
//
//			/*構成紐付く構成部品の取得(一つ下の階層へ行く 見終わったらこの場所へ戻る)*/
//			List<ExtendSetItemDTO> childSetItemList = new ForeignOrderService().getSetItemList(dto.getFormSysItemId());
//			if (childSetItemList.size() != 0 && childSetItemList != null) {
//				checkItemId = childAssemblyNumUpdate(childSetItemList, checkItemId);
//			}
//
//
//		}
//		return checkItemId;
//	}

	public List<ExtendSetItemDTO> getSetItemList(long sysItemId)
			throws DaoException {

		return new ForeignOrderDAO().getSetItemList(sysItemId);
	}

	private List<ExtendSetItemDTO> getParentSetItemList(long sysItemId) throws DaoException {

		return new ForeignOrderDAO().getParentSetItemList(sysItemId);
	}
}