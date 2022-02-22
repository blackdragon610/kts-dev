package jp.co.kts.service.mst;

import java.util.List;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstClientDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstClientDTO;
import jp.co.kts.app.search.entity.ClientSearchDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.ClientDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.ui.web.struts.WebConst;

import org.apache.commons.lang.StringUtils;

public class ClientService {

	public List<MstClientDTO> getClientList() throws Exception {

		ClientDAO dao = new ClientDAO();

		return dao.getClientList();
	}

	public List<MstClientDTO> getCorprationClientList(long sysCorporationId) throws Exception {

		ClientDAO dao = new ClientDAO();

		return dao.getCorprationClientList(sysCorporationId);
	}

	public List<ExtendMstClientDTO> getExtClientList(ClientSearchDTO search) throws Exception {

		ClientDAO dao = new ClientDAO();
		List<ExtendMstClientDTO> clientList = dao.getClientList(search);

		for (int i=0; i < clientList.size(); i++){
			if (StringUtils.equals(clientList.get(i).getPaymentMethod(),WebConst.PAYMENT_METHOD_CODE_1)) {
				clientList.get(i).setReceivableBalance(dao.getReceivableBalance(clientList.get(i).getSysClientId()));
			}
		}

		return clientList;
	}

	public MstClientDTO getClient(long sysClientId) throws Exception {

		ClientDAO dao = new ClientDAO();
		MstClientDTO dto = new MstClientDTO();
		 dto = dao.getClient(sysClientId);
		 return dto;
	}

	public ExtendMstClientDTO getDispClient(long sysClientId) throws DaoException  {
		ClientDAO dao = new ClientDAO();
		ExtendMstClientDTO dto = new ExtendMstClientDTO();
		 dto = dao.getDispClient(sysClientId);
		 return dto;
	}

	//社名を元に情報取得
	public ExtendMstClientDTO getDispClientName(String clientNm) throws DaoException  {
		ClientDAO dao = new ClientDAO();
		ExtendMstClientDTO dto = new ExtendMstClientDTO();
		 dto = dao.getDispClientName(clientNm);
		 return dto;
	}

	public void updateClient(MstClientDTO dto) throws Exception {
		ClientDAO dao = new ClientDAO();
		dao.updateClient(dto);
	}

	public void deleteClient(long sysClientId) throws Exception {
		ClientDAO dao = new ClientDAO();
		dao.deleteClient(sysClientId);
	}

	public void registryClient(MstClientDTO dto) throws Exception {
		ClientDAO dao = new ClientDAO();
		dto.setSysClientId(new SequenceDAO().getMaxSysClientId() + 1);
		dao.registryClient(dto);
	}

//	public ExtendMstUserDTO getUserName(long sysUserId) throws Exception {
//		UserDAO dao = new UserDAO();
//		ExtendMstUserDTO dto = new ExtendMstUserDTO();
//		 dto = dao.getUserName(sysUserId);
//
//		 return dto;
//	}

	public int getReceivableBalance(long sysClientId) throws Exception {

		ClientDAO dao = new ClientDAO();
		int receivable = dao.getReceivableBalance(sysClientId);

		return receivable;
	}

	public int getReceivableBalance(long sysClientId, String receiveDate) throws Exception {

		ClientDAO dao = new ClientDAO();
		int receivable = dao.getReceivableBalance(sysClientId,receiveDate+"/01");

		return receivable;
	}

	/**
	 * ［概要］入力値の整合性チェックを行う
	 * @param clientNm 得意先名
	 * @param sysCorporationId 法人ID
	 * @param clientNo 得意先番号
	 * @param validType チェックタイプ
	 * @return result
	 * @throws Exception
	 */
	public Result<MstClientDTO> validate(MstClientDTO clientDto, String validType) throws Exception {

		Result<MstClientDTO> result = new Result<MstClientDTO>();
		ClientDAO dao = new ClientDAO();

		//新規登録の得意先番号の重複チェックを行う
		if (validType.equals("insert")) {
			if(dao.getClientNoCnt(clientDto.getClientNo(), clientDto.getSysCorporationId()) > 0) {
				result.addErrorMessage("LED00122", "「得意先番号」");
			}
		}

		//更新の場合、登録されている得意先番号と違う番号が入力されてたら、入力した得意先番号の重複チェックを行う
		if (validType.equals("update")) {
			MstClientDTO dto = new MstClientDTO();
			dto = dao.getClient(clientDto.getSysClientId());
			if (!dto.getClientNo().equals(clientDto.getClientNo())) {
				if(dao.getClientNoCnt(clientDto.getClientNo(), clientDto.getSysCorporationId()) > 0) {
					result.addErrorMessage("LED00122", "「得意先番号」");
				}
			}
		}
		return result;
	}
}
