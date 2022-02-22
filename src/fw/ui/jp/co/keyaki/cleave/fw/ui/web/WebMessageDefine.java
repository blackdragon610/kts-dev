package jp.co.keyaki.cleave.fw.ui.web;

import java.util.ResourceBundle;

import jp.co.keyaki.cleave.fw.core.MessageDefine;

public class WebMessageDefine extends MessageDefine {

	/**
	 * WEBレイヤー識別子
	 */
	public static final char CORE_LAYER_IDENTITY_CHAR = 'W';

	/**
	 * メッセージローカライズ用リソースバンドル.
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(WebMessageDefine.class.getName().concat("Localize"));

	/**
	 * E000001:設定ファイルの記述が正しくないため実行できません。
	 */
	public static final WebMessageDefine E000001 = new WebMessageDefine("E000001");

	/**
	 * コンストラクタ.
	 *
	 * @param inLayerCd
	 *            レイヤー内エラーCD
	 * @param description
	 *            エラー説明
	 */
	protected WebMessageDefine(String inLayerCd) {
		super(inLayerCd, BUNDLE.getString(inLayerCd));
	}

	/**
	 * WEBレイヤー識別子を返します。
	 *
	 * @return WEBレイヤー識別子
	 * @see WebMessageDefine#CORE_LAYER_IDENTITY_CHAR
	 */
	public char getLayerIdentityChar() {
		return CORE_LAYER_IDENTITY_CHAR;
	}

}
