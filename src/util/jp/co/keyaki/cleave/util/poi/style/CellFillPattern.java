/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import jp.co.keyaki.cleave.util.poi.CellStyleDecorator;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyDecorator;
import jp.co.keyaki.cleave.util.poi.ColorUtils;
import jp.co.keyaki.cleave.util.poi.ColorUtils.Color;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 * セル塗りつぶしクラス。
 *
 * @author ytakahashi
 *
 */
public class CellFillPattern implements CellStyleDecorator, CellStyleLazyDecorator {

	/**
	 * 全塗りつぶし・単色・黄
	 */
	public static final CellFillPattern LIGHT_YELLOW = new CellFillPattern(ColorUtils.LIGHT_YELLOW);
	/**
	 * 全塗りつぶし・単色・少し薄い青色
	 */
	public static final CellFillPattern PALE_BLUE = new CellFillPattern(ColorUtils.PALE_BLUE);
	/**
	 * 全塗りつぶし・単色・薄い水色
	 */
	public static final CellFillPattern LIGHT_TURQUOISE = new CellFillPattern(ColorUtils.LIGHT_TURQUOISE);
	/**
	 * 全塗りつぶし・単色・薄い緑
	 */
	public static final CellFillPattern LIGHT_GREEN = new CellFillPattern(ColorUtils.LIGHT_GREEN);
	/**
	 * 全塗りつぶし・単色・少し薄いオレンジ色
	 */
	public static final CellFillPattern TAN = new CellFillPattern(ColorUtils.TAN);

	/**
	 * 塗りつぶしパターン
	 */
	protected short fillPattern = CellStyle.SOLID_FOREGROUND;

	/**
	 * 塗りつぶし色
	 */
	protected Color foregroundColor = ColorUtils.YELLOW;

	/**
	 * 第二塗りつぶし色
	 */
	protected Color backgroundColor = null;

	/**
	 * コンストラクタ。
	 */
	public CellFillPattern() {
	}

	/**
	 * コンストラクタ。
	 *
	 * @param foregroundColor 塗りつぶし色
	 */
	public CellFillPattern(Color foregroundColor) {
		setForegroundColor(foregroundColor);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param foregroundColorIndex 塗りつぶし色インデックス
	 */
	public CellFillPattern(short foregroundColorIndex) {
		setForegroundColorIndex(foregroundColorIndex);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param foregroundColor 塗りつぶし色
	 * @param backgroundColor 第二塗りつぶし色
	 * @param fillPattern 塗りつぶしパターン
	 */
	public CellFillPattern(Color foregroundColor, Color backgroundColor, short fillPattern) {
		setForegroundColor(foregroundColor);
		setFillPattern(fillPattern);
		setBackgroundColor(backgroundColor);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param foregroundColor 塗りつぶし色インデックス
	 * @param backgroundColor 第二塗りつぶし色インデックス
	 * @param fillPattern 塗りつぶしパターン
	 */
	public CellFillPattern(short foregroundColorIndex,short backgroundColorIndex, short fillPattern) {
		setForegroundColorIndex(foregroundColorIndex);
		setFillPattern(fillPattern);
		setBackgroundColorIndex(backgroundColorIndex);
	}

	/**
	 * 塗りつぶし色を返します。
	 *
	 * @return 塗りつぶし色
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * 塗りつぶし色を設定します。
	 *
	 * @param foregroundColor 塗りつぶし色
	 */
	protected void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	/**
	 * 塗りつぶし色を設定します。
	 *
	 * @param foregroundColorIndex 塗りつぶし色インデックス
	 */
	protected void setForegroundColorIndex(short foregroundColorIndex) {
		setForegroundColor(ColorUtils.getCacheColor(foregroundColorIndex));
	}

	/**
	 * 塗りつぶしパターンを返します。
	 *
	 * @return 塗りつぶしパターン
	 */
	public short getFillPattern() {
		return fillPattern;
	}

	/**
	 * 塗りつぶしパターンを設定します。
	 *
	 * @param fillPattern 塗りつぶしパターン
	 */
	protected void setFillPattern(short fillPattern) {
		this.fillPattern = fillPattern;
	}

	/**
	 * 第二塗りつぶし色を返します。
	 *
	 * @return 第二塗りつぶし色
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * 第二塗りつぶし色を設定します。
	 *
	 * @param backgroundColor 第二塗りつぶし色
	 */
	protected void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * 第二塗りつぶし色を設定します。
	 *
	 * @param backgroundColorIndex 第二塗りつぶし色インデックス
	 */
	protected void setBackgroundColorIndex(short backgroundColorIndex) {
		setBackgroundColor(ColorUtils.getCacheColor(backgroundColorIndex));
	}

	/**
	 * セルを修飾するメソッド。
	 *
	 * 枠線は、CellStyleを利用するため、
	 * 当メソッド内では塗りつぶしを指定することはせず、遅延処理として
	 * 遅延修飾コンテキストに塗りつぶしを設定することを記録するだけとする。
	 *
	 * @param cell 本メソッド内の修飾対象セル
	 * @param range 同時に修飾命令行う処理対象のセル範囲
	 * @param point 修飾対象の座標位置
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	public void decorate(Cell cell, Range range, Point point, CellStyleLazyContext lazyContext) {
		lazyContext.regist(point, this);
	}

	/**
	 * セルの遅延修飾を行います。
	 *
	 * 先延ばししていた、塗りつぶし修飾を実際に行います。
	 *
	 * @param style セル修飾を行うスタイルオブジェクト
	 * @param cell セル
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	public void decorateLazy(CellStyle style, Cell cell,
			CellStyleLazyContext lazyContext) {
		style.setFillPattern(getFillPattern());
		if (getFillPattern() == CellStyle.NO_FILL) {
			return;
		}
		if (getForegroundColor() != null) {
			style.setFillForegroundColor(getForegroundColor().getIndex());
		}
		if (getBackgroundColor() != null) {
			style.setFillBackgroundColor(getBackgroundColor().getIndex());
		}
	}

	/**
	 * ハッシュ値を返します。
	 *
	 * @return ハッシュ値
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += fillPattern;
		hashCode *= 32;
		if (foregroundColor != null) {
			hashCode += foregroundColor.getIndex();
		}
		hashCode *= 32;
		if (backgroundColor != null) {
			hashCode += backgroundColor.getIndex();
		}
		return hashCode;
	}

	/**
	 * オブジェクトが同値関係を判定し返します。
	 *
	 * @param obj 比較対象オブジェクト
	 * @return true：同値関係/false：同値関係ではない
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
		CellFillPattern same = (CellFillPattern) obj;
		return fillPattern == same.fillPattern
				&& ObjectUtils.equals(foregroundColor, same.foregroundColor)
				&& ObjectUtils.equals(backgroundColor, same.backgroundColor);
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
