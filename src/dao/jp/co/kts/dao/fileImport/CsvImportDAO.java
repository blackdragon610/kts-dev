package jp.co.kts.dao.fileImport;

import jp.co.keyaki.cleave.fw.core.ActionContext;
import jp.co.keyaki.cleave.fw.core.security.UserInfo;
import jp.co.keyaki.cleave.fw.dao.BaseDAO;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.dao.SQLParameters;
import jp.co.keyaki.cleave.fw.dao.util.ResultSetHandlerFactory;
import jp.co.kts.app.common.entity.CsvImportDTO;
import jp.co.kts.app.common.entity.ExtendKeepCsvImportDTO;

public class CsvImportDAO extends BaseDAO {

	/**
	 * CSV取込時のファイル登録
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public int registryCsvImport(CsvImportDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(dto, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("INS_CSV_IMPORT", parameters);
	}

	/**
	 * CSVインポートの情報を取得する
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public CsvImportDTO getCsvImport(CsvImportDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("", csvImportDTO.getOrderNo());
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_CSV_IMPORT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(CsvImportDTO.class));
	}

	/**
	 * CSVインポートのファイル名取得
	 * @param fileNm
	 * @return
	 * @throws DaoException
	 */
	public CsvImportDTO getCsvFileNm(String fileNm) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("fileNm", fileNm);

		return select("SEL_CSV_IMPORT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(CsvImportDTO.class));
	}

	/**
	 * 在庫数キープCSV取込時のファイル登録
	 * @param csvImportDTO
	 * @return
	 * @throws DaoException
	 */
	public int registryKeepCsvImport(CsvImportDTO csvImportDTO) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		addParametersFromBeanProperties(csvImportDTO, parameters);

		UserInfo userInfo = ActionContext.getLoginUserInfo();
		parameters.addParameter("createUserId", userInfo.getUserId());
		parameters.addParameter("updateUserId", userInfo.getUserId());

		return update("INS_KEEP_CSV_IMPORT", parameters);
	}

	/**
	 * 在庫数キープCSVインポートの情報を取得する
	 * @param dto
	 * @return
	 * @throws DaoException
	 */
	public ExtendKeepCsvImportDTO getKeepCsvImport(ExtendKeepCsvImportDTO dto) throws DaoException {

		SQLParameters parameters = new SQLParameters();
//		parameters.addParameter("", csvImportDTO.getOrderNo());
		addParametersFromBeanProperties(dto, parameters);

		return select("SEL_KEEP_CSV_IMPORT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepCsvImportDTO.class));
	}

	/**
	 * CSVを取り込んだ日付を取得します
	 * @param sysImportId
	 * @return String importUpdateDate
	 * @throws DaoException
	 */
	public String getKeepCsvUpdateDate(long sysImportId) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("sysImportId", sysImportId);

		return select("SEL_KEEP_CSV_UPDATEDATE", parameters, ResultSetHandlerFactory.getFirstColumnStringRowHandler());
	}

	/**
	 * 在庫数キープCSVインポートのファイル名を取得する
	 * @param fileNm
	 * @return
	 * @throws DaoException
	 */
	public ExtendKeepCsvImportDTO getKeepCsvFileNm(String fileNm) throws DaoException {

		SQLParameters parameters = new SQLParameters();
		parameters.addParameter("fileNm", fileNm);

		return select("SEL_KEEP_CSV_IMPORT", parameters, ResultSetHandlerFactory.getNameMatchBeanRowHandler(ExtendKeepCsvImportDTO.class));
	}


}
