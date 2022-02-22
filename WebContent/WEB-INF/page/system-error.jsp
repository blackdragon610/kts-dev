<!DOCTYPE html>

<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>システムエラー</title>
<link href="./css/styles.css" type="text/css"
	media="all" rel="stylesheet">
</head>
<body>
	<table class="err">
		<tr>
			<td>
				<table class="errContent">
					<tr>
						<th width="325px"></th>
						<th><img src="./img/mark_warning03.gif">
						</th>
						<th valign="middle" align="left"><font size="15pt"
							color="#FF0000" width="100px">システムエラー</font></th>
						<th><img src="./img/mark_warning03.gif">
						</th>
						<th width="325px"></th>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="errDetailTable">
					<tr>
						<td align="center" class="tdFont5px">
								大変申し訳ございませんが、システムエラーが起きました。
						</td>
					</tr>
					<tr>
						<td align="center" class="tdFont5px">
								お手数ですが、本画面のキャプチャーを管理者に送ってください。
						</td>
					</tr>
					<tr>
						<td>
							<fieldset class="errDetail">
								<legend>
									<b>エラー原因</b>
								</legend>
								<logic:iterate id="throwable" name="stackTraceInfo" indexId="idx1">
									<logic:notEqual name="idx1" value="0">Caused by:&nbsp;</logic:notEqual>
									<logic:iterate id="stackTrace" name="throwable" indexId="idx2">
										<logic:notEqual name="idx2" value="0">&nbsp;&nbsp;&nbsp;&nbsp;at&nbsp;</logic:notEqual><bean:write name="stackTrace"/><br>
									</logic:iterate>
								</logic:iterate>

							</fieldset>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html:html>