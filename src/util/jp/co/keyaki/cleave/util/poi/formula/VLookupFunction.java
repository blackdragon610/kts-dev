package jp.co.keyaki.cleave.util.poi.formula;

import jp.co.keyaki.cleave.util.poi.Range;
import jp.co.keyaki.cleave.util.poi.Referencable;
import jp.co.keyaki.cleave.util.poi.ReferenceUtils;

/**
 * VLOOKUP関数を表現する数式クラス.
 *
 * @author ytakahashi
 */
public class VLookupFunction extends AbstractFunction {

	/**
	 * 式シンボル.
	 */
	public static final String FORMULA_SYMBOL = "VLOOKUP";

	/**
	 * 検索値.
	 */
	private Referencable search;

	/**
	 * 検索範囲.
	 */
	private Range searchRange;

	/**
	 * 結果列番号.
	 */
	private IntegerFormula resultColNo;

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
	 * @param resultColNo 結果列番号
	 * @throws IllegalArgumentException 検索列番号に不正な値を指定した場合
	 */
	public VLookupFunction(Referencable search, Range searchRange, int resultColNo) {
		this(search, searchRange, resultColNo, false);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param search 検索値
	 * @param searchRange 検索範囲
	 * @param resultColNo 結果列番号
	 * @param searchType 検索タイプ
	 * @throws IllegalArgumentException 検索列番号に不正な値を指定した場合
	 */
	public VLookupFunction(Referencable search, Range searchRange, int resultColNo, boolean searchType) {
		if (resultColNo < 1) {
			throw new IllegalArgumentException("結果列番号に1より小さい値を指定できません.");
		}
		if (resultColNo > searchRange.getColCount()) {
			throw new IllegalArgumentException("結果列番号に検索範囲の列数より大きい値を指定できません. Range=" + searchRange.toReferenceStyle() + " 結果列番号=" + resultColNo);
		}
		this.search = search;
		this.searchRange = searchRange;
		this.resultColNo = new IntegerFormula(resultColNo);
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
		return ReferenceUtils.toReferenceStyleJoin(SYMBOL_COMMA, search, searchRange, resultColNo, searchType);
	}
}
