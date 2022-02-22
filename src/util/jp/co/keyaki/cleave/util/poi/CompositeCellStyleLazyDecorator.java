/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;


/**
 * {@link CellStyleLazyDecorator}をコンポーネントとしたコンポジットクラス.
 *
 * @author ytakahashi
 */
public class CompositeCellStyleLazyDecorator extends IdenticalClassComposite<CellStyleLazyDecorator> implements CellStyleLazyDecorator, Cloneable {

	/**
	 * セルの遅延修飾を行います。
	 *
	 * <p>
	 * コンポーネントの同インターフェースのメソッドを呼び出します.
	 * </p>
	 *
	 * @param style セル修飾を行うスタイルオブジェクト
	 * @param cell セル
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	public void decorateLazy(CellStyle style, Cell cell, CellStyleLazyContext lazyContext) {
		for (CellStyleLazyDecorator decorator : getComponents().values()) {
			decorator.decorateLazy(style, cell, lazyContext);
		}
	}

	/**
	 * インスタンスのクローンを作成し返します.
	 *
	 * @return クローン
	 */
	public CompositeCellStyleLazyDecorator clone() {
		CompositeCellStyleLazyDecorator clone = new CompositeCellStyleLazyDecorator();
		clone.getComponents().putAll(getComponents());
		return clone;
	}

}
