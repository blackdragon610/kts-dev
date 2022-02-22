/**
 * 
 */
package jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil;

import jp.co.keyaki.cleave.fw.ui.web.taglib.JavaScriptHelper;

public class JavaScriptNullVariableDefine implements JavaScriptVariableDefine {

    public String define(Object value) {
        return JavaScriptHelper.JS_NULL_SYMBOL;
    }
}