package jp.co.kts.ui.itemSearch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;
import jp.co.kts.app.search.entity.SearchItemDTO;

/**
 * @author job-s
 *
 */
public class ItemSearchForm extends AppBaseForm {

	private long searchSysItemId;

	private SearchItemDTO searchItemDTO = new SearchItemDTO();

	private List<ExtendMstItemDTO> searchItemList = new ArrayList<>();

	private int itemListSize = 0;

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {

	}
	public long getSearchSysItemId() {
		return searchSysItemId;
	}

	public void setSearchSysItemId(long searchSysItemId) {
		this.searchSysItemId = searchSysItemId;
	}

	public SearchItemDTO getSearchItemDTO() {
		return searchItemDTO;
	}

	public void setSearchItemDTO(SearchItemDTO searchItemDTO) {
		this.searchItemDTO = searchItemDTO;
	}

	public List<ExtendMstItemDTO> getSearchItemList() {
		return searchItemList;
	}

	public void setSearchItemList(List<ExtendMstItemDTO> searchItemList) {
		this.searchItemList = searchItemList;
	}

	/**
	 * @return itemListSize
	 */
	public int getItemListSize() {
		return itemListSize;
	}

	/**
	 * @param itemListSize セットする itemListSize
	 */
	public void setItemListSize(int itemListSize) {
		this.itemListSize = itemListSize;
	}
}
