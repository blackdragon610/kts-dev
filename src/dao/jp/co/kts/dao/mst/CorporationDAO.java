package jp.co.kts.dao.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstCorporationDTO;

public class CorporationDAO extends BaseDAO {

	public List<MstCorporationDTO> getCorporationList() throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "1");

		return selectList("SEL_CORPORATION", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstCorporationDTO.class));
	}

	public MstCorporationDTO getCorporation(long sysCorporationId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_CORPORATION", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstCorporationDTO.class));
	}

	/**
	 * 法人IDから法人名を取得する
	 * @param sysCorporationId
	 * @return corporationNm
	 * @throws DaoException
	 */
	public String getCorporationName(long sysCorporationId) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", sysCorporationId);

		return select("SEL_CORPORATION_NM", parameters, ResultSetHandlerFactory.getFirstColumnStringRowHandler());
	}

	public String getCorporationId(String corporationNm) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("corporationNm", corporationNm);

		return select("SEL_CORPORATION_ID", parameters, ResultSetHandlerFactory.getFirstColumnStringRowHandler());
	}

	public void updateCorporation(MstCorporationDTO corporationDTO) throws DaoException {
		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporationDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_CORPORATION", parameters);
	}

	public void deleteCorporation(long sysCorporationId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysCorporationId", sysCorporationId);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());
		update("UPD_DEL_CORPORATION", parameters);
	}

	public void registryUser(MstCorporationDTO corporationDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(corporationDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		update("INS_CORPORATION", parameters);
	}

	public MstCorporationDTO getCorporation(MstCorporationDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("getListFlg", "0");

		if (StringUtils.isNotEmpty(dto.getFileCorporationNm())) {
			parameters.addParameter("fileCorporationNm", dto.getFileCorporationNm());
		}

		if (dto.getSysCorporationId() != 0) {
			parameters.addParameter("sysCorporationId", dto.getSysCorporationId());
		}

		return select("SEL_CORPORATION", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstCorporationDTO.class));
	}

}
