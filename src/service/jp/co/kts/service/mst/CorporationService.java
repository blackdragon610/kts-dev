package jp.co.kts.service.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstCorporationDTO;
import jp.co.kts.app.common.entity.MstProfitDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.CorporationDAO;

public class CorporationService {

	public List<MstCorporationDTO> getCorporationList() throws Exception {

		CorporationDAO dao = new CorporationDAO();

		return dao.getCorporationList();
	}

	public MstCorporationDTO getCorporation(long sysCorporationId) throws DaoException   {

		CorporationDAO dao = new CorporationDAO();
		return  dao.getCorporation(sysCorporationId);
	}

	
	public void updateCorporation(MstCorporationDTO corporationDTO) throws Exception {

		CorporationDAO dao = new CorporationDAO();
		dao.updateCorporation(corporationDTO);
	}

	public void deleteCorporation(long sysCorporationId) throws Exception {

		CorporationDAO dao = new CorporationDAO();
		dao.deleteCorporation(sysCorporationId);

	}
	

	public void registryCorporation(MstCorporationDTO corporationDTO) throws Exception {
		CorporationDAO dao = new CorporationDAO();
		corporationDTO.setSysCorporationId(new SequenceDAO().getMaxSysCorporationId() + 1);

		dao.registryUser(corporationDTO);

	}

	public long getCorporationIdFromName(String csvFileName) throws DaoException {

		MstCorporationDTO dto = new MstCorporationDTO();
		dto.setFileCorporationNm(csvFileName);

		CorporationDAO dao = new CorporationDAO();
		dto = dao.getCorporation(dto);

		if (null == dto) {
			return 0;
		}
		return dto.getSysCorporationId();
	}

	public String getCorporationNm(long sysCorporationId) throws DaoException {

		MstCorporationDTO dto = new MstCorporationDTO();
		dto.setSysCorporationId(sysCorporationId);

		CorporationDAO dao = new CorporationDAO();
		dto = dao.getCorporation(dto);

		if (null == dto) {
			return StringUtils.EMPTY;
		}
		return dto.getCorporationNm();
	}
	

	public List<MstProfitDTO> getChannelProfitList(long sysCorporationId) throws DaoException   {

		CorporationDAO dao = new CorporationDAO();
		return  dao.getChannelProfitList(sysCorporationId);
	}
	
	public void updateChannelProfitList(List<MstProfitDTO> profitList) throws Exception {

		CorporationDAO dao = new CorporationDAO();
		for(MstProfitDTO dto : profitList) {
			String tax_flag = dto.getTaxFlg().equals("on") ? "1": "0";
			
			dao.updateProfit(dto, tax_flag);
		}
	}

	public void deleteChannelProfitList(long sysCorporationId) throws Exception {

		CorporationDAO dao = new CorporationDAO();
		dao.deleteChannelProfitList(sysCorporationId);

	}

}
