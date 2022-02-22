package jp.co.kts.app.search.entity;


/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
public class ClientSearchDTO {

	private long sysClientId;
	private String clientNo;
	private long sysCorporationId;
	private String clientNm;
	private char paymentMethod;

	public ClientSearchDTO() {
		setSysClientId(0);
		setSysCorporationId(0);
		setClientNm("");
		setPaymentMethod('0');
	}

	public long getSysClientId() {
		return sysClientId;
	}
	public void setSysClientId(long sysClientId) {
		this.sysClientId = sysClientId;
	}
	public long getSysCorporationId() {
		return sysCorporationId;
	}
	public void setSysCorporationId(long sysCorporationId) {
		this.sysCorporationId = sysCorporationId;
	}
	public String getClientNm() {
		return clientNm;
	}
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}
	public char getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(char paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return clientNo
	 */
	public String getClientNo() {
		return clientNo;
	}

	/**
	 * @param clientNo セットする clientNo
	 */
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}


}
