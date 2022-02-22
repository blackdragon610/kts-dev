<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	<head>
		<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
		<link rel="stylesheet" href="./css/domesticExhibition.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
		<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
		<link rel="stylesheet" href="./css/font-awesome.min.css"/>
		<link rel="stylesheet" href="./css/select2.css"/>



		<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
		<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

		<script src="./js/jquery.ui.core.min.js"></script>
		<script src="./js/jquery.ui.datepicker.min.js"></script>
		<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
		<script src="./js/validation.js" type="text/javascript"></script>
		<script src="./js/shortcut.js"></script>
		<script src="./js/select2.min.js"></script>
		<script src="./js/bootstrap.min.js"></script>
	<!-- 	<script src="./js/common.js"></script> -->

		<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
	<!-- 	<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script> -->

	<title>国内SET商品管理</title>

	<!--
	【国内SET商品編集画面】
	ファイル名：setDomesticExhibition.jsp
	作成日：2018/01/15
	作成者：齋藤

	（画面概要）

	国内商品のセット商品登録／編集


	（注意・補足）

	-->
	</head>
		<div class="overlay">
		<div class="messeage_box">
		<h1 class="message">読み込み中</h1>
			<BR />
			<p>Now Loading...</p>
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>
	<html:form action="/initSetManegementRegist">

		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>


		<nested:nest property="domesticExhibitionSearchDTO">
			<nested:hidden property="managementCode" styleId="searchManagementCode" />
			<nested:hidden property="itemNm" styleId="searchItemNm" />
			<nested:hidden property="makerNm" styleId="searchMakerNm"/>
			<nested:hidden property="sysMakerId" styleId="searchMakerId"/>
			<nested:hidden property="makerNmPhonetic" />
			<nested:hidden property="openerIdx" styleId="openerIdx" />
			<nested:hidden property="sysCorporationId" styleId="searchSysCorporationId" />
			<nested:hidden property="searchItemType" styleId="searchItemType"/>
			<nested:hidden property="setItemFlg" styleId="setItemFlg" />
		</nested:nest>
		<nested:nest property="setDomesticExhibitionDTO">
			<nested:equal value="0" property="sysManagementId">
				<h4 class="heading">国内セット商品登録</h4>
			</nested:equal>
			<nested:notEqual value="0" property="sysManagementId">
				<h4 class="heading">国内セット商品編集</h4>
			</nested:notEqual>
		</nested:nest>
		<div id="setDomesticExhibitionArea">

			<nested:nest property="setDomesticExhibitionDTO">
				<nested:hidden property="onloadActionType" styleId="onloadActionType" />
				<nested:hidden property="sysManagementId" styleId="sysManagementId" />
				<input type="hidden" id="nmKanaSearchFlg"/>
				<input type="hidden" id="nmSearchFlg"/>
				<div style="padding-left: 30px; padding-bottom: 5px;">
					<h3>セット商品</h3>
				</div>
				<table class="list_table">
					<tr>
						<th class="w100">管理品番<label class="necessary">※</label></th>
						<th class="w150">メーカー名<label class="necessary">※</label></th>
						<th class="w150">メーカー名(カナ)<label class="necessary">※</label></th>
						<th class="w100">メーカー品番</th>
						<th class="w210">商品名<label class="necessary">※</label></th>
						<th class="w100">問屋<label class="necessary">※</label></th>
						<th class="w30">open</th>
						<th class="w120">定価<label class="necessary">※</label></th>
						<th class="w80">掛率</th>
						<th class="w70">送料</th>
						<th class="w100">仕入原価</th>
						<th class="w70">更新日</th>
					</tr>
					<tbody>
						<tr class="setDomestic">
							<!-- 国内商品セットフラグ -->
							<nested:hidden property="setItemFlg" styleClass="setItemFlg"></nested:hidden>
							<!-- システム管理ID -->
							<nested:hidden property="sysManagementId" styleClass="sysManagementId"/>

							<!-- 管理品番 -->
							<td class="w100 td_center">
								<nested:text styleClass="setManagementCode checkAlnumHyp codeCheck right textSize_100" styleId="setManagementCode" property="managementCode" />
								<nested:hidden property="beforeManagementCode"></nested:hidden>
							</td>
							<!-- メーカー名 -->
							<td class="w150 td_center">
								<nested:select property="sysMakerId" styleClass="makerNm td_center makerNmTr select" styleId="setMakerNm">
									<html:option value="0">　</html:option>
									<html:optionsCollection property="domesticMakerNmList" label="makerNm" value="sysMakerId" />
								</nested:select>
							</td>
							<!-- メーカー名ｶﾅ -->
							<td class="w150 td_center">
								<nested:select property="sysMakerId" styleClass="makerNmPhonetic td_center makerNmKanaTr select" styleId="setMakerNmKana">
									<html:option value="0">　</html:option>
									<html:optionsCollection property="domesticMakerNmList" label="makerNmKana" value="sysMakerId" />
								</nested:select>
							</td>
							<!-- メーカー品番 -->
							<td class="w100 td_center"><nested:text styleClass="makerCode checkAlnumHyp right textSize_100" styleId="setMakerCode" property="makerCode"/></td>
							<!-- 商品名 -->
							<td class="w220 td_center"><nested:text styleClass="itemNm itemNmCheck w230" styleId="setItemNm" property="itemNm" /></td>
							<!-- 問屋 -->
							<td class="w100 td_center"><nested:text styleClass="setWholsesalerNm wholsesalerNmCheck w90" styleId="setWholsesalerNm" property="wholsesalerNm" /></td>
							<!-- Openチェックボックス -->
							<td class="w30 td_center">
								<nested:checkbox property="openPriceFlg" styleClass="openPriceFlg openPriceFlgCheck checkBoxTransForm" styleId="setOpenPriceFlg"/>
								<nested:hidden property="openPriceFlg" value="off"/>
							</td>
							<!-- 定価 -->
							<td class="w120"><nested:text styleClass="listPrice priceTextWrongMes  right listPriceCheck textSize_100 commaChange" styleId="setListPrice" property="listPrice" maxlength="20" />&nbsp;円</td>
							<!-- 掛率 -->
							<td class="w80"><nested:text styleClass="itemRateOver numText right priceSize commaChange" property="itemRateOver" maxlength="4" />&nbsp;％</td>
							<!-- 送料 -->
							<td class="w70"><nested:text styleClass="postage priceTextWrongMes right priceSize commaChange" styleId="setPostage" property="postage" maxlength="20" />&nbsp;円</td>
							<!-- 仕入原価 -->
							<td class="w100"><nested:text styleClass="purchasingCost priceTextWrongMes right textSize_100 commaChange" styleId="setPurchasingCost" property="purchasingCost" maxlength="20" />&nbsp;円</td>
							<!-- 更新日時 -->
							<td class="w70 td_center"><span><nested:write property="updateDate"/></span></td>
						</tr>
					</tbody>
				</table>
			</nested:nest>
		</div>

		<div id="errorMessageArea">
			<nested:nest property="errorDTO">
				<nested:notEmpty property="errorMessageList">
					<nested:iterate property="errorMessageList">
						<p class="errorMessage"><nested:write property="errorMessage"/></p>
					</nested:iterate>
				</nested:notEmpty>
			</nested:nest>
			<html:errors />
		</div>

		<div class="titleArea">
			<h3 >構成商品</h3>
	    </div>
		<div class="setButtonArea">

			<ul>
				<li class="buttonSpase">
					<a class="button_main addregistry" href="javascript:void(0);">追加</a>
				</li>
				<li class="buttonSpase">
					<a class="button_white delete" href="javascript:void(0);">削除</a>
				</li>
				<li class="buttonSpase">
					<a class="button_main priceCalg" href="javascript:void(0);">再計算</a>
				</li>

			</ul>
		</div>


		<div id="formDomesticExhibitionArea">
			<table class="list_table">
				<tr>
					<td class="w20"><input type="checkbox" id="allOrderCheck"class="allOrderCheck checkBoxTransForm"></td>
					<th class="w100">管理品番<label class="necessary">※</label></th>
					<th class="w150">メーカー名</th>
					<th class="w150">メーカー名(カナ)</th>
					<th class="w100">メーカー品番</th>
					<th class="w210">商品名</th>
					<th class="w100">問屋</th>
					<th class="w30">open</th>
					<th class="w120">定価</th>
					<th class="w80">掛率</th>
					<th class="w70">送料</th>
					<th class="w100">仕入原価</th>
					<th class="w70">数量</th>
				</tr>

			<!-- 表示分 -->
				<tbody>
					<nested:iterate property="formDomesticExhibitionList" id="seachList" indexId="idx">
						<tr class="domestic">
							<!-- 国内商品セットフラグ -->
							<nested:hidden property="setItemFlg" styleClass="setItemFlg"></nested:hidden>
							<!-- チェックボックス -->
							<td class="count td_center salesSlipRow w20">
								<nested:checkbox property="deleteCheckFlg" styleClass="deleteCheckFlg checkBoxTransForm"/>
								<nested:hidden property="formSysManagementId" styleClass="sysManagementId"/>
								<nested:hidden property="sysSetManagementId" styleClass="sysSetManagementId"/>
							</td>
							<!-- 管理品番 -->
							<td class="w100 td_center">
								<nested:text styleClass="managementCode checkAlnumHyp codeCheck right textSize_100" property="managementCode" />
								<nested:hidden property="beforeManagementCode"></nested:hidden>
							</td>
							<!-- メーカー名 -->
							<td class="w150 td_center">
								<nested:select property="sysMakerId" styleClass="makerNm td_center makerNmTr select" styleId="makerNmTr${idx}">
									<html:option value="0">　</html:option>
									<html:optionsCollection property="domesticMakerNmList" label="makerNm" value="sysMakerId" />
								</nested:select>
							</td>
							<!-- メーカー名ｶﾅ -->
							<td class="w150 td_center">
								<nested:select property="sysMakerId" styleClass="makerNmPhonetic td_center makerNmKanaTr select" styleId="makerNmKanaTr${idx}">
									<html:option value="0">　</html:option>
									<html:optionsCollection property="domesticMakerNmList" label="makerNmKana" value="sysMakerId" />
								</nested:select>
							</td>
							<!-- メーカー品番 -->
							<td class="w100 td_center">
								<nested:text styleClass="makerCode checkAlnumHyp right textSize_100" property="makerCode"/>
							</td>
							<!-- 商品名 -->
							<td class="w220 td_center">
								<nested:text styleClass="itemNm itemNmCheck w230" property="itemNm" />
							</td>
							<!-- 問屋 -->
							<td class="w100 td_center">
								<nested:text styleClass="wholsesalerNm wholsesalerNmCheck w90" property="wholsesalerNm" />
							</td>
							<!-- Openチェックボックス -->
							<td class="w30 td_center">
								<nested:checkbox property="openPriceFlg" styleClass="openPriceFlg openPriceFlgCheck checkBoxTransForm"/>
								<nested:hidden property="openPriceFlg" value="off"/>
							</td>
							<!-- 定価 -->
							<td class="w120">
								<nested:text styleClass="listPrice priceTextWrongMes right listPriceCheck textSize_100 commaChange" property="listPrice" maxlength="20" />&nbsp;円
							</td>
							<!-- 掛率 -->
							<td class="w80">
								<nested:text styleClass="itemRateOver numText right priceSize commaChange" property="itemRateOver" maxlength="4" />&nbsp;％
							</td>
							<!-- 送料 -->
							<td class="w70">
								<nested:text styleClass="postage priceTextWrongMes right priceSize commaChange" property="postage" maxlength="20" />&nbsp;円
							</td>
							<!-- 仕入原価 -->
							<td class="w100">
								<nested:text styleClass="purchasingCost priceTextWrongMes right textSize_100 commaChange" property="purchasingCost" maxlength="20" />&nbsp;円
							</td>
							<!-- 数量 -->
							<td class="w70 td_center">
								<nested:text styleClass="itemNum num numText ime_off" property="itemNum" maxlength="20" />
							</td>
						</tr>
					</nested:iterate>
					<!-- 追加分 -->
					<nested:iterate property="addformDomesticExhibitionList" indexId="idx">
						<tr class="addDomestic">
							<!-- チェックボックス：追加 -->
							<td class="count td_center salesSlipRow w20">
								<nested:checkbox property="removeCheckFlg" styleClass="orderCheckFlg checkBoxTransForm" value="idx"/>
							</td>
							<!-- 管理品番：追加 -->
							<td class="w100 td_center">
<!-- 								<a class="button_search search" id="button_a" href="javascript:void(0);">検</a> -->
								<nested:text styleClass="addManagementCode search checkAlnumHyp right textSize_100" property="managementCode"/>
								<nested:hidden property="formSysManagementId" styleClass="addSysManagementId"/>
							</td>
							<!-- メーカー名：追加 -->
							<td class=" w150 td_center">
								<nested:select property="sysMakerId" styleClass="addMakerId addMakerNmTr select" styleId="addMakerNm${idx}" >
									<html:option value="0">　</html:option>
									<html:optionsCollection property="domesticMakerNmList" label="makerNm" value="sysMakerId" />
								</nested:select>
							</td>
							<!-- メーカー名ｶﾅ：追加 -->
							<td class=" w150 td_center">
								<nested:select property="sysMakerId" styleClass="addMakerKanaId addMakerNmKanaTr select" styleId="addMakerNmKana${idx}">
									<html:option value="0">　</html:option>
									<html:optionsCollection property="domesticMakerNmList" label="makerNmKana" value="sysMakerId"/>
								</nested:select>
							</td>
							<!-- メーカー品番：追加 -->
							<nested:equal value="0" property="makerCode">
								<td class="w100 td_center">
									<nested:text styleClass="addMakerCode checkAlnumHyp textSize_100" property="makerCode" value=""/>
								</td>
							</nested:equal>
							<!-- メーカー品番：追加 -->
							<nested:notEqual value="0" property="makerCode">
								<td class="w100 td_center">
									<nested:text styleClass="addMakerCode checkAlnumHyp right textSize_100" property="makerCode"/>
								</td>
							</nested:notEqual>
							<!-- 商品名：追加 -->
							<td class=" w220 td_center"><nested:text styleClass="addItemNm w230" property="itemNm" /></td>
							<!-- 問屋：追加 -->
							<td class=" w100 td_center"><nested:text styleClass="addWholsesalerNm w90" property="wholsesalerNm" /></td>
							<!-- Openチェックボックス：追加 -->
							<td class="w30 td_center">
								<nested:checkbox styleClass="addOpenPriceFlg checkBoxTransForm" property="openPriceFlg"/>
								<nested:hidden property="openPriceFlg" value="off"/>
							</td>
							<!-- 定価：追加 -->
							<td class=w120><nested:text styleClass="addListPrice priceTextWrongMes right textSize_100 commaChange" property="listPrice" maxlength="20" />&nbsp;円</td>
							<!-- 掛率：追加 -->
							<td class="w80"><nested:text styleClass="addItemRateOver numText right priceSize commaChange" property="itemRateOver" maxlength="4"  />&nbsp;％</td>
							<!-- 送料：追加 -->
							<td class="w70"><nested:text styleClass="addPostage priceTextWrongMes right priceSize commaChange" property="postage" maxlength="20" />&nbsp;円</td>
							<!-- 仕入原価：追加 -->
							<td class="w100"><nested:text styleClass="addPurchasingCost priceTextWrongMes right textSize_100 commaChange" property="purchasingCost" maxlength="20" />&nbsp;円</td>
							<!-- 数量 -->
							<td class="w70 td_center"><nested:text styleClass="addOrderNum num numText ime_off" property="itemNum" styleId="setItemNum" maxlength="20" /></td>
						</tr>
					</nested:iterate>
				</tbody>
			</table>
		</div>

		<div class="buttonArea">
			<ul>
				<li class="buttonSpase">
					<nested:nest property="setDomesticExhibitionDTO">
						<nested:equal value="0" property="sysManagementId">
							<a class="button_main regist" href="javascript:void(0);">登録</a>
						</nested:equal>
						<nested:notEqual value="0" property="sysManagementId">
							<a class="button_main update" href="javascript:void(0);">更新</a>
						</nested:notEqual>
					</nested:nest>
				</li>
			</ul>
		</div>

	</html:form>
</html:html>

<script type="text/javascript">
	var ITEM_SEARCH_TYPE = "1";
	var ONLOAD_ACTION_TYPE_REGIST = "1";
	var ONLOAD_ACTION_TYPE_UPDATE = "2";
	var ONLOAD_ACTION_TYPE_DELETE = "3";
	var ONLOAD_ACTION_TYPE_RESET = "0";
	window.onload = function(){
		$(".overlay").css("display", "none");
		$("#nmSearchFlg").val("0");
		$("#nmKanaSearchFlg").val("0");
		addComma($("#setListPrice")[0]);
		addComma($("#setPostage")[0]);
		addComma($("#setPurchasingCost")[0]);

		if ($("#onloadActionType").val() == ONLOAD_ACTION_TYPE_REGIST) {
			if ((!confirm("セット商品が登録されました。\r\n画面を終了しますか？"))) {
				$("#onloadActionType").val(ONLOAD_ACTION_TYPE_RESET);
				return false;
			} else {
				window.close();
			}
		} else if ($("#onloadActionType").val() == ONLOAD_ACTION_TYPE_UPDATE){
			if ((!confirm("セット商品が更新されました。\r\n画面を終了しますか？"))) {
				$("#onloadActionType").val(ONLOAD_ACTION_TYPE_RESET);
				return false;
			} else {
				window.close();
			}
		} else if ($("#onloadActionType").val() == ONLOAD_ACTION_TYPE_DELETE){
			if ((!confirm("構成商品が削除されました。\r\n画面を終了しますか？"))) {
				$("#onloadActionType").val(ONLOAD_ACTION_TYPE_RESET);
				return false;
			} else {
				window.close();
			}
		}


	}

	$(function () {

		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1
		});

		$('.select').select2();
		//初期表示時使用
		var addDomesticExhibitionTrs = $("tr.addDomestic");

		//初期表示時、追加分レコードを隠す
		var addDomesticExhibitionIdx = $("tr.addDomestic").size();

		//メッセージエリア表示設定
		if(!$("#registryDto.message").val()) {
			if($("#messageFlg").val() == "0") {
				$("#messageArea").addClass("registrySuccess");
			}
			if($("#messageFlg").val() == "1"){
				$("#messageArea").addClass("registryFailure");
			}
			$("#messageArea").fadeOut(8000);
		}

		//構成商品レコードは編集不可とする
		$(".domestic").each(function() {
				$(this).find(":input").prop('disabled',true);
				$(this).find("input:checkbox.deleteCheckFlg").prop('disabled',false);
				$(this).find(".select").prop('disabled',true);
				$(this).find(".itemNum").prop('disabled',false);
		});

		$(".addDomestic").each(function() {
			$(this).find(":input").prop('disabled',true);
			$(this).find("input:checkbox.orderCheckFlg").prop('disabled',false);
			$(this).find(".select").prop('disabled',true);
			$(this).find(".itemNum").prop('disabled',false);
			$(this).find(".addManagementCode").prop('disabled',false);
		});

		for (var i = $("tr.addDomestic").size(); i > 0; i--) {
			if ($(".addManagementCode").eq(i - 1).val() == 0) {
				addDomesticExhibitionTrs.eq(i - 1).hide();
				addDomesticExhibitionIdx--;
			}
		}

		//openチェックボックス選択済みの場合、定価と仕入原価を入力不可にする：親商品
		if($("#setOpenPriceFlg").is(':checked')) {
			$('#setListPrice').prop('disabled',true);
			$('#setListPrice').val("0");
			$('#setPurchasingCost').prop('disabled',true);
			$('#setPurchasingCost').val("0");
		}
		//openチェックボックス選択済みの場合、定価と仕入原価を入力不可にする：構成商品
		$(".openPriceFlg").each(function() {
			if($(this).is(':checked')) {
				$(this).parents('tr').find('.listPrice').prop('disabled',true);
				$(this).parents('tr').find('.listPrice').val("0");
				$(this).parents('tr').find('.purchasingCost').prop('disabled',true);
				$(this).parents('tr').find('.purchasingCost').val("0");
			}
		});

		//メーカー名が変更された場合メーカー名ｶﾅも変更する：SETレコード
 		$('#setMakerNm').bind('change',function () {
 			if ($("#nmSearchFlg").val() == "0") {
 	 			$("#nmKanaSearchFlg").val("1");
 	 			$("#setMakerNmKana").val($('#setMakerNm').val()).trigger('change');
 			} else {
 				$("#nmSearchFlg").val("0");
 			}
 		});

		//メーカー名ｶﾅが変更されたらメーカー名も変更する：SETレコード
 		$('#setMakerNmKana').bind('change',function () {
 			if ($("#nmKanaSearchFlg").val() == "0") {
 	 			$("#nmSearchFlg").val("1");
 	 			$("#setMakerNm").val($("#setMakerNmKana").val()).trigger('change');
 			} else {
 				$("#nmKanaSearchFlg").val("0");
 			}
 		});

		//メーカー名が変更された場合メーカー名ｶﾅも変更する：登録済みレコード
 		$('.makerNmTr').bind('change',function () {
 			if ($("#nmSearchFlg").val() == "0") {
 	 			var makerNmTr = $(this)[0].id;
 	 			var makerNmNo = makerNmTr.substr(9);
 	 			$("#nmKanaSearchFlg").val("1");
 	 			$("#makerNmKanaTr"+ makerNmNo).val($("#" + makerNmTr).val()).trigger('change');
 			} else {
 				$("#nmSearchFlg").val("0");
 			}
 		});

		//メーカー名ｶﾅが変更されたらメーカー名も変更する：登録済みレコード
 		$('.makerNmKanaTr').bind('change',function () {
 			if ($("#nmKanaSearchFlg").val() == "0") {
 	 			var makerNmKanaTr = $(this)[0].id;
 	 			var makerNmKanaNo = makerNmKanaTr.substr(13);
 	 			$("#nmSearchFlg").val("1");
 	 			$("#makerNmTr"+ makerNmKanaNo).val($("#" + makerNmKanaTr).val()).trigger('change');
 			} else {
 				$("#nmKanaSearchFlg").val("0");
 			}
 		});

		//メーカー名が変更された場合メーカー名ｶﾅも変更する：追加レコード
 		$('.addMakerId').bind('change',function () {
 			if ($("#nmSearchFlg").val() == "0") {
 	 			var makerNmTr = $(this)[0].id;
 	 			var makerNmNo = makerNmTr.substr(10);
 	 			$("#nmKanaSearchFlg").val("1");
 	 			$("#addMakerNmKana"+ makerNmNo).val($("#" + makerNmTr).val()).trigger('change');
 			} else {
 				$("#nmSearchFlg").val("0");
 			}
 		});

		//メーカー名ｶﾅが変更されたらメーカー名も変更する：追加レコード
 		$('.addMakerKanaId').bind('change',function () {
 			if ($("#nmKanaSearchFlg").val() == "0") {
 	 			var makerNmKanaTr = $(this)[0].id;
 	 			var makerNmKanaNo = makerNmKanaTr.substr(14);
 	 			$("#nmSearchFlg").val("1");
 	 			$("#addMakerNm"+ makerNmKanaNo).val($("#" + makerNmKanaTr).val()).trigger('change');
 			} else {
 				$("#nmKanaSearchFlg").val("0");
 			}
 		});

		//オープン価格チェックボックスが選択された場合の制御：登録済みレコード
		$(".openPriceFlg").change(function() {
			if ($(this).is(':checked')) {
				if ($(this).parents('tr').find('.listPrice').val() != "0") {
					$(this).prop('checked', false);
					alert("定価が0円以外の場合は選択出来ません。")
				} else {
					$(this).parents('tr').find('.listPrice').prop('disabled',true);
					$(this).parents('tr').find('.purchasingCost').prop('disabled',true);
					$(this).parents('tr').find('.purchasingCost').val("0");
				}
			} else {
				$(this).parents('tr').find('.listPrice').prop('disabled',false);
				$(this).parents('tr').find('.purchasingCost').prop('disabled',false);
			}
		});
		//オープン価格チェックボックスが選択された場合の制御：追加レコード
		$(".addOpenPriceFlg").change(function() {
			if ($(this).is(':checked')) {
				if ($(this).parents('tr').find('.addListPrice').val() != "0") {
					$(this).prop('checked', false);
					alert("定価が0円以外の場合は選択出来ません。")
				} else {
					$(this).parents('tr').find('.addListPrice').prop('disabled',true);
					$(this).parents('tr').find('.addPurchasingCost').prop('disabled',true);
					$(this).parents('tr').find('.addPurchasingCost').val("0");
				}
			} else {
				$(this).parents('tr').find('.addListPrice').prop('disabled',false);
				$(this).parents('tr').find('.addPurchasingCost').prop('disabled',false);
			}
		});


		/************************ カンマの付与と除去START ************************/
		//金額の項目にカンマをつける
		$(".domestic").each(function () {
			var tr = $(this);
			var listPrice = tr.find(".listPrice");				//定価
			var postage = tr.find(".postage");					//送料
			var purchasingCost = tr.find(".purchasingCost");	//仕入原価
			addComma(listPrice[0]);
			addComma(postage[0]);
			addComma(purchasingCost[0]);
		});
		//レコードの金額
		$(".commaChange").focus(function(){
			var listPrice = $(this);
			if (listPrice.val() == 0) {
				listPrice.val("");
			} else {
				listPrice.val(removeComma(listPrice.val()));
			}
		});
		$(".commaChange").blur(function(){
			var listPrice = $(this);
			if (listPrice.val() == "") {
				listPrice.val(0);
			} else {
				addComma(listPrice[0]);
			}
		});
		/************************ カンマの付与と除去END ************************/



		//チェックボックス全件選択・解除
		$("#allOrderCheck").click(function() {
			if(this.checked) {
				$(".showCheckFlg").prop("checked", true);
				$(".deleteCheckFlg").prop("checked", true);
			} else {
				$(".orderCheckFlg").prop("checked", false);
				$(".deleteCheckFlg").prop("checked", false);
			}

		});

 		//登録・更新でエラーで画面に戻ってきたときにクラスの再設定
		$(".addDomestic").each(function () {
			if($(this).is(':visible')) {
				$(this).find('.orderCheckFlg').addClass(" showCheckFlg");
				$(this).find('.addManagementCode').addClass(" codeCheck");
				$(this).find('.addItemNm').addClass(" itemNmCheck");
				$(this).find('.addWholsesalerNm').addClass(" wholsesalerNmCheck");
				$(this).find('.addOpenPriceFlg').addClass(" openPriceFlgCheck");
				$(this).find('.addListPrice').addClass(" listPriceCheck");
			}
		});

		//追加分選択行の削除
		function copyAddDomestic(copyFromTr, copyToTr) {

			copyToTr.find(".orderCheckFlg").prop('checked', copyFromTr.find('.orderCheckFlg').prop('checked'));
			copyToTr.find(".addManagementCode").val(copyFromTr.find(".addManagementCode").val());
			copyToTr.find(".addMakerId").val(copyFromTr.find(".addMakerId").val()).trigger('change');
			copyToTr.find(".addMakerCode").val(copyFromTr.find(".addMakerCode").val());
			copyToTr.find(".addItemNm").val(copyFromTr.find(".addItemNm").val());
			copyToTr.find(".addWholsesalerNm").val(copyFromTr.find(".addWholsesalerNm").val());
			copyToTr.find(".addOpenPriceFlg").prop('checked',copyFromTr.find('.addOpenPriceFlg').prop('checked'));
			copyToTr.find(".addListPrice").prop('disabled',copyFromTr.find('.addOpenPriceFlg').prop('checked'));
			copyToTr.find(".addListPrice").val(copyFromTr.find(".addListPrice").val());
			copyToTr.find(".addItemRateOver").val(copyFromTr.find(".addItemRateOver").val());
			copyToTr.find(".addPostage").val(copyFromTr.find(".addPostage").val());
			copyToTr.find(".addPurchasingCost").val(copyFromTr.find(".addPurchasingCost").val());
			copyToTr.find(".addOrderNum").val(copyFromTr.find(".addOrderNum").val());

			copyFromTr.find(".orderCheckFlg").prop('checked', false);
			copyFromTr.find(".addManagementCode").val("");
			copyFromTr.find(".addMakerId").val("").trigger('change');
			copyFromTr.find(".addListPrice").prop('disabled',false);
			copyFromTr.find(".addOpenPriceFlg").prop('checked',false);
			copyFromTr.find(".addMakerCode").val("");
			copyFromTr.find(".addItemNm").val("");
			copyFromTr.find(".addWholsesalerNm").val("");
			copyFromTr.find(".addListPrice").val(0);
			copyFromTr.find(".addItemRateOver").val(0);
			copyFromTr.find(".addPostage").val(0);
			copyFromTr.find(".addPurchasingCost").val(0);
			copyFromTr.find(".addOrderNum").val(0);
		}

		//追加分最終行の削除
		function clearAddDomesticLastRow(domesticRow){
			domesticRow.find(".addMakerId").val("").trigger('change');
			domesticRow.find(".orderCheckFlg").prop('checked', false);
			domesticRow.find(".orderCheckFlg").removeClass('showCheckFlg');
			domesticRow.find(".addManagementCode").removeClass('codeCheck');
			domesticRow.find(".addManagementCode").val("");
			domesticRow.find(".addMakerId").val("0");
			domesticRow.find(".addMakerKanaId").val("0");
			domesticRow.find(".addMakerCode").val("");
			domesticRow.find(".addItemNm").removeClass('itemNmCheck');
			domesticRow.find(".addItemNm").val("");
			domesticRow.find(".addWholsesalerNm").val("");
			domesticRow.find(".addWholsesalerNm").removeClass('wholsesalerNmCheck');
			domesticRow.find(".addOpenPriceFlg").prop('checked',false);
			domesticRow.find(".addListPrice").prop('disabled',false);
			domesticRow.find(".addOpenPriceFlg").removeClass('openPriceFlgCheck');
			domesticRow.find(".addListPrice").val(0);
			domesticRow.find(".addListPrice").removeClass('listPriceCheck');
			domesticRow.find(".addItemRateOver").val(0);
			domesticRow.find(".addPostage").val(0);
			domesticRow.find(".addPurchasingCost").val(0);
			domesticRow.find(".addOrderNum").val(0);
		}

		//管理品番フォーカス中にEnterで検索実行
		$(".addManagementCode").on('keydown', function(e){
			if (( e.keyCode==32 || e.keyCode==13 ) && ( ! event.altKey )) {
				e.preventDefault();
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
			}
		});

		//国内商品検索ボタン(管理品番の中に表示する)
		// TODO 画面に余裕が無いためボタンは表示していない
// 		$(".search").click(function() {
// 			$("#searchItemNm").val("");
// 			$("#searchManagementCode").val("");
// 			$("#searchMakerNm").val("");
// 			$("#searchMakerId").val("");
// 			var salesItemArea = $("tr.addDomestic");
// 			var rowNum = $("tr.addDomestic").index($(this).parents("tr.addDomestic"));
// 			$("#searchManagementCode").val(salesItemArea.eq(rowNum).find(".addManagementCode").val());
// 			if (!$("#searchManagementCode").val()) {
// 				$("#searchItemNm").val(salesItemArea.eq(rowNum).find(".addItemNm").val());
// 			}
// 			$("#openerIdx").val(rowNum);
// 			$("#searchSysCorporationId").val($("#sysCorporationId").val());
// 			if (!$("#searchManagementCode").val()
// 				&& !$("#searchItemNm").val()) {
// 				FwGlobal.submitForm(document.forms[0], "/subWinDomesticItemSearch", "itemSearchWindow",	"top=0,left=500,width=870px,height=750px;");
// 			} else {
// 				FwGlobal.submitForm(document.forms[0], "/searchDomesticOrder", "itemSearchWindow", "top=0,left=500,width=870px,height=750px;");
// 			}
// 		});



		//*************************************************************************************//
		//******************************** ボタンイベントSTART ********************************//
		//*************************************************************************************//
		//追加ボタン処理
		$(".addregistry").click(function () {

			if (addDomesticExhibitionIdx >= 10) {
				alert("一度に追加できるのは10件までです。");
				return;
			}

			//一行分表示
			var trs = $("tr.addDomestic");
			var tr = trs.eq(addDomesticExhibitionIdx);
			tr.find('.orderCheckFlg').addClass(" showCheckFlg");
			tr.find('.addManagementCode').addClass(" codeCheck");
			tr.find('.addItemNm').addClass(" itemNmCheck");
			tr.find('.addWholsesalerNm').addClass(" wholsesalerNmCheck");
			tr.find('.addOpenPriceFlg').addClass(" openPriceFlgCheck");
			tr.find('.addListPrice').addClass(" listPriceCheck");

			tr.show();
			addDomesticExhibitionIdx++;
		});

		//再計算ボタン押下
		$(".priceCalg").click(function () {

			var setListPrice = 0;
			var setPostage = 0;
			var setPurchasingCost = 0;
			var setItemNum = $("#setItemNum").val();
			var objLstPrc = $("#setListPrice");
			var objPstg = $("#setPostage");
			var objPrchsngCst = $("#setPurchasingCost");


			//セット品番金額初期化
			objLstPrc.val(setListPrice);
			objPstg.val(setPostage);
			objPrchsngCst.val(setPurchasingCost);


			//表示分計算
			$(".domestic").each(function () {
				var tr = $(this);
				setItemNum = parseInt(removeComma(tr.find(".itemNum").val()));
				setListPrice += parseInt(removeComma(tr.find(".listPrice").val()));
				setPostage += parseInt(removeComma(tr.find(".postage").val()));
				setPurchasingCost += parseInt(removeComma(tr.find(".purchasingCost").val()));
			});
			//追加分計算
			//セット商品の登録時、構成商品の定価等をセット商品の定価等に加算する計算
			$(".addDomestic").each(function () {
				var tr = $(this);
				if (tr.find(".addManagementCode").val() != "") {
					setItemNum = parseInt(removeComma(tr.find(".addOrderNum").val()));
					//定価、送料、仕入原価に構成商品の数量を掛け算する計算
					setListPrice += parseInt(removeComma(tr.find(".addListPrice").val())) * setItemNum;
					setPostage += parseInt(removeComma(tr.find(".addPostage").val())) * setItemNum;
					setPurchasingCost += parseInt(removeComma(tr.find(".addPurchasingCost").val())) * setItemNum;
				}
			});

			//セット金額設定
			objLstPrc.val(setListPrice);
			objPstg.val(setPostage);
			objPrchsngCst.val(setPurchasingCost);
			//カンマ付与
			addComma(objLstPrc[0]);
			addComma(objPstg[0]);
			addComma(objPrchsngCst[0]);
		});

		//登録ボタン押下
		$(".regist").click(function () {

			//金額入力チェックとカンマ除去
			if (!checkSetPrice()) {
				return false;
			}

			$(".addDomestic").each(function() {
				$(this).find(":input").prop('disabled',false);
				$(this).find("input:checkbox.orderCheckFlg").prop('disabled',false);
				$(this).find(".select").prop('disabled',false);
			});


			$(".overlay").css("display", "block");
			$(".message").text("登録中");

			goTransaction("registSetManagementItem.do");

		});

		//更新ボタン押下
		$(".update").click(function () {

			//金額入力チェックとカンマ除去
			if (!checkSetPrice()) {
				return false;
			}

			$(".addDomestic").each(function() {
				$(this).find(":input").prop('disabled',false);
				$(this).find("input:checkbox.orderCheckFlg").prop('disabled',false);
				$(this).find(".select").prop('disabled',false);
			});


			$(".overlay").css("display", "block");
			$(".message").text("更新中");

			goTransaction("updateSetManagementItem.do");
		});

		//一括削除ボタン押下時
		$(".delete").click (function () {

			if ($(".orderCheckFlg:checked").length == 0
					&& $(".deleteCheckFlg:checked").length == 0) {
				alert("対象の情報がありません。");
				return false;
			}
			//チェックボックスにチェックが入っている数をカウントしアラートの文言に反映させる
			var deleteCheckCount = $('.deleteCheckFlg:checked').length + $('.orderCheckFlg:checked').length;

			if ((!confirm("選択された" + deleteCheckCount + "件の商品を削除します。よろしいですか？"))) {
				return;
			}

			//追加分行削除
			var selectAddArea = $("tr.addDomestic");
			var checkCount = 0;
			var j = 0;
			//追加5行分
			for (var i = 0; i < selectAddArea.size(); i++) {
				//チェックボックスにチェックされているか判定
				if ($('.showCheckFlg').eq(checkCount).prop('checked')) {
					addDomesticExhibitionIdx--;
					//削除してずらす
					for (j = checkCount; j < selectAddArea.size(); j++) {
						//最終行の判定
						if (j == addDomesticExhibitionIdx) {
							clearAddDomesticLastRow(selectAddArea.eq(addDomesticExhibitionIdx));
							selectAddArea.eq(j).hide();
						} else {
							var copyFromTr = selectAddArea.eq(j + 1);
							var copyToTr = selectAddArea.eq(j);
							copyAddDomestic(copyFromTr, copyToTr);
						}
					}
				} else {
					checkCount++;
				}
			}
			var selectDomesticArea = $("tr.domestic");
			//登録分にチェックがあるか確認
			for (var i = 0; i < selectDomesticArea.size(); i++) {
				if ($('.deleteCheckFlg').eq(i).prop('checked')) {
					$(".overlay").css("display", "block");
					$(".message").text("削除中");
	 				goTransaction("deleteSetDomesticExhibition.do");
				}
			}
			return false;
		});
		//*************************************************************************************//
		//******************************** ボタンイベントEND **********************************//
		//*************************************************************************************//

	});


	//金額入力チェックとカンマ除去
	function checkSetPrice() {

		//////////////////////////入力チェックSTART//////////////////////////
		if ($("#setManagementCode").val() == "") {
			alert('セット商品：管理品番が未入力です。');
			return false;
		}
		//メーカー名は未選択を選択できてしまうのでこの画面では必須項目とする。
		if ($("#setMakerNm").val() == "0") {
			alert('セット商品：メーカーが未選択です。');
			return false;
		}
		//if ($("#setMakerCode").val() == "") {
		//	alert('セット商品：メーカー品番が未入力です。');
		//	return false;
		//}
		if ($("#setItemNm").val() == "") {
			alert('セット商品：商品名が未入力です。');
			return false;
		}
		if ($("#setWholsesalerNm").val() == "") {
			alert('セット商品：問屋が未入力です。');
			return false;
		}
		if (!$("#setOpenPriceFlg").prop('checked') && $("#setListPrice").val() == "0") {
			alert("セット商品：openが未チェックかつ、定価が0円です。")
			return false;
		}
		////////////////////////////入力チェックEND////////////////////////////

		//問屋違いを確認する
		var wholsesaleFlg = true;
		var setWholsesaler = $("#setWholsesalerNm").val();
		$("tr.domestic").each(function () {
			if ($(this).find(".managementCode").val() != "") {
				if ($(this).find(".wholsesalerNm").val() != setWholsesaler) {
					wholsesaleFlg = false;
					return false;
				}
			}
		});
		//追加分レコードチェック
		$("tr.addDomestic").each(function () {
			if ($(this).find(".addManagementCode").val() != "") {
				if ($(this).find(".addWholsesalerNm").val() != setWholsesaler) {
					wholsesaleFlg = false;
					return false
				}
			}
		});

		if (!wholsesaleFlg) {
			alert('問屋は全て同じでなければなりません。');
			return false;
		}
		var objLstPrc = $("#setListPrice");
		var objPstg = $("#setPostage");
		var objPrchsngCst = $("#setPurchasingCost");

		objLstPrc.val(removeComma(objLstPrc.val()));
		objPstg.val(removeComma(objPstg.val()));
		objPrchsngCst.val(removeComma(objPrchsngCst.val()));

		return true;
	}


</script>