package jp.co.keyaki.cleave.fw.dao.template.xml;

import jp.co.keyaki.cleave.fw.dao.SQLParameters;

import org.apache.commons.lang.StringUtils;

/**
 * 20140313　伊東敦史
 *
 * IfNotNullのソースを編集して作成したクラス
 * IfNotNullがnull以外をSQLとして振る舞い
 * その機能にプラスしnullと空以外をSQLとして振る舞う機能にしました。
 */

/**
 *
 * XMLテンプレートの&lt;if-not-empty&gt;タグを表すクラス。
 *
 * <p>
 * &lt;if-not-empty varName=&quot;xxx&quot;&gt;がテンプレートに記述されていた場合、
 * {@link SQLParameters}内に、xxxというパラメータ名が存在し、パラメータ値がnullか空以外の場合に
 * SQLが有効（SQLが構築対象）となる振る舞いをします。
 * </p>
 *
 * @author aito
 *
 */
public class IfNotEmptySQLPart extends IfNullSQLPart {

    /**
     *
     * デフォルトコンストラクタ。
     *
     */
    public IfNotEmptySQLPart() {
    }


	/**
	 *
	 * varNameに設定されているパラメータ名が{@link SQLParameters}内に存在し、かつパラメータ値がnullか空以外の場合はSQLを構築します。
	 *
	 * @param sqlInfo 構築するSQL情報
	 * @param templates 設定情報
	 * @param parameters SQLパラメータ
	 * @return true:パラメータ名が存在し値がnull以外場合/false:パラメータ名が存在しないまたは値がnullか空場合
	 * @see EvaluatableSQLPart#evaluate(XMLSQLInfo, XMLSQLTemplates, SQLParameters)
	 */
	@Override
//	protected boolean evaluate(XMLSQLInfo sqlInfo, XMLSQLTemplates templates,
//	        SQLParameters parameters) {
//
//
//        Object parameterValue = parameters.getParam(getVarName());
//
//        if (parameterValue == null) {
//
//            return false;
//        } else {
//            return !parameterValue.toString().equals(StringUtils.EMPTY);
//        }
//
//	}
    protected boolean evaluate(XMLSQLInfo sqlInfo, XMLSQLTemplates templates,
            SQLParameters parameters) {
		Object parameterValue = parameters.getParam(getVarName());
        return (!super.evaluate(sqlInfo, templates, parameters) && !parameterValue.toString().equals(StringUtils.EMPTY));
    }
}
