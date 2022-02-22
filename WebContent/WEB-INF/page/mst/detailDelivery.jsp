<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/detailDelivery.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>
	<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>

<!--
【納入先マスタ詳細画面】
ファイル名：detailUser.jsp
作成日：2014/12/19
作成者：八鍬寛之

（画面概要）

ユーザー情報の詳細画面

・更新ボタン押下：ユーザー情報を更新する。
・削除ボタン押下：ユーザー情報を削除する。

（注意・補足）

-->

	<SCRIPT type="text/javascript">
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';

		$('#corporationList').change(function() {
	 		changeCorp();
		});

		initChangeCorp();
	}

	function changeCorp() {

		var sysCorporationId = $("#corporationList").val();

		$.ajax({
			type : 'post'
			,url : 'getClientList.do'
			,dataType : 'json'
			,data : {'sysCorporationId' : sysCorporationId}
		}).done(function(data){
			$("#clientList option").remove();
			for (var i = 0; i < data.length; i++) {
				$("#clientList").append("<option value='" + data[i].sysClientId + "'>" + data[i].clientNm + "</option>");
			}
		});
	}

	function initChangeCorp() {

		var tempClient = $('#clientList').val();
		var sysCorporationId = $("#corporationList").val();

		$.ajax({
			type : 'post'
			,url : 'getClientList.do'
			,dataType : 'json'
			,data : {'sysCorporationId' : sysCorporationId}
		}).done(function(data){
			$("#clientList option").remove();
			for (var i = 0; i < data.length; i++) {
				$("#clientList").append("<option value='" + data[i].sysClientId + "'>" + data[i].clientNm + "</option>");
			}
			$('#clientList').val(tempClient);
		});
	}
	</SCRIPT>
	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<div class="contents">
		<html:form action="/registryDelivery">
		<nested:notEqual value="0" property="deliveryDTO.sysDeliveryId">
		<h4 class="heading">納入先情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="deliveryDTO.sysDeliveryId">
		<h4 class="heading">納入先情報登録</h4>
		</nested:equal>

		<html:errors/>
		<nested:hidden property="alertType" styleId="alertType" />
		<nested:nest property="deliveryDTO">
			<table id="mstTable">
				<nested:notEqual value="0" property="sysDeliveryId">
					<tr>
						<th>納入先ID</th>
						<td><nested:write property="sysDeliveryId" /></td>
					</tr>
				</nested:notEqual>
				<tr>
					<th>法人名</th>
					<td>
						<html:select name="deliveryForm" property="sysCorporationId" styleId="corporationList">
							<html:optionsCollection name="deliveryForm" property="corporationList" label="corporationNm" value="sysCorporationId" />
						</html:select>
					</td>
				</tr>
				<tr>
					<th>
						得意先名
					</th>
					<td><nested:select property="sysClientId" styleId="clientList">
						<html:optionsCollection property="clientList" label="clientNm" value="sysClientId" />
					</nested:select></td>
				</tr>
				<tr>
					<th>納入先名</th>
					<td><nested:text property="deliveryNm" /></td>
				</tr>
				<tr>
					<th>納入先名カナ</th>
					<td><nested:text property="deliveryNmKana" /></td>
				</tr>
				<tr>
					<th>郵便番号</th>
					<td><nested:text property="zip" onkeyup=
						"AjaxZip3.zip2addr('deliveryDTO.zip', '', 'deliveryDTO.prefectures', 'deliveryDTO.municipality', 'deliveryDTO.address');" /></td>
				</tr>
				<tr>
					<th>住所（都道府県）</th>
					<td><nested:text property="prefectures" /></td>
				</tr>
				<tr>
					<th>住所（市区町村）</th>
					<td><nested:text property="municipality" /></td>
				</tr>
				<tr>
					<th>住所（市区町村以降）</th>
					<td><nested:text property="address" /></td>
				</tr>
				<tr>
					<th>住所（建物名）</th>
					<td><nested:text property="buildingNm" /></td>
				</tr>
				<tr>
					<th>部署名</th>
					<td><nested:text property="quarter" /></td>
				</tr>
				<tr>
					<th>役職名</th>
					<td><nested:text property="position" /></td>
				</tr>
				<tr>
					<th>御担当者名</th>
					<td><nested:text property="contactPersonNm" /></td>
				</tr>
				<tr>
					<th>電話番号</th>
					<td><nested:text property="tel" /></td>
				</tr>
				<tr>
					<th>FAX番号</th>
					<td><nested:text property="fax" /></td>
				</tr>
			</table>

			<nested:equal value="0" property="sysDeliveryId">
				<div class="buttonArea">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryDelivery.do');">登録</a>
				</div>
			</nested:equal>
			<nested:notEqual value="0" property="sysDeliveryId">
				<div class="buttonArea">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateDelivery.do');">更新</a>
					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteDelivery.do');">削除</a>
				</div>
			</nested:notEqual>

		</nested:nest>
		</html:form>
	</div>
	</body>
</html:html>