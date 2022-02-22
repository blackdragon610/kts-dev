package jp.co.keyaki.cleave.fw.dao.template.xml;

import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 *
 * XMLテンプレートの&lt;if-equals&gt;タグを表すクラス。
 *
 * <p>
 * &lt;if-equals varName=&quot;xxx&quot; value=&quot;yyy&quot;&gt;がテンプレートに記述されていた場合、
 * {@link SQLParameters}内のxxxパラメータ値とテンプレート値が同一、つまりyyyと同一の場合SQLが有効（SQLが構築対象）となる振る舞いをします。
 * </p>
 *
 * @author ytakahashi
 *
 */
public class IfEqualsSQLPart extends ComparableSQLPart {

    /**
     *
     * デフォルトコンストラクタ。
     *
     */
    public IfEqualsSQLPart() {
    }

    /**
     *
     * varNameとして設定されているパラメータ値がテンプレート値と同一の場合、SQLを構築します。
     *
     * @param sqlInfo 構築するSQL情報
     * @param templates 設定情報
     * @param parameters SQLパラメータ
     * @return true:パラメータ値がテンプレート値と同一の場合/false:パラメータ値がテンプレート値が異なるの場合
     * @see EvaluatableSQLPart#evaluate(XMLSQLInfo, XMLSQLTemplates, SQLParameters)
     */
    @Override
    protected boolean evaluate(XMLSQLInfo sqlInfo, XMLSQLTemplates templates,
            SQLParameters parameters) {

        Object compareValue = getValue();
        Object parameterValue = parameters.getParam(getVarName());

        if (parameterValue == null) {
            if (compareValue == null) {
                return true;
            }
            return false;
        } else {
            return parameterValue.toString().equals(compareValue);
        }

    }

}
