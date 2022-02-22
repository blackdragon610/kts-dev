<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
<head>
<title>海外注文詳細</title>

<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />

<link rel="stylesheet" href="./css/detailForeignOrder.css" type="text/css" />
<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
<link rel="stylesheet" href="./css/font-awesome.min.css"/>
<link rel="stylesheet" href="./css/common.css"/>

<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="./js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>

<script src="./js/jquery.ui.core.min.js"></script>
<script src="./js/jquery.ui.datepicker.min.js"></script>
<script src="./js/jquery.ui.datepicker-ja.min.js"></script>

<script src="./js/fw.js" type="text/javascript" type="text/javascript"></script>
<script src="./js/validation.js" type="text/javascript"></script>

<!--
【海外注文詳細画面】
ファイル名：detailForeignOrder.jsp
作成日：2016/12/01
作成者：白井崇詞

（画面概要）

業販売上データの新規登録・詳細画面
・登録先の法人は変更不可（遷移前に選択）
・判定相手は、事前に得意先マスタへ登録が必要
・入金管理は手動で行う
・納入先は得意先ごとに複数登録可能。登録がある場合はプルダウンで選択可能

（注意・補足）
・ピッキング済み、出庫済みの判定は伝票単位ではなく商品単位で管理される

-->

</head>
<div class="overlay">
	<div class="messeage_box">
		<h1 class="message">ファイル作成中</h1>
		<BR />
		<p>Now Loading...</p>
		<img src="./img/load.gif" alt="loading"></img> <BR /> <BR /> <BR />
	</div>
</div>
<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="/detailForeignOrderSlip">

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

<!-- 海外注文管理情報表示フィールド -->
	<h1 class="heading">海外注文管理詳細</h1>

		<!-- サブウィンド商品検索用 -->
	<nested:nest property="searchItemDTO">
		<nested:hidden property="itemCode" styleId="searchItemCode" />
		<nested:hidden property="itemCodeFrom" styleId="searchItemCodeFrom" />
		<nested:hidden property="itemCodeTo" styleId="searchItemCodeTo" />
		<nested:hidden property="sysSupplierId" styleId="selectSysSupplierId" />
		<nested:hidden property="sysForeignSlipItemId" styleId="searchSysForeignSlipItemId" />
		<nested:hidden property="itemNm" styleId="searchItemNm" />
		<nested:hidden property="factoryItemCode" styleId="searchFactoryItemCode"/>
		<nested:hidden property="openerIdx" styleId="openerIdx" />
		<nested:hidden property="sysItemId" styleId="searchSysItemId" />
		<nested:hidden property="rowCount" styleId="rowCount" />
	</nested:nest>

<!-- 	仕入先検索用情報 -->
	<nested:nest property="supplierSearchDTO">
		<nested:hidden property="sysSupplierId" styleId="searchSysSupplierId" />
	</nested:nest>

	<nested:nest property="warehouseStockList">
<%-- 		<nested:hidden  property="" styleId=""/> --%>
	</nested:nest>

<nested:hidden property="messageFlg" styleId="formMessageFlg"/>
<div id="fstOptionArea">
	<nested:nest property="foreignOrderDTO">
	<nested:hidden property="sysForeignSlipId" styleId="sysForeignSlipId"/>
		<table>
			<tr>
				<td>
					<fieldset class="foreignOrder_area1">

						<table>
							<tr>
								<td>PONo.</td>
								<td><nested:write property="poNo"/></td>
								<nested:hidden property="poNo" styleId="poNo"/>
							</tr>
							<tr>
								<td>インボイスNo.</td>
								<td><nested:text styleClass="text_w80 checkAlnumHypPeri" property="invoiceNo" styleId="invoiceNo" maxlength="20"/></td>
								<td class="area1_height">作成日<label class="necessary">※</label></td>
								<td><nested:text styleClass="calender text_w80 date" styleId="registDate" property="registDate" /></td>
								<td>注文日<label class="necessary">※</label></td>
								<td><nested:text styleClass="calender text_w80 date" styleId="orderDate" property="orderDate" /></td>
							</tr>
							<tr>
								<td>ステータス<label class="necessary">※</label></td>
								<td>
									<nested:select property="orderStatus" styleId="orderStatus">
										<html:optionsCollection property="orderStatusMap" label="value" value="key"/>
									</nested:select>
								</td>
								<td class="area1_height">納期１<label class="necessary">※</label></td>
								<td><nested:text styleClass="calender text_w80" styleId="deliveryDate1" property="deliveryDate1" /></td>
								<td>納期２<label class="necessary">※</label></td>
								<td><nested:text styleClass="calender text_w80" styleId="deliveryDate2" property="deliveryDate2" /></td>
							</tr>
							<tr>
								<td class="area1_height">担当者<label class="necessary">※</label></td>
								<td><nested:text styleClass="text_w100" property="personInCharge" styleId="personInCharge" /></td>
								<td colspan="2"><label>納期1超過フラグ
									<nested:checkbox property="deliveryDate1OverFlag" styleId="deliveryDate1OverFlag"/>
									<nested:hidden property="deliveryDate1OverFlag" value="off"/></label>
								</td>
								<td colspan="2"><label>納期2超過フラグ
									<nested:checkbox property="deliveryDate2OverFlag" styleId="deliveryDate2OverFlag"/>
									<nested:hidden property="deliveryDate2OverFlag" value="off"/></label>
								</td>
							</tr>
							<tr>
								<td>訂正</td>
								<td><nested:checkbox property="correctionFlag" styleId="correctionFlag"/></td>
									<nested:hidden property="correctionFlag" value="off"/>
								<td class="area1_height">支払１
								<td><nested:text styleClass="calender text_w80" styleId="paymentDate1" property="paymentDate1" /></td>
								<td>支払２
								<td><nested:text styleClass="calender text_w80" styleId="paymentDate2" property="paymentDate2" /></td>
							</tr>

<!-- 							<tr> -->
<!-- 								<td>入荷予定日<label class="necessary">※</label></td> -->
<%-- 								<td><nested:text styleClass="calender text_w80" styleId="arrivalScheduleDate" property="arrivalScheduleDate" /><label class="necessary">入荷予定日か曖昧のどちらか必須</label></td> --%>
<!-- 							</tr> -->
<!-- 							<tr> -->
<!-- 								<td>曖昧入荷日<label class="necessary">※</label></td> -->
<%-- 								<td><nested:text styleClass="text_w80" styleId="vagueArrivalSchedule" property="vagueArrivalSchedule" /><label class="necessary">入荷予定日か曖昧入荷日のどちらか必須</label></td> --%>
<!-- 							</tr> -->
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</nested:nest>

	<div id="errorMessageArea">
		<html:errors />
	</div>

<!-- 商品情報表示フィールド -->

	<fieldset class="item_area">
		<legend>商品情報</legend>
		<table class="listTable">
			<tr>
				<th id="delete_th"><a class="button_item_delete removeItem" href="Javascript:void(0);">削除</a></th>
				<th id="add_th"><a class="button_small_main addItem" href="javascript:void(0); left">追加</a></th>
				<th id="itemCode_th">品番<label class="necessary">※</label></th>
				<th id="factoryItemCode_th">工場品番</th>
				<th id="itemNm_th">商品名</th>
				<th id="itemNm_th">海外商品名</th>
				<th id="arrivalNum_th">未入荷数</th>
				<th id="orderNum_th">注文数<label class="necessary">※</label></th>
				<th id="orderNumUpdateButton_th"></th>
				<th id="unitPrice_th">仕入金額</th>
				<th id="arrivalScheduleDate_th">入荷日<label class="necessary">※</label></th>
				<th id="vagueArrivalSchedule_th">曖昧入荷予定日</th>
				<th id="sceduleDateCompleteButton_th"></th>
				<th id="back_flag_th"></th>
			</tr>
			<tr>
				<td class="td_center td_size_small"><input type="checkbox" id="allOrderCheck"class="allOrderCheck"></td>
				<td colspan="7"></td>
				<td id="orderNumUpdateButtonAll_td">
							<a id="orderNummAllUpdate" class="td_size button_small_white orderNumUpdateAllButton" href="javascript:void(0);">一括更新</a></td>
				<td colspan="1"></td>
				<td id="arrivalScheduleDateAll_th"><input type="text" class="calender" id="arrivalDateAll" size="8" placeholder="一括設定"></td>
				<td id="vagueArrivalScheduleAll_th"><input type="text" class="text_w80" id="vagueArrivalScheduleAll" maxlength="30"></td>
			</tr>
<!-- 									表示用 -->
				<nested:iterate id="itemList" property="itemList" indexId="idx">
					<tr class="itemRow">
						<td id="delete_td">
							<nested:checkbox property="deleteCheckFlg" styleClass="deleteCheckFlg"/></td>
						<td class="td_size_big"></td>
						<td id="itemCode_td"><span class="itemCode" id="detilItemCode">
							<nested:write property="itemCode"/></span></td>
						<td id="factoryItemCode_td">
							<nested:text property="factoryItemCode" styleClass="factoryItemCode text_w180" styleId="detilFactoryItemCode" maxlength="30"/></td>
						<td id="itemNm_td"><span class="itemNm" id="detilItemNm" >
							<nested:write property="itemNm"/></span></td>
						<td id="itemNm_td">
							<nested:text property="foreignItemNm" styleClass="detailForeignItemNm text_w180" styleId="detailForeignItemNm"/></td>
						<td id="arrivalNum_td">
							<nested:text property="arrivalNum" styleClass="arrivalNum num arrivalNumSize numText" styleId="detilArrivalNum"/></td>
						<td id="orderNum_td">
						<%-- <span class="spanOrderNum" id="spanOrderNum${idx}"></span> --%>
							<input type="text" class="num arrivalNumSize numText" id="detilOrderNum"/>
							<nested:hidden property="orderNum" styleClass="orderNumHidden"/>
						</td>
						<td id="orderNumUpdateButton_td">
							<a class="button_small_white orderNumUpdateButton" style="width: 40px" href="javascript:void(0);">更新</a></td>
						<td id="unitPrice_td"><span class="currencyTypeSpan"></span>
							<nested:text property="unitPrice" styleClass="unitPrice checkAlnumHypPeri text_w100 priceTextChek" styleId="detailUnitPrice"  maxlength="30"/>
<!-- 						<span class="unitPrice priceTextMinus" id="detailUnitPrice"></span> -->
<%-- 							<nested:hidden property="unitPrice" styleClass="detailHiddenListPrice checkAlnumHypPeri" /> --%>
							</td>
						<td id="arrivalScheduleDate_td">
							<nested:text property="arrivalScheduleDate" styleClass="calender detailArrivalDate detailArrivalDateSize arrivalDate"/></td>
						<td id="detailVagueArrivalSchedule_td">
							<nested:text styleClass="text_w80 vagueArrivalSchedule" styleId="detailVagueArrivalSchedule" property="vagueArrivalSchedule" maxlength="30"/></td>
						<td id="sceduleDateCompleteButton_td">
							<a class="td_size button_small_white sceduleDateCompleteButton" href="javascript:void(0);">入荷数分入荷</a></td>
							<nested:hidden property="sysForeignSlipItemId" styleClass="detailSysForeignSlipItemId" />
							<nested:hidden property="sysForeignSlipId" styleClass="detailSysForeignSlipId" />
							<nested:hidden property="sysItemId" styleClass="detailSysItemId" />
							<nested:hidden property="orderPoolFlg" styleClass="detailOrderPoolFlg"/>
							<nested:hidden property="arrivalFlag" styleClass="arrivalFlag" />
							<nested:hidden property="arrivalScheduleDateSub" styleClass="detailArrivalScheduleDateSub"/>
							<nested:hidden property="vagueArrivalScheduleSub" styleClass="detailVagueArrivalScheduleSub"/>
							<nested:hidden property="orderNum" styleClass="detailOrderNum" styleId="detailOrderNum${idx}"/>
							<nested:hidden property="stockNum" styleClass="detailStockNum"/>
							<nested:hidden property="sysArrivalScheduleId" styleClass="detailSysArrivalScheduleId"/>
							<nested:hidden property="tempArrivalNum" styleClass="tempArrivalNum"/>
							<nested:hidden property="itemCode" styleClass="hiddenItemCode"/>
						<td id="back_flag_td">
							<input type="checkbox" class="backFlagClass"></td>
					</tr>
				</nested:iterate>

<!-- 									追加用 -->
			<nested:notEmpty property="addItemList">
				<nested:iterate id="addItemList" property="addItemList" indexId="idx">
					<tr class="addItemRow">
						<td id="addDelete_td">
							<nested:checkbox property="deleteCheckFlg" styleClass="addDeleteCheck" styleId="addDeleteCheck${idx}" /></td>
						<td id="addAdd_td"><a class="button_small_main selectFromOrderPool td_size_big" href="javascript:void(0);">注文ﾌﾟｰﾙより選択</a></td>
						<td id="addItemCode_td">
							<nested:text property="itemCode" styleClass="addItemCode text_w130" styleId="addItemCode${idx}"/>
						</td>
						<td id="addFactoryItemCode_td">
							<nested:text property="factoryItemCode" styleClass="addFactoryItemCode factoryItemCode text_w180" styleId="addFactoryItemCode${idx}" maxlength="30"/>
						</td>
						<td id="addItemNm_td">
							<nested:text property="itemNm" styleClass="addItemNm text_w180" styleId="addItemNm${idx}"/>
						</td>
						<td id="addItemNm_td">
							<nested:text property="foreignItemNm" styleClass="addForeignItemNm text_w180" styleId="addForeignItemNm${idx}"/>
						</td>
						<td id="addArrivalNum_td">
							<nested:hidden property="arrivalNum" styleClass="num addArrivalNum numText" styleId="addArrivalNum${idx}"/>
						</td>
						<td id="addOrderNum_td">
							<nested:text property="orderNum" styleClass="num addOrderNum numText" styleId="addOrderNum${idx}"/>
						</td>
						<td id="addOrderNumUpdateButton_td">
							<!-- <a class="button_small_white orderNumUpdateButton" style="width: 40px" href="javascript:void(0);">更新</a> -->
							</td>
						<td id="addUnitPrice_td">
							<nested:text property="unitPrice" style="text-align: right;" styleClass="text_w100 number addUnitPrice priceTextChek" styleId="addUnitPrice${idx}"/>
						</td>
						<td id="addArrivalScheduleDate_td">
							<nested:text property="arrivalScheduleDate" styleClass="calender addArrivalDate arrivalDate" styleId="addArrivalDate${idx}"/>
						</td>
						<td id="addVagueArrivalSchedule_td">
							<nested:text styleClass="text_w80 addVagueArrivalSchedule vagueArrivalSchedule" styleId="addVagueArrivalSchedule" property="vagueArrivalSchedule" maxlength="30"/>
						</td>
						<td id="addSceduleDateCompleteButton_td"></td>
							<nested:hidden property="sysForeignSlipItemId" styleClass="addSysForeignSlipItemId" />
							<nested:hidden property="sysItemId" styleClass="addSysItemId" />
<%-- 							<nested:hidden property="orderPoolFlg" styleClass="addOrderPoolFlg"/> --%>

							<nested:hidden property="arrivalFlag" styleClass="arrivalFlag" />
							<nested:hidden property="arrivalScheduleDate" styleClass="detailArrivalScheduleDate"/>
							<nested:hidden property="sysArrivalScheduleId" styleClass="addSysArrivalScheduleId"/>

					</tr>
				</nested:iterate>
			</nested:notEmpty>
		</table>
	</fieldset>

<div id="clearArea">
<!-- 仕入先情報表示フィールド -->
<%-- <nested:hidden property="sysSupplierId" styleId="sysSupplierId"/> --%>
	<nested:nest property="supplierDTO">
		<div id="supplier">
			<fieldset class="supplier_area">
				<legend>仕入先情報</legend>
				<table>
					<tr class="wordBreak text_w150">
						<td class="w100">仕入先ID<label class="necessary">※</label></td>
						<td>
							<nested:text name="foreignOrderForm" property="sysSupplierId" styleId="sysSupplierId" styleClass="text_w100"/>&nbsp;<a class="button_main" id="searchSupplier" href="javascript:void(0);">検索</a>
						</td>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td>仕入先番号</td>
						<td>
							<span id="supplierNo"><nested:write property="supplierNo"/></span>
						</td>
					</tr>
					<tr class="wordBreak text_w150">
						<td>会社・工場名</td>
						<td>
							<span id="companyFactoryNm"><nested:write property="companyFactoryNm"/></span>
						</td>
					</tr>
					<tr>
						<td>住所</td>
						<td>
							<span id="address"><nested:write property="address"/></span>
						</td>
					</tr>
					<tr>
						<td>電話番号</td>
						<td>
							<span id="tel"><nested:write property="tel"/></span>
						</td>
					</tr>
					<tr>
						<td>FAX番号</td>
						<td>
							<span id="fax"><nested:write property="fax"/></span>
						</td>
					</tr>
					<tr>
						<td>担当者</td>
						<td>
							<span id="contactPersonNm"><nested:write property="contactPersonNm"/></span>
						</td>
					</tr>
					<tr>
						<td>メールアドレス</td>
						<td>
							<span id="mailAddress"><nested:write property="mailAddress"/></span>
						</td>
					</tr>
					<tr>
						<td>貿易条件</td>
						<td>
							<span id="tradeTerms"><nested:write property="tradeTerms"/></span>
						</td>
					</tr>
					<tr>
						<td>支払条件1</td>
						<td>
							<nested:text name="foreignOrderForm" property="foreignOrderDTO.paymentTerms1" styleId="paymentTerms1" styleClass="text_w50 numText number" style="text-align: right;" maxlength="3"/>&nbsp;％
							&nbsp;支払条件2
							<nested:text name="foreignOrderForm" property="foreignOrderDTO.paymentTerms2" styleId="paymentTerms2" styleClass="text_w50 numText number" style="text-align: right;" maxlength="3"/>&nbsp;％
						</td>
					</tr>
					<tr>
						<td>取引通貨</td>
						<td>
							<span id="currencyNm"><nested:write property="currencyNm"/></span>
							<nested:equal value="0.0" property="rate">
								<span id="rate"><bean:write name="foreignOrderForm" property="noRate"/></span>
							</nested:equal>
							<nested:notEqual value="0.0" property="rate">
								<span id="rate"><nested:write property="rate"/></span>
							</nested:notEqual>
							<nested:hidden property="currencyType" styleId="currencyType"/>
						</td>
					</tr>
					<tr>
						<td>仕入先</td>
						<td rowspan="2">
							<nested:select  name="foreignOrderForm" property="foreignOrderDTO.leadTime" styleId="suppplierLeadTime">
								<html:optionsCollection property="leadTimeMap" label="value" value="key"/>
							</nested:select>
							<a class="button_main" id="leadTimeButton" href="javascript:void(0);">納期設定</a>
						</td>
					</tr>
					<tr>
						<td>リードタイム</td>
					</tr>
					<tr>
						<td>銀行名</td>
						<td>
							<span id="bankNm"><nested:write property="bankNm"/></span>
						</td>
					</tr>
					<tr>
						<td>支店名</td>
						<td>
							<span id="branchNm"><nested:write property="branchNm"/></span>
						</td>
					</tr>
					<tr>
						<td>住所</td>
						<td>
							<span id="bankAddress"><nested:write property="bankAddress"/></span>
						</td>
					</tr>
					<tr>
						<td>SWIFT CODE</td>
						<td>
							<span id="swiftCode"><nested:write property="swiftCode"/></span>
						</td>
					</tr>
					<tr>
						<td>ACCOUNT NO.</td>
						<td>
							<span id="accountNo"><nested:write property="accountNo"/></span>
						</td>
					</tr>
					<tr>
						<td>備考</td>
						<td>
							<span id="supplierRemarks"><nested:write property="supplierRemarks"/></span>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
	</nested:nest>
		<nested:nest property="foreignOrderDTO">
			<fieldset class="price_area">
				<legend>金額情報</legend>
				<table>
					<tr>
						<td>小計：</td>
						<td><span class="currencyTypeSpan"></span><nested:text styleClass="text_w100 priceTextChek number subTotal" style="text-align: right;" property="subTotal" /></td>
						<td></td>
					</tr>
					<tr>
						<td>その他費用：</td>
						<td><span class="currencyTypeSpan"></span><nested:text styleClass="text_w100 priceTextChek number otherExpenses" style="text-align: right;" property="otherExpenses" /></td>
						<td></td>
					</tr>
					<tr>
						<td>値引き：</td>
						<td><span class="currencyTypeSpan"></span><nested:text styleClass="text_w100 priceTextChek number discount" style="text-align: right;" property="discount" /></td>
						<td></td>
					</tr>
					<tr>
					<tr>
						<td>合計：</td>
						<td><span class="currencyTypeSpan"></span><nested:text styleClass="text_w100 priceTextChek number total" style="text-align: right;" property="total" /></td>
						<td></td>
					</tr>
					<tr>
						<td>支払1：</td>
						<td><span class="currencyTypeSpan"></span><nested:text styleClass="text_w100 priceTextChek number payment1" style="text-align: right;" property="payment1" /></td>
						<td></td>
					</tr>
					<tr>
						<td>支払2：</td>
						<td><span class="currencyTypeSpan"></span><nested:text styleClass="text_w100 priceTextChek number payment2" style="text-align: right;" property="payment2" /></td>
						<td><a class="button_main reSum" id="button_a" href="javascript:void(0);">再計算</a></td>
					</tr>
					<tr>
					</tr>
				</table>
			</fieldset>

			<fieldset class="remarks_area1">
				<legend>注文書備考</legend>
				<table>
					<tr>
						<td class="td_size">メモ　　　</td>
						<td><nested:textarea rows="2" cols="60" styleClass="w250 ime_on" property="memo" /></td>
					</tr>
				</table>
			</fieldset>

			<fieldset class="remarks_area2">
				<legend>振込み依頼備考</legend>
				<table>
				<tr>
					<td class="td_size">振込み理由</td>
					<td><nested:textarea rows="2" cols="60" styleClass="w250 ime_on transferReason" property="transferReason" /></td>
				</tr>
			</table>
		</fieldset>
	</nested:nest>
</div>
</div>

<!-- 	フッターボタン -->
			<footer class="footer sPlanning">
				<nested:nest property="foreignOrderDTO">
					<ul style="position: relative;">
						<li class="footer_button">
							<a class="button_white" id="returnForeignOrderList" href="javascript:void(0);">一覧に戻る</a>
						</li>

						<li class="footer_button">
							<nested:equal value="0" property="sysForeignSlipId">
								<a class="button_main" id="registry" href="javascript:void(0);">登録</a>
							</nested:equal>
							<nested:notEqual value="0" property="sysForeignSlipId">
								<a class="button_main" id="update" href="javascript:void(0);">更新</a>
							</nested:notEqual>
						</li>
						<li class="footer_button">
							<a class="button_main" id="printOrderSheet" href="javascript:void(0);">注文書出力</a>
						</li>
						<li class="footer_button">
							<a class="button_main" id="transferRequestForm1" href="javascript:void(0);">振込依頼書(1)</a>
						</li>
						<li class="footer_button">
							<a class="button_main" id="transferRequestForm2" href="javascript:void(0);">振込依頼書(2)</a>
						</li>
<!-- 						<li class="footer_button"> -->
<!-- 							<a class="button_white" id="delete" href="javascript:void(0);">伝票の削除</a> -->
<!-- 						</li> -->
						<li class="footer_button">
							<a class="button_white" id="clear" href="javascript:void(0);">リセット</a>
						</li>
						<li class="footer_button">
							<nested:notEqual value="0" property="sysForeignSlipId">
								<a class="button_main" href="javascript:void(0);" id="initRegistryForeignOrder">新規作成</a>
							</nested:notEqual>
						</li>
					</ul>
				</nested:nest>
			</footer>

	</nested:form>
<script type="text/javascript">

	$(document).ready(function() {
		$(".overlay").css("display", "none");

		//仕入先Idが入力されていたら検索
		if ($("#sysSupplierId").val()) {
			$("#searchSupplier").trigger("click");
			$(this).parents("supplier_area").find("#searchSupplier").trigger("click");
		}
	});
	//商品レコードカウント用
	var addItemRowCount = 50;

	$(function () {

		//**************** テーブル変数 *************************************************************************************

		//登録済み用テーブル
		var resultArea = $('tr.itemRow');
		//追加用テーブル
		var initAddItemRow = $("tr.addItemRow");

		//**************** 初期表示 *************************************************************************************

		//エラー時、追加レコードを表示させる
		for (var i = 0; i < initAddItemRow.size(); i++) {

			//商品コードがあれば表示
			if (initAddItemRow.eq(i).find(".addItemCode").val() != "") {

				var itemRowTr = initAddItemRow.eq(i);
				itemRowTr.show();
				continue;
			}
			var itemRowTr = initAddItemRow.eq(i);
			itemRowTr.hide();
			addItemRowCount--;
		}

		//商品情報の最初の1レコード分を表示
		var trs = $("tr.addItemRow");
		var tr = trs.eq(addItemRowCount);

		//詳細画面時、追加レコードは表示させない
		if ($("#sysForeignSlipId").val() == 0) {

			tr.show();
			addItemRowCount++;
		}


		//残り注文数を一時保存
		for (var i = 0; i < resultArea.size(); i++){
			var tempArrivalNum = new Number($(".arrivalNum").eq(i).val());
			$(".tempArrivalNum").eq(i).val(tempArrivalNum);
		}

		//注文数の表示
		for (var i = 0; i < resultArea.size(); i++) {

			//hiddenにあるorderNumを取得しカンマを付与してからspanタグのorderNumに格納して表示
/* 			resultArea.eq(i).find(".spanOrderNum").val(resultArea.eq(i).find(".orderNumHidden").val());
			var orderNum = resultArea.eq(i).find(".spanOrderNum")[0];
			orderNum = addComma(orderNum);

			resultArea.eq(i).find(".spanOrderNum").html(resultArea.eq(i).find(".spanOrderNum").val());
 */
			//ステータスがA,B以外は仕入れ金額、工場品番、海外商品名は非活性
			if ($("#orderStatus").val() != 0 && $("#orderStatus").val() != 1) {
				resultArea.eq(i).find("#detailUnitPrice").prop('disabled',true);
				resultArea.eq(i).find("#detailForeignItemNm").prop('disabled',true);
				resultArea.eq(i).find("#detilFactoryItemCode").prop('disabled',true);
			}
			orderNum = resultArea.eq(i).find(".orderNumHidden").val();
			var arrivalNum = resultArea.eq(i).find(".arrivalNum").val();
			if(arrivalNum == 0) {
				resultArea.eq(i).find(".backFlagClass").prop( "checked", true );
				resultArea.eq(i).find(".arrivalNum").prop('disabled',true);
 				resultArea.eq(i).find("#detilOrderNum").removeClass('num');
				resultArea.eq(i).find("#detilOrderNum").prop('disabled',true);
				resultArea.eq(i).find(".orderNumUpdateButton").hide('slow');
			}else {
				if(arrivalNum == orderNum) {
					resultArea.eq(i).find(".backFlagClass").prop( "checked", false );
				}else {
					resultArea.eq(i).find(".backFlagClass").prop( "checked", true );
					resultArea.eq(i).find(".arrivalNum").prop('disabled',true);
	 				resultArea.eq(i).find("#detilOrderNum").removeClass('num');
					resultArea.eq(i).find("#detilOrderNum").prop('disabled',true);
					resultArea.eq(i).find(".orderNumUpdateButton").hide('slow');
				}
			}
			resultArea.eq(i).find(".backFlagClass").change(function() {
				var order_num = $(this).parent().parent().find(".orderNumHidden").val();
				var arrival_num = $(this).parent().parent().find(".arrivalNum").val();
				console.log($(this).parent().parent().find(".hiddenItemOrderDate").val());
			    if(this.checked) {
			    	if(order_num == arrival_num) {
			    		$(this).parent().parent().find(".backFlagClass").prop( "checked", false );
			    	}
			    }else {
//			    	if(arrival_num == 0) {
				    	$(this).parent().parent().find("#detilArrivalNum").val($(this).parent().parent().find(".orderNumHidden").val());
				    	$(this).parent().parent().find("#detilArrivalNum").prop('disabled',false);
				    	$(this).parent().parent().find("#detilOrderNum").prop('disabled',false);
						formatArrivalData($(this).parent().parent().find(".detailSysItemId").val(),
								$(this).parent().parent().find(".detailSysForeignSlipId").val(),
								$(this).parent().parent().find(".detailSysForeignSlipItemId").val(),
								$(this).parent().parent().find(".detailSysArrivalScheduleId").val(),
								$(this).parent().parent().find(".orderNumHidden").val());
						updateFlag = 1;
//			    	}
			    }
			});
		}

		//登録後、詳細画面時、仕入先IDの変更不可処理
		if ($("#sysForeignSlipId").val() != 0) {
			$("#sysSupplierId").prop('disabled',true);
		}

		//仕入先情報に通貨があれば金額情報欄textbox左に通貨表示
		if ($("#currencyType").val() != "") {
			$(".currencyTypeSpan").html($("#currencyType").val() + " ");
		}

		//振込理由欄が初期表示ならクラス追加
		if ($(".transferReason").val() == "") {

			$(".transferReason").addClass("transferReasonColor");
			$(".transferReason").val("1行全角37文字、半角86文字、5行まで");
		}

		//曖昧入荷日欄が初期表示クラス追加
		if ($("#vagueArrivalScheduleAll").val() == "") {

			$("#vagueArrivalScheduleAll").addClass("transferReasonColor");
			$("#vagueArrivalScheduleAll").val("一括設定");
		}

		//納期1超過フラグが解除されていない場合、納期2超過フラグは非活性
		if ($("#deliveryDate1OverFlag").prop("checked")) {

			$("#deliveryDate2OverFlag").prop('disabled',true);
		}

		//納期2超過フラグが解除されていない場合、納期1超過フラグは非活性
		if (!$("#deliveryDate2OverFlag").prop("checked")) {

			$("#deliveryDate1OverFlag").prop('disabled',true);
		}

		//仕入先IDが0なら空文字格納
		if ($("#sysSupplierId").val() == 0 || $("#sysSupplierId").val() == "０") {
			$("#sysSupplierId").val("");
		}

		//初期画面時、金額欄に０入れ（BigDesimalなため）
		if ($("#sysForeignSlipId").val() == 0) {

			//詳細画面、小計、その他費用、合計用
			$(".subTotal").val(0);
			$(".otherExpenses").val(0);
			$(".discount").val(0)
			$(".total").val(0);
			$('.payment1').val(0);
			$('.payment2').val(0);
		}

		//**************** 商品情報 小計 合計金額 **************************************************************************************

		//登録済み用カンマ付与
		for (var i = 0; i < resultArea.size(); i++) {

			//定価カンマ付与
			resultArea.eq(i).find("#detailUnitPrice").val(resultArea.eq(i).find("#detailUnitPrice").val());
			var listPrice = resultArea.eq(i).find("#detailUnitPrice")[0];
			listPrice = addComma(listPrice);
			
			//表示
// 			resultArea.eq(i).find("#detailUnitPrice").html($("#currencyType").val() + " " + resultArea.eq(i).find("#detailUnitPrice").val());


			//入荷予定日が空の場合、入荷済み情報の入荷予定日を入れる
			if (resultArea.eq(i).find(".detailArrivalDate").val() == "") {
				resultArea.eq(i).find(".detailArrivalDate").val(resultArea.eq(i).find(".detailArrivalScheduleDateSub").val());
			}

			//入荷予定日が空の場合、入荷済み情報の入荷予定日を入れる
			if (resultArea.eq(i).find("#detailVagueArrivalSchedule").val() == "") {
				resultArea.eq(i).find("#detailVagueArrivalSchedule").val(resultArea.eq(i).find(".detailVagueArrivalScheduleSub").val());
			}
			resultArea.eq(i).find("#detilOrderNum").val(resultArea.eq(i).find(".orderNumHidden").val());
			resultArea.eq(i).find("#detilOrderNum").bind('keyup mouseup spinstop',function(e){
			});
			resultArea.eq(i).find(".orderNumUpdateButton").click(function () {
				console.log($(this).parent().parent().find("#detilOrderNum").val());
				formatArrivalData($(this).parent().parent().find(".detailSysItemId").val(),
						$(this).parent().parent().find(".detailSysForeignSlipId").val(),
						$(this).parent().parent().find(".detailSysForeignSlipItemId").val(),
						$(this).parent().parent().find(".detailSysArrivalScheduleId").val(),
						$(this).parent().parent().find("#detilOrderNum").val());
				updateFlag = 1;
			});
			//入荷予定数が0の場合、入荷数と入荷予定日を非活性
			if (resultArea.eq(i).find(".arrivalNum").val() == 0) {
				resultArea.eq(i).find(".arrivalNum").removeClass('num');
				resultArea.eq(i).find(".detailArrivalDate").prop('disabled',true);
				resultArea.eq(i).find(".detailArrivalDate").removeClass('calender');
				resultArea.eq(i).find("#detailVagueArrivalSchedule").prop('disabled',true);
			}
		}

		//日付入力用のカレンダーを設定
		$(".calender").datepicker();
		$(".calender").datepicker("option", "showOn", 'button');
		$(".calender").datepicker("option", "buttonImageOnly", true);
		$(".calender").datepicker("option", "buttonImage", './img/calender_icon.png');

		//スピナー設定
		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1,

		});

		//詳細画面、小計、その他費用、合計用
		var subTotal = $(".subTotal")[0];
		var otherExpenses = $(".otherExpenses")[0];
		var total = $(".total")[0];
		var payment1 = $('.payment1')[0];
		var payment2 = $('.payment2')[0];

		//カンマ付与
		addComma(subTotal);
		addComma(otherExpenses);
		addComma(total);
		addComma(payment1);
		addComma(payment2);

		//直接入力用
// 		$(".addUnitPrice").blur(function() {
// 			//仕入れ金額にカンマ付与
// 			var listPrice = initAddItemRow.find(".addUnitPrice")[0];
// 			addComma(listPrice);
// 		});

		//計算ボタン
		$('.reSum').click(function() {

// 			var unitPrice = $(this).find('.unitPrice').val();
// 			var subtotal = Number(removeCommaList(unitPrice)) * $(this).find('.arrivalNum').val();

			//小計を初期化
			$('.subTotal').val(0);

			//合計値の用意
			var detailSubSum = 0;
			var detailSum = 0;
			var subSum = 0;
			var sum = 0;

			//登録用商品の計算
			for (var i = 0; i < resultArea.size(); i++) {

				//注文数の数値が0でない場合は計算
				if (resultArea.eq(i).find('.detailOrderNum').val() != "") {
					//カンマ除去
					removeCommaList($('.priceTextChek'));

					var detailHiddenListPrice = resultArea.eq(i).find('#detailUnitPrice').val();
					var detailOrderNum = resultArea.eq(i).find('.detailOrderNum').val();

// 					var unitPrice = removeComma(detailOrderNum);
					//注文数と仕入金額を掛ける
					detailSubSum += decimalPoint(Number(detailHiddenListPrice) * Number(detailOrderNum));
				}
			}

			//追加用商品の計算
			for (var i = 0; i < addItemRowCount; i++) {

				//注文数の数値が0でない場合は計算
				if (initAddItemRow.eq(i).find('.addOrderNum').val() != 0) {

					var addOrderNum = initAddItemRow.eq(i).find('.addOrderNum').val();
					//カンマ取る
					var addUnitPrice = removeComma(initAddItemRow.eq(i).find('.addUnitPrice').val());

					//注文数と仕入金額を掛ける
					subSum += decimalPoint(Number(addUnitPrice) * Number(addOrderNum));
				}
			}

			//subSum(追加商品合計)とdetailSubSum（登録済み商品合計）を足したものを小計に格納
			var result = Number(subSum) + Number(detailSubSum);

			//小計の桁数判定
			if (result >= 10000000) {
				alert("金額は7桁までの入力です。");
				$('.subTotal').val();
				$('.total').val(sum);
				return false;
			}
			//小数点第3位で切り上げ
			$('.subTotal').val(Math.ceil(result * 100) / 100);

			//小計をsubSum（小計）に格納
			subSum = Number($('.subTotal').val());

			//値引きのカンマ剥奪
			var otherExpenses = removeComma($('.otherExpenses').val());
			//値引きのカンマ剥奪
			var discount = removeComma($('.discount').val());

			//sum(合計)にsubSum(小計)とそのほか費用を足して格納
			sum = decimalPoint(Number(subSum) + Number(otherExpenses) - Number(discount));

			//合計欄に格納
			$('.total').val(sum);

			//値引きにカンマ付与
			var commaSet = $('.discount')[0];
			addComma(commaSet);

			//合計の桁数判定
			if (result >= 10000000) {
				alert("金額は7桁までの入力です。");
				$('.total').val(0);
				$('.subTotal').val();
				return false;
			}

			//支払い１と支払い2のどちらかに情報が入っていれば計算
			if ($('#paymentTerms1').val() != 0 || $('#paymentTerms2').val() != 0) {

				//支払い条件１と２を足して100になっていなければアラーと
				if (Number($('#paymentTerms1').val()) + Number($('#paymentTerms2').val()) != 100) {

					alert("支払条件1と2の入力に誤りがあります。");
				} else {

					//カンマ取る
					var reComma = $('.total').val();
					removeComma(reComma);

					//合計から支払い条件を計算
					var paymentTerms1 = Number(reComma) * Number($('#paymentTerms1').val()) / 100;

					//誤差が生じるため、小数点第四位以降を切り捨て
					result = Math.floor(result * 1000) / 10;
					paymentTerms1 = Math.floor(paymentTerms1 * 1000) / 10;

					//小数点第3位で切り上げ
					$('.subTotal').val(Math.ceil(result) / 100);
					paymentTerms1 = Math.ceil(paymentTerms1) / 100;

					$('.payment1').val(paymentTerms1);

					//カンマ付与
					$('.payment1').val(paymentTerms1);
					var commaSet = $('.payment1')[0];
					addComma(commaSet);
				}
			} else {
					$('.payment1').val(0);
					$('.payment2').val(0);
			}

			//支払い1と支払い2のどちらかに情報が入っていれば計算
			if ($('#paymentTerms1').val() != 0 || $('#paymentTerms2').val() != 0) {

				//支払い条件１と２を足して100になっていなければアラーと
				if (Number($('#paymentTerms1').val()) + Number($('#paymentTerms2').val()) != 100) {

					alert("支払い条件1と2の入力に誤りがあります。");
				} else {

					//カンマ取る
					var reComma = $('.total').val();
					removeComma(reComma);

					//合計から支払い条件を計算
					var paymentTerms2 = Number(reComma) * Number($('#paymentTerms2').val()) / 100;

					//誤差が生じるため、小数点第四位以降を切り捨て
					paymentTerms2 = Math.floor(paymentTerms2 * 1000) / 10;

					//小数点第3位で切り上げ
					paymentTerms2 = Math.ceil(paymentTerms2) / 100;
					$('.payment2').val(paymentTerms2);

					//カンマ付与
					$('.payment2').val(paymentTerms2);
					var commaSet = $('.payment2')[0];
					addComma(commaSet);
				}
			} else {
				$('.payment1').val(0);
				$('.payment2').val(0);
			}

			//それぞれにカンマ付与
			var priceTextChek = $(".priceTextChek")[0];
			addComma(priceTextChek);

			otherExpenses = $('.otherExpenses')[0];
			addComma(otherExpenses);

			$('.subTotal').val(subSum);
			subSum = $('.subTotal')[0];
			addComma(subSum);

			$('.total').val(sum);
			sum = $('.total')[0];
			addComma(sum);
		});

		//**************** 支払い条件自動入力 ************************************************************************************

		//支払い条件１
		$('#paymentTerms1').blur(function() {

			var paymentTerms1 = Number($('#paymentTerms1').val());
			var paymentTerms2 = Number($('#paymentTerms2').val());

			//入力された条件が100以内でなければアラート
			if (paymentTerms1 > 100) {

				alert("支払条件1に誤りがあります。");
				$('#paymentTerms1').val(0);
				$('#paymentTerms2').val(0);

				return false;
			}

			//条件１と２を足して100でなけれ条件２に自動入力
			if (paymentTerms1 + paymentTerms2 != 100) {

				$('#paymentTerms2').val(100 - paymentTerms1);
			}
		});

		//支払い条件２
		$('#paymentTerms2').blur(function() {

			var paymentTerms1 = Number($('#paymentTerms1').val());
			var paymentTerms2 = Number($('#paymentTerms2').val());

			//入力された条件が100以内でなければアラート
			if (paymentTerms2 > 100) {

				alert("支払条件2に誤りがあります。");
				$('#paymentTerms1').val(0);
				$('#paymentTerms2').val(0);

				return false;
			}

			//条件１と２を足して100でなけれ条件２に自動入力
			if (paymentTerms1 + paymentTerms2 != 100) {

				$('#paymentTerms1').val(100 - paymentTerms2);
			}
		});
		//**************** プレースホルダ機能 *************************************************************************************

		//振込理由欄
		$(".transferReason").focus(function() {

			$(".transferReason").removeClass("transferReasonColor");
			//初期表示状態なら空白に
			if ($(".transferReason").val() == "1行全角37文字、半角86文字、5行まで") {
				$(".transferReason").val("");
			}
		});

		//振込理由欄
		$(".transferReason").blur(function() {

			//未入力だったら再入力
			if ($(".transferReason").val() == "") {

				$(".transferReason").addClass("transferReasonColor");
				$(".transferReason").val("1行全角37文字、半角86文字、5行まで");
			}
		});

		//曖昧入荷日一括設定欄
		$("#vagueArrivalScheduleAll").focus(function() {

			$("#vagueArrivalScheduleAll").removeClass("transferReasonColor");
			//初期表示状態なら空白に
			if ($("#vagueArrivalScheduleAll").val() == "一括設定") {
				$("#vagueArrivalScheduleAll").val("");
			}
		});

		//曖昧入荷日一括設定欄
		$("#vagueArrivalScheduleAll").blur(function() {

			//未入力だったら再入力
			if ($("#vagueArrivalScheduleAll").val() == "") {

				$("#vagueArrivalScheduleAll").addClass("transferReasonColor");
				$("#vagueArrivalScheduleAll").val("一括設定");
			}
		});

		//**************** *************************************************************************************

		$('itemRow').each(function () {
			var unitPrice = $(this).find('.unitPrice').val();
			var subtotal = Number(removeCommaList(unitPrice)) * $(this).find('.arrivalNum').val();
			var subtotalWithTax = 0;
			if ($(this).find('').text() == 77) {
				subtotalWithTax = subtotal;
			} else {
// 				var taxRate = (100 + 8) / 100;
// 				if (subtotal < 0) {
// 					subtotal = subtotal * -1;
// 					subTotalWithTax = Math.floor(subtotal * taxRate);
// 					subtoTalWithTax = subtotalWithTax * -1;
// 					subtotal = subtotal * -1;
// 				} else {
// 					subtotalWithTax = Math.floor(subtotal * taxRate);
// 				}
			}

			$(this).find(".subtotal").text(subtotal.toLocaleString());
			$(this).find(".subtotalWithTax").text(subtotalWithTax.toLocaleString());
		});

		$('addItemRow').each(function () {
			var unitPrice = $(this).find('.unitPrice').val();
			var subtotal = Number(removeCommaList(unitPrice)) * $(this).find('.arrivalNum').val();
			var subtotalWithTax = 0;
			if ($(this).find('').text() == 77) {
				subtotalWithTax = subtotal;
			} else {
// 				var taxRate = (100 + 8) / 100;
// 				if (subtotal < 0) {
// 					subtotal = subtotal * -1;
// 					subTotalWithTax = Math.floor(subtotal * taxRate);
// 					subtoTalWithTax = subtotalWithTax * -1;
// 					subtotal = subtotal * -1;
// 				} else {
// 					subtotalWithTax = Math.floor(subtotal * taxRate);
// 				}
			}
			$(this).find(".subtotal").text(subtotal.toLocaleString());
			$(this).find(".subtotalWithTax").text(subtotalWithTax.toLocaleString());
		});

		//**************** //入力フォーム初期値の0を消す処理*************************************************************************************

		$('.number').focus(function(){
			var index = $(".number").index(this);
			if ($(".number").eq(index).val() == 0) {
				$(".number").eq(index).val("");
			}
		});

		$('.number').blur(function(){
			var index = $(".number").index(this);
			if ($(".number").eq(index).val() == "") {
				$(".number").eq(index).val(0);
			}
		});

		//**************** 仕入先情報  *************************************************************************************

		//フォーカス時、0の場合空文字入力
		$("#sysSupplierId").focus(function() {
			if ($("#sysSupplierId").val() == 0 || $("#sysSupplierId").val() == "０") {
				$("#sysSupplierId").val("");
			}
		});

		//フォーカスはずれ時、未入力時、0を消す
		$("#sysSupplierId").blur(function() {
			if ($("#sysSupplierId").val() == 0 || $("#sysSupplierId").val() == "０") {
				$("#sysSupplierId").val("");
			}
		});

		//仕入先検索ボタン
		$("#searchSupplier").click (function () {

			//BigDesimal対策としてカンマ剥奪
			removeCommaList($('.priceTextChek'));

			$("#searchSysSupplierId").val($("#sysSupplierId").val());

			FwGlobal.submitForm(document.forms[0],"/subWinSupplierSearch","supplierSearchWindow","top=130,left=500,width=780px,height=520px,scrollbars=1;");

			addCommaList($('.priceTextChek'));
		});

		//**************** 商品情報 サブウィンドウ検索 *************************************************************************************

		//注文プールより選択ボタン押下時の処理
		$(".selectFromOrderPool").click (function () {

			//BigDesimal対策としてカンマ剥奪
			removeCommaList($('.priceTextChek'));

			$("#searchItemCode").val("");
			$("#searchItemNm").val("");
			$("#searchItemCodeFrom").val("");
			$("#searchItemCodeTo").val("");
			$("#selectSysSupplierId").val("")

			var salesItemArea = $("tr.addItemRow");
			var rowNum = $("tr.addItemRow").index($(this).parents("tr.addItemRow"));

  			$("#searchItemCode").val(salesItemArea.eq(rowNum).find(".addItemCode").val());
// 			if (!$(".searchItemCode").val()) {
// 				$("#searchItemNm").val(salesItemArea.eq(rowNum).find(".addItemNm").val());
// 			}

			//注文プール検索ボタンを押した場所を格納
			$("#openerIdx").val(rowNum);
			$("#searchSysItemId").val($("#sysItemId").val());

			//今何列出ているか格納
			$("#rowCount").val(addItemRowCount);

			//仕入先IDが入力されている場合子画面検索条件に表示
			$("#selectSysSupplierId").val($("#sysSupplierId").val());
// 			$("#selectSysSupplierId").val($("#supplierId").val());
// 			if ($("#supplierId").val()) {
// 				$("#selectSysSupplierId").val($("#supplierId").val());
// 			}

			//品番、もしくは仕入先IDが入力されている判定
			if (!$("#searchItemCode").val() && !$("#selectSysSupplierId").val()) {
				removeCommaList($('.priceTextChek'));
				//検索処理
				FwGlobal.submitForm(document.forms[0], "/subWinInitOrderPoolItemSearch", "orderPoolItemSearchWindow",	"top=0,left=500,width=810px,height=730px;");
				addCommaList($('.priceTextChek'));
			} else {
				removeCommaList($('.priceTextChek'));
				//検索init処理
				FwGlobal.submitForm(document.forms[0], "/subWinOrderPoolItemSearch", "orderPoolItemSearchWindow", "top=0,left=500,width=810px,height=730px;");
				addCommaList($('.priceTextChek'));
			}
		});


		//**************** 追加ボタン *************************************************************************************

		$(".addItem").click(function () {

			if (addItemRowCount == 50) {
				alert("一度に追加できるのは50件までです。");
				return;
			}
			//一行分表示
			var trs = $("tr.addItemRow");
			var tr = trs.eq(addItemRowCount);
			tr.show();

			//削除チェックボックスをチェックはずす
			$(".addDeleteCheck").eq(addItemRowCount).prop("checked", false);

			addItemRowCount++;
		});

		//**************** 仕入先リードタイム、納期設定 *************************************************************************************

		$("#leadTimeButton").click(function () {

			if (!confirm("納期１と納期２に設定します。よろしいですか？")) {
				return;
			}

			//リードタイムから納期に追加する日数の計算
			var deliveryDate1 = Number($("#suppplierLeadTime option:selected").text()) / 2;
			var deliveryDate2 = Number(deliveryDate1) / 2;

			//小数点以下切捨て
			if (deliveryDate1 % 1 != 0.00) {
				deliveryDate1 = Math.floor(deliveryDate1);
			}
			if (deliveryDate2 % 1 != 0.00) {
				deliveryDate2 = Math.floor(deliveryDate2);
			}

			//日付の生成
			var date = new Date($("#orderDate").val());
			var day = date.getDate();

			//注文日から日付の値設定
// 			date.setDate($("#orderDate").val());

			date.setDate(day + deliveryDate1);

			//格納しなおし
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			day = date.getDate();
			var newDay = 0;

			if (month < 10) {
				month = "0" + month;
			}

			if (day < 10) {
				newDay = "0" + day;
			} else {
				newDay = day;
			}

			//納期１にセット
			$("#deliveryDate1").val(year + "/" + month + "/" + newDay);

			//日付の値設定
			date.setDate(day + deliveryDate2);

			//格納しなおし
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			day = date.getDate();

			if (month < 10) {
				month = "0" + month;
			}

			if (day < 10) {
				day = "0" + day;
			}

			//納期２にセット
			$("#deliveryDate2").val(year + "/" + month + "/" + day);

		});
		//**************** 商品情報 レコード削除 全件チェック *************************************************************************************

		$('#allOrderCheck').click(function(){
			if (this.checked) {
				$(".deleteCheckFlg").prop("checked", true);
				$(".addDeleteCheck").prop("checked", true);
			} else {
				$(".deleteCheckFlg").prop("checked", false);
				$(".addDeleteCheck").prop("checked", false);
			}
		  });

		//**************** 一括設定を行う処理 *************************************************************************************

		//入荷日
		$("#arrivalDateAll").change(function() {

			 $(".arrivalDate").each(function () {
				 if(! $(this).prop("disabled") ){
					$(this).val($("#arrivalDateAll").val());
				 }
			 });
		});

		//曖昧入荷日
		$("#vagueArrivalScheduleAll").change(function() {

			 $(".vagueArrivalSchedule").each(function () {
				 if(! $(this).prop("disabled") ){
					 $(this).val($("#vagueArrivalScheduleAll").val());
				 }
			 });
		});

		//**************** 入荷数分入荷 *************************************************************************************

		$(".sceduleDateCompleteButton").click(function () {

			if ($(this).parents("tr").find(".arrivalFlag").val() == "1") {

				alert("もう一度入荷したい場合は更新ボタンを押してください");
				return;
			}

			//入荷数
			var arrivalNum = new Number($(this).parents("tr").find(".arrivalNum").val());
			//注文数
			var orderNum = new Number($(this).parents("tr").find(".detailOrderNum").val());
			//初期表示入荷数
			var tempArrivalNum = new Number($(this).parents("tr").find(".tempArrivalNum").val());

			//未入荷数が注文数を上回ってボタンを押されたらエラー
			if (arrivalNum > orderNum) {

				alert("注文数より入荷数が多くは入荷できません。");
				return false;
			}

			//未入荷数が０でボタンを押されたらアラート
			if (arrivalNum == 0 && tempArrivalNum != 0) {

				alert("入荷数は0で入荷できません。");
				return false;
			}

			if (!confirm("第一倉庫の在庫数を" + arrivalNum + "個増やします。よろしいですか？")) {
				return false;
			}

			$(this).parents("tr").find(".detailStockNum").val(new Number($(this).parents("tr").find(".arrivalNum").val()));


			//入荷フラグを立てる
			$(this).parents("tr").find(".arrivalFlag").val("1");

			//入荷数が減らされた場合
			if (arrivalNum < tempArrivalNum) {

// 				//入荷数
// 				$(this).parents("tr").find(".detailStockNum").val(tempArrivalNum);
				tempArrivalNum -= arrivalNum;
				//未入荷数
				$(this).parents("tr").find(".arrivalNum").val(tempArrivalNum);

//	 			$(this).parents("tr").find(".arrivalScheduleNum").val(arrivalScheduleNum);
				alert(arrivalNum + "個入荷したので、残り入荷数が" + tempArrivalNum + "になります。\r\n" +
						"処理を完了するには更新ボタンを押してください。");
				return;
			}
			alert("注文数全てを入荷処理します。\r\n処理を完了するには更新ボタンを押してください。");
			//入荷数に変更がない場合隠す。
// 			$(this).parents("tr").hide();
		});


		//**************** 一覧に戻るボタン押下時の処理 *************************************************************************************

		$("#returnForeignOrderList").click (function () {

			//BigDesimal対策としてカンマ剥奪
			removeCommaList($('.priceTextChek'));

			goTransaction('returnForeignOrderList.do');
		});

		//**************** 登録 *************************************************************************************

		$('#registry').click (function () {

			if($('#deliveryDate1').val() == "") {
				alert("納期1は必須です。");
				return;
			}

			if($('#deliveryDate2').val() == "") {
				alert("納期2は必須です。");
				return;
			}

			if($('#sysSupplierId').val() == "") {
				alert("仕入先IDは必須です。");
				return;
			}

			if($('#orderStatus').val() == "") {
				alert("ステータスは必須です。");
				return;
			}

			if (!$("#registDate").val()) {
				alert("作成日は必須です。");
				return;
			}

			if (!$("#orderDate").val()) {
				alert("注文日は必須です。");
				return;
			}

			if (!$("#personInCharge").val()) {
				alert("担当者は必須です。");
				return;
			}

			$('#paymentTerms1').val

			//登録時、商品情報必須項目の判定
			var showTr = $("tr.addItemRow");
			var itemCnt = 0;

			for (var i = 0; i < addItemRowCount; i++) {

				var addItemCode = showTr.eq(i).find(".addItemCode").val();
				var addItemNm = showTr.eq(i).find(".addItemNm").val();
				var addOrderNum = new Number(showTr.eq(i).find(".addOrderNum").val());
// 				var addUnitPrice = showTr.eq(i).find(".addUnitPrice").val();
				var addArrivalDate = showTr.eq(i).find(".addArrivalDate").val();

				//品番、入荷日、どちらかが入っていたらtrue
				if (addItemCode != "" || addArrivalDate != "") {

					//品番、入荷日、どちらかが入っていなかったらアラート
					if (addItemCode == "" || addArrivalDate == "") {

						alert('商品欄は、品番、注文数、入荷日が必須項目です。');
						return false;

					//管理品番、商品名、が入っていて数量が無い場合アラート
					} else if (addOrderNum == 0) {

						alert('注文数が0です。');
						return false;
					}
				//管理品番、商品名がなく、数量が入っていたらアラート
				} else if (addOrderNum > 0) {
					alert('商品欄は、品番、注文数、入荷日が必須項目です。');
					return false;
				}

				//品番、仕入金額、入荷日、どれかが入っていたらtrue
// 				if (addItemCode != "" ||  addUnitPrice > 0 || addArrivalDate != "") {

// 					//品番、仕入金額、入荷日、どれかが入っていなかったらアラート
// 					if (addItemCode == "" || addUnitPrice == 0 || addArrivalDate == "") {

// 						alert('商品欄は、品番、注文数、仕入金額、入荷日が必須項目です。');
// 						return false;

// 					//管理品番、商品名、が入っていて数量が無い場合アラート
// 					} else if (addOrderNum == 0) {

// 						alert('注文数が0です。');
// 						return false;
// 					}
// 				//管理品番、商品名がなく、数量が入っていたらアラート
// 				} else if (addOrderNum > 0) {
// 					alert('商品欄は、品番、注文数、仕入金額、入荷日が必須項目です。');
// 					return false;
// 				}
			}

			var paymentTerms1 = Number($('#paymentTerms1').val());
			var paymentTerms2 = Number($('#paymentTerms2').val());

			//支払い条件が入力されていない場合
// 			if (paymentTerms1 + paymentTerms2 != 100) {

// 				if (!confirm('支払い条件が入力されていませんがよろしいですか？')) {
// 					return;
// 				}
// 			} else
			if (!confirm("新規注文書を登録します。よろしいですか？")) {
				return;
			}

			$(".overlay").css("display", "block");
			$(".message").text("登録中");

			//振込理由欄が初期表示だったら削除
			if ($(".transferReason").val() == "1行全角37文字、半角86文字、5行まで") {

				$(".transferReason").val("");
			}

			//登録時非活性解除
			$("#deliveryDate1OverFlag").prop('disabled',false);
			$("#deliveryDate2OverFlag").prop('disabled',false);

			removeCommaList($('.priceTextChek'));
			goTransaction('registryForeignOrder.do');
		});
		
		var updateFlag = 0;
		$('#orderNummAllUpdate').click(function() {
			var resultArea = $('tr.itemRow');
			for (var i = 0; i < resultArea.size(); i++) {
				if (resultArea.eq(i).find("#detilOrderNum").val() != resultArea.eq(i).find(".orderNumHidden").val()) {
 					formatArrivalData(resultArea.eq(i).find(".detailSysItemId").val(),
							resultArea.eq(i).find(".detailSysForeignSlipId").val(),
							resultArea.eq(i).find(".detailSysForeignSlipItemId").val(),
							resultArea.eq(i).find(".detailSysArrivalScheduleId").val(),
							resultArea.eq(i).find("#detilOrderNum").val());
				}
			}
			updateFlag = 1;

		});

		//**************** 更新 *************************************************************************************
		//
		$('#update').click (function () {

			if($('#deliveryDate1').val() == "") {
				alert("納期1は必須です。");
				return;
			}

			if($('#deliveryDate2').val() == "") {
				alert("納期2は必須です。");
				return;
			}

			if($('#sysSupplierId').val() == "") {
				alert("仕入先IDは必須です。");
				return;
			}

			if($('#orderStatus').val() == "") {
				alert("ステータスは必須です。");
				return;
			}

			if (!$("#registDate").val()) {
				alert("作成日は必須です。");
				return;
			}

			if (!$("#orderDate").val()) {
				alert("注文日は必須です。");
				return;
			}

			if (!$("#personInCharge").val()) {
				alert("担当者は必須です。");
				return;
			}

			//登録済み分、商品情報必須項目の判定
			var showTr = $("tr.itemRow");

			for (var i = 0; i < showTr.size(); i++) {

				var arrivalDate = showTr.eq(i).find(".detailArrivalDate").val();
				var arrivalNum = Number(showTr.eq(i).find(".arrivalNum").val());
				var tempArrivalNum = Number(showTr.eq(i).find(".tempArrivalNum").val());
				var arrivalFlag = showTr.eq(i).find(".arrivalFlag").val();
				var itemCode = showTr.eq(i).find(".hiddenItemCode").val();

				//未入荷数が注文数を上回ってボタンを押されたらエラー
				if (arrivalNum > tempArrivalNum) {

					alert("注文数より入荷数が多くは入荷できません。");
					return false;
				}

				//未入荷数がいじられているのに入荷数分入荷ボタンを押していなかったらエラー
				if (arrivalNum != tempArrivalNum && arrivalFlag == "0") {

					alert("品番「" + itemCode + "」が、未入荷数を変更しているのに入荷数分入荷ボタンが押されていません。");
					return false;
				}

				//入荷日判定
				if (arrivalDate == "") {

					alert("入荷日は必須項目です。");
					return false;
				}
			}

			//追加分、商品情報必須項目の判定
			var addShowTr = $("tr.addItemRow");

			for (var i = 0; i < addItemRowCount; i++) {

				var addItemCode = addShowTr.eq(i).find(".addItemCode").val();
				var addItemNm = addShowTr.eq(i).find(".addItemNm").val();
				var addOrderNum = new Number(addShowTr.eq(i).find(".addOrderNum").val());
// 				var addUnitPrice = addShowTr.eq(i).find(".addUnitPrice").val();
				var addArrivalDate = addShowTr.eq(i).find(".addArrivalDate").val();

				//品番、入荷日どちらかが入っていたらtrue
				if (addItemCode != "" || addArrivalDate != "") {

					//品番、入荷日、どちらかが入っていなかったらアラート
					if (addItemCode == "" || addArrivalDate == "") {

						alert('商品は品番、注文数、入荷日が必須項目です。');
						return false;

					//管理品番、商品名、が入っていて数量が無い場合アラート
					} else if (addOrderNum == 0) {

						alert('注文数が0です。');
						return false;
					}
				//管理品番、商品名がなく、数量が入っていたらアラート
				} else if (addOrderNum > 0) {
					alert('商品は品番、注文数、入荷日が必須項目です。');
					return false;
				}
			}

			var paymentTerms1 = Number($('#paymentTerms1').val());
			var paymentTerms2 = Number($('#paymentTerms2').val());

			//支払い条件が入力されていない場合
// 			if (paymentTerms1 + paymentTerms2 != 100) {

// 				if (!confirm('支払い条件が入力されていませんがよろしいですか？')) {
// 					return;
// 				}
// 			} else
			if (!confirm("注文書を更新します。よろしいですか？")) {
				return;
			}

			$(".overlay").css("display", "block");
			$(".message").text("更新中");
			//登録の際、カンマをはずす：追加用
			for (var i = 0; i < $(".addItemRow").size(); i++) {

				var unitPrice = $(".addUnitPrice").eq(i).val();
				$(".addUnitPrice").eq(i).val(removeComma(unitPrice));
			}

			//振込理由欄が初期表示だったら削除
			if ($(".transferReason").val() == "1行全角37文字、半角86文字、5行まで") {
				$(".transferReason").val("");
			}

			//更新時非活性解除
			$("#deliveryDate1OverFlag").prop('disabled',false);
			$("#deliveryDate2OverFlag").prop('disabled',false);

			//クラス指定でカンマ剥奪
			removeCommaList($('.priceTextChek'));

			goTransaction('updateForeignOrder.do');

		});

		//******************* PDF出力  **********************************************************************************

		//注文書印刷ボタン押下時の処理
		$("#printOrderSheet").click (function () {

			if (!confirm("表示されているページで注文書を出力します。\nよろしいですか？")) {
				return;
			}
// 				注文書印刷処理をここに記述する
			$(".overlay").css("display", "block");
			$.ajax({
				url : "./exportForeignOrderAcceptanceList.do",
				type : 'POST',

				success : function(data, text_status, xhr) {
					$(".overlay").css("display", "none");
					window.open('foreignOrderAcceptancePrintOutFile.do', '_new');
				},
				error : function(data, text_status, xhr) {
					$(".overlay").css("display", "none");
					alert("pdfファイルの作成に失敗しました。");
				}
			});
		});

		//振込依頼書(1)ボタン押下時の処理
		$("#transferRequestForm1").click (function () {

// 				振込依頼書(1)作成処理をここに記述する
			if (!confirm("表示されているページで振込書を出力します。\nよろしいですか？")) {
				return;
			}
// 				注文書印刷処理をここに記述する
			$(".overlay").css("display", "block");
			$.ajax({
				url : "./exportForeignOrderTransferFst.do",
				type : 'POST',

				success : function(data, text_status, xhr) {
					$(".overlay").css("display", "none");
					window.open('foreignOrderTransferPrintOutFileFst.do', '_new');
				},
				error : function(data, text_status, xhr) {
					$(".overlay").css("display", "none");
					alert("pdfファイルの作成に失敗しました。");
				}
			});
		});

		//振込依頼書(2)ボタン押下時の処理
		$("#transferRequestForm2").click (function () {

// 				振込依頼書作成処理をここに記述する
			if (!confirm("表示されているページで振込書を出力します。\nよろしいですか？")) {
				return;
			}
// 				注文書印刷処理をここに記述する
			$(".overlay").css("display", "block");
			$.ajax({
				url : "./exportForeignOrderTransferSnd.do",
				type : 'POST',
				success : function(data, text_status, xhr) {
					$(".overlay").css("display", "none");
					window.open('foreignOrderTransferPrintOutFileSnd.do', '_new');
				},
				error : function(data, text_status, xhr) {
					$(".overlay").css("display", "none");
					alert("pdfファイルの作成に失敗しました。");
				}
			});
		});

		//**************** 注文管理書削除 *************************************************************************************

		$("#delete").click(function () {

			//メッセージフラグを切る
			$("#formMessageFlg").val(0);

			//クラス指定でカンマ剥奪
			removeCommaList($('.priceTextChek'));

			if (confirm("伝票を削除します。よろしいですか？")) {
				goTransaction('deleteForeignOrder.do');
			}
			return;
		});


		//**************** リセットボタン *************************************************************************************

		$("#clear").click (function () {

			if (!confirm("入力したものをリセットします。よろしいですか？")) {
				return;
			}
			clearInfo();
		});

		function clearInfo() {

			$("#clearArea input, #clearArea select, #clearArea textarea, .addItemRow input, .addItemRow select").each(function(){
				if (this.type == "checkbox" || this.type == "radio") {
					this.checked = false;
				} else {
				    $(this).val("");
				}
			});

			//商品情報
			$("#arrivalDateAll").val("");
			$("#vagueArrivalScheduleAll").val("");
			//仕入先情報リセット
			$("#supplierNo").text("");
			$("#sysSupplierId").prop('disabled',false);
			$("#companyFactoryNm").text("");
			$("#country").text("");
			$("#address").text("");
			$("#tel").text("");
			$("#fax").text("");
			$("#contactPersonNm").text("");
			$("#mailAddress").text("");
			$("#tradeTerms").text("");
			$("#bankNm").text("");
			$("#rate").text("");
			$("#currencyNm").text("");
			$("#branchNm").text("");
			$("#bankAddress").text("");
			$("#swiftCode").text("");
			$("#accountNo").text("");
			$("#supplierRemarks").text("");
			//注文書情報リセット
			$("#invoiceNo").text("");
			$("#orderStatus").val("");
			$("#correctionFlag").prop("checked", false);
			$("#deliveryDate1OverFlag").prop("checked", true);
			$("#deliveryDate2OverFlag").prop("checked", true);
			$("#deliveryDate1OverFlag").prop('disabled',false);
			$("#deliveryDate2OverFlag").prop('disabled',true);
			$("#registDate").val("");
			$("#orderDate").val("");
			$("#deliveryDate1").val("");
			$("#deliveryDate2").val("");
			$("#paymentDate1").val("");
			$("#paymentDate2").val("");
			$("#arrivalScheduleDate").val("");
			$("#addVagueArrivalSchedule").val("");
			//金額情報欄
			$(".subTotal").val(0);
			$(".otherExpenses").val(0);
			$(".discount").val(0)
			$(".total").val(0);
			$('.payment1').val(0);
			$('.payment2').val(0);
		}

		//**************** 商品情報テーブルのレコード削除 *************************************************************************************

		var j= 0;
		$(".removeItem").click(function () {

			if ($(".addDeleteCheck:checked").length == 0
					&& $(".deleteCheckFlg:checked").length == 0) {
				alert("対象の情報がありません。");
				return false;
			}

			//追加用商品テーブル変数
			var itemArea = $("tr.addItemRow");
			var checkCount = 0;

			//表示分
// 			$("tr.addItemRow:visible").length;

			//追加50行分のチェックされた行を削除する処理
			for (var i = 0; i <= itemArea.size(); i++) {

				//チェックボックスにチェックされているか判定
				if ($(".addDeleteCheck").eq(checkCount).prop("checked")) {

					addItemRowCount--;
					//削除してずらす
					for (j = checkCount; j < itemArea.size(); j++) {

						//最終行の判定
						if (j == addItemRowCount) {

							clearAddItemLastRow(itemArea.eq(addItemRowCount));
							itemArea.eq(j).hide();
						} else  if (j < addItemRowCount) {

							var copyFromTr = itemArea.eq(j + 1);
							var copyToTr = itemArea.eq(j);
							copyItem(copyFromTr, copyToTr);
						}
					}
				} else {
					//次の商品へ
					checkCount++;
				}
			}

			//一括削除した際の対応策
			if (addItemRowCount <= 0) {
				addItemRowCount = 0;
			}

			//表示している追加行が１行の場合、
			if (addItemRowCount == 0) {

				//詳細画面時は追加レコード一行を残さないため
				if ($("#sysForeignSlipId").val() == 0) {
					//１行だけ表示処理
					var tr = itemArea.eq(addItemRowCount);
					tr.show();

					addItemRowCount++;
				}
			}

			var detailItemArea = $("tr.itemRow");

			//表示分のチェックされた行を削除する処理
			for (var i = 0; i < detailItemArea.size(); i++) {
				if ($(".deleteCheckFlg:checked").eq(i).prop("checked")) {

					if (confirm("選択された商品を削除します。よろしいですか？")) {

						//BigDesimal対策としてカンマ剥奪
						removeCommaList($('.priceTextChek'));

						goTransaction("deleteForeignOrderItem.do");

						addCommaList($('.priceTextChek'));
						return false;
					};
				};
			}
			return false;
		});

		//最終行の項目を空にする処理
		function clearAddItemLastRow(addItemRow) {
			addItemRow.find(".addDeleteCheck").prop("checked", false);
			addItemRow.find(".addItemCode").val("");
			addItemRow.find(".addFactoryItemCode").val("");
			addItemRow.find(".addItemNm").val("");
			addItemRow.find(".addForeignItemNm").val("");
			addItemRow.find(".addArrivalNum").val(0);
			addItemRow.find(".addOrderNum").val(0);
			addItemRow.find(".addUnitPrice").val(0);
			addItemRow.find(".addArrivalDate").val("");
		}

		//商品情報を削除対象の後の行からコピーする処理
		function copyItem(copyFromTr, copyToTr) {

			copyToTr.find(".addDeleteCheck").prop("checked", $(".addDeleteCheck").eq(j + 1).prop("checked"));
			copyToTr.find(".addItemCode").val(copyFromTr.find(".addItemCode").val());
			copyToTr.find(".addFactoryItemCode").val(copyFromTr.find(".addFactoryItemCode").val());
			copyToTr.find(".addItemNm").val(copyFromTr.find(".addItemNm").val());
			copyToTr.find(".addForeignItemNm").val(copyFromTr.find(".addForeignItemNm").val());
			copyToTr.find(".addArrivalNum").val(copyFromTr.find(".addArrivalNum").val());
			copyToTr.find(".addOrderNum").val(copyFromTr.find(".addOrderNum").val());
			copyToTr.find(".addUnitPrice").val(copyFromTr.find(".addUnitPrice").val());
			copyToTr.find(".addArrivalDate").val(copyFromTr.find(".addArrivalDate").val());

			copyFromTr.find(".addDeleteCheck").prop('checked', false);
			copyFromTr.find(".addItemCode").val("");
			copyFromTr.find(".addFactoryItemCode").val("");
			copyFromTr.find(".addItemNm").val("");
			copyFromTr.find(".addForeignItemNm").val("");
			copyFromTr.find(".addArrivalNum").val(0);
			copyFromTr.find(".addOrderNum").val(0);
			copyFromTr.find(".addUnitPrice").val(0);
			copyFromTr.find(".addArrivalDate").val("");
		}
		
		function formatArrivalData(sys_item_id, sys_foreign_slip_id, sys_foreign_slip_item_id, sys_arrival_schedule_id, order_num) {
			$(".overlay").css("display", "block");
  			$.ajax({
				url : './resetForeignOrderItem.do',
				type : 'POST',
				dataType : 'json',
				data : {
					'sys_item_id' : sys_item_id,
					'sys_foreign_slip_id' : sys_foreign_slip_id,
					'sys_foreign_slip_item_id' : sys_foreign_slip_item_id,
					'sys_arrival_schedule_id' : sys_arrival_schedule_id,
					'order_num' : order_num,
				},
				success : function(data, text_status, xhr) {
					if(updateFlag == 1) {
						setTimeout(
								  function() 
								  {
										updateFlag = 0;
										location.reload();
								  }, 2000);
					}
				},
				error : function(data, text_status, xhr) {
					$(".overlay").css("display", "none");
				}
			});
		}

		//**************** カンマ制御 ************************************************************************************

	//仕入金額入力制御
	$(".priceTextChek").blur (function () {

		var commaCheck = $(this).parents("tr").find(".priceTextChek").val();

		//入力制御
		if (commaCheck.match(/[^0-9.,-]+/ )) {

			alert("半角数字と小数点以外は入力できません。");
			$(this).parents("tr").find(".priceTextChek").val(0);
			return false;

		} else if (commaCheck >= 10000000) {

			alert("金額は整数部7桁までです。");
			$(this).parents("tr").find(".priceTextChek").val(0);
			return false;
		}

		if (commaCheck == "") {
			$(this).parents("tr").find(".priceTextChek").val(0);
		} else {

			//誤差が生じるため、小数点第四位以降を切り捨て
			commaCheck = Math.floor(commaCheck * 1000) / 10;
			//小数点第3位で切り上げ
			$(this).parents("tr").find(".priceTextChek").val(Math.ceil(commaCheck) / 100);

			//カンマ付与
			var commaCheck = $(this).parents("tr").find(".priceTextChek")[0];
			addComma(commaCheck);
		}

		//NaN対策
		if ($(this).parents("tr").find(".priceTextChek").val().match(/[^0-9.,-]+/ )) {

			alert("無効な数字が入力されました。");
			$(this).parents("tr").find(".priceTextChek").val(0);
			return false;
		}

		return true;
	});

	$(".priceTextChek").focus (function () {

		var commaCheck = $(this).parents("tr").find(".priceTextChek").val();
		if (commaCheck == 0.0) {
			$(this).parents("tr").find(".priceTextChek").val("");
		} else {
			$(this).parents("tr").find(".priceTextChek").val(removeComma($(this).parents("tr").find(".priceTextChek").val()))
		}
		return true;
	});



	//**************** ************************************************************************************

		function slipCalc() {
			removeCommalist($(".priceTextMinus"));
			var subTotal;
			var otherExpenses;
			var total;
		}
		//**************** 新規登録画面 *************************************************************************************

		$('#initRegistryForeignOrder').click (function () {

			//メッセージフラグを切る
			$("#formMessageFlg").val(0);

			//BigDesimal対策としてカンマ剥奪
			removeCommaList($('.priceTextChek'));

			goTransaction('initRegistryForeignOrder.do');
		});

	//**************** 納期1超過フラグが解除されていない場合、納期2超過フラグは非活性  *************************************************************************************
		$("#deliveryDate1OverFlag").change(function() {

			if ($("#deliveryDate1OverFlag").prop("checked")) {

				$("#deliveryDate2OverFlag").prop('disabled',true);
			} else {

				$("#deliveryDate2OverFlag").prop('disabled',false);
			}
		});

		$("#deliveryDate2OverFlag").change(function() {

			if ($("#deliveryDate2OverFlag").prop("checked")) {

				$("#deliveryDate1OverFlag").prop('disabled',false);
			} else {

				$("#deliveryDate1OverFlag").prop('disabled',true);
			}
		});

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
	});

	//**************** 商品検索後、仕入情報検索 *************************************************************************************

	function supplierSearch() {

		//BigDesimal対策としてカンマ剥奪
		removeCommaList($('.priceTextChek'));

		$("#searchSysSupplierId").val($("#sysSupplierId").val());

		FwGlobal.submitForm(document.forms[0],"/subWinSupplierSearch","supplierSearchWindow","top=130,left=500,width=780px,height=520px,scrollbars=1;");

		addCommaList($('.priceTextChek'));
	};

	//**************** 子画面用追加ボタン *************************************************************************************

	function addItemRowOpen() {

			//一行分表示
			var trs = $("tr.addItemRow");
			var tr = trs.eq(addItemRowCount);
			tr.show();

			//削除チェックボックスをチェックはずす
			$(".addDeleteCheck").eq(addItemRowCount).prop("checked", false);

 			addItemRowCount++;
	}

	//**************** 画面上計算、誤差修正メソッド *************************************************************************************

	function decimalPoint(num) {

		//誤差が生じるため、小数点第四位以降を切り捨て
		num = Math.floor(num * 1000) / 10;
		//小数点第3位で切り上げ
		num = Math.ceil(num) / 100;

		return num;
	}

	</script>
</html:html>