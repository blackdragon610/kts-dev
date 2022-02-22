package jp.co.kts.app.search.entity;

import java.util.ArrayList;
import java.util.List;

import jp.co.kts.app.extendCommon.entity.ExtendMstItemDTO;

public class SubWindowSearchItemDTO {

	private long searchSysItemId;

	private SearchItemDTO searchItemDTO = new SearchItemDTO();

	private List<ExtendMstItemDTO> searchItemList = new ArrayList<>();

	private int itemListSize = 0;

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

	public int getItemListSize() {
		return itemListSize;
	}

	public void setItemListSize(int itemListSize) {
		this.itemListSize = itemListSize;
	}

}
