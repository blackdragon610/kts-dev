<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>

<!--
【倉庫マスタ詳細画面】
ファイル名：detailWarehouse.jsp
作成日：2014/12/19
作成者：八鍬寛之

（画面概要）

倉庫情報の詳細画面

・更新ボタン押下：倉庫情報を更新する。
・削除ボタン押下：倉庫情報を削除する。
削除ボタンを使用できないよう修正

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
		<html:form action="/updateRule">
			<nested:notEqual value="0" property="ruleDTO.ruleId">
			<h4 class="heading">情報変更</h4>
			</nested:notEqual>
			<nested:equal value="0" property="ruleDTO.ruleId">
			<h4 class="heading">情報登録</h4>
			</nested:equal>
	
			<nested:hidden property="alertType" styleId="alertType"></nested:hidden>

			<nested:nest property="ruleDTO">
				<table id="mstTable">
					<tr>
						<th>
							分類名
						</th>
						<td>
							<nested:text property="ruleName" maxlength="25" />
						</td>
					</tr>
					
				</table>
			</nested:nest>
			<div class="detailCorporetionButton">
			<nested:notEqual value="0" property="ruleDTO.ruleId">
				<div class="update_detailWarehouseButton">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateRule.do');">更新</a>
				</div>
			</nested:notEqual>
			<nested:equal value="0" property="ruleDTO.ruleId">
				<div class="registry_detailWarehouseButton">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryRule.do');">登録</a>
				</div>
			</nested:equal>
			</div>
		</html:form>

	</body>
</html:html>