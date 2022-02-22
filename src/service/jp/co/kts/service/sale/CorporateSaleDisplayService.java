package jp.co.kts.service.sale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CorporateSalesSlipDTO;
import jp.co.kts.app.common.entity.ExportCorporateBillDTO;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstDeliveryDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.CorporateSaleListTotalDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.ErrorObject;
import jp.co.kts.app.output.entity.ExportSaleSummalyDTO;
import jp.co.kts.app.output.entity.StoreDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.dao.common.CorporateBillDAO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.common.TransactionDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.AccountDAO;
import jp.co.kts.dao.mst.ClientDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.fileExport.CorporateSummalyComparator;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.DeliveryService;
import jp.co.kts.service.mst.WarehouseService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 業販一覧から遷移できる機能で使用するクラスです
 *
 * @author aito
 *
 */

public class CorporateSaleDisplayService extends CorporateSaleService {

	// private long getSysSalesItemId(String itemCode) {
	// return 0;
	// }

	/**
	 * Brembo業販伝票自動生成対象法人
	 * 1:KTS、5:BCR、7:KTS業販
	 */
	private static final long[] BREMBO_CORP_SALE_SLIP_TARGET = {1, 5, 7};

	/**
	 * ----------------------------------------init----------------------------
	 * -----------------------
	 */

	/** リスト、配列の先頭指定する定数 */
	private static int INDEX_ZERO = 0;
	/** 金額系0設定、金額系の0と比較に使用する定数 */
	private static int AMOUNT_ZERO_SET = 0;
	/** コード値の比較：0(数値系) */
	private static long CODE_COMPARISON_ZERO = 0;
	/** 様々な計算で使用する：100 */
	private static int CALCULATION_100 = 100;

	public CorporateSaleSearchDTO initCorporateSaleSearchDTO() {

		CorporateSaleSearchDTO searchDto = new CorporateSaleSearchDTO();

		searchDto.setSlipStatus("1");/*表示ステータス*/
		searchDto.setPickingListFlg("0");
		searchDto.setLeavingFlg("0");
		searchDto.setSearchPriority("1");

		// dto.setSaleSearchMap(WebConst.SALE_SEARCH_MAP); /*並替え*/
		// dto.setSaleSearchSortOrder(WebConst.SALE_SEARCH_SORT_ORDER);
		// /*昇順・降順*/

		searchDto.setGrossProfitCalc(WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE);
		searchDto.setListPageMax(WebConst.LIST_PAGE_MAX_CODE_3);
		searchDto.setSortFirstSub("2");
		searchDto.setSumDispFlg(WebConst.SUM_DISP_FLG1);

		//通貨コード
		searchDto.setCurrency("0");

		// strutsの defineタグで使用するためnull回避
		searchDto.setScheduledLeavingDateFrom(StringUtils.EMPTY);
		searchDto.setScheduledLeavingDateTo(StringUtils.EMPTY);

		return searchDto;
	}

	public CorporateSaleSearchDTO initCorporateSaleSearchDTOSummaly() {

		CorporateSaleSearchDTO searchDto = new CorporateSaleSearchDTO();

		searchDto.setOrderDateTo(StringUtil.getToday());
		searchDto.setInvalidFlag("");
		searchDto.setReturnFlg("1");
		searchDto.setSortFirst("1");
		searchDto.setSortFirstSub("1");
		searchDto.setSlipStatus("");/*表示ステータス*/
		searchDto.setPickingListFlg("");
		searchDto.setLeavingFlg("");
		searchDto.setSearchAllFlg("1");

		return searchDto;
	}

	/**
	 * 業販伝票情報初期化
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	public ExtendCorporateSalesSlipDTO initCorporateSalesSlipDTO(HttpServletRequest request) throws ParseException {

		ExtendCorporateSalesSlipDTO slip = new ExtendCorporateSalesSlipDTO();

		slip.setEstimateDate(StringUtil.getToday());
		// 税率のデフォルトは今日日付で判定
		slip.setTaxRate(getTaxRate(StringUtil.getToday()));
		slip.setReturnFlg(StringUtil.SWITCH_OFF_VALUE);

//		HttpSession session = request.getSession();
//		String personalInCharge = (String)session.getAttribute("LOGIN_USER_NAME");
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		slip.setPersonInCharge(userInfo.getFamilyName());

		return slip;
	}

	/**
	 * 業販商品List初期化
	 * 業販商品の追加件数を20件分設定
	 * @return
	 */
	public List<ExtendCorporateSalesItemDTO> initAddCorporateSalesItemList() {

		List<ExtendCorporateSalesItemDTO> list = new ArrayList<>();

		for (int i = 0; i < WebConst.ADD_CORP_SALES_ITEM_LENGTH; i++) {

			list.add(new ExtendCorporateSalesItemDTO());
		}

		return list;
	}

	/**
	 * 返品伝票の設定
	 * @param slip
	 * @param salesItemList
	 * @return
	 * @throws Exception
	 */
	// public ExtendSalesSlipDTO initReturnSalesSlip(long sysSalesSlipId) throws
	// DaoException {
	public ExtendCorporateSalesSlipDTO initReturnCorporateSalesSlip(ExtendCorporateSalesSlipDTO slip,
			List<ExtendCorporateSalesItemDTO> salesItemList) throws Exception {

		// 消費税を再計算
		int sumPieceRate = getSumPieceRate(salesItemList);
		slip.setTax(getTax(sumPieceRate, slip.getTaxRate()));

		slip.setSumPieceRate(sumPieceRate);

		slip.setSumClaimPrice(getSumClaimPrice(slip));

		slip.setSysCorporateSalesSlipId(0L);
		slip.setReceivePrice(0);
		//注文日を今日に設定
		slip.setOrderDate(StringUtil.getToday());
		slip.setReturnFlg(StringUtil.SWITCH_ON_VALUE);

		return slip;
		// ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO();
		// dto = getSalesSlip(sysSalesSlipId);
		// dto.setOrderDate(StringUtil.getToday());
		// dto.setReturnFlg(StringUtil.SWITCH_ON_VALUE);
		// return dto;
	}

	/**
	 *
	 * @param slip
	 * @return
	 */
	private int getSumClaimPrice(ExtendCorporateSalesSlipDTO slip) {

		int sumClaimPrice = 0;

		sumClaimPrice = slip.getSumPieceRate();

		sumClaimPrice += slip.getTax();

		return sumClaimPrice;
	}

	private int calcSumSalesPrice(ExtendCorporateSalesSlipDTO dto, int sumSalesRate, int tax) {

		int sumSalesPrice = 0;

		sumSalesPrice = sumSalesRate;

		sumSalesPrice += tax;

		return sumSalesPrice;
	}


	/**
	 * 返品チェックされているものをListに格納
	 * @param salesItemList
	 * @return
	 * @throws DaoException
	 */
	// public List<ExtendSalesItemDTO> initReturnSalesItem(ExtendSalesSlipDTO
	// salesItemDTO) throws DaoException {
	public List<ExtendCorporateSalesItemDTO> initReturnCorporateSalesItem(
			List<ExtendCorporateSalesItemDTO> salesItemList) throws DaoException {

		for (int i = 0; i < salesItemList.size(); i++) {

			ExtendCorporateSalesItemDTO dto = salesItemList.get(i);

			//商品の返品チェックされているか判定
			if (StringUtils.equals(dto.getReturnFlg(), StringUtil.SWITCH_ON_KEY)) {

				dto.setSysCorporateSalesSlipId(0L);
				dto.setSysCorporateSalesItemId(0L);
				dto.setOrderNum(dto.getOrderNum() * -1);
				dto.setLeavingNum(dto.getLeavingNum() * -1);
				//BONCRE-2480 出庫日、売上日を操作できるようにしたが初期表示は今日日付
				dto.setLeavingDate(StringUtil.getToday());
				dto.setSalesDate(StringUtil.getToday());
			} else {
				//チェックされていない商品は除外
				salesItemList.remove(i--);
			}
		}
		return salesItemList;
	}

	// 20140326 八鍬
//	public List<ExtendCorporateSalesItemDTO> initCopyCorporateSlipItem2(
//			List<ExtendCorporateSalesItemDTO> salesItemList) throws DaoException {
//
//		List<ExtendCorporateSalesItemDTO> list = new ArrayList<>();
//
//		list = initAddCorporateSalesItemList();
//
//		for (int i = 0; i < salesItemList.size(); i++) {
//
//			ExtendCorporateSalesItemDTO dto = salesItemList.get(i);
//
//			dto.setSysCorporateSalesSlipId(0L);
//			dto.setSysCorporateSalesItemId(0L);
//			dto.setPieceRate(dto.getPieceRate());
//
//			list.set(i + 1, dto);
//		}
//		return list;
//	}
	/**
	 * Backlog No. BONCRE-498 対応 <br />
	 * 業販伝票、商品List(以下商品List)の複製し追加用商品Listとして返却を行います<br>
	 * @param salesItemList 商品List
	 * @return 複製された追加用商品List
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> initCopyCorporateSlipItem(
			List<ExtendCorporateSalesItemDTO> salesItemList) throws DaoException {

		// 例外
		if (salesItemList == null || salesItemList.size() <= 0) {
			throw new NullPointerException();
		}

		List<ExtendCorporateSalesItemDTO> retList = new ArrayList<ExtendCorporateSalesItemDTO>();

		// 複製時は1行目を空行とする
		//  新規追加用の行にするため
		retList.add(new ExtendCorporateSalesItemDTO());

		for (int i = 0; i < salesItemList.size(); i++) {

			ExtendCorporateSalesItemDTO dto = salesItemList.get(i);

			dto.setSysCorporateSalesSlipId(0L);
			dto.setSysCorporateSalesItemId(0L);
			dto.setPieceRate(dto.getPieceRate());

			retList.add(dto);
		}

		// 一定のサイズまでListの要素を増やします
		for (int i = retList.size() -1; i < WebConst.ADD_CORP_SALES_ITEM_LENGTH; i++) {
			retList.add(new ExtendCorporateSalesItemDTO());
		}
		return retList;
	}

	// 20140326 八鍬
	public ExtendCorporateSalesSlipDTO initCopyCorporateSalesSlip(ExtendCorporateSalesSlipDTO dto,
			List<ExtendCorporateSalesItemDTO> addSalesItemList) throws Exception {

		// 消費税を再計算
		int sumPieceRate = getSumPieceRate(addSalesItemList);
		dto.setTax(getTax(sumPieceRate, dto.getTaxRate()));

		dto.setSumPieceRate(sumPieceRate);
		dto.setSumClaimPrice(getSumClaimPrice(dto));

		dto.setSysCorporateSalesSlipId(0L);
		dto.setOrderDate(StringUtil.getToday());

		return dto;
	}

	/**
	 * 業販伝票情報取得サービス
	 * @param sysCorporateSalesSlipId
	 * @return
	 * @throws DaoException
	 */
	public ExtendCorporateSalesSlipDTO getCorporateSalesSlip(long sysCorporateSalesSlipId)
			throws DaoException {

		ExtendCorporateSalesSlipDTO dto = new ExtendCorporateSalesSlipDTO(StringUtils.EMPTY);
		//システム業販売上伝票IDを設定
		dto.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
		//業販伝票取得
		dto = new CorporateSaleDAO().getCorporateSaleSlip(dto);

		//伝票情報が無ければ終了
		if (dto == null) {

			return new ExtendCorporateSalesSlipDTO();
		}

		//画面に表示する用フラグ読み替え(伝票)
		setFlagsDisp(dto);

		return dto;
	}

	public List<SysCorporateSalesSlipIdDTO> getSysCorporateSalesSlipIdList(
			CorporateSaleSearchDTO corporateSaleSearchDTO) throws DaoException {

		List<SysCorporateSalesSlipIdDTO> list = new CorporateSaleDAO().getSearchCorporateSalesSlipList(corporateSaleSearchDTO);

		return list;
	}

	/**
	 * 伝票ひとつ分の原価（コスト）合計値を返却します
	 *
	 * @param sysSalesSlipId
	 * @param sysCorporationId
	 *            原価がitem_costTBにある場合に必要
	 * @return
	 * @throws DaoException
	 */
	private long getSumCost(long sysCorporateSalesSlipId, long sysCorporationId)
			throws DaoException {

		ExtendCorporateSalesItemDTO searchDTO = new ExtendCorporateSalesItemDTO();
		searchDTO.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
		searchDTO.setSysCorporationId(sysCorporationId);
		List<ExtendCorporateSalesItemDTO> list =
				new CorporateSaleDAO().getCorporateSalesItemList(searchDTO);

		if (list == null || list.size() == 0) {
			return 0;
		}

		// 合計用
		long sumCost = 0;

		for (ExtendCorporateSalesItemDTO dto : list) {

			sumCost += (dto.getCost() * dto.getOrderNum());
		}

		return sumCost;
	}

	public List<ExtendCorporateSalesSlipDTO> getCorporateSalesSlipList(
			List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList, int pageIdx,CorporateSaleSearchDTO searchDto) throws DaoException {

		List<ExtendCorporateSalesSlipDTO> salesSlipList = new ArrayList<>();

		if (StringUtils.isEmpty(searchDto.getListPageMax())) {
			searchDto.setListPageMax("3");
		}

		//一覧出力する分だけ必要なデータを取得
		int slipNumPerPage = WebConst.LIST_PAGE_MAX_MAP.get(searchDto.getListPageMax());
		int maxIdx = Math.min(slipNumPerPage * (pageIdx + 1), sysCorporateSalesSlipIdList.size());
		for (int i = slipNumPerPage * pageIdx; i < maxIdx ;i++) {

			CorporateSaleSearchDTO saleSearchDTO = new CorporateSaleSearchDTO();

			long sysCorporateSalesSlipId = sysCorporateSalesSlipIdList.get(i).getSysCorporateSalesSlipId();

			//伝票情報取得
			saleSearchDTO.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
			CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
			ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = corporateSaleDAO.getSearchCorporateSalesSlip(saleSearchDTO);
			setFlagsDisp(corporateSalesSlipDTO);
			if (StringUtils.isNotEmpty(searchDto.getSlipStatus()) && StringUtils.equals(searchDto.getSlipStatus(), "3")) {
				corporateSalesSlipDTO.setSlipStatusNm("売上");
			}

			//伝票に紐付くアイテム情報
			List<ExtendCorporateSalesItemDTO> itemList = this.getCorporateSalesItemList(sysCorporateSalesSlipId, corporateSalesSlipDTO.getSysCorporationId(), searchDto);
			if (itemList.size() <= 0) {
				continue;
			}
			corporateSalesSlipDTO.setCorporateSalesItemList(itemList);
			//ステータスセット
			for (ExtendCorporateSalesItemDTO item : itemList) {
				if (StringUtils.isEmpty(item.getLeavingDate())) {
					item.setDeliveryStatus("未納");
				} else if (item.getOrderNum() > item.getLeavingNum()) {
					item.setDeliveryStatus("一部");
				}
			}
			salesSlipList.add(corporateSalesSlipDTO);
		}

		Map<Long, ArrayList<ExtendCorporateSalesSlipDTO>> slipMap = new HashMap<>();

		for (ExtendCorporateSalesSlipDTO slip : salesSlipList){
			if (!slipMap.containsKey(slip.getSysClientId())) {
				ArrayList<ExtendCorporateSalesSlipDTO> salelist = new ArrayList<>();
				salelist.add(slip);
				slipMap.put(slip.getSysClientId(), salelist);
			} else {
				slipMap.get(slip.getSysClientId()).add(slip);
			}
		}

		return salesSlipList;
	}


	/**
	 * 伝票IDからすべての伝票およびそれに紐付く商品を検索、取得する
	 * （見積書作成用）
	 * @param sysCorporateSalesSlipIdList
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesSlipDTO> getCorporateSalesAllSlipList(
			List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList) throws DaoException {

		List<ExtendCorporateSalesSlipDTO> salesAllSlipList = new ArrayList<>();

		for(int i = 0; i < sysCorporateSalesSlipIdList.size(); i++) {

			CorporateSaleSearchDTO saleSearchDTO = new CorporateSaleSearchDTO();
			long sysCorporateSalesSlipId = sysCorporateSalesSlipIdList.get(i).getSysCorporateSalesSlipId();

			//伝票情報取得
			saleSearchDTO.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
			CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
			ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = corporateSaleDAO.getSearchCorporateSalesSlip(saleSearchDTO);

			//伝票に紐付くアイテム情報
			List<ExtendCorporateSalesItemDTO> itemList = this.getCorporateSalesItemListForEstimate(sysCorporateSalesSlipId, corporateSalesSlipDTO.getSysCorporationId());
			if (itemList.size() <= 0) {
				continue;
			}
			corporateSalesSlipDTO.setCorporateSalesItemList(itemList);
			salesAllSlipList.add(corporateSalesSlipDTO);
		}

		return salesAllSlipList;
	}

	// public ExtendSalesSlipDTO getSearchSalesSlip(SaleSearchDTO saleSearchDTO)
	// throws DaoException {
	//
	// return new SaleDAO().getSearchSalesSlip(saleSearchDTO);
	// }



	/**
	 * フラグ読替えて伝票IDを検索DAOに渡す
	 * @param sysCorporateSalesSlipId
	 * @param sysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemList(long sysCorporateSalesSlipId,
			long sysCorporationId) throws DaoException {

		ExtendCorporateSalesItemDTO itemDto = new ExtendCorporateSalesItemDTO(StringUtils.EMPTY);
		CorporateSaleDAO corporateSaleDao = new CorporateSaleDAO();

		//システム売上伝票ID
		itemDto.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
		//システム法人ID
		itemDto.setSysCorporationId(sysCorporationId);

		//表示用フラグ読み替え
		List<ExtendCorporateSalesItemDTO> itemList = corporateSaleDao.getCorporateSalesItemList(itemDto);
		for (ExtendCorporateSalesItemDTO item : itemList) {
			//item.setBillingFlag(StringUtil.switchCheckBoxDisp(item.getBillingFlag()));
			setFlagsDisp(item);
		}

		return itemList;
	}

	/**
	 * 伝票IDに紐付く商品を取得する
	 * (見積書作成で使用)
	 *
	 * @param sysCorporateSalesSlipId
	 * @param sysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemListForEstimate(long sysCorporateSalesSlipId,
			long sysCorporationId) throws DaoException {

		ExtendCorporateSalesItemDTO itemDto = new ExtendCorporateSalesItemDTO(StringUtils.EMPTY);
		CorporateSaleDAO corporateSaleDao = new CorporateSaleDAO();

		//システム売上伝票ID
		itemDto.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
		//システム法人ID
		itemDto.setSysCorporationId(sysCorporationId);

		List<ExtendCorporateSalesItemDTO> itemList = corporateSaleDao.getCorporateSalesItemList(itemDto);

		return itemList;
	}

	/**
	 * 伝票に紐付く商品検索
	 *
	 * @param sysCorporateSalesSlipId
	 * @param sysCorporationId
	 * @param searchDto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getCorporateSalesItemList(long sysCorporateSalesSlipId,
			long sysCorporationId, CorporateSaleSearchDTO searchDto) throws DaoException {

		ExtendCorporateSalesItemDTO itemDto = new ExtendCorporateSalesItemDTO(StringUtils.EMPTY);

		itemDto.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
		itemDto.setSysCorporationId(sysCorporationId);


		//表示用フラグ読み替え
		List<ExtendCorporateSalesItemDTO> itemList = new CorporateSaleDAO().getCorporateSalesItemList(itemDto, searchDto);
		for (ExtendCorporateSalesItemDTO item : itemList) {
			//item.setBillingFlag(StringUtil.switchCheckBoxDisp(item.getBillingFlag()));
			setFlagsDisp(item);
		}

		return itemList;
	}

	/**
	 * 出庫画面に表示する伝票の商品を選択
	 *
	 * @param corporateSalesSlipList
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> getLeaveStockList(
			List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList) throws DaoException {

		//出庫画面表示用Listの用意
		List<ExtendCorporateSalesItemDTO> leaveStockList = new ArrayList<>();

		//検索結果分ループ
		for (ExtendCorporateSalesSlipDTO corporateSalesSlipDTO : corporateSalesSlipList) {

			List<ExtendCorporateSalesItemDTO> corporateSalesItemList = new ArrayList<>();

			// 一伝票の商品取得
			corporateSalesItemList = getCorporateSalesItemList(corporateSalesSlipDTO.getSysCorporateSalesSlipId(),
					corporateSalesSlipDTO.getSysCorporationId());

			//一伝票の商品分ループ
			for (ExtendCorporateSalesItemDTO corporateSalesItemDTO : corporateSalesItemList) {

				//注文数分出庫し終わっていて、出庫日が入っているものは除外の処理に変更
				if (corporateSalesItemDTO.getOrderNum() == corporateSalesItemDTO.getLeavingNum() && !StringUtils.equals(corporateSalesItemDTO.getLeavingDate(), "")) {
//				if (corporateSalesItemDTO.getOrderNum() == corporateSalesItemDTO.getLeavingNum()) {
						continue;
				}

				//出庫予定日が今日でないものは除外
				if (!StringUtils.equals(corporateSalesItemDTO.getScheduledLeavingDate(), StringUtil.getToday())) {
					continue;
				}

				// 取得した商品の情報では足りない部分を伝票から取得
				//注文数
				corporateSalesItemDTO.setOrderNo(corporateSalesSlipDTO.getOrderNo());
				//伝票ID
				corporateSalesItemDTO.setSysCorporateSalesSlipId(corporateSalesSlipDTO.getSysCorporateSalesSlipId());
				//ここで出庫数が注文数と同じで出庫日がないバグデータを判定して出庫済み数を変更させる
				if (corporateSalesItemDTO.getOrderNum() == corporateSalesItemDTO.getLeavingNum() && StringUtils.isEmpty(corporateSalesItemDTO.getLeavingDate())) {

					// 出庫数のデフォルト値を
					corporateSalesItemDTO.setLeavingNum(0);
					corporateSalesItemDTO.setLeaveNum(corporateSalesItemDTO.getOrderNum());
				} else {

					// 出庫数のデフォルト値を注文数、出庫済み数から取得
					corporateSalesItemDTO.setLeaveNum(corporateSalesItemDTO.getOrderNum() - corporateSalesItemDTO.getLeavingNum());
				}

				//国内商品の場合
				if (corporateSalesItemDTO.getSysItemId() == 0) {
					leaveStockList.add(corporateSalesItemDTO);
					continue;
				}

				//優先度１の倉庫
				ExtendWarehouseStockDTO stockDTO = getPriorityWarehouse(corporateSalesItemDTO
						.getSysItemId());
				if (stockDTO == null) {

					corporateSalesItemDTO.setWarehouseNm("優先度１の倉庫がないか\n優先度１の倉庫が複数あります");

				} else {

					//倉庫名set
					corporateSalesItemDTO.setWarehouseNm(stockDTO.getWarehouseNm());
					//倉庫在庫数set
					corporateSalesItemDTO.setFirstWarehouseStockNum(stockDTO.getStockNum());
				}
				//出庫画面に表示するリストに追加
				leaveStockList.add(corporateSalesItemDTO);
			}
		}
		//出庫画面表示Listを返す
		return leaveStockList;
	}

	public List<ExtendCorporateSalesSlipDTO> getPickItemList(
			List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList) throws DaoException {

		// List<ExtendSalesItemDTO> leaveStockList = new ArrayList<>();

		// int i = 0;
		for (ExtendCorporateSalesSlipDTO dto : corporateSalesSlipList) {

			dto.setPickItemList(getCorporateSalesItemList(dto.getSysCorporateSalesSlipId(),
					dto.getSysCorporationId()));

			for (ExtendCorporateSalesItemDTO salesItemDTO : dto.getPickItemList()) {

				ExtendWarehouseStockDTO stockDTO = new ExtendWarehouseStockDTO();
				stockDTO = getPriorityWarehouse(salesItemDTO.getSysItemId());
				if (stockDTO == null) {

					salesItemDTO.setWarehouseNm("優先度１の倉庫がないか優先度１の倉庫が複数あります");

				} else {

					salesItemDTO.setWarehouseNm(stockDTO.getWarehouseNm());
					salesItemDTO.setLocationNo(stockDTO.getLocationNo());

				}
			}
		}

		return corporateSalesSlipList;
	}

	public List<ExtendCorporateSalesItemDTO> getTotalPickItemList(
			List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList) throws DaoException {

		List<ExtendCorporateSalesItemDTO> itemList = new ArrayList<>();

		// int i = 0;
		for (ExtendCorporateSalesSlipDTO salesSlipDTO : corporateSalesSlipList) {

			List<ExtendCorporateSalesItemDTO> salesItemList = new ArrayList<>();
			// leaveStockDTO.setSalesSlipDTO(dto);

			// 商品の注文数などを取得
			salesItemList = getCorporateSalesItemList(salesSlipDTO.getSysCorporateSalesSlipId(),
					salesSlipDTO.getSysCorporationId());

			/** 商品Distinctする？　暫定 */
			for (ExtendCorporateSalesItemDTO salesItemDTO : salesItemList) {
				if (!StringUtils.equals(salesItemDTO.getScheduledLeavingDate(), StringUtil.getToday())
						|| StringUtils.equals(salesItemDTO.getPickingListFlg(), "on")) {
					continue;
				}
				boolean addFlg = true;
				for (ExtendCorporateSalesItemDTO extendSalesItemDTO : itemList) {
					if (StringUtils.equals(extendSalesItemDTO.getItemCode(),
							salesItemDTO.getItemCode())) {
						int orederNum = 0;
						orederNum = extendSalesItemDTO.getOrderNum();
						orederNum += salesItemDTO.getOrderNum();
						extendSalesItemDTO.setOrderNum(orederNum);
						addFlg = false;
						break;
					}
				}
				if (addFlg) {
					itemList.add(salesItemDTO);
				}

			}
		}
		return itemList;
	}

	/**
	 * 優先度１の倉庫
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	private ExtendWarehouseStockDTO getPriorityWarehouse(long sysItemId)
			throws DaoException {

		List<ExtendWarehouseStockDTO> list = new ItemDAO()
				.getWarehouseStockList(sysItemId);

		ExtendWarehouseStockDTO stockDTO = new ExtendWarehouseStockDTO();

		// String warehouseNm = StringUtils.EMPTY;
		for (ExtendWarehouseStockDTO dto : list) {

			if (StringUtils.isEmpty(stockDTO.getWarehouseNm())
					&& StringUtils.equals(dto.getPriority(), "1")) {

				stockDTO.setStockNum(dto.getStockNum());
				stockDTO.setWarehouseNm(new WarehouseService()
						.getWarehouseNm(dto.getSysWarehouseId()));
				stockDTO.setLocationNo(dto.getLocationNo());
				// stockDTO = new
				// WarehouseService().getWarehouse(dto.getSysWarehouseId());

				// 優先度1が複数あった場合
			} else if (StringUtils.isNotEmpty(stockDTO.getWarehouseNm())
					&& StringUtils.equals(dto.getPriority(), "1")) {

				return null;
			}
		}

		return stockDTO;
	}

	/**
	 * 伝票一覧から商品価格合計や粗利の各合計を取得します(Java)<br />
	 * 表示フラグによって計算するか否かを判定します<br />
	 *
	 * @param sysCorporateSalesSlipIdList
	 * @param grossMarginFlg
	 * @param sumDispFlg 表示フラグ
	 * @return
	 * @throws DaoException
	 */
	public CorporateSaleListTotalDTO getCorporateSaleListTotalDTO(
			List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList
			, String grossMarginFlg
			, String sumDispFlg)
			throws DaoException {

		CorporateSaleListTotalDTO corporatesaleListTotalDTO = new CorporateSaleListTotalDTO();
		// Backlog No. BONCRE-488 対応 (サーバ側)(Backlog 存在せず)
		// 非表示の場合
		if (StringUtils.equals(WebConst.SUM_DISP_FLG1, sumDispFlg)) {
			return corporatesaleListTotalDTO;
		}

		for (SysCorporateSalesSlipIdDTO dto : sysCorporateSalesSlipIdList) {

			long price = 0;
			if (StringUtils.equals(grossMarginFlg,
					WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE)) {

				//税抜き合計金額
				price = dto.getSumPieceRate();

			} else if (StringUtils.equals(grossMarginFlg,
					WebConst.GROSS_PROFIT_CALC_TOTAL_CODE)) {

				//税込み合計金額
				price = dto.getSumClaimPrice();
			}

			long[] arrPrice = getCostGrossMargin(
					dto.getSysCorporateSalesSlipId(),
					dto.getSysCorporationId(),
					price);

			// 合計請求金額を計算する
			corporatesaleListTotalDTO.setSumSumClaimPrice(
					corporatesaleListTotalDTO.getSumSumClaimPrice() + dto.getSumClaimPrice());

			// 税込商品単価の合計を計算する
			corporatesaleListTotalDTO.setInTaxSumPieceRate(
					corporatesaleListTotalDTO.getInTaxSumPieceRate() + dto.getSumPieceRate() + dto.getTax());

			// 税抜き商品単価の合計を計算する
			corporatesaleListTotalDTO.setNoTaxSumPieceRate(
					corporatesaleListTotalDTO.getNoTaxSumPieceRate() + dto.getSumPieceRate());

			// 原価の合計を計算する
			corporatesaleListTotalDTO.setSumCost(
					corporatesaleListTotalDTO.getSumCost() + arrPrice[0]);

			// 粗利の合計を計算する
			corporatesaleListTotalDTO.setSumGrossMargin(
					corporatesaleListTotalDTO.getSumGrossMargin() + arrPrice[1]);
		}

		return corporatesaleListTotalDTO;
	}

	/**
	 * 原価と粗利を取得します<br />
	 * 売上伝票ID・会社IDから原価を取得するb<br />
	 *
	 * @param sysSalesSlipId 売上伝票ID
	 * @param sysCorporationId 会社ID
	 * @param price 売値
	 * @return
	 * [0]:原価<br />
	 * [1]:粗利(売値-原価)<br />
	 * @throws DaoException
	 */
	public long[] getCostGrossMargin(
			long sysSalesSlipId,
			long sysCorporationId,
			long price) throws DaoException {

		long[] arrPrice = new long[2];
		long cost = 0;
		long grossMargin = 0;

		cost = getSumCost(sysSalesSlipId, sysCorporationId);
		grossMargin = price - cost;

		arrPrice[0] = cost;
		arrPrice[1] = grossMargin;
		return arrPrice;
	}

	/**
	 * 単価の合計から粗利を取得します
	 *
	 * @param sysSalesSlipIdList
	 * @throws DaoException
	 */
//	 private List<SysSalesSlipIdDTO>
//	 getCostGrossMargin(List<SysSalesSlipIdDTO> sysSalesSlipIdList) throws
//	 DaoException {
//
//	 for (SysSalesSlipIdDTO dto: sysSalesSlipIdList) {
//
//	 dto.setSumCost(getSumCost(dto.getSysSalesSlipId(),
//	 dto.getSysCorporationId()));
//	 dto.setSumGrossMargin(dto.getNoTaxSumPieceRate() - dto.getCost());
//	 }
//	 return sysSalesSlipIdList;
//	 }

	/**
	 * 画面に表示する用フラグ読み替え(伝票)
	 *
	 * @param dto
	 */
	public void setFlagsDisp(ExtendCorporateSalesSlipDTO slip) {

		if (slip == null) {
			return;
		}

		slip.setInvalidFlag(StringUtil.switchCheckBoxDisp(slip.getInvalidFlag()));
	}

	/**
	 * DBに格納する用フラグ読み替え(伝票)
	 *
	 * @param dto
	 */
	public void setFlagsDB(ExtendCorporateSalesSlipDTO slip) {

		if (slip == null) {
			return;
		}

		slip.setInvalidFlag(StringUtil.switchCheckBoxDB(slip.getInvalidFlag()));
	}

	/**
	 * 画面に表示する用フラグ読み替え(商品)
	 *
	 * @param dto
	 */
	public void setFlagsDisp(ExtendCorporateSalesItemDTO item) {

		if (item == null) {
			return;
		}

		item.setPickingListFlg(StringUtil.switchCheckBoxDisp(item.getPickingListFlg()));
	}

	/**
	 * DBに格納する用フラグ読み替え(商品)
	 *
	 * @param dto
	 */
	public void setFlagsDB(ExtendCorporateSalesItemDTO item) {

		if (item == null) {
			return;
		}
		//値を読み替えて設定
		item.setPickingListFlg(StringUtil.switchCheckBoxDB(item.getPickingListFlg()));
	}




	/**
	 * 検索用フラグ読み換え
	 *
	 * @param dto
	 */
	public void setFlags(CorporateSaleSearchDTO dto) {

		if (StringUtils.isNotEmpty(dto.getReturnFlg())) {
			dto.setReturnFlg(StringUtil.switchCheckBox(dto.getReturnFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getSearchAllFlg())) {
			dto.setSearchAllFlg(StringUtil.switchCheckBox(dto.getSearchAllFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getInvalidFlag())) {
			dto.setInvalidFlag(StringUtil.switchCheckBox(dto.getInvalidFlag()));
		}
	}

	/**
	 * ----------------------------------------INPUT----------------------------
	 * -----------------------
	 *
	 */

	/**
	 *
	 * @param salesSlipDTO
	 * @throws DaoException
	 * @throws ParseException
	 */

	public void updateCorporateSalesSlip(ExtendCorporateSalesSlipDTO corporateSalesSlipDTO)
			throws DaoException, ParseException {

		setFlagsDB(corporateSalesSlipDTO);

		if (StringUtils.isNotEmpty(corporateSalesSlipDTO.getCorporateReceiveDate())
				&& corporateSalesSlipDTO.getCorporateReceivePrice() != 0) {

			new CorporateReceiveService().registryCorporateReceive(
					corporateSalesSlipDTO.getCorporateReceivePrice(), corporateSalesSlipDTO.getCorporateReceiveDate()
					, corporateSalesSlipDTO.getSysCorporateSalesSlipId(), corporateSalesSlipDTO.getSysClientId());
		}
		new CorporateSaleDAO().updateCorporateSalesSlip(corporateSalesSlipDTO);
	}

	public void lumpUpdateCorporateSalesList(List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList)
			throws DaoException {

		for (ExtendCorporateSalesSlipDTO slip : corporateSalesSlipList) {

			slip.setInvalidFlag(StringUtil.switchCheckBox(slip.getInvalidFlag()));

			new CorporateSaleDAO().updateCorporateSalesSlipFlg(slip);
			// new SaleDAO().updateA
			for (ExtendCorporateSalesItemDTO item : slip.getCorporateSalesItemList()) {
				updateCorporateSalesItem(item);
			}
		}
	}

	public void updateCorporateReceivePrice(List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList)
			throws DaoException {

		for (ExtendCorporateSalesSlipDTO dto : corporateSalesSlipList) {

			int price = 0;
			if (StringUtils.equals(dto.getPaymentMethod(), "1")) {
				price = dto.getSumSalesPrice() - dto.getReceivePrice();
			} else {
				price = dto.getSumClaimPrice() - dto.getReceivePrice();
			}
			if (price == 0) {
				continue;
			}

			new CorporateReceiveService().registryCorporateReceive(price, StringUtil.getToday()
					, dto.getSysCorporateSalesSlipId(), dto.getSysClientId());


			new CorporateSaleDAO().updateCorporateReceivePrice(dto);
			// new SaleDAO().updateA
		}
	}

	public void updateCorporateSalesItemList(List<ExtendCorporateSalesItemDTO> corporateSalesItemList)
			throws DaoException {

		for (ExtendCorporateSalesItemDTO dto : corporateSalesItemList) {

			if (StringUtils.equals(dto.getDeleteFlag(), "1")) {
				deleteCorporateSalesItem(dto.getSysCorporateSalesItemId());
			} else {
				if(dto.getPostage() > 0 && dto.getOrderNum() > 0) {
					dto.setCost(dto.getCost() - Math.abs((dto.getPostage() / dto.getOrderNum())));
				}
				updateCorporateSalesItem(dto);
			}
		}
	}

	/**
	 * 業販商品登録処理実行サービス
	 * [概要]業販伝票に紐づく商品情報を登録するサービス
	 * @param addSalesItemList
	 * @param sysSalesSlipId
	 * @param saleDto
	 * @throws DaoException
	 */
	public void registryCorporateSaleItemList(List<ExtendCorporateSalesItemDTO> addSalesItemList,
			long sysSalesSlipId, CorporateSalesSlipDTO saleDto) throws DaoException {

		//商品を一件づつ登録する
		for (ExtendCorporateSalesItemDTO item : addSalesItemList) {
			//商品名が空または注文数が0の場合は登録しない
			if (StringUtils.isBlank(item.getItemNm()) || item.getOrderNum() == 0) {
				continue;
			}

			registryCorporateSaleItem(item, sysSalesSlipId, saleDto);
		}
	}

	/**
	 * 新規業販伝票登録サービス
	 * [概要]業販画面から業販伝票の登録を行った際に使用されるサービス
	 * @param slip
	 * @throws DaoException
	 */
	public void newRegistryCorporateSalesSlip(ExtendCorporateSalesSlipDTO slip)
			throws DaoException {

		//業販伝票登録処理実施(入金データが存在すれば入金登録も行う)
		registryCorporateSalesSlip(slip);
		//フラグを対応する値に変換
		setFlagsDB(slip);
		//更新情報を更新
		new CorporateSaleDAO().updateCorporateSalesSlipFlg(slip);

	}

	/**
	 * 業販伝票登録サービス
	 * [概要]伝票番号の取得と整形を行い業販伝票をインサートする
	 * 入金情報が存在する場合入金もインサートする
	 */
	public void registryCorporateSalesSlip(ExtendCorporateSalesSlipDTO slip) throws DaoException {
		//伝票番号を取得(最大値)
		long slipId = new SequenceDAO().getMaxSysCorporateSalesSlipId() + 1;
		slip.setSysCorporateSalesSlipId(slipId);

		//伝票No作成

		//法人IDごとに伝票Noの頭文字を設定
		//法人IDが18以上は未定義なため、定数クラスWebConstに追加すること
		String initial="";
		initial = WebConst.SYS_CORPORATION_CODE_MAP.get(String.valueOf(slip.getSysCorporationId()));

		//伝票NoをDtoにセット
		slip.setOrderNo(initial+String.format("%08d", 10000+slipId));

		//入金日
		//入金日の変数が２つあり、corporateReceiveDateは入金日を持っているが、receiveDateはnullで今後Insertに使用する
		slip.setReceiveDate(slip.getCorporateReceiveDate());

		//業販伝票登録
		new CorporateSaleDAO().registryCorporateSaleSlip(slip);

		//入金日付が存在する且入金額が0以外の場合
		if (StringUtils.isNotEmpty(slip.getCorporateReceiveDate())
				&& slip.getCorporateReceivePrice() != 0) {
			//入金情報を登録
			new CorporateReceiveService().registryCorporateReceive(
					slip.getCorporateReceivePrice(), slip.getCorporateReceiveDate()
					, slip.getSysCorporateSalesSlipId(), slip.getSysClientId());
		}

	}

	/**
	 * Bremboの商品が存在するか調べる処理<br>
	 * 出庫処理した業販伝票商品の中にBremboの商品があるかを調べてその商品リストを作成します。
	 * @param corpSalesItemList
	 * @return List<ExtendCorporateSalesItemDTO>
	 * @author mSuda
	 */
	public List<ExtendCorporateSalesItemDTO> checkTargetExistsForBremboItem(List<ExtendCorporateSalesItemDTO> corpSalesItemList)
			throws Exception {

		List<ExtendCorporateSalesItemDTO> bremboItemList = new ArrayList<>();
		ItemDAO itemDAO = new ItemDAO();

		//1つも出庫してないときは何もしない
		if (corpSalesItemList == null) {
			return bremboItemList;
		}

		for (ExtendCorporateSalesItemDTO itemDto : corpSalesItemList) {

			//出庫してない商品はそもそも売り上げた商品として出さない
			if (itemDto.getLeaveNum() == 0) {
				continue;
			}

			/**** セット商品の構成商品がBrembo商品の時の処理 START ******************************/
			/*
			 * TODO
			 * 構成商品にBrembo商品があるセット商品は、(現状)Brembo商品以外が構成商品に入ってくることはない。
			 * そのため1層目だけ調べればそのセット商品の構成商品はBrembo商品ということになる。
			 * ※ただし、今後Brembo商品以外が入ってくることもありえるのでその時はCsvImportService.javaなどを参考にして、商品を階層ごとに見ていく処理を
			 * 　追加する必要がある。
			 * */

			//出庫分類フラグが取れない商品は通常商品のため、すぐに品番を調べる
			if (itemDto.getLeaveClassFlg() == null) {
				if (itemDto.getItemCode().startsWith("71")) {
					bremboItemList.add(itemDto);
					continue;
				}
			}

			//登録された商品がセット商品だった時の処理
			//セット商品が「セット商品から出庫する」場合 ※現状、品番71から始まるセット商品はない
			if (itemDto.getLeaveClassFlg().equals("0")) {
				if (itemDto.getItemCode().startsWith("71")) {
					bremboItemList.add(itemDto);
				}
			//セット商品が「構成商品から出庫する」場合、構成商品がBrembo商品だったらそのセット商品をBrembo商品の対象とする
			} else if (itemDto.getLeaveClassFlg().equals("1")) {
				//セット商品の構成商品を取得
				List<ExtendSetItemDTO> configurationItemList = itemDAO.getSetItemInfoList(itemDto.getSysItemId());

				//上で取得した構成商品は商品IDしか持っていないため、品番を取得するには再度検索する必要がある。
				String itemCode = itemDAO.getItemCode(configurationItemList.get(INDEX_ZERO).getFormSysItemId());

				//1つ目の構成商品の品番が71で始まるかを調べる。現状Brembo商品が構成商品に入る場合は他の構成商品も全てBremboの商品のため、1つだけ調べればよい。
				//※後にBrembo以外も入ってくる可能性がある。(詳細はTODOメモにて)
				if (itemCode.startsWith("71")) {
					bremboItemList.add(itemDto);
				}

			}
			/**** セット商品の構成商品がBrembo商品の時処理 END ********************************/

		}

		return bremboItemList;

	}

	/**
	 * 出庫された業販伝票商品をもとにBrembo事業部の業販伝票を自動生成する
	 * @param corpSalesSlipItemList 業販伝票に紐づいている商品のリスト
	 * @throws ParseException
	 */
	public void createBremboCorpSalesSlip(List<ExtendCorporateSalesItemDTO> corpSalesSlipItemList)
			throws DaoException, ParseException {

		//重複登録を避けるためのマップ
		Map<Long, Long> duplicateProtectionMap = new HashMap<>();
		//その他必要なインスタンス
		CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
		CorporationDAO corporationDAO = new CorporationDAO();
		ClientDAO clientDAO = new ClientDAO();
		ItemDAO itemDAO = new ItemDAO();
		//伝票作成日(本日日付)
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String createDateToday = sdf.format(cal.getTime());

		CorporateSaleService cSServie = new CorporateSaleService();

		for (int i = 0; corpSalesSlipItemList.size() > i; i++) {

			long sysCorporateSalesSlipId = corpSalesSlipItemList.get(i).getSysCorporateSalesSlipId();
			//同じ伝票の商品を一つにまとめるための商品リスト
			List<ExtendCorporateSalesItemDTO> registrySlipItemList = new ArrayList<>();

			//既に伝票作成した商品は飛ばす
			if (duplicateProtectionMap.containsKey(sysCorporateSalesSlipId)) {
				continue;
			}

			//1つ目をリストに格納する
			registrySlipItemList.add(corpSalesSlipItemList.get(i));
			//出庫した商品から同じ伝票の商品を探し一つにまとめる処理
			for (int j = i + 1; corpSalesSlipItemList.size() > j; j++) {
				long sysCorporateSalesSlipIdAnother = corpSalesSlipItemList.get(j).getSysCorporateSalesSlipId();
				//同じ伝票の商品があったらリストに格納
				if (sysCorporateSalesSlipId == sysCorporateSalesSlipIdAnother) {
					registrySlipItemList.add(corpSalesSlipItemList.get(j));
					//2つ以上商品があった伝票のIDをMapに記憶させる。(複数ある場合その分伝票が作られてしまうのを防ぐため)
					duplicateProtectionMap.put(sysCorporateSalesSlipId, sysCorporateSalesSlipId);
				}
			}

			//リストの商品が登録された業販伝票を検索するためのDTO
			ExtendCorporateSalesSlipDTO corporateSaleSlipDTO = new ExtendCorporateSalesSlipDTO();
			//Bremboの伝票を新規作成するためのDTO
			ExtendCorporateSalesSlipDTO newCorporateSaleSlipDTO = new ExtendCorporateSalesSlipDTO();
			//検索する伝票IDをセット
			corporateSaleSlipDTO.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
			//伝票IDをもとに伝票を検索し取得
			corporateSaleSlipDTO = corporateSaleDAO.getCorporateSaleSlip(corporateSaleSlipDTO);

			//KTS、BCR、KTS業販以外の法人は経理上別計上となるのでBrembo業販伝票自動生成対象外となる。
			if (!ArrayUtils.contains(BREMBO_CORP_SALE_SLIP_TARGET, corporateSaleSlipDTO.getSysCorporationId())) {
				continue;
			}

			//伝票上の法人IDで法人を検索し、法人名を取得
			String corporationName = corporationDAO.getCorporationName(corporateSaleSlipDTO.getSysCorporationId());
			//法人名から完全一致で得意先IDを検索
			long sysClientId = clientDAO.getSysClientId(corporationName);
			//得意先マスタを取得
			MstClientDTO clientDTO = clientDAO.getClient(sysClientId);

			//Brembo新規作成分の伝票に各値を格納
			//法人ID ※Brembo事業部は法人IDが12
			newCorporateSaleSlipDTO.setSysCorporationId(12);
			//伝票ステータス
			newCorporateSaleSlipDTO.setSlipStatus("2");
			//担当者
			newCorporateSaleSlipDTO.setPersonInCharge(corporateSaleSlipDTO.getPersonInCharge());
			//見積日
			newCorporateSaleSlipDTO.setEstimateDate(createDateToday);
			//注文日(画面上では受注日)
			newCorporateSaleSlipDTO.setOrderDate(createDateToday);
			//売上日
			newCorporateSaleSlipDTO.setSalesDate(createDateToday);
			//請求日
			newCorporateSaleSlipDTO.setBillingDate(corporateSaleSlipDTO.getBillingDate());
			//得意先ID
			newCorporateSaleSlipDTO.setSysClientId(sysClientId);
			//支払方法
			newCorporateSaleSlipDTO.setPaymentMethod(corporateSaleSlipDTO.getPaymentMethod());
			//納入期限
			newCorporateSaleSlipDTO.setDeliveryDeadline(corporateSaleSlipDTO.getDeliveryDeadline());
			//口座ID
			newCorporateSaleSlipDTO.setSysAccountId(12);
			//入金額
			newCorporateSaleSlipDTO.setReceivePrice(corporateSaleSlipDTO.getReceivePrice());
			//入金日
			newCorporateSaleSlipDTO.setReceiveDate(corporateSaleSlipDTO.getReceiveDate());
			/*
			 * 備考/一言メモ
			 * ・自動生成した伝票とわかるように文言を追加
			 * ※業販統計出力処理において自動生成した分の数も含まれてしまうため、自動生成分の伝票は数えない処理を追加した
			 * 　その際に備考/一言メモに「Brembo伝票自動生成分」の文言が入っているかで判断するため処理を追加した
			 * ※現在その処理は保留になりました。一応コメントアウトして残しています。
			 * */
			String remarks = corporateSaleSlipDTO.getOrderRemarks();
			//環境ごとに使える改行コード
			String newPar = System.getProperty("line.separator");
			//変に改行されてしまうのを防ぐため、空の時はそのまま、既に何かしら入力されていたら改行してから挿入
			if (remarks.isEmpty()) {
				remarks = "Brembo伝票自動生成分";
			} else {
				remarks = remarks + newPar + "Brembo伝票自動生成分";
			}
			newCorporateSaleSlipDTO.setOrderRemarks(remarks);
			//注文確定書備考
			newCorporateSaleSlipDTO.setOrderFixRemarks(corporateSaleSlipDTO.getOrderFixRemarks());
			//見積書備考
			newCorporateSaleSlipDTO.setEstimateRemarks(corporateSaleSlipDTO.getEstimateRemarks());
			//請求書備考
			newCorporateSaleSlipDTO.setBillingRemarks(corporateSaleSlipDTO.getBillingRemarks());
			//入金日
			newCorporateSaleSlipDTO.setDepositDate(corporateSaleSlipDTO.getDepositDate());
			//納入先名
			newCorporateSaleSlipDTO.setDestinationNm(clientDTO.getClientNm());
			//納入先名(カナ)
			newCorporateSaleSlipDTO.setDestinationNmKana(clientDTO.getClientNmKana());
			//納入先郵便番号
			newCorporateSaleSlipDTO.setDestinationZip(clientDTO.getZip());
			//納入先住所(都道府県)
			newCorporateSaleSlipDTO.setDestinationPrefectures(clientDTO.getPrefectures());
			//納入先住所(市区町村)
			newCorporateSaleSlipDTO.setDestinationMunicipality(clientDTO.getMunicipality());
			//納入先住所(市区町村以降)
			newCorporateSaleSlipDTO.setDestinationAddress(clientDTO.getAddress());
			//納入先住所(建物名)
			newCorporateSaleSlipDTO.setDestinationBuildingNm(clientDTO.getBuildingNm());
			//納入先会社名
			newCorporateSaleSlipDTO.setDestinationCompanyNm(clientDTO.getClientNm());
			//納入先部署名
			newCorporateSaleSlipDTO.setDestinationQuarter(clientDTO.getQuarter());
			//納入先役職名
			newCorporateSaleSlipDTO.setDestinationPosition(clientDTO.getPosition());
			//納入先御担当者名
			newCorporateSaleSlipDTO.setDestinationContactPersonNm(clientDTO.getContactPersonNm());
			//納入先電話番号
			newCorporateSaleSlipDTO.setDestinationTel(clientDTO.getTel());
			//納入先FAX番号
			newCorporateSaleSlipDTO.setDestinationFax(clientDTO.getFax());
			//備考/一言メモ(納入先)
			newCorporateSaleSlipDTO.setSenderRemarks(corporateSaleSlipDTO.getSenderRemarks());
			//運送会社システム
			newCorporateSaleSlipDTO.setTransportCorporationSystem(corporateSaleSlipDTO.getTransportCorporationSystem());
			//送り状種別
			newCorporateSaleSlipDTO.setInvoiceClassification(corporateSaleSlipDTO.getInvoiceClassification());
			//お届け指定日
			newCorporateSaleSlipDTO.setDestinationAppointDate(corporateSaleSlipDTO.getDestinationAppointDate());
			//お届け時間帯
			newCorporateSaleSlipDTO.setDestinationAppointTime(corporateSaleSlipDTO.getDestinationAppointTime());
			//出荷日
			newCorporateSaleSlipDTO.setShipmentDate(corporateSaleSlipDTO.getShipmentDate());
			//税率
			int taxRate = cSServie.getTaxRate(newCorporateSaleSlipDTO.getOrderDate());

			newCorporateSaleSlipDTO.setTaxRate(taxRate);

			//通貨
			newCorporateSaleSlipDTO.setCurrency(corporateSaleSlipDTO.getCurrency());
			//返品フラグ
			newCorporateSaleSlipDTO.setReturnFlg("0");

			//売上合計
			int sumSalesPrice = 0;
			//商品単価小計
			int sumPieceRate = 0;
			//税
			int tax = 0;

			//新規登録用商品リスト作成
			List<ExtendCorporateSalesItemDTO> newRegistrySalesItemList = new ArrayList<>();

			//ここから商品の金額計算及び新規伝票に紐付く新規業販伝票商品を作成
			/* *
			 * 新規Brembo伝票
			 * 　単価：出庫した商品の原価
			 * 　原価：(商品マスタに紐付く)Brembo商品の原価
			 * */
			for (ExtendCorporateSalesItemDTO salesItemDTO : registrySlipItemList) {
				//業販伝票商品を新規登録するためのDTO
				ExtendCorporateSalesItemDTO newSalesItemDTO = new ExtendCorporateSalesItemDTO();
				//出庫した伝票の商品の原価が、新規Brembo伝票の単価になる。
				int itemCost = salesItemDTO.getCost();
				//商品IDからBremboの原価を取得(Bremboの法人IDは12)
				int bremboItemCost = itemDAO.getItemCost(salesItemDTO.getSysItemId(), 12).getCost();
				//kind原価取得
				int kindCost = (int)itemDAO.getKindCostDTO(salesItemDTO.getSysItemId()).getKindCost();

				//売上合計を加算 (単価×出庫数)
				sumSalesPrice += salesItemDTO.getCost() * salesItemDTO.getLeavingNum();
				//商品単価小計を加算
				sumPieceRate += salesItemDTO.getCost() * salesItemDTO.getLeavingNum();

				//商品ID
				newSalesItemDTO.setSysItemId(salesItemDTO.getSysItemId());
				//品番
				newSalesItemDTO.setItemCode(salesItemDTO.getItemCode());
				//商品名
				newSalesItemDTO.setItemNm(salesItemDTO.getItemNm());
				//注文数
				newSalesItemDTO.setOrderNum(salesItemDTO.getOrderNum());
				//単価 (出庫した商品の原価)
				newSalesItemDTO.setPieceRate(itemCost);
				//原価 (Bremboの原価)
				newSalesItemDTO.setCost(bremboItemCost);
				//出庫予定日
				newSalesItemDTO.setScheduledLeavingDate(createDateToday);
				//出庫日
				newSalesItemDTO.setLeavingDate(createDateToday);
				//出庫数
				newSalesItemDTO.setLeavingNum(salesItemDTO.getLeavingNum());
				//売上日
				newSalesItemDTO.setSalesDate(createDateToday);
				//ピッキングリスト出力フラグ (ピッキングリストは出力されたものとしてみなす)
				newSalesItemDTO.setPickingListFlg("on");
				//kind原価
				newSalesItemDTO.setKindCost(kindCost);

				newRegistrySalesItemList.add(newSalesItemDTO);

			}

			//税計算(小数点以下は切り捨て)
			tax = (int) Math.floor(sumPieceRate * taxRate / 100);

			//売上合計
			newCorporateSaleSlipDTO.setSumSalesPrice(sumSalesPrice + tax);
			//合計請求金額
			newCorporateSaleSlipDTO.setSumClaimPrice(sumPieceRate + tax);
			//商品単価小計
			newCorporateSaleSlipDTO.setSumPieceRate(sumPieceRate);
			//税
			newCorporateSaleSlipDTO.setTax(tax);

			//業販伝票を登録
			registryCorporateSalesSlip(newCorporateSaleSlipDTO);
			//業販伝票に紐付く商品を登録
			registryCorporateSaleItemList(newRegistrySalesItemList, newCorporateSaleSlipDTO.getSysCorporateSalesSlipId(), newCorporateSaleSlipDTO);

		}

	}

	public void registryReturnCorporateSalesSlip(ExtendCorporateSalesSlipDTO slip)
			throws DaoException {

		registryCorporateSalesSlip(slip);

		setFlagsDB(slip);
		new CorporateSaleDAO().updateCorporateSalesSlipFlg(slip);

		//shipmentDate出荷日をnullにして更新しなおしてる？
		updateReturnFlag(slip);
	}

	private void updateReturnFlag(ExtendCorporateSalesSlipDTO slip) throws DaoException {

		ExtendCorporateSalesSlipDTO registryCorporateSalesSlipDTO = new ExtendCorporateSalesSlipDTO();
		registryCorporateSalesSlipDTO.setReturnFlg(StringUtil.SWITCH_ON_VALUE);
		registryCorporateSalesSlipDTO.setSysCorporateSalesSlipId(slip.getSysCorporateSalesSlipId());

		// dto.setReturnFlg(StringUtil.SWITCH_ON_VALUE);

		new CorporateSaleDAO().updateCorporateSalesSlipFlg(registryCorporateSalesSlipDTO);
	}

	/**
	 * 返品伝票？
	 * @param itemList
	 * @param slip
	 * @throws DaoException
	 */
	public void registryReturnCorporateSalesItem(List<ExtendCorporateSalesItemDTO> itemList,
			ExtendCorporateSalesSlipDTO slip) throws DaoException {

		registryCorporateSaleItemList(itemList, slip.getSysCorporateSalesSlipId(), slip);

		// 返品伝票の場合、原価チェックフラグを未チェックに更新する。
		CorporateSaleDAO corpSaleDAO = new CorporateSaleDAO();
		corpSaleDAO.updateCorpSaleItemCostCheckFlag(slip.getSysCorporateSalesSlipId(), "0");

	}

	/**
	 * 出庫判定を行います　一時的に在庫を減らしつつ出庫の妥当性判定を行います
	 * なので同時に在庫一覧から在庫数変更などが行われるとバグの危険性を持っています
	 *
	 *
	 * @param leaveStockList
	 * @return
	 * @throws DaoException
	 */
	public ErrorObject<List<ExtendCorporateSalesItemDTO>> checkLeaveStock(
			List<ExtendCorporateSalesItemDTO> leaveStockList) throws DaoException {

		TransactionDAO transactionDAO = new TransactionDAO();
		// 出庫チェックが始まった時点のデータを記憶させます
		transactionDAO.begin();
		ErrorObject<List<ExtendCorporateSalesItemDTO>> result = new ErrorObject<>();

		if (leaveStockList == null) {

			result.getErrorMessageList().add(
					new ErrorMessageDTO("未出庫の伝票がありません"));
			return result;
		}

		List<ExtendCorporateSalesItemDTO> checkedItemList = null;
		long[] arrSysSalesSlipId = new long[leaveStockList.size()];
		int idx = 0;
		for (ExtendCorporateSalesItemDTO item : leaveStockList) {

			result.setSuccess(true);

			ItemService itemService = new ItemService();

			// 構成商品（セット商品）から出庫する場合
			if (item.getSysItemId() != 0
					&& StringUtils.equals(item.getLeaveClassFlg(), "1")) {

				// 構成商品の出庫チェック
				result.setSuccess(checkSetItemStock(item.getSysItemId(),
						item.getLeaveNum(), item.getOrderNo(), item.getOrderName(), result));

				// 失敗の場合
				// 大まかだけどエラーの原因をセット
				if (!result.isSuccess()) {

					// 失敗の場合同伝票を探して除去する
					arrSysSalesSlipId[idx++] = item.getSysCorporateSalesSlipId();
					continue;
				}

				// 一時的に在庫減らす（return直前でロールバックします）
				updateSetItemStock(item.getSysItemId(), item.getLeaveNum(),
						item.getOrderNo(), item.getOrderName());

				// (キープなし)自在庫から出庫する場合で出庫数より仮在庫数が少ない場合
			} else if (item.getSysItemId() != 0
					&& StringUtils.equals(item.getLeaveClassFlg(), "0")
					&& itemService.getSysKeepId(item.getOrderNo(),
							item.getSysItemId()) == 0
					&& item.getLeaveNum() > itemService.getTemporaryStockNum(item
							.getSysItemId())) {

				result.setSuccess(false);
				result.getErrorMessageList().add(
						new ErrorMessageDTO("受注番号：　" + item.getOrderNo() + "　品番：　"
								+ item.getItemCode() + "　の在庫が不足しています"));

				arrSysSalesSlipId[idx++] = item.getSysCorporateSalesSlipId();

				continue;

				// (キープあり)自在庫から出庫する場合で出庫数より総在庫数が少ない場合
			} else if (item.getSysItemId() != 0
					&& StringUtils.equals(item.getLeaveClassFlg(), "0")
					&& itemService.getSysKeepId(item.getOrderNo(),
							item.getSysItemId()) != 0
					&& item.getLeaveNum() > itemService
							.getTotalWarehouseStockNum(item.getSysItemId())) {

				result.setSuccess(false);
				result.getErrorMessageList().add(
						new ErrorMessageDTO("受注番号：　" + item.getOrderNo() + "　品番：　"
								+ item.getItemCode() + "　の在庫が不足しています"));

				// 失敗の場合同伝票を探して除去する
				// removeSales(checkedSalesItemList, dto.getSysSalesSlipId());
				arrSysSalesSlipId[idx++] = item.getSysCorporateSalesSlipId();

				continue;

			}

			// 一時的に在庫減らす（return直前でロールバックします）
			itemService.lumpUpdateStock(item.getSysItemId(), item.getLeaveNum());

			if (checkedItemList == null) {
				checkedItemList = new ArrayList<>();
			}

			checkedItemList.add(item);
		}
		// 失敗した伝票IDをチェック済みリストから除去
		for (int i = 0; i < idx; i++) {
			removeSales(checkedItemList, arrSysSalesSlipId[i]);
		}

		// 出庫チェックがが始まった時点のデータに戻します
		transactionDAO.rollback();
		result.setResultObject(checkedItemList);
		return result;
	}

	private void removeSales(List<ExtendCorporateSalesItemDTO> checkedItemList,
			long sysCorporateSalesSlipId) {

		if (checkedItemList == null) {

			return;
		}

		for (int i = 0; i < checkedItemList.size(); i++) {

			ExtendCorporateSalesItemDTO dto = checkedItemList.get(i);

			if (sysCorporateSalesSlipId == 140) {
				System.out.println("aa");
			}
			if (dto.getSysCorporateSalesSlipId() == sysCorporateSalesSlipId) {

				checkedItemList.remove(i--);
			}
		}

	}

	private boolean checkSetItemStock(long sysItemId, int leaveNum,
			String orderNo, String orderName, ErrorObject<List<ExtendCorporateSalesItemDTO>> errorResult)
			throws DaoException {

		boolean result = true;
		List<ExtendSetItemDTO> setItemList = new ItemService()
				.getSetItemList(sysItemId);
		MstItemDTO mstDto = new ItemService().getMstItemDTO(sysItemId);
		String itemCode = mstDto.getItemCode();

		if (setItemList == null) {
			result = false;
		}

		// 構成商品の在庫チェック
		for (ExtendSetItemDTO item : setItemList) {

			ItemService itemService = new ItemService();
			// 構成商品が通常の商品の場合
			if (!item.getLeaveClassFlg().equals("1")) {

				// (キープなし)出庫数より仮在庫数が少ない場合
				if (itemService.getSysKeepId(orderNo, item.getFormSysItemId()) == 0
						&& item.getItemNum() * leaveNum > itemService
								.getTemporaryStockNum(item.getFormSysItemId())) {

					errorResult.getErrorMessageList().add(
							new ErrorMessageDTO("伝票番号：　" + orderNo + "　セット商品　"
									+ itemCode + "　の構成商品：　" + item.getItemCode()
									+ "　が不足しています"));
					result = false;
					return result;
					// (キープあり)出庫数より総在庫数が少ない場合
				} else if (itemService.getSysKeepId(orderNo,
						item.getFormSysItemId()) != 0
						&& item.getItemNum() * leaveNum > itemService
								.getTotalWarehouseStockNum(item
										.getFormSysItemId())) {
					errorResult.getErrorMessageList().add(
							new ErrorMessageDTO("伝票番号：　" + orderNo + "　セット商品　"
									+ itemCode + "　の構成商品：　" + item.getItemCode()
									+ "　が不足しています"));
					result = false;
					return result;
				}
				// 構成商品がセット商品だった場合(1層目)
			} else if (item.getLeaveClassFlg().equals("1")) {

				List<ExtendSetItemDTO> secondSetItemList = new ItemService()
						.getSetItemList(item.getFormSysItemId());

				for (ExtendSetItemDTO secondSetItem : secondSetItemList) {
					// 構成商品が通常の商品の場合
					if (!secondSetItem.getLeaveClassFlg().equals("1")) {

						// (キープなし)出庫数より仮在庫数が少ない場合
						if (itemService.getSysKeepId(orderNo,
								secondSetItem.getFormSysItemId()) == 0
								&& secondSetItem.getItemNum()
										* item.getItemNum() * leaveNum > itemService
											.getTemporaryStockNum(secondSetItem
													.getFormSysItemId())) {
							errorResult.getErrorMessageList().add(
									new ErrorMessageDTO("伝票番号：　" + orderNo
											+ "　セット商品　" + itemCode + "　の構成商品：　"
											+ secondSetItem.getItemCode()
											+ "　が不足しています"));
							result = false;
							return result;
							// (キープあり)出庫数より総在庫が少ない場合
						} else if (itemService.getSysKeepId(orderNo,
								secondSetItem.getFormSysItemId()) != 0
								&& secondSetItem.getItemNum()
										* item.getItemNum() * leaveNum > itemService
											.getTotalWarehouseStockNum(secondSetItem
													.getFormSysItemId())) {
							errorResult.getErrorMessageList().add(
									new ErrorMessageDTO("伝票番号：　" + orderNo
											+ "　セット商品　" + itemCode + "　の構成商品：　"
											+ secondSetItem.getItemCode()
											+ "　が不足しています"));
							result = false;
							return result;
						}
						// 構成商品がセット商品だった場合(2層目)
					} else if (secondSetItem.getLeaveClassFlg().equals("1")) {

						List<ExtendSetItemDTO> thirdSetItemList = new ItemService()
								.getSetItemList(secondSetItem
										.getFormSysItemId());

						for (ExtendSetItemDTO thirdSetItem : thirdSetItemList) {
							// 構成商品が通常の商品の場合
							if (!thirdSetItem.getLeaveClassFlg().equals("1")) {

								// (キープなし)出庫数より仮在庫数が少ない場合
								if (itemService.getSysKeepId(orderNo,
										thirdSetItem.getFormSysItemId()) == 0
										&& thirdSetItem.getItemNum()
												* secondSetItem.getItemNum()
												* item.getItemNum() * leaveNum > itemService
													.getTemporaryStockNum(thirdSetItem
															.getFormSysItemId())) {
									errorResult.getErrorMessageList().add(
											new ErrorMessageDTO("伝票番号：　"
													+ orderNo
													+ "　セット商品　"
													+ itemCode
													+ "　の構成商品：　"
													+ thirdSetItem
															.getItemCode()
													+ "　が不足しています"));
									result = false;
									return result;
									// (キープあり)出庫数より総在庫が少ない場合
								} else if (itemService.getSysKeepId(orderNo,
										thirdSetItem.getFormSysItemId()) != 0
										&& thirdSetItem.getItemNum()
												* secondSetItem.getItemNum()
												* item.getItemNum() * leaveNum > itemService
													.getTotalWarehouseStockNum(thirdSetItem
															.getFormSysItemId())) {
									errorResult.getErrorMessageList().add(
											new ErrorMessageDTO("伝票番号：　"
													+ orderNo
													+ "　セット商品　"
													+ itemCode
													+ "　の構成商品：　"
													+ thirdSetItem
															.getItemCode()
													+ "　が不足しています"));
									result = false;
									return result;
								}
								// 構成商品がセット商品の場合(3層目)
							} else if (thirdSetItem.getLeaveClassFlg().equals(
									"1")) {

								List<ExtendSetItemDTO> fourthSetItemList = new ItemService()
										.getSetItemList(thirdSetItem
												.getFormSysItemId());

								for (ExtendSetItemDTO fourthSetItem : fourthSetItemList) {
									// 構成商品が通常の商品の場合
									if (!fourthSetItem.getLeaveClassFlg()
											.equals("1")) {

										// (キープなし)出庫数より仮在庫数が少ない場合
										if (itemService.getSysKeepId(orderNo,
												fourthSetItem
														.getFormSysItemId()) == 0
												&& fourthSetItem.getItemNum()
														* thirdSetItem
																.getItemNum()
														* secondSetItem
																.getItemNum()
														* item.getItemNum()
														* leaveNum > itemService
															.getTemporaryStockNum(fourthSetItem
																	.getFormSysItemId())) {
											errorResult
													.getErrorMessageList()
													.add(new ErrorMessageDTO(
															"伝票番号：　"
																	+ orderNo
																	+ "　セット商品　"
																	+ itemCode
																	+ "　の構成商品：　"
																	+ fourthSetItem
																			.getItemCode()
																	+ "　が不足しています"));
											result = false;
											return result;
											// (キープあり)出庫数より総在庫数が少ないとき
										} else if (itemService.getSysKeepId(
												orderNo, fourthSetItem
														.getFormSysItemId()) != 0
												&& fourthSetItem.getItemNum()
														* thirdSetItem
																.getItemNum()
														* secondSetItem
																.getItemNum()
														* item.getItemNum()
														* leaveNum > itemService
															.getTotalWarehouseStockNum(fourthSetItem
																	.getFormSysItemId())) {
											errorResult
													.getErrorMessageList()
													.add(new ErrorMessageDTO(
															"伝票番号：　"
																	+ orderNo
																	+ "　セット商品　"
																	+ itemCode
																	+ "　の構成商品：　"
																	+ fourthSetItem
																			.getItemCode()
																	+ "　が不足しています"));
											result = false;
											return result;
										}
										// 構成商品がセット商品の場合(4層目)
									} else if (fourthSetItem.getLeaveClassFlg()
											.equals("1")) {

										List<ExtendSetItemDTO> fifthSetItemList = new ItemService()
												.getSetItemList(fourthSetItem
														.getFormSysItemId());

										for (ExtendSetItemDTO fifthSetItem : fifthSetItemList) {
											// 構成商品が通常の商品の場合
											if (!fifthSetItem
													.getLeaveClassFlg().equals(
															"1")) {

												// (キープなし)出庫数より仮在庫数が少ない場合
												if (itemService
														.getSysKeepId(
																orderNo,
																fifthSetItem
																		.getFormSysItemId()) == 0
														&& fifthSetItem
																.getItemNum()
																* fourthSetItem
																		.getItemNum()
																* thirdSetItem
																		.getItemNum()
																* secondSetItem
																		.getItemNum()
																* item.getItemNum()
																* leaveNum > itemService
																	.getTemporaryStockNum(fifthSetItem
																			.getFormSysItemId())) {
													errorResult
															.getErrorMessageList()
															.add(new ErrorMessageDTO(
																	"伝票番号：　"
																			+ orderNo
																			+ "　セット商品　"
																			+ itemCode
																			+ "　の構成商品：　"
																			+ fifthSetItem
																					.getItemCode()
																			+ "　が不足しています"));
													result = false;
													return result;
													// (キープあり)出庫数より総在庫数が少ないとき
												} else if (itemService
														.getSysKeepId(
																orderNo,
																fifthSetItem
																		.getFormSysItemId()) != 0
														&& fifthSetItem
																.getItemNum()
																* fourthSetItem
																		.getItemNum()
																* thirdSetItem
																		.getItemNum()
																* secondSetItem
																		.getItemNum()
																* item.getItemNum()
																* leaveNum > itemService
																	.getTotalWarehouseStockNum(fifthSetItem
																			.getFormSysItemId())) {
													errorResult
															.getErrorMessageList()
															.add(new ErrorMessageDTO(
																	"伝票番号：　"
																			+ orderNo
																			+ "　セット商品　"
																			+ itemCode
																			+ "　の構成商品：　"
																			+ fifthSetItem
																					.getItemCode()
																			+ "　が不足しています"));
													result = false;
													return result;
												}
											} else if (fifthSetItem
													.getLeaveClassFlg().equals(
															"1")) {

												errorResult
														.getErrorMessageList()
														.add(new ErrorMessageDTO(
																"品番：　"
																		+ item.getItemCode()
																		+ "　はセット商品の構造が深すぎます"));
												result = false;
												return result;

											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 出庫処理・キープ削除・総在庫数更新を行います
	 *
	 * @param list
	 * @return
	 * @throws DaoException
	 */
	public ErrorDTO leaveStock(List<ExtendCorporateSalesItemDTO> list)
			throws Exception {

		ErrorDTO errorDTO = new ErrorDTO();

		if (list == null) {
			return errorDTO;
		}

		ItemService itemService = new ItemService();

		for (ExtendCorporateSalesItemDTO item : list) {

			// 構成商品（セット商品）から出庫する場合
			if (StringUtils.equals(item.getLeaveClassFlg(), "1")) {
				//構成商品出庫処理
				updateSetItemStock(item.getSysItemId(), item.getLeaveNum(),
						item.getOrderNo(), item.getOrderName());

			} else {

				// キープがあるかどうか取得
				long sysKeepId = itemService.getSysKeepId(item.getOrderNo(),
						item.getSysItemId());
				if (sysKeepId != 0) {

					// キープを削除
					itemService.deleteKeep(sysKeepId);
				}
				//倉庫在庫数変更、総在庫数計算
				itemService.lumpUpdateStock(item.getSysItemId(), item.getLeaveNum());
			}
		}

		/* 組立可数プロシージャ化のため処理を凍結 20171107 Y_SAITO*/
//		for(ExtendCorporateSalesItemDTO item : list){
//			//セット商品の組立可数・構成商品も含めて総計算
//			itemService.setAllAssemblyNum(item.getSysItemId());
//
//		}

		for (ExtendCorporateSalesItemDTO item : list) {

			if (item.getLeaveNum() != 0) {
				// ここで出庫日を設定しているためここでバグデータが発生するのかも
				//出庫日指定は現在（今日日付）
				//商品を出庫済みにする
				updateLeaveStock(item);
				//出庫時に伝票の売上合計を更新
				updateSalesPrice(item);
			}
		}

		return errorDTO;
	}

	/**
	 * 構成商品出庫処理
	 *
	 * @param sysItemId
	 *            商品ID
	 * @param num
	 *            変動数
	 * @param orderName
	 * @return
	 * @throws DaoException
	 */
	private void updateSetItemStock(long sysItemId, int num, String orderNo, String orderName)
			throws DaoException {

		List<ExtendSetItemDTO> setItemList = new ItemService()
				.getSetItemList(sysItemId);

		ItemService itemService = new ItemService();

		for (ExtendSetItemDTO item : setItemList) {
			if (item.getLeaveClassFlg().equals("0")) {

				long sysKeepId = itemService.getSysKeepId(orderNo,
						item.getFormSysItemId());
				if (sysKeepId != 0) {
					// キープ削除処理
					itemService.deleteKeep(sysKeepId);
				}

				// 在庫更新処理
				itemService.lumpUpdateStock(item.getFormSysItemId(),
						item.getItemNum() * num);

			} else if (item.getLeaveClassFlg().equals("1")) {
				// 1層目の構成商品リストを取得
				List<ExtendSetItemDTO> secondItemList = new ItemService()
						.getSetItemList(item.getFormSysItemId());

				for (ExtendSetItemDTO secondItem : secondItemList) {
					if (secondItem.getLeaveClassFlg().equals("0")) {

						long sysKeepId = itemService.getSysKeepId(orderNo,
								secondItem.getFormSysItemId());
						if (sysKeepId != 0) {
							// キープ削除処理
							itemService.deleteKeep(sysKeepId);
						}
						// 在庫更新処理
						itemService.lumpUpdateStock(
								secondItem.getFormSysItemId(),
								secondItem.getItemNum() * item.getItemNum()
										* num);

					} else if (secondItem.getLeaveClassFlg().equals("1")) {
						// 2層目の構成商品リストを取得
						List<ExtendSetItemDTO> thirdItemList = new ItemService()
								.getSetItemList(secondItem.getFormSysItemId());
						for (ExtendSetItemDTO thirdItem : thirdItemList) {
							if (thirdItem.getLeaveClassFlg().equals("0")) {

								long sysKeepId = itemService.getSysKeepId(
										orderNo, thirdItem.getFormSysItemId());
								if (sysKeepId != 0) {
									// キープ削除処理
									itemService.deleteKeep(sysKeepId);
								}
								// 在庫更新処理
								itemService.lumpUpdateStock(
										thirdItem.getFormSysItemId(),
										thirdItem.getItemNum()
												* secondItem.getItemNum()
												* item.getItemNum() * num);

							} else if (thirdItem.getLeaveClassFlg().equals("1")) {
								// 3層目の構成商品リストを取得
								List<ExtendSetItemDTO> fourthItemList = new ItemService()
										.getSetItemList(thirdItem
												.getFormSysItemId());
								for (ExtendSetItemDTO fourthItem : fourthItemList) {
									if (fourthItem.getLeaveClassFlg().equals(
											"0")) {

										long sysKeepId = itemService
												.getSysKeepId(orderNo,
														fourthItem.getFormSysItemId());
										if (sysKeepId != 0) {
											// キープを削除
											itemService.deleteKeep(sysKeepId);
										}
										// 在庫更新処理
										itemService.lumpUpdateStock(
												fourthItem.getFormSysItemId(),
												fourthItem.getItemNum()
														* thirdItem
																.getItemNum()
														* secondItem
																.getItemNum()
														* item.getItemNum()
														* num);
									} else if (fourthItem.getLeaveClassFlg().equals("1")) {
									// 4層目の構成商品リストを取得
									List<ExtendSetItemDTO> fifthItemList = new ItemService()
											.getSetItemList(fourthItem
													.getFormSysItemId());
									for (ExtendSetItemDTO fifthItem : fifthItemList) {
										if (fifthItem.getLeaveClassFlg().equals(
												"0")) {

											long sysKeepId = itemService
													.getSysKeepId(orderNo,
															fifthItem.getFormSysItemId());
											if (sysKeepId != 0) {
												// キープを削除
												itemService.deleteKeep(sysKeepId);
											}
											// 在庫更新処理
											itemService.lumpUpdateStock(
													fifthItem.getFormSysItemId(),
													fifthItem.getItemNum()
													* fourthItem.getItemNum()
															* thirdItem
																	.getItemNum()
															* secondItem
																	.getItemNum()
															* item.getItemNum()
															* num);
										}
									}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 伝票Listを出庫済みにします
	 *
	 * @param list
	 * @throws DaoException
	 */
	public void updateLeaveStockList(List<ExtendSalesSlipDTO> list)
			throws DaoException {

		for (ExtendSalesSlipDTO slip : list) {

			updateLeaveStock(slip.getSysSalesSlipId());
		}
	}

	/**
	 * 伝票を出庫済みにします
	 *
	 * @param sysSalesSlipId
	 * @throws DaoException
	 */
	private void updateLeaveStock(long setSysCorporateSalesSlipId) throws DaoException {

		ExtendCorporateSalesSlipDTO slip = new ExtendCorporateSalesSlipDTO();
		slip.setSysCorporateSalesSlipId(setSysCorporateSalesSlipId);
		/** 20140521 Takakuwa 出庫時に現在の日付を出荷日として登録する処理を追加 */
		slip.setShipmentDate(DateUtil.dateToString("yyyy/MM/dd"));
		new CorporateSaleDAO().updateCorporateSalesSlipFlg(slip);

	}

	/**
	 * アイテムを出庫済みにします
	 * @param item
	 * @throws DaoException
	 */
	private void updateLeaveStock(ExtendCorporateSalesItemDTO item) throws DaoException {


//		dto.setLeavingNum(dto.getLeavingNum() + dto.getLeaveNum());
		item.setLeavingNum(item.getLeaveNum());
		/** 20140521 Takakuwa 出庫時に現在の日付を出庫日として登録する処理を追加 */
		String date = DateUtil.dateToString("yyyy/MM/dd");
		item.setLeavingDate(date);
		item.setSalesDate(date);
//		dto.setBillingFlag(StringUtil.switchCheckBoxDB(dto.getBillingFlag()));
		setFlagsDB(item);

		new CorporateSaleDAO().updateCorporateSalesItem(item);
	}

	public void updatePickFlg(List<ExtendCorporateSalesSlipDTO> salesSlipList, CorporateSaleSearchDTO searchDTO)
			throws DaoException {

		for (ExtendCorporateSalesSlipDTO slip : salesSlipList) {

			//伝票毎の設定だったものを商品毎に変更
			for (ExtendCorporateSalesItemDTO item : slip.getCorporateSalesItemList()){

				if (StringUtils.equals(item.getPickingListFlg(), "on")) {
					continue;
				}

				//検索時に出庫予定日の指定があれば、合致しない場合にはチェックを付けない
				if (StringUtils.isNotEmpty(searchDTO.getScheduledLeavingDateFrom())
						&& item.getScheduledLeavingDate().compareTo(searchDTO.getScheduledLeavingDateFrom()) < 0) {
					continue;
				}

				if (StringUtils.isNotEmpty(searchDTO.getScheduledLeavingDateTo())
						&& item.getScheduledLeavingDate().compareTo(searchDTO.getScheduledLeavingDateTo()) > 0) {
					continue;
				}

				//フラグ読み替えがupdateCorporateSalesItem内で行われるため、表示用の値をセット
				item.setPickingListFlg("on");
				updateCorporateSalesItem(item);
			}
		}
	}

	public void deleteCorporateSalesSlip(long sysCorporateSalesSlipId) throws DaoException {

		new CorporateSaleDAO().deleteCorporateSalesSlip(sysCorporateSalesSlipId);
	}


	public void deleteCorporateSalesItem(List<ExtendCorporateSalesItemDTO> corporateSalesItemList) throws DaoException {

		for (ExtendCorporateSalesItemDTO item : corporateSalesItemList) {

			deleteCorporateSalesItem(item.getSysCorporateSalesItemId());
		}
	}

	private void deleteCorporateSalesItem(long sysCorporateSalesitemId) throws DaoException {

		new CorporateSaleDAO().deleteCorporateSalesItem(sysCorporateSalesitemId);
	}

	public StoreDTO selectShopInfo(long sysCorporationId, long sysChannelId)
			throws Exception {

		StoreDTO dto = new StoreDTO();
		dto = new SaleDAO().selectStoreInfo(sysCorporationId, sysChannelId);
		return dto;

	}

	/**
	 *
	 * @param leaveStockList
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendCorporateSalesItemDTO> sortLeaveStockList(
			List<ExtendCorporateSalesItemDTO> leaveStockList) throws DaoException {

		//出庫画面表示用Listが無ければ終了
		if (leaveStockList == null || leaveStockList.size() <= 1) {

			return leaveStockList;
		}

		// List<ExtendSalesSlipDTO> saleSlipList = new LinkedList<>();
		// long[] arrSyaSalesItemid = new long[leaveStockList.size()];
		// String[] arrOrderDateTime = new String[leaveStockList.size()];
		// int idx = 0;
		//出庫画面表示用List分ループ
		for (ExtendCorporateSalesItemDTO item : leaveStockList) {

			// saleSlipList.add(getSalesSlip(dto.getSysSalesSlipId()));
			//伝票を検索して出庫画面表示フラグ読替
			ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = getCorporateSalesSlip(item
					.getSysCorporateSalesSlipId());
			//注文日set
			item.setOrderDate(corporateSalesSlipDTO.getOrderDate());
//			dto.setOrderTime(corporateSalesSlipDTO.getOrderTime());
		}

		// 出庫画面表示用List分ループ
		for (int i = 0; i < leaveStockList.size(); i++) {

			// ExtendSalesSlipDTO salesSlipDTO = saleSlipList.get(i);
			ExtendCorporateSalesItemDTO leaveStockDTO = leaveStockList.get(i);
			// String strDate = salesSlipDTO.getOrderDate() +
			// salesSlipDTO.getOrderTime();
			//注文日と？を足している
			String strDate = leaveStockDTO.getOrderDate()
					+ leaveStockDTO.getOrderTime();
			// if (StringUtils.isEmpty(strDate)) {
			// strDate = "2000/01/0100:00:00";
			// }
			strDate = StringUtils.replace(strDate, "/", "");
			strDate = StringUtils.replace(strDate, ":", "");

			Date minDate = DateUtil.toDate(strDate, "yyyyMMddHHmmss");

			if (minDate == null) {
				minDate = DateUtil.toDate("20000101000000", "yyyyMMddHHmmss");
			}

			strDate = null;
			// System.out.println(minDate);
			int minIdx = i;

			for (int j = i + 1; j < leaveStockList.size(); j++) {

				// salesSlipDTO = saleSlipList.get(j);
				leaveStockDTO = leaveStockList.get(j);
				// strDate = salesSlipDTO.getOrderDate() +
				// salesSlipDTO.getOrderTime();
				strDate = leaveStockDTO.getOrderDate()
						+ leaveStockDTO.getOrderTime();
				// if (StringUtils.isEmpty(strDate)) {
				// strDate = "2000/01/0100:00:00";
				// }
				strDate = StringUtils.replace(strDate, "/", "");
				strDate = StringUtils.replace(strDate, ":", "");

				Date date = DateUtil.toDate(strDate, "yyyyMMddHHmmss");
				if (date == null) {
					date = DateUtil.toDate("20000101000000", "yyyyMMddHHmmss");
				}
				if (date.compareTo(minDate) < 0) {
					minDate = date;
					minIdx = j;
				}
			}
			// ExtendSalesSlipDTO tempSalesSlipDTO = saleSlipList.get(i);
			// saleSlipList.set(i, saleSlipList.get(minIdx));
			// saleSlipList.set(minIdx, tempSalesSlipDTO);
			ExtendCorporateSalesItemDTO tempLeaveStockDTO = leaveStockList.get(i);
			leaveStockList.set(i, leaveStockList.get(minIdx));
			leaveStockList.set(minIdx, tempLeaveStockDTO);
		}

		return leaveStockList;
	}

	//伝票の粗利を計算する
	public long getGrossMargin(ExtendCorporateSalesSlipDTO corporateSalesSlipDTO,
			List<ExtendCorporateSalesItemDTO> list, CorporateSaleSearchDTO corporateSaleSearchDTO) {

		long grossMargin = 0;
		String grossProfitCalc = corporateSaleSearchDTO.getGrossProfitCalc();

		long cost = 0;
		for (ExtendCorporateSalesItemDTO item : list) {

			cost += item.getCost() * item.getOrderNum();
		}
		if (StringUtils.equals(grossProfitCalc,
				WebConst.GROSS_PROFIT_CALC_TOTAL_CODE)) {

			grossMargin = corporateSalesSlipDTO.getSumClaimPrice() - cost;

		} else if (StringUtils.equals(grossProfitCalc,
				WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE)) {

			long tax = corporateSalesSlipDTO.getTax();
			cost += tax;

			grossMargin = corporateSalesSlipDTO.getSumPieceRate() - cost;
		}

		return grossMargin;
	}

	/**
	 * 出庫時に、伝票の売上合計を更新
	 * @param item
	 * @throws Exception
	 */
	private void updateSalesPrice (ExtendCorporateSalesItemDTO item) throws Exception {

		CorporateSaleDisplayService service = new CorporateSaleDisplayService();
		ExtendCorporateSalesSlipDTO slip = service.getCorporateSalesSlip(item.getSysCorporateSalesSlipId());
		List<ExtendCorporateSalesItemDTO> itemList  = service.getCorporateSalesItemList(
				slip.getSysCorporateSalesSlipId(), slip.getSysCorporationId());

		// 売上合計を計算
		int sumPieceRate = getSumSalesRate(itemList);
		int salesTax = 0;
		if (StringUtils.equals(slip.getCurrency(),"1")) {
			salesTax = getTax(sumPieceRate, slip.getTaxRate());
		}

		slip.setSumSalesPrice(calcSumSalesPrice(slip, sumPieceRate, salesTax));

		service.updateCorporateSalesSlip(slip);
	}

	/**
	 * 伝票内の商品の出庫状況を確認し出庫フラグを立てる
	 * 一部出庫された商品は、未出庫分を別商品として登録する
	 * @param slipList
	 * @throws Exception
	 */
	public void updateLeaveStatus (List<ExtendCorporateSalesSlipDTO> slipList) throws Exception {

		for (ExtendCorporateSalesSlipDTO slipDto : slipList) {
			CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
			List<ExtendCorporateSalesItemDTO> itemList = corporateSaleDisplayService.getCorporateSalesItemList(
					slipDto.getSysCorporateSalesSlipId(), slipDto.getSysCorporationId());
			boolean leaveStatus = true;

			//伝票内の商品の出庫状況をチェック
			for (ExtendCorporateSalesItemDTO item : itemList) {
				//未出庫、一部出庫
				if (StringUtils.isEmpty(item.getLeavingDate()) || item.getOrderNum() > item.getLeavingNum()) {
					leaveStatus = false;

					//バグデータ対策として
					//出庫予定日が今日でないものは除外
					if (!StringUtils.equals(item.getScheduledLeavingDate(), StringUtil.getToday())) {
						continue;
					}

					//一部出庫された商品は、未出庫分を別商品として登録しなおす（請求計算のため）
					if (item.getLeavingNum() > 0) {
						int orderNum = item.getOrderNum();
						item.setOrderNum(item.getLeavingNum());
	//					item.setBillingFlag(StringUtil.switchCheckBoxDB(item.getBillingFlag()));
						corporateSaleDisplayService.updateCorporateSalesItem(item);

						//未出庫分を新規登録
						ExtendCorporateSalesItemDTO notLeaveItem = new ExtendCorporateSalesItemDTO();
						notLeaveItem = item;
						notLeaveItem.setSysCorporateSalesItemId(0);
						notLeaveItem.setOrderNum(orderNum - item.getLeavingNum());
						notLeaveItem.setScheduledLeavingDate("");
						notLeaveItem.setLeavingDate("");
						notLeaveItem.setSalesDate("");
						notLeaveItem.setLeavingNum(0);
						corporateSaleDisplayService.registryCorporateSaleItem(notLeaveItem, slipDto.getSysCorporateSalesSlipId(), slipDto);
					}
//					break;
				}
			}

			//全ての商品が注文数通り出庫済みなら出庫済にする
			if (leaveStatus) {
				updateLeaveStock(slipDto.getSysCorporateSalesSlipId());
			}
		}
	}

	public CorporateSaleSearchDTO getBillSearchCondition(long sysCorporationId, String exportMonth, String currency) throws ParseException {

		CorporateSaleSearchDTO searchDto = new CorporateSaleSearchDTO();

		if (sysCorporationId != 0) {
			searchDto.setSysCorporationId(sysCorporationId);
		}

		//入力された月とその前月のデータを取得
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
		String targetDateStr = exportMonth + "/01";
		Date exportMonthDate =format.parse(targetDateStr);
		//from(前月の1日)
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		cal.setTime(exportMonthDate);
		searchDto.setLeavingDateFrom(format.format(cal.getTime()));
		//to(当月の末日）
		cal.setTime(exportMonthDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		searchDto.setLeavingDateTo(format.format(cal.getTime()));


		searchDto.setSlipStatus("3");
		searchDto.setInvalidFlag("0");
		searchDto.setSearchAllFlg("1");

		//テスト用
		//dto.setSysClientId(1);

		return searchDto;
	}

	/**
	 * 請求先ごとにまとめた請求書のリストを取得する
	 *
	 * @param sysCorporationId
	 * @param exportMonth
	 * @param corpSaleItemList
	 * @param currency
	 * @return
	 * @throws Exception
	 */
	public List<ExportCorporateBillDTO> getExportCorporateBill(
			long sysCorporationId, String exportMonth, List<ExtendCorporateSalesItemDTO> corpSaleItemList, String currency, String selectedCutoff) throws Exception {

		List<ExtendCorporateSalesSlipDTO> salesSlipList = new ArrayList<>();
		CorporateBillDAO billDao = new CorporateBillDAO();
		ExportCorporateBillDTO lastMonthBillDto = new ExportCorporateBillDTO();
		ClientDAO clientDao = new ClientDAO();


		//該当アイテムをもとにSlipIDリスト取得
		List<Long> sysCorporateSalesSlipIdList =  new ArrayList<Long>();
		int i = 0;
		for (ExtendCorporateSalesItemDTO corpSaleItems : corpSaleItemList) {
			//伝票重複チェック
			System.out.println(corpSaleItems.getSysCorporateSalesSlipId());
			if (sysCorporateSalesSlipIdList.contains(corpSaleItems.getSysCorporateSalesSlipId())){
				continue;
			}
			//重複がなければ追加
			sysCorporateSalesSlipIdList.add(corpSaleItems.getSysCorporateSalesSlipId());
		}

		//当月分のデータ
		List<ExtendCorporateSalesItemDTO> itemList = corpSaleItemList;
		for (long sysCorporateSalesSlipId : sysCorporateSalesSlipIdList) {

			CorporateSaleSearchDTO saleSearchDTO = new CorporateSaleSearchDTO();
			List<ExtendCorporateSalesItemDTO> perSlipItemList = new ArrayList<ExtendCorporateSalesItemDTO>();

			//伝票情報取得
			saleSearchDTO.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
			CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
			ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = corporateSaleDAO.getSearchCorporateSalesSlip(saleSearchDTO);

			if (corporateSalesSlipDTO.getSysClientId() == (2332)) {
				System.out.print(false);

			}

			setFlagsDisp(corporateSalesSlipDTO);

			//掛売でなければ除外
			if (!StringUtils.equals(corporateSalesSlipDTO.getPaymentMethod(), "1")) {
				continue;
			}

			//伝票に紐付くアイテム情報
			for (i =0; i < itemList.size(); i++) {
				if (itemList.get(i).getSysCorporateSalesSlipId()==sysCorporateSalesSlipId){
					perSlipItemList.add(itemList.get(i));
				}
			}
			if (itemList.size() <= 0) {
				continue;
			}
			corporateSalesSlipDTO.setCorporateSalesItemList(cordinateItemList(perSlipItemList));


			//得意先IDから当該得意先の請求先を求める
			String billingDstNm = "";
			MstClientDTO clientDto = clientDao.getClient(corporateSalesSlipDTO.getSysClientId());
			if (clientDto.getBillingDst()!=null){
				billingDstNm = clientDto.getBillingDst();
			}
			//請求先の入力先がある場合、名称をもとに請求先の得意先としてのIDを求める
			if (!billingDstNm.isEmpty()){
				String billingDstNo = clientDao.getClientNmWithName(billingDstNm, sysCorporationId);
				//もし見つかれば、IDをそちらに変更する。見つからなければ元のまま放置。
				if (!StringUtils.isEmpty(billingDstNo)){
					corporateSalesSlipDTO.setSysClientId(Integer.parseInt(billingDstNo));
				}
			}

			salesSlipList.add(corporateSalesSlipDTO);
		}

		//TODO ここで法人IDの全ての請求先を検索して請求書を作成させる
//		ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = new ExtendCorporateSalesSlipDTO();
//		setFlagsDisp(corporateSalesSlipDTO);
		String billingDstNm = "";

		//なぜかFormだとStringなのでint型に変換
		int selectedCutoffDate = Integer.parseInt(selectedCutoff);

		//請求先名取得
		List<MstClientDTO> clientList = clientDao.getClientBillingNm(sysCorporationId);
		List<MstClientDTO> selectCutOffclientList = new  ArrayList<MstClientDTO>();

		for (int k = 0; k < clientList.size(); k++) {
			//BONCRE-2485 選択された締日と得意先が同じでなければ除外
			if (selectedCutoffDate == clientList.get(k).getCutoffDate()) {
				selectCutOffclientList.add(clientList.get(k));
				continue;
			}
		}

		i = salesSlipList.size();
		//請求先名が見つかった場合は
		for (int j = 0; j < selectCutOffclientList.size(); j++) {

			if (selectCutOffclientList.get(j).getBillingDst()!=null){
				if (!selectCutOffclientList.get(j).getBillingDst().isEmpty()) {
					billingDstNm = selectCutOffclientList.get(j).getBillingDst();
				}
			}

			String billingDstNo = "";
			//請求先の入力先がある場合、名称をもとに請求先の得意先としてのIDを求める
			if (!billingDstNm.isEmpty()){
				billingDstNo = clientDao.getClientNmWithName(billingDstNm, sysCorporationId);
			}

			//支払い方法が掛売でない場合は除外
			if (!StringUtils.equals(selectCutOffclientList.get(j).getPaymentMethod(), "1")) {
				continue;
			}

			//得意先分の格納するListの追加
			salesSlipList.add(new ExtendCorporateSalesSlipDTO());

			//得意先名
			salesSlipList.get(i).setClientNm(selectCutOffclientList.get(j).getClientNm());
			//得意先UD
			//請求先IDがもし見つかれば、IDをそちらに変更する。見つからなければ元のまま放置。
			if (!StringUtils.isEmpty(billingDstNo)){
				salesSlipList.get(i).setSysClientId(Integer.parseInt(billingDstNo));
			} else{
				salesSlipList.get(i).setSysClientId(selectCutOffclientList.get(j).getSysClientId());
			}
			//作成月に購入があった商品分Listに追加するためここでiにインクリメント
			i++;
			billingDstNm = "";

		}

		//得意先ごと且口座IDごとに伝票をまとめる
		Map<String, List<ExtendCorporateSalesSlipDTO>> slipMap = new HashMap<>();

		for (ExtendCorporateSalesSlipDTO slip : salesSlipList){
			if (!slipMap.containsKey(String.valueOf(slip.getSysClientId()) +"_"+ String.valueOf(slip.getSysAccountId()))) {
				ArrayList<ExtendCorporateSalesSlipDTO> salelist = new ArrayList<>();
				salelist.add(slip);
				slipMap.put(String.valueOf(slip.getSysClientId()) +"_"+ String.valueOf(slip.getSysAccountId()), salelist);
			} else {
				slipMap.get(String.valueOf(slip.getSysClientId()) +"_"+ String.valueOf(slip.getSysAccountId())).add(slip);
			}
		}

		//出力月の税率
		int taxRate = getTaxRate(exportMonth+"/01");

		//得意先ごとに、請求書出力用にデータをまとめる
		List<ExportCorporateBillDTO> billList = new ArrayList<>();
		for (Map.Entry<String, List<ExtendCorporateSalesSlipDTO>> entry : slipMap.entrySet()) {
			ExportCorporateBillDTO billDto = new ExportCorporateBillDTO();

			//伝票情報を格納
			billDto.setSlipList(entry.getValue());
			//口座情報を格納
			billDto.setSysAccountId(billDto.getSlipList().get(0).getSysAccountId());

			//得意先情報
			MstClientDTO client = new ClientService().getClient(Long.parseLong(entry.getKey().substring(0, entry.getKey().indexOf("_"))));
			billDto.setSysClientId(client.getSysClientId());
			billDto.setSysCorporationId(client.getSysCorporationId());

			//先月の文字列を求める
			//締日により請求月は調整済みなので月に関してのみ行う
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
			String targetDateStr = exportMonth + "/01";
			Date exportMonthDate = format.parse(targetDateStr);
			Calendar cal = Calendar.getInstance(Locale.JAPAN);
			cal.setTime(exportMonthDate);
			cal.add(Calendar.MONTH, -1);
			String lastMonth = format.format(cal.getTime());

			//先月の最終的な請求額をテーブルから持ってくる
//			lastMonthBillDto = billDao.getSearchCorporateBill(client.getClientNm(), lastMonth,billDto.getSysAccountId());
//
//			if (lastMonthBillDto!=null) {
//				billDto.setSumLastClaimPrice(lastMonthBillDto.getBillAmount());
//			//取得できなかった場合は0円扱い
//			} else {
//				billDto.setSumLastClaimPrice(0);
//			}

			/*************************** 前月の業販請求書を取得START ***************************/
			long sysAccountId = CODE_COMPARISON_ZERO;
			ExtendCorporateBillDTO preMonthDTO = new ExtendCorporateBillDTO();
			AccountDAO accountDAO = new AccountDAO();
			CorporateBillDAO corporateBillDAO = new CorporateBillDAO();

			if (billDto.getSlipList().get(0).getSysCorporateSalesSlipId() == 0) {
				continue;
			}
			//更新対象請求書の口座IDが無い場合：0（商品が0件）
			if (billDto.getSysAccountId() == CODE_COMPARISON_ZERO) {

				//会社コードから優先表示が高いものを1件取得
				sysAccountId = accountDAO.getAccountList(billDto.getSlipList().get(0).getSysCorporationId()).get(INDEX_ZERO).getSysAccountId();
				//口座IDを元に前月請求額を取得
				preMonthDTO = billDao.getSearchCorporateBill(client.getClientNm(), lastMonth, sysAccountId);
				if (preMonthDTO != null) {
				//口座ID指定で取得できない場合、会社コード、会社名、前回請求月で取得する
				if (preMonthDTO.getBillAmount() == AMOUNT_ZERO_SET) {
					preMonthDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(billDto.getSysCorporationId(), lastMonth, client.getClientNm());
					// 前月請求金額を設定
					if(preMonthDTO == null){
						billDto.setSumLastClaimPrice(AMOUNT_ZERO_SET);
						} else {
							billDto.setSumLastClaimPrice(preMonthDTO.getBillAmount());
						}
					} else {
						preMonthDTO.setBillAmount((preMonthDTO.getBillAmount() * taxRate / CALCULATION_100) + preMonthDTO.getBillAmount());
						billDto.setSumLastClaimPrice(preMonthDTO.getBillAmount());
					}
				} else {
					billDto.setSumLastClaimPrice(AMOUNT_ZERO_SET);
				}
			} else {
				sysAccountId = billDto.getSysAccountId();
				//口座IDを元に前月請求額を取得
				preMonthDTO = corporateBillDAO.getSearchCorporateBill(client.getClientNm(), lastMonth, sysAccountId);
				if (preMonthDTO != null) {
					if (preMonthDTO.getBillAmount() == AMOUNT_ZERO_SET) {
						preMonthDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(billDto.getSysCorporationId(), lastMonth, client.getClientNm());
						// 前月請求金額を設定
						if(preMonthDTO == null){
							billDto.setPreMonthBillAmount(AMOUNT_ZERO_SET);
						} else {
							billDto.setSumLastClaimPrice(preMonthDTO.getBillAmount());
						}
					} else {
						preMonthDTO.setBillAmount((preMonthDTO.getBillAmount() * taxRate / CALCULATION_100) + preMonthDTO.getBillAmount());
						billDto.setSumLastClaimPrice(preMonthDTO.getBillAmount());
					}
				} else {
					billDto.setPreMonthBillAmount(AMOUNT_ZERO_SET);
				}
			}
			/**************************** 前月の業販請求書を取得END ****************************/


			//先月分入金のまとめ処理は請求先単位で個別に求めることになったため不要、旧処理はSVN参照
			billDto.setSumLastReceivePrice(0);

			//今月の商品、請求金額、税を算出
			billDto.setExportMonth(exportMonth);
			//今月売上
			int sumSalesPrice = 0;
			//今月値引き
			int sumDiscount = 0;
			//今月売上商品
			List<ExtendCorporateSalesItemDTO> salesItemList = new ArrayList<>();

			for(ExtendCorporateSalesSlipDTO slipDto : entry.getValue()){

				for (ExtendCorporateSalesItemDTO itemDto : slipDto.getCorporateSalesItemList()) {
				boolean checkFlg = false;

					//請求書出力

					if (StringUtils.isNotEmpty(itemDto.getSalesDate())) {
						//値引き（77）のみ税計算をさせないため別管理する
						if (StringUtils.equals(itemDto.getItemCode(), "77")) {
							sumDiscount += itemDto.getPieceRate() * itemDto.getLeavingNum();
						} else {
							sumSalesPrice += itemDto.getPieceRate() * itemDto.getLeavingNum();
						}
						salesItemList.add(itemDto);
					}
				}
			}

			//消費税（外税の伝票のみ）
			int sumTax = this.getTax(sumSalesPrice, taxRate);
			//値引きを加算
			sumSalesPrice += sumDiscount;
			billDto.setSumSalesPrice(sumSalesPrice + sumTax);
			billDto.setSumTax(sumTax);
			billDto.setItemList(salesItemList);

			//先月の最終的な金額 - 今月入力した返済額 = 今月の繰越金額
			billDto.setLastReceivableBalance(billDto.getSumLastClaimPrice() - billDto.getSumLastReceivePrice());

			//対象月の最終的な合計請求額
			billDto.setSumClaimPrice(billDto.getLastReceivableBalance() + billDto.getSumSalesPrice());

			//繰越金額と今月請求金額がない場合除外
			if (billDto.getLastReceivableBalance() == 0 && billDto.getSumClaimPrice() == 0 && billDto.getSlipList().get(0).getOrderNo() == null) {
				continue;
			}

			// 繰越金額と今月請求金額、最終請求金額、今月売上合計金額が０円の場合、当月請求書は作成する必要がないので除外する。
			if (billDto.getLastReceivableBalance() == 0 &&
					billDto.getSumClaimPrice() == 0 &&
					billDto.getSumLastClaimPrice() == 0 &&
					billDto.getSumSalesPrice() == 0) {
					continue;
			}

			billList.add(billDto);
		}

		return billList;
	}

	//請求先ごとにまとめた請求書のリストを取得する
	public List<ExportCorporateBillDTO> getExportCorporateBillIndivList(
			long sysCorporationId, String exportMonth, String currency) throws Exception {
		List<ExportCorporateBillDTO> billList = new ArrayList<>();
		ExportCorporateBillDTO billDto = new ExportCorporateBillDTO();
		billDto.setExportMonth(exportMonth);
		billList.add(billDto);
		return billList;
	}

	/**
	 * 納入先情報新規登録サービス
	 * @param slipDTO
	 * @throws Exception
	 */
	public void insertDelivery(ExtendCorporateSalesSlipDTO slipDTO) throws Exception {

		DeliveryService deliService = new DeliveryService();
		MstDeliveryDTO dto = new MstDeliveryDTO();

		dto.setSysCorporationId(slipDTO.getSysCorporationId());
		dto.setSysClientId(slipDTO.getSysClientId());
		dto.setDeliveryNm(slipDTO.getDestinationNm());
		dto.setDeliveryNmKana(slipDTO.getDestinationNmKana());
		dto.setZip(slipDTO.getDestinationZip());
		dto.setPrefectures(slipDTO.getDestinationPrefectures());
		dto.setMunicipality(slipDTO.getDestinationMunicipality());
		dto.setAddress(slipDTO.getDestinationAddress());
		dto.setBuildingNm(slipDTO.getDestinationBuildingNm());
		dto.setQuarter(slipDTO.getDestinationQuarter());
		dto.setPosition(slipDTO.getDestinationPosition());
		dto.setContactPersonNm(slipDTO.getDestinationContactPersonNm());
		dto.setTel(slipDTO.getDestinationTel());
		dto.setFax(slipDTO.getDestinationFax());

		deliService.registryDelivery(dto);
	}

	//同じ商品番号の商品をまとめる
	public List<ExtendCorporateSalesItemDTO> cordinateItemList(List<ExtendCorporateSalesItemDTO> itemList){

		List<ExtendCorporateSalesItemDTO> cordinatedItemList = new ArrayList<>();

		for (ExtendCorporateSalesItemDTO salesItemDTO : itemList) {
			boolean addFlg = true;
			for (ExtendCorporateSalesItemDTO extendSalesItemDTO : cordinatedItemList) {
				if (StringUtils.equals(extendSalesItemDTO.getItemCode(), salesItemDTO.getItemCode())
					&& StringUtils.equals(extendSalesItemDTO.getItemNm(), salesItemDTO.getItemNm())
					&& extendSalesItemDTO.getPieceRate() == salesItemDTO.getPieceRate()) {

					//商品コード、価格、品名が同じなら注文数をまとめる
					int orderNum = 0;
					orderNum = extendSalesItemDTO.getOrderNum();
					orderNum += salesItemDTO.getOrderNum();

					extendSalesItemDTO.setOrderNum(orderNum);

					addFlg = false;
					break;
				}
			}
			if (addFlg) {
				cordinatedItemList.add(salesItemDTO);
			}
		}

		return cordinatedItemList;
	}

	public List<ExportSaleSummalyDTO> getCorporateSaleSummaly(CorporateSaleSearchDTO searchDto) throws Exception {

		//TODO 伝票検索ぽい。出庫日を指定すると商品テーブルも結合しているぽい
		//2017/09/08追記 ここの伝票IDはステータスが「受注」「売上」のものをとってきている
		List<SysCorporateSalesSlipIdDTO> idList = getSysCorporateSalesSlipIdList(searchDto);

		CorporateSaleDAO corpSaleDao = new CorporateSaleDAO();

		String sysCorporateSalesSlipIds = "";
		for (SysCorporateSalesSlipIdDTO slipId : idList) {

			sysCorporateSalesSlipIds += (StringUtils.isEmpty(sysCorporateSalesSlipIds))
					? Long.toString(slipId.getSysCorporateSalesSlipId()):","+Long.toString(slipId.getSysCorporateSalesSlipId());
		}
		//検索結果伝票IDをまとめている
		sysCorporateSalesSlipIds = "{"+sysCorporateSalesSlipIds+"}";

		//伝票IDでさらに伝票を検索しなおしている
		List<ExtendCorporateSalesSlipDTO> slipList = corpSaleDao.getCorporateSalesSlipListByIds(sysCorporateSalesSlipIds);
		searchDto.setSysCorporateSalesSlipIds(sysCorporateSalesSlipIds);

		//伝票IDに紐づく商品を検索している
		List<ExtendCorporateSalesItemDTO> itemList = corpSaleDao.getCorporateSalesItemListByIds(searchDto);

		//商品を伝票にまとめる
		for (ExtendCorporateSalesSlipDTO slipDto : slipList) {
			for (int i=0; i < itemList.size(); i++) {
				if (slipDto.getSysCorporateSalesSlipId() == itemList.get(i).getSysCorporateSalesSlipId()) {
					slipDto.getCorporateSalesItemList().add(itemList.get(i));
					//itemList.remove(i);
				}
			}
		}

		//伝票ごとに商品統計情報としてまとめる
		Map<String, ExportSaleSummalyDTO> summalyMap = new HashMap<>();

		for (ExtendCorporateSalesSlipDTO slip : slipList){

			for (ExtendCorporateSalesItemDTO item : slip.getCorporateSalesItemList()) {
				ExportSaleSummalyDTO summalyDto = new ExportSaleSummalyDTO();
				CorporateSaleDisplayService corpDispService = new CorporateSaleDisplayService();

				//全受注表示するなら出庫数ではなく注文数を見て計算をする
				int tax = 0;
				//全受注表示しない時
				if (searchDto.getAllOrderDisplayFlag().equals("0")) {

					//出庫数がプラスのものは通常計算･表示する
					if (item.getLeavingNum() > 0) {
						// TODO: 修正候補
						//	tax = corpDispService.getTax(item.getPieceRate(), slip.getTaxRate());
						tax = (item.getPieceRate() * item.getLeavingNum() * slip.getTaxRate() / 100);

					//出庫数がマイナスのもの(返品伝票)は計算･表示する
					} else if (item.getLeavingNum() < 0){
						tax = corpDispService.getTax(item.getPieceRate() * -1, slip.getTaxRate()) * -1;
						//BONCRE-2480 ↑のは単価の税。出庫数分を出していないため改修
						tax = tax * item.getLeavingNum();

					//全受注表示しない時、出庫数がないものは表示しない
					} else {
						continue;
					}

					summalyDto.setItemCode(item.getItemCode());
					summalyDto.setItemNm(item.getItemNm());

					int totalInTaxPieceRate = 0;
					int totalNoTaxPieceRate = 0;

					// TODO: 修正候補
					//単価×出庫数の税込金額
					totalInTaxPieceRate = item.getPieceRate() * item.getLeavingNum() + tax;
					// TODO: 修正候補
					//単価×出庫数の税抜き金額
					totalNoTaxPieceRate = item.getPieceRate() * item.getLeavingNum();

					//税込金額を合計にセット
					summalyDto.setTotalInTaxPieceRate(totalInTaxPieceRate);
					//税抜き金額を合計にセット
					summalyDto.setTotalNoTaxPieceRate(totalNoTaxPieceRate);

					// TODO: 修正候補
					//原価×出庫数
					summalyDto.setTotalCost(item.getCost() * item.getLeavingNum());
					//税抜き金額－原価×出庫数
					summalyDto.setTotalGrossMargin(totalNoTaxPieceRate - summalyDto.getTotalCost());
					// TODO: 修正候補
					//注文数に出庫数をセット
					summalyDto.setTotalOrderNum(item.getLeavingNum());

					//商品コードが重複しているか判定
					if (!summalyMap.containsKey(item.getItemCode())) {

						summalyMap.put(item.getItemCode(), summalyDto);
					} else {
						ExportSaleSummalyDTO summalyDtoExist = summalyMap.get(item.getItemCode());

						summalyDtoExist.setTotalInTaxPieceRate(summalyDtoExist.getTotalInTaxPieceRate() + summalyDto.getTotalInTaxPieceRate());
						summalyDtoExist.setTotalNoTaxPieceRate(summalyDtoExist.getTotalNoTaxPieceRate() + summalyDto.getTotalNoTaxPieceRate());
						summalyDtoExist.setTotalCost(summalyDtoExist.getTotalCost() + summalyDto.getTotalCost());
						summalyDtoExist.setTotalGrossMargin(summalyDtoExist.getTotalGrossMargin() + summalyDto.getTotalGrossMargin());
						summalyDtoExist.setTotalOrderNum(summalyDtoExist.getTotalOrderNum() + summalyDto.getTotalOrderNum());

						//注文数計算中に0になった場合は表示しない
						if(summalyDtoExist.getTotalOrderNum() == 0) {
							summalyMap.remove(item.getItemCode());
						} else {
							summalyMap.put(item.getItemCode(), summalyDtoExist);
						}

					}

				//全受注表示するとき
				} else if (searchDto.getAllOrderDisplayFlag().equals("1")) {

					tax = (item.getPieceRate() * item.getOrderNum() * slip.getTaxRate() / 100);

					summalyDto.setItemCode(item.getItemCode());
					summalyDto.setItemNm(item.getItemNm());

					int totalInTaxPieceRate = 0;
					int totalNoTaxPieceRate = 0;

					// TODO: 修正候補
					//単価×注文数の税込金額
					totalInTaxPieceRate = item.getPieceRate() * item.getOrderNum() + tax;
					// TODO: 修正候補
					//単価×注文数の税抜き金額
					totalNoTaxPieceRate = item.getPieceRate() * item.getOrderNum();

					//税込金額を合計にセット
					summalyDto.setTotalInTaxPieceRate(totalInTaxPieceRate);
					//税抜き金額を合計にセット
					summalyDto.setTotalNoTaxPieceRate(totalNoTaxPieceRate);

					// TODO: 修正候補
					//原価×注文数
					summalyDto.setTotalCost(item.getCost() * item.getOrderNum());
					//税抜き金額－原価×注文数
					summalyDto.setTotalGrossMargin(totalNoTaxPieceRate - summalyDto.getTotalCost());
					// TODO: 修正候補
					//注文数をセット
					summalyDto.setTotalOrderNum(item.getOrderNum());

					//商品コードが重複しているか判定 重複していたらそれぞれの数値を合計しひとつにまとめる
					if (!summalyMap.containsKey(item.getItemCode())) {

						summalyMap.put(item.getItemCode(), summalyDto);
					} else {
						ExportSaleSummalyDTO summalyDtoExist = summalyMap.get(item.getItemCode());

						summalyDtoExist.setTotalInTaxPieceRate(summalyDtoExist.getTotalInTaxPieceRate() + summalyDto.getTotalInTaxPieceRate());
						summalyDtoExist.setTotalNoTaxPieceRate(summalyDtoExist.getTotalNoTaxPieceRate() + summalyDto.getTotalNoTaxPieceRate());
						summalyDtoExist.setTotalCost(summalyDtoExist.getTotalCost() + summalyDto.getTotalCost());
						summalyDtoExist.setTotalGrossMargin(summalyDtoExist.getTotalGrossMargin() + summalyDto.getTotalGrossMargin());
						summalyDtoExist.setTotalOrderNum(summalyDtoExist.getTotalOrderNum() + summalyDto.getTotalOrderNum());

						summalyMap.put(item.getItemCode(), summalyDtoExist);
					}

				}
			}
		}

		//ソートして返却
		List<ExportSaleSummalyDTO> summalyList = new ArrayList<>();
		for (Map.Entry<String, ExportSaleSummalyDTO> entry : summalyMap.entrySet()) {
			summalyList.add(entry.getValue());
		}

		CorporateSummalyComparator comp = new CorporateSummalyComparator();
		if (StringUtils.equals(searchDto.getSortFirstSub(), "1")) {
			switch(searchDto.getSortFirst()){
			case "1":
				Collections.sort(summalyList,comp.getCodeComparatorAsc());
				break;
			case "2":
				Collections.sort(summalyList,comp.getNmComparatorAsc());
				break;
			case "3":
				Collections.sort(summalyList,comp.getPieceRateComparatorAsc());
				break;
			case "4":
				Collections.sort(summalyList,comp.getCostComparatorAsc());
				break;
			case "5":
				Collections.sort(summalyList,comp.getGrossComparatorAsc());
				break;
			case "6":
				Collections.sort(summalyList,comp.getOrderComparatorAsc());
				break;
			default:
				Collections.sort(summalyList,comp.getCodeComparatorAsc());
				break;
			}
		} else {
			switch(searchDto.getSortFirst()){
			case "1":
				Collections.sort(summalyList,comp.getCodeComparatorDesc());
				break;
			case "2":
				Collections.sort(summalyList,comp.getNmComparatorDesc());
				break;
			case "3":
				Collections.sort(summalyList,comp.getPieceRateComparatorDesc());
				break;
			case "4":
				Collections.sort(summalyList,comp.getCostComparatorDesc());
				break;
			case "5":
				Collections.sort(summalyList,comp.getGrossComparatorDesc());
				break;
			case "6":
				Collections.sort(summalyList,comp.getOrderComparatorDesc());
				break;
			default:
				Collections.sort(summalyList,comp.getCodeComparatorDesc());
				break;
			}
		}

		return summalyList;
	}

	/**
	 * 請求書作成、商品が削除されてしまった場合のPDF出力依頼用
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	public ExtendMstItemDTO getMstItemDTO(long sysItemId) throws DaoException {

		CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();

		return corporateSaleDAO.getMstItemDTO(sysItemId);
	}
}