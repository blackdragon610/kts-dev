package jp.co.keyaki.cleave.fw.ui.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.TokenProcessor;

public class StartTokenType extends TokenType {

    private static Log LOG = LogFactory.getLog(StartTokenType.class);

    public boolean validate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TokenProcessor tokenProcessor = getTokenProcessor();
        if (isTokenStarted(request)) {
//BACKLOG-2104
//        	tokenProcessor.resetToken(request);
            if (LOG.isDebugEnabled()) {
                LOG.debug("token reset.");
            }
        } else {
        	tokenProcessor.saveToken(request);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("token start.");
        }
        return true;
    }

}
