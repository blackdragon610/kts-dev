<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />

<!--
【法人マスタ一覧画面】
ファイル名：corporationList.jsp
作成日：2014/12/12
作成者：八鍬寛之

（画面概要）

法人の一覧画面

・行ダブルクリック：法人の詳細画面に遷移する。
・新規登録ボタン押下：法人の新規登録画面に遷移する。

（注意・補足）

-->

	<script>

	function goDetailCorporation(value){

		document.getElementById('sysCorporationId').value = value;
		goTransaction('detailCorporation.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailCorporation">

	<h4 class="heading">会社一覧</h4>
	<input type="hidden" name="sysCorporationId"  id="sysCorporationId" />
	<table id="mstTable" class="list">
		<tr>
			<th>ID</th>
			<th>会社名</th>
			<th>郵便番号</th>
			<th>住所</th>
			<th>電話番号</th>
			<th>FAX番号</th>
		</tr>
		<nested:iterate property="corporationList" indexId="idx">
		<tr ondblclick="goDetailCorporation(<nested:write  property="sysCorporationId"/>);">
			<td><nested:write  property="sysCorporationId"/></td>
			<td><nested:write  property="corporationNm"/></td>
			<td><nested:write  property="zip"/></td>
			<td><nested:write  property="address"/></td>
			<td><nested:write  property="telNo"/></td>
			<td><nested:write  property="faxNo"/></td>
		</tr>
		</nested:iterate>
	</table>

<!-- 	<div class="buttonArea"> -->
<!-- 		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryCorporation.do');">新規登録</a> -->
<!-- 	</div> -->

	</html:form>
</html:html>