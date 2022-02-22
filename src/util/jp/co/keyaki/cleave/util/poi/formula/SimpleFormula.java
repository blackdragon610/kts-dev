package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.Referencable;

public class SimpleFormula implements Referencable {

	private String formula;

	public SimpleFormula(String formula) {
		this.formula = formula;
	}

	public String toReferenceStyle() {
		return formula;
	}
}
