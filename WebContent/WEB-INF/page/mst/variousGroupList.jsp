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
	}

	function goRegistry() {

		if (0 != document.getElementById('groupSelect').value) {
			document.getElementById('sysVariousGroupId').value = 0;
			goTransaction('initRegistryVariousGroup.do');
		} else {

			return false;
		}

	}

	function goDetailGroup(value){

		document.getElementById('sysVariousGroupId').value = value;
		goTransaction('detailVariousGroup.do');
	}
	function goSelectGroupList(obj) {

// 		alert(obj.value);
		if (0 != obj.value) {

			goTransaction('variousGroupList.do');
		} else {

			goTransaction('initVariousGroupList.do');
		}

	};
	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initVariousGroupList">
	<nested:hidden property="alertType" styleId="alertType"/>

	<h4 class="heading">各分類一覧</h4>
	<input type="hidden" name="sysVariousGroupId"  id="sysVariousGroupId" />

<!-- 	<div class="registry_detailGroupButton"> -->
<!-- 		<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goRegistry();" align="middle"/> -->
<!-- 	</div> -->
	<table id="mstTable" class="list">

		<tr>
			<td class="pulldown" colspan="2"><nested:select property="groupSelect" styleId="groupSelect" onchange="goSelectGroupList(this);" >
					<html:option value="0">　　分類区分を選択してください　　</html:option>
					<html:option value="1">大分類</html:option>
					<html:option value="2">中分類</html:option>
					<html:option value="3">小分類</html:option>
				</nested:select>
			</td>
		</tr>
		<tr>
			<th>分類No</th>
			<th>分類名</th>
		</tr>
		<nested:notEmpty property="variousGroupList">
			<nested:iterate property="variousGroupList" indexId="idx">
			<tr ondblclick="goDetailGroup(<nested:write  property="sysVariousGroupId"/>);">
				<td><nested:write  property="variousGroupNo"/></td>
				<td><nested:write  property="variousGroupNm"/></td>
			</tr>
			</nested:iterate>
		</nested:notEmpty>
		<nested:notEmpty property="largeGroupList">
			<nested:iterate property="largeGroupList" indexId="idx">
			<tr ondblclick="goDetailGroup(<nested:write  property="sysLargeGroupId"/>);">
				<td><nested:write  property="largeGroupNo"/></td>
				<td><nested:write  property="largeGroupNm"/></td>
			</tr>
			</nested:iterate>
		</nested:notEmpty>
		<nested:notEmpty property="middleGroupList">
			<nested:iterate property="middleGroupList" indexId="idx">
			<tr ondblclick="goDetailGroup(<nested:write  property="sysMiddleGroupId"/>);">
				<td><nested:write  property="middleGroupNo"/></td>
				<td><nested:write  property="middleGroupNm"/></td>
			</tr>
			</nested:iterate>
		</nested:notEmpty>
		<nested:notEmpty property="smallGroupList">
			<nested:iterate property="smallGroupList" indexId="idx">
			<tr ondblclick="goDetailGroup(<nested:write  property="sysSmallGroupId"/>);">
				<td><nested:write  property="smallGroupNo"/></td>
				<td><nested:write  property="smallGroupNm"/></td>
			</tr>
			</nested:iterate>
		</nested:notEmpty>
	</table>

	<nested:notEqual property="groupSelect" value="0">
		<div class="buttonArea">
			<a class="button_main" href="Javascript:void(0);" onclick="goRegistry();">新規登録</a>
		</div>
	</nested:notEqual>

	</html:form>
</html:html>