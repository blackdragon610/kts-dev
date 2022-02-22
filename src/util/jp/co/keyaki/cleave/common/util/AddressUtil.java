package jp.co.keyaki.cleave.common.util;

import org.apache.commons.lang.StringUtils;

/**
 *
 * 住所情報に関するユーティリティクラス。
 *
 * @author ytakahashi
 *
 */
public class AddressUtil {

	/**
	 * 都道府県判別文字。
	 */
	private static final String[] PREFECTURES_DISTINCTION_CHARACTER = {"都","道","府","県"};

	/**
	 * 住所から都道府県名を取得する
	 * @param 都道府県市区町村文字列
	 * @return 都道府県名
	 */
	public static String getPrefectures(String targetAddress) {

		if (StringUtils.isEmpty(targetAddress)) {
			return targetAddress;
		}

		String prefectures = StringUtils.EMPTY;
		if (arrayIndexOf(targetAddress, PREFECTURES_DISTINCTION_CHARACTER,3)) {
			prefectures = targetAddress.substring(0, 4);
		} else if (arrayIndexOf(targetAddress, PREFECTURES_DISTINCTION_CHARACTER,2)){
			prefectures = targetAddress.substring(0, 3);
		}

		return prefectures;
	}

	/**
	 * 住所から市区町村名以下を取得する
	 * @param 都道府県市区町村文字列
	 * @return 市区町村
	 */
	public static String getAddress(String targetAddress) {

		if (StringUtils.isEmpty(targetAddress)) {
			return targetAddress;
		}

		String address = StringUtils.EMPTY;
		if (arrayIndexOf(targetAddress, PREFECTURES_DISTINCTION_CHARACTER, 3)) {
			address = targetAddress.substring(4, targetAddress.length());
		} else if (arrayIndexOf(targetAddress, PREFECTURES_DISTINCTION_CHARACTER, 2)){
			address = targetAddress.substring(3,  targetAddress.length());
		}

		return address;
	}

	/**
	 * 文字数と配列文字列が指定したインデックスで一致しているかを判定
	 * @param 比較元文字列
	 * @param 比較先文字列配列
	 * @param インデックス
	 * @return
	 */
	private static boolean arrayIndexOf(String target, String[] arrayStr, int index){

		for (String str : arrayStr) {
			if(target.indexOf(str) == index){
				return true;
			}
		}
		return false;
	}
}
