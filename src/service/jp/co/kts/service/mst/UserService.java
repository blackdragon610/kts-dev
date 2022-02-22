package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.util.CipherUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.UserDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.login.LoginService;

public class UserService {

	public List<MstUserDTO> getUserList() throws Exception {

		UserDAO dao = new UserDAO();

		return dao.getUserList();
	}

	/**
	 * 全ユーザを取得します(削除フラグは無視)
	 */
	public List<MstUserDTO> getUserListAll() throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		UserDAO dao = new UserDAO();

		return dao.getUserListAll();
	}

	public MstUserDTO getUser(long sysUserId) throws Exception {

		UserDAO dao = new UserDAO();
		MstUserDTO dto = new MstUserDTO();
		 dto = dao.getUser(sysUserId);
		 dto.setPassword(CipherUtil.decodeString(dto.getPassword()));
		 return dto;
	}

	public void updateUser(MstUserDTO dto) throws Exception {
		UserDAO dao = new UserDAO();
		dto.setPassword(CipherUtil.encodeString(dto.getPassword()));
		dao.updateUser(dto);
		dto.setPassword(CipherUtil.decodeString(dto.getPassword()));
	}

	public void deleteUser(long sysUserId) throws Exception {
		UserDAO dao = new UserDAO();
		dao.deleteUser(sysUserId);
	}

	public void registryUser(MstUserDTO dto) throws Exception {
		UserDAO dao = new UserDAO();
		dto.setSysUserId(new SequenceDAO().getMaxSysUserId() + 1);
		dto.setPassword(CipherUtil.encodeString(dto.getPassword()));
		dao.registryUser(dto);
		dto.setPassword(CipherUtil.decodeString(dto.getPassword()));
	}

	public ExtendMstUserDTO getUserName(long sysUserId) throws Exception {
		UserDAO dao = new UserDAO();
		ExtendMstUserDTO dto = new ExtendMstUserDTO();
		 dto = dao.getUserName(sysUserId);

		 return dto;
	}

	public Result<MstUserDTO> validate(String loginCd, String password, long sysUserId) throws Exception {

		LoginService service = new LoginService();
		Result<MstUserDTO> result = service.validate(loginCd,password);

		if (result.isSuccess()) {
			UserDAO dao = new UserDAO();
			long countSameLoginCd = 0;
			countSameLoginCd = dao.cheackSameLoginCd(loginCd, sysUserId);
			if (countSameLoginCd > 0) {
				result.addErrorMessage("LED00104");
			}
		}
		return result;
	}
}
