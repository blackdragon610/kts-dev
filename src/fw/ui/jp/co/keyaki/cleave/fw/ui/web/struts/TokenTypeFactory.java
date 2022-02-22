package jp.co.keyaki.cleave.fw.ui.web.struts;

import java.util.HashMap;
import java.util.Map;

/**
 * トークンタイプを設定するクラス
 *
 * @author ytakahashi
 *
 */
public class TokenTypeFactory {

	/** トークン処理タイプ */
	private static Map<String, TokenType> tokenTypes = new HashMap<String, TokenType>();

	static {
		tokenTypes.put("nocheck", new NoCheckTokenType());
		tokenTypes.put("start", new StartTokenType());
		tokenTypes.put("check", new CheckTokenType());
		//サブウインドウ用に追加
		tokenTypes.put("subWindow", new subWindowTokenType());
		tokenTypes.put("through", null);
	}

	/**
	 * トークン処理タイプを取得する
	 *
	 * @param tokenType
	 *            トークンタイプ
	 * @return トークン処理タイプ
	 */
	public static TokenType getTokenType(String tokenType) {
		return tokenTypes.get(tokenType);
	}

}
