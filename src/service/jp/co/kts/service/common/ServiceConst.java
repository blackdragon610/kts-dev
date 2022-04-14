package jp.co.kts.service.common;

public class ServiceConst {

	/**
	 * 取り込むエクセル行の始め
	 */
	public static final int UPLOAD_EXCEL_INIT_ROWS = 2;
	public static final int UPLOAD_EXCEL_INIT_ROWS1 = 1;

	/**
	 * 取り込むエクセルの列の終わり
	 */
	public static final int UPLOAD_EXCEL_MST_ITEM_LAST_COLUMN = 6;
	public static final int UPLOAD_EXCEL_WAREHOUSE_STOCK_LAST_COLUMN = 5;
	public static final int UPLOAD_EXCEL_ARRIVAL_SCHEDULE_LAST_COLUMN = 5;
	public static final int UPLOAD_EXCEL_ITEM_COST_LAST_COLUMN = 3;
	public static final int UPLOAD_EXCEL_ITEM_PRICE_LAST_COLUMN = 3;
	public static final int UPLOAD_EXCEL_ITEM_MANUAL_LAST_COLUMN = 2;
	public static final int UPLOAD_EXCEL_BACK_ORDER_LAST_COLUMN = 5;
	public static final int UPLOAD_EXCEL_SET_ITEM_LAST_COLUMN = 3;
	public static final int UPLOAD_EXCEL_SET_PRICE_LAST_COLUMN = 31;
	public static final int UPLOAD_EXCEL_SET_PRICE_LAST_COLUMN_NOTAUTH = 28;
	public static final int UPLOAD_EXCEL_SET_DOMESTIC_COLUMN = 12;
//	public static final int UPLOAD_EXCEL_SET_DOMESTIC_ITEM_COLUMN = 19;
	public static final int UPLOAD_EXCEL_ITEM_COLUMN = 20;
	public static final int UPLOAD_EXCEL_NOT_AUTH_ITEM_COLUMN = 13;
	public static final int UPLOAD_EXCEL_SALE_SLIP_COLUMN = 67;


	/**
	 * 日付フォーマット
	 */
	public static final String DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * 在庫一覧の検索結果をダウンロードテンプレートのパス
	 */
	public static final String ITEM_LIST_TEMPLATE_PATH = "/excelTemplate/itemListTemplate.xlsx";

	// added by wahaha
	public static final String ITEM_KEEPLIST_TEMPLATE_PATH = "/excelTemplate/itemKeepListTemplate.xlsx";
	/**
	 * 在庫一覧：商品情報のダウンロードテンプレートパス
	 */
	public static final String ITEM_INFO_LIST_TEMPLATE_PATH = "/excelTemplate/itemInfoListTemplate.xlsx";
	/**
	 * 在庫一覧：商品情報のダウンロードテンプレートパス：権限無
	 */
	public static final String ITEM_INFO_LIST_NOT_AUTH_PATH = "/excelTemplate/itemInfoListNotAuthTemplate.xlsx";

	/**
	 * 在庫一覧：在庫情報のダウンロードテンプレートパス
	 */
	public static final String STOCK_ITEM_INFO_LIST_TEMPLATE_PATH = "/excelTemplate/stockItemInfoListTemplate.xlsx";

	/**
	 * 在庫一覧：価格情報のダウンロードテンプレートパス:DL権限有
	 */
	public static final String PRICE_INFO_LIST_TEMPLATE_PATH = "/excelTemplate/priceInfoListTemplate.xlsx";

	/**
	 * 在庫一覧：価格情報のダウンロードテンプレートパス:DL権限無
	 */
	public static final String NOT_AUTH_PRICE_INFO_LIST_TEMPLATE_PATH = "/excelTemplate/notAuthpriceInfoListTemplate.xlsx";

	/**
	 * 在庫一覧：新在庫情報のダウンロードテンプレートパス:DL権限有
	 */
	public static final String ITEM_ALL_LIST_TEMPLATE_PATH = "/excelTemplate/itemAllListTemplate.xlsx";

	/**
	 * 在庫一覧：新在庫情報のダウンロードテンプレートパス:DL権限無
	 */
	public static final String ITEM_ALL_LIST_TEMPLATE_PATH_NOT_AUTH = "/excelTemplate/itemAllListNotAuthTemplate.xlsx";

	/**
	 * 売上一覧の検索結果をダウンロードテンプレートのパス
	 */
	public static final String SALE_LIST_TEMPLATE_PATH = "/excelTemplate/salesListTemplate.xls";

	/**
	 * 売上統計の結果ダウンロードテンプレートのパス
	 */
	public static final String SALE_SUMMALY_TEMPLATE_PATH = "/excelTemplate/salesSummalyTemplate.xls";

	/**
	 * 業販売上統計の結果ダウンロードテンプレートのパス
	 */
	public static final String CORPORATE_SALE_SUMMALY_TEMPLATE_PATH = "/excelTemplate/corporateSalesSummalyTemplate.xls";

	/**
	 * 業販売上一覧の検索結果をダウンロードテンプレートのパス
	 */
	public static final String CORP_SALE_LIST_TEMPLATE_PATH = "/excelTemplate/corporatSesalesListTemplate.xls";

	/**
	 * 日本郵便送り状テンプレートのパス
	 */
	public static final String YUBIN_TEMPLATE_PATH = "/excelTemplate/yubin.xls";

	/**
	 * 出品データベース一覧の検索結果Excelのダウンロードテンプレートパス
	 */
	public static final String DOMESTIC_TEMPLATE_PATH = "/excelTemplate/domesticExhibition.xls";

	/**
	 * 国内注文管理一覧の検索結果Excelのダウンロードテンプレートパス
	 */
	public static final String DOMESTIC_LIST_TEMPLATE_PATH = "/excelTemplate/domesticList.xls";
	
	/* ID・PASS一覧 */
	public static final String RULE_LIST_TEMPLATE_PATH = "/excelTemplate/ruleListTemplate.xls";

	/**
	 * 海外注文管理一覧の検索結果Excelのダウンロードテンプレートパス
	 */
	public static final String FOREIGN_LIST_TEMPLATE_PATH = "/excelTemplate/foreignList.xls";

	public static final String ITEM_CODE_SHOP_1 = "-qq-e-f1";

	public static final String ITEM_CODE_SHOP_2 = "/e-f1";

	public static final String ITEM_CODE_SHOP_3 = "-qq-e-f2";

	public static final String ITEM_CODE_SHOP_4 = "/e-f2";



}
