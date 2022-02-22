<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />

<!--
【セット商品一覧画面】
ファイル名：setItemList.jsp
作成日：2014/12/15
作成者：八鍬寛之

（画面概要）

セット商品の一覧画面

・行ダブルクリック：セット商品の詳細画面に遷移する。
・新規登録ボタン押下：セット商品の新規登録画面に遷移する。

（注意・補足）

-->

	<SCRIPT type="text/javascript">

	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	};

	function goDetailSetItem(value){

		document.getElementById('sysItemId').value = value;
		goTransaction('detailSetItem.do');
	}

	</SCRIPT>
	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />

	<html:form action="/initSetItemList">
	<nested:hidden property="alertType" styleId="alertType" />
	<h4 class="heading">セット商品一覧</h4>

	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistrySetItem.do');">新規登録</a>
	</div>

	<table id="mstTable" class="list">
		<tr>
			<th>品番</th>
			<th>商品名</th>
		</tr>

		<html:hidden property="sysItemId" styleId="sysItemId" />

		<nested:iterate property="setItemList" indexId="idx">
		<tr ondblclick="goDetailSetItem(<nested:write  property="sysItemId"/>);">
			<td><nested:write  property="itemCode"/></td>
			<td><nested:write  property="itemNm"/></td>
		</tr>
		</nested:iterate>
	</table>




	</html:form>
</html:html>