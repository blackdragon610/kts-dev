<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<title>海外注文一覧</title>
	<link rel="stylesheet" href="./css/foreignOrderList.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />

	<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>
	<script src="./js/validation.js" type="text/javascript"></script>


<!--
【海外注文一覧画面】
ファイル名:foreignOrderList.jsp
作成日:2016/12/01
作成者:白井崇詞

(画面概要)

海外注文の一覧画面
 -->


	</head>

	<div class="overlay">
		<div class="messeage_box">
			<h1 class="message">検索中</h1>
			<BR />
			<p>Now Loading...</p>
			<img  src="./img/load.gif" alt="loading" ></img>
				<BR />
				<BR />
				<BR />
		</div>
	</div>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="/initForeignOrderList">
	<html:hidden property="messageFlg" styleId="messageFlgForm"/>
		<nested:nest property="registryDto">
			<nested:hidden property="messageFlg" styleId="messageFlg"/>
			<nested:notEmpty property="message">
				<div id="messageArea">
					<p class="registryMessage" style="text-align: center;"><nested:write property="message" /></p>
				</div>
			</nested:notEmpty>

		</nested:nest>
		<h1 class ="heading" id="heading">海外注文管理一覧</h1>

		<nested:hidden property="dispOrderStatus" styleId="dispOrderStatus" />
		<nested:hidden property="foreignOrderDTO.sysForeignSlipId" styleId="foreignSlipId" />
		<nested:hidden property="sysForeignSlipId" styleId="formForeignSlipId" />

		<ul class="hmenu mb10 orderStatus">
			<logic:iterate name="foreignOrderForm" property="orderStatusNumberedMap" id="orderStatusNumberedMap">
				<li class="status orderStatusLink">
					<a href="javascript:void(0);" id="orderStatusLink<bean:write name="orderStatusNumberedMap" property="key" />" ><bean:write name="orderStatusNumberedMap" property="value" /></a>
					<html:hidden name="orderStatusNumberedMap" property="key" styleClass="dispOrderStatus" />
				</li>
			</logic:iterate>
		</ul>

		<nested:nest property="foreignSlipSearchDTO">
			<fieldset class="searchOptionField">
			<legend id="searchOptionOpen">▲隠す</legend>
				<div id="searchOptionArea">
					<table class="fl searchOption option1">
						<tr>
							<td>品番</td>
							<td>
								<span class="numLengthCheck"><nested:text property="itemCode" styleId="itemCode" maxlength="11" styleClass="text_w120 numText"/></span>

								<nested:select property="searchMethod1" styleId="searchMethod1" styleClass="select">
									<html:optionsCollection property="searchMethodMap" label="value" value="key"/>
								</nested:select>
							</td>
						</tr>
						<tr>
							<td>工場品番</td>
							<td>
								<nested:text property="factoryItemCode" styleClass="text_w120 factoryItemCode" styleId="factoryItemCode"/>

								<nested:select property="searchMethod2" styleId="searchMethod2" styleClass="select">
									<html:optionsCollection property="searchMethodMap" label="value" value="key"/>
								</nested:select>
							</td>
						</tr>
						<tr>
							<td>商品名</td>
							<td colspan="2">
								<nested:text property="itemNm" styleClass="text_w265"/>
								 (部分一致)
							</td>
						</tr>
						<tr>
							<td>会社・工場名</td>
							<td colspan="2">
								<nested:text property="companyFactoryNm" styleClass="text_w120"/>
								 (部分一致)
							</td>
						</tr>
						<tr>
							<td>インボイスNo.</td>
							<td colspan="2">
								<nested:text property="invoiceNo" styleClass="text_w120 checkAlnumHypPeri" maxlength="20"/>
								 (部分一致)
							</td>
						</tr>
						<tr>
							<td>PONo.</td>
							<td colspan="2">
								<nested:text property="poNo" styleClass="alphanumeric text_w120" maxlength="10"/>
								 (部分一致)
							</td>
						</tr>
						<tr><td></td></tr>
					</table>

					<table class="fl searchOption option2">
						<tr>
							<td>注文日</td>
							<td colspan="3">
								<nested:text property="orderDateFrom" styleId="orderDateFrom" styleClass="calender" maxlength="10" /> ～
								<nested:text property="orderDateTo" styleId="orderDateTo" styleClass="calender" maxlength="10" />
							</td>
						</tr>
						<tr>
							<td>入荷日</td>
							<td colspan="3">
								<nested:text property="arrivalScheduleDateFrom" styleId="arrivalScheduleDateFrom" styleClass="calender" maxlength="10" /> ～
								<nested:text property="arrivalScheduleDateTo" styleId="arrivalScheduleDateTo" styleClass="calender" maxlength="10" />
							</td>
						</tr>
						<tr>
							<td>曖昧入荷日</td>
							<td>
								<nested:text property="vagueArrivalSchedule" styleClass="text_w120" maxlength="30"/>
								 (部分一致)
							</td>
						</tr>
						<tr>
							<td>ステータス</td>
							<td>
								<nested:select property="orderStatus" styleId="orderStatus">
									<html:option value=""/>
									<html:optionsCollection property="orderStatusNumberedMap" label="value" value="key"/>
								</nested:select>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label><nested:radio property="searchPriority" value="1" styleClass="searchPriority" styleId="searchPriority1"/>伝票を検索する</label>
								<label><nested:radio property="searchPriority" value="2" styleClass="searchPriority" styleId="searchPriority2"/>商品を検索する</label>
							</td>
						</tr>
<!-- 						<tr></tr> -->
<!-- 						<tr></tr> -->
<!-- 						<tr></tr> -->
					</table>

					<div class="h100">
						<div class="fl">
							<ul>
								<li>入荷状態</li>
								<logic:iterate id="radio1" name="foreignOrderForm" property="foreignOrderArriveStatusMap" indexId="idx">
									<li>
										<label>
											<nested:radio idName="radio1" property="arriveStatusFlg" styleId="arriveStatusFlg${idx}" value="key"/>
											<bean:write name="radio1" property="value"/>
										</label>
									</li>
								</logic:iterate>
							</ul>
						</div>

						<div class="fl radioArea">
							<ul>
								<li>支払1</li>
								<logic:iterate id="radio2" name="foreignOrderForm" property="foreignOrderPaymentStatusMap" indexId="idx">
									<li>
										<label>
											<nested:radio idName="radio2" property="paymentStatus1Flg" styleId="paymentStatus1Flg${idx}" value="key"/>
											<bean:write name="radio2" property="value"/>
										</label>
									</li>
								</logic:iterate>
							</ul>
						</div>

						<div class="radioArea">
							<ul>
								<li>支払2</li>
								<logic:iterate id="radio3" name="foreignOrderForm" property="foreignOrderPaymentStatusMap" indexId="idx">
									<li>
										<label>
											<nested:radio idName="radio3" property="paymentStatus2Flg" styleId="paymentStatus2Flg${idx}" value="key"/>
											<bean:write name="radio3" property="value"/>
										</label>
									</li>
								</logic:iterate>
							</ul>
						</div>
					</div>

					<table class="searchOption option3">
						<tr>
							<td id="deliveryDateOverFlg1">
								<nested:checkbox property="deliveryDateOverFlg1" value="on" styleClass="deliveryDateOver" styleId="overFlg1"/>
								<label for="overFlg1">納期1超過</label>
								<nested:hidden property="deliveryDateOverFlg1" value="off"/>
							</td>
							<td id="deliveryDateOverFlg2">
								<nested:checkbox property="deliveryDateOverFlg2" value="on" styleClass="deliveryDateOver" styleId="overFlg2"/>
								<label for="overFlg2">納期2超過</label>
								<nested:hidden property="deliveryDateOverFlg2" value="off"/>
							</td>
						</tr>
						<tr></tr>

						<tr>
							<td colspan="2" style="padding-left: 20px;">並び順
								<nested:select property="sortFirst" styleClass="select" styleId="sortFirst">
									<html:optionsCollection property="foreignOrderItemSortMap" label="value" value="key"/>
								</nested:select>

								<nested:select property="sortFirstSub" styleClass="select"  styleId="sortFirstSub">
									<html:optionsCollection property="foreignOrderItemSortTypeMap" label="value" value="key"/>
								</nested:select>
								表示件数
								<nested:select property="listPageMax" styleClass="select" styleId="listPageMax">
									<html:optionsCollection property="listPageMaxMap" label="value" value="key"/>
								</nested:select> 件
							</td>
						</tr>

<!-- 						<tr></tr> -->
<!-- 						<tr></tr> -->
<!-- 						<tr></tr> -->

						<tr>
							<td class="td_center" style="padding: 10px 0 0 60px;"><a class="button_main search" href="javascript:void(0);">検索</a></td>
							<td style="padding-top: 14px; padding: 10px 0 0 60px;"><a class="button_white" href="javascript:void(0);" onclick="reset();">リセット</a></td>
						</tr>
					</table>
				</div>
			</fieldset>
		</nested:nest>

		<nested:notEmpty property="errorMessageDTO">
			<nested:nest property="errorMessageDTO">
				<nested:notEmpty property="errorMessage">
					<p id="errorMessage"><nested:write property="errorMessage" /></p>
				</nested:notEmpty>
			</nested:nest>
		</nested:notEmpty>

		<nested:notEmpty property="foreignOrderSlipList">
			<div class="paging_area">
				<div class="paging_total_top">
					<h3>全 <nested:write property="sysForeignSlipIdListSize" /> 件 （ <span class="slipNowPage" ></span> / <span class="slipPageNum"></span> ページ ）</h3>
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


			<nested:hidden property="sysForeignSlipIdListSize" styleId="sysForeignSlipIdListSize" />
			<nested:hidden property="pageIdx" styleId="pageIdx" />
			<nested:hidden property="foreignOrderListPageMax" styleId="foreignOrderListPageMax" />

			<div id="list_area">
				<table id="mstTable" class="list_table">
					<thead>
						<tr>
							<td class="td_center allOrderCheckSize"><input type="checkbox" id="allOrderCheck" class="allOrderCheck checkBoxTransForm "></td>
							<th class="orderDate">注文日</th>
							<th class="poNo">インボイスNo<br/>Po No</th>
							<th class="factoryNm">会社/工場</th>
							<th class="statusSize">ステータス</th>
							<th class="codeNum">品番<br/>工場品番</th>
							<th class="itemNm">商品名</th>
							<th class="orderNum">注文数</th>
							<th class="arrivalDate">入荷日</th>
							<th class="arraival">入荷</th>
							<th class="payment">支払1</th>
							<th class="payment">支払2</th>
							<td class="slideButton">▼</td>
						</tr>
					</thead>

					<tbody>
						<nested:iterate property="foreignOrderSlipList" indexId="idx">

							<tr class="itemRow">
								<nested:hidden property="sysForeignSlipId" styleId="sysForeignSlipId"/>
								<td class="count">
									<nested:checkbox property="checkBoxFlg" styleId="checkBoxFlg" styleClass="checkBoxFlg checkBoxTransForm"/>
									<nested:hidden property="checkBoxFlg" value="off"/>
								</td>

								<td class="foreignSlipRow"><nested:write property="orderDate"/></td>
								<td class="foreignSlipRow">
									<nested:write property="invoiceNo"/>
									<br />
									<nested:write property="poNo"/>
								</td>
								<td class="foreignSlipRow"><nested:write property="companyFactoryNm"/></td>
								<td class="foreignSlipRow">
									<nested:write property="orderStatusNm"/>
									<nested:hidden property="orderStatus"/>
								</td>

								<td colspan="5" class="foreignSlipRow itemList">
									<table class="innerTable">
										<nested:iterate property="itemList">
											<nested:hidden property="sysForeignSlipItemId" styleId="sysForeignSlipItemId" styleClass="sysForeignSlipItemId"/>
											<tr class="accordion">

												<td class="deliveryOverChangeColor item codeNumWidth">
													<nested:write property="itemCode"/>
													<br />
													<nested:write property="factoryItemCode"/>
													<br />
												</td>

												<td class="deliveryOverChangeColor item itemNmWidth">
													<nested:write property="itemNm"/>
													<br />
												</td>

												<td class="deliveryOverChangeColor item orderNumWidth">
													<nested:write property="orderNum"/>
													<br />
												</td>

												<td class="deliveryOverChangeColor schedule arrivalDateWidth">
													<nested:write property="arrivalScheduleDate"/>
												</td>

												<td class="schedule arrivalStatusCellColor arraivalWidth">
													<nested:write property="arrivalStatus"/>
													<nested:hidden property="arrivalStatus" styleClass="arrivalStatus" styleId="arrivalStatus"/>
												</td>
											</tr>
										</nested:iterate>
									</table>
								</td>

								<nested:hidden property="deliveryDate1" styleClass="deliveryDate1"/>
								<nested:hidden property="deliveryDate2" styleClass="deliveryDate2"/>

								<nested:hidden property="paymentDate1" styleClass="paymentDate1"/>
									<td class="paimentCellColor1 foreignSlipRow">
										<nested:write property="paymentStatus1"/>
										<nested:hidden property="paymentStatus1" styleClass="paymentStatus1" styleId="paymentStatus1"/>
									</td>

								<nested:hidden property="paymentDate2" styleClass="paymentDate2"/>
									<td class="paimentCellColor2 foreignSlipRow">
										<nested:write property="paymentStatus2"/>
										<nested:hidden property="paymentStatus2" styleClass="paymentStatus2" styleId="paymentStatus2"/>
									</td>
								<td id="allItemDisplay${idx}" class="allItemDisplay">▽</td>
							</tr>
						</nested:iterate>
					</tbody>
				</table>
			</div>

			<div class="under_paging_area">
				<div class="paging_total_top">
					<h3>全 <nested:write property="sysForeignSlipIdListSize" /> 件 （ <span class="slipNowPage" ></span> / <span class="slipPageNum"></span> ページ ）</h3>
				</div>
				<div class="paging_num_top">
					<ul class="pager fr mb10 underPager">
					    <li class="backButton"><a href="javascript:void(0);" id="underBackPage" >&laquo;</a></li>
					    <li class="liFirstPage" ><a href="javascript:void(0);" id="underFirstPage" >1</a></li>
					    <li class="3dotLineTop"><span>...</span></li>
						<li><a href="javascript:void(0);" class="pageNum" ></a></li>
					  	<li class="3dotLineEnd"><span>...</span></li>
					    <li class="liLastPage" ><a href="javascript:void(0);" id="underLastPage"  class="lastIdx" ></a></li>
					    <li class="nextButton"><a href="javascript:void(0);" id="underNextPage">&raquo;</a></li>
					</ul>
				</div>
			</div>
		</nested:notEmpty>

		<div class="buttonArea">
			<ul>
				<li class="buttonSpase">
					<a class="button_main" href="javascript:void(0);" id="initRegistryForeignOrder">新規登録画面</a>
				</li>
				<nested:select property="orderStatus" styleId="moveStatus" styleClass="button_white">
					<html:option value=""/>
					<html:optionsCollection property="orderStatusNumberedMap" label="value" value="key"/>
				</nested:select>
				<li class="buttonSpase">
					<a class="button_main" href="javascript:void(0);" id="lumpOrderStatusMove">ステータス移動</a>
				</li>
				<li class="buttonSpase">
					<a class="button_main" href="javascript:void(0);" onclick="goInitLumpArrival();">一括入荷へ</a>
				</li>
				<li class="buttonSpase">
					<a class="button_main" href="javascript:void(0);" id="lumpPayment">一括支払へ</a>
				</li>
				<li class="buttonSpase">
					<a class="button_white" href="javascript:void(0);" id="foreignOrderListDownLoad">検索結果をダウンロード</a>
				</li>
<!-- 				<li class="buttonSpase"> -->
<!-- 					<a class="button_white" href="javascript:void(0);" id="lumpForeignSlipDelete" >一括削除</a> -->
<!-- 				</li> -->
			</ul>
		</div>
	</nested:form>

	<script type="text/javascript">
	$(function() {

		$('.overlay').css('display', 'none');

		//全件チェック用チェックボックス
		$('#allOrderCheck').click(function(){
			if (this.checked) {
				$(".checkBoxFlg").prop("checked", true);
			} else {
				$(".checkBoxFlg").prop("checked", false);
			}
		  });

		//注文ステータスリンク
		$('.orderStatusLink').click(function () {

			reset();
			var orderStatus = $(this).find('.dispOrderStatus').val();

			$('#orderStatus').val(orderStatus);

			$("#listPageMaxId").val("1");
			$("#sortFirst").val("1");
			$("#sortFirstSub").val("1");

			if (orderStatus == '7') {

				if ($('#searchOptionOpen').text() == "▼検索") {

					$('#searchOptionOpen').text("▲隠す");
					$('#searchOptionArea').slideToggle("fast");
				}
				return;
			}

			$('.search').click();
		});

		//検索条件エリアを閉じる処理
		$('#searchOptionOpen').click(function () {

			if ($('#searchOptionOpen').text() == "▼検索") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼検索");
			}

			$('#searchOptionArea').slideToggle("fast");
		});

		//商品を表示するアコーディオンメニューの処理
		$('.allItemDisplay').click(function () {

			$(this).parents("tr.itemRow").find(".accordion").slideToggle("fast");
 			$(this).parents("tr.itemRow").find(".accordion:nth-child(1)").show("fast");

 			if ($(this).text() == "▽") {
 				$(this).text("△");
 			} else if ($(this).text() == "△") {
 				$(this).text("▽");
 			}
		});

		//全ての商品を表示するアコーディオンメニューの処理
		$('.slideButton').click(function () {

			if ($(this).text() == "▼") {
 				$(this).text("▲");
 				$('.accordion').slideDown("fast");
 				$('.allItemDisplay').text("△");
 			} else if ($(this).text() == "▲") {
 				$(this).text("▼");
 				$('.accordion').slideUp("fast");
 				$('.allItemDisplay').text("▽");
 			}

			$('.accordion:nth-child(1)').show("fast");

		});

		//カレンダー表示
		$('.calender').datepicker();
		$('.calender').datepicker("option", "showOn", 'button');
		$('.calender').datepicker("option", "buttonImageOnly", true);
		$('.calender').datepicker("option", "buttonImage", './img/calender_icon.png');

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

		//検索結果がある場合の処理
		if ($('tr.itemRow').size() != 0) {

			//検索結果があれば検索欄を隠す処理
			if ($('#searchOptionOpen').text() == "▼検索") {
				$('#searchOptionOpen').text("▲隠す");
			} else {
				$('#searchOptionOpen').text("▼検索");
			}

			$('#searchOptionArea').slideToggle("fast");

			$('.accordion').hide();
			$('.accordion:nth-child(1)').show();
		}

		//検索結果の背景色変更処理
		if ($("tr.itemRow").size() != 0) {

			//伝票リストを確認するループ
			for (var i = 0; i < $('.count').length; i++) {

				//支払1の背景色変更
				var paymentStatus1 = $('.paymentStatus1').eq(i).val();

				if (paymentStatus1 == "未") {
					$('.paimentCellColor1').eq(i).addClass("untreated");
				} else if (paymentStatus1 == "済") {
					$('.paimentCellColor1').eq(i).addClass("treated");
				}

				//支払2の背景色変更
				var paymentStatus2 = $('.paymentStatus2').eq(i).val();

				if (paymentStatus2 == "未") {
					$('.paimentCellColor2').eq(i).addClass("untreated");
				} else if (paymentStatus2 == "済") {
					$('.paimentCellColor2').eq(i).addClass("treated");
				}

				//納期超過伝票の背景色変更
				var deliveryDate1 = $('.deliveryDate1').eq(i).val().split("/");
				var paymentDate1 = $('.paymentDate1').eq(i).val();
				var deliveryDate2 = $('.deliveryDate2').eq(i).val().split("/");
				var paymentDate2 = $('.paymentDate2').eq(i).val();
		 		var today = new Date().toLocaleDateString().split("/");

		 		//納期日が入力されていない場合は処理対象外とする
		 		if (deliveryDate1 == "" && deliveryDate2 == "") {
		 			continue;
		 		}

		 		//支払日が両方入力されている場合は処理対象外とする
		 		if (paymentDate1 != "" && paymentDate2 != "") {
		 			continue;
		 		}

		 		//年、月、日 に分割する処理
		 		delivery1 = new Date(deliveryDate1[0], deliveryDate1[1], deliveryDate1[2]);

		 		delivery2 = new Date(deliveryDate2[0], deliveryDate2[1], deliveryDate2[2]);

		 		today = new Date(today[0], today[1], today[2]);

		 		//今日の日付の4日前を納期超過判定の比較対象とする
		 		today.setDate(today.getDate() - 1);

		 		//今日の日付が納期日の4日以上過ぎていて、支払日の入力がない場合納期超過とする
		 		//納期１超過
	 			if (today >= delivery1 && paymentDate1 == "") {

	 				$('.itemList').eq(i).find('.deliveryOverChangeColor').css("background-color", "#FFDB99");
		 		}

	 			//納期2超過
		 		if (today >= delivery2 && paymentDate2 == "") {

		 			$('.itemList').eq(i).find('.deliveryOverChangeColor').css("background-color", "#FFA500");
		 		}
			}

			//入荷状態の背景色変更
			for (var i = 0; i < $('.accordion').length; i++) {

				var arrivalStatus = $('.arrivalStatus').eq(i).val();

				if (arrivalStatus == "未") {
					$('.arrivalStatusCellColor').eq(i).addClass("untreated");
				} else if (arrivalStatus == "済") {
					$('.arrivalStatusCellColor').eq(i).addClass("treated");
				}
			}
		}

		//一括支払
		$('#lumpPayment').click (function (){

			if ($(".itemRow").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			goTransaction('initLumpPayment.do');
		});

		//検索結果ダウンロードボタン押下時の処理
		$('#foreignOrderListDownLoad').click (function () {

			if ($(".itemRow").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}
				goTransaction('foreignOrderListDownLoad.do');
		});

		//一括削除
		$('#lumpForeignSlipDelete').click (function () {

			if ($(".itemRow").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			if ($(".checkBoxFlg:checked").length == 0) {
				alert("対象の伝票を選択してください。");
				return false;
			}

			if (confirm("選択された伝票を削除します。よろしいですか？")) {
				goTransaction("lumpForeignSlipDelete.do");
			}

			return;
		});

		//新規登録画面
		$('#initRegistryForeignOrder').click (function () {

			//メッセージフラグを切る
			$("#messageFlgForm").val(0);

			goTransaction('initRegistryForeignOrder.do');
		});

		//ステータス移動
		$('#lumpOrderStatusMove').click (function () {

			if ($(".itemRow").size() == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			if ($(".checkBoxFlg:checked").length == 0) {
				alert("対象の伝票を選択してください。");
				return false;

			} else if($("#moveStatus").val() == "") {
				alert("ステータスを選択してください。");
				return false;

			} else {
				if(!confirm("選択されたレコードのステータスを変更します。\r\n"+
						"ステータス以外の変更内容は反映されません\r\nよろしいですか？")) {
					return false;
				}
				goTransaction('lumpOrderStatusMove.do');
			}

		});

		//検索
		$('.search').click(function () {

			//メッセージフラグを切る
			$("#messageFlgForm").val(0);

			if ($('#orderDateFrom').val() && $('#orderDateTo').val()) {
				fromAry = $("#orderDateFrom").val().split("/");
				toAry = $("#orderDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("注文日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#arrivalScheduleDateFrom").val() && $("#arrivalScheduleDateTo").val()) {
				fromAry = $("#arrivalScheduleDateFrom").val().split("/");
				toAry = $("#arrivalScheduleDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("入荷予定日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			if ($("#arrivalDateFrom").val() && $("#arrivalDateTo").val()) {
				fromAry = $("#arrivalDateFrom").val().split("/");
				toAry = $("#arrivalDateTo").val().split("/");
				fromDt = new Date(fromAry[0], fromAry[1], fromAry[2]);
				toDt = new Date(toAry[0], toAry[1], toAry[2]);
				if (fromDt > toDt) {
					alert("入荷日 の検索開始日付が、検索終了日付より後の日付になっています。");
					return false;
				}
			}

			//検索エリアを閉じる処理
			$('#searchOptionOpen').text("▼検索");
			$('#searchOptionArea').slideToggle("fast");

			$('.overlay').css("display", "block");
			$('.message').text("検索中");

			goTransaction('searchForeignOrder.do');
		});

		//ページング処理
		if ($('#sysForeignSlipIdListSize').val() != 0) {
			var slipPageNum = Math.ceil($("#sysForeignSlipIdListSize").val() / $("#foreignOrderListPageMax").val());

			$(".slipPageNum").text(slipPageNum);
			$(".slipNowPage").text(Number($("#pageIdx").val()) + 1);

			if (0 == $("#pageIdx").val()) {
 				$(".pager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
 				$(".underPager > li:eq(3)").find("a").attr("class", "pageNum nowPage");
			}

			// maxDispは奇数で入力
			var maxDisp = 7;
			// slipPageNumがmaxDisp未満の場合maxDispの値をslipPageNumに変更
			if (slipPageNum < maxDisp) {

				maxDisp = slipPageNum;
			}

			var startIdx = 0;
			var center = Math.ceil(Number(maxDisp) / 2);
			var pageIdx = new Number($('#pageIdx').val());

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

			$('.pageNum').html(startIdx);

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

			for (var i = startIdx; i < endIdx && i < slipPageNum; i++) {
				var clone = $(".pager > li:eq(3)").clone();
				clone.children('.pageNum').text(i + 1);

				if (i == $('#pageIdx').val()) {

					clone.find("a").attr("class", "pageNum nowPage");

				} else {
					clone.find("a").attr("class", "pageNum");
				}

 				$('.pager > li.3dotLineEnd').before(clone);
			}
		}

		//ページ移動
		$('.pageNum').click (function () {

			if ($('#pageIdx').val() == ($(this).text() - 1)) {
				return;
			}

			$('#pageIdx').val($(this).text() - 1);
			goTransaction('foreignSlipPageNo.do');
		});

		//次のページへ
		$('#nextPage').click (function () {

			var maxPage = new Number($('.slipPageNum').eq(0).text());
			if (Number($('#pageIdx').val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}
			$('#pageIdx').val(Number($('#pageIdx').val()) + 1);
			goTransaction('foreignSlipPageNo.do');
		});

		//前のページへ
		$('#backPage').click (function () {

			if ($('#pageIdx').val() == 0) {
				alert("先頭ページです");
				return;
			}
			$('#pageIdx').val(Number($('#pageIdx').val()) - 1);
			goTransaction('foreignSlipPageNo.do');
		});

		//先頭ページへ
		$('#firstPage').click (function () {

			if ($('#pageIdx').val() == 0) {
				alert("先頭ページです");
				return;
			}

			if ($('#pageIdx').val() == ($(this).text() - 1)) {
				return;
			}

			$('#pageIdx').val(0);
			goTransaction('foreignSlipPageNo.do');
		});

		//最終ページへ
		$('#lastPage').click (function () {

			var maxPage = new Number($('.slipPageNum').eq(0).text());
			if (Number($('#pageIdx').val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($('#pageIdx').val() == ($(this).text() - 1)) {

				return;
			}

			$("#pageIdx").val(maxPage - 1);
			goTransaction('foreignSlipPageNo.do');
		});


		// ページ送り（下側）
		//次ページへ
		$("#underNextPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;
			}

			$("#pageIdx").val(Number($("#pageIdx").val()) + 1);

			goTransaction('foreignSlipPageNo.do');
		});

		//前ページへ
		$('#underBackPage').click (function () {

			if ($("#pageIdx").val() == 0) {
				alert("先頭ページです");
				return;
			}
			$("#pageIdx").val(Number($("#pageIdx").val()) - 1);
			goTransaction('foreignSlipPageNo.do');
		});

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
			goTransaction('foreignSlipPageNo.do');
		});

		//最終ページへ
		$("#underLastPage").click (function () {

			var maxPage = new Number($(".slipPageNum").eq(0).text());
			if (Number($("#pageIdx").val()) + 1 >= maxPage) {
				alert("最終ページです");
				return;
			}

			if ($("#pageIdx").val() == ($(this).text() - 1)) {

				return;
			}

			$("#pageIdx").val(maxPage - 1);
			goTransaction('foreignSlipPageNo.do');
		});

		//レコードダブルクリック時,伝票詳細画面へ
		$('.foreignSlipRow').dblclick(function () {

			//メッセージフラグを切る
			$("#messageFlgForm").val(0);

			var slipId = $(this).parents("tr.itemRow").find('#sysForeignSlipId').val();
			$('#formForeignSlipId').val(slipId);
			goTransaction('detailForeignOrderSlip.do');
		});

	});


	//一括入荷
	function goInitLumpArrival(){

		if ($(".itemRow").size() == 0) {
			alert("対象の情報がありません。");
			return;
		}

		goTransaction("initLumpArrival.do");
	}

	//リセット
	function reset() {

        $('.searchOption input, .searchOption select').each(function () {
            if (this.type == 'checkbox' || this.type == 'radio') {
                this.checked = false;
            } else {
            	$(this).val("");
            }
        });

        $('.select').val("1");
        $('.deliveryDateOver').prop('checked', false);

        $('#searchPriority1').prop('checked',true);
        $('#arriveStatusFlg3').prop('checked', true);
        $('#paymentStatus1Flg2').prop('checked', true);
        $('#paymentStatus2Flg2').prop('checked', true);

    }

	</script>
</html:html>