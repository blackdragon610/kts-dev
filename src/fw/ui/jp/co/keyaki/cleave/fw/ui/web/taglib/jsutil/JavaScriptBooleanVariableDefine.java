/**
 * 
 */
package jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil;

public class JavaScriptBooleanVariableDefine implements JavaScriptVariableDefine {

    public String define(Object value) {
        return value.toString();
    }
}