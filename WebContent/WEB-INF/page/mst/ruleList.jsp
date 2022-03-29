<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/common.css" type="text/css" />
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>


	<script type="text/javascript">
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	}

	$(function () {
		
		//チェックボックス全件選択・解除
		$("#allDelCheck").click(function() {
			if(this.checked) {
				$(".itemCheckFlg").prop("checked", true);
			} else {
				$(".itemCheckFlg").prop("checked", false);
			}
		});
		//一括削除ボタン押下
		$(".deleteRuleListItem").click(function () {
			if($(".itemCheckFlg:checked").length == 0) {
				alert("対象データを選択してください。");
				return false;
			}
			if(!confirm("選択されてるものを削除します。よろしいですか？")){
				return false;
			}

			goTransaction('ruleItemDelete.do');
		});
		
	});
	
	function goDetailRule(value){
		$("#ruleId").val(value);
		goTransaction('detailRule.do');
	}
	
	function editRule(id){
		$("#ruleId").val(id);
		goTransaction('editeRule.do');
	}
	
	/* function registHandle(){
		if(isAddData) goTransaction('registryRule.do');
		else goTransaction('updateRule.do');
	} */
	
	</script>
	</head>
	
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<h4 class="heading">ID・PASS一覧</h4>
	<html:form action="/ruleList">
		
		<html:hidden property="ruleId"  styleId="ruleId"/>
		<html:hidden property="alertType"  styleId="alertType"/>
		<table id="mstTable" class="list">
			<tr>
				<th class="td_center allDelCheckSize"><input type="checkbox" id="allDelCheck"class="allDelCheck checkBoxTransForm"></th>
				<th class="w200">分類</th>
				<th></th>
			</tr>
			<nested:notEmpty property="ruleList">
			<nested:iterate property="ruleList" indexId="idx">
				<%-- <nested:equal property="isvisible" value="1"> --%>
					<tr ondblclick="goDetailRule(<nested:write  property="ruleId"/>);">
						<td class="count salesSlipRow td_center">
							<nested:checkbox property="itemCheckFlg" styleClass="itemCheckFlg checkBoxTransForm"/>
							<nested:hidden property="itemCheckFlg" value="off"/>
						</td>
						<td><nested:write property="ruleName" /></td>
						<td><a class="button_main" href="javascript:void(0);" onclick="editRule(<nested:write  property="ruleId"/>);">編集</a></td>
					</tr>
				<%-- </nested:equal> --%>
			</nested:iterate>
			</nested:notEmpty>
		</table>
		
		<div class="buttonArea">
			<ul style="display: inline-flex;">
			<li class="footer_button">
					<a class="button_main" href="javascript:void(0);" onclick="goTransaction('initRegistryRule.do');">新規追加</a>
				</li>
				<li class="footer_button">
					<a class="button_white btn-customize deleteRuleListItem" href="javascript:void(0);">選択した分類を削除</a>
				</li>
			</ul>
		</div>
		
	</html:form>
</html:html>