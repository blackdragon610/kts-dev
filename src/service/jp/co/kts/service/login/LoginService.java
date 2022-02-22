package jp.co.kts.service.login;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.keyaki.cleave.fw.core.util.CipherUtil;
import jp.co.kts.app.common.entity.MstUserDTO;
import jp.co.kts.app.extendCommon.entity.ExtendNoticeBoardDTO;
import jp.co.kts.dao.login.LoginDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceValidator;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.ui.login.LoginForm;



public class LoginService {

	public MstUserDTO getContact(String loginCd, String password) throws Exception{
		LoginDAO dao = new LoginDAO();
		return dao.getContact(loginCd, CipherUtil.encodeString(password));
	}

	/**
	 * @param loginDTO
	 * @return
	 */
	public Result<MstUserDTO> validate(String loginCd, String password) {

		Result<MstUserDTO> result = new Result<MstUserDTO>();

		//入力チェック(必須・文字数)
		ServiceValidator.inputChecker(result, loginCd, "ユーザーID", 8, true);
		ServiceValidator.inputChecker(result, password, "パスワード", 8, true);

		return result;
	}

	public List<ExtendNoticeBoardDTO> getNoticeBoard() throws Exception{

		LoginDAO dao = new LoginDAO();
		List<ExtendNoticeBoardDTO> list = new ArrayList<ExtendNoticeBoardDTO>();
		list = dao.getNoticeBoard();
		UserService userService = new UserService();
		List<MstUserDTO> userList = userService.getUserListAll();
		// <ユーザid, ユーザMpa<getter名, 値>>
		Map<String, Map<String, Object>> userMap = new LinkedHashMap<String, Map<String, Object>>();

		for (MstUserDTO dto: userList) {
			long key = dto.getSysUserId();
			if (userMap.get(key) != null) {
				continue;
			}
			BeanInfo info = Introspector.getBeanInfo(dto.getClass());
			Map<String, Object> beanMap = new LinkedHashMap<String, Object>();
			for (PropertyDescriptor property : info.getPropertyDescriptors()) {

				Method getter = property.getReadMethod();
				if (getter == null) {
					throw new NullPointerException();
				}
				Object value = getter.invoke(dto);
				String name = property.getName();
				beanMap.put(name, value);
			}

			userMap.put(String.valueOf(key), beanMap);
		}

		for(ExtendNoticeBoardDTO dto: list){
//			ExtendMstUserDTO extendMstUserDTO = new ExtendMstUserDTO();

//			extendMstUserDTO = userService.getUserName(dto.getCreateUserId());
//			extendMstUserDTO.setUpdateUserFamilyNmKanji(userService.getUserName(dto.getUpdateUserId()).getUserFamilyNmKanji());
//			extendMstUserDTO.setUpdateUserFirstNmKanji(userService.getUserName(dto.getUpdateUserId()).getUserFirstNmKanji());

//			dto.setCreateUserFamilyName(extendMstUserDTO.getUserFamilyNmKanji());
//			dto.setCreateUserFirstName(extendMstUserDTO.getUserFirstNmKanji());
//			dto.setUpdateUserFamilyName(extendMstUserDTO.getUpdateUserFamilyNmKanji());
//			dto.setUpdateUserFirstName(extendMstUserDTO.getUpdateUserFirstNmKanji());


			Map<String, Object> createUserMap = userMap.get(String.valueOf(dto.getCreateUserId()));
			if (createUserMap == null) {
				System.out.println(dto.getCreateUserId());
				throw new NullPointerException();
			}
			dto.setCreateUserFamilyName((String) createUserMap.get("userFamilyNmKanji"));
			dto.setCreateUserFirstName((String) createUserMap.get("userFirstNmKanji"));

			Map<String, Object> updateUserMap = userMap.get(String.valueOf(dto.getUpdateUserId()));

			if (updateUserMap == null) {
				System.out.println(dto.getUpdateUserId());
				throw new NullPointerException();
			}
			dto.setUpdateUserFamilyName((String) updateUserMap.get("userFamilyNmKanji"));
			dto.setUpdateUserFirstName((String) updateUserMap.get("userFirstNmKanji"));
		}
		return list;
	}

	//記事登録
	public void registryNotice(LoginForm form) throws Exception{

		LoginDAO dao = new LoginDAO();
		String notice = form.getNoticeDetail();

		dao.registryNoticeBoard(notice);
	}

	//記事更新
	public void updateNotice(LoginForm form) throws Exception{

		LoginDAO dao = new LoginDAO();
		int noticeSystemId = form.getNoticeSystemId();
		String notice = form.getNoticeDetail();

		dao.updateNotice(noticeSystemId, notice);
	}

	//記事削除
	public void deleteNotice(LoginForm form) throws Exception{

		LoginDAO dao = new LoginDAO();
		int noticeSystemId = form.getNoticeSystemId();

		dao.deleteNotice(noticeSystemId);
	}
}
