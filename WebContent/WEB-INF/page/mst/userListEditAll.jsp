<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />

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
	
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/editAllUserList">
	<nested:hidden property="alertType" styleId="alertType"/>
	<h4 class="heading">ユーザー一覧 > 一括編集</h4>

	<input type="hidden" name="sysUserId"  id="sysUserId" />
	
	<table id="mstTable" class="user-list">
		<tr>
			<th>ユーザー名</th>
			<th>マスタ</th>
			<th>法人間請求権限</th>
			<th>海外情報閲覧権限</th>
			<nested:iterate property="ruleList" indexId="idx">
				<th><nested:write  property="ruleName"/></th>
			</nested:iterate>
		</tr>
		<nested:iterate property="userList" indexId="idx">
			<tr>
				<td ondblclick="goDetailUser(<nested:write  property="sysUserId"/>);" style="cursor:pointer">
					<nested:write  property="userFamilyNmKanji"/><nested:write  property="userFirstNmKanji"/>
				</td>
				<td>
					<nested:checkbox property="overseasInfoAuth" value="true"></nested:checkbox>
				</td>
				<td>
					<nested:checkbox property="btobBillAuth" value="${btobBillAuth}"></nested:checkbox>
				</td>
				<td>
					<nested:checkbox property="overseasInfoAuth" value="${overseasInfoAuth}"></nested:checkbox>
				</td>
				<nested:iterate property="mstRulesList">
					<td>
						<nested:checkbox property="isvisible" value="${isvisible}"></nested:checkbox>
					</td>
				</nested:iterate>
			</tr>
		</nested:iterate>
	</table>
	<div class="buttonArea">
			<ul style="position: relative;">
			<li class="footer_button">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateAllUserList.do');">登録</a>
				</li>
			</ul>
		</div>

	</html:form>
</html:html>