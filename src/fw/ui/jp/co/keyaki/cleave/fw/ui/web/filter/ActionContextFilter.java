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

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.ui.web.WebFwConst;

/**
 * Servlet Filter implementation class ActionContextFilter
 */
public class ActionContextFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public ActionContextFilter() {
	}

	protected FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		initActionContext(servletRequest);
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			resetActionContext();
		}
	}

	public void destroy() {
		filterConfig = null;
	}

	protected void initActionContext(ServletRequest servletRequest) {
		if (!(servletRequest instanceof HttpServletRequest)) {
			return;
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}

		UserInfo loginUserInfo = (UserInfo) session.getAttribute(WebFwConst.SESSION_KEY_LOGIN_USER);
		UserInfo operationUserInfo = (UserInfo) session.getAttribute(WebFwConst.SESSION_KEY_OPERATION_USER);
		if (loginUserInfo != null) {
			ActionContext.setLoginUserInfo(loginUserInfo);
		}
		if (operationUserInfo != null) {
			ActionContext.setOperationUserInfo(operationUserInfo);
		}

	}

	protected void resetActionContext() {
		ActionContext.reset();
	}

}
