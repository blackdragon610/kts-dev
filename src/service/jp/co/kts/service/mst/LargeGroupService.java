package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstLargeGroupDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.LargeGroupDAO;

public class LargeGroupService {

//	public void registryLargeGroup(VariousGroupDTO variousGroupDTO) throws DaoException {
//
//		MstLargeGroupDTO largeGroupDTO = new MstLargeGroupDTO();
//
//		//画面上の判定条件になっているためセット
//		variousGroupDTO.setSysVariousGroupId( new SequenceDAO().getMaxSysLargeGroupId() + 1);
//
//		largeGroupDTO.setSysLargeGroupId( new SequenceDAO().getMaxSysLargeGroupId() + 1);
//		largeGroupDTO.setLargeGroupNo(variousGroupDTO.getVariousGroupNo());
//		largeGroupDTO.setLargeGroupNm(variousGroupDTO.getVariousGroupNm());
//
//		LargeGroupDAO dao = new LargeGroupDAO();
//		dao.registryLargeGroup(largeGroupDTO);
//	}
	public void registryLargeGroup(MstLargeGroupDTO largeGroupDTO) throws DaoException {

		//画面上の判定条件になっているためセット
//		variousGroupDTO.setSysVariousGroupId( new SequenceDAO().getMaxSysLargeGroupId() + 1);

		largeGroupDTO.setSysLargeGroupId( new SequenceDAO().getMaxSysLargeGroupId() + 1);
//		largeGroupDTO.setLargeGroupNo(variousGroupDTO.getVariousGroupNo());
//		largeGroupDTO.setLargeGroupNm(variousGroupDTO.getVariousGroupNm());

		LargeGroupDAO dao = new LargeGroupDAO();
		dao.registryLargeGroup(largeGroupDTO);
	}

//	public List<VariousGroupDTO> getLargeGroupList() throws DaoException {
//
//		LargeGroupDAO dao = new LargeGroupDAO();
//
//		return dao.getLargeGroupList();
//	}

	public List<MstLargeGroupDTO> getLargeGroupList() throws DaoException {

		LargeGroupDAO dao = new LargeGroupDAO();

		return dao.getLargeGroupList();
	}

	public MstLargeGroupDTO getLargeGroup(long sysVariousGroupId) throws DaoException {

		LargeGroupDAO dao = new LargeGroupDAO();

		return dao.getLargeGroup(sysVariousGroupId);
	}

	public void updateLargeGroup(MstLargeGroupDTO largeGroupDTO) throws DaoException {

		LargeGroupDAO largeGroupDAO = new LargeGroupDAO();

		largeGroupDAO.updateLargeGroup(largeGroupDTO);

	}
//	public void updateLargeGroup(VariousGroupDTO variousGroupDTO) throws DaoException {
//
//		LargeGroupDAO largeGroupDAO = new LargeGroupDAO();
//
//		largeGroupDAO.updateLargeGroup(variousGroupDTO);
//
//	}

	public void deleteLargeGroup(long sysVariousGroupId) throws DaoException {

		LargeGroupDAO largeGroupDAO = new LargeGroupDAO();

		largeGroupDAO.deleteLargeGroup(sysVariousGroupId);

	}


}
