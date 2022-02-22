package jp.co.keyaki.cleave.util.csv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

public class CsvContext implements Iterable<CsvRecord> {

    private boolean isFirstHeader = false;

    private CsvColumns header = null;

    private List<CsvRecord> records = new ArrayList<CsvRecord>();

    public static final boolean CSV_HEADER_ENABLE = true;

    public CsvContext(boolean isFirstHeader) {
        setFirstHeader(isFirstHeader);
    }

    public boolean isFirstHeader() {
        return isFirstHeader;
    }

    public void setFirstHeader(boolean isFirstHeader) {
        this.isFirstHeader = isFirstHeader;
    }

    public boolean isHasHeader() {
        return getHeader() != null;
    }

    public CsvColumns getHeader() {
        return header;
    }

    public void setHeader(CsvRecord header) {
        this.header = new CsvColumns(header);
    }

    public List<CsvRecord> getRecords() {
        return records;
    }

    public void addRecord(int rowIndex, Object[] values) {
        CsvRecord record = new CsvRecord();
        record.addValues(values);
        addRecord(rowIndex, record);
    }

    public void addRecord(int rowIndex, CsvRecord record) {
        if (isFirstHeader() && !isHasHeader()) {
            setHeader(record);
        } else {
            records.add(rowIndex, record);
        }
    }

    public void addRecord(String[] values) {
        addRecord(getRecordCount(), values);
    }

    public void addRecord(CsvRecord record) {
        addRecord(getRecordCount(), record);
    }

    public int getRecordCount() {
        return records.size();
    }

    public String getValue(int rowIndex, String columnName) {
        int columnIndex = header.findColumnIndex(columnName);
        return getValue(rowIndex, columnIndex);
    }

    public String getValue(int rowIndex, int columnIndex) {
        return getRecord(rowIndex).getValue(columnIndex);
    }

    public List<String> getValues(String columnName) {
        int columnIndex = header.findColumnIndex(columnName);
        return getValues(columnIndex);
    }

    public List<String> getValues(int columnIndex) {
        List<String> values = new ArrayList<String>(getRecordCount());
        for (CsvRecord record : getRecords()) {
            values.add(record.getValue(columnIndex));
        }
        return values;
    }

    public CsvRecord getRecord(int rowIndex) {
        validateRowIndex(rowIndex);
        return records.get(rowIndex);
    }

    public CsvRecord removeRecord(int rowIndex) {
        validateRowIndex(rowIndex);
        return records.remove(rowIndex);
    }

    protected void validateRowIndex(int rowIndex) {
        if (rowIndex < NumberUtils.INTEGER_ZERO) {
            throw new IndexOutOfBoundsException("rowIndex is minus. rowIndex=" + rowIndex);
        }
        if (rowIndex >= getRecordCount()) {
            throw new IndexOutOfBoundsException("rowIndex is over record count. rowIndex=" + rowIndex
                    + ". record count=" + getRecordCount());
        }
    }

    public Iterator<CsvRecord> iterator() {
        return getRecords().iterator();
    }

    public CsvContext union(CsvContext context) {
        CsvContext union = new CsvContext(isFirstHeader);
        union.header = this.header != null ? this.header : context.header;
        union.records = new ArrayList<CsvRecord>(this.getRecordCount() + context.getRecordCount());
        union.records.addAll(this.records);
        union.records.addAll(context.records);
        return union;
    }

}
