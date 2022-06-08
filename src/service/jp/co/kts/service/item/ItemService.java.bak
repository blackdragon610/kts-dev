package jp.co.kts.service.item;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.AnnualSalesDTO;
import jp.co.kts.app.common.entity.BackOrderDTO;
import jp.co.kts.app.common.entity.DeadStockDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.common.entity.UpdateDataHistoryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendArrivalScheduleDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemCostDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemManualDTO;
import jp.co.kts.app.extendCommon.entity.ExtendItemPriceDTO;
import jp.co.kts.app.extendCommon.entity.ExtendKeepDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.ResultItemSearchDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.core.SystemSetting;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.fileImport.ExcelImportDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.SupplierDAO;
import jp.co.kts.dao.mst.UpdateDataHistoryDAO;
import jp.co.kts.dao.mst.WarehouseDAO;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.WarehouseService;
import jp.co.kts.ui.mst.SetItemForm;
import jp.co.kts.ui.mst.WarehouseForm;
import jp.co.kts.ui.web.struts.WebConst;

public class ItemService {
	/** 注文プールフラグON */
	private final String ORDER_POOL_FLG_ON = "1";
	private final String DELIVERY_ALERT_FLG_ON = "1";
	/** リードタイム計算値 START */
	private long ITEM_LEAD_NUM1 = 1;
	private double ITEM_LEAD_NUM1_5 = 1.5;
	private long ITEM_LEAD_NUM2 = 2;
	private long ITEM_LEAD_NUM3 = 3;
	private long ITEM_LEAD_NUM4 = 4;
	private long ITEM_LEAD_NUM5 = 5;
	private long ITEM_LEAD_NUM6 = 6;
	/** リードタイム計算値 END */

	/** 更新タイプ */
	private String UPDAET_TYPE = "1";


	/**
	 * ----------------------------------------init----------------------------
	 * -----------------------
	 */

	// public List<ExtendItemCostDTO> initItemCostList(int listSize) {

	/**
	 * 法人数分リストを初期化し返却します
	 *
	 * @return　商品原価リスト
	 * @throws Exception
	 */
	public List<ExtendItemCostDTO> initItemCostList() throws Exception {

		List<ExtendItemCostDTO> list = new ArrayList<>();

		CorporationService corporationService = new CorporationService();
		List<MstCorporationDTO> corporationList = new ArrayList<>(
				corporationService.getCorporationList());

		for (int i = 0; i < corporationList.size(); i++) {

			list.add(new ExtendItemCostDTO());
			list.get(i).setCorporationNm(
					corporationList.get(i).getCorporationNm());
			list.get(i).setSysCorporationId(
					corporationList.get(i).getSysCorporationId());
		}
		return list;
	}

	/**
	 * 法人数分リストを初期化し返却します
	 *
	 * @return　商品売価リスト
	 * @throws Exception
	 */
	public List<ExtendItemPriceDTO> initItemPriceList() throws Exception {

		List<ExtendItemPriceDTO> list = new ArrayList<>();

		CorporationService corporationService = new CorporationService();
		List<MstCorporationDTO> corporationList = new ArrayList<>(
				corporationService.getCorporationList());

		for (int i = 0; i < corporationList.size(); i++) {

			list.add(new ExtendItemPriceDTO());
			list.get(i).setCorporationNm(
					corporationList.get(i).getCorporationNm());
			list.get(i).setSysCorporationId(
					corporationList.get(i).getSysCorporationId());
		}
		return list;
	}

	/**
	 * KTS倉庫登録初期表示用データ取得
	 * @return
	 * @throws Exception
	 */
	public List<ExtendWarehouseStockDTO> initAddWarehousStockList()
			throws Exception {

		List<ExtendWarehouseStockDTO> list = new ArrayList<>();

		WarehouseService warehouseService = new WarehouseService();
		List<MstWarehouseDTO> warehouseList = new ArrayList<>(
				warehouseService.getWarehouseList());

		for (int i = 0; i < warehouseList.size(); i++) {

			list.add(new ExtendWarehouseStockDTO());

			// list.get(i).setSysWarehouseId(warehouseList.get(i).getSysWarehouseId());
			// list.get(i).setWarehouseNm(warehouseList.get(i).getWarehouseNm());
		}
		return list;
	}


	/**
	 * 外部倉庫登録初期表示用データ取得
	 * @return
	 * @throws Exception
	 */
	public List<ExtendWarehouseStockDTO> initAddExternalStockList()
			throws Exception {

		List<ExtendWarehouseStockDTO> list = new ArrayList<>();

		WarehouseService warehouseService = new WarehouseService();
		List<ExtendMstWarehouseDTO> warehouseList = new ArrayList<>(warehouseService.getExternalWarehouseList());

		for (int i = 0; i < warehouseList.size(); i++) {
			ExtendWarehouseStockDTO ewsDTO = new ExtendWarehouseStockDTO();
			ewsDTO.setSysExternalWarehouseCode(warehouseList.get(i).getSysExternalWarehouseCode());
			list.add(ewsDTO);

		}
		return list;
	}


	public ExtendMstItemDTO initMstItem() {

		ExtendMstItemDTO dto = new ExtendMstItemDTO();
		dto.setOrderAlertNum(WebConst.MST_ITEM_ORDER_ALERT);
		dto.setLeaveClassFlg("0");

		return dto;
	}

	/**
	 * [概要]
	 * 追加用不良在庫リストの初期設定を行う
	 * @return
	 */
	public List<DeadStockDTO> initAddDeadStockList() {

		List<DeadStockDTO> list = new ArrayList<>();
		int max = WebConst.ADD_DEAD_STOCK_LENGTH;

		for (int i = 0; i < max; i++) {

			list.add(new DeadStockDTO());
		}

		return list;
	}

	public List<ExtendArrivalScheduleDTO> initAddArrivalScheduleList() {

		List<ExtendArrivalScheduleDTO> list = new ArrayList<>();
		int max = 10;

		for (int i = 0; i < max; i++) {

			list.add(new ExtendArrivalScheduleDTO());

			// list.get(i).setArrivalScheduleDate(getToday());
			list.get(i).setItemOrderDate(StringUtil.getToday());
		}

		return list;
	}

	public List<BackOrderDTO> initAddBackOrderList() {

		List<BackOrderDTO> list = new ArrayList<>();
		int max = 5;
		for (int i = 0; i < max; i++) {

			list.add(new BackOrderDTO());
			list.get(i).setBackOrderDate(StringUtil.getToday());
		}
		return list;
	}

	public List<ExtendSetItemDTO> initAddSetItemList() {

		List<ExtendSetItemDTO> list = new ArrayList<>();
		int max = 10;
		for (int i = 0; i < max; i++) {

			list.add(new ExtendSetItemDTO());
		}
		return list;
	}

	public List<ExtendItemManualDTO> initAddItemManualList() {

		List<ExtendItemManualDTO> list = new ArrayList<>();
		for (int i = 0; i < WebConst.ADD_ITEM_MANUAL_LENGTH; i++) {

			list.add(new ExtendItemManualDTO());
		}

		return list;
	}

	// 20140404 八鍬
	public List<ExtendKeepDTO> initAddKeepList() {

		List<ExtendKeepDTO> list = new ArrayList<>();
		int max = 5;

		for (int i = 0; i < max; i++) {

			list.add(new ExtendKeepDTO());
		}

		return list;
	}


	public List<ExtendKeepDTO> initAddExternalKeepList() {

		List<ExtendKeepDTO> list = new ArrayList<>();
		int max = 5;

		for (int i = 0; i < max; i++) {

			list.add(new ExtendKeepDTO());
		}

		return list;
	}

	/**
	 * ----------------------------------------OUTPUT--------------------------
	 * -------------------------
	 */

	public ExtendMstItemDTO getMstItemDTO(long sysItemId) throws DaoException {

		ItemDAO dao = new ItemDAO();

		return dao.getMstItemDTO(sysItemId);
	}

	/**
	 * 倉庫在庫を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendWarehouseStockDTO> getWarehouseStockList(long sysItemId)
			throws DaoException {

		ItemDAO dao = new ItemDAO();

		return dao.getWarehouseStockList(sysItemId);
	}


	/**
	 * 楽天倉庫を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendWarehouseStockDTO> getExternalStockList(long sysItemId, String sysExternalWarehouseCode)
			throws DaoException {

		return new ItemDAO().getExternalStockList(sysItemId, sysExternalWarehouseCode);
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
	 * 価格情報タブの原価情報を取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendItemCostDTO> getItemCostList(long sysItemId)
			throws DaoException {

		List<ExtendItemCostDTO> list = new ItemDAO().getItemCostList(sysItemId);

		for (ExtendItemCostDTO dto : list) {

			if (dto.getSysItemId() == 0) {

				dto.setSysItemId(sysItemId);
			}
		}
		return list;
	}

	/**
	 * 価格情報タブの構成品番原価情報合計値を取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public SetItemForm getItemCostSumList(long sysItemId, SetItemForm form)
			throws DaoException {

		//構成商品の金額合算値を取得
		List<ExtendItemCostDTO> list = new ItemDAO().getItemCostSumList(sysItemId);

		for (int i = 0; i < form.getItemCostList().size(); i++) {
			for (int j = 0; j < list.size(); j++) {

				if (list.get(j).getSysCorporationId() == 99) {
					form.getMstItemDTO().setKindCost(list.get(j).getCost());
				}

				if (form.getItemCostList().get(i).getSysCorporationId()
						== list.get(j).getSysCorporationId()) {
					form.getItemCostList().get(i).setCost(list.get(j).getCost());
					break;
				}
			}
		}



		return form;
	}

	/**
	 * 価格情報タブの売価情報の取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendItemPriceDTO> getItemPriceList(long sysItemId)
			throws DaoException {

		List<ExtendItemPriceDTO> list = new ItemDAO()
				.getItemPriceList(sysItemId);

		for (ExtendItemPriceDTO dto : list) {

			if (dto.getSysItemId() == 0) {

				dto.setSysItemId(sysItemId);
			}
		}
		return list;
	}

	/**
	 * 価格情報タブの構成品番売価情報の合計値取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public SetItemForm getItemPriceSumList(long sysItemId, SetItemForm form)
			throws DaoException {
		//構成商品の金額合算値を取得
		List<ExtendItemPriceDTO> list = new ItemDAO().getItemPriceSumList(sysItemId);

		for (int i = 0; i < form.getItemPriceList().size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (form.getItemPriceList().get(i).getSysCorporationId()
						== list.get(j).getSysCorporationId()) {
					form.getItemPriceList().get(i).setPrice(list.get(j).getPrice());
				}
			}
		}
		return form;
	}

	/**
	 * 入荷予定タブの取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendArrivalScheduleDTO> getArrivalScheduleList(long sysItemId)
			throws DaoException {

		List<ExtendArrivalScheduleDTO> list = new ItemDAO()
				.getArrivalScheduleList(sysItemId);

		for (ExtendArrivalScheduleDTO dto : list) {

			dto.setArrivalScheduleNum(dto.getArrivalNum());
			dto.setOrderStatusNm(WebConst.ORDER_STATUS_NUMBERED_MAP.get(dto.getOrderStatus()));
			if (dto.getPoNo() == null) {
				dto.setPoNo(StringUtils.EMPTY);
			}
		}
		return list;
		// return new ItemDAO().getArrivalScheduleList(sysItemId);
	}

	/**
	 * 入荷履歴情報タブの取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendArrivalScheduleDTO> getArrivalHistoryList(long sysItemId)
			throws DaoException {

		return new ItemDAO().getArrivalHistoryList(sysItemId);
	}

	public List<BackOrderDTO> getBackOrderList(long sysItemId)
			throws DaoException {

		return new ItemDAO().getBackOrderList(sysItemId);
	}

	public List<ExtendSetItemDTO> getAllSetItemList() throws DaoException {

		return new ItemDAO().getAllSetItemList();
	}

	public List<ExtendSetItemDTO> getSetItemList(long sysItemId)
			throws DaoException {

		return new ItemDAO().getSetItemList(sysItemId);
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
	 * 出庫分類フラグが1:構成商品のものを取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSetItemDTO> getSetItemLeaveClassFlgOnList(long sysItemId)
			throws DaoException {

		return new ItemDAO().getSetItemLeaveClassFlgOnList(sysItemId);
	}

	/**
	 * 組立可数更新サービス
	 */
//	public void setAssemblyNum(long sysItemId, int assemblyNum)
//			throws DaoException {
//
//		new ItemDAO().setAssemblyNum(sysItemId, assemblyNum);
//	}

	private List<ExtendSetItemDTO> getParentSetItemList(long sysItemId) throws DaoException {

		return new ItemDAO().getParentSetItemList(sysItemId);
	}

	/**
	 * 出庫分類フラグが1：構成商品から出庫のものを取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	private List<ExtendSetItemDTO> getParentSetItemLeaveOnList(long sysItemId) throws DaoException {

		return new ItemDAO().getParentSetItemLeaveOnList(sysItemId);
	}

	public long getSysItemIdFromShopCd(String shopItemCd) throws DaoException {

		ExtendMstItemDTO dto = new ExtendMstItemDTO();

		// 11桁以下の場合は自社商品ではない
		if (StringUtils.length(shopItemCd) < 11) {

			return 0;
		}

		if (StringUtils.length(shopItemCd) > 11) {

			shopItemCd = StringUtils.substring(shopItemCd, 0, 11);
		}
		dto.setItemCode(shopItemCd);

		long sysItemId = getSysItemId(dto);
		// if (sysItemId == 0) {
		//
		// dto.setItemCode(null);
		// dto.setOldItemCode(shopItemCd);
		// return getSysItemId(dto);
		// }

		return sysItemId;
	}

	public long getSysItemId(String itemCode) throws DaoException {

		ExtendMstItemDTO dto = new ExtendMstItemDTO();

		dto.setItemCode(itemCode);

		ItemDAO itemDAO = new ItemDAO();

		dto = itemDAO.getMstItemDTO(dto);

		if (dto == null) {
			return 0;
		}

		return dto.getSysItemId();
	}

	public long getSysItemId(ExtendMstItemDTO dto) throws DaoException {

		dto = new ItemDAO().getMstItemDTO(dto);

		if (dto == null) {
			return 0;
		}

		return dto.getSysItemId();
	}

	public SearchItemDTO initItemListDTO() {

		SearchItemDTO dto = new SearchItemDTO();

		dto.setListPageMax(WebConst.LIST_PAGE_MAX_CODE_1);
		dto.setDisplayContents("1");

		return dto;
	}

	/**
	 * システム商品ID検索：在庫一覧画面
	 * @param searchItemDTO
	 * @return
	 * @throws DaoException
	 */
 	// speed session-block
	public List<SysItemIdDTO> getSysItemIdList(HttpSession session, SearchItemDTO searchItemDTO)
			throws DaoException {

		//return new ItemDAO().getItemSearchList(searchItemDTO);
		List<ResultItemSearchDTO> retList = new ItemDAO().getExportItemSearchList(searchItemDTO);
		session.setAttribute("getExportItemSearchList(searchItemDTO)", retList );
		
		List<SysItemIdDTO> ret = new ArrayList<>();
		for(int i=0; i<retList.size(); i++)
		{
			long sysItemId = retList.get(i).getSysItemId();
			SysItemIdDTO idDto = new SysItemIdDTO();
			idDto.setSysItemId(sysItemId);
			ret.add(idDto);	
		}
		return ret;
	}

	public List<ResultItemSearchDTO> getItemList(
			List<SysItemIdDTO> sysItemIdList, int pageIdx, SearchItemDTO dto)
			throws DaoException {

		List<ResultItemSearchDTO> itemList = new ArrayList<>();

		if (StringUtils.isEmpty(dto.getListPageMax())) {
			dto.setListPageMax("1");
		}

		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(dto.getListPageMax())
				* pageIdx; i < WebConst.LIST_PAGE_MAX_MAP.get(dto
				.getListPageMax()) * (pageIdx + 1)
				&& i < sysItemIdList.size(); i++) {

			SearchItemDTO searchItemDTO = new SearchItemDTO();
			searchItemDTO.setSysItemId(sysItemIdList.get(i).getSysItemId());
			searchItemDTO.setHaibangFlg(dto.getHaibangFlg());
			searchItemDTO.setHaibangContainFlg(dto.getHaibangContainFlg());

			ResultItemSearchDTO itemDTO = new ItemDAO()
					.getItemSearch(searchItemDTO);

			if (itemDTO.getArrivalNum() == 0) {
				itemDTO.setArrivalNumDisp(StringUtils.EMPTY);
			} else {
				itemDTO.setArrivalNumDisp(String.valueOf(itemDTO
						.getArrivalNum()));
			}

			if (itemDTO.getBackOrderCount() == 0) {
				itemDTO.setBackOrderCountDisp(StringUtils.EMPTY);
			} else {
				itemDTO.setBackOrderCountDisp(String.valueOf(itemDTO
						.getBackOrderCount()));
			}

			if (!itemDTO.getOrderPoolFlg().equals(ORDER_POOL_FLG_ON)) {
				itemDTO.setOrderNum(itemDTO.getMinimumOrderQuantity());
			}

			itemDTO.setBeforeOrderPoolFlg(itemDTO.getOrderPoolFlg());


			//注文アラートが発生している対象はフラグを立てる
			if (itemDTO.getItemLeadTime() == null) {
				if (itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
					itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
				}

			} else {

				switch (itemDTO.getItemLeadTime()) {
				case WebConst.LEAD_TIME_CODE_0:
					if (ITEM_LEAD_NUM1 * itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				case WebConst.LEAD_TIME_CODE_1:
					if (ITEM_LEAD_NUM1_5 * itemDTO.getAnnualAverageSalesNum() >= (double) itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				case WebConst.LEAD_TIME_CODE_2:
					if (ITEM_LEAD_NUM2 * itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				case WebConst.LEAD_TIME_CODE_3:
					if (ITEM_LEAD_NUM3 * itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				case WebConst.LEAD_TIME_CODE_4:
					if (ITEM_LEAD_NUM4 * itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				case WebConst.LEAD_TIME_CODE_5:
					if (ITEM_LEAD_NUM5 * itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				case WebConst.LEAD_TIME_CODE_6:
					if (ITEM_LEAD_NUM6 * itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				case "":
					if (itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				default:
					if (itemDTO.getAnnualAverageSalesNum() >= itemDTO.getTotalStockNum()) {
						itemDTO.setDeliveryAlertTargetFlg(DELIVERY_ALERT_FLG_ON);
					}
					break;
				}
			}


			// setFlags(itemDTO);
			itemList.add(itemDTO);

		}
		return itemList;
	}

	public List<ExtendItemManualDTO> getItemManualList(long sysItemId)
			throws DaoException {

		// List<ExtendItemManualDTO> list = ;

		return new ItemDAO().getItemManualList(sysItemId);
	}

	public Map<String, String> getDocumentTypeMap()
			throws DaoException {

		Map<String, String> map = new HashMap<>();

		map = WebConst.DOCUMENT_TYPE_MAP;

		return map;
	}

	/**
	 * フラグで渡ってきた値を変換する
	 *
	 * @param searchItemDTO
	 */
	public void setFlags(SearchItemDTO searchItemDTO) {

		//キープ有フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getKeepFlg())) {
			searchItemDTO.setKeepFlg(StringUtil.switchCheckBox(searchItemDTO
					.getKeepFlg()));
		}

		//在庫アラートフラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getOrderAlertFlg())) {
			searchItemDTO.setOrderAlertFlg(StringUtil
					.switchCheckBox(searchItemDTO.getOrderAlertFlg()));
		}

		//注文アラートフラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getDeliveryAlertFlg())) {
			searchItemDTO.setDeliveryAlertFlg(StringUtil
					.switchCheckBox(searchItemDTO.getDeliveryAlertFlg()));
		}

		//入荷予定日超過フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getOverArrivalScheduleFlg())) {
			searchItemDTO.setOverArrivalScheduleFlg(StringUtil
					.switchCheckBox(searchItemDTO.getOverArrivalScheduleFlg()));
		}

		//入荷予定日有フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getArrivalScheduleFlg())) {
			searchItemDTO.setArrivalScheduleFlg(StringUtil
					.switchCheckBox(searchItemDTO.getArrivalScheduleFlg()));
		}

		//セット商品フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getSetItemFlg())) {
			searchItemDTO.setSetItemFlg(StringUtil.switchCheckBox(searchItemDTO
					.getSetItemFlg()));
		}

		//説明書フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getManualFlg())) {
			searchItemDTO.setManualFlg(StringUtil.switchCheckBox(searchItemDTO
					.getManualFlg()));
		}

		//図面フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getPlanSheetFlg())) {
			searchItemDTO.setPlanSheetFlg(StringUtil
					.switchCheckBox(searchItemDTO.getPlanSheetFlg()));
		}

		//その他資料フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getOtherDocumentFlg())) {
			searchItemDTO.setOtherDocumentFlg(StringUtil
					.switchCheckBox(searchItemDTO.getOtherDocumentFlg()));
		}

		//不良在庫フラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getDeadStockFlg())) {
			searchItemDTO.setDeadStockFlg(StringUtil
					.switchCheckBox(searchItemDTO.getDeadStockFlg()));
		}

		//注文プールフラグ
		if (StringUtils.isNotEmpty(searchItemDTO.getOrderPoolFlg())) {
			searchItemDTO.setOrderPoolFlg(StringUtil
					.switchCheckBox(searchItemDTO.getOrderPoolFlg()));
		}
	}

	/**
	 * 読み替え
	 *
	 * @param MstItemDTO
	 */
	public void setFlags(ExtendMstItemDTO dto) {

		if (StringUtils.isNotEmpty(dto.getManualFlg())) {
			dto.setManualFlg(StringUtil.switchCheckBox(dto.getManualFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getPlanSheetFlg())) {
			dto.setPlanSheetFlg(StringUtil.switchCheckBox(dto.getPlanSheetFlg()));
		}
	}

	/**
	 * 読み替え
	 *
	 * @param MstItemDTO
	 */
	public void setFlags(List<ResultItemSearchDTO> itemList) {

		for (ResultItemSearchDTO dto : itemList) {

			if (StringUtils.isNotEmpty(dto.getDeleteCheckFlg())) {
				dto.setDeleteCheckFlg(StringUtil.switchCheckBox(dto
						.getDeleteCheckFlg()));
			}
		}
	}

	/**
	 * ----------------------------------------INPUT----------------------------
	 * -----------------------
	 */

	public ExtendMstItemDTO setSetItemFlg(ExtendMstItemDTO dto)
			throws DaoException {

		dto.setSetItemFlg("0");

		return dto;
	}

	// ■商品
	/**
	 * 商品の登録を行います
	 *
	 * @param mstItemDTO
	 * @throws DaoException
	 */
	public ErrorMessageDTO registryItem(ExtendMstItemDTO mstItemDTO)
			throws DaoException {

		ItemDAO dao = new ItemDAO();

		if (StringUtils.isNotEmpty(existItemCode(mstItemDTO.getItemCode()))) {

			ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage(mstItemDTO.getItemCode()
					+ "はすでに登録されている品番です");
			return errorMessageDTO;
		}

		mstItemDTO.setSysItemId(new SequenceDAO().getMaxSysItemId() + 1);

		dao.registryItem(mstItemDTO);

		return new ErrorMessageDTO();
	}

	public ErrorMessageDTO updateItem(ExtendMstItemDTO mstItemDTO)
			throws DaoException {

		// 品番の変更が行われた場合重複チェック
		if (!StringUtils.equals(mstItemDTO.getBeforeItemCode(),
				mstItemDTO.getItemCode())
				&& StringUtils.isNotEmpty(existItemCode(mstItemDTO
						.getItemCode()))) {

			ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage(mstItemDTO.getItemCode()
					+ "はすでに登録されている品番です");
			return errorMessageDTO;
		}

		new ItemDAO().updateItem(mstItemDTO);

		return new ErrorMessageDTO();
	}

	public String existItemCode(String itemCode) throws DaoException {

		ExtendMstItemDTO dto = new ExtendMstItemDTO(StringUtils.EMPTY);
		dto.setItemCode(itemCode);
		dto = new ItemDAO().getMstItemDTO(dto);

		if (dto == null) {
			return null;
		}
		return dto.getItemCode();
	}

	// ■商品売価
	public void registryItemPriceList(List<ExtendItemPriceDTO> itemPriceList,
			long sysItemId) throws DaoException {

		for (ExtendItemPriceDTO dto : itemPriceList) {

			dto.setSysItemId(sysItemId);
			registryItemPrice(dto);
			// registryItemPrice(dto, sysItemId);
		}
	}

	private void registryItemPrice(ExtendItemPriceDTO dto) throws DaoException {
		// private void registryItemPrice(ExtendItemPriceDTO dto, long
		// sysItemId) throws DaoException {

		// dto.setSysItemId(sysItemId);

		dto.setSysItemPriceId(new SequenceDAO().getMaxSysItemPriceId() + 1);
		new ItemDAO().registryItemPrice(dto);
	}

	public void updateItemPriceList(List<ExtendItemPriceDTO> itemPriceList)
			throws DaoException {

		for (ExtendItemPriceDTO dto : itemPriceList) {

			// 登録時より会社マスタが増えていた場合新規登録する必要がある
			if (dto.getSysItemPriceId() == 0) {
				registryItemPrice(dto);
				continue;
			}
			updateItemPrice(dto);
		}
	}

	private void updateItemPrice(ExtendItemPriceDTO dto) throws DaoException {

		new ItemDAO().updateItemPrice(dto);
	}

	// ■商品原価
	public void registryItemCostList(List<ExtendItemCostDTO> itemCostList,
			long sysItemId) throws DaoException {

		for (ExtendItemCostDTO dto : itemCostList) {

			dto.setSysItemId(sysItemId);
			registryItemCost(dto);
		}
	}

	private void registryItemCost(ExtendItemCostDTO dto) throws DaoException {

		dto.setSysItemCostId(new SequenceDAO().getMaxSysItemCostId() + 1);
		new ItemDAO().registryItemCost(dto);
	}

	public void updateItemCostList(List<ExtendItemCostDTO> itemCostList)
			throws DaoException {

		for (ExtendItemCostDTO dto : itemCostList) {

			// 登録時より会社マスタが増えていた場合新規登録する必要がある
			if (dto.getSysItemCostId() == 0) {
				registryItemCost(dto);
				continue;
			}

			updateItemCost(dto);
		}
	}

	/**
	 * Kind原価を更新する
	 * @param dto
	 * @throws DaoException
	 */
	public void updateKindCost(ExtendMstItemDTO dto) throws DaoException{

		ItemDAO dao = new ItemDAO();

		if (dto.getKindCostId() == 0) {
			ExtendItemCostDTO itemCostDto = new ExtendItemCostDTO();
			SequenceDAO seqDao = new SequenceDAO();
			itemCostDto.setSysCorporationId(99);
			itemCostDto.setCost((int)dto.getKindCost());
			long maxSysItemCostId = seqDao.getMaxSysItemCostId() + 1;
			itemCostDto.setSysItemCostId(maxSysItemCostId);
			itemCostDto.setSysItemId(dto.getSysItemId());
			dao.registryItemCost(itemCostDto);
		} else {
			ExtendItemCostDTO costDto = new ExtendItemCostDTO();
			costDto.setSysItemCostId(dto.getKindCostId());
			costDto.setCost((int)dto.getKindCost());
			updateItemCost(costDto);
		}
	}

	private void updateItemCost(ExtendItemCostDTO dto) throws DaoException {

		new ItemDAO().updateItemCost(dto);
	}

	// ■新規倉庫登録時、既存商品にも新規倉庫を登録
	/**
	 *
	 * @param form
	 * @param sysWarehouseId
	 * @throws DaoException
	 */

	public void registryAllItemId(WarehouseForm form, long sysWarehouseId)
			throws DaoException {

		//既存商品分のfor文
		for (ExtendWarehouseStockDTO dto : form.getSysItemListAll()) {

			//商品IDに対して倉庫ID、優先度をdtoに格納
			dto.setSysWarehouseId(sysWarehouseId);
			dto.setPriority(String.valueOf(Integer.parseInt(dto.getWarehouseCount()) + 1));
			registryWarehouseStock(dto);

		}
	}

	// ■倉庫在庫
	/**
	 *
	 * @param addWarehouseStockList
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void registryWarehouseStockList(
			List<ExtendWarehouseStockDTO> addWarehouseStockList, long sysItemId)
			throws DaoException {

		for (ExtendWarehouseStockDTO dto : addWarehouseStockList) {

			// 優先度・倉庫ID・在庫数が入っていない場合登録しない
			if (StringUtils.isEmpty(dto.getPriority())
			// || dto.getStockNum() == 0　在庫数は０でもいいかな？？
					|| dto.getSysWarehouseId() == 0) {
				continue;
			}
			dto.setSysItemId(sysItemId);
			registryWarehouseStock(dto);
		}
	}

	private void registryWarehouseStock(ExtendWarehouseStockDTO dto)
			throws DaoException {

		dto.setSysWarehouseStockId(new SequenceDAO()
				.getMaxSysWarehouseStockId() + 1);
		new ItemDAO().registryWarehouseStock(dto);
	}


	/**
	 * 外部倉庫登録
	 * @param externalWarehouseStockList
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void registryExternalWarehouseStockList(
			List<ExtendWarehouseStockDTO> externalWarehouseStockList, long sysItemId)
			throws DaoException {

		for (ExtendWarehouseStockDTO dto : externalWarehouseStockList) {

			// 倉庫ID・在庫数が入っていない場合登録しない
			if (dto.getSysExternalWarehouseCode() == null) {
				continue;
			}
			dto.setSysItemId(sysItemId);
			registryExternalWarehouseStock(dto);
		}
	}

	private void registryExternalWarehouseStock(ExtendWarehouseStockDTO dto)
			throws DaoException {

		dto.setSysExternalStockId(new SequenceDAO().getMaxSysExternalStockId() + 1);
		new ItemDAO().registryExternalStock(dto);
	}



	/**
	 * KTS倉庫在庫数変更
	 * @param list
	 * @throws DaoException
	 */
	public void updateWarehouseStockList(List<ExtendWarehouseStockDTO> list)
			throws DaoException {

		for (ExtendWarehouseStockDTO dto : list) {

			// 20140330伊東敦史　下記ソースコメントアウト
			// 意図的に優先度と倉庫IDを消す場合があるかもしれないので
			// //優先度・倉庫ID・在庫数が入っていない場合更新しない
			// if (StringUtils.isEmpty(dto.getPriority()) || dto.getStockNum()
			// == 0
			// dto.getSysWarehouseId == 0) {
			// continue;
			// }
			updateWarehouseStock(dto);
		}
	}


	/**
	 * 外部倉庫在庫数変更
	 * @param list
	 * @throws DaoException
	 */
	public void updateExternalWarehouseStockList(List<ExtendWarehouseStockDTO> list)
			throws DaoException {

		for (ExtendWarehouseStockDTO dto : list) {
			updateExternalStock(dto);
		}
	}

	/**
	 *
	 * DB上の最新倉庫情報に変動値を反映させます
	 * @param warehouseStockList
	 * @param map
	 * @return List<ExtendWarehouseStockDTO>
	 * @throws Exception
	 */
	public List<ExtendWarehouseStockDTO> getLatestWarehouseList(List<ExtendWarehouseStockDTO> warehouseStockList,
			Map<Long, Integer> map, Long sysItemId) throws Exception {


		ItemDAO itemDao = new ItemDAO();
		//DBからの最新倉庫情報
		List<ExtendWarehouseStockDTO> latestWarehouseStockList = new ArrayList<>();
		//最新情報+変動値を加えたもの
		List<ExtendWarehouseStockDTO> newWarehouseStockList = new ArrayList<>();
		latestWarehouseStockList = itemDao.getWarehouseStockList(sysItemId);

		//倉庫情報をDB上の最新情報に置き換える
		for (int i = 0; i < warehouseStockList.size(); i++) {
			ExtendWarehouseStockDTO warehouseStockDTO = warehouseStockList.get(i);
			String locationNo = warehouseStockDTO.getLocationNo();
			warehouseStockDTO = latestWarehouseStockList.get(i);
			warehouseStockDTO.setLocationNo(locationNo);
			warehouseStockDTO.setBeforeStockNum(warehouseStockDTO.getStockNum());
			//最新の在庫情報に変動値を加える
			for (Long key : map.keySet()) {
				if (key == warehouseStockDTO.getSysWarehouseId()) {
					warehouseStockDTO.setStockNum(warehouseStockDTO.getStockNum() + map.get(key));
				}
			}
			newWarehouseStockList.add(warehouseStockDTO);
		}

		return newWarehouseStockList;
	}


	/**
	 *
	 * DB上の最新倉庫情報に変動値を反映させます
	 * @param warehouseStockList
	 * @param map
	 * @return List<ExtendWarehouseStockDTO>
	 * @throws Exception
	 */
	public List<ExtendWarehouseStockDTO> getLatestExternalWarehouseList(List<ExtendWarehouseStockDTO> externalWarehouseStockList,
			Map<String, Integer> map, Long sysItemId) throws Exception {


		ItemDAO itemDao = new ItemDAO();
		//DBからの最新倉庫情報
		List<ExtendWarehouseStockDTO> latestExternalWarehouseStockList = new ArrayList<>();
		//最新情報+変動値を加えたもの
		List<ExtendWarehouseStockDTO> newExternalWarehouseStockList = new ArrayList<>();
		latestExternalWarehouseStockList = itemDao.getExternalStockList(sysItemId, null);

		//倉庫情報をDB上の最新情報に置き換える
		for (int i = 0; i < externalWarehouseStockList.size(); i++) {
			ExtendWarehouseStockDTO warehouseStockDTO = externalWarehouseStockList.get(i);
			warehouseStockDTO = latestExternalWarehouseStockList.get(i);
			warehouseStockDTO.setBeforeStockNum(warehouseStockDTO.getStockNum());
			//最新の在庫情報に変動値を加える
			for (String key : map.keySet()) {
				if (warehouseStockDTO.getSysExternalWarehouseCode() != null && StringUtils.equals(key, warehouseStockDTO.getSysExternalWarehouseCode())) {
					warehouseStockDTO.setStockNum(warehouseStockDTO.getStockNum() + map.get(key));
				}
			}
			newExternalWarehouseStockList.add(warehouseStockDTO);
		}

		return newExternalWarehouseStockList;
	}


	/**
	 * 倉庫在庫更新
	 * @param dto
	 * @throws DaoException
	 */
	public void updateWarehouseStock(ExtendWarehouseStockDTO dto)
			throws DaoException {

		new ItemDAO().updateWarehouseStock(dto);
	}


	/**
	 * 楽天倉庫在庫更新
	 * @param dto
	 * @throws DaoException
	 */
	public void updateExternalStock(ExtendWarehouseStockDTO dto)
			throws DaoException {

		new ItemDAO().updateExternalStock(dto);
	}


	// ■入荷予定
	public void registryArrivalScheduleList(
			List<ExtendArrivalScheduleDTO> addArrivalScheduleList,
			long sysItemId) throws DaoException {

		for (ExtendArrivalScheduleDTO dto : addArrivalScheduleList) {

			// 入荷予定日・入荷数が入っていない場合登録しない
			if (StringUtils.isEmpty(dto.getArrivalScheduleDate())
					|| dto.getArrivalNum() == 0) {

				continue;
			}

			dto.setSysArrivalScheduleId(new SequenceDAO()
					.getMaxSysArrivalScheduleId() + 1);
			dto.setSysItemId(sysItemId);
			registryArrivalSchedule(dto);
		}

	}

	private void registryArrivalSchedule(ExtendArrivalScheduleDTO dto)
			throws DaoException {

		ItemDAO dao = new ItemDAO();

		dto.setSysArrivalScheduleId(new SequenceDAO()
				.getMaxSysArrivalScheduleId() + 1);
		dao.registryArrivalSchedule(dto);
	}

	private void resetArrivalSchedule(ExtendArrivalScheduleDTO dto, long sysArrivalScheduleId)
			throws DaoException {

		ItemDAO dao = new ItemDAO();

		dto.setSysArrivalScheduleId(sysArrivalScheduleId);
		dao.resetArrivalSchedule(dto);
	}

	public void updateArrivalScheduleList(
			List<ExtendArrivalScheduleDTO> arrivalScheduleList, long sysItemId)
			throws DaoException {

		for (ExtendArrivalScheduleDTO dto : arrivalScheduleList) {

			// 部分入荷の場合
			if (StringUtils.equals(dto.getArrivalFlag(), "1")
					&& dto.getArrivalNum() < dto.getArrivalScheduleNum()) {

				dto.setSysItemId(sysItemId);

				/*部分入荷の際にdtoのArrivalNumを1度書き換える為、
				 * dto.getArrivalNumの一時格納先として用意し格納*/
				int arrivalNum = dto.getArrivalNum();

				/*元の入荷予定数から入荷後の入荷予定数を差し引いて入荷数を算出したものを格納*/
				dto.setArrivalNum(dto.getArrivalScheduleNum() - dto.getArrivalNum());

				registryArrivalHistory(dto);

				/*部分入荷の際に書き換えたArrivalNumを元の値に戻します*/
				dto.setArrivalNum(arrivalNum);
				// dto.setArrivalFlag("0");

				// dto.setArrivalNum(dto.getArrivalScheduleNum() -
				// dto.getArrivalNum());
				// 残り入荷数を更新かける。
				new ItemDAO().updateArrivalNum(dto);

				continue;

				// 入荷予定数より入荷数が多い場合
			} else if (StringUtils.equals(dto.getArrivalFlag(), "1")
					&& dto.getArrivalNum() > dto.getArrivalScheduleNum()) {

				dto.setArrivalScheduleNum(dto.getArrivalNum());
			}
			updateArrivalSchedule(dto);
		}
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
		registryDTO.setArrivalNum(dto.getArrivalNum());
		registryDTO.setSysItemId(dto.getSysItemId());
		registryDTO.setItemOrderDate(dto.getItemOrderDate());
		registryDTO.setArrivalScheduleDate(dto.getArrivalScheduleDate());
		registryDTO.setSysArrivalScheduleId(new SequenceDAO()
				.getMaxSysArrivalScheduleId() + 1);
		// dto.setArrivalFlag("1");

		new ItemDAO().registryArrivalHistory(registryDTO);
	}

	private void updateArrivalSchedule(ExtendArrivalScheduleDTO dto)
			throws DaoException {

		new ItemDAO().updateArrivalSchedule(dto);
	}

	// ■バックオーダー
	public void registryBackOrderList(List<BackOrderDTO> addBackOrderList,
			long sysItemId) throws DaoException {

		for (BackOrderDTO dto : addBackOrderList) {

			// 販売チャネル・会社が入っていない場合登録しない
			if (dto.getSysChannelId() == 0 || dto.getSysCorporationId() == 0) {

				continue;
			}

			dto.setSysBackOrderId(new SequenceDAO().getMaxSysBackOrderId() + 1);
			dto.setSysItemId(sysItemId);
			registryBackOrder(dto);
		}
	}

	private void registryBackOrder(BackOrderDTO dto) throws DaoException {

		ItemDAO dao = new ItemDAO();

		dao.registryBackOrder(dto);
	}

	public void updateBackOrderList(List<BackOrderDTO> backOrderList)
			throws DaoException {

		for (BackOrderDTO dto : backOrderList) {

			updateBackOrder(dto);
		}
	}

	private void updateBackOrder(BackOrderDTO dto) throws DaoException {

		new ItemDAO().updateBackOrder(dto);
	}

	// ■構成商品
	// 20140323 八鍬
	public void registrySetItemList(List<ExtendSetItemDTO> addSetItemList,
			long sysItemId) throws DaoException {

		for (ExtendSetItemDTO dto : addSetItemList) {

			// 構成システム商品ID・個数が入っていない場合登録しない
			if (dto.getFormSysItemId() == 0 || dto.getItemNum() == 0) {

				continue;
			}

			dto.setSysSetItemId(new SequenceDAO().getMaxSysSetItemId() + 1);
			dto.setSysItemId(sysItemId);
			registrySetItem(dto);
		}
	}

	// 20140323 八鍬
	private void registrySetItem(ExtendSetItemDTO dto) throws DaoException {

		ItemDAO dao = new ItemDAO();

		dao.registrySetItem(dto);
	}

	// 20140324 八鍬
	public void updateSetItemList(List<ExtendSetItemDTO> setItemList)
			throws DaoException {

		for (ExtendSetItemDTO dto : setItemList) {

			updateSetItem(dto);
		}
	}

	// 20140324 八鍬
	private void updateSetItem(ExtendSetItemDTO dto) throws DaoException {

		new ItemDAO().updateSetItem(dto);
	}

	/**
	 * 総在庫・仮在庫数更新
	 *
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void updateTotalStockNum(long sysItemId) throws DaoException {

		new ExcelImportDAO().stockSum(sysItemId);

		new ItemDAO().updateTemporaryStockNum(sysItemId);
	}



	public void registryFileUpLoadList(
			List<ExtendItemManualDTO> ItemManualList, List<ExtendItemManualDTO> addItemManualList, long sysItemId, ExtendMstItemDTO itemDto)
			throws IOException, DaoException {

		// 各種資料用フラグ変数を定義
		boolean manualFlg = false;
		boolean planSheetFlg = false;
		boolean otherDocumentFlg = false;

		for (ExtendItemManualDTO addDto : addItemManualList) {

			// 何も入っていないか、exe等のファイルだった場合アップロードしない
			if (addDto.getManualFile() == null
					|| StringUtils.equals(addDto.getManualFile().getContentType(),
							"application/octet-stream")
					|| addDto.getDocumentType().equals("")) {
				// if (dto.getManualFile() == null &&
				// !StringUtils.equals(dto.getManualFile().getContentType(),"application/pdf"))
				// {
				continue;
			}

			// ファイルアップロード
			if (addDto.getManualFile() != null
					&& !StringUtils.equals(
							addDto.getManualFile().getContentType(),
							"application/octet-stream")) {

				// pdfファイルとJPEG・PNG画像の場合アップロード
				if (checkContentType(addDto.getManualFile())) {

					addDto.setSysItemId(sysItemId);
					addDto.setSysItemManualId(new SequenceDAO()
							.getMaxSysItemManualId() + 1);
					fileUpload(addDto);

					// アップロードするファイルの資料区分を確認し、各フラグ変数を更新する
					if (addDto.getDocumentType().equals(WebConst.DOCUMENT_TYPE_FLG0)) {
						manualFlg = true;
					}
					if (addDto.getDocumentType().equals(WebConst.DOCUMENT_TYPE_FLG1)) {
						planSheetFlg = true;
					}
					if (addDto.getDocumentType().equals(WebConst.DOCUMENT_TYPE_FLG2)) {
						otherDocumentFlg = true;
					}

					new ItemDAO().registryItemManual(addDto);
				}
			}
		}

		// 登録済みのファイルの資料区分を確認し、M_商品に各資料フラグをセットする。
		for (ExtendItemManualDTO dto : ItemManualList) {

			// 登録済みのファイルがない場合、ループを抜ける。
			if (dto == null) {
				break;
			}

			// 削除フラグがセットされているものは資料区分確認の対象外とする。
			if (dto.getDeleteFlag().equals("1")) {
				continue;
			}

			// 登録済みのファイルの資料区分を確認し、各フラグ変数を更新する。
			if (dto.getDocumentType().equals(WebConst.DOCUMENT_TYPE_FLG0)) {
				manualFlg = true;
			}
			if (dto.getDocumentType().equals(WebConst.DOCUMENT_TYPE_FLG1)) {
				planSheetFlg = true;
			}
			if (dto.getDocumentType().equals(WebConst.DOCUMENT_TYPE_FLG2)) {
				otherDocumentFlg = true;
			}
		}

		// 説明書が一件でも登録されている場合、M_商品の説明書フラグをセットする。
		if (manualFlg) {
			itemDto.setManualFlg(WebConst.DOCUMENT_TYPE_FLG1);
		} else {
			itemDto.setManualFlg(WebConst.DOCUMENT_TYPE_FLG0);
		}

		// 図面が一件でも登録されている場合、M_商品の図面フラグをセットする。
		if (planSheetFlg) {
			itemDto.setPlanSheetFlg(WebConst.DOCUMENT_TYPE_FLG1);
		} else {
			itemDto.setPlanSheetFlg(WebConst.DOCUMENT_TYPE_FLG0);
		}

		// その他資料が一件でも登録されている場合、M_商品のその他資料フラグをセットする。
		if (otherDocumentFlg) {
			itemDto.setOtherDocumentFlg(WebConst.DOCUMENT_TYPE_FLG1);
		} else {
			itemDto.setOtherDocumentFlg(WebConst.DOCUMENT_TYPE_FLG0);
		}

		new ItemDAO().updateItem(itemDto);
	}

	/**
	 * pdfファイルとJPEG・PNG画像かどうかチェック
	 *
	 * @param manualFile
	 * @return pdfファイルとJPEG・PNG画像の場合true それ以外false
	 */

	private boolean checkContentType(FormFile manualFile) {

		// pdfファイルとJPEG・PNG画像の場合アップロード
		if (StringUtils.equals(manualFile.getContentType(), "application/pdf")
				|| StringUtils
						.equals(manualFile.getContentType(), "image/jpeg")
				|| StringUtils.equals(manualFile.getContentType(), "image/png")) {

			return true;
		}

		return false;
	}

	private void fileUpload(ExtendItemManualDTO dto)
			throws FileNotFoundException, IOException {

		String fileName = String.valueOf(dto.getSysItemManualId()) + "_" + dto.getManualFile().getFileName();

		String putFilePath = SystemSetting.get("MANUAL_PATH") + fileName;

		// ファイルを保存先フォルダに格納
		InputStream inStream = dto.getManualFile().getInputStream();
		BufferedInputStream inBuffer = new BufferedInputStream(inStream);
		BufferedOutputStream outBuffer = new BufferedOutputStream(
				new FileOutputStream(putFilePath));
		int contents = 0;
		while ((contents = inBuffer.read()) != -1) {
			outBuffer.write(contents);
		}

		outBuffer.flush();
		outBuffer.close();
		inBuffer.close();
		dto.getManualFile().destroy();
		dto.setManualPath((SystemSetting.get("DISP_MANUAL_PATH") + fileName));
	}

	public void updateItemManualList(List<ExtendItemManualDTO> itemManualList)
			throws FileNotFoundException, IOException, DaoException {

		for (ExtendItemManualDTO dto : itemManualList) {

			// 画面上で削除が行われた場合、資料を論理削除する。
			if (StringUtils.equals(dto.getDeleteFlag(), "1")) {
				String manualPath = dto.getManualPath();
				File manualFile = new File(changeItemManual(manualPath));
				manualFile.delete();
				new ItemDAO().deleteItemManual(dto);
				continue;
			}

			// 何も入っていない場合アップロードしない
			if (dto.getManualFile() == null) {
				continue;
			}

			// pdfファイルとJPEG・PNG画像の場合アップロード
			if (checkContentType(dto.getManualFile())) {

				fileUpload(dto);
			}
			new ItemDAO().updateItemManual(dto);
		}
	}

	/**
	 * [概要]不良在庫リストを登録する
	 * @param deadStockList
	 * @param addDeadStockList
	 * @param sysItemId
	 * @param mstItemDTO
	 * @throws DaoException
	 */
	public void registryDeadStocklist(List<DeadStockDTO> deadStockList,
			List<DeadStockDTO> addDeadStockList, long sysItemId,
			ExtendMstItemDTO mstItemDTO) throws DaoException {

		//不良在庫があるかどうかを格納するフラグ変数
		boolean deadStockFlg = false;

		//追加用不良在庫リストの内容を確認する。
		for (DeadStockDTO addDto : addDeadStockList) {

			if (StringUtils.isEmpty(addDto.getDeadReason()) || addDto.getItemNum() == 0) {
				continue;
			}

			//不良在庫を登録する
			registryDeadStock(addDto, sysItemId);
			deadStockFlg = true;
		}

		//表示用不良在庫リストの内容を確認する
		for (DeadStockDTO dto : deadStockList) {

			//登録済みのファイルがない場合、ループを抜ける。
			if (dto == null) {
				break;
			}

			//削除フラグがセットされている物は不良在庫確認の対象外とする
			if (dto.getDeleteFlag().equals("1")) {
				continue;
			}

			deadStockFlg = true;
		}

		//不良在庫が一つでもある場合、不良在庫フラグに1をセットし、一つもない場合0にする
		if (deadStockFlg) {
			mstItemDTO.setDeadStockFlag("1");
		} else {
			mstItemDTO.setDeadStockFlag("0");
		}

		new ItemDAO().updateItem(mstItemDTO);

	}

	/**
	 * [概要]不良在庫を登録する
	 * @param dto
	 * @param sysItemId
	 * @throws DaoException
	 */
	private void registryDeadStock(DeadStockDTO dto, long sysItemId)
			throws DaoException {

		dto.setSysItemId(sysItemId);

		//新規のシステム不良在庫IDを生成する
		dto.setSysDeadStockId(new SequenceDAO().getMaxSysDeadStockId() + 1);

		new ItemDAO().registryDeadStock(dto);
	}

	/**
	 * [概要]不良在庫リストを取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<DeadStockDTO> getDeadStockList(long sysItemId) throws DaoException {

		return new ItemDAO().getDeadStockList(sysItemId);
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
	 * 楽天倉庫出庫予定商品の総在庫を取得します
	 *
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public int getExternalWarehouseStockNum(long sysItemId, String sysExternalWarehouseCode) throws DaoException {

		// 倉庫在庫を取得する。
		ExtendWarehouseStockDTO rslData = new ItemDAO().getSysExternalStockId(sysItemId, sysExternalWarehouseCode);

		// 楽天倉庫が取得できなかったということは、商品に対して楽天倉庫が登録されていないことになる。
		if (rslData == null) {
			return 0;
		}

		int totalStock = rslData.getStockNum();

		return totalStock;
	}

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
	 * 外部倉庫出庫予定商品の仮在庫を取得するサービスメソッド
	 *
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public int getExternalTemporaryStockNum(long sysItemId, String sysExternalWarehouseCode) throws DaoException {

		// 倉庫在庫を取得する。
		ExtendWarehouseStockDTO rslData = new ItemDAO().getSysExternalStockId(sysItemId, sysExternalWarehouseCode);

		// 在庫数が取得できなかった場合、商品に対して楽天倉庫の在庫数が設定されていなかったことになる。
		if (rslData == null) {
			return 0;
		}

		int temporaryStockNum = rslData.getStockNum();

		List<ExtendKeepDTO> keepList = getExternalKeepNumList(sysItemId, sysExternalWarehouseCode);

		for (ExtendKeepDTO dto : keepList) {

			temporaryStockNum -= dto.getKeepNum();
		}

		return temporaryStockNum;
	}

	// 倉庫在庫を０で更新します
	private void warehouseStockEmpty(ExtendWarehouseStockDTO dto)
			throws DaoException {

		dto.setStockNum(0);
		updateWarehouseStock(dto);
	}

	/**
	 *
	 * 2014/03/19 八鍬
	 *
	 * @throws DaoException
	 */
	public ErrorMessageDTO checkItemList(List<SysItemIdDTO> itemList)
			throws DaoException {

		if (itemList == null || itemList.size() <= 0) {

			ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("該当する検索結果はありません。");
			return errorMessageDTO;
		}

		return new ErrorMessageDTO();
	}

	/**
	 * 注文アラート、在庫アラートを表示する対象がいないか確認
	 * 対象がある場合検索結果一覧画面上部にメッセージを表示する。
	 * 2014/03/19 八鍬
	 *
	 * @throws DaoException
	 */
	public ErrorMessageDTO checkOrderAlertNum(List<ResultItemSearchDTO> itemList)
			throws DaoException {
		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
		for (ResultItemSearchDTO dto : itemList) {

			if (dto.getOrderAlertNum() >= dto.getTotalStockNum()) {

				errorMessageDTO.setSuccess(false);
				errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
				return errorMessageDTO;
			}

			if (dto.getItemLeadTime() == null) {
				if (dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
					errorMessageDTO.setSuccess(false);
					errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
					return errorMessageDTO;
				}
			} else {
				switch (dto.getItemLeadTime()) {
				case WebConst.LEAD_TIME_CODE_0:
					if (ITEM_LEAD_NUM1 * dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				case WebConst.LEAD_TIME_CODE_1:
					if (ITEM_LEAD_NUM1_5 * dto.getAnnualAverageSalesNum() >= (double) dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				case WebConst.LEAD_TIME_CODE_2:
					if (ITEM_LEAD_NUM2 * dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				case WebConst.LEAD_TIME_CODE_3:
					if (ITEM_LEAD_NUM3 * dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				case WebConst.LEAD_TIME_CODE_4:
					if (ITEM_LEAD_NUM4 * dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				case WebConst.LEAD_TIME_CODE_5:
					if (ITEM_LEAD_NUM5 * dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				case WebConst.LEAD_TIME_CODE_6:
					if (ITEM_LEAD_NUM6 * dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				case "":
					if (dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;

				default:
					if (dto.getAnnualAverageSalesNum() >= dto.getTotalStockNum()) {
						errorMessageDTO.setSuccess(false);
						errorMessageDTO.setErrorMessage("発注が必要な商品があります。");
						return errorMessageDTO;
					}
					break;
				}
			}
		}

		return new ErrorMessageDTO();
	}

	/**
	 *
	 * 2014/03/21 八鍬
	 *
	 * @throws DaoException
	 */
	public void stockNumLoad(List<ResultItemSearchDTO> itemList)
			throws DaoException {

		for (ResultItemSearchDTO dto : itemList) {

			// if(dto.getUpdateStockNum() < 0) {
			// dto.setUpdateStockNum(dto.getUpdateStockNum() * -1);
			// }
			lumpUpdateStock(dto.getSysItemId(), dto.getUpdateStockNum());

			/* 組立可数プロシージャ化のため処理を凍結 20171107 y_saito*/
			//セット商品の組立可数・構成部品も含めて総計算
//			setAllAssemblyNum(dto.getSysItemId());
		}
	}

	/**
	 * 倉庫在庫数を変更した後、総在庫を更新します
	 *
	 * 減らす場合　優先度の高い倉庫から順に減らします 増やす場合　優先度１の倉庫の在庫を増やします
	 *
	 * 2014/03/21 作成　八鍬 2014/03/23 コメント追加　伊東
	 *
	 * @param sysItemId
	 * @param orderNum
	 *            減らす場合正の整数 増やす場合負の整数
	 * @throws DaoException
	 */
	public void lumpUpdateStock(long sysItemId, int updateStockNum)
			throws DaoException {

		//変更前倉庫在庫リスト
		List<ExtendWarehouseStockDTO> beforeList = getWarehouseStockList(sysItemId);
		for (int i = 0; i < beforeList.size(); i++) {
			beforeList.get(i).setBeforeStockNum(beforeList.get(i).getStockNum());
		}

		// 倉庫在庫を更新します
		for (ExtendWarehouseStockDTO dto : beforeList) {

			// 第一倉庫在庫数より増減値のほうが多い場合
			// 第一倉庫在庫数を0にし、増減値も減らした後に継続
			if (updateStockNum > dto.getStockNum()) {
				updateStockNum -= dto.getStockNum();
				warehouseStockEmpty(dto);
				continue;
			}

			// 増減値より第一倉庫在庫数のほうが多い場合
			// 第一倉庫在庫数を減らし終了
			if (updateStockNum <= dto.getStockNum()) {
				dto.setStockNum(dto.getStockNum() - updateStockNum);
				updateWarehouseStock(dto);
				break;
			}
		}
		//変更後倉庫在庫リスト
		List<ExtendWarehouseStockDTO> afterList = getWarehouseStockList(sysItemId);
		//変更履歴更新
		insertWarehouseInfo(afterList, beforeList, UPDAET_TYPE);
		// 総在庫数を更新します
		updateTotalStockNum(sysItemId);
	}

	/**
	 * [外部倉庫用]
	 * 倉庫在庫数を変更した後、総在庫を更新します
	 *
	 * 減らす場合　優先度の高い倉庫から順に減らします 増やす場合　優先度１の倉庫の在庫を増やします
	 *
	 * @param sysItemId
	 * @param orderNum
	 *            減らす場合正の整数 増やす場合負の整数
	 * @throws DaoException
	 */
	public void lumpUpdateExternalStock(long sysItemId, int updateStockNum, String sysExternalWarehouseCode)
			throws DaoException {

		//変更前倉庫在庫リスト
		List<ExtendWarehouseStockDTO> beforeList = getExternalStockList(sysItemId, sysExternalWarehouseCode);
		for (int i = 0; i < beforeList.size(); i++) {
			beforeList.get(i).setBeforeStockNum(beforeList.get(i).getStockNum());
		}
		// 倉庫在庫を更新します
		for (ExtendWarehouseStockDTO before : beforeList) {

			// 増減値より楽天在庫数のほうが多い場合
			// 楽天倉庫の在庫数を減らし終了
			if (updateStockNum <= before.getStockNum()) {
				before.setStockNum(before.getStockNum() - updateStockNum);
				updateExternalStock(before);
			}
		}

		//変更後倉庫在庫リスト
		List<ExtendWarehouseStockDTO> afterList = getExternalStockList(sysItemId, sysExternalWarehouseCode);

		//変更履歴更新
		insertExternalWarehouseInfo(afterList, beforeList, UPDAET_TYPE);

		//楽天倉庫の場合、総在庫数や仮在庫数は商品の在庫数に含めない。
	}

	/**
	 * List分説明書を取ってくる
	 *
	 * @param itemManualList
	 */
	public void changeItemManualList(List<ExtendItemManualDTO> itemManualList) {

		for (ExtendItemManualDTO dto : itemManualList) {

			String manualPath = (changeItemManual(dto.getManualPath()));
			dto.setManualFileNameDisp(getItemManualFileName(manualPath));
		}
	}

	private String changeItemManual(String manualPath) {

		if (StringUtils.startsWith(manualPath,
				SystemSetting.get("DISP_MANUAL_PATH"))) {

			manualPath = manualPath.replace(
					SystemSetting.get("DISP_MANUAL_PATH"),
					SystemSetting.get("MANUAL_PATH"));
		}

		return manualPath;
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deleteItem(long sysItemId) throws DaoException {

		new ItemDAO().deleteItem(sysItemId);
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deleteWarehouseStock(long sysItemId) throws DaoException {

		new ItemDAO().deleteWarehouseStock(sysItemId);
	}

	public void deleteExternalStock(long sysItemId) throws DaoException {

		new ItemDAO().deleteExternalStock(sysItemId);
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deleteArrivalSchedule(long sysItemId) throws DaoException {

		new ItemDAO().deleteArrivalSchedule(sysItemId);
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deleteCost(long sysItemId) throws DaoException {

		new ItemDAO().deleteCost(sysItemId);
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deletePrice(long sysItemId) throws DaoException {

		new ItemDAO().deletePrice(sysItemId);
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deleteBackOrder(long sysItemId) throws DaoException {

		new ItemDAO().deleteBackOrder(sysItemId);
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deleteCodeCollation(long sysItemId) throws DaoException {

		new ItemDAO().deleteCodeCollation(sysItemId);
	}

	/**
	 *
	 * 2014/03/27 八鍬
	 *
	 * @throws DaoException
	 */
	public void deleteFormItem(long sysItemId) throws DaoException {

		new ItemDAO().deleteFormItem(sysItemId);
	}

	/**
	 *
	 * 2014/03/28 八鍬
	 *
	 * @throws DaoException
	 */
	public void lumpDeleteItem(List<ResultItemSearchDTO> itemList)
			throws DaoException {

		for (ResultItemSearchDTO dto : itemList) {

			if (StringUtils.equals(dto.getDeleteCheckFlg(), "1")) {
				// 商品削除
				deleteItem(dto.getSysItemId());
				// 倉庫在庫削除
				deleteWarehouseStock(dto.getSysItemId());
				// 入荷予定削除
				deleteArrivalSchedule(dto.getSysItemId());
				// 原価削除
				deleteCost(dto.getSysItemId());
				// 売価削除
				deletePrice(dto.getSysItemId());
				// バックオーダー削除
				deleteBackOrder(dto.getSysItemId());
				// 品番照合削除
				deleteCodeCollation(dto.getSysItemId());
				// 構成商品削除
				deleteFormItem(dto.getSysItemId());
				//年間販売数削除
				deleteAnnualSales(dto.getSysItemId());
				//海外注文書商品削除
				deleteForeignItem(dto.getSysItemId());
			}
		}
	}

	/**
	 *
	 * 2014/03/29 八鍬
	 *
	 * @throws DaoException
	 */
	public List<ExtendArrivalScheduleDTO> initLumpArrivalScheduleList() {

		List<ExtendArrivalScheduleDTO> list = new ArrayList<>();
		int max = 10;

		for (int i = 0; i < max; i++) {

			list.add(new ExtendArrivalScheduleDTO());
			list.get(i).setItemOrderDate(StringUtil.getToday());
		}
		return list;
	}

	/**
	 *
	 * 2014/03/29 八鍬
	 *
	 * @throws DaoException
	 */
	public void registryLumpArrivalSchedule(List<ExtendArrivalScheduleDTO> list)
			throws DaoException {

		for (ExtendArrivalScheduleDTO dto : list) {

			if (dto.getSysItemId() != 0) {

				dto.setSysArrivalScheduleId(new SequenceDAO()
						.getMaxSysArrivalScheduleId() + 1);
				registryArrivalSchedule(dto);
			}
		}
	}

	public void resetLumpArrivalSchedule(ExtendArrivalScheduleDTO dto, long sysArrivalScheduleId)
			throws DaoException {

		resetArrivalSchedule(dto, sysArrivalScheduleId);
	}

	// private void registryLumpArrivalSchedule(ExtendArrivalScheduleDTO dto)
	// throws DaoException {
	//
	// new ItemDAO().registryLumpArrivalSchedule(dto);
	// }

	public String getItemManualPath(long sysItemManualId) throws DaoException {

		// ExtendItemManualDTO dto = getItemManualDTO(sysItemManualId);
		ExtendItemManualDTO dto = new ItemDAO()
				.getItemManualDTO(sysItemManualId);

		if (dto == null) {
			return null;
		}
		return changeItemManual(dto.getManualPath());
	}

	/**
	 * 表示用のファイル名を取得します
	 *
	 * @param filePath
	 * @return
	 */
	public String getItemManualFileName(String filePath) {

		// pdfのフォルダ階層分除去
		String fileName = filePath.substring(SystemSetting.get("MANUAL_PATH")
				.length());

		// sysItemManualId_ファイル名の部分を除去
		// fileName = fileName.substring(fileName.indexOf("_") + 1);

		return fileName;
	}

	public ErrorMessageDTO checkWarehouseStock(
			List<ExtendWarehouseStockDTO> warehouseList,
			List<ExtendWarehouseStockDTO> addWarehouseStockList) {

		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

		// warehouseListとaddWarehouseStockListを一つのリストに格納し
		// 優先度・倉庫の重複をチェックします。
		List<ExtendWarehouseStockDTO> checkList = new ArrayList<>();

		// checkListを作成します
		for (ExtendWarehouseStockDTO dto : warehouseList) {
			checkList.add(dto);
		}
		for (ExtendWarehouseStockDTO dto : addWarehouseStockList) {
			checkList.add(dto);
		}

		int arrSize = (checkList.size());
		String[] arrPriority = new String[arrSize];
		long[] arrSysWarehouseId = new long[arrSize];
		int idx = 0;
		for (ExtendWarehouseStockDTO dto : checkList) {

			if (StringUtils.isEmpty(dto.getPriority())
					|| dto.getSysWarehouseId() == 0) {
				continue;
			}

			arrPriority[idx] = dto.getPriority();
			arrSysWarehouseId[idx++] = dto.getSysWarehouseId();
		}

		for (int i = 0; i < idx; i++) {
			// String priority = arrPriority[i];
			// long sysWarehouseId = arrSysWarehouseId[i];

			for (int j = i + 1; j < idx; j++) {

				if (StringUtils.equals(arrPriority[i], arrPriority[j])
						|| arrSysWarehouseId[i] == arrSysWarehouseId[j]) {
					errorMessageDTO.setSuccess(false);
					errorMessageDTO.setErrorMessage("優先度・倉庫名は重複登録できません");
					break;
				}
			}
		}

		return errorMessageDTO;
	}

	public ErrorMessageDTO checkKeep(List<ExtendKeepDTO> keepList,
			List<ExtendKeepDTO> addKeepList) {

		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

		// keepListとaddKeepListを一つのリストに格納し
		// 受注番号の重複をチェックします。
		List<ExtendKeepDTO> checkList = new ArrayList<>();

		// checkListを作成します
		for (ExtendKeepDTO dto : keepList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || StringUtils.equals(dto.getDeleteFlag(), "1")) {
				continue;
			}
			checkList.add(dto);
		}
		for (ExtendKeepDTO dto : addKeepList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || StringUtils.equals(dto.getDeleteFlag(), "1")) {
				continue;
			}
			checkList.add(dto);
		}

		int arrSize = (checkList.size());
		String[] arrOrderNo = new String[arrSize];
		int idx = 0;
		for (ExtendKeepDTO dto : checkList) {

			arrOrderNo[idx++] = dto.getOrderNo();
		}

		for (int i = 0; i < idx; i++) {

			for (int j = i + 1; j < idx; j++) {

				if (StringUtils.equals(arrOrderNo[i], arrOrderNo[j])) {
					errorMessageDTO.setSuccess(false);
					errorMessageDTO.setErrorMessage("キープの受注番号は重複登録できません");
					break;
				}
			}
		}
		return errorMessageDTO;
	}

	public ErrorMessageDTO checkExternalKeep(List<ExtendKeepDTO> keepExternalList,
			List<ExtendKeepDTO> addExternalKeepList) {

		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

		// keepExternalListとaddExternalKeepListを一つのリストに格納し
		// 受注番号の重複をチェックします。
		List<ExtendKeepDTO> checkList = new ArrayList<>();

		// checkListを作成します
		for (ExtendKeepDTO dto : keepExternalList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || StringUtils.equals(dto.getDeleteFlag(), "1")) {
				continue;
			}
			checkList.add(dto);
		}
		for (ExtendKeepDTO dto : addExternalKeepList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || StringUtils.equals(dto.getDeleteFlag(), "1")) {
				continue;
			}
			checkList.add(dto);
		}

		int arrSize = (checkList.size());
		String[] arrOrderNo = new String[arrSize];
		int idx = 0;
		for (ExtendKeepDTO dto : checkList) {

			arrOrderNo[idx++] = dto.getOrderNo();
		}

		for (int i = 0; i < idx; i++) {

			for (int j = i + 1; j < idx; j++) {

				if (StringUtils.equals(arrOrderNo[i], arrOrderNo[j])) {
					errorMessageDTO.setSuccess(false);
					errorMessageDTO.setErrorMessage("キープの受注番号は重複登録できません");
					break;
				}
			}
		}
		return errorMessageDTO;
	}
	/**
	 * 旧品番から新品番を取得します
	 *
	 * @param shopItemCd
	 *            旧品番
	 * @return 新品番
	 */
	public String getItemCd(String oldItemCd) throws DaoException {

		ExtendMstItemDTO dto = new ExtendMstItemDTO();
		dto.setOldItemCode(oldItemCd);

		dto = new ItemDAO().getMstItemDTO(dto);

		if (dto == null) {

			return null;
		}

		return dto.getItemCode();
	}
//TODO 削除してはいけません
//	/**
//	 * 一括入荷予定取得
//	 *
//	 * 在庫一覧以外から来た場合正しいデータきません。
//	 *
//	 * @param itemList
//	 * @return
//	 * @throws DaoException
//	 */
//	public List<ExtendArrivalScheduleDTO> getLumpArrivalList(
//			List<ResultItemSearchDTO> itemList) throws DaoException {
//
//		List<ExtendArrivalScheduleDTO> arrivalScheduleList = new ArrayList<>();
//
//		for (ResultItemSearchDTO dto : itemList) {
//
//			// 入荷予定がない場合
//			if (dto.getSysArrivalScheduleId() == 0) {
//				continue;
//			}
//
//			ExtendArrivalScheduleDTO arrivalScheduleDTO = new ItemDAO()
//					.getArrivalSchedule(dto.getSysArrivalScheduleId());
//
//			if (arrivalScheduleDTO == null) {
//				continue;
//			}
//
//			arrivalScheduleDTO.setSysWarehouseId(dto.getSysWarehouseId());
//			arrivalScheduleDTO.setItemCode(dto.getItemCode());
//			arrivalScheduleDTO.setItemNm(dto.getItemNm());
//			arrivalScheduleList.add(arrivalScheduleDTO);
//		}
//
//		return arrivalScheduleList;
//	}
//TODO 削除してはいけません
//	public void lumpArrival(List<ExtendArrivalScheduleDTO> lumpArrivalList)
//			throws DaoException {
//
//		for (ExtendArrivalScheduleDTO dto : lumpArrivalList) {
//
//			int arrivalNum = 0;
//
//			arrivalNum = dto.getArrivalNum();
//
//			// ■入荷処理あとできりわけるかも
//			ItemDAO itemDAO = new ItemDAO();
//			// 入荷予定より、入荷数が少ない場合
//			if (dto.getArrivalNum() < dto.getArrivalScheduleNum()) {
//
//				//入荷履歴を登録
//				registryArrivalHistory(dto);
//				// 入荷した分入荷予定数を減らして更新
//				dto.setArrivalNum(dto.getArrivalScheduleNum() - arrivalNum);
//				itemDAO.updateArrivalNum(dto);
//
//			} else {
//
//				dto.setArrivalFlag("1");
//				dto.setArrivalScheduleNum(arrivalNum);
//				itemDAO.updateArrivalSchedule(dto);
//			}
//
//			// ■在庫増処理あとできりわけるかも
//			List<ExtendWarehouseStockDTO> list = getWarehouseStockList(dto
//					.getSysItemId());
//
//			// for (ExtendWarehouseStockDTO warehouseStockDTO: list) {
//			String registryFlg = "0";
//			ExtendWarehouseStockDTO registryWarehouseStockDTO = new ExtendWarehouseStockDTO();
//
//			// 倉庫在庫が一つも無い場合登録フラグを立てる
//			if (list == null) {
//				registryFlg = "1";
//			}
//			for (int i = 0; i < list.size(); i++) {
//
//				ExtendWarehouseStockDTO warehouseStockDTO = list.get(i);
//				// 既に存在している倉庫在庫の場合
//				// 在庫数を増やす
//				if (warehouseStockDTO.getSysWarehouseId() == dto
//						.getSysWarehouseId()) {
//
//					warehouseStockDTO.setStockNum(warehouseStockDTO
//							.getStockNum() + arrivalNum);
//					itemDAO.updateWarehouseStock(warehouseStockDTO);
//					break;
//				}
//
//				// 入荷したい倉庫に倉庫在庫が無い場合登録フラグを立てる
//				if (i == list.size() - 1) {
//					registryFlg = "1";
//				}
//			}
//
//			// 新規登録の場合
//			if (StringUtils.equals(registryFlg, "1")) {
//
//				registryWarehouseStockDTO.setPriority(getMaxPriority(dto
//						.getSysItemId()));
//				registryWarehouseStockDTO.setStockNum(arrivalNum);
//				registryWarehouseStockDTO.setSysWarehouseId(dto
//						.getSysWarehouseId());
//				registryWarehouseStockDTO.setSysItemId(dto.getSysItemId());
//				registryWarehouseStock(registryWarehouseStockDTO);
//			}
//			updateTotalStockNum(dto.getSysItemId());
//
//			//セット商品の組立可数・構成部品も含めて総計算
//			setAllAssemblyNum(dto.getSysItemId());
//		}
//	}


//TODO 削除してはいけません
	// public void itemManualDownLoad(long sysItemManualId) throws
	// FileNotFoundException, DocumentException, DaoException {

	// ExtendItemManualDTO dto = getItemManualDTO(sysItemManualId);
	// ExtendItemManualDTO dto = new
	// ItemDAO().getItemManualDTO(sysItemManualId);
	//
	// if (dto == null) {
	// return;
	// }
	// dto.setManualPath(changeItemManual(dto.getManualPath()));

	// Document document = new Document(PageSize.A4, 5, 5, 40, 5);
	// PdfWriter.getInstance(document, new
	// FileOutputStream(dto.getManualPath()));
	// document.open();
	// PdfWriter.
	// PdfReader.
	// document.close();
	// }

	// private ExtendItemManualDTO getItemManualDTO(long sysItemManualId) throws
	// DaoException {
	//
	// ExtendItemManualDTO dto = new ExtendItemManualDTO();
	// dto.setSysItemManualId(sysItemManualId);
	//
	// return new ItemDAO().getItemManual(dto);
	// }
	private String getMaxPriority(long sysItemId) throws DaoException {

		List<ExtendWarehouseStockDTO> list = new ArrayList<>();
		list = new ItemDAO().getWarehouseStockList(sysItemId);

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

	public void registryKeepList(List<ExtendKeepDTO> addKeepList, long sysItemId)
			throws DaoException {

		List<ExtendKeepDTO> oldKeepList = getKeepList(sysItemId);
		List<ExtendKeepDTO> newKeepList = new ArrayList<>();
		List<ExtendKeepDTO> updateKeepList = new ArrayList<>();

		for (ExtendKeepDTO add : addKeepList) {

			if (StringUtils.isEmpty(add.getOrderNo()) || add.getKeepNum() == 0) {
				continue;
			}

			boolean isExist = false;
			for (ExtendKeepDTO old : oldKeepList) {
				if (StringUtils.equals(add.getOrderNo(), old.getOrderNo())) {
					isExist = true;
					break;
				}
			}
			if (isExist) {
				updateKeepList.add(add);
			}else {
				newKeepList.add(add);
			}
		}

		updateKeepList(updateKeepList);
		
		for (ExtendKeepDTO dto : newKeepList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || dto.getKeepNum() == 0) {
				continue;
			}
			registryKeep(dto, sysItemId);
		}

	}

	public void registryExternalKeepList(List<ExtendKeepDTO> addExternalKeepList, long sysItemId)
			throws DaoException {

		List<ExtendKeepDTO> oldExternalKeepList = getExternalKeepList(sysItemId);
		List<ExtendKeepDTO> newExternalKeepList = new ArrayList<>();
		List<ExtendKeepDTO> updateExternalKeepList = new ArrayList<>();

		for (ExtendKeepDTO add : addExternalKeepList) {

			if (StringUtils.isEmpty(add.getOrderNo()) || add.getKeepNum() == 0) {
				continue;
			}

			boolean isExist = false;
			for (ExtendKeepDTO old : oldExternalKeepList) {
				if (StringUtils.equals(add.getOrderNo(), old.getOrderNo())) {
					isExist = true;
					break;
				}
			}
			if (isExist) {
				updateExternalKeepList.add(add);
			}else {
				newExternalKeepList.add(add);
			}
		}

		updateExternalKeepList(updateExternalKeepList);

		for (ExtendKeepDTO dto : newExternalKeepList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || dto.getKeepNum() == 0) {
				continue;
			}
			registryExternalKeep(dto, sysItemId);
		}

	}

	private void registryKeep(ExtendKeepDTO dto, long sysItemId)
			throws DaoException {

		dto.setSysItemId(sysItemId);
		dto.setSysKeepId(new SequenceDAO().getMaxSysKeepId() + 1);

		new ItemDAO().registryKeep(dto);
	}

	private void registryExternalKeep(ExtendKeepDTO dto, long sysItemId)
			throws DaoException {

		dto.setSysItemId(sysItemId);
		dto.setSysExternalKeepId(new SequenceDAO().getMaxSysExternalKeepId() + 1);
		// TODO 楽天倉庫以外の外部倉庫が出てきたら倉庫コードを選択する処理に改修が必要です。
		dto.setSysExternalWarehouseCode("RSL");

		new ItemDAO().registryExternalKeep(dto);
	}

	public List<ExtendKeepDTO> getKeepList(long sysItemId) throws DaoException {

		return new ItemDAO().getKeepList(sysItemId);
	}

	/**
	 * 外部倉庫のキープ個数を取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getExternalKeepList(long sysItemId) throws DaoException {

		return new ItemDAO().getExternalKeepList(sysItemId);
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
	 * 外部倉庫のキープ数を取得する。
	 * @param sysItemId
	 * @return 実行結果
	 * @throws DaoException
	 */
	public List<ExtendKeepDTO> getExternalKeepNumList(long sysItemId, String sysExternalWarehouseCode) throws DaoException {

		return new ItemDAO().getExternalKeepNumList(sysItemId, sysExternalWarehouseCode);
	}




	public void updateKeepList(List<ExtendKeepDTO> keepList)
			throws DaoException {

		for (ExtendKeepDTO dto : keepList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || dto.getKeepNum() == 0) {
				continue;
			}

			updateKeep(dto);
			if (StringUtils.equals(dto.getDeleteFlag(), "1")) {
				deleteKeep(dto.getSysKeepId());
			}
		}
	}

	public void updateExternalKeepList(List<ExtendKeepDTO> externalKeepList)
			throws DaoException {

		for (ExtendKeepDTO dto : externalKeepList) {

			if (StringUtils.isEmpty(dto.getOrderNo()) || dto.getKeepNum() == 0) {
				continue;
			}

			updateExternalKeep(dto);
			if (StringUtils.equals(dto.getDeleteFlag(), "1")) {
				deleteExternalKeep(dto.getSysExternalKeepId());
			}
		}
	}

	public void deleteKeep(long sysKeepId) throws DaoException {

		new ItemDAO().deleteKeep(sysKeepId);
	}


	public void deleteExternalKeep(long sysExternalKeepId) throws DaoException {

		new ItemDAO().deleteExternalKeep(sysExternalKeepId);
	}

	public void deleteExternalKeepOfItem(long sysItemId) throws DaoException {

		new ItemDAO().deleteExternalKeep(sysItemId);
	}



	private void updateKeep(ExtendKeepDTO dto) throws DaoException {

		new ItemDAO().updateKeep(dto);
	}

	private void updateExternalKeep(ExtendKeepDTO dto) throws DaoException {

		new ItemDAO().updateExternalKeep(dto);
	}

	/**
	 * 受注番号に紐づくキープを取得します。
	 * @param orderNo
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public long getSysKeepId(String orderNo, long sysItemId)
			throws DaoException {

		ExtendKeepDTO dto = new ExtendKeepDTO();
		dto.setOrderNo(orderNo);
		dto.setSysItemId(sysItemId);

		dto = new ItemDAO().getKeepDTO(dto);

		if (dto == null) {
			return 0;
		}

		return dto.getSysKeepId();
	}

	/**
	 * [外部倉庫用]
	 * 受注番号に紐づくキープを取得します。
	 * @param orderNo
	 * @param sysItemId
	 * @param sysExternalWarehouseCode
	 * @return
	 * @throws DaoException
	 */
	public long getExternalSysKeepId(String orderNo, long sysItemId, String sysExternalWarehouseCode)
			throws DaoException {

		ExtendKeepDTO dto = new ExtendKeepDTO();
		dto.setOrderNo(orderNo);
		dto.setSysItemId(sysItemId);

		dto = new ItemDAO().getExternalKeepDTO(dto, sysExternalWarehouseCode);

		if (dto == null) {
			return 0;
		}

		return dto.getSysExternalKeepId();
	}

	/**
	 * 商品IDと倉庫IDから倉庫在庫IDを取得します
	 *
	 * @param sysItemId
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public long getSysWarehouseStockId(long sysItemId, long sysWarehouseId)
			throws DaoException {

		ItemDAO itemDAO = new ItemDAO();

		ExtendWarehouseStockDTO dto = itemDAO.getSysWarehouseStockId(sysItemId,
				sysWarehouseId);
		if (dto == null) {
			return 0;
		}

		return dto.getSysWarehouseStockId();
	}

	/**
	 * 全セット商品組立可数総計算。 20171107 y_saito
	 * @throws DaoException
	 */
	public int setAllSetItemAssemblyNum() throws DaoException {

		/*全セット商品組立可数計算*/
		int result = new ItemDAO().setAllAssemblyNum();
		return result;
	}

	/**
	 * すでに探索済みのシステムIDを探索しようとしていたら、そこは探索から除外する。
	 *
	 */
	public boolean checkSeachedItemId(Long sysItemId, List<Long> calcSysItemIdList) {

		for (int i = 0; i < calcSysItemIdList.size(); i++) {
			Long checkedSysItemId = calcSysItemIdList.get(i);

			if (sysItemId.equals(checkedSysItemId)) {
				return false;
			}

		}

		return true;
	}

	/**
	 * [概要]更新履歴リストを取得する
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public List<UpdateDataHistoryDTO> getUpdateDataHistoryList(long sysItemId) throws DaoException {

		return new ItemDAO().getUpdateDataHistoryList(sysItemId);
	}

	/**
	 * [概要]更新情報を登録する(セット商品用)
	 * @param form
	 */
	public UpdateDataHistoryDTO registryUpdateData(String afterInfo, String beforeInfo, String updTarget, long sysItemId, String updateType) {

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
			if (updateType.equals(UPDAET_TYPE)) {
				updateHistory += " 出庫 ";
			}
			updateHistory += updTarget + "に[" + afterInfo;
			updateHistory += "] を登録しました。";
		} else {
			updateHistory += " [" + sdf1.format(date) + "]";
			if (updateType.equals(UPDAET_TYPE)) {
				updateHistory += " 出庫 ";
			}
			updateHistory += updTarget + "を [" + beforeInfo + "]→[" + afterInfo;
			updateHistory += "]に変更しました。";
		}

		//更新履歴設定
		dto.setUpdateHistory(updateHistory);
		//システム商品ID設定
		dto.setSysItemId(sysItemId);
		//更新者ID設定
		dto.setUpdateUserId(userInfo.getUserId());
		//登録者ID設定
		dto.setCreateUserId(userInfo.getUserId());

		return dto;
	}

	/**
	 * [概要]不良在庫リストを更新する
	 * @param deadStockList
	 * @throws DaoException
	 */
	public void updateDeadStocklist(List<DeadStockDTO> deadStockList) throws DaoException {

		for (DeadStockDTO dto : deadStockList) {

			if (StringUtils.isEmpty(dto.getDeadReason()) || dto.getItemNum() == 0) {
				continue;
			}

			updateDeadStock(dto);
			if (StringUtils.equals(dto.getDeleteFlag(), "1")) {
				deleteDeadStock(dto.getSysDeadStockId());
			}
		}
	}

	/**
	 * [概要]不良在庫を削除する
	 * @param sysDeadStockId
	 * @throws DaoException
	 */
	public void deleteDeadStock(long sysDeadStockId) throws DaoException {

		new ItemDAO().deleteDeadStock(sysDeadStockId);
	}

	/**
	 * [概要]年間販売数を削除する
	 * @param sysDeadStockId
	 * @throws DaoException
	 */
	public void deleteAnnualSales(long sysItemId) throws DaoException {

		new ItemDAO().deleteAnnualSales(sysItemId);
	}

	/**
	 * [概要]海外注文商品を削除する
	 * @param sysItemId
	 * @throws DaoException
	 */
	public void deleteForeignItem(long sysItemId) throws DaoException {

		new ItemDAO().deleteForeignItem(sysItemId);
	}

	/**
	 * [概要]更新者情報を更新する
	 * @param sysDeadStockId
	 * @throws DaoException
	 */
	public void updateInfo(long sysItemId) throws DaoException {

		new ItemDAO().updateInfo(sysItemId);
	}

	/**
	 * [概要]不良在庫を更新する
	 * @param dto
	 * @throws DaoException
	 */
	private void updateDeadStock(DeadStockDTO dto) throws DaoException {

		new ItemDAO().updateDeadStock(dto);
	}

	/**
	 * [概要]仕入先リストを取得する
	 * @param sysSupplierId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstSupplierDTO> getSupplierList(long sysSupplierId) throws DaoException {

		List<ExtendMstSupplierDTO> list = new ItemDAO().getSupplierList(sysSupplierId);

		return list;
	}

	/**
	 * [概要]仕入先情報を取得する
	 * @param sysSupplierId
	 * @return
	 * @throws DaoException
	 */
	public ExtendMstSupplierDTO getSupplier(long sysSupplierId) throws DaoException {

		ExtendMstSupplierDTO dto = new ItemDAO().getSupplier(sysSupplierId);

		return dto;
	}

	/**
	 * 更新情報用に取得してきた値を「変更前」変数に格納：商品マスタ
	 * @param dto
	 * @param form
	 * @throws DaoException
	 */
	public void setBeforItemInfo(ExtendMstItemDTO form) throws DaoException {
		ExtendMstItemDTO dto = form;

		form.setBeforeAssemblyNum(dto.getAssemblyNum());
		form.setBeforeCarModel(dto.getCarModel());
		form.setBeforeDocumentRemarks(dto.getDocumentRemarks());
		form.setBeforeFactoryItemCode(dto.getFactoryItemCode());
		form.setBeforeForeignItemNm(dto.getForeignItemNm());
		form.setBeforeItemCode(dto.getItemCode());
		form.setBeforeItemLeadTime(dto.getItemLeadTime());
		form.setBeforeItemNm(dto.getItemNm());
		form.setBeforeLoading(dto.getLoading());
		form.setBeforeMakerNm(dto.getMakerNm());
		form.setBeforeMinimumOrderQuantity(dto.getMinimumOrderQuantity());
		form.setBeforeModel(dto.getModel());
		form.setBeforeOldItemCode(dto.getOldItemCode());
		form.setBeforeOrderAlertNum(dto.getOrderAlertNum());
		form.setBeforeOrderNum(dto.getOrderNum());
		form.setBeforePackingSize(dto.getPackingSize());
		form.setBeforePurchaceCost(dto.getPurchaceCost());
		form.setBeforePurchaceCountry(dto.getPurchaceCountry());
		form.setBeforePurchacePrice(dto.getPurchacePrice());
		form.setBeforeSpecMemo(dto.getSpecMemo());
		form.setBeforeSysSupplierId(dto.getSysSupplierId());
		form.setBeforeTemporaryStockNum(dto.getTemporaryStockNum());
		form.setBeforeTotalStockNum(dto.getTotalStockNum());
		form.setBeforeWeight(dto.getWeight());
	}

	/**
	 * 更新情報用に取得してきた値を「変更前」変数に格納:倉庫情報
	 * @param dto
	 * @param form
	 * @throws DaoException
	 */
	public void setBeforWarehouseInfo(List<ExtendWarehouseStockDTO> form) throws DaoException {
		for (int i = 0; i < form.size(); i ++ ) {
			form.get(i).setBeforeStockNum(form.get(i).getStockNum());
			form.get(i).setBeforeLocationNo(form.get(i).getLocationNo());
			form.get(i).setBeforePriority(form.get(i).getPriority());
		}
	}


	/**
	 * 更新情報用に取得してきた値を「変更前」変数に格納:外部倉庫情報
	 * @param dto
	 * @param form
	 * @throws DaoException
	 */
	public void setBeforExternalWarehouseInfo(List<ExtendWarehouseStockDTO> form) throws DaoException {
		for (int i = 0; i < form.size(); i ++ ) {
			form.get(i).setBeforeStockNum(form.get(i).getStockNum());
		}
	}


	/**
	 * 更新情報を作成する:商品マスタ
	 * @param afterDTO
	 * @param beforeDto
	 * @return
	 * @throws DaoException
	 */
	public void insertItemInfo(ExtendMstItemDTO afterDTO, ExtendMstItemDTO beforeDTO)
			throws DaoException {

		//インスタンス生成
		SequenceDAO seqDao = new SequenceDAO();
		UpdateDataHistoryDAO dao = new UpdateDataHistoryDAO();
		List<UpdateDataHistoryDTO> updHstry = new ArrayList<UpdateDataHistoryDTO>();

		//品番
		if (!afterDTO.getItemCode().equals(beforeDTO.getBeforeItemCode())) {
			updHstry.add(registryUpdateData(afterDTO.getItemCode(), beforeDTO.getBeforeItemCode(), "品番", afterDTO.getSysItemId(), "0"));
		}
		//旧品番
		if (!afterDTO.getOldItemCode().equals(beforeDTO.getBeforeOldItemCode())) {
			updHstry.add(registryUpdateData(afterDTO.getOldItemCode(), beforeDTO.getBeforeOldItemCode(), "旧品番", afterDTO.getSysItemId(), "0"));
		}
		//商品名
		if (!afterDTO.getItemNm().equals(beforeDTO.getBeforeItemNm())) {
			updHstry.add(registryUpdateData(afterDTO.getItemNm(), beforeDTO.getBeforeItemNm(), "商品名", afterDTO.getSysItemId(), "0"));
		}
		//車種
		if (!afterDTO.getCarModel().equals(beforeDTO.getBeforeCarModel())) {
			updHstry.add(registryUpdateData(afterDTO.getCarModel(), beforeDTO.getBeforeCarModel(), "車種", afterDTO.getSysItemId(), "0"));
		}
		//型式
		if (!afterDTO.getModel().equals(beforeDTO.getBeforeModel())) {
			updHstry.add(registryUpdateData(afterDTO.getModel(), beforeDTO.getBeforeModel(), "型式", afterDTO.getSysItemId(), "0"));
		}
		//メーカー
		if (!afterDTO.getMakerNm().equals(beforeDTO.getBeforeMakerNm())) {
			updHstry.add(registryUpdateData(afterDTO.getMakerNm(), beforeDTO.getBeforeMakerNm(), "メーカー", afterDTO.getSysItemId(), "0"));
		}
		//仕様メモ
		if (!afterDTO.getSpecMemo().equals(beforeDTO.getBeforeSpecMemo())) {
			updHstry.add(registryUpdateData(afterDTO.getSpecMemo(), beforeDTO.getBeforeSpecMemo(), "仕様メモ", afterDTO.getSysItemId(), "0"));
		}
		//在庫アラート
		if (afterDTO.getOrderAlertNum() != beforeDTO.getBeforeOrderAlertNum()) {
			updHstry.add(registryUpdateData(String.valueOf(afterDTO.getOrderAlertNum())
					,String.valueOf(beforeDTO.getBeforeOrderAlertNum()), "在庫アラート", afterDTO.getSysItemId(), "0"));
		}
		//商品リードタイム
		if (!afterDTO.getItemLeadTime().equals(beforeDTO.getBeforeItemLeadTime())) {
			updHstry.add(registryUpdateData(WebConst.ITEM_LEAD_TIME_MAP.get(afterDTO.getItemLeadTime())
					, WebConst.ITEM_LEAD_TIME_MAP.get(beforeDTO.getBeforeItemLeadTime()), "商品リードタイム", afterDTO.getSysItemId(), "0"));
		}
		//梱包サイズ
		if (!afterDTO.getPackingSize().equals(beforeDTO.getBeforePackingSize())) {
			updHstry.add(registryUpdateData(afterDTO.getPackingSize(), beforeDTO.getBeforePackingSize(), "梱包サイズ", afterDTO.getSysItemId(), "0"));
		}
		//重量
		if (afterDTO.getWeight() != beforeDTO.getBeforeWeight()) {
			updHstry.add(registryUpdateData(String.valueOf(afterDTO.getWeight())
					, String.valueOf(beforeDTO.getBeforeWeight()), "重量", afterDTO.getSysItemId(), "0"));
		}
		//工場品番
		if (!afterDTO.getFactoryItemCode().equals(beforeDTO.getBeforeFactoryItemCode())) {
			updHstry.add(registryUpdateData(afterDTO.getFactoryItemCode()
					, beforeDTO.getBeforeFactoryItemCode(), "工場品番", afterDTO.getSysItemId(), "0"));
		}
		//仕入国
		if (!afterDTO.getPurchaceCountry().equals(beforeDTO.getBeforePurchaceCountry())) {
			updHstry.add(registryUpdateData(afterDTO.getPurchaceCountry()
					, beforeDTO.getBeforePurchaceCountry(), "仕入国", afterDTO.getSysItemId(), "0"));
		}
		//仕入金額
		if (afterDTO.getPurchaceCost() != beforeDTO.getBeforePurchaceCost()) {
			String afterCost = StringUtil.formatCalc(BigDecimal.valueOf(afterDTO.getPurchaceCost()));
			String beforeCost = StringUtil.formatCalc(BigDecimal.valueOf(beforeDTO.getBeforePurchaceCost()));
			updHstry.add(registryUpdateData(afterCost, beforeCost, "仕入金額", afterDTO.getSysItemId(), "0"));
		}
		//海外商品名
		if (!afterDTO.getForeignItemNm().equals(beforeDTO.getBeforeForeignItemNm())) {
			updHstry.add(registryUpdateData(afterDTO.getForeignItemNm(), beforeDTO.getBeforeForeignItemNm(), "海外商品名", afterDTO.getSysItemId(), "0"));
		}
		//工場名(仕入先)
		if (afterDTO.getSysSupplierId() != beforeDTO.getBeforeSysSupplierId()) {
			SupplierDAO searchAfter = new SupplierDAO();
			String afterNm = searchAfter.getSupplier(afterDTO.getSysSupplierId()).getCompanyFactoryNm();
			String beforeNm = searchAfter.getSupplier(beforeDTO.getBeforeSysSupplierId()).getCompanyFactoryNm();
			updHstry.add(registryUpdateData(afterNm, beforeNm, "仕入先", afterDTO.getSysItemId(), "0"));
		}
		//仕入価格
		if (afterDTO.getPurchacePrice() != beforeDTO.getBeforePurchacePrice()) {
			String afterPrice = StringUtil.formatCalc(BigDecimal.valueOf(afterDTO.getPurchacePrice()));
			String beforePrice = StringUtil.formatCalc(BigDecimal.valueOf(beforeDTO.getBeforePurchacePrice()));
			updHstry.add(registryUpdateData(afterPrice, beforePrice, "仕入価格", afterDTO.getSysItemId(), "0"));
		}
		//加算経費
		if (afterDTO.getLoading() != beforeDTO.getBeforeLoading()) {
			String afterLoading = StringUtil.formatCalc(BigDecimal.valueOf(afterDTO.getLoading()));
			String beforeLoading = StringUtil.formatCalc(BigDecimal.valueOf(beforeDTO.getBeforeLoading()));
			updHstry.add(registryUpdateData(afterLoading, beforeLoading, "加算経費", afterDTO.getSysItemId(), "0"));
		}
		//MEMO
		if (!afterDTO.getDocumentRemarks().equals(beforeDTO.getBeforeDocumentRemarks())) {
			updHstry.add(registryUpdateData(afterDTO.getDocumentRemarks()
					, beforeDTO.getBeforeDocumentRemarks(), "MEMO", afterDTO.getSysItemId(), "0"));
		}


		//差分のある情報を判別し、更新メッセージ作成
		for (UpdateDataHistoryDTO dto: updHstry) {
			long sysUpdateDataId = seqDao.getMaxUpdateDataId() + 1;
			dto.setSysUpdateDataId(sysUpdateDataId);
			dao.updateHistoryInfo(dto);
		}
	}

	/**
	 * 更新情報を作成する：倉庫情報
	 * @param afterDTO
	 * @param beforeDto
	 * @return
	 * @throws DaoException
	 */
	public void insertWarehouseInfo(List<ExtendWarehouseStockDTO> afterDTO, List<ExtendWarehouseStockDTO> beforeDTO, String updateType)
			throws DaoException {

		//インスタンス生成
		SequenceDAO seqDao = new SequenceDAO();
		UpdateDataHistoryDAO dao = new UpdateDataHistoryDAO();
		List<UpdateDataHistoryDTO> updHstry = new ArrayList<UpdateDataHistoryDTO>();

		WarehouseDAO warehouseDao = new WarehouseDAO();

		for (int i = 0; i < afterDTO.size(); i++) {
			//各倉庫の在庫数に変動があった時それぞれの在庫数を文字列に変換して更新履歴を作成しリストに格納
			if (afterDTO.get(i).getStockNum() != beforeDTO.get(i).getBeforeStockNum()) {
				updHstry.add(registryUpdateData(String.valueOf(afterDTO.get(i).getStockNum()), String.valueOf(beforeDTO.get(i).getBeforeStockNum()),
						warehouseDao.getWarehouse(afterDTO.get(i).getSysWarehouseId()).getWarehouseNm() +  "在庫数", afterDTO.get(i).getSysItemId(), updateType));
			}
		}

		//差分のある情報を判別し、更新メッセージ作成
		for (UpdateDataHistoryDTO dto: updHstry) {
			long sysUpdateDataId = seqDao.getMaxUpdateDataId() + 1;
			dto.setSysUpdateDataId(sysUpdateDataId);
			dao.updateHistoryInfo(dto);
		}
	}


	/**
	 * 更新情報を作成する：外部倉庫情報
	 * @param afterDTO
	 * @param beforeDto
	 * @return
	 * @throws DaoException
	 */
	public void insertExternalWarehouseInfo(List<ExtendWarehouseStockDTO> afterDTO, List<ExtendWarehouseStockDTO> beforeDTO, String updateType)
			throws DaoException {

		//インスタンス生成
		SequenceDAO seqDao = new SequenceDAO();
		UpdateDataHistoryDAO dao = new UpdateDataHistoryDAO();
		List<UpdateDataHistoryDTO> updHstry = new ArrayList<UpdateDataHistoryDTO>();

		WarehouseDAO warehouseDao = new WarehouseDAO();

		for (int i = 0; i < afterDTO.size(); i++) {
			//各倉庫の在庫数に変動があった時それぞれの在庫数を文字列に変換して更新履歴を作成しリストに格納
			if (afterDTO.get(i).getStockNum() != beforeDTO.get(i).getBeforeStockNum()) {
				updHstry.add(registryUpdateData(String.valueOf(afterDTO.get(i).getStockNum()), String.valueOf(beforeDTO.get(i).getBeforeStockNum()),
						warehouseDao.getExternalWarehouse(afterDTO.get(i).getSysExternalWarehouseCode()).getWarehouseNm() +  "在庫数", afterDTO.get(i).getSysItemId(), updateType));
			}
		}

		//差分のある情報を判別し、更新メッセージ作成
		for (UpdateDataHistoryDTO dto: updHstry) {
			long sysUpdateDataId = seqDao.getMaxUpdateDataId() + 1;
			dto.setSysUpdateDataId(sysUpdateDataId);
			dao.updateHistoryInfo(dto);
		}
	}

	/**
	 * 各倉庫の変動値を算出します
	 * @param afterDTO
	 * @param beforeDTO
	 * @param map
	 */
	public void calculationWarehouseStock(List<ExtendWarehouseStockDTO> afterDTO, List<ExtendWarehouseStockDTO> beforeDTO,
			Map<Long, Integer> map) throws DaoException {

		//倉庫をひとつずつ参照し、変更前と後で差が出た時はその増減値を格納する
		for (int i = 0; i <afterDTO.size(); i++) {
				map.put(afterDTO.get(i).getSysWarehouseId(), afterDTO.get(i).getStockNum() - beforeDTO.get(i).getBeforeStockNum());
		}
	}


	/**
	 * 各外部倉庫の変動値を算出します
	 * @param afterDTO
	 * @param beforeDTO
	 * @param map
	 */
	public void calculationExternalWarehouseStock(List<ExtendWarehouseStockDTO> afterDTO, List<ExtendWarehouseStockDTO> beforeDTO,
			Map<String, Integer> map) throws DaoException {

		//倉庫をひとつずつ参照し、変更前と後で差が出た時はその増減値を格納する
		for (int i = 0; i <afterDTO.size(); i++) {
				map.put(afterDTO.get(i).getSysExternalWarehouseCode(), afterDTO.get(i).getStockNum() - beforeDTO.get(i).getBeforeStockNum());
		}
	}





	/**
	 * 検索結果の商品一覧から注文プールに追加する対象を識別し
	 * フラグの更新を行う
	 * @param itemList
	 * @return
	 */
	public ErrorMessageDTO addOrderPool(List<ResultItemSearchDTO> itemList)throws DaoException {
		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

		ItemDAO dao = new ItemDAO();

		for (ResultItemSearchDTO resultDto : itemList) {
			//注文プールフラグが1かどうか判定
			if (resultDto.getOrderPoolFlg().equals("1")) {
				//変更前注文プールフラグが0の物だけ処理を実施
				if (!resultDto.getBeforeOrderPoolFlg().equals(ORDER_POOL_FLG_ON)) {
					MstItemDTO dto = new MstItemDTO();
					dto.setSysItemId(resultDto.getSysItemId());
					dto.setOrderPoolFlg(resultDto.getOrderPoolFlg());
					dto.setOrderNum(resultDto.getOrderNum());
					if (dao.updatePoolFlg(dto) == 0) {
						errorMessageDTO.setSuccess(false);
					}
				}
			}
		}
		return errorMessageDTO;
	}

	/**
	 * 検索結果の商品を注文プールから削除する処理
	 * @param itemList
	 * @return
	 */
	public ErrorMessageDTO deleteOrderPool(List<ResultItemSearchDTO> itemList)throws DaoException {
		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

		ItemDAO dao = new ItemDAO();
		int resultCnt = 0;

		for (ResultItemSearchDTO resultDto : itemList) {
			//選択されている商品のみ処理を実行
			if (resultDto.getOrderCheckFlg().equals("on")) {
				//変更前注文プールフラグが1の物だけ処理を実施
				if (resultDto.getBeforeOrderPoolFlg().equals(ORDER_POOL_FLG_ON)) {
					MstItemDTO dto = new MstItemDTO();
					dto.setSysItemId(resultDto.getSysItemId());
					dto.setOrderPoolFlg("0");
					dto.setOrderNum(0);
					if (dao.updatePoolFlg(dto) == 0) {
						errorMessageDTO.setSuccess(false);
						return errorMessageDTO;
					}
					resultCnt++;
				}
			}
		}
		//更新件数を格納
		errorMessageDTO.setResultCnt(resultCnt);
		return errorMessageDTO;
	}

	/**
	 * 年間販売数情報の登録を行います
	 *
	 * @param mstItemDTO
	 * @throws DaoException
	 */
	public void registryAnnualSales(long sysItemId)
			throws DaoException {
		AnnualSalesDTO dto = new AnnualSalesDTO();
		ItemDAO dao = new ItemDAO();
		dto.setSysAnnualSalesId(new SequenceDAO().getMaxSysAnnualSalesId() + 1);
		dto.setSysItemId(sysItemId);
		dao.registryAnnualSales(dto);
	}

	/**
	 * 価格情報タブの構成品番売価情報の合計値取得
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public SetItemForm getSumPurcharPrice(long sysItemId, SetItemForm form)
			throws DaoException {
		//構成商品の金額合算値を取得
		ExtendMstItemDTO dto = new ExtendMstItemDTO();
		ItemDAO dao = new ItemDAO();
		dto = dao.getSumPurcharPrice(sysItemId);

		form.getMstItemDTO().setPurchaceCost(dto.getPurchaceCost());
		form.getMstItemDTO().setPurchacePrice(dto.getPurchacePrice());
		form.getMstItemDTO().setLoading(dto.getLoading());

		return form;
	}

}
