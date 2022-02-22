<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<title>法人間請求書一覧</title>
	<link rel="stylesheet" href="./css/btobBill.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.ympicker.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【法人間請求書一覧画面】
ファイル名：btobBill.jsp
作成日：2016/1/14
作成者：大山智史

（画面概要）



（注意・補足）

-->

	<script type="text/javascript">
	$(document).ready(function(){
		$(".overlay").css("display", "none");

		// 法人リンクの選択文字
// 		$("#corpLink<bean:write name="btobBillForm" property="dispSysCorporationId" />").removeAttr("href");
// 		$("#corpLink<bean:write name="btobBillForm" property="dispSysCorporationId" />").parents("li").attr("class", "corp");
// 		$("#corpLink<bean:write name="btobBillForm" property="dispSysCorporationId" />").attr("class", "selected");
		$("#corpLink<bean:write name="btobBillForm" property="dispSysCorporationId" />").css({'color': '#990000'});
	 });


	$(function() {

		$(".calendar").datepicker();
		$(".calendar").datepicker("option", "showOn", 'button');
		$(".calendar").datepicker("option", "buttonImageOnly", true);
		$(".calendar").datepicker("option", "buttonImage", './img/calender_icon.png');

		// start カレンダー機能(ympicker)の設定処理

		// ympickerの設定処理で入力値が初期化されてしまうので一度変数に再表示したい値を格納する。
		var expMonth = $("#exportMonth").val();

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
		// end


		//ページング********************************************************************************************

		// ページリストの表示
		if ($("#btobBillListSize").val() != 0) {
			var slipPageNum = Math.ceil($("#btobBillListSize").val() / $("#btobBillListPageMax").val());

			$(".slipPageNum").text(slipPageNum);
			$(".slipNowPage").text(Number($("#btobBillPageIdx").val()) + 1);

			var i;

			if (0 == $("#btobBillPageIdx").val()) {
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
			var btobBillPageIdx = new Number($("#btobBillPageIdx").val());

			// 現在のページが中央より左に表示される場合
			if (center >= btobBillPageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(slipPageNum);
				$(".liFirstPage").hide();

			// 現在のページが中央より右に表示される場合
			} else if (slipPageNum - (center - 1) <= btobBillPageIdx + 1) {

				startIdx = slipPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

			// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#btobBillPageIdx").val() - (center - 2);
				$(".lastIdx").html(slipPageNum);

			}

			$(".pageNum").html(startIdx);
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

 				$(".3dotLineTop").hide();

 			}

			if ((slipPageNum <= 8) || ((slipPageNum - center) <= (btobBillPageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (slipPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}


			for (i = startIdx; i < endIdx && i < slipPageNum; i++) {
				var clone = $(".pager > li:eq(3)").clone();
				clone.children(".pageNum").text(i + 1);

				if (i == $("#btobBillPageIdx").val()) {

					clone.find("a").attr("class", "pageNum nowPage");

				} else {
					clone.find("a").attr("class", "pageNum");
				}

 				$(".pager > li.3dotLineEnd").before(clone);
			}
		}

		// ページの押下
		$(".pageNum").click (function () {

			if ($("#btobBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#btobBillPageIdx").val($(this).text() - 1);
			goTransaction("btobBillListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#btobBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#btobBillPageIdx").val(Number($("#btobBillPageIdx").val()) + 1);
			goTransaction("btobBillListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#btobBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#btobBillPageIdx").val(Number($("#btobBillPageIdx").val()) - 1);
			goTransaction("btobBillListPageNo.do");
		});

		//ページ送り（上側）
		//先頭ページへ
		$("#firstPage").click (function () {

			if ($("#btobBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#btobBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#btobBillPageIdx").val(0);

			goTransaction("btobBillListPageNo.do");
		});

		//最終ページへ
		$("#lastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#btobBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#btobBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#btobBillPageIdx").val(maxPage - 1);

			goTransaction("btobBillListPageNo.do");
		});

		// ページ送り（下側）
		//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#btobBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#btobBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#btobBillPageIdx").val(Number($("#btobBillPageIdx").val()) + 1);

			goTransaction("btobBillListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#btobBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#btobBillPageIdx").val(Number($("#btobBillPageIdx").val()) - 1);
			goTransaction("btobBillListPageNo.do");
		});

		//20140403 安藤　ここから
		//先頭ページへ
		$("#underFirstPage").click (function () {

			if ($("#btobBillPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#btobBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#btobBillPageIdx").val(0);
			goTransaction("btobBillListPageNo.do");
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#btobBillPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#btobBillPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#btobBillPageIdx").val(maxPage - 1);
			goTransaction("btobBillListPageNo.do");
		});

//******************************************************************************************************

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		// 法人リンク
		$(".corpLink").click(function(){
			var corporationId = $(this).find(".dispSysCorporationId").val();
			$("#btobBillPageIdx").val(0);
			$("#dispSysCorporationId").val(corporationId);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction('btobBillList.do');
		});

		// 編集を有効にするボタン
		$(".updateBtobBill").click (function (){
			if (confirm("変更を反映させます。よろしいですか？")) {
				removeCommaGoTransaction("updateBtobBill.do");
			}
			return;
		});

		// 請求書削除ボタン
		$(".deleteBtobBill").click (function (){

			if (confirm("削除します。よろしいですか？")) {
				// 一覧のインデックスを設定
				$("#listIdx").val($(".deleteBtobBill").index(this));

				goTransaction("deleteBtobBill.do");

			}

			return;
		});

		// 請求書出力ボタン
		$(".exportBillBtobBill").click (function (){

			$(".overlay").css("display", "block");
			$.ajax({
				url: "./exportBillBtobBill.do"
				,type: 'POST'
				,data : {
					"btobBillListIdx": $(".exportBillBtobBill").index(this)
				}
				,success: function(data, text_status, xhr){
					$(".overlay").css("display", "none");
					window.open('billBtobBillPrintOutFile.do','_new');
				}
				,error: function(data, text_status, xhr){
					$(".overlay").css("display", "none");
					var errorMsg = "";
					if (data['responseText']==1) {
						errorMsg = "出力する請求書がありません。";
					} else {
						errorMsg = data['responseText'];
					}
					alert("pdfファイルの作成に失敗しました。\n"+errorMsg);
				}
			});

			return;
		});

		$("#exportBill").click(function(){

			if($("#exportMonth").val() == "") {
				alert("出力月を入力してください。");
				return;
			}

			if($("#exportSysCorporationId").val() == "0") {
				alert("法人を選択してください。");
				return;
			}

			if (!confirm("入力された条件で請求書を作成します。\nよろしいですか？")) {
				return;
			}
			$(".overlay").css("display", "block");
			$(".message").text("作成");
			goTransaction("createBtobBill.do");

		});
		//請求書検索処理
		$("#searchBill").click(function() {

			//検索の制御は外しました。もしこの先つくことがあったらコメントを外してください
// 			if($("#exportMonthForSearch").val() == "") {
// 				alert("出力月を入力してください。");
// 				return;
// 			}

// 			if($("#exportSysCorporationIdForSearch").val() == "0") {
// 				alert("法人を選択してください。");
// 				return;
// 			}

			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("searchBtobBill.do");
		});

 	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="/initBtobBillList" >
	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="headingKind">法人間請求書一覧</h4>

	<nested:hidden property="dispSysCorporationId" styleId="dispSysCorporationId" />

		<ul class="hmenu mb10">
		<nested:iterate property="corporationList" id="corporation">
			<li class="corp corpLink">
				<a href="javascript:void(0);" id="corpLink<nested:write property="sysCorporationId" />"><nested:write property="abbreviation" /></a>
				<nested:hidden property="sysCorporationId" styleClass="dispSysCorporationId" />
				<nested:hidden property="corporationNm" styleId="corporationNm" />
			</li>
		</nested:iterate>
		<li class="corp corpLink"><a href="javascript:void(0);"  id="corpLink0">ALL</a><input type="hidden" class="sysCorporationId" value="0" /></li>
		<nested:nest property="btobBillSearchDto">

			<li class="sumAreaSelect">合計値エリア
				<nested:select property="sumDispFlg">
				<html:optionsCollection property="sumDispMap" label="value" value="key" />
				</nested:select>
			</li >
			<li class="gross">粗利
				<nested:select property="grossProfitCalc" styleId="grossProfitCalc">
				<html:optionsCollection property="btobBillGrossProfitCalcMap" value="key" label="value" />
				</nested:select>
			</li >
		</nested:nest>
		<li id="summaryLi">
			<nested:notEmpty property="corporateSaleListTotalDTO">
	<%-- 		<nested:equal property="btobBillSearchDto.sumDispFlg" value="0"> --%>
			<table class="summary">
				<tr>
	<!-- 				<th>合計請求金額</th> -->
					<th>税込商品計</th>
					<th>税抜き商品計</th>
					<th>kind原価</th>
					<th>粗利</th>
				</tr>
				<tr>
					<nested:nest property="corporateSaleListTotalDTO">
	<%-- 					<td><nested:write property="sumSumClaimPrice" format="###,###,###" /></td> --%>
						<td><nested:write property="inTaxSumPieceRate" format="###,###,###" /></td>
						<td><nested:write property="noTaxSumPieceRate" format="###,###,###" /></td>
						<td><nested:write property="sumCost" format="###,###,###" /></td>
						<td><nested:write property="sumGrossMargin" format="###,###,###" /></td>
					</nested:nest>
				</tr>
			</table>
	<%-- 		</nested:equal> --%>
			</nested:notEmpty>
		</li>
	</ul>
	<ul class="descriprion">
		<li>税込商品計　　：各請求書の商品単価の税込合計金額の合算値</li>
		<li>税抜き商品計　：各請求書の商品単価の税抜き合計金額の合算値</li>
		<li>kind原価　　　：売上、業販伝票に登録されたkind原価の合算値</li>
		<li>粗利(税込商品計からの算出)　　　：税込商品計 - kind原価 = 粗利</li>
		<li>粗利(税抜き商品計からの算出)　　：税抜き商品計 - kind原価 = 粗利</li>
	</ul>
<!-- BONCRE-2587 請求書検索機能追加のときに使う 検索エリアが作成と同じスタイル-->
<!-- 法人間検索機能を表示するときはここのコメントを外してください -->
	<!-- ここから -->
<!-- 			<h5>絞り込み条件を指定して「検索」ボタンを押してください</h5> -->
<!-- 			<fieldset id="searchOptionField"> -->
<!-- 				<table> -->
<!-- 					<tr> -->
<!-- 						<td> -->
<!-- 							<table class="searchOptionTable"> -->
<!-- 								<tr> -->
<!-- 									<td>出力月</td> -->
<!-- 									<td> -->
<%-- 										<nested:text property="exportMonth" styleClass="ymCalendar text_w80" styleId="exportMonthForSearch"/> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>法人</td> -->
<!-- 									<td> -->
<%-- 										<nested:select property="exportSysCorporationId" styleId="exportSysCorporationIdForSearch" > --%>
<%-- 											<html:option value="0">　</html:option> --%>
<%-- 											<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" /> --%>
<%-- 										</nested:select> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td></td> -->
<!-- 									<td><a id="searchBill" class="button_main" href="javascript:void(0);"  >検索</a></td> -->
<!-- 								</tr> -->
<!-- 							</table> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 				</table> -->
<!-- 			</fieldset> -->
<!-- 	ここまで -->

	<h5>絞り込み条件を指定して「作成」ボタンを押してください</h5>

	<fieldset id="searchOptionField">
		<table>
			<tr>
				<td>
					<table class="searchOptionTable">
						<tr>
							<td>出力月</td>
							<td>
								<nested:text property="exportMonth" styleClass="ymCalendar text_w80" styleId="exportMonth" />
							</td>
						</tr>
						<tr>
							<td>法人</td>
							<td><nested:select property="exportSysCorporationId" styleId="exportSysCorporationId">
								<html:option value="0">　</html:option>
								<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
							</nested:select></td>
						</tr>
						<tr>
							<td>
							</td>
							<td><a id="exportBill" class="button_main" href="javascript:void(0);"  >作成</a></td>
						</tr>
					</table>
				</td>
				<td>
					請求書は以下の構成にて作成されます。<br/>
					<br/>
					株式会社KTS：KTS、T-FOUR、KTS法人営業部、KTS掛業販、KTS海外事業部<br/>
					株式会社車楽院：車楽院<br/>
					株式会社ラルグスリテール：ラルグスリテール、Largusretail業販<br/>
					株式会社BCR：株式会社BCR<br/>
					株式会社日本中央貿易：サイバーエコ、株式会社日本中央貿易、ウルトラレーシング事業部<br/>
					Brembo事業部：Brembo事業部
				</td>
			</tr>
		</table>
	</fieldset>

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

	<nested:notEmpty property="btobBillDTOList">

		<div class="middleArea">
			<table class="editButtonTable">
				<tr>
					<td><a class="button_main updateBtobBill" href="Javascript:void(0);">編集を有効にする</a></td>
				</tr>
			</table>
		</div>

<!-- ページ(上側) -->
		<div class="paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="btobBillListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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
		<nested:hidden property="btobBillListSize" styleId="btobBillListSize" />
		<nested:hidden property="btobBillPageIdx" styleId="btobBillPageIdx" />
		<nested:hidden property="btobBillListPageMax" styleId="btobBillListPageMax" />
		<nested:hidden property="btobBillListIdx" styleId="listIdx" />
		<table id="mstTable" class="list_table">
			<tr>
				<th class="btobBillNoHd">請求書番号</th>
				<th class="clientCorporationNmHd">取引先法人</th>
				<th class="billdataCreateDateHd">請求月</th>
				<th class="billAmountHd">請求額</th>
				<th class="receiveDateHd">入金日</th>
				<th class="freeWordHd">フリーワード</th>
				<th class="receivePriceHd">入金額</th>
				<th class="chargeHd">手数料</th>
				<th class="exportBillHd">請求書出力</th>
				<th class="deleteBillHd">請求書削除</th>
			</tr>

			<nested:iterate property="btobBillDTOList" indexId="listIdx">
				<tbody class="change_color_only">
				<nested:hidden property="sysBtobBillId" styleClass="sysBtobBillId"></nested:hidden>
				<tr>
					<td><nested:write property="btobBillNo" /></td>
					<td><nested:write property="clientCorporationNm" /></td>
					<td><nested:write property="demandMonth" />&nbsp;月請求</td>
					<td><nested:write property="billAmount" format="###,###,###" />&nbsp;円</td>
					<td><nested:text property="receiveDate" styleClass="calendar receiveDate" maxlength="10"  /></td>
					<td><nested:text property="freeWord" styleClass="freeWord" style="width: 216px;" /></td>
					<td><nested:text property="receivePrice" styleClass="priceText receivePrice" style="width: 100px; text-align: right;" maxlength="9" />&nbsp;円</td>
					<td><nested:text property="charge" styleClass="priceText charge" style="width: 100px; text-align: right;"  maxlength="9" />&nbsp;円</td>
					<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main exportBillBtobBill" tabindex="-1" >出力</a></td>
					<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main deleteBtobBill" tabindex="-1" >削除</a></td>
				</tr>
				</tbody>
			</nested:iterate>

		</table>
	</div>

<!-- ページ(下側) -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="btobBillListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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