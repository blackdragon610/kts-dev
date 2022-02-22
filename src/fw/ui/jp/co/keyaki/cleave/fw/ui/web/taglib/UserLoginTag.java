package jp.co.keyaki.cleave.fw.ui.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.co.keyaki.cleave.fw.core.ActionContext;

public class UserLoginTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserLoginTag() {
	}

	@Override
	public int doStartTag() throws JspException {
		if (ActionContext.hasLoginUserInfo()) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

}
