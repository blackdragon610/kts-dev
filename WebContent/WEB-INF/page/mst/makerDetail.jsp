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
	<link rel="stylesheet" href="./css/maker.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【ユーザーマスタ詳細画面】
ファイル名：makerDetail.jsp
作成日：2016/12/9
作成者：齋藤優太

（画面概要）

メーカー情報の詳細画面
・登録ボタン押下：メーカー情報を登録する。
・更新ボタン押下：メーカー情報を更新する。
・削除ボタン押下：メーカー情報を削除する。

（注意・補足）

-->

	<script type="text/javascript">

	$(function () {

		//メッセージ表示時間指定
		if ($(".registryMessage") != 0) {
			$("#massageArea").fadeOut(2800);
		}

		$(".kanaCheck").blur(function () {
			var kanaStr = this.value;

			if (!kanaStr.match(/^[\uff65-\uff9f\ \　\-\/\(\)]+$/)) {
				alert("半角ｶﾅのみで入力してください。");
				$("#makerNmKana").val("");
				return false;
			}
		})

		//削除ボタン押下時
		$("#delete").click( function () {

			if (!confirm("メーカー情報を削除します、よろしいですか？？")) {
				return;
			}
			goTransaction('deleteMaker.do');
		});

	});

	</script>

	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<html:form action="/initRegistryMaker">

		<nested:nest property="registryDto">
			<nested:notEmpty property="registryMessage">
				<div id="massageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="registryMessage"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>


		<nested:notEqual value="0" property="sysMakerId">
		<h4 class="heading">メーカー情報変更</h4>
		</nested:notEqual>
		<nested:equal value="0" property="sysMakerId">
		<h4 class="heading">メーカー情報登録</h4>
		</nested:equal>

		<html:errors/>
			<nested:nest property="makerDto">
				<table id="mstTable">
					<nested:hidden property="sysMakerId" styleId="sysMakerId"/>
					<nested:notEqual value="0" property="sysMakerId">
						<tr>
							<th>
								メーカーID
							</th>
							<td>
								<nested:write property="sysMakerId"/>
							</td>
						</tr>
					</nested:notEqual>
					<tr>
						<th>
							メーカー名
						</th>
						<td>
							<nested:text property="makerNm"  maxlength="30" styleClass="w200"/>
						</td>
					</tr>
					<tr>
						<th>
							メーカー名ｶﾅ
						</th>
						<td>
							<nested:text property="makerNmKana" styleId="makerNmKana" maxlength="30" styleClass="w200 kanaCheck"/>
						</td>
					</tr>
					<tr>
						<th>
							担当者名
						</th>
						<td>
							<nested:text property="contactPersonNm" styleId="contactPersonNm" maxlength="20" styleClass="w200 "/>
						</td>
					</tr>
				</table>

				<nested:notEqual value="0" property="sysMakerId">
					<div class="footerButtonArea">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('updateMaker.do');">更新</a>
						<a class="button_white" id="delete" href="Javascript:void(0);">削除</a>
					</div>
				</nested:notEqual>

				<nested:equal value="0" property="sysMakerId">
					<div class="footerButtonArea">
						<a class="button_main" href="Javascript:void(0);" onclick="goTransaction('registryMaker.do');">登録</a>
					</div>
				</nested:equal>
			</nested:nest>


		</html:form>

	</body>
</html:html>