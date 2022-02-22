package jp.co.keyaki.cleave.fw.ui.web.taglib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.WriteTag;

import jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil.JavaScriptVariableDefine;
import jp.co.keyaki.cleave.fw.ui.web.taglib.jsutil.JavaScriptVariableDefineFactory;

/**
 * JavaBean Collection を JavaScript の配列へ変換するタグライブラリ。
 * <p>
 * 生成される JavaScript の配列内の要素は、JSON(JavaScript Object Notation)形式で生成されます。
 * </p>
 * <p>
 * JSONで出力される JavaBean のプロパティはすべて文字列として出力されます。
 * </p>
 * 
 * @author ytakahashi
 */
public class JavaScriptCollectionBeanWriteTag extends WriteTag {

	/**
	 * シリアルバージョンID。
	 */
	private static final long serialVersionUID = -1372344757320661554L;

	protected JavaScriptVariableDefineFactory factory = JavaScriptVariableDefineFactory.INSTANCE;

	/**
	 * JavaScript配列変数名。
	 */
	private String javaScriptArrayName;

	/**
	 * nullプロパティ置換文字。
	 */
	private String nullStr = null;;

	/**
     *
     */
	private final List<String> nonSerializePropertyNames = new ArrayList<String>();

	public JavaScriptCollectionBeanWriteTag() {
		initNonSerializePropertyNames();
	}

	/**
	 * タグ評価開始メソッド。
	 * <p>
	 * 当メソッド内で、タグライブラリの属性として指定された、<code>scope</code>、<code>name</code>、
	 * <code>property</code>にて 属性を検索し、見つからない場合、またはインスタンスが
	 * <code>java.util.Collection</code>で評価できない場合は メソッドがスキップされます。
	 * </p>
	 * 
	 * @see org.apache.struts.taglib.bean.WriteTag#doStartTag()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {

		Object value = TagUtils.getInstance().lookup(pageContext, name, property, scope);
		if (value == null) {
			return SKIP_BODY;
		}
		if (value.getClass().isArray()) {
			// Class<?> elementType = value.getClass().getComponentType();
			value = Arrays.asList(value);
		}
		if (!(value instanceof Collection)) {
			return SKIP_BODY;
		}
		Collection<Object> collection = (Collection<Object>) value;
		StringBuilder javaScript = new StringBuilder();
		javaScript.append(JavaScriptHelper.JS_DEFINE_START);
		javaScript.append("var ").append(javaScriptArrayName);
		javaScript.append(" = new Array(").append(collection.size()).append(");\n");
		try {
			serializeCollection(javaScript, collection);
		} catch (Exception e) {
			throw new JspException(e);
		}
		javaScript.append(JavaScriptHelper.JS_DEFINE_END);
		TagUtils.getInstance().write(pageContext, javaScript.toString());
		return SKIP_BODY;
	}

	/**
	 * Java Collection をJSON形式に変換します。
	 * 
	 * @param javaScript
	 *            JavaScript生成先
	 * @param collection
	 *            JavaScript配列に変換する Java Collection
	 * @throws Exception
	 *             処理中に例外が発生した場合
	 */
	protected void serializeCollection(StringBuilder javaScript, Collection<Object> collection) throws Exception {
		int index = 0;
		for (Object object : collection) {
			javaScript.append(javaScriptArrayName).append("[").append(index).append("]").append(" = ");
			serializeObject(javaScript, object);
			javaScript.append(";\n");
			index++;
		}
	}

	/**
	 * Java Bean をJSON形式に変換します。
	 * <p>
	 * Java Bean の読み出し可能のプロパティをJSON形式にてJavaScriptに変換します。
	 * </p>
	 * <p>
	 * ただし、下記のプロパティ名についてはJava言語固有のプロパティなのでJavaScriptに変換しません。
	 * <ul>
	 * <li>class</li>
	 * </ul>
	 * </p>
	 * 
	 * @param javaScript
	 *            JavaScript生成先
	 * @param object
	 *            JSON形式に変換する Java Bean
	 * @throws Exception
	 *             処理中に例外が発生した場合
	 */
	@SuppressWarnings("unchecked")
	protected void serializeObject(StringBuilder javaScript, Object object) throws Exception {
		Map<String, Object> properties = PropertyUtils.describe(object);
		javaScript.append("{");
		boolean isFirst = true;
		for (Map.Entry<String, Object> property : properties.entrySet()) {
			String propertyName = property.getKey();
			Object propertyValue = property.getValue();
			if (nonSerializePropertyNames.contains(propertyName)) {
				continue;
			}
			Object value = nullStr;
			if (propertyValue != null) {
				value = propertyValue;
			}
			JavaScriptVariableDefine variableDefine = factory.getVariableDefine(value, null);
			if (variableDefine == null) {
				continue;
			}
			if (!isFirst) {
				javaScript.append(", ");
			}
			javaScript.append(propertyName).append(":").append(variableDefine.define(value));
			isFirst = false;
		}
		javaScript.append("}");
	}

	/**
	 * 生成するJavaScript配列変数名を設定します。
	 * 
	 * @param javaScriptArrayName
	 *            JavaScript配列変数名
	 */
	public void setJavaScriptArrayName(String javaScriptArrayName) {
		this.javaScriptArrayName = javaScriptArrayName;
	}

	/**
	 * プロパティ値がnullの際に置き換える文字列を設定します。
	 * <p>
	 * デフォルトは空文字が設定されています。
	 * </p>
	 * 
	 * @param nullStr
	 */
	public void setNullStr(String nullStr) {
		this.nullStr = nullStr;
	}

	/**
	 * タグライブラリのインスタンス初期化処理。
	 * <p>
	 * 当クラスにてインスタンス変数javaScriptArrayNameを定義しているが、 必須であるため、あえて初期化は行っていない。
	 * </p>
	 * 
	 * @see org.apache.struts.taglib.bean.WriteTag#release()
	 */
	@Override
	public void release() {
		setNullStr(null);
		initNonSerializePropertyNames();
		super.release();
	}

	public void setNonSerializePropertyNames(String propertyNames) {

		initNonSerializePropertyNames();
		String[] props = propertyNames.split(",");
		nonSerializePropertyNames.addAll(Arrays.asList(props));
	}

	protected void initNonSerializePropertyNames() {
		nonSerializePropertyNames.clear();
		nonSerializePropertyNames.add("class");
	}
}
