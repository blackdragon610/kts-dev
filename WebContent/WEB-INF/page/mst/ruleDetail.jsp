<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
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

			goTransaction('deleteRuleList.do');
		});
	});

	
	function editRuleList(id){
		
		$("#ruleListId").val(id);
		goTransaction('initUpdateRuleList.do');
		
	}
	
	</script>

	</head>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/detailRule">
		<h4 class="heading">ID・PASS一覧</h4>
			
		<html:hidden property="ruleId"  styleId="ruleId"/>
		<html:hidden property="ruleListId"  styleId="ruleListId"/>
		<html:hidden property="alertType"  styleId="alertType"/>
		<table id="mstTable" class="list">
			<tr>
				<th class="td_center allDelCheckSize"><input type="checkbox" id="allDelCheck"class="allDelCheck checkBoxTransForm"></th>
				<th class="w220">名称</th>
				<th class="w100">ID</th>
				<!-- <th>コピー</th> -->
				<th class="w200">PASS</th>
				<!-- <th>コピー</th> -->
				<th class="w200">備考</th>
				<th class="w220">リンク</th>
				<th></th>
			</tr>
			<nested:iterate property="ruleDetailList" indexId="idx">
				<tr>
					<td class="count salesSlipRow td_center">
						<nested:checkbox property="itemCheckFlg" styleClass="itemCheckFlg checkBoxTransForm"/>
						<nested:hidden property="itemCheckFlg" value="off"/>
					</td>
					<td><nested:write property="listName" /></td>
					<td><nested:write property="listId" /></td>
					<%-- <td>
						<a class="button_white" href="javascript:void(0);" onclick="copyClipBoard('<nested:write property="listId" />','id_<nested:write property="ruleListId" />')" id="id_<nested:write property="ruleListId" />"> 
						コピー</a>
					</td> --%>
					<td><nested:password property="listPass" styleClass="border-none hand text-center" readonly="true"/></td>
					<%-- <td>
						<a class="button_white" href="javascript:void(0);" onclick="copyClipBoard('<nested:write property="listPass" />','pass_<nested:write property="ruleListId" />')" id="pass_<nested:write property="ruleListId" />"> 
						コピー</a>
					</td> --%>
					<td><nested:write property="listRemarks" /></td>
					<td><a href="<nested:write property="listLink" />"  target="_blank"><nested:write property="listLink"/></a></td>
					<td>
						<a class="button_main" href="javascript:void(0);" onclick="editRuleList(<nested:write property="ruleListId"/>);"> 編集 </a>
					</td>
				</tr>
			</nested:iterate>
		</table>
			
		<div class="buttonArea">
			<ul style="display: inline-flex;">
				<li class="footer_button">
					<a class="button_white btn-customize" href="javascript:void(0);" onclick="goTransaction('ruleList.do');">戻る</a>
				</li>
				<li class="footer_button">
					<a class="button_main" href="javascript:void(0);" onclick="goTransaction('initRegistryRuleDetail.do');" >新規追加</a>
				</li>
				<li class="footer_button">
					<a class="button_white btn-customize deleteRuleListItem" href="javascript:void(0);">選択した分類を削除</a>
				</li>
			</ul>
		</div>
	</html:form>
</html:html>