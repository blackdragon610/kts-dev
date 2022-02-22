package jp.co.keyaki.cleave.fw.ui.web.taglib;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.WriteTag;

import jp.co.keyaki.cleave.fw.core.util.ReflectionUtils;
import jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil.JavaScriptVariableDefine;
import jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil.JavaScriptVariableDefineFactory;

public class JavaScriptConstantsBuilderTag extends WriteTag {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Map<String, Map<String, Object>> CACHE_DEDFINE = new HashMap<String, Map<String, Object>>();

	private String javaClassName;

	private String javaScriptClassName;

	protected JavaScriptVariableDefineFactory factory = JavaScriptVariableDefineFactory.INSTANCE;

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		Map<String, Object> constants = CACHE_DEDFINE.get(getJavaClassName());
		if (constants == null) {
			constants = getPublicStaticFinalFields();
			CACHE_DEDFINE.put(getJavaClassName(), constants);
		}
		String jsClassName = javaScriptClassName;
		if (jsClassName == null) {
			jsClassName = createDefaultJavaScriptClassName();
		}
		StringBuilder js = new StringBuilder();
		js.append(JavaScriptHelper.JS_DEFINE_START);
		defineJavaScriptClass(js, jsClassName, constants);
		js.append(JavaScriptHelper.JS_DEFINE_END);
		TagUtils.getInstance().write(pageContext, js.toString());
		return SKIP_BODY;
	}

	protected void defineJavaScriptClass(StringBuilder js, String jsClassName, Map<String, Object> constants) {

		js.append("/**\n");
		js.append(" * \n");
		js.append(" * ").append(javaClassName).append(" に対応する JavaScript Class\n");
		js.append(" * \n");
		js.append(" */\n");
		js.append("function ").append(jsClassName).append("() {};\n");
		js.append(jsClassName).append(".prototype = new Object();\n");
		for (Map.Entry<String, Object> entry : constants.entrySet()) {
			String constantName = entry.getKey();
			Object constantValue = entry.getValue();
			JavaScriptVariableDefine variableDefine = factory.getVariableDefine(constantValue, null);
			if (variableDefine == null) {
				continue;
			}
			js.append(jsClassName).append(".").append(constantName);
			js.append(" = ").append(variableDefine.define(constantValue)).append(";\n");
		}

	}

	protected Class<?> getJavaClass() {
		return ReflectionUtils.forName(getJavaClassName());
	}

	protected String createDefaultJavaScriptClassName() {
		return getJavaClass().getSimpleName();
	}

	protected Map<String, Object> getPublicStaticFinalFields() {
		Map<String, Object> constants = new LinkedHashMap<String, Object>();
		Class<?> clazz = getJavaClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!ReflectionUtils.isPublicStaticFinalField(field)) {
				continue;
			}
			String name = field.getName();
			Object value = ReflectionUtils.getValue(field, null);;
			constants.put(name, value);
		}
		return constants;
	}

	/**
	 * タグライブラリのインスタンス初期化処理。
	 * <p>
	 * 当クラスにてインスタンス変数javaScriptArrayNameを定義しているが、 必須であるため、あえて初期化は行っていない。
	 * </p>
	 *
	 * @see org.apache.struts.taglib.bean.WriteTag#release()
	 */
	@Override
	public void release() {
		javaClassName = null;
		javaScriptClassName = null;
		super.release();
	}

	/**
	 * @return the javaClassName
	 */
	public String getJavaClassName() {
		return javaClassName;
	}

	/**
	 * @param javaClassName
	 *            the javaClassName to set
	 */
	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	/**
	 * @return the javaScriptClassName
	 */
	public String getJavaScriptClassName() {
		return javaScriptClassName;
	}

	/**
	 * @param javaScriptClassName
	 *            the javaScriptClassName to set
	 */
	public void setJavaScriptClassName(String javaScriptClassName) {
		this.javaScriptClassName = javaScriptClassName;
	}

}
