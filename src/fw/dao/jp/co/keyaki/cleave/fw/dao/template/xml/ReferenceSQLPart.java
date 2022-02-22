package jp.co.keyaki.cleave.fw.dao.template.xml;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.DaoMessageDefine;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 *
 * XMLテンプレートの&lt;sql-ref&gt;タグを表すクラス。
 *
 * <p>
 * &lt;sql-ref refId=&quot;xxx&quot;&gt;がテンプレートに記述されていた場合、
 * XMLテンプレート内にid=xxxと定義されているSQLテンプレートをインクルードします。
 * </p>
 *
 * @author ytakahashi
 * @see #buildSQLInfo(XMLSQLInfo, XMLSQLTemplates, SQLParameters)
 *
 */
public class ReferenceSQLPart extends SQLPart {

	/**
	 * 参照SQLID。
	 */
	private String refId;

	/**
	 * 別名。
	 */
	private String alias;

	/**
	 *
	 * デフォルトコンストラクタ。
	 *
	 */
	public ReferenceSQLPart() {
	}

	/**
	 *
	 * 参照SQLIDを返します。
	 *
	 * @return 参照SQLID
	 */
	public String getRefId() {
		return this.refId;
	}

	/**
	 *
	 * 参照SQLIDを設定します。
	 *
	 * @param refId
	 *            参照SQLID
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * 別名を返します。
	 *
	 * @return 別名
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * 別名を設定します。
	 *
	 * @param alias 別名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 *
	 * 当メソッドでは、参照SQLIDを元に{@link XMLSQLTemplates}より、テンプレートを取得しSQLを構築します。
	 *
	 * <p>
	 * 参照SQLIDが{@link XMLSQLTemplates}内に定義されていない場合、例外を発生させます。
	 * また、参照SQLテンプレートに引き渡される{@link SQLParameters}は、
	 * {@link SQLParameters#getCompositeSQLParameters(String)}にて 取得できる
	 * {@link SQLParameters}を引き渡します。
	 * {@link SQLParameters#getCompositeSQLParameters(String)}の第１引数として
	 * 別名が設定されている場合は別名を、別名が設定されていない場合は参照SQLIDを利用します。
	 * </p>
	 * <p>
	 * 以下に{@link SQLParameters}の設定方法を記します。
	 *
	 * <pre>
	 * <code>
	 * ↓参照先SQLで必要なパラメータ
	 * SQLParameters refParameters = new SQLParameters();
	 * refParameters.addParameter("refVar1", "valueRef1");
	 * refParameters.addParameter("refVar1", "valueRef2");
	 * refParameters.addParameter("refVar1", "valueRef3");
	 *
	 * ↓参照元SQLで必要なパラメータ
	 * SQLParameters parameters = new SQLParameters();
	 * parameters.addParameter("var1", "value1");
	 * parameters.addParameter("var2", "value2");
	 * parameters.addParameter("var3", "value3");
	 *
	 * ↓参照元SQLパラメータに参照先SQLパラメータを内包させる。
	 * ↓その際、第一引数として別名または参照SQLIDを指定する。
	 * parameters.compose("aliasOrRefId", refParameters);
	 * </code>
	 * </pre>
	 *
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
	 * @see SQLPart#buildSQLInfo(jp.co.ans.dao.template.xml.XMLSQLInfo, XMLSQLTemplates, SQLParameters)
	 */
	@Override
	protected void buildSQLInfo(XMLSQLInfo sqlInfo, XMLSQLTemplates templates, SQLParameters parameters)
			throws DaoException {
		XMLSQLTemplate referenceTemplate = templates.getSQLTemplate(refId);
		if (referenceTemplate == null) {
			throw new DaoException(DaoMessageDefine.E000019, "sqlId=[" + refId + "]");
		}
		String aliasOrRefId = refId;
		if (StringUtils.isNotEmpty(alias)) {
			aliasOrRefId = alias;
		}
		SQLParameters compositeParametes = parameters.getCompositeSQLParameters(aliasOrRefId);
		if (compositeParametes == null) {
			compositeParametes = new SQLParameters();
		}
		referenceTemplate.buildSQLInfo(sqlInfo, templates, compositeParametes);
	}

}
