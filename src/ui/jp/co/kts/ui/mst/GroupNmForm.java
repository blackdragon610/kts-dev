package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstGroupNmDTO;
import jp.co.kts.app.common.entity.MstLargeGroupDTO;
import jp.co.kts.app.common.entity.MstMiddleGroupDTO;
import jp.co.kts.app.common.entity.MstSmallGroupDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstGroupNmDTO;

public class GroupNmForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysGroupNmId;

	private List<ExtendMstGroupNmDTO> groupNmList = new ArrayList<>();

	private MstGroupNmDTO groupNmDTO = new MstGroupNmDTO();

	private String alertType;

	private List<MstLargeGroupDTO> largeGroupList = new ArrayList<>();

	private List<MstMiddleGroupDTO> middleGroupList = new ArrayList<>();

	private List<MstSmallGroupDTO> smallGroupList = new ArrayList<>();

	/**
	 * @return sysGroupNmId
	 */
	public long getSysGroupNmId() {
		return sysGroupNmId;
	}







	/**
	 * @param sysGroupNmId セットする sysGroupNmId
	 */
	public void setSysGroupNmId(long sysGroupNmId) {
		this.sysGroupNmId = sysGroupNmId;
	}







	/**
	 * @return groupNmList
	 */
	public List<ExtendMstGroupNmDTO> getGroupNmList() {
		return groupNmList;
	}







	/**
	 * @param groupNmList セットする groupNmList
	 */
	public void setGroupNmList(List<ExtendMstGroupNmDTO> groupNmList) {
		this.groupNmList = groupNmList;
	}







	/**
	 * @return groupNmDTO
	 */
	public MstGroupNmDTO getGroupNmDTO() {
		return groupNmDTO;
	}







	/**
	 * @param groupNmDTO セットする groupNmDTO
	 */
	public void setGroupNmDTO(MstGroupNmDTO groupNmDTO) {
		this.groupNmDTO = groupNmDTO;
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







	public List<MstLargeGroupDTO> getLargeGroupList() {
		return largeGroupList;
	}







	public void setLargeGroupList(List<MstLargeGroupDTO> largeGroupList) {
		this.largeGroupList = largeGroupList;
	}







	public List<MstMiddleGroupDTO> getMiddleGroupList() {
		return middleGroupList;
	}







	public void setMiddleGroupList(List<MstMiddleGroupDTO> middleGroupList) {
		this.middleGroupList = middleGroupList;
	}







	public List<MstSmallGroupDTO> getSmallGroupList() {
		return smallGroupList;
	}







	public void setSmallGroupList(List<MstSmallGroupDTO> smallGroupList) {
		this.smallGroupList = smallGroupList;
	}







	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		this.alertType = "0";
	}

}
