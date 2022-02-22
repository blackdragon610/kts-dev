package jp.co.keyaki.cleave.fw.dao.template.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.DaoMessageDefine;
import jp.co.keyaki.cleave.fw.dao.SQLBuilder;
import jp.co.keyaki.cleave.fw.dao.SQLInfo;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 * {@link SQLInfo}をXMLファイルから生成するファクトリークラス.
 *
 *
 * @author ytakahashi
 *
 */
public class XMLSQLBuilder implements SQLBuilder {

	/**
	 * ロガー.
	 */
	private static final Log LOG = LogFactory.getLog(XMLSQLBuilder.class);

	/**
	 * {@link Digester}用ルールファイル.
	 */
	private static final String RULE_XML_NAME = "sql-template-rule.xml";

	/**
	 * SQL定義用デフォルトXMLファイル.
	 */
	private static final String DEFAULT_TEMPLATE_XML_NAME = "sql-template.xml";

	/**
	 * SQL定義用XMLファイルの正規化用変換XSLファイル.
	 */
	private static final String TEMPLATE_CONVERT_XSL_NAME = "sql-template-convert.xsl";

	/**
	 * 設定ファイル読み込み用の{@link Digester}.
	 */
	private static Digester digester = DigesterLoader.createDigester(getResource(RULE_XML_NAME));

	/**
	 * {@link Class#getResource(String)}を利用してリソースの{@link URL}を取得します.
	 *
	 * 「/」から始まるリソース名は絶対パスとして解釈されリソースを探します.
	 * 「/」から始まらないリソース名は、このクラス{@link XMLSQLBuilder}のパッケージ「jp.co.keyaki.cleave.fw.dao.template.xml」内で名前が一致するリソースを探します.
	 * 詳しくは{@link Class#getResource(String)}を参照.
	 *
	 * @param name 取得するリソース名
	 * @return リソースに対する{@link URL}(見つからなかった場合はnull)
	 * @see Class#getResource(String)
	 */
	private static URL getResource(String name) {
		return XMLSQLBuilder.class.getResource(name);
	}

	/**
	 * リソースに対する{@link InputStream}を取得し返します.
	 *
	 * @param url リソースに対する{@link URL}
	 * @return リソースへの{@link InputStream}
	 * @throws DaoException ストリームをオープンできなかった場合
	 */
	private static InputStream toInputStream(URL url) throws DaoException {
		try {
			return url.openStream();
		} catch (IOException ioe) {
			throw new DaoException(DaoMessageDefine.E000023, "url=" + url.toString(), ioe);
		}
	}

	/**
	 * XMLファイルから構築したSQLテンプレートオブジェクト群.
	 */
	private transient XMLSQLTemplates templates = null;

	/**
	 * コンストラクタ.
	 */
	public XMLSQLBuilder() {
	}

	/**
	 * XMLファイルを読み込み、SQLテンプレートオブジェクトを構築します.
	 *
	 * @param input XMLファイルに対するリソース{@link URL}
	 */
	public void configure(URL input) throws DaoException {
		InputStream is = null;
		try {
			is = convertTemplate(input, getResource(TEMPLATE_CONVERT_XSL_NAME));
			synchronized (XMLSQLBuilder.class) {
				digester.setValidating(false);
				try {
					templates = (XMLSQLTemplates) digester.parse(is);
				} catch (IOException ioe) {
					throw new DaoException(DaoMessageDefine.E000021, "url=" + input.toString(), ioe);
				} catch (SAXException saxe) {
					throw new DaoException(DaoMessageDefine.E000021, "url=" + input.toString(), saxe);
				}
			}
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * SQL定義用XMLファイルを正規化した結果を{@link InputStream}として返します.
	 *
	 * @param xmlInput 非正規化の可能性があるSQL定義用XMLファイル
	 * @param xslInput 正規化用XSLファイル
	 * @return 正規化されたSQL定義用XMLファイルに対する{@link InputStream}
	 * @throws DaoException 正規化が失敗した場合
	 */
	protected InputStream convertTemplate(URL xmlInput, URL xslInput) throws DaoException {
		TransformerFactory factory = TransformerFactory.newInstance();
		InputStream xmlStream = null;
		InputStream xslStream = null;
		ByteArrayOutputStream out = null;
		try {
			xmlStream = toInputStream(xmlInput);
			xslStream = toInputStream(xslInput);
			StreamSource xmlSource = new StreamSource(xmlStream);
			StreamSource xslSource = new StreamSource(xslStream);
			Transformer transformer = null;
			try {
				transformer = factory.newTransformer(xslSource);
			} catch (TransformerConfigurationException tce) {
				throw new DaoException(DaoMessageDefine.E000024, tce);
			}
			out = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(out);
			try {
				transformer.transform(xmlSource, result);
			} catch (TransformerException te) {
				throw new DaoException(DaoMessageDefine.E000022, "xml=" + xmlInput.toString() + ", xsl="
						+ xslInput.toString(), te);
			}
			byte[] bytes = out.toByteArray();
			return new ByteArrayInputStream(bytes);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(xslStream);
			IOUtils.closeQuietly(xmlStream);
		}
	}

	/**
	 * デフォルトの位置にあると仮定してSQL定義用デフォルトXMLファイルを利用して、{@link #configure(URL)}を読み出します.
	 *
	 * @throws DaoException SQL定義用デフォルトXMLファイルを利用して読み込めなかった場合
	 */
	public void load() throws DaoException {
		configure(getResource(DEFAULT_TEMPLATE_XML_NAME));
	}

	/**
	 * 引数を元にSQL情報を生成し返します。
	 *
	 * @param sqlId
	 *            SQLID
	 * @param parameters
	 *            SQL情報を生成するにあたって必要なパラメータ群
	 * @return SQL情報
	 * @throws DaoException
	 *             SQL情報生成時に例外が発生した場合
	 */
	public SQLInfo build(String sqlId, SQLParameters parameters) throws DaoException {
		if (templates == null) {
			synchronized (XMLSQLBuilder.class) {
				if (templates == null) {
					load();
				}
			}
		}
		XMLSQLTemplate template = templates.getSQLTemplate(sqlId);
		if (template == null) {
			throw new DaoException(DaoMessageDefine.E000019, "sqlId=" + sqlId);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("template sql=\n" + template);
		}
		XMLSQLInfo sqlInfo = new XMLSQLInfo(sqlId);
		template.buildSQLInfo(sqlInfo, templates, parameters);
		return sqlInfo;
	}

	/**
	 * このオブジェクトの文字列表現を返します.
	 *
	 * @return 文字列表現
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
