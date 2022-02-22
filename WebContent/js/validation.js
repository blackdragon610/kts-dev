
$(document).ready (function () {

	//金額
	$(".priceText").blur(function () {

		if (!checkNum(this)){
			$(this).val('');
			return;
		}
		change0(this);
		addComma(this);
	});

	//金額
	$(".priceTextWrongMes").blur(function () {

		if (!checkNumWrongMes(this)){
			$(this).val('');
			return;
		}
		change0(this);
		addComma(this);
	});

	//金額(マイナスOK)
	$(".priceTextMinus").blur(function () {

		if (!checkPulasMinusNum(this)){
			$(this).val('');
			return;
		}
		change0(this);
		addComma(this);
	});

	//金額(マイナスOK)検索用
	$(".priceTextMinusSearch").blur(function () {

		if (!checkPulasMinusNumSearch(this)){
			$(this).val('');
			return;
		}
		//change0(this);
		//addComma(this);
	});

	//半角英数字
	$(".alphanumeric").blur(function () {

		if (!checkAlphanumeric(this)){
			$(this).val('');
			return;
		}
	});

	////半角英数字ハイフン、スペース、アンダーバー、スラッシュOK
	$(".checkAlnumHyp").blur(function () {

		if (!checkAlnumHyp(this)){
			$(this).val('');
			return;
		}
	});

	//半角英数字:ハイフン,ピリオドOK
	$(".checkAlnumHypPeri").blur(function () {

		if (!checkAlnumHypPeri(this)){
			$(this).val('');
			return;
		}
	});


	//半角英数字:ハイフン,ピリオド,スペースOK
	$('.factoryItemCode').blur(function () {
		if (!checkAlnumHypPeriSpe(this)){
			$(this).val('');
			return;
		}
	})

	//日付書式
	$(".calender").blur(function () {

		if (changeDate(this)){
			$(this).val('');
			return;
		}
	});


	// 日付書式(年月)
	$(".ymCalendar").blur(function () {
		if (changeDateYM(this)){
			$(this).val('');
			return;
		}
	});

	//半角数字
	$(".numText").blur(function () {

		if (!numberCheckCormal(this)){
			$(this).val('');
			return;
		}
	});

	//半角数字(マイナスOK)
	$(".numTextMinus").blur(function () {

		if (!numberCheckCormalMinus(this)){
			$(this).val('');
			return;
		}
	});

	//半角数字&品番FromTo11桁チェック
	$(".numLengthCheck").blur(function () {

		if (!numLengthCheck(this, 11)){
//			$(this).val('');
			return;
		}
	});
});

function removeCommaGoTransaction(actionPath) {
//	undisabled();
	removeCommaPriceText();
	goTransaction(actionPath);
}
function removeCommaGoTransactionNew(actionPath) {
	undisabled();
	removeCommaPriceText();
	goTransactionNew(actionPath);
}

function removeCommaPriceText() {

//	var priceTextList = $(".priceText");
//	var priceText = "";

	$(".priceText").each (function (i) {

//		priceText = priceTextList.eq(i);
//		priceText.val(removeComma(priceText.val()));
		$(this).val(removeComma($(this).val()));
		i++;
	});
}

function removeCommaList(obj) {

	obj.each (function () {

		$(this).val(removeComma($(this).val()));
	});
}

function addCommaList(obj) {
	obj.each (function () {

		addComma(this);
	});
}
