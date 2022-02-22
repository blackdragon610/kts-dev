package jp.co.keyaki.cleave.fw.dao.template.xml;

/**
 *
 * テンプレート値とパラメータ値を比較して、テンプレートを有効にするか、無効にするか評価が必要なタグを表す抽象クラス。
 *
 *
 * @author ytakahashi
 *
 */
public abstract class ComparableSQLPart extends EvaluatableSQLPart {

    /**
     * テンプレート値（比較値）。
     */
    private String value;

    /**
     *
     * デフォルトコンストラクタ。
     *
     */
    public ComparableSQLPart() {
    }

    /**
     *
     * テンプレート値（比較値）を返します。
     *
     * @return テンプレート値（比較値）
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * テンプレート値（比較値）を設定します。
     *
     * @param value テンプレート値（比較値）を
     */
    public void setValue(String value) {
        this.value = value;
    }

}
