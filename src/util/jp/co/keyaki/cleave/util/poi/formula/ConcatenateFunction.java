package jp.co.keyaki.cleave.util.poi.formula;

import java.util.ArrayList;
import java.util.List;

import jp.co.keyaki.cleave.util.poi.Range;
import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

/**
 * CONCATENATE関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class ConcatenateFunction extends AbstractFunction {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "CONCATENATE";

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
	public ConcatenateFunction(Referencable... refs) {
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
	 * 関数名を返します.
	 *
	 * @return 関数名
	 */
	@Override
	public String getFunctionName() {
		return FORMULA_SYMBOL;
	}

	/**
	 * 引数式を返します.
	 *
	 * @return 引数式
	 */
	@Override
	public String getArgs() {
		return ReferenceUtils.toReferenceStyleJoin(SYMBOL_COMMA, refs);
	}

}
