package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstChannelDTO;

public class ChannelForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysChannelId;

	private List<MstChannelDTO> channelList = new ArrayList<>();

	private MstChannelDTO channelDTO = new MstChannelDTO();

	private String alertType;

	/**
	 * @return sysChannelId
	 */
	public long getSysChannelId() {
		return sysChannelId;
	}







	/**
	 * @param sysChannelId セットする sysChannelId
	 */
	public void setSysChannelId(long sysChannelId) {
		this.sysChannelId = sysChannelId;
	}







	/**
	 * @return channelList
	 */
	public List<MstChannelDTO> getChannelList() {
		return channelList;
	}







	/**
	 * @param channelList セットする channelList
	 */
	public void setChannelList(List<MstChannelDTO> channelList) {
		this.channelList = channelList;
	}







	/**
	 * @return channelDTO
	 */
	public MstChannelDTO getChannelDTO() {
		return channelDTO;
	}







	/**
	 * @param channelDTO セットする channelDTO
	 */
	public void setChannelDTO(MstChannelDTO channelDTO) {
		this.channelDTO = channelDTO;
	}







	/**
	 * @return alertType
	 */
	public String getAlertType() {
		return alertType;
	}







	/**
	 * @param alertType セットする alertType
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}







	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		this.alertType = "0";
	}

}
