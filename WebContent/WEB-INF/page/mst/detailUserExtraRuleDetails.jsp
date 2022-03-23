<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【ユーザーマスタ詳細画面】
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
	};
	</SCRIPT>
	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<html:form action="/updateUserExtrRuleDetails">
		<h4 class="heading">詳細画面</h4>

		<nested:hidden property="alertType" styleId="alertType"></nested:hidden>
		<html:errors/>
			<nested:nest property="userDTO">
				<table id="mstTable">
					<tr>
						<th>名称</th>
						<th>権限</th>
					</tr>
					<<%-- nested:iterate property="mstRulesList" indexId="idx">
						<tr>
							<td>
								<nested:write property="listName" />
							</td>
							<td>
								<nested:equal property="isvisible" value="1">&#9898;</nested:equal>
								<nested:notEqual property="isvisible" value="1">&#9932;</nested:notEqual>
							</td>
						</tr>
					</nested:iterate> --%>
				</table>

			</nested:nest>
			<nested:notEqual value="0" property="userDTO.sysUserId">
			<div class="update_detailUserButton">
				<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateUser.do');">更新</a>
				<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteUser.do');">削除</a>
			</div>
			</nested:notEqual>

			<nested:equal value="0" property="userDTO.sysUserId">
			<div class="registry_detailUserButton">
				<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryUser.do');">登録</a>
			</div>
			</nested:equal>

		</html:form>

	</body>
</html:html>