package jp.co.keyaki.cleave.fw.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * SQL発行に必要なパラメータを表すクラス。
 *
 * @author ytakahashi
 *
 */
public class SQLParameters {

	/**
	 * 内包しているパラメータのキーに対して付与するセパレータ。
	 */
	public static final String COMPOSITE_KEY_SEP = ".";

	/**
	 * 内包しているパラメータでは無い場合に利用するキーに対するプレフィックス。
	 */
	private static final String NON_COMPOSITE_PREFIX_KEY = "";

	/**
	 * このインスタンス自体が保持しているパラメータマップ。
	 */
	private Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * このクラスが内包している他のインスタンスへのパラメータマップ。
	 */
	private Map<String, SQLParameters> compositeParams = new HashMap<String, SQLParameters>();

	/**
	 * デフォルトコンストラクタ。
	 *
	 */
	public SQLParameters() {
	}

	/**
	 * 他のパラメータクラスをこのインスタンスへ内包します。
	 *
	 * @param compositeSqlIdOrAlias
	 *            内包する際に付与するプレフィックス
	 * @param parameters
	 *            内包するパラメータ
	 */
	public void compose(String compositeSqlIdOrAlias, SQLParameters parameters) {
		compositeParams.put(compositeSqlIdOrAlias, parameters);
	}

	/**
	 * 内包しているパラメータを返却します。
	 *
	 * @param compositeSqlIdOrAlias
	 *            内包する際に付与したプレフィックス
	 * @return 内包しているパラメータ
	 */
	public SQLParameters getCompositeSQLParameters(String compositeSqlIdOrAlias) {
		return compositeParams.get(compositeSqlIdOrAlias);
	}

	/**
	 * このインスタンスへパラメータを追加します。
	 *
	 * @param varName
	 *            パラメータ名
	 * @param value
	 *            パラメータ値
	 */
	public void addParameter(String varName, Object value) {
		if (varName.contains(COMPOSITE_KEY_SEP)) {
			throw new IllegalArgumentException("cannot be included in a name. '" + COMPOSITE_KEY_SEP + "'");
		}
		params.put(varName, value);
	}

	/**
	 * 現在このインスタンスそのものに設定されているされているパラメータを初期化します。
	 *
	 * <p>
	 * このメソッドは{@link SQLParameters#reset(boolean)}の第１引数に<code>false</code>を指定して
	 * 呼び出している行っている事と同様です。
	 * <p>
	 *
	 * @see SQLParameters#reset(boolean)
	 */
	public void reset() {
		reset(false);
	}

	/**
	 * 現在設定されているパラメータを初期化します。
	 *
	 * <p>
	 * 第１引数に<code>true</code>を設定した場合、内包しているパラメータへの参照を破棄します。<br>
	 * つまり内包しているパラメータが保持しているパラメータへの操作は一切行いません。
	 * </p>
	 *
	 * @param isResetComopositeParamRef
	 *            true:内包しているパラメータへの参照を破棄する場合/false:
	 *            このインスタンスそのものが保持しているパラメータを破棄する場合
	 */
	public void reset(boolean isResetComopositeParamRef) {
		params.clear();
		if (!isResetComopositeParamRef) {
			return;
		}
		compositeParams.clear();
	}

	/**
	 * このパラメータがパラメータ名を保持しているか判定します。
	 *
	 * @param varName
	 *            パラメータ名
	 * @return true:パラメータを保持している場合/false:パラメータを保持していない場合
	 */
	public boolean containsVarName(String varName) {
		return getVarNames().contains(varName);
	}

	/**
	 * このパラメータが保持しているパラメータ名一覧を返却します。
	 *
	 * @return パラメータ名一覧
	 */
	public Set<String> getVarNames() {
		return getVarNames(NON_COMPOSITE_PREFIX_KEY);
	}

	/**
	 * パラメータ名の先頭が一致する名称一覧を返却します。
	 *
	 * @param prefix 検索する先頭パラメータ名
	 * @return パラメータ名の左側が一致する一覧
	 */
	protected Set<String> getVarNames(String prefix) {
		String compositePrefix = getCompositePrefix(prefix);
		Set<String> varNames = new HashSet<String>();
		for (String varName : params.keySet()) {
			varNames.add(compositePrefix + varName);
		}
		for (Map.Entry<String, SQLParameters> compositeParam : compositeParams.entrySet()) {
			String compositeSqlIdOrAlias = compositeParam.getKey();
			SQLParameters composite = compositeParam.getValue();
			varNames.addAll(composite.getVarNames(compositePrefix + compositeSqlIdOrAlias));
		}
		return varNames;
	}

	/**
	 * パラメータ値を返却します。
	 *
	 * @param varName 取得するパラメータ名
	 * @return パラメータ値（パラメータが存在しない場合はnull）
	 */
	public Object getParam(String varName) {
		Object param = params.get(varName);
		if (param != null) {
			return param;
		}
		for (Map.Entry<String, SQLParameters> compositeParam : compositeParams.entrySet()) {
			String prefix = getCompositePrefix(compositeParam.getKey());
			if (varName.startsWith(prefix)) {
				SQLParameters composite = compositeParam.getValue();
				return composite.getParam(varName.substring(prefix.length()));
			}
		}
		return null;
	}

	/**
	 * 内包しているパラメータの接頭値になる合成パラメータ名を返却します。
	 *
	 * @param prefix エンクロージャーパラメータ名
	 * @return 合成されたエンクロージャーパラメータ名
	 */
	protected String getCompositePrefix(String prefix) {
		String compositePrefix = NON_COMPOSITE_PREFIX_KEY;
		if (StringUtils.isNotEmpty(prefix)) {
			compositePrefix = prefix + COMPOSITE_KEY_SEP;
		}
		return compositePrefix;
	}

	/**
	 * このオブジェクトの文字列表現を返します。
	 *
	 * @return インスタンスの文字列表現
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
