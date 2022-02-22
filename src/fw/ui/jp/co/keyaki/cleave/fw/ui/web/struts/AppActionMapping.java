/*
 *  �J���V�X�e�� �l���J���V�X�e��
 *  ���쌠      Copyright 2009 cleave
 */
package jp.co.keyaki.cleave.fw.ui.web.struts;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.SessionActionMapping;

/**
 * 
 * カスタムのアクションマッピングクラス。
 * 
 * <p>
 * このクラスは、通常のアクションマッピングに加えて、下記のプロパティを保持します。
 * </p>
 * <ol>
 * <li>アクション起動のログインの必要性</li>
 * <li>トークンチェックタイプ</li>
 * </ol>
 * 
 * @author ytakahashi
 * 
 */
public class AppActionMapping extends SessionActionMapping {

	/**
	 * シリアルバージョン。
	 */
	private static final long serialVersionUID = -6418486993800949090L;

	/**
	 * ログ出力クラス.
	 */
	private static final Log LOG = LogFactory.getLog(AppActionMapping.class);

	/**
	 * トークンタイプのデフォルト値。
	 */
	public static final String DEFAULT_TOKEN_TYPE = "nocheck";

	/**
	 * アクション起動のログインの必要性。
	 * 
	 * true:ログインしてなくてもアクセスできるアクションの場合/false:ログインしていないとアクセスできない場合
	 */
	private boolean isNonSecureAction = false;

	/**
	 * トークンタイプ。
	 */
	private String tokenType;

	/**
	 * コンテンツ初期化アクション。
	 * 
	 * true:コンテンツが初期化（初期表示）されるアクションの場合/false:コンテンツを引き継ぐアクションの場合
	 */
	private boolean isContentsInitAction = false;

	/**
	 * コンストラクタ。
	 * 
	 */
	public AppActionMapping() {
		setTokenType(DEFAULT_TOKEN_TYPE);
	}

	/**
	 * アクション起動のログインの必要性を返します。。
	 * 
	 * @return true:ログインしてなくてもアクセスできるアクションの場合/false:ログインしていないとアクセスできない場合
	 */
	public boolean isNonSecureAction() {
		return isNonSecureAction;
	}

	/**
	 * ノンセキュアアクションかを設定します。
	 * 
	 * @param isNonSecureAction
	 *            true=ノンセキュア/false=セキュア
	 */
	public void setNonSecureAction(boolean isNonSecureAction) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("setNonSecureAction(" + isNonSecureAction + ")");
		}
		this.isNonSecureAction = isNonSecureAction;
	}

	/**
	 * トークンタイプを返します。
	 * 
	 * @return トークンタイプ
	 */
	public String getTokenType() {
		return this.tokenType;
	}

	/**
	 * トークンタイプを設定します。
	 * 
	 * @param tokenType
	 *            トークンタイプ
	 */
	public void setTokenType(String tokenType) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("setTokenType(" + tokenType + ")");
		}
		this.tokenType = tokenType;
	}

	/**
	 * コンテンツが初期化されるアクションかを返します。
	 * 
	 * @return true:初期化される/false:コンテンツを引き継ぐ
	 */
	public boolean isContentsInitAction() {
		return isContentsInitAction;
	}

	/**
	 * コンテンツが初期化されるアクションかを設定します。
	 * 
	 * @param isContentsInitAction
	 *            true:初期化される/false:コンテンツを引き継ぐ
	 */
	public void setContentsInitAction(boolean isContentsInitAction) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("setContentsInitAction(" + isContentsInitAction + ")");
		}
		this.isContentsInitAction = isContentsInitAction;
	}

	/**
	 * このオブジェクトの文字列表現を返します.
	 * 
	 * @return オブジェクトの文字列表現
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
