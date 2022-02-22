/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import org.apache.poi.ss.usermodel.Cell;

/**
 * セルを修飾を行うインターフェース定義。
 *
 * @author ytakahashi
 *
 */
public interface CellStyleDecorator {

	/**
	 * セルを修飾するメソッド。
	 *
	 * @param cell 本メソッド内の修飾対象セル
	 * @param range 同時に修飾命令行う処理対象のセル範囲
	 * @param point 修飾対象の座標位置
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	void decorate(Cell cell, Range range, Point point, CellStyleLazyContext lazyContext);

}
