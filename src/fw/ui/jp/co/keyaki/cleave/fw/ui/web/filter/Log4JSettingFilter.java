package jp.co.keyaki.cleave.fw.ui.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.ui.web.WebFwConst;

/**
 * ログの設定をする。
 * 
 * @author ytakahashi
 * 
 */
public class Log4JSettingFilter implements Filter {

	// セッションＩＤを設定する
	private static final String KEY_SESSION_ID = "sessionId";

	// ログインCDを設定する
	private static final String KEY_LOGIN_CD = "loginCd";

	// セッションID不明
	private String unknownSessionId = "********************************";

	// 社員番号不明
	private String unknownLoginCd = "**************";

	/**
	 * 初期処理
	 * 
	 * @param config
	 */
	public void init(FilterConfig config) throws ServletException {
		if (config.getInitParameter("unknownSessionId") != null) {
			unknownSessionId = config.getInitParameter("unknownSessionId");
		}
		if (config.getInitParameter("unknownLoginCd") != null) {
			unknownLoginCd = config.getInitParameter("unknownLoginCd");
		}
	}

	/**
	 * ログを出力する。
	 * 
	 * @param request
	 *            ServletRequest
	 * @param response
	 *            ServletResponse
	 * @param chain
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		String sessionId = unknownSessionId;
		String loginCd = unknownLoginCd;
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpSession session = httpServletRequest.getSession(false);
			if (session != null) {
				sessionId = session.getId();
				UserInfo loginUser = (UserInfo) session.getAttribute(WebFwConst.SESSION_KEY_LOGIN_USER);
				if (loginUser != null) {
					loginCd = loginUser.getLoginCd();
				}
			}
		}
		MDC.put(KEY_SESSION_ID, sessionId);
		MDC.put(KEY_LOGIN_CD, loginCd);
		chain.doFilter(request, response);
		MDC.remove(KEY_LOGIN_CD);
		MDC.remove(KEY_SESSION_ID);
	}

	/**
	 * 終了処理
	 * 
	 */
	public void destroy() {
	}

}
