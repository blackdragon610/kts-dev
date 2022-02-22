<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/maker.css" type="text/css" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/maker.css" type="text/css" />
	<link rel="stylesheet" href="./css/common.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【メーカー一覧画面】
ファイル名：makerList.jsp
作成日：2016/12/9
作成者：齋藤 優太

（画面概要）

メーカーの一覧画面

・行ダブルクリック：メーカーの詳細画面に遷移する。
・新規登録ボタン押下：メーカーの新規登録画面に遷移する。

（注意・補足）

-->

	<script type="text/javascript">

	$(function () {
		//メッセージ表示時間指定
		if ($(".registryMessage") != 0) {
			$("#massageArea").fadeOut(2800);
		}
	});

	//メーカー情報ダブルクリック時
	function goDetailMaker(value){
		$("#sysMakerId").val(value);
		goTransaction('detailMaker.do');
	}

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initMakerList">

	<nested:nest property="registryDto">
		<nested:notEmpty property="registryMessage">
			<div id="massageArea">
				<p class="registryMessage" style="text-align: leght;"><nested:write property="registryMessage"/></p>
			</div>
		</nested:notEmpty>
	</nested:nest>

	<h4 class="heading">メーカー一覧</h4>

	<html:hidden property="sysMakerId"  styleId="sysMakerId" />
	<table id="mstTable" class="list">
		<tr>
			<th class="w90">メーカーID</th>
			<th class="w200">メーカー名</th>
			<th class="w200">メーカー名カナ</th>
			<th class="w200">担当者名</th>
		</tr>
		<nested:iterate property="makerList" indexId="idx">
			<tr ondblclick="goDetailMaker(<nested:write  property="sysMakerId"/>);">
				<td><nested:write property="sysMakerId" /></td>
				<td><nested:write property="makerNm" /></td>
				<td><nested:write property="makerNmKana" /></td>
				<td><nested:write property="contactPersonNm" /></td>
			</tr>
		</nested:iterate>
	</table>

	<div class="footerButtonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryMaker.do');">新規登録</a>
	</div>

	</html:form>
</html:html>