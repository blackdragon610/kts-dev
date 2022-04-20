package jp.co.kts.service.mst;

import java.util.ArrayList;
import java.util.List;

import jp.co.keyaki.cleave.fw.core.util.CipherUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.common.entity.MstMasterDTO;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.common.entity.MstUserExtraRulesDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstUserDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.RulesDAO;
import jp.co.kts.dao.mst.UserDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.login.LoginService;

public class UserService {

	public List<MstUserDTO> getUserList() throws Exception {

		UserDAO dao = new UserDAO();
		
		List<MstUserDTO> dto = dao.getUserList();
		RulesDAO ruleDao = new RulesDAO();
		
		
		for (MstUserDTO uDto : dto) 
		{
			
			List<MstRulesDTO> ruleDto = ruleDao.getRulesByUserId(uDto.getSysUserId());
			uDto.setMstRulesList(ruleDto);
			for (MstRulesDTO rDto : ruleDto) 
			{
				List<MstRulesListDTO> ruleDetailList = ruleDao.getRuleDetailInfoByUserId(rDto.getRuleId(), uDto.getSysUserId());
				rDto.setMstRulesDetailList(ruleDetailList);
				rDto.setChildCount(rDto.getChildCount());
				int childViewCount = 0;
				if(ruleDetailList.size() > 0) {
					for (MstRulesListDTO ruleDetailDto : ruleDetailList) 
					{
						if(ruleDetailDto.getIsvisible().equals("1"))  childViewCount++;
							
					}
				}
				rDto.setViewableChildCount(childViewCount);
				rDto.setIsAllcheck(childViewCount == rDto.getChildCount() ? 1 : 0);
			}
			List<MstMasterDTO> masterList = dao.getMasterList(uDto.getSysUserId());
			uDto.setMstMasterList(this.setMasterList(masterList));
		}
		
		return dto;
//		return dao.getUserList();
	}
	
	public List<MstMasterDTO> getMasterList(long userId) throws Exception{
		
		UserDAO dao = new UserDAO();
		
		return this.setMasterList(dao.getMasterList(userId));
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
 		List<MstMasterDTO> masterList = dao.getMasterList(dto.getSysUserId());
		dto.setMstMasterList(this.setMasterList(masterList));

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
	
	public void updateUserMainRule(MstUserDTO dto) throws Exception {
		UserDAO dao = new UserDAO();
		
		dao.updateUserMainRule(dto);
	}
	
	public void updateMasterByUser(MstMasterDTO dto) throws Exception {
		UserDAO dao = new UserDAO();
		
		List<MstMasterDTO> mdto = dao.getMasterList(dto.getSysUserId());
		
		if(mdto.size() < 1)
			dao.insetMasterByUser(dto);
		else
			dao.updateMasterByUser(dto);
	}
	
	public List<MstMasterDTO> setMasterList(List<MstMasterDTO> masterList) throws Exception {
		UserDAO dao = new UserDAO();
		
		
		int viewableCount = 0;
		
		List<MstMasterDTO> mdto = new ArrayList<MstMasterDTO>();
		MstMasterDTO masterDto = new MstMasterDTO();
		masterDto.setUserListName(StrutsBaseConst.MASTER_USER_LIST_NAME);
		masterDto.setRuleListName(StrutsBaseConst.MASTER_RULE_LIST_NAME);
		masterDto.setCorporationListName(StrutsBaseConst.MASTER_CORPORATION_LIST_NAME);
		masterDto.setAccountListName(StrutsBaseConst.MASTER_ACCOUNT_LIST_NAME);
		masterDto.setChannelListName(StrutsBaseConst.MASTER_CHANNEL_LIST_NAME);
		masterDto.setWarehouseListName(StrutsBaseConst.MASTER_WAREHOUSE_LIST_NAME);
		masterDto.setMakerListName(StrutsBaseConst.MASTER_MAKER_LIST_NAME);
		masterDto.setSetItemListName(StrutsBaseConst.MASTER_SET_ITEM_LIST_NAME);
		masterDto.setClientListName(StrutsBaseConst.MASTER_CLIENT_LIST_NAME);
		masterDto.setDeliveryListName(StrutsBaseConst.MASTER_DELIVERY_LIST_NAME);
		
		for (MstMasterDTO items : masterList) 
		{
			masterDto.setUserListFlg(items.getUserListFlg());
			masterDto.setRuleListFlg(items.getRuleListFlg());
			masterDto.setCorporationListFlg(items.getCorporationListFlg());
			masterDto.setAccountListFlg(items.getAccountListFlg());
			masterDto.setChannelListFlg(items.getChannelListFlg());
			masterDto.setWarehouseListFlg(items.getWarehouseListFlg());
			masterDto.setMakerListFlg(items.getMakerListFlg());
			masterDto.setSetItemListFlg(items.getSetItemListFlg());
			masterDto.setClientListFlg(items.getClientListFlg());
			masterDto.setDeliveryListFlg(items.getDeliveryListFlg());
			
			if(items.getUserListFlg().equals("1")) viewableCount++;
			if(items.getRuleListFlg().equals("1")) viewableCount++;
			if(items.getCorporationListFlg().equals("1")) viewableCount++;
			if(items.getAccountListFlg().equals("1")) viewableCount++;
			if(items.getChannelListFlg().equals("1")) viewableCount++;
			if(items.getWarehouseListFlg().equals("1")) viewableCount++;
			if(items.getMakerListFlg().equals("1")) viewableCount++;
			if(items.getSetItemListFlg().equals("1")) viewableCount++;
			if(items.getClientListFlg().equals("1")) viewableCount++;
			if(items.getDeliveryListFlg().equals("1")) viewableCount++;
			
		}
		
		masterDto.setViewableCount(viewableCount);
		masterDto.setIsvisible(masterDto.getViewableCount() > 0 ? "1" : "0");
		mdto.add(masterDto);
		return mdto;
		
	}
}
