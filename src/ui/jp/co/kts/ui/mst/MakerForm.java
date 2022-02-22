package jp.co.kts.ui.mst;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.app.output.entity.RegistryMessageDTO;

public class MakerForm extends AppBaseForm {

	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;

	/** システムメーカーID */
	private long sysMakerId;

	/** メーカー情報DTO */
	private MstMakerDTO makerDto = new MstMakerDTO();

	/** メーカーリスト */
	private List<MstMakerDTO> makerList = new ArrayList<>();

	/** メッセージ */
	private RegistryMessageDTO registryDto = new RegistryMessageDTO();

	@Override
	protected void doReset(AppActionMapping appMapping,
			HttpServletRequest request) {
	}

	/**
	 * システムメーカーIDを取得します。
	 * @return システムメーカーID
	 */
	public long getSysMakerId() {
	    return sysMakerId;
	}

	/**
	 * システムメーカーIDを設定します。
	 * @param sysMakerId システムメーカーID
	 */
	public void setSysMakerId(long sysMakerId) {
	    this.sysMakerId = sysMakerId;
	}

	/**
	 * メーカー情報DTOを取得します。
	 * @return メーカー情報DTO
	 */
	public MstMakerDTO getMakerDto() {
	    return makerDto;
	}

	/**
	 * メーカー情報DTOを設定します。
	 * @param makerDto メーカー情報DTO
	 */
	public void setMakerDto(MstMakerDTO makerDto) {
	    this.makerDto = makerDto;
	}

	/**
	 * メーカーリストを取得します。
	 * @return メーカーリスト
	 */
	public List<MstMakerDTO> getMakerList() {
	    return makerList;
	}

	/**
	 * メーカーリストを設定します。
	 * @param makerList メーカーリスト
	 */
	public void setMakerList(List<MstMakerDTO> makerList) {
	    this.makerList = makerList;
	}

	/**
	 * @return registryDTO
	 */
	public RegistryMessageDTO getRegistryDto() {
		return registryDto;
	}

	/**
	 * @param registryDTO セットする registryDTO
	 */
	public void setRegistryDto(RegistryMessageDTO registryDto) {
		this.registryDto = registryDto;
	}

}
