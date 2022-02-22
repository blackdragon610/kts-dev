<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
<!-- 	<script type="text/javascript" src="./js/prototype.js"></script> -->
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>
<!--
【法人マスタ詳細画面】
ファイル名：detailCorporation.jsp
作成日：2014/12/19
作成者：八鍬寛之

（画面概要）

法人情報の詳細画面
ここの情報が納品書に出力される。
法人ごとに出力する項目が異なる(テーブルMST_STORE参照)。


・更新ボタン押下：法人情報を更新する。
・削除ボタン押下：法人情報を削除する。

（注意・補足）

-->

	<SCRIPT type="text/javascript">
	window.onload = function() {
		actAlert(document.getElementById('alertType').value);
		document.getElementById('alertType').value='0';
	}

	$(function() {
		//法人掛け率
		$(".rateOver").blur(function () {

			if($(this).val()==""){
				$(this).val(0);
				return;
			}
			if (!numberFloatCheck(this)){
				$(this).val(0);
				return;
			}
			var numList = $(this).val().split(".");
			if(numList[0].length > 2 || numList[1].length > 2){
				alert("法人掛け率の桁数が多いです。");
				$(this).val(0);
				return;
			}
		});

	})
	</SCRIPT>
	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<html:form action="/updateCorporation">
		<nested:notEqual value="0" property="corporationDTO.sysCorporationId">
		<h4 class="heading">会社情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="corporationDTO.sysCorporationId">
		<h4 class="heading">会社情報登録</h4>
		</nested:equal>

		<nested:hidden property="alertType" styleId="alertType"></nested:hidden>

			<nested:nest property="corporationDTO">
				<table id="mstTable">
					<tr>
						<th>
							会社名
						</th>
						<td>
							<nested:text property="corporationNm" styleClass="w300" maxlength="50" />
						</td>
					</tr>
					<tr>
						<th>略称</th>
						<td>
							<nested:text property="abbreviation" styleClass="w300" maxlength="20" />
						</td>
					</tr>
					<tr>
						<th>
							郵便番号
						</th>
						<td>
							<nested:text property="zip" styleClass="w100" maxlength="8" />
						</td>
					</tr>
					<tr>
						<th>
							住所
						</th>
						<td>
							<nested:text property="address" styleClass="w300" maxlength="150" />
						</td>
					</tr>
					<tr>
						<th>
							電話番号
						</th>
						<td>
							<nested:text property="telNo" styleClass="w300" maxlength="13" />
						</td>
					</tr>
					<tr>
						<th>
							FAX番号
						</th>
						<td>
							<nested:text property="faxNo" styleClass="w300" maxlength="13" />
						</td>
					</tr>
					<tr>
						<th>
							メールアドレス
						</th>
						<td>
							<nested:text property="mailAddress" styleClass="w300" maxlength="256" />
						</td>
					</tr>
					<tr>
						<th>
							法人掛け率
						</th>
						<td>
							<nested:text property="corporationRateOver" styleClass="w100 rateOver" maxlength="5" />&nbsp;％
						</td>
					</tr>
				</table>

			</nested:nest>
			<div class="update_detailCorporetionButton">
			<nested:notEqual value="0" property="corporationDTO.sysCorporationId">
				<div class="update_detailUserButton">
<!-- 					<input type="image" src="./img/delete.gif" class="btn_submit" onmouseover="this .src='./img/delete_over.gif'" onmouseout="this .src='./img/delete.gif'"onclick="goTransaction('deleteCorporation.do');" align="middle"/> -->
<!-- 					<input type="image" src="./img/update.gif" class="btn_submit" onmouseover="this .src='./img/update_over.gif'" onmouseout="this .src='./img/update.gif'"onclick="goTransaction('updateCorporation.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateCorporation.do');">更新</a>
					<a class="button_white" href="Javascript:void(0);" onclick="goTransaction('deleteCorporation.do');">削除</a>
				</div>
			</nested:notEqual>
			<nested:equal value="0" property="corporationDTO.sysCorporationId">
				<div class="registry_detailCorporetionButton">
<!-- 					<input type="image" src="./img/registry.gif" class="btn_submit" onmouseover="this .src='./img/registry_over.gif'" onmouseout="this .src='./img/registry.gif'"onclick="goTransaction('registryCorporation.do');" align="middle"/> -->
					<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryCorporation.do');">登録</a>
				</div>
			</nested:equal>
			</div>
		</html:form>

	</body>
</html:html>