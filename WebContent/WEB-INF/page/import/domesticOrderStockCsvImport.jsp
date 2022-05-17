<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/import.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>

<!--
【国内注文入荷予定日データ取込画面】
ファイル名：domesticOrderStockCsvImport.jsp.jsp
作成日：2022/5/12
作成者：xxx

（画面概要）

助ネコのCSV(国内注文データ)を取り込む画面。
送り状番号は付与されていないのが前提。

・CSV選択ボタン押下：助ネコのCSVを選択する。

・インポートボタン押下：引っ張ってきたCSVファイルを売上データとして取り込む

【画面上部】
プルダウンにて選択された法人を付与し、1つのみ取込可能

【画面下部】
ファイル名から法人を判別し、5つまで同時に取り込む

（注意・補足）

-->

	<script>
	$(document).ready(function(){
		$(".overlay").css("display", "none");
		var errStr = "";
		$("#errorArea").children("p").each(function() {
			errStr = errStr + $(this).html() + "\n";
		});
		if (errStr != '') {
			alert("登録か完了しました。");
		}
	 });
$(function () {

	//メッセージエリア表示設定
	if(!$("#registryDto.message").val()) {
		if($("#messageFlg").val() == "0") {
			$("#messageArea").addClass("registrySuccess");
		}
		if($("#messageFlg").val() == "1"){
			$("#messageArea").addClass("registryFailure");
		}
		$("#messageArea").fadeOut(4000);
	}

	$(".importButton").click(function () {

		if ($("#filupload_01").val() == "" || $("#select_box").val() == "0") {

			alert("法人とファイルを選択してください。");
			return;
		}

		$(".overlay").css("display", "block");

		goTransaction("domesticOrderStockCsvImport.do");
	});

});

function filupload_01click() {

	document.getElementById("filupload_01").click();
}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initDomesticOrderStockCsvImport" enctype="multipart/form-data">

		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>

	<html:hidden property="alertType" styleId="alertType"/>
	<html:hidden property="trueCount" styleId="trueCount"/>

	<h4 class="heading">国内注文入荷予定日データ取込</h4>


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
		</nested:nest>

		<nested:notEmpty property="csvErrorList">
			<nested:iterate property="csvErrorList">
				<nested:notEmpty property="fileName">
					<p class="fileName"><nested:write property="fileName"/></p>
				<br/>
				</nested:notEmpty>
				<nested:notEmpty property="errorMessageList">
					<nested:iterate property="errorMessageList">
						<p class="errorMessage"><nested:write property="errorMessage"/></p>
					</nested:iterate>
				</nested:notEmpty>
			</nested:iterate>
		</nested:notEmpty>
	</div>


	<div id="import_div">

		<p class="import_elements">
		<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/>
			<input type="text" id="txtfilename_01" class="input_fileupload"  size="25" disabled="disabled"/>
			<input type="button" id="btnbrowse_01" class="btn_fileupload_green" onmouseover="this.className='btn_fileupload_over_green'" onmouseout="this.className='btn_fileupload_green'" onclick="filupload_01.click()" value=""/>
		</p>
		<div class="buttonPositionTop">
			<input type="button" class="btn_import_green importButton" onmouseover="this.className='btn_import_over_green'" onmouseout="this.className='btn_import_green'"/>
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