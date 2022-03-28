<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />
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
		<html:form action="/updateRuleList">
			<nested:notEqual value="0" property="ruleDetailDTO.ruleListId">
			<h4 class="heading">情報変更</h4>
			</nested:notEqual>
			<nested:equal value="0" property="ruleDetailDTO.ruleListId">
			<h4 class="heading">情報登録</h4>
			</nested:equal>
	
			<nested:hidden property="alertType" styleId="alertType"></nested:hidden>

			<nested:nest property="ruleDetailDTO">
				<table id="mstTable">
					<tr>
						<th>
							名称
						</th>
						<td>
							<nested:text property="listName" maxlength="25" />
						</td>
					</tr>
					<tr>
						<th>
							ID
						</th>
						<td>
							<nested:text property="listId"/>
						</td>
					</tr>
					<tr>
						<th>
							PASS
						</th>
						<td>
							<nested:password property="listPass" />
						</td>
					</tr>
					<tr>
						<th>
							備考
						</th>
						<td>
							<nested:text property="listRemarks" />
						</td>
					</tr>
					<tr>
						<th>
							リンク
						</th>
						<td>
							<nested:text property="listLink" />
						</td>
					</tr>
					
				</table>
			</nested:nest>
			<div class="buttonArea">
				<ul style="display: inline-flex;">
					<li class="footer_button">
						<a class="button_white btn-customize" href="javascript:void(0);" onclick="goTransaction('detailRule.do');">戻る</a>
					</li>
					<nested:notEqual value="0" property="ruleDetailDTO.ruleListId">
					<li class="footer_button">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateRuleList.do');">更新</a>
					</li>
					</nested:notEqual>
					<nested:equal value="0" property="ruleDetailDTO.ruleListId">
					<li class="footer_button">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryRuleList.do');">登録</a>
					</li>
					</nested:equal>
				</ul>
			</div>
			<%-- <div class="detailCorporetionButton">
				<div class="update_detailWarehouseButton">
					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('detailRule.do');">戻る</a>
				</div>
			<nested:notEqual value="0" property="ruleDetailDTO.ruleListId">
				<div class="update_detailWarehouseButton">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateRuleList.do');">更新</a>
				</div>
			</nested:notEqual>
			<nested:equal value="0" property="ruleDetailDTO.ruleListId">
				<div class="registry_detailWarehouseButton">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryRuleList.do');">登録</a>
				</div>
			</nested:equal>
			</div>
 --%>		</html:form>

	</body>
</html:html>