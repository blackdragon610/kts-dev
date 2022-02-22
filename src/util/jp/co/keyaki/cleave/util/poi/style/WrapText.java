/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
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
public class WrapText implements CellStyleDecorator, CellStyleLazyDecorator {

	/**
	 *
	 */
	public static final WrapText NO_WRAP = new WrapText(false);

	/**
	 *
	 */
	public static final WrapText WRAP = new WrapText(true);

	/**
	 *
	 */
	protected boolean wrap = false;

	/**
	 *
	 * @param alignment
	 */
	public WrapText(boolean wrap) {
		this.wrap = wrap;
	}

	/**
	 * @return the alignment
	 */
	public boolean getWrap() {
		return wrap;
	}

	/**
	 * @param alignment the alignment to set
	 */
	protected void setWrap(boolean wrap) {
		this.wrap = wrap;
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
		style.setWrapText(getWrap());
	}

	/**
	 *
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += wrap ? 1 : 0;
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
		WrapText same = WrapText.class.cast(obj);
		return wrap == same.wrap;
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
