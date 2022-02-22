<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />

<!--
【販売チャネルマスタ一覧画面】
ファイル名：channelList.jsp
作成日：2014/12/12
作成者：八鍬寛之

（画面概要）

商品の売上ルートを表す販売チャネルの一覧画面

・行ダブルクリック：販売チャネルの詳細画面に遷移する。
・新規登録ボタン押下：販売チャネルの新規登録画面に遷移する。

（注意・補足）

-->

	<script>
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	}

	function goDetailChannel(value){

		document.getElementById('sysChannelId').value = value;
		goTransaction('detailChannel.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailChannel">
	<nested:hidden property="alertType" styleId="alertType"/>

	<h4 class="heading">販売チャネル一覧</h4>
	<input type="hidden" name="sysChannelId"  id="sysChannelId" />
	<table id="mstTable" class="list">
		<tr>
			<th>ID</th>
			<th>販売チャネル名</th>
			<th>URL</th>
		</tr>
		<nested:iterate property="channelList" indexId="idx">
		<tr ondblclick="goDetailChannel(<nested:write  property="sysChannelId"/>);">
			<td><nested:write  property="sysChannelId"/></td>
			<td><nested:write  property="channelNm"/></td>
			<td><a href="<nested:write  property="url"/>" target="_blank"><nested:write  property="url"/></a></td>
		</tr>
		</nested:iterate>
	</table>

	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryChannel.do');">新規登録</a>
	</div>


	</html:form>
</html:html>