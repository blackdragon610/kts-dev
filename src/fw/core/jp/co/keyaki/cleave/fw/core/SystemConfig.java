package jp.co.keyaki.cleave.fw.core;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * システム設定クラス.
 *
 * @author ytakahashi
 */
public class SystemConfig {

	/**
	 * システムロガー.
	 */
	private static final Log SYSTEM_LOG = LogFactory.getLog(SystemConst.LOGGER_NAME_SYSTEM);

	/**
	 * ドット.
	 */
	private static final String DOT = ".";

	/**
	 * スラッシュ.
	 */
	private static final String SLASH = "/";

	/**
	 * デフォルトコンフィグ配置フォルダ名.
	 */
	private static final String DEFAULT_CONFIG_DIR_NAME = "config";

	/**
	 * デフォルトコンフィグファイル名.
	 */
	private static final String DEFAULT_CONFIG_FILE_NAME = "config.xml";

	/**
	 * デフォルトコンフィグファイルパス.
	 */
	public static final String DEFAULT_CONFIG_FILE_PATH;
	static {
		DEFAULT_CONFIG_FILE_PATH = SLASH
				.concat(StringUtils.replace(SystemConst.class.getPackage().getName(), DOT, SLASH)).concat(SLASH)
				.concat(DEFAULT_CONFIG_DIR_NAME).concat(SLASH).concat(DEFAULT_CONFIG_FILE_NAME);
	}

	/**
	 * インデックスキー開始トークン
	 */
	public static final String INDEX_START_TOKEN = "(";

	/**
	 * インデックスキー終了トークン
	 */
	public static final String INDEX_END_TOKEN = ")";

	/**
	 * コンフィグオブジェクト.
	 */
	protected static Configuration config;
	static {
		init();
	}

	/**
	 * コンフィグオブジェクト初期化処理.
	 */
	static void init() {
		config = null;
		URL configURL = getConfigURL();
		if (SYSTEM_LOG.isInfoEnabled()) {
			SYSTEM_LOG.info("設定ファイル情報");
			SYSTEM_LOG.info("\tconfigURL=".concat(configURL.toString()));
		}
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		builder.setURL(configURL);
		try {
			config = builder.getConfiguration();
		} catch (ConfigurationException ce) {
			throw new CoreRuntimeException(CoreMessageDefine.E000004, "Configuration生成に失敗", ce);
		}
	}

	/**
	 * 利用するコンフィグファイルを判断し、URL形式で返します.
	 *
	 * <p>
	 * コンフィグファイルは下記の順番にて探します.
	 * <ol>
	 * <li>システムプロパティ{@link SystemConst#SYS_PROP_CONFIG_NAME}で指定されていた場合はそのパス</li>
	 * <li>システムプロパティ{@link SystemConst#SYS_PROP_CONFIG_NAME}がされていなかった場合は{@link #DEFAULT_CONFIG_FILE_PATH}のパス</li>
	 * </ol>
	 * </p>
	 *
	 * @return 利用コンフィグファイルに対するURL
	 * @throws CoreRuntimeException コンフィグファイルが参照できなかった場合
	 * @see SystemConst#SYS_PROP_CONFIG_NAME
	 */
	private static URL getConfigURL() {
		String sysPropConfigName = System.getProperty(SystemConst.SYS_PROP_CONFIG_NAME);
		URL configURL = null;
		if (sysPropConfigName != null) {
			try {
				configURL = new File(sysPropConfigName).toURI().toURL();
			} catch (MalformedURLException murle) {
				throw new CoreRuntimeException(CoreMessageDefine.E000004, "システムプロパティで指定されたパスでURL生成に失敗", murle);
			}
		} else {
			configURL = SystemConfig.class.getResource(DEFAULT_CONFIG_FILE_PATH);
			if (configURL == null) {
				throw new CoreRuntimeException(CoreMessageDefine.E000004, "デフォルトのコンフィグレーションファイルが見つからない。"
						+ DEFAULT_CONFIG_FILE_PATH);
			}
		}
		return configURL;
	}

	/**
	 * キーの個数を返します.
	 *
	 * @param key
	 * @return
	 */
	public static int getKeyCount(String key) {
		Object property = getProperty(key);
		if (property == null) {
			return 0;
		}
		if (Collection.class.isAssignableFrom(property.getClass())) {
			return Collection.class.cast(property).size();
		}
		return 1;
	}

	/**
	 * ネスト構造プロパティのコレクション要素指定用のキーを作成し返します.
	 *
	 * @param prefix コレクション要素までのキー
	 * @param index 添え字
	 * @return コレクション要素指定用のキー
	 */
	public static String createIndexKey(String prefix, int index) {
		return prefix.concat(INDEX_START_TOKEN).concat(Integer.toBinaryString(index)).concat(INDEX_END_TOKEN);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#containsKey(java.lang.String)
	 */
	public static boolean containsKey(String key) {
		return config.containsKey(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigDecimal(java.lang.String,
	 *      java.math.BigDecimal)
	 */
	public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return config.getBigDecimal(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigDecimal(java.lang.String)
	 */
	public static BigDecimal getBigDecimal(String key) {
		return config.getBigDecimal(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigInteger(java.lang.String,
	 *      java.math.BigInteger)
	 */
	public static BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return config.getBigInteger(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigInteger(java.lang.String)
	 */
	public static BigInteger getBigInteger(String key) {
		return config.getBigInteger(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBoolean(java.lang.String,
	 *      boolean)
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBoolean(java.lang.String,
	 *      java.lang.Boolean)
	 */
	public static Boolean getBoolean(String key, Boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBoolean(java.lang.String)
	 */
	public static boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getByte(java.lang.String,
	 *      byte)
	 */
	public static byte getByte(String key, byte defaultValue) {
		return config.getByte(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getByte(java.lang.String,
	 *      java.lang.Byte)
	 */
	public static Byte getByte(String key, Byte defaultValue) {
		return config.getByte(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getByte(java.lang.String)
	 */
	public static byte getByte(String key) {
		return config.getByte(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getDouble(java.lang.String,
	 *      double)
	 */
	public static double getDouble(String key, double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getDouble(java.lang.String,
	 *      java.lang.Double)
	 */
	public static Double getDouble(String key, Double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getDouble(java.lang.String)
	 */
	public static double getDouble(String key) {
		return config.getDouble(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getFloat(java.lang.String,
	 *      float)
	 */
	public static float getFloat(String key, float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getFloat(java.lang.String,
	 *      java.lang.Float)
	 */
	public static Float getFloat(String key, Float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getFloat(java.lang.String)
	 */
	public static float getFloat(String key) {
		return config.getFloat(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getInt(java.lang.String,
	 *      int)
	 */
	public static int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getInt(java.lang.String)
	 */
	public static int getInt(String key) {
		return config.getInt(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getInteger(java.lang.String,
	 *      java.lang.Integer)
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		return config.getInteger(key, defaultValue);
	}

	/**
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getKeys()
	 */
	@SuppressWarnings("unchecked")
	public static Iterator<String> getKeys() {
		return config.getKeys();
	}

	/**
	 * @param prefix
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getKeys(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public static Iterator<String> getKeys(String prefix) {
		return config.getKeys(prefix);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getList(java.lang.String,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getList(String key, List<String> defaultValue) {
		return config.getList(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getList(String key) {
		return config.getList(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getLong(java.lang.String,
	 *      long)
	 */
	public static long getLong(String key, long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getLong(java.lang.String,
	 *      java.lang.Long)
	 */
	public static Long getLong(String key, Long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getLong(java.lang.String)
	 */
	public static long getLong(String key) {
		return config.getLong(key);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getProperties(java.lang.String)
	 */
	public static Properties getProperties(String key) {
		return config.getProperties(key);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getProperty(java.lang.String)
	 */
	public static Object getProperty(String key) {
		return config.getProperty(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getShort(java.lang.String,
	 *      short)
	 */
	public static short getShort(String key, short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getShort(java.lang.String,
	 *      java.lang.Short)
	 */
	public static Short getShort(String key, Short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getShort(java.lang.String)
	 */
	public static short getShort(String key) {
		return config.getShort(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getString(java.lang.String,
	 *      java.lang.String)
	 */
	public static String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getString(java.lang.String)
	 */
	public static String getString(String key) {
		return config.getString(key);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getStringArray(java.lang.String)
	 */
	public static String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	/**
	 * @return
	 * @see org.apache.commons.configuration.Configuration#isEmpty()
	 */
	public static boolean isEmpty() {
		return config.isEmpty();
	}

}
