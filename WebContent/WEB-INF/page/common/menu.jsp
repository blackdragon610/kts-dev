<link rel='stylesheet' type='text/css' href='./css/menu.css' />

<div id='cssmenu'>
	<ul>
		<li><a href='initNotice.do'><span>TOP</span></a></li>
		<li class='has-sub'><a href='#'><span>助ネコインポート</span></a>
			<ul>
				<li><html:link href="initCsvImport.do" title="受注データ取込"><span>受注データ取込</span></html:link></li>
				<li><html:link href="initDeliveryCsvImport.do" title="配送データ取込"><span>配送データ取込</span></html:link></li>
				<li><html:link href="initDomesticCsvImport.do" title="国内注文データ取込"><span>国内注文データ取込</span></html:link></li>
				<li><html:link href="initDomesticOrderStockCsvImport.do" title="国内注文入荷予定日取込"><span>国内注文入荷予定日取込</span></html:link></li>
			</ul>
		</li>
		<li class='has-sub'><a href='#'><span>業販管理</span></a>
			<ul>
				<li><html:link href="initCorporateSaleList.do" title="業販一覧"><span>業販一覧</span></html:link></li>
				<li><html:link href="initExportCorporateBill.do" title="請求書出力"><span>請求書出力</span></html:link></li>
				<li><html:link href="initCorporatePaymentManagement.do" title="入金管理"><span>入金管理</span></html:link></li>
				<li><html:link href="initCorporateSaleSummary.do" title="業販売上統計"><span>業販売上統計</span></html:link></li>
			</ul>
		</li>
		<li class='has-sub'><a href='#'><span>売上管理</span></a>
			<ul>
				<li><html:link href="initSaleList.do" title="売上一覧"><span>売上一覧</span></html:link></li>
				<li><html:link href="initSaleSummary.do" title="売上統計"><span>売上統計</span></html:link></li>
				<li><html:link href="initRegistrySales.do" title="新規登録"><span>新規登録</span></html:link></li>
			</ul>
		</li>
		<li class='has-sub'><html:link href="initExcelImport.do" title="商品データインポート"><span>商品データインポート</span></html:link></li>
		<li class='has-sub'><a href='#'><span>在庫管理</span></a>
			<ul>
				<li><html:link href="initItemList.do" title="在庫一覧"><span>在庫一覧</span></html:link></li>
<%-- 				<li><html:link href="initScheduledLumpArrivalRegistry.do" title="一括入荷予定登録"><span>一括入荷予定登録</span></html:link></li> --%>
				<li><html:link href="initRegistryItem.do" title="新規登録"><span>新規登録</span></html:link></li>
				<li><html:link href="initKeepCsvImport.do" title="キープ取込"><span>キープ取込</span></html:link></li>
				<li><html:link href="initRemoveKeepCsvImport.do" title="キープ取消"><span>キープ取消</span></html:link></li>
			</ul>
		</li>
		<!--  2015/12/15 ooyama ADD START 法人間請求書機能対応  -->
		<logic:equal name="LOGIN_USER_BTOB_BILL_AUTH" value="1">
			<li class='has-sub'><a href='#'><span>法人間請求書管理</span></a>
				<ul>
<%-- 					<li><html:link href="initKindCostList.do" title="Kind原価一覧"><span>Kind原価一覧</span></html:link></li> --%>
					<li><html:link href="initSaleCostList.do" title="売上原価一覧"><span>売上原価入力</span></html:link></li>
					<li><html:link href="initCorporateSaleCostList.do" title="業販原価一覧"><span>業販原価入力</span></html:link></li>
					<li><html:link href="initBtobBillList.do" title="法人間請求書管理"><span>法人間請求書管理</span></html:link></li>
				</ul>
			</li>
		</logic:equal>
		<!--  2015/12/15 ooyama ADD END 法人間請求書機能対応  -->
		<li class='has-sub last'><a href='#'><span>マスタ</span></a>
			<ul id="ruleClass">
			
			<logic:equal name="LOGIN_USER_ID" value="2">
				<li><html:link href="initUserList.do" title="ユーザー一覧"><span>ユーザー一覧</span></html:link></li>
				<li><html:link href="ruleList.do" title="ユーザー一覧"><span>ID・PASS一覧</span></html:link></li>
				<logic:iterate name="LOGIN_USER_MASTER_LIST" id="listMasterId">
					<logic:equal name="listMasterId" property="corporationListFlg" value="1">
						<li>
							<html:link href="initCorporationList.do"><span><bean:write name="listMasterId" property="corporationListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="accountListFlg" value="1">
						<li>
							<html:link href="initAccountList.do"><span><bean:write name="listMasterId" property="accountListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="channelListFlg" value="1">
						<li>
							<html:link href="initChannelList.do"><span><bean:write name="listMasterId" property="channelListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="warehouseListFlg" value="1">
						<li>
							<html:link href="initWarehouseList.do"><span><bean:write name="listMasterId" property="warehouseListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="makerListFlg" value="1">
						<li>
							<html:link href="initMakerList.do"><span><bean:write name="listMasterId" property="makerListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="setItemListFlg" value="1">
						<li>
							<html:link href="initSetItemList.do"><span><bean:write name="listMasterId" property="setItemListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="clientListFlg" value="1">
						<li>
							<html:link href="initClientList.do"><span><bean:write name="listMasterId" property="clientListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="deliveryListFlg" value="1">
						<li>
							<html:link href="initDeliveryList.do"><span><bean:write name="listMasterId" property="deliveryListName"/></span></html:link>
						</li>
					</logic:equal>
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="LOGIN_USER_ID" value="2">
				<logic:iterate name="LOGIN_USER_MASTER_LIST" id="listMasterId">
					<logic:equal name="listMasterId" property="userListFlg" value="1">
						<li>
							<html:link href="initUserList.do"><span><bean:write name="listMasterId" property="userListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="ruleListFlg" value="1">
						<li>
							<html:link href="ruleList.do"><span><bean:write name="listMasterId" property="ruleListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="corporationListFlg" value="1">
						<li>
							<html:link href="initCorporationList.do"><span><bean:write name="listMasterId" property="corporationListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="accountListFlg" value="1">
						<li>
							<html:link href="initAccountList.do"><span><bean:write name="listMasterId" property="accountListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="channelListFlg" value="1">
						<li>
							<html:link href="initChannelList.do"><span><bean:write name="listMasterId" property="channelListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="warehouseListFlg" value="1">
						<li>
							<html:link href="initWarehouseList.do"><span><bean:write name="listMasterId" property="warehouseListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="makerListFlg" value="1">
						<li>
							<html:link href="initMakerList.do"><span><bean:write name="listMasterId" property="makerListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="setItemListFlg" value="1">
						<li>
							<html:link href="initSetItemList.do"><span><bean:write name="listMasterId" property="setItemListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="clientListFlg" value="1">
						<li>
							<html:link href="initClientList.do"><span><bean:write name="listMasterId" property="clientListName"/></span></html:link>
						</li>
					</logic:equal>
					<logic:equal name="listMasterId" property="deliveryListFlg" value="1">
						<li>
							<html:link href="initDeliveryList.do"><span><bean:write name="listMasterId" property="deliveryListName"/></span></html:link>
						</li>
					</logic:equal>
				</logic:iterate>
			</logic:notEqual>
				
				<%-- <logic:equal name="LOGIN_USER_OVERSEAS_INFO_AUTH" value="1">
					<li><html:link href="initUserList.do" title="ユーザー一覧"><span>ユーザー一覧</span></html:link></li>
					
				</logic:equal>
				<li><html:link href="initCorporationList.do" title="会社一覧"><span>会社一覧</span></html:link></li>
				<li><html:link href="initAccountList.do" title="口座一覧"><span>口座一覧</span></html:link></li>
				<li><html:link href="initChannelList.do" title="販売チャネル一覧"><span>販売チャネル一覧</span></html:link></li>
				<li><html:link href="initWarehouseList.do" title="倉庫一覧"><span>倉庫一覧</span></html:link></li>
				<li><html:link href="initVariousGroupList.do" title="各分類一覧"><span>各分類一覧</span></html:link></li>
				<li><html:link href="groupNmList.do" title="分類一覧"><span>分類一覧</span></html:link></li>
				<li><html:link href="initMakerList.do" title="メーカー一覧"><span>メーカー一覧</span></html:link></li>
				<li><html:link href="initSetItemList.do" title="セット商品一覧"><span>セット商品一覧</span></html:link></li>
				<li><html:link href="initClientList.do" title="得意先一覧"><span>得意先一覧</span></html:link></li>
				<li><html:link href="initDeliveryList.do" title="納入先一覧"><span>納入先一覧</span></html:link></li> --%>
			</ul>
		</li>
		<!--  2016/11/8 nozawa ADD END 法人間請求書機能対応  -->
		<li class='has-sub last'><a href='#'><span>国内</span></a>
			<ul>
				<li><html:link href="initDomesticOrder.do"  title="注文書作成"><span>注文書作成</span></html:link></li><li/>
				<li><html:link href="initDomesticOrderList.do" title="注文管理一覧"><span>注文管理一覧</span></html:link></li><li/>
				<li><html:link href="initDomesticExhibitionList.do" title="出品DB一覧"><span>出品DB一覧</span></html:link><li/>
				<li><html:link href="initDomesticExhibitionImportList.do" title="出品データインポート"><span>出品データインポート</span></html:link></li>
			</ul>
		</li>
		<logic:equal name="LOGIN_USER_OVERSEAS_INFO_AUTH" value="1">
		<li class='has-sub last'><a href='#'><span>海外</span></a>
			<ul>
				<li><html:link href="initForeignOrderList.do" title="海外注文管理"><span>注文管理</span></html:link></li>
				<li><html:link href="initSupplierList.do" title="海外仕入先台帳"><span>仕入先台帳</span></html:link></li>
				<li><html:link href="initCurrencyLedgerList.do" title="海外通貨台帳"><span>通貨台帳</span></html:link></li>
			</ul>
		</li>
		</logic:equal>

		<li id="loginName" style="float: right;"><span class="nameLabel"><bean:write name="LOGIN_USER_NAME" />   | </span><span class="logout"><a style="color: #F8F8F8;" href="logout.do" >ログアウト</a></span></li>
	</ul>
</div>
