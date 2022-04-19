package jp.co.kts.service.btobBill;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.SysItemIdDTO;
import jp.co.kts.app.output.entity.SysSaleItemIDDTO;
import jp.co.kts.app.output.entity.SysSalesSlipIdDTO;
import jp.co.kts.app.search.entity.SaleCostSearchDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 法人間請求書機能・売上原価サービスクラス
 * 業務ロジックにて使用される機能を提供する。
 *
 * 作成日　：2015/12/22
 * 作成者　：大山智史
 * 更新日　：
 * 更新者　：
 *
 */


public class BtobBillSaleCostService {


	/**
	 * 売上原価一覧検索用DTOの初期化
	 * @return 売上原価一覧検索用DTO
	 */
	public SaleCostSearchDTO initSaleCostSearchDTO() {

		SaleCostSearchDTO dto = new SaleCostSearchDTO();

		/* 注文日終わり */
		dto.setOrderDateTo(StringUtil.getToday());

		dto.setGrossProfitCalc(WebConst.GROSS_PROFIT_CALC_TOTAL_CODE);

		/* 1ページあたりの最大表示件数 */
		dto.setListPageMax(WebConst.LIST_PAGE_MAX_CODE_6);

		dto.setSortFirstSub("2");

		return dto;
	}

	/**
	 * 売上原価一覧検索用DTOチェックボックスのキーと値を設定し直す。
	 *
	 * @param 売上原価一覧検索用DTO
	 */
	public void setFlags(SaleCostSearchDTO dto) {

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

		// 原価・メーカー品
		if (StringUtils.isNotEmpty(dto.getCostMakerItemFlg())) {
			dto.setCostMakerItemFlg(StringUtil.switchCheckBox(dto.getCostMakerItemFlg()));
		}

		// 原価・原価未入力
		if (StringUtils.isNotEmpty(dto.getCostNoRegistry())) {
			dto.setCostNoRegistry(StringUtil.switchCheckBox(dto.getCostNoRegistry()));
		}

		// 原価・原価0円
		if (StringUtils.isNotEmpty(dto.getCostZeroRegistry())) {
			dto.setCostZeroRegistry(StringUtil.switchCheckBox(dto.getCostZeroRegistry()));
		}

		// 原価チェック：未チェックフラグ
		if (StringUtils.isNotEmpty(dto.getCostNoCheckFlg())) {
			dto.setCostNoCheckFlg(StringUtil.switchCheckBox(dto.getCostNoCheckFlg()));
		}

		// 原価チェック：チェック済フラグ
		if (StringUtils.isNotEmpty(dto.getCostCheckedFlg())) {
			dto.setCostCheckedFlg(StringUtil.switchCheckBox(dto.getCostCheckedFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getSysChannelIdOne())) {
			dto.setSysChannelIdOne(StringUtil.switchCheckBox(dto.getSysChannelIdOne()));
		}

		if (StringUtils.isNotEmpty(dto.getSysChannelIdTwo())) {
			dto.setSysChannelIdTwo(StringUtil.switchCheckBox(dto.getSysChannelIdTwo()));
		}

		if (StringUtils.isNotEmpty(dto.getSysChannelIdOther())) {
			dto.setSysChannelIdOther(StringUtil.switchCheckBox(dto.getSysChannelIdOther()));
		}
	}


	/**
	 * 売上原価一覧検索用DTOにて検索し、
	 * 検索結果・システム売上商品IDリストを取得する。
	 *
	 * @param 売上原価一覧検索用DTO
	 * @return 検索結果・システム売上商品IDリスト
	 */
	public List<SysSaleItemIDDTO> getSaleItemIDList(
			SaleCostSearchDTO saleSearchDTO) throws DaoException {

		List<SysSaleItemIDDTO> list = new SaleDAO()
				.getSearchSysSaleItemIDList(saleSearchDTO);

		return list;
	}

	/**
	 * システム売上商品IDリストから指定されたページの売上原価一覧リストを取得する。
	 *
	 * @param システム売上商品IDリスト
	 * @param 指定ページ
	 * @param 売上原価一覧検索用DTO
	 * @return 売上原価一覧リスト
	 */
	public List<ExtendSalesItemDTO> getSaleCostList(List<SysSaleItemIDDTO> saleItemIDList, int pageIdx,
			SaleCostSearchDTO dto) throws DaoException {

		List<ExtendSalesItemDTO> salesCostList = new ArrayList<>();

		// 売上原価一覧１ページ当りの最大表示件数を確認
		if (StringUtils.isEmpty(dto.getListPageMax())) {
			// 設定されていない場合、100件に設定
			dto.setListPageMax("3");
		}

		// 指定されたページに表示する分のレコードをシステム売上商品IDリストをもとに
		// 1件ずつDBから取得する。
		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(dto.getListPageMax())
				* pageIdx; i < WebConst.LIST_PAGE_MAX_MAP.get(dto
				.getListPageMax()) * (pageIdx + 1)
				&& i < saleItemIDList.size(); i++) {


			SaleCostSearchDTO costSearchDTO = new SaleCostSearchDTO();

			// システム売上商品IDを検索条件に設定
			costSearchDTO.setSysSaleItemId(saleItemIDList.get(i).getSysSalesItemId());

			SaleDAO saleDAO = new SaleDAO();

			// システム売上商品IDから売上原価情報を取得する
			ExtendSalesItemDTO itemCostDTO = saleDAO.getSearchSalesCost(costSearchDTO);

			setCheckFlags(itemCostDTO);
			salesCostList.add(itemCostDTO);

		}

		return salesCostList;

	}

	/**
	 * 売上原価情報チェックボックスのキーと値を設定し直す。
	 *
	 * @param 売上原価情報DTO
	 */
	public void setCheckFlags(ExtendSalesItemDTO dto) {

		if (StringUtils.isNotEmpty(dto.getCostCheckFlag())) {
			dto.setCostCheckFlag(StringUtil.switchCheckBox(dto
					.getCostCheckFlag()));
		}
	}

	/**
	 * 売上原価一覧リストの原価・Kind原価・定価・商品掛け率をDBに反映する。
	 *
	 * @param 売上原価一覧リスト
	 */
	public void updateSaleCostList(List<ExtendSalesItemDTO> salesCostList) throws DaoException{

		SaleDAO saleDAO = new SaleDAO();

		// 売上原価一覧リストから1件ずつ取り出しアップデート
		for(ExtendSalesItemDTO dto : salesCostList){
			setCheckFlags(dto);
			dto.setUpdatedFlag(1);
			saleDAO.updateSalesCost(dto);

			DomesticExhibitionDAO domesticDAO = new DomesticExhibitionDAO();
			DomesticExhibitionDTO domesticDto = new DomesticExhibitionDTO();
			
			domesticDto.setManagementCode(dto.getItemCode());
			domesticDto.setItemRateOver(Double.valueOf(String.valueOf(dto.getItemRateOver())));
			
			domesticDAO.updateItemCodeDomesticExhibition(domesticDto);
			
//			ExtendSalesSlipDTO slipDto = new ExtendSalesSlipDTO();
//			
//			slipDto.setSysSalesSlipId(dto.getSysSalesSlipId());
//			slipDto.setPostage(dto.getDomePostage());
//			
//			saleDAO.updateSalesSlipPostage(slipDto);
		}

	}

	public void updateSaleCostId(ExtendSalesItemDTO salesCost) throws DaoException{

		SaleDAO saleDAO = new SaleDAO();

//		setCheckFlags(salesCost);
		saleDAO.updateSalesCost(salesCost);

	}

	/**
	 * 選択した売上原価情報の直近の原価を取得し、原価を書き換えて、一覧に設定し直す。
	 *
	 * @param 売上原価一覧リスト
	 * @param 選択行のインデックス
	 * @return 設定し直した売上原価一覧リスト
	 */
	public List<ExtendSalesItemDTO> reflectLatestSaleCost(
			List<ExtendSalesItemDTO> salesCostList, int saleCostListIdx)  throws DaoException {

		SaleDAO saleDAO = new SaleDAO();

		// 選択行の情報を取得から直近の原価を取得
		ExtendSalesItemDTO salesItemDTO = saleDAO.getLatestSaleCost(salesCostList.get(saleCostListIdx));

		// 売上原価一覧リストに取得した値を設定し直す。
		// 原価
		salesCostList.get(saleCostListIdx).setCost(salesItemDTO.getCost());
		// Kind原価
		salesCostList.get(saleCostListIdx).setKindCost(salesItemDTO.getKindCost());
		
		salesCostList.get(saleCostListIdx).setPostage(salesItemDTO.getPostage());
		// 定価
		salesCostList.get(saleCostListIdx).setListPrice(salesItemDTO.getListPrice());
		// 商品掛け率
		salesCostList.get(saleCostListIdx).setItemRateOver(salesItemDTO.getItemRateOver());

		return salesCostList;
	}



}
