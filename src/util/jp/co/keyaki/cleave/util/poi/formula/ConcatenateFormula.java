package jp.co.keyaki.cleave.util.poi.formula;

import java.util.ArrayList;
import java.util.List;

import jp.co.keyaki.cleave.util.poi.CellFormula;
import jp.co.keyaki.cleave.util.poi.Range;
import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

/**
 *
 * @author ytakahashi
 *
 */
public class ConcatenateFormula implements CellFormula {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "&";

	/**
	 * 文字列結合対象.
	 */
	private Referencable[] refs = null;

	/**
	 * コンストラクタ.
	 *
	 * <p>
	 * 引数の文字列結合対象に、{@link Range}を指定した場合、
	 * {@link Range#getPoints()}で返された座標オブジェクトを結合対象として展開します.
	 * </p>
	 *
	 * @param refs 文字列結合対象
	 */
	public ConcatenateFormula(Referencable... refs) {
		List<Referencable> args = new ArrayList<Referencable>();
		for (Referencable ref : refs) {
			if (Range.class.isAssignableFrom(ref.getClass())) {
				Range range = Range.class.cast(ref);
				args.addAll(range.getPoints());
			} else {
				args.add(ref);
			}
		}
		this.refs = args.toArray(new Referencable[args.size()]);
	}

	/**
	 *
	 */
	public String toReferenceStyle() {
		return ReferenceUtils.toReferenceStyleJoin(FORMULA_SYMBOL, refs);
	}

}
