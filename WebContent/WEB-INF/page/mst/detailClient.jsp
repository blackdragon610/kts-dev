<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<title>得意先詳細</title>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/detailClient.css" type="text/css" />

	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>
	<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
	<script src="./js/shortcut.js"></script>

<!--
【得意先マスタ詳細画面】
ファイル名：detailClient.jsp
作成日：2015/07/19
作成者：高桑良大

（画面概要）

得意先情報の詳細画面

・更新ボタン押下：得意先情報を更新する。
・削除ボタン押下：得意先情報を削除する。

（注意・補足）

-->
	<script>
	$(function() {
		if (window.opener) {
			$("#cssmenu").hide();
		}

		if ($("#sysClientId").val() != 0) {
			shortcut.add("F2", function(){
				goTransaction("initRegistryClient.do");
			});
		}

		$("#registry").click( function () {
			if(!$("#clientNo").val()){
				alert("顧客Noは必須です。");
				return;
			}

			goTransaction('registryClient.do');
		});

		$("#update").click( function () {
			if(!$("#clientNo").val()){
				alert("顧客Noは必須です。");
				return;
			}

			goTransaction('updateClient.do');
		});

		$("#delete").click( function () {
			if(!confirm("得意先情報を削除します。")){
				return;
			}
			goTransaction('deleteClient.do');
		});
	});



	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		if ($("#alertType").val() == 1 && window.opener) {
			//親画面
			var corpSaleSlip = window.opener;

			corpSaleSlip.$("#sysClientId").val($("#sysClientId").val());
			corpSaleSlip.$("#clientNo").val($("#clientNo").val());
			corpSaleSlip.$("#clientNm").text($("#clientNm").val());
			corpSaleSlip.$("#paymentMethod").val($("#paymentMethod").val()).change();

			var delivList = [];

			var newDelivery = {
				deliveryNm : '',
				deliveryNmKana : $("#clientNmKana").val(),
				tel : $("#tel").val(),
				fax : $("#fax").val(),
				zip : $("#zip").val(),
				prefectures : $("#prefectures").val(),
				municipality : $("#municipality").val(),
				address : $("#address").val(),
				buildingNm : $("#buildingNm").val(),
				quarter : $("#quarter").val(),
				position : $("#position").val(),
				contactPersonNm : $("#contactPersonNm").val(),
				billingDst : $("#billingDst").val(),
			};
			delivList.push(newDelivery);

			corpSaleSlip.$("#deliverySelect").hide();
			corpSaleSlip.$("#deliveryJson").val(JSON.stringify(delivList)).change();

			window.close();
		}
		document.getElementById('alertType').value='0';
	};

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<div class="contents">
		<html:form action="/detailClient">
		<nested:notEqual value="0" property="clientDTO.sysClientId">
		<h4 class="heading">得意先情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="clientDTO.sysClientId">
		<h4 class="heading">得意先情報登録</h4>
		</nested:equal>
			<input type="hidden" name="sysClientId"  id="sysClientId" value="<nested:write property="clientDTO.sysClientId" />" />
			<html:errors/>
			<nested:hidden property="alertType" styleId="alertType" />
			<input type="hidden" name="sysCorporationId" value="<nested:write property="clientDTO.sysCorporationId" />" />
			<nested:nest property="clientDTO">

				<nested:notEqual value="0" property="sysClientId">
					<div class="AddButtonArea">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryClient.do');">新しい得意先を登録する</a>
					</div>
				</nested:notEqual>

				<nested:notEqual value="0" property="sysClientId">
					<div class="CopyButtonArea">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('copyRegistryClient.do');">複製する</a>
					</div>
				</nested:notEqual>

				<table id="mstTable" class="">
					<nested:notEqual value="0" property="sysClientId">
						<tr>
							<th>得意先ID</th>
							<td><nested:write property="sysClientId" /></td>
						</tr>
					</nested:notEqual>
					<tr>
						<th>得意先番号<label class="necessary">※</label></th>
						<td><nested:text property="clientNo" styleId="clientNo" /></td>
					</tr>
					<tr>
						<th>取引先法人</th>
						<td>
							<bean:write name="clientForm" property="corporationNm" />
							<nested:hidden property="sysCorporationId" />
						</td>
					</tr>
					<tr>
						<th>得意先名</th>
						<td><nested:text property="clientNm" styleClass="text_w250" styleId="clientNm" /></td>
					</tr>
					<tr>
						<th>フリガナ</th>
						<td><nested:text property="clientNmKana" styleClass="text_w250" styleId="clientNmKana" /></td>
					</tr>
					<tr>
						<th>略称</th>
						<td><nested:text property="clientAbbreviation" /></td>
					</tr>
				</table>

				<table id="mstTable" class="">
					<tr>
						<th>郵便番号</th>
						<td><nested:text property="zip" styleId="zip" onkeyup=
						"AjaxZip3.zip2addr('clientDTO.zip', '', 'clientDTO.prefectures', 'clientDTO.municipality', 'clientDTO.address');" /></td>
					</tr>
					<tr>
						<th>住所(都道府県)</th>
						<td><nested:text property="prefectures" styleId="prefectures" /></td>
					</tr>
					<tr>
						<th>住所(市区町村)</th>
						<td><nested:text property="municipality" styleId="municipality" /></td>
					</tr>
					<tr>
						<th>住所(市区町村以降)</th>
						<td><nested:text property="address" styleId="address" /></td>
					</tr>
					<tr>
						<th>住所(建物名等)</th>
						<td><nested:text property="buildingNm" styleClass="text_w250" styleId="buildingNm" /></td>
					</tr>
					<tr>
						<th>部署名</th>
						<td><nested:text property="quarter" styleId="quarter" /></td>
					</tr>
					<tr>
						<th>役職名</th>
						<td><nested:text property="position" styleId="position" /></td>
					</tr>
					<tr>
						<th>ご担当者</th>
						<td>
						<nested:text property="contactPersonNm" styleId="contactPersonNm" />
						</td>
					</tr>
					<tr>
						<th>TEL</th>
						<td><nested:text property="tel" styleId="tel" /></td>
					</tr>
					<tr>
						<th>FAX</th>
						<td><nested:text property="fax" styleId="fax" /></td>
					</tr>
					<tr>
						<th>E-mail</th>
						<td><nested:text property="mailAddress" styleId="mailAddress" /></td>
					</tr>
					<tr>
						<th>運送会社</th>
						<td><nested:select property="transportCorporationSystem">
								<html:optionsCollection property="transportCorporationSystemMap" label="value" value="key"/>
						</nested:select></td>
					</tr>
				</table>

				<table id="mstTable" class="">
					<tr>
						<th>締日</th>
						<td>
						<nested:select property="cutoffDate">
								<html:optionsCollection property="cutoffDateMap" label="value" value="key"/>
						</nested:select>
						</td>
					</tr>
					<tr>
						<th>請求先</th>
						<td>
						<nested:text property="billingDst" styleId="billingDst" />
						</td>
					</tr>
					<tr>
						<th>支払方法</th>
						<td><nested:select property="paymentMethod" styleId="paymentMethod">
							<html:optionsCollection property="paymentMethodMap" label="value" value="key" />
						</nested:select></td>
					</tr>
				</table>

				<table>
					<tr>
						<th>備考/一言メモ</th>
						<td><nested:textarea property="remarks" rows="6" cols="50"/></td>
					</tr>
				</table>

				<nested:equal value="0" property="sysClientId">
					<div class="buttonArea">
						<a class="button_main" href="Javascript:void(0);" id="registry">登録</a>
					</div>
				</nested:equal>
				<nested:notEqual value="0" property="sysClientId">
					<div class="buttonArea">
						<a class="button_main" href="Javascript:void(0);" id="update">更新</a>
						<a class="button_white" href="Javascript:void(0);" id="delete">削除</a>
					</div>
				</nested:notEqual>
			</nested:nest>

		</html:form>
	</div>
</html:html>