<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
		<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<link rel="stylesheet" href="./css/orderPoolItemSearch.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
		<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
		<script src="./js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>
		<script src="./js/jquery.ui.core.min.js"></script>
		<script src="./js/validation.js" type="text/javascript"></script>

<!--
【注文プール商品検索小画面】
ファイル名：orderPoolItemSearch.jsp
作成日：2017/01/12
作成者：白井崇詞

（画面概要）
注文プール内の商品検索・一覧画面
・海外注文伝票詳細画面から遷移する。

【検索条件エリア】
・検索条件をもとに、商品を検索する。
品番、商品名を検索条件とする。

【商品一覧表示エリア】
・検索結果を一覧表示する
・選択ボタンを押下することで、該当行の商品情報が海外注文伝票詳細画面に反映される。
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
			//************** 初期表示 ****************************************************************************************

			//親画面仕入先IDを取得
			var supplierArea = window.opener.$("div#supplier").val();
			if (supplierArea) {
				$("#selectSysSupplierId").val(supplierArea.find("#sysSupplierId").val());
			}

			//検索結果テーブル
			var addItemRow = $("tr.addItemRow");
			//送料、値引き、代引き手数料は「-」で表示
			for (var i = 0; i < addItemRow.size(); i++) {

				//検索結果の仕入先IDを表示
				addItemRow.eq(i).find(".sysSupplierIdSpan").text(addItemRow.eq(i).find("#resultSysSupplierId").val());

				//この品番のものは仕入先ID欄はハイフン表示
				if (addItemRow.eq(i).find("#resultItemCode").val() == 11 || addItemRow.eq(i).find("#resultItemCode").val() == 77 ||
					addItemRow.eq(i).find("#resultItemCode").val() == 88 || addItemRow.eq(i).find("#resultItemCode").val() == 99 ||
					addItemRow.eq(i).find("#resultItemCode").val() == 00) {
					addItemRow.eq(i).find(".sysSupplierIdSpan").text(" - ");
				}
			}

			//**************** 商品選択ボタン *************************************************************************************

			$(".select").click( function () {

				//親画面の商品エリア
				var itemArea = window.opener.$("tr.addItemRow");
				//親画面の仕入先情報エリア
				var supplierArea = window.opener.$("div.supplier");
				//親画面で押された検索ボタンインデックス
				var idx = Number(window.opener.$("#openerIdx").val());
				//選択ボタンが押された行のデータ
				var selectArea = $("tr.addItemRow");
				//レコードが何行出ているか取得
 				var addItemRowCount = Number(window.opener.$("#rowCount").val());
				//残りレコード数
				var remainCount = 50 - idx;

				//残りレコードの空きをカウント
				for (var i = idx; i < 50; i++) {

					//親画面商品レコード
					var itemCode = itemArea.eq(i).find(".addItemCode").val();
					//子画面検索結果レコード
					var resultItemCode = selectArea.eq(checkCount).find("#resultItemCode").val();

					//親画面商品が入力されていて、親画面商品コードと検索結果商品コードが同じならカウントダウン
					if (itemCode != "" && itemCode != resultItemCode) {
						remainCount--;
					}
				}

				//チェック数より空きレコード数が少なければアラート
				if ($(".check:checked").length > remainCount) {

					alert("一度に追加できるのは50件までです。");
					return false;
				}

				//表示可能数をカウント
				var checkCount = 0;
				//チェック数が追加可能レコード数以内か判定
				for (var i = idx; i < 50; i++) {

					var itemCode = itemArea.eq(i).find(".addItemCode").val();
					var resultItemCode = selectArea.eq(checkCount).find("#resultItemCode").val();

					//親画面商品が入力されていて、親画面商品コードと検索結果商品コードが同じならコンティニュー
					if (itemCode != "" && itemCode != resultItemCode) {
						continue;
					}

					//入力されていなければカウント
					checkCount++;
				}

				//表示可能数がチェック数をより少なければアラート
				if (checkCount < $(".check:checked").length) {

					alert("一度に追加できるのは50件までです。");
					return false;
				}

				var i = 0;
				//商品検索結果分ループ
				while (i < selectArea.size()) {

					//チェックボックスにチェックがあれば親画面に格納
					if (selectArea.eq(i).find(".check").prop("checked")) {

						//商品が50行を超えてしまった場合アラート
						if (idx >= 51) {

							var overItemCode = selectArea.eq(i).find("#resultItemCode").val();
							alert("商品「" + overItemCode + "」が追加可能数を超えたため追加できませんでした。"
									+ "更新ボタンを押してから再度追加してください。");
							return false;
						}

						//親画面でボタンを押された場所以降にレコードがあるか判定
						if (addItemRowCount - 1 < idx) {

							//無ければレコード追加
							window.opener.addItemRowOpen();
						}

						//レコードに情報が入っていないか判定
						if (itemArea.eq(idx).find(".addItemCode").val() && itemArea.eq(idx).find(".addItemCode").val() != selectArea.eq(i).find("#resultItemCode").val()) {

							//入っていれば次の列参照
							idx++;
							continue;
						}

						//親画面に商品情報格納
						itemArea.eq(idx).find(".addSysItemId").val(selectArea.eq(i).find("#resultSysItemId").val());
						itemArea.eq(idx).find(".addItemCode").val(selectArea.eq(i).find("#resultItemCode").val());
						itemArea.eq(idx).find(".addFactoryItemCode").val(selectArea.eq(i).find("#resultFactoryItemCode").val());
						itemArea.eq(idx).find(".addItemNm").val(selectArea.eq(i).find("#resultItemNm").val());
						itemArea.eq(idx).find(".addOrderNum").val(selectArea.eq(i).find("#resultOrderNum").val());
						itemArea.eq(idx).find(".addOrderNum").html(selectArea.eq(i).find("#resultOrderNum").val());
						itemArea.eq(idx).find("#sysSupplierId").val(selectArea.eq(i).find("#resultSysSupplierId").val());
						itemArea.eq(idx).find(".addForeignItemNm").val(selectArea.eq(i).find("#resultForeignItemNm").val());

						//仕入れ金額にカンマ付与
						var UnitPrice = selectArea.eq(i).find("#resultUnitPrice")[0];
						addComma(UnitPrice);

		 				itemArea.eq(idx).find(".addUnitPrice").val(selectArea.eq(i).find("#resultUnitPrice").val());
						itemArea.eq(idx).find(".arrivalDate").val("");

						idx++;
					}

					i++;
				}

				//親画面に仕入先IDが入っているか判定
				if (!window.opener.$("#sysSupplierId").val()) {

					//親画面仕入先IDを入れて検索
					window.opener.$("#sysSupplierId").val(selectArea.find("#resultSysSupplierId").val());
					window.opener.supplierSearch();
				}

		 		window.close();
			});

			//検索結果が１件の場合自動選択
			if($("#itemListSize").val() == '1') {

				$(".check").prop("checked", true);
				$(".select").click();
			}

			//**************** 検索 *************************************************************************************

			$(".search").click( function () {

				if ($('#selectSysSupplierId').val() != 0) {
					var strSupplierId = String($('#selectSysSupplierId').val());
					$('#selectSysSupplierId').val(strSupplierId);
				}

				goTransaction("subWinOrderPoolItemSearch.do");
			});

			//**************** 商品情報 レコード削除 全件チェック *************************************************************************************

			$('#allOrderCheck').click(function(){
				if (this.checked) {
					$(".check").prop("checked", true);
				} else {
					$(".check").prop("checked", false);
				}
			  });

			//**************** 半角数字&品番FromTo11桁チェック *************************************************************************************

			$(".lengthCheck").blur(function () {

				if (!numLengthCheck(this, 11)){
					$(this).val("");
					return;
				}
			});

		});
		</script>
	</head>

	<html:form action="/subWinOrderPoolItemSearch">

	<nested:nest property="registryDto">
		<nested:hidden property="messageFlg" styleId="messageFlg"/>
		<nested:notEmpty property="message">
			<div id="messageArea">
				<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
			</div>
		</nested:notEmpty>
	</nested:nest>

	<h4 class="head">商品検索</h4>

	<div class="searchOptionArea">
		<table class="searchOptionTable">
			<nested:nest property="searchItemDTO">
				<nested:hidden property="openerIdx" styleId="openerIdx" />
				<tr>
					<td rowspan="2">品番</td>
					<td><nested:text property="itemCode" styleClass="text_w120 numText searchItemCode" maxlength="11" />&nbsp;<span class="explain">※前方一致検索</span></td>
					<td>仕入先ID</td>
					<td>
						<nested:text property="sysSupplierId" styleClass="text_w50 numText" styleId="selectSysSupplierId"/>
						<br/><span class="explain">※仕入先情報と同じ仕入先IDが入ります</span>
					</td>
				</tr>
				<tr>
					<td><nested:text property="itemCodeFrom" styleClass="text_w120 lengthCheck searchItemCodeFrom" styleId="searchItemCodeFrom" maxlength="11" />&nbsp;～&nbsp;<nested:text property="itemCodeTo" styleClass="text_w120 lengthCheck searchItemCodeTo" styleId="searchItemCodeTo" maxlength="11"/>
						<br/><span class="explain">※入力する場合は11桁必須</span>
					</td>
					<td style="padding-left: 20px;">商品名</td>
					<td><nested:text property="itemNm" styleClass="text_w200 searchItemNm" /></td>
				</tr>
			</nested:nest>
		</table>
	</div>

	<nested:hidden property="orderPoolItemListSize" styleId="itemListSize"/>
	<div class="paging_num">
		<h3>全<nested:write property="orderPoolItemListSize" />件</h3>
		<div class="searchButton"><a class="button_main search" href="Javascript:void(0);">検索</a></div>
	</div>

	<div class="out_Div">
		<div class="in_Div">
			<table class="list_table">
				<thead>
					<tr>
						<th class="wButton"><input type="checkbox" id="allOrderCheck"class="allOrderCheck checkBoxTransForm"></th>
						<th class="wCode">品番</th>
						<th class="wNm">工場品番</th>
						<th class="wNm">商品名</th>
						<th class="wNum">注文数</th>
						<th class="wNum">仕入先ID</th>
					</tr>
				</thead>
				<nested:iterate property="orderPoolSearchResultItemList" indexId="idx" id="resultItemList">
					<tr class="addItemRow">
						<td class="wListButton">
							<nested:checkbox property="itemSelect" styleClass="check checkBoxTransForm"/>
						<td class="wListCode">
							<nested:write property="itemCode"/></td>
						<td class="wListNm">
							<nested:write property="factoryItemCode"/></td>
						<td class="wListNm">
							<nested:write property="itemNm"/></td>
						<td class="wListNum">
							<nested:write property="orderNum"/></td>
						<td class="wListNum">
							<span class="sysSupplierIdSpan"></span></td>
<!-- 							<a class="button_small_main select" href="Javascript:void(0);">選択</a></td> -->
						<nested:hidden property="sysItemId" styleId="resultSysItemId"/>
						<nested:hidden property="itemCode" styleId="resultItemCode"/>
						<nested:hidden property="factoryItemCode" styleId="resultFactoryItemCode"/>
						<nested:hidden property="itemNm" styleId="resultItemNm"/>
						<nested:hidden property="orderNum" styleId="resultOrderNum"/>
						<nested:hidden property="foreignItemNm" styleId="resultForeignItemNm"/>
<%-- 						<nested:hidden property="orderPoolFlg" styleId="resultOrderPoolFlg"/> --%>
						<nested:hidden property="sysSupplierId" styleId="resultSysSupplierId"/>
						<nested:hidden property="unitPrice" styleId="resultUnitPrice"/>
					</tr>
				</nested:iterate>
			</table>
			<!-- 	フッターボタン -->
			<div class="buttonArea">
				<a class="button_main select" href="Javascript:void(0);">選択</a>
			</div>
		</div>
	</div>

	</html:form>
</html:html>