package jp.co.kts.service.fileExport;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstRulesDTO;
import jp.co.kts.app.common.entity.MstRulesListDTO;
import jp.co.kts.service.item.ItemService;
/**
 * 商品のエクセルを出力するクラス
 * @author aito
 *
 */
public class ExportRuleListService extends FileExportExcelService implements AutoCloseable{

	{
		/**
		 * 始まりの行
		 */
		rowIdx = 1;

		/**
		 * 始まりの列
		 */
		colIdx = 0;
	}


	/**
	 * 商品の在庫表をエクセル出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public HSSFWorkbook getExportRuleList(List<MstRulesDTO> ruleDto, HSSFWorkbook workBook) throws DaoException {

		HSSFSheet sheet = workBook.getSheetAt(0);
		// シート名の設定.
		//使うかわからないけどコピーしました
		workBook.setSheetName(0, "ID・PASS一覧");

		//値の始まりの行
		rowIdx = 1;
		for (MstRulesDTO dto: ruleDto) {
			if(dto.getItemCheckFlg().equals("on")) {
				for(MstRulesListDTO listDto : dto.getMstRulesDetailList()) {

					colIdx = 0;
		
					//行の設定
					row = sheet.getRow(rowIdx);
					if (row == null) {
						row = sheet.createRow(rowIdx);
					}
					row.setHeightInPoints(16.5F);
					
					//分類
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(dto.getRuleName()));
		
					//名称
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(listDto.getListName()));
		
					//ID
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(listDto.getListId()));
		
					//PASS
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(listDto.getListPass()));
		
					//備考
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(listDto.getListRemarks()));
		
					// リンク
					callCreateCell(row, colIdx++).setCellValue(castRichTextString(listDto.getListLink()));
		
					row = null;
					rowIdx++;
				}
			}
		}

		return workBook;
	}

	/**
	 * 在庫一覧：助ネコCSVを出力します
	 *
	 * @param searchItemDTO
	 * @param workBook
	 * @return
	 * @throws DaoException
	 */
	public void exportCsvRuleList(List<MstRulesDTO> ruleDto, String fname, HttpServletResponse response) throws DaoException {

		ItemService itemService = new ItemService();

		// エクセルファイル出力
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fname);

		try (PrintWriter pw = response.getWriter()) {

			//タイトル設定
			String title =    "分類,"
					 + "名称,"
					 + "ID,"
					 + "PASS,"
					 + "備考,"
					 + "リンク,";

			//CSVファイルに書き込み
			pw.println(title);

			for (MstRulesDTO dto : ruleDto) {
				if(dto.getItemCheckFlg().equals("on")) {
					for(MstRulesListDTO listDto : dto.getMstRulesDetailList()) {
						
						String ruleName			= dto.getRuleName() + ",";
						String ruleDetailName	= listDto.getListName() + ",";
						String ruleDetailId		= listDto.getListId() + ",";
						String ruleDetailP		= listDto.getListPass() + ",";
						String ruleDetaiRemarks	= listDto.getListRemarks() + ",";
						String ruleDetaiLink	= listDto.getListLink() + ",";

						//文字列を結合する
						String csvRecord =  ruleName
											+ ruleDetailName
											+ ruleDetailId
											+ ruleDetailP
											+ ruleDetaiRemarks
											+ ruleDetaiLink;

						//CSVファイルに書き込み
						pw.println(csvRecord);

					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}
}
