/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * クラスをキーとしたインスタンスを保持するコンポジットクラス.
 *
 * <p>
 * コンポジットクラス自体を登録しようとした場合、コンポーネントを展開し上書きした形で登録します.
 * </p>
 *
 * @author ytakahashi
 *
 * @param <E> コンポーネント
 */
public abstract class IdenticalClassComposite<E> implements Cloneable {

	/**
	 * コンポーネント.
	 */
	private Map<Class<E>, E> components = new LinkedHashMap<Class<E>, E>();

	/**
	 * 変更不可設定.
	 */
	private boolean isUnmodifiable = false;

	/**
	 * コンストラクタ.
	 */
	public IdenticalClassComposite() {

	}

	/**
	 * 変更不可設定されているか返します.
	 *
	 * @return true:変更不可/false:変更可
	 */
	public boolean isUnmodifiable() {
		return isUnmodifiable;
	}

	/**
	 * 変更不可状態に設定します.
	 *
	 */
	public void setUnmodifiable() {
		this.isUnmodifiable = true;
	}

	/**
	 * コンポーネントを返します.
	 *
	 * @return クラスをキーとしたコンポーネント
	 */
	protected Map<Class<E>, E> getComponents() {
		return components;
	}

	/**
	 * コンポーネントを登録します.
	 *
	 * <p>
	 * コンポーネントとして派生クラスを登録しようとした場合、コンポジットが抱えているコンポーネントを展開した形で登録します.
	 * またキーとしてすでに登録されている場合は上書きされます.
	 * </p>
	 *
	 * @param element 登録するコンポーネント
	 */
	@SuppressWarnings("unchecked")
	public void add(E element) {
		if (isUnmodifiable()) {
			throw new UnsupportedOperationException("変更不可に設定されています。cloneを取得して変更を実施してください。");
		}
		if (element == null) {
			return;
		}
		if (IdenticalClassComposite.class.isAssignableFrom(element.getClass())) {
			IdenticalClassComposite<E> composite = IdenticalClassComposite.class.cast(element);
			for (E elem : composite.components.values()) {
				add(elem);
			}
			return;
		}
		components.put((Class<E>)element.getClass(), element);
	}

	/**
	 * ハッシュ値を返します.
	 *
	 * @return ハッシュ値
	 */
	public int hashCode() {
		return components.hashCode();
	}

	/**
	 * オブジェクトの同値性を判定します.
	 *
	 * <p>
	 * このクラスの同値性は下記のフィールドを利用して判断します.
	 * <ul>
	 * <li>コンポーネント</li>
	 * </ul>
	 * つまり内部に抱えているコンポーネントが同一かを判断することとなります.
	 * </p>
	 *
	 * @param obj 比較対象
	 * @return true:同値/false:同値ではない
	 *
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(getClass().isAssignableFrom(obj.getClass()))) {
			return false;
		}
		IdenticalClassComposite<E> same = IdenticalClassComposite.class.cast(obj);
		return components.equals(same.components);
	}

	/**
	 * インスタンスのクローンを作成し返します.
	 *
	 * @return クローン
	 */
	public abstract E clone();
}
