package jp.co.kts.service.sale;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.mst.DomesticOrderDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 売上機能で使用するクラスです
 * @author aito
 *
 */
public class SaleService {

	//税率判定
	protected int getTaxRate(String orderDate) throws ParseException {

		Date taxUpDate8 = new Date();
		Date taxUpDate10 = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		taxUpDate8 = dateFormat.parse(WebConst.TAX_UP_DATE_8);
		taxUpDate10 = dateFormat.parse(WebConst.TAX_UP_DATE_10);

		int taxRate = 0;
		if (DateUtil.parseYYYYMMDDDate(orderDate).compareTo(taxUpDate8) < 0) {
			taxRate = WebConst.TAX_RATE_5;
		} else if (DateUtil.parseYYYYMMDDDate(orderDate).compareTo(taxUpDate10) < 0){
			taxRate = WebConst.TAX_RATE_8;
		} else {
			taxRate = WebConst.TAX_RATE_10;
		}
		return taxRate;
	}

	protected int getTax(long sumPieceRate, String taxClass, int taxRate) {

		if (sumPieceRate == 0) {
			return 0;
		}
		//TODO 税計算のロジックを変更する
		//saleDetail.jspのtaxCalcにも同じロジックがあり
		//ここの税計算ロジックを変更した場合、同じ仕様に変更する必要あり
		long tax = 0;
		//内税の場合
		if (StringUtils.equals(WebConst.TAX_CODE_INCLUDE, taxClass)) {
			if (sumPieceRate < 0) {
				sumPieceRate = sumPieceRate * -1;
				tax = sumPieceRate - (int)Math.floor((sumPieceRate / (double)(100 + taxRate)) * 100);
				tax = tax * -1;
			} else {
				tax = sumPieceRate - (int)Math.floor((sumPieceRate / (double)(100 + taxRate)) * 100);
			}


		//外税の場合
		} else if (StringUtils.equals(WebConst.TAX_CODE_EXCLUSIVE, taxClass)) {
			if (sumPieceRate < 0) {
				sumPieceRate = sumPieceRate * -1;
				tax = (int)Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
				tax = tax * -1;
			} else {
				tax = (int)Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
			}
		}

		return (int)tax;
	}

	protected String exsistOrderNo(String orderNo) throws DaoException {

		ExtendSalesSlipDTO dto = new ExtendSalesSlipDTO(StringUtils.EMPTY);
		dto.setOrderNo(orderNo);

		dto = new SaleDAO().getSaleSlip(dto);

		if (dto == null) {

			return StringUtils.EMPTY;
		}
		return dto.getOrderNo();
	}

	/**
	 * 商品単価の合計を取得します
	 * @param salesItemList
	 * @return
	 */
	protected int getSumPieceRate(List<ExtendSalesItemDTO> salesItemList) {

		int sumPieceRate = 0;
		for (ExtendSalesItemDTO dto: salesItemList) {

			sumPieceRate += (dto.getPieceRate() * dto.getOrderNum());
		}
		return sumPieceRate;
	}

	/**
	 * 売上商品の登録を行う
	 * @param dto
	 * @param sysSalesSlipId
	 * @param salesSlipDto
	 * @throws DaoException
	 */
	public void registrySaleItem(ExtendSalesItemDTO dto
			, long sysSalesSlipId, ExtendSalesSlipDTO salesSlipDto) throws DaoException {

		DomesticOrderDAO dao = new DomesticOrderDAO();
		DomesticOrderListDTO orderItemDTO  = new DomesticOrderListDTO();
		/*  2016/1/22 ooyama ADD START 法人間請求書機能対応  */

		dto.setSysSalesItemId(new SequenceDAO().getMaxSysSalesItemId() + 1);
		dto.setSysSalesSlipId(sysSalesSlipId);

		ExtendMstItemDTO kindDTO = null;

		//システムIDがある場合は商品コードはTBに格納されているため、登録はしない
		// 在庫情報からKind原価を取得する
		if (dto.getSysItemId() != 0) {

			dto.setItemCode(StringUtils.EMPTY);

			// Kind原価を取得
			ItemDAO itemDAO = new ItemDAO();
			kindDTO = itemDAO.getKindCostDTO(dto.getSysItemId());

		}

		if(kindDTO == null){
			// 在庫にない商品はそのまま登録する。
			if (salesSlipDto != null) {
				orderItemDTO = dao.getDomesticOrderItemSearchCsv(dto.getItemCode(), salesSlipDto.getOrderNo());
				if (orderItemDTO != null) {
					MstCorporationDTO corpDto = new MstCorporationDTO();
					CorporationDAO corpDao = new CorporationDAO();
					corpDto = corpDao.getCorporation(salesSlipDto.getSysCorporationId());
					//法人掛率と商品掛率を合算
					BigDecimal sumRate = corpDto.getCorporationRateOver().add(BigDecimal.valueOf(orderItemDTO.getItemRateOver()));
					//Kind原価
					String kindCost = String.valueOf(orderItemDTO.getPurchasingCost());
					//定価
					String listPrice = String.valueOf(orderItemDTO.getListPrice());
					//商品掛率
					BigDecimal itemRateOver = BigDecimal.valueOf(orderItemDTO.getItemRateOver());
					sumRate.add(itemRateOver);
 					int cost = sumRate.multiply(BigDecimal.valueOf(orderItemDTO.getListPrice())).divide(BigDecimal.valueOf(100)).intValue();
					dto.setKindCost(Integer.valueOf(kindCost));
					dto.setListPrice(Integer.valueOf(listPrice));
					dto.setItemRateOver(itemRateOver);
					dto.setCost(cost);
				}
			}
			new SaleDAO().registrySaleItem(dto);
		}else{
			// Kind原価を設定
			dto.setKindCost((int)kindDTO.getCost());

			// kind原価登録用
			new SaleDAO().registrySaleItemKindCost(dto);
		}

		/*  2016/1/22 ooyama ADD START 法人間請求書機能対応  */
	}

	public void updateSalesItem(ExtendSalesItemDTO dto) throws DaoException {

//		ExtendSalesItemDTO dto = aadto;
//		if (dto.getSysItemId() != 0) {
//			dto.setCost(0);
//			dto.setItemCode(StringUtils.EMPTY);
//		}

		new SaleDAO().updateSalesItem(dto);
	}
}
