/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

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
public class ValueVerticalAlignment implements CellStyleDecorator, CellStyleLazyDecorator {

	/**
	 *
	 */
	public static final ValueVerticalAlignment TOP = new ValueVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

	/**
	 *
	 */
	public static final ValueVerticalAlignment MIDDLE = new ValueVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

	/**
	 *
	 */
	public static final ValueVerticalAlignment BOTTOM = new ValueVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);

	/**
	 *
	 */
	public static final ValueVerticalAlignment JUSTIFY = new ValueVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);

	/**
	 *
	 */
	protected short alignment = HSSFCellStyle.VERTICAL_BOTTOM;

	/**
	 *
	 * @param alignment
	 */
	public ValueVerticalAlignment(short alignment) {
		this.alignment = alignment;
	}

	/**
	 * @return the alignment
	 */
	public short getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment the alignment to set
	 */
	protected void setAlignment(short alignment) {
		this.alignment = alignment;
	}

	/**
	 *
	 */
	public void decorate(Cell cell, Range range, Point point, CellStyleLazyContext lazyContext) {
		lazyContext.regist(point, this);
	}

	/**
	 *
	 */
	public void decorateLazy(CellStyle style, Cell cell,
			CellStyleLazyContext lazyContext) {
		style.setVerticalAlignment(getAlignment());
	}

	/**
	 *
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += alignment;
		return hashCode;
	}

	/**
	 *
	 */
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
		ValueVerticalAlignment same = ValueVerticalAlignment.class.cast(obj);
		return alignment == same.alignment;
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
