package jp.co.kts.service.mst;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;

public class SetItemService {

	public ExtendMstItemDTO setSetItemFlg(ExtendMstItemDTO dto) throws DaoException {

		dto.setSetItemFlg("1");

		return dto;
	}
}
