package jp.co.kts.service.mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.DomesticOrderItemDTO;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.DomesticOrderSlipDTO;
import jp.co.kts.app.common.entity.DomesticOrderStockItemDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderItemSearchDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.mst.DomesticOrderDAO;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticOrderService {

	private static final String OPEN_PRICE_FLG_OFF = "0";

	/**
	 * 商品検索
	 *
	 * @param domesticOrderSearchDTO
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendDomesticOrderItemSearchDTO> getSearchDomesticOrder(DomesticExhibitionSearchDTO searchDTO) throws DaoException {

		//検索結果をリストに格納
		StringUtil.switchCheckBox(searchDTO.getSetItemFlg());
		List<ExtendDomesticOrderItemSearchDTO> list = new DomesticOrderDAO().getDomesticOrderSearch(searchDTO);

		return list;
	}


	/**
	 * 商品検索:Ajax
	 *
	 * @param domesticOrderSearchDTO
	 * @return
	 * @throws DaoException
	 */
	public ExtendDomesticOrderItemSearchDTO getFormDomesticOrder(long formSysManagementId, long sysManagementId) throws DaoException {

		//検索結果をリストに格納
		ExtendDomesticOrderItemSearchDTO dto = new DomesticOrderDAO().getSetDomesticOrderSearch(formSysManagementId, sysManagementId);

		return dto;
	}

	/**
	 * PDF出力用伝票検索
	 *
	 * @param domesticOrderSearchDTO
	 * @return
	 * @throws DaoException
	 */
	public ExtendDomesticOrderSlipDTO getSearchDomesticOrderSlip(long sysDomesticSlipId) throws DaoException {

		//検索結果をリストに格納
		ExtendDomesticOrderSlipDTO dto = new DomesticOrderDAO().getExportDomesticOrderSlip(sysDomesticSlipId);

		return dto;
	}

	/**
	 * 登録分商品List表示検索
	 *
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> getSearchDomesticOrderItemList(long sysDomesticSlipId) throws DaoException {

		//検索結果をリストに格納
		List<DomesticOrderListDTO> list = new DomesticOrderDAO().getDomesticOrderItemSearch(sysDomesticSlipId);

		return list;
	}

	/**
	 * PDF出力用商品List表示検索
	 *
	 * @param sysDomesticSlipIdl
	 * @return
	 * @throws DaoException
	 */
	public List<DomesticOrderListDTO> getSearchExtendDomesticOrderItemList(long sysDomesticSlipId) throws DaoException {

		//検索結果をリストに格納
		List<DomesticOrderListDTO> list = new DomesticOrderDAO().getExportDomesticOrderItemSearch(sysDomesticSlipId);

		return list;
	}

	/**
	 * 登録分伝票表示検索
	 *
	 * @param sysDomesticSlipId
	 * @return
	 */
	public DomesticOrderSlipDTO getSearchDomesticOrder(long sysDomesticSlipId) throws DaoException {

		//検索結果をリストに格納
		DomesticOrderSlipDTO dto = new DomesticOrderDAO().getDomesticOrderSlipSearch(sysDomesticSlipId);

		dto.setPrintCheckFlag(StringUtil.switchCheckBox(dto.getPrintCheckFlag()));
		return dto;
	}

	/**
	 * 追加用listセット
	 *
	 * @return
	 */
	public List<DomesticOrderListDTO> initAddDomesticOrderList() {

		List<DomesticOrderListDTO> list = new ArrayList<>();

		for (int i = 0; i < WebConst.ADD_DOMESTIC_ORDER_LIST_LENGTH; i++) {

			list.add(new DomesticOrderListDTO());
		}

		return list;
	}

	/**
	 * 法人情報取得
	 *
	 *
	 * @param corporList
	 * @return
	 * @throws Exception
	 */
	public List<MstCorporationDTO> getCorporationList(List<Long> corporList) throws Exception {

		CorporationDAO dao = new CorporationDAO();
		List<MstCorporationDTO> mstCorporationInfo = new ArrayList<MstCorporationDTO>();

		for (int i = 0; i < 6; i++) {

			mstCorporationInfo.add(dao.getCorporation(corporList.get(i)));
		}
		mstCorporationInfo.get(5).setCorporationNm("eco");

		return mstCorporationInfo;
	}

	/**
	 * 納入先倉庫List情報取得
	 *
	 *
	 * @param corporList
	 * @return
	 * @throws Exception
	 */
	public List<DomesticOrderSlipDTO> getWarehouse() throws Exception {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		return  dao.getWarehouse();
	}

	/**
	 * 国内倉庫List情報取得
	 *
	 *
	 * @param corporList
	 * @return
	 * @throws Exception
	 */
	public List<DomesticOrderSlipDTO> getDomesticWarehouse() throws Exception {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		return  dao.getDomesticWarehouse();
	}

	/**
	 * 納入先倉庫情報取得
	 *
	 *
	 * @param corporList
	 * @return
	 * @throws Exception
	 */
	public DomesticOrderSlipDTO getWarehouse(long sysWarehouseId) throws Exception {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		return  dao.getWarehouse(sysWarehouseId);
	}

	/**
	 * 国内納入先倉庫情報取得
	 *
	 *
	 * @param corporList
	 * @return
	 * @throws Exception
	 */
	public DomesticOrderSlipDTO getDomesticWarehouse(long sysWarehouseId) throws Exception {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		return  dao.getDomesticWarehouse(sysWarehouseId);
	}

	/**
	 * 受注番号の確認
	 *
	 * @param domesticOrderItemList
	 * @return
	 * @return
	 * @throws DaoException
	 */
	public RegistryMessageDTO orderNoCheck(RegistryMessageDTO messageDto, DomesticOrderSlipDTO dto) throws DaoException {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		DomesticOrderSlipDTO orderNo = dao.orderNoCheck(dto.getOrderNo());

		if (orderNo != null) {
			messageDto.getResult().addErrorMessage("LED00138",dto.getOrderNo());
			messageDto.setSuccess(false);
		}
		return messageDto;
	}

	/**
	 * 管理品番の確認
	 *
	 * @param domesticOrderItemList
	 * @return
	 * @return
	 * @throws DaoException
	 */
	public RegistryMessageDTO managementCheck(RegistryMessageDTO messageDto, List<DomesticOrderListDTO> list, List<DomesticOrderListDTO> addList) throws DaoException {

		//------ 存在する管理品番かチェック  --------------------------------------------------------------------------
//		for (DomesticOrderListDTO dto: list) {
//
//			// 管理品番が無いものは確認しない
//			if (StringUtils.isBlank(dto.getManagementCode())) {
//				continue;
//			}
//			DomesticOrderDAO dao = new DomesticOrderDAO();
//			DomesticOrderItemDTO management = dao.managementCheck(dto.getManagementCode());
//
//			if (management == null) {
//				messageDto.getResult().addErrorMessage("LED00130",dto.getManagementCode());
//				messageDto.setSuccess(false);
//			}
//		}

		for (DomesticOrderListDTO dto: addList) {

			// 管理品番が無いものは確認しない
			if (StringUtils.isBlank(dto.getManagementCode())) {
				continue;
			}
			DomesticOrderDAO dao = new DomesticOrderDAO();
			DomesticOrderListDTO management = dao.managementCheck(dto.getManagementCode());

			if (management == null) {
				messageDto.getResult().addErrorMessage("LED00130",dto.getManagementCode());
				messageDto.setSuccess(false);
			}
		}
		//-------- 管理品番が重複しているかチェック  --------------------------------------------------------------------------

		Map<String, String> checkUpdMngCd = new HashMap<String, String>();

		for (DomesticOrderListDTO domesticAddDto : addList) {

			//管理品番が空の場合は処理をスキップ
			if (domesticAddDto.getManagementCode().equals(StringUtils.EMPTY)) {
				continue;
			}
			//構成商品の場合は処理をスキップ
			if (domesticAddDto.getItemType().equals(WebConst.RESULT_ITEM_TYPE_FORM)) {
				continue;
			}

			if (checkUpdMngCd.containsKey(domesticAddDto.getManagementCode())){
				messageDto.getResult().addErrorMessage("LED00127",domesticAddDto.getManagementCode());
				messageDto.setSuccess(false);
			}
			checkUpdMngCd.put( domesticAddDto.getManagementCode(), domesticAddDto.getManagementCode());
		}

		for (DomesticOrderListDTO domesticDto : list) {

			//管理品番が空の場合は処理をスキップ
			if (domesticDto.getManagementCode().equals(StringUtils.EMPTY)) {
				continue;
			}
			//構成商品の場合は処理をスキップ
			if (domesticDto.getItemType().equals(WebConst.RESULT_ITEM_TYPE_FORM)) {
				continue;
			}
			if (checkUpdMngCd.containsKey(domesticDto.getManagementCode())){
				messageDto.getResult().addErrorMessage("LED00127",domesticDto.getManagementCode());
				messageDto.setSuccess(false);
			}

			checkUpdMngCd.put( domesticDto.getManagementCode(), domesticDto.getManagementCode());
		}
		return messageDto;
	}

	/**
	 * 問屋名、定価チェック
	 *
	 * @param messageDto
	 * @param dto
	 * @param addDto
	 * @return
	 * @throws DaoException
	 */
	public RegistryMessageDTO domesticRegistryCheck(RegistryMessageDTO messageDto, List<DomesticOrderListDTO> list, List<DomesticOrderListDTO> addList, DomesticExhibitionSearchDTO openDto) throws DaoException {

		for (DomesticOrderListDTO domesticCheckDto : addList) {

			//出品DBの情報取得
			DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
			DomesticExhibitionDTO exhibitionDto = new DomesticExhibitionDTO();

			//管理品番がなければ検索かけない
			if (StringUtils.equals(domesticCheckDto.getManagementCode(), null) || StringUtils.equals(domesticCheckDto.getManagementCode(), "")) {
				continue;
			}

			exhibitionDto = dao.getManagementInfo(domesticCheckDto.getManagementCode());

			// 管理品番・商品名・注文数が空のものはチェックしない
			if (StringUtils.isBlank(domesticCheckDto.getManagementCode()) || StringUtils.isBlank(domesticCheckDto.getItemNm()) || domesticCheckDto.getOrderNum() == 0) {
				continue;
			}

			//問屋名、定価が入力されていないものはアラート
			if (StringUtils.isBlank(domesticCheckDto.getWholsesalerNm())) {
				messageDto.getResult().addErrorMessage("LED00134", domesticCheckDto.getManagementCode());
				messageDto.setSuccess(false);
			}

			//定価の入力チェック※未入力の場合オープン価格フラグを取得する：1ならOK
			if (messageDto.isSuccess()){
				if (domesticCheckDto.getListPrice() == 0
						&& exhibitionDto.getOpenPriceFlg().equals(OPEN_PRICE_FLG_OFF)) {
					messageDto.getResult().addErrorMessage("LED00134", domesticCheckDto.getManagementCode());
					messageDto.setSuccess(false);
				}
			}
		}


		Map<String, String> checkWholseNm = new HashMap<String, String>();

		//問屋名チェック：登録済みレコード
		for (int i = 0; i < list.size(); i++) {

			//初回のみput
			if (i == 0) {
				checkWholseNm.put(list.get(i).getWholsesalerNm(), list.get(i).getWholsesalerNm());
			}

			if (checkWholseNm.containsKey(list.get(i).getWholsesalerNm())){
				continue;
			}
			//他商品と問屋が違う場合アラート
			messageDto.getResult().addErrorMessage("LED00135",list.get(i).getManagementCode());
			messageDto.setSuccess(false);
		}

		//問屋名チェック：追加レコード
		for (int i = 0; i < addList.size(); i++) {

			//管理品番の無いものは判定しない
			if (addList.get(i).getManagementCode().equals(StringUtils.EMPTY)) {
				continue;
			}

			//登録済みレコードが無い場合put
			if (checkWholseNm.size() == 0) {
				checkWholseNm.put(addList.get(i).getWholsesalerNm(), addList.get(i).getWholsesalerNm());
			}

			if (checkWholseNm.containsKey(addList.get(i).getWholsesalerNm())){
				continue;
			}
			//他商品と問屋が違う場合アラート
			messageDto.getResult().addErrorMessage("LED00135",addList.get(i).getManagementCode());
			messageDto.setSuccess(false);
		}
		return messageDto;
	}

	/**
	 * メーカー名重複チェック
	 *
	 * @param registryDto
	 * @param domesticOrderList
	 * @param domesticOrderItemList
	 * @return
	 */
	public RegistryMessageDTO makerCheck(RegistryMessageDTO messageDto, List<DomesticOrderListDTO> list,
			List<DomesticOrderListDTO> addList) {

		Map<Long, Long> checkMakerNm = new HashMap<Long, Long>();

		//メーカー名チェック：登録済みレコード
		for (int i = 0; i < list.size(); i++) {

			//初回のみput
			if (i == 0) {
				checkMakerNm.put(list.get(i).getMakerId(), list.get(i).getMakerId());
			}

			if (checkMakerNm.containsKey(list.get(i).getMakerId())){
				continue;
			}
			//メーカー名が他商品と違う場合アラート
			messageDto.getResult().addErrorMessage("LED00139", list.get(i).getManagementCode());
			messageDto.setSuccess(false);
		}

		//メーカー名チェック：追加レコード
		for (int i = 0; i < addList.size(); i++) {

			//管理品番の無いものは判定しない
			if (addList.get(i).getManagementCode().equals(StringUtils.EMPTY)) {
				continue;
			}

			//登録済みレコードが無い場合put
			if (checkMakerNm.size() == 0) {
				checkMakerNm.put(addList.get(i).getMakerId(), addList.get(i).getMakerId());
			}

			if (checkMakerNm.containsKey(addList.get(i).getMakerId())){
				continue;
			}
			//メーカー名が他商品と違う場合アラート
			messageDto.getResult().addErrorMessage("LED00139", addList.get(i).getManagementCode());
			messageDto.setSuccess(false);
		}
		return messageDto;
	}

	/**
	 * 注文伝票登録
	 *
	 * @param domesticOrderItemDTO
	 */
	public void newRegistryDomesticOrderSlip(DomesticOrderSlipDTO dto) throws DaoException {

		//システム国内伝票ID付与
		dto.setSysDomesticSlipId(new SequenceDAO().getMaxSysDomesticSlipId() + 1);
		//国内注文書No付与
		dto.setPurchaseOrderNo(new SequenceDAO().getMaxPurchaseOrderNo() + 1);

		DomesticOrderDAO dao = new DomesticOrderDAO();
		dao.registryDomesticSlipDAO(dto);
	}

	/**
	 * 注文商品登録
	 *
	 * @param domesticOrderItemList
	 * @param sysDomesticSlipId
	 * @throws DaoException
	 */
	public void newRegistryDomesticOrderItemList(List<DomesticOrderListDTO> domesticOrderItemList, long sysDomesticSlipId) throws DaoException {

		//セット商品の注文数を格納するMap
	Map<String, String> setOrderNumMap = new HashMap<String, String>();
		Map<String, String> setItemIdMap = new HashMap<String, String>();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		for (DomesticOrderListDTO dto : domesticOrderItemList) {


			if (dto.getItemType().equals("1") || dto.getItemType().equals("2")) {

				if (!dto.getItemType().equals("2")) {
					//構成商品の場合、管理品番・商品名・注文数チェックを行わない
					if (StringUtils.isBlank(dto.getManagementCode()) || StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
						continue;
					}
					setOrderNumMap.put(String.valueOf(dto.getSetSysDomesticItemId()), String.valueOf(dto.getOrderNum()));

					//システム国内商品ID付与
					dto.setSysDomesticItemId(new SequenceDAO().getMaxSysDomesticItemId() + 1);
					setItemIdMap.put(String.valueOf(dto.getSetSysDomesticItemId()), String.valueOf(dto.getSysDomesticItemId()));
					dto.setSetSysDomesticItemId(dto.getSysDomesticItemId());

				} else {
					String setOrderNum = setOrderNumMap.get(String.valueOf(dto.getSetSysDomesticItemId()));
					dto.setOrderNum(dto.getOrderNum()* Long.parseLong(setOrderNum));

					//システム国内商品ID付与
					dto.setSysDomesticItemId(new SequenceDAO().getMaxSysDomesticItemId() + 1);
					String setItemId = setItemIdMap.get(String.valueOf(dto.getSetSysDomesticItemId()));
					dto.setSetSysDomesticItemId(Long.parseLong(setItemId));

				}


				//システム国内伝票ID付与
				dto.setSysDomesticSlipId(sysDomesticSlipId);
				//注文担当設定
				dto.setOrderCharge(userInfo.getFullName());
				//原価確認フラグの設定
				dto.setCostComfFlag("0");

				DomesticOrderDAO dao = new DomesticOrderDAO();
				dao.newRegistryDomesticItem(dto, sysDomesticSlipId);

			} else {

				// 管理品番・商品名・注文数が空のものは登録しない
				if (StringUtils.isBlank(dto.getManagementCode()) || StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
					continue;
				}

				//システム国内商品ID付与
				dto.setSysDomesticItemId(new SequenceDAO().getMaxSysDomesticItemId() + 1);
				//システム国内伝票ID付与
				dto.setSysDomesticSlipId(sysDomesticSlipId);
				//注文担当設定
				dto.setOrderCharge(userInfo.getFullName());
				//原価確認フラグの設定
				dto.setCostComfFlag("0");

				DomesticOrderDAO dao = new DomesticOrderDAO();
				dao.newRegistryDomesticItem(dto, sysDomesticSlipId);
			}

			//システム国内商品ID付与
			dto.setSysDomesticItemId(new SequenceDAO().getMaxSysDomesticItemId() + 1);
			//システム国内伝票ID付与
			dto.setSysDomesticSlipId(sysDomesticSlipId);
			//注文担当設定
			dto.setOrderCharge(userInfo.getFullName());
			//通番の付与
//			dto.setSerealNum(String.valueOf(Long.valueOf(new SequenceDAO().getMaxSerealNum()) + 1));
			//原価確認フラグの設定
			dto.setCostComfFlag("0");

			DomesticOrderDAO dao = new DomesticOrderDAO();
			dao.newRegistryDomesticItem(dto, sysDomesticSlipId);
		}
	}

	/**
	 * 注文伝票更新
	 *
	 * @param domesticOrderSlipDto
	 * @throws DaoException
	 */
	public void updateDomesticOrderSlip(DomesticOrderSlipDTO dto) throws DaoException {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		dao.updateDomesticSlipDAO(dto);
	}

	/**
	 * 印刷時印刷確認フラグ更新
	 *
	 * @param domesticOrderSlipDto
	 * @throws DaoException
	 */
	public void updateDomesticSlipPrintFlg(DomesticOrderSlipDTO dto) throws DaoException {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		dto.setPrintCheckFlag(StringUtil.switchCheckBox(dto.getPrintCheckFlag()));
		dao.updateDomesticPrintFlgDAO(dto);
	}

	/**
	 * 注文商品更新
	 *
	 * @param domesticOrderItemList
	 * @param sysDomesticSlipId
	 * @throws DaoException
	 */
	public void updateDomesticOrderItemList(List<DomesticOrderListDTO> list, long sysDomesticSlipId) throws DaoException {

		//セット商品の注文数を格納するMap
		Map<String, String> setOrderNumMap = new HashMap<String, String>();
		Map<String, String> setItemIdMap = new HashMap<String, String>();
		DomesticExhibitionDAO domesticDao = new DomesticExhibitionDAO();

		for (DomesticOrderListDTO dto : list) {

			if (dto.getItemType().equals("1") || dto.getItemType().equals("2")) {

				if (!dto.getItemType().equals("2")) {
					//構成商品の場合、管理品番・商品名・注文数チェックを行わない
					if (StringUtils.isBlank(dto.getManagementCode()) || StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
						continue;
					}
					setOrderNumMap.put(String.valueOf(dto.getSetSysDomesticItemId()), String.valueOf(dto.getOrderNum()));
					//システム国内商品ID付与
					setItemIdMap.put(String.valueOf(dto.getSetSysDomesticItemId()), String.valueOf(dto.getSysManagementId()));

				} else {
					List<ExtendSetDomesticExhibitionDto> formList = new ArrayList<>();

					//親のシステム管理IDを取得
					String setSysManagementId = setItemIdMap.get(String.valueOf(dto.getSetSysDomesticItemId()));
					//構成商品を取得
					formList = domesticDao.getFormDomesticExhibition( Long.parseLong(setSysManagementId));
					//元注文数を取得し計算する
					for (ExtendSetDomesticExhibitionDto formdto : formList) {
						if (formdto.getFormSysManagementId() == dto.getSysManagementId()) {
							String setOrderNum = setOrderNumMap.get(String.valueOf(dto.getSetSysDomesticItemId()));
							dto.setOrderNum(formdto.getItemNum()* Long.parseLong(setOrderNum));
						}
					}
				}

				//更新処理
				DomesticOrderDAO dao = new DomesticOrderDAO();
				dao.updateDomesticItem(dto, sysDomesticSlipId);

			}

			// 管理品番・商品名・注文数が空のものは更新しない
			if (StringUtils.isBlank(dto.getManagementCode()) || StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
				continue;
			}

			DomesticOrderDAO dao = new DomesticOrderDAO();
			dao.updateDomesticItem(dto, sysDomesticSlipId);
		}
	}

	/**
	 *
	 *
	 * @param domesticOrderItemList
	 * @param sysDomesticSlipId
	 * @throws DaoException
	 */
	public void updateDomesticOrderItem(DomesticOrderListDTO dto, long sysDomesticSlipId) throws DaoException {
		DomesticOrderDAO dao = new DomesticOrderDAO();
		dao.updateDomesticOrderPrintStatus(dto);

	}

	/**
	 * 注文商品登録
	 *
	 * @param domesticOrderItemList
	 * @param sysDomesticSlipId
	 * @throws DaoException
	 */
	public void registryDomesticOrderItem(List<DomesticOrderListDTO> domesticOrderItemList, long sysDomesticSlipId) throws DaoException {

		int serealNum = 1;
		int resultCnt = 0;
		for (DomesticOrderListDTO dto : domesticOrderItemList) {

			// 管理品番・商品名・注文数が空のものは登録しない
			if (StringUtils.isBlank(dto.getManagementCode()) || StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
				continue;
			}

			//システム国内商品ID付与
			dto.setSysDomesticItemId(new SequenceDAO().getMaxSysDomesticItemId() + serealNum++);
			//システム国内伝票ID付与
			dto.setSysDomesticSlipId(sysDomesticSlipId);
			//通番の付与
			dto.setSerealNum(String.valueOf(Long.valueOf(new SequenceDAO().getMaxSerealNum()) + 1));


			DomesticOrderDAO dao = new DomesticOrderDAO();
			resultCnt += dao.registryDomesticItem(dto, sysDomesticSlipId);
		}
	}

	/**
	 * 注文商品登録
	 * Csv用
	 * @param domesticOrderItemList
	 * @param sysDomesticSlipId
	 * @throws DaoException
	 */
	public int registryDomesticOrderItemList(List<DomesticOrderItemDTO> domesticOrderItemList, long sysDomesticSlipId) throws DaoException {

		int serealNum = 1;
		int resultCnt = 0;
		for (DomesticOrderItemDTO dto : domesticOrderItemList) {

			// 管理品番・商品名・注文数が空のものは登録しない
			if (StringUtils.isBlank(dto.getManagementCode()) || StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
				continue;
			}

			//システム国内商品ID付与
			dto.setSysDomesticItemId(new SequenceDAO().getMaxSysDomesticItemId() + serealNum++);
			//システム国内伝票ID付与
			dto.setSysDomesticSlipId(sysDomesticSlipId);
			//商品タイプを0：通常で設定
			dto.setItemType(WebConst.RESULT_ITEM_TYPE_NORMAL);
			//セットシステム国内商品IDを0：非セット/構成商品で登録
			dto.setSetSysDomesticItemId(0);

			DomesticOrderDAO dao = new DomesticOrderDAO();
			resultCnt += dao.registryDomesticItemList(dto, sysDomesticSlipId);
		}
		return resultCnt;
	}

	/**
	 * 削除フラグ
	 *
	 * @param MstItemDTO
	 */
	public void setFlags(List<DomesticOrderListDTO> list) {

		for (DomesticOrderListDTO dto : list) {

			if (StringUtils.isNotEmpty(dto.getDeleteCheckFlg())) {
				dto.setDeleteCheckFlg(StringUtil.switchCheckBox(dto
						.getDeleteCheckFlg()));
			}
		}
	}

	/**
	 * 削除機能
	 *
	 * @param list
	 * @throws DaoException
	 */
	public int deleteDomesticOrderItem(List<DomesticOrderListDTO> list) throws DaoException {

		int resultDeleteCnt = 0;
		for (DomesticOrderListDTO dto: list) {

			if (StringUtils.equals(dto.getDeleteCheckFlg(), "1")) {

				DomesticOrderDAO dao = new DomesticOrderDAO();
				resultDeleteCnt += dao.deleteDomesticOrderItem(dto.getSysDomesticItemId());

				//削除対象がセット商品の場合、構成商品も削除
				if (dto.getItemType().equals(WebConst.RESULT_ITEM_TYPE_SET)) {
					dao.deleteFormDomesticItem(dto.getSysDomesticItemId());
				}
			}
		}
		return resultDeleteCnt;
	}

	/**
	 * 注文伝票登録
	 *
	 * @param domesticOrderItemDTO
	 */
	public ExtendDomesticOrderSlipDTO registryDomesticOrderSlipCsv(ExtendDomesticOrderSlipDTO dto) throws DaoException {

		//システム国内伝票ID付与
		dto.setSysDomesticSlipId(new SequenceDAO().getMaxSysDomesticSlipId() + 1);
		//国内注文書No付与
		dto.setPurchaseOrderNo(new SequenceDAO().getMaxPurchaseOrderNo() + 1);

		DomesticOrderDAO dao = new DomesticOrderDAO();
		dao.registryDomesticSlipDAO(dto);
		return dto;
	}
	
	
	/**
	 * Csv用
	 * @param domesticOrderItemList
	 * @param sysDomesticSlipId
	 * @throws DaoException
	 */
	public int registryDomesticOrderStockScheduleDate(List<DomesticOrderStockItemDTO> domesticOrderItemList) throws DaoException {

		int serealNum = 1;
		int resultCnt = 0;
		for (DomesticOrderStockItemDTO dto : domesticOrderItemList) {
			DomesticOrderDAO dao = new DomesticOrderDAO();
			resultCnt += dao.updateDomesticItemArrival(dto);
		}
		return resultCnt;
	}
	
	public ErrorDTO searchDomesticOrderSlipByOrderNo(ErrorDTO csvErrorDTO, DomesticOrderStockItemDTO dto) throws DaoException {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		List<DomesticOrderStockItemDTO>	returnSlipList = new ArrayList<>(); 
		ErrorMessageDTO messageDTO = new ErrorMessageDTO();
		
		csvErrorDTO.setSuccess(true);
		SearchItemDTO searchDto = new SearchItemDTO();
		searchDto.setOrderNo(dto.getOrderNo());
		List<ExtendMstItemDTO> mst = dao.getSearchDomesticItemList(searchDto);
		if(mst.isEmpty())
		{
			csvErrorDTO.setSuccess(false);
			messageDTO.setErrorMessage("受注番号"+dto.getOrderNo() + "は国内商品に存在しない商品です。");
			csvErrorDTO.getErrorMessageList().add(messageDTO);
		}
		return csvErrorDTO;
	}
}
