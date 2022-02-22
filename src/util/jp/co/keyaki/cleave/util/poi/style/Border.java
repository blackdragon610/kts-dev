/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import jp.co.keyaki.cleave.util.poi.CellStyleDecorator;
import jp.co.keyaki.cleave.util.poi.ColorUtils;
import jp.co.keyaki.cleave.util.poi.ColorUtils.Color;

/**
 * 枠線を表す抽象クラス。
 *
 * @author ytakahashi
 *
 */
public abstract class Border implements CellStyleDecorator {

	/**
	 * 枠線の線形状。
	 */
	protected short borderType = HSSFCellStyle.BORDER_THIN;

	/**
	 * 枠線色。
	 */
	protected Color borderColor = ColorUtils.BLACK;

	/**
	 * コンストラクタ。
	 *
	 * 枠線オブジェクトの枠線の形状・枠線色と同一のオブジェクトを作成するコンストラクタ。
	 *
	 * @param source 複製元枠線オブジェクト
	 */
	protected Border(Border source) {
		setBorderType(source.getBorderType());
		setBorderColor(source.getBorderColor());
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @see HSSFCellStyle
	 */
	public Border(short borderType) {
		setBorderType(borderType);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderColor 枠線色
	 * @see IndexedColors
	 */
	public Border(Color borderColor) {
		setBorderColor(borderColor);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状と枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @param borderColor 枠線色
	 * @see HSSFCellStyle
	 * @see IndexedColors
	 */
	public Border(short borderType, Color borderColor) {
		setBorderType(borderType);
		setBorderColor(borderColor);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状と枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @param borderColorIndex 枠線色インデックス
	 */
	public Border(short borderType, short borderColorIndex) {
		setBorderType(borderType);
		setBorderColorIndex(borderColorIndex);
	}

	/**
	 * 枠線の形状を返します。
	 *
	 * @return 枠線の形状
	 */
	public short getBorderType() {
		return borderType;
	}

	/**
	 * 枠線の形状を設定します。
	 *
	 * @param borderType 枠線の形状
	 */
	protected void setBorderType(short borderType) {
		this.borderType = borderType;
	}

	/**
	 * 枠線色を返します。
	 *
	 * @return 枠線色
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * 枠線色を設定します。
	 *
	 * @param borderColor 枠線色
	 */
	protected void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * 枠線色を設定します。
	 *
	 * @param borderColorIndex 枠線色インデックス
	 */
	protected void setBorderColorIndex(short borderColorIndex) {
		setBorderColor(ColorUtils.getCacheColor(borderColorIndex));
	}

	/**
	 * ハッシュ値を返します。
	 *
	 * @return ハッシュ値
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode *= 32;
		hashCode += getClass().hashCode();
		hashCode *= 32;
		hashCode += borderType;
		hashCode *= 32;
		hashCode += borderColor.getIndex();
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
		Border same = (Border) obj;
		return borderType == same.borderType && ObjectUtils.equals(borderColor, borderColor);
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
