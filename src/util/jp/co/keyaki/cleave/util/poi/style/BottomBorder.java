/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyDecorator;
import jp.co.keyaki.cleave.util.poi.ColorUtils;
import jp.co.keyaki.cleave.util.poi.ColorUtils.Color;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 * セル下枠線クラス。
 *
 * @author ytakahashi
 *
 */
public class BottomBorder extends Border implements CellStyleLazyDecorator {

	/**
	 * 細い実線・黒色。
	 */
	public static BottomBorder THIN = new BottomBorder(CellStyle.BORDER_THIN, ColorUtils.BLACK);
	/**
	 * 点線・黒色。
	 */
	public static BottomBorder DOTTED = new BottomBorder(CellStyle.BORDER_DOTTED, ColorUtils.BLACK);
	/**
	 * 点線・灰色50%。
	 */
	public static BottomBorder DOTTED_GREY_50 = new BottomBorder(CellStyle.BORDER_DOTTED, ColorUtils.GREY_50_PERCENT);
	/**
	 * 枠線無し（枠線を消す）。
	 */
	public static BottomBorder NONE = new BottomBorder(CellStyle.BORDER_NONE);

	/**
	 * コンストラクタ。
	 *
	 * 枠線オブジェクトの枠線の形状・枠線色と同一の下枠線オブジェクトを作成するコンストラクタ。
	 *
	 * @param source 複製元枠線オブジェクト
	 */
	public BottomBorder(Border source) {
		super(source);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線の形状を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderType 枠線の形状
	 * @see CellStyle
	 */
	public BottomBorder(short borderType) {
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
	public BottomBorder(Color borderColor) {
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
	public BottomBorder(short borderType, Color borderColor) {
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
	public BottomBorder(short borderType, short borderColorIndex) {
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
		// 同一処理範囲のセルの一番下に位置しないセルの場合は枠線を設定する事はしない
		if (!range.isBottommost(point)) {
			return;
		}
		// 遅延処理として処理対象座標に対して下枠線を登録する
		lazyContext.regist(point, this);
		// 枠線が重なる位置（下のセル）の座標を取得
		Point overlapPoint = point.getDownPoint();
		// 座標を取得できた場合
		if (overlapPoint != null) {
			// 下のセルの上枠線を下枠線と同一の属性で遅延処理するために
			// 上枠線を登録する。
			lazyContext.regist(overlapPoint, new TopBorder(this));
		}
	}

	/**
	 * セルの遅延修飾を行います。
	 *
	 * 先延ばししていた、下枠線修飾を実際に行います。
	 *
	 * @param style セル修飾を行うスタイルオブジェクト
	 * @param cell セル
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	public void decorateLazy(CellStyle style, Cell cell,
			CellStyleLazyContext lazyContext) {
		style.setBorderBottom(getBorderType());
		// 枠線形状が無し以外の場合は枠線色を設定する
		if (getBorderType() != CellStyle.BORDER_NONE) {
			style.setBottomBorderColor(getBorderColor().getIndex());
		}
	}

}
