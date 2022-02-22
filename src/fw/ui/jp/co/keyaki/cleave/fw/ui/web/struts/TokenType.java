package jp.co.keyaki.cleave.fw.ui.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.TokenProcessor;

/**
 * トランザクショントークンを表現する抽象クラス。
 *
 * @author ytakahashi
 *
 */
public abstract class TokenType {

	/**
	 *
	 * トークンプロセッサーを返却します。
	 *
	 * @return トークンプロセッサー
	 */
	protected TokenProcessor getTokenProcessor() {
		return TokenProcessor.getInstance();
	}

	/**
	 *
	 * トークンが開始されているか判定して返します。
	 *
	 * @param request
	 *            リクエストオブジェクト
	 * @return true:トークンが開始されている/false:トークンが開始されていない。
	 */
	protected boolean isTokenStarted(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		String token = (String) session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
		if (token == null) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * サブクラスが実装すべき、トークンチェック処理メソッド。
	 *
	 * @param mapping
	 *            マッピングオブジェクト
	 * @param form
	 *            フォームオブジェクト
	 * @param request
	 *            リクエストオブジェクト
	 * @param response
	 *            レスポンスオブジェクト
	 * @return true:処理継続可能な場合/false:処理継続が不可能な場合（トークンチェック結果がNGの場合）
	 * @throws Exception
	 */
	public abstract boolean validate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
