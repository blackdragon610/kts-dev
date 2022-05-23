package jp.co.kts.service.sale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSetItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.ErrorObject;
import jp.co.kts.app.output.entity.SaleListTotalDTO;
import jp.co.kts.app.output.entity.StoreDTO;
import jp.co.kts.app.output.entity.SysSalesSlipIdDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.common.TransactionDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.ClientDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.service.item.ItemService;
import jp.co.kts.service.mst.WarehouseService;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 売上一覧から遷移できる機能で使用するクラスです
 *
 * @author aito
 *
 */

public class SaleDisplayService extends SaleService {

	// private long getSysSalesItemId(String itemCode) {
	// return 0;
	// }
	/**
	 * Brembo業販伝票自動生成対象法人
	 * 1:KTS、2:車楽院、3:T-FOUR
	 */
	private static final long[] BREMBO_CORP_SALE_SLIP_TARGET = {1, 2, 3};
	/**
	 * ----------------------------------------init----------------------------
	 * -----------------------
	 */

	public SaleSearchDTO initSaleSearchDTO() {

		SaleSearchDTO dto = new SaleSearchDTO();

		// form.setSaleSearchDTO(new SaleSearchDTO()); /*日付*/
		// dto.setOrderDateFrom(StringUtil.getToday()); /*注文日始まり*/
		dto.setOrderDateTo(StringUtil.getToday()); /* 注文日終わり */
		// dto.setShipmentPlanDateFrom(StringUtil.getToday()); /*発注予定日始まり*/
		// dto.setShipmentPlanDateTo(StringUtil.getToday()); /*発注予定日終わり*/
		// dto.setShipmentPlanDateTo(StringUtil.getToday());
		// dto.setDestinationAppointDateTo(StringUtil.getToday());

		// dto.setSaleSearchMap(WebConst.SALE_SEARCH_MAP); /*並替え*/
		// dto.setSaleSearchSortOrder(WebConst.SALE_SEARCH_SORT_ORDER);
		// /*昇順・降順*/

		dto.setGrossProfitCalc(WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE);
		dto.setListPageMax(WebConst.LIST_PAGE_MAX_CODE_3);
		dto.setSortFirstSub("2");

		dto.setSumDispFlg(WebConst.SUM_DISP_FLG1);

		return dto;
	}

	public SaleSearchDTO initSaleSearchDTOSummaly() {

		SaleSearchDTO dto = new SaleSearchDTO();

		dto.setOrderDateTo(StringUtil.getToday());
		dto.setSortFirst("1");
		dto.setSortFirstSub("1");

		return dto;
	}

	public ExtendSalesSlipDTO initSalesSlipDTO() throws ParseException {

		ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO();

		dto.setOrderDate(StringUtil.getToday());
		dto.setTaxClass(WebConst.TAX_CODE_EXCLUSIVE);
		// 税率のデフォルトは今日日付で判定
		dto.setTaxRate(getTaxRate(StringUtil.getToday()));
		dto.setReturnFlg(StringUtil.SWITCH_OFF_VALUE);

		return dto;
	}

	public List<ExtendSalesItemDTO> initAddSalesItemList() {

		List<ExtendSalesItemDTO> list = new ArrayList<>();

		for (int i = 0; i < WebConst.ADD_SALES_ITEM_LENGTH; i++) {

			list.add(new ExtendSalesItemDTO());
		}

		return list;
	}

	// public ExtendSalesSlipDTO initReturnSalesSlip(long sysSalesSlipId) throws
	// DaoException {
	public ExtendSalesSlipDTO initReturnSalesSlip(ExtendSalesSlipDTO dto,
			List<ExtendSalesItemDTO> salesItemList) throws DaoException {

		dto.setCodCommission(0);
		dto.setPostage(0);
		dto.setEtcPrice(0);

		//返品フラグがONではない場合、受注番号の末尾に「re」を追加
		if (!dto.getReturnFlg().equals(StringUtil.SWITCH_ON_VALUE)) {
			dto.setOrderNo(dto.getOrderNo() + "re");
		}

		// 消費税を再計算
		int sumPieceRate = getSumPieceRate(salesItemList);
		dto.setTax(getTax(sumPieceRate, dto.getTaxClass(), dto.getTaxRate()));

		dto.setSumPieceRate(sumPieceRate);

		dto.setSumClaimPrice(getSumClaimPrice(dto));

		dto.setGrossMargin(dto.getGrossMargin() * -1);

		dto.setSysSalesSlipId(0L);
		dto.setOrderDate(StringUtil.getToday());
		dto.setReturnFlg(StringUtil.SWITCH_ON_VALUE);

		return dto;
		// ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO();
		// dto = getSalesSlip(sysSalesSlipId);
		// dto.setOrderDate(StringUtil.getToday());
		// dto.setReturnFlg(StringUtil.SWITCH_ON_VALUE);
		// return dto;
	}

	private int getSumClaimPrice(ExtendSalesSlipDTO dto) {

		int sumClaimPrice = 0;

		sumClaimPrice = dto.getSumPieceRate() + dto.getCodCommission()
				+ dto.getPostage() + dto.getEtcPrice();

		if (StringUtils.equals(dto.getTaxClass(), WebConst.TAX_CODE_EXCLUSIVE)) {

			sumClaimPrice += dto.getTax();
		}

		return sumClaimPrice;
	}

	// public List<ExtendSalesItemDTO> initReturnSalesItem(ExtendSalesSlipDTO
	// salesItemDTO) throws DaoException {
	public List<ExtendSalesItemDTO> initReturnSalesItem(
			List<ExtendSalesItemDTO> salesItemList) throws DaoException {
		for (int i = 0; i < salesItemList.size(); i++) {

			ExtendSalesItemDTO dto = salesItemList.get(i);

			if (StringUtils
					.equals(dto.getReturnFlg(), StringUtil.SWITCH_ON_KEY)) {

				dto.setSysSalesSlipId(0L);
				dto.setSysSalesItemId(0L);
				dto.setOrderNum(dto.getOrderNum() * -1);
			} else {
				salesItemList.remove(i--);
			}
		}
		return salesItemList;

	}

	// 20140326 八鍬
//	public List<ExtendSalesItemDTO> initCopySlipItem2(List<ExtendSalesItemDTO> salesItemList) throws DaoException {
//
//		List<ExtendSalesItemDTO> list = new ArrayList<>();
//
//		list = initAddSalesItemList();
//
//		for (int i = 0; i < salesItemList.size(); i++) {
//
//			ExtendSalesItemDTO dto = salesItemList.get(i);
//
//			dto.setSysSalesSlipId(0L);
//			dto.setSysSalesItemId(0L);
//			dto.setPieceRate(dto.getPieceRate());
//
//			list.set(i + 1, dto);
//		}
//		return list;
//	}

	/**
	 * Backlog No. BONCRE-498 対応 <br />
	 * 売上伝票、商品List(以下商品List)の複製し追加用商品Listとして返却を行います<br>
	 * @param salesItemList 商品List
	 * @return 複製された追加用商品List
	 * @throws DaoException
	 */
	public List<ExtendSalesItemDTO> initCopySlipItem(List<ExtendSalesItemDTO> salesItemList) throws DaoException {

		// 例外
		if (salesItemList == null || salesItemList.size() <= 0) {
			throw new NullPointerException();
		}
		List<ExtendSalesItemDTO> retList = new ArrayList<ExtendSalesItemDTO>();


		// 複製時は1行目を空行とする
		//  新規追加用の行にするため
		retList.add(new ExtendSalesItemDTO());

		for (int i = 0; i < salesItemList.size(); i++) {

			ExtendSalesItemDTO dto = salesItemList.get(i);

			dto.setSysSalesSlipId(0L);
			dto.setSysSalesItemId(0L);
			dto.setPieceRate(dto.getPieceRate());

			retList.add(dto);
		}

		for (int i = retList.size() -1; i < WebConst.ADD_SALES_ITEM_LENGTH; i++) {
			retList.add(new ExtendSalesItemDTO());
		}
		return retList;
	}

	// 20140326 八鍬
	public ExtendSalesSlipDTO initCopySlip(ExtendSalesSlipDTO dto,
			List<ExtendSalesItemDTO> addSalesItemList) throws DaoException {

		// dto.setCodCommission(0);
		// dto.setPostage(0);
		// dto.setEtcPrice(0);

		// 消費税を再計算
		int sumPieceRate = getSumPieceRate(addSalesItemList);
		dto.setTax(getTax(sumPieceRate, dto.getTaxClass(), dto.getTaxRate()));

		dto.setSumPieceRate(sumPieceRate);
		dto.setSumClaimPrice(getSumClaimPrice(dto));

		dto.setSysSalesSlipId(0L);
		dto.setOrderDate(StringUtil.getToday());

		return dto;
	}

	/**
	 * ----------------------------------------OUTPUT--------------------------
	 * -------------------------
	 *
	 */

	/**
	 *
	 * @param saleSearchDTO
	 * @return
	 * @throws DaoException
	 */

	// public List<ExtendSalesSlipDTO> getSearchSalesSlipList(SaleSearchDTO
	// saleSearchDTO) throws DaoException {
	//
	// return new SaleDAO().getSearchSalesSlipList(saleSearchDTO);
	// }

	public ExtendSalesSlipDTO getSalesSlip(long sysSalesSlipId)
			throws DaoException {

		ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO(StringUtils.EMPTY);
		dto.setSysSalesSlipId(sysSalesSlipId);

		dto = new SaleDAO().getSaleSlip(dto);

		if (dto == null) {

			return new ExtendSalesSlipDTO();
		}

		setEtcPrice(dto);
		setFlagsDisp(dto);

		return dto;
	}

	public List<SysSalesSlipIdDTO> getSysSalesSlipIdList(
			HttpSession session, 
			SaleSearchDTO saleSearchDTO) throws DaoException {

		List<SysSalesSlipIdDTO> list = new SaleDAO().getSearchSalesSlipList(session , saleSearchDTO);

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
	private long getSumCost(long sysSalesSlipId, long sysCorporationId)
			throws DaoException {

		ExtendSalesItemDTO searchDTO = new ExtendSalesItemDTO();
		searchDTO.setSysSalesSlipId(sysSalesSlipId);
		searchDTO.setSysCorporationId(sysCorporationId);
		List<ExtendSalesItemDTO> list =
				new SaleDAO().getSalesItemList(searchDTO);

		if (list == null || list.size() == 0) {
			return 0;
		}

		// 合計用
		long sumCost = 0;

		for (ExtendSalesItemDTO dto : list) {

			sumCost += (dto.getCost() * dto.getOrderNum());
		}

		return sumCost;
	}

	public List<ExtendSalesSlipDTO> getSalesSlipList(
			List<SysSalesSlipIdDTO> sysSalesSlipIdList, int pageIdx,
			SaleSearchDTO dto) throws DaoException {

		List<ExtendSalesSlipDTO> salesSlipList = new ArrayList<>();

		if (StringUtils.isEmpty(dto.getListPageMax())) {
			dto.setListPageMax("3");
		}

		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(dto.getListPageMax())
				* pageIdx; i < WebConst.LIST_PAGE_MAX_MAP.get(dto
				.getListPageMax()) * (pageIdx + 1)
				&& i < sysSalesSlipIdList.size(); i++) {

			SaleSearchDTO saleSearchDTO = new SaleSearchDTO();
			saleSearchDTO.setSysSalesSlipId(sysSalesSlipIdList.get(i)
					.getSysSalesSlipId());

			SaleDAO saleDAO = new SaleDAO();
			ExtendSalesSlipDTO salesSlipDTO = saleDAO
					.getSearchSalesSlip(saleSearchDTO);
			// if (null == salesSlipDTO) {
			// continue;
			// }
			setFlagsDisp(salesSlipDTO);
			salesSlipList.add(salesSlipDTO);
		}
		// salesSlipList.add(null);
		return salesSlipList;
	}

	// public ExtendSalesSlipDTO getSearchSalesSlip(SaleSearchDTO saleSearchDTO)
	// throws DaoException {
	//
	// return new SaleDAO().getSearchSalesSlip(saleSearchDTO);
	// }

	public List<ExtendSalesItemDTO> getSalesItemList(long sysSalesSlipId,
			long sysCorporationId) throws DaoException {

		ExtendSalesItemDTO dto = new ExtendSalesItemDTO(StringUtils.EMPTY);

		dto.setSysSalesSlipId(sysSalesSlipId);
		dto.setSysCorporationId(sysCorporationId);

		return new SaleDAO().getSalesItemList(dto);
	}

	/**
	 * 出庫商品取得処理
	 * @param salesSlipList
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSalesItemDTO> getLeaveStockList(
			List<ExtendSalesSlipDTO> salesSlipList) throws DaoException {

		List<ExtendSalesItemDTO> leaveStockList = new ArrayList<>();

		// int i = 0;
		for (ExtendSalesSlipDTO salesSlipDTO : salesSlipList) {

			if (StringUtils.equals(
					getLeavingFlg(salesSlipDTO.getSysSalesSlipId()),
					StringUtil.SWITCH_ON_VALUE)) {
				continue;
			}

			List<ExtendSalesItemDTO> salesItemList = new ArrayList<>();

			// 商品の注文数などを取得
			salesItemList = getSalesItemList(salesSlipDTO.getSysSalesSlipId(),
					salesSlipDTO.getSysCorporationId());

			for (ExtendSalesItemDTO salesItemDTO : salesItemList) {

				// 取得した商品の情報では足りない部分を伝票から取得
				salesItemDTO.setShipmentPlanDate(salesSlipDTO
						.getShipmentPlanDate());
				salesItemDTO.setOrderNo(salesSlipDTO.getOrderNo());
				salesItemDTO
						.setSysSalesSlipId(salesSlipDTO.getSysSalesSlipId());

				// 出庫数のデフォルト値を注文数から取得
				salesItemDTO.setLeaveNum(salesItemDTO.getOrderNum());

				if (salesItemDTO.getSysItemId() == 0) {
					leaveStockList.add(salesItemDTO);
					continue;
				}

				ExtendWarehouseStockDTO stockDTO = new ExtendWarehouseStockDTO();

				// 楽天倉庫から出庫する商品の場合
				if (salesItemDTO.getRslLeaveFlag() != null && salesItemDTO.getRslLeaveFlag().equals("1")) {

					stockDTO = getExternalWarehouse(salesItemDTO.getSysItemId(), "RSL");

					// 商品の倉庫在庫に楽天倉庫が登録されていなかった場合エラーとする。
					if (stockDTO == null) {
						salesItemDTO.setWarehouseNm("商品に対して楽天倉庫が登録されていない可能性があります。");
					} else {
						// 楽天倉庫のデータを取得する。
						salesItemDTO.setWarehouseNm(stockDTO.getWarehouseNm());
						salesItemDTO.setFirstWarehouseStockNum(stockDTO.getStockNum());

						// 楽天倉庫から出荷する商品の場合在庫数は楽天倉庫の在庫数となる。
						salesItemDTO.setTotalStockNum(stockDTO.getStockNum());
						// 仮在庫数は総在庫数からキープされている在庫数を引いた数となる。
						salesItemDTO.setTemporaryStockNum(new ItemService().getExternalTemporaryStockNum(salesItemDTO.getSysItemId(), "RSL"));
					}


				} else {

					stockDTO = getPriorityWarehouse(salesItemDTO.getSysItemId());
					if (stockDTO == null) {

						salesItemDTO.setWarehouseNm("優先度１の倉庫がないか\n優先度１の倉庫が複数あります");

					} else {
						// KTS倉庫のデータを取得する。
						salesItemDTO.setWarehouseNm(stockDTO.getWarehouseNm());
						salesItemDTO.setFirstWarehouseStockNum(stockDTO
								.getStockNum());
					}
				}

				leaveStockList.add(salesItemDTO);
			}
		}

		return leaveStockList;
	}

	public List<ExtendSalesSlipDTO> getPickItemList(
			List<ExtendSalesSlipDTO> salesSlipList) throws DaoException {

		for (ExtendSalesSlipDTO dto : salesSlipList) {

			dto.setPickItemList(
					getSalesItemList(dto.getSysSalesSlipId(), dto.getSysCorporationId()));

			ExtendWarehouseStockDTO stockDTO = new ExtendWarehouseStockDTO();

			for (ExtendSalesItemDTO salesItemDTO : dto.getPickItemList()) {

				// 楽天倉庫から出庫する商品の場合ピッキングリスト出力しない。
				if (salesItemDTO.getRslLeaveFlag() != null && salesItemDTO.getRslLeaveFlag().equals("1")) {

					stockDTO = getExternalWarehouse(salesItemDTO.getSysItemId(), "RSL");

					// 商品の倉庫在庫に楽天倉庫が登録されていなかった場合エラーとする。
					if (stockDTO == null) {
						salesItemDTO.setWarehouseNm("商品に対して楽天倉庫が登録されていない可能性があります。");
						dto.setRslLeaveFlag("0");
						continue;
					}

					// 楽天倉庫のから出荷予定の伝票はピッキングリスト出力しない。
					dto.setRslLeaveFlag("1");
					continue;

				}

				/*
				 * 楽天倉庫以外の倉庫から出荷する場合は通常通りの処理
				 */
				stockDTO = getPriorityWarehouse(salesItemDTO.getSysItemId());
				if (stockDTO == null) {

					salesItemDTO.setWarehouseNm("優先度１の倉庫がないか優先度１の倉庫が複数あります");

				} else {

					salesItemDTO.setWarehouseNm(stockDTO.getWarehouseNm());
					salesItemDTO.setLocationNo(stockDTO.getLocationNo());

					// 楽天倉庫から出荷予定ではないのでピッキングリスト出力する。
					dto.setRslLeaveFlag("0");

				}
			}
		}

		return salesSlipList;
	}

	public List<ExtendSalesItemDTO> getTotalPickItemList(
			List<ExtendSalesSlipDTO> salesSlipList) throws DaoException {

		List<ExtendSalesItemDTO> itemList = new ArrayList<>();

		// int i = 0;
		for (ExtendSalesSlipDTO salesSlipDTO : salesSlipList) {

			List<ExtendSalesItemDTO> salesItemList = new ArrayList<>();
			// leaveStockDTO.setSalesSlipDTO(dto);

			// 商品の注文数などを取得
			salesItemList = getSalesItemList(salesSlipDTO.getSysSalesSlipId(),
					salesSlipDTO.getSysCorporationId());

			/** 商品Distinctする？　暫定 */
			for (ExtendSalesItemDTO salesItemDTO : salesItemList) {
				boolean addFlg = true;

				// 楽天倉庫から出庫する商品はトータルピッキングリストには出力しない。
				if (salesItemDTO.getRslLeaveFlag() != null && salesItemDTO.getRslLeaveFlag().equals("1")) {
					continue;
				}

				for (ExtendSalesItemDTO extendSalesItemDTO : itemList) {
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
	 * 優先度が高い倉庫を取得する処理です。
	 * 楽天倉庫以外の倉庫が対象となります。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */

	private ExtendWarehouseStockDTO getPriorityWarehouse(long sysItemId)
			throws DaoException {

		// 倉庫在庫を取得する。
		List<ExtendWarehouseStockDTO> list = new ItemDAO()
				.getWarehouseStockList(sysItemId);

		ExtendWarehouseStockDTO stockDTO = new ExtendWarehouseStockDTO();

		// 商品内で優先度が高い倉庫の情報を取得する。
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
	 * 楽天倉庫のデータを取得する。
	 * @param sysItemId
	 * @return
	 * @throws DaoException
	 */
	private ExtendWarehouseStockDTO getExternalWarehouse(long sysItemId, String sysExternalWarehouseCode)
			throws DaoException {

		// 倉庫在庫を取得する。
		ExtendWarehouseStockDTO rslData = new ItemDAO().getSysExternalStockId(sysItemId, sysExternalWarehouseCode);

		// 楽天倉庫が登録されていない場合
		if (rslData == null) {
			return null;
		} else {
			rslData.setWarehouseNm(new WarehouseService().getExternalWarehouseNm(sysExternalWarehouseCode));
		}

		return rslData;
	}

//	public SaleListTotalDTO getSaleListTotalDTO2(
//			List<SysSalesSlipIdDTO> sysSalesSlipIdList,
//			String grossProfitCalc) {
//		// 作成途中
//
//		List<Long> sysCorporateIdList = new ArrayList<Long>();
//		List<Long> sysSlipIdList = new ArrayList<Long>();
//		for (SysSalesSlipIdDTO dto : sysSalesSlipIdList) {
//
//		}
//		return null;
//	}

	/**
	 * 伝票一覧から商品価格合計や粗利の各合計を取得します(Java)<br />
	 * 表示フラグによって計算するか否かを判定します<br />
	 *
	 * @param sysSalesSlipIdList
	 * @param grossMarginFlg
	 * @param sumDispFlg 表示フラグ
	 * @return
	 * @throws DaoException
	 */

	public SaleListTotalDTO getSaleListTotalDTO(
			List<SysSalesSlipIdDTO> sysSalesSlipIdList
			, String grossMarginFlg
			, String sumDispFlg)
			throws DaoException {

		SaleListTotalDTO saleListTotalDTO = new SaleListTotalDTO();
		// Backlog No. BONCRE-488 対応 (サーバ側)
		// 非表示の場合
		if (StringUtils.equals(WebConst.SUM_DISP_FLG1, sumDispFlg)) {
			return saleListTotalDTO;
		}

		for (SysSalesSlipIdDTO dto : sysSalesSlipIdList) {

			long price = 0;
			if (StringUtils.equals(grossMarginFlg,
					WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE)) {

				price = dto.getNoTaxSumPieceRate();

			} else if (StringUtils.equals(grossMarginFlg,
					WebConst.GROSS_PROFIT_CALC_TOTAL_CODE)) {

				price = dto.getSumClaimPrice();
			}

			long[] arrPrice = getCostGrossMargin(
					dto.getSysSalesSlipId(),
					dto.getSysCorporationId(),
					price);

			saleListTotalDTO.setSumSumClaimPrice(
					saleListTotalDTO.getSumSumClaimPrice() + dto.getSumClaimPrice());

			saleListTotalDTO.setInTaxSumPieceRate(
					saleListTotalDTO.getInTaxSumPieceRate() + dto.getInTaxSumPieceRate());

			saleListTotalDTO.setNoTaxSumPieceRate(
					saleListTotalDTO.getNoTaxSumPieceRate() + dto.getNoTaxSumPieceRate());

			saleListTotalDTO.setSumCost(
					saleListTotalDTO.getSumCost() + arrPrice[0]);

			saleListTotalDTO.setSumGrossMargin(
					saleListTotalDTO.getSumGrossMargin() + arrPrice[1]);
		}

		return saleListTotalDTO;
	}

	/**
	 * 伝票一覧から商品価格合計や粗利の各合計を取得します(SQL)
	 *
	 * @param saleSearchDTO
	 * @return
	 * @throws DaoException
	 */
	public SaleListTotalDTO getSaleListTotalDTO(SaleSearchDTO saleSearchDTO)
			throws DaoException {

		return new SaleDAO().getSaleListTotalDTO(saleSearchDTO);
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
	// private List<SysSalesSlipIdDTO>
	// getCostGrossMargin(List<SysSalesSlipIdDTO> sysSalesSlipIdList) throws
	// DaoException {
	//
	// for (SysSalesSlipIdDTO dto: sysSalesSlipIdList) {
	//
	// dto.setSumCost(getSumCost(dto.getSysSalesSlipId(),
	// dto.getSysCorporationId()));
	// dto.setSumGrossMargin(dto.getNoTaxSumPieceRate() - dto.getCost());
	// }
	// return sysSalesSlipIdList;
	// }
	/**
	 * 非商品・ギフトをetcとして読み替える
	 *
	 * @param dto
	 */
	public void setEtcPrice(ExtendSalesSlipDTO dto) {

		// DBから情報取得したとき
		if (dto.getDiscommondity() != 0 || dto.getGift() != 0) {

			if (dto.getDiscommondity() != 0) {

				dto.setEtcPrice(dto.getDiscommondity());
				dto.setDiscommondity(0);
				dto.setEtcStr(WebConst.ETC_PRICE_CODE_1);

			} else if (dto.getGift() != 0) {

				dto.setEtcPrice(dto.getGift());
				dto.setGift(0);
				dto.setEtcStr(WebConst.ETC_PRICE_CODE_2);
			}

			// 画面上から取得したとき
		} else if (dto.getEtcPrice() != 0) {

			if (StringUtils.equals(dto.getEtcStr(), WebConst.ETC_PRICE_CODE_1)) {

				dto.setDiscommondity(dto.getEtcPrice());

			} else if (StringUtils.equals(dto.getEtcStr(),
					WebConst.ETC_PRICE_CODE_2)) {

				dto.setGift(dto.getEtcPrice());
			}
		}
	}

	/**
	 * 画面に表示する用フラグ読み替え
	 *
	 * @param dto
	 */
	public void setFlagsDisp(ExtendSalesSlipDTO dto) {

		if (dto == null) {
			return;
		}

		dto.setPickingListFlg(StringUtil.switchCheckBoxDisp(dto
				.getPickingListFlg()));
		dto.setLeavingFlg(StringUtil.switchCheckBoxDisp(dto.getLeavingFlg()));
	}

	/**
	 * DBに格納する用フラグ読み替え
	 *
	 * @param dto
	 */
	public void setFlagsDB(ExtendSalesSlipDTO dto) {

		if (dto == null) {
			return;
		}

		dto.setPickingListFlg(StringUtil.switchCheckBoxDB(dto
				.getPickingListFlg()));
		dto.setLeavingFlg(StringUtil.switchCheckBoxDB(dto.getLeavingFlg()));
	}

	/**
	 * 検索用フラグ読み換え
	 *
	 * @param dto
	 */
	public void setFlags(SaleSearchDTO dto) {

		if (StringUtils.isNotEmpty(dto.getPickingListFlg())) {
			dto.setPickingListFlg(StringUtil.switchCheckBox(dto
					.getPickingListFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getLeavingFlg())) {
			dto.setLeavingFlg(StringUtil.switchCheckBox(dto.getLeavingFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getReturnFlg())) {
			dto.setReturnFlg(StringUtil.switchCheckBox(dto.getReturnFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getSlipNoExist())) {
			dto.setSlipNoExist(StringUtil.switchCheckBox(dto.getSlipNoExist()));
		}

		if (StringUtils.isNotEmpty(dto.getSlipNoHyphen())) {
			dto.setSlipNoHyphen(StringUtil.switchCheckBox(dto.getSlipNoHyphen()));
		}

		if (StringUtils.isNotEmpty(dto.getSlipNoNone())) {
			dto.setSlipNoNone(StringUtil.switchCheckBox(dto.getSlipNoNone()));
		}

		if (StringUtils.isNotEmpty(dto.getSearchAllFlg())) {
			dto.setSearchAllFlg(StringUtil.switchCheckBox(dto.getSearchAllFlg()));
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
	 */

	public void updateSalesSlip(ExtendSalesSlipDTO salesSlipDTO)
			throws DaoException {

		setFlagsDB(salesSlipDTO);
		setEtcPrice(salesSlipDTO);

		new SaleDAO().updateSalesSlip(salesSlipDTO);
	}

	public void updateSalesList(List<ExtendSalesSlipDTO> salesSlipList)
			throws DaoException {

		for (ExtendSalesSlipDTO dto : salesSlipList) {

			dto.setPickingListFlg(StringUtil.switchCheckBox(dto
					.getPickingListFlg()));
			dto.setLeavingFlg(StringUtil.switchCheckBox(dto.getLeavingFlg()));

			new SaleDAO().updateSalesSlipFlg(dto);
			// new SaleDAO().updateA
		}
	}

	public void updateSalesItemList(List<ExtendSalesItemDTO> salesItemList)
			throws DaoException {

		for (ExtendSalesItemDTO dto : salesItemList) {
			if(dto.getPostage() > 0 && dto.getOrderNum() > 0) {
				dto.setCost(dto.getCost() - Math.abs((dto.getPostage() / dto.getOrderNum())));
			}
			updateSalesItem(dto);
		}
	}

	public void registrySaleItemList(List<ExtendSalesItemDTO> addSalesItemList,
			long sysSalesSlipId, ExtendSalesSlipDTO salesSlipDto) throws DaoException {

		for (ExtendSalesItemDTO dto : addSalesItemList) {

			// 商品名・注文数が空のものは売上商品に登録しない
			if (StringUtils.isBlank(dto.getItemNm()) || dto.getOrderNum() == 0) {
				continue;
			}
			registrySaleItem(dto, sysSalesSlipId, salesSlipDto);
		}
	}

	/**
	 * 登録処理振り分け
	 * @param dto
	 * @throws DaoException
	 */
	public void newRegistrySalesSlip(ExtendSalesSlipDTO dto)
			throws DaoException {

		//非商品・ギフトをetcとして読み替える
		setEtcPrice(dto);

		//売上伝票新規登録
		registrySalesSlip(dto);
		//DBに格納する用フラグ読み替え
		setFlagsDB(dto);
		//売上円票更新
		new SaleDAO().updateSalesSlipFlg(dto);
	}

	/**
	 * 新規登録
	 * @param dto
	 * @throws DaoException
	 */
	public void registrySalesSlip(ExtendSalesSlipDTO dto) throws DaoException {

		//売上伝票IDを付与
		dto.setSysSalesSlipId(new SequenceDAO().getMaxSysSalesSlipId() + 1);

		//売上伝票新規登録
		new SaleDAO().registrySaleSlip(dto);
	}

	public void registryReturnSalesSlip(ExtendSalesSlipDTO dto)
			throws DaoException {

		registrySalesSlip(dto);

		setFlagsDB(dto);
		new SaleDAO().updateSalesSlipFlg(dto);

		updateReturnFlag(dto);
	}

	private void updateReturnFlag(ExtendSalesSlipDTO dto) throws DaoException {

		ExtendSalesSlipDTO registrySalesSlipDTO = new ExtendSalesSlipDTO();
		registrySalesSlipDTO.setReturnFlg(StringUtil.SWITCH_ON_VALUE);
		registrySalesSlipDTO.setSysSalesSlipId(dto.getSysSalesSlipId());

		// dto.setReturnFlg(StringUtil.SWITCH_ON_VALUE);

		new SaleDAO().updateSalesSlipFlg(registrySalesSlipDTO);
	}

	public void registryReturnSalesItem(List<ExtendSalesItemDTO> salesItemList,
			ExtendSalesSlipDTO salesSlipDTO,  ExtendSalesSlipDTO salesSlipDto) throws DaoException {

		registrySaleItemList(salesItemList, salesSlipDTO.getSysSalesSlipId(), salesSlipDto);

		// 返品伝票の場合、原価チェックフラグを未チェックに更新する。
		SaleDAO saleDAO = new SaleDAO();
		saleDAO.updateSaleItemCostCheckFlag(salesSlipDTO.getSysSalesSlipId(), "0");

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
	public ErrorObject<List<ExtendSalesItemDTO>> checkLeaveStock(
			List<ExtendSalesItemDTO> leaveStockList) throws DaoException {

		TransactionDAO transactionDAO = new TransactionDAO();
		// 出庫チェックが始まった時点のデータを記憶させます
		transactionDAO.begin();
		ErrorObject<List<ExtendSalesItemDTO>> result = new ErrorObject<>();

		if (leaveStockList == null) {

			result.getErrorMessageList().add(
					new ErrorMessageDTO("未出庫の伝票がありません"));
			return result;
		}

		List<ExtendSalesItemDTO> checkedSalesItemList = null;
		long[] arrSysSalesSlipId = new long[leaveStockList.size()];
		int idx = 0;
		for (ExtendSalesItemDTO dto : leaveStockList) {

			result.setSuccess(true);

			// 出庫済みフラグの場合出庫しない
			if (StringUtils.equals(getLeavingFlg(dto.getSysSalesSlipId()),
					StringUtil.SWITCH_ON_VALUE)) {

				result.setSuccess(false);
				result.getErrorMessageList().add(
						new ErrorMessageDTO("受注番号：　" + dto.getOrderNo()
								+ "　は既に出庫済みです"));
				continue;
			}

			ItemService itemService = new ItemService();

			// 構成商品（セット商品）から出庫する場合
			// XXX 楽天倉庫には構成商品は存在しないのでチェックは不要です。
			// 念のため、楽天倉庫出庫予定商品は除外します。
			if (dto.getSysItemId() != 0
					&& (dto.getRslLeaveFlag() == null || StringUtils.equals(dto.getRslLeaveFlag(), "0"))
					&& StringUtils.equals(dto.getLeaveClassFlg(), "1")) {

				// 構成商品の出庫チェック
				result.setSuccess(checkSetItemStock(dto.getSysItemId(),
						dto.getLeaveNum(), dto.getOrderNo(), result));

				// 失敗の場合
				// 大まかだけどエラーの原因をセット
				if (!result.isSuccess()) {

					// 失敗の場合同伝票を探して除去する
					arrSysSalesSlipId[idx++] = dto.getSysSalesSlipId();
					continue;
				}

				// 一時的に在庫減らす（return直前でロールバックします）
				updateSetItemStock(dto.getSysItemId(), dto.getLeaveNum(),
						dto.getOrderNo());

				// (キープなし)楽天倉庫から出庫する場合で出庫数より仮在庫数が少ない場合
			} else if (dto.getSysItemId() != 0
					&& StringUtils.equals(dto.getLeaveClassFlg(), "0")
					&& StringUtils.equals(dto.getRslLeaveFlag(), "1")
					&& itemService.getExternalSysKeepId(dto.getOrderNo(),
							dto.getSysItemId(), "RSL") == 0
					&& dto.getLeaveNum() > itemService.getExternalTemporaryStockNum(dto
							.getSysItemId(), "RSL")) {

				result.setSuccess(false);
				result.getErrorMessageList().add(
						new ErrorMessageDTO("受注番号：　" + dto.getOrderNo() + "　品番：　"
								+ dto.getItemCode() + "　の在庫が楽天倉庫で不足しています"));

				arrSysSalesSlipId[idx++] = dto.getSysSalesSlipId();

				continue;

				// (キープなし)自在庫から出庫する場合で出庫数より仮在庫数が少ない場合
			} else if (dto.getSysItemId() != 0
					&& StringUtils.equals(dto.getLeaveClassFlg(), "0")
					&& (dto.getRslLeaveFlag() == null || StringUtils.equals(dto.getRslLeaveFlag(), "0"))
					&& itemService.getSysKeepId(dto.getOrderNo(),
							dto.getSysItemId()) == 0
					&& dto.getLeaveNum() > itemService.getTemporaryStockNum(dto
							.getSysItemId())) {

				result.setSuccess(false);
				result.getErrorMessageList().add(
						new ErrorMessageDTO("受注番号：　" + dto.getOrderNo() + "　品番：　"
								+ dto.getItemCode() + "　の在庫が不足しています"));

				arrSysSalesSlipId[idx++] = dto.getSysSalesSlipId();

				continue;

				// (キープあり)楽天倉庫から出庫する場合で出庫数より総在庫数が少ない場合
			} else if (dto.getSysItemId() != 0
					&& StringUtils.equals(dto.getLeaveClassFlg(), "0")
					&& StringUtils.equals(dto.getRslLeaveFlag(), "1")
					&& itemService.getExternalSysKeepId(dto.getOrderNo(),
							dto.getSysItemId(), "RSL") != 0
					&& dto.getLeaveNum() > itemService
							.getExternalWarehouseStockNum(dto.getSysItemId(), "RSL")) {

				result.setSuccess(false);
				result.getErrorMessageList().add(
						new ErrorMessageDTO("受注番号：　" + dto.getOrderNo() + "　品番：　"
								+ dto.getItemCode() + "　の在庫が楽天倉庫で不足しています"));

				// 失敗の場合同伝票を探して除去する
				arrSysSalesSlipId[idx++] = dto.getSysSalesSlipId();

				continue;

				// (キープあり)自在庫から出庫する場合で出庫数より総在庫数が少ない場合
			} else if (dto.getSysItemId() != 0
					&& StringUtils.equals(dto.getLeaveClassFlg(), "0")
					&& (dto.getRslLeaveFlag() == null || StringUtils.equals(dto.getRslLeaveFlag(), "0"))
					&& itemService.getSysKeepId(dto.getOrderNo(),
							dto.getSysItemId()) != 0
					&& dto.getLeaveNum() > itemService
							.getTotalWarehouseStockNum(dto.getSysItemId())) {

				result.setSuccess(false);
				result.getErrorMessageList().add(
						new ErrorMessageDTO("受注番号：　" + dto.getOrderNo() + "　品番：　"
								+ dto.getItemCode() + "　の在庫が不足しています"));

				// 失敗の場合同伝票を探して除去する
				arrSysSalesSlipId[idx++] = dto.getSysSalesSlipId();

				continue;

			}


			// XXX 業販伝票への影響を避けるために楽天倉庫出庫とKTS倉庫出庫で処理を分けます。
			// 一時的に在庫減らす（return直前でロールバックします）
			if (StringUtils.equals(dto.getRslLeaveFlag(), "1")) {
				itemService.lumpUpdateExternalStock(dto.getSysItemId(), dto.getLeaveNum(), "RSL");
			} else {
				itemService.lumpUpdateStock(dto.getSysItemId(), dto.getLeaveNum());
			}

			if (checkedSalesItemList == null) {
				checkedSalesItemList = new ArrayList<>();
			}

			checkedSalesItemList.add(dto);
		}
		// 失敗した伝票IDをチェック済みリストから除去
		for (int i = 0; i < idx; i++) {
			removeSales(checkedSalesItemList, arrSysSalesSlipId[i]);
		}

		// 出庫チェックが始まった時点のデータに戻します
		transactionDAO.rollback();
		result.setResultObject(checkedSalesItemList);
		return result;
	}

	private void removeSales(List<ExtendSalesItemDTO> checkedSalesItemList,
			long sysSalesSlipId) {

		if (checkedSalesItemList == null) {

			return;
		}

		for (int i = 0; i < checkedSalesItemList.size(); i++) {

			ExtendSalesItemDTO dto = checkedSalesItemList.get(i);

			if (sysSalesSlipId == 140) {
				System.out.println("aa");
			}
			if (dto.getSysSalesSlipId() == sysSalesSlipId) {

				checkedSalesItemList.remove(i--);
			}
		}

	}

	/**
	 * 構成商品在庫チェック
	 * @param sysItemId
	 * @param leaveNum
	 * @param orderNo
	 * @param errorResult
	 * @return
	 * @throws DaoException
	 */
	private boolean checkSetItemStock(long sysItemId, int leaveNum,
			String orderNo, ErrorObject<List<ExtendSalesItemDTO>> errorResult)
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
		for (ExtendSetItemDTO dto : setItemList) {

			ItemService itemService = new ItemService();
			// 構成商品が通常の商品の場合
			if (!dto.getLeaveClassFlg().equals("1")) {

				// (キープなし)出庫数より仮在庫数が少ない場合
				if (itemService.getSysKeepId(orderNo, dto.getFormSysItemId()) == 0
						&& dto.getItemNum() * leaveNum > itemService
								.getTemporaryStockNum(dto.getFormSysItemId())) {

					errorResult.getErrorMessageList().add(
							new ErrorMessageDTO("伝票番号：　" + orderNo + "　セット商品　"
									+ itemCode + "　の構成商品：　" + dto.getItemCode()
									+ "　が不足しています"));
					result = false;
					return result;
					// (キープあり)出庫数より総在庫数が少ない場合
				} else if (itemService.getSysKeepId(orderNo,
						dto.getFormSysItemId()) != 0
						&& dto.getItemNum() * leaveNum > itemService
								.getTotalWarehouseStockNum(dto
										.getFormSysItemId())) {
					errorResult.getErrorMessageList().add(
							new ErrorMessageDTO("伝票番号：　" + orderNo + "　セット商品　"
									+ itemCode + "　の構成商品：　" + dto.getItemCode()
									+ "　が不足しています"));
					result = false;
					return result;
				}
				// 構成商品がセット商品だった場合(1層目)
			} else if (dto.getLeaveClassFlg().equals("1")) {

				List<ExtendSetItemDTO> secondSetItemList = new ItemService()
						.getSetItemList(dto.getFormSysItemId());

				for (ExtendSetItemDTO secondSetItem : secondSetItemList) {
					// 構成商品が通常の商品の場合
					if (!secondSetItem.getLeaveClassFlg().equals("1")) {

						// (キープなし)出庫数より仮在庫数が少ない場合
						if (itemService.getSysKeepId(orderNo,
								secondSetItem.getFormSysItemId()) == 0
								&& secondSetItem.getItemNum()
										* dto.getItemNum() * leaveNum > itemService
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
										* dto.getItemNum() * leaveNum > itemService
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
												* dto.getItemNum() * leaveNum > itemService
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
												* dto.getItemNum() * leaveNum > itemService
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
														* dto.getItemNum()
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
														* dto.getItemNum()
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
																* dto.getItemNum()
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
																* dto.getItemNum()
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
																		+ dto.getItemCode()
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
	public ErrorDTO leaveStock(List<ExtendSalesItemDTO> list)
			throws DaoException {

		ErrorDTO errorDTO = new ErrorDTO();

		if (list == null) {
			return errorDTO;
		}

		ItemService itemService = new ItemService();

		for (ExtendSalesItemDTO dto : list) {

			// 構成商品（セット商品）から出庫する場合
			//XXX 楽天倉庫には構成商品は存在しないので楽天倉庫用の処理は入れていません。
			if (StringUtils.equals(dto.getLeaveClassFlg(), "1")) {

				updateSetItemStock(dto.getSysItemId(), dto.getLeaveNum(),
						dto.getOrderNo());

			} else {

				//楽天倉庫出荷とKTS倉庫出荷を分ける
				if (StringUtils.equals(dto.getRslLeaveFlag(), "1")) {

					// キープがあるかどうか取得
					long sysExternalKeepId = itemService.getExternalSysKeepId(dto.getOrderNo(), dto.getSysItemId(), "RSL");
					if (sysExternalKeepId != 0) {

						// キープを削除
						itemService.deleteExternalKeep(sysExternalKeepId);
					}

					itemService.lumpUpdateExternalStock(dto.getSysItemId(), dto.getLeaveNum(), "RSL");


				} else {
					// キープがあるかどうか取得
					long sysKeepId = itemService.getSysKeepId(dto.getOrderNo(),
							dto.getSysItemId());
					if (sysKeepId != 0) {

						// キープを削除
						itemService.deleteKeep(sysKeepId);
					}
					itemService.lumpUpdateStock(dto.getSysItemId(), dto.getLeaveNum());
				}
			}
		}

		for (ExtendSalesItemDTO dto : list) {

			if (StringUtils.equals(getLeavingFlg(dto.getSysSalesSlipId()), "1")) {
				continue;
			}

			updateLeaveStock(dto.getSysSalesSlipId());
		}

		return errorDTO;
	}

	/**
	 * Bremboの商品が存在するか調べる処理
	 * 出庫処理した売上伝票商品にBremboの商品があるかを調べてその商品をリスト化する
	 * @param saleItemList
	 * @return List<ExtendSalesItemDTO>
	 */
	public List<ExtendSalesItemDTO> checkTargetExistsForBremboItem(List<ExtendSalesItemDTO> saleItemList)
			throws Exception {

		List<ExtendSalesItemDTO> bremboItemList = new ArrayList<>();
		ItemDAO itemDAO = new ItemDAO();

		//1つも出庫していないときは何もしない
		if (saleItemList == null) {
			return saleItemList;
		}

		for (ExtendSalesItemDTO itemDTO : saleItemList) {

			//出庫してない商品はそもそもBremboの業販売上として出さない
			if (itemDTO.getLeaveNum() == 0) {
				continue;
			}

			/**** セット商品の構成商品がBrembo商品の時の処理 START ********************************/
			/*
			 * TODO
			 * 構成商品にBrembo商品があるセット商品は、(現状)Brembo商品以外が構成商品に入ってくることはない。
			 * そのため1層目だけ調べればそのセット商品の構成商品は全てBrembo商品ということになる。
			 * ※ただし、今後Brembo商品以外が入ってくることもありえるのでその時はCsvImportService.javaなどを参考にして、商品を階層ごとに見ていく処理を
			 * 　追加する必要がある。
			 * */

			//出庫分類フラグが取れない商品は通常商品のためすぐに品番を調べる
			if (itemDTO.getLeaveClassFlg() == null) {
				if (itemDTO.getItemCode().startsWith("71")) {
					bremboItemList.add(itemDTO);
				}
			}

			//登録された商品がセット商品だった時の処理
			//セット商品が「セット商品から出庫する」場合 ※現状、品番71から始まるセット商品はない
			if (itemDTO.getLeaveClassFlg().equals("0")) {
				if (itemDTO.getItemCode().startsWith("71")) {
					bremboItemList.add(itemDTO);
				}
			//セット商品が「構成商品から出庫する」場合、構成商品がBrembo商品だったらそのセット商品をBrembo商品の対象とする
			} else if (itemDTO.getLeaveClassFlg().equals("1")) {
				//セット商品の構成商品を取得
				List<ExtendSetItemDTO> configurationItemList = itemDAO.getSetItemInfoList(itemDTO.getSysItemId());

				//上で取得した構成商品は商品IDしか持っていないため、品番を取得するには再度検索する必要がある。
				String itemCode = itemDAO.getItemCode(configurationItemList.get(0).getFormSysItemId());

				//1つ目の構成商品の品番が71で始まるかを調べる。現状Brembo商品が構成商品に入る場合は他の構成商品も全てBremboの商品のため、1つだけ調べればよい。
				//※後にBrembo以外も入ってくる可能性がある。(詳細はTODOメモにて)
				if (itemCode.startsWith("71")) {
					bremboItemList.add(itemDTO);
				}
			}
			/**** セット商品の構成商品がBrembo商品の時の処理 END ********************************/

		}

		return bremboItemList;

	}

	/**
	 * 出庫された売上伝票商品をもとにBrembo事業部の業販伝票を作成する
	 * @param bremboItemList Brembo商品のリスト
	 * @throws ParseException
	 */
	public void createBremboCorpSaleSlip(List<ExtendSalesItemDTO> bremboItemList)
			throws DaoException, ParseException {

		//重複登録を避けるためのマップ
		Map<Long, Long> duplicateProtectionMap = new LinkedHashMap<>();
		//その他必要なインスタンス
		SaleDAO saleDAO = new SaleDAO();
		CorporationDAO corporationDAO = new CorporationDAO();
		ClientDAO clientDAO = new ClientDAO();
		ItemDAO itemDAO = new ItemDAO();
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		//伝票作成日(本日日付)
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String createDateToday = sdf.format(cal.getTime());

		SaleService sService = new SaleService();

		for (int i = 0; bremboItemList.size() > i; i++) {
			//同じ伝票の商品を一つにまとめ、登録するリスト
			List<ExtendSalesItemDTO> registrySlipItemList = new ArrayList<>();
			//売上伝票ID
			Long sysSaleSlipId = bremboItemList.get(i).getSysSalesSlipId();

			//既に伝票作成した商品は飛ばす
			if (duplicateProtectionMap.containsKey(sysSaleSlipId)) {
				continue;
			}

			//1つ目をリストに格納
			registrySlipItemList.add(bremboItemList.get(i));
			//出庫した商品から同じ伝票を探し1つにまとめる処理
			for (int j = i + 1; bremboItemList.size() > j; j++) {
				long sysSaleSlipIdAnother = bremboItemList.get(j).getSysSalesSlipId();
				//同じ伝票の商品があったらリストに格納
				if (sysSaleSlipId == sysSaleSlipIdAnother) {
					registrySlipItemList.add(bremboItemList.get(j));
					//2つ以上商品があった伝票のIDをMapに記憶させる。(複数ある場合その分伝票が作成されてしまうため)
					duplicateProtectionMap.put(sysSaleSlipId, sysSaleSlipId);
				}
			}

			//リスト内の商品が登録されている伝票を検索するためのDTO
			ExtendSalesSlipDTO saleSlipDTO = new ExtendSalesSlipDTO();
			//Bemboの伝票を作成するためのDTO
			ExtendCorporateSalesSlipDTO newCorporateSaleSlipDTO = new ExtendCorporateSalesSlipDTO();
			//検索する伝票IDをセット
			saleSlipDTO.setSysSalesSlipId(bremboItemList.get(i).getSysSalesSlipId());
			//伝票IDをもとに売上伝票を検索し取得
			saleSlipDTO = saleDAO.getSaleSlip(saleSlipDTO);

			//KTS、T-FOUR、車楽院以外の法人は経理上別計上となるのでBrembo業販伝票自動生成対象外となる。
			if (!ArrayUtils.contains(BREMBO_CORP_SALE_SLIP_TARGET, saleSlipDTO.getSysCorporationId())) {
				continue;
			}

			//伝票上の法人IDで法人を検索し、法人名を取得
			String corporationName = corporationDAO.getCorporationName(saleSlipDTO.getSysCorporationId());
			//法人名から完全一致で得意先IDを検索
			long sysClientId = clientDAO.getSysClientId(corporationName);
			//得意先マスタを取得
			MstClientDTO clientDTO = clientDAO.getClient(sysClientId);

			//Bremboの新規作成分の伝票に各値を格納
			//法人ID
			newCorporateSaleSlipDTO.setSysCorporationId(12);
			//伝票ステータス
			newCorporateSaleSlipDTO.setSlipStatus("2");
			//担当者(売上伝票上に無いのでログインユーザーの名字を取得)
			newCorporateSaleSlipDTO.setPersonInCharge(userInfo.getFamilyName());
			//見積日
			newCorporateSaleSlipDTO.setEstimateDate(createDateToday);
			//注文日(業販伝票詳細画面上では受注日)
			newCorporateSaleSlipDTO.setOrderDate(createDateToday);
			//売上日
			newCorporateSaleSlipDTO.setSalesDate(createDateToday);
			//請求日
			newCorporateSaleSlipDTO.setBillingDate(createDateToday);
			//得意先ID
			newCorporateSaleSlipDTO.setSysClientId(sysClientId);
			//支払方法
			newCorporateSaleSlipDTO.setPaymentMethod("1");
			//納入期限 ※売上伝票上にないため保留
			//口座ID
			newCorporateSaleSlipDTO.setSysAccountId(12);
			//入金額
			//入金日
			newCorporateSaleSlipDTO.setReceiveDate(saleSlipDTO.getDepositDate());
			/*
			 * 備考/一言メモ
			 * ・売上伝票画面上の「一言メモ/備考(注文)」から取得
			 * ・自動生成した伝票とわかるように文言を追加
			 * ・業販統計出力処理において自動生成した分の数も含まれてしまうため、自動生成分の伝票は数えない処理を追加した
			 * ・その際に備考/一言メモに「Brembo伝票自動生成分」の文言が入っているかで判断するため処理を追加した
			 * */
			String remarks = saleSlipDTO.getOrderRemarksMemo();
			String newPar = System.getProperty("line.separator");
			//変に改行されてしまうのを防ぐため
			if (remarks.isEmpty()) {
				remarks = "Brembo伝票自動生成分";
			} else {
				remarks = remarks + newPar + "Brembo伝票自動生成分";
			}
			newCorporateSaleSlipDTO.setOrderRemarks(remarks);
			/*-- ※これらは売上伝票上にないため保留 --*/
			//注文確定書備考
			//見積書備考
			//請求書備考
			/*---------------------------------------*/
			//入金日
			newCorporateSaleSlipDTO.setReceiveDate(saleSlipDTO.getDepositDate());
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
			//備考/一言メモ(納入先) ※売上伝票上にはないため保留
			//運送会社システム
			newCorporateSaleSlipDTO.setTransportCorporationSystem(saleSlipDTO.getTransportCorporationSystem());
			//送り状種別
			newCorporateSaleSlipDTO.setInvoiceClassification(saleSlipDTO.getInvoiceClassification());
			//お届け指定日
			newCorporateSaleSlipDTO.setDestinationAppointDate(saleSlipDTO.getDestinationAppointDate());

			//お届け時間帯 ※運送会社と時間帯によって格納する値を変更する
			String transportCorporation = saleSlipDTO.getTransportCorporationSystem();
			String destinationAppointTime = saleSlipDTO.getDestinationAppointTime();
			if (transportCorporation.equals("ヤマト運輸")) {
				switch (destinationAppointTime) {
				case "指定なし":
					newCorporateSaleSlipDTO.setDestinationAppointTime("0");
					break;
				case "午前中":
					newCorporateSaleSlipDTO.setDestinationAppointTime("0812");
					break;
				case "14時-16時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("1416");
					break;
				case "16時-18時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("1618");
					break;
				case "18時-20時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("1820");
					break;
				case "19時-21時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("1921");
					break;
				}
			} else if (transportCorporation.equals("佐川急便")) {
				switch (destinationAppointTime) {
				case "指定なし":
					newCorporateSaleSlipDTO.setDestinationAppointTime("00");
					break;
				case "午前中":
					newCorporateSaleSlipDTO.setDestinationAppointTime("01");
					break;
				case "12時-14時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("12");
					break;
				case "14時-16時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("14");
					break;
				case "16時-18時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("16");
					break;
				case "18時-20時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("18");
					break;
				case "18時-21時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("04");
					break;
				case "19時-20時":
					newCorporateSaleSlipDTO.setDestinationAppointTime("19");
					break;
				}
			}
			//出荷日
			newCorporateSaleSlipDTO.setShipmentDate(saleSlipDTO.getShipmentDate());
			//税率 ※8％

			int taxRate = sService.getTaxRate(newCorporateSaleSlipDTO.getOrderDate());

			newCorporateSaleSlipDTO.setTaxRate(taxRate);

			//通貨
			newCorporateSaleSlipDTO.setCurrency("1");
			//返品フラグ
			newCorporateSaleSlipDTO.setReturnFlg("0");

			//売上合計
			int sumSalesPrice = 0;
			//商品単価承継
			int sumPieceRate = 0;
			//税
			int tax = 0;

			//新規登録用商品リスト
			List<ExtendCorporateSalesItemDTO> newRegistryItemList = new ArrayList<>();

			//ここから商品の金額計算及び新規伝票に紐付く新規業販伝票商品を作成
			//新規Brembo伝票の単価：出庫した商品の原価、原価：Brembo商品の原価になる
			for (ExtendSalesItemDTO salesItemDTO : registrySlipItemList) {
				//業販伝票商品を新規登録するためのDTO
				ExtendCorporateSalesItemDTO newSalesItemDTO = new ExtendCorporateSalesItemDTO();
				//出庫した商品の原価
				int itemCost = salesItemDTO.getCost();
				//商品IDからBremboの原価を取得(Bremboの法人IDは12)
				int bremboItemCost = itemDAO.getItemCost(salesItemDTO.getSysItemId(), 12).getCost();
				//kind原価取得
				int kindCost = (int)itemDAO.getKindCostDTO(salesItemDTO.getSysItemId()).getKindCost();

				//売上合計を加算 (単価×出庫数) ※売上伝票の場合は一部出庫されたとしても注文数の分計算する
				sumSalesPrice += salesItemDTO.getCost() * salesItemDTO.getOrderNum();
				//商品単価小計を加算 ※売上伝票の場合は一部出庫されたとしても注文数の分計算する
				sumPieceRate += salesItemDTO.getCost() * salesItemDTO.getOrderNum();

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
				//出庫数 (一部出庫されたとしても注文数の分だけ値を入れる)
				newSalesItemDTO.setLeavingNum(salesItemDTO.getOrderNum());
				//売上日
				newSalesItemDTO.setSalesDate(createDateToday);
				//ピッキングリスト出力フラグ (ピッキングリストは出力されたものとしてみなす)
				newSalesItemDTO.setPickingListFlg("on");
				//kind原価
				newSalesItemDTO.setKindCost(kindCost);

				newRegistryItemList.add(newSalesItemDTO);

			}

			//税計算(小数点以下は切り捨て)
			//売上伝票においてのみ内税外税があるが業販伝票では外税しかないため外税で計算する
			tax = (int)Math.floor(sumPieceRate * taxRate / 100);

			//売上合計
			newCorporateSaleSlipDTO.setSumSalesPrice(sumSalesPrice + tax);
			//合計請求金額
			newCorporateSaleSlipDTO.setSumClaimPrice(sumPieceRate + tax);
			//商品単価小計
			newCorporateSaleSlipDTO.setSumPieceRate(sumPieceRate);
			//税
			newCorporateSaleSlipDTO.setTax(tax);

			//業販伝票を登録
			corporateSaleDisplayService.newRegistryCorporateSalesSlip(newCorporateSaleSlipDTO);
			//業販伝票に紐付く商品を登録
			corporateSaleDisplayService.registryCorporateSaleItemList(newRegistryItemList, newCorporateSaleSlipDTO.getSysCorporateSalesSlipId(), newCorporateSaleSlipDTO);

		}
	}

	/**
	 * 構成商品出庫処理
	 *
	 * @param sysItemId
	 *            商品ID
	 * @param num
	 *            変動数
	 * @return
	 * @throws DaoException
	 */

	private void updateSetItemStock(long sysItemId, int num, String orderNo)
			throws DaoException {

		List<ExtendSetItemDTO> setItemList = new ItemService()
				.getSetItemList(sysItemId);

		ItemService itemService = new ItemService();

		for (ExtendSetItemDTO dto : setItemList) {
			if (dto.getLeaveClassFlg().equals("0")) {

				long sysKeepId = itemService.getSysKeepId(orderNo,
						dto.getFormSysItemId());
				if (sysKeepId != 0) {
					// キープ削除処理
					itemService.deleteKeep(sysKeepId);
				}

				// 在庫更新処理
				itemService.lumpUpdateStock(dto.getFormSysItemId(),
						dto.getItemNum() * num);

			} else if (dto.getLeaveClassFlg().equals("1")) {
				// 1層目の構成商品リストを取得
				List<ExtendSetItemDTO> secondItemList = new ItemService()
						.getSetItemList(dto.getFormSysItemId());

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
								secondItem.getItemNum() * dto.getItemNum()
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
												* dto.getItemNum() * num);

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
														* dto.getItemNum()
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
															* dto.getItemNum()
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

	private String getLeavingFlg(long sysSalesSlipId) throws DaoException {

		ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO();
		dto.setSysSalesSlipId(sysSalesSlipId);

		dto = new SaleDAO().getSaleSlip(dto);

		if (dto == null) {
			return null;
		}

		return dto.getLeavingFlg();
	}

	/**
	 * 伝票Listを出庫済みにします
	 *
	 * @param list
	 * @throws DaoException
	 */
	public void updateLeaveStockList(List<ExtendSalesSlipDTO> list)
			throws DaoException {

		for (ExtendSalesSlipDTO dto : list) {

			updateLeaveStock(dto.getSysSalesSlipId());
		}
	}

	/**
	 * 伝票を出庫済みにします
	 *
	 * @param sysSalesSlipId
	 * @throws DaoException
	 */
	private void updateLeaveStock(long sysSalesSlipId) throws DaoException {

		ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO();
		dto.setSysSalesSlipId(sysSalesSlipId);
		dto.setPickingListFlg(null);
		dto.setLeavingFlg(StringUtil.SWITCH_ON_VALUE);
		/** 20140521 Takakuwa 出庫時に現在の日付を出荷日として登録する処理を追加 */
		dto.setShipmentDate(DateUtil.dateToString("yyyy/MM/dd"));
		new SaleDAO().updateSalesSlipFlg(dto);

	}

	public void updatePickFlg(List<ExtendSalesSlipDTO> salesSlipList)
			throws DaoException {

		for (ExtendSalesSlipDTO dto : salesSlipList) {

			setFlagsDB(dto);
			dto.setPickingListFlg(StringUtil.SWITCH_ON_VALUE);
			new SaleDAO().updateSalesSlipFlg(dto);
		}
	}

	public void deleteSalesSlip(long sysSalesSlipId) throws DaoException {

		new SaleDAO().deleteSalesSlip(sysSalesSlipId);
	}

	public void deleteSalesItem(List<ExtendSalesItemDTO> salesItemList,
			long sysSalesSlipId) throws DaoException {

		for (ExtendSalesItemDTO dto : salesItemList) {

			deleteSalesItem(dto.getSysSalesItemId());
		}
	}

	private void deleteSalesItem(long sysSalesitemId) throws DaoException {

		new SaleDAO().deleteSalesItem(sysSalesitemId);
	}

	// private SalesSlipDTO setSalesSlip(String[] csvLineArray) {
	//
	// SalesSlipDTO salesSlipDTO = new SalesSlipDTO();
	//
	// return salesSlipDTO;
	// }

	public StoreDTO selectShopInfo(long sysCorporationId, long sysChannelId)
			throws Exception {

		StoreDTO dto = new StoreDTO();
		dto = new SaleDAO().selectStoreInfo(sysCorporationId, sysChannelId);
		return dto;

	}

	/**
	 * 出庫処理対象商品並び替え処理
	 * @param leaveStockList
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendSalesItemDTO> sortLeaveStockList(
			List<ExtendSalesItemDTO> leaveStockList) throws DaoException {

		if (leaveStockList == null || leaveStockList.size() <= 1) {

			return leaveStockList;
		}

		// List<ExtendSalesSlipDTO> saleSlipList = new LinkedList<>();
		// long[] arrSyaSalesItemid = new long[leaveStockList.size()];
		// String[] arrOrderDateTime = new String[leaveStockList.size()];
		// int idx = 0;
		for (ExtendSalesItemDTO dto : leaveStockList) {

			// saleSlipList.add(getSalesSlip(dto.getSysSalesSlipId()));
			ExtendSalesSlipDTO salesSlipDTO = getSalesSlip(dto
					.getSysSalesSlipId());
			dto.setOrderDate(salesSlipDTO.getOrderDate());
			dto.setOrderTime(salesSlipDTO.getOrderTime());
		}

		// for (int i = 0; i < saleSlipList.size(); i++) {
		for (int i = 0; i < leaveStockList.size(); i++) {

			// ExtendSalesSlipDTO salesSlipDTO = saleSlipList.get(i);
			ExtendSalesItemDTO leaveStockDTO = leaveStockList.get(i);
			// String strDate = salesSlipDTO.getOrderDate() +
			// salesSlipDTO.getOrderTime();
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
			ExtendSalesItemDTO tempLeaveStockDTO = leaveStockList.get(i);
			leaveStockList.set(i, leaveStockList.get(minIdx));
			leaveStockList.set(minIdx, tempLeaveStockDTO);
		}

		return leaveStockList;
	}

	public long getGrossMargin(ExtendSalesSlipDTO salesSlipDTO,
			List<ExtendSalesItemDTO> list, SaleSearchDTO saleSearchDTO) {

		long grossMargin = 0;
		String grossProfitCalc = saleSearchDTO.getGrossProfitCalc();

		long cost = 0;
		for (ExtendSalesItemDTO dto : list) {

			cost += dto.getCost() * dto.getOrderNum();
		}
		if (StringUtils.equals(grossProfitCalc,
				WebConst.GROSS_PROFIT_CALC_TOTAL_CODE)) {

			grossMargin = salesSlipDTO.getSumClaimPrice() - cost;

		} else if (StringUtils.equals(grossProfitCalc,
				WebConst.GROSS_PROFIT_CALC_SUBTOTAL_CODE)) {

			long tax = salesSlipDTO.getTax();
			if (StringUtils.equals(salesSlipDTO.getTaxClass(),
					WebConst.TAX_CODE_INCLUDE)) {

				cost += tax;
			}
			grossMargin = salesSlipDTO.getSumPieceRate() - cost;
		}

		return grossMargin;
	}

	/**
	 * 受注番号の重複検索
	 * @param salesSlipDTO
	 * @return
	 * @throws DaoException
	 */
	public ErrorDTO salesSlipValidate(ExtendSalesSlipDTO salesSlipDTO) throws DaoException {

		ErrorDTO errorMessageDTO = new ErrorDTO();
		String searchResult = new SaleDAO().getSearchSalesSlipOrderNo(salesSlipDTO.getOrderNo(), salesSlipDTO.getOriginOrderNo());

		//検索結果がある場合エラーメッセージ格納
		if (searchResult != null) {
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("既に登録されている受注番号です。");
		}

		return errorMessageDTO;
	}

}
