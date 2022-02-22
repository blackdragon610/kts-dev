package jp.co.keyaki.cleave.fw.ui.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class NoCheckTokenType extends TokenType {

    private static Log LOG = LogFactory.getLog(NoCheckTokenType.class);

    public boolean validate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (isTokenStarted(request)) {
        	//BACKLOG-2104
        	//getTokenProcessor().resetToken(request);
            if (LOG.isDebugEnabled()) {
                LOG.debug("token reset.");
            }
        }
        return true;
    }

}
