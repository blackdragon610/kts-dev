package jp.co.kts.dao.login;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendNoticeBoardDTO;
import jp.co.kts.dao.common.SequenceDAO;

public class LoginDAO extends BaseDAO{

	public MstUserDTO getContact(String loginCd, String password) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("loginCd", loginCd);
		parameters.addParameter("password", password);

		return select("SEL_LOGIN_USER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstUserDTO.class));
	}

	//掲示板内容取得
	public List<ExtendNoticeBoardDTO> getNoticeBoard() throws Exception{
		return selectList("SEL_NOTICE", ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendNoticeBoardDTO.class));
	}

	//掲示板内容登録
	public void  registryNoticeBoard(String notice) throws Exception{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("notice", notice);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("userId", userInfo.getUserId());
		SequenceDAO seqDao = new SequenceDAO();
		long noticeSysId = seqDao.getMaxNoticeSysId();
		parameters.addParameter("noticeID", noticeSysId + 1);

		update("INS_NOTICE", parameters);
	}

	//掲示板内容更新
	public void  updateNotice(int noticeSystemId, String notice) throws Exception{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("noticeSystemId", noticeSystemId);
		parameters.addParameter("notice", notice);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("UPD_NOTICE", parameters);
	}

	//掲示板内容削除
	public void  deleteNotice(int noticeSystemId) throws Exception{
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("noticeSystemId", noticeSystemId);
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("deleteFlag", "1");
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_NOTICE", parameters);
	}
}
