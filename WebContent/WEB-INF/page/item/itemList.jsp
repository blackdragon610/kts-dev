<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/itemList.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

<!--
【在庫一覧画面】
ファイル名：itemList.jsp
作成日：2014/12/29
作成者：八鍬寛之

（画面概要）

商品マスタの検索/一覧画面。

・検索条件：商品情報で絞り込みが可能。検索結果一覧の表示件数も変更可能。
・検索結果：商品情報の一覧表示。在庫数を一括で変更することができる。

・検索ボタン押下：設定された絞り込み項目をもとに検索処理を実行する。
・リセットボタン押下：設定された絞り込み項目を全て空にする。
・「編集を有効にする」ボタン押下：在庫数の変更を反映させる。
・「一括入荷へ」ボタン押下：検索結果の商品の中からそれぞれ最も古い入荷予定情報を引継ぎ、一括入荷画面に遷移する。
・「新規登録」ボタン押下：商品の新規登録画面に遷移する。
・「検索結果をダウンロード」ボタン押下：検索結果の商品データ全てをエクセルにダウンロードする。セット商品は含まず。
・「一括削除」ボタン押下：商品リスト左のチェックボックスにチェックが付いている商品を一括削除する。
・行をダブルクリックまたは品番リンクをクリック：対象データの商品詳細画面に遷移する。

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
	<html:form action="/initItemList">

	<input type="hidden" name="sysItemId" id="sysItemId" />
	<html:hidden property="alertType" styleId="alertType"></html:hidden>

	<nested:nest property="registryDto">
		<nested:hidden property="messageFlg" styleId="messageFlg"/>
		<nested:notEmpty property="message">
			<div id="messageArea">
				<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
			</div>
		</nested:notEmpty>
	</nested:nest>

	<h4 class="heading">在庫一覧</h4>

	<html:hidden property="alertType" styleId="alertType"></html:hidden>
	<nested:hidden property="overseasInfoAuth" styleId="overseasInfoAuth"></nested:hidden>

		<nested:notEmpty property="errorMessageDTO">
			<div id="errorArea">
				<nested:nest property="errorMessageDTO">
					<p class="errorMessage"><nested:write property="errorMessage"/></p>
				</nested:nest>
			</div>
		</nested:notEmpty>

		<fieldset class="searchOptionField">
			<legend id="searchOptionOpen">▲隠す</legend>
			<div id="searchOptionArea">
				<nested:nest property="searchItemDTO" >
					<table id="search_option">
						<tr>
							<!-- 品番区分SELECT -->
							<td>
								<nested:select property="itemCodeType" styleId="itemCodeType">
									<html:optionsCollection property="itemListCodeTypeMap" value="key" label="value" />
								</nested:select>
							</td>
							<!-- 品番TEXT -->
							<td>
								<nested:text property="itemCode" styleClass="text_w155" styleId="itemCodeText" maxlength="11" />&nbsp;
								<!-- 一致区分 -->
								<nested:select property="searchMethod" styleId="searchMethod">
									<html:optionsCollection property="searchMethodMap" value="key" label="value" />
								</nested:select>
								&nbsp;
							</td>
							<td>
							</td>
							<td>注文日</td>
							<td><nested:text property="itemOrderDateFrom" styleId="itemOrderDateFrom" styleClass="calender" maxlength="10" />&nbsp;～</td>
							<td>&nbsp;<nested:text property="itemOrderDateTo" styleId="itemOrderDateTo" styleClass="calender" maxlength="10" /></td>
							<td style="padding-left: 20px;"><label><nested:checkbox property="deadStockFlg" />不良在庫有</label></td>
								<nested:hidden property="deadStockFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px;"><label><nested:checkbox property="keepFlg" />キープ有</label></td>
								<nested:hidden property="keepFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px;">
								<label><nested:checkbox property="haibangFlg" styleId="haibangFlg"/>廃盤商品のみ</label>&nbsp;&nbsp;&nbsp;
								<label><nested:checkbox property="haibangContainFlg"  styleId="haibangContainFlg"/>廃盤商品含む</label>
							</td>
								<nested:hidden property="haibangFlg" value="off"></nested:hidden>
								<nested:hidden property="haibangContainFlg" value="off"></nested:hidden>
						</tr>
						<tr>
							<td>商品名</td>
							<td><nested:text property="itemNm" styleClass="text_w155"/>&nbsp;部分一致</td>
							<td></td>
							<td>入荷予定日</td>
							<td><nested:text property="arrivalScheduleDateFrom" styleId="arrivalScheduleDateFrom" styleClass="calender" maxlength="10" />&nbsp;～</td>
							<td>&nbsp;<nested:text property="arrivalScheduleDateTo" styleId="arrivalScheduleDateTo" styleClass="calender" maxlength="10" /></td>
							<td style="padding-left: 20px;"><label><nested:checkbox property="overArrivalScheduleFlg" />入荷予定日超過</label></td>
								<nested:hidden property="overArrivalScheduleFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px;"><label><nested:checkbox property="arrivalScheduleFlg" />入荷予定有</label></td>
								<nested:hidden property="arrivalScheduleFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px;"><label><nested:checkbox property="setItemFlg" />セット商品</label></td>
								<nested:hidden property="setItemFlg" value="off"></nested:hidden>
						</tr>
						<tr>
							<td>会社・工場名</td>
							<td><nested:text property="companyFactoryNm" styleClass="text_w155"/>&nbsp;部分一致</td>
							<td></td>
							<td>入荷日</td>
							<td><nested:text property="arrivalDateFrom" styleId="arrivalDateFrom" styleClass="calender" maxlength="10" />&nbsp;～</td>
							<td>&nbsp;<nested:text property="arrivalDateTo" styleId="arrivalDateTo" styleClass="calender" maxlength="10" /></td>
							<td style="padding-left: 20px;"><label><nested:checkbox property="manualFlg" />説明書有</label></td>
								<nested:hidden property="manualFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px;"><label><nested:checkbox property="planSheetFlg" />図面有</label></td>
								<nested:hidden property="planSheetFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px;"><label><nested:checkbox property="otherDocumentFlg" />その他有</label></td>
								<nested:hidden property="otherDocumentFlg" value="off"></nested:hidden>
						</tr>
						<tr>
							<td>仕入先ID</td>
							<td><nested:text property="sysSupplierId" styleClass="text_w155 numText rigth" maxlength="30" /></td>
							<td colspan="1"></td>
							<td>仕様メモ</td>
							<td colspan="2"><nested:text property="specMemo" styleClass="text_w285"/></td>
							<td style="padding-left: 20px; background-color: #FFE0E0"><label><nested:checkbox property="orderAlertFlg" styleClass="orderAlertFlg" styleId="orderAlertFlg"/>在庫アラート</label></td>
								<nested:hidden property="orderAlertFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px; background-color: #8ef70e"><label><nested:checkbox property="deliveryAlertFlg" styleClass="deliveryAlertFlg" styleId="deliveryAlertFlg"/>注文アラート</label></td>
								<nested:hidden property="deliveryAlertFlg" value="off"></nested:hidden>
							<td style="padding-left: 20px;"><label><nested:checkbox property="orderPoolFlg" styleClass="orderPoolFlg" styleId="orderPoolFlg"/>注文プール</label></td>
								<nested:hidden property="orderPoolFlg" value="off"></nested:hidden>
						</tr>
						<tr>
							<td>ロケーションNo</td>
							<td colspan="2"><nested:text property="locationNo" styleClass="text_w155" maxlength="30" />&nbsp;前方一致</td>

							<td colspan="2">並び順&nbsp;
								<nested:select property="sortFirst" styleId="sortFirst">
									<html:optionsCollection property="itemListSortMap" value="key" label="value" />
								</nested:select>
								<nested:select property="sortFirstSub" styleId="sortFirstSub">
									<html:optionsCollection property="itemListSortOrder" value="key" label="value" />
								</nested:select>
								&nbsp;
							</td>
							<td style="padding-left: 20px;">表示件数
								<nested:select property="listPageMax" styleId="listPageMax">
									<html:optionsCollection property="listPageMaxMap" value="key" label="value" />
								</nested:select>&nbsp;件
							</td>
							<td style="padding-left: 20px;">
								<label><nested:radio property="displayContents" value="1" styleClass="displayContents" styleId="stockInfo"/>在庫情報</label>
							</td>
							<td style="padding-left: 20px;">
								<label><nested:radio property="displayContents" value="2" styleClass="displayContents" styleId="salesInfo"/>販売情報</label>
							</td>
<%-- 							<nested:equal value="2" name="itemForm" property="displayContentsVal"> --%>
								<td style="padding-left: 20px;">
									<label><nested:checkbox property="orderNum0Flg" styleClass="orderNum0Flg FlgSwich" styleId="orderNum0Flg"/>注文数0</label>
									<nested:hidden property="orderNum0Flg" value="off"/>
								</td>
<%-- 							</nested:equal> --%>
						</tr>
						<tr>
							<td>
								<nested:select property="sysWarehouseId" styleId="sysWarehouseId">
									<html:option value="0">総在庫</html:option>
									<html:optionsCollection property="warehouseList" label="warehouseNm" value="sysWarehouseId" />
								</nested:select>
							</td>
							<td colspan="2">数量<nested:text property="stockNum" styleId="stockNum" styleClass="stockNum numText rigth"/>&nbsp;
								<nested:select property="numberOrder" styleId="numberOrder">
									<html:optionsCollection property="numberOrderMap" value="key" label="value" />
								</nested:select>
							</td>
							<td colspan="3"></td>
							<td class="td_center" style="padding-left: 20px;"><a class="button_main search" href="javascript:void(0);">検索</a></td>
							<td><a class="button_white clear" href="javascript:void(0);">リセット</a></td>
<%-- 							<nested:equal value="2" name="itemForm" property="displayContentsVal"> --%>
								<td class="td_center" style="padding-left: 20px;"><a class="button_main sum" href="javascript:void(0);">注文数計算</a></td>
<%-- 							</nested:equal> --%>
						</tr>
					</table>
				</nested:nest>
			</div>
		</fieldset>

	<nested:notEmpty property="itemList">


		<div class="paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysItemIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="itemNowPage" ></span>&nbsp;/&nbsp;<span class="itemPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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

		<div class="totalOrderNumArea">
			<nested:equal value="2" property="displayContentsVal">
				<h3>注文数：計&nbsp;<span class="totalOrderNum"><nested:write property="totalOrderNum"/></span>&nbsp;個</h3>
			</nested:equal>
		</div>

		<div id="list_area">

		<nested:hidden property="sysItemIdListSize" styleId="sysItemIdListSize" />
		<nested:hidden property="pageIdx" styleId="pageIdx" />
		<nested:hidden property="itemListPageMax" styleId="itemListPageMax" />
			<nested:equal value="1" property="displayContentsVal">
				<table class="list_table">
					<tr>
						<td id="allCheck" class="td_center allDeleteCheckSize"><input type="checkbox" id="allDeleteCheck"class="allDeleteCheck checkBoxTransForm"></td>
						<th id="code">品番</th>
						<th id="item">商品名</th>
						<th id="stock">総在庫数</th>
						<th id="temporary">仮在庫数</th>
						<th id="bo_date">組立可数</th>
						<th id="orderSize">注文</th>
						<th id="schedule_date">入荷予定日</th>
						<th id="arrival_num_size">未入荷数</th>
						<th id="carModel">車種</th>
						<th id="model">型式</th>
						<th id="memo">仕様</th>
					</tr>

					<nested:notEmpty property="itemList">
						<nested:iterate property="itemList" indexId="idx">

<%-- 	 					<nested:greaterEqual property="orderAlertNum" value="<nested:write property="totalStockNum" />"> --%>
<%-- 	 					<nested:greaterEqual property="orderAlertNum" value="totalStockNum"> --%>
<%-- 	 						<bean:define id="rowColor" value="#FFE4E4" /> --%>
<%-- 	 					</nested:greaterEqual> --%>

						<nested:hidden property="orderAlertNum" styleClass="orderAlertNum"></nested:hidden>
						<nested:hidden property="deliveryAlertTargetFlg" styleClass="deliveryAlertTargetFlg"></nested:hidden>

						<tr class="change_color rowNum stockRow">

						<nested:hidden property="stockNum" styleClass="stockNum" />
						<nested:hidden property="totalStockNum" styleClass="totalStockNumBase" />
						<nested:hidden property="updateStockNum" styleClass="updateStockNum" />
						<nested:hidden property="itemCode" styleClass="itemCode" />
						<nested:hidden property="itemNm" styleClass="itemNm" />
						<nested:hidden property="sysWarehouseId"/>

							<td class="count deleteCheckFlg">
								<nested:checkbox property="deleteCheckFlg" styleClass="deleteCheckFlg checkBoxTransForm" />
							</td>
							<td class="itemCode"><a href="javascript:void(0);" class="itemListRow">
									<nested:hidden property="sysItemId" styleClass="sysItemId" />
									<nested:hidden property="setItemFlg" styleClass="setItemFlg"></nested:hidden>
									<nested:write property="itemCode" />
								</a>
							</td>
							<td class="itemNm"><nested:write property="itemNm" /></td>

							<td><nested:write property="totalStockNumInput" /></td>
							<td class="temporaryStockNum"><nested:write property="temporaryStockNum" /></td>
							<td class="assemblyNum">
								<nested:equal value="1" property="setItemFlg">
									<nested:write property="assemblyNum" />
								</nested:equal>
							</td>
							<td class="sumNotInStock">
								<nested:notEqual value="0" property="sumNotInStock">
									○(<nested:write property="sumNotInStock"/>)
								</nested:notEqual>
							</td>
							<td class="arrivalScheduleDate"><nested:write property="arrivalScheduleDate"/></td>
							<td><nested:write property="arrivalNum"/></td>
							<td><nested:write property="carModel"/></td>
							<td><nested:write property="model"/></td>
							<td class="specMemo"><nested:write property="specMemo" /></td>
						</tr>
						</nested:iterate>
					</nested:notEmpty>
				</table>
			</nested:equal>

			<nested:equal value="2" property="displayContentsVal">
				<table class="list_table_sale">
					<tr>
						<td rowspan="2" id="allCheck" class="td_center allOrderCheckSize"><input type="checkbox" id="allOrderCheck" class="allOrderCheck checkBoxTransForm"></td>
						<th rowspan="2" id="code">品番</th>
						<th rowspan="2" id="item">商品名</th>
						<th rowspan="2" id="stock">在庫</th>
						<th rowspan="2" id="orderSize">注文</th>
						<th rowspan="2" id="orderNum">注文数</th>
						<th rowspan="2" id="orderPoolBtn">
							<a class="button_pool_white allOrder" href="javascript:void(0);">一括注文プール設定</a>
						<th colspan="13" id="">販売数(平均以外は「ヶ月前」を表す)</th>
					</tr>
					<tr>
						<th id="">平均</th>
						<th id="">1</th>
						<th id="">2</th>
						<th id="">3</th>
						<th id="">4</th>
						<th id="">5</th>
						<th id="">6</th>
						<th id="">7</th>
						<th id="">8</th>
						<th id="">9</th>
						<th id="">10</th>
						<th id="">11</th>
						<th id="">12</th>
					</tr>

					<nested:notEmpty property="itemList">
						<nested:iterate property="itemList" indexId="idx">

						<nested:hidden property="orderAlertNum" styleClass="orderAlertNum"></nested:hidden>
						<nested:hidden property="deliveryAlertTargetFlg" styleClass="deliveryAlertTargetFlg"></nested:hidden>

						<tr class="change_color rowNum saleRow">

							<nested:hidden property="stockNum" styleClass="stockNum" />
							<nested:hidden property="totalStockNum" styleClass="totalStockNumBase" />
							<nested:hidden property="updateStockNum" styleClass="updateStockNum" />
							<nested:hidden property="itemCode" styleClass="itemCode" />
							<nested:hidden property="itemNm" styleClass="itemNm" />
							<nested:hidden property="sysWarehouseId"/>

							<td class="count td_center itemRow">
								<nested:checkbox property="orderCheckFlg" styleClass="orderCheckFlg checkBoxTransForm"/>
								<nested:hidden property="orderCheckFlg" value="off"/>
							</td>
							<td class="itemCode itemRow"><a href="javascript:void(0);" class="itemListRow">
									<nested:hidden property="sysItemId" styleClass="sysItemId" />
									<nested:hidden property="setItemFlg" styleClass="setItemFlg"></nested:hidden>
									<nested:write property="itemCode" />
								</a>
							</td>
							<td class="itemNm itemRow"><nested:write property="itemNm" /></td>

							<td class="itemRow">
								<nested:write property="totalStockNumInput"/>
								(<nested:write property="temporaryStockNum" />)
							</td>
							<td class="sumNotInStock itemRow">
								<nested:notEqual value="0" property="sumNotInStock">
									○(<nested:write property="sumNotInStock"/>)
								</nested:notEqual>
							</td>
							<td>
								<nested:text property="orderNum" styleClass="orderNum num" ></nested:text>
								<nested:hidden property="minimumOrderQuantity" styleClass="minimumOrderQuantity"/>
							</td>
							<td><a class="button_white order" href="javascript:void(0);">注文</a>
								<nested:hidden property="orderPoolFlg" styleClass="orderPoolFlg"></nested:hidden>
								<nested:hidden property="beforeOrderPoolFlg" styleClass="beforeOrderPoolFlg"></nested:hidden>
							</td>
							<td class="itemRow"><nested:write property="annualAverageSalesNum"/></td>
							<td class="itemRow"><nested:write property="oneMonthAgo"/></td>
							<td class="itemRow"><nested:write property="twoMonthAgo"/></td>
							<td class="itemRow"><nested:write property="threeMonthAgo"/></td>
							<td class="itemRow"><nested:write property="fourMonthAgo"/></td>
							<td class="itemRow"><nested:write property="fiveMonthAgo"/></td>
							<td class="itemRow"><nested:write property="sixMonthAgo"/></td>
							<td class="itemRow"><nested:write property="sevenMonthAgo"/></td>
							<td class="itemRow"><nested:write property="eightMonthAgo"/></td>
							<td class="itemRow"><nested:write property="nineMonthAgo"/></td>
							<td class="itemRow"><nested:write property="tenMonthAgo"/></td>
							<td class="itemRow"><nested:write property="elevenMonthAgo"/></td>
							<td class="itemRow"><nested:write property="twelveMonthAgo"/></td>
						</tr>
						</nested:iterate>
					</nested:notEmpty>
				</table>
			</nested:equal>
		</div>

		<!-- ******************************************************************************** -->
		<div class="under_paging_area">
			<div class="paging_total_top">
				<h3>全&nbsp;<nested:write property="sysItemIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="itemNowPage" ></span>&nbsp;/&nbsp;<span class="itemPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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
		<!-- ******************************************************************************** -->
	</nested:notEmpty>

	<footer class="footer buttonArea">
		<ul style="position: relative;">
			<nested:equal value="1" property="displayContentsVal">
				<li class="footer_button">
					<a class="button_main regist" href="Javascript:void(0);" onclick="goTransaction('initRegistryItem.do')">新規登録</a>
				</li>
				<li class="footer_button">
					<nested:select property="searchItemDTO.downloadType" styleId="downloadType" styleClass="button_white">
						<html:optionsCollection property="downloadTypeCodeMap" value="key" label="value" />
					</nested:select>
					<a class="button_white itemListDownLoad" href="Javascript:void(0);">検索結果をダウンロード</a>
				</li>
				<li class="footer_button">
					<a class="button_white delete" href="Javascript:void(0);">商品の削除</a>
				</li>
				<li class="footer_button">
					<a class="button_white"id="assembly" href="Javascript:void(0);">組立可数更新</a>
				</li>
			</nested:equal>

			<nested:equal value="2" property="displayContentsVal">
				<li class="footer_button">
					<a class="button_main addOrderPool" href="Javascript:void(0);" >注文プールに追加</a>
				</li>
				<li class="footer_button">
					<a class="button_white orderPoolFlgDelete" href="Javascript:void(0);">注文プールから削除</a>
				</li>
				<li class="footer_button">
					<a class="button_white saleReset" href="Javascript:void(0);">リセット</a>
				</li>
			</nested:equal>
		</ul>
	</footer>

	</html:form>
</html:html>
	<script type="text/javascript">



	$(document).ready(function(){
		$(".overlay").css("display", "none");
    	window.onpageshow = function(event) {
    		if (sessionStorage.getItem('toItemDetail') == 'true') {
              	var url = window.location.href;
            	if (url.indexOf("itemList") >= 0) {
                	location.reload();
            		$(".overlay").css("display", "block");
        			sessionStorage.setItem('toItemDetail', 'false');
            	}
    		}
        };
    	
    	document.addEventListener("visibilitychange", function() {
    	    if (document.hidden){
    	    } else {
              	var url = window.location.href;
            	if (url.indexOf("itemList") >= 0) {
                	location.reload();
            		$(".overlay").css("display", "block");
            	}
    	    }
    	});

		$("body").keydown( function(event) {
			if(event.which === 13){
				$(".search").click();
			}
		});
		$('.specMemo').each(function(i, e){
			let txt = $(e).text();
			if(txt.length > 50){
				$(e).text(txt.substr(0, 50) + '...');
			}
		});
	 });

	function goInitLumpArrival(){

		if($(".sysItemId").size() == 0) {
			alert("対象の在庫データがありません。");
			return;
		}

		goTransaction("initLumpArrival.do");
	}



	$(function() {

		//検索結果表示時注文プールフラグを見て、注文数と注文ボタンを非活性にする
		$(".saleRow").each(function () {
			var orderPoolFlg = $(this).find(".orderPoolFlg").val();
			if (orderPoolFlg == "1") {
				$(this).find('.orderNum').prop('disabled',true);
				$(this).find('.orderNum').removeClass("num");
				$(this).find('.orderNum').width(60);
				$(this).find('.button_white').addClass("button_main");
				$(this).find('.button_white').addClass("orderCandidate");
				$(this).find('.button_white').removeClass("order");
				$(this).find('.button_white').removeClass("button_white");
			}
		});

		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1
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

		//リセットボタン押下時処理
		 $(".clear").click(function(){
	        $("#search_option input, #search_option select").each(function(){
	            if (this.type == "checkbox" || this.type == "radio") {
	                this.checked = false;
	            } else {
	                $(this).val("");
	            }
	            //セレクトボックスは空欄ではなく初期化
	            $("#itemCodeType").val("1");
	            $("#searchMethod").val("1");
	            $("#sysWarehouseId").val("0");
	            $("#numberOrder").val("1");
	            $("#sortFirst").val("1");
	            $("#sortFirstSub").val("1");
	            $("#listPageMax").val("1");
	            $("#stockInfo").prop('checked', true);

	        });
	    });

		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

// 		if ($("tr.rowNum").size() != 0) {

// 			//検索結果があれば検索欄を隠す処理
// 			if($('#searchOptionOpen').text() == "▼絞り込み") {
// 				$('#searchOptionOpen').text("▲隠す");
// 			} else {
// 				$('#searchOptionOpen').text("▼絞り込み");
// 			}

// 			$('#searchOptionArea').slideToggle("fast");
// 		}

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		$('#searchOptionOpen').click(function () {

			if($('#searchOptionOpen').text() == "▼絞り込み") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼絞り込み");
			}

			$('#searchOptionArea').slideToggle("fast");
		});

		//検索ボタン
		$(".search").click( function () {

			if ($("#itemOrderDateFrom").val() && $("#itemOrderDateTo").val()){
				fromAry = $("#itemOrderDateFrom").val().split("/");
				toAry = $("#itemOrderDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("注文日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#itemCodeFrom").val() && $("#itemCodeTo").val()) {
				if ($("#itemCodeFrom").val() > $("#itemCodeTo").val()) {
					alert("品番 の出力開始番号が、出力終了番号より大きい値になっています。");
					return false;
				}
			}

			if ($("#arrivalScheduleDateFrom").val() && $("#arrivalScheduleDateTo").val()){
				fromAry = $("#arrivalScheduleDateFrom").val().split("/");
				toAry = $("#arrivalScheduleDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("入荷予定日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#arrivalDateFrom").val() && $("#arrivalDateTo").val()){
				fromAry = $("#arrivalDateFrom").val().split("/");
				toAry = $("#arrivalDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("入荷日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}
			
			$(".overlay").css("display", "block");
			$(".message").text("検索中");

			goTransaction("itemList.do");
		});
		
		$("#haibangContainFlg").change(function() {
		    if(this.checked) {
		    	$('#haibangFlg').prop('checked', false)
		    }
		});
		$("#haibangFlg").change(function() {
		    if(this.checked) {
		    	$('#haibangContainFlg').prop('checked', false)
		    }
		});
		

		//************************注文数計算ボタン*********************
		//画面読み込み時、チェックボックスが販売情報のときは計算し、それ以外は隠す
		if ($("#salesInfo").prop("checked")) {
			stockSum();
		} else {
			$("div.totalOrderNumArea").hide();
		}

		$(".sum").click( function () {
			//チェックボックスが販売情報のときは計算し、それ以外は隠す
			if ($("#salesInfo").prop("checked")) {

				stockSum();
			} else {
				$("div.totalOrderNumArea").hide();
			}

		});

		function stockSum() {

			var sumOrderNum = 0;
			//検索結果分ループ
			for (var i = 0; i < $("tr.saleRow").size(); i++) {

				var orderNum = $("tr.saleRow").eq(i).find(".orderNum").val();
				sumOrderNum += Number(orderNum);
			}
			//表示
			$("div.totalOrderNumArea").show();
			$(".totalOrderNum").html(sumOrderNum);
		}
		//*************************************************************

		//商品詳細
		$(".itemListRow").click( function () {

			$("#sysItemId").val($(this).find(".sysItemId").val());

			sessionStorage.setItem('toItemDetail', 'true');
 			if($(this).find(".setItemFlg").val() == '1') {
 				goTransaction("detailSetItem.do");
 			} else {
 				goTransaction("detailItem.do");
 			}
		});

		//注文プールフラグボタン押下時処理
		$('.order').click(function (){
			if ($(this).hasClass("button_white")) {
				$(this).removeClass("button_white");
				$(this).addClass("button_main");
				$(this).addClass("orderCandidate");
				$(this).parents('tr').find(".orderPoolFlg").val("1");
			} else {
				$(this).removeClass("button_main");
				$(this).removeClass("orderCandidate");
				$(this).addClass("button_white");
				$(this).parents('tr').find(".orderPoolFlg").val("0");
			}
		});

		//一括注文プールフラグ設定押下時処理
		$(".allOrder").click(function () {
			if ($(this).hasClass("button_pool_white")) {
				$(this).removeClass("button_pool_white");
				$(this).addClass("button_pool_main");
				$(this).addClass("orderCandidate");
				for (var i = 0; i < $(".saleRow").size() ; i++) {
					$('.order').eq(i).removeClass("button_white");
					$('.order').eq(i).removeClass("button_white");
					$('.order').eq(i).addClass("button_main");
					$('.order').eq(i).addClass("orderCandidate");
					$('.order').eq(i).parents('tr').find(".orderPoolFlg").val("1");
				}

			} else {
				$(this).removeClass("button_pool_main");
				$(this).removeClass("orderCandidate");
				$(this).addClass("button_pool_white");
				for (var i = 0; i < $(".saleRow").size() ; i++) {
					$('.order').eq(i).removeClass("button_main");
					$('.order').eq(i).removeClass("orderCandidate");
					$('.order').eq(i).addClass("button_white");
					$('.order').eq(i).parents('tr').find(".orderPoolFlg").val("0");
				}
			}
		});

		//注文プールに追加ボタン押下時処理
		$('.addOrderPool').click(function () {
			if ($(".saleRow").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			if ((!confirm("商品を注文プールフラグに追加します。よろしいですか？"))) {
				return;
			}

			$(".overlay").css("display", "block");
			$(".message").text("更新中");
			goTransaction("addOrderPool.do");
		});

		//注文プールから削除ボタン押下時処理
		$(".orderPoolFlgDelete").click(function () {
			if ($(".saleRow").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			if ($(".orderCheckFlg:checked").length == 0) {
				alert("対象を選択してください。");
				return false;
			}

			if ((!confirm("選択された商品を注文プールフラグから削除します。よろしいですか？"))) {
				return;
			}
			$(".overlay").css("display", "block");
			$(".message").text("削除中");
			goTransaction("deleteOrderPool.do");

		});

		//発注アラートの行色変え
		//20140320 八鍬
		for(i = 0; i < $(".count").length; i++) {
			var totalStockNumBase = new Number($(".totalStockNumBase").eq(i).val());
			var orderAlertNum = new Number($(".orderAlertNum").eq(i).val());
			if(orderAlertNum >= totalStockNumBase) {
				$(".rowNum").eq(i).css("background-color", "#FFE0E0");
			}

			var deliveryAlertTargetFlg = $(".deliveryAlertTargetFlg").eq(i).val();
			if (deliveryAlertTargetFlg == "1") {
				$(".rowNum").eq(i).css("background-color", "#8ef70e");
			}
		}



		//ページ送り
		//20140321 八鍬　作成
		//20140401 伊東　編集
		//20140404 安藤　編集
		if ($("#sysItemIdListSize").val() != 0) {
			var itemPageNum = Math.ceil($("#sysItemIdListSize").val() / $("#itemListPageMax").val());

			//20140401 伊東　編集 start
			$(".itemPageNum").text(itemPageNum);
			$(".itemNowPage").text(Number($("#pageIdx").val()) + 1);
			//20140401 伊東　編集 end
			var pageIdx = new Number($("#pageIdx").val());

				if (0 == pageIdx) {
					$(".pager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
					$(".underPager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
				}

			//20140404 安藤　編集 start
			var startIdx;
			// maxDispは奇数で入力
			var maxDisp = 7;
			// itemPageNumがmaxDisp未満の場合maxDispの値をitemPageNumに変更
			if (itemPageNum < maxDisp) {

				maxDisp = itemPageNum;

			}

			var center = Math.ceil(Number(maxDisp) / 2);

			// 現在のページが中央より左に表示される場合
			if (center >= pageIdx + 1) {
				startIdx = 1;
				$(".lastIdx").html(itemPageNum);
				$(".liFirstPage").hide();

			// 現在のページが中央より右に表示される場合
			} else if (itemPageNum - (center - 1) <= pageIdx + 1) {

				startIdx = itemPageNum - (maxDisp - 1);
				$(".liLastPage").hide();
				$(".3dotLineEnd").hide();

			// 現在のページが中央に表示される場合
			} else {

				startIdx = $("#pageIdx").val() - (center - 2);
				$(".lastIdx").html(itemPageNum);

			}

			//表示の初期値を設定
			$(".pageNum").html(startIdx);
			//endIdx設定
			var endIdx = startIdx + (maxDisp - 1);

			if (startIdx <= 2) {

 				$(".3dotLineTop").hide();

 			}

			if ((itemPageNum <= 8) || ((itemPageNum - center) <= (pageIdx + 1))) {

				$(".3dotLineEnd").hide();

			}

			if (itemPageNum <= maxDisp) {

				$(".liLastPage").hide();
				$(".liFirstPage").hide();

			}


			var i;
			for (i = startIdx; i < endIdx && i < itemPageNum; i++) {
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
		//20140404 安藤　編集 end

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		$(".pageNum").click (function () {

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val($(this).text() - 1);

			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
		});

		//20140403 安藤　ここから
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

			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
		});

		//最終ページへ
		$("#lastPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);

			goTransaction("itemListPageNo.do");
		});
		//20140403 安藤　ここまで

		//一括商品削除
		$(".delete").click (function () {

			if($(".sysItemId").size() == 0) {
				alert("対象の在庫データがありません。");
				return;
			}

		if ($(".deleteCheckFlg:checked").length == 0) {
			alert("対象を選択してください。");
			return false;
		}


			if (confirm("選択された商品を削除します。よろしいですか？")) {
				$(".overlay").css("display", "block");
				$(".message").text("削除中");
				removeCommaGoTransaction("lumpDeleteItem.do");
			}
			return;
		});

		//組立可数更新
		$("#assembly").click (function () {

			alert("この処理は、10分程度掛かることがあります。");
			if (confirm("組立可数を更新します。よろしいですか？")) {
				$(".overlay").css("display", "block");
				$(".message").text("組立可数更新中");
				goTransaction("updAssemblyNum.do");
			}
			return;
		});

//ページ送り（下側）
//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
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
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".itemPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val(maxPage - 1);
			$(".overlay").css("display", "block");
			$(".message").text("検索中");
			goTransaction("itemListPageNo.do");
		});
		//20140403 安藤　ここまで

		// ダブルクリックで商品詳細遷移：在庫情報
		$(".stockRow").dblclick(function () {
			var tr = $(this);
			$("#sysItemId").val(tr.find(".sysItemId").val());
			sessionStorage.setItem('toItemDetail', 'true');
				if(tr.find(".setItemFlg").val() == '1') {
					goTransaction("detailSetItem.do");
 				} else if(tr.find(".setItemFlg").val() == '0') {
 					goTransaction("detailItem.do");
 				}
		});
		// ダブルクリックで商品詳細遷移：販売情報
		$(".itemRow").dblclick(function () {
			var tr = $(this);
			$("#sysItemId").val(tr.parents('.saleRow').find(".sysItemId").val());
			sessionStorage.setItem('toItemDetail', 'true');
			if(tr.parents('.saleRow').find(".setItemFlg").val() == '1') {
				goTransaction("detailSetItem.do");
				} else if(tr.parents('.saleRow').find(".setItemFlg").val() == '0') {
					goTransaction("detailItem.do");
				}
		});


//******************************************************************************************

		//検索結果ダウンロードボタン押下時の処理
		$(".itemListDownLoad").click (function () {

			//CSV形式でダウンロード
			if ($("#downloadType").val() == "3") {

				if($(".sysItemId").size() == 0) {
					alert("対象の在庫データがありません。");
					return;
				}

				goTransaction("itemListCsvDownLoad.do");
			//Excelでダウンロード
			} else {
				if($(".sysItemId").size() == 0) {
					alert("対象の在庫データがありません。");
					return;
				}
				goTransaction("itemListDownLoad.do");
			}
		});

		//販売情報リセットボタン押下時
		$(".saleReset").click(function() {
			$(".saleRow").each(function () {
				if (!$(this).find(".orderNum").prop('disabled')) {
					$(this).find(".orderNum").val($(this).find(".minimumOrderQuantity").val());
				}
				$(this).find(".orderCheckFlg").prop("checked", false);
			})
		});

		//チェックボックス全件選択・解除：在庫情報
		$("#allDeleteCheck").click(function() {
			if(this.checked) {
				$(".deleteCheckFlg").prop("checked", true);
			} else {
				$(".deleteCheckFlg").prop("checked", false);
			}
		});

		//チェックボックス全件選択・解除：販売情報
		$("#allOrderCheck").click(function() {
			if(this.checked) {
				$(".orderCheckFlg").prop("checked", true);
			} else {
				$(".orderCheckFlg").prop("checked", false);
			}
		});


		//品番関連の入力制御をかける
		$("#itemCodeText").blur(function () {
			var itemCodeType = $("#itemCodeType").val();
			//Mst_itemの品番系の場合数値のみ
			if (itemCodeType == "1" || itemCodeType == "4" || itemCodeType == "5" ) {
				if (!numberCheckCormal(this)){
					$(this).val('');
					return;
				}
			//工場品番の場合半角数字ハイフンドット半角スペースのみ　detailItem.jsp foreignOrderList.jspにも同様の記載あり
			} else if (itemCodeType == "3") {
				var str = this.value;
				if (!str.match("^[0-9A-Za-z\-\.\ ]*$")) {
					alert("半角英数字ハイフン「-」ドット「.」半角スペースのみで入力してください。");
					this.value = '';
					this.focus();
					this.select();
					$(this).val("");
					return;
				}
			}
		});

		//品番種別が変更された場合、品番のテキストに入力されている文字をチェックする
		$("#itemCodeType").change(function () {
			var itemCodeType = $(this).val();
			var itemCodeText = $('#itemCodeText')[0];
			//Mst_itemの品番系の場合数値のみ
			if (itemCodeType == "1" || itemCodeType == "4" || itemCodeType == "5" ) {
				if (!numberCheckCormal(itemCodeText)){
					return;
				}
			//工場品番の場合半角数字ハイフンドットのみ
			} else if (itemCodeType == "3") {
				var str = itemCodeText.value;
				if (!str.match("^[0-9A-Za-z\-\.]*$")) {
					alert("半角英数字ハイフン「-」ドット「.」のみで入力してください。");
					$('#itemCodeText').val("");
					return;
				}
			}
		})

		//海外情報閲覧権限制御
		if ($("#overseasInfoAuth").val() == "1") {
			$("#salesInfo").prop('disabled', false);
		} else {
			$("#salesInfo").prop('disabled', true);
		}

// 		$(".FlgSwich").change(function () {

// 			if (this.checked) {
// 				$("this").prop("checked", true);
// 			} else {
// 				$(".this").prop("checked", false);
// 			}
// 		});
	});

	</script>