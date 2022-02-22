<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/clientSearch.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【商品検索画面】
ファイル名：itemSearch.jsp
作成日：2014/12/10
作成者：八鍬寛之

（画面概要）

売上詳細・入荷予定一括登録画面から子画面として開く。
親画面で「商品検索」ボタンを押した際、品番と商品名に入力されている値を
引継ぎそのまま検索をかける。

・検索ボタン押下：品番・商品名を条件に検索を実行する。商品名は部分一致。
・選択ボタン押下：親画面に選択された商品の値を渡し、画面を閉じる。

（注意・補足）

-->

<script type="text/javascript">
$(function () {

	$(".select").click( function () {

		//親画面
		var corpSaleSlip = window.opener;

		//選択ボタンが押された行のデータ
		var selectArea = $(this).parents("tr.clientSearchRow");
		var remarks = selectArea.find(".remarks").val();
		//備考の文字列が多い場合は省略する

		corpSaleSlip.$("#sysClientId").val(selectArea.find(".sysClientId").val());
		corpSaleSlip.$("#clientNo").val(selectArea.find(".clientNo").text());
		corpSaleSlip.$("#clientNm").text(selectArea.find(".clientNm").text());
		corpSaleSlip.$("#clientTel").text(selectArea.find(".tel").val());
		corpSaleSlip.$("#clientFax").text(selectArea.find(".fax").val());
 		corpSaleSlip.$("#clientRemarks").text(selectArea.find(".remarks").val());
 		corpSaleSlip.$("#clientRemarksShortened").text(selectArea.find(".shortenedRemarks").val());
 		corpSaleSlip.$("#clientRemarksShortened").attr('title' ,selectArea.find(".remarks").val());
		corpSaleSlip.$("#paymentMethod").val(selectArea.find(".paymentMethod").val()).change();
		corpSaleSlip.$("#transportCorporationSystem").val(selectArea.find(".transportCorporationSystem").val()).trigger("change");

		var delivList = [];

		var newDelivery = {
			deliveryNm : '',
			deliveryNmKana : selectArea.find(".clientNmKana").val(),
			tel : selectArea.find(".tel").val(),
			fax : selectArea.find(".fax").val(),
			zip : selectArea.find(".zip").val(),
			prefectures : selectArea.find(".prefectures").val(),
			municipality : selectArea.find(".municipality").val(),
			address : selectArea.find(".address").val(),
			buildingNm : selectArea.find(".buildingNm").val(),
			quarter : selectArea.find(".quarter").val(),
			position : selectArea.find(".position").val(),
			contactPersonNm : selectArea.find(".contactPersonNm").val(),
		};
		corpSaleSlip.deliveryInfo.push(newDelivery);
		delivList.push(newDelivery);

		$("#sysClientId").val(selectArea.find(".sysClientId").val());

		$.ajax({
			type : 'post'
			,url : 'getDeliveryList.do'
			,dataType : 'json'
			,data : {'sysClientId' : selectArea.find(".sysClientId").val()}
		}).done(function(data){
			if (data.length != 0) {
				corpSaleSlip.$("#deliverySelect").children().remove();
				corpSaleSlip.$("#deliverySelect").append("<option></option>");
				for (var i = 0; i < data.length; i++) {
					var newDelivery = {
						deliveryNm : data[i].deliveryNm,
						deliveryNmKana : data[i].deliveryNmKana,
						tel : data[i].tel,
						fax : data[i].fax,
						zip : data[i].zip,
						prefectures : data[i].prefectures,
						municipality : data[i].municipality,
						address : data[i].address,
						buildingNm : data[i].buildingNm,
						quarter : data[i].quarter,
						position : data[i].position,
						contactPersonNm : data[i].contactPersonNm,
					};

					delivList.push(newDelivery);

					corpSaleSlip.$("#deliverySelect").append("<option value='" + (i + 1) + "'>" + data[i].deliveryNm + "</option>");
				}

				corpSaleSlip.$("#deliverySelect").show();
			} else {
				corpSaleSlip.$("#deliverySelect").hide();
			}
				corpSaleSlip.$("#deliveryJson").val(JSON.stringify(delivList)).change();
				window.close();
		});



	});

	if($("#clientListSize").val() == '1') {

		$(".select").click();
	}

	$(".search").click( function () {

		goTransaction("subWinClientSearch.do");
	});

	$("#registry").click( function() {

		goTransaction("initRegistryClient.do");
	});
});

function getDeliveryList() {
	$.ajax({
		type : 'post'
		,url : 'getDeliveryList.do'
		,dataType : 'json'
	}).done(function(data){
		alert(data[0].deliveryNm);
	});
}
</script>
	</head>
	<nested:form action="/subWinClientSearch"	>
	<h4 class="head">得意先検索</h4>
	<nested:hidden property="sysClientId" styleId="sysClientId" />
	<input type="hidden" name="sysCorporationId" id="sysCorporationId" value="<nested:write property="clientSearchDTO.sysCorporationId" />" />
	<div class="searchOptionArea">
		<table class="searchOptionTable">
		<nested:nest property="clientSearchDTO">
			<nested:hidden property="sysCorporationId" />

			<tr>
				<td>得意番号</td>
				<td><nested:text property="clientNo" styleClass="text_w120" maxlength="30" /></td>
			</tr>
			<tr>
				<td>得意先名</td>
				<td><nested:text property="clientNm" styleClass="text_w120" maxlength="30" /></td>
			</tr>
		</nested:nest>
		</table>
	</div>

	<nested:hidden property="clientListSize" styleId="clientListSize"/>
	<div class="paging_num">
		<h3>全<nested:write property="clientListSize" />件</h3>
		<div class="searchButton"><a class="button_main search" href="Javascript:void(0);">検索</a></div>
	</div>

	<div class="out_Div">
		<div class="in_Div">
			<table class="list_table">
				<thead>
					<tr>
						<th class="wCode">得意先番号</th>
						<th class="wNm">得意先名</th>
<!-- 						<th class="wNum">在庫数</th> -->
						<th class="wButton"></th>
					</tr>
				</thead>
				<nested:iterate property="searchClientList" indexId="idx" id="searchClientList" >
				<tr class="clientSearchRow">
					<nested:hidden property="sysClientId" styleClass="sysClientId" />
					<nested:hidden property="paymentMethod" styleClass="paymentMethod" />
					<nested:hidden property="clientNmKana" styleClass="clientNmKana" />
					<nested:hidden property="tel" styleClass="tel" />
					<nested:hidden property="fax" styleClass="fax" />
					<nested:hidden property="zip" styleClass="zip" />
					<nested:hidden property="remarks" styleClass="remarks" />
					<nested:hidden property="shortenedRemarks" styleClass="shortenedRemarks" />
					<nested:hidden property="prefectures" styleClass="prefectures" />
					<nested:hidden property="municipality" styleClass="municipality" />
					<nested:hidden property="address" styleClass="address" />
					<nested:hidden property="buildingNm" styleClass="buildingNm" />
					<nested:hidden property="quarter" styleClass="quarter" />
					<nested:hidden property="position" styleClass="position" />
					<nested:hidden property="contactPersonNm" styleClass="contactPersonNm" />
					<nested:hidden property="transportCorporationSystem" styleClass="transportCorporationSystem" />
					<td class="clientNo wListCode"><nested:write property="clientNo"/></td>
					<td class="clientNm wListNm"><nested:write property="clientNm"/></td>
					<td class="wListButton wListButton"><a class="button_small_main select" href="Javascript:void(0);">選択</a></td>
				</tr>
				</nested:iterate>
			</table>
		</div>
		<div class="td_center mt30">
			<a class="button_main" href="Javascript:void(0);" id="registry">新規登録</a>
		</div>
	</div>

	</nested:form>
</html:html>