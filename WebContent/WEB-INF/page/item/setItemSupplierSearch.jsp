<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
		<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<link rel="stylesheet" href="./css/itemSupplierSearch.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
		<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
		<script src="./js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>
		<script src="./js/jquery.ui.core.min.js"></script>
		<script src="./js/validation.js" type="text/javascript"></script>

<!--
【工場検索小画面】
ファイル名：setItemSupplierSearch.jsp
作成日：2017/01/04
作成者：白井崇詞

（画面概要）

工場(仕入先)の検索・一覧画面
・商品詳細画面から遷移する

【検索条件エリア】
・工場IDをもとに、工場を検索する。

【工場一覧表示エリア】
・工場IDを入力しなかった場合、登録されている全ての工場が表示される
・選択ボタンを押下することで、該当行の工場情報が商品詳細画面に反映される。
（注意・補足）

-->

		<script type="text/javascript">
		$(function () {

			$(".select").click(function () {

				//親画面
				var itemDetail = window.opener;

				//選択ボタンが押された行のデータ
				var selectArea = $(this).parents('tr.supplierSearchRow');

				var strSupplierId = selectArea.find('.sysSupplierId').val();
				String(strSupplierId);

				itemDetail.$("#sysSupplierId").val(selectArea.find(".sysSupplierId").val());
				itemDetail.$("#supplierId").val(strSupplierId);
				itemDetail.$("#companyFactoryNm").text(selectArea.find(".companyFactoryNm").val());
				itemDetail.$("#purchaceCountry").val(selectArea.find(".country").val());
				itemDetail.$(".currencyType").text(selectArea.find(".currencyType").val());
				itemDetail.$("#currencyId").val(selectArea.find(".currencyId").val());
				itemDetail.$("#currencyNm").text(selectArea.find(".currencyNm").val());
				itemDetail.$("#rate").val(selectArea.find(".rate").val());

				window.close();
			});


			if($('#supplierListSize').val() == '1') {

				$(".select").click();
			}

			$('.search').click( function () {

				var longSupplierId = $("#supplierId").val();
				Number(longSupplierId);
				$("#sysSupplierId").val(longSupplierId);

				$("#searchSysSupplierId").val($("#sysSupplierId").val());

				goTransaction("subWinSetItemSupplierSearch.do");
			});

			//入力フォーム初期値の0を消す処理
			$('.numText').focus ( function() {

				var num = $(this).val();
				if (num == 0) {
					$(this).val('');
				} else {
					num.toString().replace(/,/g , "");
					$(this).val(num);
				}
			})
			.blur ( function () {

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

	<html:form>
		<h4 class="head">工場検索</h4>

		<div class="searchOptionArea">
			<table class="searchOptionTable">
				<tr>
					<th>工場ID</th>
					<td>
						<nested:text property="mstItemDTO.supplierId" styleId="supplierId" style="text-align: right;"/>
						<nested:hidden property="mstItemDTO.sysSupplierId" styleId="searchSysSupplierId"></nested:hidden>
					</td>
				</tr>
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
					<th class="wFcCode" nowrap>工場ID</th>
					<th class="wFcNm" nowrap>工場名</th>
					<th class="wCoNm" nowrap>国</th>
					<th class="wCuNm" nowrap>通貨</th>
					<th class="wButton" nowrap></th>
				</tr>
				<nested:iterate property="supplierList" indexId="idx" id="supplierList">
					<tr class="supplierSearchRow">
						<nested:hidden property="sysSupplierId" styleClass="sysSupplierId" styleId="sysSupplierId"/>
						<nested:hidden property="companyFactoryNm" styleClass="companyFactoryNm" styleId="companyFactoryNm"/>
						<nested:hidden property="country" styleClass="country" styleId="country"/>
						<nested:hidden property="currencyId" styleClass="currencyId" styleId="currencyId"/>
						<nested:hidden property="currencyType" styleClass="currencyType" styleId="currencyType"/>
						<nested:hidden property="currencyNm" styleClass="currencyNm" styleId="currencyNm"/>
						<nested:hidden property="rate" styleClass="rate" styleId="rate"/>

						<td class="sysSupplierId wListCode"><nested:write property="sysSupplierId" /></td>
						<td class="companyFactoryNm wListNm"><nested:write property="companyFactoryNm" /></td>
						<td class="country wListNum"><nested:write property="country" /></td>
						<td class="currencyNm wListNum"><nested:write property="currencyNm" /></td>
						<td class="wListButton"><a class="button_small_main select" href="Javascript:void(0);">選択</a></td>
					</tr>
				</nested:iterate>
			</table>
			</div>
		</div>
	</html:form>
</html:html>