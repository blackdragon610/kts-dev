<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/scheduledLumpArrivalRegistry.css" type="text/css" />
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
【一括入荷予定登録画面】
ファイル名：scheduledLumpArrivalRegistry.jsp
作成日：2014/12/19
作成者：八鍬寛之

（画面概要）

入荷予定の一括登録画面
商品を検索し、10件まで登録できる

・商品検索ボタン押下：入荷予定を登録する商品を検索する。
・一括登録ボタン押下：登録処理を行う。

（注意・補足）

-->

	<script type="text/javascript">
	$(function() {
		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1
		});

		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		var addArrivalScheduleItemRowArea = $("tr.addArrivalScheduleItemRow");
		addArrivalScheduleItemRowArea.hide();

		for (var i = 0; i < 10; i++) {
			$("tr.addArrivalScheduleItemRow").eq(i).show();
		}

		//検索追加用
		$(".searchAddItem").click (function () {

			var setItemArea = $("tr.itemSearchRow");

			var rowNum = $("tr.itemSearchRow").index($(this).parents("tr.itemSearchRow"));

			$("#searchItemCode").val(setItemArea.eq(rowNum).find(".addItemCode").val());
			$("#searchItemNm").val(setItemArea.eq(rowNum).find(".addItemNm").val());
			$("#openerIdx").val(rowNum);

			FwGlobal.submitForm(document.forms[0],"/subWinItemSearch","itemSearchWindow","top=130,left=500,width=780px,height=520px;");
		});

		$(".regist").click (function () {

			if(!addArrivalScheduleItemAlert($("tr.addArrivalScheduleItemRow"))) {
				return;
			}

			if (!confirm("入荷予定を登録します。よろしいですか？")) {
				return;
			}

			goTransaction('registryLumpArrivalSchedule.do');
		});

		function addArrivalScheduleItemAlert (showTr) {

			for (var i = 0; i <= 10; i++) {

				var addItemCode = showTr.eq(i).find(".addItemCode").val();
				var addItemNm = showTr.eq(i).find(".addItemNm").val();
				var itemOrderDate = showTr.eq(i).find(".itemOrderDate").val();
				var arrivalScheduleDate = showTr.eq(i).find(".arrivalScheduleDate").val();
				var arrivalNum = new Number(showTr.eq(i).find(".arrivalNum").val());

				if (addItemCode != "" || addItemNm != "" || arrivalScheduleDate != "" || arrivalNum != 0) {

					if (addItemCode == "" || addItemNm == "" || itemOrderDate == "" || arrivalScheduleDate == "" || arrivalNum == 0) {

						alert('入力する場合は、全て必須項目です。');
						return false;
					}
				}
			}
			return true;
		}

// 		function copySalesItem(copyFromTr, copyToTr) {

// 			copyToTr.find(".addSysItemId").val(copyFromTr.find(".addSysItemId").val());
// 			copyToTr.find(".addItemCode").val(copyFromTr.find(".addItemCode").val());
// 			copyToTr.find(".addItemNm").val(copyFromTr.find(".addItemNm").val());
// 			copyToTr.find(".addOrderNum").val(copyFromTr.find(".addOrderNum").val());
// 			copyToTr.find(".addPieceRate").val(copyFromTr.find(".addPieceRate").val());
// 			copyToTr.find(".addCost").val(copyFromTr.find(".addCost").val());
// 			copyToTr.find(".addTotalStockNum").html(copyFromTr.find(".addTotalStockNum").html());

// 			copyFromTr.find(".addSysItemId").val(0);
// 			copyFromTr.find(".addItemCode").val("");
// 			copyFromTr.find(".addItemNm").val("");
// 			copyFromTr.find(".addOrderNum").val(0);
// 			copyFromTr.find(".addPieceRate").val(0);
// 			copyFromTr.find(".addCost").val(0);
// 			copyFromTr.find(".addTotalStockNum").html(0);
// 		}
	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form>

	<h4 class="heading">一括入荷予定登録</h4>

	<nested:nest property="searchItemDTO">
		<nested:hidden property="itemCode" styleId="searchItemCode" />
		<nested:hidden property="itemNm" styleId="searchItemNm" />
		<nested:hidden property="itemCodeTo" />
		<nested:hidden property="openerIdx" styleId="openerIdx" />
		<nested:hidden property="sysCorporationId" styleId="searchSysCorporationId" />
	</nested:nest>

	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<div id="list_area">
		<table id="list_table">
			<tr>
				<th class="index"></th>
				<th class="code">品番</th>
				<th class="itemNm">商品名</th>
				<th class="orderDate">発注日</th>
				<th class="scheduleDate">入荷予定日</th>
				<th class="scheduleNum">入荷予定数</th>
			</tr>





			<logic:iterate name="itemForm" id="lumpArrivalScheduleList" property="lumpArrivalScheduleList" indexId="idx">
			<tr class="addArrivalScheduleItemRow itemSearchRow">
				<html:hidden property="lumpArrivalScheduleList[${idx}].sysItemId" styleClass="addSysItemId" />

				<td><a class="button_small_main searchAddItem" href="javascript:void(0)" >商品検索</a></td>

				<td><html:text property="lumpArrivalScheduleList[${idx}].itemCode" styleClass="addItemCode text_w120 numText codeCheck" maxlength="11" /></td>

				<td><html:text property="lumpArrivalScheduleList[${idx}].itemNm" styleClass="addItemNm text_w400" /></td>

				<td><html:text property="lumpArrivalScheduleList[${idx}].itemOrderDate" styleClass="dateText calender itemOrderDate" maxlength="10"/></td>

				<td><html:text property="lumpArrivalScheduleList[${idx}].arrivalScheduleDate" styleClass="dateText calender arrivalScheduleDate" maxlength="10"/></td>

				<td><html:text property="lumpArrivalScheduleList[${idx}].arrivalNum" styleClass="num numText arrivalNum" maxlength="4"/></td>

<!-- 				<td> -->
<!-- 					<a class="button_small_main searchAddItem" href="javascript:void(0)" >商品検索</a>&nbsp; -->
<!-- 					<a class="button_small_white removeSetItem" href="Javascript:void(0);">削除</a> -->
<!-- 				</td> -->
			</tr>
			</logic:iterate>









		</table>
	</div>

	<div class="paging_area">
		<div class="button_area">
			<a class="button_main regist" href="Javascript:void(0);">一括登録</a>
		</div>
	</div>


	</html:form>
</html:html>