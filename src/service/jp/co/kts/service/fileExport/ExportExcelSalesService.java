package jp.co.kts.service.fileExport;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.search.entity.SaleSearchDTO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * エクセルダウンロード機能の抽象クラス
 * @author aito
 *
 */
public abstract class ExportExcelSalesService extends FileExportExcelService {

	/**
	 * 売上のエクセルを出力します
	 *
	 * @param saleSearchDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 * @throws Exception
	 */
	public abstract HSSFWorkbook getFileExportSales(SaleSearchDTO saleSearchDTO,
			HSSFWorkbook workBook) throws DaoException, Exception;

}
