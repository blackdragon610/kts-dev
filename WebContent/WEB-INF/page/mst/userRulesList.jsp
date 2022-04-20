<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>



	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/updateAllUserList">
	<nested:hidden property="alertType" styleId="alertType"/>
	<h4 class="heading">詳細画面</h4>

	<nested:hidden property="sysUserId" styleId="sysUserId"/>
	<nested:hidden property="ruleId" styleId="ruleId"/>
	<nested:hidden property="selectRowId" styleId="selectRowId"/>
	<nested:hidden property="isEditModeAll" styleId="isEditModeAll"/>
	<nested:hidden property="isEditModeSingle" styleId="isEditModeSingle"/>
	<!-- ユーザー権限に属するリンクの編集 -->	
	<div id="tblExtraUserRuleDetail">
	
		<table id="mstTable">
			<tr>
				<th class="w150">名称</th>
				<th class="td_center allCheckSize"><input type="checkbox" id="allCheck" class="allCheck"></th>
			</tr>
			<nested:equal value="0" property="isEditModeSingle">
				<nested:iterate property="rdetailList" indexId="id">
					<tr class="rulesList_${id}">
						<td>
							<nested:write property="listName" />
						</td>
						<td>
							<nested:equal property="isvisible" value="1">&#9898;</nested:equal>
							<nested:notEqual property="isvisible" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
				</nested:iterate>
			</nested:equal>
			<nested:equal value="1" property="isEditModeSingle">
				<nested:iterate property="rdetailList" indexId="id">
					<tr class="editRulesList">
						<td>
							<nested:write property="listName" />
						</td>
						<td class="editCheckBox">
							<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${id}"/>
							<nested:checkbox property="listVisible" styleClass="visibleFlag_${id} checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
				</nested:iterate>
			</nested:equal>
			<nested:equal value="2" property="isEditModeSingle">
				<nested:iterate property="mdetailList" indexId="id">
					<tr class="masterList_${id}">
						<td><nested:write property="userListName" /></td>
						<td>
							<nested:equal property="userListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="userListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="ruleListName" /></td>
						<td>
							<nested:equal property="ruleListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="ruleListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="corporationListName" /></td>
						<td>
							<nested:equal property="corporationListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="corporationListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="accountListName" /></td>
						<td>
							<nested:equal property="accountListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="accountListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="channelListName" /></td>
						<td>
							<nested:equal property="channelListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="channelListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="warehouseListName" /></td>
						<td>
							<nested:equal property="warehouseListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="warehouseListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="makerListName" /></td>
						<td>
							<nested:equal property="makerListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="makerListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="setItemListName" /></td>
						<td>
							<nested:equal property="setItemListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="setItemListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="clientListName" /></td>
						<td>
							<nested:equal property="clientListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="clientListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					<tr class="masterList_${id}">
						<td><nested:write property="deliveryListName" /></td>
						<td>
							<nested:equal property="deliveryListFlg" value="1">&#9898;</nested:equal>
							<nested:notEqual property="deliveryListFlg" value="1">&#9932;</nested:notEqual>
						</td>
					</tr>
					
					
					
				</nested:iterate>
			</nested:equal>
			<nested:equal value="3" property="isEditModeSingle">
				<nested:iterate property="mdetailList" indexId="id">
					<tr class="editRulesList">
						<td> <nested:write property="userListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="userListFlg" styleClass="hidden_visibleFlag_0"/>
							<nested:checkbox property="userListFlg" styleClass="visibleFlag_0 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="ruleListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="ruleListFlg" styleClass="hidden_visibleFlag_1"/>
							<nested:checkbox property="ruleListFlg" styleClass="visibleFlag_1 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="corporationListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="corporationListFlg" styleClass="hidden_visibleFlag_2"/>
							<nested:checkbox property="corporationListFlg" styleClass="visibleFlag_2 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="accountListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="accountListFlg" styleClass="hidden_visibleFlag_3"/>
							<nested:checkbox property="accountListFlg" styleClass="visibleFlag_3 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="channelListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="channelListFlg" styleClass="hidden_visibleFlag_4"/>
							<nested:checkbox property="channelListFlg" styleClass="visibleFlag_4 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="warehouseListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="warehouseListFlg" styleClass="hidden_visibleFlag_5"/>
							<nested:checkbox property="warehouseListFlg" styleClass="visibleFlag_5 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="makerListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="makerListFlg" styleClass="hidden_visibleFlag_6"/>
							<nested:checkbox property="makerListFlg" styleClass="visibleFlag_6 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="setItemListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="setItemListFlg" styleClass="hidden_visibleFlag_7"/>
							<nested:checkbox property="setItemListFlg" styleClass="visibleFlag_7 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="clientListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="clientListFlg" styleClass="hidden_visibleFlag_8"/>
							<nested:checkbox property="clientListFlg" styleClass="visibleFlag_8 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
					<tr class="editRulesList">
						<td> <nested:write property="deliveryListName" /></td>
						<td class="editCheckBox">
							<nested:hidden property="deliveryListFlg" styleClass="hidden_visibleFlag_9"/>
							<nested:checkbox property="deliveryListFlg" styleClass="visibleFlag_9 checkBoxClass"></nested:checkbox>
							
						</td>
					</tr>
				</nested:iterate>
			</nested:equal>
			
		</table>

		<div class="update_detailUserButton">
			<a class="button_main btnUpdateExtraUserList" href="Javascript:void(0);" onclick="saveExtraRuleDetailByUserId()">更新</a>
			<a class="button_white btn-customize" href="Javascript:void(0);" onclick="onclickBack()">戻る</a>
		</div>
	</div>
	
	</html:form>
	
	<script>
		window.onload = function() {
			if($("#isEditModeSingle").val() == 0 ||$("#isEditModeSingle").val() == 2 ){
				$("#allCheck").css("display", "none");
			}
			else{
				initCheckBox();
			}
			$(document).on('click', '.checkBoxClass', function () {
				$(this).unbind().each(function() {
					var classList = $(this).attr('class').split(' ');
		        	$(this).parent().find(".hidden_" + classList[0]).val(this.checked?1:0);
		        });
				calcCheckCount();
			});
			
			setCheckBoxDisable();
			
			$(document).on('click', '#allCheck', function () {
				var tdElement = $("td.editCheckBox");
				
				var dynamicColumnsCount = tdElement.length;
				
				if(this.checked){
					for(var i = 0; i < dynamicColumnsCount; i++){
						tdElement.find(".hidden_visibleFlag_"+i).val(1);
						tdElement.find(".visibleFlag_"+i).prop( "checked", true );
						if($("#sysUserId").val() == 2 && i < 2 && $("#isEditModeSingle").val() == 3){
							tdElement.find(".hidden_visibleFlag_"+i).val(1);
							tdElement.find(".visibleFlag_"+i).prop( "checked", true );
						}
					}
				}
				else{
					for(var i = 0; i < dynamicColumnsCount; i++){
						tdElement.find(".hidden_visibleFlag_"+i).val(0);
						tdElement.find(".visibleFlag_"+i).prop( "checked", false );
						if($("#sysUserId").val() == 2 && i < 2 && $("#isEditModeSingle").val() == 3){
							tdElement.find(".hidden_visibleFlag_"+i).val(1);
							tdElement.find(".visibleFlag_"+i).prop( "checked", true );
						}
					}
				}
			});
		};
		
		function setCheckBoxDisable(){
			var tdElement = $("td.editCheckBox");
			
			var dynamicColumnsCount = tdElement.length;
			
			for(var i = 0; i < dynamicColumnsCount; i++){
				if($("#sysUserId").val() == 2 && i < 2 && $("#isEditModeSingle").val() == 3){
					$(".visibleFlag_"+i).attr('disabled', true);
				}
			}
		}
		
		function saveExtraRuleDetailByUserId(){
			var userId = $("#sysUserId").val();
			var ruleId = $("#ruleId").val();
			var ruleDetailList = [];
			var checkList = $(".editCheckBox").find(':hidden');
			for(var i = 0; i < checkList.size(); i++){
				ruleDetailList.push(checkList[i].value);
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
				if($("#isEditModeAll").val() == 0)
					goTransaction("returnUserList.do");
				else
					goTransaction("editAllUserList.do");
			}).fail(function(data) {
				
			});
			return;
		}
		
		function onclickBack(){
			if($("#isEditModeAll").val() == 0)
				goTransaction("returnUserList.do");
			else
				goTransaction("editAllUserList.do");
		}
		
		function initCheckBox(){
			var resultArea = $('tr.editRulesList');
			var checkCount = 0;
			for (var i = 0; i < resultArea.size(); i++){
				if(resultArea.eq(i).find(".hidden_visibleFlag_"+i).val() == 1){
					resultArea.eq(i).find(".visibleFlag_"+i).prop( "checked", true );
					checkCount++;
				}
			}
			if(resultArea.size() == checkCount)
				$("#allCheck").prop("checked", true);
		}
		
		function calcCheckCount(){
			
			var resultArea = $('tr.editRulesList');
			var checkCount = 0;
			for (var i = 0; i < resultArea.size(); i++){
				if(resultArea.eq(i).find(".hidden_visibleFlag_"+i).val() == 1){
					checkCount++;
				}
			}
			if(resultArea.size() == checkCount)
				$("#allCheck").prop("checked", true);
			else
				$("#allCheck").prop("checked", false);
		}
		
	</script>
</html:html>