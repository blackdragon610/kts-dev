<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<title>仕入先一覧</title>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/detailSupplier.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript" />
	<script src="./js/validation.js" type="text/javascript" />
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

<!--
【海外仕入先一覧画面】
ファイル名：supplierList.jsp
作成日：2016/11/14
作成者：白井崇詞

（画面概要）

仕入先の一覧画面

・行ダブルクリック：仕入先の詳細画面に遷移する。
・新規登録ボタン押下：仕入先の新規登録画面に遷移する。

（注意・補足）

-->

	</head>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailSupplier">
		<html:hidden property="messageFlg" styleId="messageFlgForm"/>
		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: center;"><nested:write property="message" /></p>
				</div>
			</nested:notEmpty>
		</nested:nest>
		<h1 class="heading">海外仕入先一覧</h1>

		<nested:notEmpty property="registryDto">
			<nested:nest property="registryDto">
				<nested:notEmpty property="errorMessage">
					<p id="errorMessage">
						<nested:write property="errorMessage" />
					</p>
				</nested:notEmpty>
			</nested:nest>
		</nested:notEmpty>

		<input type="hidden" name="sysSupplierId" id="sysSupplierId" />
		<table id="mstTable" class="list">
			<tr>
				<th>ID</th>
				<th>会社・工場名</th>
				<th>国</th>
				<th>TEL</th>
				<th>MAIL</th>
			</tr>
			<nested:iterate property="supplierList" indexId="idx">
				<tr ondblclick="goDetailSupplier(<nested:write property="sysSupplierId"/>);">
					<td><nested:write property="sysSupplierId" /></td>
					<td><nested:write property="companyFactoryNm" /></td>
					<td><nested:write property="country"/></td>
					<td><nested:write property="tel" /></td>
					<td><nested:write property="mailAddress" /></td>
				</tr>
			</nested:iterate>
		</table>
		<div class="footerButtonArea">
			<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistrySupplier.do');">新規登録画面</a>
		</div>
	</html:form>

	<script>

	window.onload = function() {

		//メッセージエリア表示設定
		if(!$("#registryDto.message").val()) {
			if ($("#messageFlg").val() == "0") {
				$("#messageArea").addClass("registrySuccess");
			}
			if ($("#messageFlg").val() == "1") {
				$("#messageArea").addClass("registryFailure");
			}
			$("#messageArea").fadeOut(4000);
		}
	};

	function goDetailSupplier(value){

		$("#sysSupplierId").val(value);
		goTransaction('detailSupplier.do');
	}

	</script>
</html:html>