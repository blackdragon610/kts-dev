package jp.co.keyaki.cleave.fw.ui.web.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.PermissionInfo;
import jp.co.keyaki.cleave.fw.core.security.PermissionInfos;

public class HasPermissionTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String CONDITION_AND = "and";

	public static final String CONDITION_OR = "or";

	private String condition;

	private String permissionCds;

	public HasPermissionTag() {
	}

	@Override
	public int doStartTag() throws JspException {
		if (!ActionContext.hasLoginUserInfo()) {
			return SKIP_BODY;
		}
		String[] permissionCds = this.permissionCds.split(",");
		List<String> hasPermissionCds = new ArrayList<String>();
		for (String permissionCd : permissionCds) {
			PermissionInfo permissionInfo = PermissionInfos.getPermissionInfo(permissionCd);
			if (permissionInfo == null) {
				continue;
			}
			if (ActionContext.validatePermission(permissionInfo)) {
				hasPermissionCds.add(permissionCd);
			}
		}
		if (isAndCondition()) {
			if (hasPermissionCds.size() == permissionCds.length) {
				return EVAL_BODY_INCLUDE;
			}
		} else {
			if (!hasPermissionCds.isEmpty()) {
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

	protected boolean isAndCondition() {
		return CONDITION_AND.equals(getCondition());
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPermissionCds() {
		return permissionCds;
	}

	public void setPermissionCds(String permissionCd) {
		this.permissionCds = permissionCd;
	}

	@Override
	public void release() {
		this.condition = CONDITION_AND;
		this.permissionCds = null;
		super.release();
	}

}
