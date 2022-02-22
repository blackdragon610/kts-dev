package jp.co.kts.ui.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendNoticeBoardDTO;

public class LoginForm extends AppBaseForm{

	/**シリアルID**/
	private static final long serialVersionUID = 1L;

	/**
	 * ログインコード.
	 **/
	private String loginCd;

	/**
	 * パスワード.
	 */
	private String password;

	/**
	 * ログインコード保存フラグ.
	 */
	private boolean saveLoginCdFlg;


	private MstUserDTO userDTO = new MstUserDTO();


	private long sysUserId;

	/**
	 * バージョン
	 */
	private String version;

	/**
	 * 掲示板用DTO
	 * */
	private List<ExtendNoticeBoardDTO> noticeList = new ArrayList<ExtendNoticeBoardDTO>();
	/**
	 * 入力記事内容
	 * */
	private String noticeDetail;

	/**
	 * システム記事ID
	 * */
	private int noticeSystemId;

	/**
	 * @return loginCd
	 */
	public String getLoginCd() {
		return loginCd;
	}

	/**
	 * @param loginCd セットする loginCd
	 */
	public void setLoginCd(String loginCd) {
		this.loginCd = loginCd;
	}

	/**
	 * パスワードを返します.
	 *
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定します.
	 *
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @return saveLoginCdFlg
	 */
	public boolean isSaveLoginCdFlg() {
		return saveLoginCdFlg;
	}

	/**
	 * @param saveLoginCdFlg セットする saveLoginCdFlg
	 */
	public void setSaveLoginCdFlg(boolean saveLoginCdFlg) {
		this.saveLoginCdFlg = saveLoginCdFlg;
	}

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {

		setSaveLoginCdFlg(false);
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
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version セットする version
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * @return noticeList
	 */
	public List<ExtendNoticeBoardDTO> getNoticeList() {
		return noticeList;
	}

	/**
	 * @param noticeList セットする noticeList
	 */
	public void setNoticeList(List<ExtendNoticeBoardDTO> noticeList) {
		this.noticeList = noticeList;
	}

	/**
	 * @return noticeDetail
	 */
	public String getNoticeDetail() {
		return noticeDetail;
	}

	/**
	 * @param noticeDetail セットする noticeDetail
	 */
	public void setNoticeDetail(String noticeDetail) {
		this.noticeDetail = noticeDetail;
	}

	/**
	 * @return noticeSystemId
	 */
	public int getNoticeSystemId() {
		return noticeSystemId;
	}

	/**
	 * @param noticeSystemId セットする noticeSystemId
	 */
	public void setNoticeSystemId(int noticeSystemId) {
		this.noticeSystemId = noticeSystemId;
	}



}
