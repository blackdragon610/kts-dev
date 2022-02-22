package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

public class EqualsFormula implements BooleableFormula {

	public static final String FORMULA_SYMBOL = "=";

	private Referencable obj1;
	private Referencable obj2;

	public EqualsFormula(Referencable obj1, Referencable obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	public String toReferenceStyle() {
		return ReferenceUtils.toReferenceStyleJoin(FORMULA_SYMBOL, obj1, obj2);
	}
}
