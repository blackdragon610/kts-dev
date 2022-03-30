/****************************************************
 * ▼共通関数
 ****************************************************/

/**
 * サブウィンドウを表示する。
 */
function openSubWindow(action, windowName, option) {
	var subWindow = window.open(action, windowName, option);
	subWindow.focus();
	return false;
}

/**
 * disabled解除し画面遷移をする。
 *
 * @param actionPath
 *            アクションパス
 */
function undisabledGoTransaction(actionPath) {
	undisabled();
	document.forms[0].action = actionPath;
	document.forms[0].target = "_self";
	document.forms[0].submit();
}

/**
 * 画面遷移をする。
 *
 * @param actionPath
 *            アクションパス
 */
function goTransaction(actionPath) {
	document.forms[0].action = actionPath;
	document.forms[0].target = "_self";
	document.forms[0].submit();
}

function goTransactionNew(actionPath) {
	document.forms[0].action = actionPath;
	document.forms[0].target = "_new";
	document.forms[0].submit();
}

function goTransactionBlank(actionPath) {
	document.forms[0].action = actionPath;
	document.forms[0].target = "_blank";
	document.forms[0].submit();
}

function goWindowTransaction(actionPath) {
	window.opener.document.forms[0].action = actionPath;
	window.opener.document.forms[0].target = "_self";
	window.opener.document.forms[0].submit();
}

function mout(obj) {
	obj.style.backgroundColor = "transparent";
}

function mover(obj) {
	obj.style.backgroundColor = "#a3d9fd";
}

function mout_end(obj) {
	obj.style.backgroundColor = "#aaaaaa";
}
function mout_color(obj) {
	obj.style.backgroundColor = "#FF99FF";
}
/**
 * 数字系入力コントロールの値がに対して、数字文字列のみで値が構成されているかチェックします.
 *
 * 内部的に、一度文字列内のカンマを除去した値に対してチェックします.
 * チェックに違反した場合は、コントロールの値をZEROに置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:数字のみまたは空文字/false:数字以外の文字列
 */
function checkNum(obj) {
	var str = removeComma(obj.value);
	if (!str.match("^[0-9]*$")) {
		alert("入力された文字が数字ではありません。");
		obj.value = '0';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}
/**
 * ゼロ埋め
 *
 * @param オブジェクト
 * @param 文字数
 * @returns {Boolean}
 */
function zeroFormat(obj, max) {
	var tmp = "" + obj.value;

	if (tmp.length == 0) {
		return false;
	}
	while (tmp.length < max) {
		tmp = "0" + tmp;
	}
	obj.value = tmp;
}

/**
 * 日付の表示変換. 例 2012/1/1→2012/01/01 例 20120101→2012/01/01
 *
 * @param オブジェクト
 */
function changeDate(obj) {

	var arayStr = obj.value.split("/");

	if (arayStr.length == 3) {
		var year = arayStr[0];
		var month = arayStr[1];
		var day = arayStr[2];
		if (!(year.length == 4 && year.match("^[0-9]*$"))) {
			alert("/を使う場合は年は4桁の半角数字で入力してください。");
			obj.value = "";
			obj.focus();
			obj.select();
			return;
		}
		if (!(month.length >= 1 && month.length <= 2 && month.match("^[0-9]*$"))) {
			alert("/を使う場合は月は2桁か1桁の半角数字で入力してください。");
			obj.value = "";
			obj.focus();
			obj.select();
			return;
		}
		if (month.length == 1 && month.match("^[1-9]*$")) {
			month = "0" + arayStr[1];
		}

		if (!(day.length >= 1 && day.length <= 2 && day.match("^[0-9]*$"))) {
			alert("/を使う場合は日は2桁か1桁の半角数字で入力してください。");
			obj.value = "";
			obj.focus();
			obj.select();
			return;
		}
		if (day.length == 1 && day.match("^([1-9]{1})*$")) {
			day = "0" + arayStr[2];
		}

		var dt = new Date(year, month - 1, day);
		if (dt == null || dt.getFullYear() != year || dt.getMonth() + 1 != month || dt.getDate() != day) {
			alert("存在しない日付です。");
			obj.value = "";
			obj.focus();
			obj.select();
			return;
		}

		obj.value = year + "/" + month + "/" + day;

	} else if (arayStr.length == 1) {

		var str = obj.value;

		if (str.length == 8 && str.match("^[0-9]*$")) {
			obj.value = str.substr(0, 4) + "\/" + str.substr(4, 2) + "\/"
					+ str.substr(6, 2);

		} else {
			if (!str == '') {
				alert("半角数字8桁で入力してください。\nまたは、yyyy/mm/ddで入力してください。");
				obj.value = "";
				obj.focus();
				obj.select();
			}
		}
	} else {
		alert("半角数字8桁で入力してください。\nまたは、yyyy/mm/ddで入力してください。");
		obj.value = "";
		obj.focus();
		obj.select();
	}
}

/**
 * 日付の表示変換（年月）. 例 2012/1→2012/01 例 20120101→2012/01
 *
 * @param オブジェクト
 */
function changeDateYM(obj) {

	var arayStr = obj.value.split("/");

	if (arayStr.length == 2) {
		var year = arayStr[0];
		var month = arayStr[1];

		if (!(year.length == 4 && year.match("^[0-9]*$"))) {
			alert("/を使う場合は年は4桁の半角数字で入力してください。");
			obj.value = "";
			obj.focus();
			obj.select();
			return;
		}
		if (!(month.length >= 1 && month.length <= 2 && month.match("^[0-9]*$"))) {
			alert("/を使う場合は月は2桁か1桁の半角数字で入力してください。");
			obj.value = "";
			obj.focus();
			obj.select();
			return;
		}
		if (month.length == 1 && month.match("^[1-9]*$")) {
			month = "0" + arayStr[1];
		}

		var dt = new Date(year, month - 1);
		if (dt == null || dt.getFullYear() != year || dt.getMonth() + 1 != month) {
			alert("存在しない日付です。");
			obj.value = "";
			obj.focus();
			obj.select();
			return;
		}

		obj.value = year + "/" + month;

	} else if (arayStr.length == 1) {

		var str = obj.value;

		if (str.length == 6 && str.match("^[0-9]*$")) {
			obj.value = str.substr(0, 4) + "\/" + str.substr(4, 2);
		} else {
			if (!str == '') {
				alert("半角数字6桁で入力してください。\nまたは、yyyy/mmで入力してください。");
				obj.value = "";
				obj.focus();
				obj.select();
			}
		}
	} else {
		alert("半角数字6桁で入力してください。\nまたは、yyyy/mmで入力してください。");
		obj.value = "";
		obj.focus();
		obj.select();
	}
}



/**
 * 数字系入力コントロールの値がZEROの場合、空文字に変更します.
 *
 * @param 数字系入力コントロール
 */
function changeBlank(obj) {
	if (obj.value == '0' || obj.value == '0.0') {
		obj.value = '';
	}
}

/**
 * 数字系入力コントロールの値が空文字の場合、ZEROに変更します.
 *
 * @param 数字系入力コントロール
 */
function change0(obj) {
	if (obj.value == '') {
		obj.value = '0';
	}
}

/**
 * 数字系入力コントロールの値が空文字の場合、0.0に変更します.
 *
 * @param 数字系入力コントロール
 */
function change0_0(obj) {

	if (obj.value == '') {
		obj.value = '0.0';
	}
}
/**
 * 文字列内のカンマの除去した値を返します.
 *
 * @param val
 *            処理対象文字列
 * @return カンマを除去した文字列
 */
function removeComma(val) {
	return new String(val).replace(/,/g, "");
}

/**
 * 数字系入力コントロールの値がに対して、カンマ付与値に変更します.
 *
 * 内部的に、一度文字列内のカンマを除去してから行うため、すでにカンマ付与済みなど意識する必要はない.
 *
 * @param 数字系入力コントロール
 */
function addComma(obj) {
	var num = removeComma(obj.value);
	while (num != (num = num.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
	obj.value = num;
}

/**
 * 数字系入力コントロールの値がに対して、カンマ付与値に変更します.
 *
 * 内部的に、一度文字列内のカンマを除去してから行うため、すでにカンマ付与済みなど意識する必要はない.
 *
 * @param 数字系入力コントロール
 */
function addCommaFloat(obj) {
	var num = removeComma(obj.value);
	if (num.indexOf("\.") != -1) {
		var parts = num.split("\.");
		var integerPart = parts[0];
		var decimalPart = parts[1];
		while (integerPart != (integerPart = integerPart.replace(
				/^(-?\d+)(\d{3})/, "$1,$2")));
		obj.value = integerPart + "\." + decimalPart;
	} else {
		addComma(obj);
	}
}

function checkZipCode(obj) {
	var str = obj.value;
	if (!(str == "" || str == null)) {
		if (!str.match(/^\d{3}\-\d{4}$/)) {
			alert("正しいフォーマットで入力してください。");
			obj.value="";
		}
	}
}
/**
 * 数字系入力コントロールの値がに対して、数字文字列のみで値が構成されているかチェックします.
 *
 * 内部的に、一度文字列内のカンマを除去した値に対してチェックします. チェックに違反した場合は、コントロールの値をZEROに置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:数字のみまたは空文字/false:数字以外の文字列
 */
function checkNum(obj) {
	var str = removeComma(obj.value);
	if (!str.match("^[0-9]*$")) {
		alert("入力された文字が数字ではありません。");
		obj.value = '0';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}

/**
 * 数字系入力コントロールの値がに対して、数字文字列のみで値が構成されているかチェックします.
 *
 * 内部的に、一度文字列内のカンマを除去した値に対してチェックします. チェックに違反した場合は、コントロールの値をZEROに置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:数字のみまたは空文字/false:数字以外の文字列
 */
function checkNumWrongMes(obj) {
	var str = removeComma(obj.value);
	if (!str.match("^[0-9]*$")) {
		alert("半角数字のみで入力してください。");
		obj.value = '0';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}

/**
 * 数字系入力コントロールの値がに対して、数字文字列のみで値が構成されているかチェックします.
 *
 * チェックに違反した場合は、コントロールの値を''に置き換えます.
 *(左に0が存在する場合取り除く)
 * @param 数字系入力コントロール
 * @return true:数字のみまたは空文字/false:数字以外の文字列
 */
function numberCheck(obj) {
	var str = obj.value;
	if (!str.match("^[0-9]*$")) {
		alert("半角数字のみで入力してください。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	} else {
		obj.value = trimLeftZero(obj);
	}
	return true;
}
/**
 * 上記の左側の0を取り除かないバージョン
 * @param obj
 * @returns {Boolean}
 */
function numberCheckCormal(obj) {
	var str = obj.value;
	if (!str.match("^[0-9]*$")) {
		alert("半角数字のみで入力してください。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}
/**
 * 上記のマイナス許容バージョン
 * @param obj
 * @returns {Boolean}
 */
function numberCheckCormalMinus(obj) {
	var str = obj.value;
	if (!str.match("^-?[0-9]*$")) {
		alert("半角数字のみで入力してください。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}
/**
 * 半角数字&桁数チェック
 * @param obj
 * @param strLength
 * @returns {Boolean}
 */
function numLengthCheck(obj, strLength) {
	var str = obj.value;
	if (!str.match("^[0-9]*$")) {
		alert("半角数字のみで入力してください。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	} else if(str != "" && str.length != strLength) {
		alert(strLength + '桁で入力してください。');
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}


/**
 * 数字系入力コントロールの値がに対して、先頭文字がマイナスそれ以降数字文字列のみで値が構成されているかチェックします.
 *
 * 内部的に、一度文字列内のカンマを除去した値に対してチェックします. チェックに違反した場合は、コントロールの値をZEROに置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:先頭文字がマイナスであり数字のみまたは空文字/false:数字以外の文字列またはマイナスが先頭にない文字列
 */
function checkMinusNum(obj) {
	var str = removeComma(obj.value);
	if (!str.match("^([0]|[\-][0-9]*)$")) {
		alert("入力された文字がマイナスの数字ではありません。");
		obj.value = '0';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}

/**
 * 数字系入力コントロールの値がに対して、先頭文字がマイナスまたは数字文字列のみで値が構成されているかチェックします.
 *
 * 内部的に、一度文字列内のカンマを除去した値に対してチェックします. チェックに違反した場合は、コントロールの値をZEROに置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:先頭文字がマイナスまたは数字のみまたは空文字/false:数字以外の文字列
 */
function checkPulasMinusNum(obj) {
	var str = removeComma(obj.value);
	if (!str.match("^[\-]*[0-9]*$")) {
		alert("入力された文字が数字ではありません。");
		obj.value = '0';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}

function checkPulasMinusNumSearch(obj) {
	var str = removeComma(obj.value);
	if (!str.match("^[\-]*[0-9]*$")) {
		alert("入力された文字が数字ではありません。");
		//obj.value = '0';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}

/**
 * 数字系入力コントロールの値がに対して、数字文字列およびピリオドのみで値が構成されているかチェックします.
 *
 * 内部的に、一度文字列内のカンマを除去した値に対してチェックします. チェックに違反した場合は、コントロールの値を0.0に置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:数字のみまたは空文字/false:数字以外の文字列
 */
function checkFloat(obj) {
	var str = removeComma(obj.value);
	if (!str.match("^[0-9]+\.[0-9]+$")) {
		alert("入力された文字が数字ではありません。");
		obj.value = '0.0';
		obj.focus();
		obj.select();
		return false;
	}
//	if (str.indexOf("\.") != -1) {
//		var parts = str.split("\.");
//		var integerPart = parts[0];
//		var decimalPart = parts[1];
//		if (integerPart.length > 7 || decimalPart.length > 4) {
//			alert("整数部７桁、小数部４桁以内にしてください。");
//			return false;
//		}
//	} else {
//		if (str.length > 7) {
//			alert("整数部７桁、小数部４桁以内にしてください。");
//			return false;
//		}
//	}
	return true;
}

/**
 * 数字系入力コントロールの値がに対して、数字文字列およびピリオドのみで値が構成されているかチェックします.
 *
 * チェックに違反した場合は、コントロールの値を''に置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:数字のみまたは空文字/false:数字以外の文字列
 */
function numberFloatCheck(obj) {
	var str = obj.value;
	if (!str.match("^[0-9]+\.[0-9]+$") && !str.match("^[0-9]*$") ) {
		alert("半角数字のみで入力してください。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	}
//	if (str.indexOf("\.") != -1) {
//		var parts = str.split("\.");
//		var integerPart = parts[0];
//		var decimalPart = parts[1];
//		if (integerPart.length > 7 || decimalPart.length > 3) {
//			alert("整数部７桁、小数部３桁以内にしてください。");
//			obj.value = '';
//			obj.focus();
//			obj.select();
//			return false;
//		}
//	} else {
//		if (str.length > 7) {
//			alert("整数部７桁、小数部３桁以内にしてください。");
//			obj.value = '';
//			obj.focus();
//			obj.select();
//			return false;
//		}
//	}
	return true;
}

/**
 * 数字系入力コントロールの値がに対して、ハイフンまたは数字文字列のみで値が構成されているかチェックします.
 *
 * 先頭文字がハイフンの場合もチェックします．チェックに違反した場合は、コントロールの値を''に置き換えます.
 *
 * @param 数字系入力コントロール
 * @return true:先頭文字がマイナスまたは数字のみまたは空文字/false:数字以外の文字列
 */
function checkTelNum(obj) {
	var str = obj.value;
	if (str.match("^[\-]")) {
		alert("先頭文字がハイフンです。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	}
//	if (str.match("[\-]$")) {
//		alert("末尾文字がハイフンです。");
//		obj.value = '';
//		obj.focus();
//		obj.select();
//		return false;
//	}
	if (!str.match("^[0-9\-]*$")) {
		alert("半角数字とハイフンのみで入力してください。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}

function judgmentIsZenkaku(obj) {
	for ( var i = 0; i < obj.length; ++i) {
		var c = obj.charCodeAt(i);
		// 半角カタカナは不許可
		if (c < 256 || (c >= 0xff61 && c <= 0xff9f)) {
			return false;
		}
	}
	return true;
}

function checkIsZenkaku(obj) {
	if (!judgmentIsZenkaku(obj.value)) {
		alert("指定の文字に全角以外の文字が含まれています。");
	}
}

//伝票No確認
function slipNoCheck (obj, headStr) {

	var slipNo = obj.value;
	var lowerHead = headStr.toLowerCase();
	var regexStr = "^[" + lowerHead + headStr + "][0-9]{2}-[0-9]{4}$";
	var regex = new RegExp(regexStr);

	if (slipNo.length == 0) {
		return false;
	}

	if (!slipNo.match(regex)) {
		alert("伝票Noの書式が間違っています。");
		obj.focus();
		obj.select();
		return true;
	}
}

//作品No確認
function itemNoCheck (obj) {

	var itemNo = obj.value;
	var regexStr = "^[0-9]{2}-[0-9]{5}$";
	var regex = new RegExp(regexStr);

	if (itemNo.length == 0) {
		return false;
	}

	if (!itemNo.match(regex)) {
		alert("作品Noの書式が間違っています。");
		obj.focus();
		obj.select();
		return true;
	}
}

//カンマ付与後の値に対してチェック
function priceCheck (obj) {

	var price = obj.value;
	var priceAfterRemoveComma = removeComma(price);

	if (!priceAfterRemoveComma.match("^[0-9]*$")) {
		alert("半角数字のみで入力して下さい。");
		obj.value = '';
		obj.focus();
		obj.select();
		return false;
	}
}

//半角英数字チェック
function checkAlphanumeric(obj) {
	var str = obj.value;
	if(str.match(/[^0-9A-Za-z]+/) == null) {
		return true;
	} else {
		alert("半角英数字のみで入力して下さい。");
		return false;
	}
}

//半角英数字ハイフン、スペース、アンダーバー、スラッシュチェック:-含む
function checkAlnumHyp(obj) {
	var str = obj.value;
	if(str.match(/[^0-9A-Za-z- /_]+/) == null) {
		return true;
	} else {
		alert("半角英数字のみで入力して下さい。");
		return false;
	}
}

//半角英数字チェック:-.含む
function checkAlnumHypPeri(obj) {
	var str = obj.value;
	if(str.match(/[^0-9A-Za-z-.]+/) == null) {
		return true;
	} else {
		alert("半角英数字のみで入力して下さい。");
		return false;
	}
}

//半角数字ハイフンドット半角スペースのみ　itemList.jspに同様の記載あり
function checkAlnumHypPeriSpe(obj) {
	var str = obj.value;
	if (str.match("^[0-9A-Za-z\-\.\ ]*$")) {
		return true;
	} else {
		alert("半角英数字ハイフン「-」ドット「.」半角スペースのみで入力してください。");
		return;
	}
}

/*******************************************************************************
 * disabled解除
 ******************************************************************************/
function undisabled()
{
    $("*").removeAttr('disabled');
    return true;
}
/*******************************************************************************
 * ▼ 外部JSファイル インポート：サードパーティライブラリ
 ******************************************************************************/


function mout(obj) {
	obj.style.backgroundColor = "transparent";
}

function mover(obj) {
	obj.style.backgroundColor = "#a3d9fd";
}

function mout_end(obj) {
	obj.style.backgroundColor = "#aaaaaa";
}
function mout_color(obj) {
	obj.style.backgroundColor = "#FF99FF";
}
/*
タブの切り替え
bpref = tab body prefix, hpref = tab header prefix
*/
function seltab(bpref, hpref, id_max, selected) {
  if (! document.getElementById) return;
  for (var i = 0; i <= id_max; i++) {
    if (! document.getElementById(bpref + i)) continue;
    if (i == selected) {
      document.getElementById(bpref + i).style.visibility = "visible";
      document.getElementById(bpref + i).style.position = "";
      document.getElementById(hpref + i).className = "open";
    } else {
      document.getElementById(bpref + i).style.visibility = "hidden";
      document.getElementById(bpref + i).style.position = "absolute";
      document.getElementById(hpref + i).className = "close";
    }
  }
}

/*
→キーをクリックをtabキークリックに置き換える
ただし、text、textareaの場合はカーソル位置が
右端の場合のみ実行する。
*/
function tabChn(obj)
{
	var sElmType	= obj.type;
	var iPos		= getPos();
	//alert(sElmType);
	//alert(iPos);
	//alert(window.event.keyCode);
	if (window.event.keyCode == 39){
		if (sElmType == 'text' || sElmType == 'textarea'){
			if (iPos == 0 || obj.value == ""){
				//カーソルが右端もしくは何も入力されていない場合にタブ移動する。
				window.event.keyCode = 9;
			}
		}else if(sElmType == 'undefined'){
			//エレメントタイプを取得できない場合は何もしない
		}else{
			//その他のエレメントの場合は無条件でタブ移動する
				window.event.keyCode = 9;
		}
	}

}

//現在のカーソル位置を返す
function getPos(){
	var r=document.selection.createRange();
	r.moveEnd("textedit");
	return r.text.length;
}

//引数のIDをもつエレメントを返す
function $(id){
	return document.getElementById(id);
}

//引数のIDを持つエレメントのvalue値を返す
//エレメントが存在しない場合は空文字を返す
function $f(id){
	if ($(id)) return $(id).value;
	return "";
}

//引数が空文字の場合は0を返す
//数字の場合は数字型にキャストした値を返す
//それ以外の場合は引数をそのまま返す
function Nz(str){
	//alert(str);
	if (str == "") return 0;
	if (!isNaN(str)) return parseInt(str);
	return str;
}

// フォームを受け取り、データ形式を変更する
function changeMimeTypeNormal(form) {
	form.enctype = "application/x-www-form-urlencoded";
	form.encoding = "application/x-www-form-urlencoded"; //IE対策
}

function changeMimeTypeData(form) {
	form.enctype = "multipart/form-data";
	form.encoding = "multipart/form-data"; //IE対策
}

//フォームを受け取り、データ形式を変更する
function changeMimeType (form, boolean) {
	if (boolean) {
		form.enctype = "multipart/form-data";
		form.encoding = "multipart/form-data"; //IE対策
	} else {
		form.enctype = "application/x-www-form-urlencoded";
		form.encoding = "application/x-www-form-urlencoded"; //IE対策
	}
}
function trimLeftZero(obj){
	   var tmpBefore=obj.value;
       var rep = new RegExp("^0+0?");
       return obj.value=tmpBefore.replace(rep,"");

}
function actAlert(alertTypeValue) {

	if (alertTypeValue == '0' || alertTypeValue == '') {
		return;
	} else if (alertTypeValue == '1') {
		alert('登録しました。');
	} else if (alertTypeValue == '2') {
		alert('更新しました。');
	} else if (alertTypeValue == '3') {
		alert('削除しました。');
	} else if (alertTypeValue == '4') {
		alert('組立可数更新が完了しました。');
	} else if (alertTypeValue == '5') {
		alert('組立可数更新に失敗しました\r\n再度更新を行ってください。');
	}
}

function actRuleAlert(alertTypeValue, alertDescription) {

	if (alertTypeValue == '0' || alertTypeValue == '') {
		return;
	} else if (alertTypeValue >= '1') {
		alert(alertDescription);
	
	}
}

function numActAlert(alertTypeValue, numObj) {

	if (alertTypeValue == '0' || alertTypeValue == '') {
		return;
	} else if (alertTypeValue == '1') {
		alert(numObj.val() + '件登録しました。');
	} else if (alertTypeValue == '2') {
		alert(numObj.val() + '件更新しました。');
	} else if (alertTypeValue == '3') {
		alert(numObj.val() + '件削除しました。');
	}

}

/**
 * 文字列のバイト数を返却する
 * @param strSrc
 * @returns {Number}
 */
function getBytes(strSrc){
    var len = 0;
    strSrc = escape(strSrc);
    for(i = 0; i < strSrc.length; i++, len++){
        if(strSrc.charAt(i) == "%"){
            if(strSrc.charAt(++i) == "u"){
                i += 3;
                len++;
            }
            i++;
        }
    }
    return len;
}
//function page(startIdx, pageNum, obj, aa) {
//
//	var i;
//	for (i = startIdx; i < pageNum; i++) {
//		var clone = obj.clone(aa);
//		clone.text(i + 1);
//		clone.insertAfter(obj.last());
//	}
//}