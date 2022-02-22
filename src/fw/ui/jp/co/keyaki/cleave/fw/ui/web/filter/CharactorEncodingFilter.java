package jp.co.keyaki.cleave.fw.ui.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文字エンコードするクラス
 *
 * @author ytakahashi
 *
 */
public class CharactorEncodingFilter implements Filter {

	/**
	 * ロガー
	 */
	private static final Log LOG = LogFactory.getLog(CharactorEncodingFilter.class);

	// 文字コードを設定する
	private String encoding = "UTF-8";

	/**
	 * エンコード処理
	 *
	 * @param request
	 *            ServletRequest
	 * @param response
	 *            ServletResponse
	 * @param chain
	 *
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	/**
	 * 初期処理
	 *
	 * @param config
	 */
	public void init(FilterConfig config) throws ServletException {

		if (config.getInitParameter("encoding") != null) {
			encoding = config.getInitParameter("encoding");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("encoding=[" + encoding + "]");
		}
	}

	/**
	 * 終了処理
	 *
	 */
	public void destroy() {
	}

}
