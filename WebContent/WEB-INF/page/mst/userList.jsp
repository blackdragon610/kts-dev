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
				<th class="td_center allCheckSize"><input type="checkbox" id="allCheck"class="allCheck"></th>
			</tr>
			<nested:iterate property="userList" indexId="id">
				<nested:iterate property="mstMasterList" indexId="idx">
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="userListName" /></td>
						<td>
							<nested:equal property="userListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="userListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="ruleListName" /></td>
						<td>
							<nested:equal property="ruleListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="ruleListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="corporationListName" /></td>
						<td>
							<nested:equal property="corporationListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="corporationListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="accountListName" /></td>
						<td>
							<nested:equal property="accountListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="accountListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="channelListName" /></td>
						<td>
							<nested:equal property="channelListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="channelListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="warehouseListName" /></td>
						<td>
							<nested:equal property="warehouseListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="warehouseListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="makerListName" /></td>
						<td>
							<nested:equal property="makerListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="makerListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="setItemListName" /></td>
						<td>
							<nested:equal property="setItemListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="setItemListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="clientListName" /></td>
						<td>
							<nested:equal property="clientListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="clientListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}_${idx}" style="display:none;">
						<td><nested:write property="deliveryListName" /></td>
						<td>
							<nested:equal property="deliveryListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="deliveryListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					
					
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="userListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="userListFlg" styleClass="hidden_visibleFlag_0"/>
							<nested:checkbox property="userListFlg" styleClass="visibleFlag_0 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="ruleListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="ruleListFlg" styleClass="hidden_visibleFlag_1"/>
							<nested:checkbox property="ruleListFlg" styleClass="visibleFlag_1 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="corporationListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="corporationListFlg" styleClass="hidden_visibleFlag_2"/>
							<nested:checkbox property="corporationListFlg" styleClass="visibleFlag_2 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="accountListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="accountListFlg" styleClass="hidden_visibleFlag_3"/>
							<nested:checkbox property="accountListFlg" styleClass="visibleFlag_3 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="channelListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="channelListFlg" styleClass="hidden_visibleFlag_4"/>
							<nested:checkbox property="channelListFlg" styleClass="visibleFlag_4 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="warehouseListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="warehouseListFlg" styleClass="hidden_visibleFlag_5"/>
							<nested:checkbox property="warehouseListFlg" styleClass="visibleFlag_5 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="makerListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="makerListFlg" styleClass="hidden_visibleFlag_6"/>
							<nested:checkbox property="makerListFlg" styleClass="visibleFlag_6 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="setItemListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="setItemListFlg" styleClass="hidden_visibleFlag_7"/>
							<nested:checkbox property="setItemListFlg" styleClass="visibleFlag_7 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="clientListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="clientListFlg" styleClass="hidden_visibleFlag_8"/>
							<nested:checkbox property="clientListFlg" styleClass="visibleFlag_8 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editMasterList_${id}_${idx}" style="display:none;">
						<td> <nested:write property="deliveryListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="deliveryListFlg" styleClass="hidden_visibleFlag_9"/>
							<nested:checkbox property="deliveryListFlg" styleClass="visibleFlag_9 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
				</nested:iterate>
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
								<nested:checkbox property="listVisible" styleClass="visibleFlag_${indx} checkBoxClass"></nested:checkbox>
								
							</td>
						</tr>
					</nested:iterate>
				</nested:iterate>
			</nested:iterate>
		</table>

		<div class="update_detailUserButton">
			<a class="button_main btnUpdateExtraUserList" href="Javascript:void(0);" onclick="saveExtraRuleDetailByUserId()">更新</a>
			<a class="button_white btn-customize" href="Javascript:void(0);" onclick="onclickBack()">戻る</a>
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
					<th><nested:write property="ruleName"/></th>
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
						<td>
							<nested:notEmpty property="mstMasterList">
								<nested:iterate property="mstMasterList" indexId="idx">
								
									<nested:equal property="isvisible" value="1">
										<nested:equal property="viewableCount" value="1">&#9898;</nested:equal>
										<nested:notEqual property="viewableCount" value="1">
											<nested:equal property="viewableCount" value="10">&#9898;</nested:equal>
											<nested:notEqual property="viewableCount" value="10">
											<span class="viewChildRule" id="masterList_${id}_${idx}"> &#9651; 詳細</span>
											</nested:notEqual>
										</nested:notEqual>
									</nested:equal>
									<nested:notEqual property="isvisible" value="1">&#9932;</nested:notEqual>
								</nested:iterate>
							</nested:notEmpty>
						</td>
							
						<td>
							<nested:equal property="btobBillAuth" value="1">&#9898;</nested:equal>
							<nested:notEqual property="btobBillAuth" value="1">&#9932;</nested:notEqual>
							
						</td>
						<td>
							<nested:equal property="overseasInfoAuth" value="1">&#9898;</nested:equal>
							<nested:notEqual property="overseasInfoAuth" value="1">&#9932;</nested:notEqual>
						</td>
						<nested:notEmpty property="mstRulesList">
							<nested:iterate property="mstRulesList" indexId="idx">
								<td>
									<nested:equal property="isvisible" value="1">
										<nested:equal property="isAllcheck" value="1">&#9898;</nested:equal>
										<nested:notEqual property="isAllcheck" value="1">
											<span class="viewChildRule" id="rulesList_${id}_${idx}"> &#9651; 詳細</span>
										</nested:notEqual>
									</nested:equal>
									<nested:notEqual property="isvisible" value="1">&#9932;</nested:notEqual>
								</td>
							</nested:iterate>
						</nested:notEmpty>
						<td>
							<a class="button_main" href="Javascript:void(0);" onclick="goEditUser(<nested:write property="sysUserId"/>, '${id}');">編集</a>
						</td>
						<td>
							<a class="button_white btn-customize" href="Javascript:void(0);" onclick="goDelUser(<nested:write property="sysUserId"/>);">削除</a>
						</td>
					</tr>
					<!-- 個々のユーザー権限の編集 -->
					<tr class="edit_row_${id}" style="display:none;">
						<td ondblclick="goDetailUser(<nested:write  property="sysUserId"/>);" style="cursor:pointer">
							<nested:write  property="userFamilyNmKanji"/>
							<nested:write  property="userFirstNmKanji"/>
							<nested:hidden property="sysUserId" styleId="sysUserIdx_${id}"/>
						</td>
						<td><div class="justify-content-arround">
							<nested:notEmpty property="mstMasterList">
								<nested:iterate property="mstMasterList" indexId="idx">
									<nested:hidden property="isvisible" styleClass="hidden_mastareItemCheckFlg"/>
									<nested:checkbox property="isvisible" styleClass="mastareItemCheckFlg checkBoxClass listCheck"></nested:checkbox>
									<span class="editChildRule" id="editMasterList_${id}_${idx}"> 詳細</span>
									<nested:hidden property="childrenMasterCheckedFlag" styleClass="hidden_mastareItemCheckFlg"/>
								</nested:iterate>
							</nested:notEmpty>
						</div></td>
						<td>
							<nested:hidden property="btobBillAuth" styleClass="hidden_btobItemCheckFlg"/>
							<nested:checkbox property="btobBillAuth" styleClass="btobItemCheckFlg checkBoxClass"/>
						</td>
						<td>
							<nested:hidden property="overseasInfoAuth" styleClass="hidden_infoAuthItemCheckFlg"/>
							<nested:checkbox property="overseasInfoAuth" styleClass="infoAuthItemCheckFlg checkBoxClass"/>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<td class="itemColumn"><div class="justify-content-arround">
								<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${id}_${idx}"/>
								<nested:checkbox property="isvisible" styleClass="visibleFlag_${id}_${idx} checkBoxClass listCheck"></nested:checkbox>
								<nested:notEqual property="childCount" value="0">
									<nested:notEqual property="childCount" value="1">
										<span class="editChildRule" id="editRulesList_${id}_${idx}"> 詳細</span>
										<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
										<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_visibleFlag_${id}_${idx}"/>
									</nested:notEqual>
								</nested:notEqual>
							</div></td>
						</nested:iterate>
						<td>
							<a class="button_main" href="Javascript:void(0);" onclick="goUpdateExtraUserRule(<nested:write  property="sysUserId"/>);">登録</a>
						</td>
						<td>
							<a class="button_white btn-customize" href="Javascript:void(0);" onclick="goDelUser(<nested:write  property="sysUserId"/>);">削除</a>
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
						<td><div class="justify-content-arround">
							<nested:notEmpty property="mstMasterList">
								<nested:iterate property="mstMasterList" indexId="idx">
									<nested:hidden property="isvisible" styleClass="hidden_mastareItemCheckFlg"/>
									<nested:checkbox property="isvisible" styleClass="mastareItemCheckFlg checkBoxClass listCheck"></nested:checkbox>
									<span class="editChildRule" id="editMasterList_${id}_${idx}"> 詳細</span>
									<nested:hidden property="childrenMasterCheckedFlag" styleClass="hidden_mastareItemCheckFlg"/>
								</nested:iterate>
							</nested:notEmpty>
						</div></td>
						<td>
							<nested:hidden property="btobBillAuth" styleClass="hidden_btobItemCheckFlg"/>
							<nested:checkbox property="btobBillAuth" styleClass="btobItemCheckFlg checkBoxClass"/>
						</td>
						<td>
							<nested:hidden property="overseasInfoAuth" styleClass="hidden_infoAuthItemCheckFlg"/>
							<nested:checkbox property="overseasInfoAuth" styleClass="infoAuthItemCheckFlg checkBoxClass"/>
						</td>
						<nested:iterate property="mstRulesList" indexId="idx">
							<td class="itemColumn"><div class="justify-content-arround">
								<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${id}_${idx }"/>
								<nested:checkbox property="isvisible" styleClass="visibleFlag_${id}_${idx } checkBoxClass listCheck"></nested:checkbox>
								<nested:notEqual property="childCount" value="0">
									<nested:notEqual property="childCount" value="1">
										<span class="editChildRule" id="editRulesList_${id}_${idx}"> 詳細</span>
										<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
										<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_visibleFlag_${id}_${idx}"/>
									</nested:notEqual>
								</nested:notEqual>
							</div></td>
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
				</nested:equal>
			</ul>
		</div>
	</div>

	</html:form>
	
	<script>
		var seleletElmentId = "";
		window.onload = function() {
			alertTypeValue = document.getElementById('alertType').value;
			if (alertTypeValue == '1') {
				alert('登録しました。\r\n更新を有効にするには、もう一度ログインする必要があります');
			} else if (alertTypeValue == '2') {
				alert('更新しました。\r\n更新を有効にするには、もう一度ログインする必要があります');
			}else if (alertTypeValue == '3') {
				alert('削除しました。');
			}
			
			document.getElementById('alertType').value='0';
			
			if($("#isEditModeAll").val() == 1){
				initCheckBox();
			}
			
			$(document).on('click', '.checkBoxClass', function () {
				$(this).unbind().each(function() {
					var classList = $(this).attr('class').split(' ');
					console.log($(this).parent().find(".hidden_" + classList[0]));
		        	$(this).parent().find(".hidden_" + classList[0]).val(this.checked?1:0);
		        });
			});
			
			$(document).on('click', '.editChildRule', function () {
				$(this).unbind().each(function() {
					seleletElmentId = $(this).attr('id');
					// console.log("seleletElmentId", seleletElmentId);
					$("h4.heading").html("詳細画面");
					$("#tblUserList").css("display","none");
					$(".btnUpdateExtraUserList").css("display","");
					$("#tblExtraUserRuleDetail").css("display","");
					$("#tblExtraUserRuleDetail ."+ seleletElmentId).css("display","");
					
					var arrItem = seleletElmentId.split('_');
					var selectUserId = $(this).parent().parent().parent().find("#sysUserIdx_"+ arrItem[1]).val();
					var selectRuleId = 0;
					$("#sysUserId").val(selectUserId); 
					
					
					var tdElement = $("."+ seleletElmentId).find("td.editCheckBox");
					
					var dynamicColumnsCount = tdElement.length;
					if(arrItem[0] != "editMasterList"){
						selectRuleId = $(this).parent().find("#ruleId_"+ arrItem[1]).val();
					}
					$("#ruleId").val(selectRuleId);
					var checkedCount = 0;
					for(var i = 0; i < dynamicColumnsCount; i++){
						if(tdElement.find(".hidden_visibleFlag_"+i).val() == 1){
							tdElement.find(".visibleFlag_"+i).prop( "checked", true );
							checkedCount++;
						}
						if($("#sysUserId").val() == 2 && i < 2 && arrItem[0] == "editMasterList"){
							
							tdElement.find(".visibleFlag_"+i).attr('disabled', true);
						}
					}
					if(checkedCount == dynamicColumnsCount) $("#allCheck").prop("checked", true);
		        });
			});
			
			$(document).on('click', '#allCheck', function () {
				var tdElement = $("."+ seleletElmentId).find("td.editCheckBox");
				
				var dynamicColumnsCount = tdElement.length;
				
				if(this.checked){
					for(var i = 0; i < dynamicColumnsCount; i++){
						tdElement.find(".hidden_visibleFlag_"+i).val(1);
						tdElement.find(".visibleFlag_"+i).prop( "checked", true );
					}
				}
				else{
					for(var i = 0; i < dynamicColumnsCount; i++){
						tdElement.find(".hidden_visibleFlag_"+i).val(0);
						tdElement.find(".visibleFlag_"+i).prop( "checked", false );
					}
				}
			});
			
			$(document).on('click', '.viewChildRule', function () {
				$(this).unbind().each(function() {
					seleletElmentId = $(this).attr('id');
					$("h4.heading").html("詳細画面");
					$("#tblUserList").css("display","none");
					$(".btnUpdateExtraUserList").css("display","none");
					$("#tblExtraUserRuleDetail").css("display","");
					$("#tblExtraUserRuleDetail ."+ seleletElmentId).css("display","");
					$("#allCheck").css("display", "none");
		        });
			}); 
		};
		
		function saveExtraRuleDetailByUserId(){
			var userId = $("#sysUserId").val();
			var ruleId = $("#ruleId").val();
			var ruleDetailList = [];
			var checkList = $("." + seleletElmentId + " .editCheckBox").find(':hidden');
			var ruleCheck = 0;
			for(var i = 0; i < checkList.size(); i++){
				ruleDetailList.push(checkList[i].value);
				if(checkList[i].value == 1) ruleCheck = 1;
			}
			
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
				
				var arrItemId = seleletElmentId.split('_');
				var targetId = "";
				if(ruleId == 0) targetId = "mastareItemCheckFlg";
				else targetId = "visibleFlag_"+ arrItemId[1] + "_" + arrItemId[2];
				
				$("#allCheck").prop( "checked", false );
				
				if(ruleCheck > 0)
					$("#" + seleletElmentId).parent().find("." + targetId).prop( "checked", true );
				else 
					$("#" + seleletElmentId).parent().find("." + targetId).prop( "checked", false );
				
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
			$("#allCheck").css("display", "");
			$("#allCheck").prop( "checked", false );
			
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
				if($(".edit_row_"+rowIndex).find(".hidden_visibleFlag_"+rowIndex+"_"+j).val() == 1)
					$(".edit_row_"+rowIndex).find(".visibleFlag_"+rowIndex+"_"+j).prop( "checked", true );
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