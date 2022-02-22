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
		<html:form action="/updateUser">
		<nested:notEqual value="0" property="userDTO.sysUserId">
		<h4 class="heading">ユーザー情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="userDTO.sysUserId">
		<h4 class="heading">ユーザー情報登録</h4>
		</nested:equal>

		<nested:hidden property="alertType" styleId="alertType"></nested:hidden>
		<html:errors/>
			<nested:nest property="userDTO">
				<table id="mstTable">
					<tr>
						<th>
							ユーザーID
						</th>
						<td>
							<nested:text property="loginCd" maxlength="8" styleClass="alphanumeric" />
						</td>
					</tr>
					<tr>
						<th>
							パスワード
						</th>
						<td>
							<nested:password property="password"  maxlength="8" styleClass="alphanumeric" />
						</td>
					</tr>
					<tr>
						<th>
							社員名(漢字・苗字)
						</th>
						<td>
							<nested:text property="userFamilyNmKanji"  maxlength="30" />
						</td>
					</tr>
					<tr>
						<th>
							社員名(漢字・名前)
						</th>
						<td>
							<nested:text property="userFirstNmKanji"  maxlength="30" />
						</td>
					</tr>
					<tr>
						<th>
							社員名(ｶﾅ・苗字)
						</th>
						<td>
							<nested:text property="userFamilyNmKana"  maxlength="30" />
						</td>
					</tr>
					<tr>
						<th>
							社員名(ｶﾅ・名前)
						</th>
						<td>
							<nested:text property="userFirstNmKana"  maxlength="30" />
						</td>
					</tr>
					<tr>
						<th>
							担当者番号
						</th>
						<td>
							<nested:text property="responsibleNo"  maxlength="5" />
						</td>
					</tr>
				</table>

			</nested:nest>
			<nested:notEqual value="0" property="userDTO.sysUserId">
			<div class="update_detailUserButton">
<!-- 				<input type="image" src="./img/delete.gif" class="btn_submit" onmouseover="this .src='./img/delete_over.gif'" onmouseout="this .src='./img/delete.gif'"onclick="goTransaction('deleteUser.do');" align="middle"/> -->
<!-- 				<input type="image" src="./img/update.gif" class="btn_submit" onmouseover="this .src='./img/update_over.gif'" onmouseout="this .src='./img/update.gif'"onclick="goTransaction('updateUser.do');" align="middle"/> -->
				<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateUser.do');">更新</a>
				<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteUser.do');">削除</a>
			</div>
			</nested:notEqual>

			<nested:equal value="0" property="userDTO.sysUserId">
			<div class="registry_detailUserButton">
<!-- 				<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goTransaction('registryUser.do');" align="middle"/> -->
				<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryUser.do');">登録</a>
			</div>
			</nested:equal>

		</html:form>

	</body>
</html:html>