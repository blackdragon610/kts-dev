package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.CellFormula;
import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

public class DivideFormula implements CellFormula {

	public static final String FORMULA_SYMBOL = "/";

	private Referencable[] refs = null;
	public DivideFormula(Referencable... refs) {
		this.refs = refs;
	}
	public String toReferenceStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append(ReferenceUtils.toReferenceStyleJoin(FORMULA_SYMBOL, refs));
		return sb.toString();
	}
}
