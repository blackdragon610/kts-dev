package jp.co.kts.service.sale;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CorporateReceiveDTO;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.ClientDAO;
import jp.co.kts.dao.sale.CorporateReceiveDAO;

/**
 * 業販機能で使用するクラスです
 *
 * @author aito
 *
 */
public class CorporateReceiveService {

	/**
	 * 入金情報登録
	 *
	 * @param price
	 * @param date
	 * @param slipId
	 * @param sysClientId
	 * @throws DaoException
	 */
	public void registryCorporateReceive(int price, String date, long slipId, long sysClientId) throws DaoException {

		CorporateReceiveDTO dto = new CorporateReceiveDTO();

		dto.setReceivePrice(price);
		dto.setReceiveDate(date);
		dto.setSysCorporateSalesSlipId(slipId);
		dto.setSysCorporateReceiveId(new SequenceDAO().getMaxSysCorporateReceiveId() + 1);

		ClientDAO clientDao = new ClientDAO();
		//請求先名
		String billingDstNm = "";
		// 得意先情報を取得
		MstClientDTO clientDTO = clientDao.getClient(sysClientId);

		/*
		 * 入金処理中に、支店の入金情報を本店に統合する処理
		 * 引数の得意先が支店がある場合、入金情報をINSERTするdtoの得意先IDを本店のIDで上書きする。
		 * 請求書で支店の入金データを本店へ統合している以下の処理と同様  CorporateSaleDisplayService のgetExportCorporateBill ()メソッド
		 */
		dto.setSysClientId(sysClientId);

		// 支店の場合は、以下のカラムに値がある（nullもしくは空文字ではない）
		// mst_client テーブルのbilling_dst(請求先) カラム
		if (!StringUtils.isEmpty(clientDTO.getBillingDst())) {

			billingDstNm = clientDTO.getBillingDst();
			if(!StringUtils.isEmpty(billingDstNm)) {
				Long billingDstNo = Long.parseLong(clientDao.getClientNmWithName(billingDstNm, clientDTO.getSysCorporationId()));
				dto.setSysClientId(billingDstNo);
			}

		}
		new CorporateReceiveDAO().registryCorporateReceive(dto);

	}

	// 伝票or得意先に紐付く入金取得
	public List<CorporateReceiveDTO> getCorporateReceiveList(long sysCorporateSalesSlipId, long sysClientId,
			String receiveMonth) throws DaoException, ParseException {

		return new CorporateReceiveDAO().getCorporateReceiveList(sysCorporateSalesSlipId, sysClientId, receiveMonth);
	}

	// 入金の総額を計算
	public int getCorporateReceiveTotal(List<CorporateReceiveDTO> corporateReceiveList) throws DaoException {

		if (corporateReceiveList.size() == 0) {
			return 0;
		} else if (corporateReceiveList.size() == 1) {
			return corporateReceiveList.get(0).getReceivePrice();
		} else {
			int total = 0;
			for (CorporateReceiveDTO corporateReceive : corporateReceiveList) {
				total += corporateReceive.getReceivePrice();
			}
			return total;
		}
	}

	// 伝票or得意先に紐付く入金の総額取得
	public int getCorporateReceiveTotal(long sysCorporateSalesSlipId, long sysClientId, String receiveMonth)
			throws DaoException, ParseException {
		List<CorporateReceiveDTO> receiveList = new CorporateReceiveDAO()
				.getCorporateReceiveList(sysCorporateSalesSlipId, sysClientId, receiveMonth);

		return getCorporateReceiveTotal(receiveList);
	}

	// 最新の入金日
	public String getCorporateReceiveLatestDate(long sysCorporateSalesSlipId) throws DaoException, ParseException {

		List<CorporateReceiveDTO> corporateReceiveList = getCorporateReceiveList(sysCorporateSalesSlipId, 0, "");

		if (corporateReceiveList.size() >= 1) {
			return corporateReceiveList.get(0).getReceiveDate();
		} else {
			return "";
		}
	}

	// 入金情報削除
	public void deleteCorporateReceive(long sysCorporateReceiveId) throws DaoException {

		new CorporateReceiveDAO().deleteCorporateReceive(sysCorporateReceiveId);
	}

}
