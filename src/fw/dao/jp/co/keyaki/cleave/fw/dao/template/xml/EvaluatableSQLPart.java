package jp.co.keyaki.cleave.fw.dao.template.xml;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 *
 * テンプレートを有効にするか、無効にするか評価が必要なタグを表す抽象クラス。
 *
 *
 * @author ytakahashi
 *
 */
public abstract class EvaluatableSQLPart extends SQLPart {

	/**
	 * 変数名。
	 */
	private String varName;

	/**
	 *
	 * デフォルトコンストラクタ。
	 *
	 */
	public EvaluatableSQLPart() {
	}

	/**
	 *
	 * 変数名を返します。
	 *
	 * @return 変数名。
	 */
	public String getVarName() {
		return this.varName;
	}

	/**
	 *
	 * 変数名を設定します。
	 *
	 * @param varName
	 *            変数名
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

	/**
	 *
	 * 当メソッドでは、サブクラスが実装する
	 * {@link #evaluate(XMLSQLInfo, XMLSQLTemplates, SQLParameters)}が
	 * <code>ture</code>が 返却されてきた場合に、SQLの構築を実施します。<code>false</code>
	 * が返却されてきた場合、SQLの構築は行いません（スキップ）。
	 *
	 * @param sqlInfo
	 *            構築するSQL情報
	 * @param templates
	 *            設定情報
	 * @param parameters
	 *            SQLパラメータ
	 * @throws DaoException
	 *             SQL情報生成時に例外が発生した場合
	 * @see SQLPart#buildSQLInfo(XMLSQLInfo, XMLSQLTemplates, SQLParameters)
	 */
	public void buildSQLInfo(XMLSQLInfo sqlInfo, XMLSQLTemplates templates, SQLParameters parameters)
			throws DaoException {
		if (evaluate(sqlInfo, templates, parameters)) {
			replaceVariableAndSetParameter(sqlInfo, parameters);
			buildSQLInfoChild(sqlInfo, templates, parameters);
		}
	}

	/**
	 *
	 * サブクラスが、個々の評価を実施します。
	 *
	 * @param sqlInfo
	 *            構築するSQL情報
	 * @param templates
	 *            設定情報
	 * @param parametes
	 *            SQLパラメータ
	 * @return true:処理を継続する場合/false:処理をスキップする場合
	 */
	protected abstract boolean evaluate(XMLSQLInfo sqlInfo, XMLSQLTemplates templates, SQLParameters parametes);

}
