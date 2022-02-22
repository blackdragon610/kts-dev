package jp.co.keyaki.cleave.fw.core;

import java.util.ResourceBundle;

/**
 * 基盤レイヤーメッセージ定義クラス.
 *
 * @author ytakahashi
 *
 */
public class CoreMessageDefine extends MessageDefine {

	/**
	 * コアレイヤー識別子
	 */
	public static final char CORE_LAYER_IDENTITY_CHAR = 'C';

	/**
	 * メッセージローカライズ用リソースバンドル.
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(CoreMessageDefine.class.getName().concat(
			"Localize"));

	/**
	 * E000001:ActionContextの予約済みキーにて登録できません。
	 */
	public static final CoreMessageDefine E000001 = new CoreMessageDefine("E000001");

	/**
	 * E000002:ログイン者情報が無い状態では操作者情報を設定できません。
	 */
	public static final CoreMessageDefine E000002 = new CoreMessageDefine("E000002");

	/**
	 * E000003:権限不足のため実行できません。
	 */
	public static final CoreMessageDefine E000003 = new CoreMessageDefine("E000003");

	/**
	 * E000004:設定ファイルの記述が正しくないため実行できません。
	 */
	public static final CoreMessageDefine E000004 = new CoreMessageDefine("E000004");

	/**
	 * E000005:暗号化に失敗しました。
	 */
	public static final CoreMessageDefine E000005 = new CoreMessageDefine("E000005");

	/**
	 * E000006:復号化に失敗しました。
	 */
	public static final CoreMessageDefine E000006 = new CoreMessageDefine("E000006");

	/**
	 * E000007:JavaBeansのインスタンス生成に失敗しました。
	 */
	public static final CoreMessageDefine E000007 = new CoreMessageDefine("E000007");

	/**
	 * E000008:JavaBeansのプロパティ型の取得に失敗しました。
	 */
	public static final CoreMessageDefine E000008 = new CoreMessageDefine("E000008");

	/**
	 * E000009:JavaBeansのプロパティ値の設定に失敗しました。
	 */
	public static final CoreMessageDefine E000009 = new CoreMessageDefine("E000009");

	/**
	 * E000010:JavaBeansのプロパティ値の取得に失敗しました。
	 */
	public static final CoreMessageDefine E000010 = new CoreMessageDefine("E000010");

	/**
	 * E000011:指定されたクラスの取得に失敗しました。
	 */
	public static final CoreMessageDefine E000011 = new CoreMessageDefine("E000011");

	/**
	 * E000012:指定されたスーパータイプへのCASTに失敗しました。
	 */
	public static final CoreMessageDefine E000012 = new CoreMessageDefine("E000012");

	/**
	 * E000013:フィールドへのアクセスに失敗しました。
	 */
	public static final CoreMessageDefine E000013 = new CoreMessageDefine("E000013");

	/**
	 * E000014:フィールドへのアクセスに失敗しました。
	 */
	public static final CoreMessageDefine E000014 = new CoreMessageDefine("E000014");

	/**
	 * コンストラクタ.
	 *
	 * @param inLayerErrorCd
	 *            レイヤー内エラーCD
	 * @param description
	 *            エラー説明
	 */
	protected CoreMessageDefine(String inLayerErrorCd) {
		super(inLayerErrorCd, BUNDLE.getString(inLayerErrorCd));
	}

	/**
	 * COREレイヤー識別子を返します。
	 *
	 * @return COREレイヤー識別子
	 * @see CoreMessageDefine#CORE_LAYER_IDENTITY_CHAR
	 */
	public char getLayerIdentityChar() {
		return CORE_LAYER_IDENTITY_CHAR;
	}

}
