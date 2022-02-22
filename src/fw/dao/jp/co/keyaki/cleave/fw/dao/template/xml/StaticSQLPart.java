package jp.co.keyaki.cleave.fw.dao.template.xml;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 *
 * XMLテンプレートの&lt;static&gt;タグを表すクラス。
 * <p>
 * &lt;static&gt;タグは内部的にはパラメータ置換は行うが、他のタグをネストを許さない前提です。
 * <p>
 *
 * @author ytakahashi
 *
 */
public class StaticSQLPart extends SQLPart {

	/**
	 *
	 * デフォルトコンストラクタ。
	 *
	 */
	public StaticSQLPart() {
	}

	/**
	 *
	 * 第一引数のSQL情報に対して、&lt;static&gt;で囲われているSQL文／パラメータを追加します。
	 *
	 * <p>
	 * この処理内で、バインドパラメータを構築します。
	 * </p>
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
	@Override
	protected void buildSQLInfo(XMLSQLInfo sqlInfo, XMLSQLTemplates templates, SQLParameters parameters)
			throws DaoException {
		replaceVariableAndSetParameter(sqlInfo, parameters);
	}

}
