package jp.co.kts.dao.mst;

import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstStoreDTO;

public class StoreDAO extends BaseDAO {

	public MstStoreDTO getSysStoreId(long sysCorporationId, long sysChannelId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", sysCorporationId);
		parameters.addParameter("sysChannelId", sysChannelId);

		parameters.addParameter("getListFlg", "0");

		return select("SEL_MST_STORE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstStoreDTO.class));
	}


}
