<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/import.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>

<!--
【キープ取込画面】
ファイル名：csvKeepImport.jsp
作成日：2017/08/04
作成者：須田将規

（画面概要）

助ネコのCSV(受注番号、個数、備考(受注ルート、注文者(姓)、注文者(名)))を取り込む画面。
送り状番号は付与されていないのが前提。

・CSV選択ボタン押下：助ネコのCSVを選択する。

・インポートボタン押下：引っ張ってきたCSVファイルを取り込み、
	商品のキープの受注番号、個数、備考(受注ルート、注文者(姓)、注文者(名))にデータを格納する。

【画面上部】
	1つのみ取込可能

【画面下部】
	5つまで同時に取り込む

（注意・補足）

-->

	<script>
	$(document).ready(function(){
		$(".overlay").css("display", "none");
	 });
$(function () {

	numActAlert($("#alertType").val(), $("#trueCount"));
	$("#alertType").val(0);
	$("#trueCount").val(0);

	$(".importButton").click(function () {

		if ($("#filupload_01").val() == "" || $("#select_box").val() == "0") {

			alert("ファイルを選択してください。");
			return;
		}

		$(".overlay").css("display", "block");

		goTransaction("keepRemoveCsvImport.do");
	});

	$(".listImportButton").click(function () {

// 		alert($(".txtfilenameList").val());

		var i = 0;
		var j = 0;

		//少し長いけど、、、
		//インポートボタンを押せない条件

		//すべて空の場合
		if ($("#txtfilenameList_"+ i++).val() == "" && $("#txtfilenameList_"+ i++).val() == ""
					&& $("#txtfilenameList_"+ i++).val() == "" && $("#txtfilenameList_"+ i++).val() == ""
						&& $("#txtfilenameList_"+ i++).val() == "") {

			alert("ファイルを選択してください。");
			return;
		}

		//同ファイル名が一つでも存在する場合
		for (i = 0; i < $(".txtfilenameList").length; i++) {
			if ($("#txtfilenameList_"+ i).val() == "") {
				continue;
			}
			for (j = i + 1; j < $(".txtfilenameList").length; j++) {

				if ($("#txtfilenameList_"+ i).val() == $("#txtfilenameList_"+ j).val()) {
					alert("同一のファイルが選択されています。");
					return;
				}
			}
		}

// 		if ($("#txtfilenameList_"+ i++).val() == "" && $("#txtfilenameList_"+ i++).val() == ""
// 			&& $("#txtfilenameList_"+ i++).val() == "" && $("#txtfilenameList_"+ i++).val() == ""
// 			&& $("#txtfilenameList_"+ i++).val() == "") {
// 			alert("NOList");
// 			return;
// 		}

		$(".overlay").css("display", "block");

		goTransaction("keepCsvListImport.do");
// 		alert($("#txtfilenameList_"+ i).val());
	});

});

function filupload_01click() {

	document.getElementById("filupload_01").click();
}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/csvImport" enctype="multipart/form-data">

	<html:hidden property="alertType" styleId="alertType"/>
	<html:hidden property="trueCount" styleId="trueCount"/>

	<h4 class="heading">キープ取消</h4>


	<div id="errorArea">
		<nested:nest property="csvErrorDTO">
			<nested:notEmpty property="fileName">
				<p class="fileName"><nested:write property="fileName"/></p>
			<br/>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageList">
				<nested:iterate property="errorMessageList">
						<p class="errorMessage"><nested:write property="errorMessage"/></p>
				</nested:iterate>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageListBlue">
				<nested:iterate property="errorMessageListBlue">
						<p class="errorMessageBlue"><nested:write property="errorMessage"/></p>
				</nested:iterate>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageListGreen">
				<nested:iterate property="errorMessageListGreen">
						<p class="errorMessageGreen"><nested:write property="errorMessage"/></p>
				</nested:iterate>
			</nested:notEmpty>
			<nested:notEmpty property="errrorMessageListYellow">
				<nested:iterate property="errrorMessageListYellow">
						<p class="errorMessageYellow"><nested:write property="errorMessage"/></p>
				</nested:iterate>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageListPurple">
				<nested:iterate property="errorMessageListPurple">
						<p class="errorMessagePurple"><nested:write property="errorMessage"/></p>
				</nested:iterate>
			</nested:notEmpty>

			<nested:notEmpty property="errorMessageBlueMap">
				<nested:iterate  id="map" property="errorMessageBlueMap">
					<p class="errorMessageBlue"><nested:write name="map" property="value"/></p>
				</nested:iterate>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageMap">
				<nested:iterate  id="map" property="errorMessageMap">
					<p class="errorMessage"><nested:write name="map" property="value"/></p>
				</nested:iterate>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageGreenMap">
					<nested:iterate  id="map" property="errorMessageGreenMap">
						<p class="errorMessageGreen"><nested:write name="map" property="value"/></p>
					</nested:iterate>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageYellowMap">
					<nested:iterate  id="map" property="errorMessageYellowMap">
						<p class="errorMessageYellow"><nested:write name="map" property="value"/></p>
					</nested:iterate>
			</nested:notEmpty>
		</nested:nest>

		<nested:notEmpty property="csvErrorList">
			<nested:iterate property="csvErrorList">
				<nested:notEmpty property="fileName">
					<p class="fileName"><nested:write property="fileName"/></p>
				<br />
				</nested:notEmpty>
				<nested:notEmpty property="errorMessageList">
					<nested:iterate property="errorMessageList">
						<p class="errorMessage"><nested:write property="errorMessage"/></p>
					</nested:iterate>
				</nested:notEmpty>
				<nested:notEmpty property="errorMessageMap">
					<nested:iterate  id="map" property="errorMessageMap">
						<p class="errorMessage"><nested:write name="map" property="value"/></p>
					</nested:iterate>
				</nested:notEmpty>
				<nested:notEmpty property="errorMessageBlueMap">
					<nested:iterate  id="map" property="errorMessageBlueMap">
						<p class="errorMessageBlue"><nested:write name="map" property="value"/></p>
					</nested:iterate>
				</nested:notEmpty>
				<nested:notEmpty property="errorMessageGreenMap">
					<nested:iterate  id="map" property="errorMessageGreenMap">
						<p class="errorMessageGreen"><nested:write name="map" property="value"/></p>
					</nested:iterate>
				</nested:notEmpty>
				<nested:notEmpty property="errorMessageYellowMap">
					<nested:iterate  id="map" property="errorMessageYellowMap">
						<p class="errorMessageYellow"><nested:write name="map" property="value"/></p>
					</nested:iterate>
				</nested:notEmpty>
			</nested:iterate>
		</nested:notEmpty>
	</div>


	<div id="import_div">


		<p class="import_message">※助ネコのフォーマット以外のCSVは取り込まないようにしてください。</p>
			<p class="import_message">(システムエラーになる可能性があります。)</p>
		<br/>
			<p class="import_elements">
			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/>
				<input type="text" id="txtfilename_01" class="input_fileupload"  size="25" disabled="disabled"/>
				<input type="button" id="btnbrowse_01" class="fileUpload_red" onmouseover="this.className='fileUpload_red_hover'" onmouseout="this.className='fileUpload_red'" onclick="filupload_01.click()" value=""/>
<!-- 			<input type="button" id="btnbrowse_01" class="btn_fileupload" onmouseover="this.className='btn_fileupload_over'" onmouseout="this.className='btn_fileupload'" onclick="filupload_01click()" value=""/> -->
			</p>
			<div class="buttonPositionTop">
				<input type="button" class="import_red importButton" onmouseover="this.className='import_red_hover'" onmouseout="this.className='import_red'"/>
			</div>

	</div>
	</html:form>

	<div class="overlay">
		<div class="messeage_box">
			<h1>インポート中</h1>
			<BR />
			<p>Now Loading...</p>
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>
</html:html>