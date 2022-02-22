<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<title>売上一覧</title>
	<link rel="stylesheet" href="./css/saleList.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
<!-- 	<script type="text/javascript" src="./js/prototype.js"></script> -->
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【売上一覧画面】
ファイル名：saleList.jsp
作成日：2014/12/22
作成者：八鍬寛之

（画面概要）

助ネコ・新規売上登録で生成された売上データの検索/一覧画面。

・検索条件：伝票情報か商品情報で絞り込みが可能。検索結果一覧の表示件数も変更可能。
・検索結果：売上データ毎の表示。中央右に売上額/原価/粗利の集計。

・検索ボタン押下：設定された絞り込み項目をもとに検索処理を実行する。
・リセットボタン押下：設定された絞り込み項目を全て空にする。
・「編集を有効にする」ボタン押下：検索結果左の「出力済」「出庫済」チェックボックスと納品書備考の変更を反映させる。
・「ピッキングリスト・納品書出力」ボタン押下：検索結果の中から「出力済」にチェックが入っていないものを対象に
　　ピッキングリストと納品書のPDFを別タブで出力する(ページ単位)。売上データ毎にピッキングリスト→納品書の順に出す。
・「トータルピッキングリスト出力」ボタン押下：検索結果の商品データをリスト化したPDFを別タブで出力する(ページ単位)。
・「出庫処理」ボタン押下：「出庫済」にチェックが入っていない商品データを引継ぎ(ページ単位)、出庫処理画面に遷移する。
・「検索結果をダウンロード」ボタン押下：検索結果の売上データと商品データ全てをエクセルにダウンロードする。
・「新規登録」ボタン押下：売上の新規登録画面に遷移する。
・行をダブルクリックまたは受注Noリンクをクリック：対象データの売上詳細画面に遷移する。

（注意・補足）

-->


	<script type="text/javascript">

	$(document).ready(function(){
		$(".overlay").css("display", "none");
    	window.onpageshow = function(event) {
    		if (sessionStorage.getItem('toSaleDetail') == 'true') {
              	var url = window.location.href;
            	if (url.indexOf("searchSaleList") >= 0) {
                	location.reload();
            		$(".overlay").css("display", "block");
        			sessionStorage.setItem('toSaleDetail', 'false');
            	}
    		}
        };
	 });
	
	document.addEventListener("visibilitychange", function() {
	    if (document.hidden){
	    } else {
          	var url = window.location.href;
        	if (url.indexOf("searchSaleList") >= 0) {
            	location.reload();
        		$(".overlay").css("display", "block");
        	}
	    }
	});

	// prototype.jsのAjax通信機能を使ってアクセスする処理
// 	function goExportPickList(){
// 		var ajaxOptions = {
// 		asynchronous: true,
// 		method: "post",
// 		evalScripts: true,
// 		onComplete: CallBackJsonExportPickList,
// 		parameters: ""
// 		}
// 		$(".overlay").css("display", "block");
// 		new Ajax.Request("exportPickList.do", ajaxOptions);
// 	}

	function goExportPickList(){

		if($(".sysSalesSlipId").size() == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		if (!confirm("表示されているページでピッキングリストと納品書を出力します。\nよろしいですか？")) {
			return;
		}

		$(".overlay").css("display", "block");
		$.ajax({
			url: "./exportPickList.do",
			type: 'POST',
			dataType : 'json',

			success: function(data, text_status, xhr){

				var msg = "ピッキングリスト・納品書　内訳\n";
				msg += "KTS倉庫出庫予定伝票 ：" + data[0] + "\n";
				msg += "楽天倉庫出庫予定伝票：" + data[1] + "\n";

				//data = {KTS伝票数,RSL伝票数}
				if (data[0] >= 1) {
					$("#pickoutputFlg").val('1');
					$(".overlay").css("display", "none");
					msg += "※楽天倉庫から出庫予定の伝票はピッキングリスト・納品書に出力されません。";
					alert(msg);
					goTransaction("saleListPageNo.do");
				} else if (data[0] <= 0 && data[1] >= 1) {
					$("#pickoutputFlg").val('0');
					$(".overlay").css("display", "none");
					msg += "※楽天倉庫から出庫予定の伝票はピッキングリスト・納品書を出力しません。";
					alert(msg);
					goTransaction("saleListPageNo.do");
				} else {
			 		$(".overlay").css("display", "none");
			 		msg += "※伝票に想定外のデータが存在した為、pdfファイルの作成に失敗しました。";
			 		alert(msg);
				}
			},
		 	error: function(data, text_status, xhr){
		 		$(".overlay").css("display", "none");
				$("#pickoutputFlg").val('0');
				msg = "システムエラーが発生しました。\n";
				msg += "pdfファイルの作成に失敗しました。";
		 		alert(msg);
		 	}
		  });
	}

	document.addEventListener("readystatechange", function(e){
 	    if (document.readyState == 'complete' && $("#pickoutputFlg").val() == '1') {
 	    	$("#pickoutputFlg").val('0');
 	    	$(".overlay").css("display", "none");
 	    	window.open('pickListPrintOutFile.do','_new');
		}
	}, false);

	function goExportTotalPickList(){

		if($(".sysSalesSlipId").size() == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		if (!confirm("表示されているページでトータルピッキングリストを出力します。\nよろしいですか？")) {
			return;
		}
		$(".overlay").css("display", "block");
		$.ajax({
			url: "./exportTotalPickList.do",
			type: 'POST',

			success: function(data, text_status, xhr){
				$(".overlay").css("display", "none");
				window.open('totalPickListPrintOutFile.do','_new');
			},
		 	error: function(data, text_status, xhr){
		 		$(".overlay").css("display", "none");
		 		alert("pdfファイルの作成に失敗しました。");
		 	}
		  });
	}

	function goInitLeaveStock(){

		if($(".sysSalesSlipId").size() == 0) {
			alert("対象の売上データがありません。");
			return;
		}

		goTransaction("initLeaveStock.do");
	}

// 	function goExportTotalPickList(){
// 		var ajaxOptions = {
// 		asynchronous: true,
// 		method: "post",
// 		evalScripts: true,
// 		onComplete: CallBackJsonExportTotalPickList,
// 		parameters: ""
// 		}
// 		$(".overlay").css("display", "block");
// 		new Ajax.Request("exportTotalPickList.do", ajaxOptions);
// 		}

// 		// Ajax通信のコールバック関数
// 		function CallBackJsonExportTotalPickList(request){
// 			if(request == null){
// 				return;
// 			}
// 			$(".overlay").css("display", "none");
// 			if (request.status ==202) {

// 				return;

// 			}else if (request.status !=200) {

// 				alert("pdfファイルの作成に失敗しました。");
// 				return;
// 			} else {
// 				window.open('totalPickListPrintOutFile.do','_self');

// 			}
// 		}

	$(function() {

	     if($('.slipNoExist').prop('checked') || $('.slipNoHyphen').prop('checked')) {
	    	 $('.slipNoNone').attr('disabled','disabled');
	     }

		 if($('.slipNoNone').prop('checked')) {
		    	$('.slipNoExist').attr('disabled','disabled');
		    	$('.slipNoHyphen').attr('disabled','disabled');
		    }

// 		//ページ
// 		if ($("#sysSalesSlipIdListSize").val() != 0) {
// 			var slipPageNum = Math.ceil($("#sysSalesSlipIdListSize").val() / $("#saleListPageMax").val());

// 			$(".slipPageNum").text(slipPageNum);
// 			$(".slipNowPage").text(Number($("#pageIdx").val()) + 1);

// // 			page(1, slipPageNum, $(".pageNum"), true);
// 			var i;


// 			if (0 == $("#pageIdx").val()) {
// 				$(".pager > li:eq(1)").find("a").attr("class", "pageNum nowPage");
// 			}
// 			for (i = 1; i < slipPageNum; i++) {
// // 				var clone = $(".pageNum").clone();
// // 				clone.text(i + 1);
// // 				$(".pager > a:last-child").before(clone);

// // 				alert($("#pageIdx").val() + 1);
// 				var clone = $(".pager > li:eq(1)").clone();
// 				clone.children(".pageNum").text(i + 1);

// 				if (i == $("#pageIdx").val()) {

// 					clone.find("a").attr("class", "pageNum nowPage");

// 				} else {
// 					clone.find("a").attr("class", "pageNum");
// 				}

// 				$(".pager > li:last-child").before(clone);
// 			}
// 		}

//******************************************************************************************************
		if ($("#sysSalesSlipIdListSize").val() != 0) {
			var slipPageNum = Math.ceil($("#sysSalesSlipIdListSize").val() / $("#saleListPageMax").val());

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

		 $('.slipNoCheckBox').click(function(){
		    if($(this).hasClass("slipNoExist") || $(this).hasClass("slipNoHyphen")){
			    $('.slipNoNone').attr('disabled','disabled');
		    }else{
		    	$('.slipNoExist').attr('disabled','disabled');
		    	$('.slipNoHyphen').attr('disabled','disabled');
		    }

		    if(!$('.slipNoCheckBox:checked').val()){
		    	$('.slipNoCheckBox').removeAttr('disabled');
		    }
		  });

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

	    $(".clear").click(function(){
	        $("#searchOptionArea input, #searchOptionArea select").each(function(){
	            if (this.type == "checkbox" || this.type == "radio") {
	                this.checked = false;
	            } else {
	                $(this).val("");
	            }
	        });
	    });

		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1
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

		$('.td_editRemarks').dblclick(function() {
			var txt = $(this).text();
			$(this).html('<textarea rows="4" col="50" style="width: 150px;">'+ txt +'</textarea>');
			$('textarea').focus();
		});

		//売上詳細
		$(".salesSlipRow").dblclick(function () {

			$("#sysSalesSlipId").val($(this).find(".sysSalesSlipId").val());
			sessionStorage.setItem('toSaleDetail', 'true');
			goTransaction("detailSale.do");
		});

		$(".salesSlipLink").click(function () {

			$("#sysSalesSlipId").val($(this).find(".sysSalesSlipId").val());
			sessionStorage.setItem('toSaleDetail', 'true');
			goTransaction("detailSale.do");
		});

		// 売上データインポートボタン
		$(".saleSlipImport").click (function (){

			goTransaction("initSaleSlipExcelImport.do");

			return;
		});

// 		$(".pageNum").click (function () {

//	 		$(".pageNum").parents(".pager");
// 			alert($(this).text());
// 			$("#pageIdx").val($(this).text() - 1);
// 			goTransaction("saleListPageNo.do");
// 			$(this).text();

// 		});

// 		//次ページへ
// 		$("#nextPage").click (function () {
// 			if (Number($("#pageIdx").val()) + 1 >= $(".slipPageNum").text()) {
// 				alert("最終ページです");
// 				return;
// 			}
// 			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
// 			goTransaction("saleListPageNo.do");
// 		});

// 		//前ページへ
// 		$("#backPage").click (function () {

// 			if ($("#pageIdx").val() == 0) {
// 				alert("先頭ページです");
// 				return;
// 			}
// 			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
// 			goTransaction("saleListPageNo.do");
// 		});
//******************************************************************************************************

		$(".pageNum").click (function () {

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val($(this).text() - 1);
			goTransaction("saleListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
			goTransaction("saleListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("saleListPageNo.do");
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

			goTransaction("saleListPageNo.do");
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

			goTransaction("saleListPageNo.do");
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

			goTransaction("saleListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("saleListPageNo.do");
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
			goTransaction("saleListPageNo.do");
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
			goTransaction("saleListPageNo.do");
		});

//******************************************************************************************************
		$(".search").click (function () {

			//全件表示または出庫済みにチェックが入っているとき下記項目のいずれかを必須項目にする
			//注文日From、注文者TEL、受注番号、品番From-To、品番前方一致、他社品番、届け先TEL
			if($("#searchAllChecker").prop("checked") || $("#leavingFlgChecker").prop("checked")) {
				var searchCheckFlg = false;

				$(".searchInputCheck").each(function() {
					if ($(this).val() != "") {
						searchCheckFlg = true;
					}
				})

				if (searchCheckFlg == false) {
					alert("「注文日From、注文者TEL、受注番号、品番From-To、品番前方一致、他社品番、出荷予定日From、届け先TEL」\r\n上記検索項目のいずれかを入力してください");
					return;
				}
			}

			if ($("#orderDateFrom").val() && $("#orderDateTo").val()){
				fromAry = $("#orderDateFrom").val().split("/");
				toAry = $("#orderDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("注文日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#sumClaimPriceFrom").val() && $("#sumClaimPriceTo").val()) {
				var fromPrice = parseInt($("#sumClaimPriceFrom").val());
				var toPrice = parseInt($("#sumClaimPriceTo").val());
				if (fromPrice > toPrice) {
					alert("請求額 の検索開始金額が、検索終了金額より大きい額になっています。");
					return false;
				}
			}

			if ($("#destinationAppointDateFrom").val() && $("#destinationAppointDateTo").val()){
				fromAry = $("#destinationAppointDateFrom").val().split("/");
				toAry = $("#destinationAppointDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("配送指定日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#shipmentPlanDateFrom").val() && $("#shipmentPlanDateTo").val()){
				fromAry = $("#shipmentPlanDateFrom").val().split("/");
				toAry = $("#shipmentPlanDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("出荷予定日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#itemCodeFrom").val() && $("#itemCodeTo").val()) {
				if ($("#itemCodeFrom").val() > $("#itemCodeTo").val()) {
					alert("品番 の出力開始番号が、出力終了番号より大きい値になっています。");
					return false;
				}
			}

			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			removeCommaList($(".priceTextMinus"));
			removeCommaGoTransaction('searchSaleList.do');
// 			goTransaction('searchSaleList.do');
		});

		$(".deliveryRemarksAble").click (function () {


		});

		$(".lumpUpdate").click (function (){

			if (confirm("変更を反映させます。よろしいですか？")) {
			goTransaction("lumpUpdateSales.do");
			}
			return;
		});


		$(".saleListDownLoad").click (function () {

			removeCommaGoTransaction('saleListDownLoad.do');
		});

// 		$('.allEditRemarks').click(function() {
// 			var txt = $('textarea').text();
// 			$('textarea').html('<textarea rows="4" col="50" style="width: 150px;">'+ txt +'</textarea>');
// 			$('textarea').focus();
// 		});

		//出庫チェックがつけられた際、出荷日を設定
		$(document).on("change", ".leaveCheckBox", function () {
			if ($(this).prop("checked")) {
				var date = new Date();
				var yyyy = date.getFullYear();
				var MM = ("0" + (date.getMonth() + 1)).slice(-2);
				var dd = ("0" + (date.getDate())).slice(-2);
				$(this).parents("tr").find(".shipmentDate").val(yyyy + "/" + MM + "/" + dd);
			} else {
				$(this).parents("tr").find(".shipmentDate").val("");
			}
			$('.leave').text("空");
		});

	});

	var count;
	function boxChecked(check) {
	  	for(count= 0; count < document.saleForm.pickingFinBox.length; count++) {
	  		document.saleForm.pickingFinBox[count].checked = check;
	  	}
	  }

// 	function editDeliveryRemarks() {
// 		var text = document.getElementById('editRemarks').value;


// 	}

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initSaleList" enctype="multipart/form-data">
	<nested:hidden property="pickoutputFlg" styleId="pickoutputFlg"></nested:hidden>
	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="heading">売上一覧</h4>

	<fieldset id="searchOptionField">
	<legend id="searchOptionOpen">▲隠す</legend>
	<div id="searchOptionArea">
	<nested:nest property="saleSearchDTO" >
		<table id="checkBoxTable" style="border-collapse: collapse;">
			<tr>
				<td colspan="2" class="flgCheck"><label><nested:checkbox property="pickingListFlg"/>ピッキングリスト出力済</label></td>
				<td class="arrow">→</td>
				<td class="flgCheck"><label><nested:checkbox property="leavingFlg" styleId="leavingFlgChecker"/>出庫済</label></td>
				<td class="pdg_left_30px"><label><nested:checkbox property="returnFlg"/>返品伝票</label></td>
			</tr>
			<tr>
				<td><label><nested:checkbox property="searchAllFlg" styleId="searchAllChecker" />全件表示</label></td>
				<td class="td_right slipNoLabel">伝票番号</td>
				<td class="slipNoCheckBoxTd"><label><nested:checkbox property="slipNoExist" styleClass="slipNoCheckBox slipNoExist" />有</label></td>
				<td class="slipNoCheckBoxTd"><label><nested:checkbox property="slipNoHyphen" styleClass="slipNoCheckBox slipNoHyphen" />ハイフン</label></td>
				<td class="slipNoNoneCheckBoxTd"><label><nested:checkbox property="slipNoNone" styleClass="slipNoCheckBox slipNoNone" />無</label></td>
			</tr>
		</table>

		<table id="rootTable">
			<tr>
				<td>法人</td>
				<td><nested:select property="sysCorporationId">
						<html:option value="0">　</html:option>
						<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
					</nested:select>
				</td>
				<td class="td_center">処理ルート</td>
				<td colspan="3"><nested:select property="disposalRoute">
						<html:optionsCollection property="disposalRouteMap" label="value" value="key"/>
					</nested:select>
				</td>
			</tr>
			<tr>
				<td>販売チャネル</td>
				<td><nested:select property="sysChannelId" >
						<html:option value="0">　</html:option>
						<html:optionsCollection property="channelList" label="channelNm" value="sysChannelId" />
					</nested:select>
				</td>
			</tr>
		</table>

		<table id="orderTable">
			<tr>
				<td>注文日</td>
				<td><nested:text property="orderDateFrom" styleId="orderDateFrom" styleClass="calender searchInputCheck" maxlength="10" /> ～ <nested:text property="orderDateTo" styleId="orderDateTo" styleClass="calender" maxlength="10" /></td>
			</tr>
			<tr>
				<td>受注番号</td>
				<td><nested:text property="orderNo" styleClass="text_w250 searchInputCheck" maxlength="30" styleId="orderNo" /></td>
			</tr>
			<tr>
				<td>注文者名</td>
				<td><nested:text property="orderNm" styleClass="text_w150" maxlength="30" /></td>
			</tr>
			<tr>
				<td>注文者TEL</td>
				<td><nested:text property="orderTel" styleClass="text_w150 searchInputCheck" maxlength="13" styleId="orderTel"/></td>
			</tr>
			<tr>
				<td>注文者MAIL</td>
				<td><nested:text property="orderMailAddress" styleClass="text_w250" maxlength="256" /></td>
			</tr>
			<tr>
				<td>決済方法</td>
				<td><nested:select property="accountMethod">
					<html:optionsCollection property="accountMethodMap" label="value" value="key" />
				</nested:select></td>
			</tr>
			<tr>
				<td>請求額</td>
				<td><nested:text property="sumClaimPriceFrom" styleId="sumClaimPriceFrom" styleClass="text_w100 priceTextMinusSearch" maxlength="9" />&nbsp;円&nbsp;～&nbsp;<nested:text property="sumClaimPriceTo" styleId="sumClaimPriceTo" styleClass="text_w100 priceTextMinusSearch" maxlength="9" />&nbsp;円</td>
			</tr>
			<tr>
				<td>一言メモ</td>
				<td><nested:text property="memo" styleClass="text_w250" /><br/><span class="explain">※BO情報やYahooID等</span></td>
			</tr>
		</table>

		<div id="centerArea">

		<table id="deliveryTable">
			<tr>
				<td>運送会社</td>
				<td><nested:select property="transportCorporationSystem">
							<html:optionsCollection property="transportCorporationSystemMap" label="value" value="key"/>
					</nested:select>
				</td>
			</tr>
			<tr>
				<td>送り状番号</td>
				<td><nested:text property="slipNo" styleClass="text_w200" maxlength="30" /></td>
			</tr>
			<tr>
				<td>送り状種別</td>
				<td>
				<nested:select property="invoiceClassification">
				<html:optionsCollection property="invoiceClassificationMap" label="key" value="key"/>
				</nested:select></td>
			</tr>
			<tr>
				<td>配送指定日</td>
				<td><nested:text property="destinationAppointDateFrom" styleId="destinationAppointDateFrom" styleClass="calender" /> ～ <nested:text property="destinationAppointDateTo" styleId="destinationAppointDateTo" styleClass="calender" maxlength="10" />
			</tr>
			<tr>
				<td>出荷予定日</td>
				<td><nested:text property="shipmentPlanDateFrom" styleId="shipmentPlanDateFrom" styleClass="calender searchInputCheck" maxlength="10" /> ～ <nested:text property="shipmentPlanDateTo" styleId="shipmentPlanDateTo" styleClass="calender" maxlength="10" /></td>
			</tr>
		</table>

		<table id="destinationTable">
			<tr>
				<td>届け先名</td>
				<td><nested:text property="destinationNm" styleClass="text_w150" maxlength="30" /></td>
			</tr>
			<tr>
				<td>届け先TEL</td>
				<td><nested:text property="destinationTel" styleClass="text_w150 searchInputCheck" maxlength="13" styleId="destinationTel"/></td>
			</tr>
		</table>

		</div>

		<table id="itemTable">
			<tr>
				<td>品番（前方一致）</td>
				<td><nested:text property="itemCode" styleClass="text_w120 numText searchInputCheck" maxlength="11" styleId="itemCode"/></td>
			</tr>
			<tr>
				<td>品番</td>
				<td><nested:text property="itemCodeFrom" styleId="itemCodeFrom" styleClass="text_w120 numText searchInputCheck" maxlength="11" /> ～ <nested:text property="itemCodeTo" styleId="itemCodeTo" styleClass="text_w120 numText searchInputCheck" maxlength="11" /></td>
			</tr>
			<tr>
				<td>他社品番（前方一致）</td>
				<td><nested:text property="salesItemCode" styleClass="text_w120 searchInputCheck" maxlength="30" styleId="salesItemCode"/></td>
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

		<table id="buttonTable">
			<tr>
				<td>並び順</td>
				<td>
					<nested:select property="sortFirst">
						<html:optionsCollection property="saleSearchMap" value="key" label="value" />
					</nested:select>
					<nested:select property="sortFirstSub">
						<html:optionsCollection property="saleSearchSortOrder" value="key" label="value" />
					</nested:select>
				</td>
				<!-- Backlog No. BONCRE-488 対応 (クライアント側) -->
				<td>合計値エリア</td>
				<td>
					<nested:select property="sumDispFlg">
						<html:optionsCollection property="sumDispMap" label="value" value="key" />
					</nested:select>
				</td>
			</tr>
			<tr>
				<td>表示件数</td>
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
				<td colspan="2" style="padding: 10px 0 0 60px;"><a class="button_main search" href="javascript:void(0);" >検索</a></td>
				<td colspan="2" style="padding-top: 14px;"><a class="button_white clear" href="javascript:void(0);">リセット</a></td>
			</tr>
		</table>
	</nested:nest>
	</div>
	</fieldset>
	<nested:nest property="errorDTO">
	<nested:notEmpty property="errorMessage">
		<div id="errorArea">
			<p class="errorMessage"><nested:write property="errorMessage"/></p>
		</div>
	</nested:notEmpty>
	</nested:nest>

	<nested:notEmpty property="salesSlipList">
	<div class="middleArea">
		<table class="editButtonTable">
			<tr>
				<td><a class="button_main lumpUpdate" href="Javascript:void(0);">編集を有効にする</a></td>
<!-- 				<td><a class="button_white deliveryRemarksAble" href="Javascript:void(0);">納品書備考一括編集</a></td> -->
			</tr>
		</table>
	</div>


<!-- 	<div class="paging_area"> -->
<!-- 	ページ -->
<!-- 		<div class="paging_total_top"> -->
<%-- 			<h3>全<nested:write property="sysSalesSlipIdListSize" />件（<span class="slipNowPage" ></span>/<span class="slipPageNum"></span>ページ）</h3> --%>
<!-- 		</div> -->
<!-- 		<div class="paging_num_top"> -->
<!-- 			<ul class="pager fr mb10"> -->
<!-- 			    <li class="backButton"><a href="javascript:void(0);" id="backPage" >&laquo;</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" class="pageNum" >1</a></li> -->
<!-- 			    <li class="nextButton"><a href="javascript:void(0);" id="nextPage" >&raquo;</a></li> -->
<!-- 			</ul> -->
<!-- 		</div> -->

<!-- ページ(上側)　20140407 安藤 -->

	<div class="paging_area">
		<div class="paging_total_top">
			<h3>全&nbsp;<nested:write property="sysSalesSlipIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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


<!-- ページ(上側)ここまで -->
		<nested:notEmpty property="saleListTotalDTO">
		<table class="summary">
			<tr>
				<th>合計請求金額</th>
				<th>税込商品単価</th>
				<th>税抜商品単価</th>
				<th>原価</th>
				<th>粗利</th>
			</tr>
			<tr>
				<nested:nest property="saleListTotalDTO">
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
	<table class="checkButtonArea">
		<tr>
			<td><a class="button_small_main pick" href="Javascript:void(0);">✔</a></td>
			<td><a class="button_small_main leave" href="Javascript:void(0);">✔</a></td>
		</tr>
	</table>
	<div id="list_area" >

	<input type="hidden" name="sysSalesSlipId" id="sysSalesSlipId" />
	<nested:hidden property="sysSalesSlipIdListSize" styleId="sysSalesSlipIdListSize" />
	<nested:hidden property="pageIdx" styleId="pageIdx" />
	<nested:hidden property="saleListPageMax" styleId="saleListPageMax" />
		<table class="list_table">
			<tr>
				<th class="check">出力済</th>
				<th class="check">出庫済</th>
				<th class="status">ステータス</th>
				<th class="orderRoot">受注ルート</th>
				<th class="orderDate">注文日</th>
				<th class="orderInf">注文情報</th>
				<th class="demandInf">請求金額</th>
				<th class="destinationInf">届け先情報</th>
				<th class="deliveryInf">配送情報</th>
				<th class="deliveryAppoint">配送指定日</th>
				<th class="shipmentPlan">出荷予定日</th>
				<th class="memo">一言メモ(届け先)</th>
				<th class="memo">備考/一言メモ(注文)</th>
				<th class="deliveryRemarks">納品書備考</th>
			</tr>

			<nested:iterate property="salesSlipList" indexId="idx">

			<nested:equal property="transportCorporationSystem" value="ヤマト運輸">
				<bean:define id="fontColor" value="green" />
			</nested:equal>
			<nested:equal property="transportCorporationSystem" value="佐川急便">
				<bean:define id="fontColor" value="blue" />
			</nested:equal>
			<nested:equal property="transportCorporationSystem" value="日本郵便">
				<bean:define id="fontColor" value="red" />
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

			<tbody style="background:${backgroundColor};" class="salesSlipRow change_color">
			<nested:hidden property="sysSalesSlipId" styleClass="sysSalesSlipId" />
<%-- 			<nested:hidden property="sysArrivalScheduleId" styleClass="sysArrivalScheduleId" /> --%>
			<tr>
				<td class="pickTd" rowspan="3"><nested:checkbox property="pickingListFlg" styleClass="pickCheckBox"/></td>
				<td class="leaveTd" rowspan="3"><nested:checkbox property="leavingFlg" styleClass="leaveCheckBox"/></td>
				<nested:hidden property="shipmentDate" styleClass="shipmentDate" />
				<td><nested:write property="status" /></td>
				<td><nested:write property="corporationNm" /></td>
				<td><nested:write property="orderDate" /></td>
				<td rowspan="3"><nested:write property="orderFullNm" /><br/>(<nested:write property="orderFullNmKana" />)<br/><nested:write property="orderTel" /></td>
				<td rowspan="2" class="td_center"><nested:write property="sumClaimPrice" format="###,###,###" /></td>
				<td rowspan="3"><nested:write property="destinationFullNm" /><br/>(<nested:write property="destinationFullNmKana" />)
						<br/><nested:write property="destinationPrefectures" /><nested:write property="destinationMunicipality" /><br/><nested:write property="destinationTel" /></td>
				<td style=" color:${fontColor};"><nested:write property="transportCorporationSystem" /></td>
				<td><nested:write property="destinationAppointDate" /></td>
				<td rowspan="3"><nested:write property="shipmentPlanDate" /></td>
				<td rowspan="3"><span class="memoColor" ><nested:write property="senderMemo" /></span></td>
				<td rowspan="3"><div title="<nested:write property="orderRemarksMemo" />" class="remarksHeight"><nested:write property="orderRemarksMemo" /></div></td>
				<td rowspan="3" class="td_editRemarks"><span class="editRemarks"><nested:textarea rows="3" property="deliveryRemarks" /></span></td>
			</tr>
			<tr>
				<td><nested:write property="disposalRoute" /></td>
				<td><nested:write property="channelNm" /></td>
				<td><nested:write property="orderTime" /></td>
				<td><nested:write property="invoiceClassification" /></td>
				<td><nested:write property="destinationAppointTime" /></td>
			</tr>
			<tr>
				<td colspan="3" class="td_center">
					<a href="Javascript:(void);" class="salesSlipLink">
						<nested:hidden property="sysSalesSlipId" styleClass="sysSalesSlipId"></nested:hidden>
						<nested:write property="orderNo" />
					</a>
				</td>
				<td><nested:write property="accountMethod" /></td>
				<td colspan="2" class="td_center">伝票番号:<nested:write property="slipNo" /></td>
			</tr>
			<tr>
				<td colspan="15" class="boundary"></td>
			</tr>
			</tbody>
			</nested:iterate>
			</table>
		</div>

<!-- ページ(下側) 20140407 安藤 -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysSalesSlipIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="slipNowPage" ></span>&nbsp;/&nbsp;<span class="slipPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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

	<footer class="footer buttonArea">
		<ul style="position: relative;">
			<li class="footer_button">
				<a class="button_main" href="Javascript:void(0);" onclick="goExportPickList();">ピッキングリスト・納品書出力</a>
			</li>
			<li class="footer_button">
				<a class="button_main" href="Javascript:void(0);" onclick="goExportTotalPickList();">トータルピッキングリスト出力</a>
			</li>
			<li class="footer_button">
				<a class="button_main" href="Javascript:void(0);" onclick="goInitLeaveStock();">出庫処理</a>
			</li>
			<li class="footer_button">
				<a class="button_white saleListDownLoad" href="Javascript:void(0);">検索結果をダウンロード</a>
			</li>
			<li class="footer_button">
				<a class="button_main saleSlipImport" href="Javascript:void(0);">売上データインポート</a>
			</li>
			<li class="footer_button">
				<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistrySales.do')">新規登録</a>
			</li>

		</ul>
	</footer>

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
