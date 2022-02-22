package jp.co.keyaki.cleave.fw.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * キーによるスレッドロックオブジェクト。
 * 
 * <p>
 * このクラスは、内部的にスレッドをロック、開放するためのキーとしてハッシュ値を利用しています。 つまり、
 * {@link Object#equals(Object)}と{@link Object#hashCode()}が正しく、
 * 実装されている事が前提になっています。
 * </p>
 * <p>
 * またこのThreadLockerクラスは、インスタンス毎にロックを管理するため、１つのロック対象にThreadLockerのインスタンスが
 * シングルトンになる様に実装してください。
 * </p>
 * <p>
 * また、アプリケーション側で必ずロックを開放するように実装してください。
 * </p>
 * <p>
 * 利用例は、下記の通りとなります。（文字列をキーとした場合）
 * </p>
 * <p>
 * <code>
 * <pre>
 * 
 * // ThreadLockerのインスタンスがシングルトンになるように、static として宣言
 * private static final KeyThreadLocker&lt;String&gt; LOCKER = new KeyThreadLocker&lt;String&gt;();
 * 
 * public void example() {
 * 
 *     // ロック対象となるキーを取得
 *     String key = ....;
 * 
 *     try {
 * 
 *         // キーとなる文字列と同じ値ですでにロックがされている場合は
 *         // ここでストップ（一時停止）させる。
 *         LOCKER.lock(key);
 * 
 *         // ここに必要な処理を記述
 * 
 *     } finally {
 *         // try句を抜ける際に、必ずロックを開放するために、finally句にてunlockを呼び出し
 *         LOCKER.unlock(key);
 *     }
 * }
 * </pre>
 * </code>
 * </p>
 * 
 * @author ytakahashi
 * 
 */
public class KeyThreadLocker<K> {

	/**
	 * ロガー。
	 */
	private static final Log LOG = LogFactory.getLog(KeyThreadLocker.class);

	/**
	 * ロックの際に利用したキーとロックを取得しているスレッドを保持してるマップ。
	 */
	private Map<K, Thread> lockKeyAndRunningThreadMap = new HashMap<K, Thread>();

	/**
	 * 引数のオブジェクトをキーとして、同一キーのスレッドをロックします。
	 * 
	 * 
	 * @param lockKey
	 *            ロックキー
	 * @throws Exception
	 *             スレッドをロックする際に例外が発生した場合
	 */
	public synchronized void lock(K lockKey) throws Exception {
		while (lockKeyAndRunningThreadMap.containsKey(lockKey)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("スレッドを一時停止。" + "key=" + lockKey + ",Thread=" + Thread.currentThread());
			}
			// このObjectLockerのロックを開放し
			// 自スレッドを一時停止する。
			wait();
			if (LOG.isDebugEnabled()) {
				LOG.debug("スレッドを再起動。" + "key=" + lockKey + ",Thread=" + Thread.currentThread());
			}
		}
		lockKeyAndRunningThreadMap.put(lockKey, Thread.currentThread());
		if (LOG.isDebugEnabled()) {
			LOG.debug("オブジェクトをロック。" + "key=" + lockKey + ",Thread=" + Thread.currentThread());
		}
	}

	/**
	 * ロックを解除します。
	 * 
	 * @param lockKey
	 *            ロックを取得する際に使用したロックキー
	 */
	public synchronized void unlock(K lockKey) {
		lockKeyAndRunningThreadMap.remove(lockKey);
		if (LOG.isDebugEnabled()) {
			LOG.debug("オブジェクトを開放。" + "key=" + lockKey + ",Thread=" + Thread.currentThread());
		}
		notifyAll();
		LOG.debug("スレッドの再起動要求");
	}
}
