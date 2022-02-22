package jp.co.keyaki.cleave.util.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class CsvRecord implements Iterable<String> {

    public static final String EMPTY_STRING = StringUtils.EMPTY;

    private static final String[] EMPTY_STRING_ARRAY = new String[NumberUtils.INTEGER_ZERO];

    private final List<String> values = new ArrayList<String>();

    public CsvRecord() {
    }

    public CsvRecord(Object... values) {
        addValues(values);
    }

    public CsvRecord(List<?> values) {
        addValues(values);
    }

    public void addValues(Object... values) {
        for (Object value : values) {
            addValue(value);
        }
    }

    public void addValues(List<?> values) {
        for (Object value : values) {
            addValue(value);
        }
    }

    public void setValue(int columnIndex, Object value) {
        validateColumnIndex(columnIndex);
        value = toNullSafe(value);
        values.set(columnIndex, value.toString());
    }

    public void addValue(Object value) {

        value = toNullSafe(value);

        if (value.getClass().isArray()) {
            addValues(Object[].class.cast(value));
            return;
        }
        if (List.class.isAssignableFrom(value.getClass())) {
            addValues(List.class.cast(value));
            return;
        }
        values.add(value.toString());
    }

    protected Object toNullSafe(Object value) {
        if (value == null) {
            value = EMPTY_STRING;
        }
        return value;
    }

    public String getValue(int columnIndex) {
        validateColumnIndex(columnIndex);
        return values.get(columnIndex);
    }

    public String[] toArray() {
        return values.toArray(EMPTY_STRING_ARRAY);
    }

    public int getValueCount() {
        return values.size();
    }

    @Override
    public String toString() {
        return Arrays.deepToString(toArray());
    }

    public Iterator<String> iterator() {
        return values.iterator();
    }

    protected void validateColumnIndex(int columnIndex) throws IndexOutOfBoundsException {
        if (columnIndex < NumberUtils.INTEGER_ZERO) {
            throw new IndexOutOfBoundsException("columnIndex is minus. columnIndex=" + columnIndex);
        }
        if (columnIndex >= getValueCount()) {
            throw new IndexOutOfBoundsException("columnIndex is over column count. columnIndex=" + columnIndex
                    + ". column count=" + getValueCount());
        }
    }

}
