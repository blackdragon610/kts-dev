<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />

<!--
【倉庫マスタ一覧画面】
ファイル名：warehouseList.jsp
作成日：2014/12/15
作成者：八鍬寛之

（画面概要）

倉庫の一覧画面

・行ダブルクリック：倉庫の詳細画面に遷移する。
・新規登録ボタン押下：倉庫の新規登録画面に遷移する。

（注意・補足）

-->

	<script>
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	}

	function goDetailWarehouse(value){

		document.getElementById('sysWarehouseId').value = value;
		goTransaction('detailWarehouse.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailWarehouse">
	<nested:hidden property="alertType" styleId="alertType"/>

	<h4 class="heading">倉庫一覧</h4>
	<input type="hidden" name="sysWarehouseId"  id="sysWarehouseId" />
	<table id="mstTable" class="list">
		<tr>
			<th>ID</th>
			<th>倉庫名</th>
			<th>所在地</th>
		</tr>
		<nested:iterate property="warehouseList" indexId="idx">
		<tr ondblclick="goDetailWarehouse(<nested:write  property="sysWarehouseId"/>);">
			<td><nested:write  property="sysWarehouseId"/></td>
			<td><nested:write  property="warehouseNm"/></td>
			<td><nested:write  property="address"/></td>
		</tr>
		</nested:iterate>
	</table>

	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryWarehouse.do');">新規登録</a>
	</div>

	</html:form>
</html:html>