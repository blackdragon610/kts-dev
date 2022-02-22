package jp.co.keyaki.cleave.fw.core.security;

import java.math.BigInteger;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 単純な権限を表すクラス.
 *
 * @author ytakahashi
 *
 */
public class SimplePermissionInfo implements PermissionInfo {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 権限のビット表現.
	 */
	private BigInteger permissionBits = BigInteger.ZERO;

	/**
	 * 権限コード.
	 */
	private String permissionCd;

	/**
	 * 権限名.
	 */
	private String permissionName;

	/**
	 * コンストラクタ.
	 *
	 * @param permissionNo 権限No
	 * @param description 権限名
	 * @throws IllegalArgumentException 権限Noが1より小さい値を指定した場合
	 * @throws NullPointerException 権限名にnullが指定された場合
	 */
	public SimplePermissionInfo(int permissionNo, String permissionCd, String permissionName) {
		if (permissionNo < 1) {
			throw new IllegalArgumentException("permissionNo に 1 より小さい値は指定できません。");
		}
		if (permissionCd == null) {
			throw new NullPointerException("permissionCd に null は指定できません。");
		}
		if (permissionName == null) {
			throw new NullPointerException("permissionName に null は指定できません。");
		}
		this.permissionBits = BigInteger.ZERO.setBit(permissionNo - 1);
		this.permissionCd = permissionCd;
		this.permissionName = permissionName;
	}

	/**
	 * このオブジェクトと指定されたオブジェクトの順序を比較します.
	 *
	 * <p>
	 * 順序については権限を数値化した値を利用します.
	 * 権限の強さにて順序付けするわけではありません.
	 * </p>
	 *
	 * @return このオブジェクトが指定されたオブジェクトより小さい場合は負の整数、等しい場合はゼロ、大きい場合は正の整数
	 */
	public int compareTo(PermissionInfo o) {
		return value().compareTo(o.value());
	}

	/**
	 * 引数の権限を保有する権限かを判定します.
	 *
	 * @param permissionInfo 保有判定権限
	 * @return true:権限を有する/false:権限を有しない
	 */
	public boolean contains(PermissionInfo permissionInfo) {
		return getPermissionSet().containsAll(permissionInfo.getPermissionSet());
	}

	/**
	 * 権限を数値化して返します.
	 *
	 * <p>
	 * この戻り値は、権限Noを返すわけではありません.
	 * </p>
	 *
	 * @return 数値化した権限
	 */
	public BigInteger value() {
		return permissionBits;
	}

	/**
	 * ハッシュ値を返します.
	 *
	 * @return ハッシュ値
	 */
	@Override
	public int hashCode() {
		return permissionBits.hashCode();
	}

	/**
	 * 権限の同値性を判定し返します.
	 *
	 * @param obj 比較対象
	 * @return true:同値/false:不一致
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!getClass().isAssignableFrom(object.getClass())) {
			return false;
		}
		SimplePermissionInfo other = getClass().cast(object);
		return value().equals(other.value());
	}

	/**
	 * このオブジェクトの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	@Override
	public String toString() {
		return permissionBits.toString(16) + ":" + permissionCd + ":" + permissionName;
	}

	/**
	 * 権限Noを返します.
	 *
	 * @return
	 */
	public int getPermissionNo() {
		return permissionBits.getLowestSetBit() + 1;
	}

	/**
	 * 権限コードを返します.
	 *
	 * @return 権限コード
	 */
	public String getPermissionCd() {
		return permissionCd;
	}

	/**
	 * 権限名を返します.
	 *
	 * @return 権限名
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * ネスト構造および重複を解除した権限セットを返します.
	 *
	 * @param permissionInfo 処理対象権限
	 * @return 権限セット.
	 */
	public SortedSet<PermissionInfo> getPermissionSet() {
		SortedSet<PermissionInfo> permissionSet = new TreeSet<PermissionInfo>();
		permissionSet.add(this);
		return permissionSet;
	}

}
