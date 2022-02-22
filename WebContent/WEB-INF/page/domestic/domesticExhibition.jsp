<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />

	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<link rel="stylesheet" href="./css/select2.css"/>
	<link rel="stylesheet" href="./css/domesticExhibition.css" type="text/css" />
	<link rel="stylesheet" href="./css/common.css"/>

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

	<title>出品DB一覧</title>


<!--
【業販売上一覧画面】
ファイル名：domesticExhibition.jsp
作成日：2016/11/21
作成者：野澤

（画面概要）


業販売上データの検索/一覧画面。
・行ダブルクリック：詳細画面に遷移する。
・商品毎のピッキングリスト出力済みフラグは、画面から操作、更新が可能。
・「ピッキングリスト・納品書出力」ボタン押下：画面に表示されているデータを対象にピッキングリストと納品書のPDFを別タブで出力する(ページ単位)。
	売上データ毎にピッキングリスト→納品書の順に出す。ピッキングリスト出力済フラグが立っている商品は出力されない。
・「トータルピッキングリスト出力」ボタン押下：検索結果の商品データをリスト化したPDFを別タブで出力する(ページ単位)。
・「出庫処理」ボタン押下：出庫処理画面に遷移する。
・「一括入金ボタン」ボタン押下：画面に表示されているデータを対象に、入金処理を行う。
・「B2」ボタン押下：画面に表示されている、配送先が「ヤマト運輸」の売り上げの伝票用csvをダウンロードする。
・「e飛伝」ボタン押下：画面に表示されている、配送先が「佐川急便」の売り上げの伝票用csvをダウンロードする。
・「日本郵政」ボタン押下：画面に表示されている、配送先が「日本郵便」の売り上げの伝票用Excelファイルをダウンロードする。
・「見積書」ボタン押下：画面に表示されている売り上げの見積書を一括でPDFに出力する（別タブ）
・「注文請書」ボタン押下：画面に表示されている売り上げの注文請書を一括でPDFに出力する（別タブ）
・「検索結果をダウンロード」ボタン押下：検索結果の売上データと商品データ全てをエクセルにダウンロードする。



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
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initDomesticExhibitionList">

		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>

	<h4 class="heading">出品データベース<br/>一覧</h4>


	<fieldset class="searchOptionField">
		<legend id="searchOptionOpen">▲隠す</legend>

		<input type="hidden" name="orderManagementSearchDTO.searchPreset" value="0" id="searchPreset">



		<div id="searchOptionArea">
			<nested:nest property="domesticExhibitionSearchDTO">
			<nested:hidden property="sysManagementId" styleId="sysManagementId" />
			<table id="orderTable" style="float:left">
				<tr>
					<td>管理品番</td>
					<td><nested:text property="managementCode" styleClass="managementCodeFrom checkAlnumHyp left" ></nested:text>（部分一致）</td>
				</tr>
				<tr class="teila">
					<td>定価</td>
					<td><nested:text property="listPriceFrom" maxlength="20" styleClass="listPriceFrom numText right" ></nested:text> ～<nested:text property="listPriceTo"  maxlength="20" styleClass="listpriceTo numText right" size="10"></nested:text></td>
				</tr>
				<tr>
					<td>掛率</td>
					<td>
						<nested:text property="itemRateOver" maxlength="10" size="4" styleClass="numText right"></nested:text><label> ％</label>
						<nested:select property="numberOrderType">
							<html:optionsCollection property="numberOrderMap" value="key" label="value" />
						</nested:select>
					</td>
				</tr>
				<tr>
					<td>メーカー名</td>
					<td>
						<nested:select property="makerNm" styleClass="makerNm select" styleId="makerNmSearch">
							<html:option value="">　</html:option>
							<html:optionsCollection property="domesticMakerNmList" label="makerNm" value="sysMakerId" />
						</nested:select>
						<input type="hidden" id="nmSearchFlg"/>
						メーカー名ｶﾅ
						<nested:select property="makerNmPhonetic" styleClass="makerNmPhonetic select"  styleId="makerNmKanaSearch">
							<html:option value="">　</html:option>
							<html:optionsCollection property="domesticMakerNmList" label="makerNmKana" value="sysMakerId" />
						</nested:select>
						<input type="hidden" id="nmKanaSearchFlg"/>
						<nested:hidden property="sysMakerId" styleId="sysMakerId"></nested:hidden>
					</td>

				</tr>
				<tr>
					<td>メーカー品番</td>
					<td><nested:text property="makerCode" styleClass="checkAlnumHyp"></nested:text>（前方一致）</td>
				</tr>
				<tr>
					<td>商品名</td>
					<td><nested:text property="itemNm" styleClass="text_w250"></nested:text>（部分一致）</td>
				</tr>
				<tr>
					<td>担当部署名</td>
					<td><nested:text property="departmentNm" styleClass="text_w250"></nested:text>（部分一致）</td>
				</tr>
				<tr>
					<td>問屋</td>
					<td><nested:text property="wholsesalerNm" styleClass="wholsesalerNm wholsesalerNmText"></nested:text>（部分一致）</td>
				</tr>
				<tr>
					<td>仕入原価</td>
					<td><nested:text property="purchasingCostFrom" styleClass="purchasingCostFrom priceText right textSize_100" ></nested:text> ～<nested:text property="purchasingCostTo" styleClass="purchasingCostTo priceText right textSize_100"></nested:text></td>
				</tr>
				<tr>
					<td>更新日</td>
					<td><nested:text property="updateDateFrom" styleClass="right calender"></nested:text>
					～<nested:text property="updateDateTo" styleClass="right calender"></nested:text></td>
				</tr>
				<tr>
					<td style="padding-left: 100px;"></td>
					<td>
						<label>
							<nested:checkbox property="setItemFlg"></nested:checkbox>セット商品
							<nested:hidden property="setItemFlg" value="off" styleId="setItemFlg"></nested:hidden>
						</label>
					</td>
				</tr>
			</table>
			</nested:nest>

			<table id="itemTable">
				<nested:nest property="domesticExhibitionSearchDTO">
					<tr>
						<td class="td_sort">並び順</td>
						<td>
							<nested:select property="sortFirst" styleId="itemListSortMap">
								<html:optionsCollection property="itemListSortMap" value="key" label="value" />
							</nested:select>
							<nested:select property="sortFirstSub" styleId="itemListSortOrder">
								<html:optionsCollection property="itemListSortOrder" value="key" label="value" />
							</nested:select>
						</td>
					</tr>
					<tr>
						<td>表示件数</td>
						<td>
							<nested:select property="listPageMax" styleId="listPageMaxMap">
								<html:optionsCollection property="listPageMaxMap" value="key" label="value" />
							</nested:select>&nbsp;件
						</td>
						<!-- Backlog No. BONCRE-488 対応 (クライアント側) -->
					</tr>
					<tr>
						<td style="padding: 0px 0px 0px 4px;"><a class="button_main search" href="javascript:void(0);" >検索</a></td>
						<td style="padding-top: 5px;"><a class="button_white clear" href="javascript:void(0);">リセット</a></td>
					</tr>
				</nested:nest>
			</table>

		</div>

		</fieldset>
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

<!-- 	<div id="errorMessageArea"> -->
<%-- 		<html:errors /> --%>
<!-- 	</div> -->

	<nested:notEmpty property="domesticExhibitionList">
		<div class="paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysManagementIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="domesticNowPage" ></span>&nbsp;/&nbsp;<span class="domesticPageNum"></span>&nbsp;ページ&nbsp;）</h3>
			</div>
			<div class="paging_num_top">
				<ul class="pager fr mb10">
						<li class="backButton"><a href="javascript:void(0);" id="backPage" >&laquo;</a></li>
						<li class="liFirstPage" ><a href="javascript:void(0);" id="firstPage" >1</a></li>
						<li class="3dotLineTop"><span>...</span></li>
					<li><a href="javascript:void(0);" class="pageNum" ></a></li>
						<li class="3dotLineEnd"><span>...</span></li>
						<li class="liLastPage" ><a href="javascript:void(0);" id="lastPage"  class="lastIdx" ></a></li>
						<li class="nextButton"><a href="javascript:void(0);" id="nextPage" >&raquo;</a></li>
				</ul>
			</div>
		</div>
	</nested:notEmpty>

		<nested:hidden property="sysManagementIdListSize" styleId="sysManagementIdListSize" />
		<nested:hidden property="pageIdx" styleId="pageIdx" />
		<nested:hidden property="domesticExhibitionListPageMax" styleId="domesticExhibitionListPageMax" />

		<table class="list_table">
			<tr>
				<td class="w20"><input type="checkbox" id="allOrderCheck"class="allOrderCheck checkBoxTransForm"></td>
				<th class="w100">管理品番<label class="necessary">※</label></th>
				<th class="w150">メーカー名</th>
				<th class="w150">メーカー名(カナ)</th>
				<th class="w100">メーカー品番</th>
				<th class="w210">商品名<label class="necessary">※</label></th>
				<th class="w100">問屋<label class="necessary">※</label></th>
				<th class="w30">open</th>
				<th class="w120">定価<label class="necessary">※</label></th>
				<th class="w80">掛率</th>
				<th class="w70">送料</th>
				<th class="w100">仕入原価</th>
				<th class="w70">更新日</th>
				<th class="w150">担当部署名<label class="necessary">※</label></th>
			</tr>

<!-- 表示分 -->
			<tbody>
			<nested:iterate property="domesticExhibitionList" id="seachList" indexId="idx">
				<tr class="domestic">
					<!-- 国内商品セットフラグ -->
					<nested:hidden property="setItemFlg" styleClass="setItemFlg"></nested:hidden>
					<!-- チェックボックス -->
					<td class="count td_center salesSlipRow w20">
						<nested:checkbox property="deleteCheckFlg" styleClass="deleteCheckFlg checkBoxTransForm"/>
						<nested:hidden property="sysManagementId" styleClass="sysManagementId"/>
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
					<td class="w100 td_center"><nested:text styleClass="makerCode checkAlnumHyp right textSize_100" property="makerCode"/></td>
					<!-- 商品名 -->
					<td class="w220 td_center"><nested:text styleClass="itemNm itemNmCheck w230" property="itemNm" /></td>
					<!-- 問屋 -->
					<td class="w100 td_center"><nested:text styleClass="wholsesalerNm wholsesalerNmCheck w90" property="wholsesalerNm" /></td>
					<!-- Openチェックボックス -->
					<td class="w30 td_center">
						<nested:checkbox property="openPriceFlg" styleClass="openPriceFlg openPriceFlgCheck checkBoxTransForm"/>
						<nested:hidden property="openPriceFlg" value="off"/>
					</td>
					<!-- 定価 -->
					<td class="w120"><nested:text styleClass="listPrice priceTextWrongMes right listPriceCheck textSize_100" property="listPrice" maxlength="20" />&nbsp;円</td>
					<!-- 掛率 -->
					<td class="w80"><nested:text styleClass="itemRateOver right priceSize" property="itemRateOver" maxlength="10" />&nbsp;％</td>
					<!-- 送料 -->
					<td class="w70"><nested:text styleClass="postage priceTextWrongMes right priceSize" property="postage" maxlength="20" />&nbsp;円</td>
					<!-- 仕入原価 -->
					<td class="w100"><nested:text styleClass="purchasingCost priceTextWrongMes right textSize_100" property="purchasingCost" maxlength="20" />&nbsp;円</td>
					<!-- 更新日時 -->
					<td class="w70 td_center"><span><nested:write property="updateDate"/></span></td>
					<!-- 担当部署名 -->
					<td class=" w220 td_center"><nested:text styleClass="dpartmentNm w230" property="departmentNm" /></td>
				</tr>
				</nested:iterate>
			<!-- 追加分 -->
			<nested:iterate property="addDomesticExhibitionList" indexId="idx">
				<tr class="addDomestic">
					<!-- チェックボックス：追加 -->
					<td class="count td_center salesSlipRow w20">
						<nested:checkbox property="removeCheckFlg" styleClass="orderCheckFlg checkBoxTransForm" value="idx"/>
					</td>
					<!-- 管理品番：追加 -->
					<td class="w100 td_center">
						<nested:text styleClass="addManagementCode checkAlnumHyp right textSize_100" property="managementCode"/>
					</td>
					<!-- メーカー名：追加 -->
					<td class=" w150 td_center">
						<nested:select property="sysMakerId" styleClass="addMakerNmTr select" styleId="addMakerNmTr${idx}" >
							<html:option value="0">　</html:option>
							<html:optionsCollection property="domesticMakerNmList" label="makerNm" value="sysMakerId" />
						</nested:select>
					</td>
					<!-- メーカー名ｶﾅ：追加 -->
					<td class=" w150 td_center">
						<nested:select property="sysMakerId" styleClass="addMakerNmKanaTr select" styleId="addMakerNmKanaTr${idx}">
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
							<nested:text styleClass="addMakerCode checkAlnumHyp textSize_100" property="makerCode"/>
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
					<td class=w120><nested:text styleClass="addListPrice priceTextWrongMes right textSize_100" property="listPrice" maxlength="20" />&nbsp;円</td>
					<!-- 掛率：追加 -->
					<td class="w80"><nested:text styleClass="addItemRateOver right priceSize" property="itemRateOver" maxlength="10"  />&nbsp;％</td>
					<!-- 送料：追加 -->
					<td class="w70"><nested:text styleClass="addPostage priceTextWrongMes right priceSize" property="postage" maxlength="20" />&nbsp;円</td>
					<!-- 仕入原価：追加 -->
					<td class="w100"><nested:text styleClass="addPurchasingCost priceTextWrongMes right textSize_100" property="purchasingCost" maxlength="20" />&nbsp;円</td>
					<td class="w70"><a class="button_white setRegistry" href="javascript:void(0);">SET</a></td>
					<!-- 担当部署名 -->
					<td class=" w220 td_center"><nested:text styleClass="addDpartmentNm w230" property="departmentNm" /></td>

				</tr>
			</nested:iterate>
			</tbody>
		</table>
<!-- ページ(下側) 20140407 安藤 -->
		<!-- ******************************************************************************** -->
		<nested:notEmpty property="domesticExhibitionList">
			<div class="under_paging_area">
				<div class="paging_total_top">
					<h3>全&nbsp;<nested:write property="sysManagementIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="domesticNowPage" ></span>&nbsp;/&nbsp;<span class="domesticPageNum"></span>&nbsp;ページ&nbsp;）</h3>
				</div>
				<div class="paging_num_top">
					<ul class="pager fr mb10 underPager">
							<li class="backButton"><a href="javascript:void(0);" id="underBackPage" >&laquo;</a></li>
							<li class="liFirstPage"><a href="javascript:void(0);" id="underFirstPage" >1</a></li>
							<li class="3dotLineTop"><span>...</span></li>
						<li><a href="javascript:void(0);" class="pageNum" ></a></li>
							<li class="3dotLineEnd"><span>...</span></li>
							<li class="liLastPage"><a href="javascript:void(0);" id="underLastPage"  class="lastIdx" ></a></li>
							<li class="nextButton"><a href="javascript:void(0);" id="underNextPage" >&raquo;</a></li>
					</ul>
				</div>
			</div>
		</nested:notEmpty>
		<!-- ******************************************************************************** -->
<!-- ページ(下側)　ここまで -->

	<div class="buttonArea">
		<ul>
			<li class="buttonSpase">
				<a class="button_main addregistry" href="javascript:void(0)" >新規追加</a>
			</li>
			<li class="buttonSpase">
				<a class="button_main saleListDownLoad update" href="javascript:void(0);">登録/更新</a>
			</li>
			<li class="buttonSpase">
				<a class="button_white domesticListDownLoad"  href="javascript:void(0);">検索結果をダウンロード</a>
			</li>
			<li class="buttonSpase">
				<a class="button_white delete" href="javascript:void(0);">選択した商品を削除</a>
			</li>
		</ul>

	</div>

	</html:form>
</html:html>
	<script type="text/javascript">
	window.onload = function(){
		$(".overlay").css("display", "none");
		$("#nmSearchFlg").val("0");
		$("#nmKanaSearchFlg").val("0");


		//仕入原価Fromのカンマを追加
		if ($(".purchasingCostFrom").val() != 0) {
			var costFrom = $(".purchasingCostFrom")[0];
			addComma(costFrom);
		}
		//仕入原価Toのカンマを追加
		if ($(".purchasingCostTo").val() != 0) {
			var costTo = $(".purchasingCostTo")[0];
			addComma(costTo);
		}
	};

	$(function() {
		/****************************************初期設定****************************************/
		//セレクトボックス内検索設定
		$('.select').select2();

		// モーダルウィンドウが開くときの処理
		// ダブルクリックで国内セット商品画面を開く
		$(".domestic").dblclick(function () {
			var tr = $(this);
			$("#sysManagementId").val(tr.find(".sysManagementId").val());
// 			subWin = window.open('initSetManegementEdit.do','sub','top=130,left=500, width=1500, height=520');
			subWin = FwGlobal.submitForm(document.forms[0],"/initSetManegementEdit","setManegementEditWindow","top=0,left=0,width=1770px,height=800px,scrollbars=1;");
// 			FwGlobal.submitForm(document.forms[0],"/setManegementEdit","clientSearchWindow","top=130,left=500,width=780px,height=520px,scrollbars=1;");

		});

		//SETボタン押下時新規SET登録モーダルを開く
		$(".setRegistry").click(function () {
			var tr = $(this);
			$("#sysManagementId").val(tr.find(".sysManagementId").val());
// 			subWin = window.open('initSetManegementEdit.do','sub','width=1500, height=520');
			subWin = FwGlobal.submitForm(document.forms[0],"/initSetManegementRegist","setManegementEditWindow","top=0,left=0,width=1770px,height=800px,scrollbars=1;");
// 			FwGlobal.submitForm(document.forms[0],"/setManegementEdit","clientSearchWindow","top=130,left=500,width=780px,height=520px,scrollbars=1;");
		});
		//メッセージエリア表示設定
		if(!$("#registryDto.message").val()) {
			if($("#messageFlg").val() == "0") {
				$("#messageArea").addClass("registrySuccess");
			}
			if($("#messageFlg").val() == "1"){
				$("#messageArea").addClass("registryFailure");
			}
			$("#messageArea").fadeOut(4000);
		}

		//初期表示時使用
		var addDomesticExhibitionTrs = $("tr.addDomestic");

		//初期表示時、追加分レコードを隠す
		var addDomesticExhibitionIdx = $("tr.addDomestic").size();

		for (var i = $("tr.addDomestic").size(); i > 0; i--) {
			if ($(".addManagementCode").eq(i - 1).val() == 0) {
				addDomesticExhibitionTrs.eq(i - 1).hide();
				addDomesticExhibitionIdx--;
			}
		}

		//カレンダー設定
		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');


		//openチェックボックス選択済みの場合、定価と仕入原価を入力不可にする
		$(".openPriceFlg").each(function() {
			if($(this).is(':checked')) {
				$(this).parents('tr').find('.listPrice').prop('disabled',true);
				$(this).parents('tr').find('.listPrice').val("0");
				$(this).parents('tr').find('.itemRateOver').prop('disabled',true);
				$(this).parents('tr').find('.itemRateOver').val("0");

				// XXX 入力された値は変更しない。
				//$(this).parents('tr').find('.purchasingCost').prop('disabled',true);
				//$(this).parents('tr').find('.purchasingCost').val("0");
			}
		});

		$(".domestic").each(function() {
			if ($(this).find(".setItemFlg").val() == "1") {
				$(this).find(":input").prop('disabled',true);
				$(this).find("input:checkbox.deleteCheckFlg").prop('disabled',false);
				$(this).find(".select").prop('disabled',true);
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


		//定価From
		if ($(".listPriceFrom").val() === "0") {
			$(".listPriceFrom").val("");
		}
		//定価To
		if ($(".listpriceTo").val() === "0") {
			$(".listpriceTo").val("");
		}
		//仕入原価From
		if ($(".purchasingCostFrom").val() === "0") {
			$(".purchasingCostFrom").val("");
		}
		//仕入原価To
		if ($(".purchasingCostTo").val() === "0") {
			$(".purchasingCostTo").val("");
		}

		//検索条件仕入原価Fromの0円除去
		$(".purchasingCostFrom").blur(function(){
			if ($(".purchasingCostFrom").val() == 0) {
				$(".purchasingCostFrom").val("");
			}
		});
		$(".purchasingCostFrom").focus(function(){
			if ($(".purchasingCostFrom").val() != 0) {
				$(".purchasingCostFrom").val(removeComma($(".purchasingCostFrom").val()));
			}
		});
		//検索条件仕入原価Toの0円除去
		$(".purchasingCostTo").blur(function(){
			if ($(".purchasingCostTo").val() == 0) {
				$(".purchasingCostTo").val("");
			}
		});
		$(".purchasingCostTo").focus(function(){
			if ($(".purchasingCostTo").val() != 0) {
				$(".purchasingCostTo").val(removeComma($(".purchasingCostTo").val()));
			}
		});

		//メーカー名が変更された場合メーカー名ｶﾅも変更する：検索条件欄
		$('#makerNmSearch').bind('change',function () {
			if ($("#nmSearchFlg").val() == "0") {
				$("#nmKanaSearchFlg").val("1");
				$('select[id=makerNmKanaSearch]').val($('select[id=makerNmSearch]').val()).trigger('change');
			} else {
				$("#nmSearchFlg").val("0");
			}
		});

		//メーカー名ｶﾅが変更されたらメーカー名も変更する：検索条件欄
		$('#makerNmKanaSearch').bind('change',function () {
			if ($("#nmKanaSearchFlg").val() == "0") {
				$("#nmSearchFlg").val("1");
				$('#makerNmSearch').val($('#makerNmKanaSearch').val()).trigger('change');
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
		$('.addMakerNmTr').bind('change',function () {
			if ($("#nmSearchFlg").val() == "0") {
					var makerNmTr = $(this)[0].id;
					var makerNmNo = makerNmTr.substr(12);
					$("#nmKanaSearchFlg").val("1");
					$("#addMakerNmKanaTr"+ makerNmNo).val($("#" + makerNmTr).val()).trigger('change');
			} else {
				$("#nmSearchFlg").val("0");
			}
		});

		//メーカー名ｶﾅが変更されたらメーカー名も変更する：追加レコード
		$('.addMakerNmKanaTr').bind('change',function () {
			if ($("#nmKanaSearchFlg").val() == "0") {
					var makerNmKanaTr = $(this)[0].id;
					var makerNmKanaNo = makerNmKanaTr.substr(16);
					$("#nmSearchFlg").val("1");
					$("#addMakerNmTr"+ makerNmKanaNo).val($("#" + makerNmKanaTr).val()).trigger('change');
			} else {
				$("#nmKanaSearchFlg").val("0");
			}
		});

		//オープン価格チェックボックスが選択された場合の制御：登録済みレコード
		$(".openPriceFlg").change(function() {
			if ($(this).is(':checked')) {
				$(this).parents('tr').find('.listPrice').prop('disabled',true);
				$(this).parents('tr').find('.listPrice').val(0);
				$(this).parents('tr').find('.itemRateOver').prop('disabled',true);
				$(this).parents('tr').find('.itemRateOver').val(0);
/* 				if ($(this).parents('tr').find('.listPrice').val() != "0") {
					$(this).prop('checked', false);
					alert("定価が0円以外の場合は選択出来ません。")
				} else {
					//$(this).parents('tr').find('.purchasingCost').prop('disabled',true);
					//$(this).parents('tr').find('.purchasingCost').val("0");
				}
 */			} else {
				$(this).parents('tr').find('.listPrice').prop('disabled',false);
				$(this).parents('tr').find('.itemRateOver').prop('disabled',false);
			}
		});
		//オープン価格チェックボックスが選択された場合の制御：追加レコード
		$(".addOpenPriceFlg").change(function() {
			if ($(this).is(':checked')) {
				$(this).parents('tr').find('.addListPrice').prop('disabled',true);
				$(this).parents('tr').find('.addListPrice').val(0);
				$(this).parents('tr').find('.addItemRateOver').prop('disabled',true);
				$(this).parents('tr').find('.addItemRateOver').val(0);
/* 				if ($(this).parents('tr').find('.addListPrice').val() != "0") {
					$(this).prop('checked', false);
					alert("定価が0円以外の場合は選択出来ません。")
				} else {
					$(this).parents('tr').find('.addListPrice').prop('disabled',true);
					//$(this).parents('tr').find('.addPurchasingCost').prop('disabled',true);
					//$(this).parents('tr').find('.addPurchasingCost').val("0");
				}
 */			} else {
				$(this).parents('tr').find('.addListPrice').prop('disabled',false);
				$(this).parents('tr').find('.addItemRateOver').prop('disabled',false);
			}
		});




		if ($("tr.domestic").size() != 0) {
			//検索結果があれば検索欄を隠す処理
			if($('#searchOptionOpen').text() == "▼絞り込み") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼絞り込み");
			}
			$('#searchOptionArea').slideToggle("fast");

		}


		//金額の項目にカンマをつける
		for (var i = 0; i < $(".domestic").size(); i++) {
			//定価
			var listPrice = $(".listPrice").eq(i)[0];
			//送料
			var postage = $(".postage").eq(i)[0];
			//仕入原価
			var purchasingCost = $(".purchasingCost").eq(i)[0];

			listPrice = addComma(listPrice);
			postage = addComma(postage);
			purchasingCost = addComma(purchasingCost);
		}

		//リセットボタン押下時
			$(".clear").click(function(){

			if ((!confirm("検索欄の入力したものをリセットします。よろしいですか？"))) {
				return;
			}
			$("#nmKanaSearchFlg").val("1");
			$('select[id=makerNmKanaSearch]').val("").trigger('change');
			$("#nmSearchFlg").val("1");
			$('#makerNmSearch').val("").trigger('change');
					$("#searchOptionArea input, #searchOptionArea select").each(function(){
							if (this.type == "checkbox" || this.type == "radio") {
									this.checked = false;
							} else {
									$(this).val("");
							}
					});
					$("#itemListSortMap").val("1");
					$("#itemListSortOrder").val("1");
					$("#listPageMaxMap").val("1");

			});

		//検索条件エリアのリンク押下時
		$('#searchOptionOpen').click(function () {

			if($('#searchOptionOpen').text() == "▼絞り込み") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼絞り込み");
			}

			$('#searchOptionArea').slideToggle("fast");
		});



//**********************************************ページ数関連************************************************

		if ($("#sysManagementIdListSize").val() != 0) {
			var domesticPageNum = Math.ceil($("#sysManagementIdListSize").val() / $("#domesticExhibitionListPageMax").val());

			$(".domesticPageNum").text(domesticPageNum);
			$(".domesticNowPage").text(Number($("#pageIdx").val()) + 1);

			var i;

			if (0 == $("#pageIdx").val()) {
				$(".pager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
				$(".underPager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
			}

			var startIdx;
			// maxDispは奇数で入力
			var maxDisp = 7;
			// domesticPageNumがmaxDisp未満の場合maxDispの値をdomesticPageNumに変更
			if (domesticPageNum < maxDisp) {

				maxDisp = domesticPageNum;

			}

			var center = Math.ceil(Number(maxDisp) / 2);
			var pageIdx = new Number($("#pageIdx").val());

			// 現在のページが中央より左に表示される場合
			if (center >= pageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(domesticPageNum);
				$(".liFirstPage").hide();

			// 現在のページが中央より右に表示される場合
			} else if (domesticPageNum - (center - 1) <= pageIdx + 1) {

				startIdx = domesticPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

			// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#pageIdx").val() - (center - 2);
				$(".lastIdx").html(domesticPageNum);

			}

			$(".pageNum").html(startIdx);
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

				$(".3dotLineTop").hide();

			}

			if ((domesticPageNum <= 8) || ((domesticPageNum - center) <= (pageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (domesticPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}


			for (i = startIdx; i < endIdx && i < domesticPageNum; i++) {
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
//**********************************************ページ遷移関連********************************************************
		$(".pageNum").click (function () {

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val($(this).text() - 1);
			goTransaction("domesticExhibitionPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".domesticPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
			goTransaction("domesticExhibitionPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("domesticExhibitionPageNo.do");
		});

//ページ送り（上側）
		//先頭ページへ
		$("#firstPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(0);

			goTransaction("domesticExhibitionPageNo.do");
		});

		//最終ページへ
		$("#lastPage").click (function () {

			var maxPage = new Number($(".domesticPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);

			goTransaction("domesticExhibitionPageNo.do");
		});

		// ページ送り（下側）
		//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".domesticPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);

			goTransaction("domesticExhibitionPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("domesticExhibitionPageNo.do");
		});

		//20140403 安藤　ここから
		//先頭ページへ
		$("#underFirstPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(0);
			goTransaction("domesticExhibitionPageNo.do");
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".domesticPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);
			goTransaction("domesticExhibitionPageNo.do");
		});

//******************************************************************************************************


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
		//追加ボタン処理
		$(".addregistry").click(function () {

			if (addDomesticExhibitionIdx >= 5) {
				alert("一度に追加できるのは5件までです。");
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
			tr.find('.addDepartmentNm').addClass(" departmentNmCheck");

			tr.show();
			addDomesticExhibitionIdx++;
		});

		//登録ボタン処理
		$(".update").click(function () {
			var emptyMngmntCdCnt = 0;
			var emptyItmNmCnt = 0;
			var emptyWhlsslrNmCnt = 0;
			var emptyOpnPrcFlgCnt = 0;
			var emptyPrcLstCnt = 0;
			var emptyDepartmentNmCnt = 0;

			if ($(".codeCheck").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

		/****************入力チェックSTART***************/
			//管理品番未入力チェック
			$(".codeCheck").each(function() {
				if($(this).val() == "") {
					emptyMngmntCdCnt++;
				}
			});
			//商品名未入力チェック
			$(".itemNmCheck").each(function() {
				if($(this).val() == "") {
					emptyItmNmCnt++;
				}
			});
			//問屋未入力チェック
			$(".wholsesalerNmCheck").each(function() {
				if($(this).val() == "") {
					emptyWhlsslrNmCnt++;
				}
			});
			//open/定価入力チェック
			$(".openPriceFlgCheck").each(function() {
				if (!$(this).prop('checked') && $(this).parents('tr').find('.listPriceCheck').val() == "0") {
					emptyOpnPrcFlgCnt++;
				}
			});
			//open/定価入力チェック
			$(".dpartmentNm").each(function() {
				if($(this).val() == "") {
					emptyDepartmentNmCnt++;
				}
			});


			if (emptyMngmntCdCnt > 0) {
				alert('管理品番が未入力の列があります。');
				return false;
			}
			if (emptyItmNmCnt > 0) {
				alert('商品名が未入力の列があります。');
				return false;
			}
			if (emptyWhlsslrNmCnt > 0) {
				alert('問屋が未入力の列があります。');
				return false;
			}
			if (emptyOpnPrcFlgCnt > 0) {
				alert("openが未チェックかつ、定価が0円の列があります。")
				return false;
			}
			if (emptyDepartmentNmCnt > 0) {
				alert("担当部署名は必須入力してください。")
				return false;
			}
		/*****************入力チェックEND****************/


			//登録の際、カンマをはずす：表示用
			for (var i = 0; i < $(".domestic").size(); i++) {

				var listPrice = $(".listPrice").eq(i).val();
				var postage = $(".postage").eq(i).val();
				var purchasingCost = $(".purchasingCost").eq(i).val();
				var itemRateOver = $(".itemRateOver").eq(i).val();

				$(".listPrice").eq(i).val(removeComma(listPrice));
				$(".postage").eq(i).val(removeComma(removeComma(postage)));
				$(".purchasingCost").eq(i).val(removeComma(removeComma(purchasingCost)));
				$(".itemRateOver").eq(i).val(removeComma(removeComma(itemRateOver)));

			}
			//登録の際、カンマをはずす：追加用
			for (var i = 0; i < $(".addDomestic").size(); i++) {

				var listPrice = $(".addListPrice").eq(i).val();
				var postage = $(".addPostage").eq(i).val();
				var purchasingCost = $(".addPurchasingCost").eq(i).val();
				var addItemRateOver = $(".addItemRateOver").eq(i).val();

				$(".addListPrice").eq(i).val(removeComma(listPrice));
				$(".addPostage").eq(i).val(removeComma(removeComma(postage)));
				$(".addPurchasingCost").eq(i).val(removeComma(removeComma(purchasingCost)));
				$(".addItemRateOver").eq(i).val(removeComma(removeComma(addItemRateOver)));
			}


			//仕入原価From/Toのカンマを除去
			deleteComma();

			$(".overlay").css("display", "block");
			$(".message").text("更新中");

			goTransaction('updateDomesticExhibition.do');
		});

		//一括削除ボタン押下時
		var j = 0;
		$(".delete").click (function () {

			if ($(".orderCheckFlg:checked").length == 0
					&& $(".deleteCheckFlg:checked").length == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			//チェックボックスにチェックが入っている数をカウントしアラートの文言に反映させる
			var deleteCheckCount = $('.deleteCheckFlg:checked').length;

// 			if ((!confirm("選択された商品を削除します。よろしいですか？"))) {
// 				return;
// 			}
			if ((!confirm("選択された" + deleteCheckCount + "件の商品を削除します。よろしいですか？"))) {
				return;
			}

			//追加分行削除
			var selectArea = $("tr.addDomestic");
			var checkCount = 0;

			//追加5行分
			for (var i = 0; i < selectArea.size(); i++) {

				//チェックボックスにチェックされているか判定
				if ($('.showCheckFlg').eq(checkCount).prop('checked')) {

					addDomesticExhibitionIdx--;
					//削除してずらす
					for (j = checkCount; j < selectArea.size(); j++) {

						//最終行の判定
						if (j == addDomesticExhibitionIdx) {

							clearAddDomesticLastRow(selectArea.eq(addDomesticExhibitionIdx));
							selectArea.eq(j).hide();
						} else {
							var copyFromTr = selectArea.eq(j + 1);
							var copyToTr = selectArea.eq(j);
							copyDomestic(copyFromTr, copyToTr);
						}
					}
				} else {
					checkCount++;
				}
			}


			//仕入原価From/Toのカンマを除去
			deleteComma();

			//登録分にチェックがあるか確認
			for (var i = 0; i < selectArea.size(); i++) {
				if ($('.deleteCheckFlg').eq(i).prop('checked')) {
					$(".overlay").css("display", "block");
					$(".message").text("削除中");
				}
			}
			goTransaction("deleteDomesticExhibition.do");
			return false;
		});

		//追加分選択行の削除
		function copyDomestic(copyFromTr, copyToTr) {

			copyToTr.find(".orderCheckFlg").prop('checked', $('.orderCheckFlg').eq(j + 1).prop('checked'));
			copyToTr.find(".addManagementCode").val(copyFromTr.find(".addManagementCode").val());
			copyToTr.find(".addMakerNmTr").val(copyFromTr.find(".addMakerNmTr").val()).trigger('change');
			copyToTr.find(".addMakerCode").val(copyFromTr.find(".addMakerCode").val());
			copyToTr.find(".addItemNm").val(copyFromTr.find(".addItemNm").val());
			copyToTr.find(".addWholsesalerNm").val(copyFromTr.find(".addWholsesalerNm").val());
			copyToTr.find(".addOpenPriceFlg").prop('checked',$('.addOpenPriceFlg').eq(j + 1).prop('checked'));
			copyToTr.find(".addListPrice").prop('disabled',$('.addOpenPriceFlg').eq(j + 1).prop('checked'));
			copyToTr.find(".addListPrice").val(copyFromTr.find(".addListPrice").val());
			copyToTr.find(".addItemRateOver").val(copyFromTr.find(".addItemRateOver").val());
			copyToTr.find(".addPostage").val(copyFromTr.find(".addPostage").val());
			copyToTr.find(".addPurchasingCost").val(copyFromTr.find(".addPurchasingCost").val());
			copyToTr.find(".addDepartmentNm").val(copyFromTr.find(".addDepartmentNm").val());

			copyFromTr.find(".orderCheckFlg").prop('checked', false);
			copyFromTr.find(".addManagementCode").val("");
			copyFromTr.find(".addMakerNmTr").val("").trigger('change');
			copyFromTr.find(".addListPrice").prop('disabled',$('.addOpenPriceFlg').eq(j + 2).prop('checked'));
			copyFromTr.find(".addOpenPriceFlg").prop('checked',$('.addOpenPriceFlg').eq(j + 2).prop('checked'));
			copyFromTr.find(".addMakerCode").val("");
			copyFromTr.find(".addItemNm").val("");
			copyFromTr.find(".addWholsesalerNm").val("");
			copyFromTr.find(".addListPrice").val(0);
			copyFromTr.find(".addItemRateOver").val(0);
			copyFromTr.find(".addPostage").val(0);
			copyFromTr.find(".addPurchasingCost").val(0);
			copyFromTr.find(".addDepartmentNm").val(0);
		}

		//追加分最終行の削除
		function clearAddDomesticLastRow(domesticRow){
			domesticRow.find(".addMakerNmTr").val("").trigger('change');
			domesticRow.find(".orderCheckFlg").prop('checked', false);
			domesticRow.find(".orderCheckFlg").removeClass('showCheckFlg');
			domesticRow.find(".addManagementCode").removeClass('codeCheck');
			domesticRow.find(".addManagementCode").val("");
			domesticRow.find(".addMakerNmTr").val("0");
			domesticRow.find(".addMakerNmKanaTr").val("0");
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
			domesticRow.find(".addDepartmentNm").val(0);
		}

		//検索ボタン処理
		$(".search").click (function () {
			$("#searchPreset").val(0);
			$("#sysManagementId").val(0);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");

			//仕入原価From/Toのカンマを除去
			deleteComma();

			$("#sysMakerId").val($("#makerNmSearch").val());
			goTransaction('searchDomesticExhibition.do');
		});



		//******************************************************************************************

		//検索結果ダウンロードボタン押下時の処理
		$(".domesticListDownLoad").click (function () {
			if ($(".sysManagementId").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			//仕入原価From/Toのカンマを除去
			deleteComma();
			goTransaction("domesticListDownload.do");
		});

		//*************************** 既存レコード仕入原価計算 ***************************************************************

		//定価
		//定価選択時
		$(".listPrice").focus(function(){
			var index = $(".listPrice").index(this);
			if ($(".listPrice").eq(index).val() == 0) {
				$(".listPrice").eq(index).val("");
			} else {
				$(".listPrice").eq(index).val(removeComma($(".listPrice").eq(index).val()))
			}
		});
		$(".listPrice").blur(function(){
			var index = $(".listPrice").index(this);
			if ($(".listPrice").eq(index).val() == "") {
				$(".listPrice").eq(index).val(0);
			}
			//仕入原価計算
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------
			if (!$(this).parents('tr').find('.openPriceFlg').is(':checked')) {
				var listPrice = $(".listPrice").eq(index).val();
				var itemRateOver = $(".itemRateOver").eq(index).val();
				var purchasingCost = $(".purchasingCost").eq(index).val();
				listPrice = removeComma(listPrice);
				itemRateOver = removeComma(itemRateOver);
				purchasingCost = removeComma(purchasingCost);

				if (itemRateOver != 0) {
					purchasingCostCalc(index);
				}
				
				if (itemRateOver == 0 && purchasingCost != 0) {
					itemRateOverCalc(index);
				}
			}
		});

		//掛率
		$(".itemRateOver").focus(function(){
			var index = $(".itemRateOver").index(this);
			if ($(".itemRateOver").eq(index).val() == 0) {
				$(".itemRateOver").eq(index).val("");
			}
		});
		$(".itemRateOver").on('input', function(){
			var index = $(".itemRateOver").index(this);
			var val = $(".itemRateOver").eq(index).val();
			var rmVal = val.substring(val.indexOf('.') + 3);			
			if (parseInt(rmVal) > 0) {
				var calc = parseInt(val * 100) / 100;
				val = val.substring(0, val.length-rmVal.length);
				$(".itemRateOver").eq(index).val(calc);
			}
		});
		$(".itemRateOver").blur(function(){
			var index = $(".itemRateOver").index(this);
			if ($(".itemRateOver").eq(index).val() == "") {
				$(".itemRateOver").eq(index).val(0);
			}
			//仕入原価計算
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------
			if (!$(this).parents('tr').find('.openPriceFlg').is(':checked')) {
				var listPrice = $(".listPrice").eq(index).val();
				var itemRateOver = $(".itemRateOver").eq(index).val();
				var purchasingCost = $(".purchasingCost").eq(index).val();
				listPrice = removeComma(listPrice);
				itemRateOver = removeComma(itemRateOver);
				purchasingCost = removeComma(purchasingCost);

				if (purchasingCost == 0 || purchasingCost != 0 && listPrice != 0) {
					if (itemRateOver != 0) {
						purchasingCostCalc(index);
					}
				}
				
				if (listPrice == 0 && purchasingCost != 0) {
					listPriceCalc(index);
				}
			}
		});

		//送料
		$(".postage").focus(function(){
			var index = $(".postage").index(this);
			if ($(".postage").eq(index).val() == 0) {
				$(".postage").eq(index).val("");
			} else {
				$(".postage").eq(index).val(removeComma($(".postage").eq(index).val()))
			}
		});
		$(".postage").blur(function(){
			var index = $(".postage").index(this)
			if ($(".postage").eq(index).val() == "") {
				$(".postage").eq(index).val(0);
			}
			//仕入原価計算
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------
			if (!$(this).parents('tr').find('.openPriceFlg').is(':checked')) {
				if ($(this).parents('tr').find('.itemRateOver').val() != 0) {
					// purchasingCostCalc(index);
				}
			}
		});

		//仕入原価：0を消す表示用
		$(".purchasingCost").focus(function(){
			var index = $(".purchasingCost").index(this)
			if ($(".purchasingCost").eq(index).val() == 0) {
				$(".purchasingCost").eq(index).val("");
			} else {
				$(".purchasingCost").eq(index).val(removeComma($(".purchasingCost").eq(index).val()))
			}
		});
		//仕入原価：0を付ける
		$(".purchasingCost").blur(function(){
			var index = $(".purchasingCost").index(this)
			if ($(".purchasingCost").eq(index).val() == "") {
				$(".purchasingCost").eq(index).val(0);
			}
			//仕入原価計算
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------

			if (!$(this).parents('tr').find('.openPriceFlg').is(':checked')) {
				var listPrice = $(".listPrice").eq(index).val();
				var itemRateOver = $(".itemRateOver").eq(index).val();
				var purchasingCost = $(".purchasingCost").eq(index).val();
				listPrice = removeComma(listPrice);
				itemRateOver = removeComma(itemRateOver);
				purchasingCost = removeComma(purchasingCost);

				if (itemRateOver == 0 && listPrice != 0) {
					itemRateOverCalc(index);
				}
				
				if (listPrice == 0 && itemRateOver != 0) {
					listPriceCalc(index);
				}
			}
		});

		//*************************** 追加レコード仕入原価計算 ***************************************************************


		//定価
		$(".addListPrice").focus(function(){
			var index = $(".addListPrice").index(this);
			if ($(".addListPrice").eq(index).val() == 0) {
				$(".addListPrice").eq(index).val("");
			} else {
				$(".addListPrice").eq(index).val(removeComma($(".addListPrice").eq(index).val()))
			}
		});
		$(".addListPrice").blur(function(){
			var index = $(".addListPrice").index(this);
			if ($(".addListPrice").eq(index).val() == "") {
				$(".addListPrice").eq(index).val(0);
			}
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------
			if (!$(this).parents('tr').find('.addOpenPriceFlg').is(':checked')) {
				var listPrice = $(".addListPrice").eq(index).val();
				var itemRateOver = $(".addItemRateOver").eq(index).val();
				var purchasingCost = $(".addPurchasingCost").eq(index).val();
				listPrice = removeComma(listPrice);
				itemRateOver = removeComma(itemRateOver);
				purchasingCost = removeComma(purchasingCost);

				if (itemRateOver != 0) {
					addPurchasingCostCalc(index);
				}
				
				if (itemRateOver == 0 && purchasingCost != 0) {
					addItemRateOverCalc(index);
				}
			}
		});

		//掛率
		$(".addItemRateOver").focus(function(){
			var index = $(".addItemRateOver").index(this);
			if ($(".addItemRateOver").eq(index).val() == 0) {
				$(".addItemRateOver").eq(index).val("");
			}
		});
		$(".addItemRateOver").on('input', function(){
			var index = $(".addItemRateOver").index(this);
			var val = $(".addItemRateOver").eq(index).val();
			var rmVal = val.substring(val.indexOf('.') + 3);			
			if (parseInt(rmVal) > 0) {
				var calc = parseInt(val * 100) / 100;
				val = val.substring(0, val.length-rmVal.length);
				$(".addItemRateOver").eq(index).val(calc);
			}
		});
		$(".addItemRateOver").blur(function(){
			var index = $(".addItemRateOver").index(this);
			if ($(".addItemRateOver").eq(index).val() == "") {
				$(".addItemRateOver").eq(index).val(0);
			}
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------
			if (!$(this).parents('tr').find('.addOpenPriceFlg').is(':checked')) {
				var listPrice = $(".addListPrice").eq(index).val();
				var itemRateOver = $(".addItemRateOver").eq(index).val();
				var purchasingCost = $(".addPurchasingCost").eq(index).val();
				listPrice = removeComma(listPrice);
				itemRateOver = removeComma(itemRateOver);
				purchasingCost = removeComma(purchasingCost);

				if (purchasingCost == 0 || purchasingCost != 0 && listPrice != 0) {
					if (itemRateOver != 0) {
						addPurchasingCostCalc(index);
					}
				}
				
				if (listPrice == 0 && purchasingCost != 0) {
					addListPriceCalc(index);
				}
			}
		});

		//送料
		$(".addPostage").focus(function(){
			var index = $(".addPostage").index(this);
			if ($(".addPostage").eq(index).val() == 0) {
				$(".addPostage").eq(index).val("");
			} else {
				$(".addPostage").eq(index).val(removeComma($(".addPostage").eq(index).val()))
			}
		});
		$(".addPostage").blur(function(){
			var index = $(".addPostage").index(this)
			if ($(".addPostage").eq(index).val() == "") {
				$(".addPostage").eq(index).val(0);
			}
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------
			if (!$(this).parents('tr').find('.addOpenPriceFlg').is(':checked')) {
				if ($(this).parents('tr').find('.addItemRateOver').val() != 0) {
					// addPurchasingCostCalc(index);
				}
			}
		});


		//仕入原価：0を消す
		$(".addPurchasingCost").focus(function(){
			var index = $(".addPurchasingCost").index(this)
			if ($(".addPurchasingCost").eq(index).val() == 0) {
				$(".addPurchasingCost").eq(index).val("");
			} else {
				$(".addPurchasingCost").eq(index).val(removeComma($(".addPurchasingCost").eq(index).val()))
			}
		});
		//仕入原価：0を付ける
		$(".addPurchasingCost").blur(function(){
			var index = $(".addPurchasingCost").index(this)
			if ($(".addPurchasingCost").eq(index).val() == "") {
				$(".addPurchasingCost").eq(index).val(0);
			}
			//--------------------------------------------------------------------------------------------------------------
			// オープンフラグにチェックが入っていない尚且つ　掛率が０かnullの場合に自動計算は行わない
			//又はオープンフラグにチェックが入っている尚且つ　掛率が０かnullの場合に自動計算は行わない
			//--------------------------------------------------------------------------------------------------------------
			if (!$(this).parents('tr').find('.addOpenPriceFlg').is(':checked')) {
				var listPrice = $(".addListPrice").eq(index).val();
				var itemRateOver = $(".addItemRateOver").eq(index).val();
				var purchasingCost = $(".addPurchasingCost").eq(index).val();
				listPrice = removeComma(listPrice);
				itemRateOver = removeComma(itemRateOver);
				purchasingCost = removeComma(purchasingCost);

				if (itemRateOver == 0 && listPrice != 0) {
					addItemRateOverCalc(index);
				}
				
				if (listPrice == 0 && itemRateOver != 0) {
					addListPriceCalc(index);
				}
			}
		});


		$(".overlay").css("display", "none");
	});

	//仕入原価計算：表示用
	function purchasingCostCalc(index) {
		var listPrice = $(".listPrice").eq(index).val();
		var itemRateOver = $(".itemRateOver").eq(index).val();

		listPrice = removeComma(listPrice);
		itemRateOver = removeComma(itemRateOver);

		var calc = 0;

		//--------------------------------------------------------------------------------------------------------------
		// 定価や掛け率の数値に関係なく次の計算を行っている
		//
		// 1. 定価に掛率を乗算した値を算出(定価×掛率)
		// 2. 定価をfloat型の変数にcastする。掛率は0.01を乗算した上でfloat型にcastしている
		// 3. castした定価と掛率の小数部の位置を比較し値が大きい方の小数部(大きい小数部)を取得する
		// 4. 大きい小数部と10をべき乗する
		// 5. べき乗した結果と定価×掛率を除算する
		// 6. 5の計算結果に送料の値を加算する
		//--------------------------------------------------------------------------------------------------------------

		if (itemRateOver != 0 && listPrice != 0) {
			calc = itemRateOver * listPrice / 100;
			calc = Math.round(calc);
		}

		$(".purchasingCost").eq(index).val(calc);
		calc = $(".purchasingCost").eq(index)[0];
		addComma(calc);
	}

	function listPriceCalc(index) {
		var itemRateOver = $(".itemRateOver").eq(index).val();
		var purchasingCost = $(".purchasingCost").eq(index).val();

		itemRateOver = removeComma(itemRateOver);
		purchasingCost = removeComma(purchasingCost);

		var calc = 0;
		if (itemRateOver != 0 && purchasingCost != 0) {
			calc = purchasingCost / itemRateOver * 100;
			calc = Math.round(calc);
		}

		$(".listPrice").eq(index).val(calc);
		calc = $(".listPrice").eq(index)[0];
		addComma(calc);
	}

	function itemRateOverCalc(index) {
		var listPrice = $(".listPrice").eq(index).val();
		var purchasingCost = $(".purchasingCost").eq(index).val();

		listPrice = removeComma(listPrice);
		purchasingCost = removeComma(purchasingCost);

		var calc = 0;
		if (listPrice != 0 && purchasingCost != 0) {
			calc = purchasingCost / listPrice * 100;
			calc = Math.round(calc * 100) / 100;
		}

		$(".itemRateOver").eq(index).val(calc);
		calc = $(".itemRateOver").eq(index)[0];
		addComma(calc);
	}

	//仕入原価計算：追加用
	function addPurchasingCostCalc(index) {
		var listPrice = $(".addListPrice").eq(index).val();
		var itemRateOver = $(".addItemRateOver").eq(index).val();

		listPrice = removeComma(listPrice);
		itemRateOver = removeComma(itemRateOver);


		//--------------------------------------------------------------------------------------------------------------
		// 定価や掛け率の数値に関係なく次の計算を行っている
		//
		// 1. 定価に掛率を乗算した値を算出(定価×掛率)
		// 2. 定価をfloat型の変数にcastする。掛率は0.01を乗算した上でfloat型にcastしている
		// 3. castした定価と掛率の小数部の位置を比較し値が大きい方の小数部(大きい小数部)を取得する
		// 4. 大きい小数部と10をべき乗する
		// 5. べき乗した結果と定価×掛率を除算する
		// 6. 5の計算結果に送料の値を加算する
		//--------------------------------------------------------------------------------------------------------------

		var calc = 0;
		if (itemRateOver != 0 && listPrice != 0) {
			calc = itemRateOver * listPrice / 100;
			calc = Math.round(calc);
		}

		$(".addPurchasingCost").eq(index).val(calc);
		calc = $(".addPurchasingCost").eq(index)[0];
		addComma(calc);
	}

	function addListPriceCalc(index) {
		var itemRateOver = $(".addItemRateOver").eq(index).val();
		var purchasingCost = $(".addPurchasingCost").eq(index).val();

		itemRateOver = removeComma(itemRateOver);
		purchasingCost = removeComma(purchasingCost);

		var calc = 0;
		if (itemRateOver != 0 && purchasingCost != 0) {
			calc = purchasingCost / itemRateOver * 100;
			calc = Math.round(calc);
		}

		$(".addListPrice").eq(index).val(calc);
		calc = $(".addListPrice").eq(index)[0];
		addComma(calc);
	}

	function addItemRateOverCalc(index) {
		var listPrice = $(".addListPrice").eq(index).val();
		var purchasingCost = $(".addPurchasingCost").eq(index).val();

		listPrice = removeComma(listPrice);
		purchasingCost = removeComma(purchasingCost);

		var calc = 0;
		if (listPrice != 0 && purchasingCost != 0) {
			calc = purchasingCost / listPrice * 100;
			calc = Math.round(calc * 100) / 100;
		}

		$(".addItemRateOver").eq(index).val(calc);
		calc = $(".addItemRateOver").eq(index)[0];
		addComma(calc);
	}

	//原価の計算メソッド (引数：定価、掛率)
	function calcCost(value1, value2) {

		var listPrice = parseFloat(value1);
		var upDateItemRateOver = parseFloat((value2) * 0.01);

		// それぞれの小数点の位置を取得
		var dotPosition1 = getDotPosition(listPrice);
		var dotPosition2 = getDotPosition(upDateItemRateOver);

		// 位置の値が大きい方（小数点以下の位が多い方）の位置を取得
		var max = Math.max(dotPosition1, dotPosition2);

		// 大きい方に小数の桁を合わせて文字列化、
		// 小数点を除いて整数の値にする
		var intValue1 = parseFloat((listPrice.toFixed(max) + '').replace('.', ''));
		var intValue2 = parseFloat((upDateItemRateOver.toFixed(max) + '').replace('.', ''));

		// 10^N の値を計算

		if (max == 1) {
			max = max + 1;
		} else {
			max = max * 2;
		}
		var power = Math.pow(10, max);

		//整数値で引き算した後に10^Nで割る
		return [ intValue1, intValue2, power ];

	}
	//小数点の位置を探るメソッド
	function getDotPosition(value) {

		// 数値のままだと操作できないので文字列化する
		var strVal = String(value);
		var dotPosition = 0;

		//小数点が存在するか確認
		//小数点があったら位置を取得
		if (strVal.lastIndexOf('.') !== -1) {
			dotPosition = (strVal.length - 1) - strVal.lastIndexOf('.');
		}

		// 掛率変換の際に2進数→10進数への変換で小数点位置が膨大な数値となるのを防ぐためにmax最大2とします。
		if (dotPosition >= 2) {
			dotPosition = 2;
		}

		return dotPosition;
	}



	//検索条件の仕入原価カンマを除去
	function deleteComma() {
		//仕入原価Fromのカンマを除去
		if ($(".purchasingCostFrom").val() != 0) {
			$(".purchasingCostFrom").val(removeComma($(".purchasingCostFrom").val()));
		}
		//仕入原価Toのカンマを除去
		if ($(".purchasingCostTo").val() != 0) {
			$(".purchasingCostTo").val(removeComma($(".purchasingCostTo").val()));
		}
	}

	var subWin;

	function chkWin(){
		if(subWin!=null && subWin!=''){


			if(!subWin.closed){
				window.blur();
				subWin.focus();
			}
		}
	}


	</script>