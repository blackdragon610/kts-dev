<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<SCRIPT type="text/javascript">
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	}

	</SCRIPT>
	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<html:form action="/updateVariousGroup">
		<nested:notEqual value="0" property="sysVariousGroupId">
		<h4 class="heading"><nested:write property="groupSelectName"/>分類変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="sysVariousGroupId">
		<h4 class="heading"><nested:write property="groupSelectName"/>分類登録</h4>
		</nested:equal>

		<nested:hidden property="alertType" styleId="alertType"></nested:hidden>

<%-- 		<nested:select property="groupSelect" styleId="groupSelect" > --%>
<%-- 			<html:option value="0">　</html:option> --%>
<%-- 			<html:option value="1">大分類</html:option> --%>
<%-- 			<html:option value="2">中分類</html:option> --%>
<%-- 			<html:option value="3">小分類</html:option> --%>
<%-- 		</nested:select> --%>



		<nested:notEmpty property="largeGroupDTO">
			<nested:nest property="largeGroupDTO">
				<table id="mstTable">
					<tr>
						<th>
							分類No
						</th>
						<td style="text-align: left;">
							<nested:text property="largeGroupNo" styleClass="w50" maxlength="4" />
						</td>
					</tr>
					<tr>
						<th>
							分類名
						</th>
						<td>
							<nested:text property="largeGroupNm" maxlength="50" />
						</td>
					</tr>
				</table>
			</nested:nest>
		</nested:notEmpty>


		<nested:notEmpty property="middleGroupDTO">
			<nested:nest property="middleGroupDTO">
				<table id="mstTable">
					<tr>
						<th>
							分類No
						</th>
						<td style="text-align: left;">
							<nested:text property="middleGroupNo" styleClass="w50" maxlength="4" />
						</td>
					</tr>
					<tr>
						<th>
							分類名
						</th>
						<td>
							<nested:text property="middleGroupNm" maxlength="50" />
						</td>
					</tr>
				</table>
			</nested:nest>
		</nested:notEmpty>


		<nested:notEmpty property="smallGroupDTO">
			<nested:nest property="smallGroupDTO">
				<table id="mstTable">
					<tr>
						<th>
							分類No
						</th>
						<td style="text-align: left;">
							<nested:text property="smallGroupNo" styleClass="w50" maxlength="4" />
						</td>
					</tr>
					<tr>
						<th>
							分類名
						</th>
						<td>
							<nested:text property="smallGroupNm" maxlength="50" />
						</td>
					</tr>
				</table>
			</nested:nest>
		</nested:notEmpty>

			<div class="detailCorporetionButton">
			<nested:notEqual value="0" property="sysVariousGroupId">
				<div class="update_detailGroupButton">
<!-- 					<input type="image" src="./img/delete.gif" class="btn_submit" onmouseover="this .src='./img/delete_over.gif'" onmouseout="this .src='./img/delete.gif'"onclick="goTransaction('deleteVariousGroup.do');" align="middle"/> -->
<!-- 					<input type="image" src="./img/update.gif" class="btn_submit" onmouseover="this .src='./img/update_over.gif'" onmouseout="this .src='./img/update.gif'"onclick="goTransaction('updateVariousGroup.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateVariousGroup.do');">更新</a>
					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteVariousGroup.do');">削除</a>
				</div>
			</nested:notEqual>
			<nested:equal value="0" property="sysVariousGroupId">
				<div class="registry_detailGroupButton">
<!-- 					<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goTransaction('registryVariousGroup.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryVariousGroup.do');">登録</a>
				</div>
			</nested:equal>
			</div>
		</html:form>

	</body>
</html:html>