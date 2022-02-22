<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

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
	}

	function goDetailAccount(value){

		$("#sysAccountId").val(value);
		goTransaction('detailAccount.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailAccount">
	<nested:hidden property="alertType" styleId="alertType" />

	<h4 class="heading">口座一覧</h4>
	<input type="hidden" name="sysAccountId"  id="sysAccountId" />
	<table id="mstTable" class="list">
		<tr>
			<th>法人名</th>
			<th>口座名</th>
		</tr>
		<nested:iterate property="accountList" indexId="idx">
			<tr ondblclick="goDetailAccount(<nested:write  property="sysAccountId"/>);">
				<td><nested:write property="corporationNm" /></td>
				<td><nested:write property="accountNm" /></td>
			</tr>
		</nested:iterate>
	</table>

	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryAccount.do');">新規登録</a>
	</div>

	</html:form>
</html:html>