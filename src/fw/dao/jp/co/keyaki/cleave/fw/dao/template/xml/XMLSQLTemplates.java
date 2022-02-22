package jp.co.keyaki.cleave.fw.dao.template.xml;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * SQLの情報を保持いているクラス
 *
 * @author ytakahashi
 *
 */
public class XMLSQLTemplates {

	/**
	 * sqlIdをキーとしたマップ.
	 */
	private Map<String, XMLSQLTemplate> templates = new HashMap<String, XMLSQLTemplate>();

	/**
	 * SQL情報を追加する
	 *
	 * @param template
	 */
	public void addSQLTemplate(XMLSQLTemplate template) {
		templates.put(template.getId(), template);
	}

	/**
	 * SQL情報を取得する
	 *
	 * @param sqlId
	 * @return SQL情報
	 */
	public XMLSQLTemplate getSQLTemplate(String sqlId) {
		return templates.get(sqlId);
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
