/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import org.apache.poi.ss.usermodel.Cell;



/**
 * {@link CellStyleDecorator}をコンポーネントとしたコンポジットクラス.
 *
 * @author ytakahashi
 */
public class CompositeCellStyleDecorator extends IdenticalClassComposite<CellStyleDecorator> implements CellStyleDecorator, Cloneable {

	/**
	 * セルを修飾するメソッド。
	 *
	 * <p>
	 * コンポーネントの同インターフェースのメソッドを呼び出します.
	 * </p>
	 *
	 * @param cell 本メソッド内の修飾対象セル
	 * @param range 同時に修飾命令行う処理対象のセル範囲
	 * @param point 修飾対象の座標位置
	 * @param lazyContext 遅延修飾コンテキスト
	 */
	public void decorate(Cell cell, Range range, Point point, CellStyleLazyContext lazyContext) {
		for (CellStyleDecorator decorator : getComponents().values()) {
			decorator.decorate(cell, range, point, lazyContext);
		}
	}

	/**
	 * インスタンスのクローンを作成し返します.
	 *
	 * @return クローン
	 */
	public CompositeCellStyleDecorator clone() {
		CompositeCellStyleDecorator clone = new CompositeCellStyleDecorator();
		clone.getComponents().putAll(getComponents());
		return clone;
	}

}
