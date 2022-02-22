package jp.co.kts.ui.domestic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.common.entity.DomesticOrderItemDTO;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.DomesticOrderSlipDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderItemSearchDTO;
import jp.co.kts.app.extendCommon.entity.ExtendDomesticOrderSlipDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticExhibitionSearchDTO;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticOrderForm extends AppBaseForm {

	/** 出品データベース情報 */
	private DomesticExhibitionDTO domesticExhibitionDto = new DomesticExhibitionDTO();
	private List<DomesticExhibitionDTO> domesticExhibitionList = new ArrayList<>();
	/** 出品データベース検索DTO*/
	private DomesticExhibitionSearchDTO domesticExhibitionSearchDTO = new DomesticExhibitionSearchDTO();
	private List<ExtendDomesticOrderItemSearchDTO> extendDomesticOrderItemSearchList = new ArrayList<>();

	/** 国内注文商品情報 */
	private DomesticOrderItemDTO domesticOrderItemDto = new DomesticOrderItemDTO();
	private List<DomesticOrderItemDTO> domesticOrderItemList = new ArrayList<>();
	private List<DomesticOrderListDTO> domesticOrderList = new ArrayList<>();
	private List<DomesticOrderListDTO> addDomesticOrderItemList = new ArrayList<>();

	/** 国内注文伝票 */
	private DomesticOrderSlipDTO domesticOrderSlipDto = new DomesticOrderSlipDTO();
	private List<DomesticOrderSlipDTO> domesticOrderSlipList = new ArrayList<>();
	private ExtendDomesticOrderSlipDTO extendDomesticOrderSlipDto = new ExtendDomesticOrderSlipDTO();
	private List<ExtendDomesticOrderSlipDTO> extendDomesticOrderSlipList = new ArrayList<>();
	/** システム伝票ID */
	private long sysDomesticSlipId;
	/** システム倉庫ID */
	private String sysWarehouseId;

	/** 会社一覧 */
	private List<MstCorporationDTO> corporationListDto = new ArrayList<>();
	private long sysCorporationId;

	/** メーカー名リスト */
	private List<MstMakerDTO> domesticMakerNmList = new ArrayList<>();
	private String sysMakerId;
	private String makerNm;
	private String makerNmKana;

	/** メッセージ用 */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();

	/** ユーザーDTO */
	private MstUserDTO userDto = new MstUserDTO();

	/** モール */
	private String mall;

	/** ユーザー情報 */
	private long userInfo;

	/** 担当者番号 */
	private String responsibleNo;

	/** 商品名 */
	private String itemNm;

	/**	検索結果数 */
	private int itemListSize = 0;

	/** オープン価格フラグ */
	private String openPriceFlg;

	/** メッセージ有無フラグ */
	private String messageFlg = "0";

	/** システム伝票ID：文字列用*/
	private String strSysDomesticSlipId;

	/** 印刷確認フラグ */
	private String printCheckFlag;

	/** システムセット管理ID **/
	private long sysManagementId;

	/**
	 * ---------------------------------------getterのみ---------------------------------------------------------
	 */
		/** 並替え */
		private Map<String, String> itemListSortMap = WebConst.DOMESTIC_EXIHIBITION_SORT_MAP;
		/** 昇順・降順 */
		private Map<String, String> itemListSortOrder = WebConst.DOMESTIC_EXIHIBITION_ORDER;
		/** 最大表示件数 */
		private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;
		/** チェック */
		private Map<String, String> domesticMap = WebConst.DOMESTIC_EXIHIBITION_SORT_LIST_MAP;
		/** モール（KTS） */
		private Map<String, String> domesticMallKts = WebConst.DOMESTIC_MALL_KTS_MAP;
		/** モール（車楽院） */
		private Map<String, String> domesticMallSharakuin = WebConst.DOMESTIC_MALL_MAP_SHARAKUIN;
		/** モール（t-four） */
		private Map<String, String> domesticMallTFour = WebConst.DOMESTIC_MALL_MAP_T_FOUR;
		/** モール（ラルグス） */
		private Map<String, String> domesticMallRarugusu = WebConst.DOMESTIC_MALL_MAP_RARUGUSU;
		/** モール（BCR） */
		private Map<String, String> domesticMallBcr = WebConst.DOMESTIC_MALL_MAP_BCR;
		/** モール（eco） */
		private Map<String, String> domesticMallCyberEco = WebConst.DOMESTIC_MALL_MAP_CYBER_ECO;
		/** モール（店舗） */
		private Map<String, String> domesticMallShop = WebConst.DOMESTIC_MALL_MAP_SHOP;

		@Override
		protected void doReset(AppActionMapping appMapping,
				HttpServletRequest request) {
			// TODO 自動生成されたメソッド・スタブ
		}


		public DomesticExhibitionDTO getDomesticExhibitionDto() {
			return domesticExhibitionDto;
		}

		public void setDomesticExhibitionDto(DomesticExhibitionDTO domesticExhibitionDto) {
			this.domesticExhibitionDto = domesticExhibitionDto;
		}



	/**
	 *
	 * @return
	 */
	public List<DomesticExhibitionDTO> getDomesticExhibitionList() {
			return domesticExhibitionList;
	}

	public void setDomesticExhibitionList(List<DomesticExhibitionDTO> domesticExhibitionList) {
		this.domesticExhibitionList = domesticExhibitionList;
	}


	public DomesticExhibitionSearchDTO getDomesticExhibitionSearchDTO() {
		return domesticExhibitionSearchDTO;
	}

	public void setDomesticExhibitionSearchDTO(
			DomesticExhibitionSearchDTO domesticExhibitionSearchDTO) {
		this.domesticExhibitionSearchDTO = domesticExhibitionSearchDTO;
	}

	/**
	 * システム伝票ID：文字列用を取得します。
	 * @return システム伝票ID：文字列用
	 */
	public String getStrSysDomesticSlipId() {
	    return strSysDomesticSlipId;
	}


	/**
	 * システム伝票ID：文字列用を設定します。
	 * @param strSysDomesticSlipId システム伝票ID：文字列用
	 */
	public void setStrSysDomesticSlipId(String strSysDomesticSlipId) {
	    this.strSysDomesticSlipId = strSysDomesticSlipId;
	}


	/**
	 * 印刷確認フラグを取得します。
	 * @return 印刷確認フラグ
	 */
	public String getPrintCheckFlag() {
	    return printCheckFlag;
	}


	/**
	 * 印刷確認フラグを設定します。
	 * @param printCheckFlag 印刷確認フラグ
	 */
	public void setPrintCheckFlag(String printCheckFlag) {
	    this.printCheckFlag = printCheckFlag;
	}


	public Map<String, String> getItemListSortMap() {
		return itemListSortMap;
	}


	public Map<String, String> getItemListSortOrder() {
		return itemListSortOrder;
	}

	public Map<String, Integer> getListPageMaxMap() {
		return listPageMaxMap;
	}

	public Map<String, String> getDomesticMap() {
		return domesticMap;
	}

	public void setDomesticMap(Map<String, String> domesticMap) {
		this.domesticMap = domesticMap;
	}

	public String getSysWarehouseId() {
		return sysWarehouseId;
	}

	public void setSysWarehouseId(String sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}

	public List<MstCorporationDTO> getCorporationListDTO() {
		return corporationListDto;
	}

	public void setCorporationListDTO(List<MstCorporationDTO> corporationListDto) {
		this.corporationListDto = corporationListDto;
	}

	public long getSysCorporationId() {
		return sysCorporationId;
	}

	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}

	public DomesticOrderItemDTO getDomesticOrderItemDto() {
		return domesticOrderItemDto;
	}

	public void setDomesticOrderItemDto(DomesticOrderItemDTO domesticOrderItemDto) {
		this.domesticOrderItemDto = domesticOrderItemDto;
	}

	public String getMall() {
		return mall;
	}

	public void setMall(String mall) {
		this.mall = mall;
	}


	public long getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(long userInfo) {
		this.userInfo = userInfo;
	}

	public MstUserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(MstUserDTO userDto) {
		this.userDto = userDto;
	}


	public Map<String, String> getDomesticMallKts() {
		return domesticMallKts;
	}

	public Map<String, String> getDomesticMallSharakuin() {
		return domesticMallSharakuin;
	}

	/**
	 * @return domesticMallTFour
	 */
	public Map<String, String> getDomesticMallTFour() {
		return domesticMallTFour;
	}

	public Map<String, String> getDomesticMallRarugusu() {
		return domesticMallRarugusu;
	}

	public Map<String, String> getDomesticMallBcr() {
		return domesticMallBcr;
	}

	/**
	 * @return domesticMallCyberEco
	 */
	public Map<String, String> getDomesticMallCyberEco() {
		return domesticMallCyberEco;
	}

	public Map<String, String> getDomesticMallShop() {
		return domesticMallShop;
	}

	public String getResponsibleNo() {
		return responsibleNo;
	}

	public void setResponsibleNo(String responsibleNo) {
		this.responsibleNo = responsibleNo;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public List<MstMakerDTO> getDomesticMakerNmList() {
		return domesticMakerNmList;
	}

	public void setDomesticMakerNmList(List<MstMakerDTO> domesticMakerNmList) {
		this.domesticMakerNmList = domesticMakerNmList;
	}

	public String getMakerNm() {
		return makerNm;
	}

	public void setMakerNm(String makerNm) {
		this.makerNm = makerNm;
	}

	public String getMakerNmKana() {
		return makerNmKana;
	}

	public void setMakerNmKana(String makerNmKana) {
		this.makerNmKana = makerNmKana;
	}

	public String getSysMakerId() {
		return sysMakerId;
	}

	public void setSysMakerId(String sysMakerId) {
		this.sysMakerId = sysMakerId;
	}


	public List<DomesticOrderItemDTO> getDomesticOrderItemList() {
		return domesticOrderItemList;
	}


	public void setDomesticOrderItemList(List<DomesticOrderItemDTO> domesticOrderItemList) {
		this.domesticOrderItemList = domesticOrderItemList;
	}

	public int getItemListSize() {
		return itemListSize;
	}

	public void setItemListSize(int itemListSize) {
		this.itemListSize = itemListSize;
	}


	public List<ExtendDomesticOrderItemSearchDTO> getExtendDomesticOrderItemSearchList() {
		return extendDomesticOrderItemSearchList;
	}


	public void setExtendDomesticOrderItemSearchList(
			List<ExtendDomesticOrderItemSearchDTO> extendDomesticOrderItemSearchList) {
		this.extendDomesticOrderItemSearchList = extendDomesticOrderItemSearchList;
	}


	public DomesticOrderSlipDTO getDomesticOrderSlipDto() {
		return domesticOrderSlipDto;
	}


	public void setDomesticOrderSlipDto(DomesticOrderSlipDTO domesticOrderSlipDto) {
		this.domesticOrderSlipDto = domesticOrderSlipDto;
	}


	/**
	 * @return domesticOrderSlipList
	 */
	public List<DomesticOrderSlipDTO> getDomesticOrderSlipList() {
		return domesticOrderSlipList;
	}


	/**
	 * @param domesticOrderSlipList セットする domesticOrderSlipList
	 */
	public void setDomesticOrderSlipList(List<DomesticOrderSlipDTO> domesticOrderSlipList) {
		this.domesticOrderSlipList = domesticOrderSlipList;
	}


	/**
	 * @return registryDto
	 */
	public RegistryMessageDTO getRegistryDto() {
		return registryDto;
	}


	/**
	 * @param registryDto セットする registryDto
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
		this.registryDto = registryDto;
	}


	/**
	 * @return messageFlg
	 */
	public String getMessageFlg() {
		return messageFlg;
	}


	/**
	 * @param messageFlg セットする messageFlg
	 */
	public void setMessageFlg(String messageFlg) {
		this.messageFlg = messageFlg;
	}


	/**
	 * @return addDomesticOrderItemList
	 */
	public List<DomesticOrderListDTO> getAddDomesticOrderItemList() {
		return addDomesticOrderItemList;
	}


	/**
	 * @param addDomesticOrderItemList セットする addDomesticOrderItemList
	 */
	public void setAddDomesticOrderItemList(List<DomesticOrderListDTO> addDomesticOrderItemList) {
		this.addDomesticOrderItemList = addDomesticOrderItemList;
	}


	/**
	 * @return sysDomesticSlipId
	 */
	public long getSysDomesticSlipId() {
		return sysDomesticSlipId;
	}


	/**
	 * @param sysDomesticSlipId セットする sysDomesticSlipId
	 */
	public void setSysDomesticSlipId(long sysDomesticSlipId) {
		this.sysDomesticSlipId = sysDomesticSlipId;
	}

	/**
	 * @return extendDomesticOrderSlipList
	 */
	public List<ExtendDomesticOrderSlipDTO> getExtendDomesticOrderSlipList() {
		return extendDomesticOrderSlipList;
	}


	/**
	 * @param extendDomesticOrderSlipList セットする extendDomesticOrderSlipList
	 */
	public void setExtendDomesticOrderSlipList(
			List<ExtendDomesticOrderSlipDTO> extendDomesticOrderSlipList) {
		this.extendDomesticOrderSlipList = extendDomesticOrderSlipList;
	}


	/**
	 * @return extendDomesticOrderSlipDto
	 */
	public ExtendDomesticOrderSlipDTO getExtendDomesticOrderSlipDto() {
		return extendDomesticOrderSlipDto;
	}


	/**
	 * @param extendDomesticOrderSlipDto セットする extendDomesticOrderSlipDto
	 */
	public void setExtendDomesticOrderSlipDto(ExtendDomesticOrderSlipDTO extendDomesticOrderSlipDto) {
		this.extendDomesticOrderSlipDto = extendDomesticOrderSlipDto;
	}


	/**
	 * @return openPriceFlg
	 */
	public String getOpenPriceFlg() {
		return openPriceFlg;
	}


	/**
	 * @param openPriceFlg セットする openPriceFlg
	 */
	public void setOpenPriceFlg(String openPriceFlg) {
		this.openPriceFlg = openPriceFlg;
	}


	/**
	 * @return domesticOrderList
	 */
	public List<DomesticOrderListDTO> getDomesticOrderList() {
		return domesticOrderList;
	}


	/**
	 * @param domesticOrderList セットする domesticOrderList
	 */
	public void setDomesticOrderList(List<DomesticOrderListDTO> domesticOrderList) {
		this.domesticOrderList = domesticOrderList;
	}


	/**
	 * @return sysManagementId
	 */
	public long getSysManagementId() {
		return sysManagementId;
	}


	/**
	 * @param sysManagementId セットする sysManagementId
	 */
	public void setSysManagementId(long sysManagementId) {
		this.sysManagementId = sysManagementId;
	}
}
