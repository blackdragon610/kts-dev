package jp.co.kts.app.output.entity;

import org.apache.commons.lang.StringUtils;

public class ErrorMessageDTO {

	private boolean isSuccess = true;

	private String errorMessage = StringUtils.EMPTY;

	private int resultCnt;

	/**
	 * デフォルトコンストラクタ
	 */
	public ErrorMessageDTO () {

	}
	public ErrorMessageDTO (String errorMessage) {

		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	/**
	 * resultCntを取得します。
	 * @return resultCnt
	 */
	public int getResultCnt() {
	    return resultCnt;
	}
	/**
	 * resultCntを設定します。
	 * @param resultCnt resultCnt
	 */
	public void setResultCnt(int resultCnt) {
	    this.resultCnt = resultCnt;
	}

}
