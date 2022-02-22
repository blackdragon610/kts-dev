package jp.co.kts.service.mst;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticExhibitionService {

	static final int RESULT_MANAGEMENT_CNT = 0;


	/**
	 * 追加登録分レコード数作成
	 *
	 * @return
	 */
	public List<DomesticExhibitionDTO> initAddDomesticExhibitionList() {

		List<DomesticExhibitionDTO> list = new ArrayList<>();

		for (int i = 0; i < WebConst.ADD_DOMESTIC_EXHIBITION_LIST_LENGTH; i++) {

			list.add(new DomesticExhibitionDTO());
		}

		return list;
	}


	/**
	 * 出品データベース検索使用
	 *
	 * @param searchDTO
	 * @return
	 * @throws Exception
	 */
	public List<DomesticExhibitionDTO> getDomesticExhibionSearch(List<DomesticExhibitionDTO> domesticIdList,
			int pageIdx,DomesticExhibitionSearchDTO searchDTO)
					throws Exception {

		List<DomesticExhibitionDTO> list = new ArrayList<>();

		if (StringUtils.isEmpty(searchDTO.getListPageMax())) {
			searchDTO.setListPageMax("1");
		}

		//1ページに表示する件数分格納
		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(searchDTO.getListPageMax())
				* pageIdx; i < WebConst.LIST_PAGE_MAX_MAP.get(searchDTO
				.getListPageMax()) * (pageIdx + 1)
				&& i < domesticIdList.size(); i++) {

			DomesticExhibitionSearchDTO searchItemDTO = new DomesticExhibitionSearchDTO();
			searchItemDTO.setSysManagementId(domesticIdList.get(i).getSysManagementId());
			//検索実行
			DomesticExhibitionDTO itemDTO = new DomesticExhibitionDAO().getDomesticExhibitionIdSearch(searchItemDTO);
			itemDTO.setOpenPriceFlg(StringUtil.switchCheckBox(itemDTO.getOpenPriceFlg()));

			list.add(itemDTO);
		}



		return list;
	}

	/**
	 * 検索対象件数取得用
	 *
	 * @param searchDTO
	 * @return
	 * @throws Exception
	 */
	public List<DomesticExhibitionDTO> getDomesticExhibionIdList(DomesticExhibitionSearchDTO searchDTO) throws Exception {

		List<DomesticExhibitionDTO> list = new DomesticExhibitionDAO().getDomesticExhibitionSearch(searchDTO);

		return list;
	}

	/**
	 * 検索対象件数取得:国内注文管理一覧遷移用
	 *
	 * @param searchDTO
	 * @return
	 * @throws Exception
	 */
	public DomesticExhibitionDTO getDomesticExhibionIdItem(DomesticExhibitionSearchDTO searchDTO) throws Exception {

		DomesticExhibitionDTO dto = new DomesticExhibitionDAO().getDomesticExhibitionItemSearch(searchDTO);

		return dto;
	}

	/**
	 * 更新機能
	 *
	 * @param list
	 * @throws DaoException
	 */
	public int updateDomesticExhibitionList(List<DomesticExhibitionDTO> list) throws DaoException {
		int resultCnt = 0;
		DomesticExhibitionDAO domesticExhibitDao = new DomesticExhibitionDAO();
		// 登録済み通貨情報を全て更新
		for (DomesticExhibitionDTO dto : list) {
			if (StringUtils.equals(dto.getOpenPriceFlg(), "on")) {
				dto.setListPrice(0);
				dto.setItemRateOver(0);
			}
			resultCnt += domesticExhibitDao.updateDomesticExhibition(dto);


		}
		return resultCnt;
	}

	/**
	 * 登録機能
	 *
	 * @param addlist
	 * @throws DaoException
	 */
	public int registryDomesticExhibitionList(List<DomesticExhibitionDTO> addlist) throws DaoException {
		int resultCnt = 0;
		for (DomesticExhibitionDTO dto : addlist) {

			//管理品番が無い場合は登録しない
			if (StringUtils.isBlank(dto.getManagementCode())) {
				continue;
			}

			//登録済みの管理IDの最大値を調べる
			dto.setSysManagementId(new SequenceDAO().getSysManagementId() + 1);
			new DomesticExhibitionDAO().registryDomesticExhibition(dto);
			resultCnt++;
		}
		return resultCnt;
	}

	/**
	 * 国内注文商品原価反映処理
	 *
	 * @param list
	 * @throws DaoException
	 */
	public RegistryMessageDTO updateDomesticOrderItemCost(RegistryMessageDTO messageDto, DomesticExhibitionDTO list) throws DaoException {

		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
		DomesticExhibitionDAO domesticExhibitDao = new DomesticExhibitionDAO();
		List<DomesticOrderListDTO> resultDto = new ArrayList<DomesticOrderListDTO>();
		List<ExtendSalesSlipDTO> resultsalesDto = new ArrayList<ExtendSalesSlipDTO>();

		try {
			//同一管理品番の商品の情報を取得する
			resultDto = dao.getDomesticOrderItemTargetSearch(list);

			if (resultDto != null) {
				for (DomesticOrderListDTO targetDto : resultDto) {
					//国内注文商品を更新
					domesticExhibitDao.updateDomesticOrderItemCost(targetDto, list);

					//同一受注番号の伝票情報を取得する
					resultsalesDto = domesticExhibitDao.getSalesItemTargetSearch(targetDto);

					if (resultsalesDto != null) {
						for (ExtendSalesSlipDTO targetSalesDto: resultsalesDto) {
							ExtendSalesItemDTO itemDto = new ExtendSalesItemDTO();
							MstCorporationDTO corpDto = new MstCorporationDTO();
							CorporationDAO corpDao = new CorporationDAO();
							corpDto = corpDao.getCorporation(targetSalesDto.getSysCorporationId());
							//法人掛率と商品掛率を合算
							BigDecimal sumRate = corpDto.getCorporationRateOver().add(BigDecimal.valueOf(list.getItemRateOver()));

							//Kind原価
							String kindCost = String.valueOf(list.getPurchasingCost());
							//定価
							String listPrice = String.valueOf(list.getListPrice());
							//商品掛率
							BigDecimal itemRateOver = BigDecimal.valueOf(list.getItemRateOver());
							sumRate.add(itemRateOver);
		 					int cost = sumRate.multiply(BigDecimal.valueOf(list.getListPrice())).divide(BigDecimal.valueOf(100)).intValue();

		 					itemDto.setKindCost(Integer.valueOf(kindCost));
		 					itemDto.setListPrice(Integer.valueOf(listPrice));
		 					itemDto.setItemRateOver(itemRateOver);
		 					itemDto.setCost(cost);


							domesticExhibitDao.updateSalesItemTargetCost(targetSalesDto, list, itemDto);
						}
					}
				}
			}
		} catch(Exception e) {
			messageDto.setSuccess(false);
			return messageDto;
		}
		messageDto.setSuccess(true);
		return messageDto;
	}

	/**
	 * 削除フラグ
	 *
	 * @param MstItemDTO
	 */
	public void setFlags(List<DomesticExhibitionDTO> list) {

		for (DomesticExhibitionDTO dto : list) {

			if (StringUtils.isNotEmpty(dto.getDeleteCheckFlg())) {
				dto.setDeleteCheckFlg(StringUtil.switchCheckBox(dto
						.getDeleteCheckFlg()));
			}
		}
	}

	/**
	 * 追加削除フラグ
	 *
	 * @param MstItemDTO
	 * @return
	 */
	public List<DomesticExhibitionDTO> setAddFlags(List<DomesticExhibitionDTO> list) {

		for (DomesticExhibitionDTO dto : list) {

			if (StringUtils.isNotEmpty(dto.getRemoveCheckFlg())) {

				dto.setRemoveCheckFlg(StringUtils.EMPTY);
				dto.setManagementCode(StringUtils.EMPTY);
				dto.setMakerNm(StringUtils.EMPTY);
				dto.setMakerNmPhonetic(StringUtils.EMPTY);
				dto.setMakerCode(StringUtils.EMPTY);
				dto.setItemNm(StringUtils.EMPTY);
				dto.setWholsesalerNm(StringUtils.EMPTY);
				dto.setListPrice(0);
				dto.setItemRateOver(0);
				dto.setPostage(0);
				dto.setPurchasingCost(0);
			}
		}
		return list;
	}

	/**
	 * 削除機能
	 *
	 * @param list
	 * @throws DaoException
	 */
	public RegistryMessageDTO deleteDomesticExhibition(RegistryMessageDTO messageDto, List<DomesticExhibitionDTO> list) throws DaoException {

		//インスタンス生成
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();

		int resultDeleteCnt = 0;
		for (DomesticExhibitionDTO dto: list) {
			//注文書で既に使用されている品番のカウント
			int orderImteCnt = 0;

			if (StringUtils.equals(dto.getDeleteCheckFlg(), "1")) {
				orderImteCnt = dao.getDomesticOrderItemCnt(dto.getManagementCode());
				if (orderImteCnt > 0) {
					ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
					errorMessageDTO.setErrorMessage("管理品番:" + dto.getManagementCode() + " は国内注文伝票で使用されています。");
					messageDto.getErrorMessageList().add(errorMessageDTO);
					continue;
				}
				resultDeleteCnt += dao.deleteDomesticExhibition(dto.getSysManagementId());
			}
		}

		messageDto.setResultCnt(resultDeleteCnt);

		return messageDto;
	}


	/**
	 * システム管理Idのリストを取得するサービス
	 * @param managementCodesSet
	 * @return sysManagementId
	 * @throws DaoException
	 */
	public List<DomesticExhibitionDTO> getDomesticExhibitionDTOList(Set<String> managementCodesSet) throws DaoException {

		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
		return dao.getManagementInfoList(managementCodesSet);
	}


	/**
	 * 登録・更新時に管理品番の重複チェックを行う
	 * @param messageDto
	 * @param dto
	 * @param adddto
	 * @return
	 * @throws Exception
	 */
	public RegistryMessageDTO checkManagementCode(RegistryMessageDTO messageDto, List<DomesticExhibitionDTO> dto,
			List<DomesticExhibitionDTO> addDto) throws Exception{
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();
		Map<String, String> checkUpdMngCd = new HashMap<String, String>();

		//管理品番重複チェック：登録済みレコード
		for (DomesticExhibitionDTO domesticDto : dto) {
			if (checkUpdMngCd.containsKey(domesticDto.getManagementCode())){
				messageDto.getResult().addErrorMessage("LED00127",domesticDto.getManagementCode());
				messageDto.setSuccess(false);
			}
			checkUpdMngCd.put( domesticDto.getManagementCode(), domesticDto.getManagementCode());
		}
		//管理品番重複チェック：追加レコード
		for (DomesticExhibitionDTO domesticAddDto : addDto) {
			if (domesticAddDto.getManagementCode().equals(StringUtils.EMPTY)) {
				continue;
			}
			if (checkUpdMngCd.containsKey(domesticAddDto.getManagementCode())){
				messageDto.getResult().addErrorMessage("LED00127",domesticAddDto.getManagementCode());
				messageDto.setSuccess(false);
			}
			checkUpdMngCd.put( domesticAddDto.getManagementCode(), domesticAddDto.getManagementCode());

			if (dao.getManagementCodeCnt(domesticAddDto) > RESULT_MANAGEMENT_CNT) {
				messageDto.getResult().addErrorMessage("LED00128",domesticAddDto.getManagementCode());
				messageDto.setSuccess(false);
			}


		}


		return messageDto;
	}

	/**
	 * 検索用フラグ読み換え
	 *
	 * @param dto
	 */
	public void setFlagsSwutch(DomesticExhibitionSearchDTO dto) {
		//セット商品フラグ読み替え
		String dd = dto.getSetItemFlg();
		if (StringUtils.isNotEmpty(dto.getSetItemFlg())) {
			dto.setSetItemFlg(StringUtil.switchCheckBox(dto.getSetItemFlg()));
		}
	}

	/**
	 * 国内構成商品追加登録分レコード数作成
	 *
	 * @return
	 */
	public List<ExtendSetDomesticExhibitionDto> initAddFromDomesticExhibitionList() {

		List<ExtendSetDomesticExhibitionDto> list = new ArrayList<>();

		for (int i = 0; i < WebConst.ADD_DOMESTIC_EXHIBITION_LIST_LENGTH; i++) {

			list.add(new ExtendSetDomesticExhibitionDto());
		}

		return list;
	}

	/**
	 * 国内セット商品登録時の管理品番重複チェック
	 * @param setDto
	 * @param messageDto
	 * @return
	 * @throws Exception
	 */
	public RegistryMessageDTO checkSetManagementCode(DomesticExhibitionDTO setDto, RegistryMessageDTO messageDto) throws Exception{

		//インスタンス生成
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();

		if (dao.getManagementCodeCnt(setDto) > RESULT_MANAGEMENT_CNT) {
			messageDto.getResult().addErrorMessage("LED00128",setDto.getManagementCode());
			messageDto.setSuccess(false);
		}

		return messageDto;
	}

	/**
	 * 国内セット商品登録サービス：親商品
	 * @param setDto
	 * @return
	 * @throws Exception
	 */
	public RegistryMessageDTO registSetDomesticExhibition(DomesticExhibitionDTO setDto, RegistryMessageDTO messageDto) throws Exception{

		int resultCnt = 0;

		//登録済みの管理IDの最大値を調べる
		setDto.setSysManagementId(new SequenceDAO().getSysManagementId() + 1);
		resultCnt = new DomesticExhibitionDAO().registSetDomesticExhibition(setDto);
		messageDto.setResultCnt(resultCnt);
		messageDto.setSysManagementId(setDto.getSysManagementId());
		return messageDto;
	}

	/**
	 * 国内セット商品登録サービス：構成商品
	 * @param dtoList
	 * @return
	 * @throws Exception
	 */
	public int registFromDomesticExhibition(List<ExtendSetDomesticExhibitionDto> dtoList, DomesticExhibitionDTO setDto) throws Exception{

		int resultCnt = 0;

		for (ExtendSetDomesticExhibitionDto dto : dtoList) {

			//管理品番が無い場合は登録しない
			if (StringUtils.isBlank(dto.getManagementCode())) {
				continue;
			}

			//登録済みの管理IDの最大値を調べる
			dto.setSysSetManagementId(new SequenceDAO().getSysSetManagementId() + 1);
			new DomesticExhibitionDAO().registFromDomesticExhibition(dto, setDto);
			resultCnt++;
		}

		return resultCnt;

	}

	/**
	 * 国内セット商品更新サービス：親商品
	 * @param setDto
	 * @return
	 * @throws Exception
	 */
	public int updateSetDomesticExhibition(DomesticExhibitionDTO setDto) throws Exception{

		int resultCnt = 0;
		DomesticExhibitionDAO domesticExhibitDao = new DomesticExhibitionDAO();

		resultCnt = domesticExhibitDao.updateDomesticExhibition(setDto);

		return resultCnt;
	}

	/**
	 * 国内セット商品更新サービス：構成商品
	 * @param dtoList
	 * @return
	 * @throws Exception
	 */
	public int updateFromDomesticExhibition(List<ExtendSetDomesticExhibitionDto> dtoList, DomesticExhibitionDTO setDto) throws Exception{

		int resultCnt = 0;

		for (ExtendSetDomesticExhibitionDto dto : dtoList) {

			//管理品番が無い場合は登録しない
			if (StringUtils.isBlank(dto.getManagementCode())) {
				continue;
			}
			new DomesticExhibitionDAO().updateFromDomesticExhibition(dto, setDto);
			resultCnt++;
		}

		return resultCnt;

	}

	/**
	 * 削除機能：構成商品削除
	 *
	 * @param list
	 * @throws DaoException
	 */
	public RegistryMessageDTO deleteSetDomesticExhibition(RegistryMessageDTO messageDto, List<ExtendSetDomesticExhibitionDto> list) throws DaoException {

		//インスタンス生成
		DomesticExhibitionDAO dao = new DomesticExhibitionDAO();

		int resultDeleteCnt = 0;
		for (ExtendSetDomesticExhibitionDto dto: list) {
			//注文書で既に使用されている品番のカウント
//			int orderImteCnt = 0;

			if (StringUtils.equals(dto.getDeleteCheckFlg(), "on")) {
				//注文書に登録されているものをカウントする　TODO 構成商品には不要？
				//orderImteCnt = dao.getDomesticOrderItemCnt(dto.getManagementCode());
				//if (orderImteCnt > 0) {
				//	ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
				//	errorMessageDTO.setErrorMessage("管理品番:" + dto.getManagementCode() + " は国内注文伝票で使用されています。");
				//	messageDto.getErrorMessageList().add(errorMessageDTO);
				//	continue;
				//}
				resultDeleteCnt += dao.deleteSetDomesticExhibition(dto.getSysSetManagementId());
			}
		}

		messageDto.setResultCnt(resultDeleteCnt);

		return messageDto;
	}
}
