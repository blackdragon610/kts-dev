//package jp.co.kts.service.mst;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import jp.co.keyaki.cleave.fw.dao.DaoException;
//import jp.co.kts.app.common.entity.MstLargeGroupDTO;
//import jp.co.kts.app.common.entity.MstMiddleGroupDTO;
//import jp.co.kts.app.common.entity.MstSmallGroupDTO;
//import jp.co.kts.app.extendCommon.entity.VariousGroupDTO;
//import jp.co.kts.dao.common.SequenceDAO;
//import jp.co.kts.dao.mst.LargeGroupDAO;
//import jp.co.kts.dao.mst.MiddleGroupDAO;
//import jp.co.kts.dao.mst.SmallGroupDAO;
//import jp.co.kts.ui.web.struts.WebConst;
//
//public class VariousGroupService {
//
//
//	public void registryVariousGroup(VariousGroupDTO variousGroupDTO, int groupSelect) throws DaoException {
//
//		if (WebConst.GROUP_CODE_LARGE == groupSelect) {
//
//			//画面上の判定条件になっているためセット
//			variousGroupDTO.setSysVariousGroupId( new SequenceDAO().getMaxSysLargeGroupId() + 1);
//
//			MstLargeGroupDTO largeGroupDTO = new MstLargeGroupDTO();
//
//			largeGroupDTO.setSysLargeGroupId( new SequenceDAO().getMaxSysLargeGroupId() + 1);
//			largeGroupDTO.setLargeGroupNo(variousGroupDTO.getVariousGroupNo());
//			largeGroupDTO.setLargeGroupNm(variousGroupDTO.getVariousGroupNm());
//
//			LargeGroupDAO dao = new LargeGroupDAO();
//			dao.registryLargeGroup(largeGroupDTO);
//
//		} else if (WebConst.GROUP_CODE_MIDDLE == groupSelect) {
//
//			//画面上の判定条件になっているためセット
//			variousGroupDTO.setSysVariousGroupId( new SequenceDAO().getMaxSysMiddleGroupId() + 1);
//
//			MstMiddleGroupDTO middleGroupDTO = new MstMiddleGroupDTO();
//
//			middleGroupDTO.setSysMiddleGroupId( new SequenceDAO().getMaxSysMiddleGroupId() + 1);
//			middleGroupDTO.setMiddleGroupNo(variousGroupDTO.getVariousGroupNo());
//			middleGroupDTO.setMiddleGroupNm(variousGroupDTO.getVariousGroupNm());
//
//			MiddleGroupDAO dao = new MiddleGroupDAO();
//			dao.registryMiddleGroup(middleGroupDTO);
//
//		} else if (WebConst.GROUP_CODE_SMALL == groupSelect) {
//
//			//画面上の判定条件になっているためセット
//			variousGroupDTO.setSysVariousGroupId( new SequenceDAO().getMaxSysSmallGroupId() + 1);
//
//			MstSmallGroupDTO smallGroupDTO = new MstSmallGroupDTO();
//
//			smallGroupDTO.setSysSmallGroupId( new SequenceDAO().getMaxSysSmallGroupId() + 1);
//			smallGroupDTO.setSmallGroupNo(variousGroupDTO.getVariousGroupNo());
//			smallGroupDTO.setSmallGroupNm(variousGroupDTO.getVariousGroupNm());
//
//			SmallGroupDAO dao = new SmallGroupDAO();
//			dao.registrySmallGroup(smallGroupDTO);
//
//		}
//	}
//
//	public void deleteVariousGroup(long sysVariousGroupId, int groupSelect) throws DaoException {
//
//
//		if (WebConst.GROUP_CODE_LARGE == groupSelect) {
//
//			LargeGroupDAO largeGroupDAO = new LargeGroupDAO();
//
//			largeGroupDAO.deleteLargeGroup(sysVariousGroupId);
//
//		} else if (WebConst.GROUP_CODE_MIDDLE == groupSelect) {
//
//			MiddleGroupDAO middleGroupDAO = new MiddleGroupDAO();
//
//			middleGroupDAO.deleteMiddleGroup(sysVariousGroupId);
//
//		} else if (WebConst.GROUP_CODE_SMALL == groupSelect) {
//
//			SmallGroupDAO smallGroupDAO = new SmallGroupDAO();
//
//			smallGroupDAO.deleteSmallGroup(sysVariousGroupId);
//
//		}
//
//	}
//
//	public void updateVariousGroup(VariousGroupDTO variousGroupDTO, int groupSelect) throws DaoException {
//
//		if (WebConst.GROUP_CODE_LARGE == groupSelect) {
//
//			LargeGroupDAO largeGroupDAO = new LargeGroupDAO();
//
//			largeGroupDAO.updateLargeGroup(variousGroupDTO);
//
//		} else if (WebConst.GROUP_CODE_MIDDLE == groupSelect) {
//
//			MiddleGroupDAO middleGroupDAO = new MiddleGroupDAO();
//
//			middleGroupDAO.updateMiddleGroup(variousGroupDTO);
//
//		} else if (WebConst.GROUP_CODE_SMALL == groupSelect) {
//
//			SmallGroupDAO smallGroupDAO = new SmallGroupDAO();
//
//			smallGroupDAO.updateSmallGroup(variousGroupDTO);
//
//		}
//
//	}
//
//	public VariousGroupDTO getVariousGroup(long sysVariousGroupId, int groupSelect) throws DaoException {
//
//		if (WebConst.GROUP_CODE_LARGE == groupSelect) {
//
//			LargeGroupDAO largeGroupDAO = new LargeGroupDAO();
//
//			return largeGroupDAO.getLargeGroup(sysVariousGroupId);
//
//		} else if (WebConst.GROUP_CODE_MIDDLE == groupSelect) {
//
//			MiddleGroupDAO middleGroupDAO = new MiddleGroupDAO();
//
//			return middleGroupDAO.getMiddleGroup(sysVariousGroupId);
//
//		} else if (WebConst.GROUP_CODE_SMALL == groupSelect) {
//
//			SmallGroupDAO smallGroupDAO = new SmallGroupDAO();
//
//			return smallGroupDAO.getSmallGroup(sysVariousGroupId);
//
//		} else {
//
//			return (new VariousGroupDTO());
//		}
//
//
//	}
//
//	public List<VariousGroupDTO> getVariousGroupList(int groupSelect) throws DaoException {
//
//		if (WebConst.GROUP_CODE_LARGE == groupSelect) {
//
//			LargeGroupService service = new LargeGroupService();
//
//			return service.getLargeGroupList();
//
//		} else if (WebConst.GROUP_CODE_MIDDLE == groupSelect) {
//
//			MiddleGroupService service = new MiddleGroupService();
//
//			return service.getMiddleGroupList();
//
//		} else if (WebConst.GROUP_CODE_SMALL == groupSelect) {
//
//			SmallGroupService service = new SmallGroupService();
//
//			return service.getSmallGroupList();
//
//		} else {
//			return (new ArrayList<>());
//		}
//
//	}
//}
