<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【納入先一覧画面】
ファイル名：deliveryList.jsp
作成日：2014/12/15
作成者：八鍬寛之

（画面概要）

納入先の一覧画面

・行ダブルクリック：納入先の詳細画面に遷移する。
・新規登録ボタン押下：納入先の新規登録画面に遷移する。

（注意・補足）

-->

	<script>
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	}

	function goDetailDelivery(value){

		$("#sysDeliveryId").val(value);
		goTransaction('detailDelivery.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailDelivery">
	<h4 class="heading">納入先一覧</h4>
	<nested:hidden property="alertType" styleId="alertType" />

	<input type="hidden" name="sysDeliveryId"  id="sysDeliveryId" />
	<table id="mstTable" class="list">
		<tr>
			<th>納入先ID</th>
			<th>得意先名</th>
			<th>納入先名</th>
		</tr>
		<nested:iterate property="deliveryList" indexId="idx">
			<tr ondblclick="goDetailDelivery(<nested:write  property="sysDeliveryId"/>);">
				<td><nested:write property="sysDeliveryId" /></td>
				<td><nested:write property="clientNm" /></td>
				<td><nested:write property="deliveryNm" /></td>
			</tr>
		</nested:iterate>
	</table>

	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryDelivery.do');">新規登録</a>
	</div>

	</html:form>
</html:html>