package jp.co.kts.dao.mst;

import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.kts.app.common.entity.UpdateDataHistoryDTO;

public class UpdateDataHistoryDAO extends BaseDAO {


	/**
	 * 更新情報履歴作成
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int updateHistoryInfo(UpdateDataHistoryDTO dto) throws DaoException{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysUpdateDataId", dto.getSysUpdateDataId());
		parameters.addParameter("sysItemId", dto.getSysItemId());
		parameters.addParameter("updateHistory", dto.getUpdateHistory());
		parameters.addParameter("updateUserId", dto.getUpdateUserId());
		parameters.addParameter("createUserId", dto.getCreateUserId());
		return update("UPD_UPDATE_DATA_HISTORY", parameters);
	}

}
