package jp.co.kts.ui.web.struts;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class WebConst {

	/**
	 * クッキー名：最終ログインユーザID
	 */
	public static final String COOKIE_LOGIN_USER_ID_NAME = "LastLoginUserID";

	/**
	 * クッキー有効期限：一ヶ月(1日(86400秒) * 30日)
	 */
	public static final int COOKIE_MAX_AGE = 86400 * 30;

	/**
	 * 大分類コード：
	 */
	public static final int GROUP_CODE_LARGE = 1;
	/**
	 * 大分類名：
	 */
	public static final String GROUP_NAME_LARGE = "大";

	/**
	 * 中分類コード：
	 */
	public static final int GROUP_CODE_MIDDLE = 2;
	/**
	 * 中分類名：
	 */
	public static final String GROUP_NAME_MIDDLE = "中";

	/**
	 * 小分類コード：
	 */
	public static final int GROUP_CODE_SMALL = 3;
	/**
	 * 小分類名：
	 */
	public static final String GROUP_NAME_SMALL = "小";

	public static final Map<Integer, String> GROUP_MAP;

	public static int CSV_INPUT_LENGTH = 5;


	static {
		Map<Integer, String> groupMap = new LinkedHashMap<Integer, String>();
		groupMap.put(0, StringUtils.EMPTY);
		groupMap.put(GROUP_CODE_LARGE, GROUP_NAME_LARGE);
		groupMap.put(GROUP_CODE_MIDDLE, GROUP_NAME_MIDDLE);
		groupMap.put(GROUP_CODE_SMALL, GROUP_NAME_SMALL);
		GROUP_MAP = Collections.unmodifiableMap(groupMap);
	}

	public static final String CSV_FILE_NAME_HEADER = "OS";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_ORDER_DATE = "1";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_ORDER_DATE = "受注日";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_ORDER_NO = "2";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_ORDER_NO = "受注番号";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_ORDER_NM = "3";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_ORDER_NM = "注文者名";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_CHARGE = "4";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_CHARGE = "請求額";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_ACCOUNT_METHOD = "5";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_ACCOUNT_METHOD = "支払方法";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_DESTINATION_NM = "6";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_DESTINATION_NM = "届け先";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_INVOICE_CLASS_IFICATION = "7";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_INVOICE_CLASS_IFICATION = "送り状種別";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_DESTINATION_APPOINT_DATE = "8";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_DESTINATION_APPOINT_DATE = "配送指定日";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_DESTINATION_APPOINT_TIME = "9";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_DESTINATION_APPOINT_TIME = "時間帯指定";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_SHIPMENT_PLAN_DATE = "10";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_SHIPMENT_PLAN_DATE = "出荷予定日";


	public static final Map<String, String> SALE_SEARCH_MAP;
	static {
		Map<String, String> saleSearchMap = new LinkedHashMap<String, String>();
	//	groupMap.put(0, StringUtils.EMPTY);
		saleSearchMap.put(SALE_SEARCH_CODE_ORDER_DATE, SALE_SEARCH_NAME_ORDER_DATE);
		saleSearchMap.put(SALE_SEARCH_CODE_ORDER_NO, SALE_SEARCH_NAME_ORDER_NO);
		saleSearchMap.put(SALE_SEARCH_CODE_ORDER_NM, SALE_SEARCH_NAME_ORDER_NM);
		saleSearchMap.put(SALE_SEARCH_CODE_CHARGE, SALE_SEARCH_NAME_CHARGE);
		saleSearchMap.put(SALE_SEARCH_CODE_ACCOUNT_METHOD, SALE_SEARCH_NAME_ACCOUNT_METHOD);
		saleSearchMap.put(SALE_SEARCH_CODE_DESTINATION_NM, SALE_SEARCH_NAME_DESTINATION_NM);
		saleSearchMap.put(SALE_SEARCH_CODE_INVOICE_CLASS_IFICATION, SALE_SEARCH_NAME_INVOICE_CLASS_IFICATION);
		saleSearchMap.put(SALE_SEARCH_CODE_DESTINATION_APPOINT_DATE, SALE_SEARCH_NAME_DESTINATION_APPOINT_DATE);
		saleSearchMap.put(SALE_SEARCH_CODE_DESTINATION_APPOINT_TIME, SALE_SEARCH_NAME_DESTINATION_APPOINT_TIME);
		saleSearchMap.put(SALE_SEARCH_CODE_SHIPMENT_PLAN_DATE, SALE_SEARCH_NAME_SHIPMENT_PLAN_DATE);
		SALE_SEARCH_MAP = Collections.unmodifiableMap(saleSearchMap);
	}

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_SORT_ASEND = "1";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_SORT_ASEND = "昇順";

	/**
	 * ：
	 */
	public static final String SALE_SEARCH_CODE_SORT_DESEND = "2";
	/**
	 * ：
	 */
	public static final String SALE_SEARCH_NAME_SORT_DESEND = "降順";


	public static final Map<String, String> SALE_SEARCH_SORT_ORDER;


	static {
		Map<String, String> saleSearchSortOrder = new LinkedHashMap<String, String>();
	//	groupMap.put(0, StringUtils.EMPTY);
		saleSearchSortOrder.put(SALE_SEARCH_CODE_SORT_DESEND, SALE_SEARCH_NAME_SORT_DESEND);
		saleSearchSortOrder.put(SALE_SEARCH_CODE_SORT_ASEND, SALE_SEARCH_NAME_SORT_ASEND);
		SALE_SEARCH_SORT_ORDER = Collections.unmodifiableMap(saleSearchSortOrder);
	}


	/**
	 *
	 */
	public static final String ALERT_TYPE_REGIST = "1";

	public static final String ALERT_TYPE_UPDATE = "2";

	public static final String ALERT_TYPE_DELETE = "3";

	public static final String ALERT_TYPE_ARRIVAL = "4";

	/**
	 * 出庫済アラート
	 */
	public static final String ALERT_TYPE_LEAVE = "5";



	/**
	 * 商品詳細画面：商品発注アラート
	 */
	public static final int MST_ITEM_ORDER_ALERT = 3;
	/**
	 * 商品詳細画面：入荷予定上限
	 */
	public static final int ADD_ARRIVAL_SCHEDULE_LENGTH = 10;
	/**
	 * 商品詳細画面：バックオーダー上限
	 */
	public static final int ADD_BACK_ORDER_LENGTH = 5;
	/**
	 * 商品詳細画面：キープ上限
	 */
	public static final int ADD_KEEP_LENGTH = 5;

	/**
	 * 商品詳細画面:不良在庫上限
	 */
	public static final int ADD_DEAD_STOCK_LENGTH = 5;

	/**
	 * セット商品詳細画面：構成商品数上限
	 */
	public static final int ADD_SET_ITEM_LENGTH = 10;


	/**
	 * クレジットカード
	 */
	public static final String ACCOUNT_METHOD_CODE_1 = "クレジットカード";
	/** クレジットカード */
	public static final String ACCOUNT_METHOD_NAME_1 = "クレジットカード";
	/** 代金引換 */
	public static final String ACCOUNT_METHOD_CODE_2 = "代金引換";
	/** 代金引換 */
	public static final String ACCOUNT_METHOD_NAME_2 = "代金引換";
	/** 代金引換（カード） */
	public static final String ACCOUNT_METHOD_CODE_3 = "代金引換（カード）";
	/** 代金引換（カード） */
	public static final String ACCOUNT_METHOD_NAME_3 = "代金引換（カード）";
	/** 銀行振込（前払い） */
	public static final String ACCOUNT_METHOD_CODE_4 = "銀行振込（前払い）";
	/** 銀行振込（前払い） */
	public static final String ACCOUNT_METHOD_NAME_4 = "銀行振込（前払い）";
	/** 銀行振込（後払い） */
	public static final String ACCOUNT_METHOD_CODE_5 = "銀行振込（後払い）";
	/** 銀行振込（後払い） */
	public static final String ACCOUNT_METHOD_NAME_5 = "銀行振込（後払い）";
	/** 銀行ネット */
	public static final String ACCOUNT_METHOD_CODE_6 = "銀行ネット";
	/** 銀行ネット */
	public static final String ACCOUNT_METHOD_NAME_6 = "銀行ネット";
	/** 郵貯振替（前払い） */
	public static final String ACCOUNT_METHOD_CODE_7 = "郵貯振替（前払い）";
	/** 郵貯振替（前払い） */
	public static final String ACCOUNT_METHOD_NAME_7 = "郵貯振替（前払い）";
	/** 郵貯振替（後払い） */
	public static final String ACCOUNT_METHOD_CODE_8 = "郵貯振替（後払い）";
	/** 郵貯振替（後払い） */
	public static final String ACCOUNT_METHOD_NAME_8 = "郵貯振替（後払い）";
	/** コンビニ（前払い） */
	public static final String ACCOUNT_METHOD_CODE_9 = "コンビニ（前払い）";
	/** コンビニ（前払い） */
	public static final String ACCOUNT_METHOD_NAME_9 = "コンビニ（前払い）";
	/** コンビニ（後払い） */
	public static final String ACCOUNT_METHOD_CODE_10 = "コンビニ（後払い）";
	/** コンビニ（後払い） */
	public static final String ACCOUNT_METHOD_NAME_10 = "コンビニ（後払い）";
	/** コンビニ（前払い） */
	public static final String ACCOUNT_METHOD_CODE_11 = "ぺイジー（前払い）";
	/** コンビニ（前払い） */
	public static final String ACCOUNT_METHOD_NAME_11 = "ぺイジー（前払い）";
	/** ショッピングローン */
	public static final String ACCOUNT_METHOD_CODE_12 = "ショッピングローン";
	/** ショッピングローン */
	public static final String ACCOUNT_METHOD_NAME_12 = "ショッピングローン";
	/** 楽天バンク決済 */
	public static final String ACCOUNT_METHOD_CODE_13 = "楽天バンク決済";
	/** 楽天バンク決済 */
	public static final String ACCOUNT_METHOD_NAME_13 = "楽天バンク決済";
	/** 楽天コンビニ決済 */
	public static final String ACCOUNT_METHOD_CODE_14 = "楽天コンビニ決済";
	/** 楽天コンビニ決済 */
	public static final String ACCOUNT_METHOD_NAME_14 = "楽天コンビニ決済";
	/** Yahoo!コンビニ決済 */
	public static final String ACCOUNT_METHOD_CODE_15 = "Yahoo!コンビニ決済";
	/** Yahoo!コンビニ決済 */
	public static final String ACCOUNT_METHOD_NAME_15 = "Yahoo!コンビニ決済";
	/** Yahoo!かんたん決済 */
	public static final String ACCOUNT_METHOD_CODE_16 = "Yahoo!かんたん決済";
	/** Yahoo!かんたん決済 */
	public static final String ACCOUNT_METHOD_NAME_16 = "Yahoo!かんたん決済";
	/** Yahoo!ウォレット */
	public static final String ACCOUNT_METHOD_CODE_17 = "Yahoo!ウォレット";
	/** Yahoo!ウォレット */
	public static final String ACCOUNT_METHOD_NAME_17 = "Yahoo!ウォレット";
	/** Amazonへのお支払い */
	public static final String ACCOUNT_METHOD_CODE_18 = "Amazonへのお支払い";
	/** Amazonへのお支払い */
	public static final String ACCOUNT_METHOD_NAME_18 = "Amazonへのお支払い";
	/** まとめてau支払い */
	public static final String ACCOUNT_METHOD_CODE_19 = "まとめてau支払い";
	/** まとめてau支払い */
	public static final String ACCOUNT_METHOD_NAME_19 = "まとめてau支払い";
	/** 銀行振込（後払い） */
	public static final String ACCOUNT_METHOD_CODE_20 = "銀行振込（後払い）";
	/** 銀行振込（後払い） */
	public static final String ACCOUNT_METHOD_NAME_20 = "銀行振込（後払い）";
	/** auかんたん決済[前払い] */
	public static final String ACCOUNT_METHOD_CODE_21 = "auかんたん決済[前払い]";
	/** auかんたん決済[前払い] */
	public static final String ACCOUNT_METHOD_NAME_21 = "auかんたん決済[前払い]";
	/** auかんたん決済 */
	public static final String ACCOUNT_METHOD_CODE_22 = "auかんたん決済";
	/** auかんたん決済 */
	public static final String ACCOUNT_METHOD_NAME_22 = "auかんたん決済";
	/** ドコモケータイ払い */
	public static final String ACCOUNT_METHOD_CODE_23 = "ドコモケータイ払い";
	/** ドコモケータイ払い */
	public static final String ACCOUNT_METHOD_NAME_23 = "ドコモケータイ払い";
	/** クレジット決済（iD） */
	public static final String ACCOUNT_METHOD_CODE_24 = "クレジット決済（iD）";
	/** クレジット決済（iD） */
	public static final String ACCOUNT_METHOD_NAME_24 = "クレジット決済（iD）";
	/** ソフトバンクケータイ支払い */
	public static final String ACCOUNT_METHOD_CODE_25 = "ソフトバンクケータイ支払い";
	/** ソフトバンクケータイ支払い */
	public static final String ACCOUNT_METHOD_NAME_25 = "ソフトバンクケータイ支払い";
	/** ソフトバンクまとめて支払い */
	public static final String ACCOUNT_METHOD_CODE_26 = "ソフトバンクまとめて支払い";
	/** ソフトバンクまとめて支払い */
	public static final String ACCOUNT_METHOD_NAME_26 = "ソフトバンクまとめて支払い";
	/** 自分銀行決済 */
	public static final String ACCOUNT_METHOD_CODE_27 = "自分銀行決済";
	/** 自分銀行決済 */
	public static final String ACCOUNT_METHOD_NAME_27 = "自分銀行決済";
	/** 国際取引決済 */
	public static final String ACCOUNT_METHOD_CODE_28 = "国際取引決済";
	/** 国際取引決済 */
	public static final String ACCOUNT_METHOD_NAME_28 = "国際取引決済";
	/** PayPal決済 */
	public static final String ACCOUNT_METHOD_CODE_29 = "PayPal決済";
	/** PayPal決済 */
	public static final String ACCOUNT_METHOD_NAME_29 = "PayPal決済";
	/** 現金書留 */
	public static final String ACCOUNT_METHOD_CODE_30 = "現金書留";
	/** 現金書留 */
	public static final String ACCOUNT_METHOD_NAME_30 = "現金書留";
	/** NP後払い */
	public static final String ACCOUNT_METHOD_CODE_31 = "NP後払い";
	/** NP後払い */
	public static final String ACCOUNT_METHOD_NAME_31 = "NP後払い";
	/** 電子マネー */
	public static final String ACCOUNT_METHOD_CODE_32 = "電子マネー";
	/** 電子マネー */
	public static final String ACCOUNT_METHOD_NAME_32 = "電子マネー";
	/** 店頭払い */
	public static final String ACCOUNT_METHOD_CODE_33 = "店頭払い";
	/** 店頭払い */
	public static final String ACCOUNT_METHOD_NAME_33 = "店頭払い";
	/** ご来店決済 */
	public static final String ACCOUNT_METHOD_CODE_34 = "ご来店決済";
	/** ご来店決済 */
	public static final String ACCOUNT_METHOD_NAME_34 = "ご来店決済";
	/** 全額ポイント払い */
	public static final String ACCOUNT_METHOD_CODE_35 = "全額ポイント払い";
	/** 全額ポイント払い */
	public static final String ACCOUNT_METHOD_NAME_35 = "全額ポイント払い";
	/** 全額ポイント払い[残金有] */
	public static final String ACCOUNT_METHOD_CODE_36 = "全額ポイント払い[残金有]";
	/** 全額ポイント払い[残金有] */
	public static final String ACCOUNT_METHOD_NAME_36 = "全額ポイント払い[残金有]";
	/** EC決済（前払い） */
	public static final String ACCOUNT_METHOD_CODE_37 = "EC決済（前払い）";
	/** EC決済（前払い） */
	public static final String ACCOUNT_METHOD_NAME_37 = "EC決済（前払い）";
	/** EC決済（後払い） */
	public static final String ACCOUNT_METHOD_CODE_38 = "EC決済（後払い）";
	/** EC決済（後払い） */
	public static final String ACCOUNT_METHOD_NAME_38 = "EC決済（後払い）";

	/**
	 * 決済方法
	 */
	public static final Map<String, String> ACCOUNT_METHOD_MAP;

	static {
		Map<String, String> accountMethodMap = new LinkedHashMap<String, String>();
		accountMethodMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_1, ACCOUNT_METHOD_NAME_1);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_2, ACCOUNT_METHOD_NAME_2);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_3, ACCOUNT_METHOD_NAME_3);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_4, ACCOUNT_METHOD_NAME_4);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_5, ACCOUNT_METHOD_NAME_5);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_6, ACCOUNT_METHOD_NAME_6);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_7, ACCOUNT_METHOD_NAME_7);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_8, ACCOUNT_METHOD_NAME_8);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_9, ACCOUNT_METHOD_NAME_9);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_10, ACCOUNT_METHOD_NAME_10);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_11, ACCOUNT_METHOD_NAME_11);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_12, ACCOUNT_METHOD_NAME_12);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_13, ACCOUNT_METHOD_NAME_13);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_14, ACCOUNT_METHOD_NAME_14);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_15, ACCOUNT_METHOD_NAME_15);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_16, ACCOUNT_METHOD_NAME_16);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_17, ACCOUNT_METHOD_NAME_17);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_18, ACCOUNT_METHOD_NAME_18);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_19, ACCOUNT_METHOD_NAME_19);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_20, ACCOUNT_METHOD_NAME_20);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_21, ACCOUNT_METHOD_NAME_21);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_22, ACCOUNT_METHOD_NAME_22);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_23, ACCOUNT_METHOD_NAME_23);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_24, ACCOUNT_METHOD_NAME_24);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_25, ACCOUNT_METHOD_NAME_25);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_26, ACCOUNT_METHOD_NAME_26);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_27, ACCOUNT_METHOD_NAME_27);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_28, ACCOUNT_METHOD_NAME_28);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_29, ACCOUNT_METHOD_NAME_29);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_30, ACCOUNT_METHOD_NAME_30);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_31, ACCOUNT_METHOD_NAME_31);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_32, ACCOUNT_METHOD_NAME_32);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_33, ACCOUNT_METHOD_NAME_33);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_34, ACCOUNT_METHOD_NAME_34);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_35, ACCOUNT_METHOD_NAME_35);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_36, ACCOUNT_METHOD_NAME_36);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_37, ACCOUNT_METHOD_NAME_37);
		accountMethodMap.put(ACCOUNT_METHOD_CODE_38, ACCOUNT_METHOD_NAME_38);
		ACCOUNT_METHOD_MAP = Collections.unmodifiableMap(accountMethodMap);
	}

//	/** 発払い（元払い） */
//	public static final String INVOICE_CLASSIFICATION_CODE_1 = "発払い（元払い）";
//	/** 発払い（元払い） */
//	public static final String INVOICE_CLASSIFICATION_NAME_1 = "発払い（元払い）";
//	/** コレクト（代引き） */
//	public static final String INVOICE_CLASSIFICATION_CODE_2 = "コレクト（代引き）";
//	/** コレクト（代引き） */
//	public static final String INVOICE_CLASSIFICATION_NAME_2 = "コレクト（代引き）";
//	/** メール便 */
//	public static final String INVOICE_CLASSIFICATION_CODE_3 = "メール便";
//	/** メール便 */
//	public static final String INVOICE_CLASSIFICATION_NAME_3 = "メール便";
//	/** 定形郵便 */
//	public static final String INVOICE_CLASSIFICATION_CODE_4 = "定形郵便";
//	/** 定形郵便 */
//	public static final String INVOICE_CLASSIFICATION_NAME_4 = "定形郵便";
//	/** 定形外郵便 */
//	public static final String INVOICE_CLASSIFICATION_CODE_5 = "定形外郵便";
//	/** 定形外郵便 */
//	public static final String INVOICE_CLASSIFICATION_NAME_5 = "定形外郵便";
//	/** ポスパケット */
//	public static final String INVOICE_CLASSIFICATION_CODE_6 = "ポスパケット";
//	/** ポスパケット */
//	public static final String INVOICE_CLASSIFICATION_NAME_6 = "ポスパケット";

	/** 発払い（元払い） */
	public static final String INVOICE_CLASSIFICATION_CODE_1 = "0";
	/** 発払い（元払い） */
	public static final String INVOICE_CLASSIFICATION_NAME_1 = "発払い（元払い）";
	/** コレクト（代引き） */
	public static final String INVOICE_CLASSIFICATION_CODE_2 = "2";
	/** コレクト（代引き） */
	public static final String INVOICE_CLASSIFICATION_NAME_2 = "コレクト（代引き）";
	/** DM便 */
	public static final String INVOICE_CLASSIFICATION_CODE_3 = "3";
	/** DM便 */
	public static final String INVOICE_CLASSIFICATION_NAME_3 = "DM便";
	/** メール便速達 */
	public static final String INVOICE_CLASSIFICATION_CODE_4 = "6";
	/** メール便速達 */
	public static final String INVOICE_CLASSIFICATION_NAME_4 = "メール便速達";
	/** ネコポス */
	public static final String INVOICE_CLASSIFICATION_CODE_5 = "7";
	/** ネコポス */
	public static final String INVOICE_CLASSIFICATION_NAME_5 = "ネコポス";
	/** 宅急便コンパクト */
	public static final String INVOICE_CLASSIFICATION_CODE_6 = "8";
	/** 宅急便コンパクト */
	public static final String INVOICE_CLASSIFICATION_NAME_6 = "宅急便コンパクト";

	public static final Map<String, String> INVOICE_CLASSIFICATION_MAP_B2;

	public static final Map<String, String> INVOICE_CLASSIFICATION_MAP_EHIDEN;

	public static final Map<String, String> INVOICE_CLASSIFICATION_MAP_SEINO;
	/** 元払い */
    public static final String INVOICE_CLASSIFICATION_SEINO_CODE_1 = "1";
    /** 元払い */
    public static final String INVOICE_CLASSIFICATION_SEINO_NAME_1 = "元払";
    /** 元払い */
    public static final String INVOICE_CLASSIFICATION_SEINO_CODE_3 = "3";
    /** 元払い */
    public static final String INVOICE_CLASSIFICATION_SEINO_NAME_3 = "着払";

	/**
	 * 送状種別：クロネコ
	 */
	static {
		Map<String, String> invoiceClassificationMap = new LinkedHashMap<String, String>();
		invoiceClassificationMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		invoiceClassificationMap.put(INVOICE_CLASSIFICATION_NAME_1, INVOICE_CLASSIFICATION_CODE_1);
		invoiceClassificationMap.put(INVOICE_CLASSIFICATION_NAME_2, INVOICE_CLASSIFICATION_CODE_2);
		invoiceClassificationMap.put(INVOICE_CLASSIFICATION_NAME_3, INVOICE_CLASSIFICATION_CODE_3);
		invoiceClassificationMap.put(INVOICE_CLASSIFICATION_NAME_4, INVOICE_CLASSIFICATION_CODE_4);
		invoiceClassificationMap.put(INVOICE_CLASSIFICATION_NAME_5, INVOICE_CLASSIFICATION_CODE_5);
		invoiceClassificationMap.put(INVOICE_CLASSIFICATION_NAME_6, INVOICE_CLASSIFICATION_CODE_6);
		INVOICE_CLASSIFICATION_MAP_B2 = Collections.unmodifiableMap(invoiceClassificationMap);
	}

	/**
	 * 送り状種別：佐川
	 */
	static {
		Map<String, String> invoiceClassificationMapSagawa = new LinkedHashMap<String, String>();
		invoiceClassificationMapSagawa.put(StringUtils.EMPTY, StringUtils.EMPTY);
		invoiceClassificationMapSagawa.put(INVOICE_CLASSIFICATION_NAME_1, INVOICE_CLASSIFICATION_CODE_1);
		invoiceClassificationMapSagawa.put(INVOICE_CLASSIFICATION_NAME_2, INVOICE_CLASSIFICATION_CODE_2);
		INVOICE_CLASSIFICATION_MAP_EHIDEN = Collections.unmodifiableMap(invoiceClassificationMapSagawa);

	}

	/**
     * 送り状種別：西濃運輸
     */
    static {
        Map<String, String> invoiceClassificationMapSeino = new LinkedHashMap<String, String>();
        invoiceClassificationMapSeino.put(INVOICE_CLASSIFICATION_SEINO_NAME_1, INVOICE_CLASSIFICATION_SEINO_CODE_1);
        invoiceClassificationMapSeino.put(INVOICE_CLASSIFICATION_SEINO_NAME_3, INVOICE_CLASSIFICATION_SEINO_CODE_3);
        INVOICE_CLASSIFICATION_MAP_SEINO = Collections.unmodifiableMap(invoiceClassificationMapSeino);

    }

    /**
     * 原票区分
     */
    public static final Map<String, String> GENPYO_KBN_MAP_SEINO;
    static {
        Map<String, String> genpyoKbnMapSeino = new LinkedHashMap<String, String>();
        genpyoKbnMapSeino.put("一般", "0");
        genpyoKbnMapSeino.put("ミニ", "3");
        GENPYO_KBN_MAP_SEINO = Collections.unmodifiableMap(genpyoKbnMapSeino);
    }

    /**
     * 輸送指示
     */
    public static final Map<String, String> YUSO_SHIJI_MAP_SEINO;
    static {
        Map<String, String> yusoShijiMapSeino = new LinkedHashMap<String, String>();
        yusoShijiMapSeino.put("止商品", "01");
        yusoShijiMapSeino.put("配達指定", "02");
        yusoShijiMapSeino.put("取扱注意", "03");
        yusoShijiMapSeino.put("われもの注意", "04");
        yusoShijiMapSeino.put("ガラス注意", "05");
        yusoShijiMapSeino.put("コワレモノ", "06");
        yusoShijiMapSeino.put("水漏れ注意", "07");
        yusoShijiMapSeino.put("精密機器", "08");
        yusoShijiMapSeino.put("下積厳禁", "09");
        yusoShijiMapSeino.put("横積厳禁", "10");
        yusoShijiMapSeino.put("平積厳禁", "11");
        yusoShijiMapSeino.put("立積厳禁", "12");
        yusoShijiMapSeino.put("天地無用", "13");
        yusoShijiMapSeino.put("角つき厳禁", "14");
        yusoShijiMapSeino.put("貴重品", "15");
        yusoShijiMapSeino.put("高価品", "16");
        yusoShijiMapSeino.put("予約", "17");
        YUSO_SHIJI_MAP_SEINO = Collections.unmodifiableMap(yusoShijiMapSeino);
    }

	public static final String DISPOSAL_ROUTE_CODE_1 = "基本ルート";
	public static final String DISPOSAL_ROUTE_NAME_1 = "基本ルート";
	public static final String DISPOSAL_ROUTE_CODE_2 = "BO";
	public static final String DISPOSAL_ROUTE_NAME_2 = "BO";

	/**
	 * ルート
	 */
	public static final Map<String, String> DISPOSAL_ROUTE_MAP;

	static {
		Map<String, String> disposalRouteMap = new LinkedHashMap<String, String>();
		disposalRouteMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		disposalRouteMap.put(DISPOSAL_ROUTE_CODE_1, DISPOSAL_ROUTE_NAME_1);
		disposalRouteMap.put(DISPOSAL_ROUTE_CODE_2, DISPOSAL_ROUTE_NAME_2);
		DISPOSAL_ROUTE_MAP = Collections.unmodifiableMap(disposalRouteMap);
	}

	public static final String TRANSPORT_CORPORATION_SYSTEM_CODE_1 = "ヤマト運輸";
	public static final String TRANSPORT_CORPORATION_SYSTEM_NAME_1 = "ヤマト運輸";
	public static final String TRANSPORT_CORPORATION_SYSTEM_CODE_2 = "佐川急便";
	public static final String TRANSPORT_CORPORATION_SYSTEM_NAME_2 = "佐川急便";
	public static final String TRANSPORT_CORPORATION_SYSTEM_CODE_3 = "日本郵便";
	public static final String TRANSPORT_CORPORATION_SYSTEM_NAME_3 = "日本郵便";
	public static final String TRANSPORT_CORPORATION_SYSTEM_CODE_4 = "西濃運輸";
	public static final String TRANSPORT_CORPORATION_SYSTEM_NAME_4 = "西濃運輸";

	/**
	 * 配送会社
	 */
	public static final Map<String, String> TRANSPORT_CORPORATION_SYSTEM_MAP;

	static {
		Map<String, String> transportCorporationSystemMap = new LinkedHashMap<String, String>();
		transportCorporationSystemMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		transportCorporationSystemMap.put(TRANSPORT_CORPORATION_SYSTEM_CODE_1, TRANSPORT_CORPORATION_SYSTEM_NAME_1);
		transportCorporationSystemMap.put(TRANSPORT_CORPORATION_SYSTEM_CODE_2, TRANSPORT_CORPORATION_SYSTEM_NAME_2);
		transportCorporationSystemMap.put(TRANSPORT_CORPORATION_SYSTEM_CODE_3, TRANSPORT_CORPORATION_SYSTEM_NAME_3);
		transportCorporationSystemMap.put(TRANSPORT_CORPORATION_SYSTEM_CODE_4, TRANSPORT_CORPORATION_SYSTEM_NAME_4);
		TRANSPORT_CORPORATION_SYSTEM_MAP = Collections.unmodifiableMap(transportCorporationSystemMap);
	}

	public static final String CUTOFF_DATE_CODE_0 = "0";
	public static final String CUTOFF_DATE_NAME_0 = "末日";
	public static final String CUTOFF_DATE_CODE_1 = "1";
	public static final String CUTOFF_DATE_NAME_1 = "25日";
	public static final String CUTOFF_DATE_CODE_2 = "2";
	public static final String CUTOFF_DATE_NAME_2 = "20日";
	public static final String CUTOFF_DATE_CODE_3 = "3";
	public static final String CUTOFF_DATE_NAME_3 = "15日";
	public static final String CUTOFF_DATE_CODE_4 = "4";
	public static final String CUTOFF_DATE_NAME_4 = "10日";
	public static final String CUTOFF_DATE_CODE_5 = "5";
	public static final String CUTOFF_DATE_NAME_5 = "5日";
	/**
	 * 締日
	 */
	public static final Map<String, String> CUTOFF_DATE_MAP;

	static {
		Map<String, String> cutoffDateMap = new LinkedHashMap<String, String>();
		cutoffDateMap.put(CUTOFF_DATE_CODE_0, CUTOFF_DATE_NAME_0);
		cutoffDateMap.put(CUTOFF_DATE_CODE_1, CUTOFF_DATE_NAME_1);
		cutoffDateMap.put(CUTOFF_DATE_CODE_2, CUTOFF_DATE_NAME_2);
		cutoffDateMap.put(CUTOFF_DATE_CODE_3, CUTOFF_DATE_NAME_3);
		cutoffDateMap.put(CUTOFF_DATE_CODE_4, CUTOFF_DATE_NAME_4);
		cutoffDateMap.put(CUTOFF_DATE_CODE_5, CUTOFF_DATE_NAME_5);
		CUTOFF_DATE_MAP = Collections.unmodifiableMap(cutoffDateMap);
	}


	/**
	 * 非商品コード
	 */
	public static final String ETC_PRICE_CODE_1 = "非商品";
	/**
	 * 非商品
	 */
	public static final String ETC_PRICE_NAME_1 = "非商品";
	/**
	 * ギフトコード
	 */
	public static final String ETC_PRICE_CODE_2 = "ギフト";
	/**
	 * ギフト
	 */
	public static final String ETC_PRICE_NAME_2 = "ギフト";
	/**
	 * 商品種別
	 */
	public static final Map<String, String> ETC_PRICE_MAP;
	static {
		Map<String, String> etcPriceMap = new LinkedHashMap<String, String>();
		etcPriceMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		etcPriceMap.put(ETC_PRICE_CODE_1, ETC_PRICE_NAME_1);
		etcPriceMap.put(ETC_PRICE_CODE_2, ETC_PRICE_NAME_2);
		ETC_PRICE_MAP = Collections.unmodifiableMap(etcPriceMap);
	}

	public static final int ADD_SALES_ITEM_LENGTH = 30;

	public static final int ADD_CORP_SALES_ITEM_LENGTH = 20;

	//20161115 野澤

	public static final int ADD_CURRENCY_LEDGER_LIST_LENGTH = 5;

	public static final int ADD_DOMESTIC_EXHIBITION_LIST_LENGTH = 20;

	public static final int ADD_DOMESTIC_ORDER_LIST_LENGTH = 20;


	/**
	 * 国内注文管理一覧、ステータス
	 */
	public static final String DOMESTIC_EXIHIBITION_SORT_ORDER_NO = "1";
	public static final String DOMESTIC_EXIHIBITION_SORT_ORDER = "注文前";
	public static final String DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO = "2";
	public static final String DOMESTIC_EXIHIBITION_SORT_BACKORDERED = "入荷待ち";
	public static final String DOMESTIC_EXIHIBITION_SORT_DIRECT_NO = "3";
	public static final String DOMESTIC_EXIHIBITION_SORT_DIRECT = "直送";
	public static final String DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO = "4";
	public static final String DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY = "入荷済";
	public static final String DOMESTIC_EXIHIBITION_SORT_TREATED_NO = "5";
	public static final String DOMESTIC_EXIHIBITION_SORT_TREATED = "処理済";
	public static final String DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO = "6";
	public static final String DOMESTIC_EXIHIBITION_SORT_CONFIRMED = "原価確認済";
	public static final String DOMESTIC_EXIHIBITION_SORT_COMPLETE_NO = "7";
	public static final String DOMESTIC_EXIHIBITION_SORT_COMPLETE = "完了";
	public static final String DOMESTIC_EXIHIBITION_SORT_ALL_NO = "8";
	public static final String DOMESTIC_EXIHIBITION_SORT_ALL = "ALL";

	public static final Map<String, String> DOMESTIC_EXIHIBITION_STATUS_MAP;
	static {
		Map<String, String> domesticstatus = new LinkedHashMap<String, String>();
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_ORDER_NO, DOMESTIC_EXIHIBITION_SORT_ORDER);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO, DOMESTIC_EXIHIBITION_SORT_BACKORDERED);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_DIRECT_NO, DOMESTIC_EXIHIBITION_SORT_DIRECT);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO, DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_TREATED_NO, DOMESTIC_EXIHIBITION_SORT_TREATED);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO, DOMESTIC_EXIHIBITION_SORT_CONFIRMED);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_COMPLETE_NO, DOMESTIC_EXIHIBITION_SORT_COMPLETE);
		DOMESTIC_EXIHIBITION_STATUS_MAP = Collections.unmodifiableMap(domesticstatus);
	}

	/**
	 * 国内注文管理一覧、ステータス
	 */
	public static final Map<String, String> DOMESTIC_EXIHIBITION_STATUS_PULLDOWN_MAP;
	static {
		Map<String, String> domesticstatus = new LinkedHashMap<String, String>();
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_ORDER_NO, DOMESTIC_EXIHIBITION_SORT_ORDER);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_BACKORDERED_NO, DOMESTIC_EXIHIBITION_SORT_BACKORDERED);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_DIRECT_NO, DOMESTIC_EXIHIBITION_SORT_DIRECT);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY_NO, DOMESTIC_EXIHIBITION_SORT_STOCK_ALREADY);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_TREATED_NO, DOMESTIC_EXIHIBITION_SORT_TREATED);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_CONFIRMED_NO, DOMESTIC_EXIHIBITION_SORT_CONFIRMED);
		domesticstatus.put(DOMESTIC_EXIHIBITION_SORT_COMPLETE_NO, DOMESTIC_EXIHIBITION_SORT_COMPLETE);
		DOMESTIC_EXIHIBITION_STATUS_PULLDOWN_MAP = Collections.unmodifiableMap(domesticstatus);
	}




	/**
	 * 国内注文書作成画面、モール(KTS)
	 */
	public static final String DOMESTIC_ORDER_SORT_KTS_YAHOO_OKU_NO = "KY";
	public static final String DOMESTIC_ORDER_SORT_KTS_YAHOO_OKU = "ヤフオク";
	public static final String DOMESTIC_ORDER_SORT_KTS_YAHOO_SHOP_NO = "KYS";
	public static final String DOMESTIC_ORDER_SORT_KTS_YAHOO_SHOP = "Yahoo!ショッピング";
	public static final String DOMESTIC_ORDER_SORT_KTS_RAKUTEN_NO = "KR";
	public static final String DOMESTIC_ORDER_SORT_KTS_RAKUTEN = "楽天市場";
	public static final String DOMESTIC_ORDER_SORT_KTS_AMAZON_NO = "KA";
	public static final String DOMESTIC_ORDER_SORT_KTS_AMAZON = "Amazon";
	public static final String DOMESTIC_ORDER_SORT_KTS_CORPORATE_SALE_NO = "KG";
	public static final String DOMESTIC_ORDER_SORT_KTS_CORPORATE_SALE = "業販";
	public static final String DOMESTIC_ORDER_SORT_KTS_HOUSE_NO = "KE";
	public static final String DOMESTIC_ORDER_SORT_KTS_HOUSE = "自社サイト";
	public static final String DOMESTIC_ORDER_SORT_KTS_MANUAL_NO = "KK";
	public static final String DOMESTIC_ORDER_SORT_KTS_MANUAL = "手動受注(メール・電話)";
	public static final String DOMESTIC_ORDER_SORT_KTS_STOCK_NO = "KZ";
	public static final String DOMESTIC_ORDER_SORT_KTS_STOCK = "在庫";
	public static final String DOMESTIC_ORDER_SORT_KTS_KAIHATSU_NO = "KKH";
	public static final String DOMESTIC_ORDER_SORT_KTS_KAIHATSU = "開発";

	public static final Map<String, String> DOMESTIC_MALL_KTS_MAP;
	static {
		Map<String, String> domesticMallKts = new LinkedHashMap<String, String>();
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_YAHOO_OKU_NO, DOMESTIC_ORDER_SORT_KTS_YAHOO_OKU);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_YAHOO_SHOP_NO, DOMESTIC_ORDER_SORT_KTS_YAHOO_SHOP);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_RAKUTEN_NO, DOMESTIC_ORDER_SORT_KTS_RAKUTEN);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_AMAZON_NO, DOMESTIC_ORDER_SORT_KTS_AMAZON);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_CORPORATE_SALE_NO, DOMESTIC_ORDER_SORT_KTS_CORPORATE_SALE);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_HOUSE_NO, DOMESTIC_ORDER_SORT_KTS_HOUSE);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_MANUAL_NO, DOMESTIC_ORDER_SORT_KTS_MANUAL);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_STOCK_NO, DOMESTIC_ORDER_SORT_KTS_STOCK);
		domesticMallKts.put(DOMESTIC_ORDER_SORT_KTS_KAIHATSU_NO, DOMESTIC_ORDER_SORT_KTS_KAIHATSU);
		DOMESTIC_MALL_KTS_MAP = Collections.unmodifiableMap(domesticMallKts);
	}


	/**
	 * 国内注文書作成画面、モール(車楽院)
	 */
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_OKU_NO = "SY";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_OKU = "ヤフオク";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_SHOP_NO = "SYS";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_SHOP = "Yahoo!ショッピング";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_RAKUTEN_NO = "SR";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_RAKUTEN = "楽天市場";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_AMAZON_NO = "SA";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_AMAZON = "Amazon";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_MANUAL_NO = "SK";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_MANUAL = "手動受注(メール・電話)";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_STOCK_NO = "SZ";
	public static final String DOMESTIC_ORDER_SORT_SHARAKUIN_STOCK = "在庫";

	public static final Map<String, String> DOMESTIC_MALL_MAP_SHARAKUIN;
	static {
		Map<String, String> domesticMallSharakuin = new LinkedHashMap<String, String>();
		domesticMallSharakuin.put(DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_OKU_NO, DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_OKU);
		domesticMallSharakuin.put(DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_SHOP_NO, DOMESTIC_ORDER_SORT_SHARAKUIN_YAHOO_SHOP);
		domesticMallSharakuin.put(DOMESTIC_ORDER_SORT_SHARAKUIN_RAKUTEN_NO, DOMESTIC_ORDER_SORT_SHARAKUIN_RAKUTEN);
		domesticMallSharakuin.put(DOMESTIC_ORDER_SORT_SHARAKUIN_AMAZON_NO, DOMESTIC_ORDER_SORT_SHARAKUIN_AMAZON);
		domesticMallSharakuin.put(DOMESTIC_ORDER_SORT_SHARAKUIN_MANUAL_NO, DOMESTIC_ORDER_SORT_SHARAKUIN_MANUAL);
		domesticMallSharakuin.put(DOMESTIC_ORDER_SORT_SHARAKUIN_STOCK_NO, DOMESTIC_ORDER_SORT_SHARAKUIN_STOCK);
		DOMESTIC_MALL_MAP_SHARAKUIN = Collections.unmodifiableMap(domesticMallSharakuin);
	}

	/**
	 * 国内注文書作成画面、モール(T-FOUR)
	 */
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_OKU_NO = "TY";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_OKU = "ヤフオク";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_SHOP_NO = "TYS";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_SHOP = "Yahoo!ショッピング";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_RAKUTEN_NO = "TR";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_RAKUTEN = "楽天市場";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_AMAZON_NO = "TA";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_AMAZON = "Amazon";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_CORPORATE_SALE_NO = "TG";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_CORPORATE_SALE = "業販";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_HOUSE_NO = "TE";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_HOUSE = "自社サイト";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_MANUAL_NO = "TK";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_MANUAL = "手動受注(メール・電話)";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_STOCK_NO = "TZ";
	public static final String DOMESTIC_ORDER_SORT_T_FOUR_STOCK = "在庫";

	public static final Map<String, String> DOMESTIC_MALL_MAP_T_FOUR;
	static {
		Map<String, String> domesticMallTFour = new LinkedHashMap<String, String>();
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_OKU_NO, DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_OKU);
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_SHOP_NO, DOMESTIC_ORDER_SORT_T_FOUR_YAHOO_SHOP);
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_RAKUTEN_NO, DOMESTIC_ORDER_SORT_T_FOUR_RAKUTEN);
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_AMAZON_NO, DOMESTIC_ORDER_SORT_T_FOUR_AMAZON);
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_CORPORATE_SALE_NO, DOMESTIC_ORDER_SORT_T_FOUR_CORPORATE_SALE);
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_HOUSE_NO, DOMESTIC_ORDER_SORT_T_FOUR_HOUSE);
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_MANUAL_NO, DOMESTIC_ORDER_SORT_T_FOUR_MANUAL);
		domesticMallTFour.put(DOMESTIC_ORDER_SORT_T_FOUR_STOCK_NO, DOMESTIC_ORDER_SORT_T_FOUR_STOCK);
		DOMESTIC_MALL_MAP_T_FOUR = Collections.unmodifiableMap(domesticMallTFour);
	}

	/**
	 * 国内注文書作成画面、モール(ラルグス)
	 */
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_OKU_NO = "LY";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_OKU = "ヤフオク";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_SHOP_NO = "LYS";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_SHOP = "Yahoo!ショッピング";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_RAKUTEN_NO = "LR";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_RAKUTEN = "楽天市場";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_AMAZON_NO = "LA";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_AMAZON = "Amazon";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_MANUAL_NO = "LK";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_MANUAL = "手動受注(メール・電話)";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_STOCK_NO = "LZ";
	public static final String DOMESTIC_ORDER_SORT_RARUGUSU_STOCK = "在庫";

	public static final Map<String, String> DOMESTIC_MALL_MAP_RARUGUSU;
	static {
		Map<String, String> domesticMallRarugusu = new LinkedHashMap<String, String>();
		domesticMallRarugusu.put(DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_OKU_NO, DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_OKU);
		domesticMallRarugusu.put(DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_SHOP_NO, DOMESTIC_ORDER_SORT_RARUGUSU_YAHOO_SHOP);
		domesticMallRarugusu.put(DOMESTIC_ORDER_SORT_RARUGUSU_RAKUTEN_NO, DOMESTIC_ORDER_SORT_RARUGUSU_RAKUTEN);
		domesticMallRarugusu.put(DOMESTIC_ORDER_SORT_RARUGUSU_AMAZON_NO, DOMESTIC_ORDER_SORT_RARUGUSU_AMAZON);
		domesticMallRarugusu.put(DOMESTIC_ORDER_SORT_RARUGUSU_MANUAL_NO, DOMESTIC_ORDER_SORT_RARUGUSU_MANUAL);
		domesticMallRarugusu.put(DOMESTIC_ORDER_SORT_RARUGUSU_STOCK_NO, DOMESTIC_ORDER_SORT_RARUGUSU_STOCK);
		DOMESTIC_MALL_MAP_RARUGUSU = Collections.unmodifiableMap(domesticMallRarugusu);
	}


	/**
	 * 国内注文書作成画面、モール(BCR)
	 */
	public static final String DOMESTIC_ORDER_SORT_BCR_YAHOO_OKU_NO = "BY";
	public static final String DOMESTIC_ORDER_SORT_BCR_YAHOO_OKU = "ヤフオク";
	public static final String DOMESTIC_ORDER_SORT_BCR_YAHOO_SHOP_NO = "BYS";
	public static final String DOMESTIC_ORDER_SORT_BCR_YAHOO_SHOP = "Yahoo!ショッピング";
	public static final String DOMESTIC_ORDER_SORT_BCR_RAKUTEN_NO = "BR";
	public static final String DOMESTIC_ORDER_SORT_BCR_RAKUTEN = "楽天市場";
	public static final String DOMESTIC_ORDER_SORT_BCR_MANUAL_NO = "BK";
	public static final String DOMESTIC_ORDER_SORT_BCR_MANUAL = "手動受注(メール・電話)";
	public static final String DOMESTIC_ORDER_SORT_BCR_STOCK_NO = "BZ";
	public static final String DOMESTIC_ORDER_SORT_BCR_STOCK = "在庫";

	public static final Map<String, String> DOMESTIC_MALL_MAP_BCR;
	static {
		Map<String, String> domesticMallBcr = new LinkedHashMap<String, String>();
		domesticMallBcr.put(DOMESTIC_ORDER_SORT_BCR_YAHOO_OKU_NO, DOMESTIC_ORDER_SORT_BCR_YAHOO_OKU);
		domesticMallBcr.put(DOMESTIC_ORDER_SORT_BCR_YAHOO_SHOP_NO, DOMESTIC_ORDER_SORT_BCR_YAHOO_SHOP);
		domesticMallBcr.put(DOMESTIC_ORDER_SORT_BCR_RAKUTEN_NO, DOMESTIC_ORDER_SORT_BCR_RAKUTEN);
		domesticMallBcr.put(DOMESTIC_ORDER_SORT_BCR_MANUAL_NO, DOMESTIC_ORDER_SORT_BCR_MANUAL);
		domesticMallBcr.put(DOMESTIC_ORDER_SORT_BCR_STOCK_NO, DOMESTIC_ORDER_SORT_BCR_STOCK);
		DOMESTIC_MALL_MAP_BCR = Collections.unmodifiableMap(domesticMallBcr);
	}

	/**
	 * 国内注文書作成画面、モール(ECO)
	 */
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_OKU_NO = "EY";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_OKU = "ヤフオク";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_SHOP_NO = "EYS";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_SHOP = "Yahoo!ショッピング";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_RAKUTEN_NO = "ER";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_RAKUTEN = "楽天市場";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_AMAZON_NO = "EA";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_AMAZON = "Amazon";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_CORPORATE_SALE_NO = "EG";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_CORPORATE_SALE = "業販";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_HOUSE_NO = "EE";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_HOUSE = "自社サイト";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_MANUAL_NO = "EK";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_MANUAL = "手動受注(メール・電話)";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_STOCK_NO = "EZ";
	public static final String DOMESTIC_ORDER_SORT_CYBER_ECO_STOCK = "在庫";

	public static final Map<String, String> DOMESTIC_MALL_MAP_CYBER_ECO;
	static {
		Map<String, String> domesticMallCyberEco = new LinkedHashMap<String, String>();
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_OKU_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_OKU);
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_SHOP_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_YAHOO_SHOP);
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_RAKUTEN_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_RAKUTEN);
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_AMAZON_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_AMAZON);
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_CORPORATE_SALE_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_CORPORATE_SALE);
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_HOUSE_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_HOUSE);
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_MANUAL_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_MANUAL);
		domesticMallCyberEco.put(DOMESTIC_ORDER_SORT_CYBER_ECO_STOCK_NO, DOMESTIC_ORDER_SORT_CYBER_ECO_STOCK);
		DOMESTIC_MALL_MAP_CYBER_ECO = Collections.unmodifiableMap(domesticMallCyberEco);
	}

	/**
	 * 国内注文書作成画面、モール(店舗)
	 */
	public static final String DOMESTIC_ORDER_SORT_SHOP_FACTORY_NO = "1K";
	public static final String DOMESTIC_ORDER_SORT_SHOP_FACTORY = "ファクトリー客注";
	public static final String DOMESTIC_ORDER_SORT_SHOP_FACTORY_STOCK_NO = "1Z";
	public static final String DOMESTIC_ORDER_SORT_SHOP_FACTORY_STOCK = "ファクトリー在庫";
	public static final String DOMESTIC_ORDER_SORT_SHOP_ICHINOE_NO = "2K";
	public static final String DOMESTIC_ORDER_SORT_SHOP_ICHINOE = "一之江客注";
	public static final String DOMESTIC_ORDER_SORT_SHOP_ICHINOE_STOCK_NO = "2Z";
	public static final String DOMESTIC_ORDER_SORT_SHOP_ICHINOE_STOCK = "一之江在庫";
	public static final String DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA__NO = "4K";
	public static final String DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA_ = "平和島客注";
	public static final String DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA_STOCK_NO = "4Z";
	public static final String DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA_STOCK = "平和島在庫";

	public static final Map<String, String> DOMESTIC_MALL_MAP_SHOP;
	static {
		Map<String, String> domesticMallShop = new LinkedHashMap<String, String>();
		domesticMallShop.put(DOMESTIC_ORDER_SORT_SHOP_FACTORY_NO, DOMESTIC_ORDER_SORT_SHOP_FACTORY);
		domesticMallShop.put(DOMESTIC_ORDER_SORT_SHOP_FACTORY_STOCK_NO, DOMESTIC_ORDER_SORT_SHOP_FACTORY_STOCK);
		domesticMallShop.put(DOMESTIC_ORDER_SORT_SHOP_ICHINOE_NO, DOMESTIC_ORDER_SORT_SHOP_ICHINOE);
		domesticMallShop.put(DOMESTIC_ORDER_SORT_SHOP_ICHINOE_STOCK_NO, DOMESTIC_ORDER_SORT_SHOP_ICHINOE_STOCK);
		domesticMallShop.put(DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA__NO, DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA_);
		domesticMallShop.put(DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA_STOCK_NO, DOMESTIC_ORDER_SORT_SHOP_HEIWAJIMA_STOCK);
		DOMESTIC_MALL_MAP_SHOP = Collections.unmodifiableMap(domesticMallShop);
	}


	/**
	 * 国内管理一覧画面、並び替え
	 */
	public static final String DOMESTIC_ORDER_SORT_ARRIVAL_SCHEDULE_DATE_NO = "1";
	public static final String DOMESTIC_ORDER_SORT_ARRIVAL_SCHEDULE_DATE = "入荷予定日";
	public static final String DOMESTIC_ORDER_SORT_DATE_NO = "2";
	public static final String DOMESTIC_ORDER_SORT_DATE = "注文書作成日";
	public static final String DOMESTIC_ORDER_SORT_ORDER_LIST_NO = "3";
	public static final String DOMESTIC_ORDER_SORT_ORDER_LIST = "注文書No.";
	public static final String DOMESTIC_ORDER_SORT_MAKER_NO = "4";
	public static final String DOMESTIC_ORDER_SORT_MAKER = "メーカー";
	public static final String DOMESTIC_ORDER_SORT_WHOLSESALER_NM = "5";
	public static final String DOMESTIC_ORDER_SORT_WHOLSESALER = "問屋";
	public static final String DOMESTIC_ORDER_SORT_CODE_NO = "6";
	public static final String DOMESTIC_ORDER_SORT_CODE = "メーカー品番";
	public static final String DOMESTIC_ORDER_SORT_SEREAL_NO = "7";
	public static final String DOMESTIC_ORDER_SORT_SEREAL = "通番";
	public static final String DOMESTIC_ORDER_SORT_ARRIVAL_DATE_NO = "8";
	public static final String DOMESTIC_ORDER_SORT_ARRIVAL_DATE = "入荷日";
	public static final String DOMESTIC_ORDER_SORT_ORDER_CHARGE_NO = "9";
	public static final String DOMESTIC_ORDER_SORT_ORDER_CHARGE = "注文担当";


	public static final Map<String, String> DOMESTIC_ORDER_LIST_SORT_MAP;
	static {
		Map<String, String> domesticListSortMap = new LinkedHashMap<String, String>();
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_ARRIVAL_SCHEDULE_DATE_NO, DOMESTIC_ORDER_SORT_ARRIVAL_SCHEDULE_DATE);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_DATE_NO, DOMESTIC_ORDER_SORT_DATE);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_ORDER_LIST_NO, DOMESTIC_ORDER_SORT_ORDER_LIST);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_MAKER_NO, DOMESTIC_ORDER_SORT_MAKER);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_WHOLSESALER_NM, DOMESTIC_ORDER_SORT_WHOLSESALER);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_CODE_NO, DOMESTIC_ORDER_SORT_CODE);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_SEREAL_NO, DOMESTIC_ORDER_SORT_SEREAL);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_ARRIVAL_DATE_NO, DOMESTIC_ORDER_SORT_ARRIVAL_DATE);
		domesticListSortMap.put(DOMESTIC_ORDER_SORT_ORDER_CHARGE_NO, DOMESTIC_ORDER_SORT_ORDER_CHARGE);
		DOMESTIC_ORDER_LIST_SORT_MAP = Collections.unmodifiableMap(domesticListSortMap);
	}


	/**
	 *国内注文書作成画面、並び替え
	 */
	public static final String DOMESTIC_EXIHIBITION_SORT_MNAGEMENT_CODE_NO = "1";
	public static final String DOMESTIC_EXIHIBITION_SORT_MNAGEMENT_CODE = "管理品番";
	public static final String DOMESTIC_EXIHIBITION_SORT_MAKER_CODE_NO = "2";
	public static final String DOMESTIC_EXIHIBITION_SORT_MAKER_CODE = "メーカー品番";
	public static final String DOMESTIC_EXIHIBITION_SORT_MAKER_NM_NO = "3";
	public static final String DOMESTIC_EXIHIBITION_SORT_MAKER_NM = "メーカー名";
	public static final String DOMESTIC_EXIHIBITION_SORT_ITEM_NM_NO = "4";
	public static final String DOMESTIC_EXIHIBITION_SORT_ITEM_NM = "商品名";
	public static final String DOMESTIC_EXIHIBITION_SORT_WHOLSESALER_NM_NO = "5";
	public static final String DOMESTIC_EXIHIBITION_SORT_WHOLSESALER_NM = "問屋";
	public static final String DOMESTIC_EXIHIBITION_SORT_UPDATE_DATE_NO = "6";
	public static final String DOMESTIC_EXIHIBITION_SORT_UPDATE_DATE_NM = "更新日";

	public static final Map<String, String> DOMESTIC_EXIHIBITION_SORT_MAP;
	static {
		Map<String, String> itemListSortMap = new LinkedHashMap<String, String>();
		itemListSortMap.put(DOMESTIC_EXIHIBITION_SORT_MNAGEMENT_CODE_NO, DOMESTIC_EXIHIBITION_SORT_MNAGEMENT_CODE);
		itemListSortMap.put(DOMESTIC_EXIHIBITION_SORT_MAKER_CODE_NO, DOMESTIC_EXIHIBITION_SORT_MAKER_CODE);
		itemListSortMap.put(DOMESTIC_EXIHIBITION_SORT_MAKER_NM_NO, DOMESTIC_EXIHIBITION_SORT_MAKER_NM);
		itemListSortMap.put(DOMESTIC_EXIHIBITION_SORT_ITEM_NM_NO, DOMESTIC_EXIHIBITION_SORT_ITEM_NM);
		itemListSortMap.put(DOMESTIC_EXIHIBITION_SORT_WHOLSESALER_NM_NO, DOMESTIC_EXIHIBITION_SORT_WHOLSESALER_NM);
		itemListSortMap.put(DOMESTIC_EXIHIBITION_SORT_UPDATE_DATE_NO, DOMESTIC_EXIHIBITION_SORT_UPDATE_DATE_NM);
		DOMESTIC_EXIHIBITION_SORT_MAP = Collections.unmodifiableMap(itemListSortMap);
	}

	/**
	 * 検索のソート順Map:昇順、降順
	 */
	public static final String DOMESTIC_EXIHIBITION_SORT_CODE_SORT_ASEND = "1";
	public static final String DOMESTIC_EXIHIBITION_SORT_NAME_SORT_ASEND = "昇順";
	public static final String DOMESTIC_EXIHIBITION_SORT_CODE_SORT_DESEND = "2";
	public static final String DOMESTIC_EXIHIBITION_SORT_NAME_SORT_DESEND = "降順";

	public static final Map<String, String> DOMESTIC_EXIHIBITION_ORDER;
	static {
		Map<String, String> itemListSortOrder = new LinkedHashMap<String, String>();
		itemListSortOrder.put(DOMESTIC_EXIHIBITION_SORT_CODE_SORT_ASEND, DOMESTIC_EXIHIBITION_SORT_NAME_SORT_ASEND);
		itemListSortOrder.put(DOMESTIC_EXIHIBITION_SORT_CODE_SORT_DESEND, DOMESTIC_EXIHIBITION_SORT_NAME_SORT_DESEND);
		DOMESTIC_EXIHIBITION_ORDER = Collections.unmodifiableMap(itemListSortOrder);
	}

	/**
	 * 検索のソート順Map：一致、以上、以下
	 */
	public static final String NUMBER_ORDER_CODE_MATCH = "1";
	public static final String NUMBER_ORDER_CODE_ABOVE = "2";
	public static final String NUMBER_ORDER_CODE_BELOW = "3";
	public static final String NUMBER_ORDER_NAME_MATCH = "一致";
	public static final String NUMBER_ORDER_NAME_ABOVE = "以上";
	public static final String NUMBER_ORDER_NAME_BELOW = "以下";

	public static final Map<String, String> NUMBER_ORDER_MAP;
	static {
		Map<String, String> numberOrderMap = new LinkedHashMap<String, String>();
		numberOrderMap.put(NUMBER_ORDER_CODE_MATCH, NUMBER_ORDER_NAME_MATCH);
		numberOrderMap.put(NUMBER_ORDER_CODE_ABOVE, NUMBER_ORDER_NAME_ABOVE);
		numberOrderMap.put(NUMBER_ORDER_CODE_BELOW, NUMBER_ORDER_NAME_BELOW);
		NUMBER_ORDER_MAP = Collections.unmodifiableMap(numberOrderMap);
	}


	//20140326 八鍬
	public static final String LIST_PAGE_MAX_CODE_1 = "1";

	public static final int LIST_PAGE_MAX_20 = 20;

	public static final String LIST_PAGE_MAX_CODE_2 = "2";

	public static final int LIST_PAGE_MAX_50 = 50;

	public static final String LIST_PAGE_MAX_CODE_3 = "3";

	public static final int LIST_PAGE_MAX_100 = 100;

	public static final String LIST_PAGE_MAX_CODE_4 = "4";

	public static final int LIST_PAGE_MAX_10 = 10;

	public static final String LIST_PAGE_MAX_CODE_5 = "5";

	public static final int LIST_PAGE_MAX_500 = 500;

	public static final String LIST_PAGE_MAX_CODE_6 = "6";

	public static final int LIST_PAGE_MAX_1000 = 1000;

	/**
	 * 1ページの件数
	 */
	public static final Map<String, Integer> LIST_PAGE_MAX_MAP;
	static {
		Map<String, Integer> listPageMaxMap = new LinkedHashMap<String, Integer>();
		listPageMaxMap.put(LIST_PAGE_MAX_CODE_1, LIST_PAGE_MAX_20);
		listPageMaxMap.put(LIST_PAGE_MAX_CODE_2, LIST_PAGE_MAX_50);
		listPageMaxMap.put(LIST_PAGE_MAX_CODE_3, LIST_PAGE_MAX_100);
		listPageMaxMap.put(LIST_PAGE_MAX_CODE_5, LIST_PAGE_MAX_500);
		listPageMaxMap.put(LIST_PAGE_MAX_CODE_6, LIST_PAGE_MAX_1000);
		LIST_PAGE_MAX_MAP = Collections.unmodifiableMap(listPageMaxMap);
	}

	/**
	 * 1ページの件数<br>
	 * (入金管理画面用)
	 */
	public static final Map<String, Integer> LIST_PAGE_MAX_FOR_PAYMENTMANAGEMENT_MAP;
	static {
		Map<String, Integer> listPageMaxForPaymentManagementMap = new LinkedHashMap<String, Integer>();
		listPageMaxForPaymentManagementMap.put(LIST_PAGE_MAX_CODE_4, LIST_PAGE_MAX_10);
		listPageMaxForPaymentManagementMap.put(LIST_PAGE_MAX_CODE_1, LIST_PAGE_MAX_20);
		listPageMaxForPaymentManagementMap.put(LIST_PAGE_MAX_CODE_2, LIST_PAGE_MAX_50);
		listPageMaxForPaymentManagementMap.put(LIST_PAGE_MAX_CODE_3, LIST_PAGE_MAX_100);
		LIST_PAGE_MAX_FOR_PAYMENTMANAGEMENT_MAP = Collections.unmodifiableMap(listPageMaxForPaymentManagementMap);
	}


	/**
	 * 国内出品DB
	 */
	public static final String DOMESTIC_EXIHIBITION_SORT_STOCK_CHARGE_NO = "1";
//	public static final String DOMESTIC_EXIHIBITION_SORT_STOCK_CHARGE = "入荷担当";
	public static final String DOMESTIC_EXIHIBITION_SORT_CONTACT_PERSPN_NO = "2";
//	public static final String DOMESTIC_EXIHIBITION_SORT_CONTACT_PERSPN = "対応者";
	public static final String DOMESTIC_EXIHIBITION_SORT_CORRES_NO = "3";
	public static final String DOMESTIC_EXIHIBITION_SORT_CORRES = "対応";
	public static final String DOMESTIC_EXIHIBITION_SORT_KIND_COST_NO = "4";
	public static final String DOMESTIC_EXIHIBITION_KIND_COST_DATE = "原価";
	public static final String DOMESTIC_EXIHIBITION_SORT_MEMO_NO = "5";
	public static final String DOMESTIC_EXIHIBITION_SORT_MEMO = "備考";
	public static final String DOMESTIC_EXIHIBITION_SORT_SCHEDULE_DATE_NO = "6";
	public static final String DOMESTIC_EXIHIBITION_SORT_SCHEDULE_DATE = "入荷予定日";

	public static final Map<String, String> DOMESTIC_EXIHIBITION_SORT_LIST_MAP;
	static {
		Map<String, String> domesticMap = new LinkedHashMap<String, String>();
//		domesticMap.put(DOMESTIC_EXIHIBITION_SORT_STOCK_CHARGE_NO, DOMESTIC_EXIHIBITION_SORT_STOCK_CHARGE);
//		domesticMap.put(DOMESTIC_EXIHIBITION_SORT_CONTACT_PERSPN_NO, DOMESTIC_EXIHIBITION_SORT_CONTACT_PERSPN);
		domesticMap.put(DOMESTIC_EXIHIBITION_SORT_SCHEDULE_DATE_NO, DOMESTIC_EXIHIBITION_SORT_SCHEDULE_DATE);
		domesticMap.put(DOMESTIC_EXIHIBITION_SORT_KIND_COST_NO, DOMESTIC_EXIHIBITION_KIND_COST_DATE);
		domesticMap.put(DOMESTIC_EXIHIBITION_SORT_CORRES_NO, DOMESTIC_EXIHIBITION_SORT_CORRES);
		domesticMap.put(DOMESTIC_EXIHIBITION_SORT_MEMO_NO, DOMESTIC_EXIHIBITION_SORT_MEMO);

		DOMESTIC_EXIHIBITION_SORT_LIST_MAP = Collections.unmodifiableMap(domesticMap);
	}

	/**
	 * 国内注文管理一覧画面、対応
	 */
	public static final String DOMESTIC_EXIHIBITION_NULL_NO = "0";
	public static final String DOMESTIC_EXIHIBITION_NULL = "";
	public static final String DOMESTIC_EXIHIBITION_SHIPMENT_NO = "1";
	public static final String DOMESTIC_EXIHIBITION_SHIPMENT = "出荷";
	public static final String DOMESTIC_EXIHIBITION_MOVE_NO = "2";
	public static final String DOMESTIC_EXIHIBITION_MOVE = "移動";

	public static final Map<String, String> DOMESTIC_EXIHIBITION_SELECT_MAP;
	static {
		Map<String, String> domesticselect = new LinkedHashMap<String, String>();
		domesticselect.put(StringUtils.EMPTY, StringUtils.EMPTY);
		domesticselect.put(DOMESTIC_EXIHIBITION_SHIPMENT_NO, DOMESTIC_EXIHIBITION_SHIPMENT);
		domesticselect.put(DOMESTIC_EXIHIBITION_MOVE_NO, DOMESTIC_EXIHIBITION_MOVE);
		DOMESTIC_EXIHIBITION_SELECT_MAP = Collections.unmodifiableMap(domesticselect);
	}
	/**
	 * 各税区分 - 内税
	 */
	public static final String TAX_CODE_INCLUDE = "1";
	/**
	 * 各税区分 - 内税
	 */
	public static final String TAX_INCLUDE = "内税";

	/**
	 * 各税区分 - 外税
	 */
	public static final String TAX_CODE_EXCLUSIVE = "2";
	/**
	 * 各税区分 - 外税
	 */
	public static final String TAX_EXCLUSIVE = "外税";

	public static final Map<String, String> TAX_MAP;

	static {
		Map<String, String> taxMap = new LinkedHashMap<String, String>();
		taxMap.put(TAX_CODE_INCLUDE, TAX_INCLUDE);
		taxMap.put(TAX_CODE_EXCLUSIVE, TAX_EXCLUSIVE);
		TAX_MAP = Collections.unmodifiableMap(taxMap);
	}

	/**
	 * 税率 2014_03月まで
	 */
	public static final int TAX_RATE_5 = 5;
	/**
	 * 税率 2014_04月から
	 */
	public static final int TAX_RATE_8 = 8;
	/**
	 * 税率 8パーセントに変わる日付
	 */
	public static final String TAX_UP_DATE_8 = "2014/04/01";
	/**
	 * 税率 2019_10月から
	 */
	public static final int TAX_RATE_10 = 10;
	/**
	 * 税率 10パーセントに変わる日付
	 */
	public static final String TAX_UP_DATE_10 = "2019/10/01";


	//20140319 八鍬
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_CODE1 = "1";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_LABEL1 = "品番";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_CODE2 = "2";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_LABEL2 = "工場品番";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_CODE3 = "3";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_LABEL3 = "工場名";

	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_CODE4 = "4";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_LABEL4 = "商品名";

	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_CODE5 = "5";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_LABEL5 = "総在庫数";


	public static final Map<String, String> ITEM_LIST_SORT_MAP;
	static {
		Map<String, String> itemListSortMap = new LinkedHashMap<String, String>();
		itemListSortMap.put(ITEM_LIST_SORT_CODE1, ITEM_LIST_SORT_LABEL1);
		itemListSortMap.put(ITEM_LIST_SORT_CODE2, ITEM_LIST_SORT_LABEL2);
		itemListSortMap.put(ITEM_LIST_SORT_CODE3, ITEM_LIST_SORT_LABEL3);
		itemListSortMap.put(ITEM_LIST_SORT_CODE4, ITEM_LIST_SORT_LABEL4);
		itemListSortMap.put(ITEM_LIST_SORT_CODE5, ITEM_LIST_SORT_LABEL5);
		ITEM_LIST_SORT_MAP = Collections.unmodifiableMap(itemListSortMap);
	}

	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_CODE_SORT_ASEND = "1";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_NAME_SORT_ASEND = "昇順";

	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_CODE_SORT_DESEND = "2";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_SORT_NAME_SORT_DESEND = "降順";


	public static final Map<String, String> ITEM_LIST_SORT_ORDER;
	static {
		Map<String, String> itemListSortOrder = new LinkedHashMap<String, String>();
		itemListSortOrder.put(ITEM_LIST_SORT_CODE_SORT_ASEND, ITEM_LIST_SORT_NAME_SORT_ASEND);
		itemListSortOrder.put(ITEM_LIST_SORT_CODE_SORT_DESEND, ITEM_LIST_SORT_NAME_SORT_DESEND);
		ITEM_LIST_SORT_ORDER = Collections.unmodifiableMap(itemListSortOrder);
	}

	public static final String ITEM_LIST_CODE_TYPE_CODE1 = "1";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_CODE_TYPE_LABEL1 = "品番";

	/**
	 *
	 */
	public static final String ITEM_LIST_CODE_TYPE_CODE2 = "2";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_CODE_TYPE_LABEL2 = "旧品番";

	/**
	 *
	 */
	public static final String ITEM_LIST_CODE_TYPE_CODE3 = "3";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_CODE_TYPE_LABEL3 = "工場品番";

	/**
	 *
	 */
	public static final String ITEM_LIST_CODE_TYPE_CODE4 = "4";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_CODE_TYPE_LABEL4 = "構成品番";

	/**
	 *
	 */
	public static final String ITEM_LIST_CODE_TYPE_CODE5 = "5";
	/**
	 * ：
	 */
	public static final String ITEM_LIST_CODE_TYPE_LABEL5 = "品番＋構成品番";

	public static final Map<String, String> ITEM_LIST_CODE_TYPE_MAP;
	static {
		Map<String, String> itemListCodeTypeMap = new LinkedHashMap<String, String>();
		itemListCodeTypeMap.put(ITEM_LIST_CODE_TYPE_CODE1, ITEM_LIST_CODE_TYPE_LABEL1);
		itemListCodeTypeMap.put(ITEM_LIST_CODE_TYPE_CODE2, ITEM_LIST_CODE_TYPE_LABEL2);
		itemListCodeTypeMap.put(ITEM_LIST_CODE_TYPE_CODE3, ITEM_LIST_CODE_TYPE_LABEL3);
		itemListCodeTypeMap.put(ITEM_LIST_CODE_TYPE_CODE4, ITEM_LIST_CODE_TYPE_LABEL4);
		itemListCodeTypeMap.put(ITEM_LIST_CODE_TYPE_CODE5, ITEM_LIST_CODE_TYPE_LABEL5);
		ITEM_LIST_CODE_TYPE_MAP = Collections.unmodifiableMap(itemListCodeTypeMap);
	}

	public static final String ITEM_LIST_NAME_TYPE_CODE1 = "1";

	public static final String ITEM_LIST_NAME_TYPE_LABEL1 = "商品名";

	public static final String ITEM_LIST_NAME_TYPE_CODE2 = "2";

	public static final String ITEM_LIST_NAME_TYPE_LABEL2 = "会社・工場名";

	public static final Map<String, String> ITEM_LIST_NAME_TYPE_MAP;
	static {
		Map<String, String> itemListNameTypeMap = new LinkedHashMap<String, String>();
		itemListNameTypeMap.put(ITEM_LIST_NAME_TYPE_CODE1, ITEM_LIST_NAME_TYPE_LABEL1);
		itemListNameTypeMap.put(ITEM_LIST_NAME_TYPE_CODE2, ITEM_LIST_NAME_TYPE_LABEL2);
		ITEM_LIST_NAME_TYPE_MAP = Collections.unmodifiableMap(itemListNameTypeMap);
	}

	public static final String ITEM_LIST_ARRIVAL_DATE_TYPE_CODE1 = "1";

	public static final String ITEM_LIST_ARRIVAL_DATE_TYPE_LABEL1 = "入荷予定日";

	public static final String ITEM_LIST_ARRIVAL_DATE_TYPE_CODE2 = "2";

	public static final String ITEM_LIST_ARRIVAL_DATE_TYPE_LABEL2 = "入荷日";

	public static final Map<String, String> ITEM_LIST_ARRIVAL_DATE_TYPE_MAP;
	static {
		Map<String, String> itemListArrivalDateTypeMap = new LinkedHashMap<String, String>();
		itemListArrivalDateTypeMap.put(ITEM_LIST_ARRIVAL_DATE_TYPE_CODE1, ITEM_LIST_ARRIVAL_DATE_TYPE_LABEL1);
		itemListArrivalDateTypeMap.put(ITEM_LIST_ARRIVAL_DATE_TYPE_CODE2, ITEM_LIST_ARRIVAL_DATE_TYPE_LABEL2);
		ITEM_LIST_ARRIVAL_DATE_TYPE_MAP = Collections.unmodifiableMap(itemListArrivalDateTypeMap);
	}

	public static final int ITEM_LIST_PAGE_MAX = 20;

	public static final int ADD_ITEM_MANUAL_LENGTH = 5;

	/**
	 * ピッキングリスト未出力・未出庫コード
	 */
	public static final String SALES_SLIP_FLG_CODE_0 = "0";
	/**
	 * ピッキングリスト未出力・未出庫記号
	 */
	public static final String SALES_SLIP_FLG_NAME_0 = "×";

	/**
	 * ピッキングリスト出力済・出庫済コード
	 */
	public static final String SALES_SLIP_FLG_CODE_1 = "1";

	/**
	 * ピッキングリスト出力済・出庫済記号
	 */
	public static final String SALES_SLIP_FLG_NAME_1 = "○";

	/**
	 * 売上伝票フラグmap
	 */
	public static final Map<String, String> SALES_SLIP_FLG_MAP;
	static {
		Map<String, String> salesSlipFlgMap = new LinkedHashMap<String, String>();
		salesSlipFlgMap.put(SALES_SLIP_FLG_CODE_0, SALES_SLIP_FLG_NAME_0);
		salesSlipFlgMap.put(SALES_SLIP_FLG_CODE_1, SALES_SLIP_FLG_NAME_1);
		SALES_SLIP_FLG_MAP = Collections.unmodifiableMap(salesSlipFlgMap);
	}



	/**
	 * 売上一覧画面：合計請求額から算出コード
	 */
	public static final String GROSS_PROFIT_CALC_TOTAL_CODE = "1";
	/**
	 * 売上一覧画面：合計請求額から算出
	 */
	public static final String GROSS_PROFIT_CALC_TOTALNAME = "合計請求額から算出";

	/**
	 * 売上一覧画面：小計から算出コード
	 */
	public static final String GROSS_PROFIT_CALC_SUBTOTAL_CODE = "2";
	/**
	 * 売上一覧画面：小計から算出
	 */
	public static final String GROSS_PROFIT_CALC_SUBTOTAL_NAME = "小計から算出";


	public static final Map<String, String> GROSS_PROFIT_CALC_MAP;
	static {
		Map<String, String> grossProfitCalcMap = new LinkedHashMap<String, String>();
		grossProfitCalcMap.put(GROSS_PROFIT_CALC_TOTAL_CODE, GROSS_PROFIT_CALC_TOTALNAME);
		grossProfitCalcMap.put(GROSS_PROFIT_CALC_SUBTOTAL_CODE, GROSS_PROFIT_CALC_SUBTOTAL_NAME);
		GROSS_PROFIT_CALC_MAP = Collections.unmodifiableMap(grossProfitCalcMap);
	}

	/**
	 * 法人間請求書一覧画面：税込商品計から算出コード
	 */
	public static final String BTOBBILL_GROSS_PROFIT_CALC_TOTAL_CODE = "1";
	/**
	 * 法人間請求書一覧画面：税込商品計から算出
	 */
	public static final String BTOBBILL_GROSS_PROFIT_CALC_TOTALNAME = "税込商品計から算出";

	/**
	 * 法人間請求書一覧画面：税抜き商品計から算出コード
	 */
	public static final String BTOBBILL_GROSS_PROFIT_CALC_SUBTOTAL_CODE = "2";
	/**
	 * 法人間請求書一覧画面：税抜き商品計から算出
	 */
	public static final String BTOBBILL_GROSS_PROFIT_CALC_SUBTOTAL_NAME = "税抜き商品計から算出";


	public static final Map<String, String> BTOBBILL_GROSS_PROFIT_CALC_MAP;
	static {
		Map<String, String> btobBillGrossProfitCalcMap = new LinkedHashMap<String, String>();
		btobBillGrossProfitCalcMap.put(BTOBBILL_GROSS_PROFIT_CALC_TOTAL_CODE, BTOBBILL_GROSS_PROFIT_CALC_TOTALNAME);
		btobBillGrossProfitCalcMap.put(BTOBBILL_GROSS_PROFIT_CALC_SUBTOTAL_CODE, BTOBBILL_GROSS_PROFIT_CALC_SUBTOTAL_NAME);
		BTOBBILL_GROSS_PROFIT_CALC_MAP = Collections.unmodifiableMap(btobBillGrossProfitCalcMap);
	}

	/** 売上統計ソート：品番コード */
	public static final String SUMMALY_SORT_CODE_1 = "1";
	/** 売上統計ソート：品番  */
	public static final String SUMMALY_SORT_NAME_1 = "品番";

	/** 売上統計ソート：商品名コード */
	public static final String SUMMALY_SORT_CODE_2 = "2";
	/** 売上統計ソート：商品名 */
	public static final String SUMMALY_SORT_NAME_2 = "商品名";

	/** 売上統計ソート：合計売上金額コード */
	public static final String SUMMALY_SORT_CODE_3 = "3";
	/** 売上統計ソート：合計売上金額 */
	public static final String SUMMALY_SORT_NAME_3 = "合計売上金額";

	/** 売上統計ソート：原価コード */
	public static final String SUMMALY_SORT_CODE_4 = "4";
	/** 売上統計ソート：原価 */
	public static final String SUMMALY_SORT_NAME_4 = "原価";

	/** 売上統計ソート：粗利コード */
	public static final String SUMMALY_SORT_CODE_5 = "5";
	/** 売上統計ソート：粗利 */
	public static final String SUMMALY_SORT_NAME_5 = "粗利";

	/** 売上統計ソート：合計注文数コード */
	public static final String SUMMALY_SORT_CODE_6 = "6";
	/** 売上統計ソート：合計注文数 */
	public static final String SUMMALY_SORT_NAME_6 = "合計注文数";

	/** 売上統計ソートソートマップ */
	public static final Map<String, String> SUMMALY_SORT_MAP;
	static {
		Map<String, String> summalySortMap = new LinkedHashMap<String, String>();
		summalySortMap.put(SUMMALY_SORT_CODE_1, SUMMALY_SORT_NAME_1);
		summalySortMap.put(SUMMALY_SORT_CODE_2, SUMMALY_SORT_NAME_2);
		summalySortMap.put(SUMMALY_SORT_CODE_3, SUMMALY_SORT_NAME_3);
		summalySortMap.put(SUMMALY_SORT_CODE_4, SUMMALY_SORT_NAME_4);
		summalySortMap.put(SUMMALY_SORT_CODE_5, SUMMALY_SORT_NAME_5);
		summalySortMap.put(SUMMALY_SORT_CODE_6, SUMMALY_SORT_NAME_6);
		SUMMALY_SORT_MAP = Collections.unmodifiableMap(summalySortMap);
	}

	/** 業販支払方法コード：掛売 */
	public static final String PAYMENT_METHOD_CODE_1 = "1";
	/** 業販支払方法名：掛売  */
	public static final String PAYMENT_METHOD_NAME_1 = "掛売";

	/** 業販支払方法コード：代引き */
	public static final String PAYMENT_METHOD_CODE_2 = "2";
	/** 業販支払方法名：代引き  */
	public static final String PAYMENT_METHOD_NAME_2 = "代引き";

	/** 業販支払方法コード：銀行振込 */
	public static final String PAYMENT_METHOD_CODE_3 = "3";
	/** 業販支払方法名：銀行振込  */
	public static final String PAYMENT_METHOD_NAME_3 = "銀行振込";

	/** 業販支払方法マップ */
	public static final Map<String, String> PAYMENT_METHOD_MAP;
	static {
		Map<String, String> paymentMethodMap = new LinkedHashMap<String, String>();
		paymentMethodMap.put(PAYMENT_METHOD_CODE_1, PAYMENT_METHOD_NAME_1);
		paymentMethodMap.put(PAYMENT_METHOD_CODE_2, PAYMENT_METHOD_NAME_2);
		paymentMethodMap.put(PAYMENT_METHOD_CODE_3, PAYMENT_METHOD_NAME_3);
		PAYMENT_METHOD_MAP = Collections.unmodifiableMap(paymentMethodMap);
	}


//	/** 法人正式名称コード：KTS */
//	public static final long CORPORATION_FULL_NAME_CODE_1 = 1;
//	/** 法人正式名称：KTS  */
//	public static final String CORPORATION_FULL_NAME_NM_1 = "㈱ｶｲﾝﾄﾞﾃｸﾉｽﾄﾗｸﾁｬｰ";
//
//
//	/** 法人正式名称マップ */
//	public static final Map<Long, String> CORPORATION_FULL_NAME_MAP;
//	static {
//		Map<Long, String> corporationFullNameMap = new LinkedHashMap<Long, String>();
//		corporationFullNameMap.put(CORPORATION_FULL_NAME_CODE_1, CORPORATION_FULL_NAME_NM_1);
//		CORPORATION_FULL_NAME_MAP = Collections.unmodifiableMap(corporationFullNameMap);
//	}

	/** B2時間帯指定MAP */
	public static final Map<String, String> APPOINT_TIME_B2_MAP;
	static {
		Map <String, String> appointTimeB2Map = new LinkedHashMap<String, String>();
		appointTimeB2Map.put("0", "指定なし");
		appointTimeB2Map.put("0812", "午前中");
		appointTimeB2Map.put("1416", "14～16時");
		appointTimeB2Map.put("1618", "16～18時");
		appointTimeB2Map.put("1820", "18～20時");
		appointTimeB2Map.put("1921", "19～21時");
		APPOINT_TIME_B2_MAP = appointTimeB2Map;
	}

	/** ehiden時間帯指定MAP */
	public static final Map<String, String> APPOINT_TIME_EHIDEN_MAP;
	static {
		Map <String, String> appointTimeEhidenMap = new LinkedHashMap<String, String>();
		appointTimeEhidenMap.put("00", "指定なし");
		appointTimeEhidenMap.put("01", "午前中");
		appointTimeEhidenMap.put("12", "12～14時");
		appointTimeEhidenMap.put("14", "14～16時");
		appointTimeEhidenMap.put("16", "16～18時");
		appointTimeEhidenMap.put("18", "18～20時");
		appointTimeEhidenMap.put("04", "18～21時");
		appointTimeEhidenMap.put("19", "19～20時");
		APPOINT_TIME_EHIDEN_MAP = appointTimeEhidenMap;
	}

	/** seino時間帯指定MAP */
    public static final Map<String, String> APPOINT_TIME_SEINO_MAP;
    static {
        Map <String, String> appointTimeSeinoMap = new LinkedHashMap<String, String>();
        appointTimeSeinoMap.put("0", "希望なし");
        appointTimeSeinoMap.put("1", "午前");
        appointTimeSeinoMap.put("2", "午後");
        APPOINT_TIME_SEINO_MAP = appointTimeSeinoMap;
    }

	/**
	 * 販売チャネルID　DBの値　業務販売<br />
	 *
	 */
	public static final int DB_CORPORATE_SALE_ID = 9;

	/*  2015/12/22 ooyama ADD START 法人間請求書機能対応  */

	/**
	 * ：ソート順指定コード：伝票番号
	 */
	public static final String COST_SEARCH_CODE_SLIP_NO = "1";
	/**
	 * ：ソート順指定名：伝票番号
	 */
	public static final String COST_SEARCH_NAME_SLIP_NO = "伝票番号";

	/**
	 * ：ソート順指定コード：取引先法人ID
	 */
	public static final String COST_SEARCH_CODE_SYS_CORPORATION_ID = "2";
	/**
	 * ：ソート順指定名：取引先法人ID
	 */
	public static final String COST_SEARCH_NAME_SYS_CORPORATION_ID = "取引先法人ID";

	/**
	 * ：ソート順指定コード：出庫予定日
	 */
	public static final String COST_SEARCH_CODE_SHIPMENT_PLAN_DATE = "3";
	/**
	 * ：ソート順指定名：出庫予定日
	 */
	public static final String COST_SEARCH_NAME_SHIPMENT_PLAN_DATE = "出庫予定日";

	/**
	 * ：ソート順指定コード：品番
	 */
	public static final String COST_SEARCH_CODE_ITEM_CODE = "4";
	/**
	 * ：ソート順指定名：品番
	 */
	public static final String COST_SEARCH_NAME_ITEM_CODE = "品番";

	/**
	 * ：ソート順指定コード：商品名
	 */
	public static final String COST_SEARCH_CODE_ITEM_NM = "5";
	/**
	 * ：ソート順指定名：商品名
	 */
	public static final String COST_SEARCH_NAME_ITEM_NM = "商品名";


	/** 売上・業販原価一覧ソート指定MAP */
	public static final Map<String, String> SALE_COST_LIST_SORT_MAP;
	static {
		Map <String, String> saleCostListSortMap = new LinkedHashMap<String, String>();
		saleCostListSortMap.put(COST_SEARCH_CODE_SHIPMENT_PLAN_DATE, COST_SEARCH_NAME_SHIPMENT_PLAN_DATE);
		saleCostListSortMap.put(COST_SEARCH_CODE_SLIP_NO, COST_SEARCH_NAME_SLIP_NO);
		saleCostListSortMap.put(COST_SEARCH_CODE_SYS_CORPORATION_ID, COST_SEARCH_NAME_SYS_CORPORATION_ID);
		saleCostListSortMap.put(COST_SEARCH_CODE_ITEM_CODE, COST_SEARCH_NAME_ITEM_CODE);
		saleCostListSortMap.put(COST_SEARCH_CODE_ITEM_NM, COST_SEARCH_NAME_ITEM_NM);
		SALE_COST_LIST_SORT_MAP = saleCostListSortMap;
	}

	/**
	 * ：システム法人ID1
	 */
	public static final String SYS_CORPORATION_ID_1 = "1";
	/**
	 * ：法人コードA
	 */
	public static final String SYS_CORPORATION_CODE_A = "a";
	/**
	 * ：システム法人ID12
	 */
	public static final String SYS_CORPORATION_ID_2 = "2";
	/**
	 * ：法人コードB
	 */
	public static final String SYS_CORPORATION_CODE_B = "b";
	/**
	 * ：システム法人ID3
	 */
	public static final String SYS_CORPORATION_ID_3 = "3";
	/**
	 * ：法人コードC
	 */
	public static final String SYS_CORPORATION_CODE_C = "c";
	/**
	 * ：システム法人ID4
	 */
	public static final String SYS_CORPORATION_ID_4 = "4";
	/**
	 * ：法人コードD
	 */
	public static final String SYS_CORPORATION_CODE_D = "d";
	/**
	 * ：システム法人ID5
	 */
	public static final String SYS_CORPORATION_ID_5 = "5";
	/**
	 * ：法人コードE
	 */
	public static final String SYS_CORPORATION_CODE_E = "e";
	/**
	 * ：システム法人ID6
	 */
	public static final String SYS_CORPORATION_ID_6 = "6";
	/**
	 * ：法人コードF
	 */
	public static final String SYS_CORPORATION_CODE_F = "f";
	/**
	 * ：システム法人ID7
	 */
	public static final String SYS_CORPORATION_ID_7 = "7";
	/**
	 * ：法人コードG
	 */
	public static final String SYS_CORPORATION_CODE_G = "g";
	/**
	 * ：システム法人ID8
	 */
	public static final String SYS_CORPORATION_ID_8 = "8";
	/**
	 * ：法人コードH
	 */
	public static final String SYS_CORPORATION_CODE_H = "h";
	/**
	 * ：システム法人ID9
	 */
	public static final String SYS_CORPORATION_ID_9 = "9";
	/**
	 * ：法人コードI
	 */
	public static final String SYS_CORPORATION_CODE_I = "i";
	/**
	 * ：システム法人ID10
	 */
	public static final String SYS_CORPORATION_ID_10 = "10";
	/**
	 * ：法人コードJ
	 */
	public static final String SYS_CORPORATION_CODE_J = "j";
	/**
	 * ：システム法人ID11
	 */
	public static final String SYS_CORPORATION_ID_11 = "11";
	/**
	 * ：法人コードK
	 */
	public static final String SYS_CORPORATION_CODE_K = "k";
	/**
	 * ：システム法人ID12
	 */
	public static final String SYS_CORPORATION_ID_12 = "12";
	/**
	 * ：法人コードL
	 */
	public static final String SYS_CORPORATION_CODE_L = "l";
	/**
	 * ：システム法人ID13
	 */
	public static final String SYS_CORPORATION_ID_13 = "13";

	/**
	 * ：法人コードM
	 */
	public static final String SYS_CORPORATION_CODE_M = "m";
	/**
	 * ：システム法人ID14
	 */
	public static final String SYS_CORPORATION_ID_14 = "14";
	/**
	 * ：法人コードN
	 */
	public static final String SYS_CORPORATION_CODE_N = "n";
	/**
	 * ：システム法人ID15
	 */
	public static final String SYS_CORPORATION_ID_15 = "15";
	/**
	 * ：法人コードO
	 */
	public static final String SYS_CORPORATION_CODE_O = "o";
	/**
	 * ：システム法人ID16
	 */
	public static final String SYS_CORPORATION_ID_16 = "16";
	/**
	 * ：法人コードP
	 */
	public static final String SYS_CORPORATION_CODE_P = "p";
	/**
	 * ：システム法人ID17
	 */
	public static final String SYS_CORPORATION_ID_17 = "17";
	/**
	 * ：法人コードQ
	 */
	public static final String SYS_CORPORATION_CODE_Q = "q";
	/**
	 * ：システム法人ID20
	 */
	public static final String SYS_CORPORATION_ID_20 = "20";

	/**
	 * ：システム法人ID77
	 */
	public static final String SYS_CORPORATION_ID_77 = "77";

	/**
	 * ：システム法人ID88
	 */
	public static final String SYS_CORPORATION_ID_88 = "88";

	/**
	 * ：システム法人ID99
	 */
	public static final String SYS_CORPORATION_ID_99 = "99";


	/** システム法人ID法人コード指定MAP */
	public static final Map<String, String> SYS_CORPORATION_CODE_MAP;
	static {
		Map <String, String> sysCorporationCodeMap = new LinkedHashMap<String, String>();
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_1, SYS_CORPORATION_CODE_A);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_2, SYS_CORPORATION_CODE_B);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_3, SYS_CORPORATION_CODE_C);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_4, SYS_CORPORATION_CODE_D);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_5, SYS_CORPORATION_CODE_E);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_6, SYS_CORPORATION_CODE_F);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_7, SYS_CORPORATION_CODE_G);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_8, SYS_CORPORATION_CODE_H);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_9, SYS_CORPORATION_CODE_I);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_10, SYS_CORPORATION_CODE_J);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_11, SYS_CORPORATION_CODE_K);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_12, SYS_CORPORATION_CODE_L);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_13, SYS_CORPORATION_CODE_M);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_14, SYS_CORPORATION_CODE_N);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_15, SYS_CORPORATION_CODE_O);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_16, SYS_CORPORATION_CODE_P);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_17, SYS_CORPORATION_CODE_Q);
		sysCorporationCodeMap.put(SYS_CORPORATION_ID_20, SYS_CORPORATION_CODE_I);
		SYS_CORPORATION_CODE_MAP = sysCorporationCodeMap;
	}

	/*  2015/12/22 ooyama ADD END 法人間請求書機能対応  */

	/** 表示 */
	public static final String SUM_DISP_FLG0 = "0";
	/** 非表示 */
	public static final String SUM_DISP_FLG1 = "1";
	/** 表示 */
	public static final String SUM_DISP_LABEL0 = "表示";
	/** 非表示 */
	public static final String SUM_DISP_LABEL1 = "非表示";
	public static final Map<String, String> SUM_DISP_MAP;
	static {
		Map <String, String> map = new LinkedHashMap<String, String>();
		map.put(SUM_DISP_FLG0, SUM_DISP_LABEL0);
		map.put(SUM_DISP_FLG1, SUM_DISP_LABEL1);
		SUM_DISP_MAP = map;
	}
	/** 資料区分フラグ:説明書*/
	public static final String DOCUMENT_TYPE_FLG0 = "0";
	/** 資料区分フラグ:図面*/
	public static final String DOCUMENT_TYPE_FLG1 = "1";
	/** 資料区分フラグ:その他*/
	public static final String DOCUMENT_TYPE_FLG2 = "2";
	/** 資料区分名:説明書*/
	public static final String DOCUMENT_TYPE_LABEL0 = "説明書";
	/** 資料区分名:図面*/
	public static final String DOCUMENT_TYPE_LABEL1 = "図面";
	/** 資料区分名:その他*/
	public static final String DOCUMENT_TYPE_LABEL2 = "その他";
	public static final Map<String, String> DOCUMENT_TYPE_MAP;
	static {
		Map <String, String> documentTypeMap = new LinkedHashMap<String, String>();
		documentTypeMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		documentTypeMap.put(DOCUMENT_TYPE_FLG0, DOCUMENT_TYPE_LABEL0);
		documentTypeMap.put(DOCUMENT_TYPE_FLG1, DOCUMENT_TYPE_LABEL1);
		documentTypeMap.put(DOCUMENT_TYPE_FLG2, DOCUMENT_TYPE_LABEL2);
		DOCUMENT_TYPE_MAP = documentTypeMap;
	}

	/**在庫ダウンロードMap*/
	/**ダウンロード区分コード1：商品情報*/
	public static final String DOWNLOAD_TYPE_CODE1 = "0";
	/**ダウンロード区分コード2：在庫情報*/
	public static final String DOWNLOAD_TYPE_CODE2 = "1";
	/**ダウンロード区分コード3：価格情報*/
	public static final String DOWNLOAD_TYPE_CODE3 = "2";
	/**ダウンロード区分コード4：助ネコCSV*/
	public static final String DOWNLOAD_TYPE_CODE4 = "3";
	/**ダウンロード区分コード5：新在庫DLExcel*/
	public static final String DOWNLOAD_TYPE_CODE5 = "4";
	// added by Wahaha
	/** 受注情報 */
	public static final String DOWNLOAD_TYPE_CODE6 = "5";

	/**ダウンロード区分名1：商品情報*/
	public static final String DOWNLOAD_TYPE_NAME1 = "商品情報";
	/**ダウンロード区分名2：在庫情報*/
	public static final String DOWNLOAD_TYPE_NAME2 = "在庫情報";
	/**ダウンロード区分名3：価格情報*/
	public static final String DOWNLOAD_TYPE_NAME3 = "価格情報";
	/**ダウンロード区分名4：助ネコCSV*/
	public static final String DOWNLOAD_TYPE_NAME4 = "助ネコCSV";
	/**ダウンロード区分名1：商品情報*/
	public static final String DOWNLOAD_TYPE_NAME5 = "新在庫DLExcel";
	// added by Wahaha
	/** 受注情報 */
	public static final String DOWNLOAD_TYPE_NAME6 = "受注情報";
	

	public static final Map<String, String> DOWNLOAD_TYPE_CODE_MAP;
	static {
		Map <String, String> downloadTypeCodeMap = new LinkedHashMap<String, String>();

		downloadTypeCodeMap.put(DOWNLOAD_TYPE_CODE1, DOWNLOAD_TYPE_NAME1);
		downloadTypeCodeMap.put(DOWNLOAD_TYPE_CODE2, DOWNLOAD_TYPE_NAME2);
		downloadTypeCodeMap.put(DOWNLOAD_TYPE_CODE3, DOWNLOAD_TYPE_NAME3);
		downloadTypeCodeMap.put(DOWNLOAD_TYPE_CODE4, DOWNLOAD_TYPE_NAME4);
		downloadTypeCodeMap.put(DOWNLOAD_TYPE_CODE5, DOWNLOAD_TYPE_NAME5);
		downloadTypeCodeMap.put(DOWNLOAD_TYPE_CODE6, DOWNLOAD_TYPE_NAME6);
		DOWNLOAD_TYPE_CODE_MAP = downloadTypeCodeMap;
	}

	/** 海外情報閲覧権限有り */
	public static final String AUTH_INFO_OK = "1";
	/** 海外情報閲覧権限無し */
	public static final String AUTH_INFO_ON = "0";

	/** リードタイム区分コード1：30 */
	public static final String LEAD_TIME_CODE_0 = "0";

	/** リードタイム区分1：30 */
	public static final String LEAD_TIME_LABEL_0 = "30";

	/** リードタイム区分コード2：45 */
	public static final String LEAD_TIME_CODE_1 = "1";

	/** リードタイム区分2：45 */
	public static final String LEAD_TIME_LABEL_1 = "45";

	/** リードタイム区分コード3：60 */
	public static final String LEAD_TIME_CODE_2 = "2";

	/** リードタイム区分3：60 */
	public static final String LEAD_TIME_LABEL_2 = "60";

	/** リードタイム区分コード4：90 */
	public static final String LEAD_TIME_CODE_3 = "3";

	/** リードタイム区分4：90 */
	public static final String LEAD_TIME_LABEL_3 = "90";

	/** リードタイム区分コード5：120 */
	public static final String LEAD_TIME_CODE_4 = "4";

	/** リードタイム区分5：120 */
	public static final String LEAD_TIME_LABEL_4 = "120";

	/** リードタイム区分コード6：150 */
	public static final String LEAD_TIME_CODE_5 = "5";

	/** リードタイム区分6：150 */
	public static final String LEAD_TIME_LABEL_5 = "150";

	/** リードタイム区分コード7：180 */
	public static final String LEAD_TIME_CODE_6 = "6";

	/** リードタイム区分7：180 */
	public static final String LEAD_TIME_LABEL_6 = "180";

	/** リードタイム区分コード指定MAP */
	public static final Map<String, String> LEAD_TIME_MAP;
	static {
		Map <String, String> leadTimeMap = new LinkedHashMap<String, String>();
		leadTimeMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		leadTimeMap.put(LEAD_TIME_CODE_0, LEAD_TIME_LABEL_0);
		leadTimeMap.put(LEAD_TIME_CODE_1, LEAD_TIME_LABEL_1);
		leadTimeMap.put(LEAD_TIME_CODE_2, LEAD_TIME_LABEL_2);
		leadTimeMap.put(LEAD_TIME_CODE_3, LEAD_TIME_LABEL_3);
		leadTimeMap.put(LEAD_TIME_CODE_4, LEAD_TIME_LABEL_4);
		leadTimeMap.put(LEAD_TIME_CODE_5, LEAD_TIME_LABEL_5);
		leadTimeMap.put(LEAD_TIME_CODE_6, LEAD_TIME_LABEL_6);
		LEAD_TIME_MAP = leadTimeMap;
	}

	public static final int ADD_FOREIGN_ORDER_LIST_LENGTH = 50;

	/** 注文ステータス区分コード1：注文書作成 */
	public static final String ORDER_STATUS_CODE_0 = "0";

	/** 注文ステータス区分1：注文書作成 */
	public static final String ORDER_STATUS_LABEL_0 = "A:注文書作成";

	/** 注文ステータス区分コード2：注文済 */
	public static final String ORDER_STATUS_CODE_1 = "1";

	/** 注文ステータス区分2：注文済 */
	public static final String ORDER_STATUS_LABEL_1 = "B:注文済";

	/** 注文ステータス区分コード3：納期1 */
	public static final String ORDER_STATUS_CODE_2 = "2";

	/** 注文ステータス区分3：納期1 */
	public static final String ORDER_STATUS_LABEL_2 = "C:納期1";

	/** 注文ステータス区分コード4：納期2 */
	public static final String ORDER_STATUS_CODE_3 = "3";

	/** 注文ステータス区分4：納期2 */
	public static final String ORDER_STATUS_LABEL_3 = "D:納期2";

	/** 注文ステータス区分コード5：出荷待ち */
	public static final String ORDER_STATUS_CODE_4 = "4";

	/** 注文ステータス区分5：出荷待ち */
	public static final String ORDER_STATUS_LABEL_4 = "E:出荷待ち";

	/** 注文ステータス区分コード6：一部出荷済み */
	public static final String ORDER_STATUS_CODE_5 = "5";

	/** 注文ステータス区分6：一部出荷済み */
	public static final String ORDER_STATUS_LABEL_5 = "F:一部出荷済";

	/** 注文ステータス区分コード7：出荷済み */
	public static final String ORDER_STATUS_CODE_6 = "6";

	/** 注文ステータス区分7：出荷済み */
	public static final String ORDER_STATUS_LABEL_6 = "G:出荷済";

	/** 注文ステータス区分コード8：入荷済 */
	public static final String ORDER_STATUS_CODE_7 = "7";

	/** 注文ステータス区分8：入荷済 */
	public static final String ORDER_STATUS_LABEL_7 = "H:入荷済";


	public static final Map<String, String> ORDER_STATUS_MAP;
	static {
		Map <String, String> orderStatusMap = new LinkedHashMap<String, String>();
		orderStatusMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		orderStatusMap.put(ORDER_STATUS_CODE_0, ORDER_STATUS_LABEL_0);
		orderStatusMap.put(ORDER_STATUS_CODE_1, ORDER_STATUS_LABEL_1);
		orderStatusMap.put(ORDER_STATUS_CODE_2, ORDER_STATUS_LABEL_2);
		orderStatusMap.put(ORDER_STATUS_CODE_3, ORDER_STATUS_LABEL_3);
		orderStatusMap.put(ORDER_STATUS_CODE_4, ORDER_STATUS_LABEL_4);
		orderStatusMap.put(ORDER_STATUS_CODE_5, ORDER_STATUS_LABEL_5);
		orderStatusMap.put(ORDER_STATUS_CODE_6, ORDER_STATUS_LABEL_6);
		orderStatusMap.put(ORDER_STATUS_CODE_7, ORDER_STATUS_LABEL_7);
		ORDER_STATUS_MAP = orderStatusMap;
	}

	/** 注文ステータス区分コード1：注文書作成 */
	public static final String ORDER_STATUS_NUMBERED_CODE_0 = "0";

	/** 注文ステータス区分1：注文書作成 */
	public static final String ORDER_STATUS_NUMBERED_LABEL_0 = "A:注文書作成";

	/** 注文ステータス区分コード2：注文済 */
	public static final String ORDER_STATUS_NUMBERED_CODE_1 = "1";

	/** 注文ステータス区分2：注文済 */
	public static final String ORDER_STATUS_NUMBERED_LABEL_1 = "B:注文済";

	/** 注文ステータス区分コード3：納期1 */
	public static final String ORDER_STATUS_NUMBERED_CODE_2 = "2";

	/** 注文ステータス区分3：納期1 */
	public static final String ORDER_STATUS_NUMBERED_LABEL_2 = "C:納期1";

	/** 注文ステータス区分コード4：納期2 */
	public static final String ORDER_STATUS_NUMBERED_CODE_3 = "3";

	/** 注文ステータス区分4：納期2 */
	public static final String ORDER_STATUS_NUMBERED_LABEL_3 = "D:納期2";

	/** 注文ステータス区分コード5：出荷待ち */
	public static final String ORDER_STATUS_NUMBERED_CODE_4 = "4";

	/** 注文ステータス区分5：出荷待ち */
	public static final String ORDER_STATUS_NUMBERED_LABEL_4 = "E:出荷待ち";

	/** 注文ステータス区分コード6：一部出荷済み */
	public static final String ORDER_STATUS_NUMBERED_CODE_5 = "5";

	/** 注文ステータス区分6：一部出荷済み */
	public static final String ORDER_STATUS_NUMBERED_LABEL_5 = "F:一部出荷済";

	/** 注文ステータス区分コード7：出荷済み */
	public static final String ORDER_STATUS_NUMBERED_CODE_6 = "6";

	/** 注文ステータス区分7：出荷済み */
	public static final String ORDER_STATUS_NUMBERED_LABEL_6 = "G:出荷済";

	/** 注文ステータス区分コード8：入荷済 */
	public static final String ORDER_STATUS_NUMBERED_CODE_7 = "7";

	/** 注文ステータス区分8：入荷済 */
	public static final String ORDER_STATUS_NUMBERED_LABEL_7 = "H:入荷済";

	public static final Map<String, String> ORDER_STATUS_NUMBERED_MAP;
	static {
		Map <String, String> orderStatusNumberedMap = new LinkedHashMap<String, String>();
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_0, ORDER_STATUS_NUMBERED_LABEL_0);
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_1, ORDER_STATUS_NUMBERED_LABEL_1);
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_2, ORDER_STATUS_NUMBERED_LABEL_2);
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_3, ORDER_STATUS_NUMBERED_LABEL_3);
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_4, ORDER_STATUS_NUMBERED_LABEL_4);
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_5, ORDER_STATUS_NUMBERED_LABEL_5);
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_6, ORDER_STATUS_NUMBERED_LABEL_6);
		orderStatusNumberedMap.put(ORDER_STATUS_NUMBERED_CODE_7, ORDER_STATUS_NUMBERED_LABEL_7);
		ORDER_STATUS_NUMBERED_MAP = orderStatusNumberedMap;
	}

	public static final String SEARCH_METHOD_CODE_1 = "1";

	public static final String SEARCH_METHOD_LABEL_1 = "前方一致";

	public static final String SEARCH_METHOD_CODE_2 = "2";

	public static final String SEARCH_METHOD_LABEL_2 = "部分一致";

	public static final String SEARCH_METHOD_CODE_3 = "3";

	public static final String SEARCH_METHOD_LABEL_3 = "後方一致";

	public static final Map<String, String> SEARCH_METHOD_MAP;
	static {
		Map <String, String> searchMethodMap = new LinkedHashMap<String, String>();
		searchMethodMap.put(SEARCH_METHOD_CODE_1, SEARCH_METHOD_LABEL_1);
		searchMethodMap.put(SEARCH_METHOD_CODE_2, SEARCH_METHOD_LABEL_2);
		searchMethodMap.put(SEARCH_METHOD_CODE_3, SEARCH_METHOD_LABEL_3);
		SEARCH_METHOD_MAP = searchMethodMap;
	}

	public static final String FOREIGN_ORDER_SORT_CODE_1 = "1";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_LABEL_1 = "注文日";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_CODE_2 = "2";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_LABEL_2 = "入荷日";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_CODE_3 = "3";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_LABEL_3 = "工場名";

	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_CODE_4 = "4";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_LABEL_4 = "ステータス";

	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_CODE_5 = "5";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_LABEL_5 = "インボイスNo.";

	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_CODE_6 = "6";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_LABEL_6 = "PO No.";


	public static final Map<String, String> FOREIGN_ORDER_ITEM_SORT_MAP;
	static {
		Map<String, String> foreignOrderSortMap = new LinkedHashMap<String, String>();
		foreignOrderSortMap.put(FOREIGN_ORDER_SORT_CODE_1, FOREIGN_ORDER_SORT_LABEL_1);
		foreignOrderSortMap.put(FOREIGN_ORDER_SORT_CODE_2, FOREIGN_ORDER_SORT_LABEL_2);
		foreignOrderSortMap.put(FOREIGN_ORDER_SORT_CODE_3, FOREIGN_ORDER_SORT_LABEL_3);
		foreignOrderSortMap.put(FOREIGN_ORDER_SORT_CODE_4, FOREIGN_ORDER_SORT_LABEL_4);
		foreignOrderSortMap.put(FOREIGN_ORDER_SORT_CODE_5, FOREIGN_ORDER_SORT_LABEL_5);
		foreignOrderSortMap.put(FOREIGN_ORDER_SORT_CODE_6, FOREIGN_ORDER_SORT_LABEL_6);
		FOREIGN_ORDER_ITEM_SORT_MAP = Collections.unmodifiableMap(foreignOrderSortMap);
	}

	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_TYPE_CODE_1 = "1";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_TYPE_LABEL_1 = "昇順";

	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_TYPE_CODE_2 = "2";
	/**
	 * ：
	 */
	public static final String FOREIGN_ORDER_SORT_TYPE_LABEL_2 = "降順";


	public static final Map<String, String> FOREIGN_ORDER_ITEM_SORT_TYPE_MAP;
	static {
		Map<String, String> foreignOrderItemListSortOrder = new LinkedHashMap<String, String>();
		foreignOrderItemListSortOrder.put(FOREIGN_ORDER_SORT_TYPE_CODE_1, FOREIGN_ORDER_SORT_TYPE_LABEL_1);
		foreignOrderItemListSortOrder.put(FOREIGN_ORDER_SORT_TYPE_CODE_2, FOREIGN_ORDER_SORT_TYPE_LABEL_2);
		FOREIGN_ORDER_ITEM_SORT_TYPE_MAP = Collections.unmodifiableMap(foreignOrderItemListSortOrder);
	}

	public static final int FOREIGN_ORDER_ITEM_LIST_PAGE_MAX = 20;

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_CODE_1 = "1";

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_LABEL_1 = "全て未入荷";

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_CODE_2 = "2";

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_LABEL_2 = "一部未入荷";

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_CODE_3 = "3";

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_LABEL_3 = "全て入荷済";

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_CODE_4 = "4";

	public static final String FOREIGN_ORDER_ARRIVE_STATUS_LABEL_4 = "全件";

	public static final Map<String, String> FOREIGN_ORDER_ARRIVE_STATUS_MAP;
	static {
		Map<String, String> foreignOrderArriveStatusMap = new LinkedHashMap<String, String>();
		foreignOrderArriveStatusMap.put(FOREIGN_ORDER_ARRIVE_STATUS_CODE_1, FOREIGN_ORDER_ARRIVE_STATUS_LABEL_1);
		foreignOrderArriveStatusMap.put(FOREIGN_ORDER_ARRIVE_STATUS_CODE_2, FOREIGN_ORDER_ARRIVE_STATUS_LABEL_2);
		foreignOrderArriveStatusMap.put(FOREIGN_ORDER_ARRIVE_STATUS_CODE_3, FOREIGN_ORDER_ARRIVE_STATUS_LABEL_3);
		foreignOrderArriveStatusMap.put(FOREIGN_ORDER_ARRIVE_STATUS_CODE_4, FOREIGN_ORDER_ARRIVE_STATUS_LABEL_4);
		FOREIGN_ORDER_ARRIVE_STATUS_MAP = Collections.unmodifiableMap(foreignOrderArriveStatusMap);
	}

	public static final String FOREIGN_ORDER_PAYMENT_STATUS_CODE_1 = "1";

	public static final String FOREIGN_ORDER_PAYMENT_STATUS_LABEL_1 = "未払い";

	public static final String FOREIGN_ORDER_PAYMENT_STATUS_CODE_2 = "2";

	public static final String FOREIGN_ORDER_PAYMENT_STATUS_LABEL_2 = "払い済";

	public static final String FOREIGN_ORDER_PAYMENT_STATUS_CODE_3 = "3";

	public static final String FOREIGN_ORDER_PAYMENT_STATUS_LABEL_3 = "全件";

	public static final Map<String, String> FOREIGN_ORDER_PAYMENT_STATUS_MAP;
	static {
		Map<String, String> foreignOrderPaymentStatusMap = new LinkedHashMap<String, String>();
		foreignOrderPaymentStatusMap.put(FOREIGN_ORDER_ARRIVE_STATUS_CODE_1, FOREIGN_ORDER_PAYMENT_STATUS_LABEL_1);
		foreignOrderPaymentStatusMap.put(FOREIGN_ORDER_ARRIVE_STATUS_CODE_2, FOREIGN_ORDER_PAYMENT_STATUS_LABEL_2);
		foreignOrderPaymentStatusMap.put(FOREIGN_ORDER_ARRIVE_STATUS_CODE_3, FOREIGN_ORDER_PAYMENT_STATUS_LABEL_3);
		FOREIGN_ORDER_PAYMENT_STATUS_MAP = Collections.unmodifiableMap(foreignOrderPaymentStatusMap);
	}

	/** リードタイム区分コード1：30 */
	public static final String ITEM_LEAD_TIME_CODE_0 = "0";

	/** リードタイム区分1：30 */
	public static final String ITEM_LEAD_TIME_LABEL_0 = "30";

	/** リードタイム区分コード2：45 */
	public static final String ITEM_LEAD_TIME_CODE_1 = "1";

	/** リードタイム区分2：45 */
	public static final String ITEM_LEAD_TIME_LABEL_1 = "45";

	/** リードタイム区分コード3：60 */
	public static final String ITEM_LEAD_TIME_CODE_2 = "2";

	/** リードタイム区分3：60 */
	public static final String ITEM_LEAD_TIME_LABEL_2 = "60";

	/** リードタイム区分コード4：90 */
	public static final String ITEM_LEAD_TIME_CODE_3 = "3";

	/** リードタイム区分4：90 */
	public static final String ITEM_LEAD_TIME_LABEL_3 = "90";

	/** リードタイム区分コード5：120 */
	public static final String ITEM_LEAD_TIME_CODE_4 = "4";

	/** リードタイム区分5：120 */
	public static final String ITEM_LEAD_TIME_LABEL_4 = "120";

	/** リードタイム区分コード6：150 */
	public static final String ITEM_LEAD_TIME_CODE_5 = "5";

	/** リードタイム区分6：150 */
	public static final String ITEM_LEAD_TIME_LABEL_5 = "150";

	/** リードタイム区分コード7：180 */
	public static final String ITEM_LEAD_TIME_CODE_6 = "6";

	/** リードタイム区分7：180 */
	public static final String ITEM_LEAD_TIME_LABEL_6 = "180";

	/** リードタイム区分コード指定MAP */
	public static final Map<String, String> ITEM_LEAD_TIME_MAP;
	static {
		Map <String, String> itemLeadTimeMap = new LinkedHashMap<String, String>();
		itemLeadTimeMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		itemLeadTimeMap.put(ITEM_LEAD_TIME_CODE_0, ITEM_LEAD_TIME_LABEL_0);
		itemLeadTimeMap.put(ITEM_LEAD_TIME_CODE_1, ITEM_LEAD_TIME_LABEL_1);
		itemLeadTimeMap.put(ITEM_LEAD_TIME_CODE_2, ITEM_LEAD_TIME_LABEL_2);
		itemLeadTimeMap.put(ITEM_LEAD_TIME_CODE_3, ITEM_LEAD_TIME_LABEL_3);
		itemLeadTimeMap.put(ITEM_LEAD_TIME_CODE_4, ITEM_LEAD_TIME_LABEL_4);
		itemLeadTimeMap.put(ITEM_LEAD_TIME_CODE_5, ITEM_LEAD_TIME_LABEL_5);
		itemLeadTimeMap.put(ITEM_LEAD_TIME_CODE_6, ITEM_LEAD_TIME_LABEL_6);
		ITEM_LEAD_TIME_MAP = itemLeadTimeMap;
	}

	/**納入先：その他以外コード値：0*/
	public static final String OTHER_THAN_DELIVERY_CODE = "0";
	/**納入先：その他以外*/
	public static final String OTHER_THAN_DELIVERY_LABEL = "その他以外";
	/**納入先：その他コード値：1*/
	public static final String OTHER_DELIVERY_CODE = "1";
	/**納入先：その他*/
	public static final String OTHER_DELIVERY_LABEL = "その他";

	/**納入先種別Map*/
	public static final Map<String, String> DELIVERTY_TYPE;
	static {
		Map<String, String> deliveryTypeMap = new LinkedHashMap<String, String>();
		deliveryTypeMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
		deliveryTypeMap.put(OTHER_THAN_DELIVERY_CODE, OTHER_THAN_DELIVERY_LABEL);
		deliveryTypeMap.put(OTHER_DELIVERY_CODE, OTHER_DELIVERY_LABEL);
		DELIVERTY_TYPE = deliveryTypeMap;
	}

	/** 国内商品検索タイプ0：注文書作成 */
	public static final String DOMESTIC_ITEM_SEARCH_TYPE_0 = "0";
	/** 国内商品検索タイプ1：セット商品管理 */
	public static final String DOMESTIC_ITEM_SEARCH_TYPE_1 = "1";
	/** 国内商品編集画面へ返却する直前の処理種別 */
	public static final String ONLOAD_ACTION_TYPE_REGIST = "1";
	public static final String ONLOAD_ACTION_TYPE_UPDATE = "2";
	public static final String ONLOAD_ACTION_TYPE_DELETE = "3";
	public static final String ONLOAD_ACTION_TYPE_RESET = "0";

	public static final String  RESULT_ITEM_TYPE_NORMAL = "0";				//セット商品フラグ比較値：通常商品
	public static final String RESULT_ITEM_TYPE_SET = "1";				//セット商品フラグ比較値：セット商品
	public static final String RESULT_ITEM_TYPE_FORM = "2";				//セット商品フラグ比較値：構成商品
	public static final String PRINT_CHECK_COMPLETION = "1";

	/*
	 * 御見積書・注文請書・請求書の注意文言。
	 * 振込先の一段下の行に表示。
	 */
	public static final String BEAR_TRANSFER_FEE = "振込手数料は貴社にてご負担願います。";

}