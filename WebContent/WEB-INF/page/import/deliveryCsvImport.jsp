<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/import.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>

<!--
【配送データ取込画面】
ファイル名：deliveryCsvImport.jsp
作成日：2014/09/29
作成者：八鍬寛之

（画面概要）

助ネコのCSV(配送データ)を取り込む画面。
送り状番号が付与されており、取り込む際にラジオボタンで
同じ受注番号のデータを上書きするか送り状番号のみ付与するかを選べる。


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
	 });
// 	function filupload_01click() {
// 		document.getElementById("filupload_01").click();
// 	}
$(function () {
	numActAlert($("#alertType").val(), $("#trueCount"));
	$("#alertType").val(0);
	$("#trueCount").val(0);

	$(".importButton").click(function () {

		if ($("#filupload_01").val() == "") {

			alert("ファイルを選択してください。");
			return;
		}
/* 		if ($("#filupload_01").val() == "" || 
				($("#select_box").val() == "0" && $("#deliveryCompanyId").val() == "0" )) {

			alert("法人とファイルを選択してください。");
			return;
		}
*/
		//チェックされている値取得
		var radio =$("input:radio[name='deliveryRadio']:checked").val();

		if (radio == "" ||radio == null) {

			alert("「データを全て上書き」か「送り状番号のみ付与」を選択してください");
		}

		$(".overlay").css("display", "block");

 		//伝票上書
		if (radio == "save") {

			goTransaction("saveDeliveryCsvImport.do");

 		//配送番号のみ付与
		} else if (radio == "addSlipNo") {
			goTransaction("addSlipNoDeliveryCsvImport.do");
		}

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
					alert("ListNO同じファイルある");
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

		//チェックされている値取得
		var radio =$("input:radio[name='deliveryRadio']:checked").val();

		if (radio == "" ||radio == null) {

			alert("「データを全て上書き」か「送り状番号のみ付与」を選択してください");
		}

		$(".overlay").css("display", "block");
		//伝票上書
		if (radio == "save") {

			goTransaction("saveDeliveryCsvImportList.do");

		//配送番号のみ付与
		} else if (radio == "addSlipNo") {

			goTransaction("addSlipNoDeliveryCsvImportList.do");
		}
// 		goTransaction("saveDeliveryCsvImportList.do");
// 		alert($("#txtfilenameList_"+ i).val());
	});

});
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initDeliveryCsvImport" enctype="multipart/form-data">

	<html:hidden property="alertType" styleId="alertType"/>
	<html:hidden property="trueCount" styleId="trueCount"/>

	<h4 class="heading">配送データ取込</h4>

 	<table class="copyRadio">
		<tr>
			<td><label><input type="radio" name="deliveryRadio" value="save" checked/>データを全て上書き</label></td>
			<td><label><input type="radio" name="deliveryRadio" value="addSlipNo" />送り状番号のみ付与</label></td>
		</tr>
	</table>
	<div id="errorArea">
		<nested:nest property="csvErrorDTO">
			<nested:notEmpty property="fileName">
<%-- 			<nested:notEmpty property="errorMessageList"> --%>
				<p class="fileName"><nested:write property="fileName"/></p>
			<br/>
			</nested:notEmpty>
			<nested:notEmpty property="errorMessageList">
				<nested:iterate property="errorMessageList">
					<p class="errorMessage"><nested:write property="errorMessage"/></p>
				</nested:iterate>
			</nested:notEmpty>
<%-- 			</nested:notEmpty> --%>
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


<%--  		<p class="import_message">※助ネコのフォーマット以外のCSVは取り込まないようにしてください。</p>
			<p class="import_message">(システムエラーになる可能性があります。)</p>
		<br/>
			<p class="import_elements"><nested:select property="corporationId" styleId="select_box">
			<html:option value="0">　　　　　法人を選択してください　　　　　</html:option>
							<nested:optionsCollection property="corporationList" label="corporationNm" value="sysCorporationId" />
							</nested:select></p>
 --%>
<%-- 			<!-- Added by Wahaha -->
			<p class="import_message">※助ネコをご利用でない場合は、配送会社名をご選択ください</p> 
			<p class="import_elements">
				<nested:select property="deliveryCompanyId" styleId="deliveryCompanyId">
					<html:option value="0">配送会社名</html:option>
					<nested:optionsCollection property="deliveryCompanyList" label="companyName" value="companyId" />
				</nested:select>
			</p>	
 --%>
			<p class="import_elements">
			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/>
				<input type="text" id="txtfilename_01" class="input_fileupload"  size="25" disabled="disabled"/>
				<input type="button" id="btnbrowse_01" class="fileUpload_red" onmouseover="this.className='fileUpload_red_hover'" onmouseout="this.className='fileUpload_red'" onclick="filupload_01.click()" value=""/>
<!-- 			<input type="button" id="btnbrowse_01" class="btn_fileupload" onmouseover="this.className='btn_fileupload_over'" onmouseout="this.className='btn_fileupload'" onclick="filupload_01click()" value=""/> -->
			</p>
<!-- 			<div class="buttonPositionTop"><a href="javascript:void(0);"  class="button_main importButton" >インポート</a></div> -->
			<div class="buttonPositionTop">
				<input type="button" class="import_red importButton" onmouseover="this.className='import_red_hover'" onmouseout="this.className='import_red'"/>
			</div>

<!-- 		<fieldset class="contentstable"> -->

<%-- 			<h3 class="multi_import">一括取り込み</h3>
			<p class="import_message">※「OS_【法人名半角英数字】_【日付】.csv」このファイル名以外のものは取り込まないようにしてください。</p>
			<br/>

			<nested:iterate property="csvInputList" indexId="idx">
				<p class="import_elements">
					<nested:file property="fileUp" styleId="filuploadList_${idx}" styleClass="dn" onchange="txtfilenameList_${idx}.value = this.value;"/>
					<input type="text" id="txtfilenameList_${idx}" name="csvInputList${idx}.csvFileName" class="input_fileupload txtfilenameList" size="25" disabled="disabled" />
					<input type="button" id="btnbrowseList_${idx}" class="fileUpload_red" onmouseover="this.className='fileUpload_red_hover'"
							onmouseout="this.className='fileUpload_red'" onclick="filuploadList_${idx}.click()" value=""/>
				</p>
			</nested:iterate>
 --%>
<!-- 			<p class="import_elements_multi"> -->
<%-- 			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/> --%>
<!-- 				<input type="text" id="txtfilename_01" class="input_fileupload_multi"  size="25" disabled="disabled"/> -->
<!-- 				<input type="button" id="btnbrowse_01" class="btn_fileupload_multi" onmouseover="this.className='btn_fileupload_over'" onmouseout="this.className='btn_fileupload'" onclick="filupload_01.click()" value=""/> -->
<!-- 			</p> -->
<!-- 			<p class="import_elements_multi"> -->
<%-- 			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/> --%>
<!-- 				<input type="text" id="txtfilename_01" class="input_fileupload_multi"  size="25" disabled="disabled"/> -->
<!-- 				<input type="button" id="btnbrowse_01" class="btn_fileupload_multi" onmouseover="this.className='btn_fileupload_over'" onmouseout="this.className='btn_fileupload'" onclick="filupload_01.click()" value=""/> -->
<!-- 			</p> -->
<!-- 			<p class="import_elements_multi"> -->
<%-- 			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/> --%>
<!-- 				<input type="text" id="txtfilename_01" class="input_fileupload_multi"  size="25" disabled="disabled"/> -->
<!-- 				<input type="button" id="btnbrowse_01" class="btn_fileupload_multi" onmouseover="this.className='btn_fileupload_over'" onmouseout="this.className='btn_fileupload'" onclick="filupload_01.click()" value=""/> -->
<!-- 			</p> -->
<!-- 			<p class="import_elements_multi"> -->
<%-- 			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/> --%>
<!-- 				<input type="text" id="txtfilename_01" class="input_fileupload_multi"  size="25" disabled="disabled"/> -->
<!-- 				<input type="button" id="btnbrowse_01" class="btn_fileupload_multi" onmouseover="this.className='btn_fileupload_over'" onmouseout="this.className='btn_fileupload'" onclick="filupload_01.click()" value=""/> -->
<!-- 			</p> -->
<!-- 			<p class="import_elements_multi"> -->
<%-- 			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/> --%>
<!-- 				<input type="text" id="txtfilename_01" class="input_fileupload_multi"  size="25" disabled="disabled"/> -->
<!-- 				<input type="button" id="btnbrowse_01" class="btn_fileupload_multi" onmouseover="this.className='btn_fileupload_over'" onmouseout="this.className='btn_fileupload'" onclick="filupload_01.click()" value=""/> -->
<!-- 			</p> -->


<!-- 		</fieldset> -->

<!-- 		<br/>
		<div class="buttonPositionBottom">
			<input type="button" class="import_red listImportButton" onmouseover="this.className='import_red_hover'" onmouseout="this.className='import_red'"/>
		</div>
 --><!-- 		<p class="import_submit"><input type="image" src="./img/import.gif" class="btn_submit" onmouseover="this .src='./img/import_over.gif'" onmouseout="this.src='./img/import.gif'" align="middle"/></p> -->
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