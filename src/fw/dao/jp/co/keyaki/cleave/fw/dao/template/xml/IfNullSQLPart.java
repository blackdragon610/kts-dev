package jp.co.keyaki.cleave.fw.dao.template.xml;

import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 *
 * XMLテンプレートの&lt;if-null&gt;タグを表すクラス。
 *
 * <p>
 * &lt;if-null varName=&quot;xxx&quot;&gt;がテンプレートに記述されていた場合、
 * {@link SQLParameters}内に、xxxというパラメータ名が存在しない、またはパラメータ名を存在するがパラメータ値がnullの場合に
 * SQLが有効（SQLが構築対象）となる振る舞いをします。
 * </p>
 *
 * @author ytakahashi
 *
 */
public class IfNullSQLPart extends EvaluatableSQLPart {

    /**
     *
     * デフォルトコンストラクタ。
     *
     */
    public IfNullSQLPart() {
    }

    /**
     *
     * varNameに設定されているパラメータ名が{@link SQLParameters}内に存在しない、またはパラメータ値がnullの場合はSQLを構築します。
     *
     * @param sqlInfo 構築するSQL情報
     * @param templates 設定情報
     * @param parameters SQLパラメータ
     * @return true:パラメータ名が存在しないまたは値がnull場合/false:パラメータ名が存在し値がnull以外場合
     * @see EvaluatableSQLPart#evaluate(XMLSQLInfo, XMLSQLTemplates, SQLParameters)
     */
    @Override
    protected boolean evaluate(XMLSQLInfo sqlInfo, XMLSQLTemplates templates,
            SQLParameters parameters) {
        return parameters.getParam(getVarName()) == null;
    }

}
