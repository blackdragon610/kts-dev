/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.dao.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstSupplierDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstSupplierDTO;

/**
 * 仕入先管理daoクラス
 * @author Boncre
 *
 */
public class SupplierDAO extends BaseDAO {

	SQLParameters parameters = new SQLParameters();

	/**
	 * [概要]仕入先リストを取得する処理
	 * @return
	 * @throws DaoException
	 */
	public List<ExtendMstSupplierDTO> getSupplierList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSupplierId", 0);
		return selectList("SEL_MST_SUPPLIER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstSupplierDTO.class));
	}

	/**
	 * [概要]仕入先情報を取得する処理
	 * @param sysSupplierId システム仕入先ID
	 * @return
	 * @throws DaoException
	 */
	public ExtendMstSupplierDTO getSupplier(long sysSupplierId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysSupplierId", sysSupplierId);
		return select("SEL_MST_SUPPLIER", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendMstSupplierDTO.class));
	}

	/**
	 * [概要]仕入先情報を更新する処理
	 * @param dto マスタ仕入先DTO
	 * @throws DaoException
	 */
	public int updateSupplier(MstSupplierDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		int result = update("UPD_MST_SUPPLIER", parameters);
		return result;
	}

	/**
	 * [概要]仕入先情報を論理削除する処理
	 * @param sysSupplierId システム仕入先Id
	 * @throws DaoException
	 */
	public int deleteSupplier(long sysSupplierId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		UserInfo userInfo = ActionContext.getLoginUserInfo();

		parameters.addParameter("sysSupplierId", sysSupplierId);
		parameters.addParameter("updateUserId", userInfo.getUserId());
		return update("DEL_MST_SUPPLIER", parameters);
	}

	/**
	 * [概要]仕入先情報を登録する処理
	 * @param dto マスタ仕入先DTO
	 * @throws DaoException
	 */
	public int registrySupplier(MstSupplierDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		int result = update("INS_MST_SUPPLIER", parameters);
		return result;
	}

	/**
	 * ［概要］引数で渡された仕入先Noが既に存在するか、登録数をカウントする。
	 * @param supplierNo 仕入先No
	 * @return 登録数(int)
	 * @throws DaoException
	 */
	public int getSupplierNoCnt(String supplierNo) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("supplierNo", supplierNo);

		return select("CNT_SUPPLIER_NO", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}

	/**
	 * [概要]引数で渡されたPoNoInitialが既に存在するか、登録数をカウントする。
	 * @param poNoInitial
	 * @return
	 * @throws DaoException
	 */
	public int getPoNoInitialCnt(String poNoInitial) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("poNoInitial", poNoInitial);

		return select("CNT_PO_NO_INITIAL", parameters, ResultSetHandlerFactory.getFirstColumnIntegerRowHandler());
	}
}