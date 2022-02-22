<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<title>業販詳細</title>
	<link rel="stylesheet" href="./css/corporateSaleDetail.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>

	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/shortcut.js"></script>

	<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>
	<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
<!--
【業販売上詳細画面】
ファイル名：corporateSaleDetailButton.jsp
作成日：2016/12/28
作成者：藤原

（画面概要）

業販売上データの新規登録・詳細画面
・登録先の法人は変更不可（遷移前に選択）
・判定相手は、事前に得意先マスタへ登録が必要
・入金管理は手動で行う
・納入先は得意先ごとに複数登録可能。登録がある場合はプルダウンで選択可能

（注意・補足）
・ピッキング済み、出庫済みの判定は伝票単位ではなく商品単位で管理される


-->


<script type="text/javascript">
//コピー用得意先情報
deliveryInfo = [];
var delivery = {
	deliveryNm		: '',
	deliveryNmKana	: '<bean:write name="corporateSaleForm" property="clientDTO.clientNmKana" />',
	tel				: '<bean:write name="corporateSaleForm" property="clientDTO.tel" />',
	fax				: '<bean:write name="corporateSaleForm" property="clientDTO.fax" />',
	zip				: '<bean:write name="corporateSaleForm" property="clientDTO.zip" />',
	prefectures		: '<bean:write name="corporateSaleForm" property="clientDTO.prefectures" />',
	municipality	: '<bean:write name="corporateSaleForm" property="clientDTO.municipality" />',
	address			: '<bean:write name="corporateSaleForm" property="clientDTO.address" />',
	buildingNm		: '<bean:write name="corporateSaleForm" property="clientDTO.buildingNm" />',
	position		: '<bean:write name="corporateSaleForm" property="clientDTO.position" />',
	contactPersonNm	: '<bean:write name="corporateSaleForm" property="clientDTO.contactPersonNm" />',
	quarter			: '<bean:write name="corporateSaleForm" property="clientDTO.quarter" />',
};
deliveryInfo.push(delivery);
<logic:iterate name="corporateSaleForm" property="deliveryList" id="delivery" indexId="idx">
	var delivery = {
		deliveryNm		: '<bean:write name="delivery" property="deliveryNm" />',
		deliveryNmKana	: '<bean:write name="delivery" property="deliveryNmKana" />',
		tel				: '<bean:write name="delivery" property="tel" />',
		fax				: '<bean:write name="delivery" property="fax" />',
		zip				: '<bean:write name="delivery" property="zip" />',
		prefectures		: '<bean:write name="delivery" property="prefectures" />',
		municipality	: '<bean:write name="delivery" property="municipality" />',
		address			: '<bean:write name="delivery" property="address" />',
		buildingNm		: '<bean:write name="delivery" property="buildingNm" />',
		position		: '<bean:write name="delivery" property="position" />',
		contactPersonNm	: '<bean:write name="delivery" property="contactPersonNm" />',
		quarter			: '<bean:write name="delivery" property="quarter" />',
	};
	deliveryInfo.push(delivery);
</logic:iterate>

$(document).ready(function(){
	$("#receivableBalance").text(<bean:write name="corporateSaleForm" property="corporateSalesSlipDTO.sumSalesPrice" /> - <bean:write name="corporateSaleForm" property="corporateSalesSlipDTO.receivePrice" />);
	$(".overlay").css("display", "none");
});

$(window).load(function () {
	//アラート
	if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value = '0';
	}
});

$(function() {
	/*ショートカットキー設定********************************************************/
	shortcut.add("Ctrl+s", function(){
		if (Number($("#sysCorporateSalesSlipId").val()) != 0) {
			$("#update").click();
		} else {
			$("#registry").click();
		}
	});

	shortcut.add("Ctrl+Insert", function(){

		$(".addSalesItem").trigger("click");
	});

	shortcut.add("Alt+Enter", function(){

		if ($(":focus").hasClass("addItemCode") || $(":focus").hasClass("addItemNm")) {
			var index = $("tr.addSalesItemRow").index($(":focus").parents("tr.addSalesItemRow"));
			addSaleItemBefore(index);
		} else {
			$(".addSalesItem").trigger("click");
		}
	});

	if ($("#sysCorporateSalesSlipId").val() != 0) {
		shortcut.add("F2", function(){
			$(".sysCorporationId").val($("#sysCorporationId").val());
			goTransaction("initRegistryCorporateSales.do");
		});
	}

	shortcut.add("Alt+Delete", function(){

		if ($(".addSalesItemRow:visible").length > 1) {
			if ($(':focus').hasClass("addItemCode") || $(':focus').hasClass("addItemNm")) {

				var index = $("tr.addSalesItemRow").index($(":focus").parents("tr.addSalesItemRow"));
				var i = 0;

				for (i = index; i <= salesItemAreaIdx; i++) {
					if (i == $("#salesItemLength").val() - 1) {
						clearAddItemRow(salesItemArea.eq(i));
					} else {
						var copyFromTr = salesItemArea.eq(i + 1);
						var copyToTr = salesItemArea.eq(i);

						copySalesItem(copyFromTr, copyToTr);
					}
				}

				var tr = salesItemArea.eq(salesItemAreaIdx--);
				tr.hide();
			}
		}
	});

	$(".addItemCode").on('keydown', function(e){
		if (( e.keyCode==32 || e.keyCode==13 ) && ( ! event.altKey )) {
			e.preventDefault();
			$(this).parents("tr.addSalesItemRow").find(".searchItem").trigger("click");
		}
	});

	$("#clientNo").on('keydown', function(e){
		if (e.keyCode==32 || e.keyCode==13) {
			e.preventDefault();

			$("#searchClientSysCorporationId").val($("#sysCorporationId").val());
			$("#searchClientClientNm").val("");
			$("#searchClientClientNo").val($("#clientNo").val());

			FwGlobal.submitForm(document.forms[0],"/subWinClientSearch","clientSearchWindow","top=130,left=500,width=780px,height=520px,scrollbars=1;");
		}
	});

	shortcut.add("Ctrl+Delete", function() {
		if ($(':focus').hasClass("addItemNm") || $(':focus').hasClass("addItemCode")) {
			var itemRow = $(':focus').parents(".addSalesItemRow");

			clearAddItemRow(itemRow);
		}
	});
	/*ショートカットキー設定ここまで***********************************************/

	$('.num').spinner( {
		max: 9999,
		min: 0,
		step: 1
	});

	// 初期画面表示時の商品情報入力制御
	 window.onload = function textControl() {

		 for (var i = 0; i < $(".corporateSalesItemRow:visible").length; i++) {
			 var leavingDate = $("input[name='corporateSalesItemList["+ i +"].leavingDate']").val();
			 var salesDate = $("input[name='corporateSalesItemList["+ i +"].leavingDate']").val();

			 if (leavingDate.length > 0 && salesDate.length > 0) {
				if ($("#returnFlg").val() == "0"){
					$("input[name='corporateSalesItemList["+ i +"].orderNum']").attr("disabled","disabled");
					jQuery("input[name='corporateSalesItemList["+ i +"].orderNum']").spinner().spinner( "disable" );
					$("input[name='corporateSalesItemList["+ i +"].pieceRate']").attr("disabled","disabled");
					$("input[name='corporateSalesItemList["+ i +"].cost']").attr("disabled","disabled");
					$("input[name='corporateSalesItemList["+ i +"].scheduledLeavingDate']").attr("disabled","disabled");
					$("input[name='corporateSalesItemList["+ i +"].pickingListFlg']").attr("disabled","disabled");
					$("input[name='corporateSalesItemList["+ i +"].leavingDate']").attr("disabled","disabled");
					$("input[name='corporateSalesItemList["+ i +"].salesDate']").attr("disabled","disabled");
					$("input[name='corporateSalesItemList["+ i +"].scheduledLeavingDate']").datepicker( "option", "disabled", true );
				}
			 }
			//バグデータ対策（暫定的に）
			//出庫日が入っていないが、出庫数が入っている場合出庫数を0にする
			if ($("tr.corporateSalesItemRow").eq(i).find(".leavingDate").val() == "" && $("tr.corporateSalesItemRow").eq(i).find(".hiddenLeavingNum").val() != 0) {

				$("tr.corporateSalesItemRow").eq(i).find(".hiddenLeavingNum").val(0);
				$("tr.corporateSalesItemRow").eq(i).find(".leavingNum").val("0");
				$("tr.corporateSalesItemRow").eq(i).find(".leavingNum").html(0);
			} else {
				$("tr.corporateSalesItemRow").eq(i).find(".leavingNum").html($("tr.corporateSalesItemRow").eq(i).find(".hiddenLeavingNum").val());
			}
		 }
	};


	//小計計算（初期表示用）
	$(".corporateSalesItemRow").each(function () {
		var pieceRate = $(this).find(".pieceRate").val();
		var subtotal = Number(pieceRate.replace(",","")) * $(this).find(".orderNum").val();
		var subtotalWithTax = 0;
		if ($(this).find(".dispItemCode").text() == 77) {
			subtotalWithTax = subtotal;
		} else {
			var taxRate = (100 + Number($("#taxRate").val())) / 100;
			if (subtotal < 0) {
				subtotal = subtotal  * -1;
				subtotalWithTax = Math.floor(subtotal * taxRate);
				subtotalWithTax = subtotalWithTax * -1;
				subtotal = subtotal * -1;
			} else {
				subtotalWithTax = Math.floor(subtotal * taxRate);
			}

		}

		$(this).find(".subtotal").text(subtotal.toLocaleString());
		$(this).find(".subtotalWithTax").text(subtotalWithTax.toLocaleString());
	});

	//金額計算
	slipCalc();

	 //返品伝票でない場合
	 if ($("#returnFlg").val() == "0") {
		//出庫日と売上日からcalenderをとる
		$('.leavingDate').removeClass("calender");
		$('.salesDate').removeClass("calender");
		$('.addLeavingDate').removeClass("calender");
		$('.addSalesDate').removeClass("calender");
	 }

	//カレンダー用クリアボタン
	var showAdditionalButton = function(input){
		setTimeout(function() {
			var buttonPanel = $(input).datepicker("widget").find(".ui-datepicker-buttonpane"),

			btn = $('<button class="ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all" type="button">クリア</button>');
			btn.unbind("click").bind("click", function() {
				$.datepicker._clearDate(input);
			});

			btn.appendTo(buttonPanel);
		}, 1);
	};

	$(".calender").not('#corporateReceiveDate').datepicker({
		showButtonPanel: true,
		beforeShow: showAdditionalButton,
		onChangeMonthYear: showAdditionalButton,
		showOn: "button",
		buttonImage: "./img/calender_icon.png",
		buttonImageOnly: true,
	});

	//入金用カレンダー
	var showAdditionalButtonForCorporateReceive = function(input){
		if ($('#corporateReceiveDate').prop('disabled')) {
			return false;
		}
		setTimeout(function() {
			var buttonPanel = $(input).datepicker("widget").find(".ui-datepicker-buttonpane"),

			btn = $('<button class="ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all" type="button">クリア</button>');
			btn.unbind("click").bind("click", function() {
				$.datepicker._clearDate(input);
			});

			btn.appendTo(buttonPanel);
		}, 1);
	};

	$("#corporateReceiveDate").datepicker({
		showButtonPanel: true,
		beforeShow: showAdditionalButtonForCorporateReceive,
		onChangeMonthYear: showAdditionalButton,
		showOn: "button",
		buttonImage: "./img/calender_icon.png",
		buttonImageOnly: true,
	});

	//売掛、それ以外以外の処理
	if ($("#paymentMethod").val() != "1") {
		$(".receivableBalance").hide();
		$(".sumSalesPrice").hide();
	} else {
		$(".sumClaimPrice").hide();
	}

	//納入先プルダウン初期化
<logic:empty name="corporateSaleForm" property="deliveryList">
	$("#deliverySelect").hide();
</logic:empty>
<logic:notEmpty name="corporateSaleForm" property="deliveryList">
	$("#deliverySelect").show();
	$("#deliverySelect").children().remove();
	$("#deliverySelect").append("<option value='0'></option>");
	for (var i = 1; i < deliveryInfo.length; i++) {
		$("#deliverySelect").append("<option value='" + i + "'>" + deliveryInfo[i]['deliveryNm'] + "</option>");
	}
</logic:notEmpty>

//通貨コードの表示処理
	if($("#currency").val() == "1") {
		$(".currency").text("円");
	} else {
		$(".taxCalc").hide();
		$(".currency").text("＄");
	}

	//見積書初期表示
	if ($(".estimateRemarks").val().length == 0) {
		$(".estimateRemarks").val("有効期限 : 2週間");
	}

	//返品伝票時の表示処理
	if ($("#returnFlg").val() == "1") {
		$(".receive").hide();
		$(".orderNum").unbind("blur");
		$('.orderNum').spinner( {
			max: 9999,
			min: -9999,
			step: 1
		});

		//返品のときは出庫日と売上日を操作できるように
		$('.leavingDate').attr('readonly',false);
		$('.salesDate').attr('readonly',false);
		$('.addLeavingDate').attr('readonly',false);
		$('.addSalesDate').attr('readonly',false);
	}



	var salesItemAreaIdx = 0;
	var salesItemArea = $("tr.addSalesItemRow");
	salesItemArea.hide();

	//返品フラグが立っている場合作品追加部分は非表示
	if ($("#returnFlg").val() == "0") {

		var count = 0;

		if($(".addItemNm").eq(1).val() != 0) {
			for(var i = 1; i < $(".addSysItemId").length; i++) {
				if($(".addItemNm").eq(i).val() != 0) {
					count++;
					$("tr.addSalesItemRow").eq(i).show();
				}
			}
			salesItemAreaIdx = count;
		}
		$("tr.addSalesItemRow").eq(0).show();

		aTag = $("tr.addSalesItemRow").eq(0).find(".button_small_white");
		if (aTag) {
			aTag.hide();
		}
		aTag = "";
		tr = "";

		$("#add_button_area").append("<a href='javascript:void(0);' id='add_button'></a>");
		$("#add_button").attr("class", "button_small_main addSalesItem");
		$("#add_button").text("追加");
	}

	//売上商品追加
	$(".addSalesItem").click(function () {

		if (salesItemAreaIdx + 1 >= $("#salesItemLength").val()) {
			alert("一括で登録できるのは" + $("#salesItemLength").val() + "件までです。");
			return;
		}

		$("tr.addSalesItemRow").eq(++salesItemAreaIdx).show();
		$("tr.addSalesItemRow").eq(salesItemAreaIdx).find(".addItemCode").focus();



	});

	//売上商品追加（1行前に挿入）
	function addSaleItemBefore(rowNum) {
		if (salesItemAreaIdx + 1 >= $("#salesItemLength").val()) {
			alert("一括で登録できるのは" + $("#salesItemLength").val() + "件までです。");
			return;
		}

		$("tr.addSalesItemRow").eq(++salesItemAreaIdx).show();
		for (var i = salesItemAreaIdx; i > rowNum; i--) {

				var copyFromTr = salesItemArea.eq(i - 1);
				var copyToTr = salesItemArea.eq(i);

				copySalesItem(copyFromTr, copyToTr);
		}


		$("tr.addSalesItemRow").eq(rowNum).find(".addItemCode").focus();
	}

	//登録済み売上商品削除
	$(".deleteRegisted").click( function() {
		if (confirm("登録済みの商品を削除します。よろしいですか？\n(変更は伝票を更新するまで反映されません)")) {
			var item = $(this).closest(".corporateSalesItemRow");
			item.find(".deleteFlag").val(1);
			item.find(".itemPieceRate").remove();
			item.find(".itemCost").remove();
			item.hide();
		}
	});

	//売上商品削除
	$(".removeSalesItem").click(function () {

		var salesItemArea = $("tr.addSalesItemRow");
		var i = 0;
		for (i = $("tr.addSalesItemRow").index($(this).parents("tr.addSalesItemRow")); i <= salesItemAreaIdx; i++) {
			if (i == $("#salesItemLength").val() - 1) {
				clearAddItemRow(salesItemArea.eq(i));
			} else {
				var copyFromTr = salesItemArea.eq(i + 1);
				var copyToTr = salesItemArea.eq(i);

				copySalesItem(copyFromTr, copyToTr);
			}
		}

		var tr = salesItemArea.eq(salesItemAreaIdx--);
		tr.hide();
	});

	function copySalesItem(copyFromTr, copyToTr) {

		copyToTr.find(".addSysItemId").val(copyFromTr.find(".addSysItemId").val());
		copyToTr.find(".addItemCode").val(copyFromTr.find(".addItemCode").val());
		copyToTr.find(".addItemNm").val(copyFromTr.find(".addItemNm").val());
		copyToTr.find(".addOrderNum").val(copyFromTr.find(".addOrderNum").val());
		copyToTr.find(".addPieceRate").val(copyFromTr.find(".addPieceRate").val());
		copyToTr.find(".addCost").val(copyFromTr.find(".addCost").val());
		copyToTr.find(".addTotalStockNum").html(copyFromTr.find(".addTotalStockNum").html());
		copyToTr.find(".addTemporaryStockNum").html(copyFromTr.find(".addTemporaryStockNum").html());
		copyToTr.find(".scheduledLeavingDate").val(copyFromTr.find(".scheduledLeavingDate").val());
		copyToTr.find(".addPickingFlg").prop("checked", copyFromTr.find(".addPickingFlg").prop("checked"));
		copyToTr.find(".subtotal").text(copyFromTr.find(".subtotal").text());
		copyToTr.find(".subtotalWithTax").text(copyFromTr.find(".subtotalWithTax").text());

		copyFromTr.find(".addSysItemId").val(0);
		copyFromTr.find(".addItemCode").val("");
		copyFromTr.find(".addItemNm").val("");
		copyFromTr.find(".addOrderNum").val(0);
		copyFromTr.find(".addPieceRate").val(0);
		copyFromTr.find(".addCost").val(0);
		copyFromTr.find(".addTotalStockNum").html("0 ");
		copyFromTr.find(".addTemporaryStockNum").html("(0)");
		copyFromTr.find(".scheduledLeavingDate").val("");
		copyFromTr.find("input[type='checkbox']").prop("checked", false);
		copyFromTr.find(".subtotal").text(0);
		copyFromTr.find(".subtotalWithTax").text(0);
	}

	//検索
	$(".searchItem").click (function () {

		if($("#sysCorporationId").val() == '0') {
			alert("法人を選択してください。");
			return;
		}

		$("#searchItemNm").val("");
		$("#searchItemCode").val("");
		$("#searchOrderNo").val("");
		//伝票番号が存在していれば検索条件として渡す
		if ($("#orderNo").val() != "") {
			$("#searchOrderNo").val($("#orderNo").val());
		}


		var salesItemArea = $("tr.addSalesItemRow");

		var rowNum = $("tr.addSalesItemRow").index($(this).parents("tr.addSalesItemRow"));

		$("#searchItemCode").val(salesItemArea.eq(rowNum).find(".addItemCode").val());
		if ( ! $("#searchItemCode").val()) {
 			$("#searchItemNm").val(salesItemArea.eq(rowNum).find(".addItemNm").val());
		}
		$("#openerIdx").val(rowNum);
		$("#searchSysCorporationId").val($("#sysCorporationId").val());

		if (!$("#searchItemCode").val() && !$("#searchItemNm").val()) {
			FwGlobal.submitForm(document.forms[0],"/initSubWinItem","itemSearchWindow","top=130,left=500,width=780px,height=520px;");
		} else {
			FwGlobal.submitForm(document.forms[0],"/subWinItemSearch","itemSearchWindow","top=130,left=500,width=780px,height=520px;");
		}
	});

	//得意先検索ウィンド表示
	$("#clientSearch").click (function () {

		$("#searchClientSysCorporationId").val($("#sysCorporationId").val());
		$("#searchClientClientNm").val("");
		$("#searchClientClientNo").val($("#clientNo").val());

		FwGlobal.submitForm(document.forms[0],"/subWinClientSearch","clientSearchWindow","top=130,left=500,width=780px,height=520px,scrollbars=1;");
	});

	$('.changeColorCheck').click(function(evt) {

		var $t = $(this);
		var chk = $t.find(':checkbox')[0];
		if (evt.target != chk) {
			chk.checked = !chk.checked;
		}
		if (chk.checked) {
			$t.addClass('checked_tr');
		} else {
			$t.removeClass('checked_tr');
		}
	});

	//通常伝票の場合のみ動的に税率の変更を行う
	if ($("#returnFlg").val() == "0") {
		//税率の設定
		$("#orderDate").blur (function () {

			var orderDate = new Date($("#orderDate").val());
			//8パーセントになる日付
			var taxUpDate8 = new Date($("#taxUpDate8").val());
			//10パーセントになる日付
			var taxUpDate10 = new Date($("#taxUpDate10").val());

			//20140401より小さい場合
			if (orderDate < taxUpDate8 && $("#taxRate").val() != $("#taxRate5").val()) {
				$("#taxRate").val($("#taxRate5").val());
			} else if (taxUpDate8 <= orderDate && orderDate < taxUpDate10 && $("#taxRate").val() != $("#taxRate8").val()) {
				$("#taxRate").val($("#taxRate8").val());
			} else if (taxUpDate10 <= orderDate && $("#taxRate").val() != $("#taxRate10").val()) {
				$("#taxRate").val($("#taxRate10").val());
			} else {
				return;
			}
			alert("注文日変更によって、消費税率が変わりました。");
		});
	}

	//伝票新規登録
	$("#registry").click (function () {

 		if ($("#sysClientId").val() == 0) {
 			alert('得意先コードは必須項目です。');
 			return;
 		}

 		var showTr = $("tr.addSalesItemRow");
 		var updateTr = $("tr.corporateSalesItemRow");

		//返品の場合
		if ($("#returnFlg").val() == "1") {
			for (var i = 0; i < updateTr.size(); i++) {

				var leavingDate = updateTr.eq(i).find(".leavingDate").val();
				var salesDate = updateTr.eq(i).find(".salesDate").val();

		 		if (!leavingDate) {
		 			alert('出庫日は必須項目です。');
		 			return;
		 		}

		 		if (!salesDate) {
		 			alert('売上日は必須項目です。');
		 			return;
		 		}

		 		if (leavingDate != salesDate) {
		 			alert('出庫日と売上日は同日でないと登録できません。');
		 			return;
		 		}
			}
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction("returnCorporateSales.do");
			return;
		}

		for (var i = 0; i <= salesItemAreaIdx; i++) {

			var addPieceRate = new Number(showTr.eq(i).find(".addPieceRate").val());
			var addOrderNum = new Number(showTr.eq(i).find(".addOrderNum").val());
			var addItemNm = showTr.eq(i).find(".addItemNm").val();

			if (addItemNm != "" || addPieceRate != 0 || addOrderNum != 0) {

			if (addItemNm == "" || addOrderNum == 0) {

					alert('商品名・注文数・単価は必須項目です。');
					return;
				}
			}
		}

		//配送情報が選択されていない場合、ユーザに確認を行う。
		if ($('#transportCorporationSystem').val() == "") {
			var msg = "配送情報が選択されていません\n本当によろしいですか?";

			if (!confirm(msg)) {
				return;
			}
		}

		//}

		//金額、税計算
		slipCalc();

		//入金額更新
		$("#receivePrice").val($("#corporateReceivePrice").val());

		//入金額をチェック
		if (!checkCorporateReceive()){ return; };

		//代引きの場合
		if ($("#paymentMethod").val() == 2) {
			var commission = 0;
			$(".dispItemCode").each (function () {
				if ($(this).text() == "88") {
					commission = 1;
					return false;
				}
			});

			$(".addSalesItemRow").each (function () {
				if (commission != 0) { return false; }
				if ($(this).find(".addItemCode").val() == "88" && $(this).find(".addPieceRate").val() > 0) {
					commission = 1;
					return false;
				}
			});

			var msg = "";

			if (commission == 0) {
				msg += "「88:代引き手数料」が登録されていません。\n";
			}

			var receivePrice = Number($("#dispReceivePrice").text()) + Number($("#corporateReceivePrice").val()) ;
			if(receivePrice < $(".receive").find(".dispSumClaimPrice").text()) {
				msg += "入金登録がされていないか、不足があります。\n";
			}

			msg += "登録します。よろしいですか？";
			if (!confirm(msg)) {
				return;
			}
		} else if (!confirm("登録します。よろしいですか？")) {
			return;
		}
		removeCommaList($(".priceTextMinus"));
		removeCommaGoTransaction('registryCorporateSalesButton.do');
	});

	//伝票更新
	$("#update").click (function () {

		var showTr = $("tr.addSalesItemRow");
		var updateTr = $("tr.corporateSalesItemRow");

		//返品の場合
		if ($("#returnFlg").val() == "1") {
			for (var i = 0; i < updateTr.size(); i++) {

				var leavingDate = updateTr.eq(i).find(".leavingDate").val();
				var salesDate = updateTr.eq(i).find(".salesDate").val();

		 		if (!leavingDate) {
		 			alert('出庫日は必須項目です。');
		 			return;
		 		}

		 		if (!salesDate) {
		 			alert('売上日は必須項目です。');
		 			return;
		 		}

		 		if (leavingDate != salesDate) {
		 			alert('出庫日と売上日は同日でないと更新できません。');
		 			return;
		 		}
			}
		}

		//登録済み商品の必須項目制御
		for (var i = 0; i < $(".corporateSalesItemRow:visible").length; i++) {

			var pieceRate = new Number(showTr.eq(i).find(".pieceRate").val());
			var orderNum = new Number(showTr.eq(i).find(".orderNum").val());
			var itemNm = showTr.eq(i).find(".itemNm").val();

			if (itemNm != "" || pieceRate != 0 || orderNum != 0) {

				if (itemNm == "" || orderNum == 0) {

					alert('商品名・注文数・単価は必須項目です。');
					return;
				}
			}
		}

		//追加商品の必須項目制御
		var i;
		var addShowTr = $("tr.addSalesItemRow");

		for (i = 0; i <= salesItemAreaIdx; i++) {

			var addPieceRate = new Number(addShowTr.eq(i).find(".addPieceRate").val());
			var addOrderNum = new Number(addShowTr.eq(i).find(".addOrderNum").val());
			var addItemNm = addShowTr.eq(i).find(".addItemNm").val();

			if (addItemNm != "" || addPieceRate != 0 || addOrderNum != 0) {

				if (addItemNm == "" || addOrderNum == 0) {

					alert('商品名・注文数・単価は必須項目です。');
					return;
				}
			}
		}

		//配送情報が選択されていない場合、ユーザに確認を行う。
		if ($('#transportCorporationSystem').val() == "") {
			var msg = "配送情報が選択されていません\n本当によろしいですか?";

			if (!confirm(msg)) {
				return;
			}
		}

		//登録済み商品削除後で、商品情報無しの場合処理終了--start
		var deleteFlgCnt = 0;
		//表示用商品情報がない場合
		if ($(".corporateSalesItemRow:visible").length == 0) {
			//表示用商品情報の「削除フラグ：1」をカウント
			$(".corporateSalesItemRow").each( function () {
				if ($(this).find(".deleteFlag").val() == "1") {
					deleteFlgCnt++;
				}
			});

			//「削除フラグ：1」が存在し、追加商品情報がない場合処理終了
			if (deleteFlgCnt > 0) {
				var addShowTr = $("tr.addSalesItemRow");

				for (var i = 0; i <= salesItemAreaIdx; i++) {
					var addPieceRate = new Number(addShowTr.eq(i).find(".addPieceRate").val());
					var addOrderNum = new Number(addShowTr.eq(i).find(".addOrderNum").val());
					var addItemNm = addShowTr.eq(i).find(".addItemNm").val();

					if (addItemNm == "" || addPieceRate == 0 || addOrderNum == 0) {
						alert('「登録済み商品」全件削除後は、追加商品を登録しなければいけません。');
						return;
					}
				}
			}
		}
		//登録済み商品削除後で、商品情報無しの場合処理終了--end

		//金額、税計算
		slipCalc();

		//入金額更新
		var receive= Number($("#receivePrice").val()) + Number($("#corporateReceivePrice").val());
		$("#receivePrice").val(receive);

		//入金額をチェック
		if (!checkCorporateReceive()){ return; };

		//代引きの場合
		if ($("#paymentMethod").val() == 2) {
			var commission = 0;
			$(".dispItemCode").each (function () {
				if ($(this).text() == "88") {
					commission = 1;
					return false;
				}
			});

			$(".addSalesItemRow").each (function () {
				if (commission != 0) { return false; }
				if ($(this).find(".addItemCode").val() == "88" && $(this).find(".addPieceRate").val() > 0) {
					commission = 1;
					return false;
				}
			});

			var msg = "";

			if (commission == 0) {
				msg += "「88:代引き手数料」が登録されていません。\n";
			}

			var receivePrice = Number($("#dispReceivePrice").text()) + Number($("#corporateReceivePrice").val()) ;
			if(receivePrice < $(".receive").find(".dispSumClaimPrice").text()) {
				msg += "入金登録がされていないか、不足があります。\n";
			}

			msg += "登録します。よろしいですか？";
			if (!confirm(msg)) {
				return;
			}
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction('updateDetailCorporateSaleButton.do');
		} else{
			if (!confirm("登録します。よろしいですか？")) {
				return;
			}

			 for (var i = 0; i < $(".corporateSalesItemRow:visible").length; i++) {

				//バグデータ対策（暫定的に）
				//出庫日が入っていないが、出庫数が入っている場合出庫数を0にする
				if ($("tr.corporateSalesItemRow").eq(i).find(".leavingDate").val() == "" && $("tr.corporateSalesItemRow").eq(i).find(".leavingNum").html() != 0) {

					$("tr.corporateSalesItemRow").eq(i).find(".hiddenLeavingNum").val(0);
					$("tr.corporateSalesItemRow").eq(i).find(".hiddenLeavingNum").text(0);
					$("tr.corporateSalesItemRow").eq(i).find(".leavingNum").val(0);
					$("tr.corporateSalesItemRow").eq(i).find(".leavingNum").html(0);
				}
			 }
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction('updateDetailCorporateSaleButton.do');
		}

	});

	//新規伝票作成
	$("#newSlip").click(function(){
		$(".sysCorporationId").val($("#sysCorporationId").val());
		goTransaction("initRegistryCorporateSalesButton.do");
	});

	//税計算
	$(".taxCalc").click (function () {

		removeCommaList($(".priceTextMinus"));
		var sumPieceRate = new Number(getSumPieceRate());
		var sumCost = new Number(getSumCost());
		//商品の値段の合計を算出（小計）
		$("#sumPieceRate").val(sumPieceRate);
		$("#dispSumPieceRate").text(sumPieceRate);

		//消費税計算
		var tax = new Number(0);
		tax = taxCalc(new Number($("#taxRate").val()));
		$("#tax").val(tax);
		$("#dispTax").text(tax);

		//小計、消費税、代引き手数料、送料の合計を計算（合計）
		$("#sumClaimPrice").val(getSumClaimPrice());
		$(".dispSumClaimPrice").text(getSumClaimPrice());

		//粗利を計算
		$("#grossMargin").val(sumPieceRate - sumCost);
		$("#dispGrossMargin").text(sumPieceRate - sumCost);
	});

	$("#returnSlip").click (function () {
		var check = 0;
		$(".corporateSalesItemRow").each(function(i){
			if($(this).find('.changeColorCheck').prop('checked')){
				check = 1;
				return false;
			} else {
				return true;
			}
		});
		if(check == 0){
			alert("返品する商品を選択してください。");
			return false;
		}

		removeCommaList($(".priceTextMinus"));
		removeCommaGoTransaction("initReturnCorporateSales.do");
	});

	$(".copySlip").click (function () {

		removeCommaList($(".priceTextMinus"));
		removeCommaGoTransaction('initCopyCorporateSalesSlip.do');
	});

	//配送先情報コピー
	$("#copy").click (function () {
		$("#deliverySelect").val(0);

		$(".destinationNm").val($("#clientNm").text());
		$(".destinationNmKana").val(deliveryInfo[0]['deliveryNmKana']);
		$(".destinationTel").val(deliveryInfo[0]['tel']);
		$(".destinationFax").val(deliveryInfo[0]['fax']);
		$(".destinationZip").val(deliveryInfo[0]['zip']);
		$(".destinationPrefectures").val(deliveryInfo[0]['prefectures']);
		$(".destinationMunicipality").val(deliveryInfo[0]['municipality']);
		$(".destinationAddress").val(deliveryInfo[0]['address']);
		$(".destinationBuildingNm").val(deliveryInfo[0]['buildingNm']);
		$(".destinationQuarter").val(deliveryInfo[0]['quarter']);
		$(".destinationPosition").val(deliveryInfo[0]['position']);
		$(".destinationContactPersonNm").val(deliveryInfo[0]['contactPersonNm']);
	});

	//伝票削除
	$("#delete").click (function () {

		if (confirm("伝票を削除します。よろしいですか？")) {
			removeCommaGoTransaction("deleteCorporateSales.do");
		}
		return;
	});

	//出庫のチェックに応じて出荷日を設定
	$("#leavingCheck").change(function () {
		if ($("#leavingCheck").prop("checked")) {
			var date = new Date();
			var yyyy = date.getFullYear();
			var MM = ("0" + (date.getMonth() + 1)).slice(-2);
			var dd = ("0" + (date.getDate())).slice(-2);
			$("#shipmentDate").val(yyyy + "/" + MM + "/" + dd);
		} else {
			$("#shipmentDate").val("");
		}
	});


	//通貨コード切り替え
	$("#currency").change(function (){
		if($(this).val() == "1") {

			$(".currency").text("円");
			$(".taxCalc").show();

			slipCalc();
		} else {

			$(".currency").text("＄");
			$(".taxCalc").hide();

			slipCalc();
		}
	});

	//配送先選択処理
	$("#deliverySelect").change(function(){
		if ($(this).val() != 0) {
			$(".destinationNm").val(deliveryInfo[$(this).val()]['deliveryNm']);
			$(".destinationNmKana").val(deliveryInfo[$(this).val()]['deliveryNmKana']);
			$(".destinationTel").val(deliveryInfo[$(this).val()]['tel']);
			$(".destinationFax").val(deliveryInfo[$(this).val()]['fax']);
			$(".destinationZip").val(deliveryInfo[$(this).val()]['zip']);
			$(".destinationPrefectures").val(deliveryInfo[$(this).val()]['prefectures']);
			$(".destinationMunicipality").val(deliveryInfo[$(this).val()]['municipality']);
			$(".destinationAddress").val(deliveryInfo[$(this).val()]['address']);
			$(".destinationBuildingNm").val(deliveryInfo[$(this).val()]['buildingNm']);
			$(".destinationQuarter").val(deliveryInfo[$(this).val()]['quarter']);
			$(".destinationPosition").val(deliveryInfo[$(this).val()]['position']);
			$(".destinationContactPersonNm").val(deliveryInfo[$(this).val()]['contactPersonNm']);
		}
	});

	//業販入金
	//初期状態は無効
	$("#corporateReceiveDate").prop("disabled", true);
	$("#corporateReceivePrice").prop("disabled", true);

	$("#corporateReceive").click(function(){
		if($("#corporateReceive").hasClass("in")) {
			$("#corporateReceiveDate").prop("disabled", false);
			$("#corporateReceivePrice").prop("disabled", false);
			var today = new Date();
			var year = today.getFullYear();
			var month = today.getMonth() + 1;
			var aday = today.getDate();

			var receiveDate = year + "/" + ("0" + month).substr(-2) + "/" + ("0" + aday).substr(-2);
			$("#corporateReceiveDate").val(receiveDate);
			if ($("#paymentMethod").val() == "1") {
				$("#corporateReceivePrice").val(Number($("#receivableBalance").text()));
			} else {
				$("#corporateReceivePrice").val(Number($(".dispSumClaimPrice").eq(0).text()));
			}
			$("#corporateReceive").removeClass("in");
			$("#corporateReceive").text("戻す");
		} else {
			$("#corporateReceiveDate").prop("disabled", true);
			$("#corporateReceiveDate").val("");
			$("#corporateReceivePrice").prop("disabled", true);
			$("#corporateReceivePrice").val("");
			$("#corporateReceive").addClass("in");
			$("#corporateReceive").text("入金");
		}
	});

	//業販入金削除
	$(document).on("click",".delReceive",function(){
		if (!confirm("入金情報を削除します。よろしいですか？\n（変更は伝票情報を更新するまで反映されません。）")) {
			return;
		}
		var row = $(this).parents("tr");
		var delPrice = Number(row.find(".receivePrice").text());
		row.find(".deleteFlag").val("1");
		row.hide();

		var receivableBalance = Number($("#receivableBalance").text());
		var dispReceivePrice = Number($("#dispReceivePrice").text());

		$("#receivableBalance").text(receivableBalance + delPrice);
		$("#dispReceivePrice").text(dispReceivePrice - delPrice);
		$("#receivePrice").val(dispReceivePrice - delPrice);
	});


	//支払方法切り替え時の処理
	//支払方法、伝票種別切り替え時相互に値を変更
	$("#paymentMethod").change(function(){
		if ($("#paymentMethod").val() == "1") {
			$(".receivableBalance").show();
			$(".sumSalesPrice").show();
			$(".sumClaimPrice").hide();
		} else {
			$(".receivableBalance").hide();
			$(".sumSalesPrice").hide();
			$(".sumClaimPrice").show();
		}

		if ($("#paymentMethod").val() == "2") {
			$("#invoiceClassificationB2").val("コレクト（代引き）");
			$("#invoiceClassificationEhiden").val("コレクト（代引き）");
		} else if ($("#paymentMethod").val() == "3") {
			$("#invoiceClassificationB2").val("発払い（元払い）");
			$("#invoiceClassificationEhiden").val("発払い（元払い）");
		}
	});

	//伝票種別切り替え時の処理 クロネコ
	$("#invoiceClassificationB2").change( function () {

		var val = $(this).val();


		var invoiceMap = {
				'コレクト（代引き）' : '2',
				'発払い（元払い）' : '3'
				};

		if (invoiceMap[val] != $("#paymentMethod").val()) {
			var msg = "支払方法と送り状種別の組み合わせ一致しておりません。\n";
			msg += "ご確認した上、登録・更新を行ってください。";
			alert(msg);
		}



	});

	//伝票種別切り替え時の処理 佐川
	$("#invoiceClassificationEhiden").change( function () {

		var val = $(this).val();
		var invoiceMap = {
				'コレクト（代引き）' : '2',
				'発払い（元払い）' : '3'
				};

		if (invoiceMap[val] != $("#paymentMethod").val()) {
			var msg = "支払方法と送り状種別の組み合わせ一致しておりません。\n";
			msg += "ご確認した上、登録・更新を行ってください。";
			alert(msg);
		}
	});



	$("#slipStatus").change(function(){
		if ($("#slipStatus").val() == "2") {
			var today = new Date();
			var year = today.getFullYear();
			var month = today.getMonth() + 1;
			var aday = today.getDate();

			var orderDate = year + "/" + ("0" + month).substr(-2) + "/" + ("0" + aday).substr(-2);
			$("#orderDate").val(orderDate);
		}
	});

	//出庫予定日一括設定
	$("#scheduledLeavingDateAll").change(function(){

		 $(".scheduledLeavingDate").each(function () {
			 if(! $(this).prop("disabled") ){
				$(this).val($("#scheduledLeavingDateAll").val());
			 }
		 });

	});

	$("#deliveryJson").change(function(){
		deliveryInfo = JSON.parse($("#deliveryJson").val());
		$("#deliveryJson").val("");
	});

	//単価、個数が変更されたときに小計を再計算
	$(".addPieceRate, .addOrderNum, .pieceRate, .orderNum").change(function () {
		var itemRow = "";
		var subtotal = 0;
		var subtotalWithTax = 0;

		if ($(this).parents(".addSalesItemRow").length != 0) {
			itemRow = $(this).parents(".addSalesItemRow");
			var pieceRate = itemRow.find(".addPieceRate").val();
			subtotal = Number(pieceRate.replace(",","")) * itemRow.find(".addOrderNum").val();
			//77の値引きのみ、税計算は行わない
			if (itemRow.find(".addItemCode").val() == 77) {
				subtotalWithTax = subtotal;
			} else {
				var taxRate = (100 + Number($("#taxRate").val())) / 100;
				subtotalWithTax = Math.floor(subtotal * taxRate);
			}
		} else if ($(this).parents(".corporateSalesItemRow").length != 0) {
			itemRow = $(this).parents(".corporateSalesItemRow");
			var pieceRate = itemRow.find(".pieceRate").val();
			subtotal = Number(pieceRate.replace(",","")) * itemRow.find(".orderNum").val();
			//77の値引きのみ、税計算は行わない
			if (itemRow.find(".dispItemCode").text() == 77) {
				subtotalWithTax = subtotal;
			} else {
				var taxRate = (100 + Number($("#taxRate").val())) / 100;
				subtotalWithTax = Math.floor(subtotal * taxRate);
			}
		} else {
			return;
		}

		itemRow.find(".subtotal").text(subtotal.toLocaleString());
		itemRow.find(".subtotalWithTax").text(subtotalWithTax.toLocaleString());

	});

	//スピナークリック時にchangeイベント発火
	$(document).on("click", ".ui-spinner-button", function() {
		if ($(this).parents(".addSalesItemRow").length != 0) {
			$(this).parents(".addSalesItemRow").find(".addOrderNum").trigger("change");
		} else {
			$(this).parents(".corporateSalesItemRow").find(".orderNum").trigger("change");
		}
	});

	//配送情報入力項目切り替え
	$("#transportCorporationSystem").change(function () {
		if ($("#transportCorporationSystem").val() == "ヤマト運輸") {

			$(".ehiden").hide();
			$(".seino").hide();

			$("#destinationAppointTimeEhiden").prop("disabled", true);
			$("#invoiceClassificationEhiden").prop("disabled", true);
			$("#destinationAppointTimeSeino").prop("disabled", true);
			$("#invoiceClassificationSeino").prop("disabled", true);

			$("#destinationAppointTimeB2").prop("disabled", false);
			$("#invoiceClassificationB2").prop("disabled", false);

			$(".b2").show();

		} else if ($("#transportCorporationSystem").val() == "佐川急便") {

			$(".b2").hide();
			$(".seino").hide();

			$("#destinationAppointTimeB2").prop("disabled", true);
			$("#invoiceClassificationB2").prop("disabled", true);
			$("#destinationAppointTimeSeino").prop("disabled", true);
			$("#invoiceClassificationSeino").prop("disabled", true);

			$("#destinationAppointTimeEhiden").prop("disabled", false);
			$("#invoiceClassificationEhiden").prop("disabled", false);

			$(".ehiden").show();

		} else if ($("#transportCorporationSystem").val() == "西濃運輸") {

			$(".ehiden").hide();
			$(".b2").hide();

			$("#destinationAppointTimeB2").prop("disabled", true);
			$("#invoiceClassificationB2").prop("disabled", true);
			$("#destinationAppointTimeEhiden").prop("disabled", true);
			$("#invoiceClassificationEhiden").prop("disabled", true);

			$("#destinationAppointTimeSeino").prop("disabled", false);
			$("#invoiceClassificationSeino").prop("disabled", false);

			$(".seino").show();

		} else {

			$(".b2").hide();
			$(".ehiden").hide();
			$(".seino").hide();

			$(".destinationAppointDate").val("");
			$("#invoiceClassificationEhiden").val("");
			$("#invoiceClassificationB2").val("");
			$("#invoiceClassificationSeino").val("");

			$("#destinationAppointTimeB2").prop("disabled", true);
			$("#destinationAppointTimeEhiden").prop("disabled", true);
			$("#destinationAppointTimeSeino").prop("disabled", true);
		}
	});

	$("#transportCorporationSystem").trigger("change");
});

function slipCalc() {
	removeCommaList($(".priceTextMinus"));
	var sumPieceRate = new Number(getSumPieceRate());
	var sumCost = new Number(getSumCost());
	//商品の値段の合計を算出（小計）
	$("#sumPieceRate").val(sumPieceRate);
	$("#dispSumPieceRate").text(sumPieceRate);

	//消費税計算
	var tax = new Number(0);

	//通貨が円の場合のみ税計算
	if($("#currency").val() == "1") {
		tax = taxCalc(new Number($("#taxRate").val()));
	}
	$("#tax").val(tax);
	$("#dispTax").text(tax);

	//小計、消費税、代引き手数料、送料の合計を計算（合計）
	$("#sumClaimPrice").val(getSumClaimPrice());
	$(".dispSumClaimPrice").text(getSumClaimPrice());

	//粗利を計算
	$("#grossMargin").val(sumPieceRate - sumCost);
	$("#dispGrossMargin").text(sumPieceRate - sumCost);

}

//商品の価格の合計を返却
function getSumPieceRate () {

	var sum = new Number(0);
	$(".pieceRate").each (function () {

		sum += Number($(this).val()) * Number($(this).parents("tr").find(".orderNum").val());
	});
	$(".addPieceRate").each (function () {

		sum += Number($(this).val()) * Number($(this).parents("tr").find(".addOrderNum").val());
	});
	return sum;
}

//商品の原価の合計を返却
function getSumCost () {

	var sum = new Number(0);
	$(".cost").each (function () {

		sum += Number($(this).val()) * Number($(this).parents("tr").find(".orderNum").val());
	});
	$(".addCost").each (function () {

		sum += Number($(this).val()) * Number($(this).parents("tr").find(".addOrderNum").val());
	});
	return sum;
}


function getSumClaimPrice () {

	var sumClaimPrice = new Number(0);
	sumClaimPrice += Number($("#dispSumPieceRate").text());


	//業販は外税のみ
	sumClaimPrice += Number($("#dispTax").text());

	return sumClaimPrice;
}

function taxCalc(taxRate) {

	//商品コード77の商品を除く全商品の合計を出し、税を計算する
	var sumPieceRate = 0;
	$(".addSalesItemRow").each( function () {

		// Backlog No. BONCRE-527 対応
		// 削除フラグが立っている場合
		//   税計算対象外の為continueします
		if ($(this).find(".deleteFlag").val() == "1") { return true; }

		if ($(this).find(".addItemCode").val() != 77) {
			sumPieceRate += new Number($(this).find(".addPieceRate").val()) * $(this).find(".addOrderNum").val();
		}
	});

	$(".corporateSalesItemRow").each( function () {

		// Backlog No. BONCRE-527 対応
		// 削除フラグが立っている場合
		//   税計算対象外の為continueします
		if ($(this).find(".deleteFlag").val() == "1") { return true; }

		if ($(this).find(".dispItemCode").text() != 77) {
			sumPieceRate += new Number($(this).find(".pieceRate").val()) * $(this).find(".orderNum").val();
		}
	});


	if (sumPieceRate == 0) {
		return 0;
	}

	var tax = new Number(0);
	//CorporateSaleService.javaのgetTaxにも同じロジックがあり
	//ここの税計算ロジックを変更した場合、同じ仕様に変更する必要あり
	//業販は外税のみ
	//税端数は全て切り捨て

	if (sumPieceRate < 0) {
		sumPieceRate = sumPieceRate * -1;
		tax = Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
		tax = tax * -1;
	} else {
		tax = Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
	}


	return tax;
}




function PxTextareaAdjuster(textarea){
	textarea.style.overflow='hidden';
	textarea.style.height = 13 +'px';
	if( textarea.scrollHeight  > textarea.offsetHeight ){
		textarea.style.height = textarea.scrollHeight -2 +'px';
    }
}

function goExportEstimate(){

	if (!confirm("見積書を出力します。\nよろしいですか？")) {
		return;
	}

	$(".overlay").css("display", "block");
	$.ajax({
		url: "./exportCorporateEstimate.do",
		type: 'POST',
		data : {
			'tax' : $("#tax").val(),
		},
		success: function(data, text_status, xhr){
			$("#estimateOutputFlag").val('1');
			$(".overlay").css("display", "none");
 			goTransaction("detailCorporateSale.do");
		},
	 	error: function(data, text_status, xhr){
	 		$(".overlay").css("display", "none");
	 		alert("pdfファイルの作成に失敗しました。");
	 	}
	});
}

function goExportOrderAcceptance(){

	if (!confirm("注文請書を出力します。\nよろしいですか？")) {
		return;
	}

	$(".overlay").css("display", "block");
	$.ajax({
		url: "./exportCorporateOrderAcceptance.do",
		type: 'POST',
		data : {
			'tax' : $("#tax").val(),
		},
		success: function(data, text_status, xhr){
			$("#orderAcceptanceOutputFlag").val('1');
			$(".overlay").css("display", "none");
 			goTransaction("detailCorporateSale.do");
		},
	 	error: function(data, text_status, xhr){
	 		$(".overlay").css("display", "none");
	 		alert("pdfファイルの作成に失敗しました。");
	 	}
	});
}

//入金額が未納額を上回っていた場合、アラート表示
function checkCorporateReceive() {
	var corporateReceive = Number($("#corporateReceivePrice").val());
	var receivableBalance = Number($("#receivableBalance").text());
	var sumClaimPrice = Number($(".dispSumClaimPrice").eq(0).text());

	if ($("#paymentMethod").val() != "1") {
		if(sumClaimPrice < corporateReceive) {
			alert("入金額が合計金額を超えています。");
			$("#corporateReceivePrice").val(0);
			return false;
		}
	} else {
		if(receivableBalance < corporateReceive) {
			alert("入金額が未納額を超えています。");
			$("#corporateReceivePrice").val(0);
			return false;
		}
	}

	//ついでに入金額が0より小さい場合もアラート
	if(corporateReceive < 0) {
		alert("入金額が不正です。");
		$("#corporateReceivePrice").val(0);
		return false;
	}

	return true;
}

//商品追加セルを初期化
function clearAddItemRow(itemRow){
	itemRow.find(".addSysItemId").val(0);
	itemRow.find(".addItemCode").val("");
	itemRow.find(".addItemNm").val("");
	itemRow.find(".addTotalStockNum").text("0 ");
	itemRow.find(".addTemporaryStockNum").text("(0)");
	itemRow.find(".addOrderNum").val(0);
	itemRow.find(".addPieceRate").val(0);
	itemRow.find(".addCost").val(0);
	itemRow.find(".scheduledLeavingDate").val("");
	itemRow.find("input[type='checkbox']").prop("checked", false);
	itemRow.find(".subtotal").text(0);
	itemRow.find(".subtotalWithTax").text(0);
}


document.addEventListener("readystatechange", function(){

	var date = new Date();
	var yyyy = date.getFullYear();
	var MM = ("0" + (date.getMonth() + 1)).slice(-2);
	var dd = ("0" + (date.getDate())).slice(-2);

	if (document.readyState == 'complete' && $("#estimateOutputFlag").val() == '1') {
		$("#estimateOutputFlag").val('0');
		$(".overlay").css("display", "none");
		$("#estimateDate").val(yyyy + "/" + MM + "/" + dd);
		window.open('corporateEstimatePrintOutFile.do','_new');
		goTransaction("detailCorporateSale.do");
	}

	if (document.readyState == 'complete' && $("#orderAcceptanceOutputFlag").val() == '1') {
		$("#orderAcceptanceOutputFlag").val('0');
		$(".overlay").css("display", "none");
		$("#orderDate").val(yyyy + "/" + MM + "/" + dd);
		window.open('corporateOrderAcceptancePrintOutFile.do','_new');
		goTransaction("detailCorporateSale.do");
	}
}, false);

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailCorporateSale" styleClass="saleForm">
	<nested:hidden property="estimateOutputFlag" styleId="estimateOutputFlag" />
	<nested:hidden property="orderAcceptanceOutputFlag" styleId="orderAcceptanceOutputFlag" />
	<input type="hidden" id="deliveryJson" />
<!-- 	<h4 class="heading">業務販売詳細</h4> -->
	<nested:equal value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
		<nested:equal value="0" property="corporateSalesSlipDTO.returnFlg">
		<h4 class="heading">業販売上登録</h4>
		</nested:equal>
		<nested:equal value="1" property="corporateSalesSlipDTO.returnFlg">
		<h4 class="heading" style="color:red; ">業販売上返品</h4>
		</nested:equal>
	</nested:equal>
	<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
		<nested:equal value="0" property="corporateSalesSlipDTO.returnFlg">
		<h4 class="heading">業務販売詳細</h4>
		</nested:equal>
		<nested:equal value="1" property="corporateSalesSlipDTO.returnFlg">
		<h4 class="heading" style="color:red; ">業販売上返品</h4>
		</nested:equal>
	</nested:notEqual>


<!-- サブウィンド商品検索用 -->
	<nested:nest property="searchItemDTO">
		<nested:hidden property="itemCode" styleId="searchItemCode" />
		<nested:hidden property="itemNm" styleId="searchItemNm" />
		<nested:hidden property="itemCodeTo" />
		<nested:hidden property="openerIdx" styleId="openerIdx" />
		<nested:hidden property="orderNo" styleId="searchOrderNo"/>
		<nested:hidden property="sysCorporationId" styleId="searchSysCorporationId" />
	</nested:nest>

<!-- サブウィンド得意先検索用 -->
	<nested:nest property="clientSearchDTO">
		<nested:hidden property="sysCorporationId" styleId="searchClientSysCorporationId"/>
		<nested:hidden property="clientNm" styleId="searchClientClientNm"/>
		<nested:hidden property="clientNo" styleId="searchClientClientNo"/>
	</nested:nest>

<!-- 商品の最大追加数 -->
	<html:hidden property="salesItemLength" styleId="salesItemLength" />
<!-- 	税率系判定用 -->
	<html:hidden property="taxRate5" styleId="taxRate5" />
	<html:hidden property="taxRate8" styleId="taxRate8" />
	<html:hidden property="taxUpDate8" styleId="taxUpDate8" />
	<html:hidden property="taxRate10" styleId="taxRate10" />
	<html:hidden property="taxUpDate10" styleId="taxUpDate10" />
	<html:hidden property="alertType" styleId="alertType"></html:hidden>
<!-- 連続新規登録用 -->
	<nested:hidden property="sysCorporationId" styleClass="clientCorporationId" />

	<nested:nest property="corporateSalesSlipDTO">
		<nested:hidden property="sysCorporateSalesSlipId" styleId="sysCorporateSalesSlipId" />
		<nested:hidden property="taxRate" styleId="taxRate"/>
		<nested:hidden property="returnFlg" styleId="returnFlg" />

		<fieldset class="corporation_area">
			<nested:hidden property="orderNo" styleId="orderNo" />
			<table>
			<nested:notEqual property="sysCorporateSalesSlipId" value="0">
				<tr>
					<td>伝票番号</td>
					<td><nested:write property="orderNo" /></td>
				</tr>
			</nested:notEqual>
				<tr>
					<td>ステータス</td>
					<td colspan="2"><nested:select property="slipStatus" styleId="slipStatus">
							<html:option value="1">見積</html:option>
							<html:option value="2">受注</html:option>
					</nested:select></td>
				</tr>
				<tr>
					<td>法人</td>
					<td colspan="2">
						<input type="hidden" id="sysCorporationId" name="corporateSalesSlipDTO.sysCorporationId" value="<bean:write name="corporateSaleForm" property="corporationDTO.sysCorporationId" />" />
						<bean:write name="corporateSaleForm" property="corporationDTO.corporationNm" />
					</td>
				</tr>
				<tr>
					<td>担当者</td>
					<td colspan="2">
						<nested:text property="personInCharge" styleClass="ime_on" />
					</td>
				</tr>
				<tr>
					<td>無効にする</td>
					<td colspan="2"><nested:checkbox property="invalidFlag" /></td>
				</tr>
			</table>
		</fieldset>
			<fieldset class="updateinfo_area">
				<legend>最終更新情報</legend>
				<table style="margin-left: 110px;">
					<tr>
						<td>
							<bean:write name="corporateSaleForm" property="mstUserDTO.userFamilyNmKanji"/>
							<bean:write name="corporateSaleForm" property="mstUserDTO.userFirstNmKanji"/>
							<label>　　</label>
							<nested:write property="updateDate" format="yyyy/MM/dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
			</fieldset>

	<div class="leftArea">
		<fieldset class="slip_area">
		<legend>販売情報</legend>
			<table>
				<tr>
					<td>見積日</td>
					<td class="nw" colspan="2"><nested:text property="estimateDate" styleClass="calender ime_off" maxlength="10" styleId="estimateDate" /></td>
				</tr>
				<tr>
					<td>受注日</td>
					<td class="nw" colspan="2"><nested:text property="orderDate" styleClass="calender ime_off" maxlength="10" styleId="orderDate" /></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td>売上日</td> -->
<%-- 					<td style="white-space:nowrap;" colspan="2"><nested:text property="salesDate" styleClass="calender" maxlength="10" /></td> --%>
<!-- 				</tr> -->
				<tr>
					<td>請求日</td>
					<td class="nw" colspan="2"><nested:text property="billingDate" styleClass="calender ime_off" maxlength="10" /></td>
				</tr>
				<tr>
					<td colspan="4"><hr class="line"></td>
				</tr>
				<tr>
					<td>得意先番号</td>
					<td colspan="2">
						<label class="necessary">※</label>&nbsp;
						<html:text name="corporateSaleForm" property="clientDTO.clientNo" styleId="clientNo" styleClass="text_w150 ime_off" />
						<nested:hidden property="sysClientId" styleId="sysClientId" />
					</td>
					<td><a class="button_main" href="javascript:void(0);" id="clientSearch">検索</a></td>
<!-- 					<td><a class="button_small_main" href="javascript:void(0);" id="newClient">新規</a></td> -->
				</tr>
				<tr>
					<td>得意先名</td>
					<td colspan="3">
						<span class="text_w150" id="clientNm"><nested:notEqual value="0" property="sysClientId"><bean:write name="corporateSaleForm" property="clientDTO.clientNm" /></nested:notEqual></span>
					</td>
				</tr>
				<tr>
					<td>得意先TEL</td>
					<td colspan="3">
						<span class="text_w150" id="clientTel"><nested:notEqual value="0" property="sysClientId"><bean:write name="corporateSaleForm" property="clientDTO.tel" /></nested:notEqual></span>
					</td>
				</tr>
				<tr>
					<td>得意先FAX</td>
					<td colspan="3">
						<span class="text_w150" id="clientFax"><nested:notEqual value="0" property="sysClientId"><bean:write name="corporateSaleForm" property="clientDTO.fax" /></nested:notEqual></span>
					</td>
				</tr>

				<tr>
					<td>備考</td>
					<td colspan="3">
						<span class="text_w150" id="clientRemarksShortened" title ="<bean:write name="corporateSaleForm" property="clientDTO.remarks" />"><nested:notEqual value="0" property="sysClientId"><bean:write name="corporateSaleForm" property="clientDTO.shortenedRemarks" /></nested:notEqual></span>
					</td>
				</tr>


				<tr>
					<td colspan="4"><hr class="line"></td>
				</tr>
				<tr>
					<td>支払方法</td>
					<td colspan="3">
 						<nested:select property="paymentMethod" styleId="paymentMethod">
							<html:optionsCollection property="paymentMethodMap" label="value" value="key" />
 						</nested:select>
<%-- 						<span id="paymentMethodNm"><bean:write name="corporateSaleForm" property="clientDTO.paymentMethodNm" /></span> --%>
					</td>
				</tr>
				<tr>
					<td colspan="4"><hr class="line"></td>
				</tr>
				<tr>
					<td>納入期限</td>
					<td colspan="3"><nested:text property="deliveryDeadline" styleClass="calender ime_off" maxlength="10" /></td>
				</tr>
				<tr>
					<td>回収方法</td>
					<td colspan="3"><nested:select property="sysAccountId">
						<html:optionsCollection property="accountList" label="accountNm" value="sysAccountId" />
					</nested:select></td>
				</tr>
				<tr class="receive">
					<td colspan="4"><hr class="line"></td>
				</tr>
				<tr class="sumSalesPrice receive">
					<td>売上合計</td>
					<td colspan="3">
						<span id="dispSumSalesPrice"><nested:write property="sumSalesPrice" /></span>
						<nested:hidden property="sumSalesPrice" styleId="sumSalesPrice" />
					</td>
				</tr>
				<tr class="sumClaimPrice receive">
					<td>合計金額</td>
					<td colspan="3">
						<span class="dispSumClaimPrice"><nested:write property="sumClaimPrice" /></span>
					</td>
				</tr>
				<tr class="receive">
					<td>入金</td>
					<td><nested:text property="corporateReceiveDate" styleClass="calender ime_off" styleId="corporateReceiveDate" maxlength="10" /></td>
					<td><nested:text property="corporateReceivePrice" styleClass="w100 numText ime_off" styleId="corporateReceivePrice" onchange="checkCorporateReceive()"/></td>
					<td><a class="button_small_main in" href="javascript:void(0);" id="corporateReceive">入金</a></td>
				</tr>
				<logic:notEmpty name="corporateSaleForm" property="corporateReceiveList">
					<logic:iterate id="corporateReceiveList" name="corporateSaleForm" property="corporateReceiveList" indexId="idx" >
					<tr class="receive">
						<td>
							<html:hidden property="sysCorporateReceiveId" name="corporateReceiveList" styleClass="sysCorporateReceiveId" />
							<input type="hidden" name="corporateReceiveList[${idx }].deleteFlag" class="deleteFlag" />
						</td>
						<td><bean:write property="receiveDate" name="corporateReceiveList" /></td>
						<td><span class="receivePrice"><bean:write property="receivePrice" name="corporateReceiveList" /></span></td>
						<td><span class="delReceive" style="cursor:pointer;"><i class="fa fa-trash-o"></i></span></td>
					</tr>
					</logic:iterate>
				</logic:notEmpty>
				<tr class="receive">
					<td>入金合計</td>
					<td colspan="3">
						<span id="dispReceivePrice"><nested:write property="receivePrice"/></span>
						<nested:hidden property="receivePrice" styleId="receivePrice"/>
					</td>
				</tr>
				<tr class="receivableBalance receive">
					<td>未納額</td>
					<td colspan="3"><span id="receivableBalance"></span></td>
				</tr>
			</table>
		</fieldset>

			<fieldset class="remarks_area">
			<legend>一言メモ</legend>
				<div class="remarks">
					<nested:textarea rows="4" cols="60" styleClass="w440 ime_on" property="orderRemarks" />
				</div>
			</fieldset>
			<fieldset class="remarks_area">

			<legend>見積書備考</legend>
				<div class="remarks">
					<nested:textarea rows="10" cols="60" styleClass="w440 ime_on estimateRemarks" property="estimateRemarks" />
				</div>
			</fieldset>
			<fieldset class="remarks_area">

			<legend>注文確定書備考</legend>
				<div class="remarks">
					<nested:textarea rows="10" cols="60" styleClass="w440 ime_on" property="orderFixRemarks" />
				</div>
			</fieldset>
			<fieldset class="remarks_area">

			<legend>請求書備考</legend>
				<div class="remarks">
					<nested:textarea rows="4" cols="60" styleClass="w440 ime_on" property="billingRemarks"/>
				</div>
			</fieldset>
	</div>

		<fieldset class="destination_area">
		<legend>納入先情報</legend>
		<html:errors/>
		<label style="margin-left:285px"><html:checkbox name="corporateSaleForm" property="newDeliveryFlg" styleId="newDeliveryFlg"/>納入先を新規登録</label>
			<table>
				<tr>
					<td></td>
					<td><a id="copy" href="javascript:void(0)" >得意先情報をコピーする</a></td>
				</tr>
				<tr>
					<td>その他納入先</td>
					<td><select id="deliverySelect">
					</select></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td>得意先名</td> -->
<%-- 					<td><nested:text property="destinationNm" styleClass="text_w150 destinationNm" maxlength="50" /></td> --%>
<!-- 				</tr> -->
				<tr>
					<td>納入先名</td>
					<td><nested:text property="destinationNm" styleClass="text_w250 destinationNm ime_on" maxlength="50" /></td>
				</tr>
				<tr>
					<td>納入先名カナ</td>
					<td><nested:text property="destinationNmKana" styleClass="text_w250 destinationNmKana ime_on" maxlength="50" /></td>
				</tr>
				<tr>
					<td>納入先電話番号</td>
					<td><nested:text property="destinationTel" styleClass="text_w150 destinationTel ime_off" maxlength="13" /></td>
				</tr>
				<tr>
					<td>納入先FAX番号</td>
					<td><nested:text property="destinationFax" styleClass="text_w150 destinationFax ime_off" maxlength="13" /></td>
				</tr>
				<tr>
					<td colspan="2"><hr class="line"></td>
				</tr>
				<tr>
					<td>郵便番号</td>
					<td>〒&nbsp;<nested:text property="destinationZip" styleClass=
						"text_w80 destinationZip ime_off" maxlength="8" onkeyup=
						"AjaxZip3.zip2addr('corporateSalesSlipDTO.destinationZip', '', 'corporateSalesSlipDTO.destinationPrefectures', 'corporateSalesSlipDTO.destinationMunicipality', 'corporateSalesSlipDTO.destinationAddress');" /></td>
				</tr>
				<tr>
					<td>都道府県</td>
					<td><nested:text property="destinationPrefectures" styleClass="text_w100 destinationPrefectures ime_on" maxlength="10" /></td>
				</tr>
				<tr>
					<td>市区町村</td>
					<td><nested:text property="destinationMunicipality" styleClass="text_w150 destinationMunicipality ime_on" /></td>
				</tr>
				<tr>
					<td>市区町村以降</td>
					<td><nested:text property="destinationAddress" styleClass="text_w250 destinationAddress ime_on" /></td>
				</tr>
				<tr>
					<td>建物名</td>
					<td><nested:text property="destinationBuildingNm" styleClass="text_w200 destinationBuildingNm ime_on" /></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td>会社名</td> -->
<%-- 					<td><nested:text property="destinationCompanyNm" styleClass="text_w200 destinationCompanyNm" /></td> --%>
<!-- 				</tr> -->
				<tr>
					<td colspan="2"><hr class="line"></td>
				</tr>
				<tr>
					<td>部署名</td>
					<td><nested:text property="destinationQuarter" styleClass="text_w200 destinationQuarter ime_on" /></td>
				</tr>
				<tr>
					<td>役職名</td>
					<td><nested:text property="destinationPosition" styleClass="text_w200 destinationPosition ime_on" /></td>
				</tr>
				<tr>
					<td>御担当者名</td>
					<td><nested:text property="destinationContactPersonNm" styleClass="text_w200 destinationContactPersonNm ime_on" /></td>
				</tr>
			</table>
		</fieldset>

		<div class="h575">
			<fieldset class="delivery_area">
			<legend>配送情報</legend>
				<table>
					<!-- <nested:hidden property="slipNo" /> -->
					<nested:hidden property="shipmentDate"/>
					<tr>
						<td>運送会社</td>
						<td><nested:select property="transportCorporationSystem" styleId="transportCorporationSystem">
							<html:optionsCollection property="transportCorporationSystemMap" label="value" value="key"/>
						</nested:select></td>
					</tr>
					<tr class="b2 ehiden seino">
						<td><span class="seino">原票区分</span></td>
						<td><nested:select property="genpyoKbn" styleId="genpyoKbnMapSeino" styleClass="seino">
								<html:optionsCollection property="genpyoKbnMapSeino" label="key" value="key"/>
							</nested:select>
						</td>
					</tr>
					<tr class="b2 ehiden seino">
						<td>送り状種別</td>
						<td><nested:select property="invoiceClassification" styleId="invoiceClassificationB2" styleClass="b2">
								<html:optionsCollection property="invoiceClassificationMapB2" label="key" value="key"/>
							</nested:select>
							<nested:select property="invoiceClassification" styleId="invoiceClassificationEhiden" styleClass="ehiden">
								<html:optionsCollection property="invoiceClassificationMapEhiden" label="key" value="key"/>
							</nested:select>
							<nested:select property="invoiceClassification" styleId="invoiceClassificationSeino" styleClass="seino">
								<html:optionsCollection property="invoiceClassificationMapSeino" label="key" value="key"/>
							</nested:select>
						</td>
					</tr>
					<tr class="b2 ehiden seino">
						<td><span class="seino">輸送指示1</span></td>
						<td><nested:select property="yusoShiji" styleId="yusoShijiMapSeino" styleClass="seino">
								<html:optionsCollection property="yusoShijiMapSeino" label="key" value="key"/>
							</nested:select>
						</td>
					</tr>
					<tr class="b2 ehiden seino">
						<td><span class="seino">輸送指示2</span></td>
						<td><nested:select property="yusoShiji2" styleId="yusoShijiMapSeino" styleClass="seino">
								<html:optionsCollection property="yusoShijiMapSeino" label="key" value="key"/>
							</nested:select>
						</td>
					</tr>
					<tr class="ehiden b2 seino">
						<td><span class="ehiden">配達日</span><span class="b2 seino">お届け予定日</span></td>
						<td><nested:text property="destinationAppointDate" styleClass="calender ime_off destinationAppointDate" maxlength="10" /></td>
					</tr>
					<tr class="b2 ehiden seino">
						<td>時間帯指定</td>
						<td>
							<nested:select property="destinationAppointTime" styleId="destinationAppointTimeB2" styleClass="b2">
								<html:optionsCollection property="appointTimeB2Map" label="value" value="key" />
							</nested:select>
							<nested:select property="destinationAppointTime" styleId="destinationAppointTimeEhiden" styleClass="ehiden">
								<html:optionsCollection property="appointTimeEhidenMap" label="value" value="key" />
							</nested:select>
							<nested:select property="destinationAppointTime" styleId="destinationAppointTimeSeino" styleClass="seino">
								<html:optionsCollection property="appointTimeSeinoMap" label="value" value="key" />
							</nested:select>
							<input type="hidden" name="corporateSalesSlipDTO.destinationAppointTime" value="" />
						</td>
					</tr>
				</table>
			</fieldset>

			<fieldset class="memo_area">
				<legend>送り状記載事項</legend>
					<div class="senderMemo">
						<nested:textarea rows="3" cols="60" styleClass="w290 ime_on" property="senderRemarks"/>
					</div>
			</fieldset>
			
			<fieldset class="memo_area">
				<legend>送り状番号</legend>
					<div class="senderMemo">
						<nested:textarea rows="3" cols="60" styleClass="w290 ime_on" property="slipNo"/>
					</div>
			</fieldset>
		</div>

		<fieldset class="item_area">
		<legend>商品情報</legend>
			<table class="itemTable">
				<tr>
					<th id="add_button_area"colspan="2" class="w100"></th>
					<th id="code">品番</th>
					<th id="item_nm"><label class="necessary">※</label>&nbsp;商品名</th>
					<th id="stock_num">在庫数</th>
					<th id="assembly_num">組立可数</th>
					<th id="order_num"><label class="necessary">※</label>&nbsp;注文数</th>
					<th id="price"><label class="necessary">※</label>&nbsp;単価</th>
					<th id="cost">原価</th>
					<th>個別小計 （税込金額）</th>
					<th>出庫予定日</th>
					<th>ピッキング</th>
					<th>出庫日</th>
					<th id="stock_num">出庫数</th>
					<th>売上日</th>
					<th></th>
				</tr>
				<tr>
					<td colspan="10"></td>
					<td><input type="text" class="calender" id="scheduledLeavingDateAll" placeholder="一括設定"></td>
				</tr>

<!-- 				表示用 -->
				<logic:notEmpty name="corporateSaleForm" property="corporateSalesItemList">
				<logic:iterate id="corporateSalesItemList" name="corporateSaleForm" property="corporateSalesItemList" indexId="idx" >

				<!-- 		マスタにない商品の色変え -->
				<logic:equal name="corporateSaleForm" property="corporateSalesItemList[${idx}].sysItemId" value="0">
					<bean:define id="backgroundColor" value="#FFFFC0" />
				</logic:equal>
				<logic:notEqual name="corporateSaleForm" property="corporateSalesItemList[${idx}].sysItemId" value="0">
					<bean:define id="backgroundColor" value="#FFFFFF" />
<%-- 					<bean:define id="disabled" value="true" /> --%>
				</logic:notEqual>

				<html:hidden property="corporateSalesItemList[${idx}].sysCorporateSalesItemId" />
				<html:hidden property="corporateSalesItemList[${idx}].sysItemId" />
				<html:hidden property="corporateSalesItemList[${idx}].postage" />
<%-- 				<html:hidden property="salesItemList[${idx}].sysItemId" /> --%>
				<tr style="background-color: ${backgroundColor};" class="corporateSalesItemRow">
					<td><html:checkbox property="corporateSalesItemList[${idx}].returnFlg" styleClass="changeColorCheck"/></td>
					<td></td>
					<td><span class="dispItemCode"><bean:write name="corporateSaleForm" property="corporateSalesItemList[${idx}].itemCode" /></span></td>
					<td class="itemNmWidth"><bean:write name="corporateSaleForm" property="corporateSalesItemList[${idx}].itemNm" /></td>
						<html:hidden property="corporateSalesItemList[${idx}].itemNm" styleClass="itemNm"/>
					<td style="font-size: 18px;"><bean:write name="corporateSaleForm" property="corporateSalesItemList[${idx}].totalStockNum" />&nbsp;(<bean:write name="corporateSaleForm" property="corporateSalesItemList[${idx}].temporaryStockNum" />)</td>
					<td style="font-size: 18px;"><bean:write name="corporateSaleForm" property="corporateSalesItemList[${idx}].assemblyNum" /></td>
					<td><html:text property="corporateSalesItemList[${idx}].orderNum" styleClass="num orderNum numText ime_off" maxlength="4" /></td>
					<td><span class="itemPieceRate" ><html:text property="corporateSalesItemList[${idx}].pieceRate" styleClass="price_w80 pieceRate priceTextMinus ime_off" maxlength="9"/></span></td>
<%-- 					<td><html:text property="salesItemList[${idx}].cost" styleClass="price_w80 cost priceTextMinus" disabled="${disabled}" maxlength="9"/></td> --%>
					<td><span class="itemCost"><html:text property="corporateSalesItemList[${idx}].cost" styleClass="price_w80 cost priceTextMinus ime_off" maxlength="9"/></span></td>
					<td><span class="price_w80"><span class="subtotal">0</span>&nbsp;(<span class="subtotalWithTax">0</span>)</span></td>
					<td class="nw"><html:text property="corporateSalesItemList[${idx}].scheduledLeavingDate" styleClass="calender scheduledLeavingDate ime_off" maxlength="10" /></td>
					<td>
						<html:checkbox property="corporateSalesItemList[${idx}].pickingListFlg" />
						<html:hidden property="corporateSalesItemList[${idx}].pickingListFlg" value="off"/>
					</td>
					<td class="nw"><html:text property="corporateSalesItemList[${idx}].leavingDate" styleClass="text_w80 leavingDate calender ime_off" maxlength="10" readonly="true"/></td>
<%-- 					<td><html:text property="corporateSalesItemList[${idx}].leavingNum" /></td> --%>
					<td style="font-size: 18px;"><span class="leavingNum">
<%-- 					<bean:write name="corporateSalesItemList" property="leavingNum" /> --%>
					</span></td>
						<html:hidden property="corporateSalesItemList[${idx}].leavingNum" styleClass="hiddenLeavingNum"/>
					<td class="salesDateTh"><html:text property="corporateSalesItemList[${idx}].salesDate" styleClass="text_w80 salesDate calender ime_off" readonly="true"/></td>
					<td><logic:empty name="corporateSaleForm" property="corporateSalesItemList[${idx}].leavingDate">
						<a href="Javascript:void(0);" class="button_item_delete deleteRegisted">削除</a>
						<html:hidden property="corporateSalesItemList[${idx}].deleteFlag" styleClass="deleteFlag" />
					</logic:empty></td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
<!-- 				追加用 -->
				<logic:notEmpty name="corporateSaleForm" property="addCorporateSalesItemList">
				<logic:iterate id="addCorporateSalesItemList" name="corporateSaleForm" property="addCorporateSalesItemList" indexId="idx" >
				<tr class="addSalesItemRow itemSearchRow">
					<html:hidden property="addCorporateSalesItemList[${idx}].sysItemId" styleClass="addSysItemId" />
					<td><input type="checkbox" class="changeColorCheck"/></td>
					<td class="pdg_left_10px nw"><a href="Javascript:void(0);" class="button_small_main searchItem">商品検索</a></td>
					<td><html:text property="addCorporateSalesItemList[${idx}].itemCode" style="min-width:120px; max-width:120px;" styleClass="addItemCode ime_off" maxlength="100" /></td>
					<td><html:textarea property="addCorporateSalesItemList[${idx}].itemNm" style="min-width:250px; max-width:250px;" styleClass="addItemNm ime_on"
								cols="30" rows="1" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
								onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);">
						</html:textarea>
					</td>
					<td>
						<span style="font-size: 18px;" class="addTotalStockNum">
							<bean:write name="corporateSaleForm" property="addCorporateSalesItemList[${idx}].totalStockNum" />
						</span>
						<span style="font-size: 18px;" class="addTemporaryStockNum">
							(<bean:write name="corporateSaleForm" property="addCorporateSalesItemList[${idx}].temporaryStockNum" />)
						</span>
					</td>
					<td><span style="font-size: 18px;" class="addAssemblyNum"><bean:write name="corporateSaleForm" property="addCorporateSalesItemList[${idx}].assemblyNum" /></span></td>
					<td><html:text property="addCorporateSalesItemList[${idx}].orderNum" styleClass="num addOrderNum numText ime_off" maxlength="4" /></td>
					<td><html:text property="addCorporateSalesItemList[${idx}].pieceRate" styleClass="price_w80 addPieceRate priceTextMinus ime_off" maxlength="9"/></td>
					<td><html:text property="addCorporateSalesItemList[${idx}].cost" styleClass="price_w80 addCost priceTextMinus ime_off" maxlength="9"/></td>
					<td><span class="price_w80"><span class="subtotal">0</span>&nbsp;(<span class="subtotalWithTax">0</span>)</span></td>
					<td class="nw"><html:text property="addCorporateSalesItemList[${idx}].scheduledLeavingDate" styleClass="calender scheduledLeavingDate ime_off" maxlength="10" /></td>
					<td>
						<html:checkbox property="addCorporateSalesItemList[${idx}].pickingListFlg" styleClass="addPickingFlg" />
						<html:hidden property="addCorporateSalesItemList[${idx}].pickingListFlg" value="off"/>
					</td>
					<td class="nw"><html:text property="addCorporateSalesItemList[${idx}].leavingDate" styleClass="text_w80 addLeavingDate calender ime_off" readonly="true"/></td>
<%-- 					<td><html:text property="addCorporateSalesItemList[${idx}].leavingNum" /></td> --%>
					<td style="font-size: 18px;"><span class="leavingNum"><bean:write name="addCorporateSalesItemList" property="leavingNum" /></span></td>
					<td class="salesDateTh"><html:text property="addCorporateSalesItemList[${idx}].salesDate" styleClass="text_w80 addSalesDate calender ime_off" readonly="true"/></td>
					<td class="tdButton"><a href="Javascript:void(0);" class="button_small_white removeSalesItem">削除</a></td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
			</table>
		</fieldset>

		<fieldset class="price_area">
		<legend>金額情報</legend>
<!-- 	登録/編集用金額データ -->
		<nested:hidden property="sumPieceRate" styleId="sumPieceRate" />
		<nested:hidden property="tax" styleId="tax" />
		<nested:hidden property="sumClaimPrice" styleId="sumClaimPrice" styleClass="priceTextMinus" />
		<nested:hidden property="grossMargin" styleId="grossMargin" />


			<table>
				<tr>
					<td>為替<nested:select property="currency" styleId="currency">
						<html:option value="1">円</html:option>
						<html:option value="2">ドル</html:option>
					</nested:select></td>
				</tr>
				<tr>
					<td>
						小計:&nbsp;<span id="dispSumPieceRate" class="price_w100 priceTextMinus" ><nested:write property="sumPieceRate" /></span>&nbsp;<span class="currency">円</span>
					</td>
					<td class="pdg_left_20px">消費税:</td>
					<td class="td_right">
						&nbsp;<span id="dispTax" class="price_w80 priceTextMinus"><nested:write property="tax" /></span>&nbsp;<span class="currency">円</span>
					</td>
					<td class="pdg_left_20px" style="text-align: right;">外税</td>
				</tr>
				<tr>
					<td class="pdg_top_20px">
						合計:&nbsp;<span class="price_w100 priceTextMinus dispSumClaimPrice"><nested:write property="sumClaimPrice" /></span>&nbsp;<span class="currency">円</span>
					</td>
					<td class="pdg_top_20px pdg_left_20px">粗利:</td>
					<td class="pdg_top_20px td_right">
						&nbsp;<span id="dispGrossMargin" class="price_w80"><nested:write property="grossMargin" /></span>&nbsp;<span class="currency">円</span>
					</td>
					<td class="pdg_left_20px pdg_top_20px" style="text-align: center;"><a href="Javascript:void(0);" class="button_small_main taxCalc">再計算</a></td>
				</tr>
				<tr>
					<td colspan="2"><span class="explain">※外税・・・小計に対して消費税が加算される</span></td>
				</tr>
			</table>
		</fieldset>

	</nested:nest>
		<footer class="footer sPlanning">
			<ul style="position: relative;">
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<a class="button_white clear" href="javascript:void(0)" onclick="goTransaction('corporateSaleListPageNo.do');" >一覧へ戻る</a>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:equal value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<a class="button_main" id="registry" href="javascript:void(0)" >登録</a>
					</nested:equal>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<a class="button_main" id="update" href="javascript:void(0)" >修正</a>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<a class="button_white" id="delete" href="javascript:void(0)">削除</a>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<nested:notEqual value="1" property="corporateSalesSlipDTO.returnFlg">
							<a class="button_main copySlip" href="javascript:void(0)" >複製</a>
						</nested:notEqual>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<nested:notEqual value="1" property="corporateSalesSlipDTO.returnFlg">
							<a class="button_return" id="returnSlip" href="javascript:void(0)">返品</a>
						</nested:notEqual>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<nested:notEqual value="1" property="corporateSalesSlipDTO.returnFlg">
							<a class="button_main" href="javascript:void(0)" onclick="goExportEstimate();" >見積書印刷</a>
						</nested:notEqual>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<nested:notEqual value="1" property="corporateSalesSlipDTO.returnFlg">
							<a class="button_main" href="javascript:void(0)" onclick="goExportOrderAcceptance();" >注文請書印刷</a>
						</nested:notEqual>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="corporateSalesSlipDTO.sysCorporateSalesSlipId">
						<a class="button_main" id="newSlip" href="javascript:void(0)" >新規登録</a>
					</nested:notEqual>
				</li>
			</ul>
		</footer>

	</html:form>
	<div class="overlay">
		<div class="messeage_box">
			<h1 class="message">ファイル作成中</h1>
			<BR />
			<p>Now Loading...</p>
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>

</html:html>