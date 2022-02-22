package jp.co.keyaki.cleave.fw.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jp.co.keyaki.cleave.fw.core.SystemConfig;
import jp.co.keyaki.cleave.fw.core.util.ActionContextCallable;

/**
 * サービス実行クラス.
 *
 * @author ytakahashi
 */
public class ServiceExecutor {

	private transient static Map<String, String> configs = null;

	private static final String REQUEST_TYPE = "services.service.request-type";
	private static final String SERVICE_TYPE = "services.service.service-type";

	/**
	 * サービスを非同期で実行します.
	 *
	 * @param request リクエスト
	 * @return フューチャーオブジェクト
	 * @see Future
	 */
	public static <S extends ResponseMessage<?>, Q extends RequestMessage<S>> Future<S> invoke(final Q request) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		return executorService.submit(new ActionContextCallable<S>() {

			@Override
			protected S doCall() throws Exception {
				return execute(request);
			}
		});
	}

	/**
	 * サービスを実行します.
	 *
	 * @param request リクエスト
	 * @return レスポンス
	 */
	public static <S extends ResponseMessage<?>, Q extends RequestMessage<S>> S execute(Q request) {
		if (configs == null) {
			loadConfig();
		}
		String serviceName = configs.get(request.getClass().getName());
		S res = null;
		Throwable handle = null;
		try {
			@SuppressWarnings("unchecked")
			Service<S, Q> service = (Service<S, Q>) Class.forName(serviceName).newInstance();
			res = service.execute(request);
		} catch (InstantiationException e) {
			handle = e;
		} catch (IllegalAccessException e) {
			handle = e;
		} catch (ClassNotFoundException e) {
			handle = e;
		}
		if (res == null) {
			res = request.createResponse();
		}
		if (handle != null) {
			res.handleThrowable(handle);
		}
		return res;
	}

	/**
	 * 設定ファイル読み込み処理.
	 *
	 */
	protected static synchronized void loadConfig() {
		if (configs != null) {
			return;
		}
		Map<String, String> work = new HashMap<String, String>();
		Iterator<String> requests = SystemConfig.getList(REQUEST_TYPE).iterator();
		Iterator<String> services = SystemConfig.getList(SERVICE_TYPE).iterator();
		while (requests.hasNext() && services.hasNext()) {
			String request = requests.next();
			String service = services.next();
			work.put(request, service);
		}
		configs = work;
	}
}
