package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstUserDTO;

public class UserForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysUserId;

	private List<MstUserDTO> userList = new ArrayList<>();

	private MstUserDTO userDTO = new MstUserDTO();

	private String alertType;


	/**
	 * @return sysUserId
	 */
	public long getSysUserId() {
		return sysUserId;
	}



	/**
	 * @param sysUserId セットする sysUserId
	 */
	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}



	/**
	 * @return userList
	 */
	public List<MstUserDTO> getUserList() {
		return userList;
	}



	/**
	 * @param userList セットする userList
	 */
	public void setUserList(List<MstUserDTO> userList) {
		this.userList = userList;
	}



	/**
	 * @return userDTO
	 */
	public MstUserDTO getUserDTO() {
		return userDTO;
	}



	/**
	 * @param userDTO セットする userDTO
	 */
	public void setUserDTO(MstUserDTO userDTO) {
		this.userDTO = userDTO;
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
