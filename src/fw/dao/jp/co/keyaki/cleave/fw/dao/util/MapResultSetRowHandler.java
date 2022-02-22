package jp.co.keyaki.cleave.fw.dao.util;


import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.jdbc.JdbcUtils;

/**
 * リザルトセットの内容をMapに設定するハンドラ。
 *
 * @author ytakahashi
 *
 * @param <E> Mapの型
 */
public class MapResultSetRowHandler extends
        AbstractResultSetRowHandler<Map<String, Object>> {

    /**
     * ロガー。
     */
    private static final Log LOG = LogFactory
            .getLog(MapResultSetRowHandler.class);

    /**
     * コンストラクタ
     *
     */
    public MapResultSetRowHandler() {
    }

    /**
     * リザルトセットの情報をMapに設定し、返却する。
     *
     * @param resultSet
     * @return map
     */
    public Map<String, Object> handle(ResultSet resultSet) throws DaoException {
        List<ColumnMappingInfo> resultSetMetaInfos = getResultSetMetaInfos(resultSet);
        Map<String, Object> map = new HashMap<String, Object>();
        for (ColumnMappingInfo mappingInfo : resultSetMetaInfos) {
            Object value = JdbcUtils.getObject(resultSet, mappingInfo.getColumnName());
            String propertyName = mappingInfo.getPropertyName();
            map.put(propertyName, value);
            if (LOG.isDebugEnabled()) {
                LOG.debug("set propertyName=[" + propertyName + "], value=[" + value + "]");
            }
        }
        return map;
    }

}
