package jp.co.keyaki.cleave.util.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class CsvReader {

    private CsvConfig config;

    private boolean isFirstHeader = false;

    public CsvReader(CsvConfig config) {
        this(config, false);
    }

    public CsvReader(CsvConfig config, boolean isFirstHeader) {
        setConfig(config);
        setFirstHeader(isFirstHeader);
    }

    protected CsvConfig getConfig() {
        return config;
    }

    protected void setConfig(CsvConfig config) {
        this.config = config;
    }

    protected boolean isFirstHeader() {
        return isFirstHeader;
    }

    protected void setFirstHeader(boolean isFirstHeader) {
        this.isFirstHeader = isFirstHeader;
    }

    public CsvContext parse(File csvFile) throws IOException {

        FileInputStream input = null;

        try {
            input = new FileInputStream(csvFile);
            return parse(input);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    public CsvContext parse(InputStream input) throws IOException {

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(input, Charset.forName(getConfig().getEncoding()));
            return parse(reader);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public CsvContext parse(Reader reader) throws IOException {

        CsvContext context = new CsvContext(isFirstHeader());

        BufferedReader bufReader = null;

        try {
            bufReader = new BufferedReader(reader);
            // クォーテーションにてエスケープされているか
            boolean isEscapedValue = false;
            // １つ前の文字が区切り文字であったか
            boolean isBeforeSep = true;
            CsvRecord record = null;
            StringBuilder value = new StringBuilder();
            while (bufReader.ready()) {
                String line = bufReader.readLine();
                if (line == null) {
                    break;
                }
                if (!isEscapedValue) {
                    if (record != null) {
                        context.addRecord(record);
                    }
                    record = new CsvRecord();
                } else {
                    value.append(getConfig().getNewLineCode());
                }
                // 読み込んだ文字列を１文字ずつ参照
                for (int nowIndex = 0; nowIndex < line.length(); nowIndex++) {
                    char nowChar = line.charAt(nowIndex);
                    // 文字がクォーテーションの場合
                    if (getConfig().getQuoteChar() == nowChar) {
                        // 現在エスケープ解析中の場合
                        if (isEscapedValue) {
                            // 次の文字を参照
                            int nextIndex = nowIndex + 1;
                            // 次の文字が存在しない、または次の文字がクォーテーションではない場合
                            if (nextIndex >= line.length() || getConfig().getQuoteChar() != line.charAt(nextIndex)) {
                                isEscapedValue = false;
                                // 次の文字もクォーテーションの場合
                            } else {
                                // クォーテーション２文字を１文字のデータとする
                                value.append(nowChar);
                                nowIndex++;
                            }
                        } else {
                            isEscapedValue = true;
                        }
                        // 文字が区切り文字の場合
                    } else if (getConfig().getSeparateChar() == nowChar && !isEscapedValue) {
                        record.addValue(StringUtils.trim(value.toString()));
                        value.delete(0, value.length());
                        isBeforeSep = true;
                    } else {
                        value.append(nowChar);
                        isBeforeSep = false;
                    }
                }
                if (!isEscapedValue && isBeforeSep || !isEscapedValue && value.length() > 0) {
                    record.addValue(value);
                    value.delete(0, value.length());
                }
            }
            if (record != null) {
                context.addRecord(record);
            }
        } finally {
            IOUtils.closeQuietly(bufReader);
        }
        return context;
    }

}
