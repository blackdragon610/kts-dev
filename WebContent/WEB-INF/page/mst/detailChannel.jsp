<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />

<!--
【販売チャネル詳細画面】
ファイル名：detailChannel.jsp
作成日：2014/12/15
作成者：八鍬寛之

（画面概要）

販売チャネルの詳細画面

・更新ボタン押下：更新処理を行う。
・削除ボタン押下：販売チャネルを削除する。

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
		<html:form action="/updateChannel">
		<nested:notEqual value="0" property="channelDTO.sysChannelId">
		<h4 class="heading">チャネル情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="channelDTO.sysChannelId">
		<h4 class="heading">チャネル情報登録</h4>
		</nested:equal>

		<nested:hidden property="alertType" styleId="alertType"></nested:hidden>

			<nested:nest property="channelDTO">
				<table id="mstTable">
					<tr>
						<th>
							チャネル名
						</th>
						<td>
							<nested:text property="channelNm" maxlength="50" />
						</td>
					</tr>
					<tr>
						<th>
							URL
						</th>
						<td>
							<nested:text property="url" maxlength="200" />
						</td>
					</tr>
				</table>

			</nested:nest>
			<div class="update_detailChannelButton">
			<nested:notEqual value="0" property="channelDTO.sysChannelId">
				<div class="update_detailUserButton">
<!-- 					<input type="image" src="./img/delete.gif" class="btn_submit" onmouseover="this .src='./img/delete_over.gif'" onmouseout="this .src='./img/delete.gif'"onclick="goTransaction('deleteChannel.do');" align="middle"/> -->
<!-- 					<input type="image" src="./img/update.gif" class="btn_submit" onmouseover="this .src='./img/update_over.gif'" onmouseout="this .src='./img/update.gif'"onclick="goTransaction('updateChannel.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateChannel.do');">更新</a>
					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteChannel.do');">削除</a>
				</div>
			</nested:notEqual>
			<nested:equal value="0" property="channelDTO.sysChannelId">
				<div class="registry_detailChannelButton">
<!-- 					<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goTransaction('registryChannel.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryChannel.do');">登録</a>
				</div>
			</nested:equal>
			</div>
		</html:form>

	</body>
</html:html>