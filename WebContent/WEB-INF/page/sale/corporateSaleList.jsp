<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<title>業販一覧</title>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/corporateSaleList.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
<!-- 	<script type="text/javascript" src="./js/prototype.js"></script> -->
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>
	<script src="./js/shortcut.js"></script>

<!--
【業販売上一覧画面】
ファイル名：corporateSaleList.jsp
作成日：2015/08/08
作成者：髙桑

（画面概要）


業販売上データの検索/一覧画面。
・行ダブルクリック：詳細画面に遷移する。
・商品毎のピッキングリスト出力済みフラグは、画面から操作、更新が可能。
・「ピッキングリスト・納品書出力」ボタン押下：画面に表示されているデータを対象にピッキングリストと納品書のPDFを別タブで出力する(ページ単位)。
	売上データ毎にピッキングリスト→納品書の順に出す。ピッキングリスト出力済フラグが立っている商品は出力されない。
・「トータルピッキングリスト出力」ボタン押下：検索結果の商品データをリスト化したPDFを別タブで出力する(ページ単位)。
・「出庫処理」ボタン押下：出庫処理画面に遷移する。
・「一括入金ボタン」ボタン押下：画面に表示されているデータを対象に、入金処理を行う。
・「B2」ボタン押下：画面に表示されている、配送先が「ヤマト運輸」の売り上げの伝票用csvをダウンロードする。
・「e飛伝」ボタン押下：画面に表示されている、配送先が「佐川急便」の売り上げの伝票用csvをダウンロードする。
・「日本郵政」ボタン押下：画面に表示されている、配送先が「日本郵便」の売り上げの伝票用Excelファイルをダウンロードする。
・「見積書」ボタン押下：画面に表示されている売り上げの見積書を一括でPDFに出力する（別タブ）
・「注文請書」ボタン押下：画面に表示されている売り上げの注文請書を一括でPDFに出力する（別タブ）
・「検索結果をダウンロード」ボタン押下：検索結果の売上データと商品データ全てをエクセルにダウンロードする。



（注意・補足）

-->
	<script>
	$(document).ready(function(){
		$(".overlay").css("display", "none");
    	window.onpageshow = function(event) {
    		if (sessionStorage.getItem('toCorporateSaleDetail') == 'true') {
              	var url = window.location.href;
            	if (url.indexOf("searchCorporateSaleList") >= 0 || url.indexOf("corporateSaleListPageNo") >= 0) {
                	location.reload();
            		$(".overlay").css("display", "block");
        			sessionStorage.setItem('toCorporateSaleDetail', 'false');
            	}
    		}
        };
	});
	
	document.addEventListener("visibilitychange", function() {
	    if (document.hidden){
	    } else {
          	var url = window.location.href;
        	if (url.indexOf("searchCorporateSaleList") >= 0 || url.indexOf("corporateSaleListPageNo") >= 0) {
            	location.reload();
        		$(".overlay").css("display", "block");
        	}
	    }
	});
	$(function() {
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

		$(".check").click(function(){
			var check_all = true;
			if($(".check_all").prop("checked") && !$(this).prop("checked")) {
				$(".check_all").prop("checked", false);
			} else if ($(this).prop("checked")) {
				$(".check").each(function(index,checkbox){
					if (!$(checkbox).prop("checked")) {
						check_all = false;
						return false;
					}
				});
				$(".check_all").prop("checked", check_all);
			}
		});

		$(".check_all").click(function(){
			if($(".check_all").prop("checked")) {
				$(".check").prop("checked", true);
			} else {
				$(".check").prop("checked", false);
			}
		});

		//法人メニュー開閉
		$(".corptgl").click(function(){
			$(this).parent(".corp").find(".corpmenu").toggle();
		});

		//新規伝票
		$(".registryButton").click(function(){
			$("#sysCorporationId").val($(this).parents(".corp").find(".sysCorporationId").val());
			goTransaction("initRegistryCorporateSales.do");
		});

		//テンプレート検索用
		//当日出庫検索
		$(".currentLeaving").click(function(){
			$("#sysCorporationId").val($(this).parents(".corp").find(".sysCorporationId").val());
			$("#searchPreset").val(3);
			goTransaction("searchCorporateSaleList.do");
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
		if ($("#sysCorporateSalesSlipIdListSize").val() != 0) {
			var slipPageNum = Math.ceil($("#sysCorporateSalesSlipIdListSize").val() / $("#corporateSaleListPageMax").val());

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
		$(".pageNum").click (function () {

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val($(this).text() - 1);
			goTransaction("corporateSaleListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
			goTransaction("corporateSaleListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("corporateSaleListPageNo.do");
		});

//ページ送り（上側）
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

			goTransaction("corporateSaleListPageNo.do");
		});

		//最終ページへ
		$("#lastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);

			goTransaction("corporateSaleListPageNo.do");
		});

// ページ送り（下側）
		//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);

			goTransaction("corporateSaleListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("corporateSaleListPageNo.do");
		});

		//20140403 安藤　ここから
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
			goTransaction("corporateSaleListPageNo.do");
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);
			goTransaction("corporateSaleListPageNo.do");
		});

//******************************************************************************************************


		$(".search").click (function () {

			//売上にチェックが入っているときのみ検索条件の下記いずれかを入力必須項目にする
			//出庫予定日From-To、品番From-To、品番前方一致、他社品番、納入先電話番号、伝票番号、
			if($("#slipStatusSales").prop("checked")) {
				var searchCheckFlg = false;

				$(".searchInputCheck").each(function() {
					if ($(this).val() != "") {
						searchCheckFlg = true;
					}
				})

				if (searchCheckFlg == false) {
					alert("「出庫予定日From、品番、他社品番、納入先電話番号、伝票番号」\r\n上記検索項目のいずれかを入力してください");
					return;
				}
			}

			$("#searchPreset").val(0);
			if ( ! /^[0-9]+$/.test($("#sumClaimPrice").val()) ) {
				$("#sumClaimPrice").val("");
			}
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction('searchCorporateSaleList.do');
// 			goTransaction('searchSaleList.do');
		});

		//売上詳細
		$(".corporateSalesSlipRow").dblclick(function () {

			$("#sysCorporateSalesSlipId").val($(this).find(".sysCorporateSalesSlipId").val());
			sessionStorage.setItem('toCorporateSaleDetail', 'true');
			goTransaction("detailCorporateSale.do");
		});

		//売上詳細
		$(".itemListRow").click( function () {
			$("#sysCorporateSalesSlipId").val($(this).parent().parent().parent().find(".sysCorporateSalesSlipId").val());
			sessionStorage.setItem('toCorporateSaleDetail', 'true');
			goTransaction("detailCorporateSale.do");
		});

		 $('.pick').click(function(){
		    if($(this).text() == "✔"){
				$('.pickCheckBox').prop({'checked':'checked'});
				$('.pick').text("空");
			}else{
				$('.pickCheckBox').prop({'checked':false});
				$('.pick').text("✔");
			}
		});

		$(document).on("change", ".pickCheckBox", function () {
			$('.pick').text("空");
		});

		$('.leave').click(function(){
				if($(this).text() == "✔"){
					$('.leaveCheckBox').prop({'checked':'checked'});
					$('.leave').text("空");
					$(".leaveCheckBox").each(function () {
						var date = new Date();
						var yyyy = date.getFullYear();
						var MM = ("0" + (date.getMonth() + 1)).slice(-2);
						var dd = ("0" + (date.getDate())).slice(-2);
						$(this).parents("tr").find(".shipmentDate").val(yyyy + "/" + MM + "/" + dd);
					});
				}else{
					$('.leaveCheckBox').prop({'checked':false});
					$('.leave').text("✔");
					$(".leaveCheckBox").each(function () {
						$(this).parents("tr").find(".shipmentDate").val("");
					});
				}
		});

		$(".lumpUpdate").click (function (){

			if (confirm("変更を反映させます。よろしいですか？")) {
				pickingListFlgChange = 0;
				goTransaction("lumpUpdateCorporateSales.do");
			}
			return;
		});
		//CSVダウンロード
		//e飛伝
		$("#ehidenCsvDownLoad").click (function () {

			if ($(".corporateSalesSlipRow").length == 0) {
				alert("データがありません。");
				return;
			}

			var count=0;
			$(".transport").each(function(){
				count += ($(this).text() == "佐川急便") ? 1 : 0;
			});
			if (count==0){
				alert("該当するデータがありません。");
				return;
			}

			removeCommaGoTransaction('ehidenCsvDownLoad.do');
		});

		//B2
		$("#b2CsvDownLoad").click (function () {

			if ($(".corporateSalesSlipRow").length == 0) {
				alert("データがありません。");
				return;
			}

			var count=0;
			$(".transport").each(function(){
				count += ($(this).text() == "ヤマト運輸") ? 1 : 0;
			});
			if (count==0){
				alert("該当するデータがありません。");
				return;
			}

			removeCommaGoTransaction('b2CsvDownLoad.do');
		});

		//郵便
		$("#yubinExcelDownLoad").click (function () {

			if ($(".corporateSalesSlipRow").length == 0) {
				alert("データがありません。");
				return;
			}

			var count=0;
			$(".transport").each(function(){
				count += ($(this).text() == "日本郵便") ? 1 : 0;
			});
			if (count==0){
				alert("該当するデータがありません。");
				return;
			}

			removeCommaGoTransaction('yubinExcelDownLoad.do');
		});

		//西濃
		$("#seinoCsvDownLoad").click (function () {

			if ($(".corporateSalesSlipRow").length == 0) {
				alert("データがありません。");
				return;
			}

			var count=0;
			$(".transport").each(function(){
				count += ($(this).text() == "西濃運輸") ? 1 : 0;
			});
			if (count==0){
				alert("該当するデータがありません。");
				return;
			}

			removeCommaGoTransaction('seinoCsvDownLoad.do');
		});

		$("#lumpUpdateCorporateReceivePrice").click (function (){

			if (confirm("入金額を一括設定します。よろしいですか？")) {
			goTransaction("lumpUpdateCorporateReceivePrice.do");
			}
			return;
		});

		$(".saleListDownLoad").click (function () {

			removeCommaGoTransaction('corporateSaleListDownLoad.do');
		});


		$(".pickingListFlg").change(function () {
			pickingListFlgChange = 1;
		});

	});
	function goDetailClient(value){

		document.getElementById('sysClientId').value = value;
//		goTransaction('detailUser.do');
	}

	function goDetailCorporateSale(value){

		$("#sysCorporateSalesSlipId").val(value);
		sessionStorage.setItem('toCorporateSaleDetail', 'true');
		goTransaction('detailCorporateSale.do');
	}

	function goInitCorporateLeaveStock(){

		if($(".sysCorporateSalesSlipId").size() == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		goTransaction("initCorporateLeaveStock.do");
	}

	function goExportPickList(){

		if($(".sysCorporateSalesSlipId").size() == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		if (pickingListFlgChange != 0) {
			alert("「編集を有効にするボタン」ボタンから変更を反映、または再検索して下さい。");
			return;
		}

		var strDateFrom = $('#scheduledLeavingDateFrom').val();
		var dateFrom = null;
		if (strDateFrom) {
			var arrStrDate = strDateFrom.split("/");
			dateFrom = new Date(arrStrDate[0], parseInt(arrStrDate[1], 10)-1, arrStrDate[2]);
		}


		var strDateTo = $('#scheduledLeavingDateTo').val();
		var dateTo = null;
		if (strDateTo) {
			var arrStrDate = strDateTo.split("/");
			dateTo = new Date(arrStrDate[0], parseInt(arrStrDate[1], 10)-1, arrStrDate[2]);
		}
		var pickCount=0;
		var allCount = 0;
		$(".corporateSalesSlipRow").each(function(){
			for(var i = 0; i < $(this).find(".pickingListFlg").size(); i++){

				allCount++;
				//■以下の判定は３箇所同ソースがあります。
				//　・ここのソースを変更する場合はサーバーサイドのバリデートも変更してください 20151026 aito
				//■対象となる判定
				//　01:ピッキング済み
				//　02:日付の期間判定

				//01:ピッキング済みのものは対象外
				if ($(this).find(".pickingListFlg").eq(i).prop("checked")) {
					continue;
				}

				var strDate = $(this).find(".scheduledLeavingDate").eq(i).text();
				var date = null;
				if (strDate) {
					var arrStrDate = strDate.split("/");
					date = new Date(arrStrDate[0], parseInt(arrStrDate[1], 10)-1, arrStrDate[2]);
				}

				//02:From未満の場合対象外
				if (dateFrom
						&&date < dateFrom) {
					continue;
				}
				//02:Toを超過している場合対象外
				if (dateTo
						&&date > dateTo) {
					continue;
				}
				//dateFrom ~ dateToの範囲場合のみ出力対象に設定します。
				pickCount++;

			}
		});
		if (pickCount == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		var msg = "以下の条件でピッキングリストと納品書を出力します。よろしいですか？";
		msg += "\n・表示されているページ";
		var term = "\n・出庫予定日：";

		if (strDateFrom != ""
				&& strDateTo != "") {

			term += strDateFrom + "~" + strDateTo;

		} else if (strDateFrom == ""
				&& strDateTo != "") {

			term += "~" + strDateTo;

		} else if (strDateFrom != ""
			&& strDateTo == "") {

			term += strDateFrom + "~";

		} else {

			term += "指定なし(指定したい場合は、\n　　出庫予定日で絞り込みを行ってください。)";

		}

		msg += term;
		msg += "\n\n"
			+"表示商品数　　　　　　　:　" + allCount;
		msg += "\n"
			+"出力対象商品数　　　　:　" + pickCount;
		if (!confirm(msg)) {
			return;
		}

		// 納品書発行・再発行の種別（0：ピッキングリスト・納品書発行/1：納品書再発行）
		var pdfPattern = "0";

		$(".overlay").css("display", "block");
		$.ajax({
			url: "./exportCorporatePickList.do",
			type: 'POST',
			data : {'pdfPattern' : pdfPattern},

			success: function(data, text_status, xhr){
				$("#pickoutputFlg").val('1');
				$(".overlay").css("display", "none");
				goTransaction("corporateSaleListPageNo.do");
			},
		 	error: function(data, text_status, xhr){
		 		$(".overlay").css("display", "none");
		 		alert("pdfファイルの作成に失敗しました。");
		 	}
		  });
	}

	document.addEventListener("readystatechange", function(e){

 	    if (document.readyState == 'complete' && $("#pickoutputFlg").val() == '1') {
 	    	$("#pickoutputFlg").val('0');
 	    	$(".overlay").css("display", "none");
 	    	window.open('corporatePickListPrintOutFile.do','_new');
 	    }

	}, false);

	function goExportTotalPickList(){

		if($(".sysCorporateSalesSlipId").size() == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		if (pickingListFlgChange != 0) {
			alert("「編集を有効にするボタン」ボタンから変更を反映、または再検索して下さい。");
			return;
		}

// 		var date = new Date();
// 		var yyyy = date.getFullYear();
// 		var MM = ("0" + (date.getMonth() + 1)).slice(-2);
// 		var dd = ("0" + (date.getDate())).slice(-2);
// 		var dateStr = yyyy + "/" + MM + "/" + dd;
// 		var pickCount=0;
// 		$(".corporateSalesSlipRow").each(function(){
// 			for(var i = 0; i < $(this).find(".pickingListFlg").size(); i++){
// 				if (!$(this).find(".pickingListFlg").eq(i).prop("checked")) {
// 					if($(this).find(".scheduledLeavingDate").eq(i).text() == dateStr){
// 						pickCount++;
// 						break;
// 					}
// 				}
// 			}
// 		});
// 		if (pickCount == 0) {
// 			alert("対象の売上データがありません。");
// 			return;
// 		}

		if (!confirm("表示されているページでトータルピッキングリストを出力します。\nよろしいですか？")) {
			return;
		}
		$(".overlay").css("display", "block");
		$.ajax({
			url: "./exportCorporateTotalPickList.do",
			type: 'POST',

			success: function(data, text_status, xhr){
				$(".overlay").css("display", "none");
				window.open('corporateTotalPickListPrintOutFile.do','_new');
			},
			error: function(data, text_status, xhr){
				$(".overlay").css("display", "none");
				alert("pdfファイルの作成に失敗しました。");
			}
		});
	}

	function goExportEstimateList(){

		if($(".sysCorporateSalesSlipId").size() == 0) {
			alert("対象のデータがありません。");
			return;
		}

// 		if (!confirm("表示されているページで見積書を出力します。\nよろしいですか？")) {
// 			return;
// 		}
		if (!confirm("見積書を出力します。\nよろしいですか？")) {
			return;
		}
		$(".overlay").css("display", "block");
		$.ajax({
			url: "./exportCorporateEstimateList.do",
			type: 'POST',

			success: function(data, text_status, xhr){
				$(".overlay").css("display", "none");
				window.open('corporateEstimatePrintOutFile.do','_new');
			},
			error: function(data, text_status, xhr){
				$(".overlay").css("display", "none");
				if (data.statusText == 'OK') {
					window.open('corporateEstimatePrintOutFile.do','_new');
				}else {
					alert("pdfファイルの作成に失敗しました。");
				}
			}
		});
	}

	function goExportOrderAcceptanceList(){

		if($(".sysCorporateSalesSlipId").size() == 0) {
			alert("対象のデータがありません。");
			return;
		}

		if (!confirm("表示されているページで注文請書を出力します。\nよろしいですか？")) {
			return;
		}
		$(".overlay").css("display", "block");
		$.ajax({
			url: "./exportCorporateOrderAcceptanceList.do",
			type: 'POST',
/* 			dataType : 'json',
			data : {
				'tax' : "1000",
			},
 */
			success: function(data, text_status, xhr){
				$(".overlay").css("display", "none");
				window.open('corporateOrderAcceptancePrintOutFile.do','_new');
			},
			error: function(data, text_status, xhr){
				$(".overlay").css("display", "none");
				if (data.statusText == 'OK') {
					window.open('corporateOrderAcceptancePrintOutFile.do','_new');
				}else {
					alert("pdfファイルの作成に失敗しました。");
				}
			}
		});
	}

	//納品書再発行
	//一度出力したものだけを対象とする(ピッキング済みのもの)
	function goReExportPickList(){

		if($(".sysCorporateSalesSlipId").size() == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		if (pickingListFlgChange != 0) {
			alert("「編集を有効にするボタン」ボタンから変更を反映、または再検索して下さい。");
			return;
		}


		var strDateFrom = $('#scheduledLeavingDateFrom').val();
		var dateFrom = null;
		if (strDateFrom) {
			var arrStrDate = strDateFrom.split("/");
			dateFrom = new Date(arrStrDate[0], parseInt(arrStrDate[1], 10)-1, arrStrDate[2]);
		}


		var strDateTo = $('#scheduledLeavingDateTo').val();
		var dateTo = null;
		if (strDateTo) {
			var arrStrDate = strDateTo.split("/");
			dateTo = new Date(arrStrDate[0], parseInt(arrStrDate[1], 10)-1, arrStrDate[2]);
		}
		var pickCount=0;
		var allCount = 0;

		$(".corporateSalesSlipRow").each(function(){
			for(var i = 0; i < $(this).find(".pickingListFlg").size(); i++){

				allCount++;
				//■以下の判定は３箇所同ソースがあります。
				//　・ここのソースを変更する場合はサーバーサイドのバリデートも変更してください 20151026 aito
				//■対象となる判定
				//　01:ピッキング済み
				//　02:日付の期間判定

				//01:ピッキング済みのもの「以外」は対象外
				if (!$(this).find(".pickingListFlg").eq(i).prop("checked")) {
					continue;
				}

				var strDate = $(this).find(".scheduledLeavingDate").eq(i).text();
				var date = null;
				if (strDate) {
					var arrStrDate = strDate.split("/");
					date = new Date(arrStrDate[0], parseInt(arrStrDate[1], 10)-1, arrStrDate[2]);
				}

				//02:From未満の場合対象外
				if (dateFrom
						&&date < dateFrom) {
					continue;
				}
				//02:Toを超過している場合対象外
				if (dateTo
						&&date > dateTo) {
					continue;
				}
				//dateFrom ~ dateToの範囲場合のみ出力対象に設定します。
				pickCount++;

			}
		});
		if (pickCount == 0) {
			alert("再発行できる売上データがありません。");
			return;
		}

		var msg = "以下の条件で納品書を再発行します。よろしいですか？";
		msg += "\n・表示されているページ";
		var term = "\n・出庫予定日：";

		if (strDateFrom != ""
				&& strDateTo != "") {

			term += strDateFrom + "~" + strDateTo;

		} else if (strDateFrom == ""
				&& strDateTo != "") {

			term += "~" + strDateTo;

		} else if (strDateFrom != ""
			&& strDateTo == "") {

			term += strDateFrom + "~";

		} else {

			term += "指定なし(指定したい場合は、\n　　出庫予定日で絞り込みを行ってください。)";

		}

		msg += term;
		msg += "\n\n"
			+"表示商品数　　　　　　　:　" + allCount;
		msg += "\n"
			+"出力対象商品数　　　　:　" + pickCount;
		if (!confirm(msg)) {
			return;
		}

		// 納品書発行・再発行の種別（0：ピッキングリスト・納品書発行/1：納品書再発行）
		var pdfPattern = "1";

		$(".overlay").css("display", "block");
		$.ajax({
			url: "./exportCorporatePickList.do",
			type: 'POST',
			data:{'pdfPattern': pdfPattern},

			success: function(data, text_status, xhr){
				$("#pickoutputFlg").val('1');
				$(".overlay").css("display", "none");
				goTransaction("corporateSaleListPageNo.do");
			},
		 	error: function(data, text_status, xhr){
		 		$(".overlay").css("display", "none");
				if (data.statusText == 'OK') {
					goTransaction("corporateSaleListPageNo.do");
				}else {
					alert("pdfファイルの作成に失敗しました。");
				}
		 	}
		  });
	}


</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailCorporateSale">
	<nested:hidden property="pickoutputFlg" styleId="pickoutputFlg"></nested:hidden>
	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="heading">業務販売一覧</h4>
	<nested:hidden property="sysCorporateSalesSlipId" styleId="sysCorporateSalesSlipId"/>
	<nested:hidden property="sysCorporationId" styleId="sysCorporationId"/>

	<ul class="hmenu mb10">
		<nested:iterate property="corporationList" id="corporation" indexId="idx">
			<li class="corp"><nested:write property="abbreviation" />&nbsp;(${idx+1})&nbsp;<span class="corptgl" style="cursor: pointer;"><i class="fa fa-caret-down"></i></span>
				<input type="hidden" class="sysCorporationId" value="<nested:write property="sysCorporationId" />" />
				<ul class="corpmenu" style="display: none;" >
<!-- 					<li><a href="javascript:void(0);">見積</a></li> -->
<!-- 					<li><a href="javascript:void(0);">受注</a></li> -->
					<li><a href="javascript:void(0);" class="currentLeaving">本日出庫</a></li>
<!-- 					<li><a href="javascript:void(0);">売上</a></li> -->
					<li><a href="javascript:void(0);" class="registryButton"><i class="fa fa-plus-circle"></i>新規</a></li>
				</ul>
			</li>
		</nested:iterate>
		<li class="corp">ALL&nbsp;<span class="corptgl" style="cursor: pointer;"><i class="fa fa-caret-down"></i></span>
			<input type="hidden" class="sysCorporationId" value="0" />
			<ul class="corpmenu" style="display: none;" >
<!-- 				<li><a href="javascript:void(0);">見積</a></li> -->
<!-- 				<li><a href="javascript:void(0);">受注</a></li> -->
				<li><a href="javascript:void(0);" class="currentLeaving">本日出庫</a></li>
<!-- 				<li><a href="javascript:void(0);">売上</a></li> -->
			</ul>
		</li>
	</ul>
	<fieldset class="searchOptionField">
		<legend id="searchOptionOpen">▲隠す</legend>
		<nested:nest property="corporateSaleSearchDTO">
			<nested:hidden property="searchPreset" styleId="searchPreset" />
			<div id="searchOptionArea">
				<div class="h80">
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
					<div class="fl">
						<div class="mt10 pdg_left_30px">
							<span><label><nested:checkbox property="returnFlg" />返品伝票</label></span>
							<span class="flgCheck"><label><nested:checkbox property="invalidFlag" />無効伝票</label></span>
							<span><label><nested:checkbox property="searchAllFlg" />全件表示</label></span>
						</div>
						<div class="mt10 pdg_left_30px">
							<label><nested:radio property="searchPriority" value="1" styleClass="searchPriority" />伝票を検索する</label>
							<label><nested:radio property="searchPriority" value="2" styleClass="searchPriority" />商品を検索する</label>
						</div>
					</div>
				</div>
				<table class="fl">
					<tr>
						<td>見積日</td>
						<td style="white-space: nowrap;"><nested:text property="estimateDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="estimateDateTo" styleClass="calender"  maxlength="10" /></td>
					</tr>
					<tr>
						<td>受注日</td>
						<td style="white-space: nowrap;"><nested:text property="orderDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="orderDateTo" styleClass="calender"  maxlength="10" /></td>
					</tr>
					<tr>
						<td>
							出庫予定日
							<nested:define id="dateFrom" property="scheduledLeavingDateFrom" />
							<input type="hidden" value="${dateFrom}" id="scheduledLeavingDateFrom" />
							<nested:define id="dateTo" property="scheduledLeavingDateTo" />
							<input type="hidden" value="${dateTo}" id="scheduledLeavingDateTo" />
						</td>
						<td style="white-space: nowrap;"><nested:text property="scheduledLeavingDateFrom" styleClass="calender searchInputCheck" styleId="scheduledLeavingDateFromChecker"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="scheduledLeavingDateTo" styleClass="calender" styleId="scheduledLeavingDateToChecker" maxlength="10" /></td>
					</tr>
					<tr>
						<td>出庫日</td>
						<td style="white-space: nowrap;"><nested:text property="leavingDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="leavingDateTo" styleClass="calender"  maxlength="10" /></td>
					</tr>
					<tr>
						<td>売上日</td>
						<td style="white-space: nowrap;"><nested:text property="salesDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="salesDateTo" styleClass="calender"  maxlength="10" /></td>
					</tr>
					<tr>
						<td>請求日</td>
						<td style="white-space: nowrap;"><nested:text property="billingDateFrom" styleClass="calender"  maxlength="10" />&nbsp;～&nbsp;<nested:text property="billingDateTo" styleClass="calender"  maxlength="10" /></td>
					</tr>
				</table>

				<table class="fl">
					<tr>
						<td>ステータス</td>
						<td>
<%-- 							<span><label><nested:checkbox property="slipStatusEstimate" />見積</label></span> --%>
<%-- 							<span><label><nested:checkbox property="slipStatusOrder" />受注</label></span> --%>
<%-- 							<span><label><nested:checkbox property="slipStatusSales" />売上</label></span> --%>
								<label><nested:radio property="slipStatus" value="1" styleClass="slipStatus" />見積</label>
								<label><nested:radio property="slipStatus" value="2" styleClass="slipStatus" />受注</label>
								<label><nested:radio property="slipStatus" value="3" styleClass="slipStatus" styleId="slipStatusSales" />売上</label>
						</td>
					</tr>
					<tr>
						<td>伝票番号</td>
						<td><nested:text property="orderNo" styleId="orderNo" styleClass="searchInputCheck"/></td>
					</tr>
					<tr>
						<td>法人</td>
						<td>
							<nested:select property="sysCorporationId">
								<option value="0">　</option>
								<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
							</nested:select>
						</td>
					</tr>
					<tr>
						<td>得意先番号</td>
						<td><nested:text property="clientNo" /></td>
					</tr>
					<tr>
						<td>得意先名</td>
						<td><nested:text property="clientNm" /></td>
					</tr>

					<tr>
						<td>納入先電話番号</td>
						<td><nested:text property="deliveryTelNo" styleId="deliveryTelNo" styleClass="searchInputCheck"/></td>
					</tr>
					<tr>
						<td>納入先名</td>
						<td><nested:text property="deliveryNm" /></td>
					</tr>


					<tr>
						<td>担当者名</td>
						<td><nested:text property="personInCharge" /></td>
					</tr>
					<tr>
						<td>運送会社</td>
						<td><nested:select property="transportCorporationSystem">
								<html:optionsCollection property="transportCorporationSystemMap" label="value" value="key"/>
						</nested:select></td>
					</tr>
					<tr>
						<td>合計金額</td>
						<td>
							<nested:text property="sumClaimPrice" styleId="sumClaimPrice" />
							<nested:select property="sumClaimPriceOption">
								<html:option value="1">等しい</html:option>
								<html:option value="2">以上</html:option>
								<html:option value="3">以下</html:option>
							</nested:select>
						</td>
					</tr>
					<tr>
						<td>支払方法</td>
						<td><nested:select property="paymentMethod">
							<html:option value="0">　</html:option>
							<html:optionsCollection property="corporateSaleSearchDTO.paymentMethodMap" label="value" value="key" />
						</nested:select></td>
					</tr>
					<tr>
						<td>売掛残</td>
						<td><nested:select property="receivableBalance">
							<html:option value="0">　</html:option>
							<html:option value="1">有</html:option>
							<html:option value="2">無</html:option>
						</nested:select></td>
					</tr>
					<tr>
					</tr>
				</table>

				<table class="">
					<tr>
						<td>品番（前方一致）</td>
						<td><nested:text property="itemCode" styleClass="text_w120 numText searchInputCheck" maxlength="11" styleId="itemCode"/></td>
					</tr>
					<tr>
						<td>品番</td>
						<td style="white-space: nowrap;"><nested:text property="itemCodeFrom" styleId="itemCodeFrom" styleClass="text_w120 numText searchInputCheck" maxlength="11" /> ～ <nested:text property="itemCodeTo" styleId="itemCodeTo" styleClass="text_w120 numText searchInputCheck" maxlength="11" /></td>
					</tr>
					<tr>
						<td>他社品番（前方一致）</td>
						<td><nested:text property="salesItemCode" styleClass="text_w120 searchInputCheck" maxlength="30" styleId="salesItemCode" /></td>
					</tr>
					<tr>
						<td>商品名</td>
						<td><nested:text property="itemNm" styleClass="text_w200" /></td>
					</tr>
					<tr>
						<td>他社商品名</td>
						<td><nested:text property="salesItemNm" styleClass="text_w200" /></td>
					</tr>
				</table>

				<table id="search_option" class="mt30">
					<tr>
						<td nowrap>並び替え</td>
						<td style="white-space: nowrap;">
							<nested:select property="sortFirst">
								<html:option value="1">見積日</html:option>
								<html:option value="2">受注日</html:option>
								<html:option value="3">売上日</html:option>
								<html:option value="4">請求日</html:option>
							</nested:select>
							<nested:select property="sortFirstSub">
								<html:option value="1">降順</html:option>
								<html:option value="2">昇順</html:option>
							</nested:select>
						</td>
						<td nowrap>合計値エリア</td>
						<td>
							<nested:select property="sumDispFlg">
								<html:optionsCollection property="sumDispMap" label="value" value="key" />
							</nested:select>
						</td>
					</tr>
					<tr>
						<td nowrap>表示件数</td>
						<td>
							<nested:select property="listPageMax">
								<html:optionsCollection property="listPageMaxMap" value="key" label="value" />
							</nested:select>&nbsp;件
						</td>
						<td style="padding-left: 15px;">粗利</td>
						<td>
							<nested:select property="grossProfitCalc">
								<html:optionsCollection property="grossProfitCalcMap" value="key" label="value" />
							</nested:select>
						</td>
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
<nested:notEmpty property="corporateSalesSlipList">
	<div class="middleArea">
		<table class="editButtonTable">
			<tr>
				<td><a class="button_main lumpUpdate" href="Javascript:void(0);">編集を有効にする</a></td>
			</tr>
		</table>
	</div>
	<div class="paging_area">
		<div class="paging_total_top">
			<h3>全&nbsp;<nested:write property="sysCorporateSalesSlipIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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
		<nested:notEmpty property="corporateSaleListTotalDTO">
		<table class="summary">
			<tr>
				<th>合計請求金額</th>
				<th>税込商品単価</th>
				<th>税抜商品単価</th>
				<th>原価</th>
				<th>粗利</th>
			</tr>
			<tr>
				<nested:nest property="corporateSaleListTotalDTO">
					<td><nested:write property="sumSumClaimPrice" format="###,###,###" /></td>
					<td><nested:write property="inTaxSumPieceRate" format="###,###,###" /></td>
					<td><nested:write property="noTaxSumPieceRate" format="###,###,###" /></td>
					<td><nested:write property="sumCost" format="###,###,###" /></td>
					<td><nested:write property="sumGrossMargin" format="###,###,###" /></td>
				</nested:nest>
			</tr>
		</table>
		</nested:notEmpty>

	</div>

	<div id="list_area">
		<input type="hidden" name="sysClientId"  id="sysClientId" />
		<nested:hidden property="sysCorporateSalesSlipIdListSize" styleId="sysCorporateSalesSlipIdListSize" />
		<nested:hidden property="pageIdx" styleId="pageIdx" />
		<nested:hidden property="corporateSaleListPageMax" styleId="corporateSaleListPageMax" />
		<table id="mstTable" class="list_table">
			<tr>
<!-- 				<th rowspan="2"><input type="checkbox" class="check_all"/></th> -->
				<th rowspan="2">登録日/伝票No</th>
				<th>ステータス</th>
				<th colspan="2">得意先名</th>
				<th colspan="2">納入先名</th>
				<th rowspan="2">配送</th>
				<th rowspan="2" class="min_w40">納品<br />状況</th>
				<th rowspan="2">商品名</th>
				<th rowspan="2">注文数</th>
				<th rowspan="2">出庫予定日</th>
				<th rowspan="2">ピッキング</th>
				<th rowspan="2">出庫日</th>
				<th rowspan="2">合計金額</th>
				<th rowspan="2">支払方法</th>
				<th rowspan="2">入金額</th>
				<th rowspan="2">入金日</th>
				<th rowspan="2">売掛残</th>
				<th rowspan="2">備考/一言メモ</th>
			</tr>
			<tr>
				<th>最終更新日付</th>
				<th>取引先法人</th>
				<th>担当者</th>
				<th>納入先TEL</th>

			</tr>


			<nested:iterate property="corporateSalesSlipList" indexId="listIdx">

			<nested:equal property="transportCorporationSystem" value="ヤマト運輸">
				<bean:define id="fontColor" value="green" />
			</nested:equal>
			<nested:equal property="transportCorporationSystem" value="佐川急便">
				<bean:define id="fontColor" value="blue" />
			</nested:equal>
			<nested:equal property="transportCorporationSystem" value="日本郵便">
				<bean:define id="fontColor" value="red" />
			</nested:equal>

			<nested:equal property="slipStatusNm" value="見積">
				<bean:define id="backgroundColorStatus" value="dodgerblue" />
			</nested:equal>
			<nested:equal property="slipStatusNm" value="受注">
				<bean:define id="backgroundColorStatus" value="green" />
			</nested:equal>
			<nested:equal property="slipStatusNm" value="売上">
				<bean:define id="backgroundColorStatus" value="black" />
			</nested:equal>

<!-- 		マスタにない商品 -->
			<nested:notEqual property="notItemCount" value="0">
				<bean:define id="backgroundColor" value="#FFFFC0" />
			</nested:notEqual>
			<nested:equal property="notItemCount" value="0">
				<bean:define id="backgroundColor" value="" />
			</nested:equal>

<!-- 		返品伝票 -->
			<nested:equal property="returnFlg" value="1">
				<bean:define id="backgroundColor" value="#FFE0E0" />
			</nested:equal>

				<tbody style="background:${backgroundColor};" class="corporateSalesSlipRow change_color">
				<nested:hidden property="sysCorporateSalesSlipId" styleClass="sysCorporateSalesSlipId"/>
					<tr>
<!-- 						<td rowspan="2"><input type="checkbox" class="check"/></td> -->
						<td rowspan="2" class="td_center"><nested:write property="createDate" format="yyyy/MM/dd" /><br />
						<a href="javascript:void(0);" class="itemListRow"><nested:write property="orderNo" /></a></td>
						<td class="td_center">
							<span class="slipStatus" style="background-color:${backgroundColorStatus};"><nested:write property="slipStatusNm" /></span>
						</td>
						<td colspan="2" class="td_left" ><nested:write property="clientNm" /></td>
						<td colspan="2" class="td_left" ><nested:write property="destinationNm" /></td>
						<td rowspan="2" class="td_center transport"  style="color:${fontColor};"><nested:write property="transportCorporationSystem" /></td>
						<td rowspan="2" class="deliveryStatus td_center">
<!-- 							未納/完納 -->
							<nested:iterate property="corporateSalesItemList">
								<nested:write property="deliveryStatus" />
								<br />
							</nested:iterate>
						</td>
						<td rowspan="2" class="td_left" >
<!-- 							商品名BB-EE-AAA<br /> -->
							<nested:iterate property="corporateSalesItemList">
								<nested:write property="itemNm" />
								<br />
								<nested:hidden property="sysCorporateSalesItemId"/>
							</nested:iterate>
						</td>
						<td rowspan="2" class="td_center">
<!-- 							注文数 -->
							<nested:iterate property="corporateSalesItemList">
								<span class="orderNum"><nested:write property="orderNum" /></span>
								<br />
							</nested:iterate>
						</td>
						<td rowspan="2">
<!-- 							出庫予定日2015-05-31<br /> -->
							<nested:iterate property="corporateSalesItemList">
								<span class="scheduledLeavingDate"><nested:write property="scheduledLeavingDate" /></span>
								<br />
							</nested:iterate>
						</td>
						<td rowspan="2" class="td_center">
<!-- 							ピッキングフラグ<br /> -->
							<nested:iterate property="corporateSalesItemList" indexId="pickIdx">
								<nested:checkbox property="pickingListFlg" styleClass="pickingListFlg" />
								<input type="hidden" name="corporateSalesSlipList[${listIdx}].corporateSalesItemList[${pickIdx}].pickingListFlg" value="off"/>
								<br />
							</nested:iterate>
						</td>
						<td rowspan="2" class="td_center">
<!-- 							出庫日2015-05-14<br /> -->
							<nested:iterate property="corporateSalesItemList">
								<nested:write property="leavingDate" />
								<br />
							</nested:iterate>
						</td>
						<td rowspan="2" class="td_center">
							<logic:equal name="corporateSaleForm" property="corporateSaleSearchDTO.slipStatus" value="3">
								<nested:write property="sumSalesPrice" format="###,###,###" />
							</logic:equal>
							<logic:notEqual name="corporateSaleForm" property="corporateSaleSearchDTO.slipStatus" value="3">
								<nested:write property="sumClaimPrice" format="###,###,###" />
							</logic:notEqual>
						</td>
						<td rowspan="2" class="td_center"><nested:write property="paymentMethodNm" /></td>
						<td rowspan="2" class="nyukin td_center">
						<!--入金額-->
							<nested:write property="receivePrice" format="###,###,###" />
<!-- 					<a href="javascript:void(0);">入力</a> -->

						</td>
						<td rowspan="2" class="td_center"><nested:write property="receiveDate" /></td>
						<td rowspan="2"><!--売掛残--></td>
						<td rowspan="2"><nested:write property="orderRemarks" /></td>
					</tr>
					<tr>
						<td class="td_center"><nested:write property="updateDate" format="yyyy/MM/dd" /></td>
						<td style="text-align:left;" ><nested:write property="corporationNm" /></td>
						<td style="text-align:left;" ><nested:write property="personInCharge" /></td>
						<td class="td_center"><nested:write property="destinationTel" /></td>

					</tr>
					<tr>
						<td colspan="20" class="boundary"></td>
					</tr>
				</tbody>
			</nested:iterate>

		</table>
	</div>
<!-- ページ(下側) 20140407 安藤 -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysCorporateSalesSlipIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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
	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goExportPickList();" >ピッキングリスト・納品書</a>
		<a class="button_main" href="Javascript:void(0);" onclick="goReExportPickList();" >納品書再発行</a>
		<a class="button_main" href="Javascript:void(0);" onclick="goExportTotalPickList();" >トータルピッキングリスト</a>
		<a class="button_main" href="Javascript:void(0);" onclick="goInitCorporateLeaveStock();" >出庫</a>
		<a class="button_main" href="Javascript:void(0);" id="lumpUpdateCorporateReceivePrice">一括入金</a>
		<a class="button_main" href="Javascript:void(0);" id="b2CsvDownLoad" >B2</a>
		<a class="button_main" href="Javascript:void(0);" id="ehidenCsvDownLoad" >e飛伝</a>
		<a class="button_main" href="Javascript:void(0);" id="yubinExcelDownLoad" >日本郵便</a>
		<a class="button_main" href="Javascript:void(0);" id="seinoCsvDownLoad" >西濃運輸</a>
		<a class="button_main" href="javascript:void(0)" onclick="goExportEstimateList();" >見積書</a>
		<a class="button_main" href="javascript:void(0)" onclick="goExportOrderAcceptanceList();" >注文請書</a>
<!-- 		<a class="button_main" href="Javascript:void(0);" >請求書発行</a> -->
		<a class="button_white saleListDownLoad" href="Javascript:void(0);">検索結果をダウンロード</a>
	</div>

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