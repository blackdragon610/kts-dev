package jp.co.keyaki.cleave.fw.ui.web.struts;

/**
 * トークンクラス
 *
 *  tokenチェック時、サブウインドウを開くとtokenが初期化されてしまうため
 *  tokenを保持したままの処理が必要になった
 * @author n_nozawa
 * 20170606
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class subWindowTokenType extends TokenType {

	@Override
	public boolean validate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.getAttribute("org.apache.struts.action.TOKEN");

		//jspのtoken
		String jspToken = request.getParameter("org.apache.struts.taglib.html.TOKEN");
		//サーバのtoken
		String serverToken = (String) request.getSession().getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY);

		//nullでなければ
		if (!StringUtils.equals(jspToken, null)) {
			//違ければサーバのほうを書き換え
			if (!jspToken.equals(serverToken)) {
				request.getSession().setAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY, jspToken);
				System.out.println("セッションのToken："+request.getSession().getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY));
			}
		}

		//セッションtokenになにが入っているか確認用
//		System.out.println("セッションのToken："+request.getSession().getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY));
		return true;
	}

}
