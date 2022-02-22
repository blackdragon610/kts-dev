package jp.co.kts.dao.sale;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.CorporateReceiveDTO;

public class CorporateReceiveDAO extends BaseDAO {

	//入金情報新規登録
	public int registryCorporateReceive(CorporateReceiveDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("INS_CORP_RECEIVE", parameters);
	}

	//伝票に紐付く入金取得
	public List<CorporateReceiveDTO> getCorporateReceiveList(long sysCorporateSalesSlipId
			, long sysClientId, String receiveMonth)
			throws DaoException, ParseException {

		SQLParameters parameters = new SQLParameters();
		if (sysCorporateSalesSlipId != 0) {
			parameters.addParameter("sysCorporateSalesSlipId", sysCorporateSalesSlipId);
		}
		if (sysClientId != 0) {
			parameters.addParameter("sysClientId", sysClientId);
		}
		if (StringUtils.isNotEmpty(receiveMonth)) {
			//入力された月のFrom Toを作成
			String targetDateStr = receiveMonth + "/01";
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
			Date targetDate = format.parse(targetDateStr);

			//入力された月のfrom
			String receiveDateFrom = format.format(targetDate);

			//入力された月のto
			Calendar cal = Calendar.getInstance(Locale.JAPAN);
			cal.setTime(targetDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			String receiveDateTo = format.format(cal.getTime());

			parameters.addParameter("receiveDateFrom", receiveDateFrom);
			parameters.addParameter("receiveDateTo", receiveDateTo);
		}

		return selectList("SEL_CORP_RECEIVE", parameters,
				ResultSetHandlerFactory
						.getNameMatchBeanRowHandler(CorporateReceiveDTO.class));
	}

//	//伝票に紐付く入金の総額
//	public int getCoroprateReceiveTotal(long sysCorporateSalesSlipId)
//			throws DaoException {
//
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("sysCorporateSalesSlipId", sysCorporateSalesSlipId);
//
//		return select("SEL_CORP_RECEIVE_TOTAL", parameters,
//				ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
//	}
//
//	//最新の入金日
//	public String getCorporateReceiveLatestDate(long sysCorporateSalesSlipId) throws DaoException{
//		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("sysCorporateSalesSlipId", sysCorporateSalesSlipId);
//
//		Date latestDate =  select("SEL_CORP_RECEIVE_LATEST_DATE", parameters,
//				ResultSetHandlerFactory.getFirstColumnDateRowHandler());
//		SimpleDateFormat format = new SimpleDateFormat();
//
//		return format.format(latestDate);
//	}


	//入金情報削除
	public void deleteCorporateReceive(long sysCorporateReceiveId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporateReceiveId", sysCorporateReceiveId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_CORP_RECEIVE", parameters);
	}

}
