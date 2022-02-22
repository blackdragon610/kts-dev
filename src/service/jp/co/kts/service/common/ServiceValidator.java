package jp.co.kts.service.common;

import org.apache.commons.validator.GenericValidator;


/**
 * ビジネス層で共通的に利用するチェックロジック.
 *
 * <p>
 * 文字種について
 * <table border="1">
 * <tr>
 * <th>定義名</th><th>備考：例など</th>
 * </tr>
 * <tr>
 * <td>半角数字</td><td>0-9</td>
 * </tr>
 * <tr>
 * <td>半角文字</td><td>0-9,a-z,A-Z,半角スペース,半角カタカナ,半角記号</td>
 * </tr>
 * </table>
 *
 * </p>
 *
 */
public class ServiceValidator {

//	/**
//	 * 正規表現：半角(数字).
//	 */
//	private static final Pattern REGEXP_HALFWIDTH_NUMERIC = Pattern.compile("^[0-9]*$");
//
//	/**
//	 * 正規表現：半角(数字ハイフン).
//	 */
//	private static final Pattern REGEXP_HALFWIDTH_NUMERIC_HYPHEN = Pattern.compile("^[0-9-]*$");
//
//	/**
//	 * 正規表現：半角(英数字).
//	 */
//	private static final Pattern REGEXP_HALFWIDTH_ALPHA_NUMERIC = Pattern.compile("^[a-zA-Z0-9]*$");
//
//	/**
//	 * 正規表現：半角(英数字ハイフン).
//	 */
//	private static final Pattern REGEXP_HALFWIDTH_ALPHA_NUMERIC_HYPHEN = Pattern.compile("^[a-zA-Z0-9-]*$");
//
//	/**
//	 * 正規表現：デフォルト日付書式.
//	 */
//	private static final Pattern REGEXP_DEFAULT_DAY = Pattern.compile("^[0-9]{4}[0-9]{2}[0-9]{2}$");
//
//	/**
//	 * 正規表現：注文日付書式.
//	 */
//	private static final Pattern REGEXP_SALES_DATE =Pattern.compile("^[0-9]{4}/[0-9]{2}/[0-9]{2}[ ]{1}[0-9]{2}[:]{1}[0-9]{2}$");


	/**
	 * 文字数入力チェックメソッド(文字数何文字以下指定Var)
	 * @param errors
	 * @param presentCompanyCd
	 * @return
	 */
	public static void inputChecker(Result<?> result, String value, String caption, int length, boolean required) {

		if (required && GenericValidator.isBlankOrNull(value)) {
			result.addErrorMessage("LED00101", caption);
		} else {
			if (value != null) {
				if (value.length() > length) {
					result.addErrorMessage("LED00103", caption, String.valueOf(length));
				}
			}
		}
	}

	/**
	 * 文字数入力チェックメソッド(文字数何文字以下指定Var)
	 * @param result
	 * @param value
	 * @param caption
	 * @param length
	 */
	public static void inputChecker(Result<?> result, String value, String caption, int length) {

		if (GenericValidator.isBlankOrNull(value)) {
			return;
		} else {
			if (value != null) {
				if (value.length() > length) {
					result.addErrorMessage("LED00103", caption, String.valueOf(length));
				}
			}
		}
	}


	/**
	 * 文字数入力チェックメソッド(文字数指定Var)
	 * @param errors
	 * @param presentCompanyCd
	 * @return
	 */
	public static void equalLengthChecker(Result<?> result, String value, String caption, int length) {

		if (GenericValidator.isBlankOrNull(value)) {
			return;
		} else {
			if (value != null) {
				if (value.length() != length) {
					result.addErrorMessage("LED00107", caption, String.valueOf(length));
				}
			}
		}
	}

	/**
	 * 必須項目のチェックを行います。
	 * @param result
	 * @param value
	 * @param caption
	 * @param length
	 */
	public static void requiredChecker(Result<?> result, String value, String caption) {

		if (GenericValidator.isBlankOrNull(value)) {
			result.addErrorMessage("LED00101", caption);
		}
	}

	/**
	 *  日付の妥当性のチェック
	 * @param errors
	 * @param value
	 * @param caption
	 * @param required
	 * @return
	 */
	public static void checkDate(Result<?> result, String value, String caption) {

		if (GenericValidator.isBlankOrNull(value)) {
			return;
		} else {
			if (!GenericValidator.isDate(value, ServiceConst.DATE_FORMAT, true)) {
				result.addErrorMessage("LED00105", caption);
			}
		}
	}

	/**
	 * 数字のチェック(Double型でチェックしているのは、エクセルから数値はDouble型としてくるため)
	 * @param errors
	 * @param value
	 * @param caption
	 * @return
	 * @throws Exception
	 */
	public static void checkDouble(Result<?> result, String value, String caption) throws Exception {

		if (GenericValidator.isBlankOrNull(value)) {
			return;
		}

		if (!GenericValidator.isDouble(value)) {
			result.addErrorMessage("LED00106", caption);
		}
	}

	/**
	 * [在庫数チェック用]正の数値が入力されているかチェックします。
	 * (Double型でチェックしているのは、エクセルから数値はDouble型としてくるため)
	 *
	 * @param errors
	 * @param value
	 * @param caption
	 * @return
	 * @throws Exception
	 */
	public static void excelStockCheck(Result<?> result, String value, String caption) throws Exception {

		if (GenericValidator.isBlankOrNull(value)) {
			return;
		}

		if (!GenericValidator.isDouble(value)) {
			result.addErrorMessage("LED00106", caption);
		} else {
			if (Double.parseDouble(value) < 0) {
				result.addErrorMessage("LED00108", caption);
			}
		}
	}

	public static void strMatchesCheck(Result<?> result , String value, String caption, String targetNm) throws Exception {
		if (GenericValidator.isBlankOrNull(value)) {
			return;
		}
		if (!value.matches(("^[a-zA-Z0-9-]*$"))) {
			result.addErrorMessage("LED00140", caption, targetNm);
		}
	}

	//	国内出品DBインポート時、管理品番のみ"/"を有効にするため別に作成。上のに付け足すと、メーカー品番まで"/"が通ってしまうため。
	public static void strMatchesCheckSlash(Result<?> result , String value, String caption, String targetNm) throws Exception {
		if (GenericValidator.isBlankOrNull(value)) {
			return;
		}
		if (!value.matches(("^[a-zA-Z0-9-/]*$"))) {
			result.addErrorMessage("LED00140", caption, targetNm);
		}
	}

	public static void strMatchesCheckHypDot(Result<?> result , String value, String caption, String targetNm) throws Exception {

		if (GenericValidator.isBlankOrNull(value)) {
			return;
		}

		if (!value.matches(("^[0-9A-Za-z-. ]*$"))) {
			result.addErrorMessage("LED00148", caption, targetNm);
		}
	}

}
