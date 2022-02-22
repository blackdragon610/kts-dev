package jp.co.kts.service.mst;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.kts.app.common.entity.MstAccountDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstAccountDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.AccountDAO;
import jp.co.kts.service.common.Result;

public class AccountService {

	public void registryAccount(MstAccountDTO dto, List<ExtendMstAccountDTO> accountList) throws Exception {

		AccountDAO dao = new AccountDAO();

		dto.setSysAccountId(new SequenceDAO().getMaxSysAccountId() + 1);
		if (StringUtils.equals("on", dto.getPriorFlag())) {
			dto.setPriorFlag("1");

			//他口座分ループ
			for (ExtendMstAccountDTO accountDto: accountList) {
				//他口座は優先度外す
				dao.updatePrior(accountDto, 1);
			}
		} else {
			dto.setPriorFlag("0");

			//登録された口座情報がひとつしかない場合は強制的に優先度をつける
			if (accountList.size() == 0) {
				dto.setPriorFlag("1");
			}
		}

		dao.registryAccount(dto);
	}

	public ExtendMstAccountDTO getAccount(long sysAccountId) throws Exception {

		AccountDAO dao = new AccountDAO();
		ExtendMstAccountDTO dto = new ExtendMstAccountDTO();
		 dto = dao.getAccount(sysAccountId);
		 return dto;
	}

	/**
	 * 更新処理
	 * @param dto
	 * @throws Exception
	 */
	public void updateAccount(MstAccountDTO dto, int listCount) throws Exception {

		if (StringUtils.equals("on", dto.getPriorFlag())) {
			dto.setPriorFlag("1");
		} else {
			dto.setPriorFlag("0");
		}

		AccountDAO dao = new AccountDAO();
		dao.updateAccount(dto, listCount);
	}

	public void deleteAccount(long sysAccountId) throws Exception {
		AccountDAO dao = new AccountDAO();
		dao.deleteAccount(sysAccountId);
	}

	/**
	 * 法人IDに複数の口座があるか検索
	 * @param sysAccountId
	 * @return
	 * @throws Exception
	 */
	public List<ExtendMstAccountDTO> getAccountList(long sysAccountId) throws Exception {

		AccountDAO dao = new AccountDAO();
		return dao.getAccountList(sysAccountId);
	}

	public Result<MstAccountDTO> validate(String accountNm, long sysAccountId) throws Exception {

//		LoginService service = new LoginService();
		Result<MstAccountDTO> result = new Result<MstAccountDTO>();

		return result;
	}

	/**
	 * 他法人IDが存在した場合、優先度の変更
	 * @param list
	 * @param accountDto
	 * @throws DaoException
	 */
	public void updatePrior(List<ExtendMstAccountDTO> list, MstAccountDTO accountDto) throws DaoException {

		AccountDAO dao = new AccountDAO();
		int listCount = 0;

		//今回更新した口座が優先なら他口座を優先から外す
		if (StringUtils.equals( "1", accountDto.getPriorFlag())) {

			//他口座分ループ
			for (ExtendMstAccountDTO dto: list) {

				if (StringUtils.equals("1", dto.getPriorFlag()) && dto.getSysAccountId() != accountDto.getSysAccountId()) {
					dao.updatePrior(dto, 1);
				}
			}
		}

		//優先度がすべて外れてしまっているか確認
		for (ExtendMstAccountDTO dto: list) {
			if (StringUtils.equals("1", dto.getPriorFlag())) {
				listCount++;
			}
		}

		//優先度がすべて外れてしまっていたら
		if (listCount == 0) {
			//一番最初の口座情報を優先
			dao.updatePrior(list.get(0), listCount);
		}
	}
}
