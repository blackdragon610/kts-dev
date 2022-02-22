<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/import.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>

<!--
【商品データインポート画面】
ファイル名：domesticExhibitionImport.jsp
作成日：2016/11/29
作成者：野澤法都

（画面概要）

国内出品データベースの新規登録・更新をエクセルファイルの取り込みで行う。
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

				goTransaction("dmstcExibtExclImprt.do");

			//更新
			} else if (radio == "update") {

				goTransaction("dmstcExibtExclImprtUpdItm.do");
			}
		});
	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/dmstcExibtExclImprt" enctype="multipart/form-data">

	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<nested:nest property="registryDto">
		<nested:hidden property="messageFlg" styleId="messageFlg"/>
		<nested:notEmpty property="message">
			<div id="messageArea">
				<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
			</div>
		</nested:notEmpty>
	</nested:nest>

	<h4 class="heading">出品データベース<br/>インポート</h4>

	<table class="copyRadio">
		<tr>
			<td><label><input type="radio" name="deliveryRadio" value="registry" checked/>出品データベースを新規登録する</label></td>
			<td><label><input type="radio" name="deliveryRadio" value="update" />出品データベースを更新する</label></td>
		</tr>
	</table>

	<div id="import_div">
		<div id="errorArea">
			<html:errors/>
		</div>

		<p class="import_message">※フォーマット以外のファイルは取り込まないようにしてください。</p>
			<p class="import_message">(システムエラーになる可能性があります。)</p>
			<p class="import_message">※管理品番未記載の行が存在する場合、管理品番未記載行以降は取り込まれません。</p>
		<br>
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