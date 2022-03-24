<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />
	<link rel="stylesheet" href="./css/common.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>


	<script type="text/javascript">
	$(document).ready(function() {
		$(".overlay").css("display", "none");
		$("fieldset input").val("");
		if($("#alertType").val() == "3") 
			$(".searchOptionField").css("visibility","visible");
	});

	$(function () {
		//メッセージ表示時間指定
		if ($(".registryMessage") != 0) {
			$("#massageArea").fadeOut(2800);
		}
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
		
		$(".addRuleItem").click(function () {
			$(".searchOptionField").css("visibility","visible");
		});
		
	});

	var isAddData = true;
	//メーカー情報ダブルクリック時
	function goDetailRule(value){
		$("#ruleId").val(value);
		goTransaction('detailRule.do');
	}
	
	function editRule(id, name){
		$("#ruleId").val(id);
		$(".searchOptionField").css("visibility","visible");
		$("#input_name").val(name);
		$("legend").html("情報変更");
		isAddData = false;
	}
	
	function registHandle(){
		if(isAddData) goTransaction('registryRule.do');
		else goTransaction('updateRule.do');
	}
	
	</script>
	</head>
	
	<div class="overlay">
		<div class="messeage_box">
			<h1 class="message">読み込み中</h1>
			<BR />
			<p>Now Loading...</p>
			<img src="./img/load.gif" alt="loading"></img>
			<BR />
			<BR />
			<BR />
		</div>
	</div>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	
	<h4 class="heading">ID・PASS一覧</h4>
	
	<html:form action="/ruleList">

		<nested:nest property="registryDto">
			<nested:notEmpty property="registryMessage">
				<div id="massageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="registryMessage"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>

		<html:errors/>
			<fieldset class="searchOptionField w-50 pdg_top_10px pdg_bottom_10px ms-20" style="visibility: hidden;">
				<legend>新規追加</legend>
				<nested:nest property="ruleDTO">
					<div class="justify-content-arround">
						<div>
							<%-- <nested:hidden property="ruleId" styleId="ruleId"/> --%>
							<label class="">分類</label>
							<nested:text property="ruleName" styleId="input_name"  maxlength="30" styleClass="w200 pdg_top_5px pdg_bottom_5px ms-20"/>
						</div>
						<div>
							<div class="footerButtonArea">
								<a class="button_main" href="Javascript:void(0);" onclick="registHandle();">登録</a>
							</div>
						</div>
					</div>
				</nested:nest>
			</fieldset>
		
	
		<div class="buttonArea">
			<ul style="position: relative;">
			<li class="footer_button">
					<a class="button_main addRuleItem" href="javascript:void(0);">新規追加</a>
				</li>
				<!-- <li class="footer_button">
					<a class="button_main editRuleItem" href="javascript:void(0);">編集</a>
				</li> -->
				<li class="footer_button">
					<a class="button_white deleteRuleListItem" href="javascript:void(0);">選択した分類を削除</a>
				</li>
			</ul>
		</div>
		
		<html:hidden property="ruleId"  styleId="ruleId"/>
		<html:hidden property="alertType"  styleId="alertType"/>
		<table id="mstTable" class="list">
			<tr>
				<th class="td_center allDelCheckSize"><input type="checkbox" id="allDelCheck"class="allDelCheck checkBoxTransForm"></th>
				<th class="w200">分類</th>
				<th></th>
			</tr>
			<nested:iterate property="ruleList" indexId="idx">
				<tr ondblclick="goDetailRule(<nested:write  property="ruleId"/>);">
					<td class="count salesSlipRow td_center">
						<nested:checkbox property="itemCheckFlg" styleClass="itemCheckFlg checkBoxTransForm"/>
						<nested:hidden property="itemCheckFlg" value="off"/>
					</td>
					<td><nested:write property="ruleName" /></td>
					<td><a class="button_main" href="javascript:void(0);" onclick="editRule(<nested:write  property="ruleId"/>, '<nested:write property="ruleName" />');">編集</a></td>
				</tr>
			</nested:iterate>
		</table>
		
	</html:form>
</html:html>