package jp.co.kts.app.output.entity;

import jp.co.kts.app.common.entity.ExcelImportDTO;
import jp.co.kts.service.common.Result;

public class ActionErrorExcelImportDTO {

	/** エラー */
	Result<ExcelImportDTO> result = new Result<ExcelImportDTO>();

	/** Excel */
	ExcelImportDTO excelImportDTO = new ExcelImportDTO();

	/**
	 * @return result
	 */
	public Result<ExcelImportDTO> getResult() {
		return result;
	}

	/**
	 * @param result セットする result
	 */
	public void setResult(Result<ExcelImportDTO> result) {
		this.result = result;
	}

	/**
	 * @return excelImportDTO
	 */
	public ExcelImportDTO getExcelImportDTO() {
		return excelImportDTO;
	}

	/**
	 * @param excelImportDTO セットする excelImportDTO
	 */
	public void setExcelImportDTO(ExcelImportDTO excelImportDTO) {
		this.excelImportDTO = excelImportDTO;
	}

}
