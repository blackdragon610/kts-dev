package jp.co.kts.service.fileExport;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.common.util.StringUtil;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.service.mst.ClientService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.sale.CorporateSaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;


public class ExportCsvService {

	public void ehidenCsvDownLoad(HttpServletResponse response, List<ExtendCorporateSalesSlipDTO> slipList) throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String csvName =  new String((dateFormat.format(date) + "e飛伝.csv").getBytes("Shift_JIS"), "ISO8859_1");

		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + csvName+"\"");
		response.flushBuffer();

		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "Windows-31J"));

		CorporationService corporationService = new CorporationService();
		MstCorporationDTO corporation = new MstCorporationDTO();

		ClientService clientService = new ClientService();
		MstClientDTO client = new MstClientDTO();

		for (ExtendCorporateSalesSlipDTO dto : slipList) {
			if (!StringUtils.equals(dto.getTransportCorporationSystem(), WebConst.TRANSPORT_CORPORATION_SYSTEM_NAME_2)) {
				continue;
			}

			//届け先
			client = clientService.getClient(dto.getSysClientId());
			//荷主/依頼主
			corporation =  corporationService.getCorporation(dto.getSysCorporationId());

			//住所録コード
			out.print(client.getClientNo());

			//お届け先電話番号
			out.print(",");
			out.print(dto.getDestinationTel());

			//お届け先郵便番号
			out.print(",");
			out.print(dto.getDestinationZip());

			String twoBytesClientAddressAll = StringUtil.toUpperPlurals(dto.getDestinationPrefectures() +
					dto.getDestinationMunicipality() + dto.getDestinationAddress()+ dto.getDestinationBuildingNm());

			String twoBytesClientAddress[] = ehidenAddressSplit(twoBytesClientAddressAll);

			//お届け先住所１
			out.print(",");
			out.print(twoBytesClientAddress[0]);

			//お届け先住所２
			out.print(",");
			out.print(twoBytesClientAddress[1]);

			//お届け先住所３
			out.print(",");
			out.print(twoBytesClientAddress[2]);

			//お届け先名称１
			out.print(",");
			out.print(dto.getDestinationNm());

			//お届け先名称２
			out.print(",");
			out.print("");

			//お客様管理ナンバー
			out.print(",");
			out.print(client.getClientNo());

			//お客様コード
			out.print(",");
			String clientCode = "";
			switch ((int)dto.getSysCorporationId()){
			case 1: //KTS
			case 7: //KTS法人
			case 10: //KTS掛業販
			case 11: //KTS海外事業部
			case 12: //Brenbo事業部
				clientCode = "128589410004";
				break;
			case 2: //車楽院
				clientCode = "132683520002";
				break;
			case 3: //T-four
				clientCode = "128589410004";
				break;
			case 4: //ラルグスリテール
			case 8: //ラルグス法人営業部
			case 9: //ラルグス車高調
				clientCode = "132683680000";
				break;
//				clientCode = "13268368000";
//				break;
			case 5: //BCR
				clientCode = "132683600006";
				break;
			case 6: //サイバーエコ
			case 13: // ウルトラレーシング事業部
				clientCode = "132683570000";
				break;
			}
			out.print(clientCode);

			//部署・担当者
			out.print(",");
			out.print("");

			//荷送人電話番号
			out.print(",");
			out.print(corporation.getTelNo());

			//ご依頼主電話番号
			out.print(",");
			out.print(corporation.getTelNo());

			//ご依頼主郵便番号
			out.print(",");
			out.print(corporation.getZip());

			//ご依頼主住所１ 全角英数16
			String twoBytesCorporationAddressAll = StringUtil.toUpperPlurals(corporation.getAddress());
			String towByteCorporationAddress[] = ehidenAddressSplit(twoBytesCorporationAddressAll);
			out.print(",");
			out.print(towByteCorporationAddress[0]);

			//ご依頼主住所２ 全角英数16
			out.print(",");
			out.print(towByteCorporationAddress[1]);

			//ご依頼主名称１
			out.print(",");
			out.print(corporation.getCorporationNm());

			//ご依頼主名称２
			out.print(",");
			out.print("");

			//荷姿コード
			out.print(",");
			out.print("1");

			//記載事項があれば記載事項、なければ品名を入力していく
			String itemNm[] = ehidenItemSplit(dto);
			//品名１
			out.print(",");
			out.print(itemNm[0]);

			//品名２
			out.print(",");
			out.print(itemNm[1]);

			//品名３
			out.print(",");
			out.print(itemNm[2]);

			//品名４
			out.print(",");
			out.print(itemNm[3]);

			//品名５
			out.print(",");
			// out.print(itemNm[4]);			// HERE order NO
			if (dto.getOrderNo() == null)
				out.print("");
			else
				out.print(dto.getOrderNo());

			//出荷個数
			out.print(",");
			out.print("1");

			//便種（スピードで選択）
			out.print(",");
			out.print("0");

			//便種（商品）
			out.print(",");
			out.print("1");

			//配達日
			out.print(",");
			out.print(dto.getDestinationAppointDate().replace("/", ""));

			//配達指定時間帯
			out.print(",");
			out.print(dto.getDestinationAppointTime());

			//配達指定時間（時分）
			out.print(",");
			out.print("");

			//代引きの物は代引き金額を設定
			int[] price = {0,0};
			if (StringUtils.equals(WebConst.INVOICE_CLASSIFICATION_MAP_EHIDEN.get(dto.getInvoiceClassification()),"2")) {
				price[0] = dto.getSumClaimPrice();
				price[1] = dto.getTax();
			}
			//代引金額 半角7
			out.print(",");
			out.print(price[0]);

			//消費税 半角6
			out.print(",");
			out.print(price[1]);


			//決済種別
			out.print(",");
			out.print("2");

			//保険金額
			out.print(",");
			out.print("0");

			//保険金額印字
			out.print(",");
			out.print("0");

			//指定シール①
			out.print(",");
			out.print("011");

			//指定シール②
			out.print(",");
			out.print("");

			//指定シール③
			out.print(",");
			out.print("");

			//営業店止め
			out.print(",");
			out.print("0");

			//SRC区分
			out.print(",");
			out.print("0");

			//営業店コード
			out.print(",");
			out.print("");

			//元着区分
			out.print(",");
			out.print("1");

			//送り状種別 半角数字1文字 (※宅急便_必須項目)
			out.print(",");
			out.print(WebConst.INVOICE_CLASSIFICATION_MAP_EHIDEN.get(dto.getInvoiceClassification()));
			out.print("\r\n");
		}

		out.flush();
		out.close();
	}

	private String[] ehidenAddressSplit(String twoBytesAddressAll) {

		String twoBytesddress[] = {"","",""};
		for (int i = 0; i < 3; i++) {
			if (twoBytesAddressAll.length() <= 16*(i+1)) {
				twoBytesddress[i] = twoBytesAddressAll.substring(16*i);
				break;
			}
			twoBytesddress[i] = twoBytesAddressAll.substring(16*i, 16*(i+1));
		}
		return twoBytesddress;
	}

	private String[] ehidenItemSplit(ExtendCorporateSalesSlipDTO slipDto) throws Exception {

		//e飛伝の商品名は5列まで
		String item[] = {"","","","",""};
		int maxColumn = 5;

		CorporateSaleDisplayService service = new CorporateSaleDisplayService();
		if (StringUtils.isEmpty(slipDto.getSenderRemarks())) {
			List<ExtendCorporateSalesItemDTO> itemList = service.getCorporateSalesItemList(
					slipDto.getSysCorporateSalesSlipId(), slipDto.getSysCorporationId());

			//商品が一つなら限界まで詰める
			if (itemList.size() == 1) {
				if (StringUtils.equals(itemList.get(0).getScheduledLeavingDate(), StringUtil.getToday())) {
					String twoBytesItem = StringUtil.toUpperPlurals(itemList.get(0).getItemNm().replaceAll("\r\n", ""));
					for (int i = 0; i < maxColumn; i++) {
						if (twoBytesItem.length() <= 16*(i+1)) {
							item[i] = twoBytesItem.substring(16*i);
							break;
						}
						item[i] = twoBytesItem.substring(16*i, 16*(i+1));
					}
				}
			//複数商品なら16字で切って入れていく
			} else {
				int loop = (itemList.size() < maxColumn) ? itemList.size() : maxColumn;
				int i=0;
				for (ExtendCorporateSalesItemDTO itemDto : itemList){
					if (!StringUtils.equals(itemDto.getScheduledLeavingDate(), StringUtil.getToday())) {
						continue;
					}

					String twoBytesItem = StringUtil.toUpperPlurals(itemDto.getItemNm().replaceAll("\r\n", ""));
					item[i] = (twoBytesItem.length() <= 16) ? twoBytesItem : twoBytesItem.substring(0,15);
					if (++i >= loop) { break; }
				}
			}
		} else {
			//記載事項を限界まで詰める
			String twoBytesItem = StringUtil.toUpperPlurals(slipDto.getSenderRemarks().replaceAll("\r\n", ""));
			for (int i = 0; i < maxColumn; i++) {
				if (twoBytesItem.length() <= 16*(i+1)) {
					item[i] = twoBytesItem.substring(16*i);
					break;
				}
				item[i] = twoBytesItem.substring(16*i, 16*(i+1));
			}
		}


		return item;
	}

	private int[] calcSumPrice (ExtendCorporateSalesSlipDTO slipDto) throws Exception {

		CorporateSaleDisplayService corpDispService = new CorporateSaleDisplayService();

		List<ExtendCorporateSalesItemDTO> itemList = corpDispService.getCorporateSalesItemList(
				slipDto.getSysCorporateSalesSlipId(), slipDto.getSysCorporationId());

		int[] price = {0,0};
		int tax = 0;

		for (ExtendCorporateSalesItemDTO itemDto : itemList){

			if (!StringUtils.equals(itemDto.getScheduledLeavingDate(), StringUtil.getToday())) {
				continue;
			}

			tax = corpDispService.getTax(itemDto.getPieceRate(), slipDto.getTaxRate());
			price[0] += (itemDto.getPieceRate() + tax) * itemDto.getOrderNum();
			price[1] += tax * itemDto.getOrderNum();
		}

		return price;
	}


	public void b2CsvDownLoad(HttpServletResponse response, List<ExtendCorporateSalesSlipDTO> slipList) throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String csvName =  new String((dateFormat.format(date) + "B2.csv").getBytes("Shift_JIS"), "ISO8859_1");

		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + csvName+"\"");
		response.flushBuffer();

		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "Windows-31J"));

		CorporationService corporationService = new CorporationService();
		MstCorporationDTO corporation = new MstCorporationDTO();

		ClientService clientService = new ClientService();
		MstClientDTO client = new MstClientDTO();

		for (ExtendCorporateSalesSlipDTO dto : slipList) {
			if (!StringUtils.equals(dto.getTransportCorporationSystem(), WebConst.TRANSPORT_CORPORATION_SYSTEM_NAME_1)) {
				continue;
			}

			//届け先
			client = clientService.getClient(dto.getSysClientId());
			//荷主/依頼主
			corporation =  corporationService.getCorporation(dto.getSysCorporationId());

			//お客様管理番号 半角英数字20文字
			out.print(client.getClientNo());

			//送り状種類 半角数字1文字 (※宅急便_必須項目)
			out.print(",");
//			//Keyを数字に直したいが売上管理でも使ってしまっているので、こちら側で対応する数字に変換
//			String invoice = "";
//			switch (dto.getInvoiceClassification()) {
//			case "発払い（元払い）":
//				invoice = "0";
//				break;
//			case "コレクト（代引き）":
//				invoice = "2";
//				break;
//			case "DM便":
//				invoice = "3";
//				break;
//			case "メール便速達":
//				invoice = "6";
//				break;
//			case "ネコポス":
//				invoice = "7";
//				break;
//			case "宅急便コンパクト":
//				invoice = "8";
//				break;
//			}
			out.print(WebConst.INVOICE_CLASSIFICATION_MAP_B2.get(dto.getInvoiceClassification()));
			//クール区分 半角数字1文字
			out.print(",");
			out.print("0");

			//伝票番号 半角数字12文字 ※B2Webにて付与
			out.print(",");
			out.print("");

			//出荷予定日 半角10文字 ｢YYYY/MM/DD｣ (※宅急便_必須項目)
			out.print(",");
			//本日日付を入れるように変更
			//out.print(dto.getShipmentPlanDate());
			out.print(StringUtil.getToday());

			//お届け予定日 半角10文字 ｢YYYY/MM/DD｣
			out.print(",");
			out.print(dto.getDestinationAppointDate());

			//配達時間帯 半角4文字
			out.print(",");
			String appointTime = (!StringUtils.equals(dto.getDestinationAppointTime(), "0") && StringUtils.isNotEmpty(dto.getDestinationAppointTime()))
					? dto.getDestinationAppointTime(): "";
			out.print(appointTime);

			//お届け先コード 半角英数字20文字
			out.print(",");
			out.print("");

			//お届け先電話番号 半角数字15文字ハイフン含む (※宅急便_必須項目)
			out.print(",");
			out.print(dto.getDestinationTel());

			//お届け先電話番号枝番 半角数字2文字
			out.print(",");
			out.print("");

			//お届け先郵便番号 半角数字8文字 ハイフンなし7文字も可 (※宅急便_必須項目)
			out.print(",");
			out.print(dto.getDestinationZip());

			//お届け先住所 全角/半角 都道府県（４文字） 市区郡町村（１２文字） 町・番地（１６文字） (※宅急便_必須項目)
			out.print(",");
			out.print(dto.getDestinationPrefectures()
					+ dto.getDestinationMunicipality() + dto.getDestinationAddress());

			//お届け先アパートマンション名 全角/半角  16文字/32文字
			out.print(",");
			out.print(dto.getDestinationBuildingNm());

			//お届け先会社・部門１ 全角/半角 25文字/50文字
			out.print(",");
			out.print("");

			//お届け先会社・部門２ 全角/半角 25文字/50文字
			out.print(",");
			out.print("");

			//お届け先名 全角/半角 16文字/32文字  (※宅急便_必須項目)
			out.print(",");
			out.print(dto.getDestinationNm());

			//お届け先名(ｶﾅ) 半角カタカナ 50文字
			out.print(",");
			out.print(dto.getDestinationNmKana());

			//敬称 全角/半角 2文字/4文字 ＤＭ便、メール便速達の場合に指定可能
			out.print(",");
			out.print("様");

			//ご依頼主コード 半角英数字 20文字
			out.print(",");
			out.print("");

			//ご依頼主電話番号 半角数字15文字ハイフン含む (※宅急便_必須項目)
			out.print(",");
			out.print(corporation.getTelNo());

			//ご依頼主電話番号枝番 半角数字 2文字
			out.print(",");
			out.print("");

			//ご依頼主郵便番号 半角数字8文字 ハイフンなし半角7文字も可  (※宅急便_必須項目)
			out.print(",");
			out.print(corporation.getZip());

			//ご依頼主住所 全角/半角32文字/64文字 都道府県（４文字） 市区郡町村（１２文字） 町・番地（１６文字） (※宅急便_必須項目)
			out.print(",");
			out.print(corporation.getAddress());

			//ご依頼主アパートマンション 全角/半角 16文字/32文字
			out.print(",");
			out.print("");

			//ご依頼主名 全角/半角 16文字/32文字  (※宅急便_必須項目)
			out.print(",");
			out.print(corporation.getCorporationNm());

			//ご依頼主名(ｶﾅ) 半角カタカナ 50文字
			out.print(",");
			out.print("");

			String item[] = b2ItemSplit(dto);

			//品名コード１ 半角英数字 30文字
			out.print(",");
			out.print("");

			//品名１ 全角/半角 25文字/50文字  (※宅急便_必須項目)
			out.print(",");
			out.print(item[0]);

			//品名コード２ 半角英数字 30文字
			out.print(",");
			out.print("");

			//品名２
			out.print(",");
			out.print(item[1]);

			//荷扱い１ 全角/半角 10文字/20文字
			out.print(",");
			out.print("");

			//荷扱い２ 全角/半角 10文字/20文字
			out.print(",");
			out.print("");

			//記事 全角/半角 16文字/32文字
			out.print(",");
			
			if (dto.getOrderNo() == null)
				out.print("");					// HERE ORDERNO for yamato
			else
				out.print(dto.getOrderNo());		// 

			//代引きの物は代引き金額を設定
			if (StringUtils.equals(WebConst.INVOICE_CLASSIFICATION_MAP_B2.get(dto.getInvoiceClassification()),"2")) {
				//ｺﾚｸﾄ代金引換額（税込) 半角数字 7文字 ※送り状種類がコレクトの場合は必須 300,000円以下　1円以上
				out.print(",");
				out.print(dto.getSumClaimPrice());

				//内消費税額等 半角数字 7文字 ※送り状種類がコレクトの場合は必須  ※コレクト代金引換額（税込)以下
				out.print(",");
				out.print(dto.getTax());
			} else {
				//ｺﾚｸﾄ代金引換額（税込) 半角数字 7文字 ※送り状種類がコレクトの場合は必須 300,000円以下　1円以上
				out.print(",");
				out.print("");

				//内消費税額等 半角数字 7文字 ※送り状種類がコレクトの場合は必須  ※コレクト代金引換額（税込)以下
				out.print(",");
				out.print("");
			}

			//止置き 半角数字 1文字
			out.print(",");
			out.print("0");

			//営業所コード 半角数字 6文字 ※止置きを利用する場合は必須
			out.print(",");
			out.print("");

			//発行枚数 半角数字 2文字 ※発払いのみ指定可能
			out.print(",");
			out.print("1");

			//個数口表示フラグ 半角数字 1文字
			out.print(",");
			out.print("1");

			//請求先顧客コード 半角数字12文字 (※宅急便_必須項目)
			//とりあえずべた書き。必要があればWebconst or corpマスタへ
			out.print(",");
			String billingCode = "";
			switch ((int)dto.getSysCorporationId()){
			case 1: //KTS
			case 7: //KTS法人
			case 10: //KTS掛業販
			case 11: //KTS海外事業部
			case 12: //Brenbo事業部
				billingCode = "0482858871";
				break;
			case 2: //車楽院
				billingCode = "0354033457";
				break;
			case 4: //ラルグスリテール
			case 8: //ラルグス法人
			case 9: //ラルグス車高調
				billingCode = "0353224259";
				break;
			case 5: //BCR
				billingCode = "0357895975";
				break;
			case 6: //サイバーエコ
			case 13: //ウルトラレーシング事業部
				billingCode = "0356865931";
				break;
			}
			out.print(billingCode);

			//請求先分類コード 空白または半角数字3文字
			out.print(",");
			out.print("");

			//運賃管理番号 半角数字2文字 (※宅急便_必須項目)
			out.print(",");
			out.print("01");

			//注文時カード払いデータ登録 半角数字 1文字
			out.print(",");
			out.print("0");

			//注文時カード払い加盟店番号 半角英数字 9文字  ※注文時カード払いデータ有りの場合は必須
			out.print(",");
			out.print("");

			//注文時カード払い申込受付番号１ 半角英数字 23文字 ※注文時カード払いデータ有りの場合は必須
			out.print(",");
			out.print("");

			//注文時カード払い申込受付番号２	半角英数字 23文字
			out.print(",");
			out.print("");

			//注文時カード払い申込受付番号３ 半角英数字 23文字
			out.print(",");
			out.print("");

			//お届け予定ｅメール利用区分 半角数字 1文字
			out.print(",");
			out.print("0");

			//お届け予定ｅメールe-mailアドレス 半角英数字＆記号 60文字 ※お届け予定eメールを利用する場合は必須
			out.print(",");
			out.print("");

			//入力機種 半角数字 1文字  ※お届け予定eメールを利用する場合は必須
			out.print(",");
			out.print("1");

			//お届け予定ｅメールメッセージ 全角 74文字 ※お届け予定eメールを利用する場合は必須
			out.print(",");
			out.print("");

			//お届け完了ｅメール利用区分 半角数字 1文字
			out.print(",");
			out.print("0");

			//お届け完了ｅメールe-mailアドレス 半角英数字 60文字  ※お届け完了eメールを利用する場合は必須
			out.print(",");
			out.print("");

			//お届け完了ｅメールメッセージ	全角 159文字 ※お届け完了eメールを利用する場合は必須  ※お届け完了eメールを利用する場合は必須
			out.print(",");
			out.print("");

			//クロネコ収納代行利用区分 半角1文字
			out.print(",");
			out.print("0");

			out.print("\r\n");
		}

		out.flush();
		out.close();
	}

	private String[] b2ItemSplit(ExtendCorporateSalesSlipDTO slipDto) throws Exception {

		//B2の商品名は（全角/半角） ：（25文字/50文字）2列まで
		int maxColumn = 2;
		int maxSize = 50;

		String itemAry[] = new String[maxColumn];
		for ( int i = 0; i < maxColumn; i++ ) { itemAry[i] = ""; }

		CorporateSaleDisplayService service = new CorporateSaleDisplayService();
		if (StringUtils.isEmpty(slipDto.getSenderRemarks())) {
			List<ExtendCorporateSalesItemDTO> itemList = service.getCorporateSalesItemList(
					slipDto.getSysCorporateSalesSlipId(), slipDto.getSysCorporationId());

			//商品が一つなら限界まで詰める
			if (itemList.size() == 1) {
				if ( ! StringUtils.equals(itemList.get(0).getScheduledLeavingDate(), StringUtil.getToday())) {
					return itemAry;
				}

				//商品名
				String itemName = itemList.get(0).getItemNm().replaceAll("\r\n", "");

				int currentColumn = 0;
				int currentSize = 0;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < itemName.length(); i++ ) {

					//Shift_JISで文字サイズチェック（半角1、全角2）
					currentSize += String.valueOf(itemName.charAt(i)).getBytes("Shift_JIS").length;
					if (currentSize >= maxSize) {
						if ( currentSize == maxSize) {
							sb.append(itemName.charAt(i));
						} else {
							i--;
						}

						itemAry[currentColumn] = sb.toString();
						currentColumn++;
						if (currentColumn >= maxColumn) { break; }
						sb.setLength(0);
						currentSize = 0;
					} else {
						sb.append(itemName.charAt(i));
					}
				}

				if (currentColumn < maxColumn) {
					if (sb.length() > 0) {
						itemAry[currentColumn] = sb.toString();
					}
				}
			//複数商品なら25字で切って入れていく
			} else {

				int currentColumn = 0;
				StringBuilder sb = new StringBuilder();
				for (ExtendCorporateSalesItemDTO item : itemList) {
					if ( ! StringUtils.equals(item.getScheduledLeavingDate(), StringUtil.getToday())) {
						continue;
					}

					//商品名
					String itemName = item.getItemNm().replaceAll("\r\n", "");
					int currentSize = 0;
					sb.setLength(0);
					for ( int i = 0; i < itemName.length(); i++ ) {

						//Shift_JISで文字サイズチェック（半角1、全角2）
						currentSize += String.valueOf(itemName.charAt(i)).getBytes("Shift_JIS").length;
						if (currentSize >= maxSize) {
							if ( currentSize == maxSize) {
								sb.append(itemName.charAt(i));
							}

							break;
						} else {
							sb.append(itemName.charAt(i));
						}
					}

					itemAry[currentColumn] = sb.toString();
					currentColumn++;
					if (currentColumn >= maxColumn) {
						break;
					}
				}
			}
		} else {
			//記載事項を限界まで詰める
			String senderRemarks = slipDto.getSenderRemarks().replaceAll("\r\n", "");

			int currentColumn = 0;
			int currentSize = 0;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < senderRemarks.length(); i++ ) {

				//Shift_JISで文字サイズチェック（半角1、全角2）
				currentSize += String.valueOf(senderRemarks.charAt(i)).getBytes("Shift_JIS").length;
				if (currentSize >= maxSize) {
					if ( currentSize == maxSize) {
						sb.append(senderRemarks.charAt(i));
					} else {
						i--;
					}

					itemAry[currentColumn] = sb.toString();
					currentColumn++;
					if (currentColumn >= maxColumn) { break; }
					sb.setLength(0);
					currentSize = 0;
				} else {
					sb.append(senderRemarks.charAt(i));
				}

			}

			if (currentColumn < maxColumn) {
				if (sb.length() > 0) {
					itemAry[currentColumn] = sb.toString();
				}
			}
		}

		return itemAry;
	}

	/**
	* <p>
	* 西濃運輸のCSVファイルダウンロードを行います。
	* </p>
	* @param response HTTPレスポンス
	* @param slipList リスト
	* @throws Exception 例外
	*/
	public void seinoCsvDownLoad(HttpServletResponse response, List<ExtendCorporateSalesSlipDTO> slipList)
		  throws Exception {

		// 変数定義
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		// CSVファイル名の取得
		String csvName = new String((dateFormat.format(date) + "seino.csv").getBytes("Shift_JIS"), "ISO8859_1");

		// コンテントタイプの設定
		response.setContentType("application/octet-stream; charset=Windows-31J");

		// ヘッダーの設定
		response.setHeader("Content-Disposition", "attachment; filename=\"" + csvName + "\"");

		// 保存
		response.flushBuffer();

		// PrintWriterの生成
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "Windows-31J"));

		String shipperCode = null;

		// CorporationServiceの生成
		CorporationService corporationService = new CorporationService();

		// CorporateSaleDisplayServiceの生成
		CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

		// リスト分、ループ
		for (ExtendCorporateSalesSlipDTO dto : slipList) {

		  // 運送会社システムが西濃運輸でない場合
		  if (!StringUtils.equals(dto.getTransportCorporationSystem(),
				WebConst.TRANSPORT_CORPORATION_SYSTEM_NAME_4)) {

			 // 次へ
			 continue;
		  }

		  // 管理番号(受注番号）
		  // 管理番号のアルファベットが小文字だとインポート出来ないため、アルファベットを大文字にする。
		  String orderNo = dto.getOrderNo().toUpperCase();
		  out.print(orderNo);

		  // 出荷予定日
		  out.print(",");
		  //本日日付を入れるように変更
		  out.print(StringUtil.getToday());

		  // 原票区分
		  out.print(",");
		  out.print(WebConst.GENPYO_KBN_MAP_SEINO.get(dto.getGenpyoKbn()));

		  // 元着区分
		  out.print(",");
		  out.print(WebConst.INVOICE_CLASSIFICATION_MAP_SEINO.get(dto.getInvoiceClassification()));

		  // 届先コード
		  out.print(",");
		  out.print(dto.getDestinationTel());

		  // 届先電話番号
		  out.print(",");
		  out.print(dto.getDestinationTel());

		  // 届先郵便番号
		  out.print(",");
		  out.print(dto.getDestinationZip());

		  // 届先住所１
		  out.print(",");
		  out.print(dto.getDestinationPrefectures()
				+ dto.getDestinationMunicipality()
				+ dto.getDestinationAddress()
				+ dto.getDestinationBuildingNm());

		  // 届先住所２
		  out.print(",");
		  out.print("");

		  // 届先名称１
		  out.print(",");
		  out.print(dto.getDestinationNm());

		  // 届先名称２
		  out.print(",");
		  out.print("");

		  // 個口
		  out.print(",");
		  out.print("1");

		  // 輸送指示1
		  out.print(",");
		  out.print(WebConst.YUSO_SHIJI_MAP_SEINO.get(dto.getYusoShiji()));

		  // 輸送指示2
		  out.print(",");
		  out.print(WebConst.YUSO_SHIJI_MAP_SEINO.get(dto.getYusoShiji2()));

		  // 配送日時指定
		  out.print(",");
		  out.print(getDestinationAppoint(dto));

		  // 荷送人コード（KTS法人営業部固定）
		  out.print(",");
		  switch ((int) dto.getSysCorporationId()) {
		  //case 1: //KTS
		  //case 2: //車楽院
		  //case 3: //T-four
		  //case 4: //ラルグスリテール
		  //case 5: //BCR
		  //case 6: //サイバーエコ
		  case 7: //KTS法人
			 shipperCode = "0482858941";
			 break;
		  //case 8: //ラルグス法人営業部
		  //case 9: //ラルグス車高調
		  //case 10: //KTS掛業販
		  //case 11: //KTS海外事業部
		  //case 12: //Brenbo事業部
		  default:
			 shipperCode = "";
			 break;
		  }
		  out.print(shipperCode);

		  // マスタ：会社一覧の取得
		  MstCorporationDTO corporation = corporationService.getCorporation(dto.getSysCorporationId());

		  // 電話番号
		  out.print(",");
		  out.print(corporation.getTelNo());

		  // 住所１
		  out.print(",");
		  out.print(corporation.getAddress());

		  // 住所２
		  out.print(",");
		  out.print("");

		  // 名称
		  out.print(",");
		  out.print(corporation.getCorporationNm());

		  // 商品名称
		  out.print(",");
		  List<ExtendCorporateSalesItemDTO> itemList = corporateSaleDisplayService
				.getCorporateSalesItemListForEstimate(dto.getSysCorporateSalesSlipId(), dto.getSysCorporationId());
		  if (itemList == null) {
			 out.print("");
		  } else {
			 out.print(((ExtendCorporateSalesItemDTO) itemList.get(0)).getItemNm());
		  }

		  out.print("\r\n");
		}

		out.flush();
		out.close();
	}

	/**
	* <p>
	* 配送日時指定の値（文字列）を返します。
	* </p>
	* @param dto 拡張コーポレート売上DTO
	* @return String 文字列
	*/
	private String getDestinationAppoint(ExtendCorporateSalesSlipDTO dto) {

		// 02：配達指定でない場合
		if (!"02".equals(WebConst.YUSO_SHIJI_MAP_SEINO.get(dto.getYusoShiji())) &&
			 !"02".equals(WebConst.YUSO_SHIJI_MAP_SEINO.get(dto.getYusoShiji2()))) {

		  // 空文字を返却
		  return "";
		}
		// お届け指定日がない場合
		else if (dto.getDestinationAppointDate().length() == 0) {

		  // 届け時間帯を返却
		  return dto.getDestinationAppointTime();
		}
		// 上記以外の場合
		else {

		  // 届け指定日 + 届け時間帯
		  String value = dto.getDestinationAppointDate() + dto.getDestinationAppointTime();

		  // 返却
		  return value.replace("/", "").substring(4);
		}
	}
}
