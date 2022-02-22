/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.ColorUtils;
import jp.co.keyaki.cleave.util.poi.ColorUtils.Color;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 * セル範囲指定時の縦位置中央枠線クラス。
 *
 * @author ytakahashi
 *
 */
public class MiddleBorder extends Border {

	/**
	 * 細い実線・黒色。
	 */
	public static MiddleBorder THIN = new MiddleBorder(CellStyle.BORDER_THIN, ColorUtils.BLACK);
	/**
	 * 点線・黒色。
	 */
	public static MiddleBorder DOTTED = new MiddleBorder(CellStyle.BORDER_DOTTED, ColorUtils.BLACK);
	/**
	 * 点線・灰色50%。
	 */
	public static MiddleBorder DOTTED_GREY_50 = new MiddleBorder(CellStyle.BORDER_DOTTED, ColorUtils.GREY_50_PERCENT);
	/**
	 * 枠線無し（枠線を消す）。
	 */
	public static MiddleBorder NONE = new MiddleBorder(CellStyle.BORDER_NONE);

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @see CellStyle
	 */
	public MiddleBorder(short borderType) {
		super(borderType);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderColor 枠線色
	 */
	public MiddleBorder(Color color) {
		super(color);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状と枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @param borderColor 枠線色
	 * @see CellStyle
	 */
	public MiddleBorder(short borderType, Color borderColor) {
		super(borderType, borderColor);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状と枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @param borderColorIndex 枠線色インデックス
	 */
	public MiddleBorder(short borderType, short borderColorIndex) {
		super(borderType, borderColorIndex);
	}

	/**
	 * セルを修飾するメソッド。
	 *
	 * 枠線は、CellStyleを利用するため、
	 * 当メソッド内では枠線を指定することはせず、遅延処理として
	 * 遅延修飾コンテキストに枠線を設定することを記録するだけとする。
	 *
	 * @param cell 本メソッド内の修飾対象セル
	 * @param range 同時に修飾命令行う処理対象のセル範囲
	 * @param point 修飾対象の座標位置
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	public void decorate(Cell cell, Range range, Point point, CellStyleLazyContext lazyContext) {
		// 修飾対象セルの下にセルが存在する場合
		if (range.hasDown(point)) {
			// 修飾対象セルのセル下に枠線を枠線を設定
			lazyContext.regist(point, new BottomBorder(getBorderType(), getBorderColor()));
			// 修飾対象セルの下セルのセル上に枠線を枠線を設定
			Point overlapPoint = point.getDownPoint();
			lazyContext.regist(overlapPoint, new TopBorder(getBorderType(), getBorderColor()));
		}
		// 修飾対象セルの上にセルが存在する場合
		if (range.hasUp(point)) {
			// 修飾対象セルのセル上に枠線を枠線を設定
			lazyContext.regist(point, new TopBorder(getBorderType(), getBorderColor()));
			Point overlapPoint = point.getUpPoint();
			// 修飾対象セルの上セルのセル下に枠線を枠線を設定
			lazyContext.regist(overlapPoint, new BottomBorder(getBorderType(), getBorderColor()));
		}
	}

}
