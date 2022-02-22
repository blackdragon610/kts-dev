]<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<title>請求書出力</title>
	<link rel="stylesheet" href="./css/exportCorporateBill.css" type="text/css" />
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
【請求書出力画面】
ファイル名：exportCorporateBill.jsp
作成日：2014/12/19
作成者：八鍬寛之

（画面概要）

請求書出力

・作成ボタン押下：絞り込み条件にしたがい業販請求書テーブルを作成する
・出力ボタン押下：テーブルの中から選択した請求書をpdf出力する。

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
		// end

		$("#createBill").click(function(){

			if($("#exportMonth").val() == "") {
				alert("作成月を入力してください。");
				return;
			}

			if($("#sysCorporationId").val() == "0") {
				alert("法人を選択してください。");
				return;
			}

			if (!confirm("入力された条件で請求書を作成します。\nよろしいですか？")) {
				return;
			}
			$(".overlay").css("display", "block");
			$(".message").text("作成");
			goTransaction("createExportCorporateBill.do");

		});

		// 「検索」ボタン押下時
		$("#searchBill").click(function(){

			if ($("#searchExportMonthFrom").val() && $("#searchExportMonthTo").val()){
				fromAry = $("#searchExportMonthFrom").val().split("/");
				toAry = $("#searchExportMonthTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1]);
				toDt = new Date(toAry[0], toAry[1]);
				if (fromDt > toDt) {
					alert("請求月 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#searchBillingAmountFrom").val() && $("#searchBillingAmountTo").val()) {
				var fromPrice = parseInt($("#searchBillingAmountFrom").val());
				var toPrice = parseInt($("#searchBillingAmountTo").val());
				if (fromPrice > toPrice) {
					alert("合計請求金額の検索開始金額が、検索終了金額より大きい額になっています。");
					return false;
				}
			}

			$(".overlay").css("display", "block");
			$(".message").text("検索");
			goTransaction("searchExportCorporateBill.do");

		});


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
			goTransaction("corporateBillListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corporateBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#corporateBillPageIdx").val(Number($("#corporateBillPageIdx").val()) + 1);
			goTransaction("corporateBillListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#corporateBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#corporateBillPageIdx").val(Number($("#corporateBillPageIdx").val()) - 1);
			goTransaction("corporateBillListPageNo.do");
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

			goTransaction("corporateBillListPageNo.do");
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

			goTransaction("corporateBillListPageNo.do");
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

			goTransaction("corporateBillListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#corporateBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#corporateBillPageIdx").val(Number($("#corporateBillPageIdx").val()) - 1);
			goTransaction("corporateBillListPageNo.do");
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
			goTransaction("corporateBillListPageNo.do");
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
			goTransaction("corporateBillListPageNo.do");
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
			goTransaction("corporateBillList.do");
		});

		//業者で選択
		$(".non_cutoff").click(function(){
			var corporationId = $(this).parent(".li_cutoff").find(".dispSysCorporationId").val();
			$("#corporateBillPageIdx").val(0);
			$("#dispSysCorporationId").val(corporationId);
			$("#dispCutoffDate").val(999);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("corporateBillList.do");
		});


		// ***********************編集を有効にするボタン*********************************************
		$(".updateCorporateBill").click (function (){

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
				removeCommaGoTransaction("updateExportCorporateBill.do");
			}
			return;
		});

		// ***********************請求書出力ボタン*****************************************
		$(".exportBillCorporateBill").click (function (){

			$(".overlay").css("display", "block");

			$.ajax({
				url: "./exportCorporateBillList.do"
				,type: 'POST'
				,data : {
					"corporateBillListIdx": $(".exportBillCorporateBill").index(this),
					"sysCorporateBillId": $(this).siblings('.sysCorporateBillId').val()
				}
			}).done(function(data) {
				$(".overlay").css("display", "none");
				window.open('corporateBillPrintOutFile.do','_new');
			}).fail(function(data) {
				$(".overlay").css("display", "none");
				var errorMsg = "";
				if (data['responseText']==1) {
					errorMsg = "出力する請求書がありません。";
				} else {
					errorMsg = data['responseText'];
				}
				alert("pdfファイルの作成に失敗しました。\n"+errorMsg);
			});

			return;
		});

		// 請求書削除ボタン
		$(".deleteCorporateBill").click (function (){

			if (confirm("削除します。よろしいですか？")) {
				// 一覧のインデックスを設定
				$("#listIdx").val($(".deleteCorporateBill").index(this));

				goTransaction("deleteExportCorporateBill.do");
			}

			return;
		});

		// 全選択全解除ボタン押下時
		$('.pick').click(function(){
			if($(this).text() == "✔"){
				$('.pickCheckBox').prop({'checked':'checked'});
				$('.pick').text("空");
			}else{
				$('.pickCheckBox').prop({'checked':false});
				$('.pick').text("✔");
			}
		});

		// チェックボックスの状態を監視、一つでもチェックが入ったら「空」に変更
		$(document).on("change", ".pickCheckBox", function () {
			var isCheckBox = 0;
			$(".pickCheckBox").each(function(){
				if ($(this).prop('checked')) {
					isCheckBox = 1;
				}
			});

			// チェックボックスの状態を確認してチェックが一つでも入ったら「空」に変更
			if (isCheckBox) {
				$('.pick').text("空");
				return;
			}

			$('.pick').text("✔");
		});


		// 一括出力ボタン押下時
		$(".batchExportCorporateBill").click (function (){

			// チェックボックスの状態を確認してチェックが一つも入っていなかったらエラーとする。
			var isCheckBox = 1;
			$(".pickCheckBox").each(function(){
				if ($(this).prop('checked')) {
					isCheckBox = 0;
				}
			});
			if (isCheckBox) {
				alert("一括出力をチェックしてください。")
				return;
			}

			if (!confirm("チェックされている請求書を一括出力します。\nよろしいですか？")) {
				return;
			}

			// チェックされた請求書番号を配列に格納する。
			var sysCorporateBillIdArray = [];
			$('.pickCheckBox:checked').each(function() {
				sysCorporateBillIdArray.push($(this).siblings('#sysCorporateBillId').val());

			});


			$(".overlay").css("display", "block");
			$.ajax({
				url: "./batchExportCorporateBill.do"
				,type: 'POST'
				,traditional: true
				,data : {
					'sysCorporateBillIdArray': sysCorporateBillIdArray
				 }
			}).done(function(data) {
				$(".overlay").css("display", "none");
				window.open('corporateBillPrintOutFile.do','_new');
			}).fail(function(data) {
				$(".overlay").css("display", "none");
				var errorMsg = "";
				if (data['responseText']==1) {
					errorMsg = "出力する請求書がありません。";
				} else {
					errorMsg = data['responseText'];
				}
				alert("pdfファイルの作成に失敗しました。\n"+errorMsg);
			});
			return;
		});


		// TODO 並び替えと表示件数はクリアしない。
		$(".clear").click(function(){
			// テキスト項目のみをクリアする。
			$("#searchOptionField input, #searchOptionField text").each(function(){
					$(this).val("");
			});

			// セレクトボックス項目は初期表示時の値に初期化する。
			$("#searchSysCorporationId").val(0);
			$("#sortFirstSub").val(2);
			$("#listPageMax").val(1);
		});

	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="/initExportCorporateBill">
	<html:hidden property="alertType" styleId="alertType"></html:hidden>
	<h4 class="heading">請求書作成</h4>
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
		<li class="corp corpLink"><a href="./initExportCorporateBill.do">ALL</a></li>
	</ul>

	<h5>絞り込み条件を指定して「作成」ボタンを押してください</h5>



 	<fieldset id="makeOptionField">
		<legend id="searchOptionOpen">作成</legend>
			<table class="searchOptionTable">
				<tr>
					<td>作成月</td>
					<td>
						<nested:text property="exportMonth" styleClass="ymCalendar" styleId="exportMonth" maxlength="8" />
					</td>
				</tr>
				<tr>
					<td>法人</td>
					<td><nested:select property="sysCorporationId" styleId="sysCorporationId">
						<html:option value="0">　</html:option>
						<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
					</nested:select></td>

				</tr>
				<tr>
					<td>締日</td>
					<td><nested:select property="selectedCutoff" styleId="selectedCutoff">
						<html:optionsCollection property="billingCutoffDate" label="value" value="key" />
					</nested:select>
					</td>
				</tr>
	<!-- 			<tr> -->
	<!-- 				<td>通貨</td> -->
	<!-- 				<td><select id="currency"> -->
	<!-- 					<option value="1">円</option> -->
	<!-- 					<option value="2">ドル</option> -->
	<!-- 				</select></td> -->
	<!-- 			</tr> -->

				<tr>
					<td>
					</td>
					<td><a id="createBill" class="button_main" href="javascript:void(0);">作成</a></td>
				</tr>
			</table>
		</fieldset>

		<fieldset id="searchOptionField">
			<legend id="searchOptionOpen">検索</legend>
			<nested:nest property="corporateSaleSearchDTO">
				<table class="searchOptionTable">
					<tr>
						<td>請求月</td>
						<td>
							<!-- TODO 入力値の保持をしたいが、なぜか保持できない。 ymCalenderは値を保持しない処理となっている。-->
							<nested:text property="searchExportMonthFrom" styleId="searchExportMonthFrom" styleClass="ymCalendar" maxlength="7" />
							～
							<nested:text property="searchExportMonthTo" styleId="searchExportMonthTo" styleClass="ymCalendar" maxlength="7" />
						</td>
					</tr>
					<tr>
						<td>請求書番号</td>
						<td><nested:text property="corporateBillNo" styleClass="alphanumeric" />
							&nbsp;部分一致
						</td>
					</tr>
					<tr>
						<td>法人</td>
						<td>
							<nested:select property="searchSysCorporationId" styleId="searchSysCorporationId">
								<html:option value="0">　</html:option>
								<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
							</nested:select>
						</td>
					</tr>
					<tr>
						<td>得意先番号</td>
							<td><nested:text property="clientNo" styleClass="numText" />
								&nbsp;部分一致
							</td>
					</tr>

					<tr>
						<td>得意先名</td>
						<td><nested:text property="clientNm" />
							&nbsp;部分一致
							</td>
							<td nowrap>並び替え</td>
							<td style="white-space: nowrap;">
							<nested:select property="sortFirstSub" styleId="sortFirstSub">
								<html:option value="1">昇順</html:option>
								<html:option value="2">降順</html:option>
							</nested:select>
						</td>
					</tr>
					<tr>
						<td>合計請求金額</td>
						<td>
							<nested:text property="searchBillingAmountFrom" styleId="searchBillingAmountFrom" styleClass="priceTextMinusSearch" maxlength="9"/>
							<input type="hidden" id="searchBillingAmountFrom" value=""/>
							～
							<nested:text property="searchBillingAmountTo" styleId="searchBillingAmountTo" styleClass="priceTextMinusSearch" maxlength="9"/>
							<input type="hidden" id="searchBillingAmountTo" value=""/>
						</td>

						<td nowrap>表示件数</td>
						<td>
							<nested:select property="listPageMax" styleId="listPageMax" >
								<html:optionsCollection property="listPageMaxMap" value="key" label="value" />
							</nested:select>&nbsp;件
						</td>
					</tr>

					<tr>
						<td>
						</td>
						<td>
							<a id="searchBill" class="button_main" href="javascript:void(0);">検索</a>
							<a class="button_white clear" href="javascript:void(0);">リセット</a>
						</td>
					</tr>
				</table>
			</nested:nest>
		</fieldset>
	<!-- TODO end 検索-->


	<nested:nest property="errorDTO">
		<nested:notEmpty property="errorMessageList">
			<div id="errorArea">
				<nested:iterate property="errorMessageList" >
					<p style="padding-top: 350px;" class="errorMessage">
					<nested:write property="errorMessage"/></p>
				</nested:iterate>
			</div>
		</nested:notEmpty>
		<nested:notEmpty property="errorMessage">
			<div id="errorArea">
					<p style="padding-top: 350px; "class="errorMessage">
					<nested:write property="errorMessage"/></p>
			</div>
		</nested:notEmpty>
	</nested:nest>

	<nested:notEmpty property="corporateBillDTOList">

		<div class="middleArea">
			<table class="editButtonTable">
				<tr>
					<td style = "margin-left:20px;"><a class="button_main updateCorporateBill" href="Javascript:void(0);">編集を有効にする</a></td>
					<td style = "padding-left:20px;"><a class="button_main batchExportCorporateBill" href="Javascript:void(0);">選択した請求書を一括出力する</a></td>
				</tr>
			</table>
		</div>

<!-- ページ(上側) -->
		<div class="paging_area">

			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="corporateBillListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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


	<table class="checkButtonArea">
		<tr>
			<td style="padding-bottom:0px;"><a class="button_small_main pick" href="Javascript:void(0);">✔</a></td>
		</tr>
	</table>


	<div id="list_area">
		<nested:hidden property="corporateBillListSize" styleId="corporateBillListSize" />
		<nested:hidden property="corporateBillPageIdx" styleId="corporateBillPageIdx" />
		<nested:hidden property="corporateBillListPageMax" styleId="corporateBillListPageMax" />
		<nested:hidden property="corporateBillListIdx" styleId="listIdx" />
		<table id="mstTable" class="list_table">
			<tr>
				<th class="check">一括出力</th>
				<th class="corporateBillNoHd">請求書番号</th>
				<th class="clientCorporationNmHd">取引先法人</th>
				<th class="clientBillingNmHd">請求先</th>
				<th class="clientCutoffDateNmHd">請求先締日</th>
				<th class="billdataCreateDateHd">請求月</th>
				<th class="billAmountHd">請求額</th>
				<th class="billAccountNmHd">口座名</th>
				<th class="receiveDateHd">入金日</th>
				<th class="receivePriceHd">入金額</th>
				<th class="freeWordHd">フリーワード1</th>
				<th class="chargeHd">金額1</th>
				<th class="freeWordHd">フリーワード2</th>
				<th class="chargeHd">金額2</th>
				<th class="freeWordHd">フリーワード3</th>
				<th class="chargeHd">金額3</th>
<!-- 				<th class="chargeHd">手数料</th> -->
				<th class="exportBillHd">請求書出力</th>
				<th class="deleteBillHd">請求書削除</th>
				<th class="freeWordHd">メモ</th>
			</tr>

			<!-- 請求書作成日＞法人番号＞請求書番号の順番で表示する。 -->
			<nested:iterate property="corporateBillDTOList" indexId="listIdx">
				<tbody class="change_color_only">
				<tr class="mainTable">
					<td class="pickTd" rowspan="3">
						<input type="checkbox" class="pickCheckBox" />
						<nested:hidden property="sysCorporateBillId" styleId="sysCorporateBillId"/>
					</td>
					<td><nested:write property="corporateBillNo" /></td>
					<td><nested:write property="clientCorporationNm" /></td>
					<td><nested:write property="clientBillingNm" /></td>
					<td><nested:write property="clientCutoffDateNm" />締</td>
					<td><nested:write property="demandMonth"/>&nbsp;月請求</td>
					<td><nested:write property="billAmount" format="###,###,###" />&nbsp;円</td>
					<td><nested:write property="accountNm" /></td>
					<nested:hidden property="sysAccountId" styleClass="sysAccountId"></nested:hidden>
					<td><nested:text property="receiveDate" styleClass="calendar receiveDate" maxlength="10"  /></td>
					<td><nested:text property="receivePrice" styleClass="priceText receivePrice" style="width: 100px; text-align: right;" maxlength="9" />&nbsp;円</td>
					<td><nested:text property="freeWord" styleId="freeWord1" styleClass="freeWord" style="width: 136px;" maxlength="10" /></td>
					<td><nested:text property="charge" styleId="charge1" styleClass="priceText charge" style="width: 50px; text-align: right;"  maxlength="9" />&nbsp;円</td>
					<td><nested:text property="freeWord2" styleId="freeWord2" styleClass="freeWord" style="width: 136px;" maxlength="10"/></td>
					<td><nested:text property="charge2" styleId="charge2" styleClass="priceText charge" style="width: 50px; text-align: right;"  maxlength="9" />&nbsp;円</td>
					<td><nested:text property="freeWord3" styleId="freeWord3" styleClass="freeWord" style="width: 136px;"  maxlength="10"/></td>
					<td><nested:text property="charge3" styleId="charge3" styleClass="priceText charge" style="width: 50px; text-align: right;"  maxlength="9" />&nbsp;円</td>
					<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main exportBillCorporateBill" tabindex="-1" >出力</a>
					<nested:hidden property="sysCorporateBillId" styleClass="sysCorporateBillId"></nested:hidden>
					</td>
					<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main deleteCorporateBill" tabindex="-1" >削除</a></td>
					<td><nested:text property="memo" styleId="memo" styleClass="freeWord" style="width: 200px;" maxlength="40" /></td>
				</tr>
				</tbody>
			</nested:iterate>

		</table>
	</div>


<!-- ページ(下側) -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="corporateBillListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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