<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />

	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<link rel="stylesheet" href="./css/domesticItemsearch.css" type="text/css" />
	<link rel="stylesheet" href="./css/select2.css"/>

	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>

	<script src="./js/shortcut.js" language="javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>
	<script src="./js/select2.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>

	<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>

<!--
【商品検索画面】
ファイル名：domesticItemSearch.jsp
作成日：2016/12/14
作成者：野澤法都

（画面概要）

国内注文書作成画面詳細・国内注文書作製画面から子画面として開く。
親画面で「商品検索」ボタンを押した際、品番と商品名に入力されている値を
引継ぎそのまま検索をかける。

・検索ボタン押下：品番・商品名を条件に検索を実行する。商品名は部分一致。
・選択ボタン押下：親画面に選択された商品の値を渡し、画面を閉じる。

（注意・補足）

-->

<script type="text/javascript">


	var SEARCH_ITEM_TYPE_ORDER = "0";			//国内注文書登録時検索種別値
	var SEARCH_ITEM_TYPE_EXHIBITION = "1";		//セット商品登録時検索種別値

	var RESULT_ITEM_TYPE_NORMAL = "0";				//セット商品フラグ比較値：通常商品
	var RESULT_ITEM_TYPE_SET = "1";				//セット商品フラグ比較値：セット商品
	var RESULT_ITEM_TYPE_FORM = "2";				//セット商品フラグ比較値：構成商品

	$(document).ready(function(){
		$("#nmSearchFlg").val("0");
		$("#nmKanaSearchFlg").val("0");
	});

	$(function () {

		//セレクトボックス内検索
		$('.select').select2();

	//**************** メッセージエリア表示設定 **************************************************************************************

		if(!$("#registryDto.message").val()) {
			if($("#messageFlg").val() == "0") {
				$("#messageArea").addClass("registrySuccess");
			}
			if($("#messageFlg").val() == "1"){
				$("#messageArea").addClass("registryFailure");
			}
			$("#messageArea").fadeOut(4000);
		}
	//******************************************************************************************************

		//メーカー名が変更された場合メーカー名ｶﾅも変更する
			$('#makerNmSearch').bind('change',function () {
				if ($("#nmSearchFlg").val() == "0") {
					$("#nmKanaSearchFlg").val("1");
	 			$('select[id=makerNmKanaSearch]').val($('select[id=makerNmSearch]').val()).trigger('change');
				} else {
					$("#nmSearchFlg").val("0");
				}
			});

		//メーカー名ｶﾅが変更されたらメーカー名も変更する
			$('#makerNmKanaSearch').bind('change',function () {
				if ($("#nmKanaSearchFlg").val() == "0") {
					$("#nmSearchFlg").val("1");
					$('#makerNmSearch').val($('#makerNmKanaSearch').val()).trigger('change');
				} else {
					$("#nmKanaSearchFlg").val("0");
				}
			});


		$(".selecte").click( function () {

			//親画面の商品エリア
			var itemArea = window.opener.$("tr.addDomestic");
			//親画面で押された検索ボタンインデックス
			var idx = window.opener.$("#openerIdx").val();

			//選択ボタンが押された行のデータ
			var selectArea = $(this).parents("tr.addDomestic");

			//国内注文検索
			if ($("#searchItemType").val() == SEARCH_ITEM_TYPE_ORDER) {

				if (selectArea.find(".setItemFlg").val() == RESULT_ITEM_TYPE_SET) {
					//最初にセット商品を注文商品レコードに反映させる
					resultReflection(idx, itemArea, selectArea, RESULT_ITEM_TYPE_SET);
					var setSysManagementId = selectArea.find(".resultSysManagementId").val();

					$.ajax({
						type : 'post'
						,url : 'searchSetDomesticOrder.do'
						,dataType : 'json'
						,data : {'sysManagementId' : selectArea.find(".resultSysManagementId").val()}
					}).done(function(data){
						if (data.length != 0) {
							idx++;
							for (var i = 0; i < data.length; i++) {
								itemArea.eq(idx).css('background-color','lightgoldenrodyellow');
								itemArea.eq(idx).find(".addManagementCode").val(data[i].managementCode);
								itemArea.eq(idx).find(".addItemNm").val(data[i].itemNm);
// 								itemArea.eq(idx).find(".addOrderNum").val(data[i].orderNum);
								itemArea.eq(idx).find(".addWholsesalerNm").html(data[i].wholsesalerNm);
								itemArea.eq(idx).find(".addWholsesalerNm").val(data[i].wholsesalerNm);
								itemArea.eq(idx).find(".addItemRateOver").val(data[i].itemRateOver);
								itemArea.eq(idx).find(".addHiddenWholsesalerNm").val(data[i].wholsesalerNm);
								itemArea.eq(idx).find(".addHiddenListPrice").val(data[i].listPrice);
								itemArea.eq(idx).find(".addOrderRemarks").html(data[i].orderRemarks);
								itemArea.eq(idx).find(".addMakerNm").html(data[i].makerNm);
								itemArea.eq(idx).find(".addHiddenMakerNm").val(data[i].makerNm);
								itemArea.eq(idx).find(".addMakerId").val(data[i].sysMakerId);
								itemArea.eq(idx).find(".addMakerId").val(data[i].sysMakerId).trigger('change');
								itemArea.eq(idx).find(".addMakerKanaId").val(data[i].sysMakerId).trigger('change');
								itemArea.eq(idx).find(".addMakerCode").val(data[i].makerCode);
								itemArea.eq(idx).find(".addPurchasingCost").val(data[i].purchasingCost);
								itemArea.eq(idx).find(".addOpenPriceFlg").val(data[i].openPriceFlg);
								itemArea.eq(idx).find(".addPostage").val(data[i].postage);
								itemArea.eq(idx).find(".addPostage").val(data[i].postage);
								itemArea.eq(idx).find(".addSysManagementId").val(data[i].sysManagementId);
								itemArea.eq(idx).find(".addOrderNum").val(data[i].itemNum);
								itemArea.eq(idx).find(".addOrderNum").prop('disabled',true);
								itemArea.eq(idx).find(".addOrderNum").parent(".ui-spinner").css('background-color','lightgray');
								itemArea.eq(idx).find(".addOrderNum").parent(".ui-spinner").removeClass("ui-spinner");
								itemArea.eq(idx).find(".addSetSysDomesticItemId").val(setSysManagementId);

								//定価にopenFlgがあれば表示にopen
								if (data[i].openPriceFlg == 0) {
									//金額の項目にカンマをつける
									//定価
									itemArea.eq(idx).find(".addListPrice").val(data[i].listPrice);
									var listPrice = itemArea.eq(idx).find(".addListPrice")[0];
									addComma(listPrice);
									itemArea.eq(idx).find(".addListPrice").html(listPrice.value + "円");
									itemArea.eq(idx).find(".addListPrice").val(listPrice.value);

								} else {
									itemArea.eq(idx).find(".addOpenPriceFlg").prop("checked", true);
									itemArea.eq(idx).find('.addListPrice').prop('disabled',true);
									itemArea.eq(idx).find('.addPurchasingCost').prop('disabled',true);
									itemArea.eq(idx).find('.addPurchasingCost').val("0");
									itemArea.eq(idx).find(".addListPrice").html("open");
									itemArea.eq(idx).find(".addListPrice").val(data[i].resultListPrice);
								}

								itemArea.eq(idx).find(".addArrivalScheduleDate").val(data[i].arrivalScheduleDate);

								itemArea.eq(idx).find(".addPickingFlg").prop("checked", false);
								itemArea.eq(idx).find(".addBillingFlag").prop("checked", false);

								//商品タイプを渡す
								itemArea.eq(idx).find(".addItemType").val(RESULT_ITEM_TYPE_FORM);

								idx++;
							}

							window.close();
						} else {
							window.close();
						}
					});

					//Ajaxでセット商品の構成商品を検索
					//構成商品用のhiddenListを作成？
					//親画面の商品エリアにeachで値を格納していく
				} else {
					resultReflection(idx, itemArea, selectArea, RESULT_ITEM_TYPE_NORMAL);
					window.close();
				}
			//SET商品登録時検索
			} else {
				resultReflection(idx, itemArea, selectArea, RESULT_ITEM_TYPE_NORMAL);
				window.close();
			}
		});

		if($("#itemListSize").val() == '1') {
			$(".selecte").click();
		}

		$(".search").click( function () {
			goTransaction("searchDomesticOrder.do");
		});
	});

	function resultReflection(idx, itemArea, selectArea, itemType) {

		itemArea.eq(idx).find(".addManagementCode").val(selectArea.find(".resultManagementCode").val());
		itemArea.eq(idx).find(".addItemNm").val(selectArea.find(".resultItemNm").val());
		itemArea.eq(idx).find(".addOrderNum").val(selectArea.find(".resultOrderNum").val());

		itemArea.eq(idx).find(".addWholsesalerNm").html(selectArea.find(".resultWholsesalerNm").val());
		itemArea.eq(idx).find(".addWholsesalerNm").val(selectArea.find(".resultWholsesalerNm").val());
		itemArea.eq(idx).find(".addItemRateOver").val(selectArea.find(".resultItemRateOver").val());
		itemArea.eq(idx).find(".addHiddenWholsesalerNm").val(selectArea.find(".resultWholsesalerNm").val());
		itemArea.eq(idx).find(".addHiddenListPrice").val(selectArea.find(".resultListPrice").val());

		itemArea.eq(idx).find(".addOrderRemarks").html(selectArea.find(".resultOrderRemarks").val());
		itemArea.eq(idx).find(".addMakerNm").html(selectArea.find(".resultMakerNm").val());
		itemArea.eq(idx).find(".addHiddenMakerNm").val(selectArea.find(".resultMakerNm").val());
		itemArea.eq(idx).find(".addMakerId").val(selectArea.find(".resultSysMakerId").val());

		itemArea.eq(idx).find(".addMakerId").val(selectArea.find(".resultSysMakerId").val()).trigger('change');
		itemArea.eq(idx).find(".addMakerKanaId").val(selectArea.find(".resultSysMakerId").val()).trigger('change');
		itemArea.eq(idx).find(".addMakerCode").val(selectArea.find(".resultMakerCode").val());
		itemArea.eq(idx).find(".addPurchasingCost").val(selectArea.find(".resultPurchasingCost").val());
		itemArea.eq(idx).find(".addOpenPriceFlg").val(selectArea.find(".resultOpenPriceFlg").val());
		itemArea.eq(idx).find(".addPostage").val(selectArea.find(".resultPostage").val());
		itemArea.eq(idx).find(".addPostage").val(selectArea.find(".resultPostage").val());
		itemArea.eq(idx).find(".addSysManagementId").val(selectArea.find(".resultSysManagementId").val());
		if (itemType == RESULT_ITEM_TYPE_NORMAL) {
			itemArea.eq(idx).find(".addSetSysDomesticItemId").val(0);
		} else {
			itemArea.eq(idx).css('background-color','powderblue');
			itemArea.eq(idx).find(".addSetSysDomesticItemId").val(selectArea.find(".resultSysManagementId").val());
		}

		//商品タイプを渡す
		itemArea.eq(idx).find(".addItemType").val(itemType);

		//定価にopenFlgがあれば表示にopen
		if (selectArea.find(".resultOpenPriceFlg").val() == 0) {

			//金額の項目にカンマをつける
			for (var i = 0; i < selectArea.size(); i++) {
				//定価
				var listPrice = selectArea.find(".resultListPrice")[0];

				addComma(listPrice);
			}
			itemArea.eq(idx).find(".addListPrice").html(selectArea.find(".resultListPrice").val() + "円");
			itemArea.eq(idx).find(".addListPrice").val(selectArea.find(".resultListPrice").val());

		} else {
			itemArea.eq(idx).find(".addOpenPriceFlg").prop("checked", true);
			itemArea.eq(idx).find('.addListPrice').prop('disabled',true);
			itemArea.eq(idx).find('.addPurchasingCost').prop('disabled',true);
			itemArea.eq(idx).find('.addPurchasingCost').val("0");
			itemArea.eq(idx).find(".addListPrice").html("open");
			itemArea.eq(idx).find(".addListPrice").val(selectArea.find(".resultListPrice").val());
		}

		itemArea.eq(idx).find(".addArrivalScheduleDate").val(selectArea.find(".resultArrivalScheduleDate").val());

		itemArea.eq(idx).find(".addPickingFlg").prop("checked", false);
		itemArea.eq(idx).find(".addBillingFlag").prop("checked", false);


	}


</script>
</head>

<html:form action="/subWinDomesticItemSearch">

	<nested:nest property="registryDto">
		<nested:hidden property="messageFlg" styleId="messageFlg"/>
		<nested:notEmpty property="message">
			<div id="messageArea">
				<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
			</div>
		</nested:notEmpty>
	</nested:nest>

<h4 class="head">出品DB商品検索</h4>

<div class="searchOptionArea">

<nested:nest property="domesticExhibitionSearchDTO">
	<table class="searchOptionTable">
		<nested:hidden property="openerIdx" styleId="openerIdx" />
		<nested:hidden property="searchItemType" styleId="searchItemType"/>

		<tr class="s_size_tr">
			<td class="s_size ">管理品番</td>
			<td class="s_size_td">
				<nested:text property="managementCode" styleClass="searchManagementCode checkAlnumHyp left" maxlength="11" size="15"/><br/>
				<span class="explain">(部分一致)</span>
			</td>
			<td class="s_size_th">商品名</td>
			<td class="s_size_td">
				<nested:text property="itemNm" styleClass="searchItemNm" size="21" /><br/>
				<span class="explain">(部分一致)</span>
			</td>
			<td>
				<label>
					<nested:checkbox property="setItemFlg"></nested:checkbox>セット商品
					<nested:hidden property="setItemFlg" value="off" styleId="setItemFlg"></nested:hidden>
				</label>
			</td>


		</tr>
	</table>
	<table class="searchOptionMakerTable">
		<tr class="">
			<td class="wholsesaler_td">問屋名</td>
			<td class="wholsesaler_text">
				<nested:text property="wholsesalerNm" styleClass="WholsesalerNm searchWholsesalerNm" styleId="WholsesalerNm"/>
				<br/><span class="explain">(部分一致)</span></td>
			<td class="maker_td">メーカー名</td>
			<td class="">
				<nested:select property="makerNm" styleClass="searchMakerNm select" styleId="makerNmSearch">
					<html:option value="">　</html:option>
					<html:optionsCollection property="domesticMakerNmList" label="makerNm" value="sysMakerId" />
				</nested:select>
				<input type="hidden" id="nmSearchFlg"/>
				メーカー名ｶﾅ
				<nested:select property="makerNmPhonetic" styleClass="searchMakerNmKana select"  styleId="makerNmKanaSearch">
					<html:option value="">　</html:option>
					<html:optionsCollection property="domesticMakerNmList" label="makerNmKana" value="sysMakerId" />
				</nested:select>
				<input type="hidden" id="nmKanaSearchFlg"/>
				<nested:hidden property="sysMakerId" styleId="searchMakerId"></nested:hidden>
			</td>

	</table>
</nested:nest>
</div>

<nested:hidden property="itemListSize" styleId="itemListSize"/>
	<div class="paging_num">
	<h3>全<nested:write property="itemListSize" />件</h3>
	<div class="searchButton"><a class="button_main search" href="Javascript:void(0);">検索</a></div>
</div>

<div class="out_Div">
	<div class="in_Div">
		<table class="list_table">
			<thead>
				<tr>
					<th class="wCode">管理品番</th>
					<th class="wNm">商品名</th>
					<th class="wNum">問屋</th>
					<th class="wNum">メーカー名</th>
					<th class="wButton"></th>
				</tr>
			</thead>
			<nested:iterate property="extendDomesticOrderItemSearchList" indexId="idx" id="searchItemList" >
			<tr class="addDomestic">
				<td class="wListCode"><nested:write property="managementCode" /></td>
				<td class="wListNm"><nested:write property="itemNm"/></td>
				<td class="wListNum"><nested:write property="wholsesalerNm"/></td>
				<td class="wListNum"><nested:write property="makerNm"/></td>
				<td class="wListButton"><a class="button_small_main selecte" href="Javascript:void(0);">選択</a></td>

 				<nested:hidden property="managementCode" styleClass="resultManagementCode"/>
 				<nested:hidden property="itemNm" styleClass="resultItemNm"/>
 				<nested:hidden property="makerNm" styleClass="resultMakerNm"/>
 				<nested:hidden property="orderNum" styleClass="resultOrderNum" />
 				<nested:hidden property="wholsesalerNm" styleClass="resultWholsesalerNm" />
 				<nested:hidden property="itemRateOver" styleClass="resultItemRateOver" />
 				<nested:hidden property="listPrice" styleClass="resultListPrice" />
 				<nested:hidden property="orderRemarks" styleClass="resultOrderRemarks" />
 				<nested:hidden property="sysMakerId" styleClass="resultSysMakerId" />
 				<nested:hidden property="makerCode" styleClass="resultMakerCode" />
 				<nested:hidden property="purchasingCost" styleClass="resultPurchasingCost" />
 				<nested:hidden property="openPriceFlg" styleClass="resultOpenPriceFlg" />
 				<nested:hidden property="arrivalScheduleDate" styleClass="resultArrivalScheduleDate" />
 				<nested:hidden property="postage" styleClass="resultPostage" />
 				<nested:hidden property="sysManagementId" styleClass="resultSysManagementId" />
 				<nested:hidden property="setItemFlg" styleClass="setItemFlg"></nested:hidden>
 			</tr>
			</nested:iterate>
		</table>
	</div>
</div>

</html:form>
</html:html>