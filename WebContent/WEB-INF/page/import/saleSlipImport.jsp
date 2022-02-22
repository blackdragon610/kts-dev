<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/import.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>

<!--
【売上データインポート画面】
ファイル名：saleSlipImport.jsp
作成日：2021/06/07
作成者：金城

（画面概要）

・EXCEL選択ボタン押下：エクセルファイルを選択する。

・インポートボタン押下：引っ張ってきたエクセルファイルを売上データとして取り込む

（注意・補足）

-->

	<script type="text/javascript">

	$(document).ready(function(){
		$(".overlay").css("display", "none");
	 });

	$(function() {
		//アラート
		if ($("#alertType").val() != '' && $("#alertType").val() != '0') {
			actAlert($("#alertType").val());
			$("#alertType").val("0");
		}

		$(".listImportButton").click(function () {

			if ($("#filupload_01").val() == "") {

				alert("ファイルを選択してください。");
				return;
			}

			$(".overlay").css("display", "block");

			goTransaction("saleSlipExcelImport.do");
		});
	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/saleSlipExcelImport" enctype="multipart/form-data">

	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="heading">売上データ</br>インポート</h4>

	<div id="import_div">
		<div id="errorArea">
			<html:errors/>
		</div>

		<br/>

			<p class="import_elements">
			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/>
				<input type="text" id="txtfilename_01" class="input_fileupload"  size="25" disabled="disabled"/>
				<input type="button" id="btnbrowse_01" class="btn_excelUpload" onmouseover="this.className='btn_excelUpload_over'" onmouseout="this.className='btn_excelUpload'" onclick="filupload_01.click()" value=""/>
			</p>
		<br/>
		<p class="excel_import_submit">
			<input type="button" class="btn_import listImportButton" onmouseover="this.className='btn_import_over'" onmouseout="this.className='btn_import'"/>

		</p>
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