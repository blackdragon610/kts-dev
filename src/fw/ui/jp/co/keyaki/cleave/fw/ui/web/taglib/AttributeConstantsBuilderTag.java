package jp.co.keyaki.cleave.fw.ui.web.taglib;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import jp.co.keyaki.cleave.fw.core.util.ReflectionUtils;

public class AttributeConstantsBuilderTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String SCOPE_PAGE = "page";
	public static final String SCOPE_REQUEST = "request";
	public static final String SCOPE_SESSION = "session";
	public static final String SCOPE_APPLICATION = "applicaion";

	public static final String DEFAULT_MEMBER_SEPARATOR = "__";

	private static Map<String, Integer> SCOPE_MAP = new HashMap<String, Integer>();
	static {
		SCOPE_MAP.put(SCOPE_PAGE, PageContext.PAGE_SCOPE);
		SCOPE_MAP.put(SCOPE_REQUEST, PageContext.REQUEST_SCOPE);
		SCOPE_MAP.put(SCOPE_SESSION, PageContext.SESSION_SCOPE);
		SCOPE_MAP.put(SCOPE_APPLICATION, PageContext.APPLICATION_SCOPE);
	}

	private static final Map<String, Map<String, Object>> CACHE_DEDFINE = new HashMap<String, Map<String, Object>>();
	private static final Map<String, String> SHORT_CLASS_NAMES = new HashMap<String, String>();

	private String scope;

	/**
	 * Fully Qualified Class Name.
	 */
	private String javaClassName;

	private String prefix;

	/**
	 *
	 */
	private String memberSeparator = DEFAULT_MEMBER_SEPARATOR;

	protected String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	protected String getJavaClassName() {
		return javaClassName;
	}

	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	protected String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	protected String getMemberSeparator() {
		return memberSeparator;
	}

	public void setMemberSeparator(String memberSeparator) {
		this.memberSeparator = memberSeparator;
	}

	@Override
	public int doStartTag() throws JspException {
		Integer contextScope = SCOPE_MAP.get(getScope());
		if (contextScope == null) {
			contextScope = PageContext.PAGE_SCOPE;
		}
		Map<String, Object> constants = getConstants();
		String attPrefix = prefix;
		if (attPrefix == null) {
			attPrefix = SHORT_CLASS_NAMES.get(getJavaClassName());
		}
		putAttribute(contextScope, attPrefix, constants);
		return SKIP_BODY;
	}

	@Override
	public void release() {
		scope = null;
		javaClassName = null;
		prefix = null;
		memberSeparator = DEFAULT_MEMBER_SEPARATOR;
		super.release();
	}

	protected void putAttribute(int scope, String prefix, Map<String, Object> constants) {
		for (Map.Entry<String, Object> constant : constants.entrySet()) {
			String attributeName = prefix + getMemberSeparator() + constant.getKey();
			Object attributeValue = constant.getValue();
			pageContext.setAttribute(attributeName, attributeValue, scope);
		}
	}

	public Map<String, Object> getConstants() {
		Map<String, Object> constants = CACHE_DEDFINE.get(getJavaClassName());
		if (constants == null) {
			constants = getPublicStaticFinalFields();
			CACHE_DEDFINE.put(getJavaClassName(), constants);
		}
		return constants;
	}

	/**
	 *
	 *
	 * @return
	 */
	protected Map<String, Object> getPublicStaticFinalFields() {
		Map<String, Object> constants = new LinkedHashMap<String, Object>();
		Class<?> clazz = ReflectionUtils.forName(getJavaClassName());
		SHORT_CLASS_NAMES.put(getJavaClassName(), clazz.getSimpleName());
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!ReflectionUtils.isPublicStaticFinalField(field)) {
				continue;
			}
			String name = field.getName();
			Object value = ReflectionUtils.getValue(field, null);
			constants.put(name, value);
		}
		return constants;
	}

}
