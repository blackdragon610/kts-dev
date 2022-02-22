package jp.co.keyaki.cleave.fw.dao.util;


import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.core.util.BeanUtils;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.jdbc.JdbcUtils;

/**
 * データベースの列名とJavaBeanのプロパティ名の規則を利用して、リザルトセットの内容をJavaBeanに設定するハンドラ。
 *
 * <p>
 * データベースの列名からJavaBeanのプロパティ名を推測して設定します。
 * 列名の単語がアンダースコア(_)で区切られている前提です。
 * </p>
 *
 * @author ytakahashi
 *
 * @param <E> JavaBeanの型
 */
public class NameMatchBeanResultSetRowHandler<E> extends
        AbstractResultSetRowHandler<E> {

    /**
     * ロガー。
     */
    private static final Log LOG = LogFactory
            .getLog(NameMatchBeanResultSetRowHandler.class);

    /**
     * JavaBean操作に必要なユーティリティインスタンス。
     */
    private BeanUtils<E> beanUtils;

    /**
     * コンストラクタ。
     *
     * @param entityType JavaBeanの型
     */
    public NameMatchBeanResultSetRowHandler(Class<E> entityType) {
        this(new BeanUtils<E>(entityType));
    }

    /**
     * コンストラクタ。
     *
     * @param entityUtils JavaBean操作に必要なユーティリティインスタンス
     */
    public NameMatchBeanResultSetRowHandler(BeanUtils<E> entityUtils) {
        this.beanUtils = entityUtils;
    }

    /**
     * リザルトセットの列情報をJavaBeanのプロパティへ写してインスタンスを返却します。
     *
     * <p>
     * このメソッドでは、リザルトセットのカーソルを次へ移動します。
     * カーソルが行を指していない場合は、戻り値はnullを返します。
     * カーソルが存在する場合は、JavaBeanのインスタンスを生成しリザルトセットの列の値を
     * JavaBeanのプロパティへ写してJavaBeanのインスタンスを返却します。
     * </p>
     *
     * @see jp.co.ans.dao.base.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public E handle(ResultSet resultSet) throws DaoException {
        List<ColumnMappingInfo> resultSetMetaInfos = getResultSetMetaInfos(resultSet);
        E instance = beanUtils.newInstance();
        for (ColumnMappingInfo mappingInfo : resultSetMetaInfos) {
            Object value = JdbcUtils.getObject(resultSet, mappingInfo.getColumnName());
            if (value == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(mappingInfo.getColumnName() + " is null.");
                }
                continue;
            }
            String propertyName = mappingInfo.getPropertyName();
            beanUtils.setProperty(instance, propertyName, value);
        }
        return instance;
    }

}
