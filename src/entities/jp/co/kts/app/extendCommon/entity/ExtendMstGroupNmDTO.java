package jp.co.kts.app.extendCommon.entity;

import jp.co.kts.app.common.entity.MstGroupNmDTO;

public class ExtendMstGroupNmDTO extends MstGroupNmDTO {


	/** システム大分類名 */
	private String largeGroupNm;

	/** システム中分類名 */
	private String middleGroupNm;

	/** システム小分類名 */
	private String smallGroupNm;

	/** システム分類名（結合） */
	private String groupNmConcat;

	public String getLargeGroupNm() {
		return largeGroupNm;
	}

	public void setLargeGroupNm(String largeGroupNm) {
		this.largeGroupNm = largeGroupNm;
	}

	public String getMiddleGroupNm() {
		return middleGroupNm;
	}

	public void setMiddleGroupNm(String middleGroupNm) {
		this.middleGroupNm = middleGroupNm;
	}

	public String getSmallGroupNm() {
		return smallGroupNm;
	}

	public void setSmallGroupNm(String smallGroupNm) {
		this.smallGroupNm = smallGroupNm;
	}

	public String getGroupNmConcat() {
		return groupNmConcat;
	}

	public void setGroupNmConcat(String groupNmConcat) {
		this.groupNmConcat = groupNmConcat;
	}



}
