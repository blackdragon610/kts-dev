/****************************************************************
ファイル名	：progressbar.js
概要		：プログレスバーの表示を行う
作成		：2006.1.20 岡本

実装方法

＜検索実行側＞
・<head></head>内に<script type="text/javascript" src="common.js"></script>を追加
・検索実行時のボタン処理内に showProgressWindow();を追加
（window.openよりも前に記述）
・bodyタグに onbeforeunload="closeProgressWindow();"を追加
（親画面が切り替わった場合に画面を閉じるため）

＜検索結果画面側＞
・bodyタグのonload=parent.closeProgressWindow();を追加
（既に処理がある場合は処理前に挿入）
*****************************************************************/

//グローバル変数を定義
var g_iProgressBarCount = 0;
var g_oInterval			= "";
var g_iProgresspixles	= -18;
var g_oInterval			= "";
var g_iProgressSpeed	= 60;//Intervalの間隔。数を減らすほど早くなる
var g_sProgressBar
var g_iProgressCount

/****************************************************************
関数名	：fnProgressBar
処理内容：プログレスバー表示の実態。親ウィンドウの存在チェック。
*****************************************************************/
function fnProgressBar(){

	if(g_iProgressBarCount == g_iProgressCount) {
		g_iProgressBarCount = 0;
		g_iProgresspixles	= -18;
	}

	$("fileprogress").innerHTML			= g_sProgressBar;
	$("OuterProgress").style.marginLeft = g_iProgresspixles;
	$("OuterProgress").style.height		= '20px';

	g_iProgressBarCount += 1;
	g_iProgresspixles	+= 10
	if (fnExistOpener()==false){
		//alert("閉じます");
		window.clearInterval(g_oInterval);
		window.close();
	}
}

/****************************************************************
関数名	：fnSearching
処理内容：プログレスバーの開始
*****************************************************************/
function fnSearching(){
	g_iProgressBarCount	= 0;
	g_iProgresspixles	= -45;
	g_iProgressCount	= 25;	//回数
	//左が薄い、右が濃い
	g_sProgressBar = "<span id='OuterProgress' name='OuterProgress'><span class=progreessbarlightest></span><span class=progreessbarlight></span><span class=progreessbar></span><span class=progreessbar></span><span class=progreessbar></span><span class=progreessbar></span><span class=progreessbar></span></span>";
	//左が濃い、右が薄い
	//g_sProgressBar = "<span id='OuterProgress' name='OuterProgress'><span class=progreessbar></span><span class=progreessbar></span><span class=progreessbar></span><span class=progreessbar></span><span class=progreessbar></span><span class=progreessbarlight></span><span class=progreessbarlightest></span></span>";
	g_oInterval = window.setInterval(fnProgressBar, g_iProgressSpeed);
}

/****************************************************************
関数名	：$
処理内容：引数のid要素をもつオブジェクトを返す
*****************************************************************/
function $(id){
	return document.getElementById(id);
}

/****************************************************************
関数名	：fnExistOpener
処理内容：親ウィンドウが無い場合はfalseを返す
*****************************************************************/
function fnExistOpener(){
    var ow = window.opener;
    //$("under_msg").innerText = ow.closed;
    if (ow.closed) return false;
	return true;
}
