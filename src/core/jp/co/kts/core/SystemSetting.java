package jp.co.kts.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jp.co.keyaki.cleave.common.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * システム設定情報。
 *
 * @author hotake
 */
public final class SystemSetting {

    /** システム設定情報のリソースバンドル */
    private static ResourceBundle systemSetting;

    /** ログ出力オブジェクト */
    private static Log log;

    static {
        try {
//            log = LogFactory.getLog(SystemSetting.class);
        	log = LogFactory.getLog(SystemSetting.class);
            systemSetting = ResourceBundle.getBundle("SystemSetting");
        } catch (MissingResourceException mre) {
            log.fatal("SystemSetting#スタティックイニシャライザ", mre);
        }
    }

    /**
     * <P>
     * SystemSettingのインスタンス化を防止するためコンストラクタを隠蔽します。
     * </P>
     */
    private SystemSetting() {
    }

    /**
     * <P>
     * SystemSetting.propertiesより設定値を取得します。
     * </P>
     *
     * @param key SystemSetting.propertiesより値を取得する際のキー
     * @return 設定値
     */
    public static String get(String key) {
        String setting = null;
        try {
            setting = systemSetting.getString(key);
        } catch (Exception e) {
            log.warn("システム設定情報取得エラー：key=" + key, e);
        }
        return setting;
    }

    /**
     * <P>
     * SystemSetting.propertiesより設定値を配列で取得します。
     * </P>
     *
     * @param key SystemSetting.propertiesより値を取得する際のキー
     * @return 設定値
     */
    public static String[] getStringArray(String key) {
        String[] setting = null;
        try {
            setting = systemSetting.getString(key).split(",");
        } catch (Exception e) {
            log.warn("システム設定情報取得エラー：key=" + key, e);
        }
        return setting;
    }

    /**
     * <P>
     * 実装非表示状態を取得します。
     *
     * </P>
     * @return true:実装非表示、false:表示
     */
    public static boolean isNonDisplay() {
        String displayDate = get("DISPLAY_DATE");
        String systemDate = DateUtil.dateToString("yyyyMMdd");
        if (displayDate.compareTo(systemDate) >= 0) {
            return true;
        }
        return false;
    }
}