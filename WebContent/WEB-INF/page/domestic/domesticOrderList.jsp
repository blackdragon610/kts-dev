<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<title>国内仕入注文管理一覧</title>

	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />

	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css"/>
	<link rel="stylesheet" href="./css/domesticOrderList.css" type="text/css" />
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
【業販売上一覧画面】
ファイル名：domesticOrderList.jsp
作成日：2016/12/01
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
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>

<div id='cssmenu'>

</div>

	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="/initDomesticOrderList" styleId="domesticForm">
	<html:hidden property="messageFlg" styleId="messageFlgForm"/>
		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: leght;"><nested:write property="message"/></p>
				</div>
			</nested:notEmpty>
		</nested:nest>
	<html:hidden property="managementCode" styleId="managementCode"/>
	<html:hidden property="purchaseOrderNo" styleId="purchaseOrderNo"/>
	<html:hidden property="sysDomesticSlipId" styleId="sysDomesticSlipId"/>
	<html:hidden property="strSysDomesticSlipId" styleId="strSysDomesticSlipId"/>
	<input type="hidden" name="alertType" value="0" id="alertType">

	<h4 class="heading">国内注文管理一覧</h4>

	<ul class="hmenu mb10">
		<nested:iterate property="domesticstatus" >
			<li class="corp">
				<a href="javascript:void(0);" class="searchStatus">
					<nested:write name="domesticstatus" property="value" />
					<nested:hidden name="domesticstatus" styleClass="statusId" property="key"/>
				</a>
			</li>
		</nested:iterate>
	</ul>

	<fieldset class="searchOptionField01">
		<legend id="searchOptionOpen">▲隠す</legend>

		<div id="searchOptionArea">

			<nested:nest property="domesticOrderListSearchDTO">
				<table id="orderTable">
					<tr>
						<td>注文書作成日</td>
						<td><nested:text property="orderCreatDateFrom" maxlength="20" styleClass="orderCreatDateFrom  right calender"></nested:text>
						 ～<nested:text property="orderCreatDateTo"  maxlength="20" styleClass="orderCreatDateTo  right calender"></nested:text></td>
					</tr>
					<tr>
						<td>入荷予定日</td>
						<td><nested:text property="arrivalScheduleDateFrom" maxlength="20" styleClass="arrivalScheduleDateFrom  right calender"></nested:text>
						 ～<nested:text property="arrivalScheduleDateTo"  maxlength="20" styleClass="arrivalScheduleDateTo  right calender"></nested:text></td>
					</tr>
					<tr>
						<td>メーカー名</td>
						<td>
							<nested:select property="sysMakerId" styleId="makerNmSearch" styleClass="makerNmKana select">
								<html:option value="">　</html:option>
								<html:optionsCollection property="makerListDTO" label="makerNm" value="sysMakerId" />
							</nested:select>
							<input type="hidden" id="nmSearchFlg"/>
						</td>
					<tr>
					<tr>
						<td>メーカー名ｶﾅ</td>
						<td>
							<nested:select property="sysMakerId" styleId="makerNmKanaSearch" styleClass="makerNmKana select">
								<html:option value="">　</html:option>
								<html:optionsCollection property="makerListDTO" label="makerNmKana" value="sysMakerId" />
							</nested:select>
							<input type="hidden" id="nmKanaSearchFlg"/>
							<nested:hidden property="sysMakerId" styleId="sysMakerId"></nested:hidden>
						</td>
					</tr>
					<tr>
						<td>商品名</td>
						<td><nested:text property="itemNm" maxlength="30" styleClass="textSize_240" ></nested:text>(部分一致)</td>
					</tr>
					<tr>
						<td>受注番号</td>
						<td><nested:text property="ordeNo" maxlength="30"  styleClass="textSize_240"></nested:text>(前方一致)</td>
					</tr>
					<tr>
						<td>メーカー品番</td>
						<td><nested:text property="makerCode" styleClass="textSize_240 checkAlnumHyp"></nested:text>(前方一致)</td>
					</tr>
					<tr>
						<td>数量</td>
						<TD>
							<nested:text property="orderNum" maxlength="6" styleClass="quantity right numText"></nested:text>
							<nested:select property="numberOrderType" styleClass="">
								<html:optionsCollection property="numberOrderMap" value="key" label="value" />
							</nested:select>
						</TD>
					</tr>
					<tr>
						<td>通番</td>
						<td><nested:text property="serealNum" maxlength="30" styleClass="left numText serealNumText" ></nested:text>(後方一致)</td>
					</tr>
				</table>

				<div id="centerArea">
					<table id="deliveryTable">
						<tr>
							<td>ステータス</td>
							<td>
								<nested:select property="status" styleId="searchStatus">
									<html:option value=""></html:option>
									<html:optionsCollection property="domesticstatus" value="key" label="value" />
								</nested:select>
							</td>
						</tr>
						<tr>
							<td>入荷担当</td>
						<td><nested:text property="stockCharge" maxlength="30" styleClass="chargesSize" ></nested:text>(部分一致)</td>
						</tr>
						<tr>
							<td>注文担当</td>
							<td><nested:text property="orderCharge" maxlength="30" styleClass="chargesSize" ></nested:text>(部分一致)</td>
						</tr>
						<tr>
							<td>仕入原価</td>
							<td><nested:text property="purchasingCostFrom" maxlength="20" styleClass="purchasingCostFrom priceTextWrongMes right demandInf"></nested:text>
						 ～<nested:text property="purchasingCostTo"  maxlength="20" styleClass="purchasingCostTo priceTextWrongMes right demandInf"></nested:text></td>
						</tr>
						<tr>
							<td>対応者</td>
							<td><nested:text property="personCharge" maxlength="30" styleClass="chargesSize" ></nested:text>(部分一致)</td>
						</tr>
						<tr>
							<td>対応</td>
							<td>
								<nested:select property="correspondence" styleId="correspondence" styleClass="numberOrderType">
									<html:optionsCollection property="domesticselect" value="key" label="value" />
								</nested:select>
							</td>
						</tr>
						<tr>
							<td>対応日</td>
							<td><nested:text property="chargeDateFrom" maxlength="20" styleClass="chargeDateFrom right calender"></nested:text>
							 ～<nested:text property="chargeDateTo"  maxlength="20" styleClass="chargeDateTo right calender"></nested:text></td>
						</tr>
						<tr>
							<td>納入先</td>
							<td>
								<nested:select property="deliveryType" styleId="deliveryType" styleClass="deliveryType">
									<html:optionsCollection property="deliveryTypeMap" value="key" label="value" />
								</nested:select>
							</td>
						</tr>
						<tr>
							<td>印刷</td>
							<td >
								<label style="padding: inherit; background-color: #d2f1f7">
									<nested:checkbox property="notPrintData"></nested:checkbox>未印刷
									<nested:hidden property="notPrintData" value="off" styleId="notPrintDataHidden"></nested:hidden>
								</label>

								<label>
									<nested:checkbox property="printedData"></nested:checkbox>印刷済
									<nested:hidden property="printedData" value="off" styleId="printedDataHidden"></nested:hidden>
								</label>
							</td>
						</tr>
					</table>
					</div>
				</nested:nest>


			<div id="subArea">
				<table id="itemTable">
					<tr>
						<nested:nest property="domesticOrderListSearchDTO">
						<tr>
							<td class="td_sort" style="padding-left: 4px;">並び順</td>
							<td>
								<nested:select property="sortFirst">
									<html:optionsCollection property="domesticListSortMap" value="key" label="value" />
								</nested:select>
								<nested:select property="sortFirstSub">
									<html:optionsCollection property="itemListSortOrder" value="key" label="value" />
								</nested:select>
							</td>
						</tr>
						<tr>
							<td style="padding-left: 4px;">表示件数</td>
							<td>
								<nested:select property="listPageMax" styleId="listPageMaxId">
									<html:optionsCollection property="listPageMaxMap" value="key" label="value" />
								</nested:select>&nbsp;件
							</td>
						</tr>
						<tr>
							<td style="padding: 0px 0px 0px 4px;"><a class="button_main search" href="javascript:void(0);" >検索</a></td>
							<td style="padding-top: 5px;"><a class="button_white clear" href="javascript:void(0);">リセット</a></td>
						</tr>
						</nested:nest>
					</tr>
				</table>
			</div>
			</div>
		</fieldset>

		<nested:notEmpty property="domesticOrderItemInfoList">
			<div class="paging_area">
				<div class="paging_total_top">
					<h3>全&nbsp;<nested:write property="sysDomesticItemIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="domesticNowPage" ></span>&nbsp;/&nbsp;<span class="domesticPageNum"></span>&nbsp;ページ&nbsp;）</h3>
					<h4 style="margin-left:255px;color:red;">※直送の場合は入荷予定日ではなく出荷予定日の意味になります</h4>
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


			<nested:hidden property="sysDomesticItemIdListSize" styleId="sysDomesticItemIdListSize" />
			<nested:hidden property="pageIdx" styleId="pageIdx" />
			<nested:hidden property="domesticOrderPageMax" styleId="domesticOrderPageMax" />


			<div id="list_set_area">
				<nested:nest property="domesticOrderListSearchDTO">
					<td class="td_checkDate">チェックしたデータの</td>
					<td>
					<nested:select property="checkDate" styleId="checkDateVal" styleClass="checkDate" onchange="checkDate()">
						<html:optionsCollection property="domesticMap" value="key" label="value" />
					</nested:select>

					<td class="">に</td>
					<td>
						<nested:text property="shipment" styleId="freeWard"></nested:text>
					</td>
					<td>
						<nested:select property="checkSelect" styleId="allCorrespondence">
							<html:optionsCollection property="domesticselect" value="key" label="value" />
						</nested:select>
					</td>
					<td>
						<span id="allSchedule_td">
							<nested:text property="move" styleClass="calender" styleId="allSchedule"/>
						</span>
					</td>
					<td>
						<nested:text property="kindCost" styleId="kindCost" styleClass="priceText right"></nested:text>
					</td>

					<td class="">を</td>
					<td colspan="2" style="padding-top: 14px;"><a class="button_white " id="allValueSet" href="javascript:void(0);">セット</a></td>
				</nested:nest>
			</div>

			<table id="mstTable" class="list_table01">
				<tr>
	<!-- 				<th rowspan="2"><input type="checkbox" class="check_all"/></th> -->
					<td rowspan="2" class="td_center allOrderCheckSize"><input type="checkbox" id="allOrderCheck"class="allOrderCheck checkBoxTransForm"></td>
					<th class="orderNo">受注番号</th>
					<!--<th class="serealNum">通番</th>-->
					<th rowspan="2" class="statusNm">ステータス</th>
					<th rowspan="2" class="itemOrderDate">注文書作成日</th>
					<th class="arrivalScheduleDateSize">入荷予定日(※)</th>
					<th rowspan="2" class="wholsesalerNm">問屋</th>
					<th class="makerNm">メーカー</th>
					<th rowspan="2" class="itemNm">商品名</th>
					<th rowspan="2" class="orderNum">数量</th>

				<!-- 見出しの『通番』『納入先』の列を『数量』と『備考』の間に入れる -->
					<th class="serealNum">通番</th>
					<th rowspan="2" class="listRemarks">備考</th>
					<th rowspan="2" class="purchasingCost">原価</th>
					<th rowspan="2" class="postage">送料</th>
					<th rowspan="2" class="costComfFlag">原価確認</th>
					<th class="orderCharge">注文担当</th>
					<th rowspan="2" class="stockCharge">入荷担当</th>
					<th rowspan="2" class="correspondence">対応</th>
					<th rowspan="2" class="chargeDate">対応日</th>
				</tr>
				<tr >
					<th>注文書No.</th>
					<th>入荷日</th>
					<th>メーカー品番</th>

				<!-- 見出しの『通番』『納入先』の列を『数量』と『備考』の間に入れる -->
					<th>納入先</th>
					<th>対応者</th>
				</tr>

				<tbody>
					<nested:iterate property="domesticOrderItemInfoList" indexId="idx">
						<tbody class="domesticSlipRow">
							<tr class="domestic">
								<nested:hidden property="managementCode" styleClass="managementCode"></nested:hidden>
								<nested:hidden property="printCheckFlag" styleClass="printCheckFlag"/>
								<nested:hidden property="sysDomesticItemId" styleClass="sysDomesticItemId"/>
								<nested:hidden property="itemType" styleClass="itemType"/>
								<td rowspan="2" class="count salesSlipRow td_center">
									<nested:checkbox property="orderCheckFlg" styleClass="orderCheckFlg checkBoxTransForm"/>
									<nested:hidden property="orderCheckFlg" value="off"/>
								</td>
								<td class="td_center"><nested:write property="orderNo" ></nested:write></td>
							<!--<td class="td_center"><nested:write property="serealNum"></nested:write></td> -->
								<nested:hidden property="status" styleClass="hiddenStatus"></nested:hidden>
								<td rowspan="2" class="td_center"><nested:write property="statusNm"></nested:write></td>
								<td rowspan="2" class="td_center itemOrderDate" ><nested:write property="orderSlipDate" ></nested:write></td>
								<td class="td_center"><nested:text styleClass="arrivalScheduleDate calender" property="arrivalScheduleDate" ></nested:text></td>
								<td rowspan="2" class="td_center wholsesalerNm"><nested:write property="wholsesalerNm"></nested:write></td>
								<nested:hidden property="makerId"></nested:hidden>
								<td class="td_center makerNm"><nested:write property="makerNm"></nested:write></td>
								<td rowspan="2" class="td_center itemNm"><nested:write property="itemNm"></nested:write></td>
								<td rowspan="2" class="td_center"><nested:text property="orderNum" styleClass="orderNumText numTextMinus right"></nested:text></td>

                        	<!-- 見出しの『通番』『納入先』の列を『数量』と『備考』の間に入れる -->
								<td class="td_center"><nested:write property="serealNum"></nested:write></td>
								<td rowspan="2" class="td_center"><nested:textarea property="listRemarks" styleClass="listRemarksVal"></nested:textarea></td>
								<td rowspan="2" class="td_center purchasingCost"><nested:text styleClass="price_w80  priceText purchasingCostValue" property="purchasingCost"></nested:text> 円</td>
								<td rowspan="2" class="td_center postage"><nested:write property="postage"></nested:write> 円</td>
								<td rowspan="2" class="td_center">
									<nested:notEqual property="costComfFlag" value="0">
										<nested:write property="costComfFlagNm"></nested:write>
										<nested:hidden property="costComfFlag" styleClass="costComfFlagVal"></nested:hidden>
									</nested:notEqual>
								</td>
								<td class="td_center"><nested:write property="orderCharge"></nested:write></td>
								<td rowspan="2" class="td_center"><nested:write property="stockCharge"></nested:write></td>
								<td rowspan="2" class="td_center">
									<nested:select property="correspondence" styleId="correspondence" styleClass="correspondenceVal">
										<html:optionsCollection property="domesticselect" value="key" label="value" />
									</nested:select>
								</td>
								<td rowspan="2" class="td_center"><nested:write property="chargeDate"></nested:write></td>

							</tr>

							<!-- 二段目START -->
							<tr class="rowSize">
								<td class="td_center">
									<span class="itemListRow">
										<nested:write property="purchaseOrderNo"></nested:write>
										<nested:hidden property="purchaseOrderNo" styleClass="purchaseOrderNo"/>
										<nested:hidden property="sysDomesticSlipId" styleClass="sysDomesticSlipId"/>
										<nested:hidden property="strSysDomesticSlipId" styleClass="strSysDomesticSlipId"/>
									</span>
								</td>

								<td class="td_center"><nested:write property="arrivalDate"></nested:write></td>
								<td class="td_center makerNm"><nested:write property="makerCode"></nested:write></td>

							<!-- 見出しの『通番』『納入先』の列を『数量』と『備考』の間に入れる -->
								<td class="td_center"><nested:write property="deliveryNm"></nested:write></td>
								<td class="td_center"><nested:write property="personInCharge"></nested:write></td>
							</tr>
							<!-- 二段目END -->

						</tbody>
					</nested:iterate>
				</tbody>
			</table>



	<!-- ページ(下側) 20140407 安藤 -->

			<div class="under_paging_area">
				<div class="paging_total_top">
					<h3>全&nbsp;<nested:write property="sysDomesticItemIdListSize" />&nbsp;件&nbsp;（&nbsp;<span class="domesticNowPage" ></span>&nbsp;/&nbsp;<span class="domesticPageNum"></span>&nbsp;ページ&nbsp;）</h3>
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
<!-- ページ(下側)　ここまで -->

	<div class="buttonArea">
		<ul>
			<nested:select property="moveStatus" styleId="moveStatus">
				<html:option value=""></html:option>
				<html:optionsCollection property="domesticstatusPulLdown" value="key" label="value" />
			</nested:select>
			<li class="buttonSpase">
				<a class="button_main" href="javascript:void(0);" onclick="orderStatusMove();" >ステータス移動</a>
			</li>
			<li class="buttonSpase">
				<a class="button_main update" href="javascript:void(0);">登録/更新</a>
			</li>
			<li class="buttonSpase">
				<a class="button_white domesticListDownLoad" href="javascript:void(0);">検索結果をダウンロード</a>
			</li>
			<li class="buttonSpase">
				<a class="button_white exportDomesticOrderPdf" href="javascript:void(0);">注文書一括印刷</a>
			</li>
			<li class="buttonSpase">
				<a class="button_white deleteDomesticListItem" href="javascript:void(0);">一括削除</a>
			</li>
		</ul>
	</div>
	</html:form>
</html:html>
<script type="text/javascript">

	var PRINT_CHECK_DO_NOT_RUN = "0";	//印刷確認非実行
	var PRINT_CHECK_COMPLETION = "1";	//印刷確認完了


window.onload = function(){
		$(".overlay").css("display", "none");
		$("#nmSearchFlg").val("0");
		$("#nmKanaSearchFlg").val("0");

		//一括設定初期化
		$("#checkDateVal").val("6");
		$("#freeWard").val("");
		$("#allCorrespondence").val("");
		$("#allSchedule").val("");
		$("#kindCost").val("");
		checkDate();


		//仕入原価Fromのカンマを除去
		if ($(".purchasingCostFrom").val() != 0) {
			var costFrom = $(".purchasingCostFrom")[0];
			addComma(costFrom);
		}
		//仕入原価Toのカンマを除去
		if ($(".purchasingCostTo").val() != 0) {
			var costTo = $(".purchasingCostTo")[0];
			addComma(costTo);
		}
	};

	$(function() {

		/****************************************初期設定****************************************/

				//数字の上下
		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1
		});
		$(".num").blur(function () {
			if (!checkNum(this)){
				$(this).val('');
				return;
			}
			change0(this);
			addComma(this);
		});

		$('.select').select2();

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

		//アラート
		if (document.getElementById('alertType').value != '' && document.getElementById('alertType').value != '0') {
			actAlert(document.getElementById('alertType').value);
			document.getElementById('alertType').value = '0';
		}

		if ($("tr.domestic").size() != 0) {

			//検索結果があれば検索欄を隠す処理
			if($('#searchOptionOpen').text() == "▼絞り込み") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼絞り込み");
			}

			$('#searchOptionArea').slideToggle("fast");
			$('#centerArea').slideToggle("fast");
			$('#subArea').slideToggle("fast");
		}

		//初期表示時処理
		for (var i = 0; i < $(".domestic").size(); i++) {
			//数量
			var orderNum = $(".orderNumText").eq(i)[0];
// 			//送料
// 			var postage = $(".postageValue").eq(i)[0];
			//仕入原価
			var purchasingCost = $(".purchasingCostValue").eq(i)[0];
			//カンマを付ける
			orderNum = addComma(orderNum);
// 			postage = addComma(postage);
			purchasingCost = addComma(purchasingCost);
		}

		//数量：0を消す表示用
		$(".orderNumText").focus(function(){
			var index = $(".orderNumText").index(this);
			if ($(".orderNumText").eq(index).val() == 0) {
				$(".orderNumText").eq(index).val("");
			}
		});
		//数量：0を付ける
		$(".orderNumText").blur(function(){
			var index = $(".orderNumText").index(this)
			if ($(".orderNumText").eq(index).val() == "") {
				$(".orderNumText").eq(index).val(0);
			}
		});
// 		//送料：0を消す表示用
// 		$(".postageValue").focus(function(){
// 			var index = $(".postageValue").index(this);
// 			if ($(".postageValue").eq(index).val() == 0) {
// 				$(".postageValue").eq(index).val("");
// 			}
// 		});
// 		//送料：0を付ける
// 		$(".postageValue").blur(function(){
// 			var index = $(".postageValue").index(this)
// 			if ($(".postageValue").eq(index).val() == "") {
// 				$(".postageValue").eq(index).val(0);
// 			}
// 		});

		//仕入原価：0を消す表示用
		$(".purchasingCostValue").focus(function(){
			var index = $(".purchasingCostValue").index(this)
			if ($(".purchasingCostValue").eq(index).val() == 0) {
				$(".purchasingCostValue").eq(index).val("");
			}
		});
		//仕入原価：0を付ける
		$(".purchasingCostValue").blur(function(){
			var index = $(".purchasingCostValue").index(this)
			if ($(".purchasingCostValue").eq(index).val() == "") {
				$(".purchasingCostValue").eq(index).val(0);
			}
		});

		//原価確認FLGが済の時、仕入原価を入力不可にする
		if ($(".domestic").size() > 0) {
			$(".domestic").each(function() {
				var orderStatus = $(this).find('.hiddenStatus').val();
				var costComfFlg = $(this).find('.costComfFlagVal').val();

				if (orderStatus == "5" || orderStatus == "6" || orderStatus == "7") {
					$(this).find('.correspondenceVal').prop('disabled',true);
				} else {
					$(this).find('.correspondenceVal').prop('disabled',false);
				}
				if (costComfFlg == "1") {
					$(this).find('.purchasingCostValue').prop('disabled',true);
				} else {
					$(this).find('.purchasingCostValue').prop('disabled',false);
				}

				//印刷確認フラグが0の物は色を変える
				if ($(this).find('.printCheckFlag').val() == '0') {
					$(this).parents('.domesticSlipRow').css("background-color", "#d2f1f7");
				}

			})
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

		//仕入原価の0円除去
		$("#kindCost").blur(function(){
			if ($("#kindCost").val() == 0) {
				$("#kindCost").val("");
			}
		});
		//仕入原価の0円除去
		$("#kindCost").blur(function(){
			if ($("#kindCost").val() == 0) {
				$("#kindCost").val("");
			}
		});



		/****************************************検索、メーカー名設定****************************************/

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

 		/********************************************************************************/
		//リセットボタン押下時
	    $(".clear").click(function(){

			if ((!confirm("検索欄の入力したものをリセットします。よろしいですか？"))) {
				return;
			}
			clearInfo();
	    });

 		//一括設定の「セット」ボタン押下時
	    $("#allValueSet").click(function () {
			var checkDate = $("#checkDateVal").val();
			//対応
			if (checkDate === "3") {
				var allCorrespondence = $("#allCorrespondence").val();
				if ($(".orderCheckFlg:checked").size() == 0){
					alert("一括変更対象を選択してください。");
				} else {
					$(".orderCheckFlg:checked").each(function () {
						$(this).parents('tr').find(".correspondenceVal").val(allCorrespondence);
					});
					alert("対応に一括で設定しました。\r\n「更新/登録」ボタンを押下しないとデータに反映されません。");
				}

			}

			//原価
			if (checkDate === "4") {
				var kindCost = $("#kindCost").val();
				if ($(".orderCheckFlg:checked").size() == 0){
					alert("一括変更対象を選択してください。");
				} else {
					$(".orderCheckFlg:checked").each(function () {
						var status = $(this).parents('tr').find(".hiddenStatus").val();
						if (status != "4" && status != "5") {
							$(this).parents('tr').find(".purchasingCostValue").val(kindCost);
						}
					});
					alert("原価に一括で設定しました。\r\n「更新/登録」ボタンを押下しないとデータに反映されません。");
				}
			}

			//備考
			if (checkDate === "5") {
				var freeWard = $("#freeWard").val();
				if ($(".orderCheckFlg:checked").size() == 0){
					alert("一括変更対象を選択してください。");
				} else {
					$(".orderCheckFlg:checked").each(function () {
						$(this).parents('tr').find(".listRemarksVal").val(freeWard);
					});
					alert("備考に一括で設定しました。\r\n「更新/登録」ボタンを押下しないとデータに反映されません。");
				}
			}

			//入荷予定日
			if (checkDate === "6") {

				var allSchedule = $("#allSchedule").val();
				if ($(".orderCheckFlg:checked").size() == 0){
					alert("一括変更対象を選択してください。");
				} else {
					$(".orderCheckFlg:checked").each(function () {
						$(this).parents('tr').find(".arrivalScheduleDate").val(allSchedule);
					});
					alert("入荷予定日に一括で設定しました。\r\n「更新/登録」ボタンを押下しないとデータに反映されません。");
				}
			}
	    });

 		//検索条件エリアの表示非表示設定
		$('#searchOptionOpen').click(function () {

			if($('#searchOptionOpen').text() == "▼絞り込み") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼絞り込み");
			}

			$('#searchOptionArea').slideToggle("fast");
			$('#centerArea').slideToggle("fast");
			$('#subArea').slideToggle("fast");
		});

 		//カレンダー設定
		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

//******************************************************************************************************
		if ($("#sysCorporateSalesSlipIdListSize").val() != 0) {
			var domesticPageNum = Math.ceil($("#sysDomesticItemIdListSize").val() / $("#domesticOrderPageMax").val());

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
//*********** ページ操作  *******************************************************************************************
		$(".pageNum").click (function () {

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;

			}

			$("#pageIdx").val($(this).text() - 1);
			goTransaction("domesticListPageNo.do");
		});

		//次ページへ
		$("#nextPage").click (function () {

			var maxPage = new Number($(".domesticPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);
			goTransaction("domesticListPageNo.do");
		});

		//前ページへ
		$("#backPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("domesticListPageNo.do");
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

			goTransaction("domesticListPageNo.do");
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

			goTransaction("domesticListPageNo.do");
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

			goTransaction("domesticListPageNo.do");
		});

		//前ページへ
		$("#underBackPage").click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction("domesticListPageNo.do");
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
			goTransaction("domesticListPageNo.do");
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
			goTransaction("domesticListPageNo.do");
		});

//******************************************************************************************************

		//検索
		$(".search").click (function () {
			//注文書作成日FROM~TOの整合性チェック
			if ($(".orderCreatDateFrom").val() && $(".orderCreatDateTo").val()){
				fromAry = $(".orderCreatDateFrom").val().split("/");
				toAry = $(".orderCreatDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("注文書作成日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}
			//入荷予定日FROM~TOの整合性チェック
			if ($(".arrivalScheduleDateFrom").val() && $(".arrivalScheduleDateTo").val()){
				fromAry = $(".arrivalScheduleDateFrom").val().split("/");
				toAry = $(".arrivalScheduleDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("入荷予定日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}
			//対応日FROM~TOの整合性チェック
			if ($(".chargeDateFrom").val() && $(".chargeDateTo").val()){
				fromAry = $(".chargeDateFrom").val().split("/");
				toAry = $(".chargeDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("対応日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}


			if($('#searchOptionOpen').text() == "▼絞り込み") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼絞り込み");
			}

			$('#searchOptionArea').slideToggle("fast");
			$('#centerArea').slideToggle("fast");
			$('#subArea').slideToggle("fast");
			//メッセージフラグを切る
			$("#messageFlgForm").val("0");

			$(".overlay").css("display", "block");
			$(".message").text("検索中");

			//仕入原価Fromのカンマを除去
			if ($(".purchasingCostFrom").val() != 0) {
				$(".purchasingCostFrom").val(removeComma($(".purchasingCostFrom").val()));
			}
			//仕入原価Toのカンマを除去
			if ($(".purchasingCostTo").val() != 0) {
				$(".purchasingCostTo").val(removeComma($(".purchasingCostTo").val()));
			}

			goTransaction('searchDomesticOrderList.do');
		});

		//チェックボックス全件選択・解除
		$("#allOrderCheck").click(function() {
			if(this.checked) {
				$(".orderCheckFlg").prop("checked", true);
			} else {
				$(".orderCheckFlg").prop("checked", false);
			}
		});

		//更新ボタン処理
		$(".update").click(function () {

			if ($("#sysDomesticItemIdListSize").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}
			if(!confirm("更新します。よろしいですか？")) {
				return false;
			}

			//登録の際、カンマをはずす：表示用
			for (var i = 0; i < $(".domestic").size(); i++) {

				var orderNum = $(".orderNumText").eq(i).val();
// 				var postage = $(".postageValue").eq(i).val();
				var purchasingCost = $(".purchasingCostValue").eq(i).val();

				$(".orderNumText").eq(i).val(removeComma(orderNum));
// 				$(".postageValue").eq(i).val(removeComma(removeComma(postage)));
				$(".purchasingCostValue").eq(i).val(removeComma(removeComma(purchasingCost)));

			}

			//仕入原価From/Toのカンマを除去
			deleteComma();

			goTransaction('updateDomesticOrderList.do');
		});

		//一括削除ボタン押下
		$(".deleteDomesticListItem").click(function () {
			if($(".orderCheckFlg:checked").length == 0) {
				alert("対象データを選択してください。");
				return false;
			}
			if(!confirm("選択されてるものを削除します。よろしいですか？")){
				return false;
			}

			//仕入原価From/Toのカンマを除去
			deleteComma();

			goTransaction('domesticListItemDelete.do');
		});

		//注文書一括印刷ボタン押下
		$(".exportDomesticOrderPdf").click(function () {
			if($(".orderCheckFlg:checked").length == 0) {
				alert("対象データを選択してください。");
				return false;
			}

			if(!confirm("選択されている注文書の印刷を行います。宜しいですか？")){
				return false;
			}

			if(confirm("印刷確認が完了していない注文書は、全て印刷完了にし印刷しますか?")){

				$(".overlay").css("display", "block");

				var data = $("#domesticForm").serialize() + '&printCheckFlag=1';

				$.ajax({
					url : "./exportDomesticOrdeList.do",
					type : 'POST',
					data : data,

					success : function(data, text_status, xhr) {
						$(".overlay").css("display", "none");
						window.open('domesticOrderListPrintOutFile.do', '_new');
					},
					error : function(data, text_status, xhr) {
						$(".overlay").css("display", "none");
						alert("pdfファイルの作成に失敗しました。");
					}
				});

			} else {
				if (!confirm("印刷確認をせずに、印刷しますか?")) {

					alert("印刷を中止します");
					return false;
				} else {
					var data = $("#domesticForm").serialize() + '&printCheckFlag=0';

					$(".overlay").css("display", "block");

					$.ajax({
						url : "./exportDomesticOrdeList.do",
						type : 'POST',
						data : data,

						success : function(data, text_status, xhr) {
							$(".overlay").css("display", "none");
							window.open('domesticOrderListPrintOutFile.do', '_new');
						},
						error : function(data, text_status, xhr) {
							$(".overlay").css("display", "none");
							alert("pdfファイルの作成に失敗しました。");
						}
					});
				}
			}
		});

		//検索結果DLボタン押下
		$(".domesticListDownLoad").click(function () {
			if ($("#sysDomesticItemIdListSize").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			//仕入原価From/Toのカンマを除去
			deleteComma();

			goTransaction('domesticSlipListDownLoad.do');
		});

		//ステータスのリンク押下
		$(".searchStatus").click(function() {
			//検索項目を一度空にする
			clearInfo();
			var statusId = $(this).find('.statusId').val();
			//検索条件項目：ステータス設定
			$("#searchStatus").val(statusId);
			//ページ表示件数設定：100件
			$("#listPageMaxId").val("3");
			//メッセージフラグを切る
			$("#messageFlgForm").val("0");

			if (statusId != "7") {
				//検索条件エリアを隠す
				if($('#searchOptionOpen').text() == "▼絞り込み") {
					$('#searchOptionOpen').text("▲隠す");
				} else {
					$('#searchOptionOpen').text("▼絞り込み");
				}
				$('#searchOptionArea').slideToggle("fast");
				$('#centerArea').slideToggle("fast");
				$('#subArea').slideToggle("fast");

				$(".overlay").css("display", "block");
				$(".message").text("検索中");
				//検索実行
				goTransaction('searchDomesticOrderList.do');
			} else {
				if($('#searchOptionOpen').text() == "▼絞り込み") {
					$('#searchOptionOpen').text("▲隠す");
					$('#searchOptionArea').slideToggle("fast");
					$('#centerArea').slideToggle("fast");
					$('#subArea').slideToggle("fast");
				}

			}

		});

		//レコードダブルクリック時
		$(".domesticSlipRow").dblclick(function () {
			$("#managementCode").val($(this).find(".managementCode").val());
			$.ajax({
				type : 'post',
				url : './setDomesticValue.do',
				dataType : 'json',
				data : {
					'managementCode' : $("#managementCode").val()
					}
			}).done(function () {
				console.log("ajax:success");
			})
			goTransaction("searchDomesticExhibitionItem.do");
		});


		//注文書Noリンク押下時
		$(".itemListRow").click(function () {
			$("#strSysDomesticSlipId").val($(this).find(".strSysDomesticSlipId").val());
			$.ajax({
				type : 'post',
				url : './setDomesticValue.do',
				dataType : 'json',
				data : {
					'strSysDomesticSlipId' : $("#strSysDomesticSlipId").val()
					},

			}).done(function () {
				console.log("ajax:success");
			})
			goTransactionBlank("searchDomesticSlipItem.do");
		});
	});

	//ステータス移動ボタン押下
	function orderStatusMove() {

		if ($("#sysDomesticItemIdListSize").size() == 0) {
			alert("対象の情報がありません。");
			return false;
		}
		if($(".orderCheckFlg:checked").length == 0) {
			alert("対象データを選択してください。");
			return false;

		} else if($("#moveStatus").val() == "") {
			alert("ステータスを選択してください。");
			return false;

		} else {
			if(!confirm("選択されたレコードのステータスを変更します。\r\n"+
					"ステータス以外の変更内容は反映されません\r\nよろしいですか？")) {
				return false;
			}
			goTransaction('domesticOrderStatusMove.do');
		}
	}


	//チェックデータ選択
	function checkDate() {

		//対応
		if ($("#checkDateVal").val() === "3") {
			$("#freeWard").hide();
			$("#kindCost").hide();
			$("#allCorrespondence").show();
			$("#allSchedule_td").hide();
		}
		//原価
		if ($("#checkDateVal").val() === "4") {
			$("#kindCost").show();
			$("#freeWard").hide();
			$("#allCorrespondence").hide();
			$("#allSchedule_td").hide();
		}

		//備考
		if ($("#checkDateVal").val() === "5") {
			$("#freeWard").show();
			$("#kindCost").hide();
			$("#allCorrespondence").hide();
			$("#allSchedule_td").hide();
		}
		//入荷予定日
		if ($("#checkDateVal").val() === "6") {
			$("#freeWard").hide();
			$("#kindCost").hide();
			$("#allCorrespondence").hide();
			$("#allSchedule_td").show();
		}

		return false;
	}

	//リセット処理
	function clearInfo() {
		$("#searchOptionArea input, #searchOptionArea select").each(function(){
			if (this.type == "checkbox" || this.type == "radio") {
				this.checked = false;
			} else {
			    $(this).val("");
			}
		});
		$("#nmKanaSearchFlg").val("1");
		$('select[id=makerNmKanaSearch]').val($('').val()).trigger('change');
		$("#nmSearchFlg").val("1");
		$('#makerNmSearch').val($('').val()).trigger('change');
		$("#printedDataHidden").val("off");
		$("#notPrintDataHidden").val("off");
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




</script>