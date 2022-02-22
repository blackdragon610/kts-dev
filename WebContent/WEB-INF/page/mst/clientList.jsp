<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<title>得意先一覧</title>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/clientList.css" type="text/css" />

	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

<!--
【ユーザー一覧画面】
ファイル名：userList.jsp
作成日：2014/12/15
作成者：八鍬寛之

（画面概要）

ユーザーの一覧画面

・行ダブルクリック：ユーザーの詳細画面に遷移する。
・新規登録ボタン押下：ユーザーの新規登録画面に遷移する。

（注意・補足）

-->
	<script>

	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	};

	$(document).ready(function(){
		$("#corpLink<bean:write name="clientForm" property="clientSearchDTO.sysCorporationId" />").removeAttr("href");
		$("#corpLink<bean:write name="clientForm" property="clientSearchDTO.sysCorporationId" />").parents("li").attr("class", "corp");
		$("#corpLink<bean:write name="clientForm" property="clientSearchDTO.sysCorporationId" />").attr("class", "selected");
		$("#heading").text('得意先一覧 - ' + $("#corpLink<bean:write name="clientForm" property="clientSearchDTO.sysCorporationId" />").text());
	});

	$(function() {
		$('#searchOptionOpen').click(function () {

			if($('#searchOptionOpen').text() == "▼検索") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼検索");
			}

			$('#searchOptionArea').slideToggle("fast");
		});

		$(".corpLink").click(function(){
			var corporationId = $(this).find(".sysCorporationId").val();
			$("#sysCorporationId").val(corporationId);
			goTransaction('clientList.do');
		});

		$("#registryClient").click(function(){
			$("#regCorporationId").val($("#sysCorporationId").val());
			goTransaction('initRegistryClient.do');
		});
	});

	function goDetailClient(value){

		document.getElementById('sysClientId').value = value;
		goTransaction('detailClient.do');
	}

	function searchClient(value){
		$("#sysCorporationId").val(value);
		goTransaction('clientList.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/clientList">
	<nested:hidden property="alertType" styleId="alertType" />
	<nested:hidden property="sysCorporationId" styleId="regCorporationId" />
	<h4 class="heading" id="heading">得意先一覧</h4>

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
		<nested:nest property="clientSearchDTO">
			<nested:hidden property="sysCorporationId" styleId="sysCorporationId" />
			<div id="searchOptionArea">
				<table id="search_option">
					<tr>
						<td>得意先番号</td>
						<td><nested:text styleClass="text_w265" property="clientNo" /></td>
					</tr>
					<tr>
						<td>得意先名</td>
						<td><nested:text styleClass="text_w265" property="clientNm" /></td>
					</tr>
					<tr>
						<td>支払方法</td>
						<td><nested:select property="paymentMethod">
							<option value="0"></option>
							<html:option value="1">掛売</html:option>
							<html:option value="2">代引き</html:option>
							<html:option value="3">銀行振込</html:option>
						</nested:select></td>
					</tr>
					<tr>
						<td>売掛残</td>
						<td><select>
							<option></option>
							<option>有</option>
							<option>無</option>
						</select></td>
					</tr>
					<tr>
						<td></td><td class="td_center" style="padding-left: 20px;"><a class="button_main search" href="javascript:void(0);" onclick="goTransaction('clientList.do');" >検索</a></td>
					</tr>
				</table>
			</div>
		</nested:nest>
	</fieldset>


	<nested:notEqual property="clientSearchDTO.sysCorporationId" value="0">
		<div class="buttonArea">
			<a class="button_main" id="registryClient" href="Javascript:void(0);">新規登録</a>
		</div>
	</nested:notEqual>
	<input type="hidden" name="sysClientId"  id="sysClientId" />
	<table id="mstTable" class="list">
		<tr>
			<th>得意先番号</th>
			<th>得意先名</th>
			<th>取引先法人</th>
			<th>支払方法</th>
			<th>売掛残</th>
		</tr>
		<nested:iterate property="extClientList" indexId="idx">
			<tr ondblclick="goDetailClient(<nested:write  property="sysClientId"/>);">
				<td><nested:write property="clientNo" /></td>
				<td><nested:write property="clientNm" /></td>
				<td><nested:write property="corporationNm" /></td>
				<td><nested:write property="paymentMethodNm" /></td>
				<td>
					<nested:equal value="1" property="paymentMethod">
						<nested:write property="receivableBalance" />
					</nested:equal>
					<nested:notEqual value="1" property="paymentMethod">
						-
					</nested:notEqual></td>
			</tr>
		</nested:iterate>
	</table>


	</html:form>
</html:html>