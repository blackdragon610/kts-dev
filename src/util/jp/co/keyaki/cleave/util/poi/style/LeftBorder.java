/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import jp.co.keyaki.cleave.util.poi.CellStyleLazyContext;
import jp.co.keyaki.cleave.util.poi.CellStyleLazyDecorator;
import jp.co.keyaki.cleave.util.poi.ColorUtils;
import jp.co.keyaki.cleave.util.poi.ColorUtils.Color;
import jp.co.keyaki.cleave.util.poi.Point;
import jp.co.keyaki.cleave.util.poi.Range;

/**
 * セル左枠線クラス。
 *
 * @author ytakahashi
 *
 */
public class LeftBorder extends Border implements CellStyleLazyDecorator {

	/**
	 * 細い実線・黒色。
	 */
	public static LeftBorder THIN = new LeftBorder(CellStyle.BORDER_THIN, ColorUtils.BLACK);
	/**
	 * 点線・黒色。
	 */
	public static LeftBorder DOTTED = new LeftBorder(CellStyle.BORDER_DOTTED, ColorUtils.BLACK);
	/**
	 * 点線・灰色50%。
	 */
	public static LeftBorder DOTTED_GREY_50 = new LeftBorder(CellStyle.BORDER_DOTTED, ColorUtils.GREY_50_PERCENT);
	/**
	 * 枠線無し（枠線を消す）。
	 */
	public static LeftBorder NONE = new LeftBorder(CellStyle.BORDER_NONE);

	/**
	 * コンストラクタ。
	 *
	 * 枠線オブジェクトの枠線の形状・枠線色と同一の左枠線オブジェクトを作成するコンストラクタ。
	 *
	 * @param source 複製元枠線オブジェクト
	 */
	public LeftBorder(Border source) {
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
	public LeftBorder(short borderType) {
		super(borderType);
	}

	/**
	 * コンストラクタ。
	 *
	 * 枠線色を指定してオブジェクトを生成するコンストラクタ。
	 *
	 * @param borderColor 枠線色
	 */
	public LeftBorder(Color borderColor) {
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
	 */
	public LeftBorder(short borderType, Color borderColor) {
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
	public LeftBorder(short borderType, short borderColorIndex) {
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
		// 同一処理範囲のセルの一番左に位置しないセルの場合は枠線を設定する事はしない
		if (!range.isLeftmost(point)) {
			return;
		}
		// 遅延処理として処理対象座標に対して左枠線を登録する
		lazyContext.regist(point, this);
		// 枠線が重なる位置（左のセル）の座標を取得
		Point overlapPoint = point.getLeftPoint();
		// 座標を取得できた場合
		if (overlapPoint != null) {
			// 左のセルの右枠線を左枠線と同一の属性で遅延処理するために
			// 右枠線を登録する。
			lazyContext.regist(overlapPoint, new RightBorder(this));
		}
	}

	/**
	 * セルの遅延修飾を行います。
	 *
	 * 先延ばししていた、左枠線修飾を実際に行います。
	 *
	 * @param style セル修飾を行うスタイルオブジェクト
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	public void decorateLazy(CellStyle style, Cell cell,
			CellStyleLazyContext lazyContext) {
		style.setBorderLeft(getBorderType());
		// 枠線形状が無し以外の場合は枠線色を設定する
		if (getBorderType() != CellStyle.BORDER_NONE) {
			style.setLeftBorderColor(getBorderColor().getIndex());
		}
	}

}
