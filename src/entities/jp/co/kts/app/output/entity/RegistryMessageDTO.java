/*
 *　開発システム　商品在庫・受注管理システム
 *　著作権　　　　Copyright 2016 boncre
 */
package jp.co.kts.app.output.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.kts.service.common.Result;

/**
 * メッセージ用DTOクラス
 *
 *
 *
 * @author n_nozawa
 * 20161116
 */

public class RegistryMessageDTO {
	private String messageFlg;

	private boolean success = true;

	private String registryMessage;

	private String message;

	private int resultCnt;

	/** 国内商品：システム管理ID */
	private long sysManagementId;

	private List<ErrorMessageDTO> errorMessageList = new ArrayList<ErrorMessageDTO>();

	/** エラー */
	Result<RegistryMessageDTO> result = new Result<RegistryMessageDTO>();

	private String errorMessage = StringUtils.EMPTY;

	/**
	 * @return messageFlg
	 */
	public String getMessageFlg() {
		return messageFlg;
	}

	/**
	 * @param messageFlg セットする messageFlg
	 */
	public void setMessageFlg(String messageFlg) {
		this.messageFlg = messageFlg;
	}

	/**
	 * @return ｒegistryMessage
	 */
	public String getRegistryMessage() {
		return registryMessage;
	}

	/**
	 * @param ｒegistryMessage セットする ｒegistryMessage
	 */
	public void setRegistryMessage(String ｒegistryMessage) {
		this.registryMessage = ｒegistryMessage;
	}

	/**
	 * @return success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success セットする success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * messageを取得します。
	 * @return message
	 */
	public String getMessage() {
	    return message;
	}

	/**
	 * messageを設定します。
	 * @param message message
	 */
	public void setMessage(String message) {
	    this.message = message;
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

	/**
	 * errorMessageListを取得します。
	 * @return errorMessageList
	 */
	public List<ErrorMessageDTO> getErrorMessageList() {
	    return errorMessageList;
	}

	/**
	 * errorMessageListを設定します。
	 * @param errorMessageList errorMessageList
	 */
	public void setErrorMessageList(List<ErrorMessageDTO> errorMessageList) {
	    this.errorMessageList = errorMessageList;
	}

	/**
	 * エラーを取得します。
	 * @return エラー
	 */
	public Result<RegistryMessageDTO> getResult() {
	    return result;
	}

	/**
	 * エラーを設定します。
	 * @param result エラー
	 */
	public void setResult(Result<RegistryMessageDTO> result) {
	    this.result = result;
	}

	/**
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage セットする errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return sysManagementId
	 */
	public long getSysManagementId() {
		return sysManagementId;
	}

	/**
	 * @param sysManagementId セットする sysManagementId
	 */
	public void setSysManagementId(long sysManagementId) {
		this.sysManagementId = sysManagementId;
	}

}