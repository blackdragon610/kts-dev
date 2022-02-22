package jp.co.keyaki.cleave.util.csv;

/**
 * CSV設定情報クラス.
 *
 * @author Yoshikazu Takahashi
 *
 */
public class CsvConfig {

    /**
     * 区切り文字：カンマ
     */
    public static final char SEPARATE_CHAR_COMMA = ',';

    /**
     * 区切り文字：タブ文字
     */
    public static final char SEPARATE_CHAR_TAB = '\t';

    /**
     * 囲い文字：シングルクォート文字
     */
    public static final char QUOTE_CHAR_SINGLE = '\'';

    /**
     * 囲い文字：ダブルクォート文字
     */
    public static final char QUOTE_CHAR_DOUBLE = '"';

    /**
     * ヘッダー行マーク文字：シャープ文字
     */
    public static final char HEADER_ROW_MARK_CHAR_SHARP = '#';

    /**
     * 行送り文字：キャリッジリターン
     */
    public static final String NEW_LINE_CODE_CR = "\r";

    /**
     * 行送り文字：ラインフィード
     */
    public static final String NEW_LINE_CODE_LF = "\n";

    /**
     * 行送り文字：キャリッジリターン＋ラインフィード
     */
    public static final String NEW_LINE_CODE_CRLF = NEW_LINE_CODE_CR + NEW_LINE_CODE_LF;

    /**
     * 文字エンコード：Windows-31J
     */
    public static final String ENCODING_WINDWOS31J = "Windows-31J";

    /**
     * 文字エンコード：Shift_JIS
     */
    public static final String ENCODING_SHIFT_JIS = "Shift_JIS";

    /**
     * 区切り文字.
     */
    private char separateChar;

    /**
     * 囲い文字.
     */
    private char quoteChar;

    /**
     * ヘッダー行マーク文字.
     */
    private char headerRowMarkChar;

    /**
     * 行送り文字.
     */
    private String newLineCode;

    /**
     * 文字エンコード
     */
    private String encoding;

    /**
     * 値を必ず囲い文字で囲うか.
     * true:囲う/false:必要に応じて
     */
    private boolean requireEnquote;

    /**
     * 出力時に改行を削除するか.
     * true:削除/false:削除しない
     */
    private boolean removeNewLine;

    /**
     * デフォルトコンストラクタ.
     */
    public CsvConfig() {
        this(ENCODING_WINDWOS31J);
    }

    /**
     * コンストラクタ.
     *
     * @param encoding 文字エンコード
     */
    protected CsvConfig(String encoding) {
        this(SEPARATE_CHAR_COMMA, QUOTE_CHAR_DOUBLE, HEADER_ROW_MARK_CHAR_SHARP, NEW_LINE_CODE_CRLF, encoding, false, false);
    }

    /**
     * コンストラクタ.
     *
     * @param separateChar 区切り文字
     * @param quoteChar 囲い文字
     * @param newLineCode 行送り文字
     * @param encoding 文字エンコード
     * @param requireEnquote 値を必ず囲い文字で囲うか true:囲う/false:必要に応じて
     * @param removeNewLine 出力時に改行を削除を行うか
     */
    protected CsvConfig(char separateChar, char quoteChar, char headerRowMarkChar, String newLineCode, String encoding, boolean requireEnquote, boolean removeNewLine) {
        setSeparateChar(separateChar);
        setQuoteChar(quoteChar);
        setHeaderRowMarkChar(headerRowMarkChar);
        setNewLineCode(newLineCode);
        setEncoding(encoding);
        setRequireEnquote(requireEnquote);
        setRemoveNewLine(removeNewLine);
    }

	/**
     * 区切り文字を返します.
     *
     * @return 区切り文字
     */
    public char getSeparateChar() {
        return separateChar;
    }

    /**
     * 区切り文字を設定します.
     *
     * @param separateChar 区切り文字
     */
    public void setSeparateChar(char separateChar) {
        this.separateChar = separateChar;
    }

    /**
     * 囲い文字を返します.
     *
     * @return 囲い文字
     */
    public char getQuoteChar() {
        return quoteChar;
    }

    /**
     * 囲い文字を設定します.
     *
     * @param quoteChar 囲い文字
     */
    public void setQuoteChar(char quoteChar) {
        this.quoteChar = quoteChar;
    }

    /**
     * ヘッダー行マーク文字を返します.
     *
     * @return ヘッダー行マーク文字
     */
    public char getHeaderRowMarkChar() {
        return headerRowMarkChar;
	}

    /**
     * ヘッダー行マーク文字を設定します.
     *
     * @param headerRowMarkChar ヘッダー行マーク文字
     */
    public void setHeaderRowMarkChar(char headerRowMarkChar) {
        this.headerRowMarkChar = headerRowMarkChar;
	}

    /**
     * 行送り文字を返します.
     *
     * @return 行送り文字
     */
    public String getNewLineCode() {
        return newLineCode;
    }

    /**
     * 行送り文字を設定します.
     *
     * @param newLineCode 行送り文字
     */
    public void setNewLineCode(String newLineCode) {
        this.newLineCode = newLineCode;
    }

    /**
     * 文字エンコードを返します.
     *
     * @return 文字エンコード
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * 文字エンコードを設定します.
     *
     * @param encoding 文字エンコード
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 値を必ず囲い文字で囲うかを返します.
     *
     * @return 値を必ず囲い文字で囲うか
     */
    public boolean isRequireEnquote() {
        return requireEnquote;
    }

    /**
     * 値を必ず囲い文字で囲うかを設定します.
     *
     * @param requireEnquote true:囲う/false:必要に応じて
     */
    public void setRequireEnquote(boolean requireEnquote) {
        this.requireEnquote = requireEnquote;
    }

    /**
     * 出力時に改行を削除を行うか返します.
     *
     * @return 出力時に改行を削除を行うか
     */
	public boolean isRemoveNewLine() {
		return removeNewLine;
	}

	/**
	 * 出力時に改行を削除を行うかを設定します.
	 *
	 * @param removeNewLine true:削除/false:削除しない
	 */
	public void setRemoveNewLine(boolean removeNewLine) {
		this.removeNewLine = removeNewLine;
	}

}
