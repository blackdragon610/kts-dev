package jp.co.kts.dao.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstChannelDTO;

public class ChannelDAO extends BaseDAO {

	public List<MstChannelDTO> getChannelList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_CHANNEL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstChannelDTO.class));
	}

	public MstChannelDTO getChannel(long sysChannelId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysChannelId", sysChannelId);

		return select("SEL_CHANNEL", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstChannelDTO.class));
	}

	public void updateChannel(MstChannelDTO channelDTO) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(channelDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_CHANNEL", parameters);
	}

	public void deleteChannel(long sysChannelId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysChannelId", sysChannelId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_CHANNEL", parameters);
	}

	public void registryChannel(MstChannelDTO channelDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(channelDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_CHANNEL", parameters);

	}

	public long getChannelId(String orderRoute) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		if (StringUtils.isNotEmpty(orderRoute)) {

			parameters.addParameter("channelNm", orderRoute);

		} else {
			return 0;
		}

		return select("SEL_CHANNEL_ID", parameters, ResultSetHandlerFactory.getFirstColumnLongRowHandler());
	}
}
