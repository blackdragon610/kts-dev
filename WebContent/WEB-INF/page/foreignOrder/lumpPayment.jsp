<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/lumpPayment.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【一括支払画面】
ファイル名：lumpPayment.jsp
作成日：2017/01/18
作成者：白井崇詞

（画面概要）

・海外注文一覧で検索された伝票の支払日情報を表示し、
一括で伝票に登録することができる


・一括支払ボタン押下：入力されている支払情報を伝票に登録する。

（注意・補足）

-->


	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="lumpPayment">
	<html:hidden property="messageFlg" styleId="messageFlgForm"/>

		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: center;"><nested:write property="message"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>

		<h4 class="heading">一括支払画面</h4>

		<nested:notEmpty property="errorMessageDTO">
			<nested:nest property="errorMessageDTO">
				<nested:notEmpty property="errorMessage">
					<p id="errorMessage"><nested:write property="errorMessage" /></p>
				</nested:notEmpty>
			</nested:nest>
		</nested:notEmpty>

		<div id="list_area">
			<table id="list_table">
				<tr>
					<th class="index"></th>
					<th class="poNo">PoNo.</th>
					<th class="date">注文日</th>
					<th class="date">納期１</th>
					<th class="date">納期2</th>
<!-- 					<th class="date">入荷日</th> -->
					<th class="payment">支払1<input type="text" class="calender paymentDateAll" id="payment1DateAll" size="8" placeholder="一括設定"></th>
					<th class="payment">支払2<input type="text" class="calender paymentDateAll" id="payment2DateAll" size="8" placeholder="一括設定"></th>
				</tr>

<!-- 				<tr> -->
<!-- 					<td colspan="7" /> -->
<!-- 					<td><input type="text" class="calender paymentDateAll" id="payment1DateAll" size="8" placeholder="一括設定"></td> -->
<!-- 					<td><input type="text" class="calender paymentDateAll" id="payment2DateAll" size="8" placeholder="一括設定"></td> -->
<!-- 				</tr> -->

				<nested:iterate property="lumpPaymentList" indexId="idx">
					<tr>
						<nested:hidden property="sysForeignSlipId"/>
						<td class="w20"><%= idx + 1 %></td>
						<td><nested:write property="poNo"/></td>
						<td><nested:write property="orderDate"/></td>
						<td><nested:write property="deliveryDate1" /></td>
						<td><nested:write property="deliveryDate2" /></td>
<%-- 						<td><nested:write property="arrivalScheduleDate"/></td> --%>
						<td><nested:text property="paymentDate1" styleClass="calender payment1Date" styleId="payment1Date${idx}"/></td>
						<td><nested:text property="paymentDate2" styleClass="calender payment2Date" styleId="payment2Date${idx}"/></td>
					</tr>
				</nested:iterate>
			</table>
		</div>

		<div class="paging_area">
<!-- 			<div class="paging_total"> -->
<!-- 					<h3>全100件（1/8ページ）</h3> -->
<!-- 			</div> -->
<!-- 			<div class="paging_num"> -->
<!-- 				<ul class="pager fr mb10"> -->
<!-- 				    <li class="disabled"><a href="#">&laquo;</a></li> -->
<!-- 				    <li class="current"><span>1</span></li> -->
<!-- 				    <li><a href="javascript:void(0);" >2</a></li> -->
<!-- 				    <li><a href="javascript:void(0);" >3</a></li> -->
<!-- 				    <li><a href="javascript:void(0);" >4</a></li> -->
<!-- 				    <li><a href="javascript:void(0);" >5</a></li> -->
<!-- 				    <li><a href="javascript:void(0);" >6</a></li> -->
<!-- 				    <li><a href="javascript:void(0);" >7</a></li> -->
<!-- 				    <li><a href="javascript:void(0);" >8</a></li> -->
<!-- 				    <li><a href="javascript:void(0);" >&raquo;</a></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->

			<div class="button_area">
				<ul style="position: relative;">
					<li class="footer_button">
						<a class="button_white" id="returnForeignOrderList" href="javascript:void(0);">一覧に戻る</a>
					</li>
					<li class="footer_button">
						<a class="button_main" href="Javascript:void(0);" id="lumpPayment">一括支払</a>
					</li>
				</ul>
			</div>
		</div>

	</html:form>

	<script type="text/javascript">
	$(function() {

		$('.calender').datepicker();
		$('.calender').datepicker("option", "showOn", 'button');
		$('.calender').datepicker("option", "buttonImageOnly", true);
		$('.calender').datepicker("option", "buttonImage", './img/calender_icon.png');

		//支払日1の一括設定を行う処理
		$("#payment1DateAll").change(function() {

			 $(".payment1Date").each(function () {
				$(this).val($("#payment1DateAll").val());
			 });
		});

		//支払日2の一括設定を行う処理
		$("#payment2DateAll").change(function() {

			 $(".payment2Date").each(function () {
				$(this).val($("#payment2DateAll").val());
			 });
		});

		$("#lumpPayment").click (function () {

			if (!confirm("伝票を更新します。よろしいですか？")) {
				return;
			}

			goTransaction("lumpPayment.do");
		});

		//一覧に戻るボタン押下時の処理
		$("#returnForeignOrderList").click (function () {
			goTransaction('returnForeignOrderList.do');
		});

		//メッセージエリア表示設定
		if(!$("#registryDto.message").val()) {
			if ($("#messageFlg").val() == "0") {
				$("#messageArea").addClass("registrySuccess");
			}
			if ($("#messageFlg").val() == "1") {
				$("#messageArea").addClass("registryFailure");
			}
			$("#messageArea").fadeOut(4000);
		}

	});

	</script>
</html:html>