package jp.co.keyaki.cleave.fw.ui.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * トークンをチェックするクラス
 *
 * @author ytakahashi
 *
 */
public class CheckTokenType extends TokenType {

	/**
	 * ロガー
	 */
	private static Log LOG = LogFactory.getLog(CheckTokenType.class);

	/**
	 * トークンチェック処理
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public boolean validate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//jspのtoken
		String jspToken = request.getParameter("org.apache.struts.taglib.html.TOKEN");
		//サーバのtoken
		String serverToken = (String) request.getSession().getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY);

		if (LOG.isDebugEnabled()) {
			if (!isTokenStarted(request)) {
				LOG.debug("token not start.");
			}
		}
		return getTokenProcessor().isTokenValid(request, false);
	}

}
