/**
 *
 */
package jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil;

import jp.co.keyaki.cleave.fw.ui.web.taglib.JavaScriptHelper;

public class JavaScriptCharVariableDefine implements JavaScriptVariableDefine {

	public String define(Object value) {
		String output = escape(value.toString());
		return JavaScriptHelper.JS_CHAR_DEFINE_SYMBOL.concat(output).concat(
				JavaScriptHelper.JS_CHAR_DEFINE_SYMBOL);
	}

	protected String escape(String value) {
		value = value.replace(JavaScriptHelper.JS_CHAR_DEFINE_SYMBOL,
				JavaScriptHelper.JS_ESCAPE_CHAR_SYMBOL.concat(JavaScriptHelper.JS_CHAR_DEFINE_SYMBOL));
		value = value.replaceAll("\r\n", "\n").replaceAll("[\n|\r]", "\\\\n");
		return value;
	}
}