package jp.co.keyaki.cleave.fw.ui.web.taglib;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SessionInvalidateTag extends TagSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
     * (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        session.invalidate();
        return SKIP_BODY;
    }

}
