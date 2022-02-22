package jp.co.kts.service.mst;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstStoreDTO;
import jp.co.kts.dao.mst.StoreDAO;

public class StoreService {

	public long getSysStoreId(long sysCorporationId, long sysChannelId) throws DaoException {

		MstStoreDTO dto = new MstStoreDTO();
		dto = new StoreDAO().getSysStoreId(sysCorporationId, sysChannelId);

		return dto.getSysStoreId();
	}

}
