<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<title>入金管理</title>
	<link rel="stylesheet" href="./css/corporatePaymentManagement.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.ympicker.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【業販入金管理画面】
ファイル名：corporatePaymentManagement.jsp
作成日：2017/11/09
作成者：BONCRE 須田将規

（画面概要）

業販入金管理

・検索ボタン押下：絞り込み条件にしたがい業販請求書の入金テーブルから検索する

（注意・補足）

-->

	<script type="text/javascript">
	$(document).ready(function(){
		$(".overlay").css("display", "none");

		// 法人リンクの選択文字
		$("#corpLink<bean:write name="corporateSaleForm" property="dispSysCorporationId" />").removeAttr("href");
		$("#corpLink<bean:write name="corporateSaleForm" property="dispSysCorporationId" />").parents("li").attr("class", "corp");
		$("#corpLink<bean:write name="corporateSaleForm" property="dispSysCorporationId" />").attr("class", "selected");
	 });


	$(function() {

		$(".calendar").datepicker();
		$(".calendar").datepicker("option", "showOn", 'button');
		$(".calendar").datepicker("option", "buttonImageOnly", true);
		$(".calendar").datepicker("option", "buttonImage", './img/calender_icon.png');

		// start カレンダー機能(ympicker)の設定処理

		// ympickerの設定処理で入力値が初期化されてしまうので一度変数に再表示したい値を格納する。
		var expMonth = $("#exportMonth").val();
		var semFrom = $("#searchExportMonthFrom").val();
		var semTo = $("#searchExportMonthTo").val();

		$(".ymCalendar").ympicker({
			closeText:'閉じる'
			,prevText:'<前'
			,nextText:'次>'
			,currentText:'今日'
			,monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			,monthNamesShort:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			,dateFormat:'yy/mm'
			,yearSuffix:'年'
		});
		$(".ymCalendar").ympicker("option", "showOn", 'button');
		$(".ymCalendar").ympicker("option", "buttonImageOnly", true);
		$(".ymCalendar").ympicker("option", "buttonImage", './img/calender_icon.png');
		// ympickerの設定処理が終わったら再表示したい値を項目に設定する。
		$("#exportMonth").val(expMonth);
		$("#searchExportMonthFrom").val(semFrom);
		$("#searchExportMonthTo").val(semTo);
		//end



		$("#searchPaymentInfo").click(function(){

			if ($("#searchExportMonthFrom").val() != "") {
				if (!isYYYYMM($("#searchExportMonthFrom").val())) {
					alert("請求月の形式が違います。形式はYYYY/MMです");
					return;
				}
			}

			if ($("#searchExportMonthTo").val() != "") {
				if (!isYYYYMM($("#searchExportMonthTo").val())) {
					alert("請求月の形式が違います。形式はYYYY/MMです");
					return;
				}
			}

			if($("#clientNo").val() != "") {
				if(!$.isNumeric($("#clientNo").val())) {
					alert("請求先番号は数字を入力してください");
					return;
				}
			}

			if($("#searchBillingAmountFrom").val() != "") {
				if(!$.isNumeric($("#searchBillingAmountFrom").val())) {
					alert("合計請求金額は数字を入力してください");
					return;
				}
			}

			if($("#searchBillingAmountTo").val() != "") {
				if(!$.isNumeric($("#searchBillingAmountTo").val())) {
					alert("合計請求金額は数字を入力してください");
					return;
				}
			}

			$(".overlay").css("display", "block");
			goTransaction("searchPaymentManagement.do");

		});

		//日付がYYYY/MMの書式かを判別する関数
		function isYYYYMM(dateStr) {
			//入力された値がYYYY/MMの7文字であるかを判別
			if (dateStr.length != 7) {
				return false;
			}

			//年
			var year = dateStr.substr(0, 4);
			//スラッシュ
			var srash = dateStr.substr(4, 1);
			//月
			var month = dateStr.substr(5, 7);

			//年が数値がどうか判定
			if (!$.isNumeric(year)) {
				return false;
			}
			//スラッシュであるかどうか判定
			if (srash != "/") {
				return false;
			}
			//月が数値か判定
			if ( !$.isNumeric(month)) {
				return false;
			}
			//月が1月～12月の範囲内であるか判定
			if (month < 1 || month > 12) {
				return false;
			}

			return true;

		}

		//ページング********************************************************************************************

		// ページリストの表示
		if ($("#corporateBillListSize").val() != 0) {
			var slipPageNum = Math.ceil($("#corporateBillListSize").val() / $("#corporateBillListPageMax").val());

			$(".slipPageNum").text(slipPageNum);
			$(".slipNowPage").text(Number($("#corporateBillPageIdx").val()) + 1);

			var i;

			if (0 == $("#corporateBillPageIdx").val()) {
 				$(".pager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
 				$(".underPager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
			}

			var startIdx;
			// maxDispは奇数で入力
			var maxDisp = 7;
			// slipPageNumがmaxDisp未満の場合maxDispの値をslipPageNumに変更
			if (slipPageNum < maxDisp) {

				maxDisp = slipPageNum;

			}

			var center = Math.ceil(Number(maxDisp) / 2);
			var corporateBillPageIdx = new Number($("#corporateBillPageIdx").val());

			// 現在のページが中央より左に表示される場合
			if (center >= corporateBillPageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(slipPageNum);
				$(".liFirstPage").hide();

			// 現在のページが中央より右に表示される場合
			} else if (slipPageNum - (center - 1) <= corporateBillPageIdx + 1) {

				startIdx = slipPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

			// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#corporateBillPageIdx").val() - (center - 2);
				$(".lastIdx").html(slipPageNum);

			}

			$(".pageNum").html(startIdx);
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

 				$(".3dotLineTop").hide();

 			}

			if ((slipPageNum <= 8) || ((slipPageNum - center) <= (corporateBillPageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (slipPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}


			for (i = startIdx; i < endIdx && i < slipPageNum; i++) {
				var clone = $(".pager > li:eq(3)").clone();
				clone.children(".pageNum").text(i + 1);

				if (i == $("#corporateBillPageIdx").val()) {

					clone.find("a").attr("class", "pageNum nowPage");

				} else {
					clone.find("a").attr("class", "pageNum");
				}

 				$(".pager > li.3dotLineEnd").before(clone);
			}
		}

		// ページの押下
		$(".pageNum").click (function () {

			if ($("#corporateBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corporateBillPageIdx").val($(this).text() - 1);
			goTransaction("corporatePaymentManagementPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corporateBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#corporateBillPageIdx").val(Number($("#corporateBillPageIdx").val()) + 1);
			goTransaction("corporatePaymentManagementPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#corporateBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#corporateBillPageIdx").val(Number($("#corporateBillPageIdx").val()) - 1);
			goTransaction("corporatePaymentManagementPageNo.do");
		});

		//ページ送り（上側）
		//先頭ページへ
		$("#firstPage").click (function () {

			if ($("#corporateBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#corporateBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corporateBillPageIdx").val(0);

			goTransaction("corporatePaymentManagementPageNo.do");
		});

		//最終ページへ
		$("#lastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corporateBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#corporateBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corporateBillPageIdx").val(maxPage - 1);

			goTransaction("corporatePaymentManagementPageNo.do");
		});

		// ページ送り（下側）
		//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corporateBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#corporateBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corporateBillPageIdx").val(Number($("#corporateBillPageIdx").val()) + 1);

			goTransaction("corporatePaymentManagementPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#corporateBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#corporateBillPageIdx").val(Number($("#corporateBillPageIdx").val()) - 1);
			goTransaction("corporatePaymentManagementPageNo.do");
		});

		//20140403 安藤　ここから
		//先頭ページへ
		$("#underFirstPage").click (function () {

			if ($("#corporateBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#corporateBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corporateBillPageIdx").val(0);
			goTransaction("corporatePaymentManagementPageNo.do");
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corporateBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#corporateBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corporateBillPageIdx").val(maxPage - 1);
			goTransaction("corporatePaymentManagementPageNo.do");
		});

//******************************************************************************************************
		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		//法人メニュー開閉
		$(".corptgl").click(function(){
			$(this).parent(".corp").find(".corpmenu").toggle();
		});

		//業者と締日で選択
		$(".cutoff").click(function(){
			var corporationId = $(this).parent(".li_cutoff").find(".dispSysCorporationId").val();
			var cutoffDate = $(this).parent(".li_cutoff").find(".dispCutoffDateVal").val();
			$("#corporateBillPageIdx").val(0);
			$("#dispSysCorporationId").val(corporationId);
			$("#dispCutoffDate").val(cutoffDate);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("paymentManagementList.do");
		});

		//業者で選択
		$(".non_cutoff").click(function(){
			var corporationId = $(this).parent(".li_cutoff").find(".dispSysCorporationId").val();
			$("#corporateBillPageIdx").val(0);
			$("#dispSysCorporationId").val(corporationId);
			$("#dispCutoffDate").val(999);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("paymentManagementList.do");
		});


		// ***********************編集を有効にするボタン*********************************************
		$(".updatePaymentManagement").click (function (){

			//フリーワードと金額チェック
			for (var i = 0; i < $("tr.mainTable").size(); i++) {

				var freeWord1 = $("tr.mainTable").eq(i).find("#freeWord1").val();
				var charge1 = $("tr.mainTable").eq(i).find("#charge1").val();
				var freeWord2 = $("tr.mainTable").eq(i).find("#freeWord2").val();
				var charge2 = $("tr.mainTable").eq(i).find("#charge2").val();
				var freeWord3 = $("tr.mainTable").eq(i).find("#freeWord3").val();
				var charge3 = $("tr.mainTable").eq(i).find("#charge3").val();

				if (charge1 != 0 || freeWord1 != "") {
					if (charge1 == 0 || freeWord1 == "") {

						alert("フリーワードと金額は両方入力してください");
						return false;
					}
				}

				if (charge2 != 0 || freeWord2 != "") {
					if (charge2 == 0 || freeWord2 == "") {

						alert("フリーワードと金額は両方入力してください");
						return false;
					}
				}

				if (charge3 != 0 || freeWord3 != "") {
					if (charge3 == 0 || freeWord3 == "") {

						alert("フリーワードと金額は両方入力してください");
						return false;
					}
				}
			}

			if (confirm("変更を反映させます。よろしいですか？")) {
				removeCommaGoTransaction("updatePaymentManagement.do");
			}
			return;
		});

		// ***********************請求書出力ボタン*****************************************
// 		$(".exportBillCorporateBill").click (function (){

// 			$(".overlay").css("display", "block");

// 			$.ajax({
// 				url: ".//exportCorporateBillList.do"
// 				,type: 'POST'
// 				,data : {
// 					"corporateBillListIdx": $(".exportBillCorporateBill").index(this),
// 					"sysCorporateBillId": $(this).siblings('.sysCorporateBillId').val()
// 				}
// 				,success: function(data, text_status, xhr){
// 					$(".overlay").css("display", "none");
// 					window.open('corporateBillPrintOutFile.do','_new');
// 				}
// 				,error: function(data, text_status, xhr){
// 					$(".overlay").css("display", "none");
// 					var errorMsg = "";
// 					if (data['responseText']==1) {
// 						errorMsg = "出力する請求書がありません。";
// 					} else {
// 						errorMsg = data['responseText'];
// 					}
// 					alert("pdfファイルの作成に失敗しました。\n"+errorMsg);
// 				}
// 			});

// 			return;
// 		});

// 		// 請求書削除ボタン
// 		$(".deleteCorporateBill").click (function (){

// 			if (confirm("削除します。よろしいですか？")) {
// 				// 一覧のインデックスを設定
// 				$("#listIdx").val($(".deleteCorporateBill").index(this));

// 				goTransaction("deleteExportCorporateBill.do");
// 			}

// 			return;
// 		});
 	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="/initExportCorporateBill" >
	<html:hidden property="alertType" styleId="alertType"></html:hidden>
	<h4 class="heading">入金管理</h4>
		<nested:hidden property="dispSysCorporationId" styleId="dispSysCorporationId" />
		<nested:hidden property="dispCutoffDate" styleId="dispCutoffDate" />
	<ul class="hmenu mb10">
		<nested:iterate property="corporationList" id="corporation" indexId="idx">
			<li class="corp">
			<span class="corpLink"><nested:write property="abbreviation" /></span>&nbsp;<span class="corptgl" style="cursor: pointer;"><i class="fa fa-caret-down"></i></span>
				<ul class="corpmenu" style="display: none;" >
					<li class="li_cutoff"><a href="javascript:void(0);" class="non_cutoff corpLink">締日指定なし</a>
					<input type="hidden" class="dispSysCorporationId" value="<nested:write property="sysCorporationId" />"></input>
					</li>
					<li class="li_cutoff"><a href="javascript:void(0);" class="cutoff corpLink">月末</a>
					<input type="hidden" class="dispSysCorporationId" value="<nested:write property="sysCorporationId" />"></input>
					<input type="hidden" class="dispCutoffDateVal" value="0" />
					</li>
					<li class="li_cutoff"><a href="javascript:void(0);" class="cutoff corpLink">25日</a>
					<input type="hidden" class="dispSysCorporationId" value="<nested:write property="sysCorporationId" />"></input>
					<input type="hidden" class="dispCutoffDateVal" value="1" />
					</li>
					<li class="li_cutoff"><a href="javascript:void(0);" class="cutoff corpLink">20日</a>
					<input type="hidden" class="dispSysCorporationId" value="<nested:write property="sysCorporationId" />"></input>
					<input type="hidden" class="dispCutoffDateVal" value="2" />
					</li>
					<li class="li_cutoff"><a href="javascript:void(0);" class="cutoff corpLink">15日</a>
					<input type="hidden" class="dispSysCorporationId" value="<nested:write property="sysCorporationId" />"></input>
					<input type="hidden" class="dispCutoffDateVal" value="3" />
					</li>
					<li class="li_cutoff"><a href="javascript:void(0);" class="cutoff corpLink">10日</a>
					<input type="hidden" class="dispSysCorporationId" value="<nested:write property="sysCorporationId" />"></input>
					<input type="hidden" class="dispCutoffDateVal" value="4" />
					</li>
					<li class="li_cutoff"><a href="javascript:void(0);" class="cutoff corpLink">5日</a>
					<input type="hidden" class="dispSysCorporationId" value="<nested:write property="sysCorporationId" />"></input>
					<input type="hidden" class="dispCutoffDateVal" value="5" />
					</li>
				</ul>
			</li>
		</nested:iterate>
		<li class="corp corpLink"><a href="./initCorporatePaymentManagement.do">ALL</a></li>
	</ul>

<!-------------- 検索エリア ここから -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->
	<h5>絞り込み条件を指定して「検索」ボタンを押してください</h5>
		<nested:nest property="corporateSaleSearchDTO">
			<fieldset id="searchOptionField">
				<table class="searchOptionTable">
					<tr>
						<td>請求月</td>
						<td>
							<nested:text property="searchExportMonthFrom" styleClass="ymCalendar text_w80" styleId="searchExportMonthFrom" /> ～ <nested:text property="searchExportMonthTo" styleClass="ymCalendar text_w80" styleId="searchExportMonthTo" />
						</td>
					</tr>
					<tr>
						<td>法人</td>
						<td><nested:select property="searchSysCorporationId" styleId="searchSysCorporationId">
							<html:option value="0">　</html:option>
							<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
						</nested:select></td>
					</tr>
					<tr>
						<td>得意先番号</td>
						<td>
							<nested:text styleId="clientNo" property="clientNo" />&nbsp;部分一致
						</td>
					</tr>
					<tr>
						<td>請求先</td>
						<td>
							<nested:text property="clientNm"/>&nbsp;部分一致
						</td>
						<td>　　並び替え</td>
						<td>
							<nested:select property="sortFirstSub" >
								<html:option value="1">昇順</html:option>
								<html:option value="2">降順</html:option>
							</nested:select>
						</td>
					</tr>
					<tr>
						<td>合計請求金額</td>
						<td>
							<nested:text styleId="searchBillingAmountFrom" property="searchBillingAmountFrom" /> ～ <nested:text styleId="searchBillingAmountTo" property="searchBillingAmountTo" />
						</td>
						<td>　　表示件数</td>
						<td>
							<nested:select property="listPageMax">
								<html:optionsCollection property="listPageMaxForPaymentManagementMap" value="key" label="value" />
							</nested:select>
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td><a id="searchPaymentInfo" class="button_main" href="javascript:void(0);">検索</a></td>
					</tr>
				</table>
			</fieldset>
		</nested:nest>
<!-------------- 検索エリア ここまで -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->

<!-------------- エラーメッセージ ここから ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->
	<nested:nest property="errorDTO">
		<nested:notEmpty property="errorMessageList">
			<div id="errorArea">
				<nested:iterate property="errorMessageList" >
					<p class="errorMessage"><nested:write property="errorMessage"/></p>
				</nested:iterate>
			</div>
		</nested:notEmpty>
		<nested:notEmpty property="errorMessage">
			<div id="errorArea">
					<p class="errorMessage"><nested:write property="errorMessage"/></p>
			</div>
		</nested:notEmpty>
	</nested:nest>
<!-------------- エラーメッセージ ここまで  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------>

	<nested:notEmpty property="paymentManageDTOList">

		<div class="middleArea">
			<table class="editButtonTable">
				<tr>
					<td><a class="button_main updatePaymentManagement" href="Javascript:void(0);">編集を有効にする</a></td>
				</tr>
			</table>
		</div>

<!-- ページ(上側) -->
		<div class="paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="paymentManageListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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
<!-- ページ(上側)　ここまで -->





	<div id="list_area">
		<nested:hidden property="paymentManageListSize" styleId="corporateBillListSize" />
		<nested:hidden property="paymentManagePageIdx" styleId="corporateBillPageIdx" />
		<nested:hidden property="paymentManageListPageMax" styleId="corporateBillListPageMax" />
		<nested:hidden property="paymentManageListIdx" styleId="listIdx" />
		<table id="mstTable" class="list_table">
			<tr>
				<th class="clientCorporationNmHd">得意先番号</th>
				<th class="clientBillingNmHd">請求先</th>
				<th class="billdataCreateDateHd">口座</th>
				<th class="billdataCreateDateHd">請求月</th>
				<th class="billAmountHd">請求額</th>
				<th class="receiveDateHd">入金日</th>
				<th class="receivePriceHd">入金額</th>
				<th class="freeWordHd">フリーワード1</th>
				<th class="chargeHd">金額1</th>
				<th class="freeWordHd">フリーワード2</th>
				<th class="chargeHd">金額2</th>
				<th class="freeWordHd">フリーワード3</th>
				<th class="chargeHd">金額3</th>
			</tr>

			<nested:iterate property="paymentManageDTOList" indexId="listIdx">
				<tbody class="change_color_only">
					<tr class="mainTable">
						<td><nested:write property="clientNo" /></td>
						<td><nested:write property="clientNm" /></td>
						<td><nested:write property="accountNm" /></td>
					<td><nested:write property="demandMonth"/>&nbsp;月請求</td>
					<td><nested:write property="billAmount" format="###,###,###" />&nbsp;円</td>
						<td><nested:text property="receiveDate" styleClass="calendar receiveDate" maxlength="10"  /></td>
						<td><nested:text property="receivePrice" styleClass="priceText receivePrice" style="width: 100px; text-align: right;" maxlength="9" />&nbsp;円</td>
						<td><nested:text property="freeWord" styleId="freeWord1" styleClass="freeWord" style="width: 136px;" maxlength="10" /></td>
						<td><nested:text property="charge" styleId="charge1" styleClass="priceText charge" style="width: 50px; text-align: right;"  maxlength="9" />&nbsp;円</td>
						<td><nested:text property="freeWord2" styleId="freeWord2" styleClass="freeWord" style="width: 136px;" maxlength="10"/></td>
						<td><nested:text property="charge2" styleId="charge2" styleClass="priceText charge" style="width: 50px; text-align: right;"  maxlength="9" />&nbsp;円</td>
						<td><nested:text property="freeWord3" styleId="freeWord3" styleClass="freeWord" style="width: 136px;"  maxlength="10"/></td>
						<td><nested:text property="charge3" styleId="charge3" styleClass="priceText charge" style="width: 50px; text-align: right;"  maxlength="9" />&nbsp;円</td>
<!-- 						<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main exportBillCorporateBill" tabindex="-1" >出力</a> -->
<%-- 							<nested:hidden property="sysCorporateBillId" styleClass="sysCorporateBillId"></nested:hidden> --%>
<!-- 						</td> -->
<!-- 							<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main deleteCorporateBill" tabindex="-1" >削除</a></td> -->
<%-- 						<td><nested:text property="memo" styleId="memo" styleClass="freeWord" style="width: 200px;" maxlength="40" /></td> --%>
					</tr>
				</tbody>
			</nested:iterate>
		</table>
	</div>


<!-- ページ(下側) -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="paymentManageListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
			</div>
			<div class="paging_num_top">
				<ul class="pager fr mb10 underPager">
				    <li class="backButton"><a href="javascript:void(0);" id="underBackPage" >&laquo;</a></li>
				    <li class="liFirstPage" ><a href="javascript:void(0);" id="underFirstPage" >1</a></li>
				    <li class="3dotLineTop"><span>...</span></li>
					<li><a href="javascript:void(0);" class="pageNum" ></a></li>
				  	<li class="3dotLineEnd"><span>...</span></li>
				    <li class="liLastPage" ><a href="javascript:void(0);" id="underLastPage"  class="lastIdx" ></a></li>
				    <li class="nextButton"><a href="javascript:void(0);" id="underNextPage" >&raquo;</a></li>
				</ul>
			</div>
		</div>
<!-- ページ(下側)　ここまで -->



	</nested:notEmpty>
	</nested:form>
	<div class="overlay">
		<div class="messeage_box">
			<h1 class="message">ファイル検索中</h1>
			<BR />
			<p>Now Loading...</p>
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>
</html:html>