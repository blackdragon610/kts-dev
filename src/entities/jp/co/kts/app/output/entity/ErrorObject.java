package jp.co.kts.app.output.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aito
 *
 * @param <O>
 */
public class ErrorObject<O> {

    /** 結果 */
    private O resultObject = null;

	private boolean isSuccess = true;

	public O getResultObject() {
		return resultObject;
	}

	public void setResultObject(O resultObject) {
		this.resultObject = resultObject;
	}

	private List<ErrorMessageDTO> errorMessageList = new ArrayList<ErrorMessageDTO>();

	public List<ErrorMessageDTO> getErrorMessageList() {
		return errorMessageList;
	}

	public void setErrorMessageList(List<ErrorMessageDTO> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
