package jp.co.keyaki.cleave.fw.core.security;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.SortedSet;

/**
 * 権限を表現するインターフェース.
 *
 * @author ytakahashi
 *
 */
public interface PermissionInfo extends Serializable, Comparable<PermissionInfo> {

	/**
	 * 引数の権限を保有する権限かを判定します.
	 *
	 * @param permissionInfo 保有判定権限
	 * @return true:権限を有する/false:権限を有しない
	 */
	public boolean contains(PermissionInfo permissionInfo);

	/**
	 * 権限を数値化して返します.
	 *
	 * @return 数値化した権限
	 */
	public BigInteger value();

	/**
	 * ハッシュ値を返します.
	 *
	 * @return ハッシュ値
	 */
	public int hashCode();

	/**
	 * 権限の同値性を判定し返します.
	 *
	 * @param obj 比較対象
	 * @return true:同値/false:不一致
	 */
	public boolean equals(Object obj);

	/**
	 * ネスト構造および重複を解除した権限セットを返します.
	 *
	 * @return 権限セット.
	 */
	public SortedSet<PermissionInfo> getPermissionSet();

}

