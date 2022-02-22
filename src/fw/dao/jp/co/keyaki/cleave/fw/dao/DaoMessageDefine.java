package jp.co.keyaki.cleave.fw.dao;

import java.util.ResourceBundle;

import jp.co.keyaki.cleave.fw.core.MessageDefine;

/**
 * DAO層のメッセージ定義クラス.
 *
 * @author ytakahashi
 */
public class DaoMessageDefine extends MessageDefine {

	/**
	 * DAOレイヤー識別子
	 */
	public static final char CORE_LAYER_IDENTITY_CHAR = 'D';

	/**
	 * メッセージローカライズ用リソースバンドル.
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(DaoMessageDefine.class.getName().concat("Localize"));

	/**
	 * E000001:DatabaseDriverClassを取得できません。
	 */
	public static final DaoMessageDefine E000001 = new DaoMessageDefine("E000001");

	/**
	 * E000002:DriverManagerよりConnectionを取得できません。
	 */
	public static final DaoMessageDefine E000002 = new DaoMessageDefine("E000002");

	/**
	 * E000003:ConnectionResourceのインスタンスが生成できません。
	 */
	public static final DaoMessageDefine E000003 = new DaoMessageDefine("E000003");

	/**
	 * E000004:ConnectionResourceの初期化処理に失敗しました。
	 */
	public static final DaoMessageDefine E000004 = new DaoMessageDefine("E000004");

	/**
	 * E000005:Rollback処理に失敗しました。
	 */
	public static final DaoMessageDefine E000005 = new DaoMessageDefine("E000005");

	/**
	 * E000006:Commit処理に失敗しました。
	 */
	public static final DaoMessageDefine E000006 = new DaoMessageDefine("E000006");

	/**
	 * E000007:ConnectionRelease処理に失敗しました。
	 */
	public static final DaoMessageDefine E000007 = new DaoMessageDefine("E000007");

	/**
	 * E000008:トランザクション開始に失敗しました。
	 */
	public static final DaoMessageDefine E000008 = new DaoMessageDefine("E000008");

	/**
	 * E000009:SQL発行に失敗しました。
	 */
	public static final DaoMessageDefine E000009 = new DaoMessageDefine("E000009");

	/**
	 * E000010:InitialContextの初期化に失敗しました。
	 */
	public static final DaoMessageDefine E000010 = new DaoMessageDefine("E000010");

	/**
	 * E000011:ネーミングサービスよりLookupに失敗しました。
	 */
	public static final DaoMessageDefine E000011 = new DaoMessageDefine("E000011");

	/**
	 * E000012:DataSourceよりコネクションの取得に失敗しました。
	 */
	public static final DaoMessageDefine E000012 = new DaoMessageDefine("E000012");

	/**
	 * E000013:PreparedStatementオブジェクトの生成に失敗しました。
	 */
	public static final DaoMessageDefine E000013 = new DaoMessageDefine("E000013");

	/**
	 * E000014:バインド変数の設定に失敗しました。
	 */
	public static final DaoMessageDefine E000014 = new DaoMessageDefine("E000014");

	/**
	 * E000015:ResultSet操作に失敗しました。
	 */
	public static final DaoMessageDefine E000015 = new DaoMessageDefine("E000015");

	/**
	 * E000016:ResultSetMetaDataの取得に失敗しました。
	 */
	public static final DaoMessageDefine E000016 = new DaoMessageDefine("E000016");

	/**
	 * E000017:ResultSetのクローズ処理に失敗しました。
	 */
	public static final DaoMessageDefine E000017 = new DaoMessageDefine("E000017");

	/**
	 * E000018:Statementのクローズ処理に失敗しました。
	 */
	public static final DaoMessageDefine E000018 = new DaoMessageDefine("E000018");

	/**
	 * E000019:SQLIDが見つかりません。
	 */
	public static final DaoMessageDefine E000019 = new DaoMessageDefine("E000019");

	/**
	 * E000020:SQLで必要なバインドパラメータが取得できません。
	 */
	public static final DaoMessageDefine E000020 = new DaoMessageDefine("E000020");

	/**
	 * E000021:sql-templateの解析に失敗しました。
	 */
	public static final DaoMessageDefine E000021 = new DaoMessageDefine("E000021");

	/**
	 * E000022:sql-templateの変換に失敗しました。
	 */
	public static final DaoMessageDefine E000022 = new DaoMessageDefine("E000022");

	/**
	 * E000023:URLからストリームの取得に失敗しました。
	 */
	public static final DaoMessageDefine E000023 = new DaoMessageDefine("E000023");

	/**
	 * E000024:XSL変換準備に失敗しました。
	 */
	public static final DaoMessageDefine E000024 = new DaoMessageDefine("E000024");

	/**
	 * E000025:指定されたコネクション番号は存在しません。
	 */
	public static final DaoMessageDefine E000025 = new DaoMessageDefine("E000025");

	/**
	 * E000026:JavaBeanからバインド変数の抽出に失敗しました。
	 */
	public static final DaoMessageDefine E000026 = new DaoMessageDefine("E000026");

	/**
	 * コンストラクタ.
	 *
	 * @param inLayerCd
	 *            レイヤー内エラーCD
	 * @param description
	 *            エラー説明
	 */
	protected DaoMessageDefine(String inLayerCd) {
		super(inLayerCd, BUNDLE.getString(inLayerCd));
	}

	/**
	 * COREレイヤー識別子を返します。
	 *
	 * @return COREレイヤー識別子
	 * @see DaoMessageDefine#CORE_LAYER_IDENTITY_CHAR
	 */
	public char getLayerIdentityChar() {
		return CORE_LAYER_IDENTITY_CHAR;
	}

}
