package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.Range;
import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

/**
 * HLOOKUP関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class HLookupFunction extends AbstractFunction {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "HLOOKUP";

	/**
	 * 検索値.
	 */
	private Referencable search;

	/**
	 * 検索範囲.
	 */
	private Range searchRange;

	/**
	 * 結果行番号.
	 */
	private IntegerFormula resultRowNo;

	/**
	 * 検索タイプ.
	 */
	private BooleanFormula searchType;

	/**
	 * コンストラクタ.
	 *
	 * <p>
	 * このコンストラクタは、検索タイプを{@code false}で作成します.
	 * </p>
	 *
	 * @param search 検索値
	 * @param searchRange 検索範囲
	 * @param resultRowNo 結果行番号
	 * @throws IllegalArgumentException 検索行番号に不正な値を指定した場合
	 */
	public HLookupFunction(Referencable search, Range searchRange, int resultRowNo) {
		this(search, searchRange, resultRowNo, false);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param search 検索値
	 * @param searchRange 検索範囲
	 * @param resultRowNo 結果行番号
	 * @param searchType 検索タイプ
	 * @throws IllegalArgumentException 検索行番号に不正な値を指定した場合
	 */
	public HLookupFunction(Referencable search, Range searchRange, int resultRowNo, boolean searchType) {
		if (resultRowNo < 1) {
			throw new IllegalArgumentException("結果行番号に1より小さい値を指定できません.");
		}
		if (resultRowNo > searchRange.getRowCount()) {
			throw new IllegalArgumentException("結果行番号に検索範囲の列数より大きい値を指定できません. Range=" + searchRange.toReferenceStyle() + " 結果行番号=" + resultRowNo);
		}
		this.search = search;
		this.searchRange = searchRange;
		this.resultRowNo = new IntegerFormula(resultRowNo);
		this.searchType = new BooleanFormula(searchType);
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
		return ReferenceUtils.toReferenceStyleJoin(SYMBOL_COMMA, search, searchRange, resultRowNo, searchType);
	}
}
