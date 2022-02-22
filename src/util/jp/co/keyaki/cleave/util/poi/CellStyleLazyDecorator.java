/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * セルの遅延修飾を行うインターフェース定義。
 *
 * @author ytakahashi
 *
 */
public interface CellStyleLazyDecorator {

	/**
	 * セルの遅延修飾を行います。
	 *
	 * @param style セル修飾を行うスタイルオブジェクト
	 * @param cell セル
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	void decorateLazy(CellStyle style, Cell cell, CellStyleLazyContext lazyContext);

}
