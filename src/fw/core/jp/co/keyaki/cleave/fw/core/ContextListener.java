package jp.co.keyaki.cleave.fw.core;

/**
 * コンテキスト操作リスナー
 *
 * @author ytakahashi
 *
 */
public interface ContextListener {

	/**
	 * コンテキストへ登録されたときに呼び出されるメソッド.
	 *
	 * @param registKey 登録キー
	 */
	void handleAdded(String registKey);

	/**
	 * コンテキストから削除されたときに呼び出されるメソッド.
	 *
	 * @param removetKey 登録キー
	 */
	void handleRemoved(String removetKey);

}
