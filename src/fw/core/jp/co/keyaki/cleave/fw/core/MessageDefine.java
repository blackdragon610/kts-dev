package jp.co.keyaki.cleave.fw.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

/**
 * メッセージ定義クラス.
 *
 * @author ytakahashi
 */
public abstract class MessageDefine implements Cloneable {

	/**
	 * 定義済みメッセージ.
	 */
	private static final Set<MessageDefine> DEFINE_MESSAGES = new HashSet<MessageDefine>();

	private static final Object[] EMPTY_ARRAY = new Object[]{};

	/**
	 * メッセージコード.
	 */
	private String code;

	/**
	 * メッセージ.
	 */
	private String description;

	private Object[] args = EMPTY_ARRAY;

	/**
	 * コンストラクタ.
	 *
	 * @param inLayerCd レイヤー内メッセージコード
	 * @param description メッセージ
	 */
	protected MessageDefine(String inLayerCd, String description) {
		this.code = new Character(getLayerIdentityChar()).toString().concat(inLayerCd);
		this.description = description;
		if (DEFINE_MESSAGES.contains(this)) {
			throw new RuntimeException("メッセージコードが重複しています。" + toString());
		}
		DEFINE_MESSAGES.add(this);
	}

	/**
	 * コピー用コンストラクタ.
	 *
	 * @param source コピー元
	 */
	protected MessageDefine(MessageDefine source) {
		this.code = source.code;
		this.description = source.description;
	}

	/**
	 * メッセージコードを返します.
	 *
	 * @return メッセージコード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * メッセージを返します.
	 *
	 * @return メッセージ
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * ハッシュ値を返します.
	 *
	 * @return ハッシュ値
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/**
	 * 同値性を評価します.
	 *
	 * @return true:同値/false:非同値
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!getClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		MessageDefine other = getClass().cast(obj);
		return ObjectUtils.equals(code, other.code);
	}

	/**
	 * オブジェクトの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	@Override
	public String toString() {
		String result = "[" + getCode() + "]:" + getDescription();
		if (args != null && args.length > 0) {
			result = result + " (" + Arrays.toString(args) + ")";
		}
		return  result;
	}

	/**
	 * メッセージオブジェクトに付加情報を追加します.
	 *
	 * <p>
	 * ただしこのメソッドは、インスタンスの変更は実施せず新規にクローンインスタンスを作成し
	 * クローンインスタンスに対して付加情報を追加した上で、戻り値としてクローンインスタンスを返却します.
	 * </p>
	 *
	 * @param args 付加情報
	 * @return 付加情報を保持したクローンインスタンス
	 */
	public MessageDefine setArgs(Object... args) {
		MessageDefine clone = clone();
		clone.args = args;
		return clone;
	}

	/**
	 * 保持している付加情報を返します.
	 *
	 * @return 付加情報
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * クローンインスタンスを作成します.
	 *
	 * @return クローン
	 */
	protected MessageDefine clone() {
		MessageDefine clone = null;
		try {
			clone = MessageDefine.class.cast(super.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		clone.args = args.clone();
		return clone;
	}

	/**
	 * メッセージ定義レイヤーを識別子を返します.
	 *
	 * @return メッセージ定義レイヤー識別子
	 */
	public abstract char getLayerIdentityChar();

}
