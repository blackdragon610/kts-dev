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
	}
	</SCRIPT>
	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<html:form action="/registryAccount">
		<nested:notEqual value="0" property="accountDTO.sysAccountId">
		<h4 class="heading">口座情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="accountDTO.sysAccountId">
		<h4 class="heading">口座情報登録</h4>
		</nested:equal>
		<nested:hidden property="alertType" styleId="alertType" />
		<nested:nest property="accountDTO">
			<table id="mstTable">
				<nested:notEqual value="0" property="sysAccountId">
					<tr>
						<th>口座ID</th>
						<td><nested:write property="sysAccountId" /></td>
					</tr>
				</nested:notEqual>
				<tr>
					<th>
						法人名
					</th>
					<td><nested:select property="sysCorporationId">
						<html:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
<!-- 						<option selected>KTS</option> -->
<!-- 						<option>車楽院</option> -->
<!-- 						<option>ラルグスリテール法人営業部</option> -->
					</nested:select></td>
				</tr>
				<tr>
					<th>
						口座名
					</th>
					<td>
						<nested:text property="accountNm" />
					</td>
				</tr>
				<tr>
					<th>
						銀行名
					</th>
					<td>
						<nested:text property="bankNm" />
					</td>
				</tr>
				<tr>
					<th>
						支店名
					</th>
					<td>
						<nested:text property="branchNm" />
					</td>
				</tr>
				<tr>
					<th>
						種目
					</th>
					<td><nested:select property="accountType">
						<html:option value="1">普通</html:option>
						<html:option value="2">当座</html:option>
						<html:option value="3">貯蓄</html:option>
						<html:option value="4">その他</html:option>
					</nested:select></td>
				</tr>
				<tr>
					<th>
						口座番号
					</th>
					<td>
						<nested:text property="accountNumber" />
					</td>
				</tr>
				<tr>
					<th>
						名義
					</th>
					<td>
						<nested:text property="accountHolder" />
					</td>
				</tr>
				<tr>
					<th>
						優先表示
					</th>
					<td>
						<input type="checkbox" name="accountDTO.priorFlag" <nested:equal property="priorFlag" value="1">checked</nested:equal> />
						<nested:hidden property="priorFlag" value="unchecked"/>
					</td>
				</tr>
			</table>

			<nested:equal value="0" property="sysAccountId">
				<div class="buttonArea">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryAccount.do');">登録</a>
				</div>
			</nested:equal>
			<nested:notEqual value="0" property="sysAccountId">
				<div class="buttonArea">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateAccount.do');">更新</a>
					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteAccount.do');">削除</a>
				</div>
			</nested:notEqual>

		</nested:nest>
		</html:form>

	</body>
</html:html>