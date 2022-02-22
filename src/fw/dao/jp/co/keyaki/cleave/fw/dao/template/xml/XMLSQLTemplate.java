package jp.co.keyaki.cleave.fw.dao.template.xml;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 * SQLを構築するクラス
 *
 * @author ytakahashi
 *
 */
public class XMLSQLTemplate extends SQLPart {

	/** id */
	private String id;

	/**
	 * コンストラクタ
	 *
	 */
	public XMLSQLTemplate() {

	}

	/**
	 * idを取得する。
	 *
	 * @return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * idを設定する。
	 *
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * 第一引数のSQL情報に対して、SQL文／パラメータを構築します。
	 *
	 * @param sqlInfo
	 *            構築するSQL情報
	 * @param templates
	 *            設定情報
	 * @param parameters
	 *            SQLパラメータ
	 * @throws DaoException
	 *             SQL情報生成時に例外が発生した場合
	 */
	protected void buildSQLInfo(XMLSQLInfo sqlInfo, XMLSQLTemplates templates, SQLParameters parameters)
			throws DaoException {
		buildSQLInfoChild(sqlInfo, templates, parameters);
	}

}
