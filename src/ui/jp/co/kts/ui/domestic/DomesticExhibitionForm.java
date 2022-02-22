package jp.co.kts.ui.domestic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.DomesticOrderItemDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetDomesticExhibitionDto;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticExhibitionForm extends AppBaseForm {

	/** 出品データベース情報 */
	private List<DomesticExhibitionDTO> domesticExhibitionList = new ArrayList<>();
	/** 追加分出品データベース情報 */
	private List<DomesticExhibitionDTO> addDomesticExhibitionList = new ArrayList<>();
	/** 出品データベース検索DTO*/
	private DomesticExhibitionSearchDTO domesticExhibitionSearchDTO = new DomesticExhibitionSearchDTO();
	/** メーカー名リスト */
	private List<MstMakerDTO> domesticMakerNmList = new ArrayList<>();
	/** 原価反映対象国内注文商品リスト */
	private List<DomesticOrderItemDTO> domesticOrderItemCostList = new ArrayList<DomesticOrderItemDTO>();
	/** 管理ID */
	private long sysManagementId;
	/** 並替え */
	private Map<String, String> itemListSortMap = WebConst.DOMESTIC_EXIHIBITION_SORT_MAP;
	/** 昇順・降順 */
	private Map<String, String> itemListSortOrder = WebConst.DOMESTIC_EXIHIBITION_ORDER;
	/** ソート順：一致・以上・以下 */
	private Map<String, String> numberOrderMap = WebConst.NUMBER_ORDER_MAP;
	/** 最大表示件数 */
	private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;
	/** メッセージ */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();
	/** システムマネージメントIDリスト */
	private List<DomesticExhibitionDTO> sysManagementIdList = new ArrayList<>();
	/** システムマネージメントIDリストサイズ */
	private int sysManagementIdListSize = 0;
	/**  */
	private int pageIdx = 0;
	/** ページ最大表示件数 */
	private int domesticExhibitionListPageMax;
	/** メッセージ有無フラグ */
	private String messageFlg = "0";
	/** 管理品番：出品DB検索用 */
	private String managementCode;
	/** エラーメッセージ格納DTO */
	private ErrorDTO errorDTO = new ErrorDTO();
	/** 国内SET商品格納DTO */
	private DomesticExhibitionDTO setDomesticExhibitionDTO = new DomesticExhibitionDTO();
	/** 国内構成商品格納List */
	private List<ExtendSetDomesticExhibitionDto> formDomesticExhibitionList = new ArrayList<>();
	/** 追加分国内構成商品格納List */
	private List<ExtendSetDomesticExhibitionDto> addformDomesticExhibitionList = new ArrayList<>();



	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
	}

	/**
	 * 出品データベース情報を取得します。
	 * @return 出品データベース情報
	 */
	public List<DomesticExhibitionDTO> getDomesticExhibitionList() {
	    return domesticExhibitionList;
	}

	/**
	 * 出品データベース情報を設定します。
	 * @param domesticExhibitionList 出品データベース情報
	 */
	public void setDomesticExhibitionList(List<DomesticExhibitionDTO> domesticExhibitionList) {
	    this.domesticExhibitionList = domesticExhibitionList;
	}

	/**
	 * 追加分出品データベース情報を取得します。
	 * @return 追加分出品データベース情報
	 */
	public List<DomesticExhibitionDTO> getAddDomesticExhibitionList() {
	    return addDomesticExhibitionList;
	}

	/**
	 * 追加分出品データベース情報を設定します。
	 * @param addDomesticExhibitionList 追加分出品データベース情報
	 */
	public void setAddDomesticExhibitionList(List<DomesticExhibitionDTO> addDomesticExhibitionList) {
	    this.addDomesticExhibitionList = addDomesticExhibitionList;
	}

	/**
	 * 出品データベース検索DTOを取得します。
	 * @return 出品データベース検索DTO
	 */
	public DomesticExhibitionSearchDTO getDomesticExhibitionSearchDTO() {
	    return domesticExhibitionSearchDTO;
	}

	/**
	 * 出品データベース検索DTOを設定します。
	 * @param domesticExhibitionSearchDTO 出品データベース検索DTO
	 */
	public void setDomesticExhibitionSearchDTO(DomesticExhibitionSearchDTO domesticExhibitionSearchDTO) {
	    this.domesticExhibitionSearchDTO = domesticExhibitionSearchDTO;
	}

	/**
	 * メーカーリストを取得します。
	 * @return メーカーリスト
	 */
	public List<MstMakerDTO> getDomesticMakerNmList() {
	    return domesticMakerNmList;
	}

	/**
	 * メーカーリストを設定します。
	 * @param domesticMakerList メーカーリスト
	 */
	public void setDomesticMakerNmList(List<MstMakerDTO> domesticMakerNmList) {
	    this.domesticMakerNmList = domesticMakerNmList;
	}

	/**
	 * 原価反映対象国内注文商品リストを取得します。
	 * @return 原価反映対象国内注文商品リスト
	 */
	public List<DomesticOrderItemDTO> getDomesticOrderItemCostList() {
	    return domesticOrderItemCostList;
	}

	/**
	 * 原価反映対象国内注文商品リストを設定します。
	 * @param domesticOrderItemCostList 原価反映対象国内注文商品リスト
	 */
	public void setDomesticOrderItemCostList(List<DomesticOrderItemDTO> domesticOrderItemCostList) {
	    this.domesticOrderItemCostList = domesticOrderItemCostList;
	}

	/**
	 * 管理IDを取得します。
	 * @return 管理ID
	 */
	public long getSysManagementId() {
	    return sysManagementId;
	}

	/**
	 * 管理IDを設定します。
	 * @param sysManagementId 管理ID
	 */
	public void setSysManagementId(long sysManagementId) {
	    this.sysManagementId = sysManagementId;
	}

	/**
	 * 並替えを取得します。
	 * @return 並替え
	 */
	public Map<String,String> getItemListSortMap() {
	    return itemListSortMap;
	}

	/**
	 * 並替えを設定します。
	 * @param itemListSortMap 並替え
	 */
	public void setItemListSortMap(Map<String,String> itemListSortMap) {
	    this.itemListSortMap = itemListSortMap;
	}

	/**
	 * 昇順・降順を取得します。
	 * @return 昇順・降順
	 */
	public Map<String,String> getItemListSortOrder() {
	    return itemListSortOrder;
	}

	/**
	 * 昇順・降順を設定します。
	 * @param itemListSortOrder 昇順・降順
	 */
	public void setItemListSortOrder(Map<String,String> itemListSortOrder) {
	    this.itemListSortOrder = itemListSortOrder;
	}

	/**
	 * ソート順：一致・以上・以下を取得します。
	 * @return ソート順：一致・以上・以下
	 */
	public Map<String,String> getNumberOrderMap() {
	    return numberOrderMap;
	}

	/**
	 * ソート順：一致・以上・以下を設定します。
	 * @param numberOrderMap ソート順：一致・以上・以下
	 */
	public void setNumberOrderMap(Map<String,String> numberOrderMap) {
	    this.numberOrderMap = numberOrderMap;
	}

	/**
	 * 最大表示件数を取得します。
	 * @return 最大表示件数
	 */
	public Map<String,Integer> getListPageMaxMap() {
	    return listPageMaxMap;
	}

	/**
	 * 最大表示件数を設定します。
	 * @param listPageMaxMap 最大表示件数
	 */
	public void setListPageMaxMap(Map<String,Integer> listPageMaxMap) {
	    this.listPageMaxMap = listPageMaxMap;
	}

	/**
	 * メッセージを取得します。
	 * @return メッセージ
	 */
	public RegistryMessageDTO getRegistryDto() {
	    return registryDto;
	}

	/**
	 * メッセージを設定します。
	 * @param registryDto メッセージ
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
	    this.registryDto = registryDto;
	}

	/**
	 * システムマネージメントIDリストを取得します。
	 * @return システムマネージメントIDリスト
	 */
	public List<DomesticExhibitionDTO> getSysManagementIdList() {
	    return sysManagementIdList;
	}

	/**
	 * システムマネージメントIDリストを設定します。
	 * @param sysManagementIdList システムマネージメントIDリスト
	 */
	public void setSysManagementIdList(List<DomesticExhibitionDTO> sysManagementIdList) {
	    this.sysManagementIdList = sysManagementIdList;
	}

	/**
	 * システムマネージメントIDリストサイズを取得します。
	 * @return システムマネージメントIDリストサイズ
	 */
	public int getSysManagementIdListSize() {
	    return sysManagementIdListSize;
	}

	/**
	 * システムマネージメントIDリストサイズを設定します。
	 * @param sysManagementIdListSize システムマネージメントIDリストサイズ
	 */
	public void setSysManagementIdListSize(int sysManagementIdListSize) {
	    this.sysManagementIdListSize = sysManagementIdListSize;
	}

	/**
	 * pageIdxを取得します。
	 * @return pageIdx
	 */
	public int getPageIdx() {
	    return pageIdx;
	}

	/**
	 * pageIdxを設定します。
	 * @param pageIdx pageIdx
	 */
	public void setPageIdx(int pageIdx) {
	    this.pageIdx = pageIdx;
	}

	/**
	 * ページ最大表示件数を取得します。
	 * @return ページ最大表示件数
	 */
	public int getDomesticExhibitionListPageMax() {
	    return domesticExhibitionListPageMax;
	}

	/**
	 * ページ最大表示件数を設定します。
	 * @param domesticExhibitionListPageMax ページ最大表示件数
	 */
	public void setDomesticExhibitionListPageMax(int domesticExhibitionListPageMax) {
	    this.domesticExhibitionListPageMax = domesticExhibitionListPageMax;
	}

	/**
	 * メッセージ有無フラグを取得します。
	 * @return メッセージ有無フラグ
	 */
	public String getMessageFlg() {
	    return messageFlg;
	}

	/**
	 * メッセージ有無フラグを設定します。
	 * @param messageFlg メッセージ有無フラグ
	 */
	public void setMessageFlg(String messageFlg) {
	    this.messageFlg = messageFlg;
	}

	/**
	 * 管理品番：出品DB検索用を取得します。
	 * @return 管理品番：出品DB検索用
	 */
	public String getManagementCode() {
	    return managementCode;
	}

	/**
	 * 管理品番：出品DB検索用を設定します。
	 * @param managementCode 管理品番：出品DB検索用
	 */
	public void setManagementCode(String managementCode) {
	    this.managementCode = managementCode;
	}

	/**
	 * errorDTOを取得します。
	 * @return errorDTO
	 */
	public ErrorDTO getErrorDTO() {
	    return errorDTO;
	}

	/**
	 * errorDTOを設定します。
	 * @param errorDTO errorDTO
	 */
	public void setErrorDTO(ErrorDTO errorDTO) {
	    this.errorDTO = errorDTO;
	}

	/**
	 * @return setDomesticExhibitionDTO
	 */
	public DomesticExhibitionDTO getSetDomesticExhibitionDTO() {
		return setDomesticExhibitionDTO;
	}

	/**
	 * @param setDomesticExhibitionDTO セットする setDomesticExhibitionDTO
	 */
	public void setSetDomesticExhibitionDTO(DomesticExhibitionDTO setDomesticExhibitionDTO) {
		this.setDomesticExhibitionDTO = setDomesticExhibitionDTO;
	}

	/**
	 * 国内構成商品格納List
	 * @return fromDomesticExhibitionList
	 */
	public List<ExtendSetDomesticExhibitionDto> getFormDomesticExhibitionList() {
		return formDomesticExhibitionList;
	}

	/**
	 * 国内構成商品格納List
	 * @param fromDomesticExhibitionList セットする fromDomesticExhibitionList
	 */
	public void setFormDomesticExhibitionList(List<ExtendSetDomesticExhibitionDto> formDomesticExhibitionList) {
		this.formDomesticExhibitionList = formDomesticExhibitionList;
	}

	/**
	 * 追加分国内構成商品格納List
	 * @return addfromDomesticExhibitionList
	 */
	public List<ExtendSetDomesticExhibitionDto> getAddformDomesticExhibitionList() {
		return addformDomesticExhibitionList;
	}

	/**
	 * 追加分国内構成商品格納List
	 * @param addfromDomesticExhibitionList セットする addfromDomesticExhibitionList
	 */
	public void setAddformDomesticExhibitionList(List<ExtendSetDomesticExhibitionDto> addformDomesticExhibitionList) {
		this.addformDomesticExhibitionList = addformDomesticExhibitionList;
	}

}
