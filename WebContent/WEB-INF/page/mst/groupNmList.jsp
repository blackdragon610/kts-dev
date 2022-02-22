<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<script>

	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	};
	function goDetailGroupNm(value){

		document.getElementById('sysGroupNmId').value = value;
		goTransaction('detailGroupNm.do');
	}
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailGroupNm">
	<nested:hidden property="alertType" styleId="alertType"/>

	<h4 class="heading">分類一覧</h4>
	<input type="hidden" name="sysGroupNmId"  id="sysGroupNmId" />
<!-- 	<div class="registry_detailUserButton"> -->
<!-- 		<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goTransaction('initRegistryGroupNm.do');" align="middle"/> -->
<!-- 	</div> -->

	<div class="buttonArea">
		<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryGroupNm.do');">新規登録</a>
	</div>

	<table id="mstTable" class="list">
		<tr>
			<th>ID</th>
			<th>分類名</th>
		</tr>
		<nested:notEmpty property="groupNmList">
			<nested:iterate property="groupNmList" indexId="idx">
				<tr ondblclick="goDetailGroupNm(<nested:write  property="sysGroupNmId"/>);">
					<td><nested:write  property="sysGroupNmId"/></td>
					<td><nested:write  property="groupNmConcat"/></td>
				</tr>
			</nested:iterate>
		</nested:notEmpty>

	</table>
	</html:form>
</html:html>