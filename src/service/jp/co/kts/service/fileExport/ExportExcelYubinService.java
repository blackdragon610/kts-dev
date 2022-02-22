package jp.co.kts.service.fileExport;

import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * エクセルダウンロード機能の抽象クラス
 * @author aito
 *
 */
public abstract class ExportExcelYubinService extends FileExportExcelService {

	/**
	 * 日本郵便の送り状エクセルを出力します
	 *
	 * @param saleSearchDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 * @throws Exception
	 */
	public abstract HSSFWorkbook getFileExportYubin(List<ExtendCorporateSalesSlipDTO> corpSalesSlipList,
			HSSFWorkbook workBook) throws DaoException, Exception;

}
