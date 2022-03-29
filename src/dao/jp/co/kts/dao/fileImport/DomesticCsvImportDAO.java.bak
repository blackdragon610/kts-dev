package jp.co.kts.dao.fileImport;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.CsvImportDTO;
import jp.co.kts.app.common.entity.DomesticCsvImportDTO;

/**
 * 国内CSVインポートのDAOクラス
 * @author Boncre
 *
 */
public class DomesticCsvImportDAO extends BaseDAO{

	/**
	 * 国内商品取込時のファイル登録
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int registryDomesticCsvImport(CsvImportDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		parameters.addParameter("sysDomesticImportId", dto.getSysImportId());

		return update("INS_DOMESTIC_CSV_IMPORT", parameters);
	}

	/**
	 * 国内CSVインポートの情報を取得する
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public DomesticCsvImportDTO getDomesticCsvImport(DomesticCsvImportDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_DOMESTIC_CSV_IMPORT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticCsvImportDTO.class));
	}

	/**
	 * 国内CSVフインポートのファイル名取得
	 * @param fileNm
	 * @return
	 * @throws DaoException
	 */
	public DomesticCsvImportDTO getDomesticCsvFileNm(String fileNm) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("fileNm", fileNm);

		return select("SEL_DOMESTIC_CSV_IMPORT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(DomesticCsvImportDTO.class));
	}
}
