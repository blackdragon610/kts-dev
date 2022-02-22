/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.CurrencyLedgerDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;
import jp.co.kts.app.output.entity.ErrorMessageDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;
import jp.co.kts.ui.web.struts.WebConst;

/**
 * 仕入先情報を格納します。
 * @author Boncre
 *
 */
public class SupplierForm extends AppBaseForm {

	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;

	/** システム仕入先ID */
	private long sysSupplierId;

	/** 仕入先リスト */
	private List<ExtendMstSupplierDTO> supplierList = new ArrayList<>();

	/** 仕入先情報DTO */
	private ExtendMstSupplierDTO supplierDTO = new ExtendMstSupplierDTO();

	/** メッセージ用 */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();
	/** メッセージ有無フラグ */
	private String messageFlg = "0";

	/** エラーメッセージ用  */
	private ErrorMessageDTO errorDTO = new ErrorMessageDTO();

	/** 取引通貨リスト */
	private List<CurrencyLedgerDTO> currencyList = new ArrayList<>();

	/** 通貨ID */
	private long currencyId;

	/** リードタイム情報MAP */
	private Map<String, String> leadTimeMap = WebConst.LEAD_TIME_MAP;

	/**
	 * <p>
	 * システム仕入先IDを返却します。
	 * </p>
	 * @return sysSupplierId
	 */
	public long getSysSupplierId() {
		return sysSupplierId;
	}

	/**
	 * <p>
	 * システム仕入先IDを設定します。
	 * </p>
	 * @param sysSupplierId
	 */
	public void setSysSupplierId(long sysSupplierId) {
		this.sysSupplierId = sysSupplierId;
	}

	/**
	 * <p>
	 * 仕入先リストを返却します。
	 * </p>
	 * @return supplierList
	 */
	public List<ExtendMstSupplierDTO> getSupplierList() {
		return supplierList;
	}

	/**
	 * <p>
	 * 仕入先リストを設定します。
	 * </p>
	 * @param supplierList
	 */
	public void setSupplierList(List<ExtendMstSupplierDTO> supplierList) {
		this.supplierList = supplierList;
	}

	/**
	 * <p>
	 * 仕入先情報DTOを返却します。
	 * </p>
	 * @return supplierDTO
	 */
	public ExtendMstSupplierDTO getSupplierDTO() {
		return supplierDTO;
	}

	/**
	 * <p>
	 * 仕入先情報DTOを設定します。
	 * </p>
	 * @param supplierDTO
	 */
	public void setSupplierDTO(ExtendMstSupplierDTO supplierDTO) {
		this.supplierDTO = supplierDTO;
	}

	/**
	 * @return registryDto
	 */
	public RegistryMessageDTO getRegistryDto() {
		return registryDto;
	}

	/**
	 * @param registryDto
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
		this.registryDto = registryDto;
	}

	/**
	 * @return messageFlg
	 */
	public String getMessageFlg() {
		return messageFlg;
	}

	/**
	 * @param messageFlg セットする messageFlg
	 */
	public void setMessageFlg(String messageFlg) {
		this.messageFlg = messageFlg;
	}

	/**
	 * @return errorDTO
	 */
	public ErrorMessageDTO getErrorDTO() {
		return errorDTO;
	}

	/**
	 * @param errorDTO セットする errorDTO
	 */
	public void setErrorDTO(ErrorMessageDTO errorDTO) {
		this.errorDTO = errorDTO;
	}


	/**
	 * <p>
	 * リードタイム情報MAPを返却します。(getterのみ)
	 * </p>
	 * @return leadTimeMap
	 */
	public Map<String, String> getLeadTimeMap() {
		return leadTimeMap;
	}

	/**
	 * <p>
	 * 通貨リストを返却します。
	 * </p>
	 * @return currencyList
	 */
	public List<CurrencyLedgerDTO> getCurrencyList() {
		return currencyList;
	}

	/**
	 * <p>
	 * 通貨リストを設定します。
	 * </p>
	 * @param currencyList
	 */
	public void setCurrencyList(List<CurrencyLedgerDTO> currencyList) {
		this.currencyList = currencyList;
	}

	/**
	 * <p>
	 * 通貨IDを返却します。
	 * </p>
	 * @return currencyId
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * <p>
	 * 通貨IDを設定します。
	 * </p>
	 * @param currencyId
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
	}
}