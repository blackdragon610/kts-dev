<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/common.css" type="text/css" />
	<link rel="stylesheet" href="./css/rules.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

	<script type="text/javascript">
	$(document).ready(function() {
		$(".overlay").css("display", "none");
		$("fieldset input").val("");
		if($("#alertType").val() == "3") 
			$(".searchOptionField").css("display","");
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

			goTransaction('deleteRuleList.do');
		});
		
		$(".addRuleListItem").click(function () {
			$(".searchOptionField").css("visibility","visible");
		});
	});

	var isAddData = true;
	//メーカー情報ダブルクリック時
	function editRuleList(id){
		
		$("#ruleListId").val(id);

		$.ajax({
			url : "./initUpdateRuleList.do",
			type : 'POST',
			data : {'ruleListId': id},
			dataType : 'json',
			success : function(data, text_status, xhr) {
				$(".searchOptionField").css("display","");
				$("legend").html("情報変更");
				$("#input_name").val(data[0].listName);
				$("#input_id").val(data[0].listId);
				$("#input_pass").val(data[0].listPass);
				$("#input_remarks").val(data[0].listRemarks);
				$("#input_link").val(data[0].listLink);
				isAddData = false;
			},
			error : function(data, text_status, xhr) {
				alert("資料取得に失敗");
			}
		});
	}
	
	function copyClipBoard(txt, id){
		var $temp = $("<input>");
	    $("body").append($temp);
	    $temp.val(txt).select();
	    document.execCommand("copy");
	    $temp.remove();
	    console.log(this);
	    $("#"+id).html("コピー成功");
	}
	
	function registHandle(){
		if(isAddData) goTransaction('registryRuleList.do');
		else goTransaction('updateRuleList.do');
	}
	</script>

	</head>

	<body>
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
		<html:form action="/detailRule">
			<h4 class="heading">ID・PASS一覧</h4>
	
			<nested:nest property="registryDto">
				<nested:notEmpty property="registryMessage">
					<div id="massageArea">
						<p class="registryMessage" style="text-align: leght;"><nested:write property="registryMessage"/></p>
					</div>
				</nested:notEmpty>
			</nested:nest>
	
			<html:errors/>
			<fieldset class="searchOptionField pdg_top_10px pdg_bottom_10px" style="display: none;">
				<legend>新規追加</legend>
				<nested:nest property="ruleDetailDTO">
					<table class="list justify-content-center">
						<tr>
							<th class="w220">名称</th>
							<th class="w100">ID</th>
							<!-- <th class="w200">コピー</th> -->
							<th class="w200">PASS</th>
							<th class="w200">備考</th>
							<th class="w220">リンク</th>
						</tr>
						<tr ondblclick="goDetailRule(<nested:write  property="ruleListId"/>);">
							<td><nested:text property="listName" styleId="input_name"/></td>
							<td><nested:text property="listId" styleId="input_id"/></td>
							<td><nested:password property="listPass" styleId="input_pass"/></td>
							<td><nested:text property="listRemarks" styleId="input_remarks"/></td>
							<td><nested:text property="listLink" styleId="input_link"/></td>
							
						</tr>
					</table>
					<div class="footerButtonArea txt-right">
						<a class="button_main" href="Javascript:void(0);" onclick="registHandle();">登録</a>
					</div>
				</nested:nest>
			</fieldset>
		
	
			<div class="buttonArea">
				<ul style="position: relative;">
					<li class="footer_button">
						<a class="button_white btn-customize" href="javascript:void(0);" onclick="goTransaction('ruleList.do');">戻る</a>
					</li>
					<li class="footer_button">
						<a class="button_main addRuleListItem" href="javascript:void(0);">新規追加</a>
					</li>
					<li class="footer_button">
						<a class="button_white btn-customize deleteRuleListItem" href="javascript:void(0);">選択した分類を削除</a>
					</li>
				</ul>
			</div>
			
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
						<td><a class="button_main" href="javascript:void(0);" onclick="editRuleList(<nested:write  property="ruleListId"/>);">編集</a></td>
					</tr>
				</nested:iterate>
			</table>
		</html:form>
	</body>
</html:html>