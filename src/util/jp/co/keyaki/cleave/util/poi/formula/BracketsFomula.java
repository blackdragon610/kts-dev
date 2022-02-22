package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.CellFormula;
import jp.co.keyaki.cleave.util.poi.Referencable;

/**
 * 評価優先を決める括弧を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class BracketsFomula implements CellFormula {

	/**
	 * 評価優先度を上げる参照可能オブジェクト
	 */
	private Referencable ref;

	/**
	 * コンストラクタ.
	 *
	 * @param ref 評価優先度を上げる参照可能オブジェクト
	 */
	public BracketsFomula(Referencable ref) {
		this.ref = ref;
	}

	/**
	 * 参照式を返します.
	 *
	 * @return 参照式
	 */
	public String toReferenceStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append(SYMBOL_BRACKETS_OPEN);
		sb.append(ref.toReferenceStyle());
		sb.append(SYMBOL_BRACKETS_CLOSE);
		return sb.toString();
	}
}
