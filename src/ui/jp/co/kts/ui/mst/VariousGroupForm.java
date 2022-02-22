package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstLargeGroupDTO;
import jp.co.kts.app.common.entity.MstMiddleGroupDTO;
import jp.co.kts.app.common.entity.MstSmallGroupDTO;
import jp.co.kts.app.extendCommon.entity.VariousGroupDTO;

public class VariousGroupForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysVariousGroupId;

	private String alertType;

	private List<VariousGroupDTO> variousGroupList = new ArrayList<>();

	private List<MstLargeGroupDTO> largeGroupList = new ArrayList<>();

	private List<MstMiddleGroupDTO> middleGroupList = new ArrayList<>();

	private List<MstSmallGroupDTO> smallGroupList = new ArrayList<>();

	private MstLargeGroupDTO largeGroupDTO = new MstLargeGroupDTO();

	private MstMiddleGroupDTO middleGroupDTO = new MstMiddleGroupDTO();

	private MstSmallGroupDTO smallGroupDTO = new MstSmallGroupDTO();

	private VariousGroupDTO variousGroupDTO = new VariousGroupDTO();;

	private int groupSelect;

	private String groupSelectName;









	public long getSysVariousGroupId() {
		return sysVariousGroupId;
	}







	public void setSysVariousGroupId(long sysVariousGroupId) {
		this.sysVariousGroupId = sysVariousGroupId;
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








	public List<VariousGroupDTO> getVariousGroupList() {
		return variousGroupList;
	}







	public void setVariousGroupList(List<VariousGroupDTO> variousGroupList) {
		this.variousGroupList = variousGroupList;
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







	public MstLargeGroupDTO getLargeGroupDTO() {
		return largeGroupDTO;
	}







	public void setLargeGroupDTO(MstLargeGroupDTO largeGroupDTO) {
		this.largeGroupDTO = largeGroupDTO;
	}







	public MstMiddleGroupDTO getMiddleGroupDTO() {
		return middleGroupDTO;
	}







	public void setMiddleGroupDTO(MstMiddleGroupDTO middleGroupDTO) {
		this.middleGroupDTO = middleGroupDTO;
	}







	public MstSmallGroupDTO getSmallGroupDTO() {
		return smallGroupDTO;
	}







	public void setSmallGroupDTO(MstSmallGroupDTO smallGroupDTO) {
		this.smallGroupDTO = smallGroupDTO;
	}







	public VariousGroupDTO getVariousGroupDTO() {
		return variousGroupDTO;
	}







	public void setVariousGroupDTO(VariousGroupDTO variousGroupDTO) {
		this.variousGroupDTO = variousGroupDTO;
	}







	public static long getSerialversionuid() {
		return serialVersionUID;
	}







	public int getGroupSelect() {
		return groupSelect;
	}







	public void setGroupSelect(int groupSelect) {
		this.groupSelect = groupSelect;
	}







	public String getGroupSelectName() {
		return groupSelectName;
	}







	public void setGroupSelectName(String groupSelectName) {
		this.groupSelectName = groupSelectName;
	}







	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		this.alertType = "0";
	}




}
