package jp.co.keyaki.cleave.fw.core.security;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 権限を組み合わせた合成権限を表すクラス.
 *
 * <p>
 * このオブジェクトの同値は構成権限の構造は意識せず、同じ権限を参照している場合は同値として判断する.
 * <p>例</p>
 * <pre>
 * <code>
 * SimplePermissionInfo s1 = ...;
 * SimplePermissionInfo s2 = ...;
 *
 * CompositePermissionInfo c1 = new CompositePermissionInfo(s1, s2);
 * CompositePermissionInfo c2 = new CompositePermissionInfo(s1, new CompositePermissionInfo(s2));
 * CompositePermissionInfo c3 = new CompositePermissionInfo(s1, new CompositePermissionInfo(s1, s2));
 *
 * c1.equals(c2); '==> true
 * c1.equals(c3); '==> true
 * </code>
 * </pre>
 *
 * </p>
 *
 * @author ytakahashi
 *
 */
public class CompositePermissionInfo implements PermissionInfo {

	/**
	 * シリアルバージョン.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 構成権限.
	 */
	private SortedSet<PermissionInfo> permissionInfos;

	/**
	 * コンストラクタ.
	 *
	 * @param permissionInfos 構成権限
	 */
	public CompositePermissionInfo(PermissionInfo... permissions) {
		this(Arrays.asList(permissions));
	}

	/**
	 * コンストラクタ.
	 *
	 * @param permissionInfos 構成権限
	 */
	public CompositePermissionInfo(Collection<PermissionInfo> permissionInfos) {
		this.permissionInfos = Collections.unmodifiableSortedSet(new TreeSet<PermissionInfo>(permissionInfos));
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

		return getPermissionSet().containsAll(getPermissionSet(permissionInfo));

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
		BigInteger value = BigInteger.ZERO;
		for (PermissionInfo permissionInfo : getPermissionSet()) {
			value = value.add(permissionInfo.value());
		}
		return value;
	}

	/**
	 * ハッシュ値を返します.
	 *
	 * @return ハッシュ値
	 */
	public int hashCode() {
		return getPermissionSet().hashCode();
	}

	/**
	 * 権限の同値性を判定し返します.
	 *
	 * @param obj 比較対象
	 * @return true:同値/false:不一致
	 * @see CompositePermissionInfo
	 */
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
		CompositePermissionInfo other = getClass().cast(object);
		return getPermissionSet().equals(other.getPermissionSet());
	}

	/**
	 * このオブジェクトの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	@Override
	public String toString() {
		return permissionInfos.toString();
	}

	/**
	 * ネスト構造および重複を解除した権限セットを返します.
	 *
	 * @return 権限セット.
	 */
	public SortedSet<PermissionInfo> getPermissionSet() {
		return getPermissionSet(this);
	}

	/**
	 * ネスト構造および重複を解除した権限セットを返します.
	 *
	 * @param permissionInfo 処理対象権限
	 * @return 権限セット.
	 */
	protected static SortedSet<PermissionInfo> getPermissionSet(PermissionInfo permissionInfo) {
		SortedSet<PermissionInfo> permissionSet = new TreeSet<PermissionInfo>();
		if (CompositePermissionInfo.class.isAssignableFrom(permissionInfo.getClass())) {
			CompositePermissionInfo composite = CompositePermissionInfo.class.cast(permissionInfo);
			for (PermissionInfo composition : composite.permissionInfos) {
				permissionSet.addAll(getPermissionSet(composition));
			}
		} else {
			permissionSet.add(permissionInfo);
		}
		return permissionSet;
	}

}
