<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/leaveStock.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【出庫処理画面】
ファイル名：leaveStock.jsp
作成日：2014/12/26
作成者：八鍬寛之

（画面概要）

売上一覧で表示されているページの商品をリスト化し、出庫数を指定して一括で在庫を減らす
出庫数の初期値は売上伝票の注文数

・出庫ボタン押下：在庫数の項目のカッコ内「仮在庫数」と出庫数を比較していき、
　出庫が可能であれば実際に減らしていく。もし出庫できない場合はエラーを表示する。
　ロールバックはしないので、エラーが出なかったものはそのまま在庫が減っている。

（注意・補足）

-->

	<script type="text/javascript">

	$(document).ready(function(){
		$(".overlay").css("display", "none");
	 });

	$(function() {

		$('.num').each (function () {
			$(this).spinner( {
				max: $(this).val(),
				min: 0,
				step: 1
			});
		});
// 		$('.num').spinner( {
// 			max: 9999,
// 			min: 0,
// 			step: 1
// 		});

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			alert('在庫数を更新しました。「戻る」ボタンを押してください。');
			document.getElementById('alertType').value = '0';
		}

		$("#leaveStock").click (function () {

			if (!confirm("在庫に反映させます。よろしいですか？")) {
				return;
			}

			$(".overlay").css("display", "block");

			var arrSysItemId = new Array(20);
// 			var[] arrSysItemId = {0,0,0,0,0,0,0,0,0,0};
			$(".leaveStockRow").each (function (i) {

				arrSysItemId[i] = $(this).find(".sysItemId").val();

				$(this).find(".sysItemId").val();
				$(this).find(".itemCode").val();
				$(this).find(".firstWarehouseStockNum").val();
			});

			goTransaction('corporateLeaveStock.do');
		});

	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="corporateLeaveStock">

	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="heading">出庫処理画面</h4>

<!-- エラーメッセージ -->
	<div id="errorArea">
	<nested:nest property="errorDTO">
		<nested:iterate property="errorMessageList">
		<p class="errorMessage"><nested:write property="errorMessage"/></p>
		</nested:iterate>
	</nested:nest>
	</div>
<!-- エラーメッセージ -->

	<nested:notEmpty property="leaveStockList" >
	<div id="list_area">
		<table id="list_table">
			<tr>
				<th class="index"></th>
<!-- 				<th class="saleNum">(開発テスト)受注日</th> -->
				<th class="saleNum">受注番号</th>
				<th class="orderNm">注文者名</th>
				<th class="itemCode">品番</th>
				<th class="itemNm">商品名</th>
				<th class="warehouse">倉庫</th>
				<th class="shipmentPlanDate">出荷予定日</th>
				<th class="stockNum">在庫数</th>
				<th class="orderNum">注文数</th>
				<th class="leavingNum">出庫済</th>
				<th class="leavingNum">出庫数</th>
			</tr>
			<nested:iterate property="leaveStockList" indexId="idx">
				<logic:equal name="indexId" value="0"></logic:equal>
				<tr class="leaveStockRow">
					<nested:hidden property="itemCode" styleClass="itemCode" />
					<nested:hidden property="sysItemId" styleClass="sysItemId"/>
					<nested:hidden property="firstWarehouseStockNum" styleClass="firstWarehouseStockNum"/>
					<nested:hidden property="leaveClassFlg" />
					<nested:hidden property="sysCorporateSalesSlipId"/>
					<nested:hidden property="orderName" />
					<td><%= idx + 1 %></td>
<%-- 					<td class="saleNum"><nested:write property="orderDate" /> --%>
<%-- 						<nested:write property="orderTime" /></td> --%>
					<td class="saleNum"><nested:write property="orderNo" /></td>
					<td class="orderNm"><nested:write property="orderName" /></td>
					<td class="itemCode"><nested:write property="itemCode" /></td>
					<td class="itemNmTd"><nested:write property="itemNm" /></td>
					<td class="warehouse"><nested:write property="warehouseNm" /></td>
					<td class="shipmentPlanDate"><nested:write property="scheduledLeavingDate" /></td>
					<td class="stockNum"><nested:write property="totalStockNum" />(<nested:write property="temporaryStockNum" />)</td>
					<td class="orderNum"><nested:write property="orderNum" /></td>
					<td class="leavingNum"><nested:write property="leavingNum" /></td>
					<td class="leavingNum"><nested:text property="leaveNum" styleClass="num numText" readonly="true" maxlength="4" /></td>
				</tr>
			</nested:iterate>

		</table>
	</div>
	</nested:notEmpty>

	<div class="paging_area">
		<div class="button_area">
			<a class="button_main" href="javascript: void(0);" id="leaveStock" >出庫</a>
			<a class="button_white" href="javascript: void(0);" onclick="goTransaction('corporateSaleListPageNo.do');">戻る</a>
		</div>
	</div>
	</nested:form>

	<div class="overlay">
		<div class="messeage_box">
			<h1 class="message">処理中</h1>
			<BR />
			<p>Now Loading...</p>
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>
</html:html>