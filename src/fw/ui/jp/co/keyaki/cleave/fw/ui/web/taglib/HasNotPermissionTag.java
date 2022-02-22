package jp.co.keyaki.cleave.fw.ui.web.taglib;

import javax.servlet.jsp.JspException;

public class HasNotPermissionTag extends HasPermissionTag {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HasNotPermissionTag() {
	}

	@Override
	public int doStartTag() throws JspException {
		int result = super.doStartTag();
		if (result == SKIP_BODY) {
			result = EVAL_BODY_INCLUDE;
		} else {
			result = SKIP_BODY;
		}
		return result;
	}

}
