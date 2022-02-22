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
		<html:form action="/updateWarehouse">
		<nested:notEqual value="0" property="warehouseDTO.sysWarehouseId">
		<h4 class="heading">倉庫情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="warehouseDTO.sysWarehouseId">
		<h4 class="heading">倉庫情報登録</h4>
		</nested:equal>

		<nested:hidden property="alertType" styleId="alertType"></nested:hidden>

			<nested:nest property="warehouseDTO">
				<table id="mstTable">
					<tr>
						<th>
							倉庫名
						</th>
						<td>
							<nested:text property="warehouseNm" maxlength="25" />
						</td>
					</tr>
					<tr>
						<th>
							所在地
						</th>
						<td>
							<nested:text property="address" maxlength="150" />
						</td>
					</tr>
					<tr>
						<th>
							郵便番号
						</th>
						<td>
							<nested:text property="zip" maxlength="25" styleClass="zip" onkeyup=
							"AjaxZip3.zip2addr('warehouseDTO.zip', '', 'warehouseDTO.addressFst', 'warehouseDTO.addressNxt');" />
						</td>
					</tr>
					<tr>
						<th>
							住所1
						</th>
						<td>
							<nested:text property="addressFst" maxlength="25" />
						</td>
					</tr>
					<tr>
						<th>
							住所2
						</th>
						<td>
							<nested:text property="addressNxt" maxlength="25" />
						</td>
					</tr>
					<tr>
						<th>
							電話番号
						</th>
						<td>
							<nested:text property="tellNo" maxlength="13" />
						</td>
					</tr>
					<tr>
						<th>
							氏名
						</th>
						<td>
							<nested:text property="logisticNm" maxlength="25" />
						</td>
					</tr>
				</table>

			</nested:nest>
			<div class="detailCorporetionButton">
			<nested:notEqual value="0" property="warehouseDTO.sysWarehouseId">
				<div class="update_detailWarehouseButton">
<!-- 					<input type="image" src="./img/delete.gif" class="btn_submit" onmouseover="this .src='./img/delete_over.gif'" onmouseout="this .src='./img/delete.gif'"onclick="goTransaction('deleteWarehouse.do');" align="middle"/> -->
<!-- 					<input type="image" src="./img/update.gif" class="btn_submit" onmouseover="this .src='./img/update_over.gif'" onmouseout="this .src='./img/update.gif'"onclick="goTransaction('updateWarehouse.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateWarehouse.do');">更新</a>
<!-- 					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteWarehouse.do');">削除</a> -->
				</div>
			</nested:notEqual>
			<nested:equal value="0" property="warehouseDTO.sysWarehouseId">
				<div class="registry_detailWarehouseButton">
<!-- 					<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goTransaction('registryWarehouse.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryWarehouse.do');">登録</a>
				</div>
			</nested:equal>
			</div>
		</html:form>

	</body>
</html:html>