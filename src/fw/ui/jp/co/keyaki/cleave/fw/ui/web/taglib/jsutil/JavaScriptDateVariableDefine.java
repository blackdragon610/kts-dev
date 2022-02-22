/**
 * 
 */
package jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil;

import java.util.Calendar;
import java.util.Date;

public class JavaScriptDateVariableDefine implements JavaScriptVariableDefine {

    public String define(Object value) {
        StringBuilder result = new StringBuilder();
        Date date = (Date) value;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        result.append("new Date(");
        result.append(cal.get(Calendar.YEAR));
        result.append(", ");
        result.append(cal.get(Calendar.MONDAY));
        result.append(", ");
        result.append(cal.get(Calendar.DAY_OF_MONTH));
        result.append(", ");
        result.append(cal.get(Calendar.HOUR_OF_DAY));
        result.append(", ");
        result.append(cal.get(Calendar.MINUTE));
        result.append(", ");
        result.append(cal.get(Calendar.SECOND));
        result.append(")");
        return result.toString();
    }
}