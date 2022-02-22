<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/kindCostDetail.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>

	<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【Kind原価詳細画面】
ファイル名：detailKindCost.jsp
作成日：2015/12/16
作成者：大山智史

（画面概要）

Kind原価の登録画面
詳細画面はKind原価一覧から遷移
※ 商品詳細画面（itemDetail.jsp）の流用
※「分類」の項目は現在使用していない

【基本情報エリア】
・総在庫数の隣のカッコは仮在庫数。キープをかける毎に総在庫数からマイナスされる。
・どの倉庫に何個在庫があるか登録でき、その合計が総在庫数となる。
・倉庫の優先度はこの画面以外で在庫の増減を行う場合、優先度の高い倉庫を対象とするため。
・Kind原価欄にて、Kind原価を登録する。

以下画面右半分の各タブの概要


【価格情報】
・法人マスタにある掛け率から原価/売価を算出。


【キープ】
・受注番号と個数を入れて登録すると個数分が総在庫数からマイナスされる。
・出庫時にここで登録した受注番号のものを出庫した場合は対象のキープのデータが消える。
※Kind原価詳細画面では表示しない

【入荷予定】
・入荷予定情報を複数登録できる。「曖昧予定日」には「10末」等のメモが入る。
・「入荷数分入荷」ボタン押下：入荷予定数の数が優先度1の倉庫にプラスされ、その行のデータが入荷履歴のタブに入る。
　　もし全ての入荷予定数が入荷されたらその行は消え、残数があると残る。(修正ボタンを押さないと反映されない)
※Kind原価詳細画面では表示しない

【入荷履歴】
・入荷予定タブで「入荷数分入荷」されたデータが入る。一括入荷画面でも同様。
※Kind原価詳細画面では表示しない

【BO】
・キープ機能が実装されてから使用されていない
※Kind原価詳細画面では表示しない

【説明書】
・JPEG/PDFファイルのアップロードが5つまで可能。
※Kind原価詳細画面では表示しない

（注意・補足）

-->


<script type="text/javascript">

	function PxTextareaAdjuster(textarea){
		textarea.style.overflow='hidden';
		textarea.style.height = 13 +'px';
		if( textarea.scrollHeight  > textarea.offsetHeight ){
			textarea.style.height = textarea.scrollHeight -2 +'px';
	    }
	}

$(function () {


	var count = $('.indexNum').length;


	$('.num').spinner( {
		max: 9999,
		min: 0,
		step: 1
	});

	$(".calender").datepicker();
	$(".calender").datepicker("option", "showOn", 'button');
	$(".calender").datepicker("option", "buttonImageOnly", true);
	$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

	$('#rightArea > ul > li').click(function () {
		$('#rightArea > div').hide().filter($(this).find('a').attr('href')).show();

		$('#rightArea > ul > li').removeClass('active');
		$(this).addClass('active');

		return false;
	}).filter(':eq(0)').click();

	//■onload時
	seltab('box', 'head', 4, 1);


	//追加時のタグ操作用変数
	var tr = "";
	var aTag = "";

	//倉庫在庫追加部分を隠す
	var addWarehouseTrs = $("tr.addWarehouseStockRow");
	addWarehouseTrs.hide();
	var addWarehouseIdx = 0;

	//まだ登録されていない倉庫がある場合倉庫在庫追加を表示
	if ($(".warehouseNum").length < $("#warehouseLength").val()) {

		//一行分
		tr = addWarehouseTrs.eq(addWarehouseIdx);

		// aタグの操作
		aTag = tr.find(".button_small_white");
		if (aTag) {
			aTag.attr("class", "button_small_main addWarehouse");
			aTag.text("追加");
		}
		tr.show();
		addWarehouseTrs = "";
		tr = "";
		aTag = "";

		appendPrioritySelect();
	}


	//入荷予定追加
	var addArrivalSceduleTrs = $("tr.addArrivalScheduleRow");
	addArrivalSceduleTrs.hide();
	var addArrivalScheduleIdx = 0;
	tr = addArrivalSceduleTrs.eq(addArrivalScheduleIdx);

	// aタグの操作
	aTag = tr.find(".button_small_white");
	if (aTag) {
		aTag.attr("class", "button_small_main addArrivalScheduleDate");
		aTag.text("追加");
	}
	tr.show();
	tr = "";
	aTag = "";

	//バックオーダー追加
	var addBackOrderRowTbody = $(".addBackOrderTbody");
	addBackOrderRowTbody.hide();

	var addBackOrderIdx = 0;
	tbody = addBackOrderRowTbody.eq(addBackOrderIdx);

	// aタグの操作
	aTag = tbody.find("a");
	if (aTag) {
		aTag.attr("class", "button_small_main addBackOrder");
		aTag.text("追加");
	}
	tbody.show();
	tbody = "";
	aTag = "";

	//キープ追加
	var addKeepRow = $(".addKeepRow");
	addKeepRow.hide();

	var addKeepIdx = 0;
	keepTr = addKeepRow.eq(addKeepIdx);

	// aタグの操作
	aTag = keepTr.find(".button_small_white");
	if (aTag) {
		aTag.attr("class", "button_small_main addKeep");
		aTag.text("追加");
	}
	keepTr.show();

	keepTr = "";
	aTag = "";

	//説明書
	var addItemManualRows = $(".addItemManualRow");

	var addItemManualIdx = new Number(0);
	tr = addItemManualRows.eq(addItemManualIdx);
	aTag = tr.find("a");
	tr.show();
	tr = "";
	aTag = "";

	//アラート
	if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
		actAlert(document.getElementById('alertType').value);
		if ($("#alertType").val() == 1 && window.opener) {
			window.close();
		}
		document.getElementById('alertType').value = '0';
	}



	//■バリデートfunction

	//日付系
	$(".dateText").blur(function () {


	});

	//■イベントハンドラ系function

	//倉庫在庫行追加
	//追加ボタンからみて一つ下の行に倉庫在庫入力項目を追加させる。（表示させる）
	//倉庫は優先順位があり、優先順位順に入力していく仕様なので画面上の他の二つ(入荷予定・バックオーダー)追加機能とは異なる。
	$(".addWarehouse").click(function () {

		if ($(".warehouseNum").length + addWarehouseIdx + 1 >= $("#warehouseLength").val()) {
			alert("倉庫はこれ以上存在しません。");
			return;
		}
		//一行分表示
		var trs = $("tr.addWarehouseStockRow");
		var tr = trs.eq(++addWarehouseIdx);
		tr.show();
		appendPrioritySelect();
	});

	//倉庫在庫行削除
	$(".removeWarehouse").click(function () {

		var trs = $("tr.addWarehouseStockRow");
		var i = 0;
		for (i = $("tr.addWarehouseStockRow").index($(this).parents("tr.addWarehouseStockRow")); i <= addWarehouseIdx; i++) {

			var copyFromTr = trs.eq(i + 1);
			var copyToTr = trs.eq(i);

			copyToTr.find(".priority").val(copyFromTr.find(".priority").val());
			copyToTr.find(".sysWarehouseId").val(copyFromTr.find(".sysWarehouseId").val());
			copyToTr.find(".stockNum").val(copyFromTr.find(".stockNum").val());
			copyToTr.find(".locationNo").val(copyFromTr.find(".locationNo").val());

			copyFromTr.find(".priority").val(0);
			copyFromTr.find(".sysWarehouseId").val(0);
			copyFromTr.find(".stockNum").val(0);
			copyFromTr.find(".locationNo").val("");
		}

		$("select.prioritySelect option:last-child").remove();
		var tr = trs.eq(addWarehouseIdx--);
		tr.hide();
	});

	//入荷予定行追加
	$(".addArrivalScheduleDate").click(function () {

		if (addArrivalScheduleIdx + 1 >= $("#addArrivalScheduleLength").val()) {
			alert("一括で登録できるのは10件までです。");
			return;
		}

		var trs = $("tr.addArrivalScheduleRow");

		var i = 0;
		for (i = addArrivalScheduleIdx; i >= 0; i--) {

			var copyFromTr = trs.eq(i);
			var copyToTr = trs.eq(i + 1);

			copyToTr.find(".arrivalScheduleDate").val(copyFromTr.find(".arrivalScheduleDate").val());
			copyToTr.find(".vagueArrivalSchedule").val(copyFromTr.find(".vagueArrivalSchedule").val());
			copyToTr.find(".arrivalNum").val(copyFromTr.find(".arrivalNum").val());
			copyFromTr.find(".arrivalScheduleDate").val("");
			copyFromTr.find(".vagueArrivalSchedule").val("");
			copyFromTr.find(".arrivalNum").val(0);
		}

		var tr = trs.eq(++addArrivalScheduleIdx);
		tr.show();
	});

	//入荷予定行削除
	$(".removeArrivalScheduleDate").click(function () {

 		//alert($("tr.addArrivalScheduleRow").index($(this).parents("tr.addArrivalScheduleRow")));

		var trs = $("tr.addArrivalScheduleRow");
		var i = 0;
		for (i = $("tr.addArrivalScheduleRow").index($(this).parents("tr.addArrivalScheduleRow")); i <= addArrivalScheduleIdx; i++) {

			var copyFromTr = trs.eq(i + 1);
			var copyToTr = trs.eq(i);
			copyToTr.find(".arrivalScheduleDate").val(copyFromTr.find(".arrivalScheduleDate").val());
			copyToTr.find(".arrivalNum").val(copyFromTr.find(".arrivalNum").val());

			copyFromTr.find(".arrivalScheduleDate").val("");
			copyFromTr.find(".arrivalNum").val(0);
		}

		var tr = trs.eq(addArrivalScheduleIdx--);
		tr.hide();
	});

	//入荷完了
	$(".sceduleDateCompleteButton").click(function () {

		if ($(this).parents("tr").find(".arrivalFlag").val() == "1") {

			alert("もう一度入荷したい場合は修正ボタンを押してください");
			return;
		}

		var targetObj = null;
// 		var targetWarehouseNm = null;
		$(".prioritySelect").each(function (){

			if ($(this).children(":selected").val() == "1" && targetObj != null) {
				alert("優先度１の倉庫が複数あります");
				return;
			}

			if ($(this).children(":selected").val() == "1") {

				targetObj = $(this).parents("tr").find(".num");

			}


		});

		if (!targetObj || targetObj == null) {
			alert("優先度１の倉庫がありません");
			return;
		}


		//入荷フラグを立てる
		$(this).parents("tr").find(".arrivalFlag").val("1");

		var arrivalNum = new Number($(this).parents("tr").find(".arrivalNum").val());
		var arrivalScheduleNum = new Number($(this).parents("tr").find(".arrivalScheduleNum").val());


		alert("第一倉庫の在庫数を" + arrivalNum + "個増やします。");
		//第一倉庫に入荷分追加
		targetObj.val(Number(targetObj.val()) + arrivalNum);

		//入荷数が減らされた場合
		if (arrivalNum < arrivalScheduleNum) {

			arrivalScheduleNum -= arrivalNum;
			$(this).parents("tr").find(".arrivalNum").val(arrivalScheduleNum);
			alert(arrivalNum + "個入荷したので、残り入荷数が" + arrivalScheduleNum + "になりました");
			return;
		}

		//入荷数に変更がない場合隠す。
		$(this).parents("tr").hide();
	});

	//入荷予定削除
	$(".sceduleDateDeleteButton").click(function () {

		if (!confirm("入荷予定を削除します、よろしいですか？？")) {
			return;
		}
		$(this).parents("tr").find(".deleteFlag").val("1");
		$(this).parents("tr").hide();
	});

	//バックオーダー追加
	$(".addBackOrder").click(function () {

		if (addBackOrderIdx + 1 >= $("#addBackOrderLength").val()) {
			alert("一括で登録できるのは5件までです。");
			return;
		}

		var tbodyList = $(".addBackOrderTbody");
		var i = 0;
		for (i = addBackOrderIdx; i >= 0; i--) {

			var copyFromTr = tbodyList.eq(i);
			var copyToTr = tbodyList.eq(i + 1);

			copyToTr.find(".remarks").val(copyFromTr.find(".remarks").val());
			copyToTr.find(".sysCorporationId").val(copyFromTr.find(".sysCorporationId").val());
			copyToTr.find(".sysChannelId").val(copyFromTr.find(".sysChannelId").val());

			copyFromTr.find(".remarks").val("");
			copyFromTr.find(".sysCorporationId").val(0);
			copyFromTr.find(".sysChannelId").val(0);
		}

		var tbody = tbodyList.eq(++addBackOrderIdx);
		tbody.show();
	});

	//バックオーダー削除
	$(".removeBackOrder").click(function () {

		var tbodyList = $(".addBackOrderTbody");
		var i = 0;
		for (i = $(".addBackOrderTbody").index($(this).parents(".addBackOrderTbody")); i <= addBackOrderIdx; i++) {

			var copyFromTr = tbodyList.eq(i + 1);
			var copyToTr = tbodyList.eq(i);

			copyToTr.find(".remarks").val(copyFromTr.find(".remarks").val());
			copyToTr.find(".sysCorporationId").val(copyFromTr.find(".sysCorporationId").val());
			copyToTr.find(".sysChannelId").val(copyFromTr.find(".sysChannelId").val());

			copyFromTr.find(".remarks").val("");
			copyFromTr.find(".sysCorporationId").val(0);
			copyFromTr.find(".sysChannelId").val(0);
		}

		var tbody = tbodyList.eq(addBackOrderIdx--);
		tbody.hide();
	});

	$(".completeBackOrder").click (function () {

		$(this).parents("tbody").find(".deleteFlag").val("1");
		$(this).parents("tbody").hide();

	});


	//キープ追加
	$(".addKeep").click(function () {

		if (addKeepIdx + 1 >= $("#addKeepLength").val()) {
			alert("一括で登録できるのは5件までです。");
			return;
		}

		var trList = $(".addKeepRow");
		var i = 0;
		for (i = addKeepIdx; i >= 0; i--) {

			var copyFromTr = trList.eq(i);
			var copyToTr = trList.eq(i + 1);

			copyToTr.find(".orderNo").val(copyFromTr.find(".orderNo").val());
			copyToTr.find(".keepNum").val(copyFromTr.find(".keepNum").val());
			copyToTr.find(".remarks").val(copyFromTr.find(".remarks").val());

			copyFromTr.find(".orderNo").val("");
			copyFromTr.find(".keepNum").val(0);
			copyFromTr.find(".remarks").val("");
		}

		var tr = trList.eq(++addKeepIdx);
		tr.show();
	});

	//キープ削除
	$(".removeKeep").click(function () {

		var trList = $(".addKeepRow");
		var i = 0;
		for (i = $(".addKeepRow").index($(this).parents(".addKeepRow")); i <= addKeepIdx; i++) {

			var copyFromTr = trList.eq(i + 1);
			var copyToTr = trList.eq(i);

			copyToTr.find(".orderNo").val(copyFromTr.find(".orderNo").val());
			copyToTr.find(".keepNum").val(copyFromTr.find(".keepNum").val());
			copyToTr.find(".remarks").val(copyFromTr.find(".remarks").val());

			copyFromTr.find(".orderNo").val("");
			copyFromTr.find(".keepNum").val(0);
			copyFromTr.find(".remarks").val("");
		}

		var tr = trList.eq(addKeepIdx--);
		tr.hide();
	});




	$(".priorityOptions").change(function () {

		priorityOptions();
	});

	$(".orderNumCheck").blur(function () {

		$(this).val($.trim($(this).val()));
	});

	//原価コピー
	$("#costCopy").click(function () {

		$(".costList").find(".priceText").val($("#cost0").val());
	});

	//売価コピー
	$("#priceCopy").click(function () {

		$(".priceList").find(".priceText").val($("#price0").val());
	});


	//登録
	$(".registry").click(function () {

		if($(".codeCheck").val() == "") {
			alert('品番を入力してください。');
			return;
		} else if($(".itemNmCheck").val() == "") {
			alert('商品名を入力してください。');
			return;
		}

		if ($(".codeCheck").val().replace(/\s+/g,'').length <= 10) {

			if ((!confirm("品番が10桁以下ですが、よろしいですか？"))) {
				return;
			}

		}

		//倉庫在庫追加登録判定
		if(!addWarehouseStockAlert($("tr.addWarehouseStockRow"))) {
			return;
		}

		//入荷予定追加登録判定
		if(!addArrivalScheduleAlert($("tr.addArrivalScheduleRow"))) {
			return;
		}

		//BO追加登録判定
		if(!addBackOrderAlert($(".addBackOrderTbody"))) {
			return;
		}

		if (!confirm("登録します。よろしいですか？")) {
			return;
		}

		removeCommaGoTransaction("registryKindCost.do");
	});

	//修正
	$(".update").click(function () {

		if($(".codeCheck").val() == "") {
			alert('品番を入力してください。');
			return;
		} else if($(".itemNmCheck").val() == "") {
			alert('商品名を入力してください。');
			return;
		}

		if ($(".codeCheck").val().replace(/\s+/g,'').length <= 10) {

			if ((!confirm("品番が10桁以下ですが、よろしいですか？"))) {
				return;
			}

		}

		//倉庫在庫追加登録判定
		if(!addWarehouseStockAlert($("tr.addWarehouseStockRow"))) {
			return;
		}

		//入荷予定追加登録判定
		if(!addArrivalScheduleAlert($("tr.addArrivalScheduleRow"))) {
			return;
		}

		//BO追加登録判定
		if(!addBackOrderAlert($(".addBackOrderTbody"))) {
			return;
		}

		if (!confirm("修正します。よろしいですか？")) {
			return;
		}

		removeCommaGoTransaction("registryKindCost.do");
	});

	//説明書追加
	$(".addItemManual").click (function () {

		if (addItemManualIdx + 1 >= $("#addItemManualLength").val()) {
			alert("一括で登録できるのは"+ $("#addItemManualLength").val() +"件までです。");
			return;
		}
		var trs = $("tr.addItemManualRow");
		tr = trs.eq(++addItemManualIdx);

		tr.show();

	});



	//説明書ダウンロード
	$(".downLoadItemManual").click (function () {


		$("#sysItemManualId").val($(this).parents(".itemManualRow").find(".sysItemManualId").val());

		goTransactionNew("itemManualDownLoad.do");

	});

	$(".deleteItemManual").click (function () {

		$(this).parents(".itemManualRow").find(".deleteFlag").val("1");
		$(this).parents("tr.itemManualRow").hide();
	});

	$(".deleteKeep").click (function () {

		$(this).parents(".keepRow").find(".deleteFlag").val("1");
		$(this).parents("tr.keepRow").hide();

	});

	//■ただのfunction
	function priorityOptions() {

		$(".prioritySelect").each(function (){

			$(".priorityOptions").remove($(".prioritySelect").val());
		});
	}

	function appendPrioritySelect () {

		var length = $(".warehouseNum").length + addWarehouseIdx + 1;
		$(".prioritySelect").append($('<option>').html(length).val(length));
	}
	function removePrioritySelect () {

		$(".prioritySelect").each(function () {

			$(".priorityOptions").remove().appendTo($(".prioritySelect").val());
		});
	}

	//20140821 k.saito 倉庫在庫追加登録判定
	function warehouseStockAlert (showTr) {
		var errorFlg = true;
		showTr.each(function(){
			var prioritySelect = $(this).find(".prioritySelect").val();
			var sysWarehouseId = $(this).find(".sysWarehouseId").val();
			var stockNum = new Number($(this).find(".stockNum").val());

			if (prioritySelect != ""|| sysWarehouseId != "") {

				if (prioritySelect == "" || sysWarehouseId == "") {

					alert('優先度・倉庫名は必須項目です。');
					errorFlg = false;
					return false;
				}
			} else if(stockNum > 0) {

				if (prioritySelect == "" || sysWarehouseId == "") {
					alert('優先度・倉庫名は必須項目です。');
					errorFlg = false;
					return false;
				}
			}
		});
		return errorFlg;
	}


	//20140821 k.saito 倉庫在庫追加登録判定
	function addWarehouseStockAlert (showTr) {

		for (var i = 0; i <= addWarehouseIdx; i++) {
			var prioritySelect = showTr.eq(i).find(".prioritySelect").val();
			var sysWarehouseId = showTr.eq(i).find(".sysWarehouseId").val();
			var stockNum = new Number(showTr.eq(i).find(".stockNum").val());

			if (prioritySelect != ""|| sysWarehouseId != "") {

				if (prioritySelect == "" || sysWarehouseId == "") {

					alert('優先度・倉庫名は必須項目です。');
					return false;
				}
			} else if(stockNum > 0) {

				if (prioritySelect == "" || sysWarehouseId == "") {

					alert('優先度・倉庫名は必須項目です。');
					return false;
				}
			}
		}
		return true;
	}

	function addArrivalScheduleAlert (showTr) {

		for (var i = 0; i <= addArrivalScheduleIdx; i++) {

			var itemOrderDate = showTr.eq(i).find(".itemOrderDate").val();
			var arrivalScheduleDate = showTr.eq(i).find(".arrivalScheduleDate").val();
			var arrivalNum = new Number(showTr.eq(i).find(".arrivalNum").val());

			if (arrivalScheduleDate != "" || arrivalNum != 0) {

				if (itemOrderDate == "" || arrivalScheduleDate == "" || arrivalNum == 0) {

					alert('発注日・入荷予定日・入荷数は必須項目です。');
					return false;
				}
			}
		}
		return true;
	}

	function addBackOrderAlert (showTbody) {

		for (var i = 0; i <= addBackOrderIdx; i++) {

			var addBackOrderDate = showTbody.eq(i).find(".addBackOrderDate").val();
			var sysCorporationId = new Number(showTbody.eq(i).find(".sysCorporationId").val());
			var sysChannelId = new Number(showTbody.eq(i).find(".sysChannelId").val());
			var remarks = showTbody.eq(i).find(".remarks").val();

			if (sysCorporationId != 0 || sysChannelId != 0 || remarks != "") {

				if (addBackOrderDate == "" || sysCorporationId == 0 || sysChannelId == 0) {

					alert('BO日・法人・販売チャネルは必須項目です。');
					return false;
				}
			}
		}
		return true;
	}

	//説明書日本語入力NGチェック(追加時)
	$(".manualFile").change (function () {
		var file = this.files[0];

		var isJapanese = false;  //日本語（英語以外）の場合「true」に設定
		for(var i=0; i < file.name.length; i++){
		    if(file.name.charCodeAt(i) >= 256) {
		      isJapanese = true;
		      break;
		    }
		}

		//日本語（英語以外）の場合
		if(isJapanese) {
			alert("ファイル名は、半角英数字のみ入力してください。");
			$(this).val("");
		}

	});


	//説明書日本語入力NGチェック(更新時)
	$(".hideManualFile").change (function () {
		var file = this.files[0];

		var isJapanese = false;  //日本語（英語以外）の場合「true」に設定
		for(var i=0; i < file.name.length; i++){
		    if(file.name.charCodeAt(i) >= 256) {
		      isJapanese = true;
		      break;
		    }
		}

		//日本語（英語以外）の場合
		if(isJapanese) {
			alert("ファイル名は、半角英数字のみ入力してください。");
			$(this).val("");
			var idname = $(this).attr("id");
			if (idname.length != 0) {
				var num = idname.slice(idname.length - 1);
				$("#manualFileNameDisp" + num).val("");
			}
		}

	});

});
</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="/detailKindCost" enctype="multipart/form-data">

	<div class="headingDetail">Kind原価詳細&nbsp;
		<nested:equal value="0" property="mstUserDTO.sysUserId">
			<span class="explainLabel">※編集後は、必ず「登録」ボタンを押してください。</span>
		</nested:equal>
		<nested:notEqual value="0" property="mstUserDTO.sysUserId">
			<span class="explainLabel">※編集後は、必ず「修正」ボタンを押してください。</span>
		</nested:notEqual>
	</div>

	<nested:hidden property="alertType" styleId="alertType"></nested:hidden>
<!-- 	画面上に追加できる行の上限 -->
	<nested:hidden property="warehouseLength" styleId="warehouseLength"/>
	<nested:hidden property="addArrivalScheduleLength" styleId="addArrivalScheduleLength"/>
	<nested:hidden property="addKeepLength" styleId="addKeepLength"/>
	<nested:hidden property="addBackOrderLength" styleId="addBackOrderLength"/>
	<nested:hidden property="addItemManualLength" styleId="addItemManualLength"/>
	<nested:hidden property="sysItemManualId" styleId="sysItemManualId"/>

	<html:hidden property="alertType" styleId="alertType"></html:hidden>

		<nested:notEmpty property="errorMessageDTO">
		<nested:nest property="errorMessageDTO">
			<p style="text-align: center; font-weight:bold; color:red;"><nested:write property="errorMessage"/></p>
		</nested:nest>
		</nested:notEmpty>

		<nested:nest property="mstItemDTO">
			<nested:hidden property="sysItemId" />
			<div id="leftArea">
				<div id="detailArea">
					<table class="list" style="width: 100%;">
						<tr>
							<th>品番</th>
							<td><nested:text property="itemCode" styleClass="w120 numText codeCheck" maxlength="11" disabled="true" /></td>
								<nested:hidden property="beforeItemCode" />
							<th>旧品番</th>
							<td><nested:text property="oldItemCode" styleClass="w100" maxlength="30" disabled="true" /></td>
						</tr>
						<tr>
							<th>商品名</th>
							<td colspan="3">
								<nested:textarea disabled="true" property="itemNm" style="width: 400px;" styleClass="itemNmCheck" cols="20" rows="1" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
										onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);">
								</nested:textarea>
							</td>

						</tr>
						<tr>
							<th>分類</th>
							<td colspan="3">
								<nested:hidden property="sysGroupNmId" styleId="sysGroupNmId" />
								<textarea disabled="true" cols="20" class="groupNm" style="width: 330px;" rows="1" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
										onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);">
								</textarea>
							</td>
						</tr>
						<tr>
							<th>総在庫数</th>
							<td class="w150"><span class="totalNumLabel"><nested:write property="totalStockNum" />&nbsp;(<nested:write property="temporaryStockNum" />)</span></td>
							<th>発注アラート数</th>
							<td><nested:text disabled="true" property="orderAlertNum" styleClass="num numText" maxlength="4"  /></td>
						</tr>

					</table>
					<table>
						<tr>
							<th>Kind原価</th>
							<td style="width: 150px;">
								<label class="necessary">※</label>&nbsp;
								<nested:text property="cost" styleId="kindCost" styleClass="priceText kindCostCheck" style="width: 100px; text-align: right;" maxlength="9" />
							</td>
						</tr>
					</table>
				</div>

				<table id="warehouseArea">
					<tr>
						<th style="min-width: 55px;">優先度</th>
						<th style="min-width: 100px;">倉庫名</th>
						<th style="min-width: 60px;">在庫数</th>
						<th colspan="2" style="min-width: 200px;max-width: 200px">ロケーションNo</th>
					</tr>

<!-- 					表示用 -->
					<logic:notEmpty name="btobBillForm" property="warehouseStockList">
					<logic:iterate id="warehouseStockList" name="btobBillForm" property="warehouseStockList" indexId="idx">
						<html:hidden property="warehouseStockList[${idx}]sysWarehouseStockId" />
							<tr class="warehouseStockRow">
								<td><html:select disabled="true" property="warehouseStockList[${idx}]priority" styleClass="prioritySelect warehouseNum">
									<html:option value="">　</html:option>
									<html:optionsCollection name="btobBillForm" property="warehouseStockList" label="priority" value="priority" styleClass="priorityOptions" />
								</html:select></td>
								<td><html:select disabled="true" property="warehouseStockList[${idx}]sysWarehouseId" styleClass="sysWarehouseId">
									<html:option value="">　</html:option>
									<html:optionsCollection property="warehouseList" label="warehouseNm" value="sysWarehouseId" styleClass="sysWarehouseId" />
								</html:select></td>
								<td><html:text disabled="true" property="warehouseStockList[${idx}]stockNum"  styleClass="num stockNum" maxlength="4" /></td>
								<td colspan="2"><html:text disabled="true" property="warehouseStockList[${idx}]locationNo"  styleClass="w100" maxlength="30" /></td>
							</tr>
						</tbody>
					</logic:iterate>
					</logic:notEmpty>
				</table>

				<table id="specMemoArea">
					<tr>
						<th>仕様メモ</th>
					</tr>
					<tr>
						<td class="tdPdg"><nested:textarea property="specMemo" rows="4" style="width: 500px;"></nested:textarea></td>
					</tr>
				</table>
					<table id="lastUpdateDetail">
						<tr>
							<th>最終更新情報</th>
						</tr>
						<tr>
							<td class="tdPdg">
								<bean:write name="btobBillForm" property="mstUserDTO.userFamilyNmKanji"/>
								<bean:write name="btobBillForm" property="mstUserDTO.userFirstNmKanji"/>
								<nested:write property="updateDate" format="yyyy/MM/dd HH:mm:ss"/>
							</td>
						</tr>
					</table>
			</div>
		</nested:nest>

		<div id="rightArea">
			<ul>
				<li><a href="#priceInf" ><span>価格情報</span></a></li>
			</ul>

			<div id="keep">
				<table id="keepTable">
					<tr>
						<th>受注番号</th>
						<th>個数</th>
						<th>備考</th>
						<td></td>
					</tr>
<!-- 					表示用 -->
					<nested:notEmpty property="keepList">
					<nested:iterate property="keepList" indexId="idx">
						<tr class="keepRow">
							<nested:hidden property="sysKeepId" />
<%-- 							<nested:hidden property="arrivalFlag" styleClass="arrivalFlag" /> --%>
							<nested:hidden property="deleteFlag" styleClass="deleteFlag"/>
<%-- 							<nested:hidden property="arrivalScheduleNum" styleClass="arrivalScheduleNum"/> --%>
<%-- 							<nested:hidden property="arrivalNum" styleClass="arrivalScheduleNum"/> --%>
							<td><nested:text styleClass="orderNo orderNumCheck w300" property="orderNo" maxlength="30"/></td>
							<td><nested:text styleClass="num keepNum numText" property="keepNum" maxlength="4"/></td>
							<td><nested:textarea property="remarks" styleClass="w250 remarks" rows="2" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
											onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);"></nested:textarea></td>
							<td><a class="button_small_white deleteKeep" href="Javascript:void(0);">削除</a></td>
						</tr>
					</nested:iterate>
					</nested:notEmpty>
<!-- 					追加用 -->
					<nested:notEmpty property="addKeepList">
					<nested:iterate property="addKeepList" indexId="idx">
						<tr class="addKeepRow">
							<td><nested:text styleClass="addOrderNum orderNumCheck w300" property="orderNo" maxlength="30"/></td>
							<td><nested:text styleClass="num addKeepNum numText" property="keepNum" maxlength="4"/></td>
							<td><nested:textarea property="remarks" styleClass="w250 addRemarks" rows="2" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
											onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);"></nested:textarea></td>
							<td><a class="button_small_white removeKeep" href="Javascript:void(0);">削除</a></td>
						</tr>
					</nested:iterate>
					</nested:notEmpty>

				</table>

			</div>

			<div id="schedule">
				<table id="scheduleDate" class="list">
					<tr>
						<th>発注日</th>
						<th>入荷予定日</th>
						<th>曖昧予定日</th>
						<th>入荷数</th>
						<td></td>
						<td></td>
					</tr>
<!-- 					表示用 -->
					<nested:notEmpty property="arrivalScheduleList">
					<nested:iterate property="arrivalScheduleList" indexId="idx">
						<tr class="arrivalScheduleRow">
							<nested:hidden property="sysArrivalScheduleId" />
							<nested:hidden property="arrivalFlag" styleClass="arrivalFlag" />
							<nested:hidden property="deleteFlag" styleClass="deleteFlag"/>
							<nested:hidden property="arrivalScheduleNum" styleClass="arrivalScheduleNum"/>
<%-- 							<nested:hidden property="arrivalNum" styleClass="arrivalScheduleNum"/> --%>
							<td><nested:text styleClass="dateText calender" property="itemOrderDate" maxlength="10"/></td>
							<td><nested:text styleClass="dateText calender" property="arrivalScheduleDate" maxlength="10"/></td>
							<td><nested:text styleClass="dateText" property="vagueArrivalSchedule" style="width: 200px" maxlength="30"/></td>
							<td><nested:text property="arrivalNum" styleClass="num numText arrivalNum" maxlength="4" /></td>
							<td><a class="button_small_white sceduleDateCompleteButton" href="Javascript:void(0);">入荷数分入荷</a></td>
							<td><a class="button_small_white sceduleDateDeleteButton" href="Javascript:void(0);">削除</a></td>
						</tr>
					</nested:iterate>
					</nested:notEmpty>
<!-- 					追加用 -->
					<nested:notEmpty property="addArrivalScheduleList">
					<nested:iterate property="addArrivalScheduleList" indexId="idx">
						<tr class="addArrivalScheduleRow">
							<td><nested:text styleClass="dateText itemOrderDate calender" property="itemOrderDate" maxlength="10"/></td>
							<td><nested:text styleClass="dateText arrivalScheduleDate calender" property="arrivalScheduleDate" maxlength="10"/></td>
							<td><nested:text styleClass="dateText vagueArrivalSchedule" property="vagueArrivalSchedule" style="width:200px" maxlength="30"/></td>
							<td><nested:text styleClass="num arrivalNum numText" property="arrivalNum" maxlength="4"/></td>
							<td colspan="2"><a class="button_small_white removeArrivalScheduleDate" href="Javascript:void(0);">削除</a></td>
						</tr>
					</nested:iterate>
					</nested:notEmpty>
				</table>
			</div>

			<div id="record">
				<table id="recordTable">
					<tr>
						<th class="w150">発注日</th>
						<th class="w150">入荷予定日</th>
						<th class="w100">入荷数</th>
					</tr>
					<nested:notEmpty property="arrivalHistoryList">
					<nested:iterate property="arrivalHistoryList" indexId="idx">
					<tr>
						<td class="w120"><nested:write  property="itemOrderDate"/></td>
						<td class="w120"><nested:write property="arrivalScheduleDate"/></td>
						<td class="w135"><nested:write property="arrivalNum"/></td>
					</tr>
					</nested:iterate>
					</nested:notEmpty>
				</table>
			</div>

			<div id="priceInf">
				<table class="list costList">
					<tr>
						<th>原価</th>
						<td></td>
					</tr>
					<nested:iterate property="itemCostList" indexId="idx">
					<nested:hidden property="sysItemCostId" />
					<tr>
						<nested:hidden property="sysCorporationId" />
						<td><nested:write property="corporationNm"/></td>
						<td><nested:text disabled="true" property="cost" styleId="cost${idx}" styleClass="priceText itemCost" style="width: 100px; text-align: right;" maxlength="9"/></td>
					</tr>
					</nested:iterate>
				</table>

				<table class="list priceList">
					<tr>
						<th>売価</th>
						<td></td>
					</tr>
				<nested:iterate property="itemPriceList" indexId="idx">
					<nested:hidden property="sysItemPriceId" />
					<tr>
						<nested:hidden property="sysCorporationId" />
						<td><nested:write property="corporationNm"/></td>
						<td><nested:text disabled="true" property="price" styleId="price${idx}" styleClass="priceText itemPrice" style="width: 100px; text-align: right;" maxlength="9"/></td>
					</tr>
				</nested:iterate>
				</table>
			</div>

			<div id="backOrder">
				<table id="boTable">
<!-- 					表示用 -->
					<nested:notEmpty property="backOrderList" >
					<nested:iterate property="backOrderList" indexId="idx">
					<nested:hidden property="sysBackOrderId"/>
					<tbody class="backOrderTbody">
						<nested:hidden property="deleteFlag" styleClass="deleteFlag"/>
						<tr>
							<td rowspan="2"><span class="indexNum"><%= idx + 1 %></span></td>
							<th>日付</th>
							<td><nested:text property="backOrderDate" styleClass="calender" maxlength="10" /></td>
							<th>会社</th>
							<td class="w250"><nested:select property="sysCorporationId" >
									<html:option value="0">　</html:option>
									<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
								</nested:select>
							</td>
							<td rowspan="2"><a class="button_small_white completeBackOrder" href="Javascript:void(0);">完了</a></td>
						</tr>
						<tr>
							<th>販売チャネル</th>
							<td><nested:select property="sysChannelId" >
									<html:option value="0" />
									<html:optionsCollection property="channelList" label="channelNm" value="sysChannelId" />
								</nested:select>
							</td>
							<th>備考</th>
							<td colspan="3" class="td_left"><nested:textarea property="remarks" styleClass="w250" rows="2" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
											onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);"></nested:textarea>
							</td>
						</tr>
					</tbody>
					</nested:iterate>
					</nested:notEmpty>
				</table>
				<table id="addBackOrderArea">
					<nested:notEmpty property="addBackOrderList" >
					<nested:iterate property="addBackOrderList" indexId="idx">
					<tbody class="addBackOrderTbody">
						<tr class="addBackOrderRow1">
							<td rowspan="2"><span class="indexNumAdd"><%= idx + 1 %></span></td>
							<th>日付</th>
							<td><nested:text property="backOrderDate" styleClass="calender addBackOrderDate" maxlength="10" /></td>
							<td style="background-color:#258; color:white; font-weight:bold;">会社</td>
							<td class="w250"><nested:select property="sysCorporationId" styleClass="sysCorporationId" style="min-width:120px;">
									<html:option value="0">　</html:option>
									<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
								</nested:select></td>
							<td rowspan="2"><a class="button_small_white removeBackOrder" href="Javascript:void(0);">削除</a></td>
						</tr>
						<tr class="addBackOrderRow2">
							<th>販売チャネル</th>
							<td><nested:select property="sysChannelId" styleClass="sysChannelId" style="min-width:120px;">
									<html:option value="0">　</html:option>
									<html:optionsCollection property="channelList" label="channelNm" value="sysChannelId" />
								</nested:select>
							<td style="background-color:#258; color:white; font-weight:bold;">備考</td>
							<td colspan="3" class="td_left"><nested:textarea property="remarks" styleClass="w250 remarks" rows="2" onfocus="PxTextareaAdjuster(this);" onkeyup="PxTextareaAdjuster(this);"
											onchange="PxTextareaAdjuster(this);" onblur="PxTextareaAdjuster(this);"></nested:textarea>
							</td>

						</tr>
					</tbody>
					</nested:iterate>
					</nested:notEmpty>

				</table>
			</div>

			<div id="manual">
			<nested:nest property="mstItemDTO">
				<table id="fileArea">
					<tr>
						<td><label><nested:checkbox property="manualFlg"/>説明書有</label>&nbsp;&nbsp;&nbsp;<label><nested:checkbox property="planSheetFlg"/>図面有</label></td>
					</tr>
<!-- 					表示用 -->
					<logic:notEmpty name="btobBillForm" property="itemManualList">
					<logic:iterate id="itemManualList" name="btobBillForm" property="itemManualList" indexId="idx">
						<tr class="itemManualRow">
						<html:hidden property="itemManualList[${idx}]sysItemManualId"  styleClass="sysItemManualId"/>
						<html:hidden property="itemManualList[${idx}]sysItemId" />
						<html:hidden property="itemManualList[${idx}]deleteFlag" styleClass="deleteFlag"/>
						<html:hidden property="itemManualList[${idx}]manualPath" styleClass="manualPath"/>
							<td colspan="1"><html:file property="itemManualList[${idx}]manualFile" style="display: none;" styleId="manualFile${idx}" onchange="manualFileNameDisp${idx}.value = this.value;" styleClass="hideManualFile"/>
								<input type="text" name="manualFileNameDisp" value="${btobBillForm.itemManualList[idx].manualFileNameDisp}" id="manualFileNameDisp${idx}" disabled="disabled"/>
								<input type="button" id="manualButton" class="manualButton${idx}" onclick="manualFile${idx}.click()" value="参照" /></td>
							<td><a class="button_small_main downLoadItemManual" href="Javascript:void(0);">表示</a></td>
							<td><a class="button_small_white deleteItemManual" href="Javascript:void(0);">削除</a></td>
<!-- 							<td><a class="button_small_main" href="Javascript:void(0);">追加</a></td> -->
						</tr>
					</logic:iterate>
					</logic:notEmpty>
<!-- 					追加用 -->
					<logic:notEmpty name="btobBillForm" property="addItemManualList">

					<logic:iterate id="addItemManualList" name="btobBillForm" property="addItemManualList" indexId="idx">
						<tr class="addItemManualRow">
<%-- 						<html:hidden property="addItemManualList[${idx}]sysItemManualId" /> --%>
<%-- 						<html:hidden property="addItemManualList[${idx}]deleteFlag" /> --%>
							<td colspan="2"><html:file property="addItemManualList[${idx}]manualFile" styleClass="manualFile" /></td>
<!-- 							<td><a class="button_small_white hideItemManual" href="Javascript:void(0);">削除</a></td> -->
<!-- 							<td><a class="button_small_main addItemManual" href="Javascript:void(0);">追加</a></td> -->
						</tr>
					</logic:iterate>
					</logic:notEmpty>
				</table>
			</nested:nest>
			<p class="memo">※ファイル名には、半角英数字のみ入力してください。</p>
			</div>


		</div>

		<footer class="footer sPlanning">
			<ul style="position: relative;">
				<li class="footer_button">
					<nested:equal value="0" property="mstUserDTO.sysUserId">
						<a class="button_main registry" href="javascript:void(0)" >登録</a>
					</nested:equal>
				</li>
				<li class="footer_button">
					<nested:notEqual value="0" property="mstUserDTO.sysUserId">
						<a class="button_main update" href="javascript:void(0)" >修正</a>
					</nested:notEqual>
				</li>
			</ul>
		</footer>
	</nested:form>
</html:html>