/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jp.co.keyaki.cleave.util.poi.CellStyleDecorator;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyDecorator;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 *
 * @author ytakahashi
 *
 */
public class DisplayFormat implements CellStyleDecorator, CellStyleLazyDecorator {

	/**
	 *
	 */
	public static final String FORMAT_INTEGER_COMMA = "#,##0";

	/**
	 *
	 */
	public static final String FORMAT_DECIAL_COMMA_SCALE2 = "#,##0.00";

	/**
	 *
	 */
	public static final String FORMAT_PERCENT_SCALE0 = "0%";

	/**
	 *
	 */
	public static final String FORMAT_PERCENT_SCALE1 = "0.0%";

	/**
	 *
	 */
	public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy/mm/dd";

    /**
     *
     */
    public static final String FORMAT_DATE_YYYY_MM = "yyyy/mm";

    /**
    *
    */
   public static final String FORMAT_TIMESTAMP_YYYY_MM_DD_HH_MM_SS = "yyyy/mm/dd hh:mm:ss";

	/**
	 *
	 */
	public static final DisplayFormat INTEGER_COMMA = new DisplayFormat(FORMAT_INTEGER_COMMA);

	/**
	 *
	 */
	public static final DisplayFormat DECIAL_COMMA_SCALE2 = new DisplayFormat(FORMAT_DECIAL_COMMA_SCALE2);

	/**
	 *
	 */
	public static final DisplayFormat PERCENT_SCALE0 = new DisplayFormat(FORMAT_PERCENT_SCALE0);

	/**
	 *
	 */
	public static final DisplayFormat PERCENT_SCALE1 = new DisplayFormat(FORMAT_PERCENT_SCALE1);

	/**
	 *
	 */
	public static final DisplayFormat DATE_YYYY_MM_DD = new DisplayFormat(FORMAT_DATE_YYYY_MM_DD);

    /**
     *
     */
    public static final DisplayFormat DATE_YYYY_MM = new DisplayFormat(FORMAT_DATE_YYYY_MM);

    /**
    *
    */
   public static final DisplayFormat TIMESTAMP_YYYY_MM_DD_HH_MM_SS = new DisplayFormat(FORMAT_TIMESTAMP_YYYY_MM_DD_HH_MM_SS);

	/**
	 *
	 */
	protected String format;

	/**
	 *
	 * @param format
	 */
	public DisplayFormat(String format) {
		setFormat(format);
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	protected void setFormat(String format) {
		this.format = format;
	}


	public void decorate(Cell cell, Range range, Point point, CellStyleLazyContext lazyContext) {
		lazyContext.regist(point, this);
	}

	public void decorateLazy(CellStyle style, Cell cell,
			CellStyleLazyContext lazyContext) {
		Sheet sheet = cell.getSheet();
		Workbook book = sheet.getWorkbook();
		DataFormat format = book.createDataFormat();
		style.setDataFormat(format.getFormat(getFormat()));
	}

	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		if (format != null) {
			hashCode += format.hashCode();
		}
		return hashCode;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(getClass().isAssignableFrom(obj.getClass()))) {
			return false;
		}
		DisplayFormat same = (DisplayFormat) obj;
		return ObjectUtils.equals(format, same.format);
	}

	/**
	 * オブジェクトの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
