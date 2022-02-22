package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstSmallGroupDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.SmallGroupDAO;

public class SmallGroupService {

	public void registrySmallGroup(MstSmallGroupDTO smallGroupDTO) throws DaoException {

		smallGroupDTO.setSysSmallGroupId( new SequenceDAO().getMaxSysSmallGroupId() + 1);
		SmallGroupDAO dao = new SmallGroupDAO();
		dao.registrySmallGroup(smallGroupDTO);
	}

	public List<MstSmallGroupDTO> getSmallGroupList() throws DaoException {

		SmallGroupDAO dao = new SmallGroupDAO();

		return dao.getSmallGroupList();
	}

	public MstSmallGroupDTO getSmallGroup(long sysVariousGroupId) throws DaoException {

		SmallGroupDAO dao = new SmallGroupDAO();

		return dao.getSmallGroup(sysVariousGroupId);
	}

	public void updateSmallGroup(MstSmallGroupDTO smallGroupDTO) throws DaoException {

		SmallGroupDAO smallGroupDAO = new SmallGroupDAO();

		smallGroupDAO.updateSmallGroup(smallGroupDTO);

	}

	public void deleteSmallGroup(long sysVariousGroupId) throws DaoException {

		SmallGroupDAO smallGroupDAO = new SmallGroupDAO();

		smallGroupDAO.deleteSmallGroup(sysVariousGroupId);

	}


}
