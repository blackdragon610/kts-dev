package jp.co.keyaki.cleave.fw.ui.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Servlet Filter implementation class RequestDebugFilter
 */
public class RequestDebugFilter implements Filter {

	private static final String INIT_PARAM_NAME_REQUEST_WRAP = "requestWrap";

	private boolean isRequestWrap = false;

	/**
	 * Default constructor.
	 */
	public RequestDebugFilter() {
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		isRequestWrap = Boolean.valueOf(config.getInitParameter(INIT_PARAM_NAME_REQUEST_WRAP)).booleanValue();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (isRequestWrap) {
			if (HttpServletRequest.class.isAssignableFrom(request.getClass())) {
				request = new DebugHttpServletRequest(
						HttpServletRequest.class.cast(request));
			} else {
				request = new DebugServletRequest(request);
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	protected static class DebugServletRequest extends ServletRequestWrapper {

		public DebugServletRequest(ServletRequest request) {
			super(request);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return new DebugParameterMap(super.getParameterMap());
		}

	}

	protected static class DebugHttpServletRequest extends
			HttpServletRequestWrapper {

		public DebugHttpServletRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return new DebugParameterMap(super.getParameterMap());
		}

	}

	protected static class DebugParameterMap implements Map<String, String[]> {

		private Map<String, String[]> parameterMap;

		public DebugParameterMap(Map<String, String[]> parameterMap) {
			this.parameterMap = parameterMap;
		}

		public int size() {
			return parameterMap.size();
		}

		public boolean isEmpty() {
			return parameterMap.isEmpty();
		}

		public boolean containsKey(Object key) {
			return parameterMap.containsKey(key);
		}

		public boolean containsValue(Object value) {
			return parameterMap.containsValue(value);
		}

		public String[] get(Object key) {
			return parameterMap.get(key);
		}

		public String[] put(String key, String[] value) {
			return parameterMap.put(key, value);
		}

		public String[] remove(Object key) {
			return parameterMap.remove(key);
		}

		public void putAll(Map<? extends String, ? extends String[]> m) {
			parameterMap.putAll(m);
		}

		public void clear() {
			parameterMap.clear();
		}

		public Set<String> keySet() {
			return parameterMap.keySet();
		}

		public Collection<String[]> values() {
			return parameterMap.values();
		}

		public Set<java.util.Map.Entry<String, String[]>> entrySet() {
			return parameterMap.entrySet();
		}

		@Override
		public boolean equals(Object o) {
			return parameterMap.equals(o);
		}

		@Override
		public int hashCode() {
			return parameterMap.hashCode();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(Arrays.toString(entry.getValue()));
			}
			sb.insert(0, "{");
			sb.append("}");
			return sb.toString();
		}
	}

}
