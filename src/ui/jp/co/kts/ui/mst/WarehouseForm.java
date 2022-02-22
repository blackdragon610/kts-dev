package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;

public class WarehouseForm extends AppBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long sysWarehouseId;

	private List<MstWarehouseDTO> warehouseList = new ArrayList<>();

	private MstWarehouseDTO warehouseDTO = new MstWarehouseDTO();

	private String alertType;

	private List<ExtendWarehouseStockDTO> sysItemListAll;

	private String warehouseCount;

	/**
	 * @return sysWarehouseId
	 */
	public long getSysWarehouseId() {
		return sysWarehouseId;
	}







	/**
	 * @param sysWarehouseId セットする sysWarehouseId
	 */
	public void setSysWarehouseId(long sysWarehouseId) {
		this.sysWarehouseId = sysWarehouseId;
	}







	/**
	 * @return warehouseList
	 */
	public List<MstWarehouseDTO> getWarehouseList() {
		return warehouseList;
	}







	/**
	 * @param warehouseList セットする warehouseList
	 */
	public void setWarehouseList(List<MstWarehouseDTO> warehouseList) {
		this.warehouseList = warehouseList;
	}







	/**
	 * @return warehouseDTO
	 */
	public MstWarehouseDTO getWarehouseDTO() {
		return warehouseDTO;
	}







	/**
	 * @param warehouseDTO セットする warehouseDTO
	 */
	public void setWarehouseDTO(MstWarehouseDTO warehouseDTO) {
		this.warehouseDTO = warehouseDTO;
	}







	/**
	 * @return alertType
	 */
	public String getAlertType() {
		return alertType;
	}







	/**
	 * @param alertType セットする alertType
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}







	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
		this.alertType = "0";
	}






	/**
	 * sysItemListAllを取得します。
	 * @return sysItemListAll
	 */
	public List<ExtendWarehouseStockDTO> getSysItemListAll() {
		return sysItemListAll;
	}






	/**
	 * sysItemListAllを設定します。
	 * @param sysItemListAll sysItemListAll
	 */
	public void setSysItemListAll(List<ExtendWarehouseStockDTO> sysItemListAll) {
		this.sysItemListAll = sysItemListAll;
	}







	public String getWarehouseCount() {
		return warehouseCount;
	}







	public void setWarehouseCount(String warehouseCount) {
		this.warehouseCount = warehouseCount;
	}

}
