package jp.co.kts.service.fileExport;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.extendCommon.entity.ExtendCorporateSalesSlipDTO;
import jp.co.kts.app.search.entity.CorporateSaleSearchDTO;
import jp.co.kts.app.search.entity.SaleSearchDTO;


/**
 * エクセルダウンロード機能の抽象クラス
 * @author aito
 *
 */
public abstract class ExportExcelCorporateSalesService extends FileExportExcelService {

	/**
	 * 業販売上のエクセルを出力します
	 *
	 * @param saleSearchDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 * @throws Exception
	 */
	public abstract HSSFWorkbook getFileExportCorporateSales(CorporateSaleSearchDTO searchDto,
			HSSFWorkbook workBook) throws DaoException, Exception;

}
