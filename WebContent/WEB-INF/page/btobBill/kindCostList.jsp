<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/kindCostList.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【Kind原価一覧画面】
ファイル名：kindCostList.jsp
作成日：2015/12/16
作成者：大山智史

（画面概要）

商品マスタの検索/一覧画面。

・検索条件：商品情報で絞り込みが可能。検索結果一覧の表示件数も変更可能。
・検索結果：商品情報の一覧表示。在庫数を一括で変更することができる。

・検索ボタン押下：設定された絞り込み項目をもとに検索処理を実行する。
・リセットボタン押下：設定された絞り込み項目を全て空にする。
・行をダブルクリックまたは品番リンクをクリック：対象データの商品詳細画面に遷移する。

（注意・補足）

-->

	<script type="text/javascript">



	$(document).ready(function(){
		$(".overlay").css("display", "none");

		$("body").keydown( function(event) {
			if(event.which === 13){
				$(".search").click();
			}
		});
	 });


	$(function() {
		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1
		});

		 $(".clear").click(function(){
	        $("#search_option input, #search_option select").each(function(){
	            if (this.type == "checkbox" || this.type == "radio") {
	                this.checked = false;
	            } else {
	                $(this).val("");
	            }
	        });
	    });

		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');


		$('#searchOptionOpen').click(function () {

			if($('#searchOptionOpen').text() == "▼絞り込み") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼絞り込み");
			}

			$('#searchOptionArea').slideToggle("fast");
		});

		//検索ボタン
		$(".search").click( function () {

			if ($("#itemOrderDateFrom").val() && $("#itemOrderDateTo").val()){
				fromAry = $("#itemOrderDateFrom").val().split("/");
				toAry = $("#itemOrderDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("発注日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#itemCodeFrom").val() && $("#itemCodeTo").val()) {
				if ($("#itemCodeFrom").val() > $("#itemCodeTo").val()) {
					alert("品番 の出力開始番号が、出力終了番号より大きい値になっています。");
					return false;
				}
			}

			if ($("#arrivalScheduleDateFrom").val() && $("#arrivalScheduleDateTo").val()){
				fromAry = $("#arrivalScheduleDateFrom").val().split("/");
				toAry = $("#arrivalScheduleDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("入荷予定日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#backOrderDateFrom").val() && $("#backOrderDateTo").val()){
				fromAry = $("#backOrderDateFrom").val().split("/");
				toAry = $("#backOrderDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("ＢＯ日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#costFrom").val() && $("#costTo").val()) {
				if ($("#costFrom").val() > $("#costTo").val()) {
					alert("原価 の検索開始金額が、検索終了金額より大きい額になっています。");
					return false;
				}
			}



			$(".overlay").css("display", "block");

			goTransaction("kindCostList.do");
		});

		//商品詳細
		$(".itemListRow").click( function () {

			$("#sysItemId").val($(this).find(".sysItemId").val());

 				goTransaction("detailKindCost.do");
		});

		//発注アラートの行色変え
		for(i = 0; i < $(".count").length; i++) {
			var totalStockNumBase = new Number($(".totalStockNumBase").eq(i).val());
			var orderAlertNum = new Number($(".orderAlertNum").eq(i).val());
			if(orderAlertNum >= totalStockNumBase) {
				$(".rowNum").eq(i).css("background-color", "#FFE0E0");
			}
		}

		//ページ送り
		if ($("#sysItemIdListSize").val() != 0) {
			var itemPageNum = Math.ceil($("#sysItemIdListSize").val() / $("#itemListPageMax").val());

			$(".itemPageNum").text(itemPageNum);
			$(".itemNowPage").text(Number($("#pageIdx").val()) + 1);
			var pageIdx = new Number($("#pageIdx").val());

				if (0 == pageIdx) {
					$(".pager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
					$(".underPager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
				}

			var startIdx;
			// maxDispは奇数で入力
			var maxDisp = 7;
			// itemPageNumがmaxDisp未満の場合maxDispの値をitemPageNumに変更
			if (itemPageNum < maxDisp) {

				maxDisp = itemPageNum;

			}

			var center = Math.ceil(Number(maxDisp) / 2);

			// 現在のページが中央より左に表示される場合
			if (center >= pageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(itemPageNum);
				$(".liFirstPage").hide();

			// 現在のページが中央より右に表示される場合
			} else if (itemPageNum - (center - 1) <= pageIdx + 1) {

				startIdx = itemPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

			// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#pageIdx").val() - (center - 2);
				$(".lastIdx").html(itemPageNum);

			}

			//表示の初期値を設定
			$(".pageNum").html(startIdx);
			//endIdx設定
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

 				$(".3dotLineTop").hide();

 			}

			if ((itemPageNum <= 8) || ((itemPageNum - center) <= (pageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (itemPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}


			var i;
			for (i = startIdx; i < endIdx && i < itemPageNum; i++) {
				var clone = $(".pager > li:eq(3)").clone();
				clone.children(".pageNum").text(i + 1);

				if (i == $("#pageIdx").val()) {

					clone.find("a").attr("class", "pageNum nowPage");

				} else {
					clone.find("a").attr("class", "pageNum");
				}

 				$(".pager > li.3dotLineEnd").before(clone);
			}
		}

		$(".pageNum").click (function () {

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val($(this).text() - 1);
			goTransaction("kindCostListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
			goTransaction("kindCostListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("kindCostListPageNo.do");
		});

		//先頭ページへ
		$("#firstPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(0);

			goTransaction("kindCostListPageNo.do");
		});

		//最終ページへ
		$("#lastPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);

			goTransaction("kindCostListPageNo.do");
		});

//ページ送り（下側）
//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);

			goTransaction("kindCostListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("kindCostListPageNo.do");
		});

		//先頭ページへ
		$("#underFirstPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(0);
			goTransaction("kindCostListPageNo.do");
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);
			goTransaction("kindCostListPageNo.do");
		});

		// ダブルクリックで商品詳細遷移
		$(".salesSlipRow").dblclick(function () {
			var tr = $(this).parents("tr");

			$("#sysItemId").val(tr.find(".sysItemId").val());


 					goTransaction("detailKindCost.do");


		});

	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initKindCostList">

	<input type="hidden" name="sysItemId" id="sysItemId" />
	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="headingDetail">Kind原価一覧</h4>

	<html:hidden property="alertType" styleId="alertType"></html:hidden>

		<nested:notEmpty property="errorMessageDTO">
			<div id="errorArea">
				<nested:nest property="errorMessageDTO">
					<p class="errorMessage"><nested:write property="errorMessage"/></p>
				</nested:nest>
			</div>
		</nested:notEmpty>

		<fieldset class="searchOptionField">
		<legend id="searchOptionOpen">▲隠す</legend>
		<div id="searchOptionArea">
		<nested:nest property="searchItemDTO" >
		<table id="search_option">
			<tr>
				<td rowspan="2">品番</td>
				<td><nested:text property="itemCode" styleClass="text_w120 numText" maxlength="11" />&nbsp;<span class="explain">※前方一致検索</span></td>
				<td style="padding-left: 20px;">発注日</td>
				<td><nested:text property="itemOrderDateFrom" styleId="itemOrderDateFrom" styleClass="calender" maxlength="10" />&nbsp;～&nbsp;<nested:text property="itemOrderDateTo" styleId="itemOrderDateTo" styleClass="calender" maxlength="10" /></td>
<%-- 				<td style="padding-left: 20px;"><label><nested:checkbox property="backOrderFlg" />BO有</label></td> --%>
				<td style="padding-left: 20px;"><label><nested:checkbox property="keepFlg" />キープ有</label></td>
				<td><label><nested:checkbox property="orderAlertFlg" />発注アラート</label></td>
				<td style="padding-left: 20px;"><label><nested:checkbox property="overArrivalScheduleFlg" />入荷予定日超過</label></td>
			</tr>
			<tr>
				<td><nested:text property="itemCodeFrom" styleId="itemCodeFrom" styleClass="text_w120 numLengthCheck" maxlength="11" />&nbsp;～&nbsp;<nested:text property="itemCodeTo" styleId="itemCodeTo" styleClass="text_w120 numLengthCheck" maxlength="11" />
					<br/><span class="explain">※入力する場合は11桁必須</span></td>
				<td style="padding-left: 20px;">入荷予定日</td>
				<td><nested:text property="arrivalScheduleDateFrom" styleId="arrivalScheduleDateFrom" styleClass="calender" maxlength="10" />&nbsp;～&nbsp;<nested:text property="arrivalScheduleDateTo" styleId="arrivalScheduleDateTo" styleClass="calender" maxlength="10" /></td>
				<td style="padding-left: 20px;"><label><nested:checkbox property="setItemFlg" />セット商品</label></td>
				<td><label><nested:checkbox property="manualFlg" />説明書有</label></td>
				<td style="padding-left: 20px;"><label><nested:checkbox property="planSheetFlg" />図面有</label></td>
				<td style="padding-left: 20px;"><label><nested:checkbox property="otherDocumentFlg" />その他資料有</label></td>
			</tr>
			<tr>
				<td>商品名</td>
				<td><nested:text property="itemNm" styleClass="text_w265" /></td>
				<td style="padding-left: 20px;">BO日</td>
				<td><nested:text property="backOrderDateFrom" styleId="backOrderDateFrom" styleClass="calender" maxlength="10" />&nbsp;～&nbsp;<nested:text property="backOrderDateTo" styleId="backOrderDateTo" styleClass="calender" maxlength="10" /></td>
				<td colspan="2" style="padding-left: 20px;">並び順&nbsp;
					<nested:select property="sortFirst">
						<html:optionsCollection property="itemListSortMap" value="key" label="value" />
					</nested:select>
					<nested:select property="sortFirstSub">
						<html:optionsCollection property="itemListSortOrder" value="key" label="value" />
					</nested:select>
				</td>
				<td style="padding-left: 20px;">表示件数
					<nested:select property="listPageMax">
						<html:optionsCollection property="listPageMaxMap" value="key" label="value" />
					</nested:select>&nbsp;件
				</td>
			</tr>
			<tr>
				<td>倉庫</td>
				<td><nested:select property="sysWarehouseId">
						<html:option value="0">　</html:option>
						<html:optionsCollection property="warehouseList" label="warehouseNm" value="sysWarehouseId" />
					</nested:select>
				</td>
				<td style="padding-left: 20px;">原価</td>
				<td><nested:text property="costFrom" styleId="costFrom" styleClass="text_w130 priceTextMinusSearch" maxlength="9" />&nbsp;～&nbsp;<nested:text property="costTo" styleId="costTo" styleClass="text_w130 priceTextMinusSearch" maxlength="9" /></td>

			</tr>
			<tr>
				<td>ロケーションNo</td>
				<td><nested:text property="locationNo" styleClass="text_w150" maxlength="30" /></td>
				<td style="padding-left: 20px;">仕様メモ</td>
				<td><nested:text property="specMemo" styleClass="text_w285" /></td>
				<td class="td_center" style="padding-left: 20px;"><a class="button_main search" href="javascript:void(0);">検索</a></td>
				<td><a class="button_white clear" href="javascript:void(0);">リセット</a></td>
			</tr>
		</table>
		</nested:nest>
		</div>
		</fieldset>

	<nested:notEmpty property="itemList">


		<div class="paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysItemIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="itemNowPage" ></span>&nbsp;/&nbsp;<span class="itemPageNum"></span>&nbsp;ページ&nbsp;）</h3>
			</div>
			<div class="paging_num_top">
				<ul class="pager fr mb10">
				    <li class="backButton"><a href="javascript:void(0);" id="backPage" >&laquo;</a></li>
				    <li class="liFirstPage" ><a href="javascript:void(0);" id="firstPage" >1</a></li>
				    <li class="3dotLineTop"><span>...</span></li>
					<li><a href="javascript:void(0);" class="pageNum" ></a></li>
				  	<li class="3dotLineEnd"><span>...</span></li>
				    <li class="liLastPage" ><a href="javascript:void(0);" id="lastPage"  class="lastIdx" ></a></li>
				    <li class="nextButton"><a href="javascript:void(0);" id="nextPage" >&raquo;</a></li>
				</ul>
			</div>
		</div>

		<div id="list_area">

		<nested:hidden property="sysItemIdListSize" styleId="sysItemIdListSize" />
		<nested:hidden property="pageIdx" styleId="pageIdx" />
		<nested:hidden property="itemListPageMax" styleId="itemListPageMax" />
			<table class="list_table">
				<tr>
					<th id="code">品番</th>
					<th id="item">商品名</th>
					<th id="warehouse">倉庫<br/>ロケーションNo</th>
					<th id="stock">総在庫数</th>
					<th id="temporary">仮在庫数</th>
					<th id="orderDate">発注日</th>
					<th id="schedule_date">入荷予定日<br/>入荷予定数</th>
					<th id="price">Kind原価</th>
					<th id="bo_date">組立可数</th>
					<th id="memo">仕様メモ</th>
				</tr>

				<nested:notEmpty property="itemList">
				<nested:iterate property="itemList" indexId="idx">



				<nested:hidden property="orderAlertNum" styleClass="orderAlertNum"></nested:hidden>

				<tr class="change_color rowNum">

				<nested:hidden property="stockNum" styleClass="stockNum" />
				<nested:hidden property="totalStockNum" styleClass="totalStockNumBase" />
				<nested:hidden property="updateStockNum" styleClass="updateStockNum" />
				<nested:hidden property="itemCode" styleClass="itemCode" />
				<nested:hidden property="itemNm" styleClass="itemNm" />
				<nested:hidden property="sysWarehouseId"/>

					<td class="salesSlipRow"><a href="javascript:void(0);" class="itemListRow">
							<nested:hidden property="sysItemId" styleClass="sysItemId" />
							<nested:hidden property="setItemFlg" styleClass="setItemFlg"></nested:hidden>
							<nested:write property="itemCode" />
						</a>
					</td>
					<td class="salesSlipRow"><nested:write property="itemNm" /></td>

					<td class="salesSlipRow"><nested:write property="warehouseNm" /><br><nested:write property="locationNo" /></td>
					<td><nested:text property="totalStockNumInput" styleClass="num totalStockNumInput" maxlength="4" /></td>
					<td class="salesSlipRow"><nested:write property="temporaryStockNum" /></td>
					<td class="salesSlipRow"><nested:write property="itemOrderDate"/></td>
					<td class="salesSlipRow"><nested:write property="arrivalScheduleDate"/><br><span id="arrivalNumSpan"><nested:write property="arrivalNumDisp"/></span></td>
					<td class="salesSlipRow"><nested:write property="cost" format="###,###,###" /></td>
					<td class="salesSlipRow">
						<nested:equal value="1" property="setItemFlg">
							<nested:write property="assemblyNum" />
						</nested:equal>
					<%-- <nested:write property="backOrderDate" /><br><nested:write property="backOrderCountDisp" /> --%>
					</td>
					<td class="salesSlipRow"><nested:write property="specMemo" /></td>
				</tr>
				</nested:iterate>
				</nested:notEmpty>

			</table>
		</div>

		<!-- ******************************************************************************** -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysItemIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="itemNowPage" ></span>&nbsp;/&nbsp;<span class="itemPageNum"></span>&nbsp;ページ&nbsp;）</h3>
			</div>
			<div class="paging_num_top">
				<ul class="pager fr mb10 underPager">
				    <li class="backButton"><a href="javascript:void(0);" id="underBackPage" >&laquo;</a></li>
				    <li class="liFirstPage"><a href="javascript:void(0);" id="underFirstPage" >1</a></li>
				    <li class="3dotLineTop"><span>...</span></li>
					<li><a href="javascript:void(0);" class="pageNum" ></a></li>
				  	<li class="3dotLineEnd"><span>...</span></li>
				    <li class="liLastPage"><a href="javascript:void(0);" id="underLastPage"  class="lastIdx" ></a></li>
				    <li class="nextButton"><a href="javascript:void(0);" id="underNextPage" >&raquo;</a></li>
				</ul>
			</div>
		</div>
		<!-- ******************************************************************************** -->
	</nested:notEmpty>

	</html:form>

	<div class="overlay">
		<div class="messeage_box">
			<h1 class="message">検索中</h1>
			<BR />
			<p>Now Loading...</p>
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>
</html:html>