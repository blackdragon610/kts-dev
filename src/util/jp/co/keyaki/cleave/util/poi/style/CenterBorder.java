/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.ColorUtils;
import jp.co.keyaki.cleave.util.poi.ColorUtils.Color;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 * セル範囲指定時の横位置中央枠線クラス。
 *
 * @author ytakahashi
 *
 */
public class CenterBorder extends Border {

	/**
	 * 細い実線・黒色。
	 */
	public static CenterBorder THIN = new CenterBorder(CellStyle.BORDER_THIN, ColorUtils.BLACK);
	/**
	 * 点線・黒色。
	 */
	public static CenterBorder DOTTED = new CenterBorder(CellStyle.BORDER_DOTTED, ColorUtils.BLACK);
	/**
	 * 点線・灰色50%。
	 */
	public static CenterBorder DOTTED_GREY_50 = new CenterBorder(CellStyle.BORDER_DOTTED, ColorUtils.GREY_50_PERCENT);
	/**
	 * 枠線無し（枠線を消す）。
	 */
	public static CenterBorder NONE = new CenterBorder(CellStyle.BORDER_NONE);

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @see CellStyle
	 */
	public CenterBorder(short borderType) {
		super(borderType);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderColor 枠線色
	 * @see IndexedColors
	 */
	public CenterBorder(Color borderColor) {
		super(borderColor);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状と枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @param borderColor 枠線色
	 * @see CellStyle
	 * @see IndexedColors
	 */
	public CenterBorder(short borderType, Color borderColor) {
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
	public CenterBorder(short borderType, short borderColorIndex) {
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
		// 修飾対象セルの右にセルが存在する場合
		if (range.hasRight(point)) {
			// 修飾対象セルのセル右に枠線を枠線を設定
			lazyContext.regist(point, new RightBorder(getBorderType(), getBorderColor()));
			// 修飾対象セルの右セルのセル左に枠線を枠線を設定
			Point overlapPoint = point.getRightPoint();
			lazyContext.regist(overlapPoint, new LeftBorder(getBorderType(), getBorderColor()));
		}
		// 修飾対象セルの左にセルが存在する場合
		if (range.hasLeft(point)) {
			// 修飾対象セルのセル左に枠線を枠線を設定
			lazyContext.regist(point, new LeftBorder(getBorderType(), getBorderColor()));
			// 修飾対象セルの左セルのセル右に枠線を枠線を設定
			Point overlapPoint = point.getLeftPoint();
			lazyContext.regist(overlapPoint, new RightBorder(getBorderType(), getBorderColor()));
		}
	}

}
