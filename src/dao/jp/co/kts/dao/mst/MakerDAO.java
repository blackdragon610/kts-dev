package jp.co.kts.dao.mst;

import java.util.List;
import java.util.Set;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.MstMakerDTO;

public class MakerDAO extends BaseDAO {


	/**
	 * メーカー情報全件取得
	 * @return
	 * @throws DaoException
	 */
	public List<MstMakerDTO> getMakerInfoList() throws DaoException {

		//パラメータ設定
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysMakerId", 0);

		return selectList("SEL_MST_MAKER", parameters,ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstMakerDTO.class));
	}

	/**
	 * メーカー詳細情報取得
	 * @param dto
	 * @return MstMakerDTO
	 * @throws DaoException
	 */
	public MstMakerDTO getMakerDetailInfo(MstMakerDTO dto) throws DaoException {

		//パラメータ設定
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysMakerId", dto.getSysMakerId());

		return select("SEL_MST_MAKER", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstMakerDTO.class));
	}

	/**
	 * メーカー情報の登録
	 * @param dto
	 * @return 影響数
	 * @throws DaoException
	 */
	public int registoryMaker(MstMakerDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("INS_MAKER", parameters);
	}

	/**
	 * メーカー情報の更新
	 * @param dto
	 * @return 影響数
	 * @throws DaoException
	 */
	public int updateMaker(MstMakerDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		parameters.addParameter("sysMakerId", dto.getSysMakerId());
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("UPD_MAKER", parameters);
	}

	/**
	 * メーカー情報の削除
	 * @param dto
	 * @return 影響数
	 * @throws DaoException
	 */
	public int deleteMaker(MstMakerDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();

		parameters.addParameter("sysMakerId", dto.getSysMakerId());
		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("DEL_MAKER", parameters);
	}

	/**
	 * システムメーカーIDリスト取得
	 * @param makerNmSet, makerNmKanaSet
	 * @return List<MstMakerDTO>
	 * @throws DaoException
	 */
	public List<MstMakerDTO> getSysMakerIdList(Set<String> makerNmSet, Set<String> makerNmKanaSet) throws DaoException {

		//パラメータ設定
		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("makerNmSet", makerNmSet);
		parameters.addParameter("makerNmKanaSet", makerNmKanaSet);

		return selectList("SEL_MST_MAKER_ID_LIST", parameters , ResultSetHandlerFactory.getNameMatchBeanRowHandler(MstMakerDTO.class));

	}
}
