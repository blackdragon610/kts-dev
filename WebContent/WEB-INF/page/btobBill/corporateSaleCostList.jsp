<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<title>業販原価入力</title>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/corporateSaleCostList.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
<!-- 	<script type="text/javascript" src="./js/prototype.js"></script> -->
	<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>
	<script src="./js/shortcut.js"></script>

<!--
【業販原価一覧画面】
ファイル名：corporateSaleCostList.jsp
作成日：2016/01/04
作成者：髙桑

（画面概要）


業販原価データの検索/一覧画面。
・行ダブルクリック：詳細画面に遷移する。
・商品毎のピッキングリスト出力済みフラグは、画面から操作、更新が可能。




（注意・補足）

-->
	<script>
	$(document).ready(function(){
		$(".overlay").css("display", "none");
		$("#sortFirstSub").val(1);

		// 法人リンクの選択文字
		$("#corpLink<bean:write name="btobBillForm" property="corpSaleCostSearchDTO.sysCorporationId" />").removeAttr("href");
		$("#corpLink<bean:write name="btobBillForm" property="corpSaleCostSearchDTO.sysCorporationId" />").parents("li").attr("class", "corp");
		$("#corpLink<bean:write name="btobBillForm" property="corpSaleCostSearchDTO.sysCorporationId" />").attr("class", "selected");
		
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
			var cost = removeComma($(".costEdit").eq(index).text());
			cost = parseInt(cost);
			var postage = removeComma($(".domePostageKindEdit").eq(index).text());
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
 		
 		$(".domePostageKindEdit").on('input',function(e){
  			var index = $('.domePostageKindEdit').index(this);
			var postage = $(".domePostageKindEdit").eq(index).children('input').val();
			$(".domePostageEdit").eq(index).children('input').val(postage);
 		});
 		
 		$(".domePostageEdit").on('input',function(e){
  			var index = $('.domePostageEdit').index(this);
			var postage = $(".domePostageEdit").eq(index).children('input').val();
			$(".domePostageKindEdit").eq(index).children('input').val(postage);
 		});

	});

	$(function() {
		var result = false;
		$(".saleCostEdit").click(function(){
			var index = $(".saleCostEdit").index(this);
			
			if($(this).html() == "保存"){

				if (confirm("保存しますか？")) {
					result = false;
			        saveSaleCostById(index);
				}

			}else{
				$(this).html('保存');

				$(".calcSaleCost").eq(index).attr('disabled', false);
				$(".reflectLatestSaleCostCost").eq(index).attr('disabled', false);

				var cost = removeComma($(".costEdit").eq(index).text());
				cost = parseInt(cost);
				
				$(".costEdit").eq(index).html("<input type='text' name='cost' id='cost' class='priceText' value='" + cost + "' style='width: 80px; text-align: right;' maxlength='9'>")

				var kindCost = removeComma($(".kindCostEdit").eq(index).text());
				kindCost = parseInt(kindCost);

				$(".kindCostEdit").eq(index).html("<input type='text' name='kindCost' id='kindCost' class='priceText' value='" + kindCost + "' style='width: 80px; text-align: right;' maxlength='9'>")

				var domePostage = removeComma($(".domePostageEdit").eq(index).text());
				domePostage = parseInt(domePostage);

				$(".domePostageEdit").eq(index).html("<input type='text' name='domePostage' id='domePostage' class='priceText' value='" + domePostage + "' style='width: 80px; text-align: right;' maxlength='9'>")

				var domePostageKind = removeComma($(".domePostageKindEdit").eq(index).text());
				domePostageKind = parseInt(domePostageKind);

				$(".domePostageKindEdit").eq(index).html("<input type='text' name='domePostageKind' id='domePostageKind' class='priceText' value='" + domePostageKind + "' style='width: 80px; text-align: right;' maxlength='9'>")

				var listPrice = removeComma($(".listPriceEdit").eq(index).text());
				listPrice = parseInt(listPrice);

				$(".listPriceEdit").eq(index).html("<input type='text' name='listPrice' id='listPrice' class='priceText' value='" + listPrice + "' style='width: 80px; text-align: right;' maxlength='9'>")
			
				var itemRateOver = removeComma($(".itemRateOverEdit").eq(index).text());
				itemRateOver = parseFloat(itemRateOver);

				$(".itemRateOverEdit").eq(index).html("<input type='text' name='itemRateOver' id='itemRateOver' class='priceText' value='" + itemRateOver + "' style='width: 80px; text-align: right;' maxlength='9'>")
			
				$(".costCheck").eq(index).children('input').prop('disabled', false);
			}
		})
		
				// 変更内容を保存
		$(".saveSaleCost").click(function() {

			if (confirm("変更を反映させます。よろしいですか？")) {
				result = false;
				$(".saleCostEdit").each(function(index, item){                 
					if ($(".saleCostEdit").eq(index).html() == "保存") {
				        console.log($(".saleCostEdit").eq(index).html());
				        saveSaleCostById(index);
					}
			    });    
			}
			return;
		});
		
		function saveSaleCostById(index) {
			$(".saleCostEdit").eq(index).html('編集');

			var sysCorporateSalesItemId = $(".sysCorporateSalesItemId").eq(index).val();
			var postage = removeComma($(".domePostageEdit").eq(index).children('input').val());
			var cost = removeComma($(".costEdit").eq(index).children('input').val());
			var kindCost = removeComma($(".kindCostEdit").eq(index).children('input').val());
			var itemRateOver = $(".itemRateOverEdit").eq(index).children('input').val();
			var listPrice = removeComma($(".listPriceEdit").eq(index).children('input').val());
			var itemCode = $(".itemCodeValue").eq(index).val();
			var orderNum = $(".orderNumEdit").eq(index).text();
			
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
			
			if($(".costCheck").eq(index).children('input').is(':checked') == true)
				var costCheckFlag = 1;
			else
				var costCheckFlag = 0;
			
			var returnIndex = index;

			
			$.ajax({
				type : 'post',
				url : './savecorporateSaleCostById.do',
				dataType : 'json',
				data : {
					'sysCorporateSalesItemId' : sysCorporateSalesItemId,
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
					$("#searchPreset").val(0);
//					removeCommaList($(".priceTextMinus"));
//					removeCommaGoTransaction('corporateSaleCostList.do');

					alert('更新しました。');
				}

				result = true;
				var idx = data;

/* 				var listPrice1 = $(".listPriceEdit").eq(idx).children('input').val();
				
				// 掛け率取得
				var rateOver1 = $(".itemRateOverEdit").eq(idx).children('input').val();

				// 法人掛け率取得
				var cRateOver1 = $(".corporationRateOverEdit").eq(idx).text();
				if (cRateOver1 == "") {
					cRateOver1 = 0;
				}

				// 原価の計算処理
				// 掛率と法人掛率で定価用の掛率を算出する。
				var rate = parseFloat(rateOver1) + parseFloat(removeComma(cRateOver1));

				// 定価と定価用の掛け率から原価（メーカー）を算出
				var costArray = calcCost(listPrice1, rate);
				
				var tempCost = (costArray[0] * costArray[1]) / costArray[2];
				var cost = parseInt(tempCost);
 */
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
				
				$(".costCheck").eq(idx).children('input').prop('disabled', true);
				$(".calcSaleCost").eq(idx).attr('disabled', true);
				$(".reflectLatestSaleCostCost").eq(index).attr('disabled', true);
				
			});
		}

		// 直近の原価を反映
		$(".reflectLatestSaleCostCost").click(function() {

			// 一覧のインデックスを設定
			$("#sysSalesIndex").val($(".reflectLatestSaleCostCost").index(this));

			var sysSalesIndex = $(".reflectLatestSaleCostCost").index(this);
			
			$.ajax({
				type : 'post',
				url : './reflectLatestCorporateSaleCostById.do',
				dataType : 'json',
				data:{
					'sysSalesIndex' : sysSalesIndex,
				}
			}).done(function(data) {

				console.log(data);
				var returnArray = data.split(",");
				
				console.log(returnArray);
				
				var index = returnArray[0];
				var cost = returnArray[1];
				var kindCost = returnArray[2];
				var domePostage = returnArray[3];
				var listPrice = returnArray[4];
				var itemRateOver = returnArray[5];

				$(".costEdit").eq(index).html("<input type='text' name='cost' id='cost' class='priceText' value='" + cost + "' style='width: 80px; text-align: right;' maxlength='9'>")

				$(".kindCostEdit").eq(index).html("<input type='text' name='kindCost' id='kindCost' class='priceText' value='" + kindCost + "' style='width: 80px; text-align: right;' maxlength='9'>")

				$(".domePostageEdit").eq(index).html("<input type='text' name='domePostage' id='domePostage' class='priceText' value='" + domePostage + "' style='width: 80px; text-align: right;' maxlength='9'>")

				$(".domePostageKindEdit").eq(index).html("<input type='text' name='domePostageKind' id='domePostageKind' class='priceText' value='" + domePostage + "' style='width: 80px; text-align: right;' maxlength='9'>")

				$(".listPriceEdit").eq(index).html("<input type='text' name='listPrice' id='listPrice' class='priceText' value='" + listPrice + "' style='width: 80px; text-align: right;' maxlength='9'>")
			
				$(".itemRateOverEdit").eq(index).html("<input type='text' name='itemRateOver' id='itemRateOver' class='priceText' value='" + itemRateOver + "' style='width: 80px; text-align: right;' maxlength='9'>")
			
				$(".costCheck").eq(index).children('input').prop('disabled', false);
			
			});


			return;
		});

		// 入力した原価で金額算出
		$(".calcSaleCost")
				.click(
						function() {

							// 一覧のインデックスを設定
							var index = $(".calcSaleCost").index(this);

							// 定価取得
							var listPrice = $(".listPriceEdit").eq(index).children('input').val();
							
							if (listPrice == 0 || listPrice == "") {
								alert("定価が設定されていません。");
								return;
							}

							// 掛け率取得
							var rateOver = $(".itemRateOverEdit").eq(index).children('input').val();
							if (rateOver == 0 || rateOver == "") {
								alert("掛け率が設定されていません。");
								return;
							}

							// 送料取得
							var postage = $(".domePostageKindEdit").eq(index).children('input').val();
							$(".domePostageEdit").eq(index).children('input').val(postage);

							// 法人掛け率取得
							var cRateOver = $(".corporationRateOverEdit").eq(index).text();
							if (cRateOver == "") {
								cRateOver = 0;
							}

							// カンマを除去
							listPrice = removeComma(listPrice);
							rateOver = removeComma(rateOver);
							postage = removeComma(postage);
							cRateOver = removeComma(cRateOver);

							// カインドコストの計算処理
							// 定価と掛率に0.01を掛けた数値でカインドコストを算出する。
							var kindCost = parseInt(calcCost(parseInt(listPrice), parseFloat(rateOver)));

							$(".kindCostEdit").eq(index).children('input').val(kindCost);
							addComma($(".kindCostEdit").eq(index).children('input').val());

							// 原価の計算処理
							// 掛率と法人掛率で定価用の掛率を算出する。
							var rate = parseFloat(rateOver) + parseFloat(cRateOver);

							// 定価と定価用の掛け率から原価（メーカー）を算出
							var cost = parseInt(calcCost(parseInt(listPrice), rate));

							$(".costEdit").eq(index).children('input').val(cost);
							addComma($(".costEdit").eq(index).children('input').val());
							
							
							// 単価取得
							var pieceRate = removeComma($(".pieceRateEdit").eq(index).text());
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
		
		
		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		for (var i = 1; i <= 9; i++) {
			shortcut.add("Alt+" + i, function(e){
				$("#sysCorporationId").val(Number(e.keyCode) - 48);
				goTransaction("initRegistryCorporateSales.do");
			});
		}

		pickingListFlgChange = 0;

	    $(".clear").click(function(){
	        $("#searchOptionArea input, #searchOptionArea select").each(function(){
	            if (this.type == "checkbox" || this.type == "radio") {
	                this.checked = false;
	            } else {
	                $(this).val("");
	            }
	        });
	        $(".slipStatus").eq(0).prop("checked", true);
	        $(".pickingListFlg").eq(0).prop("checked", true);
	        $(".leavingFlg").eq(0).prop("checked", true);
	    });
		$('#searchOptionOpen').click(function () {

			if($('#searchOptionOpen').text() == "▼検索") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼検索");
			}

			$('#searchOptionArea').slideToggle("fast");
		});

		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

		$(".nyukin").children("a").click(function(){
			$(this).hide();
			$(this).parent().append("<input type='text' class='nyukingaku' />");
		});

		//法人メニュー開閉
		$(".corptgl").click(function(){
			$(this).parent(".corp").find(".corpmenu").toggle();
		});



//******************************************************************************************************
	     if($('.slipNoExist').prop('checked') || $('.slipNoHyphen').prop('checked')) {
	    	 $('.slipNoNone').attr('disabled','disabled');
	     }

		 if($('.slipNoNone').prop('checked')) {
		    	$('.slipNoExist').attr('disabled','disabled');
		    	$('.slipNoHyphen').attr('disabled','disabled');
		    }

//******************************************************************************************************
		if ($("#sysCorprateSaleItemIDListSize").val() != 0) {
			var slipPageNum = Math.ceil($("#sysCorprateSaleItemIDListSize").val() / $("#corpSaleListPageMax").val());

			$(".slipPageNum").text(slipPageNum);
			$(".slipNowPage").text(Number($("#corpSaleCostPageIdx").val()) + 1);

			var i;

			if (0 == $("#corpSaleCostPageIdx").val()) {
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
			var corpSaleCostPageIdx = new Number($("#corpSaleCostPageIdx").val());

			// 現在のページが中央より左に表示される場合
			if (center >= corpSaleCostPageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(slipPageNum);
				$(".liFirstPage").hide();

			// 現在のページが中央より右に表示される場合
			} else if (slipPageNum - (center - 1) <= corpSaleCostPageIdx + 1) {

				startIdx = slipPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

			// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#corpSaleCostPageIdx").val() - (center - 2);
				$(".lastIdx").html(slipPageNum);

			}

			$(".pageNum").html(startIdx);
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

 				$(".3dotLineTop").hide();

 			}

			if ((slipPageNum <= 8) || ((slipPageNum - center) <= (corpSaleCostPageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (slipPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}


			for (i = startIdx; i < endIdx && i < slipPageNum; i++) {
				var clone = $(".pager > li:eq(3)").clone();
				clone.children(".pageNum").text(i + 1);

				if (i == $("#corpSaleCostPageIdx").val()) {

					clone.find("a").attr("class", "pageNum nowPage");

				} else {
					clone.find("a").attr("class", "pageNum");
				}

 				$(".pager > li.3dotLineEnd").before(clone);
			}
		}
//******************************************************************************************************
		$(".pageNum").click (function () {

			if ($("#corpSaleCostPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corpSaleCostPageIdx").val($(this).text() - 1);
			goTransaction("corporateSaleCostListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corpSaleCostPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#corpSaleCostPageIdx").val(Number($("#corpSaleCostPageIdx").val()) + 1);
			goTransaction("corporateSaleCostListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#corpSaleCostPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#corpSaleCostPageIdx").val(Number($("#corpSaleCostPageIdx").val()) - 1);
			goTransaction("corporateSaleCostListPageNo.do");
		});

//ページ送り（上側）
		//先頭ページへ
		$("#firstPage").click (function () {

			if ($("#corpSaleCostPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#corpSaleCostPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corpSaleCostPageIdx").val(0);

			goTransaction("corporateSaleCostListPageNo.do");
		});

		//最終ページへ
		$("#lastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corpSaleCostPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#corpSaleCostPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corpSaleCostPageIdx").val(maxPage - 1);

			goTransaction("corporateSaleCostListPageNo.do");
		});

// ページ送り（下側）
		//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corpSaleCostPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#corpSaleCostPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corpSaleCostPageIdx").val(Number($("#corpSaleCostPageIdx").val()) + 1);

			goTransaction("corporateSaleCostListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#corpSaleCostPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#corpSaleCostPageIdx").val(Number($("#corpSaleCostPageIdx").val()) - 1);
			goTransaction("corporateSaleCostListPageNo.do");
		});

		//20140403 安藤　ここから
		//先頭ページへ
		$("#underFirstPage").click (function () {

			if ($("#corpSaleCostPageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#corpSaleCostPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corpSaleCostPageIdx").val(0);
			goTransaction("corporateSaleCostListPageNo.do");
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#corpSaleCostPageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#corpSaleCostPageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#corpSaleCostPageIdx").val(maxPage - 1);
			goTransaction("corporateSaleCostListPageNo.do");
		});

//******************************************************************************************************


		$(".search").click (function () {

			$("#searchPreset").val(0);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction('corporateSaleCostList.do');
		});
		
        $(window).keydown(function(event){
            if(event.keyCode == 13) {
    			$("#searchPreset").val(0);
    			$(".overlay").css("display", "block");
    			$(".message").text("検索中");
    			removeCommaList($(".priceTextMinus"));
    			removeCommaGoTransaction('corporateSaleCostList.do');
            }
        });

		//売上詳細
		$(".corporateSalesSlipRow").dblclick(function () {

			$("#sysCorporateSalesSlipId").val($(this).find(".sysCorporateSalesSlipId").val());
			goTransaction("initCorporateSaleDetail.do");
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

		$(".editCorporateSaleCost").click (function (){

			goTransaction("editCorporateSaleCost.do");
		});


		$(".pickingListFlg").change(function () {
			pickingListFlgChange = 1;
		});
		
		$(".itemCodeLink").click(function () {

			var value = $(this).find(".itemCodeValue").val();
			
			$("#managementCode").val(value);

			goTransactionNew("searchDomesticExhibition.do");
		});

		// 原価メーカのカーソルキー移動
		$(".costEdit").keyup(function(e) {
			// 一覧のインデックスを設定
			var index = $(".costEdit").index(this);
			switch (e.which) {
			case 39: // [→]
				$(".domePostageEdit").eq(index).children('input').focus();
				$(".domePostageEdit").eq(index).children('input').select();
				break;
			case 37: // [←]
				index--;
				$(".calcSaleCost").eq(index).focus();
				break;
			case 38: // [↑]
				index--;
				$(".costEdit").eq(index).children('input').focus();
				$(".costEdit").eq(index).children('input').select();
				break;
			case 40: // [↓]
				index++;
				$(".costEdit").eq(index).children('input').focus();
				$(".costEdit").eq(index).children('input').select();
				break;
			}
		});

		// 送料のカーソルキー移動
		$(".domePostageEdit").keyup(function(e) {
			// 一覧のインデックスを設定
			var index = $(".domePostageEdit").index(this);
			switch (e.which) {
			case 39: // [→]
				$(".kindCostEdit").eq(index).children('input').focus();
				$(".kindCostEdit").eq(index).children('input').select();
				break;
			case 37: // [←]
				$(".costEdit").eq(index).children('input').focus();
				$(".costEdit").eq(index).children('input').select();
				break;
			case 38: // [↑]
				index--;
				$(".domePostageEdit").eq(index).children('input').focus();
				$(".domePostageEdit").eq(index).children('input').select();
				break;
			case 40: // [↓]
				index++;
				$(".domePostageEdit").eq(index).children('input').focus();
				$(".domePostageEdit").eq(index).children('input').select();
				break;
			}
		});

		// Kind原価のカーソルキー移動
		$(".kindCostEdit").keyup(function(e) {
			// 一覧のインデックスを設定
			var index = $(".kindCostEdit").index(this);
			switch (e.which) {
			case 39: // [→]
				$(".listPriceEdit").eq(index).children('input').focus();
				$(".listPriceEdit").eq(index).children('input').select();
				break;
			case 37: // [←]
				$(".domePostageEdit").eq(index).children('input').focus();
				$(".domePostageEdit").eq(index).children('input').select();
				break;
			case 38: // [↑]
				index--;
				$(".kindCostEdit").eq(index).children('input').focus();
				$(".kindCostEdit").eq(index.children('input')).select();
				break;
			case 40: // [↓]
				index++;
				$(".kindCostEdit").eq(index).children('input').focus();
				$(".kindCostEdit").eq(index).children('input').select();
				break;
			}
		});

		// 定価のカーソルキー移動
		$(".listPriceEdit").keyup(function(e) {
			// 一覧のインデックスを設定
			var index = $(".listPriceEdit").index(this);
			switch (e.which) {
			case 39: // [→]
				$(".domePostageKindEdit").eq(index).children('input').focus();
				$(".domePostageKindEdit").eq(index).children('input').select();
				break;
			case 37: // [←]
				$(".kindCostEdit").eq(index).children('input').focus();
				$(".kindCostEdit").eq(index).children('input').select();
				break;
			case 38: // [↑]
				index--;
				$(".listPriceEdit").eq(index).children('input').focus();
				$(".listPriceEdit").eq(index).children('input').select();
				break;
			case 40: // [↓]
				index++;
				$(".listPriceEdit").eq(index).children('input').focus();
				$(".listPriceEdit").eq(index).children('input').select();
				break;
			}
		});


		// 送料のカーソルキー移動
		$(".domePostageKindEdit").keyup(function(e) {
			// 一覧のインデックスを設定
			var index = $(".domePostageKindEdit").index(this);
			switch (e.which) {
			case 39: // [→]
				$(".itemRateOverEdit").eq(index).children('input').focus();
				$(".itemRateOverEdit").eq(index).children('input').select();
				break;
			case 37: // [←]
				$(".listPriceEdit").eq(index).children('input').focus();
				$(".listPriceEdit").eq(index).children('input').select();
				break;
			case 38: // [↑]
				index--;
				$(".domePostageKindEdit").eq(index).children('input').focus();
				$(".domePostageKindEdit").eq(index).children('input').select();
				break;
			case 40: // [↓]
				index++;
				$(".domePostageKindEdit").eq(index).children('input').focus();
				$(".domePostageKindEdit").eq(index).children('input').select();
				break;
			}
		});

		// 商品掛け率のカーソルキー移動
		$(".itemRateOverEdit").keyup(function(e) {
			// 一覧のインデックスを設定
			var index = $(".itemRateOverEdit").index(this);
			switch (e.which) {
			case 39: // [→]
				$(".calcSaleCost").eq(index).focus();
				break;
			case 37: // [←]
				$(".domePostageKindEdit").eq(index).children('input').focus();
				$(".domePostageKindEdit").eq(index).children('input').select();
				break;
			case 38: // [↑]
				index--;
				$(".itemRateOverEdit").eq(index).children('input').focus();
				$(".itemRateOverEdit").eq(index).children('input').select();
				break;
			case 40: // [↓]
				index++;
				$(".itemRateOverEdit").eq(index).children('input').focus();
				$(".itemRateOverEdit").eq(index).children('input').select();
				break;
			}
		});

		// 算出のカーソルキー移動
		$(".calcSaleCost").keyup(function(e) {
			// 一覧のインデックスを設定
			var index = $(".calcSaleCost").index(this);
			switch (e.which) {
			case 39: // [→]
				index++;
				$(".costEdit").eq(index).children('input').focus();
				$(".costEdit").eq(index).children('input').select();
				break;
			case 37: // [←]
				$(".itemRateOverEdit").children('input').eq(index).focus();
				$(".itemRateOverEdit").children('input').eq(index).select();
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
		
	});



</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initCorporateSaleCostList"  enctype="multipart/form-data">
	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="headingKind">業販原価入力</h4>
	<nested:hidden property="sysCorporateSalesSlipId" styleId="sysCorporateSalesSlipId"/>


	<ul class="hmenu mb10">
		<nested:iterate property="corporationList" id="corporation">
			<li class="corp corpLink">
				<a href="javascript:void(0);" id="corpLink<nested:write property="sysCorporationId" />"><nested:write property="abbreviation" /></a>
				<nested:hidden property="sysCorporationId" styleClass="sysCorporationId" />
			</li>
		</nested:iterate>
		<li class="corp corpLink"><a href="javascript:void(0);"  id="corpLink0">ALL</a><input type="hidden" class="sysCorporationId" value="0" /></li>
	</ul>
	<fieldset class="searchOptionField">
		<legend id="searchOptionOpen">▲隠す</legend>
		<nested:nest property="corpSaleCostSearchDTO">
			<nested:hidden property="searchPreset" styleId="searchPreset" />
			<div id="searchOptionArea">
				<table id="search_option">
					<tr>
						<td colspan="8">
							<div class="flgCheck fl">
								<ul>
									<li>ピッキングリスト</li>
									<li><label><nested:radio property="pickingListFlg" value="0" styleClass="pickingListFlg" />ピッキング前の商品を含む伝票を検索する</label></li>
									<li><label><nested:radio property="pickingListFlg" value="1" styleClass="pickingListFlg" />全てピッキング済みの伝票を検索する</label></li>
									<li><label><nested:radio property="pickingListFlg" value="2" styleClass="pickingListFlg" />全てピッキング前の伝票を検索する</label></li>
								</ul>
							</div>
							<div class="arrow fl mt30">→</div>
							<div class="flgCheck fl">
								<ul>
									<li>出庫</li>
									<li><label><nested:radio property="leavingFlg" value="0" styleClass="leavingFlg" />未出庫の商品を含む伝票を検索する</label></li>
									<li><label><nested:radio property="leavingFlg" value="1" styleClass="leavingFlg" />全て出庫済みの伝票を検索する</label></li>
									<li><label><nested:radio property="leavingFlg" value="2" styleClass="leavingFlg" />全て未出庫の伝票を検索する</label></li>
								</ul>
							</div>
							<div class="fl mt30">
								<span class="pdg_left_30px"><label><nested:checkbox property="returnFlg" />返品伝票</label></span>
								<span class="flgCheck"><label><nested:checkbox property="invalidFlag" />無効伝票</label></span>
								<span class="pdg_left_30px"><label><nested:checkbox property="searchAllFlg" />全件表示</label></span>
							</div>
							<div class="fl" style="margin-top:14px;">
									<table id="costTable">
										<tr>
											<td>原価</td>
											<td><label><nested:checkbox property="costMakerItemFlg"/>メーカー品</label></td>
											<td><label><nested:checkbox property="costNoRegistry"/>原価未入力</label></td>
											<td><label><nested:checkbox property="costZeroRegistry"/>原価が0円</label></td>
										</tr>
										<tr>
											<td>原価確認</td>
											<td><label><nested:checkbox property="costNoCheckFlg"/>未確認</label></td>
											<td><label><nested:checkbox property="costCheckedFlg"/>確認済</label></td>
										</tr>
									</table>
							</div>
						</td>
					</tr>
					<tr>
						<td>見積日</td>
						<td style="white-space: nowrap;"><nested:text property="estimateDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="estimateDateTo" styleClass="calender"  maxlength="10" /></td>
						<td>ステータス</td>
						<td colspan="3">
<%-- 							<span><label><nested:checkbox property="slipStatusEstimate" />見積</label></span> --%>
<%-- 							<span><label><nested:checkbox property="slipStatusOrder" />受注</label></span> --%>
<%-- 							<span><label><nested:checkbox property="slipStatusSales" />売上</label></span> --%>
								<label><nested:radio property="slipStatus" value="1" styleClass="slipStatus" />見積</label>
								<label><nested:radio property="slipStatus" value="2" styleClass="slipStatus" />受注</label>
								<label><nested:radio property="slipStatus" value="3" styleClass="slipStatus" />売上</label>
						</td>
						<td>品番（前方一致）</td>
						<td><nested:text property="itemCode" styleClass="text_w120 numText" maxlength="11" /></td>
					</tr>
					<tr>
						<td>受注日</td>
						<td style="white-space: nowrap;"><nested:text property="orderDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="orderDateTo" styleClass="calender"  maxlength="10" /></td>
						<td>伝票番号</td>
						<td><nested:text property="orderNo" /></td>
						<td>運送会社</td>
						<td><nested:select property="transportCorporationSystem">
								<html:optionsCollection property="transportCorporationSystemMap" label="value" value="key"/>
						</nested:select></td>
					</tr>
					<tr>
						<td>
							出庫予定日
							<nested:define id="dateFrom" property="scheduledLeavingDateFrom" />
							<input type="hidden" value="${dateFrom}" id="scheduledLeavingDateFrom" />
							<nested:define id="dateTo" property="scheduledLeavingDateTo" />
							<input type="hidden" value="${dateTo}" id="scheduledLeavingDateTo" />
						</td>
						<td style="white-space: nowrap;"><nested:text property="scheduledLeavingDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="scheduledLeavingDateTo" styleClass="calender"  maxlength="10" /></td>

						<td>法人</td>
						<td>
							<nested:select property="sysCorporationId" styleId="sysCorporationId">
								<option value="0">　</option>
								<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
							</nested:select>
						</td>
						<td>売掛残</td>
						<td><nested:select property="receivableBalance">
							<html:option value="0">　</html:option>
							<html:option value="1">有</html:option>
							<html:option value="2">無</html:option>
						</nested:select></td>
						<td>他社品番（部分一致）</td>
						<td><nested:text property="salesItemCode" styleClass="text_w120" maxlength="30" /></td>
					</tr>
					<tr>
						<td>出庫日</td>
						<td style="white-space: nowrap;"><nested:text property="leavingDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="leavingDateTo" styleClass="calender"  maxlength="10" /></td>
						<td>得意先番号</td>
						<td><nested:text property="clientNo" /></td>
						<td>並び替え</td>
						<td>
							<nested:select property="sortFirst">
								<html:optionsCollection property="saleSearchMap" value="key" label="value" />
							</nested:select>
							<nested:select property="sortFirstSub" styleId="sortFirstSub">
								<html:optionsCollection property="saleSearchSortOrder" value="key" label="value" />
							</nested:select>
						</td>
						<td>商品名</td>
						<td><nested:text property="itemNm" styleClass="text_w200" /></td>
					</tr>
					<tr>
						<td>売上日</td>
						<td style="white-space: nowrap;"><nested:text property="salesDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="salesDateTo" styleClass="calender"  maxlength="10" /></td>
						<td>得意先名</td>
						<td><nested:text property="clientNm" /></td>
						<td>表示件数</td>
						<td>
							<nested:select property="listPageMax">
								<html:optionsCollection property="listPageMaxMap" value="key" label="value" />
							</nested:select>&nbsp;件
						</td>
						<td>他社商品名</td>
						<td><nested:text property="salesItemNm" styleClass="text_w200" /></td>
					</tr>
					<tr>
						<td>請求日</td>
						<td style="white-space: nowrap;"><nested:text property="billingDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="billingDateTo" styleClass="calender"  maxlength="10" /></td>
						<td>担当者名</td>
						<td><nested:text property="personInCharge" /></td>
						<td>支払方法</td>
						<td><nested:select property="paymentMethod">
							<html:option value="0">　</html:option>
							<html:optionsCollection property="paymentMethodMap" label="value" value="key" />
						</nested:select></td>

						<td>問屋名</td>
						<td><nested:text property="wholseSalerName" styleClass="text_w200" /></td>
						
					</tr>
					<tr>
						<td colspan="2" class="td_center" style="padding-left: 20px;"><a class="button_main search" href="javascript:void(0);">検索</a></td>
						<td colspan="2"><a class="button_white clear" href="javascript:void(0);">リセット</a></td>
					</tr>
				</table>
			</div>
		</nested:nest>
	</fieldset>

	<nested:nest property="errorDTO">
	<nested:notEmpty property="errorMessage">
		<div id="errorArea">
			<p class="errorMessage"><nested:write property="errorMessage"/></p>
		</div>
	</nested:notEmpty>
	</nested:nest>

<nested:notEmpty property="corpSalesCostList">
	<div class="middleArea">
		<table class="editButtonTable">
			<tr>
				<td><a class="button_main editCorporateSaleCost" href="Javascript:void(0);">一括編集する</a></td>
				<td><a class="button_main saveSaleCost" href="Javascript:void(0);">一括保存する</a></td>
			</tr>
		</table>
	</div>
	<div class="paging_area">
		<div class="paging_total_top">
			<h3>全&nbsp;<nested:write property="sysCorprateSaleItemIDListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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
		<nested:hidden property="sysCorprateSaleItemIDListSize" styleId="sysCorprateSaleItemIDListSize" />
		<nested:hidden property="corpSaleCostPageIdx" styleId="corpSaleCostPageIdx" />
		<nested:hidden property="corpSaleListPageMax" styleId="corpSaleListPageMax" />
		<input type="hidden" name="sysSalesIndex" id="sysSalesIndex" />
		<table id="mstTable" class="list_table">
			<tr>
				<th class="saleSlipNo">伝票番号</th>
				<th class="corporationNm">取引先法人</th>
				<th class="shipmentPlanDate">出庫予定日</th>
				<th class="itemCode">品番</th>
				<th class="itemNm">商品名</th>
				<th class="orderNm">注文数</th>
				<th class="pieceRate">単価</th>
				<th class="corporationRateOverHd">法人掛け率</th>
				<th class="cost">原価(メーカー)</th>
				<th class="domePostage">送料</th>
				<th class="kindCost">Kind原価</th>
				<th class="listPrice">定価</th>
				<th class="domePostage">送料</th>
				<th class="itemRateOver">商品掛け率</th>
				<th class="calcHd">入力した定価で<br />金額算出</th>
				<th class="reflectHd">直近の原価を<br />反映</th>
				<th class="profit">利益判定</th>
				<th class="check">確認</th>
				<th class="saveHd">編集</th>
			</tr>


			<nested:iterate property="corpSalesCostList" indexId="listIdx">

<!-- 		マスタにない商品 -->
			<nested:notEqual property="sysItemId" value="0">
				<bean:define id="backgroundColor" value="" />
			</nested:notEqual>
			<nested:equal property="sysItemId" value="0">
				<bean:define id="backgroundColor" value="#FFFFC0" />
			</nested:equal>
			<input type="hidden" name="corporateSalesIndex" id="corporateSalesIndex" />

				<tbody style="background:${backgroundColor};" class="corporateSalesSlipRow change_color">
				<nested:hidden property="sysCorporateSalesSlipId" styleClass="sysCorporateSalesSlipId"></nested:hidden>
				<nested:hidden property="sysCorporateSalesItemId" styleClass="sysCorporateSalesItemId"></nested:hidden>
				<nested:hidden property="storeFlag"	styleClass="storeFlag" />
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
					<td class="orderNumEdit"><nested:write property="orderNum" /></td>
					<td class="pieceRateEdit"><nested:write property="pieceRate" format="###,###,###" />&nbsp;円</td>
					<td class="corporationRateOverEdit"><nested:write property="corporationRateOver" />&nbsp;％</td>
					<td class="costEdit"><nested:write property="cost" format="###,###,###" />&nbsp;円</td>
					<td class="domePostageEdit"><nested:write property="postage" format="###,###,###" />&nbsp;円</td>
					<td class="kindCostEdit"><nested:write property="kindCost" format="###,###,###" />&nbsp;円</td>
					<td class="listPriceEdit"><nested:write property="listPrice" format="###,###,###" />&nbsp;円</td>
					<td class="domePostageKindEdit"><nested:write property="postage" format="###,###,###" />&nbsp;円</td>
					<td class="itemRateOverEdit"><nested:write property="itemRateOver" />&nbsp;％</td>
					<td class="tdButton"><button type="button"
						class="button_small_main calcSaleCost" disabled>算出</button></td>
					<td class="tdButton"><button type="button"
						class="button_small_main reflectLatestSaleCostCost" disabled>反映</button></td>
					<td class="profitId"><nested:write property="profit" format="###,###,###"/>&nbsp;円</td>
					<td class="costCheck"><nested:checkbox property="costCheckFlag" disabled="true" /></td>
					<td class="tdButton"><button type="button"
						class="button_small_main saleCostEdit" >編集</button></td>
				</tr>
				</tbody>
			</nested:iterate>

		</table>
	</div>
<!-- ページ(下側) -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysCorprateSaleItemIDListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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

	</html:form>
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