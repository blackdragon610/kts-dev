package jp.co.keyaki.cleave.fw.core.util;

import java.nio.ByteBuffer;

/**
 * １６進ユーティリティ.
 *
 *
 * @author ytakahashi
 *
 */
public class HexUtil {

	/**
	 * 文字列内容が16進文字列と仮定し、バイト配列に変換します.
	 *
	 * @param hex 16進文字列
	 * @return バイト配列
	 */
	public static byte[] fromHexString(String hex) {
		ByteBuffer bb = ByteBuffer.allocate(hex.length() / 2 + hex.length() % 2);
		for (int i = 0; i < hex.length(); i = i + 2) {
			String s = hex.substring(i, i + 2);
			byte b = (byte) Integer.parseInt(s, 16);
			bb.put(b);
		}
		return bb.array();
	}

	/**
	 * バイト配列の内容を16進文字列に変換します.
	 *
	 * @param value バイト配列
	 * @return 16進文字列
	 */
	public static String toHexString(byte[] value) {
		StringBuilder hex = new StringBuilder();
		for (byte b : value) {
			int i = b;
			if (i < 0) {
				i += 256;
			}
			String s = Integer.toHexString(i);
			while (s.length() < 2) {
				s = "0" + s;
			}
			hex.append(s);
		}
		return hex.toString().toUpperCase();
	}

}
