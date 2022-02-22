package jp.co.keyaki.cleave.util.poi.style;

import org.apache.poi.ss.usermodel.CellStyle;

import jp.co.keyaki.cleave.util.poi.CompositeCellStyleLazyDecorator;

public class StyleParser {

	public static CompositeCellStyleLazyDecorator parse(CellStyle style) {
		CompositeCellStyleLazyDecorator decorator = new CompositeCellStyleLazyDecorator();

		return decorator;
	}
}
