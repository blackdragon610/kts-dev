/**
 * 
 */
package jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil;


public class JavaScriptDecimalVariableDefine implements JavaScriptVariableDefine {

    public String define(Object value) {
        return value.toString();
    }
}