package jp.co.keyaki.cleave.fw.service;

import java.util.ResourceBundle;

import jp.co.keyaki.cleave.fw.core.MessageDefine;

/**
 * サービス層のメッセージ定義クラス.
 *
 * @author ytakahashi
 */
public class ServiceMessageDefine extends MessageDefine {

	/**
	 * サービスレイヤー識別子
	 */
	public static final char SERVICE_LAYER_IDENTITY_CHAR = 'S';

	/**
	 * メッセージローカライズ用リソースバンドル.
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(ServiceMessageDefine.class.getName().concat("Localize"));

	/**
	 * E000001:予期せぬ例外が発生しました。
	 */
	public static final ServiceMessageDefine E000001 = new ServiceMessageDefine("E000001");

	/**
	 * コンストラクタ.
	 *
	 * @param inLayerCd
	 *            レイヤー内エラーCD
	 * @param description
	 *            エラー説明
	 */
	protected ServiceMessageDefine(String inLayerCd) {
		super(inLayerCd, BUNDLE.getString(inLayerCd));
	}

	/**
	 * メッセージ定義レイヤーを識別子を返します.
	 *
	 * @return メッセージ定義レイヤー識別子
	 */
	@Override
	public char getLayerIdentityChar() {
		return SERVICE_LAYER_IDENTITY_CHAR;
	}

}
