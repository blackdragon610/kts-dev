package jp.co.kts.service.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.mst.DomesticOrderDAO;

public class ItemSearchService {

	/**
	 * 自社商品の検索サービス
	 * @param searchItemDTO
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstItemDTO> getSearchItemList(SearchItemDTO searchItemDTO) throws DaoException {
		List<ExtendMstItemDTO> item = new ArrayList<ExtendMstItemDTO>();
		List<ExtendMstItemDTO> addItem = new ArrayList<ExtendMstItemDTO>();

		item = new ItemDAO().getMstItemList(searchItemDTO);
		for (ExtendMstItemDTO dto : item) {
			dto.setItemType("海外");
			addItem.add(dto);
		}
		return addItem;
	}

	/**
	 * 国内商品の検索サービス
	 * @param searchDto
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstItemDTO> getSearchDomesticItemList(SearchItemDTO searchDto) throws DaoException {

		List<ExtendMstItemDTO> item = new ArrayList<ExtendMstItemDTO>();
		List<ExtendMstItemDTO> addItem = new ArrayList<ExtendMstItemDTO>();

		item = new DomesticOrderDAO().getSearchDomesticItemList(searchDto);
		CorporationDAO crpDao = new CorporationDAO();
		MstCorporationDTO crpDto = new MstCorporationDTO();
		crpDto = crpDao.getCorporation(searchDto.getSysCorporationId());


		for (ExtendMstItemDTO dto : item) {
			//法人掛率と商品掛率を合算
			BigDecimal sumRate = crpDto.getCorporationRateOver().add(BigDecimal.valueOf(dto.getItemRateOver()));
			int cost = sumRate.multiply(BigDecimal.valueOf(dto.getListPrice())).divide(BigDecimal.valueOf(100)).intValue();
			dto.setCost(cost);
			dto.setItemType("国内");
			dto.setSysItemId(0);

			addItem.add(dto);
		}

		return addItem;
	}

}
