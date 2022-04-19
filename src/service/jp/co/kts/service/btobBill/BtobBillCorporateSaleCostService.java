package jp.co.kts.service.btobBill;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticExhibitionDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.output.entity.SysCorprateSaleItemIDDTO;
import jp.co.kts.app.output.entity.SysSaleItemIDDTO;
import jp.co.kts.app.search.entity.CorporateSaleCostSearchDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.app.search.entity.SaleCostSearchDTO;
import jp.co.kts.dao.mst.DomesticExhibitionDAO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.ui.web.struts.WebConst;



/**
 * 法人間請求書機能・業販原価サービスクラス
 * 業務ロジックにて使用される機能を提供する。
 *
 * 作成日　：2016/1/4
 * 作成者　：大山智史
 * 更新日　：
 * 更新者　：
 *
 */
public class BtobBillCorporateSaleCostService {

	/**
	 * 業販原価一覧検索用DTOの初期化
	 * @return 業販原価一覧検索用DTO
	 */
	public CorporateSaleCostSearchDTO initCorpSaleCostSearchDTO() {

		CorporateSaleCostSearchDTO searchDto = new CorporateSaleCostSearchDTO();

		searchDto.setSlipStatus("3");/*表示ステータス*/
		searchDto.setPickingListFlg("0");
		searchDto.setLeavingFlg("0");

		searchDto.setSearchAllFlg("ON");

		searchDto.setListPageMax(WebConst.LIST_PAGE_MAX_CODE_6);
		searchDto.setSortFirstSub("2");

		//通貨コード
		searchDto.setCurrency("0");

		// strutsの defineタグで使用するためnull回避
		searchDto.setScheduledLeavingDateFrom(StringUtils.EMPTY);
		searchDto.setScheduledLeavingDateTo(StringUtils.EMPTY);

		return searchDto;

	}

	/**
	 * 検索用フラグ読み換え
	 *
	 * @param dto
	 */
	public void setFlags(CorporateSaleCostSearchDTO dto) {

		if (StringUtils.isNotEmpty(dto.getReturnFlg())) {
			dto.setReturnFlg(StringUtil.switchCheckBox(dto.getReturnFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getSearchAllFlg())) {
			dto.setSearchAllFlg(StringUtil.switchCheckBox(dto.getSearchAllFlg()));
		}

		if (StringUtils.isNotEmpty(dto.getInvalidFlag())) {
			dto.setInvalidFlag(StringUtil.switchCheckBox(dto.getInvalidFlag()));
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
	}

	/**
	 * 業販原価一覧検索用DTOにて検索し、
	 * 検索結果・システム業販商品IDリストを取得する。
	 *
	 * @param 業販原価一覧検索用DTO
	 * @return 検索結果・システム業販商品IDリスト
	 */
	public List<SysCorprateSaleItemIDDTO> getCorporateSaleItemIDList(
			CorporateSaleCostSearchDTO corpSaleSearchDTO) throws DaoException {

		List<SysCorprateSaleItemIDDTO> list = new CorporateSaleDAO()
				.getSearchCorporateSalesItemIDList(corpSaleSearchDTO);

		return list;
	}

	/**
	 * システム業販商品IDリストから指定されたページの業販原価一覧リストを取得する。
	 *
	 * @param システム業販商品IDリスト
	 * @param 指定ページ
	 * @param 業販原価一覧検索用DTO
	 * @return 業販原価一覧リスト
	 */
	public List<ExtendCorporateSalesItemDTO> getCorpSaleCostList(List<SysCorprateSaleItemIDDTO> saleItemIDList, int pageIdx,
			CorporateSaleCostSearchDTO dto) throws DaoException {

		List<ExtendCorporateSalesItemDTO> salesCostList = new ArrayList<>();

		// 業販原価一覧１ページ当りの最大表示件数を確認
		if (StringUtils.isEmpty(dto.getListPageMax())) {
			// 設定されていない場合、100件に設定
			dto.setListPageMax("3");
		}

		// 指定されたページに表示する分のレコードをシステム業販商品IDリストをもとに
		// 1件ずつDBから取得する。
		for (int i = WebConst.LIST_PAGE_MAX_MAP.get(dto.getListPageMax())
				* pageIdx; i < WebConst.LIST_PAGE_MAX_MAP.get(dto
				.getListPageMax()) * (pageIdx + 1)
				&& i < saleItemIDList.size(); i++) {

			CorporateSaleCostSearchDTO costSearchDTO = new CorporateSaleCostSearchDTO();

			// システム業販商品IDを検索条件に設定
			costSearchDTO.setSysCorpSaleItemId(saleItemIDList.get(i).getSysCorporateSalesItemId());

			CorporateSaleDAO corpSaleDAO = new CorporateSaleDAO();

			// システム業販商品IDから業販原価情報を取得する
			ExtendCorporateSalesItemDTO itemCostDTO = corpSaleDAO.getSearchCorpSalesCost(costSearchDTO);

			setCheckFlags(itemCostDTO);
			salesCostList.add(itemCostDTO);

		}

		return salesCostList;

	}

	/**
	 * 業販原価情報チェックボックスのキーと値を設定し直す。
	 *
	 * @param 業販原価情報DTO
	 */
	public void setCheckFlags(ExtendCorporateSalesItemDTO dto) {

		if (StringUtils.isNotEmpty(dto.getCostCheckFlag())) {
			dto.setCostCheckFlag(StringUtil.switchCheckBox(dto
					.getCostCheckFlag()));
		}
	}

	/**
	 * 業販原価一覧リストの原価・Kind原価・定価・商品掛け率をDBに反映する。
	 *
	 * @param 業販原価一覧リスト
	 */
	public void updateCorpSaleCostList(List<ExtendCorporateSalesItemDTO> salesCostList) throws DaoException{

		CorporateSaleDAO saleDAO = new CorporateSaleDAO();

		// 売上原価一覧リストから1件ずつ取り出しアップデート
		for(ExtendCorporateSalesItemDTO dto : salesCostList){
			dto.setUpdatedFlag(1);
			setCheckFlags(dto);
			saleDAO.updateCorpSalesCost(dto);
			
			DomesticExhibitionDAO domesticDAO = new DomesticExhibitionDAO();
			DomesticExhibitionDTO domesticDto = new DomesticExhibitionDTO();
			
			domesticDto.setManagementCode(dto.getItemCode());
			domesticDto.setItemRateOver(Double.valueOf(String.valueOf(dto.getItemRateOver())));
			
			domesticDAO.updateItemCodeDomesticExhibition(domesticDto);
			
		}

	}

	public void updateCorpSaleCostId(ExtendCorporateSalesItemDTO corporateSalesCost) throws DaoException{

		CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();

//		setCheckFlags(salesCost);
		corporateSaleDAO.updateCorpSalesCost(corporateSalesCost);

	}
	/**
	 * 選択した業販原価情報の直近の原価を取得し、原価を書き換えて、一覧に設定し直す。
	 *
	 * @param 業販原価一覧リスト
	 * @param 選択行のインデックス
	 * @return 設定し直した業販原価一覧リスト
	 */
	public List<ExtendCorporateSalesItemDTO> reflectLatestCorpSaleCost(
			List<ExtendCorporateSalesItemDTO> salesCostList, int saleCostListIdx)  throws DaoException {

		CorporateSaleDAO saleDAO = new CorporateSaleDAO();

		// 選択行の情報を取得から直近の原価を取得
		ExtendCorporateSalesItemDTO salesItemDTO = saleDAO.getLatestCorpSaleCost(salesCostList.get(saleCostListIdx));

		// 業販原価一覧リストに取得した値を設定し直す。
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
