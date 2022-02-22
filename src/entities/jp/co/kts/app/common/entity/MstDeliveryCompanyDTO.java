package jp.co.kts.app.common.entity;

/**
 * Delivery company : ヤマト, 佐川, 西濃, 郵政
 *
 * @author wahaha
 */

public class MstDeliveryCompanyDTO {

	private long companyId;

	private String companyName;

	/**
	 * <p>
	 * get company id 
	 * </p>
	 * @return companyId
	 */
	public long getCompanyId() {
		return this.companyId;
	}

	/**
	 * <p>
	 * set company id
	 * </p>
	 * @param companyId
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	/**
	 * <p>
	 * get company name 
	 * </p>
	 * @return companyName
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * <p>
	 * set company name
	 * </p>
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
