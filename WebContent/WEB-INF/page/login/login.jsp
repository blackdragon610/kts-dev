<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>

<link rel="stylesheet" href="./css/login.css" type="text/css" />

	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>

<script type="text/javascript">
$(function () {

	$("#loginCd").focus();
});
</script>
</head>

<body>
	<div id="container">

		<center>
			<div id="contents">
			<div class="errorsArea">
				<html:errors/>
			</div>
			<br>
			<nested:form action="/login">

				<table class="loginTable">
					<tr>
						<td style="font-size: 70px;">α</td>
					</tr>
					<tr>
						<td>version <nested:write property="version"/></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<th>ログイン</th> -->
<!-- 					</tr> -->
					<tr>
						<td>ログインするにはユーザIDとパスワードを入力して、 &quot;ログイン&quot;をクリックして下さい。</td>
					</tr>
					<tr>
						<td>ユーザーID：&nbsp;<nested:text styleId="loginCd" property="loginCd" maxlength="8" value="test"/></td>
					</tr>
					<tr>
						<td style="padding-left: 10px;">パスワード：&nbsp;<nested:password property="password" maxlength="8" value="test"/>
						</td>
					</tr>
					<tr>
						<td>
							<label><nested:checkbox styleId="check" property="saveLoginCdFlg" />次回からユーザIDの入力を省略します</label>
						</td>
					</tr>
					<tr>
						<td><input type="submit" name="submit" value="ログイン"></td>
					</tr>
				</table>
			</nested:form>
			</div>
			<br>
		</center>
	</div>
</body>
</html>