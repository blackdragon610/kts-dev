/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.keyaki.cleave.util.poi.CellStyleDecorator;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 *
 * @author ytakahashi
 *
 */
public class ColumnWidth implements CellStyleDecorator {

	/**
	 *
	 */
	private short width;

	/**
	 *
	 * @param columnWidth
	 */
	public ColumnWidth(short columnWidth) {
		setWidth(columnWidth);
	}

	/**
	 * @return the width
	 */
	public short getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	protected void setWidth(short width) {
		this.width = width;
	}

	/**
	 *
	 */
	public void decorate(Cell cell, Range range, Point point,
			CellStyleLazyContext lazyContext) {
		Sheet sheet = cell.getSheet();
		if (sheet.getColumnWidth(point.getColIndex()) != getWidth()) {
			sheet.setColumnWidth(point.getColIndex(), getWidth());
		}
	}

	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += width;
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
		ColumnWidth same = ColumnWidth.class.cast(obj);
		return width == same.width;
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
