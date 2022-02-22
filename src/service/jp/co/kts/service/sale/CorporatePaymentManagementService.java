package jp.co.kts.service.sale;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.CorporateReceiveDTO;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateBillDTO;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.extendCommon.entity.ExtendPaymentManagementDTO;
import jp.co.kts.app.output.entity.SysCorporateSalesSlipIdDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.dao.common.CorporateBillDAO;
import jp.co.kts.dao.common.TransactionDAO;
import jp.co.kts.dao.mst.AccountDAO;
import jp.co.kts.dao.mst.ClientDAO;
import jp.co.kts.dao.sale.CorporatePaymentManagementDAO;
import jp.co.kts.dao.sale.CorporateReceiveDAO;
import jp.co.kts.dao.sale.CorporateSaleDAO;
import jp.co.kts.service.mst.CorporationService;

/**
 * 業販入金管理のServiceクラス
 * 入金管理の目的は、得意先・月の締日ごとに掛売(後払い)の、支払が済んでいない情報と、入金情報を管理する目的で作られた。
 * 掛売かつステータス受注の業販伝票が[得意先・月の締日]ごとに合算され、支払が済んでいない情報(請求額・繰越金額)と、入金情報(入金額・入金日)を管理する。
 *
 * 先方からの要望は大別して以下の３点
 * (1)業販管理 の 請求書作成時には、入金管理の情報＝請求書の情報となるようにしたい。
 * (2)入金管理は、掛売の得意先のみが載るようにしたい。
 * (3)入金管理は、掛売の業販伝票が締日ごとに合算され、支払が済んでいない情報と、入金情報を管理したい。
 * @author boncre t_yamamura 修正
 */
public class CorporatePaymentManagementService extends CorporationService {

	/** リスト、配列の先頭を示す定数 */
	private static int INDEX_ZERO_SET = 0;
	/** 金額系0設定 金額系の0と比較に使用する定数 */
	private static int AMOUNT_ZERO_SET = 0;
	/** コード値の比較：0 */
	private static int CODE_COMPARISON_ZERO = 0;

	/**
	 * 業販伝票作成時に、以下の条件に合致していたら入金管理情報を新規作成or更新する<br>
	 * ・ステータス：受注
	 * ・支払方法：掛売
	 *
	 * 対象請求月、法人、請求先(得意先)、口座に一致する入金管理データが存在しないとき、新規登録<br>
	 * 既に存在する場合は、請求額や入金情報を合算して更新する
	 * @param corporateSalesSlipDTO
	 */
	public void registerPaymentInformation(ExtendCorporateSalesSlipDTO corporateSalesSlipDTO) throws Exception {

		// その他必要なインスタンス
		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();
		CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
		ClientDAO clientDAO = new ClientDAO();
		AccountDAO accountDAO = new AccountDAO();
		CorporateReceiveDAO receiveDAO = new CorporateReceiveDAO();

		/*
		 * 得意先が支店である場合、全支店と本店の入金情報を本店に統合する。
		 * 登録or更新する得意先IDを本店の得意先IDで上書きし、本店の入金管理情報として登録・更新する。
		 */
		// 得意先Idを格納するList
		List<Long> clientIdList = new ArrayList<Long>();

		// 得意先情報取得
		MstClientDTO clientDTO = clientDAO.getClient(corporateSalesSlipDTO.getSysClientId());
		// 支店チェック
		boolean isBranch = false;
		// 請求先カラムに格納されている本店の得意先名
		String billingDstNm = "";
		// 本店のsys_client_id 得意先ID
		long billingDstId = 0;
		// 本店のシステム入金管理ID
		long headPaymentManagementId = 0;
		// 入金日
		String receiveDate = "";
		// 対象の伝票が存在する場合のフラグ。請求額と入金情報を合算する、掛売かつ受注伝票が対象。
		boolean isExistSlip = false;

		/*
		 * 支店がある場合は、以下のカラムに値がある（nullもしくは空文字ではない）
		 * mst_clientテーブルのbilling_dst(請求先)カラム
		 */

		if (!StringUtils.isEmpty(clientDTO.getBillingDst())) {
			billingDstNm = clientDTO.getBillingDst();
			if (!StringUtils.isEmpty(billingDstNm)) {
				billingDstId = Long.parseLong(clientDAO.getClientNmWithName(billingDstNm, clientDTO.getSysCorporationId()));
			}
			// 支店ありのため支店チェックをtrueにする
			isBranch = true;
			// 請求先カラムに入っている同一の文字列は、本店の得意先名。
			// 請求額や入金額を合算し本店に統合するために、本店と各支店の得意先IdをListで持つ
			clientIdList = clientDAO.getSysClientIdList(clientDTO.getBillingDst());

		} else { // 本店・支店のない場合
			clientIdList.add(clientDTO.getSysClientId());
		}

		/*****
		 * 締日と本日日付から請求月を決定する処理 START
		 *************************************************************/
		// 得意先の締日ごとに請求月を指定する処理
		// 今月請求月と前月請求月
		String demandMonth = null;
		String preDemandMonth = null;
		// 日付形式
		DateFormat formatYMD = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
		DateFormat formatYM = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);

		// 日本の現在時間設定
		Calendar cal = Calendar.getInstance(Locale.JAPAN);
		// 本日日付 yyyy/MM/dd
		String today = formatYMD.format(cal.getTime());
		// 今月 yyyy/MM
		String curMonth = formatYM.format(cal.getTime());
		// 前月 yyyy/MM
		cal.add(Calendar.MONTH, -1);
		String preMonth = formatYM.format(cal.getTime());
		// 来月 yyyy/MM
		cal = Calendar.getInstance(Locale.JAPAN);
		cal.add(Calendar.MONTH, 1);
		String nextMonth = formatYM.format(cal.getTime());
		// 締日
		int CUTOFF_DATE = clientDTO.getCutoffDate();

		// 日付リセット
		cal = Calendar.getInstance(Locale.JAPAN);

		// 本日日付と締日から請求月と前月請求月を求める
		// 比較に使用する日付
		String comparisonDateFrom = "";
		String comparisonDateTo = "";

		// 末日締め、25日締め、20日締めの時は前月～今月、15日締め、10日締め、5日締めの時は今月～次月が対象となる
		switch (CUTOFF_DATE) {
		// 末日締め
		case 0:
			comparisonDateFrom = curMonth + "/01";
			comparisonDateTo = curMonth + "/" + cal.getActualMaximum(Calendar.DATE);

			// 締日の範囲から今日がいつの請求月になるかを判定し、請求月と前月請求月を算出する。
			if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
				demandMonth = curMonth;
				preDemandMonth = preMonth;
			} else if (comparisonDateTo.compareTo(today) < 0) {
				demandMonth = nextMonth;
				preDemandMonth = curMonth;
			} else {
				demandMonth = preMonth;
				cal.add(Calendar.MONTH, -2);
				preDemandMonth = formatYM.format(cal.getTime());
			}
			break;

		// 25日締め
		case 1:
			comparisonDateFrom = preMonth + "/26";
			comparisonDateTo = curMonth + "/25";

			// 締日の範囲から今日がいつの請求月になるかを判定し、請求月と前月請求月を算出する。
			if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
				demandMonth = curMonth;
				preDemandMonth = preMonth;
			} else if (comparisonDateTo.compareTo(today) < 0) {
				demandMonth = nextMonth;
				preDemandMonth = curMonth;
			} else {
				demandMonth = preMonth;
				cal.add(Calendar.MONTH, -2);
				preDemandMonth = formatYM.format(cal.getTime());
			}
			break;

		// 20日締め
		case 2:
			comparisonDateFrom = preMonth + "/21";
			comparisonDateTo = curMonth + "/20";

			// 締日の範囲から今日がいつの請求月になるかを判定し、請求月と前月請求月を算出する。
			if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
				demandMonth = curMonth;
				preDemandMonth = preMonth;
			} else if (comparisonDateTo.compareTo(today) < 0) {
				demandMonth = nextMonth;
				preDemandMonth = curMonth;
			} else {
				demandMonth = preMonth;
				cal.add(Calendar.MONTH, -2);
				preDemandMonth = formatYM.format(cal.getTime());
			}
			break;

		// 15日締め
		case 3:
			comparisonDateFrom = curMonth + "/16";
			comparisonDateTo = nextMonth + "/15";

			// 締日の範囲から今日がいつの請求月になるかを判定し、請求月と前月請求月を算出する。
			if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
				demandMonth = curMonth;
				preDemandMonth = preMonth;
			} else if (comparisonDateTo.compareTo(today) < 0) {
				demandMonth = nextMonth;
				preDemandMonth = curMonth;
			} else {
				demandMonth = preMonth;
				cal.add(Calendar.MONTH, -2);
				preDemandMonth = formatYM.format(cal.getTime());
			}
			break;

		// 10日締め
		case 4:
			comparisonDateFrom = curMonth + "/11";
			comparisonDateTo = nextMonth + "/10";

			// 締日の範囲から今日がいつの請求月になるかを判定し、請求月と前月請求月を算出する。
			if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
				demandMonth = curMonth;
				preDemandMonth = preMonth;
			} else if (comparisonDateTo.compareTo(today) < 0) {
				demandMonth = nextMonth;
				preDemandMonth = curMonth;
			} else {
				demandMonth = preMonth;
				cal.add(Calendar.MONTH, -2);
				preDemandMonth = formatYM.format(cal.getTime());
			}
			break;

		// 5日締め
		case 5:
			comparisonDateFrom = curMonth + "/06";
			comparisonDateTo = nextMonth + "/05";

			// 締日の範囲から今日がいつの請求月になるかを判定し、請求月と前月請求月を算出する。
			if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
				demandMonth = curMonth;
				preDemandMonth = preMonth;
			} else if (comparisonDateTo.compareTo(today) < 0) {
				demandMonth = nextMonth;
				preDemandMonth = curMonth;
			} else {
				demandMonth = preMonth;
				cal.add(Calendar.MONTH, -2);
				preDemandMonth = formatYM.format(cal.getTime());
			}
			break;

		}
		/*****
		 * 締日と本日日付から請求月を決定する処理 END
		 *************************************************************/

		// 本店・支店ありの時、繰越額・請求額・入金額を支店全てと本店で合算するのに使用
		// 繰越金額合算用
		int sumCarryOverAmount = 0;
		// 請求額合算用
		int sumBillAmount = 0;
		// 入金額合算用
		int sumReceivePrice = 0;

		// 既存の入金管理レコード
		ExtendPaymentManagementDTO prePayManageDTO = new ExtendPaymentManagementDTO();
		// 前月の請求書レコード
		ExtendCorporateBillDTO lastDemandMonthCorpBillDTO = new ExtendCorporateBillDTO();

		// 入金管理レコードが存在しないとき新規登録するDTO
		ExtendPaymentManagementDTO newPayManageDTO = new ExtendPaymentManagementDTO();

		for (int i = 0; i < clientIdList.size(); i++) {

			long clientId = clientIdList.get(i); // 得意先ID
			/*
			 * 本店・支店ありなら、本店の得意先IDで行う処理があるため分岐
			 * 以後、本店・支店ありなら本店IDはclientId、各支店IdはclientIdList.get(i)
			 * 既存の入金管理レコードは本店IDで、業販伝票は各支店IDで検索
			 */
			if (isBranch) {
				clientId = billingDstId;
			}

			// 作成した伝票と同じ法人ID、得意先ID、口座ID、請求月で入金管理テーブルを検索
			// 本店・支店ありの場合、本店の入金管理テーブルを取得
			prePayManageDTO = getPaymentManagement(corporateSalesSlipDTO.getSysCorporationId(), clientId,
					corporateSalesSlipDTO.getSysAccountId(), demandMonth);

			// 前月の請求書情報を取得
			// 本店・支店ありの場合、本店の情報を取得
			lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(
					corporateSalesSlipDTO.getSysCorporationId(), preDemandMonth,
					clientDAO.getClient(clientId).getClientNm(), corporateSalesSlipDTO.getSysAccountId());

			// 入金管理レコードが存在しないとき新規登録
			if (prePayManageDTO == null) {

				// 法人ID
				newPayManageDTO.setSysCorporationId(corporateSalesSlipDTO.getSysCorporationId());
				// 得意先ID
				newPayManageDTO.setSysClientId(clientIdList.get(i)); // 支店ありの時、各支店のIDをセット
				// 口座ID
				newPayManageDTO.setSysAccountId(corporateSalesSlipDTO.getSysAccountId());
				// 請求月
				newPayManageDTO.setDemandMonth(demandMonth);
				// 入金日
				newPayManageDTO.setReceiveDate(corporateSalesSlipDTO.getReceiveDate());

				/****
				 * 請求額計算 START
				 **********************************************************/
				// 繰越金額
				int carryOverAmount = 0;
				// 請求月内の伝票の売上合計
				int sumClaimPrice = 0;
				long sysAccountId;
				if (lastDemandMonthCorpBillDTO != null) {
					sysAccountId = lastDemandMonthCorpBillDTO.getSysAccountId();
				} else {
					sysAccountId = 0;
				}

				// 更新対象請求書の口座IDが無い場合：0(商品が0件だが、繰越請求額だけある請求書)
				if (sysAccountId == CODE_COMPARISON_ZERO) {
					// 法人IDから優先表示が高いものを1件取得
					sysAccountId = accountDAO.getAccountList(corporateSalesSlipDTO.getSysCorporationId())
							.get(INDEX_ZERO_SET).getSysAccountId();
					// 口座IDをもとに前月請求額を取得
					lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchCorporateBill(
							clientDAO.getClient(clientIdList.get(i)).getClientNm(), preDemandMonth, sysAccountId);
					if (lastDemandMonthCorpBillDTO != null) {
						// 口座ID指定で取得できない場合、法人ID、法人名、前回請求月で取得する
						if (lastDemandMonthCorpBillDTO.getBillAmount() == AMOUNT_ZERO_SET) {
							lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(
									corporateSalesSlipDTO.getSysCorporationId(), preDemandMonth,
									clientDAO.getClient(clientIdList.get(i)).getClientNm());
							// 前月請求月を設定
							if (lastDemandMonthCorpBillDTO == null) {
								carryOverAmount = AMOUNT_ZERO_SET;
							} else {
								carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
							}
						} else {
							carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
						}
					} else {
						carryOverAmount = AMOUNT_ZERO_SET;
					}
				} else {
					sysAccountId = corporateSalesSlipDTO.getSysAccountId();
					// 口座IDを元に前月請求額を取得
					lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchCorporateBill(
							clientDAO.getClient(clientIdList.get(i)).getClientNm(), preDemandMonth, sysAccountId);
					if (lastDemandMonthCorpBillDTO != null) {
						if (lastDemandMonthCorpBillDTO.getBillAmount() == AMOUNT_ZERO_SET) {
							lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(
									corporateSalesSlipDTO.getSysCorporationId(), preDemandMonth,
									clientDTO.getClientNm());
							if (lastDemandMonthCorpBillDTO != null) {
								if (lastDemandMonthCorpBillDTO.getSysAccountId() != sysAccountId
										&& lastDemandMonthCorpBillDTO.getSysAccountId() != 0) {
									carryOverAmount = AMOUNT_ZERO_SET;
								} else {
									carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
								}
							} else {
								carryOverAmount = AMOUNT_ZERO_SET;
							}
						} else {
							carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
						}
					} else {
						carryOverAmount = AMOUNT_ZERO_SET;
					}
				}
				// 繰越金額を加算
				sumCarryOverAmount += carryOverAmount;

				/*
				 * 繰越金額 計算END
				 */

				/*
				 * 請求額 計算START
				 */
				// 請求月内の業販伝票商品の合計金額を求める処理
				List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList = new ArrayList<ExtendCorporateSalesSlipDTO>();
				List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList = new ArrayList<SysCorporateSalesSlipIdDTO>();
				CorporateSaleSearchDTO searchDTO = new CorporateSaleSearchDTO();
				CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

				// 締日に対応した対象月内で受注された伝票を全て取得する。
				/*
				 * 【検索条件】 ・ステータスが受注伝票 ・法人ID ・得意先ID ・出庫予定日FROM ・出庫予定日TO
				 *  追加：支払方法が「掛売」
				 */
				//掛売
				searchDTO.setPaymentMethod("1"); // 掛売
				searchDTO.setSlipStatus("2"); // 受注伝票
				searchDTO.setSearchPriority("1");
				searchDTO.setSearchAllFlg("on");
				searchDTO.setReturnFlg("off");
				searchDTO.setSysCorporationId(corporateSalesSlipDTO.getSysCorporationId());
				searchDTO.setSysClientId(clientIdList.get(i)); // 本店・支店ありなら支店のId
				searchDTO.setScheduledLeavingDateFrom(comparisonDateFrom);
				searchDTO.setScheduledLeavingDateTo(comparisonDateTo);
				corporateSaleDisplayService.setFlags(searchDTO);

				// 今月請求対象の業販伝票IDを取得
				// 業販伝票に「出庫予定日」の入力が無いと取得結果０件になる
				sysCorporateSalesSlipIdList = corporateSaleDAO.getSearchCorporateSalesSlipList(searchDTO);

				//業販伝票IDがなければ以後の請求額、入金は終了。
				//あればフラグを立てる
				if (sysCorporateSalesSlipIdList != null && sysCorporateSalesSlipIdList.size() != 0) {
					isExistSlip = true;
				}

				// 業販伝票IDのListから業販伝票リストを作成
				for (SysCorporateSalesSlipIdDTO slipId : sysCorporateSalesSlipIdList) {
					ExtendCorporateSalesSlipDTO dto = new ExtendCorporateSalesSlipDTO();
					dto.setSysCorporateSalesSlipId(slipId.getSysCorporateSalesSlipId());
					dto = corporateSaleDAO.getCorporateSaleSlip(dto);
					corporateSalesSlipList.add(dto);
				}

				for (ExtendCorporateSalesSlipDTO corporateSalesSlip : corporateSalesSlipList) {
					// 作成した伝票と違う口座の場合は計算しない
					// ※上の締日対象月内の伝票検索で既に法人と得意先で検索しているため、口座だけを条件にしている。
					if (corporateSalesSlip.getSysAccountId() != corporateSalesSlipDTO.getSysAccountId()) {
						continue;
					}
					sumClaimPrice = corporateSalesSlip.getSumClaimPrice(); // 請求月内の伝票の売上合計
					sumBillAmount += sumClaimPrice;
				}

				/*
				 * 請求額 計算END
				 */

				/*
				 * 入金額 計算START
				 * 支店・本店がある場合に本店に入金情報を統合するため、全支店と本店の各伝票idに紐付く入金額を取得して合算する
				 * 入金日は、比較した中で最も遅い日付をreceiveDateに格納し、登録処理の直前でセットしている
				 */
				List<CorporateReceiveDTO> receiveList = new ArrayList<CorporateReceiveDTO>();
				for (int j = 0; j < sysCorporateSalesSlipIdList.size(); j++) {

					receiveList = receiveDAO.getCorporateReceiveList(sysCorporateSalesSlipIdList.get(j).getSysCorporateSalesSlipId(), clientId, demandMonth);

					int receivePrice = 0;
					DateFormat dateformatYMD = new SimpleDateFormat("yyyy/mm/dd", Locale.JAPAN);

					String receiveDateTmp = ""; // 比較用の入金日
					for (int k = 0; k < receiveList.size(); k++) {

						// 入金額
						receivePrice = receiveList.get(k).getReceivePrice();
						sumReceivePrice += receivePrice;

						// 入金日START
						receiveDateTmp = receiveList.get(k).getReceiveDate();

						if (!StringUtils.isEmpty(newPayManageDTO.getReceiveDate())) {
							// 既にセットされた入金日
							receiveDate = newPayManageDTO.getReceiveDate();

							// 入金日の比較
							if (dateformatYMD.parse(receiveDate).after(dateformatYMD.parse(receiveDateTmp))) {
								receiveDate = receiveDateTmp;
							}

						} else { // newPayManageDTO.getReceiveDate()が、nullか空文字の場合
							receiveDate = receiveDateTmp;
						} // 入金日END

					}//入金合算のfor文終了

				} //入金情報のfor文終了

				/*
				 * 入金額計算 END
				 */

				/****
				 * 請求額計算 END
				 ************************************************************/

			}
			// 既存の入金管理レコードprePayManageDTOが存在するとき
			// 出庫時に更新するように変更になった場合はこのelse処理をコメントアウトすること
			// ここより↑は新規の入金管理レコードnewPaymentDTO、↓は既存の入金管理レコードprePayManageDTOに格納し、最後に処理を実行
			else {
				/*
				 * 更新処理 START
				 */

				/*
				 * 繰越金額START
				 */
				int carryOverAmount = 0;
				int sumClaimPrice = 0;
				long sysAccountId;
				if (lastDemandMonthCorpBillDTO != null) {
					sysAccountId = lastDemandMonthCorpBillDTO.getSysAccountId();
				} else {
					sysAccountId = 0;
				}

				// 更新対象請求書の口座IDが無い場合：0(商品が0件だが、請求額だけある請求書)
				if (sysAccountId == CODE_COMPARISON_ZERO) {
					// 法人IDから優先表示が高いものを1件取得
					sysAccountId = accountDAO.getAccountList(corporateSalesSlipDTO.getSysCorporationId())
							.get(INDEX_ZERO_SET).getSysAccountId();
					// 口座IDをもとに前月請求額を取得
					lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchCorporateBill(clientDTO.getClientNm(),
							preDemandMonth, sysAccountId);
					if (lastDemandMonthCorpBillDTO != null) {
						// 口座ID指定で取得できない場合、法人ID、法人名、前回請求月で取得する
						if (lastDemandMonthCorpBillDTO.getBillAmount() == AMOUNT_ZERO_SET) {
							lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(
									corporateSalesSlipDTO.getSysCorporationId(), preDemandMonth,
									clientDTO.getClientNm());
							// 前月請求月を設定
							if (lastDemandMonthCorpBillDTO == null) {
								carryOverAmount = AMOUNT_ZERO_SET;
							} else {
								carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
							}
						} else {
							carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
						}
					} else {
						carryOverAmount = AMOUNT_ZERO_SET;
					}
				} else {
					sysAccountId = corporateSalesSlipDTO.getSysAccountId();
					// 口座IDを元に前月請求額を取得
					lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchCorporateBill(
							clientDAO.getClient(clientIdList.get(i)).getClientNm(), preDemandMonth, sysAccountId);
					if (lastDemandMonthCorpBillDTO != null) {
						if (lastDemandMonthCorpBillDTO.getBillAmount() == AMOUNT_ZERO_SET) {
							lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(
									corporateSalesSlipDTO.getSysCorporationId(), preDemandMonth,
									clientDTO.getClientNm());
							if (lastDemandMonthCorpBillDTO != null) {
								if (lastDemandMonthCorpBillDTO.getSysAccountId() != sysAccountId
										&& lastDemandMonthCorpBillDTO.getSysAccountId() != 0) {
									carryOverAmount = AMOUNT_ZERO_SET;
								} else {
									carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
								}
							} else {
								carryOverAmount = AMOUNT_ZERO_SET;
							}
						} else {
							carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
						}
					} else {
						carryOverAmount = AMOUNT_ZERO_SET;
					}
				}
				// 繰越金額を加算
				sumCarryOverAmount += carryOverAmount;

				/*
				 * 繰越金額 計算END
				 */

				/*
				 * 請求額 計算START
				 */

				// 請求月内の業販伝票商品の合計金額を求める処理
				// 締日に対応した対象月内で受注された伝票を全て取得するための検索条件
				List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList = new ArrayList<ExtendCorporateSalesSlipDTO>();
				List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList = new ArrayList<SysCorporateSalesSlipIdDTO>();
				CorporateSaleSearchDTO searchDTO = new CorporateSaleSearchDTO();
				CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

				// 締日に対応した対象月内で受注された伝票を全て取得する。
				/*
				 * 【検索条件】 ・掛売・受注伝票 ・法人ID ・得意先ID ・出庫予定日FROM ・出庫予定日TO
				 */
				searchDTO.setPaymentMethod("1"); //掛売
				searchDTO.setSlipStatus("2");
				searchDTO.setSearchPriority("1");
				searchDTO.setSearchAllFlg("on");
				searchDTO.setReturnFlg("off");
				searchDTO.setSysCorporationId(corporateSalesSlipDTO.getSysCorporationId());
				searchDTO.setSysClientId(clientIdList.get(i)); // 本店・支店ありなら支店の得意先Id
				searchDTO.setScheduledLeavingDateFrom(comparisonDateFrom);
				searchDTO.setScheduledLeavingDateTo(comparisonDateTo);
				corporateSaleDisplayService.setFlags(searchDTO);
				// 今月請求対象の伝票IDを取得
				// 業販伝票に「出庫予定日」の入力が無いと取得結果０件になる_SQL文参照
				sysCorporateSalesSlipIdList = corporateSaleDAO.getSearchCorporateSalesSlipList(searchDTO);

				//伝票がなければ以後の請求額、入金情報の合算はしない。
				//あればフラグを立てる
				if (sysCorporateSalesSlipIdList != null && sysCorporateSalesSlipIdList.size() != 0) {
					isExistSlip = true;
				}

				// 伝票IDのListから業販伝票リストを作成
				for (SysCorporateSalesSlipIdDTO slipId : sysCorporateSalesSlipIdList) {
					ExtendCorporateSalesSlipDTO dto = new ExtendCorporateSalesSlipDTO();
					dto.setSysCorporateSalesSlipId(slipId.getSysCorporateSalesSlipId());
					dto = corporateSaleDAO.getCorporateSaleSlip(dto);
					corporateSalesSlipList.add(dto);
				}


				// 請求額
				for (ExtendCorporateSalesSlipDTO corporateSalesSlip : corporateSalesSlipList) {
					// 作成した伝票と違う口座の場合は計算しない
					// ※上の締日対象月内の伝票検索で既に法人と得意先で検索しているため、口座だけを条件にしている。
					if (corporateSalesSlip.getSysAccountId() != corporateSalesSlipDTO.getSysAccountId()) {
						continue;
					}
					sumClaimPrice = corporateSalesSlip.getSumClaimPrice(); // 請求月内の伝票の売上合計
					sumBillAmount += sumClaimPrice; // 請求額合算
				}

				/*
				 * 請求額 計算END
				 */

				/*
				 * 入金額 計算START
				 * 支店・本店がある場合に本店に入金情報を統合するため、全支店と本店の各伝票idに紐付く入金額を取得して合算する
				 * 入金日は、比較した中で最も遅い日付をreceiveDateに格納し、更新処理の直前でセットしている
				 */

				// 各伝票ごとの入金情報のリスト
				List<CorporateReceiveDTO> receiveList = new ArrayList<CorporateReceiveDTO>();
				for (int j = 0; j < sysCorporateSalesSlipIdList.size(); j++) {

					receiveList = receiveDAO.getCorporateReceiveList(
							sysCorporateSalesSlipIdList.get(j).getSysCorporateSalesSlipId(), clientId, demandMonth);

					int receivePrice = 0;
					DateFormat dateFormatYMD = new SimpleDateFormat("yyyy/mm/dd", Locale.JAPAN);
					String receiveDateTmp = ""; // 比較用の入金日

					//１つの伝票と紐付く各入金情報を取得し、合算
					for (int k = 0; k < receiveList.size(); k++) {
						// 入金額
						receivePrice = receiveList.get(k).getReceivePrice();
						sumReceivePrice += receivePrice;

						// 入金日START
						receiveDateTmp = receiveList.get(k).getReceiveDate();

						if (!StringUtils.isEmpty(receiveDate)) {

							// 入金日の比較
							if (dateFormatYMD.parse(receiveDate).after(dateFormatYMD.parse(receiveDateTmp))) {
								receiveDate = receiveDateTmp;
							}
						} else { // receiveDateが、nullか空文字の場合
							receiveDate = receiveDateTmp;
						}
						// 入金日END

					} // 合算用のfor文終了

				} // 入金情報のfor文終了

				/*
				 * 入金額 計算END
				 */

			} // if(prePayManageDTO == null) { } else{}の終了

		} // 支店を本店に統合する処理のfor文終了

		/*
		 * 既存の入金管理レコード prePayManageDTOがnullなら新規登録処理、nullでないなら更新処理
		 */
		// 入金管理 新規登録処理
		// 支店ありの場合、本店に統合するので処理をかける得意先IDは本店の得意先IDとする。最後の支店Idで処理がされてしまう。
		if (prePayManageDTO == null) {
			//掛売のもののみ入金管理に載るようにしたい。掛売以外の伝票なら入金管理は作成しない。
			//繰越額が0かつ、掛売の伝票が存在しないなら入金管理を作成しない
			if (sumCarryOverAmount == 0 && isExistSlip == false) {
				return;
			}


			if (isBranch) { // 本店の得意先Id、本店の得意先名で更新するため
				newPayManageDTO.setSysClientId(billingDstId);
				newPayManageDTO.setClientNm(billingDstNm);

			}

			// 入金TBLのシステム入金管理IDのシーケンス ※入金TBLの仕様上プライマリキーを複数持っているため
			long sysPaymentManagementId = getSequenceOfPaymentManagement() + 1;
			// システム入金管理ID
			newPayManageDTO.setSysPaymentManagementId(sysPaymentManagementId);

			newPayManageDTO.setReceiveDate(receiveDate); // 入金日：最新の（最も遅い）日付
			newPayManageDTO.setReceivePrice(sumReceivePrice); // 総入金額
			newPayManageDTO.setCarryOverAmount(sumCarryOverAmount); // 総繰越金額
			newPayManageDTO.setBillAmount(sumCarryOverAmount + sumBillAmount); // 請求額 = 繰越金額 + 請求月内の業販伝票商品の金額計

			registryPaymentManagement(newPayManageDTO);
		}
		// 入金管理 更新処理
		else {
			if (isBranch) { // 本店のシステム入金管理Id、本店の得意先Id、本店の得意先名で更新するため

				CorporatePaymentManagementDAO paymentManagementDAO = new CorporatePaymentManagementDAO();
				headPaymentManagementId = paymentManagementDAO
						.getPaymentManagement(corporateSalesSlipDTO.getSysCorporationId(), billingDstId,
								corporateSalesSlipDTO.getSysAccountId(), demandMonth)
						.getSysPaymentManagementId();
				prePayManageDTO.setSysPaymentManagementId(headPaymentManagementId);

				prePayManageDTO.setSysClientId(billingDstId);
				prePayManageDTO.setClientNm(billingDstNm);

			}
			prePayManageDTO.setReceiveDate(receiveDate); //入金日：最新の（最も遅い）日付
			prePayManageDTO.setReceivePrice(sumReceivePrice); // 総入金額
			prePayManageDTO.setCarryOverAmount(sumCarryOverAmount); // 総繰越金額

			// 請求額は以下のため、新規登録処理とは違う計算を行う
			// 入金額がもともと入力されていた時、最新の請求額を求めた後に入金額分を減算する。繰越金額を変更してしまうと入金額がもともと入力されていたものより低い金額に編集された時元に戻らない。
			int preMonthBillAmount = prePayManageDTO.getCarryOverAmount() - (prePayManageDTO.getReceivePrice()
					+ prePayManageDTO.getCharge() + prePayManageDTO.getCharge2() + prePayManageDTO.getCharge3());
			prePayManageDTO.setBillAmount(sumBillAmount + preMonthBillAmount); // 請求額 = 請求月内の業販伝票商品の金額計 + preMonthBillAmount

			//入金管理更新処理
			updatePaymentManagement(prePayManageDTO);
		}

	}


	/*
	 * 業販請求書作成時に、繰越額があれば「次月の入金管理」を作成するメソッド
	 * 次月の入金管理が無ければ、引数で渡された繰越額をセットし次月の入金管理を新規作成する処理。
	 *
	 * @param corporateSalesSlipDTO
	 * @param nextMonth
	 * @param nextCarryOverAmount
	 * @author boncre t_yamamura 作成
	 */
	public void registerPaymentInformation(ExtendCorporateSalesSlipDTO corporateSalesSlipDTO, String nextMonth, int nextCarryOverAmount) throws Exception {

				// その他必要なインスタンス
				ClientDAO clientDAO = new ClientDAO();

				/*
				 * 得意先が支店である場合、全支店と本店の入金情報を本店に統合する。
				 * 登録or更新する得意先IDを本店の得意先IDで上書きし、本店の入金管理情報として登録・更新する。
				 */
				// 得意先Idを格納するList
				List<Long> clientIdList = new ArrayList<Long>();

				// 得意先情報取得
				MstClientDTO clientDTO = clientDAO.getClient(corporateSalesSlipDTO.getSysClientId());
				// 支店チェック
				boolean isBranch = false;
				// 請求先カラムに格納されている本店の得意先名
				String billingDstNm = "";
				// 本店のsys_client_id 得意先ID
				long billingDstId = 0;

				// 対象の伝票が存在する場合のフラグ。請求額と入金情報を合算する、掛売かつ受注伝票が対象。
				boolean isExistSlip = false;

				/*
				 * 支店がある場合は、以下のカラムに値がある（nullもしくは空文字ではない）
				 * mst_clientテーブルのbilling_dst(請求先)カラム
				 */
				if (!StringUtils.isEmpty(clientDTO.getBillingDst())) {
					billingDstNm = clientDTO.getBillingDst();
					if (!StringUtils.isEmpty(billingDstNm)) {
						billingDstId = Long.parseLong(clientDAO.getClientNmWithName(billingDstNm, clientDTO.getSysCorporationId()));
					}
					// 支店ありのため支店チェックをtrueにする
					isBranch = true;
					// 請求先カラムに入っている同一の文字列は、本店の得意先名。
					// 請求額や入金額を合算し本店に統合するために、本店と各支店の得意先IdをListで持つ
					clientIdList = clientDAO.getSysClientIdList(clientDTO.getBillingDst());

				} else { // 本店・支店のない場合
					clientIdList.add(clientDTO.getSysClientId());
				}

				// 来月の入金管理レコード
				ExtendPaymentManagementDTO nextPayManageDTO = new ExtendPaymentManagementDTO();

				if (nextCarryOverAmount == 0 && isExistSlip == false) {
					return;
				}
				//次月の入金管理を作成するため次月を入れる
				nextPayManageDTO.setDemandMonth(nextMonth);
				// 法人ID
				nextPayManageDTO.setSysCorporationId(corporateSalesSlipDTO.getSysCorporationId());
				// 口座ID
				nextPayManageDTO.setSysAccountId(corporateSalesSlipDTO.getSysAccountId());
				// 入金TBLのシステム入金管理IDのシーケンス ※入金TBLの仕様上プライマリキーを複数持っているため
				long sysPaymentManagementId = getSequenceOfPaymentManagement() + 1;
				// システム入金管理ID
				nextPayManageDTO.setSysPaymentManagementId(sysPaymentManagementId);

				nextPayManageDTO.setSysClientId(corporateSalesSlipDTO.getSysClientId());
				nextPayManageDTO.setClientNm(clientDAO.getClient(corporateSalesSlipDTO.getSysClientId()).getClientNm());

				if (isBranch) { // 本店の得意先Id、本店の得意先名で更新するため
					nextPayManageDTO.setSysClientId(billingDstId);
					nextPayManageDTO.setClientNm(billingDstNm);
				}
				nextPayManageDTO.setCarryOverAmount(nextCarryOverAmount); // 繰越金額
				nextPayManageDTO.setBillAmount(nextCarryOverAmount); // 次月の請求金額=(次月の総売上金額：まだ無い)＋繰越金額

				registryPaymentManagement(nextPayManageDTO);
	}

	/**
	 * 出庫された商品をもとに入金管理データを更新する
	 * <h1>現在未使用 請求書の概念に合わせる時使用</h1>
	 * 請求書の概念とは？：<br>
	 * 請求書の金額は本来売り上げた(出庫された)商品の金額の合計である。<br>
	 * よって伝票が作られた時点では請求月と請求額が算出できず、出庫されてから初めて請求月と請求額が求められる。<br>
	 * ただし、実装時は請求月内で作成された業販伝票は全て出庫される前提であったため作成時から請求額と月を算出した。
	 *
	 * @param resultObject(corporateSalesSlipItemList)
	 */
//	public void updatePaymentManagementInformation(List<ExtendCorporateSalesItemDTO> corporateSalesSlipItemList)
//			throws Exception {
//
//		//業販伝票リスト
//		List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList = new ArrayList<>();
//		//その他必要なインスタンス
//		CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
//		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();
//		ClientDAO clientDAO = new ClientDAO();
//		AccountDAO accountDAO = new AccountDAO();
//
//		//出庫された伝票商品リストから業販伝票リストを作成する
//		for (ExtendCorporateSalesItemDTO itemDTO : corporateSalesSlipItemList) {
//
//			ExtendCorporateSalesSlipDTO corporateSalesSlipDTO = new ExtendCorporateSalesSlipDTO();
//
//			corporateSalesSlipDTO.setSysCorporateSalesSlipId(itemDTO.getSysCorporateSalesSlipId());
//			corporateSalesSlipDTO = corporateSaleDAO.getCorporateSaleSlip(corporateSalesSlipDTO);
//
//			corporateSalesSlipList.add(corporateSalesSlipDTO);
//		}
//
//		for (ExtendCorporateSalesSlipDTO corporateSalesSlipDTO : corporateSalesSlipList) {
//
//			//入金管理で使用する伝票の各データ
//			long sysCorporationId = corporateSalesSlipDTO.getSysCorporationId();
//			long sysAccountId = corporateSalesSlipDTO.getSysAccountId();
//			long sysClientId = corporateSalesSlipDTO.getSysClientId();
//
//			//得意先情報取得
//			MstClientDTO clientDTO = clientDAO.getClient(sysClientId);
//
//			//得意先の締日ごとに請求月を指定する処理
//			//今月請求月と前月請求月
//			String demandMonth = null;
//			String preDemandMonth = null;
//			//日付形式
//			SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
//			SimpleDateFormat formatYM = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
//			//日本の現在時間設定
//			Calendar cal = Calendar.getInstance(Locale.JAPAN);
//			//本日日付 yyyy/MM/dd
//			String today = formatYMD.format(cal.getTime());
//			//今月 yyyy/MM
//			String curMonth = formatYM.format(cal.getTime());
//			//前月 yyyy/MM
//			cal.add(Calendar.MONTH, -1);
//			String preMonth = formatYM.format(cal.getTime());
//			//来月 yyyy/MM
//			cal = Calendar.getInstance(Locale.JAPAN);
//			cal.add(Calendar.MONTH, 1);
//			String nextMonth = formatYM.format(cal.getTime());
//
//			//日付リセット
//			cal = Calendar.getInstance(Locale.JAPAN);
//
//			//締日
//			int CUTOFF_DATE = clientDTO.getCutoffDate();
//
//			//本日日付と締日から請求月と前月請求月を求める
//			//比較に使用する日付
//			String comparisonDateFrom = "";
//			String comparisonDateTo = "";
//
//			switch (CUTOFF_DATE) {
//			//末日締め
//			case 0:
//				comparisonDateFrom = curMonth + "/01";
//				comparisonDateTo = curMonth + "/" + cal.getActualMaximum(Calendar.DATE);
//
//				//今日の日付が指定範囲内かチェックする
//				if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
//					demandMonth = curMonth;
//					preDemandMonth = preMonth;
//				} else if (comparisonDateTo.compareTo(today) < 0) {
//					demandMonth = nextMonth;
//					preDemandMonth = curMonth;
//				} else {
//					demandMonth = preMonth;
//					cal.add(Calendar.MONTH, -2);
//					preDemandMonth = formatYM.format(cal.getTime());
//				}
//				break;
//
//			//25日締め
//			case 1:
//				comparisonDateFrom = preMonth + "/26";
//				comparisonDateTo = curMonth + "/25";
//
//				//今日の日付が指定範囲内かチェックする
//				if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
//					demandMonth = curMonth;
//					preDemandMonth = preMonth;
//				} else if (comparisonDateTo.compareTo(today) < 0) {
//					demandMonth = nextMonth;
//					preDemandMonth = curMonth;
//				} else {
//					demandMonth = preMonth;
//					cal.add(Calendar.MONTH, -2);
//					preDemandMonth = formatYM.format(cal.getTime());
//				}
//				break;
//
//			//20日締め
//			case 2:
//					//今月初
//					comparisonDateFrom = preMonth + "/21";
//					//今月末
//					comparisonDateTo = curMonth + "/20";
//
//					//今日の日付が指定範囲内かチェックする
//					if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
//						demandMonth = curMonth;
//						preDemandMonth = preMonth;
//					} else if (comparisonDateTo.compareTo(today) < 0) {
//						demandMonth = nextMonth;
//						preDemandMonth = curMonth;
//					} else {
//						demandMonth = preMonth;
//						cal.add(Calendar.MONTH, -2);
//						preDemandMonth = formatYM.format(cal.getTime());
//					}
//				break;
//
//			//15日締め
//			case 3:
//				//今月初
//				comparisonDateFrom = preMonth + "/16";
//				//今月末
//				comparisonDateTo = curMonth + "/15";
//
//				//今日の日付が指定範囲内かチェックする
//				if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
//					demandMonth = curMonth;
//					preDemandMonth = preMonth;
//				} else if (comparisonDateTo.compareTo(today) < 0) {
//					demandMonth = nextMonth;
//					preDemandMonth = curMonth;
//				} else {
//					demandMonth = preMonth;
//					cal.add(Calendar.MONTH, -2);
//					preDemandMonth = formatYM.format(cal.getTime());
//				}
//				break;
//
//			//10日締め
//			case 4:
//				//今月初
//				comparisonDateFrom = preMonth + "/11";
//				//今月末
//				comparisonDateTo = curMonth + "/10";
//
//				//今日の日付が指定範囲内かチェックする
//				if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
//					demandMonth = curMonth;
//					preDemandMonth = preMonth;
//				} else if (comparisonDateTo.compareTo(today) < 0) {
//					demandMonth = nextMonth;
//					preDemandMonth = curMonth;
//				} else {
//					demandMonth = preMonth;
//					cal.add(Calendar.MONTH, -2);
//					preDemandMonth = formatYM.format(cal.getTime());
//				}
//				break;
//
//			//5日締め
//			case 5:
//				//今月初
//				comparisonDateFrom = preMonth + "/06";
//				//今月末
//				comparisonDateTo = curMonth + "/05";
//
//				//今日の日付が指定範囲内かチェックする
//				if (comparisonDateFrom.compareTo(today) <= 0 && comparisonDateTo.compareTo(today) >= 0) {
//					demandMonth = curMonth;
//					preDemandMonth = preMonth;
//				} else if (comparisonDateTo.compareTo(today) < 0) {
//					demandMonth = nextMonth;
//					preDemandMonth = curMonth;
//				} else {
//					demandMonth = preMonth;
//					cal.add(Calendar.MONTH, -2);
//					preDemandMonth = formatYM.format(cal.getTime());
//				}
//				break;
//
//			}
//
//
//			//既に登録されている入金情報と前月の請求書情報
//			ExtendPaymentManagementDTO prePayManageDTO = new ExtendPaymentManagementDTO();
//			ExtendCorporateBillDTO lastDemandMonthCorpBillDTO = new ExtendCorporateBillDTO();
//
//			//DBの入金管理レコードを検索
//			prePayManageDTO = getPaymentManagement(sysCorporationId, sysClientId, sysAccountId, demandMonth);
//			//前月の請求書情報を取得
//			lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(sysCorporationId, preDemandMonth, clientDTO.getClientNm());
//
//			//入金レコードが存在するとき更新処理
//			if (prePayManageDTO != null) {
//
//				//前月の請求額に業販伝票の売上額を加算
//				prePayManageDTO.setBillAmount(prePayManageDTO.getBillAmount() + corporateSalesSlipDTO.getSumClaimPrice());
//
//				//入金管理更新処理
//				updatePaymentManagement(prePayManageDTO);
//
//			//存在するしないとき新規登録する
//			} else {
//
//				ExtendPaymentManagementDTO newPayManageDTO = new ExtendPaymentManagementDTO();
//				//入金TBLのシステム入金管理IDのシーケンス ※入金TBLの仕様、構造上プライマリキーを複数持っているため
//				long sysPaymentManagementId = getSequenceOfPaymentManagement() + 1;
//
//				//システム入金管理ID
//				newPayManageDTO.setSysPaymentManagementId(sysPaymentManagementId);
//				//法人ID
//				newPayManageDTO.setSysCorporationId(sysCorporationId);
//				//得意先ID
//				newPayManageDTO.setSysClientId(sysClientId);
//				//口座ID
//				newPayManageDTO.setSysAccountId(sysAccountId);
//				//請求月
//				newPayManageDTO.setDemandMonth(demandMonth);
//				/**** 請求額計算 START **********************************************************/
//				int carryOverAmount = 0;
//
//				//更新対象請求書の口座IDが無い場合：0(商品が0件)
//				if (lastDemandMonthCorpBillDTO == null || lastDemandMonthCorpBillDTO.getSysAccountId() == CODE_COMPARISON_ZERO) {
//					//法人IDから優先表示が高いものを1件取得
//					sysAccountId = accountDAO.getAccountList(corporateSalesSlipDTO.getSysCorporationId()).get(INDEX_ZERO_SET).getSysAccountId();
//					//口座IDをもとに前月請求額を取得
//					lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchCorporateBill(clientDTO.getClientNm(), preDemandMonth, sysAccountId);
//					if (lastDemandMonthCorpBillDTO != null) {
//						//口座ID指定で取得できない場合、法人ID、法人名、前回請求月で取得する
//						if (lastDemandMonthCorpBillDTO.getBillAmount() == AMOUNT_ZERO_SET) {
//							lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(corporateSalesSlipDTO.getSysCorporationId(), preDemandMonth, clientDTO.getClientNm());
//							//前月請求月を設定
//							if (lastDemandMonthCorpBillDTO == null) {
//								carryOverAmount = AMOUNT_ZERO_SET;
//							} else {
//								carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
//							}
//						} else {
//							carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
//						}
//					} else {
//						carryOverAmount = AMOUNT_ZERO_SET;
//					}
//				} else {
//					sysAccountId = corporateSalesSlipDTO.getSysAccountId();
//					//口座IDを元に前月請求額を取得
//					lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchCorporateBill(clientDTO.getClientNm(), preDemandMonth, sysAccountId);
//					if (lastDemandMonthCorpBillDTO != null) {
//						if (lastDemandMonthCorpBillDTO.getBillAmount() == AMOUNT_ZERO_SET) {
//							lastDemandMonthCorpBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(corporateSalesSlipDTO.getSysCorporationId(), preDemandMonth, clientDTO.getClientNm());
//							if (lastDemandMonthCorpBillDTO != null) {
//								if (lastDemandMonthCorpBillDTO.getSysAccountId() != sysAccountId && lastDemandMonthCorpBillDTO.getSysAccountId() != 0) {
//									carryOverAmount = AMOUNT_ZERO_SET;
//								} else {
//									carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
//								}
//							} else {
//								carryOverAmount = AMOUNT_ZERO_SET;
//							}
//						} else {
//							carryOverAmount = lastDemandMonthCorpBillDTO.getBillAmount();
//						}
//					} else {
//						carryOverAmount = AMOUNT_ZERO_SET;
//					}
//				}
//				//繰越金額
//				newPayManageDTO.setCarryOverAmount(carryOverAmount);
//				//請求額 ※ここでは既に売り上げた商品の伝票なので伝票の金額も加算する
//				newPayManageDTO.setBillAmount(carryOverAmount + corporateSalesSlipDTO.getSumClaimPrice());
//				/**** 請求額計算 END ************************************************************/
//
//				//入金管理情報新規登録
//				registryPaymentManagement(newPayManageDTO);
//
//			}
//		}
//
//	}

	/**
	 * 入金管理テーブルにおけるシステム入金管理IDの最大値を取得する
	 *
	 * @return
	 * @throws DaoException
	 */
	private int getSequenceOfPaymentManagement() throws DaoException {
		return new CorporatePaymentManagementDAO().getSequenceOfPaymentManagement();
	}

	/**
	 * 法人IDから入金管理情報IDリストを取得する
	 *
	 * @param sysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendPaymentManagementDTO> getSysPaymentManageIdList(long sysCorporationId) throws DaoException {
		return new CorporatePaymentManagementDAO().getSysPaymentManageIdList(sysCorporationId);
	}

	/**
	 * 検索条件をもとに入金管理情報IDリストを取得する
	 *
	 * @param sysCorporationId
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendPaymentManagementDTO> getSysPaymentManageIdList(CorporateSaleSearchDTO searchDTO)
			throws DaoException {
		return new CorporatePaymentManagementDAO().getSysPaymentManageIdList(searchDTO);
	}

	/**
	 * <h1>入金管理初期表示</h1>
	 * 入金管理情報を取得する
	 * @param sysCorporationId
	 * @param sysClientId
	 * @param sysAccountId
	 * @param demandMonth
	 * @return ExtendPaymentManagementDTO
	 * @throws DaoException
	 */
	public ExtendPaymentManagementDTO getPaymentManagement(long sysCorporationId, long sysClientId, long sysAccountId, String demandMonth)
			throws DaoException {
		return new CorporatePaymentManagementDAO().getPaymentManagement(sysCorporationId, sysClientId, sysAccountId, demandMonth);
	}

	/**
	 * <h1>入金管理ページング処理、検索処理</h1>
	 * 入金管理情報IDリストと表示件数をもとに1ページに表示する入金情報を取得する
	 * @param sysPaymentManageIdList
	 * @param pageIdx
	 * @param pageMax
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendPaymentManagementDTO> getPaymentManagement(List<ExtendPaymentManagementDTO> sysPaymentManageIdList, int pageIdx, int pageMax)
			throws DaoException {

		List<ExtendPaymentManagementDTO> paymentManageList = new ArrayList<ExtendPaymentManagementDTO>();

		CorporatePaymentManagementDAO dao = new CorporatePaymentManagementDAO();

		for (int i = pageMax * pageIdx;
				i < pageMax * (pageIdx + 1)
				&& i < sysPaymentManageIdList.size();i++) {
			ExtendPaymentManagementDTO dto = dao.getSearchPaymentManage(sysPaymentManageIdList.get(i).getSysPaymentManagementId());
			paymentManageList.add(dto);
		}

		return paymentManageList;
	}

	/**
	 * 入金管理情報を新規登録する
	 *
	 * @param newPayManageDTO
	 * @throws DaoException
	 */
	public void registryPaymentManagement(ExtendPaymentManagementDTO newPayManageDTO)
			throws DaoException {
		new CorporatePaymentManagementDAO().registryPaymentManagement(newPayManageDTO);
	}

	/**
	 * 入金管理情報を更新する<br>
	 * 業販伝票作成時に入金管理情報があった場合に使用している更新処理
	 *
	 * @param newPayManageDTO
	 * @throws DaoException
	 */
	public void updatePaymentManagement(ExtendPaymentManagementDTO prePayManageDTO)
			throws DaoException {
		new CorporatePaymentManagementDAO().updatePaymentManagement(prePayManageDTO);
	}

	/**
	 * 入金画面の入金額、入金日、フリーワード、金額を更新する<br>
	 * 入金管理画面で「編集を有効にする」ボタン押下時に使用している更新処理<br>
	 * 入金管理TBL更新後、それに紐付く請求書TBLのレコードも一緒に更新することで連動を図る
	 *
	 * @param paymentManageDTOList
	 */
	public void updatePaymentManagement(List<ExtendPaymentManagementDTO> paymentManageDTOList)
			throws Exception {

		TransactionDAO transactionDAO = new TransactionDAO();
		CorporateBillDAO corporateBillDAO = new CorporateBillDAO();
		CorporateSaleDAO corporateSaleDAO = new CorporateSaleDAO();
		ClientDAO clientDAO = new ClientDAO();

		transactionDAO.begin();

		// 1件ずつ更新処理
		for (ExtendPaymentManagementDTO payManageDTO : paymentManageDTOList) {

			// 請求月と締日に紐付く業販伝票商品を取得する際に使用するカレンダークラスのインスタンス
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM", Locale.JAPAN);
			Calendar fromCal = Calendar.getInstance(Locale.JAPAN);
			Calendar toCal = Calendar.getInstance(Locale.JAPAN);
			String preMonth = payManageDTO.getDemandMonth() + "/01";
			String nextMonth = payManageDTO.getDemandMonth() + "/01";
			Date convertPreMonth = dateFormat.parse(preMonth);
			Date convertNextMonth = dateFormat.parse(nextMonth);
			fromCal.setTime(convertPreMonth);
			fromCal.add(Calendar.MONTH, -1);
			toCal.setTime(convertNextMonth);
			toCal.add(Calendar.MONTH, 1);
			preMonth = dateFormat.format(fromCal.getTime());
			nextMonth = dateFormat.format(toCal.getTime());
			String demandMonth = payManageDTO.getDemandMonth();

			// 得意先を取得
			MstClientDTO client = new MstClientDTO();
			client = clientDAO.getClient(payManageDTO.getSysClientId());
			// 締日を取得
			int CUTOFF_DATA = client.getCutoffDate();

			String fromDate = "";
			String toDate = "";

			// 末日締め、25日締め、20日締めの時は前月～今月、15日締め、10日締め、5日締めの時は今月～次月が対象となる
			switch (CUTOFF_DATA) {
			case 0:
				fromDate = demandMonth + "/01";
				toCal = Calendar.getInstance(Locale.JAPAN);
				toDate = demandMonth + "/" + toCal.getActualMaximum(Calendar.DATE);
				break;
			case 1:
				fromDate = preMonth + "/26";
				toDate = demandMonth + "/25";
				break;
			case 2:
				fromDate = preMonth + "/21";
				toDate = demandMonth + "/20";
				break;
			case 3:
				fromDate = demandMonth + "/16";
				toDate = nextMonth + "/15";
				break;
			case 4:
				fromDate = demandMonth + "/11";
				toDate = nextMonth + "/10";
				break;
			case 5:
				fromDate = demandMonth + "/06";
				toDate = nextMonth + "/05";
				break;
			}

			// 請求月内の業販伝票商品の合計金額を求める処理
			// 締日に対応した対象月内で受注された伝票を全て取得するための検索条件
			List<ExtendCorporateSalesSlipDTO> corporateSalesSlipList = new ArrayList<ExtendCorporateSalesSlipDTO>();
			CorporateSaleSearchDTO searchDTO = new CorporateSaleSearchDTO();
			CorporateSaleDisplayService corporateSaleDisplayService = new CorporateSaleDisplayService();

			// 締日に対応した対象月内で受注された伝票を全て取得する。
			/*
			 * 【検索条件】
			 * ・掛売
			 * ・受注伝票
			 * ・法人ID
			 * ・得意先ID
			 * ・出庫予定日FROM
			 * ・出庫予定日TO
			 * */
			searchDTO.setPaymentMethod("1"); //掛売
			searchDTO.setSlipStatus("2"); //受注
			searchDTO.setSearchPriority("1");
			searchDTO.setSearchAllFlg("on");
			searchDTO.setReturnFlg("off");
			searchDTO.setSysCorporationId(payManageDTO.getSysCorporationId()); //法人ID
			searchDTO.setSysClientId(payManageDTO.getSysClientId()); //得意先ID
			searchDTO.setScheduledLeavingDateFrom(fromDate);
			searchDTO.setScheduledLeavingDateTo(toDate);
			corporateSaleDisplayService.setFlags(searchDTO);
			List<SysCorporateSalesSlipIdDTO> sysCorporateSalesSlipIdList = corporateSaleDAO.getSearchCorporateSalesSlipList(searchDTO);
			for (SysCorporateSalesSlipIdDTO slipId : sysCorporateSalesSlipIdList) {
				ExtendCorporateSalesSlipDTO dto = new ExtendCorporateSalesSlipDTO();
				dto.setSysCorporateSalesSlipId(slipId.getSysCorporateSalesSlipId());
				dto = corporateSaleDAO.getCorporateSaleSlip(dto);
				corporateSalesSlipList.add(dto);
			}

			int sumClaimPrice = 0;
			int preMonthBillAmount = 0;

			for (ExtendCorporateSalesSlipDTO corporateSalesSlip : corporateSalesSlipList) {
				// 作成した伝票と違う口座の場合は計算しない
				// ※上の締日対象月内の伝票検索で既に法人と得意先で検索しているため、口座だけを条件にしている。
				if (corporateSalesSlip.getSysAccountId() != payManageDTO.getSysAccountId()) {
					continue;
				}
				sumClaimPrice += corporateSalesSlip.getSumClaimPrice();
			}

			// 入金額がもともと入力されていたものより低い金額になった時、請求額は加算しないといけないためcarryOverAmountを変えずに計算した
			preMonthBillAmount = payManageDTO.getCarryOverAmount() - (payManageDTO.getReceivePrice()
					+ payManageDTO.getCharge() + payManageDTO.getCharge2() + payManageDTO.getCharge3());

			// 入金管理の請求額を更新
			payManageDTO.setBillAmount(sumClaimPrice + preMonthBillAmount);
			// 入金管理TBLを更新
			updatePaymentManagement(payManageDTO);

			/*
			 * 次月の入金管理がある（請求書作成時に、繰越額があれば次月の入金管理が作られる）場合に
			 * 今月の入金に応じて次月の請求額を合わせる（入金された分、減額する）処理
			 *@author boncre t_yamamura
			 */

			ExtendPaymentManagementDTO nextManageDTO = new ExtendPaymentManagementDTO() ;

			nextManageDTO.setDemandMonth(nextMonth);
			nextManageDTO = getPaymentManagement(payManageDTO.getSysCorporationId(), payManageDTO.getSysClientId(),
					payManageDTO.getSysAccountId(), nextManageDTO.getDemandMonth());

			//次月の入金管理がある時のみ更新する
			if (nextManageDTO != null) {

			//次月の繰越額＝当月の請求額ー{次月の入金額 + charge1,2,3}
			//次月の入金額は存在しないので、次月の繰越額＝当月の請求額で算出。
			int nextCarryOverAmount = 0;
			nextCarryOverAmount = payManageDTO.getBillAmount();
					//- (payManageDTO.getReceivePrice()
					//+ payManageDTO.getCharge() + payManageDTO.getCharge2() + payManageDTO.getCharge3());

			nextManageDTO.setCarryOverAmount(nextCarryOverAmount);
			nextManageDTO.setBillAmount(nextCarryOverAmount); //請求額

			//来月の入金管理TBLを更新
			updatePaymentManagement(nextManageDTO);
			}


			// ここから請求書のほうも更新する
			ExtendCorporateBillDTO corporateBillDTO = new ExtendCorporateBillDTO();

			// 対象の請求書データを取得する
			corporateBillDTO = corporateBillDAO.getSearchDemandMonthCorporateBill(payManageDTO.getSysCorporationId(),
					payManageDTO.getDemandMonth(), client.getClientNm(), payManageDTO.getSysAccountId());

			// 対象の請求書が無い場合は更新しない
			if (corporateBillDTO == null) {
				continue;
			}

			// 対象情報をセット
			corporateBillDTO.setReceiveDate(payManageDTO.getReceiveDate());
			corporateBillDTO.setReceivePrice(payManageDTO.getReceivePrice());
			corporateBillDTO.setFreeWord(payManageDTO.getFreeWord());
			corporateBillDTO.setCharge(payManageDTO.getCharge());
			corporateBillDTO.setFreeWord2(payManageDTO.getFreeWord2());
			corporateBillDTO.setCharge2(payManageDTO.getCharge2());
			corporateBillDTO.setFreeWord3(payManageDTO.getFreeWord3());
			corporateBillDTO.setCharge3(payManageDTO.getCharge3());
			//繰越金額の計算 前月請求額 - (入金額 + 各金額)
				corporateBillDTO.setCarryOverAmount(corporateBillDTO.getPreMonthBillAmount() - (corporateBillDTO.getReceivePrice()
																																							+ corporateBillDTO.getCharge()
																																							+ corporateBillDTO.getCharge2()
																																							+ corporateBillDTO.getCharge3()));
			//繰越金額の再設定に伴い、合計請求額の更新
			corporateBillDTO.setBillAmount(sumClaimPrice + corporateBillDTO.getCarryOverAmount());
			// 業販請求書更新処理
			corporateBillDAO.updateCorporateBill(corporateBillDTO);

		} //payManageの拡張for終了

		transactionDAO.commit();

	}

	/**
	 * 入金管理画面の法人と締日で検索する
	 *
	 * @param dispSysCorporationId
	 * @param dispCutoffDate
	 * @return List<ExtendPaymentManagementDTO>
	 * @throws DaoException
	 */
	public List<ExtendPaymentManagementDTO> getSysPaymentManageIdList(long dispSysCorporationId, int dispCutoffDate)
			throws DaoException {

		return new CorporatePaymentManagementDAO().getSearchPaymentManage(dispSysCorporationId, dispCutoffDate);
	}

}
