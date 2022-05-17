<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/itemSearch.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【商品検索画面】
ファイル名：itemSearch.jsp
作成日：2014/12/10
作成者：八鍬寛之

（画面概要）

売上詳細・入荷予定一括登録画面から子画面として開く。
親画面で「商品検索」ボタンを押した際、品番と商品名に入力されている値を
引継ぎそのまま検索をかける。

・検索ボタン押下：品番・商品名を条件に検索を実行する。商品名は部分一致。
・選択ボタン押下：親画面に選択された商品の値を渡し、画面を閉じる。

（注意・補足）

-->

<script type="text/javascript">
$(function () {

	$(".select").click( function () {

		//親画面の商品エリア
		var itemArea = window.opener.$("tr.itemSearchRow");
		//親画面で押された検索ボタンインデックス
		var idx = $("#openerIdx").val();

		//選択ボタンが押された行のデータ
		var selectArea = $(this).parents("tr.itemSearchRow");

		if(0 < window.opener.$("#taxRate").size()) {

			if(window.opener.$("#taxClass").val() == "1") {
				//税率計算はここではしない
// 				var taxRate = new Number(window.opener.$("#taxRate").val());
// 				var price = new Number(selectArea.find(".price").val());
// 				var tax = new Number(0);
// 				tax = Math.floor(taxRate / 100 * price);
// 				price += tax;
// 				selectArea.find(".price").val(price);
			}
// 			var taxRate = new Number(window.opener.$("#taxRate").val());
// 			var price = new Number(selectArea.find(".price").val());

// 			var tax = new Number(0);

// 			tax = window.opener.taxCalc(price, window.opener.$("#taxClass").val(), taxRate);
// 			price += tax;

// 			selectArea.find(".price").val(price);
		}

// 		alert(selectArea.find(".itemCode").val());
		itemArea.eq(idx).find(".addItemCode").val(selectArea.find(".itemCode").val());
		itemArea.eq(idx).find(".addItemNm").val(selectArea.find(".itemNm").val());
		itemArea.eq(idx).find(".addSysItemId").val(selectArea.find(".sysItemId").val());
		itemArea.eq(idx).find(".addCost").val(selectArea.find(".cost").val());
		itemArea.eq(idx).find(".addPieceRate").val(selectArea.find(".price").val());
		itemArea.eq(idx).find(".addTotalStockNum").html(selectArea.find(".totalStockNum").val());
		itemArea.eq(idx).find(".addTemporaryStockNum").html('(' + selectArea.find(".temporaryStockNum").val() + ')');
		itemArea.eq(idx).find(".addTemporaryStockNum").val(selectArea.find(".temporaryStockNum").val());
		itemArea.eq(idx).find(".addAssemblyNum").html(selectArea.find(".assemblyNum").val());
		itemArea.eq(idx).find(".addOrderNum").val(0);
		itemArea.eq(idx).find(".scheduledLeavingDate").val("");
		itemArea.eq(idx).find(".subtotal").html("0");
		itemArea.eq(idx).find(".subtotalWithTax").html("0");
		itemArea.eq(idx).find(".addPickingFlg").prop("checked", false);
		itemArea.eq(idx).find(".addBillingFlag").prop("checked", false);


 		window.close();

	});

	if($("#itemListSize").val() == '1') {

		$(".select").click();
	}

	$(".search").click( function () {

		goTransaction("subWinItemSearch.do");
	});
});
</script>
	</head>
	<nested:form action="/subWinItemSearch"	>
	<h4 class="head">商品検索</h4>

	<div class="searchOptionArea">
		<table class="searchOptionTable">
		<nested:nest property="searchItemDTO">
			<nested:hidden property="openerIdx" styleId="openerIdx" />
			<tr>
				<td rowspan="2">品番</td>
				<td><nested:text property="itemCode" styleClass="text_w120 checkAlnumHyp" maxlength="11" />&nbsp;<span class="explain">※前方一致検索</span></td>
				<td rowspan="2" style="padding-left: 20px;">商品名</td>
				<td rowspan="2"><nested:text property="itemNm" styleClass="text_w200" /></td>
			</tr>
			<tr>
				<td><nested:text property="itemCodeFrom" styleClass="text_w120 numLengthCheck" maxlength="11" />&nbsp;～&nbsp;<nested:text property="itemCodeTo" styleClass="text_w120 numLengthCheck" maxlength="11"/>
					<br/><span class="explain">※入力する場合は11桁必須</span>
				</td>
			</tr>
		</nested:nest>
		</table>
	</div>

	<nested:hidden property="itemListSize" styleId="itemListSize"/>
	<div class="paging_num">
		<h3>全<nested:write property="itemListSize" />件</h3>
		<div class="searchButton"><a class="button_main search" href="Javascript:void(0);">検索</a></div>
	</div>

	<div class="out_Div">
		<div class="in_Div">
			<table class="list_table">
				<thead>
					<tr>
						<th class="wCode">品番</th>
						<th class="wNm">商品名</th>
						<th class="wNum">在庫数</th>
						<th class="wNum">種別</th>
						<th class="wButton"></th>
					</tr>
				</thead>
				<nested:iterate property="searchItemList" indexId="idx" id="searchItemList" >
				<tr class="itemSearchRow">
				<nested:hidden property="sysItemId" styleClass="sysItemId"/>
				<nested:hidden property="itemCode" styleClass="itemCode" />
				<nested:hidden property="itemNm" styleClass="itemNm" />
				<nested:hidden property="totalStockNum" styleClass="totalStockNum" />
				<nested:hidden property="temporaryStockNum" styleClass="temporaryStockNum" />
				<nested:hidden property="assemblyNum" styleClass="assemblyNum" />
				<nested:hidden property="cost" styleClass="cost"/>
				<nested:hidden property="price" styleClass="price"/>
					<td class="wListCode"><nested:write property="itemCode"/></td>
					<td class="wListNm"><nested:write property="itemNm"/></td>
					<td class="wListNum"><nested:write property="totalStockNum"/>&nbsp;(<nested:write property="temporaryStockNum"/>)</td>
					<td class="wListNum"><nested:write property="itemType"/></td>
					<td class="wListButton"><a class="button_small_main select" href="Javascript:void(0);">選択</a></td>
				</tr>
				</nested:iterate>
			</table>
		</div>
	</div>

	</nested:form>
</html:html>