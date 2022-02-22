<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<title>業販原価一括編集</title>
	<link rel="stylesheet" href="./css/corporateSaleCostListEdit.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
<!-- 	<script type="text/javascript" src="./js/prototype.js"></script> -->
	<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【業販原価一括編集画面】
ファイル名：editCorporateSaleCost.jsp
作成日：2015/12/28
作成者：大山智史

（画面概要）

助ネコ・新規売上登録で生成された売上商品データの検索/一覧画面。

・変更内容を保存：
・直近の原価を反映：
・一覧へ戻る：


（注意・補足）

-->


	<script type="text/javascript">

	$(document).ready(function(){
		$(".overlay").css("display", "none");
		
		function calcCost(value1, value2) {

			var listPrice = parseFloat(value1);
			var nrateOver = parseFloat((value2) * 0.01);
			var cost = Math.round(listPrice * nrateOver);

			return cost;
		}

		//小数点の位置を探るメソッド
		function getDotPosition(value) {

			// 数値のままだと操作できないので文字列化する
			var strVal = String(value);
			var dotPosition = 0;

			//小数点が存在するか確認
			// 小数点があったら位置を取得
			if (strVal.lastIndexOf('.') !== -1) {
				dotPosition = (strVal.length - 1) - strVal.lastIndexOf('.');
			}

			return dotPosition;
		}
		

 		$('.profitId').each(function(profit){
 			var index = $('.profitId').index(this);
			// 単価取得
			var pieceRate = removeComma($(".pieceRateEdit").eq(index).text());
			if (pieceRate == "") {
				pieceRate = 0;
			}
			
			pieceRate = parseInt(pieceRate);
			var cost = removeComma($(".cost").eq(index).val());
			cost = parseInt(cost);
			var postage = removeComma($(".domePostageKind").eq(index).val());
			
			var profit = parseInt(parseInt(pieceRate)/1.1) - parseInt(parseInt(pieceRate)*0.1) - parseInt(cost) - parseInt(postage);
			var color = '';
			if(profit < 0 ){
				color = "red";
			}else if(profit > 800){
				color = "white";
			}else {
				color = "orange";
			}
			profit = new String(profit).replace(/,/g, "");
			while (profit != (profit = profit.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
			$('.profitId').eq(index).html(profit + "&nbsp;円");
			$('.profitId').eq(index).attr('style', 'background-color:'+color+';');
		});		
 		
 		$(".domePostageKind").on('input',function(e){
  			var index = $('.domePostageKind').index(this);
			var postage = $(".domePostageKind").eq(index).val();
			$(".domePostage").eq(index).val(postage);
 		});
 		
 		$(".domePostage").on('input',function(e){
  			var index = $('.domePostage').index(this);
			var postage = $(".domePostage").eq(index).val();
			$(".domePostageKind").eq(index).val(postage);
 		});

	});



	$(function() {

		var result = false;

		if ($("#sysCorprateSaleItemIDListSize").val() != 0) {
			var slipPageNum = Math.ceil($("#sysCorprateSaleItemIDListSize").val() / $("#saleListPageMax").val());

			$(".slipPageNum").text(slipPageNum);
			$(".slipNowPage").text(Number($("#pageIdx").val()) + 1);

			var i;

			if (0 == $("#pageIdx").val()) {
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
			var pageIdx = new Number($("#pageIdx").val());

			// 現在のページが中央より左に表示される場合
			if (center >= pageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(slipPageNum);
				$(".liFirstPage").hide();

			// 現在のページが中央より右に表示される場合
			} else if (slipPageNum - (center - 1) <= pageIdx + 1) {

				startIdx = slipPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

			// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#pageIdx").val() - (center - 2);
				$(".lastIdx").html(slipPageNum);

			}

			$(".pageNum").html(startIdx);
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

				$(".3dotLineTop").hide();

			}

			if ((slipPageNum <= 8) || ((slipPageNum - center) <= (pageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (slipPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}


			for (i = startIdx; i < endIdx && i < slipPageNum; i++) {
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
//******************************************************************************************************

		//商品掛け率
		$(".priceTextRateOver").blur(function () {

			if($(this).val()==""){
				$(this).val(0);
				return;
			}
			if (!numberFloatCheck(this)){
				$(this).val(0);
				return;
			}
/* 			var numList = $(this).val().split(".");
			if(numList[0].length > 2 || numList[1].length > 2){
				alert("商品掛け率の桁数が多いです。");
				$(this).val(0);
				return;
			}
 */		});

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		// 変更内容を保存
		$(".savecorporateSaleCost").click (function (){

			if (confirm("変更を反映させます。よろしいですか？")) {
				removeCommaGoTransaction("savecorporateSaleCost.do");
			}
			return;
		});

		// 直近の原価を反映
		$(".reflectLatestCorporateSaleCost").click (function (){

			// 一覧のインデックスを設定
			$("#listIdx").val($(".reflectLatestCorporateSaleCost").index(this));

			goTransaction("reflectLatestCorporateSaleCost.do");

			return;
		});
		
		// 法人リンク
		$(".salesSlipLink").click(function () {

			var id = $(this).find(".sysCorporateSalesSlipId_link").val();
			$("#sysCorporateSalesSlipId").val(id);
			
			FwGlobal.submitForm(document.forms[0],"/initCorporateSaleDetail","CorporateSaleDetail" + $("#sysSalesSlipId").val(),"top=130,left=500,width=780px,height=520px,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1");

		});


		// 法人リンク
		$(".corpLink").click(function(){
			var corporationId = $(this).find(".sysCorporationId").val();
			$("#sysCorporationId").val(corporationId);
			$("#corpSaleCostPageIdx").val(0);
			$("#searchPreset").val(0);
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction('corporateSaleCostList.do');
		});


		// 入力した原価で金額算出
		$(".calcSaleCost").click (function (){

			// 一覧のインデックスを設定
			var index = $(".calcSaleCost").index(this);

			// 定価取得
			var listPrice = $(".listPrice").eq(index).val();
			if(listPrice == 0 || listPrice == ""){
				alert("定価が設定されていません。");
				return;
			}
			// 掛け率取得
			var rateOver = $(".itemRateOver").eq(index).val();
			if(rateOver == 0 || rateOver == ""){
				alert("掛け率が設定されていません。");
				return;
			}
			var postage = $(".domePostageKind").eq(index).val();
			$(".domePostage").eq(index).val(postage);

			var cRateOver = $(".corporationRateOver").eq(index).val();
			if(cRateOver == ""){
				cRateOver = 0;
			}

			// カンマを除去
			listPrice = removeComma(listPrice);
			rateOver = removeComma(rateOver);
			postage = removeComma(postage);
			cRateOver = removeComma(cRateOver);



			// カインドコストの計算処理
			// 定価と掛率に0.01を掛けた数値でカインドコストを算出する。
			var kindCost = parseInt(calcCost(listPrice,rateOver));

			$(".purchasingCost").eq(index).val(kindCost);
			addComma($(".purchasingCost").eq(index).val());

			// 原価の計算処理
			// 掛率と法人掛率で定価用の掛率を算出する。
			var rate = parseFloat(rateOver) + parseFloat(cRateOver);

			// 定価と定価用の掛け率から原価（メーカー）を算出
			var cost = parseInt(calcCost(listPrice, rate));

			$(".cost").eq(index).val(cost);
			addComma($(".cost").eq(index).val());

			// 単価取得
			var pieceRate = $(".pieceRateEdit").eq(index).val();
			if (pieceRate == "") {
				pieceRate = 0;
			}
			
			pieceRate = parseInt(pieceRate);
			
			var storeFlag = $(".storeFlag").eq(index).val();
			
			var profit = parseInt(parseInt(pieceRate)/1.1) - parseInt(parseInt(pieceRate)*0.1) - parseInt(cost) - parseInt(postage);

			var color = '';
			if(profit < 0 ){
				color = "red";
			}else if(profit > 800){
				color = "white";
			}else {
				color = "orange";
			}
			profit = new String(profit).replace(/,/g, "");
			while (profit != (profit = profit.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
			
			$('.profitId').eq(index).html(profit + "&nbsp;円");
			$('.profitId').eq(index).attr('style', 'background-color:'+color+';');

			return;
		});

		//Kind原価の計算メソッド (引数：定価、掛率)
		function calcCost(value1, value2) {

			var listPrice = parseFloat(value1);
			var nrateOver = parseFloat((value2) * 0.01);
			var cost = Math.round(listPrice * nrateOver);

			return cost;
		}

		//小数点の位置を探るメソッド
		function getDotPosition(value) {

			// 数値のままだと操作できないので文字列化する
			var strVal = String(value);
			var dotPosition = 0;

			//小数点が存在するか確認
			// 小数点があったら位置を取得
			if (strVal.lastIndexOf('.') !== -1) {
				dotPosition = (strVal.length - 1) - strVal.lastIndexOf('.');
			}

			return dotPosition;
		}

		function saveSaleCostById(index) {
			var sysSalesItemId = $(".sysCorporateSalesItemId").eq(index).val();
			var cost = removeComma($(".cost").eq(index).val());
			var kindCost = removeComma($(".purchasingCost").eq(index).val());
			var itemRateOver = $(".itemRateOver").eq(index).val();
			var listPrice = removeComma($(".listPrice").eq(index).val());
			var itemCode = $(".itemCodeValue").eq(index).val();
			var postage = removeComma($(".domePostage").eq(index).val());
			var sysSalesSlipId = $('.sysSalesSlipId').eq(index).val();
			var orderNum = $(".orderNum").eq(index).text();
			
			var profit = $('.profitId').eq(index).html();
			var list = profit.split("&nbsp;");
			profit = removeComma(list[0]);

			
/* 			if (cost == 0 || cost == "") {
				alert("単価が設定されていません。");
				return;
			}
			if (kindCost == 0 || kindCost == "") {
				alert("Kind原価が設定されていません。");
				return;
			}
			if (listPrice == 0 || listPrice == "") {
				alert("定価が設定されていません。");
				return;
			}
			if (itemRateOver == 0 || itemRateOver == "") {
				alert("掛け率が設定されていません。");
				return;
			}
 */
			
			if($(".costCheckFlag").eq(index).is(':checked') == true)
				var costCheckFlag = 1;
			else
				var costCheckFlag = 0;
			
			var returnIndex = index;

			
			$.ajax({
				type : 'post',
				url : './savecorporateSaleCostById.do',
				dataType : 'json',
				data : {
					'sysCorporateSalesItemId' : sysSalesItemId,
					'domePostage' : postage,
					'cost' : cost,
					'kindCost' : kindCost,
					'itemRateOver' : itemRateOver,
					'listPrice' : listPrice,
					'costCheckFlag' : costCheckFlag,
					'returnIndex' : returnIndex,
					'itemCode' : itemCode,
					'profit' : profit,
					'updatedFlag' : 1,
				}
			}).done(function(data) {

				if(!result) {
//					goTransaction("editCorporateSaleCost.do");

					alert('更新しました。');
				}

				result = true;

				var idx = data;

				var cost = $(".costEdit").eq(idx).children('input').val();
				cost = new String(cost).replace(/,/g, "");
				while (cost != (cost = cost.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
				$('.costEdit').eq(idx).html(cost + "&nbsp;円");
				
				var kindCost = $(".kindCostEdit").eq(idx).children('input').val();
				kindCost = new String(kindCost).replace(/,/g, "");
				while (kindCost != (kindCost = kindCost.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
				$('.kindCostEdit').eq(idx).html(kindCost + "&nbsp;円");
				
				var domePostage = $(".domePostageEdit").eq(idx).children('input').val();
				domePostage = new String(domePostage).replace(/,/g, "");
				while (domePostage != (domePostage = domePostage.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
				$('.domePostageEdit').eq(idx).html(domePostage + "&nbsp;円");

				$('.domePostageKindEdit').eq(idx).html(domePostage + "&nbsp;円");

				var listPrice = $(".listPriceEdit").eq(idx).children('input').val();
				listPrice = new String(listPrice).replace(/,/g, "");
				while (listPrice != (listPrice = listPrice.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
				$('.listPriceEdit').eq(idx).html(listPrice + "&nbsp;円");

				var itemRateOver = $(".itemRateOverEdit").eq(idx).children('input').val();
				itemRateOver = new String(itemRateOver).replace(/,/g, "");
				while (itemRateOver != (itemRateOver = itemRateOver.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
				$('.itemRateOverEdit').eq(idx).html(itemRateOver + "&nbsp;%");
			});
		}

		// 原価メーカのカーソルキー移動
		$(".cost").keyup (function (e){
			// 一覧のインデックスを設定
			var index = $(".cost").index(this);
			switch(e.which){
			case 39: // [→]
				$(".domePostage").eq(index).focus();
				$(".domePostage").eq(index).select();
				break;
			case 37: // [←]
				index--;
				$(".calcSaleCost").eq(index).focus();
				break;
			case 38: // [↑]
				index--;
				$(".cost").eq(index).focus();
				$(".cost").eq(index).select();
				break;
			case 40: // [↓]
				index++;
				$(".cost").eq(index).focus();
				$(".cost").eq(index).select();
				break;
			}
		});

		// 送料のカーソルキー移動
		$(".domePostage").keyup (function (e){
			// 一覧のインデックスを設定
			var index = $(".domePostage").index(this);
			switch(e.which){
			case 39: // [→]
				$(".purchasingCost").eq(index).focus();
				$(".purchasingCost").eq(index).select();
				break;
			case 37: // [←]
				$(".cost").eq(index).focus();
				$(".cost").eq(index).select();
				break;
			case 38: // [↑]
				index--;
				$(".domePostage").eq(index).focus();
				$(".domePostage").eq(index).select();
				break;
			case 40: // [↓]
				index++;
				$(".domePostage").eq(index).focus();
				$(".domePostage").eq(index).select();
				break;
			}
		});

		// Kind原価のカーソルキー移動
		$(".purchasingCost").keyup (function (e){
			// 一覧のインデックスを設定
			var index = $(".purchasingCost").index(this);
			switch(e.which){
			case 39: // [→]
				$(".listPrice").eq(index).focus();
				$(".listPrice").eq(index).select();
				break;
			case 37: // [←]
				$(".domePostage").eq(index).focus();
				$(".domePostage").eq(index).select();
				break;
			case 38: // [↑]
				index--;
				$(".purchasingCost").eq(index).focus();
				$(".purchasingCost").eq(index).select();
				break;
			case 40: // [↓]
				index++;
				$(".purchasingCost").eq(index).focus();
				$(".purchasingCost").eq(index).select();
				break;
			}
		});

		// 定価のカーソルキー移動
		$(".listPrice").keyup (function (e){
			// 一覧のインデックスを設定
			var index = $(".listPrice").index(this);
			switch(e.which){
			case 39: // [→]
				$(".domePostageKind").eq(index).focus();
				$(".domePostageKind").eq(index).select();
				break;
			case 37: // [←]
				$(".purchasingCost").eq(index).focus();
				$(".purchasingCost").eq(index).select();
				break;
			case 38: // [↑]
				index--;
				$(".listPrice").eq(index).focus();
				$(".listPrice").eq(index).select();
				break;
			case 40: // [↓]
				index++;
				$(".listPrice").eq(index).focus();
				$(".listPrice").eq(index).select();
				break;
			}
		});

		// 送料のカーソルキー移動
		$(".domePostageKind").keyup (function (e){
			// 一覧のインデックスを設定
			var index = $(".domePostageKind").index(this);
			switch(e.which){
			case 39: // [→]
				$(".itemRateOver").eq(index).focus();
				$(".itemRateOver").eq(index).select();
				break;
			case 37: // [←]
				$(".listPrice").eq(index).focus();
				$(".listPrice").eq(index).select();
				break;
			case 38: // [↑]
				index--;
				$(".domePostageKind").eq(index).focus();
				$(".domePostageKind").eq(index).select();
				break;
			case 40: // [↓]
				index++;
				$(".domePostageKind").eq(index).focus();
				$(".domePostageKind").eq(index).select();
				break;
			}
		});

		// 商品掛け率のカーソルキー移動
		$(".itemRateOver").keyup (function (e){
			// 一覧のインデックスを設定
			var index = $(".itemRateOver").index(this);
			switch(e.which){
			case 39: // [→]
				$(".calcSaleCost").eq(index).focus();
				break;
			case 37: // [←]
				$(".domePostageKind").eq(index).focus();
				$(".domePostageKind").eq(index).select();
				break;
			case 38: // [↑]
				index--;
				$(".itemRateOver").eq(index).focus();
				$(".itemRateOver").eq(index).select();
				break;
			case 40: // [↓]
				index++;
				$(".itemRateOver").eq(index).focus();
				$(".itemRateOver").eq(index).select();
				break;
			}
		});

		// 算出のカーソルキー移動
		$(".calcSaleCost").keyup (function (e){
			// 一覧のインデックスを設定
			var index = $(".calcSaleCost").index(this);
			switch(e.which){
			case 39: // [→]
				index++;
				$(".cost").eq(index).focus();
				$(".cost").eq(index).select();
				break;
			case 37: // [←]
				$(".itemRateOver").eq(index).focus();
				$(".itemRateOver").eq(index).select();
				break;
			case 38: // [↑]
				index--;
				$(".calcSaleCost").eq(index).focus();
				break;
			case 40: // [↓]
				index++;
				$(".calcSaleCost").eq(index).focus();
				break;
			}
		});

		$(".itemCodeLink").click(function () {

			var value = $(this).find(".itemCodeValue").val();
			
			$("#managementCode").val(value);

			goTransactionNew("searchDomesticExhibition.do");
		});

	});



	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/editCorporateSaleCost" enctype="multipart/form-data">
	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="headingKind">業販原価一括編集</h4>
	<nested:nest property="errorDTO">
	<nested:notEmpty property="errorMessage">
		<div id="errorArea">
			<p class="errorMessage"><nested:write property="errorMessage"/></p>
		</div>
	</nested:notEmpty>
	</nested:nest>

	<div class="middleArea">
		<table class="editButtonTable">
			<tr>
				<td><a class="button_main savecorporateSaleCost" href="Javascript:void(0);" tabindex="-1" >変更内容を保存</a></td>
				<td><a class="button_white" href="javascript: void(0);" onclick="goTransaction('corporateSaleCostListPageNo.do');" tabindex="-1" >一覧へ戻る</a></td>
			</tr>
		</table>
	</div>




	<div id="list_area" >
	<input type="hidden" name="sysCorporateSalesSlipId" id="sysCorporateSalesSlipId" />
	<nested:hidden property="sysCorprateSaleItemIDListSize" styleId="sysCorprateSaleItemIDListSize" />
	<nested:hidden property="corpSaleCostPageIdx" styleId="pageIdx" />
	<nested:hidden property="saleListPageMax" styleId="saleListPageMax" />
	<nested:hidden property="corpSaleCostListIdx" styleId="listIdx" />
		<table class="list_table">
			<tr>
				<th class="saleSlipNo">伝票番号</th>
				<th class="corporationNm">取引先法人</th>
				<th class="shipmentPlanDate">出庫予定日</th>
				<th class="itemCode">品番</th>
				<th class="itemNm">商品名</th>
				<th class="orderNm">注文数</th>
				<th class="pieceRate">単価</th>
				<th class="corporationRateOverHd">法人掛け率</th>
				<th class="costHd">原価(メーカー)</th>
				<th class="domePostageHd">送料</th>
				<th class="kindCostHd">Kind原価</th>
				<th class="listPriceHd">定価</th>
				<th class="domePostageHd">送料</th>
				<th class="itemRateOverHd">商品掛け率</th>
				<th class="calcHd">入力した定価で<br/>金額算出</th>
				<th class="reflectHd">直近の原価を<br/>反映</th>
				<th class="profitHd">利益判定</th>
				<th class="check">確認</th>
			</tr>

			<nested:iterate property="corpSalesCostList" indexId="idx">

<!-- 		マスタにない商品 -->
			<nested:notEqual property="sysItemId" value="0">
				<bean:define id="backgroundColor" value="" />
			</nested:notEqual>
			<nested:equal property="sysItemId" value="0">
				<bean:define id="backgroundColor" value="#FFFFC0" />
			</nested:equal>

			<tbody style="background:${backgroundColor};" class="salesSlipRow change_color_only">
			<nested:hidden property="sysCorporateSalesItemId" styleClass="sysCorporateSalesItemId" />
			<nested:hidden property="pieceRate"	styleClass="pieceRateHidden" />

			<tr>
					<td>
						<a href="Javascript:(void);" class="salesSlipLink">
							<nested:write property="saleSlipNo" />
							<nested:hidden property="sysCorporateSalesSlipId" styleClass="sysCorporateSalesSlipId_link"></nested:hidden>
						</a>
					</td>
				<td><nested:write property="corporationNm" /></td>
				<td><nested:write property="scheduledLeavingDate" /></td>
				<td>
					<a href="Javascript:(void);" class="itemCodeLink" >
						<nested:write property="itemCode" />
						<input type="hidden" name="managementCode" id="managementCode">
						<nested:hidden property="itemCode" styleClass="itemCodeValue"></nested:hidden>
					</a>
				
				</td>
				<td><nested:write property="itemNm" /></td>
				<td class="orderNum"><nested:write property="orderNum" /></td>
				<td class="pieceRateEdit"><nested:write property="pieceRate" format="###,###,###" />&nbsp;円</td>
				<td><nested:write property="corporationRateOver" />&nbsp;％
					<nested:hidden property="corporationRateOver" styleClass="corporationRateOver" />
				</td>
				<td><nested:text property="cost" styleClass="priceText cost" style="width: 80px; text-align: right;" maxlength="9" />&nbsp;円</td>
				<td><nested:text property="postage"
						styleClass="priceText domePostage"
						style="width: 80px; text-align: right;" maxlength="9" />&nbsp;円</td>
				<td><nested:text property="kindCost" styleClass="priceText purchasingCost" style="width: 80px; text-align: right;"  maxlength="9" />&nbsp;円</td>
				<nested:hidden property="updatedFlag" value="1"></nested:hidden>
				<td><nested:text property="listPrice" styleClass="priceText listPrice" style="width: 80px; text-align: right;"  maxlength="9" />&nbsp;円</td>
				<td><nested:text property="postage"
						styleClass="priceText domePostageKind"
						style="width: 80px; text-align: right;" maxlength="9" />&nbsp;円</td>
				<td><nested:text property="itemRateOver" styleClass="priceTextRateOver itemRateOver" style="width: 80px; text-align: right;"  maxlength="9" />&nbsp;％</td>
				<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main calcSaleCost" tabindex="-1" >算出</a></td>
				<td class="tdButton"><a href="Javascript:void(0);" class="button_small_main reflectLatestCorporateSaleCost" tabindex="-1" >反映</a></td>
				<td class="profitId"><nested:write property="profit" format="###,###,###" />&nbsp;円</td>
				<td><nested:checkbox property="costCheckFlag" styleClass="costCheckFlag"  /></td>
				<nested:hidden property="costCheckFlag" value="off" />
			</tr>
			</tbody>
			</nested:iterate>
			</table>
		</div>

		<footer class="footer buttonArea">
			<ul style="position: relative;">
				<li class="footer_button">
					<a class="button_main savecorporateSaleCost" href="Javascript:void(0);" tabindex="-1" >変更内容を保存</a>
				</li>
				<li class="footer_button">
					<a class="button_white" href="javascript: void(0);" onclick="goTransaction('corporateSaleCostListPageNo.do');" tabindex="-1" >一覧へ戻る</a>
				</li>
			</ul>
		</footer>


	</html:form>
</html:html>
