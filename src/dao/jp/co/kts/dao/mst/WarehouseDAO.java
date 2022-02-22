package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstWarehouseDTO;
import jp.co.kts.app.extendCommon.entity.ExtendWarehouseStockDTO;

public class WarehouseDAO extends BaseDAO {


	/**
	 *
	 * 倉庫List検索
	 *
	 * @return
	 * @throws DaoException
	 */
	public List<MstWarehouseDTO> getWarehouseList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstWarehouseDTO.class));
	}


	/**
	 *
	 * 外部倉庫List検索
	 *
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstWarehouseDTO> getExternalWarehouseList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_EXTERNAL_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstWarehouseDTO.class));
	}

	/**
	 *
	 * 倉庫検索
	 *
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public MstWarehouseDTO getWarehouse(long sysWarehouseId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysWarehouseId", sysWarehouseId);

		return select("SEL_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstWarehouseDTO.class));
	}


	/**
	 * 外部倉庫検索
	 * @param sysWarehouseId
	 * @return
	 * @throws DaoException
	 */
	public MstWarehouseDTO getExternalWarehouse(String sysExternalWarehouseCode) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysExternalWarehouseCode", sysExternalWarehouseCode);

		return select("SEL_EXTERNAL_WAREHOUSE", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstWarehouseDTO.class));
	}


	/**
	 *
	 * 更新
	 *
	 * @param warehouseDTO
	 * @throws DaoException
	 */
	public void updateWarehouse(MstWarehouseDTO warehouseDTO) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(warehouseDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_WAREHOUSE", parameters);
	}

	/**
	 *
	 * 削除
	 *
	 * @param sysWarehouseId
	 * @throws DaoException
	 */
	public void deleteWarehouse(long sysWarehouseId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysWarehouseId", sysWarehouseId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_WAREHOUSE", parameters);
	}

	/**
	 *
	 * 登録
	 *
	 * @param warehouseDTO
	 * @throws DaoException
	 */
	public void registryUser(MstWarehouseDTO warehouseDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(warehouseDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());


		update("INS_WAREHOUSE", parameters);

	}

	/**
	 *
	 * 全ての商品IDを検索
	 *
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendWarehouseStockDTO> getSysItemIdList() throws DaoException {

		SQLParameters parameters = new SQLParameters();

		return selectList("SEARCH_SYS_ITEM_ID",parameters,ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendWarehouseStockDTO.class));
	}
}
