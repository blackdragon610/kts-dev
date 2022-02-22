package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.WarehouseDAO;

public class WarehouseService {

	/**
	 * KTS倉庫リスト取得
	 * @return
	 * @throws Exception
	 */
	public List<MstWarehouseDTO> getWarehouseList() throws Exception {

		WarehouseDAO dao = new WarehouseDAO();

		return dao.getWarehouseList();
	}

	/**
	 * 外部倉庫リスト取得
	 * @return
	 * @throws Exception
	 */
	public List<ExtendMstWarehouseDTO> getExternalWarehouseList() throws Exception {

		WarehouseDAO dao = new WarehouseDAO();

		return dao.getExternalWarehouseList();
	}

	/**
	 * KTS倉庫名取得
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public String getWarehouseNm(long sysWarehouseId) throws DaoException {

		MstWarehouseDTO dto = new WarehouseDAO().getWarehouse(sysWarehouseId);

		if (dto == null) {
			return null;
		}
		return dto.getWarehouseNm();
	}


	/**
	 * 外部倉庫名取得
	 * @param sysExternalWarehouseCode
	 * @return
	 * @throws DaoException
	 */
	public String getExternalWarehouseNm(String sysExternalWarehouseCode) throws DaoException {

		MstWarehouseDTO dto = new WarehouseDAO().getExternalWarehouse(sysExternalWarehouseCode);

		if (dto == null) {
			return null;
		}
		return dto.getWarehouseNm();
	}


	public MstWarehouseDTO getWarehouse(long sysWarehouseId) throws Exception {

		WarehouseDAO dao = new WarehouseDAO();
		return  dao.getWarehouse(sysWarehouseId);
	}

	public void updateWarehouse(MstWarehouseDTO warehouseDTO) throws Exception {

		WarehouseDAO dao = new WarehouseDAO();
		dao.updateWarehouse(warehouseDTO);
	}

	public void deleteWarehouse(long sysWarehouseId) throws Exception {

		WarehouseDAO dao = new WarehouseDAO();
		dao.deleteWarehouse(sysWarehouseId);

	}

	public long registryWarehouse(MstWarehouseDTO warehouseDTO) throws Exception {
		WarehouseDAO dao = new WarehouseDAO();
		warehouseDTO.setSysWarehouseId(new SequenceDAO().getMaxSysWarehouseId() + 1);

		long sysWarehouseId = warehouseDTO.getSysWarehouseId();

		dao.registryUser(warehouseDTO);

		return sysWarehouseId;

	}

	public List<ExtendWarehouseStockDTO> getSysItemIdList() throws Exception {

		WarehouseDAO dao = new WarehouseDAO();

		return dao.getSysItemIdList();
	}

}