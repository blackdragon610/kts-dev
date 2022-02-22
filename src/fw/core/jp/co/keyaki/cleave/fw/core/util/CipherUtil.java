package jp.co.keyaki.cleave.fw.core.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jp.co.keyaki.cleave.fw.core.CoreMessageDefine;
import jp.co.keyaki.cleave.fw.core.CoreRuntimeException;

/**
 * 暗号・複合化ユーティリティクラス.
 *
 * @author ytakahashi
 *
 */
public class CipherUtil {

	/**
	 * アルゴリズム.
	 */
	private static final String ALGORITHM_NAME = "AES";

	/**
	 * キー.
	 */
	private static final String KEY_BASE_VALUE = "nexio.coresystem";

	/**
	 * デフォルト暗号キーオブジェクト.
	 */
	public static final Key DEFAULT_KEY = new SecretKeySpec(KEY_BASE_VALUE.getBytes(), ALGORITHM_NAME);

	/**
	 * バイト配列の内容をデフォルト暗号キーを利用して暗号化します.
	 *
	 * @param value オリジナル値
	 * @return 暗号値
	 * @throws CoreRuntimeException 暗号化に失敗した場合
	 */
	public static byte[] encode(byte[] value) {
		return encode(value, DEFAULT_KEY);
	}

	/**
	 * バイト配列の内容を暗号化します.
	 *
	 * @param value オリジナル値
	 * @param key 暗号キー
	 * @return 暗号値
	 * @throws CoreRuntimeException 暗号化に失敗した場合
	 */
	public static byte[] encode(byte[] value, Key key) {
		try {
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(value);
		} catch (Exception e) {
			throw new CoreRuntimeException(CoreMessageDefine.E000005, e);
		}
	}

	/**
	 * バイト配列の内容をデフォルト暗号キーを利用して複合化します.
	 *
	 * @param value 暗号値
	 * @return オリジナル値
	 * @throws CoreRuntimeException 複合化に失敗した場合
	 */
	public static byte[] decode(byte[] value) {
		return decode(value, DEFAULT_KEY);
	}

	/**
	 * バイト配列の内容を複合化します.
	 *
	 * @param value 暗号値
	 * @param key 暗号キー
	 * @return オリジナル値
	 * @throws CoreRuntimeException 複合化に失敗した場合
	 */
	public static byte[] decode(byte[] value, Key key) {
		try {
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(value);
		} catch (Exception e) {
			throw new CoreRuntimeException(CoreMessageDefine.E000006, e);
		}
	}

	/**
	 * 文字列をデフォルト暗号キーを利用して暗号化します.
	 *
	 * @param value オリジナル文字列
	 * @return 暗号文字列
	 * @throws CoreRuntimeException 暗号化に失敗した場合
	 */
	public static String encodeString(String value) {
		return encodeString(value, DEFAULT_KEY);
	}

	/**
	 * 文字列を暗号化します.
	 *
	 * @param value オリジナル文字列
	 * @param key 暗号キー
	 * @return 暗号文字列
	 * @throws CoreRuntimeException 暗号化に失敗した場合
	 */
	public static String encodeString(String value, Key key) {
		byte[] bytes = value.getBytes();
		byte[] encode = encode(bytes, key);
		return HexUtil.toHexString(encode);
	}

	/**
	 * 文字列をデフォルト暗号キーを利用して複合化します.
	 *
	 * @param value 暗号文字列
	 * @return オリジナル文字列
	 * @throws CoreRuntimeException 複合化に失敗した場合
	 */
	public static String decodeString(String value) {
		return decodeString(value, DEFAULT_KEY);
	}

	/**
	 * 文字列を複合化します.
	 *
	 * @param value 暗号文字列
	 * @param key 暗号キー
	 * @return オリジナル文字列
	 * @throws CoreRuntimeException 複合化に失敗した場合
	 */
	public static String decodeString(String value, Key key) {
		byte[] bytes = HexUtil.fromHexString(value);
		byte[] decode = decode(bytes, key);
		return new String(decode);
	}

	public static void main(String[] args) {
		String value = "SYSTEM";
		System.out.println(value);
		String encode = encodeString(value);
		System.out.println(encode);
		String decode = decodeString(encode);
		System.out.println(decode);
	}
}
