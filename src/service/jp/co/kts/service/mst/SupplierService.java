/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.service.mst;

import java.util.ArrayList;
import java.util.List;

import jp.co.kts.app.common.entity.CurrencyLedgerDTO;
import jp.co.kts.app.common.entity.MstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.SupplierDAO;

/**
 * [概要]仕入先管理serviceクラス
 * @author Boncre
 *
 */
public class SupplierService {

	//インスタンス生成
	SupplierDAO dao = new SupplierDAO();

	/**
	 * [概要]仕入先リスト取得処理
	 * @return
	 * @throws Exception
	 */
	public List<ExtendMstSupplierDTO> getSupplierList() throws Exception {

		return dao.getSupplierList();
	}

	/**
	 * [概要]仕入先詳細情報取得処理
	 * @param sysSupplierId システム仕入先ID
	 * @return
	 * @throws Exception
	 */
	public ExtendMstSupplierDTO getSupplier(long sysSupplierId) throws Exception {

		return dao.getSupplier(sysSupplierId);
	}

	/**
	 * [概要]仕入先情報更新処理
	 * @param dto マスタ仕入先DTO
	 * @throws Exception
	 */
	public int updateSupplier(ExtendMstSupplierDTO dto) throws Exception {

		return dao.updateSupplier(dto);
	}

	/**
	 * [概要]仕入先情報削除処理
	 * @param sysSupplierId　システム仕入先Id
	 * @throws Exception
	 */
	public int deleteSupplier(long sysSupplierId) throws Exception {

		return dao.deleteSupplier(sysSupplierId);
	}

	/**
	 * [概要]仕入先情報登録処理
	 * @param dto マスタ仕入先DTO
	 * @throws Exception
	 */
	public int registrySupplier(ExtendMstSupplierDTO dto) throws Exception {

		//登録されているシステム仕入先Idの最大値を取得
		dto.setSysSupplierId(new SequenceDAO().getMaxSysSupplierId() + 1);

		return dao.registrySupplier(dto);
	}

	/**
	 * ［概要］仕入先番号の整合性チェックを行う
	 * @param sysSupplierId システム仕入先ID
	 * @param supplierId 仕入先番号
	 * @param validType チェックタイプ
	 * @return result
	 * @throws Exception
	 */
	public boolean validateSupplierNo(ExtendMstSupplierDTO supplierDTO, String validType) throws Exception {

		//結果格納用変数
		boolean result = true;

		//新規登録の仕入先番号の重複チェックを行う
		if (validType.equals("insert")) {
			if (dao.getSupplierNoCnt(supplierDTO.getSupplierNo()) > 0) {
				result = false;
			}
		}

		//更新の場合、登録されている仕入先番号と違う番号が入力されていたら、入力した仕入先Idの重複チェックを行う
		if (validType.equals("update")) {
			MstSupplierDTO dto = new MstSupplierDTO();
			dto = dao.getSupplier(supplierDTO.getSysSupplierId());
			if (!dto.getSupplierNo().equals(supplierDTO.getSupplierNo())) {
				if (dao.getSupplierNoCnt(supplierDTO.getSupplierNo()) > 0) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * ［概要］PoNo頭文字の整合性チェックを行う
	 * @param supplierDTO
	 * @param validType
	 * @return
	 * @throws Exception
	 */
	public boolean validatePoNoInitial(ExtendMstSupplierDTO supplierDTO, String validType) throws Exception {

		//結果格納用変数
		boolean result = true;

		//新規登録のPoNo頭文字の重複チェックを行う
		if (validType.equals("insert")) {
			if (dao.getPoNoInitialCnt(supplierDTO.getPoNoInitial()) > 0) {
				result = false;
			}
		}

		//更新の場合、登録されているPoNo頭文字と違う文字が入力されていたら、入力した仕入先Idの重複チェックを行う
		if (validType.equals("update")) {
			MstSupplierDTO dto = new MstSupplierDTO();
			dto = dao.getSupplier(supplierDTO.getSysSupplierId());
			if (!dto.getPoNoInitial().equals(supplierDTO.getPoNoInitial())) {
				if (dao.getPoNoInitialCnt(supplierDTO.getPoNoInitial()) > 0) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * [概要]通貨IDと通貨名を連結して通貨リストに格納する。
	 * @param currencyList 通貨リスト
	 * @return currencyList
	 */
	public List<CurrencyLedgerDTO> connectCurrencyList(List<CurrencyLedgerDTO> currencyList) {

		//結果格納用リスト
		List<CurrencyLedgerDTO> result = new ArrayList<CurrencyLedgerDTO>();

		for (int i = 0; i < currencyList.size(); i++) {
			//通貨タイプに通貨名を結合しリストに格納
			currencyList.get(i).setCurrencyType(currencyList.get(i).getCurrencyType() + ":" +
					currencyList.get(i).getCurrencyNm());
			result.add(currencyList.get(i));
		}

		return result;
	}

}