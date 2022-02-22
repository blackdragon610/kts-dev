package jp.co.keyaki.cleave.fw.ui.web.struts;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import jp.co.keyaki.cleave.fw.dao.SQLBuilderFactory;

public class AppInitPlugIn implements PlugIn {

	private static final Log LOG = LogFactory.getLog(AppInitPlugIn.class);

	private String sqlBuilderClass = "jp.co.keyaki.cleave.fw.dao.template.xml.XMLSQLBuilder";

	private String templatePath;

	public void setSqlBuilderClass(String sqlBuilderClass) {
		this.sqlBuilderClass = sqlBuilderClass;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public void init(ActionServlet paramActionServlet, ModuleConfig paramModuleConfig) throws ServletException {
		if (LOG.isInfoEnabled()) {
			LOG.info("SQLBuilder class '" + sqlBuilderClass + "'");
			LOG.info("Loading templatePath file from '" + templatePath + "'");
		}

		try {
			SQLBuilderFactory.regist(Class.forName(sqlBuilderClass));
		} catch (Throwable t) {
			throw new ServletException("notfound SQLBuilder '" + sqlBuilderClass + "'.", t);
		}

		URL input = null;
		try {
			input = paramActionServlet.getServletContext().getResource(templatePath);
		} catch (MalformedURLException e) {
		}
		if (input == null) {
			input = getClass().getResource(templatePath);
		}
		if (input == null) {
			throw new ServletException("Skipping templatePath file from '" + templatePath
					+ "'. No url could be located.");
		}
		try {
			SQLBuilderFactory.configure(input);
		} catch (Throwable t) {
			throw new ServletException("cannot initialize SQLBuilder class '" + sqlBuilderClass + "' templatePath'"
					+ templatePath + "'.", t);
		}
		setPermission();
	}

	public void destroy() {

	}

	protected void setPermission() {
		/*
		PermissionDao dao = new PermissionDao();
		List<Permission> allPermissions = dao.findAll();
		List<SimplePermissionInfo> permissionInfos = new ArrayList<SimplePermissionInfo>();
		for (Permission permission : allPermissions) {
			permissionInfos.add(new SimplePermissionInfo(new Long(permission.getPermissionId()).intValue(), permission .getPermissionCd(), permission.getPermissionName()));
		}
		PermissionInfos.getInstance().setPermissionInfos(permissionInfos);
		*/
	}
}
