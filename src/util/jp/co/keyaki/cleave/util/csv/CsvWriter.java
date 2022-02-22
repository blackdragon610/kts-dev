package jp.co.keyaki.cleave.util.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class CsvWriter {

	private static final char[] FROM = {'\u00A2', '\u00A3', '\u00AC', '\u2016', '\u2212', '\u301C', '\u2014'};
	private static final char[]   TO = {'\uFFE0', '\uFFE1', '\uFFE2', '\u2225', '\uFF0D', '\uFF5E', '\u2015'};

	private CsvConfig config;

	public CsvWriter(CsvConfig config) {
		setConfig(config);
	}

	protected CsvConfig getConfig() {
		return config;
	}

	protected void setConfig(CsvConfig config) {
		this.config = config;
	}

	public void write(CsvContext context, File csvFile, boolean isWriteHeader)
			throws IOException {
		List<CsvRecord> records = getOutputRecords(context, isWriteHeader);
		write(records, csvFile, isWriteHeader && context.isHasHeader());
	}

	public void write(CsvContext context, OutputStream output,
			boolean isWriteHeader) throws IOException {
		List<CsvRecord> records = getOutputRecords(context, isWriteHeader);
		write(records, output, isWriteHeader && context.isHasHeader());
	}

	public void write(CsvContext context, Writer writer, boolean isWriteHeader)
			throws IOException {
		List<CsvRecord> records = getOutputRecords(context, isWriteHeader);
		write(records, writer, isWriteHeader && context.isHasHeader());
	}

	protected List<CsvRecord> getOutputRecords(CsvContext context,
			boolean isWriteHeader) {
		List<CsvRecord> records = new ArrayList<CsvRecord>(context.getRecordCount() + 1);
		if (context.isHasHeader() && isWriteHeader) {
			records.add(context.getHeader().getRecord());
		}
		records.addAll(context.getRecords());
		return records;
	}

	public void write(List<CsvRecord> records, File csvFile,
			boolean isFirstHeader) throws IOException {
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(csvFile, true);
			write(records, output, isFirstHeader);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	public void write(List<CsvRecord> records, OutputStream output,
			boolean isFirstHeader) throws IOException {
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(output, Charset.forName(getConfig().getEncoding()));
			write(records, writer, isFirstHeader);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	public void write(List<CsvRecord> records, Writer writer,
			boolean isFirstHeader) throws IOException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(writer);
			boolean isFirstRow = true;
			for (CsvRecord record : records) {
				if (isFirstRow && isFirstHeader) {
					bw.write(getConfig().getHeaderRowMarkChar());
				}
				boolean isFirstValue = true;
				for (String value : record.toArray()) {
					if (getConfig().isRemoveNewLine()) {
						value = removeNewLine(value);
					}
					if (isFirstValue) {
						isFirstValue = false;
					} else {
						bw.write(getConfig().getSeparateChar());
					}
					bw.write(escapeAndEnquote(value));
				}
				bw.write(getConfig().getNewLineCode());
				isFirstRow = false;
			}
		} finally {
			IOUtils.closeQuietly(bw);
		}
	}

	protected String escapeAndEnquote(String value) throws IOException {

		for (int i = 0; i < FROM.length; i++) {
			value = value.replace(FROM[i], TO[i]);
		}

		boolean requireEnquote = getConfig().isRequireEnquote();
		List<String> values = divideNewLine(value);
		// 改行が含まれる文字列の場合
		if (values.size() > 1) {
			requireEnquote = true;
		}
		String outputValue = concatNewLine(values);
		String seqarateString = Character.toString(getConfig()
				.getSeparateChar());
		if (outputValue.indexOf(seqarateString) >= 0) {
			requireEnquote = true;
		}
		if (!requireEnquote) {
			return outputValue;
		}
		String quoteString = Character.toString(getConfig().getQuoteChar());
		if (outputValue.indexOf(quoteString) >= 0) {
			outputValue = outputValue.replace(quoteString, quoteString + quoteString);
		}
		return quoteString + outputValue + quoteString;
	}

	protected String removeNewLine(String value) throws IOException {
		List<String> singleValues = divideNewLine(value);
		StringBuilder concatValue = new StringBuilder();
		for (String singleValue : singleValues) {
			if (concatValue.length() > 0) {
				concatValue.append(" ");
			}
			concatValue.append(singleValue);
		}
		return concatValue.toString();
	}

	protected List<String> divideNewLine(String value) throws IOException {

		List<String> values = new ArrayList<String>();
		StringReader strReader = null;
		BufferedReader bufReader = null;
		try {
			strReader = new StringReader(value);
			bufReader = new BufferedReader(strReader);
			while (bufReader.ready()) {
				String line = bufReader.readLine();
				if (line == null) {
					break;
				}
				values.add(line);
			}
		} finally {
			IOUtils.closeQuietly(bufReader);
			IOUtils.closeQuietly(strReader);
		}
		return values;
	}

	protected String concatNewLine(List<String> values) {
		String newLineCode = getConfig().getNewLineCode();
		StringBuilder sb = new StringBuilder();
		for (String aValue : values) {
			if (sb.length() > 0) {
				sb.append(newLineCode);
			}
			sb.append(aValue);
		}
		return sb.toString();
	}
}
