package jp.co.kts.ui.domestic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.app.search.entity.DomesticOrderListSearchDTO;
import jp.co.kts.ui.web.struts.WebConst;

public class DomesticOrderListForm extends AppBaseForm {

	/** 表示分国内注文文書情報 */
	private List<DomesticOrderListDTO> domesticOrderItemInfoList = new ArrayList<>();
	/** 検索用国内注文文書情報 */
	private DomesticOrderListSearchDTO domesticOrderListSearchDTO = new DomesticOrderListSearchDTO();
	/** メーカーリスト */
	private List<MstMakerDTO> makerListDTO = new ArrayList<>();
	
	
	private List<MstCorporationDTO> corporationListDto = new ArrayList<>();
	
	/** システムメーカーID */
	private long sysMakerId;
	/** システム国内商品IDリスト */
	private List<DomesticOrderListDTO> domesticOrderItemIdList = new ArrayList<>();
	/** システム国内商品IDリストサイズ */
	private int sysDomesticItemIdListSize = 0;
	/**  */
	private int pageIdx;
	/** ページ最大表示件数 */
	private int domesticOrderPageMax;
	/** メッセージ */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();
	/** メッセージ有無フラグ */
	private String messageFlg = "0";
	/** 移動ステータス */
	private String moveStatus;
	/** 管理品番：出品DB検索用 */
	private String managementCode;
	/** 注文書No：注文詳細表示用 */
	private String purchaseOrderNo;
	/** システム伝票ID：注文詳細表示用*/
	private String sysDomesticSlipId;
	/** システム伝票ID：文字列用*/
	private String strSysDomesticSlipId;
	/** 印刷確認フラグ */
	private String printCheckFlag = "0";
	/** 国内注文書印刷情報 */
	private List<DomesticOrderListDTO> domesticPrintOrderItemInfoList = new ArrayList<>();


	/**
	 * ---------------------------------------getterのみ---------------------------------------------------------
	 */
		/** 並替え */
		private Map<String, String> domesticListSortMap = WebConst.DOMESTIC_ORDER_LIST_SORT_MAP;
		/** 昇順・降順 */
		private Map<String, String> itemListSortOrder = WebConst.DOMESTIC_EXIHIBITION_ORDER;
		/** 最大表示件数 */
		private Map<String, Integer> listPageMaxMap = WebConst.LIST_PAGE_MAX_MAP;
		/**  */
		private Map<String, String> domesticMap = WebConst.DOMESTIC_EXIHIBITION_SORT_LIST_MAP;
		/** 出荷・移動 */
		private Map<String, String> domesticselect = WebConst.DOMESTIC_EXIHIBITION_SELECT_MAP;
		/** ステータス検索用 */
		private Map<String, String> domesticstatus = WebConst.DOMESTIC_EXIHIBITION_STATUS_MAP;
		/** ステータスプルダウン */
		private Map<String, String> domesticstatusPulLdown = WebConst.DOMESTIC_EXIHIBITION_STATUS_PULLDOWN_MAP;
		/** ソート順：一致・以上・以下 */
		private Map<String, String> numberOrderMap = WebConst.NUMBER_ORDER_MAP;
		/** 納入先 */
		private Map<String, String> deliveryTypeMap = WebConst.DELIVERTY_TYPE;

		@Override
		protected void doReset(AppActionMapping appMapping,
				HttpServletRequest request) {
			// TODO 自動生成されたメソッド・スタブ
		}


	/**
		 * 表示分国内注文文書情報を取得します。
		 * @return 表示分国内注文文書情報
		 */
		public List<DomesticOrderListDTO> getDomesticOrderItemInfoList() {
		    return domesticOrderItemInfoList;
		}


	/**
	 * 表示分国内注文文書情報を設定します。
	 * @param domesticOrderItemInfoList 表示分国内注文文書情報
	 */
	public void setDomesticOrderItemInfoList(List<DomesticOrderListDTO> domesticOrderItemInfoList) {
	    this.domesticOrderItemInfoList = domesticOrderItemInfoList;
	}


	public DomesticOrderListSearchDTO getDomesticOrderListSearchDTO() {
			return domesticOrderListSearchDTO;
		}

		public void setDomesticOrderListSearchDTO(
				DomesticOrderListSearchDTO domesticOrderListSearchDTO) {
			this.domesticOrderListSearchDTO = domesticOrderListSearchDTO;
		}


//	/**
//	 *
//	 * @return
//	 */
//	public List<DomesticOrderListDTO> getDomesticOrderList() {
//		return domesticOrderList;
//	}
//
//	/**
//	 *
//	 * @param domesticOrderList
//	 */
//	public void setDomesticOrderList(List<DomesticOrderListDTO> domesticOrderList) {
//		this.domesticOrderList = domesticOrderList;
//	}

	/**
	 * システム国内商品IDリストを取得します。
	 * @return システム国内商品IDリスト
	 */
	public List<DomesticOrderListDTO> getDomesticOrderItemIdList() {
	    return domesticOrderItemIdList;
	}


	/**
	 * システム国内商品IDリストを設定します。
	 * @param domesticOrderItemIdList システム国内商品IDリスト
	 */
	public void setDomesticOrderItemIdList(List<DomesticOrderListDTO> domesticOrderItemIdList) {
	    this.domesticOrderItemIdList = domesticOrderItemIdList;
	}


	/**
	 * システム国内商品IDリストサイズを取得します。
	 * @return システム国内商品IDリストサイズ
	 */
	public int getSysDomesticItemIdListSize() {
	    return sysDomesticItemIdListSize;
	}


	/**
	 * システム国内商品IDリストサイズを設定します。
	 * @param sysDomesticItemIdListSize システム国内商品IDリストサイズ
	 */
	public void setSysDomesticItemIdListSize(int sysDomesticItemIdListSize) {
	    this.sysDomesticItemIdListSize = sysDomesticItemIdListSize;
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
	public int getDomesticOrderPageMax() {
	    return domesticOrderPageMax;
	}


	/**
	 * ページ最大表示件数を設定します。
	 * @param domesticOrderPageMax ページ最大表示件数
	 */
	public void setDomesticOrderPageMax(int domesticOrderPageMax) {
	    this.domesticOrderPageMax = domesticOrderPageMax;
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
	 * 移動ステータスを取得します。
	 * @return 移動ステータス
	 */
	public String getMoveStatus() {
	    return moveStatus;
	}


	/**
	 * 移動ステータスを設定します。
	 * @param moveStatus 移動ステータス
	 */
	public void setMoveStatus(String moveStatus) {
	    this.moveStatus = moveStatus;
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
	 * 注文書No：注文詳細表示用を取得します。
	 * @return 注文書No：注文詳細表示用
	 */
	public String getPurchaseOrderNo() {
	    return purchaseOrderNo;
	}


	/**
	 * 注文書No：注文詳細表示用を設定します。
	 * @param purchaseOrderNo 注文書No：注文詳細表示用
	 */
	public void setPurchaseOrderNo(String purchaseOrderNo) {
	    this.purchaseOrderNo = purchaseOrderNo;
	}


	/**
	 * システム伝票ID：注文詳細表示用を取得します。
	 * @return システム伝票ID：注文詳細表示用
	 */
	public String getSysDomesticSlipId() {
	    return sysDomesticSlipId;
	}


	/**
	 * システム伝票ID：注文詳細表示用を設定します。
	 * @param sysDomesticSlipId システム伝票ID：注文詳細表示用
	 */
	public void setSysDomesticSlipId(String sysDomesticSlipId) {
	    this.sysDomesticSlipId = sysDomesticSlipId;
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

	/** 会社一覧 */
	public List<MstCorporationDTO> getCorporationListDTO() {
		return corporationListDto;
	}


	public void setCorporationListDTO(List<MstCorporationDTO> corporationListDto) {
		this.corporationListDto = corporationListDto;
	}

	/**
	 *
	 * @return
	 */


	public Map<String, String> getDomesticListSortMap() {
		return domesticListSortMap;
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

	public Map<String, String> getDomesticselect() {
		return domesticselect;
	}


	public Map<String, String> getDomesticstatus() {
		return domesticstatus;
	}


	public void setDomesticstatus(Map<String, String> domesticstatus) {
		this.domesticstatus = domesticstatus;
	}


	public List<MstMakerDTO> getMakerListDTO() {
		return makerListDTO;
	}


	public void setMakerListDTO(List<MstMakerDTO> makerListDTO) {
		this.makerListDTO = makerListDTO;
	}


	public long getSysMakerId() {
		return sysMakerId;
	}


	public void setSysMakerId(long sysMakerId) {
		this.sysMakerId = sysMakerId;
	}


	public Map<String, String> getNumberOrderMap() {
		return numberOrderMap;
	}


	public Map<String, String> getDomesticstatusPulLdown() {
		return domesticstatusPulLdown;
	}


	public void setDomesticstatusPulLdown(Map<String, String> domesticstatusPulLdown) {
		this.domesticstatusPulLdown = domesticstatusPulLdown;
	}


	/**
	 * 納入先を取得します。
	 * @return 納入先
	 */
	public Map<String,String> getDeliveryTypeMap() {
	    return deliveryTypeMap;
	}


	/**
	 * 納入先を設定します。
	 * @param deliveryTypeMap 納入先
	 */
	public void setDeliveryTypeMap(Map<String,String> deliveryTypeMap) {
	    this.deliveryTypeMap = deliveryTypeMap;
	}


	/**
	 * @return printCheckFlag
	 */
	public String getPrintCheckFlag() {
		return printCheckFlag;
	}


	/**
	 * @param printCheckFlag セットする printCheckFlag
	 */
	public void setPrintCheckFlag(String printCheckFlag) {
		this.printCheckFlag = printCheckFlag;
	}

}
