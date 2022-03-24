<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>

<!--
【ユーザー一覧画面】
ファイル名：userList.jsp
作成日：2014/12/15
作成者：八鍬寛之

（画面概要）

ユーザーの一覧画面

・行ダブルクリック：ユーザーの詳細画面に遷移する。
・新規登録ボタン押下：ユーザーの新規登録画面に遷移する。
追記：
・海外閲覧権限および法人間請求書権限を持っている人は色を変えてわかりやすくした。(2017/08/24)

（注意・補足）

-->

	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/updateAllUserList">
	<nested:hidden property="alertType" styleId="alertType"/>
	<h4 class="heading">ユーザー一覧</h4>

	<nested:hidden property="sysUserId" styleId="sysUserId"/>
	<nested:hidden property="isEditModeAll" styleId="isEditModeAll"/>
	<nested:hidden property="ruleId" styleId="ruleId"/>
	
	<!-- ユーザー権限に属するリンクの編集 -->	
	<div id="tblExtraUserRuleDetail" style="display:none">
	
		<table id="mstTable">
			<tr>
				<th class="w150">名称</th>
				<th class="w50">権限</th>
			</tr>
			<nested:iterate property="userList" indexId="id">
				<nested:iterate property="mstRulesList" indexId="idx">
					<nested:iterate property="mstRulesDetailList" indexId="indx">
						<tr class="rulesList_${id}_${idx}" style="display:none;">
							<td>
								<nested:write property="listName" />
							</td>
							<td>
								<nested:equal property="isvisible" value="1">&#9898;</nested:equal>
								<nested:notEqual property="isvisible" value="1">&#9932;</nested:notEqual>
							</td>
						</tr>
						<tr class="editRulesList_${id}_${idx}" style="display:none;">
							<td>
								<nested:write property="listName" />
							</td>
							<td class="editCheckBox">
								<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${indx}"/>
								<nested:checkbox property="listVisible" styleClass="visibleFlag_${indx} checkBoxTransForm"></nested:checkbox>
								
							</td>
						</tr>
					</nested:iterate>
				</nested:iterate>
			</nested:iterate>
		</table>

		<div class="update_detailUserButton">
			<a class="button_main btnUpdateExtraUserList" href="Javascript:void(0);" onclick="saveExtraRuleDetailByUserId()">更新</a>
			<a class="button_white" href="Javascript:void(0);" onclick="onclickBack()">戻る</a>
		</div>
	</div>
	<!--  -->
	<!-- ユーザー権限の表示 -->
	<div id="tblUserList">
		<table id="mstTable" class="user-list">
			<tr>
				<th>ユーザー名</th>
				<th>マスタ</th>
				<th>法人間請求権限</th>
				<th>海外情報閲覧権限</th>
				<nested:iterate property="ruleList" indexId="idx">
					<nested:notEqual property="ruleName" value="マスタ">
						<th><nested:write property="ruleName"/></th>
					</nested:notEqual>
				</nested:iterate>
				<nested:equal property="isEditModeAll" value="0">
					<th></th>
					<th></th>
				</nested:equal>
			</tr>
			<!-- 一括編集ではない場合 -->
			<nested:equal property="isEditModeAll" value="0">
				<nested:iterate property="userList" indexId="id">
				<!--  -->
					<tr class="view_row_${id}">
						<td ondblclick="goDetailUser(<nested:write  property="sysUserId"/>);" style="cursor:pointer">
							<nested:write  property="userFamilyNmKanji"/><nested:write  property="userFirstNmKanji"/>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:equal property="ruleName" value="マスタ">
							<td>
								<nested:equal property="isvisible" value="1">
									<nested:equal property="childCount" value="1">&#9898;</nested:equal>
										<nested:notEqual property="childCount" value="1">
											<span class="viewChildRule" id="rulesList_${id}_${idx}"> &#9651; 詳細</span>
										</nested:notEqual>
								</nested:equal>
									
								<nested:notEqual property="isvisible" value="1">&#9932;</nested:notEqual>
							</td>
							</nested:equal>
						</nested:iterate>
						<td>
							<nested:equal property="btobBillAuth" value="1">&#9898;</nested:equal>
							<nested:notEqual property="btobBillAuth" value="1">&#9932;</nested:notEqual>
							
						</td>
						<td>
							<nested:equal property="overseasInfoAuth" value="1">&#9898;</nested:equal>
							<nested:notEqual property="overseasInfoAuth" value="1">&#9932;</nested:notEqual>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:notEqual property="ruleName" value="マスタ">
								<td>
									<nested:equal property="isvisible" value="1">
										<nested:equal property="childCount" value="1">&#9898;</nested:equal>
										<nested:notEqual property="childCount" value="1">
											<span class="viewChildRule" id="rulesList_${id}_${idx}"> &#9651; 詳細</span>
										</nested:notEqual>
									</nested:equal>
									<nested:notEqual property="isvisible" value="1">&#9932;</nested:notEqual>
								</td>
							</nested:notEqual>
						</nested:iterate>
							<td>
								<a class="button_main" href="Javascript:void(0);" onclick="goEditUser(<nested:write property="sysUserId"/>, '${id}');">編集</a>
							</td>
							<td>
								<a class="button_white" href="Javascript:void(0);" onclick="goDelUser(<nested:write property="sysUserId"/>);">削除</a>
							</td>
					</tr>
					<!-- 個々のユーザー権限の編集 -->
					<tr class="edit_row_${id}" style="display:none;">
						<td ondblclick="goDetailUser(<nested:write  property="sysUserId"/>);" style="cursor:pointer">
							<nested:write  property="userFamilyNmKanji"/>
							<nested:write  property="userFirstNmKanji"/>
							<nested:hidden property="sysUserId" styleId="sysUserIdx_${id}"/>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:equal property="ruleName" value="マスタ">
								<td>
									<nested:hidden property="isvisible" styleClass="hidden_mastareItemCheckFlg"/>
									<nested:checkbox property="isvisible" styleClass="mastareItemCheckFlg checkBoxTransForm listCheck"></nested:checkbox>
									<nested:notEqual property="childCount" value="0">
										<nested:notEqual property="childCount" value="1">
											<span class="editChildRule" id="editRulesList_${id}_${idx}"> 詳細</span>
											<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
											<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_mastareItemCheckFlg"/>
										</nested:notEqual>
									</nested:notEqual>
								</td>
							</nested:equal>
						</nested:iterate>
						<td>
							<nested:hidden property="btobBillAuth" styleClass="hidden_btobItemCheckFlg"/>
							<nested:checkbox property="btobBillAuth" styleClass="btobItemCheckFlg checkBoxTransForm"/>
						</td>
						<td>
							<nested:hidden property="overseasInfoAuth" styleClass="hidden_infoAuthItemCheckFlg"/>
							<nested:checkbox property="overseasInfoAuth" styleClass="infoAuthItemCheckFlg checkBoxTransForm"/>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:notEqual property="ruleName" value="マスタ">
								<td class="itemColumn">
									<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${idx}"/>
									<nested:checkbox property="isvisible" styleClass="visibleFlag_${idx} checkBoxTransForm listCheck"></nested:checkbox>
									<nested:notEqual property="childCount" value="0">
										<nested:notEqual property="childCount" value="1">
											<span class="editChildRule" id="editRulesList_${id}_${idx}"> 詳細</span>
											<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
											<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_visibleFlag_${id}"/>
										</nested:notEqual>
									</nested:notEqual>
								</td>
							</nested:notEqual>
						</nested:iterate>
						<td>
							<a class="button_main" href="Javascript:void(0);" onclick="goUpdateExtraUserRule(<nested:write  property="sysUserId"/>);">登録</a>
						</td>
						<td>
							<a class="button_white" href="Javascript:void(0);" onclick="goDelUser(<nested:write  property="sysUserId"/>);">削除</a>
						</td>
					</tr>
				</nested:iterate>
			</nested:equal>
			<!-- 一括編集 -->
			<nested:equal property="isEditModeAll" value="1">
				<nested:iterate property="userList" indexId="id">
					<tr class="itemRow">
						<td ondblclick="goDetailUser(<nested:write  property="sysUserId"/>);" style="cursor:pointer">
							<nested:write  property="userFamilyNmKanji"/>
							<nested:write  property="userFirstNmKanji"/>
							<nested:hidden property="sysUserId" styleId="sysUserIdx_${id}"/>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:equal property="ruleName" value="マスタ">
								<td>
									<nested:hidden property="isvisible" styleClass="hidden_mastareItemCheckFlg"/>
									<nested:checkbox property="isvisible" styleClass="mastareItemCheckFlg checkBoxTransForm listCheck"/>
									<nested:notEqual property="childCount" value="0">
										<nested:notEqual property="childCount" value="1">
											<span class="editChildRule" id="editRulesList_${id}_${idx}"> 詳細</span>
											<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
											<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_mastareItemCheckFlg"/>
										</nested:notEqual>
									</nested:notEqual>
								</td>
							</nested:equal>
						</nested:iterate>
						<td>
							<nested:hidden property="btobBillAuth" styleClass="hidden_btobItemCheckFlg"/>
							<nested:checkbox property="btobBillAuth" styleClass="btobItemCheckFlg checkBoxTransForm"/>
						</td>
						<td>
							<nested:hidden property="overseasInfoAuth" styleClass="hidden_infoAuthItemCheckFlg"/>
							<nested:checkbox property="overseasInfoAuth" styleClass="infoAuthItemCheckFlg checkBoxTransForm"/>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:notEqual property="ruleName" value="マスタ">
								<td class="itemColumn">
									<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${id}_${idx }"/>
									<nested:checkbox property="isvisible" styleClass="visibleFlag_${id}_${idx } checkBoxTransForm listCheck"></nested:checkbox>
									<nested:notEqual property="childCount" value="0">
										<nested:notEqual property="childCount" value="1">
											<span class="editChildRule" id="editRulesList_${id}_${idx}"> 詳細</span>
											<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
											<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_visibleFlag_${id}"/>
										</nested:notEqual>
									</nested:notEqual>
								</td>
							</nested:notEqual>
						</nested:iterate>
					</tr>
				</nested:iterate>
			</nested:equal>
		</table>
		<div class="buttonArea">
			<ul style="position: relative;">
				<li class="footer_button">
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('initRegistryUser.do');">新規登録</a>
				</li>
				<nested:equal property="isEditModeAll" value="0">
					<li class="footer_button">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('editAllUserList.do');">一括編集</a>
					</li>
				</nested:equal>
				<nested:equal property="isEditModeAll" value="1">
					<li class="footer_button">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateAllUserList.do');">一括登録</a>
					</li>
					<li class="footer_button">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('backViewList.do');">BACK</a>
					</li>
				</nested:equal>
			</ul>
		</div>
	</div>

	</html:form>
	
	<script>
		var seleletElmentId = "";
		window.onload = function() {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value='0';
			
			if($("#isEditModeAll").val() == 1){
				initCheckBox();
			}
			
			$(document).on('click', '.checkBoxTransForm', function () {
				$(this).unbind().each(function() {
					var classList = $(this).attr('class').split(' ');
					console.log($(this).parent().find(".hidden_" + classList[0]));
		        	$(this).parent().find(".hidden_" + classList[0]).val(this.checked?1:0);
		        });
			});
			
			$(document).on('click', '.editChildRule', function () {
				$(this).unbind().each(function() {
					seleletElmentId = $(this).attr('id');
					var arrItem = seleletElmentId.split('_');
					var selectUserId = $(this).parent().parent().find("#sysUserIdx_"+ arrItem[1]).val();
					var selectRuleId = $(this).parent().find("#ruleId_"+ arrItem[1]).val();
					
					$("#sysUserId").val(selectUserId); $("#ruleId").val(selectRuleId);
					
					$("h4.heading").html("詳細画面");
					$("#tblUserList").css("display","none");
					$(".btnUpdateExtraUserList").css("display","");
					$("#tblExtraUserRuleDetail").css("display","");
					$("#tblExtraUserRuleDetail ."+ seleletElmentId).css("display","");
					
					var tdElement = $("."+ seleletElmentId).find("td.editCheckBox");
					console.log("seleletElmentId = ", seleletElmentId);
					var dynamicColumnsCount = tdElement.length;
					for(var i = 0; i < dynamicColumnsCount; i++){
						if(tdElement.find(".hidden_visibleFlag_"+i).val() == 1)
							tdElement.find(".visibleFlag_"+i).prop( "checked", true );
					}
		        });
			});
			
			$(document).on('click', '.viewChildRule', function () {
				$(this).unbind().each(function() {
					seleletElmentId = $(this).attr('id');
					$("h4.heading").html("詳細画面");
					$("#tblUserList").css("display","none");
					$(".btnUpdateExtraUserList").css("display","none");
					$("#tblExtraUserRuleDetail").css("display","");
					$("#tblExtraUserRuleDetail ."+ seleletElmentId).css("display","");
		        });
			}); 
		};
		
		function saveExtraRuleDetailByUserId(){
			var userId = $("#sysUserId").val();
			var ruleId = $("#ruleId").val();
			var ruleDetailList = [];
			var checkList = $("." + seleletElmentId + " .editCheckBox").find(':hidden');
			
			for(var i = 0; i < checkList.size(); i++){
				ruleDetailList.push(checkList[i].value);
			}
			
			console.log(ruleDetailList); 
			
			$.ajax({
				url : "./saveExtraRuleDetailByUserId.do"
				,type : 'POST'
				,traditional: true
				,data : {
					'ruleDetailList': ruleDetailList
					,'sysUserId': userId
					,'ruleId': ruleId
					}
			}).done(function(data) {
				$("h4.heading").html("ユーザー一覧");
				$("#tblUserList").css("display","");
				$("#tblExtraUserRuleDetail").css("display","none");
				$("#tblExtraUserRuleDetail ."+ seleletElmentId).css("display","none");
			}).fail(function(data) {
				
			});
			return;
		}
		
		function onclickBack(){
			$("h4.heading").html("ユーザー一覧");
			$("#tblUserList").css("display","");
			$("#tblExtraUserRuleDetail").css("display","none");
			$(".btnUpdateExtraUserList").css("display","none");
			$("#tblExtraUserRuleDetail ."+ seleletElmentId).css("display","none");
		}
		
		function goDetailUser(value){
	
			document.getElementById('sysUserId').value = value;
			goTransaction('detailUser.do');
		}
		
		function goEditUser(userId, rowIndex){
			$("#sysUserId").val(userId);
			$(".view_row_"+rowIndex).css("display","none");
			$(".edit_row_"+rowIndex).css("display","");
			if($(".edit_row_"+rowIndex).find(".hidden_mastareItemCheckFlg").val() == 1)
				$(".edit_row_"+rowIndex).find(".mastareItemCheckFlg").prop( "checked", true );
			if($(".edit_row_"+rowIndex).find(".hidden_btobItemCheckFlg").val() == 1)
				$(".edit_row_"+rowIndex).find(".btobItemCheckFlg").prop( "checked", true );
			if($(".edit_row_"+rowIndex).find(".hidden_infoAuthItemCheckFlg").val() == 1)
				$(".edit_row_"+rowIndex).find(".infoAuthItemCheckFlg").prop( "checked", true );
			
			var dynamicColumnsCount = $(".edit_row_"+rowIndex).find("td.itemColumn").length;
			for(var j = 0; j < dynamicColumnsCount; j++){
				if($(".edit_row_"+rowIndex).find(".hidden_visibleFlag_"+j).val() == 1)
					$(".edit_row_"+rowIndex).find(".visibleFlag_"+j).prop( "checked", true );
			}
		}
		
		function goUpdateExtraUserRule(userId){
			$("#sysUserId").val(userId);
			goTransaction('goUpdateExtraUserRule.do');
		}
		
		function goDelUser(userId){
			$("#sysUserId").val(userId);
			if(!confirm("選択されてるものを削除します。よろしいですか？")){
				return false;
			}

			goTransaction('deleteUser.do');
		}
		
		function initCheckBox(){
			
			var resultArea = $('tr.itemRow');
			for (var i = 0; i < resultArea.size(); i++){
				console.log(resultArea.eq(i).find("td.itemColumn").length)
				if(resultArea.eq(i).find(".hidden_mastareItemCheckFlg").val() == 1)
					resultArea.eq(i).find(".mastareItemCheckFlg").prop( "checked", true );
				if(resultArea.eq(i).find(".hidden_btobItemCheckFlg").val() == 1)
					resultArea.eq(i).find(".btobItemCheckFlg").prop( "checked", true );
				if(resultArea.eq(i).find(".hidden_infoAuthItemCheckFlg").val() == 1)
					resultArea.eq(i).find(".infoAuthItemCheckFlg").prop( "checked", true );
				
				var dynamicColumnsCount = resultArea.eq(i).find("td.itemColumn").length;
				for(var j = 0; j < dynamicColumnsCount; j++){
					if(resultArea.eq(i).find(".hidden_visibleFlag_"+i+"_"+j).val() == 1)
						resultArea.eq(i).find(".visibleFlag_"+i+"_"+j).prop( "checked", true );
				}
			}
		}
	

	</script>
</html:html>