<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
<head>
<title>国内注文書作成</title>

<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />

<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
<link rel="stylesheet" href="./css/font-awesome.min.css" />
<link rel="stylesheet" href="./css/domesticOrder.css" type="text/css" />

<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

<script src="./js/jquery.ui.core.min.js"></script>
<script src="./js/jquery.ui.datepicker.min.js"></script>
<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
<script src="./js/shortcut.js" language="javascript"></script>
<script src="./js/validation.js" type="text/javascript"></script>

<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>


<!--
【国内注文書作成画面】
ファイル名：domesticOrder.jsp
作成日：2016/11/30
作成者：野澤

（画面概要）



（注意・補足）

-->
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
<html:form action="/initDomesticOrder">
	<div id='cssmenu'></div>

	<input type="hidden" name="pickoutputFlg" value="0" id="pickoutputFlg">
	<input type="hidden" name="alertType" value="0" id="alertType">
	<nested:nest property="registryDto">
		<nested:hidden property="messageFlg" styleId="messageFlg" />
		<nested:notEmpty property="message">
			<div id="messageArea">
				<p class="registryMessage" style="text-align: leght;">
					<nested:write property="message" />
				</p>
			</div>
		</nested:notEmpty>
	</nested:nest>

	<nested:nest property="domesticOrderSlipDto">
		<nested:hidden property="sysDomesticSlipId"
			styleClass="sysDomesticSlipId" />
		<nested:equal value="0" property="sysDomesticSlipId">
			<h4 class="heading">国内注文書作成</h4>
		</nested:equal>
		<nested:notEqual value="0" property="sysDomesticSlipId">
			<div class="heading">国内注文書詳細</div>
		</nested:notEqual>
	</nested:nest>


	<!-- サブウィンド商品検索用 -->
	<nested:nest property="domesticExhibitionSearchDTO">
		<nested:hidden property="managementCode" styleId="searchManagementCode" />
		<nested:hidden property="itemNm" styleId="searchItemNm" />
		<nested:hidden property="makerNm" styleId="searchMakerNm"/>
		<nested:hidden property="sysMakerId" styleId="searchMakerId"/>
		<nested:hidden property="makerNmPhonetic" />
		<nested:hidden property="openerIdx" styleId="openerIdx" />
		<nested:hidden property="sysCorporationId" styleId="searchSysCorporationId" />
		<nested:hidden property="setItemFlg" styleId="setItemFlg" />
		<nested:hidden property="searchItemType" styleId="searchItemType"/>
	</nested:nest>

	<fieldset class="searchOptionField">
		<legend id="searchOptionOpen">▲隠す</legend>

		<div id="searchOptionArea">

			<nested:hidden property="responsibleNo" styleClass="responsibleNo" />

			<nested:nest property="domesticOrderSlipDto">
				<nested:hidden property="sysDomesticSlipId" styleId="newDomesticSlipRegistry" />
				<nested:hidden property="orderSlipDate" styleClass="orderSlipDate" />

				<table id="orderTable">

					<tr>
						<td>注文日<label class="necessary">※</label></td>
						<td><nested:text property="itemOrderDate" maxlength="10"
								styleClass="itemOrderDate calender" /></td>
					</tr>
					<tr>
						<td>会社<label class="necessary">※</label></td>
						<td><nested:select property="sysCorporationId" styleId="sysCorporationId">
								<html:option value=""></html:option>
								<html:optionsCollection property="corporationListDTO"
									label="corporationNm" value="sysCorporationId" />
							</nested:select></td>
					</tr>

					<tr>
						<td>モール<label class="necessary">※</label></td>
						<td><nested:select property="mall" styleClass="mall" styleId="kts">
								<html:option value=""></html:option>
								<html:optionsCollection property="domesticMallKts" value="key" label="value" />
							</nested:select>
							<nested:select property="mall" styleClass="mall" styleId="sharakuin">
								<html:option value=""></html:option>
								<html:optionsCollection property="domesticMallSharakuin" value="key" label="value" />
							</nested:select>
							<nested:select property="mall" styleClass="mall" styleId="tFour">
								<html:option value=""></html:option>
								<html:optionsCollection property="domesticMallTFour" value="key" label="value" />
							</nested:select>
							<nested:select property="mall" styleClass="mall" styleId="rarugusu">
								<html:option value=""></html:option>
								<html:optionsCollection property="domesticMallRarugusu"
									value="key" label="value" />
							</nested:select> <nested:select property="mall" styleClass="mall" styleId="bcr">
								<html:option value=""></html:option>
								<html:optionsCollection property="domesticMallBcr" value="key" label="value" />
							</nested:select>
							<nested:select property="mall" styleClass="mall" styleId="cyberEco">
								<html:option value=""></html:option>
								<html:optionsCollection property="domesticMallCyberEco" value="key" label="value" />
							</nested:select>
							<nested:select property="mall" styleClass="mall" styleId="shop">
								<html:option value=""></html:option>
								<html:optionsCollection property="domesticMallShop" value="key" label="value" />
							</nested:select></td>
							<nested:hidden property="mall" styleId="hiddenMall" />
					</tr>

					<tr>
						<td>受注番号<label class="necessary">※</label></td>
						<td><nested:text property="orderNo" maxlength="30" styleClass="orderNo" /></td>
					</tr>
					<tr>
						<td>MEMO</td>
						<td><nested:textarea property="senderRemarks" styleClass="senderRemarks" /></td>
					</tr>
					<tr>
						<td>注番<label class="necessary">※</label></td>
						<td><nested:text property="noteTurn" maxlength="30" styleClass="noteTurn" /></td>
					</tr>

				</table>


				<div id="centerArea">
					<table id="deliveryTable">
						<tr>

							<td>納入先<label class="necessary">※</label></td>
							<td><nested:select property="sysWarehouseId"
									styleId="sysWarehouseId">
									<html:optionsCollection property="domesticOrderSlipList"
										label="warehouseNm" value="sysWarehouseId" />
									<nested:hidden property="warehouseNm" styleClass="warehouseNm" />
									<nested:hidden property="sysWarehouseId" styleClass="sysHiddenWarehouseId" />
								</nested:select></td>
						</tr>
						<tr>
							<td>〒</td>
							<td><nested:text property="zip" maxlength="8" styleClass="zip"  onkeyup=
							"AjaxZip3.zip2addr('domesticOrderSlipDto.zip', '', 'domesticOrderSlipDto.addressFst', 'domesticOrderSlipDto.addressNxt');" /></td>
						</tr>
						<tr>
							<td>住所1</td>
							<td><nested:text property="addressFst" maxlength="25" styleClass="addressFst" /></td>
						</tr>
						<tr>
							<td>住所2</td>
							<td><nested:text property="addressNxt" maxlength="25" styleClass="addressNxt" /></td>
						</tr>
						<tr>
							<td>住所3</td>
							<td><nested:text property="addressNxt2" maxlength="25" styleClass="addressNxt2" /></td>
						</tr>
						<tr>
							<td>氏名</td>
							<td><nested:text property="logisticNm" maxlength="25" styleClass="logisticNm" /></td>
						</tr>
						<tr>
							<td>電話番号</td>
							<td><nested:text property="tellNo" maxlength="13" styleClass="tellNo"/></td>
						</tr>
					</table>
				</div>
			</nested:nest>
		</div>
	</fieldset>

	<div id="errorMessageArea">
		<html:errors />
	</div>

	<div class="controlButtonArea">
		<a class="button_main remove " id="red" href="javascript:void(0);">削除</a>
		<a class="button_main addregistry" id="button_a" href="javascript:void(0);">追加</a>
	</div>
	<input type="hidden" name="sysItemIdListSize" value="1"
		id="sysItemIdListSize">
	<input type="hidden" name="pageIdx" value="0" id="pageIdx">
	<input type="hidden" name="itemListPageMax" value="20"
		id="itemListPageMax">
	<table id="mstTable" class="list_table">

		<tbody>
		<thead>
			<tr id="th_table">
				<td class="size_small td_center"><input type="checkbox"
					id="allOrderCheck" class="allOrderCheck checkBoxTransForm"></input></td>
				<th class="size_big">管理品番<label class="necessary">※</label></th>
				<th class="size_tall">商品名<label class="necessary">※</label></th>
				<th class="size_short">数量<label class="necessary">※</label></th>
				<th class="size_tall">問屋</th>
				<th class="size_tall">メーカー</th>
				<th class="size_normal">定価</th>
				<th class="size_tall">備考</th>
			</tr>
		</thead>
		<!-- 			詳細画面表示用 -->
		<nested:iterate property="domesticOrderList" indexId="idx">
			<tr class="domestic">
				<td class="count salesSlipRow td_center size_small"><nested:checkbox
						property="deleteCheckFlg" styleClass="detailCheckFlg checkBoxTransForm" /></td>
				<td class="size_big td_center"><span class="alphanumeric right">
					<nested:write property="managementCode" /></span></td>
				<td class="td_center size_tall"><span class="detailItemNm">
					<nested:write property="itemNm"	/></span></td>

				<td class="td_center size_short"><nested:text
						property="orderNum" styleClass="detailOrderNum num numText"	size="5" maxlength="4" /></td>
				<td class="td_center size_normal"><span	class="size_normal detailWholsesalerNm"></span></td>
					<nested:hidden property="wholsesalerNm" styleClass="detailHiddenWholsesalerNm" />
				<td class="td_center size_normal"><span	class="size_normal detailMakerNm"></span></td>
					<nested:hidden property="makerNm" styleClass="detailHiddenMakerNm" />
				<td class="td_center size_tall"><span class="detailListPrice"></span></td>
					<nested:hidden property="listPrice" styleClass="detailHiddenListPrice" />
				<td class="td_center size_tall">
					<nested:text property="orderRemarks" styleClass="detailOrderRemarks" maxlength="50" /></td>
					<nested:hidden property="serealNum"	styleClass="detailHiddenSerealNum" />
					<nested:hidden property="makerId" styleId="sysMakerId" />
					<nested:hidden property="makerCode" styleClass="makerCode" />
					<nested:hidden property="openPriceFlg" styleClass="detailOpenPriceFlg" />
					<nested:hidden property="status" styleClass="status"/>
					<nested:hidden property="itemType" styleClass="itemType"/>
					<nested:hidden property="setSysDomesticItemId" styleClass="setSysDomesticItemId"/>
					<nested:hidden property="sysManagementId" styleClass="sysManagementId"/>
			</tr>
		</nested:iterate>

		<!-- 			検索追加表示用 -->
		<nested:iterate property="addDomesticOrderItemList" indexId="idx">
			<tr class="addDomestic">
				<td class="count salesSlipRow td_center size_small">
					<nested:checkbox property="deleteCheckFlg" styleClass="orderCheckFlg checkBoxTransForm" /></td>
				<td class="size_big td_center"><a class="button_main search" id="button_a" href="javascript:void(0);">検索</a>
					<nested:text property="managementCode" styleClass="addManagementCode checkAlnumHyp left"/></td>
				<td class="td_center size_tall">
					<nested:text property="itemNm" styleClass="addItemNm" /></td>
				<td class="td_center size_short">
					<nested:text property="orderNum" styleClass="addOrderNum num numText" size="5" maxlength="20" /></td>
				<td class="td_center size_normal"><span class="size_normal addWholsesalerNm"></span></td>
					<nested:hidden property="wholsesalerNm" styleClass="addHiddenWholsesalerNm" />
				<td class="td_center size_normal"><span class="size_normal addMakerNm"></span></td>
					<nested:hidden property="makerNm" styleClass="addHiddenMakerNm" />
				<td class="td_center size_tall">
					<span class="addListPrice"></span></td>
					<nested:hidden property="listPrice" styleClass="addHiddenListPrice" />
				<td class="td_center size_tall">
					<nested:text property="orderRemarks" styleClass="addOrderRemarks" maxlength="50" /></td>
					<nested:hidden property="serealNum" styleClass="serealNum" />
					<nested:hidden property="makerId" styleClass="addMakerId" />
					<nested:hidden property="makerCode" styleClass="addMakerCode" />
					<nested:hidden property="purchasingCost" styleClass="addPurchasingCost" />
					<nested:hidden property="arrivalScheduleDate" styleClass="addArrivalScheduleDate" />
					<nested:hidden property="postage" styleClass="addPostage" />
					<nested:hidden property="openPriceFlg" styleClass="addOpenPriceFlg" />
					<nested:hidden property="itemType" styleClass="addItemType"/>
					<nested:hidden property="setSysDomesticItemId" styleClass="addSetSysDomesticItemId"/>
			</tr>
		</nested:iterate>
		</tbody>
	</table>


	<!-- ページ(下側)　ここまで -->

	<nested:nest property="domesticOrderSlipDto">
		<div class="buttonArea">
		<ul style="position: relative;">
			<li class="footer_button">
				<nested:equal value="0" property="sysDomesticSlipId">
					<a class="button_main button_order" href="javascript:void(0)" onclick="registry();">登録</a>
				</nested:equal>
			</li>

			<nested:notEqual value="0" property="sysDomesticSlipId">
				<li class="footer_button">
					<a class="button_main button_order" href="javascript:void(0)" onclick="orderUpdate();">更新</a>
				</li>
				<li class="footer_button">
					<label>
						<nested:hidden property="printCheckFlag" value="off"/>
						印刷確認(手動)<nested:checkbox property="printCheckFlag" styleClass="checkBoxTransForm" styleId="printFlgOn"/>
						<nested:hidden property="printCheckFlag" styleId="printFlgValue"></nested:hidden>
					</label>
					<a class="button_main button_order" href="javascript:void(0)" onclick="exportCreate();">注文書出力</a>
				</li>
				<li class="footer_button">
					<a class="button_main button_order" href="initDomesticOrder.do">新規登録画面</a>
				</li>
			</nested:notEqual>

			<li class="footer_button">
				<a class="button_white clear button_order" href="javascript:void(0);">画面全体をリセット</a>
			</li>
		</ul>
		</div>
	</nested:nest>

</html:form>

<script type="text/javascript">
	var ITEM_SEARCH_TYPE = "0";
	var RESULT_ITEM_TYPE_NORMAL = "0";				//セット商品フラグ比較値：通常商品
	var RESULT_ITEM_TYPE_SET = "1";				//セット商品フラグ比較値：セット商品
	var RESULT_ITEM_TYPE_FORM = "2";				//セット商品フラグ比較値：構成商品
	$(document).ready(function() {
		$(".overlay").css("display", "none");

		if ($("#newDomesticSlipRegistry").val() == 0 && $("#messageFlg").val() == 0) {
			$.ajax({
				type : 'post',
				url : './getDomesticOrderWarehouse.do',
				dataType : 'json',
				data : {
					'sysWarehouseId' : 1
				}
			}).done(function(data) {

				if (data != "") {
					$(".zip").val(data.zip);
					$(".addressFst").val(data.addressFst);
					$(".addressNxt").val(data.addressNxt);
					$(".tellNo").val(data.tellNo);
					$(".logisticNm").val(data.logisticNm);
					$(".warehouseNm").val(data.warehouseNm);
				} else if (data == "") {
					$(".zip").val("");
					$(".addressFst").val("");
					$(".addressNxt").val("");
					$(".tellNo").val("");
					$(".logisticNm").val("");
					$(".warehouseNm").val("その他");
				}
			});
		}

		//商品にステータスが注文前以外のものが含まれていた場合、印刷確認チェックボックスを非活性にする
		if ($("tr.domestic").size() > 0) {
			var targetStatusCnt = 0;
			var orderBeforeCnt = 0;
			$("tr.domestic").each(function () {
				var status = $(this).find(".status").val();
				if (status != "1") {
					targetStatusCnt++;
				} else {
					orderBeforeCnt++;
				}
				//セット商品と構成商品の色付けを行う：表示用のレコード
				if($(this).find('.itemType').val() == RESULT_ITEM_TYPE_SET) {
					$(this).css('background-color','powderblue');
				} else if ($(this).find('.itemType').val() == RESULT_ITEM_TYPE_FORM) {
					$(this).find(".detailCheckFlg").prop('disabled', true);
					$(this).css('background-color','lightgoldenrodyellow');
					$(this).find(".detailOrderNum").prop('disabled', true);
					$(this).find(".detailOrderNum").removeClass("num");
					$(this).find(".detailOrderNum").parent(".ui-spinner").css('background-color','lightgray');
					$(this).find(".detailOrderNum").parent(".ui-spinner").removeClass("ui-spinner");
				}

			})
			if (targetStatusCnt > 0) {
				$("#printFlgOn").prop('disabled', true);
			} else {
				$("#printFlgOn").prop('disabled', false);
			}

			if (orderBeforeCnt > 0) {
				alert("伝票にステータス「注文前」の商品が含まれています。");
			}
		}
		//セット商品と構成商品の色付けを行う：追加用のレコード
		$("tr.addDomestic").each(function () {
			if($(this).find('.addItemType').val() == RESULT_ITEM_TYPE_SET) {
				$(this).css('background-color','powderblue');
			} else if ($(this).find('.addItemType').val() == RESULT_ITEM_TYPE_FORM) {
				$(this).find(".orderCheckFlg").prop('disabled', true);
				$(this).css('background-color','lightgoldenrodyellow');
				$(this).find(".addOrderNum").prop('disabled', true);
				$(this).find(".addOrderNum").removeClass("num");
				$(this).find(".addOrderNum").parent(".ui-spinner").css('background-color','lightgray');
				$(this).find(".addOrderNum").parent(".ui-spinner").removeClass("ui-spinner");
			}
		});

		if ($("#printFlgValue").val() == "on") {
			$("#sysWarehouseId").prop('disabled', true);
		} else {
			$("#sysWarehouseId").prop('disabled', false);
		}
	});

	$(function() {

		$("#freeWard").show();
		$("#checkDateSub").hide();
		$("#checkCalender").hide();

		//初期表示、会社の選択によって表示するモールの表示を選択
		switch ($("#sysCorporationId").val()) {
		case "1":
			ktsMall();
			break;
		case "2":
			sharakuinMall();
			break;
		case "3":
			tFourMall();
			break;
		case "4":
			rarugusuMall();
			break;
		case "5":
			bcrMall();
			break;
		case "6":
			cyberEcoMall();
			break;
		case "99":
			shopMall();
			break;
		default:
			ktsMall();
			break;
		}

		//**************** 納入先情報制御  **************************************************************************************

		$("#sysWarehouseId").change(function() {

			var sysWarehouseId = $("#sysWarehouseId").val();
			warehouseChange(sysWarehouseId);
		});

		function warehouseChange(sysWarehouseId) {

			$.ajax({
				type : 'post',
				url : './getDomesticOrderWarehouse.do',
				dataType : 'json',
				data : {
					'sysWarehouseId' : sysWarehouseId
				}
			}).done(function(data) {

				if (data != "") {
					$(".zip").val(data.zip);
					$(".addressFst").val(data.addressFst);
					$(".addressNxt").val(data.addressNxt);
					$(".tellNo").val(data.tellNo);
					$(".logisticNm").val(data.logisticNm);
					$(".warehouseNm").val(data.warehouseNm);
				} else if (data == "") {
					$(".zip").val("");
					$(".addressFst").val("");
					$(".addressNxt").val("");
					$(".tellNo").val("");
					$(".logisticNm").val("");
					$(".warehouseNm").val("その他");
				}
			});
		}


		$("#sysWarehouseId").blur(function() {

			var sysWarehouseId = $("#sysWarehouseId").val();
			warehouseChange(sysWarehouseId);
		});

// 		function warehouseChange(sysWarehouseId) {

// 			$.ajax({
// 				type : 'post',
// 				url : './getDomesticOrderWarehouse.do',
// 				dataType : 'json',
// 				data : {
// 					'sysWarehouseId' : sysWarehouseId
// 				}
// 			}).done(function(data) {

// 				if (data != "") {
// 					$(".zip").val(data.zip);
// 					$(".addressFst").val(data.addressFst);
// 					$(".addressNxt").val(data.addressNxt);
// 					$(".tellNo").val(data.tellNo);
// 					$(".logisticNm").val(data.logisticNm);
// 					$(".warehouseNm").val(data.warehouseNm);
// 				} else if (data == "") {
// 					$(".zip").val("");
// 					$(".addressFst").val("");
// 					$(".addressNxt").val("");
// 					$(".tellNo").val("");
// 					$(".logisticNm").val("");
// 					$(".warehouseNm").val("その他");
// 				}
// 			});
// 		}

		//************* 問屋名、定価の表示  *****************************************************************************************

		//詳細画面登録済み商品に対して
		if ($("#newDomesticSlipRegistry").val() != 0) {

			var resultArea = $("tr.domestic");
			//詳細画面時、問屋名、定価、メーカー名の表示
			for (var i = 0; i < resultArea.size(); i++) {
				console.log("aaaaaaaaa", resultArea.eq(i).find(".detailHiddenWholsesalerNm").val());
				if (resultArea.eq(i).find(".detailManagementCode").val() != 0) {
					resultArea.eq(i).find(".detailWholsesalerNm").html(resultArea.eq(i).find(".detailHiddenWholsesalerNm").val());
					resultArea.eq(i).find(".detailMakerNm").html(resultArea.eq(i).find(".detailHiddenMakerNm").val());

					//定価カンマ付与
					resultArea.eq(i).find(".detailListPrice").val(resultArea.eq(i).find(".detailHiddenListPrice").val());
					var listPrice = resultArea.eq(i).find(".detailListPrice")[0];
					addComma(listPrice);

					//オープンプライスフラグ判定
					if (resultArea.eq(i).find(".detailOpenPriceFlg").val() == 0) {
						resultArea.eq(i).find(".detailListPrice").html(resultArea.eq(i).find(".detailListPrice").val() + "円");

					} else if (resultArea.eq(i).find(".detailOpenPriceFlg").val() == 1) {
						resultArea.eq(i).find(".detailListPrice").html("open");
					}
				}
			}
		}

		var addArea = $("tr.addDomestic");
		//登録失敗時、問屋名、定価、メーカー名の表示
		for (var i = 0; i < addArea.size(); i++) {
			if (addArea.eq(i).find(".addManagementCode").val() != 0) {
				addArea.eq(i).find(".addWholsesalerNm").html(addArea.eq(i).find(".addHiddenWholsesalerNm").val());
				addArea.eq(i).find(".addMakerNm").html(addArea.eq(i).find(".addHiddenMakerNm").val());

				//定価カンマ付与
				addArea.eq(i).find(".addListPrice").val(addArea.eq(i).find(".addHiddenListPrice").val());
				var listPrice = addArea.eq(i).find(".addListPrice")[0];
				listPrice = addComma(listPrice);

				if (addArea.eq(i).find(".addOpenPriceFlg").val() == 0) {
					addArea.eq(i).find(".addListPrice").html(addArea.eq(i).find(".addListPrice").val() + "円");

					if (addArea.eq(i).find(".addListPrice").val() == 0) {
						addArea.eq(i).find(".addListPrice").html(addArea.eq(i).find(""));
					}
				} else if (addArea.eq(i).find(".addOpenPriceFlg").val() == 1) {
					addArea.eq(i).find(".addListPrice").html("open");
 				}
			}
		}

		//************* 注番の出力処理  *****************************************************************************************

		//リセット用に担当者番号の保持
		var responsibleNo = $(".responsibleNo").val();

		//会社の選択によって表示するモールの表示を選択
		$("#sysCorporationId").change(function() {

			$(".noteTurn").val(responsibleNo);
			typeClear();

			switch ($("#sysCorporationId").val()) {
			case "1":
				ktsMall();
				break;
			case "2":
				sharakuinMall();
				break;
			case "3":
				tFourMall();
				break;
			case "4":
				rarugusuMall();
				break;
			case "5":
				bcrMall();
				break;
			case "6":
				cyberEcoMall();
				break;
			case "99":
				shopMall();
				break;
			default:
				ktsMall();
				break;
			}
		});

		$(".mall").change(
				function() {

					$(".noteTurn").val(responsibleNo);

					if ($("#sysCorporationId").val() >= 1
							&& $("#sysCorporationId").val() <= 99) {

						var noteturn = responsibleNo;

						noteturn += " - " + $("#kts").val()
								+ $("#sharakuin").val() + $("#tFour").val() + $("#rarugusu").val()
								+ $("#bcr").val() + $("#cyberEco").val() + $("#shop").val();

						$(".noteTurn").val(noteturn);
					}

				});

		//会社を選択した際注番をリセット
		function typeClear() {

			$("#kts").val("");
			$("#sharakuin").val("");
			$("#tFour").val("");
			$("#rarugusu").val("");
			$("#bcr").val("");
			$("#cyberEco").val("");
			$("#shop").val("");
		}

		//モールの表示、非表示
		function ktsMall() {

			$("#kts").show();
			$("#sharakuin").hide();
			$("#tFour").hide();
			$("#rarugusu").hide();
			$("#bcr").hide();
			$("#cyberEco").hide();
			$("#shop").hide();

//			$(".kts").val($(".mall").val());
		}

		function sharakuinMall() {

			$("#kts").hide();
			$("#sharakuin").show();
			$("#tFour").hide();
			$("#rarugusu").hide();
			$("#bcr").hide();
			$("#cyberEco").hide();
			$("#shop").hide();

//			$(".sharakuin").val($(".mall").val());
		}

		function tFourMall() {

			$("#kts").hide();
			$("#sharakuin").hide();
			$("#tFour").show();
			$("#rarugusu").hide();
			$("#bcr").hide();
			$("#cyberEco").hide();
			$("#shop").hide();

//			$(".tFour").val($(".mall").val());
		}

		function rarugusuMall() {

			$("#kts").hide();
			$("#sharakuin").hide();
			$("#tFour").hide();
			$("#rarugusu").show();
			$("#bcr").hide();
			$("#cyberEco").hide();
			$("#shop").hide();

//			$(".rarugusu").val($(".mall").val());
		}

		function bcrMall() {

			$("#kts").hide();
			$("#sharakuin").hide();
			$("#tFour").hide();
			$("#rarugusu").hide();
			$("#bcr").show();
			$("#cyberEco").hide();
			$("#shop").hide();

//			$(".bcr").val($(".mall").val());
		}

		function cyberEcoMall() {

			$("#kts").hide();
			$("#sharakuin").hide();
			$("#tFour").hide();
			$("#rarugusu").hide();
			$("#bcr").hide();
			$("#cyberEco").show();
			$("#shop").hide();

//			$(".cyberEco").val($(".mall").val());
		}

		function shopMall() {

			$("#kts").hide();
			$("#sharakuin").hide();
			$("#tFour").hide();
			$("#rarugusu").hide();
			$("#bcr").hide();
			$("#cyberEco").hide();
			$("#shop").show();

//			$(".shop").val($(".mall").val());
		}
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
		//***************** 初期表示時、追加分レコードを隠す  *************************************************************************************

		var addDomesticExhibitionTrs = $("tr.addDomestic");
		addDomesticExhibitionTrs.hide();

		//追加分レコードの表示分をカウントに使用
		var addDomesticExhibitionIdx = 0;

		//初期表示時、5行表示
		for (var i = 0; i < 5; i++) {

			//一行分表示
			var trs = $("tr.addDomestic");
			var tr = trs.eq(addDomesticExhibitionIdx);
			tr.show();
			addDomesticExhibitionIdx++;
		}

		//エラー時、入力したレコードの表示
		for (var i = 0; i < addDomesticExhibitionTrs.size(); i++) {

			//入力された管理品番のあるものは表示
			if (addDomesticExhibitionTrs.eq(i).find(".addManagementCode").val() != 0) {
				var trs = $("tr.addDomestic");
				var tr = trs.eq(addDomesticExhibitionIdx);
				tr.show();
				addDomesticExhibitionIdx++;
			}
		}

		//**************** *************************************************************************************

		//アラート
		if (document.getElementById('alertType').value != ''
				&& document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		for (var i = 1; i <= 9; i++) {
			shortcut.add("Alt+" + i, function(e) {
				$("#sysCorporationId").val(Number(e.keyCode) - 48);
				goTransaction("initRegistryCorporateSales.do");
			});
		}

		arrivalGoodsFlgChange = 0;

		//**************** リセットボタン  *************************************************************************************

		$(".clear").click(function() {

					if (!confirm("入力したものをリセットします。よろしいですか？")) {
						return;
					} else {
						$("#searchOptionArea input, #searchOptionArea select, #searchOptionArea textarea").each(function() {
							if (this.type == "checkbox"	|| this.type == "radio") {
								this.checked = false;
							} else {
								$(this).val("");
							}
						});
						$(".slipStatus").eq(0).prop("checked", true);
						$(".arrivalGoodsFlg").eq(0).prop("checked", true);
						$(".paymentFlg").eq(0).prop("checked", true);
						$(".addOrderNum").val(0);
						$(".addOrderNum").html(0);
						$(".addListPrice").val(0);
						$(".addListPrice").html("");
						$(".addManagementCode").val("");
						$(".addItemNm").val("");
						$(".addWholsesalerNm").val("");
						$(".addWholsesalerNm").text("");
						$(".addMakerNm").val("");
						$(".addMakerNm").text("");
						$(".addOrderRemarks").val("");
					}
		});

		//****************  *************************************************************************************

		$('#searchOptionOpen').click(function() {

			if ($('#searchOptionOpen').text() == "▼入力") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼入力");
			}

			$('#searchOptionArea').slideToggle("fast");
			$('#centerArea').slideToggle("fast");
		});

		$(".check").click(function() {
			var check_all = true;
			if ($(".check_all").prop("checked") && !$(this).prop("checked")) {
				$(".check_all").prop("checked", false);
			} else if ($(this).prop("checked")) {
				$(".check").each(function(index, checkbox) {
					if (!$(checkbox).prop("checked")) {
						check_all = false;
						return false;
					}
				});
				$(".check_all").prop("checked", check_all);
			}
		});

		$(".check_all").click(function() {
			if ($(".check_all").prop("checked")) {
				$(".check").prop("checked", true);
			} else {
				$(".check").prop("checked", false);
			}
		});

		//数字の上下
		$('.num').spinner({
			max : 9999,
			min : 0,
			step : 1
		});

		//法人メニュー開閉
		$(".corptgl").click(function() {
			$(this).parent(".corp").find(".corpmenu").toggle();
		});

		//新規伝票
		$(".registryButton").click(
				function() {
					$("#sysCorporationId").val(
							$(this).parents(".corp").find(".sysCorporationId")
									.val());
					goTransaction("initRegistryCorporateSales.do");
				});


		//******************************************************************************************************
		if ($('.slipNoExist').prop('checked')
				|| $('.slipNoHyphen').prop('checked')) {
			$('.slipNoNone').attr('disabled', 'disabled');
		}

		if ($('.slipNoNone').prop('checked')) {
			$('.slipNoExist').attr('disabled', 'disabled');
			$('.slipNoHyphen').attr('disabled', 'disabled');
		}

		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

		//******************************************************************************************************
		if ($("#sysCorporateSalesSlipIdListSize").val() != 0) {
			var slipPageNum = Math.ceil($("#sysCorporateSalesSlipIdListSize")
					.val()
					/ $("#corporateSaleListPageMax").val());

			$(".slipPageNum").text(slipPageNum);
			$(".slipNowPage").text(Number($("#pageIdx").val()) + 1);

			var i;

			if (0 == $("#pageIdx").val()) {
				$(".pager > li:eq(3)").find("a").attr("class",
						"pageNum nowPage");
				$(".underPager > li:eq(3)").find("a").attr("class",
						"pageNum nowPage");
			}

			var startIdx;
			// maxDispは奇数で入力
			var maxDisp = 7;
			// slipPageNumがmaxDisp未満の場合maxDispの値をslipPageNumに変更
			if (slipPageNum < maxDisp) {

				maxDisp = slipPageNum;

			}

			var center = Math.ceil(Number(maxDisp) / 2);
			var pageIdx = new Number($("#pageIdx").val());

			// 現在のページが中央より左に表示される場合
			if (center >= pageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(slipPageNum);
				$(".liFirstPage").hide();

				// 現在のページが中央より右に表示される場合
			} else if (slipPageNum - (center - 1) <= pageIdx + 1) {

				startIdx = slipPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

				// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#pageIdx").val() - (center - 2);
				$(".lastIdx").html(slipPageNum);

			}

			$(".pageNum").html(startIdx);
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

				$(".3dotLineTop").hide();

			}

			if ((slipPageNum <= 8) || ((slipPageNum - center) <= (pageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (slipPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}

			for (i = startIdx; i < endIdx && i < slipPageNum; i++) {
				var clone = $(".pager > li:eq(3)").clone();
				clone.children(".pageNum").text(i + 1);

				if (i == $("#pageIdx").val()) {

					clone.find("a").attr("class", "pageNum nowPage");

				} else {
					clone.find("a").attr("class", "pageNum");
				}

				$(".pager > li.3dotLineEnd").before(clone);
			}
		}

		//****** ショートカットキー設定  ************************************************************************************************

		$(".addManagementCode").on('keydown', function(e){
			if (( e.keyCode==13) && ( ! event.altKey )) {
				e.preventDefault();
				$(this).parents("tr.addDomestic").find(".search").trigger("click");
			}
		});

		//********* 検索小画面  *********************************************************************************************

		$(".search").click(function() {

			$("#searchItemNm").val("");
			$("#searchManagementCode").val("");
			$("#searchMakerNm").val("");
			$("#searchMakerId").val("");
			var salesItemArea = $("tr.addDomestic");

			var rowNum = $("tr.addDomestic").index($(this).parents("tr.addDomestic"));

			$("#searchManagementCode").val(salesItemArea.eq(rowNum).find(".addManagementCode").val());
			if (!$("#searchManagementCode").val()) {
				$("#searchItemNm").val(salesItemArea.eq(rowNum).find(".addItemNm").val());
			}

			$("#openerIdx").val(rowNum);
			$("#searchSysCorporationId").val($("#sysCorporationId").val());

			$("#searchItemType").val(ITEM_SEARCH_TYPE);
			$("#setItemFlg").val("off");

			if (!$("#searchManagementCode").val()
				&& !$("#searchItemNm").val()) {
				FwGlobal.submitForm(document.forms[0], "/subWinDomesticItemSearch", "itemSearchWindow",	"top=0,left=500,width=870px,height=750px;");
			} else {
				FwGlobal.submitForm(document.forms[0], "/searchDomesticOrder", "itemSearchWindow", "top=0,left=500,width=870px,height=750px;");
			}
		});

		//**************** チェックボックス全件選択・解除 *************************************************************************************

		$("#allOrderCheck").click(function() {
			if (this.checked) {
				$(".orderCheckFlg").prop("checked", true);
				$(".detailCheckFlg").prop("checked", true);
			} else {
				$(".orderCheckFlg").prop("checked", false);
				$(".detailCheckFlg").prop("checked", false);
			}
		});

		$(".orderCheckFlg").click(function() {

			var tr = $(this).parent("tr.addDomestic");
			if (this.checked) {
				//追レコード行
				var selectArea = $("tr.addDomestic");
				var addCheckCount = 0;
				for (var i = 0; i < selectArea.size(); i++) {

				}


				if (tr.find(".itemType").val() == RESULT_ITEM_TYPE_SET) {
					if ($('.orderCheckFlg').eq(addCheckCount).prop('checked')) {

					}
				}

			}



		});

		//******************* レコード追加 ***********************************************************************************
		//追加ボタン処理
		$(".addregistry").click(function() {

			if (addDomesticExhibitionIdx >= $("tr.addDomestic").size()) {
				alert("一度に追加できるのは20件までです。");
				return;
			}

			//一行分表示
			var trs = $("tr.addDomestic");
			var tr = trs.eq(addDomesticExhibitionIdx);
			tr.find(".addOrderNum").val(0);
			tr.find(".addOrderNum").html(0);
			tr.find(".addListPrice").html("");
			tr.find(".addHiddenListPrice").val(0);

			tr.show();
			addDomesticExhibitionIdx++;
		});
		//******************* 削除 ***********************************************************************************
		//一括削除

		$(".remove").click(	function() {

			if ($(".orderCheckFlg:checked").length == 0
					&& $(".detailCheckFlg:checked").length == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			//追加分行削除
			var selectArea = $("tr.addDomestic");
			var addCheckCount = 0;
			var j = 0;
			//追加20行分
			for (var i = 0; i < selectArea.size(); i++) {

				//チェックボックスにチェックされているか判定
				if ($('.orderCheckFlg').eq(addCheckCount).prop('checked')) {

					addDomesticExhibitionIdx--;
					//削除してずらす
					for (j = addCheckCount; j < selectArea.size(); j++) {

						//最終行の判定
						if (j == addDomesticExhibitionIdx) {

							clearAddDomesticLastRow(selectArea.eq(addDomesticExhibitionIdx));
							selectArea.eq(j).hide();
							break;
						} else {
							var copyFromTr = selectArea.eq(j + 1);
							var copyToTr = selectArea.eq(j);
							copyAddDomestic(copyFromTr,	copyToTr);
						}
					}
				} else {
					addCheckCount++;
				}
			}

			//一括削除した際の対応策
			if (addDomesticExhibitionIdx < 0) {
				addDomesticExhibitionIdx = 0;
			}

			var detailArea = $("tr.domestic");
			var checkCount = 0;
			var endCount = $("tr.domestic").size();

			//登録分にチェックがあるか確認
			for (var i = 0; i < detailArea.size(); i++) {
				if ($('.detailCheckFlg').eq(i).prop('checked')) {

					if (confirm("選択された商品を削除します。よろしいですか？")) {

						$(".overlay").css("display", "block");
						$(".message").text("削除中");
		 				goTransaction("deleteDomesticOrderSlip.do");
					}
				}
			}
 				return false;
		});

		//追加分選択行の削除
		function copyAddDomestic(copyFromTr, copyToTr) {

			if (!copyToTr.find("span.ui-widget").hasClass('ui-spinner')) {
				if(copyFromTr.find("span.ui-widget").hasClass('ui-spinner')) {
					copyToTr.find("span.ui-widget").addClass('ui-spinner');
					copyToTr.find("span.ui-widget").css("background-color", "white");
				}
			} else {
				if(!copyFromTr.find("span.ui-widget").hasClass('ui-spinner')) {
					copyToTr.find("span.ui-widget").removeClass('ui-spinner');
					copyToTr.find("span.ui-widget").css("background-color", "lightgray");
				}
			}

			if (!copyToTr.find(".addOrderNum").prop('disabled')) {
				if(copyFromTr.find(".addOrderNum").prop('disabled')) {
					copyToTr.find(".addOrderNum").prop('disabled',true);
				}
			} else {
				if(!copyFromTr.find(".addOrderNum").prop('disabled')) {
					copyToTr.find(".addOrderNum").prop('disabled',false);
				}
			}

			copyToTr.css("background-color",copyFromTr.css("background-color"));
			copyToTr.find(".orderCheckFlg").prop('checked', copyFromTr.find('.orderCheckFlg').prop('checked'));
			copyToTr.find(".addManagementCode").val(copyFromTr.find(".addManagementCode").val());
			copyToTr.find(".addItemNm").val(copyFromTr.find('.addItemNm').val());
			copyToTr.find(".addOrderNum").val(copyFromTr.find(".addOrderNum").val());
			copyToTr.find(".addHiddenWholsesalerNm").val(copyFromTr.find(".addHiddenWholsesalerNm").val());
			copyToTr.find(".addHiddenMakerNm").val(copyFromTr.find(".addHiddenMakerNm").val());
			copyToTr.find(".addWholsesalerNm").html(copyFromTr.find(".addWholsesalerNm").html());
			copyToTr.find(".addMakerNm").html(copyFromTr.find(".addMakerNm").html());
			copyToTr.find(".addHiddenListPrice").val(copyFromTr.find(".addHiddenListPrice").val());
			copyToTr.find(".addListPrice").html(copyFromTr.find(".addListPrice").html());
			copyToTr.find(".addOrderRemarks").val(copyFromTr.find(".addOrderRemarks").val());
			copyToTr.find(".addItemType").val(copyFromTr.find(".addItemType").val());
			copyToTr.find(".addSetSysDomesticItemId").val(copyFromTr.find(".addSetSysDomesticItemId").val());

			copyFromTr.find(".orderCheckFlg").prop('checked', false);
			copyFromTr.find(".addManagementCode").val("");
			copyFromTr.find(".addItemNm").val("");
			copyFromTr.find(".addOrderNum").val(0);
			copyFromTr.find(".addHiddenWholsesalerNm").val("");
			copyFromTr.find(".addWholsesalerNm").html("");
			copyFromTr.find(".addHiddenMakerNm").val("");
			copyFromTr.find(".addMakerNm").html("");
			copyFromTr.find(".addHiddenListPrice").val(0);
			copyFromTr.find(".addListPrice").html("");
			copyFromTr.find(".addOrderRemarks").val("");
			if(!copyFromTr.find("span.ui-widget").hasClass('ui-spinner')) {
				copyFromTr.find("span.ui-widget").addClass('ui-spinner');
				copyFromTr.find("span.ui-widget").css("background-color", "white");
			}
			if(copyFromTr.find(".addOrderNum").prop('disabled')) {
				copyFromTr.find(".addOrderNum").prop('disabled',false);
			}
			copyFromTr.find(".addItemType").val("");
			copyFromTr.find(".addSetSysDomesticItemId").val("");

		}

		//追加分最終行の削除
		function clearAddDomesticLastRow(domesticRow) {

			domesticRow.find(".orderCheckFlg").prop('checked', false);
			domesticRow.find(".addManagementCode").val("");
			domesticRow.find(".addItemNm").val("");
			domesticRow.find(".addOrderNum").val(0);
			domesticRow.find(".addHiddenWholsesalerNm").val("");
			domesticRow.find(".addWholsesalerNm").html("");
			domesticRow.find(".addHiddenMakerNm").val("");
			domesticRow.find(".addMakerNm").html("");
			domesticRow.find(".addHiddenListPrice").val(0);
			domesticRow.find(".addListPrice").html("");
			domesticRow.find(".addOrderRemarks").val("");
			domesticRow.find(".addOrderNum").prop('disabled',false);
			if(!domesticRow.find("span.ui-widget").hasClass('ui-spinner')) {
				domesticRow.find("span.ui-widget").addClass('ui-spinner');
				domesticRow.find("span.ui-widget").css("background-color", "white");
			}
			domesticRow.find(".addItemType").val("");
			domesticRow.find(".addSetSysDomesticItemId").val("");


		}



		//******************* 数値０を操作 ***********************************************************************************

		$(".addListPrice").focus(function() {
			var index = $(".addListPrice").index(this);
			if ($(".addListPrice").eq(index).val() == 0) {
				$(".addListPrice").eq(index).val("");
			}
		});

		$(".addListPrice").blur(function() {
			var index = $(".addListPrice").index(this)
			if ($(".addListPrice").eq(index).val() == "") {
				$(".addListPrice").eq(index).val(0);
			}
		});


		//******************* MEMO欄の制御 ***********************************************************************************

		$(".senderRemarks").blur(function() {
			var index = $(".senderRemarks").val().length;
			if (index > 400) {
				alert("MEMOは400文字までです。");
			}
		});
	});

	document.addEventListener("readystatechange", function(e) {
		if (document.readyState == 'complete'
				&& $("#pickoutputFlg").val() == '1') {
			$("#pickoutputFlg").val('0');
			$(".overlay").css("display", "none");
			window.open('corporatePickListPrintOutFile.do', '_new');
		}
	}, false);

	function warehouseChange(sysWarehouseId) {

		$.ajax({
			type : 'post',
			url : './getDomesticOrderWarehouse.do',
			dataType : 'json',
			data : {
				'sysWarehouseId' : sysWarehouseId
			}
		}).done(function(data) {

			if (data != "") {
				$(".zip").val(data.zip);
				$(".addressFst").val(data.addressFst);
				$(".addressNxt").val(data.addressNxt);
				$(".addressNxt2").val(data.addressNxt2);
				$(".tellNo").val(data.tellNo);
				$(".logisticNm").val(data.logisticNm);
				$(".warehouseNm").val(data.warehouseNm);
			} else if (data == "") {
				$(".zip").val("");
				$(".addressFst").val("");
				$(".addressNxt").val("");
				$(".addressNxt2").val("");
				$(".tellNo").val("");
				$(".logisticNm").val("");
				$(".warehouseNm").val("その他");
			}
		});
	}


	//******************* 登録  ***********************************************************************************

	function registry() {

		//伝票情報必須項目の判定

		var senderRemarksCount = unescape(encodeURIComponent($(".senderRemarks").val())).length;
		var index = $(".senderRemarks").val().length;
		if (index > 400) {
			alert("MEMOは400文字までです。");
		}

		if ($(".itemOrderDate").val() == "") {

			alert('注文日は必須項目です。');
			return false;
		}

		if ($("#sysCorporationId").val() == "") {

			alert('会社は必須項目です。');
			return false;
		}

		if ($("#kts").val() == "" && $("#sharakuin").val() == "" && $("#tFour").val() == "" &&
		$("#rarugusu").val() == "" && $("#bcr").val() == "" && $("#cyberEco").val() == "" &&
		$("#shop").val() == "") {

			alert('モールは必須項目です。');
			return false;
		}

		if ($(".orderNo").val() == "") {

			alert('受注番号は必須項目です。');
			return false;
		}

		if ($(".noteTurn").val() == "") {

			alert('注番は必須項目です。');
			return false;
		}

		var itemCnt = 0;

		//登録時、商品情報必須項目の判定
		var showTr = $(".addDomestic");

		for (var i = 0; i <= $(".addDomestic").size(); i++) {

			var addManagementCode = showTr.eq(i).find(".addManagementCode").val();
			var addItemNm = showTr.eq(i).find(".addItemNm").val();
			var addOrderNum = new Number(showTr.eq(i).find(".addOrderNum").val());

			//管理品番、商品名、どちらかが入っていたらtrue
			if (addManagementCode != "" || addItemNm != "") {

				//管理品番、商品名、どちらかが入っていなかったらアラート
				if (addManagementCode == "" || addItemNm == "") {

					alert('管理品番・商品名は必須項目です。');
					return false;

				//管理品番、商品名、が入っていて数量が無い場合アラート
				} else if (addOrderNum == 0) {

					alert('数量が0です。');
					return false;
				}
			//管理品番、商品名がなく、数量が入っていたらアラート
			} else if (addOrderNum > 0) {
				alert('管理品番・商品名は必須項目です。');
				return false;
			}
			itemCnt++;
		}

		if (itemCnt == 0) {
			alert('商品がひとつも入力されていません。');
			return false;
		}

		if (confirm("新規注文書を登録します。よろしいですか？")) {

			$(".overlay").css("display", "block");
			$(".message").text("登録中");

			var mall = "";

			mall += $("#kts").val()
					+ $("#sharakuin").val() + $("#rarugusu").val() + $("#tFour").val()
					+ $("#bcr").val() + $("#cyberEco").val() + $("#shop").val();

			$(".mall").val(mall);
			$(".addOrderNum").prop('disabled',false);
			goTransaction("registryDomesticOrderSlip.do");
		} else {
			return false;
		}

	}

	//**************** 更新 *************************************************************************************

	function orderUpdate() {

		//伝票情報必須項目の判定

		var senderRemarksCount = unescape(encodeURIComponent($(".senderRemarks").val())).length;
		var index = $(".senderRemarks").val().length;
		if (index > 400) {
			alert("MEMOは400文字までです。");
		}

		if ($(".itemOrderDate").val() == "") {

			alert('注文日は必須項目です。');
			return false;
		}

		if ($("#sysCorporationId").val() == "") {

			alert('会社は必須項目です。');
			return false;
		}

		if ($("#kts").val() == "" && $("#sharakuin").val() == "" &&  $("#tFour").val() == "" &&
		$("#rarugusu").val() == "" && $("#bcr").val() == "" &&  + $("#cyberEco").val() == "" &&
		$("#shop").val() == "") {

			alert('モールは必須項目です。');
			return false;
		}

		if ($(".orderNo").val() == "") {

			alert('受注番号は必須項目です。');
			return false;
		}

		if ($(".noteTurn").val() == "") {

			alert('注番は必須項目です。');
			return false;
		}

		//商品情報必須項目の判定：既存登録レコード
		var showTr = $(".domestic");

		for (var i = 0; i <= $(".domestic").size(); i++) {

			var managementCode = showTr.eq(i).find(".detailManagementCode").val();
			var itemNm = showTr.eq(i).find(".detailItemNm").val();
			var orderNum = new Number(showTr.eq(i).find(".detailOrderNum").val());

			//数量が入っていなかったらアラート
			if (orderNum == 0) {

				alert('数量は1以上が必須です。');
				return false;
			}
		}

		//更新時、必須項目の判定：追加登録レコード
		var showTr = $(".addDomestic");

		for (var i = 0; i <= $(".addDomestic").size(); i++) {

			var addManagementCode = showTr.eq(i).find(".addManagementCode").val();
			var addItemNm = showTr.eq(i).find(".addItemNm").val();
			var addOrderNum = new Number(showTr.eq(i).find(".addOrderNum").val());

			//管理品番、商品名、どちらかが入っていたらtrue
			if (addManagementCode != "" || addItemNm != "") {

				//管理品番、商品名、どちらかが入っていなかったらアラート
				if (addManagementCode == "" || addItemNm == "") {

					alert('管理品番・商品名は必須項目です。');
					return false;

					//管理品番、商品名、が入っていて数量が無い場合アラート
				} else if (addOrderNum == 0) {

					alert('数量が0です。');
					return false;
				}
				//管理品番、商品名がなく、数量が入っていたらアラート
			} else if (addOrderNum > 0) {
				alert('管理品番・商品名は必須項目です。');
				return false;
			}
		}

		//追加されていなくても更新
		if (confirm("注文書を更新します。よろしいですか？")) {

			$(".overlay").css("display", "block");
			$(".message").text("更新中");

			var mall = "";

			mall += $("#kts").val()
					+ $("#sharakuin").val() + $("#rarugusu").val() + $("#tFour").val()
					+ $("#bcr").val() + $("#cyberEco").val() + $("#shop").val();

			$(".mall").val(mall);
			$(".addOrderNum").prop('disabled',false);
			goTransaction("updateDomesticOrderSlip.do");
		} else {
			return false;
		}
	}

	//*********** 注文書出力  ******************************************************************************************
	function exportCreate() {

		//Actionに送る値設定
		var printCheckFlag = "off";

		if ($("#printFlgOn").prop('checked')) {
			if (!confirm("注文書出力後、ステータスが「注文前」の商品は\nステータスが移動します。よろしいですか？")) {
				return;
			}
			printCheckFlag = "on";
		} else {
			if (!confirm("印刷確認が済んでいません。\n注文書を出力しますか？")) {
				return;
			}
		}
		$(".overlay").css("display", "block");
		$.ajax({
			url : "./exportDomesticOrderAcceptanceList.do",
			type : 'POST',
			data : {'printCheckFlag':printCheckFlag},

			success : function(data, text_status, xhr) {
				$(".overlay").css("display", "none");
				window.open('domesticOrderAcceptancePrintOutFile.do', '_new');
			},
			error : function(data, text_status, xhr) {
				$(".overlay").css("display", "none");
				alert("pdfファイルの作成に失敗しました。");
			}
		});
	}
</script>
</html:html>