<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/import.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>

<!--
【商品データインポート画面】
ファイル名：excelImport.jsp
作成日：2014/09/29
作成者：八鍬寛之

（画面概要）

商品データの新規登録・更新をエクセルファイルの取り込みで行う。
どちらでインポートするかはラジオボタンで選択する。


・EXCEL選択ボタン押下：エクセルファイルを選択する。

・インポートボタン押下：引っ張ってきたエクセルファイルを商品データとして取り込む

（注意・補足）

-->

	<script type="text/javascript">

	$(document).ready(function(){
		$(".overlay").css("display", "none");
	 });

	$(function() {
		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		$(".listImportButton").click(function () {

			if ($("#filupload_01").val() == "") {

				alert("ファイルを選択してください。");
				return;
			}

			//チェックされている値取得
			var radio =$("input:radio[name='deliveryRadio']:checked").val();

			if (radio == "" ||radio == null) {

				alert("「商品を新規登録する」か「商品データを更新する」を選択してください");
			}

			$(".overlay").css("display", "block");

			//新規登録
			if (radio == "registry") {

				goTransaction("excelImport.do");

			//更新
			} else if (radio == "update") {

				goTransaction("excelImportUpdateItem.do");
			}
		});
	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/excelImport" enctype="multipart/form-data">

	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<h4 class="heading">商品データインポート</h4>

	<table class="copyRadio">
		<tr>
			<td><label><input type="radio" name="deliveryRadio" value="registry" checked/>商品を新規登録する</label></td>
			<td><label><input type="radio" name="deliveryRadio" value="update" />商品データを更新する</label></td>
		</tr>
	</table>

	<div id="import_div">
		<div id="errorArea">
			<html:errors/>
		</div>

		<p class="import_message">※フォーマット以外のファイルは取り込まないようにしてください。</p>
			<p class="import_message">(システムエラーになる可能性があります。)</p>
		<br>
		<p class="import_message">※商品データの更新は『商品情報／在庫情報／価格情報』のシートのみが対象です。</p>
		<br/>

			<p class="import_elements">
			<nested:file property="fileUp" styleId="filupload_01" styleClass="dn" onchange="txtfilename_01.value = this.value;"/>
				<input type="text" id="txtfilename_01" class="input_fileupload"  size="25" disabled="disabled"/>
				<input type="button" id="btnbrowse_01" class="btn_excelUpload" onmouseover="this.className='btn_excelUpload_over'" onmouseout="this.className='btn_excelUpload'" onclick="filupload_01.click()" value=""/>
			</p>
		<br/>
		<p class="excel_import_submit">
			<input type="button" class="btn_import listImportButton" onmouseover="this.className='btn_import_over'" onmouseout="this.className='btn_import'"/>
<!-- 			<input type="button" class="btn_import listImportButton" onmouseover="this.className='btn_import_over'" onmouseout="this.className='btn_import'"/> -->
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