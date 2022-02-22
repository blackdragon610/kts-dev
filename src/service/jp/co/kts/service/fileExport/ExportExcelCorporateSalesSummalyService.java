package jp.co.kts.service.fileExport;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;

/**
 * エクセルダウンロード機能の抽象クラス
 * @author aito
 *
 */
public abstract class ExportExcelCorporateSalesSummalyService extends FileExportExcelService {

	/**
	 * 業販売上のエクセルを出力します
	 *
	 * @param saleSearchDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 * @throws Exception
	 */
	public abstract HSSFWorkbook getFileExportCorporateSales(CorporateSaleSearchDTO saleSearchDTO,
			HSSFWorkbook workBook) throws DaoException, Exception;

}
