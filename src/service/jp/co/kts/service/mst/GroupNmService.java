package jp.co.kts.service.mst;

import java.util.List;

import jp.co.kts.app.common.entity.MstGroupNmDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstGroupNmDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.GroupNmDAO;

public class GroupNmService {

	public List<ExtendMstGroupNmDTO> getGroupNmList() throws Exception {

		GroupNmDAO dao = new GroupNmDAO();

		return dao.getGroupNmList();
	}

	public MstGroupNmDTO getGroupNm(long sysGroupNmId) throws Exception {

		GroupNmDAO dao = new GroupNmDAO();
		return  dao.getGroupNm(sysGroupNmId);
	}

	public void updateGroupNm(MstGroupNmDTO groupNmDTO) throws Exception {

		GroupNmDAO dao = new GroupNmDAO();
		dao.updateGroupNm(groupNmDTO);
	}

	public void deleteGroupNm(long sysGroupNmId) throws Exception {

		GroupNmDAO dao = new GroupNmDAO();
		dao.deleteGroupNm(sysGroupNmId);

	}

	public void registryGroupNm(MstGroupNmDTO groupNmDTO) throws Exception {

		GroupNmDAO dao = new GroupNmDAO();
		groupNmDTO.setSysGroupNmId(new SequenceDAO().getMaxSysGroupNmId() + 1);

		dao.registryGroupNm(groupNmDTO);
	}

}