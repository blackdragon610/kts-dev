<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />

<!--
【ユーザー一覧画面】
ファイル名：userList.jsp
作成日：2014/12/15
作成者：八鍬寛之

（画面概要）

ユーザーの一覧画面

・行ダブルクリック：ユーザーの詳細画面に遷移する。
・新規登録ボタン押下：ユーザーの新規登録画面に遷移する。
追記：
・海外閲覧権限および法人間請求書権限を持っている人は色を変えてわかりやすくした。(2017/08/24)

（注意・補足）

-->

	<script>
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	};
	function goDetailUser(value){

		document.getElementById('sysUserId').value = value;
		goTransaction('detailUser.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailUser">
	<nested:hidden property="alertType" styleId="alertType"/>
	<h4 class="heading">ユーザー一覧</h4>

	<input type="hidden" name="sysUserId"  id="sysUserId" />
	<table id="mstTable" class="list">
		<tr>
			<th>ユーザーID</th>
			<th>ユーザー名</th>
			<th>ユーザー名(カタカナ)</th>
			<th>担当者番号</th>
		</tr>
		<nested:iterate property="userList" indexId="idx">
			<tr ondblclick="goDetailUser(<nested:write  property="sysUserId"/>);">
				<nested:equal property="btobBillAuth" value="1">
					<nested:equal property="overseasInfoAuth" value="1">
						<td class="authUser"><nested:write  property="loginCd"/></td>
						<td class="authUser"><nested:write  property="userFamilyNmKanji"/><nested:write  property="userFirstNmKanji"/></td>
						<td class="authUser"><nested:write  property="userFamilyNmKana"/><nested:write  property="userFirstNmKana"/></td>
						<td class="authUser"><nested:write  property="responsibleNo"/></td>
					</nested:equal>
				</nested:equal>
				<nested:notEqual property="btobBillAuth" value="1">
					<nested:notEqual property="overseasInfoAuth" value="1">
						<td><nested:write  property="loginCd"/></td>
						<td><nested:write  property="userFamilyNmKanji"/><nested:write  property="userFirstNmKanji"/></td>
						<td><nested:write  property="userFamilyNmKana"/><nested:write  property="userFirstNmKana"/></td>
						<td><nested:write  property="responsibleNo"/></td>
					</nested:notEqual>
				</nested:notEqual>
			</tr>
		</nested:iterate>
	</table>

	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryUser.do');">新規登録</a>
	</div>

	</html:form>
</html:html>