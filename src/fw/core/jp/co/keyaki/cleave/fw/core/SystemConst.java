package jp.co.keyaki.cleave.fw.core;

public class SystemConst {

    /**
     * システム設定ファイルに対するJavaオプション引数. (-Dcdb.config.path=)
     */
    public static final String SYS_PROP_CONFIG_NAME = "cdb.config.path";

    /**
     * ロガー名称：システム.
     */
    public static final String LOGGER_NAME_SYSTEM = "system_logger";

    /**
     * ロガー名称：データベース.
     */
    public static final String LOGGER_NAME_DATABASE = "database_logger";

    /**
     * ロガー名称：オペレーション.
     */
    public static final String LOGGER_NAME_OPERATION = "operation_logger";

    /**
     * ロガー名称：タスク.
     */
    public static final String LOGGER_NAME_TASK = "task_logger";

    /**
     * ロガー名称：通知.
     */
    public static final String LOGGER_NAME_NOTICE = "notice_logger";
}
