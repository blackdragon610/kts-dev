<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
<!-- 		<title>売上詳細</title> -->
	<link rel="stylesheet" href="./css/saleDetail.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />

	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>

	<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>
	<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>

<!--
【売上詳細画面】
ファイル名：saleDetail.jsp
作成日：2014/12/24
作成者：八鍬寛之

（画面概要）

売上データの新規登録・詳細画面
詳細画面は売上一覧から遷移

・「注文者情報をコピーする」リンク押下：注文情報の注文者名から部署名までの情報を届け先情報にコピーする
・「商品検索」ボタン押下：品番と商品名で検索をかけた状態で商品検索画面が開く。1件のみの該当だった場合はそのまま画面が閉じて
　　検索結果の品番と商品名がテキストボックスに入る
・「税計算」ボタン押下：小計をもとに消費税計算を行う。消費税計算後に代引手数料・送料が加算される。
　　※内税・・・小計に含まれている消費税を計算 ※外税・・・小計に対して消費税が加算される
・「修正」ボタン押下：入力情報を更新する
・「一覧に戻る」ボタン押下：売上一覧に遷移する
・「削除」ボタン押下：売上情報を削除する
・「複製」ボタン押下：売上情報を全て引継いだ状態で新規登録画面に遷移する
・「返品」ボタン押下：複製と同様売上情報を全て引継いだ状態で新規登録画面に遷移する。
　　そこでマイナス計上する商品をチェックボックスで選択して登録する


（注意・補足）

-->


	<script type="text/javascript">


$(function() {

	$(".calender").datepicker();
	$(".calender").datepicker("option", "showOn", 'button');
	$(".calender").datepicker("option", "buttonImageOnly", true);
	$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

	// 初期画面表示時の商品情報、配送情報、金額情報の入力制御
	window.onload = function textControl() {
		//出庫済みにチェックがあるか否か
			if ($("#leavingCheck").prop("checked")) {
				//返品処理中であるか否か
				if ($("#returnFlg").val() == "0") {
					//配送情報
					
					// $(".delivery_area").attr("disabled","disabled");
					$('input[name ="salesSlipDTO.slipNo"]').removeAttr('disabled');
					$('select[name ="salesSlipDTO.transportCorporationSystem"]').attr("disabled","disabled");
					$('select[name ="salesSlipDTO.invoiceClassification"]').attr("disabled","disabled");
					$('input[name ="salesSlipDTO.cashOnDeliveryCommission"]').attr("disabled","disabled");
					$('input[name ="salesSlipDTO.destinationAppointTime"]').attr("disabled","disabled");
					
					$(".price_area").attr("disabled","disabled");
					//個数入力欄
					$(".num").attr("disabled","disabled");
					//単価・原価
					$(".price_cost_area").attr("disabled","disabled");
					//税計算
					$('.taxCalc').css('pointer-events', 'none');
					$("input[name='salesSlipDTO.destinationAppointDate']").datepicker( "option", "disabled", true );
					$("input[name='salesSlipDTO.shipmentPlanDate']").datepicker( "option", "disabled", true );
					$("#shipmentDate").datepicker( "option", "disabled", true );
					$('.addSalesItem').css('pointer-events', 'none');
					$('.searchItem').css('pointer-events', 'none');
					$("#leavingCheck").prop("disabled" , true);
				} else {
					//個数欄のスピナー
					$(".num").unbind("blur");
					$('.num').spinner( {
						max: 9999,
						min: -9999,
						step: 1
					});
				}
				//追加用の行を削除
				$(".addSalesItemRow").css("display","none");
			} else {

				if ($("#returnFlg").val() == "0") {
					//個数欄のスピナー
					$('.num').spinner( {
						max: 9999,
						min: 0,
						step: 1
					});
				} else {
				//個数欄のスピナー
					$(".num").unbind("blur");
					$('.num').spinner( {
						max: 9999,
						min: -9999,
						step: 1
					});
				}
			}
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

			var salesItemAreaIdx = count;
		}
		$("tr.addSalesItemRow").eq(0).show();

		aTag = $("tr.addSalesItemRow").eq(0).find(".button_small_white");
		if (aTag) {
			aTag.attr("class", "button_small_main addSalesItem");
			aTag.text("追加");
		}
		aTag = "";
		tr = "";
	}
	//アラート
	if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value = '0';
	}

	//売上商品追加
	$(".addSalesItem").click(function () {

		if (salesItemAreaIdx + 1 >= $("#salesItemLength").val()) {
			alert("一括で登録できるのは"+$("#salesItemLength").val()+"件までです。");
			return;
		}

		var salesItemArea = $("tr.addSalesItemRow");

		var i = 0;
		for (i = salesItemAreaIdx; i >= 0; i--) {

			var copyFromTr = salesItemArea.eq(i);
			var copyToTr = salesItemArea.eq(i + 1);

			copySalesItem(copyFromTr, copyToTr);
		}

		var tr = salesItemArea.eq(++salesItemAreaIdx);

		tr.show();
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
	//売上商品削除
	$(".removeSalesItem").click(function () {


		var salesItemArea = $("tr.addSalesItemRow");
		var i = 0;
		for (i = $("tr.addSalesItemRow").index($(this).parents("tr.addSalesItemRow")); i <= salesItemAreaIdx; i++) {

			var copyFromTr = salesItemArea.eq(i + 1);
			var copyToTr = salesItemArea.eq(i);

			copySalesItem(copyFromTr, copyToTr);
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

		copyFromTr.find(".addSysItemId").val(0);
		copyFromTr.find(".addItemCode").val("");
		copyFromTr.find(".addItemNm").val("");
		copyFromTr.find(".addOrderNum").val(0);
		copyFromTr.find(".addPieceRate").val(0);
		copyFromTr.find(".addCost").val(0);
		copyFromTr.find(".addTotalStockNum").html(0);
		copyFromTr.find(".addTemporaryStockNum").html(0);

	}

	//検索
	$(".searchItem").click (function () {

		if($("#sysCorporationId").val() == '0') {
			alert("法人を選択してください。");
			return;
		}

		var salesItemArea = $("tr.addSalesItemRow");

		var rowNum = $("tr.addSalesItemRow").index($(this).parents("tr.addSalesItemRow"));

		$("#searchItemCode").val(salesItemArea.eq(rowNum).find(".addItemCode").val());
		$("#searchItemNm").val(salesItemArea.eq(rowNum).find(".addItemNm").val());
		$("#openerIdx").val(rowNum);
		$("#searchSysCorporationId").val($("#sysCorporationId").val());
		//国内商品検索用に受注番号を渡す
		if ($("#orderNo").val() != "") {
			$("#searchOrderNo").val($("#orderNo").val());
		} else {
			$("#searchOrderNo").val("");
		}

		if (!$("#searchItemCode").val() && !$("#searchItemNm").val()) {
			FwGlobal.submitForm(document.forms[0],"/initSubWinItem","itemSearchWindow","top=130,left=500,width=780px,height=520px;");
		} else {
			FwGlobal.submitForm(document.forms[0],"/subWinItemSearch","itemSearchWindow","top=130,left=500,width=780px,height=520px;");
		}

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

	$(".registry").click (function () {
		if ($("#orderDate").val() == "" || $("#sysCorporationId").val() == 0
				|| $("#sysChannelId").val() == 0) {

			alert('注文日・法人・販売チャネルは必須項目です。');
			return;
		}

		if ($("#orderNo").val() == "") {
			alert('受注番号は必須項目です。');
			return;
		}

		//返品の場合
		if ($("#returnFlg").val() == "1") {
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction("returnSales.do");
			return;
		}
		var i;
		var showTr = $("tr.addSalesItemRow");

		for (i = 0; i <= salesItemAreaIdx; i++) {

			var addPieceRate = new Number(showTr.eq(i).find(".addPieceRate").val());
			var addOrderNum = new Number(showTr.eq(i).find(".addOrderNum").val());
			var addItemNm = showTr.eq(i).find(".addItemNm").val();

			if (addItemNm != "" || addPieceRate != 0 || addOrderNum != 0) {

				if (addItemNm == "" || addOrderNum == 0) {

					alert('商品名・注文数は必須項目です。');
					return;
				}
			}
		}


		if (!confirm("登録します。よろしいですか？")) {
			return;
		}
		removeCommaList($(".priceTextMinus"));
		removeCommaGoTransaction('registrySales.do');
	});
	$(".update").click (function () {

		if ($("#orderDate").val() == "" || $("#sysCorporationId").val() == 0
				|| $("#sysChannelId").val() == 0) {

			alert('注文日・法人・販売チャネルは必須項目です。');
			return;
		}

		var i;
		var showTr = $("tr.addSalesItemRow");

		for (i = 0; i <= salesItemAreaIdx; i++) {

			var addPieceRate = new Number(showTr.eq(i).find(".addPieceRate").val());
			var addOrderNum = new Number(showTr.eq(i).find(".addOrderNum").val());
			var addItemNm = showTr.eq(i).find(".addItemNm").val();

			if (addItemNm != "" || addPieceRate != 0 || addOrderNum != 0) {

				if (addItemNm == "" || addOrderNum == 0) {

					alert('商品名・注文数は必須項目です。');
					return;
				}
			}
		}

		if (!confirm("更新します。よろしいですか？")) {
			return;
		}


		var leaveingCElm = document.getElementById("leavingCheck");

		if (leaveingCElm.disabled == true) {
			// clickイベントでdisabledを解除
			leaveingCElm.disabled = false;
		}

		removeCommaList($(".priceTextMinus"));
		removeCommaGoTransaction('updateDetailSale.do');
	});

	//税計算
	$(".taxCalc").click (function () {

		removeCommaList($(".priceTextMinus"));
		var sumPieceRate = new Number(getSumPieceRate());
		$("#sumPieceRate").val(sumPieceRate);
		var tax = new Number(0);
		tax = taxCalc(sumPieceRate, $("#taxClass").val(), new Number($("#taxRate").val()));
		$("#tax").val(tax);
		$("#sumClaimPrice").val(getSumClaimPrice($("#taxClass").val()));
	});

	$("#returnSlip").click (function () {
		var check = 0;
		$(".salesItemRow").each(function(i){
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
		removeCommaGoTransaction("initReturnSales.do");
	});

	$(".copySlip").click (function () {

		removeCommaList($(".priceTextMinus"));
		removeCommaGoTransaction('initCopySlip.do');
	});

	$(".copy").click (function () {

		$(".destinationFamilyNm").val($(".orderFamilyNm").val());
		$(".destinationFirstNm").val($(".orderFirstNm").val());
		$(".destinationFamilyNmKana").val($(".orderFamilyNmKana").val());
		$(".destinationFirstNmKana").val($(".orderFirstNmKana").val());
		$(".destinationTel").val($(".orderTel").val());
		$(".destinationZip").val($(".orderZip").val());
		$(".destinationPrefectures").val($(".orderPrefectures").val());
		$(".destinationMunicipality").val($(".orderMunicipality").val());
		$(".destinationAddress").val($(".orderAddress").val());
		$(".destinationBuildingNm").val($(".orderBuildingNm").val());
		$(".destinationCompanyNm").val($(".orderCompanyNm").val());
		$(".destinationQuarter").val($(".orderQuarter").val());
	});

	//伝票削除
	$("#delete").click (function () {

		if (confirm("伝票を削除します。よろしいですか？")) {
			removeCommaGoTransaction("deleteSales.do");
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

});
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
function getSumClaimPrice (taxClass) {

	var sumClaimPrice = new Number(0);
	sumClaimPrice += Number($("#sumPieceRate").val());
	sumClaimPrice += Number($("#codCommission").val());
	sumClaimPrice += Number($("#postage").val());
	sumClaimPrice += Number($("#etcPrice").val());
	sumClaimPrice -= Number($("#usedPoint").val());
	//外税の場合
	if (taxClass == "2") {

		sumClaimPrice += Number($("#tax").val());
	}

	return sumClaimPrice;
}

function taxCalc(sumPieceRate, taxClass, taxRate) {

	var tax = new Number(0);

	if (sumPieceRate == 0) {
		return 0;
	}
	//SaleService.javaのgetTaxにも同じロジックがあり
	//ここの税計算ロジックを変更した場合、同じ仕様に変更する必要あり
	//内税の場合
	if (taxClass == "1") {
		if (sumPieceRate < 0) {
			sumPieceRate = sumPieceRate * -1;
			tax = sumPieceRate - Math.floor((sumPieceRate / (100 + taxRate)) * 100);
			tax = tax * -1;
		} else {
			tax = sumPieceRate - Math.floor((sumPieceRate / (100 + taxRate)) * 100);
		}


	//外税の場合
	} else if (taxClass == "2") {
		if (sumPieceRate < 0) {
			sumPieceRate = sumPieceRate * -1;
			tax = Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
			tax = tax * -1;
		} else {
			tax = Math.floor((sumPieceRate * (100 + taxRate)) / 100) - sumPieceRate;
		}

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

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailSale">
	<nested:equal value="0" property="salesSlipDTO.sysSalesSlipId">
		<nested:equal value="0" property="salesSlipDTO.returnFlg">
		<h4 class="heading">売上登録</h4>
		</nested:equal>
		<nested:equal value="1" property="salesSlipDTO.returnFlg">
		<h4 class="heading" style="color:red; ">売上返品</h4>
		</nested:equal>
	</nested:equal>
	<nested:notEqual value="0" property="salesSlipDTO.sysSalesSlipId">
		<nested:equal value="0" property="salesSlipDTO.returnFlg">
		<h4 class="heading">売上詳細</h4>
		</nested:equal>
		<nested:equal value="1" property="salesSlipDTO.returnFlg">
		<h4 class="heading" style="color:red; ">売上返品</h4>
		</nested:equal>
	</nested:notEqual>

	<nested:nest property="searchItemDTO">
		<nested:hidden property="itemCode" styleId="searchItemCode" />
		<nested:hidden property="itemNm" styleId="searchItemNm" />
		<nested:hidden property="itemCodeTo" />
		<nested:hidden property="orderNo" styleId="searchOrderNo"/>
		<nested:hidden property="openerIdx" styleId="openerIdx" />
		<nested:hidden property="sysCorporationId" styleId="searchSysCorporationId" />
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

	<nested:nest property="errorDTO">
	<nested:notEmpty property="errorMessage">
		<div id="errorArea">
			<p class="errorMessage"><nested:write property="errorMessage"/></p>
		</div>
	</nested:notEmpty>
	</nested:nest>

	<nested:nest property="salesSlipDTO">

	<nested:hidden property="originOrderNo" styleId="originOrderNo"/>
	<nested:hidden property="returnFlg" styleId="returnFlg" />
	<nested:hidden property="taxRate" styleId="taxRate"/>
		<fieldset class="corporation_area">
			<table>
				<tr>
					<td>注文日</td>
					<td colspan="2"><label class="necessary">※</label>&nbsp;<nested:text styleClass="calender" styleId="orderDate" property="orderDate" maxlength="10" />&nbsp;&nbsp;&nbsp;<nested:text property="orderTime" styleClass="w100" maxlength="8" /></td>
				</tr>
				<tr>
					<td>法人</td>
					<td colspan="2"><label class="necessary">※</label>&nbsp;<nested:select property="sysCorporationId" styleId="sysCorporationId">
							<html:option value="0">　</html:option>
							<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
						</nested:select>
					</td>
				</tr>
				<tr>
					<td>販売チャネル</td>
					<td colspan="2"><label class="necessary">※</label>&nbsp;<nested:select property="sysChannelId" styleId="sysChannelId">
							<html:option value="0">　</html:option>
							<html:optionsCollection property="channelList" label="channelNm" value="sysChannelId"/>
						</nested:select>
					</td>
				</tr>
				<tr>
					<td>処理ルート</td>
					<td style="padding-left: 20px;"><nested:select property="disposalRoute">
						<html:optionsCollection property="disposalRouteMap" label="value" value="key"/>
					</nested:select>
					</td>
				</tr>
			</table>
		</fieldset>
			<fieldset class="updateinfo_area">
				<legend>最終更新情報</legend>
				<table style="margin-left: 110px;">
					<tr>
						<td>
							<bean:write name="saleForm" property="mstUserDTO.userFamilyNmKanji"/>
							<bean:write name="saleForm" property="mstUserDTO.userFirstNmKanji"/>
							<label>　　</label>
							<nested:write property="updateDate" format="yyyy/MM/dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
			</fieldset>
		<fieldset class="checkbox_area">
			<table>
				<tr>
					<td class="flgCheck"><label><nested:checkbox property="pickingListFlg"/>ピッキングリスト出力済</label></td>
					<td class="arrow">→</td>
					<td class="flgCheck"><label><nested:checkbox styleId="leavingCheck" property="leavingFlg"/>出庫済</label></td>
				</tr>
			</table>
		</fieldset>

<!-- 		<fieldset class="checkbox_area"> -->
<!-- 			<table> -->
<!-- 				<tr> -->
<!-- 					<td><label><input type="checkbox"/>返品伝票</label></td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<!-- 		</fieldset> -->
	<div class="leftArea">
		<fieldset class="slip_area">
		<legend>注文情報</legend>
			<table>
				<tr>
					<td>受注番号</td>
					<td colspan="2"><label class="necessary-2">※</label><nested:text property="orderNo" styleClass="text_w300" maxlength="30" styleId="orderNo"/></td>
				</tr>
				<tr>
					<td colspan="2"><hr class="line"></td>
				</tr>
				<tr>
					<td>注文者名</td>
					<td><nested:text property="orderFamilyNm" styleClass="text_w150 orderFamilyNm" maxlength="50" />&nbsp;<nested:text property="orderFirstNm" styleClass="text_w150 orderFirstNm" maxlength="50" /></td>
				</tr>
				<tr>
					<td>注文者名カナ</td>
					<td><nested:text property="orderFamilyNmKana" styleClass="text_w150 orderFamilyNmKana" maxlength="50" />&nbsp;<nested:text property="orderFirstNmKana" styleClass="text_w150 orderFirstNmKana" maxlength="50" /></td>
				</tr>
				<tr>
					<td>注文者TEL</td>
					<td><nested:text property="orderTel" styleClass="text_w150 orderTel" maxlength="13" /></td>
				</tr>
				<tr>
					<td>注文者MAIL</td>
					<td><nested:text property="orderMailAddress" styleClass="text_300" maxlength="256" /></td>
<%-- 					<td><nested:text property="orderMail" value="hiroyuki_yakuwa@boncre.co.jp" styleClass="w300" /></td> --%>
				</tr>
				<tr>
					<td colspan="2"><hr class="line"></td>
				</tr>
				<tr>
					<td>郵便番号</td>
					<td>〒&nbsp;<nested:text property="orderZip" styleClass="text_w80 orderZip" maxlength="8" onkeyup=
						"AjaxZip3.zip2addr('salesSlipDTO.orderZip', '', 'salesSlipDTO.orderPrefectures', 'salesSlipDTO.orderMunicipality', 'salesSlipDTO.orderAddress');" /></td>
				</tr>
				<tr>
					<td>都道府県</td>
					<td><nested:text property="orderPrefectures" styleClass="text_w100 orderPrefectures" maxlength="10" /></td>
				</tr>
				<tr>
					<td>市区町村</td>
					<td><nested:text property="orderMunicipality" styleClass="text_w150 orderMunicipality" /></td>
				</tr>
				<tr>
					<td>市区町村以降</td>
					<td><nested:text property="orderAddress" styleClass="text_w250 orderAddress" /></td>
				</tr>
				<tr>
					<td>建物名</td>
					<td><nested:text property="orderBuildingNm" styleClass="text_w200 orderBuildingNm" /></td>
				</tr>
				<tr>
					<td>会社名</td>
					<td><nested:text property="orderCompanyNm" styleClass="text_w200 orderCompanyNm" /></td>
				</tr>
				<tr>
					<td>部署名</td>
					<td><nested:text property="orderQuarter" styleClass="text_w200 orderQuarter" /></td>
				</tr>
				<tr>
					<td colspan="2"><hr class="line"></td>
				</tr>
				<tr>
					<td>決済方法</td>
					<td><nested:select property="accountMethod">
						<html:optionsCollection property="accountMethodMap" label="value" value="key"/>
					</nested:select></td>
<%-- 					<td><nested:text property="accountMethod" value="クレジットカード" styleClass="w120" /></td> --%>
				</tr>
				<tr>
					<td>決済手数料</td>
					<td><nested:text property="accountCommission" styleClass="price_w100 priceTextMinus" maxlength="9" /></td>
				</tr>
				<tr>
					<td>入金日</td>
					<td><nested:text property="depositDate" styleClass="calender" maxlength="10"/></td>
				</tr>
				<tr>
					<td>商品種別</td>
					<td><nested:select property="etcStr">
						<html:optionsCollection property="etcPriceMap" label="value" value="key" />
					</nested:select></td>
				</tr>
				<tr>
					<td>商品種別（金額）</td>
					<td><nested:text property="etcPrice" styleId="etcPrice" styleClass="price_w100 priceTextMinus" maxlength="9" /></td>
				</tr>
				<tr>
					<td>会員番号</td>
					<td><nested:text property="menberNo" styleClass="w200" maxlength="20" /></td>
				</tr>
			</table>
		</fieldset>

			<fieldset class="remarks_area">
			<legend>一言メモ/備考(注文)</legend>
				<div class="remarks">
					<nested:textarea rows="5" cols="60" style="width: 430px;" property="orderRemarksMemo"/>
				</div>
			</fieldset>
	</div>

		<fieldset class="destination_area">
		<legend>届け先情報</legend>
			<table>
				<tr>
					<td></td>
					<td><a class="copy" href="javascript:void(0)" >注文者情報をコピーする</a></td>
				</tr>
				<tr>
					<td>届け先名</td>
					<td><nested:text property="destinationFamilyNm" styleClass="text_w150 destinationFamilyNm" maxlength="50" />&nbsp;<nested:text property="destinationFirstNm" styleClass="text_w150 destinationFirstNm" maxlength="50" /></td>
				</tr>
				<tr>
					<td>届け先名カナ</td>
					<td><nested:text property="destinationFamilyNmKana" styleClass="text_w150 destinationFamilyNmKana" maxlength="50" />&nbsp;<nested:text property="destinationFirstNmKana" styleClass="text_w150 destinationFirstNmKana" maxlength="50" /></td>
				</tr>
				<tr>
					<td>届け先TEL</td>
					<td><nested:text property="destinationTel" styleClass="text_w150 destinationTel" maxlength="13" /></td>
				</tr>
				<tr>
					<td colspan="2"><hr class="line"></td>
				</tr>
				<tr>
					<td>郵便番号</td>
					<td>〒&nbsp;<nested:text property="destinationZip" styleClass="text_w80 destinationZip" maxlength="8" onkeyup=
						"AjaxZip3.zip2addr('salesSlipDTO.destinationZip', '', 'salesSlipDTO.destinationPrefectures', 'salesSlipDTO.destinationMunicipality', 'salesSlipDTO.destinationAddress');" /></td>
				</tr>
				<tr>
					<td>都道府県</td>
					<td><nested:text property="destinationPrefectures" styleClass="text_w100 destinationPrefectures" maxlength="10" /></td>
				</tr>
				<tr>
					<td>市区町村</td>
					<td><nested:text property="destinationMunicipality" styleClass="text_w150 destinationMunicipality" /></td>
				</tr>
				<tr>
					<td>市区町村以降</td>
					<td><nested:text property="destinationAddress" styleClass="text_w250 destinationAddress" /></td>
				</tr>
				<tr>
					<td>建物名</td>
					<td><nested:text property="destinationBuildingNm" styleClass="text_w200 destinationBuildingNm" /></td>
				</tr>
				<tr>
					<td>会社名</td>
					<td><nested:text property="destinationCompanyNm" styleClass="text_w200 destinationCompanyNm" /></td>
				</tr>
				<tr>
					<td>部署名</td>
					<td><nested:text property="destinationQuarter" styleClass="text_w200 destinationQuarter" /></td>
				</tr>
			</table>
		</fieldset>

		<fieldset class="delivery_area">
		<legend>配送情報</legend>
			<table>
				<tr>
					<td>伝票番号</td>
					<td><nested:text property="slipNo" styleClass="text_w200" maxlength="30" /></td>
				</tr>
				<tr>
					<td>運送会社</td>
						<td><nested:select property="transportCorporationSystem">
							<html:optionsCollection property="transportCorporationSystemMap" label="value" value="key"/>
						</nested:select>
					</td>
				</tr>
				<tr>
					<td>送り状種別</td>
					<td><nested:select property="invoiceClassification">
							<html:optionsCollection property="invoiceClassificationMap" label="key" value="key"/>
						</nested:select>
					</td>
				</tr>
				<tr>
					<td>代引き金額</td>
<!-- 					<td><input type="text" class="text_w100" maxlength="9" /></td> -->
					<td><nested:text property="cashOnDeliveryCommission" styleClass="price_w100 priceTextMinus" /></td>
				</tr>
				<tr>
					<td>配送指定日</td>
					<td><nested:text property="destinationAppointDate" styleClass="calender" maxlength="10" /></td>
				</tr>
				<tr>
					<td>時間帯指定</td>
					<td><nested:text property="destinationAppointTime" styleClass="text_w120" maxlength="8" /></td>
				</tr>
				<tr>
					<td>出荷予定日</td>
					<td><nested:text property="shipmentPlanDate" styleClass="calender" maxlength="10" /></td>
				</tr>
				<tr>
					<td>出荷日</td>
					<td><nested:text property="shipmentDate" styleClass="calender" styleId="shipmentDate" maxlength="10" /></td>
				</tr>
			</table>
		</fieldset>

		<fieldset class="memo_area">
			<legend>一言メモ(届け先)</legend>
				<div class="senderMemo">
					<nested:textarea rows="2" cols="60" style="width: 290px;" property="senderMemo"/>
				</div>
		</fieldset>

		<fieldset class="item_area">
		<legend>商品情報</legend>
			<table class="itemTable">
				<tr>
					<th colspan="2" class="w100"></th>
					<th id="code">品番</th>
					<th id="item_nm"><label class="necessary">※</label>&nbsp;商品名</th>
					<th id="stock_num">在庫数</th>
					<th id="order_num"><label class="necessary">※</label>&nbsp;注文数</th>
					<th id="price">単価</th>
					<th id="cost">原価</th>
				</tr>
<!-- 				表示用 -->
				<logic:notEmpty name="saleForm" property="salesItemList">
				<logic:iterate id="salesItemList" name="saleForm" property="salesItemList" indexId="idx" >

					<!-- 		マスタにない商品の色変え -->
					<logic:equal name="saleForm" property="salesItemList[${idx}].sysItemId" value="0">
						<bean:define id="backgroundColor" value="#FFFFC0" />
					</logic:equal>
					<logic:notEqual name="saleForm" property="salesItemList[${idx}].sysItemId" value="0">
						<bean:define id="backgroundColor" value="#FFFFFF" />
	<%-- 					<bean:define id="disabled" value="true" /> --%>
					</logic:notEqual>

					<html:hidden property="salesItemList[${idx}].sysSalesItemId" />
					<html:hidden property="salesItemList[${idx}].sysItemId" />
	<%-- 				<html:hidden property="salesItemList[${idx}].sysItemId" /> --%>
					<tr style="background-color: ${backgroundColor};" class="salesItemRow">
						<td><html:checkbox property="salesItemList[${idx}].returnFlg" styleClass="changeColorCheck returnFlg"/></td>
						<td></td>
						<td><bean:write name="saleForm" property="salesItemList[${idx}].itemCode" /></td>
						<td class="itemNmWidth"><bean:write name="saleForm" property="salesItemList[${idx}].itemNm" /></td>
						<td style="font-size: 18px;"><bean:write name="saleForm" property="salesItemList[${idx}].totalStockNum" />&nbsp;(<bean:write name="saleForm" property="salesItemList[${idx}].temporaryStockNum" />)</td>
						<td><html:text property="salesItemList[${idx}].orderNum" styleClass="num orderNum numText" maxlength="4" /></td>
						<td><html:text property="salesItemList[${idx}].pieceRate" styleClass="price_w80 pieceRate priceTextMinus price_cost_area" maxlength="9"/></td>
	<%-- 					<td><html:text property="salesItemList[${idx}].cost" styleClass="price_w80 cost priceTextMinus" disabled="${disabled}" maxlength="9"/></td> --%>
	
						<logic:notEqual name="saleForm" property="salesItemList[${idx}].updatedFlag" value="1">
							<td><html:text property="salesItemList[${idx}].domeCost" styleClass="price_w80 cost priceTextMinus price_cost_area" maxlength="9"/></td>
						</logic:notEqual>
						<logic:equal name="saleForm" property="salesItemList[${idx}].updatedFlag" value="1">
							<td><html:text property="salesItemList[${idx}].cost" styleClass="price_w80 cost priceTextMinus price_cost_area" maxlength="9"/></td>
						</logic:equal>
						<td></td>
					</tr>
					<tr>
						<td colspan="9" class="h10"></td>
					</tr>
				</logic:iterate>
				</logic:notEmpty>
<!-- 				追加用 -->
				<logic:notEmpty name="saleForm" property="addSalesItemList">
				<logic:iterate id="addSalesItemList" name="saleForm" property="addSalesItemList" indexId="idx" >
				<tr class="addSalesItemRow itemSearchRow">
					<html:hidden property="addSalesItemList[${idx}].sysItemId" styleClass="addSysItemId" />
					<td><input type="checkbox" class="changeColorCheck"/></td>
					<td class="pdg_left_10px"><a href="Javascript:void(0);" class="button_small_main searchItem">商品検索</a></td>
					<td><html:text property="addSalesItemList[${idx}].itemCode" style="min-width:120px; max-width:120px;" styleClass="addItemCode" maxlength="30" /></td>
					<td><html:textarea property="addSalesItemList[${idx}].itemNm" style="min-width:250px; max-width:250px;" styleClass="addItemNm"
								cols="30" rows="1" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
								onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);">
						</html:textarea>
					</td>
					<td>
						<span style="font-size: 18px;" class="addTotalStockNum">
							<bean:write name="saleForm" property="addSalesItemList[${idx}].totalStockNum" />
						</span>
						<span style="font-size: 18px;" class="addTemporaryStockNum">
							(<bean:write name="saleForm" property="addSalesItemList[${idx}].temporaryStockNum" />)
						</span>
					</td>
					<td><html:text property="addSalesItemList[${idx}].orderNum" styleClass="num addOrderNum numText" maxlength="4" /></td>
					<td><html:text property="addSalesItemList[${idx}].pieceRate" styleClass="price_w80 addPieceRate priceTextMinus price_cost_area" maxlength="9"/></td>
					<td><html:text property="addSalesItemList[${idx}].cost" styleClass="price_w80 addCost priceTextMinus price_cost_area" maxlength="9"/></td>
					<td class="tdButton"><a href="Javascript:void(0);" class="button_small_white removeSalesItem">削除</a></td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
			</table>
		</fieldset>

		<fieldset class="price_area">
		<legend>金額情報</legend>
			<table>
				<tr>
					<td>小計:<nested:text property="sumPieceRate" styleId="sumPieceRate" styleClass="price_w100 priceTextMinus" maxlength="9" />&nbsp;円</td>
					<td class="pdg_left_20px">消費税:</td>
					<td><nested:text property="tax" styleId="tax" styleClass="price_w80 priceTextMinus" maxlength="9" disabled="disabled"/>&nbsp;円</td>
					<td class="pdg_left_20px priceTextMinus">代引手数料:</td>
					<td><nested:text property="codCommission" styleId="codCommission" styleClass="price_w80 priceTextMinus" maxlength="9" />&nbsp;円</td>
					<td class="pdg_left_20px priceTextMinus">送料:</td>
					<td><nested:text property="postage" styleId="postage" styleClass="price_w80 priceTextMinus" maxlength="9" />&nbsp;円</td>
				</tr>
				<tr>
					<td class="pdg_top_20px">合計:<nested:text property="sumClaimPrice" styleId="sumClaimPrice" styleClass="price_w100 priceTextMinus" maxlength="9" />&nbsp;円</td>
					<td class="pdg_top_20px" style="text-align: right;"><nested:select property="taxClass" styleId="taxClass">
						<html:optionsCollection property="taxMap" label="value" value="key"/>
					</nested:select></td>
					<td class="pdg_top_20px" style="text-align: center;"><a href="Javascript:void(0);" class="button_small_main taxCalc">税計算</a></td>
					<td class="pdg_top_20px pdg_left_20px" colspan="2">使用ポイント:<nested:text property="usedPoint" styleId="usedPoint" styleClass="price_w80" />&nbsp;pt</td>
					<td class="pdg_top_20px pdg_left_20px" colspan="2">粗利:<nested:text property="grossMargin" styleClass="price_w80" />&nbsp;円</td>
				</tr>
				<tr>
					<td colspan="2" class="pdg_top_10px"><span class="explain">※内税・・・小計に含まれている消費税を計算</span></td>
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
					<nested:equal value="0" property="salesSlipDTO.sysSalesSlipId">
						<a class="button_main registry" href="javascript:void(0)" >登録</a>
					</nested:equal>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="salesSlipDTO.sysSalesSlipId">
						<a class="button_main update" href="javascript:void(0)" >修正</a>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<logic:notEqual value="0" name="saleForm" property="returnButtonFlg">
						<a class="button_white" href="javascript:void(0)" onclick="goTransaction('initSaleList.do');">一覧に戻る</a>
					</logic:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="salesSlipDTO.sysSalesSlipId">
						<a class="button_white" id="delete" href="javascript:void(0)">削除</a>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="salesSlipDTO.sysSalesSlipId">
						<a class="button_main copySlip" href="javascript:void(0)" >複製</a>
					</nested:notEqual>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="salesSlipDTO.sysSalesSlipId">
						<a class="button_return" id="returnSlip" href="javascript:void(0)">返品</a>
					</nested:notEqual>
				</li>
			</ul>
		</footer>

	</html:form>
</html:html>