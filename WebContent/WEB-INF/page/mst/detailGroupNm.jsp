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
	};
	</SCRIPT>
	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<html:form action="/updateGroupNm">
		<nested:notEqual value="0" property="groupNmDTO.sysGroupNmId">
		<h4 class="heading">分類変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="groupNmDTO.sysGroupNmId">
		<h4 class="heading">分類登録</h4>
		</nested:equal>

		<nested:hidden property="alertType" styleId="alertType"></nested:hidden>

			<nested:nest property="groupNmDTO">
				<table id="mstTable">
					<tr>
						<th>
							分類組み合わせ
						</th>
						<td>
							<nested:select property="sysLargeGroupId" >
								<html:option value="" />
								<html:optionsCollection property="largeGroupList" value="sysLargeGroupId"  label="largeGroupNm" />
							</nested:select>
						</td>
						<td>
							<nested:select property="sysMiddleGroupId" >
								<html:option value="" />
								<html:optionsCollection property="middleGroupList" value="sysMiddleGroupId"  label="middleGroupNm" />
							</nested:select>
						</td>
						<td>
							<nested:select property="sysSmallGroupId" >
								<html:option value="" />
								<html:optionsCollection property="smallGroupList" value="sysSmallGroupId"  label="smallGroupNm" />
							</nested:select>
						</td>
					</tr>
				</table>

			</nested:nest>
			<div class="detailCorporetionButton">
			<nested:notEqual value="0" property="groupNmDTO.sysGroupNmId">
				<div class="update_detailGroupButton">
<!-- 					<input type="image" src="./img/delete.gif" class="btn_submit" onmouseover="this .src='./img/delete_over.gif'" onmouseout="this .src='./img/delete.gif'"onclick="goTransaction('deleteGroupNm.do');" align="middle"/> -->
<!-- 					<input type="image" src="./img/update.gif" class="btn_submit" onmouseover="this .src='./img/update_over.gif'" onmouseout="this .src='./img/update.gif'"onclick="goTransaction('updateGroupNm.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateGroupNm.do');">更新</a>
					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteGroupNm.do');">削除</a>
				</div>
			</nested:notEqual>
			<nested:equal value="0" property="groupNmDTO.sysGroupNmId">
				<div class="registry_detailGroupButton">
<!-- 					<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goTransaction('registryGroupNm.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryGroupNm.do');">登録</a>
				</div>
			</nested:equal>
			</div>
		</html:form>

	</body>
</html:html>