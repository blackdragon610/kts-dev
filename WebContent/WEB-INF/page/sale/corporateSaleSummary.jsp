<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<title>業販売上統計</title>
	<link rel="stylesheet" href="./css/saleSummary.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【売上統計画面】
ファイル名：saleSummary.jsp
作成日：2014/12/19
作成者：八鍬寛之

（画面概要）

売上商品データを絞り込み、エクセル形式でダウンロードする画面
エクセルには合計注文数、売上額、原価、粗利も計算される

・ダウンロードボタン押下：絞り込み条件を元にエクセルをダウンロードする。

（注意・補足）

-->

	<script type="text/javascript">


	$(function() {

		/* 他社製品検索の場合に品番のテキストボックス(FROMとTO)を入力不可にする  */
		 checkflg = false;
		$("#otherItemFlag").click(function(){
			if(checkflg == false){
				$("#itemCodeFrom").prop("disabled" , true);
				$("#itemCodeTo").prop("disabled" , true);
				checkflg = true;
			} else {
				$("#itemCodeFrom").prop("disabled" , false);
				$("#itemCodeTo").prop("disabled" , false);
				checkflg = false;
			}
		});

		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

		$("#downloadSummary").click(function() {
			if ($("#orderDateFrom").val() && $("#orderDateTo").val()){
				fromAry = $("#orderDateFrom").val().split("/");
				toAry = $("#orderDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("期間(受注日) の出力開始日が、出力終了日より後の日付になっています。");
					return false;
				}
			}

			if ($("#scheduledLeavingDateFrom").val() && $("#scheduledLeavingDateTo").val()){
				fromAry = $("#scheduledLeavingDateFrom").val().split("/");
				toAry = $("#scheduledLeavingDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("期間(出庫予定日) の出力開始日が、出力終了日より後の日付になっています。");
					return false;
				}
			}

			if ($("#itemCodeFrom").val() && $("#itemCodeTo").val()) {
				if ($("#itemCodeFrom").val() > $("#itemCodeTo").val()) {
					alert("品番 の出力開始番号が、出力終了番号より大きい値になっています。");
					return false;
				}
			}
			goTransaction('corporateSaleSummaryDownLoad.do');
		});

 	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initCorporateSaleSummary" >

	<h4 class="heading">業販売上統計</h4>

	<h5>絞り込み条件を指定して「ダウンロード」ボタンを押してください</h5>

	<fieldset id="searchOptionField">
	<nested:nest property="corporateSaleSearchDTO" >

		<table class="searchOptionTable">
			<tr>
				<td>受注日</td>
				<td><nested:text property="orderDateFrom" styleId="orderDateFrom" styleClass="calender" /> ～ <nested:text property="orderDateTo" styleId="orderDateTo" styleClass="calender" /></td>
			</tr>
			<tr>
				<td>出庫予定日</td>
				<td><nested:text property="scheduledLeavingDateFrom" styleId="scheduledLeavingDateFrom" styleClass="calender" /> ～ <nested:text property="scheduledLeavingDateTo" styleId="scheduledLeavingDateTo" styleClass="calender" /></td>
			</tr>
			<tr>
				<td>法人</td>
				<td><nested:select property="sysCorporationId">
						<html:option value="0">　</html:option>
						<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
					</nested:select>
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td>販売チャネル</td> -->
<%-- 				<td><nested:select property="sysChannelId" > --%>
<%-- 						<html:option value="0">　</html:option> --%>
<%-- 						<html:optionsCollection property="channelList" label="channelNm" value="sysChannelId" /> --%>
<%-- 					</nested:select> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>処理ルート</td> -->
<%-- 				<td><nested:select property="disposalRoute"> --%>
<%-- 						<html:optionsCollection property="disposalRouteMap" label="value" value="key"/> --%>
<%-- 					</nested:select> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<td>検索対象</td>
				<td>
				<label><nested:checkbox property="otherItemFlag" styleId="otherItemFlag" value="1" > 他社製品のみ </nested:checkbox></label>
				<input type="hidden" name="corporateSaleSearchDTO.otherItemFlag" value="0"/>
				</td>
			</tr>
			<tr>
				<td>品番（前方一致）</td>
				<td><nested:text property="itemCode" styleClass="text_w120" maxlength="11" /></td>
			</tr>
			<tr>
				<td>品番</td>
				<td><nested:text property="itemCodeFrom" styleId="itemCodeFrom" styleClass="text_w120 numText" maxlength="11" /> ～ <nested:text property="itemCodeTo" styleId="itemCodeTo" styleClass="text_w120 numText" maxlength="11" /></td>
			</tr>
			<tr>
				<td>商品名</td>
				<td><nested:text property="itemNm" styleClass="text_w200" /></td>
			</tr>


			<tr>
				<td>並び順</td>
				<td>
					<nested:select property="sortFirst">
						<html:optionsCollection property="summalySortMap" value="key" label="value" />
					</nested:select>
					<nested:select property="sortFirstSub">
						<html:optionsCollection property="saleSearchSortOrder" value="key" label="value" />
					</nested:select>
				</td>
				<td><a id="downloadSummary" class="button_main" href="javascript:void(0);"  >ダウンロード</a></td>
			</tr>
			<tr>
				<td>全受注表示</td>
				<td>
				<label><nested:checkbox property="allOrderDisplayFlag" value="1">受注分も含む</nested:checkbox></label>
					<input type="hidden" name="corporateSaleSearchDTO.allOrderDisplayFlag" value="0"/>
				</td>
			</tr>
		</table>
	</nested:nest>
	</fieldset>

	</html:form>
</html:html>