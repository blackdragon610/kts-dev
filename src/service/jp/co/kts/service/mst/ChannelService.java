package jp.co.kts.service.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.kts.app.common.entity.MstChannelDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.ChannelDAO;

public class ChannelService {

	public List<MstChannelDTO> getChannelList() throws Exception {

		ChannelDAO dao = new ChannelDAO();

		return dao.getChannelList();
	}

	public MstChannelDTO getChannel(long sysChannelId) throws Exception {

		ChannelDAO dao = new ChannelDAO();
		return  dao.getChannel(sysChannelId);
	}

	public String getChannelNm(long sysChannelId) throws Exception {

		ChannelDAO dao = new ChannelDAO();
		MstChannelDTO dto = dao.getChannel(sysChannelId);

		if (dto == null) {

			return StringUtils.EMPTY;
		}

		return dto.getChannelNm();
	}

	public void updateChannel(MstChannelDTO channelDTO) throws Exception {

		ChannelDAO dao = new ChannelDAO();
		dao.updateChannel(channelDTO);
	}

	public void deleteChannel(long sysChannelId) throws Exception {

		ChannelDAO dao = new ChannelDAO();
		dao.deleteChannel(sysChannelId);

	}

	public void registryChannel(MstChannelDTO channelDTO) throws Exception {
		ChannelDAO dao = new ChannelDAO();
		channelDTO.setSysChannelId(new SequenceDAO().getMaxSysChannelId() + 1);

		dao.registryChannel(channelDTO);
	}

}