package jp.co.keyaki.cleave.fw.dao.template.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.DaoMessageDefine;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;

/**
 * テンプレートから読み取ったSQL文の一部分を表現する抽象クラス。
 *
 * @author ytakahashi
 *
 */
public abstract class SQLPart {

	/**
	 * パラメータ開始宣言。
	 */
	public static final String PERT_VARIABLE_PREFIX = "${";

	/**
	 * パラメータ終了宣言。
	 */
	public static final String PERT_VARIABLE_SUFFIX = "}";

	/**
	 * 子要素への参照。
	 */
	private List<SQLPart> childSqlParts = new LinkedList<SQLPart>();

	/**
	 * このインスタンスが保持している一部分SQL文
	 */
	private String sqlContent = "";

	/**
	 * このインスタンスへ子要素を追加します。
	 *
	 * @param part
	 *            子要素
	 */
	public void addChildSQLPart(SQLPart part) {
		childSqlParts.add(part);
	}

	/**
	 *
	 * このインスタンスが保持している一部分SQL文を返却します。
	 *
	 * @return このインスタンスが保持している一部分SQL文
	 */
	public String getSqlContent() {
		return this.sqlContent;
	}

	/**
	 *
	 * このインスタンスに一部分SQL文を設定します。
	 *
	 * @param sqlContent
	 *            一部分SQL文
	 */
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	/**
	 *
	 * 第一引数のSQL情報に対して、SQL文／パラメータを構築します。
	 *
	 * @param sqlInfo
	 *            構築するSQL情報
	 * @param templates
	 *            設定情報
	 * @param parameters
	 *            SQLパラメータ
	 * @throws DaoException
	 *             SQL情報生成時に例外が発生した場合
	 */
	protected abstract void buildSQLInfo(XMLSQLInfo sqlInfo, XMLSQLTemplates templates, SQLParameters parameters)
			throws DaoException;

	/**
	 *
	 * 子要素に対してSQL情報を構築させる。
	 *
	 * <p>
	 * 内部的には、このインスタンスが保持している子要素の
	 * {@link SQLPart#buildSQLInfo(XMLSQLInfo, XMLSQLTemplates, SQLParameters)}
	 * を呼び出します。
	 * </p>
	 *
	 * @param sqlInfo
	 *            構築するSQL情報
	 * @param templates
	 *            設定情報
	 * @param parameters
	 *            SQLパラメータ
	 * @throws DaoException
	 *             SQL情報生成時に例外が発生した場合
	 * @see SQLPart#buildSQLInfo(XMLSQLInfo, XMLSQLTemplates, SQLParameters)
	 */
	protected void buildSQLInfoChild(XMLSQLInfo sqlInfo, XMLSQLTemplates templates, SQLParameters parameters)
			throws DaoException {
		for (SQLPart parts : childSqlParts) {
			parts.buildSQLInfo(sqlInfo, templates, parameters);
		}
	}

	/**
	 *
	 * SQLテンプレート内に記述されているバインドパラメータ名をJDBCのバインドパラメータ形式に変換しパラメータをセットします。
	 *
	 * XMLに記述されているバンドパラメータ（書式：${bindPrameterName}）を、JDBC形式のバインドパラメーター（書式：?）に
	 * 置き換えます。また、{@link SQLParameters}に設定されている同名のパラメータ値を、JDBCのバインドパラメータとして
	 * {@link XMLSQLInfo} に設定します.
	 *
	 * @param sqlInfo SQL情報（JDBCSQL構築先およびバインドパラメータ設定先オブジェクト）
	 * @param parameters バインドパラメータ
	 * @throws DaoException 必要なバインドパラメータが{@link SQLParameters}に存在しなかった場合
	 */
	protected void replaceVariableAndSetParameter(XMLSQLInfo sqlInfo, SQLParameters parameters) throws DaoException {

		// XML上のSQL(${xxxx}形式のSQL)を取得します
		String work = getSqlContent();

		while (true) {

			// バインドパラメータ(${xxxx}形式のSQL)の名称部分（xxxx）の取り出し
			String bindParameterName = StringUtils.substringBetween(work, PERT_VARIABLE_PREFIX, PERT_VARIABLE_SUFFIX);
			// バインドパラメータが存在しない場合はバインドパラメータ置き換え処理終了
			if (bindParameterName == null) {
				break;
			}

			// 置き換え用文字列
			String replaceVariableName = getReplaceVariableName(bindParameterName);

			// パラメータオブジェクトにバインドパラメータ名が設定されていなかった場合は例外
			if (!parameters.containsVarName(bindParameterName)) {
				throw new DaoException(DaoMessageDefine.E000020, replaceVariableName);
			}

			// パラメータ値をコレクションとして取得（元の要素が１であってもコレクションとして取得）
			Collection<Object> paramValues = toCollection(parameters.getParam(bindParameterName));

			// XML形式のバインドパラメータ表記をJDBC形式のバインドパラメータ表記に置き換え
			work = StringUtils.replace(work, replaceVariableName, createBindingWord(paramValues.size()), 1);

			// SQL情報にバインドパラメータ値を追加
			for (Object value : paramValues) {
				sqlInfo.addParameter(value);
			}
		}
		// SQL情報にSQLを追加
		sqlInfo.appendFragment(work);
	}

	/**
	 * 引数のオブジェクトをコレクション型の要素へ変換し返します.
	 *
	 * 引数が集合（配列またはコレクション）の場合は、その集合そのものをコレクションとして返します.
	 * 引数が集合ではない場合、コレクションの１要素となるように変換し返します.
	 *
	 * @param paramValues コレクションへ変換するオブジェクト.
	 * @return コレクション
	 */
	@SuppressWarnings("unchecked")
	protected Collection<Object> toCollection(Object paramValues) {

		// 引数配列の場合はList形式にして返します
		if (paramValues instanceof Object[]) {
			return Arrays.asList((Object[]) paramValues);
		}

		// 引数がコレクションの場合はそのまま返します
		if (paramValues instanceof Collection) {
			return (Collection<Object>) paramValues;
		}

		// 配列・コレクション以外の場合はListの要素として返します
		List<Object> collection = new ArrayList<Object>(1);
		collection.add(paramValues);
		return collection;
	}

	/**
	 * 指定個数のJDBCのバインドパラメータ形式を作成し返します.
	 *
	 * @param count 作成するバインドパラメータ個数
	 * @return JDBCバインドパラメータ
	 */
	protected String createBindingWord(int count) {
		StringBuilder sb = new StringBuilder(count + (count - 1) * 2);
		for (int i = 0; i < count; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("?");
		}
		return sb.toString();
	}

	/**
	 * XML形式のバインドパラメータ置換文字を作成し返します.
	 *
	 * @param varName バインドパラメーター名
	 * @return 置換用バインドパラメータ名
	 */
	protected String getReplaceVariableName(String varName) {
		return new StringBuilder().append(PERT_VARIABLE_PREFIX).append(varName).append(PERT_VARIABLE_SUFFIX).toString();
	}

	/**
	 * 子ノードのインスタンスの文字列表現を返します.
	 *
	 * @return 子ノードのオブジェクトの文字列表現
	 */
	protected String toStringChildSQLPart() {
		StringBuilder toString = new StringBuilder();
		for (SQLPart parts : childSqlParts) {
			toString.append(" ");
			toString.append(parts.toString());
		}
		return toString.toString();
	}

	/**
	 * このインスタンスの文字列表現を返します.
	 *
	 * @return オブジェクトの文字列表現
	 */
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append(getSqlContent());
		toString.append(toStringChildSQLPart());
		return toString.toString();
	}

}
