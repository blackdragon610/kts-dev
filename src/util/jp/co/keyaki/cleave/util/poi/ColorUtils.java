/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 色に関するユーティリティークラス.
 *
 * @author ytakahashi
 *
 */
public class ColorUtils {

	/**
	 * 色インデックスをキーにしたキャッシュ
	 */
	private static Map<Short, Color> cacheIndex = new HashMap<Short, ColorUtils.Color>();

	/**
	 * 色名をキーにしたキャッシュ.
	 */
	private static Map<String, Color> cacheName = new HashMap<String, ColorUtils.Color>();
	static {
		cacheIndex.clear();
		cacheName.clear();
		for (IndexedColors colors : IndexedColors.values()) {
			Color color = new Color(colors.getIndex(), colors.name());
			cacheIndex.put(color.getIndex(), color);
			cacheName.put(color.getName(), color);
		}
	}

	/**
	 * 色情報クラス.
	 *
	 * @author ytakahashi
	 */
	public static class Color implements Comparable<Color> {

		/**
		 * 色インデックス.
		 */
		private short index;

		/**
		 * 色名.
		 */
		private String name;

		/**
		 * コンストラクタ.
		 *
		 * @param index 色インデックス
		 * @param name 色名
		 */
		protected Color(short index, String name) {
			this.index = index;
			this.name = name;
		}

		/**
		 * 色インデックスを返します.
		 *
		 * @return 色インデックス
		 */
		public short getIndex() {
			return index;
		}

		/**
		 * 色名を返します.
		 *
		 * @return 色名
		 */
		public final String getName() {
			return name;
		}

		/**
		 * オブジェクトの文字列表現を返します.
		 *
		 * @return 文字列表現
		 */
		public String toString() {
			return ReflectionToStringBuilder.toString(this);
		}

		/**
		 * オブジェクトの同値性を判定します.
		 *
		 * <p>
		 * このクラスの同値性は下記のフィールドを利用して判断します.
		 * <ul>
		 * <li>色インデックス</li>
		 * </ul>
		 * つまり、色名については同値性の判断基準にはなりません.
		 * </p>
		 * @param obj 比較対象
		 * @return true:同値/false:同値ではない
		 */
		public final boolean equals(Object other) {
			if (other == this) {
				return true;
			}
			if (other == null) {
				return false;
			}
			if (!Color.class.isAssignableFrom(other.getClass())) {
				return false;
			}
			Color same = Color.class.cast(other);
			return index == same.index;
		}

		/**
		 * ハッシュ値を返します.
		 *
		 * @return ハッシュ値
		 */
		public final int hashCode() {
			return index;
		}

		/**
		 * 自然順序で比較し返します.
		 *
		 * <p>
		 * インデックス値を利用して比較します.
		 * </P>
		 *
		 * @return マイナス値:このオブジェクトの方が小さい/0:同一インデックス/プラス値;このオブジェクトの方が大きい
		 */
		public final int compareTo(Color o) {
			return index - o.index;
		}

	}

	/**
	 * ブラック.
	 */
	public static final Color BLACK = getCacheColor(IndexedColors.BLACK.getIndex());

	/**
	 * イエロー.
	 */
	public static final Color YELLOW = getCacheColor(IndexedColors.YELLOW.getIndex());

	/**
	 * ライトイエロー.
	 */
	public static final Color LIGHT_YELLOW = getCacheColor(IndexedColors.LIGHT_YELLOW.getIndex());

	/**
	 * ライトグリーン.
	 */
	public static final Color LIGHT_GREEN = getCacheColor(IndexedColors.LIGHT_GREEN.getIndex());

	/**
	 * ペイルブルー.
	 */
	public static final Color PALE_BLUE = getCacheColor(IndexedColors.PALE_BLUE.getIndex());

	/**
	 * ライトターコイズ
	 */
	public static final Color LIGHT_TURQUOISE = getCacheColor(IndexedColors.LIGHT_TURQUOISE.getIndex());

	/**
	 * タン
	 */
	public static final Color TAN = getCacheColor(IndexedColors.TAN.getIndex());

	/**
	 * グリーン50%
	 */
	public static final Color GREY_50_PERCENT = getCacheColor(IndexedColors.GREY_50_PERCENT.getIndex());

	/**
	 * 自動色
	 */
	public static final Color AUTOMATIC = getCacheColor(IndexedColors.AUTOMATIC.getIndex());

	/**
	 * 内部定義済み色オブジェクトを返します.
	 *
	 * @param colorIndex 取得する色インデックス
	 * @return 色オブジェクト 未定義色インデックスを指定された場合はnull
	 * @see IndexedColors
	 */
	public static Color getCacheColor(short colorIndex) {
		return cacheIndex.get(colorIndex);
	}

	/**
	 * 内部定義済み色オブジェクトを返します.
	 *
	 * @param colorName 取得する色名
	 * @return 色オブジェクト 未定義色名を指定された場合はnull
	 * @see IndexedColors
	 */
	public static Color getCacheColor(String colorName) {
		return cacheName.get(colorName);
	}

	/**
	 * 色オブジェクトを返します.
	 *
	 * <p>
	 * このメソッドはまずキャッシュないの定義済み色オブジェクトを検索します.
	 * 見つからなかった場合はオブジェクトを生成し返しますが、
	 * キャッシュは行いません.
	 * </p>
	 *
	 * @param colorIndex 取得する色インデックス
	 * @return 色オブジェクト 未定義色インデックスを指定された場合はオブジェクトを生成し返します
	 * @see IndexedColors
	 */
	public static Color getColor(short colorIndex) {
		Color color = getCacheColor(colorIndex);
		if (color != null) {
			return color;
		}
		return new Color(colorIndex, null);
	}
}
