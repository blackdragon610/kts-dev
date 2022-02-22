package jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.core.SystemConfig;
import jp.co.keyaki.cleave.fw.core.SystemConst;
import jp.co.keyaki.cleave.fw.ui.web.WebMessageDefine;
import jp.co.keyaki.cleave.fw.ui.web.WebRuntimeException;

public class JavaScriptVariableDefineFactory {

	private static final Log SYSTEM_LOG = LogFactory.getLog(SystemConst.LOGGER_NAME_SYSTEM);
	private static final Log LOG = LogFactory.getLog(JavaScriptVariableDefineFactory.class);

	private static final String JAVA_TYPE = "java-script-variable-define-factory.variable-defines.variable-define.java-type";
	private static final String DEFINE_CLASS = "java-script-variable-define-factory.variable-defines.variable-define.define-class";

	private static final Map<Class<?>, JavaScriptVariableDefine> DEFINES;
	static {
		if (SYSTEM_LOG.isInfoEnabled()) {
			SYSTEM_LOG.info("JavaScript変数宣言設定");
		}
		Map<Class<?>, JavaScriptVariableDefine> defines = new LinkedHashMap<Class<?>, JavaScriptVariableDefine>();
		Iterator<String> javaTypes = SystemConfig.getList(JAVA_TYPE).iterator();
		Iterator<String> defineClasses = SystemConfig.getList(DEFINE_CLASS).iterator();
		while (javaTypes.hasNext() && defineClasses.hasNext()) {
			String javaTypeName = javaTypes.next();
			String defineClassName = defineClasses.next();
			Class<?> javaType;
			try {
				javaType = Class.forName(javaTypeName);
			} catch (Throwable t) {
				throw new WebRuntimeException(WebMessageDefine.E000001, "java-typeのクラス取得に失敗", t);
			}
			Class<?> defineClass;
			try {
				defineClass = Class.forName(defineClassName);
			} catch (Throwable t) {
				throw new WebRuntimeException(WebMessageDefine.E000001, "define-classのクラス取得に失敗", t);
			}
			JavaScriptVariableDefine defineObject;
			try {
				defineObject = (JavaScriptVariableDefine) defineClass.newInstance();
			} catch (Throwable t) {
				throw new WebRuntimeException(WebMessageDefine.E000001, "define-classのインスタンス生成に失敗", t);
			}
			defines.put(javaType, defineObject);
			if (SYSTEM_LOG.isInfoEnabled()) {
				SYSTEM_LOG.info("\t".concat(javaTypeName).concat(" -> ").concat(defineClassName));
			}
		}
		DEFINES = Collections.unmodifiableMap(defines);
		if (DEFINES.isEmpty()) {
			if (SYSTEM_LOG.isInfoEnabled()) {
				SYSTEM_LOG.info("\tJavaScript変数宣言設定なし");
			}
		}
	}

	public static final JavaScriptVariableDefine NULL_VARIABLE_DEFINE = new JavaScriptNullVariableDefine();
	public static final JavaScriptVariableDefine DEFAULT_VARIABLE_DEFINE = new JavaScriptCharVariableDefine();

	public static final JavaScriptVariableDefineFactory INSTANCE = new JavaScriptVariableDefineFactory();

	public JavaScriptVariableDefineFactory() {
	}

	public JavaScriptVariableDefine getVariableDefine(Object obj) {
		return getVariableDefine(obj, DEFAULT_VARIABLE_DEFINE);
	}

	public JavaScriptVariableDefine getVariableDefine(Object obj, JavaScriptVariableDefine defaultVariableDefine) {
		if (obj == null) {
			return NULL_VARIABLE_DEFINE;
		}
		Class<?> objClass = obj.getClass();

		if (objClass.isArray()) {
			objClass = Collection.class;
		}

		for (Map.Entry<Class<?>, JavaScriptVariableDefine> define : DEFINES.entrySet()) {
			Class<?> javaType = define.getKey();
			if (javaType.isAssignableFrom(objClass)) {
				return define.getValue();
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("型に一致するJavaScript変数宣言設定が見つからない Class=" + obj.getClass().getName());
		}
		return defaultVariableDefine;
	}
}
