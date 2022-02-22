package jp.co.keyaki.cleave.util.poi;

/**
 * 参照式作成用ユーティリティクラス.
 *
 * @author ytakahashi
 *
 */
public class ReferenceUtils {

	/**
	 * 参照式を作成し返します.
	 *
	 * @param separator 各参照式を結合する際のセパレーター
	 * @param refs 参照可能オブジェクト
	 * @return 参照式
	 */
	public static String toReferenceStyleJoin(String separator, Referencable... refs) {
		StringBuilder sb = new StringBuilder();
		for (Referencable ref : refs) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			sb.append(ref.toReferenceStyle());
		}
		return sb.toString();
	}
}
