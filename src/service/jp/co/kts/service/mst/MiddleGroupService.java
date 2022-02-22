package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstMiddleGroupDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.MiddleGroupDAO;

public class MiddleGroupService {

	public void registryMiddleGroup(MstMiddleGroupDTO middleGroupDTO) throws DaoException {

		middleGroupDTO.setSysMiddleGroupId( new SequenceDAO().getMaxSysMiddleGroupId() + 1);

		MiddleGroupDAO dao = new MiddleGroupDAO();
		dao.registryMiddleGroup(middleGroupDTO);
	}

	public List<MstMiddleGroupDTO> getMiddleGroupList() throws DaoException {

		MiddleGroupDAO dao = new MiddleGroupDAO();

		return dao.getMiddleGroupList();
	}

	public MstMiddleGroupDTO getMiddleGroup(long sysVariousGroupId) throws DaoException {

		MiddleGroupDAO dao = new MiddleGroupDAO();

		return dao.getMiddleGroup(sysVariousGroupId);
	}

	public void updateMiddleGroup(MstMiddleGroupDTO middleGroupDTO) throws DaoException {

		MiddleGroupDAO middleGroupDAO = new MiddleGroupDAO();

		middleGroupDAO.updateMiddleGroup(middleGroupDTO);

	}

	public void deleteMiddleGroup(long sysVariousGroupId) throws DaoException {

		MiddleGroupDAO middleGroupDAO = new MiddleGroupDAO();

		middleGroupDAO.deleteMiddleGroup(sysVariousGroupId);

	}


}
