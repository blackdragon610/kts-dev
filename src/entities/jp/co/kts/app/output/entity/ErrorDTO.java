package jp.co.kts.app.output.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ErrorDTO {

	private int falseIdx;

	private boolean isSuccess = true;

	private String fileName;

	private List<ErrorMessageDTO> errorMessageList = new ArrayList<ErrorMessageDTO>();

	private List<ErrorMessageDTO> errorMessageListBlue = new ArrayList<ErrorMessageDTO>();

	private List<ErrorMessageDTO> errorMessageListGreen =  new ArrayList<ErrorMessageDTO>();

	private List<ErrorMessageDTO> errrorMessageListYellow = new ArrayList<ErrorMessageDTO>();

	private List<ErrorMessageDTO> errorMessageListPurple = new ArrayList<ErrorMessageDTO>();

	private Map<String, String> errorMessageMap = new LinkedHashMap<>();

	private Map<String, String> errorMessageBlueMap = new LinkedHashMap<>();

	private Map<String, String> errorMessageGreenMap = new LinkedHashMap<>();

	private Map<String, String> errorMessageYellowMap = new LinkedHashMap<>();

	private Map<String, String> errorMessagePurpleMap = new LinkedHashMap<>();

	private String errorMessage = StringUtils.EMPTY;

	private int trueCount;

	private boolean notDomesticFlg = true;

	public ErrorDTO() {
		trueCount = 0;
	}

	public List<ErrorMessageDTO> getErrorMessageListPurple() {
		return errorMessageListPurple;
	}

	public void setErrorMessageListPurple(List<ErrorMessageDTO> errorMessageListPurple) {
		this.errorMessageListPurple = errorMessageListPurple;
	}

	public Map<String, String> getErrorMessagePurpleMap() {
		return errorMessagePurpleMap;
	}

	public void setErrorMessagePurpleMap(Map<String, String> errorMessagePurpleMap) {
		this.errorMessagePurpleMap = errorMessagePurpleMap;
	}

	public int getFalseIdx() {
		return falseIdx;
	}

	public void setFalseIdx(int falseIdx) {
		this.falseIdx = falseIdx;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<ErrorMessageDTO> getErrorMessageList() {
		return errorMessageList;
	}

	public void setErrorMessageList(List<ErrorMessageDTO> errorMessageList) {
		this.errorMessageList = errorMessageList;
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

	public int getTrueCount() {
		return trueCount;
	}

	public void setTrueCount(int trueCount) {
		this.trueCount = trueCount;
	}

	/**
	 * notDomesticFlgを取得します。
	 * @return notDomesticFlg
	 */
	public boolean isNotDomesticFlg() {
	    return notDomesticFlg;
	}

	/**
	 * notDomesticFlgを設定します。
	 * @param notDomesticFlg notDomesticFlg
	 */
	public void setNotDomesticFlg(boolean notDomesticFlg) {
	    this.notDomesticFlg = notDomesticFlg;
	}

	/**
	 * @return errorMessageListBlue
	 */
	public List<ErrorMessageDTO> getErrorMessageListBlue() {
		return errorMessageListBlue;
	}

	/**
	 * @param errorMessageListBlue セットする errorMessageListBlue
	 */
	public void setErrorMessageListBlue(List<ErrorMessageDTO> errorMessageListBlue) {
		this.errorMessageListBlue = errorMessageListBlue;
	}

	/**
	 * @return errorMessageListGreen
	 */
	public List<ErrorMessageDTO> getErrorMessageListGreen() {
		return errorMessageListGreen;
	}

	/**
	 * @param errorMessageListGreen セットする errorMessageListGreen
	 */
	public void setErrorMessageListGreen(List<ErrorMessageDTO> errorMessageListGreen) {
		this.errorMessageListGreen = errorMessageListGreen;
	}

	/**
	 * @return errrorMessageListYellow
	 */
	public List<ErrorMessageDTO> getErrrorMessageListYellow() {
		return errrorMessageListYellow;
	}

	/**
	 * @param errrorMessageListYellow セットする errrorMessageListYellow
	 */
	public void setErrrorMessageListYellow(List<ErrorMessageDTO> errrorMessageListYellow) {
		this.errrorMessageListYellow = errrorMessageListYellow;
	}

	/**
	 * @return errorMessageMap
	 */
	public Map<String, String> getErrorMessageMap() {
		return errorMessageMap;
	}

	/**
	 * @param errorMessageMap セットする errorMessageMap
	 */
	public void setErrorMessageMap(Map<String, String> errorMessageMap) {
		this.errorMessageMap = errorMessageMap;
	}

	/**
	 * @return errorMessageBlueMap
	 */
	public Map<String, String> getErrorMessageBlueMap() {
		return errorMessageBlueMap;
	}

	/**
	 * @param errorMessageBlueMap セットする errorMessageBlueMap
	 */
	public void setErrorMessageBlueMap(Map<String, String> errorMessageBlueMap) {
		this.errorMessageBlueMap = errorMessageBlueMap;
	}

	/**
	 * @return errorMessageGreenMap
	 */
	public Map<String, String> getErrorMessageGreenMap() {
		return errorMessageGreenMap;
	}

	/**
	 * @param errorMessageGreenMap セットする errorMessageGreenMap
	 */
	public void setErrorMessageGreenMap(Map<String, String> errorMessageGreenMap) {
		this.errorMessageGreenMap = errorMessageGreenMap;
	}

	/**
	 * @return errorMessageYellowMap
	 */
	public Map<String, String> getErrorMessageYellowMap() {
		return errorMessageYellowMap;
	}

	/**
	 * @param errorMessageYellowMap セットする errorMessageYellowMap
	 */
	public void setErrorMessageYellowMap(Map<String, String> errorMessageYellowMap) {
		this.errorMessageYellowMap = errorMessageYellowMap;
	}

}
