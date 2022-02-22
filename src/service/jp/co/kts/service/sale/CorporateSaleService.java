package jp.co.kts.service.sale;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CorporateSalesSlipDTO;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.item.ItemDAO;
import jp.co.kts.dao.mst.CorporationDAO;
import jp.co.kts.dao.mst.DomesticOrderDAO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.dao.sale.SaleDAO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 業販機能で使用するクラスです
 * @author aito
 *
 */
public class CorporateSaleService {

	/**
	 * 業販伝票新規登録サービス
	 * @param dto
	 * @throws DaoException
	 */
	public void registryCorporateSalesSlip(ExtendCorporateSalesSlipDTO dto) throws DaoException {

		dto.setSysCorporateSalesSlipId(new SequenceDAO().getMaxSysCorporateSalesSlipId() + 1);

		new CorporateSaleDAO().registryCorporateSaleSlip(dto);

	}

	/**
	 * 税率判定サービス
	 * @param orderDate
	 * @return
	 * @throws ParseException
	 */
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

	/**
	 * 税計算サービス
	 * @param sumPieceRate
	 * @param taxRate
	 * @return
	 * @throws Exception
	 */
	public int getTax(long sumPieceRate, int taxRate) throws Exception {

		if (sumPieceRate == 0) {
			return 0;
		}

		//corporateSaleDetail.jspのtaxCalcにも同じロジックがあり
		//ここの税計算ロジックを変更した場合、同じ仕様に変更する必要あり
		long tax = 0;

		//業販は外税のみ
		//税端数は全て切り捨て
		if (sumPieceRate < 0) {
			sumPieceRate = sumPieceRate * -1;
			tax = (int)Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
			tax = tax * -1;
		} else {
			tax = (int)Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
		}


		return (int)tax;
	}

	/**
	 * 業販請求書検索：伝票番号キー
	 * @param orderNo
	 * @return
	 * @throws DaoException
	 */
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
	 * @param corporateSalesItemList
	 * @return
	 */
	protected int getSumPieceRate(List<ExtendCorporateSalesItemDTO> corporateSalesItemList) {

		int sumPieceRate = 0;
		for (ExtendCorporateSalesItemDTO dto: corporateSalesItemList) {

			sumPieceRate += (dto.getPieceRate() * dto.getOrderNum());
		}
		return sumPieceRate;
	}

	/**
	 * 出庫済み商品の合計を取得
	 * @param corporateSalesItemList
	 * @return
	 */
	protected int getSumSalesRate (List<ExtendCorporateSalesItemDTO> corporateSalesItemList) {
		int sumSalecRate = 0;
		for (ExtendCorporateSalesItemDTO dto: corporateSalesItemList) {

			sumSalecRate += (dto.getPieceRate() * dto.getLeavingNum());
		}
		return sumSalecRate;
	}

	/**
	 * 業販商品登録サービス
	 * [概要]システム業販売上伝票IDに紐づける業販商品を登録するサービス
	 * @param dto
	 * @param sysCorporateSalesSlipId
	 * @param saleDto
	 * @throws DaoException
	 */
	public void registryCorporateSaleItem(ExtendCorporateSalesItemDTO dto, long sysCorporateSalesSlipId
			,CorporateSalesSlipDTO saleDto) throws DaoException {

		//インスタンス生成
		DomesticOrderDAO dao = new DomesticOrderDAO();
		DomesticOrderListDTO orderItemDTO  = new DomesticOrderListDTO();

		//システム業販売上商品IDを取得し設定(最大値)
		dto.setSysCorporateSalesItemId(new SequenceDAO().getMaxSysCorporateSalesItemId() + 1);
		//システム業販売上伝票IDを設定
		dto.setSysCorporateSalesSlipId(sysCorporateSalesSlipId);
		//DBに格納するフラグの置換を行う
		new CorporateSaleDisplayService().setFlagsDB(dto);

		ExtendMstItemDTO kindDTO = null;

		//システムIDがある場合は商品コードはTBに格納されているため、登録はしない
		// 在庫情報からKind原価を取得する
		if (dto.getSysItemId() != 0) {
			dto.setItemCode(StringUtils.EMPTY);

			// Kind原価を取得
			ItemDAO itemDAO = new ItemDAO();
			kindDTO = itemDAO.getKindCostDTO(dto.getSysItemId());
		}

		//Kind原価情報が存在しない場合
		if(kindDTO == null){
			// 在庫にない商品はそのまま登録する。
			if (saleDto != null) {
				//国内商品として登録されているか検索
				orderItemDTO = dao.getDomesticOrderItemSearchCsv(dto.getItemCode(), saleDto.getOrderNo());
				//国内商品の場合国内商品として業販商品テーブルに登録
				if (orderItemDTO != null) {
					MstCorporationDTO corpDto = new MstCorporationDTO();
					CorporationDAO corpDao = new CorporationDAO();
					corpDto = corpDao.getCorporation(saleDto.getSysCorporationId());
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
					new CorporateSaleDAO().registryCorporateSaleDomesticItem(dto);

				//海外商品の場合海外商品として業販商品テーブルに登録
				} else {
					new CorporateSaleDAO().registryCorporateSaleItem(dto);
				}

			} else {
				new CorporateSaleDAO().registryCorporateSaleItem(dto);
			}

		//Kind原価情報が存在する場合はKind原価情報を設定し登録を行う
		}else{
			// Kind原価を設定
			dto.setKindCost((int)kindDTO.getCost());

			new CorporateSaleDAO().registryCorporateSaleItemKind(dto);

		}


	}

	/**
	 * 業販商品更新処理サービス
	 * @param dto
	 * @throws DaoException
	 */
	public void updateCorporateSalesItem(ExtendCorporateSalesItemDTO dto) throws DaoException {

		//DBに格納する用フラグ読み替え(商品)
		new CorporateSaleDisplayService().setFlagsDB(dto);

		new CorporateSaleDAO().updateCorporateSalesItem(dto);
	}
}
