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
public class CellOptions implements CellStyleDecorator, CellStyleLazyDecorator {

	public static final CellOptions DEFAULT = new CellOptions();

	/**
	 *
	 */
	public static final CellOptions UNLOCK_VISIBLE = new CellOptions(false, false);

	/**
	 *
	 */
	public static final CellOptions UNLOCK_HIDDEN = new CellOptions(false, true);

	/**
	 *
	 */
	public static final CellOptions LOCK_VISIBLE = new CellOptions(true, false);

	/**
	 *
	 */
	public static final CellOptions LOCK_HIDDEN = new CellOptions(true, true);

	/**
	 *
	 */
	private boolean locked = false;

	/**
	 *
	 */
	private boolean hidden = false;

	/**
	 *
	 * @param alignment
	 */
	public CellOptions() {
		this(false, false);;
	}

	/**
	 *
	 * @param alignment
	 */
	public CellOptions(boolean locked) {
		this(locked, false);;
	}

	/**
	 *
	 * @param alignment
	 */
	public CellOptions(boolean locked, boolean hidden) {
		setLocked(locked);
		setHidden(hidden);
	}

	/**
	 * @return ロック
	 */
	public boolean getLocked() {
		return locked;
	}

	/**
	 * @param locked ロック
	 */
	protected void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return 非可視性
	 */
	public boolean getHidden() {
		return hidden;
	}

	/**
	 * @param hidden 非可視性
	 */
	protected void setHidden(boolean hidden) {
		this.hidden = hidden;
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
	public void decorateLazy(CellStyle style, Cell cell, CellStyleLazyContext lazyContext) {
		style.setLocked(getLocked());
		style.setHidden(getHidden());
	}

	/**
	 *
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += locked ? 1 : 0;
		hashCode *= 32;
		hashCode += hidden ? 1 : 0;
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
		CellOptions same = CellOptions.class.cast(obj);
		return locked == same.locked
				&& hidden == same.hidden;
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
