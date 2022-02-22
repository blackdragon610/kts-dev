package jp.co.keyaki.cleave.fw.dao.template.xml;

import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 *
 * XMLテンプレートの&lt;if-not-null&gt;タグを表すクラス。
 *
 * <p>
 * &lt;if-not-null varName=&quot;xxx&quot;&gt;がテンプレートに記述されていた場合、
 * {@link SQLParameters}内に、xxxというパラメータ名が存在し、パラメータ値がnull以外の場合に
 * SQLが有効（SQLが構築対象）となる振る舞いをします。
 * </p>
 *
 * @author ytakahashi
 *
 */
public class IfNotNullSQLPart extends IfNullSQLPart {

    /**
     *
     * デフォルトコンストラクタ。
     *
     */
    public IfNotNullSQLPart() {
    }

    /**
     *
     * varNameに設定されているパラメータ名が{@link SQLParameters}内に存在し、かつパラメータ値がnull以外の場合はSQLを構築します。
     *
     * @param sqlInfo 構築するSQL情報
     * @param templates 設定情報
     * @param parameters SQLパラメータ
     * @return true:パラメータ名が存在し値がnull以外場合/false:パラメータ名が存在しないまたは値がnull場合
     * @see EvaluatableSQLPart#evaluate(XMLSQLInfo, XMLSQLTemplates, SQLParameters)
     */
    @Override
    protected boolean evaluate(XMLSQLInfo sqlInfo, XMLSQLTemplates templates,
            SQLParameters parameters) {
        return !super.evaluate(sqlInfo, templates, parameters);
    }

}
