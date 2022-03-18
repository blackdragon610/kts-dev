package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.util.CipherUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.DomesticOrderListDTO;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.DomesticSlipDAO;
import jp.co.kts.dao.mst.MakerDAO;
import jp.co.kts.dao.mst.RulesDAO;
import jp.co.kts.dao.mst.UserDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceValidator;
import jp.co.kts.service.login.LoginService;

public class RulesService {

	private static final String RULE_CHECK_FLG_ON = "on";

	/**
	 * 全ユーザを取得します(削除フラグは無視)
	 */
	public List<MstRulesDTO> getRulesList() throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		RulesDAO dao = new RulesDAO();

		return dao.getRulesList();
	}

	public int ruleItemDelete(List<MstRulesDTO> dto) throws DaoException {
		int resultCnt = 0;
		RulesDAO dao = new RulesDAO();
		for (MstRulesDTO ruleDto : dto) {
			if (!ruleDto.getItemCheckFlg().equals(RULE_CHECK_FLG_ON)) {
				continue;
			}
			resultCnt += dao.deleteRuleItem(ruleDto);
		}

		return resultCnt;
	}
	
	public int registryRule(MstRulesDTO dto)throws Exception {
		int result = 0;
		RulesDAO dao = new RulesDAO();
		dto.setRuleId(new SequenceDAO().getMaxRuleId() + 1);

		result = dao.registoryRule(dto);
		return result;
	}
	
	public int updateRule(MstRulesDTO dto, long id)throws Exception {
		int result = 0;
		RulesDAO dao = new RulesDAO();
		result = dao.updateRule(dto, id);
		return result;
	}
	public Result<MstRulesDTO> validate(String ruleName) {

		Result<MstRulesDTO> result = new Result<MstRulesDTO>();

		//入力チェック(必須・文字数)
		ServiceValidator.inputChecker(result, ruleName, "名", 30, true);

		return result;
	}

//	public MstUserDTO getUser(long sysUserId) throws Exception {
//
//		UserDAO dao = new UserDAO();
//		MstUserDTO dto = new MstUserDTO();
//		 dto = dao.getUser(sysUserId);
//		 dto.setPassword(CipherUtil.decodeString(dto.getPassword()));
//		 return dto;
//	}
//
//	public void updateUser(MstUserDTO dto) throws Exception {
//		UserDAO dao = new UserDAO();
//		dto.setPassword(CipherUtil.encodeString(dto.getPassword()));
//		dao.updateUser(dto);
//		dto.setPassword(CipherUtil.decodeString(dto.getPassword()));
//	}
//
//	public void deleteUser(long sysUserId) throws Exception {
//		UserDAO dao = new UserDAO();
//		dao.deleteUser(sysUserId);
//	}
//
//	public void registryUser(MstUserDTO dto) throws Exception {
//		UserDAO dao = new UserDAO();
//		dto.setSysUserId(new SequenceDAO().getMaxSysUserId() + 1);
//		dto.setPassword(CipherUtil.encodeString(dto.getPassword()));
//		dao.registryUser(dto);
//		dto.setPassword(CipherUtil.decodeString(dto.getPassword()));
//	}
//
//	public ExtendMstUserDTO getUserName(long sysUserId) throws Exception {
//		UserDAO dao = new UserDAO();
//		ExtendMstUserDTO dto = new ExtendMstUserDTO();
//		 dto = dao.getUserName(sysUserId);
//
//		 return dto;
//	}
//
//	public Result<MstUserDTO> validate(String loginCd, String password, long sysUserId) throws Exception {
//
//		LoginService service = new LoginService();
//		Result<MstUserDTO> result = service.validate(loginCd,password);
//
//		if (result.isSuccess()) {
//			UserDAO dao = new UserDAO();
//			long countSameLoginCd = 0;
//			countSameLoginCd = dao.cheackSameLoginCd(loginCd, sysUserId);
//			if (countSameLoginCd > 0) {
//				result.addErrorMessage("LED00104");
//			}
//		}
//		return result;
//	}
}
