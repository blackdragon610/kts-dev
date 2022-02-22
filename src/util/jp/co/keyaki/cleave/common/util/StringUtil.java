package jp.co.keyaki.cleave.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
/**
*
* 文字列操作に関するユーティリティクラス。
*
* @author ytakahashi
*
*/
public class StringUtil {

   /**
    * カタカナの変換テーブルです。（半角カタカナ）
    */
   private static final String[] HALF_KATAKANA = { "ｱ", "ｲ", "ｳ", "ｴ", "ｵ",
       "ｶ", "ｷ", "ｸ", "ｹ", "ｺ", "ｻ", "ｼ", "ｽ", "ｾ", "ｿ", "ﾀ", "ﾁ", "ﾂ",
       "ﾃ", "ﾄ", "ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾋ", "ﾌ", "ﾍ", "ﾎ", "ﾏ",
       "ﾐ", "ﾑ", "ﾒ", "ﾓ", "ﾔ", "ﾕ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ",
       "ｦ", "ﾝ", "ｧ", "ｨ", "ｩ", "ｪ", "ｫ", "ｯ", "ｬ", "ｭ", "ｮ", "ﾜ", "ｶﾞ",
       "ｷﾞ", "ｸﾞ", "ｹﾞ", "ｺﾞ", "ｻﾞ", "ｼﾞ", "ｽﾞ", "ｾﾞ", "ｿﾞ", "ﾀﾞ", "ﾁﾞ",
       "ﾂﾞ", "ﾃﾞ", "ﾄﾞ", "ﾊﾞ", "ﾋﾞ", "ﾌﾞ", "ﾍﾞ", "ﾎﾞ", "ﾊﾟ", "ﾋﾟ", "ﾌﾟ",
       "ﾍﾟ", "ﾎﾟ", "ｰ", "ｰ", "ｰ", "ｰ", "ｰ", "ｰ", "ｰ","ｳﾞ"," ", "ﾞ", "ﾟ" };

   /**
    * カタカナの変換テーブルです。（全角カタカナ）
    */
   private static final String[] ALL_KATAKANA = { "ア", "イ", "ウ", "エ", "オ", "カ",
       "キ", "ク", "ケ", "コ", "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ツ", "テ",
       "ト", "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ", "マ", "ミ",
       "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヲ",
       "ン", "ァ", "ィ", "ゥ", "ェ", "ォ", "ッ", "ャ", "ュ", "ョ", "ヮ", "ガ", "ギ",
       "グ", "ゲ", "ゴ", "ザ", "ジ", "ズ", "ゼ", "ゾ", "ダ", "ヂ", "ヅ", "デ", "ド",
       "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ", "ー", "―", "‐",
       "－", "-","─","━","ヴ","　", "゛", "゜" };
   /**
    *  濁点許容文字
    */
   private static final String[] HALF_KANA_DAKUTEN = {"ｳ","ｶ","ｷ","ｸ","ｹ","ｺ","ｻ","ｼ","ｽ","ｾ","ｿ","ﾀ","ﾁ","ﾂ","ﾃ","ﾄ","ﾊ","ﾋ","ﾌ","ﾍ","ﾎ"};

   /**
    *  半濁点許容文字
    */
   private static final String[] HALF_KANA_HANDAKUTEN = {"ﾊ","ﾋ","ﾌ","ﾍ","ﾎ"};
   /**
    *  濁点許容文字
    */
   private static final String[] ALL_KANA_DAKUTEN = {"ヴ","ガ","ギ","グ","ゲ","ゴ","ザ","ジ","ズ","ゼ","ゾ","ダ","ヂ","ヅ","デ","ド","バ","ビ","ブ","ベ","ボ"};

   /**
    *  半濁点許容文字
    */
   private static final String[] ALL_KANA_HANDAKUTEN = {"パ","ピ","プ","ペ","ポ"};

   /**
    * 小文字アルファべトの変換テーブルです。（半角アルファべト）
    */
   private static final String[] HALF_SMALLALPHABET = { "a", "b", "c", "d", "e", "f", "g",
   	"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
   	"x", "y", "z"};

   /**
    * アルファべトの変換テーブルです。（半角アルファべト）
    */
   private static final String[] HALF_ALPHABET = { "a", "b", "c", "d", "e", "f", "g",
   	"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
   	"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H",
   	"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

   /**
    * アルファべトの変換テーブルです。（全角アルファべト）
    */
   private static final String[] ALL_ALPHABET = { "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ",
   	"ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ","ｖ",
   	"ｗ", "ｘ", "ｙ", "ｚ", "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ",
   	"Ｈ", "Ｉ","Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ",
   	"Ｘ", "Ｙ", "Ｚ"};

   /**
    * 大文字アルファベットの変換テーブルです。（半角アルファベット）
    */
   private static final String[] HALF_CAPITALALPHABET = { "A", "B", "C", "D", "E", "F", "G", "H",
   	"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};


   /**
    * 小文字カタカナの変換テーブルです。
    */
   private static final String[] LOWER_CASE_KATAKANA = {"ｧ", "ｨ", "ｩ", "ｪ", "ｫ", "ｯ", "ｬ", "ｭ", "ｮ"};

   /**
    * 大文字カタカナの変換テーブルです。
    */
   private static final String[] UPPER_CASE_KATAKANA = {"ｱ", "ｲ", "ｳ", "ｴ", "ｵ", "ﾂ", "ﾔ", "ﾕ", "ﾖ"};

   /**
    * 置換対象ハイフンテーブル
    */
   private static final String[] ALL_HYPHEN = {"－","ー","―","‐","ｰ","─","━"};

   /**
    * 半角ハイフン
    */
   private static final String HALF_HYPHEN = "-";

   /**
    * 全角ハイフン
    */
   private static final String UPPER_HYPHEN = "ー";

   /**
    * 数字の変換テーブルです。（半角数字）
    */
   private static final String[] HALF_NUMBER = { "0", "1", "2", "3", "4", "5",
           "6", "7", "8", "9" };

   /**
    * 数字の変換テーブルです。（全角数字）
    */
   private static final String[] ALL_NUMBER = { "０", "１", "２", "３", "４", "５",
           "６", "７", "８", "９" };

   /**
    * 記号の変換テーブルです。（半角記号）
    */
   private static final String[] HALF_SIGN = {
       " ", "!", "\"", "#", "$",
       "%", "&", "'", "(", ")",
       "-", "-", "-", "-", "-",
       "-", "-", "=", "^", "~",
       "\\", "|", "@", "`", "[",
       "{", ";", "+", ":", "*",
       "]", "}", ",", "<", ".",
       ">", "/", "?", "\\", "_",
       "､", "｡", "｢", "｣", "･",
       "ﾞ", "ﾟ"
   };

   /**
    * 記号の変換テーブルです。（全角記号）
    */
   private static final String[] ALL_SIGN = {
       "　", "！", "”", "＃", "＄",
       "％", "＆", "’", "（", "）",
       "－", "ー", "―", "‐", "ｰ",
       "─",  "━", "＝", "＾", "～",
       "￥", "｜", "＠", "‘", "［",
       "｛", "；", "＋", "：", "＊",
       "］", "｝", "，", "＜", "．",
       "＞", "／", "？", "＼", "＿",
       "、", "。", "「", "」", "・",
       "゛", "゜"
   };

   /**
    * 引数の金額を3桁カンマ区切りに変換し返却
    *
    * @param amount 金額(BigDecimal型)
    * @return (3桁カンマ)金額
    */
   public static String formatCalc(BigDecimal amount) {

       // 金額がnullであればnullを返す
       if (amount == null) {
           return null;
       }
       DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###");
       return decimalFormat.format(amount.doubleValue());
   }
   /**
    * 引数の金額を3桁カンマ区切りに変換し返却
    *
    * @param amount 金額(BigDecimal型)
    * @return (3桁カンマ)金額
    */
   public static String formatCalcFloat(BigDecimal amount) {

       // 金額がnullであればnullを返す
       if (amount == null) {
           return null;
       }
       DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###.##");
       return decimalFormat.format(amount.doubleValue());
   }

   /**
    * 引数の金額を3桁カンマ区切りに変換し返却
    *
    * @param amount 金額(BigDecimal型)
    * @return (3桁カンマ)金額
    */
   public static String formatCalcFloatad(BigDecimal amount) {

       // 金額がnullであればnullを返す
       if (amount == null) {
           return null;
       }
       DecimalFormat decimalFormat = new DecimalFormat(".##");
       return decimalFormat.format(amount.doubleValue());
   }

   /**
    * 半角カタカナを全角カタカナに変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String toUpperKatakana(String source) {
       // 濁点･半濁点半角文字を全角に変換
       source = toUpperDakutenHandakuten(source);
       return translate(source, HALF_KATAKANA, ALL_KATAKANA);
   }

   /**
    * 全角カタカナを半角カタカナに変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String toHalfKatakana(String source) {
       return translate(source, ALL_KATAKANA, HALF_KATAKANA);
   }

   /**
    * 濁点･半濁点半角文字を全角に変換
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    *          変換できない場合は変換前のものを返します。
    */
   public static String toUpperDakutenHandakuten(String source) {
       if(source == null || source.length() == 0) {
           return source;
       }
       int index = 0;
       char ch[] = source.toCharArray();

       if (ch[0] == 'ﾞ' || ch[0] == 'ﾟ') {
           // 1文字目が濁点・半濁点の場合
           return source;
       }
       for(int i = 1;i < ch.length;i++) {
           // 濁点置換
           if(ch[i] == 'ﾞ') {
               while(index < HALF_KANA_DAKUTEN.length &&
                   ch[i - 1] != HALF_KANA_DAKUTEN[index].charAt(0)) {
                   index++;
               }
               if (index >= HALF_KANA_DAKUTEN.length) {
                   // 半→全角に変換できない場合
                   return source;
               }
               // 変換前は文字と濁点で2文字使っているので全角濁点文字+ヌル文字に変換
               ch[i - 1] = ALL_KANA_DAKUTEN[index].charAt(0);
               ch[i] = '\0';
               index = 0;
           }
           // 半濁点置換
           else if(ch[i] == 'ﾟ') {
               while(index < HALF_KANA_HANDAKUTEN.length &&
                   ch[i - 1] != HALF_KANA_HANDAKUTEN[index].charAt(0)) {
                   index++;
               }
               if (index >= HALF_KANA_HANDAKUTEN.length) {
               	// 半→全角に変換できない場合
                   return source;
               }
               // 変換前は文字と半濁点で2文字使っているので全角半濁点文字+ヌル文字に変換
               ch[i - 1] = ALL_KANA_HANDAKUTEN[index].charAt(0);
               ch[i] = '\0';
               index = 0;
           }
       }
       // ヌル文字以外を出力
       StringBuffer sb = new StringBuffer();
       for(int i = 0;i < ch.length;i++) {
           if(ch[i] != '\0') {
               sb.append(ch[i]);
           }
       }
       return sb.toString();
   }

   /**
    * 対象文字列がfrom配列の文字に該当した場合、to配列内にある
    * 同一インデックス番号値に置換する
    *
    * @param source 対象となる文字列
    * @param from   置換えられる文字列の配列
    * @param to     置換える文字列の配列
    * @return       置換え後の文字列を返却
    */
   private static String translate(
           String source,
           String[] from,
           String[] to) {

       if (source == null) {
           return null;
       }
       if (from == null) {
           return null;
       }
       if (to == null) {
           return null;
       }

       for (int i = 0; i < from.length && i < to.length; i++) {
           source = StringUtils.replace(source, from[i], to[i]);
       }

       return source;
   }

   /**
    * カタカナ小文字を全角の大文字カタカナに変換します。
    *
    * @param source
    * @return
    */
   public static String allToKatakanaSmall(String source){
       return translate(source, LOWER_CASE_KATAKANA, UPPER_CASE_KATAKANA);
   }

   /**
    * 置換対象文字列を半角ハイフンに変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String allToHalfHypen(String source) {

       return translate(source, ALL_HYPHEN, HALF_HYPHEN);
   }


   /**
    * 対象文字列がfrom配列の文字に該当した場合、toで指定された
    * 文字に置換する
    *
    * @param source 対象となる文字列
    * @param from   置換えられる文字列の配列
    * @param to     置換える文字列
    * @return       置換え後の文字列を返却
    */
   private static String translate(
           String source,
           String[] from,
           String to) {

       if (source == null) {
           return null;
       }
       if (from == null) {
           return null;
       }
       if (to == null) {
           return null;
       }

       for (int i = 0; i < from.length; i++) {
           source = StringUtils.replace(source, from[i], to);
       }

       return source;
   }

   /**
    * 全角数字を半角数字に変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String allToHalfNumber(String source) {

       return translate(source, ALL_NUMBER, HALF_NUMBER);
   }

   /**
    * 半角数字を全角数字に変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String toUpperHalfNumber(String source) {

       return translate(source, HALF_NUMBER, ALL_NUMBER);
   }

   /**
    * アルファベット（全角）をアルファベット（半角）に変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String allToHalfAlphabet(String source) {

       return translate(source, ALL_ALPHABET, HALF_ALPHABET);
   }

   /**
    * アルファベット（半角）をアルファベット（全角）に変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String toUpperHalfAlphabet(String source) {

       return translate(source, HALF_ALPHABET, ALL_ALPHABET);
   }

   /**
    * 記号（全角）を記号（半角）に変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String allToHalfSign(String source) {

       return translate(source, ALL_SIGN, HALF_SIGN);
   }

   /**
    * 記号（半角）を記号（全角）に変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String toUpperSign(String source) {

       return translate(source, HALF_SIGN, ALL_SIGN);
   }

	/**
    * 小文字アルファべト（半角）を大文字アルファベット（半角）に変換します。
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String toUpperAlphabet(String source) {
       return translate(source, HALF_SMALLALPHABET, HALF_CAPITALALPHABET);
   }

   /**
    * <p>
    * セットされたStringオブジェクトをゼロサプレスする。
    * </p>
    *
    * @param arg
    * @return ゼロサプレスされた文字列
    */
   public static String zeroDelete(String arg) {
       if (arg == null) {
           return null;
       }
       try {
           int intObject = Integer.parseInt(arg);
           return String.valueOf(intObject);
       } catch (NumberFormatException ex) {
           return arg;
       }
   }

   /**
    * 指定した文字列のスラッシュを削除します。
    *
    * @param str スラッシュを削除したい文字列
    * @return スラッシュ削除後文字列
    */
   public static String slashDelete(String str) {
       return str.replaceAll("/", "");
   }


   /**
    * BigDecimal型をString型に変換する。
    *
    * @param value
    * @return
    */
   public static String bigDecimalToString(BigDecimal value) {

       if (value == null) {
           return "";
       }
       return value.toString();
   }

   public static String toAllHyphen(String source) {

       return translate(source, ALL_HYPHEN, UPPER_HYPHEN);

   }


   /**
    * 配列の空文字をnullに置換する。
    *
    * @param values
    * @return
    */
   public static String[] toNullReplaceForArray(String[] values) {
       if(values.length < 1) {
           return values;
       }

       // 空文字をnull置換
       for (int i = 0; i < values.length;i++) {
           if(values[i] != null && values[i].equals("")) {
               values[i] = null;
           }
       }

       return values;
   }

   /**
    * nullを空文字に変換する。
    */
   public static String nullToSpace(String value) {
   	if (value == null) {
   		return "";
   	}

   	return value;
   }

   /**
    * String型をBigDecimal型へ変換する。
    *
    * @param value 変換前文字
    * @return 変換後文字
    */
   public static BigDecimal StringToBigDecimal(String value) {
       if (GenericValidator.isBlankOrNull(value)) {
           return null;
       }
       if (value.substring(0, 1).equals("-")) {
           // "-"から始まる場合
           if (!StringUtils.isNumeric(value.substring(1))) {
               return null;
           }
       } else {
           value = value.replace(".", "");
           if (!StringUtils.isNumeric(value)) {
               return null;
           }
       }
       return new BigDecimal(value);
   }

   /**
    * 末尾の全角／半角スペースをトリムする。
    *
    * @param value 変換前文字
    * @return 変換後文字
    */
   public static String trimRightSpace(String value) {

       if (value == null) {
           return value;
       }

       char[] ary = value.toCharArray();
       StringBuilder buf = new StringBuilder("");

       for (int i = ary.length-1; 0 <= i; i--) {
           if (ary[i] == ' ' || ary[i] == '　') {
               continue;
           } else {
               buf.append(ary[i]);
           }
       }
       return buf.reverse().toString();
   }

   /**
    * 半角を全角に変換します。
    * １、濁点･半濁点半角文字
    * ２、半角アルファベット
    * ３、半角数字
    * ４、半角カタカナ
    * ５、半角記号
    *
    * @param source
    *            変換対象を指定してください。
    * @return 変換結果を返却します。
    */
   public static String toUpperPlurals(String source) {

   	source = toUpperDakutenHandakuten(source);
   	source = toUpperHalfAlphabet(source);
   	source = toUpperHalfNumber(source);
   	source = toUpperKatakana(source);
   	source = toUpperSign(source);

       return source;
   }
   /**
    * String型の文字列に全角が含まれているかチェック。
    * @param str
    * 		入力チェックする文字列。
    * @return 全角が含まれていたら"Em-size"という文字列を返す。
    * 		  全角が含まれていなかったら"normal-width"を返す。
    */
   public static String judgeEmSize (String str){
	   String mojiType = null;
	   char[] chars = str.toCharArray();
	   for (int i =0; i < chars.length; i++){
		   if (String.valueOf(chars[i]).getBytes().length < 2) {

		   } else {
			   mojiType = "Em-size";
			   return mojiType;
		   }
	   }
	   mojiType = "normal-width";
	   return mojiType;

   }

   /**
    * String型の文字列に全角が含まれているかチェック。
    * @param str
    * 		入力チェックする文字列。
    * @return 全角が含まれていたら"Em-size"という文字列を返す。
    * 		  全角が含まれていなかったら"normal-width"を返す。
    */
   public static boolean boolEmSize (String str){
	   char[] chars = str.toCharArray();

	   if(chars.length != str.length()){
		   return true;
	   }
	   return false;
   }

   /**
    * yyyymmddの日付の妥当性をチェック
    * @param strDate
    * 		入力チェックする文字列。
    * @return yyyymmddが存在する日付ならtrue
    * 					存在しない日付ならfalse
    */
   public static boolean checkDate(String strDate) {

	   if (strDate.length() != 8) {
		   return false;
	   }
	   SimpleDateFormat formatter =(SimpleDateFormat)DateFormat.getDateInstance();
	   formatter.applyPattern("yyyymmdd");
	   try{
		   formatter.parse(strDate);
		   return true;
	   }
	   catch (Exception e) {
		   return false;
	   }

   }

   public static final String SWITCH_ON_KEY ="on";
   public static final String SWITCH_ON_VALUE ="1";
   public static final String SWITCH_OFF_KEY ="off";
   public static final String SWITCH_OFF_VALUE ="0";

	public static String switchCheckBox(String key) {

		String value = null;

		switch (key) {

			case SWITCH_ON_KEY:
				value = SWITCH_ON_VALUE;
				break;

			case SWITCH_ON_VALUE:
				value = SWITCH_ON_KEY;
				break;

			case SWITCH_OFF_KEY:
				value = SWITCH_OFF_VALUE;
				break;

			case SWITCH_OFF_VALUE:
				value = SWITCH_OFF_KEY;
				break;
			default:
				value = StringUtils.EMPTY;
		}
		return value;
	}
	public static String switchCheckBoxDisp(String key) {

		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		String value = null;

		switch (key) {

			case SWITCH_ON_VALUE:
				value = SWITCH_ON_KEY;
				break;

			case SWITCH_OFF_VALUE:
				value = SWITCH_OFF_KEY;
				break;

			default:
				value = StringUtils.EMPTY;
		}
		return value;
	}

	/**
	 * フラグ読み替え
	 * on:1
	 * off:0
	 * else:Empty
	 * @param key
	 * @return
	 */
	public static String switchCheckBoxDB(String key) {

		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}

		String value = null;

		switch (key) {

			case SWITCH_ON_KEY:
				value = SWITCH_ON_VALUE;
				break;

			case SWITCH_OFF_KEY:
				value = SWITCH_OFF_VALUE;
				break;

			default:
				value = StringUtils.EMPTY;
		}
		return value;
	}
	/**
	 * 今日の日付をyyyy/MM/ddで取得する
	 * @return
	 */
	public static String getToday() {

		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		return dateFormat.format(today);
	}

	/**
	 * 文字列の行数を取得する
	 * @return
	 */
	public static int getlinelength(String str) {

		String[] strlist = str.split("\r\n");

		return strlist.length;
	}
}
