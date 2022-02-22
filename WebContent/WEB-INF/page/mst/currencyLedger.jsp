<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<link rel="stylesheet" href="./css/currencyLedger.css" type="text/css" />

	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

	<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>


<!--
【商品詳細画面】
ファイル名：currencyLedger.jsp
作成日：2016/11/10
作成者：野澤法都

（画面概要）
仕入管理の海外通貨台帳画面
通貨の登録、削除、更新
※登録済み通貨は「削除」は他に使用されていないことが条件

【基本情報エリア】
初期表示時、登録された通貨一覧を表示
登録ボタンでレコードの追加
更新ボタンで全ての入力した情報を更新
削除ボタンでレコードが削除

（注意・補足）

-->

<script type="text/javascript">

$(function () {

	if(!$("#registryDTO.message").val()) {
		if($("#messageFlg").val() == "0") {
			$("#messageArea").addClass("registrySuccess");
		}
		if($("#messageFlg").val() == "1"){
			$("#messageArea").addClass("registryFailure");
		}
		$("#messageArea").fadeOut(4000);
	}

	//初期表示時、追加分レコードを隠す
	var addCurrencyLedgerTrs = $("tr.addCurrency");
	addCurrencyLedgerTrs.hide();

	//追加分レコードの表示分をカウントに使用
	var addCurrencyLedgerIdx = 0;

	//登録分レートに小数点以下が0の場合、表示させない
	for (var i = 0; i < $("tr.currency").size(); i++) {
		var trs = $("tr.currency");
		var tr = trs.eq(i);

		if (tr.find(".rate").val() % 1 === 0.00000) {
			tr.find(".rate").val(Math.floor(tr.find(".rate").val()));
		}
	}

	//登録追加分レートに小数点が以下が0の場合、表示させない
	for (var i = 0; i < $("tr.addCurrency").size(); i++) {
		var trs = $("tr.addCurrency");
		var tr = trs.eq(i);

		if (tr.find(".addRate").val() % 1 === 0) {
			tr.find(".addRate").val(Math.floor(tr.find(".addRate").val()));
		}
	}

// 	$(".addCurrencyType").toString().replace(/()}));
	//登録ボタン処理
	$(".addregistry").click(function () {

		if (addCurrencyLedgerIdx >= 5) {
			alert("一度に追加できるのは5件までです。");
			return;
		}

		//一行分表示
		var trs = $("tr.addCurrency");
		var tr = trs.eq(addCurrencyLedgerIdx);
		tr.show();
		tr.find(".addRate").val(0);

		addCurrencyLedgerIdx++;
		//CurrencyIdは隠す
		$(".addId").hide();
	});

	//追加分行削除
	$(".removeBtn").click(function () {

		var selectArea = $("tr.addCurrency");
		var tr = selectArea.eq(--addCurrencyLedgerIdx);
		var i = 0;
		for (i = $("tr.addCurrency").index($(this).parents("tr.addCurrency")); i <= addCurrencyLedgerIdx; i++) {

			//最終行の判定
			if (i == 5) {

				clearAddCurrencyLastRow(selectArea.eq(i));
			} else {
				var copyFromTr = selectArea.eq(i + 1);
				var copyToTr = selectArea.eq(i);

				copyCurrency(copyFromTr, copyToTr);
			}
		}

		tr.hide();
	});

	//追加分選択行の削除
	function copyCurrency(copyFromTr, copyToTr) {

		copyToTr.find(".addCurrencyType").val(copyFromTr.find(".addCurrencyType").val());
		copyToTr.find(".addCurrencyNm").val(copyFromTr.find(".addCurrencyNm").val());
		copyToTr.find(".addRate").val(copyFromTr.find(".addRate").val());

		copyFromTr.find(".addCurrencyType").val("");
		copyFromTr.find(".addCurrencyNm").val("");
		copyFromTr.find(".addRate").val(0);
	}

	//追加分最終行の削除
	function clearAddCurrencyLastRow(currencyRow){
		currencyRow.find(".addCurrencyType").val("");
		currencyRow.find(".addCurrencyNm").val("");
		currencyRow.find(".addRate").val(0);
	}

	//更新ボタン処理
	$(".update").click(function () {

		//更新判定
		if(!registryCheck($("tr.currency"), $("tr.addCurrency"))) {
			return;
		}

		goTransaction('updateCurrencyLedger.do');
	});

	//更新判定
	function registryCheck (showTr, addShowTr) {

		var registered = true;
		showTr.each(function(){
		for (var i = 0; i < showTr.size(); i++) {
			var currencyType = showTr.eq(i).find(".currencyType").val();
			var currencyNm = showTr.eq(i).find(".currencyNm").val();
			var rate = showTr.eq(i).find(".rate").val();

			//登録分の記号、名称が消されている判定
			if (currencyType == "" || currencyNm == "" || rate == "") {

				registered = false;
				alert('登録済みの記号、名称、レートは必須項目です。');
				return false;
			}

			//レートの数字以外の判定
			if (rate.match( /[^0-9.,-]+/ )) {

				registered = false;
				alert("半角数字で入力して下さい。");
				return false;
			} else if (rate <= -100000 || rate >= 100000) {
				registered = false;
				alert("レートは整数部5桁までです。");
				return false;
			}
		}

		//追加分の優先度、倉庫名が片方だけの判定
		for (var i = 0; i < addCurrencyLedgerIdx; i++) {
			var addCurrencyType = addShowTr.eq(i).find(".addCurrencyType").val();
			var addCurrencyNm = addShowTr.eq(i).find(".addCurrencyNm").val();
			var addRate = addShowTr.eq(i).find(".addRate").val();

			//追加分の記号、名称が消されている判定
			if (addCurrencyType == "" || addCurrencyNm == "" || addRate == "") {
				registered = false;
				alert('記号、名称、レートは必須項目です。');
				return false;
			}

			//レートの数字以外の判定
			if (addRate.match( /[^0-9.,-]+/ )) {

				registered = false;
				alert("レートは半角数字で入力して下さい。");
				return false;
			} else if (addRate <= -100000 || addRate >= 100000) {
				registered = false;
				alert("レートは5桁までです。");
				return false;
			}
		}
		});

		return registered;
	}

	$(".rate").focus(function(){
		var index = $(".rate").index(this);
		if ($(".rate").eq(index).val() == 0) {
			$(".rate").eq(index).val("");
		}
	});

	$(".rate").blur(function(){
		var index = $(".rate").index(this);
		if ($(".rate").eq(index).val() == "") {
			$(".rate").eq(index).val(0);
		}
		addPurchasingCostCalc(index)
		});

	$(".addRate").focus(function(){
		var index = $(".addRate").index(this);
		if ($(".addRate").eq(index).val() == 0) {
			$(".addRate").eq(index).val("");
		}
	});

	$(".addRate").blur(function(){
		var index = $(".addRate").index(this);
		if ($(".addRate").eq(index).val() == "") {
			$(".addRate").eq(index).val(0);
		}
		addPurchasingCostCalc(index)
		});

});

	//登録済み通貨削除ボタン
	function goDelete(deletetarget){
		if (confirm("登録済みの通貨を削除します。よろしいですか？")) {

			var del = new Number(deletetarget);

			$("#deleteTargetId").val(del);

	 		goTransaction("deleteCurrencyLedger.do");
		}
	}

</script>
	</head>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initCurrencyLedgerList">

		<nested:nest property="registryDTO">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>

		<h1 class="heading">海外通貨台帳</h1>

<%-- 		<nested:hidden property="alertType" styleId="alertType"></nested:hidden> --%>
		<input type="hidden" name="currencyId"  id="currencyId" />
		<nested:hidden property="deleteTargetId" styleId="deleteTargetId"></nested:hidden>

		<table id="mstTable" class="list">
			<tr>
				<th class="thId" rowspan="2">ID</th>
				<th colspan="2">通貨</th>
				<th class="thRate" rowspan="2">レート<label class="necessary">※</label></th>
			</tr>
			<tr>
				<td class="colorType">記号<label class="necessary">※</label></td>
				<td class="colorNm">名称<label class="necessary">※</label></td>
			</tr>
				<!-- 表示用 -->
			<nested:iterate property="currencyLedgerList" indexId="idx">
			<tr class="currency">
					<td><nested:write property="currencyId"/></td>
					<td><nested:text styleClass="currencyType" property="currencyType" maxlength="20" size="8"/></td>
					<td><nested:text styleClass="currencyNm" property="currencyNm" maxlength="20" size="20"/></td>
					<td class="right"><label>\</label><nested:text styleClass="rate" property="rate" size="8"/>
					</td>
					<td class="noborder"><a class="button_small_white deleteBtn" href="Javascript:void(0);" onclick="goDelete(<nested:write  property="currencyId"/>);">削除
					</a></td>
				</tr>
			</nested:iterate>
			<!-- 追加用 -->
			<nested:iterate property="addCurrencyLedgerList" indexId="idx">
				<tr class="addCurrency">
					<td><span class="addId"><nested:write  property="currencyId"/></span></td>
					<td><nested:text styleClass="addCurrencyType" property="currencyType" maxlength="20"  size="8"/></td>
					<td><nested:text styleClass="addCurrencyNm" property="currencyNm" maxlength="20" size="20"/></td>
					<td class="right"><label>\</label><nested:text styleClass="addRate" property="rate" size="8"/></td>
					<td class="noborder"><a class="button_small_white removeBtn" href="Javascript:void(0);">削除</a></td>
				</tr>
			</nested:iterate>
		</table>

		<footer class="footer buttonArea">
			<ul>
				<li class="footer_button"><a class="button_main addregistry" href="javascript:void(0)" >新規追加</a></li>
				<li class="footer_button"><a class="button_main update" href="javascript:void(0);">登録/更新</a></li>
			</ul>
		</footer>
	</html:form>
</html:html>