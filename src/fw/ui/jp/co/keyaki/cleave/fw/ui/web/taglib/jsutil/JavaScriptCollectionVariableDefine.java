/**
 *
 */
package jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jp.co.keyaki.cleave.fw.ui.web.taglib.JavaScriptHelper;

public class JavaScriptCollectionVariableDefine implements JavaScriptVariableDefine {

    public String define(Object value) {
    	if (value.getClass().isArray()) {
    		value = toCollection(value);
    	}
    	Collection<?> col = Collection.class.cast(value);
    	StringBuilder sb = new StringBuilder();
    	for (Object obj : col) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			JavaScriptVariableDefine variableDefine = JavaScriptVariableDefineFactory.INSTANCE.getVariableDefine(obj);
			sb.append(variableDefine.define(obj));
		}
        return JavaScriptHelper.JS_ARRAY_START_SYMBOL + sb.toString() + JavaScriptHelper.JS_ARRAY_END_SYMBOL;
    }

    protected Collection<?> toCollection(Object arr) {
    	List<Object> result = new ArrayList<Object>();
    	int length = Array.getLength(arr);
    	for (int i = 0; i < length; i++) {
    		Object e = Array.get(arr, i);
    		result.add(e);
    	}
    	return result;
    }
}