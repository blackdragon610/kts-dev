/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jp.co.keyaki.cleave.util.poi.CellStyleDecorator;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyDecorator;
import jp.co.keyaki.cleave.util.poi.ColorUtils;
import jp.co.keyaki.cleave.util.poi.ColorUtils.Color;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 *
 * @author ytakahashi
 *
 */
public class FontSetting implements CellStyleDecorator, CellStyleLazyDecorator {

	/**
	 *
	 */
	public static final String FONT_NAME_ARIAL = "Arial";
	/**
	 *
	 */
	public static final String FONT_NAME_MS_GOTHIC = "ＭＳ ゴシック";
	/**
	 *
	 */
	public static final String FONT_NAME_MS_P_GOTHIC = "ＭＳ Ｐゴシック";
	/**
	 *
	 */
	public static final short DEFALUT_FONT_SIZE = 11;

	public static final short BOLDWEIGHT_NORMAL = Font.BOLDWEIGHT_NORMAL;
	public static final short BOLDWEIGHT_BOLD = Font.BOLDWEIGHT_BOLD;

	public static final short OFFSET_NONE = Font.SS_NONE;
	public static final short OFFSET_SUPER = Font.SS_SUPER;
	public static final short OFFSET_SUB = Font.SS_SUB;

	public static final byte UNDERLINE_NONE = Font.U_NONE;
	public static final byte UNDERLINE_SINGLE = Font.U_SINGLE;
	public static final byte UNDERLINE_SINGLE_ACCOUNTING = Font.U_SINGLE_ACCOUNTING;
	public static final byte UNDERLINE_DOUBLE = Font.U_DOUBLE;
	public static final byte UNDERLINE_DOUBLE_ACCOUNTING = Font.U_DOUBLE_ACCOUNTING;

	/**
	 *
	 */
	public static final FontSetting DEFAULT = new FontSetting();

	private short boldweight = BOLDWEIGHT_NORMAL;

	/**
	 *
	 */
	private Color fontColor = ColorUtils.BLACK;
	/**
	 *
	 */
	private short fontSize = DEFALUT_FONT_SIZE;
	/**
	 *
	 */
	private String fontName = FONT_NAME_MS_P_GOTHIC;
	/**
	 *
	 */
	private boolean italic = false;
	private boolean strikeout = false;
	private short typeOffset = OFFSET_NONE;
	private byte underline = UNDERLINE_NONE;

	/**
	 *
	 */
	public FontSetting() {
		this(BOLDWEIGHT_NORMAL, ColorUtils.BLACK, DEFALUT_FONT_SIZE, FONT_NAME_MS_P_GOTHIC, false, false, OFFSET_NONE, Font.U_NONE);
	}

	/**
	 *
	 * @param fontColor
	 */
	public FontSetting(Color fontColor) {
		this(BOLDWEIGHT_NORMAL, fontColor, DEFALUT_FONT_SIZE, FONT_NAME_MS_P_GOTHIC, false, false, OFFSET_NONE, Font.U_NONE);
	}

	/**
	 *
	 * @param fontName
	 */
	public FontSetting(String fontName) {
		this(BOLDWEIGHT_NORMAL, ColorUtils.BLACK, DEFALUT_FONT_SIZE, fontName, false, false, OFFSET_NONE, Font.U_NONE);
	}

	/**
	 *
	 * @param fontSize
	 */
	public FontSetting(short fontSize) {
		this(BOLDWEIGHT_NORMAL, ColorUtils.BLACK, fontSize, FONT_NAME_MS_P_GOTHIC, false, false, OFFSET_NONE, Font.U_NONE);
	}

	/**
	 *
	 * @param fontName
	 * @param fontSize
	 */
	public FontSetting(String fontName, short fontSize) {
		this(BOLDWEIGHT_NORMAL, ColorUtils.BLACK, fontSize, fontName, false, false, OFFSET_NONE, Font.U_NONE);
	}

	/**
	 *
	 * @param boldweight
	 * @param fontColor
	 * @param fontSize
	 * @param fontName
	 * @param italic
	 * @param strikeout
	 * @param typeOffset
	 * @param underline
	 */
	public FontSetting(short boldweight, Color fontColor, short fontSize, String fontName, boolean italic, boolean strikeout, short typeOffset, byte underline) {
		setBoldweight(boldweight);
		setFontColor(fontColor);
		setFontSize(fontSize);
		setFontName(fontName);
		setItalic(italic);
		setStrikeout(strikeout);
		setTypeOffset(typeOffset);
		setUnderline(underline);
	}

	public short getBoldweight() {
		return boldweight;
	}

	protected void setBoldweight(short boldweight) {
		this.boldweight = boldweight;
	}

	/**
	 * @return the fontColor
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor the fontColor to set
	 */
	protected void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return the fontSize
	 */
	public short getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	protected void setFontSize(short fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * @param fontName the fontName to set
	 */
	protected void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public boolean isItalic() {
		return italic;
	}

	protected void setItalic(boolean italic) {
		this.italic = italic;
	}

	public boolean isStrikeout() {
		return strikeout;
	}

	protected void setStrikeout(boolean strikeout) {
		this.strikeout = strikeout;
	}

	public short getTypeOffset() {
		return typeOffset;
	}

	protected void setTypeOffset(short typeOffset) {
		this.typeOffset = typeOffset;
	}

	public byte getUnderline() {
		return underline;
	}

	protected void setUnderline(byte underline) {
		this.underline = underline;
	}

	public short getFontHeight() {
		return (short)(getFontSize() * 20);
	}

	public void decorate(Cell cell, Range range, Point point, CellStyleLazyContext lazyContext) {
		lazyContext.regist(point, this);
	}

	public void decorateLazy(CellStyle style, Cell cell,
			CellStyleLazyContext lazyContext) {
		Sheet sheet = cell.getSheet();
		Workbook book = sheet.getWorkbook();
		Font font = book.findFont(getBoldweight(), fontColor.getIndex(), getFontHeight(), getFontName(), isItalic(), isStrikeout(), getTypeOffset(), getUnderline());
		if (font == null) {
			font = book.createFont();
			font.setBoldweight(getBoldweight());
			font.setColor(fontColor.getIndex());
			font.setFontHeightInPoints(getFontSize());
			font.setFontName(getFontName());
			font.setItalic(isItalic());
			font.setStrikeout(isStrikeout());
			font.setTypeOffset(getTypeOffset());
			font.setUnderline(getUnderline());
		}

		style.setFont(font);
	}

	public int hashCode() {
		int hashCode = 0;

		hashCode *= 32;
		hashCode += boldweight;
		hashCode *= 32;
		if (fontColor != null) {
			hashCode += fontColor.getIndex();
		}
		hashCode *= 32;
		hashCode += fontSize;
		hashCode *= 32;
		if (fontName != null) {
			hashCode += fontName.hashCode();
		}
		hashCode *= 32;
		if (italic) {
			hashCode += 1;
		}
		hashCode *= 32;
		if (strikeout) {
			hashCode += 1;
		}
		hashCode *= 32;
		hashCode += typeOffset;
		hashCode *= 32;
		hashCode += underline;

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
		FontSetting same = (FontSetting) obj;

		return  boldweight == same.boldweight
				&& ObjectUtils.equals(fontColor, same.fontColor)
				&& fontSize == same.fontSize
				&& ObjectUtils.equals(fontName, same.fontName)
				&& italic == same.italic
				&& strikeout == same.strikeout
				&& typeOffset == same.typeOffset
				&& underline == same.underline;
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
