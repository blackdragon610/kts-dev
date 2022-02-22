<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<title>仕入先詳細</title>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/mst.css" type="text/css" />
	<link rel="stylesheet" href="./css/detailSupplier.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" type="text/javascript" />
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【仕入先マスタ詳細画面】
ファイル名：detailSupplier.jsp
作成日：2016/11/14
作成者：白井崇詞

（画面概要）

仕入先情報の詳細画面

・登録ボタン押下:仕入先情報を登録する。
・更新ボタン押下：仕入先情報を更新する。
・削除ボタン押下：仕入先情報を削除する。

（注意・補足）

-->


	</head>

	<body>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
		<html:form action="/registrySupplier">
		<html:hidden property="messageFlg" styleId="messageFlgForm"/>
		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: center;"><nested:write property="message" /></p>
				</div>
			</nested:notEmpty>
		</nested:nest>


			<nested:notEqual value="0" property="supplierDTO.sysSupplierId">
				<h1 class="heading">海外仕入先情報詳細</h1>
			</nested:notEqual>
			<nested:equal value="0" property="supplierDTO.sysSupplierId">
				<h1 class="heading">海外仕入先情報登録</h1>
			</nested:equal>

			<nested:notEmpty property="errorDTO">
				<nested:nest property="errorDTO">
					<nested:notEmpty property="errorMessage">
						<p id="errorMessage"><nested:write property="errorMessage" /></p>
					</nested:notEmpty>
				</nested:nest>
			</nested:notEmpty>

			<html:errors/>
			<nested:nest property="supplierDTO">
				<table id="mstTable" class="supplierTable">
					<nested:notEqual value="0" property="sysSupplierId">
						<tr>
							<th>仕入先ID<label class="necessary">※</label></th>
							<td colspan="4" style="text-align : left"><nested:write property="sysSupplierId"/></td>
							<nested:hidden property="sysSupplierId" styleId="sysSupplierId"/>
						</tr>
					</nested:notEqual>

					<tr>
						<th>仕入先番号<label class="necessary">※</label></th>
						<td colspan="4" style="text-align : left"><nested:text property="supplierNo" styleId="supplierNo" maxlength="30" styleClass="numText"/></td>
					</tr>
					<tr>
						<th>会社・工場名<label class="necessary">※</label></th>
						<td colspan="4" style="text-align : left"><nested:text property="companyFactoryNm" styleClass="text_w550" styleId="companyFactoryNm" maxlength="50"/></td>
					</tr>
					<tr>
						<th>PoNo.頭文字<label class="necessary">※</label></th>
						<td colspan="4" style="text-align : left"><nested:text property="poNoInitial" styleClass="alphanumeric" styleId="poNoInitial" maxlength="3"/> 3文字まで</td>
					</tr>
					<tr>
						<th>国</th>
						<td colspan="4" style="text-align : left"><nested:text property="country" styleClass="text_w550" styleId="country"/></td>
					</tr>
					<tr>
						<th>住所</th>
						<td colspan="4" style="text-align : left"><nested:text property="address" styleClass="text_w550" styleId="address"/></td>
					</tr>
					<tr>
						<th>TEL</th>
						<td colspan="4" style="text-align : left"><nested:text property="tel" maxlength="25" styleId="tel" styleClass="w210"/>  ハイフンも記入する</td>
					</tr>
					<tr>
						<th>FAX</th>
						<td colspan="4" style="text-align : left"><nested:text property="fax" maxlength="25" styleId="fax" styleClass="w210"/>  ハイフンも記入する</td>
					</tr>
					<tr>
						<th>担当者</th>
						<td colspan="4" style="text-align : left"><nested:text property="contactPersonNm" maxlength="20" styleId="contactPersonNm"/></td>
					</tr>
					<tr>
						<th>E-mail</th>
						<td colspan="4" style="text-align : left"><nested:text property="mailAddress" maxlength="256" styleClass="text_w550" styleId="mailAddress"/></td>
					</tr>
					<tr>
						<th>貿易条件</th>
						<td colspan="4" style="text-align : left"><nested:text property="tradeTerms" maxlength="20" styleId="tradeTerms"/></td>
					</tr>
					<tr>
						<th>支払条件1</th>
						<td>
							<nested:text property="paymentTerms1" styleId="paymentTerms1" styleClass="numText" maxlength="3"/>
							 ％
						</td>
						<th>支払条件2</th>
						<td>
							<nested:text property="paymentTerms2" styleId="paymentTerms2" styleClass="numText" maxlength="3"/>
							％
						</td>
					</tr>
					<tr>
						<th>取引通貨</th>
						<td colspan="4" style="text-align : left">
							<nested:select property="currencyId" styleId="currencyId">
								<html:option value=""></html:option>
								<html:optionsCollection property="currencyList" label="currencyType" value="currencyId"/>
							</nested:select>
							&nbsp;
							<input type="text" name="currencyRate" size="10" readonly/>
						</td>
					</tr>
					<tr>
						<th>仕入先リードタイム</th>
						<td colspan="4" style="text-align : left">
							<nested:select property="leadTime" styleId="leadTime" styleClass="leadTime">
								<html:optionsCollection property="leadTimeMap" label="value" value="key"/>
							</nested:select>
						</td>
					</tr>
					<tr>
						<th colspan="4">銀行口座情報</th>
					</tr>
					<tr>
						<th>銀行名</th>
						<td colspan="4" style="text-align : left"><nested:text property="bankNm" styleClass="text_w550" styleId="bankNm"/></td>
					</tr>
					<tr>
						<th>支店名</th>
						<td colspan="4" style="text-align : left"><nested:text property="branchNm" styleClass="text_w550" styleId="branchNm"/></td>
					</tr>
					<tr>
						<th>住所</th>
						<td colspan="4" style="text-align : left"><nested:text property="bankAddress" styleClass="text_w550" styleId="bankAddress"/></td>
					</tr>
					<tr>
						<th>SWIFT CODE</th>
						<td colspan="4" style="text-align : left"><nested:text property="swiftCode" styleClass="swiftCode" maxlength="20" styleId="swiftCode"/></td>
					</tr>
					<tr>
						<th>ACCOUNT No</th>
						<td colspan="4" style="text-align : left"><nested:text property="accountNo" styleClass="accountNo" maxlength="20" styleId="accountNo"/></td>
					</tr>
					<tr>
						<th>備考</th>
						<td colspan="4" style="text-align : left"><nested:textarea property="supplierRemarks" rows="8" cols="80" /></td>
					</tr>
				</table>

					<nested:equal value="0" property="sysSupplierId">
					<div class="footerButtonArea">
						<a class="button_main" id="registry" href="Javascript:void(0);">登録</a>
					</div>
					</nested:equal>
					<nested:notEqual value="0" property="sysSupplierId">
					<div class="footerButtonArea">
						<ul>
							<li class="buttonSpase">
								<a class="button_main" id="update" href="Javascript:void(0);">更新</a>
							</li>
							<li class="buttonSpase">
								<a class="button_white" id="delete" href="Javascript:void(0);">削除</a>
							</li>
						</ul>
					</div>
					</nested:notEqual>

			</nested:nest>
		</html:form>
	</body>
	<script type="text/javascript">
	$(function() {

		//通貨レート初期化
		$("input[name='currencyRate']").val("");

		//詳細画面遷移時、通貨レートを取得する処理
		if($("#sysSupplierId").val() != 0) {

			var currencyId = $("#currencyId").val();

			$.ajax({
				type : 'post'
				,url : './getCurrencyList.do'
				,dataType : 'json'
				,data : {'currencyId' : currencyId}
			}).done(function(data){

				if(data != "") {
					$("input[name='currencyRate']").val(data[0].rate);
				} else if(data == "") {
					$("input[name='currencyRate']").val("");
				}
			});
		}

		//入力フォーム初期値の0を消す処理
		$(".numText").focus (function() {

			var num = $(this).val();
			if (num == 0) {
				$(this).val('');
			} else {
				num.toString().replace(/,/g , "");
				$(this).val(num);
			}
		})
		.blur (function () {

			var num = $(this).val();

			if (num == '') {
				$(this).val(0);
			} else {
				num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				$(this).val(num);
			}
		});

		//通貨レート表示変更
		$("#currencyId").change( function() {

			var currencyId = $("#currencyId").val();

			$.ajax({
				type : 'post'
				,url : './getCurrencyList.do'
				,dataType : 'json'
				,data : {'currencyId' : currencyId}
			}).done(function(data){

				if(data != "") {
					$("input[name='currencyRate']").val(data[0].rate);
				} else if(data == "") {
					$("input[name='currencyRate']").val("");
				}
			});
		});

		//登録処理
		$("#registry").click( function () {

			if($("#supplierNo").val() == 0) {
				alert("仕入先番号は必須です。");
				return;
			}
			if(!$("#companyFactoryNm").val()) {
				alert("会社・工場名は必須です。");
				return;
			}
			if(!$("#poNoInitial").val()) {
				alert("PoNo頭文字は必須です。");
				return;
			}

			//支払い条件１と２を足して100になっていなければアラーと
			if (Number($('#paymentTerms1').val()) + Number($('#paymentTerms2').val()) != 100) {

				alert("支払条件1と2の入力に誤りがあります。");
				return false;
			}

			goTransaction('registrySupplier.do');
		});

		//更新処理
		$("#update").click( function () {

			if($("#supplierNo").val() == 0) {
				alert("仕入先番号は必須です。");
				return;
			}
			if(!$("#companyFactoryNm").val()) {
				alert("会社・工場名は必須です。");
				return;
			}
			if(!$("#poNoInitial").val()) {
				alert("PoNo頭文字は必須です。");
				return;
			}

			//支払い条件１と２を足して100になっていなければアラーと
			if (Number($('#paymentTerms1').val()) + Number($('#paymentTerms2').val()) != 100) {

				alert("支払条件1と2の入力に誤りがあります。");
				return false;
			}

			goTransaction('updateSupplier.do');
		});

		//削除処理
		$("#delete").click( function () {

			if (!confirm("仕入先情報を削除します、よろしいですか？？")) {
				return;
			}
			goTransaction('deleteSupplier.do');
		});

		//メッセージエリア表示設定
		if(!$("#registryDto.message").val()) {
			if ($("#messageFlg").val() == "0") {
				$("#messageArea").addClass("registrySuccess");
			}
			if ($("#messageFlg").val() == "1") {
				$("#messageArea").addClass("registryFailure");
			}
			$("#messageArea").fadeOut(4000);
		}

		//**************** 支払い条件自動入力 ************************************************************************************

		//支払い条件１
		$('#paymentTerms1').blur(function() {

			var paymentTerms1 = Number($('#paymentTerms1').val());
			var paymentTerms2 = Number($('#paymentTerms2').val());

			//入力された条件が100以内でなければアラート
			if (paymentTerms1 > 100) {

				alert("支払条件1に誤りがあります。");
				$('#paymentTerms1').val("");

				return false;
			}

			//条件１と２を足して100でなけれ条件２に自動入力
			if (paymentTerms1 + paymentTerms2 != 100) {

				$('#paymentTerms2').val(100 - paymentTerms1);
			}
		});

		//支払い条件２
		$('#paymentTerms2').blur(function() {

			var paymentTerms1 = Number($('#paymentTerms1').val());
			var paymentTerms2 = Number($('#paymentTerms2').val());

			//入力された条件が100以内でなければアラート
			if (paymentTerms2 > 100) {

				alert("支払条件2に誤りがあります。");
				$('#paymentTerms2').val("");

				return false;
			}

			//条件１と２を足して100でなけれ条件２に自動入力
			if (paymentTerms1 + paymentTerms2 != 100) {

				$('#paymentTerms1').val(100 - paymentTerms2);
			}
		});
	});

	</script>
</html:html>