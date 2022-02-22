<?xml version="1.0" encoding="UTF-8""?>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<head>
	<jsp:include page="/WEB-INF/page/define/define-meta.jsp" />
	<link rel="stylesheet" href="./css/lumpArrival.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.min.css" type="text/css" />
	<link rel="stylesheet" href="./css/jquery-ui-1.10.4.custom.css" type="text/css" />
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>
	<script src="./js/jquery.ui.datepicker.min.js"></script>
	<script src="./js/jquery.ui.datepicker-ja.min.js"></script>

<!--
【一括入荷画面】
ファイル名：lumpArrival.jsp
作成日：2014/12/19
作成者：八鍬寛之

（画面概要）

・在庫一覧で検索された商品の入荷予定情報を表示し、
一括で在庫に反映することができる
・反映する倉庫と数は変更可能。もし全ての入荷数を反映させなかった場合は
そこで登録した分だけ入荷履歴に入り、残数はそのまま入荷予定データとして残る

・一括入荷ボタン押下：入力されている倉庫に入荷数分在庫を増やす。

（注意・補足）

-->

	<script type="text/javascript">
	$(function() {
		$('.num').spinner( {
			max: 9999,
			min: 0,
			step: 1
		});

		var alertType = $("#alertType").val();
		if (alertType != null && alertType == "4") {
			alert("入荷しました。");
		}
		$("#alertType").val(0);

		$("#lumpArrival").click (function () {

			if (!confirm("在庫に反映させます。よろしいですか？")) {
				return;
			}

			goTransaction("lumpArrival.do");
		});
	});

	</script>
	</head>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<html:form action="lumpArrival">

	<html:hidden property="alertType" styleId="alertType" />

	<h4 class="heading">一括入荷画面</h4>

	<div id="list_area">
		<table id="list_table">
			<tr>
				<th class="index"></th>
				<th class="code">品番</th>
				<th class="itemNm">商品名</th>
				<th class="warehouse">倉庫</th>
				<th class="orderDate">発注日</th>
				<th class="scheduleDate">入荷予定日</th>
				<th class="scheduleNum">入荷予定数</th>
				<th class="updateNum">反映数</th>
			</tr>

			<nested:iterate property="lumpArrivalList" indexId="idx">
			<tr>
				<nested:hidden property="sysItemId"/>
				<td class="w20"><%= idx + 1 %></td>
				<td><nested:write property="itemCode" /></td>
				<td><nested:write property="itemNm" /></td>
				<td><nested:select property="sysWarehouseId">
						<html:optionsCollection property="warehouseList" label="warehouseNm" value="sysWarehouseId"/>
					</nested:select>
				</td>
				<td><nested:write property="itemOrderDate"/></td>
				<td><nested:write property="arrivalScheduleDate"/></td>
				<td><nested:write property="arrivalScheduleNum"/></td>
				<td><nested:text property="arrivalNum" styleClass="num numText" maxlength="4" /></td>
			</tr>
			</nested:iterate>
		</table>
	</div>

	<div class="paging_area">
<!-- 		<div class="paging_total"> -->
<!-- 				<h3>全100件（1/8ページ）</h3> -->
<!-- 		</div> -->
<!-- 		<div class="paging_num"> -->
<!-- 			<ul class="pager fr mb10"> -->
<!-- 			    <li class="disabled"><a href="#">&laquo;</a></li> -->
<!-- 			    <li class="current"><span>1</span></li> -->
<!-- 			    <li><a href="javascript:void(0);" >2</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" >3</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" >4</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" >5</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" >6</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" >7</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" >8</a></li> -->
<!-- 			    <li><a href="javascript:void(0);" >&raquo;</a></li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
		<div class="button_area">
			<a class="button_main" href="Javascript:void(0);" id="lumpArrival">一括入荷</a>
		</div>
	</div>


	</html:form>
</html:html>