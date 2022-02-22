<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
		<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<link rel="stylesheet" href="./css/supplierSearch.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
		<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
		<script src="./js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>
		<script src="./js/jquery.ui.core.min.js"></script>
		<script src="./js/validation.js" type="text/javascript"></script>

<!--
【仕入先検索小画面】
ファイル名：supplierSearch.jsp
作成日：2016/12/21
作成者：白井崇詞

（画面概要）
仕入先の検索・一覧画面
・海外注文伝票詳細画面から遷移する。

【検索条件エリア】
・検索条件をもとに、仕入先を検索する。
・仕入先ID、支払条件1、支払条件2、仕入先リードタイムを検索条件とする。

【仕入先一覧表示エリア】
・検索結果を一覧表示する
・選択ボタンを押下することで、該当行の仕入先情報が海外注文伝票詳細画面に反映される。
（注意・補足）

-->
		<script type="text/javascript">
		$(function () {

			//**************** メッセージエリア表示設定 **************************************************************************************

			if (!$("#registryDto.message").val()) {
				if ($("#messageFlg").val() == "0") {
					$("#messageArea").addClass("registrySuccess");
				}
				if ($("#messageFlg").val() == "1") {
					$("#messageArea").addClass("registryFailure");
				}
				$("#messageArea").fadeOut(4000);
			}
			//******************************************************************************************************

			$(".select").click(function () {

				//親画面
				var fgnOrderSlip = window.opener;

				//選択ボタンが押された行のデータ
				var selectArea = $(this).parents("tr.supplierSearchRow");

				//検索結果仕入先IDを０表示させないためにString型に変換して格納
				var strSupplierId = selectArea.find(".sysSupplierId").val();
				String(strSupplierId);

				fgnOrderSlip.$("#sysSupplierId").val(selectArea.find(".sysSupplierId").val());
				fgnOrderSlip.$("#paymentTerms1").val(selectArea.find(".paymentTerms1").val());
				fgnOrderSlip.$("#paymentTerms2").val(selectArea.find(".paymentTerms2").val());
				fgnOrderSlip.$("#suppplierLeadTime").val(selectArea.find("#leadTime").val());

				fgnOrderSlip.$("#supplierId").val(strSupplierId);
				fgnOrderSlip.$("#supplierNo").text(selectArea.find(".supplierNo").val());
				fgnOrderSlip.$("#companyFactoryNm").text(selectArea.find(".companyFactoryNm").val());
				fgnOrderSlip.$("#country").text(selectArea.find(".country").val());
				fgnOrderSlip.$("#address").text(selectArea.find(".address").val());
				fgnOrderSlip.$("#tel").text(selectArea.find(".tel").val());
				fgnOrderSlip.$("#fax").text(selectArea.find(".fax").val());
				fgnOrderSlip.$("#contactPersonNm").text(selectArea.find(".contactPersonNm").val());
				fgnOrderSlip.$("#mailAddress").text(selectArea.find(".mailAddress").val());
				fgnOrderSlip.$("#tradeTerms").text(selectArea.find(".tradeTerms").val());
				fgnOrderSlip.$("#currencyId").val(selectArea.find(".currencyId").val());
				fgnOrderSlip.$("#bankNm").text(selectArea.find(".bankNm").val());
				fgnOrderSlip.$("#branchNm").text(selectArea.find(".branchNm").val());
				fgnOrderSlip.$("#bankAddress").text(selectArea.find(".bankAddress").val());
				fgnOrderSlip.$("#swiftCode").text(selectArea.find(".swiftCode").val());
				fgnOrderSlip.$("#accountNo").text(selectArea.find(".accountNo").val());
				fgnOrderSlip.$("#supplierRemarks").text(selectArea.find(".supplierRemarks").val());
				fgnOrderSlip.$("#currencyNm").text(selectArea.find(".currencyNm").val());
				fgnOrderSlip.$("#rate").text(selectArea.find(".rate").val());
				fgnOrderSlip.$("#currencyType").text(selectArea.find(".currencyType").val());

				//金額情報欄textbox左に通貨表示
				window.opener.$(".currencyTypeSpan").html(selectArea.find(".currencyType").val() + " ");

				window.close();
			});

			//検索結果１件なら選択ボタン押下処理
			if($("#supplierListSize").val() == '1') {

				$(".select").click();

				//親画面関数呼び出し
				window.opener.paymentTerms();
			}
			//**************** 検索 *************************************************************************************

			$('.search').click(function () {

				var longSupplierId = $("#supplierId").val();
				Number(longSupplierId);
				$("#sysSupplierId").val(longSupplierId);

				$("#searchSysSupplierId").val($("#sysSupplierId").val());

				goTransaction("subWinSupplierSearch.do");
			});

			//****************  *************************************************************************************
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

		});

		</script>
	</head>
	<nested:form action="/subWinSupplierSearch">

		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>

		<h4 class="head">仕入先検索</h4>

		<div class="searchOptionArea">
			<table class="searchOptionTable">
				<nested:nest property="supplierSearchDTO">
					<tr>
						<th>仕入先ID</th>
						<td>
							<nested:text property="supplierId" styleId="supplierId" style="text-align: right;" styleClass="numText"/>
							<nested:hidden property="sysSupplierId" styleId="searchSysSupplierId"/>
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<th>支払条件1</th> -->
<%-- 						<td><nested:text property="paymentTerms1" styleClass="numText" style="text-align: right;"/></td> --%>
<!-- 						<th>支払条件2</th> -->
<%-- 						<td><nested:text property="paymentTerms2" styleClass="numText" style="text-align: right;"/></td> --%>
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th>仕入先リードタイム</th> -->
<!-- 						<td> -->
<%-- 							<nested:select property="leadTime" styleId="leadTime"> --%>
<%-- 								<html:optionsCollection property="leadTimeMap" label="value" value="key"/> --%>
<%-- 							</nested:select> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
				</nested:nest>
			</table>
		</div>

		<nested:hidden property="supplierListSize" styleId="supplierListSize"/>
		<div class="paging_num">
			<h3>全<nested:write property="supplierListSize" />件</h3>
			<div class="searchButton"><a class="button_main search" href="Javascript:void(0);">検索</a></div>
		</div>

		<div class="out_Div">
			<div class="in_Div">
			<table class="list_table">
				<tr>
					<th class="wCode" nowrap>仕入先ID</th>
					<th class="wNm" nowrap>仕入先名</th>
					<th class="wNum" nowrap>支払条件1</th>
					<th class="wNum" nowrap>支払条件2</th>
					<th class="wSelect" nowrap>仕入先リードタイム</th>
					<th class="wButton" nowrap></th>
				</tr>
				<nested:iterate property="searchSupplierList" indexId="idx" id="searchSupplierList">
					<tr class="supplierSearchRow">
						<nested:hidden property="sysSupplierId" styleClass="sysSupplierId" styleId="sysSupplierId"/>
						<nested:hidden property="supplierNo" styleClass="supplierNo" styleId="supplierNo"/>
						<nested:hidden property="companyFactoryNm" styleClass="companyFactoryNm" styleId="companyFactoryNm"/>
						<nested:hidden property="country" styleClass="country" styleId="country"/>
						<nested:hidden property="address" styleClass="address" styleId="address"/>
						<nested:hidden property="tel" styleClass="tel" styleId="tel"/>
						<nested:hidden property="fax" styleClass="fax" styleId="fax"/>
						<nested:hidden property="contactPersonNm" styleClass="contactPersonNm" styleId="contactPersonNm"/>
						<nested:hidden property="mailAddress" styleClass="mailAddress" styleId="mailAddress"/>
						<nested:hidden property="tradeTerms" styleClass="tradeTerms" styleId="tradeTerms"/>
						<nested:hidden property="paymentTerms1" styleClass="paymentTerms1" styleId="paymentTerms1"/>
						<nested:hidden property="paymentTerms2" styleClass="paymentTerms2" styleId="paymentTerms2"/>
						<nested:hidden property="currencyId" styleClass="currencyId" styleId="currencyId"/>
						<nested:hidden property="leadTime" styleClass="leadTime" styleId="leadTime"/>
						<nested:hidden property="bankNm" styleClass="bankNm" styleId="bankNm"/>
						<nested:hidden property="branchNm" styleClass="branchNm" styleId="branchNm"/>
						<nested:hidden property="bankAddress" styleClass="bankAddress" styleId="bankAddress"/>
						<nested:hidden property="swiftCode" styleClass="swiftCode" styleId="swiftCode"/>
						<nested:hidden property="accountNo" styleClass="accountNo" styleId="accountNo"/>
						<nested:hidden property="supplierRemarks" styleClass="supplierRemarks" styleId="supplierRemarks"/>
						<nested:hidden property="currencyNm" styleClass="currencyNm" styleId="currencyNm"/>
						<nested:hidden property="rate" styleClass="rate" styleId="rate"/>
						<nested:hidden property="currencyType" styleClass="currencyType" styleId="currencyType"/>

						<td class="sysSupplierId wListCode"><nested:write property="sysSupplierId" /></td>
						<td class="companyFactoryNm wListNm"><nested:write property="companyFactoryNm" /></td>
						<td class="paymentTerms1 wListNum"><nested:write property="paymentTerms1" />％</td>
						<td class="paymentTerms2 wListNum"><nested:write property="paymentTerms2" />％</td>
						<td>
							<nested:select disabled="true" property="leadTime" styleId="leadTime" styleClass="wListSelect">
								<html:optionsCollection property="leadTimeMap" label="value" value="key" />
							</nested:select>
						</td>
						<td class="wListButton"><a class="button_small_main select" href="Javascript:void(0);">選択</a></td>
					</tr>
				</nested:iterate>
			</table>
			</div>
		</div>
	</nested:form>
</html:html>