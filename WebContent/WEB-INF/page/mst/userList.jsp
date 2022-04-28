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
			
			if(($("#isEditModeSingle").val() == 1 || $("#isEditModeSingle").val() == 3 ) && $("#isEditModeAll").val() == 0 && $("#selectRowId").val() != -1){
				var rowIndex = $("#selectRowId").val();
				goEditUser($("#sysUserId").val(), rowIndex);
			}
			
			$(document).on('click', '.checkBoxClass', function () {
				$(this).unbind().each(function() {
					var classList = $(this).attr('class').split(' ');
		        	$(this).parent().find(".hidden_" + classList[0]).val(this.checked?1:0);
		        });
			});
			
		};
		
		function viewChildRule(rowId,ruleId){
			$("#selectRowId").val(rowId);
			$("#ruleId").val(ruleId);
			var selectUserId = $("#sysUserIdx_"+ rowId).val();
			$("#sysUserId").val(selectUserId);
			$("#isEditModeSingle").val(0);
			goTransaction('editDetailList.do');
		}
		function editChildRule(rowId, ruleId){
			$("#selectRowId").val(rowId);
			$("#ruleId").val(ruleId);
			var selectUserId = $("#sysUserIdx_"+ rowId).val();
			$("#sysUserId").val(selectUserId);
			$("#isEditModeSingle").val(1);
			goTransaction('editDetailList.do');
		}
		function viewChildMaster(rowId){
			$("#selectRowId").val(rowId);
			var selectUserId = $("#sysUserIdx_"+ rowId).val();
			$("#sysUserId").val(selectUserId);			
			$("#isEditModeSingle").val(2);
			$("#ruleId").val(0);
			goTransaction('editDetailList.do');
		}
		function editChildMaster(rowId){
			$("#selectRowId").val(rowId);
			$("#isEditModeSingle").val(3);
			var selectUserId = $("#sysUserIdx_"+ rowId).val();
			$("#sysUserId").val(selectUserId);
			$("#ruleId").val(0);
			goTransaction('editDetailList.do');
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

	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/updateAllUserList">
	<nested:hidden property="alertType" styleId="alertType"/>
	<h4 class="heading">ユーザー一覧</h4>

	<nested:hidden property="sysUserId" styleId="sysUserId"/>
	<nested:hidden property="isEditModeAll" styleId="isEditModeAll"/>
	<nested:hidden property="ruleId" styleId="ruleId"/>
	<nested:hidden property="selectRowId" styleId="selectRowId"/>
	<nested:hidden property="isEditModeSingle" styleId="isEditModeSingle"/>
	<!-- ユーザー権限に属するリンクの編集 -->	

	<!-- ユーザー権限の表示 -->
	<div id="tblUserList">
		<table id="mstTable" class="user-list">
			<tr>
				<th>ユーザー名</th>
				<th>マスタ</th>
				<th>法人間請求権限</th>
				<th>海外情報閲覧権限</th>
				<!-- <th>ID・PASS削除権限</th> -->
				<nested:iterate property="ruleList" indexId="idx">
					<nested:notEqual property="childCount" value="0">
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
						<td>
							<nested:notEmpty property="mstMasterList">
								<nested:iterate property="mstMasterList" indexId="idx">
								
									<nested:equal property="isvisible" value="1">
										<nested:equal property="viewableCount" value="1">&#9898;</nested:equal>
										<nested:notEqual property="viewableCount" value="1">
											<nested:equal property="viewableCount" value="10">&#9898;</nested:equal>
											<nested:notEqual property="viewableCount" value="10">
											<span onclick="viewChildMaster(${id })" id="masterList_${id}_${idx}"> &#9651; 詳細</span>
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
						<%-- <td>
							<nested:equal property="idPassDelAuth" value="1">&#9898;</nested:equal>
							<nested:notEqual property="idPassDelAuth" value="1">&#9932;</nested:notEqual>
						</td> --%>
						<nested:notEmpty property="mstRulesList">
							<nested:iterate property="mstRulesList" indexId="idx">
								<nested:notEqual property="childCount" value="0">
								<td>
									<nested:notEqual property="viewableChildCount" value="0">
									
										<nested:equal property="isAllcheck" value="1">&#9898;</nested:equal>
										<nested:notEqual property="isAllcheck" value="1">
											<span onclick="viewChildRule(${id},<nested:write property='ruleId'/>)" id="rulesList_${id}_${idx}"> &#9651; 詳細</span>
										</nested:notEqual>
									</nested:notEqual>
									<nested:equal property="viewableChildCount" value="0">&#9932;</nested:equal>
								</td>
								</nested:notEqual>
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
									<span onclick="editChildMaster(${id })" id="editMasterList_${id}_${idx}"> 詳細</span>
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
						<%-- <td>
							<nested:hidden property="idPassDelAuth" styleClass="hidden_idPassDelItemCheckFlg"/>
							<nested:checkbox property="idPassDelAuth" styleClass="idPassDelItemCheckFlg checkBoxClass"/>
						</td> --%>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:notEqual property="childCount" value="0">
							<td class="itemColumn"><div class="justify-content-arround">
								<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${id}_${idx}"/>
								<nested:checkbox property="isvisible" styleClass="visibleFlag_${id}_${idx} checkBoxClass listCheck"></nested:checkbox>
								<nested:notEqual property="childCount" value="0">
									<nested:notEqual property="childCount" value="1">
										<span onclick="editChildRule(${id },<nested:write property='ruleId'/>)" id="editRulesList_${id}_${idx}"> 詳細</span>
										<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
										<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_visibleFlag_${id}_${idx}"/>
									</nested:notEqual>
								</nested:notEqual>
							</div></td>
							</nested:notEqual>
							<nested:equal property="childCount" value="0">
								<td class="itemColumn" style="display:none;"></td>
							</nested:equal>
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
									<span onclick="editChildMaster(${id })" id="editMasterList_${id}_${idx}"> 詳細</span>
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
						<%-- <td>
							<nested:hidden property="idPassDelAuth" styleClass="hidden_idPassDelItemCheckFlg"/>
							<nested:checkbox property="idPassDelAuth" styleClass="idPassDelItemCheckFlg checkBoxClass"/>
						</td> --%>
						<nested:iterate property="mstRulesList" indexId="idx">
							<nested:notEqual property="childCount" value="0">
							<td class="itemColumn"><div class="justify-content-arround">
								<nested:hidden property="isvisible" styleClass="hidden_visibleFlag_${id}_${idx }"/>
								<nested:checkbox property="isvisible" styleClass="visibleFlag_${id}_${idx } checkBoxClass listCheck"></nested:checkbox>
								<nested:notEqual property="childCount" value="0">
									<nested:notEqual property="childCount" value="1">
										<span onclick="editChildRule(${id},<nested:write property='ruleId'/>)" id="editRulesList_${id}_${idx}"> 詳細</span>
										<nested:hidden property="ruleId" styleId="ruleId_${id}"/>
										<nested:hidden property="childrenRuleCheckedFlag" styleClass="hidden_visibleFlag_${id}_${idx}"/>
									</nested:notEqual>
								</nested:notEqual>
							</div></td>
							</nested:notEqual>
							<nested:equal property="childCount" value="0">
								<td class="itemColumn" style="display:none;"></td>
							</nested:equal>
						</nested:iterate>
					</tr>
				</nested:iterate>
			</nested:equal>
		</table>
		<div class="bottomArea">
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
	
	
</html:html>