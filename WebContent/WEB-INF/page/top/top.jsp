<!DOCTYPE html>
<html:html>
<head>
	<link rel='stylesheet' type='text/css' href='./css/menu.css' />
	<link rel="stylesheet" href="./css/common.css" type="text/css" />
	<link rel="stylesheet" href="./css/notice.css" type="text/css" />
	<script src="./js/common.js" type="text/javascript"></script>
	<script src="./js/jquery-1.10.2.min.js" language="javascript"></script>
	<script src="./js/jquery-ui-1.10.4.custom.min.js" language="javascript"></script>
	<script src="./js/validation.js" type="text/javascript"></script>

	<script src="./js/jquery.ui.core.min.js"></script>

<!--
【TOP画面】
ファイル名：top.jsp
作成日：2014/12/10
作成者：八鍬寛之

（画面概要）

ログイン時、またはメニューバーの「TOP」リンクを押下時に遷移する。
掲示板機能を有する。

・「新規で投稿する」ボタン押下：テキストエリアの内容を投稿する。
・投稿No右ラジオボタン押下：その内容が下部のテキストエリアに入る。
・「内容を更新する」ボタン押下：テキストエリアの内容を更新する。
・「クリア」ボタン押下：ラジオボタンの選択をクリアする。
・「削除」ボタン押下：ラジオボタンをチェックしたNoの投稿を削除する。

ラジオボタンを選択して「新規で投稿する」ボタンを押した場合は参照登録のようなこともできる。

（注意・補足）

-->

	<script type="text/javascript">

	//EnterキーSubmit制御処理
	window.onload = function(){
		$("input:not(.allow_submit)").on("keypress", function(){
			return event.which !== 13;
		});
	};

	$(function(){
		//掲示板記事登録
		$(".registryNotice").click(function(){
			if($(".newNotice").val() == '') {
				alert("内容を入力してください。");
				return;
			}
			removeCommaGoTransaction("registryNotice.do");
		});

		//ラジオボタンチェック時投稿内容を取得
		$(".noticeRadio").click(function () {
			var noticeText = $(this).parents(".notice_td").find(".notice").val();
			$(".notice_text").val(noticeText);
			noticeText = "";

			$(this).val($(this).parents(".notice_td").find(".noticeSystemId").val());
		});

		//記事更新
		$(".updateNotice").click(function(){
			if(!$("input:radio[name='noticeSystemId']:checked").val()) {
				alert("編集したい投稿No右のラジオボタンをチェックしてください。");
				return;
			}

			if($(".newNotice").val() == '') {
				alert("内容を入力してください。");
				return;
			}
			removeCommaGoTransaction("updateNotice.do");
		});

		//記事クリア(ラジオボタンのチェックを外す)
		$(".clearNotice").click(function(){
			$(".notice_text").val("");
			$(".noticeRadio").attr("checked", false);
		});

		//記事削除
		$(".deleteNotice").click(function(){
			if(!$("input:radio[name='noticeSystemId']:checked").val()) {
				alert("削除したい投稿No右のラジオボタンをチェックしてください。");
				return;
			}

			if(!confirm("削除すると元に戻せません。\n削除しますか？")) {
				return;
			}
			removeCommaGoTransaction("deleteNotice.do");
		});

	});
	</script>


<!-- 	<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script> -->
</head>
<body>
	<jsp:include page="/WEB-INF/page/common/menu.jsp" />
	<nested:form action="/login" enctype="multipart/form-data">
		<div>
		<fieldset class="notice_board">
			<table>
				<tr>
					<td>
						<label class="board_label">α-BOARD</label>
					</td>
				</tr>
			</table>
			<div class="notice_div">
			<div class="notice_inner_div">
			<TABLE>
				<TR>
					<TD>
						<logic:notEmpty name="loginForm" property="noticeList">
							<nested:iterate property="noticeList" indexId="idx">
								<table style="margin-bottom: 20px; border-bottom: solid 1px; width: 1067px;">
									<tr>
										<td class="notice_td">
											<label>
												<nested:write property="noticeSystemId"/>
												<html:radio property="noticeSystemId" value="" name="noticeList" styleClass="noticeRadio" />
												<nested:hidden property="noticeSystemId" styleClass="noticeSystemId" />
											</label>
											<pre class="pre_tag"><nested:write property="notice" /><nested:hidden property="notice" styleClass="notice" /></pre>
										</td>
										<td>
											<label>　　投稿日：</label>
											<nested:write property="createDate" format="yyyy/MM/dd HH:mm:ss"/>
											<nested:write property="createUserFamilyName"/>
											<nested:write property="createUserFirstName"/><br/>
											<label>　　更新日：</label>
											<nested:write property="updateDate" format="yyyy/MM/dd HH:mm:ss"/>
											<nested:write property="updateUserFamilyName"/>
											<nested:write property="updateUserFirstName"/>
										</td>
									</tr>
								</table>
							</nested:iterate>
							</logic:notEmpty>
							<logic:empty name="loginForm" property="noticeList">
							<label>投稿がありません。</label>
							</logic:empty>
						</TD>
					</TR>
				</TABLE>
				</div>
				</div>
				<table class="text_table">
					<tr>
						<td>
							<html:textarea rows="5" styleClass="newNotice notice_text" property="noticeDetail"></html:textarea>
						</td>
						<td class="pdg_right_20px">
							<a href="javascript:void(0);" style="margin-bottom: 20px" class="button_main registryNotice" >新規で投稿する</a><br/>
							<a href="javascript:void(0);" class="button_main updateNotice" >内容を更新する</a>
						</td>
						<td class="td_center">
							<a href="javascript:void(0);" style="margin-bottom: 20px" class="button_white clearNotice" >クリア</a><br/>
							<a href="javascript:void(0);" class="button_return deleteNotice" >削除</a>
						</td>
					</tr>

				</table>
			</fieldset>
		</div>
	</nested:form>
</body>
</html:html>